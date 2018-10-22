package com.googosoft.controller.systemset.qxgl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.LUser;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.fzgn.jcsz.RybService;
import com.googosoft.service.systemset.qxgl.CzqxbService;
import com.googosoft.service.systemset.qxgl.GlqxbService;
import com.googosoft.util.Validate;

/**
 * 操作权限设置控制类
 * @author master
 */
@Controller
@RequestMapping(value="/czqxb")
public class CzqxbController extends BaseController{
	
	@Resource(name="czqxbService")
	private CzqxbService czqxbService;//单例
	
	@Resource(name="glqxbService")
	private  GlqxbService glqxbService;//单例

	@Resource(name="ryxxService")
	private RybService ryxxService;
	/**
	 * 获取使用方向权限设置  页面
	 */
	@RequestMapping(value="goSyfxqx_frmPage")
	public ModelAndView goSyfxqx_frmPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/qxgl/syfxqx_frm");
		return mv;
	}

	/**
	 * 获取操作权限设置  页面
	 */
	@RequestMapping(value="goCzqxb_frmPage")
	public ModelAndView goCzqxb_frmPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/qxgl/czqxb_frm");
		return mv;
	}
	/**
	 * 查看操作权限  页面
	 */
	@RequestMapping(value="golookCzqxb_frmPage")
	public ModelAndView goLookCzqxb_frmPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/qxgl/czqxb_frmlook");
		return mv;
	}
	/**
	 *查看有此模块操作权限的人员
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMkxx")
	public ModelAndView getMkxx() {
		ModelAndView mv = this.getModelAndView();
		
			List<Map<String, Object>> rymc = czqxbService.getRymc(this.getPageData().getString("mkbh"));
			mv.addObject("rymcList", rymc);
		    mv.setViewName("systemset/qxgl/mkxx");		
		    return mv;
	}
	
	/**
	 * 获取操作权限设置列表页面
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/findCzqxszList")
	public ModelAndView findDwxxList(){
		ModelAndView mv = this.getModelAndView();
		String rybh =this.getPageData().getString("rybh");//获取设置人员	
		if(Validate.isNull(rybh)){
			rybh=LUser.getRybh();
		}
		/*if("000000".equals(rybh)) {
			rybh = czqxbService.getNextRyIfGly().get("rybh")+"";//如果是管理员则寻找人员工号最靠前的人员
		}*/
		String login = LUser.getRybh();
		//获取菜单
		List<Map> menu_list = czqxbService.getCzqxMenuList(rybh,login);
		mv.addObject("menu_list", menu_list);
		/*根据人员编号获取人员工号和姓名*/
		Map rymap = ryxxService.getObjectById(rybh);
		mv.addObject("rymap", rymap);
		mv.setViewName("systemset/qxgl/czqxb_list");
		return mv;
	}
	/**
	 * 查看操作权限设置列表页面
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/lookCzqxszList")
	public ModelAndView lookDwxxList(){
		ModelAndView mv = this.getModelAndView();
		String rybh =this.getPageData().getString("rybh");//获取设置人员
		
		if(Validate.isNull(rybh)){
			rybh=LUser.getRybh();
		}
		String login = LUser.getRybh();
		//获取菜单
		List<Map> menu_list = czqxbService.getCzqxMenuList1(rybh,login);
		mv.addObject("menu_list", menu_list);
		/*根据人员编号获取人员工号和姓名*/
		Map rymap = ryxxService.getObjectById(rybh);
		mv.addObject("rymap", rymap);
		mv.setViewName("systemset/qxgl/czqxb_view");
		return mv;
	}
	
	/**
	 * 保存操作信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=utf-8")
	@ResponseBody
	
	public Object doSave() throws Exception{
		String mkbh = this.getPageData().getString("MKBH");
		String rybh = this.getPageData().getString("RYBH");
		String b = "";
		int i=0;
		i= czqxbService.doSave(mkbh,rybh);
		if(i>0){
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}else{
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}
	
	/**
	 * 通过人员工号查询人员编号存不存在
	 */
	@RequestMapping(value="/getRybh",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object getRybh(){
		String ryxm = Validate.isNullToDefault(this.getRequest().getParameter("RYXM"), "")+"";
		String loginrybh = LUser.getRybh();
		String rybh = ryxxService.findRybhByRyxm(loginrybh,ryxm);
		return rybh ;
	}
}
