package com.googosoft.controller.kjhs.pzxx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.pzxx.PzcxService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 凭证冲销控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value="/pzcx")
public class PzcxController extends BaseController{
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name = "pzcxService")
	private PzcxService pzcxService;
	
	@Resource(name="dictService")
	DictService dictService;
	/**
	 * 跳转录入页面
	 * @return
	 */
	@RequestMapping(value="/goPzcx")
	public ModelAndView goPzlrPage(){
		ModelAndView mv = this.getModelAndView();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 //获取前月的第一天
		Calendar   cal_1=Calendar.getInstance();//获取当前日期 
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		String firstDay = format.format(cal_1.getTime());
		//获取前月的最后一天
		Calendar cale = Calendar.getInstance();   
		cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
		String lastDay = format.format(cale.getTime());
		mv.addObject("firstDay", firstDay);
		mv.addObject("lastDay", lastDay);
		mv.addObject("zt",Validate.isNullToDefault(this.getPageData().getString("zt"), ""));
		mv.addObject("kssj",Validate.isNullToDefault(this.getPageData().getString("kssj"), ""));
		mv.addObject("jssj",Validate.isNullToDefault(this.getPageData().getString("jssj"), ""));
		mv.setViewName("kjhs/pzxx/pzcx/pzcx");
		return mv;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		String sszt = Validate.isNullToDefaultString(Constant.getztid(session),"");
		tablename.append(" (select z.guid,z.pzbh,z.pznd,z.kjqj,z.sszt,to_char(z.pzrq,'yyyy-mm-dd') pzrq,"
				//+ "(select j.kmmc from cw_jjkmb j where j.kmbh=m.jjfl)jjflmc,m.bmbh,"
				+ "(z.sfcx)cxzt,z.pzz,"
				+ " (case z.pzzt  when '00' then  '已保存' when '01' then '已复核' when '02' then '已记账'when '99' then '已结账' else '' end) pzztmc,"
				+ " to_char(z.jfjehj,'FM999999999990.00')jfjehj, to_char(z.dfjehj,'FM999999999990.00')dfjehj ,"
				+ " (select '('||j.rybh||')'||j.xm from gx_sys_ryb j where j.guid=z.zdr)zdrmc,"
				+ " (select '('||j.rybh||')'||j.xm from gx_sys_ryb j where j.guid=z.FHR)fzrmc,"
				+ " (select '('||j.rybh||')'||j.xm from gx_sys_ryb j where j.guid=z.jzr)jzrmc,z.fjzs,"
				+ "(select count(0) from cw_pzlrmxb b where b.pzbh=z.guid) as flts,"
				+ "(select zy from cw_pzlrmxb b where b.pzbh = z.guid and rownum <= 1)as zy ," 
				+" z.pzlxmc "
				+ "from cw_pzlrzb z "
				//+ "left join cw_pzlrmxb m on m.pzbh=z.guid "
				+ "where 1=1 and z.sszt='"+sszt+"'");
			tablename.append(" and (z.pzzt = '02' or z.sfcx = '1' )");
		
		if(Validate.noNull(pd.getString("kssj"))&&Validate.noNull(pd.getString("jssj"))){
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
	}
	/**
	 * 冲销和反冲销
	 * @return
	 */
	@RequestMapping(value="/doDeal1",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDeal1(HttpSession session){
		PageData pd = this.getPageData();
		String pzbh = pd.getString("pzbh");
		String guid1 = pd.getString("guid");
//		String sszt = pd.getString("sszt");
		String sszt=Constant.getztid(session);
		String type = pd.getString("type");
		String pznd = pd.getString("pznd");//凭证年度
		String kjqj = pd.getString("kjqj");
		Map<String, Object> kjqjMap = pzcxService.getKjqj(sszt);
		//首次加载页面
		if(pznd == null) {
			pznd = ""+kjqjMap.get("ztnd");
			kjqj = ""+kjqjMap.get("kjqj");
		}
		Gson gson = new Gson();
		String IsCx = "";
		if("cx".equals(type)){
			//IsCx = pzcxService.checkIsCx(pd.getString("guid"));//查询是否已经冲销
			IsCx = pzcxService.checkIsOrNoCx(pd.getString("guid"));
			if(Integer.parseInt(IsCx)>0){//已经冲销
				return gson.toJson(true);
			}else{//未冲销 进行冲销
				int k = 0;
				String guid =this.get32UUID();
				k = pzcxService.doDeal(pd,type,guid,session);
			}
		}else if("qxcx".equals(type)){
			IsCx = pzcxService.checkIsQxCx(pd.getString("guid"));//查询是否已经取消冲销
//			System.out.println("_>0表示已经冲销_"+IsCx);
			if(Integer.parseInt(IsCx)>0){
				int k  =0;
				int j = 0;
				String guid =this.get32UUID();
				k = pzcxService.doDeal(pd,type,guid,session);
				j = pzcxService.qxcx(guid1,pzbh,sszt);
			}else{
				return gson.toJson(true);
			}
		}
		
		return gson.toJson(false);
	}
	@RequestMapping(value="/demoPrint")
	public ModelAndView demoPrint(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("wdbh");
		String pzbh = pd.getString("pzbh");
		String dm = pd.getString("dm");
		String  bmmc= pzcxService.getdwmcByIds(pzbh);
		System.out.print(bmmc+")))))))))))))))))))))");
		List list = pzcxService.getPrintInfoByIds(pd);//查询打印数据
		Map map = pzcxService.getInfo(pd);
		String url = "kjhs/pzxx/PrintSample44";///controller/kjhs/pzxx/PrintSample44.jsp
		mv.addObject("guid", guid);
		mv.addObject("pzbh", pzbh);
		mv.addObject("dwmc", bmmc);
		mv.addObject("info", list);
		mv.addObject("info2", map);
		String pzcx = "pzcx";
		mv.addObject("mkbh", pzcx );
		mv.setViewName(url);
		return mv;
	}
	
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
			Map<String, Object> zbMap = pzcxService.getPzlrZbInfo(pznd,kjqj, pzbh,sszt);
			List<Map<String, Object>> mxList = pzcxService.getPzlrMxFzInfo(pznd,kjqj, pzbh,sszt);
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
					List<Map<String, Object>> pzzList = pzcxService.getPzzList(sszt);
					Map<String, Object> ztxx = pzcxService.getZtxx(sszt);
					String pznd = pd.getString("pznd");
					String kjqj = pd.getString("kjqj");
					Map<String, Object> kjqjMap = pzcxService.getKjqj(sszt);
					//首次加载页面
					if(pznd == null) {
						pznd = ""+kjqjMap.get("ztnd");
						kjqj = ""+kjqjMap.get("kjqj");
					}
//					//凭证字为空说明首次加载页面
//					if(pzz == null) {
//						pzz = ""+pzzList.get(0).get("lxbh");
//					}
					if(pzbh == null) {
						type = "wfh";
					}
					//查询数据库中已经存在的凭证编号
					List<String> pzbhList = pzcxService.getPzbhList(pznd,kjqj,sszt);
					//获取自动生成的凭证编号（规则为已存在的最大凭证编号加一）
					String autoPzbh= pzcxService.getAutoPzbh(pznd,kjqj, 4,sszt);
					String pageName = "pzcxView";
					if(type != null) {
						switch (type) {
						case "first":
							pzbh = "0001";
							break;
						case "wfh":
							pzbh = pzcxService.pzbhwfh(guid);
							break;
						case "last":
							pzbh = autoPzbh;
							break;
						case "prev":
							if(!"0001".equals(pzbh)) {
								pzbh = ""+(Integer.parseInt(pzbh) - 1);
								pzbh = pzcxService.autoFill(pzbh, 4, "0");
							}
							break;
						case "next":
							if(!autoPzbh.equals(pzbh)) {
								pzbh = ""+(Integer.parseInt(pzbh) + 1);
								pzbh = pzcxService.autoFill(pzbh, 4, "0");
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
							Object fhr = pzcxService.getFhr(pzbh, pznd,kjqj, sszt);
							if(Validate.isNull(fhr)) {
								pageName = "pzcxView";
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
					mv.addObject("bybwkm",pzcxService.getBybwkm(pzz,sszt));
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
//						pzz = iniPzInfo(mv,pd);
//						pzbhwfh = iniPzInfo(mv,pd);
						break;
					default:
						break;
					}
					iniLogin(mv);
					System.err.println("++++++++++++++++++"+pageName);
					mv.setViewName("kjhs/pzxx/pzcx/"+pageName);
					return mv;
				}
				
				//获取交互数据
				@RequestMapping(value="getEchoData",produces="text/html;charset=UTF-8")
				@ResponseBody
				public Object getEchoJson() {
					PageData pd = this.getPageData();
					Map<String, Object> map = pzcxService.getEchoData(pd);
					Gson gson = new Gson();
					return gson.toJson(map);
				}
}

