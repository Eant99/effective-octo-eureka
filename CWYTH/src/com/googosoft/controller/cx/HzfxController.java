package com.googosoft.controller.cx;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.util.PageData;

/**
 * 汇总分析跳转控制类
 */
@Controller
@RequestMapping(value="/hzfx")
public class HzfxController extends BaseController{
	/**
	 * 跳转汇总分析列表页面。
	 * @return
	 */
	@RequestMapping(value="/goHzfxPage")
	public ModelAndView goHzfxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String lx = pd.getString("lx");
	
		if("1".equals(lx)){//验收单的汇总分析
			mv.setViewName("cx/ysdcx/hzfx_list");
		}else if("2".equals(lx)){//主机表的汇总分析(公开查询模块)
			mv.setViewName("cx/public/hzfx_list");
		}else if("3".equals(lx)){//主机表的汇总分析
			mv.setViewName("cx/zccx/hzfx_list");
		}else if ("4".equals(lx)){//附件表的汇总分析
			mv.setViewName("cx/fjcx/hzfx_list");
		}
		return mv;
	}
}
