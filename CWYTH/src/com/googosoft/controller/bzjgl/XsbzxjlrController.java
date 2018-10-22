package com.googosoft.controller.bzjgl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.bzjgl.CW_XSBZMXB;
import com.googosoft.pojo.bzjgl.CW_XSBZZB;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.bzjgl.XsbzxjlrService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.ysgl.grjfsz.GrjfszService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value ="/xsbzxjlr")
public class XsbzxjlrController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;//单例
	
	@Resource(name="grjfszService")
	private GrjfszService grjfszService;//单例
	
	@Resource(name="dictService")
	DictService dictService;
	
	@Resource(name="xsbzxjlrService")
	private XsbzxjlrService xsbzxjlrService;//单例
	
	@Resource(name="rcbxService")
	private RcbxService rcbxService;//单例
	
	//跳转到补助学金录入页面
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String qc = pd.getString("qc");
		String bmh = pd.getString("bmh");
		mv.setViewName("bzjgl/xsbzxjlr/xsbzxjlr_list");
		mv.addObject("dwbh",dwbh);
		mv.addObject("bmh",bmh);
		mv.addObject("qc",qc);
		return mv;

	}
	//获得补助学金录入页面详情
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" distinct* ");
		tablename.append(" ( SELECT T.GUID,T.FABH,T.FAMC,T.ZY,(SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=T.XMBH)AS XMMC,(SELECT MC FROM GX_SYS_DMB WHERE ZL = 'zffs' AND DM=T.FFFS) AS FFFS,T.JBR,T.BZ, ");
		tablename.append(" TO_CHAR(T.FFJE,'FM999999999999999999999999999999990.00') as FFJE,TO_CHAR(T.FFSJ,'YYYY-MM-DD HH24:MI:SS') AS FFSJ FROM CW_XSBZZB T left join CW_XSBZMXB M ON T.FABH=M.FABH) K");
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
	/**
	 * 获得项目弹窗信息展示
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXmxxPageList")
	@ResponseBody
	public Object getXmxxPageList() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append("*");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName(" (select distinct a.guid,a.xmbh,a.xmmc,decode(nvl(ye,'0'),'0','0.00',to_char(round(YE,2),'fm9999999999999999999999999900.00'))ye,jzsj, row_number() over(partition by a.guid order by jzsj desc)rw," +
				"(SELECT D.MC FROM GX_SYS_DMB D WHERE D.ZL = '251' AND D.DM = A.XMLB) XMLBMC," +
				"(select B.mc from gx_sys_dmb B where B.zl = '"+Constant.XMDL+"' and B.dm = A.xmdl) AS XMDLMC," +
				"(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=a.fzr)as fzr," +
				"(SELECT decode(nvl(YSJE,'0'),'0','0.00',to_char(round(YSJE,2),'fm9999999999999999999999999999900.00')) FROM CW_XMJBXXB B WHERE B.GUID=A.GUID) AS YSJE," +
				" DECODE(NVL(zfcgsyje, '0'),'0','0.00',TO_CHAR(ROUND(zfcgsyje, 2),'fm9999999999999999999999999900.00')) zfcgsyje," +
				" DECODE(NVL(fzfcgsyje, '0'),'0','0.00',TO_CHAR(ROUND(fzfcgsyje, 2),'fm9999999999999999999999999900.00')) fzfcgsyje," +
				"decode(a.jflx,'01','部门经费','个人经费')jflx,a.jflx as jflxdm " +
				"from  XMINFOS A  where 1=1 and a.ye<>0) k" 
						);
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = " and rw=1 ";
		PageData pd = this.getPageData();
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
	 * 随机生成32位的guid
	 * @return
	 */
	@RequestMapping(value="/getGuid")
	@ResponseBody
	public String getGuid(){
		return get32UUID();
	}
	/**
	 * 人员银行卡号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/xsyhxx",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object xsyhxx(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String dqdlrguid = pd.getString("dqdlrguid");
		List list = xsbzxjlrService.getXsyhxx(dqdlrguid);
		Gson gson = new Gson();
		String datas = gson.toJson(list);
		return datas;
	}
	/**
	 * 跳转单位信息编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		String qc = pd.getString("qc");
		String bmh = pd.getString("bmh");
		String guid = pd.getString("guid");
		ModelAndView mv = this.getModelAndView();
		List zffs = dictService.getDict(Constant.ZFFS);
		mv.addObject("zffs",zffs);
		mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
		mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
		mv.addObject("bzdjList",xsbzxjlrService.getDjlist());//补助等级List
		
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){		
			mv.addObject("qc", qc);
			mv.addObject("bmh", bmh);
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = xsbzxjlrService.getObjectById(guid);
			List list = new ArrayList<>();
			List yhklist = new ArrayList<>();
			list =xsbzxjlrService.getBzxjlrmc(guid);
			yhklist = xsbzxjlrService.getXxyhk();
			mv.addObject("bzxj", map);
			mv.addObject("list", list);
			mv.addObject("yhklist", yhklist);
			mv.addObject("guid", guid);
		}
		mv.setViewName("bzjgl/xsbzxjlr/xsbzxjlr_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 保存
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doSave(CW_XSBZZB xsbzzb){
		String operateType = this.getPageData().getString("operateType");
		String fabh = this.getPageData().getString("fabh");
		String ffzje = this.getPageData().getString("ffzje");
		String xhs = this.getPageData().getString("xhs");
		String yhmcs = this.getPageData().getString("yhmcs");
		String yhkhs = this.getPageData().getString("yhkhs");
		String bzbhs = this.getPageData().getString("bzbhs");
		String ffjes = this.getPageData().getString("ffjes");
		String b = "";
		int i;
		if("C".equals(operateType)){
			xsbzzb.setFabh(AutoKey.createDwbh("CW_XSBZZB", "fabh", "6"));
			i = xsbzxjlrService.doAdd(xsbzzb,fabh,ffzje,xhs,bzbhs,yhmcs,yhkhs,ffjes); 
			if(i==1){
				if(Validate.isNull(xsbzzb.getGuid())){
					b = "{\"success\":\"true\",\"gid\":\""+xsbzzb.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
				}else{
					b = "{\"success\":\"true\",\"gid\":\""+xsbzzb.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
				}
			}else{
				b = "{\"success\":\"false\",\"gid\":\""+xsbzzb.getGuid()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}
		else if("U".equals(operateType)){
			i = xsbzxjlrService.doUpdate(xsbzzb,fabh,ffzje,xhs,bzbhs,yhmcs,yhkhs,ffjes); 
			if(i==1){
				if(Validate.isNull(xsbzzb.getGuid())){
					b = "{\"success\":\"true\",\"gid\":\""+xsbzzb.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
				}else{
					b = "{\"success\":\"true\",\"gid\":\""+xsbzzb.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
				}
			}else{
				b = "{\"success\":\"false\",\"gid\":\""+xsbzzb.getGuid()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}
		return b ;
	} 
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String fabh = pd.getString("fabh");
		//删除单位时验证该单位下是否有人员或下级单位或资产
    	int k = xsbzxjlrService.doDelete(fabh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	/**
	 * 批量删除
	 * @return
	 */
	@RequestMapping(value="/doDelete2",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete2(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
    	int k = xsbzxjlrService.doDelete2(guid);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	/**
	 *  导出
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
		
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("SELECT distinct T.GUID,T.FABH,T.FAMC,T.ZY,(SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=T.XMBH)AS XMMC,(SELECT MC FROM GX_SYS_DMB WHERE ZL = 'zffs' AND DM=T.FFFS) AS FFFS,T.JBR,T.BZ, ");
		sqltext.append(" TO_CHAR(T.FFJE,'FM999999999999999999999999999999990.00') as FFJE,TO_CHAR(T.FFSJ,'YYYY-MM-DD HH24:MI:SS') AS FFSJ FROM CW_XSBZZB T left join CW_XSBZMXB M ON T.FABH=M.FABH ");
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" where T.guid in ('"+id+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		//方案编号 方案名称  发放金额   摘要 项目编号  发放方式 发放时间 
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("FABH");
		m.setShowname("方案编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("FAMC");
		m.setShowname("方案名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("FFJE");
		m.setShowname("发放金额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ZY");
		m.setShowname("摘要");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XMMC");
		m.setShowname("项目名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("FFFS");
		m.setShowname("发放方式");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("FFSJ");
		m.setShowname("发放时间");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 *  导入上传
	 */
	@RequestMapping(value="/uploadt")
	public ModelAndView Uploadt(MultipartFile imageFile) throws IllegalStateException, IOException{
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
		listbt = grjfszService.insertJcsj(file);
		mv.addObject("listbt", listbt);
		mv.addObject("file", file);
		String pname = pd.getString("pname");
		mv.addObject("bool","true");
		mv.addObject("pname",pname);
 		mv.setViewName("fzgn/jcsz/jsxxsz/txl_imp");
		return mv;
	}
}