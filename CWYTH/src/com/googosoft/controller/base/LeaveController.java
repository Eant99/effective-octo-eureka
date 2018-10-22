package com.googosoft.controller.base;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.googosoft.constant.GlobalContants;
import com.googosoft.service.LeaveService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.util.CommonUtils;


@Controller
@RequestMapping("/leave")
public class LeaveController {
	
	@Autowired
	LeaveService leaveService;
	
	@Autowired
	WorkflowService workflowService;
	
	@RequestMapping("/view")
	public String findLeaveInfos(ModelMap modelMap,HttpSession session){
		String userid = CommonUtils.getRybh();
		modelMap.put("leaveInfos", leaveService.findLeaveInfoByUser(userid));
		return "leave/list";
	}
	@RequestMapping(value = "/editLeave")
	public String editLeave(ModelMap modelMap, String cmd, String id) {
		String forward="";
		switch (cmd) {
		case GlobalContants.ADD:
			forward="leave/editLeave";
			break;
		case GlobalContants.UPDATE:
			modelMap.put("leave", leaveService.findLeaveInfoByPK(id));
			forward="leave/editLeave";
			break;
		case GlobalContants.APPROVE:
			modelMap.put("leave", leaveService.findLeaveInfoByPK(id));
			forward="leave/editLeave";
			break;
		}
		modelMap.put("cmd", cmd);
		return forward;
	}
	
	/*@RequestMapping(value = "/saveLeave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String editLeave(ModelMap modelMap, String cmd,OA_HYSSQB hyssqb,OA_SHYJB shyjb,HttpSession session,String pass) {
		String userId =CommonUtils.getRybh();
		hyssqb.setSqr(userId);
		String forward="";
		switch (cmd) {
			case GlobalContants.ADD:
				
				boolean flag=leaveService.saveLeaveInfo(hyssqb);
				if(flag){
					forward= "{success:'true',msg:'保存信息成功！'}";
				}else{
					forward="{success:'true',msg:'保存信息失败！'}";
				}
				break;
			case GlobalContants.UPDATE:
				//leaveService.updateLeaveInfo(leave);
				modelMap.put("leaveInfos", leaveService.findLeaveInfoByUser(userId));
				forward="leave/list";
				break;
				//审核请假信息
			case GlobalContants.APPROVE:
				//退回
				if(WorkflowContant.REJECT.equals(pass)){
					hyssqb.setShzt("0");
					leaveService.rejectLeaveInfo(hyssqb,session,shyjb);
					forward= "{success:'true',msg:'退回成功！'}";
				}else{
					//通过
					hyssqb.setShzt("3");
					leaveService.approveLeaveInfo(hyssqb,session,shyjb);
					forward= "{success:'true',msg:'通过成功！'}";
				}
				break;
			case GlobalContants.SUBMIT:
				hyssqb.setShzt("2");
				leaveService.submitLeaveInfo(hyssqb, session);
				forward= "{success:'true',msg:'提交成功！'}";
				break;
			}
		return forward;
	}*/
	@RequestMapping("/viewApprove")
	public String findLeaveApproveInfos(ModelMap modelMap,HttpSession session){
		String userid = CommonUtils.getRybh();
		modelMap.put("leaveInfos", leaveService.findLeaveApproveInfoByUser(userid));
		return "leave/listApprove";
	}
	
}
