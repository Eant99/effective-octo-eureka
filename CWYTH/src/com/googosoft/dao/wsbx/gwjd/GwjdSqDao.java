package com.googosoft.dao.wsbx.gwjd;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.gwjdf.CW_GWJDFSQ;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

/**
 * 公务接待事前Dao
 * @author googosoft
 *
 */
@Repository("gwjdsqDao")
public class GwjdSqDao extends BaseDao{
	private Logger logger = Logger.getLogger(GwjdSqDao.class);
	
	/**
	 * 新增公务接待事前信息
	 * @param dwb
	 * @return
	 */
	public boolean doAdd(CW_GWJDFSQ gwjdfsq) {
		List<String> sqls = new ArrayList<String>();
		List<Object[]> objs = new ArrayList<Object[]>();
		String sql = "insert into CW_GWJDYWSQSPB(guid,djbh,sqr,szbm,jdrq,jdbm,lbdw,jdfd,lbxmjrs,ptxmjrs,sfyzf,yzfje,jdsy,shzt,sqrq,sfjzbx,jzbxr,lbxm, lbzw, lxr, lxdh, txry,czr,czrq,fjzs) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				gwjdfsq.getGuid(),
				GenAutoKey.createKeyforth("cw_gwjdywsqspb","GW", "djbh"),
				gwjdfsq.getSqr(),
				gwjdfsq.getSzbm(),
				gwjdfsq.getJdrq(),
				gwjdfsq.getJdbm(),
				gwjdfsq.getLbdw(),
				gwjdfsq.getJdfd(),
				gwjdfsq.getLbxmjrs(),
				gwjdfsq.getPtxmjrs(),
				gwjdfsq.getSfyzf(),
				gwjdfsq.getYzfje(),
				gwjdfsq.getJdsy(),
				gwjdfsq.getShzt(),
				gwjdfsq.getSqrq(),
				gwjdfsq.getSfjzbx(),
				gwjdfsq.getJzbxr(),
				gwjdfsq.getLbxm(),
				gwjdfsq.getLbzw(),
				gwjdfsq.getLxr(),
				gwjdfsq.getLxdh(),
				gwjdfsq.getTxry(),
				LUser.getGuid(),
				gwjdfsq.getFjzs()
		};
		sqls.add(sql);
		objs.add(obj);
		
		Map<String, Object> json = new Gson().fromJson(gwjdfsq.getJson(), new TypeToken<HashMap<String,Object>>(){}.getType());
		List<Map<String,Object>> XmxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : XmxxList) {
			String hdxm = map.get("hdxm") + "";
			String hdsj = map.get("hdsj") + "";
			String hddd = map.get("hddd") + "";
			String ptry = map.get("ptry") + "";
			if(Validate.noNull(hdxm)||Validate.noNull(hdsj)||Validate.noNull(hddd)||Validate.noNull(ptry)){
				sqls.add("insert into CW_GWJDYWSQSPB_HDXX(GUID,HDXM,HDSJ,HDDD,PTRY,gwjdsqid) values(sys_guid(),?,to_date(?,'yyyy-mm-dd'),?,?,?)");
				objs.add(new Object[]{hdxm,hdsj,hddd,ptry,gwjdfsq.getGuid()});
			}
		}
		return db.batchUpdate(sqls, objs);
	}
	/**
	 * 公务接待费报销信息
	 * 
	 * @param guid
	 * @return
	 */
	public Map getMapInfoByGuid(String guid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM Cw_Gwjdywsqspb WHERE GUID=?");
		Map map = db.queryForMap(sql.toString(), new Object[] { guid });
		return map;
	}
	/**
	 * 审批通过的信息向预支付表插数据
	 * @param dwb
	 * @return
	 */
	public int doPassAdd(Map map) {
		String sql = "insert into CW_YZFSQSPB(guid,sqspdh,djbh,jkr,szbm,jkje,sqrq,yzfsy,czrq,czr) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				map.get("uuid"),
				map.get("guid"),
				map.get("djbh"),
				map.get("sqr"),
				map.get("szbm"),
				map.get("yzfje"),
				map.get("sqrq"),
				map.get("jdsy"),
				map.get("czrq"),
				map.get("czr")
		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 单位信息实体
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid) {
		String sql = "select guid,djbh,nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=a.sqr),'') sqr,"
				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=a.szbm) szbm,jdrq,"
				+ "to_char(to_date(sqrq, 'yyyy-mm-dd'), 'yyyy') as year,to_char(to_date(sqrq, 'yyyy-mm-dd'), 'mm') as mon,to_char(to_date(sqrq, 'yyyy-mm-dd'), 'dd') as days,"
				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=a.jdbm) jdbm,"
				+ "lbdw,jdfd,lbxmjrs,ptxmjrs,sfyzf,yzfje,jdsy,a.shyj,a.sfjzbx,a.jzbxr,(select xm from gx_sys_ryb where rybh = a.jzbxr) as jzbxrxm,"
				+ "lbxm, lbzw, lxr, lxdh, txry,fjzs from CW_GWJDYWSQSPB a where guid=?";
		sql=String.format(sql);
		return db.queryForMap(sql,new Object[]{guid});
	}
	
	/**
	 * 获取活动信息
	 * @param string
	 * @return
	 */
	public List getMxList(String guid) {
		// TODO Auto-generated method stub
		return db.queryForList("select guid, hdxm, to_char(hdsj,'yyyy-mm-dd') as hdsj, hddd, ptry, gwjdsqid from CW_GWJDYWSQSPB_HDXX where gwjdsqid = ?",new Object[]{guid});
	}
	/**
	 * 获取审核信息
	 * @param string
	 * @return
	 */
	public List getYjList1(String guid) {
		// TODO Auto-generated method stub
		return db.queryForList("select task_def_key_,r.xm,s.shyj, to_char(t.start_time_, 'yyyy.mm.dd') as sj "
				+ " from act_hi_taskinst t "
				+ " left join gx_sys_ryb r on t.assignee_ = r.guid "
				+ " left join oa_shyjb s on t.id_ = s.taskid "
				+ "where proc_inst_id_ = (select procinstid from cw_gwjdywsqspb where guid=?) "
					+ "and t.task_def_key_ = 'bgsfzrsh' "
					+ "and s.shzt='1'"
				+ "order by t.start_time_ desc",
				new Object[]{guid});
	}
	/**
	 * 获取审核信息
	 * @param string
	 * @return
	 */
	public List getYjList2(String guid) {
		// TODO Auto-generated method stub
		return db.queryForList("select task_def_key_,r.xm,s.shyj, to_char(t.start_time_, 'yyyy.mm.dd') as sj "
				+ " from act_hi_taskinst t "
				+ " left join gx_sys_ryb r on t.assignee_ = r.guid "
				+ " left join oa_shyjb s on t.id_ = s.taskid "
				+ "where proc_inst_id_ = (select procinstid from cw_gwjdywsqspb where guid=?) "
					+ "and t.task_def_key_ = 'fgldsh' "
					+ "and s.shzt='1'"
				+ "order by t.start_time_ desc",
				new Object[]{guid});
	}
	/**
	 * 审核通过
	 * @param guid
	 * @return
	 */
	public Map<String, Object> getPassObjectById(String guid) {
		String sql = "select a.* from CW_GWJDYWSQSPB a where guid=?";
		sql=String.format(sql);
		return db.queryForMap(sql,new Object[]{guid});
	}
	
	/**
	 * 修改公务接待事前信息
	 * @param gwjdfsq
	 * @return
	 */
	public boolean doUpdate(CW_GWJDFSQ gwjdfsq){
		List<String> sqls = new ArrayList<String>();
		List<Object[]> objs = new ArrayList<Object[]>();
		String sql = "update CW_GWJDYWSQSPB set guid=?,djbh=?,sqr=?,szbm=?,jdrq=?,jdbm=?,lbdw=?,jdfd=?,lbxmjrs=?,ptxmjrs=?,sfyzf=?,yzfje=?,jdsy=?,jzbxr=?,sfjzbx=?,lbxm=?, lbzw=?, lxr=?, lxdh=?, txry=?,fjzs=? where guid=?";
		sql=String.format(sql);
		Object[] obj = new Object[]{
				gwjdfsq.getGuid(),
				gwjdfsq.getDjbh(),
				gwjdfsq.getSqr(),
				gwjdfsq.getSzbm(),
				gwjdfsq.getJdrq(),
				gwjdfsq.getJdbm(),
				gwjdfsq.getLbdw(),
				gwjdfsq.getJdfd(),
				gwjdfsq.getLbxmjrs(),
				gwjdfsq.getPtxmjrs(),
				gwjdfsq.getSfyzf(),
				gwjdfsq.getYzfje(), 
				gwjdfsq.getJdsy(),
				gwjdfsq.getJzbxr(),
				gwjdfsq.getSfjzbx(),
				gwjdfsq.getLbxm(),
				gwjdfsq.getLbzw(),
				gwjdfsq.getLxr(),
				gwjdfsq.getLxdh(),
				gwjdfsq.getTxry(),
				gwjdfsq.getFjzs(),
				gwjdfsq.getGuid()
		};
		sqls.add(sql);
		objs.add(obj);
		sqls.add("delete from cw_gwjdywsqspb_hdxx where gwjdsqid = ?");
		objs.add(new Object[]{gwjdfsq.getGuid()});
		Map<String, Object> json = new Gson().fromJson(gwjdfsq.getJson(), new TypeToken<HashMap<String,Object>>(){}.getType());
		List<Map<String,Object>> XmxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : XmxxList) {
			String hdxm = map.get("hdxm") + "";
			String hdsj = map.get("hdsj") + "";
			String hddd = map.get("hddd") + "";
			String ptry = map.get("ptry") + "";
			if(Validate.noNull(hdxm)||Validate.noNull(hdsj)||Validate.noNull(hddd)||Validate.noNull(ptry)){
				sqls.add("insert into cw_gwjdywsqspb_hdxx(guid,hdxm,hdsj,hddd,ptry,gwjdsqid) values(sys_guid(),?,to_date(?,'yyyy-mm-dd'),?,?,?)");
				objs.add(new Object[]{hdxm,hdsj,hddd,ptry,gwjdfsq.getGuid()});
			}
		}
		return db.batchUpdate(sqls, objs);
	}
	
	/**
	 * 查询办公室负责人信息
	 * @param rybh
	 * @return
	 */
	public String findBgsfzr(String rybh) {
		String sql = " select \r\n" + 
				"        G.XM AS RYMC\r\n" + 
				"      \r\n" + 
				"  from ZC_SYS_JSRYB D\r\n" + 
				"  left join ZC_SYS_JSB Z on D.JSBH = Z.JSBH\r\n" + 
				"  left join GX_SYS_RYB G on D.RYBH = G.RYBH\r\n" + 
				"  left join GX_SYS_DWB DW on G.DWBH = DW.DWBH\r\n" + 
				" where 1 = 1\r\n" + 
				"   and D.JSBH = '09' and rownum = 1\r\n" + 
				" order by RYGH asc\r\n" + 
				" ";
		return db.queryForSingleValue(sql);
	}
	
	
	/**
	 *  删除公务接待信息
	 * @param guid
	 * @return
	 */
	public boolean doDelete(String guid) {
		List<String> sqls = new ArrayList<String>();
		List<Object[]> objs = new ArrayList<Object[]>();
		sqls.add("DELETE CW_GWJDYWSQSPB WHERE guid"+CommonUtils.getInsql(guid));
		objs.add(guid.split(","));
		sqls.add("delete from CW_GWJDYWSQSPB_HDXX where gwjdsqid"+CommonUtils.getInsql(guid));
		objs.add(guid.split(","));
		return db.batchUpdate(sqls, objs);
	}
	
	/**
	 * 提交审核，状态变为待审核
	 * @param dwbh   000001
	 * @return 
	 */
	public int xxTj(String guid) {
		String sql = "UPDATE CW_GWJDYWSQSPB SET SHZT='01' WHERE guid"+CommonUtils.getInsql(guid);
		sql=String.format(sql);
		Object[] obj = guid.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 审核通过业务表状态更新
	 * @param guid
	 * @param shyj
	 * @return
	 */
	public int xxPass(String guid,String shyj) {
		String sql = "UPDATE CW_GWJDYWSQSPB SET SHZT='04',SHYJ='"+shyj+"'  WHERE guid"+CommonUtils.getInsql(guid);
		sql=String.format(sql);
		Object[] obj = guid.split(",");
		
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 审核退回业务表状态更新
	 * @param guid
	 * @param shyj
	 * @return
	 */
	public int xxRefuse(String guid,String shyj) {
		String sql = "UPDATE CW_GWJDYWSQSPB SET SHZT='02',SHYJ='"+shyj+"' WHERE guid"+CommonUtils.getInsql(guid);
		sql=String.format(sql);
		Object[] obj = guid.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
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
	 * 更新业务表审核状态
	 * @param 
	 * @return
	 */
	public int doStatus(String ywid,String zt,String shyj,String checkshzt) {
		String sql = "UPDATE CW_GWJDYWSQSPB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+LUser.getGuid()+"',checkshzt='"+checkshzt+"' WHERE guid"+CommonUtils.getInsql(ywid);
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
	 * 审核通过后插入预支付信息
	 * @param guid
	 * @return
	 */
	public int insertYzfsqspb(String guid) {
		String sql = "insert into cw_yzfsqspb(guid,djbh,jkr,szbm,jkje,sqrq,czr,czrq,sqspdh,qkje)" +
				" (select sys_guid(),djbh,sqr,szbm,to_char(yzfje),sqrq,czr,czrq,guid,to_char(yzfje) from CW_GWJDYWSQSPB where guid=?)";
		Object[] obj = {guid};
		return db.update(sql,obj); 
	}
	
	/**
	 * 获取流程节点
	 * @param taskId
	 * @return
	 */
	public String getTaskNodeId(String taskId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ from ACT_RU_TASK t  ");
		buffer.append(" where id_ ='" + taskId + "'");
		String nodeId = null;
		try {
			nodeId = db.queryForSingleValue(buffer.toString()) + "";
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
		}
		return nodeId;
	}
	
	/**
	 * 获取当前节点id
	 * @param procinstid
	 * @return
	 */
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
	public String getNextTaskNodeId(String taskId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ from ACT_RU_TASK t  ");
		buffer.append(" where id_ ='" + taskId + "'");
		String nodeId = null;
		try {
			nodeId = db.queryForSingleValue(buffer.toString()) + "";
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
	
	/**
	 * 查询办公室负责人信息
	 * @return
	 */
	public Map<String, Object> getBgsfzr() {
		String sql = "select distinct(r.guid)as guid from ZC_SYS_JSRYB t left join gx_sys_ryb r on r.rybh=t.rybh where t.jsbh='"
				+ Constant.BGSFZR + "' and rownum=1";
		return db.queryForMap(sql);
	}
	
	/**
	 * 查询经费信息
	 * @param guid
	 * @param searchValue
	 * @param rybh
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getJsList(String guid, String searchValue,
			String rybh, String sql) {
		return db.queryForList(sql);
	}
}
