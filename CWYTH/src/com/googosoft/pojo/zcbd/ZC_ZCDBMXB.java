package com.googosoft.pojo.zcbd;

/**
* ZC_ZCDBMXB 实体类
* 创建时间： 2016-12-21
*@author lijunhao
*/ 

public class ZC_ZCDBMXB {

	/**
	 * 调拨编号
	 */
	private String guid;
	/**
	 * 变动报告编号
	 */
	private String bdbgbh;
	/**
	 * 资产编号
	 */
	private String yqbh;
	/**
	 * 数量
	 */
	private String sl;
	/**
	 * 金额
	 */
	private float je;
	/**
	 * 使用单位
	 */
	private String sydw;
	/**
	 * 使用人
	 */
	private String syr;
	/**
	 * 存放地点
	 */
	private String cfdd;
	/**
	 * 状态标志（55:未提交；00：已提交；10：归口审核退回；90：归口审核通过）
	 */
	private String ztbz;
	/**
	 * 新生成资产编号
	 */
	private String newyqbh;
	/**
	 * 使用方向
	 */
	private String syfx;
	/**
	 * 现状
	 */
	private String xz;
	/**
	 * 记账标志
	 */
	private String jzbz;
	
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getBdbgbh() {
		return bdbgbh;
	}
	public void setBdbgbh(String bdbgbh) {
		this.bdbgbh = bdbgbh;
	}
	public String getYqbh() {
		return yqbh;
	}
	public void setYqbh(String yqbh) {
		this.yqbh = yqbh;
	}
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	public Float getJe() {
		return je;
	}
	public void setJe(float je) {
		this.je = je;
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
	public String getZtbz() {
		return ztbz;
	}
	public void setZtbz(String ztbz) {
		this.ztbz = ztbz;
	}
	public String getNewyqbh() {
		return newyqbh;
	}
	public void setNewyqbh(String newyqbh) {
		this.newyqbh = newyqbh;
	}
	public String getSyfx() {
		return syfx;
	}
	public void setSyfx(String syfx) {
		this.syfx = syfx;
	}
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	public String getJzbz() {
		return jzbz;
	}
	public void setJzbz(String jzbz) {
		this.jzbz = jzbz;
	}
	
	
}
