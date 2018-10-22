package com.googosoft.controller.cwbb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.base.PageService;
import com.googosoft.service.cwbb.CzbzsrzcService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 财政补助收入支出表
 * @author wangzhiduo
 * @date 2018-1-2下午7:14:29
 */
@Controller
@RequestMapping(value = "/czbzsrzc")
public class CzbzsrzcbController extends BaseController {
	@Resource(name="czbzsrzcService")
	private CzbzsrzcService czbzsrzcService;
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	/**
	 * 跳转资产负债表
	 */
	@RequestMapping(value="/czbzsrzc_list")
	public ModelAndView czbzsrzc(HttpServletRequest req,HttpSession session){
		PageData pd = this.getPageData();
		String login = LUser.getGuid();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");//0月度1年度
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "1");//结转凭证
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "1");//记账凭证
		String sszt = Validate.isNullToDefaultString(Constant.getztid(req.getSession()), "googosoft");
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"), Constant.getztid(session));
		Map<String,Object> bzdw =  czbzsrzcService.getBzdw();//编制单位
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		sysDate = Validate.isNullToDefaultString(pd.getString("nd"), sdf.format(date));//
		//String ztgid = Constant.getztid(session);//账套编号
		ModelAndView mv = this.getModelAndView();//视图转化器
		List list = new ArrayList<Map<String,Object>>();
		String sj = sysDate.substring(0, 4);
		// bblx==1，sysdate===当前时间取得年和月 sszt==学生账套 jzpz==0
		int result = czbzsrzcService.checkCzbz(bblx, sysDate, sszt, jzpz);
		if (result > 0) {
			list = czbzsrzcService.getResultList(bblx, sysDate, sszt, jzpz);
		} else {
			String kjzd = Validate.isNullToDefaultString(czbzsrzcService.getKjzd(sszt,sysDate), "0001");//会计制度
			list = czbzsrzcService.getResultListByPro(bblx, sj, sszt, jzpz, bzdw.get("GUID")+"",kjzd);
		}
		
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("sysDate",sysDate);
		mv.addObject("sszt", sszt);
		mv.setViewName("bbzx/cwbb/czbzsrzc/czbzsrzc_list");//要跳转的jsp页面
		return mv;
	}
	
	/**
	 * 跳转打印页面
	 * @return
	 */
	@RequestMapping(value = "/goPrint")
	public ModelAndView goPrint(HttpServletRequest req,HttpServletResponse res) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");
		String sysDate = Validate.isNullToDefaultString(pd.getString("sysDate"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(req.getSession()), "googosoft");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		String bzdw = Validate.isNullToDefaultString(pd.getString("bzdw"), "001");
		int result = czbzsrzcService.checkCzbz(bblx, sysDate, sszt,jzpz);
		List list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map =  czbzsrzcService.getBzdw();
		if(result>0){
			list = czbzsrzcService.getResultList(bblx, sysDate, sszt, jzpz);
		}else{
			String kjzd = Validate.isNullToDefaultString(czbzsrzcService.getKjzd(sszt,sysDate), "0001");//会计制度
			list = czbzsrzcService.getResultListByPro(bblx, sysDate, sszt, jzpz, map.get("GUID")+"",kjzd);
		}
		System.err.println(list);
		mv.addObject("list", list);
		mv.addObject("map", map);
		mv.addObject("bblx", bblx);
		mv.addObject("jzpz", jzpz);
		mv.addObject("sysDate", sysDate);
		mv.setViewName("bbzx/cwbb/czbzsrzc/czbzsrzc_print");
		return mv;
	} 
	
	/**
	 * 点击保存
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
			bool = czbzsrzcService.doSave(list);
		}
		return gson.toJson(bool);
	}
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info2(HttpServletRequest req,HttpSession session) {
		PageData pd = this.getPageData();
		String searchValue = pd.getString("searchJson");
		
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");
		String sysDate = Validate.isNullToDefaultString(pd.getString("sysDate"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(req.getSession()), "googosoft");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		String bzdw = Validate.isNullToDefaultString(pd.getString("bzdw"), "001");
		int result = czbzsrzcService.checkCzbz(bblx, sysDate, sszt,jzpz);
		List list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map =  czbzsrzcService.getBzdw();
//		if(result>0){
//			list = czbzsrzcService.getResultList(bblx, sysDate, sszt, jzpz);
//		}else{
//			list = czbzsrzcService.getResultListByPro(bblx, sysDate, sszt, jzpz, map.get("GUID")+"");
//		}
		
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.czbzsrzcService.expExcel(realfile, shortfileurl,searchValue);
//		return this.czbzsrzcService.expExcel(realfile, shortfileurl,searchValue,list);
	}

}
