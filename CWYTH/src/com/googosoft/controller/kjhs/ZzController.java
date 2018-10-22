package com.googosoft.controller.kjhs;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.alibaba.druid.sql.visitor.functions.Substring;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.ZzService;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.service.kjhs.bbzx.ZjrbbService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Controller
@RequestMapping(value="/Zz")
public class ZzController extends BaseController{
	@Resource(name="ZzService")
	private ZzService zzService; //单例
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	@Resource(name="zjrbbService")	
	private ZjrbbService zjrbbService;

	/**
	 * 弹出条件选择页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jumpWindow")
	public ModelAndView jumpWindow() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String flag = Validate.isNullToDefaultString(pd.getString("flag"),"select");
		String qskjqj = Validate.isNullToDefaultString(pd.getString("qskjqj"),"");
		String jskjqj = Validate.isNullToDefaultString(pd.getString("jskjqj"),"");
		String kjkm = Validate.isNullToDefaultString(pd.getString("kjkm"),"");
		String kmmc = Validate.isNullToDefaultString(pd.getString("kmmc"),"");
		mv.addObject("flag", flag);
		mv.addObject("kmmc", kmmc);
		mv.addObject("kjkm", kjkm);
		mv.addObject("qskjqj", qskjqj);
		mv.addObject("jskjqj", jskjqj);
		//科目级别list
		List list = zzService.getKjkmJb();
		mv.addObject("list", list);
		mv.setViewName("kjhs/bbzx/zz_search");
		return mv;
	}
	@RequestMapping(value="/getXxList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getXxList(String jb,String dm) throws Exception{
		Gson gson = new Gson();
		List list =zzService.getXxList(jb,dm);
		return gson.toJson(list);
	}
	/**
	 * 导出日总账
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportRzzExcel")
	@ResponseBody
	public Object exportRzzExcel(HttpServletRequest request,HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		//从session中取出总账
		@SuppressWarnings("unchecked")
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		List<Map<String, Object>> list = zzService.getPageList(kmbh,kjzd,treesearch,pd);
//		System.err.println("总账list+"+list);
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zzService.exportRzzExcel(realfile, shortfileurl,"日总账",list);
	}
	
	@RequestMapping("/expExcel")
	@ResponseBody
	public Object Info(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, List<Map<String, Object>>> result_map = new HashMap<String, List<Map<String, Object>>>();
/*		Params params = this.getParams(request);
		if (params != null) {
			result_map = jjkmyebService.getResult(params);
		}*/
		list = result_map.get("mxList");
		List<M_largedata> mlist = new ArrayList<M_largedata>();// 存放第一行标题
		List<M_largedata> mlist1 = new ArrayList<M_largedata>();// 存放第二行标题
		List<M_largedata> mlist2 = new ArrayList<M_largedata>();// 存放数据
		List<List<M_largedata>> tlist = new ArrayList<List<M_largedata>>();
		M_largedata m = new M_largedata();

		// 导出初始化列表
		m.setIsmerge(true);// 合并单元格
		m.setErow(1);// 合并行0开始
		m.setName("kmbh");// 序号直接写rn即可
		m.setShowname("经济科目编号");// 第一行
		mlist.add(m);
		mlist2.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);// 合并单元格
		m.setErow(1);// 合并行0开始
		m.setName("kmmc");// 序号直接写rn即可
		m.setShowname("经济科目名称");// 第一行
		mlist.add(m);
		mlist2.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);// 合并单元格
		m.setErow(1);// 合并行0开始
		m.setName("qcye");// 序号直接写rn即可
		m.setShowname("期初余额");// 第一行
		mlist.add(m);
		mlist2.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);// 合并单元格
		m.setEcol(1);// 合并列0开始
		m.setShowname("本期发生");// 第一行
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);// 合并单元格
		m.setErow(1);// 合并行0开始
		m.setName("BQJF");// 序号直接写rn即可
		m.setShowname("期末余额");// 第一行
		mlist.add(m);
		mlist2.add(m);
		m = null;

		m = new M_largedata();
		m.setSindex(4);// 开始列0开始
		m.setColstyle("double");
		m.setName("BQDF");
		m.setShowname("借方");// 第二行
		mlist1.add(m);
		mlist2.add(m);
		m = null;

		m = new M_largedata();
		m.setColstyle("double");
		m.setName("QMYE");
		m.setShowname("贷方");// 第二行
		mlist1.add(m);
		mlist2.add(m);
		m = null;

		tlist.add(mlist);
		tlist.add(mlist1);

		System.out.println("WEAEAWE" + list);
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\") + "WEB-INF\\file\\excel\\"
				+ shortfileurl;
		String name = "经济科目余额表.xls";
		kmyeService.ExpExcel(list, realfile, name, mlist2, tlist);
		return "{\"url\":\"excel\\\\" + shortfileurl + "\"}";
		// return this.kmyeService.expExcel(realfile, shortfileurl,"","",list);
	}
	
	
	
	
	
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public List<Map<String,Object>> getPageList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		
		ModelAndView mv = this.getModelAndView();
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		//创建参数对象
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String jsonStr = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		String qskjqj = Validate.isNullToDefaultString(pd.getString("qskjqj"),"");
		String jskjqj = Validate.isNullToDefaultString(pd.getString("jskjqj"),"");
		System.err.println("A"+qskjqj+"A"+"============================================"+"B"+jskjqj+"B");
		String kjkm = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		mv.addObject("kmbh", kmbh);
		if(kmbh!=""&&kmbh.contains("(")){
			kmbh=kmbh.substring(1, kmbh.lastIndexOf(")"));					
		}
//		if(Validate.noNull(jsonStr)){
//			String ajson = jsonStr.substring(8,jsonStr.length()-1);
//			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			boolean result = zzService.getResult(qskjqj,jskjqj,kjkm);
			if(!result){
				return null;
			}
//		}
		System.err.println("treesearch="+treesearch);
		return  zzService.getPageList(kmbh,kjzd,treesearch,pd);
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
	 * 导出日总账
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportYzzExcel")
	@ResponseBody
	public Object exportYzzExcel(HttpServletRequest request,HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		//从session中取出月总账
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String,Object>>)session.getAttribute("rzzList");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zzService.exportYzzExcel(realfile, shortfileurl,"月总账",list);
	}
	/**
	 * 跳转到日总账页面（勾选按日汇总）
	 * @param session
	 * @return
	 */
	@RequestMapping("goRzzPage")
	public ModelAndView goRzzPage(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);
		String kjkm = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		String bmbh = Validate.isNullToDefaultString(pd.getString("bmbh"), "");
		String xmbh = Validate.isNullToDefaultString(pd.getString("xmbh"), "");
		String bhwjzpz = Validate.isNullToDefaultString(pd.getString("bhwjzpz"),"1");
		String sysYear = Validate.isNullToDefaultString(zzService.getKjqj(session).get("ZTND"), DateUtil.getYear());
		String syskJqj = Validate.isNullToDefaultString(zzService.getKjqj(session).get("KJQJ"), "12");
		String pznd = Validate.isNullToDefaultString(pd.getString("kjnd"),sysYear);
		String qskjqj = Validate.isNullToDefaultString(pd.getString("qskjqj"), "2018-01");
		String jskjqj = "";
		if(Integer.parseInt(syskJqj)>9){
			jskjqj = Validate.isNullToDefaultString(pd.getString("jskjqj"), "2018-"+syskJqj);
		}else{
			jskjqj = Validate.isNullToDefaultString(pd.getString("jskjqj"), "2018-0"+syskJqj);
		}
		String qskjqjyf = qskjqj.substring(5,7);
		String jskjqjyf = jskjqj.substring(5,7);
		mv.addObject("jskjqjyf",jskjqjyf);
		mv.addObject("qskjqjyf",qskjqjyf);
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kmbh = WindowUtil.getText(kjkm);
		List<Map<String,Object>> rzzList = new ArrayList<Map<String,Object>>();
//		if(Validate.noNull(kmbh)){
//			rzzList = zzService.getRzzList(sszt, kjzd, pznd, kmbh, bmbh, xmbh, bhwjzpz, qskjqj, jskjqj,pznd,treesearch);
//		}
		//把日总账放到session中
//		System.err.println("rzzList="+rzzList);
//		session.setAttribute("rzzList", rzzList);
		mv.addObject("rzzList",rzzList);
		mv.addObject("qskjqj",qskjqj);
		mv.addObject("treesearch",treesearch);
		mv.addObject("jskjqj",jskjqj);
		mv.addObject("pznd",pznd);
		mv.addObject("kjkm",kjkm);
		mv.addObject("kmmc",Validate.isNullToDefaultString(pd.getString("kmmc"),""));
		mv.setViewName("kjhs/bbzx/rzz");
		return mv;
	}
	/**
	 * 跳转到月总账页面（不勾选按日汇总）,默认进入该页面
	 * @param session
	 * @return
	 */
	@RequestMapping("goYzzPage")
	public ModelAndView goYzzPage(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);
		String kmbh2 = Validate.isNullToDefaultString(pd.getString("kmbh"),"10");
		String kjkm = Validate.isNullToDefaultString(pd.getString("kjkm"),kmbh2);
		String bmbh = Validate.isNullToDefaultString(pd.getString("bmbh"), "");
		String xmbh = Validate.isNullToDefaultString(pd.getString("xmbh"), "");
		String bhwjzpz = Validate.isNullToDefaultString(pd.getString("bhwjzpz"),"0");
		String pznd = Validate.isNullToDefaultString(pd.getString("kjnd"), DateUtil.getYear());
		String qskjqj = Validate.isNullToDefaultString(pd.getString("qskjqj"), "1");
		String jskjqj = Validate.isNullToDefaultString(pd.getString("jskjqj"), "12");
		String sfarhzcx = Validate.isNullToDefaultString(pd.getString("sfarhzcx"), "0");
		String kmbh = WindowUtil.getText(kjkm);

		List<Map<String,Object>> yzzList = zzService.getYzzList(sszt, kjzd, pznd, kmbh, bmbh, xmbh, bhwjzpz, qskjqj, jskjqj);
		session.setAttribute("yzzList", yzzList);
		mv.addObject("yzzList",yzzList);
		mv.addObject("qskjqj",qskjqj);
		mv.addObject("jskjqj",jskjqj);
		mv.setViewName("kjhs/bbzx/yzz");
		return mv;
	}
	//页面之间的跳转
	@RequestMapping("/zjc")
	public ModelAndView PageSkip(HttpServletRequest request) {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		
		mv.setViewName("kjhs/bbzx/mxztc");
		return mv;
	}
	
	
}

