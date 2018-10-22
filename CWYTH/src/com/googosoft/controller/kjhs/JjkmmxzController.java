package com.googosoft.controller.kjhs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.zxing.pdf417.encoder.PDF417;
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
@RequestMapping(value="/kjhs/jjkmmxz")
public class JjkmmxzController extends BaseController{
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	
	/**
	 * 获取经济科目列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String jjfl = pd.getString("kmbh");
		String starttime = pd.getString("starttime");
		String endtime = pd.getString("endtime");
		//设置查询字段名
		pageList.setSqlText(" * ");
		//设置表名
		StringBuffer tableName = new StringBuffer();
		tableName.append(" (select to_char(zb.pzrq,'yyyy-mm-dd') pzrq,t.guid,zb.guid as pzid,t.xmbh,zb.pzbh, zb.pzzt,zb.pzz,zb.pzlxmc,t.zy,kj.kmmc,t.kmbh,bmbh, ");
		tableName.append(" (select mc from GX_SYS_DWB where dwbh=t.bmbh) as bmmc,t.jjfl,zb.kjqj,zb.pznd, ");
		tableName.append(" (select xmmc from CW_XMJBXXB where xmbh = t.xmbh and bmbh=t.bmbh)xmmc,to_char(nvl(t.jfje,0),'99999999999990.99')jfje,to_char(nvl(t.dfje,0),'99999999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'99999999999990.99')ye ");
		tableName.append(" from CW_PZLRMXB t left join cw_pzlrzb zb on t.pzbh=zb.guid left join CW_KJKMSZB kj on t.kmbh=kj.kmbh where 1=1 and t.jjfl is not null ");
		if(Validate.noNull(jjfl)){
			tableName.append(" and t.jjfl='"+jjfl+"' ");
		}
		if(Validate.noNull(starttime)){
			tableName.append(" and to_char(zb.pzrq, 'yyyy-MM') >='"+starttime+"' ");
		}
		if(Validate.noNull(endtime)){
			tableName.append(" and  to_char(zb.pzrq, 'yyyy-MM') <='"+endtime+"' ");
		}
		tableName.append(" )k ");
		pageList.setTableName(tableName.toString());
		//设置表主键名
		pageList.setKeyId("guid");
		//设置WHERE条件
		String kmbh = pd.getString("treedwbh");
        String strWhere = "";
        if(Validate.noNull(kmbh)){
        	strWhere = " and jjfl like '"+kmbh+"%'";
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
		sqltext.append(" select * from (select to_char(zb.pzrq,'yyyy-mm-dd') pzrq,t.guid,t.xmbh,zb.pzbh,zb.pzzt, zb.pzz,zb.pzlxmc,t.zy,kj.kmmc,t.kmbh,bmbh,(select mc from GX_SYS_DWB where dwbh=t.bmbh) as bmmc,t.jjfl,zb.kjqj,zb.pznd,"
				+ " (select xmmc from CW_XMJBXXB where xmbh = t.xmbh and bmbh=t.bmbh)xmmc,to_char(nvl(t.jfje,0),'99999999999990.99')jfje,to_char(nvl(t.dfje,0),'99999999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'99999999999990.99')ye"
				+ " from CW_PZLRMXB t left join cw_pzlrzb zb on t.pzbh=zb.guid left join CW_KJKMSZB kj on t.kmbh=kj.kmbh)k where 1=1 and jjfl is not null");
				
		String kmbh = pd.getString("treedwbh");
       
		if(Validate.noNull(kmbh)){
			sqltext.append(" and jjfl like '"+kmbh+"%'");
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
		m.setName("KMMC");
		m.setShowname("会计科目");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("BMMC");
		m.setShowname("部门名称");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("XMMC");
		m.setShowname("项目名称");
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
