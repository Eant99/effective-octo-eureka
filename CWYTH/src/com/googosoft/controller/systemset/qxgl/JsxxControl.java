package com.googosoft.controller.systemset.qxgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.constant.TnameU;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.systemset.qxgl.JSB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.qxgl.JsxxService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 角色信息控制类 
 */
@Controller
@RequestMapping(value = "/jsxx")
public class JsxxControl extends BaseController {

	@Resource(name = "jsxxService")
	private JsxxService jsxxService;
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	/**
	 * 跳转角色管理页面
	 */
	@RequestMapping(value="goJsglPage")
	public ModelAndView goHsortSetPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/qxgl/jsgl/jsgl_list");
		return mv;
	}

	/**
	 * 删除前判断是不是已经被启用
	 */
	@RequestMapping(value="/getNewStatus",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getNewStatus(){
		PageData pd = this.getPageData();
		String sjdd = pd.getString("sjdd");
		String count = jsxxService.getNewStatus(sjdd);
		if("0".equals(count)){
			return  "{success:'true'}";
		}else{
			return  "{success:'false',msg:'请将该单位下所有节点单位状态禁用后重试!'}";
		}
	}
	/**
	 * 角色管理（列表）：获取角色信息列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("D.jsbh as jsbh,'('||D.jsbh||')'||D.jsmc as jsmc,D.bz,case D.zt when 0 then '禁用' when 1 then '启用' end as zt,case D.xgbz when 0 then '用户添加' when 1 then '系统定义' end as xgbz");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("jsbh");//主键
//		String searchValue = pd.getString("search[value]");
//		System.out.println("==================="+searchValue);
//		if(Validate.noNull(searchValue)){
//			pageList.setStrWhere(" and (D.JSBH like '%"+CommonUtils.StringFilter(searchValue)+"%' or D.JSMC like '%"+CommonUtils.StringFilter(searchValue)+"%') ");
//		}else{
//			pageList.setStrWhere("");//where条件
//		}
		pageList.setStrWhere("");
		pageList.setTableName(TnameU.JSB+" D");//表名
		pageList.setHj1("");//合计
		
		
		System.out.println(pd.get("search[value]"));
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}

	/**
	 * 角色管理：U/L:获取单个人员详细信息 C:添加新的人员信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getJsxx")
	public ModelAndView getJsxx() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		if (operateType.equals("U") || operateType.equals("L")) {
			Map map = jsxxService.getRyxx(this.getPageData().getString("jsbh"));
			mv.addObject("jsb", map);
		} else if (operateType.equals("C")) {
			String jsbh = AutoKey.makeCkbh(TnameU.JSB, "JSBH", "2");
			Map map = new HashMap();
			map.put("jsbh", jsbh);
			mv.addObject("jsb", map);
		}
		mv.setViewName("systemset/qxgl/jsgl/jsgl_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}

	/**
	 * 角色管理（添加角色）：人员信息保存
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(JSB jsb) {
		String operateType = this.getPageData().getString("operateType");
		boolean isSuccess;
		switch (operateType) {
		case "C":// 添加人员
			isSuccess = jsxxService.doAdd(jsb);
			if (isSuccess)
				return "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
			else
				return "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
		case "U":// 修改人员
			isSuccess = jsxxService.doUpdate(jsb);
			if (isSuccess)
				return "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
			else
				return "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
		default:
			return "{\"success\":\"false\",\"msg\":\"参数传入有误！\"}";
		}
	}
	/**
	 * 角色管理：（角色用户）增加按钮
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doAdd")
	public ModelAndView doAdd() {
		ModelAndView mv = this.getModelAndView();
		String jsbh = this.getPageData().getString("jsbh");
		mv.setViewName("systemset/qxgl/jsgl/jsgl_window");
		mv.addObject("jsbh", jsbh);
		return mv;
	}
	/**
	 * 角色管理：（角色用户）增加按钮获取角色人员
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findJsyh")
	public ModelAndView findJsyf() {
		ModelAndView mv = this.getModelAndView();
		String pname = this.getPageData().getString("pname");
		String jsbh = this.getPageData().getString("jsbh");
		mv.setViewName("systemset/qxgl/jsgl/jsgl_ryxxList");
		mv.addObject("pname", pname);
		mv.addObject("jsbh", jsbh);
		return mv;
	}

	/**
	 * 角色管理： 删除角色
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String jsbh = this.getPageData().getString("jsbh");
		boolean b = false;
		b = jsxxService.doDelete(jsbh);
		if (b) {
			return "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
		} else {
			return "{\"success\":\"false\",\"msg\":\"操作成功！\"}";
		}
	}

	/**
	 * 角色管理（操作限制）：获取操作权限设置列表页面
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/findJsCzqxszList")
	public ModelAndView findJsCzqxszList(){
		ModelAndView mv = this.getModelAndView();
		String jsbh = this.getPageData().getString("jsbh");
		//获取角色信息
		Map jsxx=jsxxService.getRyxx(jsbh);
		//获取一级菜单
		List<Map> yjcd = jsxxService.getYjcdList();
		//循环一级菜单，获取二级菜单
		for(int i=0;i<yjcd.size();i++){
			Map map = yjcd.get(i);
			List<Map> ejcd = jsxxService.getEjcdList(map.get("MKBH")+"");
			int sjcdSize = 1;
			//循环二级级菜单，获取三级菜单
			for(int j=0;j<ejcd.size();j++){
				Map ejcd_map = ejcd.get(j);
				List<Map> sjcd = jsxxService.getSjcdList(ejcd_map.get("MKBH")+"");
				//循环三级级菜单，判断角色是否有该菜单的权限，有权返回true，无权返回false
				for(int k=0;k<sjcd.size();k++){
					Map sjcd_map = sjcd.get(k);
					boolean b = jsxxService.doCheckCzqx(jsbh,sjcd_map.get("MKBH")+"");
					sjcd_map.put("operate", b);
					sjcd_map.put("idx", k);
				}
				ejcd_map.put("childList", sjcd);
				ejcd_map.put("size",sjcd.size());
				sjcdSize = sjcdSize +(sjcd.size()==0?0:sjcd.size());
				ejcd_map.put("idx", j);
			}
			map.put("size", sjcdSize);
			map.put("childList", ejcd);
		}
		mv.addObject("menu_list", yjcd);
		mv.addObject("jsxx",jsxx);
		mv.setViewName("systemset/qxgl/jsgl/jsgl_czqx_list");
		return mv;
	}
	
	/**
	 * 角色管理：保存权限信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSaveCzqx",produces = "text/html;charset=utf-8")
	@ResponseBody
	
	public Object doSaveCzqx() throws Exception{
		String mkbh = this.getPageData().getString("MKBH");
		String jsbh = this.getPageData().getString("RYBH");
		boolean isSuccess=jsxxService.saveJsCzqx(mkbh,jsbh);
		if(isSuccess)
			return "{\"success\":\"true\",\"msg\":\"保存成功！\"}";
		else 
			return "{\"success\":\"false\",msg\":\"保存失败！\"}";
	}
	
	/**
	 * 角色管理（角色用户）：添加新的用户，与角色绑定
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getYhxxPage")
	public ModelAndView getYhxxPage() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		String jsbh=this.getPageData().getString("jsbh");
		mv.setViewName("systemset/qxgl/jsgl/jsgl_jsyh_list");
		mv.addObject("operateType", operateType);
		mv.addObject("jsbh",jsbh);
		return mv;
	}
	
	/**
	 * 角色管理（角色用户 列表）： 获取用户信息，单位信息，角色信息 列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getRyxxPageList")
	@ResponseBody
	public Object getRyxxPageList() throws Exception{
		String jsbh=this.getPageData().getString("search[regex]");
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("G.RYGH,G.XM,G.DWBH,DW.MC,D.JSBH,Z.JSMC,G.RYBH,");
		sqltext.append("'（'||G.RYGH||'）'||G.XM AS RYMC,");//人员名称拼接
		sqltext.append("'（'|| G.DWBH||'）'||DW.MC AS DWMC ");//单位名称拼接
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("RYGH");//主键
		pageList.setStrWhere("and D.JSBH='"+jsbh+"'");//where条件
		String searchValue = pd.getString("search[value]");
		if(Validate.noNull(searchValue)){
			pageList.setStrWhere(pageList.getStrWhere()+" and (G.RYGH like '%"+CommonUtils.StringFilter(searchValue)+"%' or G.XM like '%"+CommonUtils.StringFilter(searchValue)+"%') ");
		}
		pageList.setHj1("");//合计
		pd.remove("search[value]");
		pageList.setTableName(TnameU.JSRYB+" D left join "+TnameU.JSB+" Z on D.JSBH=Z.JSBH left join GX_SYS_RYB G on D.RYBH=G.RYBH left join GX_SYS_DWB DW  on G.DWBH = DW.DWBH ");//表名
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 角色管理（角色用户 列表）：增加用户，实现与角色关联
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doSaveRy",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveRy() throws Exception{
		String jsbh=this.getPageData().getString("jsbh");
		String rybh=this.getPageData().getString("rybh");
		int i = jsxxService.doSaveRy(rybh,jsbh);
		if(i==1){
			return "{\"success\":\"true\",\"jsbh\":\""+jsbh+"-"+rybh+"\",\"msg\":\"添加成功！\"}";
		}else if(i==0){
			return "{\"success\":\"false\",\"jsbh\":\""+jsbh+"-"+rybh+"\",\"msg\":\"该角色下已经有该人员！\"}";
		}else{
			return "{\"success\":\"false\",\"jsbh\":\""+jsbh+"-"+rybh+"\",\"msg\":\"添加失败！\"}";
		}
	}
	
	/**
	 * 角色管理（角色用户 列表）：删除用户，实现与角色解绑
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDeleteRy",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDeleteRy() throws Exception{
		String rybh=this.getPageData().getString("rybh");
		String jsbh=this.getPageData().getString("jsbh");
		boolean isSuccess=jsxxService.doDeleteRy(rybh,jsbh);
		if(isSuccess)
			return "{\"success\":\"true\",\"msg\":\"取消授权成功！\"}";
		else 
			return "{\"success\":\"false\",\"msg\":\"取消授权失败！\"}";
	}
	
	/**
	 * 导出角色信息Excel
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
		String searchValue = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" SELECT D.jsbh as jsbh,'（'||D.jsbh||'）'||D.jsmc as jsmc,D.bz,case D.zt when 0 then '禁用' when 1 then '启用' end as zt,");
		sqltext.append(" case D.xgbz when 0 then '用户添加' when 1 then '系统定义' end as xgbz");
		sqltext.append(" FROM  "+TnameU.JSB+" D WHERE 1=1 ");
		if(Validate.noNull(searchValue)&&!searchValue.equals("undefined")){
			sqltext.append(" and (D.JSBH like '%"+CommonUtils.StringFilter(searchValue)+"%' or D.JSMC like '%"+CommonUtils.StringFilter(searchValue)+"%') ");
		}else{
			sqltext.append("");//where条件
		}
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" AND D.JSBH IN ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" ORDER BY JSBH ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("jsmc");
		m1.setShowname("角色名称");
		mlist.add(m1);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("zt");
		m2.setShowname("状态");
		mlist.add(m2);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 * 导出角色用户Excel
	 */
	@RequestMapping(value="/expExcelr",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcelr(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchValue = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" SELECT G.RYGH,G.XM,G.DWBH,DW.MC,D.JSBH,Z.JSMC,G.RYBH,");
		sqltext.append("'（'||G.RYGH||'）'||G.XM AS RYMC,");//人员名称拼接
		sqltext.append("'（'|| G.DWBH||'）'||DW.MC AS DWMC FROM  "+TnameU.JSRYB+" D left join "+TnameU.JSB+" Z on D.JSBH=Z.JSBH left join GX_SYS_RYB G on D.RYBH=G.RYBH left join GX_SYS_DWB DW  on G.DWBH = DW.DWBH WHERE 1=1");//单位名称拼接
		if(Validate.noNull(searchValue)){
			sqltext.append(" and (G.RYGH like '%"+CommonUtils.StringFilter(searchValue)+"%' or G.XM like '%"+CommonUtils.StringFilter(searchValue)+"%') ");
		}else{
			sqltext.append("");//where条件
		}
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" AND G.RYBH IN ('"+id.replace(",", "','")+"') ");
		}
		String jsbh = pd.getString("jsbh");
		if(Validate.noNull(jsbh)){
			sqltext.append(" and D.JSBH='"+jsbh+"' ");
		}
		sqltext.append(" ORDER BY G.RYBH ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("rygh");
		m1.setShowname("工号");
		mlist.add(m1);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("rymc");
		m2.setShowname("姓名");
		mlist.add(m2);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("dwmc");
		m3.setShowname("单位名称");
		mlist.add(m3);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 * 导出人员信息Excel   wzd
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String guid = pd.getString("id");
		String jsbh = pd.getString("jsbh");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.jsxxService.expExcel(realfile, shortfileurl, guid,searchValue,jsbh);
	}
	/**
	 * 启用禁用
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/qyjy",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public String qyjy(HttpServletRequest request,HttpServletResponse response)
	{
		StringBuffer msg = new StringBuffer();//存放错误信息
		String jsbh = this.getPageData().getString("jsbh");
		String type = this.getPageData().getString("type");
		int result = 0;
		result = jsxxService.qyjy(jsbh,type);
		if(result>0){
			msg.append("操作成功");
			return "{\"success\":true,\"msg\":\"" + msg.toString() + "\"}";
		}else{
			msg.append("操作失败");
			return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
		}
	}
}
