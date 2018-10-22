package com.googosoft.controller.ysgl.bmysbz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.LUser;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.sjzd.GX_SYS_DMB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.KmszService;
import com.googosoft.service.xmgl.bmysbz.BmysbzService;
import com.googosoft.service.ysgl.bmysbz.CW_JBZCYSB;
import com.googosoft.service.ysgl.bmysbz.CW_JBZCYSMXB;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSB;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSMXB;
import com.googosoft.service.ysgl.bmysbz.CW_XMZCYSB;
import com.googosoft.service.ysgl.bmysbz.CW_XMZCYSMXB;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;


@Controller
@RequestMapping("/bmysbz")
public class bmysbzController extends BaseController{
	@Resource(name="bmysbzService")
	private BmysbzService bmszService; //单例
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name = "dictService")
	private DictService dictService;// 单例
	
	/**
	 * 部门预算编制树
	 */
	@RequestMapping(value="/bmysbzWindow")
	public ModelAndView goDwxxPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/bmysbz/bmysbzTree");
		return mv;
	}
	/**
	 * 部门预算编制表
	 */
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
		String src = "ysgl/bmysbz/srys/srys_list";
		if("2".equals(dm)){
			src = "ysgl/bmysbz/jbzcys/jbzcys_list";
		}else if("3".equals(dm)){
			src = "ysgl/bmysbz/xmzcys/xmzcys_list";
		}
		
		mv.addObject("map", map);
		mv.setViewName(src);
		return mv;

	}
	/**
	 * 增加收入预算
	 * @return
	 */
	@RequestMapping(value="/goAddsrysPage")
	public ModelAndView goAddsrysPage(){
		ModelAndView mv = this.getModelAndView();
		//定义主键guid
		String guid =this.get32UUID();
		//String mxguid = this.get32UUID();
		String dlr = LUser.getXm();
		String dwmc = LUser.getDwmc();
		String rybh = LUser.getGuid();
		String dwbh = LUser.getDwbh();
		mv.addObject("rybh",rybh);
		mv.addObject("dwbh",dwbh);
		mv.addObject("dwmc",dwmc);
		mv.addObject("dlr",dlr);
		//传guid到页面
		mv.addObject("guid",guid);
		mv.setViewName("ysgl/bmysbz/srys/srys_add");
		
		return mv;
	}
	/**
	 * 增加项目支出预算
	 * @return
	 */
	@RequestMapping(value="/goAddxmzcPage")
	public ModelAndView goAddxmzcPage(){
		List<Map<String, Object>> xmlx = dictService.getDict(Constant.XMFL);// 教师类别
		String dlr = LUser.getXm();
		//定义主键guid
		String guid =this.get32UUID();
		ModelAndView mv = this.getModelAndView();
		//传guid到页面
		String dwmc = LUser.getDwmc();
		String rybh = LUser.getGuid();
		String dwbh = LUser.getDwbh();
		mv.addObject("rybh",rybh);
		mv.addObject("dwbh",dwbh);
		mv.addObject("dwmc",dwmc);
		mv.addObject("dlr",dlr);
		mv.addObject("guid",guid);
		mv.addObject("xmlxList", xmlx);
		mv.setViewName("ysgl/bmysbz/xmzcys/xmzcys_add");
		
		return mv;
	}
	/**
	 * 增加基本支出预算
	 * @return
	 */
	@RequestMapping(value="/goAddjbzcysPage")
	public ModelAndView goAddjbzcysPage(){
		//定义主键guid
		String guid =this.get32UUID();
		String dlr = LUser.getXm();
		ModelAndView mv = this.getModelAndView();
		//传guid到页面
		String dwmc = LUser.getDwmc();
		String rybh = LUser.getGuid();
		String dwbh = LUser.getDwbh();
		mv.addObject("rybh",rybh);
		mv.addObject("dwbh",dwbh);
		mv.addObject("dwmc",dwmc);
		mv.addObject("dlr",dlr);		
		mv.addObject("guid",guid);
		mv.setViewName("ysgl/bmysbz/jbzcys/jbzcys_add");		
		
		return mv;
	}
	
	/**
	 * 增加基本支出预算
	 * @return
	 */
	@RequestMapping(value="/goAddxmzcysPage")
	public ModelAndView goAddxmzcysPage(){	
		ModelAndView mv = this.getModelAndView();		
		mv.setViewName("ysgl/bmysbz/xmzcys/xmzcys_add");		
		return mv;
	}
	/**
	 * 编辑基本支出预算
	 * @return
	 */
	@RequestMapping(value="/goEditxmzcysPage")
	public ModelAndView goEditxmzcysPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();		
		mv.addObject("guid", pd.getString("guid"));
		mv.setViewName("ysgl/bmysbz/xmzcys/xmzcys_edit");		
		return mv;
	}
	/**
	 * 获取收入预算列表数据
	 * 
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getsryslist")
	@ResponseBody
	public Object getsryslist() throws Exception {		
	    PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String dwbh = pd.getString("treedwbh");
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select guid,(select '('||d.DWBH||')' || d.mc from gx_sys_dwb d where d.DWBH = g.sbbm) as sbbm,(select '('||m.rybh||')' || m.xm \r\n" + 
				"         from gx_sys_ryb m \r\n" + 
				"        where m.guid = g.sbry) as sbry,to_char(sbnd,'yyyy')sbnd,(case g.qrzt when '1' then '已确认' else '未确认' end ) as qrzt,\r\n" + 
				"       decode(nvl(dynsrhz,'0'),'0','0.0000',to_char(round(dynsrhz,4),'fm999999999999990.0000'))dynsrhz,\r\n" + 
				"      decode(nvl(densrhz,'0'),'0','0.0000',to_char(round(densrhz,4),'fm999999999999990.0000'))densrhz, \r\n" + 
				"       decode(nvl(dsnsrhz,'0'),'0','0.0000',to_char(round(dsnsrhz,4),'fm999999999999990.0000'))dsnsrhz,\r\n" + 
				"        csyj,kzfw from Cw_srysb g where 1=1  ");
			
		tablename.append(")a");
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
	 * 获取基本支出预算列表数据
	 * 
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getJbzcysList")
	@ResponseBody
	public Object getJbzcysList() throws Exception {		
		
		  PageData pd = this.getPageData();
			StringBuffer sqltext = new StringBuffer();//查询字段
			StringBuffer tablename = new StringBuffer();
			String dwbh = pd.getString("treedwbh");
			PageList pageList = new PageList();
			sqltext.append(" * ");
			tablename.append("(select guid,(case a.qrzt when '1' then '已确认' else '未确认' end ) as qrzt,\r\n" + 
					"       (select '(' || d.DWBH || ')' || d.mc\r\n" + 
					"          from gx_sys_dwb d\r\n" + 
					"         where d.DWBH = a.sbbm) as sbbm,\r\n" + 
					"       (select '(' || m.rybh || ')' || m.xm\r\n" + 
					"          from gx_sys_ryb m\r\n" + 
					"         where m.guid = a.sbry) as sbry,\r\n" + 
					"       to_char(sbnd, 'yyyy') sbnd,\r\n" + 
					"       decode(nvl(dynzchz, '0'),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(dynzchz, 4), 'fm999999999999990.0000')) dynzchz,\r\n" + 
					"       decode(nvl(denzchz, '0'),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(denzchz, 4), 'fm999999999999990.0000')) denzchz,\r\n" + 
					"       decode(nvl(dsnzchz, '0'),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(dsnzchz, 4), 'fm999999999999990.0000')) dsnzchz,\r\n" + 
					"       csyj,\r\n" + 
					"       kzfw\r\n" + 
					"  from cw_jbzcysb A\r\n" + 
					" where 1 = 1)k ");
				
			
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
	 * 获取项目支出预算列表数据
	 * 
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getXmzcysList")
	@ResponseBody
	public Object getXmzcysList() throws Exception {
		 PageData pd = this.getPageData();
			StringBuffer sqltext = new StringBuffer();//查询字段
			StringBuffer tablename = new StringBuffer();
			String dwbh = pd.getString("treedwbh");
			PageList pageList = new PageList();
			sqltext.append(" * ");
			tablename.append(" ( select guid,(case a.qrzt when '1' then '已确认' else '未确认' end ) as qrzt,\r\n" + 
					"                  (select '(' || m.rybh || ')' || m.xm\r\n" + 
					"                     from gx_sys_ryb m\r\n" + 
					"                    where m.guid = a.sbry) as sbry,\r\n" + 
					"                  (select '(' || d.DWBH || ')' || d.mc\r\n" + 
					"                     from gx_sys_dwb d\r\n" + 
					"                    where d.DWBH = a.sbbm) as sbbm, (select  '(' || m.dm || ')' ||  m.mc\r\n" + 
					"                     from gx_sys_dmb m\r\n" + 
					"                    where m.dm = a.xmlx and m.zl='xmfl') as xmlx,"
					+ "\r\n" + 
					"                  to_char(sbnd, 'yyyy') sbnd,\r\n" + 
					"                  ktbh,\r\n" + 
					"                  ktmc,\r\n" + 
					"                  (select '(' || m.rybh || ')' || m.xm\r\n" + 
					"                     from gx_sys_ryb m\r\n" + 
					"                    where m.guid = a.zcr) as zcr,\r\n" + 
					"                  to_char(ktkssj, 'yyyy-mm-dd') ktkssj,\r\n" + 
					"                  to_char(ktjssj, 'yyyy-mm-dd') ktjssj,\r\n" + 
					"                  decode(nvl(dynzchz, '0'),\r\n" + 
					"                         '0',\r\n" + 
					"                         '0.0000',\r\n" + 
					"                         to_char(round(dynzchz, 4), 'fm999999999999990.0000')) dynzchz,\r\n" + 
					"                  decode(nvl(denzchz, '0'),\r\n" + 
					"                         '0',\r\n" + 
					"                         '0.0000',\r\n" + 
					"                         to_char(round(denzchz, 4), 'fm999999999999990.0000')) denzchz,\r\n" + 
					"                  decode(nvl(dsnzchz, '0'),\r\n" + 
					"                         '0',\r\n" + 
					"                         '0.0000',\r\n" + 
					"                         to_char(round(dsnzchz, 4), 'fm999999999999990.0000')) dsnzchz,\r\n" + 
					"                  csyj,\r\n" + 
					"                  kzfw\r\n" + 
					"             from cw_xmzcysb A\r\n" + 
					"            where 1 = 1)k ");
				
			//tablename.append(")a");
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
	 * 添加收入预算信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addSrys",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object addSrys(CW_SRYSB srysb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		int c;
		//String guid = this.get32UUID();
		String guid = pd.getString("guid");
		String sbry = pd.getString("sbry");
		String sbbm = pd.getString("sbbm");
		String sbnd = pd.getString("sbnd");
		String dynsrhz = pd.getString("dynsrhz");
		String densrhz = pd.getString("densrhz"); 
		String dsnsrhz = pd.getString("dsnsrhz");
		String csyj = pd.getString("csyj");
		String kzfw = pd.getString("kzfw");
		String qrzt = pd.getString("qrzt");
		srysb.setGuid(guid);
		srysb.setSbry(sbry);
		srysb.setSbbm(sbbm);
		srysb.setSbnd(sbnd);
		srysb.setDynsrhz(dynsrhz);
		srysb.setDensrhz(densrhz);
		srysb.setDsnsrhz(dsnsrhz);
		srysb.setCsyj(csyj);
		srysb.setKzfw(kzfw);
		srysb.setQrzt(qrzt);
		c=bmszService.doDelete(guid, srysb);
		List list = bmszService.getSrysSbbm(sbbm);
		int j = list.size();
		if(j!=0) {
			b = "{\"success\":\"false\",\"msg\":\"当前部门已经存在！\"}";

		}else {
			i= bmszService.doAdd(srysb);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}
		
	    
		
		return b;
	}
	/**
	 * 添加基本支出预算信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addJbzcys",produces = "text/html;charset=UTF-8")
	@ResponseBody	
	public Object addJbzcys(CW_JBZCYSB jbzcysb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		int c;
		//String guid = this.get32UUID();
		String guid = pd.getString("guid");
		String sbry = pd.getString("sbry");
		String sbbm = pd.getString("sbbm");
		String sbnd = pd.getString("sbnd");
		String dynzchz = pd.getString("dynzchz");
		String denzchz = pd.getString("denzchz"); 
		String dsnzchz = pd.getString("dsnzchz");
		String csyj = pd.getString("csyj");
		String kzfw = pd.getString("kzfw");
		String qrzt = pd.getString("qrzt");
		
		jbzcysb.setGuid(guid);
		jbzcysb.setSbry(sbry);
		jbzcysb.setSbbm(sbbm);
		jbzcysb.setSbnd(sbnd);
		jbzcysb.setDynzchz(dynzchz);
		jbzcysb.setDenzchz(denzchz);
		jbzcysb.setDsnzchz(dsnzchz);
		jbzcysb.setCsyj(csyj);
		jbzcysb.setKzfw(kzfw);
		jbzcysb.setQrzt(qrzt);
		c=bmszService.doDeletejbzcys(guid, jbzcysb);
		List list = bmszService.getJbzcysSbbm(sbbm);
		int j = list.size();
		if(j!=0) {
			b = "{\"success\":\"false\",\"msg\":\"当前部门已经存在！\"}";

		}else {
			i = bmszService.doAddJbzcys(jbzcysb);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}
		
		
		
		return b;
	}
	/**
	 * 添加项目支出预算信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addXmzcys",produces = "text/html;charset=UTF-8")
	@ResponseBody	
	public Object addXmzcys(CW_XMZCYSB xmzcysb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		int c;
		//String guid = this.get32UUID();
		String guid = pd.getString("guid");
		String sbry = pd.getString("sbry");
		String sbbm = pd.getString("sbbm");
		String sbnd = pd.getString("sbnd");
		String ktbh = pd.getString("ktbh");
		String ktmc = pd.getString("ktmc");
		String zcr = pd.getString("zcr");
		String ktkssj = pd.getString("ktkssj");
		String ktjssj = pd.getString("ktjssj");
		String dynzchz = pd.getString("dynzchz");
		String denzchz = pd.getString("denzchz"); 
		String dsnzchz = pd.getString("dsnzchz");
		String csyj = pd.getString("csyj");
		String kzfw = pd.getString("kzfw");
		String qrzt = pd.getString("qrzt");
		
		
		xmzcysb.setGuid(guid);
		xmzcysb.setSbry(sbry);
		xmzcysb.setSbbm(sbbm);
		xmzcysb.setSbnd(sbnd);
		xmzcysb.setKtbh(ktbh);
		xmzcysb.setKtmc(ktmc);
		xmzcysb.setZcr(zcr);
		xmzcysb.setKtkssj(ktkssj);
		xmzcysb.setKtjssj(ktjssj);
		xmzcysb.setDynzchz(dynzchz);
		xmzcysb.setDenzchz(denzchz);
		xmzcysb.setDsnzchz(dsnzchz);
		xmzcysb.setCsyj(csyj);
		xmzcysb.setKzfw(kzfw);
		c=bmszService.doDeletexmzc(guid, xmzcysb);
	    List list= bmszService.getXmzcSbbm(sbbm);
			int j= list.size();
		if(j!=0) {
			b = "{\"success\":\"false\",\"msg\":\"当前部门已经存在！\"}";

		}else {
			i = bmszService.doAddXmzcys(xmzcysb);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
			
		}
		
		
		return b;
	}
	/**
	 * 编辑收入预算信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editSrys",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editSrys(CW_SRYSB srysb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		//String guid = this.get32UUID();
		String guid = pd.getString("guid");
		String sbry = pd.getString("sbry");
		String sbbm = pd.getString("sbbm");
		String sbnd = pd.getString("sbnd");
		String dynsrhz = pd.getString("dynsrhz");
		String densrhz = pd.getString("densrhz"); 
		String dsnsrhz = pd.getString("dsnsrhz");
		String csyj = pd.getString("csyj");
		String kzfw = pd.getString("kzfw");
		String qrzt = pd.getString("qrzt");
		srysb.setGuid(guid);
		srysb.setSbry(sbry);
		srysb.setSbbm(sbbm);
		srysb.setSbnd(sbnd);
		srysb.setDynsrhz(dynsrhz);
		srysb.setDensrhz(densrhz);
		srysb.setDsnsrhz(dsnsrhz);
		srysb.setCsyj(csyj);
		srysb.setKzfw(kzfw);
		srysb.setQrzt(qrzt);
		
		List list = bmszService.getSrysSbbm1(guid,sbbm);
		int j= list.size();
		if(j!=0) {
			b = "{\"success\":\"false\",\"msg\":\"当前部门已经存在！\"}";

			
		}else {
			i = bmszService.doEdit(srysb);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}
		
		
		
		return b;
	}
	/**
	 * 编辑基本支出预算信息
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editJbzcys",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editJbzcys(CW_JBZCYSB jbzcysb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		//String guid = this.get32UUID();
		String guid = pd.getString("guid");
		String sbry = pd.getString("sbry");
		String sbbm = pd.getString("sbbm");
		String sbnd = pd.getString("sbnd");
		String dynzchz = pd.getString("dynzchz");
		String denzchz = pd.getString("denzchz"); 
		String dsnzchz = pd.getString("dsnzchz");
		String csyj = pd.getString("csyj");
		String kzfw = pd.getString("kzfw");
		String qrzt = pd.getString("qrzt");
		
		jbzcysb.setGuid(guid);
		jbzcysb.setSbry(sbry);
		jbzcysb.setSbbm(sbbm);
		jbzcysb.setSbnd(sbnd);
		jbzcysb.setDynzchz(dynzchz);
		jbzcysb.setDenzchz(denzchz);
		jbzcysb.setDsnzchz(dsnzchz);
		jbzcysb.setCsyj(csyj);
		jbzcysb.setKzfw(kzfw);
		jbzcysb.setQrzt(qrzt);
		List list = bmszService.getJbzcysSbbm1(guid,sbbm);
		int j= list.size();
		if(j!=0) {
			b = "{\"success\":\"false\",\"msg\":\"当前部门已经存在！\"}";

		}else {
			i = bmszService.doEditJbzcys(jbzcysb);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}
		
		
		return b;
	}
	/**
	 * 编辑基本支出预算信息
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editXmzcys",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editXmzcys(CW_XMZCYSB xmzcysb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		//String guid = this.get32UUID();
		String guid = pd.getString("guid");
		String sbry = pd.getString("sbry");
		String sbbm = pd.getString("sbbm");
		String sbnd = pd.getString("sbnd");
		String ktmc = pd.getString("ktmc");
		String ktbh = pd.getString("ktbh");
		String zcr = pd.getString("zcr");
		String ktkssj = pd.getString("ktkssj");
		String ktjssj = pd.getString("ktjssj");
		String xmlx = pd.getString("xmlx");
		String dynzchz = pd.getString("dynzchz");
		String denzchz = pd.getString("denzchz"); 
		String dsnzchz = pd.getString("dsnzchz");
		String csyj = pd.getString("csyj");
		String kzfw = pd.getString("kzfw");
		
		xmzcysb.setGuid(guid);
		xmzcysb.setSbry(sbry);
		xmzcysb.setSbbm(sbbm);
		xmzcysb.setSbnd(sbnd);
		xmzcysb.setDynzchz(dynzchz);
		xmzcysb.setDenzchz(denzchz);
		xmzcysb.setDsnzchz(dsnzchz);
		xmzcysb.setCsyj(csyj);
		xmzcysb.setKzfw(kzfw);
		xmzcysb.setKtmc(ktmc);
		xmzcysb.setKtbh(ktbh);
		xmzcysb.setZcr(zcr);
		xmzcysb.setXmlx(xmlx);
		xmzcysb.setKtkssj(ktkssj);
		xmzcysb.setKtjssj(ktjssj);
		
		List list=bmszService.getXmzcSbbm1(guid,sbbm);
		System.out.println("list======"+list);
		int j= list.size();
		System.out.println("j========="+j);
		if(j!=0) {
			b="{\"success\":\"false\",\"msg\":\"当前部门已经存在\"}";
		}else {
			i = bmszService.doEditXmzcys(xmzcysb);
			if(i==1){
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}
		
		
		return b;
	}
	@RequestMapping(value="/srysDel",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object srysDel(CW_SRYSB srysb) throws Exception{
		String b;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		srysb.setGuid(guid);
		int i = 0;
		i = bmszService.doDelete(guid, srysb);
		b="";
		return b;
	}
	/**
	 * 删除基本支出预算明细
	 * 
	 */
	@RequestMapping(value="/delJbzcysmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object delJbzcysmx(CW_JBZCYSMXB jbzcysmx) throws Exception{
		String b;
		PageData pd = this.getPageData();
		String jbzcbh = pd.getString("jbzcbh");
		jbzcysmx.setGuid(jbzcbh);
		int i = 0;
		i = bmszService.doDeleteJbzcysxmx(jbzcbh, jbzcysmx);
		b="";
		return b;
	}
	/**
	 * 删除项目支出预算明细
	 * 
	 */
	@RequestMapping(value="/delXmzcysmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object delXmzcysmx(CW_XMZCYSMXB xmzcysmx) throws Exception{
		String b;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		xmzcysmx.setGuid(guid);
		int i = 0;
		i = bmszService.doDeleteXmzcysxmx(guid, xmzcysmx);
		b="";
		return b;
	}
	
	
	/**
	 * 删除收入预算明细
	 * @param xmzcys
	 * @return deleteSrysmx
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteSrysmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object deleteSrysmx(CW_SRYSMXB srysmxb) throws Exception{
		String b;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		 srysmxb.setGuid(guid);
		
		int i = 0;
		i = bmszService.doDeletesrysmx(guid, srysmxb);
		b="";
		return b;
	}
	
	
	
	
	@RequestMapping(value="/xmzcDel",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xmzcDel(CW_XMZCYSB xmzcys) throws Exception{
		String b;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		xmzcys.setGuid(guid);
		int i = 0;
		i = bmszService.doDeletexmzc(guid, xmzcys);
		b="";
		return b;
	}
	/**
	 * 删除基本支出预算表
	 * @param srysb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/jbzcysDel",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object srysDel(CW_JBZCYSB jbzcysb) throws Exception{
		String b;
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		jbzcysb.setGuid(guid);
		int i = 0;
		i = bmszService.doDeletejbzcys(guid, jbzcysb);
		b="";
		return b;
	}

	/**
	 * 添加收入预算明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addSrysmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object addSrysmx(CW_SRYSMXB srysmx)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		String srysbh = pd.getString("srysbh");
		bmszService.doDeleteSryxmxys(srysbh, srysmx);
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = this.get32UUID();//创建主键
			srysbh = (String) map.get("srysbh");
			String	srxmbh = (String) map.get("srxmbh");
			String dynsr = (String) map.get("dynsr");
			String densr = (String) map.get("densr");
			String dsnsr = (String) map.get("dsnsr");
			    //将字段放入srysmx
			    srysmx.setGuid(guid);
				srysmx.setSrysbh(srysbh);
				srysmx.setSrxmbh(srxmbh);
				srysmx.setDynsr(dynsr);
				srysmx.setDensr(densr);
				srysmx.setDsnsr(dsnsr);
				
				//增加
				bmszService.doAdd1(srysmx);
			
		}
		b="success";
		return b;
	}
	/**
	 * 添加支出预算明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addJbzcysmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object addJbzcysmx(CW_JBZCYSMXB jbzcysmx)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		String jbzcbh = pd.getString("jbzcbh");
		bmszService.doDeleteJbzcysmxjb(jbzcbh, jbzcysmx);
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = this.get32UUID();//创建主键
			jbzcbh = (String) map.get("jbzcbh");
			String	jjkmbh = (String) map.get("jjkmbh");
			String dynzc = (String) map.get("dynzc");
			String denzc = (String) map.get("denzc");
			String dsnzc = (String) map.get("dsnzc");
			String bz = (String) map.get("bz");
			
			    //将字段放入jbzcysmx
			    jbzcysmx.setGuid(guid);
			    jbzcysmx.setJbzcbh(jbzcbh);
			    jbzcysmx.setJjkm(jjkmbh);
			    jbzcysmx.setDynzc(dynzc);
			    jbzcysmx.setDenzc(denzc);
			    jbzcysmx.setDsnzc(dsnzc);
			    jbzcysmx.setBz(bz);
			    
			   // bmszService.doDeleteJbzcysmxjb(jbzcbh, jbzcysmx);
				//增加
				bmszService.doAddJbzcysmx(jbzcysmx);
			
		}
		b="success";
		return b;
	}
	/**
	 * 添加项目出预算明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addXmzcysmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object addXmzcysmx(CW_XMZCYSMXB xmzcysmx)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		String xmzcbh = pd.getString("xmzcbh");
		bmszService.doDeletejXmzcysmxxm(xmzcbh, xmzcysmx);
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = this.get32UUID();//创建主键
			xmzcbh = (String) map.get("xmzcbh");
			String	jjkmbh = (String) map.get("jjkmbh");
			String dynzc = (String) map.get("dynzc");
			String denzc = (String) map.get("denzc");
			String dsnzc = (String) map.get("dsnzc");
			String bz = (String) map.get("bz");
			
			
			    //将字段放入jbzcysmx
			xmzcysmx.setGuid(guid);
			xmzcysmx.setXmzcbh(xmzcbh);
			xmzcysmx.setJjkm(jjkmbh);
			xmzcysmx.setDynzc(dynzc);
			xmzcysmx.setDenzc(denzc);
			xmzcysmx.setDsnzc(dsnzc);
			xmzcysmx.setBz(bz);
				//增加
				bmszService.doAddXmzcysmx(xmzcysmx);
			
		}
		b="success";
		return b;
	}
	/**
	 * 编辑收入预算明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editSrysmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editSrysmx(CW_SRYSMXB srysmx)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			//String guid = this.get32UUID();//创建主键
			String guid = (String) map.get("guid");
			String srysbh = (String) map.get("srysbh");
			String	srxmbh = (String) map.get("srxmbh");
			String dynsr = (String) map.get("dynsr");
			String densr = (String) map.get("densr");
			String dsnsr = (String) map.get("dsnsr");
			if(guid.length()==0) {
				
				guid=this.get32UUID();
			}
			    //将字段放入srysmx
			    srysmx.setGuid(guid);
				srysmx.setSrysbh(srysbh);
				srysmx.setSrxmbh(srxmbh);
				srysmx.setDynsr(dynsr);
				srysmx.setDensr(densr);
				srysmx.setDsnsr(dsnsr);
				
				//删除
				bmszService.doDeleteSryxmx(guid, srysmx);
				//增加
				bmszService.doAdd1(srysmx);
			
		}
		b="success";
		return b;
	}
	/**
	 * 编辑基本支出预算明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editJbzcysmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editJbzcysmx(CW_JBZCYSMXB jbzcysmx)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = (String) map.get("guid");
			String jbzcbh = (String) map.get("jbzcbh");
			String	jjkmbh = (String) map.get("jjkmbh");
			String dynzc = (String) map.get("dynzc");
			String denzc = (String) map.get("denzc");
			String dsnzc = (String) map.get("dsnzc");
			String bz = (String) map.get("bz");
			    //将字段放入srysmx
			if(guid.length()==0) {
				guid=this.get32UUID();
			}
			jbzcysmx.setGuid(guid);
			jbzcysmx.setJjkm(jjkmbh);
			jbzcysmx.setJbzcbh(jbzcbh);
			jbzcysmx.setDynzc(dynzc);
			jbzcysmx.setDenzc(denzc);
			jbzcysmx.setDsnzc(dsnzc);
			jbzcysmx.setBz(bz);
			
			    //删除
			    bmszService.doDeleteJbzcysmx(guid, jbzcysmx);
				//增加
				bmszService.doAddJbzcysmx(jbzcysmx);
			
		}
		b="success";
		return b;
	}
	/**
	 * 得到项目支出预算表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getJbzcmxListaaa")
	@ResponseBody
	public Object getJbzcmxListaaa() throws Exception {
	
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		
		List list = bmszService.getjbzcysmx(dwbh);
		System.out.println("list======="+list);
		return list;
	}
 
	
	/**
	 * 编辑项目支出预算明细信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editXmzcysmx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editXmzcysmx(CW_XMZCYSMXB xmzcysmx)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			//String guid = this.get32UUID();//创建主键
			String guid = (String) map.get("guid");
			String xmzcbh = (String) map.get("xmzcbh");
			String	jjkmbh = (String) map.get("jjkmbh");
			String dynzc = (String) map.get("dynzc");
			String denzc = (String) map.get("denzc");
			String dsnzc = (String) map.get("dsnzc");
			String bz = (String) map.get("bz");
			if(guid.length()==0) {
				
				guid=this.get32UUID();
			}
			    //将字段放入srysmx
			xmzcysmx.setGuid(guid);
			xmzcysmx.setJjkm(jjkmbh);
			xmzcysmx.setXmzcbh(xmzcbh);
			xmzcysmx.setDynzc(dynzc);
			xmzcysmx.setDenzc(denzc);
			xmzcysmx.setDsnzc(dsnzc);
			xmzcysmx.setBz(bz);
			    //删除
			    bmszService.doDeletejXmzcysmx(guid, xmzcysmx);
				//增加
				bmszService.doAddXmzcysmx(xmzcysmx);
			
		}
		b="success";
		return b;
	}
	/**
	 * 跳转收入预算信息编辑页面
	 * @return
	 */
	@RequestMapping(value="/goSrysEditPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goSrysEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		String L = pd.getString("L");
		System.out.println("L==================="+L);
		mv.addObject("guid",guid);
		Map<?, ?> map = bmszService.getObjectById(guid);
		List list = bmszService.getObjectById1(guid);
		mv.addObject("srys", map);
		mv.addObject("srysmx",list);
		if("L".equals(L)) {
			mv.setViewName("ysgl/bmysbz/srys/srys_look");
		}else {
			mv.setViewName("ysgl/bmysbz/srys/srys_edit");
		}
		
		return mv;

	}
	/**
	 * 跳转到基本支出预算信息编辑页面
	 * @return
	 */
	@RequestMapping(value="/goJbzcysEditPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goJbzcysEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String L = pd.getString("L");
		String guid = pd.getString("guid");
		mv.addObject("guid",guid);
		Map<?, ?> map = bmszService.getJbzcysById(guid);
		List list = bmszService.getJbzcysmxById1(guid);
		mv.addObject("srys", map);
		mv.addObject("srysmx",list);
		if("L".equals(L)) {
			mv.setViewName("ysgl/bmysbz/jbzcys/jbzcys_look");

		}else {
			mv.setViewName("ysgl/bmysbz/jbzcys/jbzcys_edit");

		}
		return mv;

	}
	/**
	 * 跳转到项目支出预算信息编辑页面
	 * @return
	 */
	@RequestMapping(value="/goXmzcysEditPage",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goXmzcysEditPage(){
		PageData pd = this.getPageData();
		List<Map<String, Object>> xmlx = dictService.getDict(Constant.XMFL);// 教师类别

		
		
		ModelAndView mv = this.getModelAndView();
		String guid = pd.getString("guid");
		String L = pd.getString("L");
		mv.addObject("guid",guid);
		Map<?, ?> map = bmszService.getXmzcysById(guid);
		List list = bmszService.getXmysmxById1(guid);
		mv.addObject("xmlxList", xmlx);
		
		
		mv.addObject("xmzc", map);
		mv.addObject("xmzcysmx",list);
		
		
	//	System.out.println("xmlxList======="+xmlx);
		//System.out.println("xmzc======="+map);
		if("L".equals(L)){
			mv.setViewName("ysgl/bmysbz/xmzcys/xmzcys_look");

		}else {
			mv.setViewName("ysgl/bmysbz/xmzcys/xmzcys_edit");

		}
		return mv;

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
			return bmszService.getYsLx(pd, rootPath);
		} else {
			return "";
		}
	} 
	
	
	/**
	 * 部门信息弹出窗
	 */
	@RequestMapping(value="/dwpage")
	public ModelAndView dwpage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		String a = pd.getString("a");
		mv.addObject("controlId",controlId);
		mv.addObject("controlId1", controlId1);
		mv.addObject("a", a);
		mv.setViewName("ysgl/bmysbz/dwTree");
		return mv;
	}
	/**
	 * 部门信息弹出窗
	 */
	@RequestMapping(value="/dwTree")
	public ModelAndView dwTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		mv.addObject("controlId",controlId);
		mv.addObject("controlId1",controlId1);
		mv.setViewName("ysgl/bmysbz/dwTree1");
		return mv;
	}
	/**
	 * 经济科目弹出窗
	 */
	@RequestMapping(value="/jjkmTree")
	public ModelAndView jjkmTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		String controlId2 = pd.getString("controlId2");
		mv.addObject("controlId",controlId);
		mv.addObject("controlId1",controlId1);
		mv.addObject("controlId2",controlId2);
		mv.setViewName("ysgl/bmysbz/jjkmWindwo");
		return mv;
	}
	/**
	 * 收入项目窗口
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/srxmPage")
	public ModelAndView srxmPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId2 = pd.getString("controlId2");
		String type = pd.getString("type");
		mv.addObject("controlId", controlId);
		mv.addObject("controlId2", controlId2);

		mv.setViewName("ysgl/bmysbz/srxmList");
		return mv;
		
	}
//	/**
//	 * 经济科目窗口
//	 * @return
//	 * @throws Exception 
//	 */
//	@RequestMapping(value="/jjkmPage")
//	public ModelAndView jjkmPage(){
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = this.getPageData();
//		String controlId = pd.getString("controlId");
//		String controlId2 = pd.getString("controlId2");
//		String type = pd.getString("type");
//		mv.addObject("controlId", controlId);
//		mv.addObject("controlId2", controlId2);
//		mv.setViewName("ysgl/bmysbz/jbzcys/jjkmList");
//		return mv;
//		
//	}
	/**
	 * 获取经济科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/goJjkmPage")
	public ModelAndView goJjkmPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/kmsz/JjkmWindow_list");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	//导出excel
		@RequestMapping(value="/expExcelsrys",produces ="text/json;charset=UTF-8")
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
			sqltext.append("select * from  (select rownum as xh, guid,(select '('||d.DWBH||')' || d.mc from gx_sys_dwb d where d.DWBH = g.sbbm) as sbbm,(select '('||m.rybh||')' || m.xm \r\n" + 
					"         from gx_sys_ryb m \r\n" + 
					"        where m.guid = g.sbry) as sbry,to_char(sbnd,'yyyy')sbnd,\r\n" + 
					"       decode(nvl(dynsrhz,'0'),'0','0.0000',to_char(round(dynsrhz,4),'fm999999999999990.0000'))dynsrhz,\r\n" + 
					"      decode(nvl(densrhz,'0'),'0','0.0000',to_char(round(densrhz,4),'fm999999999999990.0000'))densrhz, \r\n" + 
					"       decode(nvl(dsnsrhz,'0'),'0','0.0000',to_char(round(dsnsrhz,4),'fm999999999999990.0000'))dsnsrhz,\r\n" + 
					"        csyj,kzfw from Cw_srysb g)a where 1=1  ");
			sqltext.append(ToSqlUtil.jsonToSql(searchJson));

			String guid = pd.getString("id");			
			if(Validate.noNull(guid)){
				sqltext.append(" and a.guid in ('"+guid.replace(",", "','")+"') ");
			}
			
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
		/**
		 * 导出基本支出预算表
		 * @return
		 */
		//基本支出预算导出excel
				@RequestMapping(value="/expExcelJbzcys",produces ="text/json;charset=UTF-8")
				@ResponseBody
				public Object expExcelJbzcys(){
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
					String searchValue = pd.getString("searchJson");
					StringBuffer sqltext = new StringBuffer();
					sqltext.append("select  *  from (select rownum as xh, guid,\r\n" + 
							"       (select '(' || d.DWBH || ')' || d.mc\r\n" + 
							"          from gx_sys_dwb d\r\n" + 
							"         where d.DWBH = a.sbbm) as sbbm,\r\n" + 
							"       (select '(' || m.rybh || ')' || m.xm\r\n" + 
							"          from gx_sys_ryb m\r\n" + 
							"         where m.guid = a.sbry) as sbry,\r\n" + 
							"       to_char(sbnd, 'yyyy') sbnd,\r\n" + 
							"       decode(nvl(dynzchz, '0'),\r\n" + 
							"              '0',\r\n" + 
							"              '0.0000',\r\n" + 
							"              to_char(round(dynzchz, 4), 'fm999999999999990.0000')) dynzchz,\r\n" + 
							"       decode(nvl(denzchz, '0'),\r\n" + 
							"              '0',\r\n" + 
							"              '0.0000',\r\n" + 
							"              to_char(round(denzchz, 4), 'fm999999999999990.0000')) denzchz,\r\n" + 
							"       decode(nvl(dsnzchz, '0'),\r\n" + 
							"              '0',\r\n" + 
							"              '0.0000',\r\n" + 
							"              to_char(round(dsnzchz, 4), 'fm999999999999990.0000')) dsnzchz,\r\n" + 
							"       csyj,\r\n" + 
							"       kzfw\r\n" + 
							"  from cw_jbzcysb A\r\n" + 
							" where 1 = 1)k  where 1=1 ");
					sqltext.append(ToSqlUtil.jsonToSql(searchValue));

					String guid = pd.getString("id");
					if(Validate.noNull(guid)){
						sqltext.append(" and k.guid in ('"+guid.replace(",", "','")+"') ");
					}
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
					m5.setName("dynzchz");
					m5.setShowname(dyn+"年支出汇总（万元）");
					mlist.add(m5);
					M_largedata m6 = new M_largedata();
					m6.setColtype("String");
					m6.setName("denzchz");
					m6.setShowname(den+"年支出汇总（万元）");
					mlist.add(m6);
					M_largedata m7 = new M_largedata();
					m7.setColtype("String");
					m7.setName("dsnzchz");
					m7.setShowname(dsn+"年支出汇总（万元）");
					mlist.add(m7);
					M_largedata m8 = new M_largedata();
					m8.setColtype("String");
					m8.setName("csyj");
					m8.setShowname("测算依据");
					mlist.add(m8);
					M_largedata m9 = new M_largedata();
					m9.setColtype("String");
					m9.setName("kzfw");
					m9.setShowname("开支范围");
					mlist.add(m9);
					//导出方法
					pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
					return "{\"url\":\"excel\\\\"+file+".xls\"}";
				}
				/**
				 * 项目支出导出表
				 * @return
				 */
				//项目支出预算导出excel
				@RequestMapping(value="/expExcelXmzcys",produces ="text/json;charset=UTF-8")
				@ResponseBody
				public Object expExcelXmzcys(){
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
					String searchValue = pd.getString("searchJson");
					StringBuffer sqltext = new StringBuffer();
					sqltext.append("select  *  from  ( select rownum as xh, guid,\r\n" + 
							"                  (select '(' || m.rybh || ')' || m.xm\r\n" + 
							"                     from gx_sys_ryb m\r\n" + 
							"                    where m.guid = a.sbry) as sbry,\r\n" + 
							"                  (select '(' || d.DWBH || ')' || d.mc\r\n" + 
							"                     from gx_sys_dwb d\r\n" + 
							"                    where d.DWBH = a.sbbm) as sbbm,\r\n" + 
							"                  to_char(sbnd, 'yyyy') sbnd,\r\n" + 
							"                  ktbh,\r\n" + 
							"                  ktmc,\r\n" + 
							"                  (select '(' || m.rybh || ')' || m.xm\r\n" + 
							"                     from gx_sys_ryb m\r\n" + 
							"                    where m.guid = a.zcr) as zcr,\r\n" + 
							"                  xmlx,\r\n" + 
							"                  to_char(ktkssj, 'yyyy-mm-dd') ktkssj,\r\n" + 
							"                  to_char(ktjssj, 'yyyy-mm-dd') ktjssj,\r\n" + 
							"                  decode(nvl(dynzchz, '0'),\r\n" + 
							"                         '0',\r\n" + 
							"                         '0.0000',\r\n" + 
							"                         to_char(round(dynzchz, 4), 'fm999999999999990.0000')) dynzchz,\r\n" + 
							"                  decode(nvl(denzchz, '0'),\r\n" + 
							"                         '0',\r\n" + 
							"                         '0.0000',\r\n" + 
							"                         to_char(round(denzchz, 4), 'fm999999999999990.0000')) denzchz,\r\n" + 
							"                  decode(nvl(dsnzchz, '0'),\r\n" + 
							"                         '0',\r\n" + 
							"                         '0.0000',\r\n" + 
							"                         to_char(round(dsnzchz, 4), 'fm999999999999990.0000')) dsnzchz,\r\n" + 
							"                  csyj,\r\n" + 
							"                  kzfw\r\n" + 
							"             from cw_xmzcysb A\r\n" + 
							"            where 1 = 1)k  where 1=1");
					sqltext.append(ToSqlUtil.jsonToSql(searchValue));
					String guid = pd.getString("id");
					if(Validate.noNull(guid)){
						sqltext.append(" and k.guid in ('"+guid.replace(",", "','")+"') ");
					}
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
					m5.setName("dynzchz");
					m5.setShowname(dyn+"年支出汇总（万元）");
					mlist.add(m5);
					M_largedata m6 = new M_largedata();
					m6.setColtype("String");
					m6.setName("denzchz");
					m6.setShowname(den+"年支出汇总（万元）");
					mlist.add(m6);
					M_largedata m7 = new M_largedata();
					m7.setColtype("String");
					m7.setName("dsnzchz");
					m7.setShowname(dsn+"年支出汇总（万元）");
					mlist.add(m7);
					M_largedata m8 = new M_largedata();
					m8.setColtype("String");
					m8.setName("csyj");
					m8.setShowname("测算依据");
					mlist.add(m8);
					M_largedata m9 = new M_largedata();
					m9.setColtype("String");
					m9.setName("kzfw");
					m9.setShowname("开支范围");
					mlist.add(m9);
					M_largedata m10 = new M_largedata();
					m10.setColtype("String");
					m10.setName("ktbh");
					m10.setShowname("课题编号");
					mlist.add(m10);
					M_largedata m11 = new M_largedata();
					m11.setColtype("String");
					m11.setName("ktmc");
					m11.setShowname("课题名称");
					mlist.add(m11);
					M_largedata m12 = new M_largedata();
					m12.setColtype("String");
					m12.setName("zcr");
					m12.setShowname("主持人");
					mlist.add(m12);
					M_largedata m13 = new M_largedata();
					m13.setColtype("String");
					m13.setName("xmlx");
					m13.setShowname("项目类型");
					mlist.add(m13);
					M_largedata m14 = new M_largedata();
					m14.setColtype("String");
					m14.setName("ktkssj");
					m14.setShowname("课题开始时间");
					mlist.add(m14);
					M_largedata m15 = new M_largedata();
					m15.setColtype("String");
					m15.setName("ktjssj");
					m15.setShowname("课题结束时间");
					mlist.add(m15);
					//导出方法
					pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
					return "{\"url\":\"excel\\\\"+file+".xls\"}";
				}
		/**
		 * 获取经济科目设置页面信息
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/getJjkmPageList",produces ="text/json;charset=UTF-8")
		@ResponseBody
		public Object getJjkmPageList() throws Exception{
			PageData pd = this.getPageData();
			String treeDm = pd.getString("treeDm");
			String treesearch = pd.getString("treesearch");
			StringBuffer sqltext = new StringBuffer();//查询字段
			sqltext.append("guid,j.KMBH,j.KMMC,KMJC,L,K,SM,DECODE(QYF,'1','是','否')QYF");
		//	sqltext.append("and  WHERE j.k is null and l is null");
			PageList pageList = new PageList();
			pageList.setSqlText(sqltext.toString());
			pageList.setKeyId("guid");//主键
			// 设置WHERE条件
			String strWhere = "";
			if(Validate.noNull(treeDm)){
				strWhere += " and j.k='"+treeDm+"' or j.kmbh = (select kmbh from cw_jjkmb where k  is not null and kmbh='"+treeDm+"') or j.l='"+treeDm+"'";

			}
			strWhere +="and j.k is not null";
			pageList.setStrWhere(strWhere);
			pageList.setTableName("CW_JJKMB j");//表名
			pageList = pageService.findPageList(pd, pageList);
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
		/**
		 * 单位信息列表
		 * @return
		 */
		@RequestMapping("/dwxx")
		@ResponseBody
		public Object getDwxxWindow(){
			String userId = LUser.getRybh();
			PageData pd = this.getPageData();
			String dwbh = pd.getString("dwbh");
			String a = pd.getString("a");
			
			String ischeck = Validate.isNullToDefault(pd.getString("ischeck"), "")+"";
			StringBuffer sqltext = new StringBuffer();//查询字段
			sqltext.append("k.dwbh,k.mc,k.jc,k.dz,to_char(k.jlrq,'yyyy-mm-dd') jlrq,");
			sqltext.append("k.fgld,decode(k.dwzt,'0','禁用','1','正常') dwzt,k.dwjc,k.mjbz,k.pxxh,k.bmh,");
			sqltext.append("k.bmsx,k.bz,k.sysbz,k.sysjb,k.sysgs,k.syslb,k.sysmj,k.jlnf,k.syslx,");
			sqltext.append("(select mc from gx_sys_dmb m where zl='"+Constant.DWXZ+"' and m.dm = k.dwxz) dwxz,");
			sqltext.append("(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh=k.dwld) dwld,");
			sqltext.append("(case k.sjdw when '000000' then '无' else (select '('||c.bmh||')'||to_char(c.mc) from gx_sys_dwb c where c.dwbh=k.sjdw) end) sjdw");
			PageList pageList = new PageList();
			pageList.setSqlText(sqltext.toString());
			pageList.setKeyId("dwbh");//主键
			sqltext = new StringBuffer();
			String from = pd.getString("from");
			if(Validate.noNull(dwbh)){
				//点击左侧单位树的where条件
				sqltext.append(" and k.dwbh in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + dwbh.replace("D", "") + "')");
			}
			
			if("CD".equals(from)){//CD是不限制管理权限
				
			}
			else{
				//当前登录人管理单位权限
				sqltext.append(pageService.getQxsql(userId, "k.dwbh", "D"));
				if("ED".equals(from)){
					sqltext.append(" and k.dwjc = '" + Constant.DWJC_EJ + "' ");
				}
				else if("wxjfhb".equals(from)){
					sqltext.append(" and k.dwjc = '" + Constant.DWJC_EJ + "' and k.dwbh not in (select sydw from zc_wxjfhb where nd = '"+Constant.MR_YEAR()+"') ");
				}
			}
			//System.out.println("a===============12312"+a);
			if("sr".equals(a)) {
				sqltext.append(" and nvl(dwzt,'1') = '1' and dwbh not in (select sr.sbbm from cw_srysb sr where k.dwbh=sr.sbbm ) ");
				System.out.println("111111111111111111");
			}else if("jb".equals(a)) {
				sqltext.append(" and nvl(dwzt,'1') = '1' and dwbh not in (select sr.sbbm from cw_jbzcysb sr where k.dwbh=sr.sbbm ) ");

			}else if("xm".equals(a)) {
				System.out.println("2222222222222222222");
				sqltext.append(" and nvl(dwzt,'1') = '1' and dwbh not in (select sr.sbbm from cw_xmzcysb sr where k.dwbh=sr.sbbm ) ");

			}
			//sqltext.append(" and nvl(dwzt,'1') = '1' and dwbh not in (select sr.sbbm from cw_srysb sr where k.dwbh=sr.sbbm ) ");
		    
		    pageList.setStrWhere(sqltext.toString());
		    
			pageList.setTableName("gx_sys_dwb k");//表名
			pageList.setOrderBy("pxxh,bmh");
		    pageList = pageService.findWindowList(pd,pageList,"D");//引用传递
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
	   
		/**
		 * 
		 * @author  wangguanghua
		 * @version  2018-9-13上午11:02:35
		 *部门项目信息统计的单位信息
		 */
		@RequestMapping("/dwxxbm")
		@ResponseBody
		public Object getBmDwxxWindow(){
			String userId = LUser.getRybh();
			PageData pd = this.getPageData();
			String dwbh = pd.getString("dwbh");
			String a = pd.getString("a");
			
			String ischeck = Validate.isNullToDefault(pd.getString("ischeck"), "")+"";
			StringBuffer sqltext = new StringBuffer();//查询字段
			sqltext.append("k.dwbh,k.mc,k.jc,k.dz,to_char(k.jlrq,'yyyy-mm-dd') jlrq,");
			sqltext.append("k.fgld,decode(k.dwzt,'0','禁用','1','正常') dwzt,k.dwjc,k.mjbz,k.pxxh,k.bmh,");
			sqltext.append("k.bmsx,k.bz,k.sysbz,k.sysjb,k.sysgs,k.syslb,k.sysmj,k.jlnf,k.syslx,");
			sqltext.append("(select mc from gx_sys_dmb m where zl='"+Constant.DWXZ+"' and m.dm = k.dwxz) dwxz,");
			sqltext.append("(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh=k.dwld) dwld,");
			sqltext.append("(case k.sjdw when '000000' then '无' else (select '('||c.bmh||')'||to_char(c.mc) from gx_sys_dwb c where c.dwbh=k.sjdw) end) sjdw");
			PageList pageList = new PageList();
			pageList.setSqlText(sqltext.toString());
			pageList.setKeyId("dwbh");//主键
			sqltext = new StringBuffer();
			String from = pd.getString("from");
			if(Validate.noNull(dwbh)){
				//点击左侧单位树的where条件
				sqltext.append(" and k.dwbh in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + dwbh.replace("D", "") + "')");
			}
			
			if("CD".equals(from)){//CD是不限制管理权限
				
			}
			else{
				//当前登录人管理单位权限
				sqltext.append(pageService.getQxsql(userId, "k.dwbh", "D"));
				if("ED".equals(from)){
					sqltext.append(" and k.dwjc = '" + Constant.DWJC_EJ + "' ");
				}
				else if("wxjfhb".equals(from)){
					sqltext.append(" and k.dwjc = '" + Constant.DWJC_EJ + "' and k.dwbh not in (select sydw from zc_wxjfhb where nd = '"+Constant.MR_YEAR()+"') ");
				}
			}
			//System.out.println("a===============12312"+a);
			if("sr".equals(a)) {
				sqltext.append(" and nvl(dwzt,'1') = '1' and dwbh not in (select sr.sbbm from cw_srysb sr where k.dwbh=sr.sbbm ) ");
				System.out.println("111111111111111111");
			}else if("jb".equals(a)) {
				sqltext.append(" and nvl(dwzt,'1') = '1' and dwbh not in (select sr.sbbm from cw_jbzcysb sr where k.dwbh=sr.sbbm ) ");

			}else if("xm".equals(a)) {
				System.out.println("2222222222222222222");
				sqltext.append(" and nvl(dwzt,'1') = '1' and dwbh not in (select sr.sbbm from cw_xmzcysb sr where k.dwbh=sr.sbbm ) ");

			}
			sqltext.append("  and dwbh  in (select bmbh from cw_bmxmxxtj ) ");
		    
		    pageList.setStrWhere(sqltext.toString());
		    
			pageList.setTableName("gx_sys_dwb k");//表名
			pageList.setOrderBy("pxxh,bmh");
		    pageList = pageService.findWindowList(pd,pageList,"D");//引用传递
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
	   
}
