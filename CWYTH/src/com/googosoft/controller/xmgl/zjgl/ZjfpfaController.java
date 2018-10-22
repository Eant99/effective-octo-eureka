package com.googosoft.controller.xmgl.zjgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.LUser;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xmgl.zjgl.XmfpfaService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

import net.sf.json.JSONArray;
@Controller
@RequestMapping(value = "/zjfpfa")
public class ZjfpfaController extends BaseController {
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name = "xmfpfaService")
	private XmfpfaService xmfpfaService;// 单例
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.SHZT,K.FABH,K.FAMC,K.CJRQ,K.CJR");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("CW_ZJFASZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	//
	@RequestMapping(value = "/getPageList2")
	@ResponseBody
	public Object getPageList2() throws Exception {		
		String data = SendHttpUtil.sendPost(SystemSet.address+"/zjfpfa/getPageList", "");
		List<Map<String, Object>> list = SendHttpUtil.getResultToList(data);
		PageInfo pageInfo = new PageInfo("1",""+list.size(),""+list.size(),data);
		return pageInfo.toJson();
	}
	@RequestMapping(value = "/getShPageList")
	@ResponseBody
	public Object getShPageList() throws Exception {		
		String data = SendHttpUtil.sendPost(SystemSet.address+"/zjfpsh/getPageList", "");
		List<Map<String, Object>> list = SendHttpUtil.getResultToList(data);
		PageInfo pageInfo = new PageInfo("1",""+list.size(),""+list.size(),data);
		return pageInfo.toJson();
	}
	@RequestMapping("/getXmList")
	@ResponseBody
	public Object getXmList() throws Exception {	
		String data = SendHttpUtil.sendPost(SystemSet.address+"/zjfpfa/getXmList", "");
		List<Map<String, Object>> list = SendHttpUtil.getResultToList(data);
		PageInfo pageInfo = new PageInfo("1",""+list.size(),""+list.size(),data);
		return pageInfo.toJson();
	}
	/**
	 * 项目类型
	 */
	@RequestMapping(value="/main")
	public ModelAndView goMainTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("xmgl/zjgl/zjfpfa/main");
		return mv;
	}
	@RequestMapping(value = "/xmTree", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xmflTree(HttpServletResponse response)
			throws java.io.IOException {
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String sjfl = pd.getString("sjfl");
		if(Validate.isNull(sjfl)){
			sjfl = "root";
		}
		if ("get-xmfl".equals(menu)) {
			return xmfpfaService.getXmNode(pd, sjfl, rootPath);
		} else {
			return "";
		}
	}
	//初始化下拉框数据
	public void initialSelect(ModelAndView mv) {
		String url = SystemSet.address+"/demo/dict";
		String data = SendHttpUtil.sendPost(url, "zl="+Constant.ZJLY);
		url = SystemSet.address+"/getLoginID";
		String loginID = SendHttpUtil.sendPost(url, "rybh="+LUser.getRybh());
		mv.addObject("zjlyList",SendHttpUtil.getResultToList(data));
		mv.addObject("loginID",loginID);
	}
	//跳转到添加页面
	@RequestMapping(value="/goAddPage")
	public ModelAndView goAddPage(){
		ModelAndView mv = this.getModelAndView();
		initialSelect(mv);
		mv.setViewName("/xmgl/zjgl/zjfpfa/zjfpfa_add");
		return mv;
	}
	//跳转到编辑页面
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		ModelAndView mv = this.getModelAndView();
		initialSelect(mv);
		String guid = this.getPageData().getString("guid");
		String data = SendHttpUtil.sendPost(SystemSet.address+"/zjfpfa/guidForList", "guid="+guid);
		Gson gson = new Gson();
		List<Map<String, Object>> list = gson.fromJson(data, new TypeToken<ArrayList<Map<String,Object>>>(){}.getType());
		String fabh = (String) list.get(0).get("FABH");
		String famc = (String) list.get(0).get("FAMC");
		mv.addObject("info",list);
		mv.addObject("fabh",fabh);
		mv.addObject("famc",famc);
		mv.setViewName("/xmgl/zjgl/zjfpfa/zjfpfa_edit");
		return mv;
	}
	//查看
	@RequestMapping(value="/goViewPage")
	public ModelAndView goViewPage(){
		ModelAndView mv = this.getModelAndView();
		String guid = this.getPageData().getString("guid");
		String data = SendHttpUtil.sendPost(SystemSet.address+"/zjfpfa/guidForList", "guid="+guid);
		Gson gson = new Gson();
		List<Map<String, Object>> list = gson.fromJson(data, new TypeToken<ArrayList<Map<String,Object>>>(){}.getType());
		String fabh = (String) list.get(0).get("FABH");
		String famc = (String) list.get(0).get("FAMC");
		String shyj = (String) list.get(0).get("SHYJ");
		mv.addObject("info",list);
		mv.addObject("fabh",fabh);
		mv.addObject("famc",famc);
		mv.addObject("shyj",shyj);
		mv.setViewName("/xmgl/zjgl/zjfpfa/zjfpfa_view");
		return mv;
	}
	//审核查看
	@RequestMapping(value="/goShViewPage")
	public ModelAndView goShViewPage(){
		ModelAndView mv = this.getModelAndView();
		String guid = this.getPageData().getString("guid");
		String data = SendHttpUtil.sendPost(SystemSet.address+"/zjfpfa/guidForList", "guid="+guid);
		Gson gson = new Gson();
		List<Map<String, Object>> list = gson.fromJson(data, new TypeToken<ArrayList<Map<String,Object>>>(){}.getType());
		String fabh = (String) list.get(0).get("FABH");
		String famc = (String) list.get(0).get("FAMC");
		String shyj = (String) list.get(0).get("SHYJ");
		mv.addObject("info",list);
		mv.addObject("fabh",fabh);
		mv.addObject("famc",famc);
		mv.addObject("shyj",shyj);
		mv.setViewName("/xmgl/zjgl/zjfpsh/zjfpsh_view");
		return mv;
	}
}
