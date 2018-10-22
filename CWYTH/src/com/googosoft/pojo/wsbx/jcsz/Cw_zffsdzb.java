package com.googosoft.pojo.wsbx.jcsz;

import sun.util.calendar.BaseCalendar.Date;

public class Cw_zffsdzb {
	private String guid;
	private String zffs;
	private String jdfx;
	private String kmbh;
	private String sszt;
	private String kjzd;
	
	public String getKjzd() {
		return kjzd;
	}
	public void setKjzd(String kjzd) {
		this.kjzd = kjzd;
	}
	public String getSszt() {
		return sszt;
	}
	public void setSszt(String sszt) {
		this.sszt = sszt;
	}
	public String getKmbh() {
		return kmbh;
	}
	public void setKmbh(String kmbh) {
		this.kmbh = kmbh;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getZffs() {
		return zffs;
	}
	public void setZffs(String zffs) {
		this.zffs = zffs;
	}
	public String getJdfx() {
		return jdfx;
	}
	public void setJdfx(String jdfx) {
		this.jdfx = jdfx;
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
	public Date getCzrq() {
		return czrq;
	}
	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}
	private String bz;
	private String czr;
	private Date czrq;

	@Override
	public String toString() {
		return "Cw_zffsdzb [guid=" + guid + ", zffs=" + zffs + ", jdfx=" + jdfx
				+ ", kmbh=" + kmbh + ", sszt=" + sszt + ", kjzd=" + kjzd
				+ ", bz=" + bz + ", czr=" + czr + ", czrq=" + czrq + "]";
	}

}
