package com.googosoft.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.util.PageData;
import com.googosoft.util.WindowUtil;
@Controller
@RequestMapping("/MutiSearch")
public class MutiSearchConroller extends BaseController {
	
	/**
	 * 综合查询
	 */
	@RequestMapping(value="/goSearchListPage")
	@ResponseBody
	public Object goSearchListPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		mv.addObject("dwbh",dwbh);
//		List syfxList = dictService.getDict(Constant.SYFX);//使用方向
//		List xzList = dictService.getDict(Constant.XZ);//现状
		mv.addObject("lmList", WindowUtil.getLmList());
		mv.addObject("fhList", WindowUtil.getfhList());
		mv.setViewName("window/search/MutiSearchList");
		return mv;
	}
	
	/**
	 * 综合查询
	 */
	@RequestMapping(value="/goSearchTreePage")
	@ResponseBody
	public Object goSearchTreePage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("window/search/MutiSearchTree");
		return mv;
	}
	/**
	 * 保存方案
	 * @return
	 */
	@RequestMapping(value="/doSave")
	@ResponseBody
	public Object doSave(){
		return null;
	}
	/**
	 * 删除方案
	 * @return
	 */
	@RequestMapping(value="/doDel")
	@ResponseBody
	public Object doDel(){
		return null;
	}

}
