package com.googosoft.controller.ysgl.ystz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.jzmb.JzmbService;
import com.googosoft.service.ysgl.xmlx.XmlxService;
import com.googosoft.service.ysgl.xmsz.XmxxService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;


@Controller
@RequestMapping("ysgl/ystz/bmyx")
public class BmysController extends BaseController{

	@Resource(name="xmxxService")
	XmxxService xmxxService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name = "pageService")
	private PageService pageService;
	@Resource(name = "jzmbService")
	private JzmbService jzmbService;
	@Resource(name = "xmlxService")
	private XmlxService xmlxService;
	/**
	 * 跳转到项目信息列表页面
	 * @return
	 */
	@RequestMapping("/goXmxxPage")
	public ModelAndView goXmxxPage() {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("isWindow",this.getPageData().getString("isWindow"));
		mv.addObject("pname",this.getPageData().getString("param.pname"));
		mv.addObject("controlId",this.getPageData().getString("controlId"));
		mv.setViewName("ysgl/bmystz/xmxx_list");
		return mv;
	}
	/**
	 * 跳转到我的项目信息列表页面
	 * @return
	 */
	@RequestMapping("/goWdxmPage")
	public ModelAndView goWdxmPage() {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("isWindow",this.getPageData().getString("isWindow"));
		mv.addObject("pname",this.getPageData().getString("param.pname"));
		mv.addObject("controlId",this.getPageData().getString("controlId"));
		mv.setViewName("ysgl/ystz/bmyx/wdxm_list");
		return mv;
	}
	/**
	 * 跳转到执行情况查询
	 * @author 作者：Administrator
	 * @version 创建时间:2018-3-3下午7:03:14
	 */
	@RequestMapping("/goZxxmPage")
	public ModelAndView goZxxmPage() {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("isWindow",this.getPageData().getString("isWindow"));
		mv.addObject("pname",this.getPageData().getString("param.pname"));
		mv.addObject("controlId",this.getPageData().getString("controlId"));
		mv.setViewName("ysgl/ystz/bmyx/zxxm_list");
		return mv;
	}
	/**
	 * 跳转到变更项目负责人记录列表页面
	 * @return
	 */
	@RequestMapping("/goFzrbgjlPage")
	public ModelAndView goFzrbgjlPage() {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("isWindow",this.getPageData().getString("isWindow"));
		mv.addObject("pname",this.getPageData().getString("param.pname"));
		mv.addObject("controlId",this.getPageData().getString("controlId"));
		mv.addObject("id", this.getPageData().getString("id"));
		mv.setViewName("ysgl/ystz/bmyx/bgfzrjlList");
		return mv;
	}
	//获取列表数据
	@RequestMapping(value="getXmxxPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getXmxxPageData(HttpSession session) {
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbh,('('||dw.dwbh||')'||dw.mc) as bmmc ,x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,"
				+ " x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc,"
				+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
//				+ " x.fzr,((select r.xm from gx_sys_ryb r where r.rybh=x.fzr ))fzrmc,"
				+ " x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"			
				+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
//				+ " x.gkbm,((select d.mc from gx_sys_dwb d where d.dwbh=x.gkbm ))gkbmmc,"
				+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
				+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
				+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
				+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
				+ " left join gx_sys_dwb dw on dw.dwbh=x.bmbh  "
				+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"' ) k");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("k.GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere("");
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	//获取列表数据
	@RequestMapping(value="getWdxmPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getWdxmPageData(HttpSession session) {
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbh, x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,"
				+ " x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc," +
				"decode(nvl(ysje,0),0,'0.00',(to_char(round(ysje,2),'fm999999999999999999990.00')))ysje,decode(nvl(zfcgje,0),0,'0.00',(to_char(round(zfcgje,2),'fm999999999999999999990.00')))zfcgje,decode(nvl(fzfcgje,0),0,'0.00',(to_char(round(fzfcgje,2),'fm999999999999999999990.00')))fzfcgje,trunc(((zfcgje-zfcgsyje)/zfcgje)* 100,2) AS zfcgbl,trunc(((fzfcgje-fzfcgsyje)/fzfcgje)* 100,2) AS fzfcgbl,"
				+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
//				+ " x.fzr,((select r.xm from gx_sys_ryb r where r.rybh=x.fzr ))fzrmc,"
				+ " x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"			
				+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
//				+ " x.gkbm,((select d.mc from gx_sys_dwb d where d.dwbh=x.gkbm ))gkbmmc,"
				+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
				+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
				+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
				+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
				+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"' and x.fzr='"+LUser.getRybh()+"' ) k");
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
	//获取变更负责任记录列表数据
	@RequestMapping(value="getBgfzrjlPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getBgfzrjlPageData(HttpSession session) {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select t.guid as guid,"
					+ "        		k.xmmc as xmmc,"
					+ "        		k.xmbh as xmbh,"
					+ " 		    '(' || r1.rygh || ')' || r1.xm as oldfzr,"	
					+ " 		    '(' || r2.rygh || ')' || r2.xm as newfzr,"	
					+ " 		    '(' || r3.rygh || ')' || r3.xm as bgr,"	
					+ "             '(' || dw.dwbh || ')' || dw.mc as bmmc,"
					+ " 		    to_char(t.jdsj,'yyyy-MM-dd HH24:mi:ss') as bgsj"	
					+ " 	 from cw_fzrbgjlb t"	
					+ " 	 left join Cw_xmjbxxb k"	
					+ " 	   on t.xmid = k.guid"	
					+ " left join gx_sys_dwb dw on dw.dwbh = k.bmbh"
					+ " 	 left join gx_sys_ryb r1"	
					+ " 	   on t.oldfzr = r1.rybh"
					+ " 	 left join gx_sys_ryb r2"	
					+ " 	   on t.newfzr = r2.rybh"	
					+ " 	 left join gx_sys_ryb r3"	
					+ "        on t.jdr = r3.rybh ");
		if(Validate.noNull(pd.getString("idppp"))) {
			tablename.append(" where k.xmbh = '"+pd.getString("idppp")+"' ");
		}
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
	//初始化添加和编辑页面的下拉框数据
	public void iniSelect(ModelAndView mv) {
		/*String url = SystemSet.address+"/demo/dict";
		String xmdl = SendHttpUtil.sendPost(url, "zl="+Constant.XMDL);
		String xmlb = SendHttpUtil.sendPost(url, "zl="+Constant.XMLB);
		String xmsx = SendHttpUtil.sendPost(url, "zl="+Constant.XMSX);
		mv.addObject("xmdlList",SendHttpUtil.getResultToList(xmdl));
		mv.addObject("xmlbList",SendHttpUtil.getResultToList(xmlb));
		mv.addObject("xmsxList",SendHttpUtil.getResultToList(xmsx));*/
		mv.addObject("xmdlList",dictService.getDict(Constant.XMDL));
		mv.addObject("xmlbList",dictService.getDict(Constant.XMLB));
		mv.addObject("xmsxList",dictService.getDict(Constant.XMSX));
		mv.addObject("yslxList",dictService.getDict(Constant.YSLX));
		mv.addObject("xmlxList",dictService.getDict(Constant.XMLX));
		String loginId = LUser.getGuid();
		mv.addObject("loginId",loginId);
	}
	//跳转到项目信息添加页面
	@RequestMapping("/goXmxxAddPage")
	public ModelAndView goXmxxAddPage() {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		iniSelect(mv);
		String guid = this.get32UUID();//主表guid
		String xmym = pd.getString("xmym");
		mv.addObject("guid",guid);
		mv.addObject("xmym",xmym);
		mv.setViewName("ysgl/ystz/bmyx/xmxx_add");
		return mv;
	}
	//添加项目信息
	@RequestMapping("/xmxxAdd")
	@ResponseBody
	public Object xmxxAdd() {
		if(xmxxService.addXmxx(this.getPageData()) > 0) {
			return "{\"success\":true}";
		}else {
			return "{\"success\":false}";
		}
	}
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
    	int k = xmxxService.doDelete(dwbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_DWB dwb,HttpSession session){
		PageData pd = this.getPageData();
		String zfcgje = pd.getString("zfcgje");
		String fzfcgje = pd.getString("fzfcgje");
		System.err.println(zfcgje+"================="+fzfcgje);
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		String rybh = LUser.getRybh();
		//判断项目项目名称是否重复
//		boolean checkbmh=xmxxService.doCheckXmmc(pd.getString("xmmc"));
//		if(checkbmh==false)
//		{
//			
//			return "{\"success\":\"false\",\"msg\":\"项目名称不可重复!\"}";
//		}
		
		result = xmxxService.doAdd(pd,rybh,session,zfcgje,fzfcgje);
		if(result==1){
			return	"{\"success\":\"true\",\"msg\":\"信息保存成功!\"}";
			//return  "{success:'true', msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"',operateType:'U'}";
		}else{
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	@RequestMapping(value="/doSaveU",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSaveU(GX_SYS_DWB dwb){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String guid = pd.getString("guid");
		int result=0;
		String rybh = LUser.getRybh();
		//判断项目项目名称是否重复
//		boolean checkbmh=xmxxService.doCheckXmmcU(pd.getString("xmmc"));
//		if(checkbmh==false)
//		{
//			return  "{success:false,msg:'项目名称不可重复!'}";
//		}
		Map<String, Object> xmxx = xmxxService.getXmxxMapById(pd.getString("guid"));
		result = xmxxService.doUpdate(pd,guid,xmxx.get("FZR")+"");
		if(result==1)
		{
			return "{\"success\":true}";
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	
	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete() {
		if(xmxxService.deleteXmxx(this.getPageData()) > 0) {
			return "{\"success\":true}";
		}else {
			return "{\"success\":false}";
		}
	}

	//跳转到项目信息编辑页面
			@RequestMapping("/goXmxxLookPage")
			public ModelAndView goXmxxLookPage(HttpSession session) {
				ModelAndView mv = this.getModelAndView();
				PageData pd = this.getPageData();
				iniSelect(mv);
				String guid = pd.getString("guid");
				String xmym = pd.getString("xmym");
				String flag = pd.getString("flag");
				String kjzd=CommonUtil.getKjzd(session);
				//
				Map<String, Object> xmxx = xmxxService.getXmxxMapById(pd.getString("guid"));
				//收入信息
				List<Map<String, Object>> srmap  = xmxxService.getsrkmById(guid,kjzd);
				List<Map<String, Object>> zcmap  = xmxxService.getzckmById(guid,kjzd);
				List<Map<String, Object>> jjflmap  = xmxxService.getjjflkmById(guid);


				mv.addObject("xmxx",xmxx);
				mv.addObject("guid",guid);
				mv.addObject("xmym",xmym);
				mv.addObject("srmap",srmap);
				mv.addObject("zcmap",zcmap);
				mv.addObject("jjflmap",jjflmap);
				mv.addObject("flag",flag);
				mv.setViewName("ysgl/bmystz/xmxx_look");
				return mv;
			}	
}
