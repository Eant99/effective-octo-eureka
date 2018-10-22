
package com.googosoft.controller.ysgl.xmsz;

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
@RequestMapping("ysgl/xmsz/xmxx")
public class XmxxController extends BaseController{

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
		
		
		PageData pd = this.getPageData();
		
		String xmbh = pd.getString("xmbh");
		String xmmc = pd.getString("xmmc");
		String bmmc = pd.getString("bmmc");
		ModelAndView mv = this.getModelAndView();
		mv.addObject("xmbh",xmbh);
		mv.addObject("xmmc",xmmc);
		mv.addObject("bmmc",bmmc);
		mv.addObject("isWindow",this.getPageData().getString("isWindow"));
		mv.addObject("pname",this.getPageData().getString("param.pname"));
		mv.addObject("controlId",this.getPageData().getString("controlId"));
		mv.setViewName("ysgl/xmsz/xmxx/xmxx_list");
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
		mv.setViewName("ysgl/xmsz/xmxx/wdxm_list");
		return mv;
	}
	/**
	 * 跳转到执行情况查询页面
	 * @author 作者：Administrator
	 * @version 创建时间:2018-3-3下午7:03:14
	 */
	@RequestMapping("/goZxxmPage")
	public ModelAndView goZxxmPage() {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("isWindow",this.getPageData().getString("isWindow"));
		mv.addObject("pname",this.getPageData().getString("param.pname"));
		mv.addObject("controlId",this.getPageData().getString("controlId"));
		mv.setViewName("ysgl/xmsz/xmxx/zxxm_list");
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
		mv.setViewName("ysgl/xmsz/xmxx/bgfzrjlList");
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
	/**
	 * 跳转到执行情况查询列表页面
	 * @return
	 */
	@RequestMapping(value="getWdxmPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getWdxmPageData(HttpSession session) {
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" K.*,to_char((nvl(zcje1,0)+nvl(zcje2,0)),'FM9999999999999999999999999999999999999990.00')as zcje,to_char((nvl(dshje1,0)+nvl(dshje2,0)),'FM999999999999999999999999999999999990.00')as dshje  ");
		tablename.append(" ( select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbh, x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,"
				+ " x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc," +
				"decode(nvl(ysje,0),0,'0.00',(to_char(round(ysje,2),'fm999999999999999999990.00')))ysje,decode(nvl(zfcgje,0),0,'0.00',(to_char(round(zfcgje,2),'fm999999999999999999990.00')))zfcgje,decode(nvl(zfcgje+fzfcgje,0),0,'0.00',(to_char(round(zfcgje+fzfcgje,2),'fm999999999999999999990.00')))ysyje,decode(nvl(fzfcgje,0),0,'0.00',(to_char(round(fzfcgje,2),'fm999999999999999999990.00')))fzfcgje,decode(zfcgje,0,0,trunc(((zfcgje - zfcgsyje) / zfcgje) * 100, 2)) AS zfcgbl,"
				+ " to_char(syje,'FM999999999999999999999999999999990.00') as syje,(select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
//				+ " x.fzr,((select r.xm from gx_sys_ryb r where r.rybh=x.fzr ))fzrmc,"
				+ " x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"			
				+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
//				+ " x.gkbm,((select d.mc from gx_sys_dwb d where d.dwbh=x.gkbm ))gkbmmc,"
				+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
				+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc, "
				+ " (select sum(r.BXJE) from cw_rcbxmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=x.guid and b.shzt='8' )as zcje1,"
				+ " (select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=x.guid and b.shzt='8' )as zcje2,"
				+ " (select sum(r.BXJE) from cw_rcbxmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=x.guid and b.shzt not in('8') )as dshje1,"
				+ " (select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=x.guid and b.shzt not in('8') )as dshje2 " 
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
	/**
	 * 获取变更负责任记录列表数据
	 * @return
	 */
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
	/**
	 * 跳转到项目信息添加页面
	 * @return
	 */
	@RequestMapping("/goXmxxAddPage")
	public ModelAndView goXmxxAddPage() {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		iniSelect(mv);
		String guid = this.get32UUID();//主表guid
		String xmym = pd.getString("xmym");
		mv.addObject("guid",guid);
		mv.addObject("xmym",xmym);
		mv.setViewName("ysgl/xmsz/xmxx/xmxx_add");
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
	//添加页面的保存
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_DWB dwb,HttpSession session){
		PageData pd = this.getPageData();
		String zfcgje = pd.getString("zfcgje");
		String fzfcgje = pd.getString("fzfcgje");
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
	//编辑页面的保存
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
	/**
	 * 部门人员信息弹出窗
	 */
	@RequestMapping(value="/rypage")
	public ModelAndView goRyxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String xmid = pd.getString("xmid");
		mv.addObject("xmid",xmid);
		mv.setViewName("ysgl/xmsz/xmxx/ryxx_window");
		return mv;
	}
	@RequestMapping(value="/doChangeFzr",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doChangeFzr(){
		PageData pd = this.getPageData();
		String xmid = pd.getString("xmid");
		String newfzr = pd.getString("rybh");
    	int k = xmxxService.doChangeFzr(xmid,newfzr);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_SAVE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
    	}
	}
	
	//跳转到项目信息编辑页面
	@RequestMapping("/goXmxxEditPage")
	public ModelAndView goXmxxEditPage(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		iniSelect(mv);
		String guid = pd.getString("guid");
		String xmym = pd.getString("xmym");
		//实现快捷查询的返回
		String xmbh = pd.getString("xmbh");
		String xmmc = pd.getString("xmmc");
		String bmmc = pd.getString("bmmc");
		//判断编辑页面入口
		String type=pd.getString("type");
		String kjzd=CommonUtil.getKjzd(session);
		//
		Map<String, Object> xmxx = xmxxService.getXmxxMapById(pd.getString("guid"));
		//收入信息
		List<Map<String, Object>> srmap  = xmxxService.getsrkmById(guid,kjzd);
		List<Map<String, Object>> zcmap  = xmxxService.getzckmById(guid,kjzd);
		List<Map<String, Object>> jjflmap  = xmxxService.getjjflkmById(guid);
		mv.addObject("xmbh",xmbh);
		mv.addObject("xmmc",xmmc);
		mv.addObject("bmmc",bmmc);
		mv.addObject("type", type);
		mv.addObject("xmxx",xmxx);
		mv.addObject("guid",guid);
		mv.addObject("xmym",xmym);
		mv.addObject("srmap",srmap);
		mv.addObject("zcmap",zcmap);
		mv.addObject("jjflmap",jjflmap);
		mv.setViewName("ysgl/xmsz/xmxx/xmxx_edit");
		return mv;
	}
	//跳转到项目信息查看页面
		@RequestMapping("/goXmxxLookPage")
		public ModelAndView goXmxxLookPage(HttpSession session) {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			iniSelect(mv);
			//实现快捷查询的返回
			String xmbh = pd.getString("xmbh");
			String xmmc = pd.getString("xmmc");
			String bmmc = pd.getString("bmmc");
			
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

			mv.addObject("xmbh",xmbh);
			mv.addObject("xmmc",xmmc);
			mv.addObject("bmmc",bmmc);
			mv.addObject("xmxx",xmxx);
			mv.addObject("guid",guid);
			mv.addObject("xmym",xmym);
			mv.addObject("srmap",srmap);
			mv.addObject("zcmap",zcmap);
			mv.addObject("jjflmap",jjflmap);
			mv.addObject("flag",flag);
			mv.setViewName("ysgl/xmsz/xmxx/xmxx_look");
			return mv;
		}
	//编辑项目信息
	@RequestMapping("/xmxxEdit")
	@ResponseBody
	public Object xmxxEdit() {
		if(xmxxService.editXmxx(this.getPageData()) > 0) {
			return "{\"success\":true}";
		}else {
			return "{\"success\":false}";
		}
	}
	/**
	 * 验证项目信息是否正被使用
	 */
	@RequestMapping(value="/selectXmmc",produces = "text/html;chartset=UTF-8")
	@ResponseBody
	public Object selectXmmc() {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("dwbh");
		boolean yzxmxx = xmxxService.selectXmmc(guid);
		Gson gson = new Gson();
		return gson.toJson(yzxmxx);
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
	//检查项目编号是否存在
	@RequestMapping("/checkXmbhExist")
	@ResponseBody
	public Object checkXmbhExist() {
		if(xmxxService.checkXmbhExist(this.getPageData())) {
			return "{\"exist\":true}";
		}else {
			return "{\"exist\":false}";
		}
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
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select k.guid,k.bmbh, k.xmbh,k.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=k.xmdl)xmdlmc,"
				+ " k.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=k.xmlb)xmlbmc,k.xmmc,"
				+ " k.xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=k.xmlx)xmlxmc,"
				+ " k.fzr,('('||k.fzr||')'||(select r.xm from gx_sys_ryb r where r.rybh=k.fzr ))fzrmc,"
				+ "k.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
//				+ "k.xmsx,(select D.MC from gx_sys_dmb d where d.zl='252' and d.dm=k.xmsx)xmsxmc,"
				+ " k.gkbm,('('||k.gkbm||')'||(select d.mc from gx_sys_dwb d where d.dwbh=k.gkbm ))gkbmmc,"
				+ " k.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
				+ " from Cw_xmjbxxb k left join Cw_xmkzxxb c  on c.xmbh = k.xmbh"
				+ " left join Cw_xmsrbnew s  on s.xmxxbh = k.xmbh left join Cw_xmzcbnew z on z.xmxxbh = k.xmbh"
				+ " left join Cw_xmjjflbnew j on j.xmxxbh = k.xmbh  where 1=1");
		String xmbh = pd.getString("xmbh");
		String xmmc = pd.getString("xmmc");
		if(Validate.noNull(xmbh)){
			sqltext.append(" and k.xmbh like '%" + xmbh + "%'");
		}
		if(Validate.noNull(xmmc)){
			sqltext.append(" and k.xmmc like '%" + xmmc + "%'");
		}
		String guid = pd.getString("guid");
		if(Validate.noNull(guid)){
			sqltext.append(" and k.guid in ('"+guid+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchValue));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("bmbh");
		m.setShowname("部门编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xmbh");
		m.setShowname("项目编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xmmc");
		m.setShowname("项目名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XMDLMC");
		m.setShowname("项目大类");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XMLBMC");
		m.setShowname("项目类别");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XMLX");
		m.setShowname("项目类型");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("FZRMC");
		m.setShowname("负责人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XMSXMC");
		m.setShowname("项目属性");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("GKBMMC");
		m.setShowname("归口部门");
		mlist.add(m);
		m = null;
		
		
		m = new M_largedata();
		m.setName("SFQYMC");
		m.setShowname("是否启用");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	//导出项目信息
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpSession session) {
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.xmlxService.expExcel(realfile, shortfileurl,searchValue,pd,sszt);
	}
	@RequestMapping("/expExcel3")
	@ResponseBody
	public Object Info3(HttpServletRequest request,HttpSession session) {
		PageData pd = this.getPageData();
		String guid = pd.getString("id");
		String sszt = Constant.getztid(session);
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.xmlxService.expExcel3(realfile, shortfileurl,searchValue,guid,sszt);
	}
	/**
	 * 项目类型弹出窗
	 */
	@RequestMapping(value="/getxmlx")
	public ModelAndView getxmlx(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String bmbh = pd.getString("bmbh");
		mv.addObject("bmbh",bmbh);
		mv.addObject("controlId",controlId);//名称
		mv.setViewName("ysgl/xmsz/xmxx/xmlx_window");
		return mv;
	}
	
	/**
	 * 项目类型弹出窗
	 */
	@RequestMapping(value="/getxmlxall")
	public ModelAndView getxmlxall(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String id = pd.getString("id");
		String bmbh = pd.getString("bmbh");
		mv.addObject("bmbh",bmbh);
		mv.addObject("controlId",controlId);//名称
		mv.addObject("id",id);
		mv.setViewName("ysgl/xmsz/xmxx/xmlxall_window");
		return mv;
	}
	/**
	 * 得到收入科目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getsrkm")
	@ResponseBody
	public Object getsrkm(HttpSession session) throws Exception {
	
		PageData pd = this.getPageData();
		String xmlxbh = pd.getString("xmlxbh");
		String kjzd=CommonUtil.getKjzd(session);
		List list = xmxxService.getsrkm(xmlxbh,kjzd);
		return list;
	}
	/**
	 * 联想得到收入科目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getsrkm1")
	@ResponseBody
	public Object getsrkm1(HttpSession session) throws Exception {
	
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String xmbh = pd.getString("xmbh");
		String kjzd=CommonUtil.getKjzd(session);
		
		List list = xmxxService.getsrkm1(xmbh,sszt,kjzd);
		return list;
	}
	/**
	 * 得到项目扩展信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getxmlxxx")
	@ResponseBody
	public Object getxmlxxx() throws Exception {
	
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		
		Map map = xmxxService.getxmlxxx(guid);
		return map;
	}
	/**
	 * 联想输入得到项目扩展信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getxmlxxx1")
	@ResponseBody
	public Object getxmlxxx1(HttpSession session) throws Exception {
	
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String xmbh = pd.getString("xmbh");
		
		Map map = xmxxService.getxmlxxx1(xmbh,sszt);
		return map;
	}
	/**
	 * 得到收入科目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getzckm")
	@ResponseBody
	public Object getzckm(HttpSession session) throws Exception {
	
		PageData pd = this.getPageData();
		String xmlxbh = pd.getString("xmlxbh");
		String kjzd=CommonUtil.getKjzd(session);
		List list = xmxxService.getzckm(xmlxbh,kjzd);
		return list;
	}
	/**
	 * 联想得到收入科目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getzckm1")
	@ResponseBody
	public Object getzckm1(HttpSession session) throws Exception {
	
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String xmbh = pd.getString("xmbh");
		String kjzd=CommonUtil.getKjzd(session);
		List list = xmxxService.getzckm1(xmbh,sszt,kjzd);
		return list;
	}
	/**
	 * 得到收入科目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getjjflkm")
	@ResponseBody
	public Object getjjflkm() throws Exception {
	
		PageData pd = this.getPageData();
		String xmlxbh = pd.getString("xmlxbh");
		
		List list = xmxxService.getjjflkm(xmlxbh);
		return list;
	}
	/**
	 * 联想得到经济分类科目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getjjflkm1")
	@ResponseBody
	public Object getjjflkm1(HttpSession session) throws Exception {
	
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String xmbh = pd.getString("xmbh");
		
		List list = xmxxService.getjjflkm1(xmbh,sszt);
		return list;
	}
	@RequestMapping(value="/addsr",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addsr(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
//		String sszt = Constant.getztid(session);
//		String kjzd=CommonUtil.getKjzd(session);
		String srkmbhs = pd.getString("srkmbhs");
		String srkmmcs = pd.getString("srkmmcs");
		String yslxs = pd.getString("yslxs");
//		String srkmbh[] = srkmbhs.split(",",-1);
//		String srkmmc[] = srkmmcs.split(",",-1);
//		String json = pd.getString("json");	//得到前台的json
//		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
//		Gson gson = new Gson();
//		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		xmxxService.addsr(session,srkmbhs,srkmmcs,yslxs);
//		for(int i=0;i<srkmbh.length;i++) {
//			Map map=(Map) list.get(i);
			 
//			Map map1 = new HashMap<>();
//			String czr = LUser.getGuid();
//			String guid = this.get32UUID();
//			map1.put("guid", guid);
//			map1.put("ywid", map.get("kmbh"));
//			map1.put("czid", czr);
//			map1.put("czr", czr);
//			map1.put("kmlx", "1");
//			map1.put("zbid", map.get("xmbh"));
//			map1.put("sszt", sszt);
//			
//			xmlxService.doAddjwjl(map1,kjzd);
//		}
		
		return 0;
		
		
	}
	@RequestMapping(value="/addzc",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addzc(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String kjzd=CommonUtil.getKjzd(session);
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for(int i=0;i<list.size();i++) {
			Map map=(Map) list.get(i);
			xmxxService.addzc(session,map);
			
			Map map1 = new HashMap<>();
			String czr = LUser.getGuid();
			String guid = this.get32UUID();
			map1.put("guid", guid);
			map1.put("ywid", map.get("kmbh"));
			map1.put("czid", czr);
			map1.put("czr", czr);
			map1.put("kmlx", "1");
			map1.put("zbid", map.get("xmbh"));
			map1.put("sszt", sszt);
			
			xmlxService.doAddjwjl(map1,kjzd);
		}
		
		return 0;
		
		
	}
	@RequestMapping(value="/addjjfl",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addjjfl(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for(int i=0;i<list.size();i++) {
			Map map=(Map) list.get(i);
			xmxxService.addjjfl(session,map);
			
			Map map1 = new HashMap<>();
			String czr = LUser.getGuid();
			String guid = this.get32UUID();
			map1.put("guid", guid);
			map1.put("ywid", map.get("kmbh"));
			map1.put("czid", czr);
			map1.put("czr", czr);
			map1.put("kmlx", "2");
			map1.put("zbid", map.get("xmbh"));
			map1.put("sszt", sszt);
			
			xmlxService.doAddjwjl1(map1);
		}
	return 0;
	}
	@RequestMapping(value="/editjjfl",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object editjjfl(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		String xmlxbh=pd.getString("xmxxbh");
		xmxxService.deletejjfl(xmlxbh);
		for(int i=0;i<list.size();i++) {
			Map map=(Map) list.get(i);
			xmxxService.addjjfl(session,map);
			
		}
	return 0;
	}
	@RequestMapping(value="/editsr",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object editsr(HttpSession session)throws Exception{
//		PageData pd = this.getPageData();
//		String json = pd.getString("json");	//得到前台的json
//		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
//		Gson gson = new Gson();
//		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
//		//删除
//		String xmlxbh=pd.getString("xmxxbh");
//		
//		xmxxService.deletesr(xmlxbh);
//		
//			for(int i=0;i<list.size();i++) {
//				Map map=(Map) list.get(i);
//				xmxxService.addsr(session,map);
//				
//			
//		}
			PageData pd = this.getPageData();
//			String sszt = Constant.getztid(session);
//			String kjzd=CommonUtil.getKjzd(session);
			String srkmbhs = pd.getString("srkmbhs");
			String srkmmcs = pd.getString("srkmmcs");
			String yslxs = pd.getString("yslxs");
//			String srkmbh[] = srkmbhs.split(",",-1);
//			String srkmmc[] = srkmmcs.split(",",-1);
//			String json = pd.getString("json");	//得到前台的json
//			String ajson = json.substring(8,json.length()-1);//截取json,让gson用
//			Gson gson = new Gson();
//			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			xmxxService.updatesr(session,srkmbhs,srkmmcs,yslxs);
//			for(int i=0;i<srkmbh.length;i++) {
//				Map map=(Map) list.get(i);
				 
//				Map map1 = new HashMap<>();
//				String czr = LUser.getGuid();
//				String guid = this.get32UUID();
//				map1.put("guid", guid);
//				map1.put("ywid", map.get("kmbh"));
//				map1.put("czid", czr);
//				map1.put("czr", czr);
//				map1.put("kmlx", "1");
//				map1.put("zbid", map.get("xmbh"));
//				map1.put("sszt", sszt);
//				
//				xmlxService.doAddjwjl(map1,kjzd);
//			}
			
			return 0;
	}
	@RequestMapping(value="/editzc",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object editzc(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		String xmlxbh=pd.getString("xmxxbh");
		xmxxService.deletezc(xmlxbh);
		for(int i=0;i<list.size();i++) {
			Map map=(Map) list.get(i);
			xmxxService.addzc(session,map);
			
		}
	return 0;
	}
	
	
}
