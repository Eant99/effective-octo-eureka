package com.googosoft.controller.wsbx.qtjfbx;

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
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value = "/qtjfbxsq")
public class qtjfbxController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例


	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.SPZT,K.DJBH,K.JFDM,K.JFMC,K.BXR,K.BXBM,K.BXSY,K.FJZS,K.YSND,K.SQDJBH,decode(nvl(K.BXJE,0),0,'0.00',(to_char(round(K.BXJE,2),'fm99999999999990.00'))) as BXJE,K.BXRQ,K.BZ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("CW_QTJFBX K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 
	 */
	@RequestMapping(value="/check")
	public ModelAndView check(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String url = "wsbx/qtjfbx/check1";
		if(!"first".equals(type)){
			url = "wsbx/qtjfbx/check2";
		}
		mv.setViewName(url);
		return mv;
	}

	
}
