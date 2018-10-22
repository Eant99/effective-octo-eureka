package com.googosoft.controller.wsbx.jcsz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.ysgl.jcsz.zysxszService;
import com.googosoft.pojo.wsbx.jcsz.Cw_fykmdzb;
import com.googosoft.service.base.PageService;

import org.springframework.web.servlet.ModelAndView;

import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
@Controller
@RequestMapping(value="/zysxsz")
public class zysxszController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="zysxszService")
	private zysxszService zysxszService;//单例
	
	/**
	 * 获取结转模板列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gozysxListPage")
	public ModelAndView goJzmbListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("wsbx/jcsz/zysxsz/zysxsz_list");
		return mv;

	}
	
	/**
	 * 字典信息弹出窗
	 */
	@RequestMapping(value="/zdpage")
	public ModelAndView goZdxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("wsbx/jcsz/zysxsz/mkTree");
		return mv;
	}
	/**
	 * 获取模块信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/window")
	public ModelAndView goPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("wsbx/jcsz/zysxsz/zysxsz_window");
		return mv;
	}
	
	/**
	 * 跳转结转信息编辑页面_增加
	 * @return
	 */
	@RequestMapping(value="/goAddPage")
	public ModelAndView goDmbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		System.err.println("___________" + operateType);
		if(operateType.equals("C")){
			
			Map<String,String> map = new HashMap<String,String>();
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map map = zysxszService.getzysxMapById(pd.getString("dwbh"));
			
			mv.addObject("dwb", map);
//					mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.setViewName("wsbx/jcsz/zysxsz/zysxsz_zj");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 跳转结转信息编辑页面_修改
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		System.err.println("________________________"+operateType);
		if(operateType.equals("C")){
			Map<String,String> map = new HashMap<String,String>();
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map map = zysxszService.getzysxMapById(pd.getString("guid"));
			mv.addObject("dwb", map);
//					mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.setViewName("wsbx/jcsz/zysxsz/zysxsz_update");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 获取注意事项列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("GUID,MKBH,MKMC,SFXS,ZYSXNR");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("cw_zysxb K" );//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
//	    ZYSXNR
	    List finalList = new ArrayList();
	    for(int i=0;i<pageList.getContentList().size();i++){
	    	Map map = (Map)pageList.getContentList().get(i);
	    	String zysxnr = CommonUtils.BlobToString((Blob)map.get("ZYSXNR"));
	    	map.put("ZYSXNR", zysxnr);
	    	finalList.add(map);
	    }
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(finalList));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取注意事项列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMKBList")
	@ResponseBody
	public Object getMKBList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("MKBH,MKMC");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("MKBH");//主键
		pageList.setStrWhere("");
		pageList.setTableName("zc_sys_mkb K" );//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(Cw_fykmdzb fykmdzb){
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		String mkbh =pd.getString("mkbh");
		HttpServletRequest request = this.getRequest();
		String content = request.getParameter("zysx");
		String check = request.getParameter("check");
		int result=0;
		String rybh = LUser.getRybh();
		fykmdzb.setCzr(rybh);
		if("C".equals(operateType))//新增
		{  
			//生成单位编号
			String guid =this.get32UUID();//生成主键id
			fykmdzb.setGuid(guid);
			result = zysxszService.addZysx(pd,guid);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = zysxszService.editZysx(pd,check);
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
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		PageData pd = this.getPageData();
		int i = zysxszService.doDelete(pd);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	
	/**
	 * 展示
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Xs", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object Xs() {
		String guid = this.getPageData().getString("guid");
		PageData pd = this.getPageData();
		int i = zysxszService.Xs(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	
	/**
	 * 不展示
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Bxs", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object Bxs() {
		String guid = this.getPageData().getString("guid");
		PageData pd = this.getPageData();
		int i = zysxszService.Bxs(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}

}
