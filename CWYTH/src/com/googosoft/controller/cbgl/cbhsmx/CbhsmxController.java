package com.googosoft.controller.cbgl.cbhsmx;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;

@Controller
@RequestMapping(value= "/cbgl/cbhsmx")
public class CbhsmxController extends BaseController{
	/**
	 * 跳转成本中心页面 
	 */
	@RequestMapping(value ="/goPageList")
	@ResponseBody
	public ModelAndView goCbzxPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("cbgl/cbhsmx/cbhsmx_list");
		return mv;
	}
	/**
	 * 成本核算模型管理：U/L:获取单个核算模型详细信息 C:添加新的成本核算模型信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCbhsmxxx")
	@ResponseBody
	public ModelAndView getCbhsmxxx() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		mv.setViewName("cbgl/cbhsmx/cbhsmx_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
}
