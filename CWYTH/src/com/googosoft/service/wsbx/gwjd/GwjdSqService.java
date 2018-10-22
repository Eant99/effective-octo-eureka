package com.googosoft.service.wsbx.gwjd;

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

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.controller.wsbx.gwjdf.gwjdfsqExportExcel;
import com.googosoft.dao.wsbx.ccyw.CcywshDao;
import com.googosoft.dao.wsbx.gwjd.GwjdSqDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.gwjdf.CW_GWJDFSQ;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.wsbx.ccyw.JDJumpTaskCmd;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 公务接待service
 * @author master
 */
@Service("gwjdsqService")
public class GwjdSqService extends BaseService{
	@Resource(name="ccywshDao")
	private CcywshDao ccywshDao;
	
	@Resource(name="identityService")
	IdentityService identityService;
	
	@Resource(name="workflowService")
	WorkflowService workflowService;
	
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	
	@Resource(name="gwjdsqDao")
	public GwjdSqDao gwjdsqDao;
	
	
	/**
	 * 新增保存申请信息
	 * @param dwb
	 * @return
	 */
	public boolean doAdd(CW_GWJDFSQ gwjdsq){
		boolean result = gwjdsqDao.doAdd(gwjdsq);
		return result;
	}
	
	/**
	 * 通过后插入预支付表
	 * @param dwb
	 * @return
	 */
	public int doPassAdd(Map map){
		int result = gwjdsqDao.doPassAdd(map);
		return result;
	}
	
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getObjectById(String guid) {
		return gwjdsqDao.getObjectById(guid);
	}
	/**
	 * 获取活动信息
	 * @param string
	 * @return
	 */
	public List getMxList(String guid) {
		// TODO Auto-generated method stub
		return gwjdsqDao.getMxList(guid);
	}
	/**
	 * 获取审核信息
	 * @param string
	 * @return
	 */
	public List getYjList1(String guid) {
		// TODO Auto-generated method stub
		return gwjdsqDao.getYjList1(guid);
	}
	
	public List getYjList2(String guid) {
		// TODO Auto-generated method stub
		return gwjdsqDao.getYjList2(guid);
	}
	
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getPassObjectById(String guid) {
		return gwjdsqDao.getPassObjectById(guid);
	}
	
	/**
	 * 修改公务接待事前审批信息
	 * 
	 * @param dwb
	 * @return
	 */
	public boolean doUpdate(CW_GWJDFSQ gwjdsq) {
		boolean result = gwjdsqDao.doUpdate(gwjdsq);
		if (result) {
			doAddOplog(OplogFlag.UPD, "公务接待申请", gwjdsq.getGuid());
		}
		return result;
	}
	
	/**
	 * 查询审核人信息
	 * @param pd
	 * @return
	 */
	public Map checkWhoSh(PageData pd) {
		String rybh = LUser.getRybh();
//		String shr = gwjdsqDao.findBgsfzr(rybh);
		String shr = Validate.isNullToDefaultString(CommonUtil.getYbbzy().get("XM"), "");
		Map map = new HashMap<>();
		map.put("shr", shr);
		return map;
	}
	
	/**
	 * 删除公务接待事前审批信息
	 * @param dwb
	 * @return
	 */
	public boolean doDelete(String guid) {
		boolean result = gwjdsqDao.doDelete(guid);
		if(result){
			doAddOplog(OplogFlag.DEL,"公务接待申请",guid);
		}
		return result;
	}
	/**
	 * 提交后修改业务表的审核状态
	 * @param dwb
	 * @return
	 */
	public int xxTj(String guid) {
		int result = gwjdsqDao.xxTj(guid);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"公务接待申请提交",guid);
		}
		return result;
	}
	
	/**
	 * 审核通过后修改业务表的审核状态
	 * @param guid
	 * @param shyj
	 * @return
	 */
	public int xxPass(String guid,String shyj) {
		int result = gwjdsqDao.xxPass(guid,shyj);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"公务接待申请通过",guid);
		}
		return result;
	}
	
	/**
	 * 审核退回后修改业务表的审核状态
	 * @param guid
	 * @param shyj
	 * @return
	 */
	public int xxRefuse(String guid,String shyj) {
		int result = gwjdsqDao.xxRefuse(guid,shyj);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"公务接待申请退回",guid);
		}
		return result;
	}
	
	/**
	 * 提交申请
	 * @param guid
	 * @return
	 */
	@Transactional
	public String submitBySqr(String guid){
		Map<String, Object> variables = new HashMap<String, Object>();
		//分别给申请人和审核人赋值
		Map<String,Object> map = gwjdsqDao.getLoginLd();
		String loginId = LUser.getGuid();
		String fgld = ""+map.get("fgld");
		String status = "02";
		variables.put("sqr",loginId);
//		variables.put("fgld",fgld);
		variables.put("fgld",Validate.isNullToDefault(CommonUtil.getYbfgld().get("GUID"),""));
		variables.put("pass",true);
//		variables.put("bgsfzr",gwjdsqDao.getBgsfzr().get("guid"));
		Map<String, Object> info = new HashMap<String, Object>();
		variables.put("bgsfzr",Validate.isNullToDefault(CommonUtil.getYbbzy().get("GUID"),""));
		info = gwjdsqDao.getMapInfoByGuid(guid);
		String procinstid = Validate.isNullToDefaultString(info.get("PROCINSTID"), "");
		if(Validate.isNull(procinstid)){
			//添加当前用户
			identityService.setAuthenticatedUserId(LUser.getGuid());
			//启动流程sqsp
			ProcessInstance ps=null;
		    ps = workflowService.startProcess(variables,"gwjdsp");
			
			 procinstid = ps.getId();
		}
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		//执行任务，按成后删除该任务插入流程历史记录表
		workflowService.completeTask(task, variables);
		gwjdsqDao.doUpdateByProcinstId("cw_gwjdywsqspb", guid, procinstid,status,"","11");
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
		String taskDefKey = gwjdsqDao.getTaskNodeId(task.getId());
		if("bgsfzrsh".equals(taskDefKey)){
			gwjdsqDao.doStatus(guid, "04", shyj,"11");//部门负责任审核通过，提交给分管领导审核
		}
		if("fgldsh".equals(taskDefKey)){
			gwjdsqDao.doStatus(guid, "06", shyj,"99");//分管领导审核通过
			gwjdsqDao.insertYzfsqspb(guid);
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		gwjdsqDao.doAddShyj(shyjb);
	}

	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfo(HttpSession session, String guid,String procinstid, String shyj,String apply,OA_SHYJB shyjb) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey = gwjdsqDao.getTaskNodeId(task.getId());
		String checkshzt = "11";
		boolean start = false;
		if(apply.equals("1")){//退回到申请人
//			Map<String, Object> vars = new HashMap<String, Object>();  
//			String[] v = { "shareniu1", "shareniu2", "shareniu3", "shareniu4" };  
//			vars.put("assigneeList", Arrays.asList(v));  
//			//分享牛原创(尊重原创 转载对的时候第一行请注明，转载出处来自分享牛http://blog.csdn.net/qq_30739519) 
//			 ProcessEngine engine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
//			RepositoryService repositoryService = engine.getRepositoryService();  
//			String taskDefId = ccywshDao.getTaskDefId(task.getId());
//			ReadOnlyProcessDefinition processDefinitionEntity = (ReadOnlyProcessDefinition) repositoryService  
//			.getProcessDefinition(taskDefId);
//			//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
//	        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
//	                    .processInstanceId(procinstid)//使用流程实例ID查询
//	                    .singleResult();
//	        //获取当前活动的id
//	        String activityId = pi.getActivityId();
//			// 目标节点  
//			ActivityImpl destinationActivity = (ActivityImpl) processDefinitionEntity  
//			.findActivity("sqr");
//			String executionId = procinstid;  
//			// 当前节点  
//			   ActivityImpl currentActivity = (ActivityImpl)processDefinitionEntity  
//			      .findActivity(activityId);
//			   engine.getManagementService().executeCommand(new JDJumpTaskCmd(executionId, destinationActivity, vars,currentActivity));
//			 ccywshDao.insertACTINST(activityId,procinstid);//更新历史表，否则当前人员已审核单子，找不到该条。
			start = true;
			checkshzt="00";
		}
		variables.put("pass", false);
		variables.put("start", start);
		workflowService.completeTask(task, variables);
		String hh = gwjdsqDao.getFinId(procinstid);
		if("sqrsq".equals(gwjdsqDao.getFinId(procinstid))){
			checkshzt="00";
		}
		if ("bgsfzrsh".equals(taskDefKey)) {
			gwjdsqDao.doStatus(guid, "03", shyj,checkshzt);// 部门负责任审核退回
		}
		if ("fgldsh".equals(taskDefKey)) {
			gwjdsqDao.doStatus(guid, "05", shyj,checkshzt);//分管领导审核通过
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		gwjdsqDao.doAddShyj(shyjb);
	}
	
	/**
	 * 导出教师信息Excel   wzd
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl, String guid,String searchValue, String rybh, String sql) {
		List<Map<String, Object>> dwList = this.gwjdsqDao.getJsList(guid,searchValue,rybh,sql);
		String Title = "公务接待申请";
		String[] title = new String[] { "序号", "审批状态", "单据编号","申请人", "接待部门","接待日期","来宾单位","接待地点","申请日期" };
		Map<String, Object> dataMap = gwjdfsqExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
	
	/**
	 * 导出教师信息Excel   wzd
	 * @return
	 */
	public Object expExcel1(String realfile, String shortfileurl, String guid,String searchValue, String rybh, String sql) {
		List<Map<String, Object>> dwList = this.gwjdsqDao.getJsList(guid,searchValue,rybh,sql);
		String Title = "公务接待审核";
		String[] title = new String[] { "序号", "审批状态", "单据编号","申请人", "接待部门","接待日期","来宾单位","接待饭店","申请日期" };
		Map<String, Object> dataMap = gwjdfsqExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
}
