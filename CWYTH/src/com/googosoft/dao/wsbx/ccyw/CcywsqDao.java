package com.googosoft.dao.wsbx.ccyw;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 出差业务事前dao
 * @author googosoft
 *
 */
@Repository("ccywsqDao")
public class CcywsqDao extends BaseDao{
	
	/**
	 * 收入项目查询
	 * @return
	 */
	public List<Map<String, Object>> selectCcywsqList(){
		String sql = "SELECT A.*,ROWNUM AS \"_XH\",(SELECT '('||B.DM||')'||B.MC FROM GX_SYS_DMB B WHERE B.ZL = '"+Constant.XMFL+"' AND B.DM = A.XMFL) AS XMFLMC FROM CW_SRXMB A order by \"_XH\"";
		return db.queryForList(sql);
	}
	/**
	 * 查询出差业务事前信息
	 * @param guid
	 * @return
	 */
	public Map<String, Object> selectCcywsqMapById(String guid){
		Object[] obj = {guid};
		String sql = "SELECT A.SQRXM, (select xm from gx_sys_ryb ry where ry.guid = a.sqr) as sqrmc,A.GUID AS GUID_M,A.SZBM," 
				+ "to_char(B.kssj,'yyyy-mm-dd hh24:mi') AS KSSJMC,"
				+ "to_char(B.jssj,'yyyy-mm-dd hh24:mi') AS JSSJMC,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.JFLX+"' and dm = c.jflx) as jflxmc,"
				+ "(select province from cw_provinces p where p.provinceid=b.provinceid) as province,"
				+ "(select city from cw_cities t where t.cityid = b.cityid) as city,"
				+ "A.*,B.*,C.*"
				+ " FROM cw_xcxxb B, cw_ccsqspb A LEFT JOIN cw_jfszb C ON A.JFBH = C.GUID where A.guid = B.spbh and A.guid = ?";
		return db.queryForMap(sql,obj);
	}
	
	/**
	 * 查询同行人员
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> selectTxryListById(String guid){
		Object[] obj = {guid};
		String sql = "select A.RYBH,ryxm AS XM, ryxm as xmm,dwmc as szbm  "
				+ "FROM ("
					+ "select guid,txbh,rybh,"
						+ "(case nvl(t.sfxwry,'0') when '1' then t.ryxm else (select '('||r.rygh||')'||to_char(r.xm) from gx_sys_ryb r where r.guid=t.rybh) end) as ryxm,"
						+ "(case nvl(t.sfxwry,'0') when '1' then t.dwmc else (select '('||d.bmh||')'||to_char(d.mc) from gx_sys_dwb d where d.dwbh=t.szdw) end) as dwmc,"
						+ "sfxwry,jdr,"
						+ "(case when rybh=jdr then 0 else 1 end) as xh from CW_TXRYXXB t) A "
//				+ "LEFT JOIN GX_SYS_RYB B ON A.RYBH = B.GUID "
//				  +" LEFT JOIN GX_SYS_DWB C ON B.DWBH = C.DWBH "
				  + "WHERE A.TXBH = ? order by a.xh";
		return db.queryForList(sql,obj);
	}
	
	/**
	 * 修改出差业务申请信息
	 * @param pd
	 * @return
	 */
	public int updateCcywsq(PageData pd) {
		String sql = "UPDATE CW_CCSQSPB SET sqr=?,sqrxm=?,szbm=?, DJBH = ?,CCLX = ?,"
				+ "JFBH = ?,SFYZF = ?,YZFJE = ?,CCTS = ?,CCRS = ?,CCNR = ?,CZR = ?,CZRQ = to_date(?,'yyyy-mm-dd hh24:mi:ss'),sxfy = ? where guid = ?";
		Object[] obj = {
				pd.getString("sqrid"),
				pd.getString("sqrxm"),
				pd.getString("szbm"),
				pd.getString("djbh"),
				pd.getString("cclx"),
				pd.getString("jfbh"),
				pd.getString("sfyzf"),
				pd.getString("yzfje"),
				pd.getString("ccts"),
				pd.getString("ccrs"),
				pd.getString("ccnr"),
				pd.getString("czr"),
				DateUtil.getTime(),
				pd.getString("sxfy"),
				pd.getString("guid")
		};
		        String czr = LUser.getGuid();
		        db.update("update cw_sys_ywjlb set ywid = '"+pd.getString("jfbh")+"',kmlx='',czr='"+czr+"',czsj=sysdate where czid = '"+pd.getString("guid")+"'");
		return db.update(sql, obj);
	}
	
	/**
	 * 修改出差业务申请信息
	 * @param pd
	 * @return
	 */
	public int updateCcywsqBybzr(PageData pd) {
		String sql = "UPDATE CW_CCSQSPB SET sqr=?,sqrxm=?,szbm=?, DJBH = ?,CCLX = ?,"
				+ "JFBH = ?,SFYZF = ?,YZFJE = ?,CCTS = ?,CCRS = ?,CCNR = ?,CZR = ?,CZRQ = to_date(?,'yyyy-mm-dd hh24:mi:ss'),sxfy = ? where guid = ?";
		Object[] obj = {
				pd.getString("sqrid"),
				pd.getString("sqrxm"),
				pd.getString("szbm"),
				pd.getString("djbh"),
				pd.getString("cclx"),
				pd.getString("jfbh"),
				pd.getString("sfyzf"),
				pd.getString("yzfje"),
				pd.getString("ccts"),
				pd.getString("ccrs"),
				pd.getString("ccnr"),
				pd.getString("czr"),
				DateUtil.getTime(),
				pd.getString("sxfy"),
				pd.getString("guid")
		};
		        String czr = LUser.getGuid();
		        db.update("update cw_sys_ywjlb set ywid = '"+pd.getString("jfbh")+"',kmlx='',czr='"+czr+"',czsj=sysdate where czid = '"+pd.getString("guid")+"'");
		return db.update(sql, obj);
	}
	
	/**
	 * 修改行程信息
	 * @param pd
	 * @return
	 */
	public int updateXcxx(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "UPDATE CW_XCXXB SET  "
				+ "kssj = to_date(?,'yyyy-mm-dd hh24:mi:ss'),jssj = to_date(?,'yyyy-mm-dd hh24:mi:ss'),"
				+ "cfdd = ?,mddd = ?,jtgj = ?, provinceid = ?, cityid = ? where spbh = ?";
		Object[] obj = {
				pd.getString("kssj"),
				pd.getString("jssj"),
				pd.getString("cfdd"),
				pd.getString("mddd"),
				pd.getString("jtgjm"),
				pd.getString("provinceid"),
				pd.getString("cityid"),
				guid
		};
		return db.update(sql, obj);
	}
	
	/**
	 * 新增出差业务申请信息
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int insertCcywsq(PageData pd,String guid) {
		String sql = "insert into cw_ccsqspb (guid,djbh,sqr,szbm,cclx,jfbh,sfyzf,yzfje,ccts,ccrs,ccnr,shzt,czr,czrq,sxfy,sqrxm,sqrq) "
				+ "values(?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'))";
		Object[] obj = {
				guid,
				GenAutoKey.createKeyforth("cw_ccsqspb", "CC", "djbh"),
				pd.getString("sqrid"),
				pd.getString("szbm"),
				pd.getString("cclx"),
				pd.getString("jfbh"),
				pd.getString("sfyzf"),
				pd.getString("yzfje"),
				pd.getString("ccts"),
				pd.getString("ccrs"),
				pd.getString("ccnr"),
				"01",
				LUser.getGuid(),
				DateUtil.getTime(),
				pd.getString("sxfy"),
				pd.getString("sqrxm"),
				DateUtil.getTime()
		};
		       String czr = LUser.getGuid();
		       db.update("insert into cw_sys_ywjlb(ywid,kmlx,sszt,guid,czid,czr,czsj) select a.guid,a.xmbh,a.sszt,sys_guid(),'"+guid+"','"+czr+"',sysdate from Cw_xmjbxxb a where a.guid='"+pd.getString("jfbh")+"' ");
		return db.update(sql,obj);
	}
	
	/**
	 * 新增行程信息
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int insertXcxx(PageData pd,String guid) {
		String sql = "insert into cw_xcxxb(guid,spbh,kssj,jssj,cfdd,mddd,jtgj,provinceid,cityid) values(sys_guid(),?,to_date(?,'yyyy-mm-dd HH24:mi'),to_date(?,'yyyy-mm-dd HH24:mi'),?,?,?,?,?)";
		Object[] obj = {
				guid,
				pd.getString("kssj"),
				pd.getString("jssj"),
				pd.getString("cfdd"),
				pd.getString("mddd"),
				pd.getString("jtgjm"),
				pd.getString("provinceid"),
				pd.getString("cityid")
		};
		return db.update(sql,obj);
	}
	
	/**
	 * 新增同行人员
	 * @param txryxx
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int insertTxryxx(Map<String, Object> txryxx,PageData pd,String guid) {
		String sql = "insert into cw_txryxxb(guid,txbh,rybh,szdw,ryxm,dwmc,sfxwry,jdr) values(sys_guid(),?,?,(select dwbh from gx_sys_ryb where guid=?),?,?,?,?)";
		Object[] obj = {
				guid,
				txryxx.get("rybh"),
				txryxx.get("rybh"),
				txryxx.get("xm"),
				txryxx.get("szbm"),
				Validate.isNull(txryxx.get("rybh"))?"1":"0",
				LUser.getRybh()
		};
		return db.update(sql,obj);
	}

	/**
	 * 新增项目信息
	 * @param txryxx
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int insertXmxx(Map<String, Object> txryxx,PageData pd,String guid) {
		String sql = "insert into CW_CCSQSPB_XM(guid,ccsqid,jfbh) values(sys_guid(),?,?)";
		Object[] obj = {
				guid,
				txryxx.get("jfbh")
		};
		return db.update(sql,obj);
	}
	
	/**
	 * 删除出差申请
	 * @param guid
	 * @return
	 */
	public int deleteCcywsq(String guid) {
		String sql = "delete from cw_ccsqspb where guid in ('"+guid+"')";
		       db.update("delete from cw_sys_ywjlb where czid in ('"+guid+"')");
		return db.update(sql);
	}
	
	/**
	 * 删除同行人员
	 * @param guid
	 * @return
	 */
	public int deleteTxryxx(String guid) {
		String sql = "delete from cw_txryxxb where txbh in ('"+guid+"')";
		return db.update(sql);
	}
	
	/**
	 * 删除项目信息
	 * @param guid
	 * @return
	 */
	public int deleteXmxx(String guid) {
		String sql = "delete from CW_CCSQSPB_XM where CCSQID in ('"+guid+"')";
		return db.update(sql);
	}
	
	/**
	 * 提交后修改业务表状态
	 * @param pd
	 * @return
	 */
	public int submit(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "update cw_ccsqspb set shzt = '01',shyj = '' where guid in ('"+guid+"')";
		return db.update(sql);
	}
	
	/**
	 * 检查单据编号是否存在
	 * @param pd
	 * @return
	 */
	public boolean checkDjbhExist(PageData pd) {
		String djbh = pd.getString("djbh");
		String sql = "select * from cw_ccsqspb where djbh = '"+djbh+"' ";
		if(db.queryForList(sql).size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 查询提交人的单位领导判断提交人是不是领导
	 * @param rybh
	 * @return
	 */
	public String checkWhoSh(String rybh) {
		String sql = " select d.dwld from gx_sys_dwb d left join gx_sys_ryb r on r.dwbh=d.dwbh where r.rybh = '"+rybh+"' ";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 * 查询提交人的分管领导
	 * @param rybh
	 * @return
	 */
	public String findFgld(String rybh) {
		String sql = " select (select b.xm from gx_sys_ryb b where b.rybh=d.fgld) xm from gx_sys_dwb d left join gx_sys_ryb r on r.dwbh=d.dwbh where r.rybh = '"+rybh+"' ";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 * 查询提交人的单位领导
	 * @param rybh
	 * @return
	 */
	public String findDwld(String rybh) {
		String sql = " select (select b.xm from gx_sys_ryb b where b.rybh=d.dwld) xm from gx_sys_dwb d left join gx_sys_ryb r on r.dwbh=d.dwbh where r.rybh = '"+rybh+"' ";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 * 查询单位领导审核信息
	 * @param procinstid
	 * @return
	 */
	public Map<String, Object> getBmldsh(String procinstid){
		String sql = "select shyj,jdsj as shrq,(select r.xm from gx_sys_ryb r where r.guid=n.rybh) xm from oa_shyjb n left join act_hi_taskinst m on n.taskid = m.id_ where m.task_def_key_ = 'dwldsh' and m.proc_inst_id_ = '"+procinstid+"'";
		return db.queryForMap(sql);
	}
	
	/**
	 * 查询分管领导审核信息
	 * @param procinstid
	 * @return
	 */
	public Map<String, Object> getFgldsh(String procinstid){
		String sql = "select shyj,jdsj as shrq,(select r.xm from gx_sys_ryb r where r.guid=n.rybh) xm from oa_shyjb n left join act_hi_taskinst m on n.taskid = m.id_ where m.task_def_key_ = 'fgld' and m.proc_inst_id_ = '"+procinstid+"'";
		return db.queryForMap(sql);
	}
	/**
	 * 联想得到项目信息
	 * @param guid
	 * @return
	 */
	public Map getxmxx(String xmbh) {
		String sql = " select guid,jflx,ye,(select B.mc from gx_sys_dmb B where B.zl = '"+Constant.JFLX+"' and B.dm = A.jflx) AS JFLXMC from XMINFOS A  where xmbh=? and bsqr='"+LUser.getRybh()+"'";
		return db.queryForMap(sql,new Object[] {CommonUtil.getBeginText(xmbh)});
		
	}
	/**
	 * 查询当前登录人的单位领导
	 * @return
	 */
	public Map<String,Object> getLoginLd(){
		String sql = " select (select guid from gx_sys_ryb where rybh = (select dwld from gx_sys_dwb where dwbh='"+LUser.getDwbh()+"')) as dwld, "
				+" (select guid from gx_sys_ryb where rybh = (select fgld from gx_sys_dwb where dwbh='"+LUser.getDwbh()+"')) as fgld from dual ";
		return db.queryForMap(sql);
	}
	
	/**
	 * 更新业务表的信息
	 * @param table
	 * @param guid
	 * @param procinstid
	 * @param val
	 * @return
	 */
	public int doUpdateByProcinstId(String table,String guid,String procinstid,String val,String shyj,String checkshzt){
		String sql = "update "+table+" t set t.shzt='"+val+"',t.procinstid='"+procinstid+"',shyj='"+shyj+"',checkshzt='"+checkshzt+"' where t.guid='"+guid+"'";
		int r = db.update(sql);
		return r;
	}
	
	/**
	 * 查询经费信息
	 * @param guid
	 * @param searchValue
	 * @param rybh
	 * @param s1
	 * @param s2
	 * @param s3
	 * @param shzt
	 * @return
	 */
	public List<Map<String, Object>> getJsList(String guid, String searchValue,
			String rybh, String s1, String s2, String s3,String shzt) {
		StringBuffer sql = new StringBuffer();
		sql.append("select A.GUID AS GUID,A.DJBH AS DJBH,( SELECT WM_CONCAT(B.XMMC) FROM  CW_CCSQSPB_XM S  LEFT JOIN  CW_JFSZB B ON S.JFBH= B.guid  WHERE  S.CCSQID=A.GUID) AS KTMC,A.CCTS AS CCTS, TO_CHAR(A.SQRQ,'YYYY-MM-DD')SQRQ,A.SZBM,");
		sql.append("(SELECT '('||c.rybh||')'||c.xm FROM GX_SYS_RYB C WHERE C.GUID = A.SQR) AS SQR, ");
		sql.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+s1+"' AND C.DM = A.CCLX) AS CCLX, ");
//		sql.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+s2+"' AND C.DM = B.JFLX) AS JFLX, ");
		sql.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+s3+"' AND C.DM = A.SHZT) AS SHZT ");
		sql.append("from CW_CCSQSPB A LEFT JOIN CW_JFSZB B ON A.JFBH = B.GUID ");
		sql.append("where 1 = 1  and A.SQR = '"+LUser.getGuid()+"'  ");
		if(Validate.noNull(shzt)&&"all".equals(shzt)) {
			sql.append(" and 1=1"); 
		}else{
			sql.append("  and A.SHZT IN ('"+shzt+"')"); 
		}
		if(Validate.noNull(guid)){
			sql.append(" and A.guid in ('"+guid.trim()+"') ");
		}
		
//		Object[] guid2 = guid.split(",");
		return db.queryForList(sql.toString());
	}
	
	/**
	 * 查询审核信息list
	 * @param guid
	 * @param searchValue
	 * @param rybh
	 * @param s1
	 * @param s2
	 * @param s3
	 * @param status
	 * @return
	 */
	public List<Map<String, Object>> getShList(String guid, String searchValue,
			String rybh, String s1, String s2, String s3,String status) {
		StringBuffer sql = new StringBuffer();
		sql.append("select A.GUID,  (SELECT '('||c.rybh||')'||c.xm FROM GX_SYS_RYB C WHERE C.GUID = A.SQR) AS SQRXM, A.SZBM, A.DJBH, "
				+ "( SELECT WM_CONCAT(B.XMMC) FROM  CW_CCSQSPB_XM S  LEFT JOIN  CW_JFSZB B ON S.JFBH= B.guid  WHERE  S.CCSQID=A.GUID) AS KTMC, A.CCTS, A.PROCINSTID, to_char(a.sqrq, 'yyyy-mm-dd') sqrq, ");
		if("0".equals(status) || "".equals(status) || status == null) {
			sql.append(" '0' as shztdm, ");
		}else if("1".equals(status)) {
			sql.append(" '1' as shztdm, ");
		}
		sql.append("(SELECT C.MC  FROM GX_SYS_DMB C  WHERE C.ZL = 'cclx' AND C.DM = A.CCLX) AS CCLX, ");
//		sql.append("(SELECT C.MC  FROM GX_SYS_DMB C WHERE C.ZL = 'jflx' AND C.DM = B.JFLX) AS JFLX, ");
		sql.append("  (SELECT C.MC FROM GX_SYS_DMB C  WHERE C.ZL = '11033'   AND C.DM = A.SHZT) AS SHZT, ");
		sql.append(" C.* from CW_CCSQSPB A LEFT JOIN CW_JFSZB B ON A.JFBH = B.GUID ");
		if("0".equals(status) || "".equals(status) || status == null) {
			sql.append("LEFT JOIN ACT_RU_TASK C ON A.PROCINSTID = C.PROC_INST_ID_  ");
		}else if("1".equals(status)) {
			sql.append("LEFT JOIN (SELECT DISTINCT ASSIGNEE_,PROC_INST_ID_ FROM ACT_HI_ACTINST  WHERE END_TIME_ IS NOT NULL) C ON A.PROCINSTID = C.PROC_INST_ID_  ");
		}
		sql.append("   where 1 = 1 AND A.SHZT NOT IN ('01') ");
		sql.append(" AND A.SHZT NOT IN ('01') and A.sqr != '"+LUser.getGuid()+"' and C.assignee_='"+LUser.getGuid()+"'  ");
		if(Validate.noNull(guid)){
			sql.append(" and A.guid in ('"+guid+"') ");
		}
		sql.append(" order by DJBH asc ");
//		Object[] guid2 = guid.split(",");
		return db.queryForList(sql.toString());
	}
	
	/**
	 * 查询项目信息list
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> selectXmxxListById(String guid) {
		Object[] obj = { guid };
		String sql = "select (select B.mc  from gx_sys_dmb B where B.zl = '"+Constant.JFLX+"'  " +
				"and B.dm = A.jflx) AS JFLXMC,(select '(' || b.rybh || ')' || b.xm  from gx_sys_ryb b  " +
				" where b.rybh = a.fzr) as fzrxm,'('||A.xmbh||')' || a.xmmc as mc,a.ye,a.guid from XMINFOS A left join  CW_CCSQSPB_XM k on k.jfbh = a.guid " +
				"where 1 = 1  " +
//				"and A.ye <> 0  and ((bsqr = '"+LUser.getGuid()+"' or bsqr = '"+LUser.getRybh()+"') and jflx = '02')  or (jflx = '01' and bmbh = '"+LUser.getDwbh()+"') " +
				"and k.ccsqid=? ";
		return db.queryForList(sql, obj);
	}

	/**
	 * 查询本单位的报账员
	 * @return
	 */
	public String getBmbzy(){
		String sql = "select wm_concat(distinct to_char(r.xm))xm from zc_sys_jsryb t left join gx_sys_ryb r on r.rybh=t.rybh where r.dwbh=? and t.jsbh=?";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{LUser.getDwbh(),"13"}),"");
	}
	/**
	 * 获得出差业务申请的List
	 * @param sqltext
	 * @return
	 */
	public List getCcywsqDyList(String sqltext) {
		return db.queryForList(sqltext);
	}
}






















