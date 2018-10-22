package com.googosoft.controller.ysgl.ysxd;

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
import com.googosoft.service.ysgl.ysxd.YsxdService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping("ysxd")
public class YsxdController extends BaseController{

	@Resource(name="ysxdService")
	private YsxdService ysxdService;
	@Resource(name="pageService")
	private PageService pageService;
	
	
	@RequestMapping("goYsxdPage")
	public ModelAndView goYsxdPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/ysxd/ysxd");
		return mv;
	}
	@RequestMapping(value="getYsxdPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getYsxdPageData() {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("guid,dwbh,xmbh,extract(year from ysnd) as ysnd,to_char(round(ysje,4),'fm999999999999.0000') ysje,xdje,zjly");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		pageList.setTableName("cw_ysxdszb");//表名
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
}
