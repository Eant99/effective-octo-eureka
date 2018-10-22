package com.googosoft.controller.ysgl.yszx;

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
import com.googosoft.service.ysgl.yszx.YszxService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping("yszx")
public class YszxController extends BaseController{

	@Resource(name="yszxService")
	private YszxService yszxService;
	@Resource(name="pageService")
	private PageService pageService;
	
	
	@RequestMapping("goYszxPage")
	public ModelAndView goYszxPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/yszx/yszx");
		return mv;
	}
	@RequestMapping(value="getYszxPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getYszxPageData() {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("guid,dwbh,xmbh,zcje,to_char(zxrq,'YYYY-MM-DD') zxrq,sm");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		pageList.setTableName("cw_yszxb");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
}
