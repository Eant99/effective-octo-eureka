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

import org.activiti.engine.impl.cmd.GetStartFormCmd;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.Jjkmmxz1Service;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.service.kjhs.bbzx.XmjfyeService;
import com.googosoft.service.kjhs.bbzx.ZjrbbService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value="/xmjfye")
public class XmjfyeController extends BaseController {
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	
	@Resource(name="zjrbbService")	
	private ZjrbbService zjrbbService;
	
	@Resource(name="xmjfyeService")	
	private XmjfyeService xmjfyeService;
	
	@Resource(name = "jjkmmxz1Service")
	private Jjkmmxz1Service jjkmmxz1Service;
	
	/**
	 * 初始化页面--项目经费余额表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getXmjfyebPage")
	public ModelAndView getXmjfyebPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<Map<String,Object>> months = kmyeService.getMonth();
		//默认获取当前年份
		String year=Validate.isNullToDefaultString(pd.getString("year"), sdf.format(new Date())) ;
		String startMonth=Validate.isNullToDefaultString(pd.getString("startMonth"), "01") ;
		String endMonth=Validate.isNullToDefaultString(pd.getString("endMonth"), "01") ;//Constant.MR_MONTH()
		String jzpz=Validate.isNullToDefaultString(pd.getString("jzpz"), "1") ;
		mv.addObject("date", year+"年"+startMonth+"月至"+endMonth+"月");
		mv.addObject("year", year);
		mv.addObject("startMonth", startMonth);
		mv.addObject("endMonth", endMonth);
		mv.addObject("months", months);
		mv.addObject("jzpz", jzpz);
		mv.setViewName("kjhs/bbzx/xmjf/xmjfyf_list");
		return mv;
	}
	/**
	 * 初始化页面--部门余额表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBmyebPage")
	public ModelAndView gokmxxPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<Map<String,Object>> months = kmyeService.getMonth();
		//默认获取当前年份
		String year=Validate.isNullToDefaultString(pd.getString("year"), sdf.format(new Date())) ;
		String startMonth=Validate.isNullToDefaultString(pd.getString("startMonth"), "01") ;
		String endMonth=Validate.isNullToDefaultString(pd.getString("endMonth"), "01") ;
		String jzpz=Validate.isNullToDefaultString(pd.getString("jzpz"), "1") ;
		mv.addObject("date", year+"年"+startMonth+"月至"+endMonth+"月");
		mv.addObject("year", year);
		mv.addObject("startMonth", startMonth);
		mv.addObject("endMonth", endMonth);
		mv.addObject("months", months);
		mv.addObject("jzpz", jzpz);
		mv.setViewName("kjhs/bbzx/xmjf/bmye_list");
		return mv;
	}
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Map<String, List<Map<String,Object>>> getPageList(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception {
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = xmjfyeService.getResult(params);
		}
		return result_map;
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
	public void paramSession(HttpServletRequest request, HttpServletResponse response) {
		String params = this.getPageData().getString("params");
		request.getSession().setAttribute("params", params);
		request.getSession().setAttribute("bbqj", this.getPageData().getString("bbqj"));
	}
	private Params getParams(HttpServletRequest request){
		Params params = new Params();
			String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
			String jzpz = Validate.isNullToDefaultString(request.getParameter("jzpz"), "");
			if("1".equals(jzpz)){
				jzpz = "02','99','00','01";
			}else{
				jzpz = "02','99";
			}
			params.setEndMonth(Validate.isNullToDefaultString(request.getParameter("endMonth"), ""));
			params.setStartMonth(Validate.isNullToDefaultString(request.getParameter("startMonth"), ""));
			params.setYear(Validate.isNullToDefaultString(request.getParameter("year"), ""));
			params.setBmbh(Validate.isNullToDefaultString(request.getParameter("bmbh"), ""));
			params.setXmbh(Validate.isNullToDefaultString(request.getParameter("xmbh"), ""));
			params.setTreebh(Validate.isNullToDefaultString(request.getParameter("treebh"), ""));
			params.setTypes(Validate.isNullToDefaultString(request.getParameter("types"), ""));
			//综合查询
			
			String kjzd = zjrbbService.getkjzd(Validate.isNullToDefaultString(request.getParameter("year"), ""));
			params.setSszt(sszt);
			params.setKjzd(kjzd);
			params.setJzpz(jzpz);
		return params;
	}
	@RequestMapping("/doExp")
	@ResponseBody
	public Object Info(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = xmjfyeService.getResult(params);
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
				m.setName("xmmc");//序号直接写rn即可
				m.setShowname("项目名称");//第一行
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
				m.setColstyle("double");
				m.setColtype("Number");
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("jfje");//序号直接写rn即可
				m.setShowname("借方金额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;

				m = new M_largedata();
				m.setColstyle("double");
				m.setColtype("Number");
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("dfje");//序号直接写rn即可
				m.setShowname("贷方金额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setColtype("Number");
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("qmye");//序号直接写rn即可
				m.setShowname("期末余额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				
				tlist.add(mlist);
				tlist.add(mlist1);
				
		System.out.println("WEAEAWE"+list);
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel\\" + shortfileurl;
		String  name = "项目经费余额表.xls";
		kmyeService.ExpExcel(list,realfile,name,mlist2,tlist);
		return "{\"url\":\"excel\\\\"+shortfileurl+"\"}";
		//return this.kmyeService.expExcel(realfile, shortfileurl,"","",list);
	}
	@RequestMapping("/doExpBm")
	@ResponseBody
	public Object doExpBm(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, List<Map<String,Object>>> result_map=new HashMap<String, List<Map<String,Object>>>();
		Params params = this.getParams(request);
		if(params != null){
			result_map = xmjfyeService.getResult(params);
		}
		list = result_map.get("zjList");
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
				m.setName("jfje");//序号直接写rn即可
				m.setShowname("借方金额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;

				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("dfje");//序号直接写rn即可
				m.setShowname("贷方金额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("qmye");//序号直接写rn即可
				m.setShowname("期末余额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				
				tlist.add(mlist);
				tlist.add(mlist1);
				
		System.out.println("WEAEAWE"+list);
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel\\" + shortfileurl;
		String  name = "项目经费余额表.xls";
		kmyeService.ExpExcel(list,realfile,name,mlist2,tlist);
		return "{\"url\":\"excel\\\\"+shortfileurl+"\"}";
		//return this.kmyeService.expExcel(realfile, shortfileurl,"","",list);
	}
	
	/**
	 * 双击跳转页面，部门余额表，项目经济余额表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toUrl")
	public ModelAndView toUrl(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		//部门编号、名称
		String bmbh = Validate.isNullToDefaultString(pd.getString("bmbh"), "");
		String bmmc = Validate.isNullToDefaultString(pd.getString("bmmc"), "");
		//项目编号、名称
		String xmbh = pd.getString("xmbh");
		String xmmc = pd.getString("xmmc");
		//会计期间
		String year = Validate.isNullToDefaultString(pd.getString("year"),"");
		String startMonth = Validate.isNullToDefaultString(pd.getString("startMonth"),"");
		String endMonth = Validate.isNullToDefaultString(pd.getString("endMonth"),"");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"),"");
		//from,判断哪个页面的点击事件：部门余额表bmyeb，项目经济余额表xmjfyeb
		String from =Validate.isNullToDefaultString(pd.get("from"),"");
		mv.addObject("bmbh", bmbh);
		mv.addObject("bmmc", bmmc);
		if(Validate.noNull(xmbh)){//部门余额表不传xmbh
			mv.addObject("xmbh", xmbh);
			mv.addObject("xmmc", xmmc);
		}
		mv.addObject("year", year);
		mv.addObject("startMonth", startMonth);
		mv.addObject("endMonth", endMonth);
		mv.addObject("jzpz", jzpz);
		if("xmjfyeb".equals(from)){
			mv.setViewName("kjhs/bbzx/xmjf/xmjfyf_click_list");
		}else if("bmyeb".equals(from)){
			mv.setViewName("kjhs/bbzx/xmjf/bmye_click_list");
		}
		return mv;
	}
	
	
	/**
	 * 获得页面数据信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMxzPageList")
	@ResponseBody
	public List<Map<String,Object>> getMxzPageList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		StringBuffer sqltext = new StringBuffer();//查询字段
		
		//部门编号、名称
		String bmbh = Validate.isNullToDefaultString(pd.getString("bmbh"), "");
		//项目编号、名称
		String xmbh = Validate.isNullToDefaultString(pd.getString("xmbh"), "");
		//会计期间
		String year = Validate.isNullToDefaultString(pd.getString("year"),"");
		String startMonth = Validate.isNullToDefaultString(pd.getString("startMonth"),"");
		String endMonth = Validate.isNullToDefaultString(pd.getString("endMonth"),"");
		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"),"");
		if("1".equals(jzpz)){
			jzpz = "02','99','00','01";
		}else{
			jzpz = "02','99";
		}
		//会计制度
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);
		//排序方式
		String pxfs = "pzrq";
		
		//创建参数对象
		Params params = new Params();
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		boolean result = xmjfyeService.getResultMxz(bmbh,sszt,xmbh,year,startMonth,endMonth,jzpz,kjzd,pd,"jjkm");//调用存储过程
		if(!result){
			return null;
		}
		return  xmjfyeService.getMxzPageList(bmbh,xmbh,kjzd,pd);//数据查询
	}
	
	
	@RequestMapping("/doExp2")
	@ResponseBody
	public Object Info2(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		//部门编号、名称
		String bmbh = Validate.isNullToDefaultString(pd.getString("bmbh"), "");
		//项目编号、名称
		String xmbh = Validate.isNullToDefaultString(pd.getString("xmbh"), "");
		//会计期间
		String year = Validate.isNullToDefaultString(pd.getString("year"),"");
		//会计制度
		String kjzd = CommonUtil.getKjzdByPzlrAndKmye(request.getSession(),year);

		list=xmjfyeService.getMxzPageList(bmbh,xmbh,kjzd,pd);
		
		List<M_largedata> mlist = new ArrayList<M_largedata>();//存放第一行标题
		List<M_largedata> mlist1 = new ArrayList<M_largedata>();//存放第二行标题
		List<M_largedata> mlist2 = new ArrayList<M_largedata>();//存放数据
		List<List<M_largedata>> tlist = new ArrayList<List<M_largedata>>();
		M_largedata m = new M_largedata();
		
		//导出初始化列表
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("pzrq");//序号直接写rn即可
				m.setShowname("凭证日期");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("pzlxmc");//序号直接写rn即可
				m.setShowname("凭证类型");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("pzbhguid");//序号直接写rn即可
				m.setShowname("凭证号");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("zy");//序号直接写rn即可
				m.setShowname("摘要");//第一行
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
				m.setName("wldwmc");//序号直接写rn即可
				m.setShowname("项目名称");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("kmmc");//序号直接写rn即可
				m.setShowname("会计科目");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("jjkmmc");//序号直接写rn即可
				m.setShowname("经济科目");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setColtype("Number");
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("jfje");//序号直接写rn即可
				m.setShowname("借方金额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;

				m = new M_largedata();
				m.setColstyle("double");
				m.setColtype("Number");
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("dfje");//序号直接写rn即可
				m.setShowname("贷方金额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setColtype("Number");
				m.setIsmerge(true);//合并单元格
				m.setErow(1);//合并行0开始
				m.setName("ye");//序号直接写rn即可
				m.setShowname("余额");//第一行
				mlist.add(m);
				mlist2.add(m);
				m = null;
				
				
				tlist.add(mlist);
				tlist.add(mlist1);
				
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel\\" + shortfileurl;
		String  name = "项目经费明细账.xls";
		kmyeService.ExpExcel(list,realfile,name,mlist2,tlist);
		return "{\"url\":\"excel\\\\"+shortfileurl+"\"}";
	}
}
