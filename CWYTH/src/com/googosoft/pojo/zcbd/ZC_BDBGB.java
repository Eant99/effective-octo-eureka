package com.googosoft.pojo.zcbd;

import java.util.Date;

/**
* ZC_BDBGB 实体类
* 创建时间： 2016-11-07
*@author master
*/ 


public class ZC_BDBGB{
	/**
	 * 变动报告编号（年月加四位流水号）
	 */
	private String bdbgbh;
	/**
	 * 变动日期
	 */
	private Date bdrq;
	/**
	 * 变动人人员编号
	 */
	private String rybh;
	/**
	 * 变动人单位编号
	 */
	private String dwbh;
	/**
	 * 字(变)
	 */
	private String zi;
	/**
	 *号(变动报告编号) 
	 */
	private String hao;
	/**
	 * 变动后使用单位
	 */
	private String hdwbh;
	/**
	 * 变动后使用人
	 */
	private String hrybh;
	/**
	 * 变动后使用方向
	 */
	private String hsyfx;
	/**
	 * 变动后存放地点
	 */
	private String hcfdd;
	/**
	 * 变动后现状
	 */
	private String hxz;
	/**
	 * 变动后单价
	 */
	private Float bddj;
	/**
	 * 变动原因
	 */
	private String bdyy;
	/**
	 * 变动后套数量
	 */
	private String sh;
	/**
	 * 变动后层数、册数
	 */
	private Integer hcs;
	/**
	 * 变动制单人
	 */
	private String jdr;
	/**
	 * 建档日期
	 */
	private String jdrq;
	/**
	 * 财务审核人
	 */
	private String shr;
	/**
	 * 财务审核日期
	 */
	private String shrq;
	/**
	 * 财务审核意见
	 */
	private String shyj;
	/**
	 * 变动人姓名
	 */
	private String bdrxm;
	/**
	 * 单位名称
	 */
	private String dwmc;
	/**
	 * 凭证号
	 */
	private String pzh;
	/**
	 * 记账日期
	 */
	private String jzrq;
	/**
	 * 记账标识(未记账：1；已记账：0)
	 */
	private String jzbz;
	/**
	 * 归口单位
	 */
	private String gkdw;
	/**
	 * 归口审核人员
	 */
	private String gkshry;
	/**
	 * 归口意见
	 */
	private String gkyj;
	/**
	 * 归口日期
	 */
	private String gkrq;
	/**
	 * 归口人员
	 */
	private String gkry;
	/**
	 * 状态标识(录入：55，提交：00，归口审核通过：90，归口不通过：10，财务通过：99，财务不通过：91，领用人录入：55，领用人提交：00，管理员退回：10，领用人通过：60，领用人退回：61，调出单位领导通过：70，调出单位领导退回：71，调入单位领导通过：80，调入单位领导退回：81)
	 */
	private String ztbz;
	/**
	 * 变动标识(项目变动：0，单价变动：1，附件增加：2，附件处置：3，资产调拨：4)
	 */
	private String djbz;
	/**
	 * 变动后建筑面积
	 */
	private Float hmj;
	/**
	 * 变动后自然间数
	 */
	private Integer hzrjs;
	/**
	 * 变动后附属设施
	 */
	private String hfsss;
	/**
	 * 变动项目标志(资产调拨中的：：：单位内调拨：0，单位间调拨：1，价值变动：2，领用人变动：3，存放地点变动：4，部分报废：5)
	 */
	private String xmbz;
	/**
	 * 领用人
	 */
	private String lyr;
	/**
	 * 领用人审核日期
	 */
	private String lyrrq;
	/**
	 * 领用人审核意见
	 */
	private String lyryj;
	/**
	 * 管理员
	 */
	private String gly;
	/**
	 * 管理员审核日期
	 */
	private String glyshrq;
	/**
	 * 管理员审核意见
	 */
	private String glyshyj;
	/**
	 * 调出单位审核人
	 */
	private String dcdwry;
	/**
	 * 调出单位领导审核日期
	 */
	private String dcdwshrq;
	/**
	 * 调出单位领导审核意见
	 */
	private String dcdwshyj;
	/**
	 * 调入单位审核人
	 */
	private String drdwry;
	/**
	 * 调入单位领导审核日期
	 */
	private String drdwshrq;
	/**
	 * 调入单位领导审核意见
	 */
	private String drdwshyj;
	/**
	 * 变动后土地面积
	 */
	private String htdmj;
	/**
	 * 处置收益（部分报废）
	 */
	private Float czsy;
	
	/**
	 * 返回的错误信息
	 */
	private String errmsg;
	private String yqbh;
	
	/**   
	 * 获取：变动报告编号（年月加四位流水号）   
	 * @return bdbgbh 变动报告编号（年月加四位流水号）   
	 */
	public String getBdbgbh() {
		return bdbgbh;
	}
	/**   
	 * 获取：变动日期   
	 * @return bdrq 变动日期   
	 */
	public Date getBdrq() {
		return bdrq;
	}
	/**   
	 * 获取：变动人人员编号   
	 * @return rybh 变动人人员编号   
	 */
	public String getRybh() {
		return rybh;
	}
	/**   
	 * 获取：变动人单位编号   
	 * @return dwbh 变动人单位编号   
	 */
	public String getDwbh() {
		return dwbh;
	}
	/**   
	 * 获取：字(变)   
	 * @return zi 字(变)   
	 */
	public String getZi() {
		return zi;
	}
	/**   
	 * 获取：号(变动报告编号)   
	 * @return hao 号(变动报告编号)   
	 */
	public String getHao() {
		return hao;
	}
	/**   
	 * 获取：变动后使用单位   
	 * @return hdwbh 变动后使用单位   
	 */
	public String getHdwbh() {
		return hdwbh;
	}
	/**   
	 * 获取：变动后使用人   
	 * @return hrybh 变动后使用人   
	 */
	public String getHrybh() {
		return hrybh;
	}
	/**   
	 * 获取：变动后使用方向   
	 * @return hsyfx 变动后使用方向   
	 */
	public String getHsyfx() {
		return hsyfx;
	}
	/**   
	 * 获取：变动后存放地点   
	 * @return hcfdd 变动后存放地点   
	 */
	public String getHcfdd() {
		return hcfdd;
	}
	/**   
	 * 获取：变动后现状   
	 * @return hxz 变动后现状   
	 */
	public String getHxz() {
		return hxz;
	}
	/**   
	 * 获取：变动后单价   
	 * @return bddj 变动后单价   
	 */
	public Float getBddj() {
		return bddj;
	}
	/**   
	 * 获取：变动原因   
	 * @return bdyy 变动原因   
	 */
	public String getBdyy() {
		return bdyy;
	}
	/**   
	 * 获取：变动后套数量   
	 * @return sh 变动后套数量   
	 */
	public String getSh() {
		return sh;
	}
	/**   
	 * 获取：变动后层数、册数   
	 * @return hcs 变动后层数、册数   
	 */
	public Integer getHcs() {
		return hcs;
	}
	/**   
	 * 获取：变动制单人   
	 * @return jdr 变动制单人   
	 */
	public String getJdr() {
		return jdr;
	}
	/**   
	 * 获取：建档日期   
	 * @return jdrq 建档日期   
	 */
	public String getJdrq() {
		return jdrq;
	}
	/**   
	 * 获取：财务审核人   
	 * @return shr 财务审核人   
	 */
	public String getShr() {
		return shr;
	}
	/**   
	 * 获取：财务审核日期   
	 * @return shrq 财务审核日期   
	 */
	public String getShrq() {
		return shrq;
	}
	/**   
	 * 获取：财务审核意见   
	 * @return shyj 财务审核意见   
	 */
	public String getShyj() {
		return shyj;
	}
	/**   
	 * 获取：变动人姓名   
	 * @return bdrxm 变动人姓名   
	 */
	public String getBdrxm() {
		return bdrxm;
	}
	/**   
	 * 获取：单位名称   
	 * @return dwmc 单位名称   
	 */
	public String getDwmc() {
		return dwmc;
	}
	/**   
	 * 获取：凭证号   
	 * @return pzh 凭证号   
	 */
	public String getPzh() {
		return pzh;
	}
	/**   
	 * 获取：记账日期   
	 * @return jzrq 记账日期   
	 */
	public String getJzrq() {
		return jzrq;
	}
	/**   
	 * 获取：记账标识(未记账：1；已记账：0)   
	 * @return jzbz 记账标识(未记账：1；已记账：0)   
	 */
	public String getJzbz() {
		return jzbz;
	}
	/**   
	 * 获取：归口单位   
	 * @return gkdw 归口单位   
	 */
	public String getGkdw() {
		return gkdw;
	}
	/**   
	 * 获取：归口审核人员   
	 * @return gkshry 归口审核人员   
	 */
	public String getGkshry() {
		return gkshry;
	}
	/**   
	 * 获取：归口意见   
	 * @return gkyj 归口意见   
	 */
	public String getGkyj() {
		return gkyj;
	}
	/**   
	 * 获取：归口日期   
	 * @return gkrq 归口日期   
	 */
	public String getGkrq() {
		return gkrq;
	}
	/**   
	 * 获取：归口人员   
	 * @return gkry 归口人员   
	 */
	public String getGkry() {
		return gkry;
	}
	/**   
	 * 获取：状态标识(录入：55，提交：00，归口审核通过：90，归口不通过：10，财务通过：99，财务不通过：91，领用人录入：55，领用人提交：00，管理员退回：10，领用人通过：60，领用人退回：61，调出单位领导通过：70，调出单位领导退回：71，调入单位领导通过：80，调入单位领导退回：81)   
	 * @return ztbz 状态标识(录入：55，提交：00，归口审核通过：90，归口不通过：10，财务通过：99，财务不通过：91，领用人录入：55，领用人提交：00，管理员退回：10，领用人通过：60，领用人退回：61，调出单位领导通过：70，调出单位领导退回：71，调入单位领导通过：80，调入单位领导退回：81)   
	 */
	public String getZtbz() {
		return ztbz;
	}
	/**   
	 * 获取：变动标识(项目变动：0，单价变动：1，附件增加：2，附件处置：3，资产调拨：4)   
	 * @return djbz 变动标识(项目变动：0，单价变动：1，附件增加：2，附件处置：3，资产调拨：4)   
	 */
	public String getDjbz() {
		return djbz;
	}
	/**   
	 * 获取：变动后建筑面积   
	 * @return hmj 变动后建筑面积   
	 */
	public Float getHmj() {
		return hmj;
	}
	/**   
	 * 获取：变动后自然间数   
	 * @return hzrjs 变动后自然间数   
	 */
	public Integer getHzrjs() {
		return hzrjs;
	}
	/**   
	 * 获取：变动后附属设施   
	 * @return hfsss 变动后附属设施   
	 */
	public String getHfsss() {
		return hfsss;
	}
	/**   
	 * 获取：变动项目标志(资产调拨中的：：：单位内调拨：0，单位间调拨：1，价值变动：2，领用人变动：3，存放地点变动：4，部分报废：5)   
	 * @return xmbz 变动项目标志(资产调拨中的：：：单位内调拨：0，单位间调拨：1，价值变动：2，领用人变动：3，存放地点变动：4，部分报废：5)   
	 */
	public String getXmbz() {
		return xmbz;
	}
	/**   
	 * 获取：领用人   
	 * @return lyr 领用人   
	 */
	public String getLyr() {
		return lyr;
	}
	/**   
	 * 获取：领用人审核日期   
	 * @return lyrrq 领用人审核日期   
	 */
	public String getLyrrq() {
		return lyrrq;
	}
	/**   
	 * 获取：领用人审核意见   
	 * @return lyryj 领用人审核意见   
	 */
	public String getLyryj() {
		return lyryj;
	}
	/**   
	 * 获取：管理员   
	 * @return gly 管理员   
	 */
	public String getGly() {
		return gly;
	}
	/**   
	 * 获取：管理员审核日期   
	 * @return glyshrq 管理员审核日期   
	 */
	public String getGlyshrq() {
		return glyshrq;
	}
	/**   
	 * 获取：管理员审核意见   
	 * @return glyshyj 管理员审核意见   
	 */
	public String getGlyshyj() {
		return glyshyj;
	}
	/**   
	 * 获取：调出单位审核人   
	 * @return dcdwry 调出单位审核人   
	 */
	public String getDcdwry() {
		return dcdwry;
	}
	/**   
	 * 获取：调出单位领导审核日期   
	 * @return dcdwshrq 调出单位领导审核日期   
	 */
	public String getDcdwshrq() {
		return dcdwshrq;
	}
	/**   
	 * 获取：调出单位领导审核意见   
	 * @return dcdwshyj 调出单位领导审核意见   
	 */
	public String getDcdwshyj() {
		return dcdwshyj;
	}
	/**   
	 * 获取：调入单位审核人   
	 * @return drdwry 调入单位审核人   
	 */
	public String getDrdwry() {
		return drdwry;
	}
	/**   
	 * 获取：调入单位领导审核日期   
	 * @return drdwshrq 调入单位领导审核日期   
	 */
	public String getDrdwshrq() {
		return drdwshrq;
	}
	/**   
	 * 获取：调入单位领导审核意见   
	 * @return drdwshyj 调入单位领导审核意见   
	 */
	public String getDrdwshyj() {
		return drdwshyj;
	}
	/**  
	 * 设置：变动报告编号（年月加四位流水号）  
	 * @param bdbgbh 变动报告编号（年月加四位流水号）  
	 */
	public void setBdbgbh(String bdbgbh) {
		this.bdbgbh = bdbgbh;
	}
	/**  
	 * 设置：变动日期  
	 * @param bdrq 变动日期  
	 */
	public void setBdrq(Date bdrq) {
		this.bdrq = bdrq;
	}
	/**  
	 * 设置：变动人人员编号  
	 * @param rybh 变动人人员编号  
	 */
	public void setRybh(String rybh) {
		this.rybh = rybh;
	}
	/**  
	 * 设置：变动人单位编号  
	 * @param dwbh 变动人单位编号  
	 */
	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}
	/**  
	 * 设置：字(变)  
	 * @param zi 字(变)  
	 */
	public void setZi(String zi) {
		this.zi = zi;
	}
	/**  
	 * 设置：号(变动报告编号)  
	 * @param hao 号(变动报告编号)  
	 */
	public void setHao(String hao) {
		this.hao = hao;
	}
	/**  
	 * 设置：变动后使用单位  
	 * @param hdwbh 变动后使用单位  
	 */
	public void setHdwbh(String hdwbh) {
		this.hdwbh = hdwbh;
	}
	/**  
	 * 设置：变动后使用人  
	 * @param hrybh 变动后使用人  
	 */
	public void setHrybh(String hrybh) {
		this.hrybh = hrybh;
	}
	/**  
	 * 设置：变动后使用方向  
	 * @param hsyfx 变动后使用方向  
	 */
	public void setHsyfx(String hsyfx) {
		this.hsyfx = hsyfx;
	}
	/**  
	 * 设置：变动后存放地点  
	 * @param hcfdd 变动后存放地点  
	 */
	public void setHcfdd(String hcfdd) {
		this.hcfdd = hcfdd;
	}
	/**  
	 * 设置：变动后现状  
	 * @param hxz 变动后现状  
	 */
	public void setHxz(String hxz) {
		this.hxz = hxz;
	}
	/**  
	 * 设置：变动后单价  
	 * @param bddj 变动后单价  
	 */
	public void setBddj(Float bddj) {
		this.bddj = bddj;
	}
	/**  
	 * 设置：变动原因  
	 * @param bdyy 变动原因  
	 */
	public void setBdyy(String bdyy) {
		this.bdyy = bdyy;
	}
	/**  
	 * 设置：变动后套数量  
	 * @param sh 变动后套数量  
	 */
	public void setSh(String sh) {
		this.sh = sh;
	}
	/**  
	 * 设置：变动后层数、册数  
	 * @param hcs 变动后层数、册数  
	 */
	public void setHcs(Integer hcs) {
		this.hcs = hcs;
	}
	/**  
	 * 设置：变动制单人  
	 * @param jdr 变动制单人  
	 */
	public void setJdr(String jdr) {
		this.jdr = jdr;
	}
	/**  
	 * 设置：建档日期  
	 * @param jdrq 建档日期  
	 */
	public void setJdrq(String jdrq) {
		this.jdrq = jdrq;
	}
	/**  
	 * 设置：财务审核人  
	 * @param shr 财务审核人  
	 */
	public void setShr(String shr) {
		this.shr = shr;
	}
	/**  
	 * 设置：财务审核日期  
	 * @param shrq 财务审核日期  
	 */
	public void setShrq(String shrq) {
		this.shrq = shrq;
	}
	/**  
	 * 设置：财务审核意见  
	 * @param shyj 财务审核意见  
	 */
	public void setShyj(String shyj) {
		this.shyj = shyj;
	}
	/**  
	 * 设置：变动人姓名  
	 * @param bdrxm 变动人姓名  
	 */
	public void setBdrxm(String bdrxm) {
		this.bdrxm = bdrxm;
	}
	/**  
	 * 设置：单位名称  
	 * @param dwmc 单位名称  
	 */
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	/**  
	 * 设置：凭证号  
	 * @param pzh 凭证号  
	 */
	public void setPzh(String pzh) {
		this.pzh = pzh;
	}
	/**  
	 * 设置：记账日期  
	 * @param jzrq 记账日期  
	 */
	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}
	/**  
	 * 设置：记账标识(未记账：1；已记账：0)  
	 * @param jzbz 记账标识(未记账：1；已记账：0)  
	 */
	public void setJzbz(String jzbz) {
		this.jzbz = jzbz;
	}
	/**  
	 * 设置：归口单位  
	 * @param gkdw 归口单位  
	 */
	public void setGkdw(String gkdw) {
		this.gkdw = gkdw;
	}
	/**  
	 * 设置：归口审核人员  
	 * @param gkshry 归口审核人员  
	 */
	public void setGkshry(String gkshry) {
		this.gkshry = gkshry;
	}
	/**  
	 * 设置：归口意见  
	 * @param gkyj 归口意见  
	 */
	public void setGkyj(String gkyj) {
		this.gkyj = gkyj;
	}
	/**  
	 * 设置：归口日期  
	 * @param gkrq 归口日期  
	 */
	public void setGkrq(String gkrq) {
		this.gkrq = gkrq;
	}
	/**  
	 * 设置：归口人员  
	 * @param gkry 归口人员  
	 */
	public void setGkry(String gkry) {
		this.gkry = gkry;
	}
	/**  
	 * 设置：状态标识(录入：55，提交：00，归口审核通过：90，归口不通过：10，财务通过：99，财务不通过：91，领用人录入：55，领用人提交：00，管理员退回：10，领用人通过：60，领用人退回：61，调出单位领导通过：70，调出单位领导退回：71，调入单位领导通过：80，调入单位领导退回：81)  
	 * @param ztbz 状态标识(录入：55，提交：00，归口审核通过：90，归口不通过：10，财务通过：99，财务不通过：91，领用人录入：55，领用人提交：00，管理员退回：10，领用人通过：60，领用人退回：61，调出单位领导通过：70，调出单位领导退回：71，调入单位领导通过：80，调入单位领导退回：81)  
	 */
	public void setZtbz(String ztbz) {
		this.ztbz = ztbz;
	}
	/**  
	 * 设置：变动标识(项目变动：0，单价变动：1，附件增加：2，附件处置：3，资产调拨：4)  
	 * @param djbz 变动标识(项目变动：0，单价变动：1，附件增加：2，附件处置：3，资产调拨：4)  
	 */
	public void setDjbz(String djbz) {
		this.djbz = djbz;
	}
	/**  
	 * 设置：变动后建筑面积  
	 * @param hmj 变动后建筑面积  
	 */
	public void setHmj(Float hmj) {
		this.hmj = hmj;
	}
	/**  
	 * 设置：变动后自然间数  
	 * @param hzrjs 变动后自然间数  
	 */
	public void setHzrjs(Integer hzrjs) {
		this.hzrjs = hzrjs;
	}
	/**  
	 * 设置：变动后附属设施  
	 * @param hfsss 变动后附属设施  
	 */
	public void setHfsss(String hfsss) {
		this.hfsss = hfsss;
	}
	/**  
	 * 设置：变动项目标志(资产调拨中的：：：单位内调拨：0，单位间调拨：1，价值变动：2，领用人变动：3，存放地点变动：4，部分报废：5)  
	 * @param xmbz 变动项目标志(资产调拨中的：：：单位内调拨：0，单位间调拨：1，价值变动：2，领用人变动：3，存放地点变动：4，部分报废：5)  
	 */
	public void setXmbz(String xmbz) {
		this.xmbz = xmbz;
	}
	/**  
	 * 设置：领用人  
	 * @param lyr 领用人  
	 */
	public void setLyr(String lyr) {
		this.lyr = lyr;
	}
	/**  
	 * 设置：领用人审核日期  
	 * @param lyrrq 领用人审核日期  
	 */
	public void setLyrrq(String lyrrq) {
		this.lyrrq = lyrrq;
	}
	/**  
	 * 设置：领用人审核意见  
	 * @param lyryj 领用人审核意见  
	 */
	public void setLyryj(String lyryj) {
		this.lyryj = lyryj;
	}
	/**  
	 * 设置：管理员  
	 * @param gly 管理员  
	 */
	public void setGly(String gly) {
		this.gly = gly;
	}
	/**  
	 * 设置：管理员审核日期  
	 * @param glyshrq 管理员审核日期  
	 */
	public void setGlyshrq(String glyshrq) {
		this.glyshrq = glyshrq;
	}
	/**  
	 * 设置：管理员审核意见  
	 * @param glyshyj 管理员审核意见  
	 */
	public void setGlyshyj(String glyshyj) {
		this.glyshyj = glyshyj;
	}
	/**  
	 * 设置：调出单位审核人  
	 * @param dcdwry 调出单位审核人  
	 */
	public void setDcdwry(String dcdwry) {
		this.dcdwry = dcdwry;
	}
	/**  
	 * 设置：调出单位领导审核日期  
	 * @param dcdwshrq 调出单位领导审核日期  
	 */
	public void setDcdwshrq(String dcdwshrq) {
		this.dcdwshrq = dcdwshrq;
	}
	/**  
	 * 设置：调出单位领导审核意见  
	 * @param dcdwshyj 调出单位领导审核意见  
	 */
	public void setDcdwshyj(String dcdwshyj) {
		this.dcdwshyj = dcdwshyj;
	}
	/**  
	 * 设置：调入单位审核人  
	 * @param drdwry 调入单位审核人  
	 */
	public void setDrdwry(String drdwry) {
		this.drdwry = drdwry;
	}
	/**  
	 * 设置：调入单位领导审核日期  
	 * @param drdwshrq 调入单位领导审核日期  
	 */
	public void setDrdwshrq(String drdwshrq) {
		this.drdwshrq = drdwshrq;
	}
	/**  
	 * 设置：调入单位领导审核意见  
	 * @param drdwshyj 调入单位领导审核意见  
	 */
	public void setDrdwshyj(String drdwshyj) {
		this.drdwshyj = drdwshyj;
	}
	/**
	 * 变动后土地面积	
	 * @return
	 */
	public String getHtdmj() {
		return htdmj;
	}
	/**
	 * 变动后土地面积
	 * @param htdmj
	 */
	public void setHtdmj(String htdmj) {
		this.htdmj = htdmj;
	}
	/**
	 * 处置收益（部分报废）
	 * @return
	 */
	public Float getCzsy() {
		return czsy;
	}
	/**
	 * 处置收益（部分报废）
	 * @return
	 */
	public void setCzsy(Float czsy) {
		this.czsy = czsy;
	}
	/**
	 * 错误信息
	 * @return
	 */
	public String getErrmsg() {
		return errmsg;
	}
	/**
	 * 错误信息
	 * @param errmsg
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	/**
	 * 资产编号
	 * @return
	 */
	public String getYqbh() {
		return yqbh;
	}
	/**
	 * 资产编号
	 * @return
	 */
	public void setYqbh(String yqbh) {
		this.yqbh = yqbh;
	}
}

