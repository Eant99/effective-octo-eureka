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
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.bbzx.XjrjzService;
import com.googosoft.service.kjhs.zjz.LyerjzService;
import com.googosoft.service.kjhs.zjz.YhckrjzService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value = "/lyerjz")
public class LyerjzController extends BaseController {
	@Resource(name = "dictService")
	private DictService dictService;// 数据字典单例

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@Resource(name = "lyerjzService")
	private LyerjzService lyerjzService;
	
	@Resource(name = "yhckrjzService")
	private YhckrjzService yhckrjzService;
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
		String lyerjzType = Validate.isNullToDefaultString(pd.getString("lyerjzType"), "");
		if("yes".equals(jump)){
			lyerjzService.deleteKmyeb();
			request.getSession().removeAttribute("lyerjzJson");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = Validate.isNullToDefaultString(bbqj, sdf.format(date));
		year = year.substring(0,4);
		String url = "kjhs/bbzx/rjz/lyerjz";
		mv.addObject("kmxx", kmxx);
		mv.addObject("clicks", clicks);
		mv.addObject("jump", jump);
		mv.addObject("bbqj", bbqj);
		mv.addObject("year", year);
		mv.addObject("kmbh", kmbh);
		mv.addObject("lyerjzType", lyerjzType);
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
		//科目级别list
		List list = lyerjzService.getKjkmJb();
		List list1 = lyerjzService.getKmmj();
		mv.addObject("list", list);
		mv.addObject("list1", list1);
		mv.setViewName("kjhs/bbzx/rjz/lyerjz_search");
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
		
			String params = this.getPageData().getString("lyerjzJson");	
			String lyerjzType = this.getPageData().getString("type");	
			request.getSession().setAttribute("lyerjzJson", params);
			return lyerjzType;
			
	}
	
	
	public boolean runPro(HttpServletRequest request,String xmbh,String zy,String pzbh,String pzrq1,String pzrq2) throws SQLException{
		int i;		
		if(xmbh!=""&&xmbh.contains("(")){
			xmbh=xmbh.substring(1, xmbh.lastIndexOf(")"));					
		}
		String json = Validate.isNullToDefaultString(request.getSession().getAttribute("lyerjzJson"), "");//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List<Object> list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list	
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		List<String> lyerjzlist=new ArrayList<String>();
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map			
			String kjqj = Validate.isNullToDefaultString((String)map.get("kjqj"),"");
			String kjqjz = Validate.isNullToDefaultString((String)map.get("kkjqj"),"");
			String kmbhstart1 =(String)map.get("startKjkm");		
			String kmbhstart2=kmbhstart1.substring(kmbhstart1.lastIndexOf("("),kmbhstart1.lastIndexOf(")"));
			String kmbhstart=kmbhstart2.substring(1,kmbhstart2.length());
			String	startJc =(String)map.get("startJc");
			String	qbjzpz =Validate.isNullToDefaultString((String)map.get("qbjzpz"),"");
			String	yfhjzpz =Validate.isNullToDefaultString((String)map.get("yfhjzpz"),"");			
							
			lyerjzlist.add(kjqj);
			lyerjzlist.add(kjqjz);
			
			if(xmbh!=""){
				lyerjzlist.add(xmbh);
			}else{
				lyerjzlist.add(kmbhstart);
			}			
			
			lyerjzlist.add(startJc);
			lyerjzlist.add(qbjzpz);
			lyerjzlist.add(yfhjzpz);
			lyerjzlist.add(sszt);
			lyerjzlist.add(LUser.getGuid());
			lyerjzlist.add(kjzd);
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
			lyerjzlist.add(sql.toString());
		}
		boolean bl=lyerjzService.runPro("pro_cwyth_lyerjz",lyerjzlist);
		return bl;
	}
	
	
	public boolean runPro2(HttpServletRequest request,String xmbh,String zy,String pzbh,String pzrq1,String pzrq2) throws SQLException{
		if(xmbh!=""&&xmbh.contains("(")){
			xmbh=xmbh.substring(1, xmbh.lastIndexOf(")"));					
		}	
		int i;		
		String json = Validate.isNullToDefaultString(request.getSession().getAttribute("lyerjzJson"), "");
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List<Object> list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list		
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		List<String> lyerjzlist=new ArrayList<String>();
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
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
			lyerjzlist.add(kjqj);
			lyerjzlist.add(kjqjz);
			if(xmbh!=""){
				lyerjzlist.add(xmbh);
			}else{
				lyerjzlist.add(kmbhstart);
			}			

			lyerjzlist.add(startJc);
			lyerjzlist.add(qbjzpz);
			lyerjzlist.add(yfhjzpz);
			lyerjzlist.add(sszt);
			lyerjzlist.add(LUser.getGuid());
			lyerjzlist.add(kjzd);
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
			lyerjzlist.add(sql.toString());
		}
		boolean bl=lyerjzService.runPro("pro_cwyth_lyerjz",lyerjzlist);
		return bl;
	}
	/**
	 * 获取列表数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();	
		//执行存储过程
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		String type = Validate.isNullToDefaultString(pd.getString("lyerjzType"),"");
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
		}else if(type.equals("anlc")){
			boolean bl=runPro2(request,kmbh,zy,pzbh,pzrq1,pzrq2);
		}					
		//执行表查询	
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select k.PZRQ,k.pzbhguid,k.PZH,k.pzlxmc,k.ZY,xh,");
		sql.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm999,999,999,990.00'))jfje,");
		sql.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm999,999,999,990.00'))dfje,");
		sql.append(" decode(k.fx,'0','借','1','贷','平')fx,");
		sql.append(" decode(nvl(k.ye,0.00),0.00,'',to_char(round(k.ye,2),'fm999,999,999,990.00'))ye");		
		sql.append(" from cwpt_lyerjz k ");
		sql.append(" where login='"+LUser.getGuid()+"'");
		if(Validate.noNull(zy)){
			sql.append(" and k.zy like '%"+zy+"%' ");
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
			sql.append(" and (CASE LENGTH(k.PZRQ) WHEN 7 THEN PZRQ || '-01' ELSE PZRQ END) >= '"+pzrq1+"'");
		}
		if(Validate.noNull(pzrq2)){
			sql.append(" and (CASE LENGTH(k.PZRQ) WHEN 7 THEN PZRQ || '-01' ELSE PZRQ END)<= '"+pzrq2+"'");
		}*/
		sql.append(" ORDER BY case when length(pzrq)<10 then to_date(pzrq,'yyyy-mm') else to_date(pzrq,'yyyy-mm-dd') end ASC,pzh,xh");
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
		sqltext.append(" from cwpt_lyerjz k ");
		sqltext.append(" where login='"+LUser.getGuid()+"')a");
		sqltext.append(" union all");
		sqltext.append(" (select to_char(pzrq,'yyyy-mm-dd') pzrq,null,'日合计' as zy,");
		sqltext.append(" decode(sum(jfje),0.00,'',to_char(round(sum(jfje),2),'fm999,999,999,990.00'))jffs,");
		sqltext.append(" decode(sum(dfje),0.00,'',to_char(round(sum(dfje),2),'fm999,999,999,990.00'))dffs,");
		sqltext.append(" case when fx = '0.00' then '借方' when fx = '1' then '贷方' end as FX,");
		sqltext.append(" null ");
		sqltext.append(" from cwpt_lyerjz where login='"+LUser.getGuid()+"'  group by pzrq,fx )  )");
		return sqltext.toString();
	}
	/**
	 * 获取 1011 xia 会计科目设置页面信息
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
		tablename.append(" start with c.jb='1011' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root' ");
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
	
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		String searchValue = pd.getString("searchJson");
		String flag = pd.getString("foo");
		
		 String ztgid1 = Constant.getztid(session);//账套编号
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "0");//0月度1年度
//		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
//		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
		
//		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),ztgid1);
//		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		sysDate = Validate.isNullToDefaultString(pd.getString("bbyf"), sdf.format(date));//
//		List list = new ArrayList();
//		list = lyerjzService.daochu(ztgid1, kmbh);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select k.PZRQ,k.PZH,k.ZY,xh,");
		sql.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm999,999,999,990.00'))jfje,");
		sql.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm999,999,999,990.00'))dfje,");
		sql.append(" decode(k.fx,'0','借','1','贷','平')fx,");
		sql.append(" decode(nvl(k.ye,0.00),0.00,'',to_char(round(k.ye,2),'fm999,999,999,990.00'))ye");		
		sql.append(" from cwpt_lyerjz k ");
		sql.append(" where login='"+LUser.getGuid()+"'");
		sql.append(" ORDER BY case when length(pzrq)<9 then to_date(pzrq,'yyyy-mm') else to_date(pzrq,'yyyy-mm-dd') end ASC,xh");
		list=yhckrjzService.getPageList(sql.toString());
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.lyerjzService.expExcel(realfile, shortfileurl,searchValue,flag,list);
	}
	
	
}
