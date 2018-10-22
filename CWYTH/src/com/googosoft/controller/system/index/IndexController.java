package com.googosoft.controller.system.index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.LicenseUtil;
import com.googosoft.commons.PropertiesUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.ShMap;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.service.system.index.DeskService;
import com.googosoft.service.system.index.IndexService;
import com.googosoft.service.system.menu.MenuService;
import com.googosoft.service.system.user.UserService;
import com.googosoft.service.systemset.cssz.DlxxService;
import com.googosoft.service.systemset.xtsz.BzxxService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Const;
import com.googosoft.util.MD5;
import com.googosoft.util.PageData;
import com.googosoft.util.PageListUtil;
import com.googosoft.util.Validate;


@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{
	
	@Resource(name="indexService")
	private IndexService indexService;
	
	@Resource(name="deskService")
	private DeskService deskService;
	
	@Resource(name="bzxxService")
	private BzxxService bzxxService;
	
	@Resource(name="dlxxService")
	private DlxxService dlxxService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="menuService")
	private MenuService menuService;
	
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="jdbcTemplate")
	public DBHelper db;
	
	@Resource(name="pzlrService")
	PzlrService pzlrService;
	/**
	 * 获取柱状图数据
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getZztData123",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getZztData() throws Exception {
		PageData pd = this.getPageData();//浏览器不兼容时，继续访问标志
		String sbnd= pd.getString("sbnd");
		List list=deskService.getZztData(sbnd);
		return new Gson().toJson(list);
		//return list;
	}
	//初始化登录人员信息
	public void iniLogin(ModelAndView mv) {		
		mv.addObject("loginId",LUser.getGuid());			
		mv.addObject("ryxm",LUser.getRyxm());			
	}
	
	/**
	 * 登录成功后的跳转页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login_toIndex",produces = "text/html;charset=UTF-8")
	public ModelAndView login_toIndex(HttpSession session){
		ModelAndView mv = this.getModelAndView();
		if(!LicenseUtil.doLicenseCheck()){//检测是否在试用期内
			mv.addObject("licenseInfo","您好，软件已过试用期！");
		}
		String rybh = LUser.getRybh();
		String ryxm=LUser.getRyxm();
		String guid = LUser.getGuid();
		//
		PageData pd = this.getPageData();//浏览器不兼容时，继续访问标志
		String flag = pd.getString("flag");
		mv.addObject("flag",flag);
		Map<?, ?> map = dlxxService.getXtcs();
		mv.addObject("sysname",map.get("XTMC"));//系统名称
		mv.addObject("schoolname", map.get("XXMC"));//学校名称
		String wdsl =deskService.getWdsl(rybh);//未读通知公告数量
		mv.addObject("wdsl", wdsl);
		List<?> wdxx =deskService.getWdxx(rybh);//未读通知公告信息
		mv.addObject("wdxx", wdxx);
		
		List tzList = deskService.getTzxx(guid);//审核信息
		mv.addObject("tzxx", tzList);
		//审核权限
		mv.addObject("shqx", deskService.getShqx(rybh));
		List<?> shxx = deskService.getShxx(guid);//审核信息
		mv.addObject("shsl", shxx.size());
		mv.addObject("shxx", shxx);
		//账套信息
		mv.addObject("ztxx",deskService.getZtxx());
		
		//
		mv.addObject("appDisplay", PropertiesUtil.getGlobalValueByKey("appDisplay"));//【下载手机app】显示控制  1为正常，0为禁用
		mv.addObject("reLoginDisplay", PropertiesUtil.getGlobalValueByKey("reLoginDisplay"));//【重新登录】按钮显示控制  1为正常，0为禁用
		mv.addObject("rybh", rybh);
		mv.addObject("loginName", ryxm);
		mv.addObject("ryid",guid);
		
		
		String grtx = userService.getGrtx(rybh);//个人头像地址
		mv.addObject("grtx", Validate.isNullToDefaultString(grtx, ""));
		mv.addObject("websocket_uri", PropertiesUtil.getGlobalValueByKey("WebSocket_Uri"));
		
		mv.addObject("ztmc",Constant.getztmc(session));
		//用户屏幕宽度
		mv.addObject("userScreenWidth",pd.getString("userScreenWidth"));
		//跳转到index页面
		mv.setViewName("system/index/index");
		
		
		//mv.setViewName("system/index/indexOld");  /*****  旧版menu页面   *****/
		return mv;
	}
	
	/**
	 * 登录成功后的跳转页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login_jztab",produces = "text/html;charset=UTF-8")
	public ModelAndView login_jztab(HttpSession session){
		ModelAndView mv = this.getModelAndView();
		
		mv.setViewName("system/index/jztab");
		
		
		//mv.setViewName("system/index/indexOld");  /*****  旧版menu页面   *****/
		return mv;
	}
	/**
	 * 得到通知公告
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTzggList")
	@ResponseBody
	public Object getTzggList() throws Exception {
	
		PageData pd = this.getPageData();
		String js= Validate.isNullToDefaultString(pd.getString("js"), "x");
		System.out.println("pd++++++++++++++++++++++++++++++++++++++++++++++++++++"+pd);
		List list = indexService.getTzgg(js);
		return list;
	}
	
	/**
	 *加载左侧导航菜单JSON数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getMenuList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getMenuList(){
		List<?> list = menuService.findMkbList(LUser.getRybh());
		Gson gson = new Gson();
		String json = gson.toJson(list);
		logger.debug("首页导航栏数据："+json);
		return json;
	}
	
	/**
	 *加载左侧导航菜单JSON数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getMenuListnew",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getMenuListnew(){
		List<?> list = menuService.getMenuListnew(LUser.getRybh());
		Gson gson = new Gson();
		String json = gson.toJson(list);
		logger.debug("首页导航栏数据："+json);
		return json;
	}
	/**
	 * 从index.jsp页面中的iframe打开desk.jsp页面
	 * @return
	 */
	@RequestMapping(value="/goMain",produces = "text/html;charset=UTF-8")
	public ModelAndView goMain(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();	
		String yz=MD5.getPassword(rybh+""+new SimpleDateFormat("yyyyMMddHH").format(new Date()));
		//如果事前审批没有启用，则去除该模块
		String filter = "";
		if(!indexService.getSqspSfqy()) {
			filter += "'13'";
		}
		List<Map<String, Object>> zs_list = indexService.getBsdtMkList(rybh, filter);
		String userScreenWidth = pd.getString("userScreenWidth");//获取用户屏幕宽度，默认是1400
		int width = Validate.isNullOrEmpty(userScreenWidth)?1400:Integer.parseInt(userScreenWidth);
		int zssl = 0;//每页展示模块数量
		int page_count = 1;//页数
		String li_width = "";
		if(width>=1800) {
			zssl = 15;
			li_width = "20%";
		}else if(width>=1400) {
			zssl = 12;
			li_width = "25%";
		}else if(width>=1000){
			zssl = 9;
			li_width = "33.33%";
		}else {
			zssl = 6;
			li_width = "50%";
		}
		page_count = zs_list.size()/zssl;
		page_count = zs_list.size()%zssl == 0 ? page_count : page_count+1;
		List<Map<String, Object>> bsdtPageList = new ArrayList<>();
		if(page_count == 1) {
			Map<String, Object> bsdtPage = new HashMap<>();
			bsdtPage.put("zsList", zs_list);
			bsdtPage.put("liWidth", li_width);
			bsdtPageList.add(bsdtPage);
		}else {
			for(int i=1;i<=page_count;i++) {
				Map<String, Object> bsdtPage = new HashMap<>();
				if(i==1) {
					bsdtPage.put("zsList", zs_list.subList((i-1)*zssl, i*zssl));
				}else if(i==page_count){
					bsdtPage.put("zsList", zs_list.subList((i-1)*zssl, zs_list.size()));
				}else {
					bsdtPage.put("zsList", zs_list.subList((i-1)*zssl, i*zssl));
				}
				bsdtPage.put("liWidth", li_width);
				bsdtPageList.add(bsdtPage);
			}
		}
		mv.addObject("bsdtPageCount",page_count);
		mv.addObject("bsdtPageList",bsdtPageList);
		//
		iniLogin(mv);
  
		String jsbh=deskService.getJsbh(rybh);
		String type=LUser.getType();
		//我的薪酬
		mv.addObject("xzList", indexService.getXzList(LUser.getRygh()));  
		//我的项目
		mv.addObject("wdxmList", indexService.getWdxmList(LUser.getRybh()));
		if(jsbh.contains("08")){
			Map xmmap= indexService.getxm();
			mv.addObject("xmzx", xmmap);
			mv.setViewName("system/index/deskxz");  
		}else if(jsbh.contains("11")){
			Map map= indexService.getjrbx();
			mv.setViewName("system/index/deskcw"); 
			Map<?, ?> dscpz = indexService.getDscpz();
			String dscpzsl = String.valueOf(dscpz.get("dscpzsl")) ;
			mv.addObject("dscpzsl",dscpzsl);
			mv.addObject("rybh",rybh);
			mv.addObject("jrbx", map);
		}else if("T".equals(type)){
			Map wdbxmap= indexService.getwdbx();
			mv.setViewName("system/index/deskteacher");
			
			mv.addObject("rybh",rybh);
			mv.addObject("wdbx", wdbxmap);
			mv.addObject("yz", yz);
			
		}else if("S".equals(type)){
			mv.setViewName("system/index/deskstudent"); 
		}else{
			
			mv.setViewName("system/index/deskteacher");  
		}
		return mv;
	}
	
	/**
	 * 用户重新登录
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/logout",produces = "text/html;charset=UTF-8")
	public ModelAndView logout()
	{
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		//shiro销毁登录
		currentUser.logout();//任何现有的Session 都将会失效，而且任何身份都将会失去关联 	
		return new ModelAndView("redirect:/login/login");  
	}
	/**
	 * 用户注销
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/loginout",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object loginout(){
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		//shiro销毁登录
		currentUser.logout();
		return "{success:true,msg:'系统退出成功！'}";
	}
	
	/**
	 * 用户掉线
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/offline",produces = "text/html;charset=UTF-8")
	public ModelAndView offline() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("error/offline");
		return mv;
	}
	
	/**
	 * 帮助框架页
	 */
	@RequestMapping(value="/goHelpPage",produces = "text/html;charset=UTF-8")
	public ModelAndView goHelpPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/index/help_frm");
		return mv;
	}
	/**
	 * 跳转到系统帮助页面
	 * @return
	 */
	@RequestMapping(value="/goHelpListPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goHelpListPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String bzdms = indexService.getBzdm();
		String bzdm = Validate.isNullToDefault(pd.getString("bzdm"),bzdms) + "";
		Map map = bzxxService.getObjectByBh(bzdm);
		mv.addObject("Content",map.get("BZXX"));
		mv.addObject("JDR",map.get("JDR"));
		mv.addObject("JDRQ",map.get("JDRQ"));
		mv.addObject("Content",map.get("BZXX"));
		mv.addObject("bzxx", map);
		mv.setViewName("system/index/help_list");
		return mv;
	}
	/**
	 * 设置选项页面
	 * @return
	 */
	@RequestMapping(value="/goSzxxPage",produces = "text/html;charset=UTF-8")
	public ModelAndView goSzxxPage(){
		ModelAndView mv = this.getModelAndView();
		Map<String,Object> map = userService.getObjectById(LUser.getRybh());
		mv.addObject("szxx", map);
		mv.setViewName("system/index/szxx");
		return mv;
	}
	/**
	 * 保存个人基本信息（设置选项页面）
	 */
	@RequestMapping(value="/doSaveSzxx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveSzxx(){
		PageData pd = this.getPageData();
		String rownums = pd.getString("rownums");
		String lxdh = Validate.isNullToDefaultString(pd.getString("lxdh"),"");
		String qq = pd.getString("qq");
		String mail = pd.getString("mail");
		String mmwt = pd.getString("mmwt");
		String mmda = pd.getString("mmda");
		boolean result = userService.doSaveSzxx(rownums,lxdh,qq,mail,LUser.getRybh(),mmwt,mmda);
		if(result){
			if(Validate.isNull(rownums)){
				rownums = pageService.getSingleValue("select nvl((select rownums from zc_sys_xtb where rownum = 1),100) rownums from dual");
			}
			
			//直接更新session中值
			Session session = LUser.getSession();
			GX_SYS_RYB luser = (GX_SYS_RYB)session.getAttribute(Const.SESSION_USER);
			luser.setRownums(Integer.parseInt(rownums));
			luser.setLxdh(lxdh);
			session.setAttribute("rownum", rownums);//注意逐步替换上述方式
			session.setAttribute(Const.SESSION_USER, luser);

			return "{success:true,msg:'信息保存成功！'}";
		}else{
			return "{success:false,msg:'信息保存失败！'}";
		}
	}
	/**
	 * 修改密码（设置选项页面）
	 */
	@RequestMapping(value="/doUpdPwd",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdPwd(){
		PageData pd = this.getPageData();
		String oldpwd = pd.getString("passwordo");
		String newpwd = pd.getString("passwordn");
		String rybh=LUser.getRybh();
		int result= userService.doUpdPwd(oldpwd,newpwd,rybh);
		if(result==1)
		{
			return "{success:true,msg:'密码修改成功！'}";
		}
		else if(result==0)
		{
			return "{success:false,msg:'旧密码输入错误，请重新输入！'}";
		}
		else if(result==2){
			return "{success:false,msg:'不允许使用初始密码，请重新输入新密码！'}";
		}
		else
		{
			return "{success:false,msg:'密码修改失败，请重试！'}";
		}
	}
	
	/**
	 * 修改密码（登录页面，找回密码）
	 */
	@RequestMapping(value="/doUpdPwdByZhmm",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdPwdByZhmm(){
		PageData pd = this.getPageData();
		String rygh = pd.getString("rygh");
		String xmm = pd.getString("xmm");
		int result= userService.doUpdPwdByZhmm(rygh,xmm);
		if(result==1){
			return "{\"success\":\"true\",\"msg\":\"密码修改成功！\"}";
		}else{
			return "{\"success\":\"true\",\"msg\":\"密码修改失败！\"}";
		}
	}
	/**
	 * 搜索页
	 * @return
	 */
	@RequestMapping(value="/goSearchPage",produces = "text/html;charset=UTF-8")
	public ModelAndView goSearchPage(){
		ModelAndView mv = this.getModelAndView();
		String keyword=this.getPageData().getString("keyword");
		mv.addObject("keyword", keyword);
		mv.setViewName("system/index/search");
		return mv;
	}
	/**
	 * 首页页面信息搜索（资产信息）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageSerList",produces = "text/html;charset=UTF-8")
	@ResponseBody
		public Object getPageSerList() throws Exception{
		PageData pd = this.getPageData();
		String searchword = pd.getString("keyword");    
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("z.mc ,z.ztbz,z.xz,z.yqbh,z.yqmc");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("z.yqbh");//主键
		pageList.setStrWhere(" and ztbz = '" + StateManager.ZCJZ_CW_TG + "' and xz not in(select dm from gx_sys_dmb where zl='41') and z.yqmc like '%"+searchword+"%' ");
		pageList.setTableName("(select yqmc,ztbz,xz,yqbh,'<b>资产名称：</b>【<b>'||yqmc||'</b>】'||'</br>'||'该资产的资产编号：<b>'||yqbh||'</b>；分类号：<b>'||flh||'</b>；分类名称：<b>'||flmc||'</b>；规格情况：<b>'||(decode(gg,null,'暂无',gg))||'</b>；型号情况：<b>'||(decode(xh,null,'暂无',xh) )||'</b>' mc from zc_zjb) z");//表名
		pageList.setHj1("");//合计
		pd.remove("search[value]");
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 首页页面信息搜索（资产数量）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAssetsnum",produces = "text/html;charset=UTF-8")
	@ResponseBody
		public String getAssetsnum() throws Exception{
		PageData pd = this.getPageData();
		String searchword = pd.getString("keyword");    
		int val = indexService.getAssetsNum(searchword);
		return String.valueOf(val);
	}
	
	/**
	 * 验证单据是否存在
	 * @return
	 */
	@RequestMapping(value="/check",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String check(){
		PageData pd = this.getPageData();
		String cnt = deskService.getInfoByCount(pd);
		String tj = pd.getString("cxtj");
		if(Validate.isNull(cnt)){
			return "{\"success\":false,\"msg\":\"查询条件错误！\"}";
		}
		else if("0".equals(cnt)){
			if("txlcx".equals(tj)){
				return "{\"success\":false,\"msg\":\"请输入正确的" + pd.getString("title") + "的信息！\"}";
			}else{
				return "{\"success\":false,\"msg\":\"没有查询到" + pd.getString("title") + "是" + pd.getString("dbh") + "的信息，请确认是否对该信息具有管理权限！\"}";
			}
		}
		else{
			if("tmdy".equals(pd.getString("cxtj"))){
				String[] dbharr = pd.getString("dbh").split(",");
				int i = dbharr.length - Integer.parseInt(cnt);
				if(i == 0){
					return "{\"success\":true}";
				}
				else{
					return "{\"success\":false,\"msg\":\"没有查询到" + i + "条信息，请确认是否对该信息具有管理权限！\"}";
				}
			}
			else {
				return "{\"success\":true}";
			}
		}
	}
	/**
	 * 首页通讯录查询列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getTxlList",produces = "text/html;charset=UTF-8")
	@ResponseBody
		public Object getTxlList(){
		PageData pd = this.getPageData();
		String searchword = pd.getString("search[value]");
		String key = pd.getString("key");
		PageList pageList = new PageList();
		pageList.setSqlText("*");
		pageList.setKeyId("gid");//主键
		pageList.setStrWhere(" and nvl(zt,'1') = '1' and (rybh like '%" + searchword + "%' or xm like '%" + searchword + "%') ");
		if(Validate.noNull(key)){
			pageList.setStrWhere("and (rybh like '%" + key + "%' or xm like '%" + key + "%') ");
		}
		pageList.setTableName("zc_txl");//表名
		pd.remove("search[value]");
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 进度跟踪信息弹窗
	 * @return
	 */
	@RequestMapping(value="/jdgz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getJdgzWindow(){
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		String userId = LUser.getRybh();
		
		pageList.setSqlText("id,bh,mc,lx,jd,css,rq");
		pageList.setKeyId("id");//主键
		
		pageList.setTableName("(select bh||mc||lx||to_char(rq,'yyyymmddhhmiss')||jd id,bh,mc,lx,jd,css,rq from (" + deskService.getJdgzSql(userId) + ")) j");
		PageListUtil.setPgxxList(pd,pageList,"J");
		
		List list = pageService.getPageList(pageList);
		
	    //pageList = pageService.findWindowList(pd,pageList,"J");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getRecordCnt()+"",pageList.getRecordCnt()+"",gson.toJson(list));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取审核信息
	 * @return
	 */
	@RequestMapping(value="/getShxx",produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object getShxx(){
		Map map = ShMap.shmaps.get(LUser.getRybh());
		if(Validate.isNull(map) || map.isEmpty()){
			return "{\"success\":false}";
		}
		else{
			String sl = Validate.isNullToDefaultString(map.get("shsl"),"0");//现在待审核的数量
			int shsl = Integer.parseInt(sl);
			if(shsl == 0){
				return "{\"success\":true,\"shsl\":0}";
			}
			else if(sl.equals(this.getPageData().get("ysl"))){//页面上显示的数量跟现在待审核的数量相等
				return "{\"success\":true,\"shsl\":" + sl + "}";
			}
			else{
				List list = (List)map.get("shxx");
				int end = 3;
				if(shsl < 3){
					end = shsl;
				}
				StringBuffer shxx = new StringBuffer();
				Set<Entry> set;
				for(int i = 0; i < end; i++){
					set = ((Map)list.get(i)).entrySet();
					shxx.append("{");
					for(Entry entry : set){
						shxx.append("\"" + entry.getKey() + "\":\"" + Validate.isNullToDefault(entry.getValue(), "") + "\",");
					}
					shxx.deleteCharAt(shxx.length() - 1);
					shxx.append("},");
				}
				shxx.deleteCharAt(shxx.length() - 1);
				return "{\"success\":true,\"shsl\":" + shsl + ",\"shxx\":[" + shxx.toString() + "]}";
			}
		}
	}
	
	/**
	 * 从index.jsp页面中打开allYw.jsp页面
	 * @return
	 */
	@RequestMapping(value="/goAllyw",produces = "text/html;charset=UTF-8")
	public ModelAndView goAllyw(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/index/allYw");   
		return mv;
	}
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append("	(select q.*  from (  select a.pzzt, (select zy from cw_pzlrmxb b"
				+ "   where b.pzbh = a.guid and rownum <= 1) as zy, (case a.pzzt  when '00' then  '已保存' when '01' then '已复核' when '02' then '已记账'when '99' then '已结账' else '' end) pzztmc,"
	          + "  to_char(a.pzrq, 'yyyy-MM-dd') as pzrq,l.lxbh,l.lxmc PZLXMC, a.guid,(select count(0) from cw_pzlrmxb b where b.pzbh = a.guid) as flts,a.fhr,(select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r "
	           + "  where r.guid = a.fhr) fhrmc, a.zdr,a.jzr,(select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r where r.guid = a.jzr) jzrmc,a.fjzs,(select xmbh from cw_pzlrmxb b where b.pzbh = a.guid "
	          + "  and rownum <= 1) as xmbh,(select bmbh from cw_pzlrmxb b where b.pzbh = a.guid and rownum <= 1) as bmbh,(select jjfl from cw_pzlrmxb b where b.pzbh = a.guid and rownum <= 1) as jjfl,"
	          + "  to_char(round(a.dfjehj, 2), 'fm9999999990.00') as dfjehj,to_char(round(a.jfjehj, 2), 'fm9999999999990.00') as jfjehj,(select '(' || j.rybh || ')' || j.xm from gx_sys_ryb j where j.guid = a.zdr) zdrmc,"
	         + "  (case nvl(a.fhr, 0) when '0' then '已记账' else '已复核' end) sffh,a.pzbh,(select kmbh from cw_pzlrmxb b where b.pzbh = a.guid and rownum <= 1) as kmbh"
	         + "  from Cw_pzlrzb a left join cw_pzlxb l on l.guid = a.pzz where 1 = 1 order by pzscsj desc) q where 1=1 and rownum < 7");

		tablename.append(" ) k ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere("");
		pageList.setHj1("");//合计
		Gson gson = new Gson();
		pageList = pageService.findPageList(pd,pageList);
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/*
	 * 今日报销
	 */
	@RequestMapping(value = "/getjrbxPageList")
	@ResponseBody
	public Object getbxywcxPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String sjqj=pd.getString("sjqj");
		String jsqj = "";
		String jsqj1 = "";
		String jsqj2 = "";
		if(sjqj.equals("jr")){
			 jsqj="to_char(t.CZRQ, 'yyyy-mm-dd') =to_char(sysdate, 'yyyy-mm-dd')";
			 jsqj1="m.czrq = to_char(sysdate, 'yyyy-mm-dd')";
			 jsqj2="to_char(n.CZRQ, 'yyyy-mm-dd') =to_char(sysdate, 'yyyy-mm-dd')";
		}
		if(sjqj.equals("bz")){
			 jsqj="trunc(sysdate,'day')< = t.CZRQ and t.CZRQ< = trunc(sysdate,'day')+(INTERVAL '6' DAY)";
			 jsqj1="trunc(sysdate,'day')< = to_date(m.CZRQ,'yyyy-mm-dd') and to_date(m.CZRQ,'yyyy-mm-dd')< = trunc(sysdate,'day')+(INTERVAL '6' DAY)";
			 jsqj2="trunc(sysdate,'day')< = n.CZRQ and n.CZRQ< = trunc(sysdate,'day')+(INTERVAL '6' DAY)";
		}
		if(sjqj.equals("by")){
			 jsqj="trunc(sysdate,'month')< = t.CZRQ and t.CZRQ< = last_day(sysdate)";
			 jsqj1="trunc(sysdate,'month')< = to_date(m.CZRQ,'yyyy-mm-dd') and to_date(m.CZRQ,'yyyy-mm-dd')< = last_day(sysdate)";
			 jsqj2="trunc(sysdate,'month')< = n.CZRQ and n.CZRQ< = last_day(sysdate)";
		}
		if(sjqj.equals("bjd")){
			 jsqj="trunc(sysdate,'q')< = t.CZRQ and t.CZRQ<  trunc(trunc(sysdate,'q'))+(INTERVAL '3' MONTH)";
			 jsqj1="trunc(sysdate,'q')< = to_date(m.CZRQ,'yyyy-mm-dd') and to_date(m.CZRQ,'yyyy-mm-dd')< trunc(trunc(sysdate,'q'))+(INTERVAL '3' MONTH)";
			 jsqj2="trunc(sysdate,'q')< = n.CZRQ and n.CZRQ<  trunc(trunc(sysdate,'q'))+(INTERVAL '3' MONTH)";
		}
		if(sjqj.equals("bn")){
			 jsqj=" trunc(sysdate,'year')< = t.CZRQ and t.CZRQ< trunc(trunc(sysdate,'year'))+(INTERVAL '1' YEAR)";
			 jsqj1="trunc(sysdate,'year')< = to_date(m.CZRQ,'yyyy-mm-dd') and to_date(m.CZRQ,'yyyy-mm-dd')< trunc(trunc(sysdate,'year'))+(INTERVAL '1' YEAR)";
			 jsqj2=" trunc(sysdate,'year')< = n.CZRQ and n.CZRQ< trunc(trunc(sysdate,'year'))+(INTERVAL '1' YEAR)";
		}
//		String zl = pd.getString("treedwbh");
		sqltext.append(" * ");
		  tablename.append("(select t.guid, t.shzt, t.jdsy as bxsy,(select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm = t.shzt) as shztmc,t.djbh,'公务接待报销' as bxlx,t.BXRY as bxr,((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r  where r.rybh =t.BXRY)) as bxrmc,"
					+ "('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(t.CZRQ,'yyyy-mm-dd')as czrq,to_char(T.bxje,'FM9999999999999990.00') as BXZJE "
					+ " from Cw_gwjdfbxzb t where t.shzt='8' and "+jsqj
					+ " union "
					+ " select m.guid, m.shzt, m.CCSY AS BXSY,(select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm=m.shzt)as shztmc,"
					+ "  m.djbh, '差旅费报销' as bxlx,  m.sqr as bxr, (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = m.sqr) as bxrmc,"
					+ "  ('(' || (select r.dwbh from gx_sys_ryb r where r.guid = m.sqr) || ')' ||(select d.mc from gx_sys_dwb d"
					+ " where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = m.sqr))) as szbmMC, m.CZRQ,  to_char(m.bxzje, 'FM9999999999999990.00')  AS bxzje"
					+ "  from Cw_clfbxzb m where m.shzt='8' and "+jsqj1
					+ " union "
					+ "  select n.guid, n.shzt, n.bxsy,(select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm=n.shzt)as shztmc,"
					+ " n.djbh, '日常报销' as bxlx, n.bxr, ('(' || (select r.rybh from gx_sys_ryb r where r.guid=n.bxr) || ')' ||(select r.xm from gx_sys_ryb r where r.guid = n.bxr)) as bxrmc,"
					+ " ((select '('||r.dwbh||')'||r.mc from gx_sys_dwb r where r.dwbh = n.szbm)) as szbmmc,"
					+ " to_char(czrq, 'yyyy-mm-dd') as czrq, to_char(n.bxzje, 'FM9999999999999990.00') as bxzje from Cw_rcbxzb n where n.shzt='8' and "+jsqj2+")k");


		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere("and rownum <= 6"); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}

	
	@RequestMapping(value = "/getPagexzList")
	@ResponseBody
	public Object getPagexzList() throws Exception {
		PageData pd = this.getPageData();
		
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" (  select t.*, decode(nvl(t.ysje - syje,0),0,'0.00',(to_char(round(t.ysje - syje,2),'fm999999999999999999990.00'))) as xzye,to_char(((t.ysje-syje)/ysje)*100,'FM9999999999999990.00')|| '%' as dd from ( "
				+ " select count(xmbh) as sl, "
	            + " bmbh, "
	            + " (select mc from gx_sys_dwb dw  "
	            + " where dw.dwbh = x.bmbh) as bmbh1, "
	            + "  decode(nvl( sum(x.syje),0),0,'0.00',(to_char(round(sum(x.syje),2),'fm999999999999999999990.00')))  ysje, "
	            + " decode(nvl( sum(x.syje),0),0,'0.00',(to_char(round(sum(x.syje),2),'fm999999999999999999990.00'))) syje "
	            + " from CW_XMJBXXB x   group by bmbh)t ");

		tablename.append(" ) k ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("sl");//主键
		//设置WHERE条件
		pageList.setStrWhere("and rownum <= 6");
		pageList.setHj1("");//合计
		Gson gson = new Gson();
		pageList = pageService.findPageList(pd,pageList);
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	
	

	/*
	 * 我的报销
	 */
	@RequestMapping(value = "/getwdbxPageList")
	@ResponseBody
	public Object getwdbxPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String rybh=CommonUtils.getRybh();
//		String zl = pd.getString("treedwbh");
		sqltext.append(" * ");
		  tablename.append("(select t.guid,decode(t.shzt,'0','未提交','1','财务预审','2','科研处负责人审核','3','部门负责人审核','4','财务负责人审核','5','部门分管领导审核','6','财务分管领导审核','7','校长审核','8','审核通过','9','财务预审退回','10','科研处负责人退回','11','部门负责人退回','12','财务负责人退回','13','部门分管领导退回','14','财务分管领导退回','15','校长退回') as shzt,"
		  		    + " (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm = t.shzt) as shztmc,t.djbh,'公务接待报销' as bxlx,t.BXRY as bxr,((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r  where r.rybh = substr(t.BXRY,2,6))) as bxrmc,"
					+ "('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(t.CZRQ,'yyyy-mm-dd')as czrq,to_char(T.bxje,'FM9999999999999990.00') as BXZJE "
					+ " from Cw_gwjdfbxzb t where t.shzt='8' and substr(t.BXRY,2,6)='"+rybh+"'"
					+ " union "
					+ " select m.guid,decode(m.shzt,'0','未提交','1','财务预审','2','科研处负责人审核','3','部门负责人审核','4','财务负责人审核','5','部门分管领导审核','6','财务分管领导审核','7','校长审核','8','审核通过','9','财务预审退回','10','科研处负责人退回','11','部门负责人退回','12','财务负责人退回','13','部门分管领导退回','14','财务分管领导退回','15','校长退回') as shzt, " +
					"(select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm=m.shzt)as shztmc,"
					+ "  m.djbh, '差旅费报销' as bxlx,  m.sqr as bxr, (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.guid = m.sqr) as bxrmc,"
					+ "  ('(' || (select r.dwbh from gx_sys_ryb r where r.guid = m.sqr) || ')' ||(select d.mc from gx_sys_dwb d"
					+ " where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = m.sqr))) as szbmMC, m.CZRQ,  to_char(m.bxzje, 'FM9999999999999990.00')  AS bxzje"
					+ "  from Cw_clfbxzb m where m.shzt='8' and (select guid from gx_sys_ryb where rybh='"+rybh+"') = m.sqr"
					+ " union "
					+ "  select n.guid,decode(n.shzt,'0','未提交','1','财务预审','2','科研处负责人审核','3','部门负责人审核','4','财务负责人审核','5','部门分管领导审核','6','财务分管领导审核','7','校长审核','8','审核通过','9','财务预审退回','10','科研处负责人退回','11','部门负责人退回','12','财务负责人退回','13','部门分管领导退回','14','财务分管领导退回','15','校长退回') as shzt," +
					" (select d.mc from gx_sys_dmb d where d.zl = 'djshzt' and d.dm=n.shzt)as shztmc,"
					+ " n.djbh, '日常报销' as bxlx, n.bxr, ('(' || (select r.rybh from gx_sys_ryb r where r.guid=n.bxr) || ')' ||(select r.xm from gx_sys_ryb r where r.guid = n.bxr)) as bxrmc,"
					+ " ((select '('||r.dwbh||')'||r.mc from gx_sys_dwb r where r.dwbh = n.szbm)) as szbmmc,"
					+ " to_char(czrq, 'yyyy-mm-dd') as czrq, to_char(n.bxzje, 'FM9999999999999990.00') as bxzje from Cw_rcbxzb n where n.shzt='8' and (select guid from gx_sys_ryb where rybh='"+rybh+"') = n.bxr)k");


		
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere("and rownum <= 7"); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/*
	 * 我的项目
	 */
	@RequestMapping(value = "/getwdxmPageList")
	@ResponseBody
	public Object getwdxmPageList(HttpSession session) throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String rybh=CommonUtils.getRybh();
		String sszt = Constant.getztid(session);
//		String zl = pd.getString("treedwbh");
		sqltext.append(" * ");
		  tablename.append("(select guid,xmbh,xmmc,to_char(k.ysje,'FM9999999999999990.00') as ysje ,to_char(k.syje,'FM9999999999999990.00') as syje,to_char((k.ysje-k.syje),'FM9999999999999990.00') as zcje from CW_XMJBXXB k where fzr = '"+rybh+"' and sszt='"+sszt+"')");
		
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere("and rownum <= 6"); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value="/getWsbx",produces = "text/html;charset=utf-8")
    @ResponseBody
    public Object getWsbx(){
		PageData pd = this.getPageData();
		String sjqj=pd.getString("sjqj");
        return indexService.getWsbx(sjqj);
    }
	@RequestMapping(value="/goTzxxwindow")
	@ResponseBody
	public ModelAndView goTzxxwindow(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("loginId",LUser.getGuid());
		mv.setViewName("system/index/xtxx");
		return mv;
	}
	/*
	 * 
	 */
	@RequestMapping(value = "/getTzxxList")
	@ResponseBody
	public Object getTzxxList() throws Exception {		
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		pageList.setSqlText(" * ");
		//设置表名
		pageList.setTableName("(select t.guid,t.fbr,to_char(t.fbsj,'yyyy-mm-dd') as fbsj,t.jsr,t.jssj,t.sjid,t.xxnr,decode(nvl(t.xxzt,'0'),'0','未读','已读') as zt,t.xxzt,"
				+ "(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.guid=t.fbr) as fbrxm,p.pzbh,p.pzz "
				+ "from CW_XTXX t left join cw_pzlrzb p on p.guid=t.sjid where jsr='"+LUser.getGuid()+"')");
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setHj1("");//合计
	    pageList = pageService.findWindowList(pd,pageList,"TZXX");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	@RequestMapping(value="/doUpdateTzxx",produces = "text/html;charset=utf-8")
    @ResponseBody
    public Object doUpdateTzxx(HttpSession session){
		PageData pd = this.getPageData();
		String guid=pd.getString("guid");
		boolean flag = indexService.doUpdateTzxx(guid);
		if(flag){
			String pzlx=Validate.isNullToDefaultString(pd.getString("pzlx"), pzlrService.getPzlx());
			String pzbh = pd.getString("pzbh");
			String sszt = Constant.getztid(session);
			Map<String, Object> map = pzlrService.getWjzDate(sszt,pzlx);
			session.setAttribute("pznd", ""+map.get("ztnd"));
			session.setAttribute("kjqj", ""+map.get("kjqj"));
		}
        return "{\"success\":"+flag+"}";
    }
	
}
