package com.googosoft.controller.systemset.sjgl;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.service.systemset.sjgl.XtcshService;

@Controller
@RequestMapping(value = "/xtcsh")
public class XtcshController extends BaseController{
	@Resource(name="xtcshService")
	private XtcshService xtcshService;//单例
	/**
	 * 获取系统初始化    页面
	 */
	@RequestMapping(value="goSysteminit_frmPage")
	public ModelAndView goSysteminit_frmPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/sjgl/systeminit_frm");
		return mv;
	}
	/**
	 * 系统初始化
	 */
	@RequestMapping(value="/doDeleteAll")
	@ResponseBody
	public Object doDeleteAll(){
		boolean b = xtcshService.doDeleteAll();
		if(b){
			return "{\"success\":\"true\",\"msg\":\"系统初始化成功！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"系统初始化失败！\"}";
		}
	}	

}


