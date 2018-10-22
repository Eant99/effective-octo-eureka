package com.googosoft.controller.xmgl.xmsb;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.shzt.XmShzt;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xmgl.xmsb.XmsbService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 项目申报控制类
 * 
 * @author googosoft
 * 
 */
@Controller
@RequestMapping(value = "/xmgl/xmsb")
public class XmsbController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;// 单例
	@Resource(name = "dictService")
	private DictService dictService;// 单例
	@Resource(name = "xmsbService")
	private XmsbService xmsbService;// 单例
	@Resource(name="fileService")
	FileService fileService;

	/**
	 * 获取项目申报列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goXmsbPage")
	public ModelAndView goDmbPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("xmshztList",dictService.getDict(Constant.XMSHZT));
		String treesearch = pd.getString("treesearch");
		String type = pd.getString("type");
		String url = "xmgl/xmsb/xmsb_list";
		if("first".equals(type)){
			url = "xmgl/xmsb/firstCheck_list";
		}else if("second".equals(type)){
			url = "xmgl/xmsb/secondCheck_list";
		}else if("other".equals(type)){
			url = "xmgl/xmsb/change_list";
		}
		mv.setViewName(url);
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	
	/**
	 * 获取项目分类列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageData")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();
		String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"0");
		StringBuffer tablename = new StringBuffer();
		StringBuilder strWhere = new StringBuilder();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select b.guid,(select mc from gx_sys_dmb where zl = '"+XmShzt.SHZTZL+"' and dm = shzt) as shztmc,b.shzt,b.xmbh,b.xmmc,xmpm,"
				+ "(select xmlxmc from cw_xmlxb where guid = xmlx) as xmlx,(select zymc from cw_zyxxb where zybh = fwzy ) as fwzy,fwxk,"
				+ "(case when sfsndcxlzxm = '1' then '是' else '否' end) as sfsndcxlzxm,to_char(ysje,'FM999999999.00') as ysje,"
				+ "(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = sbdw) as sbdw,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = sbr) as sbr,"
				+ "to_char(sbrq,'yyyy-MM-dd') as sbrq,(select '('||rybh||')'||xm from gx_sys_ryb where guid = csr) as csr,"
				+ "to_char(csrq,'yyyy-MM-dd') as csrq from cw_xmsbxxb b) A ");
		pageList.setSqlText(sqltext.toString());
		if(shzt.equals(XmShzt.WTJ)) {
		    strWhere.append("and a.shzt = '"+XmShzt.WTJ+"' ");
		}else if(shzt.equals(XmShzt.CSZ)) {
			strWhere.append("and a.shzt = '"+XmShzt.CSZ+"'");
		}else if(shzt.equals(XmShzt.CSTH)) {
			strWhere.append("and a.shzt = '"+XmShzt.CSTH+"'");
		}else if(shzt.equals(XmShzt.FSZ)) {
			strWhere.append("and a.shzt = '"+XmShzt.FSTH+"'");
		}else if (shzt.equals(XmShzt.FSTH)) {
		    strWhere.append("and a.shzt = '"+XmShzt.FSTH+"'");	
		}else if (shzt.equals(XmShzt.SHTG)) {
			strWhere.append("and a.shzt='"+XmShzt.SHTG+"'");
		}
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		pageList.setStrWhere(strWhere.toString());
		pageList.setHj1("");
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		//将列表数据放到session中
		session.setAttribute("xmspList", pageList.getContentList());
		return pageInfo.toJson();
	}
	/**
	 * 获取重新论证项目列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCxlzxm")
	@ResponseBody
	public Object getCxlzxm(HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		StringBuilder strWhere = new StringBuilder();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select b.guid,(select mc from gx_sys_dmb where zl = '"+XmShzt.SHZTZL+"' and dm = shzt) as shztmc,b.shzt,b.xmbh,b.xmmc,xmpm,"
				+ "(select xmlxmc from cw_xmlxb where guid = xmlx) as xmlx,(select zymc from cw_zyxxb where zybh = fwzy ) as fwzy,fwxk,"
				+ "(case when sfsndcxlzxm = '1' then '是' else '否' end) as sfsndcxlzxm,to_char(ysje,'FM999999999.00') as ysje,"
				+ "(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = sbdw) as sbdw,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = sbr) as sbr,"
				+ "to_char(sbrq,'yyyy-MM-dd') as sbrq,(select '('||rybh||')'||xm from gx_sys_ryb where guid = csr) as csr,"
				+ "to_char(csrq,'yyyy-MM-dd') as csrq from cw_xmsbxxb b where sfsndcxlzxm='1') A ");
		pageList.setSqlText(sqltext.toString());
		strWhere.append("and a.shzt = '"+XmShzt.WTJ+"' ");
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		pageList.setStrWhere(strWhere.toString());
		pageList.setHj1("");
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		//将列表数据放到session中
		session.setAttribute("xmspList", pageList.getContentList());
		return pageInfo.toJson();
	}
	/**
	 * 获取第一步详细信息（增加、修改）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goEditPage")
	public ModelAndView goCgmlszPage() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		if (operateType.equals("C")) {
			String guid = GenAutoKey.get32UUID();
			String mxbid = GenAutoKey.get32UUID();
			mv.addObject("guid", guid);
			mv.addObject("mxbid", mxbid);
		} else if (operateType.equals("update") || operateType.equals("L")) {
			PageData pd = this.getPageData();
			String guid = pd.getString("guid");		
			Map xmsb = xmsbService.getMapXmsbByGuid(guid);
			String xmlxbh = Validate.isNullToDefaultString(xmsb.get("XMLX"),"");
			List wjlxlist = xmsbService.getFjxxByXmlx(xmlxbh);
			iniFile(mv,this.getPageData().getString("guid"));//查询领导签字
			mv.addObject("xmsb", xmsb);
			mv.addObject("wjlxlist", wjlxlist);
			mv.addObject("guid",guid);
		}
		mv.addObject("today",DateUtil.getDay());
		mv.setViewName("xmgl/xmsb/xmsb_operate");
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 获取下一步详细信息（增加、修改）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goNextPage")
	public ModelAndView goNextPage() {
		PageData pd = this.getPageData();
		String zbid = pd.getString("zbid");
		String operateType = pd.getString("operateType");
		ModelAndView mv = this.getModelAndView();
		if (operateType.equals("C")) {
//			String guid = GenAutoKey.get32UUID();
//			mv.addObject("guid", guid);
		} else if (operateType.equals("update") || operateType.equals("L")) {
			Map mxbxx = xmsbService.getMapMxbByGuid(zbid);
			mv.addObject("mxbxx",mxbxx);		
		}
		mv.addObject("zbid",zbid);
		mv.addObject("jldwList",dictService.getDict(Constant.JLDW));
		mv.addObject("operateType", operateType);
		mv.setViewName("xmgl/xmsb/cgmx");
		return mv;
	}
	//获得领导签字
	public void iniFile(ModelAndView mv,String guid) {
		String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
	}
	/**
	 * 查看
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goViewPage")
	public ModelAndView goLookPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String zbid = pd.getString("guid");
		Map xmsb = xmsbService.getMapXmsbByGuid(guid);
		String xmlxbh = Validate.isNullToDefaultString(xmsb.get("XMLX"),"");
		List wjlxlist = xmsbService.getFjxxByXmlx(xmlxbh);
		iniFile(mv,this.getPageData().getString("guid"));//查询领导签字
		mv.addObject("xmsb", xmsb);
		mv.addObject("wjlxlist", wjlxlist);
		Map mxbxx = xmsbService.getMapMxbByGuid(zbid);
		mv.addObject("jldwList",dictService.getDict(Constant.JLDW));
		mv.addObject("mxbxx",mxbxx);		
		mv.setViewName("xmgl/xmsp/xmsp_view");
		return mv;
	}
	
	/**
	 * 保存项目申报主表信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSaveZb", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveZb() {
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		int result = 0;
		if ("C".equals(operateType))//新增保存 
		{
			// 判断项目编号是否重复
			int flag = xmsbService.getObjectById(pd);
			if (flag!=0) {
				return MessageBox.show(false, "该项目编号已经存在，请重新输入！");
			}
			result = xmsbService.doAddMxb(pd);
			result = xmsbService.doAddZb(pd);
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else if ("update".equals(operateType)){ //修改
			String guid = pd.getString("guid");
			result = xmsbService.updXmsb(pd,guid);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else {
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
    	}
		
	}
	/**
	 * 保存项目申报明细表信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSaveMxb", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveMxb() {
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String zbid = pd.getString("zbid");
		int result = 0;
//		if ("C".equals(operateType))//新增保存 
//		{
//			result = xmsbService.doAddMxb(pd);
//			if (result > 0) {
//				return "{success:'true',msg:'信息保存成功！'}";
//			} else {
//				return MessageBox.show(false, MessageBox.FAIL_SAVE);
//			}
//		}else if ("U".equals(operateType))// 修改
//		{
			result = xmsbService.updMxbxx(pd,zbid);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
//		} else {
//			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
//    	}
		
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String b="";
		int i=0;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		
		i = xmsbService.doDelete(guid);
		if(i>0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
		
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/doSubmit",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSubmit() {
		String b="";
		int i=0;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		
		i = xmsbService.doUpdate(guid);
		if(i>0) {
			return "{\"success\":\"true\",\"msg\":\"提交成功！\"}";
		}else {
			return "{\"success\":\"false\",\"msg\":\"提交失败，请稍后重试！\"}";
		}
		
	}
	/**
	 * 项目类型弹出窗
	 */
	@RequestMapping(value="/getxmlxall")
	public ModelAndView getxmlxall(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String id = pd.getString("id");
		String bmbh = pd.getString("bmbh");
		mv.addObject("bmbh",bmbh);
		mv.addObject("controlId",controlId);//名称
		mv.addObject("id",id);
		mv.setViewName("/xmgl/xmsb/xmlxWindow");
		return mv;
	}
	/**
	 * 得到附件设置信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFjxx")
	@ResponseBody
	public Object getFjxx() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String zbid = pd.getString("zbid");
		List list = xmsbService.getFjxx(zbid);
		mv.addObject("list",list);
		return list;
	}
	
	@RequestMapping(value = "/xmlx")
	@ResponseBody
	public Object getFjxxByXmlx(String xmlxbh) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List list = xmsbService.getFjxxByXmlx(xmlxbh);
		Gson gson = new Gson();
		if(list.size()==0){
			list = new ArrayList<Map<String,Object>>();
		}
		return gson.toJson(list);
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
				PageList pageList = new PageList();
				StringBuffer sqltext = new StringBuffer();
				pageList.setSqlText("*");
				sqltext.append("select guid,xmbh,xmmc,shzt,"
						+ "(select d.mc from gx_sys_dmb d where d.zl='"+Constant.XMSHZT+"' and d.dm=t.shzt)as shztmc,"
						+ "(select '('||xmlxbh||')'||xmlxmc from cw_xmlxb x where x.guid=t.xmlx) as xmlx,"
						+ "(select '('||zybh||')'||zymc from cw_zyxxb a where a.zybh=t.fwzy) as fwzy,"
						+ "(select '('||dwbh||')'||mc from gx_sys_dwb b where b.dwbh=t.sbdw) as sbdw,fwxk,to_char(ysje,'FM999999999999.00') as ysje,"
						+ "(case sfsndcxlzxm when '1' then '是' when '0' then '否' end) as sfsndcxlzxm,"
						+ "(select '('||rybh||')'||xm from gx_sys_ryb c where c.rybh=t.sbr) as sbr,to_char(sbrq,'yyyy-mm-dd') as sbrq "
						+ "from cw_xmsbxxb t where 1=1");
				//sqltext.append(" where 1 = 1 and T.SQR='"+LUser.getGuid()+"'");//where条件
				String guid = pd.getString("id");
				String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"0");
				if(Validate.noNull(guid)){
					sqltext.append(" AND T.GUID IN ('"+guid+"') ");
				}else {			
					if("0".equals(shzt)){
						sqltext.append(" AND T.SHZT IN('0') ");
					}else if("1".equals(shzt)){
						sqltext.append(" AND T.SHZT in('1')");
					}else if("2".equals(shzt)){
						sqltext.append(" AND T.SHZT in('2')");
					}else if("3".equals(shzt)){
						sqltext.append(" AND T.SHZT in('3')");
					}else if("4".equals(shzt)){
						sqltext.append(" AND T.SHZT in('4')");
					}else if("5".equals(shzt)){
						sqltext.append(" AND T.SHZT in('5')");
					}
				}
				sqltext.append(" ORDER BY T.XMBH ");
				List<M_largedata> mlist = new ArrayList<M_largedata>();
				M_largedata m1 = new M_largedata();
				m1.setColtype("String");
				m1.setName("shztmc");
				m1.setShowname("审核状态");
				mlist.add(m1);
				M_largedata m2 = new M_largedata();
				m2.setColtype("String");
				m2.setName("xmbh");
				m2.setShowname("项目编号");
				mlist.add(m2);
				M_largedata m3 = new M_largedata();
				m3.setColtype("String");
				m3.setName("xmlx");
				m3.setShowname("项目类型");
				mlist.add(m3);
				M_largedata m4 = new M_largedata();
				m4.setColtype("String");
				m4.setName("fwzy");
				m4.setShowname("服务专业");
				mlist.add(m4);
				M_largedata m5 = new M_largedata();
				m5.setColtype("String");
				m5.setName("fwxk");
				m5.setShowname("服务学科");
				mlist.add(m5);
				M_largedata m6 = new M_largedata();
				m6.setColtype("String");
				m6.setName("ysje");
				m6.setShowname("预算金额（元）");
				mlist.add(m6);
				M_largedata m7 = new M_largedata();
				m7.setColtype("String");
				m7.setName("sfsndcxlzxm");
				m7.setShowname("是否上年度重新论证项目");
				mlist.add(m7);
				M_largedata m8 = new M_largedata();
				m8.setColtype("String");
				m8.setName("sbdw");
				m8.setShowname("申报单位");
				mlist.add(m8);
				M_largedata m9 = new M_largedata();
				m9.setColtype("String");
				m9.setName("sbr");
				m9.setShowname("申报人");
				mlist.add(m9);
				M_largedata m10 = new M_largedata();
				m10.setColtype("String");
				m10.setName("sbrq");
				m10.setShowname("申报日期");
				mlist.add(m10);
				//导出方法
				pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
				return "{\"url\":\"excel\\\\"+file+".xls\"}";
			}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 项目类型
	 */
	@RequestMapping(value="/main")
	public ModelAndView goMainTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("xmgl/xmsb/main");
		return mv;
	}
	/**
	 * 项目类型
	 */
	@RequestMapping(value="/cxsb")
	public ModelAndView goCxsbList(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("xmgl/xmsb/thxm_list");
		return mv;
	}
	/**
	 * 项目类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/xmflTree", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xmflTree(HttpServletResponse response)
			throws java.io.IOException {
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String sjfl = pd.getString("sjfl");
		if(Validate.isNull(sjfl)){
			sjfl = "root";
		}
		if ("get-xmfl".equals(menu)) {
			return xmsbService.getXmsbNode(pd, sjfl, rootPath);
		} else {
			return "";
		}
	}
	/**
	 * 项目类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cgflTree", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cgflTree(HttpServletResponse response)
			throws java.io.IOException {
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String cgfl = pd.getString("cgfl");
		if(Validate.isNull(cgfl)){
			cgfl = "root";
		}
		if ("get-cgfl".equals(menu)) {
			return xmsbService.getCgflNode(pd, cgfl, rootPath);
		} else {
			return "";
		}
	}
	/**
	 * 列表页面数据
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		String treeid=pd.getString("treeid");
		System.out.println("^v^"+treeid);
		String datas="",strnum="";
		if(treeid.equals("")){
			datas = SendHttpUtil.sendPost(SystemSet.address+"/xmgl/xmsb/getPageListFlow", "");
		    strnum = SendHttpUtil.sendPost(SystemSet.address+"/xmgl/xmsb/getCountFlow", "");	
		}else{
			datas = SendHttpUtil.sendPost(SystemSet.address+"/xmgl/xmsb/getPageListWhereFlow","treeid="+treeid.trim());
			System.out.println("^^>>"+datas);
		    strnum = SendHttpUtil.sendPost(SystemSet.address+"/xmgl/xmsb/getCountWhereFlow","treeid="+treeid.trim());
		}
		
		//int num=Integer.parseInt(strnum);
		strnum=strnum.substring(8,strnum.indexOf("}"));
		PageInfo pageInfo = new PageInfo((String) pd.get("draw"), strnum, strnum,datas);
		return pageInfo.toJson();
	}*/
	/**
	 * 项目排名调整列表页面数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageLists")
	@ResponseBody
	public Object getPageLists() throws Exception {	
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		String datas = SendHttpUtil.sendPost(SystemSet.address+"/xmpmtz/getPageList", "");
		String strnum = SendHttpUtil.sendPost(SystemSet.address+"/xmpmtz/getCountFlow", "");
		//int num=Integer.parseInt(strnum);
		strnum=strnum.substring(8,strnum.indexOf("}"));
		PageInfo pageInfo = new PageInfo((String) pd.get("draw"), strnum, strnum,datas);
		return pageInfo.toJson();
		
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
		String type = pd.getString("type");
		if(Validate.isNull(type)){
			type="";
		}
		String _url = "xmgl/xmsb/xmsb_operate";
		if("L".equals(operate)){
			mv.addObject("type", type);
			_url = "xmgl/xmsb/xmsb_view";
		}else if("F".equals(operate)){
			_url = "xmgl/xmsb/firstCheck";
		}else if("S".equals(operate)){
			_url = "xmgl/xmsb/secondCheck";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = sdf.format(new Date());
		mv.addObject("sysDate", sysDate);
		mv.setViewName(_url);
		return mv;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/xmflList")
	public ModelAndView goxmflList(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		mv.addObject("controlId", controlId);
		mv.setViewName("xmgl/xmsb/xmflList");
		return mv;
	}
	/**
	 * 项目类型
	 */
	@RequestMapping(value="/window")
	public ModelAndView goWindowTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String _url = "xmgl/xmsb/zyTree";
		if("txt_xmfl".equals(controlId)){
			_url = "xmgl/xmsb/xxmTree";
		}
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.addObject("controlId", controlId);
		mv.setViewName(_url);
		return mv;
	}
	/**
	 * 列表页面数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/xmflPage")
	@ResponseBody
	public Object getxmflPage() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		                        
		StringBuffer sql = new StringBuffer();
		sql.append(" GUID,FLBH,FLMC");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_XMFLSZB A");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");
		String lrybh = LUser.getRybh();// 获取当前登录人员编号
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
		pageList.setKeyId("zybh");
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
	 * 下一步
	 */
	@RequestMapping(value="/after")
	public ModelAndView goAfter(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("xmgl/xmsb/after");
		return mv;
	}
	/**
	 * 
	 */
	@RequestMapping(value="/goFirstCheckPage")
	public ModelAndView goFirstCheckPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("xmgl/xmsb/firstCheck_list");
		return mv;
	}
	
	@RequestMapping(value="/check1")
	public ModelAndView check1(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String url = "xmgl/xmsb/check4";
		if(!"first".equals(type)){
			url = "xmgl/xmsb/check3";
		}
		mv.setViewName(url);
		return mv;
	}
	/**
	 * 
	 */
	@RequestMapping(value="/goSecondCheckPage")
	public ModelAndView goSecondCheckPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("xmgl/xmsb/secondCheck_list");
		return mv;
	}
	@RequestMapping(value="/goOtherCheckPage")
	public ModelAndView goOtherCheckPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("xmgl/xmsb/other_list");
		return mv;
	}
	
	

	/**
	 *  导入上传
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws Exception
	 */
	@RequestMapping(value="/upload")
	public ModelAndView Upload(String sclx,MultipartFile fj) throws IllegalStateException, IOException{
		ModelAndView mv = this.getModelAndView();
		//原始文件名称
		String error="";
    	String pictureFile_name =  fj.getOriginalFilename();
    	if(Validate.isNull(pictureFile_name)){
    		error = "请选择文件上传！";
    		mv.addObject("error",error);
    	}else{
    		//新文件名称
        	String n = pictureFile_name.trim().substring(pictureFile_name.indexOf("."));
    		if("xlsx".equals(n)){
    			error = "请将.xlsx文件另存为.xls文件!";
    			mv.addObject("error",error);
    		}
    		if(!".xls".equals(n)){
    			error = "请选择.xls格式的文件!";
    			mv.addObject("error",error);
    		}
        	if(Validate.isNull(error)){
        		String newFileName = this.get32UUID()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
        		String realPath = this.getRequest().getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        		//上传文件
        		String file = realPath+"WEB-INF/file/excel/"+newFileName;
        		File uploadPic = new File(file);
        		if(!uploadPic.exists()){
        			uploadPic.mkdirs();
        		}
        		//向磁盘写文件
        		fj.transferTo(uploadPic);
        		
        	}
    	}
		mv.setViewName("xmgl/xmsb/xmsb_operate");
		return mv;
	}
}
