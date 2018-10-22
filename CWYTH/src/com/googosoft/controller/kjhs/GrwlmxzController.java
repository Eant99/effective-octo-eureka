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
@RequestMapping(value="/kjhs/grwlmxz")
public class GrwlmxzController extends BaseController{
	@Resource(name="pageService")
	private PageService pageService;//单例

	/**
	 * 获取个人往来明细账列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getGrwlPageList")
	@ResponseBody
	public Object getGrwlPageList(){
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String rybh2 = pd.getString("rybh");
		String starttime = pd.getString("starttime");
		String endtime = pd.getString("endtime");
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" ( ");
		sqltext.append( "select to_char(zb.pzrq, 'yyyy-mm-dd') pzrq,t.guid, zb.pzlxmc,zb.guid as zbid,t.xmbh,t.kmbh,bmbh, zb.pzzt,zb.pzbh,zb.pzz, t.zy,fz.zrr,(select xm from gx_sys_ryb where rybh=fz.zrr) as xm,zb.kjqj,zb.pznd," + 
				 " (select kmmc from CW_KJKMSZB where kmbh=t.kmbh  and  rownum =1 )kjkm,(select kmmc from CW_JJKMB where kmbh=t.jjfl)jjkm,(select mc from gx_sys_dwb where dwbh=t.bmbh )bmmc ,(select xmmc from cw_xmjbxxb where xmbh=t.xmbh and bmbh=t.bmbh) xmmc," + 
				 " to_char(nvl(t.jfje,0),'999999990.99')jfje, to_char(nvl(t.dfje,0),'999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'999999990.99')ye" + 
				 " from cw_pzlrmxb t" + 
				 " left join cw_pzlrzb zb on t.pzbh = zb.guid" + 
				 " left join cw_fzlrb fz on t.guid=fz.kmbh" +
				 " where 1=1 and fz.zrr is not null ");
		if(Validate.noNull(starttime)){
			sqltext.append(" and to_char(zb.pzrq, 'yyyy-MM') >='"+starttime+"' ");
		}
		if(Validate.noNull(endtime)){
			sqltext.append(" and  to_char(zb.pzrq, 'yyyy-MM') <='"+endtime+"' ");
		}
		sqltext.append(" ) k");
		pageList.setSqlText("guid,pzrq,pzbh,pzlxmc,zbid,xmbh,kmbh,bmbh,zy,pzz,pzzt,zrr,xm,kjqj,pznd,kjkm,jjkm,bmmc,xmmc,jfje,dfje,ye");
		//设置表名
		pageList.setTableName(sqltext.toString());
		//设置表主键名
		pageList.setKeyId("guid");
		//设置WHERE条件
		String zrr = pd.getString("treezrr");
        String strWhere = "";
        if(Validate.noNull(rybh2)){
        	strWhere = " and zrr like '"+rybh2+"%'";
        }
        if(Validate.noNull(zrr)){
        	strWhere = " and zrr like '"+zrr+"%'";
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
		sqltext.append("select * from (select to_char(zb.pzrq, 'yyyy-mm-dd') pzrq,t.guid, zb.pzlxmc,t.xmbh,t.kmbh,bmbh, zb.pzzt,zb.pzbh,zb.pzz, t.zy,fz.zrr,(select xm from gx_sys_ryb where rybh=fz.zrr) as xm,zb.kjqj,zb.pznd," + 
				 " (select kmmc from CW_KJKMSZB where kmbh=t.kmbh)kjkm,(select kmmc from CW_JJKMB where kmbh=t.jjfl)jjkm,(select mc from gx_sys_dwb where dwbh=t.bmbh )bmmc ,(select xmmc from cw_xmjbxxb where xmbh=t.xmbh and bmbh=t.bmbh) xmmc," + 
				 " to_char(nvl(t.jfje,0),'999999990.99')jfje, to_char(nvl(t.dfje,0),'999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'999999990.99')ye" + 
				 " from cw_pzlrmxb t" + 
				 " left join cw_pzlrzb zb on t.pzbh = zb.guid" + 
				 " left join cw_fzlrb fz on t.guid=fz.kmbh)k" +
				 " where 1=1 and zrr is not null");
				
		String zrr = pd.getString("treezrr");
       
		if(Validate.noNull(zrr)){
			sqltext.append(" and zrr like '"+zrr+"%'");
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
		m.setName("XM");
		m.setShowname("个人");
		mlist.add(m);
		m = null;

		
		m = new M_largedata();
		m.setName("KJKM");
		m.setShowname("会计科目");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("JJKM");
		m.setShowname("经济科目");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("BMMC");
		m.setShowname("部门");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("XMMC");
		m.setShowname("项目");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("JFJE");
		m.setShowname("借方金额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("DFJE");
		m.setShowname("贷方金额");
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
