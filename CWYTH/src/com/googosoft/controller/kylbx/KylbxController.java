package com.googosoft.controller.kylbx;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.controller.wsbx.gwjdfbx.GwjdfbxsqController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.base.ProcessService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.kylbx.KylbxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.gwjd.GwjdSqService;
import com.googosoft.service.wsbx.gwjdfbx.GwjdfbxsqService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value ="/kylbx")
public class KylbxController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	@Resource(name="kylbxService")
	private KylbxService kylbxService;//单例

	@Resource(name="dictService")
	DictService dictService;
	
	@Resource(name="fileService")
	FileService fileService;
	
	@Resource(name="gwjdfbxsqService")
	GwjdfbxsqService gwjdfbxsqService;
	
	@Resource(name="gwjdfbxsqController")
	GwjdfbxsqController gwjdfbxsqController;


	@Resource(name = "rcbxService")
	private RcbxService rcbxService;

	@Resource(name = "processService")
	private ProcessService processService;
	@Resource(name="gwjdsqService")
	private GwjdSqService gwjdsqService;
	
	/*
	 * 科研类报销树(new)
	 */
	@RequestMapping(value="/kylbxWindow")
	public ModelAndView goDwxxPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/wdbx/bmysbzTree");
		return mv;
	}
	/*
	 * 科研类报销树2(new)
	 */
	@RequestMapping(value="/kylbxWindowwdbx")
	public ModelAndView gowdbxDwxxPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/wdbx/wdbxbmysbzTree");
		return mv;
	}
	//跳转到公务接待明细页面
	@RequestMapping(value="/gwjdmxEdit")
	public ModelAndView goGwjdmxEditPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		gwjdfbxsqController.iniFileConfig(mv);
		gwjdfbxsqController.iniGwjdmx(pd, mv);
		String guid=pd.getString("guid");
		mv.addObject("guid", guid);
		//mv.setViewName("wsbx/wdbx/gwjdmx_edit");
		mv.addObject("look", "look");
		mv.setViewName("wsbx/gwjdfbx/gwjdfbxsq/gwjdmx_edit");
		return mv;
	}
	//跳转到结算方式页面
	@RequestMapping(value="/jsfssz")
	public ModelAndView goJsfssz(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		gwjdfbxsqController.iniFileConfig(mv);
		gwjdfbxsqController.iniGwjdmx(pd, mv);
		gwjdfbxsqController.iniJsfssz(mv, pd);
		String guid=pd.getString("guid");
		System.out.println("****"+guid);
		mv.addObject("guid", guid);
		mv.setViewName("wsbx/wdbx/jsfssz");
		return mv;
	}
	
		
	/*
	 * 
	 */
	@RequestMapping(value = "/yslx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object schoolTree(HttpServletResponse response)
			throws java.io.IOException {
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		if ("get-ys".equals(menu)) {
			return kylbxService.getYsLx(pd, rootPath);
		} else {
			return "";
		}
	} 
	
	
	/**
	 * 科研类报销树
	 * 
	 */
	@RequestMapping(value="/kylbxTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object kmszTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();	
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		if("get-kmsz".equals(menu)){			
			if("root".equals(jb)){//		
				return kylbxService.getKmszNodezj(pd,"1",rootPath);
			}else{
			    return kylbxService.getKmszNodezj(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
	
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dm");
		mv.setViewName("wsbx/wdbx/kylbx_list");
		mv.addObject("dwbh",dwbh);
		String dwbh1="4";
		mv.addObject("dwbh1",dwbh1);
		return mv;

	}
	
	@RequestMapping(value="/demoPrint")
	public ModelAndView demoPrint(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("wdbh");
		String length = pd.getString("length");
		String dm = pd.getString("dm");
		List map =  kylbxService.getObjectDyById(pd,guid);
//		String numFalg = kylbxService.getflagById(pd,guid);
		List list = kylbxService.getPrintInfoByIds(pd);//查询打印数据
//		List listg = kylbxService.getPrintInfoByIds(pd);//公共接待报销信息查询
		String url = "wsbx/gwjdf/PrintSample442";
		mv.addObject("guid", guid);
		mv.addObject("List", map);
		mv.addObject("length", length);
		mv.addObject("printinfolist", list);
		mv.setViewName(url);
		return mv;
	}
	/**
	 * @return
	 */
	@RequestMapping(value="/singlePrint")
	public ModelAndView singlePrint(){
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = "'"+pd.getString("guid")+"'";
		String guid1 = pd.getString("guid");
		String lx = pd.getString("lx");
		Map<?,?> list = kylbxService.getSinglePrintInfoByIds(guid);//查询打印数据
		Map yxMap;
		Map bmMap;
		Map kjMap;
		List jjMap;
		if("公务接待报销".equals(lx)) {
			 yxMap = rcbxService.getPrintGWJDYx(guid1);
			 String[] fjxx = fileService.getFjList((String)yxMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView",fjxx[0].replace("'", ""));
			 mv.addObject("fjConfig",fjxx[1]);
			 bmMap = rcbxService.getPrintGWJDBm(guid1);
			 String[] fjxx2 = fileService.getFjList((String)bmMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView2",fjxx2[0].replace("'", ""));
			 mv.addObject("fjConfig2",fjxx2[1]);
			 kjMap = rcbxService.getPrintGWJDKj(guid1);
			 String[] fjxx3 = fileService.getFjList((String)kjMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView3",fjxx3[0].replace("'", ""));
			 mv.addObject("fjConfig3",fjxx3[1]);
			
		}else {//
			 jjMap = rcbxService.getPrintJj(guid1);
			 yxMap = rcbxService.getPrintYx(guid1);
			 System.err.println((String)yxMap.get("RYBH"));
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
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(new Date());
		String url = "wsbx/gwjdf/PrintSample441";
		mv.addObject("guid", guid);
		mv.addObject("printinfolist", list);
		mv.addObject("yxMap",yxMap);
		mv.addObject("bmMap",bmMap);
		mv.addObject("kjMap",kjMap);
		mv.addObject("time", time);
		mv.setViewName(url);
		return mv;
	}
	
	@RequestMapping(value="/singlePrintwdbx")
	public ModelAndView singlePrintwdbx(){
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = "'"+pd.getString("guid")+"'";
		String guid1 = pd.getString("guid");
		String lx = pd.getString("lx");
		Map<?,?> list = kylbxService.getSinglePrintInfoByIds(guid);//查询打印数据
		Map yxMap;
		Map bmMap;
		Map kjMap;
		List jjMap;
		String url = null;
		if("公务接待报销".equals(lx)) {
			 yxMap = rcbxService.getPrintGWJDYx(guid1);
			 String[] fjxx = fileService.getFjList((String)yxMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView",fjxx[0].replace("'", ""));
			 mv.addObject("fjConfig",fjxx[1]);
			 bmMap = rcbxService.getPrintGWJDBm(guid1);
			 String[] fjxx2 = fileService.getFjList((String)bmMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView2",fjxx2[0].replace("'", ""));
			 mv.addObject("fjConfig2",fjxx2[1]);
			 kjMap = rcbxService.getPrintGWJDKj(guid1);
			 String[] fjxx3 = fileService.getFjList((String)kjMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView3",fjxx3[0].replace("'", ""));
			 mv.addObject("fjConfig3",fjxx3[1]);
			 mv.addObject("yxMap",yxMap);
			 mv.addObject("bmMap",bmMap);
			 mv.addObject("kjMap",kjMap);
			 url = "wsbx/gwjdf/PrintSample443";
			
		}else if("日常报销".equals(lx)) {
			 jjMap = rcbxService.getPrintJj(guid1);
			 yxMap = rcbxService.getPrintYx(guid1);
			 System.err.println((String)yxMap.get("RYBH"));
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
			 mv.addObject("yxMap",yxMap);
			mv.addObject("bmMap",bmMap);
			mv.addObject("kjMap",kjMap);
			url = "wsbx/gwjdf/PrintSample441";
		}else{
			 jjMap = rcbxService.getPrintJj(guid1);
			 yxMap = rcbxService.getPrintYxclf(guid1);
			 System.err.println((String)yxMap.get("RYBH"));
			 String[] fjxx = fileService.getFjList((String)yxMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView",fjxx[0].replace("'", ""));
			 mv.addObject("fjConfig",fjxx[1]);
			 bmMap = rcbxService.getPrintBmclf(guid1);
			 String[] fjxx2 = fileService.getFjList((String)bmMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView2",fjxx2[0].replace("'", ""));
			 mv.addObject("fjConfig2",fjxx2[1]);
			 kjMap = rcbxService.getPrintKjclf(guid1);
			 String[] fjxx3 = fileService.getFjList((String)kjMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView3",fjxx3[0].replace("'", ""));
			 mv.addObject("fjConfig3",fjxx3[1]);
			 int length = jjMap.size()+1;
			 mv.addObject("jjMap",jjMap);
			 mv.addObject("length",length);
			 mv.addObject("yxMap",yxMap);
			mv.addObject("bmMap",bmMap);
			mv.addObject("kjMap",kjMap);
			
		}
        SimpleDateFormat dfs=new SimpleDateFormat("yyyy年MM月dd日");
        String time=dfs.format(new Date());
		
		mv.addObject("guid", guid);
		mv.addObject("printinfolist", list);
		mv.addObject("time",time);
		mv.setViewName(url);
		return mv;
	}
	
	@RequestMapping(value = "/gowdbxListPage")
	public ModelAndView gowdbxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dm");
		mv.setViewName("wsbx/wdbx/kylbxwdbx_list");
		mv.addObject("dwbh",dwbh);
		return mv;

	}
	

	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String zl = pd.getString("treedwbh");
		System.err.println("getPageList+zl===="+zl);
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		sqltext.append(" * ");
		if("3".equals(zl)){
			tablename.append(" (select t.guid,t.djbh,'' as FJZZS, to_char( (select '('||rybh||')'||xm from gx_sys_ryb ry where ry.rybh = t.bxry))  as bxrmc,"
					+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,"
					+ "to_char(t.CZRQ,'yyyy-mm-dd')as czrq ,'公务接待报销' as lx,t.shzt,"
					+ "(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc from Cw_gwjdfbxzb t where t.shzt = '8' and t.BXRY like '%"+rybh+"%')k ");
		}else if("1".equals(zl)){
			tablename.append(" ( select t.guid,t.djbh,t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid = t.bxr )||')'||(select r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,"
					+ "('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
					+ "t.szbm,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00')as bxzje,"
					+ "to_char(czrq,'yyyy-mm-dd')as czrq ,'日常报销'as lx,t.shzt,"
					+ "(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc from Cw_rcbxzb t where t.sfkylbx is null and t.shzt = '8' and t.bxr like '%"+loginId+"%' )k ");
		}else{
			
			tablename.append(" (  (select t.guid, t.djbh, '' as FJZZS,  to_char( (select '('||rybh||')'||xm from gx_sys_ryb ry where ry.rybh = t.bxry))  as bxrmc,"
					+ " ('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
					+ " to_char(T.bxje, 'FM9999999999999990.00') as BXZJE, t.szbm, to_char(t.CZRQ, 'yyyy-mm-dd') as czrq,"
					+ " '公务接待报销' as lx,t.shzt,(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc"
					+ "  from Cw_gwjdfbxzb t where t.shzt = '8' and t.BXRY like '%"+rybh+"%') union (select t.guid, t.djbh, to_char(t.fjzzs),"
					+ " to_char('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc,"
					+ " ('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
					+ " to_char(T.bxzje, 'FM9999999999999990.00') as bxzje, t.szbm, to_char(czrq, 'yyyy-mm-dd') as czrq,"
					+ " '日常报销' as lx,t.shzt,(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc from Cw_rcbxzb t where t.sfkylbx = '0' and t.shzt = '8' and t.bxr like '%"+loginId+"%' )   )k ");
		}
//		tablename.append(" ( select g.guid,g.bmbh,('('||g.bmbh||')'||(select d.mc from gx_sys_dmb d where d.dm = g.bmbh)) as bmmc,"
//				+ " (select t.mc from GX_SYS_DWB t where t.dwbh = g.fzr) as xmfzrmc, g.xmmc, g.nd, g.fzr,('('||g.fzr||')'||(select d.xm from gx_sys_ryb d where d.rybh = g.bmbh)) as fzrmc,"
//				+ " to_char(g.ysje, 'FM9999999999999990.0000') as ysje, to_char(g.syje, 'FM9999999999999990.0000') as syje, g.xmbh, g.xmlx,"
//				+ " (select d.mc from gx_sys_dmb d where d.zl = 'xmfl' and d.dm = g.xmlx) as xmlxmc, g.jflx, (select d.mc   from gx_sys_dmb d  where d.zl = 'jflx'"
//				+ " and d.dm = g.jflx) as jflxmc,g.kssj,  g.jssj,g.bz,  g.czr,g.czrq from Cw_jfszb g ) k");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(""); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 查询报销信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getwdbxPageList")
	@ResponseBody
	public Object getZcxgshwdbxPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String zl = pd.getString("treedwbh");
		String dlr= LUser.getRybh();
		sqltext.append(" distinct * ");
		if("3".equals(zl)){
			tablename.append(" (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt,t.guid,t.djbh,'' as FJZZS,t.BXRY as bxr,((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.BXRY) ) as bxrmc,");
			tablename.append("t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999999990.00') as BXZJE,");
			tablename.append("to_char(t.CZRQ,'yyyy-mm-dd')as czrq ,'公务接待报销' as lx from Cw_gwjdfbxzb t  where t.bxryid =(select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))k ");//where t.bxry='"+dlr+"'
		}else if("2".equals(zl)){
			tablename.append(" (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt,t.guid,(select x.guid from cw_xmjbxxb x where x.guid=t.xmmc)as xmmc ,t.djbh,t.sqr as bxr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.sqr) as bxrmc,");
			tablename.append("((select r.xm from gx_sys_ryb r where r.guid=t.sqr) ) as bxrmc1,");
		    tablename.append("t.kssj,t.jssj,t.cfdd,t.FJZZS,to_char(T.bxzje,'FM9999999999999999990.00')as bxzje ,( '(' || (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr) || ')' || (select d.mc");
		    tablename.append(" from gx_sys_dwb d where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr))) as szbmmc,");
		    tablename.append("t.CZRQ as czrq ,'差旅费报销'as lx,ktmc,t.ccywguid  from Cw_clfbxzb t  where t.sqr=(select ry.guid from gx_sys_ryb ry where ry.rybh='"+dlr+"'))k ");
		}
		else if("1".equals(zl)){
			tablename.append(" (select t.procinstid,t.shzt,t.checkshzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.guid, t.djbh, t.xmmc, to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r");
			tablename.append(" where r.guid = t.bxr)) as bxrmc, t.szbm as szbm, ('(' || t.szbm || ')' ||(select d.mc from gx_sys_dwb d where d.dwbh = t.szbm)) as szbmmc,");
			tablename.append(" to_char(t.fjzzs), to_char(T.bxzje, 'FM9999999999999999990.00') as bxzje, to_char(czrq, 'yyyy-mm-dd') as czrq,'日常报销' as lx from Cw_rcbxzb t");
			tablename.append(" where t.bxr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))k ");
		}else{
			tablename.append(" ( (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt,t.guid, t.djbh, t.xmmc, to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r");
			tablename.append(" where r.guid = t.bxr)) as bxrmc, t.szbm as szbm, ('(' || t.szbm || ')' ||(select d.mc from gx_sys_dwb d where d.dwbh = t.szbm)) as szbmmc,");
			tablename.append(" to_char(t.fjzzs), to_char(T.bxzje, 'FM9999999999999999990.00') as bxzje, to_char(czrq, 'yyyy-mm-dd') as czrq,'日常报销' as lx,'' ccywguid from Cw_rcbxzb t");
			tablename.append(" where t.bxr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))  ");
			tablename.append(" union ");
			tablename.append(" (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt, t.guid,t.djbh,(select x.guid from cw_xmjbxxb x where x.guid = t.xmmc) as xmmc,");
			tablename.append(" to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r  where r.guid = t.sqr)) as bxrmc, '' as szbm,( '(' || (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr) || ')' || (select d.mc ");
			tablename.append(" from gx_sys_dwb d where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr))) as szbmmc,  t.FJZZS, to_char(T.bxzje, 'FM9999999999999999990.00') as bxzje,");
			tablename.append(" t.CZRQ as czrq, '差旅费报销' as lx,t.ccywguid ccywguid from Cw_clfbxzb t where t.sqr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))");
			tablename.append(" union ");
			tablename.append(" (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt, t.guid, t.djbh,''xmmc, to_char((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.BXRY)) as bxrmc, t.szbm, ('(' || t.szbm || ')' || (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,");
			tablename.append(" '' as FJZZS, to_char(T.bxje, 'FM9999999999999999990.00') as BXZJE, to_char(t.CZRQ, 'yyyy-mm-dd') as czrq, '公务接待报销' as lx,'' ccywguid");
			tablename.append(" from Cw_gwjdfbxzb t   where t.bxryid =(select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"')) )k ");//where t.bxry = '"+dlr+"'
		}
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置WHERE条件
		String strWhere = "";
		String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"00");
		pageList.setKeyId("guid");//主键
		if("00".equals(shzt)){
			strWhere += " AND checkshzt IN('00')";
		}else if("11".equals(shzt)){
			strWhere += " AND checkshzt IN('11')";
		}else if("99".equals(shzt)){
			strWhere += " AND checkshzt in('99')";
		}else{
			strWhere += " AND 1=1";
		}
		pageList.setHj1("");//合计
		pageList.setStrWhere(strWhere); 
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	
	/**
	 * 跳转单位信息编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		System.err.println("pd."+pd.getString("zl"));
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){
			
			String bh = AutoKey.createDwbh("Cw_grjfszb", "guid", "6");
			Map<String,String> map = new HashMap<String,String>();
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = kylbxService.getObjectById(pd,pd.getString("dwbh"));
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
			mv.addObject("dwb", map);
			mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.addObject("zl",pd.getString("zl"));
		mv.setViewName("wsbx/wdbx/kylbx_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 复核
	 */
//	@RequestMapping(value="/goFhPage")
//	public int goFhPage(){
//		PageData pd = this.getPageData();
//		String dwbh = pd.getString("dwbh");
//		ModelAndView mv = this.getModelAndView();
//		return kylbxService.goFhPage(pd,dwbh);
//		
//	}
	
	@RequestMapping(value="/goFhPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void doPublish(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		kylbxService.goFhPage(pd,dwbh);
//		if(kylbxService.goFhPage(pd,dwbh)){
//			return MessageBox.show(true, "发布成功！");
//		}else {
//			return MessageBox.show(false, "发布失败！");
//		}	
	}
	/**
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_DWB dwb){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String dwbh = pd.getString("guid");
		int result=0;
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		dwb.setCzr(loginId);
		if("C".equals(operateType))//新增
		{  
			//生成单位编号
			String guid =this.get32UUID();//生成主键id
			dwb.setGuid(guid);
			dwb.setDwbh(AutoKey.createDwbh("GX_SYS_DWB", "dwbh", "6"));
			result = kylbxService.doAdd(pd);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = kylbxService.doUpdate(pd,dwbh);
			if(result==1)
			{
				return "{success:'true',msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
	        return	MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		//删除单位时验证该单位下是否有人员或下级单位或资产
    	int k = kylbxService.doDelete(dwbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	
	//导出Excel
	@RequestMapping(value="/expExcelwdbx",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcelwdbx(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		String zl = pd.getString("treedwbh");
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		String dlr= LUser.getRybh();
		String rybhAndxm= LUser.getXm();
		if("3".equals(zl)){
			sqltext.append("select distinct* from (select t.shzt,t.guid,t.djbh,'' as FJZZS,t.BXRY as bxrmc,((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.BXRY) ) as bxr,"
					+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,"
					+ "to_char(t.CZRQ,'yyyy-mm-dd')as czrq ,'公务接待报销' as lx from Cw_gwjdfbxzb t where t.bxry='"+rybhAndxm+"')k where 1=1");
		}else if("2".equals(zl)){
			sqltext.append("select distinct* from (select t.shzt,t.guid,(select x.guid from cw_xmjbxxb x where x.guid=t.xmmc)as xmmc ,t.djbh,t.sqr as bxr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.sqr) as bxrmc,"
					+ "((select r.xm from gx_sys_ryb r where r.guid=t.sqr) ) as bxrmc1,"
					+ "t.kssj,t.jssj,t.cfdd,t.FJZZS,to_char(T.bxzje,'FM9999999999999990.00')as bxzje ,( '(' || (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr) || ')' || (select d.mc"
					+ " from gx_sys_dwb d where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr))) as szbmmc,"
					+ "t.CZRQ as czrq ,'差旅费报销'as lx,ktmc,t.ccywguid  from Cw_clfbxzb t  where t.sqr=(select ry.guid from gx_sys_ryb ry where ry.rybh='"+dlr+"'))k where 1=1");
		}

		else if("1".equals(zl)){
			sqltext.append("select distinct* from (select t.shzt, t.guid, t.djbh, t.xmmc, to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r"
					+ " where r.guid = t.bxr)) as bxrmc, t.szbm as szbm, ('(' || t.szbm || ')' ||(select d.mc from gx_sys_dwb d where d.dwbh = t.szbm)) as szbmmc,"
					+ " to_char(t.fjzzs), to_char(T.bxzje, 'FM9999999999999990.00') as bxzje, to_char(czrq, 'yyyy-mm-dd') as czrq,'日常报销' as lx from Cw_rcbxzb t"
					+ " where t.bxr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))k where 1=1 ");
		}else{
			sqltext.append("select distinct* from ( (select t.shzt, t.guid, t.djbh, t.xmmc, to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r"
					+ " where r.guid = t.bxr)) as bxrmc, t.szbm as szbm, ('(' || t.szbm || ')' ||(select d.mc from gx_sys_dwb d where d.dwbh = t.szbm)) as szbmmc,"
					+ " to_char(t.fjzzs), to_char(T.bxzje, 'FM9999999999999990.00') as bxzje, to_char(czrq, 'yyyy-mm-dd') as czrq,'日常报销' as lx,'' ccywguid from Cw_rcbxzb t"
					+ " where t.bxr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"')) "
					+ " union "
					+ " (select t.shzt, t.guid,t.djbh,(select x.guid from cw_xmjbxxb x where x.guid = t.xmmc) as xmmc,"
					+ " to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r  where r.guid = t.sqr)) as bxrmc, '' as szbm, (select d.mc from gx_sys_dwb d"
					+ " where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr)) as szbmmc,  t.FJZZS, to_char(T.bxzje, 'FM9999999999999990.00') as bxzje,"
					+ " t.CZRQ as czrq, '差旅费报销' as lx,t.ccywguid ccywguid from Cw_clfbxzb t where t.sqr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))"
					+ " union "
					+ " (select t.shzt, t.guid, t.djbh, to_char(t.BXRY) as bxrmc,''xmmc, t.szbm, ('(' || t.szbm || ')' || (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,"
					+ " '' as FJZZS, to_char(T.bxje, 'FM9999999999999990.00') as BXZJE, to_char(t.CZRQ, 'yyyy-mm-dd') as czrq, '公务接待报销' as lx,'' ccywguid"
					+ " from Cw_gwjdfbxzb t  where t.bxry = '"+rybhAndxm+"') )k where 1=1");
		}

		String djbh = pd.getString("djbh");
		String bxrmc = pd.getString("bxrmc");
		if(Validate.noNull(djbh)){
			sqltext.append(" and t.djbh like '%" + djbh + "%'");
		}
		if(Validate.noNull(bxrmc)){
			sqltext.append(" and t.bxrmc like '%" + bxrmc + "%'");
		}
		String id = pd.getString("id");
		
		if(Validate.noNull(id)){
			sqltext.append(" and k.guid in ('"+id+"') ");
		}
		System.err.println("+++++"+sqltext.toString());
		
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();

		m = new M_largedata();
		m.setName("DJBH");
		m.setShowname("单据编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("BXRMC");
		m.setShowname("报销人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SZBMMC");
		m.setShowname("所在部门");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("BXZJE");
		m.setShowname("报销总结额（元）");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("CZRQ");
		m.setShowname("报销日期");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("LX");
		m.setShowname("类型");
		mlist.add(m);
		m = null;
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}

	@RequestMapping("/expExcelwdbx2")
	@ResponseBody
	public Object KylbxInfo() {
		PageData pd = this.getPageData();
		String guid = pd.getString("id");
		String dlr= LUser.getRybh();
		String rybhAndxm= LUser.getXm();
		String searchValue = pd.getString("searchJson");
		String shzt = pd.getString("shzt");
		String zl = pd.getString("treedwbh");
		System.err.println("expExcelwdbx2+zl==="+zl);
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select * from  ");
		if("3".equals(zl)){
			sqltext.append(" (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt,t.guid,t.djbh,'' as FJZZS,t.BXRY as bxr,((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.BXRY) ) as bxrmc,"
					+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999999990.00') as BXZJE,"
					+ "to_char(t.CZRQ,'yyyy-mm-dd')as czrq ,'公务接待报销' as lx from Cw_gwjdfbxzb t )k ");//where t.bxry='"+dlr+"'
		}else if("2".equals(zl)){
			sqltext.append(" (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt,t.guid,(select x.guid from cw_xmjbxxb x where x.guid=t.xmmc)as xmmc ,t.djbh,t.sqr as bxr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.sqr) as bxrmc,"
					+ "((select r.xm from gx_sys_ryb r where r.guid=t.sqr) ) as bxrmc1,"
					+ "t.kssj,t.jssj,t.cfdd,t.FJZZS,to_char(T.bxzje,'FM9999999999999999990.00')as bxzje ,( '(' || (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr) || ')' || (select d.mc"
					+ " from gx_sys_dwb d where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr))) as szbmmc,"
					+ "t.CZRQ as czrq ,'差旅费报销'as lx,ktmc,t.ccywguid  from Cw_clfbxzb t  where t.sqr=(select ry.guid from gx_sys_ryb ry where ry.rybh='"+dlr+"'))k ");
		}
//		else{
//			tablename.append(" ( select t.shzt,t.guid,t.djbh,t.bxr,t.xmmc,((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,t.szbm as szbm,"
//					+ " ('(' || t.szbm || ')' ||(select d.mc from gx_sys_dwb d where d.dwbh=t.szbm))as szbmmc,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00')as bxzje ,"
//					+ "to_char(czrq,'yyyy-mm-dd')as czrq ,'日常报销'as lx from Cw_rcbxzb t  where t.bxr = (select ry.guid from gx_sys_ryb ry where ry.rybh='"+dlr+"' ))k ");
//		}
		else if("1".equals(zl)){
			sqltext.append(" (select t.procinstid,t.shzt,t.checkshzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.guid, t.djbh, t.xmmc, to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r"
					+ " where r.guid = t.bxr)) as bxrmc, t.szbm as szbm, ('(' || t.szbm || ')' ||(select d.mc from gx_sys_dwb d where d.dwbh = t.szbm)) as szbmmc,"
					+ " to_char(t.fjzzs), to_char(T.bxzje, 'FM9999999999999999990.00') as bxzje, to_char(czrq, 'yyyy-mm-dd') as czrq,'日常报销' as lx from Cw_rcbxzb t"
					+ " where t.bxr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))k ");
		}else{
			sqltext.append(" ( (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt,t.guid, t.djbh, t.xmmc, to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r"
					+ " where r.guid = t.bxr)) as bxrmc, t.szbm as szbm, ('(' || t.szbm || ')' ||(select d.mc from gx_sys_dwb d where d.dwbh = t.szbm)) as szbmmc,"
					+ " to_char(t.fjzzs), to_char(T.bxzje, 'FM9999999999999999990.00') as bxzje, to_char(czrq, 'yyyy-mm-dd') as czrq,'日常报销' as lx,'' ccywguid from Cw_rcbxzb t"
					+ " where t.bxr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))  "
					+ " union "
					+ " (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt, t.guid,t.djbh,(select x.guid from cw_xmjbxxb x where x.guid = t.xmmc) as xmmc,"
					+ " to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r  where r.guid = t.sqr)) as bxrmc, '' as szbm,( '(' || (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr) || ')' || (select d.mc\"\r\n" + 
					"					+ \" from gx_sys_dwb d where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr))) as szbmmc,  t.FJZZS, to_char(T.bxzje, 'FM9999999999999999990.00') as bxzje,"
					+ " t.CZRQ as czrq, '差旅费报销' as lx,t.ccywguid ccywguid from Cw_clfbxzb t where t.sqr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))"
					+ " union "
					+ " (select t.procinstid,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) shztmc,t.checkshzt, t.guid, t.djbh,''xmmc, to_char((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.BXRY)) as bxrmc, t.szbm, ('(' || t.szbm || ')' || (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,"
					+ " '' as FJZZS, to_char(T.bxje, 'FM9999999999999999990.00') as BXZJE, to_char(t.CZRQ, 'yyyy-mm-dd') as czrq, '公务接待报销' as lx,'' ccywguid"
					+ " from Cw_gwjdfbxzb t  ) )k ");//where t.bxry = '"+dlr+"'
		}
		sqltext.append(" where 1=1 ");
		if("00".equals(shzt)){
			sqltext.append(" AND checkshzt IN('00')");
		}else if("11".equals(shzt)){
			sqltext.append(" AND checkshzt IN('11')");
		}else if("99".equals(shzt)){
			sqltext.append(" AND checkshzt IN('99')");
		}
		if(Validate.noNull(guid)){
			sqltext.append(" and k.guid in ('"+guid.trim()+"') ");
		}
		
		
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.kylbxService.expExcelwdbx2(realfile, shortfileurl, guid,searchValue,sqltext.toString());
	}
	//导出Excel
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
			//查询数据的sql语句
			String searchJson = pd.getString("searchJson");
			StringBuffer sqltext = new StringBuffer();
			String zl = pd.getString("treedwbh");
			String rybh = LUser.getRybh();
			String loginId = xsxxService.getLoginIdByRybh(rybh);
			if("3".equals(zl)){
				sqltext.append("select t.guid,t.djbh,'' as FJZZS,t.BXRY as bxrmc,"
						+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,"
						+ "to_char(t.CZRQ,'yyyy-mm-dd')as czrq ,'公务接待报销' as lx,t.shzt,"
						+ "(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc from Cw_gwjdfbxzb t where t.shzt = '8' and t.BXRY like '%"+rybh+"%'");
			}else if("1".equals(zl)){
				sqltext.append(" select t.guid,t.djbh,t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid = t.bxr )||')'||(select r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,"
						+ "('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
						+ "t.szbm,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00')as bxzje,"
						+ "to_char(czrq,'yyyy-mm-dd')as czrq ,'日常报销'as lx,t.shzt,"
						+ "(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc from Cw_rcbxzb t where t.sfkylbx='0' and t.shzt = '8' and t.bxr like '%"+loginId+"%' ");
			}else{
				
				sqltext.append("select * from ((select t.guid, t.djbh, '' as FJZZS, to_char(t.BXRY)  as bxrmc,"
						+ " ('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
						+ " to_char(T.bxje, 'FM9999999999999990.00') as BXZJE, t.szbm, to_char(t.CZRQ, 'yyyy-mm-dd') as czrq,"
						+ " '公务接待报销' as lx,t.shzt,(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc"
						+ "  from Cw_gwjdfbxzb t where t.shzt = '8' and t.BXRY like '%"+rybh+"%') union ( select t.guid, t.djbh, to_char(t.fjzzs),"
						+ " to_char('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc,"
						+ " ('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
						+ " to_char(T.bxzje, 'FM9999999999999990.00') as bxzje, t.szbm, to_char(czrq, 'yyyy-mm-dd') as czrq,"
						+ " '日常报销' as lx,t.shzt,(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc from Cw_rcbxzb t where t.sfkylbx = '0' and t.shzt = '8' and t.bxr like '%"+loginId+"%')) k where 1=1");
			}

			String djbh = pd.getString("djbh");
			String bxrmc = pd.getString("bxrmc");
			if(Validate.noNull(djbh)){
				sqltext.append(" and t.djbh like '%" + djbh + "%'");
			}
			if(Validate.noNull(bxrmc)){
				sqltext.append(" and t.bxrmc like '%" + bxrmc + "%'");
			}
			String id = pd.getString("id");
			if("3".equals(zl)){
				if(Validate.noNull(id)){
					sqltext.append(" and t.guid in ('"+id+"') ");
				}
			}else if("1".equals(zl)){
				if(Validate.noNull(id)){
					sqltext.append(" and t.guid in ('"+id+"') ");
				}
			}else{
				if(Validate.noNull(id)){
					sqltext.append(" and k.guid in ('"+id+"') ");
				}
			}
			sqltext.append(" order by BXRMC asc");
			System.err.println("+++++"+sqltext.toString());
			
			sqltext.append(ToSqlUtil.jsonToSql(searchJson));
			//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
			List<M_largedata> mlist = new ArrayList<M_largedata>();
			M_largedata m = new M_largedata();
			m.setName("SHZTMC");
			m.setShowname("审核状态");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("DJBH");
			m.setShowname("单据编号");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("BXRMC");
			m.setShowname("报销人");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("SZBMMC");
			m.setShowname("所在部门");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("BXZJE");
			m.setShowname("报销总结额（元）");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("CZRQ");
			m.setShowname("报销日期");
			mlist.add(m);
			m = null;

			m = new M_largedata();
			m.setName("LX");
			m.setShowname("类型");
			mlist.add(m);
			m = null;
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
			String searchJson = pd.getString("searchJson");
			StringBuffer sqltext = new StringBuffer();
			String zl = pd.getString("treedwbh");
			String loginId = xsxxService.getLoginIdByRybh(rybh);
			if("3".equals(zl)){
				sqltext.append("select t.guid,t.djbh,'' as FJZZS,t.BXRY as bxrmc,"
						+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,"
						+ "to_char(t.CZRQ,'yyyy-mm-dd')as czrq ,'公务接待报销' as lx,t.shzt,"
						+ "(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc from Cw_gwjdfbxzb t where t.shzt = '8' and t.BXRY like '%"+rybh+"%'");
			}else if("1".equals(zl)){
				sqltext.append(" select t.guid,t.djbh,t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid = t.bxr )||')'||(select r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,"
						+ "('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
						+ "t.szbm,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00')as bxzje,"
						+ "to_char(czrq,'yyyy-mm-dd')as czrq ,'日常报销'as lx,t.shzt,"
						+ "(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc from Cw_rcbxzb t where t.sfkylbx='0' and t.shzt = '8' and t.bxr like '%"+loginId+"%' ");
			}else{
				
				sqltext.append("select * from ((select t.guid, t.djbh, '' as FJZZS, to_char(t.BXRY)  as bxrmc,"
						+ " ('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
						+ " to_char(T.bxje, 'FM9999999999999990.00') as BXZJE, t.szbm, to_char(t.CZRQ, 'yyyy-mm-dd') as czrq,"
						+ " '公务接待报销' as lx,t.shzt,(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc"
						+ "  from Cw_gwjdfbxzb t where t.shzt = '8' and t.BXRY like '%"+rybh+"%') union ( select t.guid, t.djbh, to_char(t.fjzzs),"
						+ " to_char('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc,"
						+ " ('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
						+ " to_char(T.bxzje, 'FM9999999999999990.00') as bxzje, t.szbm, to_char(czrq, 'yyyy-mm-dd') as czrq,"
						+ " '日常报销' as lx,t.shzt,(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc from Cw_rcbxzb t where t.sfkylbx = '0' and t.shzt = '8' and t.bxr like '%"+loginId+"%')) k where 1=1");
			}

			String djbh = pd.getString("djbh");
			String bxrmc = pd.getString("bxrmc");
			if(Validate.noNull(djbh)){
				sqltext.append(" and t.djbh like '%" + djbh + "%'");
			}
			if(Validate.noNull(bxrmc)){
				sqltext.append(" and t.bxrmc like '%" + bxrmc + "%'");
			}
			String id = pd.getString("id");
			if("3".equals(zl)){
				if(Validate.noNull(id)){
					sqltext.append(" and t.guid in ('"+id+"') ");
				}
			}else if("1".equals(zl)){
				if(Validate.noNull(id)){
					sqltext.append(" and t.guid in ('"+id+"') ");
				}
			}else{
				if(Validate.noNull(id)){
					sqltext.append(" and k.guid in ('"+id+"') ");
				}
			}
			sqltext.append(" order by BXRMC asc");
			System.err.println("+++++"+sqltext.toString());
			
			sqltext.append(ToSqlUtil.jsonToSql(searchJson));
			String guid = pd.getString("guid");
			String searchValue = pd.getString("searchJson");
			String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
			String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
			return this.kylbxService.expExcel(realfile, shortfileurl, guid,searchValue,rybh,sqltext.toString());
		}
		
	/**
	 *  导入上传
	 */
	@RequestMapping(value="/uploadt")
	public ModelAndView Uploadt(MultipartFile imageFile) throws IllegalStateException, IOException{
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
    	String pictureFile_name =  imageFile.getOriginalFilename();
		String newFileName = this.get32UUID()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
		String realPath = this.getRequest().getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
		//上传文件
		String file = realPath+"WEB-INF/file/excel/"+newFileName;
		File uploadPic = new File(file);
		if(!uploadPic.exists()){
			uploadPic.mkdirs();
		}
		//向磁盘写文件
		imageFile.transferTo(uploadPic);
		List listbt = new ArrayList();
		listbt = kylbxService.insertJcsj(file);
		mv.addObject("listbt", listbt);
		mv.addObject("file", file);
		String pname = pd.getString("pname");
		mv.addObject("bool","true");
		mv.addObject("pname",pname);
 		mv.setViewName("fzgn/jcsz/jsxxsz/txl_imp");
		return mv;
	}
	@RequestMapping(value="/singlePrintwdbxByMe")
	public ModelAndView singlePrintwdbxByMe(HttpSession session){
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = "'"+pd.getString("guid")+"'";
		String rcbxguid = pd.getString("guid");
		String lx = pd.getString("lx");
		String procinstid = pd.getString("procinstid");
		System.out.println("lx--------"+lx);
		Map<?,?> list = kylbxService.getSinglePrintInfoByIds(guid);//查询打印数据
		Map yxMap;
		Map bmMap;
		Map kjMap;
		List jjMap;
		
		Map map = kylbxService.getPrintInfoById(guid);//查询打印数据
		List list1 = processService.findBljlOfTg(procinstid);
		List list2 = processService.findBljlOfSqr(procinstid);
		System.out.println("list1-----------"+list1);
		System.out.println("list2-----------"+list2);
		boolean flag=false;
		for (int i = 0; i < list1.size(); i++) {
			Map yzsjMap = (Map)list1.get(i); 
			String yzsj = yzsjMap.get("TASKNAME")+"";
			if(yzsj.contains("书记和院长")){
				flag=true;
			}
		}
		if(flag){
			Map yzsjMaps =  processService.findYzAndSj(procinstid);
			String name = yzsjMaps.get("XM")+"";
			String time = yzsjMaps.get("STARTTIME")+"";
			mv.addObject("name",name);
			mv.addObject("time",time);
		}
		mv.addObject("flag",flag);
		//---------------------------------------------------
		
		//--------------------------------------------------------------
		mv.addObject("guid", guid);
		mv.addObject("printinfolists", list1);
		mv.addObject("size",list1.size());
		mv.addObject("printinfolistsqr", list2);
		mv.addObject("organizationname1",map);
		if("公务接待报销".equals(lx)) {
			
			Map<?, ?> sqsp = kylbxService.getSqsp(guid);
			String sqspid = String.valueOf(sqsp.get("sqspbid")) ;
			System.out.println("sqspid------------"+sqspid);
			Map gwjdfsq = gwjdsqService.getObjectById(sqspid);
			mv.addObject("gwjdfsq",gwjdfsq);
			System.out.println("gwjdfsq-------------"+gwjdfsq);
			List mxList = gwjdsqService.getMxList(sqspid);
			mv.addObject("mxList",mxList);
			
			List yjList1 = gwjdsqService.getYjList1(sqspid);
			List yjList2 = gwjdsqService.getYjList2(sqspid);
			mv.addObject("yjList1",Validate.isNull(yjList1)?null:yjList1.get(0));
			mv.addObject("yjList2",Validate.isNull(yjList2)?null:yjList2.get(0));
			mv.addObject("title","山东农业工程学院公务接待审批单");

			 yxMap = rcbxService.getPrintGWJDYx(rcbxguid);
			 String[] fjxx = fileService.getFjList((String)yxMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView",fjxx[0].replace("'", ""));
			 mv.addObject("fjConfig",fjxx[1]);
			 bmMap = rcbxService.getPrintGWJDBm(rcbxguid);
			 String[] fjxx2 = fileService.getFjList((String)bmMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView2",fjxx2[0].replace("'", ""));
			 mv.addObject("fjConfig2",fjxx2[1]);
			 kjMap = rcbxService.getPrintGWJDKj(rcbxguid);
			 String[] fjxx3 = fileService.getFjList((String)kjMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView3",fjxx3[0].replace("'", ""));
			 mv.addObject("fjConfig3",fjxx3[1]);
			 mv.addObject("yxMap",yxMap);
			 mv.addObject("bmMap",bmMap);
			 mv.addObject("kjMap",kjMap);
			//添加打印参数
			mv.addObject("dyzdbj", CommonUtil.getDyzdbj());//装订边距
			mv.addObject("ymkd", 297-19-CommonUtil.getDyzdbj());//页面宽度
			mv.addObject("ztdkd", 297+6-CommonUtil.getDyzdbj());//粘贴单宽度
			mv.addObject("txmbj", 297-79-CommonUtil.getDyzdbj());//条形码边距
			 mv.setViewName("wsbx/wdbx/wdbx_print");
		}else if("日常报销".equals(lx)) {
			 jjMap = rcbxService.getPrintJj(rcbxguid);
			 yxMap = rcbxService.getPrintYx(rcbxguid);
			 List xmhxlist = rcbxService.getXmhxList(rcbxguid);
			 System.err.println((String)yxMap.get("RYBH"));
			 String[] fjxx = fileService.getFjList((String)yxMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView",fjxx[0].replace("'", ""));
			 mv.addObject("fjConfig",fjxx[1]);
			 bmMap = rcbxService.getPrintBm(rcbxguid);
			 String[] fjxx2 = fileService.getFjList((String)bmMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView2",fjxx2[0].replace("'", ""));
			 mv.addObject("fjConfig2",fjxx2[1]);
			 kjMap = rcbxService.getPrintKj(rcbxguid);
			 String[] fjxx3 = fileService.getFjList((String)kjMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView3",fjxx3[0].replace("'", ""));
			 mv.addObject("fjConfig3",fjxx3[1]);
			 int length = jjMap.size()+1;
			 mv.addObject("xmhxlist",xmhxlist);
			 mv.addObject("jjMap",jjMap);
			 mv.addObject("length",length);
			 mv.addObject("yxMap",yxMap);
			mv.addObject("bmMap",bmMap);
			mv.addObject("kjMap",kjMap);
			//添加打印参数
			mv.addObject("dyzdbj", CommonUtil.getDyzdbj());//装订边距
			mv.addObject("ymkd", 297-19-CommonUtil.getDyzdbj());//页面宽度
			mv.addObject("ztdkd", 297+6-CommonUtil.getDyzdbj());//粘贴单宽度
			mv.addObject("txmbj", 297-79-CommonUtil.getDyzdbj());//条形码边距
			mv.setViewName("wsbx/wdbx/wdbx_printrc");
		}else{
			 jjMap = rcbxService.getPrintJj(rcbxguid);
			 yxMap = rcbxService.getPrintYxclf(rcbxguid);
			 System.err.println((String)yxMap.get("RYBH"));
			 String[] fjxx = fileService.getFjList((String)yxMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView",fjxx[0].replace("'", ""));
			 mv.addObject("fjConfig",fjxx[1]);
			 bmMap = rcbxService.getPrintBmclf(rcbxguid);
			 String[] fjxx2 = fileService.getFjList((String)bmMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView2",fjxx2[0].replace("'", ""));
			 mv.addObject("fjConfig2",fjxx2[1]);
			 kjMap = rcbxService.getPrintKjclf(rcbxguid);
			 String[] fjxx3 = fileService.getFjList((String)kjMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			 mv.addObject("fjView3",fjxx3[0].replace("'", ""));
			 mv.addObject("fjConfig3",fjxx3[1]);
			 int length = jjMap.size()+1;
			 mv.addObject("jjMap",jjMap);
			 mv.addObject("length",length);
			 mv.addObject("yxMap",yxMap);
			mv.addObject("bmMap",bmMap);
			mv.addObject("kjMap",kjMap);
			mv.setViewName("wsbx/wdbx/wdbx_printcl");
			
		}
        SimpleDateFormat dfs=new SimpleDateFormat("yyyy-MM-dd");
        String organizationname=session.getAttribute("organizationname")+"";
        String time=dfs.format(new Date());
        //String url = "wsbx/wdbx/PrintSample441";
		mv.addObject("guid", guid);
		mv.addObject("printinfolist", list);
		mv.addObject("time",time);
		mv.addObject("organizationname",organizationname);
		//mv.setViewName(url);
		return mv;
	} 
	@RequestMapping(value="/PrintwdbxByMe")
	public ModelAndView PrintwdbxByMe(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String procinstid = pd.getString("procinstid");
		Map map = kylbxService.getPrintInfoById(guid);//查询打印数据
		List list = processService.findBljlOfTg(procinstid);
		mv.addObject("guid", guid);
		mv.addObject("printinfolist", list);
		mv.addObject("organizationname2",map);
		mv.setViewName("wsbx/wdbx/wdbx_print");
		return mv;
	}
}




