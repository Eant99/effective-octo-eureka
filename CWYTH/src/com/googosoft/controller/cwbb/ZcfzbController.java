package com.googosoft.controller.cwbb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.cwbb.ZcfzbService;
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
@RequestMapping(value = "/zcfz")
public class ZcfzbController extends BaseController {
	@Resource(name="zcfzbService")
	private ZcfzbService zcfzbService;
	
	@Resource(name = "pageService")
	private PageService pageService;
	/**
	 * 跳转资产负债表
	 */
	@RequestMapping(value="/zcfzList")
	public ModelAndView zcfzList(HttpSession session){
		PageData pd = this.getPageData();
		String login = LUser.getGuid();
	    String ztgid1 = Constant.getztid(session);//账套编号
	    System.err.println("++++++++++++++++++++++++"+ztgid1);
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "0");//0月度1年度
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),ztgid1);
		System.out.println("++++++++++++++++++"+sfjz);
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		sysDate = Validate.isNullToDefaultString(pd.getString("bbyf"), sdf.format(date));//
		ModelAndView mv = this.getModelAndView();//视图转化器
		List list = new ArrayList<Map<String,Object>>();
		mv.setViewName("bbzx/cwbb/zcfzlist");//要跳转的jsp页面
		list = zcfzbService.getResultListByPro(sysDate, sysDate, "1", sfjz, "1", ztgid, bblx, jzpz);
		System.err.println("输出list"+list);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("sysDate",sysDate);
		return mv;
	}
	@RequestMapping(value="/zcfzbyear")
	public ModelAndView zcfzbyear(HttpSession session){
		PageData pd = this.getPageData();
		String login = LUser.getGuid();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");//0月度1年度
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"), Constant.getztid(session));
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
		sysDate = Validate.isNullToDefaultString(pd.getString("bbnd"), sdf.format(date));//
		//String ztgid = Constant.getztid(session);//账套编号
		ModelAndView mv = this.getModelAndView();//视图转化器
		List list = new ArrayList<Map<String,Object>>();
		mv.setViewName("bbzx/cwbb/zcfzbyear");//要跳转的jsp页面
		list = zcfzbService.getResultListByPro(sysDate, sysDate, "1", sfjz, "1",ztgid, bblx, jzpz);
		System.err.println("输出list"+list);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("sysDate",sysDate);
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
		System.err.println(list);
		boolean bool = false;
		if (list.size()>0) {
			bool = zcfzbService.doSave(list);
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
		//导出数据
	/*	String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");
		String sysDate = Validate.isNullToDefaultString(pd.getString("sysDate"), "");
		String sszt = Validate.isNullToDefaultString(pd.getString("sszt"), "googosoft");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "1");
		String bzdw = Validate.isNullToDefaultString(pd.getString("bzdw"), "1");*/
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
	public Object Info(HttpSession session) {
		PageData pd = this.getPageData();
		String searchValue = pd.getString("searchJson");
		String flag = pd.getString("foo");
		
//		 String ztgid1 = Constant.getztid(session);//账套编号
//		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "0");//0月度1年度
//		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
//		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
//		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),ztgid1);
//		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
//		Date date = new Date();
//		String sysDate = "";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//		sysDate = Validate.isNullToDefaultString(pd.getString("bbyf"), sdf.format(date));//
//		List list = new ArrayList<Map<String,Object>>();
//		list = zcfzbService.getResultListByPro(sysDate, sysDate, "1", sfjz, "1", ztgid, bblx, jzpz);
		
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zcfzbService.expExcels(realfile, shortfileurl,searchValue,flag);
	}
	@RequestMapping("/expExcel3")
	@ResponseBody
	public Object Info2(HttpSession session) {
		PageData pd = this.getPageData();
		String searchValue = pd.getString("searchJson");
		String flag = pd.getString("foo");
		
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");//0月度1年度
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"), Constant.getztid(session));
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		sysDate = Validate.isNullToDefaultString(pd.getString("bbnd"), sdf.format(date));//
		List list = new ArrayList<Map<String,Object>>();
		list = zcfzbService.getResultListByPro(sysDate, sysDate, "1", sfjz, "1",ztgid, bblx, jzpz);
		
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zcfzbService.expExcel(realfile, shortfileurl,searchValue,flag,list);
	}
	/**
	 * 跳转打印页面
	 * @return
	 */
	@RequestMapping(value = "/goPrint")
	public ModelAndView goPrint(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "0");
		String bbyf = Validate.isNullToDefaultString(pd.getString("bbyf"), "");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"), "0");
		List list = new ArrayList<Map<String,Object>>();
	    list = zcfzbService.getResultListByPro("2017-12-15", "2017-12", "1", sfjz, "1", ztgid, bblx, jzpz);

		mv.addObject("list", list);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("sysDate",bbyf);
		mv.setViewName("bbzx/cwbb/print_zcfz");
		return mv;
	} 
	/**
	 * 跳转打印页面
	 * @return
	 */
	@RequestMapping(value = "/goPrintyear")
	public ModelAndView goPrintyear(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "0");
		String bbnd = Validate.isNullToDefaultString(pd.getString("bbnd"), "");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");
		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"), "0");
		List list = new ArrayList<Map<String,Object>>();
	    list = zcfzbService.getResultListByPro(bbnd, bbnd, "1", sfjz, "1", ztgid, bblx, jzpz);
		mv.addObject("list", list);
		mv.addObject("bblx",bblx);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.addObject("sysDate",bbnd);
		mv.setViewName("bbzx/cwbb/print_zcfzyear");
		return mv;
	} 

}
