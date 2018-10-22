package com.googosoft.pojo.fzgn.wxzf;

import java.util.Date;

/**
* CW_CBSXX 实体类
* 创建时间： 2018-02-09
*@author dddd
*/ 


public class CW_CBSXX{
	private String guid;
	private String cbsbh;
	private String cbsmc;
	private String cbswxh;
	private String lxr;
	private String lxdh;
	private Date cbksrq;
	private Date cbjsrq;
	private String bz;
	private String sftj;
	public String getSftj() {
		return sftj;
	}
	public void setSftj(String sftj) {
		this.sftj = sftj;
	}
	public void setGuid(String GUID){
	this.guid=GUID;
	}
	public String getGuid(){
		return guid;
	}
	public void setCbsbh(String CBSBH){
	this.cbsbh=CBSBH;
	}
	public String getCbsbh(){
		return cbsbh;
	}
	public void setCbsmc(String CBSMC){
	this.cbsmc=CBSMC;
	}
	public String getCbsmc(){
		return cbsmc;
	}
	public void setCbswxh(String CBSWXH){
	this.cbswxh=CBSWXH;
	}
	public String getCbswxh(){
		return cbswxh;
	}
	public void setLxr(String LXR){
	this.lxr=LXR;
	}
	public String getLxr(){
		return lxr;
	}
	public void setLxdh(String LXDH){
	this.lxdh=LXDH;
	}
	public String getLxdh(){
		return lxdh;
	}
	
	public Date getCbksrq() {
		return cbksrq;
	}
	public void setCbksrq(Date cbksrq) {
		this.cbksrq = cbksrq;
	}
	public Date getCbjsrq() {
		return cbjsrq;
	}
	public void setCbjsrq(Date cbjsrq) {
		this.cbjsrq = cbjsrq;
	}
	public void setBz(String BZ){
	this.bz=BZ;
	}
	public String getBz(){
		return bz;
	}
}

