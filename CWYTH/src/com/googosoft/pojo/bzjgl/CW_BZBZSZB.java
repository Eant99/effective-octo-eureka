package com.googosoft.pojo.bzjgl;

import java.util.Date;

/**
* 补助标准设置实体类
* 创建时间： 2018-03-23
* @author 
*/ 
public class CW_BZBZSZB {
	
	private String guid;
	private String jbr; //经办人
	private Date szsj; //设置时间
	private String bzbh;//标准编号
	private String bzmc;//补助名称
	private String bzje;//补助金额
	private String sfqy;//是否启用
	private String bz;//备注
	public String getBzbh() {
		return bzbh;
	}
	public void setBzbh(String bzbh) {
		this.bzbh = bzbh;
	}
	public String getBzmc() {
		return bzmc;
	}
	public void setBzmc(String bzmc) {
		this.bzmc = bzmc;
	}
	public String getBzje() {
		return bzje;
	}
	public void setBzje(String bzje) {
		this.bzje = bzje;
	}
	public String getSfqy() {
		return sfqy;
	}
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getJbr() {
		return jbr;
	}
	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
	public Date getSzsj() {
		return szsj;
	}
	public void setSzsj(Date szsj) {
		this.szsj = szsj;
	}
}
