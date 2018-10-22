package com.googosoft.commons;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.fzgn.jcsz.DwbService;
import com.googosoft.util.StringUtils;
import com.googosoft.util.Validate;

/**
 * 通用工具
 * @author master
 * @version 1.0
 */
@Component
public class CommonUtil {
	
	@Resource(name="dwbService")
	private DwbService dwbService;//单例	
	
	@Resource(name="jdbcTemplate")
	private DBHelper cacheDao;
	
	private static DBHelper dao;
	
	@PostConstruct
	public void init(){
		CommonUtil.dao = cacheDao;
	}
		
	/**
	 * 导出请使用PageService.java中的导出，修改完成后本方法废弃
	 */
	public static List<M_largedata> doExp(String columns) {
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		Gson gson =new Gson();
		ArrayList list = gson.fromJson(columns,ArrayList.class);
		for (int i = 0,len=list.size();i < len; i++) {
			Map map = (Map) list.get(i);
			String expData = StringUtils.StringFilter(map.get("expData")+"");
			String expVal = StringUtils.StringFilter(map.get("expVal")+"");
			String expType = StringUtils.StringFilter(map.get("expType")+"");
			m = new M_largedata();
			if(Validate.noNull(expType)&&("dbl".equals(expType)||"num".equals(expType))){
				m.setColtype("Number");
				if("dbl".equals(expType)){
					m.setColstyle("double");
				}
			}
			m.setName(expData);
			m.setShowname(expVal);
			mlist.add(m);
			m = null;
		}
		return mlist;
	}
	/**
	 * 单位弹窗信息处理类
	 * @param xx  人员或单位的信息
	 * @param flag  标志 单位 D：普通  M：多条 
	 * 例子： (000001)小明    (000001)实验室1 作为传入的数据格式
	 * @return 返回对应的人员编号或单位编号
	 * @throws Exception 
	 */
	public static String getDwXx(String dwxx){
		dwxx = getBeginText(dwxx);
		dwxx = bmhTodwbh(dwxx);
		return dwxx;
	}
	/**
	 * 将(bh)mc;(bh)mc转成'bh','bh'格式
	 * @param bhmcs 多个(bh)mc;(bh)mc格式的信息
	 * @param fgbj 分隔标记
	 * @return 编号
	 * @throws Exception 
	 */
	public static String bhmcsToBhs(String bhmcs,String fgbj){
		if(Validate.noNull(bhmcs)){
			String[] bhmcarr = bhmcs.split(fgbj);
			StringBuffer bhs = new StringBuffer();
			for(int i = 0; i < bhmcarr.length; i++){
				bhs.append("'" + getBeginText(bhmcarr[i]) + "',");
			}
			bhs.deleteCharAt(bhs.length() - 1);
			return bhs.toString();
		}else {
			return "";
		}
	}
	
	
	/**
	 * 部门号转单位编号（多个(bmh)dwmc;(bmh)dwmc转成'dwbh','dwbh'）
	 * @param bmhs 多个部门号
	 * @param fgbj 分隔标记
	 * @return 单位编号
	 * @throws Exception 
	 */
	public static String bmhsTodwbhs(String bmhs,String fgbj) throws Exception{
		if(Validate.noNull(bmhs)){
			String[] bmharr = bmhs.split(fgbj);
			StringBuffer dwbhs = new StringBuffer();
			for(int i = 0; i < bmharr.length; i++){
				dwbhs.append("'" + getBeginText(bmharr[i]) + "',");
			}
			dwbhs.deleteCharAt(dwbhs.length() - 1);
			
			List<Map<String, Object>>  list = dao.queryForList("SELECT DWBH FROM GX_SYS_DWB WHERE BMH in (?)", new Object[]{dwbhs.toString()});
			dwbhs = null;
			dwbhs = new StringBuffer();
			if(list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					Map<String,Object> map = (Map<String,Object>)list.get(i);
					dwbhs.append("'" + Validate.isNullToDefault(map.get("DWBH"),"") + "',");
					map = null;
				}
				dwbhs.deleteCharAt(dwbhs.length() - 1);
				return dwbhs.toString();
			}else{
				return "";
			}
		}else {
			return "";
		}
	}
	/**
	 * 单位编号转部门号
	 * @param dwbh 单位编号
	 * @return 部门号
	 * @throws Exception 
	 */
	public static String dwbhTobmh(String dwbh) throws Exception{
		return dao.queryForObject("SELECT BMH FROM GX_SYS_DWB WHERE DWBH=?", new Object[]{dwbh},String.class);
	}
	
	/**
	 * 部门号转 （部门号）部门名称
	 */
	public static String bmhTomc(String bmh){
		if(Validate.noNull(bmh)){
			String mc = dao.queryForSingleValue("SELECT '('||BMH||')'||MC FROM GX_SYS_DWB WHERE BMH=?", new Object[]{bmh});
			return mc;
		}else {
			return "";
		}
	}
	
	/**
	 * 部门号转单位编号
	 * @param bmh 部门号
	 * @return 单位编号
	 * @throws Exception 
	 */
	public static String bmhTodwbh(String bmh){
		if(Validate.noNull(bmh)){
			String dwbh = dao.queryForSingleValue("SELECT DWBH FROM GX_SYS_DWB WHERE BMH=?", new Object[]{bmh});
			return dwbh;
		}else {
			return "";
		}
	}
	
	/**
	 * 地点号转地点编号
	 * @param ddh 部门号
	 * @return 地点编号
	 * @throws Exception 
	 */
	public static String ddhToDdbh(String ddh){
		if(Validate.noNull(ddh)){
			return dao.queryForSingleValue("select ddbh from zc_sys_ddb where ddh = ?", new Object[]{ddh});
		}else {
			return "";
		}
	}
	
	/**
	 * 地点信息通用处理
	 * @param ddxx  地点的信息  (000001)国子软件 
	 * @return 返回对应的地点编号
	 * @throws Exception 
	 */
	public static String getDdbh(String ddxx){
		ddxx = getBeginText(ddxx);
		return ddhToDdbh(ddxx);
	}
	
	/**
	 * 人员信息通用处理
	 * @param ryxx  人员的信息  (000001)小明  
	 * @return 返回对应的人员编号
	 * @throws Exception 
	 */
	public static String getRybh(String ryxx){
		ryxx = getBeginText(ryxx);
		ryxx = ryghTorybh(ryxx);
		return ryxx;
	}
	
	/**
	 * 人员信息通用处理
	 * @param rybh  人员编号
	 * @return 返回人员的信息  (000001)小明
	 * @throws Exception 
	 */
	public static String getXm(String rybh){
		if(rybh.equals(""))
		{
			return "";
		}
		return rybhToRyxm(rybh);
	}

	/**
	 * 由人员编号返回标准格式的人员信息
	 * 例子：000001------>(0101021126)小明
	 * @param rybh 人员编号
	 * @return  人员标准信息
	 * @throws Exception 
	 */
	public static String rybhToRyxm(String rybh){
		String sql = "SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB WHERE RYBH=?";
		return dao.queryForSingleValue(sql, new Object[]{rybh});
	}
	
	/**
	 * 由主键得到人员信息
	 * @param ryid 
	 * @return  
	 * @throws Exception 
	 */
	public static Map getRyxx(String ryid){
		String sql = "SELECT * FROM GX_SYS_RYB WHERE guid=?";
		return dao.queryForMap(sql, new Object[]{ryid});
	}

	/**
	 * 人员编号转人员工号
	 * @param rybh 人员编号
	 * @return 人员工号
	 * @throws Exception 
	 */
	public static String rybhTorygh(String rybh) throws Exception{
		return dao.queryForObject("SELECT RYGH FROM GX_SYS_RYB WHERE RYBH=?", new Object[]{rybh},String.class);
	}
	/**
	 * 人员工号转人员编号（多个(rygh)xm;(rygh)xm转成'rybh','rybh'）
	 * @param ryghs 多个(rygh)xm;(rygh)xm
	 * @param fgbj 分隔标记
	 * @return 人员编号
	 * @throws Exception 
	 */
	public static String ryghsTorybhs(String ryghs,String fgbj) throws Exception{
		if(Validate.noNull(ryghs)){
			String[] rygharr = ryghs.split(fgbj);
			StringBuffer rybhs = new StringBuffer();
			for(int i = 0; i < rygharr.length; i++){
				rybhs.append("'" + getBeginText(rygharr[i]) + "',");
			}
			rybhs.deleteCharAt(rybhs.length() - 1);
			
			List<Map<String,Object>> list = dao.queryForList("SELECT RYBH FROM gx_sys_ryb WHERE rygh in (?)", new Object[]{rybhs.toString()});
			rybhs = null;
			rybhs = new StringBuffer();
			if(list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					Map<String,Object> map = (Map<String,Object>)list.get(i);
					rybhs.append("'" + Validate.isNullToDefault(map.get("RYBH"),"") + "',");
					map = null;
				}
				rybhs.deleteCharAt(rybhs.length() - 1);
				return rybhs.toString();
			}else{
				return "";
			}
		}else {
			return "";
		}
	}
	/**
	 * 人员工号转人员编号
	 * @param rygh 人员工号
	 * @return 人员编号
	 * @throws Exception 
	 */
	public static String ryghTorybh(String rygh){
		if(Validate.noNull(rygh)){
			String dwbh = dao.queryForSingleValue("SELECT RYBH FROM GX_SYS_RYB WHERE RYGH=?", new Object[]{rygh});
			return dwbh;
		}else {
			return "";
		}
	}
	
	/**
	 * 获取括号之间内容
	 * 数据例子：(000001)小明
	 * @param text
	 * @return 000001
	 */
	public static String getBeginText(String text){
		if(Validate.noNull(text)){
			int i = text.indexOf(")");
			if(i>0)
				return text.substring(1, i);
			else
				return "";
		}
		else
		{
			return "";
		}
	}
	/**
	 * 获取）后面的内容
	 * 数据例子：(000001)小明
	 * @param text
	 * @return 小明
	 */
	public static String getEndText(String text){
		if(Validate.noNull(text)){
			int i = text.indexOf(")");
			if(i>0)
				return text = text.substring(i+1);
			else
				return text;
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * 补齐字符串(汉字按一个字符算，省略补齐字符)
	 * @param str 需要补齐的字符串
	 * @param cd 总长度
	 * @param coltype 字段类型（String：在右侧补齐，Number：在左侧补齐）
	 * @param errlist 存放错误信息的集合
	 * @param zj 主键字段的名称
	 * @param zjzd 主键字段的内容
	 * @param dqzd 当前字段的名称
	 * @param flag 为空时：超长的字段把错误信息放入errlist  不为空时：超长的字段直接截取，不显示错误信息
	 * @return
	 */
	public static String completeStrOne(Object str, int cd, String coltype, List errlist, String zj, String zjzd, String dqzd, String flag){
		return completeStrOne(str, cd, " ", coltype, errlist, zj, zjzd, dqzd, flag);
	}

	/**
	 * 补齐字符串(汉字按一个字符算)
	 * @param str 需要补齐的字符串
	 * @param cd 总长度
	 * @param zd 用哪个字符补齐
	 * @param coltype 字段类型（String：在右侧补齐，Number：在左侧补齐）
	 * @param errlist 存放错误信息的集合
	 * @param zj 主键字段的名称
	 * @param zjzd 主键字段的内容
	 * @param dqzd 当前字段的名称
	 * @param flag 为空时：超长的字段把错误信息放入errlist  不为空时：超长的字段直接截取，不显示错误信息
	 * @return
	 */
	public static String completeStrOne(Object str, int cd, String zd, String coltype, List errlist, String zj, String zjzd, String dqzd, String flag){
		String data = Validate.isNullToDefault(str, "") + "";
		if(cd > 0){
			int varlen = data.toCharArray().length;
			int zdlen = zd.toCharArray().length;
			if(varlen < cd){//如果要显示的字符长度不够，则用补齐字符补齐
				for(int i = varlen; i < cd; i++){
					if("String".equals(coltype)){
						data = data + zd;
					}
					else{
						data = zd + data;
					}
					if(zdlen != 1){
						i = i + zdlen - 1;
					}
				}
			}
			else if(varlen > cd){//如果要显示的字符超长，则要把错误信息存入list
				if(Validate.isNull(flag)){
					String text = "";
					if(Validate.noNull(zjzd)){
						text = zj + zjzd + "的";
					}
					errlist.add(text + dqzd + "超长，规定长度" + cd + "，实际长度" + varlen + "，请修改后导出");
				}
				else{
					data = data.substring(0,cd);
				}
			}
		}
		return data;
	}
	
	/**
	 * 补齐字符串(一个汉字当两个字符，省略补齐字符)
	 * @param str 需要补齐的字符串
	 * @param cd 总长度
	 * @param coltype 字段类型（String：在右侧补齐，Number：在左侧补齐）
	 * @param errlist 存放错误信息的集合
	 * @param zj 主键字段的名称
	 * @param zjzd 主键字段的内容
	 * @param dqzd 当前字段的名称
	 * @param flag 为空时：超长的字段把错误信息放入errlist  不为空时：超长的字段直接截取，不显示错误信息
	 * @return
	 */
	public static String completeStrTwo(Object str, int cd, String coltype, List errlist, String zj, String zjzd, String dqzd, String flag){
		return completeStrTwo(str, cd, " ", coltype, errlist, zj, zjzd, dqzd, flag);
	}
	/**
	 * 补齐字符串（一个汉字当两个字符）
	 * @param str 需要补齐的字符串
	 * @param cd 总长度
	 * @param zd 用哪个字符补齐
	 * @param coltype 字段类型（String：在右侧补齐，Number：在左侧补齐）
	 * @param errlist 存放错误信息的集合
	 * @param zj 主键字段的名称
	 * @param zjzd 主键字段的内容
	 * @param dqzd 当前字段的名称
	 * @param flag 为空时：超长的字段把错误信息放入errlist  不为空时：超长的字段直接截取，不显示错误信息
	 * @return
	 */
	public static String completeStrTwo(Object str, int cd, String zd, String coltype, List errlist, String zj, String zjzd, String dqzd, String flag){
		String data = Validate.isNullToDefault(str, "") + "";
		if(cd > 0){
			String chinese = "[^\\x00-\\xff]|[×±°]";
			int varlen = 0;//当前显示字段的长度
			for (int i = 0; i < data.length(); i++) {
	            String temp = data.substring(i, i + 1);
	            /* 判断是否为中文字符 */
	            if (temp.matches(chinese)) {
	            	varlen += 2;
	            } else {
	            	varlen += 1;
	            }
	        }
			int zdlen = 0;//补齐字符的长度
			for (int i = 0; i < zd.length(); i++) {
	            String temp = zd.substring(i, i + 1);
	            /* 判断是否为中文字符 */
	            if (temp.matches(chinese)) {
	            	zdlen += 2;
	            } else {
	            	zdlen += 1;
	            }
	        }
			
			if(varlen < cd){//如果要显示的字符长度不够，则用补齐字符补齐
				for(int i = varlen; i < cd; i++){
					if("String".equals(coltype)){
						data = data + zd;
					}
					else{
						data = zd + data;
					}
					if(zdlen != 1){
						i = i + zdlen - 1;
					}
				}
			}
			else if(varlen > cd){
				if(Validate.isNull(flag)){//如果要显示的字符超长，则要把错误信息存入list
					String text = "";
					if(Validate.noNull(zjzd)){
						text = zj + zjzd + "的";
					}
					errlist.add(text + dqzd + "超长，规定长度" + cd + "，实际长度" + varlen + "（一个汉字等于两个字符），请修改后导出");
				}else{
					int elen = 0;//存放当前字符的长度
					String endStr = "";
					for (int i = 0; i < data.length(); i++) {
			            String temp = data.substring(i, i + 1);
			            /* 判断是否为中文字符 */
			            if (temp.matches(chinese)) {//一个中文占两个字符
			            	elen += 2;
			            } else {
			            	elen += 1;
			            }
			            if(elen > cd){
			            	break;
			            }
			            else{
			            	endStr += temp;
			            	if(elen == cd){
				            	break;
				            }
			            }
			        }
					data = endStr;
				}
			}
		}
		return data;
	}
	
	/**
	 * 根据分类号进行十二大类分类判断返回相应的字母标志 
	 * @param FLH
	 * @return
	 */
	public static String GetTemplWord(String FLH) {
		String returnString = "";
		if (Validate.noNull(FLH)) {
			String flh = FLH.substring(0, 8);
			if (flh.length() == 8) {
				if (flh.substring(0, 2).equals("01")) {//房屋及构筑物
					returnString = "A";
				}else if(flh.substring(0, 2).equals("02")){
					if(flh.substring(0, 4).equals("0201")){//土地
						returnString = "C";
					}else if(flh.substring(0, 4).equals("0202")){//植物
						returnString = "D";
					}
				}else if(flh.substring(0, 2).equals("03")){//通用设备--仪器仪表
					returnString = "E";
				}else if(flh.substring(0, 2).equals("04")){
					if(flh.substring(0, 4).equals("0413")){//车辆
						returnString = "G";
					}else{//通用设备--机电设备
						returnString = "E";
					}
				}else if(flh.substring(0, 2).equals("05")){//通用设备-电子设备
					returnString = "E";
				}else if(flh.substring(0, 2).equals("06")){//通用设备--印刷机械
					returnString = "E";
				}else if(flh.substring(0, 2).equals("07")){//通用设备--卫生医疗器械
					returnString = "E";
				}else if(flh.substring(0, 2).equals("08")){//通用设备--文体设备
					returnString = "E";
				}else if(flh.substring(0, 2).equals("09")){//通用设备--标本模型
					returnString = "E";
				}else if(flh.substring(0, 2).equals("10")){//文物及陈列品
					returnString = "H";
				}else if(flh.substring(0, 2).equals("11")){//图书
					returnString = "I";
				}else if(flh.substring(0, 2).equals("12")){//通用设备--工具、量具和器皿
					returnString = "E";
				}else if(flh.substring(0, 2).equals("13")){//家具
					returnString = "F";
				}else if(flh.substring(0, 2).equals("14")){//通用设备--行政办公设备
					returnString = "E";
				}else if(flh.substring(0, 2).equals("15")){//被服装具
					returnString = "M";
				}else if(flh.substring(0, 2).equals("16")){//牲畜
					returnString = "K";
				}else if(flh.substring(0, 2).equals("17")){//无形资产
					returnString = "L";
				}
			}
		}
		return returnString;
	}
	/**
	 * 根据分类号获取模板号
	 * @param FLH
	 * @return
	 */
	public static String GetMbhByFlh(String flh) {
		if(Validate.isNull(flh)){
			return "";
		}
		else{
			String flbz2 = flh.substring(0,2);
			String flbz4 = flh.substring(0,4);
			String mbh = "";
			if("0101".equals(flbz4)){
				mbh = "01";
			}else if("0102".equals(flbz4)){
				mbh = "02";
			}else if("0201".equals(flbz4)){
				mbh = "03";
			}else if("0202".equals(flbz4)){
				mbh = "04";
			}else if("0413".equals(flbz4) || "0711".equals(flbz4)){
				mbh = "06";
			}else if("0571".equals(flbz4)){
				mbh = "11";
			}else if("09".equals(flbz2) || "10".equals(flbz2)){
				mbh = "07";
			}else if("11".equals(flbz2)){
				mbh = "08";
			}else if("13".equals(flbz2) || "15".equals(flbz2)){
				mbh = "09";
			}else if("16".equals(flbz2)){
				mbh = "10";
			}else if("17".equals(flbz2)){
				mbh = "12";
			}else{
				mbh = "05";
			}
			return mbh;
		}
	}
	/**
	 * @author Ldh
	 * @param tableName 表名
	 * @param zdName 字段名
	 * @param num 需要生成的新流水号的位数
	 * @param where where条件（例如： and z.guid = '000001'）
	 * @return bhNew新生成的流水号
	 */
	public static String getNewFlownum(String tableName,String zdName,int num,String where){
		String bhOld = dao.queryForSingleValue("select nvl(max(to_char(z."+zdName+")),0)+1 from "+tableName+" z where 1 = 1 "+where+"");
		String bhNew = "";
		for(int i = 0;i < num - bhOld.length();i++){
			bhNew = bhNew + "0";
		}
		bhNew += bhOld;
//		System.err.println("bhOld:"+bhOld+"  bhNew:"+bhNew);
		return bhNew;
	}
	
	
	public static String getNewcxFlownum(String tableName,String zdName,int num,String pzz){
		String bhOld = dao.queryForSingleValue("select nvl(max(to_char(z."+zdName+")),0)+1 from "+tableName+" z where 1 = 1 and pzz='"+pzz+"' ");
		String bhNew = "";
		for(int i = 0;i < num - bhOld.length();i++){
			bhNew = bhNew + "0";
		}
		bhNew += bhOld;
//		System.err.println("bhOld:"+bhOld+"  bhNew:"+bhNew);
		return bhNew;
	}

	/**
	 * @author wzd
	 * 2018-1-2
	 * 通过当前账套获取会计制度
	 * @param dqzt当前账套id
	 */
	public static String getKjzd( HttpSession session){
		String dqzt =  Constant.getztid(session);
		String kjmb = dao.queryForSingleValue(" select zd from cw_kjzdqy where zt ='"+dqzt+"'  ");
		return kjmb;
		
	}
	
	/**
	 * 根据当前账套查询cw_pzlrzb表和cw_kmyeb表里的会计制度,结果为空的话默认session里的会计制度
	 * 格式：001','002
	 * @param session
	 * @return
	 */
	public static String getKjzdByPzlrAndKmye(HttpSession session,String nd){
		String dqzt =  Constant.getztid(session);
		StringBuffer sql = new StringBuffer();
//		sql.append(" select");
//		sql.append(" wm_concat(distinct kjzd) from(");
//		sql.append(" select distinct kjzd from cw_pzlrzb where sszt=?");
//		sql.append(" union");
//		sql.append(" select distinct kjzd from cw_kmyeb where sszt=?");
//		sql.append(")");
		sql.append(" select kjzd from cw_kmyeb where sszt=? and nd=? and rownum=1");
		String kjzd = Validate.isNullToDefaultString(dao.queryForSingleValue(sql.toString(), new Object[]{dqzt,nd}),"");
		if(Validate.noNull(kjzd)){
			kjzd = kjzd.replace(",","','");
		}
		return kjzd;
	}
	
	/**
	 * 查询院办报账员
	 * @return
	 */
	public static Map getYbbzy(){
		String sql = "select r.xm,r.guid from gx_sys_dwb t left join gx_sys_ryb r on t.dwld=r.rybh where t.dwbh=? and rownum=1";
		return dao.queryForMap(sql,new Object[]{"101"});
	}
	
	/**
	 * 查询院办分管领导
	 * @return
	 */
	public static Map getYbfgld(){
		String sql = "select r.xm,r.guid from gx_sys_dwb t left join gx_sys_ryb r on t.fgld=r.rybh where t.dwbh=? and rownum=1";
		return dao.queryForMap(sql,new Object[]{"101"});
	}
	
	/**
	 * 查询本单位的报账员
	 * @return
	 */
	public static Map getBmbzy(){
		String sql = "select r.guid,r.xm from zc_sys_jsryb t left join gx_sys_ryb r on r.rybh=t.rybh where r.dwbh=? and t.jsbh=?";
		return dao.queryForMap(sql,new Object[]{LUser.getDwbh(),"13"});
	}
	
	/**
	 * 查询本单位的报账员
	 * @return
	 */
	public static List getBmbzyList(){
		String sql = "select r.guid from zc_sys_jsryb t left join gx_sys_ryb r on r.rybh=t.rybh where r.dwbh=? and t.jsbh=?";
		List list = dao.queryForList(sql,new Object[]{LUser.getDwbh(),"13"});
		List people = new ArrayList<String>();
		if(list!=null&&list.size()>0){
			for(int i=0,len=list.size();i<len;i++){
				Map map = (Map)list.get(i);
				people.add(Validate.isNullToDefault(map.get("GUID"),""));
			}
		}
		return people;
	}
	
	/**
	 * 检查当前登录人是不是报账员
	 * @return
	 */
	public static boolean checkSbmbzy(){
		String sql = "select r.guid,r.xm from zc_sys_jsryb t left join gx_sys_ryb r on r.rybh=t.rybh where r.dwbh=? and t.jsbh=? and r.guid=?";
		List list = dao.queryForList(sql,new Object[]{LUser.getDwbh(),"13",LUser.getGuid()});
		if(list==null||list.size()==0){
			return false;
		}
		return true;
	}
	
	public static String getRyGuidByXm(String rybh){
		String sql = "select guid from gx_sys_ryb where rybh=?";
		if(Validate.noNull(rybh)&&rybh.contains(")")){
			rybh = rybh.substring(1,rybh.lastIndexOf(")"));
		}
		return Validate.isNullToDefaultString(dao.queryForSingleValue(sql,new Object[]{rybh}),"");
	}
	/**
	 * 项目有多条情况
	 * @param xmguid
	 * @return
	 */
	public static String getXmDlBYGuid(String xmguid){
		String xmguidArry="";
		String sql;
		if(xmguid.contains(",")) {
			xmguidArry = xmguid.replace(",", "','");
			sql = "select xmdl from cw_xmjbxxb where guid in('"+xmguidArry+"')";
		}else {
			sql = "select xmdl from cw_xmjbxxb where guid ='"+xmguid+"'";
		}
		return Validate.isNullToDefaultString(dao.queryForSingleValue(sql), "");
	}
	/**
	 * 
	 * @param xmguid
	 * @return
	 */
	public static String getXGuid(String guid){
		String sql = " SELECT distinct s.xmdl FROM CW_CCSQSPB_XM xm left join XMINFOS s on s.guid = xm.jfbh WHERE CCSQID=?";
		return Validate.isNullToDefaultString(dao.queryForSingleValue(sql,new Object[]{guid}), "");
	}
	/**
	 * 打印装订线边距
	 */
	public static int getDyzdbj() {
		String sql="select mc from GX_SYS_DMB where zl='dyxx' and dm='01'";
		String mc=dao.queryForSingleValue(sql);
		return new Integer(mc);
	}
	/**
	 * 获取 项目的 可用 金额
	 * 项目信息表中剩余金额减去函数求得的所有被占用的金额（FUN_GETYYJE）
	 * @author Biming and QiGongtong 
	 * @date 创建时间：2018年9月1日 星期六 
	 */
	public static  BigDecimal getXmkyje(String xmguid,HttpSession session){
		String kyjeS;
		if(Validate.noNull(xmguid)) {
			String sqlkyje = " select nvl(syje,0) - nvl(FUN_GETYYJE(t.guid),0) from CW_XMJBXXB t where guid='"+xmguid+"'";
			 kyjeS = Validate.isNullToDefaultString(dao.queryForSingleValue(sqlkyje), "0.00");
		}else {
			kyjeS = "0.00";
		}
		BigDecimal kyje = new BigDecimal(kyjeS);
		kyje.setScale(2);
		return kyje;
	}
	/**
	 * 考虑到从这个项目借款，到了第三步结算的时候会冲掉一部门款项，
	 * 所以第二步函数已用金额加上借款就不合适，所以该函数去掉了借款的占用金额。
	 * @author BiMing
	 * @Time 2018年10月19日下午2:07:16
	 */
	public static BigDecimal getXmkyje_2(String xmguid, HttpSession session) {
		String kyjeS;
		if(Validate.noNull(xmguid)) {
			String sqlkyje = " select nvl(syje,0) - nvl(FUN_GETYYJE_2(t.guid),0) from CW_XMJBXXB t where guid='"+xmguid+"'";
			 kyjeS = Validate.isNullToDefaultString(dao.queryForSingleValue(sqlkyje), "0.00");
		}else {
			kyjeS = "0.00";
		}
		BigDecimal kyje = new BigDecimal(kyjeS);
		kyje.setScale(2);
		StringBuffer sqlJkje = new StringBuffer();
		sqlJkje.append(" select sum( nvl(zcje,0) ) AS zcje from CW_JKYWB_MXB m,cw_jkywb t   WHERE m.jkid=t.gid AND jfbh=?  and t.jkr=?   and  t.gid in");
		sqlJkje.append(" (select bxid  from cw_pzlrbxdzb  where pzid in (select guid from cw_pzlrzb where pzzt != '00') and bxtype = 'jkbx') ");
		String jkje = Validate.isNullToDefaultString(dao.queryForSingleValue(sqlJkje.toString(),new Object[] {xmguid,LUser.getGuid()}), "0.00");//查询当前登录人从该项目借款金额，且该项目钱款已支付
		BigDecimal jkjes = new BigDecimal(jkje);
		kyje = kyje.add(jkjes);
		return kyje;
	}
    @Test
	public static void main(String[] args,HttpSession session) {
		String mxguid = "3735D5C8FCE94A6EADD81530FD3C5BFE";
		System.out.println(CommonUtil.getXmkyje(mxguid, session));
	}
}
