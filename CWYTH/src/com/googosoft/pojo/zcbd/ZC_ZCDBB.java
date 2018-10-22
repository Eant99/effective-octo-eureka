package com.googosoft.pojo.zcbd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googosoft.util.Validate;

/**
* ZC_ZCDBB 实体类
* 创建时间： 2016-12-21
*@author lijunhao
*/ 

public class ZC_ZCDBB {

	/**
	 * 变动报告编号（年月加四位流水号）
	 */
	private String bdbgbh;
	/**
	 * 变动日期
	 */
	private Date bdrq;
	/**
	 * 资产编号
	 */
	private String yqbh;
	/**
	 * 资产名称
	 */
	private String yqmc;
	/**
	 * 编报单位
	 */
	private String dwbh;
	/**
	 *拆分数量 
	 */
	private String cfshl;
	/**
	 * 单价
	 */
	private Float dj;
	/**
	 * 总价
	 */
	private Float zj;
	/**
	 *使用单位
	 */
	private String sydw;
	/**
	 *使用人
	 */
	private String syr;
	/**
	 *存放地点 
	 */
	private String cfdd;
	/**
	 *变动原因
	 */
	private String bdyy;
	/**
	 *状态标志（55:未提交；00：已提交；10：归口审核退回；90：归口审核通过）
	 */
	private String ztbz;
	/**
	 *归口人员
	 */
	private String gkry;
	/**
	 *现状
	 */
	private String xz;
	/**
	 *使用方向
	 */
	private String syfx;
	/**
	 *归口审核意见
	 */
	private String gkyj;
	/**
	 *归口审核日期
	 */
	private Date gkrq;
	/**
	 *归口审核人员
	 */
	private String gkshry;
	/**
	 *建档人
	 */
	private String jdr;
	/**
	 *建档日期
	 */
	private Date jdrq;
	/**
	 *记账标志(未记账：1；已记账：0)
	 */
	private String jzbz;
	
	private List<ZC_ZCDBMXB>  dbmxb = new ArrayList();
	
	public String getBdbgbh() {
		return bdbgbh;
	}
	public void setBdbgbh(String bdbgbh) {
		this.bdbgbh = bdbgbh;
	}
	public Date getBdrq() {
		return bdrq;
	}
	public void setBdrq(Date bdrq) {
		this.bdrq = bdrq;
	}
	public String getYqbh() {
		return yqbh;
	}
	public void setYqbh(String yqbh) {
		this.yqbh = yqbh;
	}
	public String getYqmc() {
		return yqmc;
	}
	public void setYqmc(String yqmc) {
		this.yqmc = yqmc;
	}
	public String getDwbh() {
		return dwbh;
	}
	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}
	public String getCfshl() {
		return cfshl;
	}
	public void setCfshl(String cfshl) {
		this.cfshl = cfshl;
	}
	public Float getDj() {
		return dj;
	}
	public void setDj(Float dj) {
		this.dj = dj;
	}
	public Float getZj() {
		return zj;
	}
	public void setZj(Float zj) {
		this.zj = zj;
	}
	public String getSydw() {
		return sydw;
	}
	public void setSydw(String sydw) {
		this.sydw = sydw;
	}
	public String getSyr() {
		return syr;
	}
	public void setSyr(String syr) {
		this.syr = syr;
	}
	public String getCfdd() {
		return cfdd;
	}
	public void setCfdd(String cfdd) {
		this.cfdd = cfdd;
	}
	public String getBdyy() {
		return bdyy;
	}
	public void setBdyy(String bdyy) {
		this.bdyy = bdyy;
	}
	public String getZtbz() {
		return ztbz;
	}
	public void setZtbz(String ztbz) {
		this.ztbz = ztbz;
	}
	public String getGkry() {
		return gkry;
	}
	public void setGkry(String gkry) {
		this.gkry = gkry;
	}
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	public String getSyfx() {
		return syfx;
	}
	public void setSyfx(String syfx) {
		this.syfx = syfx;
	}
	public String getGkyj() {
		return gkyj;
	}
	public void setGkyj(String gkyj) {
		this.gkyj = gkyj;
	}
	public Date getGkrq() {
		return gkrq;
	}
	public void setGkrq(Date gkrq) {
		this.gkrq = gkrq;
	}
	public String getGkshry() {
		return gkshry;
	}
	public void setGkshry(String gkshry) {
		this.gkshry = gkshry;
	}
	public String getJdr() {
		return jdr;
	}
	public void setJdr(String jdr) {
		this.jdr = jdr;
	}
	public Date getJdrq() {
		return jdrq;
	}
	public void setJdrq(Date jdrq) {
		this.jdrq = jdrq;
	}
	public String getJzbz() {
		return jzbz;
	}
	public void setJzbz(String jzbz) {
		this.jzbz = jzbz;
	}
	
	public List<ZC_ZCDBMXB> getDbmxb() {
		return dbmxb;
	}

	public void setDbmxb(List<ZC_ZCDBMXB> dbmxb) {
		this.dbmxb = dbmxb;
	}
}
