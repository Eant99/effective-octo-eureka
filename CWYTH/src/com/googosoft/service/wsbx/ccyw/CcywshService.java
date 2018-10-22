package com.googosoft.service.wsbx.ccyw;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.commons.LUser;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.dao.wsbx.ccyw.CcywshDao;
import com.googosoft.dao.wsbx.ccyw.CcywsqDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.util.Validate;

/**
 * 出差业务审核service
 * @author googosoft
 *
 */
@Service("ccywshService")
public class CcywshService extends BaseService{

	@Resource(name="ccywshDao")
	private CcywshDao ccywshDao;
	@Resource(name="ccywsqDao")
	private CcywsqDao ccywsqDao;
	@Resource(name="identityService")
	private IdentityService identityService;
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	
	
	
//	
//	@Transactional
//	public int verify(PageData pd) {
//		String guid = pd.getString("guid");
//		String shzt = pd.getString("shzt");
//		ccywshDao.updateShzt(pd);
//		String[] arr = guid.split("','");
//		for (String str : arr) {
//			Map<String, Object> ccywsq = ccywsqDao.selectCcywsqMapById(str);
//			if("1".equals(ccywsq.get("sfyzf"))&&"04".equals(shzt)) {
//				ccywshDao.insertYzfsqspb(pd, ccywsq);				
//			}
//		}
//		return arr.length;
//	}
	/**
	 * 审批通过
	 * @param leave
	 */
	@Transactional
	 public void approveLeaveInfo(HttpSession session, OA_SHYJB shyjb,String guid,String procinstid,String shyj) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey = ccywshDao.getTaskNodeId(task.getId());
		
		if("bzr".equals(taskDefKey)){//报账人审核通过
			String roles = Validate.isNullToDefaultString(runtimeService.getVariables(procinstid).get("role"), "false");
			if("false".equals(roles)){
				ccywshDao.doStatus(guid, "02", shyj,"11");
			}else{
				ccywshDao.doStatus(guid, "03", shyj,"11");
			}
		}
		
		if("dwldsh".equals(taskDefKey)){
			ccywshDao.doStatus(guid, "03", shyj,"11");//部门负责任审核通过，提交给分管领导审核
		}
		if("fgld".equals(taskDefKey)){
			ccywshDao.doStatus(guid, "06", shyj,"99");//分管领导审核通过
			ccywshDao.insertYzfsqspb(guid);
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		ccywshDao.doAddShyj(shyjb);
	}

	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfo(HttpSession session,OA_SHYJB shyjb, String guid,String procinstid, String shyj,String apply) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey = ccywshDao.getTaskNodeId(task.getId());
		Map<String, Object> var = runtimeService.getVariables(procinstid);
		String checkshzt = "11";
		boolean start = false;
		if("1".equals(apply)){
			start = true;
			checkshzt = "00";
		}
		variables.put("start", start);
		boolean role = (boolean) var.get("role");
		variables.put("pass", false);
		variables.put("role", role);
		workflowService.completeTask(task, variables);
		String sqr = Validate.isNullToDefaultString(ccywshDao.getFinId(procinstid),"");
		if("sqr".equals(sqr)){
			checkshzt = "00";
		}
		if ("dwldsh".equals(taskDefKey)) {
			ccywshDao.doStatus(guid, "04", shyj,checkshzt);// 部门负责任审核退回
		}
		if ("fgld".equals(taskDefKey)) {
			ccywshDao.doStatus(guid, "05", shyj,checkshzt);//分管领导审核退回
		}
		if ("bzr".equals(taskDefKey)) {//报账人退回
			ccywshDao.doStatus(guid, "012", shyj,"00");
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		ccywshDao.doAddShyj(shyjb);
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
