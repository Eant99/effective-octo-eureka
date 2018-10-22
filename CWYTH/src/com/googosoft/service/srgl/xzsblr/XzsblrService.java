package com.googosoft.service.srgl.xzsblr;

import java.math.BigDecimal;
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
import com.googosoft.dao.srgl.xzsblr.XzsblrDao;
import com.googosoft.dao.wsbx.RcbxDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.srgl.xzsblr.CW_GRSRZB;
import com.googosoft.pojo.wsbx.CW_CJKB;
import com.googosoft.pojo.wsbx.CW_DGZFB;
import com.googosoft.pojo.wsbx.CW_FJXXB;
import com.googosoft.pojo.wsbx.CW_GWKB;
import com.googosoft.pojo.wsbx.Cw_DSZFB;
import com.googosoft.pojo.wsbx.Rcbxmx;
import com.googosoft.pojo.wsbx.Rcbxzb;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Service("xzsblrService")
public class XzsblrService {
	@Resource(name = "xzsblrDao")
	private XzsblrDao xzsblrDao;
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
	 * 获取发放批次
	 * @param wlbh
	 * @return
	 */
	public String getFfpc() {
		return xzsblrDao.getFfpc();
	}
	/**
	 * 获取个人收入经济科目
	 * @param wlbh
	 * @return
	 */
	public List getJjkm() {
		return xzsblrDao.getJjkm();
	}
	/**
	 * 保存主表信息
	 * @param wlbh
	 * @return
	 */
	public int doTqmb(CW_GRSRZB grsrzb,String rybhs, String ffjes,String yhkhs,String fffs)  {
		return xzsblrDao.doTqmb(grsrzb,rybhs,ffjes,yhkhs,fffs);
	}
	/**
	 * 保存模版提取信息
	 * @param wlbh
	 * @return
	 */
	public int doUpdate(CW_GRSRZB grsrzb,String rybhs, String ffjes,String yhkhs)  {
		return xzsblrDao.doUpdate(grsrzb,rybhs,ffjes,yhkhs);
	}
	/**
	 * 更新主表信息
	 * @param wlbh
	 * @return
	 */
	public int doAdd(CW_GRSRZB grsrzb,String rybhs, String ffjes,String yhkhs)  {
		return xzsblrDao.doAdd(grsrzb,rybhs,ffjes,yhkhs);
	}
	/**
	 * 保存模版
	 * @param wlbh
	 * @return
	 */
	public int doSavemb(String guid){
		return xzsblrDao.doSavemb(guid);
	}
	/**
	 * 提交操作
	 * @param wlbh
	 * @return
	 */
	public int submit(String guid){
//		return xzsblrDao.submit(guid);
		int m = submitByGuid(guid, "11", "xinzi", "xinzi");//公务接待费报销
		return m;
	}
	/**
	 * 提交薪资申报录入
	 * 
	 * @param guid
	 * @param shzt
	 * @param type
	 * @return
	 */
	@Transactional
	public int submitByGuid(String guid, String shzt, String type,
			String process) {
		String tableName = "";
		shzt = Validate.isNullToDefaultString(shzt, "1");
//		String bxzje = "";
		// 创建流程参数map
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		tableName = "CW_GRSRZB";
		info = xzsblrDao.getMapInfoByXzsbfAndGuid(guid);
//		bxzje = Validate.isNullToDefaultString(info.get("BXJE"), "0");

		// 创建当前登录人的流程
		identityService.setAuthenticatedUserId(LUser.getGuid());
		ProcessInstance ps = null;
		variables.put("sqr", LUser.getGuid());
		variables.put("pass", true);
//		//本部门的报账员
//		variables.put("bzy", Validate.isNullToDefault(CommonUtil.getBmbzy().get("GUID"),""));
		//审核人1
		variables.put("shrUser", xzsblrDao.getShrList("2013088"));
		//审核人2
//		variables.put("shr2User",xzsblrDao.getShr2List("2013093"));
		//财务处
//		variables.put("cwcz",dao.getDwldByGdbm(Constant.CWC));
		//单位领导
//		variables.put("fgld",dao.getDwldSql().get("DWFGLD")+"");
		//校长
//		variables.put("wwyx",true);
//		if(new BigDecimal(bxzje).compareTo(new BigDecimal(Constant.FIVE))>0){
//			String xz = dao.getXz();
//			variables.put("xz",xz);
//			variables.put("wwyx",false);
//			if(xz.equals(variables.get("fgld"))){//如果校长就是分管领导，则跳过分管领导
//				variables.put("self",true);
//			}else{
//				variables.put("self",true);
//			}
//		}
		// 启动流程
		ps = workflowService.startProcess(variables, process);
		// 获取流程的任务节点
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(),
				ps.getId());
		// 执行流程
		workflowService.completeTask(task, variables);
		String procinstid = ps.getId();
		// 流程执行完毕，更新业务表
		int m = xzsblrDao.submit(shzt, tableName, procinstid, "", guid);
		return m;
	}
	/**
	 * 退回操作
	 * @param wlbh
	 * @return
	 */
	public int nopass(String guid){
		return xzsblrDao.nopass(guid);
	}
	/**
	 * 通过操作
	 * @param wlbh
	 * @return
	 */
	public int pass(String guid){
		return xzsblrDao.pass(guid);
	}
	/**
	 * 核算操作
	 * @param wlbh
	 * @return
	 */
	public int doAccount(String guid){
		return xzsblrDao.doAccount(guid);
	}
	/**
	 * 撤销提交
	 * @param wlbh
	 * @return
	 */
	public int chexiao(String guid){
		return xzsblrDao.chexiao(guid);
	}
	/**
	 * 删除模版
	 * 
	 * @param guid
	 * @return
	 */
	public int doDelmb(String guid) {
		return xzsblrDao.doDelmb(guid);
	}
	/**
	 *  删除薪资申报录入信息
	 * 
	 * @param guid
	 * @return
	 */
	public int doDelete(String guid) {
		return xzsblrDao.doDelete(guid);
	}
	/**
	 * 获取保存信息
	 * @param wlbh
	 * @return
	 */
	public Map getAll(String guid){
		return xzsblrDao.getAll(guid);
	}
	/**
	 * 获取保存模版信息
	 * @param wlbh
	 * @return
	 */
	public Map getAllmb(String guid){
		return xzsblrDao.getAllmb(guid);
	}
	/**
	 * 获取学生保存信息
	 * @param wlbh
	 * @return
	 */
	public List getAllxsxx(String guid){
		return xzsblrDao.getAllxsxx(guid);
	}
	/**
	 * 获取教师保存信息
	 * @param wlbh
	 * @return
	 */
	public List getAlljsxx(String guid){
		return xzsblrDao.getAlljsxx(guid);
	}
	/**
	 * 获取校外人员保存信息
	 * @param wlbh
	 * @return
	 */
	public List getAllxwryxx(String guid){
		return xzsblrDao.getAllxwryxx(guid);
	}
	/**
	 * 获取学生个人银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getXxyhk(){
		return xzsblrDao.getXxyhk();
	}
	/**
	 * 获取教师个人银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getJsyhk(){
		return xzsblrDao.getJsyhk();
	}
	/**
	 * 获取校外人员个人银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getXwryyhk(){
		return xzsblrDao.getXwryyhk();
	}
}
