package com.googosoft.controller.pjgl.rcyw;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.pjgl.rcyw.PjlbService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/pjgl/rcyw/pjlb")
public class PjlbController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="pjlbService")
	private PjlbService pjlbService;
	/**
	 * 跳转到票据列表初始页面（显示的是票据账户列表）
	 */
	@RequestMapping(value="/getPjlbPage")
	public ModelAndView getPjlbPage() {
		ModelAndView mv=this.getModelAndView();
		mv.setViewName("/pjgl/rcyw/pjlb/pjzh_list");
		return mv;
	}
	
	/**
	 * 获得票据账户列表数据
	 * @return
	 */
	@RequestMapping(value="/getPjzhList")
	@ResponseBody
	public Object getPjzhList() {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String dm=pd.getString("dm");
		sqltext.append("PJZH,PJZHMC,CPJE,KBPJ,YLPJ,YCPJ,YBXPJ,YZFPJ,HJ");
		
		StringBuffer sb=new StringBuffer();
		sb.append(" select p.pjzh,sum(nvl(p.je,0))as cpje,  ");
		sb.append(" (select z.zhmc from Cw_Pjzhb z where z.guid = p.pjzh ) as pjzhmc, ");
		sb.append(" nvl((select sum(1) from CW_PJ kp where kp.zt in('00')  and kp.pjzh = p.pjzh ),0)as kbpj,  ");
		sb.append(" nvl((select sum(1) from CW_PJ kp where kp.zt in ('10')  and kp.pjzh = p.pjzh),0)as ylpj,  ");
		sb.append(" nvl((select sum(1) from CW_PJ kp where kp.zt in ('20')  and kp.pjzh = p.pjzh),0)as ycpj,  ");
		sb.append(" nvl((select sum(1) from CW_PJ kp where kp.zt in ('30')  and kp.pjzh = p.pjzh),0)as ybxpj,  ");
		sb.append(" nvl((select sum(1) from CW_PJ kp where kp.zt in ('50')  and kp.pjzh = p.pjzh),0)as yzfpj,  ");
		sb.append(" nvl((select sum(1) from CW_PJ kp where kp.zt in ('00','10','20','30','50')  and kp.pjzh = p.pjzh),0)as hj  ");
		sb.append(" from CW_PJ p ");
		sb.append(" where p.zt in('00','10','20','30','50') ");
		sb.append(" group by p.pjzh  ");
		
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("PJZH");//主键
		
		pageList.setTableName(" ("+sb.toString()+")k ");//表名
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));

		return pageInfo.toJson();
	}
	
	/**
	 * 跳转票据列表页面
	 * @return
	 */
	@RequestMapping(value = "/goPjListPage")
	public ModelAndView goEditPage() {
		ModelAndView mv = this.getModelAndView();
		String pjzh = this.getPageData().getString("pjzh");
		
		mv.addObject("pjzh", pjzh);
		mv.setViewName("pjgl/rcyw/pjlb/pjlb_list");
		return mv;
	}
	@RequestMapping(value = "/getPjListPage")
	@ResponseBody
	public Object getListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String pjzh = pd.getString("pjzh");
		sqltext.append("GID,ZT,ZTMC,PJH,CPR,CPZH,HCDD,CPYH,SKR,SKZH,HRDD,SKYH,JE,SFDYMC,CPRQ");
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.gid,t.zt,decode(t.zt,'00','未使用','10','已领用','20','已开票','30','已报销','40','已核销','50','作废')as ztmc , ");
		sb.append(" t.PJH,t.CPR,t.CPZH,t.HCDD,t.CPYH,t.SKR,t.SKZH,t.HRDD,t.SKYH,t.JE,decode(t.SFDY,'00','否','01','是')sfdymc,to_char(t.CPRQ,'yyyy-MM-dd HH:mm') CPRQ ");
		sb.append(" from CW_PJ t left join cw_pjgm  m on m.gid = t.gmgid where 1=1 and t.pjzh = '"+pjzh+"' ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GID");//主键		
		pageList.setTableName(" ("+sb.toString()+")k ");//表名
	    pageList = pageService.findPageList(pd,pageList);//引用传递		
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		
		return pageInfo.toJson();
	}
	
	/**
	 * 跳转开票页面
	 * @return
	 */
	@RequestMapping(value = "/pjlb_ly")
	public ModelAndView goKPPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		Map map = pjlbService.getPjytMapById(pd.getString("guid"));
		List pjytList=pjlbService.getPjytList();
		mv.addObject("lyrq",new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		mv.addObject("guid", pd.getString("guid"));
		mv.addObject("pjytList", pjytList);
		mv.addObject("pjxxMap", map);
		
//		mv.addObject("operateType", operateType);
		mv.setViewName("pjgl/rcyw/pjlb/pjlb_ly");
		return mv;
	}
	
	/**
	 * 票据领用页面
	 * @return
	 */
	@RequestMapping(value = "/doSavely")
	@ResponseBody
	public Object doSavely() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		int result=0;
		result = pjlbService.editPjlx(pd);
		if (result == 1) {
			return "{success:'true',msg:'信息保存成功！'}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
		
	}
	
	/**
	 * 票据作废
	 * @return
	 */
	@RequestMapping(value = "/doZf")
	@ResponseBody
	public Object doZf() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String gid = pd.getString("guid");
		
		int result=0;
		result = pjlbService.editPjZf(gid);
		if (result == 1) {
			return "{success:'true',msg:'信息保存成功！'}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
		
	}
	/**
	 * 票据报销
	 * @return
	 */
	@RequestMapping(value = "/doBx")
	@ResponseBody
	public Object doBx() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String gid = pd.getString("guid");
		
		int result=0;
		result = pjlbService.editPjBx(gid);
		if (result == 1) {
			return "{success:'true',msg:'信息保存成功！'}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
		
	}
	
	/***
	 * 票据删除
	 * 
	 * */
	@RequestMapping(value = "/doDelete")
	@ResponseBody
	public Object doDelete() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String gid = pd.getString("guid");
	System.err.println("##########################guid="+gid);	
		int result=0;
		result = pjlbService.doDelete(gid);
		if (result == 1) {
			return "{success:'true',msg:'信息保存成功！'}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
		
	}
	
	//页面之间的跳转
		@RequestMapping(value="/pageSkip")
		public ModelAndView PageSkip() {
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
			String pageName="";
			String pjlx=pjlbService.getPjlxById(pd.getString("guid"));
			Map pjMap=pjlbService.getPjxxById(pd.getString("guid"));

			switch (pjlx) {
			case "C7810156711B4421AC8EB0A20F80D8E4":    //普通发票 开票
				pageName="ptfp_kp";
				break;
			case "498BBFE1F8FA45C389D694201ED0F73F":    //收据 开票
				pageName="sj_kp";
				break;
			case "4958SDE1F8FA45C389D694201ED0F73F":	//专用票据 开票
				pageName="zyfp_kp";
				break;
			case "E9DA0768B4E848B894A1828325C863AE":	//增值税专用发票
				pageName="zzs_kp";
				break;
			default:
				pageName="";
				break;
			}
			String user="("+LUser.getRygh()+")"+LUser.getRyxm();
			mv.addObject("guid",pd.getString("guid"));
			mv.addObject("pj",pjMap);
			mv.addObject("zdr",user);
			mv.addObject("cprq",new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
			if(Validate.isNull(pageName)){
				mv.setViewName("pjgl/rcyw/pjlb/pjlb_list");
			}else{
				mv.setViewName("pjgl/rcyw/pjlb/"+pageName);
			}
			return mv;
		}	
	
	
		/**
		 * 票据开票（普通发票）
		 * @return
		 */
		@RequestMapping(value = "/doSavePtfp")
		@ResponseBody
		public Object doSavePtfp() {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			int result=0;
			result = pjlbService.doSavePtfp(pd);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
			
		}
		/**
		 * 票据领用页面
		 * @return
		 */
		@RequestMapping(value = "/doSaveSj")
		@ResponseBody
		public Object doSaveSj() {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			int result=0;
			result = pjlbService.doSaveSjfp(pd);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
			
		}
		/**
		 * 票据领用页面
		 * @return
		 */
		@RequestMapping(value = "/doSaveZyfp")
		@ResponseBody
		public Object doSaveZyfp() {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			int result=0;
			result = pjlbService.doSaveZyfp(pd);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
			
		}
		/**
		 * 票据领用页面
		 * @return
		 */
		@RequestMapping(value = "/doSaveZzsfp")
		@ResponseBody
		public Object doSaveZzsfp() {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			int result=0;
			result = pjlbService.doSaveZzsfp(pd);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
			
		}
		
}
