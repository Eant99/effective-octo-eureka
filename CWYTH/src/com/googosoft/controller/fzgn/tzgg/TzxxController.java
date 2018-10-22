package com.googosoft.controller.fzgn.tzgg;

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
import com.googosoft.pojo.fzgn.tzgg.ZC_SYS_TZXX;
import com.googosoft.pojo.fzgn.tzgg.ZC_SYS_XTXX;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.tzgg.TzxxService;
import com.googosoft.util.PageData;

/**
 * 通知公告控制类
 * @author master
 */
@Controller
@RequestMapping(value="/tzxx")
public class TzxxController  extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="tzxxService")
	private TzxxService tzxxService;//单例
	
	/**
	 * 获取发布系统信息列表页面
	 * @return
	 */
	@RequestMapping(value="/goFbxxPage")
	public ModelAndView goDwbPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fzgn/tzgg/tzxx_list");
		return mv;
	}
	/**
	 * 获取发布系统信息列表数据
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" GID,TITLE,FBSJ,SFZS, FBR, DWBH ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GID");//主键
		pageList.setTableName("( select W.GID,W.TITLE,TO_CHAR(W.FBSJ,'yyyy-MM-dd') AS FBSJ,DECODE(W.SFZS,'1','是','0','否')AS SFZS,(SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=W.FBR) AS FBR,(SELECT '('||DWBH||')'||MC FROM GX_SYS_DWB B WHERE B.DWBH=W.DWBH) AS DWBH  from ZC_SYS_TZXX W)K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取发布系统消息编辑页面
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		Map map = new HashMap();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = tzxxService.getObjectById(pd.getString("gid"));
			mv.addObject("Content",map.get("XX"));
			mv.addObject("xtxx", map);
		}else{
			map.put("SFZS", "1");
		}
		mv.addObject("xtxx", map);
		mv.setViewName("fzgn/tzgg/tzxx_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	
	/**
	 * 添加系统消息
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_SYS_TZXX xtxx) throws Exception{
		HttpServletRequest request = this.getRequest();
		String content = request.getParameter("content");
		String title = request.getParameter("title");
		String sfzs = request.getParameter("sfzs");
		String gid = request.getParameter("gid");
		String dwbh = request.getParameter("dwbh");
		xtxx.setTitle(title);
		xtxx.setSfzs(sfzs);
		
		String operateType = request.getParameter("operateType");
		String b = "";
		int i;
		if("C".equals(operateType)){
			i = tzxxService.doAdd(xtxx, content);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else if(i==0){
				b = "{\"success\":\"false\",\"id\":\"\",\"msg\":\"模块编号已经存在！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){
			i = tzxxService.doUpdate(xtxx, content,gid);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}else{
			b = "{\"success\":\"false\",\"id\":\"\",\"msg\":\"参数传入有误！\"}";
		}
		return b ;
	}
	
	/**
	 * 删除系统消息
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doDelete(){
		PageData pd = this.getPageData();
		String gid = pd.getString("gid");
		String b = "";
		int k = tzxxService.doDelete(gid);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
		return b;
	}
	
	/**
	 * 增加阅读通知信息
	 */
	@RequestMapping(value="/goBjxx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object goBjxx(){
		PageData pd = this.getPageData();
		String bh = pd.getString("bh");
		String b = "";
		int k = tzxxService.goBjxx(bh);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"信息添加成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"信息添加失败！\"}";
		}
		return b;
	}

}
