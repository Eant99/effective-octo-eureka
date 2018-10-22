package com.googosoft.dao.wsbx.gwjdfbx;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Repository("gwjdfbxsqDao")
public class GwjdfbxsqDao extends BaseDao{
	private Logger logger = Logger.getLogger(GwjdfbxsqDao.class);
	//公务接待报销明细(主表)添加，更新，单行查询，删除
	public int insertGwjdmx(PageData pd) {
		String sql = "insert into cw_gwjdfbxzb (guid,djbh,bxryid,bxry,szbm,bxje,jdcs,jdrq,lbry,ptry,jdsy,gwhdxm,shzt,czr,czrq,sfxy,sfgwk,sfdgzf,sfdszf,sfcjk,lbdw,jdbm,FYFJZS)"
				+ "values(?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),(select sfxy from gx_sys_dwb where dwbh = (select dwbh from gx_sys_ryb where guid = ?)),?,?,?,?,?,?,?)";
		Object[] obj = {
				pd.getString("guid"),
				GenAutoKey.createKeyforth("cw_gwjdfbxzb", "JD", "djbh"),
				pd.getString("bxryid"),
				WindowUtil.getText(pd.getString("bxry")),
				WindowUtil.getText(pd.getString("szbm")),
				pd.getString("bxje"),
				pd.getString("jdcs"),
				pd.getString("jdrq"),
				pd.getString("lbry"),
				pd.getString("ptry"),
				pd.getString("jdsy"),
				pd.getString("gwhdxm"),
				"00",
				LUser.getGuid(),
				DateUtil.getTime(),
				pd.getString("bxryid"),
				"0",
				"0",
				"0",
				"0",
				WindowUtil.getText(pd.getString("lbdw")),
				WindowUtil.getText(pd.getString("jdbm")),
				pd.getString("fyfjzs")
		};
		return db.update(sql,obj);
	}
	public int insertGwjdbxdz(String bxbh,String sqspbh) {
		String sql = "insert into cw_gwjdbxdzb (guid,bxbh,sqspbh) values(sys_guid(),?,?)";
		Object[] obj = {bxbh,sqspbh};
		return db.update(sql,obj);
	}
	public int updateGwjdmx(PageData pd) {
		String sql = "update cw_gwjdfbxzb set djbh = ?,bxryid = ?,bxry = ?,"
				+ "szbm = ?,bxje = ?,jdcs = ?,"
				+ "jdrq = to_date(?,'yyyy-mm-dd'),lbry = ?,ptry = ?,"
				+ "jdsy = ?,gwhdxm = ?,czr = ?,"
				+ "czrq = to_date(?,'yyyy-mm-dd hh24:mi:ss'),"
				+ " FYFJZS = ? "
				+ " where guid = ?";
		Object[] obj = {
				pd.getString("djbh"),
				pd.getString("bxryid"),
				WindowUtil.getText(pd.getString("bxry")),
				WindowUtil.getText(pd.getString("szbm")),
				pd.getString("bxje"),
				pd.getString("jdcs"),
				pd.getString("jdrq"),
				pd.getString("lbry"),
				pd.getString("ptry"),
				pd.getString("jdsy"),
				pd.getString("gwhdxm"),
				LUser.getGuid(),
				DateUtil.getTime(),
				pd.getString("fyfjzs"),
				pd.getString("guid")
		};
		return db.update(sql,obj);
	}
	public int updateZffs(String type,String action,String guid) {
		String sql = "update cw_gwjdfbxzb set "+type+" = '"+action+"' where guid = '"+guid+"'";
		return db.update(sql);
	}
	public Map<String, Object> selectGwjdmxMapById(String guid){
		String sql = " select sfxy,guid,djbh,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = bxry) as sqr,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = szbm) as szbm,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = jdbm) as jdbm," + 
				" jdrq,bxje,lbdw,jdcs,lbry,gwhdxm,ptry,jdsy,sfcjk,sfdgzf,sfdszf,sfgwk,bxryid,FYFJZS from cw_gwjdfbxzb where guid = '"+guid+"'";
		return db.queryForMap(sql);
	}
	public int deleteGwjdfbxsq(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "delete from cw_gwjdfbxzb where guid in ('"+guid+"')";
		return db.update(sql);
	}
	public String selectDjbhByDjbh(PageData pd) {
		String djbh = pd.getString("djbh");
		String sql = "select * from cw_gwjdfbxzb where djbh = '"+djbh+"' ";
		return db.queryForSingleValue(sql);
	}
	public int submit(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "update cw_gwjdfbxzb set shzt = '01',shyj = '' where guid in ('"+guid+"')";
		return db.update(sql);
	}
	public int verify(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "update cw_gwjdfbxzb set shzt = ?,shyj = ?,shr = ?,shrq = to_date(?,'yyyy-mm-dd hh24:mi:ss') where guid in ('"+guid+"')";
		Object[] obj = {
				pd.getString("shzt"),
				pd.getString("shyj"),
				LUser.getGuid(),
				DateUtil.getTime(),
		};
		return db.update(sql,obj);
	}
	//对公支付
	public int insertDgzf(Map<String, Object> map,String guid) {
		String sql = "insert into cw_dgzfb (guid,zfdh,dfdw,dfdq,dfyh,dfzh,je,bxlx) values(sys_guid(),?,?,?,?,?,?,?)";
		Object[] ojb = {
			guid,
			map.get("dfdw"),
			map.get("dfdq"),
			map.get("dfyh"),
			map.get("dfzh"),
			map.get("je"),
			"3"
		};
		return db.update(sql,ojb);
	}
	//对私支付
	public int insertDszf(Map<String, Object> map,String guid) {
		String sql = "insert into cw_dszfb(guid,zfdh,ryxz,ryxm,klx,dfzh,je,bxlx) values (sys_guid(),?,?,?,?,?,?,?)";
		String ryxm = Validate.isNullToDefaultString(map.get("ryxm"), "");
		if(Validate.noNull(ryxm)){
			ryxm = ryxm.substring(1,ryxm.indexOf(")"));
		}
		Object[] ojb = {
			guid,
			map.get("ryxz"),
			ryxm,
			map.get("klx"),
			map.get("dfzh"),
			map.get("je"),
			"3"
		};
		return db.update(sql,ojb);
	}
	//公务卡
	public int insertGwk(Map<String, Object> map,String guid) {
		String sql = "insert into cw_gwkb (guid,skdh,ryxm,skrq,skje,sjskje,kh,bxlx) values (sys_guid(),?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?)";
		Object[] ojb = {
			guid,
			WindowUtil.getText((String)map.get("ryxm")),
			map.get("skrq"),
			map.get("skje"),
			map.get("sjskje"),
			map.get("kh"),
			"3"
		};
		return db.update(sql,ojb);
	}
	public List<Map<String, Object>> selectGwkMapById(String guid){
		String sql = "select a.*,b.* from cw_gwk a left join gx_sys_ryb on a.ryxm = b.rybh where a.skdh = ?";
		Object[] obj = {guid};
		return db.queryForList(sql,obj);
	}
	//冲借款
	public int insertCjk(Map<String, Object> map,String guid) {
		String sql = "insert into cw_cjkb (guid,jkdh,jkr,szbm,jkje,cjkje,jkid,bxlx) values (sys_guid(),?,?,?,?,?,?,'3')";
		Object[] ojb = {
			map.get("jkdh"),
			map.get("jkr"),
			map.get("szbm"),
			map.get("jkje"),
			map.get("cjkje"),
			map.get("djbh")
		};
		System.err.println(sql);
		return db.update(sql,ojb);
	}
	//
	public int updatesfwqbc(String zbid) {
		String sql = " update cw_gwjdfbxzb a set a.sfwqbc = '' where guid = '"+zbid+"' ";
		return db.update(sql);
	}
	public Map checkIsSubmit(String zbid) {
		String sql = " select a.sfwqbc from cw_gwjdfbxzb a where a.guid = '"+zbid+"' ";
		return db.queryForMap(sql);
	}
	public List<Map<String, Object>> selectZffs(PageData pd,String table){
		String sql;
		String zbid = Validate.isNullToDefaultString(pd.getString("guid"), "");
		switch (table) {
		case "gwk":
			//sql = "select a.*,b.* from cw_gwkb a left join gx_sys_ryb b on a.ryxm = b.rybh where a.skdh = ?";
			StringBuffer gwk = new StringBuffer();
			gwk.append(" select");
			gwk.append(" t.guid,t.skdh,to_char(t.skrq,'yyyy-MM-dd')as skrq,t.sjskje,t.kh,");
			gwk.append(" decode(nvl(t.skje,0),0,'0.00',(to_char(round(t.skje,2),'fm99999999999990.00')))skje,");
			gwk.append(" (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.ryxm or r.guid=t.ryxm)ryxm");
			gwk.append(" from cw_gwkb t where 1=1 and t.skdh='" + zbid + "'");
			sql = gwk.toString();
			break;
		case "dszf":
			//sql = "select * from cw_dszfb where zfdh = ?";
			StringBuffer dszf = new StringBuffer();
			dszf.append(" select");
			dszf.append(" '('||w.rybh||')'||w.xm as ryxm,");
			dszf.append(" t.guid,t.zfdh,t.ryxz,t.dfzh,t.klx,");
			dszf.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je");
			dszf.append(" from CW_DSZFB t ");
			dszf.append(" left join gx_sys_ryb w on w.rybh=t.ryxm");
			dszf.append(" where 1=1 and t.zfdh='" + zbid + "'");
			sql = dszf.toString();
			break;
		case "dgzf":
			//sql = "select * from cw_dgzfb where zfdh = ?";
			StringBuffer dgzf = new StringBuffer();
			dgzf.append(" select  (select m.khyh from Cw_Wldwmxb m where m.guid=t.dfyh)yhname,");
			dgzf.append(" t.guid,t.zfdh,t.dfdq,t.dfzh,w.dwmc,w.guid,w.wlbh,t.dfyh,");
			dgzf.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je");
			dgzf.append(" from CW_DGZFB t");
			dgzf.append(" left join Cw_wldwb w on w.WLBH=t.DFDW");
			dgzf.append(" where 1=1 and t.zfdh='" + zbid + "'");
			sql = dgzf.toString();
			break;
		case "cjk":
			//sql = "select * from cw_cjkb where jkdh = ?";
			StringBuffer cjk = new StringBuffer();
			cjk.append(" select");
			cjk.append(" distinct(t.guid)guid,d.mc as dwmc,d.dwbh as szbm,decode(nvl(t.cjkje,0),0,'0.00',(to_char(round(t.cjkje,2),'fm99999999999990.00')))cjkje,t.jkr,'('||r.rybh||')'||r.xm as jkrxm,cw.djbh,decode(nvl(cw.jkzje,0),0,'0.00',(to_char(round(cw.jkzje,2),'fm99999999999990.00')))jkje");
			cjk.append(" from Cw_cjkb t");
			cjk.append(" left join gx_sys_ryb r on r.guid=t.jkr or r.rybh=t.jkr");
			cjk.append(" left join gx_sys_dwb d on d.dwbh=r.dwbh");
			cjk.append(" left join cw_jkywb cw on cw.djbh=t.jkid");
			cjk.append(" where 1=1");
			cjk.append(" and jkdh='" + zbid + "'");
			sql = cjk.toString();
			break;
		default:
			sql = "";
			break;
		}
//		Object[] obj = {
//			pd.getString("guid")
//		};
		return db.queryForList(sql);
	}
	public int updateGWjdsqsp(String guid) {
		String sql = "update cw_gwjdywsqspb set sfybx = '1' where guid in ('"+guid+"') ";
		return db.update(sql);
	}
	public int deleteZffs(PageData pd,String table) {
		String sql;
		switch (table) {
		case "gwk":
			sql = "delete from cw_gwkb where skdh = ?";
			break;
		case "dszf":
			sql = "delete from cw_dszfb where zfdh = ?";
			break;
		case "dgzf":
			sql = "delete from cw_dgzfb where zfdh = ?";
			break;
		case "cjk":
			sql = "delete from cw_cjkb where jkdh = ?";
			break;
		default:
			sql = "";
			break;
		}
		Object[] obj = {
			pd.getString("guid")
		};
		return db.update(sql,obj);
	}
	//
	/**
	 * 更新流程id
	 * @param gid
	 * @return
	 */
	public int doUpdateProcinstId(String ywid,String procinstid) {
		String sql = "UPDATE CW_GWJDFBXZB SET PROCINSTID='"+procinstid+"' WHERE guid"+CommonUtils.getInsql(ywid);
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
		String sql = "UPDATE CW_GWJDFBXZB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+LUser.getGuid()+"' WHERE guid"+CommonUtils.getInsql(ywid);
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
	
	public int insertYzfsqspb(PageData pd,Map<String, Object> ccywsq) {
		String sql = "insert into cw_yzfsqspb (guid,sqspdh,djbh,jkr,szbm,jkje,sqrq,yzfsy,czr,czrq) values(sys_guid(),?,?,?,?,"
				+ "?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'))";
		Object[] obj = {
			ccywsq.get("guid_m"),
			ccywsq.get("djbh"),
			WindowUtil.getText((String)ccywsq.get("sqrxm")),
			WindowUtil.getText((String)ccywsq.get("szbm")),
			ccywsq.get("yzfje"),
			DateUtil.getTime(),
			ccywsq.get("yzfsy"),
			LUser.getGuid(),
			DateUtil.getTime()
		};
		return db.update(sql,obj); 
	}
	
	public Map getgwjd(String guid){
		String sql="select (select mc from gx_sys_dwb where dwbh = t.jdbm) as jdbmmc,(select guid from gx_sys_ryb where rybh=t.sqr)bxryid," +
				"(select '('||rybh||')'||xm from gx_sys_ryb where rybh=t.sqr)bxyrxm,t.* from CW_GWJDYWSQSPB t where t.guid='"+guid+"'";
		return db.queryForMap(sql);
	}
	public List<Map<String, Object>> getDcList(String guid, String searchValue,String sql) {
		return db.queryForList(sql);
	}
	
	//获得当前登录人 银行卡 
	public List<Map<String,Object>> getdlryhklist(){
		String ryguid=LUser.getGuid();
		String sql="select b.guid,b.jsbh,b.khyh,b.khyhzh from cw_jsyhzhb b where b.jsbh='"+ryguid+"'" ;
		return db.queryForList(sql);
	}
	/**
	 * 凭证退回  公务接待
	 * @param fjzs
	 * @param jdsy
	 * @param guid
	 * @return
	 */
	public int doUpdateGWJD(String fjzs, String jdsy, String guid) {
		String sql = "update cw_gwjdfbxzb set FYFJZS = ? ,jdsy=? where guid = ?";
		Object[] obj = {fjzs,jdsy,guid};
		return db.update(sql, obj);
	}
	/**
	 * 凭证退回  复核
	 * @param guid
	 * @return
	 */
	public int GwjdbxSubmit(String guid) {
		String sql = "update cw_gwjdfbxzb set shzt='8' where guid = '"+guid+"'";
		return db.update(sql);
	}
	/**
	 * 获取系统设置  的 公务接待 的 项目的guid
	 * @return
	 */
	public String getGwjdfXmguid() {
		String sql = " select t.guid from CW_XMJBXXB t where  t.bmbh='101' and ( xmmc='公务接待费' or t.xmbh='1005') ";
		return db.queryForSingleValue(sql);
	}
	
	
	
	
}





















