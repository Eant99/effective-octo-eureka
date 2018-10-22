package com.googosoft.controller.systemset.angl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.service.fzgn.jcsz.RybService;

/**
 * 修改他人密码控制类
 * 
 * @create by 刘帅 on 2016-10-18 14:57
 * 
 */
@Controller
@RequestMapping(value = "/xgtrmm")
public class XgtrmmControl extends BaseController {

	@Resource(name="ryxxService")
	private RybService ryxxService;
	
	/**
	 * 跳转到修改他人密码页面
	 * @return
	 */
	@RequestMapping(value="/goXgtrmmPage")
	public ModelAndView goXgtrmmPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/aqgl/xgtrmm");
		return mv;
	}
	/**
	 * 修改密码
	 * 
	 * @param ddb
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/doUpdate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdate(GX_SYS_RYB ryb) throws Exception {
		if (ryxxService.doUpdate_xgtrmm(ryb))
			return "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
		else
			return "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
	}

}
