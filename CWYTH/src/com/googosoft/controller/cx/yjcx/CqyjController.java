package com.googosoft.controller.cx.yjcx;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.DwbService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 超期预警
 */
@Controller
@RequestMapping(value="/cqyj")
public class CqyjController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="dictService")
	private DictService dictService;
	
	@Resource(name="dwbService")
	private DwbService dwbService;//单例
	
	/**
	 * 获取超期预警列表页面
	 * @return
	 */
	@RequestMapping(value="/goCqyjPage")
	public ModelAndView goCqyjPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		mv.addObject("dwbh",dwbh);
		mv.addObject("dwmc",dwbService.getDwxx(dwbh));
		List syfxList = dictService.getDict(Constant.SYFX);//使用方向
		List xzList = dictService.getDict(Constant.XZ);//现状
		mv.addObject("syfxList", syfxList);
		mv.addObject("xzList", xzList);
		mv.setViewName("cx/yjcx/cqyj_list");
		return mv;
	}
	
	/**
	 * 获取超期预警列表数据
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");
		String userId = LUser.getRybh();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" DECODE(K.ZTBZ,'99','财务通过','') AS ZTBZ,nvl(K.SYKH,1)SYKH, K.YQBH AS YQBH,K.YQBH AS YQH,K.YQMC AS YQMC,K.FLMC AS FLMC,K.FLGBM AS FLGBM,K.FLH AS FLH,");
		sqltext.append(" decode(nvl(K.dj,0),0,'0.00',(to_char(round(k.dj,2),'fm99999999999990.00'))) as dj, ");
		sqltext.append(" decode(nvl(K.ZZJ,0),0,'0.00',(to_char(round(k.zzj,2),'fm99999999999990.00'))) as zzj, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL = '"+Constant.SYFX+"'AND DM = K.SYFX ) AS syfx, ");
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=K.sydw) as sydw,K.SYDW AS SYDWH,");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=K.syr) AS syr,K.SYR AS SYRH,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.XZ+"' and DM=K.XZ ) AS XZ, ");
		sqltext.append(" TO_CHAR(K.RZRQ,'YYYY-MM-DD') AS RZRQ,NVL(K.SYNX,'0') AS SYNX, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.JLDW+"' and DM=K.JLDW ) AS JLDW, ");
		sqltext.append(" TO_CHAR(K.GZRQ,'YYYY-MM-DD') AS GZRQ,TO_CHAR(K.DZRRQ,'YYYY-MM-DD') AS DZRRQ, ");
		sqltext.append(" (SELECT '('||D.DDH||')'||D.MC FROM ZC_SYS_DDB D WHERE D.DDH =K.BZXX) AS BZXX,K.BZXX AS CFDD,K.HTBH,K.PZH,K.YSDH, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.JFKM+"' and DM=K.JFKM ) AS JFKM, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZCLY+"' and DM=K.ZCLY ) AS ZCLY, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.JZLX+"' and DM=K.JZLX ) AS JZLX, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.XKML+"' and DM=K.XK ) AS XK, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.XKLB+"' and DM=K.XKLB ) AS XKLB, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=K.GKRY) AS GKRY, ");
		sqltext.append("(case substr(k.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(k.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(k.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(k.gg) end) as gg,");//规格
		sqltext.append("(case substr(k.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(k.xh) end) as xh,");//型号
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=K.GKDW) as GKDW, ");
		sqltext.append(" TO_CHAR(K.GKRQ,'YYYY-MM-DD') AS GKRQ,K.GKYJ,TO_CHAR(K.SHRQ,'YYYY-MM-DD') AS SHRQ,K.SHYJ, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=K.SHR) AS SHR  ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("K.YQBH");//主键
		if(Validate.noNull(dwbh)){
			pageList.setStrWhere(" AND K.SYDW IN(SELECT DWBH FROM GX_SYS_DWB CONNECT BY PRIOR DWBH=SJDW START WITH DWBH='" + dwbh + "') AND K.ZTBZ = '"+StateManager.SHWC+"'  and add_months(sysdate,3)>= add_months(k.rzrq,nvl(k.synx*12,0)) and k.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else{
			//当前登录人管理单位权限
			pageList.setStrWhere(pageService.getQxsql(userId, "k.SYDW", "D") + "AND K.ZTBZ = '"+StateManager.SHWC+"' and add_months(sysdate,3)>= add_months(k.rzrq,nvl(k.synx*12,0)) and k.xz not in (select dm from gx_sys_dmb where zl='41')");
		}
		pageList.setTableName("ZC_ZJB K");//表名
		pageList.setHj1(" " +
				" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj,"+ 
				" decode(nvl(sum(sykh),0),0,'0.00',(to_char(round(sum(sykh),2),'fm99999999999990'))) slhj, " +
				" decode(nvl(sum(DJ),0),0,'0.00',(to_char(round(sum(DJ),2),'fm99999999999990.00'))) DJS");
	    pageList = pageService.findPageList(pd,pageList);//引用传递
	    List zjlist = pageService.getPageZjList(pageList);//总计
		List hjlist = pageService.getPageHjList(pageList);//当前页
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()),gson.toJson(pageList.getGroupList()),gson.toJson(zjlist),gson.toJson(hjlist));
		return pageInfo.toJson();
	}
	
	/**
	 * 导出资产信息Excel
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
		sqltext.append(" SELECT DECODE(K.ZTBZ,'99','财务通过','') AS ZTBZ,nvl(K.SYKH,1)SYKH, K.YQBH AS YQBH,K.YQBH AS YQH,K.YQMC AS YQMC,K.FLMC AS FLMC,K.FLGBM AS FLGBM,K.FLH AS FLH,");
		sqltext.append(" decode(nvl(K.dj,0),0,'0.00',(to_char(round(k.dj,2),'fm99999999999990.00'))) as dj, ");
		sqltext.append(" decode(nvl(K.ZZJ,0),0,'0.00',(to_char(round(k.zzj,2),'fm99999999999990.00'))) as zzj, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL = '"+Constant.SYFX+"'AND DM = K.SYFX ) AS syfx, ");
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=K.sydw) as sydw,K.SYDW AS SYDWH,");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=K.syr) AS syr,K.SYR AS SYRH,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.XZ+"' and DM=K.XZ ) AS XZ, ");
		sqltext.append(" TO_CHAR(K.RZRQ,'YYYY-MM-DD') AS RZRQ,NVL(K.SYNX,'0') AS SYNX, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.JLDW+"' and DM=K.JLDW ) AS JLDW, ");
		sqltext.append(" TO_CHAR(K.GZRQ,'YYYY-MM-DD') AS GZRQ,TO_CHAR(K.DZRRQ,'YYYY-MM-DD') AS DZRRQ, ");
		sqltext.append(" (SELECT '('||D.DDH||')'||D.MC FROM ZC_SYS_DDB D WHERE D.DDH =K.BZXX) AS BZXX,K.BZXX AS CFDD,K.HTBH,K.PZH,K.YSDH, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.JFKM+"' and DM=K.JFKM ) AS JFKM, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.ZCLY+"' and DM=K.ZCLY ) AS ZCLY, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.JZLX+"' and DM=K.JZLX ) AS JZLX, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.XKML+"' and DM=K.XK ) AS XK, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.XKLB+"' and DM=K.XKLB ) AS XKLB, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=K.GKRY) AS GKRY, ");
		sqltext.append("(case substr(k.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(k.sccj) end) as sccj,");//生产厂家
		sqltext.append("(case substr(k.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(k.gg) end) as gg,");//规格
		sqltext.append("(case substr(k.flh,0,2) when '01' then '' when '02' then '' when '16' then '' else to_char(k.xh) end) as xh,");//型号
		sqltext.append(" (select '('||a.dwbh||')'||a.mc from GX_SYS_DWB a WHERE a.dwbh=K.GKDW) as GKDW, ");
		sqltext.append(" TO_CHAR(K.GKRQ,'YYYY-MM-DD') AS GKRQ,K.GKYJ,TO_CHAR(K.SHRQ,'YYYY-MM-DD') AS SHRQ,K.SHYJ, ");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB  WHERE RYBH=K.SHR) AS SHR  ");
		sqltext.append(" FROM ZC_ZJB K WHERE 1=1");
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		String dwbh = pd.getString("treedwbh");
		String userId = LUser.getRybh();
		if(Validate.noNull(dwbh)){
			sqltext.append(" AND K.SYDW IN(SELECT DWBH FROM GX_SYS_DWB CONNECT BY PRIOR DWBH=SJDW START WITH DWBH='" + dwbh + "') AND K.ZTBZ = '"+StateManager.SHWC+"'  and add_months(sysdate,3)>= add_months(k.rzrq,nvl(k.synx*12,0)) and k.xz not in (select dm from gx_sys_dmb where zl='41')");
		}else{
			//当前登录人管理单位权限
			sqltext.append(pageService.getQxsql(userId, "k.SYDW", "D") + "AND K.ZTBZ = '"+StateManager.SHWC+"' and add_months(sysdate,3)>= add_months(k.rzrq,nvl(k.synx*12,0)) and k.xz not in (select dm from gx_sys_dmb where zl='41')");
		}
		if(Validate.noNull(id)){
			sqltext.append(" AND K.YQBH IN ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" ORDER BY K.YQBH ASC ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
//		M_largedata m1 = new M_largedata();
//		m1.setColtype("String");
//		m1.setName("ZTBZ");
//		m1.setShowname("审核状态");
//		mlist.add(m1);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("YQBH");
		m2.setShowname("资产编号");
		mlist.add(m2);
		M_largedata myqmc = new M_largedata();
		myqmc.setColtype("String");
		myqmc.setName("YQMC");
		myqmc.setShowname("资产名称");
		mlist.add(myqmc);
		M_largedata m2flh = new M_largedata();
		m2flh.setColtype("String");
		m2flh.setName("FLH");
		m2flh.setShowname("分类号");
		mlist.add(m2flh);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("FLMC");
		m3.setShowname("分类名称");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("Number");
		m4.setColstyle("double");
		m4.setName("DJ");
		m4.setShowname("单价");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("Number");
		m5.setColstyle("double");
		m5.setName("ZZJ");
		m5.setShowname("总价");
		mlist.add(m5);
		M_largedata m6 = new M_largedata();
		m6.setColtype("Number");
		m6.setName("SYKH");
		m6.setShowname("套（件）数");
		mlist.add(m6);
		M_largedata m7 = new M_largedata();
		m7.setColtype("String");
		m7.setName("JLDW");
		m7.setShowname("计量单位");
		mlist.add(m7);
		M_largedata m8 = new M_largedata();
		m8.setColtype("String");
		m8.setName("GZRQ");
		m8.setShowname("购置日期");
		mlist.add(m8);
		M_largedata m9 = new M_largedata();
		m9.setColtype("Number");
		m9.setName("SYNX");
		m9.setShowname("使用年限");
		mlist.add(m9);
		M_largedata m20 = new M_largedata();
		m20.setColtype("String");
		m20.setName("RZRQ");
		m20.setShowname("入账日期");
		mlist.add(m20);
		M_largedata m10 = new M_largedata();
		m10.setColtype("String");
		m10.setName("SYR");
		m10.setShowname("使用人");
		mlist.add(m10);
		M_largedata m11 = new M_largedata();
		m11.setColtype("String");
		m11.setName("SYFX");
		m11.setShowname("使用方向");
		mlist.add(m11);
		M_largedata m12 = new M_largedata();
		m12.setColtype("String");
		m12.setName("SYDW");
		m12.setShowname("使用单位");
		mlist.add(m12);
		M_largedata m13 = new M_largedata();
		m13.setColtype("String");
		m13.setName("BZXX");
		m13.setShowname("存放地点");
		mlist.add(m13);
		M_largedata m14 = new M_largedata();
		m14.setColtype("String");
		m14.setName("XZ");
		m14.setShowname("现状");
		mlist.add(m14);
		M_largedata m15 = new M_largedata();
		m15.setColtype("String");
		m15.setName("SCCJ");
		m15.setShowname("生产厂家");
		mlist.add(m15);
		M_largedata m16 = new M_largedata();
		m16.setColtype("String");
		m16.setName("XH");
		m16.setShowname("型号");
		mlist.add(m16);
		M_largedata m17 = new M_largedata();
		m17.setColtype("String");
		m17.setName("GG");
		m17.setShowname("规格");
		mlist.add(m17);
		M_largedata m18 = new M_largedata();
		m18.setColtype("String");
		m18.setName("JFKM");
		m18.setShowname("经费来源");
		mlist.add(m18);
		M_largedata m19 = new M_largedata();
		m19.setColtype("String");
		m19.setName("ZCLY");
		m19.setShowname("资产来源");
		mlist.add(m19);
	
		M_largedata m21 = new M_largedata();
		m21.setColtype("String");
		m21.setName("DZRRQ");
		m21.setShowname("调转入日期");
		mlist.add(m21);
		M_largedata m22 = new M_largedata();
		m22.setColtype("String");
		m22.setName("HTBH");
		m22.setShowname("合同编号");
		mlist.add(m22);
		M_largedata m23 = new M_largedata();
		m23.setColtype("String");
		m23.setName("PZH");
		m23.setShowname("凭证号");
		mlist.add(m23);
		M_largedata m24 = new M_largedata();
		m24.setColtype("String");
		m24.setName("YSDH");
		m24.setShowname("验收单号");
		mlist.add(m24);
		M_largedata m25 = new M_largedata();
		m25.setColtype("String");
		m25.setName("JZLX");
		m25.setShowname("记账类型");
		mlist.add(m25);
		M_largedata m26 = new M_largedata();
		m26.setColtype("String");
		m26.setName("XK");
		m26.setShowname("学科");
		mlist.add(m26);
		M_largedata m27 = new M_largedata();
		m27.setColtype("String");
		m27.setName("XKLB");
		m27.setShowname("学科类别");
		mlist.add(m27);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}

}
