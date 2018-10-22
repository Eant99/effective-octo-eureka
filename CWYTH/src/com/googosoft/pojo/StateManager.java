package com.googosoft.pojo;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.googosoft.dao.base.DBHelper;
import com.googosoft.util.Validate;

@Component
public class StateManager {
	
	@Resource(name="jdbcTemplate")
	private DBHelper cacheDao;
	
	private static DBHelper dao;
	
	@PostConstruct
	public void init(){
		this.dao = cacheDao;
	}
	/************************变动状态****************************************/
	/* 资产变动状态   对应zjb表bdzt字段*/
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在单位内变动  1
	 */
	public static final String BDZT_DWN = "1";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在单位间变动  2
	 */
	public static final String BDZT_DWJ = "2";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在单价变动  3
	 */
	public static final String BDZT_DJBD = "3";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在处置  4
	 */
	public static final String BDZT_CZ = "4";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在附件增加  5
	 */
	public static final String BDZT_FJZJ = "5";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在附件处置  6
	 */
	public static final String BDZT_FJCZ = "6";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在处置申报  7
	 */
	public static final String BDZT_CZSB = "7";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在部分调拨  8
	 */
	public static final String BDZT_BFDB = "8";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在部分报废  9
	 */
	public static final String BDZT_BFBF = "9";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 已完成处置  10
	 */
	public static final String BDZT_CZWC = "10";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在项目变动  11
	 */
	public static final String BDZT_XMBD = "11";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在项目维修  12
	 */
	public static final String BDZT_WXBD = "12";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 资产闲置 申请中 13
	 */
	public static final String BDZT_ZCXZ = "13";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正在闲置资产领用申请中  14
	 */
	public static final String BDZT_XZLY = "14";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 闲置资产待公示  15
	 */
	public static final String BDZT_XZDGS = "15";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 闲置资产公示中  16
	 */
	public static final String BDZT_XZGSZ = "16";
	/**
	 * 资产变动状态   对应zjb表bdzt字段
	 * 正常  0
	 */
	public static final String BDZT_Normal= "0";
	
	
	/**
	 * 附件变动状态，对应zc_fjb中的bdzt字段，待处置
	 */
	public static final String FJB_BDZT_DCZ = "1";

	/**
	 * 附件变动状态，对应zc_fjb中的bdzt字段，撤销处置
	 */
	public static final String FJB_BDZT_CXCZ = "0";
	
	/*资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz*/
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--项目变动  0
	 */
	public static final String BDXM_XMBD = "0";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--单价变动  1
	 */
	public static final String BDXM_DJBD = "1";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--附件增加  2
	 */
	public static final String BDXM_FJZJ = "2";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--附件处置  3
	 */
	public static final String BDXM_FJCZ = "3";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--资产调拨  4
	 * 貌似这个标识没用到
	 */
	public static final String BDXM_ZCDB = "4";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--部分调拨  5
	 */
	public static final String BDXM_BFDB = "5";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--部分报废  6
	 */
	public static final String BDXM_BFBF = "6";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--单位内调拨  7
	 */
	public static final String BDXM_DWNDB = "7";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--单位间调拨  8
	 */
	public static final String BDXM_DWJDB = "8";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--使用人变动  9
	 */
	public static final String BDXM_SYRBD = "9";
	/**
	 * 资产变动项目  对应zc_bdbgb表的djbz和zc_bdb表的xmbz
	 * 资产变动项目--存放地点变动  10
	 */
	public static final String BDXM_CFDDBD = "10";
	
	public static final String getBdxm(String colname){
		return "(case " + colname + " when '" + BDXM_XMBD + "' then '项目变动'"
				+" when '" + BDXM_DJBD + "' then '单价变动'"
				+" when '" + BDXM_FJZJ + "' then '增加附件'"
				+" when '" + BDXM_FJCZ + "' then '处置附件'"
				+" when '" + BDXM_ZCDB + "' then '资产调拨'"
				+" when '" + BDXM_BFDB + "' then '部分调拨'"
				+" when '" + BDXM_BFBF + "' then '部分报废'"
				+" when '" + BDXM_DWNDB + "' then '单位内调拨'"
				+" when '" + BDXM_DWJDB + "' then '单位间调拨'"
				+" when '" + BDXM_SYRBD + "' then '使用人变动'"
				+" when '" + BDXM_CFDDBD + "' then '存放地点变动' end) ";
	}
   
	/***********************审核状态*****************************************/
    /***审核完成*/
	public static final String SHWC = "99";
	public static final String GKSHWC = "90";
	public static final String SHYJ = "同意";
	
	
	/*****************资产建账****************/
	/***未提交(领用人和管理员)*/
	public static final String ZCJZ_LR = "55";
	/***管理员未提交(包含领用人提交和管理员未提交)*/
	public static final String ZCJZ_GLYLR = "55,25";
	/***已提交(领用人)*/
	public static final String ZCJZ_TJ = "25";
	/***领用人通过(使用人确认模块)*/
	public static final String ZCJZ_LYR_TG = "60";
	/***领用人退回(使用人确认模块)*/
	public static final String ZCJZ_LYR_TH = "61";
	/***管理员未通过*/
	public static final String ZCJZ_GLY_WTG = "20";
	/***管理员通过(等同于管理员提交)*/
	public static final String ZCJZ_GLY_TG = "00";
	/***归口未通过*/
	public static final String ZCJZ_GK_WTG = "10";
	/***归口通过*/
	public static final String ZCJZ_GK_TG = "90";
	/***财务未通过*/
	public static final String ZCJZ_CW_WTG = "91";
	/***财务通过*/
	public static final String ZCJZ_CW_TG = "99";
	
	/**建账人类型-管理员建账  1**/
	public static final String JZRLX_GLY = "1";
	/**建账人类型-领用人建账  0**/
	public static final String JZRLX_LYR = "0";
	
	/***********************资产变动*********************/
	/***未提交*/
	public static final String ZCBD_LR = "55";
	/***已提交*/
	public static final String ZCBD_TJ = "00";
	/***领用人未通过*/
	public static final String ZCBD_LYR_WTG = "61";
	/***领用人通过*/
	public static final String ZCBD_LYR_TG = "60";
	/***管理员退回*/
	public static final String ZCBD_GLY_WTG = "11";
	/***管理员通过*/
	public static final String ZCBD_GLY_TG = "15";
	/***调出单位领导未通过*/
	public static final String ZCBD_DC_WTG = "71";
	/***调出单位领导通过*/
	public static final String ZCBD_DC_TG = "70";
	/***调入单位领导未通过*/
	public static final String ZCBD_DR_WTG = "81";
	/***调入单位领导通过*/
	public static final String ZCBD_DR_TG = "80";
	/***归口未通过*/
	public static final String ZCBD_GK_WTG = "10";
	/***归口通过*/
	public static final String ZCBD_GK_TG = "90";
	/***财务未通过*/
	public static final String ZCBD_CW_WTG = "91";
	/***财务通过*/
	public static final String ZCBD_CW_TG = "99";
	
	/***********************资产处置*********************/
	/***未提交*/
	public static final String ZCCZ_LR = "55";
	/***已提交*/
	public static final String ZCCZ_TJ = "00";
	/***管理员未通过*/
	public static final String ZCCZ_GLY_WTG = "10";
	/***管理员通过*/
	public static final String ZCCZ_GLY_TG = "11";
	/***归口未通过*/
	public static final String ZCCZ_GK_WTG = "10";
	/***归口通过*/
	public static final String ZCCZ_GK_TG = "90";
	/***财务未通过*/
	public static final String ZCCZ_CW_WTG = "91";
	/***财务通过*/
	public static final String ZCCZ_CW_TG = "99";
	
	
	/**
	 * 获取资产处置状态标识sql
	 * @param ZTBZ
	 * @return
	 */
	public String getCzZtbz(String colName){
		return "(case "+colName+" when '"+ZCCZ_LR+"' then '未提交'"
					+" when '"+ZCCZ_TJ+"' then '已提交'" 
					+" when '"+ZCCZ_GK_WTG+"' then '归口审核退回'" 
					+" when '"+ZCCZ_GK_TG+"' then '归口审核通过'" 
					+" when '"+ZCCZ_CW_WTG+"' then '财务审核退回'" 
					+" when '"+ZCCZ_CW_TG+"' then '财务审核通过' end)as ztbz";
	}
	public String getCzsqZtbz(String colName){
		return "(case "+colName+" when '"+ZCCZ_LR+"' then '未提交'"
		+" when '"+ZCCZ_TJ+"' then '已提交'" 
		+" when '"+ZCCZ_GLY_WTG+"' then '管理员退回'" 
		+" when '"+ZCCZ_GLY_TG+"' then '管理员通过' end)as ztbz";
	}
	/**
	 * 列表上单据状态取值SQL
	 * @param colName
	 * @return
	 */
	public String getCzGroupZtbz(String colName){
		return " case "+colName+" when '"+ZCCZ_LR+"' then 'wtj'"
		+" when '"+ZCCZ_TJ+"' then 'ytj'" 
		+" when '"+ZCCZ_GK_WTG+"' then 'gkth'" 
		+" when '"+ZCCZ_GK_TG+"' then 'gktg'" 
		+" when '"+ZCCZ_CW_WTG+"' then 'cwth'" 
		+" when '"+ZCCZ_CW_TG+"' then 'cwtg' end as ztbzmc ";
	}
	public String getCzsqGroupZtbz(String colName){
		return " case "+colName+" when '"+ZCCZ_LR+"' then 'wtj'"
		+" when '"+ZCCZ_TJ+"' then 'ytj'" 
		+" when '"+ZCCZ_GLY_WTG+"' then 'glyth'" 
		+" when '"+ZCCZ_GLY_TG+"' then 'glytg' end as ztbzmc ";
	}
	
	/***********************精密仪器管理******************************/
	/**
	 * 年使用信息
	 */
	public static final String NSYXX_WTJ="55";
	public static final String NSYXX_YTJ="00";
	public static final String NSYXX_TG="10";
	public static final String NSYXX_TH="90";
	/***未发布*/
	public static final String JMYQ_LR = "01";
	/***已发布*/
	public static final String JMYQ_FB = "55";
	/***已提交*/
	public static final String JMYQ_TJ = "00";
	/***未通过*/
	public static final String JMYQ_WTG = "10";
	/***通过*/
	public static final String JMYQ_TG = "11";
	
	public String getJmyqGroupZtbz(String colName){
		return " case "+colName+" when '"+JMYQ_FB+"' then 'wtj'"
		+" when '"+JMYQ_TJ+"' then 'ytj'" 
		+" when '"+JMYQ_WTG+"' then 'shth'" 
		+" when '"+JMYQ_TG+"' then 'shtg' end as ztbzmc ";
	}
	public String getJmyqZtbz(String colName){
		return "(case "+colName+" when '"+JMYQ_FB+"' then '未提交'"
					+" when '"+JMYQ_TJ+"' then '已提交'" 
					+" when '"+JMYQ_WTG+"' then '审核退回'" 
					+" when '"+JMYQ_TG+"' then '审核通过' end)as ztbzmc";
	}
	/***********************闲置调剂（领用申请）*********************/
	/***未提交*/
	public static final String LYSQ_LR = "55";
	/***已提交*/
	public static final String LYSQ_TJ = "00";
	/***资产管理员通过*/
	public static final String LYSQ_GLY_TG = "90";
	/***资产管理员退回*/
	public static final String LYSQ_GLY_TH = "10";
	/***归口通过*/
	public static final String LYSQ_GK_TG = "99";
	/***归口退回*/
	public static final String LYSQ_GK_TH = "91";
	/**
	 * 获取领用申请状态标识sql、资产闲置申请
	 * @param ZTBZ
	 * @return
	 */
	public String getLysqZtbz(String colName){
		String ztsql = "(case "+colName+" when '"+LYSQ_LR+"' then '未提交'"
				+" when '"+LYSQ_TJ+"' then '已提交'" 
				+" when '"+LYSQ_GLY_TG+"' then '资产主管审核通过'" 
				+" when '"+LYSQ_GLY_TH+"' then '资产主管审核退回'" 
				+" when '"+LYSQ_GK_TG+"' then '归口审核通过'" 
				+" when '"+LYSQ_GK_TH+"' then '归口审核退回'end) ztbzmc";
		return ztsql;
	}
	
	/* 设备维修 --> 维修经费管理  */
	public static final String WFH = "55";
	public static final String YFH = "00";
	
	/**********************维修经费追加******************/
	/***未提交*/
	public static final String WXJFZJ_LR = "55";
	/***已提交*/
	public static final String WXJFZJ_TJ = "00";
	/***院系领导审核通过*/
	public static final String WXJFZJ_DW_TG = "90";
	/***院系领导审核退回*/
	public static final String WXJFZJ_DW_WTG = "10";
	/***财务通过*/
	public static final String WXJFZJ_CW_TG = "99";
	/***财务未通过*/
	public static final String WXJFZJ_CW_WTG = "91";
	
	/***********************维修报告登记*********************/
	/***未提交*/
	public static final String WXBGDJ_LR = "55";
	/***已提交*/
	public static final String WXBGDJ_TJ = "00";
	/***院系领导审核通过*/
	public static final String WXBGDJ_LD_TG = "90";
	/***院系领导审核退回*/
	public static final String WXBGDJ_LD_WTG = "10";
	/***财务通过*/
	public static final String WXBGDJ_CW_TG = "99";
	/***财务未通过*/
	public static final String WXBGDJ_CW_WTG = "91";
	
	/***********************维修申请*********************/
	/***未提交*/
	public static final String WXSQ_LR = "0";
	/***已提交*/
	public static final String WXSQ_TJ = "1";
	/***审核通过*/
	public static final String WXSQ_TG = "2";
	/***审核退回*/
	public static final String WXSQ_WTG = "3";
	
	/***********************资产自查*********************/
	/***未提交*/
	public static final String ZCZC_LR = "0";
	/***已提交*/
	public static final String ZCZC_TJ = "1";
	/***审核通过*/
	public static final String ZCZC_TG = "5";
	/***审核退回*/
	public static final String ZCZC_WTG = "2";
	/*************************************************/
	
	/* 状态标志的类型   */
	public static final String FH_WFH = "FH";//复核未复核一类的
	public static final String TJ_WTJ = "TJ";//提交未提交一类的
	
	/***********************资产折旧*********************/
	/***资产折旧未提交*/
	public static final String ZJWTJ = "55";//资产折旧未提交
	/***资产折旧已提交*/
	public static final String ZJYTJ = "00";//资产折旧已提交（待审核）
	/***资产折旧退回*/
	public static final String ZJTH = "10";//资产折旧退回
	/***资产折旧审核通过*/
	public static final String ZJTG = "90";//资产折旧通过

	/***资产折旧计提未提交*/
	public static final String ZJJT_WTJ = "55";//资产折旧计提未提交
	/***资产折旧计提已提交*/
	public static final String ZJJT_YTJ = "00";//资产折旧计提已提交（待审核）
	/***资产折旧计提退回*/
	public static final String ZJJT_TH = "10";//资产折旧计提退回
	/***资产折旧计提审核通过*/
	public static final String ZJJT_TG = "99";//资产折旧计提通过
	
	/***月工作量未复核*/
	public static final String GZL_WFH = "55";//月工作量未复核
	/***月工作量已复核*/
	public static final String GZL_YFH = "90";//月工作量已复核
	
	/***资产折旧调整未提交*/
	public static final String ZJTZ_WTJ = "55";//资产折旧调整未提交
	/***资产折旧调整已提交*/
	public static final String ZJTZ_YTJ = "00";//资产折旧调整已提交（待审核）
	/***资产折旧调整退回*/
	public static final String ZJTZ_TH = "10";//资产折旧调整退回
	/***资产折旧调整审核通过*/
	public static final String ZJTZ_TG = "90";//资产折旧调整通过
	
	
	/***资产借用申请未提交 55*/
	public static final String ZCJY_SQ_WTJ = "55";
	/***资产借用申请已提交 00*/
	public static final String ZCJY_SQ_YTJ = "00";
	/***资产借用申请审核通过 90*/
	public static final String ZCJY_SQ_TG  = "90";
	/***资产借用申请审核退回 10*/
	public static final String ZCJY_SQ_TH  = "10";
	
	/***资产借用未归还 55*/
	public static final String ZCJY_GH_WGH = "55";
	/***资产借用已归还 99*/
	public static final String ZCJY_GH_YGH = "99";
	
	/**
	 * 获取变动状态名称
	 * @param BDZT  变动状态
	 * @return
	 */
	public String getBdztMc(String BDZT){
		if(BDZT_DWN.equals(BDZT)) return "正在单位内变动";//1
		else if(BDZT_DWJ.equals(BDZT)) return "正在单位间变动";//2
		else if(BDZT_DJBD.equals(BDZT)) return "正在单价变动";//3
		else if(BDZT_CZ.equals(BDZT)) return "正在处置";//4
		else if(BDZT_FJZJ.equals(BDZT)) return "正在附件增加";//5
		else if(BDZT_FJCZ.equals(BDZT)) return "正在附件处置";//6
		else if(BDZT_CZSB.equals(BDZT)) return "正在处置申报";//7
		else if(BDZT_BFDB.equals(BDZT)) return "正在部分调拨";//8
		else if(BDZT_BFBF.equals(BDZT)) return "正在部分报废";//9
		else if(BDZT_CZWC.equals(BDZT)) return "已完成处置";//10
		else if(BDZT_XMBD.equals(BDZT)) return "正在进行项目变动";//10
		else if(BDZT_Normal.equals(BDZT)) return "正常";//0
		else return "正常";
	}
	/**
	 * 获取变动状态sql
	 * @param colName  列名
	 * @return
	 */
	public String getBdztSql(String colName){
		String bdsql=" (case "+colName+" when "+BDZT_DWN+" then '正在单位内变动' " //1
				+ " when "+BDZT_DWJ+" then '正在单位间变动'"//2
			    + " when "+BDZT_DJBD+" then '正在单价变动' "//3
			    + " when "+BDZT_CZ+" then '正在处置' "//4
			    + " when "+BDZT_FJZJ+" then '正在附件增加' "//5
			    + " when "+BDZT_FJCZ+" then '正在附件处置' "//6
			    + " when "+BDZT_CZSB+" then '正在处置申报' "//7
			    + " when "+BDZT_BFDB+" then '正在部分调拨' "//8
			    + " when "+BDZT_BFBF+" then '正在部分报废' "//9
		        + " when "+BDZT_CZWC+" then '已完成处置' "//10
		        + " when "+BDZT_XMBD+" then '正在进行项目变动' "//11
		        + " when "+BDZT_WXBD+" then '正在进行项目维修' "//12
		        + " when "+BDZT_ZCXZ+" then '闲置申请中' "//13正在进行资产闲置
		        + " when "+BDZT_XZLY+" then '领用申请中' "//14闲置资产领用申请中
		        + " when "+BDZT_XZDGS+" then '待公示中' "//15闲置资产待公示中
		        + " when "+BDZT_XZGSZ+" then '公示中' "//16闲置资产公示中
		        + " when "+BDZT_Normal+" then '正常' "//0
        		+ " end) bdzt ";//0			
		return bdsql;
	}
	
	/**
	 * 根据状态类型和字段获取状态名称sql
	 * @param ztlx 状态类型（属于复核未复核一类还是提交未提交一类的）
	 * @param col 字段
	 * @param bm  获取状态标志名称后的别名
	 * @param czmk  操作的模块
	 * @return  获取状态标志中文名称的sql
	 */
	public static String getZtbzmcSql(String ztlx, String col, String bm){
		StringBuffer sql = new StringBuffer();
		if(FH_WFH.equals(ztlx)){
			sql.append(" (case "+col+" when '"+WFH+"' then '未复核' when '"+YFH+"' then '已复核' end) "+bm+" ");
		}else if(TJ_WTJ.equals(ztlx)){
			sql.append(" (case "+col+" when '55' then '未提交' when '00' then '已提交' end) "+bm+" ");
		}
		return sql.toString();
	}
	
	/***资产建账*/
	public static final String CZLX_ZCJZ = "1";
	public static final String CZLX_ZCJZ_GLY = "10";
	/***资产变动*/
	public static final String CZLX_ZCBD = "2";
	/***资产处置申请*/
	public static final String CZLX_ZCCZ = "3";
	/***资产维修报告登记*/
	public static final String CZLX_ZCWX_BGDJ = "4";
	/***资产维修申请*/
	public static final String CZLX_ZCWX_WXSQ = "5";
	/***资产折旧***/
	public static final String CZLX_ZJ = "6";
	/***折旧计提***/
	public static final String CZLX_ZJJT = "7";
	/***折旧工作量设置***/
	public static final String CZLX_ZJGZL = "8";
	/***折旧调整***/
	public static final String CZLX_ZJTZ = "9";
	/***资产维修经费追加*/
	public static final String CZLX_ZCWX_JFZJ = "11";
	/***年使用信息审核*/
	public static final String CZLX_ZCSY_XXSH = "12";
	/*资产自查审核*/
	public static final String CZLX_ZCZC = "13";
	/***资产闲置(领用申请)*/
	public static final String CZLX_ZCXZ = "14";
	/***资产借用-申请*/
	public static final String CZLX_ZCJY_SQ = "15";
	/***资产借用-归还*/
	public static final String CZLX_ZCJY_GH = "16";
	
	/***************业务类型（用于定义gx_sys_ztbzb中的lx）******************/
	public static final String LX_YSHDGL = "01";//验收单管理
	
	/****************************************************************/
	
	
	/**
	 * 获取状态sql
	 * @param czlx     操作类型
	 * @param colName  列名
	 * @return
	 */
	public static String getZtmcSql(String czlx, String colName){
		return getZtmcSql(czlx, colName,"","1");
	}
	
	/**
	 * 获取状态sql
	 * @param czlx     操作类型
	 * @param colName  列名
	 * @param colBm    列别名
	 * @param lb	         类别   1：按最近审核角色显示  2：按审核流程角色显示
	 * @return
	 */
	public static String getZtmcSql(String czlx, String colName, String colBm, String lb){
		String zjsql = "";
		if(czlx.equals(CZLX_ZCJZ)){//资产建账
			if("1".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + ZCJZ_LR + "' then '未提交' "
						+ " when '" + ZCJZ_TJ + "' then '领用人已提交' "
					    + " when '" + ZCJZ_GLY_WTG + "' then '管理员审核退回' "
					    + " when '" + ZCJZ_GLY_TG + "' then '管理员提交' "
					    + " when '" + ZCJZ_GK_WTG + "' then '归口审核退回' "
					    + " when '" + ZCJZ_GK_TG + "' then '归口审核通过' "
					    + " when '" + ZCJZ_CW_WTG + "' then '财务审核退回' "
					    + " when '" + ZCJZ_CW_TG + "' then '财务审核通过' "
				        + " else '' end) " + colBm + " ";
			}
			else if("2".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + ZCJZ_LR + "' then '未提交' "
						+ " when '" + ZCJZ_TJ + "' then '等待管理员审核' "
					    + " when '" + ZCJZ_GLY_WTG + "' then '管理员退回' "
					    + " when '" + ZCJZ_GLY_TG + "' then '等待归口审核' "
					    + " when '" + ZCJZ_GK_WTG + "' then '归口退回' "
					    + " when '" + ZCJZ_GK_TG + "' then '等待财务审核' "
					    + " when '" + ZCJZ_CW_WTG + "' then '财务退回' "
					    + " when '" + ZCJZ_CW_TG + "' then '审核完成' "
				        + " else '' end) " + colBm + " ";
			}
		}
		else if(czlx.equals(CZLX_ZCJZ_GLY)){//管理员建账列表
			if("1".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + ZCJZ_LR + "' then '未提交' "
						+ " when '" + ZCJZ_TJ + "' then '领用人已提交' "
						+ " when '" + ZCJZ_GLY_WTG + "' then '管理员退回' "
					    + " when '" + ZCJZ_GLY_TG + "' then '管理员已提交' "
					    + " when '" + ZCJZ_GK_WTG + "' then '归口审核退回' "
					    + " when '" + ZCJZ_GK_TG + "' then '归口审核通过' "
					    + " when '" + ZCJZ_CW_WTG + "' then '财务审核退回' "
					    + " when '" + ZCJZ_CW_TG + "' then '财务审核通过' "
				        + " else '' end) " + colBm + " ";
			}
			else if("2".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + ZCJZ_LR + "' then '未提交' "
						+ " when '" + ZCJZ_TJ + "' then '等待管理员审核' "
						+ " when '" + ZCJZ_GLY_WTG + "' then '管理员退回' "
					    + " when '" + ZCJZ_GLY_TG + "' then '等待归口审核' "
					    + " when '" + ZCJZ_GK_WTG + "' then '归口退回' "
					    + " when '" + ZCJZ_GK_TG + "' then '等待财务审核' "
					    + " when '" + ZCJZ_CW_WTG + "' then '财务退回' "
					    + " when '" + ZCJZ_CW_TG + "' then '审核完成' "
				        + " else '' end) " + colBm + " ";
			}
		}
		else if(czlx.equals(CZLX_ZCBD)){//资产变动
			if("1".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + ZCBD_LR + "' then '未提交' "
						+ " when '" + ZCBD_TJ + "' then '已提交'"
					    + " when '" + ZCBD_GLY_WTG + "' then '管理员未通过' "
					    + " when '" + ZCBD_GLY_TG + "' then '管理员通过' "
					    + " when '" + ZCBD_LYR_WTG + "' then '领用人未通过' "
					    + " when '" + ZCBD_LYR_TG + "' then '领用人通过' "
					    + " when '" + ZCBD_DC_WTG + "' then '调出单位领导未通过' "
					    + " when '" + ZCBD_DC_TG + "' then '调出单位领导通过' "
					    + " when '" + ZCBD_DR_WTG + "' then '调入单位领导未通过' "
					    + " when '" + ZCBD_DR_TG + "' then '调入单位领导通过' "
					    + " when '" + ZCBD_GK_WTG + "' then '归口审核未通过' "
					    + " when '" + ZCBD_GK_TG + "' then '归口审核通过' "
					    + " when '" + ZCBD_CW_WTG + "' then '财务审核未通过' "
					    + " when '" + ZCBD_CW_TG + "' then '财务审核通过' "
				        + " else '' end) " + colBm + " ";
			}else if("2".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + ZCBD_LR + "' then '未提交' "
						+ " when '" + ZCBD_TJ + "' then '等待领用人确认'"
					    + " when '" + ZCBD_GLY_WTG + "' then '管理员未通过' "
					    + " when '" + ZCBD_GLY_TG + "' then '等待调出单位领导审核' "
					    + " when '" + ZCBD_LYR_WTG + "' then '领用人未通过' "
					    + " when '" + ZCBD_LYR_TG + "' then '等待管理员审核' "
					    + " when '" + ZCBD_DC_WTG + "' then '调出单位领导未通过' "
					    + " when '" + ZCBD_DC_TG + "' then '等待调入单位领导审核' "
					    + " when '" + ZCBD_DR_WTG + "' then '调入单位领导未通过' "
					    + " when '" + ZCBD_DR_TG + "' then '等待归口审核' "
					    + " when '" + ZCBD_GK_WTG + "' then '归口审核未通过' "
					    + " when '" + ZCBD_GK_TG + "' then '等待财务审核' "
					    + " when '" + ZCBD_CW_WTG + "' then '财务审核未通过' "
					    + " when '" + ZCBD_CW_TG + "' then '变动完成' "
				        + " else '' end) " + colBm + " ";
			}else if("3".equals(lb)){//单据状态的sql
				zjsql = " (case " + colName
						+ " when '" + ZCBD_LR + "' then 'wtj' "
						+ " when '" + ZCBD_TJ + "' then 'ytj'"
					    + " when '" + ZCBD_GLY_WTG + "' then 'glywtg' "
					    + " when '" + ZCBD_GLY_TG + "' then 'glytg' "
					    + " when '" + ZCBD_LYR_WTG + "' then 'lyrwtg' "
					    + " when '" + ZCBD_LYR_TG + "' then 'lyrtg' "
					    + " when '" + ZCBD_DC_WTG + "' then 'dcdwldwtg' "
					    + " when '" + ZCBD_DC_TG + "' then 'dcdwldtg' "
					    + " when '" + ZCBD_DR_WTG + "' then 'drdwldwtg' "
					    + " when '" + ZCBD_DR_TG + "' then 'drdwldtg' "
					    + " when '" + ZCBD_GK_WTG + "' then 'gkwtg' "
					    + " when '" + ZCBD_GK_TG + "' then 'gktg' "
					    + " when '" + ZCBD_CW_WTG + "' then 'cwwtg' "
					    + " when '" + ZCBD_CW_TG + "' then 'cwtg' "
				        + " else '' end) " + colBm + " ";
			}else if("4".equals(lb)){//变动项目
				zjsql = " (case " + colName
						+ " when '" + BDXM_XMBD + "' then '项目变动' "
						+ " when '" + BDXM_DJBD + "' then '单价变动'"
					    + " when '" + BDXM_FJZJ + "' then '附件增加' "
					    + " when '" + BDXM_FJCZ + "' then '附件处置' "
					    + " when '" + BDXM_ZCDB + "' then '资产调拨' "
					    + " when '" + BDXM_BFDB + "' then '部分调拨' "
					    + " when '" + BDXM_BFBF + "' then '部分报废' "
					    + " when '" + BDXM_DWNDB + "' then '单位内调拨' "
					    + " when '" + BDXM_DWJDB + "' then '单位间调拨' "
					    + " when '" + BDXM_SYRBD + "' then '使用人变动' "
					    + " when '" + BDXM_CFDDBD + "' then '存放地点变动' "
				        + " else '' end) " + colBm + " ";
			}else if("5".equals(lb)){//单位内变动和使用人变动时，最终审核通过都是90状态，但是这个并不是归口审核通过，所以要特殊处理
				zjsql = " (case " + colName
						+ " when '" + ZCBD_LR + "' then '未提交' "
						+ " when '" + ZCBD_TJ + "' then '已提交'"
					    + " when '" + ZCBD_GLY_WTG + "' then '管理员未通过' "
					    + " when '" + ZCBD_GLY_TG + "' then '管理员通过' "
					    + " when '" + ZCBD_LYR_WTG + "' then '领用人未通过' "
					    + " when '" + ZCBD_LYR_TG + "' then '领用人通过' "
					    + " when '" + ZCBD_DC_WTG + "' then '调出单位领导未通过' "
					    + " when '" + ZCBD_DC_TG + "' then '调出单位领导通过' "
					    + " when '" + ZCBD_DR_WTG + "' then '调入单位领导未通过' "
					    + " when '" + ZCBD_DR_TG + "' then '调入单位领导通过' "
					    + " when '" + ZCBD_GK_WTG + "' then '归口审核未通过' "
					    + " when '" + ZCBD_GK_TG + "' then '审核通过' "
					    + " when '" + ZCBD_CW_WTG + "' then '财务审核未通过' "
					    + " when '" + ZCBD_CW_TG + "' then '财务审核通过' "
				        + " else '' end) " + colBm + " ";
			}
		}
		else if(czlx.equals(CZLX_ZCCZ)){//资产处置
			if("1".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + ZCCZ_LR + "' then '未提交' "
						+ " when '" + ZCCZ_TJ + "' then '已提交'"
					    + " when '" + ZCCZ_GLY_WTG + "' then '管理员未通过' "
					    + " when '" + ZCCZ_GLY_TG + "' then '管理员通过' "
					    + " when '" + ZCCZ_GK_WTG + "' then '归口未通过' "
					    + " when '" + ZCCZ_GK_TG + "' then '归口通过' "
					    + " when '" + ZCCZ_CW_WTG + "' then '财务未通过' "
					    + " when '" + ZCCZ_CW_TG + "' then '财务通过' "
				        + " else '' end) " + colBm + " ";
			}
			else if("2".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + ZCCZ_LR + "' then '未提交' "
						+ " when '" + ZCCZ_TJ + "' then '等待管理员审核'"
					    + " when '" + ZCCZ_GLY_WTG + "' then '管理员退回' "
					    + " when '" + ZCCZ_GLY_TG + "' then '等待归口审核' "
					    + " when '" + ZCCZ_GK_WTG + "' then '归口退回' "
					    + " when '" + ZCCZ_GK_TG + "' then '等待财务审核' "
					    + " when '" + ZCCZ_CW_WTG + "' then '财务退回' "
					    + " when '" + ZCCZ_CW_TG + "' then '审核完成' "
				        + " else '' end) " + colBm + " ";
			}
		}
		else if(czlx.equals(CZLX_ZCWX_BGDJ)){//维修报告登记
			if("1".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + WXBGDJ_LR + "' then '未提交' "
						+ " when '" + WXBGDJ_TJ + "' then '已提交'"
					    + " when '" + WXBGDJ_LD_WTG + "' then '院系领导未通过' "
					    + " when '" + WXBGDJ_LD_TG + "' then '院系领导通过' "
					    + " when '" + WXBGDJ_CW_WTG + "' then '财务未通过' "
					    + " when '" + WXBGDJ_CW_TG + "' then '财务通过' "
				        + " else '' end) " + colBm + " ";
			}
			else if("2".equals(lb)){
				zjsql = " (case " + colName
						+ " when '" + WXBGDJ_LR + "' then '未提交' "
						+ " when '" + WXBGDJ_TJ + "' then '等待院系领导审核'"
					    + " when '" + WXBGDJ_LD_WTG + "' then '院系领导退回' "
					    + " when '" + WXBGDJ_LD_TG + "' then '等待财务审核' "
					    + " when '" + WXBGDJ_CW_WTG + "' then '财务退回' "
					    + " when '" + WXBGDJ_CW_TG + "' then '审核完成' "
				        + " else '' end) " + colBm + " ";
			}
		}
		else if(czlx.equals(CZLX_ZCWX_WXSQ)){
			zjsql = "(case " + colName
						+ " when '" + WXSQ_LR + "' then '未提交' "
						+ " when '" + WXSQ_TJ + "' then '待审核'"
					    + " when '" + WXSQ_TG + "' then '审核通过' "
					    + " when '" + WXSQ_WTG + "' then '审核退回' "
				        + " else '' end) " + colBm + " ";
		}
		else if(czlx.equals(CZLX_ZCWX_JFZJ)){
			if("3".equals(lb)){
				zjsql = "(case " + colName 
							+ " when '" + WXJFZJ_LR + "' then 'wtj' " 
							+ " when '" + WXJFZJ_TJ + "' then 'ytj' " 
							+ " when '" + WXJFZJ_DW_TG + "' then 'gktg' " 
							+ " when '" + WXJFZJ_DW_WTG + "' then 'gkth' "
							+ " when '" + WXJFZJ_CW_TG + "' then 'cwtg' " 
							+ " when '" + WXJFZJ_CW_WTG + "' then 'cwth' "
							+ " else '' end) " + colBm + " ";
			}
			else{
				zjsql = "(case " + colName 
						+ " when '" + WXJFZJ_LR + "' then '未提交' " 
						+ " when '" + WXJFZJ_TJ + "' then '已提交' " 
						+ " when '" + WXJFZJ_DW_TG + "' then '单位领导通过' " 
						+ " when '" + WXJFZJ_DW_WTG + "' then '单位领导退回' "
						+ " when '" + WXJFZJ_CW_TG + "' then '财务审核通过' " 
						+ " when '" + WXJFZJ_CW_WTG + "' then '财务审核退回' "
						+ " else '' end) " + colBm + " ";
			}
		}
		else if(czlx.equals(CZLX_ZJ)){
			zjsql = "(case " + colName 
						+ " when '" + ZJWTJ + "' then '未提交' " 
						+ " when '" + ZJYTJ + "' then '已提交' " 
						+ " when '" + ZJTG + "' then '审核通过' " 
						+ " when '" + ZJTH + "' then '审核未通过' "
						+ " else '' end ) " + colBm + " ";
		}
		else if(czlx.equals(CZLX_ZJTZ)){
			zjsql = "(case " + colName 
					+ " when '" + ZJTZ_WTJ + "' then '未提交' " 
					+ " when '" + ZJTZ_YTJ + "' then '已提交' " 
					+ " when '" + ZJTZ_TG + "' then '通过' " 
					+ " when '" + ZJTZ_TH + "' then '退回' "
					+ " else '' end ) " + colBm + " ";
		}
		else if(czlx.equals(CZLX_ZCSY_XXSH)){
			zjsql = "(case " + colName 
					+ " when '" + NSYXX_WTJ + "' then '未提交' " 
					+ " when '" + NSYXX_YTJ + "' then '已提交' " 
					+ " when '" + NSYXX_TG + "' then '通过' " 
					+ " when '" + NSYXX_TH + "' then '退回' "
					+ " else '' end ) " + colBm + " ";
		}
		else if(CZLX_ZCZC.equals(czlx)){
			zjsql = "(case " + colName 
					+ " when '" + ZCZC_LR + "' then '未提交' " 
					+ " when '" + ZCZC_TJ + "' then '已提交' " 
					+ " when '" + ZCZC_TG + "' then '审核通过' " 
					+ " when '" + ZCZC_WTG + "' then '审核不通过' "
					+ " else '' end ) " + colBm + " ";
		}
		else if(CZLX_ZCJY_SQ.equals(czlx)){
			if("1".equals(lb)){
				zjsql = "(case " + colName 
						+ " when '" + ZCJY_SQ_WTJ + "' then '未提交' " 
						+ " when '" + ZCJY_SQ_YTJ + "' then '已提交' " 
						+ " when '" + ZCJY_SQ_TG + "' then '审核通过' " 
						+ " when '" + ZCJY_SQ_TH + "' then '审核退回' "
						+ " else '' end ) " + colBm + " ";
			}
			else{
				zjsql = "(case " + colName 
						+ " when '" + ZCJY_SQ_WTJ + "' then 'wtj' " 
						+ " when '" + ZCJY_SQ_YTJ + "' then 'ytj' " 
						+ " when '" + ZCJY_SQ_TG + "' then 'shtg' " 
						+ " when '" + ZCJY_SQ_TH + "' then 'shth' "
						+ " else '' end) " + colBm + " ";
			}
		}
		else if(CZLX_ZCJY_GH.equals(czlx)){
			if("1".equals(lb)){
				zjsql = "(case " + colName 
						+ " when '" + ZCJY_GH_WGH + "' then '未归还' " 
						+ " when '" + ZCJY_GH_YGH + "' then '已归还' "
						+ " else '' end ) " + colBm + " ";
			}
			else{
				zjsql = "(case " + colName 
						+ " when '" + ZCJY_GH_WGH + "' then 'wgh' " 
						+ " when '" + ZCJY_GH_YGH + "' then 'ygh' "
						+ " else '' end) " + colBm + " ";
			}
		}
		return zjsql;
	}
	
	/**
	 * 获取状态sql
	 * @param czlx     操作类型
	 * @return
	 */
	public static String getAllZtSql(String czlx){
		return getAllZtSql(czlx,"zt","1");
	}

	/**
	 * 获取状态sql
	 * @param czlx  操作类型
	 * @param lb  	 类别
	 * @return
	 */
	public static String getAllZtSql(String czlx, String lb){
		return getAllZtSql(czlx,"zt",lb);
	}
	
	/**
	 * 获取所有状态的sql
	 * @param czlx     操作类型
	 * @param colName  状态的列名     状态名称的列名：colName+mc  状态标记的列名：colName+bj  状态排序序号的列名：colName+xh
	 * @param lb	         类别   1：按最近审核角色显示  2：按审核流程角色显示
	 * @return
	 */
	public static String getAllZtSql(String czlx, String colName, String lb){
		String zjsql = "";
		if(czlx.equals(CZLX_ZCJZ)){//资产领用人建账
			if("1".equals(lb)){
				zjsql = " ("
						+ " select '" + ZCJZ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
						+ " union all "
						+ " select '" + ZCJZ_TJ + "' " + colName + ",'领用人已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_GLY_WTG + "' " + colName + ",'管理员退回' " + colName + "mc,'glywtg' " + colName + "bj,3 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_GLY_TG + "' " + colName + ",'管理员通过' " + colName + "mc,'glytg' " + colName + "bj,4 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_GK_WTG + "' " + colName + ",'归口退回' " + colName + "mc,'gkwtg' " + colName + "bj,5 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_GK_TG + "' " + colName + ",'归口通过' " + colName + "mc,'gktg' " + colName + "bj,6 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_CW_WTG + "' " + colName + ",'财务退回' " + colName + "mc,'cwwtg' " + colName + "bj,7 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_CW_TG + "' " + colName + ",'财务通过' " + colName + "mc,'cwtg' " + colName + "bj,8 " + colName + "xh from dual "
				        + ") ";
			}
			else if("2".equals(lb)){
				zjsql = " ("
						+ " select '" + ZCJZ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCJZ_TJ + "' " + colName + ",'等待管理员审核' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_GLY_WTG + "' " + colName + ",'管理员退回' " + colName + "mc,'glywtg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_GLY_TG + "' " + colName + ",'等待归口审核' " + colName + "mc,'glytg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_GK_WTG + "' " + colName + ",'归口退回' " + colName + "mc,'gkwtg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_GK_TG + "' " + colName + ",'等待财务审核' " + colName + "mc,'gktg' " + colName + "bj,6 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_CW_WTG + "' " + colName + ",'财务退回' " + colName + "mc,'cwwtg' " + colName + "bj,7 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_CW_TG + "' " + colName + ",'审核完成' " + colName + "mc,'cwtg' " + colName + "bj,8 " + colName + "xh from dual "
				        + ") ";
			}
		}
		else if(czlx.equals(CZLX_ZCJZ_GLY)){//资产管理员建账
			if("1".equals(lb)){
				zjsql = " ("
						+ " select '" + ZCJZ_LR + "' " + colName + ",'未提交' " + colName + "mc,'glywtj' " + colName + "bj,1 " + colName + "xh from dual "
						+ " union all "
//						+ " select '" + ZCJZ_TJ + "' " + colName + ",'领用人已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
//						+ " union all "
//					    + " select '" + ZCJZ_GLY_WTG + "' " + colName + ",'管理员退回' " + colName + "mc,'glywtg' " + colName + "bj,3 " + colName + "xh from dual "
//						+ " union all "
					    + " select '" + ZCJZ_GLY_TG + "' " + colName + ",'已提交' " + colName + "mc,'glytg' " + colName + "bj,4 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_GK_WTG + "' " + colName + ",'归口退回' " + colName + "mc,'gkwtg' " + colName + "bj,5 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_GK_TG + "' " + colName + ",'归口通过' " + colName + "mc,'gktg' " + colName + "bj,6 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_CW_WTG + "' " + colName + ",'财务退回' " + colName + "mc,'cwwtg' " + colName + "bj,7 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + ZCJZ_CW_TG + "' " + colName + ",'财务通过' " + colName + "mc,'cwtg' " + colName + "bj,8 " + colName + "xh from dual "
				        + ") ";
			}
			else if("2".equals(lb)){
				zjsql = " ("
						+ " select '" + ZCJZ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCJZ_TJ + "' " + colName + ",'等待管理员审核' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_GLY_WTG + "' " + colName + ",'管理员退回' " + colName + "mc,'glywtg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_GLY_TG + "' " + colName + ",'等待归口审核' " + colName + "mc,'glytg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_GK_WTG + "' " + colName + ",'归口退回' " + colName + "mc,'gkwtg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_GK_TG + "' " + colName + ",'等待财务审核' " + colName + "mc,'gktg' " + colName + "bj,6 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_CW_WTG + "' " + colName + ",'财务退回' " + colName + "mc,'cwwtg' " + colName + "bj,7 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCJZ_CW_TG + "' " + colName + ",'审核完成' " + colName + "mc,'cwtg' " + colName + "bj,8 " + colName + "xh from dual "
				        + ") ";
			}
		}
		else if(czlx.equals(CZLX_ZCBD)){//资产变动
			if(StateManager.BDXM_XMBD.equals(lb) || StateManager.BDXM_CFDDBD.equals(lb) || StateManager.BDXM_BFDB.equals(lb)){
				zjsql = " ("
						+ " select '" + ZCBD_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
							+ " union all "
						+ " select '" + ZCBD_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
							+ " union all "
						+ " select '" + ZCBD_GK_TG + "' " + colName + ",'归口审核通过' " + colName + "mc,'gktg' " + colName + "bj,3 " + colName + "xh from dual "
							+ " union all "
						+ " select '" + ZCBD_GK_WTG + "' " + colName + ",'归口审核未通过' " + colName + "mc,'gkwtg' " + colName + "bj,4 " + colName + "xh from dual "
						+ ") ";
			}
			else if(StateManager.BDXM_DWNDB.equals(lb)){
				zjsql = " ("
						+ " select '" + ZCBD_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCBD_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCBD_LYR_TG + "' " + colName + ",'领用人通过' " + colName + "mc,'lyrtg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_LYR_WTG + "' " + colName + ",'领用人未通过' " + colName + "mc,'lyrwtg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GK_TG + "' " + colName + ",'审核通过' " + colName + "mc,'gktg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCBD_GLY_WTG + "' " + colName + ",'管理员退回' " + colName + "mc,'glywtg' " + colName + "bj,6 " + colName + "xh from dual "
				        + ") ";
			}
			else if(StateManager.BDXM_DWJDB.equals(lb)){
				zjsql = " ("
						+ " select '" + ZCBD_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCBD_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_LYR_TG + "' " + colName + ",'领用人通过' " + colName + "mc,'lyrtg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_LYR_WTG + "' " + colName + ",'领用人退回' " + colName + "mc,'lyrwtg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GLY_TG + "' " + colName + ",'管理员通过' " + colName + "mc,'glytg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GLY_WTG + "' " + colName + ",'管理员退回' " + colName + "mc,'glywtg' " + colName + "bj,6 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_DC_TG + "' " + colName + ",'调出单位领导通过' " + colName + "mc,'dcldtg' " + colName + "bj,7 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_DC_WTG + "' " + colName + ",'调出单位领导退回' " + colName + "mc,'dcldwtg' " + colName + "bj,8 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_DR_TG + "' " + colName + ",'调入单位领导通过' " + colName + "mc,'drldtg' " + colName + "bj,9 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_DR_WTG + "' " + colName + ",'调入单位领导退回' " + colName + "mc,'drldwtg' " + colName + "bj,10 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GK_TG + "' " + colName + ",'归口审核通过' " + colName + "mc,'gktg' " + colName + "bj,11 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GK_WTG + "' " + colName + ",'归口审核退回' " + colName + "mc,'gkwtg' " + colName + "bj,12 " + colName + "xh from dual "
				        + ") ";
			}
			else if(StateManager.BDXM_SYRBD.equals(lb)){
				zjsql = " ("
						+ " select '" + ZCBD_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCBD_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GK_TG + "' " + colName + ",'审核通过' " + colName + "mc,'gktg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_LYR_WTG + "' " + colName + ",'领用人退回' " + colName + "mc,'lyrwtg' " + colName + "bj,4 " + colName + "xh from dual "
				        + ") ";
			}
			else if(StateManager.BDXM_DJBD.equals(lb) || StateManager.BDXM_BFBF.equals(lb) || StateManager.BDXM_FJZJ.equals(lb) || StateManager.BDXM_FJCZ.equals(lb)){
				zjsql = " ("
						+ " select '" + ZCBD_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCBD_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GK_TG + "' " + colName + ",'归口审核通过' " + colName + "mc,'gktg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GK_WTG + "' " + colName + ",'归口审核退回' " + colName + "mc,'gkwtg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_CW_TG + "' " + colName + ",'财务审核通过' " + colName + "mc,'cwtg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_CW_WTG + "' " + colName + ",'财务审核未通过' " + colName + "mc,'cwwtg' " + colName + "bj,6 " + colName + "xh from dual "
				        + ") ";
			}
			else if("bdxm".equals(lb)){
				zjsql = " ("
						+ " select '" + BDXM_XMBD + "' " + colName + ",'项目变动' " + colName + "mc,'xmbd' " + colName + "bj,0 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + BDXM_DJBD + "' " + colName + ",'单价变动' " + colName + "mc,'djbd' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + BDXM_FJZJ + "' " + colName + ",'附件增加' " + colName + "mc,'fjzj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
//					    + " select '" + BDXM_FJCZ + "' " + colName + ",'附件处置' " + colName + "mc,'fjcz' " + colName + "bj,3 " + colName + "xh from dual "
//								+ " union all "
					    + " select '" + BDXM_ZCDB + "' " + colName + ",'资产调拨' " + colName + "mc,'zcdb' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
//					    + " select '" + BDXM_BFDB + "' " + colName + ",'部分调拨' " + colName + "mc,'bfdb' " + colName + "bj,5 " + colName + "xh from dual "
//								+ " union all "
//					    + " select '" + BDXM_BFBF + "' " + colName + ",'部分报废' " + colName + "mc,'bfbf' " + colName + "bj,6 " + colName + "xh from dual "
//								+ " union all "
					    + " select '" + BDXM_DWNDB + "' " + colName + ",'单位内调拨' " + colName + "mc,'dwn' " + colName + "bj,7 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + BDXM_DWJDB + "' " + colName + ",'单位间调拨' " + colName + "mc,'dwj' " + colName + "bj,8 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + BDXM_SYRBD + "' " + colName + ",'使用人变动' " + colName + "mc,'syrbd' " + colName + "bj,9 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + BDXM_CFDDBD + "' " + colName + ",'存放地点变动' " + colName + "mc,'cfddbd' " + colName + "bj,10 " + colName + "xh from dual "
				        + ") ";
			}
			else{
				zjsql = " ("
						+ " select '" + ZCBD_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCBD_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GLY_WTG + "' " + colName + ",'管理员退回' " + colName + "mc,'glywtg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GLY_TG + "' " + colName + ",'管理员通过' " + colName + "mc,'glytg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_LYR_WTG + "' " + colName + ",'领用人未通过' " + colName + "mc,'lyrwtg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_LYR_TG + "' " + colName + ",'领用人通过' " + colName + "mc,'lyrtg' " + colName + "bj,6 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_DC_WTG + "' " + colName + ",'调出单位领导未通过' " + colName + "mc,'dcldwtg' " + colName + "bj,7 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_DC_TG + "' " + colName + ",'调出单位领导通过' " + colName + "mc,'dcldtg' " + colName + "bj,8 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_DR_WTG + "' " + colName + ",'调入单位领导未通过' " + colName + "mc,'drldwtg' " + colName + "bj,9 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_DR_TG + "' " + colName + ",'调入单位领导通过' " + colName + "mc,'drldtg' " + colName + "bj,10 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GK_WTG + "' " + colName + ",'归口审核未通过' " + colName + "mc,'gkwtg' " + colName + "bj,11 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_GK_TG + "' " + colName + ",'归口审核通过' " + colName + "mc,'gktg' " + colName + "bj,12 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_CW_WTG + "' " + colName + ",'财务审核未通过' " + colName + "mc,'cwwtg' " + colName + "bj,13 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCBD_CW_TG + "' " + colName + ",'财务审核通过' " + colName + "mc,'cwtg' " + colName + "bj,14 " + colName + "xh from dual "
				        + ") ";
			}
		}
		else if(czlx.equals(CZLX_ZCCZ)){//资产处置
			if("1".equals(lb)){
				zjsql = " ("
						+ " select '" + ZCCZ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCCZ_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_GLY_WTG + "' " + colName + ",'管理员未通过' " + colName + "mc,'glywtg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_GLY_TG + "' " + colName + ",'管理员通过' " + colName + "mc,'glytg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_GK_WTG + "' " + colName + ",'归口未通过' " + colName + "mc,'gkwtg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_GK_TG + "' " + colName + ",'归口通过' " + colName + "mc,'gktg' " + colName + "bj,6 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_CW_WTG + "' " + colName + ",'财务未通过' " + colName + "mc,'cwwtg' " + colName + "bj,7 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_CW_TG + "' " + colName + ",'财务通过' " + colName + "mc,'cwtg' " + colName + "bj,8 " + colName + "xh from dual "
				        + ") ";
			}
			else if("2".equals(lb)){
				zjsql = " ("
						+ " select '" + ZCCZ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + ZCCZ_TJ + "' " + colName + ",'等待管理员审核' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_GLY_WTG + "' " + colName + ",'管理员退回' " + colName + "mc,'glywtg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_GLY_TG + "' " + colName + ",'等待归口审核' " + colName + "mc,'glytg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_GK_WTG + "' " + colName + ",'归口退回' " + colName + "mc,'gkwtg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_GK_TG + "' " + colName + ",'等待财务审核' " + colName + "mc,'gktg' " + colName + "bj,6 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_CW_WTG + "' " + colName + ",'财务退回' " + colName + "mc,'cwwtg' " + colName + "bj,7 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + ZCCZ_CW_TG + "' " + colName + ",'审核完成' " + colName + "mc,'cwtg' " + colName + "bj,8 " + colName + "xh from dual "
				        + ") ";
			}
		}
		else if(czlx.equals(CZLX_ZCWX_WXSQ)){//维修申请
			if("1".equals(lb)){
				zjsql = " ("
						+ " select '" + WXSQ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + WXSQ_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXSQ_WTG + "' " + colName + ",'未通过' " + colName + "mc,'cwshth' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXSQ_TG + "' " + colName + ",'通过' " + colName + "mc,'cwshtg' " + colName + "bj,4 " + colName + "xh from dual "
				        + ") ";
			}
		}
		else if(czlx.equals(CZLX_ZCWX_BGDJ)){//维修报告登记
			if("1".equals(lb)){
				zjsql = " ("
						+ " select '" + WXBGDJ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + WXBGDJ_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXBGDJ_LD_WTG + "' " + colName + ",'院系领导未通过' " + colName + "mc,'ldwtg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXBGDJ_LD_TG + "' " + colName + ",'院系领导通过' " + colName + "mc,'ldtg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXBGDJ_CW_WTG + "' " + colName + ",'财务未通过' " + colName + "mc,'cwwtg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXBGDJ_CW_TG + "' " + colName + ",'财务通过' " + colName + "mc,'cwtg' " + colName + "bj,6 " + colName + "xh from dual "
				        + ") ";
			}
			else if("2".equals(lb)){
				zjsql = " ("
						+ " select '" + WXBGDJ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
								+ " union all "
						+ " select '" + WXBGDJ_TJ + "' " + colName + ",'等待院系领导审核' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXBGDJ_LD_WTG + "' " + colName + ",'院系领导退回' " + colName + "mc,'ldwtg' " + colName + "bj,3 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXBGDJ_LD_TG + "' " + colName + ",'等待财务审核' " + colName + "mc,'ldtg' " + colName + "bj,4 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXBGDJ_CW_WTG + "' " + colName + ",'财务退回' " + colName + "mc,'cwwtg' " + colName + "bj,5 " + colName + "xh from dual "
								+ " union all "
					    + " select '" + WXBGDJ_CW_TG + "' " + colName + ",'审核完成' " + colName + "mc,'cwtg' " + colName + "bj,6 " + colName + "xh from dual "
				        + ") ";
			}
		}
		else if(czlx.equals(CZLX_ZJ)){//折旧
			zjsql = " ("
					+ " select '" + ZJWTJ + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
							+ " union all "
					+ " select '" + ZJYTJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
							+ " union all "
				    + " select '" + ZJTG + "' " + colName + ",'通过' " + colName + "mc,'tg' " + colName + "bj,3 " + colName + "xh from dual "
							+ " union all "
				    + " select '" + ZJTH + "' " + colName + ",'退回' " + colName + "mc,'wtg' " + colName + "bj,4 " + colName + "xh from dual "
					+ ") ";
		}
		else if(czlx.equals(CZLX_ZJJT)){//折旧
			zjsql = " ("
					+ " select '" + ZJJT_WTJ + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
							+ " union all "
					+ " select '" + ZJJT_YTJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
							+ " union all "
				    + " select '" + ZJJT_TG + "' " + colName + ",'通过' " + colName + "mc,'tg' " + colName + "bj,3 " + colName + "xh from dual "
							+ " union all "
				    + " select '" + ZJJT_TH + "' " + colName + ",'退回' " + colName + "mc,'wtg' " + colName + "bj,4 " + colName + "xh from dual "
					+ ") ";
		}
		else if(czlx.equals(CZLX_ZJGZL)){
			zjsql = " ("
					+ " select '" + GZL_WFH + "' " + colName + ",'未复核' " + colName + "mc,'wfh' " + colName + "bj,1 " + colName + "xh from dual "
							+ " union all "
					+ " select '" + GZL_YFH + "' " + colName + ",'已复核' " + colName + "mc,'yfh' " + colName + "bj,2 " + colName + "xh from dual "
					+ ") ";
		}
		else if(czlx.equals(CZLX_ZJTZ)){//折旧调整
			zjsql = " ("
					+ " select '" + ZJTZ_WTJ + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
							+ " union all "
					+ " select '" + ZJTZ_YTJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
							+ " union all "
				    + " select '" + ZJTZ_TG + "' " + colName + ",'通过' " + colName + "mc,'tg' " + colName + "bj,3 " + colName + "xh from dual "
							+ " union all "
				    + " select '" + ZJTZ_TH + "' " + colName + ",'退回' " + colName + "mc,'wtg' " + colName + "bj,4 " + colName + "xh from dual "
					+ ") ";
		}
		else if(czlx.equals(CZLX_ZCXZ)){//资产闲置
			if("1".equals(lb)){
				zjsql = " ("
						+ " select '" + LYSQ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
						+ " union all "
						+ " select '" + LYSQ_TJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + LYSQ_GLY_TG + "' " + colName + ",'资产主管审核通过' " + colName + "mc,'glytg' " + colName + "bj,3 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + LYSQ_GLY_TH + "' " + colName + ",'资产主管审核退回' " + colName + "mc,'glyth' " + colName + "bj,4 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + LYSQ_GK_TG + "' " + colName + ",'归口审核通过' " + colName + "mc,'gktg' " + colName + "bj,5 " + colName + "xh from dual "
						+ " union all "
					    + " select '" + LYSQ_GK_TH + "' " + colName + ",'归口审核退回' " + colName + "mc,'gkth' " + colName + "bj,6 " + colName + "xh from dual "
				        + ") ";
			}
//			else if("2".equals(lb)){
//				zjsql = " ("
//						+ " select '" + ZCJZ_LR + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
//								+ " union all "
//						+ " select '" + ZCJZ_TJ + "' " + colName + ",'等待管理员审核' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
//								+ " union all "
//					    + " select '" + ZCJZ_GLY_WTG + "' " + colName + ",'管理员退回' " + colName + "mc,'glywtg' " + colName + "bj,3 " + colName + "xh from dual "
//								+ " union all "
//					    + " select '" + ZCJZ_GLY_TG + "' " + colName + ",'等待归口审核' " + colName + "mc,'glytg' " + colName + "bj,4 " + colName + "xh from dual "
//								+ " union all "
//					    + " select '" + ZCJZ_GK_WTG + "' " + colName + ",'归口退回' " + colName + "mc,'gkwtg' " + colName + "bj,5 " + colName + "xh from dual "
//								+ " union all "
//					    + " select '" + ZCJZ_GK_TG + "' " + colName + ",'等待财务审核' " + colName + "mc,'gktg' " + colName + "bj,6 " + colName + "xh from dual "
//								+ " union all "
//					    + " select '" + ZCJZ_CW_WTG + "' " + colName + ",'财务退回' " + colName + "mc,'cwwtg' " + colName + "bj,7 " + colName + "xh from dual "
//								+ " union all "
//					    + " select '" + ZCJZ_CW_TG + "' " + colName + ",'审核完成' " + colName + "mc,'cwtg' " + colName + "bj,8 " + colName + "xh from dual "
//				        + ") ";
//			}
		}
		else if(czlx.equals(CZLX_ZCJY_SQ)){
			zjsql = " ("
					+ " select '" + ZCJY_SQ_WTJ + "' " + colName + ",'未提交' " + colName + "mc,'wtj' " + colName + "bj,1 " + colName + "xh from dual "
					+ " union all "
					+ " select '" + ZCJY_SQ_YTJ + "' " + colName + ",'已提交' " + colName + "mc,'ytj' " + colName + "bj,2 " + colName + "xh from dual "
					+ " union all "
				    + " select '" + ZCJY_SQ_TG + "' " + colName + ",'审核通过' " + colName + "mc,'shtg' " + colName + "bj,3 " + colName + "xh from dual "
					+ " union all "
				    + " select '" + ZCJY_SQ_TH + "' " + colName + ",'审核退回' " + colName + "mc,'shth' " + colName + "bj,4 " + colName + "xh from dual "
			        + ") ";
		}
		else if(czlx.equals(CZLX_ZCJY_GH)){
			zjsql = " ("
					+ " select '" + ZCJY_GH_WGH + "' " + colName + ",'未归还' " + colName + "mc,'wgh' " + colName + "bj,1 " + colName + "xh from dual "
					+ " union all "
					+ " select '" + ZCJY_GH_YGH + "' " + colName + ",'已归还' " + colName + "mc,'ygh' " + colName + "bj,2 " + colName + "xh from dual "
			        + ") ";
		}
		return zjsql;
	}
	/**
	 * 获取所有状态的sql
	 * @param czlx     操作类型
	 * @param colName  状态的列名     状态名称的列名：colName+mc  状态标记的列名：colName+bj  状态排序序号的列名：colName+xh  是否默认状态的列名：mrzt
	 * @param lb	         类别   1：按最近审核角色显示  2：按审核流程角色显示
	 * @param mrzt     默认显示的状态   如果有多个状态，需要以英文的逗号分隔
	 * @return
	 */
	public static String getAllZtSqlByMr(String czlx, String colName, String lb, String mrzt){
		StringBuffer stru = new StringBuffer();
		String casetext = "";
		if(Validate.isNull(mrzt)){
			casetext = " when '' then '1' ";//因为不会有空的，所以这句话只是为了占个位置，没有实际意义
		}
		else{
			String[] zt = mrzt.split(",");
			for(int i = 0; i < zt.length; i++){
				casetext += " when '" + zt[i] + "' then '1' ";
			}
		}
		stru.append("select s.*,(case " + colName + " " + casetext + " else '0' end) mrzt from ");
		stru.append(StateManager.getAllZtSql(czlx,colName,lb) + " s ");
		stru.append("order by s." + colName + "xh ");
		return stru.toString();
	}
	
	/**
	 * 获取所有状态的sql
	 * @param czlx     操作类型
	 * @param colName  状态的列名     状态名称的列名：colName+mc  状态标记的列名：colName+bj  状态排序序号的列名：colName+xh  是否默认状态的列名：mrzt
	 * @param lb	    类别   1：按最近审核角色显示  2：按审核流程角色显示  变动时，这里是变动类型
	 * @param mrzt     默认显示的状态   如果有多个状态，需要以英文的逗号分隔
	 * @return
	 */
	public static List getAllZtSqls(String czlx, String colName, String lb, String mrzt){
		StringBuffer stru = new StringBuffer();
		String casetext = "";
		if(Validate.isNull(mrzt)){
			casetext = " when '' then '1' ";//因为不会有空的，所以这句话只是为了占个位置，没有实际意义
		}
		else{
			String[] zt = mrzt.split(",");
			for(int i = 0; i < zt.length; i++){
				casetext += " when '" + zt[i] + "' then '1' ";
			}
		}
		stru.append("select s.*,(case " + colName + " " + casetext + " else '0' end) mrzt from ");
		stru.append(StateManager.getAllZtSql(czlx,colName,lb) + " s ");
		stru.append("order by s." + colName + "xh ");
		return dao.queryForList(stru.toString());
	}
}
