package com.googosoft.controller.systemset.cssz;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseZczjController;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_XTB;
import com.googosoft.service.systemset.cssz.ZczjszService;

/**
 * 资产折旧设置控制类
 * @author zjy
 */
@Controller
@RequestMapping(value="/zczjsz")
public class ZczjszController extends BaseZczjController{
	@Resource(name="zczjszService")
	private ZczjszService zczjszService;//单例
	
	/**
	 * 获取资产折旧设置信息
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView getZczj(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("zczjsz",getZjxxBySysXtb());
		mv.setViewName("systemset/cssz/zczjsz/zczjsz_edit");
		return mv;
	}

	/**
	 * 保存资产折旧设置信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String doSave(ZC_SYS_XTB obj){
		if(zczjszService.doSave(obj) == 1){
			return "{\"success\":\"true\",\"msg\":\"资产折旧设置成功！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"资产折旧设置失败！\"}";
		}
	}
}
