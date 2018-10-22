package com.googosoft.controller.kjhs.pzxx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.pzxx.PzfhService;
import com.googosoft.service.pzxx.PzjzService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 凭证记账控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value="/pzjz")
public class PzjzController extends BaseController{
	@Resource(name="pzjzService")
	private PzjzService pzjzService; //单例
	@Resource(name="pageService")
	PageService pageService;
	@Resource(name="dictService")
	DictService dictService;
	@Autowired
	private PzfhService pzfhService;
	/**
	 * 跳转凭证记账页面
	 * @return
	 */
	@RequestMapping(value="/goPzjz")
	public ModelAndView goPzlrPage(HttpSession session){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String zt = pd.getString("zt");
		String kssj = pd.getString("kssj");
		String jssj = pd.getString("jssj");
		mv.addObject("zt",Validate.isNullToDefault(zt, ""));
		mv.addObject("kssj",Validate.isNullToDefault(kssj, ""));
		mv.addObject("jssj",Validate.isNullToDefault(jssj, ""));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 //获取前月的第一天
		Calendar   cal_1=Calendar.getInstance();//获取当前日期 
		String sszt = Validate.isNullToDefaultString(Constant.getztid(session),"");
		String mouth = pzjzService.getDqkjqj(sszt);//获取当前会计区间
		int m1 = Integer.valueOf(mouth);//String转换为int
//		String year = pzjzService.getDqkjqjYear(sszt);//获取当前会计区间
//		int y1 = Integer.valueOf(year);//String转换为int
//		cal_1.set(Calendar.YEAR, y1);
//		cal_1.add(Calendar.MONTH, m1);
//		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
//		String firstDay = format.format(cal_1.getTime());
		//获取前月的最后一天
//		Calendar cale = Calendar.getInstance();
//		cale.add(Calendar.MONTH, 0);
//		cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
//		cale = Calendar.getInstance(); 
//		int m2 =m1+1;
//		cale.set(Calendar.YEAR, y1);  
//        cale.add(Calendar.MONTH, m2);  
//        cale.set(Calendar.DAY_OF_MONTH, 0);  
//		String lastDay = format.format(cale.getTime());
		Map map = pzjzService.getDqkjqjMin(sszt);//获取当前会计区间
		String firstDay = map.get("stime") + "";
		String lastDay = map.get("etime") + "";
		mv.addObject("firstDay", firstDay);
		mv.addObject("lastDay", lastDay);
		mv.addObject("loginId",LUser.getGuid());
		mv.setViewName("kjhs/pzxx/pzjz/pzjz");
		return mv;
	}
	/**
	 * 记账和反记账
	 * @return
	 */
	@RequestMapping(value="/doDeal1",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDeal1(){
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String userid= LUser.getGuid();
		Gson gson = new Gson();
		int k  =0;
		if("jz".equals(type)){
			k = pzjzService.doDeal(pd,type,userid);
		}else if ("fjz".equals(type)){
			k = pzjzService.doDeal(pd,type,userid);
		}
		if(k>0){
			return gson.toJson(true);
		}
		return gson.toJson(false);
	}
	/**
	 * 跳转专业列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/col")
	public ModelAndView goColPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		mv.addObject("controlId", controlId);
		String window_url  = "kjhs/pzxx/cnqz/cnqzc";
		mv.setViewName(window_url);
		return mv;
		
	}
	/**
	 * 跳转专业列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/view")
	public ModelAndView goViewPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String window_url  = "kjhs/pzxx/view";
		mv.setViewName(window_url);
		return mv;
		
	}
	@RequestMapping(value="/demoPrint")
	public ModelAndView demoPrint(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("wdbh");
		String pzbh = pd.getString("pzbh");
		String dwmc = pd.getString("pzbh");
		String  bmmc= pzjzService.getdwmcByIds(pzbh);
		List list = pzjzService.getPrintInfoByIds(pd);//查询打印数据
		Map map = pzjzService.getInfo(pd);
		String url = "kjhs/pzxx/PrintSample44";///controller/kjhs/pzxx/PrintSample44.jsp
		mv.addObject("guid", guid);
		mv.addObject("dwmc", bmmc);
		mv.addObject("pzbh", pzbh);
		mv.addObject("zt", pd.getString("zt"));
		mv.addObject("info", list);
		mv.addObject("info2", map);
		String pzjz = "pzjz";
		mv.addObject("mkbh", pzjz );
		mv.setViewName(url);
		return mv;
	}
	
	/**
	 * 获取日常报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {
		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String sszt = Validate.isNullToDefaultString(Constant.getztid(session),"");
		sqltext.append(" * ");
		
		tablename.append(" ( select a.kjqj,a.pznd, a.pzzt, (case a.pzzt when '00' then '已保存' when '01' then '未记账' when'02' then'已记账' when '99' then '已结账' else '' end )pzztmc,a.guid,a.fhr,"
				+ "a.zdr,(select '('||j.rybh||')'||j.xm from gx_sys_ryb j where j.guid=a.zdr)zdrmc,(case nvl(a.fhr,0) when '0' then '已记账' else '已复核' end)sffh,"
				+ " (SELECT '('||G.rybh||')'||G.XM FROM gx_sys_ryb G WHERE G.GUID=A.FHR) AS FHRMC,"
				+ " A.JZR,(SELECT '('||G.rybh||')'||G.XM FROM gx_sys_ryb G WHERE G.GUID=A.JZR) AS JZRMC,A.FJZS, "
				+ "to_char(a.pzrq,'yyyy-mm-dd')PZRQ,a.pzbh,a.fhyj,(case nvl(a.sfdy,'0') when '1' then '是' else '否' end) as sfdy,"
				+ "a.pzz,(select (to_char(sum(b.jfje),'FM999999999990.00')) from cw_pzlrmxb b where b.pzbh=a.guid) as jfje,"
				+ "(select (to_char(sum(b.dfje),'FM999999999990.00')) from cw_pzlrmxb b where b.pzbh=a.guid) as dfje,"
				+ "(select count(0) from cw_pzlrmxb b where b.pzbh=a.guid) as flts,"
				+ "(select zy from cw_pzlrmxb b where b.pzbh = a.guid and rownum <= 1)as zy, (select x.pzlxmc from cw_pzlxbnew x where a.pzz= x.pzlxbh) as pzlxmc " 
				+ " from Cw_pzlrzb a  where 1=1 and (pzzt='01' or pzzt='02') and a.sszt='"+sszt+"'");
//		if(Validate.isNull(pd.getString("zt"))){
//			tablename.append(" and a.pzzt = '01' ");
//		}
		System.out.println("zt============"+pd.getString("zt"));
		if(pd.getString("zt")==null ||pd.getString("zt").length()==0){
			tablename.append(" and a.pzzt = '01' ");
		}else if( "all".equals(pd.getString("zt"))) {
			System.out.println("111111111111111111111");
		}else {
			tablename.append(" and a.pzzt = '"+pd.getString("zt")+"' ");
		}
			
//		if(Validate.noNull(pd.getString("kssj"))&&Validate.noNull(pd.getString("jssj"))){
//			tablename.append(" and to_date('"+pd.getString("kssj")+"','yyyy-mm-dd') <= pzrq and pzrq<=to_date('"+pd.getString("jssj")+"','yyyy-mm-dd') ");
//		}
		if(Validate.isNull(pd.get("search[value]"))) {
			tablename.append(" and to_date('"+pd.getString("kssj")+"','yyyy-mm-dd') <= pzrq and pzrq<=to_date('"+pd.getString("jssj")+"','yyyy-mm-dd') ");
		}
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
	} // 结束getPageList函数
	
	//初始化当前登录人信息
	public void iniLogin(ModelAndView mv) {
		mv.addObject("loginId",LUser.getGuid());
		mv.addObject("dwmc",LUser.getDwmc());
		mv.addObject("rybh",LUser.getRybh());
		mv.addObject("ryxm",LUser.getRyxm());
		mv.addObject("today",DateUtil.getDay());
	}
	//初始化数据字典下拉框
	public void iniSelect(ModelAndView mv) {
		mv.addObject("jsfsList",dictService.getDict(Constant.ZFFSDM));
		mv.addObject("yslxList",dictService.getDict(Constant.YSLX));
		mv.addObject("zclxList",dictService.getDict(Constant.ZCLX));
		mv.addObject("yslyList",dictService.getDict(Constant.YSLY));
	}
	public void iniView(ModelAndView mv,String pznd,String kjqj,String pzbh,String sszt) {
		Map<String, Object> zbMap = pzjzService.getPzlrZbInfo(pznd,kjqj, pzbh,sszt);
		List<Map<String, Object>> mxList = pzjzService.getPzlrMxFzInfo(pznd,kjqj, pzbh,sszt);
		mv.addObject("zbMap",zbMap);
		mv.addObject("mxList",mxList);
	}
	
	//初始化凭证信息
			public String iniPzInfo(ModelAndView mv,PageData pd) {
				String type = pd.getString("type");
				String pzz = pd.getString("pzz");
				String pzbh = pd.getString("pzbh");
				String sszt = pd.getString("sszt");
				String guid = pd.getString("guid");
				List<Map<String, Object>> pzzList = pzjzService.getPzzList(sszt);
				Map<String, Object> ztxx = pzjzService.getZtxx(sszt);
				Map<String, Object> kjqjMap = pzjzService.getKjqj(sszt);
				String pznd = pd.getString("pznd");
				String kjqj = pd.getString("kjqj");
				//凭证字为空说明首次加载页面
//				if(pzz == null) {
//					pzz = ""+pzzList.get(0).get("lxbh");
//				}
				//首次加载页面
				if(pznd == null) {
					pznd = ""+kjqjMap.get("ztnd");
					kjqj = ""+kjqjMap.get("kjqj");
				}
				if(pzbh == null) {
					type = "wfh";
				}
				//查询数据库中已经存在的凭证编号
				List<String> pzbhList = pzjzService.getPzbhList(pznd,kjqj,sszt);
				//获取自动生成的凭证编号（规则为已存在的最大凭证编号加一）
				String autoPzbh= pzjzService.getAutoPzbh(pznd,kjqj, 4,sszt);
				String pageName = "pzjzView";
				if(type != null) {
					switch (type) {
					case "first":
						pzbh = "0001";
						break;
					case "wfh":
						pzbh = pzfhService.pzbhwfh(pznd,kjqj,sszt);
						if(Validate.isNullOrEmpty(pzbh)) {
							pzbh = autoPzbh;
						}
						break;
					case "last":
						pzbh = autoPzbh;
						break;
					case "prev":
						if(!"0001".equals(pzbh)) {
							pzbh = ""+(Integer.parseInt(pzbh) - 1);
							pzbh = pzjzService.autoFill(pzbh, 4, "0");
						}
						break;
					case "next":
						if(!autoPzbh.equals(pzbh)) {
							pzbh = ""+(Integer.parseInt(pzbh) + 1);
							pzbh = pzjzService.autoFill(pzbh, 4, "0");
						}
						break;
					default:
						break;
					}
				}
				if(autoPzbh.equals(pzbh)) {
					pageName = "pzlrAdd";
				}else {
					if(pzbhList.contains(pzbh)) {
						iniView(mv, pznd,kjqj, pzbh, sszt);
						Object fhr = pzjzService.getFhr(pzbh, sszt,pznd,kjqj);
						if(Validate.isNull(fhr)) {
							pageName = "pzjzView";
						}
					}else {
						pzbh = autoPzbh;
						pageName = "pzlrAdd";
					}
				}
				mv.addObject("pzz",pzz);
				mv.addObject("pzbh",pzbh);
				pzbhList.add(autoPzbh);
				mv.addObject("pzbhList",pzbhList);
				mv.addObject("pzzList",pzzList);
				mv.addObject("ztxx",ztxx);
				mv.addObject("bybwkm",pzjzService.getBybwkm(pzz,sszt,pznd,kjqj));
				return pageName;
			}
			//页面之间的跳转
			@RequestMapping("/pageSkip")
			public ModelAndView PageSkip(HttpServletRequest request) {
				PageData pd = this.getPageData();
				ModelAndView mv = this.getModelAndView();
				String pageName = pd.getString("pageName");
				String pzz = pd.getString("pzz");
				String pzbhwfh = pd.getString("pzbh");
				String sszt = Constant.getztid(request.getSession());
				pd.put("sszt", sszt);
				switch (pageName) {
				case "pzlr":
					iniSelect(mv);
					pageName = iniPzInfo(mv,pd);
//					pzz = iniPzInfo(mv,pd);
//					pzbhwfh = iniPzInfo(mv,pd);
					break;
				default:
					break;
				}
				iniLogin(mv);
				System.err.println("++++++++++++++++++"+pageName);
				mv.setViewName("kjhs/pzxx/pzjz/"+pageName);
				return mv;
			}
			
			//获取交互数据
			@RequestMapping(value="getEchoData",produces="text/html;charset=UTF-8")
			@ResponseBody
			public Object getEchoJson() {
				PageData pd = this.getPageData();
				Map<String, Object> map = pzjzService.getEchoData(pd);
				Gson gson = new Gson();
				return gson.toJson(map);
			}
}
