package com.googosoft.controller.kjhs.bbzx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.kjhs.bbzx.ZjszyebService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;

/**
 * 资金收支余额表
 * @author ChengxinWang
 *
 */
@Controller
@RequestMapping(value="/zjszyeb")
public class ZjszyebController extends BaseController{
	@Resource(name="zjszyebService")
	private ZjszyebService zjszyebService;
	/**
	 * 进入资金收支余额表页面
	 */
	@RequestMapping(value="/goZjszyebPage")
	public ModelAndView goZjszyebPage() {
		ModelAndView mv=this.getModelAndView();
		//默认获取当前年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		String endMonth = Constant.MR_MONTH();
		//获取月份
		List<Map<String,Object>> months=zjszyebService.getMonth();
		mv.addObject("date", year+"年01月至"+endMonth+"月");
		mv.addObject("endMonth", endMonth);
		mv.addObject("startMonth", "01");
		mv.addObject("year", year);
		mv.addObject("months", months);
		mv.setViewName("/kjhs/bbzx//zjsz/zjszyeb_list");
		return mv;
	}
	/**
	 * 获取列表数据
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) {
		PageData pd=this.getPageData();
		String sszt=Constant.getztid(session);
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		result_map = zjszyebService.getResultMap(pd,sszt);
		return result_map;
	}
	/**
	 * 导出资金收支余额表
	 */
	@RequestMapping(value="/expExcel")
	@ResponseBody
	public Object expExcel(HttpSession session) {
		PageData pd=this.getPageData();
		String sszt=Constant.getztid(session);
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zjszyebService.expExcel(realfile, shortfileurl,pd,sszt);
	}
}
