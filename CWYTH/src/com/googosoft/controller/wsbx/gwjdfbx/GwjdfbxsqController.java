package com.googosoft.controller.wsbx.gwjdfbx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
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
import com.googosoft.service.systemset.qxgl.JsxxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.gwjdfbx.GwjdfbxsqService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.DshMessage;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;


@Controller
@RequestMapping("wsbx/gwjdfbx/gwjdfbxsq")
public class GwjdfbxsqController extends BaseController{
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	@Resource(name="gwjdfbxsqService")
	GwjdfbxsqService gwjdfbxsqService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name="pageService")
	PageService pageService;
	@Resource(name="fileService")
	FileService fileService;
	@Resource(name = "jsxxService")
	private JsxxService jsxxService;
	@Autowired
	private EchoService echoService;
	//初始化文件上传配置
	public void iniFileConfig(ModelAndView mv) {
		String[] fjxx = fileService.getFjList(this.getPageData().getString("guid"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
	}
	//初始化公务接待明细
	public void iniGwjdmx(PageData pd,ModelAndView mv) {
		Map<String, Object> gwjdmx = gwjdfbxsqService.getGwjdmxMapById(pd);
		mv.addObject("gwjdmx",gwjdmx);
	}
	//初始化当前登录人信息
	public void iniLogin(ModelAndView mv) {
		mv.addObject("loginId",LUser.getGuid());
		mv.addObject("dwmc",LUser.getDwmc());
		mv.addObject("ryxm","("+LUser.getRybh()+")"+LUser.getRyxm());
		mv.addObject("today",DateUtil.getDay());
	}
	//初始化结算方式
	public void iniJsfssz(ModelAndView mv,PageData pd) {
		mv.addObject("gwkList",gwjdfbxsqService.selectZffs(pd, "gwk"));
		mv.addObject("cjkList",gwjdfbxsqService.selectZffs(pd, "cjk"));
		mv.addObject("dgzfList",gwjdfbxsqService.selectZffs(pd, "dgzf"));
		mv.addObject("dszfList",gwjdfbxsqService.selectZffs(pd, "dszf"));
		mv.addObject("yhList",dictService.getDict(Constant.YHMC));
	}
	
	@RequestMapping("/updatesfwqbc")
	@ResponseBody
	public Object updatesfwqbc() {
		return gwjdfbxsqService.updatesfwqbc(this.getPageData().getString("zbid"));
	}
	
	@RequestMapping("/checkIsSubmit")
	@ResponseBody
	public Object checkIsSubmit() {
		Map<String,Object> map = gwjdfbxsqService.checkIsSubmit(this.getPageData().getString("zbid"));
		if(Validate.isNull(map.get("SFWQBC"))) {
			return 1;//oktosubmit
		}else {
			return 0;//no
		}
	}
	
	//页面之间的跳转
	@RequestMapping(value="/pageSkip")
	public ModelAndView PageSkip() {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		iniLogin(mv);
		mv.addObject("gwjdsqspGuid",pd.getString("gwjdsqspGuid"));
		String pageName = pd.getString("pageName");
		mv.addObject("look", Validate.isNullToDefault(pd.getString("look"), ""));
		switch (pageName) {
		case "gwjdfbxsq_list":
			mv.addObject("shztList",dictService.getDict(Constant.DJSHZT));
			mv.addObject("add", jsxxService.doCheckJsry(LUser.getRybh(), Constant.ROLE_YBBZY));
			break;
		case "gwjdfbxsh_list":
			break;
		case "gwjdmx_add":
			iniFileConfig(mv);
			Map map=gwjdfbxsqService.getgwjd(pd.getString("gwjdsqspGuid"));
			//mv.addObject("djbh",GenAutoKey.createKeyforth("cw_gwjdfbxzb", "JD", "djbh"));
			mv.addObject("guid",GenAutoKey.get32UUID());
			mv.addObject("gwjd",map);
			break;
		case "gwjdmx_edit":
			iniFileConfig(mv);
			iniGwjdmx(pd,mv);
			mv.addObject("sfbj", pd.getString("sfbj"));
			break;
		case "gwjdmx_view":
			iniFileConfig(mv);
			iniGwjdmx(pd,mv);
			iniJsfssz(mv,pd);
			mv.addObject("yhList",dictService.getDict(Constant.YHMC));
			break;
		case "jsfssz":
			mv.addObject("sfbj", pd.getString("sfbj"));
			iniJsfssz(mv,pd);
			iniGwjdmx(pd, mv);
			String dqdlrguid=LUser.getGuid();
			List<Map<String,Object>> dlryhklist=gwjdfbxsqService.getdlryhklist(); //获取当前登录人的银行卡list
			mv.addObject("dlryhklist",dlryhklist);
			mv.addObject("dqdlrguid",dqdlrguid);
			break;
		case "PrintSample":
			iniGwjdmx(pd, mv);
			break;
		case "check1":
			List list1 = rcbxService.getShyjListByLoginId("通过");
			mv.addObject("list", list1);
			break;
		case "check2":
			List list2 = rcbxService.getShyjListByLoginId("退回");
			mv.addObject("list", list2);
			break;
		case "check3":
			List list3 = rcbxService.getShyjListByLoginId("通过");
			mv.addObject("list", list3);
			break;
		case "check4":
			List list4 = rcbxService.getShyjListByLoginId("退回");
			mv.addObject("list", list4);
			break;
		case "gwjdmx_viewByMe":
			iniFileConfig(mv);
			iniJsfssz(mv,pd);
			iniGwjdmx(pd,mv);
			mv.addObject("yhList",dictService.getDict(Constant.YHMC));
			pageName = "gwjdmx_view";
			break;
		default:
			break;
		}
		mv.setViewName("wsbx/gwjdfbx/gwjdfbxsq/"+pageName);
		return mv;
	}	
	//获取列表数据
	@RequestMapping(value="getGwjdfbxsqPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getGwjdfbxsqPageData() {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		//String shzt = pd.getString("shzt");
		String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"00");
		//设置查询
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("(select a.CHECKSHZT,A.GUID,A.DJBH,A.PROCINSTID AS PROCINSTID,'('||rybh||')'||xm as bxry,RY.RYBH,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = A.SZBM) as szbm,A.BXJE,A.JDCS,to_char(A.JDRQ,'yyyy-mm-dd') as JDRQ,A.SHZT AS SHZTDM,");
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.LCSH+"' AND C.DM = A.SHZT) AS SHZT from CW_GWJDFBXZB A LEFT JOIN GX_SYS_RYB RY ON RY.rybh = A.BXRY where  1=1 order by a.djbh desc) B");
		//String tableName = " CW_GWJDFBXZB A ";
		pageList.setSqlText("*");
		pageList.setTableName(sqltext.toString());
		//pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("B.guid");
		String strWhere = "";
		//String shzt = pd.getString("shzt");
//		if(Validate.isNullOrEmpty(shzt)){
//			 strWhere += " and A.SHZT IN ('0') ";
//		}else {
//			strWhere += " and A.SHZT IN ('"+shzt+"') ";
//		}
		if("00".equals(shzt)){
			strWhere += " AND B.CHECKSHZT IN('00')";
		}else if("11".equals(shzt)){
			strWhere += " AND B.CHECKSHZT IN('11')";
		}else if("99".equals(shzt)){
			strWhere += " AND B.CHECKSHZT in('99')";
		}else{
			strWhere += " AND 1=1";
		}
		//strWhere += " and A.bxryid IN ('"+LUser.getGuid()+"') ";
		//
	   
	   // pageList.setTableName(tableName);
	    pageList.setTableName(sqltext.toString());
	    //pageList.setKeyId("A.guid");
	    pageList.setStrWhere(strWhere);
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	//获取列表数据
	@RequestMapping(value="getGwjdfbxshPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getGwjdfbxshPageData() {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		//设置查询
		pageList.setSqlText("*");
		String status = pd.getString("status");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" ( SELECT distinct A.GUID,A.DJBH,A.PROCINSTID,B.assignee_,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = A.BXRY) as bxry,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = A.SZBM) as szbm,A.BXJE,A.JDCS,to_char(A.JDRQ,'yyyy-mm-dd') as JDRQ,");
		if("0".equals(status) || "".equals(status)){
			sqltext.append(" '0' as shztdm, ");
		}else if("1".equals(status)){
			sqltext.append(" '1' as shztdm, ");
		}
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.LCSH+"' AND C.DM = A.SHZT) AS SHZT");
		if("0".equals(status) || Validate.isNullOrEmpty(status)){
			sqltext.append(" FROM CW_GWJDFBXZB A left join ACT_RU_TASK B ON A.PROCINSTID = B.PROC_INST_ID_ ) k");
		}else if("1".equals(status)){
			sqltext.append(" FROM CW_GWJDFBXZB A left join act_hi_actinst B ON A.PROCINSTID = B.PROC_INST_ID_ and b.END_TIME_ is not null ) K");
		}
		String strWhere = " AND K.SHZT not in ('00') and K.assignee_='"+LUser.getGuid()+"' ";
		//
		// 表名
		pageList.setTableName(sqltext.toString());
//		pageList.setSqlText(sqltext.toString());
//		pageList.setTableName(tableName);
		pageList.setKeyId("k.guid");
		pageList.setStrWhere(strWhere);
		//页面数据
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping(value = "/getGwjdPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("a.guid,a.djbh,(select '('||RYBH||')'||xm from gx_sys_ryb where rybh = a.sqr) as sqr,(select '('||DWBH||')'||mc from gx_sys_dwb where dwbh = a.jdbm) as jddw,"
				+ "jdrq,LBDW,JDFD,sqrq" );
		String tableName = " CW_GWJDYWSQSPB a ";
//		String strWhere = " and ( SQR = '"+LUser.getRybh()+"' or jzbxr = '"+LUser.getRybh()+"' ) and a.shzt = '06' and a.guid not in "
//				+ "(select sqspbh from cw_gwjdbxdzb) ";
		String strWhere = " and a.shzt = '06' and a.guid not in "
				+ "(select sqspbh from cw_gwjdbxdzb) ";
		pageList.setSqlText(sqltext.toString());
		pageList.setTableName(tableName);
		pageList.setKeyId("a.GUID");//主键
		pageList.setStrWhere(strWhere); 
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	//审核
	@RequestMapping(value="/verify",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object verify() {
		if(gwjdfbxsqService.verify(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"审核成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"审核失败，请稍后重试！\"}";
		}
	}
	//添加公务接待费报销申请
	@RequestMapping(value="/gwjdmxAdd",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object gwjdmxAdd() {
		if(gwjdfbxsqService.addGwjdmx(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"保存成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"保存失败，请稍后重试！\"}";
		}
	}
	@RequestMapping(value="/updateJsfs",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object updateJsfs() {
		if(gwjdfbxsqService.editJsfs(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"保存成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"保存失败，请稍后重试！\"}";
		}
	}
	//编辑公务接待费报销申请
	@RequestMapping(value="/gwjdfbxsqEdit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object gwjdfbxsqEdit() {
		if(gwjdfbxsqService.editGwjdmx(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"保存成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"保存失败，请稍后重试！\"}";
		}
	}
	//删除
	@RequestMapping(value="/gwjdfbxsqDelete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object delete() {
		if(gwjdfbxsqService.deleteGwjdfbxsq(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"删除成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"删除失败，请稍后重试！\"}";
		}
	}
//	//提交
//	@RequestMapping(value="/submit",produces="text/html;charset=UTF-8")
//	@ResponseBody
//	public Object submit() {
//		if(gwjdfbxsqService.submit(this.getPageData()) > 0) {
//			return "{\"success\":true,\"msg\":\"提交成功！\"}";
//		}else {
//			return "{\"success\":false,\"msg\":\"提交失败，请稍后重试！\"}";
//		}
//	}
	//检查单据编号是否存在
	@RequestMapping("/checkDjbhExist")
	@ResponseBody
	public Object checkGwjdfbxsqbhExist() {
		if(gwjdfbxsqService.checkDjbhExist(this.getPageData())) {
			return "{\"exist\":true}";
		}else {
			return "{\"exist\":false}";
		}
	}
	//导出excel
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
		String shzt = pd.getString("shzt");
		String guid = pd.getString("guid");
		System.err.println("GUID++++++++++"+guid);
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("SELECT ");
		sqltext.append(" a.CHECKSHZT,A.GUID,A.DJBH,A.PROCINSTID AS PROCINSTID,'('||rybh||')'||xm as bxry,RY.RYBH,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = A.SZBM) as szbm,A.BXJE,A.JDCS,to_char(A.JDRQ,'yyyy-mm-dd') as JDRQ,A.SHZT AS SHZTDM,");
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.LCSH+"' AND C.DM = A.SHZT) AS SHZT ");
		
		sqltext.append("from CW_GWJDFBXZB A  LEFT JOIN GX_SYS_RYB RY ON RY.rybh = A.BXRY where  1=1  ");
		if("00".equals(shzt)){
			sqltext.append( " AND A.CHECKSHZT IN('00')");
		}else if("11".equals(shzt)){
			sqltext.append(" AND A.CHECKSHZT IN('11')");
		}else if("99".equals(shzt)){
			sqltext.append( " AND A.CHECKSHZT in('99')");
		}else{
			sqltext.append( " AND 1=1");
		}
		
		if(Validate.noNull(guid)){
			sqltext.append(" AND A.GUID IN ('"+guid.trim()+"') ");
		}
		/*String shzt = pd.getString("shzt");
		if(Validate.noNull(shzt)){
			sqltext.append(" AND A.SHZT IN ('"+shzt+"') ");
		}
		sqltext.append(" ORDER BY A.DJBH ");*/
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("shzt");
		m1.setShowname("审核状态");
		mlist.add(m1);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("djbh");
		m2.setShowname("单据编号");
		mlist.add(m2);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("bxry");
		m3.setShowname("报销人员");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("szbm");
		m4.setShowname("所在部门");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("bxje");
		m5.setShowname("报销金额");
		mlist.add(m5);
		M_largedata m6 = new M_largedata();
		m6.setColtype("String");
		m6.setName("jdcs");
		m6.setShowname("接待场所");
		mlist.add(m6);
		M_largedata m7 = new M_largedata();
		m7.setColtype("String");
		m7.setName("jdrq");
		m7.setShowname("接待日期");
		mlist.add(m7);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	/**
	 * 公务接待费用报销审核
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		//String searchJson = pd.getString("searchJson");
		/*StringBuffer sqltext = new StringBuffer();
		String shzt = pd.getString("shzt");
		String guid = pd.getString("guid");
		sqltext.append("SELECT A.GUID,A.DJBH,A.PROCINSTID,A.BXRY,A.SZBM,A.BXJE,A.JDCS,to_char(A.JDRQ,'yyyy-mm-dd') as JDRQ,");
		if("0".equals(shzt) || "".equals(shzt)){
			sqltext.append(" '0' as shztdm, ");
		}else if("1".equals(shzt)){
			sqltext.append(" '1' as shztdm, ");
		}
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.DJSHZT+"' AND C.DM = A.SHZT) AS SHZT from");
		
		
		if("0".equals(shzt) || Validate.isNullOrEmpty(shzt)){
			sqltext.append( " CW_GWJDFBXZB A left join ACT_RU_TASK B ON A.PROCINSTID = B.PROC_INST_ID_ ");
		}else if("1".equals(shzt)){
			sqltext.append( " CW_GWJDFBXZB A left join act_hi_actinst B ON A.PROCINSTID = B.PROC_INST_ID_ and b.END_TIME_ is not null " );
		}
		sqltext.append(" WHERE 1=1 AND A.SHZT not in ('0') and B.assignee_='"+LUser.getGuid()+"' " );
		if(Validate.noNull(guid)){
			sqltext.append(" AND A.GUID IN ('"+guid.trim()+"') ");
		}
		sqltext.append(" order by DJBH asc ");*/
		String guid=pd.getString("id");
		String status = pd.getString("status");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("distinct A.GUID,A.DJBH,A.PROCINSTID,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = A.BXRY) as bxry,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = A.SZBM) as szbm,A.BXJE,A.JDCS,to_char(A.JDRQ,'yyyy-mm-dd') as JDRQ,");
		if("0".equals(status) || "".equals(status)){
			sqltext.append(" '0' as shztdm, ");
		}else if("1".equals(status)){
			sqltext.append(" '1' as shztdm, ");
		}
		sqltext.append("(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.LCSH+"' AND C.DM = A.SHZT) AS SHZT");
		sqltext.append(" WHERE ");
		if("0".equals(status) || Validate.isNullOrEmpty(status)){
			sqltext.append(" CW_GWJDFBXZB A left join ACT_RU_TASK B ON A.PROCINSTID = B.PROC_INST_ID_ ");
		}else {
			sqltext.append(" CW_GWJDFBXZB A left join act_hi_actinst B ON A.PROCINSTID = B.PROC_INST_ID_ and b.END_TIME_ is not null ");
		}
		sqltext.append("WHERE A.SHZT not in ('0') and B.assignee_='"+LUser.getGuid()+"' AND A.GUID IN('"+ToSqlUtil.pointToSql(guid)+"')");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.gwjdfbxsqService.expExcel(realfile, shortfileurl, guid,"",sqltext.toString());
	}
	/**
	 * 公务接待费报销提交
	 * 
	 * @return
	 */
	@RequestMapping(value = "/submit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object submit() {
		String guid = this.getPageData().getString("guid");
		String ary[] = guid.split(",");
		int i = 0;
		DshInfoMap msgMap = new DshInfoMap();
		for(int a=0;a<ary.length;a++){
			String id = Validate.isNullToDefaultString(ary[a], "");
			if(Validate.noNull(id)){
				String procinstid = gwjdfbxsqService.submit(id, "", "1","");
				if(Validate.noNull(procinstid)) {
					i++;
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstid);
					//如果不是审核通过的单据（通过的会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getGwjdbxDshxxMsg(id);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
		}
		if(i > 0) {
			EchoUtil.batchSendDshxxMsg(msgMap);
			return "{\"success\":true,\"msg\":\"提交成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"提交失败，请稍后重试！\"}";
		}
	}
	/**
	 * 公务接待费报销审核
	 * @author 
	 * @version 
	 */
	@RequestMapping(value = "/doApprove", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave( HttpSession session, OA_SHYJB shyjb,String pass,String cmd,String sfbj){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String guids[] = guid.split(","); 
		String procinstid=pd.getString("procinstid");
		String procinstids[] = procinstid.split(",");
		String shyj = pd.getString("shyj");
		String forward = "";
		DshInfoMap msgMap = new DshInfoMap();
		if ("false".equals(pass)) {
			for(int i=0;i<guids.length;i++) {
				gwjdfbxsqService.rejectleaveinfo(session, guids[i],procinstids[i],shyj,shyjb);
				if(Validate.noNull(procinstids[i])) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstids[i]);
					//如果不是审核通过的单据（通过的会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getGwjdbxDshxxMsg(guids[i]);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
			forward = "{success:true, msg:'退回成功！'}";
		} else {
			//通过
			for(int i=0;i<guids.length;i++) {
				gwjdfbxsqService.approveLeaveInfo( session,shyjb, guids[i],procinstids[i],shyj);
				if(Validate.noNull(procinstids[i])) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstids[i]);
					//如果不是审核通过的单据（通过的会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getGwjdbxDshxxMsg(guids[i]);
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
	 * 获取 项目的 可用 金额
	 */
	@RequestMapping(value="/getXmkyje",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object getXmkyje(HttpSession session){
		String xmguid = gwjdfbxsqService.getGwjdfXmguid();
		BigDecimal kyje = CommonUtil.getXmkyje(xmguid,session);
		return "{\"kyje\":\""+kyje+"\"}";
	}
	
}
