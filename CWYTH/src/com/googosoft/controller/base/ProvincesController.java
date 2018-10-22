package com.googosoft.controller.base;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.googosoft.service.base.ProvincesService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping("/provinces")
public class ProvincesController  extends BaseController{
	@Resource(name="provincesService")
	private ProvincesService provincesService;//单例
	
	/**
	 * 
	 */
	@RequestMapping("/getProvicesList")
	@ResponseBody
	public String getProvicesList(){
		List list = provincesService.getProvicesList();
		return new Gson().toJson(list);
	}
	@RequestMapping("/getCitiesByProvince")
	@ResponseBody
	public String getCitiesByProvince(){
		PageData pd = this.getPageData();
		String provinceid = pd.getString("provinceid");
		List list = provincesService.getCitiesByProvince(provinceid);
		return new Gson().toJson(list);
	}
}
