package com.googosoft.pojo.ysgl;

import oracle.sql.DATE;

public class Ysmb {//预算模板类

	private String guid;//主键
	private String mbbh;//模板编号
	private String mbmc;//模板名称
	private String bz;//备注
	private String czr;//操作人
	private DATE czrq;//操作日期
	public Ysmb() {
		
	}
	public Ysmb(String guid, String mbbh, String mbmc, String bz, String czr, DATE czrq) {
		super();
		this.guid = guid;
		this.mbbh = mbbh;
		this.mbmc = mbmc;
		this.bz = bz;
		this.czr = czr;
		this.czrq = czrq;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getMbbh() {
		return mbbh;
	}
	public void setMbbh(String mbbh) {
		this.mbbh = mbbh;
	}
	public String getMbmc() {
		return mbmc;
	}
	public void setMbmc(String mbmc) {
		this.mbmc = mbmc;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public DATE getCzrq() {
		return czrq;
	}
	public void setCzrq(DATE czrq) {
		this.czrq = czrq;
	}
	
}
