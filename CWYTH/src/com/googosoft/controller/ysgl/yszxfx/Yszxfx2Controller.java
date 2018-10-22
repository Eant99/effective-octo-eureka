package com.googosoft.controller.ysgl.yszxfx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.yszxfx.Yszxfx2Service;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/yszxfx2")
public class Yszxfx2Controller extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="dictService")
	DictService dictService;
	
	@Resource(name="yszxfx2Service")
	private Yszxfx2Service yszxfxService;
	
	@RequestMapping(value = "/goMain")
	public ModelAndView goMain() {
		ModelAndView mv = this.getModelAndView();
		System.err.println("zoul______________________");
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
		System.err.println("___________________1"+dm);
		mv.addObject("map", map);
		mv.setViewName(src);
		return mv;

	}
	
	//初始化登录人员信息
		public void iniLogin(ModelAndView mv) {
			mv.addObject("loginId",LUser.getGuid());
			mv.addObject("szbm",LUser.getDwmc());
			mv.addObject("ryxm","("+LUser.getRybh()+")"+LUser.getRyxm());
			mv.addObject("today",DateUtil.getDay());
		}
	
	//跳转到收入预算汇总表页面
			@RequestMapping("/gosrysListPage")
			public ModelAndView gosrysListPage() {
				ModelAndView mv = this.getModelAndView();
				iniLogin(mv);
				mv.addObject("shztList",dictService.getDict(Constant.SHZTDM));
				mv.setViewName("ysgl/yszxfx/srys_list");
				return mv;
			}
			//跳转到收入预算汇总详细表页面
			@RequestMapping("/gosrysMxListPage")
			public ModelAndView gosrysMxListPage() {
				PageData pd = this.getPageData();
				String xmmc = pd.getString("xmmc");
				System.err.println("这是第一个xmmc==="+xmmc);
				ModelAndView mv = this.getModelAndView();
				mv.addObject("xmmc", xmmc);
				iniLogin(mv);
				mv.setViewName("ysgl/yszxfx/srys_mxlist");
				return mv;
			}
		
	//跳转到收入预算汇总表详细信息页面
		@RequestMapping("/getsrysMxListPage")
		@ResponseBody
		public Object getsrysMxListPage() throws Exception {
				PageData pd = this.getPageData();
				String xmmc = pd.getString("xmmc");
				System.err.println("xmmc====="+xmmc);
				StringBuffer sqltext = new StringBuffer();//查询字段
				StringBuffer tablename = new StringBuffer();
				PageList pageList = new PageList();
				sqltext.append(" * ");
				tablename.append("(select guid,rownum as xh, b.xmmc,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=b.tbbm)tbbm,to_char(b.yjwcse,'fm99999999.0000') yjwcse,to_char(b.wcsj,'yyyy-mm-dd') wcsj,b.bz,to_char(b.hj,'FM99999999999999.0000') hj from cw_jbxxb b ");
				tablename.append("where  b.xmmc = '"+xmmc+"'");
				tablename.append(" ) k");
				pageList.setSqlText(sqltext.toString());
				//设置表名
				pageList.setTableName(tablename.toString());
				//设置表主键名
				pageList.setKeyId("GUID");//主键
				//设置WHERE条件
				pageList.setHj1("");//合计
				pageList.setStrWhere("");
			
				pageList = pageService.findPageList(pd,pageList);
				Gson gson = new Gson();
				PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
				return pageInfo.toJson();
		}
	
	//跳转到专项业务总表详细信息页面
		@RequestMapping("/gozxywfzcysMxListPage")
		public ModelAndView gozxywfzcysMxListPage() {
			PageData pd = this.getPageData();
			String xmmc = pd.getString("xmmc");
			System.err.println("这是第一个xmmc==="+xmmc);
			ModelAndView mv = this.getModelAndView();
			mv.addObject("xmmc", xmmc);
			iniLogin(mv);
			mv.setViewName("ysgl/yszxfx/zxywfzcys_mxlist");
			return mv;
		}
		//专项业务汇总表详细信息页面取值
				@RequestMapping("/getzxywfzcysMxListPage")
				@ResponseBody
				public Object getzxywfzcysMxListPage() throws Exception {
						PageData pd = this.getPageData();
						String xmmc = pd.getString("xmmc");
						System.err.println("xmmc====="+xmmc);
						StringBuffer sqltext = new StringBuffer();//查询字段
						StringBuffer tablename = new StringBuffer();
						PageList pageList = new PageList();
						sqltext.append(" * ");
						tablename.append("(select b.guid ,b.tbbm, b.xmmc,to_char(b.xmztz,'FM999999999999.0000') xmztz,to_char(b.xmjssj,'yyyy-mm-dd') xmjssj from cw_zxywfzcysb b ");
						tablename.append("where  b.xmmc = '"+xmmc+"'");
						tablename.append(" ) k");
						pageList.setSqlText(sqltext.toString());
						//设置表名
						pageList.setTableName(tablename.toString());
						//设置表主键名
						pageList.setKeyId("GUID");//主键
						//设置WHERE条件
						pageList.setHj1("");//合计
						pageList.setStrWhere("");
					
						pageList = pageService.findPageList(pd,pageList);
						Gson gson = new Gson();
						PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
						return pageInfo.toJson();
				}
	
				//跳转到维修租赁费支出预算总表详细信息页面
				@RequestMapping("/gowxzlfzcysListPage")
				public ModelAndView gowxzlfzcysListPage() {
					PageData pd = this.getPageData();
					String xmmc = pd.getString("xmmc");
					System.err.println("这是第一个xmmc==="+xmmc);
					ModelAndView mv = this.getModelAndView();
					mv.addObject("xmmc", xmmc);
					iniLogin(mv);
					mv.setViewName("ysgl/yszxfx/wxzlfzcys_mxlist");
					return mv;
				}
				//维修租赁费支出预算总表详细信息页面取值
				@RequestMapping("/getwxzlfzcysMxListPage")
				@ResponseBody
				public Object getwxzlfzcysMxListPage() throws Exception {
					PageData pd = this.getPageData();
					String xmmc = pd.getString("xmmc");
					System.err.println("xmmc====="+xmmc);
					StringBuffer sqltext = new StringBuffer();//查询字段
					StringBuffer tablename = new StringBuffer();
					PageList pageList = new PageList();
					sqltext.append(" * ");
					tablename.append("(select b.guid ,b.tbbm, b.xmmc,b.xmztz,to_char(b.xmjssj,'yyyy-mm-dd') xmjssj from cw_wxzlfzcysb b");
					tablename.append(" where  b.xmmc = '"+xmmc+"'");
					tablename.append(" ) k");
					pageList.setSqlText(sqltext.toString());
					//设置表名
					pageList.setTableName(tablename.toString());
					//设置表主键名
					pageList.setKeyId("GUID");//主键
					//设置WHERE条件
					pageList.setHj1("");//合计
					pageList.setStrWhere("");
					
					pageList = pageService.findPageList(pd,pageList);
					Gson gson = new Gson();
					PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
					return pageInfo.toJson();
				}
				
				//跳转到政府采购预算总表详细信息页面
				@RequestMapping("/gozfcgysListPage")
				public ModelAndView gozfcgysListPage() {
					PageData pd = this.getPageData();
					String pmmc = pd.getString("pmmc");
					System.err.println("这是第一个pmmc==="+pmmc);
					ModelAndView mv = this.getModelAndView();
					mv.addObject("pmmc", pmmc);//将获取到的品目名称的值传递到JSP页面
					iniLogin(mv);
					mv.setViewName("ysgl/yszxfx/zfcgys_mxlist");
					return mv;
				}
				//政府采购预算总表详细信息页面取值
				@RequestMapping("/getzfcgysMxListPage")
				@ResponseBody
				public Object getzfcgysMxListPage() throws Exception {
					PageData pd = this.getPageData();
					String pmmc = pd.getString("pmmc");
					System.err.println("pmmc====="+pmmc);
					StringBuffer sqltext = new StringBuffer();//查询字段
					StringBuffer tablename = new StringBuffer();
					PageList pageList = new PageList();
					sqltext.append(" * ");
					tablename.append("(select b.guid ,b.tbbm, b.pmmc,to_char(b.cgysje,'FM9999999999.0000') cgysje from cw_zfcgysb b");
					tablename.append(" where  b.pmmc = '"+pmmc+"'");
					tablename.append(" ) k");
					pageList.setSqlText(sqltext.toString());
					//设置表名
					pageList.setTableName(tablename.toString());
					//设置表主键名
					pageList.setKeyId("GUID");//主键
					//设置WHERE条件
					pageList.setHj1("");//合计
					pageList.setStrWhere("");
					
					pageList = pageService.findPageList(pd,pageList);
					Gson gson = new Gson();
					PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
					return pageInfo.toJson();
				}
				
		
		//跳转到收入预算汇总表详细信息页面
			@RequestMapping("/gowxzlfzcysMxListPage")
			public ModelAndView gowxzlfzcysMxListPage() {
				ModelAndView mv = this.getModelAndView();
				iniLogin(mv);
				mv.addObject("shztList",dictService.getDict(Constant.SHZTDM));
				mv.setViewName("ysgl/yszxfx/wxzlfzcys_mxlist");
				return mv;
			}
			
		
		//跳转到收入预算汇总表详细信息页面
			@RequestMapping("/gozfcgysMxListPage")
			public ModelAndView gozfcgysMxListPage() {
				ModelAndView mv = this.getModelAndView();
				iniLogin(mv);
				mv.addObject("shztList",dictService.getDict(Constant.SHZTDM));
				mv.setViewName("ysgl/yszxfx/zfcgys_mxlist");
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
	

			/**
			 * 导出基本支出预算汇总表
			 * 
			 * @return
			 *//*
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
			}*/
			
			/**
			 * 查询收入预算表列表数据
			 * @throws Exception
			 */
			@RequestMapping(value = "/getPageList")
			@ResponseBody
			public Object getPageList() throws Exception {
				
				PageData pd = this.getPageData();
				StringBuffer sqltext = new StringBuffer();//查询字段
				PageList pageList = new PageList();
				System.err.println("这句话要开始执行了！!!");
				sqltext.append("guid,xmmc,to_char(yjwcse,'fm99999999.0000') yjwcse,to_char(wcsj,'yyyy-mm-dd') wcsj,bz");
				pageList.setSqlText(sqltext.toString());
				pageList.setKeyId("guid");//主键
				pageList.setTableName("CW_SRYSB");//表名
				pageList.setHj1("");//合计
				System.err.println("这句话已经执行完了！!!");
				pageList.setStrWhere("");
				pageList = pageService.findPageList(pd,pageList);
				Gson gson = new Gson();
				PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
				return pageInfo.toJson();
			}
			/**
			 * 查询收入预算表列表数据
			 * @throws Exception
			 */
			@RequestMapping(value = "/getPageList2")
			@ResponseBody
			public Object getPageList2() throws Exception {
				PageData pd = this.getPageData();
				StringBuffer sqltext = new StringBuffer();//查询字段
				StringBuffer tablename = new StringBuffer();
				PageList pageList = new PageList();
				sqltext.append(" * ");
				tablename.append("(select b.guid,b.xmmc,to_char(b.xmqzsj,'yyyy-mm') xmqzsj,to_char(b.xmjssj,'yyyy-mm') xmjssj,to_char(b.xmztz,'FM9999999999.0000') xmztz,to_char(b.napzjs,'FM9999999999.0000') napzjs,to_char(b.nysjyaps,'FM999999999999.0000') nysjyaps from cw_zxywfzcysb b) k");
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
			 * 查询维修租赁费支出预算汇总表列表数据
			 * @throws Exception
			 */
			@RequestMapping(value = "/getPageList3")
			@ResponseBody
			public Object getPageList3() throws Exception {
				PageData pd = this.getPageData();
				StringBuffer sqltext = new StringBuffer();//查询字段
				StringBuffer tablename = new StringBuffer();
				PageList pageList = new PageList();
				sqltext.append(" * ");
				tablename.append("(select b.guid,b.xmmc,to_char(b.xmqzsj,'yyyy-mm') xmqzsj,to_char(b.xmjssj,'yyyy-mm') xmjssj,to_char(b.xmztz,'FM99999999999999.0000') xmztz,to_char(b.napzjs,'FM99999999999999.0000') napzjs,to_char(b.nysjyaps,'FM9999999999999.0000') nysjyaps from cw_wxzlfzcysb b) k");
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
			 * 查询政府采购预算汇总表列表数据
			 * @throws Exception
			 */
			@RequestMapping(value = "/getPageList4")
			@ResponseBody
			public Object getPageList4() throws Exception {
				PageData pd = this.getPageData();
				StringBuffer sqltext = new StringBuffer();//查询字段
				StringBuffer tablename = new StringBuffer();
				PageList pageList = new PageList();
				sqltext.append(" * ");
				tablename.append("(select b.guid,b.Pmbm,b.Pmmc,b.Sbfl,b.Jhgzs,to_char(b.Dj,'FM99999999999.0') Dj,to_char(b.Cgysje,'FM99999999.0000') Cgysje from cw_zfcgysb b ) k");
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
			//导出收入预算汇总表
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
				String searchValue = pd.getString("searchJson");
				System.out.println("searchValue============="+searchValue);
				StringBuffer sqltext = new StringBuffer();
				sqltext.append(" SELECT");
				sqltext.append(" k.guid as GUID,K.xmmc AS XMMC,to_char(K.yjwcse,'fm999999999999.0000') AS YJWCSE,to_char(K.wcsj,'yyyy-mm-dd') AS WCSJ,K.bz AS BZ");
				sqltext.append(" FROM CW_SRYSB K  where 1=1");
				sqltext.append(ToSqlUtil.jsonToSql(searchValue));
				String guid = pd.getString("guid");
				if(Validate.noNull(guid)){
					sqltext.append(" AND K.GUID IN ('"+guid+"') ");
				}
//				if(Validate.noNull(searchValue)) {
//					sqltext.append(" and '"+searchValue+"'");
//				}
				//sqltext.append(" ORDER BY K.GUID ");
				
				List<M_largedata> mlist = new ArrayList<M_largedata>();
				M_largedata m1 = new M_largedata();
				
				m1.setColtype("String");
				m1.setName("XMMC");
				m1.setShowname("项目名称");
				mlist.add(m1);
				
				M_largedata m2 = new M_largedata();
				m2.setColtype("String");
				m2.setName("YJWCSE");
				m2.setShowname("预计完成数额");
				mlist.add(m2);
				
				M_largedata m3 = new M_largedata();
				m3.setColtype("String");
				m3.setName("WCSJ");
				m3.setShowname("完成时间");
				mlist.add(m3);
				
				M_largedata m4 = new M_largedata();
				m4.setColtype("String");
				m4.setName("BZ");
				m4.setShowname("备注");
				mlist.add(m4);
				
				
				
				//导出方法
				pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
				return "{\"url\":\"excel\\\\"+file+".xls\"}";
			}	
			//导出专项业务费支出表
			@RequestMapping(value="/expExcel2",produces ="text/json;charset=UTF-8")
			@ResponseBody
			public Object ExpExcel2(){
				PageData pd = this.getPageData();
				//临时文件名
				String file = System.currentTimeMillis()+"";
				//文件绝对路径
				String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
				//下载时文件名
				String filedisplay = pd.getString("xlsname") + ".xls";
				//查询数据的sql语句
				String searchValue = pd.getString("searchJson");
				System.out.println("searchValue============="+searchValue);
				StringBuffer sqltext = new StringBuffer();
				sqltext.append(" SELECT");
				sqltext.append(" b.guid as GUID,b.xmmc AS XMMC,to_char(b.xmqzsj,'yyyy-mm')  AS XMQZSJ,to_char(b.XMJSSJ,'yyyy-mm') AS XMJSSJ,to_char(b.XMZTZ,'FM9999999999.0000') AS XMZTZ,to_char(b.NAPZJS,'FM99999999999999.0000') AS NAPZJS,to_char(b.NYSJYAPS,'FM9999999999999.0000') AS NYSJYAPS");
				sqltext.append(" FROM CW_zxywfzcysB b  where 1=1");
				sqltext.append(ToSqlUtil.jsonToSql(searchValue));
				String guid = pd.getString("guid");
				if(Validate.noNull(guid)){
					sqltext.append(" AND b.GUID IN ('"+guid+"') ");
				}
//				if(Validate.noNull(searchValue)) {
//					sqltext.append(" and '"+searchValue+"'");
//				}
				//sqltext.append(" ORDER BY K.GUID ");
				
				List<M_largedata> mlist = new ArrayList<M_largedata>();
				M_largedata m1 = new M_largedata();
				
				m1.setColtype("String");
				m1.setName("XMMC");
				m1.setShowname("项目名称");
				mlist.add(m1);
				
				M_largedata m2 = new M_largedata();
				m2.setColtype("String");
				m2.setName("XMQZSJ");
				m2.setShowname("项目起止时间");
				mlist.add(m2);
				
				M_largedata m3 = new M_largedata();
				m3.setColtype("String");
				m3.setName("XMJSSJ");
				m3.setShowname("项目结束时间");
				mlist.add(m3);
				
				M_largedata m4 = new M_largedata();
				m4.setColtype("String");
				m4.setName("XMZTZ");
				m4.setShowname("项目总投资（万元）");
				mlist.add(m4);
				
				M_largedata m5 = new M_largedata();
				m5.setColtype("String");
				m5.setName("NAPZJS");
				m5.setShowname("2017年安排资金数（万元）");
				mlist.add(m5);
				
				M_largedata m6 = new M_largedata();
				m6.setColtype("String");
				m6.setName("NYSJYAPS");
				m6.setShowname("2018年预算建议安排数（万元）");
				mlist.add(m6);
				
				//导出方法
				pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
				return "{\"url\":\"excel\\\\"+file+".xls\"}";
			}	
			//导出维修租赁费支出预算表
			@RequestMapping(value="/expExcel3",produces ="text/json;charset=UTF-8")
			@ResponseBody
			public Object ExpExcel3(){
				PageData pd = this.getPageData();
				//临时文件名
				String file = System.currentTimeMillis()+"";
				//文件绝对路径
				String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
				//下载时文件名
				String filedisplay = pd.getString("xlsname") + ".xls";
				//查询数据的sql语句
				String searchValue = pd.getString("searchJson");
				System.out.println("searchValue============="+searchValue);
				StringBuffer sqltext = new StringBuffer();
				sqltext.append(" SELECT");
				sqltext.append(" b.guid as GUID,b.xmmc AS XMMC,to_char(b.xmqzsj,'yyyy-mm')  AS XMQZSJ,to_char(b.XMJSSJ,'yyyy-mm') AS XMJSSJ,to_char(b.XMZTZ,'FM9999999999.0000') AS XMZTZ,to_char(b.NAPZJS,'FM9999999999.0000') AS NAPZJS,to_char(b.NYSJYAPS,'FM999999999999999.0000') AS NYSJYAPS");
				sqltext.append(" FROM CW_WXZLFZCYSB b  where 1=1");
				sqltext.append(ToSqlUtil.jsonToSql(searchValue));
				String guid = pd.getString("guid");
				if(Validate.noNull(guid)){
					sqltext.append(" AND b.GUID IN ('"+guid+"') ");
				}
//				if(Validate.noNull(searchValue)) {
//					sqltext.append(" and '"+searchValue+"'");
//				}
				//sqltext.append(" ORDER BY K.GUID ");
				
				List<M_largedata> mlist = new ArrayList<M_largedata>();
				M_largedata m1 = new M_largedata();
				
				m1.setColtype("String");
				m1.setName("XMMC");
				m1.setShowname("项目名称");
				mlist.add(m1);
				
				M_largedata m2 = new M_largedata();
				m2.setColtype("String");
				m2.setName("XMQZSJ");
				m2.setShowname("项目起止时间");
				mlist.add(m2);
				
				M_largedata m3 = new M_largedata();
				m3.setColtype("String");
				m3.setName("XMJSSJ");
				m3.setShowname("项目结束时间");
				mlist.add(m3);
				
				M_largedata m4 = new M_largedata();
				m4.setColtype("String");
				m4.setName("XMZTZ");
				m4.setShowname("项目总投资（万元）");
				mlist.add(m4);
				
				M_largedata m5 = new M_largedata();
				m5.setColtype("String");
				m5.setName("NAPZJS");
				m5.setShowname("2017年安排资金数（万元）");
				mlist.add(m5);
				
				M_largedata m6 = new M_largedata();
				m6.setColtype("String");
				m6.setName("NYSJYAPS");
				m6.setShowname("2018年预算建议安排数（万元）");
				mlist.add(m6);
				
				//导出方法
				pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
				return "{\"url\":\"excel\\\\"+file+".xls\"}";
			}	
			
			
			//导出政府采购预算表
			@RequestMapping(value="/expExcel4",produces ="text/json;charset=UTF-8")
			@ResponseBody
			public Object ExpExcel4(){
				PageData pd = this.getPageData();
				//临时文件名
				String file = System.currentTimeMillis()+"";
				//文件绝对路径
				String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
				//下载时文件名
				String filedisplay = pd.getString("xlsname") + ".xls";
				//查询数据的sql语句
				String searchValue = pd.getString("searchJson");
				System.out.println("searchValue============="+searchValue);
				StringBuffer sqltext = new StringBuffer();
				sqltext.append(" SELECT");
				sqltext.append(" b.guid as GUID,b.PMBM AS PMBM,b.PMMC AS PMMC,B.SBFL AS SBFL,B.JHGZS AS JHGZS,to_char(B.DJ,'FM99999999999.0') AS DJ,to_char(B.CGYSJE,'FM99999999999999.0000') AS CGYSJE");
				sqltext.append(" FROM CW_zfcgysb b  where 1=1");
				sqltext.append(ToSqlUtil.jsonToSql(searchValue));
				String guid = pd.getString("guid");
				if(Validate.noNull(guid)){
					sqltext.append(" AND b.GUID IN ('"+guid+"') ");
				}
//				if(Validate.noNull(searchValue)) {
//					sqltext.append(" and '"+searchValue+"'");
//				}
				//sqltext.append(" ORDER BY K.GUID ");
				
				List<M_largedata> mlist = new ArrayList<M_largedata>();
				M_largedata m1 = new M_largedata();
				
				m1.setColtype("String");
				m1.setName("PMBM");
				m1.setShowname("品目编码");
				mlist.add(m1);
				
				M_largedata m2 = new M_largedata();
				m2.setColtype("String");
				m2.setName("PMMC");
				m2.setShowname("品目名称");
				mlist.add(m2);
				
				M_largedata m3 = new M_largedata();
				m3.setColtype("String");
				m3.setName("SBFL");
				m3.setShowname("设备分类");
				mlist.add(m3);
				
				M_largedata m4 = new M_largedata();
				m4.setColtype("String");
				m4.setName("JHGZS");
				m4.setShowname("计划购置数");
				mlist.add(m4);
				
				M_largedata m5 = new M_largedata();
				m5.setColtype("String");
				m5.setName("DJ");
				m5.setShowname("单价（元）");
				mlist.add(m5);
				
				M_largedata m6 = new M_largedata();
				m6.setColtype("String");
				m6.setName("CGYSJE");
				m6.setShowname("采购预算金额（万元）");
				mlist.add(m6);
				
				//导出方法
				pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
				return "{\"url\":\"excel\\\\"+file+".xls\"}";
			}	
			//打印收入预算
			@RequestMapping(value="/SrysPrint")
			public ModelAndView demoPrint(){
				ModelAndView mv = this.getModelAndView();
				List<Map<String, Object>> list = yszxfxService.getPrintList();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		        String time = df.format(new Date());// new Date()为获取当前系统时间
				mv.addObject("time",time);
				mv.addObject("SrysList",list);
				mv.setViewName("ysgl/yszxfx/PrintSrys");
				return mv;
			}
			/**
			 * 打印专项业务费支出
			 * @return
			 */
			@RequestMapping(value="/ZxywfzcPrint")
			public ModelAndView ZxywfzcPrint(){
				
				ModelAndView mv = this.getModelAndView();
				List<Map<String, Object>> list = yszxfxService.getZxywPrintList();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		        String time = df.format(new Date());// new Date()为获取当前系统时间
				mv.addObject("time",time);
				mv.addObject("ZxywList",list);
				mv.setViewName("ysgl/yszxfx/PrintZxyw");
				return mv;
			}
			/**
			 * 打印维修租赁
			 * @return
			 */
			@RequestMapping(value="/WxzlPrint")
			public ModelAndView WxzlPrint(){
				
				ModelAndView mv = this.getModelAndView();
				List<Map<String, Object>> list = yszxfxService.getWxzlPrintList();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		        String time = df.format(new Date());// new Date()为获取当前系统时间
				mv.addObject("time",time);
				mv.addObject("WxzlList",list);
				mv.setViewName("ysgl/yszxfx/PrintWxzl");
				return mv;
			}
			/**
			 * 打印政府采购
			 * @return
			 */
			@RequestMapping(value="/ZfcgPrint")
			public ModelAndView ZfcgPrint(){
				
				ModelAndView mv = this.getModelAndView();
				List<Map<String, Object>> list = yszxfxService.getZfcgPrintList();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		        String time = df.format(new Date());// new Date()为获取当前系统时间
				mv.addObject("time",time);
				mv.addObject("ZfcglList",list);
				mv.setViewName("ysgl/yszxfx/PrintZfcg");
				return mv;
			}
	
			
}
