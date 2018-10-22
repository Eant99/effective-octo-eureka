package com.googosoft.controller.xmgl.zjgl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.PageData;
@Controller
@RequestMapping(value = "/zclr")
public class ZclrController extends BaseController {
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="dictService")
	private DictService dictService;//单例
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("cw_zjfaszb K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	
	@RequestMapping(value="/goEditPage")
	public ModelAndView goDmbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

        List<Map<String, Object>> jldw = dictService.getDict("38");
        List<Map<String, Object>> zzxs = dictService.getDict("90");
        List<Map<String, Object>> cgjg = dictService.getDict("cgjg");
        List<Map<String, Object>> cgfs = dictService.getDict("cgfs");
		mv.addObject("jldwlist", jldw);
		mv.addObject("zzxslist", zzxs);
		mv.addObject("cgjglist", cgjg);
		mv.addObject("cgfslist", cgfs);
		String treesearch = pd.getString("treesearch");
		mv.setViewName("xmgl/zcgl/zclr_edit");
		
		return mv;
	}
	@RequestMapping(value = "/getPageList1")
	@ResponseBody
	public Object getPageList1() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.WPBH,K.WPMC");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("CW_CGWPXX K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
}
