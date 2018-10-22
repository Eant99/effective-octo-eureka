package com.googosoft.controller.ysgl.xmbq;

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
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.ysgl.CW_XMBQMXB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.Xmbq.XmbqService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/xmbq")
public class XmbqController extends BaseController{
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="xmbqService")
	private XmbqService xmbqService;//单例
	
	/**
	 * 跳转项目标签列表页面
	 * @return
	 */
	@RequestMapping(value = "/goListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/xmsz/xmbq/xmbq_list");
		return mv;
	}
	
	/**
	 * 获取项目标签列表页面数据
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {	
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("GUID,BQBH,BQMC,BZ,"
				+ "(select wm_concat(to_char(m.XMMC)) from cw_xmbqmxb m where m.zbid = a.guid) as xmmc");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setTableName("CW_XMBQZB A");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "",
				pageList.getTotalList().get(0).get("NUM") + "", gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
	
	/**
	 * 获取项目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/xmxx")
	public ModelAndView window(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/xmsz/xmbq/xmxx");
		return mv;
	}
	
	/**
	 * 获取项目信息
	 * @param session
	 * @return
	 */
			@RequestMapping(value="getXmxxPageData",produces="text/html;charset=UTF-8")
			@ResponseBody
			public Object getXmxxPageData(HttpSession session) {
				String sszt = Constant.getztid(session);
				PageData pd = this.getPageData();
				StringBuffer sqltext = new StringBuffer();//查询字段
				StringBuffer tablename = new StringBuffer();
				String strWhere = "";
				PageList pageList = new PageList();
				String bmbh = pd.getString("bmbh");
				sqltext.append(" * ");
				tablename.append(" ( select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbhmc,x.bmbh, x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,"
						+ " x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc,"
						+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
						+ " x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"			
						+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
						+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
						+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
						+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
						+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
						+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"') k");
				pageList.setSqlText(sqltext.toString());
				//设置表名
				pageList.setTableName(tablename.toString());
				if(!Validate.isNullOrEmpty(bmbh)) {
					strWhere  += " and k.bmbh = '"+bmbh+"'";
				}
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
			
	/**
	 * 获取详细信息页面（增加、修改）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goEditPage")
	public ModelAndView goEditPage() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		if (operateType.equals("C")) {
			String guid = GenAutoKey.get32UUID();
			mv.addObject("guid", guid);
		} else if (operateType.equals("U")) {
			PageData pd = this.getPageData();					
			String guid = pd.getString("guid");		
			mv.addObject("guid",guid);
			Map<?, ?> xmbq = xmbqService.getXmbqById(guid);
			List xmbqmx = xmbqService.getXmbqmxById(guid);
			mv.addObject("xmbq", xmbq);
			if(xmbqmx.size()>0) {
				mv.addObject("xmbqmx",xmbqmx);
			}
		}
		mv.setViewName("ysgl/xmsz/xmbq/xmbq_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	
	/**
	 * 保存项目标签主表信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave() {
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		int result = 0;
		if ("C".equals(operateType))//新增保存 
		{
			// 判断编号是否重复
//			int flag = xmbqService.getXhById(pd);
//			if (flag!=0) {
//				return "{\"uccess\":\"true\",\"msg\":\"该编号已经存在！\"}";
//			}
			result = xmbqService.doAdd(pd);
			if (result > 0) {
				return "{\"uccess\":\"true\",\"msg\":\"信息保存成功！\"}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else if ("U".equals(operateType)){ //修改
			String guid = pd.getString("guid");
			result = xmbqService.updXmbqzb(pd,guid);
			if (result == 1) {
				return "{\"uccess\":\"true\",\"msg\":\"信息保存成功！\"}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else {
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
    	}		
	}
	/**
	 * 增加项目标签明细表
	 * @return
	 */
	@RequestMapping(value="/doSaveMxb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveMxb(CW_XMBQMXB xmbqmxb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String zbid = pd.getString("zbid");
		xmbqService.doDelMxb(zbid);
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = this.get32UUID();//创建主键			
			       zbid = (String) map.get("zbid");
			String xmid = (String) map.get("xmid");
			String xmbh = (String) map.get("xmbh");
			String xmmc = (String) map.get("xmmc");
			String bmmc = (String) map.get("bmmc");
			    
			xmbqmxb.setGuid(guid);
			xmbqmxb.setZbid(zbid);
			xmbqmxb.setXmid(xmid);
			xmbqmxb.setXmbh(xmbh);
			xmbqmxb.setXmmc(xmmc);
			xmbqmxb.setBmmc(bmmc);
				//增加
				xmbqService.doSaveMxb(xmbqmxb);	
		}
		b="success";
		return b;
	}
	/**
     * 删除项目标签信息
     * @return
     */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		int i = xmbqService.delete(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	/**
	 * 验证标签编号是否重复
	 */
	@RequestMapping(value="/selectBqbh",produces = "text/html;chartset=UTF-8")
	@ResponseBody
	public Object selectMbbh() {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		String bqbh = pd.getString("bqbh");
		boolean yzbqbh = xmbqService.getObjectByBqbh(guid,bqbh);
		Gson gson = new Gson();
		return gson.toJson(yzbqbh);
	}
}
