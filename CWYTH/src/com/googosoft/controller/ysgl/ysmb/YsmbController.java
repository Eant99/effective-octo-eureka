package com.googosoft.controller.ysgl.ysmb;

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
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.ysmb.YsmbService;
import com.googosoft.util.PageData;


@Controller
@RequestMapping("ysmb")
public class YsmbController extends BaseController{

	@Resource(name="pageService")
	PageService pageService;
	@Resource(name="ysmbService")
	YsmbService ysmbService;
	
	
	@RequestMapping("/goYsmbPage")
	public ModelAndView goYsmbPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/ysmb/ysmb");
		return mv;
	}
	@RequestMapping(value="getYsmbPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getYsmbPageData() {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("*");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		pageList.setTableName("cw_ysmbb");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping("/goYsmbEditPage")
	public ModelAndView goYsmbEditPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		Map<String, Object> ysmb = ysmbService.getYsmbByGuid(guid);
		mv.addObject("ysmb",ysmb);
		mv.setViewName("ysgl/ysmb/ysmbEdit");
		return mv;
	}
}
