package com.googosoft.controller.yskjbb;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.service.base.PageService;
import com.googosoft.service.yskjbb.SryszcbService;

@Controller
@RequestMapping(value = "/yskjbb")
public class YssrzcbController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="SryszcbService")
	private SryszcbService SryszcbService;//单例
	
	@RequestMapping(value = "/yssrzcb")
	@ResponseBody
	public ModelAndView yssrzcb(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("list", SryszcbService.getZcList());
		mv.setViewName("yssrzcb/yssrzcb_list");
		return mv;
	}
	
	@RequestMapping(value = "/jzjy")
	@ResponseBody
	public ModelAndView jzjy(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("list", SryszcbService.getYsList());
		mv.setViewName("yssrzcb/jzjy");
		return mv;
	}

	@RequestMapping(value = "/cz")
	@ResponseBody
	public ModelAndView cz(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("list", SryszcbService.getCzList());
		mv.setViewName("yssrzcb/cz");
		return mv;
	}
}
