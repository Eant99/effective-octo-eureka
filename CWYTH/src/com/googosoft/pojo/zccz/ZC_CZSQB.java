package com.googosoft.pojo.zccz;

import java.util.Date;

/**
* ZC_CZSQB 实体类
* 创建时间： 2016-11-07
*@author master
*/ 


public class ZC_CZSQB extends ZC_CZSQMXB{
	private String sqbh;
	private String sqr;
	private Date sqrq;
	private String sqdw;
	private String ztbz;
	private String shr;
	private Date shrq;
	private String shyj;
	private Float zje;
	private String czyy;
	private String jdr;
	private Date jdrq;
	/**
	 * 申请编号
	 */
	public void setSqbh(String SQBH){
	this.sqbh=SQBH;
	}
	public String getSqbh(){
		return sqbh;
	}
	/**
	 * 申请人
	 * @param SQR
	 */
	public void setSqr(String SQR){
	this.sqr=SQR;
	}
	public String getSqr(){
		return sqr;
	}
	/**
	 * 申请日期
	 * @param SQRQ
	 */
	public void setSqrq(Date SQRQ){
	this.sqrq=SQRQ;
	}
	public Date getSqrq(){
		return sqrq;
	}
	/**
	 * 申请单位
	 * @param SQDW
	 */
	public void setSqdw(String SQDW){
	this.sqdw=SQDW;
	}
	public String getSqdw(){
		return sqdw;
	}
	/**
	 * 状态标识
	 */
	public void setZtbz(String ZTBZ){
	this.ztbz=ZTBZ;
	}
	public String getZtbz(){
		return ztbz;
	}
	/**
	 * 审核人
	 * @param SHR
	 */
	public void setShr(String SHR){
	this.shr=SHR;
	}
	public String getShr(){
		return shr;
	}
	/**
	 * 审核日期
	 * @param SHRQ
	 */
	public void setShrq(Date SHRQ){
	this.shrq=SHRQ;
	}
	public Date getShrq(){
		return shrq;
	}
	/**
	 * 审核意见
	 * @param SHYJ
	 */
	public void setShyj(String SHYJ){
	this.shyj=SHYJ;
	}
	public String getShyj(){
		return shyj;
	}
	/**
	 * 总金额
	 * @param ZJE
	 */
	public void setZje(Float ZJE){
	this.zje=ZJE;
	}
	public Float getZje(){
		return zje;
	}
	/**
	 * 处置意见
	 * @param CZYY
	 */
	public void setCzyy(String CZYY){
	this.czyy=CZYY;
	}
	public String getCzyy(){
		return czyy;
	}
	/**
	 * 建档人
	 * @param JDR
	 */
	public void setJdr(String JDR){
	this.jdr=JDR;
	}
	public String getJdr(){
		return jdr;
	}
	/**
	 * 建档日期
	 * @param JDRQ
	 */
	public void setJdrq(Date JDRQ){
	this.jdrq=JDRQ;
	}
	public Date getJdrq(){
		return jdrq;
	}
}

