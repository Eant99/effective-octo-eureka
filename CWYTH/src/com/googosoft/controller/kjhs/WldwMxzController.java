package com.googosoft.controller.kjhs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.controller.kjhs.ZzController;
import com.googosoft.dao.kjhs.ZzDao;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.WldwMxzService;
import com.googosoft.service.kjhs.ZzService;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.service.kjhs.bbzx.MxzService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value = "/wldwmxz")
public class WldwMxzController extends BaseController {
		
	
	@Resource(name = "dictService")
	private DictService dictService;// 数据字典单例

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@Resource(name = "wldwmxzService")
	private WldwMxzService mxzService;
	
	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	
	@Resource(name="ZzService")
	private ZzService zzService; //单例
	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toUrl")
	public ModelAndView gokmxxPage(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String kmbh="",kmmc="";
		String kmxx = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
		String dm = Validate.isNullToDefaultString(pd.getString("dm"), "");
		if(kmxx.indexOf("(")!=-1){
			kmmc=kmxx.substring(kmxx.indexOf(")")+1, kmxx.length());
			kmbh=kmxx.substring(1,kmxx.lastIndexOf(")"));
		}else{
			kmbh=Validate.isNullToDefaultString(pd.getString("kmbh"), "");
			kmmc=Validate.isNullToDefaultString(pd.getString("kmmc"), "");
		}
		String bbqj = Validate.isNullToDefaultString(pd.getString("bbqj"),Validate.isNullToDefaultString(request.getSession().getAttribute("bbqj"), ""));
		
		
		String pxfs = Validate.isNullToDefaultString(pd.getString("pxfs"), "");
		String jump = Validate.isNullToDefaultString(pd.getString("jump"), "");
		String clicks = Validate.isNullToDefaultString(pd.getString("clicks"), "");
		String treesearch = pd.getString("treesearch");
		System.err.println("controller_mxz_treeaearch="+treesearch);
		//控制是否显示返回按钮
		String type = pd.getString("type");
		if("yes".equals(jump)){
			mxzService.deleteKmyeb();
			request.getSession().removeAttribute("params");
			request.getSession().removeAttribute("bbqj");
			type = "mxz";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = Validate.isNullToDefaultString(bbqj, sdf.format(date));
		year = year.substring(0,4);
		List<Map<String,Object>> months = kmyeService.getMonth();
		mv.addObject("months", months);
		String url = "kjhs/bbzx/dwwlmxz_list";
		
		String StratMonth = pd.getString("StartMonth"); 
		String endMonth = pd.getString("endMonth"); 
		String pz = pd.getString("pz"); 
		
		mv.addObject("StratMonth", StratMonth);
		mv.addObject("endMonth", endMonth);
		mv.addObject("pz", pz);
		mv.addObject("dm", dm);
		mv.addObject("bbqj", bbqj);
		mv.addObject("kmxx", kmxx);
		mv.addObject("kmmc", kmmc);
		mv.addObject("pxfs", pxfs);
		mv.addObject("clicks", clicks);
		mv.addObject("jump", jump);
		mv.addObject("kmbh", kmbh);
		mv.addObject("year", year);
		mv.addObject("type", type);
		mv.addObject("treesearch", treesearch);
		mv.setViewName(url);
		return mv;
	}
    
	/**
	 * 获取单位往来明细账列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getDwwlPageList")
	@ResponseBody
	public Object getDwwlPageList(){
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("kmbh");
		System.err.println("***********"+dwbh+"%%%%%%%%");
		String starttime = pd.getString("starttime");
		String endtime = pd.getString("endtime");
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		
		sqltext.append(" (select to_char(zb.pzrq,'yyyy-mm-dd') pzrq,t.guid,zb.guid as pzid,zb.pzzt,zb.pzbh,zb.pzz,zb.pzlxmc,t.zy,bmbh,t.xmbh,kj.kmmc,t.kmbh,t.jjfl,zb.kjqj,zb.pznd,w.dwmc ,w.wlbh,");
		sqltext.append(" (select mc from GX_SYS_DWB where dwbh=t.bmbh) as bmmc,(select km.kmmc from cw_kjkmszb km where km.kmbh=t.kmbh )kjkm,");
		sqltext.append("  (select kmb.kmmc from cw_jjkmb kmb where kmb.kmbh = t.jjfl)jjkm,");
		sqltext.append(" (select xmmc from CW_XMJBXXB where xmbh = t.xmbh and bmbh=t.bmbh)xmmc,");
		sqltext.append(" to_char(nvl(t.jfje,0),'99999999999990.99')jfje,to_char(nvl(t.dfje,0),'99999999999990.99')dfje,");
		sqltext.append(" to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'99999999999990.99')ye");	
		sqltext.append(" from CW_PZLRMXB t left join cw_pzlrzb zb on t.pzbh=zb.guid left join CW_KJKMSZB kj on t.guid = kj.kmbh left join cw_fzlrb f on  t.guid=f.kmbh left join cw_wldwb  w on w.wlbh = f.dfdw  where 1=1 and f.dfdw is not null");		
//		sqltext.append(" from CW_PZLRMXB t left join cw_pzlrzb zb on t.pzbh=zb.guid left join CW_KJKMSZB kj on t.guid = kj.kmbh left join cw_fzlrb f on  t.guid=f.kmbh left join cw_wldwb  w on w.wlbh = f.dfdw  where 1=1 and f.wldw is not null");		
		if(Validate.noNull(dwbh)){
			sqltext.append(" and f.dfdw ='"+dwbh+"' ");
		}

		if(Validate.noNull(starttime)){
			sqltext.append(" and to_char(zb.pzrq, 'yyyy-MM') >='"+starttime+"' ");
		}
		if(Validate.noNull(endtime)){
			sqltext.append(" and  to_char(zb.pzrq, 'yyyy-MM') <='"+endtime+"' ");
		}
		sqltext.append(" ) k");
		pageList.setSqlText("guid,pzid,pzrq,pzzt,pzbh,pzlxmc,zy,bmbh,xmbh,kmmc,kmbh,bmmc,xmmc,jfje,dfje,ye,jjfl,kjqj,pzz,pznd,dwmc,wlbh,kjkm,jjkm");
		//设置表名
		pageList.setTableName(sqltext.toString());
		//设置表主键名
		pageList.setKeyId("guid");
		//设置WHERE条件
		String wlbh = pd.getString("treedwbh");
        String strWhere = "";
        if(Validate.noNull(wlbh)){
        	strWhere = " and dfdw like '"+wlbh+"%'";
//        	strWhere = " and wlbh like '"+wlbh+"%'";
        }
        
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		pageList.setStrWhere(strWhere); //获取管理单位权限
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 弹出条件选择页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jumpWindow")
	public ModelAndView jumpWindow() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//12个月份
		List<Map<String,Object>> months = mxzService.getMonth();
		//默认获取当前年份
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(date);
		String nowmonth=date.getMonth()+1+"";
		//科目级别list
		List list = mxzService.getKjkmJb();
		mv.addObject("list", list);
		mv.addObject("year", year);
		mv.addObject("months", months);
		mv.addObject("nowmonth", nowmonth);
		mv.setViewName("kjhs/bbzx/mxz_search2");
		return mv;
	}
	
	/**
	 * ajax处理session
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/paramSession", produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public void paramSession(HttpServletRequest request,
			HttpServletResponse response) {
		String params = this.getPageData().getString("params");
		request.getSession().setAttribute("params", params);
		request.getSession().setAttribute("bbqj", this.getPageData().getString("bbqj"));
	}
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public List<Map<String,Object>> getPageList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		
		ModelAndView mv = this.getModelAndView();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String pxfs = "1";//默认按照凭证日期排序
		//创建参数对象
		Params params = new Params();
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String jsonStr = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");		
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		mv.addObject("kmbh", kmbh);
		if(kmbh!=""&&kmbh.contains("(")){
			kmbh=kmbh.substring(1, kmbh.lastIndexOf(")"));					
		}
		if(Validate.noNull(jsonStr)){
			String ajson = jsonStr.substring(8,jsonStr.length()-1);
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			boolean result = mxzService.getResult(list,sszt,kjzd,kmbh);
			if(!result){
				return null;
			}
		}
		System.err.println("treesearch="+treesearch);
		return  mxzService.getPageList(kmbh,kjzd,treesearch);
	}
	
	
	@RequestMapping(value = "/getWldwPageList")
	@ResponseBody
	public List<Map<String,Object>> getWldwPageList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		
		ModelAndView mv = this.getModelAndView();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String year = Validate.isNullToDefaultString(request.getParameter("year"), "");
		String dm = Validate.isNullToDefaultString(request.getParameter("dm"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		String pxfs = "1";//默认按照凭证日期排序
		//创建参数对象
		Params params = new Params();
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String jsonStr = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");		
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		String bmbh = Validate.isNullToDefaultString(pd.get("bmbh"), "");
		if(Validate.noNull(bmbh)){
			bmbh = bmbh.substring(1, bmbh.indexOf(")"));
		}
		String xmbh = Validate.isNullToDefaultString(pd.get("xmbh"), "");
		if(Validate.noNull(xmbh)){
			xmbh = xmbh.substring(1, xmbh.indexOf(")"));
		}
		String jfjel = Validate.isNullToDefaultString(pd.get("jfjel"), "");
		String jfjeh = Validate.isNullToDefaultString(pd.get("jfjeh"), "");
		String zy  = Validate.isNullToDefaultString(pd.get("zy"), "");
		mv.addObject("kmbh", kmbh);
		if(kmbh!=""&&kmbh.contains("(")){
			kmbh=kmbh.substring(1, kmbh.lastIndexOf(")"));					
		}
		String kjkm = "";
		String pznd=year;
		String startMonth = Validate.isNullToDefaultString(request.getParameter("kjqj1"), "");
		String endMonth = Validate.isNullToDefaultString(request.getParameter("kjqj2"), "");
		String pzzt = Validate.isNullToDefaultString(request.getParameter("pzzt"), "");
		if(Validate.noNull(jsonStr)){
			String ajson = jsonStr.substring(8,jsonStr.length()-1);
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			for(int i=0;i<1;i++){
				Map map = (Map) list.get(0);
				kjkm = map.get("startKjkm")+"";
				kjkm = kjkm.substring(1, kjkm.lastIndexOf(")"));
//				String kjqj = map.get("startMonth")+"";
				pznd = map.get("year")+"";
				startMonth = map.get("startMonth")+"";
//				String endM = map.get("endMonth")+"";
				endMonth = map.get("endMonth")+"";
			}
			
		}
		
		return mxzService.getJjPageList(kmbh,kjkm,pznd,startMonth,endMonth,dm,pzzt,bmbh,xmbh,jfjel,jfjeh,zy);
		
	}
	@RequestMapping(value="/expWldwExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expWldwExcel(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		String searchValue = pd.getString("searchJson");
		//查询数据的sql语句
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select * from (select to_char(zb.pzrq,'yyyy-mm-dd') pzrq,t.guid,zb.pzzt,zb.pzbh,zb.pzz,zb.pzlxmc,t.zy,bmbh,t.xmbh,kj.kmmc,t.kmbh,t.jjfl,zb.kjqj,zb.pznd,w.dwmc ,w.wlbh,");
		sqltext.append(" (select mc from GX_SYS_DWB where dwbh=t.bmbh) as bmmc,(select km.kmmc from cw_kjkmszb km where km.kmbh=t.kmbh )kjkm,");
		sqltext.append("  (select kmb.kmmc from cw_jjkmb kmb where kmb.kmbh = t.jjfl)jjkm,");
		sqltext.append(" (select xmmc from CW_XMJBXXB where xmbh = t.xmbh and bmbh=t.bmbh)xmmc,");
		sqltext.append(" to_char(nvl(t.jfje,0),'99999999999990.99')jfje,to_char(nvl(t.dfje,0),'99999999999990.99')dfje,");
		sqltext.append(" to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'99999999999990.99')ye");	
		sqltext.append(" from CW_PZLRMXB t left join cw_pzlrzb zb on t.pzbh=zb.guid left join CW_KJKMSZB kj on t.guid = kj.kmbh left join cw_fzlrb f on  t.guid=f.kmbh left join cw_wldwb  w on w.wlbh = f.dfdw )k where 1=1 and dwmc is not null");		
		
				
		String wlbh = pd.getString("treedwbh");
       
		if(Validate.noNull(wlbh)){
			sqltext.append(" and wlbh like '"+wlbh+"%'");
		}
		
		String guid = pd.getString("guid");
		if(Validate.noNull(guid)){
			sqltext.append(" and guid in ('"+guid+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchValue));
		sqltext.append(" order by pzrq desc");
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("pzrq");
		m.setShowname("凭证日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("pzlxmc");
		m.setShowname("凭证类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("pzbh");
		m.setShowname("凭证号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ZY");
		m.setShowname("摘要");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("DWMC");
		m.setShowname("往来单位名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("KJKM");
		m.setShowname("会计科目");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("JJKM");
		m.setShowname("经济科目");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("BMMC");
		m.setShowname("部门");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("XMMC");
		m.setShowname("项目");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("JFJE");
		m.setShowname("借方金额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("DFJE");
		m.setShowname("贷方金额");
		mlist.add(m);
		m = null;
		
		
		m = new M_largedata();
		m.setName("YE");
		m.setShowname("余额");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpSession session,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		String treesearch=pd.getString("treesearch");
		String searchValue = pd.getString("searchJson");
		String flag = pd.getString("foo");
		
		 String ztgid1 = Constant.getztid(session);//账套编号
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		sysDate = Validate.isNullToDefaultString(pd.getString("bbyf"), sdf.format(date));//
		List list = new ArrayList<Map<String,Object>>();
//		list = this.getPageList(request,response);
		//把明细表list从session中取出来
//		list =(List) session.getAttribute("mxzList");
		String kmbh=pd.getString("kmbh");
		String kjzd="";
		list= mxzService.getPageList(kmbh,kjzd,treesearch);
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.mxzService.expExcel(realfile, shortfileurl,searchValue,flag,list);
	}
	
	
	//页面之间的跳转
	@RequestMapping("/zjc")
	public ModelAndView PageSkip(HttpServletRequest request) {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String kmbh = pd.getString("kmbh");
		String bbqj = pd.getString("bbqj");
		String kkqjj = pd.getString("kkqjj");
		String pzbh = pd.getString("pzbh");
		String str=bbqj.substring(bbqj.lastIndexOf("("),bbqj.lastIndexOf(")"));
		String s=str.substring(1,str.length());
		List list = new ArrayList<Map<String,Object>>();
//		String kmmc = mxzService.kmmc(s);
	  	list = mxzService.kmbhList(s);
		System.err.println("测试数据="+list);
//		System.err.println("测试数据111="+kmmc);
		mv.addObject("list", list);
		mv.addObject("kmmc", kmbh);
//		mv.addObject("kmmc", kmmc);
		mv.addObject("bbqj", bbqj);
		mv.addObject("kkqjj", kkqjj);
		mv.addObject("pzbh", pzbh);
		
		mv.setViewName("kjhs/bbzx/mxztc");
		return mv;
	}
	/**
	 * 经济科目设置单位树
	 * 
	 */
	@RequestMapping(value = "/wdbx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object wdbxTree(HttpServletResponse response)
			throws java.io.IOException {
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		if ("get-ys".equals(menu)) {
			return mxzService.getYsLx2(pd, rootPath);
		} else {
			return "";
		}
	} 
	
	@RequestMapping(value="/kylbxWindowwdbx")
	public ModelAndView gowdbxDwxxPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("kjhs/bbzx/mainWldwTree");
		return mv;
	}
	
	
}
