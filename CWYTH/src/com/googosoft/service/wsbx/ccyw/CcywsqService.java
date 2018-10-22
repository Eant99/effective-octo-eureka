package com.googosoft.service.wsbx.ccyw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.controller.wsbx.ccyw.ccywsqExportExcel;
import com.googosoft.dao.wsbx.ccyw.CcywsqDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 出差业务申请service
 * @author googosoft
 *
 */
@Service("ccywsqService")
public class CcywsqService extends BaseService{
	@Resource(name="identityService")
	IdentityService identityService;
	@Resource(name="workflowService")
	WorkflowService workflowService;
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	@Resource(name="ccywsqDao")
	private CcywsqDao ccywsqDao;
	
	/**
	 * 收入项目list
	 * @return
	 */
	public List<Map<String, Object>> getCcywsqList(){
		return ccywsqDao.selectCcywsqList();
	}
	
	/**
	 * 根据guid查询详情信息
	 * @param guid
	 * @return
	 */
	public Map<String, Object> getCcywsqMapById(String guid){
		return ccywsqDao.selectCcywsqMapById(guid);
	}
	
	/**
	 * 根据guid查询同行人员信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getTxryListById(String guid){
		return ccywsqDao.selectTxryListById(guid);
	}
	
	/**
	 * 项目信息
	 * @param guid 项目的guid
	 * @return
	 */
	public List<Map<String, Object>> getXmxxListById(String guid){
		return ccywsqDao.selectXmxxListById(guid);
	}
	
	/**
	 * 查询当前登录人的单位领带
	 * @return
	 */
	public Map<String, Object> getLoginUserLd(){
		return ccywsqDao.getLoginLd();
	}
	
	/**
	 * 编辑出差业务信息
	 * @param pd
	 * @return
	 */
	@Transactional
	public int editCcywsq(PageData pd) {
		String guid = pd.getString("guid");
		ccywsqDao.updateCcywsq(pd);
		ccywsqDao.updateXcxx(pd);
		ccywsqDao.deleteTxryxx(guid);
		ccywsqDao.deleteXmxx(guid);
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			ccywsqDao.insertTxryxx(map, pd,guid);
		}
		Map<String, Object> json2 = gson.fromJson(pd.getString("json2"), new TypeToken<HashMap<String,Object>>(){}.getType());
		List<Map<String,Object>> XmxxList = (List<Map<String, Object>>) json2.get("list");
		for (Map<String, Object> map : XmxxList) {
			ccywsqDao.insertXmxx(map, pd,guid);
		}
		return 1;
	}
	
	/**
	 * 编辑出差业务信息
	 * @param pd
	 * @return
	 */
	@Transactional
	public int editCcywsqBybzr(PageData pd) {
		String guid = pd.getString("guid");
		ccywsqDao.updateCcywsqBybzr(pd);
		ccywsqDao.updateXcxx(pd);
		ccywsqDao.deleteTxryxx(guid);
		ccywsqDao.deleteXmxx(guid);
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			ccywsqDao.insertTxryxx(map, pd,guid);
		}
		Map<String, Object> json2 = gson.fromJson(pd.getString("json2"), new TypeToken<HashMap<String,Object>>(){}.getType());
		List<Map<String,Object>> XmxxList = (List<Map<String, Object>>) json2.get("list");
		for (Map<String, Object> map : XmxxList) {
			ccywsqDao.insertXmxx(map, pd,guid);
		}
		return 1;
	}
	
	/**
	 * 新增出差业务信息
	 * @param pd
	 * @return
	 */
	@Transactional
	public int addCcywsq(PageData pd) {
		String guid = pd.getString("guid");
		if(ccywsqDao.insertCcywsq(pd,guid) > 0) {
			if(ccywsqDao.insertXcxx(pd,guid) > 0) {
				Gson gson = new Gson();
				Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
				List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
				for (Map<String, Object> map : txryxxList) {
					ccywsqDao.insertTxryxx(map, pd,guid);
				}
				Map<String, Object> json2 = gson.fromJson(pd.getString("json2"), new TypeToken<HashMap<String,Object>>(){}.getType());
				List<Map<String,Object>> XmxxList = (List<Map<String, Object>>) json2.get("list");
				for (Map<String, Object> map : XmxxList) {
					ccywsqDao.insertXmxx(map, pd,guid);
				}
			}
		}
		return 1;
	}
	
	/**
	 * 删除同行人员
	 * @param pd
	 * @return
	 */
	@Transactional
	public int deleteTxryxx(PageData pd) {
		String guid = pd.getString("guid");
		return ccywsqDao.deleteTxryxx(guid);
	}
	
	/**
	 * 删除出差业务申请信息
	 * @param pd
	 * @return
	 */
	@Transactional
	public int deleteCcywsq(PageData pd) {
		String guid = pd.getString("guid");
		return ccywsqDao.deleteCcywsq(guid);
	}
	
	/**
	 * 提交申请更新业务表
	 * @param pd
	 * @return
	 */
	public int submit(PageData pd) {
		return ccywsqDao.submit(pd);
	}
	
	/**
	 * 检查单据编号是否存在
	 * @param pd
	 * @return
	 */
	public boolean checkDjbhExist(PageData pd) {
		return ccywsqDao.checkDjbhExist(pd);
	}
	
	/**
	 * 判断提交后谁是审核人，普通教师提交给单位领导，单位领导提交给分管领导
	 * @param pd
	 * @return
	 */
	public Map checkWhoSh(PageData pd) {
		String rybh = LUser.getRybh();
		String shr = "";
//		if(rybh.equals(ccywsqDao.checkWhoSh(rybh))) {
//			//自己就是部门负责人，审核人就是该单位的分管领导
//			shr = ccywsqDao.findFgld(rybh);
//		}else {
//			//普通人员，审核人就是单位领导
//			shr = ccywsqDao.findDwld(rybh);
//		}
		String loginId = Validate.isNullToDefaultString(LUser.getGuid(),"");//登录人人员编号
		List bzrlist = CommonUtil.getBmbzyList();
		boolean flag = false;
		//判断当前登陆人是否是报账人
		for (int i = 0; i < bzrlist.size(); i++) {
			if(loginId.equals(bzrlist.get(i))){
				flag=true;
			}
		}
		
		
		if(flag){
			shr = ccywsqDao.findDwld(rybh);
//			map.put("XM", dao.getBmbzy());
		}else{
//			shr = CommonUtil.getBmbzy().get("XM")+"";
//			shr=Validate.isNullToDefault(shr,"")+"";
			shr = ccywsqDao.getBmbzy();
		}
		Map map = new HashMap<>();
		map.put("shr", shr);
		return map;
	}
	
	/**
	 * 单位领导审核信息
	 * @param procinstid
	 * @return
	 */
	public Map<String, Object> getBmldsh(String procinstid){
		return ccywsqDao.getBmldsh(procinstid);
	}
	
	/**
	 * 单位分管领导审核信息
	 * @param procinstid
	 * @return
	 */
	public Map<String, Object> getFgldsh(String procinstid){
		return ccywsqDao.getFgldsh(procinstid);
	}
	
	/**
	 * 联想得到项目信息
	 * @param 
	 * @param
	 * @return
	 */
	public Map getxmxx(String xmbh) {
		return ccywsqDao.getxmxx(xmbh);
	}
	/**
	 * 提交申请流程
	 * @param guid
	 * @return
	 */
	@Transactional
	public String submitBySqr(String guid){
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> ccywspbMap = ccywsqDao.selectCcywsqMapById(guid);
		//分别给申请人和审核人赋值
		Map<String,Object> map = ccywsqDao.getLoginLd();
		String dwld = Validate.isNullToDefaultString(map.get("DWLD"), "");//部门负责人
		String fgld = Validate.isNullToDefaultString(map.get("FGLD"), "");//部门分管领导
		String loginId = Validate.isNullToDefaultString(LUser.getGuid(),"");//登录人人员编号
		boolean bool = false;
		String status = "011";
		if(loginId.equals(dwld)||dwld.equals(fgld)){//申请人是部门负责人
			bool = true;
//			status = "03";
		}
		
		variables.put("sbzr",false);
	
		variables.put("sqr",loginId);
		
		List bzrlist = CommonUtil.getBmbzyList();
		variables.put("bzrs",bzrlist);
		
		//判断当前登陆人是否是报账人
		for (int i = 0; i < bzrlist.size(); i++) {
			if(loginId.equals(bzrlist.get(i))){
				//如果申请人是报账人
				variables.put("sbzr",true);
				status = "02";
			}
		}
		
//		variables.put("bzr",Validate.isNullToDefault(CommonUtil.getBmbzy().get("GUID"),""));
		variables.put("dwld",dwld);
		variables.put("fgld",fgld);
		variables.put("role",bool);
		//添加当前用户
		identityService.setAuthenticatedUserId(LUser.getGuid());
		//启动流程sqsp
		ProcessInstance ps=null;
		Task task = null;
		String procinstid = "";
		if(Validate.noNull(ccywspbMap.get("PROCINSTID"))){
			task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), ccywspbMap.get("PROCINSTID")+"");
			procinstid = ccywspbMap.get("PROCINSTID")+"";
		}else{
			ps = workflowService.startProcess(variables,"ccyw");
			task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), ps.getId());
			procinstid = ps.getId();
		}
		//执行任务，按成后删除该任务插入流程历史记录表
		workflowService.completeTask(task, variables);
		//如果申请人是报账人,更新状态为部门负责人审核
//		if(loginId.equals(CommonUtil.getBmbzy().get("GUID"))){
//			ccywsqDao.doUpdateByProcinstId("cw_ccsqspb", guid, procinstid,status,"","02");
//		}else{
			ccywsqDao.doUpdateByProcinstId("cw_ccsqspb", guid, procinstid,status,"","11");
//		}
		return procinstid;
	}
	/**
	 * 导出出差业务申请
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl, String guid,String searchValue, String rybh, String s1, String s2, String s3,String shzt) {
		List<Map<String, Object>> dwList = this.ccywsqDao.getJsList(guid,searchValue,rybh,s1,s2,s3,shzt);
		String Title = "出差业务申请";
		String[] title = new String[] { "序号", "审核状态", "单据编号","申请人", "所在部门","出差类型","项目名称","出差天数（天）","申请日期" };
		Map<String, Object> dataMap = ccywsqExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
	/**
	 * 导出出差业务申请
	 * 新的：王承馨
	 */
	public Object expExcelNew(String realfile, String shortfileurl,String sqltext) {
		List list=ccywsqDao.getCcywsqDyList(sqltext);
		String Title = "出差业务申请";
		String[] title = new String[] { "序号", "审核状态", "单据编号","申请人", "所在部门","出差类型","项目名称","出差天数（天）","申请日期" };
		Map<String, Object> dataMap = ccywsqExportExcel.exportExcel(realfile,shortfileurl, title, Title,list);
		return dataMap;
	}
	/**
	 * 导出出差业务审核
	 * @return
	 */
	public Object expExcel1(String realfile, String shortfileurl, String guid,String searchValue, String rybh, String s1, String s2, String s3,String status) {
		List<Map<String, Object>> dwList = this.ccywsqDao.getShList(guid,searchValue,rybh,s1,s2,s3,status);
		String Title = "出差业务审核";
		String[] title = new String[] { "序号", "审核状态", "单据编号","申请人", "所在部门","出差类型","项目名称","出差天数（天）","申请日期" };
		Map<String, Object> dataMap = ccywsqExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
}
