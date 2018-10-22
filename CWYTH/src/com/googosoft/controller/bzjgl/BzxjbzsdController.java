package com.googosoft.controller.bzjgl;

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
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.bzjgl.CW_BZBZSZB;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.bzjgl.BzxjbzsdService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.ysgl.grjfsz.GrjfszService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value ="/bzxjbzsd")
public class BzxjbzsdController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")//单例
	private XsxxService xsxxService;
	
	@Resource(name="bzxjbzsdService")
	private BzxjbzsdService bzxjbzsdService;//

	@Resource(name="dictService")//单例
	DictService dictService;
	
	@Resource(name="grjfszService")//单例
	GrjfszService grjfszService;
	
	//跳转到补助学金标准设定页面
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String qc = pd.getString("qc");
		String bmh = pd.getString("bmh");
		mv.setViewName("bzjgl/bzxjbzsd/bzxjbzsd_list");
		mv.addObject("dwbh",dwbh);
		mv.addObject("bmh",bmh);
		mv.addObject("qc",qc);
		return mv;

	}
	//获得补助学金标准设定页面信息
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String dwbh = pd.getString("treedwbh");
		String sffh = pd.getString("search[value]");
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" (  select guid,bzbh,bzmc,(case sfqy when '1' then '是' when '0' then '否' else '' end )as sfqy,jbr,bz, ");
		tablename.append("to_char(bzje,'FM999999999999999999999999999999990.00') as bzje,to_char(szsj,'yyyy-MM-dd HH24:mi:ss') as szsj from cw_bzbzszb) k");
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
	 * 跳转补助学金标准设定编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		String qc = pd.getString("qc");
		String bmh = pd.getString("bmh");
		String dwbh = pd.getString("dwbh");
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
			mv.addObject("qc", qc);
			mv.addObject("bmh", bmh);
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = bzxjbzsdService.getObjectById(dwbh);
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
			mv.addObject("bzxjb", map);
			mv.addObject("guid", dwbh);
		}
		mv.setViewName("bzjgl/bzxjbzsd/bzxjbzsd_edit");
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
	public Object doSave(CW_BZBZSZB bzbz){
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		int result=0;
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		bzbz.setJbr(loginId);
		if("C".equals(operateType))//新增
		{  
			bzbz.setBzbh(AutoKey.createDwbh("CW_BZBZSZB", "bzbh", "6"));
			result = bzxjbzsdService.doAdd(bzbz);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',bzbz:'"+bzbz.getBzbh()+"',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = bzxjbzsdService.doUpdate(bzbz);
			if(result==1)
			{
				return "{success:'true',msg:'信息保存成功！',dwbh:'"+bzbz.getBzbh()+"'}";
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
	 * 单个删除
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String bzbh = pd.getString("bzbh");
    	int k = bzxjbzsdService.doDelete(bzbh);
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
		String bzbh = pd.getString("bzbh");
    	int k = bzxjbzsdService.doDelete2(bzbh);
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
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("select guid,bzbh,bzmc,(case sfqy when '1' then '是' when '0' then '否' else '' end )as sfqy,jbr,bz, ");
		
		sqltext.append("to_char(bzje,'FM999999999999999999999999999999990.00') as bzje,to_char(szsj,'yyyy-MM-dd HH24:mi:ss') as szsj from cw_bzbzszb ");

		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" where guid in ('"+id+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("BZBH");
		m.setShowname("标准编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("BZMC");
		m.setShowname("补助学金等级");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("BZJE");
		m.setShowname("补助金额");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("SZSJ");
		m.setShowname("设置时间");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SFQY");
		m.setShowname("是否启用");
		mlist.add(m);
		m = null;
		
//		m = new M_largedata();
//		m.setName("JBR");
//		m.setShowname("负责人");
//		mlist.add(m);
//		m = null;
		
		m = new M_largedata();
		m.setName("BZ");
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





