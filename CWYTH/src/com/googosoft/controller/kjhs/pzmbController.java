package com.googosoft.controller.kjhs;


import java.util.ArrayList;
import java.util.Calendar;
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
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.TnameU;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.CW_JSXXB;
import com.googosoft.pojo.kjhs.Cw_pzmb;
import com.googosoft.pojo.kjhs.Cw_pzmbmx;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.JsxxsService;
import com.googosoft.service.jzmb.JzmbService;
import com.googosoft.service.kjhs.PzmbService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/pzmb")
public class pzmbController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="pzmbService")
	private PzmbService pzmbService;//单例
	

	@Resource(name="jzmbService")
	private JzmbService jzmbService;//单例
	
	
	/**
	 * 获取凭证模板列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gopzmbListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/pzmb_list");
		return mv;

	}
	
	/**
	 * 获取科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/kmxxList")
	public ModelAndView goDdbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String pname = pd.getString("pname");
		String controlId = pd.getString("controlId");
		String controlId2 = pd.getString("controlId2");				
		String kmsx = pd.getString("kmsx"); 
		String jb = pd.getString("jb");
		String sfmj = pd.getString("sfmj");
		String id = pd.getString("id");
		String kmnd = pd.getString("kmnd");
		String opre = pd.getString("opre");
		String kmjc = pd.getString("kmjc");
		String treesearch = pd.getString("treesearch");	
		mv.addObject("treesearch", treesearch);
		mv.addObject("dm", dm);
		mv.addObject("jb", jb);
		mv.addObject("sfmj", sfmj);
		mv.addObject("id", id);
		mv.addObject("kmnd",kmnd);
		mv.addObject("opre",opre);
		mv.addObject("kmsx",kmsx);
		mv.addObject("kmjc",kmjc);			
		mv.addObject("dm", dm);
		mv.addObject("pname", pname);
		mv.addObject("controlId", controlId);
		mv.addObject("controlId2", controlId2);
		String xzid = pd.getString("xzid");
		mv.addObject("xzid",xzid);
		mv.setViewName("kjhs/pzmb/kmxxList");
		return mv;
	}
	/**
	 * 增加凭证模板
	 * @return
	 */
	@RequestMapping(value="/addPzmb123",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addJbzcys(Cw_pzmb pzmb,HttpSession session)throws Exception{
		int i=0;
		String b="";
		
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		
		String mbnr = pd.getString("mbnr");
		String pzzy = pd.getString("pzzy");
		String bz = pd.getString("bz");
		String ztid = Constant.getztid(session);
		pzmb.setBz(bz);
		pzmb.setPzzy(pzzy);
		pzmb.setGuid(guid);
		pzmb.setMbnr(mbnr);
		pzmb.setSszt(ztid);
		i = pzmbService.doAddpzmb(pzmb);
		if(i==1){
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}else{
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}
	/**
	 * 编辑凭证模板
	 * @return
	 */
	@RequestMapping(value="/editPzmb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object editPzmb(Cw_pzmb pzmb)throws Exception{
		int i=0;
		String b="";
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String mbbh = pd.getString("mbbh");
		String mbnr = pd.getString("mbnr");
		String bz = pd.getString("bz");
		String pzzy = pd.getString("pzzy");
		
		pzmb.setBz(bz);
		pzmb.setBz(pzzy);
		pzmb.setGuid(guid);
		pzmb.setMbbh(mbbh);
		pzmb.setMbnr(mbnr);
		
		i = pzmbService.doAddpzmb(pzmb);
		if(i==1){
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}else{
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}
	/**
	 * 增加凭证模板明细
	 * @return
	 */
	@RequestMapping(value="/addPzmbmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addJbzcys(Cw_pzmbmx pzmbmx,HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		String jbzcbh = pd.getString("jbzcbh");
		String sszt = Constant.getztid(session);
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = this.get32UUID();//创建主键
			
			String mbbh = (String) map.get("mbbh1");
			String kmbh = (String) map.get("kmbh");
			String zy = Validate.isNullToDefaultString(map.get("zy"), "");
			    
			pzmbmx.setGuid(guid);
			pzmbmx.setMbbh(mbbh);
			pzmbmx.setKmbh(kmbh);
			pzmbmx.setSszt(sszt);
			pzmbmx.setZy(zy);
			pzmbmx.setXh(i+1);
				//增加
				pzmbService.doAddpzmbmx(pzmbmx);
				String czr = LUser.getGuid();
				String guid2 = this.get32UUID();
				Map map1 = new HashMap<>();
				map1.put("guid", guid2);
				map1.put("ywid", kmbh);
				map1.put("czid",czr );
				map1.put("czr", czr);
				map1.put("kmlx", "1");
			    map1.put("zbid", mbbh);
			    map1.put("sszt", sszt);
			    map1.put("xh", i+1);
				
				jzmbService.doAddjwjl(map1);
				
			
		}
		b="success";
		return b;
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
		String xzid = pd.getString("xzid");
		mv.addObject("xzid",xzid);
		mv.setViewName("kjhs/pzmb/window");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 获取科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/addpzmb")
	public ModelAndView addpzmb(){
		ModelAndView mv = this.getModelAndView();
//		String mbbh = GenAutoKey.makeCkbh("Cw_pzmbzb", "mbbh", "2");
		String guid = this.get32UUID();
		mv.addObject("guid",guid);
//		mv.addObject("mbbh", mbbh);	
		mv.setViewName("kjhs/pzmb/pzmb_add");
		return mv;
	}
	/**
	 * 验证模板编号是否重复
	 */
	@RequestMapping(value="/selectMbbh",produces = "text/html;chartset=UTF-8")
	@ResponseBody
	public Object selectMbbh() {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		String mbbh = pd.getString("mbbh");
		boolean yzmbbh = pzmbService.getObjectByMbbh(guid,mbbh);
		Gson gson = new Gson();
		return gson.toJson(yzmbbh);
	}
	
	/**
	 * 获取科目弹窗信息列表数据
	 */
	@RequestMapping(value="/kmxx")
	@ResponseBody
	public Object kmxx(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String dm=pd.getString("dm");
		sqltext.append("GUID,fyfl,kmmc,kmlbdm");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setTableName("Cw_kjkmsz k ");//表名
		pageList.setStrWhere(" AND k.kmjdm = '"+dm+"'");
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	

	/**
	 * 获取凭证模板列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {	
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" GUID,MBBH,MBNR,BZ,PZZY,CZR,CZRQ ,u1," );
		sqltext.append(" (select wm_concat(to_char(m.zy)) from cw_pzmbmxb m where m.mbbh = a.guid) as zy," );
		sqltext.append(" (select wm_concat(to_char(m.kmbh)) from cw_pzmbmxb m where m.mbbh = a.guid) as kmbh," );
		sqltext.append(" (select wm_concat(to_char(m.kmmc)) from cw_kjkmszb m left join cw_pzmbmxb b on b.kmbh = m.kmbh where  b.mbbh = a.guid) as kmmc");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("and sszt ='"+sszt+"'");
		pageList.setTableName("CW_PZMBZB a");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "",
				pageList.getTotalList().get(0).get("NUM") + "", gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	
	/**
	 * 修改凭证模板
	 * @return
	 */
	@RequestMapping(value="/updatePzmb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object updatePzmb(Cw_pzmb pzmb)throws Exception{
		int i=0;
		String b="";
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String mbbh = pd.getString("mbbh");
		String mbnr = pd.getString("mbnr");
		String bz = pd.getString("bz");
		String pzzy = pd.getString("zy");
		pzmb.setGuid(guid);
		pzmb.setBz(bz);
		pzmb.setPzzy(pzzy);
		pzmb.setMbbh(mbbh);
		pzmb.setMbnr(mbnr);
		
		i = pzmbService.doupdatepzmb(pzmb);
		if(i==1){
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}else{
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		
		
		return b;
	}
	
	/**
	 * 编辑项目支出预算明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editpzmbmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editpzmbmx(Cw_pzmbmx pzmbmx,HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String mbbh = pd.getString("mbbh");
		String sszt = Constant.getztid(session);
		pzmbService.dodeletepzmbmx(mbbh);
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (int j=0;j<list.size();j++) {
			Map map = (Map) list.get(j);//将list转为map
			String guid = this.get32UUID();
			 mbbh = (String) map.get("mbbh1");
			String kmbh = (String) map.get("kmbh");
			String zy = Validate.isNullToDefaultString(map.get("zy"),"");
			
			    //将字段放入pzmbmx
			
			pzmbmx.setGuid(guid);
			pzmbmx.setKmbh(kmbh);
			pzmbmx.setMbbh(mbbh);
			pzmbmx.setSszt(sszt);
			pzmbmx.setZy(zy);
			pzmbmx.setXh(j+1);
			
			i=pzmbService.doAddpzmbmx(pzmbmx);
			//i=pzmbService.doupdatepzmbmx(pzmbmx);
			
		}
		b="success";
		return b;
	}
	
	/**
	 * 跳转到项目支出预算信息编辑页面
	 * @return
	 */
	@RequestMapping(value="/goeditpzmb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goXmzcysEditPage( HttpSession session){
		PageData pd = this.getPageData();		
		String kjzd = CommonUtil.getKjzd(session);
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		
		mv.addObject("guid",guid);
		Map<?, ?> map = pzmbService.getpzmbById(guid);
		
		List list = pzmbService.getpzmbmxById(guid,kjzd);
		mv.addObject("pzmb", map);
		if(list.size()>0) {
			mv.addObject("pzmbmx",list);
		}
		
		mv.setViewName("kjhs/pzmb/pzmb_edit");
	return mv;

	}
	/**
	 * 跳转到查看
	 * @return
	 */
	@RequestMapping(value="/goLookPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goLookPage(HttpSession session){
		PageData pd = this.getPageData();
		String kjzd = CommonUtil.getKjzd(session);
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		
		mv.addObject("guid",guid);
		Map<?, ?> map = pzmbService.getpzmbById(guid);
		
		List list = pzmbService.getpzmbmxById(guid,kjzd);
		mv.addObject("pzmb", map);
		if(list.size()>0) {
			mv.addObject("pzmbmx",list);
		}
		
		mv.setViewName("kjhs/pzmb/pzmb_look");
	return mv;

	}
	/**
	 * 凭证模板批量删除
	 */
	@RequestMapping(value="/pzmbsDel",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object pzmbsDel() {
		String b="";
		int i=0;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		
		i = pzmbService.doDeletes(guid);
		if(i>0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
		
	}
	/**
	 * 凭证模板导出表
	 * @return
	 */
	//项目支出预算导出excel
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcelXmzcys(HttpSession session){
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
		String sszt = Constant.getztid(session);
		sqltext.append("select GUID,MBBH,MBNR,BZ,PZZY,CZR,CZRQ ,"
				+ "(select wm_concat(to_char(m.kmbh)) from cw_pzmbmxb m where m.mbbh = a.guid) as kmbh,"
				+ "(select wm_concat(to_char(m.kmmc)) from cw_kjkmszb m left join cw_pzmbmxb b on b.kmbh = m.kmbh where  b.mbbh = a.guid) as kmmc"
				+ " from cw_pzmbzb a  where 1=1 and sszt ='"+sszt+"'");
		sqltext.append(ToSqlUtil.jsonToSql(searchValue));
		String guid = pd.getString("id");
		if(Validate.noNull(guid)){
			sqltext.append(" and a.guid in ('"+guid.replace(",", "','")+"') ");
		}
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("mbbh");
		m2.setShowname("模板编号");
		mlist.add(m2);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("mbnr");
		m3.setShowname("模板名称");
		mlist.add(m3);
		M_largedata m7 = new M_largedata();
		m7.setColtype("String");
		m7.setName("PZZY");
		m7.setShowname("凭证摘要");
		mlist.add(m7);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("kmbh");
		m4.setShowname("科目编号");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("kmmc");
		m5.setShowname("科目名称");
		mlist.add(m5);
		M_largedata m6 = new M_largedata();
		m6.setColtype("String");
		m6.setName("bz");
		m6.setShowname("备注");
		mlist.add(m6);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	//导出
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpSession session) {
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String guid = pd.getString("id");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.pzmbService.expExcel(realfile, shortfileurl,searchValue,guid,sszt);
	}
	/**
	 * 获取会计科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList1")
	@ResponseBody
	public Object getPageList1(HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);//获取使用的会计制度

		PageData pd = this.getPageData();
		 Calendar date = Calendar.getInstance();
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select c.kmnd, c.sfmj, c.jb, c.bz,c.guid, c.kmbh, c.kmmc,  c.kmsx,  (select d.mc from gx_sys_dmb d where d.zl='kmsx' and d.dm=c.kmsx)as kmsxmc, c.zjf, c.yefx,(case c.yefx when '1' then '贷方' else '借方'end) as yefxmc, c.hslb,"
				+ " (select d.mc from gx_sys_dmb d where d.zl='hslb' and d.dm = c.hslb) as hslbmc, c.kmjc, c.qyf,(case c.qyf when '1' then '是' else '否'end) as qyfmc,"
				+ " c.sfwyh, (case c.sfwyh when '1' then '是' else '否'end) as sfwyhmc,c.sfjjflkm,(case c.sfjjflkm when '1' then '是' else '否'end) as sfjjflkmmc,"
				+ " c.sfgnflkm,(case c.sfgnflkm when '1' then '是' else '否'end) as sfgnflkmmc, c.bmhs,(case c.bmhs when '1' then '是' else '否'end) as bmhsmc, c.xmhs,"
				+ " (case c.xmhs when '1' then '是' else '否'end) as xmhsmc, c.czr, c.czrq from Cw_kjkmszb c where 1=1 and kjzd='"+kjzd+"' and sszt='"+sszt+"' and c.sjfl <> 'root' and sfmj='1'");
		if(Validate.noNull(dm)){
			tablename.append(" start with c.sjfl='"+jb+"' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root' ");
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
	
}
