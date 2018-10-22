package com.googosoft.controller.jxgl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 精密仪器控制类
 * @author master
 */
@Controller
@RequestMapping(value="/jxpj")
public class JxpjController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	
	
	StateManager sm = new StateManager();
	
	/**
	 * 获取精密仪器信息列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.jybh,K.flh,K.mc,K.yqbh,K.yqmc,k.nd,TO_CHAR(K.rzrq,'yyyy-MM-dd') AS rzrq,TO_CHAR(K.gzrq,'yyyy-MM-dd') AS gzrq,  ");
		sqltext.append(" K.ztbz,K.jybyqbh,K.xh,K.gg,K.cj,K.xss,K.bzxx, ");
		sqltext.append(" decode(K.ZZJ,0,'0.00',trim(to_char(K.ZZJ,'fm999999999990.00'))) AS ZZJ, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.XZ+"' AND M.DM = K.xz) AS xz,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.SYFX+"' AND M.DM = K.syfx) AS syfx,");
		sqltext.append(" k.fzr as fzrbh,fzr,");
		sqltext.append(" K.sydw as sydwbh,(SELECT '('||BMH||')'||MC FROM GX_SYS_DWB C WHERE C.DWBH=K.sydw) AS sydw ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("k.jybh");//主键
		pageList.setStrWhere(" and k.ztbz='"+sm.JMYQ_LR+"' ");
		pageList.setTableName("ZC_JMYQB K");//表名
		pageList.setHj1(" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) ljzjhj," +
				" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj ");
	    pageList = pageService.findPageList(pd,pageList);//引用传递
	    List zjlist = pageService.getPageZjList(pageList);//总计
	  	List hjlist = pageService.getPageHjList(pageList);//当前页
	  	Gson gson = new Gson();
	  	PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()),gson.toJson(pageList.getGroupList()),gson.toJson(zjlist),gson.toJson(hjlist));
	  	return pageInfo.toJson();
	}
	
	
	
	
	
	/**
	 * 导出数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doExp", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String doExp(){
		PageData pd = this.getPageData();
		String id = Validate.isNullToDefault(pd.getString("id"), "")+"";	
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname");// + ".xls";
		//查询数据的sql语句
		String searchJson = CommonUtils.jsonToSql(pd.getString("searchJson"));
		
		String sql = "";
		PageList pagelist = new PageList();

		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		//导出初始化列表
		m.setName("yqbh");
		m.setShowname("资产编号");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("yqmc");
		m.setShowname("仪器名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("flh");
		m.setShowname("分类号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("mc");
		m.setShowname("分类名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sydw");
		m.setShowname("使用单位");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("fzr");
		m.setShowname("负责人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xh");
		m.setShowname("型号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("gg");
		m.setShowname("规格");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("Number");
		m.setColstyle("double");
		m.setName("zzj");
		m.setShowname("总价");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("syfx");
		m.setShowname("使用方向");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("cj");
		m.setShowname("厂家");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xss");
		m.setShowname("销售商");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("gzrq");
		m.setShowname("购置日期");
		mlist.add(m);
		m = null;
		
		getPageListDoExcel(pd,pagelist,id);
		if(Validate.noNull(searchJson)){
			pagelist.setStrWhere(pagelist.getStrWhere()+" "+searchJson+" ");//查询条件语句
		}
		sql = "select " + pagelist.getSqlText() + " from " + pagelist.getTableName() + " where 1 = 1 " + pagelist.getStrWhere() + (Validate.isNull(pagelist.getOrderBy()) ? " order by yqbh" : pagelist.getOrderBy());
		
		//导出方法
		pageService.ExpExcel(sql,realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	/**
	 * 导出sql
	 * @param pd
	 * @param pagelist
	 * @param id
	 * @return
	 */
	private PageList getPageListDoExcel(PageData pd, PageList pageList,String id) {
			pageList.setSqlText("K.jybh,K.flh,K.mc,K.yqbh,K.yqmc,TO_CHAR(K.gzrq, 'yyyy-MM-dd') AS gzrq,K.xh,K.gg,"
					+ "K.cj,K.xss,decode(K.ZZJ, 0, '0.00', trim(to_char(K.ZZJ, 'fm999999999990.00'))) AS ZZJ,"
					+ "(SELECT MC FROM GX_SYS_DMB M WHERE ZL = '"+Constant.SYFX+"' AND M.DM = K.syfx) AS syfx,"
					+ "(SELECT '(' || RYGH || ')' || XM FROM GX_SYS_RYB B WHERE B.RYBH = K.fzr) AS fzr,"
					+ "(SELECT '(' || BMH || ')' || MC FROM GX_SYS_DWB C WHERE C.DWBH = K.sydw) AS sydw ");
			pageList.setKeyId("K.jybh");
			pageList.setStrWhere(" and k.ztbz='"+StateManager.JMYQ_LR+"' ");
			pageList.setStrWhere(Validate.isNullToDefault(pageList.getStrWhere(), "")+"");
			
		if(Validate.noNull(id)){
			StringBuffer in = new StringBuffer();
			String[] arrays =id.split(",");
			for (int i = 0; i < arrays.length; i++) {
				in.append(arrays[i]+"','");
			}
			String ids = in.substring(0,in.length()-3);
			pageList.setStrWhere(Validate.isNullToDefault(pageList.getStrWhere(), "")+" and K.jybh in ('"+ids+"')");
		}
		pageList.setTableName("ZC_JMYQB K");
		return pageList;
	}
}
