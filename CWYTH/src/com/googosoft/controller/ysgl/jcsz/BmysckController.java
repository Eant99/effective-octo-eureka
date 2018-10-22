package com.googosoft.controller.ysgl.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.xmsz.XmxxService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/bmysck")
public class BmysckController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;//获取页面
	@Resource(name="bmysckService")
	private BmysckService bmysckService;//单例s
	
	@RequestMapping(value = "/goBmysckListPage")
	public ModelAndView goBmysckListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		mv.setViewName("ysgl/jcsz/bmysck/bmysck_list");
		mv.addObject("dwbh",dwbh);
		return mv;

	}
	
	
	//跳转到详细信息页面
	@RequestMapping(value = "/goBmysckXxxxPage")
	public ModelAndView goBmysckXxxxPage() {
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/jcsz/bmysck/bmysck_xxxx");
		return mv;

	}
	/**
	 * 获取项目信息列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String dwbh = pd.getString("dwbh");
		sqltext.append("*");
		tablename.append(" (SELECT");
		tablename.append(" A.TBBM,A.ND,nvl(A.XMID,'fyid')xmid,A.XMMC,DW.MC AS DWMC,");
		tablename.append(" TO_CHAR(ROUND(A.MONEY,4),'FM999999999999999999999999999999999.0000')MONEY,");
		tablename.append(" TO_CHAR(ROUND(B.ZCYSHZ,4),'FM999999999999999999999999999999999.0000')ZCYSHZ,");
		tablename.append(" TO_CHAR(ROUND(B.JYJE,4),'FM999999999999999999999999999999999.0000')JYJE,");
		tablename.append(" DECODE(A.JFLX,'01','部门经费','02','个人经费','')JFLX");
		tablename.append(" FROM CW_YSXMXXVIEW A");
		tablename.append(" LEFT JOIN GX_SYS_DWB DW ON DW.DWBH=A.TBBM");
		tablename.append(" LEFT JOIN");
		tablename.append(" (SELECT V.GUID AS ID,nvl(v.XMID,'fyid'),V.XMMC,V.TBBM,V.ND,V.MONEY,V.JFLX,T.ZCYSHZ,T.JYJE FROM CW_BMYSXDB T LEFT JOIN CW_YSXMXXVIEW V ON V.GUID=T.ZBID)B");
		tablename.append(" ON B.ID=A.GUID");
		if(Validate.noNull(dwbh)) {
			tablename.append(" WHERE A.TBBM='"+dwbh+"'  ");
		}
		tablename.append(")K");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("XMID");//主键 
		//设置WHERE条件
		
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		pageList.setStrWhere(pageService.getDwqxWhereSql(rybh, "K.tbbm", dwbh, true)); //获取管理单位权限
		pageList.setHj1("");//合计
		
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 跳转项目信息编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/gobmysckEditPage")
	public ModelAndView goXmxxEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		Map<?, ?>  map = bmysckService.getObjectById(guid);
		mv.addObject("guid", map);
		mv.setViewName("ysgl/jcsz/bmysck/bmysck_xxxx");
		return mv;
	}

	//导出
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
		sqltext.append(" SELECT");
		sqltext.append(" A.TBBM,A.ND,nvl(A.XMID,'fyid')xmid,A.XMMC,DW.MC AS DWMC,");
		sqltext.append(" TO_CHAR(ROUND(A.MONEY,4),'FM999999999999999999999999999999999.0000')MONEY,");
		sqltext.append(" TO_CHAR(ROUND(B.ZCYSHZ,4),'FM999999999999999999999999999999999.0000')ZCYSHZ,");
		sqltext.append(" TO_CHAR(ROUND(B.JYJE,4),'FM999999999999999999999999999999999.0000')JYJE,");
		sqltext.append(" DECODE(A.JFLX,'01','部门经费','02','个人经费','')JFLX");
		sqltext.append(" FROM CW_YSXMXXVIEW A");
		sqltext.append(" LEFT JOIN GX_SYS_DWB DW ON DW.DWBH=A.TBBM");
		sqltext.append(" LEFT JOIN");
		sqltext.append(" (SELECT V.GUID AS ID,nvl(v.XMID,'fyid'),V.XMMC,V.TBBM,V.ND,V.MONEY,V.JFLX,T.ZCYSHZ,T.JYJE FROM CW_BMYSXDB T LEFT JOIN CW_YSXMXXVIEW V ON V.GUID=T.ZBID)B");
		sqltext.append(" ON B.ID=A.GUID");
		sqltext.append(" WHERE A.TBBM='"+LUser.getDwbh()+"'  ");
		
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		
		m1.setColtype("String");
		m1.setName("TBBM");
		m1.setShowname("部门编号");
		mlist.add(m1);
		
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("DWMC");
		m2.setShowname("部门名称");
		mlist.add(m2);
		
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("XMMC");
		m3.setShowname("项目名称");
		mlist.add(m3);
		
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("jflx");
		m4.setShowname("经费类型");
		mlist.add(m4);
		
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("ZCYSHZ");
		m5.setShowname("预算金额(万元)");
		mlist.add(m5);
		
		M_largedata m6 = new M_largedata();
		m6.setColtype("String");
		m6.setName("JYJE");
		m6.setShowname("建议金额(万元)");
		mlist.add(m6);
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}	
	

}
