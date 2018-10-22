package com.googosoft.modules.app.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.JSON.JsonArray;
import com.googosoft.JSON.JsonObject;
import com.googosoft.JSON.KeysValues;
import com.googosoft.commons.LUser;
import com.googosoft.commons.PropertiesUtil;
import com.googosoft.controller.base.BaseController;
import com.googosoft.modules.app.dao.JdbxDao;
import com.googosoft.modules.app.service.JdbxsqService;
import com.googosoft.modules.app.service.PhoneService;
import com.googosoft.modules.app.service.YwcService;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.fzgn.tzgg.FbxxService;
import com.googosoft.service.system.index.DeskService;
import com.googosoft.service.system.index.IndexService;
import com.googosoft.util.FileUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.MyClient;
import com.googosoft.websocket.echo.SessionMap;

@Controller
@RequestMapping(value="/phone")
public class PhoneController extends BaseController{
	@Resource(name="phoneService")
	private PhoneService phoneService;
	
	@Resource(name="indexService")
	private IndexService indexService;
	
	@Resource(name="deskService")
	private DeskService deskService;
	
	@Resource(name="fbxxService")
	private FbxxService fbxxService;//单例
	
	@Resource(name = "ywcservice")
	private YwcService ywcservice;
	
	@Resource(name="jdbxsqservice")
	private JdbxsqService jdbxsqservice;
	
	@Resource(name="jdbxdao")
	private JdbxDao jdbxdao;
	
	Gson gson = new Gson();
	
	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping(value="/logingh",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object logingh(){
		return phoneService.logingh(getPageData());
	}
	
	@RequestMapping(value="/sxlogin",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object sxlogin(){
		return phoneService.sxlogin(getPageData());
	}
	/**
	 * 修改密码
	 */
	@RequestMapping(value="/xgmm",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xgmm(){
		return phoneService.xgmm(getPageData());
	}
	
	/**
	 * 日常报销列表搜索
	 */
	@RequestMapping(value="/rcbxlistsearch",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rcbxlistsearch(){
		return phoneService.rcbxlistsearch(getPageData());
	}
	
	/**
	 * 桌面刷新
	 */
	@RequestMapping(value="/main",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object main(){
		return phoneService.main(getPageData());
	}
	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping(value="/Login",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object login(){
		return phoneService.login(getPageData());
	}
	/**
	 * 首页的接口
	 * @return
	 */
	@RequestMapping(value="/work",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object work(){
		return phoneService.work(getPageData());
	}
	
	/**
	 * 首页搜索接口
	 * @return
	 */
	@RequestMapping(value="/dshlistsearch",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object dshlistsearch(){
		return phoneService.dshlistsearch(getPageData());
	}
	
	/**
	 * 差旅费审批详情数据获取
	 * @return
	 */
	@RequestMapping(value="/clfbxspxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object clfbxspxq(){
		return phoneService.clfbxspxq(getPageData());
	}
	
	/**
	 * 更多模块
	 * @return
	 */
	@RequestMapping(value="/more_yy",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object more_yy(){
		return phoneService.more_yy(getPageData());
	}
	
	/**
	 * 待办事项列表展示
	 * @return
	 */
	@RequestMapping(value="/ywsplist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ywsplist(){
		return phoneService.ywsplist(getPageData());
	}
	/**
	 * 待办事项详情加载
	 * @return
	 */
	@RequestMapping(value="/ywspxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ywspxq(){
		return phoneService.ywspxq(getPageData());
	}
	/**
	 * 待办事项提交
	 * @return
	 */
	@RequestMapping(value="/ywsptj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object ywsptj(){
		return phoneService.ywsptj(getPageData());
	}
	
	/**
	 * 加载我的
	 */
	@RequestMapping(value="/grxx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object grxx(){
		return phoneService.grxx(getPageData());
	}
	
	/**
	 * 头像上传接口
	 * @return
	 */
	@RequestMapping(value="/txsc",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object txsc(){
		return phoneService.txsc(getPageData());
	}
	
	/**
	 * 加载版本说明
	 */
	@RequestMapping(value="/versions",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object versions(){
		return phoneService.versions(getPageData());
	}
	
	/**
	 * 获取通讯录
	 * @return
	 */
	@RequestMapping(value="/hqtxl",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object hqtxl(){
		return phoneService.hqtxl(getPageData());
	}
	
	/**
	 * 我的名下资产列表展示
	 * @return
	 */
	@RequestMapping(value="/mxzclist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object mxzclist(){
		return phoneService.mxzclist(getPageData());
	}
	/**
	 * 名下资产详情加载
	 * @return
	 */
	@RequestMapping(value="/mxzcxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object mxzcxq(){
		return phoneService.mxzcxq(getPageData());
	}
	
	/**
	 * 通知公告列表展示
	 * @return
	 */
	@RequestMapping(value="/tzgglist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object tzgglist(){
		return phoneService.tzgglist(getPageData());
	}
	
	/**
	 * 通知搜索接口
	 * @return
	 */
	@RequestMapping(value="/tzggsearch",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object tzggsearch(){
		return phoneService.tzggsearch(getPageData());
	}
	
	/**
	 * 通讯录接口
	 * @return
	 */
	@RequestMapping(value="/txl",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object txl(){
		return phoneService.txl(getPageData());
	}
	
	/**
	 * 通讯录搜索接口
	 * @return
	 */
	@RequestMapping(value="/txlsearch",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object txlsearch(){
		return phoneService.txlsearch(getPageData());
	}
	
	/**
	 * 我的薪酬接口
	 * @return
	 */
//	@RequestMapping(value="/wdxc",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public Object wdxc(){
//		return phoneService.wdxc(getPageData());
//	}
	
	/**
	 * 我的项目列表接口
	 * @return
	 */
	@RequestMapping(value="/wdxm",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wdxm(){
		return phoneService.wdxm(getPageData());
	}
	
	/**
	 * 我的项目查询接口
	 * @return
	 */
	@RequestMapping(value="/wdxmsearch",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wdxmsearch(){
		return phoneService.wdxmsearch(getPageData());
	}
	
	/**
	 * 我的项目加载详情接口
	 * @return
	 */
	@RequestMapping(value="/wdxmxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wdxmxq(){
		return phoneService.wdxmxq(getPageData());
	}
	
	
	/**
	 * 密码修改接口
	 * @return
	 */
	@RequestMapping(value="/mmxg",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object mmxg(){
		return phoneService.mmxg(getPageData());
	}
	/**
	 * 我的银行卡接口
	 * @return
	 */
	@RequestMapping(value="/wdyhk",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wdyhk(){
		return phoneService.wdyhk(getPageData());
	}
	
//	@RequestMapping(value="/goMain",produces = "text/html;charset=UTF-8")
//	public ModelAndView goMain(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = this.getPageData();
//		System.err.println("_________type__");
//		mv.setViewName("system/index/tzgg_view"); 
//		return mv;
//	}
	
	@RequestMapping(value="/goMain",produces = "text/html;charset=UTF-8")
	public ModelAndView goTzggEdit(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		Map map = fbxxService.getObjectById(pd.getString("xh"));
		mv.addObject("Content",map.get("XX"));
		mv.addObject("xtxx", map);
		mv.setViewName("window/tzgg/tzggEditphone");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 首页通讯录查询列表
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value="/getTzggList",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//		public Object getTxlList(){
//		PageData pd = this.getPageData();
//		String key = pd.getString("key");
//		PageList pageList = new PageList();
//		pageList.setSqlText("*");
//		pageList.setKeyId("gid");//主键
//		pageList.setStrWhere(" and nvl(zt,'1') = '1' and (rybh like '%" + searchword + "%' or xm like '%" + searchword + "%') ");
//		if(Validate.noNull(key)){
//			pageList.setStrWhere("and (rybh like '%" + key + "%' or xm like '%" + key + "%') ");
//		}
//		pageList.setTableName("zc_txl");//表名
//		pd.remove("search[value]");
//		pageList = pageService.findPageList(pd, pageList);
//		Gson gson = new Gson();
//		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
//		return pageInfo.toJson();
//	}
	//初始化登录人员信息
	public void iniLogin(ModelAndView mv) {		
		mv.addObject("loginId",LUser.getGuid());			
		mv.addObject("ryxm",LUser.getRyxm());			
	}
	
	/**
	 * 我的公务卡接口
	 * @return
	 */
	@RequestMapping(value="/wdgwk",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wdgwk(){
		return phoneService.wdgwk(getPageData());
	}
	
	/**
	 * 个人信息修改接口
	 * @return
	 */
	@RequestMapping(value="/grxxxg",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object grxxxg(){
		return phoneService.grxxxg(getPageData());
	}
	
	/**
	 * 通知公告详情加载
	 * @return
	 */
	@RequestMapping(value="/tzggxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object tzggxq(){
		return phoneService.tzggxq(getPageData());
	}
	
	/**
	 * 系统消息列表展示
	 * @return
	 */
	@RequestMapping(value="/xtxxlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xtxxlist(){
		return phoneService.xtxxlist(getPageData());
	}
	/**
	 * 系统消息详情加载
	 * @return
	 */
	@RequestMapping(value="/xtxxxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xtxxxq(){
		return phoneService.xtxxxq(getPageData());
	}
	
	/**
	 * 资产查询列表展示
	 * @return
	 */
	@RequestMapping(value="/zccxlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zccxlist(){
		return phoneService.zccxlist(getPageData());
	}
	/**
	 * 资产查询加载详情
	 * @return
	 */
	@RequestMapping(value="/zccxxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zccxxq(){
		return phoneService.zccxxq(getPageData());
	}
	
	/**
	 * 进度跟踪列表展示
	 */
	@RequestMapping(value="/jdgzlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdgzlist(){
		return phoneService.jdgzlist(getPageData());
	}
	
	/**
	 * 资产图片维护获取资产列表
	 * @return
	 */
	@RequestMapping(value="/zctpwhlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zctpwhlist(){
		return phoneService.zctpwhlist(getPageData());
	}
	/**
	 * 资产图片维护获取资产信息
	 * @return
	 */
	@RequestMapping(value="/zctpwhxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zctpwhxq(){
		return phoneService.zctpwhxq(getPageData());
	}
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tpscgetinfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public String tpscgetinfo(MultipartHttpServletRequest request) {
		return phoneService.tpscgetinfo(request);
	}
	/**
	 * 资产图片维护提交
	 * @return
	 */
	@RequestMapping(value="/zctpwhtj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object zctpwhtj(){
		return phoneService.zctpwhtj(getPageData());
	}
	
	/**
	 * 获取资产清查状态
	 * @return
	 */
	@RequestMapping(value="/zcqczt",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zcqczt(){
		return phoneService.zcqczt();
	}
	/**
	 * 资产清查详情加载
	 * @return
	 */
	@RequestMapping(value="/zcqcgetinfo",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zcqcgetinfoqc(){
		return phoneService.zcqcgetinfoqc(getPageData());
	}
	/**
	 * 提交清查数据
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/zcqcjgtj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object zcqctj(){
		return phoneService.zcqctj(getPageData());
	}
	
	/**
	 * 获取资产自查列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/zczclist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zczclist(){
		return phoneService.zczclist(getPageData());
	}
	/**
	 * 提交自查数据
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/zczctj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object zczctj(){
		return phoneService.zczctj(getPageData());
	}
	
	/**
	 * 获取常见问题列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cjwtlb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cjwtlist(){
		return phoneService.cjwtlist(getPageData());
	}
	/**
	 * 获取常见问题详情
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cjwtlbxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cjwtlbxq(){
		return phoneService.cjwtlbxq(getPageData());
	}
	
	/**
	 * 获取业务说明列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/ywsmlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ywsmlist(){
		return phoneService.ywsmlist(getPageData());
	}
	/**
	 * 获取业务说明详情
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/ywsmxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ywsmxq(){
		return phoneService.ywsmxq(getPageData());
	}

	/**
	 * 获取存放地点变动资产列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cfddbdlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cfddbdlist(){
		return phoneService.cfddbdlist(getPageData());
	}
	/**
	 * 获取存放地点列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cfddlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cfddlist(){
		PageData pd = this.getPageData();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		JsonArray array = phoneService.getCfddList(map);
		if(array.size() == 0){
			int index = (int)Float.parseFloat(map.get("index").toString());
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的存放地点信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多存放地点信息！\"}";
			}
		}
		else{
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 提交存放地点变动信息
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cfddbdtj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object cfddbdtj(){
		return phoneService.cfddbdtj(getPageData());
	}

	/**
	 * 获取使用人变动资产列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/syrbdlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object syrbdlist(){
		return phoneService.syrbdlist(getPageData());
	}
	/**
	 * 获取备选的使用人列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/rylist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rylist(){
		PageData pd = this.getPageData();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		JsonArray array = phoneService.getRyList(map,"3");
		if(array.size() == 0){
			int index = (int)Float.parseFloat(map.get("index").toString());
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的使用人信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多使用人信息！\"}";
			}
		}
		else{
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 提交使用人变动信息
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/syrbdtj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object syrbdtj(){
		return phoneService.syrbdtj(getPageData());
	}

	/**
	 * 获取资产认领（领用人确认）信息
	 * @return
	 */
	@RequestMapping(value="/zcrllist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zcrllist(){
		return phoneService.zcrllist(getPageData());
	}
	/**
	 * 获取资产认领（领用人确认）详情
	 * @return
	 */
	@RequestMapping(value="/zcrlxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zcrlxq(){
		return phoneService.zcrlxq(getPageData());
	}
	/**
	 * 资产认领（领用人确认）提交
	 * @return
	 */
	@RequestMapping(value="/zcrlyjtj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object zcrlyjtj(){
		return phoneService.zcrlyjtj(getPageData());
	}

	/**
	 * 资产处置申请列表
	 * @return
	 */
	@RequestMapping(value="/czsqlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object czsqlist(){
		return phoneService.czsqlist(getPageData());
	}
	/**
	 * 提交处置申请
	 * @return
	 */
	@RequestMapping(value="/scczsqd",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object scczsqd(){
		return phoneService.scczsqd(getPageData());
	}

	/**
	 * 获取闲置领用申请的资产列表
	 * @return
	 */
	@RequestMapping(value="/xzzclylist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xzzclylist(){
		return phoneService.xzzclylist(getPageData());
	}
	
	
	/**
	 * 获取申购单位列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/sgdwlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object sgdwlist(){
		PageData pd = this.getPageData();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		JsonArray array = phoneService.getDwList(map,"0","");
		if(array.size() == 0){
			int index = (int)Float.parseFloat(map.get("index").toString());
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的单位信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多单位信息！\"}";
			}
		}
		else{
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 获取分类列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/fllist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object fllist(){
		PageData pd = this.getPageData();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		//sb:设备  jj:家具
		String lx = Validate.isNullToDefaultString(map.get("lx"), "1");
		JsonArray array = phoneService.getFlList(map,lx,"1", "0");
		if(array.size() == 0){
			int index = (int)Float.parseFloat(map.get("index").toString());
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的分类信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多分类信息！\"}";
			}
		}
		else{
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
//	/**
//	 * 获取财政分类列表
//	 * @return
//	 * @throws ParseException 
//	 */
//	@RequestMapping(value="/czfllist",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public Object czfllist(){
//		PageData pd = this.getPageData();
//		Map map = gson.fromJson(pd.getString("key"), Map.class);
//		JsonArray array = phoneService.getCzflList(map);
//		if(array.size() == 0){
//			int index = (int)Float.parseFloat(map.get("index").toString());
//			if(index == 1){
//				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的财政分类信息！\"}";
//			}
//			else{
//				return "{\"success\":\"false\",\"msg\":\"暂无更多财政分类信息！\"}";
//			}
//		}
//		else{
//			JsonObject json = new JsonObject(
//					new KeysValues("success",true),
//					new KeysValues("msg","数据加载成功！"),
//					new KeysValues("items",array)
//				);
//			return json.toString();
//		}
//	}
	/**
	 * 获取采购人列表
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/cgrlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cgrlist(){
		PageData pd = this.getPageData();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		JsonArray array = phoneService.getRyList(map, "0");
		if(array.size() == 0){
			int index = (int)Float.parseFloat(map.get("index").toString());
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的采购人信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多采购人信息！\"}";
			}
		}
		else{
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	
	/**
	 * 获取资产的使用人列表
	 * @return
	 */
	@RequestMapping(value="/syrlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object syrlist(){
		PageData pd = this.getPageData();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		JsonArray array = phoneService.getRyList(map,"1");
		if(array.size() == 0){
			int index = (int)Float.parseFloat(map.get("index").toString());
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的使用人信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多使用人信息！\"}";
			}
		}
		else{
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	
	/**
	 * 获取资产的使用单位列表
	 * @return
	 */
	@RequestMapping(value="/sydwlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object sydwlist(){
		PageData pd = this.getPageData();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		JsonArray array = phoneService.getDwList(map, "1", "1");
		if(array.size() == 0){
			int index = (int)Float.parseFloat(map.get("index").toString());
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的使用单位信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多使用单位信息！\"}";
			}
		}
		else{
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}

	/**
	 * 保存设备入账卡片信息
	 * @return
	 */
	@RequestMapping(value="/sbjzzctj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object sbjzzctj(){
		return phoneService.sbjzzctj(getPageData());
	}

	/**
	 * 保存家具入账卡片信息
	 * @return
	 */
	@RequestMapping(value="/jjjzzctj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object jjjzzctj(){
		return phoneService.jjjzzctj(getPageData());
	}
	
	
	
	/*****************************扫描二维码登录 start***********************************/
	/**
	 * 生成扫码上传的二维码
	 * @return
	 */
	@RequestMapping(value="/LoginCreateQRCode",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object LoginCreateQRCode(){
		String QRCodeContent = "googosoft&" + Validate.isNullToDefaultString(this.getPageData().getString("QRKey"), "") + "&" + System.currentTimeMillis();
		return phoneService.createQRCode(QRCodeContent);
	}
	
	/**
	 * 生成扫码上传的二维码承包商
	 * @return
	 */
	@RequestMapping(value="/LoginCreateQRCodeByCbs",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object LoginCreateQRCodeByCbs(){
		PageData pd = this.getPageData();
		ResourceBundle bundle = ResourceBundle.getBundle("global");
		String url = bundle.getString("xfddUrl");
		//二维码的生成规则，参照手机端
		//访问路径（还没确定）+?cbs=承办商guid&xfdd=消费地点guid&cbsmc=承办商名称&xfddmc=消费地点名称&ywlx=业务类型
//		String url = "gogoosoft";//访问路径待定
		String xfddGui = Validate.isNullToDefaultString(pd.getString("QRKey"), "");//消费地点guid
		String cbsGuid = Validate.isNullToDefaultString(phoneService.getCbsGuidByXfddGuid(xfddGui), "");//承办商guid
		String cbsmc = Validate.isNullToDefaultString(pd.getString("cbs"), "");//承办商名称
		String xfddmc = Validate.isNullToDefaultString(pd.getString("dd"), "");//消费地点名称
		String ywlx = Validate.isNullToDefaultString(pd.getString("ywlx"), "");//业务类型
		String QRCodeContent = url+"?cbs="+cbsGuid+"&xfdd="+xfddGui+"&cbsmc="+cbsmc+"&xfddmc="+xfddmc+"&ywlx="+ywlx;
		if(url.contains("?")){
			QRCodeContent = url+"cbs="+cbsGuid+"&xfdd="+xfddGui+"&cbsmc="+cbsmc+"&xfddmc="+xfddmc+"&ywlx="+ywlx;
		}else{
			QRCodeContent = url+"?cbs="+cbsGuid+"&xfdd="+xfddGui+"&cbsmc="+cbsmc+"&xfddmc="+xfddmc+"&ywlx="+ywlx;
		}
		return phoneService.createQRCode(QRCodeContent);
	}
	
	/**
	 * 生成扫码上传的二维码承包商
	 * @return
	 */
	@RequestMapping(value="/LoginCreateQRCodeByPlCbs",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object LoginCreateQRCodeByPlCbs(){
		PageData pd = this.getPageData();
		ResourceBundle bundle = ResourceBundle.getBundle("global");
		String url = bundle.getString("xfddUrl");
		//二维码的生成规则，参照手机端
		//访问路径（还没确定）+?cbs=承办商guid&xfdd=消费地点guid&cbsmc=承办商名称&xfddmc=消费地点名称
//		String url = "gogoosoft";//访问路径待定
		String[] xfddGuis = Validate.isNullToDefaultString(pd.getString("QRKey"), "").split(",");//消费地点guid
//		String[] cbsGuids = Validate.isNullToDefaultString(phoneService.getCbsGuidByXfddGuid(xfddGui), "").split(",");//承办商guid
		String[] cbsmcs = Validate.isNullToDefaultString(pd.getString("cbs"), "").split(",");//承办商名称
		String[] xfddmcs = Validate.isNullToDefaultString(pd.getString("dd"), "").split(",");//消费地点名称
		String cbsGuid = "";
		String xfddGuid = "";
		String cbsmc = "";
		String xfddmc = "";
		String QRCodeContent = "";
		String result = "";
		for(int i=0;i<xfddGuis.length;i++){
			xfddGuid = Validate.isNullToDefaultString(xfddGuis[i],"");
			cbsGuid = Validate.isNullToDefaultString(phoneService.getCbsGuidByXfddGuid(xfddGuid), "");
			cbsmc = Validate.isNullToDefaultString(cbsmcs[i],"");
			xfddmc = Validate.isNullToDefaultString(xfddmcs[i],"");
			QRCodeContent = url+"?cbs="+cbsGuid+"&xfdd="+xfddGuid+"&cbsmc="+cbsmc+"&xfddmc="+xfddmc;
			if(url.contains("?")){
				QRCodeContent = url+"cbs="+cbsGuid+"&xfdd="+xfddGuid+"&cbsmc="+cbsmc+"&xfddmc="+xfddmc;
			}else{
				QRCodeContent = url+"?cbs="+cbsGuid+"&xfdd="+xfddGuid+"&cbsmc="+cbsmc+"&xfddmc="+xfddmc;
			}
			result = phoneService.createQRCode(QRCodeContent);
		}
		return result;
	}
	
	/**
	 * 扫码登录触发websocket
	 * @return
	 */
	@RequestMapping(value="/smdl",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String LoginQRCode(){
		Map map = gson.fromJson(this.getPageData().getString("key"), Map.class);
		String QRKey = Validate.isNullToDefaultString(map.get("QRKey"),"");
		String rybh = Validate.isNullToDefaultString(map.get("userId"),"");
		String msg = "";
		System.err.println(QRKey);System.err.println(rybh);
		String uri = PropertiesUtil.getGlobalValueByKey("WebSocket_Uri");
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		try{
			Session sendsession = container.connectToServer(MyClient.class, new URI(uri));
			Session receivesession = SessionMap.sessions.get(QRKey);
			if(receivesession != null){
			    receivesession.getBasicRemote().sendText(rybh);
			    msg = "{\"success\":true,\"msg\":\"正在登录，请稍后...\"}";
			}
			else{
				msg = "{\"success\":false,\"msg\":\"登录信息有误，登录失败！\"}";
			}
			sendsession.close();
		}
		catch(Exception e){
			e.printStackTrace();
			msg = "{\"success\":false,\"msg\":\"登录失败！\"}";
		}
		return msg;
	}
	/*****************************扫描二维码登录 start***********************************/
	
	
	/*****************************扫描二维码上传图片 start***********************************/
	/**
	 * 生成扫码上传的二维码
	 * @return
	 */
	@RequestMapping(value="/ImgUploadCreateQRCode",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ImgUploadCreateQRCode(){
		PageData pd = this.getPageData();
		
		String id = Validate.isNullToDefaultString(pd.getString("id"), "");
		if(Validate.isNull(id)){
			return "{\"success\":false,\"msg\":\"传入参数不全，生成二维码失败！\"}";
		}
//		String djlx = Validate.isNullToDefaultString(pd.getString("djlx"), "");
//		if(Validate.isNull(djlx)){
//			return "{\"success\":false,\"msg\":\"传入参数不全，生成二维码失败！\"}";
//		}
		String fold = Validate.isNullToDefaultString(pd.getString("fold"), "");
		if(Validate.isNull(fold)){
			return "{\"success\":false,\"msg\":\"传入参数不全，生成二维码失败！\"}";
		}
		
		String QRCodeContent = PropertiesUtil.getGlobalValueByKey("QRCodePath") + "?dbh=" + id + "&djlx=" + id + "&fold=" + fold + "&rybh=" + Validate.isNullToDefaultString(LUser.getRybh(), "") + "&stamp=" + System.currentTimeMillis();
		return phoneService.ImgUploadCreateQRCode(QRCodeContent);
	}
	/**
	 *跳转到上传图片页面
	 */
	@RequestMapping(value="/goImgUpload")
	public ModelAndView goImgUpload() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("dbh", pd.getString("dbh"));
		mv.addObject("djlx", pd.getString("djlx"));
		mv.addObject("fold", pd.getString("fold"));
		mv.addObject("stamp", pd.getString("stamp"));
		mv.addObject("rybh", pd.getString("rybh"));
		
		mv.setViewName("app/imgUpload");
		return mv;
	}
	
	/**
	 * 扫码上传图片
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/doUploadSmsc")
	@ResponseBody
	public String doUploadSmsc(HttpServletRequest request) throws IllegalStateException, IOException{
		if(request == null){
			return "{\"error\":\"系统掉线，请重新登录！\"}";
		}
		
		String id = request.getParameter("id");
		String djlx = request.getParameter("djlx");
		String fold = request.getParameter("fold");
		String rybh = request.getParameter("rybh");
//		rybh="000000";
		String stamp = request.getParameter("stamp");
		String dayFold = new SimpleDateFormat("yyyyMMdd").format(new Date());
		System.out.println("打印id====================="+id);
		System.out.println("打印djlx====================="+djlx);
		System.out.println("打印fold====================="+fold);
		System.out.println("打印rybh====================="+rybh);
		System.out.println("打印stamp====================="+stamp);
		System.out.println("打印dayFold====================="+dayFold);
		
		if(Validate.isNull(stamp) || Validate.isNull(id) || Validate.isNull(djlx) || Validate.isNull(fold) || Validate.isNull(rybh)){
			return "{\"error\":\"参数传入有误！\"}";
		}
		
		long sjc = new Date(Long.parseLong(stamp)).getTime();//时间戳
		long nowtime = new Date(System.currentTimeMillis()).getTime();
		String timer = PropertiesUtil.getGlobalValueByKey("QRCodeTime");
		long yxsj = Long.parseLong(timer) * 60 * 1000;//有效时间，毫秒
		//System.err.println("sjc:" + sjc + "  nowtime:" + nowtime + "   yxsj:" + yxsj + "   nowtime-sjc:" + (nowtime - sjc));
		if(nowtime - sjc > yxsj){
			return "{\"error\":\"二维码失效，请刷新二维码后重新扫描！\"}";
		}
		
		// 转换为文件类型的request
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获取对应file对象
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Iterator<String> fileIterator = multipartRequest.getFileNames();
        while (fileIterator.hasNext()) {
            String fileKey = fileIterator.next();
            // 获取对应文件
            MultipartFile multipartFile = fileMap.get(fileKey);
            if (multipartFile.getSize() != 0L) {
            	//原始文件名称
            	String pictureFile_name =  multipartFile.getOriginalFilename();
            	//新文件名称
            	String newFileName = get32UUID() + pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
            	//虚拟路径
            	String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
            	//物理路径
            	String wllu_path = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath");
            	//上传图片
            	File uploadPic = new File(wllu_path+"/"+fold+"/"+dayFold+"/"+newFileName);
            	if(!uploadPic.exists()){
            		uploadPic.mkdirs();
            	}
            	//向磁盘写文件
            	multipartFile.transferTo(uploadPic);
            	String urlpath = fold+"/"+dayFold+"/"+newFileName;
            	String guid = get32UUID();
            	phoneService.doUploadSmsc(guid,id,djlx,pictureFile_name,urlpath,rybh);
            	return "{\"error\":\"\",\"msg\":\"上传成功，关闭二维码弹窗，自动刷新图片列表！\","
    			+ "\"initialPreviewConfig\":[{\"caption\":\""+pictureFile_name+"\",\"key\":\""+fold+"/"+dayFold+"/"+newFileName+"@"+guid+"\",\"size\":\""+djlx+"\"}],"
    			+ "\"initialPreview\":[\""+xnlu_path+"/"+fold+"/"+dayFold+"/"+newFileName+"\"]}";
            }
        }
        return "{\"error\":\"上传失败！\"}";
	}
	
	/**
	 * 扫码上传图片的删除图片功能
	 * @return
	 */
	@RequestMapping("/doDelSmsc")
	@ResponseBody
	public Object doDelSmsc(HttpServletRequest request,String key){
		if(request == null){
			return "{\"error\":\"系统掉线，请重新登录！\"}";
		}
		
		String rybh = request.getParameter("rybh");
		String stamp = request.getParameter("stamp");
		
		if(Validate.isNull(stamp) || Validate.isNull(rybh)){
			return "{\"error\":\"参数传入有误！\"}";
		}

		long sjc = new Date(Long.parseLong(stamp)).getTime();//时间戳
		long nowtime = new Date(System.currentTimeMillis()).getTime();
		String timer = PropertiesUtil.getGlobalValueByKey("QRCodeTime");
		long yxsj = Long.parseLong(timer) * 60 * 1000;//有效时间，毫秒
		//System.err.println("sjc:" + sjc + "  nowtime:" + nowtime + "   yxsj:" + yxsj + "   nowtime-sjc:" + (nowtime - sjc));
		if(nowtime - sjc > yxsj){
			return "{\"error\":\"二维码失效，请刷新二维码后重新扫描！\"}";
		}
		
		String[] keys = key.split("@",-1);
		StringBuffer sb =  new StringBuffer();
		sb.append(ResourceBundle.getBundle("global").getString("FileDataPhysicalPath"));
		sb.append("\\");
		sb.append(keys[0].replaceAll("//", "\\"));
		FileUtil.delFile(sb.toString());//从物理路径删除，就是从本地删除实际的文件
		phoneService.doDelSmsc(keys[1],rybh);//从数据库中删除
		return "{\"error\":\"\"}";
	}
	/*****************************扫描二维码上传图片 end***********************************/
//以下 ycc 编写
	//重写登录借口
	/**
	 * 登录
	 * @return
	 */
//	@RequestMapping(value="/Login",produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public Object login(){
//		return ywcservice.login(getPageData());
//	}
	
	/***************************上传专用*******************************/
	@RequestMapping(value = "/sczyff",produces="text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public String sczyff(MultipartHttpServletRequest request) {
		return ywcservice.tpscgetinfo(request);
	}
	//头像上传 
	@RequestMapping(value = "/txsczyff",produces="text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public String txsczyff(MultipartHttpServletRequest request) {
		return jdbxsqservice.txsczyff(request);
	}
	/***************************6.出差申请接口*******************************/
	//（1）、获取人员基本信息
	@RequestMapping(value="/ccsq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccsq(){
		return ywcservice.ccsq(getPageData());
	}
	//（2）、获取项目的基本信息
	@RequestMapping(value="/xmhq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xmhq(){
		return ywcservice.xmhq(getPageData());
	}
	//（3）出差申请提交
	@RequestMapping(value="/ccsqtj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccsqtj(HttpSession session){
		return ywcservice.ccsqtj(getPageData(),session);
	}
	//(4)根据省份查询对应的市区
	@RequestMapping(value="/cityhq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cityhq(){
		return ywcservice.cityhq(getPageData());
	}
	/***************************7.待办业务接口*******************************/
	//（1）、列表接口
	@RequestMapping(value="/dsh",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object dsh(){
		return ywcservice.dsh(getPageData());
	}
	/***************************8.我发起列表*******************************/
	//（1）、我发起列表
	@RequestMapping(value="/wfq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wfq(){
		return ywcservice.wfq(getPageData());
	}
	/***************************9.日常报销申请*******************************/
	//（1）日常报销申请列表
	@RequestMapping(value="/rcbxsqlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rcbxsqlist(){
		return ywcservice.rcbxsqlist(getPageData());
	}
	//（2）报销明细 费用修改接口详情
	@RequestMapping(value="/rcbxfyxg",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rcbxfyxg(){
		return ywcservice.rcbxfyxg(getPageData());
	}
	//（3）表单数据保存提交
	@RequestMapping(value="/rcbxfyxgtj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rcbxfyxgtj(){
		return ywcservice.rcbxfyxgtj(getPageData());
	}
	//（4）结算方式信息获取
	@RequestMapping(value="/rcbxjsfs",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rcbxjsfs(){
		return ywcservice.rcbxjsfs(getPageData());
	}
	//（5）结算方式数据提交
	@RequestMapping(value="/rcbxjsfstj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rcbxjsfstj(HttpSession session){
		return ywcservice.rcbxjsfstj(getPageData(),session);
	}
	//(6)日常报销 结算方式 项目负责人获取
	@RequestMapping(value="/rcbxryxz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rcbxryxz(){
		return ywcservice.rcbxryxz(getPageData());
	}
	//日常报销 流程 弹窗接口  
	@RequestMapping(value="/xjshr",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xjshr(HttpSession session){
		return ywcservice.xjshr(getPageData(),session);
	}
	/***************************10.、审批数据详情页面*******************************/
	//(1）、出差申请 ①审批详情接口
	@RequestMapping(value="/ccsqxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccsqxq(){
		return ywcservice.ccsqxq(getPageData());
	}
	//②、出差申请审批提交接口
	@RequestMapping(value="ccsqsptj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccsqsptj(HttpSession session,OA_SHYJB shyjb){
		return ywcservice.ccsqsptj(session,getPageData(),shyjb);
	}
	//（2）日常报销审批详情  ①审批详情接口
	@RequestMapping(value="/rcbxxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rcbxxq(){
		return ywcservice.rcbxxq(getPageData());
	}
	//②、日常报销审批提交接口
	@RequestMapping(value="/rcbxsptj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object rcbxsptj(HttpSession session,OA_SHYJB shyjb){
		return ywcservice.rcbxsptj(session,getPageData(),shyjb);
	}
	//(3)、新流程 审核弹窗 选择人员 判断 是否弹窗
	@RequestMapping(value="/xjshrsh",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xjshrsh(HttpSession session){
		return ywcservice.xjshrsh(getPageData(),session);
	}
	/***************************11.公务接待申请*******************************/
	//（1）、获取接待部门信息
	@RequestMapping(value="/jdsq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdsq(){
		return ywcservice.jdsq(getPageData());
	}
	/***************************12.差旅费申请*******************************/
	//（1）、获取出差业务
	@RequestMapping(value="/ccbxsqlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccbxsqlist(){
		return ywcservice.ccbxsqlist(getPageData());
	}
	//(2) 出差业务 搜索
	@RequestMapping(value="/ccbxsearch",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccbxsearch(){
		return ywcservice.ccbxsearch(getPageData());
	}
	//(3) 出差业务报销详情填写 第二步
	@RequestMapping(value="/ccsqbxxqtx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccsqbxxqtx(){
		return ywcservice.ccsqbxxqtx(getPageData());
	}
	//(3) 出差业务报销 第二布 保存
	@RequestMapping(value="/ccbxtwotj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccbxtwotj(){
		return ywcservice.ccbxtwotj(getPageData());
	}
	//(4)出差报销 第三部 获取信息
	@RequestMapping(value="/ccbxthree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccbxthree(){
		return ywcservice.ccbxthree(getPageData());
	}
	//(5)出差报销 第四部 保存信息 并开启流程
	@RequestMapping(value="/ccbxthreetj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ccbxthreetj(HttpSession session){
		return ywcservice.ccbxthreetj(getPageData(),session);
	}
	//出差报销审批提交接口
	@RequestMapping(value="clfbxsptj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object clfbxsptj(HttpSession session,OA_SHYJB shyjb){
		return ywcservice.clfbxsptj(session,getPageData(),shyjb);
	}
	/***************************13.接待报销申请列表接口*******************************/
	//请求数据
	@RequestMapping(value="jdbxsqlist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdbxsqlist(){
		return jdbxsqservice.jdbxsqlist(getPageData());
	}
	//接待报销申请列表搜索接口
	@RequestMapping(value="jdbxsqlistss",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdbxsqlistss(){
		return jdbxsqservice.jdbxsqlistss(getPageData());
	}
	//接待报销申请第二步接口
	@RequestMapping(value="jdbxsqtwo",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdbxsqtwo(){
		return jdbxsqservice.jdbxsqtwo(getPageData());
	}
	//接待报销申请第二步信息提交接口
	@RequestMapping(value="jdbxsqtwotj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdbxsqtwotj(){
		return jdbxsqservice.jdbxsqtwotj(getPageData());
	}
	//接待报销申请第三步信息提交接口
	@RequestMapping(value="jdbxsqthreetj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdbxsqthreetj(HttpSession session){
		return jdbxsqservice.jdbxsqthreetj(getPageData(),session);
	}
	//接待报销审批详情页面
	@RequestMapping(value="/jdbxxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdbxxq(){
		return jdbxsqservice.jdbxxq(getPageData());
	}
	//接待报销审批提交接口
	@RequestMapping(value=" jdbxsptj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdbxsptj(HttpSession session,OA_SHYJB shyjb){
		return jdbxsqservice.jdbxsptj(session,getPageData(),shyjb);
	}
	//公务接待 事前审批详情 
	@RequestMapping(value="/gwjdspxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object gwjdspxq(){
		return jdbxsqservice.gwjdspxq(getPageData());
	}
	//公务接待事前审批 提交接口
	@RequestMapping(value=" gwjdsptj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object gwjdsptj(HttpSession session,OA_SHYJB shyjb){
		return jdbxsqservice.gwjdsptj(session,getPageData(),shyjb);
	}
	//公务接待事前审批  获取人员
	@RequestMapping(value="/jdsqbxry",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdsqbxry(){
		return jdbxsqservice.jdsqbxry(getPageData());
	}
	//公务接待 事前审批 获取 部门 递归
	@RequestMapping(value="/jdsqjdbm",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jdsqjdbm(){
		return jdbxsqservice.jdsqjdbm(getPageData());
	}
	//公务接待事前申请 提交保存
	@RequestMapping(value="/gwjdsqtj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object gwjdsqtj(HttpSession session){
		return jdbxsqservice.gwjdsqtj(getPageData(),session);
	}
	/***************************14.借款流程 申请*******************************/
	//（1）、获取项目的基本信息
	@RequestMapping(value="/jksqxmhq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jksqxmhq(){
		return ywcservice.xmhq(getPageData());
	}
	//(2)借款 对私支付  结算方式 人员信息获取  lx  0.项目负责人  1.个人  2.其他人
	@RequestMapping(value="/jksqryxxhq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jksqryxxhq(){
		return ywcservice.rcbxryxz(getPageData());
	}
	//银行卡 list
	@RequestMapping(value="/yhklist",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object yhklist(){
		return ywcservice.yhklist(getPageData());
	}
	//根据部门查询部门下的人 
	@RequestMapping(value="/choosepeople",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object choosepeople(){
		return ywcservice.choosepeople(getPageData());
	}
	//(3)借款 对公支付  结算方式 对方单位和对方银行信息 获取
	@RequestMapping(value="/jksqdgzfhq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jksqdgzfhq(){
		return ywcservice.jksqdgzfhq(getPageData());
	}
	//(4)借款申请提交
	@RequestMapping(value="/jksqtj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jksqtj(HttpSession session){
		return ywcservice.jksqtj(getPageData(),session);
	}
	//(5)借款审批详情
	@RequestMapping(value="/jkspxq",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jkspxq(){
		return ywcservice.jkspxq(getPageData());
	}
	//(6)借款审批提交接口
	@RequestMapping(value="jksptj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object jksptj(HttpSession session,OA_SHYJB shyjb){
		return ywcservice.jksptj(session,getPageData(),shyjb);
	}
	/*********************15.我的薪酬**************************/
	//(1)我的薪酬
	@RequestMapping(value="wdxc",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wdxc(){
		return ywcservice.wdxc(getPageData());
	}
}