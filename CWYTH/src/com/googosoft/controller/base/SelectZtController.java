package com.googosoft.controller.base;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value="/selectzt")
public class SelectZtController extends BaseController {
	@RequestMapping(value="/add")
	@ResponseBody
	public void addZt(HttpSession session){
		PageData pd = this.getPageData();
		String ztid=pd.getString("guid");
		String name=pd.getString("name");
		System.err.print("账套id切换为"+ztid+"账套名称为："+name);
		session.setAttribute("ztid",ztid);
		session.setAttribute("ztmc", name);
		System.out.println("打印人员id"+LUser.getRybh());
		System.out.println("打印人员type"+LUser.getType());
		
		
		
	}
	@RequestMapping(value="switchZtxx",produces="text/html;charset=utf-8")
	@ResponseBody
	public Object switchZtxx(HttpSession session) {
		PageData pd = this.getPageData();
		String ztid = pd.getString("guid");
		String ztmc = pd.getString("ztmc");
		session.setAttribute("ztid", ztid);
		session.setAttribute("ztmc", ztmc);
		return "{\"success\":true}";
		
	}

}
