package com.googosoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.googosoft.dao.base.DBHelper;
import com.googosoft.service.fzgn.jcsz.DwbService;

/**
 * 弹窗数据处理类
 * @author master
 * @version 1.0
 */
@Component
public class WindowUtil {
	
	@Resource(name="dwbService")
	private DwbService dwbService;//单例	
	
	@Resource(name="jdbcTemplate")
	private DBHelper cacheDao;
	
	private static DBHelper dao;
	
	@PostConstruct
	public void init(){
		this.dao = cacheDao;
	}
	
	/**
	 * 获取括号之间内容
	 * 数据例子：(000001)小明
	 * @param text
	 * @return 000001
	 */
	public static String getText(String text){
		if(Validate.noNull(text)){
			int i = text.indexOf(")");
			if(i>=0)
				text = text.substring(1, i);
		}
		return text;
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
			if(i>=0)
				text = text.substring(i+1);
		}
		return text;
	}

	/**
	 * 将(bh)mc;(bh)mc转成'bh','bh'格式
	 * @param bhmcs 多个(bh)mc;(bh)mc格式的信息
	 * @param fgbj 分隔标记
	 * @return 编号
	 * @throws Exception 
	 */
	public static String bhmcsToBhs(String bhmcs,String fgbj) throws Exception{
		return bhmcsToBhsOrMcs(bhmcs,fgbj,true,true);
	}

	/**
	 * 将(bh)mc;(bh)mc转成bh,bh 或者 'bh','bh' 或者  mc,mc 或者 'mc','mc' 格式
	 * @param bhmcs 多个(bh)mc;(bh)mc格式的信息
	 * @param fgbj 分隔标记
	 * @param isbh 返回编号还是名称  true：编号（括号里边的信息）  false：名称（括号外边的信息）
	 * @param flag 是否需要单引号 true：拼接单引号 false：不拼接单引号
	 * @return bh,bh 或者 'bh','bh' 或者  mc,mc 或者 'mc','mc'
	 * @throws Exception 
	 */
	public static String bhmcsToBhsOrMcs(String bhmcs,String fgbj,boolean isbh,boolean flag) throws Exception{
		if(Validate.noNull(bhmcs)){
			String dyh = flag ? "'" : "";
			String[] bhmcarr = bhmcs.split(fgbj);
			StringBuffer bhs = new StringBuffer();
			for(int i = 0; i < bhmcarr.length; i++){
				if(isbh){
					bhs.append(dyh + getText(bhmcarr[i]) + dyh + ",");
				}
				else{
					bhs.append(dyh + getEndText(bhmcarr[i]) + dyh + ",");
				}
			}
			bhs.deleteCharAt(bhs.length() - 1);
			return bhs.toString();
		}else {
			return "";
		}
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
				rybhs.append("'" + getText(rygharr[i]) + "',");
			}
			rybhs.deleteCharAt(rybhs.length() - 1);
			
			List list = dao.queryForList("SELECT RYBH FROM gx_sys_ryb WHERE rygh in (?)", new Object[]{rybhs.toString()});
			rybhs = null;
			rybhs = new StringBuffer();
			if(list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					Map map = (Map)list.get(i);
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
			List list = dao.queryForList("SELECT RYBH FROM GX_SYS_RYB WHERE RYGH=?", new Object[]{rygh});
			if(list.size()==1){
				return ((Map)list.get(0)).get("RYBH")+"";
			}else{
				return "";
			}
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
			List list = dao.queryForList("SELECT DWBH FROM GX_SYS_DWB WHERE BMH=?", new Object[]{bmh});
			if(list.size()==1){
				return ((Map)list.get(0)).get("DWBH")+"";
			}else{
				return "";
			}
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
				dwbhs.append("'" + getText(bmharr[i]) + "',");
			}
			dwbhs.deleteCharAt(dwbhs.length() - 1);
			
			List list = dao.queryForList("SELECT DWBH FROM GX_SYS_DWB WHERE BMH in (?)", new Object[]{dwbhs.toString()});
			dwbhs = null;
			dwbhs = new StringBuffer();
			if(list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					Map map = (Map)list.get(i);
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
	 * 地点号转地点编号
	 * @param ddh 地点号
	 * @return 地点编号
	 * @throws Exception 
	 */
	public static String ddhToddbh(String ddh) throws Exception{
		if(Validate.noNull(ddh)){
			return dao.queryForObject("SELECT DDBH FROM ZC_SYS_DDB WHERE DDH=?", new Object[]{ddh},String.class);
		}else {
			return "";
		}
	}
	/**
	 * 地点编号转地点号
	 * @param ddbh 单位编号
	 * @return 地点号
	 * @throws Exception 
	 */
	public static String ddbhToddh(String ddbh) throws Exception{
		String sql = "SELECT DDH FROM ZC_SYS_DDB WHERE DDBH=?";
		return dao.queryForSingleValue(sql, new Object[]{ddbh});
	}
	
	/**
	 * 将(wxsbh)wxs转成wxsbh
	 * @param wxs
	 * @return
	 * @throws Exception
	 */
	public static String wxsTowxsbh(String wxs){
		if(Validate.noNull(wxs)){
			return wxs.substring(1).substring(0,wxs.indexOf(")") - 1);
		}else {
			return "";
		}
	}

	/**
	 * 将(flh)flmc;(flh)flmc转成'(flh)flmc','(flh)flmc'格式
	 * @param flhs 多个(flh)flmc;(flh)flmc格式的分类号
	 * @param fgbj 分隔标记
	 * @return 单位编号
	 * @throws Exception 
	 */
	public static String flhsToflbhs(String flhs,String fgbj) throws Exception{
		if(Validate.noNull(flhs)){
			String[] flharr = flhs.split(fgbj);
			StringBuffer flbhs = new StringBuffer();
			for(int i = 0; i < flharr.length; i++){
				flbhs.append("'" + flharr[i] + "',");
			}
			flbhs.deleteCharAt(flbhs.length() - 1);
			return flbhs.toString();
		}else {
			return "";
		}
	}
	
	/**
	 * 人员或单位弹窗信息处理类
	 * @param xx  人员或单位的信息
	 * @param flag  标志 人员 R 单位 D 
	 * 例子： (000001)小明    (000001)实验室1 作为传入的数据格式
	 * @return 返回对应的人员编号或单位编号
	 * @throws Exception 
	 */
	public static String getXx(String xx,String flag){
		if(Validate.isNull(xx)){
			return "";
		}
		else{
			xx = getText(xx);
			try {
				if("R".equals(flag)){
					xx = ryghTorybh(xx);
				}else if("D".equals(flag)){
					xx = bmhTodwbh(xx);
				}else if("P".equals(flag)){
					xx = ddhToddbh(xx); 
				}else if("F".equals(flag)){
					xx = ddhToddbh(xx); 
	//			}else if("W".equals(flag)){
	//				xx = wxsTowxsbh(xx); 
				}else if("DS".equals(flag)){
					xx = bmhsTodwbhs(xx,";");
				}else if("FS".equals(flag)){
					xx = flhsToflbhs(xx,";");
				}
				else if(Validate.isNull(flag)){
					xx = bhmcsToBhs(xx,";");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return xx;
		}
	}
	/**
	 * 弹窗信息处理类（多选）
	 * @param xx  信息
	 * @param flag  标志 人员 R 单位 D 
	 * 例子： (000001)小明    (000001)实验室1 作为传入的数据格式
	 * @return 返回对应的人员编号或单位编号
	 * @throws Exception 
	 */
	public static String getXxs(String xx,String flag){
		String fgbj = ";";
		try {
			if("R".equals(flag)){
				xx = ryghsTorybhs(xx,fgbj);
			}else if("D".equals(flag)){
				xx = bmhsTodwbhs(xx,fgbj);
			}else if("F".equals(flag)){
				xx = flhsToflbhs(xx,fgbj);
			}else if("BMTB".equals(flag)){//多个编号名称返回多个编号，不拼接单引号
				xx = bhmcsToBhsOrMcs(xx,fgbj,true,false);
			}else if("BMTM".equals(flag)){//多个编号名称返回多个名称，不拼接单引号
				xx = bhmcsToBhsOrMcs(xx,fgbj,false,false);
			}else if("BMTBY".equals(flag)){//多个编号名称返回多个编号，拼接单引号
				xx = bhmcsToBhsOrMcs(xx,fgbj,true,true);
			}else if("BMTMY".equals(flag)){//多个编号名称返回多个名称，拼接单引号
				xx = bhmcsToBhsOrMcs(xx,fgbj,false,true);
			}
			else{
				xx = bhmcsToBhs(xx,fgbj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xx;
	}
	/**
	 * 由人员编号返回标准格式的人员信息
	 * 例子：000001------>(0101021126)小明
	 * @param rybh 人员编号
	 * @return  人员标准信息
	 * @throws Exception 
	 */
	public static String getRyxx(String rybh){
		String sql = "SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB WHERE RYBH=?";
		return dao.queryForSingleValue(sql, new Object[]{rybh});
	}
	/**
	 * 由单位编号返回标准格式的单位信息
	 * 例子：000001------>(0101021126)小明
	 * @param rybh 人员编号
	 * @return  人员标准信息
	 * @throws Exception 
	 */
	public static String getDwxx(String dwbh){
		String sql = "SELECT '('||BMH||')'||MC FROM GX_SYS_DWB WHERE DWBH=?";
		return dao.queryForSingleValue(sql, new Object[]{dwbh});
	}
	/**
	 * 由单位编号返回单位名称
	 * 
	 * @param rybh 人员编号
	 * @return  人员标准信息
	 * @throws Exception 
	 */
	public static String getDwmc(String dwbh){
		String sql = "SELECT MC FROM GX_SYS_DWB WHERE DWBH=?";
		return dao.queryForSingleValue(sql, new Object[]{dwbh});
	}
	
	/**
	 * 由财政分类号返回标准代码
	 * 例子：
	 * @param flh 财政分类号
	 * @return  财政分类标准代码
	 * @throws Exception 
	 */
	public static String getCzflBzdm(String flh){
		String sql = "SELECT BZDM FROM ZC_FLB_CZBN WHERE flh=?";
		return dao.queryForSingleValue(sql, new Object[]{flh});
	}
	
	public static List getLmList(){
		List list = new ArrayList();
		Map map = new HashMap();
		map.put("bh", "yqbh");
		map.put("mc","资产编号");
		list.add(map);
		map = null;
		
		map = new HashMap();
		map.put("bh", "yqmc");
		map.put("mc","资产名称");
		list.add(map);
		map = null;
		
		map = new HashMap();
		map.put("bh", "flh");
		map.put("mc","分类号");
		list.add(map);
		map = null;
		
		map = new HashMap();
		map.put("bh", "flmc");
		map.put("mc","分类名称");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "xmh");
		map.put("mc","项目号");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "dj");
		map.put("mc","单价");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "zzj");
		map.put("mc","总价");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "jldw");
		map.put("mc","计量单位");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "syr");
		map.put("mc","使用人");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "syfx");
		map.put("mc","使用方向");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "sydw");
		map.put("mc","使用/管理部门");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "bzxx");
		map.put("mc","存放地点");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "xz");
		map.put("mc","现状");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "bdzt");
		map.put("mc","资产状态");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "jfkm");
		map.put("mc","经费来源");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "zcly");
		map.put("mc","资产来源");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "cgzzxs");
		map.put("mc","采购组织形式");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "gzrq");
		map.put("mc","购置日期");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "rzrq");
		map.put("mc","入帐日期");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "dzrrq");
		map.put("mc","调转入日期");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "flgbm");
		map.put("mc","财政分类");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "pzh");
		map.put("mc","凭证号");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "ysdh");
		map.put("mc","验收单号");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "jzlx");
		map.put("mc","记帐类型");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "xk");
		map.put("mc","学科");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "xklb");
		map.put("mc","学科类别");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "cj");
		map.put("mc","生产厂家");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "xh");
		map.put("mc","型号");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "gg");
		map.put("mc","规格");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "gkry");
		map.put("mc","归口审核人");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "gkdw");
		map.put("mc","归口单位");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "gkrq");
		map.put("mc","归口审核日期");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "gkyj");
		map.put("mc","归口审核意见");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "shr");
		map.put("mc","财务审核人");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "shrq");
		map.put("mc","财务审核日期");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "shyj");
		map.put("mc","财务审核意见");
		list.add(map);
		map = null;
		
		return list;
	}
	
	public static List getfhList(){
		List list = new ArrayList();

		Map map = new HashMap();
		map.put("bh", "=");
		map.put("mc","等于");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "!=");
		map.put("mc","不等于");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "in");
		map.put("mc","包含");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "not in ");
		map.put("mc","不包含");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "like");
		map.put("mc","相似于");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "not like");
		map.put("mc","不相似");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", ">");
		map.put("mc","大于");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "<");
		map.put("mc","小于");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", ">=");
		map.put("mc","大于等于");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "<=");
		map.put("mc","小于等于");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "zbh");
		map.put("mc","左包含");
		list.add(map);
		map = null;

		map = new HashMap();
		map.put("bh", "ybh");
		map.put("mc","右包含");
		list.add(map);
		map = null;
		
		return list;
	}
	
	public static void main(String[] args) {
		
	}
}
