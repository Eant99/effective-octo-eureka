package com.googosoft.dao.kjhs.pzxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.wsbx.gwjdfbx.GwjdfbxsqDao;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Repository
public class PzdyDao extends BaseDao{
	
	private Logger logger = Logger.getLogger(GwjdfbxsqDao.class);
		
	/**
	 * 获取交互数据
	 * */
	public Map<String, Object> getKjkmInfo(String kmbh){
		Map<String, Object> map;
		String sql = "select a.kmmc,a.yefx,a.sfjjflkm,a.bmhs,a.xmhs,b.kmzye as kmye from cw_kjkmszb a left join cw_kmyeb b "
				+ " on a.kmbh = b.kmbh where a.kmbh = '"+kmbh+"' and a.sfmj = '1'";
		try {  
			map=db.queryForMap(sql);
		} catch (Exception e) {  
		    return null;  
		}  
		return map;
	}
	public Map<String, Object> getJjkmInfo(String kmbh){
		String sql = "select a.kmmc as zcjjflkm from cw_jjkmb a where a.kmbh = '"+kmbh+"' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getBmInfo(String bmbh){
		String sql = "select a.mc as bmmc from gx_sys_dwb a where a.dwbh = '"+bmbh+"'";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getXmInfo(String xmbh,String bmbh){
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
		sql.append(" a.syje,r.xm as fzr,");
		sql.append(" DECODE(NVL(A.SYJE,0),0,'0.00',TO_CHAR(ROUND(A.SYJE,2),'FM99999999990.00'))AS XMYE,");
		sql.append(" r.xm as fzrxm,a.gkxxm as xmgkxxm,");
		sql.append(" a.gkxxm,b.bz,b.xmlxmc as xmlx,");
		sql.append(" b.xmlxmc");
		sql.append(" from cw_xmjbxxb a");
		sql.append(" left join cw_xmlxb b  on a.xmlb = b.xmlxbh");
		sql.append(" left join cw_xmsrb c  on b.guid = c.xmlxbh");
		sql.append(" left join cw_xmzcb d on b.guid = d.xmlxbh");
		sql.append(" left join cw_kjkmszb k1 on k1.kmbh=c.srkmbh");
		sql.append(" left join cw_kjkmszb k2 on k2.kmbh=d.zckmbh");
		sql.append(" left join gx_sys_ryb r on r.rybh=a.fzr");
		sql.append(" where a.xmbh = '"+xmbh+"' ");
		sql.append(strWhere);
		sql.append(" group by a.xmmc,a.syje,a.gkxxm,b.xmlxmc,r.xm,b.bz");
		return db.queryForMap(sql.toString());
	}
	/**
	 * 联想输入
	 * */
	public String getSgstZy(String dm) {
		String sql = "select '('||dm||')'||mc as zy from gx_sys_dmb where zl = '"+Constant.ZY+"' and dm like '%"+dm+"%'";
		List<Map<String, Object>> list = db.queryForList(sql);
		String result = "";
		for (Map<String, Object> map : list) {
			result += map.get("zy") + ";;"; 
		}
		return result;
	}
	public String getSgstKmbh(String kmbh) {
		String sql = "select '('||kmbh||')'||kmmc as kmbh from cw_kjkmszb where kmbh like '"+kmbh+"%' and sfmj = '1'";
		List<Map<String, Object>> list = db.queryForList(sql);
		String result = "";
		for (Map<String, Object> map : list) {
			result += map.get("kmbh") + ";;"; 
		}
		return result;
	}
	public String validateKmbh(String value) {
		String sql = "select count(d.kmbh) as counts from cw_kjkmszb d where d.kmbh = ? and sfmj = '1'";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String getSgstJjfl(String kmbh) {
		String sql = "select '('||kmbh||')'||kmmc as kmbh from cw_jjkmb where kmbh like '%"+kmbh+"%'";
		List<Map<String, Object>> list = db.queryForList(sql);
		String result = "";
		for (Map<String, Object> map : list) {
			result += map.get("kmbh") + ";;"; 
		}
		return result;
	}
	public String validateZy(String value) {
		String sql = "select count(d.dm) as counts from gx_sys_dmb d where d.zl = '"+Constant.ZY+"' and d.dm = ?";
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
		String sql = "select '('||dwbh||')'||mc as dwbh from gx_sys_dwb where dwbh like '%"+dwbh+"%'";
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
		String sql = "select '('||xmbh||')'||xmmc as xmbh from cw_xmjbxxb where xmbh like '%"+xmbh+"%' "+strWhere;
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
	 * 查询未审核最小的
	 * @param sszt
	 * @param pznd
	 * @param kjqj
	 * @param pzz
	 * @return
	 */
	
	public String getminPzbh(String sszt,String pznd,String kjqj,String pzz) {
		String sql = "select min(pzbh) from cw_pzlrzb where pznd='"+pznd+"' and kjqj='"+kjqj+"' and sszt='"+sszt+"' and pzz='"+pzz+"'   ";
		String pzbh = Validate.isNullToDefaultString(db.queryForObject(sql,String.class), "");
		
		return db.queryForObject(sql,String.class);
	}
	/**
	 * 查询凭证录入主表信息
	 * */
	public Map<String, Object> getPzlrMain(String pznd,String kjqj,String pzbh,String sszt,String pzlx){
		String sql = "select 	(select mc from gx_sys_dmb where zl = '"+Constant.PZLY+"' and dm = a.pzly)as pzlymc ," + 
				"				(select mc from gx_sys_dmb where zl = '"+Constant.PZZT+"' and dm = a.pzzt) as pzzt," + 
				"				(select xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
				"               (select rybh from gx_sys_ryb where guid = a.zdr) as zdrbh," + 
				"				(select xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
				"				(select xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
				"				(select xm from gx_sys_ryb where guid = a.cn) as cnr," + 
				"				guid,zdr as zdrId, pzbh,pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
				+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"'  and  kjqj = '"+kjqj+"' and pzz='"+pzlx+"'";
		return db.queryForMap(sql);
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
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,b.bz as lrbbz,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid"
				+ " where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' and pzz='"+pzlx+"'  )order by a.indexnew";
		return db.queryForList(sql);
	}
	public String getPzlx(String pznd,String kjqj,String sszt) {
		String sql = "select pzlxbh from cw_pzlxbnew t where pzlxbh in (select pzz from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"'and pzzt!='00' and kjqj = '"+kjqj+"') and rownum <2 ";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 获取凭证编号列表
	 * @param pznd
	 * @param kjqj
	 * @param sszt
	 * @return
	 */
	public List<String> getPzbhList(String pznd,String kjqj,String sszt,String pzlx){
		String sql = "select pzbh from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' and pzz='"+pzlx+"'  order by pzbh asc";
		List<String> list = db.queryForList(sql,String.class);
		if(list.size()==0){
			list.add("0001");
		}
		return list;
	}
	/**
	 * 主表，明细表，辅助表,展示表插入数据
	 * */
	public int insertPzlrZb(String guid,PageData pd) {
		String sql = "insert into cw_pzlrzb (guid,pzz,pzbh,pzrq,fjzs,zdr,jfjehj,dfjehj,pzzt,sszt,kjqj,pzly) values"
				+ "(?,?,(select nvl(replace(lpad(max(pzbh)+1,4),' ','0'),'0001') from cw_pzlrzb where sszt = ?),to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,?,?)";
		Object[] obj = {
				guid,
				pd.getString("pzz"),
				pd.getString("sszt"),
				pd.getString("pzrq"),
				pd.getString("fjzs"),
				LUser.getGuid(),
				pd.getString("jfjehj"),
				pd.getString("dfjehj"),
				"00",
				pd.getString("sszt"),
				pd.getString("kjqj"),
				"1"
		};
		return db.update(sql,obj);
	}
	public int updatePzlrZb(String guid,PageData pd) {
		String sql = "insert into cw_pzlrzb (guid,pzz,pzbh,pzrq,fjzs,zdr,jfjehj,dfjehj,pzzt,sszt,kjqj,pzly) values"
				+ "(?,?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,?,?)";
		Object[] obj = {
				guid,
				pd.getString("pzz"),
				pd.getString("pzbh"),
				pd.getString("pzrq"),
				pd.getString("fjzs"),
				LUser.getGuid(),
				pd.getString("jfjehj"),
				pd.getString("dfjehj"),
				"00",
				pd.getString("sszt"),
				pd.getString("kjqj"),
				"1"
		};
		return db.update(sql,obj);
	}
	public int insertPzlrMxb(String guid,String pzbh,Map<String, String> map,String sszt) {
		String sql = "insert into cw_pzlrmxb (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,czr,czrq,sszt) values"
				+ "(?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?)";
		Object[] obj = {
				guid,
				pzbh,
				map.get("zy"),
				WindowUtil.getText(map.get("kmbh")),
				WindowUtil.getText(map.get("jjfl")),
				WindowUtil.getText(map.get("bmbh")),
				WindowUtil.getText(map.get("xmbh")),
				map.get("jdfx"),
				map.get("jfje"),
				map.get("dfje"),
				LUser.getGuid(),
				DateUtil.getTime(),
				sszt
		};
		return db.update(sql,obj);
	}
	public int insertPzlrFzb(String kmbh,Map<String, String> map) {
		String sql = "insert into cw_fzlrb (guid,kmbh,wldc,zrr,wldw,jsfs,jsdh,dfdw,yslx,zclx,ysly,bz,czr,czrq,GNKM) values"
				+ "(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?)";
		Object[] obj = {
				kmbh,
				map.get("wldc"),
				WindowUtil.getText(map.get("zrr")),
				WindowUtil.getText(map.get("wldw")),
				map.get("jsfs"),
				map.get("jsdh"),
				WindowUtil.getText(map.get("dfdw")),
				map.get("yslx"),
				map.get("zclx"),
				map.get("ysly"),
				map.get("bz"),
				LUser.getGuid(),
				DateUtil.getTime(),
				WindowUtil.getText(map.get("gnkm")),
		};
		return db.update(sql,obj);
	}
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
	 * 查询凭证录入
	 * */
	public Map<String, Object> selectPzlrzb(String pzz,String pzbh,String sszt,String pznd,String kjqj){
	//	String sql = "select 	(select mc from gx_sys_dmb where zl = 'pzly' and dm = a.pzly)as pzlymc ," + 
	//			"				(select xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
	//			"				(select xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
	//			"				(select xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
	//			"				(select xm from gx_sys_ryb where guid = a.cn) as cnr," + 
	//			"				guid, pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
	//			+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and a.pzz = '"+pzz+"' and sszt = '"+sszt+"'";
//		String sql = "select 	(select mc from gx_sys_dmb where zl = 'pzly' and dm = a.pzly)as pzlymc ," + 
//				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
//				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
//				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
//				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.cn) as cnr," + 
//				"				guid,pzzt,pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
//		+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd='"+pznd+"' and kjqj='"+kjqj+"'";
//		return db.queryForMap(sql);
		String sql = "select 	(select mc from gx_sys_dmb where zl = '"+Constant.PZLY+"' and dm = a.pzly)as pzlymc ," + 
				"				(select mc from gx_sys_dmb where zl = '"+Constant.PZZT+"' and dm = a.pzzt) as pzzt," + 
				"				(select xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
				"				(select xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
				"				(select xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
				"				(select xm from gx_sys_ryb where guid = a.cn) as cnr," + 
				"				guid,zdr as zdrId, pzbh,pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
				+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"'";
		System.err.println("查询授sql="+sql);
		return db.queryForMap(sql);
	}
	public List<Map<String, Object>> selectPzlrmxAndFzlr(String pzz,String pzbh,String sszt,String pznd,String kjqj){
		/*String sql = "select a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,"
				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = b.dfdw) as dfdw,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and "
				+ "pzz = '"+pzz+"' and sszt = '"+sszt+"')";*/
//		String sql = "select a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,"
//				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
//				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
//				+ "b.jsdh,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = b.dfdw) as dfdw,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
//				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
//				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid"
//				+ " where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+" and pznd='"+pznd+"' and kjqj='"+kjqj+"'')";
//		return db.queryForList(sql);
		String sql = "select a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,"
				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.dfdw) as dfdw,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "(select '('||kmbh||')'||kmmc from CW_GNKMB where  KMBH = b.GNKM) as GNKM,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid"
				+ " where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' )";
		return db.queryForList(sql);
	}
	//获取最大的凭证编号
	public String getMaxPzbh(String pznd,String kjqj,String sszt) {
		String sql = "select max(pzbh) from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"'";
		return db.queryForSingleValue(sql);
	}
	
	//获取凭证编号列表
//	public List<String> getPzbhList(String pzz,String sszt){
//	//	String sql = "select pzbh from cw_pzlrzb where pzz = '"+pzz+"' and sszt = '"+sszt+"' order by pzbh asc";
//		String sql = "select pzbh from cw_pzlrzb where sszt = '"+sszt+"' order by pzbh asc";
//		return db.queryForList(sql,String.class);
//	}
		public List<String> getPzbhList(String pznd,String kjqj,String sszt){
			String sql = "select pzbh from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' order by pzbh asc";
			System.err.println("sql++++="+sql);
			return db.queryForList(sql,String.class);
		}
	//获取凭证字列表
	public List<Map<String, Object>> getPzzList(String sszt){
		String sql = "select lxbh,pzz from cw_pzlxb where sszt = '"+sszt+"' order by lxbh";
		System.err.println("获取凭证字列表sqp="+sql);
		return db.queryForList(sql);
	}
	//获取账套会计期间
	public Map<String, Object> getZtXx(String ztid){
		String sql = "select kjnd,qjs from cw_ztxxb where guid = '"+ztid+"'";
		System.err.println("获取账套会计期间sqp="+sql);
		return db.queryForMap(sql);
	}
	/*
	 *获取凭证生成时所用的到信息 
	 * */
	public List<Map<String, Object>> getPzListByRcbx(String guid){
		String sql = "select u.bz as zy,p.kmbh,'' as jjfl,t.szbm as bmbh ,t.xmmc as xmbh,'1' as jdfx,t.bxzje as bxje\r\n" + 
				"from cw_rcbxzb t left join cw_rcbxmxb u on t.guid = u.zbid left join cw_fykmdzb p on u.fymc = p.guid where t.guid = ?\r\n" + 
				"union\r\n" + 
				"select '冲借款' as zy,'121501' as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,cjkje as bxje\r\n" + 
				"from cw_rcbxzb r left join cw_cjkb t on r.guid = t.jkdh where r.sfcjk = '1' and r.guid = ?\r\n" + 
				"union\r\n" + 
				"select '对公支付' as zy,'1002' kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as bxje\r\n" + 
				"from cw_rcbxzb r left join cw_dgzfb t on r.guid = t.zfdh where r.sfdgzf = '1' and r.guid = ?\r\n" + 
				"union\r\n" + 
				"select '公务卡' as zy,'1011' kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,skje as bxje\r\n" + 
				"from cw_rcbxzb r left join cw_gwkb t on r.guid = t.skdh where r.sfgwk = '1' and r.guid = ?"
				+ " union\r\n"
				+ "select '对私支付' as zy,'1002' kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as bxje\r\n"
				+ "from cw_rcbxzb r left join cw_dszfb t on r.guid = t.zfdh where r.sfdszf = '1' and r.guid = ?";
		Object[] obj = {guid,guid,guid,guid,guid};
		return db.queryForList(sql,obj);
	}
	public List<Map<String, Object>> getPzListByClbx(String guid){
		String sql = "select (select to_char('('||xm||')'||'差旅费报销') from gx_sys_ryb where guid = t.sqr) as zy,'500102' as kmbh,'' as jjfl,(select dwbh from gx_sys_ryb where guid = t.sqr) as bmbh,\r\n" + 
				"(select xmbh from cw_xmjbxxb where guid = t.xmmc) as xmbh,'1' as jdfx,to_number(bxzje) as bxje\r\n" + 
				"from cw_clfbxzb t where t.guid = ?\r\n" +
				"union\r\n" + 
				"select '冲借款' as zy,'121501' as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,cjkje as bxje\r\n" + 
				"from cw_clfbxzb r left join cw_cjkb t on r.guid = t.jkdh where r.sfcjk = '1' and r.guid = ?\r\n" + 
				"union\r\n" + 
				"select '对公支付' as zy,'1002' kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as bxje\r\n" + 
				"from cw_clfbxzb r left join cw_dgzfb t on r.guid = t.zfdh where r.sfdgzf = '1' and r.guid = ?\r\n" + 
				"union\r\n" + 
				"select '公务卡' as zy,'1011' kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,skje as bxje\r\n" + 
				"from cw_clfbxzb r left join cw_gwkb t on r.guid = t.skdh where r.sfgwk = '1' and r.guid = ?\r\n"
				+ "union\r\n"
				+ "select '对私支付' as zy,'1002' kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as bxje\r\n"
				+ "from cw_clfbxzb r left join cw_dszfb t on r.guid = t.zfdh where r.sfdszf = '1' and r.guid = ?";
		Object[] obj = {guid,guid,guid,guid,guid};
		return db.queryForList(sql,obj);
	}
	public List<Map<String, Object>> getPzListByGwjdbx(String guid){
		String sql = "select '公务接待费报销' as zy,'500101' as kmbh,'' as jjfl,(select dwbh from gx_sys_ryb where guid = t.bxryid) as bmbh,\r\n" + 
				"'' as xmbh,'1' as jdfx,bxje\r\n" + 
				"from cw_gwjdfbxzb t where t.guid = ?\r\n"+
				"union\r\n" + 
				"select '冲借款' as zy,'121501' as kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,cjkje as bxje\r\n" + 
				"from cw_gwjdfbxzb r left join cw_cjkb t on r.guid = t.jkdh where r.sfcjk = '1' and r.guid = ?\r\n" + 
				"union\r\n" + 
				"select '对公支付' as zy,'1002' kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as bxje\r\n" + 
				"from cw_gwjdfbxzb r left join cw_dgzfb t on r.guid = t.zfdh where r.sfdgzf = '1' and r.guid = ?\r\n" + 
				"union\r\n" + 
				"select '公务卡' as zy,'1011' kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,skje as bxje\r\n" + 
				"from cw_gwjdfbxzb r left join cw_gwkb t on r.guid = t.skdh where r.sfgwk = '1' and r.guid = ?\r\n"
				+ " union\r\n"
				+ "select '对私支付' as zy,'1002' kmbh,'' as jjfl,'' as bmbh,'' as xmbh,'0' as jdfx,je as bxje\r\n"
				+ "from cw_gwjdfbxzb r left join cw_dszfb t on r.guid = t.zfdh where r.sfdszf = '1' and r.guid = ?";
		Object[] obj = {guid,guid,guid,guid,guid};
		return db.queryForList(sql,obj);
	}
	//查询复核人
	public Object getFhr(String pzbh,String pzz,String sszt,String pznd,String kjqj){
	//	String sql = "select fhr from cw_pzlrzb where pzbh = '"+pzbh+"' and pzz = '"+pzz+"' and sszt = '"+sszt+"'";
		String sql = "select fhr from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"'  and pznd='"+pznd+"' and kjqj='"+kjqj+"'";
		return db.queryForObject(sql, String.class);
	}
	//查询借方必有,借方必无，
	public Map<String, Object> getBybwkm(String pzz,String sszt,String pznd,String kjqj) {
		String sql = "select distinct \r\n" + 
				"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Jf') as jfbykm,\r\n" + 
				"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Df') as dfbykm,\r\n" + 
				"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzby') as pzbykm,\r\n" + 
				"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzbw') as pzbwkm\r\n" + 
				"from cw_pzlxb t join cw_pzkmdzb s on t.guid = s.pzlxm where t.lxbh = '"+pzz+"' and t.sszt = '"+sszt+"' ";
		return db.queryForMap(sql);
	}
	//删除凭证信息
	public int deletePzzb(String pzbh,String pzz,String sszt) {
	//	String sql = "delete from cw_pzlrzb where pzbh = '"+pzbh+"' and pzz = '"+pzz+"' and sszt = '"+sszt+"'";
		String sql = "delete from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"'";
		return db.update(sql);
	}
	//
	public List<Map<String, Object>> getPzListByMb(String guid) {
		String sql = "select kmbh from cw_pzmbmxb where mbbh = '"+guid+"'";
		return db.queryForList(sql);
	}
	public int insertPzlrbxdzb(String pzid,String bxid) {
		String sql = "insert into cw_pzlrbxdzb (pzid,bxid) values ('"+pzid+"','"+bxid+"')";
		return db.update(sql);
	}
	/**
	 * 复核
	 * @param guid
	 * @param pzzt
	 * @param fhr
	 * @param bmbh
	 * @param xmbh
	 * @param jfje
	 * @return
	 */
	public int saveFh(String guid,String pzzt,String fhr) {
		String sql = "UPDATE CW_PZLRZB SET PZZT = ? , FHR = ? where guid = ?";
		Object[] obj = {"01",fhr,guid};		
		int i = 0;
		try {  
			i = db.update(sql, obj);
			if(i>0){
				updateXmMoneyByPz(guid);
			}
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	/**
	 * 复核  减去 项目的 剩余 金额
	 * @param guid
	 */
	public void updateXmMoneyByPz(String guid){
		String sql = "select * from cw_pzlrmxb where pzbh in('"+guid+"') and xmbh is not null and kmbh is not null";
		List list = db.queryForList(sql);
		if(list==null||list.size()==0){
			return;
		}
		ArrayList<String> sqlList = new ArrayList<String>();
		for(int i=0,len=list.size();i<len;i++){
			Map map = (Map)list.get(i);
			String mxguid = Validate.isNullToDefaultString(map.get("GUID"),"");
			String xmbh = Validate.isNullToDefaultString(map.get("XMBH"),"");
			String bmbh = Validate.isNullToDefaultString(map.get("BMBH"),"");
			String dfje = Validate.isNullToDefaultString(map.get("DFJE"),"0");
			String jfje = Validate.isNullToDefaultString(map.get("JFJE"),"0");
			String jdfx = Validate.isNullToDefaultString(map.get("JDFX"),"");
			String kmbh = Validate.isNullToDefaultString(map.get("KMBH"),"");
			String zy = Validate.isNullToDefaultString(map.get("ZY"),"");
			if(Validate.noNull(xmbh)&&Validate.noNull(bmbh)){
				if(Validate.isNull(jdfx)){
					jdfx = db.update("select distinct yefx from cw_kjkmszb where kmbh='"+kmbh+"'")+"";
				}
				StringBuffer sqls = new StringBuffer();
				sqls.append(" update cw_xmjbxxb t set");
				sqls.append(" t.syje=");
				sqls.append(" case '"+jdfx+"' when '0' then syje-(nvl('"+jfje+"',0)-nvl('"+dfje+"',0))");
				sqls.append(" else syje-(nvl('"+dfje+"',0)-nvl('"+jfje+"',0)) end");
				sqls.append(" where t.xmbh='"+xmbh+"' and t.bmbh='"+bmbh+"'");
				sqlList.add(sqls.toString());
			}
			//增加的复核流水信息
			StringBuffer sqlBuffer = new StringBuffer();//cw_pzzsb
			sqlBuffer.append(" insert into CW_FHLSB (guid,xmguid,pzguid,bmbh,bmmc,xmbh,xmmc,czr,czsj,flguid,kjqj,zy,kmbh,jfje,dfje,czlx,xmye) values ");
			sqlBuffer.append(" (?,'',?,?,(select t.bmmc from cw_pzzsb t where t.guid='"+mxguid+"'),?,(select t.xmmc from cw_pzzsb t where t.guid='"+mxguid+"'),?,SysDate,?,(select t.kjqj from CW_PZLRZB t where t.guid='"+guid+"'),?,?,to_number(?),to_number(?),?,(select t.xmye from cw_pzzsb t where t.guid='"+mxguid+"')) ");
			Object[] obj = {
					GenAutoKey.get32UUID(),
					guid,
					bmbh,
					xmbh,
					LUser.getGuid(),
					mxguid,
					zy,
					kmbh,
					jfje,
					dfje,
					"0",
			};
		   db.update(sqlBuffer.toString(),obj);
	
		}
		db.batchUpdate(sqlList);
	}
	
	/**
	 * 取消复核
	 * @param guid
	 * @param pzzt
	 * @param fhr
	 * @return
	 */
	public int saveFhqx(String guid,String pzzt,String fhr) {
		String sql = "UPDATE CW_PZLRZB SET PZZT = ? , FHR = ? where guid = ?";
		Object[] obj = {"00",fhr,guid};		
		int i = 0;
		try {  
			i = db.update(sql, obj);
			if(i>0){
				updateXmMoneyByQxfh(guid);
			}
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	/**
	 * 取消 复核  返回  项目剩余金额
	 * @param guid
	 * @param pzzt
	 * @param fhyj
	 * @return
	 */
	public void updateXmMoneyByQxfh(String guid){
		String sql = "select * from cw_pzlrmxb where pzbh in('"+guid+"') and xmbh is not null and kmbh is not null";
		List list = db.queryForList(sql);
		if(list==null||list.size()==0){
			return;
		}
		ArrayList<String> sqlList = new ArrayList<String>();
		for(int i=0,len=list.size();i<len;i++){
			Map map = (Map)list.get(i);
			String mxguid = Validate.isNullToDefaultString(map.get("GUID"),"");
			String xmbh = Validate.isNullToDefaultString(map.get("XMBH"),"");
			String bmbh = Validate.isNullToDefaultString(map.get("BMBH"),"");
			String dfje = Validate.isNullToDefaultString(map.get("DFJE"),"0");
			String jfje = Validate.isNullToDefaultString(map.get("JFJE"),"0");
			String jdfx = Validate.isNullToDefaultString(map.get("JDFX"),"");
			String kmbh = Validate.isNullToDefaultString(map.get("KMBH"),"");
			String zy = Validate.isNullToDefaultString(map.get("ZY"),"");
			if(Validate.noNull(xmbh)&&Validate.noNull(bmbh)){
				if(Validate.isNull(jdfx)){
					jdfx = db.update("select distinct yefx from cw_kjkmszb where kmbh='"+kmbh+"'")+"";
				}
				StringBuffer sqls = new StringBuffer();
				sqls.append(" update cw_xmjbxxb t set");
				sqls.append(" t.syje=");
				sqls.append(" case '"+jdfx+"' when '0' then syje+(nvl('"+jfje+"',0)-nvl('"+dfje+"',0))");
				sqls.append(" else syje+(nvl('"+dfje+"',0)-nvl('"+jfje+"',0)) end");
				sqls.append(" where t.xmbh='"+xmbh+"' and t.bmbh='"+bmbh+"'");
				sqlList.add(sqls.toString());
			}
			//增加的复核流水信息
			StringBuffer sqlBuffer = new StringBuffer();//cw_pzzsb
			sqlBuffer.append(" insert into CW_FHLSB (guid,xmguid,pzguid,bmbh,bmmc,xmbh,xmmc,czr,czsj,flguid,kjqj,zy,kmbh,jfje,dfje,czlx,xmye) values ");
			sqlBuffer.append(" (?,'',?,?,(select t.bmmc from cw_pzzsb t where t.guid='"+mxguid+"'),?,(select t.xmmc from cw_pzzsb t where t.guid='"+mxguid+"'),?,SysDate,?,(select t.kjqj from CW_PZLRZB t where t.guid='"+guid+"'),?,?,to_number(?),to_number(?),?,(select t.xmye from cw_pzzsb t where t.guid='"+mxguid+"')) ");
			Object[] obj = {
					GenAutoKey.get32UUID(),
					guid,
					bmbh,
					xmbh,
					LUser.getGuid(),
					mxguid,
					zy,
					kmbh,
					jfje,
					dfje,
					"0",
			};
		   db.update(sqlBuffer.toString(),obj);
			
		}
		db.batchUpdate(sqlList);
	}
	
	public int qxfh(String guid,String pzzt,String fhyj) {
		String sql = "UPDATE CW_PZLRZB SET "
				+ "PZZT = ?,FHYJ = ? where guid = ?";
		
		Object[] obj = {
				"00",
				fhyj,
				guid
		};
		
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
	public int plfh(String guid,String pzzt,String qspdh,String jspdh) {
		String sql = "UPDATE CW_PZLRZB SET "
				+ "PZZT = ?,fhr='"+LUser.getGuid()+"' where zdr!=? and (pzbh BETWEEN ? AND ?) and pzzt='00'";
		
		Object[] obj = {
				"01",
				LUser.getGuid(),
				qspdh,
				jspdh
		};
		
		int i = 0;
		try {  
			String sqls = "select distinct guid from cw_pzlrzb where zdr!='"+LUser.getGuid()+"' and (pzbh BETWEEN '"+qspdh+"' AND '"+jspdh+"') and pzzt='00'";
//			String pzid = Validate.isNullToDefaultString(db.queryForSingleValue(sqls), "");
			List pzids = db.queryForList(sqls);
			i = db.update(sql, obj);
			if(i>0){
				if(pzids!=null&&pzids.size()>0){
					String pzid = "";
					for(int j=0,len=pzids.size();j<len;j++){
						Map map = (Map)pzids.get(j);
						if(Validate.noNull(map.get("GUID"))){
							pzid += Validate.isNullToDefaultString(map.get("GUID"),"")+",";
						}
					}
					pzid = pzid.substring(0,pzid.lastIndexOf(",")).replace(",", "','");
					updateXmMoneyByPz(pzid);
				}
			}
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
	public int plfhzdr(String guid,String pzzt,String qspdh,String jspdh,String zdr) {
		String userid = LUser.getGuid();
		String sql = "UPDATE CW_PZLRZB SET "
				+ "PZZT = ?,fhr='"+LUser.getGuid()+"' where ZDR != ? AND (pzbh BETWEEN ? AND ?) and pzzt='00'";
		
		Object[] obj = {
				"01",
				userid,
				userid,
				qspdh,
				jspdh,
		};
		
		int i = 0;
		try {  
			String sqls = "select distinct guid from cw_pzlrzb where zdr!='"+userid+"' and (pzbh BETWEEN '"+qspdh+"' AND '"+jspdh+"') and pzzt='00'";
//			String pzid = Validate.isNullToDefaultString(db.queryForSingleValue(sqls), "");
			List pzids = db.queryForList(sqls);
			i = db.update(sql, obj);
			if(i>0){
				if(pzids!=null&&pzids.size()>0){
					String pzid = "";
					for(int j=0,len=pzids.size();j<len;j++){
						Map map = (Map)pzids.get(j);
						if(Validate.noNull(map.get("GUID"))){
							pzid += Validate.isNullToDefaultString(map.get("GUID"),"")+",";
						}
					}
					pzid = pzid.substring(0,pzid.lastIndexOf(",")).replace(",", "','");
					updateXmMoneyByPz(pzid);
				}
			}
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
	public int plfhrq(String guid,String pzzt,String qsrq,String jsrq) {
		String sql = "UPDATE CW_PZLRZB SET "
				+ "PZZT = ?,fhr='"+LUser.getGuid()+"' where (pzrq BETWEEN TO_DATE(?,'yyyy/mm/dd') AND TO_DATE(?,'yyyy/mm/dd'))and pzzt='00' and zdr<>'"+LUser.getGuid()+"'";
		
		Object[] obj = {
				"01",
				qsrq,
				jsrq
		};
		
		int i = 0;
		try {  
			String sqls = "select distinct guid from cw_pzlrzb where (pzrq BETWEEN TO_DATE('"+qsrq+"','yyyy/mm/dd') AND TO_DATE('"+jsrq+"','yyyy/mm/dd'))and pzzt='00' and zdr<>'"+LUser.getGuid()+"'";
//			String pzid = Validate.isNullToDefaultString(db.queryForSingleValue(sqls), "");
			List pzids = db.queryForList(sqls);
			i = db.update(sql, obj);
			if(i>0){
				if(pzids!=null&&pzids.size()>0){
					String pzid = "";
					for(int j=0,len=pzids.size();j<len;j++){
						Map map = (Map)pzids.get(j);
						if(Validate.noNull(map.get("GUID"))){
							pzid += Validate.isNullToDefaultString(map.get("GUID"),"")+",";
						}
					}
					pzid = pzid.substring(0,pzid.lastIndexOf(",")).replace(",", "','");
					updateXmMoneyByPz(pzid);
				}
			}
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
	public int plfhrqzdr(String guid,String pzzt,String qsrq,String jsrq,String zdr) {
		String sql = "UPDATE CW_PZLRZB SET "
				+ "PZZT = ?,fhr='"+LUser.getGuid()+"' where ZDR = ? AND (pzrq BETWEEN TO_DATE(?,'yyyy/mm/dd') AND TO_DATE(?,'yyyy/mm/dd')) and pzzt=?";
		
		Object[] obj = {
				"01",
				zdr,
				qsrq,
				jsrq,
				"00"
		};
		
		int i = 0;
		try {
			String sqls = "select distinct guid from cw_pzlrzb where (pzrq BETWEEN TO_DATE('"+qsrq+"','yyyy/mm/dd') AND TO_DATE('"+jsrq+"','yyyy/mm/dd'))and pzzt='00' and zdr<>'"+zdr+"'";
//			String pzid = Validate.isNullToDefaultString(db.queryForSingleValue(sqls), "");
			List pzids = db.queryForList(sqls);
			i = db.update(sql, obj);
			if(i>0){
				if(pzids!=null&&pzids.size()>0){
					String pzid = "";
					for(int j=0,len=pzids.size();j<len;j++){
						Map map = (Map)pzids.get(j);
						if(Validate.noNull(map.get("GUID"))){
							pzid += Validate.isNullToDefaultString(map.get("GUID"),"")+",";
						}
					}
					pzid = pzid.substring(0,pzid.lastIndexOf(",")).replace(",", "','");
					updateXmMoneyByPz(pzid);
				}
			}
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
	public List getPzlrZbList(String pzzt,String pzz,String sszt) {
		String sql = "select t.guid as dm,t.pzbh as mc "
				+ "from cw_pzlrzb t "
				+ "where pzzt in ('"+pzzt+"') and pzz='"+pzz+"' and sszt='"+sszt+"' "
				+ "order by t.pzbh";//where guid = ?
		return db.queryForList(sql);
	}
	
	public List getZdrList(String pzzt,String sszt) {
		String sql = "select DISTINCT (select xm from gx_sys_ryb where guid = t.zdr) as zdr1,zdr"
				+ " from cw_pzlrzb t where pzzt in ('"+pzzt+"') and sszt='"+sszt+"'";//where guid = ?
		return db.queryForList(sql);
	}
	
	public String pzbhwfh(String pznd,String kjqj,String sszt) {
		String sql = "select min(pzbh) from cw_pzlrzb where pzzt = '00' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' and sszt='"+sszt+"'   ";
		return db.queryForSingleValue(sql);
	}
	
	//获取期末结账会计期间
		public Map<String, Object> getKjqj(String ztid){
			String sql = "select * from (select * from cw_qmjzb where sszt= '"+ztid+"' and sfjz = '0' order by ztnd,to_number(kjqj)) where rownum <= 1";
			System.err.println("获取期末结账会计期间sqp="+sql);
			return db.queryForMap(sql);
		}
		public List<Map<String, Object>> getPzlrMain(String qspdh,String jspdh, String qsrq, String jsrq, String zdr,String pzzt, String sszt, String kjzd) {
			// TODO Auto-generated method stub
			String sql = "select (select mc from gx_sys_dmb where zl = '"+Constant.PZLY+"' and dm = a.pzly)as pzlymc ," + 
					"		    (select mc from gx_sys_dmb where zl = '"+Constant.PZZT+"' and dm = a.pzzt) as pzzt," + 
					"			(select xm from gx_sys_ryb g left join ZC_SYS_JSRYB z on g.rybh=z.rybh where jsbh='06'and rownum<2) as cwfzr," +
					"			(select mc from gx_sys_dwb q left join gx_sys_ryb w on q.dwbh=w.dwbh where w.guid=a.zdr) as dwmc," + 
					"			(select xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
					"			(select xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
					"			(select xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
					"			to_char(jzrq,'yyyy-mm-dd')as jzrq," + 
					"			to_char(pzrq,'yyyy-mm-dd')as pzrq," + 
					"			(select xxmc from cw_xxxxb) as xxmc," + 
					"			(select xm from gx_sys_ryb where guid = a.cn) as cnr," + 
					"			guid,zdr as zdrId, pzbh,fjzs,jfjehj as jfzje,dfjehj as dfzje," +
					"           a.pzlxmc   "
					+ "		from cw_pzlrzb a "
					+ "     where pzzt in ('"+pzzt+"') and sszt = '"+sszt+"' and kjzd = '"+kjzd+"'   ";
			if(Validate.noNull(qspdh)&&Validate.noNull(jspdh)){
				sql += " and (pzbh between '"+qspdh+"' and '"+jspdh+"') and pzbh not in (select pzbh from cw_pzlrzb where pzzt='00') ";
			}
			if(Validate.noNull(qsrq)&&Validate.noNull(jsrq)){
				sql += " and pzrq between to_date('"+qsrq+"','yyyy/mm/dd') and to_date('"+jsrq+"','yyyy/mm/dd') and (pzbh not in (select pzbh from cw_pzlrzb where pzzt='00')) ";
			}
			if(Validate.noNull(zdr)){
				sql += " and zdr = '"+zdr+"' ";
			}
			sql += " order by pzbh ";
			return db.queryForList(sql );
		}
		
		
		
		
}
