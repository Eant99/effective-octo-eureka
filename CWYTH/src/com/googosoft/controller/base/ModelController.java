package com.googosoft.controller.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLStreamException;

import org.activiti.engine.repository.Model;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.GlobalContants;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.pojo.ModelVo;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.ModelService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;

/**
 * 流程设计控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value="/model")
public class ModelController extends BaseController{
	private Logger logger = Logger.getLogger(ModelController.class);
	@Resource(name="modelService")
	ModelService modelService;
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	
	/**
	 * 流程设计界面
	 * @return
	 */
	@RequestMapping(value="/getPage")
	public ModelAndView getPage(){ 
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("models/list");
		return mv;
	}
	/**
	 * 流程设计界面
	 * @return
	 */
	@RequestMapping(value="/goDrPage")
	public ModelAndView goDrPage(){ 
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("models/modelDr_edit");
		return mv;
	}
	
	/**当前登录人发起的流程界面
	 * 
	 * @return
	 */
	@RequestMapping(value="/getProcessPage")
	public ModelAndView getProcessPage(){ 
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("models/model_myStartProcessList");
		return mv;
	}
	/**
	 * 查询当前登录人待办
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDbPage")
	public String listDb(ModelMap modelMap,HttpSession session) throws Exception {
		String userid = CommonUtils.getRybh();
		modelMap.addAttribute("tasks", modelService.findAlldb(userid));
		return "models/dblist";
	}
	/**
	 * 查询所有流程信息
	 * 
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/view")
	@ResponseBody
	public Object listModels() throws Exception {
	     String saas=CommonUtils.getSaas();
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		PageList pageList = new PageList();
		sqltext.append(" distinct RES.NAME_  ,res.id_ ,to_char(res.create_time_,'yyyy-mm-dd') as CREATE_TIME_ , to_char(res.LAST_UPDATE_TIME_,'yyyy-mm-dd') as LAST_UPDATE_TIME_,k.bssj,decode(k.sfbs,'1','已部署','未部署') as sfbss,k.sfbs,decode(trim(k.lx),'2','发文流程','3','督察督办','5','会议申请','9','通知公告','10','自定义表单') as lcmxmc ");
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("id_");//主键
		pageList.setTableName(" ACT_RE_MODEL RES left join oa_key k on k.modelid=res.id_ ");//表名
		pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 查询当前登录人发起的流程
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myStartProcessList")
	@ResponseBody
	public Object myStartProcess() throws Exception {
		String userid =LUser.getRybh();
		StringBuffer tablename = new StringBuffer();//查询字段
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		pageList.setSqlText("*");
		pageList.setKeyId("id_");//主键
		tablename.append("(select distinct RES.id_ as id_ ,RES.name_ as name_,res.PROC_INST_ID_ as PROC_INST_ID_,to_char(res.START_TIME_,'yyyy-mm-dd') as START_TIME_ ,to_char(res.END_TIME_,'yyyy-mm-dd') as END_TIME_ ,res.START_USER_ID_ as START_USER_ID_,res.DURATION_ as DURATION_ ,DEF.KEY_ as PROC_DEF_KEY_, DEF.NAME_ as PROC_DEF_NAME_ , DEF.VERSION_ as PROC_DEF_VERSION_, DEF.DEPLOYMENT_ID_ as DEPLOYMENT_ID_");
		tablename.append(" from ACT_HI_PROCINST RES  left outer join ACT_RE_PROCDEF  DEF on RES.PROC_DEF_ID_ = DEF.ID_ ) T");
		pageList.setTableName(tablename.toString());//表名
		pageList.setStrWhere(" and T.START_USER_ID_ = '"+userid+"'");
		pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 加载申请页面
	 */ 
	@RequestMapping(value = "/{modelId}/startlc")
	public String Startlc(ModelMap modelMap,@PathVariable String modelId,HttpSession session) throws Exception {
		modelMap.addAttribute("models", modelService.Startlc(modelId,session));
		return "models/view";
	}
	/**
	 * 加载申请页面
	 */
	@RequestMapping(value = "/doCheckStartForm")
	@ResponseBody
	@Transactional 
	public String doCheckStartForm(ModelMap modelMap,String modelId,HttpSession session) throws Exception {
			boolean flag = modelService.checkStartForm(modelId, session);
			String b = "";
			if (flag) {
				b = "{\"success\":\"true\"}";
			} else {
				b = "{\"success\":\"false\"}";
			}
			return b;
		
	}
	
	@RequestMapping(value = "shenhe")
	public String ShenHe(ModelMap modelMap,String taskId,String processInstanceId) throws Exception {
		System.out.println("taskid==================="+taskId);
		modelMap.addAttribute("models", modelService.ShenHe(taskId,processInstanceId));
		return "models/views";
	}
	@RequestMapping(value = "/editModel")
	public String editModel(ModelMap modelMap, String cmd, String modelId) {
		String forward="";
		switch (cmd) {
		case GlobalContants.ADD:
			forward="models/editModel";
			break;
		case GlobalContants.IMPORT:
			forward="models/importModel";
			break;
		}
		return forward;
	}
	/**
	 * 保存流程模型信息，并跳转到编辑页面
	 * @param mv
	 * @param cmd
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/saveModel")
	@ResponseBody
	public Object saveModel(ModelVo mv, String cmd)
			throws UnsupportedEncodingException {
		String redirect = "";
		switch (cmd) {
		case GlobalContants.UPDATE:
			break;
		case GlobalContants.ADD:
			Model m = modelService.addModel(mv);
			redirect =m.getId();
			break;
		}
		return redirect;
	}
	/**
	 * 获取模型的json数据
	 * @param modelId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{modelId}/json")
	public  ObjectNode getEditorJson(@PathVariable String modelId) {
		ObjectNode on=modelService.getEditorJson(modelId);
		return on;
	}
	
	/**
	 * 流程导入
	 * @param files
	 * @param lx
	 * @return
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	@RequestMapping("/saveImport")
	@ResponseBody
	public Object uploads(@RequestParam("file")MultipartFile[] files,String lx) throws IOException, XMLStreamException {
	    	modelService.importModel(files,lx);
	    	  return "{\"error\":\"\",\"msg\":\"success\"}";
	}
	/**
	 * 保存流程模型
	 * @param modelId
	 * @param values
	 */
	@RequestMapping(value = "/{modelId}/save", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) {
		modelService.saveModel(modelId, values);
	}
	/**
	 * 部署流程
	 * @param modelMap
	 * @param modelId
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/{modelId}/deploy")
	@ResponseBody
	public  Object deployModel(ModelMap modelMap,@PathVariable String modelId) throws JsonProcessingException, IOException  {
		boolean flag=modelService.deployModel(modelId);
		String b="";
		if (flag) { 
			b = "{\"success\":\"true\"}";
		} else {
			b = "{\"success\":\"false\"}";
		}
		return b;
	}
	/**
	 * 导出流程模型
	 * @param modelMap
	 * @param modelId
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/{modelId}/export")
	public ResponseEntity<byte[]> exportModel(ModelMap modelMap,@PathVariable String modelId) throws JsonProcessingException, IOException {
		 return modelService.exportModel(modelId);
	}
	/***
	 * 删除流程模型
	 * @param modelMap
	 * @param modelId
	 * @return			
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/{modelId}/delete")
	@ResponseBody
	public Object deleteModel(ModelMap modelMap,@PathVariable String modelId) throws JsonProcessingException, IOException {
		modelService.deleteModel(modelId);
		return "models/list";
	}
	
	@RequestMapping(value = "/{modelId}/viewGraphic")
	public String viewGraphic(HttpServletRequest request,ModelMap modelMap,@PathVariable String modelId) throws JsonProcessingException, IOException {
		String path =modelService.viewGraphic(request,modelId);
		modelMap.addAttribute("imgpath", path);
		return "models/viewModelGraphic";
	}
	
	/**
	 * 跳转流程添加页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String bh = UUID.randomUUID().toString().replaceAll("-", "");
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", bh);
		mv.setViewName("models/model_add");
		return mv;
	}
	@RequestMapping(value = "/getStartFormAndStartProcess")
	@ResponseBody
	public String getStartFormAndStartProcess(HttpServletRequest request,HttpSession session,ModelMap modelMap,String cmd,String pass,String processInstanceId,String applyUser) throws Exception {
		String forward="";
		Map<String, String[]> formMap = request.getParameterMap();  
		switch (cmd) {
		case GlobalContants.ADD:
			//保存请假信息
			Map content=modelService.getStartFormAndStartProcess(request); 
			modelMap.addAttribute("models", modelService.findAllModels());
			forward="models/list";
			break;
			//审核信息
		case GlobalContants.APPROVE:
			//退回
			if(WorkflowContant.REJECT.equals(pass)){
				modelService.rejectLeaveInfo(processInstanceId,applyUser);
			}else{
				//通过
				modelService.approveInfo(processInstanceId, applyUser,request);
			}
			forward="leave/listApprove";
			break;
		
		}
		
		return forward;
	}
	/**
	 * 加载审核页面
	 * @param modelMap
	 * @param taskId
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "viewhis")
	public String Viewhis(ModelMap modelMap,String taskId,String processInstanceId) throws Exception {
		modelMap.addAttribute("his", modelService.Viewhis(taskId,processInstanceId));
		return "model/hislist";
	}
	/**
	 * 
	 */
	@RequestMapping(value = "/doCheckAdd")
	@ResponseBody
	public Object doCheckAdd(HttpServletRequest request,ModelMap modelMap,String modelId) throws Exception{
		PageData pd = this.getPageData();
		int flag =modelService.doCheckAdd(request, modelId);
		String b = "";
		if (flag>0) { 
			b = "{\"success\":\"true\"}";
		} else {
			b = "{\"success\":\"false\"}";
		}
		return b;
	}
}
