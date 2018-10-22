package com.googosoft.controller.kjhs;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.ztxxService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value = "/ztxx")
public class ztxxController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="ztxxService")
	private ztxxService ztxxService;//单例
	
	/**
	 * 获取帐套信息列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goztxxListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/pzsz/ztxx_list");
		return mv;

	}
	/**
	 * 切换账套
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectZtListPage")
	public ModelAndView selectZtListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/pzsz/selectztlist");
		return mv;

	}
	/**
	 * 删除账套信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		PageData pd = this.getPageData();
		int i = ztxxService.deleteztxx(pd);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	/**
	 * 跳转至编辑页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){
			
			Map<String,String> map = new HashMap<String,String>();
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = ztxxService.getztxxMapById(pd.getString("guid"));			
			mv.addObject("dwb", map);
		}
		mv.setViewName("kjhs/pzsz/ztxx_edit");///webView/kjhs/pzsz/ztxx_edit.jsp
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	 /**
	 * 判断账套名称是否存在
	 * @return
	 */
	@RequestMapping(value="/doSelect",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSelect(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String ztmc = pd.getString("ztmc");
		String guid = pd.getString("guid");
		boolean yyztmc = ztxxService.getObjectById(ztmc, guid) ;
		Gson gson = new Gson();
		return  gson.toJson(yyztmc);
       
	}
	/**
	 * 获取账套信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/goDdbPage")
	public ModelAndView goDdbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String pname = pd.getString("pname");
		String controlId = pd.getString("controlId");
		mv.setViewName("kjhs/pzsz/ztxx_edit");
		mv.addObject("dm", dm);
		mv.addObject("pname", pname);
		mv.addObject("controlId", controlId);
		return mv;
	}
	/**
	 * 获取账套信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/window")
	public ModelAndView window(){
		ModelAndView mv = this.getModelAndView();
		
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/pzsz/window");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSave(){
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		int result=0;
		String rybh = LUser.getRybh();
		System.err.println("________________"+operateType);
//		String loginId = xsxxService.getLoginIdByRybh(rybh);
		
		if("C".equals(operateType))//新增
		{  
			//生成单位编号
			String guid =this.get32UUID();//生成主键id
			boolean bool = ztxxService.doAdd(pd,guid);
			if(bool){
				return  "{success:'true', msg:'信息保存成功！',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			
			
			result = ztxxService.doUpdate(pd);
			if(result==1)
			{
				return "{success:'true',msg:'信息保存成功！'}";
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
	 * 获取科目弹窗信息列表数据
	 */
	@RequestMapping(value="/kmxx")
	@ResponseBody
	public Object kmxx(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String dm=pd.getString("dm");
		sqltext.append("GUID,ZTMC,KJND,QJS,QYRQ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setTableName("CW_ZTXXB k ");//表名
//		pageList.setStrWhere(" AND k.kmjdm = '"+dm+"'");
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
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("GUID,ZTMC,KJND,QJS,QYRQ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("CW_ZTXXB");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}

//	//跳转到账套信息添加页面
//		@RequestMapping("/goSrxmAddPage")
//		public ModelAndView goSrxmAddPage() {
//			ModelAndView mv = this.getModelAndView();
//			//iniSelect(mv);
//			mv.setViewName("ysgl/xmsz/srxm/srxm_add");
//			return mv;
//		}
//		//添加信息
//		@RequestMapping("/srxmAdd")
//		@ResponseBody
//		public Object srxmAdd() {
//			if(srxmService.addSrxm(this.getPageData()) > 0) {
//				return "{\"success\":true}";
//			}else {
//				return "{\"success\":false}";
//			}
//		}
//		//跳转到项目信息编辑页面
//		@RequestMapping("/goSrxmEditPage")
//		public ModelAndView goSrxmEditPage() {
//			ModelAndView mv = this.getModelAndView();
//			PageData pd = this.getPageData();
//			//iniSelect(mv);
//			Map<String, Object> srxm = srxmService.getSrxmMapById(pd.getString("guid"));
//			mv.addObject("srxm",srxm);
//			mv.setViewName("ysgl/xmsz/srxm/srxm_edit");
//			return mv;
//		}
//		//编辑项目信息
//		@RequestMapping("/srxmEdit")
//		@ResponseBody
//		public Object srxmEdit() {
//			if(srxmService.editSrxm(this.getPageData()) > 0) {
//				return "{\"success\":true}";
//			}else {
//				return "{\"success\":false}";
//			}
//		}
}