package com.googosoft.controller.kjhs.pzxx;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;

/**
 * 出纳签字控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value="/cnqz")
public class CnqzController extends BaseController{
	/**
	 * 跳转录入页面
	 * @return
	 */
	@RequestMapping(value="/goCnqz")
	public ModelAndView goPzlrPage(){
		ModelAndView mv = this.getModelAndView();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 //获取前月的第一天
		Calendar   cal_1=Calendar.getInstance();//获取当前日期 
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		String firstDay = format.format(cal_1.getTime());
		//获取前月的最后一天
		Calendar cale = Calendar.getInstance();   
		cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
		String lastDay = format.format(cale.getTime());
		mv.addObject("firstDay", firstDay);
		mv.addObject("lastDay", lastDay);
		mv.setViewName("kjhs/pzxx/cnqz/cnqz");
		return mv;
	}
}
