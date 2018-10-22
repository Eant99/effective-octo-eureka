package com.googosoft.service.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.commons.JumpTaskCmd;
import com.googosoft.util.ProcessDefUtils;

@Component
public class WorkflowService {

	Logger logger = Logger.getLogger(WorkflowService.class);

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	HistoryService historyService;

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	ProcessEngineConfiguration processEngineConfiguration;

	@Autowired
	ProcessEngineFactoryBean processEngineFactory;
	
	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	TaskService taskService;
	/**
	 * 查看当前流程图
	 * @param response
	 * @param processInstanceId
	 * @throws IOException
	 */
	public void viewProcessGraphic(HttpServletResponse response,
			String processInstanceId) throws IOException {
		// 获取历史流程实例
		HistoricProcessInstance processInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 获取流程图
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance
				.getProcessDefinitionId());
		processEngineConfiguration = processEngineFactory
				.getProcessEngineConfiguration();
		ProcessDiagramGenerator diagramGenerator = processEngineConfiguration
				.getProcessDiagramGenerator();
		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processInstance.getProcessDefinitionId());
		List<HistoricActivityInstance> highLightedActivitList = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.list();
		// 高亮环节id集合
		List<String> highLightedActivitis = new ArrayList<String>();
		// 高亮线路id集合
		List<String> highLightedFlows = getHighLightedFlows(definitionEntity,
				highLightedActivitList);

		for (HistoricActivityInstance tempActivity : highLightedActivitList) {
			String activityId = tempActivity.getActivityId();
			highLightedActivitis.add(activityId);
		}
		// 中文显示的是口口口，设置字体就好了
		InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel,
				"png", highLightedActivitis, highLightedFlows,
				processEngineConfiguration.getActivityFontName(),
				processEngineConfiguration.getLabelFontName(),
				processEngineConfiguration.getAnnotationFontName(),
				processEngineConfiguration.getClassLoader(), 1.0);
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	private List<String> getHighLightedFlows(
			ProcessDefinitionEntity processDefinitionEntity,
			List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
		for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
			ActivityImpl activityImpl = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i)
							.getActivityId());// 得到节点定义的详细信息
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
			ActivityImpl sameActivityImpl1 = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i + 1)
							.getActivityId());
			// 将后面第一个节点放在时间相同节点的集合里
			sameStartTimeNodes.add(sameActivityImpl1);
			for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
				HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
				HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点
				if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
					// 如果第一个节点和第二个节点开始时间相同保存
					ActivityImpl sameActivityImpl2 = processDefinitionEntity.findActivity(activityImpl2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				} else {
					// 有不相同跳出循环
					break;
				}
			}
			List<PvmTransition> pvmTransitions = activityImpl
					.getOutgoingTransitions();// 取出节点的所有出去的线
			for (PvmTransition pvmTransition : pvmTransitions) {
				// 对所有的线进行遍历
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
						.getDestination();
				// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
				if (sameStartTimeNodes.contains(pvmActivityImpl)) {
					highFlows.add(pvmTransition.getId());
				}
			}
		}
		return highFlows;
	}

	public ProcessInstance startProcess(Map<String, Object> variables,
			String processkey) {
		return runtimeService.startProcessInstanceByKey(processkey, variables);
	}

	/**
	 * 查询个人所有的待办任务
	 * 
	 * @param userId
	 * @return
	 */
	public List<Task> queryUserTasks(String userId) {
		logger.info("查询个人待办任务");
		return queryUserTasks(userId, 0, 10000);
	}

	/**
	 * 分页查询个人当前待办的所有任务
	 * 
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Task> queryUserTasks(String userId, int start, int end) {
		logger.info("查询个人待办任务");
		return taskService.createTaskQuery().taskAssignee(userId)
				.orderByTaskCreateTime().desc().listPage(start, end);
	}

	/**
	 * 查询个人当前当前流程下的待办的所有任务
	 * 
	 * @param userId
	 * @param processInstanceId
	 * @return
	 */
	public List<Task> queryUserTasksByProcessId(String userId, String processId) {
		logger.info("查询个人当前当前流程下的待办的所有任务");
		return queryUserTasksByProcessId(userId, processId, 0, 10000);
	}

	/**
	 * 分页查询个人当前当前流程下的待办的所有任务
	 * 
	 * @param userId
	 * @param processInstanceId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Task> queryUserTasksByProcessId(String userId,
			String processId, int start, int end) {
		logger.info("查询个人当前当前流程下的待办的所有任务");
		return taskService.createTaskQuery().taskAssignee(userId)
				.processDefinitionId(processId).orderByTaskCreateTime().desc()
				.listPage(start, end);
	}

	/**
	 * 询个人当前当前流程下的待办的所有任务
	 * 
	 * @param userId
	 * @param processkey
	 * @return
	 */
	public List<Task> queryUserTasksByProcessDefinition(String userId,
			String processkey) {

		return queryUserTasksByProcessDefinition(userId, processkey, 0, 10000);
	}

	/**
	 * 分页查询个人当前当前流程下的待办的所有任务
	 * 
	 * @param userId
	 * @param processInstanceId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Task> queryUserTasksByProcessDefinition(String userId,
			String processkey, int start, int end) {
		return taskService.createTaskQuery().taskAssignee(userId)
				.processDefinitionKey(processkey).orderByTaskCreateTime()
				.desc().listPage(start, end);
	}

	/**
	 * 查询当前用户在当前流程实例下的任务
	 * 
	 * @param userId
	 * @param processInstanceId
	 * @return
	 */
	public Task queryUserTaskByInstanceId(String userId,
			String processInstanceId) {
		return taskService.createTaskQuery().taskAssignee(userId).processInstanceId(processInstanceId).singleResult();
	}
	/**
	 * 完成任务
	 * 
	 * @param taskId
	 */
	public void completeTask(Task task) {
		logger.info("完成任务:" + task.getName() + task.getDescription());
		completeTask(task, null);
	}
	/**
	 * 完成任务
	 * 
	 * @param taskId
	 * @param variables
	 *            变量
	 */
	public void completeTask(Task task, Map<String, Object> variables) {
		if(task.getDelegationState()==DelegationState.PENDING){
			taskService.resolveTask(task.getId(), variables);
		}else{
			taskService.complete(task.getId(), variables);
		}
		logger.info("完成任务:" + task.getName());
	}
	
	private TaskEntity getCurrentTask(String procInsId) {
		return (TaskEntity) taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
	}
	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	public void jumpTask(String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables) {
		jumpTask(getCurrentTask(procInsId), targetTaskDefinitionKey, variables);
	}
	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	public void jumpTask(String procInsId, String currentTaskId, String targetTaskDefinitionKey, Map<String, Object> variables) {
		jumpTask(getTaskEntity(currentTaskId), targetTaskDefinitionKey, variables);
	}

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * @param currentTaskEntity 当前任务节点
	 * @param targetTaskDefinitionKey 目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	public void jumpTask(TaskEntity currentTaskEntity, String targetTaskDefinitionKey, Map<String, Object> variables) {
		ActivityImpl activity = ProcessDefUtils.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(),
				targetTaskDefinitionKey);
		jumpTask(currentTaskEntity, activity, variables);
	}

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * @param currentTaskEntity 当前任务节点
	 * @param targetActivity 目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	@Transactional
	private void jumpTask(TaskEntity currentTaskEntity, ActivityImpl targetActivity, Map<String, Object> variables) {
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new JumpTaskCmd(currentTaskEntity, targetActivity, variables));
	}
	private TaskEntity getTaskEntity(String taskId) {
		return (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	public void findAllActivities(String executionId,String processInstanceId){
	        Execution parentExec = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
	        
	        for (Execution e : runtimeService.createExecutionQuery().processInstanceId(parentExec.getProcessInstanceId()).list()) {
	            ExecutionEntity ee = (ExecutionEntity) e;
	            ReadOnlyProcessDefinition processDef = (ReadOnlyProcessDefinition) repositoryService.getProcessDefinition(ee.getProcessDefinitionId());
	            PvmActivity activity = processDef.findActivity(ee.getActivityId());
	            List<org.activiti.engine.impl.pvm.PvmTransition> transitions = activity.getOutgoingTransitions();
	      }
		
	}
	
	public Map<String,  FlowNode> findNextTask(String taskId,String processInstanceId) throws Exception{
		Map<String, org.activiti.bpmn.model.FlowNode> nodeMap = new HashMap<String, org.activiti.bpmn.model.FlowNode>();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
							.processInstanceId(processInstanceId)
							.singleResult();
		//查询当前节点
		HistoricTaskInstance histask =historyService.createHistoricTaskInstanceQuery()
									  .taskId(taskId).singleResult();
		//查询流程定义 。
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		List<org.activiti.bpmn.model.Process> listp = bpmnModel.getProcesses();
		org.activiti.bpmn.model.Process process = listp.get(0);
		//当前节点流定义
		FlowNode sourceFlowElement = ( FlowNode) process.getFlowElement(histask.getTaskDefinitionKey());
//			找到当前任务的流程变量
		List<HistoricVariableInstance> listVar=historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).list() ; 
		iteratorNextNodes(process, sourceFlowElement, nodeMap,listVar);
		return nodeMap;
		}


		/**
		* 查询流程当前节点的下一步节点。用于流程提示时的提示。
		* @param process
		* @param sourceFlowElement
		* @param nodeMap
		* @param listVar
		* @throws Exception
		*/
		private void iteratorNextNodes(org.activiti.bpmn.model.Process process, FlowNode sourceFlowElement, Map<String,  FlowNode> nodeMap,List<HistoricVariableInstance> listVar)
		throws Exception {
		List<SequenceFlow> list = sourceFlowElement.getOutgoingFlows();
		for (SequenceFlow sf : list) {
		sourceFlowElement = ( FlowNode) process.getFlowElement( sf.getTargetRef());
		if(StringUtils.isNotEmpty(sf.getConditionExpression() )){
		    ExpressionFactory factory = new ExpressionFactoryImpl();  
		            SimpleContext context = new SimpleContext();  
		            for(HistoricVariableInstance var :listVar){
		            context.setVariable(var.getVariableName(), factory.createValueExpression(var.getValue(), var.getValue().getClass()));
		            } 
		            ValueExpression e = factory.createValueExpression(context, sf.getConditionExpression(), boolean.class);  
		             if((Boolean)e.getValue(context)){
		            nodeMap.put(sourceFlowElement.getId(), sourceFlowElement);
		break;
		             }
		}
		if (sourceFlowElement instanceof org.activiti.bpmn.model.UserTask) {
		nodeMap.put(sourceFlowElement.getId(), sourceFlowElement);
		break;
		}else if (sourceFlowElement instanceof org.activiti.bpmn.model.ExclusiveGateway) { 
		iteratorNextNodes(process, sourceFlowElement, nodeMap,listVar);
		}
		}
		}

}
