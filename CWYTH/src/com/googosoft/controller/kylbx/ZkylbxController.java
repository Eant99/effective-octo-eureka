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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.kylbx.ZkylbxService;
import com.googosoft.service.wsbx.CcbxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value ="/zkylbx")
public class ZkylbxController extends BaseController {
	

	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	@Resource(name="zkylbxService")
	private ZkylbxService zkylbxService;//单例

	@Resource(name="dictService")
	DictService dictService;
	
	@Resource(name = "ccbxService")
	private CcbxService ccbxService;
	
	@Resource(name="fileService")
	FileService fileService;
	
	/*
	 * 科研类报销树(new)
	 */
	@RequestMapping(value="/kylbxWindow")
	public ModelAndView goDwxxPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/wdbx/zbmysbzTree");
		return mv;
	}
	/*
	 * 报销业务查询（树）
	 */
	@RequestMapping(value="/bxywcxWindow")
	public ModelAndView gobxywcxTreePage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/wdbx/bxywcxTree");
		return mv;
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
		mv.setViewName("wsbx/wdbx/bxywcx_list");
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
			return zkylbxService.getYsLx(pd, rootPath);
		} else {
			return "";
		}
	} 
	
	/*
	 * 我的报销
	 */
	@RequestMapping(value = "/wdbx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wdbxTree(HttpServletResponse response)
			throws java.io.IOException {
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		if ("get-ys".equals(menu)) {
			return zkylbxService.getYsLx2(pd, rootPath);
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
				return zkylbxService.getKmszNodezj(pd,"1",rootPath);
			}else{
			    return zkylbxService.getKmszNodezj(pd,jb,rootPath);
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
		System.err.println("_____"+dwbh);
		mv.setViewName("wsbx/wdbx/zkylbx_list");
		mv.addObject("dwbh",dwbh);
		return mv;

	}
	/**
	 * 单个打印
	 * @return
	 */
	@RequestMapping(value="/singlePrint")
	public ModelAndView singlePrint(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String zbid = pd.getString("guid");
		
	//	String guid = "'"+pd.getString("guid")+"'";
		String guid = pd.getString("guid");
		String dm = pd.getString("dm");
		String lx = pd.getString("lx");
		System.err.println("___dm____"+dm+"_____guid____"+guid+"______"+lx);
		String url = "wsbx/gwjdf/PrintSampleclf";
		if("2".equals(dm)||"差旅费报销".equals(lx)){
			Map map = ccbxService.getZbPage(zbid);
//			List<Map<String, Object>> ryList = ccbxService.getRyListByZbguid(zbid);		
			Map<?,?> list = zkylbxService.getSinglePrintInfoByIds(guid);//差旅费表1-1数据
			List list22 = zkylbxService.getSinglePrintInfoByIds2(guid);//差旅费表2-1打印数据
			List list3 = zkylbxService.getSinglePrintInfoByIds3(guid);//差旅费表2-2打印数据
			List<Map<String, Object>> mxList = ccbxService.getMxListByZbguid(zbid);//差旅费表3-1
			List<Map<String, Object>> mxList2 = ccbxService.getMxListByZbguid2(zbid);//差旅费表3-2getMapByguid
			Map<?,?> map2 = ccbxService.getMapByguid(zbid);//查询总报销金额
			List<Map<String, Object>> mxList4 = ccbxService.getMxListByZbguid4(zbid);//差旅费表4-2
			//科研处意见
			Map kycymap = zkylbxService.getPrintYxByKyc(guid);//
			String kyc = rcbxService.getjsxm((String)kycymap.get("RYBH"));
			String[] fjxx1 = fileService.getFjList((String)kycymap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView1",fjxx1[0].replace("'", ""));
			mv.addObject("fjConfig1",fjxx1[1]);
			mv.addObject("kyc",kyc);
			mv.addObject("kycymap",kycymap);
			
			
			mv.addObject("clfbxzb",map);
			mv.addObject("zbMap",map);
//			mv.addObject("ryList",ryList);
			mv.addObject("printinfolist", list);//差旅费表1-1数据
			mv.addObject("list22",list22);//差旅费表2-1打印数据
			mv.addObject("list3",list3);//差旅费表2-2打印数据
			mv.addObject("mxList",mxList);//差旅费表3-1打印数据
			mv.addObject("map2",map2);//
			mv.addObject("mxList2",mxList2);//差旅费表3-2打印数据
			mv.addObject("mxList4",mxList4);//差旅费表4-2打印数据
			url =  "wsbx/gwjdf/PrintSampleclf";
		}else if("1".equals(dm)||"日常报销".equals(lx)){
			List listkyrc = zkylbxService.getKyrcSinglePrintInfoByIds(guid);//日常表1-1数据
			System.out.println("listkyrc========"+listkyrc);
			List listkyrc2 = zkylbxService.getKyrcSinglePrintInfoByIds2(guid);//日常表1-2数据
			System.out.println("listkyrc2========"+listkyrc2);
//			Map<?,?> map2 = ccbxService.getMapByguid(zbid);//查询总报销金额
			Map<?,?> map3 = ccbxService.getMapByguid2(zbid);//查询总报销金额
//			mv.addObject("map2",map2);//
			mv.addObject("map3",map3);//
			mv.addObject("infokyrc",listkyrc);
			mv.addObject("listkyrc2",listkyrc2);
			Map list2 = zkylbxService.getClSinglePrintInfoByIds(guid);//日常表2打印数据
			mv.addObject("printinfolist",list2);
			Map yxMap = rcbxService.getPrintYx(guid);
			Map bmMap = rcbxService.getPrintBm(guid);
			Map kjMap = rcbxService.getPrintKj(guid);
			mv.addObject("yxMap", yxMap);
			String[] fjxx = fileService.getFjList((String)yxMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0].replace("'", ""));
			mv.addObject("fjConfig",fjxx[1]);
			mv.addObject("bmMap", bmMap);
			String[] fjxx2 = fileService.getFjList((String)bmMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView2",fjxx2[0].replace("'", ""));
			mv.addObject("fjConfig2",fjxx2[1]);
			mv.addObject("kjMap", kjMap);
			String[] fjxx3 = fileService.getFjList((String)kjMap.get("RYBH"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView3",fjxx3[0].replace("'", ""));
			mv.addObject("fjConfig3",fjxx3[1]);
//			List list22 = zkylbxService.getSinglePrintInfoByIds2(guid);//日常表2打印数据
			url = "wsbx/gwjdf/PrintSamplerc";
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(new Date());
		mv.addObject("time",time);
		mv.addObject("guid", guid);
		mv.setViewName(url);
		return mv;
	}
	
	@RequestMapping(value = "/gobxywcxListPage")
	public ModelAndView gobxywcxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dm");
		mv.setViewName("wsbx/wdbx/bxywcx_list");
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
		sqltext.append(" * ");
		if("1".equals(zl)){
			tablename.append(" ( select t.guid,t.djbh,t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,"
					+ "(t.szbm || (select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,"
//					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ "t.szbm,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00')as BXZJE,"
					+ "to_char(czrq,'yyyy-mm-dd')as CZRQ,'日常报销'as lx from Cw_rcbxzb t where t.sfkylbx='02' and t.shzt='8')k ");
		}else if("2".equals(zl)){
			tablename.append(" (select t.guid,t.djbh,t.sqr as bxr,(select r.xm from gx_sys_ryb r where r.guid=t.sqr) as bxrmc1,"
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,"
//					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ "('('||(select r.rybh from gx_sys_ryb r where r.guid=t.sqr)||')'||(select r.xm from gx_sys_ryb r where r.guid=t.sqr) ) as bxrmc,"
					+ "t.kssj,t.jssj,t.cfdd,t.FJZZS,to_char(T.bxzje,'FM9999999999999990.00')as BXZJE, (select '('|| d.bmh || ')'|| d.mc from gx_sys_dwb d where d.dwbh=(select r.dwbh from gx_sys_ryb r where r.guid= t.sqr)) as szbmmc,"
					+ "t.CZRQ,'差旅费报销'as lx  from Cw_clfbxzb t where t.sfkylbx='1' and t.shzt='8' )k ");
		}else{
			tablename.append(" ((select t.guid,t.djbh, t.sqr as bxr, ('('||(select r.rybh from gx_sys_ryb r where r.guid=t.sqr)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.sqr)) as bxrmc,"
					+ " (SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,"
//					+ " (SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ " to_char(t.FJZZS) as FJZZS, to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE, to_char((select '('|| d.bmh || ')'|| d.mc from gx_sys_dwb d where d.dwbh=(select  r.dwbh from gx_sys_ryb r where r.guid= t.sqr))) as szbmmc, t.CZRQ, '差旅费报销' as lx"
					+ " from Cw_clfbxzb t where t.sfkylbx = '1' and t.shzt='8') union (select t.guid,  t.djbh, t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc, "
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,to_char(t.fjzzs) as FJZZS,"
//					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,to_char(t.fjzzs) as FJZZS,"
					+ " to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE, to_char( t.szbm || (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,"
					+ " to_char(czrq, 'yyyy-mm-dd') as CZRQ, '日常报销' as lx from Cw_rcbxzb t   where t.sfkylbx = '02' and t.shzt='8'))k ");
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
		pageList.setStrWhere(" and k.shztmc = '审核通过'"); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/*
	 * 报销业务查询
	 */
	@RequestMapping(value = "/getbxywcxPageList")
	@ResponseBody
	public Object getbxywcxPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt1"), "all");
		String zl = pd.getString("treedwbh");
		String lid = LUser.getGuid();
		sqltext.append(" * ");
		if("3".equals(zl)){
			tablename.append(" (select t.guid,'' as ccywguid,t.PROCINSTID as PROCINSTID,t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) as shztmc,t.djbh,'' as FJZZS,'('||r.rybh||')'||r.xm  as bxrmc,"
//					tablename.append(" (select t.guid,'' as ccywguid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm = t.shzt) as shztmc,t.djbh,'' as FJZZS,t.BXRY as bxrmc,((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.BXRY) ) as bxr,"
					+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999999990.00') as BXZJE,"
					+ "to_char(t.CZRQ,'yyyy-mm-dd')as czrq,'公务接待报销' as bxlx from Cw_gwjdfbxzb t left join gx_sys_ryb r on t.BXRYID=r.guid where 1=1 and t.szbm =(select dwbh from gx_sys_ryb where rybh='"+LUser.getRybh()+"') or t.szbm in(select dwbh from zc_sys_rydwqxb where rybh='"+LUser.getRybh()+"'))k "); //and t.bxryid = '"+lid+"'
		}else if("2".equals(zl)){
			tablename.append(" ( select t.guid,t.PROCINSTID as PROCINSTID,t.ccywguid as ccywguid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm=t.shzt)as shztmc,"
//					tablename.append(" ( select t.guid,t.ccywguid as ccywguid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm=t.shzt)as shztmc,"
					+ "  t.djbh, '差旅费报销' as bxlx,  t.sqr as bxr, '('||r.rybh||')'||r.xm  as bxrmc,"
					+ " ((select r.xm from gx_sys_ryb r where r.guid = t.sqr)) as bxrmc1, ('(' || (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr) || ')' ||(select d.mc from gx_sys_dwb d"
					+ " where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr))) as szbmMC, t.CZRQ,  to_char(T.bxzje, 'FM9999999999999999990.00')  AS bxzje"
					+ "  from Cw_clfbxzb t left join gx_sys_ryb r on r.guid = t.sqr where r.dwbh =(select dwbh from gx_sys_ryb where rybh='"+LUser.getRybh()+"') or r.dwbh in(select dwbh from zc_sys_rydwqxb where rybh='"+LUser.getRybh()+"'))k ");//where t.sqr = '"+lid+"' 
		}
		else if("1".equals(zl)){
			tablename.append(" (select t.guid,'' as ccywguid," + 
					"       t.PROCINSTID as PROCINSTID," + 
					"       t.shzt," + 
					"       (select d.mc" + 
					"          from gx_sys_dmb d" + 
					"         where d.zl = 'lcsh'" + 
//					"         where d.zl = 'djshzt'" + 
					"           and d.dm = t.shzt) as shztmc," + 
					"       t.djbh," + 
					"       '日常报销' as bxlx," + 
					"       t.bxr," + 
					"       (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = t.bxr) as bxrmc," + 
					"       to_char('(' || t.szbm || ')' ||(select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc," + 
					"       t.szbm," + 
					"       to_char(t.czrq, 'yyyy-mm-dd') as czrq," + 
					"       to_char(T.bxzje, 'FM9999999999999999990.00') as bxzje," + 
					"       '日常报销' as lx" + 
					"  from Cw_rcbxzb t where 1=1 and t.szbm =(select dwbh from gx_sys_ryb where rybh='"+LUser.getRybh()+"') or t.szbm in(select dwbh from zc_sys_rydwqxb where rybh='"+LUser.getRybh()+"'))k ");//where t.bxr = '"+lid+"'
		}
		else {
			tablename.append("(select t.guid,'' as ccywguid," + 
					"       t.PROCINSTID as PROCINSTID," + 
					"       t.shzt," + 
					"       (select d.mc" + 
					"          from gx_sys_dmb d" + 
					"         where d.zl = 'lcsh'" + 
//					"         where d.zl = 'djshzt'" + 
					"           and d.dm = t.shzt) as shztmc," + 
					"       t.djbh," + 
					"       '日常报销' as bxlx," + 
					"       t.bxr," + 
					"       (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = t.bxr) as bxrmc," + 
					"       to_char('(' || t.szbm || ')' ||(select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc," + 
					"       t.szbm," + 
					"       to_char(czrq, 'yyyy-mm-dd') as czrq," + 
					"       to_char(T.bxzje, 'FM9999999999999999990.00') as bxzje," + 
					"       '日常报销' as lx" + 
					"  from Cw_rcbxzb t where  t.szbm =(select dwbh from gx_sys_ryb where rybh='"+LUser.getRybh()+"') or t.szbm in(select dwbh from zc_sys_rydwqxb where rybh='"+LUser.getRybh()+"')" + 
					/* where t.bxr = '"+lid+"'*/
					" union " + 
					" select m.guid,m.ccywguid as ccywguid," + 
					"       m.PROCINSTID as PROCINSTID," + 
					"       m.shzt," + 
					"       (select d.mc" + 
					"          from gx_sys_dmb d" + 
					"         where d.zl = 'lcsh'" + 
//					"         where d.zl = 'djshzt'" + 
					"          and d.dm = m.shzt) as shztmc, " + 
					"       m.djbh," + 
					"       '差旅费报销' as bxlx," + 
					"       m.sqr as bxr," + 
					"       (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = m.sqr) as bxrmc," + 
					"       to_char((select '('||r.dwbh||')'||r.mc from gx_sys_dwb r where r.dwbh = (select dwbh from gx_sys_ryb where guid = m.sqr))) as szbmmc," + 
					"         '' as szbm," + 
					"        m.CZRQ as czrq," + 
					"       to_char(m.bxzje, 'FM9999999999999999990.00') AS bxzje," + 
					"        '差旅费报销' as lx" + 
					"  from Cw_clfbxzb m left join gx_sys_ryb b on m.sqr=b.guid where b.dwbh =(select dwbh from gx_sys_ryb where rybh='"+LUser.getRybh()+"') or b.dwbh in(select dwbh from zc_sys_rydwqxb where rybh='"+LUser.getRybh()+"')" + 
					/*where m.sqr = '"+lid+"' */
					"   union " +
					" select t.guid," +
					" ''as ccywguid," +
					" t.PROCINSTID as PROCINSTID," +
					" t.shzt," +
					" (select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt)shztmc, " +
//					" select t.guid,''as ccywguid,t.PROCINSTID as PROCINSTID,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm = t.shzt)shztmc, " +
					"	 t.djbh," +
					" '公务接待报销' as bxlx," +
					"  t.BXRY as bxr," +
					"  (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.BXRYID) as bxrmc," + 
					"	 to_char(t.szbm||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm)) as szbmmc," +
					"  '' as szbm," + 
					"	 to_char(t.CZRQ,'yyyy-mm-dd')as czrq," +
					"    to_char(T.bxje,'FM9999999999999999990.00') as BXZJE," +
					"     '公务接待报销' as lx " +
					"" +
					"from Cw_gwjdfbxzb t where 1=1 and t.szbm =(select dwbh from gx_sys_ryb where rybh='"+LUser.getRybh()+"') or t.szbm in(select dwbh from zc_sys_rydwqxb where rybh='"+LUser.getRybh()+"')" + 
					/*and t.bxryid = '"+lid+"'*/
					"	)k");
			
		} // 结束最后一个else
		
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		String strWhere = "";
		if(Validate.noNull(shzt)){
			if("00".equals(shzt)){
				strWhere += " AND SHZTMC IN('未提交','财务预审退回')";
			}else if("11".equals(shzt)){
				strWhere += " AND SHZTMC not in('未提交','财务预审退回','审核通过')";
			}else if("99".equals(shzt)){
				strWhere += " AND SHZTMC in('审核通过')";
			}else{
				strWhere += " AND 1=1";
			}
		}
		pageList.setStrWhere(strWhere);
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",
											pageList.getTotalList().get(0).get("NUM")+"",
											gson.toJson(pageList.getContentList()));
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
			Map<?, ?>  map = zkylbxService.getObjectById(pd,pd.getString("dwbh"));
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
//		return zkylbxService.goFhPage(pd,dwbh);
//		
//	}
	
	@RequestMapping(value="/goFhPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void doPublish(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		zkylbxService.goFhPage(pd,dwbh);
//		if(zkylbxService.goFhPage(pd,dwbh)){
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
			result = zkylbxService.doAdd(pd);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = zkylbxService.doUpdate(pd,dwbh);
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
    	int k = zkylbxService.doDelete(dwbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	
	
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
		String zl = pd.getString("zl");
		if("1".equals(zl)){
			sqltext.append("  select t.guid,t.djbh,t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,"
					+ "(t.szbm || (select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,"
//					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ "t.szbm,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00')as BXZJE,"
					+ "to_char(czrq,'yyyy-mm-dd')as CZRQ,'日常报销'as lx from Cw_rcbxzb t where t.sfkylbx='1' and t.shzt='8' ");
		}else if("2".equals(zl)){
			sqltext.append("select t.guid,t.djbh,t.sqr as bxr,(select r.xm from gx_sys_ryb r where r.guid=t.sqr) as bxrmc1,"
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,"
//					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ "('('||(select r.rybh from gx_sys_ryb r where r.guid=t.sqr)||')'||(select r.xm from gx_sys_ryb r where r.guid=t.sqr) ) as bxrmc,"
					+ "t.kssj,t.jssj,t.cfdd,t.FJZZS,to_char(T.bxzje,'FM9999999999999990.00')as BXZJE, (select '('|| d.bmh || ')'|| d.mc from gx_sys_dwb d where d.dwbh=(select r.dwbh from gx_sys_ryb r where r.guid= t.sqr)) as szbmmc,"
					+ "t.CZRQ,'差旅费报销'as lx  from Cw_clfbxzb t where t.sfkylbx='1' and t.shzt='8' ");
		}else{
			sqltext.append(" select * from ((select t.guid,t.djbh, t.sqr as bxr, ('('||(select r.rybh from gx_sys_ryb r where r.guid=t.sqr)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.sqr)) as bxrmc,"
					+ " (SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,"
//					+ " (SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ " to_char(t.FJZZS) as FJZZS, to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE, to_char((select '('|| d.bmh || ')'|| d.mc from gx_sys_dwb d where d.dwbh=(select  r.dwbh from gx_sys_ryb r where r.guid= t.sqr))) as szbmmc, t.CZRQ, '差旅费报销' as lx"
					+ " from Cw_clfbxzb t where t.sfkylbx = '1' and t.shzt='8') union (select t.guid,  t.djbh, t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc, "
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,to_char(t.fjzzs) as FJZZS,"
//					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,to_char(t.fjzzs) as FJZZS,"
					+ " to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE, to_char( t.szbm || (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,"
					+ " to_char(czrq, 'yyyy-mm-dd') as CZRQ, '日常报销' as lx from Cw_rcbxzb t   where t.sfkylbx = '1' and t.shzt='8'))k where 1=1");
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
		if("1".equals(zl)){
			if(Validate.noNull(id)){
				sqltext.append(" and t.guid in ('"+id+"') ");
			}
		}else if("2".equals(zl)){
			if(Validate.noNull(id)){
				sqltext.append(" and t.guid in ('"+id+"') ");
			}
		}else{
			if(Validate.noNull(id)){
				sqltext.append(" and k.guid in ('"+id+"') ");
			}
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("SHZTMC");
		m.setShowname("审核状态");
		mlist.add(m);
		m = null;
		
	    m = new M_largedata();
		m.setName("djbh");
		m.setShowname("单据编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("FJZZS");
		m.setShowname("单据张数");
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
	
	@RequestMapping("/expExcel5")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String guid = pd.getString("id");
		String zl = pd.getString("treedwbh");
		StringBuffer sqltext = new StringBuffer();
		if("1".equals(zl)){
			sqltext.append("  select t.guid,t.djbh,t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,"
					+ "(t.szbm || (select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,"
//					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ "t.szbm,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00')as BXZJE,"
					+ "to_char(czrq,'yyyy-mm-dd')as CZRQ,'日常报销'as lx from Cw_rcbxzb t where t.sfkylbx='1' and t.shzt='8' ");
		}else if("2".equals(zl)){
			sqltext.append("select t.guid,t.djbh,t.sqr as bxr,(select r.xm from gx_sys_ryb r where r.guid=t.sqr) as bxrmc1,"
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,"
//					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ "('('||(select r.rybh from gx_sys_ryb r where r.guid=t.sqr)||')'||(select r.xm from gx_sys_ryb r where r.guid=t.sqr) ) as bxrmc,"
					+ "t.kssj,t.jssj,t.cfdd,t.FJZZS,to_char(T.bxzje,'FM9999999999999990.00')as BXZJE, (select '('|| d.bmh || ')'|| d.mc from gx_sys_dwb d where d.dwbh=(select r.dwbh from gx_sys_ryb r where r.guid= t.sqr)) as szbmmc,"
					+ "t.CZRQ,'差旅费报销'as lx  from Cw_clfbxzb t where t.sfkylbx='1' and t.shzt='8' ");
		}else{
			sqltext.append(" select * from ((select t.guid,t.djbh, t.sqr as bxr, ('('||(select r.rybh from gx_sys_ryb r where r.guid=t.sqr)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.sqr)) as bxrmc,"
					+ " (SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,"
//					+ " (SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ " to_char(t.FJZZS) as FJZZS, to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE, to_char((select '('|| d.bmh || ')'|| d.mc from gx_sys_dwb d where d.dwbh=(select  r.dwbh from gx_sys_ryb r where r.guid= t.sqr))) as szbmmc, t.CZRQ, '差旅费报销' as lx"
					+ " from Cw_clfbxzb t where t.sfkylbx = '1' and t.shzt='8') union (select t.guid,  t.djbh, t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc, "
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.LCSH+"' AND T.DM=t.SHZT )as shztmc,to_char(t.fjzzs) as FJZZS,"
//					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,to_char(t.fjzzs) as FJZZS,"
					+ " to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE, to_char( t.szbm || (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,"
					+ " to_char(czrq, 'yyyy-mm-dd') as CZRQ, '日常报销' as lx from Cw_rcbxzb t   where t.sfkylbx = '1' and t.shzt='8'))k where 1=1");
		}

		String djbh = pd.getString("djbh");
		String bxrmc = pd.getString("bxrmc");
		if(Validate.noNull(djbh)){
			sqltext.append(" and t.djbh like '%" + djbh + "%'");
		}
		if(Validate.noNull(bxrmc)){
			sqltext.append(" and t.bxrmc like '%" + bxrmc + "%'");
		}
		if("1".equals(zl)){
			if(Validate.noNull(guid)){
				sqltext.append(" and t.guid in ('"+guid+"') ");
			}
		}else if("2".equals(zl)){
			if(Validate.noNull(guid)){
				sqltext.append(" and t.guid in ('"+guid+"') ");
			}
		}else{
			if(Validate.noNull(guid)){
				sqltext.append(" and k.guid in ('"+guid+"') ");
			}
		}
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zkylbxService.expExcel5(realfile, shortfileurl, guid,searchValue,sqltext.toString());
	}
	/*
	 * exp-bxywcx
	 */
	@RequestMapping(value="/expExcelbxywcx",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcelbxywcx(){
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
		String lid = LUser.getGuid();
		if("3".equals(zl)){
			sqltext.append(" select * from (select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) as shztmc,t.djbh,'' as FJZZS,t.BXRY as bxrmc,((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.BXRY) ) as bxr,"
//					sqltext.append(" select * from (select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm = t.shzt) as shztmc,t.djbh,'' as FJZZS,t.BXRY as bxrmc,((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.BXRY) ) as bxr,"
					+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,"
					+ "to_char(t.CZRQ,'yyyy-mm-dd')as czrq,'公务接待报销' as bxlx from Cw_gwjdfbxzb t where 1=1 and t.bxryid = '"+lid+"')k  where 1=1");
		}else if("2".equals(zl)){
			sqltext.append("select * from ( select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm=t.shzt)as shztmc,"
//					sqltext.append("select * from ( select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm=t.shzt)as shztmc,"
					+ "  t.djbh, '差旅费报销' as bxlx,  t.sqr as bxr, (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = t.sqr) as bxrmc,"
					+ " ((select r.xm from gx_sys_ryb r where r.guid = t.sqr)) as bxrmc1, ('(' || (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr) || ')' ||(select d.mc from gx_sys_dwb d"
					+ " where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr))) as szbmMC, t.CZRQ,  to_char(T.bxzje, 'FM9999999999999990.00')  AS bxzje"
					+ "  from Cw_clfbxzb t where t.sqr = '"+lid+"' )k where 1=1 ");
		}
		else if("1".equals(zl)){
			sqltext.append("select * from (  select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm=t.shzt)as shztmc,"
//					sqltext.append("select * from (  select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm=t.shzt)as shztmc,"
					+ " t.djbh, '日常报销' as bxlx, t.bxr, ('(' || (select r.rybh from gx_sys_ryb r where r.guid=t.bxr) || ')' ||(select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc,"
					+ " ((select '('||r.dwbh||')'||r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc, t.szbm, t.fjzzs,"
					+ " to_char(czrq, 'yyyy-mm-dd') as czrq, to_char(T.bxzje, 'FM9999999999999990.00') as bxzje , '日常报销' as lx from Cw_rcbxzb t "
					+ " where t.bxr = '"+lid+"' )k where 1=1");
		}
		else {
			sqltext.append("select * from(select t.guid," + 
					"       t.PROCINSTID as PROCINSTID," + 
					"       t.shzt," + 
					"       (select d.mc" + 
					"          from gx_sys_dmb d" + 
					"         where d.zl = 'lcsh'" + 
//					"         where d.zl = 'djshzt'" + 
					"           and d.dm = t.shzt) as shztmc," + 
					"       t.djbh," + 
					"       '日常报销' as bxlx," + 
					"       t.bxr," + 
					"       ((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc," + 
					"       to_char('(' || t.szbm || ')' ||(select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc," + 
					"       t.szbm," + 
					"       to_char(czrq, 'yyyy-mm-dd') as czrq," + 
					"       to_char(T.bxzje, 'FM9999999999999990.00') as bxzje," + 
					"       '日常报销' as lx" + 
					"  from Cw_rcbxzb t where t.bxr = '"+lid+"'" + 
					" union " + 
					" select m.guid," + 
					"       m.PROCINSTID as PROCINSTID," + 
					"       m.shzt," + 
					"       (select d.mc" + 
					"          from gx_sys_dmb d" + 
					"         where d.zl = 'lcsh'" + 
//					"         where d.zl = 'djshzt'" + 
					"          and d.dm = m.shzt) as shztmc, " + 
					"       m.djbh," + 
					"       '差旅费报销' as bxlx," + 
					"       m.sqr as bxr," + 
					"       (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = m.sqr) as bxrmc," + 
					"       to_char((select '('||r.dwbh||')'||r.mc from gx_sys_dwb r where r.dwbh = (select dwbh from gx_sys_ryb where guid = m.sqr)))," + 
					"         '' as szbm," + 
					"        m.CZRQ as czrq," + 
					"       to_char(m.bxzje, 'FM9999999999999990.00') AS bxzje," + 
					"        '差旅费报销' as lx" + 
					"  from Cw_clfbxzb m where m.sqr = '"+lid+"' " + 
					"   union " +
					" select t.guid,t.PROCINSTID as PROCINSTID,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt)shztmc, " +
//					" select t.guid,t.PROCINSTID as PROCINSTID,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm = t.shzt)shztmc, " +
					"	 t.djbh,'公务接待报销' as bxlx,t.BXRY as bxr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.BXRYID) as bxrmc," + 
					"	 to_char(t.szbm||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm)) as szbmmc,'' as szbm," + 
					"	 to_char(t.CZRQ,'yyyy-mm-dd')as czrq,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,'公务接待报销' as bxlx from Cw_gwjdfbxzb t where 1=1"
					+ " and t.bxryid = '"+lid+"'" + 
					"	)k where 1=1");
			
		}
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "all");
		String djbh = pd.getString("djbh");
		String bxrmc = pd.getString("bxrmc");
		if(Validate.noNull(shzt)){
			sqltext.append(" and k.shzt ='" + shzt + "'");
		}
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
		if("00".equals(shzt)){
			sqltext.append(" AND SHZTMC IN('未提交','财务预审退回')");
		}else if("11".equals(shzt)){
			sqltext.append(" AND SHZTMC not in('未提交','财务预审退回','审核通过')");
		}else if("99".equals(shzt)){
			sqltext.append(" AND SHZTMC in('审核通过')");
		}else{
			sqltext.append(" AND 1=1");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("shztmc");
		m.setShowname("审核状态");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("djbh");
		m.setShowname("单据编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bxlx");
		m.setShowname("报销类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bxrmc");
		m.setShowname("报销人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("szbmmc");
		m.setShowname("所在部门");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bxzje");
		m.setShowname("报销总金额（元）");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("czrq");
		m.setShowname("报销日期");
		mlist.add(m);
		m = null;

		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	@RequestMapping("/expExcel6")
	@ResponseBody
	public Object Infocx(HttpServletRequest request) {
		PageData pd = this.getPageData();
		String searchValue = pd.getString("searchJson");
		String guid = pd.getString("id");
		String zl = pd.getString("treedwbh");
		String lid = LUser.getGuid();
		String shzt = pd.getString("shzt");
		String djbh = pd.getString("djbh");
		String bxrmc = pd.getString("bxrmc");
		
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select * from  ");
		if("3".equals(zl)){
			sqltext.append(" (select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt) as shztmc,t.djbh,'' as FJZZS,t.BXRY as bxrmc,((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.BXRY) ) as bxr,"
//					sqltext.append(" (select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm = t.shzt) as shztmc,t.djbh,'' as FJZZS,t.BXRY as bxrmc,((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.BXRY) ) as bxr,"
					+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,"
					+ "to_char(t.CZRQ,'yyyy-mm-dd')as czrq,'公务接待报销' as bxlx from Cw_gwjdfbxzb t where 1=1 and t.bxryid = '"+lid+"')k ");
		}else if("2".equals(zl)){
			sqltext.append(" ( select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm=t.shzt)as shztmc,"
//					sqltext.append(" ( select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm=t.shzt)as shztmc,"
					+ "  t.djbh, '差旅费报销' as bxlx,  t.sqr as bxr, (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = t.sqr) as bxrmc,"
					+ " ((select r.xm from gx_sys_ryb r where r.guid = t.sqr)) as bxrmc1, ('(' || (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr) || ')' ||(select d.mc from gx_sys_dwb d"
					+ " where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr))) as szbmMC, t.CZRQ,  to_char(T.bxzje, 'FM9999999999999990.00')  AS bxzje"
					+ "  from Cw_clfbxzb t where t.sqr = '"+lid+"' )k ");
		}
		else if("1".equals(zl)){
			sqltext.append(" (  select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm=t.shzt)as shztmc,"
//					sqltext.append(" (  select t.guid, t.shzt, (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm=t.shzt)as shztmc,"
					+ " t.djbh, '日常报销' as bxlx, t.bxr, ('(' || (select r.rybh from gx_sys_ryb r where r.guid=t.bxr) || ')' ||(select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc,"
					+ " ((select '('||r.dwbh||')'||r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc, t.szbm, t.fjzzs,"
					+ " to_char(czrq, 'yyyy-mm-dd') as czrq, to_char(T.bxzje, 'FM9999999999999990.00') as bxzje , '日常报销' as lx from Cw_rcbxzb t "
					+ " where t.bxr = '"+lid+"' )k ");
		}
		else {
			sqltext.append("(select t.guid," + 
					"       t.PROCINSTID as PROCINSTID," + 
					"       t.shzt," + 
					"       (select d.mc" + 
					"          from gx_sys_dmb d" + 
					"         where d.zl = 'lcsh'" + 
//					"         where d.zl = 'djshzt'" + 
					"           and d.dm = t.shzt) as shztmc," + 
					"       t.djbh," + 
					"       '日常报销' as bxlx," + 
					"       t.bxr," + 
					"       ((select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc," + 
					"       to_char('(' || t.szbm || ')' ||(select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc," + 
					"       t.szbm," + 
					"       to_char(czrq, 'yyyy-mm-dd') as czrq," + 
					"       to_char(T.bxzje, 'FM9999999999999990.00') as bxzje," + 
					"       '日常报销' as lx" + 
					"  from Cw_rcbxzb t where t.bxr = '"+lid+"'" + 
					" union " + 
					" select m.guid," + 
					"       m.PROCINSTID as PROCINSTID," + 
					"       m.shzt," + 
					"       (select d.mc" + 
					"          from gx_sys_dmb d" + 
					"         where d.zl = 'lcsh'" + 
//					"         where d.zl = 'djshzt'" + 
					"          and d.dm = m.shzt) as shztmc, " + 
					"       m.djbh," + 
					"       '差旅费报销' as bxlx," + 
					"       m.sqr as bxr," + 
					"       (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = m.sqr) as bxrmc," + 
					"       to_char((select '('||r.dwbh||')'||r.mc from gx_sys_dwb r where r.dwbh = (select dwbh from gx_sys_ryb where guid = m.sqr)))," + 
					"         '' as szbm," + 
					"        m.CZRQ as czrq," + 
					"       to_char(m.bxzje, 'FM9999999999999990.00') AS bxzje," + 
					"        '差旅费报销' as lx" + 
					"  from Cw_clfbxzb m where m.sqr = '"+lid+"' " + 
					"   union " +
					" select t.guid,t.PROCINSTID as PROCINSTID,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'lcsh' and d.dm = t.shzt)shztmc, " +
//					" select t.guid,t.PROCINSTID as PROCINSTID,t.shzt,(select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm = t.shzt)shztmc, " +
					"	 t.djbh,'公务接待报销' as bxlx,t.BXRY as bxr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid=t.BXRYID) as bxrmc," + 
					"	 to_char(t.szbm||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm)) as szbmmc,'' as szbm," + 
					"	 to_char(t.CZRQ,'yyyy-mm-dd')as czrq,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,'公务接待报销' as bxlx from Cw_gwjdfbxzb t where 1=1"
					+ " and t.bxryid = '"+lid+"'" + 
					"	)k");
			
		}
		sqltext.append(" where 1=1 ");
		if(Validate.noNull(shzt)){
			if("00".equals(shzt)){
				sqltext.append( " AND SHZTMC IN('未提交','财务预审退回')");
			}else if("11".equals(shzt)){
				sqltext.append(" AND SHZTMC not in('未提交','财务预审退回','审核通过')" );
			}else if("99".equals(shzt)){
				sqltext.append( " AND SHZTMC in('审核通过')" );
			}else{
				sqltext.append( " AND 1=1" );
			}
		}
		if(Validate.noNull(guid)){
			sqltext.append(" and k.guid in ('"+guid.trim()+"') ");
		}
		
		
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zkylbxService.expExcel(realfile, shortfileurl,searchValue,sqltext.toString());
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
		listbt = zkylbxService.insertJcsj(file);
		mv.addObject("listbt", listbt);
		mv.addObject("file", file);
		String pname = pd.getString("pname");
		System.out.println("========"+pname);
		mv.addObject("bool","true");
		mv.addObject("pname",pname);
 		mv.setViewName("fzgn/jcsz/jsxxsz/txl_imp");
		return mv;
	}
	
	
}




