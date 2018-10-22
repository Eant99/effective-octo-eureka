package com.googosoft.controller.wsbx.ccyw;

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
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.ccyw.CcywshService;
import com.googosoft.service.wsbx.ccyw.CcywsqService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;

/**
 * 出差业务审核controller
 * @author googosoft
 *
 */
@Controller
@RequestMapping("wsbx/ccyw/ccywsh")
public class CcywshController extends BaseController{

	@Resource(name="ccywshService")
	CcywshService ccywshService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name="pageService")
	PageService pageService;
	@Resource(name="ccywsqService")
	CcywsqService ccywsqService;
	@Autowired
	EchoService echoService;
	
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	
	/**
	 * 跳转到出差业务申请列表页面
	 * @return
	 */
	@RequestMapping("/goCcywshPage")
	public ModelAndView goCcywshPage() {
		ModelAndView mv = this.getModelAndView();
		iniSelect(mv);
		mv.setViewName("wsbx/ccyw/ccywsh/ccywsh_list");
		return mv;
	}
	
	/**
	 * 获取出差业务审核列表页面
	 * @return
	 */
	@RequestMapping(value="getCcywshPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getCcywshPageData() {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		String status = pd.getString("status");
		sqltext.append("A.GUID,A.SQRXM,A.SZBM,A.DJBH,(SELECT WM_CONCAT(B.XMMC)FROM CW_CCSQSPB_XM S LEFT JOIN CW_JFSZB B ON S.JFBH = B.guid WHERE S.CCSQID = A.GUID) AS xmmc,A.CCTS,A.PROCINSTID,to_char(a.sqrq,'yyyy-mm-dd')sqrq,");
		if("0".equals(status) || "".equals(status) || status == null) {
			sqltext.append(" '0' as shztdm, ");
		}else if("1".equals(status)) {
			sqltext.append(" '1' as shztdm, ");
		}
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.CCLX+"' AND C.DM = A.CCLX) AS CCLX,");
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.JFLX+"' AND C.DM = B.JFLX) AS JFLX,");
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.SHZTDM+"' AND C.DM = A.SHZT) AS SHZT,C.* ");
		String tableName = " CW_CCSQSPB A LEFT JOIN CW_JFSZB B ON A.JFBH = B.GUID ";
		String strWhere = " AND A.SHZT NOT IN ('01') and A.sqr != '"+LUser.getGuid()+"' and C.assignee_='"+LUser.getGuid()+"'  ";
		//设置条件
		if("0".equals(status) || "".equals(status) || status == null) {
			tableName += " LEFT JOIN ACT_RU_TASK C ON A.PROCINSTID = C.PROC_INST_ID_ ";
		}else if("1".equals(status)) {
			tableName += " LEFT JOIN (SELECT DISTINCT ASSIGNEE_,PROC_INST_ID_ FROM ACT_HI_ACTINST  WHERE END_TIME_ IS NOT NULL) C ON A.PROCINSTID = C.PROC_INST_ID_ ";
		}
	    pageList.setSqlText(sqltext.toString());
	    pageList.setTableName(tableName);
	    pageList.setKeyId(" A.guid ");
	    pageList.setStrWhere(strWhere);
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 初始化数据字典下拉框数据,以及当前登录人
	 * @param mv
	 */
	public void iniSelect(ModelAndView mv) {
		mv.addObject("shztList",dictService.getDictByDm(Constant.SHZTDM, "02,01,04"));
		mv.addObject("loginId",LUser.getGuid());
		mv.addObject("szdw",LUser.getOnlyDwmc());
		mv.addObject("dwbh",LUser.getDwbh());
		mv.addObject("today",DateUtil.getDay());
		mv.addObject("cclxList",dictService.getDict(Constant.CCLX));
		mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
		mv.addObject("jtgjList",dictService.getDict(Constant.JTGJ));
	}
//	//审核
//	@RequestMapping(value="verify",produces="text/html;charset=UTF-8")
//	@ResponseBody
//	public Object verify() {
//		if(ccywshService.verify(this.getPageData()) > 0) {
//			return "{\"success\":true,\"msg\":\"审核成功！\"}";
//		}else {
//			return "{\"success\":false,\"msg\":\"审核失败，请稍后重试！\"}";
//		}
//	}
	
	
	/**
	 * 审核弹窗
	 * @return
	 */
	@RequestMapping("/goVerifyPage")
	public ModelAndView goJsxxPage() {
		ModelAndView mv = this.getModelAndView();
		iniSelect(mv);
		String  status = Validate.isNullToDefaultString(this.getPageData().getString("status"),"");
		String types = "通过";
		if("false".equals(status)){
			types = "退回";
		}
		List list = rcbxService.getShyjListByLoginId(types);
		mv.addObject("list", list);
		mv.setViewName("wsbx/ccyw/ccywsh/ccywsh_verify");
		return mv;
	}
	
	/**
	 * 导出excel
	 * @return
	 */
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("select A.GUID AS GUID,A.DJBH AS DJBH,B.XMMC AS KTMC,A.CCTS AS CCTS, ");
		sqltext.append("(SELECT C.XM FROM GX_SYS_RYB C WHERE C.GUID = A.SQR) AS SQR,");
		sqltext.append("(SELECT C.MC FROM GX_SYS_DWB C WHERE C.DWBH = A.SZBM) AS SZBM,");
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.CCLX+"' AND C.DM = A.CCLX) AS CCLX, ");
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.JFLX+"' AND C.DM = B.JFLX) AS JFLX, ");
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.SHZTDM+"' AND C.DM = A.SHZT) AS SHZT ");
		sqltext.append("from CW_CCSQSPB A LEFT JOIN CW_JFSZB B ON A.JFBH = B.GUID ");
		sqltext.append("where 1 = 1");//where条件
		String guid = pd.getString("id");
		if(Validate.noNull(guid)){
			sqltext.append(" AND A.GUID IN ('"+guid+"') ");
		}else {
			String shzt = pd.getString("shzt");
			if(Validate.noNull(shzt)){
				sqltext.append(" AND A.SHZT IN ('"+shzt+"') ");
			}
		}
		sqltext.append(" ORDER BY A.DJBH ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("djbh");
		m1.setShowname("审核状态");
		mlist.add(m1);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("djbh");
		m2.setShowname("单据编号");
		mlist.add(m2);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("sqr");
		m3.setShowname("申请人");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("szbm");
		m4.setShowname("所在部门");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("cclx");
		m5.setShowname("出差类型");
		mlist.add(m5);
		M_largedata m6 = new M_largedata();
		m6.setColtype("String");
		m6.setName("jflx");
		m6.setShowname("经费类型");
		mlist.add(m6);
		M_largedata m7 = new M_largedata();
		m7.setColtype("String");
		m7.setName("ktmc");
		m7.setShowname("课题名称");
		mlist.add(m7);
		M_largedata m8 = new M_largedata();
		m8.setColtype("String");
		m8.setName("ccts");
		m8.setShowname("出差天数");
		mlist.add(m8);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 * 导出教师人员信息Excel   wzd
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		String status = pd.getString("status");
		String s1 = Constant.CCLX;
		String s2 = Constant.JFLX;
		String s3 = Constant.SHZTDM;
		String guid = pd.getString("id");
		logger.info("++++++++++++++++++++++"+guid);
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.ccywsqService.expExcel1(realfile, shortfileurl, guid,searchValue,rybh,s1,s2,s3,status);
	}
	
	/**
	 * 出差业务审核
	 * @author 
	 * @version 
	 */
	@RequestMapping(value = "/doApprove", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave( HttpSession session, OA_SHYJB shyjb,String pass,String cmd,String sfbj){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String procinstid=pd.getString("procinstid");
		String shyj = pd.getString("shyj");
		String apply = pd.getString("apply");//是否退回到申请人的标识，1：是，2否
		String forward = "";
		String guids[] = guid.split(",");
		String procinstids[] = procinstid.split(",");
		DshInfoMap msgMap = new DshInfoMap();
		if ("false".equals(pass)) {
			for(int i=0;i<guids.length;i++) {
				ccywshService.rejectleaveinfo(session, shyjb,guids[i],procinstids[i],shyj,apply);
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
				ccywshService.approveLeaveInfo( session,shyjb, guids[i],procinstids[i],shyj);
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
