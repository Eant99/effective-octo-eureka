package com.googosoft.controller.systemset.xtsz;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.systemset.xtsz.ZC_CJWT;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.xtsz.CjwtService;
import com.googosoft.util.PageData;

/**
 * 常见问题维护控制类
 */
@Controller
@RequestMapping(value="/cjwt")
public class CjwtController extends BaseController{
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="cjwtService")
	private CjwtService cjwtService;//单例
	
	/**
	 * 获取常见问题维护列表页面
	 */
	@RequestMapping(value="/goCjwtListPage")
	public ModelAndView goCjwtListPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/xtsz/cjwt_list");
		return mv;
	}
	
	/**
	 * 获取常见问题维护列表页面
	 */
	@RequestMapping(value="/goCjwtLookPage")
	public ModelAndView goCjwtLookPage(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("cjwtList", cjwtService.getList());
		mv.setViewName("systemset/xtsz/cjwt_look");
		return mv;
	}
	
	/**
	 * 获取帮助信息列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.TITLE,(SELECT RYGH||'('||XM||')' FROM GX_SYS_RYB WHERE RYBH=K.JDR) AS JDR,K.JDR AS JDRBH,TO_CHAR(K.JDRQ,'YYYY-MM-DD') AS JDRQ,TO_CHAR(K.UPDDATE,'YYYY-MM-DD HH24:MI') AS UPDDATE ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");//where条件
		pageList.setTableName("ZC_CJWTB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取常见问题维护编辑页面
	 */
	@RequestMapping(value="/goCjwtEditPage")
	public ModelAndView goCjwtEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		String id = pd.getString("id");
		Map map = new HashMap();
		if(operateType.equals("U")||operateType.equals("L")){
			map = cjwtService.getObjectById(id);
		}
		mv.addObject("cjwt", map);
		mv.setViewName("systemset/xtsz/cjwt_edit");
		return mv;
	}
	
	/**
	 * 保存帮助信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_CJWT cjwt) throws Exception{
		HttpServletRequest request = this.getRequest();
		String operateType = request.getParameter("operateType");
		boolean flag = cjwtService.doSave(cjwt);
		if(flag)
			return "{\"success\":\"true\",\"id\":\""+cjwt.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
		return "{\"success\":\"false\",\"id\":\""+cjwt.getGuid()+"\",\"msg\":\"信息保存失败！\"}";
	}
	/**
	 * 删除
	 * @param bzxx
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String id = pd.getString("id");
		boolean flag = cjwtService.doDelete(id);
		if(flag)
			return "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
		return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
	}

}
