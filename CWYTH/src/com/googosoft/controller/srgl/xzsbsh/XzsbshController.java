package com.googosoft.controller.srgl.xzsbsh;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value="/srgl/xzsbsh")
public class XzsbshController extends BaseController{
	/**
	 * 跳转薪资申报审核列表页面
	 * @return
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public ModelAndView getPageList() {
		PageData pd=this.getPageData();
		ModelAndView mv=getModelAndView();
		mv.setViewName("srgl/xzsbsh/xzsbsh_list");
		return mv;
	}
	/**
	 * 跳转薪资申报审核列表页面
	 * @return
	 */
	@RequestMapping(value="/getHsPageList")
	@ResponseBody
	public ModelAndView getHsPageList() {
		PageData pd=this.getPageData();
		ModelAndView mv=getModelAndView();
		mv.setViewName("srgl/xzsbsh/xzsbsh_hs_list");
		return mv;
	}
	/**
	 * 获取薪资申报的审核页面
	 * @return
	 */
	@RequestMapping(value="/getCheckPage")
	@ResponseBody
	public ModelAndView getCheckPage() {
		PageData pd=this.getPageData();
		String type=pd.getString("type");
		ModelAndView mv=getModelAndView();
		mv.addObject("type",type);
		mv.setViewName("srgl/xzsbsh/xzsbsh_check");
		return mv;
	}
	@RequestMapping(value="/check")
	public ModelAndView check(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String guid = pd.getString("guid");
		String procinstid = pd.getString("procinstid");
		mv.addObject("guid", guid);
		mv.addObject("procinstid", procinstid);
		String url = "srgl/xzsbsh/check1";
		if(!"first".equals(type)){
			url = "srgl/xzsbsh/check2";
		}
		mv.setViewName(url);
		return mv;
	}
	
}
