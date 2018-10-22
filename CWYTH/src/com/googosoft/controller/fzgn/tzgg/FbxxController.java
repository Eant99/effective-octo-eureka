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
import com.googosoft.pojo.fzgn.tzgg.ZC_SYS_XTXX;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.tzgg.FbxxService;
import com.googosoft.util.PageData;

/**
 * 通知公告控制类
 * @author master
 */
@Controller
@RequestMapping(value="/fbxx")
public class FbxxController  extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="fbxxService")
	private FbxxService fbxxService;//单例
	
	/**
	 * 跳转通知公告列表页面
	 * @return
	 */
	@RequestMapping(value="/goFbxxPage")
	public ModelAndView goDwbPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fzgn/tzgg/fbxx_list");
		return mv;
	}
	/**
	 * 获取通知公告列表页面的分页数据
	 */
	@RequestMapping(value="/getPageList",produces="text/html;charset=utf-8")
	@ResponseBody
	public Object getPageList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
//		sqltext.append(" GID,TITLE,FBSJ,SFZS, FBR");
		sqltext.append(" * ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GID");//主键
		pageList.setTableName("(SELECT W.GID,W.TITLE,TO_CHAR(W.FBSJ,'YYYY-MM-DD') AS FBSJ,DECODE(W.SFZS,'1','是','0','否')AS SFZS, '(' || RYGH || ')' || XM  AS FBR FROM ZC_SYS_XTXX W LEFT JOIN GX_SYS_RYB B ON B.RYBH = W.FBR) K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 跳转通知公告编辑页面和查看页面
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = fbxxService.getObjectById(pd.getString("gid"));//根据主键查询通知公告详情信息
			mv.addObject("Content",map.get("XX"));
			mv.addObject("xtxx", map);
		}else{
			map.put("SFZS", "1");
		}
		mv.addObject("xtxx", map);
		mv.setViewName("fzgn/tzgg/fbxx_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	
	/**
	 * 通知公告编辑时的保存操作
	 * @param xtxx
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_SYS_XTXX xtxx) throws Exception{
		HttpServletRequest request = this.getRequest();
		String content = request.getParameter("content");
		String title = request.getParameter("title");
		String sfzs = request.getParameter("sfzs");
		String gid = request.getParameter("gid");
		xtxx.setTitle(title);
		xtxx.setSfzs(sfzs);
		String operateType = request.getParameter("operateType");
		String b = "";
		int i;
		if("C".equals(operateType)){//新增
			i = fbxxService.doAdd(xtxx, content);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else if(i==0){
				b = "{\"success\":\"false\",\"id\":\"\",\"msg\":\"模块编号已经存在！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){//修改
			i = fbxxService.doUpdate(xtxx, content,gid);
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
	 * 删除通知公告
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String gid = pd.getString("gid");
		String b = "";
		int k = fbxxService.doDelete(gid);
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
		int k = fbxxService.goBjxx(bh);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"信息添加成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"信息添加失败！\"}";
		}
		return b;
	}

}
