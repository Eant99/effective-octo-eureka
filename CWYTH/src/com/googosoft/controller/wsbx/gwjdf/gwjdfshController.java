package com.googosoft.controller.wsbx.gwjdf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.gwjd.GwjdSqService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;

/**
 * 公务接待审核controller
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value = "/gwjdfsh")
public class gwjdfshController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例

	@Resource(name = "dictService")
	private DictService dictService;
	@Resource(name="gwjdsqService")
	private GwjdSqService gwjdsqService;
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	@Resource(name="fileService")
	private FileService fileService;//单例
	@Autowired
	EchoService echoService;
	
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	
	/**
	 * 跳转公务接待审核列表页面
	 * @return
	 */
	@RequestMapping("/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("shztList",dictService.getDict(Constant.SHZTDMGW));
		mv.setViewName("wsbx/gwjdf/gwjdfsh_list");
		return mv;
	}
	
	
	/**
	 * 获取公务接待审核列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
//		PageData pd = this.getPageData();
//		StringBuffer sqltext = new StringBuffer();//查询字段
//		String shztm=pd.getString("shztm");
//		sqltext.append(" K.GUID,(SELECT MC FROM GX_SYS_DMB WHERE ZL='11033' AND DM=K.SHZT) SHZTMC,K.SHZT, "
//				+ "K.DJBH,nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=K.sqr),'')SQR,"
//				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=K.jdbm) JDBM,K.JDRQ,K.LBDW,K.JDFD,K.SQRQ");
//		PageList pageList = new PageList();
//		String strWhere = "";
//		pageList.setSqlText(sqltext.toString());
//		pageList.setKeyId("GUID");//主键
//		System.out.println("shztm==========="+shztm);
//		if (shztm.equals("02")||shztm.equals("04")) {// 审核状态筛选
//			strWhere +=" and shzt='02' or shzt='04'";
//		} else if(shztm.equals("01")){
//			strWhere +=" and shzt='01'";
//		}else{
//			strWhere +=" and shzt='01'";
//		}
//		
//		pageList.setStrWhere(strWhere);
//		pageList.setTableName("CW_GWJDYWSQSPB K");//表名
//		pageList.setHj1("");//合计
//	    pageList = pageService.findPageList(pd,pageList);
//		Gson gson = new Gson();
//		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
//		return pageInfo.toJson();
		
		
		 PageData pd = this.getPageData();
		  String shztm=pd.getString("shztm");
		  
			StringBuffer sqltext = new StringBuffer();//查询字段
			String status = pd.getString("status");
		//	String dwbh = pd.getString("treedwbh");
			PageList pageList = new PageList();
			sqltext.append(" distinct k.djbh, K.GUID,K.PROCINSTID,(SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.SHZTDMGW+"' AND DM=K.SHZT) SHZTMC,K.SHZT, "
					+ "nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=K.sqr),'')SQR,"
					+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=K.jdbm) JDBM,K.JDRQ,K.LBDW,K.JDFD,K.SQRQ, ");
			if("0".equals(status) || "".equals(status) || status == null) {
				sqltext.append(" '0' as shztdm ");
			}else if("1".equals(status)) {
				sqltext.append(" '1' as shztdm ");
			}
			String tableName = " ";
			String strWhere = " AND K.SHZT NOT IN ('01','03') and C.assignee_='"+LUser.getGuid()+"' ";
			//设置条件
			if("0".equals(status) || "".equals(status) || status == null) {
				tableName += " CW_GWJDYWSQSPB K left join ACT_RU_TASK C ON K.PROCINSTID = C.PROC_INST_ID_ ";
			}else if("1".equals(status)) {
				tableName += "  CW_GWJDYWSQSPB K  left join act_hi_actinst C ON K.PROCINSTID = C.PROC_INST_ID_  and c.END_TIME_ is not null";
			}
			
		    pageList.setSqlText(sqltext.toString());
		    pageList.setTableName(tableName);
		    pageList.setKeyId(" K.guid ");
		    pageList.setStrWhere(strWhere);
			
			//设置WHERE条件
			pageList.setHj1("");//合计
		    pageList = pageService.findPageList(pd,pageList);
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		
	}
	/**
	 * 跳转Edit页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		ModelAndView mv = this.getModelAndView();
		
		String operateType = this.getPageData().getString("operateType");
		String shztm = this.getPageData().getString("shztm");
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		mv.addObject("loginId", loginId);
		Map map = gwjdsqService.getObjectById(this.getPageData().getString("guid"));
		mv.addObject("gwjdfsq",map);
		mv.addObject("guid",this.getPageData().getString("guid"));
		
		List list = gwjdsqService.getMxList(this.getPageData().getString("guid"));
		mv.addObject("mxList",list);
		
		String[] fjxx = fileService.getFjList(this.getPageData().getString("guid"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
	 if(operateType.equals("U")||operateType.equals("L"))
		{
			
			mv.setViewName("wsbx/gwjdf/gwjdfsh_edit");
		}
		else if(operateType.equals("V")||operateType.equals("L"))
		{
			
			mv.setViewName("wsbx/gwjdf/gwjdfsh_view");
		}
		
	   mv.addObject("shztm",shztm);
		mv.addObject("operateType", operateType);
		return mv;
	}
	
	/**
	 * 跳转审核弹窗页面
	 * @return
	 */
	@RequestMapping(value="/check")
	public ModelAndView check(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid =pd.getString("guid");
		String type = pd.getString("type");
		List<Map<String, Object>> shyj = dictService.getDict("shyj");
		String url = "wsbx/gwjdf/check2";
		if("first".equals(type)){
			url = "wsbx/gwjdf/check1";
		}else if("three".equals(type)){
			url = "wsbx/gwjdf/check3";
		}
		mv.addObject("guid", guid);
		mv.addObject("shyjList", shyj);
		mv.setViewName(url);
		return mv;
	}
	
	
	/**
	 * 信息审核通过通过
	 * @return
	 */
	@RequestMapping(value="/pass",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object xxPass(){
		String guid = this.getPageData().getString("guid");
		String shyj = this.getPageData().getString("shyj");
		int i = gwjdsqService.xxPass(guid,shyj);
		int result=0;
		if(i>0){
			Map map = gwjdsqService.getPassObjectById(guid);
			String uuid=this.get32UUID();
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");    
			String czrq = tempDate.format(new java.util.Date());
			String czrbh=LUser.getRygh();
			map.put("uuid", uuid);
			map.put("czrq", czrq);
			map.put("czr", czrbh);
			result = gwjdsqService.doPassAdd(map);
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
		
	}
	/**
	 * 信息审核退回
	 * @return
	 */
	@RequestMapping(value="/refuse",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object xxRefuse(){
		String guid = this.getPageData().getString("guid");
		String shyj = this.getPageData().getString("shyj");
		int i = gwjdsqService.xxRefuse(guid,shyj);
		if(i>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
		
	}
	/**
	 * 导出公务接待信息Excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/expExcel", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel() {
		PageData pd = this.getPageData();
		// 临时文件名
		String file = System.currentTimeMillis() + "";
		// 文件绝对路径
		String realfile = this.getRequest().getServletContext()
				.getRealPath("\\")
				+ "WEB-INF\\file\\excel\\" + file + ".xls";
		// 下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		// 查询数据的sql语句
		String searchJson = pd.getString("searchJson");
//		StringBuffer sqltext = new StringBuffer();
		// 设置查询字段名
//		sqltext.append("SELECT K.GUID,(SELECT MC FROM GX_SYS_DMB WHERE ZL='11033' AND DM=K.SHZT) SHZTMC,K.SHZT, "
//				+ "K.DJBH,nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=K.sqr),'')SQR,"
//				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=K.jdbm) JDBM,K.JDRQ,K.LBDW,K.JDFD,K.SQRQ");
//		sqltext.append(" FROM CW_GWJDYWSQSPB K WHERE 1=1");
//		String shztm=pd.getString("shztm");
//		if (Validate.noNull(shztm)) {// 审核状态筛选
//			sqltext.append("and shzt="+ shztm);
//		} else {// 单位权限
//			sqltext.append("and shzt='03'");
//		}
//		
//		if (shztm.equals("02")||shztm.equals("04")) {// 审核状态筛选
//			sqltext.append(" and shzt='02' or shzt='04'");
//			
//		} else if(shztm.equals("01")){
//			sqltext.append(" and shzt='01'");
//			
//		}else{
//			sqltext.append(" and shzt='01'");
//			
//		}
//		//sqltext.append(CommonUtils.jsonToSql(searchJson));
//		String id = pd.getString("guid");
//		if (Validate.noNull(id)) {
//			sqltext.append(" AND .guid IN ('" + id.replace(",", "','") + "') ");
//		}
//		sqltext.append(" ORDER BY K.guid ");
		
		
		StringBuffer sqltext = new StringBuffer();//查询字段
		String status = pd.getString("status");
	//	String dwbh = pd.getString("treedwbh");
		PageList pageList = new PageList();
		sqltext.append("  distinct k.djbh, K.GUID,K.PROCINSTID,(SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.SHZTDMGW+"' AND DM=K.SHZT) SHZTMC,K.SHZT, "
				+ "nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=K.sqr),'')SQR,"
				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=K.jdbm) JDBM,K.JDRQ,K.LBDW,K.JDFD,K.SQRQ, ");
		if("0".equals(status) || "".equals(status) || status == null) {
			sqltext.append(" '0' as shztdm ");
		}else if("1".equals(status)) {
			sqltext.append(" '1' as shztdm ");
		}
		String tableName = " ";
		String strWhere = " AND K.SHZT NOT IN ('01') and C.assignee_='"+LUser.getGuid()+"' ";
		//设置条件
		if("0".equals(status) || "".equals(status) || status == null) {
			tableName += " CW_GWJDYWSQSPB K left join ACT_RU_TASK C ON K.PROCINSTID = C.PROC_INST_ID_ ";
		}else if("1".equals(status)) {
			tableName += "  CW_GWJDYWSQSPB K  left join ACT_HI_TASKINST C ON K.PROCINSTID = C.PROC_INST_ID_ ";
		}
		String sql = "select " + sqltext.toString() + " from " + tableName + " where 1=1 " + strWhere;
		
		
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		m1.setName("shztmc");
		m1.setShowname("审核状态");
		mlist.add(m1);

		M_largedata m2 = new M_largedata();
		m2.setName("djbh");
		m2.setShowname("单据编号");
		mlist.add(m2);

		M_largedata m3 = new M_largedata();
		m3.setName("sqr");
		m3.setShowname("申请人");
		mlist.add(m3);

		M_largedata m4 = new M_largedata();
		m4.setName("jdbm");
		m4.setShowname("接待部门");
		mlist.add(m4);

		M_largedata m5 = new M_largedata();
		m5.setName("lbdw");
		m5.setShowname("来宾单位");
		mlist.add(m5);
		
		M_largedata m6 = new M_largedata();
		m6.setName("jdfd");
		m6.setShowname("接待饭店");
		mlist.add(m6);


		M_largedata m7 = new M_largedata();
		m7.setName("sqrq");
		m7.setShowname("申请日期");
		mlist.add(m7);

		

		// 导出方法
		pageService.ExpExcel(sql, realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}
	/**
	 * 出差业务审核
	 * @author 
	 * @version 
	 */
	@RequestMapping(value = "/doApprove", produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doSave( HttpSession session, OA_SHYJB shyjb,String pass,String cmd,String sfbj){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
	    String guids[] = guid.split(",");
		String procinstid=pd.getString("procinstid");
		String apply=pd.getString("apply");
		String procinstids[] = procinstid.split(",");
		String shyj = pd.getString("shyj");
		String forward = "";
		DshInfoMap msgMap = new DshInfoMap();
		if ("false".equals(pass)) {
			for(int i=0;i<guids.length;i++) {
//				gwjdsqService.rejectleaveinfo(session, guids[i],procinstids[i],shyj,apply);
				gwjdsqService.rejectleaveinfo(session, guids[i], procinstids[i], shyj, apply, shyjb);
				if(Validate.noNull(procinstids[i])) {
					String shr = echoService.getShrGuid(procinstids[i]);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getGwjdsqspDshxxMsg(guids[i]);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
			forward = "{success:true, msg:'退回成功！'}";
		} else {
			//通过
			for(int i=0;i<guids.length;i++) {
				gwjdsqService.approveLeaveInfo( session,shyjb, guids[i],procinstids[i],shyj);
				if(Validate.noNull(procinstids[i])) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstids[i]);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getGwjdsqspDshxxMsg(guids[i]);
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
	
	/**
	 * 封装页面跳转控制类方法
	 * @return
	 */
	@RequestMapping("/pageSkip")
	public ModelAndView pageSkip() {
		ModelAndView mv = this.getModelAndView();
		String pageName = this.getPageData().getString("pageName");
		String  status = Validate.isNullToDefaultString(this.getPageData().getString("status"),"");
		String types = "通过";
		if("false".equals(status)){
			types = "退回";
		}
		List list = rcbxService.getShyjListByLoginId(types);
		mv.addObject("list", list);
		mv.setViewName("wsbx/gwjdf/"+pageName);
		return mv;
		
	}
	
	/**
	 * 导出公务接待审核
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();//查询字段
		String status = pd.getString("status");
	//	String dwbh = pd.getString("treedwbh");
		PageList pageList = new PageList();
		sqltext.append("  distinct k.djbh, K.GUID,K.PROCINSTID,(SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.SHZTDMGW+"' AND DM=K.SHZT) SHZTMC,K.SHZT, "
				+ "nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=K.sqr),'')SQR,"
				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=K.jdbm) JDBM,K.JDRQ,K.LBDW,K.JDFD,K.SQRQ, ");
		if("0".equals(status) || "".equals(status) || status == null) {
			sqltext.append(" '0' as shztdm ");
		}else if("1".equals(status)) {
			sqltext.append(" '1' as shztdm ");
		}
		String tableName = " ";
		StringBuffer strWhere = new StringBuffer();
		strWhere.append(" AND K.SHZT NOT IN ('01') and C.assignee_='"+LUser.getGuid()+"' ");		
		String guid = pd.getString("id");
		if (Validate.noNull(guid)) {
			strWhere.append(" AND K.guid IN ('"+guid.trim()+"') ");
		}
		//设置条件
		if("0".equals(status) || "".equals(status) || status == null) {
			tableName += " CW_GWJDYWSQSPB K left join ACT_RU_TASK C ON K.PROCINSTID = C.PROC_INST_ID_ ";
		}else if("1".equals(status)) {
			tableName += "  CW_GWJDYWSQSPB K  left join ACT_HI_TASKINST C ON K.PROCINSTID = C.PROC_INST_ID_ ";
		}
		String sql = "select " + sqltext.toString() + " from " + tableName + " where 1=1 " + strWhere.toString();
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.gwjdsqService.expExcel1(realfile, shortfileurl, guid,searchValue,rybh,sql);
	}
}
