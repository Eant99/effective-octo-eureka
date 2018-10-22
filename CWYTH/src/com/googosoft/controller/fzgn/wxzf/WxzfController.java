package com.googosoft.controller.fzgn.wxzf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.wxzf.CW_CBSXX;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.wxzf.WxzfService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/wxzf")
public class WxzfController extends BaseController{

	@Resource(name="wxzfService")
	private WxzfService objService;
	
	@Resource(name="dictService")
	private DictService dictService;
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	
	@RequestMapping(value = "/goPageList")
	public ModelAndView goSecondPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("zfztList",dictService.getDict(Constant.ZFZT));
		mv.setViewName("fzgn/wxzf/wxzf_list");
		return mv;
	}
	/**
	 * 跳转到消费记录查询列表页面
	 * @return
	 */
	@RequestMapping(value = "/goPageListNew")
	public ModelAndView goPageListNew() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("ywlxlist", dictService.getDict("ywlx"));
		mv.addObject("zfztList",dictService.getDict(Constant.ZFZT));
		mv.addObject("xqlist", dictService.getDict("xq"));
		mv.setViewName("fzgn/wxzf/wxzf_list_new");
		return mv;

	}
	/**
	 * 跳转到消费结算列表页面
	 * @return
	 */
	@RequestMapping(value = "/goPageListXfjs")
	public ModelAndView goPageListXfjs() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String zfzt = pd.getString("zfzt");
		String zffs = pd.getString("zffs");
		String xfsj1 = pd.getString("xfsj1");
		String xfsj2 = pd.getString("xfsj2");
		mv.addObject("zfztList",dictService.getDict(Constant.ZFZT));
		mv.addObject("zfzt", zfzt);
		mv.addObject("zffs", zffs);
		mv.addObject("xfsj1", xfsj1);
		mv.addObject("xfsj2", xfsj2);
		mv.addObject("xqlist", dictService.getDict("xq"));
		mv.setViewName("fzgn/wxzf/xfjs_list");
		return mv;
	}
	/**
	 * 获取消费记录查询列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		String zfzt = Validate.isNullToDefaultString(this.getPageData().getString("zfzt"), "01");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" k.*");		
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("(select A.* from (select t.guid,xb.rygh,(select mc from gx_sys_dmb where zl = 'xq' and dm=b.zlwz) zlwzmc,b.zlwz,xb.ryxm,'('||xb.rygh||')'||xb.ryxm as names,(Select MC from GX_SYS_DWB where Dwbh = (select dwbh from gx_sys_ryb"
                              +" where rybh = xb.rygh)) as SSDWMC,CASE xb.type WHEN 'T' THEN '教师' WHEN 'S' THEN '学生' end as rylx,"
                              +" t.xfdd,t.sscbs,to_char(t.xfsj, 'yyyy-mm-dd') as xfsj,to_char(t.xfsj, 'yyyy-mm-dd hh24:mi:ss') as xfsjsfm,to_char(t.xfje, '99999999999990.99') xfje,t.zfzt,"
                              +" B.XFDDBH, B.xfddmc,C.CBSBH,C.CBSMC AS SSCBSMC,CASE t.paymethod WHEN 'ali' THEN '支付宝'"
                              +" WHEN 'wx' THEN '微信' end as zffs,b.ywlx,(select d.mc from gx_sys_dmb d where d.zl='ywlx'and d.dm=b.ywlx)ywlxmc"
                              +" from CW_WXZFMXB t"
                              +" left join cw_wxxxb xb on t.payaccount = xb.openid"
                              +" left join cw_xfddgl B on t.xfdd = B.Guid"
                              +" left join Cw_Cbsxx C ON c.guid = t.sscbs"
                              +" where t.paymethod = 'wx'"
                          	  +" ) A"
                          	  +" where 1 = 1"
                          	  +" union"
                          	  +" select B.* from (select t.guid,xb.rygh,(select mc from gx_sys_dmb where zl = 'xq' and dm=b.zlwz) zlwzmc,b.zlwz,xb.ryxm,'('||xb.rygh||')'||xb.ryxm as names,(Select MC"
                              +" from GX_SYS_DWB where Dwbh = (select dwbh from gx_sys_ryb where rybh = xb.rygh)) as SSDWMC,"
                              +" CASE xb.type WHEN 'T' THEN '教师' WHEN 'S' THEN '学生' end as rylx,"
                              +" t.xfdd,t.sscbs,to_char(t.xfsj, 'yyyy-mm-dd') as xfsj,to_char(t.xfsj, 'yyyy-mm-dd hh24:mi:ss') as xfsjsfm,to_char(t.xfje, '99999999999990.99') xfje,"
                              +" t.zfzt,B.XFDDBH,B.xfddmc,C.CBSBH,C.CBSMC AS SSCBSMC,"
                              +" CASE t.paymethod WHEN 'ali' THEN '支付宝' WHEN 'wx' THEN '微信' end as zffs,b.ywlx,(select d.mc from gx_sys_dmb d where d.zl='ywlx'and d.dm=b.ywlx)ywlxmc"
		                      +" from CW_WXZFMXB t left join cw_wxxxb xb on t.payaccount = xB.Aliaccout"
		                      +" left join cw_xfddgl B on t.xfdd = B.Guid left join Cw_Cbsxx C ON c.guid = t.sscbs"
		                      +" where t.paymethod = 'ali' ) B where 1 = 1 )K ");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();
//		if(Validate.noNull(zfzt)){
//			strWhere+=" and k.zfzt='"+zfzt+"'";
//		}
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 根据承包商，汇总支付信息列表
	 * 获得消费结算列表信息数据
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHzPageList")
	@ResponseBody
	public Object getHzPageList() throws Exception {
		PageList pageList = new PageList();	
		PageData pd = this.getPageData();
		// 设置查询字段名
		String sqlx = "*";
		StringBuffer sqltext = new StringBuffer();
		// 表名
		String zfzt = Validate.isNullToDefaultString(this.getPageData().getString("zfzt"), "");
		String zffs = Validate.isNullToDefaultString(this.getPageData().getString("zffs"), "");
		String xfsj1 = Validate.isNullToDefaultString(this.getPageData().getString("xfsj1"), "");
		String xfsj2 = Validate.isNullToDefaultString(this.getPageData().getString("xfsj2"), "");
		// 表名
		sqltext.append("(select rownum,z.* from(SELECT D.ZFZTMC,D.zfzt,zlwzmc,zlwz,D.paymethod,D.zffs,D.cbsbh,D.cbsmc,TO_CHAR(SUM(D.xfje), '99999999999990.99')xfje, TO_CHAR((0.95*SUM(D.xfje)),'99999999999990.99') AS ZFZE FROM ");
		sqltext.append("(SELECT (CASE T.zfzt WHEN '01' THEN '未支付' WHEN '02' THEN '支付中' WHEN '03' THEN '已支付' end)ZFZTMC,T.zfzt,(select mc from gx_sys_dmb where zl = 'xq' and dm=x.zlwz) zlwzmc,x.zlwz,");
		sqltext.append("T.sscbs,c.cbsbh,c.cbsmc,X.xfddbh,X.xfddmc,");
		sqltext.append("t.paymethod,(CASE t.paymethod WHEN 'wx' THEN '微信' WHEN 'ali' THEN '支付宝' END)zffs,t.xfje");
		sqltext.append(" from Cw_Wxzfmxb t ");
		sqltext.append(" left join cw_xfddgl x on x.guid=t.xfdd");
		sqltext.append(" LEFT JOIN CW_CBSXX C ON C.GUID = T.SSCBS where c.sftj='1'" );
		if(Validate.noNull(zfzt)){
			sqltext.append( " and t.zfzt='"+zfzt+"' " );  //
		} 
		if(Validate.noNull(zffs)){
			sqltext.append( " and t.paymethod='"+zffs+"' " );
		}
		if(Validate.noNull(xfsj1)){
			sqltext.append( " and to_date(to_char(t.xfsj, 'yyyy-mm-dd'), 'yyyy-MM-dd') >= to_date('"+xfsj1+"', 'yyyy-MM-dd') " );
		}
		if(Validate.noNull(xfsj2)){
			sqltext.append( " and to_date(to_char(t.xfsj, 'yyyy-mm-dd'), 'yyyy-MM-dd') <= to_date('"+xfsj2+"', 'yyyy-MM-dd') " );
		}
		sqltext.append(" )D GROUP BY  ZFZT, PAYMETHOD, CBSBH, CBSMC,zlwz)  Z)X");                   

		pageList.setSqlText(sqlx);
		pageList.setKeyId("cbsbh");//主键
		pageList.setTableName(sqltext.toString());//表名
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();

	}
	/**
	 * 根据承包商，汇总支付信息列表
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHzTjPageList")
	@ResponseBody
	public Object getHzTjPageList() throws Exception {
//		PageList pageList = new PageList();	
		PageData pd = this.getPageData();
		String zfzt = Validate.isNullToDefaultString(this.getPageData().getString("zfzt"), "");
		String zffs = Validate.isNullToDefaultString(this.getPageData().getString("zffs"), "");
		if("微信".equals(zffs)){
			zffs = "wx";
		}else if("支付宝".equals(zffs)){
			zffs = "ali";
		}
		String ywlx = Validate.isNullToDefaultString(this.getPageData().getString("ywlx"), "");
		String cbsmc = Validate.isNullToDefaultString(this.getPageData().getString("cbsmc"), "");
		String xfddmc = Validate.isNullToDefaultString(this.getPageData().getString("xfddmc"), "");
		String kssj = Validate.isNullToDefaultString(this.getPageData().getString("kssj"), "");
		String jssj = Validate.isNullToDefaultString(this.getPageData().getString("jssj"), "");
		String zlwz = Validate.isNullToDefaultString(this.getPageData().getString("zlwz"), "");
		// 设置查询字段名
//		StringBuffer sql = new StringBuffer();
		String sqlx = "*";
		StringBuffer sqltext = new StringBuffer();//查询字段
		// 表名
		sqltext.append( " (select * from ( select a.*,to_char(ROWNUM) AS XH from (" );
		sqltext.append( " select sum(xfje) as ze,k.xfddmc,k.zlwz,(select mc from gx_sys_dmb where zl = 'xq' and dm=k.zlwz) zlwzmc,s.cbsmc ");
		sqltext.append( " from cw_wxzfmxb t" );
		sqltext.append( " left join cw_xfddgl k on t.xfdd = k.guid " );
		sqltext.append( " left join cw_cbsxx s on t.sscbs = s.guid where 1=1" );
		if(Validate.noNull(zfzt)){
			sqltext.append( " and t.zfzt='"+zfzt+"' " );  //
		} 
		if(Validate.noNull(zffs)){
			sqltext.append( " and t.paymethod='"+zffs+"' " );
		}
		if(Validate.noNull(ywlx)){
			sqltext.append( " and k.ywlx='"+ywlx+"' " );
		}
		if(Validate.noNull(cbsmc)){
			sqltext.append( " and s.cbsmc='"+cbsmc+"' " );
		}
		if(Validate.noNull(xfddmc)){
			sqltext.append( " and k.xfddmc = '"+xfddmc+"' " );
		}
		if(Validate.noNull(zlwz)){
			sqltext.append( " and k.zlwz = '"+zlwz+"' " );
		}
		if(Validate.noNull(kssj)){
			sqltext.append( " and '"+kssj+"'<=to_char(t.xfsj,'yyyy-mm-dd hh24:mi:ss')" );
		}
		if(Validate.noNull(jssj)){
			sqltext.append( " and to_char(t.xfsj,'yyyy-mm-dd hh24:mi:ss')<='"+jssj+"'" );
		}
		sqltext.append( " group by k.xfddmc,s.cbsmc,k.zlwz ORDER BY  (XFDDMC) " );
		sqltext.append( " ) A " );
		sqltext.append( " UNION " );
		sqltext.append( " select sum(xfje) as ze, '-' as xfddmc,'' as zlwz,null as zlwzmc,'合计' as cbsmc," );
		sqltext.append( " '1188899' AS xh ");
		sqltext.append(	" from cw_wxzfmxb t" );
		sqltext.append( " left join cw_xfddgl k on t.xfdd = k.guid " );
		sqltext.append( " left join cw_cbsxx s on t.sscbs = s.guid where 1=1" );
		if(Validate.noNull(zfzt)){
			sqltext.append( " and t.zfzt='"+zfzt+"' " );  //
		} 
		if(Validate.noNull(zffs)){
			sqltext.append( " and t.paymethod='"+zffs+"' " );
		}
		if(Validate.noNull(ywlx)){
			sqltext.append( " and k.ywlx='"+ywlx+"' " );
		}
		if(Validate.noNull(cbsmc)){
			sqltext.append( " and s.cbsmc='"+cbsmc+"' " );
		}
		if(Validate.noNull(zlwz)){
			sqltext.append( " and k.zlwz = '"+zlwz+"' " );
		}
		if(Validate.noNull(xfddmc)){
			sqltext.append( " and k.xfddmc = '"+xfddmc+"' " );
		}
		if(Validate.noNull(kssj)){
			sqltext.append( " and '"+kssj+"'<=to_char(t.xfsj,'yyyy-mm-dd hh24:mi:ss')" );
		}
		if(Validate.noNull(jssj)){
			sqltext.append( " and to_char(t.xfsj,'yyyy-mm-dd hh24:mi:ss')<='"+jssj+"'" );
		}
		sqltext.append(	" ) k ORDER BY to_number(XH) )");
		// 主键
		PageList pageList = new PageList();
		pageList.setSqlText(sqlx);
		pageList.setKeyId("xfddmc");//主键
		pageList.setTableName(sqltext.toString());//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageListA(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 消费记录查询统计导出
	 * @author 作者：BiMing
	 * @version 创建时间:2018-4-14下午3:18:19
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object expExcel2(HttpServletRequest request,HttpSession session) {
		PageData pd = this.getPageData();
		String guid = pd.getString("id");
		String zfzt = Validate.isNullToDefaultString(this.getPageData().getString("zfzt"), "");
		String zffs = Validate.isNullToDefaultString(this.getPageData().getString("zffs"), "");
		if("微信".equals(zffs)){
			zffs = "wx";
		}else if("支付宝".equals(zffs)){
			zffs = "ali";
		}
		String ywlx = Validate.isNullToDefaultString(this.getPageData().getString("ywlx"), "");
		String cbsmc = Validate.isNullToDefaultString(this.getPageData().getString("cbsmc"), "");
		String xfddmc = Validate.isNullToDefaultString(this.getPageData().getString("xfddmc"), "");
		String kssj = Validate.isNullToDefaultString(this.getPageData().getString("kssj"), "");
		String jssj = Validate.isNullToDefaultString(this.getPageData().getString("jssj"), "");
		String sszt = Constant.getztid(session);
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.objService.expExcel(realfile, shortfileurl,searchValue,guid,sszt,zfzt,zffs,ywlx,cbsmc,xfddmc,kssj,jssj);
	}
	/**
	 * 支付页面
	 * @return
	 */
	@RequestMapping(value="/goZfPage")
	public ModelAndView goZfPage(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");//获取承包商guid
		Map map = objService.getZfxxByGuid(guid);
		List ewmList = fileService.getFjList(guid);
		String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
		if(ewmList.size()==1){
			Map ewmMap = (Map) ewmList.get(0);
			map.put("imagurl",xnlu_path+"/"+ewmMap.get("path") );
		}
		map.put("guid", guid);
		ModelAndView mv = this.getModelAndView();
		
		mv.addObject("map", map);
		mv.setViewName("fzgn/wxzf/zfje");
		
		return mv;
	}
	
	@RequestMapping(value = "/doUpdateZfzt", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdateZfzt() {
		String guid = this.getPageData().getString("guid");
		
		int result = 0;
		 result = objService.updateZfzt(guid);
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！',guid:'" + guid
						+ "'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		
	}
	
	@RequestMapping(value = "/ToWindow")
	public ModelAndView ToWindow() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = Validate.isNullToDefaultString(pd.getString("controlId"), "");
		String url = "fzgn/wxzf/cbsList";
		if("txt_xfdd".equals(controlId)){
			url = "fzgn/wxzf/xfddList";
		}
		mv.addObject(controlId, controlId);
		mv.setViewName(url);
		return mv;

	}
	
	@RequestMapping(value = "/getCbs")
	@ResponseBody
	public Object getCbsPageList() throws Exception {
		PageList pageList = new PageList();		
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append("*");
		pageList.setSqlText(sql.toString());
		// 表名
		// 主键
		pageList.setKeyId("GUID");
		pageList.setTableName(" CW_CBSXX T");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();		
		pageList.setStrWhere(strWhere);
		pageList = pageService.findWindowList(pd,pageList,"CBSA");//引用传递
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
//		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping("/expExcel")
	@ResponseBody
	public Object expExcel() {
		PageData pd=this.getPageData();
		List result_list=new ArrayList<>();
		result_list = objService.getResult(pd);
		List<M_largedata> mlist1 = new ArrayList<M_largedata>();//存放第一行标题
		List<M_largedata> mlist2 = new ArrayList<M_largedata>();//存放数据
		List<List<M_largedata>> tlist = new ArrayList<List<M_largedata>>();
		M_largedata m = new M_largedata();
		
		//导出初始化列表
		        
	            m.setSindex(0);//开始列0开始
				m.setName("zfztmc");//序号直接写rn即可
				m.setShowname("支付状态");//第一行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setName("zffs");//序号直接写rn即可
				m.setShowname("支付方式");//第一行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				
				m.setName("ZLWZMC");
				m.setShowname("所属校区");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setName("cbsmc");
				m.setShowname("承包商名称");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setName("XFJE");
				m.setShowname("消费金额");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				m = new M_largedata();
				m.setColstyle("double");
				m.setName("ZFZE");
				m.setShowname("支付金额");//第二行
				mlist1.add(m);
				mlist2.add(m);
				m = null;
				
				
				tlist.add(mlist1);
				
		String shortfileurl = "" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel\\" + shortfileurl;
		String  name = "消费管理表.xls";
		objService.ExpExcel(result_list,realfile,name,mlist2,tlist);
		return "{\"url\":\"excel\\\\"+shortfileurl+"\"}";
		//return this.kmyeService.expExcel(realfile, shortfileurl,"","",list);
	}
	@RequestMapping(value = "/getXfdd")
	@ResponseBody
	public Object getXfddPageList() throws Exception {
		PageList pageList = new PageList();		
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append("*");
		pageList.setSqlText(sql.toString());
		// 表名
		// 主键
		pageList.setKeyId("GUID");
		pageList.setTableName(" Cw_Xfddgl T");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();		
		pageList.setStrWhere(strWhere);
		pageList = pageService.findWindowList(pd,pageList,"XFDDA");//引用传递
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
//		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
}
