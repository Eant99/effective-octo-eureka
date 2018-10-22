package com.googosoft.service.wsbx;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.dao.base.ModelDao;
import com.googosoft.dao.wsbx.CcbxDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.CW_CLFBXMXB;
import com.googosoft.pojo.wsbx.CW_CLFBXZB;
import com.googosoft.pojo.wsbx.CW_FPXXB;
import com.googosoft.pojo.wsbx.CW_TXRYXXB;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.util.Validate;

@Service("ccbxService")
public class CcbxService {
	@Resource(name = "ccbxDao")
	private CcbxDao ccbxDao;
	@Resource(name="pzlrService")
	private PzlrService pzlrService;
	@Autowired
	IdentityService identityService;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	ModelDao modelDao;
	@Autowired
	private RuntimeService runtimeService;
	/**
	 * 费用新增
	 * 
	 * @param list
	 * @return
	 */
	public int doAddZb(CW_CLFBXZB clfbxzb) {
		return ccbxDao.doZbAdd(clfbxzb);
	}
	
	
	/**
	 * 费用新增
	 * 
	 * @param list
	 * @return
	 */
	public int doUpdateZb(CW_CLFBXZB clfbxzb) {
		return ccbxDao.doZbUpdate(clfbxzb);
	}
	/**
	 * 先删除后新增
	 * @param zbid
	 */
	public void deleteZb(String zbid){
		ccbxDao.Zbdelete(zbid);
	}
	/**
	 * 费用回显
	 * @param zbId
	 * @return
	 */
	public  Map<String, Object> getZbPage(String zbId){
		return ccbxDao.getZbPage(zbId);
	}
	
	public List getFpListByZbguid(String zbId){
		return ccbxDao.getFpListByZbguid(zbId);
	}
	public List getMxListByZbguid(String zbId){
		return ccbxDao.getMxListByZbguid(zbId);
	}
	public Map getMapByguid(String zbId){
		return ccbxDao.getMapByguid(zbId);
	}
	public Map getMapByguid2(String zbId){
		return ccbxDao.getMapByguid2(zbId);
	}
	public List getMxListByZbguid2(String zbId){
		return ccbxDao.getMxListByZbguid2(zbId);
	}
	public List getMxListByZbguid4(String zbId){
		return ccbxDao.getMxListByZbguid4(zbId);
	}
	
	public void deleteFphByZbguid(String zbguid) {
		   ccbxDao.deleteFphByZbguid(zbguid);
			
	}
	public int doAddFph(CW_FPXXB cw_fpxxb) {
		return ccbxDao.doAddFph(cw_fpxxb);
	}
		
	public void deleteMxByZbguid(String zbguid) {
	   ccbxDao.deleteMxByZbguid(zbguid);
		
	}
	public int doMxAdd(CW_CLFBXMXB clfbxmxb) {
		return ccbxDao.doMxAdd(clfbxmxb);
	}
	
	public List getRyListByZbguid(String zbId){
		return ccbxDao.getRyListByZbguid(zbId);
	}
	
	public List getRyListByZbguidByAdd(String zbId){
		return ccbxDao.getRyListByZbguidByAdd(zbId);
	}
	
	public void deleteRyByZbguid(String zbguid) {
	   ccbxDao.deleteRyByZbguid(zbguid);
		
	}
	public int doRyAdd(CW_TXRYXXB txryxxb) {
		return ccbxDao.doRyAdd(txryxxb);
	}

	public Map<String,Object> getBxzbById(String zbid){
		return ccbxDao.getBxzbById(zbid);
	}
	public Map<String,Object> checkXzxmGuid(String xzxmguid,String zbguid){
		return ccbxDao.checkXzxmGuid(xzxmguid,zbguid);
	}
	//获取选择的出差业务
	public List getCcywListByguid(String ccywguid){
		return ccbxDao.getCcywListByguid(ccywguid);
	}
	
	public String getccsyById(String guid) {
		return ccbxDao.getccsyById(guid);
	}
	/**
	 * 更新主表的结算方式
	 * @param rczb
	 * @return
	 */
	public int updateBxzbById(CW_CLFBXZB clfbxzb){
		return ccbxDao.updateBxzbById(clfbxzb);
	}
	public String selectRyGuidByRybh(String rybh){
		return ccbxDao.selectRyGuidByRybh(rybh);
	}
	/**
	 * 删除主表信息,通过触发器delete_rcbx删除关联信息
	 * 
	 * @param guid
	 * @return
	 */
	public int deleteZbInfoByGuid(String guid) {
		return ccbxDao.deleteZbInfoByGuid(guid);
	}
	
	public int updatesfwqbc(String zbid) {
		return ccbxDao.updatesfwqbc(zbid);
	}
	
	public Map checkIsSubmit(String zbid) {
		return ccbxDao.checkIsSubmit(zbid);
	}
	
	@Transactional
	public void submitLeaveInfo(CW_CLFBXZB clfbxzb, HttpSession session) {
		String userId = LUser.getGuid();
		Task task = workflowService.queryUserTaskByInstanceId(userId, clfbxzb.getProcinstid());
		workflowService.completeTask(task, null);
		ccbxDao.doUpdateShzt(clfbxzb);
	}
	/**
	 * 提交
	 * 
	 * @param guid
	 * @return
	 */
	public String submit(String guid, String shyj, String shzt,String key) {
		
		Map<String, Object> variables = new HashMap<String, Object>();
		Map ccbxmap=ccbxDao.getRcbxById(guid);
		float fyje=Float.valueOf(ccbxmap.get("BXZJE")+"");
		String sfxy=Validate.isNullToDefaultString(ccbxmap.get("sfxy"), "0");
		identityService.setAuthenticatedUserId(LUser.getGuid());
		variables.put("fyje", fyje);//费用报销金额
		if((fyje>3000&&sfxy.equals("1"))||(fyje>2000&&sfxy.equals("0"))){
			variables.put("fyjelx", true);//判断是否经由部门分管领导审核
		}else{
			variables.put("fyjelx", false);//判断是否经由部门分管领导审核
		}
		//财务预审后的类型判断：科研，非科研，公务接待费，日常报销只需要考虑是否科研
		String sfkyl = Validate.isNullToDefaultString(ccbxmap.get("SFKYLBX"), "0");
		if("0".equals(sfkyl)){
			variables.put("fky", true);// 非科研
		}else{
			variables.put("ky", true);// 科研
		}
		ProcessInstance ps=null;
	    ps = workflowService.startProcess(variables,"bxlc");
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), ps.getId());
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		ccbxDao.doUpdateProcinstId(guid, ps.getId());//往业务表里添加流程id
		int result = ccbxDao.doStatus(guid,"01","");//修改主表审核状态
//		Map map = rcbxDao.getBxrStatus(guid);// 提交人的各种信息
		return ps.getId();
	}
	
	/**
	 * 审批通过
	 * @param leave
	 */
	@Transactional
	 public void approveLeaveInfo(HttpSession session, OA_SHYJB shyjb,String guid,
			String procinstid,String shyj,String ccywguid) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> variables = new HashMap<String, Object>();
		
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey=ccbxDao.getTaskNodeId(task.getId());
		String shbz=ccbxDao.getNextTaskNodeId(task.getId());//通过任务id获取
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
				int result = ccbxDao.doStatus(guid,"2",shyj);//修改主表审核状态科研处负责人审核
			}
			if("true".equals(fky)&&!"true".equals(ky)&&!"true".equals(gwjd)){
				int result = ccbxDao.doStatus(guid,"3",shyj);//修改主表审核状态部门负责人审核
			}
			if("true".equals(gwjd)&&!"true".equals(fky)&&!"true".equals(ky)){
				int result = ccbxDao.doStatus(guid,"16",shyj);//修改主表审核状态办公室负责人审核
			}
		}
		
		if("bgsfzr".equals(taskDefKey)||"bmfzrsh".equals(taskDefKey)||"kycfzrsh".equals(taskDefKey)){
			
				int result = ccbxDao.doStatus(guid,"4",shyj);//修改主表审核状态办公室负责人审核
		}
		if("cwfzrsh".equals(taskDefKey)){
				int result = ccbxDao.doStatus(guid,"5",shyj);//财务负责人审核通过，提交给部门分管领导
		}
		if("bmfgldsh".equals(taskDefKey)){
			int result = ccbxDao.doStatus(guid,"6",shyj);//部门分管领导审核通过，提交给财务分管领导
	    }
		if("cwfgldsh".equals(taskDefKey)){
			int result = ccbxDao.doStatus(guid,"7",shyj);//财务分管领导审核通过，提交给校长
	    }
		
		if("xzsh".equals(taskDefKey)){
			int result = ccbxDao.doStatus(guid,"8",shyj);//校长审核通过
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		Map ccbxmap=ccbxDao.getRcbxById(guid);
		List<Map<String,Object>> bxzelist=ccbxDao.getshbxzelist(ccywguid);
		System.err.println("ccywguid==> "+ccywguid);
		System.err.println("bxzelist==> "+bxzelist);
		if(Validate.isNull(ccbxDao.getFinId(procinstid))){
			int result = ccbxDao.doStatus(guid,"8",shyj);//审核通过
			if(result>0&&1==3){
				String xmbh = Validate.isNullToDefaultString(ccbxmap.get("XMMC"), "");
				String bxzje = Validate.isNullToDefaultString(ccbxmap.get("BXZJE"), "0");
				if(bxzelist.size()>0) {
					for(int i=0;i<bxzelist.size();i++) {
						Object JFBH= bxzelist.get(i).get("JFBH");
						Object BCBXJE= bxzelist.get(i).get("BCBXJE");
						ccbxDao.updateJfsz(JFBH, BCBXJE);
					}
				}
				
				ccbxDao.updateQkje(guid);
			}
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		ccbxDao.doAddShyj(shyjb);
	}
	
	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfo(HttpSession session, String guid,
			String procinstid, String shyj,OA_SHYJB shyjb) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(
				LUser.getGuid(), procinstid);
		System.out.println("++++++++"+task.getId());
		String taskDefKey = ccbxDao.getTaskNodeId(task.getId());
		variables.put("pass", false);
		workflowService.completeTask(task, variables);
		if ("cwys".equals(taskDefKey)) {
			int result = ccbxDao.doStatus(guid, "9", shyj);// 财务预审
		}
		if ("bmfzrsh".equals(taskDefKey)) {
			int result = ccbxDao.doStatus(guid, "11", shyj);//部门负责人
		}
		if ("bgsfzr".equals(taskDefKey)) {
			int result = ccbxDao.doStatus(guid, "17", shyj);// 办公室负责人
		}
		if ("kycfzrsh".equals(taskDefKey)) {
			int result = ccbxDao.doStatus(guid, "10", shyj);// 财务预审
		}
		if ("cwfzrsh".equals(taskDefKey)) {
			int result = ccbxDao.doStatus(guid, "12", shyj);// 财务负责人
		}
		if ("bmfgldsh".equals(taskDefKey)) {
			int result = ccbxDao.doStatus(guid, "13", shyj);// 部门分管领导
		}
		if ("cwfgldsh".equals(taskDefKey)) {
			int result = ccbxDao.doStatus(guid, "14", shyj);// 财务分管领导
		}
		if ("xzsh".equals(taskDefKey)) {
			int result = ccbxDao.doStatus(guid, "15", shyj);// 校长
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		ccbxDao.doAddShyj(shyjb);
	}
	/**
	 * 根据项目id查询经费类型
	 * @param guid
	 * @return
	 */
	public String getJflxByGuid(String guid){
		return ccbxDao.getJflxByGuid(guid);
	}
	//删除 明细项目
	public void deleteClvxmmxb(String zbguid) {
		   ccbxDao.deleteClvxmmxb(zbguid);
	}
	//查询项目信息list
	public List getxmxxlist(String ccywguid){
		return ccbxDao.getxmxxlist(ccywguid);
	}
	//查询项目信息list--直接选择项目的
	public List getxmxxlistZjxz(String ccywguid){
		return ccbxDao.getxmxxlistZjxz(ccywguid);
	}
	//更新项目 报销金额
	public int doxmxxgx(String ccywguid,String bcbxje,String xmguid, boolean b) {
		return ccbxDao.doxmxxgx(ccywguid,bcbxje,xmguid,b);
	}
	
	//如果没有选择事前审批，手动向项目表中添加一个id
	public void addccywguid(String newccywguid) {
		 ccbxDao.addccywguid(newccywguid);
	}
	
	//如果没有选择事前审批，手动向cw_clfbxzb表中添加ccywguid
	public void updateCcywguid(String zbguid, String newccywguid,String xmguid) {
		 ccbxDao.updateCcywguid(zbguid,newccywguid,xmguid);
		
	}
	
	/**
	 * 根据主表id查询出差业务的id
	 * @param zbid
	 * @return
	 */
	public String getCcywguidByZbid(String zbid){
		return ccbxDao.getCcywguidByZbid(zbid);
	}
	/**
	 * 查询报销标准
	 * @param zbid
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getBxbz(String rylx,String bxjb,String provinceid,String cityid,String kssj,String jssj){
		List<Map<String,Object>> list = ccbxDao.getBxbz(provinceid,cityid);
		String kssjyf = kssj.substring(4, 7).replace("-0", "");
		String jssjyf = jssj.substring(4, 7).replace("-0", "");
		String zstj = "",zsqt = "",wjzstj = "",wjzsqt = "",hsf = "",jtf = "",wjyf = "";
		boolean flag1 = false,flag2 = false,flag3 = false;
		if(list.size()>0){
			Map map = list.get(0);
			zstj = Validate.isNullToDefaultString(map.get("ZSTJ"), "");//厅级住宿
			zsqt = Validate.isNullToDefaultString(map.get("ZSQT"), "");//其他住宿
			wjzstj = Validate.isNullToDefaultString(map.get("WJZSTJ"), "");;//旺季厅级住宿
			wjzsqt = Validate.isNullToDefaultString(map.get("WJZSQT"), "");;//旺季其他住宿
			hsf = Validate.isNullToDefaultString(map.get("HSF"), "");//伙食费
			jtf = Validate.isNullToDefaultString(map.get("JTF"), "");//市内交通费
			wjyf = Validate.isNullToDefaultString(map.get("WJYF"), "");//旺季月份
			
			if(!wjyf.equals("")){
				String yf[] = wjyf.split(",");
				flag1 = wjyf.indexOf(kssjyf)>0;
				flag2 = wjyf.indexOf(jssjyf)>0;
				int ks =  Integer.parseInt(kssjyf);
				int js =  Integer.parseInt(jssjyf);
				for(int i=0;i<yf.length;i++){
					int yue = Integer.parseInt(yf[i]);
					if(ks<yue&&yue<js){
						flag3=true;
					}
				}
			}
		}
		Map jsonMap = new HashMap();
		if(rylx.equals("教师")){
			jsonMap.put("hsf", hsf);
			jsonMap.put("jtf", jtf);
			if(bxjb.equals("厅局级")){
				if(flag1||flag2||flag3){//旺季
					jsonMap.put("zsf", wjzstj);
				}else{//非旺季
					jsonMap.put("zsf", zstj);
				}
			}else{
				if(flag1||flag2||flag3){
					jsonMap.put("zsf", wjzsqt);
				}else{
					jsonMap.put("zsf", zsqt);
				}
			}
		}
		Gson json = new Gson();
		return json.toJson(jsonMap);
	}
	
	public List<Map<String,Object>> checkFph(String zbid){
		return ccbxDao.checkFph(zbid);
	}
	public String checkFphs(){
		return ccbxDao.checkFphs();
	}
	/**
	 * 获取报销级别通过人员工号
	 * @return
	 */
	public String getBxjbByRygh(String rygh){
		return ccbxDao.getBxjbByRygh(rygh);
	}
	/**
	 * 保存 凭证退回  差旅费 
	 * @param clfbxzb
	 * @return
	 */
	public int doUpdateCLF(CW_CLFBXZB clfbxzb) {
		return ccbxDao.doUpdateCLF(clfbxzb);
	}
	public int doUpdateCLFMX(String fjzs, String guid) {
		return ccbxDao.doUpdateCLFMX(fjzs,guid);
	}
	/**
	 * 凭证退回  差旅费 复核
	 * @param guid
	 * @return
	 */
	public int ClfbxSubmit(String guid) {
		return ccbxDao.ClfbxSubmit(guid);
	}
	/**
	 * /若走事前审批单子，根据出差事前审批单号，查项目编号。
	 * @param ccywguid
	 * @return
	 */
	public String getSqspXmguid(String ccywguid) {
		return ccbxDao.getSqspXmguid(ccywguid);
	}
}
