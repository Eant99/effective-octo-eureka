package com.googosoft.controller.tjfx.qtfx;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.tjfx.Zhcx;
import com.googosoft.service.tjfx.qtfx.XtsyqkfxService;

/**
 * 统计分析--其他分析--系统使用情况分析控制类
 * @author master
 */
@Controller
@RequestMapping(value="/xtsyqkfx")
public class XtsyqkfxController extends BaseController {
	@Resource(name="xtsyqkfxService")
	private XtsyqkfxService xtsyqkfxService;//单例
	
	
	@RequestMapping(value="/goXtsyqkfxPage")
	public ModelAndView goZcjzfbqkfxPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("tjfx/qtfx/xtsyqkfx/main");
		return mv;
	}
	/**
	 * 获取统计分析信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getXtsyqkfxList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getXtsyqkfxList(Zhcx obj) throws Exception{
		return xtsyqkfxService.getXtsyqkfxList(obj);
	}
}
