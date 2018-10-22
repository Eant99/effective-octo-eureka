package com.googosoft.controller.kjhs.pzxx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.Xtxx;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.pzxx.PzfhService;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
/**
 * 凭证复核控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping("kjhs/pzxx/pzfh")
public class PzfhController extends BaseController{
	
	@Resource(name="pzfhService")
	PzfhService pzfhService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name="pageService")
	PageService pageService;
	@Autowired
	PzlrService pzlrService;
	/**
	 * 初始化当前登录人信息
	 * @param mv
	 */
	public void iniLogin(ModelAndView mv) {
		mv.addObject("loginId",LUser.getGuid());
		mv.addObject("dwmc",LUser.getDwmc());
		mv.addObject("rybh",LUser.getRybh());
		mv.addObject("ryxm",LUser.getRyxm());
	}
	/**
	 * 初始化数据字典下拉框
	 * @param mv
	 */
	public void iniSelect(ModelAndView mv) {
		mv.addObject("jsfsList",dictService.getDict(Constant.ZFFSDM));
		mv.addObject("yslxList",dictService.getDict(Constant.YSLX));
		mv.addObject("zclxList",dictService.getDict(Constant.ZCLX));
		mv.addObject("yslyList",dictService.getDict(Constant.YSLY));
	}
	/**
	 * 加载页面详细信息数据
	 * @param mv
	 * @param pd
	 */
	public void iniView(ModelAndView mv,String guid) {
		Map<String, Object> zbMap = pzlrService.getPzlrMain(guid);
		List<Map<String, Object>> mxList = pzlrService.getPzlrDetail(guid);
		mv.addObject("zbMap",zbMap);
		mv.addObject("mxList",mxList);
	}
	public void iniPrint(ModelAndView mv,String guid,String kjzd) {
		Map<String, Object> zbMap = pzlrService.getPzlrMain(guid);
		List<Map<String, Object>> mxList = pzlrService.getPzlrMx(guid, kjzd);
		mv.addObject("zbMap",zbMap);
		mv.addObject("mxList",mxList);
	}
	
	/**
	 * 初始化凭证信息
	 * @param mv
	 * @param pd
	 * @param session
	 * @return
	 */
	public String iniPzInfo(ModelAndView mv,PageData pd,HttpSession session) {
		String draw = pd.getString("draw");
		String lf = pd.getString("lf");
		//分页类型
		String type = pd.getString("type");
		String pznd = "";
		String kjqj = "";
		//所属账套
		String sszt = Constant.getztid(session);
		//凭证编号，先从参数中找，再从session中找
		String pzbh = pd.getString("pzbh");
		
		//凭证类型
		String pzlx="";
		
		//是首次加载页面，则把未记账的日期放到session中
		if(Validate.noNull(draw)) {
			//获取期末结账表中未结账的日期
			Map<String, Object> map = pzlrService.getWjzDate(sszt,pzlx);
			pznd = ""+map.get("ztnd");
			kjqj = ""+map.get("kjqj");
			session.setAttribute("pznd", pznd);
			session.setAttribute("kjqj", kjqj);
			//默认是最后一页
			if(!"lf".equals(lf)){
				type = "last";
			}
			
		}else {
			pznd = session.getAttribute("pznd")+"";
			kjqj = session.getAttribute("kjqj")+"";
		}
		 
		pzlx=Validate.isNullToDefaultString(pd.getString("pzlx"), pzlrService.getPzlx(pznd,kjqj,sszt));
		//查询数据库中已经存在的凭证编号，没有会计制度的限制
		List<String> pzbhList = pzlrService.getPzbhList(pznd,kjqj,sszt,pzlx);
		//查询凭证类型列表
		List<Map<String, Object>> pzlxList= pzlrService.getPzlxList();
		if(pzbhList.size()==0) {
			return "pzlrNull";
		}
		
		if(Validate.isNull(pzbh)) {
			pzbh = session.getAttribute("pzbh")+"";
		}
		if("change".equals(pd.getString("qcz"))||Validate.isNull(pzbh)||"lf".equals(lf)){
			pzbh=pzlrService.getminPzbh(sszt, pznd, kjqj, pzlx);
		}
		
		String maxPzbh = pzbhList.get(pzbhList.size()-1);
		String pageName = "pzlrView";
		if(type != null) {
			switch (type) {
			case "first":
				pzbh = "0001";
				break;
			case "last":
				pzbh = maxPzbh;
				break;
			case "prev":
				if(!"0001".equals(pzbh)) {
					pzbh = ""+(Integer.parseInt(pzbh) - 1);
					pzbh = pzlrService.autoFill(pzbh, 4, "0");
				}
				break;
			case "next":
				if(!maxPzbh.equals(pzbh)) {
					pzbh = ""+(Integer.parseInt(pzbh) + 1);
					pzbh = pzlrService.autoFill(pzbh, 4, "0");
				}
				break;
			case "self":
				pzbh = pzlrService.autoFill(pzbh, 4, "0");
				break;
			default:
				break;
			}
		}
		if(pzbhList.contains(pzbh)) {
			Map<String, Object> pzzbMap = pzlrService.getPzlrMain(pznd,kjqj, pzbh,sszt,pzlx);
			mv.addObject("zbMap",pzzbMap);
			session.setAttribute("pzzbGuid", pzzbMap.get("guid"));
			mv.addObject("mxList",pzlrService.getPzlrDetail(pznd, kjqj, pzbh, sszt,pzlx));
			//凭证状态
			String pzzt = pzzbMap.get("pzzt")+"";
			if("已保存".equals(pzzt)) {
				pageName = "pzlrEdit";
			}
		}
		session.setAttribute("pzbh", pzbh);
		session.setAttribute("pzlx", pzlx);
		mv.addObject("pzbh",pzbh);
		mv.addObject("pzz",pzlx);
		//凭证编号列表
		mv.addObject("pzbhList",pzbhList);
		//凭证类型列表
		mv.addObject("pzlxList",pzlxList);
		return pageName;
	}
	/**
	 * 页面之间的跳转
	 * @param session
	 * @return
	 */
	@RequestMapping("/pageSkip")
	public ModelAndView pageSkip(HttpSession session) {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String pageName = pd.getString("pageName");
		String guid = session.getAttribute("pzzbGuid")+"";
		switch (pageName) {
		case "pzlr":
			iniLogin(mv);
			iniSelect(mv);
			pageName = iniPzInfo(mv,pd,session);
			break;
		case "print":
			iniPrint(mv, guid,CommonUtil.getKjzd(session));
			break;
		default:
			break;
		}
		mv.setViewName("kjhs/pzxx/pzfh/"+pageName);
		return mv;
	}
	//获取列表数据
	@RequestMapping(value="getBxxxData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getCcywsqPageData() {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		//设置查询字段名
		String sqltext = "*";
		String tableName = "(select t.guid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.bxr) as sqr," + 
				"to_char(t.czrq,'yyyy-mm-dd') as sqrq," + 
				"(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = t.szbm) as szbm,'日常报销' as bxlx,t.bxzje as bxje" + 
				" from cw_rcbxzb t where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb)" + 
				" union" + 
				" select t.guid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.sqr) as sqr,czrq as sqrq," + 
				"(select '('||b.dwbh||')'||b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.guid = t.sqr) as szbm," + 
				"'差旅费报销' as bxlx,to_number(t.bxzje) as bxje " + 
				"from cw_clfbxzb t where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb)" + 
				" union " + 
				"select t.guid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.bxryid) as sqr,to_char(t.czrq,'yyyy-mm-dd') as sqrq," + 
				"(select '('||b.dwbh||')'||b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.guid = t.bxryid) as szbm," + 
				"'公务接待费报销' as bxlx,t.bxje from cw_gwjdfbxzb t where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb)) a";
		//设置条件
		pageList.setSqlText(sqltext);
		pageList.setTableName(tableName);
		pageList.setKeyId("djbh");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	//根据报销单生成凭证信息
	@RequestMapping(value="getPzJsonByBx",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getPzJsonByBx() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String type = pd.getString("type");
		List<Map<String, Object>> list = pzfhService.getPzJsonByBx(type,guid);
		Gson gson = new Gson();
		return "{\"size\":"+list.size()+",\"guid\":\""+guid+"\",\"data\":"+gson.toJson(list)+"}";
	}
	
	//获取交互数据
	@RequestMapping(value="getEchoData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getEchoJson() {
		PageData pd = this.getPageData();
		Map<String, Object> map = pzfhService.getEchoData(pd);
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	//获取联想输入
	@RequestMapping(value="getSuggestInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getSuggestInfo() {
		PageData pd = this.getPageData();
		return pzfhService.getSuggestInfo(pd);
	}
	/**
	 *  复核
	 * @return
	 */
	@RequestMapping(value="/Saverq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object Saverq() {
		PageData pd = this.getPageData();
		String guid =pd.getString("guid");
		String pzzt = pd.getString("pzzt");
		String fhr = LUser.getGuid();
		int result = 0;
		result =  pzfhService.updateFh(guid,pzzt,fhr);
		Gson gson = new Gson();
		if(result>0) {
			return gson.toJson(true);
		}
		return gson.toJson(false);
	}
	/**
	 * 取消复核 leefly
	 * @return
	 */
	@RequestMapping(value="/Saverqqx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object Saverqqx() {
		PageData pd = this.getPageData();
		String guid =pd.getString("guid");
		String pzzt = pd.getString("pzzt");
		String fhr = LUser.getGuid();
		int result = 0;
		result =  pzfhService.updateFhqx(guid,pzzt,fhr);
		Gson gson = new Gson();
		if(result>0) {
			return gson.toJson(true);
		}
		return gson.toJson(false);
	}
	
	@RequestMapping(value="/qxfh",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object qxfh(HttpServletRequest req,HttpServletResponse res) {
		PageData pd = this.getPageData();
		String guid =pd.getString("guid");
		String pzzt = pd.getString("pzzt");
		String fhyj = pd.getString("fhyj");
		int result = 0;
		result =  pzfhService.qxfh(guid,pzzt,fhyj);
		Gson gson = new Gson();
		if(result>0) {
			return gson.toJson(true);
		}
		return gson.toJson(false);
	}
	/**
	 *  批量复核
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/plfh",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object plfh(HttpServletRequest req,HttpServletResponse res) {
		PageData pd = this.getPageData();
		String guid =pd.getString("guid");
		String pzzt = pd.getString("pzzt");
		String qspdh = pd.getString("qspdh");
		String jspdh = pd.getString("jspdh");
		String zdr = pd.getString("zdr");
		String type = pd.getString("type");
		Gson gson = new Gson();
		if("zdrwk".equals(type)) {
			int result = 0;
			result =  pzfhService.plfh(guid,pzzt,qspdh,jspdh);
			if(result>0) {
				return gson.toJson(true);
			}
		}else if("zdr".equals(type)) {
			int result = 0;
			result =  pzfhService.plfhzdr(guid,pzzt,qspdh,jspdh,zdr);
			if(result>0) {
				return gson.toJson(true);
			}
		}
		
		return gson.toJson(false);
	}
	
	@RequestMapping(value="/plfhrq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object plfhrq(HttpServletRequest req,HttpServletResponse res) {
		PageData pd = this.getPageData();
		String guid =pd.getString("guid");
		String pzzt = pd.getString("pzzt");
		String qsrq = pd.getString("qsrq");
		String jsrq = pd.getString("jsrq");
		String zdr = pd.getString("zdr");
		String type = pd.getString("type");
		Gson gson = new Gson();
		if("zdrwk".equals(type)) {
			int result = 0;
			result =  pzfhService.plfhrq(guid,pzzt,qsrq,jsrq);
			if(result>0) {
				return gson.toJson(true);
			}
		}else if("zdr".equals(type)) {
			int result = 0;
			result =  pzfhService.plfhrqzdr(guid,pzzt,qsrq,jsrq,zdr);
			if(result>0) {
				return gson.toJson(true);
			}
		}
		return gson.toJson(false);
	}
	/**
	 * 批量打印
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/pzdy",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object pzdy(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String qspdh =pd.getString("qspdh");
		String jspdh = pd.getString("jspdh");
		String qsrq = pd.getString("qsrq");
		String jsrq = pd.getString("jsrq");
		String zdr = pd.getString("zdr");
		List finalList = new ArrayList();
		List<Map<String, Object>> zbList = pzfhService.getPzlrMain(qspdh,jspdh,qsrq,jsrq,zdr,"01','02','99",Constant.getztid(session), CommonUtil.getKjzd(session)) ;
		for(Map<String, Object> map : zbList){
			Map finalMap = new HashMap();
			List<Map<String, Object>> mxList = pzlrService.getPzlrMx(map.get("GUID")+"", CommonUtil.getKjzd(session));
			finalMap.put("zbMap", map);
			finalMap.put("mxList", mxList);
			finalList.add(finalMap);
		}
		mv.addObject("finalList",finalList);
		mv.setViewName("kjhs/pzxx/pzlr/print");
		return mv;
	}
	
	/**
	 * 复核意见 弹出窗
	 */
	@RequestMapping(value="/fhyj")
	public ModelAndView goDwxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String guid = pd.getString("guid");
		String pzzt = pd.getString("pzzt");
		mv.addObject("guid",guid);
		mv.addObject("pzzt",pzzt);
	//	mv.addObject("controlId",controlId);
		mv.setViewName("kjhs/pzxx/pzfh/fhyjWindow");
		return mv;
	}
	
	/**
	 * 批量复核 弹出窗
	 */
	@RequestMapping(value="/plfhwindow")
	public ModelAndView goplfhWindowPage(HttpSession session){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String pzz=pd.getString("pzz");
		String sszt = Constant.getztid(session);
		List zbList = pzfhService.getPzlrZbList("00",pzz,sszt);
		List zdrList = pzfhService.getZdrList("00",sszt);
		mv.addObject("guid",guid);
		mv.addObject("zbList",zbList);
		mv.addObject("zdrList",zdrList);
		mv.addObject("ryxm",LUser.getRyxm());
		mv.setViewName("kjhs/pzxx/pzfh/plfhWindow");
		return mv;
	}
	/**.
	 * 批量打印弹窗
	 * @return
	 */
	@RequestMapping(value="/printwindow")
	public ModelAndView goPrintwindowPage(HttpSession session){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String pzz=pd.getString("pzz");
		String pznd=pd.getString("pznd");
		String kjqj=pd.getString("kjqj");		
		String sszt = Constant.getztid(session);
		List zbList = pzfhService.getPzlrZbList("01','02','99",pzz,sszt);
		List zdrList = pzfhService.getZdrList("01','02','99",sszt);
		mv.addObject("zbList",zbList);
		mv.addObject("zdrList",zdrList);
		mv.addObject("ryxm",LUser.getRyxm());
		mv.setViewName("kjhs/pzxx/pzfh/pzdyWindow");
		return mv;
	}
	
	/**
	 * 查询开户银行信息
	 * @param type
	 * @param hm
	 * @return
	 */
	@RequestMapping(value="/getKhyh",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getKhyh(String type,String hm) {
		Gson gson = new Gson();
		List list = pzlrService.getKhyh(type, hm);
		if(list==null||list.size()==0){
			list = new ArrayList<Map<String,Object>>();
		} 
		return gson.toJson(list);
	}
	/**
	 * 银行录入明细弹窗
	 * @param mxid
	 * @param zbid
	 * @return
	 */
	@RequestMapping(value="/btn_lookMx")
	@ResponseBody
	public ModelAndView btn_lookMx(String mxid,String zbid){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List list = new ArrayList<Map<String,Object>>();
		list = pzlrService.getYhmxIdByZBidAndMxIdView(zbid, mxid);
		mv.addObject("list", list);
		mv.addObject("zbid", zbid);
		mv.addObject("mxid", mxid);
		mv.setViewName("kjhs/pzxx/pzlr/btn_lookMx");
		return mv;
	}
	/**
	 * 退回
	 * @return
	 */
	@RequestMapping(value="/goplthwindow")
	@ResponseBody
	public ModelAndView goplthwindow(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("loginId",LUser.getGuid());
		mv.setViewName("kjhs/pzxx/pzfh/pzlrth");
		return mv;
	}
	/**
	 * 已保存  的 退回
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(){
		Xtxx obj = new Xtxx();
		PageData pd = this.getPageData();
		String sjid = pd.getString("sjid");
		String jsr = pd.getString("jsr");
		String fbr = pd.getString("fbr");
		String xxnr = pd.getString("xxnr");
		obj.setFbr(fbr);
		obj.setSjid(sjid);
		obj.setJsr(jsr);
		obj.setXxnr(xxnr);
		return "{\"success\":\""+pzlrService.dosave( obj)+"\"}";
	}
}
