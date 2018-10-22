package com.googosoft.controller.wsbx.bxzf;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.wsbx.bxzf.BxzfService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;


@Controller
@RequestMapping("wsbx/bxzf")
public class BxzfController extends BaseController{
	
	@Autowired
	PageService pageService;
	@Autowired
	BxzfService bxzfService;
	
	//页面跳转
	@RequestMapping("pageSkip")
	public ModelAndView pageSkip() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String pageName = pd.getString("pageName");
		System.err.println("__________"+pageName);
		switch (pageName) {
		case "bzzfTree":
			
			break;
		
		default:
			break;
		}
		mv.setViewName("wsbx/bxzf/"+pageName);
		return mv;
	}
	//获取列表数据
	@RequestMapping(value="getPageListData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getPageListData() {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String zl = pd.getString("type");
		if(Validate.isNull(zl)){
			zl="1";
		}
		sqltext.append(" * ");
		if("1".equals(zl)){
			tablename.append(" ( select t.guid,'' as ccywguid,t.djbh,t.bxr,('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxr)||')'||(select r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,"
					+ "(t.szbm || (select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ "t.szbm,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00')as BXZJE,"
					+ "to_char(t.czrq,'yyyy-mm-dd')as CZRQ,'日常报销'as lx from Cw_rcbxzb t left join cw_pzlrzb k on t.guid =k.guid where  t.shzt='8' and k.pzzt='01' and not exists (select 1 from CW_BXZFB b where b.bxid=t.guid))k ");
		}else if("2".equals(zl)){
			tablename.append(" (select t.guid,t.ccywguid as ccywguid,t.djbh,t.sqr as bxr,(select r.xm from gx_sys_ryb r where r.guid=t.sqr) as bxrmc1,"
					+ "(SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ "('('||(select r.rybh from gx_sys_ryb r where r.guid=t.sqr)||')'||(select r.xm from gx_sys_ryb r where r.guid=t.sqr) ) as bxrmc,"
					+ "t.kssj,t.jssj,t.cfdd,t.FJZZS,to_char(T.bxzje,'FM9999999999999990.00')as BXZJE, (select '('|| d.bmh || ')'|| d.mc from gx_sys_dwb d where d.dwbh=(select r.dwbh from gx_sys_ryb r where r.guid= t.sqr)) as szbmmc,"
					+ "t.CZRQ,'差旅费报销'as lx  from Cw_clfbxzb t left join cw_pzlrzb k on t.guid =k.guid where t.shzt='8' and k.pzzt='01' and not exists (select 1 from CW_BXZFB b where b.bxid=t.guid))k ");
		}else if("3".equals(zl)){
			tablename.append(" (select t.guid,'' as ccywguid,t.djbh, t.bxryid as bxr, ('('||(select r.rybh from gx_sys_ryb r where r.guid=t.bxryid)||')'||(select r.xm from gx_sys_ryb r where r.guid = t.bxryid)) as bxrmc,"
					+ " (SELECT T.MC FROM GX_SYS_DMB T where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT )as shztmc,"
					+ " '0' as fjzzs, to_char(T.bxje, 'FM9999999999999990.00') as BXZJE, to_char((select '('|| d.bmh || ')'|| d.mc from gx_sys_dwb d where d.dwbh=(select  r.dwbh from gx_sys_ryb r where r.guid= t.bxryid))) as szbmmc, to_char(t.czrq,'yyyy-mm-dd')as CZRQ, '公务接待费报销' as lx"
					+ " from Cw_gwjdfbxzb t left join cw_pzlrzb k on t.guid =k.guid where t.shzt='8' and k.pzzt='01'  and not exists (select 1 from CW_BXZFB b where b.bxid=t.guid))k");
		}else {
			tablename.append(" ((select t.guid,'' as ccywguid,t.djbh,('(' ||(select r.rybh from gx_sys_ryb r where r.guid = t.bxr) || ')' ||(select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc,"
					+"(SELECT T.MC FROM GX_SYS_DMB T where zl = 'djshzt' AND T.DM = t.SHZT) as shztmc,to_char(t.fjzzs,'fm999999') as fjzzs,to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE,"
					+"('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,to_char(t.czrq, 'yyyy-mm-dd') as CZRQ,'日常报销' as lx from Cw_rcbxzb t left join cw_pzlrzb k on t.guid =k.guid where t.shzt='8' and k.pzzt='01' and not exists (select 1 from CW_BXZFB b where b.bxid=t.guid))"
					+" union"
					+" (select t.guid,'' as ccywguid,t.djbh,('(' ||(select r.rybh from gx_sys_ryb r where r.guid = t.bxryid) || ')' ||(select r.xm from gx_sys_ryb r where r.guid = t.bxryid)) as bxrmc,"
					+"(SELECT T.MC FROM GX_SYS_DMB T where zl = 'djshzt' AND T.DM = t.SHZT) as shztmc,'0' as fjzzs,to_char(T.bxje, 'FM9999999999999990.00') as BXZJE,"
					+"(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.bxryid)) as szbmmc,"
					+"to_char(t.czrq, 'yyyy-mm-dd') as CZRQ,'公务接待费报销' as lx from Cw_gwjdfbxzb t left join cw_pzlrzb k on t.guid =k.guid where t.shzt='8' and k.pzzt='01' and not exists (select 1 from CW_BXZFB b where b.bxid=t.guid))"
					+" union"
					+" (select t.guid,t.ccywguid as ccywguid,t.djbh,('(' ||(select r.rybh from gx_sys_ryb r where r.guid = t.sqr) || ')' ||(select r.xm from gx_sys_ryb r where r.guid = t.sqr)) as bxrmc,"
					+"(SELECT T.MC FROM GX_SYS_DMB T where zl = 'djshzt'AND T.DM = t.SHZT) as shztmc,t.FJZZS,to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE,"
					+"(select '(' || d.bmh || ')' || d.mc from gx_sys_dwb d where d.dwbh =(select r.dwbh from gx_sys_ryb r where r.guid = t.sqr)) as szbmmc,t.CZRQ,'差旅费报销' as lx from Cw_clfbxzb t left join cw_pzlrzb k on t.guid =k.guid where t.shzt='8' and k.pzzt='01' and not exists (select 1 from CW_BXZFB b where b.bxid=t.guid)))k "
					);
		}
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
	pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		//pageList.setStrWhere(" and k.guid not in (select bxid from cw_bxzfb) "); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping("/doZf")
	@ResponseBody
	public Object doZf(HttpSession session) {
		PageData pd = this.getPageData();
		String bxid = pd.getString("bxid");
		String bxlx = pd.getString("bxlx");
		String result = "";
		if(bxzfService.doZf(bxid,bxlx,CommonUtil.getKjzd(session),session) > 0) {
//			result = MessageBox.show(true);
			return MessageBox.toJson(true, "支付成功");
		}else {
//			result = MessageBox.show(false);
			return MessageBox.toJson(false, "支付失败");
		}
//		return result;
	}
}
