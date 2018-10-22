package com.googosoft.controller.ysgl.yszxfx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.yszxfx.YszxfxService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/yszxfx")
public class YszxfxController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="yszxfxService")
	private YszxfxService yszxfxService;
	
	@RequestMapping(value = "/goMain")
	public ModelAndView goMain() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/yszxfx/main");
		return mv;
	}
	
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(sdf.format(new Date()));
		String firstYear = year+1+"";
		String secondYear = year+2+"";
		String thirdYear = year+3+"";
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("firstYear", firstYear);
		map.put("secondYear", secondYear);
		map.put("thirdYear", thirdYear);
	
		String dm = pd.getString("dm");
		String src = "ysgl/yszxfx/srys_list";
		if("2".equals(dm)){
			src = "ysgl/yszxfx/zxywfzcys_list";
		}else if("3".equals(dm)){
			src = "ysgl/yszxfx/wxzlfzcys_list";
		}else if("4".equals(dm)){
			src = "ysgl/yszxfx/zfcgys_list";
		}
		mv.addObject("map", map);
		mv.setViewName(src);
		return mv;

	}
	/**
	 * 得到项目分类
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXmflList")
	@ResponseBody
	public Object getXmflList() throws Exception {
	
		PageData pd = this.getPageData();
		String qrzt = pd.getString("qrzt");
		List list = yszxfxService.getXmfl(qrzt);
		return list;
	}
	/**
	 * 得到基本支出预算表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getJbzcList")
	@ResponseBody
	public Object getJbzcList() throws Exception {
	
		PageData pd = this.getPageData();
		String qrzt = pd.getString("qrzt");
		String sbnd = pd.getString("sbnd");		

		List list = yszxfxService.getJbzc(qrzt,sbnd);

		return list;
	}
	/**
	 * 通过申报年度得到基本支出预算表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getJbzcSbndList")
	@ResponseBody
	public Object getJbzcSbndList() throws Exception {
	
		PageData pd = this.getPageData();
		String qrzt = pd.getString("qrzt");
		String sbnd = pd.getString("sbnd");
		List list = yszxfxService.getJbzcSbnd(qrzt, sbnd);		
		return list;
	}
	/**
	 * 得到基本支出预算表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateqQrzt")
	@ResponseBody
	public Object updateQrzt() throws Exception {
	    int i = 0;
		PageData pd = this.getPageData();
		String guid1= pd.getString("guid");
		String who = pd.getString("who");
		String guid[] = guid1.split(",");
		//guid 去重
		 List<String> list = new ArrayList<String>();
	        for (int m=0; m<guid.length; m++) {
	            if(!list.contains(guid[m])) {
	                list.add(guid[m]);	                
	            }
	        }
	       for (int n=0;n<list.size();n++) {	    	   
	    	   if("XMZC".equals(who)){
					i=yszxfxService.updateXmzcQrzt(list.get(n));
					
				}else {
					 i = yszxfxService.updateQrzt(list.get(n));

				}	    	   	    	   
	       }		
		return i;
	}
	/**
	 * 得到基本支出预算表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSrysqQrzt")
	@ResponseBody
	public Object updateSrysQrzt() throws Exception {
	    int i = 0;
		PageData pd = this.getPageData();
		String guid1= pd.getString("guid");
		String who = pd.getString("who");
		String guid[] = guid1.split(",");
		
		for(int m=0;m<guid.length;m++) {
			
			String guid123 = guid[m];
			i=yszxfxService.updateSrysQrzt(guid123);

		}
		
		
				
		return i;
	}
	/**
	 * 得到项目支出预算表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXmzcList")
	@ResponseBody
	public Object getXmzcList() throws Exception {
	
		PageData pd = this.getPageData();
		String qrzt = pd.getString("qrzt");
		String sbnd = pd.getString("sbnd");
		List list = yszxfxService.getXmzc(qrzt,sbnd);
		return list;
	}
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {		
		PageData pd = this.getPageData();
		String xmfl = pd.getString("xmfl");
		String qrzt = pd.getString("qrzt");
		String sbnd = pd.getString("sbnd");
//		List list = yszxfxService.getXmflxyj(xmfl, qrzt,sbnd);
		List list = null ;
		return list;
	}
	
	/**
	 * 预算类型树  收入预算、基本支出预算、项目支出预算
	 * @param response
	 * @return
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/yslx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object schoolTree(HttpServletResponse response)
			throws java.io.IOException {
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		if ("get-ys".equals(menu)) {
			return yszxfxService.getYsLx(pd, rootPath);
		} else {
			return "";
		}
	} 
	
	//打印收入预算
	@RequestMapping(value="/SrysPrint")
	public ModelAndView demoPrint(){
		ModelAndView mv = this.getModelAndView();
		String url = "wsbx/gwjdf/PrintSample43";
		mv.setViewName(url);
		return mv;
	}
			/**
			 * 导出基本支出预算汇总表
			 * 
			 * @return
			 */
			@RequestMapping(value="/expExceljbzc",produces ="text/json;charset=UTF-8")
			@ResponseBody
			public Object ExpExcel(){					
				//获得当前年
				Calendar now = Calendar.getInstance(); 
				int year = now.get(Calendar.YEAR);
				int dyn = year+1;
				int den = year+2;
				int dsn = year+3;
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
				sqltext.append("select  a.kmmc as mc,\r\n" + 
						"       b.kmmc,\r\n" + 
						"       decode(sum(nvl(dynzc, '0')),\r\n" + 
						"              '0',\r\n" + 
						"              '0.0000',\r\n" + 
						"              to_char(round(sum(dynzc), 4), 'fm999999999999990.0000')) dynzc,\r\n" + 
						"       decode(sum(nvl(denzc, '0')),\r\n" + 
						"              '0',\r\n" + 
						"              '0.0000',\r\n" + 
						"              to_char(round(sum(denzc), 4), 'fm999999999999990.0000')) denzc,\r\n" + 
						"       decode(sum(nvl(dsnzc, '0')),\r\n" + 
						"              '0',\r\n" + 
						"              '0.0000',\r\n" + 
						"              to_char(round(sum(dsnzc), 4), 'fm999999999999990.0000')) dsnzc,\r\n" + 
						"       wm_concat(to_char(k.guid)) guid,\r\n" + 
						"       wm_concat(to_char(d.mc)) dwmc,\r\n" + 
						"       wm_concat(to_char(t.bz))bz\r\n" + 
						"  from Cw_jbzcysmxb t\r\n" + 
						"  left join Cw_jbzcysb k on k.guid = t.jbzcbh\r\n" + 
						"  left join gx_sys_dwb d on d.dwbh = k.sbbm\r\n" + 
						"  left join CW_JJKMB b on b.guid = t.jjkmbh\r\n" + 
						"  left join cw_jjkmb a on a.kmbh  = b.k");
				
				
				//sqltext.append(ToSqlUtil.jsonToSql(searchJson));

				//String guid = pd.getString("id");			
				//if(Validate.noNull(guid)){
					//sqltext.append(" and a.guid in ('"+guid.replace(",", "','")+"') ");
				//}
				String guid = pd.getString("guid");
				System.out.println("guid========="+guid);
				
				List<M_largedata> mlist = new ArrayList<M_largedata>();
				M_largedata m1 = new M_largedata();
				m1.setColtype("String");
				m1.setName("xh");
				m1.setShowname("序号");
				mlist.add(m1);
				M_largedata m2 = new M_largedata();
				m2.setColtype("String");
				m2.setName("sbry");
				m2.setShowname("申报人员");
				mlist.add(m2);
				M_largedata m3 = new M_largedata();
				m3.setColtype("String");
				m3.setName("sbbm");
				m3.setShowname("申报部门");
				mlist.add(m3);
				M_largedata m4 = new M_largedata();
				m4.setColtype("String");
				m4.setName("sbnd");
				m4.setShowname("申报年度");
				mlist.add(m4);
				M_largedata m5 = new M_largedata();
				m5.setColtype("String");
				m5.setName("dynsrhz");
			//	m5.setShowname(dyn+"年收入汇总（万元）");
				m5.setShowname("第一年度年收入汇总（万元）");
				mlist.add(m5);
				M_largedata m6 = new M_largedata();
				m6.setColtype("String");
				m6.setName("densrhz");
				//m6.setShowname(den+"年收入汇总（万元）");
				m6.setShowname("第二年度年收入汇总（万元）");
				mlist.add(m6);
				M_largedata m7 = new M_largedata();
				m7.setColtype("String");
				m7.setName("dsnsrhz");
				//m7.setShowname(dsn+"年收入汇总（万元）");
				m7.setShowname("第三年度年收入汇总（万元）");
				mlist.add(m7);
				M_largedata m8 = new M_largedata();
				m8.setColtype("String");
				m8.setName("csyj");
				m8.setShowname("测算依据");
				mlist.add(m8);
				//导出方法
				pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
				return "{\"url\":\"excel\\\\"+file+".xls\"}";
			}
}
