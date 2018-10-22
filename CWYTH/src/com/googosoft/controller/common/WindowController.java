package com.googosoft.controller.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.MenuFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.dao.systemset.qxgl.GlqxbDao;

import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.StateManager;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.tzgg.FbxxService;
import com.googosoft.service.fzgn.tzgg.TzxxService;
import com.googosoft.service.system.index.DeskService;
import com.googosoft.service.systemset.cssz.XtbService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.PageListUtil;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

/**
 * 弹窗以及左侧树控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping("/window")
public class WindowController extends BaseController{
	@Resource(name="xtbService")
	private XtbService xtbService;//单例
	
	@Resource(name="pageService")
	PageService pageService;
	
	
	
	@Resource(name="jdbcTemplate")
	public DBHelper db;
	
	
	
	@Resource(name="fbxxService")
	private FbxxService fbxxService;//单例
	
	@Resource(name="glqxbDao")
	public GlqxbDao glqxbDao;
	
	@Resource(name="tzxxService")
	private TzxxService tzxxService;//单例
	
	
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	@Resource(name="deskService")
	private DeskService deskService;
	
	/**
	 * 部门信息弹出窗
	 */
	@RequestMapping(value="/dwpage")
	public ModelAndView goDwxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String mkbh = pd.getString("mkbh");
		String controlId1 = pd.getString("controlId1");
		String controlId2 = pd.getString("controlId2");
		mv.addObject("controlId",controlId);
		mv.addObject("controlId1",controlId1);
		mv.addObject("controlId2",controlId2);
		mv.addObject("mkbh",mkbh);
		mv.setViewName("window/dwxx/window");
		return mv;
	}
	/**
	 * 
	 * @author  wangguanghua
	 * @version  2018-9-13上午10:40:59
	 * 用于部门项目信息统计的单位选择
	 */
	@RequestMapping(value="/bmxmxxdwpage")
	public ModelAndView goBmxmxxDwxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		String controlName = pd.getString("controlName");
		mv.addObject("controlName",controlName);
		mv.setViewName("window/dwxx/windowbmxmxx");
		return mv;
	}
	
	@RequestMapping(value="goDyRwTerr")
	public ModelAndView goCzqxb_frmPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/qxgl/dwryb_frm");
		return mv;
	}
	
	/**
	 * 部门信息弹出窗
	 */
	@RequestMapping(value="/dwpagewqx")
	public ModelAndView goDwxxPagewqx(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("window/dwxx/window2");
		return mv;
	}
	/**
	 * 部门信息弹出窗
	 */
	@RequestMapping(value="/dwpageParent")
	public ModelAndView goDwxxPageParent(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("window/dwxx/windowParent");
		return mv;
	}

	/**
	 * 部门预算申报树
	 */
	@RequestMapping(value="/mainBmyssbTree")
	public ModelAndView goMainBmyssbTreePage(){
		ModelAndView mv = this.getModelAndView();//视图转化器
		String bzid = this.get32UUID();
		mv.addObject("bzid", bzid);
		mv.setViewName("window/commonTree/mainBmyssbTree");//要跳转的jsp页面，window/commonTree/mainDwTree.jsp
		return mv;
	}
	/**
	 * 数据字典树
	 */
	@RequestMapping(value="/mainCbysTree")
	public ModelAndView goMainCbysTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainCbysTree");
		return mv;
	}
	/**
	 * 部门人员信息弹出窗
	 */
	@RequestMapping(value="/rypage")
	public ModelAndView goRyxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		String controlId2 = pd.getString("controlId2");
		String controlId3 = pd.getString("controlId3");
		String mkbh = pd.getString("mkbh");
		String ymbz = pd.getString("ymbz");
		String ryid = pd.getString("ryid");
		String flag = pd.getString("flag");
		mv.addObject("ryid",ryid);
		mv.addObject("ymbz",ymbz);
		mv.addObject("flag",flag);
		mv.addObject("mkbh",mkbh);
		mv.addObject("controlId",controlId);
		mv.addObject("controlId1",controlId1);
		mv.addObject("controlId2",controlId2);
		mv.addObject("controlId3",controlId3);
		if("040901".equals(mkbh)){
			//凭证查询 综合查询  人员弹窗 只展示财务处人员
			mv.setViewName("kjhs/pzxx/pzcxs/cwryList");
		}else{
			mv.setViewName("window/ryxx/window");
		}
		return mv;
	}
	
	/**
	 * 部门人员信息弹出窗
	 */
	@RequestMapping(value="/rypagexm")
	public ModelAndView goRyxxPagexxxm(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String model= Validate.isNullToDefault(pd.getString("model"), "")+"";
		//控件id
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.addObject("model", model);
		mv.setViewName("window/ryxx/window5");
		return mv;
	}
	
	/**
	 * 无权限限制
	 * @return
	 */
	@RequestMapping(value="/rypagewqx")
	public ModelAndView goRyxxPage3(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		String rybh = pd.getString("rybh");
		String szbmm = pd.getString("szbmm");
		mv.addObject("controlId",controlId);
		mv.addObject("ryid",rybh);
		mv.addObject("szbmm",szbmm);
		mv.setViewName("window/ryxx/window4");
		return mv;
	}
	/**
	 * 部门人员信息弹出窗
	 */
	@RequestMapping(value="/rypage2")
	public ModelAndView goRyxxPage2(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("window/ryxx/window2");
		return mv;
	}
	/**
	 * 部门人员信息弹出窗
	 */
	@RequestMapping(value="/allrypage")
	public ModelAndView goAllryxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		String type = pd.getString("type");
		mv.addObject("type",type);
		mv.addObject("controlId",controlId);
		mv.setViewName("window/ryxx/allwindow");
		return mv;
	}
	/**
	 * 部门人员信息弹出窗
	 */
	@RequestMapping(value="/rypageParent")
	public ModelAndView goRyxxPageParent(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("window/ryxx/windowParent");
		return mv;
	}
	/**
	 * 数据字典字典信息弹出窗
	 */
	@RequestMapping(value="/zdpage")
	public ModelAndView goZdxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//控件id
		String controlId = pd.getString("controlId");
		mv.addObject("controlId",controlId);
		mv.setViewName("fzgn/sjzd/window");
		return mv;
	}
	
	/**
	 * 通用单位树页面
	 */
	@RequestMapping(value="/commonDwTreePage")
	public ModelAndView goCommonDwTreePage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		mv.addObject("sql", pd.getString("sql"));
		mv.addObject("mkbh", pd.getString("mkbh"));
		mv.addObject("ztbz", pd.getString("ztbz"));
		mv.addObject("value", pd.getString("value"));
		mv.addObject("zhcx", pd.getString("zhcx"));
		mv.addObject("bz", pd.getString("bz"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		if(MenuFlag.ZCCX_ZCFL_BZXX.equals(pd.getString("mkbh"))){
			mv.setViewName("window/commonTree/mainDdTree");
		}else if(MenuFlag.ZCCX_ZCFL_SYR.equals(pd.getString("mkbh"))){
			mv.setViewName("window/commonTree/mainDwRyTree");
		}
		else{
			mv.setViewName("window/commonTree/mainDwTree2");
		}
		
		return mv;
	}
	/**
	 * 单位机构树
	 */
	@RequestMapping(value="/mainDwTree")
	public ModelAndView goMainDwTreePage(){
		ModelAndView mv = this.getModelAndView();//视图转化器
		mv.setViewName("window/commonTree/mainDwTree");//要跳转的jsp页面，window/commonTree/mainDwTree.jsp
		return mv;
	} 
	
	/**
	 * 跳转数据字典维护左侧树
	 * @return
	 */
	@RequestMapping(value="/mainDmbTree")
	public ModelAndView mainDmbTree(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("window/commonTree/mainSjzdTree");
		return mv;
	}
	/**
	 * 采购目录设置树
	 */
	@RequestMapping(value="/mainCgTree")
	public ModelAndView goMainCgTreePage(){
		ModelAndView mv = this.getModelAndView();//视图转化器
		mv.setViewName("window/commonTree/mainCgTree");
		return mv;
	}
	/**
	 * 项目查询树
	 */
	@RequestMapping(value="/mainXmcxTree")
	public ModelAndView goMainXmcxTreePage(){
		ModelAndView mv = this.getModelAndView();//视图转化器
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		mv.setViewName("window/commonTree/mainXmcxTree");
		return mv;
	}
	/**
	 * 项目查询树
	 */
	@RequestMapping(value="/mainXmcxTjTree")
	public ModelAndView goMainXmcxTjTreePage(){
		ModelAndView mv = this.getModelAndView();//视图转化器
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		mv.setViewName("window/commonTree/mainXmcxTjTree");
		return mv;
	}
	/**
	 * 项目分类树
	 */
	@RequestMapping(value="/mainXmflTree")
	public ModelAndView goMainXmflTreePage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("window/commonTree/mainXmflTree");
		return mv;
	}
	/**
	 * 费用科目对应设置树
	 */
	@RequestMapping(value="/mainFykmdyszTree")
	public ModelAndView goMainFykmdyszTreePage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("window/commonTree/mainFykmdyszTree");
		return mv;
	}
	/**
	 * 存放地点树
	 */
	@RequestMapping(value="/mainDdTree")
	public ModelAndView goMainDdTreePage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("window/commonTree/mainDdTree");
		return mv;
	}
	/**
	 * 数据字典树
	 */
	@RequestMapping(value="/mainSjzdTree")
	public ModelAndView goMainSjzdTree(){
		ModelAndView mv = this.getModelAndView();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainSjzdTree");
		return mv;
	}
	/**
	 * 数据字典树
	 */
	@RequestMapping(value="/mainCbdxTree")
	public ModelAndView goMainCbdxTree(){
		ModelAndView mv = this.getModelAndView();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainCbdxTree");
		return mv;
	}
	/**
	 * 收入科目弹窗
	 */
	@RequestMapping(value="/multiSrkmList")
	@ResponseBody
	public Object getSrkmWindow(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select s.xmlxbh,s.srkmbh,k.kmmc from  Cw_xmsrb s left join cw_kjkmsz k on k.guid = s.srkmbh  where 1=1  )k ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("XMLXBH");//主键
		//设置WHERE条件
		pageList.setStrWhere("");
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/*
	 * 跳转专业方向页面
	 */
	@RequestMapping(value="/mainZyfx")
	public ModelAndView mainZyfx(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/ZyfxxxList");
		return mv;
	}
	/*
	 * 专业方向查值
	 */
	@RequestMapping(value="/goZyfxPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" (select d.dmxh guid,d.zl,d.dm,d.jb,d.mc from gx_sys_dmb d where d.zl='zyfx') k");
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
	 *支出科目list查询 
	 */
	@RequestMapping(value="/multiZckmList")
	@ResponseBody
	public Object getZckmWindow(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select s.xmlxbh,s.zckmbh,k.kmmc from  Cw_xmzcb s left join cw_kjkmsz k on k.guid = s.zckmbh  where 1=1  )k ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("XMLXBH");//主键
		//设置WHERE条件
		pageList.setStrWhere("");
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 *项目经济分类list信息 
	 */
	@RequestMapping(value="/multijjflkmList")
	@ResponseBody
	public Object getjjflkmWindow(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select s.xmlxbh, s.jjfl, k.kmmc from Cw_xmjjflb s left join cw_jjkmb k on k.kmbh = s.jjfl where 1 = 1  )k ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("XMLXBH");//主键
		//设置WHERE条件
		pageList.setStrWhere("");
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 会计科目设置树
	 */
	@RequestMapping(value="/mainKmszTree")
	public ModelAndView goMainKmszTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainKmszTree");
		return mv;
	}
	/**
	 * 收入科目页面
	 */
	@RequestMapping(value="/goSrkmPage")
	public ModelAndView goDdbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/kmsz/srkm_list");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 *支出科目 
	 */
	@RequestMapping(value="/goZckmPage")
	public ModelAndView goZckmPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/kmsz/zckm_list");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 *经济分类科目 
	 */
	@RequestMapping(value="/goJjflkmPage")
	public ModelAndView goJjflkmPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/kmsz/jjflkm_list");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 会计科目设置树
	 */
	@RequestMapping(value="/mainKjkmszTree")
	public ModelAndView goMainKjkmszTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainKjkmszTree");
		return mv;
	}
	
	/**
	 * 会计科目设置树
	 */
	@RequestMapping(value="/mainKjkmszTree123")
	public ModelAndView goMainKjkmszTree1(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainKjkmszTree1");
		return mv;
	}
	/**
	 * 报销类别树
	 */
	@RequestMapping(value="/mainBxlbTree")
	public ModelAndView goMainBxlbTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainBxlbTree");
		return mv;
	}
	/**
	 * 功能科目设置树
	 */
	@RequestMapping(value="/mainGnkmTree")
	public ModelAndView mainGnszTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainGnkmTree");
		return mv;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/mainGnkmszTree")
	public ModelAndView mainGnkmszTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainGnkmszTree");
		return mv;
	}
	/**
	 * 经济科目设置树
	 */
	@RequestMapping(value="/mainJjkmTree")
	public ModelAndView mainJjkmTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainJjkmTree");
		return mv;
	}
	/**
	 * 经济科目弹窗
	 */
	@RequestMapping(value="/mainJjkmWindowTree")
	public ModelAndView mainJjkmWindowTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//页面路径
		
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		mv.addObject("controlId",pd.getString("controlId"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.setViewName("window/commonTree/mainJjkmWindowTree");
		return mv;
	}
	//往来单位列表信息
	@RequestMapping(value="getWldwPageList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getPageList() {
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" K.GUID,K.WLBH,K.DWMC,K.DWJC,(select B.MC from GX_SYS_DMB B where B.Zl='wldwlx' and B.DM = K.DWLX) as DWLX,K.DWDZ,K.LXR,K.SH,K.BGDH,K.CZ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName("CW_WLDWB K");
		//设置表主键名
		pageList.setKeyId("guid");
		PageData pd = this.getPageData();
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findWindowList(pd, pageList, "WLDW");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 单位信息列表
	 * @return
	 */
	@RequestMapping("/dwxx")
	@ResponseBody
	public Object getDwxxWindow(){
		String userId = LUser.getRybh();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String ejdw = pd.getString("ejdw");
		String ischeck = Validate.isNullToDefault(pd.getString("ischeck"), "")+"";
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("k.dwbh,k.mc,k.jc,k.dz,to_char(k.jlrq,'yyyy-mm-dd') jlrq,");
		sqltext.append("k.fgld,decode(k.dwzt,'0','禁用','1','正常') dwzt,k.dwjc,k.mjbz,k.pxxh,k.bmh,");
		sqltext.append("k.bmsx,k.bz,k.sysbz,k.sysjb,k.sysgs,k.syslb,k.sysmj,k.jlnf,k.syslx,");
		sqltext.append("(select mc from gx_sys_dmb m where zl='"+Constant.DWXZ+"' and m.dm = k.dwxz) dwxz,");
		sqltext.append("(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh=k.dwld) dwld,");
		sqltext.append("(case k.sjdw when '000000' then '无' else (select '('||c.bmh||')'||to_char(c.mc) from gx_sys_dwb c where c.dwbh=k.sjdw) end) sjdw");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("dwbh");//主键
		sqltext = new StringBuffer();
		String from = pd.getString("from");
		if(Validate.noNull(dwbh)){
			//点击左侧单位树的where条件
			sqltext.append(" and k.dwbh in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + dwbh.replace("D", "") + "')");
			sqltext.append(" and dwbh != '"+ dwbh+"' ");
		}
		
		if("CD".equals(from)){//CD是不限制管理权限
			
		}
		else{
			//当前登录人管理单位权限
			sqltext.append(pageService.getQxsql(userId, "k.dwbh", "D"));
			if("ED".equals(from)){
				sqltext.append(" and k.dwjc = '" + Constant.DWJC_EJ + "' ");
			}
			else if("wxjfhb".equals(from)){
				sqltext.append(" and k.dwjc = '" + Constant.DWJC_EJ + "' and k.dwbh not in (select sydw from zc_wxjfhb where nd = '"+Constant.MR_YEAR()+"') ");
			}
		}
		if("ejdw".equals(ejdw)) {
			sqltext.append(" and and k.sfxy = '1' ");
		}
		sqltext.append(" and nvl(dwzt,'1') = '1' ");
	    
	    pageList.setStrWhere(sqltext.toString());
	    
		pageList.setTableName("gx_sys_dwb k");//表名
		pageList.setOrderBy("pxxh");
	    pageList = pageService.findWindowList(pd,pageList,"D");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 单位信息弹窗
	 * @return
	 */
	@RequestMapping("/multiSelDwxx")
	@ResponseBody
	public Object getMultiSelDwxxWindow(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.DWBH,K.MC,K.JC,K.DZ,TO_CHAR(K.JLRQ,'yyyy-MM-dd') JLRQ, ");
		sqltext.append(" K.FGLD,DECODE(K.DWZT,'0','禁用','1','正常') DWZT,K.DWJC,K.MJBZ,K.PXXH,K.BMH,");
		sqltext.append(" K.BMSX,K.BZ,K.SYSBZ,K.SYSJB,K.SYSGS,K.SYSLB,K.SYSMJ,K.JLNF,K.SYSLX,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.DWXZ+"' AND M.DM = K.DWXZ) DWXZ,");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=K.DWLD) DWLD,");
		sqltext.append(" (SELECT '('||BMH||')'||MC FROM GX_SYS_DWB C WHERE C.DWBH=K.SJDW) SJDW ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("dwbh");//主键
		pageList.setStrWhere(" and nvl(dwzt,'1')='1' ");//where条件
		pageList.setTableName("GX_SYS_DWB K");//表名
	    pageList = pageService.findWindowList(pd,pageList,"D");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 人员信息弹窗
	 * @return
	 */
	@RequestMapping("/ryxx")
	@ResponseBody
	public Object getRyxxWindow(){
		String userId = LUser.getRybh();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String mkbh = pd.getString("mkbh");
		if(Validate.isNull(dwbh)){
			dwbh=pd.getString("treedwbh");
		}
		String jsbh = pd.getString("jsbh");
		String flag = pd.getString("flag");
		String flags = pd.getString("flags");
		String zbid = pd.getString("zbid");
//		String type = pd.getString("type");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" a.guid,a.rybh rybh,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.dwbh) dwmc,");
		sqltext.append(" a.xm,(select mc from gx_sys_dmb where zl='"+Constant.SEX+"' and dm=a.xb) xb,a.xb xbdm,to_char(a.csrq,'yyyy-MM-dd') csrq,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.WHCD+"' and dm=a.whcd) whcd,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.SXZY+"' and dm=a.sxzy) sxzy,");
		sqltext.append(" to_char(a.byrq,'yyyy-MM-dd') byrq,to_char(A.gzrq,'yyyy-MM-dd') gzrq,a.sysgl,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYZC+"' and dm=a.zyzc) zyzc,a.zyzc zyzcdm,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYGZ+"' and dm=a.zygz) zygz,");
		sqltext.append(" to_char(a.drrq,'yyyy-mm-dd') drrq,to_char(a.txrq,'yyyy-mm-dd') txrq,a.bz,");
		sqltext.append(" (case a.ryzt when '1' then '正常' when '0' then '禁用' end) ryzt,a.zzbz,a.pxxh,a.sfzh,");
		sqltext.append(" a.rygh,a.rownums,a.url,a.cssclass,a.lxdh,a.qq,a.mail,a.zzzt,a.mm ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("rybh");
		//人员信息弹窗中去掉000000、单位权限下的人员
		sqltext = new StringBuffer();
		sqltext.append(" and a.rybh <> '"+SystemSet.AdminRybh()+"' and ryzt = '1' and zzjzglx='1' and a.dwbh not like '00000%'");
		if(StateManager.BDXM_SYRBD.equals(flag)){//使用人变动时没有左侧树，只选择本单位的
			sqltext.append(" and dwbh = '" + LUser.getDwbh() + "' ");
		}else{
			if(Validate.noNull(dwbh)){
				//点击左侧单位树的where条件
				sqltext.append(" and a.dwbh in (select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh = '" + dwbh.replace("D", "") + "')");
			}else{
				if("1".equals(flags)){
					sqltext.append(pageList.getStrWhere());
				}else{
					if("All".equals(flag)){
						sqltext.append(pageList.getStrWhere());
					}else{
						//当前登录人管理单位权限
						sqltext.append(pageService.getQxsql(userId, "a.dwbh", "D"));
					}
				}
			}
			
			if(Validate.noNull(jsbh)){
				String sql = "select rybh from zc_sys_jsryb where jsbh='"+jsbh+"'";
				List list = db.queryForList(sql);
				String rybh = "";
				if(list.size()>0){
					StringBuffer value = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						Map map = (Map)list.get(i);
						value.append(map.get("RYBH")+"','");
					}
					rybh = value.substring(0, value.length()-3);
				}
				if(Validate.noNull(rybh)){
					sqltext.append(" and rybh not in ('"+rybh+"') ");
				}
			}
		}
		if(Validate.noNull(zbid)){
			sqltext.append(" and rybh in (SELECT fzr FROM XMINFOS WHERE guid IN(SELECT xmguid FROM cw_rcbxmxb WHERE zbid='"+zbid+"'))");
		}
		if("040901".equals(mkbh)) {
			sqltext.append(" and a.dwbh='110' ");
		}
		pageList.setStrWhere(sqltext.toString());

		pageList.setTableName("gx_sys_ryb a");
		pageList = pageService.findWindowList(pd,pageList,"R");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 人员信息弹窗（出差报销_明细，选择老师或学生）
	 * @return
	 */
	@RequestMapping("/ryxxST")
	@ResponseBody
	public Object getRyxxSTWindow(){
		String userId = LUser.getRybh();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		if(Validate.isNull(dwbh)){
			dwbh=pd.getString("treedwbh");
		}
		String jsbh = pd.getString("jsbh");
		String flag = pd.getString("flag");
		String zbid = pd.getString("zbid");
		String type = pd.getString("type");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" a.guid,a.rybh rybh,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.dwbh) dwmc,");
		sqltext.append(" a.xm,(select mc from gx_sys_dmb where zl='"+Constant.SEX+"' and dm=a.xb) xb,a.xb xbdm,to_char(a.csrq,'yyyy-MM-dd') csrq,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.WHCD+"' and dm=a.whcd) whcd,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.SXZY+"' and dm=a.sxzy) sxzy,");
		sqltext.append(" to_char(a.byrq,'yyyy-MM-dd') byrq,to_char(A.gzrq,'yyyy-MM-dd') gzrq,a.sysgl,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYZC+"' and dm=a.zyzc) zyzc,a.zyzc zyzcdm,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYGZ+"' and dm=a.zygz) zygz,");
		sqltext.append(" to_char(a.drrq,'yyyy-mm-dd') drrq,to_char(a.txrq,'yyyy-mm-dd') txrq,a.bz,");
		sqltext.append(" (case a.ryzt when '1' then '正常' when '0' then '禁用' end) ryzt,a.zzbz,a.pxxh,a.sfzh,");
		sqltext.append(" a.rygh,a.rownums,a.url,a.cssclass,a.lxdh,a.qq,a.mail,a.zzzt,a.mm ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("rybh");
		//人员信息弹窗中去掉000000、单位权限下的人员
		sqltext = new StringBuffer();
		sqltext.append(" and a.rybh <> '"+SystemSet.AdminRybh()+"' and ryzt = '1' and type ='"+type+"' and zzjzglx='1'");
		if(StateManager.BDXM_SYRBD.equals(flag)){//使用人变动时没有左侧树，只选择本单位的
			sqltext.append(" and dwbh = '" + LUser.getDwbh() + "' ");
		}else{
			if(Validate.noNull(dwbh)){
				//点击左侧单位树的where条件
				sqltext.append(" and a.dwbh in (select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh = '" + dwbh.replace("D", "") + "')");
			}else{
				if("All".equals(flag)){
					sqltext.append(pageList.getStrWhere());
				}else{
					//当前登录人管理单位权限
//					sqltext.append(pageService.getQxsql(userId, "a.dwbh", "D"));
					sqltext.append(pageList.getStrWhere());
				}
			}
			
			if(Validate.noNull(jsbh)){
				String sql = "select rybh from zc_sys_jsryb where jsbh='"+jsbh+"'";
				List list = db.queryForList(sql);
				String rybh = "";
				if(list.size()>0){
					StringBuffer value = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						Map map = (Map)list.get(i);
						value.append(map.get("RYBH")+"','");
					}
					rybh = value.substring(0, value.length()-3);
				}
				if(Validate.noNull(rybh)){
					sqltext.append(" and rybh not in ('"+rybh+"') ");
				}
			}
		}
		if(Validate.noNull(zbid)){
			sqltext.append(" and rybh in (SELECT fzr FROM XMINFOS WHERE guid IN(SELECT xmguid FROM cw_rcbxmxb WHERE zbid='"+zbid+"'))");
		}
		pageList.setStrWhere(sqltext.toString());

		pageList.setTableName("gx_sys_ryb a");
		pageList = pageService.findWindowList(pd,pageList,"R");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 地点信息弹窗
	 * @return
	 */
	@RequestMapping("/ddxx")
	@ResponseBody
	public Object getDdbWindow(){
		PageData pd = this.getPageData();
		String ddbh = pd.getString("ddbh");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" D.DDBH,D.MC,D.SJDD,(SELECT '('||A.DDH||')'||A.MC FROM ZC_SYS_DDB A WHERE A.DDBH=D.SJDD) SJDDMC,D.DWBH,");
		sqltext.append(" (SELECT '('||A.DWBH||')'||A.MC FROM GX_SYS_DWB A WHERE A.DWBH=D.DWBH) DWMC,");
		sqltext.append(" case D.DDZT when '1' then '正常' when '0' then '禁用' end DDZT,D.DDJC,D.PXXH,D.DDH");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("ddbh");//主键
		if(Validate.noNull(ddbh)){
			pageList.setStrWhere(" AND d.ddbh IN(SELECT ddbh FROM ZC_SYS_DDB CONNECT BY PRIOR ddbh=sjdd START WITH ddbh='" + ddbh+ "')");
		}
		pageList.setStrWhere(pageList.getStrWhere()+" and nvl(ddzt,'1')='1' ");
		pageList.setTableName("ZC_SYS_DDB D");
		pageList.setHj1("");//合计
	    pageList = pageService.findWindowList(pd,pageList,"P");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 资产分类信息弹窗
	 * @return
	 */
	@RequestMapping("/flxx")
	@ResponseBody
	public Object getFlxxWindow(){
		String flhs = glqxbDao.getFlhByRybh(LUser.getRybh());
		PageData pd = this.getPageData();
		String flbm = pd.getString("flbm");
		String dl = pd.getString("dl");
		String flh = pd.getString("flh");
		String lyr = pd.getString("lyr");
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("T.bzdm bzdm, T.FLH FLH, T.flMC FLMC,T.dmjc dmjc,T.ffldm ffldm, (select '('||flh||')'||flmc from zc_flb_czbn c where c.flh = t.ffldm) czflmc, (select bzdm from zc_flb_czbn c where c.flh = t.ffldm) czbzdm,T.Fflmc Fflmc,T.SFMJ SFMJ ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("bzdm");
		Map map = xtbService.getXtcs();
		if(Validate.isNull(lyr)&&"1".equals(map.get("SFMJ"))){
			if(Validate.noNull(flbm)){
				pageList.setStrWhere(pageList.getStrWhere()+"AND SFMJ='1' AND T.FLH like '" + flbm + "%' ");
			}else{
				pageList.setStrWhere(pageList.getStrWhere()+"AND SFMJ='1'");
			}
		}else{
			if(Validate.noNull(flbm)){
				if("undefined".equals(flbm)){
					flbm="";
				}
				pageList.setStrWhere(pageList.getStrWhere()+" AND T.FLH like '" + flbm + "%' ");
			}
		}
		if(Validate.noNull(dl)){
			pageList.setStrWhere(pageList.getStrWhere()+" AND T.FLH like '" + dl + "%' ");
		}
		if(Validate.noNull(treesearch)){
			pageList.setStrWhere(pageList.getStrWhere()+" and (t.flh like '"+treesearch+"%' or t.flmc like '%"+treesearch+"%')");
		}else{
			pageList.setStrWhere(pageList.getStrWhere());
		}
		//资产分类权限
		if(flhs.equals("00','")){
			pageList.setStrWhere(pageList.getStrWhere()+"");
		}else{
			pageList.setStrWhere(pageList.getStrWhere()+" and substr (flh,0,2) in ('"+flhs+"')");
		}
		if(Validate.noNull(flh)){
			String ss=db.queryForSingleValue("select substr('"+WindowUtil.getText(flh)+"',1,to_number((select dmjc from zc_flb_jyb t where t.flh='"+WindowUtil.getText(flh)+"' group by dmjc))*2) from dual");
			pageList.setStrWhere(pageList.getStrWhere()+"AND T.FLH like '" +ss+ "%' ");
		}
		pageList.setTableName("zc_flb_jyb T");
		pageList = pageService.findWindowList(pd,pageList,"F");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 资产分类信息弹窗，常用分类信息
	 * @return
	 */
	@RequestMapping("/flxz")
	@ResponseBody
	public Object getFlxzWindow(){
		PageData pd = this.getPageData();
		String lyr = pd.getString("lyr");
		String flh = pd.getString("flh");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("*");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("id");
		if("undefined".equals(flh)){
			flh="";
		}
		if(Validate.noNull(flh)){
			pageList.setStrWhere(pageList.getStrWhere()+" AND FLH ='"+flh+"' ");
		}
		pageList.setTableName("(select*from (select sys_guid() id, k.flh,k.yqbh as flmc,'1' as dmjc,(select '(' || c.flh || ')' || c.flmc from zc_flb_czbn c where k.gbmid = c.bzdm) as czflmc,k.gbmid as czbzdm from zc_yshd k where jzrlx ='"+("lyr".equals(lyr)?"0":"1")+"'" + pageService.getQxsql(LUser.getRybh(), "k.flh", "F") + " group by k.flh,k.yqbh,k.gbmid order by count(*) desc) where rownum <=21 )T");
		PageListUtil.setPgxxList(pd,pageList,"F");
		List list = pageService.getPageList(pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getRecordCnt()+"",pageList.getRecordCnt()+"",gson.toJson(list));
		return pageInfo.toJson();
	}
	/**
	 * 财政分类信息弹窗
	 * @return
	 */
	@RequestMapping("/czfl")
	@ResponseBody
	public Object getCzflWindow(){
		PageData pd = this.getPageData();
		String bzdm = pd.getString("bzdm");
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" T.BZDM BZDM,T.FLH FLH,T.FLMC MC,'('||T.FLH||')'||T.FLMC FLMC,T.SJDM SJDW,'('||T.FLH||')'||T.FLMC CZFLMC");//('('||T.FLH||')'||T.FLMC)CZFLMC
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("bzdm");
		if(Validate.noNull(bzdm) && !"00000000".equals(bzdm)){
			pageList.setStrWhere(" start with bzdm = '" + bzdm + "' connect by prior bzdm = sjdm ");
		}else{
			pageList.setStrWhere("");
		}
		if(Validate.noNull(treesearch)){
			pageList.setStrWhere(pageList.getStrWhere()+" and (t.flh like '"+treesearch+"%' or t.flmc like '%"+treesearch+"%')");
		}else{
			pageList.setStrWhere(pageList.getStrWhere());
		}
		pageList.setTableName("zc_flb_czbn T");
		pageList = pageService.findWindowList(pd,pageList,"F");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 学科弹窗
	 * @return
	 */
	@RequestMapping(value="/xkxx")
	@ResponseBody
	public Object getxkWindow(){
		PageData pd = this.getPageData();
		String bzdm = pd.getString("bzdm");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" t.dm dm,'('||t.dm||')'||t.mc xkmc,t.mc mc");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("dmxh");
		pageList.setStrWhere(" and zl = '" + Constant.XKML + "'");
		if(Validate.noNull(bzdm)){
			pageList.setStrWhere(pageList.getStrWhere()+" and (dm like '"+bzdm+"%' or mc like '"+bzdm+"%')");
		}else{
			pageList.setStrWhere(pageList.getStrWhere());
		}
		pageList.setTableName("gx_sys_dmb T");
		pageList = pageService.findWindowList(pd,pageList,"T");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 国别弹窗
	 * @return
	 */
	@RequestMapping(value="/gbxx")
	@ResponseBody
	public Object getgbWindow(){
		PageData pd = this.getPageData();
		String bzdm = pd.getString("bzdm");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" t.dm dm,'('||t.dm||')'||t.mc xkmc,t.mc mc");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("dmxh");
		if(Validate.noNull(bzdm)){
			pageList.setStrWhere(" and (T.DM like '%" + bzdm + "%' or t.mc like '%" + bzdm + "%') ");
		}else{
			pageList.setStrWhere("");
		}
		pageList.setStrWhere(pageList.getStrWhere()+" and zl = '" + Constant.GB + "' ");
		pageList.setTableName("gx_sys_dmb T");
		pageList = pageService.findWindowList(pd,pageList,"T");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 代码表弹窗
	 * @return
	 */
	@RequestMapping(value="/multiDmbList")
	@ResponseBody
	public Object getSyfxWindow(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("t.dmxh,t.dm,'('||t.dm||')'||t.mc dmmc,t.mc");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("dmxh");
		pageList.setStrWhere(" and zl = '" + pd.getString("zl") + "' ");
		pageList.setTableName(" gx_sys_dmb T ");
		pageList = pageService.findWindowList(pd,pageList,"T");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 维修资产信息弹窗
	 */
	@RequestMapping(value="/wxzcxx")
	@ResponseBody
	public Object getWxZcXxWindow(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();
		String dwbh = pd.getString("dwbh");
		String flag = pd.getString("flag");
		String userId = LUser.getRybh();
		sqltext.append("t.yqbh,t.flh,t.flmc,t.yqmc,decode(nvl(t.dj,0),0,'0.00',(to_char(round(t.dj,2),'fm99999999999990.00'))) dj,decode(nvl(t.zzj,0),0,'0.00',(to_char(round(t.zzj,2),'fm99999999999990.00'))) zzj,to_char(t.gzrq,'yyyy-mm-dd') gzrq,t.gg,t.xh,to_char(t.bxjzrq,'yyyy-mm-dd') bxjzrq,");
		sqltext.append("(select '('||a.dwbh||')'||a.mc from gx_sys_dwb a where a.dwbh = t.sydw) sydw,");
		sqltext.append("(select '('||a.ddh||')'||a.mc from zc_sys_ddb a where a.ddbh = t.bzxx) bzxx,");
		sqltext.append("(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh = t.syr) syr,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.SYFX+"' and dm = t.syfx) syfx,");
		sqltext.append("(select mc from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=t.xz) xz");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("yqbh");
		
		sqltext = new StringBuffer();
		//维修资产,只能选择通用设备,其他分类都不行
		sqltext.append(" and substr(t.flh,1,2) in ('03','04','05','06','07','08','12','14') and substr(t.flh,1,4) not in ('0413','0571','0711') ");
		sqltext.append(" and t.ztbz = '" + StateManager.ZCJZ_CW_TG + "'");
		sqltext.append(" and nvl(t.bdzt,'0')='0'");
		sqltext.append(" and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "')");
		sqltext.append(" and t.yqbh not in (select yqbh from zc_wxsqmxb x left join zc_wxsqb s on s.reportid = x.sqbh where s.ztbz <> '" + StateManager.WXSQ_TG + "') ");
		if(Validate.noNull(dwbh)){
			//点击左侧单位树的where条件
			sqltext.append(" and t.sydw in (select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh = '" + dwbh.replace("D", "") + "')");
		}else if(!"All".equals(flag)){
			sqltext.append(pageService.getQxsql(userId, "t.sydw", "D"));
		}
		pageList.setStrWhere(sqltext.toString());
		pageList.setTableName("zc_zjb t");
		pageList = pageService.findWindowList(pd,pageList,"W");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 维修商信息弹窗
	 * @return
	 */
	@RequestMapping("/wxsxx")
	@ResponseBody
	public Object getWxsWindow(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" D.GSBH,'('||D.GSBH||')'||D.MC mc,D.FR,D.PHONE,D.ADDRESS,D.BUSS,D.ZJR,D.ZJSJ,D.LXR");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("gsbh");//主键
		pageList.setStrWhere("");//where条件
		pageList.setTableName("zc_wxsinfor D");
		pageList.setHj1("");//合计
	    pageList = pageService.findWindowList(pd,pageList,"WXS");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 我的业务流水信息弹窗
	 * @return
	 */
	@RequestMapping("/ywls")
	@ResponseBody
	public Object getYwlsWindow(){
		PageData pd = this.getPageData();
		String month = pd.getString("month");
		
		//排序列名
		String orderColumn = Validate.isNullToDefault(pd.getString("order[0][column]"), "0") + "";
		orderColumn = pd.getString("columns["+orderColumn+"][data]");
		//排序方式
		String orderDir = Validate.isNullToDefault(pd.getString("order[0][dir]"), "asc")+"";
		String order = "";
		if(Validate.noNull(orderColumn)){
			order = " order by "+orderColumn+" "+orderDir;
		}
		
		Map<String,String> parInMap = new HashMap();
		parInMap.put("p_rybh", LUser.getRybh());
		parInMap.put("p_month", month);
		parInMap.put("p_where", CommonUtils.StringFilter(pd.getString("search[value]")));
		parInMap.put("p_pagestart", pd.getString("start"));
		parInMap.put("p_pageend", (Integer.parseInt(pd.getString("length"))+Integer.parseInt(pd.getString("start"))) + "");
		parInMap.put("p_order", order);
		Set<Entry<String,String>> parInSet = parInMap.entrySet();
		parInMap = null;
		
		Map<String,Integer> parOutMap = new HashMap();
		parOutMap.put("my_num",OracleTypes.INTEGER);
		parOutMap.put("my_cursor", OracleTypes.CURSOR);
		Set<Entry<String,Integer>> parOutSet = parOutMap.entrySet();
		parOutMap = null;
		
		Map map = new HashMap();
		try{
			map = db.queryForMapByProcedure("pro_getYwls", parInSet, parOutSet);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
		String num = "0";
		if(map.containsKey("my_num")){
			num = map.get("my_num").toString();
		}
		
		List list = new ArrayList();
		if(map.containsKey("my_cursor")){
			list = (List)map.get("my_cursor");
		}
		
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",num,num,gson.toJson(list));
		return pageInfo.toJson();
	}

	/**
	 * 维修申请弹窗
	 * @return
	 */
	@RequestMapping("/wxsq")
	@ResponseBody
	public Object getWxsqWindow(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" w.reportid,w.replycompany,w.replycompanymc,w.replyperson,w.replypersonmc,w.replytime,w.questionremark,w.replycontent,decode(nvl(w.aboutmoney,0),0,'0.00',(to_char(round(w.aboutmoney,2),'fm99999999999990.00'))) aboutmoney,w.remark,w.ztbz,w.ztbzmc,w.replystatus,w.flag,w.wxsadvice,w.wxsbh,w.wxsmc,w.fjinfo,x.address,x.phone ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("reportid");//主键
		pageList.setTableName("(select reportid,replycompany,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = w.replycompany) replycompanymc,replyperson,(select '('||rygh||')'||xm from gx_sys_ryb r where r.rybh = w.replyperson) replypersonmc,to_char(replytime,'yyyy-mm-dd') replytime,questionremark,replycontent,aboutmoney,remark,ztbz," + StateManager.getZtmcSql(StateManager.CZLX_ZCWX_WXSQ, "w.ztbz") + " ztbzmc,replystatus,flag,wxsadvice,wxsbh,wxsmc,fjinfo from zc_wxsqb w where ztbz = '" + StateManager.WXSQ_TG + "'  and reportid not in (select reportid from zc_wxbgb)) w left join zc_wxsinfor x on w.wxsbh = x.gsbh ");
		pageList.setStrWhere(pageService.getQxsql(LUser.getRybh(), "replycompany", "D"));
		if(Validate.noNull(dwbh)){
			pageList.setStrWhere(Validate.isNullToDefault(pageList.getStrWhere(),"")+" and w.replycompany in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + dwbh + "' )");
		}
		pageList.setHj1("");//合计
	    pageList = pageService.findWindowList(pd,pageList,"WXSQ");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}

	/**
	 * 首页待办事项更多弹窗
	 * @return
	 */
	@RequestMapping("/dbsx")
	@ResponseBody
	public Object getDbsxWindow(){
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		pageList.setSqlText(" k.dbh,k.mc,k.tjr,k.tzlj,k.mkbh,k.sqmkbh ");
		pageList.setKeyId("gid");//主键
		pageList.setTableName(" (select k.gid,k.dbh,k.sqmkbh,'等待'||mkmc mc,tjrxm tjr,(select url from zc_sys_mkb m where m.mkbh = k.mkbh) tzlj,k.mkbh from zc_sys_shcshb k where k.rybh = '" + LUser.getRybh() + "' and sfdqjd = '1') k ");
		pageList.setStrWhere("");//where条件
		pageList.setHj1("");//合计
	    pageList = pageService.findWindowList(pd,pageList,"DBSX");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 首页待办事项更多弹窗
	 * @return
	 */
	@RequestMapping("/dtjxx")
	@ResponseBody
	public Object getDtjxxWindow(){
		ModelAndView mv = this.getModelAndView();
		String rybh = LUser.getRybh();		
		List list = db.getCgls(rybh);
		mv.addObject("list", list);
		mv.setViewName("window/dtjsx/dtj_list");   
		return mv;
	}
	
	
	/**
	 * 首页驳回更多弹窗
	 * @return
	 */
	@RequestMapping("/bhxx")
	@ResponseBody
	public Object getBhxxWindow(){
		ModelAndView mv = this.getModelAndView();
		String rybh = LUser.getRybh();		
		List list = db.getBhls(rybh);
		mv.addObject("list", list);
		mv.setViewName("window/dtjsx/bh_list");   
		return mv;
	}
	
	/**
	 * 首页--通知公告--更多
	 */
	@RequestMapping(value="/getTzggList")
	@ResponseBody
	public Object getTzggList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" T.GID,T.TITLE,TO_CHAR(T.FBSJ,'yyyy-MM-dd') FBSJ,T.FBR FBR,DECODE(T.SFZS,'1','是','0','否')AS SFZS,T.FBRMC ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GID");//主键
		pageList.setTableName("(select K.GID,K.TITLE,K.FBSJ,K.FBR FBR,K.SFZS,(SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=K.FBR) FBRMC  from ZC_SYS_XTXX K where k.sfzs='1' ) T");//表名
		pageList.setHj1("");//合计
		pageList = pageService.findWindowList(pd,pageList,"TZGG");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取通知公告编辑页面
	 */
	@RequestMapping(value="/goTzggEdit")
	public ModelAndView goTzggEdit(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		Map map = fbxxService.getObjectById(pd.getString("gid"));
		mv.addObject("Content",map.get("XX"));
		mv.addObject("xtxx", map);
		mv.setViewName("window/tzgg/tzggEdit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 获取系统消息编辑页面
	 */
	@RequestMapping(value="/goTzxxEdit")
	public ModelAndView goTzxxEdit(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		Map map = tzxxService.getObjectById(pd.getString("gid"));
		mv.addObject("Content",map.get("XX"));
		mv.addObject("xtxx", map);
		mv.setViewName("window/tzgg/tzxxEdit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 获取二级单位人员信息（资产调拨分单位内和单位间，用到）
	 * @return
	 */
	@RequestMapping("/ejdwRyxx")
	@ResponseBody
	public Object ejdwRyxx(){
		String userId = LUser.getRybh();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");//选择的单位树id
		String ejdw = pd.getString("ejdw");//资产的二级单位
		String flag = pd.getString("flag");//区分单位内和单位间的标志
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" A.rybh,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.dwbh) dwbh,");
		sqltext.append(" A.xm,(select mc from gx_sys_dmb where zl='"+Constant.SEX+"' and dm=A.xb) xb,A.xb xbdm,to_char(A.csrq,'yyyy-MM-dd') csrq,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.WHCD+"' and dm=a.whcd) whcd,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.SXZY+"' and dm=a.sxzy) sxzy,");
		sqltext.append(" to_char(A.byrq,'yyyy-MM-dd') byrq,to_char(A.gzrq,'yyyy-MM-dd') gzrq,A.sysgl,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYZC+"' and dm=a.zyzc) zyzc,A.zyzc zyzcdm,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYGZ+"' and dm=a.zygz) zygz,");
		sqltext.append(" to_char(A.drrq,'yyyy-MM-dd') drrq,to_char(A.txrq,'yyyy-MM-dd') txrq,A.bz,");
		sqltext.append(" (case A.ryzt when '1' then '正常' when '0' then '禁用' end) ryzt,A.zzbz,A.pxxh,A.sfzh,");
		sqltext.append(" A.rygh,A.rownums,A.url,A.cssclass,A.lxdh,A.qq,A.mail,A.zzzt,A.mm ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("a.rybh");
		//人员信息弹窗中去掉000000、单位权限下的人员

		if(StateManager.BDXM_SYRBD.equals(flag)){
			pageList.setStrWhere(" and dwbh = '" + LUser.getDwbh() + "' ");
		}else if(StateManager.BDXM_DWNDB.equals(flag)){
			pageList.setStrWhere(" and a.dwbh in (select dwbh from gx_sys_dwb start with dwbh = '"+ejdw+"' and dwzt='1' connect by prior dwbh = sjdw) ");
		}else if(StateManager.BDXM_DWJDB.equals(flag)){
			pageList.setStrWhere(" and a.dwbh not in (select dwbh from gx_sys_dwb start with dwbh='"+ejdw+"' and dwzt='1' connect by prior dwbh = sjdw) " + pageService.getQxsql(userId, "dwbh", "D") + " ");
		}
		if(Validate.noNull(dwbh)){
			//点击左侧单位树的where条件
			pageList.setStrWhere(pageList.getStrWhere() + " and a.dwbh in(select dwbh from gx_sys_dwb connect by prior dwbh = sjdw and dwzt='1' start with dwbh = '" + dwbh.replace("D", "") + "')");
		}
		pageList.setStrWhere(pageList.getStrWhere() + " and a.rybh <> '"+SystemSet.AdminRybh()+"' and a.ryzt = '1' ");
		pageList.setTableName("gx_sys_ryb a");
		pageList = pageService.findWindowList(pd,pageList,"R");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取二级单位信息（资产调拨分单位内和单位间，用到）
	 * @return
	 */
	@RequestMapping("/ejdwxx")
	@ResponseBody
	public Object ejdwxx(){
		String userId = LUser.getRybh();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String ejdw = pd.getString("ejdw");
		String flag = pd.getString("flag");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.DWBH,K.MC,K.JC,K.DZ,TO_CHAR(K.JLRQ,'yyyy-MM-dd') JLRQ, ");
		sqltext.append(" K.FGLD,DECODE(K.DWZT,'0','禁用','1','正常') DWZT,K.DWJC,K.MJBZ,K.PXXH,K.BMH,");
		sqltext.append(" K.BMSX,K.BZ,K.SYSBZ,K.SYSJB,K.SYSGS,K.SYSLB,K.SYSMJ,K.JLNF,K.SYSLX,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.DWXZ+"' AND M.DM = K.DWXZ) DWXZ,");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=K.DWLD) DWLD,");
		sqltext.append(" (SELECT '('||BMH||')'||MC FROM GX_SYS_DWB C WHERE C.DWBH=K.SJDW) SJDW ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("k.dwbh");//主键
		if(Validate.noNull(dwbh)){
			//点击左侧单位树的where条件
			pageList.setStrWhere(" and k.dwzt='1' AND K.DWBH IN(SELECT DWBH FROM GX_SYS_DWB CONNECT BY PRIOR DWBH=SJDW and dwzt='1' START WITH DWBH='" + dwbh.replace("D", "") + "')");
		}else{
			//当前登录人管理单位权限
			if(StateManager.BDXM_DWNDB.equals(flag)){
				pageList.setStrWhere(" and k.dwzt='1' and k.dwbh in(select dwbh from gx_sys_dwb start with dwbh = '"+ejdw+"' connect by prior dwbh=sjdw and dwzt='1') ");
			}else if(StateManager.BDXM_DWJDB.equals(flag)){
				pageList.setStrWhere(" and k.dwzt='1' and k.dwbh in(select dwbh from gx_sys_dwb start with dwbh in(select dwbh from gx_sys_dwb where dwbh not in(select dwbh from gx_sys_dwb start with dwbh='"+ejdw+"' connect by prior dwbh = sjdw) "+pageService.getQxsql(userId, "dwbh", "D")+") connect by prior dwbh=sjdw and dwzt='1') ");
			}
		}
		pageList.setTableName("GX_SYS_DWB K");//表名
	    pageList = pageService.findWindowList(pd,pageList,"D");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 财政十大类信息弹窗
	 * @return
	 */
	@RequestMapping("/czten")
	@ResponseBody
	public Object getCzTenWindow(){
		PageData pd = this.getPageData();
		String zcdm = pd.getString("zcdm");
		String dlh = pd.getString("dlh");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" T.ZCDM,T.MC,T.DLH ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("zcdm");
		if(Validate.noNull(dlh) ){
			pageList.setStrWhere(" AND T.DLH ='"+dlh+"' ");
		}else if(Validate.noNull(zcdm) ){
			pageList.setStrWhere(" AND SUBSTR(T.ZCDM,0,2) ='"+zcdm+"' ");
		}else{
			pageList.setStrWhere("");
		}
		pageList.setTableName("ZC_FLB_CZBO T");
		pageList = pageService.findWindowList(pd,pageList,"CZT");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 首页--我的名下资产--更多
	 */
	@RequestMapping(value="/getZcxxList")
	@ResponseBody
	public Object getZcxxList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" T.YQBH,T.YQMC,TO_CHAR(T.RZRQ,'yyyy-MM-dd') RZRQ,(SELECT MC FROM ZC_SYS_DDB WHERE DDBH=T.BZXX) BZXX, ");
		sqltext.append(" DECODE(T.ZZJ,0,'0.00',TO_CHAR(ROUND(T.ZZJ, 2), 'fm999999999999999990.00')) ZZJ ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("YQBH");//主键
		pageList.setStrWhere("AND T.SYR='"+LUser.getRybh()+"' AND T.ZTBZ='"+StateManager.SHWC+"' AND T.BDZT='"+StateManager.BDZT_Normal+"'");
		pageList.setTableName("ZC_ZJB T ");//表名
		pageList.setHj1("");//合计
		pageList = pageService.findWindowList(pd,pageList,"ZCXX");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 桌面--建账（本月、本年）
	 */
	@RequestMapping(value="/goJzList")
	public ModelAndView goJzList(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		List xzList = dictService.getDict(Constant.XZ);//现状
		mv.addObject("xzList", xzList);
		mv.addObject("deskbz", "1");//桌面本月建账、本年建账标志
		mv.setViewName("window/desk_jzbdcz/zcjz");
		return mv;
	}
	/**
	 * 桌面--变动（本月、本年）
	 */
	@RequestMapping(value="/goBdList")
	public ModelAndView goBdList(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.addObject("deskbz", "1");//桌面本月建账、本年建账标志
		mv.setViewName("window/desk_jzbdcz/zcbd");
		return mv;
	}
	/**
	 * 桌面--处置（本月、本年）
	 */
	@RequestMapping(value="/goCzList")
	public ModelAndView goCzList(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		List czfslist = dictService.getDict(Constant.CZFS);
		mv.addObject("czfslist", czfslist);
		mv.addObject("deskbz", "1");//桌面本月建账、本年建账标志
		mv.setViewName("window/desk_jzbdcz/zccz");
		return mv;
	}
	
	/**
	 * 资产处置信息数据
	 * @return
	 */
	@RequestMapping(value="/getZcczPageList")
	@ResponseBody
	public Object getZcczPageList(){
		PageData pd = this.getPageData();
		String userId = LUser.getRybh();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StateManager sm = new StateManager();
		sqltext.append(" K.czbgbh,K.zi,K.hao,K.hxz xz,K.czyy,K.jdyj,K.ztbz zt,decode(nvl(K.zmyz,0),0,'0.00',(to_char(round(K.zmyz,2),'fm99999999999990.00'))) zmyz, ");
		sqltext.append(" decode(nvl(K.czjz,0),0,'0.00',(to_char(round(K.czjz,2),'fm99999999999990.00'))) czjz, ");
		sqltext.append(" decode(nvl(K.pgjz,0),0,'0.00',(to_char(round(K.pgjz,2),'fm99999999999990.00'))) pgjz, ");
		sqltext.append(sm.getCzZtbz("k.ztbz")+",");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.CZFS+"' and DM=K.HXZ ) HXZ, ");
		sqltext.append(" k.gkry gkrybh,(SELECT '(' || RYGH || ')' || XM FROM GX_SYS_RYB WHERE RYBH = k.gkry) gkry, ");
		sqltext.append(" to_char(K.czrq,'yyyy-mm-dd') czrq,to_char(K.jzrq,'yyyy-mm-dd') jzrq, ");
		sqltext.append(" K.gkdw,K.gkshry,K.gkyj,K.gkrq,K.jdr,K.jdrq, K.rybh,K.dwbh,K.czrxm,K.dwmc  ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("K.czbgbh");//主键
		//点击左侧树的where条件
		String deskbz = Validate.isNullToDefaultString(pd.getString("deskbz"), "");
		if("1".equals(deskbz)){//桌面本月处置、本年处置标志
			boolean b = deskService.ryjspd();//如果是用户不属于归口、财务、管理员角色，取自己的数据。如果是取管理权限下的数据。
			if(b){
				pageList.setStrWhere(Validate.isNullToDefaultString(pageList.getStrWhere(), "")+pageService.getQxsql(LUser.getRybh(), "k.dwbh", "D"));
			}else{
				pageList.setStrWhere(Validate.isNullToDefaultString(pageList.getStrWhere(), "")+" and k.jdr = '"+LUser.getRybh()+"'");
			}
		}else{
			pageList.setStrWhere(Validate.isNullToDefaultString(pageList.getStrWhere(), "")+" and k.jdr = '"+userId+"'");
		}
		String flag = Validate.isNullToDefaultString(pd.getString("flag"), "");
		if("y".equals(flag))
			flag = "and ZTBZ = '" + StateManager.ZCCZ_CW_TG + "' and to_char(shrq,'yyyy') = to_char(sysdate,'yyyy')  and czbgbh in (select distinct czbgbh from zc_czsqmxb where czbgbh is not null)";
		else if("m".equals(flag))
			flag = "and ZTBZ = '" + StateManager.ZCCZ_CW_TG + "' and to_char(shrq,'yyyy-mm') = to_char(sysdate,'yyyy-mm') and czbgbh in (select distinct czbgbh from zc_czsqmxb where czbgbh is not null)";
		pageList.setStrWhere(Validate.isNullToDefault(pageList.getStrWhere(),"") + flag);
		pageList.setTableName("ZC_CZBGB K");//表名
		pageList.setHj1(" decode(nvl(sum(ZMYZ),0),0,'0.00',(to_char(round(sum(ZMYZ),2),'fm99999999999990.00'))) ZMYZ,"+ 
				" decode(nvl(sum(PGJZ),0),0,'0.00',(to_char(round(sum(PGJZ),2),'fm99999999999990'))) PGJZ," +
				" decode(nvl(sum(CZJZ),0),0,'0.00',(to_char(round(sum(CZJZ),2),'fm99999999999990.00'))) CZJZ");
		String groupSql = "select count(k.ztbz) counts, "+sm.getCzGroupZtbz("K.ZTBZ")+ " from ZC_CZBGB k where 1=1" + pageList.getStrWhere()+ToSqlUtil.jsonToGroupSql(pd.getString("search[value]"), "ztbz")+" group by k.ztbz ";
		pageList = pageService.findPageList(pd,pageList,groupSql);//引用传递
		List zjlist = pageService.getPageZjList(pageList);//总计
		List hjlist = pageService.getPageHjList(pageList);//当前页
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()),gson.toJson(pageList.getGroupList()),gson.toJson(zjlist),gson.toJson(hjlist));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取资产变动页面数据
	 */
	@RequestMapping(value = "/getZcbdPageList")
	@ResponseBody
	public Object getZcbdPageList() throws Exception {
		PageData pd = this.getPageData();
		String userId = LUser.getRybh();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("B.BDBGBH,B.DWBH DW, B.HDWBH BDHDWBH,B.DJBZ BDZT,");
		sqltext.append("(CASE WHEN ZTBZ = '"+StateManager.SHWC+"' THEN  TO_CHAR(B.JZRQ, 'YYYY-MM-DD') ELSE TO_CHAR(B.BDRQ,'YYYY-MM-DD') END) BDRQ, B.RYBH RY,");
		sqltext.append("(SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB WHERE RYBH=B.RYBH) RYBH,");
		sqltext.append("(SELECT '('||BMH||')'||MC FROM GX_SYS_DWB WHERE DWBH=B.DWBH) DWBH,B.BDYY,");
		sqltext.append("TO_CHAR(B.JZRQ, 'YYYY-MM-DD') JZRQ,B.ZTBZ ZT,");
		sqltext.append("(SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB WHERE RYBH=B.GKRY) GKRY,(SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB WHERE RYBH = B.HRYBH) HRYBHMC, HRYBH HRYBH,");
		sqltext.append( StateManager.getZtmcSql(StateManager.CZLX_ZCBD, "ZTBZ", "ZTBZ", "1")+","+ StateManager.getZtmcSql(StateManager.CZLX_ZCBD, "DJBZ", "DJBZ", "4"));
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("B.BDBGBH");// 主键
		String deskbz = Validate.isNullToDefaultString(pd.getString("deskbz"), "");
		if("1".equals(deskbz)){//桌面本月变动、本年变动标志
			boolean b = deskService.ryjspd();//如果是用户不属于归口、财务、管理员角色，取自己的数据。如果是取管理权限下的数据。
			if(b){
				pageList.setStrWhere(Validate.isNullToDefaultString(pageList.getStrWhere(), "")+pageService.getQxsql(LUser.getRybh(), "b.dwbh", "D"));
			}else{
				pageList.setStrWhere(Validate.isNullToDefaultString(pageList.getStrWhere(), "")+" and b.jdr = '"+LUser.getRybh()+"'");
			}
		}else{
			pageList.setStrWhere(Validate.isNullToDefaultString(pageList.getStrWhere(), "")+" and b.jdr = '"+userId+"'");
		}
		
		String flag = Validate.isNullToDefaultString(pd.getString("flag"), "");
		if("y".equals(flag))
			flag = " and ("
					+ "(djbz in ('" + StateManager.BDXM_DJBD + "','" + StateManager.BDXM_FJZJ + "','" + StateManager.BDXM_FJCZ + "','" + StateManager.BDXM_BFBF + "') and ztbz = '" + StateManager.ZCBD_CW_TG + "' and to_char(shrq,'yyyy') = to_char(sysdate,'yyyy')) "
					+ "or (djbz = '" + StateManager.BDXM_DWNDB + "' and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(glyshrq,'yyyy') = to_char(sysdate,'yyyy')) "
					+ "or (djbz in ('" + StateManager.BDXM_XMBD + "','" + StateManager.BDXM_DWJDB + "','" + StateManager.BDXM_CFDDBD + "') and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(gkrq,'yyyy')=to_char(sysdate,'yyyy')) "
					+ "or (djbz = '" + StateManager.BDXM_SYRBD + "' and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(lyrrq,'yyyy') = to_char(sysdate,'yyyy')) "
					+ ")";
		else if("m".equals(flag))
			flag = " and ("
					+ "(djbz in ('" + StateManager.BDXM_DJBD + "','" + StateManager.BDXM_FJZJ + "','" + StateManager.BDXM_FJCZ + "','" + StateManager.BDXM_BFBF + "') and ztbz = '" + StateManager.ZCBD_CW_TG + "' and to_char(shrq,'yyyy-mm') = to_char(sysdate,'yyyy-mm')) "
					+ "or (djbz = '" + StateManager.BDXM_DWNDB + "' and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(glyshrq,'yyyy-mm') = to_char(sysdate,'yyyy-mm')) "
					+ "or (djbz in ('" + StateManager.BDXM_XMBD + "','" + StateManager.BDXM_DWJDB + "','" + StateManager.BDXM_CFDDBD + "') and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(gkrq,'yyyy-mm')=to_char(sysdate,'yyyy-mm')) "
					+ "or (djbz = '" + StateManager.BDXM_SYRBD + "' and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(lyrrq,'yyyy-mm') = to_char(sysdate,'yyyy-mm')) "
					+ ")";
		pageList.setStrWhere(Validate.isNullToDefaultString(pageList.getStrWhere(), "")+flag);
		pageList.setTableName("ZC_BDBGB B ");
		pageList.setHj1("");// 合计
		String groupSql = "select count(b.ztbz) counts, "+StateManager.getZtmcSql(StateManager.CZLX_ZCBD, "b.ztbz", "ztbzmc", "3")+" from ZC_BDBGB b where 1=1 "+ToSqlUtil.jsonToGroupSql(pd.getString("search[value]"), "ztbz")+" "+pageList.getStrWhere()+" group by b.ztbz ";
		pageList = pageService.findPageList(pd,pageList,groupSql);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()),gson.toJson(pageList.getGroupList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 菜单管理--菜单树
	 */
	@RequestMapping(value="/mainCdglTree")
	public ModelAndView goMainCdglTreePage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("window/commonTree/mainCdglTree");
		return mv;
	}
	/**
	 * 人员信息弹窗--去除工资月份
	 * @return
	 */
	@RequestMapping("/ryxx_delgzyf")
	@ResponseBody
	public Object getRyxxWindow_delgzyf(){
		String userId = LUser.getRybh();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		if(Validate.isNull(dwbh)){
			dwbh=pd.getString("treedwbh");
		}
		String controlId = pd.getString("controlId");
		String jsbh = pd.getString("jsbh");
		String flag = pd.getString("flag");
		String flags = pd.getString("flags");
		String zbid = pd.getString("zbid");
		String gzyf = pd.getString("gzyf");
//		String type = pd.getString("type");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" a.guid,a.rybh rybh,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.dwbh) dwmc,");
		sqltext.append(" a.xm,(select mc from gx_sys_dmb where zl='"+Constant.SEX+"' and dm=a.xb) xb,a.xb xbdm,to_char(a.csrq,'yyyy-MM-dd') csrq,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.WHCD+"' and dm=a.whcd) whcd,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.SXZY+"' and dm=a.sxzy) sxzy,");
		sqltext.append(" to_char(a.byrq,'yyyy-MM-dd') byrq,to_char(A.gzrq,'yyyy-MM-dd') gzrq,a.sysgl,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYZC+"' and dm=a.zyzc) zyzc,a.zyzc zyzcdm,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYGZ+"' and dm=a.zygz) zygz,");
		sqltext.append(" to_char(a.drrq,'yyyy-mm-dd') drrq,to_char(a.txrq,'yyyy-mm-dd') txrq,a.bz,");
		sqltext.append(" (case a.ryzt when '1' then '正常' when '0' then '禁用' end) ryzt,a.zzbz,a.pxxh,a.sfzh,");
		sqltext.append(" a.rygh,a.rownums,a.url,a.cssclass,a.lxdh,a.qq,a.mail,a.zzzt,a.mm ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("rybh");
		//人员信息弹窗中去掉000000、单位权限下的人员
		sqltext = new StringBuffer();
		sqltext.append(" and a.rybh <> '"+SystemSet.AdminRybh()+"' and ryzt = '1' ");
		if(StateManager.BDXM_SYRBD.equals(flag)){//使用人变动时没有左侧树，只选择本单位的
			sqltext.append(" and dwbh = '" + LUser.getDwbh() + "' ");
		}else{
			if(Validate.noNull(dwbh)){
				//点击左侧单位树的where条件
				sqltext.append(" and a.dwbh in (select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh = '" + dwbh.replace("D", "") + "')");
			}else{
				if("1".equals(flags)){
					sqltext.append(pageList.getStrWhere());
				}else{
					if("All".equals(flag)){
						sqltext.append(pageList.getStrWhere());
					}else{
						//当前登录人管理单位权限
						sqltext.append(pageService.getQxsql(userId, "a.dwbh", "D"));
					}
				}
			}
			
			if(Validate.noNull(jsbh)){
				String sql = "select rybh from zc_sys_jsryb where jsbh='"+jsbh+"'";
				List list = db.queryForList(sql);
				String rybh = "";
				if(list.size()>0){
					StringBuffer value = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						Map map = (Map)list.get(i);
						value.append(map.get("RYBH")+"','");
					}
					rybh = value.substring(0, value.length()-3);
				}
				if(Validate.noNull(rybh)){
					sqltext.append(" and rybh not in ('"+rybh+"') ");
				}
			}
		}
		if(Validate.noNull(zbid)){
			sqltext.append(" and rybh in (SELECT fzr FROM XMINFOS WHERE guid IN(SELECT xmguid FROM cw_rcbxmxb WHERE zbid='"+zbid+"'))");
		}
		if("zzry".equals(controlId)) {
			sqltext.append("and rybh not in (select  nvl(gh,'0') from cw_xzb where gzyf ='"+gzyf+"')");
		}else if("txry".equals(controlId)) {
			sqltext.append("and rybh not in (select  nvl(RYBH,'0') from CW_LZXZB where gzyf ='"+gzyf+"')");
		}
		
		pageList.setStrWhere(sqltext.toString());

		pageList.setTableName("gx_sys_ryb a");
		pageList = pageService.findWindowList(pd,pageList,"R");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 归档范围分类树
	 * @return
	 */
	@RequestMapping(value="/goGdfwflTree")
	public ModelAndView goGdfwflTree(){
		ModelAndView mv=this.getModelAndView();
		String controlId = this.getPageData().getString("controlId");
		mv.addObject("controlId", controlId);
		String controlName = this.getPageData().getString("controlName");
		mv.addObject("controlName", controlName);
		mv.setViewName("/window/gdfw/mainGdfwflTree");
		return mv;
	}
	
	
}
