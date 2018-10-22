package com.googosoft.controller.kjhs.pzxx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.googosoft.service.kjhs.pzxx.PzcxsService;
import com.googosoft.service.kjhs.pzxx.PzfhService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 凭证查询控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value="/pzcxs")
public class PzcxsController extends BaseController{
	@Resource(name = "pageService")
	private PageService pageService;
	@Resource(name = "pzcxsService")
	private PzcxsService pzcxsService;
	@Autowired
	private PzfhService pzfhService;
	@Resource(name="dictService")
	DictService dictService;
	
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
	/**
	 * 跳转录入页面
	 * @return
	 */
	@RequestMapping(value="/goPzcxs")
	public ModelAndView goPzlrPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String pzzt = pd.getString("pzzt");
		
		
		String kssj = pd.getString("kssj");
		String jssj = pd.getString("jssj");
		
		String ksbh = pd.getString("ksbh");
		String jsbh = pd.getString("jsbh");
		
		String kskm = pd.getString("kskm");
		String jskm = pd.getString("jskm");
		
	
		
		String fzxm = pd.getString("fzxm");
		String fzbm = pd.getString("fzbm");
		String fzjjkm = pd.getString("fzjjkm");
		
		String dfhjje1 = pd.getString("dfhjje1");
		String dfhjje2 = pd.getString("dfhjje2");
		
		String jfhjje1 = pd.getString("jfhjje1");
		String jfhjje2 = pd.getString("jfhjje2");
		List<Map<String, Object>> pzlxmc = pzcxsService.getPzlxmc();//获取凭证类型
		mv.addObject("pzlxmc", pzlxmc);
		mv.addObject("pzzt", pzzt);
		mv.addObject("kssj", kssj);
		mv.addObject("jssj", jssj);
		mv.addObject("ksbh", ksbh);
		mv.addObject("jsbh", jsbh);
		mv.addObject("kskm", kskm);
		mv.addObject("jskm", jskm);
		
		mv.addObject("fzxm", fzxm);
		mv.addObject("fzbm", fzbm);
		mv.addObject("fzjjkm", fzjjkm);
		mv.addObject("dfhjje1", dfhjje1);
		mv.addObject("dfhjje2", dfhjje2);
		mv.addObject("jfhjje1", jfhjje1);
		mv.addObject("jfhjje2", jfhjje2);
//		String pzzt = pd.getString("pzzt");
		String zbpzlxmc = pd.getString("zbpzlxmc");
		mv.addObject("zbpzlxmc",zbpzlxmc);
		String zy = pd.getString("zy");
		mv.addObject("zy",zy);
		String pzrqs = pd.getString("pzrqs");
		mv.addObject("pzrqs",pzrqs);
		String pzrqf = pd.getString("pzrqf");
		mv.addObject("pzrqf",pzrqf);
		String bhl = pd.getString("bhl");
		mv.addObject("bhl",bhl);
		String bhh = pd.getString("bhh");
		mv.addObject("bhh",bhh);
		String kjkm1 = pd.getString("kjkm1");
		mv.addObject("kjkm1",kjkm1);
		String kjkm2 = pd.getString("kjkm2");
		mv.addObject("kjkm2",kjkm2);
		String zdr = pd.getString("zdr");
		String txt_zdr = pd.getString("txt_zdr");
		String txt_fhr = pd.getString("txt_fhr");
		String txt_jzr = pd.getString("txt_jzr");
		String fhr = pd.getString("fhr");
		String jzr = pd.getString("jzr");
		mv.addObject("zdr",zdr);
		mv.addObject("fhr",fhr);
		mv.addObject("jzr",jzr);
		mv.addObject("txt_zdr",txt_zdr);
		mv.addObject("txt_fhr",txt_fhr);
		mv.addObject("txt_jzr",txt_jzr);
		String txt_jfhjje1 = pd.getString("txt_jfhjje1");
		mv.addObject("txt_jfhjje1",txt_jfhjje1);
		String txt_jfhjje2 = pd.getString("txt_jfhjje2");
		mv.addObject("txt_jfhjje2",txt_jfhjje2);
	
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
		mv.setViewName("kjhs/pzxx/pzcxs/pzcxs");
		return mv;
	}
	//查询 凭证明细 下拉菜单 
	@RequestMapping(value = "/getpzmxlist")
	@ResponseBody
	public List<Map<String,Object>> getpzmxlist(){
		String pzbh=this.getPageData().getString("pzbh");
		List<Map<String,Object>> pzmxlist=pzcxsService.getpzmxlist(pzbh);
		return pzmxlist;
	}
	/**
	 * 获取财务人员
	 */
	@RequestMapping(value = "/getCwryPageList")
	@ResponseBody
	public Object getCwryPageList() throws Exception {
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer StrWhere = new StringBuffer();
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		sqltext.append(" guid,rybh,xm ");
		pageList.setSqlText(sqltext.toString());
		pageList.setTableName("gx_sys_ryb");
		pageList.setKeyId("GUID");
		StrWhere.append(" and dwbh='110' ");
		pageList.setStrWhere(StrWhere.toString());
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session,HttpServletRequest request) throws Exception {
		PageData pd = this.getPageData();	
		String jfje1 = "";
		String jfje2 = "";
		 String searchValue = pd.getString("search[value]");
		Gson gson = new Gson();
		if(Validate.noNull(searchValue)) {
			ArrayList list = gson.fromJson(searchValue,ArrayList.class);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map)list.get(i);
				if("jfje1".equals(map.get("name")+"")) {
					jfje1 = map.get("value")+"";
				}
				if("jfje2".equals(map.get("name")+"")) {
					jfje2 = map.get("value")+"";
				}
			}
		}
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		StringBuffer StrWhere = new StringBuffer();
		PageList pageList = new PageList();
		String sszt = Validate.isNullToDefaultString(Constant.getztid(session),"");
		sqltext.append(" * ");
		tablename.append("	(select * from (  select  a.guid,A.KJQJ,a.pzzt, (select wm_concat(zy) as zy from cw_pzlrmxb b  where b.pzbh = a.guid ) as zy,");
		tablename.append("(select zy from cw_pzlrmxb b  where b.pzbh = a.guid and rownum = 1) as firzy," );
		tablename.append( "   (case a.pzzt  when '00' then  '已保存' when '01' then '已复核' when '02' then '已记账'when '99' then '已结账' else '' end) pzztmc," );
		tablename.append( "  to_char(a.pzrq, 'yyyy-MM-dd') as pzrq, to_char(a.pzrq, 'yyyy-MM') as pzrqy, l.lxbh,l.lxmc PZLXMC,(select count(0) from cw_pzlrmxb b where b.pzbh = a.guid ) as flts,a.fhr," );
		tablename.append( " (select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r  where r.guid = a.fhr and rownum <= 1) fhrmc," );
		tablename.append( "  a.zdr,a.jzr,(select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r where r.guid = a.jzr and rownum <= 1) jzrmc,a.fjzs,(select xmbh from cw_pzlrmxb b where b.pzbh = a.guid " );
	    tablename.append( "  and rownum <= 1) as xmbh,(select '('||xx.xmbh||')'||xx.xmmc from cw_xmjbxxb xx,cw_pzlrmxb pz where xx.xmbh = pz.xmbh and rownum <= 1) as xmmc,(select bmbh from cw_pzlrmxb b where b.pzbh = a.guid and rownum <= 1) as bmbh," );
	    tablename.append( " (select jjfl from cw_pzlrmxb b where b.pzbh = a.guid and rownum <= 1) as jjfl, (select '('||jj.kmbh||')'||jj.kmmc from cw_jjkmb jj left join cw_pzlrmxb pz on jj.kmbh = pz.jjfl where pz.pzbh = a.guid and rownum <= 1) as jjmc," );
	    tablename.append( "  to_char(round(a.dfjehj, 2), 'fm9999999990.00') as dfjehj,to_char(round(a.jfjehj, 2), 'fm9999999999990.00') as jfjehj,(select '(' || j.rybh || ')' || j.xm from gx_sys_ryb j where j.guid = a.zdr and rownum <= 1) zdrmc,");
	    tablename.append( "  (case nvl(a.fhr, 0) when '0' then '已记账' else '已复核' end) sffh,a.pzbh,(select kmbh from cw_pzlrmxb b where b.pzbh = a.guid and rownum <= 1) as kmbh, ");
	    tablename.append( " (select x.pzlxmc from cw_pzlxbnew x where a.pzz= x.pzlxbh)  as zbpzlxmc,(case nvl(a.sfdy,'0') when '1' then '是' else '否' end) as sfdy " );
	    tablename.append("  from Cw_pzlrzb a left join cw_pzlxb l on l.guid = a.pzz where 1 = 1 and a.sszt='"+sszt+"' ");				
		tablename.append(" ) k where 1=1 ");
		tablename.append(")K ");
		if(Validate.noNull(jfje1)&&Validate.noNull(jfje2)){
			StrWhere.append("   and (select count(*) from cw_pzlrmxb m  where m.pzbh = k.guid and (m.jfje >= '"+jfje1+"' and m.jfje <= '"+jfje2+"')) > 0 ");
			StrWhere.append("   or (select count(*) from cw_pzlrmxb m  where m.pzbh = k.guid and (m.dfje >= '"+jfje1+"' and m.dfje <= '"+jfje2+"')) > 0 ");
		}
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(StrWhere.toString());
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	//初始化凭证信息
	public String iniPzInfo(ModelAndView mv,PageData pd) {
		String type = pd.getString("type");
		String pzz = pd.getString("pzz");
		String pzbh = pd.getString("pzbh");
		String sszt = pd.getString("sszt");
		String guid = pd.getString("guid");
		String pznd = pd.getString("pznd");
		String kjqj = pd.getString("kjqj");
		Map<String, Object> kjqjMap = pzfhService.getKjqj(sszt);
		List<Map<String, Object>> pzzList = pzfhService.getPzzList(sszt);
		Map<String, Object> ztxx = pzfhService.getZtxx(sszt);
		System.err.println("ztxx="+ztxx);
		//凭证字为空说明首次加载页面
		if(pznd == null) {
			pznd = ""+kjqjMap.get("ztnd");
			kjqj = ""+kjqjMap.get("kjqj");
		}
//		if(pzz == null) {
//			pzz = ""+pzzList.get(0).get("lxbh");
//			kjqj = ""+kjqjMap.get("kjqj");
//		}
		if(pzbh == null) {
			type = "wfh";
		}
		//查询数据库中已经存在的凭证编号
		List<String> pzbhList = pzfhService.getPzbhList(pznd,kjqj,sszt);
		System.err.println("pzbhList查询数据库中已经存在的凭证编号="+pzbhList);
		if(pzbhList.size() == 0) {
			return "pzlrNull";
		}
		//获取自动生成的凭证编号（规则为已存在的最大凭证编号加一）
//		String autoPzbh= pzfhService.getAutoPzbh(pznd,kjqj, 4,sszt);
		String autoPzbh= pzbhList.get(pzbhList.size()-1);
		String pageName = "pzcxsView";
		if(type != null) {
			switch (type) {
			case "first":
				pzbh = "0001";
				break;
			case "wfh":
				pzbh = pzfhService.pzbhwfh(pznd,kjqj,sszt);
				System.err.println("pzbh="+pzbh);
				if(Validate.isNull(pzbh)) {
					pzbh = autoPzbh;
				}
				break;
			case "last":
				pzbh = autoPzbh;
				break;
			case "prev":
				if(!"0001".equals(pzbh)) {
					pzbh = ""+(Integer.parseInt(pzbh) - 1);
					pzbh = pzfhService.autoFill(pzbh, 4, "0");
				}
				break;
			case "next":
				if(!autoPzbh.equals(pzbh)) {
					pzbh = ""+(Integer.parseInt(pzbh) + 1);
					pzbh = pzfhService.autoFill(pzbh, 4, "0");
					System.err.println("pzbh111="+pzbh);
				}
				break;
			default:
				break;
			}
		}
//		if(autoPzbh.equals(pzbh)) {
//			pageName = "pzlrAdd";
//		}else {
//			if(pzbhList.contains(pzbh)) {
//				iniView(mv, pzz, pzbh, sszt,pznd,kjqj);
//				Object fhr = pzfhService.getFhr(pzbh, pzz, sszt,pznd,kjqj);
//				
//				if(Validate.isNull(fhr)) {
//					pageName = "pzlrEdit";
//				}
//			}else {
//				pzbh = autoPzbh;
//				pageName = "pzlrAdd";
//			}
//		}
			if(pzbhList.contains(pzbh)) {
				Map<String, Object> zbMap = pzfhService.getPzlrZbInfo(pzz, pzbh,sszt,pznd,kjqj);
				System.err.println("znMap="+zbMap);
				List<Map<String, Object>> mxList = pzfhService.getPzlrMxFzInfo(pzz, pzbh,sszt,pznd,kjqj);
				System.err.println("mxList="+mxList);
				mv.addObject("zbMap",zbMap);
				System.err.println("zbMap="+zbMap);
				mv.addObject("mxList",mxList);
				System.err.println("maList====="+mxList);
//				
//				if("已保存".equals(zbMap.get("pzzt"))) {
//					pageName = "pzlrEdit";
//				}
			}
		mv.addObject("pzz",pzz);
		mv.addObject("pzbh",pzbh);
//		pzbhList.add(autoPzbh);
		mv.addObject("pzbhList",pzbhList);
		mv.addObject("ztxx",ztxx);
		mv.addObject("kjqj",kjqjMap);
		
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
			//iniSelect(mv);
			pageName = iniPzInfo(mv,pd);
//			pzz = iniPzInfo(mv,pd);
//			pzbhwfh = iniPzInfo(mv,pd);
			break;
		default:
			break;
		}
		iniLogin(mv);
		System.err.println("+++++++++pzcxsView+++++++++"+pageName);
		mv.setViewName("kjhs/pzxx/pzcxs/"+pageName);
		return mv;
	}
	public void iniView(ModelAndView mv,String pzz,String pzbh,String sszt) {
		Map<String, Object> zbMap = pzcxsService.getPzlrZbInfo(pzz, pzbh,sszt);
		List<Map<String, Object>> mxList = pzcxsService.getPzlrMxFzInfo(pzz, pzbh,sszt);
		System.err.println("mxList="+mxList);
		mv.addObject("zbMap",zbMap);
		mv.addObject("mxList",mxList);
	}
	
}