package com.googosoft.controller.zdscpz;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.Zdscpz.Zdscpz;
import com.googosoft.service.base.PageService;
import com.googosoft.service.zdscpz.ZdscpzService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
/**
 * 自动生成凭证
 * @author wangzhiduo
 * @date 2017-12-21下午2:14:05
 */
@Controller
@RequestMapping(value ="/zdscpz")
public class ZdscpzController extends BaseController{
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="zdscpzService")
	private ZdscpzService zdscpzService;//单例
	
	// 跳转页面
	@RequestMapping(value = "/zdscpz_list")
	public ModelAndView goZdscpzPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("zdscpz/zdscpz_list");
		return mv;
	}

	// 跳转到模板选择页面
	@RequestMapping(value = "/tc")
	public ModelAndView gotcPage() {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("pz", zdscpzService.getByGid());
		mv.setViewName("zdscpz/zdscpz_tc");
		return mv;
	}
	
	/**
	 * 凭证自动化信息维护保存
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(Zdscpz dto) {
		if(Validate.noNull(dto.getZdlrpz())) {
			if(Validate.isNull(dto.getFhr()) || Validate.isNull(dto.getJzr())) {
				return "{\"success\":\"true\",\"msg\":\"必填项不能为空！\"}";
			}
		}
		int flag = zdscpzService.doSave(dto);
			if (flag > 0)
				return "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
			else
				return "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
	}

	/**
	 * 给交通工具加描述 以分辨是否显示
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/doSave5", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave5() {
		PageData pd = this.getPageData();
		int flag = zdscpzService.doSave5(pd);
			if (flag > 0)
				return "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
			else
				return "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
	}
}
