package com.googosoft.dao.wsbx.ccyw;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.wsbx.gwjdfbx.GwjdfbxsqDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;

/**
 * 出差业务审核dao
 * @author googosoft
 *
 */
@Repository("ccywshDao")
public class CcywshDao extends BaseDao{
	private Logger logger = Logger.getLogger(GwjdfbxsqDao.class);
	public int updateShzt(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "update cw_ccsqspb set shzt = ?,shyj = ?,shr = ?,shrq = to_date(?,'yy-mm-dd hh24:mi:ss'),czr = ?,czrq = to_date(?,'yy-mm-dd hh24:mi:ss') where guid in('"+guid+"')";
		Object[] obj = {
				pd.getString("shzt"),
				pd.getString("shyj"),
				pd.getString("czr"),
				DateUtil.getTime(),
				pd.getString("czr"),
				DateUtil.getTime()
		};
		return db.update(sql,obj);
	}
	
	/**
	 * 审核通过过后加入预支付信息
	 * @param guid
	 * @return
	 */
	public int insertYzfsqspb(String guid) {
		String sql = "insert into cw_yzfsqspb(guid,djbh,jkr,szbm,jkje,sqrq,czr,czrq,sqspdh,qkje) select sys_guid() as guid,djbh,(select rybh from gx_sys_ryb where guid = sqr) as jkr," + 
				"(select dwbh from gx_sys_ryb where guid = sqr) as szbm,yzfje as jkje,sqrq,czr,czrq,guid,yzfje as qkje from cw_ccsqspb where guid = ?";
		Object[] obj = {guid};
		return db.update(sql,obj); 
	}
	public int insertACTINST(String activityId,String procinstid) {
		String sql = "update ACT_HI_ACTINST set end_time_=to_char(Sysdate,'dd-mon-yy') where act_id_=? and PROC_INST_ID_=?";
		Object[] obj = {activityId,procinstid};
		return db.update(sql,obj); 
	}
	/**
	 * 更新业务表审核状态
	 * @param 
	 * @return
	 */
	public int doStatus(String ywid,String zt,String shyj,String checkshzt) {
		String sql = "UPDATE CW_CCSQSPB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+LUser.getGuid()+"',checkshzt='"+checkshzt+"' WHERE guid"+CommonUtils.getInsql(ywid);
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
	 * 获取ACT_RU_TASK表中proc_def_id字段值
	 * @author 作者：Administrator
	 * @version 创建时间:2018-3-1下午8:45:55
	 */
	public String getTaskDefId(String taskId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT PROC_DEF_ID_ from ACT_RU_TASK t  ");
		buffer.append(" where id_ ='" + taskId + "'");
		String DefId = null;
		try {
			DefId = db.queryForSingleValue(buffer.toString()) + "";
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
		}
		return DefId;
	}
	/**
	 * 查询流程的当前的节点id
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
}





















