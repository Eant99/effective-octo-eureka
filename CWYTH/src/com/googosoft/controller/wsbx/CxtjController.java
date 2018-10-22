package com.googosoft.controller.wsbx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/wsbx/cxtj")
public class CxtjController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;

	@Resource(name = "dictService")
	private DictService dictService;

	/**
	 * 日常报销查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/rcbxcy")
	public ModelAndView goRcbxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		mv.setViewName("wsbx/cxtj/rcbx_list");
		return mv;
	}

	/**
	 * 出差报销查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ccbxcy")
	public ModelAndView goCcbxcyListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		mv.setViewName("wsbx/cxtj/ccbx_list");
		return mv;
	}

	/**
	 * 借款业务查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jkywcy")
	public ModelAndView goJkywcyListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		mv.setViewName("wsbx/cxtj/jkywcy_list");
		return mv;
	}

	/**
	 * 公务报销
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ggjdbxcy")
	public ModelAndView goGgjdbxcyListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		mv.setViewName("wsbx/cxtj/ggjdbxcy_list");
		return mv;
	}

	/**
	 * 出差报销查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/qtbxcy")
	public ModelAndView goQtbxcyListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		mv.setViewName("wsbx/cxtj/qtbxcy_list");
		return mv;
	}

	/**
	 * 列表页面数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");
		String lrybh = LUser.getRybh();// 获取当前登录人员编号
		// 获取查询的表
		String table = pd.getString("table");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		if("CW_RCBXZB".equals(table)){
			sql.append(getRcSql());
		}else if("CW_CLFBXB".equals(table)){
			sql.append(getCcSql());
		}else if("CW_GWJDFBXSQB".equals(table)){
			sql.append(getGwSql());
		}else if("CW_QTJFBX".equals(table)){
			sql.append(getQtSql());
		}else if("CW_JKYWB".equals(table)){
			sql.append(getJkSql());
		}
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName(table+" A");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
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
	 * 日常查询sql
	 * 
	 * @return
	 */
	public String getRcSql() {
		StringBuffer sql = new StringBuffer();
		sql.append(" A.GUID,");
		sql.append(" decode(nvl(A.CJKJE,0),0,'0.00',(to_char(round(A.CJKJE,2),'fm99999999999990.00')))CJKJE,");
		sql.append(" decode(nvl(A.HKJE,0),0,'0.00',(to_char(round(A.HKJE,2),'fm99999999999990.00')))HKJE,");
		sql.append(" decode(nvl(A.LKJE,0),0,'0.00',(to_char(round(A.LKJE,2),'fm99999999999990.00')))LKJE,");
		sql.append(" decode(nvl(A.HJJE,0),0,'0.00',(to_char(round(A.HJJE,2),'fm99999999999990.00')))HJJE,");
		sql.append(" A.XMBH,A.PJZZS,");
		sql.append(" A.SKDWMC,A.SKR,A.YHZH,A.KHYH,A.FY,");
		sql.append(" A.CZR,A.SKDWSZSH,A.SHR,A.SHRQ,");
		sql.append(" A.SKDWSZSI,A.SKDWSZQX,A.SHJY,");
		sql.append(" TO_CHAR(A.SQRQ,'YYYY-MM-DD')SQRQ,");
		sql.append(" TO_CHAR(A.TJRQ,'YYYY-MM-DD')TJRQ,");
		sql.append(" TO_CHAR(A.CZRQ,'YYYY-MM-DD')CZRQ,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.BMMC)AS BMMC,");
		sql.append(" (SELECT RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.RYBH=A.BXR)AS BXR,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZFFSDM+"' AND DM=A.ZFFS)AS ZFFS,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.SHZTDM+"' AND DM=A.SHZT)AS SHZT,");
		sql.append(" DECODE(A.SKLX,'0','单位','个人')AS SKLX");
		return sql.toString();
	}

	/**
	 * 出差sql
	 * 
	 * @return
	 */
	public String getCcSql() {
		StringBuffer sql = new StringBuffer();
		sql.append(" A.GUID,");
		sql.append(" decode(nvl(A.BZHJ,0),0,'0.00',(to_char(round(A.BZHJ,2),'fm99999999999990.00')))BZHJ,");
		sql.append(" decode(nvl(A.CJKJE,0),0,'0.00',(to_char(round(A.CJKJE,2),'fm99999999999990.00')))CJKJE,");
		sql.append(" decode(nvl(A.HKJE,0),0,'0.00',(to_char(round(A.HKJE,2),'fm99999999999990.00')))HKJE,");
		sql.append(" decode(nvl(A.LKJE,0),0,'0.00',(to_char(round(A.LKJE,2),'fm99999999999990.00')))LKJE,");
		sql.append(" A.PJZS,A.CCSY,A.SHJY,");
		sql.append(" TO_CHAR(A.SQRQ,'YYYY-MM-DD')SQRQ,");
		sql.append(" TO_CHAR(A.SHRQ,'YYYY-MM-DD')SHRQ,");
		sql.append(" TO_CHAR(A.CZRQ,'YYYY-MM-DD')CZRQ,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZFFSDM+"' AND DM=A.ZFFS)AS ZFFS,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.BMMC)AS BMMC,");
		sql.append(" (SELECT RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.BXRY)AS BXRY,");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='" + Constant.SHZTDM
				+ "' AND DM=A.SHZT)AS SHZT");
		return sql.toString();
	}
	/**
	 * 公务
	 * @return
	 */
	public String getGwSql(){
		return "A.GUID,A.SPZT,A.SPYJ,A.DJBH,A.YSDW,A.JDRQ,A.BXBM,A.BXR,A.LBRS,A.JDCS,A.PTRS,A.JDSY,A.BXJE,A.BXLX,A.BZ";
	}
	
	/**
	 * 其他
	 * @return
	 */
	public String getQtSql(){
		return " A.GUID,A.SPZT,A.DJBH,A.JFDM,A.JFMC,A.BXR,A.BXBM,A.BXSY,A.FJZS,A.YSND,A.SQDJBH,DECODE(NVL(A.BXJE,'0'),'0','0.00',TO_CHAR(ROUND(A.BXJE,2),'FM999999999990.00'))AS BXJE,A.BXRQ,A.BZ";
	}
	/**
	 * 借款
	 * @return
	 */
	public String getJkSql(){
		return "guid,jkr,bmmc,xmmc,decode(nvl(jkje,0),0,'0.00',(to_char(round(jkje,2),'fm99999999999990.00'))) as jkje,to_char(sqrq,'yyyy-mm-dd') as sqrq ,to_char(bxrq,'yyyy-mm-dd') as bxrq,zffs,decode(shzt,'1','正常','0','禁用') zt";
	}
	/**
	 * 编辑页面的跳转
	 * @return
	 */
	@RequestMapping(value = "/operate")
	public ModelAndView GoOperate() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String operate = pd.getString("operate");
		String _url = "";
		if("R".equals(operate)){
			_url = "wsbx/cxtj/rcbx_view";
		}else if("C".equals(operate)){
			_url = "wsbx/cxtj/ccbx_view";
		}else if("G".equals(operate)){
			_url = "wsbx/cxtj/gwjdbxcy_view";
		}else if("J".equals(operate)){
			_url = "wsbx/cxtj/jkywcy_view";
		}else if("Q".equals(operate)){
			_url = "wsbx/cxtj/qtbxcy_view";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = sdf.format(new Date());
		List<Map<String, Object>> zffsList = dictService.getDict(Constant.ZFFSDM);//支付方式
		mv.addObject("sysDate", sysDate);
		mv.addObject("zffsList", zffsList);
		mv.setViewName(_url);
		return mv;
	}
	/**
	 * 导出信息Excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/expExcelRc", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcelRc() {
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
		String type = pd.getString("type");
		StringBuffer sqltext = new StringBuffer();
		// 设置查询字段名
		sqltext.append(" SELECT");
		sqltext.append(" A.GUID,");
		sqltext.append(" A.XMBH,A.PJZZS,A.CJKJE,A.HKJE,");
		sqltext.append(" A.SKDWMC,A.SKR,A.YHZH,A.KHYH,A.FY,A.LKJE,");
		sqltext.append(" A.CZR,A.SKDWSZSH,A.SHR,A.SHRQ,A.HJJE,");
		sqltext.append(" A.SKDWSZSI,A.SKDWSZQX,A.SHJY,");
		sqltext.append(" TO_CHAR(A.SQRQ,'YYYY-MM-DD')SQRQ,");
		sqltext.append(" TO_CHAR(A.TJRQ,'YYYY-MM-DD')TJRQ,");
		sqltext.append(" TO_CHAR(A.CZRQ,'YYYY-MM-DD')CZRQ,");
		sqltext.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.BMMC)AS BMMC,");
		sqltext.append(" (SELECT RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.RYBH=A.BXR)AS BXR,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZFFSDM+"' AND DM=A.ZFFS)AS ZFFS,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.SHZTDM+"' AND DM=A.SHZT)AS SHZT,");
		sqltext.append(" DECODE(A.SKLX,'0','单位','个人')AS SKLX");
		sqltext.append(" FROM CW_RCBXZB A WHERE 1=1");
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("guid");
		if (Validate.noNull(id)) {
			sqltext.append(" AND A.GUID IN ('" + id.replace(",", "','") + "') ");
		}
		sqltext.append(" ORDER BY A.GUID ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("shzt");
		m.setShowname("状态");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("bxr");
		m.setShowname("报销人");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("bmmc");
		m.setShowname("部门名称");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("xmbh");
		m.setShowname("项目名称");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("cjkje");
		m.setShowname("借款金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("hkje");
		m.setShowname("还款金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("lkje");
		m.setShowname("领款金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("hjje");
		m.setShowname("合计金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("sqrq");
		m.setShowname("申请时间");
		mlist.add(m);
		m = null;
		
		if(Validate.noNull(type)&&"CK".equals(type)){
			m = new M_largedata();
			m.setName("tjrq");
			m.setShowname("提交时间");
			mlist.add(m);
			m = null;
		}
		
		m = new M_largedata();
		m.setName("czrq");
		m.setShowname("报销时间");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zffs");
		m.setShowname("支付方式");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("shjy");
		m.setShowname("审核建议");
		mlist.add(m);
		m = null;


		// 导出方法
		pageService.ExpExcel(sqltext.toString(), realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}
	
	/**
	 * 导出信息Excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/expExcelByCc", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcelByCc() {
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
		String type = pd.getString("type");
		StringBuffer sqltext = new StringBuffer();
		// 设置查询字段名
		sqltext.append(" SELECT");
		sqltext.append(" A.GUID,");
		sqltext.append(" A.BZHJ,A.PJZS,A.CCSY,A.SHJY,");
		sqltext.append(" A.CJKJE,A.HKJE,A.LKJE,");
		sqltext.append(" TO_CHAR(A.SQRQ,'YYYY-MM-DD')SQRQ,");
		sqltext.append(" TO_CHAR(A.SHRQ,'YYYY-MM-DD')SHRQ,");
		sqltext.append(" TO_CHAR(A.CZRQ,'YYYY-MM-DD')CZRQ,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZFFSDM+"' AND DM=A.ZFFS)AS ZFFS,");
		sqltext.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.BMMC)AS BMMC,");
		sqltext.append(" (SELECT RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.BXRY)AS BXRY,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='" + Constant.SHZTDM
				+ "' AND DM=A.SHZT)AS SHZT");
		sqltext.append(" FROM CW_CLFBXB A WHERE 1=1");
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("guid");
		if (Validate.noNull(id)) {
			sqltext.append(" AND A.GUID IN ('" + id.replace(",", "','") + "') ");
		}
		sqltext.append(" ORDER BY A.GUID ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("shzt");
		m.setShowname("状态");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bmmc");
		m.setShowname("部门名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bxry");
		m.setShowname("报销人员");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("cjkje");
		m.setShowname("冲借款");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("hkje");
		m.setShowname("还款");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("lkje");
		m.setShowname("领款");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("bzhj");
		m.setShowname("合计金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("pjzs");
		m.setShowname("票据张数");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ccsy");
		m.setShowname("出差事由");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("sqrq");
		m.setShowname("申请时间");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("shjy");
		m.setShowname("审核意见");
		mlist.add(m);
		m = null;
		
		if(Validate.noNull(type)&&"CK".equals(type)){
			m = new M_largedata();
			m.setName("tjrq");
			m.setShowname("提交时间");
			mlist.add(m);
			m = null;
		}
		
		m = new M_largedata();
		m.setName("shrq");
		m.setShowname("报销时间");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zffs");
		m.setShowname("支付方式");
		mlist.add(m);
		m = null;


		// 导出方法
		pageService.ExpExcel(sqltext.toString(), realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}
}
