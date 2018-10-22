package com.googosoft.controller.base;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;














import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.constant.SystemSet;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.base.ProcessService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;


@Controller
@RequestMapping(value = "/process")
public class ProcessController extends BaseController {

	@Autowired
	ProcessService processService;
	
	@Autowired
	WorkflowService workflowService;
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@RequestMapping(value="/processPage")
	public ModelAndView processPage(){ 
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/process/runProcess_list");
		return mv;
	}
	@RequestMapping(value="/historicTasksPage")
	public ModelAndView historicTasksPage(){ 
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/process/historicProcess_list");
		return mv;
	}
	@RequestMapping(value="/historicInstancesPage")
	public ModelAndView historicInstancesPage(){ 
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/process/historicProcessInstances_list");
		return mv;
	}
	@RequestMapping(value="/getBljlPage")
	public ModelAndView getBljlPage(){ 
		ModelAndView mv = this.getModelAndView();
		HttpServletRequest request=this.getRequest();
		String processInstanceId=request.getParameter("processInstanceId");
		List<Map<String,Object>> bljl=processService.findBljl(processInstanceId);
		for(Map map : bljl){
			if("办理".equals(map.get("TASKNAME"))&&"通过".equals(map.get("SHZT"))){
				map.put("SHZT", "已办理");
			}

		}
		mv.addObject("bljl", bljl);
		mv.setViewName("wsbx/process/bljl");
		return mv;
	}
	@RequestMapping(value="/processLs")
	public ModelAndView processLs(){ 
		ModelAndView mv = this.getModelAndView();
		String processInstanceId=this.getRequest().getParameter("processInstanceId")+"";
		String type=this.getRequest().getParameter("type")+"";
		mv.addObject("processInstanceId", processInstanceId);
		mv.addObject("type", type);
		System.out.println("=============查看历史流程==========");
		mv.setViewName("wsbx/process/viewProcessGraphic");
		return mv;
	}
	/**
	 * 查询所有流程信息
	 * 
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/viewProcessInstance")
	@ResponseBody
	public String listProcessInstances() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
	
		PageList pageList = new PageList();
		pageList.setSqlText("*");
		pageList.setKeyId("id_");//主键
		sqltext.append(" (select distinct ae.id_, at.id_ as taskid,arp.name_ pname,ae.proc_inst_id_,at.name_ taskname, (select t.xm from oa_sys_ryb t where t.rybh=at.assignee_) as assignee,to_char(ap.start_time_, 'yyyy-MM-dd HH24:mi:ss') start_time_, decode(ae.suspension_state_,'1','激活','2','挂起')  as suspension_state,(select t.xm from oa_sys_ryb t where t.rybh=ap.start_user_id_) as kqr,ae.suspension_state_ from"
				+ " act_ru_execution ae left join act_ru_task at on ae.id_ = at.execution_id_ left join act_hi_procinst ap on ap.proc_inst_id_ = at.proc_inst_id_ left join act_re_procdef arp on arp.id_ = ap.proc_def_id_ left join act_re_model m on m.deployment_id_=arp.deployment_id_ where  m.saas='"+CommonUtils.getSaas()+"')k");
		pageList.setTableName(sqltext.toString());//表名
		pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	@RequestMapping(value = "/viewHistoricTasks")
	@ResponseBody
	public String viewHistoricTasks() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		PageList pageList = new PageList();
		pageList.setSqlText("*");
		pageList.setKeyId("id_");//主键
		pageList.setTableName(" (select distinct RES.id_,res.PROC_INST_ID_,res.NAME_,(select a.xm  from "+SystemSet.sysBz+"ryb a where a.rybh=RES.assignee_) as ASSIGNEE_,to_char(res.START_TIME_,'yyyy-mm-dd') START_TIME_ ,to_char(res.END_TIME_,'yyyy-mm-dd') END_TIME_ ,res.DURATION_  from ACT_HI_TASKINST RES left join act_re_procdef arp  on arp.id_ =res.proc_def_id_ left join act_re_model m on m.deployment_id_=arp.deployment_id_ "
				+ " where  m.saas='"+CommonUtils.getSaas()+"' and res.assignee_='"+CommonUtils.getRybh()+"')k");//表名
		pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	@RequestMapping(value = "/viewProcess")
	public void viewProcess(HttpServletResponse response ,String processInstanceId) throws IOException {
		workflowService.viewProcessGraphic(response, processInstanceId);
	}
	@RequestMapping(value = "/historicProcessInstances")
	@ResponseBody
	public String viewHistoricInstances(ModelMap modelMap) throws IOException {
		PageData pd = this.getPageData();
		StringBuffer tablename = new StringBuffer();//查询字段
		PageList pageList = new PageList();
		pageList.setSqlText("*");
		pageList.setKeyId("id_");//主键
		tablename.append("(select distinct RES.id_ as id_ ,res.PROC_INST_ID_ as PROC_INST_ID_,to_char(res.START_TIME_,'yyyy-mm-dd') as START_TIME_ ,to_char(res.END_TIME_,'yyyy-mm-dd') as END_TIME_ ,res.DURATION_ as DURATION_ ,DEF.KEY_ as PROC_DEF_KEY_, DEF.NAME_ as PROC_DEF_NAME_ , DEF.VERSION_ as PROC_DEF_VERSION_, DEF.DEPLOYMENT_ID_ as DEPLOYMENT_ID_");
		tablename.append(" from ACT_HI_PROCINST RES  left  join ACT_RE_PROCDEF  DEF on RES.PROC_DEF_ID_ = DEF.ID_    left join act_re_model m on def.deployment_id_=m.deployment_id_  where m.saas='"+CommonUtils.getSaas()+"') T");
		pageList.setTableName(tablename.toString());//表名
		pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}

	
	@RequestMapping(value = "suspendProcessInstance")
	public String suspendProcess(ModelMap modelMap,String processInstanceId) throws IOException {
		processService.suspendProcessInstance(processInstanceId);
		modelMap.addAttribute("processInstances", processService.findProcessInstances());
		return "process/runProcessInstances";
	}
	@RequestMapping(value = "activeProcessInstance")
	public String activeProcess(ModelMap modelMap,String processInstanceId) throws IOException {
		processService.activeProcessInstance(processInstanceId);
		modelMap.addAttribute("processInstances", processService.findProcessInstances());
		return "process/runProcessInstances";
	}
	@RequestMapping(value = "deleteProcessInstance")
	public String deleteProcessInstance(ModelMap modelMap,String processInstanceId,String reason) throws IOException {
		processService.deleteProcessInstance(processInstanceId, reason);
		modelMap.addAttribute("processInstances", processService.findProcessInstances());
		return "process/runProcessInstances";
	}
	
	@RequestMapping(value = "delegateTask")
	public String delegateTask(ModelMap modelMap,String taskId,String userId) throws IOException {
		processService.delegateTask(taskId, userId);
		modelMap.addAttribute("processInstances", processService.findProcessInstances());
		return "process/runProcessInstances";
	}
	@RequestMapping(value="jumpProcess")
	public String jumpProcess(ModelMap modelMap,String taskId,String processInstanceId) throws Exception {
		
		processService.findAllActivities(taskId,processInstanceId);
		modelMap.addAttribute("processInstances", processService.findProcessInstances());
		return "process/runProcessInstances";
	}
}
