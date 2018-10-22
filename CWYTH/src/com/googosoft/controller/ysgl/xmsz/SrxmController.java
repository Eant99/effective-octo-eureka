package com.googosoft.controller.ysgl.xmsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.xmsz.SrxmService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;


@Controller
@RequestMapping("ysgl/xmsz/srxm")
public class SrxmController extends BaseController{

	@Resource(name="srxmService")
	SrxmService srxmService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name="pageService")
	PageService pageService;
	//跳转到项目信息列表页面
	@RequestMapping("/goSrxmPage")
	public ModelAndView goSrxmPage() {
		ModelAndView mv = this.getModelAndView();
		iniSelect(mv);
		mv.setViewName("ysgl/xmsz/srxm/srxm_list");
		return mv;
	}
	//获取列表数据
	@RequestMapping(value="getSrxmPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getSrxmPageData() {
//		List<Map<String, Object>> srxmList = srxmService.getSrxmList();
//		PageInfo pageInfo = new PageInfo("1",""+srxmList.size(),""+srxmList.size(),new Gson().toJson(srxmList));
//		return pageInfo.toJson();
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("A.GUID,ROWNUM AS \"_XHFF\",A.SRXMBH,A.SRXMMC,A.XMFL,A.SRBZ,A.BZ,(SELECT B.MC FROM GX_SYS_DMB B WHERE B.ZL = '"+Constant.XMFL+"' AND B.DM = A.XMFL) AS XMFLMC");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName("CW_SRXMB A");
		//设置表主键名
		pageList.setKeyId("guid");
		PageData pd = this.getPageData();
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	//初始化添加和编辑页面的下拉框数据
	public void iniSelect(ModelAndView mv) {
		mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
		String loginId = LUser.getGuid();
		mv.addObject("loginId",loginId);
	}
	//跳转到项目信息添加页面
	@RequestMapping("/goSrxmAddPage")
	public ModelAndView goSrxmAddPage() {
		ModelAndView mv = this.getModelAndView();
		iniSelect(mv);
		mv.setViewName("ysgl/xmsz/srxm/srxm_add");
		return mv;
	}
	//添加项目信息
	@RequestMapping("/srxmAdd")
	@ResponseBody
	public Object srxmAdd() {
		if(srxmService.addSrxm(this.getPageData()) > 0) {
			return "{\"success\":true}";
		}else {
			return "{\"success\":false}";
		}
	}
	//跳转到项目信息编辑页面
	@RequestMapping("/goSrxmEditPage")
	public ModelAndView goSrxmEditPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		iniSelect(mv);
		Map<String, Object> srxm = srxmService.getSrxmMapById(pd.getString("guid"));
		mv.addObject("srxm",srxm);
		mv.setViewName("ysgl/xmsz/srxm/srxm_edit");
		return mv;
	}
	//编辑项目信息
	@RequestMapping("/srxmEdit")
	@ResponseBody
	public Object srxmEdit() {
		if(srxmService.editSrxm(this.getPageData()) > 0) {
			return "{\"success\":true}";
		}else {
			return "{\"success\":false}";
		}
	}
	//删除
	@RequestMapping("/srxmDelete")
	@ResponseBody
	public Object delete() {
		if(srxmService.deleteSrxm(this.getPageData()) > 0) {
			return "{\"success\":true}";
		}else {
			return "{\"success\":false}";
		}
	}
	//检查项目编号是否存在
	@RequestMapping("/checkSrxmbhExist")
	@ResponseBody
	public Object checkSrxmbhExist() {
		if(srxmService.checkSrxmbhExist(this.getPageData())) {
			return "{\"exist\":true}";
		}else {
			return "{\"exist\":false}";
		}
	}
	//收入项目
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchValue = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("SELECT A.*,ROWNUM AS \"_XH\",(SELECT B.MC FROM GX_SYS_DMB B WHERE B.ZL = '"+Constant.XMFL+"' AND B.DM = A.XMFL) AS XMFLMC FROM CW_SRXMB A ");
		sqltext.append("where 1 = 1");//where条件
		String guid = pd.getString("id");
		if(Validate.noNull(guid)){
			sqltext.append(" AND A.GUID IN ('"+guid+"') ");
		}
		sqltext.append(" ORDER BY A.SRXMBH ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("srxmbh");
		m1.setShowname("收入项目编号");
		mlist.add(m1);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("srxmmc");
		m2.setShowname("收入项目名称");
		mlist.add(m2);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("xmflmc");
		m3.setShowname("项目分类");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("srbz");
		m4.setShowname("收入标准");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("bz");
		m5.setShowname("备注");
		mlist.add(m5);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
}
