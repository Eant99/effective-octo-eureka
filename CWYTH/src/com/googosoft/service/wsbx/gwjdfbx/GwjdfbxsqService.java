package com.googosoft.service.wsbx.gwjdfbx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
import com.googosoft.constant.Constant;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.controller.wsbx.gwjdfbx.gwjdfbxsqExportExcel;
import com.googosoft.dao.wsbx.gwjdfbx.GwjdfbxsqDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("gwjdfbxsqService")
public class GwjdfbxsqService extends BaseService{

	@Resource(name="gwjdfbxsqDao")
	private GwjdfbxsqDao gwjdfbxsqDao;
	@Resource(name="pzlrService")
	private PzlrService pzlrService;
	@Resource(name="identityService")
	private IdentityService identityService;
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	
	//公务接待费报销主表，添加，更新，单行查询,删除，检查，提交
	@Transactional
	public int addGwjdmx(PageData pd) {
		gwjdfbxsqDao.insertGwjdmx(pd);
		String[] spspbh_arr = pd.getString("gwjdsqspGuid").split(",");
		String bxbh = pd.getString("guid");
		int i = 0;
		for (String sqspbh : spspbh_arr) {
			i += gwjdfbxsqDao.insertGwjdbxdz(bxbh, sqspbh);
		}
		return i;
	}
	public int editGwjdmx(PageData pd) {
		return gwjdfbxsqDao.updateGwjdmx(pd);
	}
	public Map<String, Object> getGwjdmxMapById(PageData pd){
		String guid = pd.getString("guid");
		return gwjdfbxsqDao.selectGwjdmxMapById(guid);
	}
	public int deleteGwjdfbxsq(PageData pd) {
		return gwjdfbxsqDao.deleteGwjdfbxsq(pd);
	}
	public boolean checkDjbhExist(PageData pd) {
		if("".equals(gwjdfbxsqDao.selectDjbhByDjbh(pd))) {
			return false;
		}
		return true;
	}
//	public int submit(PageData pd) {
//		return gwjdfbxsqDao.submit(pd);
//	}
	public int verify(PageData pd) {
		return gwjdfbxsqDao.verify(pd);
	}
	//
	@Transactional
	public int editJsfs(PageData pd) {
		String guid = pd.getString("guid");
		Gson gson = new Gson();
		Map<String, Object> gwkJson = gson.fromJson(pd.getString("gwkForm"), new TypeToken<HashMap<String,Object>>(){}.getType());
		if(gwkJson != null) {
			gwjdfbxsqDao.updateZffs("sfgwk", "1", guid);
			List<Map<String,Object>> gwkList = (List<Map<String, Object>>) gwkJson.get("list");
			gwjdfbxsqDao.deleteZffs(pd, "gwk");
			for (Map<String, Object> map : gwkList) {
				if(Validate.noNull(map.get("skje"))){
					if(!"0.00".equals(map.get("skje"))){
						gwjdfbxsqDao.insertGwk(map,guid);
					}
				}
			}
		}else{
			gwjdfbxsqDao.updateZffs("sfgwk", "0", guid);
			gwjdfbxsqDao.deleteZffs(pd, "gwk");
		}
		//
		Map<String, Object> cjkJson = gson.fromJson(pd.getString("cxForm"), new TypeToken<HashMap<String,Object>>(){}.getType());
		if(cjkJson != null) {
			gwjdfbxsqDao.updateZffs("sfcjk", "1", guid);
			List<Map<String,Object>> cjkList = (List<Map<String, Object>>) cjkJson.get("list");
			gwjdfbxsqDao.deleteZffs(pd, "cjk");
			for (Map<String, Object> map : cjkList) {
				if(Validate.noNull(map.get("cjkje"))){
					if(!"0.00".equals(map.get("cjkje"))){
						gwjdfbxsqDao.insertCjk(map,guid);
					}
				}
			}
		}else{
			gwjdfbxsqDao.updateZffs("sfcjk", "0", guid);
			gwjdfbxsqDao.deleteZffs(pd, "cjk");
		}
		//
		Map<String, Object> dgzfJson = gson.fromJson(pd.getString("dgForm"), new TypeToken<HashMap<String,Object>>(){}.getType());
		if(dgzfJson != null) {
			gwjdfbxsqDao.updateZffs("sfdgzf", "1", guid);
			List<Map<String,Object>> dgzfList = (List<Map<String, Object>>) dgzfJson.get("list");
			gwjdfbxsqDao.deleteZffs(pd, "dgzf");
			for (Map<String, Object> map : dgzfList) {
				if(Validate.noNull(map.get("je"))){
					if(!"0.00".equals(map.get("je"))) {
						gwjdfbxsqDao.insertDgzf(map,guid);
					}
				}
			}
		}else{
			gwjdfbxsqDao.updateZffs("sfdgzf", "0", guid);
			gwjdfbxsqDao.deleteZffs(pd, "dgzf");
		}
		//
		Map<String, Object> dszfJson = gson.fromJson(pd.getString("dsForm"), new TypeToken<HashMap<String,Object>>(){}.getType());
		if(dszfJson != null) {
			gwjdfbxsqDao.updateZffs("sfdszf", "1", guid);
			List<Map<String,Object>> dszfList = (List<Map<String, Object>>) dszfJson.get("list");
			gwjdfbxsqDao.deleteZffs(pd, "dszf");
			for (Map<String, Object> map : dszfList) {
				if(Validate.noNull(map.get("je"))){
					if(!"0.00".equals(map.get("je"))) {
						gwjdfbxsqDao.insertDszf(map,guid);
					}
				}
			}
		}else{
			gwjdfbxsqDao.updateZffs("sfdszf", "0", guid);
			gwjdfbxsqDao.deleteZffs(pd, "dszf");
		}
		return 1;
	}
	//
	public List<Map<String, Object>> selectZffs(PageData pd,String table){
		return gwjdfbxsqDao.selectZffs(pd, table);
	}
	public int updatesfwqbc(String zbid) {
		return gwjdfbxsqDao.updatesfwqbc(zbid);
	}
	public Map checkIsSubmit(String zbid) {
		return gwjdfbxsqDao.checkIsSubmit(zbid);
	}
	/**
	 * 提交
	 * 
	 * @param guid
	 * @return
	 */
	@Transactional
	public String submit(String guid, String shyj, String shzt,String key) {
		
		Map<String, Object> variables = new HashMap<String, Object>();
		Map gwjdmx=gwjdfbxsqDao.selectGwjdmxMapById(guid);
		double fyje=Double.parseDouble(""+gwjdmx.get("bxje"));
		String sfxy=Validate.isNullToDefaultString(gwjdmx.get("sfxy"), "0");
		identityService.setAuthenticatedUserId(LUser.getGuid());
		variables.put("fyje", fyje);//费用报销金额
		if((fyje>3000&&sfxy.equals("1"))||(fyje>2000&&sfxy.equals("0"))){
			variables.put("fyjelx", true);//判断是否经由部门分管领导审核
		}else{
			variables.put("fyjelx", false);//判断是否经由部门分管领导审核
		}
		//财务预审后的类型判断：科研，非科研，公务接待费，日常报销只需要考虑是否科研
		String sfkyl = Validate.isNullToDefaultString(gwjdmx.get("SFKYLBX"), "0");
		if("0".equals(sfkyl)){
			variables.put("fky", false);// 非科研
		}else{
			variables.put("ky", false);// 科研
		}
		variables.put("gwjd", true);
		ProcessInstance ps=null;
	    ps = workflowService.startProcess(variables,"bxlc");
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), ps.getId());
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		gwjdfbxsqDao.doUpdateProcinstId(guid, ps.getId());//往业务表里添加流程id
		gwjdfbxsqDao.doStatus(guid,"1","");//修改主表审核状态
//		Map map = gwjdfbxsqDao.getBxrStatus(guid);// 提交人的各种信息
		return ps.getId();
	}
	/**
	 * 审批通过
	 * @param leave
	 */
	@Transactional
	 public void approveLeaveInfo(HttpSession session, OA_SHYJB shyjb,String guid,
			String procinstid,String shyj) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey=gwjdfbxsqDao.getTaskNodeId(task.getId());
		String shbz=gwjdfbxsqDao.getNextTaskNodeId(task.getId());//通过任务id获取
		String syr="";
		String sfbj="";
		String blr="";
		Map<String, Object> variabless = runtimeService
				.getVariables(procinstid);
		String sqr= variabless.get("applyUser")+"";
		//非科研
		String fky = variabless.get("fky")+"";
		//公务接待
		String gwjd = variabless.get("gwjd")+"";
		//科研
		String ky = variabless.get("ky")+"";
		List list=new ArrayList();
		if("cwys".equals(taskDefKey)){
			if("true".equals(ky)&&!"true".equals(fky)&&!"true".equals(gwjd)){
				int result = gwjdfbxsqDao.doStatus(guid,"2",shyj);//修改主表审核状态科研处负责人审核
			}
			if("true".equals(fky)&&!"true".equals(ky)&&!"true".equals(gwjd)){
				int result = gwjdfbxsqDao.doStatus(guid,"3",shyj);//修改主表审核状态部门负责人审核
			}
			if("true".equals(gwjd)&&!"true".equals(fky)&&!"true".equals(ky)){
				int result = gwjdfbxsqDao.doStatus(guid,"16",shyj);//修改主表审核状态办公室负责人审核
			}
		}
		
		if("bgsfzr".equals(taskDefKey)||"bmfzrsh".equals(taskDefKey)||"kycfzrsh".equals(taskDefKey)){
			
				int result = gwjdfbxsqDao.doStatus(guid,"4",shyj);//修改主表审核状态办公室负责人审核
		}
		if("cwfzrsh".equals(taskDefKey)){
				int result = gwjdfbxsqDao.doStatus(guid,"5",shyj);//财务负责人审核通过，提交给部门分管领导
		}
		if("bmfgldsh".equals(taskDefKey)){
			int result = gwjdfbxsqDao.doStatus(guid,"6",shyj);//部门分管领导审核通过，提交给财务分管领导
	    }
		if("cwfgldsh".equals(taskDefKey)){
			int result = gwjdfbxsqDao.doStatus(guid,"7",shyj);//财务分管领导审核通过，提交给校长
	    }
		
		if("xzsh".equals(taskDefKey)){
			int result = gwjdfbxsqDao.doStatus(guid,"8",shyj);//校长审核通过
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		if(Validate.isNull(gwjdfbxsqDao.getFinId(procinstid))){
			int result = gwjdfbxsqDao.doStatus(guid,"8",shyj);//审核通过
			if(result>0&&1==3){
				gwjdfbxsqDao.updateQkje(guid);
			}
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		gwjdfbxsqDao.doAddShyj(shyjb);
	}

	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfo(HttpSession session, String guid,
			String procinstid, String shyj, OA_SHYJB shyjb) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(
				LUser.getGuid(), procinstid);
		String taskDefKey = gwjdfbxsqDao.getTaskNodeId(task.getId());
		variables.put("pass", false);
		workflowService.completeTask(task, variables);
		if ("cwys".equals(taskDefKey)) {
			int result = gwjdfbxsqDao.doStatus(guid, "9", shyj);// 财务预审
		}
		if ("bmfzrsh".equals(taskDefKey)) {
			int result = gwjdfbxsqDao.doStatus(guid, "11", shyj);//部门负责人
		}
		if ("bgsfzr".equals(taskDefKey)) {
			int result = gwjdfbxsqDao.doStatus(guid, "17", shyj);// 办公室负责人
		}
		if ("kycfzrsh".equals(taskDefKey)) {
			int result = gwjdfbxsqDao.doStatus(guid, "10", shyj);// 财务预审
		}
		if ("cwfzrsh".equals(taskDefKey)) {
			int result = gwjdfbxsqDao.doStatus(guid, "12", shyj);// 财务负责人
		}
		if ("bmfgldsh".equals(taskDefKey)) {
			int result = gwjdfbxsqDao.doStatus(guid, "13", shyj);// 部门分管领导
		}
		if ("cwfgldsh".equals(taskDefKey)) {
			int result = gwjdfbxsqDao.doStatus(guid, "14", shyj);// 财务分管领导
		}
		if ("xzsh".equals(taskDefKey)) {
			int result = gwjdfbxsqDao.doStatus(guid, "15", shyj);// 校长
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		gwjdfbxsqDao.doAddShyj(shyjb);
	}
	
	public Map getgwjd(String guid){
		return gwjdfbxsqDao.getgwjd(guid);
	}

	public Object expExcel(String realfile, String shortfileurl, String guid,
			String searchValue, String sql) {
		List<Map<String, Object>> dwList = this.gwjdfbxsqDao.getDcList(guid,searchValue, sql);
		String Title = "公务接待费报销申请";
		String[] title = new String[] { "序号", "审核状态", "单据编号", "报销人员", "所在部门","报销金额", "接待场所", "接待日期" };
		Map<String, Object> dataMap = gwjdfbxsqExportExcel.exportExcel(realfile,shortfileurl, title, Title, dwList);
		return dataMap;
	}
	//获得当前登录人银行卡list
	public List<Map<String,Object>> getdlryhklist(){
		return gwjdfbxsqDao.getdlryhklist();
	}
	/**
	 * 凭证退回  公务接待
	 * @param fjzs
	 * @param jdsy
	 * @param guid
	 * @return
	 */
	public int doUpdateGWJD(String fjzs, String jdsy, String guid) {
		return gwjdfbxsqDao.doUpdateGWJD(fjzs,jdsy,guid);
	}
	/**
	 * 凭证退回  复核
	 * @param guid
	 * @return
	 */
	public int GwjdbxSubmit(String guid) {
		return gwjdfbxsqDao.GwjdbxSubmit(guid);
	}
	/**
	 * 获取系统设置  的 公务接待 的 项目的guid
	 * @return
	 */
	public String getGwjdfXmguid() {
		return gwjdfbxsqDao.getGwjdfXmguid();
	}
	
}
