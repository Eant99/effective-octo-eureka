package com.googosoft.controller.xmgl.jcsz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.CW_JSXXB;
import com.googosoft.pojo.fzgn.jcsz.CW_XSXXB;
import com.googosoft.pojo.fzgn.jcsz.CW_ZYXXB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.JsxxsService;
import com.googosoft.service.xmgl.jcsz.ZyxxwhService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/zyxxwh")
public class ZyxxwhController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="zyxxwhService")
	private ZyxxwhService zyxxwhService;//单例
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	@RequestMapping(value = "/goListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("dwbh",pd.getString("dwbh"));
		mv.addObject("operateType","U");
		mv.addObject("qc",pd.getString("qc"));
		mv.setViewName("xmgl/jcsz/zyxxwh/zyxxwh_list");
		return mv;

	}
	/**
	 * 获取专业信息列表数据
	 * 
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/getzyxxLists")
	@ResponseBody
	public Object getPageLists() throws Exception {
		PageList pageList = new PageList();
		String datas = SendHttpUtil.sendPost(SystemSet.address+"/xmgl/jcsz/getzyxxLists", "");

		PageInfo pageInfo = new PageInfo("1", "15", "15",datas);
		return pageInfo.toJson();
	}*/
	/**
	 * 添加个专业详细信息
	 * 
	 * @return
	 */
	/*@RequestMapping(value = "/gozyxxtjPage")
	public ModelAndView gozyxxtjPage() {
		String url = SystemSet.address+"/demo/dict";

		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> xzList = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.XZ1));// 出生地
		System.out.println("xzList============"+xzList);
		mv.addObject("xzList", xzList);
	
			String guid = GenAutoKey.createRybh("cw_zyxxb", "guid", "32");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("guid", guid);
			//map.put("dwbh",
					//CommonUtil.bmhTomc(this.getPageData().getString("dwbh")));
			mv.addObject("info", map);
	
		
		mv.setViewName("xmgl/jcsz/zyxxwh/zyxxwh_tjpage");
		return mv;
	}*/
	/**
	 * 跳转凭证类型增加页面
	 * @return
	 */
	@RequestMapping(value="/gozyxxtjPage")
	public ModelAndView goAddPzlxPage(){
		ModelAndView mv = this.getModelAndView();

		//定义主键guid
		String guid =this.get32UUID();	
		//传guid到页面
		//mv.addObject("guid",guid);
		mv.addObject("qc", this.getPageData().getString("qc"));
		mv.addObject("xzList",dictService.getDict(Constant.XZ1));
		mv.setViewName("xmgl/jcsz/zyxxwh/zyxxwh_tjpage");
		
		return mv;
	}
	/**
	 * 获取单个专业详细信息（修改）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gozyxxbjPage")
	public ModelAndView gozyxxbjPage() {

		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		Map map = zyxxwhService.getObjectById(guid);
		mv.addObject("zyxx",map);
		mv.addObject("operateType","U");
		mv.addObject("xzList",dictService.getDict(Constant.XZ1));
		System.err.println("map===="+map);
		mv.setViewName("xmgl/jcsz/zyxxwh/zyxxwh_edit");
		
		return mv;
	}


	/*@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.ZYBH,K.ZYMC,K.SSYX,K.ZYFX,K.XZ,K.ZYZT,K.ZYJC,K.ZYYWMC,K.XKMLM,K.YJSZYM,to_char(JLNY,'yyyy-mm-dd') JLNY,K.QSXQ,K.ZWJC,K.BZ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("CW_ZYXXB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}*/
	/**
	 * 获取单位信息列表数据
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" K.GUID,K.ZYBH,K.ZYMC,K.SSYX,K.ZYFX,K.XZ,K.ZYZT");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName("CW_ZYXXB K");
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");

		String rybh = LUser.getRybh();//当前登陆者的人员编号
		pageList.setStrWhere(pageService.getDwqxWhereSql(rybh, "K.GUID", dwbh, true)); //获取管理单位权限
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}*/
	@RequestMapping(value="/goEditPage")
	public ModelAndView goDmbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

        List<Map<String, Object>> xz = dictService.getDict("xz");
		mv.addObject("xzlist", xz);
		String treesearch = pd.getString("treesearch");
		mv.setViewName("xmgl/jcsz/zyxxwh/zyxxwh_edit");
		mv.addObject("treesearch", treesearch);
		mv.addObject("operateType","U");
		return mv;
	}
	//获取列表页面数据
		@RequestMapping(value = "/getPageList")
		@ResponseBody
		public Object getxdPageList() throws Exception {		
			PageData pd = this.getPageData();
			StringBuffer sqltext = new StringBuffer();//查询字段
			StringBuffer tablename = new StringBuffer();
			String dwbh = pd.getString("treeid");
			PageList pageList = new PageList();
			sqltext.append(" * ");
			tablename.append(" (select b.GUID,b.ZYBH,b.ZYMC, (select '('||dwbh||')'||mc from gx_sys_dwb d where d.dwbh=b.ssyx ) AS ssyx,(select '('||dwbh||')'||mc from gx_sys_dwb d where d.dwbh=b.zyfx )AS zyfx, dm.MC AS XZ,(CASE b.ZYZT WHEN '0' THEN '禁用'  ELSE '启用' END) AS ZYZT"
					+ " from CW_ZYXXB b left join gx_sys_dmb dm on dm.dm=b.xz and zl='xz'");
			if(Validate.noNull(dwbh)) {
				tablename.append(" where b.SSYX='"+dwbh+"'  ");
			}
			tablename.append(")k");
			
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
		 * 保存
		 * @param ryb
		 * @return
		 */
		@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object doSave(CW_ZYXXB zyxxb)
		{
		    int result=0;
		    //验证专业编号是否重复
		    if(zyxxwhService.checkbh(zyxxb.getZybh())>0) {
		    	return "{success:'false',msg:'专业编号已经存在！'}";
		    }
			result = zyxxwhService.doAdd(zyxxb);
			if(result>0)
			{
				return "{success:'true',msg:'信息保存成功！'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		/**
		 * 保存
		 * @param ryb
		 * @return
		 */
		@RequestMapping(value="/doSaveEdit",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object doSaveEdit(CW_ZYXXB zyxxb)
		{
		    int result=0;
		    //验证专业编号是否重复
		    if(zyxxwhService.checkbh1(zyxxb.getZybh(),zyxxb.getGuid())>0) {
		    	return "{success:'false',msg:'专业编号已经存在！'}";
		    }
			result = zyxxwhService.doEdit(zyxxb);
			if(result>0)
			{
				return "{success:'true',msg:'信息保存成功！'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		/**
		 * 删除
		 * @return
		 */
		@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object doDelete(){
			String guid = this.getPageData().getString("guid");
			String i = zyxxwhService.doDel(guid);
			return new Gson().toJson(i);
//			if(i>0){
//				return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
//			}else{
//				return MessageBox.show(false, MessageBox.FAIL_DELETE);
//			}
			
		}
		/**
		 * 启用
		 * @return
		 */
		@RequestMapping(value="/doqy",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object doqy() {
			String guid = this.getPageData().getString("guid");
			int i = zyxxwhService.doqy(guid);
			if(i>0) {
				return MessageBox.show(true,MessageBox.SUCCESS_DELETE);
			}else {
				return MessageBox.show(false,MessageBox.FAIL_DELETE);
			}
		}
		
		@RequestMapping(value="/dojy",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object dojy() {
			String guid = this.getPageData().getString("guid");
			int i = zyxxwhService.dojy(guid);
			if(i>0) {
				return MessageBox.show(true,MessageBox.SUCCESS_DELETE); 
			}else {
				return MessageBox.show(true,MessageBox.FAIL_DELETE);
			}
		}
		/**
		 * 删除
		 * @return
		 */
		@RequestMapping(value="/updZt",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object updZt(){
			String guid = this.getPageData().getString("guid");
			String zt = this.getPageData().getString("zt");
			int i = zyxxwhService.updZt(guid,zt);
			if(i>0){
				return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
			}else{
				return MessageBox.show(false, MessageBox.FAIL_DELETE);
			}
			
		}
		/**
		 * 新导出
		 */
		@RequestMapping("/expExcel")
		@ResponseBody
		public Object Info() {
			PageData pd = this.getPageData();
			String guid = pd.getString("id");
			String dwbh = pd.getString("treedwbh");
			String searchValue = pd.getString("searchJson");
			String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
			String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
			return this.zyxxwhService.expExcel(realfile, shortfileurl,searchValue,guid,dwbh);
		}
	
}
