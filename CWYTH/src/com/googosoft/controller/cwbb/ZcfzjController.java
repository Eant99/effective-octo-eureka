package com.googosoft.controller.cwbb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.PageService;
import com.googosoft.service.cwbb.ZcfzbService;
import com.googosoft.service.cwbb.ZcfzbjService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 资产负债表
 * 
 * @author googosoft
 * 
 */
@Controller
@RequestMapping(value = "/zcfzj")
public class ZcfzjController extends BaseController {
	@Resource(name="zcfzbService")
	private ZcfzbService zcfzbService;
	
	
	@Resource(name="zcfzbjService")
	private ZcfzbjService zcfzbjService;
	@Resource(name = "pageService")
	private PageService pageService;
	/**
	 * 从上一次保存的位置获取资产负债表（月度）
	 */
	@RequestMapping(value="goZcfzPage")
	public ModelAndView goZcfzPage(HttpSession session) {
		PageData pd=this.getPageData();
		ModelAndView mv=this.getModelAndView();
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String login = LUser.getGuid();
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//记账
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//结转
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),Constant.getztid(session));
		String jsyf=Validate.isNullToDefaultString(pd.get("jsyf"), DateUtil.getYearMonth());
		String ksyf=jsyf.substring(0, 4)+"-01";
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "0");//0月度1年度
		List list = new ArrayList<Map<String,Object>>();
		mv.setViewName("bbzx/cwbb/zcfzjlist");//要跳转的jsp页面
		//判断是否有存的值
		int result = zcfzbjService.checkzcfzb(bblx, jsyf);
		if(result>0){
			list = zcfzbjService.getResultList(bblx, jsyf);			
		}else{
			list = zcfzbjService.getResultListByPro1(ksyf, jsyf, "1", sfjz, ztgid, jzpz);
		}
		mv.addObject("jsyf", jsyf);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		return mv;
	}
	/**
	 * 从上一次保存的位置获取资产负债表（年度）
	 */
	@RequestMapping(value="goZcfzyearPage")
	public ModelAndView goZcfzyearPage(HttpSession session) {
		PageData pd=this.getPageData();
		ModelAndView mv=this.getModelAndView();
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String login = LUser.getGuid();
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//记账
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//结转
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),Constant.getztid(session));
		String bbnd=Validate.isNullToDefaultString(pd.getString("bbnd"), DateUtil.getYear());
		String ksyf=bbnd+"-01";
		String jsyf=bbnd+"-12";
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");//0月度1年度
		List list = new ArrayList<Map<String,Object>>();
		mv.setViewName("bbzx/cwbb/zcfzbjyear");//要跳转的jsp页面
		//判断是否有存的值
		int result = zcfzbjService.checkzcfzb(bblx, bbnd);
		if(result>0){
			list = zcfzbjService.getResultList(bblx, bbnd);			
		}else{
			list = zcfzbjService.getResultListByPro1(ksyf, jsyf, "1", sfjz, ztgid, jzpz);
		}
		mv.addObject("bbnd", bbnd);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		return mv;
	}
	/**
	 * 点击条件后查询资产负债表（月度）
	 */
	@RequestMapping(value="/zcfzList")
	public ModelAndView zcfzList(HttpSession session){
		PageData pd = this.getPageData();
		String login = LUser.getGuid();
	    String ztgid1 = Constant.getztid(session);//账套编号
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "0");//0月度1年度
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//记账
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//结转
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),ztgid1);
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String jsyf=Validate.isNullToDefaultString(pd.get("jsyf"), DateUtil.getYearMonth());
		String ksyf=jsyf.substring(0, 4)+"-01";
		ModelAndView mv = this.getModelAndView();//视图转化器
		List list = new ArrayList<Map<String,Object>>();
		mv.setViewName("bbzx/cwbb/zcfzjlist");//要跳转的jsp页面
		list = zcfzbjService.getResultListByPro1(ksyf, jsyf, "1", sfjz, ztgid,  jzpz);
		mv.addObject("jsyf", jsyf);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		return mv;
	}
	/**
	 * 从存储过程获取新的资产负债表数据
	 */
	@RequestMapping(value="/zcfzListNew")
	public ModelAndView zcfzListNew(HttpSession session){
		PageData pd = this.getPageData();
		String login = LUser.getGuid();
	    String ztgid1 = Constant.getztid(session);//账套编号
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//记账
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//结转
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),ztgid1);
		List<Map<String,Object>> months = zcfzbService.getMonth();
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String bbnd = "";
		String ksyf=Validate.isNullToDefaultString(pd.getString("ksyf"), "01");
		String jsyf=Validate.isNullToDefaultString(pd.getString("jsyf"), DateUtil.getMonth());
		bbnd = Validate.isNullToDefaultString(pd.getString("bbnd"),DateUtil.getYear());
		ModelAndView mv = this.getModelAndView();//视图转化器
		List list = new ArrayList<Map<String,Object>>();
		mv.setViewName("bbzx/cwbb/zcfzbnew");//要跳转的jsp页面
		list = zcfzbjService.getResultListByProNew(bbnd+"-"+ksyf,bbnd+"-"+jsyf, "1", sfjz, "1", ztgid, jzpz);
		mv.addObject("months",months);
		mv.addObject("ksyf",ksyf);
		mv.addObject("jsyf", jsyf);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("bbnd",bbnd);
		return mv;
	}
	/**
	 * 从上一次保存的位置查询新的资产负债表
	 * 
	 * @return
	 */
	@RequestMapping(value="/goZcfzbnewPage")
	public ModelAndView goZcfzbnewPage(HttpSession session) {
		PageData pd=this.getPageData();
		ModelAndView mv=this.getModelAndView();
		String login = LUser.getGuid();
	    String ztgid = Constant.getztid(session);//账套编号
	    List<Map<String,Object>> months = zcfzbService.getMonth();
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "1");//记账
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "1");//结转
		String bbnd = Validate.isNullToDefaultString(pd.getString("bbnd"), DateUtil.getYear());//报表年份
		String ksyf=Validate.isNullToDefaultString(pd.getString("ksyf"), "01");
		String jsyf=Validate.isNullToDefaultString(pd.getString("jsyf"), DateUtil.getMonth());
		//判断上一次存储是否有值
		List list = new ArrayList<Map<String,Object>>();
		list=zcfzbjService.getZcfzbnewList(ztgid,bbnd+"-"+ksyf,bbnd+"-"+jsyf,jzpz,sfjz);
		if(list.isEmpty()) {
			list = new ArrayList<Map<String,Object>>();
			list = zcfzbjService.getResultListByProNew(bbnd+"-"+ksyf,bbnd+"-"+jsyf, "1", sfjz, "1", ztgid, jzpz);
		}
		else {
		Map<String,Object> map=(Map<String, Object>) list.get(0);
		ksyf=Validate.isNullToDefaultString(map.get("ksyf"),"").substring(5, 7);
		jsyf=Validate.isNullToDefaultString(map.get("jsyf"),"").substring(5, 7);
		bbnd=Validate.isNullToDefaultString(map.get("ksyf"),"").substring(0, 4);
		jzpz=Validate.isNullToDefaultString(map.get("jzpz"),"0");
		sfjz=Validate.isNullToDefaultString(map.get("sfjz"),"0");
		}
		mv.addObject("list",list);
		mv.addObject("months",months);
		mv.addObject("ksyf",ksyf);
		mv.addObject("jsyf",jsyf);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("bbnd",bbnd);
		mv.setViewName("bbzx/cwbb/zcfzbnew");
		return mv;
	}
	/**
	 * 点击条件后查询资产负债表（年度）
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/zcfzbyear")
	public ModelAndView zcfzbyear(HttpSession session){
		PageData pd = this.getPageData();
		String login = LUser.getGuid();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");//0月度1年度
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"), Constant.getztid(session));
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String bbnd=Validate.isNullToDefaultString(pd.getString("bbnd"), DateUtil.getYear());
		String ksyf=bbnd+"-01";
		String jsyf=bbnd+"-12";
		ModelAndView mv = this.getModelAndView();//视图转化器
		List list = new ArrayList<Map<String,Object>>();
		mv.setViewName("bbzx/cwbb/zcfzbjyear");//要跳转的jsp页面
		list = zcfzbjService.getResultListByPro1(ksyf, jsyf, "1", sfjz, ztgid, jzpz);
		mv.addObject("bbnd", bbnd);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
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
			bool = zcfzbjService.doSave(list);
		}
		return gson.toJson(bool);
	}
	/**
	 * 点击保存新表
	 * @return
	 */
	@RequestMapping(value = "/doSaveNew", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveNew() {
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		boolean bool = false;
		if (list.size()>0) {
			bool = zcfzbjService.doSaveNew(list);
		}
		return gson.toJson(bool);
	}
	/**
	 * 导出收入费用(年度)信息Excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/expExcel", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcel() {
		PageData pd = this.getPageData();
		// 临时文件名
		String file = System.currentTimeMillis() + "";
		// 文件绝对路径
		String realfile = this.getRequest().getServletContext()
				.getRealPath("\\")
				+ "WEB-INF\\file\\excel\\" + file + ".xls";
		// 下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		String sql = "select zc, qmye1,ncye1,fzhjzc,qmye2,ncye2 from TEMP_CWBB_ZCFZB_FZ t";
		
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("zc");
		m.setShowname("资产");
		mlist.add(m);
		m = null;
		m = null;
		
		m = new M_largedata();
		m.setName("qmye1");
		m.setShowname("期末余额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ncye1");
		m.setShowname("年初余额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("fzhjzc");
		m.setShowname("负债和净资产");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("qmye2");
		m.setShowname("期末余额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ncye2");
		m.setShowname("年初余额");
		mlist.add(m);
		m = null;
		
		System.err.println("---"+realfile);
		// 导出方法
		pageService.ExpExcel(sql, realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpSession session) {
		PageData pd = this.getPageData();
		List list = new ArrayList<Map<String,Object>>();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "0");//0月度1年度
		String ztgid1 = Constant.getztid(session);//账套编号
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),ztgid1);
		//判断年度月度来设置开始结束月份
		String sysDate = "";
		String ksyf="";
		String jsyf="";
		if(bblx.equals("0")) {
		sysDate = Validate.isNullToDefaultString(pd.getString("jsyf"),DateUtil.getYearMonth());
		jsyf=sysDate;
		ksyf=sysDate.substring(0, 4)+"-01";
		}else {
		sysDate = Validate.isNullToDefaultString(pd.getString("bbnd"),DateUtil.getYear());
		ksyf=sysDate+"-01";
		jsyf=sysDate+"-12";
		}
		int result = zcfzbjService.checkzcfzb(bblx, sysDate);
		if(result>0){
			list = zcfzbjService.getResultList(bblx, sysDate);			
		}else{
			list = zcfzbjService.getResultListByPro1(ksyf, jsyf, "1", sfjz, ztgid, jzpz);
		}
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zcfzbService.expExcel2(realfile, shortfileurl,searchValue,bblx);
	}
	/**
	 * 导出新资产负债表
	 * @param session
	 * @return
	 */
	@RequestMapping("/expExcel3")
	@ResponseBody
	public Object Info3(HttpSession session) {
		PageData pd = this.getPageData();
		String ztgid = Constant.getztid(session);//账套编号
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//记账
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//结转
		String ksyf=Validate.isNullToDefaultString(pd.getString("ksyf"), "01");
		String jsyf=Validate.isNullToDefaultString(pd.getString("jsyf"), DateUtil.getMonth());
		List list = new ArrayList<Map<String,Object>>();
		list=zcfzbjService.getZcfzbnewList(ztgid,ksyf,jsyf,jzpz,sfjz);
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zcfzbService.expExcel3(realfile, shortfileurl,list);
	}
	/**
	 * 跳转打印页面
	 * @return
	 */
	@RequestMapping(value = "/goPrint")
	public ModelAndView goPrint(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "0");//0月度1年度
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"), "0");
		List list = new ArrayList<Map<String,Object>>();
		//判断年度月度来设置开始结束月份
		String sysDate = "";
		String ksyf="";
		String jsyf="";
		sysDate = Validate.isNullToDefaultString(pd.getString("jsyf"),DateUtil.getYearMonth());
		jsyf=sysDate;
		ksyf=sysDate.substring(0, 4)+"-01";
		int result = zcfzbjService.checkzcfzb(bblx, sysDate);
		if(result>0){
			list = zcfzbjService.getResultList(bblx, sysDate);			
		}else{
			list = zcfzbjService.getResultListByPro1(ksyf, jsyf, "1", sfjz, ztgid, jzpz);
		}
		mv.addObject("list", list);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("jsyf",jsyf);
		mv.setViewName("bbzx/cwbb/print_jzcfz");
		return mv;
	} 
	/**
	 * 跳转打印页面
	 * @return
	 */
	@RequestMapping(value = "/goPrintyear")
	public ModelAndView goPrintyear(HttpSession session) {
		PageData pd = this.getPageData();
		String login = LUser.getGuid();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");//0月度1年度
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"), Constant.getztid(session));
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		//判断年度月度来设置开始结束月份
		String sysDate = "";
		String ksyf="";
		String jsyf="";
		sysDate = Validate.isNullToDefaultString(pd.getString("bbnd"),DateUtil.getYear());
		ksyf=sysDate+"-01";
		jsyf=sysDate+"-12";
		ModelAndView mv = this.getModelAndView();//视图转化器
		List list = new ArrayList<Map<String,Object>>();
		mv.setViewName("bbzx/cwbb/print_zcfzjyear");//要跳转的jsp页面	
		int result = zcfzbjService.checkzcfzb(bblx, sysDate);
		if(result>0){
			list = zcfzbjService.getResultList(bblx, sysDate);			
		}else{
			list = zcfzbjService.getResultListByPro1(ksyf, jsyf, "1", sfjz, ztgid, jzpz);
		}
		System.err.println("输出list"+list);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("bbnd",sysDate);
		return mv;
	} 
	/**
	 * 跳转打印页面（新资产负债表）
	 */
	@RequestMapping(value="/goPrintNew")
	@ResponseBody
	public ModelAndView goPrintNew(HttpSession session) {
		PageData pd=this.getPageData();
		ModelAndView mv=this.getModelAndView();
		String login = LUser.getGuid();
	    String ztgid = Constant.getztid(session);//账套编号
	    List<Map<String,Object>> months = zcfzbService.getMonth();
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//记账
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//结转
		String bbnd = Validate.isNullToDefaultString(pd.getString("bbnd"), DateUtil.getYear());//报表年份
		String ksyf=Validate.isNullToDefaultString(pd.getString("ksyf"), "01");
		String jsyf=Validate.isNullToDefaultString(pd.getString("jsyf"), DateUtil.getMonth());
		List list = new ArrayList<Map<String,Object>>();
		list=zcfzbjService.getZcfzbnewList(ztgid,ksyf,jsyf,jzpz,sfjz);
		mv.addObject("list",list);
		mv.addObject("months",months);
		mv.addObject("ksyf",ksyf);
		mv.addObject("jsyf",jsyf);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("bbnd",bbnd);
		mv.setViewName("bbzx/cwbb/print_zcfzbnew");
		return mv;
	}
}
