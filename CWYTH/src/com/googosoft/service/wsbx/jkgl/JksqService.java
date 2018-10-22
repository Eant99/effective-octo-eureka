package com.googosoft.service.wsbx.jkgl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.dao.wsbx.ccyw.CcywshDao;
import com.googosoft.dao.wsbx.jkgl.JksqDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.CW_JKYWB;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.wsbx.ccyw.JDJumpTaskCmd;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Service("jksqService")
public class JksqService extends BaseService {

	@Resource(name="jksqDao")
	private JksqDao jksqDao;
	
	@Resource(name="identityService")
	IdentityService identityService;
	@Resource(name="workflowService")
	WorkflowService workflowService;
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	@Resource(name="ccywshDao")
	private CcywshDao ccywshDao;

	public Map<String, Object> getObjectById(String gid) {

		return jksqDao.getObjectById(gid);
	}
	
	public boolean doDelete(String gid) {
		return jksqDao.doDelete(gid);
	}
	
	public List<Map<String, Object>> getXmxxListById(String gid){
		return jksqDao.selectXmxxListById(gid);
	}
	
	/**
	 * 对公转账list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getDgList(String gid) {
		return jksqDao.getDgList(gid);
	}
	
	/**
	 * 对私转账list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getDsList(String gid) {
		return jksqDao.getDsList(gid);
	}
	public String getXmFzr(String gid) {
		return jksqDao.getXmFzr(gid);
	}

	/**
	 * 保存 
	 * @param jksq
	 * @return
	 */
	@Transactional
	public int doAddMx(PageData pd,String gid) {
		int i = jksqDao.doAdd(pd,gid);
		if( i > 0) {
			Gson gson = new Gson();
			Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
			List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
			for (Map<String, Object> map : txryxxList) {
				jksqDao.doAddMx(map,gid);
			}
			Map<String, Object> json2 = gson.fromJson(pd.getString("json2"), new TypeToken<HashMap<String,Object>>(){}.getType());
			if(Validate.noNull(json2)){
				List<Map<String,Object>> XmxxList = (List<Map<String, Object>>) json2.get("list");
				for (Map<String, Object> map : XmxxList) {
					jksqDao.doAddDs(map,gid);
				}
			}
			Map<String, Object> json3 = gson.fromJson(pd.getString("json3"), new TypeToken<HashMap<String,Object>>(){}.getType());
			if(Validate.noNull(json3)){
				List<Map<String,Object>> XmxxList3 = (List<Map<String, Object>>) json3.get("list");
				for (Map<String, Object> map : XmxxList3) {
					jksqDao.doAddDg(map,gid);
				}
			}
			return i;
		}else{
			return i;
		}
	}
	
	/**
	 * 
	 * @param jksq
	 * @return
	 */
	@Transactional
	public int doUpdate(PageData pd,String gid) {
		if(jksqDao.doUpdate(pd,gid) > 0) {
			Gson gson = new Gson();
			Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
			List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
			jksqDao.deleteMx(pd,gid);
			for (Map<String, Object> map : txryxxList) {
				jksqDao.doAddMx(map,gid);
			}
			Map<String, Object> dszfJson = gson.fromJson(pd.getString("json2"), new TypeToken<HashMap<String,Object>>(){}.getType());
			if(Validate.noNull(dszfJson)){	
				List<Map<String,Object>> dszfList = (List<Map<String, Object>>) dszfJson.get("list");
					jksqDao.deleteDs(pd,gid);
					for (Map<String, Object> map : dszfList) {
							jksqDao.doAddDs(map,gid);
					}
				//
					}
			//
			Map<String, Object> dgzfJson = gson.fromJson(pd.getString("json3"), new TypeToken<HashMap<String,Object>>(){}.getType());
			if(Validate.noNull(dgzfJson)){	
				List<Map<String,Object>> dgzfList = (List<Map<String, Object>>) dgzfJson.get("list");
					jksqDao.deleteDg(pd,gid);
					for (Map<String, Object> map : dgzfList) {
						jksqDao.doAddDg(map,gid);
					}
				}
		}
		return 1;
	}
	
	/**
	 * 提交
	 * @param gid
	 * @return
	 */
	public int doSumbit(String gid) {
		  int a=jksqDao.doSubmit(gid);
		  return a;
	}
	
	public int doUpdate(CW_JKYWB jksq,String flag) {
		int result = jksqDao.doUpdate(jksq,flag);
		
		return result;
	}
	
	/**
	 * 提交流程
	 * @param guid
	 * @return
	 */
	public String submitBySqr(String guid){
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		info = jksqDao.getObjectById(guid);
		
		//分别给申请人和审核人赋值
		Map<String,Object> map = jksqDao.getLoginLd();
		String dwld = Validate.isNullToDefaultString(map.get("DWLD"), "");//部门负责人
		String loginId = Validate.isNullToDefaultString(LUser.getGuid(),"");//登录人人员编号
		//查询财务负责人的信息
		Map<String,Object> cwfzrmap=jksqDao.getcwfzrSql(Constant.CWFZR);
		String cwfzr=Validate.isNullToDefaultString(cwfzrmap.get("GUID"),"");
		boolean bool = false;
		String status = "02";
		bool = true;
		//本部门的报账员
		variables.put("bzy",CommonUtil.getBmbzyList());
		variables.put("sqr",loginId);
		variables.put("dwld",dwld);
		variables.put("cwfzr",cwfzr);
		variables.put("role",bool);
		//如果提交人是报账员
		if(CommonUtil.checkSbmbzy()){
			variables.put("sbzy", true);
			status = "03";
		}else{
			variables.put("sbzy", false);
		}
		//如果提交人是财务处人员
		if(LUser.getDwbh().equals(Constant.CWC)){
			variables.put("scwc", true);
			status = "05";
		}else{
			variables.put("scwc", false);
		}
		String procinstid = Validate.isNullToDefaultString(info.get("PROCINSTID"),"");
		if(Validate.isNull(procinstid)){
			//添加当前用户
			identityService.setAuthenticatedUserId(LUser.getGuid());
			//启动流程sqsp
			ProcessInstance ps=null;
		    ps = workflowService.startProcess(variables,"jklc");
			
			//执行任务，完成后删除该任务插入流程历史记录表
		    procinstid = ps.getId();
		}
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		workflowService.completeTask(task, variables);
	/*	if(LUser.getDwbh().equals(Constant.CWC)){
			workflowService.jumpTask(procinstid, "cwfzr", variables);
			status = "05";
		}*/
		jksqDao.doUpdateByProcinstId("CW_JKYWB", guid, procinstid,status,"");
		return procinstid;
	}
	
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
		if("bzy".equals(taskDefKey)){
			jksqDao.doStatus(guid, "03", shyj);//报账员审核
		}
		if("dwld".equals(taskDefKey)){
			jksqDao.doStatus(guid, "05", shyj);//单位领导审核通过
		}
		if("cwfzr".equals(taskDefKey)) {
			jksqDao.doStatus(guid, "8", shyj);//财务负责人通过
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		jksqDao.doAddShyj(shyjb);
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
			variables.put("pass", false);
			if("1".equals(apply)){
				variables.put("start", true);
			}else{
				variables.put("start", false);
			}
			
			workflowService.completeTask(task, variables);
			
		
		if ("bzy".equals(taskDefKey)) {
			jksqDao.doStatus(guid, "04", shyj);// 报账员审核退回
		}
		if ("dwld".equals(taskDefKey)) {
			if("1".equals(apply)){
				jksqDao.doStatus(guid, "16", shyj);//单位负责人审核退回
			}else{
				jksqDao.doStatus(guid, "06", shyj);//单位负责人审核退回
			}
		}
		if ("cwfzr".equals(taskDefKey)) {
			if("1".equals(apply)){
				jksqDao.doStatus(guid, "19", shyj);//财务负责人退回
			}else{
				jksqDao.doStatus(guid, "09", shyj);//财务负责人退回
			}
			/*String scwc = Validate.isNullToDefaultString(var.get("scwc"), "false");
			if("true".equals(scwc)){
				workflowService.jumpTask(procinstid, "sqr", variables);
			}*/
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		jksqDao.doAddShyj(shyjb);
	}
	
	/***************************以下为手机接口流程方法******************************/
	/**
	 * 提交流程
	 * @param guid
	 * @return
	 */
	public String sjsubmitBySqr(String guid){
		Map<String, Object> variables = new HashMap<String, Object>();
		//分别给申请人和审核人赋值
		Map<String,Object> map = jksqDao.getLoginLd();
		String dwld = Validate.isNullToDefaultString(map.get("DWLD"), "");//部门负责人
		String fgld = Validate.isNullToDefaultString(map.get("FGLD"), "");//部门分管领导
		String loginId = Validate.isNullToDefaultString(LUser.getGuid(),"");//登录人人员编号
		//查询财务负责人的信息
		Map<String,Object> cwfzrmap=jksqDao.getcwfzrSql(Constant.CWFZR);
		String cwfzr=Validate.isNullToDefaultString(cwfzrmap.get("GUID"),"");
		boolean bool = false;
		String status = "02";
		bool = true;
		variables.put("sqr",loginId);
		variables.put("dwld",dwld);
		variables.put("fgld",fgld);
		variables.put("cwfzr",cwfzr);
		variables.put("role",bool);
		//添加当前用户
		identityService.setAuthenticatedUserId(LUser.getGuid());
		//启动流程sqsp
		ProcessInstance ps=null;
	    ps = workflowService.startProcess(variables,"jklc");
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), ps.getId());
		//执行任务，按成后删除该任务插入流程历史记录表
		workflowService.completeTask(task, variables);
		String procinstid = ps.getId();
		jksqDao.doUpdateByProcinstId("CW_JKYWB", guid, procinstid,status,"");
		return procinstid;
	}
	
	/**
	 * 审批通过
	 * @param leave
	 */
	@Transactional
	 public void sjapproveLeaveInfo(HttpSession session, OA_SHYJB shyjb,String guid,String procinstid,String shyj) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey = ccywshDao.getTaskNodeId(task.getId());
		if("dwld".equals(taskDefKey)){
			jksqDao.doStatus(guid, "03", shyj);//部门负责任审核通过，提交给分管领导审核
		}
		if("fgld".equals(taskDefKey)){
			jksqDao.doStatus(guid, "05", shyj);//分管领导审核通过
		}
		if("cwfzr".equals(taskDefKey)) {
			jksqDao.doStatus(guid, "8", shyj);//财务负责人通过
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		jksqDao.doAddShyj(shyjb);
	}

	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void sjrejectleaveinfo(HttpSession session,OA_SHYJB shyjb, String guid,String procinstid, String shyj) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey = ccywshDao.getTaskNodeId(task.getId());
		Map<String, Object> var = runtimeService.getVariables(procinstid);
		boolean role = (boolean) var.get("role");
		variables.put("pass", false);
		variables.put("role", role);
		workflowService.completeTask(task, variables);
		if ("dwld".equals(taskDefKey)) {
			jksqDao.doStatus(guid, "04", shyj);// 部门负责任审核退回
		}
		if ("fgld".equals(taskDefKey)) {
			jksqDao.doStatus(guid, "06", shyj);//分管领导审核退回
		}
		if ("cwfzr".equals(taskDefKey)) {
			jksqDao.doStatus(guid, "09", shyj);//财务负责人退回
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		jksqDao.doAddShyj(shyjb);
	}
	/**
	 * 对私登录人银行
	 * @param wlbh
	 * @return
	 */
	/*public List getdlrYh(String dqdlrguid) {
		return jksqDao.getdlrYh(dqdlrguid);
	}*/
    /**
     * 凭证退回  借款
     * @param fjzs
     * @param jdsy
     * @param guid
     * @return
     */
	public int doUpdateJK(String fjzs, String jksy, String gid) {
		return jksqDao.doUpdateJK(fjzs,jksy,gid);
	}
	 /**
     * 凭证退回  借款 复核
     * @param fjzs
     * @param jdsy
     * @param guid
     * @return
     */
	public int JKbxSubmit(String gid) {
		return jksqDao.JKbxSubmit(gid);
	}
	/**
	 * 判断是否 冲过 这个 借款
	 */
	public int checksfcjk(String gid) {
		return jksqDao.checksfcjk(gid);
	}
}
