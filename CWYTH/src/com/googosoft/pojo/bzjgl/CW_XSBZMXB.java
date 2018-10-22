package com.googosoft.pojo.bzjgl;

import java.util.Date;

/**
* 学生补助明细白表实体类
* 创建时间： 2018-03-25
* @author 
*/ 
public class CW_XSBZMXB {

	private String guid;
	private String jbr; //经办人
	private Date szsj; //设置时间
	private String fabh;//方案编号
	private String xh;//学生学号
	private String bzbh;//补助标准编号
	private String ffje;//发放金额
	private String ffzje;//发放总金额
	private String yhmc;//银行名称
	private String yhkh;//银行帐号
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
	public String getFabh() {
		return fabh;
	}
	public void setFabh(String fabh) {
		this.fabh = fabh;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getBzbh() {
		return bzbh;
	}
	public void setBzbh(String bzbh) {
		this.bzbh = bzbh;
	}
	public String getFfje() {
		return ffje;
	}
	public void setFfje(String ffje) {
		this.ffje = ffje;
	}
	public String getFfzje() {
		return ffzje;
	}
	public void setFfzje(String ffzje) {
		this.ffzje = ffzje;
	}
	public String getYhmc() {
		return yhmc;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
	public String getYhkh() {
		return yhkh;
	}
	public void setYhkh(String yhkh) {
		this.yhkh = yhkh;
	}
	public Date getSzsj() {
		return szsj;
	}
	public void setSzsj(Date szsj) {
		this.szsj = szsj;
	}
}
