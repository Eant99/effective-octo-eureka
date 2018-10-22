package com.googosoft.controller.cbgl.cbzx;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.constant.TnameU;
import com.googosoft.controller.base.BaseController;
import com.googosoft.util.AutoKey;
/**
 * 成本中心控制类
 * @author wangchengxin
 *
 */
@Controller
@RequestMapping(value = "/cbgl/cbzx")
public class CbzxController extends BaseController{
	/**
	 * 跳转成本中心页面 
	 */
	@RequestMapping(value ="goCbzxPage")
	public ModelAndView goCbzxPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("cbgl/cbzx/cbzx_list");
		return mv;
	}
	/**
	 * 成本中心管理：U/L:获取单个成本中心详细信息 C:添加新的成本中心信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCbzxxx")
	@ResponseBody
	public ModelAndView getCbzxxx() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		mv.setViewName("cbgl/cbzx/cbzx_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 获取成本中心列表
	 * @return
	 */
	@RequestMapping(value ="/getPageList")
	@ResponseBody
	public Object getPageList() {
		return null;
	}
	/**
	 * 获取所用成本中心的单位信息列表
	 * @return
	 */
	public Object getDwxxPageList() {
		return null;
	}
}
