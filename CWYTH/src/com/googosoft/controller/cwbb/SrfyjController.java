package com.googosoft.controller.cwbb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.PageService;
import com.googosoft.service.cwbb.SrfyjService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 收入费用表(旧)
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value = "/srfyj")
public class SrfyjController extends BaseController {
	@Resource(name="srfyjService")
	private SrfyjService srfyService;
	@Resource(name = "pageService")
	private PageService pageService;
	
	@RequestMapping(value="/toUrl")
	public ModelAndView toSrfyURL(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String status = Validate.isNullToDefaultString(pd.getString("status"), "year");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		Date date = new Date();
		String sysDate = "";
		String login = LUser.getGuid();//得到当前用户的guid
		String sszt = Validate.isNullToDefaultString(Constant.getztid(req.getSession()), "googosoft");
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");
		String url ="bbzx/cwbb/srzcbjyear";
		Map<String,Object> bzdw =  srfyService.getBzdw();//学校信息
		List list = new ArrayList<Map<String,Object>>();
		if("year".equals(status)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			sysDate = Validate.isNullToDefaultString(pd.getString("ny"), sdf.format(date));
			bblx = "0";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			sysDate = Validate.isNullToDefaultString(pd.getString("ny"), sdf.format(date));
			url ="bbzx/cwbb/srzcbjmonth";			
			
		}
		
		//bblx==1，sysdate===当前时间取得年和月  sszt==学生账套   jzpz==0
		int result = srfyService.checkSrfy(bblx, sysDate, sszt,jzpz);
		if(result>0){
			list = srfyService.getResultList(bblx, sysDate, sszt, jzpz);
		}else{
			System.out.println(bblx+"  "+sysDate+"  "+sszt+"  "+jzpz+"  "+bzdw.get("GUID")+" ");
			String kjzd = srfyService.getKjzd(sszt,sysDate);//会计制度
			list = srfyService.getResultListByPro(bblx, sysDate, sszt, jzpz, bzdw.get("GUID")+"",kjzd);
		}
		//数据集合
		mv.addObject("list",list);
		mv.addObject("sysDate", sysDate);
		mv.addObject("login", login);
		mv.addObject("sszt", sszt);
		mv.addObject("jzpz", jzpz);
		mv.addObject("bblx", bblx);
		mv.addObject("bzdw", bzdw);
		mv.setViewName(url);//要跳转的jsp页面
		return mv;
	}
	/**
	 * 从存储过程获取收入支出决算总表数据
	 */
	@RequestMapping(value="/srzcjszbList")
	public ModelAndView getSrzcjszbPage(HttpSession session) {
		PageData pd = this.getPageData();
		String login = LUser.getGuid();
	    String ztgid1 = Constant.getztid(session);//账套编号
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "1");//记账
		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "1");//结转
		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),ztgid1);
		Map<String,Object> bzdw =  srfyService.getBzdw();//编制单位
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
		String jsyf = Validate.isNullToDefaultString(pd.getString("jsyf"), sdf.format(date));//
		ModelAndView mv = this.getModelAndView();//视图转化器
		List list = new ArrayList<Map<String,Object>>();
		list=srfyService.getSrzcjszbListByPro(jsyf,sfjz,ztgid,jzpz);
		mv.setViewName("bbzx/cwbb/srzcjszb");//要跳转的jsp页面
		mv.addObject("jsyf", jsyf);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		return mv;
	}
	/**
	 * 从上一次保存的位置查询新的收入支出决算总表
	 * 
	 * @return
	 */
	@RequestMapping(value="/goSrzcjszbPage")
	public ModelAndView goSrzcjszbPage(HttpSession session) {
		PageData pd=this.getPageData();
		ModelAndView mv=this.getModelAndView();
		String login = LUser.getGuid();
	    String ztgid = Constant.getztid(session);//账套编号
		Map<String,Object> bzdw =  srfyService.getBzdw();//编制单位
		List list = new ArrayList<Map<String,Object>>();
		String jsyf=Validate.isNullToDefaultString(pd.getString("jsyf"),DateUtil.getYearMonth());
		String jzpz=Validate.isNullToDefaultString(pd.getString("jzpz"),"1");;
		String sfjz=Validate.isNullToDefaultString(pd.getString("sfjz"),"1");;
		System.err.println(jsyf+jzpz+sfjz);
		list=srfyService.getSrzcjszbList(jsyf,jzpz,sfjz);
		//判断存储表中是否为空
		if(list.size()==0) {
			list=srfyService.getSrzcjszbListByPro(jsyf,sfjz,ztgid,jzpz);
		}else {
		Map<String,Object> map=(Map<String, Object>) list.get(0);
		jsyf=Validate.isNullToDefaultString(map.get("jsyf"),"");
		jzpz=Validate.isNullToDefaultString(map.get("jzpz"),"1");
		sfjz=Validate.isNullToDefaultString(map.get("sfjz"),"1");
		}
		mv.addObject("list",list);
		mv.addObject("jsyf",jsyf);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.setViewName("bbzx/cwbb/srzcjszb");
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
			bool = srfyService.doSave(list);
		}
		return gson.toJson(bool);
	}
	/**
	 * sql语句
	 */
	public String getSql(int result,String bblx,String sysDate,String sszt,String jzpz,String bzdw){
		StringBuffer sql = new StringBuffer();
		if(result>0){
			sql.append(" SELECT");
			sql.append("  REPLACE((REPLACE(T.XMBH, '<b>','')),'</b>','') AS XMMC, ");
//			sql.append(" T.XMMC AS XMMC,");
			sql.append(" DECODE(NVL(T.BYS,0.00),0.00,'',TO_CHAR(ROUND(T.BYS,2),'FM999,999,999,000.00')) AS BYS,");
			sql.append(" DECODE(NVL(T.BNLJS,0.00),0.00,'',TO_CHAR(ROUND(T.BNLJS,2),'FM999,999,999,000.00')) AS BNLJS");
			sql.append(" FROM CW_JSRFYB T");
			sql.append(" LEFT JOIN CW_XXXXB X ON X.GUID=T.BZDW");
			sql.append(" WHERE 1=1");
			sql.append(" AND T.BBLX='" + bblx + "'");
			sql.append(" AND T.NY='" + sysDate + "'");
			sql.append(" AND T.ZTBH='" + sszt + "'");
			sql.append(" AND T.JZPZ='"+jzpz+"'");
			sql.append(" ORDER BY HC");
		}else{
			sql.append(" SELECT");
			sql.append("  REPLACE((REPLACE(T.XMBH, '<b>','')),'</b>','') AS XMMC, ");
//			sql.append(" T.XMMC AS XMMC,");
			sql.append(" DECODE(NVL(T.BYS,0.00),0.00,'',TO_CHAR(ROUND(T.BYS,2),'FM999,999,999,000.00')) AS BYS,");
			sql.append(" DECODE(NVL(T.BNLJS,0.00),0.00,'',TO_CHAR(ROUND(T.BNLJS,2),'FM999,999,999,000.00')) AS BNLJS");
			sql.append(" FROM TEMP_XMXXB T");
			sql.append(" WHERE 1=1 AND T.LOGIN='"+LUser.getGuid()+"' order by xmbh asc ");
		}
		return sql.toString();
	}
	/**
	 * 导出收入费用(年度)信息Excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/expExcel", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcel(HttpServletRequest req,HttpServletResponse res) {
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
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");
		String sysDate = Validate.isNullToDefaultString(pd.getString("sysDate"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(req.getSession()), "googosoft");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "1");
		String bzdw = Validate.isNullToDefaultString(pd.getString("bzdw"), "1");
		int result = srfyService.checkSrfy(bblx, sysDate, sszt,jzpz);
		String sql = "";
		sql = getSql(result, bblx, sysDate, sszt, jzpz, bzdw);
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("xmmc");
		m.setShowname("项目");
		mlist.add(m);
		m = null;
		m = null;
		
		m = new M_largedata();
		m.setName("bys");
		if(filedisplay.contains("年度")){
			m.setShowname("本年数");
		}else{
			m.setShowname("本月数");
		}
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bnljs");
		if(filedisplay.contains("年度")){
			m.setShowname("上年累计数");
		}else{
			m.setShowname("本年累计数");
		}
		mlist.add(m);
		m = null;
		System.err.println("---"+realfile);
		// 导出方法
		pageService.ExpExcel(sql, realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest req) {
		PageData pd = this.getPageData();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");
		String sysDate = Validate.isNullToDefaultString(pd.getString("sysDate"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(req.getSession()), "googosoft");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "1");
		String bzdw = Validate.isNullToDefaultString(pd.getString("bzdw"), "1");
		int result = srfyService.checkSrfy(bblx, sysDate, sszt,jzpz);
		String sql = "";
		sql = getSql(result, bblx, sysDate, sszt, jzpz, bzdw);
		
		String flag = pd.getString("flag");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.srfyService.expExcel(realfile, shortfileurl,searchValue,sql,flag);
	}
	/**
	 * 跳转打印页面
	 * @return
	 */
	@RequestMapping(value = "/goPrint")
	public ModelAndView goPrint(HttpServletRequest req,HttpServletResponse res,HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");
		String sysDate = Validate.isNullToDefaultString(pd.getString("sysDate"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(req.getSession()), "googosoft");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "1");
		String bzdw = Validate.isNullToDefaultString(pd.getString("bzdw"), "1");
		String status = Validate.isNullToDefaultString(pd.getString("status"), "");
		int result = srfyService.checkSrfy(bblx, sysDate, sszt,jzpz);
		List list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map =  srfyService.getBzdw();
		if(result>0){
			list = srfyService.getResultList(bblx, sysDate, sszt, jzpz);
		}else{
			String kjzd = CommonUtil.getKjzd(session);//会计制度
			list = srfyService.getResultListByPro(bblx, sysDate, sszt, jzpz, map.get("GUID")+"",kjzd);
		}
		mv.addObject("list", list);
		mv.addObject("map", map);
		mv.addObject("status", status);
		mv.addObject("bblx", bblx);
		mv.addObject("jzpz", jzpz);
		mv.addObject("sysDate", sysDate);
		if("year".equals(status)){
			mv.addObject("two", "本年数");
			mv.addObject("three", "上年累计数");
			mv.addObject("title", "年度");
		}else{
			mv.addObject("two", "本月数");
			mv.addObject("three", "本年累计数");
			mv.addObject("title", "月度");
		}
		mv.setViewName("bbzx/cwbb/print_jsrfy");
		return mv;
	} 
	/**
	 * 打印收入支出决算总表
	 */
	@RequestMapping(value="/goPrintSrzcjszb")
	@ResponseBody
	public ModelAndView goPrintNew(HttpSession session) {
		PageData pd=this.getPageData();
		ModelAndView mv=this.getModelAndView();
		String login = LUser.getGuid();
	    String ztgid = Constant.getztid(session);//账套编号
		Map<String,Object> bzdw =  srfyService.getBzdw();//编制单位
		List list = new ArrayList<Map<String,Object>>();
		String jsyf=Validate.isNullToDefaultString(pd.getString("jsyf"),DateUtil.getYearMonth());
		String jzpz=Validate.isNullToDefaultString(pd.getString("jzpz"),"0");;
		String sfjz=Validate.isNullToDefaultString(pd.getString("sfjz"),"0");;
		list=srfyService.getSrzcjszbList(jsyf,jzpz,sfjz);
		mv.addObject("list",list);
		mv.addObject("jsyf",jsyf);
		mv.addObject("list",list);
		mv.addObject("login",login);
		mv.addObject("jzpz",jzpz);
		mv.addObject("sfjz",sfjz);
		mv.addObject("bzdw",bzdw);
		mv.addObject("ztgid",ztgid);
		mv.setViewName("bbzx/cwbb/print_srzcjszb");
		return mv;
	}
	/**
	 * 导出收入支出决算总表Excel
	 */
	@RequestMapping("/expExcel3")
	@ResponseBody
	public Object expExcel3() {
		PageData pd = this.getPageData();
		String jsyf=Validate.isNullToDefaultString(pd.getString("jsyf"),DateUtil.getYearMonth());
		String jzpz=Validate.isNullToDefaultString(pd.getString("jzpz"),"0");;
		String sfjz=Validate.isNullToDefaultString(pd.getString("sfjz"),"0");;
		StringBuffer sql=new StringBuffer();
		sql.append("select * from cw_srzcjszb");
		sql.append(" where jsyf='"+jsyf+"'");
		sql.append(" and jzpz='"+jzpz+"'");
		sql.append(" and sfjz='"+sfjz+"'");
		sql.append(" order by to_number(bh)");
		List<M_largedata> mlist = new ArrayList<M_largedata>();//存放第一行标题
		List<M_largedata> mlist1 = new ArrayList<M_largedata>();//存放第二行标题
		List<M_largedata> mlist2 = new ArrayList<M_largedata>();//存放第三行标题
		List<M_largedata> mlist3 = new ArrayList<M_largedata>();//存放数据
		List<List<M_largedata>> tlist = new ArrayList<List<M_largedata>>();
		M_largedata m = new M_largedata();
		
		//导出初始化列表
		m.setIsmerge(true);//合并单元格
		m.setEcol(3);//合并行0开始
		m.setShowname("收入");//第一行
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setIsmerge(true);//合并单元格
		m.setEcol(7);//合并行0开始
		m.setShowname("支出");//第一行
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("项目");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("行次");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("年初预算数");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("期末数");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("项目(按功能分类)");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("行次");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("年初预算数");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("期末数");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("项目（按支出性质和经济分类）");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("行次");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("年初预算数");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("期末数");//第二行
		mlist1.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("栏次");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("1");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("2");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("栏次");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("3");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("4");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("栏次");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("5");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setShowname("6");//第三行
		mlist2.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SR");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("HC1");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColstyle("double");
		m.setColtype("Number");
		m.setName("NCYSS1");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColstyle("double");
		m.setColtype("Number");
		m.setName("QMS1");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ZC1");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("HC2");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColstyle("double");
		m.setColtype("Number");
		m.setName("NCYSS2");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColstyle("double");
		m.setColtype("Number");
		m.setName("QMS2");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ZC2");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("HC3");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColstyle("double");
		m.setColtype("Number");
		m.setName("NCYSS3");//数据
		mlist3.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColstyle("double");
		m.setColtype("Number");
		m.setName("QMS3");//数据
		mlist3.add(m);
		m = null;
		
		tlist.add(mlist);
		tlist.add(mlist1);
		tlist.add(mlist2);
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel\\" + shortfileurl;
		String  name = pd.getString("filename");
	    pageService.ExpExcel(sql.toString(),realfile,name,mlist3,tlist);
		return "{\"url\":\"excel\\\\"+shortfileurl+"\"}";
	}
	/**
	 * 保存收入支出决算总表
	 */
	@RequestMapping(value = "/doSaveSrzcjszb", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveNew() {
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String sfjz=this.getPageData().getString("sfjz");
		String jzpz=this.getPageData().getString("jzpz");
		System.err.println("****************="+jsonStr+"");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		System.out.println("@@@@@@@@@="+ajson);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		System.err.println("aaaaaa="+list);
		boolean bool = false;
		if (list.size()>0) {
			bool = srfyService.doSaveSrzcjszb(list,sfjz,jzpz);
		}
		return gson.toJson(bool);
	}
}
