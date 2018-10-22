package com.googosoft.controller.ysgl.jcsz;

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
import com.googosoft.service.ysgl.jcsz.XmflService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping("/xmflc")
public class xmflControllera extends BaseController {

	@Resource(name="pageService")
	private PageService pageService;//单例

	@Resource(name="nxmflService")
	private XmflService xmflService;//单例
	
	@RequestMapping(value = "/goXmflListPage")
	public ModelAndView goXmflListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/jcsz/xmfl/xmfl_list2");
		return mv;

	}
	
	/**
	 * 跳转项目分类编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goXmflEditPage")
	public ModelAndView goXmflEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/jcsz/xmfl/xmfl_edit");
		return mv;
	}
	
	/**
	 * 获取单位信息列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select t.guid, t.flmc, (select '(' || m.dm || ')' || m.mc from gx_sys_dmb m where m.dm = t.flmc and m.zl='xmfl') flmcs,\r\n" + 
				"       t.ywglbm, (select '(' || d.dwbh || ')' || d.mc from gx_sys_dwb d where d.dwbh = t.ywglbm ) ywglbmmc,rownum xh" + 
				"       ,t.bz from CW_XMFLB t  ) k");
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
	
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSave(){
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		String rybh = LUser.getRybh();
		PageData pd = this.getPageData();
		result = xmflService.doAdd(pd);
		if(result==1){
			return  "{success:'true', msg:'信息保存成功！'}";
		}else{
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	
	@RequestMapping(value="/doAdd",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object ccywsqAdd() {
		if(xmflService.addCcywsq(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"保存成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"保存失败，请稍后重试！\"}";
		}
	}	
	
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		int IsNotCanDelete = xmflService.validateById(pd.getString("flmcs"));
		if(IsNotCanDelete>0){
        	return "{success:false,msg:'已经使用不能删除！'}";
        }else{
        	int k = xmflService.doDel(pd.getString("guids"));
    		if(k>0){
    			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
    		}else{
    			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    		}
        }
	}
}
