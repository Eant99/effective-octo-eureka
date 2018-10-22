package com.googosoft.controller.pjgl.pjsz;

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
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.pjgl.pjsz.PjytService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/pjgl/pjyt")
public class PjytController extends BaseController{

	@Resource(name="pjytService")
	private PjytService pjytService;
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	/**
	 * 获取信息列表页面
	 * @return
	 */
	@RequestMapping(value="/goPjytPage")
	public ModelAndView goPjytPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("pjgl/pjsz/pjyt/pjyt_list");
		return mv;
	}
	/**
	 * 获取日常报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String dm=pd.getString("dm");
		sqltext.append("GUID,PJYTBH,PJYTMC,SFQY,BZ,SSZT");
		
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setTableName(" (select t.GUID,t.PJYTBH,t.PJYTMC,decode(t.sfqy,'0','否','1','是')SFQY,t.BZ,t.SSZT from CW_PJYTB t )k ");//表名
//		pageList.setStrWhere(" AND k.kmjdm = '"+dm+"'");
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));

		return pageInfo.toJson();
	}
	/**
	 * 跳转编辑页面
	 * @return
	 */
	@RequestMapping(value = "/goEditPage")
	public ModelAndView goEditPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		if (operateType.equals("C")) {
			String guid = this.get32UUID();
			mv.addObject("guid", guid);
			mv.setViewName("pjgl/pjsz/pjyt/pjyt_add");
		} else if (operateType.equals("U") || operateType.equals("L")) {
			
			Map map = pjytService.getPjytMapById(pd.getString("guid"));
			
			mv.addObject("guid", pd.getString("guid"));
			mv.addObject("pjytMap", map);
			mv.setViewName("pjgl/pjsz/pjyt/pjyt_edit");
		}
		
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value="/doSave")
	@ResponseBody
	public Object doSave( HttpSession session){
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		int result = 0;
		String rybh = LUser.getRybh();
//		String sszt = Constant.getztid(session);
		String sszt = "";
		System.err.println("===============operateType====="+operateType);			
		if ("C".equals(operateType))// 新增
		{
			// 生成单位编号
			// String guid =this.get32UUID();//生成主键id
			// fykmdzb.setGuid(guid);
			boolean checkbmh = pjytService.doCheckDwbh(pd.getString("mbbh"));
			if (checkbmh == false) {
				return "{\"success\":\"false\",\"msg\":\"模板编号不可重复!\"}";

			} else {
				result = pjytService.addPjyt(pd,session,sszt);
				if (result == 1) {
					return "{\"success\":\"true\",\"msg\":\"信息保存成功!\",\"operateType\":\"U\"}";
				} else {
					return MessageBox.show(false, MessageBox.FAIL_SAVE);
				}
			}

		} else if ("U".equals(operateType))// 修改
		{
			result = pjytService.editPjyt(pd);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else {
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	
	/**
	 * 删除数据
	 * @param guid
	 * */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("guid");
    	int k = pjytService.doDelete(dwbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpSession session) {
		PageData pd = this.getPageData();
		String guid = pd.getString("id");
		String sszt = Constant.getztid(session);
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.pjytService.expExcel(realfile, shortfileurl,searchValue,guid,sszt);
	}
	/**
	 * 查看数据是否使用
	 * @param guid
	 * */
	@RequestMapping(value="/doCheck",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doCheck(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("guid");
		
		int issy=pjytService.checkSY(dwbh);
		if(issy>0){
			return "1";
		}else{
			return "0";
		}
	}
}
