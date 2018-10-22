package com.googosoft.dao.kjhs.pzxx;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.Xtxx;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Repository
public class PzlrDao extends BaseDao{
	@Resource(name="pzlrService")
	PzlrService pzlrService;
	/**
	 * 获取交互数据
	 * */
	public Map<String, Object> getKjkmInfo(String kmbh,String kjzd){
		String sql = "select (select kmmc from cw_kjkmszb where kmjc = '1' and kmbh = substr(a.kmbh,1,4) and kjzd = '"+kjzd+"' )||'--'||a.kmmc as kmmc,a.yefx,a.sfjjflkm,a.bmhs,a.xmhs,b.kmzye as kmye from cw_kjkmszb a left join cw_kmyeb b "
				+ " on a.guid = b.kmid where a.kmbh = '"+kmbh+"' and a.sfmj = '1' and a.kjzd = '"+kjzd+"' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getJjkmInfo(String kmbh){
		String sql = "select a.kmmc as zcjjflkm from cw_jjkmb a where a.kmbh = '"+kmbh+"' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getBmInfo(String bmbh){
		String sql = "select a.mc as bmmc,wm_concat(distinct(xm.xmbh)) as xmbh from gx_sys_dwb a left join cw_xmjbxxb xm on xm.bmbh = a.dwbh where a.dwbh = '"+bmbh+"'  group by a.mc  ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getXmInfo(String xmbh,String bmbh,String pzzbguid){
		String strWhere = "";
		if(! Validate.isNullOrEmpty(bmbh)) {
			strWhere += " and a.bmbh = '"+bmbh+"'";
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.xmmc,");
		sql.append(" wm_concat(distinct to_char(k1.kmmc)) as srkmmc,");
		sql.append(" wm_concat(distinct to_char(k1.kmmc)) as srkm,");
		sql.append(" wm_concat(distinct to_char(k2.kmmc)) as zckmmc,");
		sql.append(" wm_concat(distinct to_char(k2.kmmc)) as zckm,");
		sql.append(" r.xm as fzr, A.YSJE,");
		sql.append(" DECODE(NVL(A.SYJE,0),0,'0.00',TO_CHAR(ROUND(A.SYJE,2),'FM99999999990.00'))AS syje,");
		if(Validate.noNull(pzzbguid)){
			//编辑 计算出的可用金额 + 报销金额
			sql.append(" TO_CHAR(");
			sql.append(" (");
			sql.append(" nvl(syje,0) ");//剩余金额
			sql.append(" - nvl(FUN_GETYYJE(a.guid),0) ");// - 已用金额
			sql.append(" + nvl((select sum(nvl(jfje,0)) from cw_pzlrmxb m left join cw_pzlrzb z on m.pzbh=z.guid where m.bmbh='"+bmbh+"' and m.xmbh='"+xmbh+"' and z.guid='"+pzzbguid+"'),0) ");// + 在借方的明细金额
			sql.append(" ) ");
			sql.append(" ,'FM99999999990.00') ");
			sql.append(" AS kyje, ");
		}else{
			//录入 计算出的可用金额
		    sql.append(" TO_CHAR((nvl(syje,0) - nvl(FUN_GETYYJE(a.guid),0)),'FM99999999990.00')AS kyje,");
		}
		sql.append(" r.xm as fzrxm,a.gkxxm as xmgkxxm,");
		sql.append(" a.gkxxm,b.bz,b.xmlxmc as xmlx,");
		sql.append(" b.xmlxmc");
		sql.append(" from cw_xmjbxxb a");
		sql.append(" left join cw_xmlxb b  on a.xmlx = b.guid");
		sql.append(" left join cw_xmsrb c  on b.guid = c.xmlxbh");
		sql.append(" left join cw_xmzcb d on b.guid = d.xmlxbh");
		sql.append(" left join cw_kjkmszb k1 on k1.kmbh=c.srkmbh");
		sql.append(" left join cw_kjkmszb k2 on k2.kmbh=d.zckmbh");
		sql.append(" left join gx_sys_ryb r on r.rybh=a.fzr");
		sql.append(" where a.xmbh = '"+xmbh+"' ");
		sql.append(strWhere);
		sql.append(" group by a.xmmc,a.YSJE,a.syje,a.gkxxm,b.xmlxmc,r.xm,b.bz,a.guid");
		return db.queryForMap(sql.toString());
	}
	
	/**
	 * 联想输入
	 * */
	public String getSgstZy(String zy) {
		String czr = LUser.getGuid();
		String sql = "select zynr from cw_cyzyszb where zynr like '%"+zy+"%' and sfqy ='1'";
		List<Map<String, Object>> list = db.queryForList(sql);
		String result = "";
		for (Map<String, Object> map : list) {
			result += map.get("zynr") + ";;"; 
		}
		return result;
	}
	public String getSgstKmbh(String kmbh,String kjzd) {
		String sql = "select '('||kmbh||')'||kmmc as kmbh from cw_kjkmszb where kmbh like '"+kmbh+"%' and sfmj = '1' and kjzd='"+kjzd+"' order by kmbh asc";
		List<Map<String, Object>> list = db.queryForList(sql);
		String result = "";
		for (Map<String, Object> map : list) {
			result += map.get("kmbh") + ";;"; 
		}
		return result;
	}
	public String validateKmbh(String value,String kjzd) {
		String sql = "select count(d.kmbh) as counts from cw_kjkmszb d where d.kmbh = ? and sfmj = '1' and kjzd='"+kjzd+"'";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String getSgstJjfl(String kmbh) {
		String sql = "select '('||kmbh||')'||kmmc as kmbh from cw_jjkmb where kmbh not in(select distinct k from cw_jjkmb where k is not null) and kmbh like '"+kmbh+"%' order by kmbh";
		List<Map<String, Object>> list = db.queryForList(sql);
		String result = "";
		for (Map<String, Object> map : list) {
			result += map.get("kmbh") + ";;"; 
		}
		return result;
	}
	public String validateZy(String value) {
		String czr = LUser.getGuid();
		String sql = "select count(d.zynr) as counts from cw_cyzyszb d where d.zynr = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateJjfl(String value) {
		String sql = "select count(d.kmbh) as counts from cw_jjkmb d where d.kmbh = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String getSgstDwbh(String dwbh) {
		String sql = "select '('||dwbh||')'||mc as dwbh from gx_sys_dwb where dwbh like '"+dwbh+"%' order by dwbh";
		List<Map<String, Object>> list = db.queryForList(sql);
		String result = "";
		for (Map<String, Object> map : list) {
			result += map.get("dwbh") + ";;"; 
		}
		return result;
	}
	public String validateDwbh(String value) {
		String sql = "select count(d.bmh) as counts from gx_sys_dwb d where d.bmh = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String getSgstXmbh(String bmbh,String xmbh) {
		String strWhere = "";
		if(!Validate.isNullOrEmpty(bmbh)) {
			strWhere += " and bmbh like '%"+bmbh+"%'";
		}
		String sql = "select '('||xmbh||')'||xmmc as xmbh from cw_xmjbxxb where xmbh like '"+xmbh+"%' "+strWhere+" and guid not in (select nvl(jfbh, '0') from XMINFOS) order by xmbh";
		List<Map<String, Object>> list = db.queryForList(sql);
		String result = "";
		for (Map<String, Object> map : list) {
			result += map.get("xmbh") + ";;"; 
		}
		return result;
	}
	public String validateXmbh(String bmbh,String value) {
		String strWhere = "";
		if(!Validate.isNullOrEmpty(bmbh)) {
			strWhere += " and bmbh like '%"+bmbh+"%'";
		}
		String sql = "select count(d.xmbh) as counts from cw_xmjbxxb d where d.xmbh = ?"+strWhere;
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	
	/**
	 * 主表，明细表，辅助表,展示表插入数据
	 * */
	public int insertPzlrZb(String zbid,String pzzt,PageData pd,HttpSession session) {
		ReentrantLock lock = new ReentrantLock();
		String pzz=pd.getString("pzz");
		String sfbsc =  CheckSfbsc()  ;//是否补充删除凭证号  1是补充  0 是自动追加

		//所属账套
		String sszt = Constant.getztid(session);
		//获取 未结账 最小 pznd kjqj 
		Map<String, Object> map = getWjzDate(sszt);
		String pznd = ""+map.get("ztnd");
		String kjqj = ""+map.get("kjqj");
		
		String maxpzbh = Validate.isNullToDefaultString(getMaxPzbh(pznd,kjqj,sszt,pzz),"0000");
		lock.lock();

		String pzbh;
		if(sfbsc.equals("1")){
			pzbh = GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzz);
		}else{
			pzbh= ""+(Integer.parseInt(maxpzbh) +1);
			pzbh =  pzlrService.autoFill(pzbh, 4, "0");
		}
		
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrzb (guid,pzbh,pzrq,fjzs,zdr,");
		if(pzzt.equals("02")) {
			sql.append(" fhr,fhrq,jzr,jzrq,");
		}
		sql.append(" jfjehj,dfjehj,pzzt,sszt,kjqj,pzly,pznd,kjzd,pzz,PZLXMC,pzscsj) values ");
		sql.append(" (?,?,to_date(?,'yyyy-mm-dd'),?,?,");
		if(pzzt.equals("02")) {
			sql.append(" '"+pd.getString("fhr")+"',sysdate,'"+pd.getString("jzr")+"',sysdate,");
		}
		sql.append(" ?,?,?,?,?,?,?,?,?,(select PZLXMC from  cw_pzlxbnew where pzlxbh='"+pzz+"'),?)");
		Object[] obj = {
				zbid,
				pzbh,
				pd.getString("pzrq"),
				pd.getString("fjzs"),
				pd.getString("zdr"),
				pd.getString("jfjehj"),
				pd.getString("dfjehj"),
				pzzt,
				Constant.getztid(session),
				session.getAttribute("kjqj")+"",
				"1",
				session.getAttribute("pznd")+"",
				CommonUtil.getKjzd(session),
				pd.getString("pzz"),
				sysTime				
		};

		int a= db.update(sql.toString(),obj);
		if(a>0){
			session.setAttribute("pzbh", pzbh);
		}
		lock.unlock();
		return a ;
	}
	/**
	 * 是否补充删除凭证号
	 * @return
	 */
	public String CheckSfbsc(){
		String sql = "select t.ms from GX_SYS_DMB t where zl='pzscsz' and dm ='01'";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 查找最大凭证号
	 * @return
	 */
	public String FindMaxPzbh(){
		String sql = "select max(pzbh) from CW_PZLRZB ";
		return db.queryForSingleValue(sql);
	}
	/**
	 * type = UPDATE  修改已保存的凭证
	 * @param zbid
	 * @param pzzt
	 * @param pd
	 * @param session
	 * @return
	 */
	public int insertPzlrZbForUpdate(String zbid,String pzzt,PageData pd,HttpSession session) {
		String pzz=pd.getString("pzz");
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer sql = new StringBuffer(); 
		
		sql.append(" update cw_pzlrzb set pzrq=to_date(?,'yyyy-mm-dd'),fjzs=?,zdr=?,");
		if(pzzt.equals("02")){
			sql.append(" fhr='"+pd.getString("fhr")+"',fhrq=sysdate,jzr='"+pd.getString("jzr")+"',jzrq=sysdate, ");
		}
		sql.append(" jfjehj=?,dfjehj=?,pzzt=?,sszt=?,kjqj=?,pzly=?,pznd=?,kjzd=?,pzz=?,pzscsj=? where guid = ? ");
				
		Object[] obj = {
				pd.getString("pzrq"),
				pd.getString("fjzs"),
				pd.getString("zdr"),
				pd.getString("jfjehj"),
				pd.getString("dfjehj"),
				pzzt,
				Constant.getztid(session),
				session.getAttribute("kjqj")+"",
				"1",
				session.getAttribute("pznd")+"",
				CommonUtil.getKjzd(session),
				pzz,
				sysTime,
				zbid
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 * @param guid 自动生成的明细表guid，会被辅助表和展示表关联
	 * @param pzbh 关联主表guid
	 * @param map 凭证明细所对应的map
	 * @param sszt 所属账套
	 * */
	public int insertPzlrMxb(String guid,String pzbh,Map<String, String> map,HttpSession session) {
		String bmbh = WindowUtil.getText(map.get("bmbh"));
		String xmbh = WindowUtil.getText(map.get("xmbh"));
		String sql = "insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,czr,czrq,sszt,kjzd,xmguid) values"
				+ "(?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,(select guid from CW_xmjbxxb where bmbh='"+bmbh+"' and xmbh='"+xmbh+"'))";
		Object[] obj = {
				guid,
				pzbh,
				map.get("zy"),
				WindowUtil.getText(map.get("kmbh")),
				WindowUtil.getText(map.get("jjfl")),
				bmbh,
				xmbh,
				map.get("jdfx"),
				map.get("jfje"),
				map.get("dfje"),
				LUser.getGuid(),
				Constant.getztid(session),
				CommonUtil.getKjzd(session)
		};
		return db.update(sql,obj);
	}
	
	/**
	 * @param guid 自动生成的明细表guid，会被辅助表和展示表关联
	 * @param pzbh 关联主表guid
	 * @param map 凭证明细所对应的map
	 * @param sszt 所属账套
	 * */
	public int insertPzlrMxb(String guid,String pzbh,Map<String, String> map,HttpSession session,int a) {
		String bmbh = WindowUtil.getText(map.get("bmbh"));
		String xmbh = WindowUtil.getText(map.get("xmbh"));
		
		String sql = "insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,czr,czrq,sszt,kjzd,indexnew,xmguid) values"
				+ "(?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,(select guid from CW_xmjbxxb where bmbh='"+bmbh+"' and xmbh='"+xmbh+"' ))";
		Object[] obj = {
				guid,
				pzbh,
				map.get("zy"),
				WindowUtil.getText(map.get("kmbh")),
				WindowUtil.getText(map.get("jjfl")),
				bmbh,
				xmbh,
				map.get("jdfx"),
				map.get("jfje"),
				map.get("dfje"),
				LUser.getGuid(),
				Constant.getztid(session),
				CommonUtil.getKjzd(session),
				a
		};
		return db.update(sql,obj);
	}
	/**
	 * @param guid 修改  会计 明细表guid，会被辅助表和展示表关联
	 * @param pzbh 关联主表guid
	 * @param map 凭证明细所对应的map
	 * @param sszt 所属账套
	 * */
	public int updatePzlrMxb(String pzbh) {
		String sql = "delete from cw_pzlrmxb where pzbh ='"+pzbh +"' ";
		return db.update(sql);
	}
	/**
	 * 插入 凭证录入 辅助表
	 * @param kmbh
	 * @param map
	 * @return
	 */
	public int insertPzlrFzb(String kmbh,Map<String, String> map) {
		String sql = "insert into cw_fzlrb (guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,czr,czrq,dqsj,gnkm,khyh,hm,zh,sksj,gwkh) values"
				+ "(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),to_date(?,'yyyy-MM-dd'),?,?,?,?,?,?)";
		String jsfs = Validate.isNullToDefaultString(map.get("jsfs"),"");
		Object[] obj = {};
		if(jsfs != "" && "0005".equals(jsfs)){
					obj = new Object[] {
					kmbh,
					map.get("wldc"),
					WindowUtil.getText(map.get("zrr")),
					WindowUtil.getText(map.get("wldw")),
					map.get("jsfs"),
					GenAutoKey.createKeyforth("cw_fzlrb","JS", "jsdh"),
					WindowUtil.getText(map.get("dfdw")),
					map.get("yslx"),
					Validate.isNullToDefault(map.get("zclx"), ""),
					map.get("ysly"),
					map.get("gwkxm"),
					LUser.getGuid(),
					DateUtil.getTime(),
					map.get("dqsj"),
					WindowUtil.getText(map.get("gnkm")),
					map.get("khyh"),
					Validate.isNullToDefault(map.get("hm"), ""),
					map.get("zh"),
					Validate.isNullToDefault(map.get("sksj"), ""),
					Validate.isNullToDefault(map.get("gwkh"), "")
			};
		}else{
			        obj = new Object[] {
					kmbh,
					map.get("wldc"),
					WindowUtil.getText(map.get("zrr")),
					WindowUtil.getText(map.get("wldw")),
					map.get("jsfs"),
					GenAutoKey.createKeyforth("cw_fzlrb","JS", "jsdh"),
					WindowUtil.getText(map.get("dfdw")),
					map.get("yslx"),
					Validate.isNullToDefault(map.get("zclx"), ""),
					map.get("ysly"),
					map.get("bz"),
					LUser.getGuid(),
					DateUtil.getTime(),
					map.get("dqsj"),
					WindowUtil.getText(map.get("gnkm")),
					map.get("khyh"),
					Validate.isNullToDefault(map.get("hm"), ""),
					map.get("zh"),
					Validate.isNullToDefault(map.get("sksj"), ""),
					Validate.isNullToDefault(map.get("gwkh"), "")
			};
		}
		return db.update(sql,obj);
	}
	/**
	 * 删除 凭证录入 辅助表
	 * @param kmbh
	 * @param map
	 * @return
	 */
	public int updatePzlrFzb(String zbguid) {
		String sql = " delete from cw_fzlrb where kmbh in (select guid from cw_pzlrmxb where pzbh =?)";
		Object[] obj = {zbguid};
		return db.update(sql,obj);
	}
	
	public void updateJe(Map<String, String> mapfz,Map<String, String> mapmx){
		String jfje = Validate.isNullToDefaultString(mapmx.get("jfje"),"0");
		String dfje = Validate.isNullToDefaultString(mapmx.get("dfje"),"0");
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		String money = jfje;
		if(Validate.isNull(jfje)||Double.parseDouble(jfje)==0){
			money = dfje;
		}
//		String update = "update CW_JSDHB t set t.je=t.je-? where jsdh=?";
//		sqlList.add(update);
		objList.add(new Object[]{money,mapfz.get("wldc")});
		String insert = "insert into CW_JSDHB(zrr,dfdw,je,jsdh)values(?,?,?,?)";
		sqlList.add(insert);
		objList.add(new Object[]{
				"undefined".equals((mapfz).get("zrr")+"")?"":WindowUtil.getText(Validate.isNullToDefaultString((mapfz).get("zrr"),"")),
				"undefined".equals((mapfz).get("dfdw")+"")?"":WindowUtil.getText(Validate.isNullToDefaultString((mapfz).get("dfdw"),"")),
				 money,(mapfz).get("jsdh")
		});
		db.batchUpdate(sqlList,objList);
	}
	/**
	 * 
	 * @param kmbh
	 * @param map
	 * @return
	 */
	public int insertPzlrzsb(String kmbh,Map<String, String> map) {
		String sql = "insert into cw_pzzsb values"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = {
				kmbh,
				map.get("kmmc"),
				map.get("zcjjflkm"),
				map.get("kmye"),
				map.get("bmmc"),
				map.get("xmmc"),
				map.get("srkm"),
				map.get("zckm"),
				map.get("xmye"),
				map.get("fzr"),
				map.get("xmgkxxm"),
				map.get("xmlx"),
				map.get("bz")
		};
		return db.update(sql,obj);
	}
	/**
	 * 删除 辅助录入信息 凭证展示表
	 * @param kmbh
	 * @param map
	 * @return
	 */
	public int updatePzlrzsb(String zbguid) {
		String sql = " delete from cw_pzzsb where guid in (select guid from cw_pzlrmxb where pzbh =?)";
		Object[] obj = {zbguid};
		return db.update(sql,obj);
	}
	/**
	 * 验证录入摘要是否重复
	 */
	public boolean selectPzlrzy(String zy) {
		String czr = LUser.getGuid();
		String sql = "select count(0) from cw_pzlrzyb where zy='"+zy+"' and czr='"+czr+"'";
		String count = db.queryForObject(sql, String.class);
		if("0".equals(count)) {
			return false;
		}
		return true;
	}
	/**
	 *添加摘要
	 * @param 
	 * @return
	 */
	public int insertPzlrzy(String zy) {
		String czr = LUser.getGuid();
		String sql="insert into cw_pzlrzyb(guid,zy,czr,czrq) values(sys_guid(),'"+zy+"','"+czr+"',sysdate)";
		return db.update(sql);
	}
	/**
	 * 查询凭证录入主表信息
	 * */
	public Map<String, Object> getPzlrMain(String pznd,String kjqj,String pzbh,String sszt,String pzlx){
		StringBuffer sql = new StringBuffer();
		 sql.append(" select (select mc from gx_sys_dmb where zl = '"+Constant.PZLY+"' and dm = a.pzly)as pzlymc ,"); 
		 sql.append(" (select mc from gx_sys_dmb where zl = '"+Constant.PZZT+"' and dm = a.pzzt) as pzzt,");
		 sql.append(" (select xm from gx_sys_ryb where guid = a.zdr) as zdr,");
		 sql.append(" (select rybh from gx_sys_ryb where guid = a.zdr) as zdrbh,");
		 sql.append(" (select xm from gx_sys_ryb where guid = a.fhr) as fhr,");
		 sql.append(" (select xm from gx_sys_ryb where guid = a.jzr) as jzr,");
		 sql.append(" (select xm from gx_sys_ryb where guid = a.cn) as cnr,"); 
		 sql.append(" guid,zdr as zdrId, pzbh,pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje" );
		 sql.append(" from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' and pzz='"+pzlx+"'" );
		return db.queryForMap(sql.toString());
	}
	public Map<String, Object> getPzlrMain(String guid){
		StringBuffer sql = new StringBuffer();
		 sql.append(" select (select mc from gx_sys_dmb where zl = '"+Constant.PZLY+"' and dm = a.pzly)as pzlymc ," );
		 sql.append(" (select mc from gx_sys_dmb where zl = '"+Constant.PZZT+"' and dm = a.pzzt) as pzzt,");
		 sql.append(" (select xm from gx_sys_ryb g left join ZC_SYS_JSRYB z on g.rybh=z.rybh where jsbh='06'and rownum<2) as cwfzr,");
		 sql.append(" (select mc from gx_sys_dwb q left join gx_sys_ryb w on q.dwbh=w.dwbh where w.guid=a.zdr) as dwmc,");
		 sql.append(" (select xm from gx_sys_ryb where guid = a.zdr) as zdr,");
		 sql.append(" (select xm from gx_sys_ryb where guid = a.fhr) as fhr,");
		 sql.append(" (select xm from gx_sys_ryb where guid = a.jzr) as jzr,");
		 sql.append(" to_char(jzrq,'yyyy-mm-dd')as jzrq,");
		 sql.append(" to_char(pzrq,'yyyy-mm-dd')as pzrq,");
		 sql.append(" (select xxmc from cw_xxxxb) as xxmc, ");
		 sql.append(" (select xm from gx_sys_ryb where guid = a.cn) as cnr, "); 
		 sql.append(" guid,zdr as zdrId, pzbh,fjzs,jfjehj as jfzje,dfjehj as dfzje, ");
		 sql.append(" a.pzlxmc   ");
		 sql.append(" from cw_pzlrzb a where a.guid = '"+guid+"'" );
		return db.queryForMap(sql.toString());
	}
	/**
	 * 获取凭证详细信息
	 * @param pznd
	 * @param kjqj
	 * @param pzbh
	 * @param sszt
	 * @return
	 */
	public List<Map<String, Object>> getPzlrDetail(String pznd,String kjqj,String pzbh,String sszt,String pzlx){
		String sql = "select a.guid,a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,b.jsdh,b.khyh,b.hm,b.zh,b.jsfs,b.zclx,"
				+ "b.wldc,b.sksj,b.gwkh,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.dfdw) as dfdw,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "(select '('||kmbh||')'||kmmc from CW_GNKMB where  KMBH = b.GNKM) as GNKM,"
				+ "to_char(b.dqsj,'yyyy-MM-dd') as dqsj,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,to_char(round(c.xmye,2),'FM99999999990.00') xmye,c.fzr,c.xmgkxxm,c.xmlx,b.bz as lrbbz,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid"
				+ " where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' and pzz='"+pzlx+"' )order by a.indexnew";
		return db.queryForList(sql);
	}
	public List<Map<String, Object>> getPzlrDetail(String guid){
		String sql = "select a.guid,a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,a.jdfx,b.jsdh,b.khyh,b.hm,b.zh,B.GWKH,B.SKSJ,b.jsfs,b.zclx,"
				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.dfdw) as dfdw,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "(select '('||kmbh||')'||kmmc from CW_GNKMB where  KMBH = b.GNKM) as GNKM,"
				+ "to_char(b.dqsj,'yyyy-MM-dd') as dqsj,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,b.bz as lrbbz,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid"
				+ " where a.pzbh = '"+guid+"' order by a.indexnew";
		return db.queryForList(sql);
	}
	public List<Map<String, Object>> getPzlrDetailByMb(String guid){
		String sql = "select a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,a.jdfx,b.jsdh,b.khyh,b.hm,b.zh,b.jsfs,b.zclx,"
				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = b.dfdw) as dfdw,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "(select '('||kmbh||')'||kmmc from CW_GNKMB where  KMBH = b.GNKM) as GNKM,"
				+ "to_char(b.dqsj,'yyyy-MM-dd') as dqsj,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
				+ " from CW_PZMBMXB  a left join cw_fzlrbbymb b on a.guid = b.kmbh left join cw_pzzsbbymb c on a.guid = c.guid"
				+ " where a.mbbh = '"+guid+"' order by a.indexnew";
		return db.queryForList(sql);
	}
	/**
	 * 查询凭证录入明细
	 * @param guid
	 * @param kjzd
	 * @return
	 */
	public List<Map<String, Object>> getPzlrMx(String guid,String kjzd){
		String sql = "select s.zy,s.kmbh,s.kmbh as rootkmbh,'('||s.kmbh||')' as rootkmbhs,s.jjfl,s.bmbh,s.xmbh,s.jfje,s.dfje,"
				+ "(select '('||j.kmbh||')'||j.kmmc from CW_JJKMB j where j.kmbh=s.jjfl) as jjflmc,"
				+ "(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=s.bmbh) as bmbhmc,"
				+ "(select '('||x.xmbh||')'||x.xmmc from cw_xmjbxxb x where x.xmbh=s.xmbh and x.bmbh=s.bmbh) as xmbhmc,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = f.dfdw) as dfdw,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb where rybh = f.zrr) as zrr,"
				+ "(select replace(wm_concat(u.kmmc),',','-') from cw_kjkmszb u where u.kjzd = '"+kjzd+"' and u.kmbh = s.kmbh or u.kmbh = substr(s.kmbh,1,6)) as kmmc"
				+ " from cw_pzlrzb t left join cw_pzlrmxb s on t.guid = s.pzbh left join cw_fzlrb f on f.kmbh=s.guid where t.guid = '"+guid+"' order by s.indexnew";
		return db.queryForList(sql);
	}
	
	/**
	 * 获取最大的凭证编号
	 * @param pznd
	 * @param kjqj
	 * @param sszt
	 * @return
	 */
	public String getMaxPzbh(String pznd,String kjqj,String sszt,String pzlx) {
		String sql = "select max(pzbh) from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' and pzz='"+pzlx+"'";
		return db.queryForSingleValue(sql);
	}
	
	
	/**
	 * 获取第一个凭证类型
	 * @param pznd
	 * @param kjqj
	 * @param sszt
	 * @return
	 */
	public String getPzlx() {
		String sql = "select pzlxbh from cw_pzlxbnew t where rownum <2 ";
		return db.queryForSingleValue(sql);
	}
	public String getPzlx(String pznd,String kjqj,String sszt) {
		String sql = "select pzlxbh from cw_pzlxbnew t where pzlxbh in (select pzz from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"') and rownum <2 ";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 获取凭证类型列表
	 * @return
	 */
	public List<Map<String, Object>> getPzlxList(){
		String sql = "select guid,pzlxbh,pzlxmc from cw_pzlxbnew ";
		return db.queryForList(sql);
	}
	
	/**
	 * 获取凭证编号列表
	 * @param pznd
	 * @param kjqj
	 * @param sszt
	 * @return
	 */
	public List<String> getPzbhList(String pznd,String kjqj,String sszt,String pzlx){
		String sql = "select pzbh from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' and pzz='"+pzlx+"' order by pzbh asc";
		List<String> list = db.queryForList(sql,String.class);
		if(list.size()==0){
			list.add("0001");
		}
		return list;
	}
	/**
	 * 获取期末结账表中没有结账的日期
	 * @param ztid
	 * @return
	 */
	public Map<String, Object> getWjzDate(String ztid){
		String sql = "select * from (select ztnd,kjqj from cw_qmjzb where sszt= '"+ztid+"' and sfjz = '0'   order by ztnd,to_number(kjqj)) where rownum <= 1";
		return db.queryForMap(sql);
	}
	
	/**
	 * 根据报销主表id查询凭证明细信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getPzListByClbx(String bxid,String sszt,String kjzd){
		String sql = "select zy,kmbh,jjfl,bmbh,xmbh,jdfx,sum(jfje) as jfje,sum(dfje) as dfje" + 
				" from(" +
				" select (select to_char(dw.mc||'_'||xm||' 报 差旅费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.sqr) as zy,(select kmbh from cw_fykmdzb where fymc = '差旅费') as kmbh,'' as jjfl,(select dwbh from gx_sys_ryb where guid = t.sqr) as bmbh,(select xmbh from cw_xmjbxxb where guid = t.xmmc) as xmbh,'1' as jdfx,null as jfje,to_number(bxzje) as dfje" + 
				" from cw_clfbxzb t where t.guid = ?" +
				" union" + 
				" select (select to_char(dw.mc||'_'||xm||' 报 差旅费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.sqr) as zy,(select kmbh from cw_zffsdzb where zffs = '01' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,cjkje as jfje,null as dfje " + 
				" from cw_clfbxzb t left join cw_cjkb r on t.guid = r.jkdh where t.sfcjk = '1' and t.guid = ?" + 
				" union" + 
				" select (select to_char(dw.mc||'_'||xm||' 报 差旅费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.sqr) as zy,(select kmbh from cw_zffsdzb where zffs = '02' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as jfje,null as dfje " + 
				" from cw_clfbxzb t left join cw_dgzfb r on t.guid = r.zfdh where t.sfdgzf = '1' and t.guid = ?" + 
				" union" + 
				" select (select to_char(dw.mc||'_'||xm||' 报 差旅费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.sqr) as zy,(select kmbh from cw_zffsdzb where zffs = '04' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,skje as jfje,null as dfje " + 
				" from cw_clfbxzb t left join cw_gwkb r on t.guid = r.skdh where t.sfgwk = '1' and t.guid = ?" +
				" union " +
				" select (select to_char(dw.mc||'_'||xm||' 报 差旅费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.sqr) as zy,(select kmbh from cw_zffsdzb where zffs = '03' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as jfje,null as dfje " +
				" from cw_clfbxzb t left join cw_dszfb r on t.guid = r.zfdh where t.sfdszf = '1' and t.guid = ?)" +
				" group by zy,kmbh,jjfl,bmbh,xmbh,jdfx";
		Object[] obj = new Object[]{
				bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid
				};
		return db.queryForList(sql,obj);
	}
	public List<Map<String, Object>> getPzListByGwjdbx(String bxid,String sszt,String kjzd){
		String sql = " select zy,kmbh,jjfl,bmbh,xmbh,jdfx,sum(jfje) as jfje,sum(dfje) as dfje" + 
				" from(" +
				" select (select to_char(dw.mc||'_'||xm||' 报 公务接待费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxryid) as zy,(select kmbh from cw_fykmdzb where fymc = '公务接待费') as kmbh,'' as jjfl,(select dwbh from gx_sys_ryb where guid = t.bxryid) as bmbh,'' as xmbh,'1' as jdfx,null as jfje,bxje as dfje" + 
				" from cw_gwjdfbxzb t where t.guid = ?"+
				" union" +
				" select (select to_char(dw.mc||'_'||xm||' 报 公务接待费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxryid) as zy,(select kmbh from cw_zffsdzb where zffs = '01' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,cjkje as jfje,null as dfje " + 
				" from cw_gwjdfbxzb t left join cw_cjkb r on t.guid = r.jkdh where t.sfcjk = '1' and t.guid = ? " + 
				" union " + 
				" select (select to_char(dw.mc||'_'||xm||' 报 公务接待费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxryid) as zy,(select kmbh from cw_zffsdzb where zffs = '02' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as jfje,null as dfje" + 
				" from cw_gwjdfbxzb t left join cw_dgzfb r on t.guid = r.zfdh where t.sfdgzf = '1' and t.guid = ? " + 
				" union " + 
				" select (select to_char(dw.mc||'_'||xm||' 报 公务接待费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxryid) as zy,(select kmbh from cw_zffsdzb where zffs = '04' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,skje as jfje,null as dfje" + 
				" from cw_gwjdfbxzb t left join cw_gwkb r on t.guid = r.skdh where t.sfgwk = '1' and t.guid = ? " +
				" union " + 
				" select (select to_char(dw.mc||'_'||xm||' 报 公务接待费报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxryid) as zy,(select kmbh from cw_zffsdzb where zffs = '03' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as jfje,null as dfje" + 
				" from cw_gwjdfbxzb t left join cw_dszfb r on t.guid = r.zfdh where t.sfdszf = '1' and t.guid = ?) " +
				" group by zy,kmbh,jjfl,bmbh,xmbh,jdfx ";
		Object[] obj = {
				bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid
		};
		return db.queryForList(sql,obj);
	}
	public List<Map<String, Object>> getPzListByRcbx(String bxid,String sszt,String kjzd){
		String sql = " select zy,kmbh,jjfl,bmbh,xmbh,jdfx,sum(jfje) as jfje,sum(dfje) as dfje" + 
				" from(" +
				" select (select to_char(dw.mc||'_'||xm||' 报 日常报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxr) as zy,p.kmbh,'' as jjfl,t.szbm as bmbh,n.xmbh,'1' as jdfx,null as jfje,u.bxje as dfje" + 
				" from cw_rcbxzb t left join cw_rcbxmxb u on t.guid = u.zbid left join cw_fykmdzb p on u.fymc = p.guid join cw_rcbxxmmxb m on t.guid = m.zbid join cw_xmjbxxb n on m.xmguid = n.guid where t.guid = ? " + 
				" union " + 
				" select (select to_char(dw.mc||'_'||xm||' 报 日常报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxr) as zy,(select kmbh from cw_zffsdzb where zffs = '01' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,cjkje as jfje,null as dfje" + 
				" from cw_rcbxzb t left join cw_cjkb r on t.guid = r.jkdh where t.sfcjk = '1' and t.guid = ? " + 
				" union " + 
				" select (select to_char(dw.mc||'_'||xm||' 报 日常报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxr) as zy,(select kmbh from cw_zffsdzb where zffs = '02' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as jfje,null as dfje" + 
				" from cw_rcbxzb t left join cw_dgzfb r on t.guid = r.zfdh where t.sfdgzf = '1' and t.guid = ? " + 
				" union " + 
				" select (select to_char(dw.mc||'_'||xm||' 报 日常报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxr) as zy,(select kmbh from cw_zffsdzb where zffs = '04' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,skje as jfje,null as dfje" + 
				" from cw_rcbxzb t left join cw_gwkb r on t.guid = r.skdh where t.sfgwk = '1' and t.guid = ? " +
				" union " + 
				" select (select to_char(dw.mc||'_'||xm||' 报 日常报销') from gx_sys_ryb ry left join gx_sys_dwb dw on ry.dwbh=dw.dwbh where ry.guid = t.bxr) as zy,(select kmbh from cw_zffsdzb where zffs = '03' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as jfje,null as dfje" + 
				" from cw_rcbxzb t left join cw_dszfb r on t.guid = r.zfdh where t.sfdszf = '1' and t.guid = ?)" +
				" group by zy,kmbh,jjfl,bmbh,xmbh,jdfx ";
		Object[] obj = {
				bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid
		};
		return db.queryForList(sql,obj);
	}
	/**
	 *  根据报销单向凭证主表，明细表插入数据
	 * @param guid
	 * @param sszt
	 * @param bxid
	 * @param pzzt
	 * @param kjqjMap
	 * @param pzzdh
	 * @return
	 */
	public int autoInsertPzzbByRcbx(String guid,String sszt,String bxid,String pzzt,Map<String, Object> kjqjMap,Map<String, Object> pzzdh,String kjzd,String pzz,String pzlxmc,String pzbh) {
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String sql = " insert into cw_pzlrzb (guid,pzbh,pzrq,fjzs,zdr,fhr,fhrq,jzr,jzrq,jfjehj,dfjehj,pzzt,sszt,kjqj,pzly,pznd,kjzd,pzz,pzlxmc,pzscsj) " + 
					" select ? as guid,? as pzbh," + 
					" to_date(?,'yyyy-mm-dd') as pzrq,fjzzs as fjzs,? as zdr,? as fhr,sysdate as fhrq,? as jzr,sysdate as jzrq,"
					+ "bxzje as jfjehj,bxzje as dfjehj,? as pzzt,? as sszt,? as kjqj,'4' as pzly,? as pznd,? as kjzd,? as pzz,? as pzlxmc,'"+sysTime+"' as pzscsj " + 
					" from cw_rcbxzb where guid = ?";
		Object[] obj = {
				guid,pzbh,
				//GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzz),
				kjqjMap.get("pzrq"),pzzdh.get("zdr"),pzzdh.get("fhr"),pzzdh.get("jzr"),
				pzzt,sszt,kjqjMap.get("kjqj"),kjqjMap.get("ztnd"),kjzd,pzz,pzlxmc,
				bxid
		};
		return db.update(sql,obj);
	}
	public int autoInsertPzzbByClbx(String guid,String sszt,String bxid,String pzzt,Map<String, Object> kjqjMap,Map<String, Object> pzzdh,String kjzd,String pzz,String pzlxmc,String pzbh) {
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrzb (guid,pzbh,pzrq,fjzs,zdr,fhr,fhrq,jzr,jzrq,jfjehj,dfjehj,pzzt,sszt,kjqj,pzly,pznd,kjzd,pzz,pzlxmc,pzscsj )");
		sql.append(" select ? as guid,? as pzbh, ");
		sql.append(	" to_date(?,'yyyy-mm-dd') as pzrq,fjzzs as fjzs,? as zdr,? as fhr,sysdate as fhrq,? as jzr,sysdate as jzrq, ");
		sql.append("bxzje as jfjehj,bxzje as dfjehj,? as pzzt,? as sszt,? as kjqj,'4' as pzly,? as pznd,? as kjzd,? as pzz,? as pzlxmc,'"+sysTime+"' as pzscsj ");
		sql.append(	" from cw_clfbxzb where guid = ? ");
		Object[] obj = {
				guid,pzbh,
				//GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzz),
				kjqjMap.get("pzrq"),pzzdh.get("zdr"),pzzdh.get("fhr"),pzzdh.get("jzr"),
				pzzt,sszt,kjqjMap.get("kjqj"),kjqjMap.get("ztnd"),kjzd,pzz,pzlxmc,
				bxid
		};
		return db.update(sql.toString(),obj);
	}
	public int autoInsertPzzbByGwjdbx(String guid,String sszt,String bxid,String pzzt,Map<String, Object> kjqjMap,Map<String, Object> pzzdh,String kjzd,String pzz,String pzlxmc,String pzbh) {
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer sql = new StringBuffer();
	    sql.append(" insert into cw_pzlrzb (guid,pzbh,pzrq,fjzs,zdr,fhr,fhrq,jzr,jzrq,jfjehj,dfjehj,pzzt,sszt,kjqj,pzly,pznd,kjzd,pzz,pzlxmc,pzscsj )" ); 
	    sql.append(" select ? as guid,? as pzbh," ); 
	    sql.append(" to_date(?,'yyyy-mm-dd') as pzrq,FYFJZS as fjzs,? as zdr,? as fhr,sysdate as fhrq,? as jzr,sysdate as jzrq," );
	    sql.append( "bxje as jfjehj,bxje as dfjehj,? as pzzt,? as sszt,? as kjqj,'4' as pzly,? as pznd,? as kjzd,? as pzz,? as pzlxmc,'"+sysTime+"' as pzscsj " );
	    sql.append(" from cw_gwjdfbxzb where guid = ?" );
		Object[] obj = {
				guid,pzbh,
				//GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzz),
				kjqjMap.get("pzrq"),pzzdh.get("zdr"),pzzdh.get("fhr"),pzzdh.get("jzr"),
				pzzt,sszt,kjqjMap.get("kjqj"),kjqjMap.get("ztnd"),kjzd,pzz,pzlxmc,
				bxid
		};
		return db.update(sql.toString(),obj);
	}
	public int autoInsertPzzbByGrsr(String guid,String sszt,String bxid,String pzzt,Map<String, Object> kjqjMap,Map<String, Object> pzzdh,String kjzd,String pzz,String pzlxmc,String pzbh) {
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrzb (guid,pzbh,pzrq,fjzs,zdr,fhr,fhrq,jzr,jzrq,jfjehj,dfjehj,pzzt,sszt,kjqj,pzly,pznd,kjzd,pzz,pzlxmc,pzscsj) " );
		sql.append(" select ? as guid,? as pzbh," );
		sql.append(" to_date(?,'yyyy-mm-dd') as pzrq,'' as fjzs,? as zdr,? as fhr,sysdate as fhrq,? as jzr,sysdate as jzrq," );
		sql.append(" (select sum(sfje) from cw_grsrmxb where fflsh=t.fflsh) as  jfjehj,(select sum(sfje) from cw_grsrmxb where fflsh=t.fflsh) as dfjehj,? as pzzt,? as sszt,? as kjqj,'4' as pzly,? as pznd,? as kjzd,? as pzz,? as pzlxmc, '"+ sysTime+"' as pzscsj "); 
		sql.append(" from cw_grsrzb t where guid = ?" );
		Object[] obj = {
				guid,pzbh,
				//GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzz),
				kjqjMap.get("pzrq"),pzzdh.get("zdr"),pzzdh.get("fhr"),pzzdh.get("jzr"),
				pzzt,sszt,kjqjMap.get("kjqj"),kjqjMap.get("ztnd"),kjzd,pzz,pzlxmc,
				bxid
		};
		return db.update(sql.toString(),obj);
	}
	/**借款报销凭证生成  插入凭证录入主表
	 * 
	 * @param guid  =  pzzbid  checkbox的guid 也可能是生成的 ！！其实不可能 一定有值
	 * @param sszt   账套id
	 * @param bxid   checkbox的guid 
	 * @param pzzt   pzzt ='00'已保存  或  '02' 未保存
	 * @param kjqjMap  带有 年 月 日  ztnd=n ztnd=y  pzrq=d
	 * @param pzzdh  自动化信息 （三种人）
	 * @param kjzd   会计制度
	 * @param pzz     凭证类型  pzz = 01
	 * @param pzlxmc   凭证类型  pzlxmc = 记
	 * @return
	 */
	public int autoInsertPzzbByJkbx(String guid,String sszt,String bxid,String pzzt,Map<String, Object> kjqjMap,Map<String, Object> pzzdh,String kjzd,String pzz,String pzlxmc,String pzbh) {
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrzb (guid,pzbh,pzrq,fjzs,zdr,fhr,fhrq,jzr,jzrq,jfjehj,dfjehj,pzzt,sszt,kjqj,pzly,pznd,kjzd,pzz,pzlxmc,pzscsj) ");
		sql.append(	" select ? as guid,? as pzbh," );
		sql.append(" to_date(?,'yyyy-mm-dd') as pzrq,FJZS as fjzs,? as zdr,? as fhr,sysdate as fhrq,? as jzr,sysdate as jzrq,");
		sql.append(" jkzje as jfjehj,jkzje as dfjehj,? as pzzt,? as sszt,? as kjqj,'4' as pzly,? as pznd,? as kjzd,? as pzz,? as pzlxmc, '"+sysTime+"' as pzscsj" ); 
		sql.append(" from cw_jkywb where gid = ?");
		Object[] obj = {
				guid,pzbh,
				//GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzz),
				kjqjMap.get("pzrq"),pzzdh.get("zdr"),pzzdh.get("fhr"),pzzdh.get("jzr"),
				pzzt,sszt,kjqjMap.get("kjqj"),kjqjMap.get("ztnd"),kjzd,pzz,pzlxmc,
				bxid
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 * 管理费生成凭证，主表数据
	 * @author BiMing
	 * @Time 2018年10月13日下午3:00:30
	 * @param guid  =  pzzbid  checkbox的guid 也可能是生成的 ！！其实不可能 一定有值
	 * @param sszt   账套id
	 * @param bxid   checkbox的guid 
	 * @param pzzt   pzzt ='00'已保存  或  '02' 未保存
	 * @param kjqjMap  带有 年 月 日  ztnd=n ztnd=y  pzrq=d
	 * @param pzzdh  自动化信息 （三种人）,制单,复核,记账。
	 * @param kjzd   会计制度
	 * @param pzz     凭证类型  pzz = 01
	 * @param pzlxmc   凭证类型  pzlxmc = 记
	 * @return
	 */
	public int autoInsertPzzbByGlf(String guid,String sszt,String bxid,String pzzt,Map<String, Object> kjqjMap,Map<String, Object> pzzdh,String kjzd,String pzz,String pzlxmc,String pzbh) {
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrzb (guid,pzbh,pzrq,fjzs,zdr,fhr,fhrq,jzr,jzrq,jfjehj,dfjehj,pzzt,sszt,kjqj,pzly,pznd,kjzd,pzz,pzlxmc,pzscsj) ");
		sql.append(	" select ? as guid,? as pzbh," );
		sql.append(" to_date(?,'yyyy-mm-dd') as pzrq,'' as fjzs,? as zdr,? as fhr,sysdate as fhrq,? as jzr,sysdate as jzrq,");
		sql.append(" xxjj+xyjj as jfjehj,xxjj+xyjj as dfjehj,? as pzzt,? as sszt,? as kjqj,'4' as pzly,? as pznd,? as kjzd,'01' as pzz,'记' as pzlxmc, '"+sysTime+"' as pzscsj" ); 
		sql.append(" from cw_kyglf where guid = ?");
		Object[] obj = {
				guid,pzbh,
				//GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzz),
				kjqjMap.get("pzrq"),pzzdh.get("zdr"),pzzdh.get("fhr"),pzzdh.get("jzr"),
				pzzt,sszt,kjqjMap.get("kjqj"),kjqjMap.get("ztnd"),kjzd,
//				pzz,pzlxmc,
				bxid
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 * 薪资
	 * @param map
	 * @return
	 */
	public int autoInsertPzzbByXz(Map map) {
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer sql = new StringBuffer();
		 sql.append(" insert into cw_pzlrzb (guid,pzbh,pzrq,fjzs,zdr,fhr,fhrq,jzr,jzrq,jfjehj,dfjehj,pzzt,sszt,kjqj,pzly,pznd,kjzd,pzz,pzlxmc,gzyf,rylx,pzscsj) " ); 
		 sql.append(" select ? as guid,? as pzbh," ); 
		 sql.append(" to_date(?,'yyyy-mm-dd') as pzrq,'2' as fjzs,? as zdr,'' as fhr,null as fhrq,'' as jzr,null as jzrq,"); 
		 sql.append("(select sum(nvl(jfje,0)) from cw_pzlrmxb where pzbh=?) as jfjehj,(select sum(nvl(dfje,0)) from cw_pzlrmxb where pzbh=?) as dfjehj,"); 
		 sql.append("'00' as pzzt,? as sszt,? as kjqj,'1' as pzly,? as pznd,? as kjzd,? as pzz,? as pzlxmc,? as gzyf,? as rylx, '"+sysTime+"' as pzscsj " ); 
		 sql.append(" from dual"); 
		Object[] obj = {
				map.get("pzzbid"),map.get("pzbh"),
				//GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzz),
				map.get("pzrq"),LUser.getGuid(),
				map.get("pzzbid"),map.get("pzzbid"),
				map.get("sszt"),map.get("kjqj"),map.get("ztnd"),map.get("kjzd"),map.get("pzlxbh"),map.get("pzlxmc"),map.get("gzyf"),map.get("rylx")
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 * 在职人员转账
	 * @param map
	 * @return
	 */
	public int autoInsertPzmxByXz1(Map map) {
		StringBuffer sql = new StringBuffer();
		 sql.append(" insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sszt,kjzd,indexnew,xmguid) " );
		 sql.append(" select sys_guid(),? pzbh,('计提'||to_number(to_char(to_date(?,'yyyy.mm'),'mm'))||'月工资')zy,kjkm,jjkm," );
				//+ "'' bmbh,'' xmbh,"
		 sql.append(" ( select to_char(ms) from gx_sys_dmb where zl = 'xzbmbh' and dm ='01') as bmbh, ");
		 sql.append("  ( select to_char(ms)from gx_sys_dmb where zl = 'xzxmbh' and dm ='01') as xmbh,");
		 sql.append(" k.yefx as jdfx,t.jfje,null as dfje,? as sszt,? as kjzd,kjkm||xh, " );
		 sql.append(" ( select t.guid from CW_xmjbxxb t where t.bmbh=( select to_char(ms) from gx_sys_dmb where zl = 'xzbmbh' and dm ='01') and t.xmbh=( select to_char(ms)from gx_sys_dmb where zl = 'xzxmbh' and dm ='01') ) as xmguid ");
		 sql.append(" from(" );
		 sql.append(" select 1 as xh,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010101' as jjkm,sum(nvl(gwgz,0)) as jfje " );
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz " );
		 sql.append(" union all  " ); 
		 sql.append(" select 2 as xh,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010102' as jjkm,sum(nvl(xjgz,0)) as jfje ");
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz " );
		 sql.append(" union all  " ); 
		 sql.append(" select 3 as xh,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010201' as jjkm,sum(nvl(xzft,0)) as jfje " );
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz " );
		 sql.append(" union all  " ); 
		 sql.append(" select 4 as xh,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010203' as jjkm,sum(nvl(wybt,0)) as jfje " );
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz " );
		 sql.append(" union all  " );
		 sql.append(" select 5 as xh,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3019907' as jjkm,sum(nvl(dszf ,0)+nvl(bsbt ,0)+nvl(gwbt ,0)+nvl(bgz ,0)) as jfje " ); 
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz " );
		 sql.append(" union all  " );
		 sql.append(" select 6 as xh,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010701' as jjkm,sum(nvl(jcjx ,0)) as jfje " ); 
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz" );
		 sql.append(" union all  " );
		 sql.append(" select 7 as xh,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010702' as jjkm,sum(nvl(jljx1 ,0)) as jfje " ); 
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz");
		 sql.append(" union all  " ); 
		 sql.append(" select 8 as xh,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010704' as jjkm,sum(nvl(jljx1 ,0)/0.7*0.3) as jfje ");
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz" );
		 sql.append(" ) t left join Cw_kjkmszb k on k.kmbh=t.kjkm left join cw_jjkmb j on j.kmbh=t.jjkm " );
		 sql.append(" union all  " ); 
		 sql.append(" select sys_guid(),? pzbh,('计提'||to_number(to_char(to_date(?,'yyyy.mm'),'mm'))||'月工资')zy,kjkm,'' as jjkm," );
		 sql.append(" '' bmbh,'' xmbh,k.yefx as jdfx,null as jfje,dfje as dfje,? as sszt,? as kjzd,'600000'||kjkm ,'' as xmguid " ); 
		 sql.append(" from(" );
		 sql.append(" select (case d.dwxz when '1' then '220101' when '2' then '220102' when '3' then '220103' when '4' then '220104' when '5' then '220105' else '220106'end ) as kjkm,sum(nvl(gwgz,0)+nvl(xjgz,0)+nvl(xzft,0)+nvl(wybt,0)+nvl(dszf ,0)+nvl(bsbt ,0)+nvl(gwbt ,0)+nvl(bgz ,0)+nvl(jcjx ,0)+nvl(jljx1 ,0)+nvl(jljx1 ,0)/0.7*0.3) as dfje " ); 
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz" );
		 sql.append(" )t left join Cw_kjkmszb k on k.kmbh=t.kjkm ");
		Object[] obj = {
				map.get("pzzbid"),map.get("gzyf"),map.get("sszt"),map.get("kjzd"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("pzzbid"),map.get("gzyf"),map.get("sszt"),map.get("kjzd"),
				map.get("gzyf")
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 * 在职人员付账
	 * @param map
	 * @return
	 */
	public int autoInsertPzmxByXz2(Map map) {
		String sqlxm = "select to_char(ms)from gx_sys_dmb where zl = 'xzxmbh' and dm ='01'";
		String sqlbm = " select to_char(ms) from gx_sys_dmb where zl = 'xzbmbh' and dm ='01'";
		String xmbh = db.queryForSingleValue(sqlxm);
		String bmbh = db.queryForSingleValue(sqlbm);
		StringBuffer sql = new StringBuffer();
		 sql.append(" insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sszt,kjzd,indexnew,xmguid) " );
		 sql.append(" select sys_guid(),? pzbh,('付'||to_number(to_char(to_date(?,'yyyy.mm'),'mm'))||'月份'||to_char(j.kmmc)||'--'||to_char(d.mc))zy,kjkm,jjkm," );
				//+ "'' bmbh,'' xmbh,"
		 sql.append(" ( select to_char(ms) from gx_sys_dmb where zl = 'xzbmbh' and dm ='01') as bmbh, " );
		 sql.append( "  ( select to_char(ms)from gx_sys_dmb where zl = 'xzxmbh' and dm ='01') as xmbh,");
		 sql.append( "k.yefx as jdfx,t.jfje,null as dfje,? as sszt,? as kjzd,kjkm||xh ," );
		 sql.append(" (select t.guid from CW_xmjbxxb t where t.bmbh='"+bmbh+"' and t.xmbh='"+xmbh+"' ) as xmguid ");
		 sql.append(" from(" );
		 sql.append(" select 1 as xh,d.dwxz,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010101' as jjkm,sum(nvl(gwgz,0)) as jfje " ); 
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz " );
		 sql.append(" union all  " );
		 sql.append(" select 2 as xh,d.dwxz,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010102' as jjkm,sum(nvl(xjgz,0)) as jfje " );
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz " ); 
		 sql.append(" union all  " ); 
		 sql.append(" select 3 as xh,d.dwxz,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010201' as jjkm,sum(nvl(xzft,0)) as jfje " );
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz " ); 
		 sql.append(" union all  " );
		 sql.append(" select 4 as xh,d.dwxz,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010203' as jjkm,sum(nvl(wybt,0)) as jfje " );
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz " );
		 sql.append(" union all  ");
		 sql.append(" select 5 as xh,d.dwxz,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3019907' as jjkm,sum(nvl(dszf ,0)+nvl(bsbt ,0)+nvl(gwbt ,0)+nvl(bgz ,0)) as jfje " ); 
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz ");
		 sql.append(" union all  " );
		 sql.append(" select 6 as xh,d.dwxz,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010701' as jjkm,sum(nvl(jcjx ,0)) as jfje " ); 
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz" );
		 sql.append(" union all  " );
		 sql.append(" select 7 as xh,d.dwxz,(case d.dwxz when '1' then '500101' when '2' then '500201' when '3' then '500301' when '4' then '500401' when '5' then '500501' else '540101'end ) as kjkm,'3010702' as jjkm,sum(nvl(jljx1 ,0)) as jfje " );
		 sql.append(" from cw_xzb t left join gx_sys_dwb d on d.dwbh=t.dwbh where gzyf=? and shzt='2' group by d.dwxz" );
		 sql.append(" ) t left join Cw_kjkmszb k on k.kmbh=t.kjkm left join cw_jjkmb j on j.kmbh=t.jjkm left join gx_sys_dmb d on t.dwxz=d.dm and d.zl=? " );
		 
		 sql.append(" union all  " ); 
		 sql.append(" select sys_guid(),? pzbh,zy,kjkm,'' as jjkm," );
		 sql.append(" bmbh, xmbh,");
		 sql.append(" k.yefx as jdfx,null as jfje,dfje as dfje,? as sszt,? as kjzd,'600000'||kjkm ,xmguid " );
		 sql.append(" from(" );
		 sql.append(" select 1 as xh,'扣住房公积金' as zy,'230515' as kjkm,'30113' as jjkm,'' bmbh,'' xmbh,sum(nvl(zfjj,0)) as dfje,'' as xmguid from cw_xzb where gzyf=? and shzt='2' " ); 
		 sql.append(" union all " );
		 sql.append(" select 2 as xh,'扣医疗保险' as zy,'500101' as kjkm,'30110' as jjkm,'"+bmbh+"' as bmbh,'"+xmbh+"' as xmbh,sum(nvl(ylbx,0)) as dfje,(select t.guid from CW_xmjbxxb t where t.bmbh='"+bmbh+"' and t.xmbh='"+xmbh+"' ) as xmguid from cw_xzb where gzyf=? and shzt='2' " ); 
		 sql.append(" union all " );
		 sql.append(" select 3 as xh,'扣代扣税' as zy,'210106' as kjkm,'' as jjkm,'' bmbh,'' xmbh,sum(nvl(dks,0)) as dfje,'' as xmguid  from cw_xzb where gzyf=? and shzt='2' " ); 
		 sql.append(" union all " );
		 sql.append(" select 4 as xh,'扣失业金' as zy,'500101' as kjkm,'30108' as jjkm,'"+bmbh+"' as bmbh,'"+xmbh+"' as xmbh,sum(nvl(syj,0)) as dfje,(select t.guid from CW_xmjbxxb t where t.bmbh='"+bmbh+"' and t.xmbh='"+xmbh+"' ) as xmguid from cw_xzb where gzyf=? and shzt='2' " );
		 sql.append(" union all " );
		 sql.append(" select 5 as xh,'扣社保金' as zy,'500101' as kjkm,'30108' as jjkm,'"+bmbh+"' as bmbh,'"+xmbh+"' as xmbh,sum(nvl(sbj,0)) as dfje,(select t.guid from CW_xmjbxxb t where t.bmbh='"+bmbh+"' and t.xmbh='"+xmbh+"' ) as xmguid from cw_xzb where gzyf=? and shzt='2' ");
		 sql.append(" union all ");
		 sql.append(" select 6 as xh,'扣养老金' as zy,'500101' as kjkm,'30108' as jjkm,'"+bmbh+"' as bmbh,'"+xmbh+"' as xmbh,sum(nvl(ylj,0)) as dfje,(select t.guid from CW_xmjbxxb t where t.bmbh='"+bmbh+"' and t.xmbh='"+xmbh+"' ) as xmguid from cw_xzb where gzyf=? and shzt='2' "); 
		 sql.append(" union all " );
		 sql.append(" select 7 as xh,'扣房租' as zy,'230509' as kjkm,'' as jjkm,'' bmbh,'' xmbh,sum(nvl(fz,0)) as dfje ,'' as xmguid from cw_xzb where gzyf=? and shzt='2' " );
		 sql.append(" )t left join Cw_kjkmszb k on k.kmbh=t.kjkm " );
		 
		 sql.append(" union all  " );
		 sql.append(" select sys_guid(),? pzbh,('付'||to_number(to_char(to_date(?,'yyyy.mm'),'mm'))||'月份工资')zy,kjkm,'' as jjkm," );
		 sql.append("'' bmbh,'' xmbh," );
		 sql.append( "k.yefx as jdfx,null as jfje,dfje as dfje,? as sszt,? as kjzd,'700000'||kjkm ,'' as xmguid " ); 
		 sql.append(" from(" );
		 sql.append(" select 1 as xh,'1011' as kjkm,'' as jjkm, sum(nvl(gwgz,0)+nvl(xjgz,0)+nvl(xzft,0)+nvl(wybt,0)+nvl(dszf ,0)+nvl(bsbt ,0)+nvl(gwbt ,0)+nvl(bgz ,0)+nvl(jcjx ,0)+nvl(jljx1 ,0))-sum(nvl(zfjj,0)+nvl(ylbx,0)+nvl(dks,0)+nvl(syj,0)+nvl(sbj,0)+nvl(ylj,0)+nvl(fz,0)) as dfje " );
		 sql.append(" from cw_xzb where gzyf=? and shzt='2' " );
		 sql.append(" )t left join Cw_kjkmszb k on k.kmbh=t.kjkm " );
		Object[] obj = {
				map.get("pzzbid"),map.get("gzyf"),map.get("sszt"),map.get("kjzd"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				Constant.DWXZ,
				map.get("pzzbid"),map.get("sszt"),map.get("kjzd"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("pzzbid"),map.get("gzyf"),map.get("sszt"),map.get("kjzd"),
				map.get("gzyf")
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 * 
	 * @author 作者：BiMing
	 * @version 创建时间:2018-5-9上午10:38:39
	 */
	public int autoInsertPzmxByXz3(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sszt,kjzd,indexnew,xmguid) " );
		sql.append("(select * from (" );
		sql.append(" select sys_guid(),? pzbh,('付'||to_number(to_char(to_date(?,'yyyy.mm'),'mm'))||'月份'||ry||'工资')zy,kjkm,jjkm,");
		sql.append(" ( select to_char(ms) from gx_sys_dmb where zl = 'xzbmbh' and dm ='01') as bmbh, ");
		sql.append( "  ( select to_char(ms)from gx_sys_dmb where zl = 'xzxmbh' and dm ='01') as xmbh,");
		sql.append( "k.yefx as jdfx,t.jfje,null as dfje,? as sszt,? as kjzd,kjkm||xh, " );
		sql.append(" ( select t.guid from CW_xmjbxxb t where t.bmbh=( select to_char(ms) from gx_sys_dmb where zl = 'xzbmbh' and dm ='01') and t.xmbh=( select to_char(ms)from gx_sys_dmb where zl = 'xzxmbh' and dm ='01') ) as xmguid ");
		sql.append(" from(" );
		sql.append(" select 1 as xh,'离休人员' as ry,'500501' as kjkm,'3030101' as jjkm,sum(nvl(yfhj,0)) as jfje,null as dfje from cw_lzxzb t left join cw_jzgxxb r on r.xh=t.rybh where gzyf=? and r.zzjzglx='5' and shzt='2' " );
		sql.append(" union all  " );
		sql.append(" select 2 as xh,'退休人员' as ry,'500501' as kjkm,'3030208' as jjkm,sum(nvl(yfhj,0)) as jfje,null as dfje from cw_lzxzb t left join cw_jzgxxb r on r.xh=t.rybh where gzyf=? and r.zzjzglx='4' and shzt='2' ");
		sql.append(" union all  ");
		sql.append(" select 3 as xh,'遗属' as ry,'500501' as kjkm,'30310' as jjkm,sum(nvl(yfhj,0)) as jfje,null as dfje from cw_lzxzb t left join cw_jzgxxb r on r.xh=t.rybh where gzyf=? and r.zzjzglx='3' and shzt='2' " );
		sql.append(" union all  " );
		sql.append(" select 4 as xh,'临时工' as ry,'500301' as kjkm,'3019908' as jjkm,sum(nvl(yfhj,0)) as jfje,null as dfje from cw_lzxzb t left join cw_jzgxxb r on r.xh=t.rybh where gzyf=? and r.zzjzglx='2' and shzt='2' "); 
		sql.append(" ) t left join Cw_kjkmszb k on k.kmbh=t.kjkm left join cw_jjkmb j on j.kmbh=t.jjkm " );
		
		sql.append(" union all  " ); 
		sql.append(" select sys_guid(),? pzbh,zy,kjkm,'' as jjkm,");
		sql.append(" ( select to_char(ms) from gx_sys_dmb where zl = 'xzbmbh' and dm ='01') as bmbh, ");
		sql.append("  ( select to_char(ms)from gx_sys_dmb where zl = 'xzxmbh' and dm ='01') as xmbh,");
		sql.append("k.yefx as jdfx,null as jfje,dfje as dfje,? as sszt,? as kjzd,'600000'||kjkm, ");
		sql.append(" ( select t.guid from CW_xmjbxxb t where t.bmbh=( select to_char(ms) from gx_sys_dmb where zl = 'xzbmbh' and dm ='01') and t.xmbh=( select to_char(ms)from gx_sys_dmb where zl = 'xzxmbh' and dm ='01') ) as xmguid ");
		sql.append(" from(" );
		sql.append(" select 5 as xh,'扣社保金' as zy,'500501' as kjkm,'30108' as jjkm,null as jfje,sum(nvl(sbj,0))  as dfje from cw_lzxzb t left join cw_jzgxxb r on r.xh=t.rybh where gzyf=? and shzt='2' and r.zzjzglx in ('2','3','4','5') " ); 
		sql.append(" union all " );
		sql.append(" select 6 as xh,'扣暖气费' as zy,'500501' as kjkm,'30108' as jjkm,null as jfje,sum(nvl(nqf ,0) + nvl(nqf1 ,0))  as dfje from cw_lzxzb t left join cw_jzgxxb r on r.xh=t.rybh where gzyf=? and shzt='2'  and r.zzjzglx in ('2','3','4','5') " );
		sql.append(" union all " );
		sql.append(" select 7 as xh,'扣物业费' as zy,'500501' as kjkm,'30108' as jjkm,null as jfje,sum(nvl(wyf ,0))  as dfje from cw_lzxzb t left join cw_jzgxxb r on r.xh=t.rybh where gzyf=? and shzt='2'  and r.zzjzglx in ('2','3','4','5') " ); 
		sql.append(" union all " );
		sql.append(" select 8 as xh,'扣零星扣款' as zy,'500501' as kjkm,'3030101' as jjkm,null as jfje,sum(nvl(kkhj ,0)-nvl(sbj ,0)-nvl(nqf ,0)-nvl(nqf1 ,0)-nvl(wyf ,0)) as dfje from cw_lzxzb t left join cw_jzgxxb r on r.xh=t.rybh where gzyf=? and shzt='2' and r.zzjzglx in ('2','3','4','5') " ); 
		sql.append(" )t left join Cw_kjkmszb k on k.kmbh=t.kjkm ");
		
		sql.append(" union all  " ); 
		sql.append(" select sys_guid(),? pzbh,('付'||to_number(to_char(to_date(?,'yyyy.mm'),'mm'))||'月份离退休工资')zy,kjkm,'' as jjkm,'' bmbh,'' xmbh,k.yefx as jdfx,null as jfje,dfje as dfje,? as sszt,? as kjzd,'700000'||kjkm,'' as xmguid " ); 
		sql.append(" from(" );
		sql.append(" select 9 as xh,'1011' as kjkm,'' as jjkm,null as jfje,sum(nvl(yfhj,0)-nvl(kkhj ,0)) as dfje from cw_lzxzb t left join cw_jzgxxb r on r.xh=t.rybh where gzyf=? and shzt='2' and r.zzjzglx in ('2','3','4','5') " ); 
		sql.append(" )t left join Cw_kjkmszb k on k.kmbh=t.kjkm " );
		sql.append(" )T  where nvl(dfje,0) != 0 or nvl(jfje,0)!=0 ) " );
		Object[] obj = {
				map.get("pzzbid"),map.get("gzyf"),map.get("sszt"),map.get("kjzd"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("pzzbid"),map.get("sszt"),map.get("kjzd"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("gzyf"),
				map.get("pzzbid"),map.get("gzyf"),map.get("sszt"),map.get("kjzd"),
				map.get("gzyf")
		};
		return db.update(sql.toString(),obj);
	}
//////改zy	
	public int autoInsertPzmxByRcbx(String pzzbid,String sszt,String bxid,String kjzd) {
		StringBuffer sql = new StringBuffer(); 
		sql.append(" insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sszt,kjzd,indexnew,xmguid) " );
		sql.append(" select nvl(guid,sys_guid()),? pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,? as sszt,? as kjzd,xh,xmguid " ); 
		sql.append(" from(" );
		sql.append(" select 0 as xh,'' as guid,(select (ry.xm||'报'||bxsy)AS bxsy  from cw_rcbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.bxr WHERE b.guid=?)as zy,s.srkmbh as  kmbh,u.fymc as jjfl,n.bmbh as bmbh,n.xmbh,'0' as jdfx,u.bxje as jfje,null as dfje," );   
		sql.append(" ( select t.guid from CW_xmjbxxb t where t.bmbh=n.bmbh and t.xmbh=n.xmbh ) as xmguid  " );  
		sql.append(" from cw_rcbxzb t left join cw_rcbxmxb u on t.guid = u.zbid left join cw_jjkmb p on u.fymc = p.kmbh left join cw_xmjbxxb n on u.xmguid = n.guid left join cw_xmsrbnew s on s.xmxxbh=n.guid  AND P.YSLX=S.YSLX where t.guid = ? " ); 
		sql.append(" union all  " );  
		sql.append(" select 1 as xh,r.guid,(select (ry.xm||'报'||bxsy)AS bxsy  from cw_rcbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.bxr WHERE b.guid=?)as zy,(select kmbh from cw_zffsdzb where zffs = '01' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,cjkje as dfje,'' as xmguid " ); 
		sql.append(" from cw_rcbxzb t left join cw_cjkb r on t.guid = r.jkdh where t.sfcjk = '1' and t.guid = ? " ); 
		sql.append(" union all  " ); 
		sql.append(" select 1 as xh,r.guid,(select (ry.xm||'报'||bxsy)AS bxsy  from cw_rcbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.bxr WHERE b.guid=?)as zy,(select kmbh from cw_zffsdzb where zffs = '02' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,je as dfje,'' as xmguid " );  
		sql.append(" from cw_rcbxzb t left join cw_dgzfb r on t.guid = r.zfdh where t.sfdgzf = '1' and t.guid = ? "  ); 
		sql.append(" union all  "  ); 
		sql.append(" select 1 as xh,r.guid,(select (ry.xm||'报'||bxsy)AS bxsy  from cw_rcbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.bxr WHERE b.guid=?)as zy,(select kmbh from cw_zffsdzb where zffs = '04' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,skje as dfje,'' as xmguid " );  
		sql.append(" from cw_rcbxzb t left join cw_gwkb r on t.guid = r.skdh where t.sfgwk = '1' and t.guid = ? "  );
		sql.append(" union all  "  ); 
		sql.append(" select 1 as xh,r.guid,(select (ry.xm||'报'||bxsy)AS bxsy  from cw_rcbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.bxr WHERE b.guid=?)as zy,(select kmbh from cw_zffsdzb where zffs = '03' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,je as dfje,'' as xmguid " ); 
		sql.append(" from cw_rcbxzb t left join cw_dszfb r on t.guid = r.zfdh where t.sfdszf = '1' and t.guid = ?" );
		sql.append(" union all  "  ); 
		sql.append(" select 1 as xh,r.guid,(select (ry.xm||'报'||bxsy)AS bxsy  from cw_rcbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.bxr WHERE b.guid=?)as zy,'1011' as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,je as dfje,'' as xmguid " ); 
		sql.append(" from cw_rcbxzb t left join cw_lyeb r on t.guid = r.bxid where t.sflye = '1' and t.guid = ?)" );
				
		sql.append(" where nvl(jfje,0)<>0 or nvl(dfje,0)<>0 " );
		Object[] obj = {
				pzzbid,sszt,kjzd,
				bxid,bxid,
				bxid,sszt,kjzd,bxid,
				bxid,sszt,kjzd,bxid,
				bxid,sszt,kjzd,bxid,
				bxid,sszt,kjzd,bxid,
				bxid,bxid
		};
		return db.update(sql.toString(),obj);
	}
	public int autoInsertFzlrByRcbx(String pzzbid,String sszt,String bxid,String kjzd) {
		StringBuffer sql = new StringBuffer();
		sql.append( "insert into CW_PZLRYHMX(guid,zbid,mxid,status,xm,yhzh,yhmc,je,yhlhh) " );
		sql.append("select sys_guid() as guid,zbid,mxid,status,xm,yhzh,yhmc,je,yhlhh from (" );
		sql.append( "select z.guid as zbid,t.guid as mxid,'0' as status,(select d.dwmc from cw_wldwb d where d.wlbh=r.dfdw ) as xm,r.dfzh as yhzh,(select y.khyh from cw_wldwmxb y where 1=1 and y.guid=r.dfyh) as yhmc,r.je, " );
		sql.append(" (select y.yhlhh from cw_wldwmxb y where 1=1 and y.guid=r.dfyh)yhlhh ");
		sql.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_rcbxzb c on c.guid=d.bxid " ); 
		sql.append(" left join cw_dgzfb r on c.guid = r.zfdh where c.sfdgzf = '1' and c.guid = ?  and t.guid=r.guid" ); 
		sql.append(" union all  " );
		sql.append("select z.guid as zbid,t.guid as mxid,'0' as status,(select to_char(xm) from gx_sys_ryb ry where ry.rybh=r.Ryxm) as xm,r.dfzh as yhzh,r.KLX as yhmc,r.je," );
		sql.append(" (select y.yhlhh from cw_jsyhzhb y where 1=1 and y.guid=r.ZHBGUID)yhlhh ");
		sql.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_rcbxzb c on c.guid=d.bxid " );
		sql.append(" left join cw_dszfb r on c.guid = r.zfdh where c.sfdszf = '1' and c.guid = ?  and t.guid=r.guid" );
		sql.append(" union all  " );
		sql.append("select z.guid as zbid,t.guid as mxid,'0' as status,r.hm as xm,r.yhkh as yhzh,r.yh as yhmc,r.je," );
		sql.append(" '' yhlhh ");
		sql.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_rcbxzb c on c.guid=d.bxid " );
		sql.append(" left join cw_lyeb r on c.guid = r.bxid where c.sflye = '1' and c.guid = ?  and t.guid=r.guid" );
		sql.append(" ) " );
		Object[] obj = {
				bxid,bxid,bxid
		};
		StringBuffer sql1 = new StringBuffer();
		sql1.append("insert into cw_fzlrb(guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,czr,czrq,dqsj,gnkm,khyh,hm,zh,sksj,gwkh,sjskje) " );
		sql1.append(" select sys_guid() as guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,? as czr,sysdate as czrq,dqsj,gnkm,khyh,hm,zh,sksj,gwkh,sjskje" ); 
		sql1.append(" from("); 
		sql1.append(" select t.guid as kmbh,r.jkid as wldc,(select rybh from gx_sys_ryb where guid =r.jkr) as zrr,'' as wldw,'0004' as jsfs,j.djbh as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,'' as khyh,'' as hm,'' as zh,'' as sksj,'' as gwkh,'' as sjskje  " );
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_rcbxzb c on c.guid=d.bxid " ); 
		sql1.append(" left join cw_cjkb r on c.guid = r.jkdh left join cw_jkywb j on j.gid=r.jkid where c.sfcjk = '1' and c.guid = ?  and t.guid=r.guid" ); 
		sql1.append(" union all  " );
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0002' as jsfs,'' as jsdh,r.dfdw as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,r.dfyh as khyh,'' as hm,r.dfzh as zh,'' as sksj,'' as gwkh,'' as sjskje  " );
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_rcbxzb c on c.guid=d.bxid " ); 
		sql1.append(" left join cw_dgzfb r on c.guid = r.zfdh where c.sfdgzf = '1' and c.guid = ?  and t.guid=r.guid" );
		sql1.append(" union all  " );
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0005' as jsfs,'' as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,(select a.xm from gx_sys_ryb a where a.rybh=r.ryxm) as bz,'' as  dqsj,'' as gnkm,'' as khyh,'' as hm,r.kh as zh, to_char(r.skrq, 'yyyy-mm-dd') as sksj,r.kh as gwkh,to_char(r.sjskje) as sjskje "); 
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_rcbxzb c on c.guid=d.bxid " );
		sql1.append(" left join cw_gwkb r on c.guid = r.skdh where c.sfgwk = '1' and c.guid = ?  and t.guid=r.guid" ); 
		sql1.append(" union all  " );
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0003' as jsfs,'' as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,r.zhbguid as khyh,r.ryxm as hm,r.dfzh as zh,'' as sksj,'' as gwkh ,'' as sjskje " );
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_rcbxzb c on c.guid=d.bxid " );
		sql1.append(" left join cw_dszfb r on c.guid = r.zfdh where c.sfdszf = '1' and c.guid = ?  and t.guid=r.guid"); 
		sql1.append(" union all  " );
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0006' as jsfs,'' as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,r.yh as khyh,r.hm as hm,r.yhkh as zh,'' as sksj,'' as gwkh ,'' as sjskje " );
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_rcbxzb c on c.guid=d.bxid " );
		sql1.append(" left join cw_lyeb r on c.guid = r.bxid where c.sflye = '1' and c.guid = ?  and t.guid=r.guid"); 
		sql1.append(" ) ");
		Object[] obj1 = {
				LUser.getRybh(),bxid,bxid,bxid,bxid,bxid
		};
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		sqlList.add(sql1.toString());
		sqlList.add(sql.toString());
		objList.add(obj1);
		objList.add(obj);
		boolean bool = db.batchUpdate(sqlList,objList);
		int m = 0;
		if(bool){
			m = 100;
		}
		return m;
	}
	public int autoInsertPzmxByClbx(String pzzbid,String sszt,String bxid,String kjzd) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sszt,kjzd,indexnew,xmguid) " );
		sql.append(" select nvl(guid,sys_guid()) as guid,? as pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,? as sszt,? as kjzd,xh,xmguid"); 
		sql.append(" from(" ); 
		sql.append(" select 0 as xh,'' as guid,(select (ry.xm||'报'||ccsy)AS cc  from cw_clfbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.sqr where b.guid=?) as zy,(select x.srkmbh from cw_xmsrbnew x where s.jfbh=x.xmxxbh and yslx=( select yslx from cw_jjkmb where kmbh='30211')) as kmbh,'30211' as jjfl,(select x.BMBH from cw_xmjbxxb x where x.guid = s.jfbh) as bmbh,(select xmbh from cw_xmjbxxb where guid = s.jfbh) as xmbh,'0' as jdfx,to_number(s.bcbxje) as jfje,null as dfje," ); 
		sql.append(" s.jfbh as xmguid " ); 
		sql.append(" from cw_clfbxzb t left join cw_ccsqspb_xm s on t.ccywguid = s.ccsqid where t.guid = ?");
		sql.append(" union all " ); 
		sql.append(" select 1 as xh,r.guid,(select (ry.xm||'报'||ccsy)AS cc  from cw_clfbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.sqr where b.guid=?) as zy,(select kmbh from cw_zffsdzb where zffs = '01' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,cjkje as dfje,'' as xmguid" ); 
		sql.append(" from cw_clfbxzb t left join cw_cjkb r on t.guid = r.jkdh where t.sfcjk = '1' and t.guid = ? " ); 
		sql.append(" union all  "); 
		sql.append(" select 1 as xh,r.guid,(select (ry.xm||'报'||ccsy)AS cc  from cw_clfbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.sqr where b.guid=?) as zy,(select kmbh from cw_zffsdzb where zffs = '02' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,je as dfje,'' as xmguid" ); 
		sql.append(" from cw_clfbxzb t left join cw_dgzfb r on t.guid = r.zfdh where t.sfdgzf = '1' and t.guid = ? " );
		sql.append(" union all  " );
		sql.append(" select 1 as xh,r.guid,(select (ry.xm||'报'||ccsy)AS cc  from cw_clfbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.sqr where b.guid=?) as zy,(select kmbh from cw_zffsdzb where zffs = '04' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,skje as dfje,'' as xmguid" ); 
		sql.append(" from cw_clfbxzb t left join cw_gwkb r on t.guid = r.skdh where t.sfgwk = '1' and t.guid = ? " );
		sql.append(" union all  " ); 
		sql.append(" select 1 as xh,r.guid,(select (ry.xm||'报'||ccsy)AS cc  from cw_clfbxzb b LEFT JOIN  gx_sys_ryb ry ON  ry.guid = b.sqr where b.guid=?) as zy,(select kmbh from cw_zffsdzb where zffs = '03' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,je as dfje,'' as xmguid " );
		sql.append(" from cw_clfbxzb t left join cw_dszfb r on t.guid = r.zfdh where t.sfdszf = '1' and t.guid = ?)");
		sql.append(" where nvl(jfje,0)<>0 or nvl(dfje,0)<>0");
		Object[] obj = {
				pzzbid,sszt,kjzd,
				bxid,bxid,
				bxid,sszt,kjzd,bxid,
				bxid,sszt,kjzd,bxid,
				bxid,sszt,kjzd,bxid,
				bxid,sszt,kjzd,bxid
		};
		return db.update(sql.toString(),obj);
	}
	
	public int autoInsertFzlrByClbx(String pzzbid,String sszt,String bxid,String kjzd) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into CW_PZLRYHMX(guid,zbid,mxid,status,xm,yhzh,yhmc,je,yhlhh) " );
		sql.append("select sys_guid() as guid,zbid,mxid,status,xm,yhzh,yhmc,je,yhlhh from (");
		sql.append( "select z.guid as zbid,t.guid as mxid,'0' as status,(select d.dwmc from cw_wldwb d where d.wlbh=r.dfdw ) as xm,r.dfzh as yhzh,(select y.khyh from cw_wldwmxb y where 1=1 and y.guid=r.dfyh) as yhmc,r.je, " );
		sql.append(" (select y.yhlhh from cw_wldwmxb y where 1=1 and y.guid=r.dfyh)yhlhh ");
		sql.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_clfbxzb c on c.guid=d.bxid "); 
		sql.append(" left join cw_dgzfb r on c.guid = r.zfdh where c.sfdgzf = '1' and c.guid = ? and t.guid=r.guid " );
		sql.append(" union all  " );
		sql.append("select z.guid as zbid,t.guid as mxid,'0' as status,(select to_char(xm) from gx_sys_ryb ry where ry.rybh=r.Ryxm) as xm,r.dfzh as yhzh,r.KLX as yhmc,r.je," );
		sql.append(" (select y.yhlhh from cw_jsyhzhb y where 1=1 and y.guid=r.ZHBGUID)yhlhh ");
		sql.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_clfbxzb c on c.guid=d.bxid " );
		sql.append(" left join cw_dszfb r on c.guid = r.zfdh where c.sfdszf = '1' and c.guid = ? and t.guid=r.guid " ); 
		sql.append(" ) ");
		Object[] obj = {
				bxid,bxid
		};
		StringBuffer sql1 = new StringBuffer();
		sql1.append("insert into cw_fzlrb(guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,czr,czrq,dqsj,gnkm,khyh,hm,zh,sksj,gwkh,sjskje) " );
		sql1.append(" select sys_guid() as guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,? as czr,sysdate as czrq,dqsj,gnkm,khyh,hm,zh,sksj,gwkh,sjskje" );
		sql1.append(" from(" ); 
		sql1.append(" select t.guid as kmbh,r.jkid as wldc,(select rybh from gx_sys_ryb where guid =r.jkr) as zrr,'' as wldw,'0004' as jsfs,c.djbh as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,'' as khyh,'' as hm,'' as zh,'' as sksj,'' as gwkh,'' as sjskje ");
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_clfbxzb c on c.guid=d.bxid " ); 
		sql1.append(" left join cw_cjkb r on c.guid = r.jkdh left join cw_jkywb j on j.gid=r.jkid where c.sfcjk = '1' and c.guid = ? and t.guid=r.guid" ); 
		sql1.append(" union all  " ); 
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0002' as jsfs,c.djbh as jsdh,r.dfdw as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,r.dfyh as khyh,'' as hm,r.dfzh as zh,'' as sksj, '' as gwkh,'' as sjskje "); 
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_clfbxzb c on c.guid=d.bxid " ); 
		sql1.append(" left join cw_dgzfb r on c.guid = r.zfdh where c.sfdgzf = '1' and c.guid = ? and t.guid=r.guid " ); 
		sql1.append(" union all  " ); 
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0005' as jsfs,c.djbh as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,(select a.xm from gx_sys_ryb a where a.rybh=r.ryxm) as bz,'' as  dqsj,'' as gnkm,'' as khyh,'' as hm,r.kh as zh, to_char(r.skrq, 'yyyy-mm-dd') as sksj ,r.kh as gwkh,to_char(r.sjskje) as sjskje "); 
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_clfbxzb c on c.guid=d.bxid ");
		sql1.append(" left join cw_gwkb r on c.guid = r.skdh where c.sfgwk = '1' and c.guid = ? and t.guid=r.guid " );
		sql1.append(" union all  " );
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0003' as jsfs,c.djbh as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,r.zhbguid as khyh,r.ryxm as hm,r.dfzh as zh,'' as sksj,'' as gwkh,'' as sjskje " ); 
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_clfbxzb c on c.guid=d.bxid " );
		sql1.append(" left join cw_dszfb r on c.guid = r.zfdh where c.sfdszf = '1' and c.guid = ? and t.guid=r.guid " );
		sql1.append(" ) ");
		Object[] obj1 = {
				LUser.getRybh(),bxid,bxid,bxid,bxid
		};
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		sqlList.add(sql1.toString());
		sqlList.add(sql.toString());
		objList.add(obj1);
		objList.add(obj);
		boolean bool = db.batchUpdate(sqlList,objList);
		int m = 0;
		if(bool){
			m = 100;
		}
		return m;
	}
	/**
	 *  公务接待报销 插入 凭证明细表
	 * @param pzzbid
	 * @param sszt
	 * @param bxid
	 * @param kjzd
	 * @return
	 */
	public int autoInsertPzmxByGwjdbx(String pzzbid,String sszt,String bxid,String kjzd) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sszt,kjzd,indexnew,xmguid) " );
		sql.append(" select nvl(guid,sys_guid()) as guid,? as pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx, jfje, dfje,? as sszt,? as kjzd,xh,xmguid" );
		sql.append(" from("  );
		sql.append(" select 0 as xh,'' as guid,(select to_char((select mc from gx_sys_dwb d where d.dwbh=r.dwbh)||'('||xm||')'||'报公务接待费') from gx_sys_ryb r where guid = t.bxryid) as zy,(select kmbh from cw_fykmdzb where fymc = '公务接待费') as kmbh,'30217' as jjfl,'101' as bmbh,'1005' as xmbh,'0' as jdfx,bxje as jfje,null as dfje,"  ); 
		sql.append(" ( select t.guid from CW_xmjbxxb t where t.bmbh='101' and t.xmbh='1005' ) as xmguid "  );
		sql.append(" from cw_gwjdfbxzb t where t.guid = ?" );
		sql.append(" union all "  );
		sql.append(" select 1 as xh,r.guid,(select to_char((select mc from gx_sys_dwb d where d.dwbh=r.dwbh)||'('||xm||')'||'报公务接待费') from gx_sys_ryb r where guid = t.bxryid) as zy,(select kmbh from cw_zffsdzb where zffs = '01' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,cjkje as dfje,'' as xmguid " ); 
		sql.append(" from cw_gwjdfbxzb t left join cw_cjkb r on t.guid = r.jkdh where t.sfcjk = '1' and t.guid = ? "  ); 
		sql.append(" union all  "  ); 
		sql.append(" select 1 as xh,r.guid,(select to_char((select mc from gx_sys_dwb d where d.dwbh=r.dwbh)||'('||xm||')'||'报公务接待费') from gx_sys_ryb r where guid = t.bxryid) as zy,(select kmbh from cw_zffsdzb where zffs = '02' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,je as dfje,'' as xmguid "  ); 
		sql.append(" from cw_gwjdfbxzb t left join cw_dgzfb r on t.guid = r.zfdh where t.sfdgzf = '1' and t.guid = ? "  ); 
		sql.append(" union all  "  );
		sql.append(" select 1 as xh,r.guid,(select to_char((select mc from gx_sys_dwb d where d.dwbh=r.dwbh)||'('||xm||')'||'报公务接待费') from gx_sys_ryb r where guid = t.bxryid) as zy,(select kmbh from cw_zffsdzb where zffs = '04' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,skje as dfje,'' as xmguid "  ); 
		sql.append(" from cw_gwjdfbxzb t left join cw_gwkb r on t.guid = r.skdh where t.sfgwk = '1' and t.guid = ? " );
		sql.append(" union all  "  );
		sql.append(" select 1 as xh,r.guid,(select to_char((select mc from gx_sys_dwb d where d.dwbh=r.dwbh)||'('||xm||')'||'报公务接待费') from gx_sys_ryb r where guid = t.bxryid) as zy,(select kmbh from cw_zffsdzb where zffs = '03' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,je as dfje,'' as xmguid "  ); 
		sql.append(" from cw_gwjdfbxzb t left join cw_dszfb r on t.guid = r.zfdh where t.sfdszf = '1' and t.guid = ?) "  );
//				" group by xh,zy,kmbh,jjfl,bmbh,xmbh,jdfx " +
		sql.append(" where nvl(jfje,0)<>0 or nvl(dfje,0)<>0" );
		Object[] obj = {
				pzzbid,sszt,kjzd,
				bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 *  个人收入 插入凭证 明细表
	 * @param pzzbid
	 * @param sszt
	 * @param bxid
	 * @param kjzd
	 * @return
	 */
	public int autoInsertPzmxByGrsr(String pzzbid,String sszt,String bxid,String kjzd) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sszt,kjzd,indexnew,xmguid) " );
		sql.append(" select nvl(guid,sys_guid()) as guid,?,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,?,   ?,   xh,xmguid "); 
		sql.append(" from(" );
		sql.append(" select 0 as xh,'' as guid,t.zy as zy,'500101' as kmbh,mx.jjkm as jjfl,(select b.dwbh from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.rybh = t.jbr)as bmbh,(SELECT D.XMBH FROM XMINFOS D WHERE D.guid=t.XMBH) as xmbh,'0' as jdfx,  to_char((select sum(sfje) from cw_grsrmxb where fflsh=t.fflsh)) as jfje,null as dfje," );
		sql.append(" t.XMBH as xmguid " );
		sql.append(" from cw_grsrzb t left join cw_grsrmxb mx on t.fflsh=mx.fflsh where t.guid = ?");
		sql.append(" union all " );
		sql.append(" select 1 as xh,'' as guid,t.zy as zy,(select kmbh from cw_zffsdzb where zffs = t.fffs and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,'' as jfje,(select sum(sfje) from cw_grsrmxb where fflsh=t.fflsh) as dfje,'' as xmguid " ); 
		sql.append(" from cw_grsrzb t left join cw_grsrmxb mx on t.fflsh=mx.fflsh left join cw_cjkb r on t.guid = r.jkdh where t.guid = ? "); 
		sql.append(" ) " );
		sql.append(" where nvl(jfje,0)<>0 or nvl(dfje,0)<>0" );
		Object[] obj = {
				pzzbid,sszt,kjzd,
				bxid,
				sszt,kjzd,bxid
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 * 公务接待报销 插入 辅助录入表
	 * @param pzzbid
	 * @param sszt
	 * @param bxid
	 * @param kjzd
	 * @return
	 */
	public int autoInsertFzlrByGwjdbx(String pzzbid,String sszt,String bxid,String kjzd) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into CW_PZLRYHMX(guid,zbid,mxid,status,xm,yhzh,yhmc,je,yhlhh) " );
		sql.append("select sys_guid() as guid,zbid,mxid,status,xm,yhzh,yhmc,je,yhlhh from (");
		sql.append("select z.guid as zbid,t.guid as mxid,'0' as status,(select d.dwmc from cw_wldwb d where d.wlbh=r.dfdw ) as xm,r.dfzh as yhzh,(select y.khyh from cw_wldwmxb y where 1=1 and y.guid=r.dfyh) as yhmc,r.je, " );
		sql.append(" (select y.yhlhh from cw_wldwmxb y where 1=1 and y.guid=r.dfyh)yhlhh ");
		sql.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_gwjdfbxzb c on c.guid=d.bxid " ); 
		sql.append(" left join cw_dgzfb r on c.guid = r.zfdh where c.sfdgzf = '1' and c.guid = ? and t.guid=r.guid " );  
		sql.append(" union all  " );
		sql.append("select z.guid as zbid,t.guid as mxid,'0' as status,(select to_char(xm) from gx_sys_ryb ry where ry.rybh=r.Ryxm) as xm,r.dfzh as yhzh,r.KLX as yhmc,r.je," ); 
		sql.append(" (select y.yhlhh from cw_jsyhzhb y where 1=1 and y.guid=r.ZHBGUID)yhlhh ");
		sql.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_gwjdfbxzb c on c.guid=d.bxid " );
		sql.append(" left join cw_dszfb r on c.guid = r.zfdh where c.sfdszf = '1' and c.guid = ? and t.guid=r.guid " ); 
		sql.append(" ) ");
		Object[] obj = {
				bxid,bxid
		};
		StringBuffer sql1 = new StringBuffer();
		sql1.append("insert into cw_fzlrb(guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,czr,czrq,dqsj,gnkm,khyh,hm,zh,sksj,gwkh,sjskje) " );
		sql1.append(" select sys_guid() as guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,? as czr,sysdate as czrq,dqsj,gnkm,khyh,hm,zh,sksj,gwkh,sjskje" );
		sql1.append(" from(" );
		sql1.append(" select t.guid as kmbh,r.jkid as wldc,(select rybh from gx_sys_ryb where guid =r.jkr)  as zrr,'' as wldw,'0004' as jsfs,j.djbh as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,'' as khyh,'' as hm,'' as zh,'' as sksj,'' as gwkh,'' as sjskje " );
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_gwjdfbxzb c on c.guid=d.bxid " ); 
		sql1.append(" left join cw_cjkb r on c.guid = r.jkdh left join cw_jkywb j on j.gid=r.jkid where c.sfcjk = '1' and c.guid = ? and t.guid=r.guid " ); 
		sql1.append(" union all  " ); 
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0002' as jsfs,'' as jsdh,r.dfdw as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,r.dfyh as khyh,'' as hm,r.dfzh as zh,'' as sksj,'' as gwkh,'' as sjskje  " ); 
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_gwjdfbxzb c on c.guid=d.bxid " ); 
		sql1.append(" left join cw_dgzfb r on c.guid = r.zfdh where c.sfdgzf = '1' and c.guid = ? and t.guid=r.guid " );
		sql1.append(" union all  " ); 
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0005' as jsfs,'' as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,(select a.xm from gx_sys_ryb a where a.rybh=r.ryxm) as bz,'' as  dqsj,'' as gnkm,'' as khyh,'' as hm,r.kh as zh, to_char(r.skrq, 'yyyy-mm-dd') as sksj,r.kh as gwkh,to_char(r.sjskje) as sjskje "); 
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_gwjdfbxzb c on c.guid=d.bxid " );
		sql1.append(" left join cw_gwkb r on c.guid = r.skdh where c.sfgwk = '1' and c.guid = ? and t.guid=r.guid " ); 
		sql1.append(" union all  " ); 
		sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0003' as jsfs,'' as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,null as bz,'' as dqsj,'' as gnkm,r.zhbguid as khyh,r.ryxm as hm,r.dfzh as zh,'' as sksj,'' as gwkh,'' as sjskje  " );
		sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_gwjdfbxzb c on c.guid=d.bxid ");
		sql1.append(" left join cw_dszfb r on c.guid = r.zfdh where c.sfdszf = '1' and c.guid = ? and t.guid=r.guid " ); 
		sql1.append(" ) " );
		Object[] obj1 = {
				LUser.getRybh(),bxid,bxid,bxid,bxid
		};
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		sqlList.add(sql1.toString());
		sqlList.add(sql.toString());
		objList.add(obj1);
		objList.add(obj);
		boolean bool = db.batchUpdate(sqlList,objList);
		int m = 0;
		if(bool){
			m = 100;
		}
		return m;
	}
	/**
	 * 凭证录入明细表     借款
	 * @param pzzbid  pzzbid = bxid = checkbox.guid
	 * @param sszt  sszt  = 账套id
	 * @param bxid
	 * @param kjzd  会计制度  0001,0002...
	 * @return
	 */
	public int autoInsertPzmxByJkbx(String pzzbid,String sszt,String bxid,String kjzd) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sszt,kjzd,indexnew,xmguid) " );
		sql.append(" select nvl(guid,sys_guid()) as guid,? as pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,? as sszt,? as kjzd,xh,xmguid " ); 
		sql.append(" from(" ); 
		sql.append(" select 0 as xh,'' as guid,(select to_char((select mc from gx_sys_dwb d where d.dwbh=r.dwbh)||'('||xm||')'||'借款') from gx_sys_ryb r where guid = t.jkr) as zy,(select to_char(ms) from gx_sys_dmb where zl = 'jkkmbh' and dm = '01' ) as kmbh,'' as jjfl,(select x.BMBH from cw_xmjbxxb x where x.guid = m.jfbh) as bmbh,(select xmbh from cw_xmjbxxb where guid = m.jfbh) as xmbh,'0' as jdfx,to_number(t.JKZJE) as jfje,null as dfje," );
		sql.append(" m.jfbh as xmguid " ); 
		sql.append(" from cw_jkywb t left join cw_jkywb_mxb m on t.gid=m.jkid where t.gid = ?" );
		sql.append(" union all " ); 
		sql.append(" select 1 as xh,r.guid,(select to_char((select mc from gx_sys_dwb d where d.dwbh=r.dwbh)||'('||xm||')'||'借款') from gx_sys_ryb r where guid = t.jkr) as zy,(select kmbh from cw_zffsdzb where zffs = '02' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,je as dfje,''  as xmguid " ); 
		sql.append(" from cw_jkywb t left join cw_dgzfb r on t.gid = r.zfdh where t.zffs = '1' and t.gid = ? " ); 
		sql.append(" union all " ); 
		sql.append(" select 1 as xh,r.guid,(select to_char((select mc from gx_sys_dwb d where d.dwbh=r.dwbh)||'('||xm||')'||'借款') from gx_sys_ryb r where guid = t.jkr) as zy,(select kmbh from cw_zffsdzb where zffs = '03' and sszt = ? and kjzd = ?) as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'1' as jdfx,null as jfje,je as dfje,''  as xmguid " ); 
		sql.append(" from cw_jkywb t left join cw_dszfb r on t.gid = r.zfdh where t.zffs = '0' and t.gid = ?)" );
		sql.append(" where nvl(jfje,0)<>0 or nvl(dfje,0)<>0");
		Object[] obj = {
				pzzbid,sszt,kjzd,
				bxid,
//				sszt,kjzd,bxid,
//				sszt,kjzd,bxid,
				sszt,kjzd,bxid,
				sszt,kjzd,bxid
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 * 凭证录入明细 管理费
	 * @author BiMing
	 * @Time 2018年10月13日下午3:23:21
	 * @param pzzbid  pzzbid = bxid = checkbox.guid
	 * @param sszt  sszt  = 账套id
	 * @param bxid
	 * @param kjzd  会计制度  0001,0002...
	 * @return
	 */
	public int autoInsertPzmxByGlf(String pzzbid,String sszt,String bxid,String kjzd) {
		String sqllx = "select xmlx from cw_kyglf where guid=?";
		String xmlx = db.queryForSingleValue(sqllx,new Object[] {bxid});
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sszt,kjzd) " );
		sql.append(" select sys_guid() as guid,? as pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,? as sszt,? as kjzd" ); 
		sql.append(" from(" ); 
		//学校
		if("1".equals(xmlx)) {//横向，借贷。
			sql.append(" select ('提'||(select xmmc from cw_xmjbxxb b where b.xmbh=t.xmbh and b.bmbh=t.szbm)||'管理费') as zy,(select to_char(ms) from gx_sys_dmb where zl = 'xxjjhj' and dm = '01' ) as kmbh,(select to_char(ms) from gx_sys_dmb where zl = 'glf' and dm = '01' ) as jjfl,t.szbm as bmbh,t.xmbh as xmbh,'0' as jdfx,to_number(t.xxjj) as jfje,null as dfje" ); 
			sql.append(" from cw_kyglf t where t.guid = ?" );
			sql.append(" union all " ); 
			sql.append(" select ('提'||(select xmmc from cw_xmjbxxb b where b.xmbh=t.xmbh and b.bmbh=t.szbm)||'管理费') as zy,(select to_char(ms) from gx_sys_dmb where zl = 'xxjjhd' and dm = '01' ) as kmbh,(select to_char(ms) from gx_sys_dmb where zl = 'glf' and dm = '01' ) as jjfl,(select k.bmbh  from GX_SYS_DMB d left join cw_xmjbxxb k on k.xmbh = d.ms and d.mc=k.bmbh where d.zl='xxxm'  and d.dm = '01') as bmbh,t.xmbh as xmbh,'1' as jdfx,null as jfje,to_number(t.xxjj) as dfje" ); 
			sql.append(" from cw_kyglf t where t.guid = ? " ); 
		}else {//纵向，借贷。
			sql.append(" select ('提'||(select xmmc from cw_xmjbxxb b where b.xmbh=t.xmbh and b.bmbh=t.szbm)||'管理费') as zy,(select to_char(ms) from gx_sys_dmb where zl = 'xxjjzj' and dm = '01' ) as kmbh,(select to_char(ms) from gx_sys_dmb where zl = 'glf' and dm = '01' ) as jjfl,t.szbm as bmbh,t.xmbh as xmbh,'0' as jdfx,to_number(t.xxjj) as jfje,null as dfje" ); 
			sql.append(" from cw_kyglf t where t.guid = ?" );
			sql.append(" union all " ); 
			sql.append(" select ('提'||(select xmmc from cw_xmjbxxb b where b.xmbh=t.xmbh and b.bmbh=t.szbm)||'管理费') as zy,(select to_char(ms) from gx_sys_dmb where zl = 'xxjjzd' and dm = '01' ) as kmbh,(select to_char(ms) from gx_sys_dmb where zl = 'glf' and dm = '01' ) as jjfl,(select k.bmbh  from GX_SYS_DMB d left join cw_xmjbxxb k on k.xmbh = d.ms and d.mc=k.bmbh where d.zl='xxxm'  and d.dm = '01') as bmbh,t.xmbh as xmbh,'1' as jdfx,null as jfje,to_number(t.xxjj) as dfje" ); 
			sql.append(" from cw_kyglf t where t.guid = ? " ); 
		}
		//学院
		sql.append(" union all " ); 
		sql.append(" select ('提'||(select xmmc from cw_xmjbxxb b where b.xmbh=t.xmbh and b.bmbh=t.szbm)||'科技发展基金') as zy,(select to_char(ms) from gx_sys_dmb where zl = 'xyjjj' and dm = '01' ) as kmbh,(select to_char(ms) from gx_sys_dmb where zl = 'glf' and dm = '01' ) as jjfl,t.szbm as bmbh,t.xmbh as xmbh,'0' as jdfx,to_number(t.xyjj) as jfje,null as dfje" ); 
		sql.append(" from cw_kyglf t where t.guid = ?" );
		sql.append(" union all " ); 
		sql.append(" select ('提'||(select xmmc from cw_xmjbxxb b where b.xmbh=t.xmbh and b.bmbh=t.szbm)||'科技发展基金') as zy,(select to_char(ms) from gx_sys_dmb where zl = 'xyjjd' and dm = '01' ) as kmbh,(select to_char(ms) from gx_sys_dmb where zl = 'glf' and dm = '01' ) as jjfl,(select k.bmbh  from GX_SYS_DMB d left join cw_xmjbxxb k on k.xmbh = d.ms and d.mc=k.bmbh where d.zl='xyxm'  and d.dm = '01') as bmbh,t.xmbh as xmbh,'1' as jdfx,null as jfje,to_number(t.xyjj) as dfje" ); 
		sql.append(" from cw_kyglf t where t.guid = ? )" ); 
		Object[] obj = {
				pzzbid,sszt,kjzd,
				bxid,
				bxid,
				bxid,
				bxid
		};
		return db.update(sql.toString(),obj);
	}
	/**
	 *   CW_PZLRYHMX    CW_FZLRB
	 * @param pzzbid
	 * @param sszt
	 * @param bxid
	 * @param kjzd
	 * @return
	 */
	public int autoInsertFzlrByJkbx(String pzzbid,String sszt,String bxid,String kjzd) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into CW_PZLRYHMX(guid,zbid,mxid,status,xm,yhzh,yhmc,je,yhlhh) " );
		sql.append( "select sys_guid() as guid,zbid,mxid,status,xm,yhzh,yhmc,je,yhlhh from (");
		sql.append("select z.guid as zbid,t.guid as mxid,'0' as status,(select d.dwmc from cw_wldwb d where d.wlbh=r.dfdw ) as xm,r.dfzh as yhzh,(select y.khyh from cw_wldwmxb y where 1=1 and y.guid=r.dfyh) as yhmc,r.je, " );
		sql.append(" (select y.yhlhh from cw_wldwmxb y where 1=1 and y.guid=r.dfyh)yhlhh ");
		sql.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_jkywb c on c.gid=d.bxid " ); 
		sql.append(" left join cw_dgzfb r on c.gid = r.zfdh where c.zffs = '1' and c.gid = ? and t.guid=r.guid " );  
		sql.append(" union all  " ); 
		sql.append("select z.guid as zbid,t.guid as mxid,'0' as status,(select to_char(xm) from gx_sys_ryb ry where ry.rybh=r.Ryxm) as xm,r.dfzh as yhzh,r.KLX as yhmc,r.je," ); 
		sql.append(" (select y.yhlhh from cw_jsyhzhb y where 1=1 and y.guid=r.ZHBGUID)yhlhh ");
		sql.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_jkywb c on c.gid=d.bxid " );
		sql.append(" left join cw_dszfb r on c.gid = r.zfdh where c.zffs = '0' and c.gid = ? and t.guid=r.guid " ); 
		sql.append(" ) ");
		Object[] obj = {
				bxid,bxid
		};
		StringBuffer sql1 = new StringBuffer();
		sql1.append("insert into cw_fzlrb(guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,czr,czrq,dqsj,gnkm,khyh,hm,zh) ");
				sql1.append(" select sys_guid() as guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,? as czr,sysdate as czrq,dqsj,gnkm,khyh,hm,zh"); 
				sql1.append(" from("); 
//				sql1.append(" select t.guid as kmbh,【--C.DJBH--】 r.jkr as wldc,r.ryxm as zrr,'' as wldw,'冲借款' as jsfs,j.djbh as jsdh,'' as dfdw,'' as yslx,c.djbh as zclx,'' as ysly,'' as bz,'' as dqsj,'' as gnkm,'' as khyh,'' as hm,'' as zh ");
//				sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_clfbxzb c on c.guid=d.bxid "); 
//				sql1.append(" left join cw_cjkb r on c.guid = r.jkdh left join cw_jkywb j on j.gid=r.jkid where c.sfcjk = '1' and t.guid = ? "); 
//				sql1.append(" union "); 
				sql1.append(" select t.guid as kmbh, '' as wldc,'' as zrr,'' as wldw,'0002' as jsfs,'' as jsdh,r.dfdw as dfdw,'' as yslx,'' as zclx,'' as ysly,'' as bz,'' as dqsj,'' as gnkm,r.dfyh as khyh,'' as hm,r.dfzh as zh"); 
				sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_jkywb c on c.gid=d.bxid "); 
				sql1.append(" left join cw_dgzfb r on c.gid = r.zfdh where c.zffs = '1' and c.gid = ? and t.guid=r.guid "); 
				sql1.append(" union all  "); 
//				sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'公务卡支付' as jsfs,'' as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,'' as bz,'' as  dqsj,'' as gnkm,'' as khyh,'' as hm,'' as zh"); 
//				sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_clfbxzb c on c.guid=d.bxid ");
//				sql1.append(" left join cw_gwkb r on c.guid = r.skdh where c.sfgwk = '1' and t.guid = ? "); 
//				sql1.append(" union ");                        r.ryxm
				sql1.append(" select t.guid as kmbh,'' as wldc,'' as zrr,'' as wldw,'0003' as jsfs,'' as jsdh,'' as dfdw,'' as yslx,'' as zclx,'' as ysly,'' as bz,'' as dqsj,'' as gnkm,r.zhbguid as khyh,r.ryxm as hm,r.dfzh as zh "); 
				sql1.append(" from cw_pzlrmxb t left join cw_pzlrzb z on t.pzbh  = z.guid left join cw_pzlrbxdzb d on d.pzid=z.guid left join cw_jkywb c on c.gid=d.bxid ");
				sql1.append(" left join cw_dszfb r on c.gid = r.zfdh where c.zffs = '0' and c.gid = ? and t.guid=r.guid "); 
				sql1.append(" ) ");
		Object[] obj1 = {
				LUser.getRybh(),bxid,bxid
		};
		
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		sqlList.add(sql1.toString());
		sqlList.add(sql.toString());
		objList.add(obj1);
		objList.add(obj);
		boolean bool = db.batchUpdate(sqlList,objList);
		
		String sqla = " select guid as kmbh  from CW_PZLRMXB where pzbh = (select gid from CW_JKYWB where djbh = (select c.djbh from  cw_jkywb c where c.gid = '" + bxid +"')) "
			     + " and kmbh= ( select ms from GX_SYS_DMB where zl = 'jkkmbh' ) ";
		String kmbh = db.queryForSingleValue(sqla);
		String sqlb = " select rybh from GX_SYS_RYB where guid = (select c.jkr from  cw_jkywb c where c.gid = '" + bxid +"')" ;
		String zrr = db.queryForSingleValue(sqlb);
		String sqlc = " select c.djbh from  cw_jkywb c where c.gid = '" + bxid +"' ";
		String djbh = db.queryForSingleValue(sqlc);
		String sql2 = " insert into cw_fzlrb(guid,kmbh,zrr,zclx ) VALUES (" +
		     " sys_guid() , '"+ kmbh +"','"+zrr +"','"+djbh +"'  )";
		db.update(sql2);
		
		int m = 0;
		if(bool){
			m = 100;
		}
		return m;
	}
	/**
	 * 查询借方必有,借方必无科目
	 * @param pzz
	 * @param sszt
	 * @return
	 */
	public Map<String, Object> getBybwkm(String pzz,String sszt) {
		String sql = "select distinct \r\n" + 
				"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Jf') as jfbykm,\r\n" + 
				"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Df') as dfbykm,\r\n" + 
				"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzby') as pzbykm,\r\n" + 
				"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzbw') as pzbwkm\r\n" + 
				"from cw_pzlxb t join cw_pzkmdzb s on t.guid = s.pzlxm where t.lxbh = '"+pzz+"' and t.sszt = '"+sszt+"'";
		return db.queryForMap(sql);
	}
	/**
	 * 删除凭证信息
	 * @param guid	主表id
	 * @return
	 */
	public boolean deletePzzb(String guid) {
		List<String> sqls = new ArrayList<String>();
		List<Object[]> pars = new ArrayList<Object[]>();
//		sqls.add("delete from CW_PZLRYHMX where mxid in (select guid from cw_pzlrmxb where pzbh = ?)");
//		pars.add(new Object[]{guid});
		sqls.add("delete from cw_fzlrb where kmbh in (select guid from cw_pzlrmxb where pzbh = ?)");
		pars.add(new Object[]{guid});
		sqls.add("delete from cw_pzlrmxb where pzbh = ?");
		pars.add(new Object[]{guid});
		sqls.add("delete from cw_pzlrzb where guid = ?");
		pars.add(new Object[]{guid});
		sqls.add("delete from cw_pzlrbxdzb where pzid = ?");
		pars.add(new Object[]{guid});
		return db.batchUpdate(sqls, pars);
	}
	
	 /** 
	  * 删除凭证信息
	  * 删除凭证同步更新到报销业务表审核状态为审核通过的（8）
	 * @param guid	主表id
	 * @param string 
	 * @param pzly 
	 * @return
	 */
	public boolean deletePzzbAll(String guid, String string, String pzly) {
		List<String> sqls = new ArrayList<String>();
		List<Object[]> pars = new ArrayList<Object[]>();
		sqls.add("delete from cw_fzlrb where kmbh in (select guid from cw_pzlrmxb where pzbh = ?)");
		pars.add(new Object[]{guid});
		sqls.add("delete from cw_pzlrmxb where pzbh = ?");
		pars.add(new Object[]{guid});
		sqls.add("delete from cw_pzlrzb where guid = ?");
		pars.add(new Object[]{guid});
		sqls.add("delete from cw_pzlrbxdzb where pzid = ?");
		pars.add(new Object[]{guid});
		sqls.add("delete from CW_PZLRYHMX where zbid = ?");
		pars.add(new Object[]{guid});
		
		if("del".equals(string)&&"4".equals(pzly)){
			Map bxmap = getBxpzxx(guid);
			String bxid = (String) bxmap.get("bxid");
			String type = (String) bxmap.get("bxtype");
			switch (type) {
			case "clfbx":
				sqls.add("update cw_clfbxzb  set shzt = '8' where guid= ?");
				pars.add(new Object[]{guid});
				break;
			case "gwjdbx":
				sqls.add("update cw_gwjdfbxzb  set shzt = '8' where guid= ?");
				pars.add(new Object[]{guid});
				break;
			case "rcbx":
				sqls.add("update cw_rcbxzb set shzt = '8' where guid= ?");
				pars.add(new Object[]{guid});
				break;
			case "jkbx":
				sqls.add("update cw_jkywb  set shzt = '8' where gid= ?");
				pars.add(new Object[]{guid});
				break;
			case "srsblr":
				sqls.add("update cw_grsrzb   set shzt = '8' where guid= ?");
				pars.add(new Object[]{guid});
				break;
			default:
				break;
			}
		}
		
//		sqls.add("update cw_grsrmxb   set sfje = '',bqks='', jsbz='0' where fflsh=(select fflsh from cw_grsrzb where guid=?)");
//		pars.add(new Object[]{guid});
		return db.batchUpdate(sqls, pars);
	}
	/**
	 * 删除凭证录入明细
	 * @param guid	主表id
	 * @return
	 */
	public int deletePzmx(String guid) {
		String sql = "delete from cw_pzlrmxb where pzbh = '"+guid+"'";
		Object[] obj = {guid};
		return db.update(sql,obj);
	}
	/**
	 * 凭证编号排序
	 * @param pzbh
	 * @param pzz
	 * @param sszt
	 * @param pznd
	 * @param kjqj
	 * @param kjzd
	 * @return
	 */
	public int sortPzbh(String pzbh,String sszt,String pznd,String kjqj,String pzlx) {
		String sql = "update cw_pzlrzb set pzbh = replace(lpad(pzbh - 1,4),' ','0')  where pzbh > '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' and pzz='"+pzlx+"'";
		return db.update(sql);
	}
	/**
	 * 获取凭证模板中科目编号，摘要
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getPzListByMb(String guid) {
		String sql = "select s.mbbh,s.zy as zy,s.kmbh,TO_CHAR(ROUND(s.jfje,2),'FM999999999999999999.00') jfje,TO_CHAR(ROUND(s.dfje,2),'FM999999999999999999.00') dfje,t.jfjehj,t.dfjehj from cw_pzmbzb t join cw_pzmbmxb s on t.guid = s.mbbh where t.guid = '"+guid+"' order by INDEXNEW";
		return db.queryForList(sql);
	}
	/**
	 * 向凭证录入报销对照表中插入数据
	 * @param pzid
	 * @param bxid
	 * @return
	 */
	public int insertPzlrbxdzb(String pzid,String bxid,String type) {
		String sql = "insert into cw_pzlrbxdzb (pzid,bxid,bxtype) values ('"+pzid+"','"+bxid+"','"+type+"')";
		return db.update(sql);
	}
	/**
	 * 查询是否自动生成凭证，自动录入凭证
	 * @return
	 */
	public Map<String, Object> getZdscpz(){
		String sql = "select zdscpz,zdlrpz,sfyxscpz,zdr,fhr,jzr from CW_ZDSCPZ";
		return db.queryForMap(sql);
	}
	/**
	 * 查询是否自动生成凭证，自动录入凭证
	 * @return
	 */
	public Map<String, Object> getZdscpzByAuto(){
		String sql = "select zdscpz,zdlrpz,sfyxscpz,zdr,fhr,jzr from CW_ZDSCPZ where zdlrpz='Y'";
		return db.queryForMap(sql);
	}
	/**
	 * 获取凭证状态
	 * @param sszt
	 * @param pznd
	 * @param kjqj
	 * @param pzbh
	 * @return
	 */
	public String getPzzt(String sszt,String pznd,String kjqj,String pzbh) {
		String sql = "select shzt from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' and pzbh = '"+pzbh+"'";
		return db.queryForObject(sql,String.class);
	}
	
	/**
	 * 查询凭证类型
	 * @return
	 */
	public Map<String, Object> getPzlxmc(String pzlx){
		String sql = "select pzlxbh,pzlxmc from cw_pzlxbnew where pzlxbh = ?";
		return db.queryForMap(sql, new Object[]{pzlx});
	}
	/**
	 * 
	 * @param sszt
	 * @param pznd
	 * @param kjqj
	 * @param pzbh
	 * @return
	 */
	public String getPzbh(String sszt,String pznd,String kjqj,String pzz) {
		String sql = "select pzbh from cw_pzlrzb where pznd='"+pznd+"' and kjqj='"+kjqj+"' and sszt='"+sszt+"' and pzz='"+pzz+"' and rownum<2 ";
		return db.queryForObject(sql,String.class);
	}
	/**
	 * 查询未审核最小的
	 * @param sszt
	 * @param pznd
	 * @param kjqj
	 * @param pzz
	 * @return
	 */
	
	public String getminPzbh(String sszt,String pznd,String kjqj,String pzz) {
		String sql = "select min(pzbh) from cw_pzlrzb where pznd='"+pznd+"' and kjqj='"+kjqj+"' and sszt='"+sszt+"' and pzz='"+pzz+"'  and pzzt='00'  ";
		String pzbh = Validate.isNullToDefaultString(db.queryForObject(sql,String.class), "");
		//如果全部已复核，则查询最
		if(Validate.isNull(pzbh)){
			sql = "select min(pzbh) from cw_pzlrzb where pznd='"+pznd+"' and kjqj='"+kjqj+"' and sszt='"+sszt+"' and pzz='"+pzz+"'  and pzzt='01'  ";
		}
		pzbh = Validate.isNullToDefaultString(db.queryForObject(sql,String.class), "");
		if(Validate.isNull(pzbh)){
			sql = "select min(pzbh) from cw_pzlrzb where pznd='"+pznd+"' and kjqj='"+kjqj+"' and sszt='"+sszt+"' and pzz='"+pzz+"'  and pzzt='02'  ";
		}
		pzbh = Validate.isNullToDefaultString(db.queryForObject(sql,String.class), "");
		if(Validate.isNull(pzbh)){
			sql = "select min(pzbh) from cw_pzlrzb where pznd='"+pznd+"' and kjqj='"+kjqj+"' and sszt='"+sszt+"' and pzz='"+pzz+"'  and pzzt='99'  ";
		}
		return db.queryForObject(sql,String.class);
	}
	
	/**
	 * 根据主键id查询凭证是否存在
	 * @param guid
	 * @return
	 */
	public int selectPzInfo(String guid){
		String sql = "select count(0) from cw_pzlrzb where guid=?";
		return db.queryForObject(sql,new Object[]{guid},Integer.class);
	}
	
	/**
	 * 复制凭证信息
	 * @param guid
	 * @return
	 */
	public String insertCopyPzInfoByGuid(String guid,String pzrq,HttpSession session,String dqnd,String dqqj,String autoPzbh){
		//查询项目是否超标
		String msg = checkXmcbByCopyPz(guid, session);
		//查询还款是否超出借款
		msg = checkHkjeByCopy(guid, session, msg);
		if(Validate.noNull(msg)){
			return msg;
		}
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		Map map = new HashMap<String,Object>();
		String sql = "SELECT * FROM CW_PZLRZB WHERE GUID=?";
		map = db.queryForMap(sql,new Object[]{guid});
		if(map.isEmpty()){
			return null;
		}
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		//复制凭证主表
		String zbid = GenAutoKey.get32UUID();//生成主表的主键id
		String sszt = Validate.isNullToDefaultString(map.get("SSZT"),"");
		String pznd = Validate.isNullToDefaultString(map.get("PZND"),"");
		String kjqj = Validate.isNullToDefaultString(map.get("KJQJ"),"");
		String pzz = Validate.isNullToDefaultString(map.get("PZZ"),"");
		String pzlxmc = Validate.isNullToDefaultString(map.get("PZLXMC"),"");
		//复制主表
		StringBuffer insertZb = new StringBuffer();
		String dqzt =  Constant.getztid(session);
		String dqzd = CommonUtil.getKjzd(session);
		insertZb.append(" INSERT INTO CW_PZLRZB");
		insertZb.append(" (GUID,PZBH,PZRQ,FJZS,ZDR,JFJEHJ,DFJEHJ,PZZT,SSZT,KJQJ,PZLY,PZND,KJZD,PZZ,PZLXMC,pzscsj)");
		insertZb.append(" (");
		insertZb.append(" SELECT ? GUID,");
		insertZb.append(" ?,");
		insertZb.append(" to_date(?,'yyyy/mm/dd'),FJZS,?,JFJEHJ,DFJEHJ,?,?,?,PZLY,?,?,PZZ,?,pzscsj");
		insertZb.append(" FROM CW_PZLRZB WHERE GUID=?");
		insertZb.append(" )");
		sqlList.add(insertZb.toString());
		objList.add(new Object[]{zbid,autoPzbh,pzrq,LUser.getGuid(),"00",dqzt,dqqj,dqnd,dqzd,pzlxmc,guid});
		//复制附件信息
		StringBuffer insertFj = new StringBuffer();
		insertFj.append(" INSERT INTO ZC_XGWD(GUID,DJLX,DBH,FILENAME,RYBH,SCSJ,BZ,PATH,FILETYPE,FILECONTENT)");
		insertFj.append(" SELECT SYS_GUID(),DJLX,?,FILENAME,RYBH,SCSJ,BZ,PATH,FILETYPE,FILECONTENT FROM ZC_XGWD WHERE DBH=?");
		sqlList.add(insertFj.toString());
		objList.add(new Object[]{zbid,guid});
		//查询明细
		List mxList = new ArrayList<Map<String,Object>>();
		mxList = db.queryForList("SELECT * FROM CW_PZLRMXB WHERE PZBH=? ORDER BY INDEXNEW",new Object[]{guid});
		if(mxList!=null&&mxList.size()>0){
			String xmbh = "";
			String bmbh = "";
			for(int i=0,len=mxList.size();i<len;i++){
				Map info = (Map)mxList.get(i);
				//复制凭证明细
				StringBuffer insertMx = new StringBuffer();
				String mxid = GenAutoKey.get32UUID();
				insertMx.append(" INSERT INTO CW_PZLRMXB(GUID,PZBH,ZY,KMBH,JJFL,BMBH,XMBH,JDFX,JFJE,DFJE,CZR,CZRQ,SSZT,KJZD,INDEXNEW)");
				insertMx.append(" values(?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)");
				sqlList.add(insertMx.toString());
				objList.add(new Object[]{
						mxid,zbid,
						Validate.isNullToDefaultString(info.get("ZY"), ""),
						Validate.isNullToDefaultString(info.get("KMBH"), ""),
						Validate.isNullToDefaultString(info.get("JJFL"), ""),
						Validate.isNullToDefaultString(info.get("BMBH"), ""),
						Validate.isNullToDefaultString(info.get("XMBH"), ""),
						Validate.isNullToDefaultString(info.get("JDFX"), ""),
						Validate.isNullToDefaultString(info.get("JFJE"), ""),
						Validate.isNullToDefaultString(info.get("DFJE"), ""),
						LUser.getGuid(),
						Validate.isNullToDefaultString(info.get("SSZT"), ""),
						Validate.isNullToDefaultString(info.get("KJZD"), ""),
						Validate.isNullToDefaultString(info.get("INDEXNEW"), "")                                                                                                                      
				});
				//复制凭证展示表 
				StringBuffer insertZs = new StringBuffer();
				insertZs.append(" INSERT INTO CW_PZZSB(GUID,KMMC,ZCJJFLKM,KMYE,BMMC,XMMC,SRKM,ZCKM,XMYE,FZR,XMGKXXM,XMLX,BZ)");
				insertZs.append(" SELECT ?,KMMC,ZCJJFLKM,KMYE,BMMC,XMMC,SRKM,ZCKM,XMYE,FZR,XMGKXXM,XMLX,BZ FROM CW_PZZSB WHERE GUID=?");
				sqlList.add(insertZs.toString());
				objList.add(new Object[]{
						mxid,Validate.isNullToDefaultString(info.get("GUID"), "")                                                                                                                    
				});
				//复制辅助录入表 
//				String search = "select jsdh from CW_FZLRB where kmbh=?";
				StringBuffer insertFz = new StringBuffer();
				insertFz.append(" INSERT INTO CW_FZLRB(GUID,KMBH,WLDC,ZRR,WLDW,JSFS,JSDH,DFDW,YSLX,YSLY,BZ,CZR,CZRQ,DQSJ,gnkm,khyh,hm,zh)");
				insertFz.append(" SELECT SYS_GUID(),?,WLDC,ZRR,WLDW,JSFS,?,DFDW,YSLX,YSLY,BZ,?,SYSDATE,DQSJ,gnkm,khyh,hm,zh FROM CW_FZLRB WHERE KMBH=?");
				sqlList.add(insertFz.toString());
				objList.add(new Object[]{
						mxid,GenAutoKey.createKeyforth("cw_fzlrb","JS", "jsdh"),LUser.getGuid(),Validate.isNullToDefaultString(info.get("GUID"), "")                                                                                                                    
				});
			}
		}
//		boolean bool = false;
		boolean bool = db.batchUpdate(sqlList,objList);//执行保存
		if(bool){
			return "";
		}
		return "操作失败";
	}
	/**
	 * 复制凭证的时候验证项目金额是否超标
	 * @param pzbh
	 * @return
	 */
	public String checkXmcbByCopyPz(String pzbh,HttpSession session){
		String msg = "";
		String sql = "SELECT XMBH,BMBH,SUM(NVL(JFJE,0)) as LRJE FROM CW_PZLRMXB T " +
				"WHERE XMBH IS NOT NULL AND BMBH IS NOT NULL AND PZBH=? GROUP BY XMBH,BMBH";
		List list = new ArrayList<Map<String,Object>>();
		list = db.queryForList(sql,new Object[]{pzbh});
		if(list!=null&&list.size()>0){
			for(int i=0,len=list.size();i<len;i++){
				Map mapInfo = (Map)list.get(i);
				String xmbh = Validate.isNullToDefaultString(mapInfo.get("XMBH"),"");
				String bmbh = Validate.isNullToDefaultString(mapInfo.get("BMBH"),"");
				String lrje = Validate.isNullToDefaultString(mapInfo.get("LRJE"),"0");
				
				BigDecimal kyje = getXmkyje(xmbh, bmbh,session);	
				int co = new BigDecimal(lrje).compareTo(kyje);
				if(co>0) {
					msg += "部门编号:"+bmbh+"项目编号:"+xmbh;
				}
			}
		}
		if(Validate.noNull(msg)){
			msg = msg+",已超出目前项目的可用金额";
		}
		return msg;
	}
	
	/**
	 * 判断还款金额是否超出借款金额
	 * @param pzbh
	 * @param session
	 * @param msg
	 * @return
	 */
	public String checkHkjeByCopy(String pzbh,HttpSession session,String msg){
		String sql = "SELECT DISTINCT WLDC AS WLDC FROM CW_FZLRB T WHERE KMBH IN(SELECT GUID FROM CW_PZLRMXB WHERE PZBH=?) AND WLDC IS NOT NULL GROUP BY WLDC";
		List list = new ArrayList<Map<String,String>>();
		list = db.queryForList(sql,new Object[]{pzbh});
		boolean bool = false;
		if(list!=null&&list.size()>0){
			for(int i=0,len=list.size();i<len;i++){
				Map map = (Map)list.get(i);
				String wldc = Validate.isNullToDefaultString(map.get("WLDC"), "");
				String sqlJk = "SELECT ABS(NVL(JFJE,0))+ABS(NVL(DFJE,0)) FROM CW_PZLRMXB " +
						"WHERE GUID=(SELECT KMBH FROM CW_FZLRB T WHERE ZCLX=? AND ZCLX IS NOT NULL)";
				String jkje = Validate.isNullToDefaultString(db.queryForSingleValue(sqlJk,new Object[]{wldc}),"0");//借款金额
				String sqlHk = "SELECT SUM(ABS(NVL(JFJE,0)))+SUM(ABS(NVL(DFJE,0))) FROM CW_PZLRMXB " +
						"WHERE GUID IN(SELECT KMBH FROM CW_FZLRB T WHERE WLDC=? AND WLDC IS NOT NULL)";
				String hkje = Validate.isNullToDefaultString(db.queryForSingleValue(sqlHk,new Object[]{wldc}),"0");//还款金额
				String mSql = "SELECT SUM(ABS(NVL(JFJE,0))+ABS(NVL(DFJE,0)))MONEY FROM CW_PZLRMXB " +
						"WHERE GUID IN(SELECT KMBH FROM CW_FZLRB T WHERE WLDC=? AND WLDC IS NOT NULL) AND PZBH=?";
				String money = Validate.isNullToDefaultString(db.queryForSingleValue(mSql,new Object[]{wldc,pzbh}),"0");//本次还款金额
				BigDecimal zjkje = new BigDecimal(jkje);//总借款金额
				BigDecimal yhkje = new BigDecimal(hkje);//已经还款金额
				BigDecimal zhkje = new BigDecimal(money);//准备还款金额
				if((zhkje.add(yhkje)).compareTo(zjkje)>0){
					msg += "借款单号:"+wldc;
					bool = true;
				}
			}
		}
		if(bool){
			msg = msg+",还款总金额超出借款总金额";
		}
		return msg;
	}
	/**
	 * 查询凭证类型
	 * @param guid
	 * @return
	 */
	public String getPzzByGuid(String guid){
		String sql = "SELECT PZZ FROM CW_PZLRZB WHERE GUID=?";
		return db.queryForObject(sql, new Object[]{guid},String.class);
	}
	
	/**
	 * 获取常用摘要的kmbh
	 * @param session
	 * @param zy
	 * @return
	 */
	public String getKmbhZy(HttpSession session,String zy){
		String sszt =  Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);
		String sql = "SELECT KMBH FROM CW_CYZYSZB WHERE ZYNR=? AND SSZT=? AND KJZD=? AND SFQY=? AND ROWNUM=1";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql, new Object[]{zy.trim(),sszt,kjzd,"1"}),"");
	}
	
	
	/**
	 * 复制凭证信息存为模板
	 * @param guid
	 * @return
	 */
	public boolean insertCopyPzInfoByGuidToMb(String guid,String param){
		Map map = new HashMap<String,Object>();
		String sql = "SELECT * FROM CW_PZLRZB WHERE GUID=?";
		map = db.queryForMap(sql,new Object[]{guid});
		if(map.isEmpty()){
			return false;
		}
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		//复制凭证主表
		String zbid = GenAutoKey.get32UUID();//生成主表的主键id
		String mbbh = GenAutoKey.makeCkbh("Cw_pzmbzb", "mbbh", "2");
		//复制主表
		StringBuffer insertZb = new StringBuffer();
		if("yes".equals(param)){
			insertZb.append(" INSERT INTO cw_pzmbzb");
			insertZb.append(" (GUID,MBBH,MBNR,BZ,CZR,CZRQ,U1,JFJEHJ,DFJEHJ,PZZ,PZLXMC,SSZT)");
			insertZb.append(" (");
			insertZb.append(" SELECT ? GUID,? mbbh,");
			insertZb.append(" '凭证保存模板"+mbbh+"','凭证保存模板',?,sysdate,?,jfjehj,dfjehj,pzz,pzlxmc,sszt");
			insertZb.append(" FROM CW_PZLRZB WHERE GUID=?");
			insertZb.append(" )");
			sqlList.add(insertZb.toString());
			objList.add(new Object[]{zbid,mbbh,LUser.getGuid(),"yes",guid});
		}else{
			insertZb.append(" INSERT INTO cw_pzmbzb");
			insertZb.append(" (GUID,MBBH,MBNR,BZ,CZR,CZRQ,U1,PZZ,PZLXMC,SSZT)");
			insertZb.append(" (");
			insertZb.append(" SELECT ? GUID,? mbbh,");
			insertZb.append(" '凭证保存模板"+mbbh+"','凭证保存模板',?,sysdate,?,pzz,pzlxmc,sszt");
			insertZb.append(" FROM CW_PZLRZB WHERE GUID=?");
			insertZb.append(" )");
			sqlList.add(insertZb.toString());
			objList.add(new Object[]{zbid,mbbh,LUser.getGuid(),"yes",guid});
		}
		//查询明细
		List mxList = new ArrayList<Map<String,Object>>();
		mxList = db.queryForList("SELECT * FROM CW_PZLRMXB WHERE PZBH=? ORDER BY INDEXNEW",new Object[]{guid});
		if(mxList!=null&&mxList.size()>0){
			for(int i=0,len=mxList.size();i<len;i++){
				Map info = (Map)mxList.get(i);
				//复制凭证明细
				StringBuffer insertMx = new StringBuffer();
				String mxid = GenAutoKey.get32UUID();
				insertMx.append(" INSERT INTO CW_PZMBMXB(guid,mbbh,kmbh,czr,czrq,jdfx,jfje,dfje,jjfl,bmbh,xmbh,zy,indexnew)");
				insertMx.append(" values(sys_guid(),?,?,?,sysdate,?,?,?,?,?,?,?,?)");
				sqlList.add(insertMx.toString());
				objList.add(new Object[]{
						zbid,
						Validate.isNullToDefaultString(info.get("KMBH"), ""),
						LUser.getGuid(),
						Validate.isNullToDefaultString(info.get("JDFX"), ""),
						"yes".equals(param)?Validate.isNullToDefaultString(info.get("JFJE"), ""):"",
						"yes".equals(param)?Validate.isNullToDefaultString(info.get("DFJE"), ""):"",
						Validate.isNullToDefaultString(info.get("JJFL"), ""),
						Validate.isNullToDefaultString(info.get("BMBH"), ""),
						Validate.isNullToDefaultString(info.get("XMBH"), ""),
						Validate.isNullToDefaultString(info.get("ZY"), ""),
						Validate.isNullToDefaultString(info.get("INDEXNEW"), "")                                                                                                                      
				});
//				//复制凭证展示表 
//				StringBuffer insertZs = new StringBuffer();
//				insertZs.append(" INSERT INTO CW_PZZSBBYMB(GUID,KMMC,ZCJJFLKM,KMYE,BMMC,XMMC,SRKM,ZCKM,XMYE,FZR,XMGKXXM,XMLX,BZ)");
//				insertZs.append(" SELECT ?,KMMC,ZCJJFLKM,KMYE,BMMC,XMMC,SRKM,ZCKM,XMYE,FZR,XMGKXXM,XMLX,BZ FROM CW_PZZSB WHERE GUID=?");
//				sqlList.add(insertZs.toString());
//				objList.add(new Object[]{
//						mxid,Validate.isNullToDefaultString(info.get("GUID"), "")                                                                                                                    
//				});
//				//复制辅助录入表 
//				StringBuffer insertFz = new StringBuffer();
//				insertFz.append(" INSERT INTO CW_FZLRBBYMB(GUID,KMBH,WLDC,ZRR,WLDW,JSFS,JSDH,DFDW,YSLX,ZCLX,YSLY,BZ,CZR,CZRQ,DQSJ)");
//				insertFz.append(" SELECT SYS_GUID(),?,WLDC,ZRR,WLDW,JSFS,JSDH,DFDW,YSLX,ZCLX,YSLY,BZ,?,SYSDATE,DQSJ FROM CW_FZLRB WHERE KMBH=?");
//				sqlList.add(insertFz.toString());
//				objList.add(new Object[]{
//						mxid,LUser.getGuid(),Validate.isNullToDefaultString(info.get("GUID"), "")                                                                                                                    
//				});
			}
		}
		boolean bool = db.batchUpdate(sqlList,objList);//执行保存
		
		return bool;
	}
	
	/**
	 * 验证科目与辅助信息的关系
	 * @param session
	 * @param kmbh
	 * @return
	 */
	public Map getKmFzInfoByKmbh(HttpSession session,String kmbh){
		String sszt = Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);
		String sql = "SELECT WLDWFZ,GRFZ,GUID,SFGNFLKM FROM CW_KJKMSZB WHERE SSZT=? AND KJZD=? AND KMBH=?";
		Map map = new HashMap<String,Object>();
		map = db.queryForMap(sql,new Object[]{sszt,kjzd,kmbh});
		return map;
	}
	/**
	 * 得到会计制度信息
	 */
	public List getzd() {
		String sql = "SELECT GUID,ZDM FROM CW_KJZDQY_ZDB";
		return db.queryForList(sql);
	}
	
	/**
	 * 根据bmbh和xmbh查询项目id
	 * @param bmbh
	 * @param xmbh
	 * @return
	 */
	public String getXmIdByBmbhAndXmbh(String bmbh,String xmbh){
		String sql = "SELECT GUID FROM CW_XMJBXXB WHERE XMBH=? AND BMBH=? AND ROWNUM=1";
		return db.queryForObject(sql, new Object[]{xmbh,bmbh},String.class);
	}
	
	/**
	 * 项目使用金额
	 * @param xmbh
	 * @param bmbh
	 * @param xmid
	 * @return
	 */
	public String getMoneyByXm(String xmbh,String bmbh,String xmid,HttpSession session){
		StringBuffer sql = new StringBuffer();
		//项目金额合计
		sql.append(" SELECT NVL(SUM(NVL(MONEY,0)),0) FROM(");
		//日常报销的项目花费
		sql.append(" SELECT SUM(NVL(T.BCBXJE,0))MONEY FROM CW_RCBXXMMXB T WHERE T.XMGUID=? GROUP BY XMGUID");
		//差旅报销的项目金额
		sql.append(" UNION ALL");
		sql.append(" SELECT SUM(NVL(T.BCBXJE,0))MONEY FROM CW_CCSQSPB_XM T WHERE T.JFBH=? GROUP BY JFBH");
		//凭证录入的项目金额
		sql.append(" UNION ALL");
		sql.append(" SELECT (NVL(SUM(ABS(NVL(JFJE,0))),0)+NVL(SUM(ABS(NVL(DFJE,0))),0))MONEY FROM CW_PZLRMXB T");
		sql.append(" LEFT JOIN CW_PZLRZB Z ON Z.GUID=T.PZBH");
		sql.append(" WHERE BMBH=? AND XMBH=? AND BMBH IS NOT NULL AND XMBH IS NOT NULL  AND Z.PZBH<>?");
		sql.append(")");
		String money = Validate.isNullToDefaultString(db.queryForSingleValue(sql.toString(),new Object[]{
				xmid,xmid,bmbh,xmbh,session.getAttribute("pzbh")
			}),"0");
		return money;
	}
	
	/**
	 * 获取项目 可用金额
	 */
	public BigDecimal getXmkyje(String xmbh,String bmbh,HttpSession session){
		String sql = " select t.guid from CW_XMJBXXB t where  t.xmbh= ? and bmbh=? ";
		String xmguid = db.queryForSingleValue(sql,new Object[]{xmbh,bmbh});
		BigDecimal kyje = CommonUtil.getXmkyje(xmguid,session);
		return kyje;
	}
	
	/**
	 * 单据是否必传
	 * @param guid
	 * @return
	 */
	public boolean checkDj(String guid){
		String dj = "SELECT PZDJSFBC FROM CW_ZDSCPZ WHERE ROWNUM=1";
		dj = Validate.isNullToDefaultString(db.queryForSingleValue(dj),"");
		String sql = "SELECT COUNT(*) FROM ZC_XGWD WHERE DBH=?";
		int i = Integer.valueOf(db.queryForSingleValue(sql,new Object[]{guid}));
		if("Y".equalsIgnoreCase(dj)&&i<=0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 查询开户银行信息
	 * @param type
	 * @param hm
	 * @return
	 */
	public List getKhyh(String type,String hm){
		String sql = "";
		if(Validate.noNull(hm)&&hm.indexOf(")")>0){
			hm = hm.substring(1,hm.indexOf(")"));
		}
		if("zrr".equals(type)){
			sql = "select guid,khyh,khyhzh from CW_JSYHZHB t where jsbh=(select guid from gx_sys_ryb where rybh=?)";
		}else{
			sql = "select  guid,khyh,khyhzh from cw_wldwmxb t where wlbh=(select guid from cw_wldwb where wlbh=?)";
		}
		List list = db.queryForList(sql,new Object[]{hm});
		return list;
	}
	
	/**
	 * 结算单号
	 * @param type
	 * @param hm
	 * @return
	 */
	public List getJsdh(String type,String hm,String pzid){
		StringBuffer sql = new StringBuffer();
		if(Validate.noNull(hm)&&hm.indexOf(")")>0){
			hm = hm.substring(1,hm.indexOf(")"));
		}
		List<Map<String, Object>> list = new  ArrayList<Map<String,Object>>();
		if("zrr".equals(type)){
			String jsdhSql = "select zclx,(sum(abs(nvl(m.jfje,0)))+sum(abs(nvl(m.dfje,0))))money " +
							"from cw_fzlrb t left join cw_pzlrmxb m on m.guid=t.kmbh where zrr=? and zclx is not null group by zclx";
			list = db.queryForList(jsdhSql,new Object[]{hm});
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					String zclx = Validate.isNullToDefaultString(map.get("ZCLX"),"");
					String money = Validate.isNullToDefaultString(map.get("MONEY"),"0");
					String je = getMoneyWldc(hm, zclx, "zrr",pzid);
					if(Double.parseDouble(money)<=Double.parseDouble(je)){
						list.remove(i);
					}
				}
			}
		}else{
			String jsdhSql = "select zclx,(sum(abs(nvl(m.jfje,0)))+sum(abs(nvl(m.dfje,0))))money " +
							"from cw_fzlrb t left join cw_pzlrmxb m on m.guid=t.kmbh where dfdw=? and zclx is not null group by zclx";
			list = db.queryForList(jsdhSql,new Object[]{hm});
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					String zclx = Validate.isNullToDefaultString(map.get("ZCLX"),"");
					String money = Validate.isNullToDefaultString(map.get("MONEY"),"0");
					String je = getMoneyWldc(hm, zclx, "dfdw",pzid);
					if(Double.parseDouble(money)<=Double.parseDouble(je)){
						list.remove(i);
					}
				}
			}
		}
//		List list = db.queryForList(sql.toString(),new Object[]{WindowUtil.getText(hm)});
		return list;
	}
	public String getMoneyWldc(String hm,String zclx,String colName,String pzid){
		String sql = "select sum(abs(nvl(m.jfje,0)))+sum(abs(nvl(m.dfje,0))) from cw_pzlrmxb m " +
				"where guid in(select kmbh from cw_fzlrb where "+colName+"=? and wldc=? and wldc is not null)";
		if(Validate.noNull(pzid)&&!"undefined".equals(pzid)){
			sql+=" and pzbh<>'"+pzid+"'";
		}
		String je = Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{hm,zclx}),"0");
		return je;
	}
	/**
	 * 检查还款金额是否超出借款金额
	 * @param wldc
	 * @param money
	 * @return
	 */
	public boolean checkWldc(String wldc,String money,String pzid){
		String sqlJk = "select abs(nvl(jfje,0))+abs(nvl(dfje,0)) from cw_pzlrmxb " +
				"where guid=(select kmbh from cw_fzlrb t where ZCLX=? and ZCLX is not null) and pzbh<>?";
		String jkje = Validate.isNullToDefaultString(db.queryForSingleValue(sqlJk,new Object[]{wldc,pzid}),"0");
		String sqlHk = "select sum(abs(nvl(jfje,0)))+sum(abs(nvl(dfje,0))) from cw_pzlrmxb " +
				"where guid in(select kmbh from cw_fzlrb t where wldc=? and wldc is not null) and pzbh<>?";
		String hkje = Validate.isNullToDefaultString(db.queryForSingleValue(sqlHk,new Object[]{wldc,pzid}),"0");
		BigDecimal zjkje = new BigDecimal(jkje);//总借款金额
		BigDecimal yhkje = new BigDecimal(hkje);//已经还款金额
		BigDecimal zhkje = new BigDecimal(money);//准备还款金额
		int i = (yhkje.add(zhkje)).compareTo(zjkje);
		if(i>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 检查借款单号
	 * @param zclx
	 * @param pzid
	 * @return
	 */
	public boolean checkJkdh(String zclx,String pzid){
		String guid = db.queryForSingleValue("select wm_concat(guid) from cw_pzlrmxb where pzbh=?",new Object[]{pzid});
		String sql = "select count(0) from cw_fzlrb where zclx=?";
		if(Validate.noNull(guid)){
			sql += "and kmbh not in('"+guid.replace(",", "','")+"')";
		}
		int m = db.queryForInt(sql,new Object[]{zclx});
		if(m>0){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		BigDecimal zjkje = new BigDecimal("100");//总借款金额
		BigDecimal yhkje = new BigDecimal("50");//已经还款金额
		BigDecimal zhkje = new BigDecimal("500");//准备还款金额
		int i = yhkje.add(zhkje).compareTo(zjkje);
		System.err.println(i);
	}
	/**
	 *验证经济科目是否末级
	 * @param 
	 * @return
	 */
	public Map<String, Object> getObjectByIdSfmj(String kmbh) {
		String sql="select count(*) as sfmj from cw_jjkmb p where k= ?";
		return db.queryForMap(sql,new Object[]{kmbh});
	}
	
	/**
	 * 查询银行录入明细信息
	 * @param zbid
	 * @param mxid
	 * @return
	 */
	public List getYhmxIdByZBidAndMxId(String zbid,String mxid){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" GUID,ZBID,MXID,XM,YHZH,YHMC,YHLHH,");
		sql.append(" DECODE(NVL(JE,0),0,'0.00',TO_CHAR(ROUND(JE,2),'FM999999999999999999.00'))JE,");
		sql.append(" DECODE(STATUS,'0','未支付','1','已支付','')STATUS");
		sql.append(" FROM CW_PZLRYHMX T");
		sql.append(" WHERE T.ZBID=? AND T.MXID=?");
		List list = new ArrayList<Map<String,Object>>();
		list = db.queryForList(sql.toString(),new Object[]{zbid,mxid});
//		if(list.size()==0){
//			StringBuffer sqls = new StringBuffer();
//			sqls.append("select * from(");
//			sqls.append(" select to_char(w.dwmc) xm,mx.khyh yhmc,mx.khyhzh yhzh from cw_dgzfb t left join cw_wldwb w on t.dfdw=w.guid left join cw_wldwmxb mx on mx.wlbh=w.guid where t.zfdh=?");
//			sqls.append(" union all");
//			sqls.append(" select to_char(r.xm) xm,yh.khyh yhmc,yh.khyhzh yhzh from cw_dszfb t left join gx_sys_ryb r on r.rybh=t.ryxm left join cw_jsyhzhb yh on yh.guid=t.zhbguid where t.zfdh=?");
//			sqls.append(")");
//			list = db.queryForList(sqls.toString(),new Object[]{zbid,zbid});
//		}
		return list;
	}
	
	/**
	 * 查询银行录入明细信息
	 * @param zbid
	 * @param mxid
	 * @return
	 */
	public List getYhmxIdByZBidAndMxIdView(String zbid,String mxid){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" GUID,ZBID,MXID,XM,YHZH,nvl(YHMC,(select a.khyh from cw_rcbxzb x left join CW_DSZFB s on s.zfdh = x.guid left join cw_jsyhzhb a ON s.ZHBGUID=a.guid where x.guid=t.zbid)) yhmc,yhlhh,");
		sql.append(" DECODE(NVL(JE,0),0,0.00,TO_CHAR(ROUND(JE,2),'FM999999999999999999.00'))JE,");
		sql.append(" DECODE(STATUS,'0','未支付','1','已支付','')STATUS");
		sql.append(" FROM CW_PZLRYHMX T");
		sql.append(" WHERE T.ZBID='"+zbid+"'");
		if(Validate.noNull(mxid)||!"undefined".equals(mxid)){
			sql.append(" AND MXID='"+mxid+"'");
		}
		List list = new ArrayList<Map<String,Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	
	/**
	 * 导出 银行录入明细信息
	 * @param zbid
	 * @param mxid
	 * @return
	 */
	public List getYhmxIdByZBidAndMxIdByExcel(String zbid,String mxid){
		StringBuffer sql = new StringBuffer();
		List list = new ArrayList<Map<String,Object>>();
		sql.append(" SELECT");
		sql.append(" GUID,ZBID,MXID,XM,YHZH,YHMC,yhlhh,");
		sql.append(" DECODE(NVL(JE,0),0,0.00,TO_CHAR(ROUND(JE,2),'FM999999999999999999.00'))JE,");
		sql.append(" DECODE(STATUS,'0','未支付','1','已支付','')STATUS");
		sql.append(" FROM CW_PZLRYHMX T");
		sql.append(" WHERE T.ZBID=?");
		if(mxid.equals("")||mxid.equals("undefined")) {
			list = db.queryForList(sql.toString(),new Object[]{zbid});
		}else {
			sql.append(" AND T.MXID=?");
			list = db.queryForList(sql.toString(),new Object[]{zbid,mxid});
		}
		return list;
	}
	
	/**
	 * 删除银行明细
	 * @param zbid
	 * @param mxid
	 */
	public int deleteYhmxByZbidAndMxId(String zbid,String mxid){
		String sql = "DELETE FROM CW_PZLRYHMX WHERE ZBID=? AND MXID=?";
		int i = db.update(sql,new Object[]{zbid,mxid});
		return i;
	}
	
	/**
	 * 保存银行明细
	 * @param map
	 * @param zbid
	 * @param mxid
	 * @return
	 */
	public int saveYhmx(Map map,String zbid,String mxid){
		int m = 0;
		if(Validate.isNull(map.get("xm"))&&Validate.isNull(map.get("yhzh"))&&Validate.isNull(map.get("yhmc"))&&Validate.isNull(map.get("yhmc"))){
			return m;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO CW_PZLRYHMX(GUID,ZBID,MXID,XM,YHZH,YHMC,STATUS,JE,yhlhh)");
		sql.append("VALUES(SYS_GUID(),?,?,?,?,?,?,?,?)");
		m = db.update(sql.toString(),new Object[]{
			zbid,mxid,
			Validate.isNullToDefaultString(map.get("xm"),""),
			Validate.isNullToDefaultString(map.get("yhzh"),""),
			Validate.isNullToDefaultString(map.get("yhmc"),""),
			"0",
			Validate.isNullToDefaultString(map.get("je"),""),
			Validate.isNullToDefaultString(map.get("yhlhh"),""),
		});
		return m;
	}
	
	/**
	 * 导入
	 * @param file
	 * @return
	 */
	public List insertJcsj(String file,String zbid,String mxid) {
		Workbook rwb;
		List list = new ArrayList();
		List sqlList = new ArrayList();
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			int j = 0;
//			String del = "delete from CW_PZLRYHMX where zbid='"+zbid+"' and mxid='"+mxid+"'";
//			sqlList.add(del);
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				j=0;
				Map map = new HashMap();
				String xm = rs.getCell(j++, i).getContents();
				String yhmc = rs.getCell(j++, i).getContents();
				String yhzh = rs.getCell(j++, i).getContents();
				String je = rs.getCell(j++, i).getContents();
				map.put("yhzh", yhzh);
				map.put("xm", xm);
				map.put("yhmc", yhmc);
				map.put("je", je);
				list.add(map);
				StringBuffer sql = new StringBuffer();
				sql.append("INSERT INTO CW_PZLRYHMX(GUID,ZBID,MXID,XM,YHZH,YHMC,STATUS,JE)");
				sql.append("VALUES(SYS_GUID(),'"+zbid+"','"+mxid+"','"+xm+"','"+yhzh+"','"+yhmc+"','0','"+je+"')");
				sqlList.add(sql.toString());
			}
			db.batchUpdate(sqlList);
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public boolean docheck(String rylx, String gzyf, String pzlx, String kjzd, String sszt) {
		// TODO Auto-generated method stub
		return db.queryForSingleValue("select count(1) from cw_pzlrzb where gzyf=? and pzz=? and rylx=? and kjzd=? and sszt=?", new Object[]{gzyf, pzlx, rylx, kjzd, sszt}).equals("0") ;
	}
	public boolean docheck2(String gzyf, String tablename) {
		// TODO Auto-generated method stub
		return db.queryForSingleValue("select count(1) from " + tablename + " where gzyf=? and shzt='2'", new Object[]{gzyf}).equals("0") ;
	}
	public boolean doUpdateOfPrint(String guid) {
		// TODO Auto-generated method stub
		List<String> sqls = new ArrayList<String>();
		List<Object[]> pars = new ArrayList<Object[]>();
		String[] gid = guid.split(",");
		for(int i=0; i<gid.length; i++){
			sqls.add("update cw_pzlrzb set sfdy='1' where guid=?");
			pars.add(new Object[]{guid});
		}
		return db.batchUpdate(sqls, pars);
	}
	public boolean dosave(Xtxx obj) {
		// TODO Auto-generated method stub
		return db.update("insert into CW_XTXX(guid,fbr,fbsj,jsr,sjid,xxnr)values(sys_guid(),?,sysdate,?,?,?)", new Object[]{obj.getFbr(),obj.getJsr(),obj.getSjid(),obj.getXxnr()})>0;
	}
	public boolean docheck(String pzbh, String pzlx, String kjzd, String sszt) {
		// TODO Auto-generated method stub
		return db.queryForSingleValue("select count(1) from cw_pzlrzb where pzbh=? and pzz=? and kjzd=? and sszt=? and (pznd,kjqj) in(select * from (select ztnd,kjqj from cw_qmjzb where sszt= ? and sfjz = '0' order by ztnd,to_number(kjqj)) where rownum <= 1) ", new Object[]{pzbh, pzlx, kjzd, sszt, sszt}).equals("1") ;
	}
	/**
	 *
	 * 判断 是否是  报销凭证生成
	 * @param guid
	 * @return
	 */
	public int doCheckBxpz(String guid) {
		String sql = "select count(*) from CW_PZLRBXDZB where pzid = '"+guid +"'";
		int b = Integer.parseInt(db.queryForSingleValue(sql));
		return b;
	}
	/**
	 * 获取报销类型
	 * @param bxid
	 * @return
	 */
	public Map getBxlx(String bxid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select t.djbh,'rcbx' as bxlx  from cw_rcbxzb t where t.guid =? ");
		sql.append("union select t.djbh,'clfbx' as bxlx  from cw_clfbxzb t where t.guid=? ");
		sql.append(" union select t.djbh,'gwjdbx' as bxlx  from cw_gwjdfbxzb t where  t.guid =? ");
		sql.append(" union select t.djbh,'jkbx' as bxlx  from CW_JKYWB t where t.gid = ? ) a ");
		String sqltext = sql.toString();
		Object[] obj = new Object[]{bxid,bxid,bxid,bxid};
		return db.queryForMap(sqltext, obj);
	}
	/** 
	 * 获取  CW_PZLRBXDZB中的bxid  type
	 * @param guid
	 * @return
	 */
	public Map getBxpzxx(String guid) {
		String sql = " select bxid,bxtype from CW_PZLRBXDZB where pzid = '"+guid+"'";
		return db.queryForMap(sql);
	}
	/**
	 * 修改差旅费报销凭证 状态
	 * GX_SYS_DMB t where zl = 'lcsh' dm = '80' 凭证退回 
	 * @param bxid
	 */
	public int updateCLbxpz(String bxid) {
		String sql = " update CW_CLFBXZB set shzt='80' where guid ='"+ bxid+"'";
	    return db.update(sql);
	}
	/**
	 * 修改 公务接待 报销凭证 状态
	 * @param bxid
	 */
	public int updateJDbxpz(String bxid) {
		String sql = " update cw_gwjdfbxzb set shzt='80' where guid ='"+ bxid+"'";
		return db.update(sql);
	}
	/**
	 * 修改  日常 报销凭证 状态
	 * @param bxid
	 */
	public int updateRCbxpz(String bxid) {
		String sql = " update cw_rcbxzb  set shzt='80' where guid ='"+ bxid+"'";
		return db.update(sql);	
	}
	/**
	 * 修改 借款 报销凭证 状态
	 * @param bxid
	 */
	public int updateJKbxpz(String bxid) {
		String sql = " update CW_JKYWB set shzt='80'  where gid ='"+ bxid+"'";
		return db.update(sql);
	}
	/**
	 * 修改 收入报销凭证 状态
	 * @param bxid
	 */
	public int updateSRbxpz(String bxid) {
		String sql = " update CW_GRSRZB set shzt='80'  where gid ='"+ bxid+"'";
		return db.update(sql);
	}
	/**
	 *  获取当前分录 的银行 明细 的  总金额
	 */
	public String getSumMxje(String mxid) {
		String sql = " select sum(je) from CW_PZLRYHMX t where mxid = '"+ mxid+"'";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 删除因为删除明细而造成cw_pzlryhmx表中的多余数据
	 * @param zbguid
	 * @param mxids
	 * @return
	 */
	public int deleteYhmxbyBtn(String zbguid, String mxids) {
		String sql = " delete CW_PZLRYHMX where zbid = '"+zbguid+"' and mxid in ('"+ ToSqlUtil.pointToSql(mxids) +"')";
		return db.update(sql);
	}
	/**
	 * 获取 这个科目  的 余额方向
	 * @return
	 */
	public String getKmYefx(String kmbh) {
		String sql = " select yefx from CW_KJKMSZB where kmbh = '"+ kmbh +"' ";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 生成凭证更新业务表状态为9
	 * @param bxid
	 */
	public void inertClfbxb(String bxid) {
		String sql = "update cw_clfbxzb set shzt = '9' where guid=?";
		db.update(sql,new Object[] {bxid});
	}
	/**
	 * 生成凭证更新业务表状态为9
	 * @param bxid
	 */
	public void inertGwjdbxb(String bxid) {
		String sql = "update cw_gwjdfbxzb  set shzt = '9' where guid=?";
		db.update(sql,new Object[] {bxid});
	}
	/**
	 * 生成凭证更新业务表状态为9
	 * @param bxid
	 */
	public void inertRxbxb(String bxid) {
		String sql = "update cw_rcbxzb   set shzt = '9' where guid=?";
		db.update(sql,new Object[] {bxid});
	}
	/**
	 * 生成凭证更新业务表状态为9
	 * @param bxid
	 */
	public void inertSrsbb(String bxid) {
		String sql = "update cw_grsrzb   set shzt = '9' where guid=?";
		db.update(sql,new Object[] {bxid});
	}
	/**
	 * 生成凭证更新业务表状态为9
	 * @param bxid
	 */
	public void inertJkbxb(String bxid) {
		String sql = "update cw_jkywb    set shzt = '9' where gid=?";
		db.update(sql,new Object[] {bxid});
	}
	/**
	 * 更新科研管理费状态为已生成凭证
	 * @author BiMing
	 * @Time 2018年10月13日下午3:58:26
	 */
	public void inertGlf(String bxid) {
		String sql = "update cw_kyglf  set zt = '1' where guid=?";
		db.update(sql,new Object[] {bxid});
	}
	/**
	 * 导入管理费
	 * @author BiMing
	 * @Time 2018年10月11日下午4:16:43
	 */
	public String insertGlf(String file) {
		Workbook rwb;
		List list = new ArrayList();
		List sqlList = new ArrayList();
		StringBuffer err = new StringBuffer();
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			int j = 0;
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				j=0;
				Map map = new HashMap();
				String xmbh = rs.getCell(j++, i).getContents();
				String xmlx = rs.getCell(j++, i).getContents();
				String fzr = rs.getCell(j++, i).getContents();
				String szbm = rs.getCell(j++, i).getContents();
				String xyjj = rs.getCell(j++, i).getContents();
				String xxjj = rs.getCell(j++, i).getContents();
				map.put("xmbh", xmbh);
				map.put("xmlx", xmlx);
				map.put("fzr", fzr);
				map.put("szbm", szbm);
				map.put("xxjj", xxjj);
				map.put("xyjj", xyjj);
				list.add(map);
				StringBuffer sql = new StringBuffer();
				if(this.checkxm(xmbh,fzr,szbm)){
					if(!this.checkgl(xmbh,fzr,szbm)){
						sql.append("INSERT INTO CW_KYGLF(GUID,xmbh,xmlx,fzr,szbm,xyjj,xxjj,xmguid)");
						sql.append("VALUES(SYS_GUID(),'"+xmbh+"','"+xmlx+"','"+fzr+"','"+szbm+"','"+xyjj+"','"+xxjj+"',");
						sql.append(" (select guid from cw_xmjbxxb where xmbh='"+xmbh+"' and bmbh='"+szbm+"' and fzr = '"+fzr+"'))");
						sqlList.add(sql.toString());
					}else{
						err.append("第"+(i+1)+"行数据导入失败！项目编号已经导入，请核对后重新导入！");
					}
				}else{
					err.append("第"+(i+1)+"行数据导入失败！项目编号不存在，请核对后重新导入！");
				}
				
				
			}
			db.batchUpdate(sqlList);
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return err.toString();
	}
	/**
	 * 判断项目表里是否存在此项目
	 * @author 作者：PR
	 * @date 2018-10-14  下午5:47:54
	 */
	public boolean checkxm(String xmbh,String fzr,String bmbh){
		boolean flag = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from cw_xmjbxxb where xmbh='"+xmbh+"' and fzr='"+fzr+"' and bmbh='"+bmbh+"' ");
		String a = db.queryForSingleValue(sql.toString());
		if(Integer.parseInt(a)>0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断管理费表里是否存在此项目
	 * @author 作者：PR
	 * @date 2018-10-14  下午5:47:57
	 */
	public boolean checkgl(String xmbh,String fzr,String bmbh){
		boolean flag = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from CW_KYGLF where xmbh='"+xmbh+"' and fzr='"+fzr+"' and szbm='"+bmbh+"' ");
		String a = db.queryForSingleValue(sql.toString());
		if(Integer.parseInt(a)>0){
			flag = true;
		}
		return flag;
	}
	/**
	 * 增加管理费
	 * @author BiMing
	 * @Time 2018年10月12日下午5:27:54
	 */
	public int insertGlf(List<Map<String,String>> list) {
		List sqlList = new ArrayList<>();
		List objList = new ArrayList<>();
		for(Map map:list) {
			String sql = "INSERT INTO CW_KYGLF(GUID,xmbh,xmlx,fzr,szbm,xyjj,xxjj,xmguid)values(sys_guid(),?,?,?,?,?,?,?)";
			Object[] obj = new Object[] {
					map.get("xmbh"),
					map.get("xmlx"),
					map.get("fzr"),
					map.get("bm"),
					map.get("xyjj"),
					map.get("xxjj"),
					map.get("xmguid"),
			};
			sqlList.add(sql);
			objList.add(obj);
		}
		boolean  b = db.batchUpdate(sqlList,objList);
		return 1;
	}
	/**
	 * 增加管理费
	 * @author BiMing
	 * @Time 2018年10月12日下午5:27:54
	 */
	public int DeleteKyglxm(String guids) {
		int result =  db.update( "DELETE CW_KYGLF WHERE guid in ('"+guids+"')");
		return result;
	}
	/**
	 * 查询 导出 的 管理费
	 */
	public List<Map<String, Object>> getListForKyglxm(String sql) {
		return db.queryForList(sql);
	}
	/**
	 * 检查 是否 =已 增加  了项目
	 * @author BiMing
	 * @Time 2018年10月12日下午5:14:56
	 */
	public int CheckKyglxm(String xmguids) {
		String sql = "select count(*) from CW_KYGLF where xmguid in ('"+xmguids+"')";
		int n =  Integer.valueOf(db.queryForSingleValue(sql));
		if(n>0){
			return 1;
		}else{
			return -1;
		}
	}
	
}
