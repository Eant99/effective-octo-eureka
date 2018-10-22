package com.googosoft.constant;

public class MenuFlag {
	/**
	 * 默认角色编号
	 */
	public static String MRJS = "00";
	/**
	 * 资产管理员角色编号
	 */
	public static String ZCGLY = "01";
	/**
	 * 归口管理员角色编号
	 */
	public static String GKGLY = "02";
	/**
	 * 财务管理员角色编号
	 */
	public static String CWGLY = "03";
	
	
	public static String SHCD = "05";//该模块下的才会被放到审核里边
	/**
	 * 领用人建账
	 */
	public static String ZCJZ_LYR = "010101";
	/**
	 * 管理员建账
	 */
	public static String ZCJZ_GLY = "010201";
	/**
	 * 单位内调拨申请
	 */
	public static String ZCBD_DWNDB = "010301";//020101
	/**
	 * 单位间调拨申请
	 */
	public static String ZCBD_DWJDB = "010302";//020102
	/**
	 * 使用人变动申请
	 */
	public static String ZCBD_SYRBD = "010303";//020103
	/**
	 * 存放地点变动申请
	 */
	public static String ZCBD_CFDDBD = "010304";//020104
	/**
	 * 使用人确认
	 */
	public static String ZCBD_ZCDB_SYRQR = "010501";//050201  020105
	/**
	 * 项目变动
	 */
	public static String ZCBD_XMBD = "010401";//020201
	/**
	 * 部分调拨
	 */
	public static String ZCBD_BFDB = "010305";//020204
	/**
	 * 单价变动
	 */
	public static String ZCBD_DJBD = "010402";//020301
	/**
	 * 部分报废
	 */
	public static String ZCBD_BFBF = "010604";//020302
	/**
	 * 附件增加申请
	 */
	public static String ZCBD_FJZJ = "010403";//020303
	/**
	 * 附件处置申请
	 */
	public static String ZCBD_FJCZ = "010605";//020304
	/**
	 * 资产处置
	 */
	public static String ZCCZ_CZSQ = "010601";//030101
	/**
	 * 资产处置管理员汇总
	 */
	public static String ZCCZ_GLYHZ = "010602";//030201
	/**
	 * 资产处置
	 */
	public static String ZCCZ_ZCCZ = "010603";//030202
	/**
	 * 生成贵重仪器表
	 */
	public static String JMYQ_SCGZYQB = "010801";//040101
	/**
	 * 年使用信息维护模块编号
	 */
	public static String JMYQ_NSYWH = "010802";//040201
	/**
	 * 资产借用-借用申请
	 */
	public static String ZCJY_SQ = "010901";
	/**
	 * 资产借用-借用归还
	 */
	public static String ZCJY_GH = "010902";
	/**
	 * 资产建账管理员审核
	 */
	public static String ZCJZ_GLYSH = "050101";
	/**
	 * 资产建账归口审核
	 */
	public static String ZCJZ_GKSH = "050102";
	/**
	 * 资产建账财务审核
	 */
	public static String ZCJZ_CWSH = "050103";
	/**
	 * 资产调拨管理员审核
	 */
	public static String ZCBD_ZCDB_GLYSH = "050202";
	/**
	 * 资产调拨调出单位领导审核
	 */
	public static String ZCBD_ZCDB_DCDWLD = "050203";
	/**
	 * 资产调拨调入单位领导审核
	 */
	public static String ZCBD_ZCDB_DRDWLD = "050204";
	/**
	 * 资产调拨归口审核
	 */
	public static String ZCBD_ZCDB_GKSH = "050205";
	/**
	 * 项目变动归口审核
	 */
	public static String ZCBD_XMBD_GKSH = "050301";
	/**
	 * 部分调拨归口审核
	 */
	public static String ZCBD_BFDB_GKSH = "050206";//050302
	/**
	 * 资产价值变动归口审核
	 */
	public static String ZCBD_JZBD_GKSH = "050302";//050401
	/**
	 * 资产价值变动财务审核
	 */
	public static String ZCBD_JZBD_CWSH = "050303";//050402
	/**
	 * 资产处置管理员审核
	 */
	public static String ZCCZ_GLYSH = "050501";
	/**
	 * 资产处置归口审核
	 */
	public static String ZCCZ_GKSH = "050502";
	/**
	 * 资产处置财务审核
	 */
	public static String ZCCZ_CWSH = "050503";
	/**
	 * 资产变动-部分报废-归口审核
	 */
	public static String ZCBD_BFBF_GKSH = "050504";
	/**
	 * 资产变动-部分报废-财务审核
	 */
	public static String ZCBD_BFBF_CWSH = "050505";
	/**
	 * 附件处置归口审核
	 */
	public static String ZCBD_FJCZ_GKSH = "050506";
	/**
	 * 附件处置财务审核
	 */
	public static String ZCBD_FJCZ_CWSH = "050507";
	/**
	 * 资产清查-自查审核
	 */
	public static String ZCQC_ZCSH = "050601";
	/**
	 * 业务审核
	 */
	public static String YWSH = "050701";
	/**
	 * 资产闲置-主管审核
	 */
	public static String ZCXZ_ZGSH = "050801";
	
	public static String WSBX_JKSH = "111702";
	/**
	 * 资产闲置-归口审核
	 */
	public static String ZCXZ_GKSH = "050802";
	/**
	 * 资产闲置领用-主管审核
	 */
	public static String ZCXZLY_ZGSH = "050901";
	/**
	 * 资产闲置领用-归口审核
	 */
	public static String ZCXZLY_GKSH = "050902";
	/**
	 * 审核记录查询
	 */
	public static String SHJLCX = "051001";
	/**
	 * 维修申请审核
	 */
	public static String ZCWX_WXSQSH = "051101";
	/**
	 * 维修报告审核
	 */
	public static String ZCWX_WXBG_SH = "051102";
	/**
	 * 维修报告财务审核
	 */
	public static String ZCWX_WXBG_CWSH = "051103";
	/**
	 * 经费追加单位领导审核
	 */
	public static String ZCWX_JFZJ_DWLDSH = "051104";
	/**
	 * 经费追加财务审核
	 */
	public static String ZCWX_JFZJ_CWSH = "051105";
	/**
	 * 精密仪器年使用信息审核
	 */
	public static String JMYQ_NSYSH = "051201";
	/**
	 * 资产借用-借用申请审核
	 */
	public static String ZCJY_SQSH = "051301";
	
	/**
	 * 查询--公开查询--本单位全部资产--全部资产
	 */
	public static String CX_GKCX_BDWQBZC_QBZC = "0201";
	/**
	 * 查询--公开查询--本单位全部资产--最近入库资产--今日入库
	 */
	public static String CX_GKCX_BDWQBZC_ZJRK_JRRK = "020201";
	/**
	 * 查询--公开查询--本单位全部资产--最近入库资产--本周入库
	 */
	public static String CX_GKCX_BDWQBZC_ZJRK_BZRK = "020202";
	/**
	 * 查询--公开查询--本单位全部资产--最近入库资产--本月入库
	 */
	public static String CX_GKCX_BDWQBZC_ZJRK_BYRK = "020203";
	/**
	 * 查询--公开查询--本单位全部资产--最近入库资产--本年入库
	 */
	public static String CX_GKCX_BDWQBZC_ZJRK_BNRK = "020204";
	/**
	 * 查询--公开查询--本单位全部资产--检查--单位与人员分离的资产
	 */
	public static String CX_GKCX_BDWQBZC_JC_DWRYFL = "020701";
	/**
	 * 查询--公开查询--我的全部资产
	 */
	public static String CX_GKCX_WDQBZC = "060101";
	/**
	 * 查询--验收单查询--验收单查询
	 */
	public static String CX_YSDCX_YSDCX = "060201";
	/**
	 * 查询--资产查询--资产信息查询
	 */
	public static String CX_ZCCX_ZCXX = "060301";
	/**
	 * 查询--资产查询--闲置资产查询
	 */
	public static String CX_ZCCX_XZZC = "060302";
	/**
	 * 查询--资产查询--已处置资产查询
	 */
	public static String CX_ZCCX_CZZC = "060303";
	/**
	 * 查询--资产查询--资产信息回滚
	 */
	public static String CX_ZCCX_ZCHG = "060304";
	/**
	 * 查询--资产变动查询--资产变动信息查询
	 */
	public static String ZCCX_BDCX_BDXX = "060401";
	
	 /* 查询--资产处置查询--部分报废查询
	 */
	public static String ZCCX_ZCCZ_BFBF = "060803";
	/**
	 * 查询--资产处置查询--部分报废查询
	 */
	public static String ZCCX_ZCCZ_FJCZ = "060804";
	/**
	 * 查询--资产分类查询--使用单位查询
	 */
	public static String ZCCX_ZCFL_SYDW = "060501";
	/**
	 * 查询--资产分类查询--存放地点查询
	 */
	public static String ZCCX_ZCFL_BZXX = "060502";
	/**
	 * 查询--资产分类查询--使用人查询
	 */
	public static String ZCCX_ZCFL_SYR = "060503";
	/**
	 * 查询--资产分类查询--资产类别查询
	 */
	public static String ZCCX_ZCFL_ZCLB = "060504";
	/**
	 * 查询--实验室资产查询--实验室资产查询
	 */
	public static String ZCCX_SYSZC_SYS = "060601";
	/**
	 * 查询--附件查询--附件查询
	 */
	public static String ZCCX_FJZC_FJ = "060701";
	/**
	 * 查询--资产处置查询--资产处置查询
	 */
	public static String ZCCX_ZCCZ_ZCCZ = "060801";
	/**
	 * 查询--处置申请查询--处置申请查询
	 */
	public static String ZCCX_ZCCZ_CZSQ = "060802";
	/**
	 * 资产自查
	 */
	public static String ZCQC_ZCZC = "100102";
	/**
	 * 维修申请
	 */
	public static String ZCWX_WXSQ = "110101";
	/**
	 * 维修报告
	 */
	public static String ZCWX_WXBG = "110201";
	/**
	 * 维修经费追加
	 */
	public static String ZCWX_JFZJ = "110302";
	/**
	 * 设备维修--查询统计--经费追加
	 */
	public static String ZCWX_CXTJ_JFZJ = "110503";
	/**
	 * 闲置调剂申请
	 */
	public static String ZCXZ_XZTJ = "010701";//120101
	/**
	 * 闲置资产公示
	 */
	public static String ZCXZ_XZZCGS = "010702";//120301
	/**
	 * 闲置调剂领用申请
	 */
	public static String ZCXZ_XZTJLY = "010703";//120401
	/**
	 * 资产折旧--月度折旧计提
	 */
	public static String ZCZJ_YDJT = "210401";//月度计提折旧 
	/**
	 * 资产折旧--月度折旧审核
	 */
	public static String ZCZJ_YDZJSH = "210402";
	
	/**
	 * 业务审核模块只展示业务审核，其他模块不单独展示
	 */
	public static String MKBH_SH = "'0501','0502','0503','0504','0505','0506','0508','0509','1202','1205'";
	
	public static String MKBH_RCYW = "'010101', '020101', '020102', '120401', '110101', '030101'";
}
