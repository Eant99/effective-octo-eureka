package com.googosoft.controller.cx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.MenuFlag;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.DwbService;
import com.googosoft.service.systemset.cssz.XtbService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.PageListUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/zcfl")
public class ZcflController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="dictService")
	private DictService dictService;
	
	@Resource(name="xtbService")
	private XtbService xtbService;//单例
	
	@Resource(name="dwbService")
	private DwbService dwbService;//单例
	/**
	 * 查询条件页面
	 * @return
	 */
	@RequestMapping(value="/goPage")
	public ModelAndView goPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		Map map = xtbService.getXtcs(); //只需要查询一条数据，不要根据guid 查询
		if("1".equals(map.get("MUTISEARCHFIRST"))){
			mv.setViewName("cx/zccx/czzccx_Header");
		}else{
			if(MenuFlag.ZCCX_ZCFL_BZXX.equals(mkbh)){
				mv.setViewName("cx/zcflcx/mainDdTree");
			}else{
				mv.setViewName("window/commonTree/mainDwTree_czzccx");
			}
		}
		if(MenuFlag.ZCCX_ZCFL_SYDW.equals(mkbh)){
			mv.addObject("pageUrl", "/zcfl/goDwbPage");
		}else if(MenuFlag.ZCCX_ZCFL_BZXX.equals(mkbh)){
			mv.addObject("pageUrl", "/zcfl/goCfddPage");
		}else if(MenuFlag.ZCCX_ZCFL_SYR.equals(mkbh)){
			mv.addObject("pageUrl", "/zcfl/goSyrPage");
		}
		if(MenuFlag.ZCCX_ZCFL_SYDW.equals(mkbh)){
			mv.addObject("treeJson", "/glqxb/qxdwTree");
		}else if(MenuFlag.ZCCX_ZCFL_BZXX.equals(mkbh)){
			mv.addObject("treeJson", "/glqxb/ddTree");
		}else if(MenuFlag.ZCCX_ZCFL_SYR.equals(mkbh)){
			mv.addObject("treeJson", "/glqxb/qxDwRyTree");
		}
		
		mv.addObject("mkbh",mkbh);
		return mv;
	}
	/**
	 * 资产类别查询条件页面
	 * @return
	 */
	@RequestMapping(value="/goZcflPage")
	public ModelAndView goZcflPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		Map map = xtbService.getXtcs(); //只需要查询一条数据，不要根据guid 查询
		if("1".equals(map.get("MUTISEARCHFIRST"))){
			mv.setViewName("cx/zcflcx/zcfl_Header");
		}else{
			mv.setViewName("cx/zcflcx/mainFlTree");
		}
		mv.addObject("mkbh",mkbh);
		return mv;
	}
	/**
	 * 获取地点信息列表页面
	 */
	@RequestMapping(value="/goCfddPage")
	public ModelAndView goCfddPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String ddbh = pd.getString("ddbh");
		String mkbh = pd.getString("mkbh");
		String sql = pd.getString("sql");
		mv.addObject("sql",sql);
		mv.addObject("mkbh",mkbh);
		List syfxList = dictService.getDict(Constant.SYFX);//使用方向
		List xzList = dictService.getDict(Constant.XZ);//现状
		mv.addObject("syfxList", syfxList);
		mv.addObject("xzList", xzList);
		mv.addObject("ddbh",ddbh);
		mv.setViewName("cx/zcflcx/ddcx");
		return mv;
	}
	/**
	 * 存放地点信息
	 */
	@RequestMapping(value = "/getCfddPageList")
	@ResponseBody
	public Object getCfddPageList() throws Exception {
		PageData pd = this.getPageData();
		String ddbh= pd.getString("treeddbh");
		String sql= pd.getString("sql");
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("t.yqbh as bh, t.yqbh as yqbh,t.flh as flh, t.YQMC as YQmc,t.flmc as flmc,t.yqmc as yq,  decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) as dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj, 'fm9999999999999990.00')) as zzj, to_char(t.dzrrq, 'yyyy-mm-dd') as dzrrq,");
		sqltext.append("(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d  where d.dwbh = t.sydw) as sydw,");
		sqltext.append("(select '(' || r.rygh || ')' || r.xm  from gx_sys_ryb r where r.rybh = t.syr) as syr,  t.bzxx as cfdd,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) as xz,nvl(t.sykh,1)sykh,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm=t.syfx) as syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) as jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) as xklb,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) as jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) as zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) as jldw,");
		sqltext.append("(select nvl2(d.ddh, '(' || d.ddh || ')' || d.mc, '')  from zc_sys_ddb d  where d.ddbh = t.bzxx) as bzxx, ");
		sqltext.append(" (CASE t.ZTBZ WHEN '55' THEN '未提交' WHEN '00' THEN '已提交' WHEN '10' THEN '管理员退回' WHEN '15' THEN '管理员通过' WHEN '20' THEN '归口退回' WHEN '25' THEN '归口通过' WHEN '91' THEN '财务退回' WHEN '99' THEN '财务通过' end) as ztbz, ");
		sqltext.append("t.sydw as sydwbh,t.syr as syrbh, ");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) as xk,");
		sqltext.append(" to_char(t.rzrq, 'yyyy-mm-dd') as rzrq,");
		sqltext.append("t.gkyj,to_char(t.gkrq,'yyyy-mm-dd')as gkrq,");
		sqltext.append("to_char(t.shrq,'yyyy-mm-dd')as shrq,t.shyj,");
		sqltext.append("t.ysdh,pzh,to_char(t.gzrq,'yyyy-mm-dd')as gzrq,");
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.gg) end) as gg,");//规格
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.xh) end) as xh,");//型号
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.GKRY) AS GKRY, ");
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=T.GKDW) as GKDW, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.SHR) AS SHR,  ");
		sqltext.append("t.xmh,t.flgbm as flgbm ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.yqbh");// 主键
		String userId = LUser.getRybh();
		if(!"sql".equals(sql)){
			pageList.setStrWhere(sql);
		}
		//where条件
		if(Validate.noNull(ddbh)){
			pageList.setStrWhere(pageList.getStrWhere()+pageService.getQxsql(userId, "T.sydw", "D") +" and T.bzxx in(select ddbh from zc_sys_ddb connect by prior ddbh=sjdd start with ddbh='" + ddbh + "')"+ " AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else{
			pageList.setStrWhere(pageList.getStrWhere()+pageService.getQxsql(userId, "T.sydw", "D") +" AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}
		pageList.setTableName("ZC_ZJB T");
		pageList.setHj1("" +
				" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj,"+ 
				" decode(nvl(sum(sykh),0),0,'0.00',(to_char(round(sum(sykh),2),'fm99999999999990'))) slhj," +
				" decode(nvl(sum(DJ),0),0,'0.00',(to_char(round(sum(DJ),2),'fm99999999999990.00'))) DJS");
//		StringBuffer groupSql = new StringBuffer();
//		groupSql.append("select sum(counts) counts,ztbzmc from (select count(ztbz) counts,case ztbz when '55' then 'wtj' ");
//		groupSql.append("when '00' then 'ytj' when '10' then 'glyth' when '15' then 'glytg' when '20' then 'gkth' ");
//		groupSql.append("when '25' then 'gktg' when '91' then 'cwth' when '99' then 'cwtg'");
//		groupSql.append(" end ztbzmc from zc_zjb k where 1=1 "+pageList.getStrWhere()+" group by ztbz) group by ztbzmc ");
//		pageList.setGroupSql(groupSql.toString());
		pageList = PageListUtil.setPgxxList(pd, pageList);
		List list = pageService.getPageList(pageList);
		List zjlist = pageService.getPageZjList(pageList);//总计
		List hjlist = pageService.getPageHjList(pageList);//当前页
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getRecordCnt(),pageList.getRecordCnt(),gson.toJson(list),gson.toJson(pageList.getGroupList()),gson.toJson(zjlist),gson.toJson(hjlist));
		return pageInfo.toJson();
	}
	
	/**
	 *  导出存放地点数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doExp", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String doExp(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";

		List<M_largedata> mlist = new ArrayList<M_largedata>();
//		M_largedata m0 = new M_largedata();
//		m0.setColtype("String");
//		m0.setName("ZTBZ");
//		m0.setShowname("审核状态");
//		mlist.add(m0);
		
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("YQBH");
		m1.setShowname("资产编号");
		mlist.add(m1);
		
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("YQMC");
		m2.setShowname("资产名称");
		mlist.add(m2);
		
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("FLH");
		m3.setShowname("分类号");
		mlist.add(m3);
		
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("FLMC");
		m4.setShowname("分类名称");
		mlist.add(m4);
		
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("XMH");
		m5.setShowname("项目号");
		mlist.add(m5);
		
		M_largedata m6 = new M_largedata();
		m6.setColtype("Number");
		m6.setColstyle("double");
		m6.setName("DJ");
		m6.setShowname("单价");
		mlist.add(m6);
		
		
		M_largedata m7 = new M_largedata();
		m7.setColtype("Number");
		m7.setColstyle("double");
		m7.setName("ZZJ");
		m7.setShowname("总价");
		mlist.add(m7);
		
		
		M_largedata m8 = new M_largedata();
		m8.setColtype("Number");
		m8.setName("SYKH");
		m8.setShowname("套（件）数");
		mlist.add(m8);
		
		
		M_largedata m9 = new M_largedata();
		m9.setColtype("String");
		m9.setName("JLDW");
		m9.setShowname("计量单位");
		mlist.add(m9);
		
		
		M_largedata ma = new M_largedata();
		ma.setColtype("String");
		ma.setName("SYR");
		ma.setShowname("使（领）用人");
		mlist.add(ma);
		
	
		M_largedata mb = new M_largedata();
		mb.setColtype("String");
		mb.setName("SYFX");
		mb.setShowname("使用方向");
		mlist.add(mb);
		
		
		M_largedata mc = new M_largedata();
		mc.setColtype("String");
		mc.setName("SYDW");
		mc.setShowname("使用单位");
		mlist.add(mc);
		
		
		M_largedata mc1 = new M_largedata();
		mc1.setColtype("String");
		mc1.setName("BZXX");
		mc1.setShowname("存放地点");
		mlist.add(mc1);
		
		
		M_largedata md = new M_largedata();
		md.setColtype("String");
		md.setName("XZ");
		md.setShowname("现状");
		mlist.add(md);
		
		
		M_largedata me = new M_largedata();
		me.setColtype("String");
		me.setName("JFKM");
		me.setShowname("经费来源");
		mlist.add(me);
		
		
		M_largedata mf = new M_largedata();
		mf.setColtype("String");
		mf.setName("ZCLY");
		mf.setShowname("资产来源");
		mlist.add(mf);
		
		
		M_largedata mg = new M_largedata();
		mg.setColtype("String");
		mg.setName("GZRQ");
		mg.setShowname("购置日期");
		mlist.add(mg);
		

		M_largedata mh = new M_largedata();
		mh.setColtype("String");
		mh.setName("RZRQ");
		mh.setShowname("入账日期");
		mlist.add(mh);
		

		M_largedata mj = new M_largedata();
		mj.setColtype("String");
		mj.setName("DZRRQ");
		mj.setShowname("调转入日期");
		mlist.add(mj);
		

		M_largedata mk = new M_largedata();
		mk.setColtype("String");
		mk.setName("PZH");
		mk.setShowname("凭证号");
		mlist.add(mk);
		

		M_largedata ml = new M_largedata();
		ml.setColtype("String");
		ml.setName("YSDH");
		ml.setShowname("验收单号");
		mlist.add(ml);
		

		M_largedata mq = new M_largedata();
		mq.setColtype("String");
		mq.setName("JZLX");
		mq.setShowname("记账类型");
		mlist.add(mq);
		

		M_largedata mw = new M_largedata();
		mw.setColtype("String");
		mw.setName("XK");
		mw.setShowname("学科");
		mlist.add(mw);
		

		M_largedata me1 = new M_largedata();
		me1.setColtype("String");
		me1.setName("XKLB");
		me1.setShowname("学科类别");
		mlist.add(me1);
		

		M_largedata mr = new M_largedata();
		mr.setName("SCCJ");
		mr.setShowname("生产厂家");
		mlist.add(mr);
		

		M_largedata mt = new M_largedata();
		mt.setName("GG");
		mt.setShowname("规格");
		mlist.add(mt);
		

		M_largedata my = new M_largedata();
		my.setName("XH");
		my.setShowname("型号");
		mlist.add(my);
		

		String ddbh= pd.getString("treeddbh");
		String sql= pd.getString("sql");
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append(" t.yqbh as bh, t.yqbh as yqbh,t.flh as flh, t.YQMC as YQmc,t.flmc as flmc,t.yqmc as yq,  decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) as dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj, 'fm9999999999999990.00')) as zzj, to_char(t.dzrrq, 'yyyy-mm-dd') as dzrrq,");
		sqltext.append("(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d  where d.dwbh = t.sydw) as sydw,");
		sqltext.append("(select '(' || r.rygh || ')' || r.xm  from gx_sys_ryb r where r.rybh = t.syr) as syr,  t.bzxx as cfdd,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) as xz,nvl(t.sykh,1)sykh,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm=t.syfx) as syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) as jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) as xklb,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) as jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) as zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) as jldw,");
		sqltext.append("(select nvl2(d.ddh, '(' || d.ddh || ')' || d.mc, '')  from zc_sys_ddb d  where d.ddbh = t.bzxx) as bzxx,");
		sqltext.append(" (CASE t.ZTBZ WHEN '55' THEN '未提交' WHEN '00' THEN '已提交' WHEN '10' THEN '管理员退回' WHEN '15' THEN '管理员通过' WHEN '20' THEN '归口退回' WHEN '25' THEN '归口通过' WHEN '91' THEN '财务退回' WHEN '99' THEN '财务通过' end) as ztbz, ");
		sqltext.append("t.sydw as sydwbh,t.syr as syrbh, ");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) as xk,");
		sqltext.append("to_char(t.rzrq, 'yyyy-mm-dd') as rzrq,");
		sqltext.append("t.gkyj,to_char(t.gkrq,'yyyy-mm-dd')as gkrq,");
		sqltext.append("to_char(t.shrq,'yyyy-mm-dd')as shrq,t.shyj,");
		sqltext.append("t.ysdh,pzh,to_char(t.gzrq,'yyyy-mm-dd')as gzrq,");
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.gg) end) as gg,");//规格
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.xh) end) as xh,");//型号
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.GKRY) AS GKRY, ");
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=T.GKDW) as GKDW, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.SHR) AS SHR,  ");
		sqltext.append("t.xmh,t.flgbm as flgbm ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.yqbh");// 主键
		String userId = LUser.getRybh();
		if(!"sql".equals(sql)){
			pageList.setStrWhere(sql);
		}
		//where条件
		if(Validate.noNull(ddbh)){
			pageList.setStrWhere(pageList.getStrWhere()+pageService.getQxsql(userId, "T.sydw", "D") +" and T.bzxx in(select ddbh from zc_sys_ddb connect by prior ddbh=sjdd start with ddbh='" + ddbh + "')"+ " AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else{
			pageList.setStrWhere(pageList.getStrWhere()+pageService.getQxsql(userId, "T.sydw", "D") +" AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}
		pageList.setTableName("ZC_ZJB T");
		pageList.setHj1("");// 合计
		
		
		String searchJson = CommonUtils.jsonToSql(pd.getString("searchJson"));
		if(Validate.noNull(searchJson)){
			pageList.setStrWhere(pageList.getStrWhere()+searchJson+" ");//查询条件语句
		}
		if(Validate.noNull(pd.getString("id"))){
			pageList.setStrWhere(pageList.getStrWhere()+" and yqbh in ('" + ToSqlUtil.pointToSql(pd.getString("id")) + "') ");
		}
		pageList = pageService.findPageList(pd,pageList);//引用传递
		//导出方法
		String str = "select "+pageList.getSqlText()+" from "+pageList.getTableName()+" where 1 = 1 " + pageList.getStrWhere()+ " order by yqbh";
		pageService.ExpExcel(str,realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	/**
	 * 获取单位信息列表页面
	 */
	@RequestMapping(value="/goDwbPage")
	public ModelAndView goDwbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String mkbh = pd.getString("mkbh");
		String sql = pd.getString("sql");
		mv.addObject("sql",sql);
		mv.addObject("mkbh",mkbh);
		List syfxList = dictService.getDict(Constant.SYFX);//使用方向
		List xzList = dictService.getDict(Constant.XZ);//现状
		mv.addObject("syfxList", syfxList);
		mv.addObject("xzList", xzList);
		mv.addObject("dwbh",dwbh);
		mv.addObject("dwmc",dwbService.getDwxx(dwbh));
		mv.setViewName("cx/zcflcx/sydwcx");
		return mv;
	}
	/**
	 * 获取单位信息数据
	 */
	@RequestMapping(value = "/getDwPageList")
	@ResponseBody
	public Object getDwPageList() throws Exception {
		StateManager sm = new StateManager();
		PageData pd = this.getPageData();
		String sydw= pd.getString("treedwbh"); 
		String sql= pd.getString("sql"); 
		String userId = LUser.getRybh();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("t.yqbh as bh, t.yqbh as yqbh,t.flh as flh, t.YQMC as YQmc,t.flmc as flmc,t.yqmc as yq,  decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) as dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj,'fm9999999999999990.00')) as zzj, to_char(t.dzrrq, 'yyyy-mm-dd') as dzrrq,");
		sqltext.append("(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d  where d.dwbh = t.sydw) as sydw,");
		sqltext.append("(select '(' || r.rygh || ')' || r.xm  from gx_sys_ryb r where r.rybh = t.syr) as syr,  t.bzxx as cfdd,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) as xz,nvl(t.sykh,1)sykh,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm=t.syfx) as syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) as jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) as xklb,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) as jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) as zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) as jldw,");
		sqltext.append("(select nvl2(d.ddh, '(' || d.ddh || ')' || d.mc, '')  from zc_sys_ddb d  where d.ddbh = t.bzxx) as bzxx,");
		sqltext.append(" (CASE T.ZTBZ WHEN '55' THEN '未提交' WHEN '00' THEN '已提交' WHEN '10' THEN '管理员退回' WHEN '15' THEN '管理员通过' WHEN '20' THEN '归口退回' WHEN '25' THEN '归口通过' WHEN '91' THEN '财务退回' WHEN '99' THEN '财务通过' end) as ztbz, ");
		sqltext.append("t.sydw as sydwbh,t.syr as syrbh, ");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) as xk,");
		sqltext.append(" to_char(t.rzrq, 'yyyy-mm-dd') as rzrq,T.HTBH,");
		sqltext.append("t.gkyj,to_char(t.gkrq,'yyyy-mm-dd')as gkrq,");
		sqltext.append("to_char(t.shrq,'yyyy-mm-dd')as shrq,t.shyj,");
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.gg) end) as gg,");//规格
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.xh) end) as xh,");//型号
		sqltext.append("t.ysdh,pzh,to_char(t.gzrq,'yyyy-mm-dd')as gzrq,");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.GKRY) AS GKRY, ");
		sqltext.append(" (select '(' || a.dwbh || ')' || a.mc  from GX_SYS_DWB a WHERE a.dwbh = (select dwbh from gx_sys_ryb where  RYBH = T.GKRY)) as GKDW, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.SHR) AS SHR,  ");
		sqltext.append("t.xmh,t.flgbm as flgbm ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("T.yqbh");// 主键
		if(!"sql".equals(sql)){
			pageList.setStrWhere(sql);
		}
		//点击左侧树的where条件
		if(Validate.noNull(sydw)){
			pageList.setStrWhere(pageList.getStrWhere()+" and T.sydw in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + sydw + "')"+ "AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else{
			//当前登录人管理单位权限
			pageList.setStrWhere(pageService.getQxsql(userId, "T.sydw", "D") + "AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')"+pageList.getStrWhere());
		}
		pageList.setTableName("ZC_ZJB T");
		pageList.setHj1(" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj,"+ 
				" decode(nvl(sum(sykh),0),0,'0.00',(to_char(round(sum(sykh),2),'fm99999999999990'))) slhj," +
				" decode(nvl(sum(DJ),0),0,'0.00',(to_char(round(sum(DJ),2),'fm99999999999990.00'))) DJS");
//		StringBuffer groupSql = new StringBuffer();
//		groupSql.append("select sum(counts) counts,ztbzmc from (select count(ztbz) counts,case ztbz when '55' then 'wtj' ");
//		groupSql.append("when '00' then 'ytj' when '10' then 'glyth' when '15' then 'glytg' when '20' then 'gkth' ");
//		groupSql.append("when '25' then 'gktg' when '91' then 'cwth' when '99' then 'cwtg'");
//		groupSql.append(" end ztbzmc from zc_zjb t where 1=1 "+pageList.getStrWhere()+" group by ztbz) group by ztbzmc ");
//		pageList.setGroupSql(groupSql.toString());
		pageList = PageListUtil.setPgxxList(pd, pageList);
		List list = pageService.getPageList(pageList);
		List zjlist = pageService.getPageZjList(pageList);//总计
		List hjlist = pageService.getPageHjList(pageList);//当前页
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getRecordCnt(),pageList.getRecordCnt(),gson.toJson(list),gson.toJson(pageList.getGroupList()),gson.toJson(zjlist),gson.toJson(hjlist));
		return pageInfo.toJson();
	}
	
	/**
	 * 导出资产信息Excel
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
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("SELECT t.yqbh as bh, t.yqbh as yqbh,t.flh as flh, t.YQMC as YQmc,t.flmc as flmc,t.yqmc as yq,  decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) as dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj,'fm9999999999999990.00')) as zzj, to_char(t.dzrrq, 'yyyy-mm-dd') as dzrrq,");
		sqltext.append("(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d  where d.dwbh = t.sydw) as sydw,");
		sqltext.append("(select '(' || r.rygh || ')' || r.xm  from gx_sys_ryb r where r.rybh = t.syr) as syr,  t.bzxx as cfdd,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) as xz,nvl(t.sykh,1)sykh,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm=t.syfx) as syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) as jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) as xklb,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) as jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) as zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) as jldw,");
		sqltext.append("(select nvl2(d.ddh, '(' || d.ddh || ')' || d.mc, '')  from zc_sys_ddb d  where d.ddbh = t.bzxx) as bzxx,");
		sqltext.append(" (CASE T.ZTBZ WHEN '55' THEN '未提交' WHEN '00' THEN '已提交' WHEN '10' THEN '管理员退回' WHEN '15' THEN '管理员通过' WHEN '20' THEN '归口退回' WHEN '25' THEN '归口通过' WHEN '91' THEN '财务退回' WHEN '99' THEN '财务通过' end) as ztbz, ");
		sqltext.append("t.sydw as sydwbh,t.syr as syrbh, ");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) as xk,");
		sqltext.append("to_char(t.rzrq, 'yyyy-mm-dd') as rzrq,T.HTBH,");
		sqltext.append("t.gkyj,to_char(t.gkrq,'yyyy-mm-dd')as gkrq,");
		sqltext.append("to_char(t.shrq,'yyyy-mm-dd')as shrq,t.shyj,");
		sqltext.append("t.ysdh,pzh,to_char(t.gzrq,'yyyy-mm-dd')as gzrq,");
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.gg) end) as gg,");//规格
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.xh) end) as xh,");//型号
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.GKRY) AS GKRY, ");
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=T.GKDW) as GKDW, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.SHR) AS SHR,  ");
		sqltext.append("t.xmh,t.flgbm as flgbm ");
		sqltext.append(" FROM ZC_ZJB T WHERE 1=1 ");
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		String sydw = pd.getString("treedwbh");
		String sql= pd.getString("sql"); 
		String userId = LUser.getRybh();
		if(!"sql".equals(sql)){
			sqltext.append(sql);
		}
		if(Validate.noNull(sydw)){
			sqltext.append(" and T.sydw in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + sydw + "')"+ "AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else{
			//当前登录人管理单位权限
			sqltext.append(pageService.getQxsql(userId, "T.sydw", "D")+ "AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}
		if(Validate.noNull(id)){
			sqltext.append(" AND T.YQBH IN ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" ORDER BY T.YQBH ASC ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
//		M_largedata m1 = new M_largedata();
//		m1.setColtype("String");
//		m1.setName("ZTBZ");
//		m1.setShowname("审核状态");
//		mlist.add(m1);
		M_largedata myqbh = new M_largedata();
		myqbh.setColtype("String");
		myqbh.setName("YQBH");
		myqbh.setShowname("资产编号");
		mlist.add(myqbh);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("YQMC");
		m2.setShowname("资产名称");
		mlist.add(m2);
		M_largedata mflh = new M_largedata();
		mflh.setColtype("String");
		mflh.setName("FLH");
		mflh.setShowname("分类号");
		mlist.add(mflh);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("FLMC");
		m3.setShowname("分类名称");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("XMH");
		m4.setShowname("项目号");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("Number");
		m5.setColstyle("double");
		m5.setName("DJ");
		m5.setShowname("单价");
		mlist.add(m5);
		M_largedata m6 = new M_largedata();
		m6.setColtype("Number");
		m6.setColstyle("double");
		m6.setName("ZZJ");
		m6.setShowname("总价");
		mlist.add(m6);
		M_largedata m7 = new M_largedata();
		m7.setColtype("Number");
		m7.setName("SYKH");
		m7.setShowname("套（件）数");
		mlist.add(m7);
		M_largedata m8 = new M_largedata();
		m8.setColtype("String");
		m8.setName("JLDW");
		m8.setShowname("计量单位");
		mlist.add(m8);
		M_largedata m9 = new M_largedata();
		m9.setColtype("String");
		m9.setName("SYR");
		m9.setShowname("使用人");
		mlist.add(m9);
		M_largedata m10 = new M_largedata();
		m10.setColtype("String");
		m10.setName("SYFX");
		m10.setShowname("使用方向");
		mlist.add(m10);
		M_largedata m11 = new M_largedata();
		m11.setColtype("String");
		m11.setName("SYDW");
		m11.setShowname("使用管理/部门");
		mlist.add(m11);
		M_largedata m12 = new M_largedata();
		m12.setColtype("String");
		m12.setName("BZXX");
		m12.setShowname("存放地点");
		mlist.add(m12);
		M_largedata m13 = new M_largedata();
		m13.setColtype("String");
		m13.setName("XZ");
		m13.setShowname("现状");
		mlist.add(m13);
		M_largedata m14 = new M_largedata();
		m14.setColtype("String");
		m14.setName("SCCJ");
		m14.setShowname("生产厂家");
		mlist.add(m14);
		M_largedata m15 = new M_largedata();
		m15.setColtype("String");
		m15.setName("XH");
		m15.setShowname("型号");
		mlist.add(m15);
		M_largedata m16 = new M_largedata();
		m16.setColtype("String");
		m16.setName("GG");
		m16.setShowname("规格");
		mlist.add(m16);
		M_largedata m17 = new M_largedata();
		m17.setColtype("String");
		m17.setName("JFKM");
		m17.setShowname("经费来源");
		mlist.add(m17);
		M_largedata m18 = new M_largedata();
		m18.setColtype("String");
		m18.setName("ZCLY");
		m18.setShowname("资产来源");
		mlist.add(m18);
		M_largedata m19 = new M_largedata();
		m19.setColtype("String");
		m19.setName("GZRQ");
		m19.setShowname("购置日期");
		mlist.add(m19);
		M_largedata m20 = new M_largedata();
		m20.setColtype("String");
		m20.setName("RZRQ");
		m20.setShowname("入账日期");
		mlist.add(m20);
		M_largedata m21 = new M_largedata();
		m21.setColtype("String");
		m21.setName("DZRRQ");
		m21.setShowname("调转入日期");
		mlist.add(m21);
		M_largedata m22 = new M_largedata();
		m22.setColtype("String");
		m22.setName("HTBH");
		m22.setShowname("合同编号");
		mlist.add(m22);
		M_largedata m23 = new M_largedata();
		m23.setColtype("String");
		m23.setName("PZH");
		m23.setShowname("凭证号");
		mlist.add(m23);
		M_largedata m24 = new M_largedata();
		m24.setColtype("String");
		m24.setName("YSDH");
		m24.setShowname("验收单号");
		mlist.add(m24);
		M_largedata m25 = new M_largedata();
		m25.setColtype("String");
		m25.setName("JZLX");
		m25.setShowname("记账类型");
		mlist.add(m25);
		M_largedata m26 = new M_largedata();
		m26.setColtype("String");
		m26.setName("XK");
		m26.setShowname("学科");
		mlist.add(m26);
		M_largedata m27 = new M_largedata();
		m27.setColtype("String");
		m27.setName("XKLB");
		m27.setShowname("学科类别");
		mlist.add(m27);
		M_largedata m28 = new M_largedata();
		m28.setColtype("String");
		m28.setName("GKRY");
		m28.setShowname("归口审核人");
		mlist.add(m28);
		M_largedata m29 = new M_largedata();
		m29.setColtype("String");
		m29.setName("GKDW");
		m29.setShowname("归口审核单位");
		mlist.add(m29);
		M_largedata m30 = new M_largedata();
		m30.setColtype("String");
		m30.setName("GKRQ");
		m30.setShowname("归口审核日期");
		mlist.add(m30);
		M_largedata m31 = new M_largedata();
		m31.setColtype("String");
		m31.setName("GKYJ");
		m31.setShowname("归口审核意见");
		mlist.add(m31);
		M_largedata m32 = new M_largedata();
		m32.setColtype("String");
		m32.setName("SHR");
		m32.setShowname("财务审核人");
		mlist.add(m32);
		M_largedata m33 = new M_largedata();
		m33.setColtype("String");
		m33.setName("SHRQ");
		m33.setShowname("财务审核日期");
		mlist.add(m33);
		M_largedata m34 = new M_largedata();
		m34.setColtype("String");
		m34.setName("SHYJ");
		m34.setShowname("财务审核意见");
		mlist.add(m34);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 * 获取人员信息列表页面
	 */
	@RequestMapping(value="/goSyrPage")
	public ModelAndView goSyrPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("sql",pd.getString("sql"));
		mv.addObject("mkbh",pd.getString("mkbh"));
		mv.addObject("rybh",pd.getString("rybh"));
		mv.addObject("dwbh",pd.getString("dwbh"));
		mv.addObject("xzList", dictService.getDict(Constant.XZ));
		mv.addObject("syfxList", dictService.getDict(Constant.SYFX));
		mv.setViewName("cx/zcflcx/syrcx");
		return mv;
	}
	/**
	 * 使用人信息数据
	 */
	@RequestMapping(value = "/getSyrPageList")
	@ResponseBody
	public Object getSyrPageList() throws Exception {
		PageData pd = this.getPageData();
		String syr= pd.getString("treedrybh");
		String dwbh= pd.getString("treedwbh");
		String sql= pd.getString("sql");
		String userId = LUser.getRybh();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("t.yqbh as bh, t.yqbh as yqbh,t.flh as flh, t.YQMC as YQmc,t.flmc as flmc,t.yqmc as yq,  decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) as dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj,'fm9999999999999990.00')) as zzj, to_char(t.dzrrq, 'yyyy-mm-dd') dzrrq,");
		sqltext.append("(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d  where d.dwbh = t.sydw) as sydw,");
		sqltext.append("(select '(' || r.rygh || ')' || r.xm  from gx_sys_ryb r where r.rybh = t.syr) as syr, t.bzxx as cfdd,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) as xz,nvl(t.sykh,1)sykh,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm=t.syfx) as syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) as jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) as xklb,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) as jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) as zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) as jldw,");
		sqltext.append("(select nvl2(d.ddh,'('||d.ddh||')'||d.mc,'') from zc_sys_ddb d  where d.ddbh = t.bzxx) bzxx,");
		sqltext.append(" (CASE T.ZTBZ WHEN '55' THEN '未提交' WHEN '00' THEN '已提交' WHEN '10' THEN '管理员退回' WHEN '15' THEN '管理员通过' WHEN '20' THEN '归口退回' WHEN '25' THEN '归口通过' WHEN '91' THEN '财务退回' WHEN '99' THEN '财务通过' end) as ztbz, ");
		sqltext.append("t.sydw as sydwbh,t.syr as syrbh, ");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) as xk,");
		sqltext.append("to_char(t.rzrq, 'yyyy-mm-dd') as rzrq,");
		sqltext.append("t.gkyj,to_char(t.gkrq,'yyyy-mm-dd')as gkrq,");
		sqltext.append("to_char(t.shrq,'yyyy-mm-dd')as shrq,t.shyj,");
		sqltext.append("t.ysdh,pzh,to_char(t.gzrq,'yyyy-mm-dd')as gzrq,htbh,");
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.gg) end) as gg,");//规格
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.xh) end) as xh,");//型号
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.GKRY) AS GKRY, ");
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=T.GKDW) as GKDW, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.SHR) AS SHR,  ");
		sqltext.append("t.xmh,t.flgbm as flgbm ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.yqbh");// 主键
		if(!"sql".equals(sql)){
			pageList.setStrWhere(sql);
		}
		//点击左侧树的where条件
		if(Validate.noNull(syr)){
			pageList.setStrWhere(pageList.getStrWhere()+" and T.syr ='" + syr + "'"+" AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else if (Validate.noNull(dwbh)){
			pageList.setStrWhere(pageList.getStrWhere()+" and T.sydw ='" + dwbh + "'"+" AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else {
			pageList.setStrWhere(pageService.getQxsql(userId, "t.SYDW", "D")+" AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41') "+pageList.getStrWhere());
		}
		pageList.setTableName("ZC_ZJB T");
		pageList.setHj1(" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj,"+ 
				" decode(nvl(sum(sykh),0),0,'0.00',(to_char(round(sum(sykh),2),'fm99999999999990'))) slhj," +
				" decode(nvl(sum(DJ),0),0,'0.00',(to_char(round(sum(DJ),2),'fm99999999999990.00'))) DJS");
//		StringBuffer groupSql = new StringBuffer();
//		groupSql.append("select sum(counts) counts,ztbzmc from (select count(ztbz) counts,case ztbz when '55' then 'wtj' ");
//		groupSql.append("when '00' then 'ytj' when '10' then 'glyth' when '15' then 'glytg' when '20' then 'gkth' ");
//		groupSql.append("when '25' then 'gktg' when '91' then 'cwth' when '99' then 'cwtg'");
//		groupSql.append(" end ztbzmc from zc_zjb k where 1=1 "+pageList.getStrWhere()+" group by ztbz) group by ztbzmc ");
//		pageList.setGroupSql(groupSql.toString());
		pageList = PageListUtil.setPgxxList(pd, pageList);
		List list = pageService.getPageList(pageList);
		List zjlist = pageService.getPageZjList(pageList);//总计
		List hjlist = pageService.getPageHjList(pageList);//当前页
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getRecordCnt(),pageList.getRecordCnt(),gson.toJson(list),gson.toJson(pageList.getGroupList()),gson.toJson(zjlist),gson.toJson(hjlist));
		return pageInfo.toJson();
	}
	/**
	 * 导出使用人资产信息Excel
	 */
	@RequestMapping(value="/doExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object doExcel(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String syr= pd.getString("treedrybh");
		String dwbh= pd.getString("treedwbh");
		String sql= pd.getString("sql");
		String userId = LUser.getRybh();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("t.yqbh as bh, t.yqbh as yqbh,t.flh as flh, t.YQMC as YQmc,t.flmc as flmc,t.yqmc as yq,  decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) as dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj,'fm9999999999999990.00')) as zzj, to_char(t.dzrrq, 'yyyy-mm-dd') dzrrq,");
		sqltext.append("(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d  where d.dwbh = t.sydw) as sydw,");
		sqltext.append("(select '(' || r.rygh || ')' || r.xm  from gx_sys_ryb r where r.rybh = t.syr) as syr, t.bzxx as cfdd,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) as xz,nvl(t.sykh,1)sykh,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm=t.syfx) as syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) as jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) as xklb,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) as jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) as zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) as jldw,");
		sqltext.append("(select nvl2(d.ddh,'('||d.ddh||')'||d.mc,'') from zc_sys_ddb d  where d.ddbh = t.bzxx) bzxx,");
		sqltext.append(" (CASE T.ZTBZ WHEN '55' THEN '未提交' WHEN '00' THEN '已提交' WHEN '10' THEN '管理员退回' WHEN '15' THEN '管理员通过' WHEN '20' THEN '归口退回' WHEN '25' THEN '归口通过' WHEN '91' THEN '财务退回' WHEN '99' THEN '财务通过' end) as ztbz, ");
		sqltext.append("t.sydw as sydwbh,t.syr as syrbh, ");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) as xk,");
		sqltext.append("to_char(t.rzrq, 'yyyy-mm-dd') as rzrq,");
		sqltext.append("t.gkyj,to_char(t.gkrq,'yyyy-mm-dd')as gkrq,");
		sqltext.append("to_char(t.shrq,'yyyy-mm-dd')as shrq,t.shyj,");
		sqltext.append("t.ysdh,pzh,to_char(t.gzrq,'yyyy-mm-dd')as gzrq,htbh,");
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.gg) end) as gg,");//规格
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.xh) end) as xh,");//型号
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.GKRY) AS GKRY, ");
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=T.GKDW) as GKDW, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.SHR) AS SHR,  ");
		sqltext.append("t.xmh,t.flgbm as flgbm ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.yqbh");// 主键
		
		if(!"sql".equals(sql)){
			pageList.setStrWhere(sql);
		}
		//点击左侧树的where条件
		if(Validate.noNull(syr)){
			pageList.setStrWhere(pageList.getStrWhere()+" and T.syr ='" + syr + "'"+" AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else if (Validate.noNull(dwbh)){
			pageList.setStrWhere(pageList.getStrWhere()+" and T.sydw ='" + dwbh + "'"+" AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else {
			pageList.setStrWhere(pageService.getQxsql(userId, "t.SYDW", "D")+" AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41') "+pageList.getStrWhere());
		}
		
		pageList.setTableName("ZC_ZJB T");
		pageList.setHj1("");// 合计
		
		String searchJson = CommonUtils.jsonToSql(pd.getString("searchJson"));
		if(Validate.noNull(searchJson)){
			pageList.setStrWhere(pageList.getStrWhere()+searchJson+" ");//查询条件语句
		}
		if(Validate.noNull(pd.getString("id"))){
			pageList.setStrWhere(pageList.getStrWhere()+" and t.yqbh in ('" + ToSqlUtil.pointToSql(pd.getString("id")) + "') ");
		}
		pageList = pageService.findPageList(pd,pageList);//引用传递
		//导出方法
		String str = "select "+pageList.getSqlText()+" from "+pageList.getTableName()+" where 1 = 1 " + pageList.getStrWhere()+ " order by yqbh";

		List<M_largedata> mlist = new ArrayList<M_largedata>();
//		M_largedata m1 = new M_largedata();
//		m1.setColtype("String");
//		m1.setName("ZTBZ");
//		m1.setShowname("资产状态");
//		mlist.add(m1);
		M_largedata myqbh = new M_largedata();
		myqbh.setColtype("String");
		myqbh.setName("YQBH");
		myqbh.setShowname("资产编号");
		mlist.add(myqbh);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("YQMC");
		m2.setShowname("资产名称");
		mlist.add(m2);
		M_largedata mflh = new M_largedata();
		mflh.setColtype("String");
		mflh.setName("FLH");
		mflh.setShowname("分类号");
		mlist.add(mflh);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("FLMC");
		m3.setShowname("分类名称");
		mlist.add(m3);
		
		M_largedata m9 = new M_largedata();
		m9.setColtype("String");
		m9.setName("SYR");
		m9.setShowname("使用人");
		mlist.add(m9);
		
		M_largedata m11 = new M_largedata();
		m11.setColtype("String");
		m11.setName("SYDW");
		m11.setShowname("使用单位");
		mlist.add(m11);

		M_largedata m10 = new M_largedata();
		m10.setColtype("String");
		m10.setName("SYFX");
		m10.setShowname("使用方向");
		mlist.add(m10);
		
		M_largedata m13 = new M_largedata();
		m13.setColtype("String");
		m13.setName("XZ");
		m13.setShowname("现状");
		mlist.add(m13);
		
		M_largedata m7 = new M_largedata();
		m7.setColtype("Number");
		m7.setName("SYKH");
		m7.setShowname("数量");
		mlist.add(m7);
		
		M_largedata m5 = new M_largedata();
		m5.setColtype("Number");
		m5.setColstyle("double");
		m5.setName("DJ");
		m5.setShowname("单价");
		mlist.add(m5);
		M_largedata m6 = new M_largedata();
		m6.setColtype("Number");
		m6.setColstyle("double");
		m6.setName("ZZJ");
		m6.setShowname("总价");
		mlist.add(m6);
		
		M_largedata m8 = new M_largedata();
		m8.setColtype("String");
		m8.setName("RZRQ");
		m8.setShowname("入账日期");
		mlist.add(m8);
		
		M_largedata m12 = new M_largedata();
		m12.setColtype("String");
		m12.setName("BZXX");
		m12.setShowname("存放地点");
		mlist.add(m12);
		
		M_largedata m14 = new M_largedata();
		m14.setColtype("String");
		m14.setName("YSDH");
		m14.setShowname("验收单号");
		mlist.add(m14);
		M_largedata m15 = new M_largedata();
		m15.setColtype("String");
		m15.setName("PZH");
		m15.setShowname("凭证号");
		mlist.add(m15);
		M_largedata m16 = new M_largedata();
		m16.setColtype("String");
		m16.setName("JZLX");
		m16.setShowname("记账类型");
		mlist.add(m16);
		M_largedata m17 = new M_largedata();
		m17.setColtype("String");
		m17.setName("XK");
		m17.setShowname("学科");
		mlist.add(m17);
		M_largedata m18 = new M_largedata();
		m18.setColtype("String");
		m18.setName("ZCLY");
		m18.setShowname("资产来源");
		mlist.add(m18);
		M_largedata m19 = new M_largedata();
		m19.setColtype("String");
		m19.setName("GZRQ");
		m19.setShowname("购置日期");
		mlist.add(m19);

		M_largedata m22 = new M_largedata();
		m22.setColtype("String");
		m22.setName("HTBH");
		m22.setShowname("合同编号");
		mlist.add(m22);
		M_largedata m23 = new M_largedata();
		m23.setColtype("String");
		m23.setName("GKDW");
		m23.setShowname("归口单位");
		mlist.add(m23);
		M_largedata m24 = new M_largedata();
		m24.setColtype("String");
		m24.setName("GKRY");
		m24.setShowname("归口审核人员");
		mlist.add(m24);
		M_largedata m25 = new M_largedata();
		m25.setColtype("String");
		m25.setName("GKYJ");
		m25.setShowname("归口审核意见");
		mlist.add(m25);
		M_largedata m26 = new M_largedata();
		m26.setColtype("String");
		m26.setName("GKRQ");
		m26.setShowname("归口审核日期");
		mlist.add(m26);
		M_largedata m27 = new M_largedata();
		m27.setColtype("String");
		m27.setName("SHR");
		m27.setShowname("财务审核人");
		mlist.add(m27);
		M_largedata m28 = new M_largedata();
		m28.setColtype("String");
		m28.setName("SHRQ");
		m28.setShowname("财务审核日期");
		mlist.add(m28);
		M_largedata m29 = new M_largedata();
		m29.setColtype("String");
		m29.setName("SHYJ");
		m29.setShowname("财务审核意见");
		mlist.add(m29);
		
		m14 = new M_largedata();
		m14.setColtype("String");
		m14.setName("JLDW");
		m14.setShowname("计量单位");
		mlist.add(m14);
		
		 m15 = new M_largedata();
		m15.setColtype("String");
		m15.setName("XH");
		m15.setShowname("型号");
		mlist.add(m15);
		 m16 = new M_largedata();
		m16.setColtype("String");
		m16.setName("GG");
		m16.setShowname("规格");
		mlist.add(m16);
		m14 = new M_largedata();
		m14.setColtype("String");
		m14.setName("XMH");
		m14.setShowname("项目号");
		mlist.add(m14);

		m14 = new M_largedata();
		m14.setColtype("String");
		m14.setName("SCCJ");
		m14.setShowname("生产厂家");
		mlist.add(m14);
		//导出方法
		pageService.ExpExcel(str,realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 * 资产类别信息列表
	 */
	@RequestMapping(value="/goZclbPage")
	public ModelAndView goZclbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String flh = pd.getString("flh");
		String flzd = pd.getString("flzd");
		String mkbh = pd.getString("mkbh");
		String sql = pd.getString("sql");
		mv.addObject("sql",sql);
		mv.addObject("mkbh",mkbh);
		List syfxList = dictService.getDict(Constant.SYFX);//使用方向
		List xzList = dictService.getDict(Constant.XZ);//现状
		mv.setViewName("cx/zcflcx/zclbcx");
		mv.addObject("syfxList", syfxList);
		mv.addObject("xzList", xzList);
		mv.addObject("flh",flh);
		mv.addObject("flzd",flzd);
		return mv;
	}
	/**
	 * 资产类别信息数据
	 */
	@RequestMapping(value = "/getZclbPageList")
	@ResponseBody
	public Object getZclbPageList() throws Exception {
		PageData pd = this.getPageData();
		String flh= pd.getString("treedflh");
		String flzd= pd.getString("flzd");
		String sql = pd.getString("sql");
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append(" (CASE T.ZTBZ WHEN '55' THEN '未提交' WHEN '00' THEN '已提交' WHEN '10' THEN '管理员退回' WHEN '15' THEN '管理员通过' WHEN '20' THEN '归口退回' WHEN '25' THEN '归口通过' WHEN '91' THEN '财务退回' WHEN '99' THEN '财务通过' end) as ztbz, ");
		sqltext.append("t.yqbh as bh, t.yqbh as yqbh,t.flh as flh, t.YQMC as YQmc,t.flmc as flmc,t.yqmc as yq,  decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) as dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj,'fm9999999999999990.00')) as zzj, to_char(t.dzrrq, 'yyyy-mm-dd') as dzrrq,");
		sqltext.append("(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d  where d.dwbh = t.sydw) as sydw,");
		sqltext.append("(select '(' || r.rygh || ')' || r.xm  from gx_sys_ryb r where r.rybh = t.syr) as syr,  t.bzxx as cfdd,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) as xz,nvl(t.sykh,1)sykh,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm=t.syfx) as syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) as jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) as xklb,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) as xk,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) as jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) as zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) as jldw,");
		sqltext.append("(select nvl2(d.ddh, '(' || d.ddh || ')' || d.mc, '')  from zc_sys_ddb d  where d.ddbh = t.bzxx) as bzxx,");
		sqltext.append("t.sydw as sydwbh,t.syr as syrbh, T.FLMC AS FLMCC,");
		sqltext.append("to_char(t.rzrq, 'yyyy-mm-dd') as rzrq,T.HTBH,");
		sqltext.append("t.gkyj,to_char(t.gkrq,'yyyy-mm-dd')as gkrq,");
		sqltext.append("to_char(t.shrq,'yyyy-mm-dd')as shrq,t.shyj,");
		sqltext.append("t.ysdh,pzh,to_char(t.gzrq,'yyyy-mm-dd')as gzrq,");
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.gg) end) as gg,");//规格
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.xh) end) as xh,");//型号
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.GKRY) AS GKRY, ");
		sqltext.append(" (select '(' || a.dwbh || ')' || a.mc from GX_SYS_DWB a  WHERE a.dwbh = (select dwbh from gx_sys_ryb where  RYBH = T.GKRY)) as GKDW, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.SHR) AS SHR,  ");
		sqltext.append("t.xmh,t.FLGBM AS FLGBM");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.yqbh");// 主键
		String userId = LUser.getRybh();
		//点击左侧树的where条件
		if(!"sql".equals(sql)){
			pageList.setStrWhere(sql);
		}
		if(Validate.noNull(flh)){
			if("flh".equals(flzd)){//按教育部十六大类查
				pageList.setStrWhere(pageList.getStrWhere()+pageService.getQxsql(userId, "t.SYDW", "D")+" and T.flh ='"+flh+"%'  "  + " AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
			}else if("flgbm".equals(flzd)){//按财政六大类查
				pageList.setStrWhere(pageList.getStrWhere()+pageService.getQxsql(userId, "t.SYDW", "D")+" and T.flgbm like '"+flh+"%'  "  + " AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
			}
		}else{
			pageList.setStrWhere(pageList.getStrWhere()+pageService.getQxsql(userId, "t.flh", "F")+pageService.getQxsql(userId, "t.SYDW", "D")+" and T.flh like '"+flh+"%'  "  + " AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}
		pageList.setTableName("ZC_ZJB T");
		pageList.setHj1(" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj,"+ 
				" decode(nvl(sum(sykh),0),0,'0.00',(to_char(round(sum(sykh),2),'fm99999999999990'))) slhj," +
				" decode(nvl(sum(DJ),0),0,'0.00',(to_char(round(sum(DJ),2),'fm99999999999990.00'))) DJS");
//		StringBuffer groupSql = new StringBuffer();
//		groupSql.append("select sum(counts) counts,ztbzmc from (select count(ztbz) counts,case ztbz when '55' then 'wtj' ");
//		groupSql.append("when '00' then 'ytj' when '10' then 'glyth' when '15' then 'glytg' when '20' then 'gkth' ");
//		groupSql.append("when '25' then 'gktg' when '91' then 'cwth' when '99' then 'cwtg'");
//		groupSql.append(" end ztbzmc from zc_zjb k where 1=1 "+pageList.getStrWhere()+" group by ztbz) group by ztbzmc ");
//		pageList.setGroupSql(groupSql.toString());
		pageList = PageListUtil.setPgxxList(pd, pageList);
		List list = pageService.getPageList(pageList);
		List zjlist = pageService.getPageZjList(pageList);//总计
		List hjlist = pageService.getPageHjList(pageList);//当前页
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getRecordCnt(),pageList.getRecordCnt(),gson.toJson(list),gson.toJson(pageList.getGroupList()),gson.toJson(zjlist),gson.toJson(hjlist));
		return pageInfo.toJson();
	}
	
	/**
	 * 导出资产信息Excel
	 */
	@RequestMapping(value="/ExpExcelFl",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcelFl(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" SELECT (CASE T.ZTBZ WHEN '55' THEN '未提交' WHEN '00' THEN '已提交' WHEN '10' THEN '管理员退回' WHEN '15' THEN '管理员通过' WHEN '20' THEN '归口退回' WHEN '25' THEN '归口通过' WHEN '91' THEN '财务退回' WHEN '99' THEN '财务通过' end) as ztbz, ");
		sqltext.append("t.yqbh as bh, t.yqbh as yqbh,t.flh as flh, t.YQMC as YQmc,t.flmc as flmc,t.yqmc as yq,  decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) as dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj,'fm9999999999999990.00')) as zzj, to_char(t.dzrrq, 'yyyy-mm-dd') as dzrrq,");
		sqltext.append("(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d  where d.dwbh = t.sydw) as sydw,");
		sqltext.append("(select '(' || r.rygh || ')' || r.xm  from gx_sys_ryb r where r.rybh = t.syr) as syr,  t.bzxx as cfdd,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) as xz,t.sykh,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm=t.syfx) as syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) as jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) as xklb,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) as xk,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) as jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) as zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) as jldw,");
		sqltext.append("(select nvl2(d.ddh, '(' || d.ddh || ')' || d.mc, '')  from zc_sys_ddb d  where d.ddbh = t.bzxx) as bzxx,");
		sqltext.append("t.sydw as sydwbh,t.syr as syrbh,T.FLMC AS FLMCC,");
		sqltext.append("to_char(t.rzrq, 'yyyy-mm-dd') as rzrq,T.HTBH,");
		sqltext.append("t.gkyj,to_char(t.gkrq,'yyyy-mm-dd')as gkrq,");
		sqltext.append("to_char(t.shrq,'yyyy-mm-dd')as shrq,t.shyj,");
		sqltext.append("t.ysdh,pzh,to_char(t.gzrq,'yyyy-mm-dd')as gzrq,");
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.gg) end) as gg,");//规格
		sqltext.append("(case substr(t.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(t.xh) end) as xh,");//型号
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.GKRY) AS GKRY, ");
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=T.GKDW) as GKDW, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=T.SHR) AS SHR,  ");
		sqltext.append("t.xmh,t.FLGBM AS FLGBM");
		sqltext.append(" FROM ZC_ZJB T WHERE 1=1 ");
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		String flh= pd.getString("treedflh");
		String flzd= pd.getString("flzd");
		String userId = LUser.getRybh();
		if(Validate.noNull(flh)){
			if("flh".equals(flzd)){//按教育部十六大类查
				sqltext.append(pageService.getQxsql(userId, "t.SYDW", "D")+" and T.flh like '"+flh+"%'  "  + " AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
			}else if("flgbm".equals(flzd)){//按财政六大类查
				sqltext.append(pageService.getQxsql(userId, "t.SYDW", "D")+" and T.flgbm like '"+flh+"%'  "  + " AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
			}
		}else{
			sqltext.append(pageService.getQxsql(userId, "t.flh", "F")+pageService.getQxsql(userId, "t.SYDW", "D")+" and T.flh like '"+flh+"%'  "  + " AND t.ZTBZ = '"+StateManager.SHWC+"'  and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}
		if(Validate.noNull(id)){
			sqltext.append(" AND T.YQBH IN ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" ORDER BY T.YQBH ASC ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata myqbh = new M_largedata();
		myqbh.setColtype("String");
		myqbh.setName("YQBH");
		myqbh.setShowname("资产编号");
		mlist.add(myqbh);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("YQMC");
		m2.setShowname("资产名称");
		mlist.add(m2);
		M_largedata mflh = new M_largedata();
		mflh.setColtype("String");
		mflh.setName("FLH");
		mflh.setShowname("分类号");
		mlist.add(mflh);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("FLMC");
		m3.setShowname("分类名称");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("XMH");
		m4.setShowname("项目号");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("Number");
		m5.setColstyle("double");
		m5.setName("DJ");
		m5.setShowname("单价");
		mlist.add(m5);
		M_largedata m6 = new M_largedata();
		m6.setColtype("Number");
		m6.setColstyle("double");
		m6.setName("ZZJ");
		m6.setShowname("总价");
		mlist.add(m6);
		M_largedata m7 = new M_largedata();
		m7.setColtype("Number");
		m7.setName("SYKH");
		m7.setShowname("套（件）数");
		mlist.add(m7);
		M_largedata m8 = new M_largedata();
		m8.setColtype("String");
		m8.setName("JLDW");
		m8.setShowname("计量单位");
		mlist.add(m8);
		M_largedata m9 = new M_largedata();
		m9.setColtype("String");
		m9.setName("SYR");
		m9.setShowname("使用人");
		mlist.add(m9);
		M_largedata m10 = new M_largedata();
		m10.setColtype("String");
		m10.setName("SYFX");
		m10.setShowname("使用方向");
		mlist.add(m10);
		M_largedata m11 = new M_largedata();
		m11.setColtype("String");
		m11.setName("SYDW");
		m11.setShowname("使用管理/部门");
		mlist.add(m11);
		M_largedata m12 = new M_largedata();
		m12.setColtype("String");
		m12.setName("BZXX");
		m12.setShowname("存放地点");
		mlist.add(m12);
		M_largedata m13 = new M_largedata();
		m13.setColtype("String");
		m13.setName("XZ");
		m13.setShowname("现状");
		mlist.add(m13);
		M_largedata m14 = new M_largedata();
		m14.setColtype("String");
		m14.setName("SCCJ");
		m14.setShowname("生产厂家");
		mlist.add(m14);
		M_largedata m15 = new M_largedata();
		m15.setColtype("String");
		m15.setName("XH");
		m15.setShowname("型号");
		mlist.add(m15);
		M_largedata m16 = new M_largedata();
		m16.setColtype("String");
		m16.setName("GG");
		m16.setShowname("规格");
		mlist.add(m16);
		M_largedata m17 = new M_largedata();
		m17.setColtype("String");
		m17.setName("JFKM");
		m17.setShowname("经费来源");
		mlist.add(m17);
		M_largedata m18 = new M_largedata();
		m18.setColtype("String");
		m18.setName("ZCLY");
		m18.setShowname("资产来源");
		mlist.add(m18);
		M_largedata m19 = new M_largedata();
		m19.setColtype("String");
		m19.setName("GZRQ");
		m19.setShowname("购置日期");
		mlist.add(m19);
		M_largedata m20 = new M_largedata();
		m20.setColtype("String");
		m20.setName("RZRQ");
		m20.setShowname("入账日期");
		mlist.add(m20);
		M_largedata m21 = new M_largedata();
		m21.setColtype("String");
		m21.setName("DZRRQ");
		m21.setShowname("调转入日期");
		mlist.add(m21);
		M_largedata m22 = new M_largedata();
		m22.setColtype("String");
		m22.setName("HTBH");
		m22.setShowname("合同编号");
		mlist.add(m22);
		M_largedata m23 = new M_largedata();
		m23.setColtype("String");
		m23.setName("PZH");
		m23.setShowname("凭证号");
		mlist.add(m23);
		M_largedata m24 = new M_largedata();
		m24.setColtype("String");
		m24.setName("YSDH");
		m24.setShowname("验收单号");
		mlist.add(m24);
		M_largedata m25 = new M_largedata();
		m25.setColtype("String");
		m25.setName("JZLX");
		m25.setShowname("记账类型");
		mlist.add(m25);
		M_largedata m26 = new M_largedata();
		m26.setColtype("String");
		m26.setName("XK");
		m26.setShowname("学科");
		mlist.add(m26);
		M_largedata m27 = new M_largedata();
		m27.setColtype("String");
		m27.setName("XKLB");
		m27.setShowname("学科类别");
		mlist.add(m27);
		M_largedata m28 = new M_largedata();
		m28.setColtype("String");
		m28.setName("GKRY");
		m28.setShowname("归口审核人");
		mlist.add(m28);
		M_largedata m29 = new M_largedata();
		m29.setColtype("String");
		m29.setName("GKDW");
		m29.setShowname("归口审核单位");
		mlist.add(m29);
		M_largedata m30 = new M_largedata();
		m30.setColtype("String");
		m30.setName("GKRQ");
		m30.setShowname("归口审核日期");
		mlist.add(m30);
		M_largedata m31 = new M_largedata();
		m31.setColtype("String");
		m31.setName("GKYJ");
		m31.setShowname("归口审核意见");
		mlist.add(m31);
		M_largedata m32 = new M_largedata();
		m32.setColtype("String");
		m32.setName("SHR");
		m32.setShowname("财务审核人");
		mlist.add(m32);
		M_largedata m33 = new M_largedata();
		m33.setColtype("String");
		m33.setName("SHRQ");
		m33.setShowname("财务审核日期");
		mlist.add(m33);
		M_largedata m34 = new M_largedata();
		m34.setColtype("String");
		m34.setName("SHYJ");
		m34.setShowname("财务审核意见");
		mlist.add(m34);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
}
