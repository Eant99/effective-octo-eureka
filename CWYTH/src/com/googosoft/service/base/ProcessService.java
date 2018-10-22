package com.googosoft.service.base;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googosoft.dao.base.ProcessDao;
import com.googosoft.pojo.GProcessInstance;



@Service("processService")
public class ProcessService {
	
	@Autowired
	HistoryService historyService;
	@Autowired
	ProcessDao processDao;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	TaskService taskService; 
	@Autowired
	WorkflowService workflowService;
	
	
	
	/**
	 * 查询正在运行的任务、流程实例信息
	 * @return
	 */
	public List<GProcessInstance> findProcessInstances(){
		return processDao.findProcessInstances();
	}
	/**
	 * 查询办理记录
	 * @return
	 */
	public List<Map<String,Object>> findBljl(String id) {
		return processDao.findBljl(id);
	}
	/**
	 * 
	 * @return
	 */
	public List<Map<String,Object>> findShyj(String id) {
		return processDao.findShyj(id);
	}
	/**
	 * 查询历史任务
	 * @return
	 */
	public List<HistoricTaskInstance> findHistroyTasks(){
		return findHistroyTasks(0,1000);
	}
	
	public List<HistoricTaskInstance> findHistroyTasks(int start,int end){
		List<HistoricTaskInstance> historicTasks =historyService.createHistoricTaskInstanceQuery()
			  	.orderByTaskCreateTime()
				.desc()
				.listPage(start, end);
		return historicTasks;
	}
	
	/**
	 * 查询历史了流程
	 * @param start
	 * @param end
	 * @return
	 */
	public List<HistoricProcessInstance> findHistoricInstances() {
		return findHistoricInstances(0,1000);
	}
	
	public List<HistoricProcessInstance> findHistoricInstances(int start,int end) {
		List<HistoricProcessInstance> historicInstances = historyService.createHistoricProcessInstanceQuery()
				.orderByProcessInstanceEndTime().desc()
				.finished()
				.listPage(start, end);
		return historicInstances;
	}
	public List<ProcessInstance> findRunProcessInstances(){
		return findRunProcessInstances(0,1000);
	}
	public List<ProcessInstance> findRunProcessInstances(int start ,int end){
		List<ProcessInstance> ps=runtimeService.createProcessInstanceQuery().listPage(start, end);
		return ps;
	}
	/**
	 * 挂起流程
	 * @param processInstanceId
	 */
	public void suspendProcessInstance(String processInstanceId) {
		runtimeService.suspendProcessInstanceById(processInstanceId);
		
	}
	/**
	 * 激活挂起的流程
	 * @param processInstanceId
	 */
	public void activeProcessInstance(String processInstanceId) {
		runtimeService.activateProcessInstanceById(processInstanceId);
	}
	/**
	 * 关闭当前流程实例
	 * @param processInstanceId
	 * @param deleteReason
	 */
	public void deleteProcessInstance(String processInstanceId,String deleteReason){
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
	}

	public void delegateTask(String taskId, String userId) {
		taskService.setAssignee(taskId, userId);
//		taskService.delegateTask(taskId, userId);
	}

	public void findAllActivities(String taskId, String processInstanceId) throws Exception {
		LinkedHashMap<String,String> nodes = new LinkedHashMap<String, String>();
		String key=taskService.createTaskQuery().taskId(taskId).singleResult().getExecutionId();
		workflowService.findAllActivities(key, processInstanceId);	
	}
	
	/**
	 * 获取所有通过的流程办理记录
	 * @param ddbh
	 * @return
	 */
	public List<Map<String,Object>> findBljlOfTg(String id) {
		return processDao.findBljlOfTg(id);
	}
	/**
	 * 获取申请人
	 * @param ddbh
	 * @return
	 */
	public List<Map<String,Object>> findBljlOfSqr(String id) {
		return processDao.findBljlOfSqr(id);
	}
	/**
	 * 获取院长和书记
	 * @author 作者：Administrator
	 * @version 创建时间:2018-5-11上午11:09:04
	 */
	public Map findYzAndSj(String procinstid) {
		return processDao.findYzAndSj(procinstid);
	}
 }
