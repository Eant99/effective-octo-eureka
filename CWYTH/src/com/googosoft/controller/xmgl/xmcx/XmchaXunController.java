package com.googosoft.controller.xmgl.xmcx;

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
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xmgl.xmcx.XmcxService;
import com.googosoft.service.ysgl.xmsz.XmxxService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "xmgl/xmcx/xmchaxun")
public class XmchaXunController extends BaseController {

	@Resource(name="xmcxService1")
	private  XmcxService xmcxService;//单例
	@Resource(name = "pageService")
	private PageService pageService;
	@Resource(name="xmxxService")
	XmxxService xmxxService;
	
	/**
	 * 跳转到项目查询列表页面
	 * @return
	 */
	@RequestMapping("/goXmcxPage")
	public ModelAndView goXmcxPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh=pd.getString("rybh");
		String bmh=pd.getString("bmh");
		String treesearch = pd.getString("treesearch");
		mv.addObject("isWindow",this.getPageData().getString("isWindow"));
		mv.addObject("pname",this.getPageData().getString("param.pname"));
		mv.addObject("controlId",this.getPageData().getString("controlId"));
		mv.setViewName("xmgl/xmcx/xmchaxun");
		mv.addObject("treesearch",treesearch);
		mv.addObject("rybh",rybh);
		mv.addObject("bmh",bmh);
		return mv;
	}
		/**
		 * 获得项目查询列表页面数据
		 * @return
		 */
		@RequestMapping(value="getXmxxPageData",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getXmxxPageData(HttpSession session) {
			String sszt = Constant.getztid(session);
			String loginrybh = LUser.getRybh();
			//获取当前人员所在单位（部门）权限
			String bmbh;
			bmbh=xmcxService.getDwbhByLoginuser(loginrybh);
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
					+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
//					+ " x.fzr,((select r.xm from gx_sys_ryb r where r.rybh=x.fzr ))fzrmc,"
					+ " x.fzr,('(' || x.fzr || ')' || r.xm ) fzrmc,"			
					+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
//					+ " x.gkbm,((select d.mc from gx_sys_dwb d where d.dwbh=x.gkbm ))gkbmmc,"
					+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
					+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
					+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
					+ " left join gx_sys_dwb dw on dw.dwbh=x.bmbh"
					+ " left join gx_sys_dmb dm on dm.zl='250' and dm.dm=x.xmdl"
					+ " left join gx_sys_dmb d on d.zl='251' and d.dm=x.xmlb"
					+ " left join gx_sys_ryb r on r.rybh=x.fzr"
					+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
					+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"'");
			//此处修改：李伟等人获取的单位编号为001，001是学院的顶级单位编号，没法查到相应的项目信息，改为查找所有项目
			if(!bmbh.equals("001")) {
				tablename.append(" and x.bmbh='"+bmbh+"' ");
			}
			if(Validate.noNull(treesearch)){
				tablename.append(" and x.fzr='"+CommonUtil.getBeginText(treesearch)+"' ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
			}
			if(Validate.noNull(rybh)){
				tablename.append(" and x.fzr='"+rybh+"' ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
			}
			if(Validate.noNull(bmh)){
				tablename.append(" and x.bmbh='"+bmh+"' ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
			}
			tablename.append(") k");
			pageList.setSqlText(sqltext.toString());
			//设置表名
			pageList.setTableName(tablename.toString());
			//设置表主键名
			pageList.setKeyId("GUID");//主键
			//设置WHERE条件
			pageList.setStrWhere("");
			pageList.setHj1("");//合计
			//页面数据
			pageList = pageService.findPageList(pd,pageList);
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
			/**
			 * 跳转到项目信息查看页面
			 * @return
			 */
			@RequestMapping("/goXmxxLookPage")
			public ModelAndView goXmxxLookPage(HttpSession session) {
				ModelAndView mv = this.getModelAndView();
				PageData pd = this.getPageData();
				String guid = pd.getString("guid");
				String kjzd=CommonUtil.getKjzd(session);
				//
				Map<String, Object> xmxx = xmxxService.getXmxxMapById(pd.getString("guid"));
				//收入信息
				List<Map<String, Object>> srmap  = xmcxService.getsrkmById(guid,kjzd);
				List<Map<String, Object>> zcmap  = xmcxService.getzckmById(guid,kjzd);
				List<Map<String, Object>> jjflmap  = xmcxService.getjjflkmById(guid);


				mv.addObject("xmxx",xmxx);
				mv.addObject("guid",guid);
				mv.addObject("srmap",srmap);
				mv.addObject("zcmap",zcmap);
				mv.addObject("jjflmap",jjflmap);
				mv.setViewName("xmgl/xmcx/xmcx_look");
				return mv;
			}
	/**
	 * 获取单位人员树
	 * @param rybh 人员编号
	 * @return
	 */
	@RequestMapping(value="/xmcxTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xmcxTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return xmcxService.getPowerNode(pd,loginrybh);
			}else{
			    return xmcxService.getDwRyNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取单位人员树
	 * @param rybh 人员编号
	 * @return
	 */
	@RequestMapping(value="/tjxmcxTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object tjxmcxTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return xmcxService.getPowerNode(pd,loginrybh);
			}else{
			    return xmcxService.getDwRyTjNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	
}
