package com.googosoft.dao.wsbx;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.sql.visitor.functions.Left;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.CW_CLFBXMXB;
import com.googosoft.pojo.wsbx.CW_CLFBXZB;
import com.googosoft.pojo.wsbx.CW_FPXXB;
import com.googosoft.pojo.wsbx.CW_TXRYXXB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("ccbxDao")
public class CcbxDao extends BaseDao {
	private Logger logger = Logger.getLogger(CcbxDao.class);
	

	/**
	 * 新增
	 * 
	 * @param jsxx
	 * @return
	 */
	public int doZbAdd(CW_CLFBXZB clfbxzb) {
//		String xmguid;
//		String sqlXmguid = "select jfbh from CW_CCSQSPB where guid = ?";
//		xmguid = db.queryForSingleValue(sqlXmguid,new Object[] {clfbxzb.getCcywguid()});
//		if(Validate.isNull(xmguid)) {
//			xmguid=clfbxzb.getCcywguid();
//		}
		StringBuffer sqlJkje = new StringBuffer();
		sqlJkje.append(" select sum( nvl(zcje,0) ) AS zcje from CW_JKYWB_MXB m,cw_jkywb t   WHERE m.jkid=t.gid AND jfbh= ?  and t.jkr=?  ");
//		sqlJkje.append("  and  t.gid in (select bxid  from cw_pzlrbxdzb  where pzid in (select guid from cw_pzlrzb where pzzt != '00') and bxtype = 'jkbx') ");
		String jkje = Validate.isNullToDefaultString(db.queryForSingleValue(sqlJkje.toString(),new Object[] {clfbxzb.getXmguids(),LUser.getGuid()}), "0.00");//查询当前登录人从该项目借款金额，且该项目钱款已支付
		String bxzje = Validate.isNullToDefaultString(clfbxzb.getBxzje(), "0.00");
		BigDecimal bxzjes = new BigDecimal(bxzje);
		BigDecimal jkjes = new BigDecimal(jkje);
		//若借款，报销金额需减去借款金额
		BigDecimal bxje = bxzjes.subtract(jkjes);
		String bxjes = bxje+"";
		String djbh =GenAutoKey.createKeyforth("CW_CLFBXZB","CL", "djbh");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = sdf.format(new Date());
		String sql = "insert into CW_CLFBXZB(guid,djbh,cclx,sqr,ccrs,ccts,jflx,xmmc,sfkylbx,bxzjes,bxzje,fjzzs,ccsy,sfxy,czrq,ccywguid,czr)"
				+ "VALUES(?,'"+djbh+"',?,?,?,?,?,?,?,?,?,?,?,?,'"+sysDate+"',?,?) ";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] { clfbxzb.getGuid(), 
				clfbxzb.getCclx(), clfbxzb.getSqr(), clfbxzb.getCcrs(),clfbxzb.getCcts(),clfbxzb.getJflx(),clfbxzb.getCcywguid(),clfbxzb.getSfkylbx(),clfbxzb.getBxzje(),bxjes,clfbxzb.getFjzzs(),clfbxzb.getCcsy(),clfbxzb.getSfxy(),clfbxzb.getCcywguid(),LUser.getGuid() };
		return db.update(sql, obj);
	}
	public int doZbUpdate(CW_CLFBXZB clfbxzb) {
		StringBuffer sqlJkje = new StringBuffer();
		sqlJkje.append(" select sum( nvl(zcje,0) ) AS zcje from CW_JKYWB_MXB m,cw_jkywb t   WHERE m.jkid=t.gid AND jfbh=?  and t.jkr=?   ");
		sqlJkje.append(" and  t.gid in (select bxid  from cw_pzlrbxdzb  where pzid in (select guid from cw_pzlrzb where pzzt != '00') and bxtype = 'jkbx') ");
		String jkje = Validate.isNullToDefaultString(db.queryForSingleValue(sqlJkje.toString(),new Object[] {clfbxzb.getXmguids(),LUser.getGuid()}), "0.00");//查询当前登录人从该项目借款金额，且该项目钱款已支付
		String bxzje = Validate.isNullToDefaultString(clfbxzb.getBxzje(), "0.00");
		BigDecimal bxzjes = new BigDecimal(bxzje);
		BigDecimal jkjes = new BigDecimal(jkje);
		//若借款，报销金额需减去借款金额
		BigDecimal bxje = bxzjes.subtract(jkjes);
		String bxjes = bxje+"";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = sdf.format(new Date());
		String sql = "update CW_CLFBXZB set cclx=?,sqr=?,ccrs=?,ccts=?,jflx=?,xmmc=?,sfkylbx=?,bxzjes=?,bxzje=?,fjzzs=?,ccsy=?,czrq='"+sysDate+"',ccywguid=? where guid=?";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] {
				clfbxzb.getCclx(), clfbxzb.getSqr(), clfbxzb.getCcrs(),clfbxzb.getCcts(),clfbxzb.getJflx(),clfbxzb.getXmmc(),clfbxzb.getSfkylbx(),clfbxzb.getBxzje(),bxjes,clfbxzb.getFjzzs(),clfbxzb.getCcsy(),clfbxzb.getCcywguid(),clfbxzb.getGuid() };
		return db.update(sql, obj);
	}
	public void Zbdelete(String zbid) {
		String delSql = "delete from CW_CLFBXZB where guid=?";
		int m = db.update(delSql, zbid);
	}
	
	/**
	 * 上一步回显使用
	 * 
	 * @return
	 */
	public Map<String, Object> getZbPage(String zbId) {
		String sql = "select guid,djbh,SFGWK,SFCJK,SFDGZF,SFDSZF,cclx,a.sqr rybh,nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.guid=a.sqr),'') sqr,nvl((select to_char(r.xm) from GX_SYS_RYB r where r.guid=a.sqr),'') sqrmc,"
				+ "ccrs,ccts,jflx,nvl((select '('||r.xmbh||')'||to_char(r.xmmc) from cw_xmjbxxb r where r.guid=a.xmmc),'') xmmc,a.xmmc as xmguid,"
				+ "(SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,(SELECT D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBMMC,"
				+ "sfkylbx,bxzje,fjzzs,ccsy,czrq,shzt from CW_CLFBXZB a where guid=?";
		sql=String.format(sql);
		return db.queryForMap(sql,new Object[]{zbId});
	}
	/**
	 * 上一步回显使用
	 * 
	 * @return
	 */
	public List getFpListByZbguid(String zbguid) {
		String sql = "select guid,zbid,fph1,fph2,fph3,fph4,fph5"
				+ "  from CW_FPXXB t "
				+ "  where zbid="+"'"+zbguid+"'";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		return list;
	}
	/**
	 * 上一步回显使用
	 * 
	 * @return
	 */
	public List getMxListByZbguid(String zbguid) {
		String sql = "select guid,djbh,rylx,xm,bxjb,kssj,jssj,cfdd,mddd,fjzs, "
				+ "       fjp,hcp,czcp,qcp,qtfy,hyfy,pxfy,jsshbzje,xsshbzje, "
				+ "       zsfje,snjtbzf,jsbzbz,snjtbzbz,zsfbzbz,(select province from cw_provinces p where p.provinceid=t.provinceid) as province,t.provinceid, "
				+ "       t.cityid,c.city,to_char(hjje,'FM999999999999.00') as hjje "
				+ "  from CW_CLFBXMXB t "
				+ "  left join cw_cities c "
				+ "  on t.cityid=c.cityid "
				+ "  where zbguid="+"'"+zbguid+"' "
				+ "  order by xh";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		return list;
	}
	public List getMxListByZbguid2(String zbguid) {
		String sql = " select sum(fjje) as sfjje, sum(hcje) as shcje,sum(czcje) as sczcje, sum(qcje) as sqcje, sum(pxfy) as spxfy, sum(hyfy) as shyfy, sum(qtfy) as qtfy,"
				+ " sum(lsshbzts) as slsshbzts,sum(xsshbzje) as sxsshbzje, sum(lsshbzje) as slsshbzje, sum(xsshbzje) as sxsshbzje, sum(zdfje) as szdfje, sum(ffjs) as sffjs"
				+ " from CW_CLFBXMXB where djbh="+"'"+zbguid+"'";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		return list;
	}
	public Map<?,?> getMapByguid(String zbguid) {
		String sql = " select  t.bxzje from CW_CLFBXzB t where t.guid='"+zbguid+"'";
		Map list = db.queryForMap(sql);
		return list;
	}
	public Map<?,?> getMapByguid2(String zbguid) {
		String sql = " select sum(t.bxzje)as sbxzje from CW_RCBXZB t left join Cw_Fykmdzb r on r.kmbh=t.xmmc where t.guid='"+zbguid+"'";
		Map list = db.queryForMap(sql);
		return list;
	}
	public List getMxListByZbguid4(String zbguid) {
		String sql = " select t.sqr,t.ktmc,(select d.mc from gx_sys_dwb d where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr))as sqbm,"
				+ "(select r.xm from gx_sys_ryb r where r.guid = t.sqr)as sqrmc,t.kssj,t.jssj,b.rybh,(select d.mc from gx_sys_dwb d where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = b.rybh))as ccry"
				+ " from CW_CLFBXZB t left join Cw_txryxxb b on b.txbh=t.guid where djbh="+"'"+zbguid+"'";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		return list;
	}
	public void deleteFphByZbguid(String zbguid) {
		String delSql = "delete from cw_fpxxb where zbid=?";
		int m = db.update(delSql, zbguid);
	}
	public int doAddFph(CW_FPXXB cw_fpxxb) {
		String sql = "insert into CW_FPXXB(guid,zbid,fph1,fph2,fph3,fph4,fph5)values(sys_guid(),?,?,?,?,?,?)";
		sql = String.format(sql);
		Object[] obj = new Object[] { 
				cw_fpxxb.getZbid(),
				cw_fpxxb.getFph1(),
				cw_fpxxb.getFph2(),
				cw_fpxxb.getFph3(),
				cw_fpxxb.getFph4(),
				cw_fpxxb.getFph5()
		};
		return db.update(sql, obj);
	}
	public void deleteMxByZbguid(String zbguid) {
		String delSql = "delete from CW_CLFBXMXB where zbguid=?";
		int m = db.update(delSql, zbguid);
	}
	public int doMxAdd(CW_CLFBXMXB clfbxmxb) {
		String sql = "insert into CW_CLFBXMXB(guid,zbguid,rylx,xm,bxjb,kssj,jssj,cfdd,mddd,fjzs,hjje,fjp,"
					+ "hcp,czcp,qcp,qtfy,hyfy,pxfy,jsshbzje,xsshbzje,zsfje,snjtbzf,jsbzbz,snjtbzbz,zsfbzbz,provinceid,cityid,xh)"
					+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		sql = String.format(sql);
		Object[] obj = new Object[] { 
				clfbxmxb.getZbguid(),
				clfbxmxb.getRylx(),
				clfbxmxb.getXm(),
				clfbxmxb.getBxjb(),
				clfbxmxb.getKssj(),
				clfbxmxb.getJssj(),
				clfbxmxb.getCfdd(),
				clfbxmxb.getMddd(),
				clfbxmxb.getFjzs(),
				clfbxmxb.getHjje(),
				clfbxmxb.getFjp(),
				clfbxmxb.getHcp(),
				clfbxmxb.getCzcp(),
				clfbxmxb.getQcp(),
				clfbxmxb.getQtfy(),
				clfbxmxb.getHyfy(),
				clfbxmxb.getPxfy(),
				clfbxmxb.getJsshbzje(),
				clfbxmxb.getXsshbzje(),
				clfbxmxb.getZsfje(),
				clfbxmxb.getSnjtbzf(),
				clfbxmxb.getJsbzbz(),
				clfbxmxb.getSnjtbzbz(),
				clfbxmxb.getZsfbzbz(),
				clfbxmxb.getProvinceid(),
				clfbxmxb.getCityid(),
				clfbxmxb.getXh()
		};
		return db.update(sql, obj);
	}
	
	public List getRyListByZbguid(String zbguid) {
		String sql = "select guid, rybh,"
				+ "(case nvl(t.sfxwry,'0') when '1' then t.ryxm else (select '('||r.rygh||')'||to_char(r.xm) from gx_sys_ryb r where r.guid=t.rybh) end) as ryxm,"
				+ "(case nvl(t.sfxwry,'0') when '1' then t.dwmc else (select '('||d.bmh||')'||to_char(d.mc) from gx_sys_dwb d where d.dwbh=t.szdw) end) as szdw,"
				+ "t.sfxwry from CW_TXRYXXB t where txbh="+"'"+zbguid+"'";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		return list;
	}
	
	public List getRyListByZbguidByAdd(String zbguid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.guid,");
//		sql.append(" '('||r.rybh||')'||r.xm as rybh,");
//		sql.append(" '('||d.dwbh||')'||d.mc as szdw");
		sql.append("(case nvl(t.sfxwry,0) when 1 then t.ryxm else (select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.guid=t.rybh) end) as rybh,");
		sql.append("(case nvl(t.sfxwry,0) when 1 then t.dwmc else (select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh=t.szdw) end) as szdw");
		sql.append(" from CW_TXRYXXB t");
//		sql.append(" left join gx_sys_ryb r on r.guid=t.rybh");
//		sql.append(" left join gx_sys_dwb d on d.dwbh=r.dwbh");
		sql.append(" where t.txbh='"+zbguid+"'");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	
	public void deleteRyByZbguid(String zbguid) {
		String delSql = "delete from CW_TXRYXXB where txbh=?";
		int m = db.update(delSql, zbguid);
	}
	public int doRyAdd(CW_TXRYXXB txryxxb) {
		String sql = "insert into CW_TXRYXXB(guid,txbh,rybh,szdw,ryxm, dwmc, sfxwry, jdr )"
				+ "VALUES(sys_guid(),?,?,(select dwbh from gx_sys_ryb where guid=?),?,?,?,?) ";
		sql = String.format(sql);
		Object[] obj = new Object[] { 
				txryxxb.getTxbh(),
				txryxxb.getRybh(),
				txryxxb.getRybh(),
				txryxxb.getRyxm(),
				txryxxb.getDwmc(),
				Validate.isNull(txryxxb.getRybh())?"1":"0",
				LUser.getRybh()
		};
		return db.update(sql, obj);
	}
	/**
	 * 主表信息回显
	 * 
	 * @param zbid
	 * @return
	 */
	public Map<String, Object> getBxzbById(String zbid) {
		String sql = "select * from CW_CLFBXZB t where guid='"+ zbid + "'";
		return db.queryForMap(sql);
	}
	//所选项目明细验证
	public Map<String, Object> checkXzxmGuid(String xzxmguid,String zbguid) {
		String sql = "select * from CW_CLFBXMXB t where xzxmguid='"+ xzxmguid + "' and  djbh='"+ zbguid + "' order by xh";
		return db.queryForMap(sql);
	}
	/**
	 * 更新主表的结算方式
	 * @param rczb
	 * @return
	 */
	public int updateBxzbById(CW_CLFBXZB clfbxzb){
		StringBuffer sql = new StringBuffer();
		sql.append(" update");
		sql.append(" CW_CLFBXZB t set");
		sql.append(" sfgwk=?,sfcjk=?,sfdgzf=?,sfdszf=?");
		sql.append(" where t.guid=?");
		String sqls = sql.toString();
		sqls = String.format(sqls, SystemSet.gxBz);
		Object[] obj = new Object[] {
				clfbxzb.getSfgwk(),
				clfbxzb.getSfcjk(),
				clfbxzb.getSfdgzf(),
				clfbxzb.getSfdszf(),
				clfbxzb.getGuid()
		};
		return db.update(sqls, obj);
	}
	public String selectRyGuidByRybh(String rybh){
		String ry = "select guid from gx_sys_ryb t where rybh='"+rybh+"'";
		return Validate.isNullToDefaultString(db.queryForSingleValue(ry),"");

	}
	/**
	 * 删除主表信息,通过触发器delete_clfbx删除关联信息
	 * @param guid
	 * @return
	 */
	public int deleteZbInfoByGuid(String guid){
		String sql = "delete from CW_CLFBXZB t where t.guid in('"+guid+"')";
		int m = db.update(sql);
		return m;
	}
	
	public int updatesfwqbc(String zbid) {
		String sql = " update cw_clfbxzb a set a.sfwqbc = '' where guid = '"+zbid+"' ";
		return db.update(sql);
	}
	
	public Map checkIsSubmit(String zbid) {
		String sql = " select a.sfwqbc from cw_clfbxzb a where a.guid = '"+zbid+"' ";
		return db.queryForMap(sql);
	}
	
	/**
	 * 更新审核意见信息
	 * @param ddbh
	 * @return
	 */
	public int doUpdateShzt(CW_CLFBXZB clfbxzb) {
		String sql = "update CW_CLFBXZB set shzt = ? WHERE guid = ?";
		Object[] obj = new Object[]{
				clfbxzb.getShzt(), 
				clfbxzb.getGuid()
		};
		int i = db.update(sql, obj);
		return i;
	}
	/**
	 * 获取差旅费报销金额和是否学院
	 * @param guid
	 * @return
	 */
	public Map getRcbxById(String guid){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT * from CW_CLFBXZB t  ");
	buffer.append(" where guid='"+guid+"'");
	String  nodeId = null;
	Map map=new HashMap<>();
	try {
		map = db.queryForMap(buffer+"");
	} catch (DataAccessException e) {
		SQLException sqle = (SQLException) e.getCause();  
	    logger.error("数据库操作失败\n" + sqle);  
	}
    return map;
}
	/**
	 * 更新流程id
	 * @param gid
	 * @return
	 */
	public int doUpdateProcinstId(String ywid,String procinstid) {
		String sql = "UPDATE CW_CLFBXZB SET PROCINSTID='"+procinstid+"' WHERE guid"+CommonUtils.getInsql(ywid);
		Object[] obj = ywid.split(",");
		int i = 0;
		try{  
			i = db.update(sql, obj);
		}catch(DataAccessException e){  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 更新业务表审核状态
	 * @param 
	 * @return
	 */
	public int doStatus(String ywid,String zt,String shyj) {
		String sql = "UPDATE CW_CLFBXZB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+LUser.getGuid()+"' WHERE guid"+CommonUtils.getInsql(ywid);
		Object[] obj = ywid.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 获取流程节点
	 * @param taskId
	 * @return
	 */
	public String getTaskNodeId(String taskId){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ from ACT_RU_TASK t  ");
	buffer.append(" where id_ ='"+taskId+"'");
	String  nodeId = null;
	try {
		nodeId = db.queryForSingleValue(buffer.toString())+"";
	} catch (DataAccessException e) {
		SQLException sqle = (SQLException) e.getCause();  
	    logger.error("数据库操作失败\n" + sqle);  
	}
    return nodeId;
}
	/**
	 * 获取下一节点
	 * @param taskId
	 * @return
	 */
	public String getNextTaskNodeId(String taskId){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ from ACT_RU_TASK t  ");
	buffer.append(" where id_ ='"+taskId+"'");
	String  nodeId = null;
	try {
		nodeId = db.queryForSingleValue(buffer.toString())+"";
	} catch (DataAccessException e) {
		SQLException sqle = (SQLException) e.getCause();  
	    logger.error("数据库操作失败\n" + sqle);  
	}
    return nodeId;
}
	/**
	 * 插入审核意见信息
	 * @param ddbh
	 * @return
	 */
	public int doAddShyj(OA_SHYJB shyjb) {
		String sql = "insert into OA_SHYJB (gid, rybh, procinstid, shyj, taskid, shzt,jdsj) values(sys_guid(), ?, ?, ?, ?, ?,to_char(sysdate,'yyyy-mm-dd hh24:Mi:ss'))";
		Object[] obj = new Object[]{
				LUser.getGuid(), 
				shyjb.getProcinstid(), 
				shyjb.getShyj(), 
				shyjb.getTaskid(), 
				shyjb.getShzt()
		};
		int i = db.update(sql, obj);
		return i;
	}
	
	public String getFinId(String procinstid){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ from ACT_RU_TASK t  ");
		buffer.append(" where PROC_INST_ID_ ='"+procinstid+"'");
		String  nodeId = null;
		try {
			nodeId = db.queryForSingleValue(buffer.toString())+"";
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		}
	    return nodeId;
	}
	/**
	 * 更新个人经费设置
	 * @param 
	 * @return
	 */
	public int updateJfsz(Object jFBH,Object bCBXJE) {
		String sql = "UPDATE CW_JFSZB SET syje=syje-to_number('"+bCBXJE+"') WHERE guid='"+jFBH+"'";
		int i = 0;
		try {  
			i = db.update(sql);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 更新借款表
	 * @param guid
	 */
	public void updateQkje(String guid) {
		String sql = "select sum(nvl(t.cjkje,0))jkje,jkid from cw_cjkb t where t.jkdh='"+guid+"' group by jkid";
		List list = db.queryForList(sql);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String jkje = Validate.isNullToDefaultString(map.get("JKJE"), "0");
				String jkid = Validate.isNullToDefaultString(map.get("JKID"), "");
				String update = "update CW_YZFSQSPB t set t.qkje=(nvl(t.qkje,t.jkje)-to_number('"+jkje+"')) where t.djbh='"+jkid+"'";
				db.update(update);
			}
		}
	}
	
	/**
	 * 根据项目id查询经费类型
	 * @param guid
	 * @return
	 */
	public String getJflxByGuid(String guid){
		String sql = "select jflx from CW_JFSZB where guid='"+guid+"' or xmbh='"+guid+"'";
		sql = Validate.isNullToDefaultString(db.queryForSingleValue(sql), "");
		return sql;
	}
	
	public List getCcywListByguid(String ccywguid) {
		String sql = "SELECT t.guid as guid, "
				+"       t.spbh as spbh, "
				+"       to_char(t.kssj, 'yyyy-mm-dd hh24:mi') as kssj, "
				+"       to_char(t.jssj, 'yyyy-mm-dd hh24:mi') as jssj, "
				+"       t.cfdd as cfdd, "
				+"       t.mddd as mddd, "
				+"       t.jtgj as jtgj, "
				+"       t.provinceid as provinceid, "
				+"       d.province as province, "
				+"       t.cityid as cityid, "
				+"       c.city as city, "
				+"       '('||r.rygh||')'||r.xm as xm "
				+"  FROM CW_XCXXB t "
				+"  left join CW_CCSQSPB a "
				+"  on a.guid=t.spbh"
				+"  left join gx_sys_ryb r "
				+"  on r.guid=a.sqr "
				+"  left join cw_cities c "
				+"  on t.cityid = c.cityid "
				+"  left join cw_provinces d "
					+"  on t.provinceid = d.provinceid "
				+" WHERE t.SPBH in ('"+ccywguid+"') ";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		return list;
	}
	public String getccsyById(String guid) {
		String sql = " select ccnr from cw_ccsqspb where guid = '"+guid+"' ";
		return db.queryForSingleValue(sql);
	}
	//删除差旅费项目明细 数据
	public void deleteClvxmmxb(String zbguid) {
		String delSql = "update CW_CCSQSPB_XM set BCBXJE='' where CCSQID=? ";
		int m = db.update(delSql, zbguid);
	}
	//查询项目信息list
	public List getxmxxlist(String ccywguid) {
		String sql = "SELECT distinct xm.CCSQID,xm.JFBH,xm.GUID,to_char(xm.bcbxje,'FM999999999999.00')as bcbxje,s.xmmc as xmmc,to_char(s.ye,'FM999999999999.00') as ye FROM CW_CCSQSPB_XM xm left join XMINFOS s on s.guid=xm.jfbh WHERE CCSQID in ('"+ccywguid+"')";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		System.err.println("项目信息list==>"+sql);
		return list;
	}
	//查询项目信息list----直接选择项目的
	public List getxmxxlistZjxz(String ccywguid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.guid,  a.xmbh, a.xmmc, a.guid as jfbh, decode(nvl(a.ye, '0'),  '0', '0.00', to_char(round(YE, 2), 'fm9999999999999999999999999900.00')) ye from XMINFOS A ");
//		sql.append("  left join cw_jfszb b on b.xmmc=a.xmmc  ");
		sql.append(" where 1 = 1  and a.guid in ('"+ccywguid+"') ");
		return db.queryForList(sql.toString());
	}
	//更新项目报销金额
	public int doxmxxgx(String ccywguid,String bcbxje,String xmguid, boolean b) {
		String sql = new String();
		if(b){
			 sql="update CW_CCSQSPB_XM set BCBXJE='"+bcbxje+"' where CCSQID='"+ccywguid+"' and jfbh='"+xmguid+"' ";
		}else {
			sql="update CW_CCSQSPB_XM set BCBXJE='"+bcbxje+"',jfbh='"+xmguid+"'  where CCSQID='"+ccywguid+"' and jfbh is null ";
		}
		System.err.println("更新项目报销金额 sql=> "+sql);
		return db.update(sql);
	}
	  //查询 报销总额 list
    public List<Map<String,Object>> getbxzelist(String guid) {
    	StringBuffer sql = new StringBuffer();
		sql.append(" select guid,jfbh,ccsqid,bcbxje from CW_CCSQSPB_XM where guid='"+guid+"' ");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = db.queryForList(sql.toString());
		return list;
    }
  //查询 审核 报销总额 list
    public List<Map<String,Object>> getshbxzelist(String guid) {
    	StringBuffer sql = new StringBuffer();
		sql.append(" select guid,jfbh,ccsqid,bcbxje from CW_CCSQSPB_XM where ccsqid='"+guid+"' ");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = db.queryForList(sql.toString());
		return list;
    }
    
  //如果没有选择事前审批，手动向项目表中添加一个id
	public void addccywguid(String newccywguid) {
		String sql = "insert into  CW_CCSQSPB_XM (guid,ccsqid,JFBH,BCBXJE) values  (sys_guid(),'"+newccywguid+"','','') ";
		int m = db.update(sql);
	}
	/**
	 * //如果没有选择事前审批，手动向cw_clfbxzb表中添加ccywguid
	 * @param zbguid
	 * @param newccywguid
	 */
	public void updateCcywguid(String zbguid, String newccywguid,String xmguid) {
		String sql = "update cw_clfbxzb set CCYWGUID='"+newccywguid+"',xmmc='"+xmguid+"' where GUID='"+zbguid+"'  ";
		int m = db.update(sql);
	}
	
	/**
	 * 根据主表id查询出差业务的id
	 * @param zbid
	 * @return
	 */
	public String getCcywguidByZbid(String zbid){
		String sql = "select CCYWGUID from cw_clfbxzb where guid=?";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql, new Object[]{zbid}), "");
	}
	/**
	 * 根据主表id查询出差业务的id
	 * @param zbid
	 * @return
	 */
	public List<Map<String,Object>> getBxbz(String provinceid,String cityid){
		String sql = "select t.zsbz1 as zstj,"
					+"       t.zsbz2 as zsqt,"
					+"       wj2     as wjzstj,"
					+"       wj3     as wjzsqt,"
					+"       bz      as hsf,"
					+"       jtbz    as jtf,"
					+"       wj1     as wjyf"
					+"  from GX_CLBX t"
					+"  left join cw_cities c"
					+"    on c.city = t.city"
					+"  left join cw_provinces p"
					+"    on p.province = t.city"
					+" where (c.cityid = '"+cityid+"' or p.provinceid = '"+provinceid+"')";
		return db.queryForList(sql);
	}
	public List<Map<String,Object>> checkFph(String zbid){
		String sql = "select * from( select fph1 as fph from cw_fpxxb where zbid !='"+zbid+"' " +
					" union select fph2 as fph from cw_fpxxb where zbid !='"+zbid+"' " +
					" union select fph3 as fph from cw_fpxxb where zbid !='"+zbid+"' " +
			        " union select fph4 as fph from cw_fpxxb where zbid !='"+zbid+"' " +
			        " union select fph5 as fph from cw_fpxxb where zbid !='"+zbid+"')b ";
		return db.queryForList(sql);
	}
	public String checkFphs(){
		String sql = "SELECT wm_concat(fph1),wm_concat(fph2),wm_concat(fph3),wm_concat(fph4),wm_concat(fph5) FROM cw_fpxxb";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 获取报销级别通过人员工号
	 * @return
	 */
	public String getBxjbByRygh(String rygh){
		String sql = " select dm.mc as bxjb "
					+"	  from gx_sys_ryb t "
					+"	left join (select d.dm, d.mc from gx_sys_dmb d where d.zl = 'bxjb') dm "
					+"	 on t.bxjb = dm.dm "
					+"	 where t.rygh = '"+rygh+"'";
		List<Map<String,Object>> list = db.queryForList(sql);
		return list.get(0).get("bxjb")+"";
	}
	/**
	 * 保存 凭证退回  差旅费 
	 * @param clfbxzb
	 * @return
	 */
	public int doUpdateCLF(CW_CLFBXZB clfbxzb) {
		String sql = "update CW_CLFBXZB set ccsy=?,fjzzs=? where guid = ?";
		Object[] obj = new Object[] {
				clfbxzb.getCcsy(), clfbxzb.getFjzzs(),clfbxzb.getGuid()};
		return db.update(sql, obj);
	}
	public int doUpdateCLFMX(String fjzs, String guid) {
		String sql = "update CW_CLFBXMXB set fjzs=? where guid = ?";
		Object[] obj = new Object[] {fjzs,guid};
		return db.update(sql, obj);
	}
	/**
	 * 凭证退回  差旅费 复核
	 * @param guid
	 * @return
	 */
	public int ClfbxSubmit(String guid) {
		String sql = "update CW_CLFBXZB set shzt = '8' where guid = '"+guid+"'";
		return db.update(sql);
	}
	/**
	 * /若走事前审批单子，根据出差事前审批单号，查项目编号。
	 * @param ccywguid
	 * @return
	 */
	public String getSqspXmguid(String ccywguid) {
		// TODO Auto-generated method stub
		String sql="select jfbh from cw_ccsqspb where guid =?";
		return db.queryForSingleValue(sql,new Object[] {ccywguid});
	}
}
