package com.googosoft.controller.wsbx.process;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.echo.EchoService; 
import com.googosoft.service.wsbx.process.WsbxProcessService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;

@Controller
@RequestMapping(value = "/wsbx/process")
public class WsbxProcessController extends BaseController {
	// 分页信息
	@Resource(name = "pageService")
	private PageService pageService;
	// 数据字典
	@Resource(name = "dictService")
	private DictService dictService;
	// 流程
	@Resource(name = "ProcessService")
	private WsbxProcessService service;
	
	@Autowired
	private EchoService echoService;
	/**
	 * 根据业务类型和项目类型确定流程分支
	 * 
	 * @param pd
	 * @return
	 */
	@RequestMapping(value = "/submit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object submit(String type,String guid,String xz) {
		int m = 0;
//		String type = Validate.isNullToDefaultString(pd.getString("type"), "");
//		String guids = Validate.isNullToDefaultString(pd.getString("guid"), "");
		xz = Validate.isNullToDefaultString(xz, "");
		if("undefined".equals(xz)){
			xz = "";
		}
		String process = "";
		if (Validate.noNull(guid)) {
			String guidAry[] = guid.split(",");
			for (int i = 0, len = guidAry.length; i < len; i++) {
				String id = Validate.isNullToDefaultString(guidAry[i], "");
				m += service.getProcessType(id, type,xz);
			}
		}
		if (m > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_SUBMIT);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SUBMIT);
		}
	}
	
	/**
	 * 审核
	 * @author 
	 * @version 
	 */
	@RequestMapping(value = "/doApprove", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doApprove( HttpSession session,  OA_SHYJB shyjb,String pass,String cmd,String sfbj){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String bxsy = pd.getString("bxsy");
		String fyid = pd.getString("fyid");
		String xmguid = pd.getString("xmguid");
		String fcwys = pd.getString("fcwys");//财务预审的flag
		if("cwys".equals(fcwys)){
			service.updatebxsyAndfymc(bxsy,fyid,guid,xmguid);
		}
		String procinstid=pd.getString("procinstid");
		String shyj = pd.getString("shyj");
		String apply = pd.getString("apply");
		String guids[] = guid.split(",");
		String procinstids[] = procinstid.split(",");
		String forward = "";
		String type = Validate.isNullToDefaultString(pd.get("type"),"");
		String other = Validate.isNullToDefaultString(pd.get("other"),"");
		DshInfoMap msgMap = new DshInfoMap();
		if ("false".equals(pass)) {
			for(int i=0;i<guids.length;i++){
				String id = Validate.isNullToDefaultString(guids[i], "");
				String procinstId = Validate.isNullToDefaultString(procinstids[i], "");
				service.rejectleaveinfo(shyjb,session, id,procinstId,shyj,type,apply);
				if(Validate.noNull(procinstId)) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstId);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg;
						if("gwjdfbx".equals(type)){
							shxxMsg = echoService.getGwjdbxDshxxMsg(id);
						}else if("rcbx".equals(type)){
							shxxMsg = echoService.getRcbxDshxxMsg(id);
						}else if("srsblr".equals(type)){
							shxxMsg = echoService.getXzsbshDshxxMsg(id);
						}else{
							shxxMsg = echoService.getCcbxDshxxMsg(id);
						}
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
			forward = "{success:'true', msg:'退回成功！'}";
		} else {
			//通过
			for(int i=0;i<guids.length;i++){
				String id = Validate.isNullToDefaultString(guids[i], "");
				String procinstId = Validate.isNullToDefaultString(procinstids[i], "");
				service.approveLeaveInfo(session, shyjb, id, procinstId, shyj, other, type);
				if(Validate.noNull(procinstId)) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstId);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg;
						if("gwjdfbx".equals(type)){
							shxxMsg = echoService.getGwjdbxDshxxMsg(id);
						}else if("rcbx".equals(type)){
							shxxMsg = echoService.getRcbxDshxxMsg(id);
						}else if("srsblr".equals(type)){
							shxxMsg = echoService.getXzsbshDshxxMsg(id);
						}else{
							shxxMsg = echoService.getCcbxDshxxMsg(id);
						}
						
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
			forward = "{success:'true', msg:'通过成功！'}";
		}
		//向当前操作人发送消息
		EchoUtil.sendMessage(new YshMessage(LUser.getGuid(),MessageType.YSHXX, guids));
		//向办理人发送消息
		EchoUtil.batchSendDshxxMsg(msgMap);
		return forward;
	}
	
	/**
	 * 查看该谁审核
	 * @return
	 */
	@RequestMapping("/checkWhoSh")
	@ResponseBody
	public Map checkWhoSh(String guid,String type) {
		return service.checkWhoSh(guid, type);
	}
	
	/**
	 * 报账员选择谁进行审核的判断
	 * @return
	 */
	@RequestMapping(value = "/selectWho", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object selectWho(String guid,String type) {
		String result = service.selectWho(guid, type);
		boolean bool = false;
		if ("1".equals(result)) {// 部门业务费
			bool = true;
		}
		return new Gson().toJson(bool);
	}
	
	/**
	 * 报账员选择谁进行审核的判断
	 * @return
	 */
	@RequestMapping(value = "/checkBzy", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object checkBzy() {
		boolean bool = false;
		if (LUser.getGuid().equals(CommonUtil.getBmbzy().get("GUID")+"")) {// 部门业务费
			bool = true;
		}
		return new Gson().toJson(bool);
	}
}
