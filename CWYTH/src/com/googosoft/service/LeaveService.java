package com.googosoft.service;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.dao.base.LeaveDao;
import com.googosoft.dao.system.user.UserDao;
import com.googosoft.pojo.LeaveModel;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.WorkflowService;



@Service
@Transactional
public class LeaveService extends BaseService{
	
	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	LeaveDao leaveDao;
	@Autowired
	UserDao userDao;
	@Autowired
	IdentityService identityService;
	
//	@Autowired
//	HysqDao hysqDao;
	@Autowired
	RuntimeService runtimeService;
	
	public List<LeaveModel> findLeaveInfoByUser(String userId){
		return leaveDao.findLeaveInfoByUser(userId);
	}
	
	/*@Transactional
	public boolean saveLeaveInfo(OA_HYSSQB hyssqb) {
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("applyUser",  hyssqb.getSqr());
		
		if(map.size()>0){
			variables.put("hygly", map.get("RYBH")+"");
		}
		ProcessInstance ps = workflowService.startProcess(variables, WorkflowContant.LEAVE);
		hyssqb.setProcinstid(ps.getId());
		int row=hysqDao.doAdd(hyssqb);
		boolean flag=false;
		if(row>0){
			doAddOplog(OplogFlag.ADD, "会议申请信息：增加", hyssqb.getGid());
			flag=true;
		}
		return flag;
	}
*/
//	/**
//	 * 审批通过
//	 * @param leave
//	 */
//	@Transactional
//	public void approveLeaveInfo(OA_HYSSQB hyssqb,HttpSession session,OA_SHYJB shyjb) {
//		String pid=hyssqb.getProcinstid();
//		Task task =workflowService.queryUserTaskByInstanceId(CommonUtils.getRybh(), pid);
//		Map<String,Object> variables = new HashMap<String,Object>();
//		variables.put("reject", WorkflowContant.PASS);
//		workflowService.completeTask(task,variables);
//		shyjb.setTaskid(task.getId());
//		shyjb.setShzt(WorkflowContant.PASS);
//		int j = hysqDao.doAddShyj(shyjb);
//		if (j > 0) {
//			doAddOplog(OplogFlag.ADD, "审核意见：增加", hyssqb.getGid());
//		}
//		int i = hysqDao.doUpdateShzt(hyssqb);
//		if (i > 0) {
//			doAddOplog(OplogFlag.UPD, "会议申请审核意见：修改", hyssqb.getGid());
//		}
//	}

	public LeaveModel findLeaveInfoByPK(String id) {
		return leaveDao.findLeaveInfoByPK(id);
	}

	public void updateLeaveInfo(LeaveModel leave) {
		
	}
	public List<LeaveModel> findLeaveApproveInfoByUser(String userid) {
		return leaveDao.findLeaveApproveInfoByUser(userid);
	}
//	/**
//	 * 退回审批退回
//	 * @param leave
//	 */                                                          
//	@Transactional
//	public void rejectLeaveInfo(OA_HYSSQB hyssqb,HttpSession session,OA_SHYJB shyjb) {
//          String pid=hyssqb.getProcinstid();
//		Map<String,Object> variables = new HashMap<String,Object>();
//		Task task =workflowService.queryUserTaskByInstanceId(CommonUtils.getRybh(), pid);
//		variables.put("reject", WorkflowContant.REJECT);
//		workflowService.completeTask(task, variables);
//		shyjb.setTaskid(task.getId());
//		shyjb.setShzt(WorkflowContant.REJECT);
//		int j = hysqDao.doAddShyj(shyjb);
//		if (j > 0) {
//			doAddOplog(OplogFlag.ADD, "审核意见：增加", hyssqb.getGid());
//		}
//		int i = hysqDao.doUpdateShzt(hyssqb);
//		if (i > 0) {
//			doAddOplog(OplogFlag.UPD, "会议申请审核意见：修改", hyssqb.getGid());
//		}
//	}
//	@Transactional
//	public void submitLeaveInfo(OA_HYSSQB hyssqb,HttpSession session) {
//		String userId=CommonUtils.getRybh();
//		Task task =workflowService.queryUserTaskByInstanceId(userId, hyssqb.getProcinstid());
//		workflowService.completeTask(task,null);
//		int i = hysqDao.doUpdateShzt(hyssqb);
//		if (i > 0) {
//			doAddOplog(OplogFlag.UPD, "会议申请审核意见：修改", hyssqb.getGid());
//		}
//	}
}
