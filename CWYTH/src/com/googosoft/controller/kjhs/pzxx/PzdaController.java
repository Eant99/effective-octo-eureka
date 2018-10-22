package com.googosoft.controller.kjhs.pzxx;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;

/**
 * 凭证查询控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value="/pzda")
public class PzdaController extends BaseController{
	/**
	 * 跳转录入页面
	 * @return
	 */
	@RequestMapping(value="/goPzda")
	public ModelAndView goPzlrPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("kjhs/pzxx/pzda/pzda");
		return mv;
	}
}
