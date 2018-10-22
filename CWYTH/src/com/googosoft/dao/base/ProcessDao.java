package com.googosoft.dao.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;




import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.pojo.GProcessInstance;
import com.googosoft.util.CommonUtils;



@Repository
public class ProcessDao extends BaseDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<GProcessInstance> findProcessInstances() {
		StringBuffer sql = new StringBuffer();
		sql.append("select ae.id_ execution_id,");
		sql.append("arp.name_ pname,ae.proc_def_id_,");
		sql.append("ae.proc_inst_id_ as startuser,");
		sql.append("ae.proc_inst_id_, at.name_ taskname,");
		sql.append(" at.assignee_ as  assignee,");
		sql.append("to_char(ap.start_time_,'yyyy-MM-dd HH24:mi:ss') start_time_, ");
//		sql.append("(select pd.duration from pduration pd where pd.execution_id_=ae.id_) duration,");
		sql.append("ae.is_active_ from act_ru_execution ae ");
		sql.append(" left join act_ru_task at ");
		sql.append(" on ae.id_ = at.execution_id_ ");
		sql.append(" left join act_hi_procinst ap ");
		sql.append(" on ap.proc_inst_id_ = at.proc_inst_id_ ");
		sql.append(" left join act_re_procdef arp ");
		sql.append(" on arp.id_ = ap.proc_def_id_ ");
		List<GProcessInstance> processInstances =jdbcTemplate.query(sql.toString(), new Object[]{},new RowMapper<GProcessInstance>() {
			@Override
			public GProcessInstance mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				GProcessInstance processInstance = new GProcessInstance();
				processInstance.setExecution_id(rs.getString("execution_id"));
				processInstance.setPname(rs.getString("pname"));
				processInstance.setProc_def_id_(rs.getString("proc_def_id_"));
				processInstance.setStartuser(rs.getString("startuser"));
				processInstance.setProc_inst_id_(rs.getString("proc_inst_id_"));
				processInstance.setTaskname(rs.getString("taskname"));
				processInstance.setAssignee(rs.getString("assignee"));
				processInstance.setStart_time_(rs.getString("start_time_"));
//				processInstance.setDuration(rs.getLong("duration"));
				processInstance.setIs_active_(rs.getString("is_active_"));
				return processInstance;
			}
			
		});
		return processInstances;
	}
	
	
	/**
	 * 获取所有流程办理记录
	 * @param ddbh
	 * @return
	 */
	public List<Map<String,Object>> findBljl(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.NAME_ AS TASKNAME, ('('||r.rybh||')'||r.xm) as xm,s.SHYJ,to_char(T.START_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS STARTTIME,to_char(T.END_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS ENDTIME,(case s.shzt when '1' then '通过'  when '2' then '不通过' end) shzt  FROM ACT_HI_TASKINST T LEFT JOIN GX_SYS_RYB r  ON T.ASSIGNEE_ =r.GUID  left join oa_shyjb s on T.id_=s.taskid  WHERE PROC_INST_ID_ = '"+id+"' order by T.START_TIME_ desc");
		System.out.println("========打印办理记录=========="+sql);
		return db.queryForList(sql.toString());
	}
	/**
	 * 
	 * @param ddbh
	 * @return
	 */
	public List<Map<String,Object>> findShyj(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.NAME_ AS TASKNAME, ('('||r.rybh||')'||r.xm) as xm,s.SHYJ,to_char(T.START_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS STARTTIME,to_char(T.END_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS ENDTIME,"+Constant.getHysqzt("s.shzt")+"  FROM ACT_HI_TASKINST T LEFT JOIN oa_sys_ryb r  ON T.ASSIGNEE_ =r.rybh  left join oa_shyjb s on T.id_=s.taskid  WHERE PROC_INST_ID_ = '"+id+"' and s.rybh='"+CommonUtils.getRybh()+"' order by T.START_TIME_ desc");
		return db.queryForList(sql.toString());
	}
	
	/**
	 * 获取所有通过的流程办理记录
	 * @param ddbh
	 * @return
	 */
	public List<Map<String,Object>> findBljlOfTg(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.NAME_ AS TASKNAME, r.xm as xm,s.SHYJ,to_char(T.START_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS STARTTIME,to_char(T.END_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS ENDTIME,(case s.shzt when '1' then '通过'  when '2' then '不通过' end) shzt "
				+ " FROM (SELECT TASK_DEF_KEY_,MAX(T.START_TIME_) AS START_TIME_ FROM ACT_HI_TASKINST T LEFT JOIN OA_SHYJB S ON T.ID_ = S.TASKID WHERE T.PROC_INST_ID_ = '"+id+"' AND S.SHZT = '1' GROUP BY TASK_DEF_KEY_) A "
				+ "LEFT JOIN ACT_HI_TASKINST T ON A.TASK_DEF_KEY_=T.TASK_DEF_KEY_ AND A.START_TIME_=T.START_TIME_ LEFT JOIN GX_SYS_RYB r  ON T.ASSIGNEE_ =r.GUID  left join oa_shyjb s on T.id_=s.taskid "
				+ " WHERE PROC_INST_ID_ = '"+id+"' and s.shzt='1' order by T.START_TIME_ desc ");
		System.out.println("========打印办理记录==========list1"+sql);
		return db.queryForList(sql.toString());
	}
	/**
	 * 获取申请人
	 * @param ddbh
	 * @return
	 */
	public List<Map<String,Object>> findBljlOfSqr(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.NAME_ AS TASKNAME, r.xm as xm,to_char(T.START_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS STARTTIME,to_char(T.END_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS ENDTIME"
				+ " FROM (SELECT TASK_DEF_KEY_,MAX(T.START_TIME_) AS START_TIME_ FROM ACT_HI_TASKINST T  WHERE T.PROC_INST_ID_ = '"+id+"' and t.name_='申请人'  GROUP BY TASK_DEF_KEY_) A "
				+ "LEFT JOIN ACT_HI_TASKINST T ON A.TASK_DEF_KEY_=T.TASK_DEF_KEY_ AND A.START_TIME_=T.START_TIME_ LEFT JOIN GX_SYS_RYB r  ON T.ASSIGNEE_ =r.GUID   "
				+ " WHERE PROC_INST_ID_ = '"+id+"'  order by T.START_TIME_  ");
		System.out.println("========打印办理记录==========list2"+sql);
		return db.queryForList(sql.toString());
	}
	/**
	 * 获取院长和书记
	 * @author 作者：Administrator
	 * @version 创建时间:2018-5-11上午11:08:42
	 */
	public Map findYzAndSj(String procinstid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT   R.XM AS XM,TO_CHAR(T.START_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS STARTTIME FROM ");
		sql.append(" (SELECT TASK_DEF_KEY_,ASSIGNEE_,MAX(T.START_TIME_)AS START_TIME_ FROM   ACT_HI_TASKINST T ");
		sql.append(" WHERE   PROC_INST_ID_ = '"+procinstid+"' and TASK_DEF_KEY_ ='sjyz' AND  START_TIME_");
		sql.append(" NOT IN (SELECT MAX(START_TIME_) FROM  ACT_HI_TASKINST  WHERE   PROC_INST_ID_ = '"+procinstid+"') GROUP BY TASK_DEF_KEY_,ASSIGNEE_");
		sql.append(" )T  LEFT JOIN GX_SYS_RYB R ON T.ASSIGNEE_ = R.GUID  WHERE ROWNUM=1  ORDER BY t.START_TIME_ ASC");
		System.out.println("========打印办理记录==========list3"+sql);
		return db.queryForMap(sql.toString());
	}
}
