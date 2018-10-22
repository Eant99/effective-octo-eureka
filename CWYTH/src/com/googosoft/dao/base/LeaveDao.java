package com.googosoft.dao.base;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;







import java.util.logging.SimpleFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.googosoft.pojo.LeaveModel;
import com.googosoft.util.CoderHelper;


@Repository
public class LeaveDao extends BaseDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<LeaveModel> findLeaveInfoByUser(String userId){
		System.out.println("$$$$$$$$$$$$$$##########"+userId);
		String sql ="select id,reason,duration,createdate,processinstanceid from OA_SYS_QJB where userid=?";
		List<Map<String,Object>> list=db.queryForList(sql,new Object[]{userId});
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		List<LeaveModel> leaves=new ArrayList<LeaveModel>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				LeaveModel leave = new LeaveModel();
				try {
					leave.setCreateDate(sdf.parse(list.get(i).get("createdate")+""));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				leave.setId(list.get(i).get("id")+"");
				leave.setReason(list.get(i).get("reason")+"");
				leave.setDuration(Integer.parseInt(list.get(i).get("duration")+""));
				leave.setProcessInstanceId(list.get(i).get("processinstanceid")+"");
				leaves.add(leave);
			}
		}
				//jdbcTemplate.queryForList(sql, new Object[]{userId}, LeaveModel.class);
		return leaves;
	}
	public boolean saveLeaveInfo(final LeaveModel leave) {
		String sql ="insert into OA_SYS_QJB(id,reason,duration,createdate,processinstanceid,userid) values(?,?,?,?,?,?)";
		int rows=jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, CoderHelper.generateId());
				ps.setString(2, leave.getReason());
				ps.setInt(3, leave.getDuration());
				long time =System.currentTimeMillis();
				ps.setDate(4,new Date(time));
				ps.setString(5, leave.getProcessInstanceId());
				ps.setString(6,leave.getUserId());
			}
		});
		if(rows>0)
			return true;
		return false;
	}
	
	/**
	 * 根据主键查询请假单信息
	 * @param id
	 * @return
	 */
	public LeaveModel findLeaveInfoByPK(String id) {
		String sql ="select * from OA_SYS_QJB where id=?";
		List<Map<String,Object>> list=db.queryForList(sql,new Object[]{id});
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");

		LeaveModel leave = new LeaveModel();
		if(list.size()>0){
			try {
				leave.setCreateDate(sdf.parse(list.get(0).get("createdate")+""));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			leave.setId(list.get(0).get("id")+"");
			leave.setReason(list.get(0).get("reason")+"");
			leave.setDuration(Integer.parseInt(list.get(0).get("duration")+""));
			leave.setProcessInstanceId(list.get(0).get("processinstanceid")+"");
		}
		
		return leave;
	}
	/**
	 * 查询当前用户待审批的请假信息
	 * @param userid
	 * @return
	 */
	public List<LeaveModel> findLeaveApproveInfoByUser(String userid) {
		String sql ="select l.*,rt.proc_inst_id_ from OA_SYS_QJB l inner join act_ru_task rt on l.processinstanceid=rt.proc_inst_id_ where rt.task_def_key_!='start_task' and rt.assignee_=?";
		List<Map<String,Object>> list=db.queryForList(sql,new Object[]{userid});
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		List<LeaveModel> leaves=new ArrayList<LeaveModel>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				LeaveModel leave = new LeaveModel();
				try {
					leave.setCreateDate(sdf.parse(list.get(i).get("createdate")+""));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				leave.setId(list.get(i).get("id")+"");
				leave.setReason(list.get(i).get("reason")+"");
				leave.setDuration(Integer.parseInt(list.get(i).get("duration")+""));
				leave.setProcessInstanceId(list.get(i).get("processinstanceid")+"");
				leaves.add(leave);
			}
		}
		return leaves;
	}
	/**
	 * 更新请假单信息
	 * @param leave
	 */
	public void updateLeaveInfo(final LeaveModel leave) {
		String sql ="update OA_SYS_QJB set processinstanceid=? where id=?";
		int rows=jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, leave.getProcessInstanceId());
				ps.setString(2, leave.getId());
			}
		});
	}
	public String getUserType(String userId){
		String sql="select jsbh from OA_SYS_QJB where rybh= ?";
		Map<String,Object> curUser=jdbcTemplate.queryForMap(sql, new Object[]{userId});
		return (String) curUser.get("jsbh");
	}

}
