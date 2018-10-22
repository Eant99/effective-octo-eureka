package com.googosoft.controller.wsbx.jcsz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.wsbx.xmsq.XmsqService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
//import com.sun.org.apache.bcel.internal.generic.LUSHR;

@Controller
@RequestMapping(value = "/xmsq")
public class xmsqController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xmsqService")
	private XmsqService xmsqService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getRybhByRybh(rybh);
		Date d = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);  
		
		PageList pageList = new PageList();
		sqltext.append(" * ");
//		tablename.append(" ( select distinct g.guid,g.bmbh,('('||g.bmbh||')'||(select d.mc from gx_sys_dmb d where d.dm = g.bmbh)) as bmmc,"
//				+ " (select t.mc from GX_SYS_DWB t where t.dwbh = g.fzr) as xmfzrmc, g.xmmc, g.nd, g.fzr,('('||g.fzr||')'||(select d.xm from gx_sys_ryb d where d.rybh = g.fzr)) as fzrmc,"
//				+ " to_char(g.ysje, 'FM9999999999999990.0000') as ysje, to_char(g.syje, 'FM9999999999999990.0000') as syje, g.xmbh, g.xmlx,"
//				+ " (select d.mc from gx_sys_dmb d where d.zl = 'xmfl' and d.dm = g.xmlx) as xmlxmc, g.jflx, (select d.mc   from gx_sys_dmb d  where d.zl = 'jflx'"
//				+ " and d.dm = g.jflx) as jflxmc,g.kssj,  g.jssj,g.bz, g.sffh, g.czr,g.czrq, c.sfyxecsq from Cw_jfszb g left join Cw_xmsqb c on c.xmbh = g.xmbh ) k");

//		tablename.append(" ( select distinct g.guid,g.bmbh,('('||g.bmbh||')'||(select d.mc from gx_sys_dmb d where d.dm = g.bmbh)) as bmmc,"
		tablename.append(" ( select distinct g.guid,g.bmbh,"
				+ " (select t.mc from GX_SYS_DWB t where t.dwbh = g.fzr) as xmfzrmc, g.xmmc, g.nd, g.fzr,('('||g.fzr||')'||(select d.xm from gx_sys_ryb d where d.rybh = g.fzr)) as fzrmc,"
				+ " to_char(g.ysje, 'FM9999999999999990.0000') as ysje, to_char(g.syje, 'FM9999999999999990.0000') as syje, g.xmbh,"
				+ " (select d.mc from gx_sys_dmb d where d.zl = 'xmfl' and d.dm = g.xmlx) as xmlxmc, "
				+ " xs.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=xs.xmdl) as xmdlmc, "
				+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=g.xmlx) as xmlx,g.jflx, (select d.mc   from gx_sys_dmb d  where d.zl = 'jflx'"
				+ " and d.dm = g.jflx) as jflxmc,g.kssj,  g.jssj,g.bz, g.sffh, g.czr,g.czrq from Cw_jfszb g left join xminfos xs on ((xs.guid=g.guid and xs.fzr='"+LUser.getRybh()+"') or (xs.sfyxecsq = '1' and xs.guid = g.guid)) "
				+ "LEFT JOIN  Cw_xmsqrzb z ON z.bmbh=g.bmbh"
				+ " where (g.fzr='"+rybh+"' or (xs.BSQR='"+rybh+"' and to_date('"+dateNowStr+"', 'yyyy-mm-dd') <=to_date(xs.jzsj, 'yyyy-mm-dd')) and z.bsqr='"+rybh+"' ) and xs.xmdl='2') k");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		 
		pageList.setHj1("");//合计
		//设置WHERE条件
//		pageList.setStrWhere(" and  ( k.SFYXECSQ = '1' and to_date(k.kssj,'yyyy-mm-dd')<=to_date('"+dateNowStr+"','yyyy-mm-dd') and to_date('"+dateNowStr+"','yyyy-mm-dd')<=to_date(k.jssj,'yyyy-mm-dd') or k.fzr='"+loginId+"'  )  and k.sffh = '1' ");
		pageList.setStrWhere(" and  ( to_date(k.kssj,'yyyy-mm-dd')<=to_date('"+dateNowStr+"','yyyy-mm-dd') and to_date('"+dateNowStr+"','yyyy-mm-dd')<=to_date(k.jssj,'yyyy-mm-dd') or k.fzr='"+loginId+"'  )  and k.sffh = '1' ");
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value = "/getRyPageList")
	@ResponseBody
	public Object getRyPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getRybhByRybh(rybh);
		Date d = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);  
		
		PageList pageList = new PageList();
		String xmbh = pd.getString("xmbh");
		String bmbh=pd.getString("bmbh");
		sqltext.append(" distinct * ");
		tablename.append(" ( select r.rybh,r.xm,r.rygh,r.dwbh,(select d.mc from gx_sys_dwb d where d.dwbh = r.dwbh)AS SSDW from gx_sys_ryb r "
				+ " where r.rybh not in (select bsqr from cw_xmsqb where xmbh = '"+xmbh+"' and bmbh='"+bmbh+"') and rybh!='000000') k");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("RYBH");//主键
		 
		pageList.setHj1("");//合计
		//设置WHERE条件
		pageList.setStrWhere("  ");
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value = "/getRZPageList")
	@ResponseBody
	public Object getRZPageList() throws Exception {		
		PageData pd = this.getPageData();
		String xmbh3 = pd.getString("xmbh3");  //根据某项目的编号查授权日志
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		 String loginbh = LUser.getRybh();
		 String loginid = LUser.getGuid();
		sqltext.append(" * ");
		tablename.append(" (select K.GUID,K.XMBH,K.BSQR,"
				+ " (SELECT '(' || R.RYBH || ')' || R.XM FROM GX_SYS_RYB R  WHERE R.RYBH = K.BSQR) AS BSQRMC, "
				+ "to_char(K.KSSJ,'yyyy-mm-dd') as kssj, to_char(K.JZSJ,'yyyy-mm-dd')as jzsj,K.YXECSQ,(case K.YXECSQ when '2' then '否' when '1' then '是' else '' end )as sfyxecsq,"
				+ "  s.xmmc  as xmmc,to_char(K.CZRQ,'yyyy/MM/dd HH24:mi:ss') as czrq, "
				+ " s.fzr as fzr,  t.rybh ,'(' ||t.rybh ||')'||t.xm  as sqrmc,"
				+ "   t.xm  as fzrmc from CW_XMSQRZB K "
				+ " left join  Cw_jfszb s on  s.xmbh = k.xmbh "
                + " left join gx_sys_ryb t on s.fzr = t.rybh  and t.guid = k.czr  ");
	    String xmfzrbh= xmsqService.doSelect(xmbh3);
		if(!xmfzrbh.equals(loginbh)){
			tablename.append(" where  k.czr='"+loginid+"' ");
		}
		tablename.append(" ) k ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setTableName(tablename.toString());//表名
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere(" and k.rybh is not null and k.xmbh = '"+xmbh3+"' ");
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value = "/getSqPageList")
	public ModelAndView getSqPageList() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String xmbh = pd.getString("xmbh");
		String bmbh = pd.getString("bmbh");
		mv.addObject("bmbh", bmbh);
		mv.addObject("guid", guid);
		mv.addObject("xmbh", xmbh);
		String loginrybh = LUser.getRybh();
		mv.addObject("loginrybh",loginrybh);
		mv.setViewName("wsbx/jcsz/xmsq/xmsqgl");
		return mv;

	}
	
	
	@RequestMapping(value = "/goRzPage")
	public ModelAndView goRzPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String xmbh3 = pd.getString("xmbh3");
//		Map<?, ?>  map = xmsqService.getObjectByguid(pd.getString("dwbh"));
		mv.addObject("xmbh3", xmbh3);
//		mv.addObject("map", map);
		mv.setViewName("wsbx/jcsz/xmsq/xmsqrz_list");
		return mv;

	}
	
	@RequestMapping(value = "/getSq1PageList")
	public ModelAndView getSq1PageList() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("rybh", CommonUtils.getRybh());
		mv.setViewName("wsbx/jcsz/xmsq/xmsq_list");
		///AHCW/WebContent/webView/wsbx/jcsz/xmsq/xmsq_plsq.jsp
		return mv;

	}
	
	@RequestMapping(value = "/getinfoPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String xmbh = pd.getString("xmbh");
		sqltext.append(" * ");
		String loginbh = LUser.getRybh();
		String loginid = LUser.getGuid();
		tablename.append(" ( select x.guid,x.xmbh,x.xmmc,x.bsqr,('('||x.bsqr||')'||(select r.xm from gx_sys_ryb r where r.rybh = x.bsqr)) as bsqrmc,(select rybh from gx_sys_ryb ry where ry.guid = x.czr) czrbh, "
		+ "x.kssj,x.jzsj,x.sfyxecsq,(case x.sfyxecsq when '1' then '是' when '2' then '否' else '' end ) as SFYXECSQMC,x.czr,"
		+ "('('||(select b.rybh from gx_sys_ryb b where b.guid=x.czr)||')'||(select r.xm from gx_sys_ryb r where r.guid = x.czr)) as sqrmc,x.czrq,x.fzr,x.bz from CW_XMSQB x where x.xmbh = '"+xmbh+"'and x.bsqr is not null ");
		String xmfzrbh= xmsqService.doSelect(xmbh);
		if(!xmfzrbh.equals(loginbh)){
			tablename.append(" and x.czr='"+loginid+"' ");
		}
		tablename.append(" ) k ");
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
	 * 部门人员信息弹出窗
	 */
	@RequestMapping(value="/rypage")
	public ModelAndView goRyxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("window/ryxx/window3");
		return mv;
	}
	//教师信息弹窗
	@RequestMapping("/goJsxxPage")
	public ModelAndView goJsxxPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		iniLogin(mv);
		mv.addObject("bmbh",pd.getString("bmbh"));
		mv.setViewName("wsbx/jcsz/xmsq/ryxx_list");
		return mv;
	}
	//初始化登录人员信息
	public void iniLogin(ModelAndView mv) {
		mv.addObject("loginId",LUser.getGuid());
		mv.addObject("szbm",LUser.getDwmc());
		mv.addObject("ryxm","("+LUser.getRybh()+")"+LUser.getRyxm());
		mv.addObject("today",DateUtil.getDay());
	}
	
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		String xmbh = pd.getString("xmbh");
		String bmbh = pd.getString("bmbh");
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){
			mv.addObject("guid", guid);
			mv.addObject("xmbh", xmbh);
			mv.addObject("bmbh", bmbh);
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = xmsqService.getObjectById(pd.getString("dwbh"));
			mv.addObject("xmbh",pd.getString("xmbh"));
			mv.addObject("dwb", map);
			mv.addObject("guid", pd.getString("dwbh"));
		}
		//获取被授权人的项目授权起止时间
		String xmfzrbh = xmsqService.doSelect(xmbh);
		Map<String,Object> sjmap = xmsqService.getSqqzsj(xmbh);
		mv.addObject("sjmap",sjmap);
		mv.addObject("xmfzrbh",xmfzrbh);
		mv.addObject("loginbh",rybh);
		mv.setViewName("wsbx/jcsz/xmsq/xmsqgl_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/*
	 * 批量授权
	 */
	
	@RequestMapping(value="/goSqEditPage")
	public ModelAndView goSqEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		String guid = pd.getString("guid");
		String xmbh = pd.getString("xmbh");
		String bmbh = pd.getString("bmbh");
		if(operateType.equals("C")){
			mv.addObject("guid", pd.getString("guid"));
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = xmsqService.getObjectById(pd.getString("dwbh"));
			mv.addObject("dwb", map);
			mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.setViewName("wsbx/jcsz/xmsq/xmsqgl_edit");
		mv.addObject("guid",guid);
		mv.addObject("xmbh",xmbh);
		mv.addObject("bmbh",bmbh);
		mv.addObject("ispl",1);
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_DWB dwb){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String guid = pd.getString("guid");
		String xmbh = pd.getString("xmbh1");
		String ispl = pd.getString("ispl");
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		int result=0;
		String rybh1 = LUser.getRybh();
		dwb.setCzr(loginId);
		if("C".equals(operateType))//新增
		{  
			if("1".equals(ispl)){
				result = xmsqService.doAddpl(pd,loginId,rybh1);
			}else{
				result = xmsqService.doAdd(pd,loginId,rybh1);
			}
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"',operateType:'C'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = xmsqService.doUpdate(pd,guid);
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
		String xmbh = pd.getString("xmbh");
		String bmbh = pd.getString("bmbh");
		String czr=pd.getString("czr");
		//删除单位时验证该单位下是否有人员或下级单位或资产
    	int k = xmsqService.doDelete(dwbh,xmbh,bmbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	/**
	 * 撤销
	 */
	@RequestMapping(value="/doChexiao",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doChexiao(){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String dwbh = pd.getString("dwbh1");
		System.err.println("____1_"+dwbh);
		String xmbh = pd.getString("xmbh");
		String ispl = pd.getString("ispl");
		int result=0;
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		result = xmsqService.doChexiao(pd,dwbh);
		if(result==1)
		{
			return "{success:'true',msg:'信息撤销成功！'}";
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
	}
	
	/**
	 * 判断是否授权
	 */
	@RequestMapping(value = "/doCheck")
	@ResponseBody
	@Transactional 
	public Object doCheck() throws Exception{
		PageData pd = this.getPageData();
		String xmbh=pd.getString("xmbh");
		String bmbh=pd.getString("bmbh");
		String fzr=pd.getString("fzr");
		String rybh=CommonUtils.getRybh();
		boolean flag=false;
		if(fzr.equals(rybh)){
		   flag=true;	
		}else{
		   flag = xmsqService.doCheck(xmbh,bmbh);	
		}
		String b = "";
		if (flag) {
			b = "{\"success\":\"true\"}";
		} else {
			b = "{\"success\":\"false\"}";
		}
		return b;
	}
	
	/**
	 * 判断是否授权
	 */
	@RequestMapping(value = "/doCheck1")
	@ResponseBody
	@Transactional 
	public Object doCheck1() throws Exception{
		PageData pd = this.getPageData();
		String pdm=pd.getString("xmbh");
		String rybh=CommonUtils.getRybh();
		boolean flag=false;

		  flag = xmsqService.doCheck1(pdm);	
		String b = "";
		if (flag) {
			b = "{\"success\":\"true\"}";
		} else {
			b = "{\"success\":\"false\"}";
		}
		return b;
	}
}
