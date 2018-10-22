package com.googosoft.pojo.zccz;

import java.util.Date;

/**
* ZC_CZSQMXB 实体类
* 创建时间： 2016-11-02
*@author master
*资产-申请明细表
*/ 


public class ZC_CZSQMXB{
	private String sqmxbh; //申请明细编号
	private String yqbh;//资产编号
	private String sqbh;//申请编号
	private String yqmc;//资产名称
	private String sydw;//使用单位
	private String syr;//使用人
	private String flh;//分类号
	private String flmc;//分类名称
	private float zzj;//总金额
	private String bzxx;//存放地点
	private String gg;//规格
	private String xh;//型号
	private Date gzrq;//购置日期
	private Date rzrq;//入账日期
	private String ztbz;//状态标识
	private String sfczd;//是否已生成处置单(1：是，0：否)
	private String czbgbh;//处置报告编号
	private String jdr;
	/**
	 * 建档人
	 * @return
	 */
	public String getJdr() {
		return jdr;
	}
	public void setJdr(String jdr) {
		this.jdr = jdr;
	}
	/**
	 * 申请明细编号
	 * @param SQMXBH
	 */
	public void setSqmxbh(String SQMXBH){
	this.sqmxbh=SQMXBH;
	}
	public String getSqmxbh(){
		return sqmxbh;
	}
	/**
	 * 资产编号
	 * @param YQBH
	 */
	public void setYqbh(String YQBH){
	this.yqbh=YQBH;
	}
	public String getYqbh(){
		return yqbh;
	}
	/**
	 * 申请编号
	 * @param SQBH
	 */
	public void setSqbh(String SQBH){
	this.sqbh=SQBH;
	}
	public String getSqbh(){
		return sqbh;
	}
	/**
	 * 资产名称
	 * @param YQMC
	 */
	public void setYqmc(String YQMC){
	this.yqmc=YQMC;
	}
	public String getYqmc(){
		return yqmc;
	}
	/**
	 * 使用单位
	 * @param SYDW
	 */
	public void setSydw(String SYDW){
	this.sydw=SYDW;
	}
	public String getSydw(){
		return sydw;
	}
	/**
	 * 使用人
	 * @param SYR
	 */
	public void setSyr(String SYR){
	this.syr=SYR;
	}
	public String getSyr(){
		return syr;
	}
	/**
	 * 分类号
	 * @param FLH
	 */
	public void setFlh(String FLH){
	this.flh=FLH;
	}
	public String getFlh(){
		return flh;
	}
	/**
	 * 分类名称
	 * @param FLMC
	 */
	public void setFlmc(String FLMC){
	this.flmc=FLMC;
	}
	public String getFlmc(){
		return flmc;
	}
	/**
	 * 总金额
	 * @param ZZJ
	 */
	public void setZzj(float ZZJ){
	this.zzj=ZZJ;
	}
	public float getZzj(){
		return zzj;
	}
	/**
	 * 存放地点
	 * @param BZXX
	 */
	public void setBzxx(String BZXX){
	this.bzxx=BZXX;
	}
	public String getBzxx(){
		return bzxx;
	}
	/**
	 * 规格
	 * @param GG
	 */
	public void setGg(String GG){
	this.gg=GG;
	}
	public String getGg(){
		return gg;
	}
	/**
	 * 型号
	 * @param XH
	 */
	public void setXh(String XH){
	this.xh=XH;
	}
	public String getXh(){
		return xh;
	}
	/**
	 * 购置日期
	 * @param GZRQ
	 */
	public void setGzrq(Date GZRQ){
	this.gzrq=GZRQ;
	}
	public Date getGzrq(){
		return gzrq;
	}
	/**
	 * 入账日期
	 * @param RZRQ
	 */
	public void setRzrq(Date RZRQ){
	this.rzrq=RZRQ;
	}
	public Date getRzrq(){
		return rzrq;
	}
	/**
	 * 状态标识
	 * @param ZTBZ
	 */
	public void setZtbz(String ZTBZ){
	this.ztbz=ZTBZ;
	}
	public String getZtbz(){
		return ztbz;
	}
	/**
	 * 是否已生成处置单(1：是，0：否)
	 * @param SFCZD
	 */
	public void setSfczd(String SFCZD){
	this.sfczd=SFCZD;
	}
	public String getSfczd(){
		return sfczd;
	}
	/**
	 * 处置报告编号
	 * @param CZBGBH
	 */
	public void setCzbgbh(String CZBGBH){
	this.czbgbh=CZBGBH;
	}
	public String getCzbgbh(){
		return czbgbh;
	}
}

