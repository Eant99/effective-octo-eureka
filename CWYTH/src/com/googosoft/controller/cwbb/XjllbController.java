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
import com.googosoft.service.cwbb.XjllbService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/xjllb")
public class XjllbController extends BaseController {
	@Resource(name = "XjllbService")
	private XjllbService XjllbService;
	@Resource(name="pageService")
	private PageService pageService;//单例
	

	@RequestMapping(value="/toUrl")
	public ModelAndView toXjllbURL(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		
		Date date = new Date();
		String	sysDate1 = Validate.isNullToDefaultString(pd.getString("ny"), sdf.format(date));
		String login = LUser.getGuid();
		
		//年度
		String year = Validate.isNullToDefaultString(pd.getString("year"), sdf.format(date));
		//记账凭证
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
		//所属账套
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		//获取数据取向的判断
		int m =  XjllbService.check(sszt, year, jzpz);
//		int m =  0;
		//编制单位
		Map bzdw = XjllbService.getBzdw();
		List list = new ArrayList<Map<String,Object>>();
		if(m>0){//存在数据直接查
			list = XjllbService.getDatasList(sszt, year, jzpz);
			System.err.println("list="+list);
			
		}else{
			
			
			list = XjllbService.getResultListByPro(year, jzpz, sszt, bzdw);
			System.err.println("year="+year+"***jzpz="+jzpz+"**sszt="+sszt+"**bzdw"+bzdw);
			System.err.println("list="+list);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		}
		mv.addObject("sysDate",sysDate1);
		mv.addObject("czr",login);
		mv.addObject("list", list);
		mv.addObject("sszt", sszt);
		mv.addObject("year", year);
		mv.addObject("jzpz", jzpz);
		mv.addObject("bzdw", bzdw);
		mv.setViewName("bbzx/cwbb/xjllb");
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
		System.err.println("listsave="+list);
		boolean bool = false;
		if (list.size()>0) {
			bool = XjllbService.doSave(list);
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
		System.err.println("year="+year+"jzpz="+jzpz+"sszt="+sszt);
		int m = XjllbService.check(sszt, year, jzpz);
		//编制单位
		Map bzdw = XjllbService.getBzdw();
		List list = new ArrayList<Map<String,Object>>();
		if(m>0){//存在数据直接查
//			list = XjllbService.getDatasListByPro(year, jzpz, sszt, bzdw);
			list = XjllbService.getDatasList(sszt, year, jzpz);
		}else{
			list = XjllbService.getDatasListByPro(year, jzpz, sszt, bzdw);
		}
		System.err.println(list);
		mv.addObject("list", list);
		mv.addObject("sszt", sszt);
		mv.addObject("year", year);
		mv.addObject("jzpz", jzpz);
		mv.addObject("bzdw", bzdw);
		mv.addObject("login", LUser.getGuid());///CWYTH/WebContent/webView/bbzx/cwbb/xjllbyl.jsp
		mv.setViewName("bbzx/cwbb/xjllbyl");
		return mv;
	}
	
	/**
	 * 导出人员信息Excel
	 * @return
	 */
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchJson = pd.getString("abc");
		System.err.println("skdjfhkasdf="+searchJson);
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select  k.xmbh,k.bnje,k.snje,k.hc ");
		sqltext.append(" FROM Cw_xjllb K  where 1=1 and k.nd='"+searchJson+"' ");
		
//		sqltext.append(CommonUtils.jsonToSql(searchJson));
//		String id = pd.getString("guid");
//		if(Validate.noNull(id)){
//			sqltext.append(" AND K.guid IN ('"+id.replace(",", "','")+"') ");
//		}
		sqltext.append(" ORDER BY K.HC ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("xmbh");
		m.setShowname("项目");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bnje");
		m.setShowname("本年金额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("snje");
		m.setShowname("上年金额");
		mlist.add(m);
		m = null;

		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	//导出
		@RequestMapping("/expExcel2")
		@ResponseBody
		public Object Info(HttpServletRequest request) {
			PageData pd = this.getPageData();
			String searchJson = pd.getString("abc");
			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//			Date date = new Date();
//			String	sysDate1 = Validate.isNullToDefaultString(pd.getString("ny"), sdf.format(date));
//			String login = LUser.getGuid();
//			//年度
//			String year = Validate.isNullToDefaultString(pd.getString("year"), sdf.format(date));
//			//记账凭证
//			String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");
//			//所属账套
//			String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
//			//获取数据取向的判断
//			int m =  XjllbService.check(sszt, year, jzpz);
//			int m =  0;
			//编制单位
//			Map bzdw = XjllbService.getBzdw();
//			List list = new ArrayList<Map<String,Object>>();
//			if(m>0){//存在数据直接查
//				list = XjllbService.getDatasList(sszt, year, jzpz);
//			}else{
//				list = XjllbService.getResultListByPro(year, jzpz, sszt, bzdw);
//			
//			}
			String searchValue = pd.getString("searchJson");
			String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
			String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
			return this.XjllbService.expExcel(realfile, shortfileurl,searchValue);
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
		Map bzdw = XjllbService.getBzdw();
		List list = new ArrayList<Map<String,Object>>();
		Gson gson = new Gson();
		//获取数据取向的判断
		int m = XjllbService.check(sszt, year, jzpz);
		if(m>0){//存在数据直接查
			list = XjllbService.getDatasList(sszt, year, jzpz);
		}else{
			list = XjllbService.getDatasListByPro(year, jzpz, sszt, bzdw);
		}
		return gson.toJson(list);
	}
	
	
	
}
