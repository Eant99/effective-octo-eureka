package com.googosoft.controller.systemset.xtsz;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_XTB;
import com.googosoft.service.systemset.xtsz.YxszService;

/**
 * 邮箱设置控制类
 */
@Controller
@RequestMapping(value="/yxsz")
public class YxszController extends BaseController{
	
	@Resource(name="yxszService")
	private YxszService yxszService;//单例
	
	/**
	 * 获取邮箱设置页面
	 */
	@RequestMapping(value="/goYxszPage")
	public ModelAndView goYxszPage(){
		ModelAndView mv = this.getModelAndView();
		Map map = yxszService.getObjectById();
		mv.addObject("dataMap", map);
		mv.setViewName("systemset/cssz/yxsz/mailbox");
		return mv;
	}
	
	/**
	 * 保存邮箱设置信息
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_SYS_XTB xtb){
		String b = "";
		int i;
		i = yxszService.doUpdate(xtb);
		if(i >0){
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}else{
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}

}
