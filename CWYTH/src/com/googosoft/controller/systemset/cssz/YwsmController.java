package com.googosoft.controller.systemset.cssz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.pojo.systemset.cssz.ZC_YWSM;
import com.googosoft.pojo.systemset.cssz.ZC_YWSMSET;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.cssz.YwsmService;
import com.googosoft.util.Const;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 业务说明控制类
 * @author master
 */
@Controller
@RequestMapping(value="/ywsm")
public class YwsmController extends BaseController{
	
	@Resource(name="ywsmService")
	private YwsmService ywsmService;//单例
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	/**
	 * 获取业务说明设置    页面
	 */
	@RequestMapping(value="goYwsmPage")
	public ModelAndView goYwsmPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/cssz/ywsm/ywsm_list");
		return mv;
	}
	
	/**
	 * 获取业务说明信息列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getYwsmJson")
	@ResponseBody
	public Object getPageList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.ID,K.MKBH,K.MKMC,(SELECT MKMC FROM ZC_SYS_MKB WHERE MKBH =K.MKBH) MKLX ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("id");//主键
		pageList.setStrWhere(" and k.mkbh in (select mkbh from "+SystemSet.sysBz+"mkb m where m.qxbz='1')");// where条件
		pageList.setTableName("ZC_YWSM K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取业务说明信息
	 * @return
	 */
	@RequestMapping(value="/getYwsm")
	public ModelAndView getYwsm(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		String id = pd.getString("id");
		/*获取需要设置业务说明的模块*/
		List Mk_list = ywsmService.getMkb(); 
		mv.addObject("Mk_list", Mk_list);
		if(operateType.equals("C")){
			Map map = new HashMap();
			mv.addObject("ywsmb", map);
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map map = ywsmService.getObjectById(pd.getString("id"));
			mv.addObject("Content",map.get("YWSM"));
			mv.addObject("ywsmb", map);
		}
		mv.setViewName("systemset/cssz/ywsm/ywsm_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 添加业务说明信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_YWSM ywsm) throws Exception{
		HttpServletRequest request = this.getRequest();
		String content = request.getParameter("content");
		String ywlx = request.getParameter("ywlx");
		String ywmc = request.getParameter("ywmc");
		String id = request.getParameter("id");
		ywsm.setMkbh(ywlx);
		ywsm.setMkmc(ywmc);
		String operateType = request.getParameter("operateType");
		String b = "";
		int i;
		if("C".equals(operateType)){
			i = ywsmService.doAdd(ywsm, content);
			if(i==1){
				b = "{\"success\":\"true\",\"id\":\""+ywsm.getId()+"\",\"msg\":\"信息保存成功！\"}";
			}else if(i==0){
				b = "{\"success\":\"false\",\"id\":\"\",\"msg\":\"该模块已存在业务说明信息！\"}";
			}else{
				b = "{\"success\":\"false\",\"id\":\""+ywsm.getId()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){
			i = ywsmService.doUpdate(ywsm, content,id);
			if(i==1){
				b = "{\"success\":\"true\",\"id\":\""+ywsm.getId()+"\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"id\":\""+ywsm.getId()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else{
			b = "{\"success\":\"false\",\"id\":\"\",\"msg\":\"参数传入有误！\"}";
		}
		return b ;
	}
	
	/**
	 * 删除业务说明信息
	 * @param ywsm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteYwsm",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doDelete(){
		PageData pd = this.getPageData();
		String id = pd.getString("id");
		String b = "";
		int k = ywsmService.doDelete(id);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
		return b;
	}
	
	@RequestMapping(value="/ywSet",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object ywSet(){
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		String wdxx = pd.getString("wdxx");
		String b = "";
		int k = ywsmService.ywSet(mkbh,wdxx);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
		return b;
	}
	
	/**
	 * 获取业务说明信息列表页面
	 * @return
	 */
	@RequestMapping(value="/findYwsmList")
	public ModelAndView getList(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/cssz/ywsm/ywsm_list");
		return mv;
	}
	
	/**
	 * 需要业务说明的模块，点击业务说明按钮弹窗
	 * @param mkbh
	 * @return
	 */
	@RequestMapping(value="/getYwsmWin",produces = "text/html;charset=UTF-8")
	public ModelAndView getYwsmWin(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String mkbh = pd.getString("mkbh");
		String desk = pd.getString("desk");
		String sfbc = pd.getString("sfbc");
		String url = pd.getString("url");
		String type = pd.getString("type");
		String pname = pd.getString("pname");
		Map map = ywsmService.getObjectByMkbh(mkbh);
		mv.addObject("title",Validate.isNullToDefault(map.get("MKMC"),"业务说明"));
		mv.addObject("wdxx",Validate.isNullToDefault(ywsmService.findWdxx(mkbh),"0"));
		mv.addObject("Content",map.get("YWSM"));
		mv.addObject("url", url);
		mv.addObject("type", type);
		mv.addObject("mkbh", mkbh);
		mv.addObject("desk", desk);
		mv.addObject("sfbc", sfbc);
		mv.addObject("pname", pname);
		mv.addObject("ywsmb", map);
		mv.setViewName("window/ywsm/ywsm");
		return mv;
	}
	
	
	/**
	 * 
	 * @param mkbh
	 * @return
	 */
	@RequestMapping(value="/goScanPage",produces = "text/html;charset=UTF-8")
	public ModelAndView goScanPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String zbid = pd.getString("zbid");
		String basePath = pd.getString("basePath");
		String fold = pd.getString("fold");
//		String mkbh = pd.getString("mkbh");
//		String desk = pd.getString("desk");
//		String sfbc = pd.getString("sfbc");
//		String url = pd.getString("url");
//		String type = pd.getString("type");
//		String pname = pd.getString("pname");
//		Map map = ywsmService.getObjectByMkbh(mkbh);
//		mv.addObject("title",Validate.isNullToDefault(map.get("MKMC"),"业务说明"));
//		mv.addObject("wdxx",Validate.isNullToDefault(ywsmService.findWdxx(mkbh),"0"));
//		mv.addObject("Content",map.get("YWSM"));        
//		mv.addObject("url", url);
//		mv.addObject("type", type);
//		mv.addObject("mkbh", mkbh);
//		mv.addObject("desk", desk);
//		mv.addObject("sfbc", sfbc);
//		mv.addObject("pname", pname);
		Subject currentUser = SecurityUtils.getSubject();
		GX_SYS_RYB user = (GX_SYS_RYB)currentUser.getSession().getAttribute(Const.SESSION_USER);
		System.out.println(user+"@"+user.getRybh());
		mv.addObject("zbid", zbid);
		mv.addObject("rybh", user.getRybh());
		mv.addObject("basePath", basePath);
		mv.addObject("fold", fold);
		mv.setViewName("window/ywsm/gaopay");
		return mv;
	}
	
	
	/**
	 * 获取该模块是否有业务说明
	 * @return json对象 success是true：有业务说明  success是false：没有业务说明
	 */
	@RequestMapping(value="/getYwsmByMkbh",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getYwsmByMkbh(){
		Map map = ywsmService.getObjectByMkbh(this.getPageData().getString("mkbh"));
		if(map.isEmpty()){
			return "{\"success\":false}";
		}
		else{
			return "{\"success\":true}";
		}
	}
	/**业务说明是否提示
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/upSfts")
	@ResponseBody
	public Object upSfts() throws Exception{
		ZC_YWSMSET ywsmset = new ZC_YWSMSET();
		int a = Integer.parseInt(this.getPageData().getString("sfts"));
		if(a == 1){
			ywsmset.setSfts(a);
			ywsmset.setRybh(LUser.getRybh());
			ywsmset.setMkbh(this.getPageData().getString("mkbh"));
			return ywsmService.upSfts(ywsmset);
		}else{
			return null;
		}
	}

	@RequestMapping(value="/findSfts",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object findSfts(){
		ZC_YWSMSET ywsmset = new ZC_YWSMSET();
		ywsmset.setRybh(LUser.getRybh());
		ywsmset.setMkbh(this.getPageData().getString("mkbh"));
		return "{\"success\":\"true\",\"sfts\":\"" + ywsmService.findSfts(ywsmset) + "\"}";
	}
}
