package com.googosoft.controller.systemset.cssz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
//import com.googosoft.pojo.systemset.cssz.SYS_XTCSB;

import com.googosoft.controller.base.BaseController;

import com.googosoft.pojo.systemset.cssz.SYS_XTCSB;
import com.googosoft.service.systemset.cssz.DlxxService;
import com.googosoft.util.Validate;
/**
 * 登录信息设置
 * @author master
 */
@Controller
@RequestMapping(value="/dlxx")
public class DlxxController extends BaseController{
	
	@Resource(name="dlxxService")
	private DlxxService dlxxService;
	
	/**
	 * 跳转登录信息设置页面
	 * @return
	 */
	@RequestMapping(value="/goDlxxPage")
	public ModelAndView goDlxxPage(){
		ModelAndView mv = this.getModelAndView();
		String rootPath = this.getRequest().getContextPath();
		Map map = dlxxService.getDlxx();
		List<Map<String,Object>> file = dlxxService.getImgFile();
		StringBuffer imgFilePreview = new StringBuffer();
		StringBuffer imgFileConfig = new StringBuffer();
		StringBuffer logoFilePreview = new StringBuffer();
		StringBuffer logoFileConfig = new StringBuffer();
		for(Map<String,Object> fileMap: file){
			String djlx = fileMap.get("DJLX")+"";
			//{caption:"1.jpg",showDelete:true,key:"/imgFile/1.jpg"},
			if(djlx.equals("logoImg")){
				logoFilePreview.append("\""+rootPath+"/"+fileMap.get("PATH")+"\",");
				logoFileConfig.append("{caption:\""+fileMap.get("FILENAME")+"\",showDelete:true,key:\""+fileMap.get("PATH").toString().replaceAll("\\\\", "/")+"@"+fileMap.get("GUID")+"\"},");
			}else if(djlx.equals("bgImg")){
				imgFilePreview.append("'"+rootPath+"/"+fileMap.get("PATH")+"',");
				imgFileConfig.append("{caption:\""+fileMap.get("FILENAME")+"\",showDelete:true,key:\""+fileMap.get("PATH").toString().replaceAll("\\\\", "/")+"@"+fileMap.get("GUID")+"\"},");
			}
		}
		//logo参数
		if(Validate.noNull(logoFilePreview)){
		mv.addObject("logoFilePreview", logoFilePreview.toString().substring(0, logoFilePreview.lastIndexOf(",")));
		mv.addObject("logoFileConfig", logoFileConfig.toString().substring(0, logoFileConfig.lastIndexOf(",")));
		}
		//背景参数
		if(Validate.noNull(imgFilePreview)){
		mv.addObject("imgFilePreview", imgFilePreview.toString().substring(0, imgFilePreview.lastIndexOf(",")));
		mv.addObject("imgFileConfig", imgFileConfig.toString().substring(0, imgFileConfig.lastIndexOf(",")));
		}
		mv.addObject("dlxx",map);
		mv.addObject("Content",map.get("XTSM"));
		
		mv.setViewName("systemset/cssz/dlxxsz/dlxxOption");
		return mv;
	}
	
	/**
	 * 登录信息设置保存
	 * @param xtcsb
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/doSave", produces = "text/html;charset=UTF-8")
	@Transactional
	@ResponseBody
	public Object doSave(SYS_XTCSB xtcsb,String content){
		//String content = this.getPageData().getString("content");
		boolean b = dlxxService.doSave(xtcsb,xtcsb.getContent());
		
		if(b){
			return "{\"success\":\"true\",\"msg\":\"保存成功！！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"保存失败！！\"}";
		}
	}
	
	
}
