package com.googosoft.pojo.ysgl;

public class Xmsr {
	
	private String guid;//主键
	private String xmlxbh;//项目类型编号
	private String srkmbh;//收入科目编号
	private String czr;//操作人
	private String czrq;//操作日期
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getXmlxbh() {
		return xmlxbh;
	}
	public void setXmlxbh(String xmlxbh) {
		this.xmlxbh = xmlxbh;
	}
	public String getSrkmbh() {
		return srkmbh;
	}
	public void setSrkmbh(String srkmbh) {
		this.srkmbh = srkmbh;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getCzrq() {
		return czrq;
	}
	public void setCzrq(String czrq) {
		this.czrq = czrq;
	}
	public Xmsr(String guid, String xmlxbh, String srkmbh, String czr, String czrq) {
		super();
		this.guid = guid;
		this.xmlxbh = xmlxbh;
		this.srkmbh = srkmbh;
		this.czr = czr;
		this.czrq = czrq;
	}
	
}
