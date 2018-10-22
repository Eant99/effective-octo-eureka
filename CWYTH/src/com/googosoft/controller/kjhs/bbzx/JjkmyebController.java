package com.googosoft.controller.kjhs.bbzx;

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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.bbzx.JjkmyebService;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.service.kjhs.bbzx.ZjrbbService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

import cn.jpush.api.push.model.Message;

@Controller
@RequestMapping(value="/jjkmyeb")
public class JjkmyebController extends BaseController {

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例
	
	@Resource(name="zjrbbService")	
	private ZjrbbService zjrbbService;
	
	@Resource(name="jjkmyebService")	
	private JjkmyebService jjkmyebService;
	
	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jjkmList")
	public ModelAndView gokmxxPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		String url = "kjhs/bbzx/jjkm/jjkmyeb_list";
		String mbmc = kmyeService.getMbmc();
		List<Map<String,Object>> months = kmyeService.getMonth();
		//默认获取当前年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		String endMonth = Constant.MR_MONTH();
		//科目级别list
		List list = kmyeService.getKjkmJb();
		mv.addObject("list", list);
		mv.addObject("date", year+"年01月至"+endMonth+"月");
		mv.addObject("endMonth", endMonth);
		mv.addObject("startMonth", "01");
		mv.addObject("year", year);
		mv.addObject("mbmc", mbmc);
		mv.addObject("months", months);
		mv.setViewName(url);
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
			String kjzd = zjrbbService.getkjzd(Validate.isNullToDefaultString(request.getParameter("year"), ""));
			params.setSszt(sszt);
			params.setKjzd(kjzd);
			//综合查询
			params.setTypes(Validate.isNullToDefaultString(request.getParameter("types"), ""));
			params.setKmbh(Validate.isNullToDefaultString(request.getParameter("kmbh"), ""));
			params.setKmbh1(Validate.isNullToDefaultString(request.getParameter("kmbh1"), ""));
			params.setKmbh2(Validate.isNullToDefaultString(request.getParameter("kmbh2"), ""));
			params.setKmbh3(Validate.isNullToDefaultString(request.getParameter("kmbh3"), ""));
			params.setStartJc(Validate.isNullToDefaultString(request.getParameter("startJc"), ""));
			params.setEndJc(Validate.isNullToDefaultString(request.getParameter("endJc"), ""));
			params.setTreebh(Validate.isNullToDefaultString(request.getParameter("treebh"), ""));
			System.err.println("kjzd==="+kjzd);
			System.err.println("sszt==="+sszt);
			System.err.println("params===="+params);
		return params;
	}
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Map<String, List<Map<String,Object>>> getPageList2(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception {
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = jjkmyebService.getResult_Hb(params);
		}
		return result_map;
	}
	@RequestMapping(value = "/getPageList_Click")
	@ResponseBody
	public Map<String, List<Map<String,Object>>> getPageList_Click(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception {
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = jjkmyebService.getResult(params);
		}
		return result_map;
	}
	/**
	 * 点击后的跳转页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getJjkmyeb_click")
	public ModelAndView getJjkmyeb_click(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		String url = "kjhs/bbzx/jjkm/jjkmyeb_click_list";
		String mbmc = kmyeService.getMbmc();
		List<Map<String,Object>> months = kmyeService.getMonth();
		//默认查询当前年份
		String kmbh=pd.getString("kmbh");
		String jsonStr=pd.getString("jsonStr");
		String datestr=pd.getString("datestr");
		String year = pd.getString("year");
		String startMonth = pd.getString("startMonth");
		String endMonth = pd.getString("endMonth");
		String startJc=pd.getString("startJc");
		String endJc=pd.getString("endJc");
		String jzpz=pd.getString("jzpz");
		String kmbh1=pd.getString("kmbh1");
		String kmbh2=pd.getString("kmbh2");
		String kmbh3=pd.getString("kmbh3");
		//科目级别list
		List list = kmyeService.getKjkmJb();
		mv.addObject("list", list);
		mv.addObject("date", year+"年01月至"+endMonth+"月");
		mv.addObject("endMonth", endMonth);
		mv.addObject("startMonth", startMonth);
		mv.addObject("year", year);
		mv.addObject("mbmc", mbmc);
		mv.addObject("months", months);
		mv.addObject("kmbh_click", kmbh);
		mv.addObject("jsonStr", jsonStr);
		mv.addObject("datestr", datestr);
		mv.addObject("startJc", startJc);
		mv.addObject("endJc", endJc);
		mv.addObject("jzpz", jzpz);
		mv.addObject("kmbh1", kmbh1);
		mv.addObject("kmbh2", kmbh2);
		mv.addObject("kmbh3", kmbh3);
		mv.setViewName(url);
		return mv;
	}
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> zjList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> zjList_new=new ArrayList<Map<String,Object>>();
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = jjkmyebService.getResult(params);
		}
		list = result_map.get("mxList");
		
		zjList=result_map.get("zjList");
		Map<String,Object> zjMap=new HashMap<>();
		if(Validate.noNull(zjList) && zjList.size()>0){
			zjMap.put("KMBH","合计");
			zjMap.put("QCYE",zjList.get(0).get("QCYE").equals("0.00")?"":zjList.get(0).get("QCYE"));
			zjMap.put("BQJF",zjList.get(0).get("BQJ").equals("0.00")?"":zjList.get(0).get("BQJ"));
			zjMap.put("BQDF",zjList.get(0).get("BQD").equals("0.00")?"":zjList.get(0).get("BQD"));
			zjMap.put("QMYE",zjList.get(0).get("QMYE").equals("0.00")?"":zjList.get(0).get("QMYE"));
			zjList_new.add(zjMap);
			list.addAll(zjList_new);
		}
		List<M_largedata> mlist = new ArrayList<M_largedata>();//存放第一行标题
		List<M_largedata> mlist1 = new ArrayList<M_largedata>();//存放第二行标题
		List<M_largedata> mlist2 = new ArrayList<M_largedata>();//存放数据
		List<List<M_largedata>> tlist = new ArrayList<List<M_largedata>>();
		M_largedata m = new M_largedata();
		
		//导出初始化列表
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("kmbh");//序号直接写rn即可
				m.setShowname("经济科目编号");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("kmmc");//序号直接写rn即可
				m.setShowname("经济科目名称");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;

				m = new M_largedata();
				m.setColstyle("double");
				m.setColtype("Number");
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("qcye");//序号直接写rn即可
				m.setShowname("期初余额");//第一行
				mlist.add(m);
				mlist2.add(m);
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
				m.setErow(1);//合并行0开始
				m.setName("BQJF");//序号直接写rn即可
				m.setShowname("期末余额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setSindex(4);//开始列0开始
				m.setColstyle("double");
				m.setColtype("Number");
				m.setName("BQDF");
				m.setShowname("借方");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setColtype("Number");
				m.setName("QMYE");
				m.setShowname("贷方");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				tlist.add(mlist);
				tlist.add(mlist1);
				
		System.out.println("WEAEAWE"+list);
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel\\" + shortfileurl;
		String  name = "经济科目余额表.xls";
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
		String kmbh = pd.getString("kmbh");
		String starttime = pd.getString("starttime");
		String endtime = pd.getString("endtime");
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
		mv.addObject("kmbh", kmbh);
//		/CWYTH/WebContent/webView/kjhs/bbzx/kmye/kmyetc.jsp
//		System.err.println("dddddddddddddddddddd="+bh);
		String pageUrl = "kjhs/bbzx/jjkm/jjkmmx";
		mv.addObject("bbqj", bbqj);
		mv.addObject("bh",bh);
		mv.setViewName(pageUrl);
		return mv;
	}
	/**
	 * 判断是否是末级经济科目
	 * @return
	 */
	@RequestMapping(value="checkEndJjkm")
	@ResponseBody
	public Object checkEndJjkm(String jjbh) {
		boolean bool=false;
		bool=kmyeService.checkEndJjkm(jjbh);
		return MessageBox.show(bool);
	}
}
