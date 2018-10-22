package com.googosoft.controller.srgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/xzsbcx")
public class XzsbcxController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="dictService")
	private DictService dictService;//单例
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	//跳转到薪资申报查询信息列表页面
	@RequestMapping(value = "/goXzsbcxPageList")
	public ModelAndView goXzsbcxPageList() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dm");
		mv.setViewName("srgl/xzsbcx/xzsbcx_list");
		mv.addObject("dwbh",dwbh);
		return mv;
	}
	/**
	 * 获取日常报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append("(select k.guid,k.fflsh,k.shzt,k.checkshzt,k.fffa,k.procinstid,(select mc from gx_sys_dmb where zl = 'zffs' and dm=k.fffs) as fffs,");
		sql.append(" k.ffpc,k.zy,k.ffzje,case k.ffry when 'xs' then '学生' when 'xwry' then '校外人员' when 'xnry' then '校内人员' end as rylx,k.ffry,");
		sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=k.SHZT)shztdm,");
		sql.append(" TO_CHAR(k.LRSJ,'YYYY-MM-DD')LRSJ,k.ffry as rylxdm,");
		sql.append(" (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH)AS XMMC");
		sql.append(" FROM cw_grsrzb K where K.jbr='"+CommonUtils.getRybh()+"' " );
		sql.append(	") B");
		pageList.setSqlText("*");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("guid");
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcel(){
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
		sqltext.append("select k.guid,k.fflsh,k.shzt,k.checkshzt,k.fffa,k.procinstid,(select mc from gx_sys_dmb where zl = 'zffs' and dm=k.fffs) as fffs,");
		sqltext.append(" k.ffpc,k.zy,k.ffzje,case k.ffry when 'xs' then '学生' when 'xwry' then '校外人员' when 'xnry' then '校内人员' end as rylx,k.ffry,");
		sqltext.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=k.SHZT)shztdm,");
		sqltext.append(" TO_CHAR(k.LRSJ,'YYYY-MM-DD')LRSJ,k.ffry as rylxdm,");
		sqltext.append(" (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH)AS XMMC");
		sqltext.append(" FROM cw_grsrzb K where K.jbr='"+CommonUtils.getRybh()+"' " );
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		
		m = new M_largedata();
		m.setName("SHZTDM");
		m.setShowname("审核状态");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("FFLSH");
		m.setShowname("发放流水号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("LRSJ");
		m.setShowname("录入日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("FFFS");
		m.setShowname("发放方式");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("FFFA");
		m.setShowname("发放方案");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ZY");
		m.setShowname("摘要");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XMMC");
		m.setShowname("财务项目");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("RYLX");
		m.setShowname("人员类型");
		mlist.add(m);
		m = null;
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
}
