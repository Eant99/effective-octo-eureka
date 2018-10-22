package com.googosoft.controller.ysgl.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.ysgl.CW_BMYSXDB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.bmyssb.BmysxdService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/bmysxd")
public class BmysxdController extends BaseController {
	@Resource(name="dictService")
	DictService dictService;
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="bmysxdService")
	private BmysxdService bmysxdService;
	
	//初始化登录人员信息
			public void iniLogin(ModelAndView mv) {
				mv.addObject("loginId",LUser.getGuid());
				mv.addObject("bmbh",LUser.getDwbh());
				mv.addObject("bmmc",LUser.getOnlyDwmc());
				mv.addObject("today",DateUtil.getDay());
			}
	//跳转列表页面
	@RequestMapping(value = "/goBmysxdListPage")
	public ModelAndView goBmysxdListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		iniLogin(mv);
		mv.setViewName("ysgl/jcsz/bmysxd/bmysxd_list");
		return mv;	
	}
	//获取列表页面数据
	@RequestMapping(value = "/getPageLists")
	@ResponseBody
	public Object getxdPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String dwbh = pd.getString("treeid");
		PageList pageList = new PageList();
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
		pageList.setStrWhere(""); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value = "/goBmysxdEditPage")
	public ModelAndView goBmysxdEditPage() {
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/jcsz/bmysxd/bmysxd_edit");
		return mv;

	}
	/**
	 * 先删除
	 */
	@RequestMapping(value="/delAll",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object delAll() {
		String b;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		int i = 0;
		i = bmysxdService.doDeleteAll(guid);
		b="";
		return b;
	}
	/**
	 * 保存
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object addYhzhb(CW_BMYSXDB bmysxd,HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = (String) map.get("guid1");//创建主键
			String jyje = (String) map.get("jyje");
			
			    //将字段放入bmysxdb
			    bmysxd.setGuid(guid);
			    bmysxd.setJyje(jyje);
			    
				
				//增加
				bmysxdService.doAdd(bmysxd);
			
		}
		b="success";
		return b;
	}
	/**
	 * 保存
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doSaveXd",produces = "text/html;charset=UTF-8")
	@ResponseBody	
	public Object doSaveXd(String[] params,HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		boolean bool = bmysxdService.doSaveXd(params, sszt);
		Gson gson = new Gson();
		return gson.toJson(bool);
	}
	//导出excel
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
				
				//设置查询字段名
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
				sqltext.append(" WHERE 1=1");
//				String guid = pd.getString("id");
				String dwbh = pd.getString("treeBmbh");
				if(Validate.noNull(dwbh)){
					sqltext.append(" and a.tbbm='"+dwbh+"'");
				}
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
