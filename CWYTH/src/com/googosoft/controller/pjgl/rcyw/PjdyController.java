package com.googosoft.controller.pjgl.rcyw;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.pjgl.rcyw.PjdyService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value="/pjgl/rcyw/pjdy")
public class PjdyController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="pjdyService")
	private PjdyService pjdyService;//单例
	
	/**
	 * 跳转到票据  打印  列表初始页面
	 */
	@RequestMapping(value="/goPjdyListPage")
	public ModelAndView goPjdyListPage() {
		ModelAndView mv=this.getModelAndView();
		mv.setViewName("/pjgl/rcyw/pjdy/pjdy_list");
		return mv;
	}
	/**
	 * 获取列表页数据
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response){
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" (select p.gid , p.zt,p.ztgid, p.pjzh,p.gmgid ,p.pjlx, ");
		sqltext.append(" p.pjh,p.SKR, p.YT, p.CPZH as CPRZH, p.cpyh as FKHMC, p.je as CPJE,");
		sqltext.append(" decode (p.SFDY,'01','是','00','否') as sfdy,   to_char( p.cprq,'yyyy-mm-dd HH24:mm:ss') as cprq,");
		sqltext.append(" l.pjlxmc ");
		sqltext.append("  from CW_PJ p ");
		sqltext.append(" left join cw_pjlxb l on p.pjlx = l.pjlxbh ");
		sqltext.append("  ) K");
		
		pageList.setSqlText(" * ");
		//设置表名
		pageList.setTableName(sqltext.toString());
		//设置表主键名
		pageList.setKeyId("k.gid");
		//设置WHERE条件
		pageList.setStrWhere(" and k.zt in('20','30') "); //获取管理单位权限
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
		  pageList = pageService.findPageList(pd,pageList);//引用传递
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
	}
	/**
	 * 添砖 打印阅览 页面
	 * 
	 */
	@RequestMapping(value="/goPrintPage")
	public ModelAndView goPrintPage() {
		ModelAndView mv=this.getModelAndView();
		PageData pd = this.getPageData();
		String url = "/pjgl/rcyw/pjdy/";
		String gid = pd.getString("gid");
		String gmgid = pd.getString("gmgid");
		String pjlx = pd.getString("pjlx");
		Map map ;
		if(pjlx.equals("06")){ //增值税专用发票
			Map zzsMap = pjdyService.getZzsXX(gid);
			mv.addObject("map", zzsMap);
			url += "zzs_print";
		}else if(pjlx.equals("07")){//收据
			Map sjMap = pjdyService.getSjXX(gid);
			mv.addObject("map", sjMap);
			url += "sj_print";
		}else if(pjlx.equals("08")){//普通发票
			Map ptfpMap = pjdyService.getPtfpXX(gid);
			mv.addObject("map", ptfpMap);
			url += "ptfp_print";
		}else if(pjlx.equals("09")){//专用支票
			Map zyzpMap = pjdyService.getZyzpXX(gid);
			mv.addObject("map", zyzpMap);
			url += "zyfp_print";
		}
		mv.addObject("gid", gid);
		mv.addObject("gmgid", gmgid);
		
		mv.setViewName(url);
		return mv;
	}
	/**
	 * 添砖 编辑 页面
	 * 
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage() {
		ModelAndView mv=this.getModelAndView();
		PageData pd = this.getPageData();
		String url = "/pjgl/rcyw/pjdy/";
		String gid = pd.getString("gid");
		String gmgid = pd.getString("gmgid");
		String pjlx = pd.getString("pjlx");
		Map map ;
		if(pjlx.equals("06")){ //增值税专用发票
			Map zzsMap = pjdyService.getZzsXX(gid);
			mv.addObject("pj", zzsMap);
			url += "zzs_kp_edit";
		}else if(pjlx.equals("07")){//收据
			Map sjMap = pjdyService.getSjXX(gid);
			mv.addObject("pj", sjMap);
			url += "sj_kp_edit";
		}else if(pjlx.equals("08")){//普通发票
			Map ptfpMap = pjdyService.getPtfpXX(gid);
			mv.addObject("pj", ptfpMap);
			url += "ptfp_kp_edit";
		}else if(pjlx.equals("09")){//专用支票
			Map zyzpMap = pjdyService.getZyzpXX(gid);
			mv.addObject("pj", zyzpMap);
			url += "zyfp_kp_edit";
		}
		mv.addObject("gid", gid);
		mv.addObject("gmgid", gmgid);
		
		mv.setViewName(url);
		return mv;
	}
	/**
	 *  保存 
	 */
	@RequestMapping(value = "/doSavePtfp", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSavePtfp() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		int result=0;
		result = pjdyService.doSavePtfp(pd);
		if (result == 1) {
			return "{success:'true',msg:'信息保存成功！'}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	@RequestMapping(value = "/doSaveSj", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveSj() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		int result=0;
		result = pjdyService.doSaveSj(pd);
		if (result == 1) {
			return "{success:'true',msg:'信息保存成功！'}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	@RequestMapping(value = "/doSaveZyfp", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveZyfp() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		int result=0;
		result = pjdyService.doSaveZyfp(pd);
		if (result == 1) {
			return "{success:'true',msg:'信息保存成功！'}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	@RequestMapping(value = "/doSaveZzs", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveZzs() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		int result=0;
		result = pjdyService.doSaveZzs(pd);
		if (result == 1) {
			return "{success:'true',msg:'信息保存成功！'}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	
}
