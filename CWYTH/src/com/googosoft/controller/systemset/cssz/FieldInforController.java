package com.googosoft.controller.systemset.cssz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.cssz.FieldInforService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value="/fieldinfor")
public class FieldInforController extends BaseController{
	
	@Resource(name="fieldInforService")
	private FieldInforService fieldInforService;//单例
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	/**
	 * 验收单必填项保存
	 */
	@RequestMapping(value = "/doSave", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object doSave() throws Exception {
		if(fieldInforService.doSave(this.getPageData().getString("selectItem"))){
			return "{\"success\":true,\"msg\":\"保存成功！\"}"; 
		}else{
			return "{\"success\":false,\"msg\":\"保存失败！\"}";
		}
	}
	/**
	 * 获取必填项信息
	 * @return
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public ModelAndView getPageList(){
		List list = fieldInforService.getPageList();
		ModelAndView mv = this.getModelAndView();
		if(list.size() > 0){
			StringBuffer str = new StringBuffer();
			for(int j = 0; j < list.size(); j++){
				Map map = (Map)list.get(j);
				//10|yshd|sccj|stri|[产地]
				str.append(map.get("MODETYPE")+"|"+map.get("TABLENAME")+"|"+map.get("CTLNAME")+"|"+map.get("DTYPE")+"|"+map.get("FIELDNAME")+",");
			}
			str.delete(str.length() - 1, str.length());
			mv.addObject("modetype", str);
		}
		mv.setViewName("systemset/cssz/fieldinfor_frm");
		return mv;
	}
}
