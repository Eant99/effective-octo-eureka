package com.googosoft.service.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googosoft.constant.ModelDataJsonConstants;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.dao.base.ModelDao;
import com.googosoft.pojo.ModelVo;
import com.googosoft.pojo.OA_KEY;
import com.googosoft.pojo.TaskModel;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;
import com.googosoft.util.XmlUtil;


@Service("modelService")
public class ModelService {

	protected static final Logger LOGGER = Logger.getLogger(ModelService.class);

	@Autowired
	RepositoryService repositoryService;
	 @Autowired
	 IdentityService identityService;
	 @Autowired
	 FormService orformService;
	 @Autowired
		WorkflowService workflowService;
	 @Autowired
	  private RuntimeService runtimeService;
	 @Autowired
	  private TaskService taskService;
	 @Autowired
		com.googosoft.service.base.FormService formService;
	 @Autowired
     HistoryService historyService;
	 @Autowired
	 ModelDao modelDao;

	/**
	 * 查询所有的流程记录
	 * 
	 * @return
	 */
	public List<Model> findAllModels() {
		return repositoryService.createModelQuery().list(); 
	}
	/**
	 * 查询当前登录人代办
	 * @param userid
	 * @return
	 */
	public List<Task> findAlldb(String userid){
		
	return	taskService.createTaskQuery().taskAssignee(userid).list();
	}
	/**
	 * 查询当前登录人发起的流程
	 * @param userid
	 * @return
	 */
	public List<HistoricProcessInstance> myStartProcess(String userid){
		
	return	historyService.createHistoricProcessInstanceQuery().startedBy(userid).list();
	}
	/**
	 * 根据model
	 * 
	 * @param modelId
	 * @return
	 */
	public Model findModelById(String modelId) {
		return repositoryService.getModel(modelId);
	}

	/**
	 * 添加流程
	 * 
	 * @param model
	 * @throws UnsupportedEncodingException
	 */
	public Model addModel(ModelVo mv) throws UnsupportedEncodingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		Model modelData = repositoryService.newModel();
		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put("name", mv.getName());
	                  
		modelObjectNode.put("revision", 1);
		String description = null;
		if (StringUtils.isNotEmpty(mv.getDesc())) {
			description = mv.getDesc();
		} else {
			description = "";
		}
		String mxlx="";
		if (StringUtils.isNotEmpty(mv.getMxlx())) {
			mxlx = mv.getMxlx();
		} else {
			mxlx = "";
		}
		modelObjectNode.put("description", description);
		modelObjectNode.put("mxlx", mxlx);
		modelData.setMetaInfo(modelObjectNode.toString());
		modelData.setName(mv.getName());
		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
		mv.setGid(modelData.getId());
		modelDao.updateModelSaasByid(mv);
		System.err.print("添加流程===========");
		return modelData;
	}

	public ObjectNode getEditorJson(String modelId) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode modelNode = null;
		Model model = repositoryService.getModel(modelId);
		if (model != null) {
			try {
				if (StringUtils.isNotEmpty(model.getMetaInfo())) {
					modelNode = (ObjectNode) objectMapper.readTree(model
							.getMetaInfo());
				} else {
					modelNode = objectMapper.createObjectNode();
					modelNode.put(ModelDataJsonConstants.MODEL_NAME,
							model.getName());
				}
				modelNode.put(ModelDataJsonConstants.MODEL_ID, model.getId());
				ObjectNode editorJsonNode = (ObjectNode) objectMapper
						.readTree(new String(repositoryService
								.getModelEditorSource(model.getId()), "utf-8"));
				modelNode.put("model", editorJsonNode);
			} catch (Exception e) {
				throw new ActivitiException("Error creating model JSON", e);
			}
		}
		return modelNode;
	}
	public void saveModel(String modelId, MultiValueMap<String, String> values) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Model model = repositoryService.getModel(modelId);
			ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model
					.getMetaInfo());
			modelJson.put(ModelDataJsonConstants.MODEL_NAME,
					values.getFirst("name"));
			modelJson.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
					values.getFirst("description"));
			model.setMetaInfo(modelJson.toString());
			model.setName(values.getFirst("name"));
			repositoryService.saveModel(model);
			String jsonxml=values.getFirst("json_xml");
			JSONObject  jasonObject = JSONObject.parseObject(jsonxml);
			
			Map map=jasonObject.getJSONObject("properties");
			String processid=map.get("process_id")+"";
			/*if(Validate.noNull(processid)){
				String[] ids=processid.split("_");
				if(ids[ids.length-1].equals(CommonUtils.getSaas())){*/
					repositoryService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));
				/*}else{
					String process_id=map.get("process_id")+"_"+CommonUtils.getSaas();
				     map.put("process_id", process_id);
				     jasonObject.remove("properties");
				   
				     jasonObject.put("properties", map);
				    
					repositoryService.addModelEditorSource(model.getId(), jasonObject.toJSONString().getBytes("utf-8"));
					
				}
				
			}*/
			
			InputStream svgStream = new ByteArrayInputStream(values.getFirst(
					"svg_xml").getBytes("utf-8"));
			TranscoderInput input = new TranscoderInput(svgStream);
			PNGTranscoder transcoder = new PNGTranscoder();
			// Setup output
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			TranscoderOutput output = new TranscoderOutput(outStream);
			// Do the transformation
			transcoder.transcode(input, output);
			final byte[] result = outStream.toByteArray();
			repositoryService.addModelEditorSourceExtra(model.getId(), result);
			outStream.close();
			
			
		
			String mapId=jasonObject.get("resourceId")+"";
			if(map.size()>0){
				Map mapInfo=modelDao.findMxlx(mapId);
				String metaInfo=mapInfo.get("META_INFO_")+"";
				JSONObject  jasonObjects = JSONObject.parseObject(metaInfo);
				String lx=jasonObjects.get("mxlx")+"";
				OA_KEY key=new OA_KEY();
				key.setLx(lx);
				key.setKey(map.get("process_id")+"");
				key.setModelid(model.getId()+"");
				modelDao.saveModelInfo(key);
			}
			
		} catch (Exception e) {
			LOGGER.error("Error saving model", e);
			throw new ActivitiException("Error saving model", e);
		}
	}

	/**
	 * 部署流程
	 * 
	 * @param modelId
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public boolean deployModel(String modelId) throws JsonProcessingException,
			IOException {
		Model modelData = repositoryService.getModel(modelId);
		Model model = repositoryService.getModel(modelId);

		ObjectNode modelNode = (ObjectNode) new ObjectMapper()
				.readTree(repositoryService.getModelEditorSource(modelData
						.getId()));
		BpmnModel model1 = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model1);
		DeploymentBuilder db = repositoryService.createDeployment()
				.name(modelData.getName());
		List<JsonNode> forms = modelNode
				.findValues("formkeydefinition");
		for (JsonNode node : forms) {
			// aaa.form
			String formName = node.textValue();
			if (!"".equals(formName)) {
				// 就是页面的html代码根据formName找到
				String formContent = formService.findFormByName(formName);
				System.out.println("formContentformContentformContentformContentformContentformContentformContentformC"+formContent);
				ByteArrayInputStream bi = new ByteArrayInputStream(
						formContent.getBytes());
				db.addInputStream(formName, bi);
//				break;
			}
		}
		Model dataModel = repositoryService.getModel(modelId);
		String processName = modelData.getName() + ".bpmn20.xml";
		try {
			Deployment deployment = db
					.addString(processName, new String(bpmnBytes,"UTF-8")).deploy();
			dataModel.setDeploymentId(deployment.getId());
			repositoryService.saveModel(dataModel);
			modelDao.bushu(modelId);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return false;
		}
		
	}

	public ResponseEntity<byte[]> exportModel(String moderId)
			throws JsonProcessingException, IOException {
		Model modelData = repositoryService.getModel(moderId);
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		JsonNode editorNode = new ObjectMapper().readTree(repositoryService
				.getModelEditorSource(modelData.getId()));
		BpmnModel model = jsonConverter.convertToBpmnModel(editorNode);
		String filename = model.getMainProcess().getId() + ".bpmn20.xml";
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attchement;filename=" + filename);
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(bpmnBytes,
				headers, statusCode);
		return entity;
	}
	public void deleteModel(String modelId) {
		Model model = repositoryService.getModel(modelId);
		if (model != null) {
			repositoryService.deleteModel(model.getId());
			modelDao.doDelete(modelId);
		}
	}

	public void importModel(MultipartFile[] files,String lx) throws IOException, XMLStreamException {
		for (MultipartFile file : files) {
			Model modelData = null;
			byte[] bpmnBytes = file.getBytes();
			XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
			InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(bpmnBytes), "UTF-8");
			XMLStreamReader xtr = xif.createXMLStreamReader(in);
			BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
			if (bpmnModel.getMainProcess() == null
					|| bpmnModel.getMainProcess().getId() == null) {
				throw new IOException("不合法的工作流文件");
			} else {
				if (bpmnModel.getLocationMap().isEmpty()) {
					throw new IOException("不合法的工作流文件");
				} else {
					String processName = null;
					if (StringUtils.isNotEmpty(bpmnModel.getMainProcess()
							.getName())) {
						processName = bpmnModel.getMainProcess().getName();
					} else {
						processName = bpmnModel.getMainProcess().getId();
					}
						Process pr=bpmnModel.getMainProcess();
					modelData = repositoryService.newModel();
					ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
					modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME,processName);
					modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION,1);
					if(Validate.noNull(lx)){
						modelObjectNode.put("mxlx",lx);
					}
					modelObjectNode.put("description","");

					modelData.setMetaInfo(modelObjectNode.toString());
					modelData.setName(processName);
					repositoryService.saveModel(modelData);
					BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
					ObjectNode editorNode = jsonConverter
							.convertToJson(bpmnModel);
					repositoryService.addModelEditorSource(modelData.getId(),
							editorNode.toString().getBytes("utf-8"));
					ModelVo mv=new ModelVo();
					mv.setGid(modelData.getId());
					modelDao.updateModelSaasByid(mv);
					OA_KEY key=new OA_KEY();
					key.setLx(lx);
					key.setKey(bpmnModel.getMainProcess().getId());
					key.setModelid(modelData.getId());
					modelDao.saveModelInfo(key);
				}
			}
		}
	}
	public String viewGraphic(HttpServletRequest request,String modelId) throws IOException {
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String filename = modelId+ df.format(new Date()) + ".png";
		Model modelData = repositoryService.getModel(modelId);
	    final byte[] editorSourceExtra = repositoryService.getModelEditorSourceExtra(modelData.getId());
		File folder =new File(request.getSession().getServletContext().getRealPath("/modelimg/"));
		if(!folder.exists()){
			folder.mkdir();
		}
		String destpath=folder.getAbsolutePath()+"/"+filename;
		FileOutputStream fos = new FileOutputStream(new File(destpath));
    	fos.write(editorSourceExtra);
    	fos.flush();
    	fos.close();
		return filename;
	}
	public int doCheckAdd(HttpServletRequest request,String modelId) throws IOException {
	   
		Model modelData = repositoryService.getModel(modelId);
	    final byte[] editorSourceExtra = repositoryService.getModelEditorSourceExtra(modelData.getId());
		int i=0;
	    if(Validate.noNull(editorSourceExtra)){
			i++; 
		}
		return i;
	}
	/**
	 * 启动流程 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getStartFormAndStartProcess(HttpServletRequest request) throws XMLStreamException  {
		Map<String, String[]> formMap = request.getParameterMap(); 
		Map<String, Object> map = new HashMap<String, Object>();
		String deploymentId=formMap.get("deploymentId")[0];
		Set<Entry<String, String[]>> entrySet = formMap.entrySet(); 
		HttpSession session=request.getSession();
		String person1 =CommonUtils.getRybh();
		String assginee=CommonUtils.getRybh();
		ProcessDefinition pd = repositoryService  
                .createProcessDefinitionQuery()  
                .deploymentId(deploymentId).singleResult();  
        String processDefinitionId = pd.getId();  
        Map<String, String> formProperties = new HashMap<String, String>();  
        Iterator<FlowElement> iterator1 = this.findFlow(processDefinitionId);  
        // 取第一个节点，开始节点的行号  
        String row = null;  
        while (iterator1.hasNext()) {  
            FlowElement flowElement = iterator1.next();  
            row = flowElement.getXmlRowNumber() + ""; 
            break;  
        } 
        for (Entry<String, String[]> entry : entrySet) {  
            String key = entry.getKey();  
            String value = entry.getValue()[0];  
            if (!key.equals("deploymentId")) {  
                String keyString = key;  
                System.out.println(keyString+"====================================="+value);
                formProperties.put(keyString, value);  
            }  
        }  
        formProperties.put("deploymentId", deploymentId);  
        Iterator<FlowElement> iterator = this.findFlow(pd.getId());  
        int i = 1;  
        while (iterator.hasNext()) {  
            FlowElement flowElement = iterator.next(); // 申请人  
            if (flowElement.getClass().getSimpleName()  
                    .equals("UserTask")  
                    && i == 1) {  
                UserTask userTask = (UserTask) flowElement;  
                String assignee = userTask.getAssignee();  
                int index1 = assignee.indexOf("{");  
                int index2 = assignee.indexOf("}");  
                formProperties  
                .put(assignee.substring(index1 + 1, index2),  
                                person1);  
                break;  
            }  
        }  
        identityService.setAuthenticatedUserId(assginee);
        ProcessInstance processInstance = orformService.submitStartFormData(processDefinitionId,  
                        formProperties);
        Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("applyUser", assginee );
		String userId=CommonUtils.getRybh();
		Task task =workflowService.queryUserTaskByInstanceId(userId, processInstance.getId());
		workflowService.completeTask(task,variables);
		map.put("result", "success");
		return map;
	}
	private Iterator<FlowElement> findFlow(String processDefinitionId) throws XMLStreamException {
		List<ProcessDefinition> lists = repositoryService  
                .createProcessDefinitionQuery()  
                .processDefinitionId(processDefinitionId)  
                .orderByProcessDefinitionVersion().desc().list();  
        ProcessDefinition processDefinition = lists.get(0);  
        processDefinition.getCategory();  
        String resourceName = processDefinition.getResourceName();  
        InputStream inputStream = repositoryService.getResourceAsStream(  
                processDefinition.getDeploymentId(), resourceName);  
        BpmnXMLConverter converter = new BpmnXMLConverter();  
        XMLInputFactory factory = XMLInputFactory.newInstance();  
        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);  
        BpmnModel bpmnModel = converter.convertToBpmnModel(reader);  
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements(); 
        Iterator<FlowElement> iterator = flowElements.iterator();  
        return iterator;  
	}

	/**
	 * 项目启动 根据model 查询 启动表单
	 * @param modelId
	 * @return
	 */
	public Object  Startlc(String modelId,HttpSession session) {
		  Map<String,Object> varaibles = new HashMap<String,Object>();
		  String userId = CommonUtils.getRybh();
		  Model dataModel = repositoryService.getModel(modelId);
		  String deploymentId=dataModel.getDeploymentId();
		 String processDefinationId= repositoryService.createProcessDefinitionQuery()
		  					.deploymentId(deploymentId)
		  					.singleResult().getId();
		  varaibles.put("applyUser", userId);
		  String obj= orformService.getRenderedStartForm(processDefinationId)+"";
		 
		  System.out.println("====================deploymentId=========="+deploymentId);
		  obj=obj+"<input type='hidden' name='deploymentId' value='"+deploymentId+"'>";
		 return obj;
		
	}
	/**
	 * 检查启动表单
	 * @param modelId
	 * @return
	 */
	public boolean  checkStartForm(String modelId,HttpSession session) {
		  Model dataModel = repositoryService.getModel(modelId);
		  String deploymentId=dataModel.getDeploymentId();
		 String processDefinationId= repositoryService.createProcessDefinitionQuery()
		  					.deploymentId(deploymentId)
		  					.singleResult().getId();
		  String obj= orformService.getRenderedStartForm(processDefinationId)+"";
		 if(Validate.isNull(obj)){
			 return false;
		 }else{
			 return true;
		 }
		  
		
		
	}
	/**
	 * 根据taskId查询表单
	 * @param taskId
	 * @return
	 */
	
	public TaskModel ShenHe(String taskId,String processInstanceId){
		String forms= orformService.getRenderedTaskForm(taskId)+"";
		TaskModel taskModel=new TaskModel();
		 Map<String, String> formData = new HashMap<String,String>();
		 Map<String, Object> variables = runtimeService
                 .getVariables(processInstanceId);
          Set<String> keysSet = variables.keySet();
          Iterator<String> keySet = keysSet.iterator();
      	while (keySet.hasNext()) {
			String key = keySet.next();
			if(!"pers".equals(key)){
				System.err.println("表单参数查询打印::::"+key);
				if(!"pass".equals(key)){
					String value = (String) variables.get(key);
					formData.put(key, value);
				}
			}
		}

          
          taskModel.setFormData(formData);
          taskModel.setLastForm(forms);
          taskModel.setProcessInstanceId(processInstanceId);
          
        return taskModel;
	}
	/**
	 * 退回审批退回
	 * @param leave
	 */
	@Transactional
	public void rejectLeaveInfo(String processInstanceId,String applyUser) {
		Task task =workflowService.queryUserTaskByInstanceId(CommonUtils.getRybh(), processInstanceId);
		Map<String,Object> variables = new HashMap<String,Object>();
		OA_SHYJB shyjb= new OA_SHYJB();	
		 shyjb.setTaskid(task.getId());
		 shyjb.setShzt(WorkflowContant.REJECT);
		modelDao.doAddShyj(shyjb);
		variables.put("pass", false);
		workflowService.completeTask(task, variables);   
		
	}
	/**
	 * 审批通过
	 * @param leave
	 */
	@Transactional
	public void approveInfo(String processInstanceId,String applyUser,HttpServletRequest request) {
	OA_SHYJB shyjb= new OA_SHYJB();		
	HttpSession session=request.getSession();
		String userId=CommonUtils.getRybh();
		Map<String, String[]> formMap = request.getParameterMap(); 
		 Map<String, Object> variables = runtimeService
                 .getVariables(processInstanceId);
          Set<String> keysSet = variables.keySet();
          Iterator<String> keySet = keysSet.iterator();
		Set<Entry<String, String[]>> entrySet = formMap.entrySet();
		Map<String, String> formProperties = new HashMap<String, String>();
		 for (Entry<String, String[]> entry : entrySet) { 
			 if(!keysSet.contains(entry.getKey())){
				 if (!entry.getKey().equals("pass")) {  
					 String value =entry.getValue()[0];
					 formProperties.put(entry.getKey(), value);  
		            }  
				
				 
			 }
	        }  
		 System.out.println("============applyUser=========="+applyUser);
		 Map<String,Object> variabless = new HashMap<String,Object>();
		Task task =workflowService.queryUserTaskByInstanceId(userId, processInstanceId);
//		taskService.addComment(task.getId(), processInstanceId, "我的审核意见");
		System.out.println("======================task===="+task.getId());
		 orformService.saveFormData(task.getId(), formProperties);
		variabless.put("pass", true);
		 shyjb.setTaskid(task.getId());
			shyjb.setShzt(WorkflowContant.PASS);
			modelDao.doAddShyj(shyjb);
		workflowService.completeTask(task,variabless);
	}
	/**
	 * 根据processInstanceId查询办理记录
	 * @param taskId
	 * @return
	 */
	
	public List<HistoricTaskInstance>  Viewhis(String taskId,String processInstanceId){
		List<HistoricTaskInstance> list=historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
        return list;
	}
}