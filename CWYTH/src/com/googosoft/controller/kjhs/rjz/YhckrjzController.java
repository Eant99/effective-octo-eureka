package com.googosoft.controller.kjhs.rjz;

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

import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.bbzx.XjrjzService;
import com.googosoft.service.kjhs.zjz.YhckrjzService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value = "/yhckrjz")
public class YhckrjzController extends BaseController {
	@Resource(name = "dictService")
	private DictService dictService;// 数据字典单例

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@Resource(name = "yhckrjzService")
	private YhckrjzService yhckrjzService;
	
	@Resource(name = "xjrjzService")
	private XjrjzService xjrjzService;
	
//	@Resource(name="pageService")
//	private PageService pageService;//单例
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
		String kmxx = Validate.isNullToDefaultString(pd.getString("kmxx"), "");		
		String jump = Validate.isNullToDefaultString(pd.getString("jump"), "");
		String bbqj = Validate.isNullToDefaultString(pd.getString("bbqj"), "");
		String clicks = Validate.isNullToDefaultString(pd.getString("clicks"), "");
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
		String yhckrjzType = Validate.isNullToDefaultString(pd.getString("yhckrjzType"), "");
		if("yes".equals(jump)){
			yhckrjzService.deleteKmyeb();
			request.getSession().removeAttribute("yhckrjzJson");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = Validate.isNullToDefaultString(bbqj, sdf.format(date));
		year = year.substring(0,4);
		String url = "kjhs/bbzx/rjz/yhckrjz";
		mv.addObject("kmxx", kmxx);
		mv.addObject("clicks", clicks);
		mv.addObject("jump", jump);
		mv.addObject("bbqj", bbqj);
		mv.addObject("kmbh", kmbh);
		mv.addObject("year", year);
		mv.addObject("yhckrjzType", yhckrjzType);
		mv.setViewName(url);
		return mv;
	}

	/**
	 * 弹出条件选择页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jumpWindow")
	public ModelAndView jumpWindow(HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//科目级别list
		List list = yhckrjzService.getKjkmJb();
		List list1 = yhckrjzService.getKmmj();
		mv.addObject("list", list);
		mv.addObject("list1", list1);
		String startTime0 = new SimpleDateFormat("yyyy").format(new Date());
		String startTime = startTime0+"-01";
		String endTime = new SimpleDateFormat("yyyy-MM").format(new Date());
		String syear = request.getSession().getAttribute("sBankyear")+"";
		String eyear = request.getSession().getAttribute("eBankyear")+"";
		if(Validate.noNull(syear)&&Validate.noNull(eyear)){
			mv.addObject("startTime",syear);
			mv.addObject("endTime",eyear);
		}else{
			mv.addObject("startTime",startTime);
			mv.addObject("endTime",endTime);
		}
		mv.setViewName("kjhs/bbzx/rjz/yhckrjz_search");
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
		
			String params = this.getPageData().getString("yhckrjzJson");	
			String yhckrjzType = this.getPageData().getString("type");	
			String syear = this.getPageData().getString("syear");	
			String eyear = this.getPageData().getString("eyear");	
			request.getSession().setAttribute("yhckrjzJson", params);
			request.getSession().setAttribute("sBankyear", syear);
			request.getSession().setAttribute("eBankyear", eyear);
			return yhckrjzType;
			
	}
	
	
	public boolean runPro(HttpServletRequest request,String xmbh,String zy,String pzbh,String pzrq1,String pzrq2) throws SQLException{
		int i;		
		String[] xmbhs = xmbh.split(",");
		String xmbhi = "";
		for(i=0;i<xmbhs.length;i++){
			if(i != xmbhs.length-1){
				if(xmbhs[i]!=""&&xmbhs[i].contains("(")){
					xmbhi += xmbhs[i].substring(1, xmbhs[i].lastIndexOf(")"))+",";					
				}
			}else{
				if(xmbhs[i]!=""&&xmbhs[i].contains("(")){
					xmbhi += xmbhs[i].substring(1, xmbhs[i].lastIndexOf(")"));					
				}
			}
		}
		String json = Validate.isNullToDefaultString(request.getSession().getAttribute("yhckrjzJson"), "");//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List<Object> list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list	
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		List<String> yhckrjzlist=new ArrayList<String>();
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
			if(Validate.noNull(kmbhstart)&&kmbhstart.indexOf(")")>0){
				kmbhstart = kmbhstart.substring(1,kmbhstart.indexOf(")"));
			}					
			yhckrjzlist.add(kjqj);
			yhckrjzlist.add(kjqjz);
			
			System.out.println("bh"+xmbhi);
			if(xmbhi !=""){
				yhckrjzlist.add(xmbhi);
			}else{
				yhckrjzlist.add(kmbhstart);
			}			
			
			yhckrjzlist.add(startJc);
			yhckrjzlist.add(qbjzpz);
			yhckrjzlist.add(yfhjzpz);
			yhckrjzlist.add(sszt);
			yhckrjzlist.add(LUser.getGuid());
			yhckrjzlist.add(kjzd);
			StringBuffer sql = new StringBuffer();
			//综合查询拼接条件
			if(Validate.noNull(zy.trim())){
				sql.append(" and t.zy like '%"+zy+"%'");
			}
			if(Validate.noNull(pzbh)){
				sql.append(" and to_number(PZ.PZBH) = '"+pzbh+"'");
			}
			if(Validate.noNull(pzrq1)){
				sql.append(" and to_char(pz.pzrq,'yyyy-mm-dd') >= '"+pzrq1+"'");
			}
			if(Validate.noNull(pzrq2)){
				sql.append(" and to_char(pz.pzrq,'yyyy-mm-dd') <= '"+pzrq2+"'");
			}
			yhckrjzlist.add(sql.toString());
			System.err.println(sql.toString());
		}
		boolean bl=yhckrjzService.runPro("pro_cwyth_yhckrjz",yhckrjzlist);
		return bl;
	}
	
	
	public boolean runPro2(HttpServletRequest request,String xmbh,String zy,String pzbh,String pzrq1,String pzrq2) throws SQLException{
		if(xmbh!=""&&xmbh.contains("(")){
			xmbh=xmbh.substring(1, xmbh.lastIndexOf(")"));					
		}	
		int i;		
		String json = Validate.isNullToDefaultString(request.getSession().getAttribute("yhckrjzJson"), "");
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List<Object> list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list		
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		List<String> yhckrjzlist=new ArrayList<String>();
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map			
			String kjqj = Validate.isNullToDefaultString((String)map.get("kjqj2"),"");
			String kjqjz = Validate.isNullToDefaultString((String)map.get("kkjqj2"),"");
			String kmbhstart =(String)map.get("startKjkm2");		
			String	startJc =(String)map.get("startJc2");
			String	qbjzpz =(String)map.get("qbjzpz2");
			String	yfhjzpz =Validate.isNullToDefaultString((String)map.get("yfhjzpz2"),"");
							
			yhckrjzlist.add(kjqj);
			yhckrjzlist.add(kjqjz);
			if(xmbh!=""){
				yhckrjzlist.add(xmbh);
			}else{
				yhckrjzlist.add(kmbhstart);
			}			

			yhckrjzlist.add(startJc);
			yhckrjzlist.add(qbjzpz);
			yhckrjzlist.add(yfhjzpz);
			yhckrjzlist.add(sszt);
			yhckrjzlist.add(LUser.getGuid());
			yhckrjzlist.add(kjzd);
			StringBuffer sql = new StringBuffer();
			//综合查询拼接条件
			if(Validate.noNull(zy.trim())){
				sql.append(" and t.zy like '%"+zy+"%'");
			}
			if(Validate.noNull(pzbh)){
				sql.append(" and to_number(PZ.PZBH) = '"+pzbh+"'");
			}
			if(Validate.noNull(pzbh)){
				sql.append(" and to_number(PZ.PZBH) = '"+pzbh+"'");
			}
			if(Validate.noNull(pzrq1)){
				sql.append(" and to_char(pz.pzrq,'yyyy-mm-dd') >= '"+pzrq1+"'");
			}
			if(Validate.noNull(pzrq2)){
				sql.append(" and to_char(pz.pzrq,'yyyy-mm-dd') <= '"+pzrq2+"'");
			}
			yhckrjzlist.add(sql.toString());
			System.err.println(sql.toString());
		}
		boolean bl=yhckrjzService.runPro("pro_cwyth_yhckrjz",yhckrjzlist);
		return bl;
	}

	
	
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();	
		//执行存储过程
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		System.out.println(kmbh+"dddddddddd");
		String type = Validate.isNullToDefaultString(pd.getString("yhckrjzType"),"");
		String zy = Validate.isNullToDefaultString(pd.getString("zy"),"");
		String pzbh = Validate.isNullToDefaultString(pd.getString("pzbh"),"");
		String pzrq1 = Validate.isNullToDefaultString(pd.getString("pzrq1"),"");
		String pzrq2 = Validate.isNullToDefaultString(pd.getString("pzrq2"),"");
		String jfje1 = Validate.isNullToDefaultString(pd.getString("jfje1"),"");
		String jfje2 = Validate.isNullToDefaultString(pd.getString("jfje2"),"");
		String dfje1 = Validate.isNullToDefaultString(pd.getString("dfje1"),"");
		String dfje2 = Validate.isNullToDefaultString(pd.getString("dfje2"),"");
		if(type.equals("ayfc")){
			boolean bl=runPro(request,kmbh,zy,pzbh,pzrq1,pzrq2);
			System.out.println("1的存储过程======="+bl);	
		}else if(type.equals("anlc")){
			boolean bl=runPro2(request,kmbh,zy,pzbh,pzrq1,pzrq2);
			System.out.println("2的存储过程======="+bl);
		}					
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select k.PZRQ,k.pzbhguid,k.PZH,k.pzlxmc,k.ZY,xh,");
		sql.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm999,999,999,990.00'))jfje,");
		sql.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm999,999,999,990.00'))dfje,");
		sql.append(" decode(k.fx,'0','借','1','贷','平')fx,");
		sql.append(" decode(nvl(k.ye,0.00),0.00,'',to_char(round(k.ye,2),'fm999,999,999,990.00'))ye");		
		sql.append(" from cwpt_yhckrjz k ");
		sql.append(" where login='"+LUser.getGuid()+"' and zy <>'日合计' ");
		if(Validate.noNull(zy)){
			sql.append(" and k.zy='"+zy+"' ");
		}
		if(Validate.noNull(pzbh)){
			sql.append(" and k.PZH='"+pzbh+"' ");
		}
		if(Validate.noNull(jfje1)){
			sql.append(" and k.jfje>="+jfje1+" ");
		}
		if(Validate.noNull(jfje2)){
			sql.append(" and k.jfje<="+jfje2+" ");
		}
		if(Validate.noNull(dfje1)){
			sql.append(" and k.dfje>="+dfje1+" ");
		}
		if(Validate.noNull(dfje2)){
			sql.append(" and k.dfje<="+dfje2+" ");
		}
		/*if(Validate.noNull(pzrq1)){
			sql.append(" and (CASE LENGTH(k.PZRQ) WHEN 7 THEN k.PZRQ || '-01' ELSE k.PZRQ END) >= '"+pzrq1+"'");
		}
		if(Validate.noNull(pzrq2)){
			sql.append(" and (CASE LENGTH(k.PZRQ) WHEN 7 THEN k.PZRQ || '-01' ELSE k.PZRQ END)<= '"+pzrq2+"'");
		}*/
		sql.append(" ORDER BY case when length(pzrq)<10 then to_date(pzrq,'yyyy-mm') else to_date(pzrq,'yyyy-mm-dd') end ASC,xh");
		list=yhckrjzService.getPageList(sql.toString());
		return list;
		
	}
	
	/**
	 * 获得sql语句
	 * @param pxfs
	 * @return
	 */
	public String getSql(){
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" (select distinct * from");
		sqltext.append(" (select to_char(K.PZRQ,'yyyy-mm-dd') PZRQ,k.PZH,k.ZY,");
		sqltext.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm999,999,999,990.00'))jfje,");
		sqltext.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm999,999,999,990.00'))dfje,");
		sqltext.append(" case when k.fx = '0' then '借方' when k.fx = '1' then '贷方' end as FX,");
		sqltext.append(" decode(nvl(k.ye,0.00),0.00,'',to_char(round(k.ye,2),'fm999,999,999,990.00'))ye");		
		sqltext.append(" from cwpt_yhckrjz k ");
		sqltext.append(" where login='"+LUser.getGuid()+"')a");
		sqltext.append(" union all");
		sqltext.append(" (select to_char(pzrq,'yyyy-mm-dd') pzrq,null,'日合计' as zy,");
		sqltext.append(" decode(sum(jfje),0.00,'',to_char(round(sum(jfje),2),'fm999,999,999,990.00'))jffs,");
		sqltext.append(" decode(sum(dfje),0.00,'',to_char(round(sum(dfje),2),'fm999,999,999,990.00'))dffs,");
		sqltext.append(" case when fx = '0.00' then '借方' when fx = '1' then '贷方' end as FX,");
		sqltext.append(" null ");
		sqltext.append(" from cwpt_yhckrjz where login='"+LUser.getGuid()+"'  group by pzrq,fx )  )");
		return sqltext.toString();
	}
	
	
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		int i;
		String searchValue = pd.getString("searchJson");
		String flag = pd.getString("foo");
		String json = Validate.isNullToDefaultString(request.getSession().getAttribute("yhckrjzJson"), "");//得到前台的json
		System.out.println("json======="+json);
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List<Object> list1 = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list	
		List<String> yhckrjzlist=new ArrayList<String>();
		for (i=0;i<list1.size();i++) {
			Map map = (Map) list1.get(i);//将list转为map			
			String kjqj = Validate.isNullToDefaultString((String)map.get("kjqj"),"");
			String kkjqj = Validate.isNullToDefaultString((String)map.get("kkjqj"),"");
			String kmbhstart =(String)map.get("startKjkm");		
			String	startJc =(String)map.get("startJc");
			String	qbjzpz =(String)map.get("qbjzpz");
			String	yfhjzpz =Validate.isNullToDefaultString((String)map.get("yfhjzpz"),"");
			yhckrjzlist.add(kjqj);
			yhckrjzlist.add(kkjqj);
		System.err.println("list+list+"+list1);
		System.err.println("kjqj="+kjqj+"  kkjqj="+kkjqj+"  kmbhstart="+kmbhstart+"   startJc="+startJc+"   qbjzpz="+qbjzpz+"    yfhjzpz="+yfhjzpz);
		 String ztgid1 = Constant.getztid(session);//账套编号
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "0");//0月度1年度
		kmbh=pd.getString("kmbh");
		System.err.println("kmbh="+kmbh);
		
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		sysDate = Validate.isNullToDefaultString(pd.getString("bbyf"), sdf.format(date));//
//		List list = new ArrayList();
//		list = yhckrjzService.daochu(ztgid1, kmbh,kjqj,kkjqj,kmbhstart,startJc,qbjzpz,yfhjzpz);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select k.PZRQ,k.pzlxmc,k.PZH,k.ZY,xh,");
		sql.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm999,999,999,990.00'))jfje,");
		sql.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm999,999,999,990.00'))dfje,");
		sql.append(" decode(k.fx,'0','借','1','贷','平')fx,");
		sql.append(" decode(nvl(k.ye,0.00),0.00,'',to_char(round(k.ye,2),'fm999,999,999,990.00'))ye");		
		sql.append(" from cwpt_yhckrjz k ");
		sql.append(" where login='"+LUser.getGuid()+"' and k.zy!='日合计'");
		sql.append(" ORDER BY case when length(pzrq)<9 then to_date(pzrq,'yyyy-mm') else to_date(pzrq,'yyyy-mm-dd') end ASC,xh");
		list=yhckrjzService.getPageList(sql.toString());
		System.out.println("WEAEAWE"+list);
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.yhckrjzService.expExcel(realfile, shortfileurl,searchValue,flag,list);
	}
		return yhckrjzlist;
	
}
	
	/**
	 * 导出数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doExp", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String doExp(){
		PageData pd = this.getPageData();
		String id = Validate.isNullToDefault(pd.getString("id"), "")+"";	
		String dwbh = pd.getString("dwbh");
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname");// + ".xls";
		//查询数据的sql语句
		String searchJson = ToSqlUtil.jsonToSql(pd.getString("searchJson"));
		
		String sql = "";
		PageList pagelist = new PageList();

		List<List<M_largedata>> tlist = new ArrayList<List<M_largedata>>();//表头集合
		List<M_largedata> mlist = null;//存放数据
		M_largedata m = null;
		
		mlist = new ArrayList<M_largedata>();
		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("序号");//第一列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("学年");//第二列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("仪器编号");//第三列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("分类号");//第四列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("仪器名称");//第五列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setEcol(3);//合并4列
		m.setShowname("使用机时");//第六-九列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("测样数");//第十列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setEcol(2);//合并3列
		m.setShowname("培训人数");//第十一至十三列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("教学实验项目数");//第十四列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("科研项目数");//第十五列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("社会服务项目数");//第十六列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setEcol(1);//合并2列
		m.setShowname("获奖情况");//第十七至十八列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setEcol(1);//合并2列
		m.setShowname("发明专利");//第十九至二十列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setEcol(1);//合并2列
		m.setShowname("论文情况");//第二十一至二十二列的列名
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setIsmerge(true);
		m.setErow(1);//合并行2行
		m.setShowname("负责人姓名");//第二十三列的列名
		mlist.add(m);
		m = null;
		tlist.add(mlist);//第一行表头结束
		mlist = null;
		
		mlist = new ArrayList<M_largedata>();
		m = new M_largedata();
		//m.setIsmerge(true);
		m.setSindex(6);//上一行的前5列合并行了，所以第二行的第一个单元格从第6列开始
		m.setShowname("教学");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("科研");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("社会服务");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("其中开放使用机时");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		//m.setIsmerge(true);
		m.setSindex(11);//上一行的第10列合并行了，所以第二行的这个单元格从第11列开始
		m.setShowname("学生");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("教师");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("其他");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		//m.setIsmerge(true);
		m.setSindex(17);//上一行的第16列合并行了，所以第二行的这个单元格从第17列开始
		m.setShowname("国家级");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("省部级");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("教师");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("学生");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("三大检索");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setShowname("核心刊物");
		mlist.add(m);
		m = null;
		tlist.add(mlist);//第二行表头结束
		mlist = null;
		
		//数据内容
		mlist = new ArrayList<M_largedata>();
		m = new M_largedata();
		m.setName("rn");//序号直接写rn即可
		m.setShowname("序号");//多行表头的Showname可以省略
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("nd");
		m.setShowname("学年");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("yqbh");
		m.setShowname("仪器编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("flh");
		m.setShowname("分类号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("yqmc");
		m.setShowname("仪器名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setColstyle("double");
		m.setName("jxjs");
		m.setShowname("教学");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setColstyle("double");
		m.setName("kyjs");
		m.setShowname("科研");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setColstyle("double");
		m.setName("shfwjs");
		m.setShowname("社会服务");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setColstyle("double");
		m.setName("kfsyjs");
		m.setShowname("其中开放使用机时");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("xncys");
		m.setShowname("测样数");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("pxbks");
		m.setShowname("学生");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("pxjs");
		m.setShowname("教师");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("pxqtr");
		m.setShowname("其他");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("jxsyxms");
		m.setShowname("教学实验项目数");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("kyxms");
		m.setShowname("科研项目数");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("shfwxms");
		m.setShowname("社会服务项目数");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("gjjjs");
		m.setShowname("国家级");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("qtjls");
		m.setShowname("省部级");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("jsfmzl");
		m.setShowname("教师");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("xsfmzl");
		m.setShowname("学生");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("lwsdjx");
		m.setShowname("三大检索");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("lwhxkw");
		m.setShowname("核心刊物");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("fzrxm");
		m.setShowname("负责人姓名");
		mlist.add(m);
		m = null;
		
		getPageListDoExcel(pd,pagelist,dwbh,id);
		if(Validate.noNull(searchJson)){
			pagelist.setStrWhere(pagelist.getStrWhere()+" "+searchJson+" ");//查询条件语句
		}
		sql = "select " + pagelist.getSqlText() + " from " + pagelist.getTableName() + " where 1 = 1 " + pagelist.getStrWhere() + (Validate.isNull(pagelist.getOrderBy()) ? " order by yqbh" : pagelist.getOrderBy());
		
		//导出方法
//		pageService.ExpExcel(sql,realfile,filedisplay,mlist);
		pageService.ExpExcel(sql,realfile,filedisplay,mlist,tlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}	
	
	
	/**
	 * 导出sql
	 * @param pd
	 * @param pagelist
	 * @param id
	 * @return
	 */
	private PageList getPageListDoExcel(PageData pd, PageList pageList, String dwbh, String id) {
		pageList.setSqlText("K.id,K.yqbh,K.nd - 1 || '-' || k.nd as nd,K.jxjs,K.kyjs,K.shfwjs,K.kfsyjs,K.xncys,"
				+ "K.pxjs,k.pxbks,K.pxqtr,K.jxsyxms,K.kyxms,K.shfwxms,K.GJJJS,K.QTJLS,K.jsfmzl,K.xsfmzl,"
				+ "(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=t.fzr) as fzr,"
				+ "K.LWSDJX,K.lwhxkw,K.jyid,t.flh,t.yqmc,t.sydw,t.fzr as fzrxm ");
		pageList.setKeyId("K.id");
		pageList.setStrWhere(" and k.jyid in (select jybh from zc_jmyqb where ztbz='"+StateManager.JMYQ_TG+"') ");
		pageList.setStrWhere(Validate.isNullToDefault(pageList.getStrWhere(), "")+"");
		
		if(Validate.noNull(dwbh)){
			//点击左侧单位树的where条件
			pageList.setStrWhere(pageList.getStrWhere()+" AND t.sydw IN (SELECT DWBH FROM GX_SYS_DWB CONNECT BY PRIOR DWBH=SJDW START WITH DWBH='" + dwbh + "')");
		}else{
			//当前登录人管理单位权限
			pageList.setStrWhere(pageList.getStrWhere()+pageService.getQxsql(LUser.getRybh(), "t.sydw", "D"));
		}
			
		if(Validate.noNull(id)){
			StringBuffer in = new StringBuffer();
			String[] arrays =id.split(",");
			for (int i = 0; i < arrays.length; i++) {
				in.append(arrays[i]+"','");
			}
			String ids = in.substring(0,in.length()-3);
			pageList.setStrWhere(Validate.isNullToDefault(pageList.getStrWhere(), "")+" and K.id in ('"+ids+"')");
		}
		pageList.setTableName("zc_jmyqmxb K left join zc_jmyqb t on t.jybh = k.jyid");
		return pageList;
	}
	
	/**
	 * 获取 1002 xia 会计科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getUnderPageList")
	@ResponseBody
	public Object getUnderPageList( HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		 String kjzd = Validate.isNullToDefaultString(CommonUtil.getKjzd(session), "all");
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select distinct to_char(kmnd,'yyyy') as kmnd,KJZD, c.sfkjzd,c.sfmj,c.sjfl, c.jb, c.bz,c.guid, c.kmbh, c.kmmc,  c.kmsx,  (select d.mc from gx_sys_dmb d where d.zl='kmsx' and d.dm=c.kmsx)as kmsxmc, c.zjf, c.yefx,(case c.yefx when '1' then '贷方' else '借方'end) as yefxmc, c.hslb,"
				+ " (select d.mc from gx_sys_dmb d where d.zl='hslb' and d.dm = c.hslb) as hslbmc, c.kmjc, c.qyf,(case c.qyf when '1' then '是' else '否'end) as qyfmc,"
				+ " c.sfwyh, (case c.sfwyh when '1' then '是' else '否'end) as sfwyhmc,c.sfjjflkm,(case c.sfjjflkm when '1' then '是' else '否'end) as sfjjflkmmc,"
				+ " c.sfgnflkm,(case c.sfgnflkm when '1' then '是' else '否'end) as sfgnflkmmc, c.bmhs,(case c.bmhs when '1' then '是' else '否'end) as bmhsmc, c.xmhs,"
				+ " (case c.xmhs when '1' then '是' else '否'end) as xmhsmc, c.czr, c.czrq from Cw_kjkmszb c where 1=1 and sjfl !='root' and sszt='"+sszt+"' ");
		tablename.append(" start with c.jb='1002' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root' ");
		tablename.append(" ) k ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(""); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
}
