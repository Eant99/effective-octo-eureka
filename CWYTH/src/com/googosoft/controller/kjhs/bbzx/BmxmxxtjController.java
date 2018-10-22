/**
 * 
 */
package com.googosoft.controller.kjhs.bbzx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.fzgn.jcsz.RybService;
import com.googosoft.service.kjhs.bbzx.BmxmxxtjService;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.service.kjhs.bbzx.ZjrbbService;
import com.googosoft.service.systemset.qxgl.CzqxbService;
import com.googosoft.service.systemset.qxgl.GlqxbService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 部门项目信息
 * @author wangguanghua
 */
@Controller
@RequestMapping(value="/bmxmxxtj")
public class BmxmxxtjController extends BaseController{
	
	
	@Resource(name="bmxmxxtjService")
	private BmxmxxtjService bmxmxxtjService;//单例
	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	@Resource(name="zjrbbService")	
	private ZjrbbService zjrbbService;
	
	
	
	@RequestMapping(value = "/toUrl")
	public ModelAndView gokmxxPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<Map<String,Object>> months = kmyeService.getMonth();
		//默认获取当前年份
		String year=Validate.isNullToDefaultString(pd.getString("year"), sdf.format(new Date())) ;
		String startMonth=Validate.isNullToDefaultString(pd.getString("startMonth"), "01") ;
		String endMonth=Validate.isNullToDefaultString(pd.getString("endMonth"), "01") ;
		String jzpz=Validate.isNullToDefaultString(pd.getString("jzpz"), "1") ;
		mv.addObject("date", year+"年"+startMonth+"月至"+endMonth+"月");
		List list = bmxmxxtjService.getPrintInfo();
		mv.addObject("year", year);
		mv.addObject("xmdllist", list);
		mv.addObject("startMonth", startMonth);
		mv.addObject("endMonth", endMonth);
		mv.addObject("months", months);
		mv.addObject("jzpz", jzpz);
		mv.setViewName("kjhs/bbzx/bmxmxxtj");
		return mv;
	}
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public List<Map> getPageList(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = bmxmxxtjService.getResult(params);
		}
		String login = LUser.getRybh();
		//获取菜单
		List<Map> menu_list = bmxmxxtjService.getBmxmxxList(pd,login);
		System.out.println("menulist=++++++++++"+menu_list);
		Map<String, List<Map>> map=new HashMap<String, List<Map>>();
		map.put("mxlist", menu_list);
		return menu_list;
	}
	private Params getParams(HttpServletRequest request){
		Params params = new Params();
			String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
			String jzpz = Validate.isNullToDefaultString(request.getParameter("jzpz"), "");
			if("1".equals(jzpz)){
				jzpz = "02','99','00','01";
			}else{
				jzpz = "02','99";
			}
			params.setEndMonth(Validate.isNullToDefaultString(request.getParameter("endMonth"), ""));
			params.setStartMonth(Validate.isNullToDefaultString(request.getParameter("startMonth"), ""));
			params.setYear(Validate.isNullToDefaultString(request.getParameter("year"), ""));
			params.setBmbh(Validate.isNullToDefaultString(request.getParameter("bmbh"), ""));
			params.setXmbh(Validate.isNullToDefaultString(request.getParameter("xmbh"), ""));
			params.setTreebh(Validate.isNullToDefaultString(request.getParameter("treebh"), ""));
			params.setTypes(Validate.isNullToDefaultString(request.getParameter("types"), ""));
			//综合查询
			
			String kjzd = zjrbbService.getkjzd(Validate.isNullToDefaultString(request.getParameter("year"), ""));
			params.setSszt(sszt);
			params.setKjzd(kjzd);
			params.setJzpz(jzpz);
		return params;
	}
}

