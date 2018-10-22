package com.googosoft.controller.kjhs.bbzx;

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
import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.ZzService;
import com.googosoft.service.kjhs.bbzx.GrwlMxz1Service;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value = "/grwlmxz1")
public class GrwlMxz1Controller extends BaseController {
		
	
	@Resource(name = "dictService")
	private DictService dictService;// 数据字典单例

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@Resource(name = "grwlmxz1Service")
	private GrwlMxz1Service grwlmxz1Service;
	
	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	
	@Resource(name="ZzService")
	private ZzService zzService; //单例
	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toUrl")
	public ModelAndView gokmxxPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String ly = pd.getString("ly");
		String kmbh="",kmmc="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String kmxx = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
		String dm = Validate.isNullToDefaultString(pd.getString("rybh"), "");
		if(Validate.isNull(dm)){
			dm = Validate.isNullToDefaultString(pd.getString("dm"), "");
		}
		String dwbh = Validate.isNullToDefaultString(pd.getString("dwbh"), "");
		String params = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String ymtz = Validate.isNullToDefaultString(pd.getString("ymtz"), "no");
		if(kmxx.indexOf("(")!=-1){
			kmmc=kmxx.substring(kmxx.indexOf(")")+1, kmxx.length());
			kmbh=kmxx.substring(1,kmxx.lastIndexOf(")"));
		}else{
			kmbh=Validate.isNullToDefaultString(pd.getString("kmbh"), "");
			kmmc=Validate.isNullToDefaultString(pd.getString("kmmc"), "");
		}
		if(Validate.isNull(params)&&Validate.noNull(kmbh)){
			params = "{\"list\":[{\"start\":\"start\",\"year\":\""+sdf.format(new Date())+"\",\"startMonth\":\"01\",\"endMonth\":\""+new SimpleDateFormat("MM").format(new Date())+"\",\"startKjkm\":\""+kmbh+"\",\"Kjkmmc\":\""+kmbh+"\",\"jzpz\":\"1\",\"end\":\"end\"}]}";
			request.getSession().setAttribute("params", params);
		}
		String wlrymc="";
		if(dm!=""){
			 wlrymc=grwlmxz1Service.getWlrymc(dm);				
		}
		mv.addObject("wlrymc", wlrymc);
		String sysQj = sdf.format(new Date())+"年01月至"+new SimpleDateFormat("yyyy年MM月").format(new Date());
		if(Validate.isNull(kmbh)&&"mxz".equals(ly)){
			request.getSession().removeAttribute("bbqj");
		}
		String bbqj = Validate.isNullToDefaultString(pd.getString("bbqj"),Validate.isNullToDefaultString(request.getSession().getAttribute("bbqj"), sysQj));
		String pxfs = Validate.isNullToDefaultString(pd.getString("pxfs"), "");
		String jump = Validate.isNullToDefaultString(pd.getString("jump"), "");
		String clicks = Validate.isNullToDefaultString(pd.getString("clicks"), "");
		String ryye = Validate.isNullToDefaultString(pd.getString("ryye"), "");
		String treesearch = pd.getString("treesearch");
		//控制是否显示返回按钮
		String type = pd.getString("type");
		if("mxz".equals(ly)){
			type = "mxz";
		}
		if("yes".equals(jump)){
			grwlmxz1Service.deleteKmyeb();
			request.getSession().removeAttribute("params");
			request.getSession().removeAttribute("bbqj");
			
		}
		Date date = new Date();
		String year = Validate.isNullToDefaultString(bbqj, sdf.format(date));
		year = year.substring(0,4);
		String ye =  pd.getString("ye");
		mv.addObject("ye", ye);
		String url = "kjhs/bbzx/grwl/grwlmxz_list1";
		List<Map<String,Object>> months = kmyeService.getMonth();
		String zy = pd.getString("zy");
		String km2 = pd.getString("km2");
		String km1 = pd.getString("km1");
		String PZBH1 = pd.getString("PZBH1");
		String PZBH2 = pd.getString("PZBH2");
		String PZRQ1 = pd.getString("PZRQ1");
		String PZRQ2 = pd.getString("PZRQ2");
		String JFJE1 = pd.getString("JFJE1");
		String JFJE2 = pd.getString("JFJE2");
		String DFJE1 = pd.getString("DFJE1");
		String DFJE2 = pd.getString("DFJE2");
		String StartMonth = pd.getString("StartMonth");
		String endMonth = pd.getString("endMonth");
		String pz = pd.getString("pz");
		mv.addObject("StartMonth", Validate.isNullToDefaultString(StartMonth, ""));
		mv.addObject("endMonth", Validate.isNullToDefaultString(endMonth, ""));
		mv.addObject("zy", Validate.isNullToDefaultString(zy, ""));
		mv.addObject("pz", Validate.isNullToDefaultString(pz, ""));
		mv.addObject("km1",  Validate.isNullToDefaultString(km1, ""));
		mv.addObject("km2",  Validate.isNullToDefaultString(km2, ""));
		mv.addObject("PZBH1",  Validate.isNullToDefaultString(PZBH1, ""));
		mv.addObject("PZBH2",  Validate.isNullToDefaultString(PZBH2, ""));
		mv.addObject("PZRQ1",  Validate.isNullToDefaultString(PZRQ1, ""));
		mv.addObject("PZRQ2",  Validate.isNullToDefaultString(PZRQ2, ""));
		mv.addObject("JFJE1",  Validate.isNullToDefaultString(JFJE1, ""));
		mv.addObject("JFJE2",  Validate.isNullToDefaultString(JFJE2, ""));
		mv.addObject("DFJE1",  Validate.isNullToDefaultString(DFJE1, ""));
		mv.addObject("DFJE2",  Validate.isNullToDefaultString(DFJE2, ""));
		mv.addObject("ryid", pd.getString("rybh"));
		mv.addObject("xmbh", pd.getString("xmbh"));
		mv.addObject("bmbh", pd.getString("bmbh"));
		mv.addObject("bbqj", bbqj);
		mv.addObject("kmxx", kmxx);
		mv.addObject("dm", dm);
		mv.addObject("ymtz", ymtz);
		mv.addObject("kmmc", kmmc);
		mv.addObject("pxfs", pxfs);
		mv.addObject("clicks", clicks);
		mv.addObject("jump", jump);
		mv.addObject("kmbh", kmbh);
		mv.addObject("year", year);
		mv.addObject("type", type);
		mv.addObject("ryye", ryye);
		mv.addObject("ly", ly);
		mv.addObject("treesearch", treesearch);
		mv.setViewName(url);
		return mv;
	}
	/**
	 * 获取单位人员项目树
	 * @param
	 * @return
	 */
	@RequestMapping(value="/dwryTree1" )
	public ModelAndView dwryTree1(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("kjhs/bbzx/grwl/mainDwryTree");
		return mv;
	}
	/**
	 * 弹出条件选择页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jumpWindow")
	public ModelAndView jumpWindow() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String time = pd.getString("bbqj");
		String kmbh = pd.getString("kmbh");
		//默认获取当前年份
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		
		String year = "";
		if(Validate.noNull(time)){
			year = time.substring(0, time.indexOf("年"));
		}else{
			year = sdf.format(date);
		}
		//12个月份
		List<Map<String,Object>> months = grwlmxz1Service.getMonth();
		String nowmonth=date.getMonth()+1+"";
		String StartMonth = pd.getString("StartMonth");
		String endMonth = pd.getString("endMonth");
		String pz = pd.getString("pz");
		String dm = pd.getString("dm");
		//科目级别list
		List list = grwlmxz1Service.getKjkmJb();
		mv.addObject("list", list);
		mv.addObject("StartMonth", StartMonth);
		mv.addObject("endMonth", endMonth);
		mv.addObject("year", year);
		mv.addObject("kmbh", kmbh);
		mv.addObject("pz", pz);
		mv.addObject("dm", dm);
		mv.addObject("months", months);
		mv.addObject("nowmonth", nowmonth);
		mv.setViewName("kjhs/bbzx/mxz_search4");
		return mv;
	}
	
	/**
	 * ajax处理session
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/paramSession", produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public void paramSession(HttpServletRequest request,
			HttpServletResponse response) {
		String params = this.getPageData().getString("params");
		request.getSession().setAttribute("params", params);
		request.getSession().setAttribute("bbqj", this.getPageData().getString("bbqj"));
	}
	/**
	 * 获得页面数据信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public List<Map<String,Object>> getPageList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String pxfs = "1";//默认按照凭证日期排序
		if("1".equals(pxfs)){
			pxfs = "pzrq";
		}else if("0".equals(pxfs)){
			pxfs = "pzz";
		}
		//创建参数对象
		Params params = new Params();
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String jsonStr = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");		
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		String StartMonth = Validate.isNullToDefaultString(pd.getString("StartMonth"),"");
		String endMonth = Validate.isNullToDefaultString(pd.getString("endMonth"),"");
		String rybh = Validate.isNullToDefaultString(pd.getString("dm"),"");
		mv.addObject("kmbh", kmbh);
		if(kmbh!=""&&kmbh.contains("(")){
			kmbh=kmbh.substring(1, kmbh.lastIndexOf(")"));					
		}
		String ryye = Validate.isNullToDefaultString(pd.getString("ryye"), "");
		if("ryye".equals(ryye)){
			boolean result = grwlmxz1Service.getResultNew(rybh,sszt,kmbh,year,StartMonth,endMonth,pd);//调用存储过程
			if(!result){
				return null;
			}
		}else{
			String ajson = jsonStr.substring(8,jsonStr.length()-1);
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			boolean result = grwlmxz1Service.getResult(list,sszt,kjzd,kmbh,pd);//调用存储过程
			if(!result){
				return null;
			}
		}
//		if(Validate.noNull(jsonStr)){
//			String ajson = jsonStr.substring(8,jsonStr.length()-1);
//			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
//			boolean result = grwlmxz1Service.getResult(list,sszt,kjzd,kmbh,pd);//调用存储过程
//			if(!result){
//				return null;
//			}
//		}
		return  grwlmxz1Service.getPageList(kmbh,kjzd,treesearch,pd);//查询数据
	}
	/**
	 * 导出
	 * @param
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpSession session,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		String treesearch=pd.getString("treesearch");
		String searchValue = pd.getString("searchJson");
		String flag = pd.getString("foo");
		
		 String ztgid1 = Constant.getztid(session);//账套编号
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		sysDate = Validate.isNullToDefaultString(pd.getString("bbyf"), sdf.format(date));//
		List list = new ArrayList<Map<String,Object>>();
//		list = this.getPageList(request,response);
		//把明细表list从session中取出来
//		list =(List) session.getAttribute("mxzList");
		String kmbh=pd.getString("kmbh");
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		list= grwlmxz1Service.getPageList(kmbh,kjzd,treesearch,pd);
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.grwlmxz1Service.expExcel(realfile, shortfileurl,searchValue,flag,list);
	}
	//页面之间的跳转
	@RequestMapping("/zjc")
	public ModelAndView PageSkip(HttpServletRequest request) {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String kmbh = pd.getString("kmbh");
		String bbqj = pd.getString("bbqj");
		String kkqjj = pd.getString("kkqjj");
		String pzbh = pd.getString("pzbh");
		String str=bbqj.substring(bbqj.lastIndexOf("("),bbqj.lastIndexOf(")"));
		String s=str.substring(1,str.length());
		List list = new ArrayList<Map<String,Object>>();
//		String kmmc = mxzService.kmmc(s);
	  	list = grwlmxz1Service.kmbhList(s);
		mv.addObject("list", list);
		mv.addObject("kmmc", kmbh);
//		mv.addObject("kmmc", kmmc);
		mv.addObject("bbqj", bbqj);
		mv.addObject("kkqjj", kkqjj);
		mv.addObject("pzbh", pzbh);
		
		mv.setViewName("kjhs/bbzx/mxztc");
		return mv;
	}
	public static void main(String[] args){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		System.out.println(sdf.format(new Date()));
	}
	
}

