package com.googosoft.controller.mbxz;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.ysgl.CW_XMBQMXB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjzdqy.KjzdqyService;
import com.googosoft.service.mbxz.MbxzService;
import com.googosoft.service.systemset.cssz.DlxxService;
import com.googosoft.service.zdscpz.ZdscpzService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
/**
 * 平台系统该设置控制类
 * @author wangzhiduo
 * @date 2017-12-21下午2:13:48
 */
@Controller
@RequestMapping(value ="/mbxz")
public class MbxzController extends BaseController{
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="mbxzService")
	private MbxzService mbxzService;//单例
	@Resource(name="zdscpzService")
	private ZdscpzService zdscpzService;//凭证自动化
	@Resource(name="kjzdqyService")
	private KjzdqyService kjzdqyService;//会计制度启用设置
	@Resource(name="dlxxService")
	private DlxxService dlxxService;//登录信息
	
	/**
	 * 账表模板列表数据
	 * @return
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" GID,ZBMC,DECODE(MBMC,1,'模板一',2,'模板二') AS MBMC ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GID");//主键
		pageList.setTableName(" CW_MBB ");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	//状态设置
	@RequestMapping(value="/ztsz")
	@ResponseBody
	public Object doZtsz(){
		PageData pd = this.getPageData();
		String zt = pd.getString("zt");
		String gid = pd.getString("gid");
		int k = mbxzService.doZtsz(zt,gid);
		StringBuffer msg = new StringBuffer();//存放信息
		if(k>0){
			msg.append("操作成功");
			return "{\"success\":true,\"msg\":\"" + msg.toString() + "\"}";
		}else{
			msg.append("操作失败");
			return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
		}
	}
	
	/**
	 * 账表模板保存
	 * @return
	 */
	@RequestMapping(value="/doSave")
	@ResponseBody
	public Object doSave(){
		PageData pd = this.getPageData();
		String mb = pd.getString("mb");
		String gid = pd.getString("gid");
		boolean bl = mbxzService.doSave(mb,gid);
		StringBuffer msg = new StringBuffer();//存放信息
		if(bl){
			msg.append("操作成功");
			return "{\"success\":true,\"msg\":\"" + msg.toString() + "\"}";
		}else{
			msg.append("操作失败");
			return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
		}
	}
	
	/**
	 * 跳转平台系统设置
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/mbxz_list")
	public ModelAndView goMbxzPage(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		//凭证自动化
		mv.addObject("pz", zdscpzService.getByGid());
		
		mv.addObject("gjc", zdscpzService.getGjc());
		mv.addObject("czc", zdscpzService.getCzc());
		mv.addObject("ctqc", zdscpzService.getCtqc());
		mv.addObject("hc", zdscpzService.getHc());
		mv.addObject("gt1", zdscpzService.getGt1());
		mv.addObject("lc", zdscpzService.getLc());
		mv.addObject("fj", zdscpzService.getFj());
		mv.addObject("pzsz", zdscpzService.getPzsc());
		//借款科目
		Map jkkm =  zdscpzService.getJkkm();
		mv.addObject("jkkm", jkkm);
		Map xxjjhj =  zdscpzService.getXxjjhj();
		mv.addObject("xxjjhj", xxjjhj);
		Map xxjjhd =  zdscpzService.getXxjjhd();
		mv.addObject("xxjjhd", xxjjhd);
		Map xxjjzj =  zdscpzService.getXxjjzj();
		mv.addObject("xxjjzj", xxjjzj);
		Map xxjjzd =  zdscpzService.getXxjjzd();
		mv.addObject("xxjjzd", xxjjzd);
		Map xyjjj =  zdscpzService.getXyjjj();
		mv.addObject("xyjjj", xyjjj);
		Map xyjjd =  zdscpzService.getXyjjd();
		mv.addObject("xyjjd", xyjjd);
		Map glf =  zdscpzService.getGlf();
		mv.addObject("glf", glf);
		Map xxxm =  zdscpzService.getXxxm();
		mv.addObject("xxxm", xxxm);
		Map xyxm =  zdscpzService.getXyxm();
		mv.addObject("xyxm", xyxm);
		
		List fkuc = mbxzService.getFybh();
		
		mv.addObject("fkuc", fkuc);
		//打印信息
		List dyxx=mbxzService.getDyxx();
		mv.addObject("dyxx", dyxx);
		System.err.println("+++++++++++++"+dyxx.size());
		//会计制度启用设置 
		List map1 = this.kjzdqyService.getZd();
		Map zzzy =  mbxzService.getZzzy();
		mv.addObject("zzzy", zzzy);
		mv.addObject("list", map1);
		String kjzd = CommonUtil.getKjzd(session);//获取使用的会计制度
		String sszt = Constant.getztid(session);
		//薪资项目
		Map xzxm =  zdscpzService.getXzxm(sszt);
		mv.addObject("xzxm", xzxm);
		mv.addObject("kjzd", kjzd);
		Map bxlx = mbxzService.getBxlxInfo();
		if(bxlx!=null&&bxlx.size()>0&&!bxlx.isEmpty()){
			mv.addObject("bxlx", bxlx);
		}
		mv.setViewName("ptxtsz/ptxtsz_list");
		return mv;
	}
	
	
	//跳转到模板选择页面
	@RequestMapping(value="/xztc")
	public ModelAndView goXztcPage(){
		ModelAndView mv = this.getModelAndView();
		List list =  mbxzService.getMbList();
		mv.addObject("mb",list);
		mv.setViewName("mbxz/xztc");
		return mv;
	}
	
	@RequestMapping(value="/doSaveMxb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveMxb(CW_XMBQMXB xmbqmxb)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String zbid = pd.getString("zbid");
		mbxzService.doDelMxb(zbid);
		String json = pd.getString("json");	//得到前台的json
		System.out.println("++++++"+json+"++");
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			String fybh = (String) map.get("fybh");
			String fymc = (String) map.get("fymc");
			System.err.println("__________+++++"+fybh+"___+++++"+fymc);
			//增加
			mbxzService.doSaveMxb(fybh,fymc);	
		}
		b="success";
		return b;
	}
    /**
     * baocun 凭证删除 he 借款科目
     */
	@RequestMapping(value="/doSavePzsc",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSavePzsc() throws Exception{
		PageData pd = this.getPageData();
		String ms = pd.getString("ms");
		String kmbh = pd.getString("kmbh");
		String xmbh = pd.getString("xmbh");
		String bmbh = pd.getString("bmbh");
		String xxjjhj = pd.getString("xxjjhj");
		String xxjjhd = pd.getString("xxjjhd");
		String xxjjzj = pd.getString("xxjjzj");
		String xxjjzd = pd.getString("xxjjzd");
		String xyjjj = pd.getString("xyjjj");
		String xyjjd = pd.getString("xyjjd");
		String glf = pd.getString("glf");
		String xxxm = pd.getString("xxxm");
		String xyxm = pd.getString("xyxm");
		String xxxmbmbh = pd.getString("xxxmbmbh");
		String xyxmbmbh = pd.getString("xyxmbmbh");
		String b = "";
		boolean flag = mbxzService.doSavePzsc(ms,kmbh,xmbh,bmbh,xxjjhj,xxjjhd,xxjjzj,xxjjzd,xyjjj,xyjjd,glf,xxxm,xyxm,xxxmbmbh,xyxmbmbh);
		if(flag){
			b="success";
		}
		return b;
	}
	
	@RequestMapping(value="/doSavedzqzwck",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSavedzqzwck() throws Exception{
		PageData pd = this.getPageData();
		String ms = pd.getString("ms");
		String b = "";
		int i = mbxzService.doSavePzscscsc(ms);
		if(i>0){
			b="success";
		}
		return b;
	}
	
	/**
     * baocun 转账摘要
     */
	@RequestMapping(value="/doSaveZzzy",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveZzzy() throws Exception{
		PageData pd = this.getPageData();
		String ms = pd.getString("ms");
		String b = "";
		int i = mbxzService.doSaveZzzy(ms);
		if(i>0){
			b="success";
		}
		return b;
	}
	
	/**
     * 打印维护信息保存
     */
	@RequestMapping(value="/doSaveDyxx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveDyxx() throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		if(mbxzService.doSaveDyxx(pd)) {
			b="success";
		};
		return b;
	}
	
	@RequestMapping(value="/doSaveBxlx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveBxlx()throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String zbid = pd.getString("zbid");
		mbxzService.doDelMxb(zbid);
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		for (i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);//将list转为map
			//增加
			mbxzService.doSaveBxlx(map);	
		}
		b="success";
		return b;
	}
	//获取项目信息弹窗
	@RequestMapping(value= "/getXmxxWindow")
	@ResponseBody
	public ModelAndView getXmxxWindow() {
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		String controlId2 = pd.getString("controlId2");
		String controlId3 = pd.getString("controlId3");
		mv.addObject("controlId", controlId);
		mv.addObject("controlId1", controlId1);
		mv.addObject("controlId2", controlId2);
		mv.addObject("controlId3", controlId3);
		mv.setViewName("ptxtsz/xmxx");
		return mv;
		
	}
	//获取项目信息弹窗列表数据
	@RequestMapping(value="getXmxxPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
			public Object getXmxxPageData(HttpSession session) {
				String sszt = Constant.getztid(session);
				PageData pd = this.getPageData();
				StringBuffer sqltext = new StringBuffer();//查询字段
				StringBuffer tablename = new StringBuffer();
				String strWhere = "";
				PageList pageList = new PageList();
				String bmbh = pd.getString("bmbh");
				sqltext.append(" * ");
				tablename.append(" ( select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbhmc,x.bmbh, x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,"
						+ " x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc,"
						+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
						+ " x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"			
						+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
						+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
						+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
						+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
						+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
						+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"') k");
				pageList.setSqlText(sqltext.toString());
				//设置表名
				pageList.setTableName(tablename.toString());
				if(!Validate.isNullOrEmpty(bmbh)) {
					strWhere  += " and k.bmbh = '"+bmbh+"'";
				}
				//设置表主键名
				pageList.setKeyId("GUID");//主键
				//设置WHERE条件
				pageList.setStrWhere(strWhere);
				pageList.setHj1("");//合计
				pageList = pageService.findPageList(pd,pageList);
				Gson gson = new Gson();
				PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
				return pageInfo.toJson();
			}
}
