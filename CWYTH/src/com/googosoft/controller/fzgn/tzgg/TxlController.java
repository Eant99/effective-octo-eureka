package com.googosoft.controller.fzgn.tzgg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.MenuFlag;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.tzgg.ZC_TXL;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.tzgg.TxlService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;


@Controller
@RequestMapping(value="/txl")
public class TxlController extends BaseController{
	
	@Resource(name="txlService")
	private TxlService txlService; //单例
	
	@Resource(name="dictService")
	private DictService dictService;//数据字典单例
	
	@Resource(name="pageService")
	private PageService pageService;//分页单例
	
	/**
	 * 获取通讯录信息列表页面
	 * @return
	 */
	@RequestMapping(value="/goTxlPage")
	public ModelAndView goDdbPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fzgn/tzgg/txl_list");
		return mv;
	}
	
	/**
	 * 获取通讯录列表数据
	 * @return
	 * @throws Exception
	 */
	/**
	 * 获取发布系统信息列表数据
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("gid,rybh,rygh,xm,bgdd,zw,bddh,moblie,qq,email,pxxh,decode(zt,'1','正常','0','禁用') zt ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GID");//主键
		pageList.setTableName("(select t.*,(select rygh from gx_sys_ryb r where rybh = t.rybh) rygh from zc_txl t) t");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 新建、保存通讯录信息（添加通讯录信息）
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_TXL txl){
		String operateType = this.getPageData().getString("operateType");
		String b = "";
		int i;
		if("C".equals(operateType)){
			i = txlService.doAdd(txl);
			if(i==1){
				b = "{\"success\":\"true\",\"gid\":\""+txl.getGid()+"\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"gid\":\""+txl.getGid()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){
			i=txlService.doUpdate(txl);
			if(i==1){
				b = "{\"success\":\"true\",\"ddbh\":\""+txl.getGid()+"\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"ddbh\":\""+txl.getGid()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else{
			b = "{'success':'F','msg':'参数传入有误！'}";
		}
		return b;
	}
	
	/**
	 * 删除通讯录信息
	 * @param ddb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String gid = pd.getString("id");
		int k = txlService.doDelete(gid);
		if(k>0){
			return "{success:true,msg:'信息删除成功！'}";
		}else{
			return "{success:false,msg:'信息删除失败！'}";
		}
	}

	/**
	 * 跳转Edit页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = txlService.getObjectById(pd.getString("id"));
		}
		mv.addObject("txl", map);
		mv.addObject("gid", pd.getString("id"));
		mv.setViewName("fzgn/tzgg/txl_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 跳转到增加科目页面
	 */
	@RequestMapping(value="/goEditKmPage")
	public ModelAndView goEditKmPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = txlService.getObjectById(pd.getString("id"));
		}
		mv.addObject("kmsz", map);
		mv.addObject("gid", pd.getString("id"));
		mv.setViewName("kjhs/kmsz/km_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 跳转通讯录数据导入页面
	 */
	@RequestMapping(value="/doImp")
	public ModelAndView doImp(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		mv.addObject("mkbh",mkbh);
		mv.setViewName("fzgn/tzgg/txl_imp");
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
		listbt = txlService.insertJcsj(file);
		mv.addObject("listbt", listbt);
		mv.addObject("file", file);
		String mkbh = pd.getString("mkbh");
		mv.addObject("mkbh",mkbh);
 		mv.setViewName("fzgn/tzgg/txl_imp");
		return mv;
	}
	
	/**
	 * 导出人员信息Excel
	 * @return
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
		sqltext.append(" select gid,rybh,rygh,xm,bgdd,zw,bddh,moblie,qq,email,pxxh,decode(zt,'1','正常','0','禁用') zt from (select t.*,(select rygh from gx_sys_ryb r where rybh = t.rybh) rygh from zc_txl t) t  where 1 = 1 ");
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" and gid in ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" order by rygh ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		
		M_largedata m = new M_largedata();
		m.setName("rygh");
		m.setShowname("人员工号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xm");
		m.setShowname("姓名");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bgdd");
		m.setShowname("办公地点");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zw");
		m.setShowname("职务");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("bddh");
		m.setShowname("办公电话");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("moblie");
		m.setShowname("手机号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("qq");
		m.setShowname("QQ");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("email");
		m.setShowname("Email");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zt");
		m.setShowname("状态");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
}