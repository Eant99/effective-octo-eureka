package com.googosoft.controller.systemset.qxgl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.fzgn.jcsz.RybService;
import com.googosoft.service.systemset.qxgl.SyfxqxService;
import com.googosoft.util.Validate;
/**
 * 使用方向权限设置控制类
 * @author master
 */
@Controller
@RequestMapping(value="/syfxqx")
public class SyfxqxController extends BaseController{
	
	@Resource(name="ryxxService")
	private RybService ryxxService;
	
	@Resource(name="syfxqxService")
	private SyfxqxService syfxqxService;
	
	/**
	 * 获取使用方向权限设置列表页面
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/findSyfxqxszList")
	public ModelAndView findDwxxList(){
		ModelAndView mv = this.getModelAndView();
		String rybh =this.getPageData().getString("rybh");//获取设置人员
		if(Validate.isNull(rybh)){
			rybh=LUser.getRybh();
		}
		//获取使用方向
		List<Map<String, Object>> syfx_list = syfxqxService.getSyfxList(rybh,Constant.SYFX);
		mv.addObject("syfx_list", syfx_list);
		/*根据人员编号获取人员工号和姓名*/
		Map rymap = ryxxService.getObjectById(rybh);
		mv.addObject("rymap", rymap);
		mv.setViewName("systemset/qxgl/syfxqx_list");
		return mv;
	}
	
	/**
	 * 保存操作信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=utf-8")
	@ResponseBody
	
	public Object doSave() throws Exception{
		String syfx = this.getPageData().getString("syfx");
		String rybh = this.getPageData().getString("rybh");
		boolean flag = syfxqxService.doSave(syfx,rybh);
		if(flag){
			return "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}
		return "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
	}

}
