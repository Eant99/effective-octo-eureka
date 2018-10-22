package com.googosoft.service.wsbx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.base.ModelDao;
import com.googosoft.dao.wsbx.RcbxDao;
import com.googosoft.dao.wsbx.process.WsbxProcessDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.CW_CJKB;
import com.googosoft.pojo.wsbx.CW_DGZFB;
import com.googosoft.pojo.wsbx.CW_FJXXB;
import com.googosoft.pojo.wsbx.CW_GWKB;
import com.googosoft.pojo.wsbx.CW_LYEB;
import com.googosoft.pojo.wsbx.Cw_DSZFB;
import com.googosoft.pojo.wsbx.Rcbxmx;
import com.googosoft.pojo.wsbx.Rcbxzb;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("rcbxService")
public class RcbxService {
	@Resource(name = "rcbxDao")
	private RcbxDao rcbxDao;
	
	@Resource(name = "ProcessDao")
	private WsbxProcessDao dao;
	@Autowired
	PzlrService pzlrService;
	@Autowired
	IdentityService identityService;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	ModelDao modelDao;
	@Autowired
	private RuntimeService runtimeService;

	/**
	 * 获取经济科目设置树
	 */
	public Object getjjkmNodezj(PageData pd, String jb, String rootPath) {
		String icon = rootPath
				+ "/static/plugins/ext/resources/images/default/tree/folder.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if (!"1".equals(jb)) {
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = rcbxDao.jjszMenuzj(jb);
		Map map = new HashMap();
		if (list.size() > 0) {
			String kmbh = "", kmmc = "", kmjc = "", l = "", k = "";
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				map = (Map) list.get(i);
				kmbh = Validate.isNullToDefault(map.get("KMBH"), "").toString();
				kmmc = Validate.isNullToDefaultString(map.get("KMMC"), "");
				kmjc = Validate.isNullToDefaultString(map.get("KMJC"), "");
				count = rcbxDao.getCount(kmbh);
				l = Validate.isNullToDefaultString(map.get("L"), "");
				k = Validate.isNullToDefaultString(map.get("K"), "");
				if (count > 0) {
					children.add(new ExtTreeNode(kmbh, kmmc, false, true,
							false, href.length() > 0 ? href + kmbh + "&kmjc="
									+ kmjc + "&l=" + l + "&k=" + k : href,
							target, icon));

				} else {
					children.add(new ExtTreeNode(kmbh, kmmc, true, true, false,
							href.length() > 0 ? href + kmbh + "&kmjc=" + kmjc
									+ "&l=" + l + "&k=" + k : href, target));
				}
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}

	/**
	 * 学生银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getXsyhxx(String dqdlrguid) {
		return rcbxDao.getXsyhxx(dqdlrguid);
	}
	/**
	 * 教师银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getJsyhxx(String dqdlrguid) {
		return rcbxDao.getJsyhxx(dqdlrguid);
	}
	/**
	 * 校外人员银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getXwryyhxx(String dqdlrguid) {
		return rcbxDao.getXwryyhxx(dqdlrguid);
	}
	/**
	 * 费用列表
	 * 
	 * @return
	 */
	public List getFyList(String xmguid) {
		return rcbxDao.getFyList(xmguid);
	}
	/**
	 * 对私登录人银行
	 * @param wlbh
	 * @return
	 */
	public List getdlrYh(String dqdlrguid) {
		return rcbxDao.getdlrYh(dqdlrguid);
	}
	
	/**
	 *  检查发票号 是否有重复的
	 * @author 作者：Administrator
	 * @version 创建时间:2018-2-9下午5:10:09
	 */
	public List checkFpxx() {
		return rcbxDao.checkFpxx();
	}
	/**
	 * 费用新增
	 * 
	 * @param list
	 * @return
	 */
	public int doAdd(Rcbxmx rcbxmx) {
		return rcbxDao.doAdd(rcbxmx);
	}

	public int doAddZb(Rcbxzb rczb) {
		return rcbxDao.doAddzb(rczb);
	}

	/**
	 * 先删除后新增
	 * 
	 * @param zbid
	 */
	public void deleteFymx(String zbid) {
		rcbxDao.deleteFymx(zbid);
	}

	/**
	 * 费用回显
	 * 
	 * @param zbId
	 * @return
	 */
	public List getFyListByXmid(String zbId) {
		return rcbxDao.getFyListByXmid(zbId);
	}

	public List getFyListBySelect() {
		return rcbxDao.getFyListBySelect();
	}

	/**
	 * 主表信息回显
	 * 
	 * @param zbid
	 * @return
	 */
	public Map<String, Object> getBxzbById(String zbid) {
		return rcbxDao.getBxzbById(zbid);
	}

	/**
	 * 先删后增的通用删除
	 * 
	 * @param table
	 * @param col
	 * @param id
	 */
	public void deleteDatas(String table, String col, String id) {
		rcbxDao.deleteDatas(table, col, id);
	}

	public String getjsxm(String rybh){
		return rcbxDao.getjsxm(rybh);
	}
	/**
	 * 新增冲借款
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddCjk(CW_CJKB cjk) {
		return rcbxDao.doAddCjk(cjk);
	}


	/**
	 * 新增对公支付
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddDgzf(CW_DGZFB dgzf) {
		return rcbxDao.doAddDgzf(dgzf);
	}

	/**
	 * 新增对私支付
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddDszf(Cw_DSZFB dszf) {
		return rcbxDao.doAddDszf(dszf);
	}
	/***
	 * 是否完全保存
	 * @author 作者：
	 * @version 创建时间:2018-4-20下午2:03:51
	 */
	public int updatesfwqbc(String zbid) {
		return rcbxDao.updatesfwqbc(zbid);
	}
	
	public Map checkIsSubmit(String zbid) {
		return rcbxDao.checkIsSubmit(zbid);
	}

	/**
	 * 新增附件信息
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddFjxx(CW_FJXXB fjxx) {
		return rcbxDao.doAddFjxx(fjxx);
	}

	/**
	 * 新增公务卡
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddGwk(CW_GWKB gwk) {
		return rcbxDao.doAddGwk(gwk);
	}
	
	public int doAddLye(CW_LYEB lye) {
		return rcbxDao.doAddLye(lye);
	}

	public List getCxjkListHx(String zbid) {
		return rcbxDao.getCxjkListHx(zbid);
	}

	/**
	 * 所有的借款业务
	 * 
	 * @return
	 */
	public List getCxjkLists() {
		return rcbxDao.getCxjkLists();
	}

	/**
	 * 动态控制冲借款
	 * 
	 * @param guid
	 * @return
	 */
	public Map<String, Object> getCxjkMap(String guid) {
		return rcbxDao.getCxjkMap(guid);
	}

	/**
	 * 公务卡list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getGekList(String zbid) {
		return rcbxDao.getGekList(zbid);
	}
	
	public List getLyeList(String zbid) {
		return rcbxDao.getLyeList(zbid);
	}

	/**
	 * 对公转账list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getDgList(String zbid) {
		return rcbxDao.getDgList(zbid);
	}

	/**
	 * 往来单位list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getWldwList() {
		return rcbxDao.getWldwList();
	}

	/**
	 * 往来单位银行账号
	 * 
	 * @param wlbh
	 * @return
	 */
	public List getWldwYh(String wlbh) {
		return rcbxDao.getWldwYh(wlbh);
	}

	/**
	 * 对私转账list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getDsList(String zbid) {
		return rcbxDao.getDsList(zbid);
	}

	/**
	 * 查询项目负责人
	 * 
	 * @param xmguid
	 * @return
	 */
	public String getXmFzr(String xmguid) {
		return rcbxDao.getXmFzr(xmguid);
	}

	/**
	 * 更新主表的结算方式
	 * 
	 * @param rczb
	 * @return
	 */
	public int updateBxzbById(Rcbxzb rczb) {
		return rcbxDao.updateBxzbById(rczb);
	}

	/**
	 * 回显第四步的时候使用
	 * 
	 * @param zbid
	 * @return
	 */
	public Map<String, Object> getZbMapById(String zbid) {
		return rcbxDao.getZbMapById(zbid);
	}

	/**
	 * 更新审核信息
	 * 
	 * @param guid
	 * @param shzt
	 * @param shyj
	 * @param shr
	 * @return
	 */
	public int updateShById(String guid, String shzt, String shyj, String shr) {
		return rcbxDao.updateShById(guid, shzt, shyj, shr);
	}

	/**
	 * 删除主表信息,通过触发器delete_rcbx删除关联信息
	 * 
	 * @param guid
	 * @return
	 */
	public int deleteZbInfoByGuid(String guid) {
		return rcbxDao.deleteZbInfoByGuid(guid);
	}

	/**
	 * 提交
	 * 
	 * @param guid
	 * @return 流程procinstid
	 */
	public String submit(String guid, String shyj, String shzt,String key) {
		
		Map<String, Object> variables = new HashMap<String, Object>();
		Map rcbxmap=rcbxDao.getRcbxById(guid);
		Float fyje=Float.parseFloat(Validate.isNullToDefaultString(rcbxmap.get("BXZJE"), "0"));
		String sfxy=Validate.isNullToDefaultString(rcbxmap.get("sfxy"), "0");
		identityService.setAuthenticatedUserId(LUser.getGuid());
		variables.put("fyje", fyje);//费用报销金额
		if((fyje>3000&&sfxy.equals("1"))||(fyje>2000&&sfxy.equals("0"))){
			variables.put("fyjelx", true);//判断是否经由部门分管领导审核
		}else{
			variables.put("fyjelx", false);//判断是否经由部门分管领导审核
		}
		//财务预审后的类型判断：科研，非科研，公务接待费，日常报销只需要考虑是否科研
		String sfkyl = Validate.isNullToDefaultString(rcbxmap.get("SFKYLBX"), "0");
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
		rcbxDao.doUpdateProcinstId(guid, ps.getId());//往业务表里添加流程id
		rcbxDao.doStatus(guid,"1","");//修改主表审核状态
//		Map map = rcbxDao.getBxrStatus(guid);// 提交人的各种信息
		return ps.getId();
	}

	
	/**
	 * 校验角色
	 * @param rybh
	 * @param role
	 * @return
	 */
	public boolean getJsBh(String rybh,String role){
		return rcbxDao.getJsBh(rybh, role);
	}
	
	public Map checkWhoSh(PageData pd) {
		String rybh = LUser.getRybh();
		String shr = rcbxDao.findCwys(rybh);
		Map map = new HashMap<>();
		map.put("shr", shr);
		return map;
	}
	public String getBmje(){
		return dao.getBxlxInfo().get("BMJE1")+"";
	}
	
	
	
	/**
	 * 判断当前登录人是不是部门领导
	 * 
	 * @param role
	 * @return
	 */
	public boolean checkSfLd(String role){
		return rcbxDao.checkSfLd(role);
	}
	
	/**
	 * 查询校长
	 * @return
	 */
	public String getSchoolMaster(){
		return rcbxDao.getSchoolMaster();
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
		String taskDefKey=rcbxDao.getTaskNodeId(task.getId());
		String shbz=rcbxDao.getNextTaskNodeId(task.getId());//通过任务id获取
		String syr="";
		String sfbj="";
		String blr="";
		Map rcbxmap=rcbxDao.getRcbxById(guid);
		List<Map<String,Object>> bxzelist=rcbxDao.getbxzelist(guid);
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
		String status = "";
		if("cwys".equals(taskDefKey)){
			if("true".equals(ky)&&!"true".equals(fky)&&!"true".equals(gwjd)){
				int result = rcbxDao.doStatus(guid,"2",shyj);//修改主表审核状态科研处负责人审核
				status = "2";
			}
			if("true".equals(fky)&&!"true".equals(ky)&&!"true".equals(gwjd)){
				int result = rcbxDao.doStatus(guid,"3",shyj);//修改主表审核状态部门负责人审核
				status = "3";
			}
			if("true".equals(gwjd)&&!"true".equals(fky)&&!"true".equals(ky)){
				int result = rcbxDao.doStatus(guid,"16",shyj);//修改主表审核状态办公室负责人审核
				status = "16";
			}
		}
		
		if("bgsfzr".equals(taskDefKey)||"bmfzrsh".equals(taskDefKey)||"kycfzrsh".equals(taskDefKey)){
			
				int result = rcbxDao.doStatus(guid,"4",shyj);//修改主表审核状态办公室负责人审核
				status = "4";
		}
		if("cwfzrsh".equals(taskDefKey)){
				int result = rcbxDao.doStatus(guid,"5",shyj);//财务负责人审核通过，提交给部门分管领导
				status = "5";
		}
		if("bmfgldsh".equals(taskDefKey)){
			int result = rcbxDao.doStatus(guid,"6",shyj);//部门分管领导审核通过，提交给财务分管领导
			status = "6";
	    }
		if("cwfgldsh".equals(taskDefKey)){
			int result = rcbxDao.doStatus(guid,"7",shyj);//财务分管领导审核通过，提交给校长
			status = "7";
	    }
		
		if("xzsh".equals(taskDefKey)){
//			int result = rcbxDao.doStatus(guid,"8",shyj);//校长审核通过
//			status = "8";
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		if(Validate.isNull(rcbxDao.getFinId(procinstid))){
			int result = rcbxDao.doStatus(guid,"8",shyj);//审核通过
			if(result>0&&1==3){
				String xmbh = Validate.isNullToDefaultString(rcbxmap.get("XMMC"), "");
				String bxzje = Validate.isNullToDefaultString(rcbxmap.get("BXZJE"), "0");
				if(bxzelist.size()>0) {
					for(int i=0;i<bxzelist.size();i++) {
						Object xmguid= bxzelist.get(i).get("xmguid");
						Object bcbxje= bxzelist.get(i).get("bcbxje");
						rcbxDao.updateJfsz(xmguid, bcbxje);
					}
				}
				rcbxDao.updateQkje(guid);
			}
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		rcbxDao.doAddShyj(shyjb);
	}

	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfo(OA_SHYJB shyjb,HttpSession session, String guid,
			String procinstid, String shyj) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(
				LUser.getGuid(), procinstid);
		String taskDefKey = rcbxDao.getTaskNodeId(task.getId());
		variables.put("pass", false);
		workflowService.completeTask(task, variables);
		if ("cwys".equals(taskDefKey)) {
			int result = rcbxDao.doStatus(guid, "9", shyj);// 财务预审
		}
		if ("bmfzrsh".equals(taskDefKey)) {
			int result = rcbxDao.doStatus(guid, "11", shyj);//部门负责人
		}
		if ("bgsfzr".equals(taskDefKey)) {
			int result = rcbxDao.doStatus(guid, "17", shyj);// 办公室负责人
		}
		if ("kycfzrsh".equals(taskDefKey)) {
			int result = rcbxDao.doStatus(guid, "10", shyj);// 财务预审
		}
		if ("cwfzrsh".equals(taskDefKey)) {
			int result = rcbxDao.doStatus(guid, "12", shyj);// 财务负责人
		}
		if ("bmfgldsh".equals(taskDefKey)) {
			int result = rcbxDao.doStatus(guid, "13", shyj);// 部门分管领导
		}
		if ("cwfgldsh".equals(taskDefKey)) {
			int result = rcbxDao.doStatus(guid, "14", shyj);// 财务分管领导
		}
		if ("xzsh".equals(taskDefKey)) {
			int result = rcbxDao.doStatus(guid, "15", shyj);// 校长
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		rcbxDao.doAddShyj(shyjb);
	}
	public Map<String,Object> getPrintYx(String guid){
		return rcbxDao.getPrintYx(guid);//
	}
	public Map<String,Object> getPrintYxclf(String guid){
		return rcbxDao.getPrintYxclf(guid);//
	}
	public List getPrintJj(String guid){
		List list = rcbxDao.getPrintJj(guid); 
		Map nrMap;
//		for(int i = 0;i<list.size()+1;i++) {
//			nrMap = (Map)list.get(i);
//			System.err.println("____________cs___"+i);
//			if((nrMap.get("BXJE")).equals("0")) {
//				System.err.println("_____delete");
//				list.remove(i);
//			}else {
//				System.err.println("_____other");
//			}
//		}
		Iterator<Map<String,Object>> it = list.iterator();
		while(it.hasNext()){
		    Map<String,Object> map = it.next();
		    if(map.get("BXJE").equals("0")){
		        it.remove();
		    }
		}
		
		
		return list;
	}
	//得到公务接待报销的院系领导的意见
	public Map<String,Object> getPrintGWJDYx(String guid){
		return rcbxDao.getPrintGWJDYx(guid);
	}
	
	public Map<String,Object> getPrintBm(String guid){
		return rcbxDao.getPrintBm(guid);
	}
	//getPrintBmclf
	public Map<String,Object> getPrintBmclf(String guid){
		return rcbxDao.getPrintBmclf(guid);
	}
	public Map<String,Object> getPrintBmXzclf(String guid){
		return rcbxDao.getPrintBmXzclf(guid);
	}
	public Map<String,Object> getPrintBmfgldclf(String guid){
		return rcbxDao.getPrintBmfgldclf(guid);
	}
	public Map<String,Object> getPrintCwcclf(String guid){
		return rcbxDao.getPrintCwcclf(guid);
	}
	//得到公务接待部门领导意见
	public Map<String,Object> getPrintGWJDBm(String guid){
		return rcbxDao.getPrintGWJDBm(guid);
	}
	
	public Map<String,Object> getPrintKj(String guid){
		return rcbxDao.getPrintKj(guid);
	}
	//getPrintKjclf
	public Map<String,Object> getPrintKjclf(String guid){
		return rcbxDao.getPrintKjclf(guid);
	}
	//得到公务接待会计审核意见
	public Map<String,Object> getPrintGWJDKj(String guid){
		return rcbxDao.getPrintGWJDKj(guid);
	}
	
	/**
	 * 根据项目id查询经费类型
	 * @param guid
	 * @return
	 */
	public String getJflxByGuid(String guid){
		return rcbxDao.getJflxByGuid(guid);
	}
	//联想输入
		public String getxx(PageData pd) {
			String val = pd.getString("inputvalue");
			String menu = pd.getString("menu");
			String result = "";
			switch (menu) {
			case "R":
				result = rcbxDao.getry(val);
				break;
			default:
				break;
			}
			return result;
		}
		/**
		 * 通过dwjc 查询往来单位相关信息
		 */
		public List getdwjc(String dwmc) {
			return rcbxDao.getdwjc(dwmc);
		}
	//删除项目明细表 	
	public void deleteXmmx(String zbid) {
		rcbxDao.deleteXmmx(zbid);
	}
	//删除发票信息
	public void deleteFpxx(String zbid) {
		rcbxDao.deleteFpxx(zbid);
	}
	//项目明细表 新增
	public int doAddXmmx(Rcbxmx rcbxmx,String zbid) {
		return rcbxDao.doAddXmmx(rcbxmx,zbid);
	}
	//发票信息  新增
	public int doAddFpxx(Rcbxmx rcbxmx,String zbid) {
		return rcbxDao.doAddFpxx(rcbxmx,zbid);
	}
	//项目回显查询
	public List getXmhxList(String zbid) {
		return rcbxDao.getXmhxList(zbid);
	}
	//删除日常报销 + 删除退回凭证
	public int doDelete(String guid,String type) {
		return rcbxDao.doDelete(guid,type);
	}
	//保存 项目信息 
	public int bcxmxx(String xmguids,String xmmcs,String moneys,String zbid,String zfcgsyje,String fzfcgsyje) {
		return rcbxDao.bcxmxx(xmguids,xmmcs,moneys,zbid,zfcgsyje,fzfcgsyje);
	}
	//删除 项目信息 
	public int bcxmxxsc(String zbid) {
		return rcbxDao.bcxmxxsc(zbid);
	}
	//查找发票信息
	public List getFpxx(String zbid) {
		return rcbxDao.getFpxx(zbid);
	}
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getObjectByIdFyid(String fyid) {
		return rcbxDao.getObjectByIdFyid(fyid);
	}
	
	/**
	 * 根据主表id查询项目
	 * @param zbid
	 * @return
	 */
	public String getXmGuidByZbId(String zbid){
		return rcbxDao.getXmGuidByZbId(zbid);
	}

	public String getDjbh(String zbid) {
		return rcbxDao.getDjbh(zbid);
	}
	/**
	 * 审核意见
	 * @param type
	 * @return
	 */
	public List getShyjListByLoginId(String type){
		return rcbxDao.getShyjListByLoginId(type);
	}
    /**
     * 修改保存  日常报销 凭证 修改信息
     * @param fjzzs
     * @param bxsy
     * @param guid
     * @return
     */
	public int updatePzthRC(Rcbxzb rcbx) {
		return rcbxDao.updatePzthRC(rcbx);
	}
	public int doUpdateRcBxmx(String fymc, String fjzs, String bz, String guid) {
		return rcbxDao.doUpdateRcBxmx(fymc,fjzs,bz,guid);
	}
    /**
     * 凭证复核 修改状态
     * @param guid
     * @return
     */
	public int RcbxSubmit(String guid) {
		return rcbxDao.RcbxSubmit(guid);
	}
	/**
	 * 获取申请人所在单位是否学院
	 * @param guid
	 * @return
	 */
	public String getSfxy(String guid) {
		return rcbxDao.getSfxy(guid);
	}
	/**
	 * 批量审核获取申请人所在单位是否学院
	 * @param guid
	 * @return
	 */
	public String getSfxypl(String guid) {
		return rcbxDao.getSfxypl(guid);
	}
}
