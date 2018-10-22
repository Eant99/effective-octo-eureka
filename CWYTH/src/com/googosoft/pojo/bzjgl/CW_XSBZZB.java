package com.googosoft.pojo.bzjgl;

import java.util.Date;

/**
* 学生补助总表实体类
* 创建时间： 2018-03-23
* @author 
*/ 
public class CW_XSBZZB {
	
	private String jbr; //设置时间
	private Date ffsj; //发放时间
	private String fabh;//方案编号
	private String famc;//方案名称
	private String zy;//摘要
	private String xmbh;//项目编号
	private String fffs;//发放方式
	private String ffje;//发放金额
	private String bz;//备注
	
	private String guid;
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
	public Date getFfsj() {
		return ffsj;
	}
	public void setFfsj(Date ffsj) {
		this.ffsj = ffsj;
	}
	public String getFabh() {
		return fabh;
	}
	public void setFabh(String fabh) {
		this.fabh = fabh;
	}
	public String getFamc() {
		return famc;
	}
	public void setFamc(String famc) {
		this.famc = famc;
	}
	public String getZy() {
		return zy;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	public String getXmbh() {
		return xmbh;
	}
	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}
	public String getFffs() {
		return fffs;
	}
	public void setFffs(String fffs) {
		this.fffs = fffs;
	}
	public String getFfje() {
		return ffje;
	}
	public void setFfje(String ffje) {
		this.ffje = ffje;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
}
