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
import org.springframework.jdbc.core.JdbcTemplate;
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
import com.googosoft.pojo.wsbx.CW_CLFBXMXB;
import com.googosoft.pojo.wsbx.CW_CLFBXZB;
import com.googosoft.pojo.wsbx.CW_FPXXB;
import com.googosoft.pojo.wsbx.CW_TXRYXXB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.base.ProcessService;
import com.googosoft.service.base.ProvincesService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.kylbx.KylbxService;
import com.googosoft.service.wsbx.CcbxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.ccyw.CcywsqService;
import com.googosoft.service.wsbx.gwjdfbx.GwjdfbxsqService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.SpringBeanFactoryUtils;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;

@Controller
@RequestMapping(value = "/wsbx/ccbx")
public class CcbxController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;
	@Resource(name = "ccywsqService")
	private CcywsqService ccywsqService;
	@Resource(name = "dictService")
	private DictService dictService;

	@Resource(name = "ccbxService")
	private CcbxService ccbxService;
	
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	@Resource(name="fileService")
	private FileService fileService;//单例
	@Autowired
	private EchoService echoService;
	
	@Resource(name="gwjdfbxsqService")
	GwjdfbxsqService gwjdfbxsqService;
	
	@Resource(name="provincesService")
	private ProvincesService provincesService;
	@Resource(name="kylbxService")
	private KylbxService kylbxService;//单例
	@Resource(name = "processService")
	private ProcessService processService;
	/**
	 * 编辑页面的跳转
	 * @return
	 */
	@RequestMapping(value = "/operate")
	public ModelAndView GoOperate() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String operate = pd.getString("operate");
		String _url = "wsbx/ccbx/ccbx_operate";
		if("U".equals(operate)){
			_url = "wsbx/ccbx/ccbx_update";
		}else if("CK".equals(operate)){
			_url = "wsbx/ccbx/ccbx_check";
		}else if("L".equals(operate)){
			_url = "wsbx/ccbx/ccbx_view";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = sdf.format(new Date());
		List<Map<String, Object>> zffsList = dictService.getDict(Constant.ZFFSDM);//支付方式
		mv.addObject("sysDate", sysDate);
		mv.addObject("zffsList", zffsList);
		mv.setViewName(_url);
		return mv;
	}
	/*
	 * 
	 */
	@RequestMapping(value="/check")
	public ModelAndView check(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String guid = pd.getString("guid");
		String ccywguid=pd.getString("ccywguid");
		String procinstid = pd.getString("procinstid");
		mv.addObject("guid", guid);
		mv.addObject("procinstid", procinstid);
		mv.addObject("ccywguid",ccywguid);
		String url = "wsbx/ccbx/check1";
		String types = "通过";
		if(!"first".equals(type)){
			url = "wsbx/ccbx/check2";
			types = "退回";
		}
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
		String guid = pd.getString("guid");
		String procinstid = pd.getString("procinstid");
		mv.addObject("guid", guid);
		mv.addObject("procinstid", procinstid);
		String url = "wsbx/ccbx/check3";
		String types = "通过";
		if(!"first".equals(type)){
			url = "wsbx/ccbx/check4";
			types = "退回";
		}
		List list = rcbxService.getShyjListByLoginId(types);
		mv.addObject("list", list);
		mv.setViewName(url);
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
		String status = Validate.isNullToDefaultString(pd.getString("status"), "0");
		StringBuffer sql = new StringBuffer();
		if(Validate.noNull(status)&&"0".equals(status)){
			sql.append(" select * from (select 'N' as flag,K.* from(select distinct A.DJBH,A.CCYWGUID,A.CCTS,A.CCRS,A.GUID,A.XMMC AS XMGUID,A.SHZT AS SHZTDM,A.FJZZS,(SELECT decode(x.xmsx,'01','部门经费','02','个人经费','') FROM cw_xmjbxxb x WHERE x.guid=a.xmmc) AS JFLX,a.PROCINSTID as PROCINSTID,");
			sql.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='cclx' AND DM=A.CCLX)AS CCLX,act.assignee_,");
			sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm999999999999999999999999990.00')))BXZJE,");
			sql.append(" A.CZRQ,a.shzt as sh,");
			sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,");
			sql.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.SQR)AS BXR,a.SQR,");
			sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=A.SHZT)SHZT from cw_clfbxzb A  LEFT JOIN ACT_RU_TASK ACT ON A.PROCINSTID = ACT.PROC_INST_ID_  where a.checkshzt<>'00')K where K.SHZT !='未提交' and K.assignee_='"+LUser.getGuid()+"') B ");
		}else{
			sql.append(" select * from (select 'Y' as flag,K.* from(select distinct(A.GUID)guid,A.CCYWGUID,A.CCTS,A.CCRS,A.DJBH,A.XMMC AS XMGUID,A.SHZT AS SHZTDM,A.FJZZS,(SELECT decode(x.xmsx,'01','部门经费','02','个人经费','') FROM cw_xmjbxxb x WHERE x.guid=a.xmmc) AS JFLX,a.PROCINSTID as PROCINSTID,");
			sql.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='cclx' AND DM=A.CCLX)AS CCLX,act.assignee_,");
			sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm99999999999999999999999990.00')))BXZJE,");
			sql.append(" A.CZRQ,a.shzt as sh,");
			sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,");
			sql.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.SQR)AS BXR,a.SQR,");
			sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=A.SHZT)SHZT from cw_clfbxzb A  LEFT JOIN act_hi_actinst ACT ON A.PROCINSTID = ACT.PROC_INST_ID_ and END_TIME_ is not null )K where K.SHZT !='未提交' and K.assignee_='"+LUser.getGuid()+"') B ");
		}
		// 设置查询字段名
//		sql.append("select * from (select distinct A.DJBH,A.GUID,a.shzt as sh,A.XMMC AS XMGUID,A.FJZZS,(SELECT MC FROM GX_SYS_DMB WHERE ZL='11033' AND DM=A.SHZT) AS SHZTDM,");
//		sql.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='jflx' AND DM=A.JFLX) AS JFLX,(SELECT MC FROM GX_SYS_DMB WHERE ZL='cclx' AND DM=A.CCLX)AS CCLX,");
//		sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm99999999999990.00')))BXZJE,");
//		sql.append(" A.CZRQ,");
//		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,");
//		sql.append(" (SELECT RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.SQR)AS BXR,a.SQR,");
//		sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=A.SHZT)SHZT from cw_clfbxzb A) B where 1=1");
		sql.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("guid");
		if (Validate.noNull(id)) {
			sql.append(" WHERE B.GUID IN ('" + id.replace(",", "','") + "') ");
		}
		sql.append(" ORDER BY CCTS desc");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("shzt");
		m.setShowname("审核状态");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("djbh");
		m.setShowname("单据编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bxr");
		m.setShowname("报销人");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("szbm");
		m.setShowname("所在部门");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("cclx");
		m.setShowname("出差类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ccts");
		m.setShowname("出差天数");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ccrs");
		m.setShowname("出差人数");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("czrq");
		m.setShowname("报销日期");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("bxzje");
		m.setShowname("报销总金额");
		mlist.add(m);
		m = null;
		
		// 导出方法
		pageService.ExpExcel(sql.toString(), realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
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
			_url = "wsbx/rcbx/jkyw_list";
		}else if("hkxx".equals(controlId)){
			_url = "wsbx/rcbx/hkxx_operate";
		}else if("xnzz".equals(controlId)){
			_url = "wsbx/rcbx/xnzz_list";
		}else if("gwkxx".equals(controlId)){
			_url = "wsbx/rcbx/gwk_list";
		}else if("xmfzr".equals(controlId)){//对私支付选择项目负责人
			String ccywguid = pd.getString("ccywguid");
			String id = pd.getString("id");
			String flag = pd.getString("flag");
			_url = "wsbx/ccbx/xmfzr_list";
			mv.addObject("flag",flag);
			mv.addObject("ccywguid", ccywguid);
			mv.addObject("id", id);
		}
		mv.addObject("controlId", controlId);
		mv.setViewName(_url);
		return mv;
		
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
		String sqltext = "";
		StringBuffer tableName = new StringBuffer();
		// 设置查询字段名
		sqltext = " guid,ryxm,rybh,xb,XMMC ";
		tableName.append(" (select (select guid from gx_sys_ryb where rybh = s.fzr) guid,(select xm from gx_sys_ryb where rybh = s.fzr) ryxm,(select rybh from gx_sys_ryb where rybh = s.fzr) rybh,   ");
		tableName.append(" (select  decode(xb,1,'男',2,'女','未知') from gx_sys_ryb where rybh = s.fzr) xb,S.XMMC ");
		tableName.append("  from XMINFOS s left join CW_CCSQSPB_XM x on x.jfbh = s.guid where x.ccsqid = '"+ccywguid+"' ) b ");
		pageList.setSqlText(sqltext);
		pageList.setKeyId(" b.guid ");//主键
		pageList.setTableName(tableName.toString());//表名
		pageList = pageService.findPageList(pd,pageList);//引用传递
		// 设置WHERE条件
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/*
	 * 新阶段
	 * 
	 * */
	
	/**
	 * 当前登录人被授权的项目
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageListByLoginXm")
	@ResponseBody
	public Object getPageListByLoginXm() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		//当前登录人的id
		String loginId = LUser.getGuid();
		sql.append(" guid,xmbh, xmmc,(select '('||G.RYGH||')'||G.XM from gx_sys_ryb G where G.RYGH=A.FZR),jzsj,decode(nvl(ye,'0'),'0','0.00',to_char(round(ye,2),'fm999999999999999999999999900.00'))ye,fzr,jflx,"
				+ "(SELECT XMLX FROM CW_XMJBXXB B WHERE B.GUID=A.GUID) AS XMLX,(SELECT decode(nvl(YSJE,'0'),'0','0.00',to_char(round(YSJE,2),'fm999999999999999999999900.00')) FROM CW_XMJBXXB B WHERE B.GUID=A.GUID) AS YSJE");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("XMINFOS A");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();
		//strWhere += " and (bsqr='"+loginId+"' or fzr='"+loginId+"' or bsqr='"+LUser.getRybh()+"' or fzr='"+LUser.getRybh()+"') or(jflx='01' and bmbh='"+LUser.getDwbh()+"')";
		strWhere += " and ((bsqr='"+loginId+"' or bsqr='"+LUser.getRybh()+"') and jflx = '02') or (jflx='01' and bmbh='"+LUser.getDwbh()+"')";
		String xmguid = Validate.isNullToDefaultString(pd.getString("xmguid"), "");
		if(Validate.noNull(xmguid)){
			strWhere += " and a.guid='"+xmguid+"'";
		}
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
	 * 跳转业务办理
	 * @return
	 */
	@RequestMapping(value="/ywbl")
	public ModelAndView ywbl(HttpSession session){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//此处生成申请主表的主键id
		String zbid = Validate.isNullToDefaultString(pd.get("zbid"), GenAutoKey.get32UUID()); 
		String mkbh = pd.getString("mkbh");
		String xmbh = pd.getString("xmbh");
		String xmmc = pd.getString("xmmc");
		String xmguid=pd.getString("Xmguid");
		String ccywguid=pd.getString("ccywguid");
		String zjxzxmguid=pd.getString("zjxzxmguid");
		String flag=pd.getString("flag");
		//查询 项目信息 
		List xmlist = new ArrayList<>();
		if("zjxzxm".equals(flag)){//直接选择项目
			xmlist=ccbxService.getxmxxlistZjxz(zjxzxmguid);
		}else{
			xmlist=ccbxService.getxmxxlist(ccywguid);
		}
		mv.addObject("xmlist", xmlist);
		List<Map<String, Object>> ccywList = ccbxService.getCcywListByguid(ccywguid);
		String jflx=pd.getString("jflx");
		List<Map<String, Object>> cclx = dictService.getDict("cclx");
		List<Map<String, Object>> jflxlist = dictService.getDict("jflx");
		String jflxs = ccbxService.getJflxByGuid(xmguid);
		mv.addObject("jflxs",jflxs);
		//查询可用金额
		BigDecimal kyje;
		String xmid =(String) Validate.isNullToDefault(xmguid, zjxzxmguid);
		if(Validate.isNull(xmid)) {
			String sqspXmguid = ccbxService.getSqspXmguid(ccywguid);//若走事前审批单子，根据出差事前审批单号，查项目编号。
			kyje = CommonUtil.getXmkyje_2(sqspXmguid, session);
		}else {
			kyje = CommonUtil.getXmkyje_2(xmid, session);
		}
		mv.addObject("kyje",kyje);
		String url = "wsbx/ccbx/ccbx_operate";
		String xmmcStr= "("+xmbh+")"+xmmc;
		mv.addObject("money", pd.get("money"));
		Map map =new HashMap();
		map.put("xmbh", xmbh);
		map.put("xmmc", xmmcStr);
		map.put("guid", zbid);
		map.put("xmguid", xmguid);
		//map.put("djbh", djbh);
		map.put("jflx", jflx);
		map.put("sqr", LUser.getXm());
		map.put("bmm",LUser.getDwmc());
		map.put("ccts", pd.getString("ccts"));
		map.put("ccrs", pd.getString("ccrs"));
		map.put("ccsy", ccbxService.getccsyById(ccywguid));//出差事由
		mv.addObject("login", LUser.getRyxm());
		mv.addObject("loginBm", LUser.getDwmc());
		mv.addObject("loginId", LUser.getGuid());
		mv.addObject("clfbxzb", map);
		mv.addObject("mkbh", mkbh);
		mv.addObject("cclxList",cclx);
		mv.addObject("jflxList",jflxlist);
		mv.addObject("mxList",ccywList);
		mv.addObject("ccywguid", ccywguid);
		mv.addObject("operate", "C");
		mv.addObject("ProvicesList",provincesService.getProvicesList());
		mv.setViewName(url);
		return mv;
	}
	
	/**
	 * 保存主表信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/doSaveZb", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveZb(CW_CLFBXZB clfbxzb) {
		int result = 0;
		JdbcTemplate jdbcTemplate=(JdbcTemplate) SpringBeanFactoryUtils.getBean("jdbcTemplate");
		PageData pd = this.getPageData();
		String xmmc = pd.getString("xmguid");
	    String ccywguid = pd.getString("ccywguid");
		clfbxzb.setSqr(LUser.getGuid());
		clfbxzb.setXmmc(xmmc);
		clfbxzb.setCcywguid(ccywguid);
		String zbguid=clfbxzb.getGuid();
		String type = pd.getString("type");
		String sql = "SELECT sfxy FROM  GX_SYS_DWB WHERE dwbh='"+LUser.getDwbh()+"'";
		Map<String,Object> map2=jdbcTemplate.queryForMap(sql, new Object[]{});
		clfbxzb.setSfxy(Validate.isNullToDefaultString(map2.get("sfxy"), "0"));
		if (true) {
			Map map =new HashMap();
			map=ccbxService.getBxzbById(zbguid);
//			if(map.isEmpty()||Validate.isNull(map)||map.size()==0){
//			 ccbxService.deleteZb(zbguid);
//			 result = ccbxService.doAddZb(clfbxzb);
//			}else{
//				result=	ccbxService.doUpdateZb(clfbxzb);
//			}
			if("U".equals(type)) {
				result=	ccbxService.doUpdateZb(clfbxzb);
			}else {
				result = ccbxService.doAddZb(clfbxzb);
			}
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		return "";
	}
	/**
	 * 保存明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doSaveFph",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSaveFph(CW_FPXXB cw_fpxxb)throws Exception{
		PageData pd = this.getPageData();
		String json = pd.getString("json");	//得到前台的json
		String zbguid=pd.getString("zbguid");
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		ccbxService.deleteFphByZbguid(zbguid);
		for (int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			String fpha = (String) map.get("fpha");
			String fphb = (String) map.get("fphb");
			String fphc = (String) map.get("fphc");
			String fphd = (String) map.get("fphd");
			String fphe = (String) map.get("fphe");
			cw_fpxxb.setZbid(zbguid);
			cw_fpxxb.setFph1(fpha);
			cw_fpxxb.setFph2(fphb);
			cw_fpxxb.setFph3(fphc);
			cw_fpxxb.setFph4(fphd);
			cw_fpxxb.setFph5(fphe);
			ccbxService.doAddFph(cw_fpxxb); 
		}
		return "success";
	}
	/**
	 * 保存明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doSaveMx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSaveMx(CW_CLFBXMXB clfbxmxb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String djbh=pd.getString("djbh");
		String zbguid=pd.getString("zbguid");
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		ccbxService.deleteMxByZbguid(zbguid);
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String rylx = (String) map.get("rylx");
			String xm = (String) map.get("xm");
			String bxjb = (String) map.get("bxjb");
			String kssj = (String) map.get("kssj");
			String jssj = (String) map.get("jssj");
			String cfdd = (String) map.get("cfdd");
			String mddd = (String) map.get("mddd");
			String fjzs = (String) map.get("fjzs");
			String hjje = (String) map.get("hjje");
			String fjp = (String) map.get("fjp");
			String hcp = (String) map.get("hcp");
			String czcp = (String) map.get("czcp");
			String qcp = (String) map.get("qcp");
			String qtfy = (String) map.get("qtfy");
			String hyfy = (String) map.get("hyfy");
			String pxfy = (String) map.get("pxfy");
			String jsshbzje = (String) map.get("jsshbzje");
			String xsshbzje = (String) map.get("xsshbzje");
			String zsfje = (String) map.get("zsfje");
			String snjtbzf = (String) map.get("snjtbzf");
			String jsbzbz = (String) map.get("jsbzbz");
			String snjtbzbz = (String) map.get("snjtbzbz");
			String zsfbzbz = (String) map.get("zsfbz");
			String provinceid = (String) map.get("provinceid");
			String cityid = (String) map.get("cityid");
			clfbxmxb.setZbguid(zbguid);
	        clfbxmxb.setRylx(rylx);
	        clfbxmxb.setXm(xm);
	        clfbxmxb.setBxjb(bxjb);
	        clfbxmxb.setKssj(kssj);
	        clfbxmxb.setJssj(jssj);
	        clfbxmxb.setCfdd(cfdd);
	        clfbxmxb.setMddd(mddd);
	        clfbxmxb.setFjzs(fjzs);
	        clfbxmxb.setHjje(hjje);
	        clfbxmxb.setFjp(fjp);
	        clfbxmxb.setHcp(hcp);
	        clfbxmxb.setCzcp(czcp);
	        clfbxmxb.setQcp(qcp);
	        clfbxmxb.setQtfy(qtfy);
	        clfbxmxb.setHyfy(hyfy);
	        clfbxmxb.setPxfy(pxfy);
	        clfbxmxb.setJsshbzje(jsshbzje);
	        clfbxmxb.setXsshbzje(xsshbzje);
	        clfbxmxb.setZsfje(zsfje);
	        clfbxmxb.setSnjtbzf(snjtbzf);
	        clfbxmxb.setJsbzbz(jsbzbz);
	        clfbxmxb.setSnjtbzbz(snjtbzbz);
	        clfbxmxb.setZsfbzbz(zsfbzbz);
	        clfbxmxb.setProvinceid(provinceid);
	        clfbxmxb.setCityid(cityid);
	        clfbxmxb.setXh(i+"");
			//增加
			ccbxService.doMxAdd(clfbxmxb); 
		}
		b="success";
		return b;
	}
	/**
	 * 保存项目 明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doSaveXmMx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSaveXmMx(CW_CLFBXMXB clfbxmxb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ccywguid=pd.getString("ccywguid");
		String zbguid=pd.getString("zbguid");
//		if(Validate.isNullOrEmpty(ccywguid)){//如果没有选择事前审批，手动向项目表中添加一个id
//			String newccywguid = UuidUtil.get32UUID();
//			ccbxService.addccywguid(newccywguid);
//			ccywguid = newccywguid;
//		}
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		System.err.println("项目明细 list json "+list);
		ccbxService.deleteClvxmmxb(ccywguid);
		String newccywguid = UuidUtil.get32UUID();
		String xmid = pd.getString("xmguid");
		if(Validate.isNullOrEmpty(ccywguid)){//如果没有选择事前审批，手动向cw_clfbxzb表中添加ccywguid
			ccbxService.updateCcywguid(zbguid,newccywguid,xmid);
		}
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String bcbxje = (String) map.get("bcbxje");
			String xmguid = (String) map.get("xmguid");
			if(Validate.isNullOrEmpty(ccywguid)){//如果没有选择事前审批，循环向项目表中添加选中项目
				ccbxService.addccywguid(newccywguid);
//				ccywguid = newccywguid;
				if(Validate.noNull(bcbxje)){
					ccbxService.doxmxxgx(newccywguid,bcbxje,xmguid,false); 
				}
			}else if(Validate.noNull(bcbxje)){
					ccbxService.doxmxxgx(ccywguid,bcbxje,xmguid,true); 
			 }
		}
		b="success";
		return b;
	}
	/**
	 * 保存明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doSaveRy",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSaveRy()throws Exception{
		PageData pd = this.getPageData();
		CW_TXRYXXB txryxxb = null;
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String zbguid=pd.getString("zbguid");
		System.out.println("人员获取zbguid："+zbguid);
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		ccbxService.deleteRyByZbguid(zbguid);
		for (i=0;i<list.size();i++) {
			txryxxb = new CW_TXRYXXB();
			Map map = (Map) list.get(i);//将list转为map
			//String guid = this.get32UUID();//创建主键
			String rybh = (String) map.get("rybh");
			String ryxm = (String) map.get("ryxm");
			String szbh = (String) map.get("szdw");
			txryxxb.setRybh(rybh);
			txryxxb.setRyxm(ryxm);
			txryxxb.setDwmc(szbh);
			
//			 if(Validate.noNull(rybh)&&Validate.noNull(szbh)){
//					if(Validate.noNull(rybh)&&rybh.indexOf(")")>0){
//						rybh=ccbxService.selectRyGuidByRybh(rybh.substring(1,rybh.indexOf(")")));
//						txryxxb.setRybh(rybh);
//					}
//			if(Validate.noNull(szbh)&&szbh.indexOf(")")>0){
//				txryxxb.setSzdw(szbh.substring(1,szbh.indexOf(")")));
//			}
			txryxxb.setTxbh(zbguid);
		    //增加
			ccbxService.doRyAdd(txryxxb);			 
//			 }
			
			
			
		}
		b="success";
		return b;
	}
	
	/**
	 * 跳转明细页面
	 * @return
	 */
	@RequestMapping(value="/goYwblEditPage")
	public ModelAndView goEditPage(HttpSession session){
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		String zbguid = this.getPageData().getString("zbguid");
		String sfbj = this.getPageData().getString("sfbj");	
		String look = this.getPageData().getString("look");
		String Xmguid = this.getPageData().getString("Xmguid");
		String ccywguid = this.getPageData().getString("ccywguid");
		//查询 项目信息 
		List xmlist=ccbxService.getxmxxlist(ccywguid);
		mv.addObject("xmlist", xmlist);
		List<Map<String, Object>> ccywList= null;
		JdbcTemplate jdbcTemplate=(JdbcTemplate) SpringBeanFactoryUtils.getBean("jdbcTemplate");
		PageData pd = this.getPageData();	
		Map map = ccbxService.getZbPage(zbguid);
		List<Map<String, Object>> fpList = ccbxService.getFpListByZbguid(zbguid);
		List<Map<String, Object>> mxList = ccbxService.getMxListByZbguid(zbguid);
		List<Map<String, Object>> ryList = ccbxService.getRyListByZbguid(zbguid);	
		String sql = "SELECT * FROM  CW_CLFBXZB WHERE CCYWGUID in('"+ccywguid+"')";
		Map<String,Object> map2=jdbcTemplate.queryForMap(sql, new Object[]{});
		if(map2.isEmpty()||Validate.isNull(map2)||map2.size()==0){
			 ccywList = ccbxService.getCcywListByguid(ccywguid);	
		}
		//查询可用金额
		BigDecimal kyje = CommonUtil.getXmkyje(Xmguid, session);
		mv.addObject("kyje",kyje);
		mv.addObject("clfbxzb",map);
		mv.addObject("sfbj",sfbj);
		mv.addObject("fpList",fpList);
		mv.addObject("mxList",mxList);
		mv.addObject("ryList",ryList);	
		String[] fjxx = fileService.getFjList(map.get("GUID")+"","",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		List<Map<String, Object>> cclx = dictService.getDict("cclx");
		List<Map<String, Object>> jflxlist = dictService.getDict("jflx");
		mv.addObject("cclxList",cclx);
		mv.addObject("money", pd.get("money"));
		mv.addObject("jflxList",jflxlist);
		mv.addObject("ccywList",ccywList);
		mv.addObject("ccywguid", ccywguid);
		mv.addObject("operateType", operateType);
		mv.addObject("look",look);
		String jflxs = ccbxService.getJflxByGuid(Xmguid);
		mv.addObject("jflxs",jflxs);
		mv.addObject("xmguid",Xmguid);
		mv.addObject("operate", "U");
		mv.addObject("zbguid", "zbguid");
		mv.addObject("ProvicesList",provincesService.getProvicesList());
		mv.setViewName("wsbx/ccbx/ccbx_operate");
		return mv;

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
		String zbid = Validate.isNullToDefaultString(pd.get("zbguid"), GenAutoKey.get32UUID()); 
		String ccywguid = Validate.isNullToDefaultString(pd.getString("ccywguid"), ccbxService.getCcywguidByZbid(zbid));
		//费用列表
		String mkbh = pd.getString("mkbh");
		String sfbj = pd.getString("sfbj");
		String xmguid = pd.getString("xmguid");
		String kyje = pd.getString("kyje");
		System.err.println("---------========="+xmguid);
		String xmmc = pd.getString("xmmc");
		String bxzje = pd.getString("bxzje");
		String look = pd.getString("look");
		String djbh =Validate.isNullToDefaultString(pd.get("djbh"), GenAutoKey.createKeyforth("CW_CLFBXZB","CL", "djbh"));
		mv.addObject("djbh", djbh);
		String url = "wsbx/ccbx/jsfs_operate";
		//冲借款
		List cjkList = rcbxService.getCxjkListHx(zbid);
		mv.addObject("cjkList", cjkList);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		//对公支付
		List dgList = rcbxService.getDgList(zbid);
		//对私支付
		List dsList = rcbxService.getDsList(zbid);
		String loginInfo = "("+LUser.getRybh()+")"+LUser.getRyxm();
		String xmfzr = rcbxService.getXmFzr(xmguid);
		//支付方式回显的判断
		Map map = ccbxService.getBxzbById(zbid);
		String dqdlrguid=LUser.getGuid();
		mv.addObject("dqdlrguid", dqdlrguid);//当前登录人guid
		 //获取当前登录人的银行卡list 
		List<Map<String,Object>> dlryhklist=gwjdfbxsqService.getdlryhklist();
		mv.addObject("dlryhklist", dlryhklist);
		mv.addObject("money", pd.get("money"));
		mv.addObject("dgList",dgList);
		mv.addObject("kyje",kyje);
		mv.addObject("sfbj",sfbj);
		mv.addObject("map", map);
		mv.addObject("loginInfo", loginInfo);
		mv.addObject("xmfzr", xmfzr);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("bxzje", bxzje);
		mv.addObject("xmguid", xmguid);
		mv.addObject("xmmc", xmmc);
		mv.addObject("zbid", zbid);
		mv.addObject("mkbh", mkbh);
		mv.addObject("look", look);
		mv.addObject("ccywguid", ccywguid);
		mv.setViewName(url);
		return mv;
	}
	
	/**
	 * 修改主表的信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value="/update", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void doUpdate(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		String id = pd.getString("id");
		CW_CLFBXZB clfbxzb = new CW_CLFBXZB();
		if(id.contains("cxForm")){
			clfbxzb.setSfcjk("1");
		}
		if(id.contains("dgForm")){
			clfbxzb.setSfdgzf("1");
		}
		if(id.contains("dsForm")){
			clfbxzb.setSfdszf("1");
		}
		if(id.contains("gwForm")){
			clfbxzb.setSfgwk("1");
		}
		String zbid = pd.getString("zbid");
		clfbxzb.setGuid(zbid);
		int result = 0;
		result = ccbxService.updateBxzbById(clfbxzb);

	}
	
	/**
	 * 跳转差旅报销报销列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goCcbxListPage")
	public ModelAndView goRcbxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		
		List<Map<String, Object>> shztList = dictService.getDict(Constant.DJSHZT);//审核状态
		mv.addObject("mkbh", pd.getString("mkbh"));
		mv.addObject("shztList", shztList);
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		mv.addObject("shzt", pd.getString("shzt"));
		mv.setViewName("wsbx/ccbx/ccbx_list");
		return mv;
	}
	
	@RequestMapping("/updatesfwqbc")
	@ResponseBody
	public Object updatesfwqbc() {
		return ccbxService.updatesfwqbc(this.getPageData().getString("zbid"));
	}
	
	@RequestMapping("/checkIsSubmit")
	@ResponseBody
	public Object checkIsSubmit() {
		Map<String,Object> map = ccbxService.checkIsSubmit(this.getPageData().getString("zbid"));
		if(Validate.isNull(map.get("SFWQBC"))) {
			return 1;//oktosubmit
		}else {
			return 0;//no
		}
	}
	
	/**
	 * 获取差旅费报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" (select distinct a.CHECKSHZT,A.DJBH,A.CCYWGUID, A.sfkylbx,A.GUID,A.XMMC AS XMGUID,A.SHZT AS SHZTDM,A.FJZZS,(SELECT decode(x.xmsx,'01','部门经费','02','个人经费','') FROM cw_xmjbxxb x WHERE x.guid=a.xmmc) AS JFLX,"
				+ "(select syje from cw_xmjbxxb where guid = a.xmmc) as money,a.PROCINSTID as PROCINSTID,");

		sql.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='cclx' AND DM=A.CCLX)AS CCLX,");
		sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm99999999999999999999990.00')))BXZJE,");
		sql.append(" A.CZRQ,a.shzt as sh,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,");
		sql.append(" (SELECT  '('||RYb.RYBH||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.SQR)AS BXR,a.SQR,");
		sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=A.SHZT)SHZT,a.sfwqbc from cw_clfbxzb A) B");
		pageList.setSqlText("*");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = " and B.SQR='"+LUser.getGuid()+"'";
		PageData pd = this.getPageData();
		String type=pd.getString("type");
		
		if(pd.getString("type").equals("sh")){
			strWhere+=" and b.shztdm not in('0')";
		}else{
			String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "00");
			// 设置WHERE条件
			if(Validate.noNull(shzt)&&"all".equals(shzt)){
				strWhere += " AND 1=1";
			}
			if(Validate.noNull(shzt)&&"00".equals(shzt)){
				strWhere += " AND B.CHECKSHZT IN('00')";
			}
			if(Validate.noNull(shzt)&&"11".equals(shzt)){
				strWhere += " AND B.CHECKSHZT IN('11')";
			}
			if(Validate.noNull(shzt)&&"99".equals(shzt)){
				strWhere += " AND B.CHECKSHZT = '99'";
			}
		}
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
	 * 获取差旅费报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShPageList")
	@ResponseBody
	public Object getShPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		// 设置查询字段名
		pageList.setSqlText("*");
		String status = Validate.isNullToDefaultString(pd.getString("status"), "0");
		StringBuffer sql = new StringBuffer();
		if(Validate.noNull(status)&&"0".equals(status)){
			sql.append(" (select 'N' as flag,K.* from(select distinct A.DJBH,A.CCYWGUID,A.CCTS,A.CCRS,A.GUID,DECODE(A.XMMC,'','否','null','否',null,'否','是') AS SFSQSP,A.SHZT AS SHZTDM,A.FJZZS,(SELECT decode(x.xmsx,'01','部门经费','02','个人经费','') FROM cw_xmjbxxb x WHERE x.guid=a.xmmc) AS JFLX,a.PROCINSTID as PROCINSTID,");
			sql.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='cclx' AND DM=A.CCLX)AS CCLX,act.assignee_,");
			sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm999999999999999999999999990.00')))BXZJE,");
			sql.append(" A.CZRQ,a.shzt as sh,");
			sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,");
			sql.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.SQR)AS BXR,a.SQR,");
			sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=A.SHZT)SHZT from cw_clfbxzb A  LEFT JOIN ACT_RU_TASK ACT ON A.PROCINSTID = ACT.PROC_INST_ID_  where a.checkshzt<>'00')K where K.SHZT !='未提交' and K.assignee_='"+LUser.getGuid()+"') B ");
		}else{
			sql.append(" (select 'Y' as flag,K.* from(select distinct(A.GUID)guid,A.CCYWGUID,A.CCTS,A.CCRS,A.DJBH,DECODE(A.XMMC,'','否','null','否',null,'否','是') AS SFSQSP,A.SHZT AS SHZTDM,A.FJZZS,(SELECT decode(x.xmsx,'01','部门经费','02','个人经费','') FROM cw_xmjbxxb x WHERE x.guid=a.xmmc) AS JFLX,a.PROCINSTID as PROCINSTID,");
			sql.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='cclx' AND DM=A.CCLX)AS CCLX,act.assignee_,");
			sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm99999999999999999999999990.00')))BXZJE,");
			sql.append(" A.CZRQ,a.shzt as sh,");
			sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,");
			sql.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.SQR)AS BXR,a.SQR,");
			sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=A.SHZT)SHZT from cw_clfbxzb A  LEFT JOIN act_hi_actinst ACT ON A.PROCINSTID = ACT.PROC_INST_ID_ and END_TIME_ is not null )K where K.SHZT !='未提交' and K.assignee_='"+LUser.getGuid()+"') B ");
		}
		
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		//String strWhere = " and B.assignee_='"+LUser.getGuid()+"' and b.SHZT !='未提交'";
		String type=pd.getString("type");
		
//		if(pd.getString("type").equals("sh")){
//			strWhere+=" and b.shztdm not in('0')";
//		}
		String strOrder = "";

		String dwbh = pd.getString("treedwbh");
		String lrybh = LUser.getRybh();// 获取当前登录人员编号
		//pageList.setStrWhere(strWhere);
		pageList.setOrderBy(strOrder);
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
	 * 获取差旅费报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageListBySh")
	@ResponseBody
	public Object getPageListBySh() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" (select distinct A.DJBH, A.GUID,A.XMMC AS XMGUID,A.SHZT AS SHZTDM,A.FJZZS,(SELECT MC FROM GX_SYS_DMB WHERE ZL='jflx' AND DM=A.JFLX) AS JFLX,a.PROCINSTID as PROCINSTID,");

		sql.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='cclx' AND DM=A.CCLX)AS CCLX,");
		sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm99999999999999999990.00')))BXZJE,");
		sql.append(" A.CZRQ,a.shzt as sh,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,");
		sql.append(" (SELECT RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.SQR)AS BXR,a.SQR,");
		sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=A.SHZT)SHZT from cw_clfbxzb A) B");
		pageList.setSqlText("*");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = " and B.SQR='"+LUser.getGuid()+"'";
		PageData pd = this.getPageData();
		String type=pd.getString("type");
		
		if(pd.getString("type").equals("sh")){
			strWhere+=" and b.shztdm not in('0')";
		}
		String strOrder = "";

		String dwbh = pd.getString("treedwbh");
		String lrybh = LUser.getRybh();// 获取当前登录人员编号
		pageList.setStrWhere(strWhere);
		pageList.setOrderBy(strOrder);
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
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		int i = ccbxService.deleteZbInfoByGuid(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
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
		String zbid = pd.getString("guid");
		String ccywguid=Validate.isNullToDefaultString(pd.getString("ccywguid"), ccbxService.getCcywguidByZbid(zbid));
		String mkbh = pd.getString("mkbh");
		String type= pd.getString("type");
		String procinstid= pd.getString("procinstid");
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "");
		mv.addObject("shzt", shzt);
		String look = pd.getString("look");
		//项目信息 回显
		List xmlist=ccbxService.getxmxxlist(ccywguid);
		mv.addObject("xmlist", xmlist);
		//主表信息
		Map map = ccbxService.getZbPage(zbid);
		List<Map<String, Object>> mxList = ccbxService.getMxListByZbguid(zbid);
		List<Map<String, Object>> ryList = ccbxService.getRyListByZbguid(zbid);	
		List<Map<String, Object>> fpList = ccbxService.getFpListByZbguid(zbid);
		mv.addObject("clfbxzb",map);
		mv.addObject("zbMap",map);
		mv.addObject("mxList",mxList);
		mv.addObject("fpList",fpList);
		mv.addObject("ryList",ryList);	
	   List<Map<String, Object>> cclx = dictService.getDict("cclx");
   	   List<Map<String, Object>> jflxlist = dictService.getDict("jflx");
	    mv.addObject("cclxList",cclx);
	    mv.addObject("jflxList",jflxlist);
		String[] fjxx = fileService.getFjList(map.get("GUID")+"","",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		mv.addObject("ccywguid",ccywguid);
		mv.addObject("look",look);
		//冲销
		List cxList = rcbxService.getCxjkListHx(zbid);
		//对公
		List dgList = rcbxService.getDgList(zbid);
		//对私
		List dsList = rcbxService.getDsList(zbid);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);

		mv.addObject("cxList", cxList);
		mv.addObject("dgList", dgList);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("mkbh", mkbh);
		mv.addObject("guid", zbid);
		mv.addObject("procinstid", procinstid);
		mv.addObject("ProvicesList",provincesService.getProvicesList());
		if("sh".equals(type)){
			mv.setViewName("wsbx/ccbx/ccbx_check");	
		}else{
			mv.setViewName("wsbx/ccbx/ccbx_view");
		}
		return mv;
	} 
	/**
	 * 报账员审核 --跳转详情页面
	 * @return
	 */
	@RequestMapping(value = "/goViewJsp2")
	public ModelAndView goViewJsp2() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String zbid = pd.getString("guid");
		String ccywguid=Validate.isNullToDefaultString(pd.getString("ccywguid"), ccbxService.getCcywguidByZbid(zbid));
		String mkbh = pd.getString("mkbh");
		String type= pd.getString("type");
		String procinstid= pd.getString("procinstid");
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "");
		mv.addObject("shzt", shzt);
		String look = pd.getString("look");
		//项目信息 回显
		List xmlist=ccbxService.getxmxxlist(ccywguid);
		mv.addObject("xmlist", xmlist);
		//主表信息
		Map map = ccbxService.getZbPage(zbid);
		List<Map<String, Object>> mxList = ccbxService.getMxListByZbguid(zbid);
		List<Map<String, Object>> ryList = ccbxService.getRyListByZbguid(zbid);	
		List<Map<String, Object>> fpList = ccbxService.getFpListByZbguid(zbid);
		mv.addObject("clfbxzb",map);
		mv.addObject("zbMap",map);
		mv.addObject("mxList",mxList);
		mv.addObject("fpList",fpList);
		mv.addObject("ryList",ryList);	
		List<Map<String, Object>> cclx = dictService.getDict("cclx");
		List<Map<String, Object>> jflxlist = dictService.getDict("jflx");
		mv.addObject("cclxList",cclx);
		mv.addObject("jflxList",jflxlist);
		String[] fjxx = fileService.getFjList(map.get("GUID")+"","",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		mv.addObject("ccywguid",ccywguid);
		mv.addObject("look",look);
		//冲销
		List cxList = rcbxService.getCxjkListHx(zbid);
		//对公
		List dgList = rcbxService.getDgList(zbid);
		//对私
		List dsList = rcbxService.getDsList(zbid);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		
		mv.addObject("cxList", cxList);
		mv.addObject("dgList", dgList);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("mkbh", mkbh);
		mv.addObject("guid", zbid);
		mv.addObject("procinstid", procinstid);
		mv.addObject("ProvicesList",provincesService.getProvicesList());
		if("sh".equals(type)){
			mv.setViewName("wsbx/ccbx/ccbx_shbj/ccbx_shbj");	
		}else{
			mv.setViewName("wsbx/ccbx/ccbx_view");
		}
		return mv;
	} 
	/**
	 * 报账员审核---修改保存主表信息
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/doSaveZb2", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveZb2(CW_CLFBXZB clfbxzb) {
		int result = 0;
		JdbcTemplate jdbcTemplate=(JdbcTemplate) SpringBeanFactoryUtils.getBean("jdbcTemplate");
		PageData pd = this.getPageData();
        String xmmc = pd.getString("xmguid");//项目id
        String ccywguid = pd.getString("ccywguid");//事前审批单据
        System.err.println("-------------============xmmc"+xmmc);
        System.err.println("-------------============ccywguid"+ccywguid);
		clfbxzb.setSqr(LUser.getGuid());
		clfbxzb.setXmmc(xmmc);
		clfbxzb.setCcywguid(ccywguid);
		String zbguid=clfbxzb.getGuid();
		String type = pd.getString("type");
		String sql = "SELECT sfxy FROM  GX_SYS_DWB WHERE dwbh='"+LUser.getDwbh()+"'";
		Map<String,Object> map2=jdbcTemplate.queryForMap(sql, new Object[]{});
		clfbxzb.setSfxy(Validate.isNullToDefaultString(map2.get("sfxy"), "0"));
		if (true) {
			Map map =new HashMap();
			map=ccbxService.getBxzbById(zbguid);
			if("U".equals(type)) {
				clfbxzb.setSqr(map.get("SQR").toString());
				result=	ccbxService.doUpdateZb(clfbxzb);
			}else {
				result = ccbxService.doAddZb(clfbxzb);
			}
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		return "";
	}
	/**
	 * 报账员审核 -- 结算方式
	 * @return
	 */
	@RequestMapping(value="/jsfs2")
	public ModelAndView jsfs2(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String procinstid= pd.getString("procinstid");
		//此处生成申请主表的主键id
		String zbid = Validate.isNullToDefaultString(pd.get("zbguid"), GenAutoKey.get32UUID()); 
		String ccywguid = Validate.isNullToDefaultString(pd.getString("ccywguid"), ccbxService.getCcywguidByZbid(zbid));
		//费用列表
		String mkbh = pd.getString("mkbh");
		String xmguid = pd.getString("xmguid");
		String xmmc = pd.getString("xmmc");
		String bxzje = pd.getString("bxzje");
		String look = pd.getString("look");
		String djbh =Validate.isNullToDefaultString(pd.get("djbh"), GenAutoKey.createKeyforth("CW_CLFBXZB","CL", "djbh"));
		mv.addObject("djbh", djbh);
		String url = "wsbx/ccbx/ccbx_shbj/jsfs_shbj";
		//冲借款
		List cjkList = rcbxService.getCxjkListHx(zbid);
		mv.addObject("cjkList", cjkList);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		//对公支付
		List dgList = rcbxService.getDgList(zbid);
		//对私支付
		List dsList = rcbxService.getDsList(zbid);
		String loginInfo = "("+LUser.getRybh()+")"+LUser.getRyxm();
		String xmfzr = rcbxService.getXmFzr(xmguid);
		//支付方式回显的判断
		Map map = ccbxService.getBxzbById(zbid);
		String dqdlrguid=LUser.getGuid();
		mv.addObject("dqdlrguid", dqdlrguid);//当前登录人guid
		 //获取当前登录人的银行卡list 
		List<Map<String,Object>> dlryhklist=gwjdfbxsqService.getdlryhklist();
		mv.addObject("dlryhklist", dlryhklist);
		mv.addObject("money", pd.get("money"));
		mv.addObject("dgList",dgList);
		mv.addObject("map", map);
		mv.addObject("loginInfo", loginInfo);
		mv.addObject("xmfzr", xmfzr);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("bxzje", bxzje);
		mv.addObject("xmdl", CommonUtil.getXGuid(ccywguid));
//		String xmdl = CommonUtil.getXmDlBYGuid(xmguid);
//		mv.addObject("xmdl", xmdl);
		mv.addObject("xmmc", xmmc);
		mv.addObject("zbid", zbid);
		mv.addObject("mkbh", mkbh);
		mv.addObject("look", look);
		mv.addObject("procinstid", procinstid);
		mv.addObject("ccywguid", ccywguid);
		mv.setViewName(url);
		return mv;
	}
	/**
	 * 提交
	 * 
	 * @return
	 */
	@RequestMapping(value = "/submit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object submit(CW_CLFBXZB clfbxzb,HttpSession session) {
		String guid = this.getPageData().getString("guid");
		String ary[] = guid.split(",");
		int i = 0;
		DshInfoMap msgMap = new DshInfoMap();
		for(int a=0;a<ary.length;a++){
			String id = Validate.isNullToDefaultString(ary[a], "");
			if(Validate.noNull(id)){
				String procinstid = ccbxService.submit(id, "", "1","");
				if(Validate.noNull(procinstid)) {
					i++;
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstid);
					//如果不是审核通过的单据（通过的会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getCcbxDshxxMsg(id);
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
	 * 跳转打印页面
	 * @return
	 */
	@RequestMapping(value = "/goPrint")
	public ModelAndView goPrint(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String zbid = pd.getString("guid");
		String mkbh = pd.getString("mkbh");
		String ccywguid=Validate.isNullToDefaultString(pd.getString("ccywguid"), ccbxService.getCcywguidByZbid(zbid));
		//主表信息
		Map map = ccbxService.getZbPage(zbid);
		List<Map<String, Object>> mxList = ccbxService.getMxListByZbguid(zbid);
		List<Map<String, Object>> ryList = ccbxService.getRyListByZbguid(zbid);		
		//项目信息 回显
//		List xmlist=ccbxService.getxmxxlist(ccywguid);
//		mv.addObject("xmlist", xmlist);
		mv.addObject("clfbxzb",map);
		mv.addObject("zbMap",map);
		mv.addObject("mxList",mxList);
		mv.addObject("ryList",ryList);	
	   List<Map<String, Object>> cclx = dictService.getDict("cclx");
   	   List<Map<String, Object>> jflxlist = dictService.getDict("jflx");
	    mv.addObject("cclxList",cclx);
	    mv.addObject("jflxList",jflxlist);

		//项目信息 回显
		List xmlist=ccbxService.getxmxxlist(ccywguid);
		mv.addObject("xmlist", xmlist);
		
		//冲销
		List cxList = rcbxService.getCxjkListHx(zbid);
		//对公
		List dgList = rcbxService.getDgList(zbid);
		//对私
		List dsList = rcbxService.getDsList(zbid);
		//公务卡
		List gwkList = rcbxService.getGekList(zbid);
		
		//校长（签字）
		Map yxMap = rcbxService.getPrintBmXzclf(zbid);
		String[] fjxx = fileService.getFjList((String)yxMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView1",fjxx[0].replace("'", ""));
		mv.addObject("fjConfig1",fjxx[1]);
		
		//部门分管领导
		Map shrMap = rcbxService.getPrintBmfgldclf(zbid);
		String[] fjxx2 = fileService.getFjList((String)shrMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView2",fjxx2[0].replace("'", ""));
		mv.addObject("fjConfig2",fjxx2[1]);
		
		//财务处负责人
		Map cwcMap = rcbxService.getPrintCwcclf(zbid);
		String[] fjxx3 = fileService.getFjList((String)cwcMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView3",fjxx3[0].replace("'", ""));
		mv.addObject("fjConfig3",fjxx3[1]);
		
		//部门负责人（签字）
		Map bmMap = rcbxService.getPrintBmclf(zbid);
		String[] fjxx4 = fileService.getFjList((String)bmMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView4",fjxx4[0].replace("'", ""));
		mv.addObject("fjConfig4",fjxx4[1]);
		
		//报销人（签字）
		String[] fjxx5 = fileService.getFjList((String)map.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView5",fjxx5[0].replace("'", ""));
		mv.addObject("fjConfig5",fjxx5[1]);
		 
		String organizationname=session.getAttribute("organizationname")+"";

		mv.addObject("cxList", cxList);
		mv.addObject("dgList", dgList);
		mv.addObject("dsList", dsList);
		mv.addObject("gwkList", gwkList);
		mv.addObject("mkbh", mkbh);
		mv.addObject("organizationname", organizationname);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(new Date());
		mv.addObject("time",time);
		
		//-------------------------------
		String guid = "'"+pd.getString("guid")+"'";
		String guid1 = pd.getString("guid");
		String procinstid = pd.getString("procinstid");
		Map<?,?> list = kylbxService.getSinglePrintInfoByIds(guid);//查询打印数据
		Map yxMap1;
		Map bmMap1;
		Map kjMap1;
		List jjMap1;
		jjMap1 = rcbxService.getPrintJj(guid1);
		 yxMap1 = rcbxService.getPrintYx(guid1);
		 bmMap1 = rcbxService.getPrintBmclf(guid1);
		 kjMap1 = rcbxService.getPrintKjclf(guid1);
		Map map11 = kylbxService.getPrintInfoById(guid);//查询打印数据
		List list1 = processService.findBljlOfTg(procinstid);
		List list2 = processService.findBljlOfSqr(procinstid);
		mv.addObject("guid", guid);
		mv.addObject("printinfolists", list1);
		mv.addObject("printinfolistsqr", list2);
		mv.addObject("organizationname1",map);
		  SimpleDateFormat dfs=new SimpleDateFormat("yyyy-MM-dd");
	        //String organizationname=session.getAttribute("organizationname")+"";
	        //String time=dfs.format(new Date());
	        //String url = "wsbx/wdbx/PrintSample441";
		  int length = jjMap1.size()+1;
			 mv.addObject("jjMap1",jjMap1);
			 mv.addObject("length",length);
			 mv.addObject("yxMap1",yxMap1);
			mv.addObject("bmMap1",bmMap1);
			mv.addObject("kjMap1",kjMap1);
			mv.addObject("guid", guid);
			mv.addObject("printinfolist", list);
			mv.addObject("time",time);
			mv.addObject("organizationname",organizationname);
		//-------------------------------
		//添加打印参数
		mv.addObject("dyzdbj", CommonUtil.getDyzdbj());//装订边距
		mv.addObject("ymkd", 297-19-CommonUtil.getDyzdbj());//页面宽度
		mv.addObject("ztdkd", 297+6-CommonUtil.getDyzdbj());//粘贴单宽度
		mv.addObject("txmbj", 297-79-CommonUtil.getDyzdbj());//条形码边距
		mv.setViewName("wsbx/ccbx/ccbx_print");
		return mv;
	} 
	/**
	 * 当前登录人被授权的项目
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMXPageListByLoginXm")
	@ResponseBody
	public Object getMXPageListByLoginXm() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		//当前登录人的id
		String loginId = LUser.getGuid();
		sql.append(" GUID,(SELECT DJBH FROM CW_CCSQSPB b WHERE B.GUID= A.SPBH) DJBH,SPBH,to_char(KSSJ,'yyyy-MM-dd') KSSJ,to_char(JSSJ,'yyyy-MM-dd') JSSJ,CFDD,MDDD");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_XCXXB A");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();
		strWhere += " and ((SELECT SQR FROM CW_CCSQSPB B WHERE B.GUID= A.SPBH)='"+loginId+"')";
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
	@RequestMapping(value = "/checkXzxmguid")
	@ResponseBody
	public Object checkXzxmguid() throws Exception {
		String xzxmguid = this.getPageData().getString("xzxmguid");
		String zbguid = this.getPageData().getString("zbguid");
		Map<String, Object> map = ccbxService.checkXzxmGuid(xzxmguid,zbguid);
		if (map.isEmpty()||Validate.isNull(map)||map.size()==0) {
			return "success";
		} else {
			return "false";
		}
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
		mv.addObject("shztList", shztList);
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		Map param = new HashMap();
		mv.setViewName("wsbx/ccbx/check_list");
		return mv;
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
		String ccywguid=pd.getString("ccywguid");
		String shyj = pd.getString("shyj");
		String guids[] = guid.split(",");
		String procinstids[] = procinstid.split(",");
		DshInfoMap msgMap = new DshInfoMap();
		String forward = "";
		if ("false".equals(pass)) {
			for(int i=0;i<guids.length;i++){
				String id = Validate.isNullToDefaultString(guids[i], "");
				String procinstId = Validate.isNullToDefaultString(procinstids[i], "");
				ccbxService.rejectleaveinfo(session, id,procinstId,shyj,shyjb);
				if(Validate.noNull(procinstId)) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstId);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getCcbxDshxxMsg(id);
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
				ccbxService.approveLeaveInfo( session,shyjb, id,procinstId,shyj,ccywguid);
				if(Validate.noNull(procinstId)) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstId);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getCcbxDshxxMsg(id);
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
		return "success";
	}
	
	//获取列表数据
		@RequestMapping(value="getCcyw",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getCcywsqPageData() {
			PageList pageList = new PageList();
			PageData pd = this.getPageData();
			//设置查询字段名
			StringBuffer sqltext = new StringBuffer();
			String shzt = pd.getString("shzt");
//			sqltext.append(" distinct TO_CHAR(A.SQRQ, 'YYYY-MM-DD') SQRQ,A.GUID,A.SQRXM,A.CCRS,A.SZBM,A.DJBH,A.CCTS,A.PROCINSTID,A.SHZT AS SHZTDM, ");
//			sqltext.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.CCLX+"' AND C.DM = A.CCLX) AS CCLX, ");
//			sqltext.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.JFLX+"' AND C.DM = A.SHZT) AS SHZT, ");
//			sqltext.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.JFLX+"' AND C.DM = B.JFLX) AS JFLX,");
//			sqltext.append(" (SELECT WM_CONCAT(B.XMMC) FROM CW_CCSQSPB_XM S LEFT JOIN CW_JFSZB B ON S.JFBH = B.guid WHERE S.CCSQID = A.GUID) AS xmmc ");
//			String tableName= " CW_CCSQSPB A LEFT JOIN CW_CCSQSPB_XM M ON A.GUID = M.CCSQID LEFT JOIN CW_JFSZB B ON A.GUID = M.CCSQID ";
//			String strWhere = " and A.SQR = '"+LUser.getGuid()+"'";
//			//设置条件
//			strWhere += " and A.SHZT IN ('06') and nvl(b.syje,0)>0 and not exists (select 1 from cw_clfbxzb cw where cw.ccywguid=a.guid) ";
			sqltext.append(" distinct sqrq,guid,sqrxm,szbm,djbh,ccts,shztdm,xmmc,cclx,shzt,CCRS ");//,JFLX
			String tableName=" (select distinct TO_CHAR(A.SQRQ, 'YYYY-MM-DD') SQRQ,A.GUID,A.SQRXM,A.SZBM,A.DJBH,A.CCTS,A.CCRS,A.PROCINSTID, A.SHZT AS SHZTDM," + 
				"   (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.CCLX+"' AND C.DM = A.CCLX) AS CCLX," + 
				"   (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.JFLX+"' AND C.DM = A.SHZT) AS SHZT," + //(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.JFLX+"' AND C.DM = B.JFLX) AS JFLX, 
				"   (SELECT WM_CONCAT(B.XMMC) FROM CW_CCSQSPB_XM S LEFT JOIN CW_JFSZB B ON S.JFBH = B.guid" + 
				"                  WHERE S.CCSQID = A.GUID) AS xmmc from CW_CCSQSPB A LEFT JOIN CW_CCSQSPB_XM M  ON A.GUID = M.CCSQID LEFT JOIN CW_JFSZB B  ON A.GUID = M.CCSQID" + 
				" where 1 = 1 " + 
				"   and A.SQR = '"+LUser.getGuid()+"'" + 
				"   and A.SHZT IN ('06')" + 
				"   and nvl(b.syje, 0) > 0" + 
				"   and not exists " + 
				" (select 1 from cw_clfbxzb cw where cw.ccywguid = a.guid))K";
			String ccywguid = Validate.isNullToDefaultString(pd.getString("ccywguid"),"");
			String operate = Validate.isNullToDefaultString(pd.getString("operate"),"");
//			if("U".equals(operate)){
//				strWhere += " and a.guid='"+ccywguid+"'";
//			}
			pageList.setSqlText(sqltext.toString());
			pageList.setTableName(tableName);
			pageList.setKeyId("guid");
//			pageList.setStrWhere(strWhere);
			//页面数据
		    pageList = pageService.findPageList(pd,pageList);
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
		
		/**
		 * 跳转详情页面
		 * @return
		 */
		@RequestMapping(value = "/goViewJspByMe")
		public ModelAndView goViewJspByMe() {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			String zbid = pd.getString("guid");
			String ccywguid=Validate.isNullToDefaultString(pd.getString("ccywguid"), ccbxService.getCcywguidByZbid(zbid));
			String mkbh = pd.getString("mkbh");
			String type= pd.getString("type");
			String procinstid= pd.getString("procinstid");
			String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "");
			mv.addObject("shzt", shzt);
			String look = pd.getString("look");
			//项目信息 回显
			List xmlist=ccbxService.getxmxxlist(ccywguid);
			mv.addObject("xmlist", xmlist);
			//主表信息
			Map map = ccbxService.getZbPage(zbid);
			List<Map<String, Object>> mxList = ccbxService.getMxListByZbguid(zbid);
			List<Map<String, Object>> ryList = ccbxService.getRyListByZbguid(zbid);	
			List<Map<String, Object>> fpList = ccbxService.getFpListByZbguid(zbid);
			mv.addObject("clfbxzb",map);
			mv.addObject("zbMap",map);
			mv.addObject("mxList",mxList);
			mv.addObject("fpList",fpList);
			mv.addObject("ryList",ryList);	
		   List<Map<String, Object>> cclx = dictService.getDict("cclx");
	   	   List<Map<String, Object>> jflxlist = dictService.getDict("jflx");
		    mv.addObject("cclxList",cclx);
		    mv.addObject("jflxList",jflxlist);
			String[] fjxx = fileService.getFjList(map.get("GUID")+"","",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0]);
			mv.addObject("fjConfig",fjxx[1]);
			mv.addObject("ccywguid",ccywguid);
			mv.addObject("look",look);
			//冲销
			List cxList = rcbxService.getCxjkListHx(zbid);
			//对公
			List dgList = rcbxService.getDgList(zbid);
			//对私
			List dsList = rcbxService.getDsList(zbid);
			//公务卡
			List gwkList = rcbxService.getGekList(zbid);

			mv.addObject("cxList", cxList);
			mv.addObject("dgList", dgList);
			mv.addObject("dsList", dsList);
			mv.addObject("gwkList", gwkList);
			mv.addObject("mkbh", mkbh);
			mv.addObject("guid", zbid);
			mv.addObject("procinstid", procinstid);
			mv.addObject("ProvicesList",provincesService.getProvicesList());
			if("sh".equals(type)){
				mv.setViewName("wsbx/ccbx/ccbx_check");	
			}else{
				mv.setViewName("wsbx/ccbx/ccbx_view");
			}
			return mv;
		} 
		@RequestMapping("/pageSkip")
		public ModelAndView pageSkip() {
			ModelAndView mv = this.getModelAndView();
			PageData pd=  this.getPageData();
			String ccywguid = pd.getString("ccywguid");
			mv.addObject("ccywguid",ccywguid);
			String pageName = pd.getString("pageName");
			mv.setViewName("wsbx/ccbx/"+pageName);
			System.err.println("______________wsbx/ccbx/"+pageName);
			return mv;
		}
		/**
		 * 获取报销标准
		 * @return
		 */
		@RequestMapping(value = "/getBxbz")
		@ResponseBody
		public Object getBxbz() throws Exception {
			PageData pd=  this.getPageData();
			String rylx = pd.getString("rylx");
			String bxjb = pd.getString("bxjb");
			String provinceid = pd.getString("provinceid");
			String cityid = pd.getString("cityid");
			String kssj = pd.getString("kssj");
			String jssj = pd.getString("jssj");
			return ccbxService.getBxbz(rylx,bxjb,provinceid,cityid,kssj,jssj);
		}
		/**
		 * 检测发票号
		 * @return
		 */
		@RequestMapping(value = "/checkFph")
		@ResponseBody
		public Object checkFph() throws Exception {
			PageData pd=  this.getPageData();
			String zbid = pd.getString("zbid");
			String arrs = pd.getString("arr");
			String arr[] = arrs.split(",");
			String fph = "";
			List<Map<String,Object>> fphList = ccbxService.checkFph(zbid);
			for(int i=0;i<arr.length;i++){
				for(int j=0;j<fphList.size();j++){
					fph = fphList.get(j).get("fph")+"";
					if(fph.equals(arr[i]+"")){
						return arr[i];
					}
				}
			}
			return "0";
		}
		@RequestMapping(value = "/checkFphs")
		@ResponseBody
		public Object checkFphs() throws Exception {
			PageData pd=  this.getPageData();
			String arrs = pd.getString("arr");
			String arr[] = arrs.split(",");
			String fph = "";
			String aa = "1";
			String aas = ccbxService.checkFphs();
			for(int i=0;i<arr.length;i++){
				String ss = arr[i];
					if(aas.contains(ss)){
						aa="0";
					}else{
						aa="1";
					}
			}
			return aa;
		}	
		/**
		 * 获取报销级别通过人员工号
		 * @return
		 */
		@RequestMapping(value="getBxjbByRygh",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getBxjbByRygh() throws Exception {
			PageData pd=  this.getPageData();
			String rygh = pd.getString("rygh");
			String bxjb = ccbxService.getBxjbByRygh(rygh);
			return bxjb+"";
		}
		/**
		 * 导出信息Excel
		 * 
		 * @return
		 */
		@RequestMapping(value = "/expExcel_Sq", produces = "text/json;charset=UTF-8")
		@ResponseBody
		public Object expExcel_Sq() {
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
			StringBuffer sql = new StringBuffer();
			// 设置查询字段名
			sql.append("select * from (select distinct a.CHECKSHZT,A.DJBH,A.CCYWGUID, A.sfkylbx,A.GUID,A.XMMC AS XMGUID,A.SHZT AS SHZTDM,A.FJZZS,(SELECT decode(x.xmsx,'01','部门经费','02','个人经费','') FROM cw_xmjbxxb x WHERE x.guid=a.xmmc) AS JFLX,"
					+ "(select syje from cw_xmjbxxb where guid = a.xmmc) as money,a.PROCINSTID as PROCINSTID,");

			sql.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='cclx' AND DM=A.CCLX)AS CCLX,");
			sql.append(" decode(nvl(A.BXZJE,0),0,'0.00',(to_char(round(A.BXZJE,2),'fm99999999999999999999990.00')))BXZJE,");
			sql.append(" A.CZRQ,a.shzt as sh,");
			sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,");
			sql.append(" (SELECT  '('||RYb.RYBH||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.SQR)AS BXR,a.SQR,");
			sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=A.SHZT)SHZT,a.sfwqbc from cw_clfbxzb A) B");
			// 设置WHERE条件
			sql.append(" where 1=1  and B.SQR='"+LUser.getGuid()+"'");
			String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "00");
			// 设置WHERE条件
			if(Validate.noNull(shzt)&&"all".equals(shzt)){
				sql.append(" AND 1=1 ");
			}
			if(Validate.noNull(shzt)&&"00".equals(shzt)){
				sql.append(" AND B.CHECKSHZT IN('00')");
			}
			if(Validate.noNull(shzt)&&"11".equals(shzt)){
				sql.append(" AND B.CHECKSHZT IN('11')");
			}
			if(Validate.noNull(shzt)&&"99".equals(shzt)){
				sql.append(" AND B.CHECKSHZT = '99'");
			}
			sql.append(CommonUtils.jsonToSql(searchJson));
			String id = pd.getString("guid");
			if (Validate.noNull(id)) {
				sql.append(" and B.GUID IN ('" + id.replace(",", "','") + "') ");
			}
			sql.append(" ORDER BY shzt desc,SHZTDM desc ");
			List<M_largedata> mlist = new ArrayList<M_largedata>();
			M_largedata m = new M_largedata();
			m.setName("shzt");
			m.setShowname("审核状态");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("djbh");
			m.setShowname("单据编号");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("bxr");
			m.setShowname("报销人");
			mlist.add(m);
			m = null;

			m = new M_largedata();
			m.setName("szbm");
			m.setShowname("所在部门");
			mlist.add(m);
			m = null;
			
			
			m = new M_largedata();
			m.setName("czrq");
			m.setShowname("报销日期");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("bxzje");
			m.setShowname("报销总金额（元）");
			mlist.add(m);
			m = null;

			m = new M_largedata();
			m.setName("cclx");
			m.setShowname("出差类型");
			mlist.add(m);
			m = null;

			// 导出方法
			pageService.ExpExcel(sql.toString(), realfile, filedisplay, mlist);
			return "{\"url\":\"excel\\\\" + file + ".xls\"}";
		}
}