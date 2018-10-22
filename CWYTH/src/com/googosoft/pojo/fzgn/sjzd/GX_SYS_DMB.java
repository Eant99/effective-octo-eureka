package com.googosoft.pojo.fzgn.sjzd;

import java.io.Serializable;

/**
* GX_SYS_DMB 实体类
* 创建时间： 2016-10-29
*@author master
*/ 


public class GX_SYS_DMB implements Serializable{
	private String dmxh;  //代码编号
	private String zl;  //种类
	private String dm;  //代码
	private String mc;  //名称
	private String jb;   //级别
	private String bz;   //备注
	private String ms;
	private String yzdm;//已存在代码
	private String dmsx;//代码属性
	private String sjqc;//上级全称
	
	public String getSjqc() {
		return sjqc;
	}
	public void setSjqc(String sjqc) {
		this.sjqc = sjqc;
	}
	/**
	 * 代码编号
	 * @param DMXH
	 */
	public void setDmxh(String DMXH){
	this.dmxh=DMXH;
	}
	public String getDmxh(){
		return dmxh;
	}
	/**
	 * 代码属性
	 * @param DMSX
	 */
	public String getDmsx() {
		return dmsx;
	}
	public void setDmsx(String dmsx) {
		this.dmsx = dmsx;
	}
	/**
	 * 种类
	 * @param ZL
	 */
	public void setZl(String ZL){
	this.zl=ZL;
	}
	public String getZl(){
		return zl;
	}
	/**
	 * 代码
	 * @param DM
	 */
	public void setDm(String DM){
	this.dm=DM;
	}
	public String getDm(){
		return dm;
	}
	/**
	 * 名称
	 * @param MC
	 */
	public void setMc(String MC){
	this.mc=MC;
	}
	public String getMc(){
		return mc;
	}
	/**
	 * 级别
	 * @param JB
	 */
	public void setJb(String JB){
	this.jb=JB;
	}
	public String getJb(){
		return jb;
	}
	/**
	 * 备注
	 * @param BZ
	 */
	public void setBz(String BZ){
	this.bz=BZ;
	}
	public String getBz(){
		return bz;
	}
	public void setMs(String MS){
	this.ms=MS;
	}
	public String getMs(){
		return ms;
	}
	public String getYzdm() {
		return yzdm;
	}
	public void setYzdm(String yzdm) {
		this.yzdm = yzdm;
	}
	
}

