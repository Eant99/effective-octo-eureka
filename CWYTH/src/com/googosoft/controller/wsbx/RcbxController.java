package com.googosoft.controller.wsbx;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.CW_CJKB;
import com.googosoft.pojo.wsbx.CW_DGZFB;
import com.googosoft.pojo.wsbx.CW_GWKB;
import com.googosoft.pojo.wsbx.CW_LYEB;
import com.googosoft.pojo.wsbx.Cw_DSZFB;
import com.googosoft.pojo.wsbx.Rcbxmx;
import com.googosoft.pojo.wsbx.Rcbxzb;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.kylbx.KylbxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.gwjdfbx.GwjdfbxsqService;
import com.googosoft.service.zdscpz.ZdscpzService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;

/**
 * 日常报销控制类
 * 
 * @author googosoft
 * 
 */
@Controller
@RequestMapping(value = "/wsbx/rcbx")
public class RcbxController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;

	@Resource(name = "dictService")
	private DictService dictService;
	
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	
	@Resource(name="zdscpzService")
	private ZdscpzService zdscpzService;//凭证自动化
	@Resource(name="fileService")
	private FileService fileService;//单例
	@Resource(name="kylbxService")
	private KylbxService kylbxService;//单例
	@Autowired
	private EchoService echoService;
	
	@Resource(name="gwjdfbxsqService")
	GwjdfbxsqService gwjdfbxsqService;

	/**
	 * 跳转日常报销列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goRcbxListPage")
	public ModelAndView goRcbxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		
		List<Map<String, Object>> shztList = dictService.getDict(Constant.DJSHZT);//审核状态
		List<Map<String, Object>> zffsList = dictService.getDict(Constant.ZFFSDM);//支付方式
		
		mv.addObject("mkbh", pd.getString("mkbh"));
		mv.addObject("zffsList", zffsList);
		mv.addObject("shztList", shztList);
		mv.addObject("shztdm", pd.get("shztdm"));
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		mv.setViewName("wsbx/rcbx/rcbx_list");
		return mv;
	}
	/**
	 * 获取申请人所在单位是否学院
	 * @return
	 */
	@RequestMapping("/checkSfxy")
	@ResponseBody
	public String checkSfxy() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		return rcbxService.getSfxy(guid);
	}
	/**
	 * 批量审核获取申请人所在单位是否学院
	 * @return
	 */
	@RequestMapping("/checkSfxypl")
	@ResponseBody
	public String checkSfxypl() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		return rcbxService.getSfxypl(guid);
	}
	/**
	 * 查看该谁审核
	 * @return
	 */
	@RequestMapping("/checkWhoSh")
	@ResponseBody
	public Map checkWhoSh() {
		return rcbxService.checkWhoSh(this.getPageData());
	}
	/**
	 * 跳转日常报销列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goCheckListPage")
	public ModelAndView goCheckListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		
		List<Map<String, Object>> shztList = dictService.getDict(Constant.DJSHZT);//审核状态
		List<Map<String, Object>> zffsList = dictService.getDict(Constant.ZFFSDM);//支付方式
		String bmje = rcbxService.getBmje();
		
		mv.addObject("zffsList", zffsList);
		mv.addObject("shztList", shztList);
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		mv.addObject("bmje", bmje);
		mv.setViewName("wsbx/rcbx/check_list");
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
		String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"00");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append("(select a.checkshzt,A.GUID,A.DJBH,A.XMMC AS XMGUID,A.FJZZS,A.SHZT AS SHZTDM,a.sfxy,a.PROCINSTID as PROCINSTID,");
		sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm999999999999999999990.00')))BXZJE,");
		sql.append(" TO_CHAR(A.CZRQ,'YYYY-MM-DD')CZRQ,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZBM)AS SZBM,");
		sql.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.BXR)AS BXR,");
		sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=A.SHZT)SHZT,a.sfwqbc FROM CW_RCBXZB A where a.bxr='"+LUser.getGuid()+"' order by a.djbh desc) B");
		pageList.setSqlText("*");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("guid");
		// 设置WHERE条件
		String strWhere = " ";
//		if(Validate.noNull(shztdm)&&"all".equals(shztdm)){
//			strWhere += " AND 1=1";
//		}
//		if(Validate.noNull(shztdm)&&"00".equals(shztdm)){
//			strWhere += " AND B.SHZT IN('财务预审退回','未提交')";
//		}
//		if(Validate.noNull(shztdm)&&"11".equals(shztdm)){
//			strWhere += " AND B.SHZT NOT IN('财务预审退回','未提交','审核通过')";
//		}
//		if(Validate.noNull(shztdm)&&"99".equals(shztdm)){
//			strWhere += " AND B.SHZT = '审核通过'";
//		}
		
		if("00".equals(shzt)){//未提交
			strWhere += " AND B.checkshzt IN('00')";
		}else if("11".equals(shzt)){//审核中
			strWhere += " AND B.checkshzt IN('11')";
		}else if("99".equals(shzt)){//已通过
			strWhere += " AND B.checkshzt in('99')";
		}else{
			strWhere += " AND 1=1";
		}
		
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取日常报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCheckPageList")
	@ResponseBody
	public Object getCheckPageList() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" A.GUID,A.DJBH,A.XMMC AS XMGUID,A.FJZZS,A.SHZT AS SHZTDM,");
		sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm99999999999999999990.00')))BXZJE,");
		sql.append(" TO_CHAR(A.CZRQ,'YYYY-MM-DD')CZRQ,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZBM)AS SZBM,");
		sql.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.BXR)AS BXR,");
		sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=A.SHZT)SHZT");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_RCBXZB A");
		// 主键
		pageList.setKeyId("GUID");
		String rybh = LUser.getRybh();// 获取当前登录人员编号
		// 设置WHERE条件
		String strWhere = " ";
		//登录人是财务人员
		if(rcbxService.getJsBh(rybh, Constant.CWRY)){
			strWhere += " or a.shzt='1'";
		}
		//登录人是财务负责人
		if(rcbxService.getJsBh(rybh, Constant.CWFZR)){
			strWhere += " or a.shzt='3'";
		}
		//登录人是财务分管领导
		if(rcbxService.getJsBh(rybh, Constant.CWFGLD)){
			strWhere += " or a.shzt='5'";
		}
		//登录人是校长
		if(rybh.equals(rcbxService.getSchoolMaster())){
			strWhere += " or a.shzt='6'";
		}
		//登录人是部门领导
		if(rcbxService.checkSfLd("部门领导")){
			strWhere += " or (a.shzt='2' and a.szbm='"+LUser.getDwbh()+"')";
		}
		//登录人是部门分管领导
		if(rcbxService.checkSfLd("部门分管领导")){
			strWhere += " or (a.shzt='4' and a.szbm='"+LUser.getDwbh()+"')";
		}
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 对私支付选择项目负责人
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXmfzrPageList")
	@ResponseBody
	public Object getXmfzrPageList() throws Exception {
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		String ccywguid = pd.getString("ccywguid");
//		if(ccywguid.contains(",")){
//			ccywguid.replace(",","','");
//		}
		String sqltext = "";
		StringBuffer tableName = new StringBuffer();
		// 设置查询字段名
		sqltext = " guid,ryxm,rybh,xb,XMMC ";
		tableName.append(" (select distinct(guid)as guid,ryxm,rybh,xb,XMMC from (select (select guid from gx_sys_ryb where rybh = s.fzr) guid,(select xm from gx_sys_ryb where rybh = s.fzr) ryxm,(select rybh from gx_sys_ryb where rybh = s.fzr) rybh,   ");
		tableName.append(" (select  decode(xb,1,'男',2,'女','未知') from gx_sys_ryb where rybh = s.fzr) xb,S.XMMC ");
		tableName.append("  from XMINFOS s left join CW_RCBXMXB x on x.xmguid = s.guid where s.guid in ('"+ccywguid.replace(",","','")+"') ) b )b");
		pageList.setSqlText(sqltext);
		pageList.setKeyId(" b.guid ");//主键
		pageList.setTableName(tableName.toString());//表名
		pageList = pageService.findPageList(pd,pageList);//引用传递
		// 设置WHERE条件
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 获取日常报销待审核页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRcbxDshPageList")
	@ResponseBody
	public Object getRcbxDshPageList() throws Exception {
		
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		pageList.setSqlText("*");
		ModelAndView mv = new ModelAndView();
		String status = Validate.isNullToDefaultString(pd.getString("status"), "0");
		mv.addObject("status",status);
		if(Validate.noNull(status)&&"1".equals(status)){
			
		}
		StringBuffer tablename = new StringBuffer();//查询字段
		StringBuffer sql = new StringBuffer();
		// 设置查询字段名
		if(Validate.noNull(status)&&"0".equals(status)){
			tablename.append(" (select  * from  (select distinct A.DJBH, A.GUID,A.XMMC AS XMGUID,A.FJZZS,A.SHZT AS SHZTDM,A.PROCINSTID as PROCINSTID,act.assignee_,A.BXSY, ");
			tablename.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm9999999999999999999999990.00')))BXZJE,"
					+ " (SELECT  WM_CONCAT(distinct C.XMMC) FROM CW_RCBXMXB C WHERE C.zbid=A.guid) AS BXXM,(SELECT WM_CONCAT( B.XMMC) FROM CW_RCBXZB S  LEFT JOIN CW_RCBXXMMXB B  ON S.guid = B.zbid   WHERE S.guid = A.GUID) AS XMMC, ");
			tablename.append(" TO_CHAR(A.CZRQ,'YYYY-MM-DD')CZRQ,");
			tablename.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZBM)AS SZBM,");
			tablename.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.BXR)AS BXR,");
			tablename.append("  (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=A.SHZT)SHZT");
			tablename.append(" from CW_RCBXZB A  LEFT JOIN ACT_RU_TASK ACT ON A.PROCINSTID = ACT.PROC_INST_ID_ where a.checkshzt<>'00'  order by a.djbh desc)k where k.SHZT !='未提交' and k.assignee_='"+LUser.getGuid()+"' ) D");
		}else{
			tablename.append(" (select  * from  (select distinct A.DJBH, A.GUID,A.XMMC AS XMGUID,A.FJZZS,A.SHZT AS SHZTDM,A.PROCINSTID as PROCINSTID,act.assignee_,A.BXSY, ");
			tablename.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm99999999999999999999999999990.00')))BXZJE,"
					+ "(SELECT  WM_CONCAT(distinct C.XMMC) FROM CW_RCBXMXB C WHERE C.zbid=A.guid) AS BXXM,");
			tablename.append(" TO_CHAR(A.CZRQ,'YYYY-MM-DD')CZRQ,");
			tablename.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZBM)AS SZBM,");
			tablename.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.BXR)AS BXR,");
			tablename.append("  (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=A.SHZT)SHZT");
			tablename.append(" from CW_RCBXZB A  LEFT JOIN act_hi_actinst ACT ON A.PROCINSTID = ACT.PROC_INST_ID_ and END_TIME_ is not null  order by a.djbh desc)k where k.SHZT !='未提交' and k.assignee_='"+LUser.getGuid()+"' ) D");
		}
		tablename.append(" ");
		pageList.setKeyId("guid");//主键
		pageList.setTableName(tablename.toString());//表名
		pageList = pageService.findPageList(pd,pageList);//引用传递
		String rybh = LUser.getRybh();// 获取当前登录人员编号
		// 设置WHERE条件
		String strWhere = " ";
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 往来单位
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWldwPageList")
	@ResponseBody
	public Object getWldwPageList() throws Exception {
		
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		String sqltext = "";
		String tableName = "";
		// 设置查询字段名
		sqltext = " t.dwmc,dwdz,t.guid,t.lxr,t.bgdh,t.wlbh";
		tableName = " cw_wldwb t ";
		pageList.setSqlText(sqltext);
		pageList.setKeyId("guid");//主键
		pageList.setTableName(tableName);//表名
		pageList.setStrWhere(" and t.sfdgzf='01' ");
		pageList = pageService.findPageList(pd,pageList);//引用传递
		// 设置WHERE条件
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 借款业务
	 * 
	 */
	@RequestMapping(value = "/getJkywPageList")
	@ResponseBody
	public Object getJkywPageList(){
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		StringBuffer sqltext = new StringBuffer();
		String tableName = "";
		StringBuilder strwhere = new StringBuilder();
		// 设置查询字段名
		sqltext.append(" a.gid as guid, a.jkr as jkr,a.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = a.jkr) as sqr,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = a.szbm) as szbm, ");
		sqltext.append("TO_CHAR(ROUND(A.JKzJE, 2), 'fm999999999999999999990.00') AS JKJE,TO_CHAR(ROUND(A.SYJE, 2), 'fm999999999999999999990.00') AS SYJE,");
		sqltext.append(" '"+LUser.getXm()+"'as jkrxm,'" + LUser.getDwmc() + "'as szdw");
		tableName = " CW_JKYWB a ";
		strwhere.append(" and (a.jkr = '"+LUser.getGuid()+"'");
		strwhere.append(" or a.jkr = '"+LUser.getRybh()+"')");
		strwhere.append(" and SYJE>0 " );
		strwhere.append(" and (shzt='8' or shzt='9') and gid in (select bxid from  cw_pzlrbxdzb where pzid in  (select guid from cw_pzlrzb where pzzt!='00') and bxtype='jkbx' )");
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("gid");//主键
		pageList.setTableName(tableName);//表名
		pageList.setStrWhere(strwhere.toString());
		pageList = pageService.findPageList(pd,pageList);//引用传递
		// 设置WHERE条件
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 编辑页面的跳转
	 * @return
	 */
	@RequestMapping(value = "/operate")
	public ModelAndView GoOperate() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String operate = pd.getString("operate");
		String look = pd.getString("look");
		String zbid = pd.getString("guid");
		String fcwys = pd.getString("fcwys");
		//主表信息
		Map zbMap = rcbxService.getBxzbById(zbid);
		String ymbz = pd.getString("ymbz");
		//项目明细信息
		List xmhxlist = rcbxService.getXmhxList(zbid);
		List fpxxList = rcbxService.getFpxx(zbid);//获取发票信息
		//报销明细信息
		List list = rcbxService.getFyList(zbid);
		//冲销
		List cxList = rcbxService.getCxjkListHx(zbid);
		//对公
		List dgList = rcbxService.getDgList(zbid);
		//对私
		List dsList = rcbxService.getDsList(zbid);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		//零余额
		List lyeList = rcbxService.getLyeList(zbid);
		String[] fjxx = fileService.getFjList(zbid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);		
		mv.addObject("zbMap", zbMap);
		mv.addObject("ymbz", ymbz);
		mv.addObject("xmhxlist",xmhxlist);
		mv.addObject("fpxxList",fpxxList);
		mv.addObject("list", list);
		mv.addObject("cxList", cxList);
		mv.addObject("dgList", dgList);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("lyeList", lyeList);
		String procinstid = pd.getString("procinstid");
		mv.addObject("guid", zbid);
		mv.addObject("procinstid", procinstid);
		String shzt = zbMap.get("shzt")+"";
		if("03".equals(shzt)) {
			fcwys="cwys";
			operate="CWYSCK";
		}
		mv.addObject("fcwys", fcwys);
		String _url = "wsbx/rcbx/rcbx_operate";
		if("U".equals(operate)){
			_url = "wsbx/rcbx/rcbx_update";
		}else if("CK".equals(operate)){
			_url = "wsbx/rcbx/rcbx_check";
		}else if("CWYSCK".equals(operate)){//财务预审
			_url = "wsbx/rcbx/rcbx_cwyscheck";
		}else if("L".equals(operate)){
			mv.addObject("look",look);
			_url = "wsbx/rcbx/rcbx_view";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = sdf.format(new Date());
		List<Map<String, Object>> zffsList = dictService.getDict(Constant.ZFFSDM);//支付方式
		mv.addObject("sysDate", sysDate);
		mv.addObject("zffsList", zffsList);
		mv.setViewName(_url);
		return mv;
	}
	
	/**
	 * 编辑页面的跳转
	 * @return
	 */
	@RequestMapping(value = "/operateByView")
	public ModelAndView operateByView() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String operate = pd.getString("operate");
		String look = pd.getString("look");
		String _url = "wsbx/rcbx/rcbx_operate";
		String zbid = pd.getString("guid");
		//主表信息
		Map zbMap = rcbxService.getBxzbById(zbid);
		//项目明细信息
		List xmhxlist = rcbxService.getXmhxList(zbid);
		List fpxxList = rcbxService.getFpxx(zbid);//获取发票信息
		//报销明细信息
		List list = rcbxService.getFyList(zbid);
		//冲销
		List cxList = rcbxService.getCxjkListHx(zbid);
		//对公
		List dgList = rcbxService.getDgList(zbid);
		//对私
		List dsList = rcbxService.getDsList(zbid);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		//零余额
		List lyeList = rcbxService.getLyeList(zbid);
		String[] fjxx = fileService.getFjList(zbid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);		
		mv.addObject("zbMap", zbMap);
		mv.addObject("xmhxlist",xmhxlist);
		mv.addObject("fpxxList",fpxxList);
		mv.addObject("list", list);
		mv.addObject("cxList", cxList);
		mv.addObject("lyeList", lyeList);
		mv.addObject("dgList", dgList);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		String procinstid = pd.getString("procinstid");
		mv.addObject("guid", zbid);
		mv.addObject("procinstid", procinstid);
		_url = "wsbx/rcbx/view";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = sdf.format(new Date());
		List<Map<String, Object>> zffsList = dictService.getDict(Constant.ZFFSDM);//支付方式
		mv.addObject("sysDate", sysDate);
		mv.addObject("zffsList", zffsList);
		mv.setViewName(_url);
		return mv;
	}
	/**
	 * 窗口
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/window")
	public ModelAndView goWindowPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String _url = "wsbx/rcbx/fyList";
		if("jkyw".equals(controlId)){
			List jkye = rcbxService.getCxjkLists();
			mv.addObject("jkye", jkye);
			_url = "wsbx/rcbx/jkyw_list";
		}else if("hkxx".equals(controlId)){
			_url = "wsbx/rcbx/hkxx_operate";
		}else if("xnzz".equals(controlId)){
			List wldw = rcbxService.getWldwList();
			mv.addObject("id", pd.getString("id"));
			mv.addObject("gid",pd.getString("gid"));
			mv.addObject("wldw", wldw);
			_url = "wsbx/rcbx/xnzz_list";
		}else if("gwkxx".equals(controlId)){
			_url = "wsbx/rcbx/gwk_list";
		}else{
			List list = rcbxService.getFyListBySelect();
			mv.addObject("list", list);
		}
		mv.addObject("controlId", controlId);
		mv.setViewName(_url);
		return mv;
		
	}
	/**
	 * 导出信息Excel
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
		String type = pd.getString("type");
		StringBuffer sqltext = new StringBuffer();
		// 设置查询字段名
		sqltext.append(" SELECT");
		sqltext.append(" A.GUID,");
		sqltext.append(" decode(nvl(A.CJKJE,0),0,'0.00',(to_char(round(A.CJKJE,2),'fm999999999999999999999990.00')))CJKJE,");
		sqltext.append(" decode(nvl(A.HKJE,0),0,'0.00',(to_char(round(A.HKJE,2),'fm99999999999999999999999999990.00')))HKJE,");
		sqltext.append(" decode(nvl(A.LKJE,0),0,'0.00',(to_char(round(A.LKJE,2),'fm999999999999999999999999990.00')))LKJE,");
		sqltext.append(" decode(nvl(A.HJJE,0),0,'0.00',(to_char(round(A.HJJE,2),'fm99999999999999999999999999999990.00')))HJJE,");
		sqltext.append(" A.XMBH,A.PJZZS,");
		sqltext.append(" A.SKDWMC,A.SKR,A.YHZH,A.KHYH,A.FY,");
		sqltext.append(" A.CZR,A.SKDWSZSH,A.SHR,A.SHRQ,");
		sqltext.append(" A.SKDWSZSI,A.SKDWSZQX,A.SHJY,");
		sqltext.append(" TO_CHAR(A.SQRQ,'YYYY-MM-DD')SQRQ,");
		sqltext.append(" TO_CHAR(A.TJRQ,'YYYY-MM-DD')TJRQ,");
		sqltext.append(" TO_CHAR(A.CZRQ,'YYYY-MM-DD')CZRQ,");
		sqltext.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.BMMC)AS BMMC,");
		sqltext.append(" (SELECT RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.RYBH=A.BXR)AS BXR,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZFFSDM+"' AND DM=A.ZFFS)AS ZFFS,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.SHZTDM+"' AND DM=A.SHZT)AS SHZT,");
		sqltext.append(" DECODE(A.SKLX,'0','单位','个人')AS SKLX");
		sqltext.append(" FROM CW_RCBXZB A WHERE 1=1");
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("guid");
		if (Validate.noNull(id)) {
			sqltext.append(" AND A.GUID IN ('" + id.replace(",", "','") + "') ");
		}
		sqltext.append(" ORDER BY A.GUID ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("shzt");
		m.setShowname("状态");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("bxr");
		m.setShowname("报销人");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("bmmc");
		m.setShowname("部门名称");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("xmbh");
		m.setShowname("项目名称");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("cjkje");
		m.setShowname("借款金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("hkje");
		m.setShowname("还款金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("lkje");
		m.setShowname("领款金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("hjje");
		m.setShowname("合计金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("sqrq");
		m.setShowname("申请时间");
		mlist.add(m);
		m = null;
		
		if(Validate.noNull(type)&&"CK".equals(type)){
			m = new M_largedata();
			m.setName("tjrq");
			m.setShowname("提交时间");
			mlist.add(m);
			m = null;
		}
		
		m = new M_largedata();
		m.setName("czrq");
		m.setShowname("报销时间");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zffs");
		m.setShowname("支付方式");
		mlist.add(m);
		m = null;


		// 导出方法
		pageService.ExpExcel(sqltext.toString(), realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}
	
	/**
	 * 
	 */
	@RequestMapping(value="/check")
	public ModelAndView check(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String guid = pd.getString("guid");
		String bxsy = pd.getString("bxsy");
		String fyid = pd.getString("fyid");
		String ymbz = pd.getString("ymbz");
		String xmguid = pd.getString("xmguid");
		String fcwys = pd.getString("fcwys");
		String procinstid = pd.getString("procinstid");
		String flag=Validate.isNullToDefaultString(pd.getString("flag"),"");
		mv.addObject("guid", guid);
		mv.addObject("procinstid", procinstid);
		mv.addObject("flag", flag);
		mv.addObject("bxsy", bxsy);
		mv.addObject("ymbz", ymbz);
		mv.addObject("fyid", fyid);
		mv.addObject("xmguid", xmguid);
		mv.addObject("fcwys", fcwys);
		String url = "wsbx/rcbx/check1";
		String types = "通过";
		if(!"first".equals(type)){
			url = "wsbx/rcbx/check2";
			types = "退回";
		}
		String sfxy = rcbxService.getSfxy(guid);
		mv.addObject("sfxy",sfxy);
		List list = rcbxService.getShyjListByLoginId(types);
		mv.addObject("list", list);
		mv.setViewName(url);
		return mv;
	}

	@RequestMapping(value="/check1")
	public ModelAndView check1(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String url = "wsbx/rcbx/check3";
		String guid = pd.getString("guid");
		String procinstid = pd.getString("procinstid");
		mv.addObject("procinstid", procinstid);
		mv.addObject("guid", guid);
		String types = "通过";
		if(!"first".equals(type)){
			url = "wsbx/rcbx/check4";
			types = "退回";
		}
		List list = rcbxService.getShyjListByLoginId(types);
		mv.addObject("list", list);
		mv.setViewName(url);
		return mv;
	}
	/**
	 * 经济科目设置单位树
	 * 
	 */
	@RequestMapping(value="/JjszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object JjszTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();	
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		if("get-jjkm".equals(menu)){
			if("root".equals(jb)){//
				return rcbxService.getjjkmNodezj(pd,"",rootPath);
			}else{
				
			    //return kmszService.getgnkmNodezj(pd,jb,rootPath);
				return rcbxService.getjjkmNodezj(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 当前登录人被授权的项目
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageListByLoginXm")
	@ResponseBody
	public Object getPageListByLoginXm() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		StringBuffer sql = new StringBuffer();
		String loginId = LUser.getGuid();
		sql.append("*");
		pageList.setSqlText(sql.toString());
		StringBuffer table = new StringBuffer();
		table.append(" ( ");
		table.append(" select distinct a.guid,a.xmbh,a.xmmc,decode(nvl(ye,'0'),'0','0.00',to_char(round(YE,2),'fm9999999999999999999999999900.00'))ye,jzsj, row_number() over(partition by a.guid order by jzsj desc)rw, ");
		table.append(" (SELECT D.MC FROM GX_SYS_DMB D WHERE D.ZL = '251' AND D.DM = A.XMLB) XMLBMC, ");
		table.append(" (select B.mc from gx_sys_dmb B where B.zl = '"+Constant.XMDL+"' and B.dm = A.xmdl) AS XMDLMC, ");
		table.append(" (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=a.fzr)as fzr, ");
		table.append(" a.fzr as rybh,(select  r.xm from gx_sys_ryb r  where r.rybh = a.fzr) as ryxm,bmbh, (select mc from gx_sys_dwb b where b.bmh=a.bmbh)  as bmmc, ");
		table.append(" (SELECT decode(nvl(YSJE,'0'),'0','0.00',to_char(round(YSJE,2),'fm99999999999999999900.00')) FROM CW_XMJBXXB B WHERE B.GUID=A.GUID) AS YSJE,");
		table.append(" DECODE(NVL(zfcgsyje, '0'),'0','0.00',TO_CHAR(ROUND(zfcgsyje, 2),'fm99999999999999999900.00')) zfcgsyje, ");
		table.append(" DECODE(NVL(fzfcgsyje, '0'),'0','0.00',TO_CHAR(ROUND(fzfcgsyje, 2),'fm9999999999999999900.00')) fzfcgsyje, ");
		table.append(" decode(a.jflx,'01','部门经费','个人经费')jflx,a.jflx as jflxdm ");
		table.append(" from  XMINFOS A  where 1=1 and a.ye<>0  ");
		String mkbh = pd.getString("mkbh");
		//凭证的管理费 选择项目
		if("040101".equals(mkbh)){
			table.append(" and a.xmdl='2' ");
		}else{
			table.append(" and ( ");
			table.append("		  (  (bsqr='"+loginId+"' or bsqr = '"+LUser.getRybh()+"')   and a.xmdl='2') ");
			table.append("		  or ( a.xmdl='1' and a.bmbh='"+LUser.getDwbh()+"' ) ");
			table.append("		  or ( a.xmdl='3' and a.bmbh='"+LUser.getDwbh()+"' ) ");
			table.append("		  or ( a.xmdl='4' and a.bmbh='"+LUser.getDwbh()+"' ) ");
			table.append(" ) ");
		}
		table.append(" and  a.guid not in (select nvl(jfbh,'0') from XMINFOS ) ");
		table.append(" ) ");
		// 表名
		pageList.setTableName(table.toString());
		//"where 1=1 and a.ye<>0 and ( ((bsqr='"+loginId+"' or bsqr='"+LUser.getRybh()+"') and jflx = '02') or (jflx='01' and a.bmbh='"+LUser.getDwbh()+"'))"
		//"and ((A.xmdl='"+Constant.XMDL_KYJF+"' and (a.fzr='"+LUser.getRybh()+"' or a.xmbh in (select t.xmbh from CW_XMSQB t where t.bsqr='"+LUser.getRybh()+"') ))"
		//"or (A.xmdl<>'"+Constant.XMDL_KYJF+"' and a.bmbh='"+LUser.getDwbh()+"')))"
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = " and rw=1 ";
		
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 当前登录人被授权的项目
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageListByLoginXmByClbx")
	@ResponseBody
	public Object getPageListByLoginXmByClbx() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		//当前登录人的id
		String loginId = LUser.getGuid();
		sql.append("*");
		/*sql.append(" a.guid,a.xmbh,a.xmmc,decode(nvl(ye,'0'),'0','0.00',to_char(round(YE,2),'fm9999999999999999999999999900.00'))ye,jzsj,");
		sql.append("(SELECT D.MC FROM GX_SYS_DMB D WHERE D.ZL = '251' AND D.DM = X.XMLB) XMLBMC,");
		sql.append(" (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=a.fzr)as fzr,");
		sql.append(" (SELECT decode(nvl(YSJE,'0'),'0','0.00',to_char(round(YSJE,2),'fm9999999999999999999999999999900.00')) FROM CW_XMJBXXB B WHERE B.GUID=A.GUID) AS YSJE,");
		sql.append("  decode(a.jflx,'01','部门经费','个人经费')jflx,a.jflx as jflxdm ");*/
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName(" (select a.guid,a.xmbh,a.xmmc,decode(nvl(ye,'0'),'0','0.00',to_char(round(YE,2),'fm9999999999999999999999999900.00'))ye,jzsj," +
				"(SELECT D.MC FROM GX_SYS_DMB D WHERE D.ZL = '251' AND D.DM = A.XMLB) XMLBMC," +
				"(select B.mc from gx_sys_dmb B where B.zl = '"+Constant.XMDL+"' and B.dm = A.xmdl) AS XMDLMC," +
				"(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=a.fzr)as fzr," +
				"(SELECT decode(nvl(YSJE,'0'),'0','0.00',to_char(round(YSJE,2),'fm9999999999999999999999999999900.00')) FROM CW_XMJBXXB B WHERE B.GUID=A.GUID) AS YSJE," +
				"decode(a.jflx,'01','部门经费','个人经费')jflx,a.jflx as jflxdm " +
				"from  XMINFOS A  " +
				"where 1=1 and a.xmdl='1' and a.ye<>0 and ( ((bsqr='"+loginId+"' or bsqr='"+LUser.getRybh()+"') and jflx = '02') or (jflx='01' and a.bmbh='"+LUser.getDwbh()+"')) and a.guid not in (select nvl(jfbh, '0') from XMINFOS))");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();
//		strWhere += "and a.ye<>0 and ( ((bsqr='"+loginId+"' or bsqr='"+LUser.getRybh()+"') and jflx = '02') or (jflx='01' and a.bmbh='"+LUser.getDwbh()+"'))";
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 跳转费用录入页面
	 * @return
	 */
	@RequestMapping(value="/writeFy")
	public ModelAndView writeFy(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//此处生成申请主表的主键id
		String zbid = Validate.isNullToDefaultString(pd.get("zbid"), GenAutoKey.get32UUID()); 
		String djbh =Validate.isNullToDefaultString(pd.get("djbh"), GenAutoKey.createKeyforth("CW_RCBXZB","RC", "djbh"));
		String look = Validate.isNullToDefaultString(pd.getString("look"), "");
		String bjzt=pd.getString("bjzt");
		mv.addObject("look", look);
		//费用列表
		String mkbh = pd.getString("mkbh");
		String xmguids = pd.getString("xmguid");
		String xmmcs = pd.getString("xmmc");
		String jflxdm = pd.getString("jflxdm");
		String url = "wsbx/rcbx/writeFy";
		List list = rcbxService.getFyList(zbid);
		String moneys = pd.getString("money");
		String zfcgsyje = pd.getString("zfcgsyje");
		String fzfcgsyje = pd.getString("fzfcgsyje");
		//先删除
//		rcbxService.bcxmxxsc(zbid);
		//后新增
		rcbxService.bcxmxx(xmguids,xmmcs,moneys,zbid,zfcgsyje,fzfcgsyje);
		mv.addObject("xmguid", xmguids);
		mv.addObject("money", moneys);
		mv.addObject("list", list);
		mv.addObject("jflxdm",jflxdm);
//		mv.addObject("xmguid", xmguid);
//		mv.addObject("xmmc", xmmc);
		mv.addObject("zbid", zbid);
		mv.addObject("djbh", djbh);
		mv.addObject("mkbh", mkbh);
		mv.setViewName(url);
		return mv;
	}
	/**
	 * 
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSaveBxmx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveBxmx(Rcbxmx bxmx) {
		int result = 0;
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String zbid = this.getPageData().getString("zbid");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		boolean bool = true;
		if (list.size()>0) {
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					 String fymc = Validate.isNullToDefaultString(map.get("fyid"), "");
					 String fjzs = Validate.isNullToDefaultString(map.get("fjzs"), "");
					 String bxje = Validate.isNullToDefaultString(map.get("bxje"), "");
					 String bz = Validate.isNullToDefaultString(map.get("bz"), "");
					 
					 String xmmc = Validate.isNullToDefaultString(map.get("xmmc"), "");
					 String xmguid = Validate.isNullToDefaultString(map.get("xmguid"), "");
					 String syje = Validate.isNullToDefaultString(map.get("syje"), "");
					 String zfcgje = Validate.isNullToDefaultString(map.get("zfcgje"), "");
					 String zfcgsyje = Validate.isNullToDefaultString(map.get("zfcgsyje"), "");
					 String fzfcgje = Validate.isNullToDefaultString(map.get("fzfcgje"), "");
					 String fzfcgsyje = Validate.isNullToDefaultString(map.get("fzfcgsyje"), "");
					 if(Validate.noNull(zbid)){
						 if(bool){
								rcbxService.deleteFymx(zbid);
								bool = false;
							}
						 bxmx.setXmmc(xmmc);
						 bxmx.setXmguid(xmguid);
						 bxmx.setSyje(syje);
						 bxmx.setBxje(bxje);
						 bxmx.setBz(bz);
						 bxmx.setFjzs(fjzs);
						 bxmx.setFymc(fymc);
						 bxmx.setZbid(zbid);
						 bxmx.setZfcgje(zfcgje);
						 bxmx.setZfcgsyje(zfcgsyje);
						 bxmx.setFzfcgje(fzfcgje);
						 bxmx.setFzfcgsyje(fzfcgsyje);
						 result = rcbxService.doAdd(bxmx);
					 }
				}
			if (result > 0) {
				return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else{
			return 0;
		}
	}
	//附表 项目明细 保存
	@RequestMapping(value = "/doSaveXmmx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveXmmx(Rcbxmx bxmx) {
		int result = 0;
		String gxxmguid=this.getPageData().getString("gxxmguid");
		String zbid=this.getPageData().getString("zbid");
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
//		System.err.println("项目json list "+list);
		boolean bool = true;
		if (list.size()>0) {
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					 String xmmc = Validate.isNullToDefaultString(map.get("xmmc"), "");
					 String xmguid = Validate.isNullToDefaultString(map.get("xmguid"), "");
					 String syje = Validate.isNullToDefaultString(map.get("syje"), "");
					 String bcbxje = Validate.isNullToDefaultString(map.get("bcbxje"), "");
//					 String zbid = Validate.isNullToDefaultString(map.get("zbids"), "");
					 if(Validate.noNull(xmguid)&&Validate.noNull(xmmc)){
//						 if(bool){
//								rcbxService.deleteXmmx(zbid);
//								bool = false;
//							}
						 bxmx.setBcxmmc(xmmc);
						 bxmx.setBcxmguid(xmguid);
						 bxmx.setBcsyje(syje);
						 bxmx.setBcbxje(bcbxje);
						 bxmx.setZbid(zbid);
						 result = rcbxService.doAddXmmx(bxmx,zbid);
					 }
				}
			if (result > 0) {
				return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else{
			return 0;
		}
	}
	//发票信息 保存
	@RequestMapping(value = "/doSavefpxx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSavefpxx(Rcbxmx bxmx) {
		int result = 0;
		String zbid=this.getPageData().getString("zbid");
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		boolean bool = true;
		if (list.size()>0) {
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String fpxx1 = Validate.isNullToDefaultString(map.get("fpxx1"),"");
				String fpxx2 = Validate.isNullToDefaultString(map.get("fpxx2"),"");
				String fpxx3 = Validate.isNullToDefaultString(map.get("fpxx3"),"");
				String fpxx4 = Validate.isNullToDefaultString(map.get("fpxx4"),"");
				String fpxx5 = Validate.isNullToDefaultString(map.get("fpxx5"),"");
				if(Validate.noNull(fpxx1)&&Validate.noNull(zbid)){
						 if(bool){
								rcbxService.deleteFpxx(zbid);
								bool = false;
							}
					bxmx.setFpxx1(fpxx1);
					bxmx.setFpxx2(fpxx2);
					bxmx.setFpxx3(fpxx3);
					bxmx.setFpxx4(fpxx4);
					bxmx.setFpxx5(fpxx5);
					bxmx.setZbid(zbid);
					result = rcbxService.doAddFpxx(bxmx,zbid);
				}
			}
			if (result > 0) {
				return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else{
			return 0;
		}
	}
	/**
	 * 跳转日常报销业务办理界面
	 * @return
	 */
	@RequestMapping(value="/ywbl")
	public ModelAndView ywbl(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//此处生成申请主表的主键id
		String zbid = Validate.isNullToDefaultString(pd.get("zbid"), GenAutoKey.get32UUID()); 
		//费用列表
		String sfxz = pd.getString("sfxz");
		String mkbh = pd.getString("mkbh");
		String xmguids = pd.getString("xmguid");
		String jflxdm = pd.getString("jflxdm");
		String xmmcs = pd.getString("xmmc");
		String moneys = pd.getString("money");
		String zfcgsyje = pd.getString("zfcgsyje");
		String fzfcgsyje = pd.getString("fzfcgsyje");
		String bz = pd.getString("bz");
		String flag = pd.getString("flag");//判断是否为科研类型，若1是科研，2非科研
		if(Validate.noNull(bz)){
			rcbxService.bcxmxx(xmguids,xmmcs,moneys,zbid,zfcgsyje,fzfcgsyje);//增加报销项目明细表数据
		}
		String url = "wsbx/rcbx/rcbx_operate";
		List list = rcbxService.getFyList(zbid);
		Map zbxx = rcbxService.getBxzbById(zbid);
		List fpxxList = rcbxService.getFpxx(zbid);//获取发票信息
		//项目回显 list
		List xmhxlist = rcbxService.getXmhxList(zbid);
//		String djbh =Validate.isNullToDefaultString(pd.get("djbh"), GenAutoKey.createKeyforth("CW_RCBXZB","RC", "djbh"));
		mv.addObject("djbh", pd.get("djbh"));
		String[] fjxx = fileService.getFjList(zbid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		String look = Validate.isNullToDefaultString(pd.getString("look"), "");
		mv.addObject("ysf", Constant.JJKM_YSF);
		mv.addObject("wxf", Constant.JJKM_WXF);
		mv.addObject("cgf", Constant.JJKM_CG);
		mv.addObject("xmlist", xmhxlist);
		mv.addObject("flag", flag);
		mv.addObject("look", look);
		mv.addObject("jflxdm",jflxdm);
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		mv.addObject("zbxx", zbxx);
		mv.addObject("login", LUser.getXm());
		mv.addObject("loginBm", LUser.getDwmc());
		mv.addObject("szbm", LUser.getDwbh());//所在部门的单位编号
		mv.addObject("loginId", LUser.getGuid());
		mv.addObject("list", list);
		mv.addObject("fplist", fpxxList);
		mv.addObject("xmguid", xmguids);
		mv.addObject("zbid", zbid);
		mv.addObject("mkbh", mkbh);
		mv.addObject("sfxz", sfxz);
		mv.setViewName(url);
		return mv;
	}
	/**
	 * 保存主表信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(Rcbxzb rcbx) {
		int result = 0;
		result = rcbxService.doAddZb(rcbx);
		if (result > 0) {
			return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	/**
	 * 结算方式
	 * @return
	 */
	@RequestMapping(value="/jsfs")
	public ModelAndView jsfs(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//此处生成申请主表的主键id
		String zbid = Validate.isNullToDefaultString(pd.get("zbid"), GenAutoKey.get32UUID()); 
		//费用列表
		String mkbh = pd.getString("mkbh");
		String sfxz = pd.getString("sfxz");
		String xmguid = pd.getString("xmguid");
		String xmmc = pd.getString("xmmc");
		String bxzje = pd.getString("bxzje");
		String djbh =rcbxService.getDjbh(zbid);
		mv.addObject("djbh", djbh);
		String money = pd.getString("money");
		mv.addObject("money", money);
		 //获取当前登录人的银行卡list 
		List<Map<String,Object>> dlryhklist=gwjdfbxsqService.getdlryhklist();
		mv.addObject("dlryhklist", dlryhklist);
		//将当前登录人guid 和 项目负责人的guid传过去 走ajax获取 银行信息
		String dqdlrguid=LUser.getGuid();
//		String xmfzrguid = rcbxService.getXmFzrguid(xmguid);
		String url = "wsbx/rcbx/jsfs_operate";
		//冲借款
		List cjkList = rcbxService.getCxjkListHx(zbid);
		mv.addObject("cjkList", cjkList);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		//零余额
		List lyeList = rcbxService.getLyeList(zbid);
		//对公支付
		List dgList = rcbxService.getDgList(zbid);
		//对私支付
		List dsList = rcbxService.getDsList(zbid);
		String loginInfo = "("+LUser.getRybh()+")"+LUser.getRyxm();
		String xmfzr = rcbxService.getXmFzr(xmguid);
		//支付方式回显的判断
		Map map = rcbxService.getBxzbById(zbid);
		String look = Validate.isNullToDefaultString(pd.getString("look"), "");
		mv.addObject("look", look);
//		String kyje = pd.getString("kyje");
//		mv.addObject("kyje", kyje);
		String kyje = map.get("bxzje")+"";
		mv.addObject("kyje", kyje);
		mv.addObject("sfxz", sfxz);
		mv.addObject("dgList",dgList);
		mv.addObject("map", map);
		mv.addObject("loginInfo", loginInfo);
		mv.addObject("xmfzr", xmfzr);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("lyeList", lyeList);
		mv.addObject("bxzje", bxzje);
		mv.addObject("xmguid", xmguid);
		mv.addObject("xmmc", xmmc);
		mv.addObject("zbid", zbid);
		mv.addObject("mkbh", mkbh);
		mv.addObject("dqdlrguid", dqdlrguid);//当前登录人guid
//		mv.addObject("xmfzrguid",xmfzrguid);//项目负责人guid
		mv.setViewName(url);
		return mv;
	}
	
	/**
	 * 冲借款
	 * @param cjk
	 * @return
	 */
	@RequestMapping(value = "/addCjk", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public int doSaveCjk(CW_CJKB cjk) {		
		int result = 0;
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		PageData pd = this.getPageData();
		rcbxService.deleteDatas("CW_CJKB", "JKDH", Validate.isNullToDefaultString(pd.getString("zbid"), ""));
		boolean bool = true;
		if (list.size()>0) {
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					 String Jkr = Validate.isNullToDefaultString(map.get("jkr"), "");
					 String Szbm = Validate.isNullToDefaultString(map.get("szbm"), "");
					 String Jkje = Validate.isNullToDefaultString(map.get("jkje"), "");
					 String Cjkje = Validate.isNullToDefaultString(map.get("cjkje"), "");
					 String Bxlx = Validate.isNullToDefaultString(map.get("bxlx"), "");
					 String Jkid = Validate.isNullToDefaultString(map.get("djbh"), "");
					 String zbid = Validate.isNullToDefaultString(map.get("jkdh"), "");
					
					 if(Validate.noNull(Jkid)&&Validate.noNull(Jkr)&&!"0.00".equals(Cjkje)&&Validate.noNull(Cjkje)){
						 if(bool){
								rcbxService.deleteDatas("CW_CJKB", "JKDH", zbid);
								bool = false;
							}
						 	cjk.setJkdh(zbid);  //借款单号
							cjk.setJkr(Jkr);
							cjk.setSzbm(Szbm);
							cjk.setJkje(Jkje);
							cjk.setCjkje(Cjkje);
							cjk.setBxlx(Bxlx);
							cjk.setJkid(Jkid);  //单据编号
							
						 result = rcbxService.doAddCjk(cjk);
					 }
				}
			if (result > 0) {
				return result;
			} else {
				return 0;
			}
		}else{
			return 0;
		}
	}
	/**
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/addGwk", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public int doSaveGwk(CW_GWKB gwk) {
		int result = 0;
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		PageData pd = this.getPageData();
		rcbxService.deleteDatas("CW_GWKB", "Skdh", Validate.isNullToDefaultString(pd.getString("zbid"), ""));
		boolean bool = true;
		if (list.size()>0) {
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					 String Skdh = Validate.isNullToDefaultString(map.get("skdh"), "");
					 String Szbm = Validate.isNullToDefaultString(map.get("szbm"), "");
					 String Ryxm = Validate.isNullToDefaultString(map.get("ryxm"), "");
					 String Skrq = Validate.isNullToDefaultString(map.get("skrq"), "");
					 String Skje = Validate.isNullToDefaultString(map.get("skje"), "");
					 String Sjskje = Validate.isNullToDefaultString(map.get("sjskje"), "");
					 String Kh = Validate.isNullToDefaultString(map.get("kh"), "");
					 String Bxlx = Validate.isNullToDefaultString(map.get("bxlx"), "");
					 String zbid = Validate.isNullToDefaultString(map.get("skdh"), "");
					 if(Validate.noNull(Skdh)&&Validate.noNull(Ryxm)&&!"0.00".equals(Skje)&&Validate.noNull(Skje)){
						 if(bool){
								rcbxService.deleteDatas("CW_GWKB", "Skdh", zbid);
								bool = false;
							}
						 System.err.println("实际刷卡金额="+Sjskje);
						 	gwk.setSkdh(Skdh);
							gwk.setSzbm(Szbm);
							gwk.setRyxm(Ryxm);
							gwk.setSkje(Skje);
							gwk.setSjskje(Sjskje);
							gwk.setSkrq(Skrq);
							gwk.setKh(Kh);
							gwk.setBxlx(Bxlx);
						 result = rcbxService.doAddGwk(gwk);
					 }
				}
			if (result > 0) {
				return result;
			} else {
				return 0;
			}
		}else{
			return 0;
		}
	}
	@RequestMapping(value = "/addLye", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public int doSaveLye(CW_LYEB lye) {
		PageData pd = this.getPageData();
		int result = 0;
		String zbid = Validate.isNullToDefaultString(pd.getString("zbid"), "");
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		rcbxService.deleteDatas("CW_LYEB", "bxid", zbid);
		boolean bool = true;
		if (list.size()>0) {
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					 String hm = Validate.isNullToDefaultString(map.get("hm"), "");
					 String yh = Validate.isNullToDefaultString(map.get("yh"), "");
					 String yhkh = Validate.isNullToDefaultString(map.get("yhkh"), "");
					 String je = Validate.isNullToDefaultString(map.get("je"), "");

					 if(Validate.noNull(hm)&&Validate.noNull(yh)&&Validate.noNull(yhkh)&&!"0.00".equals(je)&&Validate.noNull(je)){
						 if(bool){
								rcbxService.deleteDatas("CW_LYEB", "bxid", zbid);
								bool = false;
							}
						 	lye.setHm(hm);
						 	lye.setYh(yh);
						 	lye.setYhkh(yhkh);
						 	lye.setJe(je);
						 	lye.setBxid(zbid);
						 result = rcbxService.doAddLye(lye);
					 }
				}
			if (result > 0) {
				return result;
			} else {
				return 0;
			}
		}else{
			return 0;
		}
	}
	/**
	 * 对公支付
	 * @param dgzf
	 * @return
	 */
	@RequestMapping(value = "/addDg", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public int doSaveDgzf(CW_DGZFB dgzf) {
		int result = 0;
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		PageData pd = this.getPageData();
		rcbxService.deleteDatas("CW_DGZFB", "Zfdh", Validate.isNullToDefaultString(pd.getString("zbid"), ""));
		boolean bool = true;
		if (list.size()>0) {
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					 String Zfdh = Validate.isNullToDefaultString(map.get("zfdh"), "");
					 String Dfdw = Validate.isNullToDefaultString(map.get("dfdw"), "");
					 String Dfdq = Validate.isNullToDefaultString(map.get("dfdz"), "");
					 System.err.println("dfdq=============================="+Dfdq);
					 String Dfyh = Validate.isNullToDefaultString(map.get("dfyh"), "");
					 String Dfzh = Validate.isNullToDefaultString(map.get("dfzh"), "");
					 String Bxlx = Validate.isNullToDefaultString(map.get("bxlx"), "");
					 String Je = Validate.isNullToDefaultString(map.get("je"), "");
					 String zbid = Validate.isNullToDefaultString(map.get("zfdh"), "");
					 if(Validate.noNull(Zfdh)&&Validate.noNull(Dfdw)&&!"0.00".equals(Je)&&Validate.noNull(Je)){
						 if(bool){
								rcbxService.deleteDatas("CW_DGZFB", "Zfdh", zbid);
								bool = false;
							}
						 	dgzf.setZfdh(Zfdh);
							dgzf.setDfdw(Dfdw);
							dgzf.setDfdq(Dfdq);
							dgzf.setDfyh(Dfyh);
							dgzf.setDfzh(Dfzh);
							dgzf.setJe(Je);
							dgzf.setBxlx(Bxlx);
						 result = rcbxService.doAddDgzf(dgzf);
					 }
				}
			if (result > 0) {
				return result;
			} else {
				return 0;
			}
		}else{
			return 0;
		}
	}
	/***
	 * 是否完全保存
	 * @author 作者：
	 * @version 创建时间:2018-4-20下午2:03:51
	 */
	@RequestMapping("/updatesfwqbc")
	@ResponseBody
	public Object updatesfwqbc() {
		return rcbxService.updatesfwqbc(this.getPageData().getString("zbid"));
	}
	
	@RequestMapping("/checkIsSubmit")
	@ResponseBody
	public Object checkIsSubmit() {
		Map<String,Object> map = rcbxService.checkIsSubmit(this.getPageData().getString("zbid"));
		if(Validate.isNull(map.get("SFWQBC"))) {
			return 1;//oktosubmit
		}else {
			return 0;//no
		}
	}
	
	
	/**
	 * 对私支付
	 * @param dgzf
	 * @return
	 */
	@RequestMapping(value = "/addDs", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public int doSaveDszf(Cw_DSZFB dszf) {
		int result = 0;
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		boolean bool = true;
		PageData pd = this.getPageData();
		rcbxService.deleteDatas("Cw_DSZFB", "Zfdh", Validate.isNullToDefaultString(pd.getString("zbid"), ""));
		if (list.size()>0) {
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					 String Zfdh = Validate.isNullToDefaultString(map.get("zfdh"), "");
					 String Ryxz = Validate.isNullToDefaultString(map.get("ryxz"), "");
					 String Ryxm = Validate.isNullToDefaultString(map.get("ryxm"), "");
					 String Klx = Validate.isNullToDefaultString(map.get("klx"), "");
					 String Dfzh = Validate.isNullToDefaultString(map.get("dfzh"), "");
					 String zhbguid = Validate.isNullToDefaultString(map.get("zhbguid"), "");
					 System.err.println("=============================klx"+Klx);
					 String Bxlx = Validate.isNullToDefaultString(map.get("bxlx"), "");
					 String Je = Validate.isNullToDefaultString(map.get("je"), "");
					 String zbid = Validate.isNullToDefaultString(map.get("zfdh"), "");
					 if(Validate.noNull(Zfdh)&&!"0.00".equals(Je)&&Validate.noNull(Je)){
						 if(bool){
								rcbxService.deleteDatas("Cw_DSZFB", "Zfdh", zbid);
								bool = false;
							}
						 	dszf.setZfdh(Zfdh);
							dszf.setRyxz(Ryxz);
							dszf.setRyxm(Ryxm.substring(1, Ryxm.indexOf(")")));
							dszf.setKlx(Klx);
							dszf.setDfzh(Dfzh);
							dszf.setZhbguid(zhbguid);
							dszf.setJe(Je);
							dszf.setBxlx(Bxlx);
						 result = rcbxService.doAddDszf(dszf);
					 }
				}
			if (result > 0) {
				return result;
			} else {
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	/**
	 * 动态新增冲销借款
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cxjk",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object getCxjkMap(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		Map map = new HashMap<String,String>();
		map = rcbxService.getCxjkMap(guid);
		Gson gson = new Gson();
		String datas = gson.toJson(map);
		return datas;
	}
	
	/**
	 * 往来银行
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/dfyh",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object getDfyhList(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String wlbh = pd.getString("wlbh");
		List list = rcbxService.getWldwYh(wlbh);
		Gson gson = new Gson();
		String datas = gson.toJson(list);
		return datas;
	}
	
	/**
	 * 修改主表的信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/update", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public boolean doUpdate(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		String id = pd.getString("id");
		boolean bool = false;
		if(id.length()==0){
			return false;
		}
		Rcbxzb rczb = new Rcbxzb();
		if(id.contains("cxForm")){
			rczb.setSfcjk("1");
		}
		if(id.contains("dgForm")){
			rczb.setSfdgzf("1");
		}
		if(id.contains("dsForm")){
			rczb.setSfdszf("1");
		}
		if(id.contains("gwForm")){
			rczb.setSfgwk("1");
		}
		if(id.contains("lyeForm")){
			rczb.setSflye("1");
		}
		String zbid = pd.getString("zbid");
		rczb.setGuid(zbid);
		int result = 0;
		result = rcbxService.updateBxzbById(rczb);
		if(result>0){
			return true;
		}
		return bool;
	}
	
	/**
	 * 更新审核
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/updateSh",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public String qyjy(HttpServletRequest request,HttpServletResponse response)
	{
		StringBuffer msg = new StringBuffer();//存放错误信息
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String shr = pd.getString("shr");
		String shyj = pd.getString("shyj");
		String shzt = pd.getString("shzt");
		int result = 0;
		result = rcbxService.updateShById(guid, shzt, shyj, shr);
		if(result>0){
			msg.append("操作成功");
			return "{\"success\":true,\"msg\":\"" + msg.toString() + "\"}";
		}else{
			msg.append("操作失败");
			return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
		}
	}
	
	/**
	 * 跳转详情页面
	 * @return
	 */
	@RequestMapping(value = "/goViewJsp")
	public ModelAndView goViewJsp() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
        String look = pd.getString("look");
		String shztdm = Validate.isNullToDefaultString(pd.getString("shztdm"), "0");
		
		String zbid = pd.getString("guid");
		String mkbh = pd.getString("mkbh");
		//主表信息
		Map zbMap = rcbxService.getBxzbById(zbid);
		//项目明细信息
		List xmhxlist = rcbxService.getXmhxList(zbid);
		//报销明细信息
		List list = rcbxService.getFyList(zbid);
		//冲销
		List cxList = rcbxService.getCxjkListHx(zbid);
		//对公
		List dgList = rcbxService.getDgList(zbid);
		//对私
		List dsList = rcbxService.getDsList(zbid);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		//零余额
		List lyeList = rcbxService.getLyeList(zbid);
		//获取发票信息
		List fpxxList = rcbxService.getFpxx(zbid);
		String[] fjxx = fileService.getFjList(zbid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		mv.addObject("zbMap", zbMap);
		mv.addObject("list", list);
		mv.addObject("cxList", cxList);
		mv.addObject("lyeList", lyeList);
		mv.addObject("xmhxlist", xmhxlist);
		mv.addObject("dgList", dgList);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("mkbh", mkbh);
		mv.addObject("shztdm", shztdm);
		mv.addObject("fpxxList", fpxxList);
		mv.addObject("look",look);
		mv.setViewName("wsbx/rcbx/rcbx_view");
		return mv;
	} 
	
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		int i = rcbxService.deleteZbInfoByGuid(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	
	/**
	 * 日常报销提交
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
				String procinstid = rcbxService.submit(id, "", "1","");
				if(Validate.noNull(procinstid)) {
					i++;
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstid);
					//如果不是审核通过的单据（通过的会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getRcbxDshxxMsg(id);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
		}
		if (i > 0) {
			EchoUtil.batchSendDshxxMsg(msgMap);
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
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
		String procinstid=pd.getString("procinstid");
		String shyj = pd.getString("shyj");
		String guids[] = guid.split(",");
		String procinstids[] = procinstid.split(",");
		String forward = "";
		DshInfoMap msgMap = new DshInfoMap();
		if ("false".equals(pass)) {
			for(int i=0;i<guids.length;i++){
				String id = Validate.isNullToDefaultString(guids[i], "");
				String procinstId = Validate.isNullToDefaultString(procinstids[i], "");
				rcbxService.rejectleaveinfo(shyjb,session, id,procinstId,shyj);
				if(Validate.noNull(procinstId)) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstId);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getRcbxDshxxMsg(id);
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
				rcbxService.approveLeaveInfo( session,shyjb, id,procinstId,shyj);
				if(Validate.noNull(procinstId)) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstId);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getRcbxDshxxMsg(id);
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
	 * 跳转打印页面
	 * @return
	 */
	@RequestMapping(value = "/goPrint")
	public ModelAndView goPrint(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = "'"+pd.getString("guid")+"'";
		String guid1 = pd.getString("guid");
		Map<?,?> list = kylbxService.getSinglePrintInfoByIds(guid);//查询打印数据
		Map yxMap;
		Map bmMap;
		Map kjMap;
		List jjMap;
			 jjMap = rcbxService.getPrintJj(guid1);
			 yxMap = rcbxService.getPrintYx(guid1);
			 System.err.println("____________xm_______"+(String)yxMap.get("XM"));
			 String[] fjxx = fileService.getFjList((String)yxMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView",fjxx[0].replace("'", ""));
			 mv.addObject("fjConfig",fjxx[1]);
			 bmMap = rcbxService.getPrintBm(guid1);
			 String[] fjxx2 = fileService.getFjList((String)bmMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView2",fjxx2[0].replace("'", ""));
			 mv.addObject("fjConfig2",fjxx2[1]);
			 kjMap = rcbxService.getPrintKj(guid1);
			 String[] fjxx3 = fileService.getFjList((String)kjMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView3",fjxx3[0].replace("'", ""));
			 mv.addObject("fjConfig3",fjxx3[1]);
			 int length = jjMap.size()+1;
			 mv.addObject("jjMap",jjMap);
			 mv.addObject("length",length);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(new Date());
		String url = "wsbx/gwjdf/PrintSample441";
		String organizationname=session.getAttribute("organizationname")+"";
		mv.addObject("guid", guid);
		mv.addObject("printinfolist", list);
		mv.addObject("yxMap",yxMap);
		mv.addObject("bmMap",bmMap);
		mv.addObject("kjMap",kjMap);
		mv.addObject("time", time);
		mv.addObject("organizationname", organizationname);
		mv.setViewName(url);
		return mv;
	}
	
	/**
	 * 跳转详情页面
	 * @return
	 */
	@RequestMapping(value = "/goViewJspByMe")
	public ModelAndView goViewJspByMe() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
        String look = pd.getString("look");
		String shztdm = Validate.isNullToDefaultString(pd.getString("shztdm"), "0");
		
		String zbid = pd.getString("guid");
		String mkbh = pd.getString("mkbh");
		//主表信息
		Map zbMap = rcbxService.getBxzbById(zbid);
		//项目明细信息
		List xmhxlist = rcbxService.getXmhxList(zbid);
		//报销明细信息
		List list = rcbxService.getFyList(zbid);
		//冲销
		List cxList = rcbxService.getCxjkListHx(zbid);
		//对公
		List dgList = rcbxService.getDgList(zbid);
		//对私
		List dsList = rcbxService.getDsList(zbid);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		List fpxxList = rcbxService.getFpxx(zbid);//获取发票信息
		String[] fjxx = fileService.getFjList(zbid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		mv.addObject("zbMap", zbMap);
		mv.addObject("xmhxlist",xmhxlist);
		mv.addObject("list", list);
		mv.addObject("cxList", cxList);
		mv.addObject("dgList", dgList);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("mkbh", mkbh);
		mv.addObject("shztdm", shztdm);
		mv.addObject("look",look);
		mv.addObject("fpxxList",fpxxList);
		mv.setViewName("wsbx/wdbx/rcbx_view");
		return mv;
	} 
	//获取联想输入
		@RequestMapping(value="getxx",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getSuggestInfo() {
			PageData pd = this.getPageData();
			return rcbxService.getxx(pd);
		}
		@RequestMapping(value="getdwjc",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getdwjc() {
			PageData pd = this.getPageData();
			String dwmc = pd.getString("dwmc");
			List list = rcbxService.getdwjc(dwmc);
			Gson gson = new Gson();
			String datas = gson.toJson(list);
			return datas;
		}
	//获取 项目信息 回显试用
	@RequestMapping(value="getXmxx")
	@ResponseBody
	public List getXmxx() {
		String zbid=this.getPageData().getString("zbid");
//		List list=new ArrayList<>();
		List xmxxlist=rcbxService.getXmhxList(zbid);
//		list.add(xmxxlist);
		Map map = (Map)xmxxlist.get(0);
		List list = new ArrayList<>();
		list.add(map);
		return list;
	}
	
	/**
	 * 对私支付 银行list ajax
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/dszfyhxx",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object dszfyhxx(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String dqdlrguid = pd.getString("dqdlrguid");
		List list = rcbxService.getdlrYh(dqdlrguid);
		Gson gson = new Gson();
		String datas = gson.toJson(list);
		return datas;
	}
	/**
	 * 对私支付银行账号
	 * @author 作者：Administrator
	 * @version 创建时间:2018-2-10下午4:57:25
	 */
	@RequestMapping(value="/dszfyhzh",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object dszfyhzh(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String zbid = pd.getString("zbid");
		//对私支付
		List dsList = rcbxService.getDsList(zbid);
		Gson gson = new Gson();
		String datas = gson.toJson(dsList);
		return datas;
	}
	/**
	 * 检查发票号 是否有重复的
	 * @author 作者：Administrator
	 * @version 创建时间:2018-2-9下午5:07:05
	 */
	@RequestMapping(value="/checkFpxx",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object checkFpxx(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		List list = rcbxService.checkFpxx();
		Gson gson = new Gson();
		String datas = gson.toJson(list);
		return datas;
	}
	//重写人员弹窗方法
	@RequestMapping(value="/rypage")
	public ModelAndView goRyxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		String zbids = this.getRequest().getParameter("zbid");
		String zbid = pd.getString("zbid");
		mv.addObject("controlId",controlId);
		mv.addObject("zbid",zbid);
		mv.setViewName("wsbx/rcbx/ryxxcxta/window");
		return mv;
	}
	/**
	 * 判断经济科目
	 * @return
	 */
	@RequestMapping(value="/doSelectFyid",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String doSelectFyid(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String fyid = pd.getString("fyid");
		mv.addObject("fyid",fyid);
		Map<?, ?> map = rcbxService.getObjectByIdFyid(fyid);
		String kmgs = String.valueOf(map.get("kmgs")) ;
		return kmgs ;
       
	}
	
	
	
	
	
//从这里开始，西面的代码都是为报账员审核时的编辑用的
	/**
	 * 当前登录人被授权的项目
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageListByBzy")
	@ResponseBody
	public Object getPageListByBzy() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		//报销人
		String bxr = Validate.isNullToDefaultString(pd.getString("bxr"), "");
		if(bxr.indexOf(")")>0){
			bxr = bxr.substring(1,bxr.lastIndexOf(")"));
		}
		sql.append("*");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName(" (select a.guid,a.xmbh,a.xmmc,decode(nvl(ye,'0'),'0','0.00',to_char(round(YE,2),'fm9999999999999999999999999900.00'))ye,jzsj," +
				"(SELECT D.MC FROM GX_SYS_DMB D WHERE D.ZL = '251' AND D.DM = A.XMLB) XMLBMC," +
				"(select B.mc from gx_sys_dmb B where B.zl = '"+Constant.XMDL+"' and B.dm = A.xmdl) AS XMDLMC," +
				"(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=a.fzr)as fzr," +
				"(SELECT decode(nvl(YSJE,'0'),'0','0.00',to_char(round(YSJE,2),'fm9999999999999999999999999999900.00')) FROM CW_XMJBXXB B WHERE B.GUID=A.GUID) AS YSJE," +
				"decode(a.jflx,'01','部门经费','个人经费')jflx,a.jflx as jflxdm " +
				"from  XMINFOS A  " +
				"where 1=1 and a.ye<>0 and ( (bsqr='"+bxr+"'and jflx = '02') or (jflx='01' and a.bmbh='"+LUser.getDwbh()+"')))");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 报账员审核时的业务办理
	 * @return
	 */
	@RequestMapping(value="/ywblBybzy")
	public ModelAndView ywblBybzy(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//此处生成申请主表的主键id
		String zbid = Validate.isNullToDefaultString(pd.get("zbid"), GenAutoKey.get32UUID()); 
		//费用列表
		String xmguids = Validate.isNullToDefaultString(pd.getString("xmguid"), rcbxService.getXmGuidByZbId(zbid));
		String xmmcs = pd.getString("xmmc");
		String moneys = pd.getString("money");
		String bz = pd.getString("bz");
		String url = "wsbx/rcbx/bzy/rcbx_operate";
		List list = rcbxService.getFyList(zbid);
		Map zbxx = rcbxService.getBxzbById(zbid);
		List fpxxList = rcbxService.getFpxx(zbid);//获取发票信息
		//项目回显 list
		List xmhxlist = rcbxService.getXmhxList(zbid);
		String[] fjxx = fileService.getFjList(zbid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		String look = Validate.isNullToDefaultString(pd.getString("look"), "");
		mv.addObject("xmlist", xmhxlist);
		mv.addObject("look", look);
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		mv.addObject("zbxx", zbxx);
		mv.addObject("login", LUser.getXm());
		mv.addObject("loginBm", LUser.getDwmc());
		mv.addObject("loginId", LUser.getGuid());
		mv.addObject("list", list);
		mv.addObject("fplist", fpxxList);
		mv.addObject("xmguid", xmguids);
		mv.addObject("zbid", zbid);
		mv.setViewName(url);
		return mv;
	}
	
	/**
	 * 报账员审核时的结算方式
	 * @return
	 */
	@RequestMapping(value="/jsfsByBzy")
	public ModelAndView jsfsByBzy(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//此处生成申请主表的主键id
		String zbid = Validate.isNullToDefaultString(pd.get("zbid"), GenAutoKey.get32UUID()); 
		//费用列表
		String xmguid = Validate.isNullToDefaultString(pd.getString("xmguid"), "");
		String xmmc = pd.getString("xmmc");
		String bxzje = pd.getString("bxzje");
		String xmdl = CommonUtil.getXmDlBYGuid(xmguid);
		String djbh =Validate.isNullToDefaultString(pd.get("djbh"), GenAutoKey.createKeyforth("CW_RCBXZB","RC", "djbh"));
		mv.addObject("djbh", djbh);
		String money = pd.getString("money");
		mv.addObject("money", money);
		 //获取当前登录人的银行卡list 
		List<Map<String,Object>> dlryhklist=gwjdfbxsqService.getdlryhklist();
		mv.addObject("dlryhklist", dlryhklist);
		//将当前登录人guid 和 项目负责人的guid传过去 走ajax获取 银行信息
		String dqdlrguid = CommonUtil.getRyGuidByXm(pd.getString("bxr"));
//		String xmfzrguid = rcbxService.getXmFzrguid(xmguid);
		String url = "wsbx/rcbx/bzy/jsfs_operate";
		//冲借款
		List cjkList = rcbxService.getCxjkListHx(zbid);
		mv.addObject("cjkList", cjkList);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		//零余额
		List lyeList = rcbxService.getLyeList(zbid);
		//对公支付
		List dgList = rcbxService.getDgList(zbid);
		//对私支付
		List dsList = rcbxService.getDsList(zbid);
		
		String loginInfo = "("+LUser.getRybh()+")"+LUser.getRyxm();
		String xmfzr = rcbxService.getXmFzr(xmguid);
		//支付方式回显的判断
		Map map = rcbxService.getBxzbById(zbid);
		String look = Validate.isNullToDefaultString(pd.getString("look"), "");
		mv.addObject("look", look);
		mv.addObject("dgList",dgList);
		mv.addObject("map", map);
		mv.addObject("loginInfo", loginInfo);
		mv.addObject("xmfzr", xmfzr);
		mv.addObject("dsList", dsList);
		mv.addObject("lyeList", lyeList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("bxzje", bxzje);
		mv.addObject("xmguid", xmguid);
		mv.addObject("xmmc", xmmc);
		mv.addObject("zbid", zbid);
		mv.addObject("dqdlrguid", dqdlrguid);//当前登录人guid
		mv.addObject("xmdl",xmdl);//项目大类
		mv.setViewName(url);
		return mv;
	}
	/**
	 * 获取 项目的 可用 金额
	 */
	@RequestMapping(value="/getXmkyje",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object getXmkyje(HttpSession session){
		PageData pd = this.getPageData();
		String xmguid = pd.getString("xmguid");
		BigDecimal kyje = CommonUtil.getXmkyje(xmguid,session);
		return "{\"kyje\":\""+kyje+"\"}";
	}
	/**
	 * 获取 项目的 可用 金额
	 * 该可用金额不加借款
	 */
	@RequestMapping(value="/getXmkyje_2",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object getXmkyje_2(HttpSession session){
		PageData pd = this.getPageData();
		String xmguid = pd.getString("xmguid");
		BigDecimal kyje = CommonUtil.getXmkyje_2(xmguid,session);
		return "{\"kyje\":\""+kyje+"\"}";
	}
} 
