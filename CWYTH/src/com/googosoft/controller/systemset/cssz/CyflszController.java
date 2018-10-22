package com.googosoft.controller.systemset.cssz;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.systemset.cssz.ZC_CYFLSZB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.cssz.CyflszService;
import com.googosoft.util.PageData;
/**
 * 常用分类设置
 */
@Controller
@RequestMapping(value = "/cyflsz")
public class CyflszController extends BaseController {
	@Resource(name="cyflszService")
	private CyflszService cyflszService;
	
	@Resource(name = "pageService")
	private PageService pageService;// 单例
	
	/**
	 * 获取管理员快速建账设置    页面
	 */
	@RequestMapping(value="goCyflszPage")
	public ModelAndView goHsortSetPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/cssz/cyflsz");
		return mv;
	}
	/**
	 * 获取领用人快速建账设置    页面
	 */
	@RequestMapping(value="goLyrksjzPage")
	public ModelAndView goLyrksjzPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/cssz/lyrkssz");
		return mv;
	}
	/**
	 * 常用类型设置列表
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("t.gid,t.mc,t.flh,t.flmc,t.pxxh,case t.lx when '1' then '管理员' else '领用人' end as lx");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.gid");// 主键
		pageList.setStrWhere("");// where条件
		pageList.setTableName("zc_cyflszb t");// 表名
		pageList.setStrWhere(" and lx='"+pd.getString("lx")+"'"); 
		pageList.setHj1("");// 合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取列表数据
	 */
	@RequestMapping(value="/getPageFlxxList")
	@ResponseBody
	public Object getPageFlxxList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" flh,flmc,ffldm,fflmc ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("bzdm");//主键
		pageList.setStrWhere(" and dmjc='1'");// where条件
		pageList.setTableName("zc_flb_jyb");//表名
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 类型删除
	 */
	@RequestMapping(value = "/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String gid = this.getPageData().getString("gid");
		int b = cyflszService.doDelete(gid);
		if(b>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	/**
	 * 增加或修改
	 */
	@RequestMapping(value = "/getEditPage")
	public ModelAndView getEditPage() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		if (operateType.equals("U")) {
			Map map =cyflszService.getCyflszxx(this.getPageData().getString("gid"));
			mv.addObject("tpb", map);
		}
		mv.setViewName("systemset/cssz/cyflsz_edit");
		mv.addObject("operateType", operateType);
		mv.addObject("lx", this.getPageData().getString("lx"));
		return mv;
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/savecyflsz", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object saveTplxsz(ZC_CYFLSZB tpb) {
		String operateType = this.getPageData().getString("operateType");
		boolean isSuccess;
		switch (operateType) {
			case "C":// 常用分类设置信息
			boolean checkbmc=cyflszService.doCheckmc(tpb.getMc(),tpb.getLx());
			if(checkbmc==false){
				return  "{success:false,msg:'名称不可重复!'}";
			}
			isSuccess = cyflszService.doAdd(tpb);
			if (isSuccess)
				return "{\"success\":\"true\",\"msg\":\"保存成功！\"}";
			else
				return "{\"success\":\"false\",\"msg\":\"保存失败！\"}";
			case "U":// 常用分类设置信息
				 isSuccess = cyflszService.doUpdate(tpb);
				 if (isSuccess)
				 return "{\"success\":\"true\",\"msg\":\"保存成功！\"}";
				 else
				 return "{\"success\":\"false\",\"msg\":\"保存失败！\"}";
			default:
				return "{\"success\":\"false\",\"msg\":\"参数传入有误！\"}";
		}
	}
	
	@RequestMapping(value = "/doCheck",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doCheck() {
		String lx = this.getPageData().getString("lx");
		String operateType = this.getPageData().getString("operateType");
		boolean b = cyflszService.doCheck(lx,operateType);
		if(b){
			return "{\"success\":\"true\",\"msg\":\"ok！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"最多只能添加五条数据！\"}";
		}
	}
}