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
import com.googosoft.service.cwbb.JzcbdbService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/jzcbdb")
public class JzcbdbController extends BaseController{
	@Resource(name="jzcbdbService")
	private JzcbdbService jzcbdbService;
	@Resource(name = "pageService")
	private PageService pageService;
	
	@RequestMapping(value="/toUrl")
	public ModelAndView toJbzcbdbURL(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		//年度
		String year = Validate.isNullToDefaultString(pd.getString("year"), sdf.format(date));
		//记账凭证
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		//所属账套
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		//获取数据取向的判断
		int m = jzcbdbService.check(sszt, year, jzpz);
		//编制单位
		Map bzdw = jzcbdbService.getBzdw();
		List list = new ArrayList<Map<String,Object>>();
		if(m>0){//存在数据直接查
			list = jzcbdbService.getDatasList(sszt, year, jzpz);
			System.err.println("111");
		}else{
			list = jzcbdbService.getDatasListByPro(sszt, year, jzpz, bzdw.get("GUID")+"");
			System.err.println("222");
		}
		System.err.println(list);
		mv.addObject("list", list);
		mv.addObject("sszt", sszt);
		mv.addObject("year", year);
		mv.addObject("jzpz", jzpz);
		mv.addObject("bzdw", bzdw);
		mv.addObject("login", LUser.getGuid());
		mv.setViewName("bbzx/cwbb/jzcldb");
		return mv;
	}
	
	/**
	 * 上年数据
	 * @param sszt
	 * @param year
	 * @param jzpz
	 * @return
	 */
	@RequestMapping(value = "/SnDatas", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getSnDatasList(String sszt, String year, String jzpz){
		Map bzdw = jzcbdbService.getBzdw();
		List list = new ArrayList<Map<String,Object>>();
		Gson gson = new Gson();
		//获取数据取向的判断
		int m = jzcbdbService.check(sszt, year, jzpz);
		if(m>0){//存在数据直接查
			list = jzcbdbService.getDatasList(sszt, year, jzpz);
		}else{
			list = jzcbdbService.getDatasListByPro(sszt, year, jzpz, bzdw.get("GUID")+"");
		}
		return gson.toJson(list);
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
			bool = jzcbdbService.doSave(list);
		}
		return gson.toJson(bool);
	}
	
	/**
	 * 打印
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/goPrint")
	public ModelAndView goPrint(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		//年度
		String year = Validate.isNullToDefaultString(pd.getString("year"), sdf.format(date));
		//记账凭证
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		//所属账套
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		//获取数据取向的判断
		int m = jzcbdbService.check(sszt, year, jzpz);
		//编制单位
		Map bzdw = jzcbdbService.getBzdw();
		List list = new ArrayList<Map<String,Object>>();
		if(m>0){//存在数据直接查
			list = jzcbdbService.getDatasList(sszt, year, jzpz);
		}else{
			list = jzcbdbService.getDatasListByPro(sszt, year, jzpz, bzdw.get("GUID")+"");
		}
		System.err.println(list);
		mv.addObject("list", list);
		mv.addObject("sszt", sszt);
		mv.addObject("year", year);
		mv.addObject("jzpz", jzpz);
		mv.addObject("bzdw", bzdw);
		mv.addObject("login", LUser.getGuid());
		mv.setViewName("bbzx/cwbb/jzcldb_print");
		return mv;
	}
	
	@RequestMapping(value = "/expExcel", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcel(HttpServletRequest request,HttpServletResponse response) {
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
		ModelAndView mv = this.getModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		//年度
		String year = Validate.isNullToDefaultString(pd.getString("year"), sdf.format(date));
		//记账凭证
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		//所属账套
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		//编制单位
		Map bzdw = jzcbdbService.getBzdw();
		jzcbdbService.delete();
		List list = new ArrayList<Map<String,Object>>();
		int n = jzcbdbService.check(sszt, year, jzpz);//今年
		String byear = (Integer.parseInt(year)-1)+"";
		int b = jzcbdbService.check(sszt, byear, jzpz);//上年
		StringBuffer sql = new StringBuffer();
		if(n>0&&b>0){
			sql.append(" SELECT");
			sql.append(" T.XMBH AS XMMC,");
			sql.append(" DECODE(NVL(T.LJYY,0.00),0.00,'',TO_CHAR(ROUND(T.LJYY,2),'FM999,999,999,990.00')) AS BNLJYY,");
			sql.append(" DECODE(NVL(T.ZYJJ,0.00),0.00,'',TO_CHAR(ROUND(T.ZYJJ,2),'FM999,999,999,990.00')) AS BNZYJJ,");
			sql.append(" DECODE(NVL(T.QYFTZ,0.00),0.00,'',TO_CHAR(ROUND(T.QYFTZ,2),'FM999,999,999,990.00')) AS BNQYFTZ,");
			sql.append(" DECODE(NVL(T.JZCHJ,0.00),0.00,'',TO_CHAR(ROUND(T.JZCHJ,2),'FM999,999,999,990.00')) AS BNJZCHJ,");
			sql.append(" DECODE(NVL(TT.LJYY,0.00),0.00,'',TO_CHAR(ROUND(TT.LJYY,2),'FM999,999,999,990.00')) AS SNLJYY,");
			sql.append(" DECODE(NVL(TT.ZYJJ,0.00),0.00,'',TO_CHAR(ROUND(TT.ZYJJ,2),'FM999,999,999,990.00')) AS SNZYJJ,");
			sql.append(" DECODE(NVL(TT.QYFTZ,0.00),0.00,'',TO_CHAR(ROUND(TT.QYFTZ,2),'FM999,999,999,990.00')) AS SNQYFTZ,");
			sql.append(" DECODE(NVL(TT.JZCHJ,0.00),0.00,'',TO_CHAR(ROUND(TT.JZCHJ,2),'FM999,999,999,990.00')) AS SNJZCHJ,");
			sql.append(" T.BH AS BH,T.HC AS HC");
			sql.append(" FROM CW_JZCBDB T");
			sql.append(" LEFT JOIN CW_JZCBDB TT ON TT.BH=T.BH AND TT.ND='"+byear+"'");
			sql.append(" WHERE 1=1");
			sql.append(" AND T.JZPZ='" + jzpz + "'");
			sql.append(" AND T.ND='" + year + "'");
			sql.append(" AND T.ZTBH='" + sszt + "'");
			sql.append(" AND TT.JZPZ='" + jzpz + "'");
			sql.append(" AND TT.ND='" + byear + "'");
			sql.append(" AND TT.ZTBH='" + sszt + "'");
			sql.append(" ORDER BY T.HC");
		}else if(n==0&&b==0){
			list = jzcbdbService.getDatasListByPro(sszt, year, jzpz, bzdw.get("GUID")+"");
			boolean bool = jzcbdbService.doSaveByExp(list);
			if(bool){
				sql.append(getSql());
			}
		}else if(n>0&&b==0){
			list = jzcbdbService.getDatasList(sszt, year, jzpz);
			boolean bool = jzcbdbService.doSaveByExp(list);
			if(bool){
				jzcbdbService.updateSn(jzcbdbService.getDatasListByPro(sszt, year, jzpz, bzdw.get("GUID")+""));
			}
			sql.append(getSql());
		}else{
			list = jzcbdbService.getDatasList(sszt, byear, jzpz);
			boolean bool = jzcbdbService.doSaveByExp(list);
			if(bool){
				jzcbdbService.updateBn(jzcbdbService.getDatasListByPro(sszt, year, jzpz, bzdw.get("GUID")+""));
			}
			sql.append(getSql());
		}
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("xmmc");
		m.setShowname("项目");
		mlist.add(m);
		m = null;
		m = null;
	
		m = new M_largedata();
		m.setName("bnljyy");
		m.setShowname("本年累计盈余");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bnzyjj");
		m.setShowname("本年专用基金");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bnqyftz");
		m.setShowname("本年权益法调整");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bnjzchj");
		m.setShowname("本年净资产合计");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("snljyy");
		m.setShowname("上年累计盈余");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("snzyjj");
		m.setShowname("上年专用基金");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("snqyftz");
		m.setShowname("上年权益法调整");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("snjzchj");
		m.setShowname("上年净资产合计");
		mlist.add(m);
		m = null;
		// 导出方法
		pageService.ExpExcel(sql.toString(), realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}
	
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request) {
		PageData pd = this.getPageData();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		//年度
		String year = Validate.isNullToDefaultString(pd.getString("year"), sdf.format(date));
		//记账凭证
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		//所属账套
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		//编制单位
		Map bzdw = jzcbdbService.getBzdw();
		jzcbdbService.delete();
		List list = new ArrayList<Map<String,Object>>();
		int n = jzcbdbService.check(sszt, year, jzpz);//今年
		String byear = (Integer.parseInt(year)-1)+"";
		int b = jzcbdbService.check(sszt, byear, jzpz);//上年
		StringBuffer sql = new StringBuffer();
		if(n>0&&b>0){
			sql.append(" SELECT");
			sql.append(" T.XMBH AS XMMC,");
			sql.append(" DECODE(NVL(T.LJYY,0.00),0.00,'',TO_CHAR(ROUND(T.LJYY,2),'FM999,999,999,990.00')) AS BNLJYY,");
			sql.append(" DECODE(NVL(T.ZYJJ,0.00),0.00,'',TO_CHAR(ROUND(T.ZYJJ,2),'FM999,999,999,990.00')) AS BNZYJJ,");
			sql.append(" DECODE(NVL(T.QYFTZ,0.00),0.00,'',TO_CHAR(ROUND(T.QYFTZ,2),'FM999,999,999,990.00')) AS BNQYFTZ,");
			sql.append(" DECODE(NVL(T.JZCHJ,0.00),0.00,'',TO_CHAR(ROUND(T.JZCHJ,2),'FM999,999,999,990.00')) AS BNJZCHJ,");
			sql.append(" DECODE(NVL(TT.LJYY,0.00),0.00,'',TO_CHAR(ROUND(TT.LJYY,2),'FM999,999,999,990.00')) AS SNLJYY,");
			sql.append(" DECODE(NVL(TT.ZYJJ,0.00),0.00,'',TO_CHAR(ROUND(TT.ZYJJ,2),'FM999,999,999,990.00')) AS SNZYJJ,");
			sql.append(" DECODE(NVL(TT.QYFTZ,0.00),0.00,'',TO_CHAR(ROUND(TT.QYFTZ,2),'FM999,999,999,990.00')) AS SNQYFTZ,");
			sql.append(" DECODE(NVL(TT.JZCHJ,0.00),0.00,'',TO_CHAR(ROUND(TT.JZCHJ,2),'FM999,999,999,990.00')) AS SNJZCHJ,");
			sql.append(" T.BH AS BH,T.HC AS HC");
			sql.append(" FROM CW_JZCBDB T");
			sql.append(" LEFT JOIN CW_JZCBDB TT ON TT.BH=T.BH AND TT.ND='"+byear+"'");
			sql.append(" WHERE 1=1");
			sql.append(" AND T.JZPZ='" + jzpz + "'");
			sql.append(" AND T.ND='" + year + "'");
			sql.append(" AND T.ZTBH='" + sszt + "'");
			sql.append(" AND TT.JZPZ='" + jzpz + "'");
			sql.append(" AND TT.ND='" + byear + "'");
			sql.append(" AND TT.ZTBH='" + sszt + "'");
			sql.append(" ORDER BY T.HC");
		}else if(n==0&&b==0){
			list = jzcbdbService.getDatasListByPro(sszt, year, jzpz, bzdw.get("GUID")+"");
			boolean bool = jzcbdbService.doSaveByExp(list);
			if(bool){
				sql.append(getSql());
			}
		}else if(n>0&&b==0){
			list = jzcbdbService.getDatasList(sszt, year, jzpz);
			boolean bool = jzcbdbService.doSaveByExp(list);
			if(bool){
				jzcbdbService.updateSn(jzcbdbService.getDatasListByPro(sszt, year, jzpz, bzdw.get("GUID")+""));
			}
			sql.append(getSql());
		}else{
			list = jzcbdbService.getDatasList(sszt, byear, jzpz);
			boolean bool = jzcbdbService.doSaveByExp(list);
			if(bool){
				jzcbdbService.updateBn(jzcbdbService.getDatasListByPro(sszt, year, jzpz, bzdw.get("GUID")+""));
			}
			sql.append(getSql());
		}
		
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.jzcbdbService.expExcel(realfile, shortfileurl,searchValue,sql.toString());
	}
	/**
	 * 获取sql
	 * @return
	 */
	public String getSql(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" T.XMMC AS XMMC,");
		sql.append(" DECODE(NVL(T.BNLJYY,0.00),0.00,'',TO_CHAR(ROUND(T.BNLJYY,2),'FM999,999,999,990.00')) AS BNLJYY,");
		sql.append(" DECODE(NVL(T.BNZYJJ,0.00),0.00,'',TO_CHAR(ROUND(T.BNZYJJ,2),'FM999,999,999,990.00')) AS BNZYJJ,");
		sql.append(" DECODE(NVL(T.BNQYFTZ,0.00),0.00,'',TO_CHAR(ROUND(T.BNQYFTZ,2),'FM999,999,999,990.00')) AS BNQYFTZ,");
		sql.append(" DECODE(NVL(T.BNJZCHJ,0.00),0.00,'',TO_CHAR(ROUND(T.BNJZCHJ,2),'FM999,999,999,990.00')) AS BNJZCHJ,");
		sql.append(" DECODE(NVL(T.SNLJYY,0.00),0.00,'',TO_CHAR(ROUND(T.SNLJYY,2),'FM999,999,999,990.00')) AS SNLJYY,");
		sql.append(" DECODE(NVL(T.SNZYJJ,0.00),0.00,'',TO_CHAR(ROUND(T.SNZYJJ,2),'FM999,999,999,990.00')) AS SNZYJJ,");
		sql.append(" DECODE(NVL(T.SNQYFTZ,0.00),0.00,'',TO_CHAR(ROUND(T.SNQYFTZ,2),'FM999,999,999,990.00')) AS SNQYFTZ,");
		sql.append(" DECODE(NVL(T.SNJZCHJ,0.00),0.00,'',TO_CHAR(ROUND(T.SNJZCHJ,2),'FM999,999,999,990.00')) AS SNJZCHJ");
		sql.append(" FROM CW_CWPT_JZCBDB T");
		sql.append(" WHERE 1=1");
		sql.append(" AND T.LOGIN='" + LUser.getGuid() + "'");
		sql.append(" ORDER BY T.HC");
		return sql.toString();
	}
}
