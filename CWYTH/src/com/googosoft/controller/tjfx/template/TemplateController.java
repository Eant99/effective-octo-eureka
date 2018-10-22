package com.googosoft.controller.tjfx.template;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.tjfx.Zhcx;
import com.googosoft.service.tjfx.template.TemplateService;

/**
 * 统计分析--模板控制类
 * @author zjy
 */
@Controller
@RequestMapping(value="/template")
public class TemplateController extends BaseController {
	@Resource(name="templateService")
	private TemplateService templateService;//单例
	
	/**
	 * 获取统计分析信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getTemplateList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getTemplateList(Zhcx obj) throws Exception{
		return templateService.getTemplateList(obj);
	}
}
