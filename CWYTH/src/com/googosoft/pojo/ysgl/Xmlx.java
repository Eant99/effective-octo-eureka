package com.googosoft.pojo.ysgl;

public class Xmlx {
	
	private String guid;//主键
	private String xmlxbh;//项目类型编号
	private String xmlxmc;//项目类型名称
	private String yslx;//预算类型
	private String sfczzc;//是否财政支出
	private String xmlb;//项目类别
	private String xmszsm;//项目收支说明
	public Xmlx(String guid, String xmlxbh, String xmlxmc, String yslx, String sfczzc, String xmlb, String xmszsm,
			String bz, String czr, String czrq) {
		super();
		this.guid = guid;
		this.xmlxbh = xmlxbh;
		this.xmlxmc = xmlxmc;
		this.yslx = yslx;
		this.sfczzc = sfczzc;
		this.xmlb = xmlb;
		this.xmszsm = xmszsm;
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
	public String getXmlxbh() {
		return xmlxbh;
	}
	public void setXmlxbh(String xmlxbh) {
		this.xmlxbh = xmlxbh;
	}
	public String getXmlxmc() {
		return xmlxmc;
	}
	public void setXmlxmc(String xmlxmc) {
		this.xmlxmc = xmlxmc;
	}
	public String getYslx() {
		return yslx;
	}
	public void setYslx(String yslx) {
		this.yslx = yslx;
	}
	public String getSfczzc() {
		return sfczzc;
	}
	public void setSfczzc(String sfczzc) {
		this.sfczzc = sfczzc;
	}
	public String getXmlb() {
		return xmlb;
	}
	public void setXmlb(String xmlb) {
		this.xmlb = xmlb;
	}
	public String getXmszsm() {
		return xmszsm;
	}
	public void setXmszsm(String xmszsm) {
		this.xmszsm = xmszsm;
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
	public String getCzrq() {
		return czrq;
	}
	public void setCzrq(String czrq) {
		this.czrq = czrq;
	}
	private String bz;//备注
	private String czr;//操作人
	private String czrq;//操作日期
}
