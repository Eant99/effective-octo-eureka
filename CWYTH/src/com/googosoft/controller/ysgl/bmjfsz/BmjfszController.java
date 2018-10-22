package com.googosoft.controller.ysgl.bmjfsz;

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
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.DwbService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.ysgl.bmjfsz.BmjfszService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Controller
@RequestMapping(value ="/bmjfsz")
public class BmjfszController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	@Resource(name="bmjfszService")
	private BmjfszService bmjfszService;//单例

	private DBHelper db;
	
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("ysgl/bmjfsz/bmjfsz_list2");
		return mv;

	}
	

	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" (select k.guid, k.bmbh, k.nd, to_char(k.ysje,'FM9999999999999990.0000') as ysje, "
				+ "to_char(k.syje,'FM9999999999999990.0000') as syje,k.sffh,(case k.sffh when '1' then'已复核' else '未复核' end) as sffhmc,"
				+ " k.bz, d.mc  from Cw_bmjfszb K left join gx_sys_dwb d on d.dwbh = k.bmbh) k");
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
	 * 跳转单位信息编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){
			
			String bh = AutoKey.createDwbh("CW_BMJFSZB", "guid", "6");
			Map<String,String> map = new HashMap<String,String>();
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = bmjfszService.getObjectById(pd.getString("dwbh"));
//			if(operateType.equals("L")){
//				String dwxzmap = dictService.getMcByDm(Constant.DWXZ,map.get("DWXZ")+"");
//				mv.addObject("dwxzmap", dwxzmap);
//				String syslxmap = dictService.getMcByDm(Constant.SYSLX,map.get("SYSLX")+"");
//				mv.addObject("syslxmap", syslxmap);
//				String syslbmap = dictService.getMcByDm(Constant.SYSLB,map.get("SYSLB")+"");
//				mv.addObject("syslbmap", syslbmap);
//				String sysjbmap = dictService.getMcByDm(Constant.SYSJB,map.get("SYSJB")+"");
//				mv.addObject("sysjbmap", sysjbmap);
//				String sysgsmap = dictService.getMcByDm(Constant.SYSGS,map.get("SYSGS")+"");
//				mv.addObject("sysgsmap", sysgsmap);
//				String ssxkmap = dictService.getMcByDm(Constant.SSXK,map.get("SSXK")+"");
//				mv.addObject("ssxkmap", ssxkmap);
//				String dwbbmap = dictService.getMcByDm(Constant.DWBB,map.get("DWBB")+"");
//				mv.addObject("dwbbmap", dwbbmap);
//			}
			mv.addObject("dwb", map);
			mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.setViewName("ysgl/bmjfsz/bmjfsz_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 复核
	 */
//	@RequestMapping(value="/goFhPage")
//	public void goFhPage(){
//		PageData pd = this.getPageData();
//		ModelAndView mv = this.getModelAndView();
//		 bmjfszService.goFhPage(pd,"");
//
//	}
	@RequestMapping(value="/goFhPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void goFhPage(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		System.err.println("dwbh____"+dwbh);
		bmjfszService.goFhPage(pd,dwbh);
//		if(grjfszService.goFhPage(pd,dwbh)){
//			return MessageBox.show(true, "发布成功！");
//		}else {
//			return MessageBox.show(false, "发布失败！");
//		}	
	}
	/**
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_DWB dwb){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String dwbh = pd.getString("guid");
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
			result = bmjfszService.doAdd(pd);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = bmjfszService.doUpdate(pd,dwbh);
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
    	int k = bmjfszService.doDelete(dwbh);
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
		sqltext.append(" select k.guid, k.bmbh, k.nd, k.ysje, k.syje, k.bz, d.mc  from Cw_bmjfszb K left join gx_sys_dwb d on d.dwbh = k.bmbh where 1=1");
		String bmmc = pd.getString("mc");
		String nd = pd.getString("nd");
		if(Validate.noNull(bmmc)){
			sqltext.append(" and d.mc ='" + bmmc + "'");
		}
		if(Validate.noNull(nd)){
			sqltext.append(" and k.nd ='" + nd + "'");
		}
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" and k.guid in ('"+id+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("bmbh");
		m.setShowname("部门编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("nd");
		m.setShowname("年度");
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
		listbt = bmjfszService.insertJcsj(file);
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




