package com.googosoft.controller.sqspwh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.sqspwh.SqspwhService;
import com.googosoft.util.PageData;

/**
 * 事前审批维护控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping("sqspwh")
public class SqspwhController extends BaseController{
	
	@Autowired
	private SqspwhService sqspwhService;
	@Autowired
	private PageService pageService;
	
	@RequestMapping("/pageSkip")
	public ModelAndView pageSkip() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String pageName = pd.getString("pageName");
		switch (pageName) {
		case "":
			break;
		default:
			break;
		}
		mv.setViewName("systemset/sqspwh/"+pageName);
		return mv;
	}
	
	/**
	 * 平台系统设置：事前审批维护
	 * @return
	 */
	@RequestMapping(value="/getListData",produces="text/html;charset=utf-8")
	@ResponseBody
	public Object getListData() {
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		String sqlText = "",tableName = "",strWhere = "";
		//
		sqlText = " t.mkbh,t.mkmc,s.sfqy,s.sftzxz";
		tableName = " zc_sys_mkb t left join cw_sqspwhb s on t.mkbh = s.sqsplx";
		strWhere = " and t.mklx = 'sqsp'";
		//
		pageList.setSqlText(sqlText);
		pageList.setTableName(tableName);
		pageList.setKeyId("t.mkbh");
		pageList.setStrWhere(strWhere);
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	
	/**
	 * 平台系统设置：事前审批维护保存
	 * @return
	 */
	@RequestMapping(value="/doSave",produces="text/html;charset=utf-8")
	@ResponseBody
	public Object doSave() {
		PageData pd = this.getPageData();
		int i = sqspwhService.doSave(pd);
		if(i > 0) {
			return "{\"success\":true}";	
		}else {
			return "{\"success\":false}";
		}
	}
	@RequestMapping(value="/getSftzxz",produces="text/html;charset=utf-8")
	@ResponseBody
	public Object getSftzxz() {
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		if(sqspwhService.getSftzxz(mkbh)) {
			return "{\"success\":true}";	
		}else {
			return "{\"success\":false}";
		}
	}
	@RequestMapping(value="/getSqspSfsy",produces="text/html;charset=utf-8")
	@ResponseBody
	public Object getSqspSfsy() {
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		if(sqspwhService.getSqspSfsy(type)) {
			return "{\"success\":true}";	
		}else {
			return "{\"success\":false}";
		}
	}
}
