package com.googosoft.controller.jxgl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.StateManager;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.PageData;

/**
 * 精密仪器控制类
 * @author master
 */
@Controller
@RequestMapping(value="/jxpjsh")
public class JxpjshController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="dictService")
	private DictService dictService;//单例

	
	StateManager sm = new StateManager();
	/**
	 * 跳转到生成精密仪器表
	 * @return
	 */
	@RequestMapping(value="/goListPage")///CWYTH/WebContent/webView/jxgl/jxpjsh.jsp
	public ModelAndView goJmyqbPage(){
		ModelAndView mv = this.getModelAndView();
		
		mv.setViewName("jxgl/jxpjsh");
		return mv;
	}
	
	/**
	 * 获取服务分类列表
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		pageList.setSqlText("*");
		sql.append("(select guid,xmfzr,xmmc,bm,sjdf,decode(shjg,'0','审核通过','1','审核未通过')shjg from cw_jxpjsh");
		sql.append(" )A");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = " ";
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
}
