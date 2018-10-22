package com.googosoft.controller.ysgl.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.LUser;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.ysgl.jjkmybmdysz;
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.jcsz.JjkmybmdyszService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value ="/Jjkmybmdysz")
public class JjkmybmdyszController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="jjkmybmdyszService")
	private JjkmybmdyszService jjkmybmdyszService;
	
	@RequestMapping(value = "/goJjkmybmdyszListPage")
	public ModelAndView goListPage() {
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/jcsz/jjkmybmdysz/jjkmybmdysz_list");
		return mv;

	}
	/**
	 * 列表页面数据
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/getPageList")
//	@ResponseBody
//	public Object getPageList() throws Exception {
//		PageData pd = this.getPageData();
//		PageList pageList = new PageList();
//		String treeid=pd.getString("treeid");
//		System.out.println("^v^"+treeid);
//		String datas="",strnum="";
//		if(treeid.equals("")){
//			datas = SendHttpUtil.sendPost(SystemSet.address+"/jjkmybmdysz/getPageList", "");
//		    strnum = SendHttpUtil.sendPost(SystemSet.address+"/jjkmybmdysz/getCount", "");	
//		}else{
//			datas = SendHttpUtil.sendPost(SystemSet.address+"/jjkmybmdysz/getWhereList","treeid="+treeid.trim());
//			System.out.println("datas+++++++++++++++++++"+datas);
//		    strnum = SendHttpUtil.sendPost(SystemSet.address+"/jjkmybmdysz/getWhereCount","treeid="+treeid.trim());
//		}
//		
//		strnum=strnum.substring(8,strnum.indexOf("}"));
//		PageInfo pageInfo = new PageInfo((String) pd.get("draw"), strnum, strnum,datas);
//		return pageInfo.toJson();
//	}
	/**
	 * 获取经济科目与部门对应设置列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageLists",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getPageLists(){
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treeid");
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		
		sqltext.append(" * ");
		tablename.append(" (select guid,(SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.BMH=A.BMBH)AS BMBH,((SELECT  D.MC  FROM GX_SYS_DWB D  WHERE D.BMH = A.BMBH)) as bmmc,");
		tablename.append("(SELECT '('||B.KMBH||')'||B.KMMC FROM CW_JJKMB B WHERE B.KMBH=A.JJKMBH)AS JJKMBH,(select b.kmmc from CW_JJKMB B  WHERE B.KMBH = A.JJKMBH) as KMMC from cw_jjkmybmdyszb A"
				+ " where 1=1 ");
		if(Validate.noNull(dwbh)){
			if("000001".equals(dwbh)) {
				
			}else {
				tablename.append("AND A.bmbh=(select bmh from GX_SYS_DWB B where B.DWBH='"+dwbh+"') ");//根据左侧树查询右侧列表

			}
		}
		
		tablename.append(" )k ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("guid");
		//设置WHERE条件
		String strWhere = "";
		
		pageList.setStrWhere(strWhere);
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 删除单条经济科目与部门对应设置信息
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody	
	public Object doDelete(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		int b = jjkmybmdyszService.doDelete(guid);		
		if(b>0){
			return "{\"success\":\"true\",\"msg\":\"删除成功！\"}";    
		}else{
			return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
		
	}
	/**
	 * 增加经济科目与部门对应设置信息
	 * @return
	 */
	@RequestMapping(value="addJjkmybmdysz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addJjkmybmdysz(jjkmybmdysz bmxx)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		System.out.println("json++++++++++++++"+json);
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		System.out.println("ajson======"+ajson);
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = this.get32UUID();//创建主键
			
			String bmbh = (String) map.get("bmbh");
			String jjkmbh = (String) map.get("jjkmbh");
			System.out.println("===================打印jjkmbh========="+jjkmbh);
			System.out.println("bmbh===="+bmbh);
			    //将字段放入bmxx
			    bmxx.setGuid(guid);
			    bmxx.setBmbh(bmbh);
			    bmxx.setJjkmbh(jjkmbh);
				
				//增加
				jjkmybmdyszService.doAdd1(bmxx);
			
		}
		b="success";
		return b;
	}
	
	/**
	 * 获取经济科目信息弹窗列表的页面
	 * @return
	 */
	@RequestMapping(value="/jjkmList")
	public ModelAndView goDdbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String pname = pd.getString("pname");
		String controlId = pd.getString("controlId");
		mv.setViewName("ysgl/jcsz/jjkmybmdysz/jjkmList");
		mv.addObject("dm", dm);
		mv.addObject("pname", pname);
		mv.addObject("controlId", controlId);
		return mv;
	}
	/**
	 * 获取经济科目树
	 * @return
	 */
	@RequestMapping(value="/window",produces = "text/html;charset=UTF-8")
	public ModelAndView window(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("ysgl/jcsz/jjkmybmdysz/window");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 获取会计科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/gojjkmPage")
	public ModelAndView gojjkmPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("ysgl/jcsz/jjkmybmdysz/jjkmList");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 获取经济科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/jjkmPageList")
	@ResponseBody
	public Object jjkmPageList() throws Exception{
		PageData pd = this.getPageData();
	//	String treeDm = pd.getString("treeDm");
		String treeDm = pd.getString("dm");
		System.out.println("=======treeDm======="+treeDm);
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("j.KMBH,j.KMMC");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		if(Validate.noNull(treesearch)){
			//System.out.println(treesearch);
			pageList.setStrWhere(pageList.getStrWhere()+" and (j.zl in(select t.kmbh from cw_jjkmb t where  t.kmbh='"+treesearch.substring(1, treesearch.indexOf(")"))+"') )  ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		if(Validate.noNull(treeDm)){
			
				
		
				pageList.setStrWhere(pageList.getStrWhere()+" AND kmbh like '%"+treeDm+"%' or guid like '%"+treeDm+"%'");
		
		
				//	pageList.setStrWhere(pageList.getStrWhere()+" AND j.kmbh = (select kmbh from cw_jjkmb where k  is not null and kmbh='"+treeDm+"') or guid like '%"+treeDm+"%'");

			
		}
		pageList.setTableName("CW_JJKMB j");//表名
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 导出单位信息Excel
	 * @return
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
		//查询字段名
//		sqltext.append(" select * from (select  A.GUID AS GUID, "
//				+ "(SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.BMH=A.BMBH)AS BMBH,"
//				+"(SELECT '('||B.KMBH||')'||B.KMMC FROM CW_JJKMB B WHERE B.KMBH=A.JJKMBH)AS JJKMBH,"
//				+"A.CZR AS CZR,A.CZRQ AS CZRQ"
//				+"FROM cw_jjkmybmdyszb A  where 1=1)k where 1=1");
//		sqltext.append(ToSqlUtil.jsonToSql(searchValue));
//		String guid = pd.getString("id");
//		if(Validate.noNull(guid)){
//			sqltext.append(" and k.guid in ('"+guid.replace(",", "','")+"') ");
//		}
		sqltext.append(" SELECT");
		sqltext.append(" A.GUID AS GUID,");
		sqltext.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.BMH=A.BMBH)AS BMBH,");
		sqltext.append("(SELECT '('||B.KMBH||')'||B.KMMC FROM CW_JJKMB B WHERE B.KMBH=A.JJKMBH)AS JJKMBH,");
		sqltext.append(" A.CZR AS CZR,");
		sqltext.append(" A.CZRQ AS CZRQ");
		sqltext.append(" FROM cw_jjkmybmdyszb A  where 1=1 ");
		String dwbh = pd.getString("treeid");
		if(Validate.noNull(dwbh)){
			//点击左侧单位树的where条件
			sqltext.append("AND A.bmbh=(select B.bmh from GX_SYS_DWB B where B.DWBH='"+dwbh+"') ");
		}else {//单位权限
		sqltext.append(pageService.getQxsql(LUser.getRybh(), "A.CZR", "D"));
	}

	String id = pd.getString("BMBH");
	System.out.println("id++++++++++++"+id);
	if(Validate.noNull(id)){
		sqltext.append(" AND A.guid IN ('"+id+"') ");
	}
	sqltext.append(CommonUtils.jsonToSql(searchJson));
	sqltext.append(" ORDER BY A.BMBH ");
		//部门名称，经济科目名称
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("bmbh");
		m.setShowname("部门名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("jjkmbh");
		m.setShowname("经济科目名称");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	
}



