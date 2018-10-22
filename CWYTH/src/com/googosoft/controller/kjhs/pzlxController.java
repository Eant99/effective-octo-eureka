package com.googosoft.controller.kjhs;


import java.util.Calendar;
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
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.kjhs.CW_PZKMDZB;
import com.googosoft.pojo.kjhs.CW_PZLXB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWMXB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWSZ;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.PzlxService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;



@Controller
@RequestMapping(value="/pzlx")
public class pzlxController extends BaseController{	
	@Resource(name="pzlxService")
	PzlxService pzlxService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name="pageService")
	PageService pageService;
	
	/**
	 * 获取凭证类型列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gopzlxListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/pzlx/pzlx_list");
		return mv;
	}
	/**
	 * 获取凭证类型列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("p.guid,p.lxbh,p.lxmc,p.pzz,"
				+ "(select wm_concat(to_char(m.kmmc)) from cw_kjkmszb m left join cw_pzkmdzb b on b.kmbh = m.guid where b.xzlx = 'Jf' and b.pzlxm = p.guid) as jfbykm,"
				+ "(select wm_concat(to_char(m.kmmc)) from cw_kjkmszb m left join cw_pzkmdzb b on b.kmbh = m.guid where b.xzlx = 'Df' and b.pzlxm = p.guid) as dfbykm,"
				+ "(select wm_concat(to_char(m.kmmc)) from cw_kjkmszb m left join cw_pzkmdzb b on b.kmbh = m.guid where b.xzlx = 'Pzby' and b.pzlxm = p.guid) as pzbykm,"
				+ "(select wm_concat(to_char(m.kmmc)) from cw_kjkmszb m left join cw_pzkmdzb b on b.kmbh = m.guid where b.xzlx = 'Pzbw' and b.pzlxm = p.guid) as pzbwkm");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("CW_PZLXB p");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 验证凭证类型是否重复
	 */
	@RequestMapping(value="/selectLxmc",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object selectLxmc() {
		 PageData pd = this.getPageData();
		 ModelAndView mv = this.getModelAndView();
		 String lxmc = pd.getString("lxmc");
		 String guid = pd.getString("guid");
		 boolean yzlxmc = pzlxService.getObjectById(lxmc,guid);
		 Gson gson = new Gson();
		 return gson.toJson(yzlxmc);
		 
	}
	/**
	 * 验证凭证字是否重复
	 */
	@RequestMapping(value="/selectPzz",produces = "text/html;chartset=UTF-8")
	@ResponseBody
	public Object selectPzz() {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		String pzz = pd.getString("pzz");
		boolean yzpzz = pzlxService.getObjectByPzz(guid,pzz);
		Gson gson = new Gson();
		return gson.toJson(yzpzz);
	}
	/**
	 * 跳转凭证类型增加页面
	 * @return
	 */
	@RequestMapping(value="/goAddPage")
	public ModelAndView goAddPzlxPage(){
		ModelAndView mv = this.getModelAndView();
		String lxbh = GenAutoKey.makeCkbh("Cw_pzlxb", "lxbh", "2");
		System.err.println("___________________"+lxbh);
		//定义主键guid
		String guid =this.get32UUID();	
		//传guid到页面
		mv.addObject("guid",guid);
		mv.addObject("lxbh",lxbh);
		mv.setViewName("/kjhs/pzlx/pzlx_add");
		
		return mv;
	}
	
	/**
	 * 添加凭证类型信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addPzlx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object addPzlx(CW_PZLXB pzlxb,HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		System.out.println("sszt+++++++++++++"+sszt);
		String b = "";
		int i;
		int c;
		//String guid = this.get32UUID();
		String guid = pd.getString("guid");
		String lxbh = pd.getString("lxbh");
		String lxmc = pd.getString("lxmc");
		String pzz = pd.getString("pzz");
		//String sszt = pd.getString("sszt");
		pzlxb.setGuid(guid);
		pzlxb.setLxbh(lxbh);
		pzlxb.setLxmc(lxmc);
		pzlxb.setPzz(pzz);
		pzlxb.setSszt(sszt);
/*		c=pzlxService.doDelete(guid, pzlxb);
		List list = bmszService.getSrysSbbm(sbbm);
		int j = list.size();
		if(j!=0) {
			b = "{\"success\":\"false\",\"msg\":\"当前部门已经存在！\"}";

		}else {*/
			i= pzlxService.doAdd(pzlxb);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
//			}
		}		
		return b;
	}
	/**
	 * 添加凭证科目信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addPzkmdzb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object addPzkmdzb(CW_PZKMDZB pzkmdzb)throws Exception{
		PageData pd = this.getPageData();
		System.out.println("pd+++++++++++++++"+pd);
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		System.out.println("json+++++++++++++++"+json);
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		System.out.println("list=="+list);
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = this.get32UUID();//创建主键
			String pzlxm = (String) map.get("pzlxm");
			String kmbh = (String) map.get("kmbh");
			String xzlx = (String) map.get("xzlx");
			
			
		    //将字段放入pzkmdzb
			pzkmdzb.setGuid(guid);
			pzkmdzb.setPzlxm(pzlxm);
			pzkmdzb.setKmbh(kmbh);
			pzkmdzb.setXzlx(xzlx);	
			//增加
			pzlxService.doAdd1(pzkmdzb);
			
		}
		b="success";
		return b;
	}
	/**
	 * 跳转到凭证类型编辑页面
	 * @return
	 */
	@RequestMapping(value="/goEdit",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goEdit(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		
		mv.addObject("guid",guid);
		Map<?, ?> map = pzlxService.getpzlxById(guid);
		List listJf= pzlxService.getpzkmdzbJfById(guid);
		List listDf= pzlxService.getpzkmdzbDfById(guid);
		List listPzby= pzlxService.getpzkmdzbPzbyById(guid);
		List listPzbw= pzlxService.getpzkmdzbPzbwById(guid);
		mv.addObject("pzlx", map);
		if(listJf.size()>0) {
		mv.addObject("pzkmdzbJf",listJf);
		}
		if(listDf.size()>0) {
		mv.addObject("pzkmdzbDf",listDf);
		}
		if(listPzby.size()>0) {
		mv.addObject("pzkmdzbPzby",listPzby);
		}
		if(listPzbw.size()>0) {
		mv.addObject("pzkmdzbPzbw",listPzbw);
		}
		mv.setViewName("kjhs/pzlx/pzlx_edit");
	return mv;

	}
	/**
	 * 编辑凭证类型信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editPzlx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editWldwsz(CW_PZLXB pzlxb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		//String guid = this.get32UUID();
		String guid = pd.getString("guid");
		String lxbh = pd.getString("lxbh");
		String lxmc = pd.getString("lxmc");
		String pzz = pd.getString("pzz");
		String sszt = pd.getString("sszt");
		System.out.println("guid==="+guid);
		
		pzlxb.setGuid(guid);
		pzlxb.setLxbh(lxbh);
		pzlxb.setLxmc(lxmc);
		pzlxb.setPzz(pzz);
		
		i = pzlxService.doEdit(pzlxb);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		
		return b;
	}
	/**
	 * 编辑凭证科目对照信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editPzkmdz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editPzkmdz(CW_PZKMDZB pzkmdzb)throws Exception{
		System.out.println("1111111111111111111111111111111111");
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		System.out.println("json+++++++++++++"+json);
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		//String pzlxm = pd.getString("pzlxm");	
		System.out.println("list+++++++++++++++++"+list);
		//pzlxService.doDeletePzkmdzb(pzlxm,pzkmdzb);
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			//String guid = this.get32UUID();//创建主键
			String guid = (String) map.get("guid");
			System.out.println("guid+++++++++++++"+guid);

			String pzlxm = (String) map.get("pzlxm");
			String kmbh = (String) map.get("kmbh");
			String xzlx = (String) map.get("xzlx");
			System.out.println("pzlxm===="+pzlxm);
			System.out.println("xzlx===="+xzlx);
			System.out.println("kmbh===="+kmbh);
			
			
			
			if(guid.length()==0) {
				
				guid=this.get32UUID();
			}
			    //将字段放入pzkmdzb
			    pzkmdzb.setGuid(guid);
				pzkmdzb.setPzlxm(pzlxm);
				pzkmdzb.setKmbh(kmbh);
				pzkmdzb.setXzlx(xzlx);
				System.out.println("pzlxm%%%%%%%%%%%%%%%%%%%%%%%"+pzlxm);
				//删除
				//pzlxService.doDeletePzkmdzb(guid,pzkmdzb);
				//增加
				pzlxService.doAdd1(pzkmdzb);
			
		}
		b="success";
		return b;
	}
	/**
	 * 凭证类型编辑删除
	 */
	@RequestMapping(value="/delPzkmdz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object delPzkmdz() {
		String b;
		PageData pd = this.getPageData();
		String pzlxm = pd.getString("pzlxm");
		int i = 0;
		i = pzlxService.doDeletePzkm(pzlxm);
		b="";
		return b;
	}
	/**
	 * 凭证类型删除
	 */
	@RequestMapping(value="/pzlxDel",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object pzlxDel() {
		//String b;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		//int i = 0;
		int i = pzlxService.doDelete(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
//		b="";
//		return b;
	}
	/**
	 * 凭证类型批量删除
	 */
	@RequestMapping(value="/pzlxsDel",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object pzmbsDel() {
		String b="";
		int i=0;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		
		i = pzlxService.doDeletes(guid);
		return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
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
		String jb = pd.getString("jb");
		String pname = pd.getString("pname");
		String controlId = pd.getString("controlId");
		String controlId2 = pd.getString("controlId2");
		
		
		String kmsx = pd.getString("kmsx"); 
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
		mv.addObject("jb", jb);
		mv.addObject("pname", pname);
		mv.addObject("controlId", controlId);
		mv.addObject("controlId2", controlId2);
		mv.setViewName("kjhs/pzlx/kmxxList");
		return mv;
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
		mv.setViewName("kjhs/pzlx/window");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	/**
	 * 获取会计科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/kmxx")
	@ResponseBody
	public Object getkjkmPageList(HttpSession session) throws Exception{
		PageData pd = this.getPageData();
		 Calendar date = Calendar.getInstance();
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String sszt = Constant.getztid(session);
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select c.kmnd, c.sfmj, c.jb, c.bz,c.guid, c.kmbh, c.kmmc,  c.kmsx,  (select d.mc from gx_sys_dmb d where d.zl='kmsx' and d.dm=c.kmsx)as kmsxmc, c.zjf, c.yefx,(case c.yefx when '1' then '贷方' else '借方'end) as yefxmc, c.hslb,"
				+ " (select d.mc from gx_sys_dmb d where d.zl='hslb' and d.dm = c.hslb) as hslbmc, c.kmjc, c.qyf,(case c.qyf when '1' then '是' else '否'end) as qyfmc,"
				+ " c.sfwyh, (case c.sfwyh when '1' then '是' else '否'end) as sfwyhmc,c.sfjjflkm,(case c.sfjjflkm when '1' then '是' else '否'end) as sfjjflkmmc,"
				+ " c.sfgnflkm,(case c.sfgnflkm when '1' then '是' else '否'end) as sfgnflkmmc, c.bmhs,(case c.bmhs when '1' then '是' else '否'end) as bmhsmc, c.xmhs,"
				+ " (case c.xmhs when '1' then '是' else '否'end) as xmhsmc, c.czr, c.czrq from Cw_kjkmszb c where 1=1 and c.sjfl <> 'root'");
		if(Validate.noNull(sszt)){
			tablename.append(" and c.sszt='"+sszt+"' ");
		}
		if(Validate.noNull(dm)){
			tablename.append(" start with jb='"+jb+"' connect by prior jb=sjfl ");
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
//		pageList.setTableName("Cw_kjkmsz k ");//表名
		System.out.println("=======dm========"+dm);
//		if(Validate.noNull(dm)){
//			pageList.setStrWhere(pageList.getStrWhere()+" start with k.jb='"+dm+"' connect by prior k.jb=k.sjfl");	
//		}
	
		//设置WHERE条件
		pageList.setStrWhere(""); 
		pageList.setHj1("");//合计
	    //pageList = pageService.findPageList(pd,pageList);
		pageList = pageService.findWindowList(pd,pageList,"WWW");//引用传递
//		pageList.setHj1("");//合计
//	    pageList = pageService.findPageList(pd,pageList);//引用传递

		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
}
