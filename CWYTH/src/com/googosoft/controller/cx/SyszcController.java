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
import com.googosoft.util.Validate;

/**
 * 实验室资产信息查询
 */
@Controller
@RequestMapping(value="/syszc")
public class SyszcController extends BaseController{
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
			mv.setViewName("cx/syszccx/sys_Header");
		}else{
			mv.setViewName("cx/syszccx/mainDwTree");
		}
		mv.addObject("mkbh",mkbh);
		return mv;
	}
	/**
	 * 获取实验室资产信息查询列表页面
	 */
	@RequestMapping(value="/goSysPage")
	public ModelAndView goSysPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String sql = pd.getString("sql");
		mv.addObject("sql",sql);
		mv.addObject("dwmc",dwbService.getDwxx(dwbh));
		List syfxList = dictService.getDict(Constant.SYFX);//使用方向
		List xzList = dictService.getDict(Constant.XZ);//现状
		mv.setViewName("cx/syszccx/syszccx");
		mv.addObject("syfxList", syfxList);
		mv.addObject("xzList", xzList);
		mv.addObject("dwbh",dwbh);
		return mv;
	}
	/**
	 * 获取实验室资产查询列表数据
	 */
	@RequestMapping(value = "/getSysPageList")
	@ResponseBody
	public Object getDwPageList() throws Exception {
		PageData pd = this.getPageData();
		String sydw= pd.getString("treedwbh");
		String sql = pd.getString("sql");
		String userId = LUser.getRybh();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("(case t.ztbz when '55' then '未提交' when '00' then '已提交' when '10' then '管理员退回' when '15' then '管理员通过' when '20' then '归口退回' when '25' then '归口通过' when '91' then '财务退回' when '99' then '财务通过' end) ztbz,");
		sqltext.append("t.yqbh,t.yqbh yqh, t.flmc,t.yqmc,t.flh flh,decode(t.dj,0,'0.00',to_char(t.dj, 'fm9999999999999990.00')) dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj, 'fm9999999999999990.00')) zzj,to_char(t.dzrrq,'yyyy-mm-dd') dzrrq,");
		sqltext.append("(select '('||d.bmh||')'||d.mc from gx_sys_dwb d  where d.dwbh = t.sydw) sydw,");
		sqltext.append("(select '('||r.rygh||')'||r.xm  from gx_sys_ryb r where r.rybh = t.syr) syr,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) xz,");
		sqltext.append("t.sydw sydwbh,t.syr syrbh,");
		sqltext.append("to_char(t.rzrq,'yyyy-mm-dd') rzrq,to_char(t.gzrq,'yyyy-mm-dd') gzrq,");
		sqltext.append("t.xmh,nvl(t.sykh,1) sykh,t.pzh,t.ysdh,t.flh flhh,t.flmc flmcc,t.flgbm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) jldw,");
		sqltext.append("(select mc from gx_sys_dmb where zl= '"+Constant.SYFX+"' and dm = t.syfx) syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) xk,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) xklb,");
		sqltext.append("(select '('||rygh||')'||xm from gx_sys_ryb where rybh=t.gkry) gkry,");
		sqltext.append("(select '('||a.dwbh||')'||a.mc from gx_sys_dwb a where a.dwbh=(select dwbh from gx_sys_ryb where  RYBH = T.GKRY)) gkdw,");
		sqltext.append("to_char(t.gkrq,'yyyy-mm-dd') gkrq,t.gkyj,to_char(t.shrq,'yyyy-mm-dd') shrq,t.shyj,");
		sqltext.append("(select '('||rygh||')'||xm from gx_sys_ryb where rybh=t.shr) shr,t.gkry gkryh,t.gkdw gkdwh,t.shr shrh");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.yqbh");// 主键
		if(!"sql".equals(sql)){
			pageList.setStrWhere(sql);
		}
		//点击左侧树的where条件
		if(Validate.noNull(sydw)){
			pageList.setStrWhere(pageList.getStrWhere()+" and t.sydw in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + sydw + "' and sysbz='0') and t.ztbz = '"+StateManager.SHWC+"' and t.xz not in (select dm from gx_sys_dmb where zl='" + Constant.HXZ + "') ");
		}else{
			//当前登录人管理单位权限并且是实验室的
			pageList.setStrWhere(" and t.sydw in (select dwbh from gx_sys_dwb where sysbz = '0' " + pageService.getQxsql(userId, "T.sydw", "D")+") and t.ztbz = '"+StateManager.SHWC+"' and t.xz not in (select dm from gx_sys_dmb where zl='" + Constant.HXZ + "') ");
		}
		pageList.setTableName("zc_zjb t");
		pageList.setHj1("" +
				" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj,"+ 
				" decode(nvl(sum(sykh),0),0,'0.00',(to_char(round(sum(sykh),2),'fm99999999999990'))) slhj," +
				" decode(nvl(sum(dj),0),0,'0.00',(to_char(round(sum(DJ),2),'fm99999999999990.00'))) djs");
//		StringBuffer groupSql = new StringBuffer();
//		groupSql.append("select count(ztbz) as counts,case ztbz when '55' then 'wtj' when '00' then 'ytj' when '10' then 'glyth' when '15' then 'glytg' when '20' then 'gkth' when '25' then 'gktg' when '99' then 'cwtg' when '91' then 'cwth' end ztbzmc from zc_zjb T");
//		groupSql.append(" where 1=1 "+pageList.getStrWhere()+" group by ztbz ");
		pageList = pageService.findPageList(pd,pageList);//引用传递
		List zjlist = pageService.getPageZjList(pageList);//总计
		List hjlist = pageService.getPageHjList(pageList);//当前页
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()),gson.toJson(pageList.getGroupList()),gson.toJson(zjlist),gson.toJson(hjlist));
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
		String filedisplay = pd.getString("xlsname");
		
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
//		m.setName("ZTBZ");
//		m.setShowname("审核状态");
//		mlist.add(m);
//		m = null;
		
		m = new M_largedata();
		m.setName("YQBH");
		m.setShowname("资产编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("YQMC");
		m.setShowname("资产名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("FLH");
		m.setShowname("分类号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("FLMC");
		m.setShowname("分类名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XMH");
		m.setShowname("项目号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setColstyle("double");
		m.setName("DJ");
		m.setShowname("单价");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setColstyle("double");
		m.setName("ZZJ");
		m.setShowname("总价");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setName("SYKH");
		m.setShowname("套（件）数");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("JLDW");
		m.setShowname("计量单位");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SYR");
		m.setShowname("使用人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SYFX");
		m.setShowname("使用方向");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SYDW");
		m.setShowname("使用管理/部门");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XZ");
		m.setShowname("现状");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("JFKM");
		m.setShowname("经费来源");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ZCLY");
		m.setShowname("资产来源");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("GZRQ");
		m.setShowname("购置日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("RZRQ");
		m.setShowname("入账日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("DZRRQ");
		m.setShowname("调转入日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("PZH");
		m.setShowname("凭证号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("YSDH");
		m.setShowname("验收单号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("JZLX");
		m.setShowname("记账类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XK");
		m.setShowname("学科");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XKLB");
		m.setShowname("学科类别");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("GKRY");
		m.setShowname("归口审核人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("GKDW");
		m.setShowname("归口审核单位");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("GKRQ");
		m.setShowname("归口审核日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("GKYJ");
		m.setShowname("归口审核意见");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SHR");
		m.setShowname("财务审核人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SHRQ");
		m.setShowname("财务审核日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SHYJ");
		m.setShowname("财务审核意见");
		mlist.add(m);
		m = null;
		
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("(case t.ztbz when '55' then '未提交' when '00' then '已提交' when '10' then '管理员退回' when '15' then '管理员通过' when '20' then '归口退回' when '25' then '归口通过' when '91' then '财务退回' when '99' then '财务通过' end) ztbz,");
		sqltext.append("t.yqbh,t.yqmc,t.flh,t.flmc,decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) dj,");
		sqltext.append("decode(t.zzj,0,'0.00',to_char(t.zzj, 'fm9999999999999990.00')) zzj, to_char(t.dzrrq, 'yyyy-mm-dd') dzrrq,");
		sqltext.append("(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh = t.sydw) sydw,");
		sqltext.append("(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = t.syr) syr,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) xz,");
		sqltext.append("t.sydw sydwbh,t.syr syrbh,");
		sqltext.append("to_char(t.rzrq,'yyyy-mm-dd') rzrq,to_char(t.gzrq,'yyyy-mm-dd') gzrq,");
		sqltext.append("t.xmh,t.sykh,t.pzh,t.ysdh,t.flh flhh,t.flmc flmcc,t.flgbm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=t.jldw) jldw,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm = t.syfx) syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=t.jfkm) jfkm,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=t.zcly) zcly,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=t.jzlx) jzlx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKML+"' and dm=t.xk) xk,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=t.xklb) xklb,");
		sqltext.append("(select '('||rygh||')'||xm from gx_sys_ryb  where rybh=t.gkry) gkry,");
		sqltext.append("(select '('||a.dwbh||')'||a.mc from gx_sys_dwb a where a.dwbh=t.gkdw) gkdw,");
		sqltext.append("to_char(t.gkrq,'yyyy-mm-dd') gkrq,t.gkyj,to_char(t.shrq,'yyyy-mm-dd') shrq,t.shyj,");
		sqltext.append("(select '('||rygh||')'||xm from gx_sys_ryb where rybh=t.shr) shr,t.gkry gkryh,t.gkdw gkdwh,t.shr shrh");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.yqbh");// 主键
		String sydw= pd.getString("treedwbh");
		String userId = LUser.getRybh();
		//点击左侧树的where条件
		if(Validate.noNull(sydw)){
			pageList.setStrWhere(" and t.sydw in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + sydw + "' and sysbz='0') and t.ztbz = '"+StateManager.SHWC+"' and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else{
			//当前登录人管理单位权限并且是实验室的
			pageList.setStrWhere(" and t.sydw in (select dwbh from gx_sys_dwb where sysbz='0' "+pageService.getQxsql(userId, "T.sydw", "D")+")  AND t.ZTBZ = '"+StateManager.SHWC+"' and t.xz not in (select dm from gx_sys_dmb where zl='41')");
		}
		pageList.setTableName("zc_zjb t");
		
		String searchJson = CommonUtils.jsonToSql(pd.getString("searchJson"));
		if(Validate.noNull(searchJson)){
			pageList.setStrWhere(pageList.getStrWhere()+searchJson+" ");//查询条件语句
		}
		if(Validate.noNull(pd.getString("id"))){
			pageList.setStrWhere(pageList.getStrWhere()+" and t.yqbh in ('" + ToSqlUtil.pointToSql(pd.getString("id")) + "') ");
		}
		pageList = pageService.findPageList(pd,pageList);//引用传递
		//导出方法
		String str = "select "+pageList.getSqlText()+" from "+pageList.getTableName()+" where 1 = 1 " + pageList.getStrWhere()+ " order by T.yqbh";
		pageService.ExpExcel(str,realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
}
