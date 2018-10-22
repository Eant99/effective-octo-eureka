package com.googosoft.controller.fzgn.jcsz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.CW_JSYHZHB;
import com.googosoft.pojo.fzgn.jcsz.CW_XJXXB;
import com.googosoft.pojo.fzgn.jcsz.CW_XSXXB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.base.ProvincesService;
import com.googosoft.service.fzgn.jcsz.XjxxService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 学生信息
 * 
 * @author googosoft
 * 
 */
@Controller
@RequestMapping(value="/xsxx")
public class XsxxController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	@Resource(name="xjxxService")
	private XjxxService xjxxService;
	
	@Resource(name="provincesService")
	private ProvincesService provincesService;
	/**
	 * 跳转学生列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goXsxxListPage")
	public ModelAndView goXsxxListPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
//		List<Map<String, Object>> sex = dictService.getDict(Constant.SEX);//性别
//		mv.addObject("sexList",sex);
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		mv.setViewName("fzgn/jcsz/xsxxsz/xsxx_list");
		return mv;
		
	}
	
	/**
	 * 学籍信息弹出窗
	 */
	@RequestMapping(value="/zdpage")
	public ModelAndView goZdxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("fzgn/jcsz/xsxxsz/xjxx_list");
		return mv;
	}

	/**
	 * 获取学生列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" * ");
		
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("(select  A.GUID AS GUID, A.XH AS XH,A.XM AS XM, A.CSRQ AS CSRQ," +
				"(SELECT MC FROM GX_SYS_DMB WHERE ZL = '103' AND DM =  (select xslb  from CW_XJXXB b where b.xh = a.guid)) AS XSLB," +
				" A.SFZH AS SFZH,(SELECT MC FROM GX_SYS_DMB WHERE ZL='20' AND DM=A.XB_M)AS XB," +
				"(SELECT MC FROM GX_SYS_DMB WHERE ZL='104' AND DM=A.CSD_M)AS CSD, (SELECT MC FROM GX_SYS_DMB WHERE ZL='105' AND DM=A.MZ_M)AS MZ, " +
				"(SELECT MC FROM GX_SYS_DMB WHERE ZL='02' AND DM=A.GJDQ_M)AS GJDQ,(SELECT MC FROM GX_SYS_DMB WHERE ZL='111' AND DM=A.SFZJLX_M)AS SFZJLX," +
				"(SELECT MC FROM GX_SYS_DMB WHERE ZL='108' AND DM=A.HYZK_M)AS HYZK,(SELECT MC FROM GX_SYS_DMB WHERE ZL='109' AND DM=A.ZZMM_M)AS ZZMM," +
				"(SELECT MC FROM GX_SYS_DMB WHERE ZL='110' AND DM=A.JKZK_M)AS JKZK, (select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d " +
				"where d.bmh=(select yxbh from CW_XJXXB b where b.xh = a.guid))AS SZYX, (select d.bmh from GX_SYS_DWB d where " +
				"d.bmh=(select yxbh from CW_XJXXB b where b.xh = a.guid))AS SZYX1, (SELECT ZYMC FROM CW_ZYXXB Z WHERE Z.ZYBH = " +
				"(select zybh from CW_XJXXB b where b.xh=a.guid))AS SZZY, (SELECT BJMC FROM CW_BJXXB X WHERE X.BJBH = " +
				"(select bjbh from CW_XJXXB b where b.xh=a.guid))AS SZBJ,(SELECT NJMC FROM CW_NJXXB N WHERE N.NJBH = " +
				"(select njbh from CW_XJXXB b where b.xh=a.guid))AS SZNJ from CW_XSXXB A ) t ");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere="";
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");
		String lrybh = LUser.getRybh();//获取当前登录人员编号
	    strWhere+=pageService.getDwqxWhereSql1(lrybh, "(select d.bmh from GX_SYS_DWB d where d.bmh=(select yxbh from CW_XJXXB b where b.xh = t.guid))", dwbh, true);//单位权限
//	    strWhere+=pageService.getDwqxWhereSql1(lrybh, "(select d.bmh from GX_SYS_DWB d where d.bmh=(select yxbh from CW_XJXXB b where b.xh = t.xh))", dwbh, true);//单位权限
		pageList.setStrWhere(strWhere);
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping("/getPageList2")
	@ResponseBody
	public Object getPageList2() {
		String datas = SendHttpUtil.sendPost(SystemSet.address+"/xsxx/getPageList", "");
		List<Map<String, Object>> list = SendHttpUtil.getResultToList(datas);
		PageInfo pageInfo = new PageInfo("1", ""+list.size(), ""+list.size(), datas);
//		PageInfo pageInfo = new PageInfo("2","2","2",datas);
		return pageInfo.toJson();
	}
	/**
	 * 编辑学生银行账号信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editJsyhzh",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editJsyhzh(CW_JSYHZHB jsyhzhb)throws Exception{
		System.out.println("添加学生银行账号");
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();		
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		//根据角色编号（jsbh对应学生guid）删除学生的银行卡号
		String jsbh= pd.getString("jsbh");
		xsxxService.doDeleteJsyhzh(jsbh, jsyhzhb);
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			//String guid = this.get32UUID();//创建主键
			String guid = (String) map.get("guid");
			       jsbh = (String) map.get("jsbh1");
			String khyh = (String) map.get("khyh1");
			String khyhzh = (String) map.get("khyhzh1");
			String yhlhh = (String) map.get("yhlhh1");
			if(guid.length()==0) {
				guid=this.get32UUID();
			}
			    //将字段放入wldwmxb
			    jsyhzhb.setGuid(guid);
			    jsyhzhb.setJsbh(jsbh);
			    jsyhzhb.setKhyh(khyh);
			    jsyhzhb.setKhyhzh(khyhzh);
			    jsyhzhb.setYhlhh(yhlhh);
				//增加
			    xsxxService.doAddJsyhzh(jsyhzhb);
		}
		b="success";
		return b;
	}
	/**
	 * 获取单个学生详细信息（增加、修改）
	 * @return
	 */
	@RequestMapping(value="/goXsxxPage")
	public ModelAndView goXsxxPage(){
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> xxlb = dictService.getDict(Constant.XSLB);//学生类别
		List<Map<String, Object>> mz = dictService.getDict(Constant.MZ);//民族
		List<Map<String, Object>> gj = dictService.getDict(Constant.GB);//国籍
		List<Map<String, Object>> zjlxm = dictService.getDict(Constant.ZJLX);//证件类型
		List<Map<String, Object>> hyzk = dictService.getDict(Constant.HYZK);//婚姻状况
		List<Map<String, Object>> sex = dictService.getDict(Constant.SEX);//性别
		List<Map<String, Object>> zzmm = dictService.getDict(Constant.ZZMM);//婚姻状况
		List<Map<String, Object>> jkzk = dictService.getDict(Constant.JKZK);//健康状况
		List qydm = provincesService.getProvicesList();
		mv.addObject("xxlbList",xxlb);
		mv.addObject("qydmList",qydm);
		mv.addObject("mzList", mz);
		mv.addObject("gjList", gj);
		mv.addObject("zjlxmList", zjlxm);
		mv.addObject("hyzkList", hyzk);
		mv.addObject("sexList", sex);
		mv.addObject("zzmmList", zzmm);
		mv.addObject("jkzkList", jkzk);
		String operateType = this.getPageData().getString("operateType");
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		mv.addObject("loginId", loginId);
		if(operateType.equals("C"))
		{
			//String guid =GenAutoKey.createRybh("cw_xsxxb", "guid", "32");
			String guid = this.get32UUID();
			Map<String,String> map = new HashMap<String, String>();
			map.put("guid", guid);
			map.put("dwbh", CommonUtil.bmhTomc(this.getPageData().getString("dwbh")));
			mv.addObject("info",map);
			mv.addObject("guid",guid);
		}
		else if(operateType.equals("U")||operateType.equals("L"))
		{
			Map map = xsxxService.getObjectById(this.getPageData().getString("guid"));
			
			mv.addObject("xsxx",map);
			mv.addObject("loginId", loginId);
			mv.addObject("guid",this.getPageData().getString("guid"));
		}
		mv.setViewName("fzgn/jcsz/xsxxsz/xsxx_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	//初始化学生基本信息页面下拉框数据
	public void initialXsxxSelect(ModelAndView mv) {
		String url = "http://localhost:8081/demo/dict";
		List<Map<String, Object>> qydm =SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.QYDM));//出生地区
		List<Map<String, Object>> mz = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.MZ));//民族
		List<Map<String, Object>> gj = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.GB));//国家
		List<Map<String, Object>> zjlxm = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.ZJLX));//证件类型
		List<Map<String, Object>> hyzk = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.HYZK));//婚姻状况
		List<Map<String, Object>> sex = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.SEX));//性别
		List<Map<String, Object>> zzmm = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.ZZMM));//政治面貌
		List<Map<String, Object>> jkzk = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.JKZK));//健康状况
		mv.addObject("qydmList",qydm);
		mv.addObject("mzList", mz);
		mv.addObject("gjList", gj);
		mv.addObject("zjlxmList", zjlxm);
		mv.addObject("hyzkList", hyzk);
		mv.addObject("sexList", sex);
		mv.addObject("zzmmList", zzmm);
		mv.addObject("jkzkList", jkzk);
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		mv.addObject("loginId", loginId);
	}
	//初始化学籍信息页面下拉框数据
		public void initialXjxxSelect(ModelAndView mv) {
			String url = "http://localhost:8081/demo/dict";
			List<Map<String, Object>> xxlb = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.XSLB));//学生类别
			List<Map<String, Object>> xkml = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.XKML));//学科门类
			List<Map<String, Object>> pyfs = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.PYFS));//培养方式
			List<Map<String, Object>> yjfx = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.YJFX));//研究方向
			List<Map<String, Object>> hdxlfs = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.HDXLFS));//获得学历方式
			List<Map<String, Object>> pycc = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.PYCC));//培养层次
			List<Map<String, Object>> ldfs = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.LDFS));//连读方式
			List<Map<String, Object>> xsdqzt = SendHttpUtil.getResultToList(SendHttpUtil.sendPost(url, "zl="+Constant.XSDQZT));//学生当前状态
			mv.addObject("xslbList",xxlb);
			mv.addObject("xkmlList",xkml);
			mv.addObject("pyfsList", pyfs);
			mv.addObject("yjfxList", yjfx);
			mv.addObject("hdxlfsList", hdxlfs);
			mv.addObject("pyccList", pycc);
			mv.addObject("ldfsList", ldfs);
			mv.addObject("xsdqztList", xsdqzt);
			String rybh = LUser.getRybh();
			String loginId = xsxxService.getLoginIdByRybh(rybh);
			mv.addObject("loginId", loginId);
		}
	//跳转到添加页面
	@RequestMapping(value="/goXsxxAddPage")
	public ModelAndView goXsxxPage2(){
		ModelAndView mv = this.getModelAndView();
		initialXsxxSelect(mv);
		String guid =GenAutoKey.get32UUID();
		Map<String,String> map = new HashMap<String, String>();
		map.put("guid", guid);
		map.put("dwbh", CommonUtil.bmhTomc(this.getPageData().getString("dwbh")));
		mv.addObject("info",map);
		mv.setViewName("fzgn/jcsz/xsxxsz/xsxx_add");
		return mv;
	}
	//跳转到学籍信息添加页面
	@RequestMapping(value="/goXjxxAddPage")
	public ModelAndView goXjxxPage2(){
		ModelAndView mv = this.getModelAndView();
		initialXjxxSelect(mv);
		String guid =GenAutoKey.get32UUID();
		Map<String,String> map = new HashMap<String, String>();
		map.put("guid", guid);
		map.put("dwbh", CommonUtil.bmhTomc(this.getPageData().getString("dwbh")));
		mv.addObject("info",map);
		mv.setViewName("fzgn/jcsz/xsxxsz/xjxx_add");
		return mv;
	}
	//跳转到编辑页面
	@RequestMapping(value="/goXsxxEditPage")
	public ModelAndView goXsxxEditPage(){
		ModelAndView mv = this.getModelAndView();
		initialXsxxSelect(mv);
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String datas = SendHttpUtil.sendPost("http://localhost:8081/xsxx/getXsxx", "guid="+guid);
		HashMap<String,String> map = SendHttpUtil.getResultToMap(datas);
		mv.addObject("xsxx",map);
		mv.setViewName("fzgn/jcsz/xsxxsz/xsxx_edit");
		return mv;
	}
	//跳转到学籍信息编辑页面
	@RequestMapping(value="/goXjxxEditPage")
	public ModelAndView goXjxxEditPage(){
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> xxlb =dictService.getDict(Constant.XSLB);//学生类别
		List<Map<String, Object>> xkml =dictService.getDict(Constant.XKML);//学科门类
		List<Map<String, Object>> pyfs =dictService.getDict(Constant.PYFS);//培养方式
		List<Map<String, Object>> yjfx =dictService.getDict(Constant.YJFX);//研究方向
		List<Map<String, Object>> hdxlfs =dictService.getDict(Constant.HDXLFS); //获得学历方式
		List<Map<String, Object>> pycc =dictService.getDict(Constant.PYCC); //培养层次
		List<Map<String, Object>> ldfs =dictService.getDict(Constant.LDFS); //连读方式
		List<Map<String, Object>> xsdqzt =dictService.getDict(Constant.XSDQZT); //学生当前状态
		mv.addObject("xslbList",xxlb);
		mv.addObject("xkmlList",xkml);
		mv.addObject("pyfsList", pyfs);
		mv.addObject("yjfxList", yjfx);
		mv.addObject("hdxlfsList", hdxlfs);
		mv.addObject("pyccList", pycc);
		mv.addObject("ldfsList", ldfs);
		mv.addObject("xsdqztList", xsdqzt);
		String operateType = this.getPageData().getString("operateType");
		String xh = this.getPageData().getString("xh");
		
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		mv.addObject("loginId", loginId);
		mv.addObject("zbguid",this.getPageData().getString("guid"));
		if(operateType.equals("C"))
		{
			String guid =GenAutoKey.createRybh("cw_xjxxb", "guid", "32");
			Map<String,String> map = new HashMap<String, String>();
			map.put("guid", guid);
			map.put("dwbh", CommonUtil.bmhTomc(this.getPageData().getString("dwbh")));
			mv.addObject("info",map);
			//mv.addObject("xh",xh);
		}
		else if(operateType.equals("U")||operateType.equals("L"))
		{
			String zbguid = this.getPageData().getString("guid");
			Map map = xjxxService.getObjectById(zbguid);
			mv.addObject("xjxx",map);
			mv.addObject("loginId", loginId);
			mv.addObject("zbguid",this.getPageData().getString("guid"));;
			List list = xsxxService.getObjectByIdYhzh(this.getPageData().getString("guid"));
			mv.addObject("jsyhzh",list);
			mv.addObject("xh",xh);
		}
		mv.setViewName("fzgn/jcsz/xsxxsz/xjxx_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 保存
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(CW_XSXXB xsxx)
	{
		String operateType = this.getPageData().getString("operateType");
		int result=0;
//		String zy = Validate.isNullToDefaultString(xsxx.getZy(), "");
//		String bj = Validate.isNullToDefaultString(xsxx.getBj(), "");
//		String nj = Validate.isNullToDefaultString(xsxx.getNj(), "");
//		if(Validate.noNull(zy)&&zy.indexOf(")")>0){
//			xsxx.setZy(zy.substring(1,zy.indexOf(")")));
//		}
//		if(Validate.noNull(nj)&&nj.indexOf(")")>0){
//			xsxx.setNj(nj.substring(1,nj.indexOf(")")));
//		}
//		if(Validate.noNull(bj)&&bj.indexOf(")")>0){
//			xsxx.setBj(bj.substring(1,bj.indexOf(")")));
//		}
		if("C".equals(operateType))
		{
			//判断部门号是否重复
			boolean checkbmh=xsxxService.checkRyb(xsxx.getXh());
			if(checkbmh)
			{
				return MessageBox.show(false, "该学号对应的用户已经存在，请重新输入！");
			}
			//String guid =this.get32UUID();//生成主键id
			//xsxx.setGuid(guid);
			result = xsxxService.doAdd(xsxx);
			if(result>0)
			{
				return "{success:'true',msg:'信息保存成功！',rybh:'"+xsxx.getXh()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))
		{
			if(!xsxx.getXh().equals(xsxxService.getObjectById(xsxx.getGuid()).get("XH").toString()))
			{
				boolean checkrygh= xsxxService.checkRyb(xsxx.getXh());
				if(checkrygh)
				{
					return MessageBox.show(false, "该学号对应的用户已经存在，请重新输入！");
				}
			}
			result = xsxxService.doUpdate(xsxx);
			if(result>0)
			{
				return "{success:'true',msg:'信息保存成功！',rybh:'"+xsxx.getGuid()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	/**
	 * 保存
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value="/doSaveXj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(CW_XJXXB xjxx)
	{
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		String zy = Validate.isNullToDefaultString(xjxx.getZy(), "");
		String bj = Validate.isNullToDefaultString(xjxx.getSzbh(), "");
		String nj = Validate.isNullToDefaultString(xjxx.getSznj(), "");
		String yxsh = Validate.isNullToDefaultString(xjxx.getYxsh(), "");
		String dsh = Validate.isNullToDefaultString(xjxx.getDsh(), "");
		if(Validate.noNull(zy)&&zy.indexOf(")")>0){
			xjxx.setZy(zy.substring(1,zy.indexOf(")")));
		}
		if(Validate.noNull(nj)&&nj.indexOf(")")>0){
			xjxx.setSznj(nj.substring(1,nj.indexOf(")")));
		}
		if(Validate.noNull(bj)&&bj.indexOf(")")>0){
			xjxx.setSzbh(bj.substring(1,bj.indexOf(")")));
		}
		if(Validate.noNull(yxsh)&&yxsh.indexOf(")")>0){
			xjxx.setYxsh(yxsh.substring(1,yxsh.indexOf(")")));
		}
		if(Validate.noNull(dsh)&&dsh.indexOf(")")>0){
			xjxx.setDsh(dsh.substring(1,dsh.indexOf(")")));
		}
		String zbguid = this.getPageData().getString("zbguid");
		if("C".equals(operateType))
		{
			
			//判断部门号是否重复
			boolean checkbmh=xsxxService.checkRyb(xjxx.getXh());
			if(checkbmh)
			{
				return MessageBox.show(false, "该学号对应的用户已经存在，请重新输入！");
			}
			String guid =this.get32UUID();//生成主键id
			xjxx.setGuid(guid);
			result = xjxxService.doAdd(xjxx,zbguid);
			if(result>0)
			{
				return "{success:'true',msg:'信息保存成功！',rybh:'"+xjxx.getXh()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))
		{
			
			result = xjxxService.doUpdate(xjxx,zbguid);
			if(result>0)
			{
				return "{success:'true',msg:'信息保存成功！',rybh:'"+xjxx.getGuid()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		String xh = this.getPageData().getString("xh");
		System.out.println("============"+xh);
		int i = xsxxService.doDel(xh);
		if(i>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
		
	}
	/**
	 * 导出人员信息Excel
	 * @return
	 */
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
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		// 设置查询字段名
		sqltext.append(" SELECT");
		sqltext.append(" A.GUID AS GUID,");
		sqltext.append(" A.XH AS XH,");
		sqltext.append(" A.XM AS XM,");
		sqltext.append(" A.CSRQ AS CSRQ,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL = '103' AND DM =  (select xslb  from CW_XJXXB b where b.xh = a.xh)) AS XSLB,");
		sqltext.append(" A.SFZH AS SFZH,");
		sqltext.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='20' AND DM=A.XB_M)AS XB,");
		sqltext.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='104' AND DM=A.CSD_M)AS CSD,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='105' AND DM=A.MZ_M)AS MZ, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='02' AND DM=A.GJDQ_M)AS GJDQ,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='111' AND DM=A.SFZJLX_M)AS SFZJLX, ");
		sqltext.append("(SELECT MC FROM GX_SYS_DMB WHERE ZL='108' AND DM=A.HYZK_M)AS HYZK,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='109' AND DM=A.ZZMM_M)AS ZZMM, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='110' AND DM=A.JKZK_M)AS JKZK,");
		sqltext.append(" (select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.bmh=(select yxbh from CW_XJXXB b where b.xh = a.xh))AS SZYX,");
		sqltext.append(" (SELECT ZYMC FROM CW_ZYXXB Z WHERE Z.ZYBH = (select zybh from CW_XJXXB b where b.xh=a.xh))AS SZZY,");
		sqltext.append(" (SELECT BJMC FROM CW_BJXXB X WHERE X.BJBH = (select bjbh from CW_XJXXB b where b.xh=a.xh))AS SZBJ,");
		sqltext.append(" (SELECT NJMC FROM CW_NJXXB N WHERE N.NJBH = (select njbh from CW_XJXXB b where b.xh=a.xh))AS SZNJ");
		sqltext.append(" FROM CW_XSXXB A WHERE 1=1");
		String dwbh = pd.getString("treedwbh");
		if(Validate.noNull(dwbh)){//左侧单位树筛选
			sqltext.append(" and A.SZYX in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + dwbh + "' )");
		}else {//单位权限
			sqltext.append(pageService.getQxsql(LUser.getRybh(), "A.SZYX", "D"));
		}
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("guid");
		if(Validate.noNull(id)){
			sqltext.append(" AND A.guid IN ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" ORDER BY A.guid ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("xh");
		m.setShowname("学号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xm");
		m.setShowname("姓名");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xb");
		m.setShowname("性别");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("csrq");
		m.setShowname("出生日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xslb");
		m.setShowname("学生类别");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("szyx");
		m.setShowname("所在院系");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("szzy");
		m.setShowname("专业");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sznj");
		m.setShowname("年级");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("szbj");
		m.setShowname("班级");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("mz");
		m.setShowname("民族");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sfzh");
		m.setShowname("身份证件类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sfzh");
		m.setShowname("身份证件号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zzmm");
		m.setShowname("政治面貌");
		mlist.add(m);
		m = null;

		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	/**
	 * 导出学生信息Excel   wzd
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		String guid = pd.getString("id");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.xsxxService.expExcel(realfile, shortfileurl, guid,rybh,searchValue);
	}
	/**
	 * 导出人员信息Excel
	 * @return
	 */
	@RequestMapping(value="/expExcelZhtj",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcelZhtj(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select T.GUID AS GUID, T.XH AS XH,  T.XM AS XM,  T.CSRQ AS CSRQ,(SELECT MC FROM GX_SYS_DMB  WHERE ZL = '103' AND DM = (select xslb from CW_XJXXB b where b.xh = T.xh)) AS XSLB, T.SFZH AS SFZH,(SELECT MC  FROM GX_SYS_DMB "); 
		sqltext.append("  WHERE ZL = '20' AND DM = T.XB_M) AS XB, (SELECT MC FROM GX_SYS_DMB  WHERE ZL = '104'   AND DM = T.CSD_M) AS CSD,  (SELECT MC FROM GX_SYS_DMB  WHERE ZL = '105'   AND DM = T.MZ_M) AS MZ, (SELECT MC  FROM GX_SYS_DMB  WHERE ZL = '02'  AND DM = T.GJDQ_M) AS GJDQ, (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '111'  AND DM = T.SFZJLX_M) AS SFZJLX,");
		sqltext.append(" (SELECT MC  FROM GX_SYS_DMB  WHERE ZL = '108'   AND DM = T.HYZK_M) AS HYZK, (SELECT MC  FROM GX_SYS_DMB  WHERE ZL = '109' AND DM = T.ZZMM_M) AS ZZMM,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB  WHERE ZL = '110'  AND DM = T.JKZK_M) AS JKZK, (select '(' || d.bmh || ')' || to_char(d.mc) from GX_SYS_DWB d where d.bmh = (select yxbh from CW_XJXXB b where b.xh = T.xh)) AS SZYX,(SELECT ZYMC  FROM CW_ZYXXB Z WHERE Z.ZYBH = (select zybh from CW_XJXXB b where b.xh = T.xh)) AS SZZY, (SELECT BJMC FROM CW_BJXXB X WHERE X.BJBH = (select bjbh from CW_XJXXB b where b.xh = T.xh)) AS SZBJ,");
		sqltext.append("  (SELECT NJMC  FROM CW_NJXXB N WHERE N.NJBH = (select njbh from CW_XJXXB b where b.xh = T.xh)) AS SZNJ from CW_XSXXB T  ORDER BY T.guid ) m ");
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("guid");
		if(Validate.noNull(id)){
			sqltext.append(" where 1=1 AND m.guid IN ('"+id.replace(",", "','")+"' ");
		}else{
			sqltext.append(" where 1=1 ");
		}
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("XH");
		m.setShowname("学号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XM");
		m.setShowname("姓名");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XB");
		m.setShowname("性别");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("CSRQ");
		m.setShowname("出生日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XSLB");
		m.setShowname("学生类别");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SZYX");
		m.setShowname("所在院系");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SZZY");
		m.setShowname("专业");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SZZY");
		m.setShowname("年级");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SZBJ");
		m.setShowname("班级");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("MZ");
		m.setShowname("民族");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("SFZJLX");
		m.setShowname("省份证件类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SFZH");
		m.setShowname("身份证号");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("ZZMM");
		m.setShowname("政治面貌");
		mlist.add(m);
		m = null;
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	/**
	 * 专业信息弹出窗
	 */
	@RequestMapping(value="/zypage")
	@ResponseBody
	public Object goZyxxPage(){
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" A.ZYBH AS ZYBH,");
		sql.append(" A.ZYMC AS ZYMC");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_ZYXXB A");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere="";
		PageData pd = this.getPageData();
		pageList.setStrWhere(strWhere);
		//页面数据
		//pageList = pageService.findPageList(pd,pageList);
		pageList = pageService.findWindowList(pd,pageList,"ZY");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 单位机构设置
	 * 通过部门号(名称)查询单位编号
	 */
	@RequestMapping(value="/getZyxx",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object getDwbh(){
		String dwmc = this.getRequestParameterValue("DWMC");
		String dwbh = this.getRequestParameterValue("DWBH");
		String dwbhs = xsxxService.findZyxx(dwmc, dwbh);
		return dwbhs ;
	}
	/**
	 * 跳转专业列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goZyPage")
	public ModelAndView goZyxxListPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String controlId = pd.getString("controlId");
		mv.addObject("dwbh", dwbh);
		mv.addObject("controlId", controlId);
		mv.setViewName("fzgn/jcsz/xsxxsz/zyxxList");
		return mv;
		
	}
	/**
	 * 跳转年级列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goNjPage")
	public ModelAndView goNjxxListPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		mv.addObject("controlId", controlId);
		mv.setViewName("fzgn/jcsz/xsxxsz/njxxList");
		return mv;
	}
	/**
	 * 跳转班级列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goBjPage")
	public ModelAndView goBjxxListPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String zy = pd.getString("zy");
		String nj = pd.getString("nj");
		String controlId = pd.getString("controlId");
		mv.addObject("dwbh", zy);
		mv.addObject("dwbh", nj);
		mv.addObject("controlId", controlId);
		mv.setViewName("fzgn/jcsz/xsxxsz/bjxxList");
		return mv;
	}
	/**
	 * 年级信息弹出窗
	 */
	@RequestMapping(value="/njpage")
	@ResponseBody
	public Object goNjxxPage(){
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" A.NJBH AS NJBH,");
		sql.append(" A.NJMC AS NJMC");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_NJXXB A");
		// 主键
		pageList.setKeyId("NJBH");
		// 设置WHERE条件
		String strWhere="";
		PageData pd = this.getPageData();
		pageList.setStrWhere(strWhere);
		//页面数据
		//pageList = pageService.findPageList(pd,pageList);
		pageList = pageService.findWindowList(pd,pageList,"NJ");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 班级信息弹出窗
	 */
	@RequestMapping(value="/bjpage")
	@ResponseBody
	public Object goBjxxPage(){
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" A.BJBH AS BJBH,");
		sql.append(" A.BJMC AS BJMC");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_BJXXB A");
		// 主键
		pageList.setKeyId("bjbh");
		// 设置WHERE条件
		String strWhere="";
		PageData pd = this.getPageData();
		
		pageList.setStrWhere(strWhere);
		//页面数据
		//pageList = pageService.findPageList(pd,pageList);
		pageList = pageService.findWindowList(pd,pageList,"BJ");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 *  导入上传
	 */
	@RequestMapping(value="/upload")
	public ModelAndView Upload(MultipartFile imageFile) throws IllegalStateException, IOException{
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
    	String pictureFile_name =  imageFile.getOriginalFilename();
		String newFileName = this.get32UUID()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
		String realPath = this.getRequest().getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
		//上传文件
		String file = realPath+"WEB-INF/file/excel/"+newFileName;
		File uploadPic = new File(file);
		if(!uploadPic.exists()){
			uploadPic.mkdirs();
		}
		//向磁盘写文件
		imageFile.transferTo(uploadPic);
		List listbt = new ArrayList();
		listbt = xsxxService.insertJcsj(file);
		mv.addObject("listbt", listbt);
		mv.addObject("file", file);
		String pname = pd.getString("pname");
		System.out.println("========"+pname);
		mv.addObject("bool","true");
		mv.addObject("pname",pname);
 		mv.setViewName("fzgn/jcsz/xsxxsz/txl_imp");
		return mv;
	}
}
