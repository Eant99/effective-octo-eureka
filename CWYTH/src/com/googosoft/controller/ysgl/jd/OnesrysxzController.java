package com.googosoft.controller.ysgl.jd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.constant.TnameU;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.CW_JSXXB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.JsxxsService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/onesrysxz")
public class OnesrysxzController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@RequestMapping(value = "/goTwosrysxzListPage")
	public ModelAndView goXsxx1ListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/twoxjd/srysxz_list");
		return mv;

	}
	@RequestMapping(value = "/goOnesrysxzListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/onexjd/srysxz_list");
		return mv;

	}
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.DJBH,K.DWMC,K.BZND,K.SNYSZE AS BNYSZE,K.SNSRZE AS BNJYZE,K.BY1 CE,K.XZR,K.XZSJ,K.BY2 AS BNJY");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("SRYSXZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value = "/goOneZcysxzListPage")
	public ModelAndView goZcysxzListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/onexjd/zcysxz_list");
		return mv;

	}
	@RequestMapping(value = "/goTwoZcysxzListPage")
	public ModelAndView goZcysxzList1Page() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/twoxjd/zcysxz_list");
		return mv;

	}
	@RequestMapping(value = "/getZcysxzPageList")
	@ResponseBody
	public Object getZcPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.DJBH,K.DWMC,K.BZND,K.SNYSZE AS XMMC,K.SNSRZE AS SNYSZE,K.BY1 SNZCZE,K.BY2 AS BNJY,K.XZR,K.XZSJ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("SRYSXZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value = "/goTwosrysxgListPage")
	public ModelAndView goSrysxgListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/twosjd/srysxg_list");
		return mv;

	}
	@RequestMapping(value = "/getSrysxgPageList")
	@ResponseBody
	public Object getSrxgPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.DJBH,K.DWMC,K.BZND,K.SNYSZE,K.SNSRZE,K.BY1 BNYSZE,K.XZR AS XGR,K.XZSJ AS XGSJ,K.BY2 AS SHYJ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("SRYSXZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	
	@RequestMapping(value = "/goTwozcysxgListPage")
	public ModelAndView goZcysxgListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/twosjd/zcysxg_list");
		return mv;

	}
	@RequestMapping(value = "/getZcysxgPageList")
	@ResponseBody
	public Object getZcxgPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.DJBH,K.DWMC,K.BZND,K.SNYSZE,K.SNSRZE AS SNZCZE,K.BY1 BNYSZE,K.XZR AS XGR,K.XZSJ AS XGSJ,K.BY2 AS SHYJ,K.BY3 AS XMMC");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("SRYSXZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value = "/goTwosrysxgshListPage")
	public ModelAndView goZcysxgshListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/twosjd/srysxgsh_list");
		return mv;

	}
	@RequestMapping(value = "/getSrysxgshPageList")
	@ResponseBody
	public Object getSrxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.DJBH,K.DWMC AS SHYJ,K.BZND AS SHZT,K.BY1 AS DWMC,K.BY2 AS BZND,K.SNYSZE,K.SNSRZE,K.BY3 AS BNYSZE,K.BY4 AS XGR,K.BY5 AS XGSJ,K.BY6 AS SHR,K.BY7 AS SHRQ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("SRYSXZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value = "/goTwozcysxgshListPage")
	public ModelAndView goZcysxgshList1Page() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/twosjd/zcysxgsh_list");
		return mv;

	}
	@RequestMapping(value = "/getZcysxgshPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.DJBH,K.DWMC AS SHYJ,K.BZND AS SHZT,K.BY1 AS DWMC,K.BY2 AS BZND,K.SNYSZE,K.SNSRZE,K.BY3 AS BNYSZE,K.BY4 AS XGR,K.BY5 AS XGSJ,K.BY6 AS SHR,K.BY7 AS SHRQ,K.BY8 AS XMMC");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("SRYSXZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
}
