package com.googosoft.controller.systemset.qxgl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.common.DwbService;
import com.googosoft.service.fzgn.jcsz.RybService;
import com.googosoft.service.systemset.qxgl.GlqxbService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;


/**
 * 管理权限控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value="/glqxb")
public class GlqxbController extends BaseController{
	
	@Resource(name="glqxbService")
	private  GlqxbService glqxbService;//单例
	
	@Resource(name="ryxxService")
	private RybService ryxxService;
	
	@Resource (name="deptService")
	private DwbService deptService;
	
	/**
	 * 获取管理权限设置  页面
	 */
	@RequestMapping(value="goGlqxb_frmPage")
	public ModelAndView goCzqxb_frmPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/qxgl/glqxb_frm");
		return mv;
	}
	
	/**
	 * 获取单位人员树
	 * @param rybh 人员编号
	 * @return
	 */
	@RequestMapping(value="/glqxszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object glqxszTree(){
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
				return glqxbService.getPowerNode(pd,loginrybh);
			}else{
			    return glqxbService.getDwRyNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
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
	 * 获取权限单位树1
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
				return glqxbService.getPowerDwNode1(pd,loginrybh,rootPath);
			}else{
				return glqxbService.getDwNode1(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取单位人员树
	 * @param rybh 人员编号
	 * @return
	 */
	@RequestMapping(value="/qxDwRyTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object qxDwRyTree(){
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
				return glqxbService.getPowerDwRyNode(pd,loginrybh,rootPath);
			}else{
			    return glqxbService.getqxDwRyNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取地点树
	 * @param rybh 人员编号
	 * @return
	 */
	@RequestMapping(value="/ddTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ddTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String ddbh = pd.getString("ddbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(ddbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return glqxbService.getPowerDdNode(pd,loginrybh,rootPath);
			}else{
			    return glqxbService.getDdNode(pd,ddbh,rootPath);
			}
		}else{
			return "";
		}
	}

    /**
	 * 获取单位树
	 * @return
	 */
	@RequestMapping(value="/deptTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object deptTree(){
		HttpServletRequest request = this.getRequest();
		String dwbh = request.getParameter("dwbh");
		String rybh = LUser.getRybh();
		String datas = "";
		if(dwbh.equals("root")){
			datas=glqxbService.GetPowerNode(rybh,request);
		}else if(dwbh.indexOf("D")>-1){
			datas=glqxbService.GetTreeNode(dwbh,request);
		}
		return datas;
	}
	/**
	 * 获取资产类别树
	 * @return
	 */
	@RequestMapping(value="/zclbTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zclbTree(){
		HttpServletRequest request = this.getRequest();
		String rybh = LUser.getRybh();
		return glqxbService.GetZclbNode(rybh,request);
	}
    
    /**
	 * 获取管理权限设置右侧页面
	 * @return
     * @throws Exception 
	 */
    @RequestMapping(value="/getGlqxszList")
	public ModelAndView findDwxxList() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		if(Validate.isNull(rybh)){
			rybh=LUser.getRybh();
		}
		mv.addObject("rybh",rybh);
		mv.addObject("ryxx", WindowUtil.getRyxx(rybh));
		List glqxTree = glqxbService.findListByRybh(rybh);
		List zclbTree = glqxbService.findZclbTree(rybh);
		mv.addObject("glqxTree", glqxTree);
		mv.addObject("zclbTree", zclbTree);
		mv.setViewName("systemset/qxgl/glqxb_list");
		return mv;
	}
    
    
    
    /**
	 * 检查根据单位编号BH查询出的上级单位(包含BH)是否在DWBH中存在
	 * @param BH 单位编号
	 * @param DWBH 多个单位编号组成的字符串
	 * @return 存在返回false,不存在返回true
	 */
	@RequestMapping(value="/checkBydwbh",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object checkBybh(String left,String right) throws Exception{
		String result = glqxbService.checkBydwbh(left,right);
		return result ;
	}
	
	
	/**
	 * 保存管理权限信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=utf-8")
	@ResponseBody
	@Transactional
	public Object doSave(String qxsz,String szry,String flag) throws Exception{
		String b = "";
		int i = glqxbService.doSave(qxsz,szry,flag);
		if(i>0){
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}else{
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}
	
	/**
	 * 管理权限设置
	 * 通过人员工号查询人员编号存不存在
	 */
	@RequestMapping(value="/getRybh",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object getRybh(){
		String ryxm = Validate.isNullToDefault(this.getRequest().getParameter("RYXM"), "")+"";
		String loginrybh = LUser.getRybh();
		String rybh = ryxxService.findRybhByRyxm(loginrybh,ryxm);
		return rybh ;
	}
	/**
	 * 通过单位信息查询单位编号存不存在
	 */
	@RequestMapping(value="/getDwbh",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object getDwbh(){
		String ryxm = Validate.isNullToDefault(this.getRequest().getParameter("RYXM"), "")+"";
		String loginrybh =LUser.getRybh(); 
		String rybh = ryxxService.findRybhByRyxm(loginrybh,ryxm);
		return rybh ;
	}
	
	/**
	 * 根据当前登录人判断是否存在该单位权限
	 */
	@RequestMapping(value="/doCheckDwqx",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doCheckDwqx(){
		String dwmc =this.getRequestParameterValue("DWMC");
		String rybh =LUser.getRybh(); 
		String dwbh = deptService.GetKeyId(dwmc);
		if(dwbh.equalsIgnoreCase(""))
		{
		   return "-1";//单位信息不存在
		}
		else
		{
			String dwxx=deptService.doCheckRyDWQX(rybh, dwbh);
			if(dwxx.equalsIgnoreCase(""))
			  return "-2";//单位信息
			else 
			  return dwbh;
		}		
	}
	
	
	
	
	
	
	
	
}
