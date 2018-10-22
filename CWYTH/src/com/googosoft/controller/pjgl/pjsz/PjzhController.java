package com.googosoft.controller.pjgl.pjsz;

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
import com.googosoft.service.pjgl.pjsz.PjzhService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;

@Controller
@RequestMapping(value="/pjgl/pjzh")
public class PjzhController extends BaseController{

	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="pjzhService")
	private PjzhService pjzhService;
	
	/**
	 * 获取信息列表页面
	 * @return
	 */
	@RequestMapping(value="/goPjzhPage")
	public ModelAndView goPjzhPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("pjgl/pjsz/pjzh/pjzh_list");
		return mv;
	}
	/**
	 * 获取列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String dm=pd.getString("dm");
		sqltext.append("GUID,ZHMC,PJLXMC,SFQY,SSZT");
		
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		
		pageList.setTableName(" (select t.GUID,t.ZHMC,lx.PJLXMC as PJLXMC,decode(t.sfqy,'0','否','1','是')SFQY,t.SSZT from CW_PJZHB t " +
				" left join cw_pjlxb lx on lx.guid = t.pjlx  "+
				")k ");//表名
//		pageList.setTableName(" (select t.GUID,t.ZHMC,t.PJLX,decode(t.sfqy,'0','否','1','是')SFQY,t.SSZT from CW_PJZHB t )k ");//表名
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
		} else if (operateType.equals("U") || operateType.equals("L")) {
			
			Map map = pjzhService.getPjytMapById(pd.getString("guid"));
			
			mv.addObject("guid", pd.getString("guid"));
			mv.addObject("pjzhMap", map);
		}
		
		mv.addObject("operateType", operateType);
		mv.setViewName("pjgl/pjsz/pjzh/pjzh_edit");
		return mv;
	}
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value="/doSave")
	@ResponseBody
	public Object doSave(){
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		int result = 0;
		String rybh = LUser.getRybh();
//		String sszt = Constant.getztid(session);
		String sszt = "";
		System.err.println("===============operateType====="+operateType);			
		if ("C".equals(operateType))// 新增
		{
			result = pjzhService.addPjyt(pd,sszt);
			if (result == 1) {
				return "{\"success\":\"true\",\"msg\":\"信息保存成功!\",\"operateType\":\"U\"}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}

		} else if ("U".equals(operateType))// 修改
		{
			result = pjzhService.editPjyt(pd);
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
    	int k = pjzhService.doDelete(dwbh);
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
		String shortfileurl = "\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.pjzhService.expExcel(realfile, shortfileurl,searchValue,guid,sszt);
	}
	/**
	 * 票据类型弹出窗
	 */
	@RequestMapping(value="/getxmlxall")
	public ModelAndView getxmlxall(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String id = pd.getString("id");
		String bmbh = pd.getString("bmbh");
		String pid = pd.getString("pid");
		mv.addObject("bmbh",bmbh);
		mv.addObject("controlId",controlId);//名称
		mv.addObject("id",id);
		mv.addObject("pid", pid);
		mv.setViewName("pjgl/pjsz/pjzh/xmlxall_window");
		return mv;
	}
	/**
	 * 账号类型
	 * */
	@RequestMapping(value="/getPageListTc")
	@ResponseBody
	public Object getPageListTc(HttpSession session){
		String sszt = Constant.getztid(session);
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		sqltext.append(" * ");
		tablename.append("( select guid, PJLXBH, PJLXMC from CW_PJLXB ) k ");
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
	  
		pageList = pageService.findWindowList(pd,pageList,"PJLX");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 查询数据是否使用
	 * @param guid
	 * */
	@RequestMapping(value="/doCheck",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doCheck(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("guid");
		
		int issy=pjzhService.checkSY(dwbh);
		if(issy>0){
			return "1";
		}else{
			return "0";
		}
	}
	
}
