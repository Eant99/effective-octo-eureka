package com.googosoft.constant;

import java.awt.Toolkit;

import javax.servlet.http.HttpSession;

import com.googosoft.util.DateUtil;
import com.googosoft.util.Validate;

public class Constant {
	/**
	 * 操作类型：查看
	 */
	public static final String VIEW = "view";
	/**
	 * 操作类型：更新
	 */
	public static final String UPDATE = "update";
	/**
	 * 操作类型：添加
	 */
	public static final String ADD = "add";
	/**
	 * 操作类型：审核
	 */
	public static final String CHECK = "check";
	/**
	 * 审核结果：通过
	 */
	public static final String PASS = "pass";
	/**
	 * 审核结果：退回
	 */
	public static final String REJECT = "reject";
	/**
	 * 默认密码
	 */
	public static String MRMM = "123456";
	
	/**
	 * 归口角色的角色编号 02
	 */
	public static String GKJS = "02";
	
	/***************************添加字典时，请按照编号顺序放置，现在有几个都是重复的************************/
	/********************会议申请状态**************************/
	/***会议申请状态:已退回*/
	public final static String HYSQ_YTH = "0";
	/***会议申请状态:未提交*/
	public final static String HYSQ_WTJ = "1";
	/***会议申请状态:待审核*/
	public final static String HYSQ_DSH = "2";
	/***会议申请状态:已通过*/
	public final static String HYSQ_YTG = "3";
	/***会议申请状态:已占用*/
	public final static String HYSQ_YZY = "4";
	/***会议申请状态:已撤销*/
	public final static String HYSQ_YCX = "5";

	
	/**
	 * 获取会议申请状态标识sql
	 * @param colName
	 * @return
	 */
	public static String getHysqzt(String colName){
		return "(case "+colName+" when '"+HYSQ_YTH+"' then '已退回'"
					+" when '"+HYSQ_WTJ+"' then '未提交'" 
					+" when '"+HYSQ_DSH+"' then '待审核'" 
					+" when '"+HYSQ_YTG+"' then '已通过'" 
					+" when '"+HYSQ_YZY+"' then '已占用'" 
					+" when '"+HYSQ_YCX+"' then '已撤销' end)";
	}
	/**
	 * 所学专业、所属学科
	 */
	public static float TWO = 2000;//
	public static float THREE = 3000;//
	public static float FIVE = 50000;//
	public static float TEN = 10000;//
	public static float WQYS = 5000;//5000以上
	public static String CWYG = "11";//财务处员工
	public static String KJC = "109";//科技处
	public static String CWC = "110";//财务处
	public static String CWRY = "05";//财务人员
	public static String CWFZR = "06";//财务负责人
	public static String CWFGLD = "07";//财务分管领导
	public static String KYCFZR = "10";//科研处负责人
	public static String BGSFZR = "09";//科研处负责人
	public static String SXZY = "01";//所学专业
	public static String SSXK = "01";//所属学科
	public static String GB = "02";//国别码
	public static String XZ = "03";//现状
	public static String JFKM = "04";//经费来源
	public static String SYFX = "05";//使用方向
	public static String DLH16_17 = "06";//十六大类（带有无形资产）
	public static String DLH6 = "07";//六大类
	public static String DLH10 = "08";//十大类
	public static String SYSLB = "11";//实验室类别
	public static String JXNR = "16";//进修内容
	public static String WYYZ = "18";//外语语种
	public static String WYSP = "19";//外语水平
	public static String SEX = "20";//性别
	public static String CG = "23";//成果
	public static String LWJB = "24";//论文级别
	public static String ZZJB = "25";//著作级别
	public static String ZYGZ = "26";//主要工作
	public static String WHCD = "27";//文化程度
	public static String ZYZC = "28";//专业职称
	public static String ZCLY = "30";//资产来源
	public static String DWXZ = "32";//单位性质
	public static String SYZK = "33";//使用状况
	public static String PYYY = "34";//盘盈原因
	public static String PKYY = "35";//盘亏原因
	public static String WBZL = "36";//外币种类
	public static String SYXZ = "37";//车辆用途
	public static String JLDW = "38";//计量单位
	public static String CZFS = "41";//处置方式
	public static String HXZ = "41";//资产处置后的现状
	public static String JZLX = "44";//记账类型
	public static String XKML = "45";//学科
	public static String XKLB = "46";//学科类别
	public static String SYSJB = "47";//实验室级别
	public static String SYSGS = "48";//实验室归属
	public static String XH = "49";//建筑结构
	public static String ZJ = "50";//建筑类型
	public static String WSZK = "51";//完损状况
	public static String TDDJ = "51";//完损状况
	public static String SYSLX = "56";//实验室类型
	public static String TD_RZKM = "61";//土地入账科目
	public static String FGXS = "63";//房管形式
	public static String ZJJT = "65";//折旧状态
	public static String SYQLX = "66";//土地的使用权类型
	public static String ZJLB = "70";//专家类别
	public static String JT_PPXZ = "72";//品牌性质
	public static String CLLX = "73";//车辆类型
	public static String CLBZ = "74";//车辆编制
	public static String JT_BZQK = "74";//交通编制情况
	public static String JT_SYXZ = "75";//车辆使用性质
	public static String RJ_LX = "77";//软件类型
	public static String RJ_SQXKLX = "78";//授权许可类型
	public static String DLH16_16 = "81";//十六大类（不带无形资产）
	public static String WX_XCFS = "82";//形成方式
	public static String CGZZXS = "90";//采购组织形式(植物)
	public static String CQXS = "91";//房屋产权形式
	public static String TD_QSXZ = "92";//土地权属性质
	public static String QSXZ = "92";//房屋、构筑物类代表权属性质
	public static String JZXS = "99";//价值类型
	public static String ZZZT = "102";//在职状态
	public static String ZDSYS = "100";//重点实验室
	public static String FWYT = "fwyt";//房屋用途
	public static String SHTG = "shtg";//审核通过
	public static String SHTH = "shth";//审核退回
	public static String XSLB = "103";//学生类别
	public static String QYDM = "104";//出生地区
	public static String MZ = "105";//民族
	public static String GJ = "106";//国籍
	public static String ZJLX = "111";//证件类型
	public static String HYZK = "108";//婚姻状况
	public static String ZZMM = "109";//政治面貌
	public static String JKZK = "110";//健康状况
	public static String ZW = "112";//职务
	public static String DWBB = "113";//单位办别
	public static String ZFFSDM = "10032";//支付方式
	public static String SHZTDM = "11033";//审核状态
	public static String ZFZT = "zfzt";//支付状态
	public static String SHZTDMGW = "shztdmgw";//公务审核状态
	public static String DJSHZT = "djshzt";//审核状态
	public static String LCSH = "lcsh";//审核状态
	public static String JKZT = "jkzt";//审核状态
	public static String bmysshzt = "bmysshzt";//部门预算审核状态
	public static String DMSX = "116";//代码属性
	public static String ZJLY = "zjly";
	public static String XZ1="xz";
	public static String BMYSBZ="bmysbz";//部门预算编制树

	public static String FLQX = "1";//分类权限的zl
	public static String DWQX = "2";//单位权限的zl
	public static String DMBZL = "00";//代码表的zl
	public static String DWJC_EJ = "2";//二级单位单位级次

	
	public static String XMDL = "250";//项目大类
	public static String XMLB = "251";//项目类别
	public static String XMSX = "252";//项目属性
	public static String XMFL = "xmfl";//项目分类
	public static String ZYFX = "zyfx";//项目分类
	
	public static String CCLX = "cclx";//出差类型
	public static String XMXZ = "xmxz";//项目性质
	public static String JFLX = "jflx";//经费类型
	public static String JTGJ = "jtgj";//交通工具
	public static String BXLB = "bxlb";//交通工具
	public static String YHMC = "yhmc";//银行名称
	public static String BXLBALL = "bxlball";//全部树
	
	public static String PYFS = "200";//培养方式
	public static String YJFX = "201";//研究方向
	public static String HDXLFS = "202";//获得学历方式
	public static String PYCC = "203";//培养层次
	public static String LDFS = "204";//连读方式
	public static String XSDQZT = "205";//学生当前状态
	

	public static String GZLB="GZLB";//工资类别
	public static String LYLX="LYLX";//工资类别
	public static String YSLX="YSLX";//预算类型
	public static String XMLX="XMLX";//项目类型
	public static String YSLY="ysly";//预算来源
	public static String ZCLX="zclx";//支出类型
	public static String ZY="zy";//摘要
	public static String PZZT="pzzt";//凭证状态
	public static String PZLY="pzly";//凭证来源
	public static String JCWJLX="jcwjlx";//文件类型
	
	public static String ZKMSXDM = "zkmsxdm";
	public static String XXZT="B9BA12A24DBE4EA89763AFDE76B8C61A";//学校账套 
	public static String ZTMC="学校账套";//学校账套
	
	public static String ZFFS = "zffs";
	public static String XMSHZT ="xmshzt";//项目审核状态
	public static String BXJB ="bxjb";//报销级别
	
	//获取科研库 ky_sys_dmb 里的 学科领域 zl='B01' 一级学科zl='B02'
	public static String XKLY ="B01";
	public static String YJXK ="B02";
	
	/**
	 * 模块表中的，模块类型
	 */
	public static String MKLX_SHQX = "5";
	
	
	
	/**
	 * 科目设置中的会计科目，功能科目，经济科目
	 */
	public static String ZCZL="114";
	public static String GNZL="117";
	public static String JJZL="118";
	public static String KMSX = "kmsx";
	
	/* 资产变动   */
	public static String BDBZ_ZJ = "1";//变动的是主件
	public static String BDBZ_FJ = "2";//变动的是附件
	
	/*模板大类号*/
	/**
	 * 房屋
	 */
	public static String DLH_FW = "01";
	/**
	 * 构筑物
	 */
	public static String DLH_GZW = "02";
	/**
	 * 土地
	 */
	public static String DLH_TD = "03";
	/**
	 * 植物
	 */
	public static String DLH_ZW = "04";
	/**
	 * 通用设备
	 */
	public static String DLH_TYSB = "05";
	/**
	 * 车辆
	 */
	public static String DLH_CL = "06";
	/**
	 * 文物陈列品
	 */
	public static String DLH_WWCLP = "07";
	/**
	 * 图书
	 */
	public static String DLH_TS = "08";
	/**
	 * 家具
	 */
	public static String DLH_JJ = "09";
	/**
	 * 动物
	 */
	public static String DLH_DW = "10";
	/**
	 * 软件
	 */
	public static String DLH_RJ = "11";
	/**
	 * 无形资产
	 */
	public static String DLH_WXZC = "12";
	
	/**
	 * 保留位
	 */
	public static String BLW = "00";
	
	/*默认值*/
	
	/**
	 * 默认年yyyy
	 * @return
	 */
	public static String MR_YEAR(){
		return DateUtil.getYear();
	}
	/**
	 * 默认月MM
	 * @return
	 */
	public static String MR_MONTH(){
		return DateUtil.getMonth();
	}
	/**
	 * 默认月dd
	 * @return
	 */
	public static String MR_DAY(){
		return DateUtil.getDate();
	}
	/**
	 * 默认当前日期yyyy-MM-dd
	 * @return
	 */
	public static String MR_DATE(){
		return DateUtil.getDay();
	}
	
	/*获取特定日期*/
	/**
	 * 获取本周一的日期
	 * @return
	 */
	public static String MR_MONDAY(){
		return DateUtil.getMonday();
	}
	/**
	 * 获取本周日的日期
	 * @return
	 */
	public static String MR_SUNDAY(){
		return DateUtil.getSunday();
	}
	/**
	 * 获取本月第一天的日期
	 * @return
	 */
	public static String MR_MONTHMINDAY(){
		return DateUtil.getMonthMinDay();
	}
	/**
	 * 获取本月最后一天的日期
	 * @return
	 */
	public static String MR_MONTHMAXDAY(){
		return DateUtil.getMonthMaxDay();
	}
	/**
	 * 获取本年第一天的日期
	 * @return
	 */
	public static String MR_YEARMINDAY(){
		return DateUtil.getYearMinDay();
	}
	/**
	 * 获取本年最后一天的日期
	 * @return
	 */
	public static String MR_YEARMAXDAY(){
		return DateUtil.getYearMaxDay();
	}
		
	public static String FILEPATHTWODIMENSIONCODE="uploadFiles/";//二维码路径
	/**
	 * 返回账套id
	 * @return ztid
	 */
	
	public static String getztid(HttpSession session){
		String ztid= session.getAttribute("ztid")+"";
		if(Validate.isNull(ztid)){
			ztid=XXZT;
		}
		return ztid;
	}
	/**
	 * 返回账套id
	 * @return ztid
	 */
	
	public static String getztmc(HttpSession session){
		String ztmc= session.getAttribute("ztmc")+"";
		if(Validate.isNull(ztmc)){
			ztmc=ZTMC;
		}
		return ztmc;
	}
	/**
	 * 获取屏幕宽度
	 * @return
	 */
	public static int getScreenWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	/**
	 * 获取屏幕高度
	 * @return
	 */
	public static int getScreenHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}
	
	/* 角色   */
	public static String ROLE_BZY = "13";//报账员
	public static String ROLE_YBBZY = "14";//院办报账员
	
	/* 项目大类   */
	public static String XMDL_KYJF = "2";//报账员
	/*经济科目*/
	public static String JJKM_GWJD = "30217";//公务接待
	
	public static String JJKM_JBGZ = "30101";//基本工资
	public static String JJKM_GWGZ = "3010101";//岗位工资
	public static String JJKM_XJGZ = "3010102";//薪级工资
	public static String JJKM_JTBT = "30102";//津贴补贴
	public static String JJKM_GFBT = "3010201";//购房补贴
	public static String JJKM_CNBT = "3010202";//采暖补贴
	public static String JJKM_WYBT = "3010203";//物业补贴
	public static String JJKM_GWBT = "3010204";//岗位补贴
	public static String JJKM_QTBT = "3010299";//其他补贴
	public static String JJKM_JXGZ = "30107";//绩效工资
	public static String JJKM_JCJXGZ = "3010701";//基础绩效
	public static String JJKM_JLJXGZ = "3010702";//奖励绩效
	
	public static String JJKM_YSF = "30202";//印刷费
	public static String JJKM_WXF = "30213";//维修（护）费
	public static String JJKM_CG = "";//采购
	
	/*凭证类型*/
	public static String PZLX_JZ = "01";//记
	public static String PZLX_ZZ = "02";//转
	public static String PZLX_FZ = "03";//付
	
	/*会计科目*/
	public static String KJKM_JYZC = "5001";//教育事业支出
	public static String KJKM_JYJBZC = "500101";//教育基本支出
	public static String KJKM_JYXMZC = "500102";//教育项目支出
	public static String KJKM_KYZC = "5002";//科研事业支出
	public static String KJKM_KYJBZC = "500201";//科研基本支出
	public static String KJKM_KYXMZC = "500202";//科研项目支出
	public static String KJKM_XZZC = "5003";//行政管理支出
	public static String KJKM_XZJBZC = "500301";//行政基本支出
	public static String KJKM_XZXMZC = "500302";//行政项目支出
	public static String KJKM_HQZC = "500401";//后勤保障支出
	public static String KJKM_HQJBZC = "500401";//后勤基本支出
	public static String KJKM_HQXMZC = "500402";//后勤项目支出
	public static String KJKM_LTZC = "5005";//离退支出
	public static String KJKM_LTJBZC = "500501";//离退基本支出
	public static String KJKM_QTZC = "5401";//其他支出
	public static String KJKM_QTJBZC = "540101";//其他基本支出
	public static String KJKM_QTXMZC = "540102";//其他项目支出

	public static String KJKM_YFZGXC = "2201";//应付职工薪酬
	public static String KJKM_YFZGXC_JY = "220101";//应付职工薪酬--教育
	public static String KJKM_YFZGXC_KY = "220102";//应付职工薪酬--科研
	public static String KJKM_YFZGXC_XZ = "220103";//应付职工薪酬--行政
	public static String KJKM_YFZGXC_HQ = "220104";//应付职工薪酬--后勤
	public static String KJKM_YFZGXC_LT = "220105";//应付职工薪酬--离退休
	public static String KJKM_YFZGXC_QT = "220106";//应付职工薪酬--其他
	
	public static String KJKM_LYEZHYKED = "1011";//零余额账户用款额度
	public static String KJKM_JH9199 = "100202";//建行9199
	
	
	
	/*角色*/
	public static String JSBH_PZSC = "15";//凭证删除
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String MR_ZCSJ = "10:00";
	public static final String MR_WCSJ = "14:00";
	public static final String MR_BZX_TS = "2";
	public static final String MR_QSNL = "20";//起始年龄
	public static final String MR_JSNL = "65";//结束年龄
	public static final String MR_NLJG = "5";//年龄间隔
	public static final String STU_QSNL = "17";//起始年龄
	public static final String STU_JSNL = "29";//结束年龄
	public static final String STU_NLJG = "1";//年龄间隔
	public static final String MR_HCPJX = "70";//好评、差评界限
	public static final String NLFWKS = "1";//年龄范围开始
	public static final String NLFWJS = "100";//年龄范围结束
	public static final String XB_BOY = "男";//
	public static final String XB_GIRL = "女";//
	public static final String YJS_XSLB= "研究生";//研究生
	public static final String DM_ZZMM= "109";//专业
	public static final String SF_YES= "是";//
    public static final String SF_NO = "否";//
    public static final String ZC = "28";//职称
	
    public static final String BOY = "1";//男
	public static final String GIRL = "2";//女
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}