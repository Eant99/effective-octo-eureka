package com.googosoft.controller.ysgl.jcsz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.common.DwbService;
import com.googosoft.service.system.tree.TreeService;
import com.googosoft.service.systemset.qxgl.GlqxbService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value="/jcsz")
public class JcszController extends BaseController{
	
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="glqxbService")
	private  GlqxbService glqxbService;//单例
	
	@Resource(name="treeService")
	private TreeService treeService;
	
	/**
	 * 单位信息弹出窗
	 */
	@RequestMapping(value="/srTree")
	public ModelAndView goSrtreePage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("ysgl/jcsz/srTree");
		return mv;
	}
	/**
	 * 单位信息弹出窗
	 */
	@RequestMapping(value="/zcTree")
	public ModelAndView goZctreePage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("ysgl/jcsz/zcTree");
		return mv;
	}
	/**
	 * 获取收入增长列表的页面
	 * @return
	 */
	@RequestMapping(value="/goSrszPage")
	public ModelAndView goSrszPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("ysgl/jcsz/srzz_list");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 获取支出增长列表的页面
	 * @return
	 */
	@RequestMapping(value="/goZcszPage")
	public ModelAndView goDdbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("ysgl/jcsz/zczz_list");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 获取权限单位树
	 * @return
	 */
	@RequestMapping(value="/qxdwTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object qxdwTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return glqxbService.getPowerDwNode(pd,loginrybh,rootPath);
			}else{
			    return glqxbService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	
	
	/**
	 * 获取权限单位树
	 * @return
	 */
	@RequestMapping(value="/qxdwTree1",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object qxdwTree1(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return glqxbService.getPowerDwNode(pd,loginrybh,rootPath);
			}else{
			    return glqxbService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" K.DWBH,K.MC,K.JC,K.DZ,TO_CHAR(K.JLRQ,'yyyy-MM-dd') AS JLRQ, DECODE(K.SFXY,'0','否','1','是')SFXY,");
		sqltext.append(" K.FGLD,DECODE(K.DWZT,'0','禁用','1','正常') AS DWZT,K.DWJC,K.MJBZ,K.PXXH,K.BMH,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.DWBB+"' AND DM=K.DWBB)AS DWBB,");
		sqltext.append(" K.BMSX,K.BZ,K.SYSBZ,K.SYSJB,K.SYSGS,K.SYSLB,K.SYSMJ,K.JLNF,K.SYSLX,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.DWXZ+"' AND M.DM = K.DWXZ) AS DWXZ,");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=K.DWLD) AS DWLD,K.DWLD AS DWLDH,");
		sqltext.append(" (SELECT NVL2(C.BMH,'('||C.BMH||')'||TO_CHAR(C.MC),'') FROM GX_SYS_DWB C WHERE C.DWBH=K.SJDW) AS SJDW,K.SJDW AS SJDWH ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName("GX_SYS_DWB K");
		//设置表主键名
		pageList.setKeyId("DWBH");
		//设置WHERE条件
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");

		String rybh = LUser.getRybh();//当前登陆者的人员编号
		pageList.setStrWhere(pageService.getDwqxWhereSql(rybh, "K.DWBH", dwbh, true)); //获取管理单位权限
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取单位树
	 * @return
	 */
	@RequestMapping(value="/dwTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object dwTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return treeService.getPowerDwNode(pd,loginrybh,rootPath);
			}else{
			    return treeService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 部门信息弹出窗
	 */
	@RequestMapping(value="/dwpage")
	public ModelAndView goDwxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String test = pd.getString("test");
		mv.addObject("controlId",controlId);
		mv.addObject("test",test);
		mv.setViewName("ysgl/jcsz/window");
		return mv;
	}
	
	

}
