package com.googosoft.controller.kjhs.pzxx;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.pzxx.PzcxsService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/yhdz")
public class YhdzController extends BaseController {

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例
	@Resource(name = "pzcxsService")
	private PzcxsService pzcxsService;// 分页单例
	
	/**
	 * 银行对账页面
	 * @return
	 */
	@RequestMapping(value="/goYhdzPage")
	public ModelAndView goYhdzPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		String zfsj = Validate.isNullToDefaultString(pd.getString("zfsj"), "");		
		String je = Validate.isNullToDefaultString(pd.getString("je"), "");
		String khxlh = Validate.isNullToDefaultString(pd.getString("khxlh"), "");
		mv.addObject("zfsj", zfsj);
		mv.addObject("je", je);
		mv.addObject("khxlh", khxlh);
		
		String jump = Validate.isNullToDefaultString(pd.getString("jump"), "");
		if("yes".equals(jump)){
			//页面初始化
			//yhckrjzService.deleteKmyeb();
			request.getSession().removeAttribute("yhckrjzJson");
		}
		
		mv.addObject("jump", jump);
		mv.setViewName("kjhs/pzxx/yhdz/yhdz_list");
		return mv;
	}
	/**
	 * 弹出条件选择页面
	 * @return
	 */
	@RequestMapping(value = "/jumpWindow")
	public ModelAndView jumpWindow() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("kjhs/pzxx/yhdz/yhdz_search");
		return mv;
	}
	/**
	 * ajax处理session
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/paramSession", produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public void paramSession(HttpServletRequest request,HttpServletResponse response) throws SQLException {
		String params = this.getPageData().getString("yhdzJson");		
		request.getSession().setAttribute("yhdzJson", params);
	}
	/**
	 * 页面数据
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() {
		PageData pd = this.getPageData();	
		String zfsj = Validate.isNullToDefaultString(pd.getString("zfsj"), "");		
		String je = Validate.isNullToDefaultString(pd.getString("je"), "");
		String khxlh = Validate.isNullToDefaultString(pd.getString("khxlh"), "");
		//  pzcxsService.getPzguid(zfsj,je,khxlh);
		return "";
	}

}
