package com.googosoft.controller.fzgn.grsds;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.fzgn.grsds.GrsdsceljService;
import com.googosoft.util.PageData;


/**
 * 个人所得税超额累进税率表控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value = "/grsdscelj")
public class GrsdsceljController extends BaseController {

	@Resource(name="grsdsceljService")
	private GrsdsceljService grsdsceljService;
	
	/**
	 * 跳转到个人所得税超额累进税率表页面
	 */
	@RequestMapping(value="/goGrsdsEditPage")
	public ModelAndView goGrsdsEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		List pageList = grsdsceljService.getPageList();
		if(pageList.size()>=7){
		Map map1 = (Map) pageList.get(0);
		Map map2 = (Map) pageList.get(1);
		Map map3 = (Map) pageList.get(2);
		Map map4 = (Map) pageList.get(3);
		Map map5 = (Map) pageList.get(4);
		Map map6 = (Map) pageList.get(5);
		Map map7 = (Map) pageList.get(6);
		
		mv.addObject("map1", map1);
		mv.addObject("map2", map2);
		mv.addObject("map3", map3);
		mv.addObject("map4", map4);
		mv.addObject("map5", map5);
		mv.addObject("map6", map6);
		mv.addObject("map7", map7);
		}
		mv.setViewName("fzgn/jcsz/grsdsxx_edit");
		return mv;
	}
	
	/**
	 * 个人所得税超额累进税率表保存
	 * @return
	 */
	@RequestMapping(value="/dogrsdsSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object dogrsdsSave(){
		PageData pd = new PageData();
		String[] qyynsjcs = this.getRequest().getParameterValues("qyynsjc");
		String[] qyynsbzls = this.getRequest().getParameterValues("qyynsbzl");
		String[] qyynsbzhs = this.getRequest().getParameterValues("qyynsbzh");
		String[] sls = this.getRequest().getParameterValues("sl");
		String[] sskcss = this.getRequest().getParameterValues("sskcs");
		int result=0;
		result = grsdsceljService.dogrsdsSave(qyynsjcs,qyynsbzls,qyynsbzhs,sls,sskcss);
		if(result==1)
		{
			return "{success:'true',msg:'信息保存成功！'}";
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
		
	}
}
