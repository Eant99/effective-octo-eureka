package com.googosoft.controller.xmgl.wdxm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/wdxm")
public class wdxmController extends BaseController{
	@Resource(name="pageService")
	PageService pageService;
	
	/**
	 * 跳转到项目经费列表页面
	 * @return
	 */
	@RequestMapping(value = "/getWdxm")
	public ModelAndView getWdxm() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("xmgl/wdxm/wdxm_list");
		return mv;
	}
	/**
	 * 获得项目经费列表页面数据
	 * @return
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session){
		String sszt = Constant.getztid(session);
		PageList pageList = new PageList();
		//设置查询字段名
		String sqr = LUser.getGuid();
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		sqltext.append(" guid,xmbh,xmmc,bmmc,xmdlmc,xmlbmc,xmfzr,ysje,syje,"
				+ "to_char((nvl(zcje1, 0) + nvl(zcje2, 0)+nvl(zcje3, 0) +nvl(zcje4, 0)),'FM9999999999999999999999999990.00')as zcje,kyje,"
				+ "to_char((nvl(dshje1, 0) + nvl(dshje2, 0) + nvl(dshje3, 0) + nvl(dshje4, 0)),'FM99999999999999999999999990.00')as dshje ");
		tablename.append(" (select distinct guid, xmbh,xmmc,(select '('||RYBH||')'|| xm from gx_sys_ryb ry where ry.rybh=A.fzr) as xmfzr," );
		tablename.append(" to_char(ysje,'FM999999999999999999999999999999990.00') as ysje," );
		tablename.append(" to_char(syje,'FM999999999999999999999999999999990.00') as syje," );
		tablename.append(" (select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.bmbh  ) as bmmc," );
		tablename.append(" (select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=a.xmdl) xmdlmc," );
		tablename.append(" (select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=a.xmlb) xmlbmc," );
		tablename.append(" (select sum(b.BXZJE) from cw_rcbxmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt='8' )as zcje1," );
		tablename.append(" (select sum(b.BXZJE) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt='8' )as zcje2," );
		tablename.append(" (select sum(b.bxje) from  cw_gwjdfbxzb b where (select t.guid from CW_XMJBXXB t where  t.bmbh='101' and ( xmmc='公务接待费' or t.xmbh='1005') ) = a.guid  and b.shzt ='8' ) as zcje3, ");
		tablename.append(" (select sum(b.jkzje) from CW_JKYWB_MXB m left join cw_jkywb b on m.jkid = b.gid where m.jfbh = a.guid and b.shzt ='8') as zcje4, ");
		tablename.append(" (select sum(b.BXZJE) from cw_rcbxmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt !='8' )as dshje1," );
		tablename.append(" (select sum(b.BXZJE) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt !='8' )as dshje2," );
		tablename.append(" (select sum(b.bxje)from  cw_gwjdfbxzb b where (select t.guid from CW_XMJBXXB t where  t.bmbh='101' and ( xmmc='公务接待费' or t.xmbh='1005') ) = a.guid  and b.shzt !='8' ) as dshje3, ");
		tablename.append(" (select sum(b.jkzje) from CW_JKYWB_MXB m left join cw_jkywb b on m.jkid = b.gid where m.jfbh = a.guid and b.shzt !='8' ) as dshje4,to_char((nvl(syje,0) - nvl(FUN_GETYYJE(a.guid),0)),'FM999999999999999999999999999999990.00') as kyje ");
		
		tablename.append(" from cw_xmjbxxb A where a.fzr='"+LUser.getRybh()+"' or(xmsx='01' and bmbh='"+LUser.getDwbh()+"'))K" );
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		PageData pd = this.getPageData();
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
		/**
		 * 审核通过金额查看页面
		 * @return
		 */
		@RequestMapping("/goMxPage")
		public ModelAndView goMxPage(HttpSession session) {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			
			String guid = pd.getString("guid");
			mv.addObject("guid",guid);
			mv.setViewName("xmgl/wdxm/wdxmmx_list");
			return mv;
		}
		/**
		 * 获取审核通过金额查看页面的列表数据
		 * @return
		 */
		@RequestMapping(value="getMxPage",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getMxPage(HttpSession session) {
			String sszt = Constant.getztid(session);
			PageData pd = this.getPageData();
			String guid = pd.getString("guid");
			StringBuffer sqltext = new StringBuffer();//查询字段
			StringBuffer tablename = new StringBuffer();
			PageList pageList = new PageList();
			sqltext.append(" * ");
			//差旅费
			tablename.append(" (select to_char(c.bcbxje, 'FM999999999999.00') as bxzje, xm.guid, xm.xmbh,xm.xmmc,(select '(' || RYBH || ')' || xm" );
			tablename.append(" from gx_sys_ryb ry where ry.rybh = xm.fzr) as xmfzr,b.czrq as bxsj," ); 
			tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.guid = b.sqr) as bxr,bm.mc as bmmc,'差旅报销' as bxlx" ); 
			tablename.append(" from cw_ccsqspb_xm c left join cw_clfbxzb b on c.ccsqid = b.ccywguid" );
			tablename.append(" left join cw_xmjbxxb xm on c.jfbh = xm.guid left join gx_sys_dwb bm on xm.bmbh = bm.dwbh" );
			tablename.append(" where xm.guid = '"+guid+"'and b.shzt ='8' " );
			tablename.append(" UNION all" );
			//日常
			tablename.append(" select to_char(r.bxje, 'FM999999999999.00') as bxzje, xm.guid,xm.xmbh,xm.xmmc," );		
			tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.rybh = xm.fzr) as xmfzr," );
			tablename.append(" to_char(b.czrq, 'yyyy-mm-dd') as bxsj," );
			tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.guid = b.bxr) as bxr,bm.mc as bmmc,'日常报销' as bxlx" );
			tablename.append(" from cw_rcbxmxb r left join cw_rcbxzb b on r.zbid = b.guid" );
			tablename.append(" left join cw_xmjbxxb xm on r.xmguid = xm.guid left join gx_sys_dwb bm on xm.bmbh = bm.dwbh" );
			tablename.append("  where xm.guid = '"+guid+"'  and b.shzt ='8' " );
			tablename.append(" UNION all" );
			//公务接待
			tablename.append(" select to_char(b.bxje, 'FM999999999999.00') as bxzje,xm.guid,xm.xmbh,xm.xmmc, ");
			tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.rybh = xm.fzr) as xmfzr, ");
			tablename.append(" to_char(b.czrq, 'yyyy-mm-dd') as bxsj,(select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.guid = b.bxry) as bxr, ");
			tablename.append("  bm.mc as bmmc,'公务接待报销' as bxlx from  cw_gwjdfbxzb b,cw_xmjbxxb xm left join gx_sys_dwb bm on xm.bmbh = bm.dwbh ");
			tablename.append(" where (select t.guid from CW_XMJBXXB t where t.bmbh = '101' and (xmmc = '公务接待费' or t.xmbh = '1005')) = '"+guid+"'  and b.shzt ='8' ");
			tablename.append(" UNION all" );
			//借款
			tablename.append(" select to_char(b.jkzje, 'FM999999999999.00') as bxzje,xm.guid,xm.xmbh,xm.xmmc, ");
			tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.rybh = xm.fzr) as xmfzr, ");
			tablename.append(" to_char(b.jksj, 'yyyy-mm-dd') as bxsj,(select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.guid = b.jkr) as bxr, ");
			tablename.append("  bm.mc as bmmc,'借款报销' as bxlx from   CW_JKYWB_MXB m left join cw_jkywb b on m.jkid = b.gid ");
			tablename.append(" left join cw_xmjbxxb xm on m.jfbh = xm.guid left join gx_sys_dwb bm on xm.bmbh = bm.dwbh ");
			tablename.append("  where  xm.guid= '"+guid+"' and b.shzt ='8' ");
			
			tablename.append(" ) k");
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
			/**
			 * 待审核通过金额查看页面
			 * @return
			 */
			@RequestMapping("/goDshPage")
			public ModelAndView goDshPage(HttpSession session) {
				ModelAndView mv = this.getModelAndView();
				PageData pd = this.getPageData();
				
				String guid = pd.getString("guid");
				mv.addObject("guid",guid);
				mv.setViewName("xmgl/wdxm/wdxmdsh_list");
				return mv;
			}
			/**
			 * 获得待审核通过金额查看页面列表数据
			 * @return
			 */
			@RequestMapping(value="getDshPage",produces="text/html;charset=UTF-8")
			@ResponseBody
			public Object getDshPage(HttpSession session) {
				String sszt = Constant.getztid(session);
				PageData pd = this.getPageData();
				String guid = pd.getString("guid");
				String sqr = LUser.getGuid();
				StringBuffer sqltext = new StringBuffer();//查询字段
				StringBuffer tablename = new StringBuffer();
				PageList pageList = new PageList();
				sqltext.append(" * ");
				//差旅费
				tablename.append(" (select to_char(c.bcbxje, 'FM999999999999.00') as bxzje, xm.guid, xm.xmbh,xm.xmmc,(select '(' || RYBH || ')' || xm" );
				tablename.append(" from gx_sys_ryb ry where ry.rybh = xm.fzr) as xmfzr,b.czrq as bxsj," ); 
				tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.guid = b.sqr) as bxr,bm.mc as bmmc,'差旅报销' as bxlx" ); 
				tablename.append(" from cw_ccsqspb_xm c left join cw_clfbxzb b on c.ccsqid = b.ccywguid" );
				tablename.append(" left join cw_xmjbxxb xm on c.jfbh = xm.guid left join gx_sys_dwb bm on xm.bmbh = bm.dwbh" );
				tablename.append(" where xm.guid = '"+guid+"'and b.shzt !='8' " );
				tablename.append(" UNION all" );
				//日常
				tablename.append(" select to_char(r.bxje, 'FM999999999999.00') as bxzje, xm.guid,xm.xmbh,xm.xmmc," );		
				tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.rybh = xm.fzr) as xmfzr," );
				tablename.append(" to_char(b.czrq, 'yyyy-mm-dd') as bxsj," );
				tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.guid = b.bxr) as bxr,bm.mc as bmmc,'日常报销' as bxlx" );
				tablename.append(" from cw_rcbxmxb r left join cw_rcbxzb b on r.zbid = b.guid" );
				tablename.append(" left join cw_xmjbxxb xm on r.xmguid = xm.guid left join gx_sys_dwb bm on xm.bmbh = bm.dwbh" );
				tablename.append("  where xm.guid = '"+guid+"'  and b.shzt !='8' " );
				tablename.append(" UNION all" );
				//公务接待
				tablename.append(" select to_char(b.bxje, 'FM999999999999.00') as bxzje,xm.guid,xm.xmbh,xm.xmmc, ");
				tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.rybh = xm.fzr) as xmfzr, ");
				tablename.append(" to_char(b.czrq, 'yyyy-mm-dd') as bxsj,(select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.guid = b.bxry) as bxr, ");
				tablename.append("  bm.mc as bmmc,'公务接待报销' as bxlx from  cw_gwjdfbxzb b,cw_xmjbxxb xm left join gx_sys_dwb bm on xm.bmbh = bm.dwbh ");
				tablename.append(" where (select t.guid from CW_XMJBXXB t where t.bmbh = '101' and (xmmc = '公务接待费' or t.xmbh = '1005')) = '"+guid+"'  and b.shzt !='8' ");
				tablename.append(" UNION all" );
				//借款
				tablename.append(" select to_char(b.jkzje, 'FM999999999999.00') as bxzje,xm.guid,xm.xmbh,xm.xmmc, ");
				tablename.append(" (select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.rybh = xm.fzr) as xmfzr, ");
				tablename.append(" to_char(b.jksj, 'yyyy-mm-dd') as bxsj,(select '(' || RYBH || ')' || xm from gx_sys_ryb ry where ry.guid = b.jkr) as bxr, ");
				tablename.append("  bm.mc as bmmc,'借款报销' as bxlx from   CW_JKYWB_MXB m left join cw_jkywb b on m.jkid = b.gid ");
				tablename.append(" left join cw_xmjbxxb xm on m.jfbh = xm.guid left join gx_sys_dwb bm on xm.bmbh = bm.dwbh ");
				tablename.append("  where  xm.guid= '"+guid+"' and b.shzt !='8' ");
				
				
				tablename.append(" ) k");
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
