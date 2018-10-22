package com.googosoft.controller.txrybmhz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
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
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.ysgl.grjfsz.GrjfszService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value ="/txrybmhz")
public class TxrybmhzController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	@Resource(name="grjfszService")
	private GrjfszService grjfszService;//单例

	@Resource(name="dictService")
	DictService dictService;
	
	private DBHelper db;
	
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String qc = pd.getString("qc");
		String bmh = pd.getString("bmh");
		System.err.println("_____"+bmh);
		mv.setViewName("ysgl/grjfsz/txrybmhz_list");
		mv.addObject("dwbh",dwbh);
		mv.addObject("bmh",bmh);
		mv.addObject("qc",qc);
		return mv;

	}
	

	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String dwbh = pd.getString("treedwbh");
		String sffh = pd.getString("search[value]");
		System.err.println("________"+dwbh+"____"+sffh);
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" (  (select '111'guid,'审核通过'shzt,'离休'bmmc,NVL(max(rownum),0.00)rs,NVL(TO_CHAR(sum(a.SFHJ), 'FM999999990.00'),0.00)sfgz from CW_LZXZB a where a.bm='离休人员')union\r\n" + 
				"(select '111'guid,'审核通过'shzt,'退   休'bmmc,NVL(max(rownum),0.00)rs,NVL(TO_CHAR(sum(a.SFHJ), 'FM999999990.00'),0.00)sfgz from CW_LZXZB a where a.bm='退休人员')union\r\n" + 
				"(select '111'guid,'审核通过'shzt,'遗   属'bmmc,NVL(max(rownum),0.00)rs,NVL(TO_CHAR(sum(a.SFHJ), 'FM999999990.00'),0.00)sfgz from CW_LZXZB a where a.bm='遗属')union\r\n" + 
				"(select '111'guid,'审核通过'shzt,'临时工'bmmc,NVL(max(rownum),0.00)rs,NVL(TO_CHAR(sum(a.SFHJ), 'FM999999990.00'),0.00)sfgz from CW_LZXZB a where a.bm='临时工')union\r\n" + 
				"(select '111'guid,'审核通过'shzt,'合   计'bmmc,NVL(max(rownum),0.00)rs,NVL(TO_CHAR(sum(a.SFHJ), 'FM999999990.00'),0.00)sfgz from CW_LZXZB a )\r\n" + 
				"\r\n" + 
				" ");
			
//		if(Validate.noNull(dwbh)){
//			tablename.append("AND g.bmbh=(selecT BMH FROM GX_SYS_DWB B WHERE B.DWBH='"+dwbh+"') ");//根据左侧树查询右侧列表
//		}
//		if(Validate.isNull(sffh)){
//			tablename.append(" and g.sffh = '0' ");
//		}
		tablename.append(")k");

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
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value="/getPageList")
//	@ResponseBody
//	public Object getPageLists(){
//		PageList pageList = new PageList();
//		//设置查询字段名
//		StringBuffer tablename = new StringBuffer();
//		StringBuffer sqltext = new StringBuffer();
//		sqltext.append("g.guid,(select d.mc from  gx_sys_dwb d where d.bmh = g.bmbh) as bmmc, g.nd,g.xmbh, g.xmmc,  ( select t.xm from CW_JZGXXB t where t.xh = g.fzr ) as fzr,"
//		+ "(select d.mc from gx_sys_dmb d where d.zl = 'xmfl' and d.dm = g.xmlx) as xmlx, to_char(g.ysje, 'FM9999999999999990.0000') as ysje, to_char(g.syje, 'FM9999999999999990.0000') as syje,"
//		+ "(select d.mc from gx_sys_dmb d  where d.zl = 'jflx'and d.dm = g.jflx) as jflx,"
//		+ " g.kssj, g.jssj,g.bz,  g.czr,g.czrq ");
//		pageList.setSqlText(sqltext.toString());
//		//设置表名
//		pageList.setTableName("Cw_jfszb g");
//		//设置表主键名
//		pageList.setKeyId("guid");
//		//设置WHERE条件
//		String strWhere = "";
//		PageData pd = this.getPageData();
//		String dwbh = pd.getString("treedwbh");
//		String rybh = LUser.getRybh();//当前登陆者的人员编号
//		
//		if(Validate.noNull(dwbh)){
//			strWhere="AND g.bmbh=(select bmh from GX_SYS_DWB B where B.DWBH='"+dwbh+"') ";//根据左侧树查询右侧列表
//		}
//		pageList.setStrWhere(strWhere);
//		//设置合计值字段名
//		pageList.setHj1("");
//		//页面数据
//	    pageList = pageService.findPageList(pd,pageList);
//		Gson gson = new Gson();
//		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
//		return pageInfo.toJson();
//	}
	
	/**
	 * 跳转单位信息编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		String qc = pd.getString("qc");
		String bmh = pd.getString("bmh");
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
			mv.addObject("qc", qc);
			mv.addObject("bmh", bmh);
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = grjfszService.getObjectById(pd.getString("dwbh"));
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
			mv.addObject("dwb", map);
			mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.setViewName("ysgl/grjfsz/grjfsz_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 复核
	 */
	@RequestMapping(value="/goFhPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void doPublish(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		grjfszService.goFhPage(pd,dwbh);
	}
	/**
	 * 保存
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_DWB dwb){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String dwbh = pd.getString("guid");
		String xmbh = pd.getString("xmbh");
		int result=0;
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		dwb.setCzr(loginId);
		if("C".equals(operateType))//新增
		{  
			//生成单位编号
			String guid =this.get32UUID();//生成主键id
			dwb.setGuid(guid);
			dwb.setDwbh(AutoKey.createDwbh("GX_SYS_DWB", "dwbh", "6"));
			String check = grjfszService.checkXmbh(xmbh);
			if(Integer.parseInt(check)>0){
				return  "{success:'false', msg:'项目编号重复！',dwbh:'"+dwb.getDwbh()+"',operateType:'U'}";
			}
			result = grjfszService.doAdd(pd);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = grjfszService.doUpdate(pd,dwbh);
			if(result==1)
			{
				return "{success:'true',msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
	        return	MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		//删除单位时验证该单位下是否有人员或下级单位或资产
    	int k = grjfszService.doDelete(dwbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	
	
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
		sqltext.append(" select g.guid,g.bmbh,('('||g.bmbh||')'||(select d.mc from gx_sys_dmb d where d.dm = g.bmbh)) as bmmc,"
				+ " (select t.mc from GX_SYS_DWB t where t.dwbh = g.fzr) as xmfzrmc, g.xmmc, g.nd, g.fzr,('('||g.fzr||')'||(select d.xm from gx_sys_ryb d where d.rybh = g.bmbh)) as fzrmc,"
				+ " to_char(g.ysje, 'FM9999999999999990.0000') as ysje, to_char(g.syje, 'FM9999999999999990.0000') as syje, g.xmbh, g.xmlx,"
				+ " (select d.mc from gx_sys_dmb d where d.zl = 'xmfl' and d.dm = g.xmlx) as xmlxmc, g.jflx, (select d.mc   from gx_sys_dmb d  where d.zl = 'jflx'"
				+ " and d.dm = g.jflx) as jflxmc,g.kssj,  g.jssj,g.bz,  g.czr,g.czrq from Cw_jfszb g  where 1=1");
		String fzr = pd.getString("fzr");
		String xmmc = pd.getString("xmmc");
		if(Validate.noNull(fzr)){
			sqltext.append(" and g.fzrmc like '%" + fzr + "%'");
		}
		if(Validate.noNull(xmmc)){
			sqltext.append(" and g.xmmc like '%" + xmmc + "%'");
		}
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" and g.guid in ('"+id+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("bmmc");
		m.setShowname("部门名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("nd");
		m.setShowname("年度");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xmbh");
		m.setShowname("项目编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("fzrmc");
		m.setShowname("负责人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xmlxmc");
		m.setShowname("项目类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("jflxmc");
		m.setShowname("经费类型");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("kssj");
		m.setShowname("项目开始时间");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("jssj");
		m.setShowname("项目开始时间");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ysje");
		m.setShowname("预算金额（万元）");
		mlist.add(m);
		m = null;
		
		
		m = new M_largedata();
		m.setName("syje");
		m.setShowname("剩余金额（万元）");
		mlist.add(m);
		m = null;
		
		
		m = new M_largedata();
		m.setName("bz");
		m.setShowname("备注");
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
		System.out.println("========"+pname);
		mv.addObject("bool","true");
		mv.addObject("pname",pname);
 		mv.setViewName("fzgn/jcsz/jsxxsz/txl_imp");
		return mv;
	}
	
	
}





