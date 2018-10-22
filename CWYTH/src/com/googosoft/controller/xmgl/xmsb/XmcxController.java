package com.googosoft.controller.xmgl.xmsb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xmgl.xmsb.XmcxService;
import com.googosoft.service.xmgl.xmsb.XmsbService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/xmcx")
public class XmcxController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;// 单例
	@Resource(name = "dictService")
	private DictService dictService;// 单例
	@Resource(name = "xmcxService")
	private XmcxService xmcxService;
	@Resource(name = "xmsbService")
	private XmsbService xmsbService;
	@Resource(name="fileService")
	FileService fileService;

	
	/**
	 * 跳转到查询列表页面
	 */
	@RequestMapping(value="/goListPage")
	public ModelAndView goSecondCheckPage(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("xmshztList",dictService.getDict(Constant.XMSHZT));
		PageData pd = this.getPageData();
		mv.setViewName("xmgl/cxtj/xmcx/xmcx_list");
		return mv;
	}
	//获取列表页面信息
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"0");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		pageList.setSqlText("*");
		sql.append("(select guid,xmbh,xmmc,shzt,"
				+ "(select d.mc from gx_sys_dmb d where d.zl='"+Constant.XMSHZT+"' and d.dm=t.shzt)as shztmc,"
				+ "(select '('||xmlxbh||')'||xmlxmc from cw_xmlxb x where x.guid=t.xmlx) as xmlx,"
				+ "(select '('||zybh||')'||zymc from cw_zyxxb a where a.zybh=t.fwzy) as fwzy,"
				+ "(select '('||dwbh||')'||mc from gx_sys_dwb b where b.dwbh=t.sbdw) as sbdw,fwxk,to_char(ysje,'FM999999999999.00') as ysje,sfsndcxlzxm,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb c where c.rybh=t.sbr) as sbr,to_char(sbrq,'yyyy-mm-dd') as sbrq,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb r where r.guid=t.csr) as csr,to_char(csrq,'yyyy-mm-dd') as csrq,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb r where r.guid=t.fsr) as fsr,to_char(fsrq,'yyyy-mm-dd') as fsrq "
				+ "from cw_xmsbxxb t where 1=1");
		if("0".equals(shzt)){
			sql.append(" AND T.SHZT IN('0') ");
		}else if("1".equals(shzt)){
			sql.append(" AND T.SHZT in('1')");
		}else if("2".equals(shzt)){
			sql.append(" AND T.SHZT in('2')");
		}else if("3".equals(shzt)){
			sql.append(" AND T.SHZT in('3')");
		}else if("4".equals(shzt)){
			sql.append(" AND T.SHZT in('4')");
		}else if("5".equals(shzt)){
			sql.append(" AND T.SHZT in('5')");
		}

	
		sql.append(" )A");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = " ";
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 查看
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goLookPage")
	public ModelAndView goLookPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String zbid = pd.getString("guid");
		Map xmcx = xmcxService.getMapXmsbByGuid(guid);
		String xmlxbh = Validate.isNullToDefaultString(xmcx.get("XMLX"),"");
		List wjlxlist = xmsbService.getFjxxByXmlx(xmlxbh);
		iniFile(mv,this.getPageData().getString("guid"));//查询领导签字
		mv.addObject("xmcx", xmcx);
		mv.addObject("wjlxlist", wjlxlist);
		Map mxbxx = xmsbService.getMapMxbByGuid(zbid);
		mv.addObject("jldwList",dictService.getDict(Constant.JLDW));
		mv.addObject("mxbxx",mxbxx);		
		mv.setViewName("xmgl/cxtj/xmcx/xmcx_view");
		return mv;
	}
	//获得领导签字
		public void iniFile(ModelAndView mv,String guid) {
			String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0]);
			mv.addObject("fjConfig",fjxx[1]);
		}
		//导出excel
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
			//设置查询字段名
			PageList pageList = new PageList();
			StringBuffer sqltext = new StringBuffer();
			pageList.setSqlText("*");
			sqltext.append("select guid,xmbh,xmmc,shzt,"
					+ "(select d.mc from gx_sys_dmb d where d.zl='"+Constant.XMSHZT+"' and d.dm=t.shzt)as shztmc,"
					+ "(select '('||xmlxbh||')'||xmlxmc from cw_xmlxb x where x.guid=t.xmlx) as xmlx,"
					+ "(select '('||zybh||')'||zymc from cw_zyxxb a where a.zybh=t.fwzy) as fwzy,"
					+ "(select '('||dwbh||')'||mc from gx_sys_dwb b where b.dwbh=t.sbdw) as sbdw,fwxk,to_char(ysje,'FM999999999999.00') as ysje,"
					+ "(case sfsndcxlzxm when '1' then '是' when '0' then '否' end) as sfsndcxlzxm,"
					+ "(select '('||rybh||')'||xm from gx_sys_ryb c where c.rybh=t.sbr) as sbr,to_char(sbrq,'yyyy-mm-dd') as sbrq,"
					+ "(select '('||rybh||')'||xm from gx_sys_ryb r where r.guid=t.csr) as csr,to_char(csrq,'yyyy-mm-dd') as csrq,"
					+ "(select '('||rybh||')'||xm from gx_sys_ryb r where r.guid=t.fsr) as fsr,to_char(fsrq,'yyyy-mm-dd') as fsrq "
					+ "from cw_xmsbxxb t where 1=1");
			//sqltext.append(" where 1 = 1 and T.SQR='"+LUser.getGuid()+"'");//where条件
			String guid = pd.getString("id");
			String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"0");
			if(Validate.noNull(guid)){
				sqltext.append(" AND T.GUID IN ('"+guid+"') ");
			}else {			
				if("0".equals(shzt)){
					sqltext.append(" AND T.SHZT IN('0') ");
				}else if("1".equals(shzt)){
					sqltext.append(" AND T.SHZT in('1')");
				}else if("2".equals(shzt)){
					sqltext.append(" AND T.SHZT in('2')");
				}else if("3".equals(shzt)){
					sqltext.append(" AND T.SHZT in('3')");
				}else if("4".equals(shzt)){
					sqltext.append(" AND T.SHZT in('4')");
				}else if("5".equals(shzt)){
					sqltext.append(" AND T.SHZT in('5')");
				}
			}
			sqltext.append(" ORDER BY T.XMBH ");
			List<M_largedata> mlist = new ArrayList<M_largedata>();
			M_largedata m1 = new M_largedata();
			m1.setColtype("String");
			m1.setName("shztmc");
			m1.setShowname("审核状态");
			mlist.add(m1);
			M_largedata m2 = new M_largedata();
			m2.setColtype("String");
			m2.setName("xmbh");
			m2.setShowname("项目编号");
			mlist.add(m2);
			M_largedata m3 = new M_largedata();
			m3.setColtype("String");
			m3.setName("xmlx");
			m3.setShowname("项目类型");
			mlist.add(m3);
			M_largedata m4 = new M_largedata();
			m4.setColtype("String");
			m4.setName("fwzy");
			m4.setShowname("服务专业");
			mlist.add(m4);
			M_largedata m5 = new M_largedata();
			m5.setColtype("String");
			m5.setName("fwxk");
			m5.setShowname("服务学科");
			mlist.add(m5);
			M_largedata m6 = new M_largedata();
			m6.setColtype("String");
			m6.setName("ysje");
			m6.setShowname("预算金额（元）");
			mlist.add(m6);
			M_largedata m7 = new M_largedata();
			m7.setColtype("String");
			m7.setName("sfsndcxlzxm");
			m7.setShowname("是否上年度重新论证项目");
			mlist.add(m7);
			M_largedata m8 = new M_largedata();
			m8.setColtype("String");
			m8.setName("sbdw");
			m8.setShowname("申报单位");
			mlist.add(m8);
			M_largedata m9 = new M_largedata();
			m9.setColtype("String");
			m9.setName("sbr");
			m9.setShowname("申报人");
			mlist.add(m9);
			M_largedata m10 = new M_largedata();
			m10.setColtype("String");
			m10.setName("sbrq");
			m10.setShowname("申报日期");
			mlist.add(m10);
			M_largedata m11 = new M_largedata();
			m11.setColtype("String");
			m11.setName("csr");
			m11.setShowname("初审人");
			mlist.add(m11);
			M_largedata m12 = new M_largedata();
			m12.setColtype("String");
			m12.setName("csrq");
			m12.setShowname("初审日期");
			mlist.add(m12);
			M_largedata m13 = new M_largedata();
			m13.setColtype("String");
			m13.setName("fsr");
			m13.setShowname("复审人");
			mlist.add(m13);
			M_largedata m14 = new M_largedata();
			m14.setColtype("String");
			m14.setName("fsrq");
			m14.setShowname("复审日期");
			mlist.add(m14);
			//导出方法
			pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
			return "{\"url\":\"excel\\\\"+file+".xls\"}";
		}


}
