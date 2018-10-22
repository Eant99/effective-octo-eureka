package com.googosoft.controller.jxgl;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value="/xmjxmbsb")
public class XmjxmbsbController extends BaseController{
	 @Resource(name="dictService")
	 private DictService dictService;//单例
	 @Resource(name="pageService")
	 private PageService pageService;//单例
	 
	 /**
		 * 获取列表页面
		 * @return
		 */
		@RequestMapping(value="/goListPage")
		public ModelAndView goDwbPage(){
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			mv.setViewName("jxgl/xmjxmbsb/xmjxmbsb_list");		
			return mv;
		}

}
