package com.googosoft.controller.xmgl.jcsz;

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
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.xmgl.jcsz.Cw_cgmlszb;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xmgl.cgmlsz.CgmlszService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/cgmlsz")
public class CgmlszController extends BaseController {
	@Resource(name="dictService")
	private DictService dictService;//单例
	@Resource(name="cgmlszService")
	private CgmlszService cgmlszService;//单例
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	/**
	 * 获取采购目录设置列表页面
	 * @return
	 */
	@RequestMapping(value="/goCgmlszPage")
	public ModelAndView goCgmlszPage(){
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

//		String fyfl = pd.getString("fyfl");
		mv.setViewName("ysgl/xmsz/cgmlsz/cg_list");
//		mv.addObject("fyfl", fyfl);
		return mv;
		
	}
	
	/**
	 * 获取采购目录设置列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		
		PageList pageList = new PageList(); // 分页方法实体类
		
		/*
		 * 设置查询字段名
		 */
		StringBuffer sqlText = new StringBuffer();
		
		sqlText.append(" GUID, MLDM, MLMC, (SELECT A.MLMC FROM CW_CGMLSZB A WHERE A.GUID=K.SJML) AS SJML ");
		pageList.setSqlText(sqlText.toString());
		
		/*
		 * 设置表名
		 */
		pageList.setTableName(" CW_CGMLSZB K ");
		
		/*
		 * 设置表主键名
		 */
		pageList.setKeyId("GUID");
		
		/*
		 * 设置WHERE条件，根据点击左侧树，显示右侧列表
		 */
		PageData pd = this.getPageData();
		String mldms = pd.getString("mldms");
		
		StringBuffer tjText = new StringBuffer(); // 条件
		if(Validate.noNull(mldms)) {
			tjText.append(" AND K.SJML='"+mldms+"' OR K.GUID='"+mldms+"' ");
		}
		pageList.setStrWhere(tjText+"");
		
		/*
		 * 设置合计值字段名
		 */
		pageList.setHj1("");
		
		/*
		 * 页面数据
		 */
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"", pageList.getTotalList().get(0).get("NUM")+"",
				pageList.getTotalList().get(0).get("NUM")+"", gson.toJson(pageList.getContentList())); // 列表数据实体类
		return pageInfo.toJson();
		
	}
	    
	/**
	 * 获取采购目录设置树
	 * @return
	 */
	@RequestMapping(value="/cgmlszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cgmlszTree(){
		
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String fyfl = pd.getString("fyfl");
		
		if(menu.equals("get-cgmlsz")){
			String loginrybh = LUser.getRybh();
			return cgmlszService.getPowerDwNode(pd, loginrybh, rootPath,fyfl);
		}else{
			return "";
		}
	}
	
	/**
	 * 跳转采购目录信息编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goCGEditPage")
	public ModelAndView goCGEditPage(){
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		/*
		 * 获取操作类型参数 C增加 U修改 L查看，
		 */
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){
			
			Map<?, ?>  map = cgmlszService.getSjmlByGuid(pd.getString("guid")); // 由采购分类得到cg_edit页面中的上级目录
			
			mv.addObject("cgmlsz", map); // 这里写的cgmlsz，则cg_edit.jsp中就要这么写 value="${cgmlsz.guid}。这是个map。
			
		}else if(operateType.equals("U")||operateType.equals("L")){
			
			Map<?, ?>  map = cgmlszService.getObjectById(pd.getString("guid"));
			
			mv.addObject("cgmlsz", map); // 这里写的cgmlsz，则cg_edit.jsp中就要这么写 value="${cgmlsz.guid}。这是个map。
		}
		
		mv.setViewName("ysgl/xmsz/cgmlsz/cg_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 保存采购目录信息
	 * @param CW_FYKMDZB
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSave(Cw_cgmlszb cgmlszb){
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		String rybh = LUser.getRybh();
		cgmlszb.setCzr(rybh);
		if("C".equals(operateType))//新增
		{
			//判断部门号是否重复
			boolean checkbmh=cgmlszService.doCheckMldm(cgmlszb.getMldm());
			if(checkbmh==false)
			{
				return  "{success:false,msg:'目录代码不可重复!'}";
			}
			
			/*
			 * 生成GUID
			 */
			String guid =this.get32UUID();
			cgmlszb.setGuid(guid);
			

			result = cgmlszService.doAdd(cgmlszb);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',operateType:'U'}"; 
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = cgmlszService.doUpdate(cgmlszb);
			if(result==1)
			{
				return "{success:'true',msg:'信息保存成功！'}";
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
	 * 获取采购目录设置树
	 * @return
	 */
	@RequestMapping(value="/window")
	public ModelAndView window(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String text_id=pd.getString("text_id");
		mv.setViewName("/ysgl/xmsz/cgmlsz/window");
		mv.addObject("controlId", controlId);
		mv.addObject("text_id", text_id);
		return mv;
	}

	/**
	 * 获取点击“选择”后，弹窗中右侧的列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getTreePageList")
	@ResponseBody
	public Object getTreePageList(){
		
		PageList pageList = new PageList(); // 分页方法实体类
		
		/*
		 * 设置查询字段名
		 */
		StringBuffer sqlText = new StringBuffer();
		
		sqlText.append(" GUID, MLDM, MLMC, (SELECT A.MLMC FROM CW_CGMLSZB A WHERE A.GUID=K.SJML) AS SJML ");
		pageList.setSqlText(sqlText.toString());
		
		/*
		 * 设置表名
		 */
		pageList.setTableName(" CW_CGMLSZB K ");
		
		/*
		 * 设置表主键名
		 */
		pageList.setKeyId("GUID");
		
		
		PageData pd = this.getPageData();
		String fyfls = pd.getString("fyfls");
	
		StringBuffer tjText = new StringBuffer(); // 条件
		if(Validate.noNull(fyfls)) {
			tjText.append(" AND K.SJML='"+fyfls+"' OR K.GUID='"+fyfls+"' ");
		}
		
		pageList.setStrWhere(tjText+"");
		
		/*
		 * 设置合计值字段名
		 */
		pageList.setHj1("");
		
		/*
		 * 页面数据
		 */
		pageList = pageService.findWindowList(pd,pageList,"CGML");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"", pageList.getTotalList().get(0).get("NUM")+"",
				pageList.getTotalList().get(0).get("NUM")+"", gson.toJson(pageList.getContentList())); // 列表数据实体类
		return pageInfo.toJson();
	}
	
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		String i = cgmlszService.doDel(guid);
		return new Gson().toJson(i);
//		if (i > 0) {
//			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
//		} else {
//			return MessageBox.show(false, MessageBox.FAIL_DELETE);
//		}
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
		sqltext.append(" select GUID," + 
				"       MLDM, " + 
				"       MLMC, " + 
				"       (SELECT A.MLMC FROM CW_CGMLSZB A WHERE A.GUID = K.SJML) AS SJML " + 
				"  from CW_CGMLSZB K where 1 = 1  ");
		String dwbh = pd.getString("treedwbh");
		
		String mldms = pd.getString("mldms");
		
		if(Validate.noNull(mldms)) {
			sqltext.append(" AND K.SJML='"+mldms+"' OR K.GUID='"+mldms+"' ");
		}
		
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" and k.guid in ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		sqltext.append(" order by MLDM asc ");
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("mldm");
		m.setShowname("目录代码");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("mlmc");
		m.setShowname("目录名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sjml");
		m.setShowname("上级目录");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		String guid = pd.getString("id");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.cgmlszService.expExcel(realfile, shortfileurl, guid,searchValue);
	}
}
