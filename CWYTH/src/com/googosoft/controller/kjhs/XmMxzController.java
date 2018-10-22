package com.googosoft.controller.kjhs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.PageService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/kjhs/xmmxz")
public class XmMxzController extends BaseController{
	@Resource(name="pageService")
	private PageService pageService;//单例
	

	/**
	 * 获取项目明细账列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getXmPageList")
	@ResponseBody
	public Object getXmPageList(){
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String starttime = pd.getString("starttime");
		String endtime = pd.getString("endtime");
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" ( ");
		sqltext.append ("select to_char(zb.pzrq, 'yyyy-mm-dd') pzrq,t.xmbh,t.bmbh,t.guid, zb.guid as pzid,t.kmbh,zb.pzlxmc,zb.pzzt, zb.pzbh,zb.pzz,zb.kjqj,zb.pznd, t.zy,(select kmmc from CW_JJKMB where kmbh=t.jjfl)jjkm,(select mc from gx_sys_dwb where dwbh=t.bmbh )bmmc ,(select xmmc from cw_xmjbxxb where xmbh=t.xmbh and bmbh=t.bmbh) xmmc," +
				" to_char(nvl(t.jfje,0),'999999990.99')jfje, to_char(nvl(t.dfje,0),'999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'999999990.99')ye" +
				" from cw_pzlrmxb t" +
				" left join cw_pzlrzb zb on t.pzbh = zb.guid" +
				" left join cw_fzlrb fz on t.guid=fz.kmbh"+
				" where 1=1 and t.xmbh is not null");
		if(Validate.noNull(starttime)){
			sqltext.append(" and  to_char(zb.pzrq, 'yyyy-MM') >='"+starttime+"' ");
		}
		if(Validate.noNull(endtime)){
			sqltext.append(" and  to_char(zb.pzrq, 'yyyy-MM') <='"+endtime+"' ");
		}
		sqltext.append(" ) k");
		pageList.setSqlText("guid,pzid,pzrq,pzbh,pzlxmc,zy,pzz,pzzt,kjqj,pznd,xmmc,xmbh,bmbh,kmbh,jfje,dfje,ye");
		//设置表名
		pageList.setTableName(sqltext.toString());
		//设置表主键名
		pageList.setKeyId("guid");
		//设置WHERE条件
		String xmbh = pd.getString("treexmbh");
		String bmbh = pd.getString("bmbh");
        String strWhere = "";
        if(Validate.noNull(xmbh)){
        	strWhere = " and xmbh like '"+xmbh+"%' and bmbh ='"+bmbh+"'";
        }
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		pageList.setStrWhere(strWhere); //获取管理单位权限
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		String searchValue = pd.getString("searchJson");
		//查询数据的sql语句
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select * from (select to_char(zb.pzrq, 'yyyy-mm-dd') pzrq,t.xmbh,t.bmbh,t.kmbh,t.guid, zb.pzlxmc,zb.pzzt, zb.pzbh,zb.pzz,zb.kjqj,zb.pznd, t.zy,(select kmmc from CW_JJKMB where kmbh=t.jjfl)jjkm,(select mc from gx_sys_dwb where dwbh=t.bmbh )bmmc ,(select xmmc from cw_xmjbxxb where xmbh=t.xmbh and bmbh=t.bmbh) xmmc," +
				" to_char(nvl(t.jfje,0),'999999990.99')jfje, to_char(nvl(t.dfje,0),'999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'999999990.99')ye" +
				" from cw_pzlrmxb t" +
				" left join cw_pzlrzb zb on t.pzbh = zb.guid" +
				" left join cw_fzlrb fz on t.guid=fz.kmbh)k"+
				" where 1=1 and xmbh is not null");
				
		String xmbh = pd.getString("treexmbh");
		String bmbh = pd.getString("bmbh");
		if(Validate.noNull(xmbh)){
			sqltext.append(" and xmbh like '"+xmbh+"%' and bmbh ='"+bmbh+"'");
		}
		
		String guid = pd.getString("guid");
		if(Validate.noNull(guid)){
			sqltext.append(" and guid in ('"+guid+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchValue));
		sqltext.append(" order by pzrq desc");
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("XMMC");
		m.setShowname("项目名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("pzrq");
		m.setShowname("凭证日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("pzlxmc");
		m.setShowname("凭证类型");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("pzbh");
		m.setShowname("凭证号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ZY");
		m.setShowname("摘要");
		mlist.add(m);
		m = null;		
		
		m = new M_largedata();
		m.setName("JFJE");
		m.setShowname("本期发生额（借方）");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("DFJE");
		m.setShowname("本期发生额（贷方）");
		mlist.add(m);
		m = null;
		
		
		m = new M_largedata();
		m.setName("YE");
		m.setShowname("余额");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
}
