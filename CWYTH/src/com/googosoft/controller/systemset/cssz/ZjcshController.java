package com.googosoft.controller.systemset.cssz;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.service.systemset.cssz.ZjcshService;
/**折旧初始化
 * @author sxl
 *
 */
@Controller
@RequestMapping(value = "/zjcsh")
public class ZjcshController extends BaseController {
	@Resource(name="zjcshService")
	private ZjcshService zjcshService;//单例
	/**
	 * 跳转资产折旧初始化页面
	 */
	@RequestMapping(value="goZczjcshPage")
	public ModelAndView goZczjcshPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/cssz/zczjcsh_frm");
		return mv;
	}
	/**
	 * 折旧初始化
	 */
	@RequestMapping(value="/doDeleteAll")
	@ResponseBody
	public Object doDeleteAll(){
		if(zjcshService.doDeleteAll()){
			return "{\"success\":\"true\",\"msg\":\"折旧初始化成功！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"折旧初始化失败！\"}";
		}
	}
}
