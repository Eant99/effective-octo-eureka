package com.googosoft.pojo.systemset.xtsz;

import java.sql.Date;

/**
* zc_bzxx 实体类
* 创建时间： 2017-01-03
*@author dddd
*/ 


public class ZC_BZXX{
	private String id;
	private String mlbh;
	private String ywmc;
	private byte[] bzxx;
	private String jdr;
	private Date jdrq;
	private String sfxs;
	public void setId(String ID){
	this.id=ID;
	}
	public String getId(){
		return id;
	}
	public void setMlbh(String MLBH){
	this.mlbh=MLBH;
	}
	public String getMlbh(){
		return mlbh;
	}
	public void setYwmc(String YWMC){
	this.ywmc=YWMC;
	}
	public String getYwmc(){
		return ywmc;
	}
	public void setBzxx(byte[] BZXX){
	this.bzxx=BZXX;
	}
	public byte[] getBzxx(){
		return bzxx;
	}
	public void setJdr(String JDR){
	this.jdr=JDR;
	}
	public String getJdr(){
		return jdr;
	}
	public void setJdrq(Date JDRQ){
	this.jdrq=JDRQ;
	}
	public Date getJdrq(){
		return jdrq;
	}
	public void setSfxs(String SFXS){
	this.sfxs=SFXS;
	}
	public String getSfxs(){
		return sfxs;
	}
}

