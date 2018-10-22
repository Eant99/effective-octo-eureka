package com.googosoft.service.wsbx.process;

import java.math.BigDecimal;
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

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.dao.base.ModelDao;
import com.googosoft.dao.wsbx.ccyw.CcywshDao;
import com.googosoft.dao.wsbx.process.WsbxProcessDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.util.Validate;

/**
 * 网上报销流程service
 * 
 * @author googosoft
 * 
 */
@Service("ProcessService")
public class WsbxProcessService {
	@Resource(name="ccywshDao")
	private CcywshDao ccywshDao;
	@Resource(name = "ProcessDao")
	private WsbxProcessDao dao;
	@Autowired
	IdentityService identityService;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	ModelDao modelDao;
	@Autowired
	private RuntimeService runtimeService;

	/**
	 * 判断该走哪个流程
	 * 
	 * @param guid
	 * @param type
	 * @return
	 */
	public int getProcessType(String guid, String type,String xz) {
		String process = "";
		String result = "";
		int m = 0;
		switch (type) {
		case "rcbx":
			result = dao.getXmTypeByGuidAndRcbx(guid);
			break;
		case "clfbx":
			result = dao.getXmTypeByGuidAndClbx(guid);
			break;
		case "gwjdfbx":
			result = "4";
			break;
		case "srsblr":
			result = dao.getXmTypeByGuidAndGrsr(guid);
			break;
		default:
			break;
		}
		if ("1".equals(result)) {// 部门业务费
			process = "bmywsplc";
			m = submitByBmyw(guid, "01", type, process,"11",xz);
		} else if ("2".equals(result)) {// 科研经费
			process = "kyjf";
			m = submitByKyjf(guid, "12", type, process,"11");
		} else if ("3".equals(result)) {// 其他经费
			process = "yqndqt";
			m = submitByQt(guid, "01", type, process,"11");
		} else if ("4".equals(result)) {// 专项经费
			process = "zxjflu";
			if("gwjdfbx".equals(type)){
				m = submitByGwjd(guid, "03", type, "gwjdfbx", "11");//公务接待费报销
			}else{
				m = submitByZxyw(guid, "01", type, process,"11");
			}
		} else {
			System.err.println("---------------------业务流程不存在--------------------");
		}
		//确定提交流程
		return m;
	}

	/**
	 * 提交申请部门业务
	 * 
	 * @param guid
	 * @param shzt
	 * @param type
	 * @return
	 */
	@Transactional
	public int submitByBmyw(String guid, String shzt, String type,
			String process,String SHZTDM,String xz) {
		String tableName = "";
		shzt = Validate.isNullToDefaultString(shzt, "1");
		String bxzje = "";
		//财务员工返回true,否则返回false
		boolean cwyg = dao.checkLoginIsJs(LUser.getRybh(), Constant.CWYG);
		//财务预审人员返回true,否则返回false
		boolean cwys = dao.checkLoginIsJs(LUser.getRybh(), Constant.CWRY);
		// 创建流程参数map
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		switch (type) {
		case "rcbx"://日常报销
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXZJE"), "0");
			break;
		case "clfbx"://差旅费报销
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXZJE"), "0");
			break;
		case "gwjdfbx"://公务接待报销
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXJE"), "0");
			break;
		case "srsblr"://收入申报录入
			tableName = "CW_GRSRZB";
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXZJE"), "0");
			break;
		default:
			break;
		}
		
		variables.put("sqr", LUser.getGuid());
		variables.put("pass", true);
		//本部门的报账员
		variables.put("bzy",CommonUtil.getBmbzyList());
		//如果提交人是报账员或者提交人是财务处人员，流程自动跳过报账员步骤给sbzy赋值true
		if(CommonUtil.checkSbmbzy()||cwyg){
			variables.put("sbzy", true);
			shzt = "03";
		}else{
			variables.put("sbzy", false);
		}
		variables.put("cwyg", cwyg);
		variables.put("cwyss", cwys);
		//财务预审
		variables.put("cwys", dao.getCwysList(Constant.CWRY));
		//报销金额
		Float money = Float.parseFloat(bxzje);
		Float wqys = Float.parseFloat(Validate.isNullToDefaultString(dao.getBxlxInfo().get("BMJE1"),"0"));
		if("yzfzr".equals(xz)) {
			variables.put("syxsj", false);
			variables.put("wqys",false);
			variables.put("syzfzr", true);
			variables.put("syzfz", true);
			variables.put("yzfzr", dao.getDwldSql(guid).get("DWLD"));//单位领导
		}else {
			if(money>wqys){//报销金额超过5000元，需要部门领导会签审核
				variables.put("wqys",true);   //钱很多 到二级院系领导审
				variables.put("syxsj", false); // 是院系书记
				variables.put("syzfzr", false);//是院长或负责人
				ArrayList<String> sjyzs = new ArrayList<String>();
				String dwld = Validate.isNullToDefaultString(dao.getDwldSql(guid).get("DWLD"), "");
				if(Validate.noNull(dwld)){
					sjyzs.add(dwld);
				}
				String sj = Validate.isNullToDefaultString(dao.getDwldSql(guid).get("SJ"), "");
				if(Validate.noNull(sj)){
					sjyzs.add(sj);
				}
				variables.put("sjyzs",sjyzs);//书记院长集合
				variables.put("yzfzr", dao.getDwldSql(guid).get("DWLD"));
			}else if(("03".equals(shzt)||(cwyg||cwys))&&!(money>wqys)){//财务处员工或者金额不大于5000的审核流程
				variables.put("wqys",false);//钱不多 书记和院长 
				//财务处走行政负责人审核
				if(cwyg||cwys){
					xz = "yzfzr";
				}
				//没有院系书记默认为院长负责人
				if(Validate.isNull(dao.getDwldSql(guid).get("SJ"))){
					xz = "yzfzr";
				}
				//没有单位领导默认为院系书记
				if(Validate.isNull(dao.getDwldSql(guid).get("DWLD"))){
					xz = "xysj";
				}
				//没有审核领导直接返回
				if(Validate.isNull(dao.getDwldSql(guid).get("SJ"))&&Validate.isNull(dao.getDwldSql(guid).get("DWLD"))){
					return 0;
				}
				if("xysj".equals(xz)){
					variables.put("syxsj", true);
					variables.put("syzfzr", false);
					variables.put("wqys",false);
					variables.put("yxsj", dao.getDwldSql(guid).get("SJ"));//院系书记
					variables.put("yzfzr", dao.getDwldSql(guid).get("DWLD"));//单位领导
				}else if("yxsj".equals(xz)){
					variables.put("syxsj", true);
					variables.put("syzfzr", false);
					variables.put("wqys",false);
					variables.put("yxsj", dao.getDwldSql(guid).get("SJ"));//院系书记
				}
			}else{
				variables.put("wqys",false);
			}
		}
		String procinstid = Validate.isNullToDefaultString(info.get("PROCINSTID"),"");
		if(Validate.isNull(procinstid)){
			// 创建当前登录人的流程
			identityService.setAuthenticatedUserId(LUser.getGuid());
			ProcessInstance ps = null;
			// 启动流程
			ps = workflowService.startProcess(variables, process);
			procinstid = ps.getId();
		}
		
		// 获取流程的任务节点
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(),procinstid);
		// 执行流程
		workflowService.completeTask(task, variables);
//		if(cwys){
//			shzt = "07";
//			variables.put("yzfzr", dao.getDwldSql().get("DWLD"));
//			workflowService.jumpTask(procinstid, "yzfzr", variables);
//		}
		// 流程执行完毕，更新业务表
		int m = dao.updateTableByShzt(shzt, tableName, procinstid, "", guid,SHZTDM);
		return m;
	}
	
	/**
	 * 提交申请科研报销
	 * 
	 * @param guid
	 * @param shzt
	 * @param type
	 * @return
	 */
	@Transactional
	public int submitByKyjf(String guid, String shzt, String type,
			String process,String SHZTDM) {
		String tableName = "CW_RCBXZB";
		shzt = Validate.isNullToDefaultString(shzt, "1");
		String bxzje = "";
		// 创建流程参数map
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		info = dao.getMapInfoByRcbxAndGuid(guid);
		//财务员工直接返回true，否则返回false
		boolean cwyg = dao.checkLoginIsJs(LUser.getRybh(),Constant.CWYG);
		//财务预审员工直接返回true，否则返回false
		boolean cwys = dao.checkLoginIsJs(LUser.getRybh(),Constant.CWRY);
		boolean kjc = dao.checkLoginKjc();
		variables.put("kjc", kjc);
		variables.put("cwys", cwys);
		variables.put("cwyg", cwyg);
		variables.put("pass", true);
		variables.put("sqr", LUser.getGuid());
		//项目负责人
		variables.put("xmfzr",dao.getXmfzrByRcbxAndGuid(guid));
		boolean xmfzr = false;
		//项目负责人提交的单据直接跳过项目负责人的审核
		if(LUser.getGuid().equals(dao.getXmfzrByRcbxAndGuid(guid))){
			xmfzr = true;
			shzt = "03";
		}
		variables.put("sxmfzr", xmfzr);
		//财务预审
		variables.put("cwys", dao.getCwysList(Constant.CWRY));
		//单位领导
		variables.put("dwld",Validate.isNullToDefaultString(dao.getDwldSql(guid).get("DWLD"), ""));
		//金额
		int mm = dao.getFymcByRcbxAndGuid(guid);//劳务费、咨询费、会议费流程特殊处理
		bxzje = Validate.isNullToDefaultString(info.get("BXZJE"), "0");
		variables.put("wqys",false);
		variables.put("ywys",false);
		variables.put("wwys",false);
		BigDecimal money = new BigDecimal(bxzje);
		Map my = dao.getBxlxInfo();
		String WQYS = Validate.isNullToDefaultString(my.get("KYJE1"),"0");
		String TEN = Validate.isNullToDefaultString(my.get("KYJE2"),"0");
		String FIVE = Validate.isNullToDefaultString(my.get("KYJE3"),"0");
		BigDecimal wq = new BigDecimal(WQYS);
		BigDecimal yw = new BigDecimal(TEN);
		BigDecimal ww = new BigDecimal(FIVE);
		//金额大于五千
		if(money.compareTo(wq)>0){
			variables.put("wqys",true);
		}
		//金额大于一万
		if(money.compareTo(yw)>0){
			variables.put("ywys",true);
		}
		//金额大院五万
		if(money.compareTo(ww)>0){
			variables.put("wwys",true);
		}
		//如果报销时选择的费用包含配置项里面的特殊费用，流程是不同的
		if(money.compareTo(yw)<0&&mm>0){
			variables.put("wqys",true);
		}
//		//如果报销时选择的费用包含配置项里面的特殊费用，流程是不同的
//		if(new BigDecimal(bxzje).compareTo(new BigDecimal(Constant.TEN))<0&&mm>0){
//			bxzje = new BigDecimal(Constant.WQYS).add(new BigDecimal(mm))+"";
//			variables.put("money",bxzje);
//		}
		variables.put("money",bxzje);
		//科技处
		variables.put("kjc",dao.getDwldByGdbm(Constant.KJC));
		//财务处
		variables.put("cwcz",dao.getDwldByGdbm(Constant.CWC));
		//科技处分管领导
		variables.put("kjfgld",dao.getFgldByGdbm(Constant.KJC));
		//校长
		variables.put("xz",dao.getXz());
		String procinstid = Validate.isNullToDefaultString(info.get("PROCINSTID"), "");
		//如果procinstid/不存在，创建当前登录人的流程
		if(Validate.isNull(procinstid)){
			// 创建当前登录人的流程
			identityService.setAuthenticatedUserId(LUser.getGuid());
			ProcessInstance ps = null;
			// 启动流程
			ps = workflowService.startProcess(variables, process);
			procinstid = ps.getId();
		}
		// 获取流程的任务节点
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(),procinstid);
		// 执行流程
		workflowService.completeTask(task, variables);
		//如果提交人是财务预审也是项目负责人并且金额大于5000，任务流程直接跳到科技处审核
//		if(cwys&&xmfzr&&new BigDecimal(bxzje).compareTo(new BigDecimal("5000"))>0){
//			shzt = "03";
//			workflowService.jumpTask(procinstid, "cwys", variables);
//		}
		//如果提交人是财务预审也是项目负责人并且金额不大于5000，任务流程直接跳到单位领导处审核
//		if(cwys&&xmfzr&&new BigDecimal(bxzje).compareTo(new BigDecimal("5000"))<=0){
//			shzt = "03";
//			workflowService.jumpTask(procinstid, "cwys", variables);
//		}
		// 流程执行完毕，更新业务表
		int m = dao.updateTableByShzt(shzt, tableName, procinstid, "", guid,SHZTDM);
		return m;
	}
	
	/**
	 * 提交申请其他
	 * 
	 * @param guid
	 * @param shzt
	 * @param type
	 * @return
	 */
	@Transactional
	public int submitByQt(String guid, String shzt, String type,
			String process,String SHZTDM) {
		String tableName = "";
		shzt = Validate.isNullToDefaultString(shzt, "1");
		String bxzje = "";
		// 创建流程参数map
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			break;
		default:
			break;
		}
		//财务员工返回true，否则返回false
		boolean cwyg = dao.checkLoginIsJs(LUser.getRybh(), Constant.CWYG);
		//财务预审返回true,否则返回false
		boolean cwys = dao.checkLoginIsJs(LUser.getRybh(), Constant.CWRY);
		variables.put("sqr", LUser.getGuid());
		variables.put("pass", true);
		//报账员
		variables.put("bzy",CommonUtil.getBmbzyList());
		//如果提交人是报账员或者是财务员工，默认跳过报账员审核
		if(CommonUtil.checkSbmbzy()||cwyg){
			variables.put("sbzy", true);
			shzt = "03";
		}else{
			variables.put("sbzy", false);
		}
		variables.put("cwyss", cwys);
		variables.put("cwyg", cwyg);
		//财务预审
		variables.put("cwys",dao.getCwysList(Constant.CWRY));
		//单位领导
		variables.put("dwld",dao.getDwldSql(guid).get("DWLD")+"");
		//财务处
		variables.put("cwcz",dao.getDwldByGdbm(Constant.CWC));
		//校长
		variables.put("xz",dao.getXz());
		String procinstid = Validate.isNullToDefaultString(info.get("PROCINSTID"), "");
		if(Validate.isNull(procinstid)){
			// 创建当前登录人的流程
			identityService.setAuthenticatedUserId(LUser.getGuid());
			ProcessInstance ps = null;
			// 启动流程
			ps = workflowService.startProcess(variables, process);
			procinstid = ps.getId();
		}
		// 获取流程的任务节点
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(),procinstid);
		// 执行流程
		workflowService.completeTask(task, variables);
		//如果提交人是财务预审，则直接提交给财务领导审核
//		if(cwys){
//			shzt = "18";
//			variables.put("cwyg",true);
//			variables.put("cwyss",true);
//			workflowService.jumpTask(procinstid, "cwcz", variables);
//		}
		// 流程执行完毕，更新业务表
		int m = dao.updateTableByShzt(shzt, tableName, procinstid, "", guid,SHZTDM);
		return m;
	}
	
	/**
	 * 提交申请专项业务
	 * 
	 * @param guid
	 * @param shzt
	 * @param type// 执行流程
	 * @return
	 */
	@Transactional
	public int submitByZxyw(String guid, String shzt, String type,
			String process,String SHZTDM) {
		String tableName = "";
		shzt = Validate.isNullToDefaultString(shzt, "1");
		String bxzje = "";
		// 创建流程参数map
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXZJE"), "0");
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXZJE"), "0");
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXJE"), "0");
			break;
		case "srsblr"://公务接待报销
			tableName = "CW_GRSRZB";
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXZJE"), "0");
			break;
		default:
			break;
		}
		//财务员工返回true，否则返回false
		boolean cwyg = dao.checkLoginIsJs(LUser.getRybh(), Constant.CWYG);
		//财务预审人员返回true，否则返回false
		boolean cwys = dao.checkLoginIsJs(LUser.getRybh(), Constant.CWRY);
		variables.put("sqr", LUser.getGuid());
		variables.put("pass", true);
		//本部门的报账员
		variables.put("bzy",CommonUtil.getBmbzyList());
		//如果提交人是报账员或者财务员工，默认需要跳过报账员审核
		if(CommonUtil.checkSbmbzy()||cwyg){
			variables.put("sbzy", true);
			shzt = "03";
		}else{
			variables.put("sbzy", false);
		}
		variables.put("cwyg", cwyg);
		//财务预审
		variables.put("cwys",dao.getCwysList(Constant.CWRY));
		//单位领导
		variables.put("dwld",dao.getDwldSql(guid).get("DWLD")+"");
		//财务处
		variables.put("cwcz",dao.getDwldByGdbm(Constant.CWC));
		//单位领导
		variables.put("fgld",dao.getDwldSql(guid).get("DWFGLD")+"");
		//校长
		variables.put("wwyx",true);
		//五万以上需要学校校长审核
		variables.put("self",false);
		String five = Validate.isNullToDefaultString(dao.getBxlxInfo().get("ZYJE1"),"0");
		if(new BigDecimal(bxzje).compareTo(new BigDecimal(five))>0){
			String xz = dao.getXz();
			variables.put("xz",xz);
			variables.put("wwyx",false);
			//如果校长就是分管领导，则跳过分管领导直接到校长审核
			if(xz.equals(variables.get("fgld"))){
				variables.put("self",true);
			}
		}
		String procinstid = Validate.isNullToDefaultString(info.get("PROCINSTID"), "");
		if(Validate.isNull(procinstid)){
			// 创建当前登录人的流程
			identityService.setAuthenticatedUserId(LUser.getGuid());
			ProcessInstance ps = null;
			// 启动流程
			ps = workflowService.startProcess(variables, process);
			procinstid = ps.getId();
		}
		// 获取流程的任务节点
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(),procinstid);
		// 执行流程
		workflowService.completeTask(task, variables);
		// 流程执行完毕，更新业务表
		int m = dao.updateTableByShzt(shzt, tableName, procinstid, "", guid,SHZTDM);
		return m;
	}
	
	/**
	 * 提交申请公务接待
	 * 
	 * @param guid
	 * @param shzt
	 * @param type
	 * @return
	 */
	@Transactional
	public int submitByGwjd(String guid, String shzt, String type,
			String process,String SHZTDM) {
		String tableName = "";
		shzt = Validate.isNullToDefaultString(shzt, "1");
		String bxzje = "";
		// 创建流程参数map
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		tableName = "CW_GWJDFBXZB";
		info = dao.getMapInfoByGwjdfAndGuid(guid);
		bxzje = Validate.isNullToDefaultString(info.get("BXJE"), "0");
		//财务员工返回true，否则返回false
		boolean cwyg = dao.checkLoginIsJs(LUser.getRybh(), Constant.CWYG);
		variables.put("cwyg", cwyg);
		variables.put("sqr", LUser.getGuid());
		variables.put("pass", true);
//		//本部门的报账员
//		variables.put("bzy", Validate.isNullToDefault(CommonUtil.getBmbzy().get("GUID"),""));
		//财务预审
		variables.put("cwys", dao.getCwysList(Constant.CWRY));
		//单位领导
		variables.put("dwld",dao.getDwldSql(guid).get("DWLD")+"");
		//财务处
		variables.put("cwcz",dao.getDwldByGdbm(Constant.CWC));
		//单位领导
		variables.put("fgld",dao.getDwldSql(guid).get("DWFGLD")+"");
		//校长
		variables.put("wwyx",true);
		//报销金额超过50000,需要校长审核
		String five = Validate.isNullToDefaultString(dao.getBxlxInfo().get("ZYJE1"),"0");
		if(new BigDecimal(bxzje).compareTo(new BigDecimal(five))>0){
			String xz = dao.getXz();
			variables.put("xz",xz);
			variables.put("wwyx",false);
			//如果校长就是分管领导，则跳过分管领导
			if(xz.equals(variables.get("fgld"))){
				variables.put("self",true);
			}else{
				variables.put("self",true);
			}
		}
		String procinstid = Validate.isNullToDefaultString(info.get("PROCINSTID"), "");
		if(Validate.isNull(procinstid)){
			// 创建当前登录人的流程
			identityService.setAuthenticatedUserId(LUser.getGuid());
			ProcessInstance ps = null;
			// 启动流程
			ps = workflowService.startProcess(variables, process);
			procinstid = ps.getId();
		}
		// 获取流程的任务节点
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(),procinstid);
		// 执行流程
		workflowService.completeTask(task, variables);
		// 流程执行完毕，更新业务表
		int m = dao.updateTableByShzt(shzt, tableName, procinstid, "", guid,SHZTDM);
		return m;
	}
	
	/**
	 * 审批通过
	 * @param leave
	 */
	@Transactional
	 public int approveLeaveInfo(HttpSession session, OA_SHYJB shyjb,String guid,
			String procinstid,String shyj,String other,String type) {
		String process = "";
		String result = "";
		int m = 0;
		switch (type) {
		case "rcbx":
			result = dao.getXmTypeByGuidAndRcbx(guid);
			break;
		case "clfbx":
			result = dao.getXmTypeByGuidAndClbx(guid);
			break;
		case "gwjdfbx":
			result = "4";
			break;
		case "srsblr":
			result = dao.getXmTypeByGuidAndGrsr(guid);
			break;
		default:
			break;
		}
		if ("1".equals(result)) {// 部门业务费
			approveLeaveInfoByBmyw(session, shyjb, guid, procinstid, shyj, other, type,"11");
		} else if ("2".equals(result)) {// 科研经费
			approveLeaveInfoByKyjf(session, shyjb, guid, procinstid, shyj, "", type,"11");
		} else if ("3".equals(result)) {// 其他经费
			approveLeaveInfoByQt(session, shyjb, guid, procinstid, shyj, "", type,"11");
		} else if ("4".equals(result)) {// 专项经费
			approveLeaveInfoByZxyw(session, shyjb, guid, procinstid, shyj, "", type,"11");
		} else {
			System.err.println("---------------------业务流程不存在--------------------");
		}
		//删除跳过的节点
		dao.deleteActinstByProcinstid(procinstid);
		return m;
	}
	
	/**
	 * 审批通过部门业务
	 * @param leave
	 */
	@Transactional
	 public void approveLeaveInfoByBmyw(HttpSession session, OA_SHYJB shyjb,String guid,
			String procinstid,String shyj,String other,String type,String SHZTDM) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> info = new HashMap<String, Object>();
		Map<String, Object> variabless = runtimeService.getVariables(procinstid);
		String cwyg = Validate.isNullToDefaultString(variabless.get("cwyg"), "false");
		String cwys = Validate.isNullToDefaultString(variabless.get("cwyss"), "false");
		String tableName = "";
		String bxzje = "";
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXZJE"),"0");
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXZJE"),"0");
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXJE"),"0");
			break;
		case "srsblr":
			tableName = "CW_GRSRZB";
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			bxzje = Validate.isNullToDefaultString(info.get("BXZJE"), "0");
			break;
		default:
			break;
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		//获取当前登录人的审核任务
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		//获取当前的流程节点标识
		String taskDefKey=dao.getTaskNodeId(procinstid);
		List list=new ArrayList();
		String shzt = "";
		String sxysj = Validate.isNullToDefaultString(variabless.get("syxsj"), "false");
		String syzfzr = Validate.isNullToDefaultString(variabless.get("syzfzr"), "false");
		String sfxy = dao.getSfxy(guid);
		int co = new BigDecimal(bxzje).compareTo(new BigDecimal(Constant.WQYS));
		double bxzjes = Double.parseDouble(bxzje);
		switch (taskDefKey) {
		case "bzy"://报账员审核通过给
			shzt = "03";
			if("1".equals(sfxy)){
				if("yxsj".equals(other)&&co<=0){//二级院系书记审核
					variables.put("syxsj", true);
					variables.put("syzfzr", false);
					variables.put("wqys",false);
					variables.put("yxsj", dao.getDwldSql(guid).get("SJ"));
				}else if("yzfzr".equals(other)&&co<=0){//二级院系院长或行政负责人审核
					variables.put("syxsj", false);
					variables.put("wqys",false);
					variables.put("syzfzr", true);
					variables.put("syzfz", true);
					variables.put("yzfzr", dao.getDwldSql(guid).get("DWLD"));
				}else{//院长和书记会签审核
					variables.put("wqys", true);
					variables.put("syxsj", false);
					variables.put("syzfzr", false);
					ArrayList<String> sjyzs = new ArrayList<String>();
					String sj = dao.getDwldSql(guid).get("SJ")+"";
					sjyzs.add(sj);
					sjyzs.add(dao.getDwldSql(guid).get("DWLD")+"");
					variables.put("sjyzs", sjyzs);
				}
			}else{//二级院系院长或行政负责人审核
				variables.put("syxsj", false);
				variables.put("wqys",false);
				variables.put("syzfzr", true);
				variables.put("syzfz", true);
				variables.put("yzfzr", dao.getDwldSql(guid).get("DWLD"));
			}
			break;
		case "cwys"://财务预审通过财务预审
			if("true".equals(sxysj)){//二级院系书记审核
				shzt = "05";
				other="yxsj";
			}
			if("true".equals(syzfzr)){//二级院系院长或行政负责人审核
				shzt = "07";
				other="yzfzr";
			}
			if(!"true".equals(sxysj)&&!"true".equals(syzfzr)){//院长和书记会签审核
				shzt = "09";
			}
			if("1".equals(sfxy)){
				if("yxsj".equals(other)&&co<=0){//二级院系书记审核
					variables.put("syxsj", true);
					variables.put("syzfzr", false);
					variables.put("wqys",false);
					variables.put("yxsj", dao.getDwldSql(guid).get("SJ"));
				}else if("yzfzr".equals(other)&&co<=0){//二级院系院长或行政负责人审核
					variables.put("syxsj", false);
					variables.put("wqys",false);
					variables.put("syzfzr", true);
					variables.put("syzfz", true);
					variables.put("yzfzr", dao.getDwldSql(guid).get("DWLD"));
				}else{//院长和书记会签审核
					variables.put("wqys", true);
					variables.put("syxsj", false);
					variables.put("syzfzr", false);
					ArrayList<String> sjyzs = new ArrayList<String>();
					String sj = dao.getDwldSql(guid).get("SJ")+"";
					sjyzs.add(sj);
					sjyzs.add(dao.getDwldSql(guid).get("DWLD")+"");
					variables.put("sjyzs", sjyzs);
				}
			}else{//二级院系院长或行政负责人审核
				variables.put("syxsj", false);
				variables.put("wqys",false);
				variables.put("syzfzr", true);
				variables.put("syzfz", true);
				variables.put("yzfzr", dao.getDwldSql(guid).get("DWLD"));
			}
			break;
		case "yxsj"://二级院系书记审核审通过
			shzt = "8";
			break;
		case "yzfzr"://二级院系院长或行政负责人审核审通过
			shzt = "8";
			break;
		case "sjyz"://院长和书记会签审核审通过
			int hq = dao.checkHq(procinstid, taskDefKey);
			if(hq==2){
				shzt = "09";//会签未结束
			}else{
				shzt = "8";//会签结束
			}
			break;
		default:
			break;
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
//		if(Validate.isNull(dao.getDwldSql().get("SJ"))&&"sjyz".equals(taskDefKey)){
//			workflowService.jumpTask(procinstid, "end", variables);
//		}
		//流程结束
		if(Validate.isNull(dao.getTaskNodeId(procinstid))){
			SHZTDM = "99";
			shzt = "8";
		}
		int m = 0;
		if(shzt.equals("8")){
			dao.update(procinstid);
		}
		m = dao.updateTableByShzt(shzt, tableName, procinstid, shyj, guid,SHZTDM);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		dao.doAddShyj(shyjb);
	}
	
	/**
	 * 审批通过科研经费
	 * @param leave
	 */
	@Transactional
	 public void approveLeaveInfoByKyjf(HttpSession session, OA_SHYJB shyjb,String guid,
			String procinstid,String shyj,String other,String type,String SHZTDM) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> info = new HashMap<String, Object>();
		String tableName = "CW_RCBXZB";
		Map<String, Object> variabless = runtimeService.getVariables(procinstid);
		String cwyg = Validate.isNullToDefaultString(variabless.get("cwyg"), "");
		String cwys = Validate.isNullToDefaultString(variabless.get("cwyss"), "");
		String kjc = Validate.isNullToDefaultString(variabless.get("kjc"), "");
		String  money = Validate.isNullToDefaultString(variabless.get("money"), "0");
		int b = new BigDecimal(money).compareTo(new BigDecimal(Validate.isNullToDefaultString(dao.getBxlxInfo().get("KYJE1"), "0")));
		info = dao.getMapInfoByRcbxAndGuid(guid);
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey=dao.getTaskNodeId(procinstid);
		String xmfzrId = dao.getXmfzrByRcbxAndGuid(guid);
		String xmfzr = Validate.isNullToDefaultString(variabless.get("xmfzr"), "");
		List list=new ArrayList();
		String shzt = "";
		variables.put("pass", true);
//		workflowService.completeTask(task, variables);
		switch (taskDefKey) {
		case "xmfzr"://项目负责人通过
			shzt = "03";
			if("true".equals(cwys)&&b>0){//申请人是财务预审
				shzt = "16";
//				workflowService.jumpTask(procinstid, "kjc", variables);
				workflowService.jumpTask(procinstid, task.getId(), "kjc", variables);
			}else{
				workflowService.completeTask(task, variables);
			}
			break;
		case "cwys"://财务预审通过
			shzt = "14";
			if(("true".equals(cwyg)||"true".equals(kjc))&&b>0){
				shzt = "16";
//				workflowService.jumpTask(procinstid, "kjc", variables);
				workflowService.jumpTask(procinstid, task.getId(), "kjc", variables);
			}else{
				workflowService.completeTask(task, variables);
			}
//			if("true".equals(kjc)&&b>0){
//				shzt = "16";
//				workflowService.jumpTask(procinstid, task.getId(), "kjc", variables);
//				workflowService.jumpTask(procinstid, "kjc", variables);
//			}
			break;
		case "dwld"://部门负责人通过
			shzt = "16";
			workflowService.completeTask(task, variables);
			break;
		case "kjc"://科技处通过
			shzt = "18";
			workflowService.completeTask(task, variables);
			break;
		case "cwcz"://财务处通过
			shzt = "20";
			workflowService.completeTask(task, variables);
			break;
		case "kjfgld"://分管科技院领导通过
			shzt = "24";
			workflowService.completeTask(task, variables);
			break;
		case "xz"://校长通过
			shzt = "8";
			workflowService.completeTask(task, variables);
			break;
		default:
			break;
		}
		
		if(Validate.isNull(dao.getTaskNodeId(procinstid))){
			shzt = "8";
			SHZTDM = "99";
		}
		int m = 0;
		m = dao.updateTableByShzt(shzt, tableName, procinstid, shyj, guid,SHZTDM);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		dao.doAddShyj(shyjb);
	}
	
	/**
	 * 审批通过其他
	 * @param leave
	 */
	@Transactional
	 public void approveLeaveInfoByQt(HttpSession session, OA_SHYJB shyjb,String guid,
			String procinstid,String shyj,String other,String type,String SHZTDM) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> info = new HashMap<String, Object>();
		String tableName = "";
		Map<String, Object> variabless = runtimeService.getVariables(procinstid);
		String cwyg = Validate.isNullToDefaultString(variabless.get("cwyg"), "false");
		String cwys = Validate.isNullToDefaultString(variabless.get("cwyss"), "false");
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			break;
		case "srsblr":
			tableName = "CW_GRSRZB";
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			break;
		default:
			break;
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey=dao.getTaskNodeId(procinstid);
//		Map<String, Object> variabless = runtimeService.getVariables(procinstid);
		List list=new ArrayList();
		String shzt = "";
		variables.put("pass", true);
//		workflowService.completeTask(task, variables);
		switch (taskDefKey) {
		case "bzy"://报账员通过
			shzt = "03";
			workflowService.completeTask(task, variables);
			break;
		case "cwys"://财务预审通过
			shzt = "14";
			if("true".equals(cwyg)){
//				workflowService.jumpTask(procinstid, "cwcz", variables);
				workflowService.jumpTask(procinstid, task.getId(), "cwcz", variables);
				dao.deletetaskid(procinstid);
				shzt = "18";
			}else{
				workflowService.completeTask(task, variables);
			}
			break;
		case "dwld"://部门负责人通过
			shzt = "18";
			workflowService.completeTask(task, variables);
			break;
		case "cwcz"://财务处通过
			shzt = "24";
			workflowService.completeTask(task, variables);
			break;
		case "xz"://校长通过
			shzt = "8";
			workflowService.completeTask(task, variables);
			break;
		default:
			break;
		}
		if(Validate.isNull(dao.getTaskNodeId(procinstid))){
			shzt = "8";
			SHZTDM = "99";
		}
		int m = 0;
		m = dao.updateTableByShzt(shzt, tableName, procinstid, shyj, guid,SHZTDM);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		dao.doAddShyj(shyjb);
	}
	
	/**
	 * 审批通过专项业务
	 * @param leave
	 */
	@Transactional
	 public void approveLeaveInfoByZxyw(HttpSession session, OA_SHYJB shyjb,String guid,
			String procinstid,String shyj,String other,String type,String SHZTDM) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> info = new HashMap<String, Object>();
		String tableName = "";
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			break;
		case "srsblr":
			tableName = "CW_GRSRZB";
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			break;
		default:
			break;
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey=dao.getTaskNodeId(procinstid);
		Map<String, Object> variabless = runtimeService.getVariables(procinstid);
		List list=new ArrayList();
		String shzt = "";
		variables.put("pass", true);
		String cwyg = Validate.isNullToDefaultString(variabless.get("cwyg"), "false");
		String cwys = Validate.isNullToDefaultString(variabless.get("cwyss"), "false");
		String self = Validate.isNullToDefaultString(variabless.get("self"), "false");
		switch (taskDefKey) {
		case "bzy"://报账员通过
			shzt = "03";
			workflowService.completeTask(task, variables);
			break;
		case "cwys"://财务预审通过
			shzt = "14";
			if("true".equals(cwyg)){
//				workflowService.jumpTask(procinstid, "cwcz", variables);
				workflowService.jumpTask(procinstid, task.getId(), "cwcz", variables);
				dao.deletetaskid(procinstid);//如果是财务处员工，自动跳过部门负责人审核，到了财务处长审核，由于财务预审到财务处长审核是jump，没有连线，流程图不能加complete>0,故加此sql
				shzt = "18";
			}else{
				workflowService.completeTask(task, variables);
			}
			break;
		case "dwld"://部门负责人通过
			shzt = "18";
			workflowService.completeTask(task, variables);
			break;
		case "cwcz"://财务处通过
			shzt = "22";
			if("true".equals(self)){
				shzt = "24";
//				workflowService.jumpTask(procinstid, "xz", variables);
				workflowService.jumpTask(procinstid, task.getId(), "xz", variables);
			}else{
				workflowService.completeTask(task, variables);
			}
			break;
		case "fgld"://分管领导通过
			shzt = "24";
			workflowService.completeTask(task, variables);
			break;
		case "xz"://校长通过
			shzt = "8";
			workflowService.completeTask(task, variables);
			break;
		default:
			break;
		}
		String nodeId = dao.getTaskNodeId(procinstid);
		if(Validate.isNull(nodeId)){
			shzt = "8";
			SHZTDM = "99";
		}
		int m = 0;
		m = dao.updateTableByShzt(shzt, tableName, procinstid, shyj, guid,SHZTDM);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		dao.doAddShyj(shyjb);
	}
	
	/**
	 * 审批退回
	 * @param leave
	 */
	@Transactional
	 public int rejectleaveinfo(OA_SHYJB shyjb,HttpSession session, String guid,
				String procinstid, String shyj,String type,String apply) {
		String process = "";
		String result = "";
		int m = 0;
		switch (type) {
		case "rcbx":
			result = dao.getXmTypeByGuidAndRcbx(guid);
			break;
		case "clfbx":
			result = dao.getXmTypeByGuidAndClbx(guid);
			break;
		case "gwjdfbx":
			result = "4";
			break;
		case "srsblr":
			result = dao.getXmTypeByGuidAndGrsr(guid);
			break;
		default:
			break;
		}
		if ("1".equals(result)) {// 部门业务费
			rejectleaveinfoByBmyw(shyjb, session, guid, procinstid, shyj, type, apply);
//			approveLeaveInfoByBmyw(session, shyjb, guid, procinstid, shyj, other, type,"11");1
		} else if ("2".equals(result)) {// 科研经费
			rejectleaveinfoByKyl(shyjb, session, guid, procinstid, shyj, type, apply);
//			approveLeaveInfoByKyjf(session, shyjb, guid, procinstid, shyj, "", type,"11");1
		} else if ("3".equals(result)) {// 其他经费
			rejectleaveinfoByQt(shyjb, session, guid, procinstid, shyj, type, apply);
//			approveLeaveInfoByQt(session, shyjb, guid, procinstid, shyj, "", type,"11");1
		} else if ("4".equals(result)) {// 专项经费
			rejectleaveinfoByZxyw(shyjb, session, guid, procinstid, shyj, type, apply);
		} else {
			System.err.println("---------------------业务流程不存在--------------------");
		}
		//删除跳过的节点
		dao.deleteActinstByProcinstid(procinstid);
		return m;
	}
	
	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfoByBmywaa(OA_SHYJB shyjb,HttpSession session, String guid,
			String procinstid, String shyj,String type,String apply) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(
				LUser.getGuid(), procinstid);
		String SHZTDM = "11";
		boolean start = false;
		String taskDefKey=dao.getTaskNodeId(procinstid);
		variables.put("pass", false);
		Map<String, Object> info = new HashMap<String, Object>();
		String tableName = "";
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			break;
		default:
			break;
		}
		String shzt = "";
		switch (taskDefKey) {
		case "bzy"://报账员退回
			shzt = "02";
			break;
		case "cwys"://财务预审退回
			shzt = "04";
			break;
		case "xmfzr"://项目负责人退回
			shzt = "13";
			break;
		case "kjc"://科技处退回
			shzt = "17";
			break;
		case "dwld"://部门负责人退回
			shzt = "15";
			break;
		case "yxsj"://二级院系书记退回
			shzt = "06";
			break;
		case "yzfzr"://二级院系院长或行政负责人退回
			shzt = "08";
			break;
		case "sjyz"://书记和院长会签退回
			int hq = dao.checkHq(procinstid, taskDefKey);
			if(hq==2){
				shzt = "09";//会签未结束
			}else{
				shzt = "10";//会签结束
			}
			break;
		case "kjfgld"://科技分管退回
			shzt = "21";
			break;
		case "cwcz"://财务处退回
			shzt = "19";
			break;
		case "fgld"://分管领导退回
			shzt = "23";
			break;
		case "xz"://校长退回
			shzt = "25";
			break;
		default:
			break;
		}
		if("1".equals(apply)){
			start = true;
			SHZTDM = "00";
		}
		int m = 0;
		variables.put("start", start);
		workflowService.completeTask(task, variables);
		String TASK_DEF_KEY_ = dao.getTaskNodeId(procinstid);
		if(Validate.noNull(TASK_DEF_KEY_)&&"sqr".equals(TASK_DEF_KEY_)){
			SHZTDM = "00";
		}
		m = dao.updateTableByShzt(shzt, tableName, procinstid, shyj, guid,SHZTDM);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		dao.doAddShyj(shyjb);
	}
	
	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfoByBmyw(OA_SHYJB shyjb,HttpSession session, String guid,
			String procinstid, String shyj,String type,String apply) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(
				LUser.getGuid(), procinstid);
		Map<String, Object> variabless = runtimeService.getVariables(procinstid);
		String SHZTDM = "11";
		boolean start = false;
		String cwyg = Validate.isNullToDefaultString(variabless.get("cwyg"),"false");
		String cwys = Validate.isNullToDefaultString(variabless.get("cwyss"),"false");
		String self = Validate.isNullToDefaultString(variabless.get("self"),"false");
		String taskDefKey=dao.getTaskNodeId(procinstid);
		variables.put("pass", false);
		Map<String, Object> info = new HashMap<String, Object>();
		String tableName = "";
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			break;
		case "srsblr"://收入申报录入
			tableName = "CW_GRSRZB";
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			break;
		default:
			break;
		}
		String shzt = "";
		if("1".equals(apply)){
			start = true;
			SHZTDM = "00";
		}
		variables.put("start", start);
		workflowService.completeTask(task, variables);
		switch (taskDefKey) {
		case "bzy"://报账员退回
			shzt = "02";
			break;
		case "cwys"://财务预审退回
			shzt = "04";
			break;
		case "yxsj"://二级院系书记退回
			shzt = "06";
			break;
		case "yzfzr"://二级院系院长或行政负责人退回
			shzt = "08";
			break;
		case "sjyz"://书记和院长会签退回
			int hq = dao.checkHq(procinstid, taskDefKey);
			if(hq==2){
				shzt = "09";//会签未结束
			}else{
				shzt = "10";//会签结束
			}
			break;
		default:
			break;
		}
		
		int m = 0;
		String TASK_DEF_KEY_ = dao.getTaskNodeId(procinstid);
		if(Validate.noNull(TASK_DEF_KEY_)&&"sqr".equals(TASK_DEF_KEY_)){
			SHZTDM = "00";
		}
		m = dao.updateTableByShzt(shzt, tableName, procinstid, shyj, guid,SHZTDM);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		dao.doAddShyj(shyjb);
	}
	
	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfoByKyl(OA_SHYJB shyjb,HttpSession session, String guid,
			String procinstid, String shyj,String type,String apply) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(
				LUser.getGuid(), procinstid);
		String SHZTDM = "11";
		Map<String, Object> variabless = runtimeService.getVariables(procinstid);
		String cwyg = Validate.isNullToDefaultString(variabless.get("cwyg"),"false");
		String cwys = Validate.isNullToDefaultString(variabless.get("cwyss"),"false");
		String xmfzr = Validate.isNullToDefaultString(variabless.get("self"),"false");
		String kjc = Validate.isNullToDefaultString(variabless.get("self"),"kjc");
		boolean start = false;
		String taskDefKey=dao.getTaskNodeId(procinstid);
		variables.put("pass", false);
		Map<String, Object> info = new HashMap<String, Object>();
		String tableName = "";
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			break;
		case "srsblr"://收入申报录入
			tableName = "CW_GRSRZB";
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			break;
		default:
			break;
		}
		String shzt = "";
		if("1".equals(apply)){
			start = true;
			SHZTDM = "00";
		}
		variables.put("start", start);
//		workflowService.completeTask(task, variables);
		switch (taskDefKey) {
		case "cwys"://财务预审退回
			shzt = "04";
			workflowService.completeTask(task, variables);
			break;
		case "xmfzr"://项目负责人退回
			shzt = "13";
			workflowService.completeTask(task, variables);
			break;
		case "kjc"://科技处退回
			shzt = "17";
			boolean bool = true;
			if("true".equals(cwyg)&&!"1".equals(apply)){
//				workflowService.jumpTask(procinstid, "cwys", variables);
				workflowService.jumpTask(procinstid, task.getId(), "cwys", variables);
				bool = false;
			}
			if("true".equals(cwys)&&!"true".equals(xmfzr)&&!"1".equals(apply)){
				workflowService.jumpTask(procinstid, task.getId(), "xmfzr", variables);
//				workflowService.jumpTask(procinstid, "xmfzr", variables);
				bool = false;
			}
			if(bool){
				workflowService.completeTask(task, variables);
			}
			break;
		case "dwld"://部门负责人退回
			shzt = "15";
			workflowService.completeTask(task, variables);
			break;
		case "kjfgld"://科技分管退回
			shzt = "21";
			workflowService.completeTask(task, variables);
			break;
		case "cwcz"://财务处退回
			shzt = "19";
			workflowService.completeTask(task, variables);
			break;
		case "xz"://校长退回
			shzt = "25";
			workflowService.completeTask(task, variables);
			break;
		default:
			break;
		}
		
		int m = 0;
		String TASK_DEF_KEY_ = dao.getTaskNodeId(procinstid);
		if(Validate.noNull(TASK_DEF_KEY_)&&"sqr".equals(TASK_DEF_KEY_)){
			SHZTDM = "00";
		}
		m = dao.updateTableByShzt(shzt, tableName, procinstid, shyj, guid,SHZTDM);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		dao.doAddShyj(shyjb);
	}
	
	/**
	 * 退回审批退回其他
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfoByQt(OA_SHYJB shyjb,HttpSession session, String guid,
			String procinstid, String shyj,String type,String apply) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(
				LUser.getGuid(), procinstid);
		Map<String, Object> variabless = runtimeService.getVariables(procinstid);
		String cwyg = Validate.isNullToDefaultString(variabless.get("cwyg"),"false");
		String cwys = Validate.isNullToDefaultString(variabless.get("cwyss"),"false");
		String self = Validate.isNullToDefaultString(variabless.get("self"),"false");
		String SHZTDM = "11";
		boolean start = false;
		String taskDefKey=dao.getTaskNodeId(procinstid);
		variables.put("pass", false);
		Map<String, Object> info = new HashMap<String, Object>();
		String tableName = "";
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			break;
		case "srsblr"://收入申报录入
			tableName = "CW_GRSRZB";
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			break;
		default:
			break;
		}
		String shzt = "";
		if("1".equals(apply)){
			start = true;
			SHZTDM = "00";
		}
		variables.put("start", start);
//		workflowService.completeTask(task, variables);
		switch (taskDefKey) {
		case "bzy"://报账员退回
			shzt = "02";
			workflowService.completeTask(task, variables);
			break;
		case "cwys"://财务预审退回
			shzt = "04";
//			workflowService.completeTask(task, variables);
			if("true".equals(cwyg)&&!"1".equals(apply)){
				workflowService.jumpTask(procinstid, task.getId(), "sqr", variables);
			}else{
				workflowService.completeTask(task, variables);
			}
			break;
		case "dwld"://部门负责人退回
			shzt = "15";
			workflowService.completeTask(task, variables);
			break;
		case "cwcz"://财务处退回
			shzt = "19";
			if("true".equals(cwyg)&&!"1".equals(apply)){
				workflowService.jumpTask(procinstid, task.getId(), "cwys", variables);
//				workflowService.jumpTask(procinstid, "cwys", variabless);
			}else{
				workflowService.completeTask(task, variables);
			}
			break;
		case "xz"://校长退回
			shzt = "25";
			workflowService.completeTask(task, variables);
			break;
		default:
			break;
		}
		int m = 0;
		String TASK_DEF_KEY_ = dao.getTaskNodeId(procinstid);
		if(Validate.noNull(TASK_DEF_KEY_)&&"sqr".equals(TASK_DEF_KEY_)){
			SHZTDM = "00";
		}
		m = dao.updateTableByShzt(shzt, tableName, procinstid, shyj, guid,SHZTDM);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		dao.doAddShyj(shyjb);
	}
	
	/**
	 * 退回审批退回专项业务
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfoByZxyw(OA_SHYJB shyjb,HttpSession session, String guid,
			String procinstid, String shyj,String type,String apply) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(
				LUser.getGuid(), procinstid);
		Map<String, Object> variabless = runtimeService.getVariables(procinstid);
		String cwyg = Validate.isNullToDefaultString(variabless.get("cwyg"),"false");
		String cwys = Validate.isNullToDefaultString(variabless.get("cwyss"),"false");
		String self = Validate.isNullToDefaultString(variabless.get("self"),"false");
		String SHZTDM = "11";
		String taskDefKey=dao.getTaskNodeId(procinstid);
		variables.put("pass", false);
		Map<String, Object> info = new HashMap<String, Object>();
		String tableName = "";
		switch (type) {
		case "rcbx":
			tableName = "CW_RCBXZB";
			info = dao.getMapInfoByRcbxAndGuid(guid);
			break;
		case "clfbx":
			tableName = "CW_CLFBXZB";
			info = dao.getMapInfoByClfbxAndGuid(guid);
			break;
		case "gwjdfbx":
			tableName = "CW_GWJDFBXZB";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			break;
		case "srsblr"://收入申报录入
			tableName = "CW_GRSRZB";
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			break;
		default:
			break;
		}
		String shzt = "";
		int m = 0;
		boolean start = false;
		if("1".equals(apply)){
			start = true;
			SHZTDM = "00";
		}
		variables.put("start", start);
//		workflowService.completeTask(task, variables);
		
		switch (taskDefKey) {
		case "bzy"://报账员退回
			shzt = "02";
			workflowService.completeTask(task, variables);
			break;
		case "cwys"://财务预审退回
			shzt = "04";
			if("true".equals(cwyg)&&!"1".equals(apply)){
				workflowService.jumpTask(procinstid, task.getId(), "sqr", variables);
			}else{
				workflowService.completeTask(task, variables);
			}
			break;
		case "dwld"://部门负责人退回
			shzt = "15";
			workflowService.completeTask(task, variables);
			break;
		case "cwcz"://财务处退回
			shzt = "19";
			if("true".equals(cwyg)&&!"1".equals(apply)){
				workflowService.jumpTask(procinstid, task.getId(), "cwys", variables);
			}else{
				workflowService.completeTask(task, variables);
			}
			break;
		case "fgld"://分管领导退回
			shzt = "23";
			workflowService.completeTask(task, variables);
			break;
		case "xz"://校长退回
			shzt = "25";
			if("true".equals(self)&&!"1".equals(apply)){
				shzt = "23";
				workflowService.jumpTask(procinstid, task.getId(), "xz", variables);
//				workflowService.jumpTask(procinstid, "xz", variables);
			}else{
				workflowService.completeTask(task, variables);
			}
			break;
		default:
			break;
		}
		String TASK_DEF_KEY_ = dao.getTaskNodeId(procinstid);
		if(Validate.noNull(TASK_DEF_KEY_)&&"sqr".equals(TASK_DEF_KEY_)){
			SHZTDM = "00";
		}
		m = dao.updateTableByShzt(shzt, tableName, procinstid, shyj, guid,SHZTDM);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		dao.doAddShyj(shyjb);
	}
	
	/**
	 * 判断提交后的审核人是谁
	 * 
	 * @param guid
	 * @param type
	 * @return
	 */
	public Map checkWhoSh(String guid, String type) {
		String result = "";
		String people = "";
		int m = 0;
		Map info = new HashMap<String,Object>();
		HashMap<String,Object> map = new HashMap<String,Object>();
		if("gwjdfbx".equals(type)){//公务接待直接提交给财务预审
			map.put("XZ", "submit");
			map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
			return map;
		}
		switch (type) {
		case "rcbx":
			result = dao.getXmTypeByGuidAndRcbx(guid);
			info = dao.getMapInfoByRcbxAndGuid(guid);
			break;
		case "clfbx":
			result = dao.getXmTypeByGuidAndClbx(guid);
			info = dao.getMapInfoByClfbxAndGuid(guid);
			break;
		case "gwjdfbx":
			result = "4";
			info = dao.getMapInfoByGwjdfAndGuid(guid);
			break;
		case "srsblr":
			result = dao.getXmTypeByGuidAndGrsr(guid);
			info = dao.getMapInfoByGrsrfAndGuid(guid);
			break;
		default:
			break;
		}
		boolean cwyg = dao.checkLoginIsJs(LUser.getRybh(),Constant.CWYG);
		boolean cwys = dao.checkLoginIsJs(LUser.getRybh(),Constant.CWRY);
		Map my = dao.getBxlxInfo();
		String bmje1 = Validate.isNullToDefaultString(my.get("BMJE1"),"0"); //部门金额
		String zyje1 = Validate.isNullToDefaultString(my.get("ZYJE1"),"0"); //专项金额
		String kyje1 = Validate.isNullToDefaultString(my.get("KYJE1"),"0"); //科研金额
		String kyje2 = Validate.isNullToDefaultString(my.get("KYJE2"),"0");
		String kyje3 = Validate.isNullToDefaultString(my.get("KYJE3"),"0");
		if ("1".equals(result)) {// 部门业务费
			String je = Validate.isNullToDefaultString(info.get("BXZJE"),"0");
			if(new BigDecimal(je).compareTo(new BigDecimal(bmje1))>0){
				if(CommonUtil.checkSbmbzy()||(cwyg&&!cwys)){
					map.put("XZ", "hq");
					map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
				}else if(cwyg&&cwys){//财务预审人员提交相互审核
					map.put("XZ", "submit");
					map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
				}else{
					map.put("XZ", "submit");
					map.put("XM", dao.getBmbzy());
				}
			}else{
				if(CommonUtil.checkSbmbzy()&&!cwyg&&!cwys){
					map.put("XZ", "xz");
					map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
				}/*else if(cwyg&&!cwys&&!CommonUtil.checkSbmbzy()){
					map.put("XZ", "submit");
					map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
				}*/else if((cwyg||cwys)&&!CommonUtil.checkSbmbzy()){//是财务预审以及财务人员一种角色就直接负责人审核
					map.put("XZ", "submit");
//					map.put("XM", dao.getDwldXm());
					map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
				}else{
					map.put("XZ", "submit");
					map.put("XM", dao.getBmbzy());
				}
			}
		} else if ("2".equals(result)) {// 科研经费
			people = Validate.isNullToDefaultString(dao.getXmfzrXmByRcbxAndGuid(guid), "");//普通人员提交给项目负责人
			String bxzje = Validate.isNullToDefaultString(info.get("bxzje"),"0");
			int b = new BigDecimal(bxzje).compareTo(new BigDecimal(kyje1));
			if(LUser.getGuid().equals(dao.getXmfzrByRcbxAndGuid(guid))){//项目负责人提交给财务
				people = Validate.isNullToDefaultString(dao.getCwysWmConcat(Constant.CWRY),"");
			}
//			if(cwys&&(LUser.getGuid().equals(dao.getXmfzrByRcbxAndGuid(guid)))&&b>0){
//				people = Validate.isNullToDefaultString(dao.getPeopleXm(dao.getDwldByGdbm(Constant.KJC)).get("XM")+"","");
//			}
//			if(cwys&&(LUser.getGuid().equals(dao.getXmfzrByRcbxAndGuid(guid)))&&b<=0){
//				people = Validate.isNullToDefaultString(dao.getPeopleXm(dao.getDwldByGdbm(LUser.getDwbh())).get("XM")+"","");
//			}
			map.put("XZ", "submit");
			map.put("XM", people);
		} else if ("3".equals(result)) {// 其他经费
			if(CommonUtil.checkSbmbzy()||(cwyg&&!cwys)){//报账员提交给财务预审
				map.put("XZ", "submit");
				map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
			}else if(cwyg&&cwys){//财务预审人员直接提交给财务处长
				map.put("XZ", "submit");
//				map.put("XM", dao.getPeopleXm(dao.getDwldByGdbm(Constant.CWC)).get("XM")+"");
				map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
			}else{
				map.put("XZ", "submit");//普通人员提交给报账员
				map.put("XM", dao.getBmbzy());
			}
		} else if ("4".equals(result)) {// 专项经费
			if(CommonUtil.checkSbmbzy()||(cwyg&&!cwys)){//报账员提交给财务预审或者财务非财务预审员工提交给财务预审
				map.put("XZ", "submit");
				map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
			}else if(cwyg&&cwys){//财务预审人员直接提交给财务处长
				map.put("XZ", "submit");
//				map.put("XM", dao.getPeopleXm(dao.getDwldByGdbm(Constant.CWC)).get("XM")+"");
				map.put("XM", dao.getCwysWmConcat(Constant.CWRY));
			}else{
				map.put("XZ", "submit");//普通人员提交给报账员
				map.put("XM", dao.getBmbzy());
			}
		} else {
			System.err.println("-----------------------------流程不存在------------------------------------------");
		}
		if(map==null||map.size()==0||map.isEmpty()){
			return new HashMap<String,Object>();
		}
		return map;
	}
	
	/**
	 * 报账员判断提交后的审核人是谁
	 * 
	 * @param guid
	 * @param type
	 * @return
	 */
	public String selectWho(String guid, String type) {
		String result = "";
		String people = "";
		int m = 0;
		switch (type) {
		case "rcbx":
			result = dao.getXmTypeByGuidAndRcbx(guid);
			break;
		case "clfbx":
			result = dao.getXmTypeByGuidAndClbx(guid);
			break;
		case "gwjdfbx":
			result = "4";
			break;
		default:
			break;
		}
		return result;
	}
	/**
	 * 如果是财务预审，可以报销事由和修改费用名称。
	 * @author 作者：BiMing
	 * @version 创建时间:2018-4-20下午3:35:31
	 */
	public void updatebxsyAndfymc(String bxsy,String fyid,String guid,String xmguid) {
		dao.updatebxsyAndfymc(bxsy,fyid,guid,xmguid);
	}
	
}
