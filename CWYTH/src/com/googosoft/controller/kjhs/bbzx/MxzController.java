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
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.controller.kjhs.ZzController;
import com.googosoft.dao.kjhs.ZzDao;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.ZzService;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.service.kjhs.bbzx.MxzService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value = "/mxz")
public class MxzController extends BaseController {
		
	
	@Resource(name = "dictService")
	private DictService dictService;// 数据字典单例

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@Resource(name = "mxzService")
	private MxzService mxzService;
	
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
		String params = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String bmye = Validate.isNullToDefaultString(pd.getString("bmye"), "");
		if(kmxx.indexOf("(")!=-1){
			kmmc=kmxx.substring(kmxx.indexOf(")")+1, kmxx.length());
			kmbh=kmxx.substring(1,kmxx.lastIndexOf(")"));
		}else{
			kmbh=Validate.isNullToDefaultString(pd.getString("kmbh"), "");
			kmmc=Validate.isNullToDefaultString(pd.getString("kmmc"), "");
			//{"list":[{"start":"start","year":"2018","startMonth":"01","endMonth":"01","startKjkm":"1002","Kjkmmc":"1002","jzpz":"1","end":"end"}]}
		}
		if(Validate.isNull(params)&&Validate.noNull(kmbh)){
			params = "{\"list\":[{\"start\":\"start\",\"year\":\""+sdf.format(new Date())+"\",\"startMonth\":\"01\",\"endMonth\":\""+new SimpleDateFormat("MM").format(new Date())+"\",\"startKjkm\":\""+kmbh+"\",\"Kjkmmc\":\""+kmbh+"\",\"jzpz\":\"1\",\"end\":\"end\"}]}";
			request.getSession().setAttribute("params", params);
		}
		String sysQj = sdf.format(new Date())+"年01月至"+new SimpleDateFormat("yyyy年MM月").format(new Date());
		if(Validate.isNull(kmbh)&&"mxz".equals(ly)){
			request.getSession().removeAttribute("bbqj");
		}
		String bbqj = Validate.isNullToDefaultString(pd.getString("bbqj"),Validate.isNullToDefaultString(request.getSession().getAttribute("bbqj"), sysQj));
		String pxfs = Validate.isNullToDefaultString(pd.getString("pxfs"), "");
		String jump = Validate.isNullToDefaultString(pd.getString("jump"), "");
		String clicks = Validate.isNullToDefaultString(pd.getString("clicks"), "");
		String treesearch = pd.getString("treesearch");
		//控制是否显示返回按钮
		String type = pd.getString("type");
		if("mxz".equals(ly)){
			type = "mxz";
		}
		if("yes".equals(jump)){
			mxzService.deleteKmyeb();
			request.getSession().removeAttribute("params");
			request.getSession().removeAttribute("bbqj");
			
		}
		Date date = new Date();
		String year = Validate.isNullToDefaultString(bbqj, sdf.format(date));
		year = year.substring(0,4);
		String ye =  pd.getString("ye");
		mv.addObject("ye", ye);
		String url = "kjhs/bbzx/mxz";
		if("jjkm".equals(ly)){
			url = "kjhs/bbzx/jjkm/jjkm_list";
		}
		if("grwl".equals(ly)){
			url = "kjhs/bbzx/grwl/grwlmxz_list";
		}
		if("bmxm".equals(ly)){
			url = "kjhs/bbzx/bmxm/bmxmMxz_list";
		}
		List<Map<String,Object>> months = kmyeService.getMonth();
		mv.addObject("months", months);
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
		System.err.println(StartMonth+"%%%%%%%%%%%%%%%%"+endMonth);
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
		mv.addObject("kmmc", kmmc);
		mv.addObject("pxfs", pxfs);
		mv.addObject("clicks", clicks);
		mv.addObject("jump", jump);
		mv.addObject("kmbh", kmbh);
		mv.addObject("year", year);
		mv.addObject("type", type);
		mv.addObject("bmye", bmye);
		mv.addObject("ly", ly);
		mv.addObject("treesearch", treesearch);
		mv.setViewName(url);
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
//		bbqj=${bbqj}&kmbh
		//12个月份
		List<Map<String,Object>> months = mxzService.getMonth();
		String nowmonth=date.getMonth()+1+"";
		String StartMonth = pd.getString("StartMonth");
		String endMonth = pd.getString("endMonth");
		String pz = pd.getString("pz");
		//科目级别list
		List list = mxzService.getKjkmJb();
		mv.addObject("list", list);
		mv.addObject("StartMonth", StartMonth);
		mv.addObject("endMonth", endMonth);
		mv.addObject("year", year);
		mv.addObject("kmbh", kmbh);
		mv.addObject("pz", pz);
		mv.addObject("months", months);
		mv.addObject("nowmonth", nowmonth);
		mv.setViewName("kjhs/bbzx/mxz_search");
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
//		if("1".equals(pxfs)){
//			pxfs = "pzrq";
//		}else if("0".equals(pxfs)){
//			pxfs = "pzz";
//		}
		//创建参数对象
		Params params = new Params();
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String jsonStr = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");		
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		String pz = Validate.isNullToDefaultString(pd.getString("pz"),"");
		String StartMonth = Validate.isNullToDefaultString(pd.getString("StartMonth"),"");
		String endMonth = Validate.isNullToDefaultString(pd.getString("endMonth"),"");
		mv.addObject("kmbh", kmbh);
		if(kmbh!=""&&kmbh.contains("(")){
			kmbh=kmbh.substring(1, kmbh.lastIndexOf(")"));					
		}
		String bmye = Validate.isNullToDefaultString(pd.getString("bmye"), "");
		if("bmye".equals(bmye)){
			boolean result = mxzService.getResultNew(sszt,kmbh,year,StartMonth,endMonth,pd);
			if(!result){
				return null;
			}
		}else{
		if(Validate.noNull(jsonStr)){
			String ajson = jsonStr.substring(8,jsonStr.length()-1);
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			boolean result = mxzService.getResult(list,sszt,kjzd,kmbh,pd);
			if(!result){
				return null;
			}
		}
		}
//		if(Validate.noNull(jsonStr)){
//			String ajson = jsonStr.substring(8,jsonStr.length()-1);
//			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
//			boolean result = mxzService.getResult(list,sszt,kjzd,kmbh);
//			if(!result){
//				return null;
//			}
//		}
//		sqltext.append("k.*");
//		StringBuffer sql = new StringBuffer();
//		PageList pageList = new PageList();
//		pageList.setTableName("("+getSql(kmbh)+") K");
//		pageList.setSqlText(sqltext.toString());
//		pageList.setKeyId(pxfs);//主键
//		//pageList.setTableName(sql.toString());//表名
//		pageList.setHj1("");//合计
//	    pageList = pageService.findPageList(pd,pageList);
//		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		System.err.println("treesearch="+treesearch);
		return  mxzService.getPageList(kmbh,kjzd,treesearch,pd);
	}
	@RequestMapping(value = "/getJjkmPageList")
	@ResponseBody
	public List<Map<String,Object>> getJjkmPageList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		
		ModelAndView mv = this.getModelAndView();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String pxfs = "1";//默认按照凭证日期排序
		//创建参数对象
		Params params = new Params();
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String jsonStr = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");		
		String kmbh = Validate.isNullToDefaultString(pd.getString("treekmbh"),"");//经济科目树
		mv.addObject("kmbh", kmbh);
		if(kmbh!=""&&kmbh.contains("(")){
			kmbh=kmbh.substring(1, kmbh.lastIndexOf(")"));					
		}
		String kjkm = Validate.isNullToDefaultString(pd.getString("kmbh"),"");//会计科目
		if(Validate.noNull(kjkm)){
			kjkm = kjkm.substring(1, kjkm.indexOf(")"));
		}
		String bmbh = Validate.isNullToDefaultString(pd.getString("bmbh"),"");//部门
		if(Validate.noNull(bmbh)){
			bmbh = bmbh.substring(1, bmbh.indexOf(")"));
		}
		String xmbh = Validate.isNullToDefaultString(pd.get("xmbh"), "");//项目
		if(Validate.noNull(xmbh)){
			xmbh = xmbh.substring(1, xmbh.indexOf(")"));
		}
		String jfjel = Validate.isNullToDefaultString(pd.get("jfjel"), "");
		String jfjeh = Validate.isNullToDefaultString(pd.get("jfjeh"), "");
		String zy  = Validate.isNullToDefaultString(pd.get("zy"), "");
		String pznd=year;
		String startMonth = Validate.isNullToDefaultString(request.getParameter("kjqj1"), "");
		String endMonth = Validate.isNullToDefaultString(request.getParameter("kjqj2"), "");
		String pzzt = Validate.isNullToDefaultString(request.getParameter("pzzt"), "");
		if(Validate.noNull(jsonStr)){
			String ajson = jsonStr.substring(8,jsonStr.length()-1);
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			for(int i=0;i<1;i++){
				Map map = (Map) list.get(0);
				pznd = map.get("year")+"";
				startMonth = map.get("startMonth")+"";
				endMonth = map.get("endMonth")+"";
			}
			
		}
		
		return mxzService.getJjPageList(kmbh,kjkm,pznd,startMonth,endMonth,pzzt,bmbh,xmbh,jfjel,jfjeh,zy);
		
	}
	
	@RequestMapping(value = "/getGrwlPageList")
	@ResponseBody
	public List<Map<String,Object>> getGrwlPageList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		
		ModelAndView mv = this.getModelAndView();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String pxfs = "1";//默认按照凭证日期排序
		//创建参数对象
		Params params = new Params();
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String jsonStr = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");		
		String ryid = Validate.isNullToDefaultString(pd.getString("ryid"),"");
		mv.addObject("ryid", ryid);
		if(ryid!=""&&ryid.contains("(")){
			ryid=ryid.substring(1, ryid.lastIndexOf(")"));					
		}
		String kjkm = Validate.isNullToDefaultString(pd.getString("kmbh"),"");//会计科目
		if(Validate.noNull(kjkm)){
			kjkm = kjkm.substring(1, kjkm.indexOf(")"));
		}
		String bmbh = Validate.isNullToDefaultString(pd.getString("bmbh"),"");//部门
		if(Validate.noNull(bmbh)){
			bmbh = bmbh.substring(1, bmbh.indexOf(")"));
		}
		String xmbh = Validate.isNullToDefaultString(pd.get("xmbh"), "");//项目
		if(Validate.noNull(xmbh)){
			xmbh = xmbh.substring(1, xmbh.indexOf(")"));
		}
		String jfjel = Validate.isNullToDefaultString(pd.get("jfjel"), "");
		String jfjeh = Validate.isNullToDefaultString(pd.get("jfjeh"), "");
		String zy  = Validate.isNullToDefaultString(pd.get("zy"), "");
		String pznd=year;
		String startMonth = Validate.isNullToDefaultString(request.getParameter("kjqj1"), "");
		String endMonth = Validate.isNullToDefaultString(request.getParameter("kjqj2"), "");
		String pzzt = Validate.isNullToDefaultString(request.getParameter("pzzt"), "");
		if(Validate.noNull(jsonStr)){
			String ajson = jsonStr.substring(8,jsonStr.length()-1);
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			for(int i=0;i<1;i++){
				Map map = (Map) list.get(0);
				pznd = map.get("year")+"";
				startMonth = map.get("startMonth")+"";
//				String endM = map.get("endMonth")+"";
				endMonth = map.get("endMonth")+"";
			}
		}
		return mxzService.getGrPageList(ryid,kjkm,pznd,startMonth,endMonth,pzzt,bmbh,xmbh,jfjel,jfjeh,zy);
	}
	
	@RequestMapping(value="/dwryTree" )
	public ModelAndView dwryTree(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("kjhs/bbzx/grwl/mainDwryTree");
		return mv;
	}
	
	@RequestMapping(value="/bmxmTree")
	public ModelAndView bmxmTree(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("kjhs/bbzx/bmxm/mainBmxmTree");
		return mv;
	}
	
	/**
	 * 获取单位项目树
	 * @param
	 * @return
	 */
	@RequestMapping(value="/dwxmTree" , produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object dwxmTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return mxzService.getPowerNode(pd, loginrybh,pd.getString("pageUrl"),rootPath);
//				return glqxbService.getPowerNode(pd,loginrybh);
			}else{
//			    return glqxbService.getDwRyNode(pd,dwbh,rootPath);
				return mxzService.getDwRyNode(pd, dwbh, rootPath);
			}
		}else{
			return "";
		}
	}
	
	@RequestMapping(value = "/getBmxmPageList")
	@ResponseBody
	public List<Map<String,Object>> getBmxmPageList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		
		ModelAndView mv = this.getModelAndView();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String pxfs = "1";//默认按照凭证日期排序
		//创建参数对象
		Params params = new Params();
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String jsonStr = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");		
		String treexmbh = Validate.isNullToDefaultString(pd.getString("treexmbh"),"");
		mv.addObject("xmbh", treexmbh);
		if(treexmbh!=""&&treexmbh.contains("(")){
			treexmbh=treexmbh.substring(1, treexmbh.lastIndexOf(")"));					
		}
		String kjkm = Validate.isNullToDefaultString(pd.getString("kmbh"),"");//会计科目
		if(Validate.noNull(kjkm)){
			kjkm = kjkm.substring(1, kjkm.indexOf(")"));
		}
		String bmbh = Validate.isNullToDefaultString(pd.getString("bmbh"),"");//部门
		if(Validate.noNull(bmbh)){
			bmbh = bmbh.substring(1, bmbh.indexOf(")"));
		}
		String xmbh = Validate.isNullToDefaultString(pd.get("xmbh"), "");//项目
		if(Validate.noNull(xmbh)){
			xmbh = xmbh.substring(1, xmbh.indexOf(")"));
		}
		String jfjel = Validate.isNullToDefaultString(pd.get("jfjel"), "");
		String jfjeh = Validate.isNullToDefaultString(pd.get("jfjeh"), "");
		String zy  = Validate.isNullToDefaultString(pd.get("zy"), "");
		String pznd=year;
		String startMonth = Validate.isNullToDefaultString(request.getParameter("kjqj1"), "");
		String endMonth = Validate.isNullToDefaultString(request.getParameter("kjqj2"), "");
		String pzzt = Validate.isNullToDefaultString(request.getParameter("pzzt"), "");
		if(Validate.noNull(jsonStr)){
			String ajson = jsonStr.substring(8,jsonStr.length()-1);
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			for(int i=0;i<1;i++){
				Map map = (Map) list.get(0);
				pznd = map.get("year")+"";
				startMonth = map.get("startMonth")+"";
//				String endM = map.get("endMonth")+"";
				endMonth = map.get("endMonth")+"";
			}
		}
		return mxzService.getXmPageList(treexmbh,kjkm,pznd,startMonth,endMonth,pzzt,bmbh,xmbh,jfjel,jfjeh,zy);
	}
	
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
		list= mxzService.getPageList(kmbh,kjzd,treesearch,pd);
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.mxzService.expExcel(realfile, shortfileurl,searchValue,flag,list);
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
	  	list = mxzService.kmbhList(s);
		System.err.println("测试数据="+list.subList(0, 2));
//		System.err.println("测试数据111="+kmmc);
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
