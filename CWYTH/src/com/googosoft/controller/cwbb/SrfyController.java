package com.googosoft.controller.cwbb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.googosoft.service.base.PageService;
import com.googosoft.service.cwbb.SrfyService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 收入费用表
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value = "/srfy")
public class SrfyController extends BaseController {
	@Resource(name="srfyService")
	private SrfyService srfyService;
	@Resource(name = "pageService")
	private PageService pageService;
	
	@RequestMapping(value="/toUrl")
	public ModelAndView toSrfyURL(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String status = Validate.isNullToDefaultString(pd.getString("status"), "year");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		Date date = new Date();
		String sysDate = "";
		String login = LUser.getGuid();//得到当前用户的guid
		String sszt = Validate.isNullToDefaultString(Constant.getztid(req.getSession()), "googosoft");
		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");
		String url ="bbzx/cwbb/srzcbyear";
		Map<String,Object> bzdw =  srfyService.getBzdw();//学校信息
		List list = new ArrayList<Map<String,Object>>();
		if("year".equals(status)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			sysDate = Validate.isNullToDefaultString(pd.getString("ny"), sdf.format(date));
			bblx = "0";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM");
			sysDate = Validate.isNullToDefaultString(pd.getString("ny"), sdf.format(date));
//			System.err.println("sss="+sysDate);
			url ="bbzx/cwbb/srzcbmonth";			
			
		}
		
		//bblx==1，sysdate===当前时间取得年和月  sszt==学生账套   jzpz==0
		int result = srfyService.checkSrfy(bblx, sysDate, sszt,jzpz);
		if(result>0){
			list = srfyService.getResultList(bblx, sysDate, sszt, jzpz);
			System.err.println("111111");
		}else{
			list = srfyService.getResultListByPro(bblx, sysDate, sszt, jzpz, bzdw.get("GUID")+"");
			System.err.println("22222");
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
			sql.append(" T.XMBH AS XMMC,");
			sql.append(" DECODE(NVL(T.BYS,0.00),0.00,'',TO_CHAR(ROUND(T.BYS,2),'FM999,999,999,000.00')) AS BYS,");
			sql.append(" DECODE(NVL(T.BNLJS,0.00),0.00,'',TO_CHAR(ROUND(T.BNLJS,2),'FM999,999,999,000.00')) AS BNLJS");
			sql.append(" FROM CW_SRFYB T");
			sql.append(" LEFT JOIN CW_XXXXB X ON X.GUID=T.BZDW");
			sql.append(" WHERE 1=1");
			sql.append(" AND T.BBLX='" + bblx + "'");
			sql.append(" AND T.NY='" + sysDate + "'");
			sql.append(" AND T.ZTBH='" + sszt + "'");
			sql.append(" AND T.JZPZ='"+jzpz+"'");
			sql.append(" ORDER BY HC");
		}else{
			sql.append(" SELECT");
			sql.append(" T.XMMC AS XMMC,");
			sql.append(" DECODE(NVL(T.BYS,0.00),0.00,'',TO_CHAR(ROUND(T.BYS,2),'FM999,999,999,000.00')) AS BYS,");
			sql.append(" DECODE(NVL(T.BNLJS,0.00),0.00,'',TO_CHAR(ROUND(T.BNLJS,2),'FM999,999,999,000.00')) AS BNLJS");
			sql.append(" FROM TEMP_XMXXB T");
			sql.append(" WHERE 1=1 AND T.LOGIN='"+LUser.getGuid()+"'");
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
		String searchValue = pd.getString("searchJson");
		String status = pd.getString("status");
		System.err.println(status+"--------------------");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
//		Date date = new Date();
//		String sysDate = "";
//		String login = LUser.getGuid();
//		String sszt = Validate.isNullToDefaultString(Constant.getztid(req.getSession()), "googosoft");
//		String bblx = Validate.isNullToDefaultString(pd.getString("bblx"), "1");
//		Map<String,Object> bzdw =  srfyService.getBzdw();
//		List list = new ArrayList<Map<String,Object>>();
//		if("year".equals(status)){
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//			sysDate = Validate.isNullToDefaultString(pd.getString("ny"), sdf.format(date));
//			bblx = "0";
//		}else{
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//			sysDate = Validate.isNullToDefaultString(pd.getString("ny"), sdf.format(date));
//		}
//		int result = srfyService.checkSrfy(bblx, sysDate, sszt,jzpz);
//		if(result>0){
//			list = srfyService.getResultList(bblx, sysDate, sszt, jzpz);
//		}else{
//			list = srfyService.getResultListByPro(bblx, sysDate, sszt, jzpz, bzdw.get("GUID")+"");
//		}
		String flag = pd.getString("flag");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.srfyService.expExcel(realfile, shortfileurl,searchValue,flag);
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
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "1");
		String bzdw = Validate.isNullToDefaultString(pd.getString("bzdw"), "1");
		String status = Validate.isNullToDefaultString(pd.getString("status"), "");
		int result = srfyService.checkSrfy(bblx, sysDate, sszt,jzpz);
		List list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map =  srfyService.getBzdw();
		if(result>0){
			list = srfyService.getResultList(bblx, sysDate, sszt, jzpz);
		}else{
			list = srfyService.getResultListByPro(bblx, sysDate, sszt, jzpz, map.get("GUID")+"");
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
		mv.setViewName("bbzx/cwbb/print_srfy");
		return mv;
	} 
}
