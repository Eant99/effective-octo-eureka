package com.googosoft.controller.kjhs.pzxx;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.pzxx.PzcxsService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/gwkcx")
public class GwkcxController extends BaseController{
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name = "pzcxsService")
	private PzcxsService pzcxsService;
	
	/*
	 * 1
	 */
	@RequestMapping(value="/dcztlist")
	public ModelAndView dcztlist(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("dczt",pd.getString("dczt"));
		mv.addObject("pzzt",pd.getString("pzzt"));
		mv.addObject("zfzt",pd.getString("zfzt"));
		mv.addObject("pzh1",pd.getString("pzh1"));
		mv.addObject("pzh2",pd.getString("pzh2"));
		mv.addObject("pzrq1",pd.getString("pzrq1"));
		mv.addObject("pzrq2",pd.getString("pzrq2"));
		mv.setViewName("kjhs/pzxx/gwkcx/gwkcx_list");
		return mv;
	}
	
	/*
	 * 2
	 */
	@RequestMapping(value="/pzztlist")
	public ModelAndView pzztlist(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("dczt",pd.getString("dczt"));
		mv.addObject("pzzt",pd.getString("pzzt"));
		mv.addObject("zfzt",pd.getString("zfzt"));
		mv.addObject("pzh1",pd.getString("pzh1"));
		mv.addObject("pzh2",pd.getString("pzh2"));
		mv.addObject("pzrq1",pd.getString("pzrq1"));
		mv.addObject("pzrq2",pd.getString("pzrq2"));
		mv.setViewName("kjhs/pzxx/gwkcx/gwkcx_list");
		return mv;
	}
	
	/*
	 * 3
	 */
	@RequestMapping(value="/zfztlist")
	public ModelAndView zfztlist(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("dczt",pd.getString("dczt"));
		mv.addObject("pzzt",pd.getString("pzzt"));
		mv.addObject("zfzt",pd.getString("zfzt"));
		mv.addObject("pzh1",pd.getString("pzh1"));
		mv.addObject("pzh2",pd.getString("pzh2"));
		mv.addObject("pzrq1",pd.getString("pzrq1"));
		mv.addObject("pzrq2",pd.getString("pzrq2"));
		mv.setViewName("kjhs/pzxx/gwkcx/gwkcx_list");
		return mv;
	}
	
	/**
	 * all
	 * @return
	 */
	@RequestMapping(value="/checklist")
	public ModelAndView checklist(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		String pzzt=pd.getString("pzzt");
		String dczt=pd.getString("dczt");
		String zfzt=pd.getString("zfzt");
		
		String pzh1=pd.getString("pzh1");
		String pzh2=pd.getString("pzh2");
		String pzrq1=pd.getString("pzrq1");
		String pzrq2=pd.getString("pzrq2");
		
		
		mv.addObject("pzzt",pzzt);
		mv.addObject("dczt",dczt);
		mv.addObject("zfzt",zfzt);
		
		mv.addObject("pzh1",pzh1);
		mv.addObject("pzh2",pzh2);
		mv.addObject("pzrq1",pzrq1);
		mv.addObject("pzrq2",pzrq2);
		mv.setViewName("kjhs/pzxx/gwkcx/gwkcx_list");
		return mv;
	}
	
	/**
	 * 公务卡查询页面
	 * @return
	 */
	@RequestMapping(value="/goGwkcxList")
	public ModelAndView goPzlrPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/pzxx/gwkcx/gwkcx_list");
		return mv;
	}
	
	@RequestMapping("/fanhuisj")
	@ResponseBody
	public void fanhuisj(String guid) {
		int a = pzcxsService.fanhuisj(guid);
	}
	
	/**
	 * 获取列表页面数据
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
	    PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String strWhere="";
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( SELECT nvl(t.dczt,'0') AS dczt,nvl(t.status,'0') AS zfzt,t.guid,zb.pzzt,zb.pzlxmc,zb.pzbh,to_char(zb.pzrq,'yyyy-mm-dd') as pzrq ,mx.zy,t.xm,t.yhmc,t.yhzh,to_char(nvl(t.je,'0'), 'FM999999999999990.00') je,t.req_time AS sksj, ");
		tablename.append("(case nvl(t.dczt, '0') when '1' then '已导出' else '未导出'end )dcztmc,");
		tablename.append("(case zb.pzzt when '00' then '已保存'  else  '已复核' end) pzztmc,");
		tablename.append(" '' skje, "); 
		tablename.append("(case nvl(t.status, '0') when '1' then '已支付' else '未支付'end )zfztmc ");
		tablename.append(" FROM CW_PZLRYHMX T ");
		tablename.append(" LEFT JOIN CW_PZLRMXB MX ON (MXID = MX.GUID )" );
		tablename.append(" LEFT JOIN CW_FZLRB FZ ON (FZ.KMBH = MX.GUID) " ); 
		tablename.append(" LEFT JOIN cw_pzlrzb zb ON t.zbid=zb.guid " ); 
		tablename.append(" WHERE 1=1" );
		tablename.append(" AND (SUBSTR(MX.KMBH, 0, 4) = '1011' OR (SUBSTR(MX.KMBH, 0, 4) = '1002' AND  MX.KMBH != '100202') OR SUBSTR(MX.KMBH, 0, 4) = '1012')" ); 
		tablename.append(" AND FZ.JSFS != '0005'" ); 
		tablename.append(" union " ); 
		tablename.append(" SELECT nvl(Fz.dczt,'0') AS dczt,nvl(fz.zfzt,'0') AS zfzt,fz.guid,zb.pzzt,zb.pzlxmc,zb.pzbh,to_char(zb.pzrq,'yyyy-mm-dd') as pzrq ,mx.zy,fz.bz AS xm,'公务卡' AS yhmc,gwkh as yhzh,to_char(nvl(MX.dfje,'0'), 'FM999999999999990.00') je,fz.sksj AS sksj,"); 
		tablename.append(" (case nvl(Fz.dczt, '0') when '1' then '已导出' else '未导出'end )dcztmc," ); 
		tablename.append(" (case zb.pzzt when '00' then '已保存'  else  '已复核' end) pzztmc," ); 
		tablename.append(" to_char(fz.sjskje, 'FM999999999999990.00') as skje,"); 
		tablename.append(" (case nvl(Fz.zfzt, '0') when '1' then '已支付' else '未支付'end )zfztmc" ); 
		tablename.append(" from cw_fzlrb Fz  " ); 
		tablename.append(" LEFT JOIN CW_PZLRMXB MX ON (fz.kmbh = MX.GUID )  " );  
		tablename.append(" LEFT JOIN cw_pzlrzb zb ON MX.pzbh=zb.guid   WHERE 1=1" );  
		tablename.append(" AND (SUBSTR(MX.KMBH, 0, 4) = '1011' OR (SUBSTR(MX.KMBH, 0, 4) = '1002' AND  MX.KMBH != '100202') OR SUBSTR(MX.KMBH, 0, 4) = '1012') ");  
		tablename.append(" AND FZ.JSFS = '0005'  and zb.pzzt in ('01','02','99') "); 
		tablename.append(" )fk ");
		String dczt=Validate.isNullToDefaultString(this.getPageData().getString("dczt"),"0");
		String pzzt=Validate.isNullToDefaultString(this.getPageData().getString("pzzt"),"0");
		String zfzt=Validate.isNullToDefaultString(this.getPageData().getString("zfzt"),"0");
		if("0".equals(dczt)){
//			tablename.append(" and fk.dczt = '0'");
			strWhere +=" and fk.dczt = '0' "; 
		}else{
//			tablename.append(" and fk.dczt = '1'");
			strWhere +=" and fk.dczt = '1' ";
		}
		if("0".equals(zfzt)){
//			tablename.append(" and fk.pzzt = '0'");
			strWhere +=" and fk.zfzt = '0' ";
		}else{
//			tablename.append(" and fk.pzzt = '1'");
			strWhere +=" and fk.zfzt = '1' ";
		}
		if(!"0".equals(pzzt)){
//			tablename.append(" and fk.zfzt = '00'");
			strWhere +=" and fk.pzzt != '00' ";
		}else{
//			tablename.append(" and fk.zfzt != '00' ");
			strWhere +=" and fk.pzzt = '00' ";
		}
		String pzh1 = pd.getString("pzh11");
		String pzh2 = pd.getString("pzh22");
		String kssj = pd.getString("pzrq11");
		String jssj = pd.getString("pzrq22");
		
		
		if(Validate.noNull(pzh1) && Validate.noNull(pzh2) ){
			strWhere +=" and  to_number(fk.pzbh) >= to_number('"+pzh1+"') and to_number(fk.pzbh) <= to_number('"+pzh2+"')";
		}else if(Validate.noNull(pzh1) && Validate.isNull(pzh2)  ){
			strWhere +=" and to_number(fk.pzbh) >= to_number('"+pzh1+"')";
		}else if(Validate.isNull(pzh1) && Validate.noNull(pzh2)  ){
			strWhere +=" and to_number(fk.pzbh) <= to_number('"+pzh2+"')";
		}
		
		if(Validate.noNull(kssj) && Validate.noNull(jssj) ){
			strWhere +=" and fk.pzrq >= '"+kssj+"' and fk.pzrq <= '"+jssj+"' ";
		}else if(Validate.noNull(kssj) && Validate.isNull(jssj)  ){
			strWhere +=" and fk.pzrq >= '"+kssj+"' ";
		}else if(Validate.isNull(kssj) && Validate.noNull(jssj)  ){
			strWhere +=" and fk.pzrq <= '"+jssj+"' ) ";
		}
//		strWhere+=" order by fk.pzbh";
//		if(Validate.noNull(kssj)){
//			tablename.append(" and fk.pzrq >= to_date('"+kssj+"','yyyy-mm-dd') ");
//		}
//		if(Validate.noNull(jssj)){
//			tablename.append(" and fk.pzrq <= to_date('"+jssj+"','yyyy-mm-dd') ");
//		}
		
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(strWhere); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
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
		StringBuffer tablename = new StringBuffer();
		tablename.append("select * from  ( SELECT nvl(t.dczt,'0') AS dczt,nvl(t.status,'0') AS zfzt,t.guid,zb.pzzt,zb.pzlxmc,zb.pzbh,to_char(zb.pzrq,'yyyy-mm-dd') as pzrq ,mx.zy,t.xm,t.yhmc,t.yhzh,to_char(nvl(t.je,'0'), 'FM999999999999990.00') je,t.req_time AS sksj, ");
		tablename.append("(case nvl(t.dczt, '0') when '1' then '已导出' else '未导出'end )dcztmc,");
		tablename.append("(case zb.pzzt when '00' then '已保存'  else  '已复核' end) pzztmc,");
		tablename.append(" '' skje, "); 
		tablename.append("(case nvl(t.status, '0') when '1' then '已支付' else '未支付'end )zfztmc ");
		tablename.append(" FROM CW_PZLRYHMX T ");
		tablename.append(" LEFT JOIN CW_PZLRMXB MX ON (MXID = MX.GUID )" );
		tablename.append(" LEFT JOIN CW_FZLRB FZ ON (FZ.KMBH = MX.GUID) " ); 
		tablename.append(" LEFT JOIN cw_pzlrzb zb ON t.zbid=zb.guid " ); 
		tablename.append(" WHERE 1=1" );
		tablename.append(" AND (SUBSTR(MX.KMBH, 0, 4) = '1011' OR (SUBSTR(MX.KMBH, 0, 4) = '1002' AND  MX.KMBH != '100202') OR SUBSTR(MX.KMBH, 0, 4) = '1012')" ); 
		tablename.append(" AND FZ.JSFS != '0005'" ); 
		tablename.append(" union " ); 
		tablename.append(" SELECT nvl(Fz.dczt,'0') AS dczt,nvl(fz.zfzt,'0') AS zfzt,fz.guid,zb.pzzt,zb.pzlxmc,zb.pzbh,to_char(zb.pzrq,'yyyy-mm-dd') as pzrq ,mx.zy,fz.bz AS xm,'公务卡' AS yhmc,gwkh as yhzh,to_char(nvl(MX.dfje,'0'), 'FM999999999999990.00') je,fz.sksj AS sksj,"); 
		tablename.append(" (case nvl(Fz.dczt, '0') when '1' then '已导出' else '未导出'end )dcztmc," ); 
		tablename.append(" (case zb.pzzt when '00' then '已保存'  else  '已复核' end) pzztmc," ); 
		tablename.append(" to_char(fz.sjskje, 'FM999999999999990.00') as skje,"); 
		tablename.append(" (case nvl(Fz.zfzt, '0') when '1' then '已支付' else '未支付'end )zfztmc" ); 
		tablename.append(" from cw_fzlrb Fz  " ); 
		tablename.append(" LEFT JOIN CW_PZLRMXB MX ON (fz.kmbh = MX.GUID )  " );  
		tablename.append(" LEFT JOIN cw_pzlrzb zb ON MX.pzbh=zb.guid   WHERE 1=1" );  
		tablename.append(" AND (SUBSTR(MX.KMBH, 0, 4) = '1011' OR (SUBSTR(MX.KMBH, 0, 4) = '1002' AND  MX.KMBH != '100202') OR SUBSTR(MX.KMBH, 0, 4) = '1012') ");  
		tablename.append(" AND FZ.JSFS = '0005'  and zb.pzzt in ('01','02','99') "); 
		tablename.append(" )fk where 1=1");
		String dczt=Validate.isNullToDefaultString(this.getPageData().getString("dczt"),"0");
		String pzzt=Validate.isNullToDefaultString(this.getPageData().getString("pzzt"),"0");
		String zfzt=Validate.isNullToDefaultString(this.getPageData().getString("zfzt"),"0");
		if("0".equals(dczt)){
//			tablename.append(" and fk.dczt = '0'");
			tablename.append(" and fk.dczt = '0' ");
		}else{
//			tablename.append(" and fk.dczt = '1'");
			tablename.append(" and fk.dczt = '1' ");
		}
		if("0".equals(zfzt)){
//			tablename.append(" and fk.pzzt = '0'");
			tablename.append(" and fk.zfzt = '0' ");
		}else{
//			tablename.append(" and fk.pzzt = '1'");
			tablename.append(" and fk.zfzt = '1' ");
		}
		if(!"0".equals(pzzt)){
//			tablename.append(" and fk.zfzt = '00'");
			tablename.append(" and fk.pzzt != '00' ");
		}else{
//			tablename.append(" and fk.zfzt != '00' ");
			tablename.append(" and fk.pzzt = '00' " );
		}
		String pzh1 = pd.getString("pzh11");
		String pzh2 = pd.getString("pzh22");
		String kssj = pd.getString("pzrq11");
		String jssj = pd.getString("pzrq22");
		
//		System.err.println("_________"+pzh1+"______"+pzh2+"_______"+kssj+"_________"+jssj);
		
		if(Validate.noNull(pzh1) && Validate.noNull(pzh2) ){
			tablename.append(" and  to_number(fk.pzh) >= to_number('"+pzh1+"') and fk.pzh <= to_number('"+pzh2+"') ");
		}else if(Validate.noNull(pzh1) && Validate.isNull(pzh2)  ){
			tablename.append("and to_number(fk.pzh) >= to_number('"+pzh1+"')");
		}else if(Validate.isNull(pzh1) && Validate.noNull(pzh2)  ){
			tablename.append("and to_number(fk.pzh) <= to_number('"+pzh2+"')");
		}
		
		if(Validate.noNull(kssj) && Validate.noNull(jssj) ){
			tablename.append("and fk.pzrq >= '"+kssj+"' and fk.pzrq <= '"+jssj+"' ");
		}else if(Validate.noNull(pzh1) && Validate.isNull(pzh2)  ){
			tablename.append("and fk.pzrq >= '"+kssj+"' ");
		}else if(Validate.isNull(pzh1) && Validate.noNull(pzh2)  ){
			tablename.append("and fk.pzrq <= '"+jssj+"' ");
		}
		String guid = pd.getString("guid");
		if(Validate.noNull(guid)){
			tablename.append(" and fk.guid in ('"+guid+"') ");
		}
//		tablename.append(ToSqlUtil.jsonToSql(searchValue));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
//		m.setName("DCZTMC");
//		m.setShowname("导出状态");
//		mlist.add(m);
//		m = null;
		
		m = new M_largedata();
		m.setName("ZFZTMC");
		m.setShowname("支付状态");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("PZZTMC");
		m.setShowname("凭证状态");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("PZLXMC");
		m.setShowname("凭证类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("PZBH");
		m.setShowname("凭证号");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("PZRQ");
		m.setShowname("凭证日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ZY");
		m.setShowname("凭证摘要");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XM");
		m.setShowname("姓名");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("YHMC");
		m.setShowname("银行名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("YHZH");
		m.setShowname("卡号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("JE");
		m.setShowname("金额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SKJE");
		m.setShowname("刷卡金额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SKSJ");
		m.setShowname("刷卡时间");
		mlist.add(m);
		m = null;
		
		String n = "";
		if("0".equals(zfzt)){
			//先更新状态
				//导出方法
				pageService.ExpExcel(tablename.toString(),realfile,filedisplay,mlist);
				int a = pzcxsService.updatesomef(guid);
				int b = pzcxsService.updatesomefk(guid);
				n = "{\"url\":\"excel\\\\"+file+".xls\"}";
				
				
		}else{
			//导出方法
			pageService.ExpExcel(tablename.toString(),realfile,filedisplay,mlist);
			n = "{\"url\":\"excel\\\\"+file+".xls\"}";
		}
		return n;
	}

}
