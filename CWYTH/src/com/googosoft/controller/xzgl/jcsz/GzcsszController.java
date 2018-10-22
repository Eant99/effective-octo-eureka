package com.googosoft.controller.xzgl.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.xzgl.jcsz.GzcsszService;
import com.googosoft.service.ysgl.xmsz.XmxxService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping("/gzcssz")
public class GzcsszController extends BaseController{

	@Resource(name="gzcsszService")
	GzcsszService gzcsszService;
	/**
	 * 工资参数设置
	 */
	@RequestMapping(value="/gzcs",produces = "text/html;charset=UTF-8")
	public ModelAndView getGzcs(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();	
		Map<String, Object> gzcs = gzcsszService.getGzcsMap();
		List list = new ArrayList<Map<String,Object>>();
		mv.addObject("gzcs", gzcs);
		mv.setViewName("xzgl/jcsz/gzcssz");  
		return mv;
	}
	/**
	 * 工资参数保存
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave() {
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		boolean bool = false;
		if (list.size()>0) {
			bool = gzcsszService.doSave(list);
		}
		return gson.toJson(bool);
	}
}
