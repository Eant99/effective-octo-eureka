package com.googosoft.controller.ysgl.ystz;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/czys")
public class CzysController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@RequestMapping(value = "/goCzysPage")
	public ModelAndView goCzysPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh=pd.getString("rybh");
		String bmh=pd.getString("bmh");
		String treesearch = pd.getString("treesearch");
		mv.addObject("isWindow",this.getPageData().getString("isWindow"));
		mv.addObject("pname",this.getPageData().getString("param.pname"));
		mv.addObject("controlId",this.getPageData().getString("controlId"));
		mv.addObject("treesearch",treesearch);
		mv.addObject("rybh",rybh);
		mv.addObject("bmh",bmh);
		mv.setViewName("ysgl/ystz/czys_list");
		return mv;

	}
	/**
     *  qgt
     * @param session
     * @return
     */
	@RequestMapping(value="getXmxxPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getXmxxPageData(HttpSession session) {
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		String bmh = pd.getString("bmh");
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select x.guid,('('||dw.dwbh||')'||dw.mc ) as bmmc, x.xmbh,x.xmdl,dm.mc as xmdlmc,"
				+ " x.xmlb,D.MC as xmlbmc,x.xmmc,"
				+ "x.ysje, x.syje,"
				+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
//						+ " x.fzr,((select r.xm from gx_sys_ryb r where r.rybh=x.fzr ))fzrmc,"
				+ " x.fzr,('(' || x.fzr || ')' || r.xm ) fzrmc,"			
				+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
//						+ " x.gkbm,((select d.mc from gx_sys_dwb d where d.dwbh=x.gkbm ))gkbmmc,"
				+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
				+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
				+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
				+ " left join gx_sys_dwb dw on dw.dwbh=x.bmbh"
				+ " left join gx_sys_dmb dm on dm.zl='250' and dm.dm=x.xmdl"
				+ " left join gx_sys_dmb d on d.zl='251' and d.dm=x.xmlb"
				+ " left join gx_sys_ryb r on r.rybh=x.fzr"
				+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
				+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"' ");
		if(Validate.noNull(treesearch)){
			System.out.println(treesearch);
			tablename.append(" and x.fzr='"+CommonUtil.getBeginText(treesearch)+"' ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		if(Validate.noNull(rybh)){
			System.out.println(treesearch);
			tablename.append(" and x.fzr='"+rybh+"' ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		if(Validate.noNull(bmh)){
			System.out.println(bmh);
			tablename.append(" and x.bmbh='"+bmh+"' ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		tablename.append(") k");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(" ");
		pageList.setHj1("");//合计
		//页面数据
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}

}
