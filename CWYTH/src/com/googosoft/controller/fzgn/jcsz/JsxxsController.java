package com.googosoft.controller.fzgn.jcsz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.dao.fzgn.jcsz.CW_JZGKZXXB;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.CW_JSXXB;
import com.googosoft.pojo.fzgn.jcsz.CW_JSYHZHB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.JsxxsService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 教师信息
 * 
 * @author googosoft
 * 
 */
@Controller
@RequestMapping(value = "/jsxxs")
public class JsxxsController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;

	@Resource(name = "dictService")
	private DictService dictService;// 单例

	@Resource(name = "jsxxsService")
	private JsxxsService jsxxsService;
	@Resource(name="fileService")
	FileService fileService;
	/**
	 * 跳转教师列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goJsxxListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
//		List<Map<String, Object>> sex = dictService.getDict(Constant.SEX);// 性别
//		mv.addObject("sexList", sex);
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		String guid = LUser.getGuid();
		mv.setViewName("fzgn/jcsz/jsxxsz/jsxx_list");
		return mv;

	}
	@RequestMapping(value = "/goSecondPage")
	public ModelAndView goSecondPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		mv.setViewName("fzgn/jcsz/jsxxsz/jsxx_list");
		return mv;

	}
	/**
	 * 获取教师列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" A.GUID,");
		sql.append(" A.XH,A.XM,A.JG,A.SFZH,LXFS,A.YX,TO_CHAR(A.CJGZSJ,'YYYY-MM-DD')CJGZSJ,GL,A.PXXH,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.SEX+"' AND DM=A.XBM)AS XBM,");
		sql.append(" DECODE(ZZLX,'0','正式','1','试用','2','实习','兼职')ZZLX,");
		sql.append(" DECODE(NVL(ZZRYLY,'0'),'1','网聘','校招')ZZRYLY,");
		sql.append(" A.CSRQ,");
		sql.append(" A.CZRQ,");
		sql.append(" TO_CHAR(A.LXSJ,'YYYY-MM-DD')LXSJ,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.QYDM+"' AND DM=A.CSDM)AS CSDM,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZJLX+"' AND DM=A.SFZJLXM)AS SFZJLXM,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.MZ+"' AND DM=A.MZM)AS MZM,A.gjdqm,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.HYZK+"' AND DM=A.HYZKM)AS HYZKM,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZZMM+"' AND DM=A.ZZMMM)AS ZZMMM,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.JKZK+"' AND DM=A.JKZKM)AS JKZKM,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZDW)AS SZDW,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.WHCD+"' AND DM=A.WHCD)AS WHCD,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZYZC+"' AND DM=A.ZC)AS ZC,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZW+"' AND DM=A.ZW)AS ZW,");
		sql.append(" (SELECT D.XM FROM CW_JZGXXB D WHERE D.GUID=A.CZR)AS CZR");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_JZGXXB A");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");
		String lrybh = LUser.getRybh();// 获取当前登录人员编号
		if (Validate.noNull(dwbh)) {// 左侧单位树筛选
			strWhere +=" and A.SZDW in(SELECT DWBH FROM GX_SYS_DWB CONNECT BY PRIOR DWBH=SJDW START WITH DWBH='"
					+ dwbh + "' )";
		} else {// 单位权限
			strWhere += pageService.getQxsql(LUser.getRybh(), "A.SZDW", "D");
		}
		strWhere +=" and xh not in ('000000') ";
		strWhere += pageService.getDwqxWhereSql(lrybh, "A.SZDW", dwbh, true);// 单位权限
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取已发工资的教职工列表（代发薪资录入功能使用）
	 * @author Jiatong
	 * @throws Exception
	 */
	@RequestMapping(value = "/getxzPageList")
	@ResponseBody
	public Object getxzPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String ffpc = pd.getString("ffpc");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" A.GUID,");
		sql.append(" A.XH,A.XM,A.JG,A.SFZH,LXFS,A.YX,TO_CHAR(A.CJGZSJ,'YYYY-MM-DD')CJGZSJ,GL,A.PXXH,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.SEX+"' AND DM=A.XBM)AS XBM,");
		sql.append(" DECODE(ZZLX,'0','正式','1','试用','2','实习','兼职')ZZLX,");
		sql.append(" DECODE(NVL(ZZRYLY,'0'),'1','网聘','校招')ZZRYLY,");
		sql.append(" A.CSRQ,");
		sql.append(" A.CZRQ,");
		sql.append(" TO_CHAR(A.LXSJ,'YYYY-MM-DD')LXSJ,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.QYDM+"' AND DM=A.CSDM)AS CSDM,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZJLX+"' AND DM=A.SFZJLXM)AS SFZJLXM,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.MZ+"' AND DM=A.MZM)AS MZM,A.gjdqm,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.HYZK+"' AND DM=A.HYZKM)AS HYZKM,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZZMM+"' AND DM=A.ZZMMM)AS ZZMMM,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.JKZK+"' AND DM=A.JKZKM)AS JKZKM,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZDW)AS SZDW,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.WHCD+"' AND DM=A.WHCD)AS WHCD,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZYZC+"' AND DM=A.ZC)AS ZC,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZW+"' AND DM=A.ZW)AS ZW,");
		sql.append(" (SELECT D.XM FROM CW_JZGXXB D WHERE D.GUID=A.CZR)AS CZR");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_JZGXXB A");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		
		String lrybh = LUser.getRybh();// 获取当前登录人员编号
		if (Validate.noNull(ffpc)) {// 根据发放批次查询已经有薪资的校内教职工
			strWhere +=" and xh in (select rybh from cw_xzb where gzyf='"+ ffpc + "' and shzt='2' )";
		} 
		
		strWhere +=" and xh not in ('000000') ";
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}

	/**
	 * 保存教师信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(CW_JSXXB jsxx) {
		String operateType = this.getPageData().getString("operateType");
		PageData pd = this.getPageData();
		String guid=pd.getString("guid");
		String pxxh=pd.getString("pxxh");
		if(Validate.noNull(jsxx.getLscs())&&jsxx.getLscs().indexOf(")")>0){
			String lscs = jsxx.getLscs().substring(1, jsxx.getLscs().lastIndexOf(")"));
			jsxx.setLscs(lscs);
		}
		int result = 0;
		if ("C".equals(operateType)) {
			// 判断工号是否重复，与人员表中的用户名验证，因为工号是作为用户名登录心痛的
			if (!jsxx.getXh().equals(
					jsxxsService.getObjectById(jsxx.getGuid()).get("XH")
							+"")) {
				boolean checkrygh = jsxxsService.checkRyb(jsxx.getXh());
				if (checkrygh) {
					return MessageBox.show(false, "该工号对应的用户已经存在，请重新输入！");
				}
			}
			//判断排序序号是否重复
			
			boolean checkPxxh=jsxxsService.checkPxxh(pxxh,guid);
			if(checkPxxh==false)
			{
				return  "{success:false,msg:'排序序号不可重复!'}";
			}
			result = jsxxsService.doAdd(jsxx);
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！',rybh:'" + jsxx.getXh()
						+ "'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else {
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	/**
	 * 保存教职工扩展信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSave1", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave1(CW_JZGKZXXB jsxx) {
		String operateType = this.getPageData().getString("operateType");
		int result = 0;
		if ("C".equals(operateType)) {
			result = jsxxsService.doAdd1(jsxx);
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！',rybh:'" + jsxx.getXh()
						+ "'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else {
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	/**
	 * 编辑教师银行账号信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editJsyhzh",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editJsyhzh(CW_JSYHZHB jsyhzhb)throws Exception{
		System.out.println("添加教师银行账号");
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();		
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		//删除
		String jsbh= pd.getString("jsbh");
	    jsxxsService.doDeleteJsyhzh(jsbh, jsyhzhb);
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
			    jsxxsService.doAddJsyhzh(jsyhzhb);
			
		}
		b="success";
		return b;
	}
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		int i = jsxxsService.doDel(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}

	/**
	 * 上一步
	 * 
	 * @return
	 */
	@RequestMapping(value = "/before")
	public ModelAndView before() {
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> xxlb = dictService.getDict(Constant.XSLB);// 教师类别
		List<Map<String, Object>> qydm = dictService.getDict(Constant.QYDM);// 出生地
		List<Map<String, Object>> mz = dictService.getDict(Constant.MZ);// 民族
		List<Map<String, Object>> gj = dictService.getDict(Constant.GB);// 国籍
		List<Map<String, Object>> zjlxm = dictService.getDict(Constant.ZJLX);// 证件类型
		List<Map<String, Object>> hyzk = dictService.getDict(Constant.HYZK);// 婚姻状况
		List<Map<String, Object>> sex = dictService.getDict(Constant.SEX);// 性别
		List<Map<String, Object>> zzmm = dictService.getDict(Constant.ZZMM);// 婚姻状况
		List<Map<String, Object>> jkzk = dictService.getDict(Constant.JKZK);// 健康状况
		List<Map<String, Object>> zc = dictService.getDict(Constant.ZYZC);// 职称
		List<Map<String, Object>> zw = dictService.getDict(Constant.ZW);// 职务
		List<Map<String, Object>> whcd = dictService.getDict(Constant.WHCD);// 职务
		mv.addObject("xxlbList", xxlb);
		mv.addObject("qydmList", qydm);
		mv.addObject("mzList", mz);
		mv.addObject("gjList", gj);
		mv.addObject("zjlxmList", zjlxm);
		mv.addObject("hyzkList", hyzk);
		mv.addObject("sexList", sex);
		mv.addObject("zzmmList", zzmm);
		mv.addObject("jkzkList", jkzk);
		mv.addObject("zcList", zc);
		mv.addObject("zwList", zw);
		mv.addObject("whcdList", whcd);
		String operateType = "C";
		Map jsxx = jsxxsService.getObjectById(this.getPageData().getString(
				"jzgguid"));
//		String zjgguid = jsxxsService.getLoginIdByRybh(this.getPageData().getString(
//				"guid"));
		Map<String, String> maps = new HashMap<String, String>();
		mv.addObject("map", maps);
		mv.addObject("jsxx", jsxx);
		mv.addObject("loginId", LUser.getGuid());
		mv.addObject("guid", this.getPageData().getString("guid"));
		String rybh = LUser.getRybh();
		String loginId = jsxxsService.getLoginIdByRybh(rybh);
		mv.addObject("loginId", loginId);
		String guid = this.getPageData().getString("guid");
		String jzgguid = this.getPageData().getString("jzgguid");
		Map<String, String> map = new HashMap<String, String>();
		iniFile(mv,this.getPageData().getString("jzgguid"));//获取领导签字
		map.put("JZGGUID", guid);
		map.put("GUID", jzgguid);
		mv.addObject("map", map);
		mv.setViewName("fzgn/jcsz/jsxxsz/jsxx_edit");
		return mv;
	}
	
	
	/**
	 * 导出人员信息Excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/expExcel", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel() {
		PageData pd = this.getPageData();
		// 临时文件名
		String file = System.currentTimeMillis() + "";
		// 文件绝对路径
		String realfile = this.getRequest().getServletContext()
				.getRealPath("\\")
				+ "WEB-INF\\file\\excel\\" + file + ".xls";
		// 下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		// 查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		// 设置查询字段名
		sqltext.append(" select A.GUID,");
		sqltext.append(" A.XH,A.XM,A.JG,A.SFZH,LXFS,A.YX,TO_CHAR(A.CJGZSJ,'YYYY-MM-DD')CJGZSJ,GL,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.SEX+"' AND DM=A.XBM)AS XBM,");
		sqltext.append(" DECODE(ZZLX,'0','正式','1','试用','2','实习','兼职')ZZLX,");
		sqltext.append(" DECODE(NVL(ZZRYLY,'0'),'1','网聘','校招')ZZRYLY,");
		sqltext.append(" A.CSRQ,");
		sqltext.append(" A.CZRQ,");
		sqltext.append(" TO_CHAR(A.LXSJ,'YYYY-MM-DD')LXSJ,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.QYDM+"' AND DM=A.CSDM)AS CSDM,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZJLX+"' AND DM=A.SFZJLXM)AS SFZJLXM,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.MZ+"' AND DM=A.MZM)AS MZM,A.gjdqm,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.HYZK+"' AND DM=A.HYZKM)AS HYZKM,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZZMM+"' AND DM=A.ZZMMM)AS ZZMMM,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.JKZK+"' AND DM=A.JKZKM)AS JKZKM,");
		sqltext.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZDW)AS SZDW,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.WHCD+"' AND DM=A.WHCD)AS WHCD,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZYZC+"' AND DM=A.ZC)AS ZC,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZW+"' AND DM=A.ZW)AS ZW,");
		sqltext.append(" (SELECT D.XM FROM CW_JZGXXB D WHERE D.GUID=A.CZR)AS CZR");
		sqltext.append(" FROM CW_JZGXXB A WHERE 1=1");
		String dwbh = pd.getString("treedwbh");
		if (Validate.noNull(dwbh)) {// 左侧单位树筛选
			sqltext.append(" and A.SZDW in(SELECT DWBH FROM GX_SYS_DWB CONNECT BY PRIOR DWBH=SJDW START WITH DWBH='"
					+ dwbh + "' )");
		} else {// 单位权限
			sqltext.append(pageService.getQxsql(LUser.getRybh(), "A.SZDW", "D"));
		}
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("guid");
		if (Validate.noNull(id)) {
			sqltext.append(" AND A.guid IN ('" + id.replace(",", "','") + "') ");
		}
		sqltext.append(" ORDER BY A.guid ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("xh");
		m.setShowname("工号");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("xm");
		m.setShowname("姓名");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("xbm");
		m.setShowname("性别");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("szdw");
		m.setShowname("所在单位");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("zw");
		m.setShowname("职务");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zc");
		m.setShowname("职称");
		mlist.add(m);
		m = null;


		m = new M_largedata();
		m.setName("whcd");
		m.setShowname("文化程度");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("lxsj");
		m.setShowname("来校时间");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("csrq");
		m.setShowname("出生日期");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("mzm");
		m.setShowname("民族");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sfzjlxm");
		m.setShowname("身份证件类型");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("sfzh");
		m.setShowname("身份证件号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("hyzkm");
		m.setShowname("婚姻状况");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zzmmm");
		m.setShowname("政治面貌");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("lxfs");
		m.setShowname("联系方式");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("yx");
		m.setShowname("邮箱");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zzlx");
		m.setShowname("在职类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zzryly");
		m.setShowname("在职人员来源");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("cjgzsj");
		m.setShowname("参加工作时间");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("gl");
		m.setShowname("工龄");
		mlist.add(m);
		m = null;

		// 导出方法
		pageService.ExpExcel(sqltext.toString(), realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}
	
	/**
	 * 导出教师人员信息Excel   wzd
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		String dwbh = pd.getString("treedwbh");
		String s1 = Constant.SEX;
		String s2 = Constant.QYDM;
		String s3 = Constant.ZJLX;
		String s4 = Constant.MZ;
		String s5 = Constant.HYZK;
		String s6 = Constant.ZZMM;
		String s7 = Constant.JKZK;
		String s8 = Constant.WHCD;
		String s9 = Constant.ZYZC;
		String s10 = Constant.ZW;
		String guid = pd.getString("id");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.jsxxsService.expExcel(realfile, shortfileurl, guid,searchValue,rybh,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,dwbh);
	}
	
	/**
	 * 获取单个教师详细信息（增加、修改）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goJsxxPage")
	public ModelAndView goXsxxPage() {
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> xxlb = dictService.getDict(Constant.XSLB);// 教师类别
		List<Map<String, Object>> qydm = dictService.getDict(Constant.QYDM);// 出生地
		List<Map<String, Object>> mz = dictService.getDict(Constant.MZ);// 民族
		List<Map<String, Object>> gj = dictService.getDict(Constant.GB);// 国籍
		List<Map<String, Object>> zjlxm = dictService.getDict(Constant.ZJLX);// 证件类型
		List<Map<String, Object>> hyzk = dictService.getDict(Constant.HYZK);// 婚姻状况
		List<Map<String, Object>> sex = dictService.getDict(Constant.SEX);// 性别
		List<Map<String, Object>> zzmm = dictService.getDict(Constant.ZZMM);// 婚姻状况
		List<Map<String, Object>> jkzk = dictService.getDict(Constant.JKZK);// 健康状况
		List<Map<String, Object>> zc = dictService.getDict(Constant.ZYZC);// 职称
		List<Map<String, Object>> zw = dictService.getDict(Constant.ZW);// 职务
		List<Map<String, Object>> whcd = dictService.getDict(Constant.WHCD);// 职务
		List<Map<String, Object>> bxjb = dictService.getDict(Constant.BXJB);// 报销级别
		
		//获取科研库 ky_sys_dmb 里的 学科领域 zl='B01' 一级学科zl='B02'
		List<Map<String, Object>> xkly = dictService.getKYDict(Constant.XKLY);
		List<Map<String, Object>> yjxk = dictService.getKYDict(Constant.YJXK);
		mv.addObject("xklyList", xkly);
		mv.addObject("yjxkList", yjxk);
		
		mv.addObject("xxlbList", xxlb);
		mv.addObject("qydmList", qydm);
		mv.addObject("mzList", mz);
		mv.addObject("gjList", gj);
		mv.addObject("zjlxmList", zjlxm);
		mv.addObject("hyzkList", hyzk);
		mv.addObject("sexList", sex);
		mv.addObject("zzmmList", zzmm);
		mv.addObject("jkzkList", jkzk);
		mv.addObject("zcList", zc);
		mv.addObject("zwList", zw);
		mv.addObject("whcdList", whcd);
		mv.addObject("bxjbList", bxjb);
		String operateType = this.getPageData().getString("operateType");
		String rybh = LUser.getRybh();
		String loginId = jsxxsService.getLoginIdByRybh(rybh);
		mv.addObject("loginId", loginId);
		if (operateType.equals("C")) {
			String guid = GenAutoKey.get32UUID();
			String jzgguid = GenAutoKey.get32UUID();
			Map<String, String> map = new HashMap<String, String>();
			map.put("GUID", guid);
			map.put("JZGGUID", jzgguid);
			map.put("dwbh",
					CommonUtil.bmhTomc(this.getPageData().getString("dwbh")));
			mv.addObject("map", map);
		} else if (operateType.equals("U") || operateType.equals("L")) {
			Map map = jsxxsService.getObjectById(this.getPageData().getString(
					"guid"));
			String zjgguid = jsxxsService.getLoginIdByKz(this.getPageData().getString(
					"guid"));
			List list = jsxxsService.getObjectByIdYhzh(this.getPageData().getString("guid"));
			if(Validate.isNull(zjgguid)){
				zjgguid = GenAutoKey.get32UUID();
			}
			iniFile(mv,this.getPageData().getString("guid"));//查询领导签字
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("GUID", this.getPageData().getString("guid"));
			maps.put("JZGGUID", zjgguid);
			mv.addObject("map", maps);
			mv.addObject("jsxx", map);
			mv.addObject("loginId", loginId);
			mv.addObject("guid", this.getPageData().getString("guid"));
			mv.addObject("jsyhzh",list);
		}
		mv.setViewName("fzgn/jcsz/jsxxsz/jsxx_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}

	/**
	 * 获得领导签字
	 * @param mv
	 * @param guid
	 */
	public void iniFile(ModelAndView mv,String guid) {
		String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
	}

	/**
	 * 下一步
	 */
	@RequestMapping(value="/after")
	public ModelAndView goAfter(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<Map<String, Object>> lylxList = dictService.getDict(Constant.LYLX);// 来源类型
		List<Map<String, Object>> gzlbList = dictService.getDict(Constant.GZLB);// 教师工资类别
		String guid = pd.getString("guid");
		String jzgguid = pd.getString("jzgguid");
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> jsxx = jsxxsService.getJzgkzxxById(jzgguid);
		map.put("GUID", guid);
		map.put("JZGGUID", jzgguid);
		mv.addObject("map", map);
		mv.addObject("jsxx", jsxx);
		mv.addObject("lylxList", lylxList);
		mv.addObject("gzlbList", gzlbList);
		
		mv.setViewName("fzgn/jcsz/jsxxsz/jsxx_edit_next");
		return mv;
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
		listbt = jsxxsService.insertJcsj(file);
		mv.addObject("listbt", listbt);
		mv.addObject("file", file);
		String pname = pd.getString("pname");
		mv.addObject("bool","true");
		mv.addObject("pname",pname);
 		mv.setViewName("fzgn/jcsz/jsxxsz/txl_imp");
		return mv;
	}
	
}
