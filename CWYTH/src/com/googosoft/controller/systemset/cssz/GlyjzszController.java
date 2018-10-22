package com.googosoft.controller.systemset.cssz;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.systemset.cssz.ZC_CYFLSZB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.cssz.CyflszService;
import com.googosoft.service.systemset.cssz.XtbService;
import com.googosoft.util.PageData;
/**
 * 常用分类设置
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/glyjzsz")
public class GlyjzszController extends BaseController {
	@Resource(name="cyflszService")
	private CyflszService cyflszService;
	
	@Resource(name = "pageService")
	private PageService pageService;// 单例
	
	@Resource(name="xtbService")
	private XtbService xtbService;//单例
	
	/**
	 * 获取管理员建账设置    页面
	 */
	@RequestMapping(value="goGlyjzszPage")
	public ModelAndView goHsortSetPage(){
		ModelAndView mv = this.getModelAndView();
		Map map = cyflszService.findPage();
		mv.addObject("xtb",map);
		mv.setViewName("systemset/cssz/glyjzsz");
		return mv;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/saveXx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object saveTplxsz() {
		PageData pd=this.getPageData();
		String ksdh=pd.getString("ksdh");
		String b="";
		int i;
		i=xtbService.doUpdate(ksdh);
		if(i==1){
			b="{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}else{
			b="{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}
}
