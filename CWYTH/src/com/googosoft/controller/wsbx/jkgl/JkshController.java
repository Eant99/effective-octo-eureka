package com.googosoft.controller.wsbx.jkgl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.CW_JKYWB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.jkgl.JksqService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;

@Controller
@RequestMapping(value="/jksh")
public class JkshController extends BaseController {
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	@Resource(name="pageService")
	private PageService pageService;//分页单例
	@Resource(name="jksqService")
	private JksqService jksqService;//分页单例
	
	@Autowired
	EchoService echoService;
	
	/**
	 * 获取信息列表页面
	 * @return
	 */
	@RequestMapping(value="/goJkshPage")
	public ModelAndView goRkglPage(){
		ModelAndView mv = this.getModelAndView();
		String shzt=this.getPageData().getString("shzt");
		mv.addObject("shzt",shzt);
		mv.setViewName("wsbx/jkgl/jksh_list");
		return mv;
	}
	
	/**
	 * 获取日常报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"),"01");
		String shztpd="";
		if("01".equals(shzt) || "".equals(shzt) || shzt == null) {
			shztpd="01";
		}else if("02".equals(shzt)) {
			shztpd="02";
		}
		
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		StringBuffer tableName = new StringBuffer();
		sql.append(" A.GID,A.DJBH,A.SHZT AS SHZTDM,A.PROCINSTID,'"+shztpd+"' as shztpd,");
		sql.append(" decode(nvl(A.JKZJE,0),0,'0.00',(to_char(round(A.JKZJE,2),'fm999999999999999999990.00')))JKZJE,");
		sql.append(" TO_CHAR(A.JKSJ,'YYYY-MM-DD')JKSJ,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZBM)AS SZBM,");
		sql.append("  ry.xm AS JKR,  c.assignee_ ,");
		sql.append(" (SELECT c.mc FROM GX_SYS_DMB C WHERE C.ZL = 'jklc' AND C.DM = A.SHZT) AS SHZT ");
		tableName.append( "CW_JKYWB A left join gx_sys_ryb ry on ry.guid=a.jkr ");
/*		sql.append(" where 1=1 AND A.SHZT NOT IN ('01') and A.jkr != '"+LUser.getGuid()+"' and C.assignee_='"+LUser.getGuid()+"' ");
*/	
		pageList.setSqlText( sql.toString());
		
		if("01".equals(shzt) || "".equals(shzt) || shzt == null) {
			tableName.append( " LEFT JOIN ACT_RU_TASK C ON A.PROCINSTID = C.PROC_INST_ID_ and c.TASK_DEF_KEY_ <> 'sqr'");
		
		}else if("02".equals(shzt)) {
			tableName.append( " LEFT JOIN (SELECT DISTINCT ASSIGNEE_,PROC_INST_ID_ FROM ACT_HI_ACTINST  WHERE END_TIME_ IS NOT NULL and ACT_ID_ <> 'sqr') C ON A.PROCINSTID = C.PROC_INST_ID_   ");
		}
		
		// 表名
		pageList.setTableName(tableName.toString());
		// 主键
		pageList.setKeyId("A.gid");
		// 设置合计值字段名
		pageList.setHj1("");
		pageList.setStrWhere("  AND a.SHZT NOT IN ('01')  and c.assignee_='"+LUser.getGuid()+"' ");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping(value="/check1")
	public ModelAndView check(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String gid = pd.getString("gid");
		String procinstid=pd.getString("procinstid");
		mv.addObject("procinstid", procinstid);
		mv.addObject("gid", gid);
		String url = "wsbx/jkgl/check1";
		String types = "通过";
		if(!"first".equals(type)){
			url = "wsbx/jkgl/check2";
			types = "退回";
		}
		List list = rcbxService.getShyjListByLoginId(types);
		mv.addObject("list", list);
		mv.setViewName(url);
		return mv;
	}
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(CW_JKYWB jksq){
		String operateType = this.getPageData().getString("operateType");
		String b = "";
		int i;
		if("TG".equals(operateType)){
				i = jksqService.doUpdate(jksq,"TG");
				if(i==1){
					b = "{\"success\":\"true\",\"gid\":\""+jksq.getGid()+"\",\"msg\":\"信息操作成功！\"}";
				}else{
					b = "{\"success\":\"false\",\"gid\":\""+jksq.getGid()+"\",\"msg\":\"信息操作失败！\"}";
				}
		}else if("TH".equals(operateType)){
				i=jksqService.doUpdate(jksq,"TH");
				if(i==1){
					b = "{\"success\":\"true\",\"gid\":\""+jksq.getGid()+"\",\"msg\":\"信息操作成功！\"}";
				}else{
					b = "{\"success\":\"false\",\"gid\":\""+jksq.getGid()+"\",\"msg\":\"信息操作失败！\"}";
				}
		}else{
			b = "{'success':'F','msg':'参数传入有误！'}";
		}
		return b;
	}
	
	//审核流程
	@RequestMapping(value = "/doApprove", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave( HttpSession session, OA_SHYJB shyjb,String operateType){
		PageData pd = this.getPageData();
		String guid = pd.getString("gid");
		String procinstid=pd.getString("procinstid");
		String shyj = pd.getString("shyj");
		String apply = pd.getString("apply");
		String forward = "";
		String guids[] = guid.split(",");
		String procinstids[] = procinstid.split(",");
		DshInfoMap msgMap = new DshInfoMap();
		if ("TH".equals(operateType)) {
			for(int i=0;i<guids.length;i++) {
				jksqService.rejectleaveinfo(session, shyjb,guids[i],procinstids[i],shyj,apply);
				if(Validate.noNull(procinstids[i])) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstids[i]);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getCcywsqDshxxMsg(guids[i]);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
			forward = "{success:true, msg:'退回成功！'}";
		} else {
			//通过
			for(int i=0;i<guids.length;i++) {
				jksqService.approveLeaveInfo( session,shyjb, guids[i],procinstids[i],shyj);
				if(Validate.noNull(procinstids[i])) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstids[i]);
					//如果审核人存在
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getCcywsqDshxxMsg(guids[i]);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
			forward = "{success:true, msg:'通过成功！'}";
		}
		//向当前操作人发送消息
		EchoUtil.sendMessage(new YshMessage(LUser.getGuid(),MessageType.YSHXX, guids));
		//向办理人发送消息
		EchoUtil.batchSendDshxxMsg(msgMap);
		return forward;
	}
}
