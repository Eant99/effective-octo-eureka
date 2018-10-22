package com.googosoft.controller.sqspcx;

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
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.sqspcx.SqspcxService;
import com.googosoft.service.wsbx.ccyw.CcywsqService;
import com.googosoft.service.wsbx.gwjd.GwjdSqService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
/**
 * 事前审批查询
 * @author L
 *
 */
@Controller
@RequestMapping(value ="/sqspcx")
public class SqspcxController extends BaseController {
	@Resource(name="gwjdsqService")
	private GwjdSqService gwjdsqService;
	@Resource(name="ccywsqService")
	CcywsqService ccywsqService;
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	@Resource(name="sqspcxService")
	private SqspcxService sqspcxService;//单例

	@Resource(name="dictService")
	DictService dictService;
	
	@Resource(name="fileService")
	FileService fileService;
	
	
	private DBHelper db;
	
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String shzt=pd.getString("shzt");
		String djbh=pd.getString("djbh");
		String sqsplx=pd.getString("sqsplx");
		mv.setViewName("wsbx/sqspcx/sqspcx_list");
		mv.addObject("dwbh",dwbh);
		mv.addObject("shzt",shzt);
		mv.addObject("djbh",djbh);
		mv.addObject("sqsplx",sqsplx);
		mv.addObject("shztList_cc",dictService.getDict(Constant.SHZTDM));
		mv.addObject("shztList_gw",dictService.getDict(Constant.SHZTDMGW));
		return mv;

	}
	

	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		//
		String sqsplx = pd.getString("sqsplx");
		String shzt = pd.getString("status");
		String djbh = pd.getString("djbh");
		//定义条件
		String sqltext = "";
		String tableName = "";
		String strWhere = "";
		if(Validate.isNullOrEmpty(sqsplx) || "0".equals(sqsplx)) {
			sqltext += "A.GUID,A.PROCINSTID,A.SQRXM AS SQRMC,A.SZBM AS SZBMMC,A.DJBH,TO_CHAR(A.SQRQ,'yyyy-MM-dd') as sqrq,"
					+"(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.SHZTDM+"' AND C.DM = A.SHZT) AS SHZTMC,"
					+ "'出差业务' AS SQSPLX ";
			tableName += " CW_CCSQSPB A ";
		}else {
			sqltext += "a.guid,a.procinstid, a.djbh, a.sqr, (select '('||r.rybh||')' || r.xm from gx_sys_ryb r where r.rybh=a.sqr)as sqrmc,"
					+ " a.szbm,  '(' || a.szbm || ')' ||  (select d.mc from gx_sys_dwb d where d.dwbh = a.szbm) as szbmmc, a.shzt,"
					+ " (select d.mc from gx_sys_dmb d where d.zl = '"+Constant.SHZTDMGW+"' and d.dm=a.shzt)as shztmc,A.SQRQ, '公务接待业务'as sqsplx ";
			tableName += "  CW_GWJDYWSQSPB a  ";
		}
		if(Validate.noNull(shzt)) {
			strWhere += " and a.shzt = '"+shzt+"'";
			if("01".equals(shzt)) {
				if("0".equals(sqsplx)) {
					strWhere += " and a.sqr = '"+LUser.getGuid()+"' ";
				}else if("1".equals(sqsplx)) {
					strWhere += " and a.sqr = '"+LUser.getRybh()+"' ";
				}
			}
		}
		if(Validate.noNull(djbh)) {
			strWhere += " and a.djbh = '"+djbh+"'";
		}
		//设置条件
		pageList.setSqlText(sqltext);
		pageList.setTableName(tableName);
		pageList.setKeyId("a.GUID");//主键
		pageList.setStrWhere(strWhere); 
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
/*	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		
		tablename.append(" ( (select a.guid, a.djbh,a.sqr, (select '('||r.rybh||')' || r.xm from gx_sys_ryb r where r.guid=a.sqr)as sqrmc, a.szbm,"
				+ "  a.szbm  ||  (select d.mc from gx_sys_dwb d where d.dwbh = a.szbm) as szbmmc,  a.shzt,"
				+ "  (select d.mc from gx_sys_dmb d where d.zl = '11033' and d.dm=a.shzt)as shztmc,  to_char(a.czrq,'yyyy-mm-dd') as czrq, '出差业务'as sqsplx  from CW_CCSQSPB A"
				+ " LEFT JOIN CW_JFSZB B ON A.JFBH = B.GUID) union select a.guid, a.djbh, a.sqr, (select '('||r.rybh||')' || r.xm from gx_sys_ryb r where r.rybh=a.sqr)as sqrmc,"
				+ " a.szbm,  '(' || a.szbm || ')' ||  (select d.mc from gx_sys_dwb d where d.dwbh = a.szbm) as szbmmc, a.shzt,"
				+ " (select d.mc from gx_sys_dmb d where d.zl = '11033' and d.dm=a.shzt)as shztmc,  a.sqrq, '公务接待业务'as sqsplx"
				+ " from CW_GWJDYWSQSPB a ) k");
		
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(""); 
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
*///	/**
//	 * 跳转事前审批查询查看页面
//	 * @return
//	 */
	@RequestMapping(value="/goViewPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goViewPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		String type = Validate.isNullToDefaultString(pd.getString("type"), "");
		mv.addObject("guid",guid);
		//Map<?, ?> map = sqspcxService.getObjectById1(guid);
//		Map map = new HashMap<String,Object>();
		System.err.println("--------------------"+type);
		if(type.contains("出差")) {
			iniLogin(mv);
			iniSelect(mv);
			iniFile(mv);
			Map<String, Object> ccywsq = ccywsqService.getCcywsqMapById(pd.getString("guid"));
			mv.addObject("ccywsq",ccywsq);
			String[] jtgj = (ccywsq.get("jtgj")+"").split(",");
			String jtgjcl = (ccywsq.get("jtgj")+"");
			mv.addObject("jtgjcl", jtgjcl);
			mv.addObject("length",jtgj.length);
			mv.addObject("txryList",ccywsqService.getTxryListById(pd.getString("guid")));
			mv.addObject("xmxxlist",ccywsqService.getXmxxListById(pd.getString("guid")));
			mv.setViewName("wsbx/sqspcx/ccywsq_view");
		}else {
			Map map = gwjdsqService.getObjectById(this.getPageData().getString("guid"));
			mv.addObject("gwjdfsq",map);
			mv.addObject("guid",this.getPageData().getString("guid"));
			String[] fjxx = fileService.getFjList(this.getPageData().getString("guid"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0]);
			mv.addObject("fjConfig",fjxx[1]);
			mv.setViewName("wsbx/sqspcx/gwjdfsq_view");
		}
		return mv;

	}
	//初始化数据字典下拉框
		public void iniSelect(ModelAndView mv) {
			mv.addObject("cclxList",dictService.getDict(Constant.CCLX));
			mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
			mv.addObject("jtgjList",dictService.getDict(Constant.JTGJ));
		}
		//初始化登录人员信息
		public void iniLogin(ModelAndView mv) {
			mv.addObject("loginId",LUser.getGuid());
			mv.addObject("szbm",LUser.getDwmc());
			mv.addObject("ryxm","("+LUser.getRybh()+")"+LUser.getRyxm());
			mv.addObject("today",DateUtil.getDay());
		}
		//初始化文件配置信息
		public void iniFile(ModelAndView mv) {
			String[] fjxx = fileService.getFjList(this.getPageData().getString("guid"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0]);
			mv.addObject("fjConfig",fjxx[1]);
		}
	
//	/**
//	 * 跳转单位信息编辑页面（增加、修改、查看）
//	 * @return
//	 */
//	@RequestMapping(value="/goEditPage")
//	public ModelAndView goEditPage(){
//		PageData pd = this.getPageData();
//		ModelAndView mv = this.getModelAndView();
//		//获取操作类型参数 C增加 U修改 L查看
//		String operateType = pd.getString("operateType");
//		if(operateType.equals("C")){
//			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
//			mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
//		}else if(operateType.equals("U")||operateType.equals("L")){
//			Map<?, ?>  map = grjfszService.getObjectById(pd.getString("dwbh"));
//			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
//			mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
//			mv.addObject("dwb", map);
//			mv.addObject("guid", pd.getString("dwbh"));
//		}
//		mv.setViewName("ysgl/grjfsz/grjfsz_edit");
//		mv.addObject("operateType",operateType);
//		return mv;
//	}
//	/**
//	 * 复核
//	 */
//	@RequestMapping(value="/goFhPage",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public void doPublish(){
//		PageData pd = this.getPageData();
//		String dwbh = pd.getString("dwbh");
//		grjfszService.goFhPage(pd,dwbh);
//	}
//	/**
//	 * 保存
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public Object doSave(GX_SYS_DWB dwb){
//		PageData pd = this.getPageData();
//		String operateType = this.getPageData().getString("operateType");
//		String dwbh = pd.getString("guid");
//		int result=0;
//		String rybh = LUser.getRybh();
//		String loginId = xsxxService.getLoginIdByRybh(rybh);
//		dwb.setCzr(loginId);
//		if("C".equals(operateType))//新增
//		{  
//			//生成单位编号
//			String guid =this.get32UUID();//生成主键id
//			dwb.setGuid(guid);
//			dwb.setDwbh(AutoKey.createDwbh("GX_SYS_DWB", "dwbh", "6"));
//			result = grjfszService.doAdd(pd);
//			if(result==1){
//				return  "{success:'true', msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"',operateType:'U'}";
//			}else{
//				return MessageBox.show(false, MessageBox.FAIL_SAVE);
//			}
//		}
//		else if("U".equals(operateType))//修改
//		{
//			result = grjfszService.doUpdate(pd,dwbh);
//			if(result==1)
//			{
//				return "{success:'true',msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"'}";
//			}
//			else
//			{
//				return MessageBox.show(false, MessageBox.FAIL_SAVE);
//			}
//		}
//		else
//		{
//	        return	MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
//		}
//	}
//	
//	/**
//	 * 删除
//	 * @return
//	 */
//	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
//	@ResponseBody
//	public Object doDelete(){
//		PageData pd = this.getPageData();
//		String dwbh = pd.getString("dwbh");
//		//删除单位时验证该单位下是否有人员或下级单位或资产
//    	int k = grjfszService.doDelete(dwbh);
//		if(k>0){
//			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
//		}else{
//			return MessageBox.show(false, MessageBox.FAIL_DELETE);
//    	}
//	}
//	
//	
	/*@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
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
		//
		String sqsplx = pd.getString("sqsplx");
		String shzt = pd.getString("status");
		String djbh = pd.getString("djbh");
		String guid = pd.getString("id");
		//定义条件
		String sqltext = "";
		String tableName = "";
		String strWhere = "";
		String order = " a.djbh asc ";
		if(Validate.isNullOrEmpty(sqsplx) || "0".equals(sqsplx)) {
			sqltext += "A.GUID,A.PROCINSTID,A.SQRXM AS SQRMC,A.SZBM AS SZBMMC,A.DJBH,TO_CHAR(A.SQRQ,'yyyy-MM-dd') as sqrq,"
					+"(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.SHZTDM+"' AND C.DM = A.SHZT) AS SHZTMC,"
					+ "'出差业务' AS SQSPLX ";
			tableName += " CW_CCSQSPB A ";
		}else {
			sqltext += "a.guid,a.procinstid, a.djbh, a.sqr, (select '('||r.rybh||')' || r.xm from gx_sys_ryb r where r.rybh=a.sqr)as sqrmc,"
					+ " a.szbm,  '(' || a.szbm || ')' ||  (select d.mc from gx_sys_dwb d where d.dwbh = a.szbm) as szbmmc, a.shzt,"
					+ " (select d.mc from gx_sys_dmb d where d.zl = '"+Constant.SHZTDMGW+"' and d.dm=a.shzt)as shztmc,TO_CHAR(A.SQRQ,'yyyy-MM-dd') as sqrq, '公务接待业务'as sqsplx ";
			tableName += "  CW_GWJDYWSQSPB a  ";
		}
		if(Validate.noNull(shzt)) {
			strWhere += " and a.shzt = "+shzt;
			if("01".equals(shzt)) {
				if("0".equals(sqsplx)) {
					strWhere += " and a.sqr = '"+LUser.getGuid()+"' ";
				}else if("1".equals(sqsplx)) {
					strWhere += " and a.sqr = '"+LUser.getRybh()+"'  ";
				}
			}
		}
		if(Validate.noNull(djbh)) {
			strWhere += " and a.djbh = "+djbh;
		}
		if(Validate.noNull(guid)) {
			strWhere += " and a.guid in ('"+guid+"')";
		}
		String sql = "select "+sqltext+" from "+tableName+" where 1=1 "+strWhere+" order by "+order;
		//
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("shztmc");
		m.setShowname("审核状态");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("djbh");
		m.setShowname("单据编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sqrmc");
		m.setShowname("申请人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("szbmmc");
		m.setShowname("所在部门");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sqsplx");
		m.setShowname("事前审批类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sqrq");
		m.setShowname("申请日期");
		mlist.add(m);
		m = null;
		//导出方法
		pageService.ExpExcel(sql,realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}*/
//	
//	/**
//	 *  导入上传
//	 */
//	@RequestMapping(value="/uploadt")
//	public ModelAndView Uploadt(MultipartFile imageFile) throws IllegalStateException, IOException{
//		PageData pd = this.getPageData();
//		ModelAndView mv = this.getModelAndView();
//    	String pictureFile_name =  imageFile.getOriginalFilename();
//		String newFileName = this.get32UUID()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
//		String realPath = this.getRequest().getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
//		//上传文件
//		String file = realPath+"WEB-INF/file/excel/"+newFileName;
//		File uploadPic = new File(file);
//		if(!uploadPic.exists()){
//			uploadPic.mkdirs();
//		}
//		//向磁盘写文件
//		imageFile.transferTo(uploadPic);
//		List listbt = new ArrayList();
//		listbt = grjfszService.insertJcsj(file);
//		mv.addObject("listbt", listbt);
//		mv.addObject("file", file);
//		String pname = pd.getString("pname");
//		System.out.println("========"+pname);
//		mv.addObject("bool","true");
//		mv.addObject("pname",pname);
// 		mv.setViewName("fzgn/jcsz/jsxxsz/txl_imp");
//		return mv;
//	}
	/**
	 * 导出教师人员信息Excel   wzd
	 * @return
	 */
	/*@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		String searchJson = pd.getString("searchJson");
		String status = pd.getString("status");
		PageList pageList = new PageList();

		String sqltext = "";
		String shzt = pd.getString("status");
		String djbh = pd.getString("djbh");
		String guid = pd.getString("id");
		String sqsplx = pd.getString("sqsplx");
		String tableName = "";
		String strWhere = "";
		if(Validate.isNullOrEmpty(sqsplx) || "0".equals(sqsplx)) {
			sqltext += "A.GUID,A.PROCINSTID,A.SQRXM AS SQRMC,A.SZBM AS SZBMMC,A.DJBH,TO_CHAR(A.SQRQ,'yyyy-MM-dd') as sqrq,"
					+"(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.SHZTDM+"' AND C.DM = A.SHZT) AS SHZTMC,"
					+ "'出差业务' AS SQSPLX ";
			tableName += " CW_CCSQSPB A ";
		}else {
			sqltext += "a.guid,a.procinstid, a.djbh, a.sqr, (select '('||r.rybh||')' || r.xm from gx_sys_ryb r where r.rybh=a.sqr)as sqrmc,"
					+ " a.szbm,  '(' || a.szbm || ')' ||  (select d.mc from gx_sys_dwb d where d.dwbh = a.szbm) as szbmmc, a.shzt,"
					+ " (select d.mc from gx_sys_dmb d where d.zl = '"+Constant.SHZTDMGW+"' and d.dm=a.shzt)as shztmc,TO_DATE(A.SQRQ,'yyyy-MM-dd') as sqrq, '公务接待业务'as sqsplx ";
			tableName += "  CW_GWJDYWSQSPB a  ";
		}
		if(Validate.noNull(shzt)) {
			strWhere += " and a.shzt = "+shzt;
			if("01".equals(shzt)) {
				if("0".equals(sqsplx)) {
					strWhere += " and a.sqr = '"+LUser.getGuid()+"' ";
				}else if("1".equals(sqsplx)) {
					strWhere += " and a.sqr = '"+LUser.getRybh()+"' ";
				}
			}
		}
		if(Validate.noNull(djbh)) {
			strWhere += " and a.djbh = "+djbh;
		}
		
		
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.gwjdsqService.expExcel1(realfile, shortfileurl, guid,searchValue,rybh,sql);
	}*/
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/expExcel")
	@ResponseBody
	public Object stryexpXsInfo11() {
		PageData pd = this.getPageData();
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String sqltext = "";
		String shzt = pd.getString("status");
		String djbh = pd.getString("djbh");
		String guid = pd.getString("id");
		String sqsplx = pd.getString("sqsplx");
		String tableName = "";
		String strWhere = "";
		Object[] dwbh2 = null;
		if(Validate.isNullOrEmpty(sqsplx) || "0".equals(sqsplx)) {
			sqltext += "select A.GUID,A.PROCINSTID,A.SQRXM AS SQRMC,A.SZBM AS SZBMMC,A.DJBH,TO_CHAR(A.SQRQ,'yyyy-MM-dd') as sqrq,"
					+"(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.SHZTDM+"' AND C.DM = A.SHZT) AS SHZTMC,"
					+ "'出差业务' AS SQSPLX from CW_CCSQSPB A where 1 = 1 ";
		}else {
			sqltext += "select a.guid,a.procinstid, a.djbh, a.sqr, (select '('||r.rybh||')' || r.xm from gx_sys_ryb r where r.rybh=a.sqr)as sqrmc,"
					+ " a.szbm,  '(' || a.szbm || ')' ||  (select d.mc from gx_sys_dwb d where d.dwbh = a.szbm) as szbmmc, a.shzt,"
					+ " (select d.mc from gx_sys_dmb d where d.zl = '"+Constant.SHZTDMGW+"' and d.dm=a.shzt)as shztmc,TO_DATE(A.SQRQ,'yyyy-MM-dd') as sqrq, "
							+ "'公务接待业务'as sqsplx from CW_GWJDYWSQSPB a where 1=1 ";
		}
		if(Validate.noNull(guid)){
			sqltext += " and guid in ('"+guid.trim()+"') ";
		}
		if(Validate.noNull(shzt)) {
			sqltext += " and a.shzt = "+shzt;
			if("01".equals(shzt)) {
				if("0".equals(sqsplx)) {
					sqltext += " and a.sqr = '"+LUser.getGuid()+"' ";
				}else if("1".equals(sqsplx)) {
					sqltext += " and a.sqr = '"+LUser.getRybh()+"' ";
				}
			}
		}
		if(Validate.noNull(djbh)) {
			strWhere += " and a.djbh = "+djbh;
		}
		
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.sqspcxService.expExcel1(realfile, shortfileurl, guid,searchValue,sqltext,dwbh2);
	}
	
}




