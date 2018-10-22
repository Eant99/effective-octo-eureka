package com.googosoft.controller.kjhs;

import java.sql.SQLException;
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
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.service.kjhs.bbzx.XjrjzService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value = "/xjrjz")
public class xjrjzController extends BaseController {
	@Resource(name = "dictService")
	private DictService dictService;// 数据字典单例

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@Resource(name = "xjrjzService")
	private XjrjzService xjrjzService;
	
	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	public static void main(String[] args) {
		System.out.println("2017".substring(0,4));
	}
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
		String jump = Validate.isNullToDefaultString(pd.getString("jump"), "");
		String bbqj = Validate.isNullToDefaultString(pd.getString("bbqj"), "");
		String clicks = Validate.isNullToDefaultString(pd.getString("clicks"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
		
		String kmbh="",kmmc="";
		String kmxx = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
		if(kmxx.indexOf("(")!=-1){
			kmmc=kmxx.substring(kmxx.indexOf(")")+1, kmxx.length());
			kmbh=kmxx.substring(1,kmxx.lastIndexOf(")"));
		}else{
			kmbh=Validate.isNullToDefaultString(pd.getString("kmbh"), "");
			kmmc=Validate.isNullToDefaultString(pd.getString("kmmc"), "");
		};			
			
		String zjrjzType = Validate.isNullToDefaultString(pd.getString("zjrjzType"), "");
		if("yes".equals(jump)){
			xjrjzService.deleteKmyeb();
			request.getSession().removeAttribute("xjrjzJson");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = Validate.isNullToDefaultString(bbqj, sdf.format(date));
		year = year.substring(0,4);
		String url = "kjhs/bbzx/xjrjz";
		mv.addObject("kmxx", kmxx);
		mv.addObject("clicks", clicks);
		mv.addObject("jump", jump);
		mv.addObject("bbqj", bbqj);
		mv.addObject("kmbh", kmbh);
		mv.addObject("kmmc", kmmc);
		mv.addObject("year", year);
		mv.addObject("treesearch", treesearch);
		mv.addObject("zjrjzType", zjrjzType);
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
		String kmmc = pd.getString("kmmc");
		//科目级别list
		List list = xjrjzService.getKjkmJb();
		mv.addObject("list", list);
		mv.addObject("kmmc", kmmc);
	   String  syear=  this.getRequest().getSession().getAttribute("syear")+"";
	   String  eyear =  this.getRequest().getSession().getAttribute("eyear")+"";
	   String  syear2=  this.getRequest().getSession().getAttribute("syear2")+"";
	   String  eyear2 =  this.getRequest().getSession().getAttribute("eyear2")+"";
	   /*  String rqxx =  this.getRequest().getSession().getAttribute("xjrjzJson")+"";
	   Gson gson = new Gson();
	   if(Validate.noNull(rqxx)){
		   Map map = gson.fromJson(rqxx, Map.class);
		    String rqlist = map.get("list")+"";
		    String[] rqlists = rqlist.split(",");
		    String kjqj = rqlists[1];
		    String kkjqj = rqlists[2];
		    String kjqjs = kjqj.substring(kjqj.lastIndexOf("=")+1);
		    String kkjqjs = kkjqj.substring(kkjqj.lastIndexOf("=")+1);
		    mv.addObject("kjqj",kjqjs);
		    mv.addObject("kkjqj",kkjqjs);
	   }*/
	   if(Validate.noNull(syear)){
		   mv.addObject("kjqj",syear);
	   }
	   if(Validate.noNull(eyear)){
		   mv.addObject("kkjqj",eyear);
	   }
	   if(Validate.noNull(syear2)){
		   mv.addObject("syear2",syear2);
	   }
	   if(Validate.noNull(eyear2)){
		   mv.addObject("eyear2",eyear2);
	   }
		mv.setViewName("kjhs/bbzx/xjrjz_search");
		return mv;
	}
	
	/**
	 * ajax处理session
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/paramSession", produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public String paramSession(HttpServletRequest request,HttpServletResponse response) throws SQLException {
			String params = this.getPageData().getString("xjrjzJson");
			String zjrjzType = this.getPageData().getString("type");	
			String syear = this.getPageData().getString("syear");	
			String eyear = this.getPageData().getString("eyear");	
			String syear2 = this.getPageData().getString("syear2");	
			String eyear2 = this.getPageData().getString("eyear2");	
			request.getSession().setAttribute("xjrjzJson", params);
			request.getSession().setAttribute("syear", syear);
			request.getSession().setAttribute("eyear", eyear);
			request.getSession().setAttribute("syear2", syear2);
			request.getSession().setAttribute("eyear2", eyear2);
			return zjrjzType;
	}
	
	
	public boolean runPro(HttpServletRequest request,String xmbh,String zy,String pzbh,String pzrq1,String pzrq2) throws SQLException{
		int i;		
		String json = Validate.isNullToDefaultString(request.getSession().getAttribute("xjrjzJson"), "");//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List<Object> list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list	
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		List<String> xjrjzlist=new ArrayList<String>();
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map			
			String kjqj = Validate.isNullToDefaultString((String)map.get("kjqj"),"");
			String kjqjz = Validate.isNullToDefaultString((String)map.get("kkjqj"),"");
			String kmbhstart1 =(String)map.get("startKjkm");	
			String str=kmbhstart1.substring(kmbhstart1.lastIndexOf("("),kmbhstart1.lastIndexOf(")"));
			String kmbhstart=str.substring(1,str.length());
			String	startJc =(String)map.get("startJc");
			String	qbjzpz =Validate.isNullToDefaultString((String)map.get("qbjzpz"),"");
			String	yfhjzpz =Validate.isNullToDefaultString((String)map.get("yfhjzpz"),"");			
							
			xjrjzlist.add(kjqj);
			xjrjzlist.add(kjqjz);
			
			if(xmbh!=""){
				xjrjzlist.add(xmbh);
			}else{
				xjrjzlist.add(kmbhstart);
			}			
			
			xjrjzlist.add(startJc);
			xjrjzlist.add(qbjzpz);
			xjrjzlist.add(yfhjzpz);
			xjrjzlist.add(sszt);
			xjrjzlist.add(LUser.getGuid());
			xjrjzlist.add(kjzd);
			StringBuffer sql = new StringBuffer();
			//综合查询拼接条件
			if(Validate.noNull(zy.trim())){
				sql.append(" and t.zy like '%"+zy+"%'");
			}
			if(Validate.noNull(pzbh)){
				sql.append(" and to_number(PZ.PZBH) = '"+pzbh+"'");
			}
			if(Validate.noNull(pzrq1)){
				sql.append(" and (CASE LENGTH(PZ.PZRQ) WHEN 7 THEN PZ.PZRQ || '-01' ELSE PZ.PZRQ END) >= '"+pzrq1+"'");
			}
			if(Validate.noNull(pzrq2)){
				sql.append(" and (CASE LENGTH(PZ.PZRQ) WHEN 7 THEN PZ.PZRQ || '-01' ELSE PZ.PZRQ END)<= '"+pzrq2+"'");
			}
			xjrjzlist.add(sql.toString());
		}
		System.err.println(xjrjzlist);
		boolean bl=xjrjzService.runPro("pro_cwyth_xjrjz",xjrjzlist);
		return bl;
	}
	
	
	public boolean runPro2(HttpServletRequest request,String xmbh,String zy,String pzbh,String pzrq1,String pzrq2) throws SQLException{
			
		int i;		
		String json = Validate.isNullToDefaultString(request.getSession().getAttribute("xjrjzJson"), "");
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		List<Object> list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list		
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		List<String> xjrjzlist=new ArrayList<String>();
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map			
			String kjqj = Validate.isNullToDefaultString((String)map.get("kjqj2"),"");
			String kjqjz = Validate.isNullToDefaultString((String)map.get("kkjqj2"),"");
			String kmbhstart =(String)map.get("startKjkm2");		
			String	startJc =(String)map.get("startJc2");
			String	qbjzpz =(String)map.get("qbjzpz2");
			String	yfhjzpz =Validate.isNullToDefaultString((String)map.get("yfhjzpz2"),"");
			if(Validate.noNull(kmbhstart)&&kmbhstart.indexOf(")")>0){
				kmbhstart = kmbhstart.substring(1,kmbhstart.indexOf(")"));
			}
			xjrjzlist.add(kjqj);
			xjrjzlist.add(kjqjz);
			if(xmbh!=""){
				xjrjzlist.add(xmbh);
			}else{
				xjrjzlist.add(kmbhstart);
			}			

			xjrjzlist.add(startJc);
			xjrjzlist.add(qbjzpz);
			xjrjzlist.add(yfhjzpz);
			xjrjzlist.add(sszt);
			xjrjzlist.add(LUser.getGuid());
			xjrjzlist.add(kjzd);
			StringBuffer sql = new StringBuffer();
			//综合查询拼接条件
			if(Validate.noNull(zy.trim())){
				sql.append(" and t.zy like '%"+zy+"%'");
			}
			if(Validate.noNull(pzbh)){
				sql.append(" and to_number(PZ.PZBH) = '"+pzbh+"'");
			}
			if(Validate.noNull(pzrq1)){
				sql.append(" and (CASE LENGTH(PZ.PZRQ) WHEN 7 THEN PZ.PZRQ || '-01' ELSE PZ.PZRQ END) >= '"+pzrq1+"'");
			}
			if(Validate.noNull(pzrq2)){
				sql.append(" and (CASE LENGTH(PZ.PZRQ) WHEN 7 THEN PZ.PZRQ || '-01' ELSE PZ.PZRQ END)<= '"+pzrq2+"'");
			}
			xjrjzlist.add(sql.toString());
		}
		boolean bl=xjrjzService.runPro("pro_cwyth_xjrjz",xjrjzlist);
		return bl;
	}

	
	
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		PageData pd = this.getPageData();	
		//执行存储过程
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		String zy = Validate.isNullToDefaultString(pd.getString("zy"),"");
		String pzbh = Validate.isNullToDefaultString(pd.getString("pzbh"),"");
		String pzrq1 = Validate.isNullToDefaultString(pd.getString("pzrq1"),"");
		String pzrq2 = Validate.isNullToDefaultString(pd.getString("pzrq2"),"");
		if(kmbh!=""&&kmbh.contains("(")){
			kmbh=kmbh.substring(1, kmbh.lastIndexOf(")"));					
		}
		
		String type = Validate.isNullToDefaultString(pd.getString("zjrjzType"),"");
		if(type.equals("ayfc")){
			boolean bl=runPro(request,kmbh,zy,pzbh,pzrq1,pzrq2);
			System.out.println("1的存储过程======="+bl);	
		}else if(type.equals("anlc")){
			boolean bl=runPro2(request,kmbh,zy,pzbh,pzrq1,pzrq2);
			System.out.println("2的存储过程======="+bl);
		}							
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		list=xjrjzService.getPageList(treesearch,kmbh,zy,pzbh,pzrq1,pzrq2);
		return list;
	}
	
	
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		String searchValue = pd.getString("searchJson");
		String flag = pd.getString("foo");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		 String ztgid1 = Constant.getztid(session);//账套编号
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "");//0月度1年度,数据库里查出来是空的，所以默认为空
		System.err.println("kmbh="+kmbh);
//		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
//		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
		
//		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),ztgid1);
//		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		sysDate = Validate.isNullToDefaultString(pd.getString("bbyf"), sdf.format(date));//
		List list = new ArrayList<Map<String,Object>>();
		String zy = Validate.isNullToDefaultString(pd.getString("zy"),"");
		String pzbh = Validate.isNullToDefaultString(pd.getString("pzbh"),"");
		String pzrq1 = Validate.isNullToDefaultString(pd.getString("pzrq1"),"");
		String pzrq2 = Validate.isNullToDefaultString(pd.getString("pzqr2"),"");
		list = xjrjzService.getPageList(treesearch,kmbh,zy,pzbh,pzrq1,pzrq2);
		System.out.println("WEAEAWE"+list);
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.xjrjzService.expExcel(realfile, shortfileurl,searchValue,flag,list);
	}
	
	
}
