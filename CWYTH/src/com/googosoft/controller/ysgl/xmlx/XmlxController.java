package com.googosoft.controller.ysgl.xmlx;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.xmlx.XmlxService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/***
 * 项目类型控制类
 * @author LF
 *
 */
@Controller
@RequestMapping(value="/xmlx")
public class XmlxController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;
	@Resource(name="dictService")
	private DictService dictService;//单例
	@Resource(name="xmlxService")
	private XmlxService xmlxService;//单例
	/**
	 * 获取项目类型列表页面
	 * @return
	 */
	@RequestMapping(value="/goXmlxListPage")
	public ModelAndView goXsxxListPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/xmsz/xmlx/xmlx_list");
		return mv;
	}
	/**
	 * 获取项目类型信息列表数据
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session){
		String sszt = Constant.getztid(session);
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		sqltext.append(" * ");
		tablename.append("( select x.guid,x.xmlxbh,x.xmlxmc,x.yslx,(select d.mc from gx_sys_dmb d where d.zl='YSLX' and d.dm=x.yslx)as yslxmc,(select d.mc from gx_sys_dmb d where d.zl='"+Constant.XMLB+"' and d.dm=x.xmlb)as xmlbmc"
				+ ",x.sfczzc,(case x.sfczzc when '1' then '是' when '0' then '否' else '' end )as sfczzcmc,"
				+ "x.xmszsm,x.bz from CW_XMLXB x where sszt='"+sszt+"' ) k ");
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
	
	@RequestMapping(value="/getPageListTc")
	@ResponseBody
	public Object getPageListTc(HttpSession session){
		String sszt = Constant.getztid(session);
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		sqltext.append(" * ");
		tablename.append("( select x.guid,x.xmlxbh,'('||x.xmlxbh||')'||x.xmlxmc as xmlxmc,x.yslx,(select d.mc from gx_sys_dmb d where d.zl='YSLX' and d.dm=x.yslx)as yslxmc"
				+ ",x.sfczzc,(case x.sfczzc when '1' then '是' when '0' then '否' else '' end )as sfczzcmc,"				
				+ "x.xmszsm,x.bz from CW_XMLXB x  where x.sszt='"+sszt+"' ) k ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");

		String rybh = LUser.getRybh();//当前登陆者的人员编号
		pageList.setStrWhere(""); //获取管理单位权限
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	  
		pageList = pageService.findWindowList(pd,pageList,"XMLX");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping("/getPageList2")
	@ResponseBody
	public Object getPageList2() {
		String datas = SendHttpUtil.sendPost(SystemSet.address+"/xmlx/getPageList", "");
		List<Map<String, Object>> list = SendHttpUtil.getResultToList(datas);
		PageInfo pageInfo = new PageInfo("1", ""+list.size(), ""+list.size(), datas);
		return pageInfo.toJson();
	}
	
	@RequestMapping(value="/goXmlxAddPage")
	public ModelAndView goXmlxAddPage(){
		ModelAndView mv = this.getModelAndView();
		String url = SystemSet.address+"/demo/dict";
		String yslx = SendHttpUtil.sendPost(url, "zl="+Constant.YSLX);
		String xmlb = SendHttpUtil.sendPost(url, "zl="+Constant.XMLB);
		mv.addObject("xmlbList",xmlb);
		mv.addObject("yslxlist", yslx);
		mv.setViewName("/ysgl/xmsz/xmlx/xmlx_add");
		return mv;
	}
	/**
	 * C增加 U修改 L查看
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		String guid = pd.getString("guid");
		//预算类型
		List<Map<String, Object>> yslx = dictService.getDict(Constant.YSLX);
		//项目类别
		List<Map<String, Object>> xmlb = dictService.getDict(Constant.XMLB);
		//项目类别
		List<Map<String, Object>> fjlx = dictService.getDict(Constant.JCWJLX);
		
		mv.addObject("yslxlist", yslx);
		mv.addObject("fjlxlist", fjlx);
		mv.addObject("xmlblist", xmlb);
		
		if(operateType.equals("C")){
			String guid12 = this.get32UUID();//主表guid
			Map<String,String> map = new HashMap<String,String>();
			List xmlbmap = dictService.getJcwjlx(Constant.JCWJLX);
			mv.addObject("xmlbmap", xmlbmap);
			map.put("SYSBZ", "1");
			map.put("SFXY", "1");
			map.put("DWZT", "1");
			map.put("SYSMJ", "0.00");
			mv.addObject("dwb", map);
			mv.addObject("guid1",guid12);
		}else if(operateType.equals("U")||operateType.equals("L")){
			//数据库值
			Map<?, ?>  map = xmlxService.getInfoById(guid);
			//预算类型
			List yslxmap = dictService.getDict(Constant.YSLX);
			mv.addObject("yslxmap", yslxmap);
			//项目类别
			List xmlbmap = dictService.getDict(Constant.XMLB);
			mv.addObject("xmlbmap", xmlbmap);
			mv.addObject("map", map);
			mv.addObject("guid", guid);
		}
	
		mv.setViewName("ysgl/xmsz/xmlx/xmlx_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 编辑页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage1111")
	public ModelAndView goEditPage1111(HttpSession session){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		String kjzd=CommonUtil.getKjzd(session);
		//预算类型
		List<Map<String, Object>> yslx = dictService.getDict(Constant.YSLX);
		//项目类别
		List<Map<String, Object>> xmlb = dictService.getDict(Constant.XMLB);
		mv.addObject("yslxlist", yslx);
		mv.addObject("xmlblist", xmlb);
		List xmlbmap = dictService.getJcwjlx(Constant.JCWJLX);
		mv.addObject("xmlbmap", xmlbmap);
			//数据库值
			Map<?, ?>  map = xmlxService.getInfoById(guid);
			
			
			List<Map<String, Object>> srmap  = xmlxService.getsrkmById(guid,kjzd);
			List<Map<String, Object>> zcmap  = xmlxService.getzckmById(guid,kjzd);
			List<Map<String, Object>> jjflmap  = xmlxService.getjjflkmById(guid);
			List<Map<String, Object>> fjszmap  = xmlxService.getfjszById(guid);
			int max = fjszmap.size();
			//把map里面的值传到前台
			mv.addObject("map", map);
			mv.addObject("srmap",srmap);
			mv.addObject("zcmap",zcmap);
			mv.addObject("jjflmap",jjflmap);
			mv.addObject("fjszmap",fjszmap);
			mv.addObject("guid", guid);
			mv.addObject("max", max);
		
	
		mv.setViewName("ysgl/xmsz/xmlx/xmlx_editpage");
		
		return mv;
	}
	/**
	 * 查看页面
	 * @return
	 */
	@RequestMapping(value="/goLookPage")
	public ModelAndView goLookPage(HttpSession session){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");	
		String kjzd=CommonUtil.getKjzd(session);
		//预算类型
		List<Map<String, Object>> yslx = dictService.getDict(Constant.YSLX);
		//项目类别
		List<Map<String, Object>> xmlb = dictService.getDict(Constant.XMLB);
		mv.addObject("yslxlist", yslx);
		mv.addObject("xmlblist", xmlb);
			//数据库值
			Map<?, ?>  map = xmlxService.getInfoById(guid);
			
		
			List<Map<String, Object>> srmap  = xmlxService.getsrkmById(guid,kjzd);
			List<Map<String, Object>> zcmap  = xmlxService.getzckmById(guid,kjzd);
			List<Map<String, Object>> jjflmap  = xmlxService.getjjflkmById(guid);
		
			mv.addObject("map", map);
			mv.addObject("srmap",srmap);
			mv.addObject("zcmap",zcmap);
			mv.addObject("jjflmap",jjflmap);
			mv.addObject("guid", guid);
		
	
		mv.setViewName("ysgl/xmsz/xmlx/xmlx_lookpage");
		
		return mv;
	}
	/**
	 * 会计科目设置树
	 */
	@RequestMapping(value="/mainKjkmszTree")
	public ModelAndView goMainKjkmszTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");

		mv.addObject("controlId",controlId);
		mv.addObject("controlId1",controlId1);

		mv.setViewName("ysgl/xmsz/xmlx/mainKjkmszTree");
		return mv;
	}
	/**
	 * 经济科目设置树
	 */
	@RequestMapping(value="/mainJjkmszTree")
	public ModelAndView gomainJjkmszTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");

		mv.addObject("controlId",controlId);
		mv.addObject("controlId1",controlId1);

		mv.setViewName("ysgl/xmsz/xmlx/mainJjkmTree");
		return mv;
	}
	/**
	 * 获取会计科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/goKmszPage1")
	public ModelAndView goDdbPage1(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String treesearch = pd.getString("treesearch");
		mv.setViewName("ysgl/xmsz/xmlx/srkmxxList");
		mv.addObject("treesearch", treesearch);
		mv.addObject("dm", dm);
		mv.addObject("jb", jb);
		return mv;
	}
	/**
	 * 获取经济科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/goJjkmWindowPage")
	public ModelAndView goJjkmWindowPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("ysgl/xmsz/xmlx/jjkmxxList");
		mv.addObject("controlId",pd.getString("controlId"));
		mv.addObject("controlId1",pd.getString("controlId1"));
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	
	
	/**
	 * 保存
	 * @param dwb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doSave(GX_SYS_DWB dwb,HttpSession session){
		PageData pd = this.getPageData();
		
		String ss = dwb.getFjbh();
		String guid = this.getRequest().getParameter("guid");	
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		String rybh = LUser.getRybh();
		if("C".equals(operateType))//新增
		{
			
			//判断项目类型编号是否重复
			boolean checkbmh=xmlxService.doCheckDwbh(pd.getString("xmlxbh"));
			
			if(checkbmh==false)
			{
				return  "{success:false,msg:'项目类型编号不可重复!'}";
			}
			
			
			//判断附件类型编号是否重复
			boolean fjbh=xmlxService.doCheckDwbh(pd.getString("fjbh"));
			
			if(checkbmh==false)
			{
				return  "{success:false,msg:'项目类型编号不可重复!'}";
			}
			
			System.err.println("controller层里面的保存方法！");
			result = xmlxService.doAdd(pd,rybh,session);
			if(result==1){
				return  "{\"success\":true, \"msg\":'信息保存成功！'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = xmlxService.doUpdate(pd,guid,session);
			if(result==1)
			{
				return "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
	        return	MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	/**
	 * 验证项目类型是否正被使用
	 */
	@RequestMapping(value="/selectXmlx",produces = "text/html;chartset=UTF-8")
	@ResponseBody
	public Object selectXmlx() {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("dwbh");
		boolean yzxmlx = xmlxService.selectXmlx(guid);
		Gson gson = new Gson();
		return gson.toJson(yzxmlx);
	}
	
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
    	int k = xmlxService.doDelete(dwbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	/**
	 * 批量删除
	 * @return
	 */
	@RequestMapping(value="/doDeletePL",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDeletePL(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
    	int k = xmlxService.doDelete(dwbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	
	//初始化下拉框数据
	public void initialSelect(ModelAndView mv) {
		String url = SystemSet.address+"/demo/dict";
		String data = SendHttpUtil.sendPost(url, "zl="+Constant.ZJLY);
		url = SystemSet.address+"/getLoginID";
		String loginID = SendHttpUtil.sendPost(url, "rybh="+LUser.getRybh());
		mv.addObject("zjlyList",SendHttpUtil.getResultToList(data));
		mv.addObject("loginID",loginID);
	}
	/**
	 * 增加三种科目
	 * @return
	 */
	@RequestMapping(value="/addmorekm",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object addmorekm(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
	    
		xmlxService.addmorekm(session,list);
		
		b="success";
		return b;
	}
	@RequestMapping(value="/addsr",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object addsr(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		String sszt = Constant.getztid(session);
		String kjzd=CommonUtil.getKjzd(session);
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for(int i=0;i<list.size();i++) {
			Map map=(Map) list.get(i);
			System.err.println("即将执行收入方法！");
			xmlxService.addsr(session,map);
			
			Map map1 = new HashMap<>();
			String czr = LUser.getGuid();
			String guid = this.get32UUID();
			map1.put("guid", guid);
			map1.put("ywid", map.get("kmbh"));
			map1.put("czid", czr);
			map1.put("czr", czr);
			map1.put("kmlx", "1");
			map1.put("zbid", map.get("xmlxbh"));
			map1.put("sszt", sszt);
			
			xmlxService.doAddjwjl(map1,kjzd);
			
		}
		
		
		
		return 0;
		
		
	}
	@RequestMapping(value="/addzc",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
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
			System.err.println("即将执行支出方法！");
			xmlxService.addzc(session,map);
			
			Map map1 = new HashMap<>();
			String czr = LUser.getGuid();
			String guid = this.get32UUID();
			map1.put("guid", guid);
			map1.put("ywid", map.get("kmbh"));
			map1.put("czid", czr);
			map1.put("czr", czr);
			map1.put("kmlx", "1");
			map1.put("zbid",map.get("xmlxbh"));
			map1.put("sszt", sszt);
			
			xmlxService.doAddjwjl(map1,kjzd);
		}
		
		return 0;
		
		
	}
	
	//检查项目编号是否存在
		@RequestMapping("/checkXmmcExist")
		@ResponseBody
		public Object checkXmmcExist() {
			if(xmlxService.checkXmmcExist(this.getPageData())) {
				return "{\"exist\":true}";
			}else {
				return "{\"exist\":false}";
			}
		}
	
	@RequestMapping(value="/addfjsz1",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addfjsz1(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		String sfss = null;
		for(int i=0;i<list.size();i++) {
			int j = i+1;
			Map map=(Map) list.get(i);
			System.err.println("即将执行保存附件设置方法");
			xmlxService.addfjsz1(session,map,j);
	
		}
	return 0;
	}
//	@RequestMapping(value="/addfjsz1",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	@Transactional
//	public Object addfjsz1(HttpSession session)throws Exception{
//		PageData pd = this.getPageData();
//		String sszt = Constant.getztid(session);
//		String json = pd.getString("json");	//得到前台的json
//		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
//		Gson gson = new Gson();
//		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
//	
//		String sfss = null;
//		for(int i=0;i<list.size();i++){
//			Map map=(Map) list.get(i);
//			sfss=sfss+"','"+map.get("fjbh");
//		}
//        System.out.println("111111111111111111="+sfss);
//		//判断项目类型编号是否重复
//		String checkbmh=xmlxService.doCheckfjbh(sfss);
//		
//		if(checkbmh=="1")
//		{	
//			System.err.println("bbbbbbbbbbbbbbbbbbbbbbbbb"+checkbmh);
////			return  "{success:false,msg:'附件编号不可重复!'}";
//			return "{\"success\":\"false\",\"msg\":\"附件编号不可重复!\"}";
//		}
//		
//		for(int i=0;i<list.size();i++) {
//			int j = i+1;
//			Map map=(Map) list.get(i);
//			System.err.println("即将执行保存附件设置方法");
//			System.err.println("===fjszmap=add=="+map);
//			xmlxService.addfjsz1(session,map,j);
//			
//	
//		}
//	return 0;
//	}
	
	
	

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
			System.err.println("即将执行经济分类保存方法");
			xmlxService.addjjfl(session,map);
			
			Map map1 = new HashMap<>();
			String czr = LUser.getGuid();
			String guid = this.get32UUID();
			map1.put("guid", guid);
			map1.put("ywid", map.get("kmbh"));
			map1.put("czid", czr);
			map1.put("czr", czr);
			map1.put("kmlx", "2");
			map1.put("zbid",map.get("xmlxbh"));
			map1.put("sszt",sszt);
			
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
		String xmlxbh=pd.getString("xmlxbh");
		xmlxService.deletejjfl(xmlxbh);
		for(int i=0;i<list.size();i++) {
			Map map=(Map) list.get(i);
			xmlxService.addjjfl(session,map);
			
		}
	return 0;
	}
	/**
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editfjsz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object editfjsz(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		String xmlxbh=pd.getString("xmlxbh");
		xmlxService.deletefjsz(xmlxbh);
		for(int i=0;i<list.size();i++) {
				int j=list.size();
				Map map=(Map) list.get(i);
				if((Validate.noNull(map.get("fjbh")))) {
					xmlxService.addfjsz(session,map,j);
				}
				
		}
		return 0;
	}
	
	
	
	@RequestMapping(value="/editsr",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object editsr(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		//删除
		String xmlxbh=pd.getString("xmlxbh");
		xmlxService.deletesr(xmlxbh);
		
			for(int i=0;i<list.size();i++) {
				Map map=(Map) list.get(i);
				xmlxService.addsr(session,map);
				
			
		}
		
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
		String xmlxbh=pd.getString("xmlxbh");
		xmlxService.deletezc(xmlxbh);
		for(int i=0;i<list.size();i++) {
			Map map=(Map) list.get(i);
			xmlxService.addzc(session,map);
			
		}
	return 0;
	}
	/**
	 * 获取会计科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getkjkmPageList")
	@ResponseBody
	public Object getkjkmPageList(HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);
		PageData pd = this.getPageData();
		 Calendar date = Calendar.getInstance();
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select distinct c.kmnd, c.sfmj, c.jb, c.bz,c.guid, c.kmbh, c.kmmc,  c.kmsx,  (select d.mc from gx_sys_dmb d where d.zl='kmsx' and d.dm=c.kmsx)as kmsxmc, c.zjf, c.yefx,(case c.yefx when '1' then '贷方' else '借方'end) as yefxmc, c.hslb,"
				+ " (select d.mc from gx_sys_dmb d where d.zl='hslb' and d.dm = c.hslb) as hslbmc, c.kmjc, c.qyf,(case c.qyf when '1' then '是' else '否'end) as qyfmc,"
				+ " c.sfwyh, (case c.sfwyh when '1' then '是' else '否'end) as sfwyhmc,c.sfjjflkm,(case c.sfjjflkm when '1' then '是' else '否'end) as sfjjflkmmc,"
				+ " c.sfgnflkm,(case c.sfgnflkm when '1' then '是' else '否'end) as sfgnflkmmc, c.bmhs,(case c.bmhs when '1' then '是' else '否'end) as bmhsmc, c.xmhs,"
				+ " (case c.xmhs when '1' then '是' else '否'end) as xmhsmc, c.czr, c.czrq from Cw_kjkmszb c where 1=1 and sszt='"+sszt+"' and kjzd='"+kjzd+"' and c.sjfl <> 'root'");
		if(Validate.noNull(dm)){
			tablename.append(" start with c.jb='"+jb+"' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root' ");

		}
		tablename.append(" ) k ");
//		if(Validate.noNull(dm)){
//			tablename.append(" where c. ");
//		}
//		if(Validate.noNull(jb)){
//			tablename.append(" where c. ");
//		}
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
	
		//设置WHERE条件
		pageList.setStrWhere(""); 
		pageList.setHj1("");//合计
	    //pageList = pageService.findPageList(pd,pageList);
		pageList = pageService.findWindowList(pd,pageList,"WWW");//引用传递

		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 获取经济科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getJjkmPageList")
	@ResponseBody
	public Object getJjkmPageList(HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		String treeDm = pd.getString("treeDm");
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("guid,j.kmnd,j.KMBH,j.KMMC,KMJC,L,K,SM,DECODE(QYF,'1','是','否')QYF");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		// 设置WHERE条件
		String strWhere = "";
		if(Validate.noNull(treeDm)){
			strWhere += " and (j.k='"+treeDm+"' or j.l='"+treeDm+"' or kmbh='"+treeDm+"')";
		}
		pageList.setStrWhere(strWhere);
		pageList.setTableName("CW_JJKMB j");//表名
		//pageList = pageService.findPageList(pd, pageList);
		pageList = pageService.findWindowList(pd,pageList,"JJKM");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/window")
	public ModelAndView window(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("ysgl/xmsz/xmlx/mainKjkmszTree");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 获取经济科目科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/jjflwindow")
	public ModelAndView jjflwindow(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("ysgl/xmsz/xmlx/mainJjkmTree");
		mv.addObject("treesearch", treesearch);
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
	 * 项目类型弹出窗
	 */
	@RequestMapping(value="/getxmlxall2")
	public ModelAndView getxmlxall2(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String id = pd.getString("id");
		String bmbh = pd.getString("bmbh");
		mv.addObject("bmbh",bmbh);
		mv.addObject("controlId",controlId);//名称
		mv.addObject("id",id);
		mv.setViewName("ysgl/xmsz/xmxx/xmlxall_window2");
		return mv;
	}
}
