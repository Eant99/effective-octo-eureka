package com.googosoft.controller.systemset.aqgl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.aqgl.OplogService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
/**
 * 操作日志管理控制类
 */
@Controller
@RequestMapping(value="/oplog")
public class OplogController extends BaseController{

	@Resource(name="oplogService")
	private OplogService oplogService;//单例
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	/**
	 * 跳转操作日志管理  页面
	 */
	@RequestMapping(value="goOplogPage")
	public ModelAndView goOplogPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/aqgl/oplog_list");
		return mv;
	}
	
	/**
	 * 获取操作日志列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object  getPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" LOGBH,(SELECT'('||RYGH||')'||XM FROM GX_SYS_RYB WHERE RYBH=K.RYBH) AS RYBH,TO_CHAR(CZRQ,'yyyy-mm-dd hh24:mi:ss' ) AS CZRQ,CZNR,CZJQ,ZT,case when length(dbh)>31 then '' else dbh end dbh,DJLX,"+OplogFlag.CasLink+",XTBZ,decode(syd,1,'web端',0,'移动端','') as SYD,LLQ  ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("logbh");//主键
		pageList.setStrWhere("");//where条件
		pageList.setTableName("ZC_SYS_OPLOG K ");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 操作日志信息清空
	 */
	@RequestMapping(value="/doDeleteAll")
	@ResponseBody
	
	public Object doDeleteAll(){
		int k = oplogService.doDeleteAll();
		if(k>=0){
			return "{\"success\":\"true\",\"msg\":\"信息清空成功！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"信息清空失败！\"}";
		}
	}
	
	/**
	 * 导出操作日志信息Excel
	 */
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
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("select * from (select LOGBH,(SELECT'('||RYGH||')'||XM FROM GX_SYS_RYB WHERE RYBH=K.RYBH) AS RYBH,TO_CHAR(CZRQ,'yyyy-mm-dd hh24:mi:ss' ) AS CZRQ,CZNR,CZJQ,ZT,DBH,DJLX,"+OplogFlag.CasLink+",XTBZ,decode(syd,1,'web端',0,'移动端','') as SYD,LLQ  from ZC_SYS_OPLOG K where 1=1 ");//表名
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" AND K.LOGBH IN ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" ORDER BY K.CZRQ DESC ");
		sqltext.append(" )K ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("RYBH");
		m1.setShowname("操作人");
		mlist.add(m1);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("CZRQ");
		m2.setShowname("操作日期");
		mlist.add(m2);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("DBH");
		m3.setShowname("单据编号");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("CZLX");
		m4.setShowname("操作类型");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("CZNR");
		m5.setShowname("操作内容");
		mlist.add(m5);
		M_largedata m6 = new M_largedata();
		m6.setColtype("String");
		m6.setName("CZJQ");
		m6.setShowname("操作机器");
		mlist.add(m6);
		
		M_largedata m7 = new M_largedata();
		m7.setColtype("String");
		m7.setName("SYD");
		m7.setShowname("设备类型");
		mlist.add(m7);
		
		
		M_largedata m8 = new M_largedata();
		m8.setColtype("String");
		m8.setName("LLQ");
		m8.setShowname("设备名称");
		mlist.add(m8);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
		
}