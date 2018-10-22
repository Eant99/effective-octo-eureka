package com.googosoft.controller.kjhs;

import java.util.ArrayList;
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
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWMXB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWSZ;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.wsbx.jcsz.WldwszService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/wldwsz")
public class wldwszController extends BaseController {
	
	@Resource(name="wldwszService")
	private WldwszService wldwszService;
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	//跳转到列表页面
	@RequestMapping(value = "/gowldwszListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/wldwsz/wldwsz_list");
		return mv;

	}

	   //获取列表数据
		@RequestMapping(value="getPageList",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getPageList() {
		    PageList pageList = new PageList();
			PageData pd = this.getPageData();
//			String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"00");
			// 设置查询字段名
			StringBuffer sql = new StringBuffer();
			sql.append("(select K.GUID,K.WLBH,K.DWMC,K.DWJC, nvl(k.sfdgzf,'02') as sfdgzf, decode(dwlx,01,'供应商',02,'主管部门') as DWLX, ");
			sql.append(" K.DWDZ,K.LXR,K.SH,K.BGDH,K.CZ");
			sql.append(" FROM CW_WLDWB K " );
			sql.append(	") K");
			pageList.setSqlText("*");
			// 表名
			pageList.setTableName(sql.toString());
			// 主键
			pageList.setKeyId("guid");
			// 设置合计值字段名
			pageList.setHj1("");
			// 页面数据
			pageList = pageService.findPageList(pd, pageList);
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
	//跳转到增加页面
	@RequestMapping(value="/goEditPage")
	public ModelAndView goAddsrysPage(){
		//定义主键guid
		String guid =this.get32UUID();
		ModelAndView mv = this.getModelAndView();
		//传guid到页面
		mv.addObject("guid",guid);
		mv.setViewName("kjhs/wldwsz/wldwsz_edit");		
		return mv;
	}
	//跳转到增加页面
		@RequestMapping(value="/goSkdwAddPage")
		public ModelAndView goSkdwAddPage(){
			//定义主键guid
			String guid =this.get32UUID();
			ModelAndView mv = this.getModelAndView();
			//传guid到页面
			mv.addObject("guid",guid);
			mv.setViewName("wsbx/rcbx/skdw_add");		
			return mv;
		}
	/**
	 * 添加往来单位设置信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addWldwsz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object addWldwsz(CW_WLDWSZ wldwsz,HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String b = "";
		int i;
		String guid = pd.getString("guid");
		String dwmc = pd.getString("dwmc");
		String dwjc = pd.getString("dwjc");
		String dwlx = pd.getString("dwlx");
		String dwdz = pd.getString("dwdz"); 
		String lxr = pd.getString("lxr");
		String sh = pd.getString("sh");
		String bgdh = pd.getString("bgdh");
		String cz = pd.getString("cz");
		
		wldwsz.setGuid(guid);
		wldwsz.setDwmc(dwmc);
		wldwsz.setDwjc(dwjc);
		wldwsz.setDwlx(dwlx);
		wldwsz.setDwdz(dwdz);
		wldwsz.setLxr(lxr);
		wldwsz.setSh(sh);
		wldwsz.setBgdh(bgdh);
		wldwsz.setCz(cz);
		wldwsz.setSszt(sszt);
		i = wldwszService.doAdd(wldwsz);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		
		return b;
	}
	/**
	 * 添加往来单位明细表信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addWldwmxb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object addWldwmxb(CW_WLDWMXB wldwmxb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = this.get32UUID();//创建主键
			String wlbh = (String) map.get("wlbh1");
			String khyh = (String) map.get("khyh1");
			String khyhzh = (String) map.get("khyhzh1");
			String yhlhh = (String) map.get("yhlhh1");
			
			    //将字段放入wldwmxb
			    wldwmxb.setGuid(guid);
				wldwmxb.setWlbh(wlbh);
				wldwmxb.setKhyh(khyh);
				wldwmxb.setKhyhzh(khyhzh);
				wldwmxb.setYhlhh(yhlhh);
				//增加
				wldwszService.doAdd1(wldwmxb);
			
		}
		b="success";
		return b;
	}
	/**
	 * 跳转往来单位设置信息编辑页面
	 * @return
	 */
	@RequestMapping(value="/goEdit1Page",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goEdit1Page(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		mv.addObject("guid",guid);
		Map<?, ?> map = wldwszService.getObjectById(guid);
		List list = wldwszService.getObjectById1(guid);
		mv.addObject("wldwsz", map);
		mv.addObject("wldwmxb",list);
		mv.setViewName("kjhs/wldwsz/wldwsz_edit1");		
		return mv;

	}
	/**
	 * 跳转往来单位设置信息查看页面
	 * @return
	 */
	@RequestMapping(value="/goLookPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goLookPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		mv.addObject("guid",guid);
		Map<?, ?> map = wldwszService.getObjectById(guid);
		List list = wldwszService.getObjectById1(guid);
		mv.addObject("wldwsz", map);
		mv.addObject("wldwmxb",list);
		mv.setViewName("kjhs/wldwsz/wldwsz_look");		
		return mv;

	}
	
	
	/**
	 * 编辑往来单位设置信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editWldwsz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editWldwsz(CW_WLDWSZ wldwsz)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String guid = pd.getString("guid");
		String wlbh = pd.getString("wlbh");
		String dwmc = pd.getString("dwmc");
		String dwjc = pd.getString("dwjc");
		String dwlx = pd.getString("dwlx");
		String dwdz = pd.getString("dwdz"); 
		String lxr = pd.getString("lxr");
		String sh = pd.getString("sh");
		String bgdh = pd.getString("bgdh");
		String cz = pd.getString("cz");
		String sfdgzf = pd.getString("sfdgzf");
		
		wldwsz.setGuid(guid);
		wldwsz.setWlbh(wlbh);
		wldwsz.setDwmc(dwmc);
		wldwsz.setDwjc(dwjc);
		wldwsz.setDwlx(dwlx);
		wldwsz.setDwdz(dwdz);
		wldwsz.setLxr(lxr);
		wldwsz.setSh(sh);
		wldwsz.setBgdh(bgdh);
		wldwsz.setCz(cz);
		wldwsz.setSfdgzf(sfdgzf);
		i = wldwszService.doEdit(wldwsz);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		
		return b;
	}
	/**
	 * 编辑往来单位明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editWldwszmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editWldwszmx(CW_WLDWMXB wldwmxb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();

		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = (String) map.get("guid");
			String wlbh = (String) map.get("wlbh1");
			String khyh = (String) map.get("khyh1");
			String khyhzh = (String) map.get("khyhzh1");
			String yhlhh = (String) map.get("yhlhh1");
			
			if(guid.length()==0) {
				
				guid=this.get32UUID();
			}
			    //将字段放入wldwmxb
			    wldwmxb.setGuid(guid);
				wldwmxb.setWlbh(wlbh);
				wldwmxb.setKhyh(khyh);
				wldwmxb.setKhyhzh(khyhzh);
				wldwmxb.setYhlhh(yhlhh);
				//删除
				//wldwszService.doDeleteWldwmx(guid, wldwmxb);
				//增加
				wldwszService.doAdd1(wldwmxb);
			
		}
		b="success";
		return b;
	}
	/**
	 * 先删后增
	 */
	@RequestMapping(value="/delYhzh",produces = "text/html;charset=UTF-8")
	@ResponseBody	
	public Object delYhzh() {
		PageData pd = this.getPageData();
		String wlbh = pd.getString("wlbh");
		int b = wldwszService.delYhzh(wlbh);
		if(b>0) {
			return "{\"success\":\"true\",\"msg\":\"删除成功！\"}";    
		}else {
			return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
	}
	/**
	 * 删除单条往来单位设置信息
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody	
	public Object doDelete(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		int b = wldwszService.doDelete(guid);
		if(b>0){
			return "{\"success\":\"true\",\"msg\":\"删除成功！\"}";    
		}else{
			return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
		
	}
	/**
	 * 批量删除往来单位
	 */
	@RequestMapping(value="/wldwDel",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wldwDel() {
		String b="";
		int i=0;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		
		i = wldwszService.doDeletes(guid);
		return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
	}
	//导出
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
			sqltext.append(" SELECT");
			sqltext.append(" K.GUID AS GUID,K.WLBH AS WLBH,K.DWMC AS DWMC,K.DWJC AS DWJC,(select B.MC from GX_SYS_DMB B where B.Zl='wldwlx' and B.DM = K.DWLX) as DWLX,K.DWDZ AS DWDZ,K.LXR AS LXR,K.SH AS SH,K.BGDH AS BGDH,K.CZ AS CZ");
			sqltext.append(" FROM CW_WLDWB K  where 1=1");
			sqltext.append(ToSqlUtil.jsonToSql(searchValue));
			String guid = pd.getString("guid");
			if(Validate.noNull(guid)){
				sqltext.append(" AND K.GUID IN ('"+guid+"') ");
			}
//			if(Validate.noNull(searchValue)) {
//				sqltext.append(" and '"+searchValue+"'");
//			}
			sqltext.append(" ORDER BY K.WLBH ");
			
			List<M_largedata> mlist = new ArrayList<M_largedata>();
			M_largedata m1 = new M_largedata();
			
			m1.setColtype("String");
			m1.setName("WLBH");
			m1.setShowname("单位编号");
			mlist.add(m1);
			
			M_largedata m2 = new M_largedata();
			m2.setColtype("String");
			m2.setName("DWMC");
			m2.setShowname("户名");
			mlist.add(m2);
			
			M_largedata m3 = new M_largedata();
			m3.setColtype("String");
			m3.setName("DWJC");
			m3.setShowname("单位简称");
			mlist.add(m3);
			
			M_largedata m4 = new M_largedata();
			m4.setColtype("String");
			m4.setName("DWLX");
			m4.setShowname("单位类型");
			mlist.add(m4);
			
			M_largedata m5 = new M_largedata();
			m5.setColtype("String");
			m5.setName("DWDZ");
			m5.setShowname("单位地址");
			mlist.add(m5);
			
			M_largedata m6 = new M_largedata();
			m6.setColtype("String");
			m6.setName("LXR");
			m6.setShowname("联系人");
			mlist.add(m6);
			
			//导出方法
			pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
			return "{\"url\":\"excel\\\\"+file+".xls\"}";
		}
		
		//加上银行卡，合并导出（wzd）
		@RequestMapping("/expExcel2")
		@ResponseBody
		public Object stryexpXsInfo() {
			PageData pd = this.getPageData();
			String guid = pd.getString("id");
			String searchValue = pd.getString("searchJson");
			String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
			String realfile = this.getRequest().getServletContext()
					.getRealPath("\\")
					+ "WEB-INF\\file\\" + shortfileurl;
			return this.wldwszService.expExcel2(realfile, shortfileurl, guid,searchValue);
		}
		
		
		@RequestMapping(value="/checkExist",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object checkExist() {
			if(wldwszService.checkWldwbhExist(this.getPageData())) {
				return "{\"exist\":true}";
			}else {
				return "{\"exist\":false}";
			}
		}
	
}
