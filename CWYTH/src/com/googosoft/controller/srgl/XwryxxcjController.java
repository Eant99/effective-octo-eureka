package com.googosoft.controller.srgl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.srgl.xzsblr.CW_XWRYXXB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.base.ProvincesService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.srgl.xzsblr.XwryxxcjService;
import com.googosoft.service.wsbx.jcsz.WldwszService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;

@Controller
@RequestMapping(value = "/xwryxxcj")
public class XwryxxcjController extends BaseController {
	
	@Resource(name="wldwszService")
	private WldwszService wldwszService;
	@Resource(name="xwryxxcjService")
	private XwryxxcjService xwryxxcjService;
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="dictService")
	private DictService dictService;//单例
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	@Resource(name="provincesService")
	private ProvincesService provincesService;
	
	//获取列表数据
			@RequestMapping(value="getPageList",produces="text/html;charset=UTF-8")
			@ResponseBody
			public Object getPageList() {

				PageList pageList = new PageList();
				//设置查询字段名
				StringBuffer sqltext = new StringBuffer();
				sqltext.append(" K.GUID,K.XH, K.XM,K.XBM,K.CSRQ, K.SFZJLXM, K.SFZH, K.YHMC, K.KHYHH, K.BZ,K.LHH ");
				pageList.setSqlText(sqltext.toString());
				//设置表名
				pageList.setTableName("(select K.GUID,K.XH,K.XM,(select b.MC from gx_sys_dmb b where b.zl = '20' and b.dm = K.xbm) as xbm,(select b.MC from gx_sys_dmb b where b.zl = '111' and b.dm = K.SFZJLXM) as SFZJLXM,K.CSRQ,K.SFZH,K.YHMC,K.KHYHH, K.BZ,K.LHH from CW_XWRYXXB K) K ");
				//设置表主键名
				pageList.setKeyId("guid");
				PageData pd = this.getPageData();
				//页面数据
			    pageList = pageService.findPageList(pd,pageList);
				Gson gson = new Gson();
				PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
				return pageInfo.toJson();
			}
	//跳转到校外人员信息列表页面
	@RequestMapping(value = "/goXwryxxcjListPage")
	public ModelAndView goXwryxxcjListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String ymbz = pd.getString("ymbz");
		mv.addObject("ymbz",ymbz);
		mv.setViewName("srgl/xwryxxcj/xwryxxcj_list");
		return mv;
	}
	//跳转到校外人员信息增加页面
	@RequestMapping(value="/goXwryxxcjEditPage")
	public ModelAndView goXwryxxcjEditPage(){
		//定义主键guid
		String guid =this.get32UUID();
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		mv.addObject("operateType",operateType);
		String bz = pd.getString("bz");
		mv.addObject("bz",bz);
		System.err.println("bz==="+bz);
		List gjList = dictService.getJcwjlx(Constant.GB);
		mv.addObject("gjList", gjList);
		List xbList = dictService.getJcwjlx(Constant.SEX);
		mv.addObject("xbList", xbList);
		List zjlxList = dictService.getJcwjlx(Constant.ZJLX);
		mv.addObject("zjlxList", zjlxList);
		List dqlist = provincesService.getProvicesList();
		mv.addObject("dqlist", dqlist);
		List mzlist = dictService.getJcwjlx(Constant.MZ);
		mv.addObject("mzlist", mzlist);
		//传guid到页面
		mv.addObject("guid",guid);
		mv.setViewName("srgl/xwryxxcj/xwryxxcj_edit");		
		return mv;
	}
	/**
	 * 跳转到校外人员信息编辑页面
	 * @return
	 */
	@RequestMapping(value="/goXwryxxcjEditPage1",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goXwryxxcjEditPage1(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String operateType = pd.getString("operateType");
		ModelAndView mv = this.getModelAndView();
		Map<?, ?>  map = xwryxxcjService.getObjectById(guid);
		//传guid到页面
		List gjList = dictService.getJcwjlx(Constant.GB);
		mv.addObject("gjList", gjList);
		List mzlist = dictService.getJcwjlx(Constant.MZ);
		mv.addObject("mzlist", mzlist);
		List dqlist = provincesService.getProvicesList();
		mv.addObject("dqlist", dqlist);
		mv.addObject("xwryOb", map);
		mv.addObject("guid",guid);
		mv.addObject("operateType",operateType);
		mv.setViewName("srgl/xwryxxcj/xwryxxcj_edit");		
		return mv;
	}
	/**
	 * 保存
	 * @param dwb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(CW_XWRYXXB xwry){
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		int result=0;
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		Date currDay = new Date();
		SimpleDateFormat df  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		xwry.setCZRQ(df.format(currDay));
		xwry.setCZR(loginId);
		String b = "";
		if("C".equals(operateType)){  //新增
			xwry.setXH(AutoKey.createDwbh("CW_XWRYXXB", "xh", "6"));
			result = xwryxxcjService.doAdd(xwry);
			if(result==1){
				b = "{\"success\":\"true\",\"gid\":\""+xwry.getGUID()+"\",\"msg\":\"信息保存成功!\"}";
			}else{
				b = "{\"success\":\"false\",\"gid\":\""+xwry.getGUID()+"\",\"msg\":\"信息保存失败!\"}";
			}
		}else if("U".equals(operateType)){
			String guid = pd.getString("guid");
			xwry.setGUID(guid);
			try{
				result = xwryxxcjService.doUpdate(xwry);
			}catch(Exception  e){
			System.out.print(e);
			}
			if(result==1)
			{
				b = "{\"success\":\"true\",\"gid\":\""+xwry.getGUID()+"\",\"msg\":\"信息保存成功！\"}";
			}
			else
			{
				b = "{\"success\":\"false\",\"gid\":\""+xwry.getGUID()+"\",\"msg\":\"信息保存失败！\"}";
			}	
		}else{
			b = "{\"success\":\"false\",\"gid\":\"\",\"msg\":\"operateType参数不能为空！！！\"}";
		}
		return b;
	}
	/**
	 * 批量删除
	 * @return
	 */
	@RequestMapping(value="/doDelete2",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete2(){
		PageData pd = this.getPageData();
		String bzbh = pd.getString("guid");
    	int k = xwryxxcjService.doDelete2(bzbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	/**
	 * 单条删除
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String bzbh = pd.getString("guid");
    	int k = xwryxxcjService.doDelete(bzbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	/**
	 * 导出excel
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpSession session) {
		PageData pd = this.getPageData();
		String guid = pd.getString("id");
		String sszt = Constant.getztid(session);
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.xwryxxcjService.expExcel(realfile, shortfileurl,searchValue,guid,sszt);
	}
}
