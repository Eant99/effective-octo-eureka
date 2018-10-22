package com.googosoft.controller.kjhs.bbzx;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
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
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.pojo.kjhs.bbzx.Zjrbb;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.bbzx.KmyeService;
import com.googosoft.service.kjhs.bbzx.XjrjzService;
import com.googosoft.service.kjhs.bbzx.ZjrbbService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value = "/zjrbb")
public class ZjrbbController extends BaseController {
	@Resource(name = "dictService")
	private DictService dictService;// 数据字典单例

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@Resource(name = "zjrbbService")
	private ZjrbbService zjrbbService;
	
	@Resource(name = "kmyeService")
	private KmyeService kmyeService;
	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toUrl")
	public ModelAndView gokmxxPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String bbqj = Validate.isNullToDefaultString(pd.getString("bbqj"),Validate.isNullToDefaultString(request.getSession().getAttribute("bbqj"), ""));
		String kmxx = Validate.isNullToDefaultString(pd.getString("kmxx"), "");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		if(Validate.noNull(pd.getString("kmbh"))&&Validate.noNull(pd.getString("kmmc"))&&Validate.isNull(kmxx)){
			kmxx = Validate.isNullToDefaultString(pd.getString("kmxx"), "("+pd.getString("kmbh")+")"+pd.getString("kmmc"));
		}
		String pxfs = Validate.isNullToDefaultString(pd.getString("pxfs"), "");
		String jump = Validate.isNullToDefaultString(pd.getString("jump"), "");
		String clicks = Validate.isNullToDefaultString(pd.getString("clicks"), "");
		if("yes".equals(jump)){
			zjrbbService.deleteZjrbb();
			request.getSession().removeAttribute("params");
			request.getSession().removeAttribute("bbqj");
		}
		String url = "kjhs/bbzx/zjrbb";
		mv.addObject("treesearch", treesearch);
		mv.addObject("bbqj", bbqj);
		mv.addObject("kmxx", kmxx);
		mv.addObject("pxfs", pxfs);
		mv.addObject("clicks", clicks);
		mv.addObject("jump", jump);
		mv.addObject("kmmc", pd.getString("kmmc"));
		mv.addObject("kmbh", pd.getString("kmbh"));
		mv.setViewName(url);
		return mv;
	}

	/**
	 * 弹出条件选择页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jumpWindow")
	public ModelAndView jumpWindow(HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//科目级别list
		List list = zjrbbService.getKjkmJb();
		String kmmc = pd.getString("kmmc");
		mv.addObject("list", list);
		mv.addObject("kmmc", kmmc);
		String lxsj = request.getSession().getAttribute("lxsj")+"";
		if(Validate.noNull(lxsj)){
			mv.addObject("lxsj",lxsj);
		}
		mv.addObject("jsfsList",dictService.getDict(Constant.ZFFSDM));
		mv.setViewName("kjhs/bbzx/zjrbb_search");
		return mv;
	}
	
	/**
	 * ajax处理session
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/paramSession", produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public String paramSession(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException {
		PageData pd = this.getPageData();
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List<Object> list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list		
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		String kjzd = CommonUtil.getKjzd(session);
		List<String> xjrjzlist=new ArrayList<String>();
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map		
			
			String lxsj = Validate.isNullToDefaultString((String)map.get("lxsj"),"");
			String zj_kjkm = Validate.isNullToDefaultString((String)map.get("zj_kjkm"),"");
			String zj_cnr =Validate.isNullToDefaultString((String)map.get("zj_cnr"),"");
			if(zj_kjkm!=""){
				zj_kjkm=zj_kjkm.substring(1, zj_kjkm.lastIndexOf(")"));
			}
			String jsfs =Validate.isNullToDefaultString((String)map.get("jsfs"),"");						
			String bhqbjzpz =Validate.isNullToDefaultString((String)map.get("bhqbjzpz"),"");
			String xsszmx =Validate.isNullToDefaultString((String)map.get("xsszmx"),"");
			String bhyfhwjzpz =Validate.isNullToDefaultString((String)map.get("bhyfhwjzpz"),"");
			String zxscnwfhpz =Validate.isNullToDefaultString((String)map.get("zxscnwfhpz"),"");
			String hzxssbhsykm =Validate.isNullToDefaultString((String)map.get("hzxssbhsykm"),"");
							
			xjrjzlist.add(lxsj);
			xjrjzlist.add(zj_kjkm);
			xjrjzlist.add(zj_cnr);
			xjrjzlist.add(jsfs);
			xjrjzlist.add(bhqbjzpz);
			xjrjzlist.add(xsszmx);
			xjrjzlist.add(bhyfhwjzpz);
			xjrjzlist.add(zxscnwfhpz);
			xjrjzlist.add(hzxssbhsykm);
			xjrjzlist.add(sszt);
			xjrjzlist.add(LUser.getGuid());
			xjrjzlist.add(kjzd);
		}
		boolean bl=zjrbbService.runPro("pro_cwyth_zjrbb",xjrjzlist);
		return bl+"";	
	
	}	
	@RequestMapping(value = "/paramSession1", produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public void paramSession1(HttpServletRequest request,
			HttpServletResponse response) {
		String params = this.getPageData().getString("json");
		String lxsj = this.getPageData().getString("lxsj");
		request.getSession().setAttribute("params", params);
		request.getSession().setAttribute("lxsj", lxsj);
	}
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public List<Map<String,Object>> getPageList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		String kmbh=Validate.isNullToDefaultString(pd.getString("kmbh"),"");
		if(kmbh!=""&&kmbh.contains("(")){
			kmbh=kmbh.substring(1, kmbh.lastIndexOf(")"));					
		}
		List<Map<String,Object>>  list=new ArrayList<Map<String,Object>>();
		list=zjrbbService.getPageList(kmbh);
		return list;
	}
	@RequestMapping(value = "/getPageList1")
	@ResponseBody
	public List<Map<String,Object>> getPageList1(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		StringBuffer sqltext = new StringBuffer();//查询字段
		//创建参数对象
		List<String> xjrjzlist=new ArrayList<String>();
		//处理传递过来的json数据,数据是存储在session中的
		Gson gson = new Gson();
		String jsonStr = Validate.isNullToDefaultString(request.getSession().getAttribute("params"), "");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(request.getSession()), "googosoft");
		String treesearch = Validate.isNullToDefaultString(pd.getString("treesearch"), "");
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
		String kmbhh = Validate.isNullToDefaultString(pd.getString("kmbhh"),"");
		String login = LUser.getGuid();
		
		if(Validate.noNull(jsonStr)){
//			request.getSession().removeAttribute("params");
			//处理json数据,转换list
			String ajson = jsonStr.substring(8,jsonStr.length()-1);
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list    ////得到的查询条件

			Map map1 = new HashMap<>();
			Zjrbb zjrbb = new Zjrbb();
			for(int i=0;i<1;i++){
				Map map = (Map)list.get(0);
				String lxsj = Validate.isNullToDefaultString(map.get("lxsj"), "");
				String zj_kjkm = Validate.isNullToDefaultString(map.get("zj_kjkm"), "");
				String zj_cnr = (String) map.get("zj_cnr");
				String jsfs = (String) map.get("jsfs");
				String bhqbjzpz = (String) map.get("bhqbjzpz");
				String xsszmx = (String) map.get("xsszmx");
				String bhyfhwjzpz = (String) map.get("bhyfhwjzpz");
				String zxscnwfhpz = (String) map.get("zxscnwfhpz");
				String hzxssbhsykm = (String) map.get("hzxssbhsykm");
				String kjzd = zjrbbService.getkjzd(lxsj);
				zjrbb.setLxsj(lxsj);
				zjrbb.setZj_kjkm(zj_kjkm);
				zjrbb.setZj_cnr(zj_cnr);
				zjrbb.setJsfs(jsfs);
				zjrbb.setBhqbjzpz(bhqbjzpz);
				zjrbb.setXsszmx(xsszmx);
				zjrbb.setBhyfhwjzpz(bhyfhwjzpz);
				zjrbb.setZxscnwfhpz(zxscnwfhpz);
				zjrbb.setHzxssbhsykm(hzxssbhsykm);
				zjrbb.setSszt(sszt);
				zjrbb.setLogin(login);
				zjrbb.setKjzd(kjzd);
				mv.addObject("kmbh", zj_kjkm);
				mv.addObject("lxsj",lxsj);
			}
			//System.err.println("dfdfdf="+kmyeService.getResult(params));
			boolean result = zjrbbService.getResult(zjrbb,kmbhh);
		}
		

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select KMBH,KMMC,");
		sql.append(" DECODE(NVL(QCFX,'B'),'1','贷','0','借','A','平','')QCFX,");
		sql.append(" DECODE(NVL(QMFX,'B'),'1','贷','0','借','A','平','')QMFX,");
		sql.append(" DECODE(NVL(ZRJe,'0'),'0','',TO_CHAR(ROUND((ZRJe),2),'FM999,999,999,990.00'))ZRYE,");
		sql.append(" DECODE(NVL(JRYE,'0'),'0','',TO_CHAR(ROUND((JRYE),2),'FM999,999,999,990.00'))JRYE,");
		sql.append(" DECODE(NVL(JRFSJF,'0'),'0','',TO_CHAR(ROUND((JRFSJF),2),'FM999,999,999,990.00'))JRFSJF,");
		sql.append(" DECODE(NVL(JRFSDF,'0'),'0','',TO_CHAR(ROUND((JRFSDF),2),'FM999,999,999,990.00'))JRFSDF,");
		sql.append(" JFBS,DFBS");
		sql.append(" FROM CWPT_ZJRBB K");
		sql.append(" WHERE 1=1 AND K.LOGIN='"+LUser.getGuid()+"'");
		sql.append(" AND K.SSZT='"+sszt+"'");
		if(Validate.noNull(kmbh)){
			sql.append(" AND K.kmbh='"+kmbh+"'");
		}
		if(Validate.noNull(kmbhh)){
			sql.append(" AND K.kmbh='"+kmbhh+"'");
		}
		if(Validate.noNull(treesearch)){
			sql.append(" and K.kmmc='"+CommonUtil.getEndText(treesearch)+"'");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		sql.append(" ORDER BY K.KMBH");
		list=kmyeService.getPageList(sql.toString());		
		return list;
	}
	

	
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object Info(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		String searchValue = pd.getString("searchJson");
		String flag = pd.getString("foo");
		String sszt=Constant.getztid(request.getSession());
		 String ztgid1 = Constant.getztid(session);//账套编号
		String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "0");//0月度1年度
		String kjkmbh = Validate.isNullToDefaultString(pd.getString("kjkmbh"), "");//左侧树的kmbh
//		String jzpz = Validate.isNullToDefaultString(pd.getString("jzpz"), "0");//结转凭证
//		String sfjz = Validate.isNullToDefaultString(pd.getString("sfjz"), "0");//记账凭证
		
//		String ztgid = Validate.isNullToDefaultString(pd.getString("ztgid"),ztgid1);
//		Map<String,Object> bzdw =  zcfzbService.getBzdw();//编制单位
		Date date = new Date();
		String sysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		sysDate = Validate.isNullToDefaultString(pd.getString("bbyf"), sdf.format(date));//
//		List list = new ArrayList<Map<String,Object>>();
//		list = this.getPageList(request,response);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select KMBH,KMMC,");
		sql.append(" DECODE(NVL(QCFX,'B'),'1','贷','0','借','A','平','')QCFX,");
		sql.append(" DECODE(NVL(QMFX,'B'),'1','贷','0','借','A','平','')QMFX,");
		sql.append(" DECODE(NVL(ZRJe,'0'),'0','',TO_CHAR(ROUND((ZRJe),2),'FM999,999,999,990.00'))ZRYE,");
		sql.append(" DECODE(NVL(JRYE,'0'),'0','',TO_CHAR(ROUND((JRYE),2),'FM999,999,999,990.00'))JRYE,");
		sql.append(" DECODE(NVL(JRFSJF,'0'),'0','',TO_CHAR(ROUND((JRFSJF),2),'FM999,999,999,990.00'))JRFSJF,");
		sql.append(" DECODE(NVL(JRFSDF,'0'),'0','',TO_CHAR(ROUND((JRFSDF),2),'FM999,999,999,990.00'))JRFSDF,");
		sql.append(" JFBS,DFBS");
		sql.append(" FROM CWPT_ZJRBB K");
		sql.append(" WHERE 1=1 AND K.LOGIN='"+LUser.getGuid()+"'");
		sql.append(" AND K.SSZT='"+sszt+"'");
		if(Validate.noNull(kjkmbh)){
			sql.append(" AND K.KMBH='"+kjkmbh+"'");
		}
		sql.append(" ORDER BY K.KMBH");
	
		list=kmyeService.getPageList(sql.toString());
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.zjrbbService.expExcel(realfile, shortfileurl,searchValue,flag,list);
	}
	
	
	//页面之间的跳转
			@RequestMapping("/zjc")
			public ModelAndView PageSkip(HttpServletRequest request) {
				PageData pd = this.getPageData();
				ModelAndView mv = this.getModelAndView();
				String kmbh = pd.getString("kmbh");
				String bbqj = pd.getString("bbqj");
//				/CWYTH/WebContent/webView/kjhs/bbzx/zjrbbtc.jsp
				List list = new ArrayList<Map<String,Object>>();
				String kmmc = zjrbbService.kmmc(kmbh);
			  	list = zjrbbService.kmbhList(kmbh);
			
				mv.addObject("list", list);
				mv.addObject("kmmc", kmmc);
				mv.addObject("bbqj", bbqj);
				
				mv.setViewName("kjhs/bbzx/zjrbbtc");
				return mv;
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
				PageData pd = this.getPageData();
				StringBuffer sqltext = new StringBuffer();//查询字段
				sqltext.append(" K.KMBH,K.KMMC,K.JB,K.KMJC,'('||K.KMBH||')'||K.KMMC AS KMXX");
				PageList pageList = new PageList();
				pageList.setSqlText(sqltext.toString());
				pageList.setKeyId("GUID");//主键
				String year = Validate.isNullToDefaultString(pd.getString("year"), "");
				//String strWhere = " AND TO_CHAR(K.KMND,'YYYY')='"+year+"'";
				String strWhere = " AND (kjzd='"+kjzd+"' and sjfl!='root') and ( kmbh like '1001%' or kmbh like '1002%' or kmbh like '1011%' )";
				String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
//				if(Validate.noNull(kmbh)) {
//					strWhere +=" start with K.jb='"+kmbh+"' connect by prior jb=sjfl and sjfl!='root' ";
//				}
				
				pageList.setStrWhere(strWhere);
				pageList.setTableName("CW_KJKMSZB K");//表名
				pageList.setHj1("");//合计
			    pageList = pageService.findPageList(pd,pageList);
				Gson gson = new Gson();
				PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
				return pageInfo.toJson();
			}
			/**
			 * 会计科目列表
			 * @return
			 * @throws Exception
			 */
			@RequestMapping(value = "/getkmxxPageListForRjz")
			@ResponseBody
			public Object getkmxxPageListForRjz(HttpSession session) throws Exception {	
				String kjzd = CommonUtil.getKjzd(session);
				PageData pd = this.getPageData();
				StringBuffer sqltext = new StringBuffer();//查询字段
				sqltext.append(" K.KMBH,K.KMMC,K.JB,K.KMJC,'('||K.KMBH||')'||K.KMMC AS KMXX");
				PageList pageList = new PageList();
				pageList.setSqlText(sqltext.toString());
				pageList.setKeyId("GUID");//主键
				String year = Validate.isNullToDefaultString(pd.getString("year"), "");
				//String strWhere = " AND TO_CHAR(K.KMND,'YYYY')='"+year+"'";
				String strWhere = " AND (kjzd='"+kjzd+"' and sjfl!='root') and ( kmbh like '1001%' or kmbh like '1002%' or kmbh like '1011%' )";
				String kmbh = Validate.isNullToDefaultString(pd.getString("kmbh"), "");
				if(Validate.noNull(kmbh)) {
					strWhere +=" start with K.jb='"+kmbh+"' connect by prior jb=sjfl and sjfl!='root' ";
				}
				
				pageList.setStrWhere(strWhere);
				pageList.setTableName("CW_KJKMSZB K");//表名
				pageList.setHj1("");//合计
			    pageList = pageService.findWindowList(pd,pageList,"WWW");
				Gson gson = new Gson();
				PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
				return pageInfo.toJson();
			}
}
