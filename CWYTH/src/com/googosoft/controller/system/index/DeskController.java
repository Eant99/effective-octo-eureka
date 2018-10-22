package com.googosoft.controller.system.index;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.LUser;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.system.index.DeskService;
import com.googosoft.service.systemset.qxgl.CzqxbService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/desk")
public class DeskController extends BaseController{
	@Resource(name="deskService")
	private DeskService deskService;	

	@Resource(name="czqxbService")
	private  CzqxbService czqxbService;
	/**
	 * 跳转我的日常业务页面
	 * @return
	 */
	@RequestMapping("/goSetrcyw")
	public ModelAndView goSetrcyw(){
		ModelAndView mv = this.getModelAndView();
//		List list = deskService.getRcywEdit(LUser.getRybh());
		List list = czqxbService.getCzqxMenuList2(LUser.getRybh());
		mv.addObject("menu_list", list);
		mv.setViewName("system/index/setRcyw");
		return mv;
	}
	/**
	 *设置我的日常业务
	 * @return
	 */
	@RequestMapping("/doSaveRcyw")
	@ResponseBody
	public String doSaveRcyw(){
		PageData pd = this.getPageData();
		String data = pd.getString("data");
		String rybh = LUser.getRybh();
		boolean b = deskService.doSaveRcyw(data,rybh);
		if(b){
			return "{\"success\":\"true\",\"msg\":\"保存成功！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"保存失败！\"}";
		}
	}	
	/**
	 * 获取资产生命周期跳转的菜单
	 * @return
	 */
	@RequestMapping(value="/getSmzqMenu",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getSmzqMenu(){
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		List list = deskService.getSmzqMenu(mkbh);
		String json;
		if(list.size() == 0){
			json = "{\"success\":\"false\",\"msg\":\"暂无该业务权限！\"}";
		}
		else{
			json = "{\"success\":\"true\",\"title\":\"" + ((Map)list.get(0)).get("MKMC") + "\",\"url\":\"" + ((Map)list.get(0)).get("URL") + "\"}";
		}
		return json;
	}
	/**
	 * 获取我的业务流水中的信息
	 * @return
	 */
	@RequestMapping(value="/getYwls",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getYwls(){
		PageData pd = this.getPageData();
		String month = pd.getString("month");
		String rybh = LUser.getRybh();
		List list = deskService.getYwls(rybh, month);
		StringBuffer json = new StringBuffer();
		if(list.size() == 0){
			return "{success:false,msg:'暂无业务流水信息！'}";
		}
		json.append("{success:true,items:[");
		for(int i = 0; i < list.size(); i++){
			Map map = (Map)list.get(i);
			json.append("{");
			json.append("rq:'" + Validate.isNullToDefault(map.get("RQ"),"") + "',");
			json.append("sj:'" + Validate.isNullToDefault(map.get("SJ"),"") + "',");
			json.append("tb:'" + Validate.isNullToDefault(map.get("TB"),"") + "',");
			json.append("lx:'" + Validate.isNullToDefault(map.get("LX"),"") + "',");
			json.append("sm:'" + Validate.isNullToDefault(map.get("SM"),"") + "',");
			json.append("url:'" + Validate.isNullToDefault(map.get("URL"),"") + "',");
			json.append("bh:'" + Validate.isNullToDefault(map.get("BH"),"") + "'");
			json.append("},");
		}
		json.deleteCharAt(json.length() - 1);
		json.append("]}");
		return json.toString();
	}
	/**
	 * 获取是否展示业务说明信息
	 * @return
	 */
	@RequestMapping(value="/countYwsm",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object countYwsm(){
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		String content = deskService.countYwsm(mkbh);
		return "1".equals(content)?"success":"fail";
	}
	/**
	 * 获取业务说明信息
	 * @return
	 */
	@RequestMapping(value="/getYwsm")
	public ModelAndView getYwsm(){
		HttpServletRequest request = this.getRequest();
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//用于业务说明看完之后进行实际业务的操作的页面跳转使用
		String url = request.getQueryString();
		url = url.substring(url.indexOf("url")+4);
		String flag = pd.getString("flag");
		String pname = pd.getString("pname");
		mv.addObject("flag", flag);
		mv.addObject("url", url);
		mv.addObject("pname", pname);
		String mkbh = pd.getString("mkbh");
		Map map = deskService.getYwsm(mkbh);
		mv.addObject("content", map.get("YWSM"));
		mv.addObject("jdr", map.get("JDR"));
		mv.addObject("jdrq", map.get("JDRQ"));
		mv.setViewName("window/ywsm/ywsm");
		return mv;
	}
//	/**
//	 * 保存业务说明  暂时没找到哪里用这个方法，先注释掉
//	 * @return
//	 */
//	@RequestMapping(value="/doSaveYwsm")
//	@ResponseBody
//	public Object doSaveYwsm(){
//		PageData pd = this.getPageData();
//		String content = pd.getString("content");
//		String rybh =LUser.getRybh();
//		boolean b = deskService.doSaveYwsm(content,rybh);
//		if(b){
//			return "{\"msg\":\"保存成功\"}";
//		}else{
//			return "{\"msg\":\"保存失败\"}";
//		}
//	}
}
