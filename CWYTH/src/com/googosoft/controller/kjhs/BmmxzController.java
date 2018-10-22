package com.googosoft.controller.kjhs;

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
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.BmmxzService;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value="/kjhs/bmmxz")
public class BmmxzController extends BaseController {
	
	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="bmmxzService")
	private BmmxzService bmmxzService;//单例
	
	/**
	 * 获取单位信息列表页面
	 * @return
	 */
	@RequestMapping(value="/golist")
	public ModelAndView goDwbPage(HttpServletRequest request){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/bbzx/bmmxz/bmmxz_list");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String bbqj = Validate.isNullToDefaultString(pd.getString("bbqj"),Validate.isNullToDefaultString(request.getSession().getAttribute("bbqj"), ""));
		String year = Validate.isNullToDefaultString(bbqj,sdf.format(date));
		year = year.substring(0,4);
		mv.addObject("year", year);
		List<Map<String,Object>> months = kmyeService.getMonth();
		mv.addObject("months", months);
		String dwbh = Validate.isNullToDefaultString(pd.getString("dwbh"),"");
		mv.addObject("dwbh", dwbh);
		return mv;
	}
	/**
	 * 获取单位信息列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,
			HttpServletResponse response){
		PageList pageList = new PageList();
		
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String bmbh = pd.getString("bmbh");
		String starttime = pd.getString("starttime");
		String endtime = pd.getString("endtime");
	 //处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" (select k.guid,k.bmbh,k.kmbh,k.xmbh,");
		sqltext.append("  '('||d.dwbh||')'||d.mc bmmc,");
		sqltext.append(" to_char(z.pzrq,'yyyy-MM-dd')pzrq,");
		sqltext.append(" z.guid as pzid,z.pzlxmc,z.pzbh,z.pzz,k.zy,z.pzzt,");
		sqltext.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm9999999999999.00'))jfje,");
		sqltext.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm9999999999999.00'))dfje,");
		sqltext.append(" decode(k.jdfx,'0','借','1','贷','')jdfx,z.kjqj,z.pznd,");
		sqltext.append(" '('||j.kmbh||')'||j.kmmc jjfl");
		sqltext.append(" from cw_pzlrmxb k");
		sqltext.append(" left join cw_pzlrzb z on z.guid=k.pzbh");
		sqltext.append(" left join gx_sys_dwb d on d.dwbh=k.bmbh");
		sqltext.append(" left join cw_jjkmb j on j.kmbh=k.jjfl");
		sqltext.append("  where k.bmbh is not null ");
		if(Validate.noNull(starttime)){
			sqltext.append(" and to_char(z.pzrq, 'yyyy-MM') >='"+starttime+"' ");
		}
		if(Validate.noNull(endtime)){
			sqltext.append(" and  to_char(z.pzrq, 'yyyy-MM') <='"+endtime+"' ");
		}
		if(Validate.noNull(bmbh)){
			sqltext.append("  and d.dwbh='"+bmbh+"' ");
		}
		sqltext.append(" ) k");
		
		pageList.setSqlText("guid,pzid,bmbh,kmbh,xmbh,decode(bmmc,'()','',bmmc)bmmc,pzzt,pzrq,pzlxmc,pzbh,zy,jfje,dfje,jdfx,decode(jjfl,'()','',jjfl)jjfl");
//		if(Validate.noNull(kjkm)){
//			sqltext.append (" and k.kmbh='"+kjkm+"' and z.pznd='"+pznd+"' and z.kjqj between "+startMonth+" and "+endMonth);
//		}
		//设置表名
		pageList.setTableName(sqltext.toString());
		//设置表主键名
		pageList.setKeyId("k.guid");
		//设置WHERE条件
		//PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");

		String rybh = LUser.getRybh();//当前登陆者的人员编号
		pageList.setStrWhere(pageService.getDwqxWhereSql(rybh, "K.bmbh", dwbh, true)); //获取管理单位权限
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		//Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	@RequestMapping(value = "/getBmMxzList")
	@ResponseBody
	public List<Map<String,Object>> getBmMxzList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		String treebmbh = Validate.isNullToDefaultString(request.getParameter("treedwbh"), "");
		String pznd = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String startMonth = Validate.isNullToDefaultString(request.getParameter("kjqj1"), "");
		String endMonth = Validate.isNullToDefaultString(request.getParameter("kjqj2"), "");
		String pzzt = Validate.isNullToDefaultString(request.getParameter("pzzt"), "");
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");//会计科目
		if(Validate.noNull(kmbh)){
			kmbh = kmbh.substring(1, kmbh.indexOf(")"));
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
		return bmmxzService.getBmMxzList(treebmbh, pznd, startMonth, endMonth,pzzt,kmbh,bmbh,xmbh,jfjel,jfjeh,zy);
	}
	/**
	 * 导出
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
		String searchValue = pd.getString("searchJson");
		//查询数据的sql语句
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select * from (select k.guid,k.bmbh,k.kmbh,k.xmbh,");
		sqltext.append("  '('||d.dwbh||')'||d.mc bmmc,");
		sqltext.append(" to_char(z.pzrq,'yyyy-MM-dd')pzrq,");
		sqltext.append(" z.pzlxmc,z.pzbh,z.pzz,z.pzzt,k.zy,");
		sqltext.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm9999999999999.00'))jfje,");
		sqltext.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm9999999999999.00'))dfje,");
		sqltext.append(" decode(k.jdfx,'0','借','1','贷','')jdfx,z.kjqj,z.pznd,");
		sqltext.append(" j.kmmc jjfl");
		sqltext.append(" from cw_pzlrmxb k");
		sqltext.append(" left join cw_pzlrzb z on z.guid=k.pzbh");
		sqltext.append(" left join gx_sys_dwb d on d.dwbh=k.bmbh");
		sqltext.append(" left join cw_jjkmb j on j.kmbh=k.jjfl)k");
		sqltext.append("  where k.bmbh is not null");
				
		String dwbh = pd.getString("treedwbh");
       
		if(Validate.noNull(dwbh)){
			sqltext.append(" and k.bmbh like '"+dwbh+"%'");
		}
		
		String guid = pd.getString("guid");
		if(Validate.noNull(guid)){
			sqltext.append(" and k.guid in ('"+guid+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchValue));
		sqltext.append(" order by pzrq desc");
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("BMMC");
		m.setShowname("部门名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("pzrq");
		m.setShowname("凭证日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("pzlxmc");
		m.setShowname("凭证类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("pzbh");
		m.setShowname("凭证号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ZY");
		m.setShowname("摘要");
		mlist.add(m);
		m = null;		
		
		m = new M_largedata();
		m.setName("JFJE");
		m.setShowname("本期发生额（借方）");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("DFJE");
		m.setShowname("本期发生额（贷方）");
		mlist.add(m);
		m = null;
		
		
		m = new M_largedata();
		m.setName("JJFL");
		m.setShowname("经济科目名称");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	
}
