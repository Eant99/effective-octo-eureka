package com.googosoft.controller.kjhs.bbzx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.bbzx.XmmxzService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;


@Controller
@RequestMapping("/xmmxz")
public class XmmxzController extends BaseController{
	
	@Autowired
	XmmxzService xmmxzService;
	@Autowired
	PageService pageService;
	
	//页面跳转
	@RequestMapping("pageSkip")
	public ModelAndView pageSkip() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String pageName = pd.getString("pageName");
		switch (pageName) {
		case "":
			
			break;
		
		default:
			break;
		}
		mv.setViewName("bbzx/xmmxz/"+pageName);
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
		sqltext.append(" * ");
		tablename.append("(select distinct u.guid,u.xmlx,(select mc from gx_sys_dmb where zl = '"+Constant.JFLX+"' and dm = q.jflx) as jflxmc,"
				+ "q.jflx,u.xmbh,u.xmmc,u.bmbh,'('||r.rybh||')'||r.xm as fzr,"
				+ " '('||dwbh||')'||mc as ssbm,u.ysje as ysje,t.pzbh,T.PZLXMC,to_char(t.pzrq,'yyyy-mm-dd') as pzrq,"
				+ "t.pzzt,s.zy as pzzy, (nvl(s.dfje,0)+nvl(s.jfje,0)) as zcje,u.syje as ye,q.bsqr"
				+ " from" + 
				" cw_pzlrzb t join cw_pzlrmxb s on t.guid = s.pzbh  join cw_xmjbxxb u on s.xmbh = u.xmbh join xminfos q on u.guid = q.guid"
				+ " left join gx_sys_dwb w on w.dwbh=u.bmbh left join gx_sys_ryb r on r.rybh = u.fzr) k ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		String strWhere = "";
		String s = LUser.getRybh();
//		if(!"000104".equals(LUser.getRybh())) {
		if(!"000104".equals(LUser.getDwbh())) {
			strWhere += " and ((bsqr='"+LUser.getGuid()+"' or bsqr='"+LUser.getRybh()+"') and jflx = '02') or (jflx='01' and bmbh='"+LUser.getDwbh()+"')";
		}
		pageList.setStrWhere(strWhere); 
		pageList.setHj1("");//合计
		pd.put("search[value]", getsearchSql(pd));
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	public String getsearchSql(PageData pd) {
		String sv = pd.getString("search[value]");
		StringBuilder sql = new StringBuilder();
		if(sv.contains("{")) {
			Gson gson = new Gson();
			List<Map<String, Object>> list = gson.fromJson(sv,new TypeToken<List<Map<String,Object>>>(){}.getType());
			for (Map<String, Object> map : list) {
				String name = ""+map.get("name");
				name = name.trim();
				String value = ""+map.get("value");
				value = value.trim();
				if(Validate.noNull(value)) {
					if("qssyje".equals(name)) {
						sql.append(" and ye > "+value+" ");
					}else if("jssyje".equals(name)) {
						sql.append(" and ye < "+value+" ");
					}else if("sfbhjzpz".equals(name)) {
						if("0".equals(value)) {
							sql.append(" and pzzt != '02' ");
						}
					}else if("xmlx".equals(name)) {
						sql.append(" and xmlx in (select guid from cw_xmlxb where xmlxmc like '%"+value+"%') ");
					}else {
						sql.append(" and "+name+" like '%"+value+"%'");
					}
				}
			}
		}
		return sql.toString();
	}
}
