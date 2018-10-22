package com.googosoft.controller.fzgn.jcsz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.StateManager;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.LzryzcyjService;
import com.googosoft.util.PageData;
import com.googosoft.util.PageListUtil;
import com.googosoft.util.Validate;

/**
 * 离职人员资产移交controller层
 * @author RC 2017-08-31
 */
@Controller
@RequestMapping(value="/lzry")
public class LzryzcyjController extends BaseController{
	
	@Resource(name="lzryzcyjService")
	private LzryzcyjService lzryzcyjService;
	
	@Resource(name="pageService")
	private PageService pageService;

	/**
	 * 跳转到离职人员资产移交列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goLzryListPage")
	public ModelAndView goLzryListPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fzgn/jcsz/lzryzcyj/lzryzcyj_list");
		return mv;
	}
	
	/**
	 * 获取离职人员资产移交列表数据
	 * @throws Exception 
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception{
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("r.rybh,r.rygh,r.xm,r.dwbh,r.lxdh,");
		sqltext.append("(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh=r.dwbh) szdwmc,");
		sqltext.append("(select sum(nvl(sykh,1)) from zc_zjb where syr = r.rybh) zczs,");
		sqltext.append("(select decode(sum(nvl(zzj,0)),0,'0.00',(to_char(sum(nvl(zzj,0)),'fm99999999999990.00'))) from zc_zjb where syr=r.rybh) zzj ");
		pageList.setSqlText(sqltext.toString());
		//表名
		pageList.setTableName("gx_sys_ryb r");
		//主键
		pageList.setKeyId("rybh");
		//设置WHERE条件
		pageList.setStrWhere(" and rybh in (select syr from zc_zjb group by syr) " + pageService.getDwqxWhereSql(LUser.getRybh(), "r.dwbh", "", true));
		PageData pd = this.getPageData();
		//设置合计值字段名
		pageList.setHj1("");
		pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 跳转到待移交资产页面 
	 */
	@RequestMapping(value = "/goZcPage", produces = "text/html;charset=UTF-8")
	public ModelAndView goZcPage() {
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		String flag = pd.getString("flag");
		ModelAndView mv = this.getModelAndView();
		mv.addObject("rybh", rybh);
		mv.addObject("flag", flag);
		mv.setViewName("fzgn/jcsz/lzryzcyj/yjzc_select");
		return mv;
	}
	
	/**
	 * 名下资产列表数据
	 */
	@RequestMapping(value = "/getZcPageList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getZcPageList() {
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("t.yqbh,t.flh,t.flmc,t.yqmc,t.yqmc yq,decode(t.dj,0,'0.00',to_char(t.dj,'fm9999999999999990.00')) dj,");
		sqltext.append("t.jldw,decode(t.zzj,0,'0.00',to_char(t.zzj,'fm9999999999999990.00')) zzj,to_char(t.dzrrq, 'yyyy-mm-dd') dzrrq,");
		sqltext.append("(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh = t.sydw) sydw,");
		sqltext.append("(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = t.syr) syr,t.bzxx cfdd,");
		sqltext.append("(select mc from gx_sys_dmb where zl = '" + Constant.XZ + "' and dm = t.xz) xz,nvl(t.sykh,1) sykh,");
		sqltext.append("(select '('||d.ddh||')'||d.mc from zc_sys_ddb d where d.ddbh = t.bzxx) bzxx,");
		sqltext.append("t.sydw sydwbh,t.syr syrbh,to_char(t.rzrq, 'yyyy-mm-dd') rzrq,nvl(bdzt,0) bddm,");
		sqltext.append("(select d.mc from zc_sys_ddb d where d.ddbh = t.bzxx) cfddmc,");
		sqltext.append("(select d.mc from gx_sys_dwb d where d.dwbh = t.sydw) sydwmc,");
		sqltext.append(ToSqlUtil.getSqlByZjbXh("t", "xh") + "," + ToSqlUtil.getSqlByZjbGg("t", "gg") + ",");
		sqltext.append("(select dwbh from gx_sys_dwb where dwbh in (select dwbh from gx_sys_dwb where sjdw = '" + SystemSet.Dwbh() + "') start with dwbh = t.sydw connect by prior sjdw = dwbh) ejdw,");
		sqltext.append(new StateManager().getBdztSql("bdzt"));
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.yqbh");// 主键
		pageList.setStrWhere(" and t.syr = '" + rybh + "' ");// where条件
		pageList.setTableName("zc_zjb t");
		pageList.setHj1(" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) ljzjhj," +
				" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj,"+ 
				" decode(nvl(sum(sykh),0),0,'0.00',(to_char(round(sum(sykh),2),'fm99999999999990'))) slhj," +
				" decode(nvl(sum(sykh),0),0,'0.00',(to_char(round(sum(sykh),2),'fm99999999999990'))) slAllhj");
		
		PageListUtil.setPgxxList(pd,pageList);
		List list = pageService.getPageList(pageList);
		List zjlist = pageService.getPageZjList(pageList);//总计
		List hjlist = pageService.getPageHjList(pageList);//当前页
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",list.size()+"",list.size()+"",gson.toJson(list),gson.toJson(zjlist),gson.toJson(hjlist));
		return pageInfo.toJson();
	}
	
	/**
	 * 保存资产移交信息
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave()
	{
		PageData pd = this.getPageData();
		String lzry = pd.getString("rybh");
		String jsr = pd.getString("jsr");
		String yqbhs = pd.getString("yqbhs");
		if(lzryzcyjService.doSave(lzry, jsr, yqbhs))
		{
			return "{success:'true',msg:'资产移交成功！'}";
		}
		else
		{
			return "{success:'false',msg:'资产移交失败！'}";
		}
	}
}
