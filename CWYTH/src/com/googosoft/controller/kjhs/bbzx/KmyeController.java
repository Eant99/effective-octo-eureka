package com.googosoft.controller.kjhs.bbzx;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.controller.cwbb.expExcel.ZcfzExportExcel;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.cwbb.ZcfzbService;
import com.googosoft.service.kjhs.KmszService;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.service.kjhs.bbzx.ZjrbbService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Const;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 科目余额表
 * 
 * @author googosoft
 * 
 */
@Controller
@RequestMapping(value = "/kmye")
public class KmyeController extends BaseController {
	@Resource(name="kmszService")	
	private KmszService kmszService;
	
	@Resource(name="zjrbbService")	
	private ZjrbbService zjrbbService;
	
	@Resource(name="zcfzbService")
	private ZcfzbService zcfzbService;
	
	
	@Resource(name = "dictService")
	private DictService dictService;// 数据字典单例

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/kmyeList")
	public ModelAndView gokmxxPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String gs = Validate.isNullToDefaultString(pd.getString("gs"), "");
		String url = "kjhs/bbzx/kmye/kmye_list";
		String flag = Validate.isNullToDefaultString(pd.getString("flag"), "");
		if("xm".equals(gs)||"xmmx".equals(gs)){
			url = "kjhs/bbzx/kmye/kmyexm_list";
		}else if("bm".equals(gs)||"bmmx".equals(gs)){
			url = "kjhs/bbzx/kmye/kmyebm_list";
		}
		List<Map<String,Object>> months = kmyeService.getMonth();
		//默认获取当前年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		String startMonth = "01";
		
		String endMonth = Constant.MR_MONTH();
		//科目级别list
		List list = kmyeService.getKjkmJb();
		String mbmc = kmyeService.getMbmc();
		mv.addObject("mbmc", mbmc);
		mv.addObject("ly", pd.getString("ly"));
		mv.addObject("list", list);
		mv.addObject("date", year+"年"+startMonth+"月至"+endMonth+"月");
		mv.addObject("endMonth", endMonth);
		mv.addObject("startMonth", startMonth);
		mv.addObject("year", year);
		mv.addObject("months", months);
		mv.setViewName(url);
		return mv;
	}
	
	@RequestMapping(value = "/kmyeList2")
	public ModelAndView gokmxxPage2(HttpServletRequest request,
			HttpServletResponse response) {		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String flag = Validate.isNullToDefaultString(pd.getString("flag"),
				"select");
		String date = Validate.isNullToDefaultString(pd.getString("date"), "");
		String gs = Validate.isNullToDefaultString(pd.getString("gs"), "");
		String url = "kjhs/bbzx/kmye/kmye_list2";
		if("xm".equals(gs)){
			url = "kjhs/bbzx/kmye/kmyexm_list2";
		}else if("bm".equals(gs)){
			url = "kjhs/bbzx/kmye/kmyebm_list2";
		}
		if("select".equals(flag)){
			request.getSession().removeAttribute("params");
		}
		mv.addObject("date", date);
		mv.addObject("flag", flag);
		mv.addObject("gs", gs);
//		kmyeService.deleteKmyeb();
		mv.setViewName(url);
		return mv;
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
		String flag = Validate.isNullToDefaultString(pd.getString("flag"),
				"select");
		mv.addObject("flag", flag);
		//12个月份
		List<Map<String,Object>> months = kmyeService.getMonth();
		//默认获取当前年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		String mm = Constant.MR_MONTH();
		//科目级别list
		List list = kmyeService.getKjkmJb();
		mv.addObject("list", list);
		mv.addObject("mm", mm);
		mv.addObject("year", year);
		mv.addObject("months", months);
		
		mv.setViewName("kjhs/bbzx/kmye/jumpWindow");
		return mv;
	}
	
	/**
	 * 会计科目设置单位树
	 * 
	 */
	@RequestMapping(value="/KmszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object kmszTree(HttpSession session) throws java.io.IOException{
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String jump=pd.getString("jump");
		if(menu.equals("get-kmsz")){
			String loginrybh = LUser.getRybh();
			if("root".equals(dm)){//
				return kmszService.getPowerDwNode(pd, loginrybh, rootPath,dm, session);
			}else{
				return 	kmszService.getPowerDwNode(pd, loginrybh, rootPath,dm, session);
			}			
			
		}else {
			return "";
		}
		
	}
	/**
	 * 会计科目列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getkmxxPageList")
	@ResponseBody
	public Object getkmxxPageList(HttpSession session) throws Exception {	
		String kjzd = CommonUtil.getKjzd(session);
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("distinct K.KMBH,K.KMMC,K.JB,K.KMJC,'('||K.KMBH||')'||K.KMMC AS KMXX");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		String year = Validate.isNullToDefaultString(pd.getString("year"), "");
		//String strWhere = " AND TO_CHAR(K.KMND,'YYYY')='"+year+"'";
		String strWhere = "";
		if(Validate.noNull(mkbh) && ("070503".equals(mkbh)||"070507".equals(mkbh)||"070501".equals(mkbh)||"070701".equals(mkbh))){
			strWhere = " AND kjzd='"+kjzd+"' and sjfl!='root'";
		}else{
		    strWhere = " and wldwfz='1' AND kjzd='"+kjzd+"' and sjfl!='root'";
		}
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
		if(Validate.noNull(kmbh)) {
			strWhere +=" start with kmbh='"+kmbh+"' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root'  ";
		}
		
		pageList.setStrWhere(strWhere);
		pageList.setTableName("CW_KJKMSZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findWindowList(pd,pageList,"WWW");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 会计科目列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getkmxxgrPageList")
	@ResponseBody
	public Object getkmxxgrPageList(HttpSession session) throws Exception {	
		String kjzd = CommonUtil.getKjzd(session);
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("distinct K.KMBH,K.KMMC,K.JB,K.KMJC,'('||K.KMBH||')'||K.KMMC AS KMXX");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		String year = Validate.isNullToDefaultString(pd.getString("year"), "");
		//String strWhere = " AND TO_CHAR(K.KMND,'YYYY')='"+year+"'";
		String strWhere = " and grfz='1' AND kjzd='"+kjzd+"' and sjfl!='root'";
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
		if(Validate.noNull(kmbh)) {
			strWhere +=" start with kmbh='"+kmbh+"' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root'  ";
		}
		
		pageList.setStrWhere(strWhere);
		pageList.setTableName("CW_KJKMSZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findWindowList(pd,pageList,"WWW");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
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
		request.getSession().setAttribute("params_"+request.getParameter("gs"), params);
	}
	private Params getParams(HttpServletRequest request){
		Params params = new Params();
			String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
			String gs = Validate.isNullToDefaultString(request.getParameter("gs"), "");
			String jzpz = Validate.isNullToDefaultString(request.getParameter("jzpz"), "");
			if("1".equals(jzpz)){
				jzpz = "02','99','00','01";
			}else{
				jzpz = "02','99";
			}
			params.setEndMonth(Validate.isNullToDefaultString(request.getParameter("endMonth"), ""));
			params.setGs(gs);
			params.setJzpz(jzpz);
			params.setKjkm(Validate.isNullToDefaultString(request.getParameter("kjkm"), ""));
			params.setStartMonth(Validate.isNullToDefaultString(request.getParameter("startMonth"), ""));
			params.setYear(Validate.isNullToDefaultString(request.getParameter("year"), ""));
			//综合查询
			params.setKmbh1(Validate.isNullToDefaultString(request.getParameter("kmbh1"), ""));
			params.setKmbh2(Validate.isNullToDefaultString(request.getParameter("kmbh2"), ""));
			params.setKmbh3(Validate.isNullToDefaultString(request.getParameter("kmbh3"), ""));
			params.setStartJc(Validate.isNullToDefaultString(request.getParameter("sjc"), ""));
			params.setEndJc(Validate.isNullToDefaultString(request.getParameter("ejc"), ""));
			params.setBmh(Validate.isNullToDefaultString(request.getParameter("bmh"), ""));
			
			String kjzd = zjrbbService.getkjzd(Validate.isNullToDefaultString(request.getParameter("year"), ""));
			params.setSszt(sszt);
			params.setKjzd(kjzd);
			String treebh = Validate.isNullToDefaultString(request.getParameter("treebh"), "");
			String kmsx = Validate.isNullToDefaultString(request.getParameter("kmsx"), "");
			params.setTreebh(treebh.length() == 2? kmsx : treebh);
			System.err.println("kjzd==="+kjzd);
			System.err.println("sszt==="+sszt);
			System.err.println("params===="+params);
		return params;
	}
	@RequestMapping(value = "/getPageList2")
	@ResponseBody
	public Object getPageList2(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception {
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = kmyeService.getResult(params);
		}
		return result_map;
	}
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Map<String, List<Map<String,Object>>> getPageList(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception {
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = kmyeService.getResult(params);
		}
		return result_map;
	}
	/**
	 * 会计科目设置单位树
	 * 
	 */
	@RequestMapping(value="/computer",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object computer(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();	
		Map map = new HashMap<String,String>();
		map = kmyeService.computer();
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	
	/**
	 * 跳转打印页面
	 * @return
	 */
	@RequestMapping(value = "/goPrint")
	public ModelAndView goPrint(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String flag = Validate.isNullToDefaultString(pd.getString("flag"), "");
		String gs = Validate.isNullToDefaultString(pd.getString("gs"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		mv.addObject("flag", flag);
		mv.addObject("gs",gs);
		List list = kmyeService.getPrintInfo(sszt);
		Map map = kmyeService.computer();
		String date = Validate.isNullToDefaultString(pd.getString("date"), "");
		mv.addObject("date", date);
		mv.addObject("list", list);
		mv.addObject("map", map);
		System.err.println(map);
		if("bm".equals(gs)){
			mv.setViewName("kjhs/bbzx/kmye/print_bm");
		}else if("xm".equals(gs)){
			mv.setViewName("kjhs/bbzx/kmye/print_xm");
		}else{
			mv.setViewName("kjhs/bbzx/kmye/print");
		}
		return mv;
	} 
	@RequestMapping(value="/getXxList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getXxList(HttpSession session) throws Exception{
		Gson gson = new Gson();
		
		List list =kmyeService.getXxList(session);
		return gson.toJson(list);
	}
	
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = kmyeService.getResult(params);
		}
		list = result_map.get("mxList");
		List<M_largedata> mlist = new ArrayList<M_largedata>();//存放第一行标题
		List<M_largedata> mlist1 = new ArrayList<M_largedata>();//存放第二行标题
		List<M_largedata> mlist2 = new ArrayList<M_largedata>();//存放数据
		List<List<M_largedata>> tlist = new ArrayList<List<M_largedata>>();
		M_largedata m = new M_largedata();
		
		//导出初始化列表
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("kmbh");//序号直接写rn即可
				m.setShowname("科目编号");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("kmmc");//序号直接写rn即可
				m.setShowname("科目名称");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;

				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setEcol(1);//合并列0开始
				m.setShowname("期初余额");//第一行
				mlist.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setEcol(1);//合并列0开始
				m.setShowname("本期发生");//第一行
				mlist.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setEcol(1);//合并列0开始
				m.setShowname("期末余额");//第一行
				mlist.add(m);
				m = null;
				
				m = new M_largedata();
				m.setSindex(3);//开始列0开始
				m.setName("QCFX");
				m.setShowname("方向");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setName("QCYE");
				m.setShowname("余额");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setName("BQJF");
				m.setShowname("借方");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setName("BQDF");
				m.setShowname("贷方");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setName("QMFX");
				m.setShowname("方向");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setName("QMYE");
				m.setShowname("余额");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				tlist.add(mlist);
				tlist.add(mlist1);
				
		System.out.println("WEAEAWE"+list);
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel\\" + shortfileurl;
		String  name = "会计科目余额表.xls";
		kmyeService.ExpExcel(list,realfile,name,mlist2,tlist);
		return "{\"url\":\"excel\\\\"+shortfileurl+"\"}";
		//return this.kmyeService.expExcel(realfile, shortfileurl,"","",list);
	}
	
	
	
	//页面之间的跳转
		@RequestMapping("/zjc")
		public ModelAndView PageSkip(HttpServletRequest request) {
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
			String bh = pd.getString("bh");
			String bbqj = pd.getString("bbqj");
			String gs = Validate.isNullToDefaultString(pd.getString("gs"), "");
//			/CWYTH/WebContent/webView/kjhs/bbzx/kmye/kmyetc.jsp
			String sszt = Constant.getztid(request.getSession());
			List list = new ArrayList<Map<String,Object>>();
			String pageUrl = "kjhs/bbzx/kmye/kmyetc";
			String mc = "";
			String starttime = pd.getString("starttime");
			String endtime = pd.getString("endtime");
			if("kj".equals(gs)){
				list = kmyeService.kmbhList(bh,sszt,starttime,endtime);
				mc = kmyeService.kmmc(bh);
			}else if("bm".equals(gs)){
				list = kmyeService.bmbhList(bh);
				String bmmc = pd.getString("bmmc");
				String bmbh = bmmc.substring(bmmc.indexOf("(")+1,bmmc.indexOf(")")); 
				mv.addObject("bmbh", bmbh);

				mv.addObject("starttime", starttime);
				mv.addObject("endtime", endtime);
				pageUrl += "_bm";
			}else if("xm".equals(gs)){
				list = kmyeService.xmbhList(bh);
				mv.addObject("starttime", starttime);
				mv.addObject("endtime", endtime);
				pageUrl += "_xm";
			}
			System.err.println("测试数据="+list);
			System.err.println("测试数据111="+mc);
			mv.addObject("list", list);
			mv.addObject("mc", mc);
			mv.addObject("bbqj", bbqj);
			mv.addObject("bh",bh);
			mv.setViewName(pageUrl);
			return mv;
		}
		@RequestMapping(value = "/getxmPageList")
		@ResponseBody
		public Map<String, List<Map<String,Object>>> getxmPageList(HttpServletRequest request,
				HttpServletResponse response,HttpSession session) throws Exception {
			Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
			Params params = this.getParams(request);
			if(params != null){
				result_map = kmyeService.getResult(params);
			}
			return result_map;
		}
		/**
		 * 导出部门
		 */
		@RequestMapping("/bmexpExcel")
		@ResponseBody
		public Object bmexpExcel(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
			Params params = this.getParams(request);
			if(params != null){
				result_map = kmyeService.getResult(params);
			}
			list = result_map.get("mxList");
			List<M_largedata> mlist = new ArrayList<M_largedata>();//存放第一行标题
			List<M_largedata> mlist1 = new ArrayList<M_largedata>();//存放第二行标题
			List<M_largedata> mlist2 = new ArrayList<M_largedata>();//存放数据
			List<List<M_largedata>> tlist = new ArrayList<List<M_largedata>>();
			M_largedata m = new M_largedata();
			
			//导出初始化列表
					m.setIsmerge(true);//合并单元格
					m.setErow(1);//合并行0开始
					m.setName("bmmc");//序号直接写rn即可
					m.setShowname("部门名称");//第一行
					mlist.add(m);
					mlist2.add(m);
					m = null;
			
					m = new M_largedata();
					m.setIsmerge(true);//合并单元格
					m.setErow(1);//合并行0开始
					m.setName("kmbh");//序号直接写rn即可
					m.setShowname("科目编号");//第一行
					mlist.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setIsmerge(true);//合并单元格
					m.setErow(1);//合并行0开始
					m.setName("kmmc");//序号直接写rn即可
					m.setShowname("科目名称");//第一行
					mlist.add(m);
					mlist2.add(m);
					m = null;
					

					m = new M_largedata();
					m.setColstyle("double");
					m.setColtype("Number");
					m.setIsmerge(true);//合并单元格
					m.setEcol(1);//合并列0开始
					m.setShowname("期初余额");//第一行
					mlist.add(m);
					m = null;
					
					m = new M_largedata();
					m.setIsmerge(true);//合并单元格
					m.setEcol(1);//合并列0开始
					m.setShowname("本期发生");//第一行
					mlist.add(m);
					m = null;
					
					m = new M_largedata();
					m.setColstyle("double");
					m.setColtype("Number");
					m.setIsmerge(true);//合并单元格
					m.setEcol(1);//合并列0开始
					m.setShowname("期末余额");//第一行
					mlist.add(m);
					m = null;
					
					m = new M_largedata();
					m.setSindex(4);//开始列0开始
					m.setName("QCFX");
					m.setShowname("方向");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setColstyle("double");
					m.setColtype("Number");
					m.setName("QCYE");
					m.setShowname("余额");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setColstyle("double");
					m.setColtype("Number");
					m.setName("BQJF");
					m.setShowname("借方");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setColstyle("double");
					m.setColtype("Number");
					m.setName("BQDF");
					m.setShowname("贷方");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setName("QMFX");
					m.setShowname("方向");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setColstyle("double");
					m.setColtype("Number");
					m.setName("QMYE");
					m.setShowname("余额");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					tlist.add(mlist);
					tlist.add(mlist1);
					
			System.out.println("WEAEAWE"+list);
			String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
			String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel\\" + shortfileurl;
			String  name = "部门余额表.xls";
			kmyeService.ExpExcel(list,realfile,name,mlist2,tlist);
			return "{\"url\":\"excel\\\\"+shortfileurl+"\"}";
			//return this.kmyeService.expExcel(realfile, shortfileurl,"","",list);
		}
		/**
		 * 导出项目
		 */
		@RequestMapping("/xmexpExcel")
		@ResponseBody
		public Object xmexpExcel(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
			Params params = this.getParams(request);
			if(params != null){
				result_map = kmyeService.getResult(params);
			}
			list = result_map.get("mxList");
			List<M_largedata> mlist = new ArrayList<M_largedata>();//存放第一行标题
			List<M_largedata> mlist1 = new ArrayList<M_largedata>();//存放第二行标题
			List<M_largedata> mlist2 = new ArrayList<M_largedata>();//存放数据
			List<List<M_largedata>> tlist = new ArrayList<List<M_largedata>>();
			M_largedata m = new M_largedata();
			
			//导出初始化列表
					m.setIsmerge(true);//合并单元格
					m.setErow(1);//合并行0开始
					m.setName("kmbh");//序号直接写rn即可
					m.setShowname("科目编号");//第一行
					mlist.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setIsmerge(true);//合并单元格
					m.setErow(1);//合并行0开始
					m.setName("kmmc");//序号直接写rn即可
					m.setShowname("科目名称");//第一行
					mlist.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setIsmerge(true);//合并单元格
					m.setErow(1);//合并行0开始
					m.setName("bmmc");//序号直接写rn即可
					m.setShowname("部门名称");//第一行
					mlist.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setIsmerge(true);//合并单元格
					m.setErow(1);//合并行0开始
					m.setName("xmmc");//序号直接写rn即可
					m.setShowname("项目名称");//第一行
					mlist.add(m);
					mlist2.add(m);
					m = null;

					m = new M_largedata();
					m.setIsmerge(true);//合并单元格
					m.setEcol(1);//合并列0开始
					m.setShowname("期初余额");//第一行
					mlist.add(m);
					m = null;
					
					m = new M_largedata();
					m.setIsmerge(true);//合并单元格
					m.setEcol(1);//合并列0开始
					m.setShowname("本期发生");//第一行
					mlist.add(m);
					m = null;
					
					m = new M_largedata();
					m.setIsmerge(true);//合并单元格
					m.setEcol(1);//合并列0开始
					m.setShowname("期末余额");//第一行
					mlist.add(m);
					m = null;
					
					m = new M_largedata();
					m.setSindex(5);//开始列0开始
					m.setName("QCFX");
					m.setShowname("方向");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setColstyle("double");
					m.setName("QCYE");
					m.setShowname("余额");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setColstyle("double");
					m.setName("BQJF");
					m.setShowname("借方");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setColstyle("double");
					m.setName("BQDF");
					m.setShowname("贷方");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setName("QMFX");
					m.setShowname("方向");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					m = new M_largedata();
					m.setColstyle("double");
					m.setName("QMYE");
					m.setShowname("余额");//第二行
					mlist1.add(m);
					mlist2.add(m);
					m = null;
					
					tlist.add(mlist);
					tlist.add(mlist1);
					
					System.out.println("WEAEAWE"+list);
					String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
					String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel\\" + shortfileurl;
					String  name = "会计科目余额表.xls";
					kmyeService.ExpExcel(list,realfile,name,mlist2,tlist);
					return "{\"url\":\"excel\\\\"+shortfileurl+"\"}";
					//return this.kmyeService.expExcel(realfile, shortfileurl,"","",list);
		}
	
}
