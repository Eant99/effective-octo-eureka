package com.googosoft.pojo.wsbx;

import com.googosoft.commons.LUser;

public class CW_CJKB {
	private String guid;
	private String jkdh;
	private String jkr;
	private String szbm;
	private String jkje;
	private String cjkje;
	private String bxlx;
	private String jkid;

	public String getJkid() {
		return jkid;
	}

	public void setJkid(String jkid) {
		this.jkid = jkid;
	}

	public String getGuid() {
		return LUser.getGuid();
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getJkdh() {
		return jkdh;
	}

	public void setJkdh(String jkdh) {
		this.jkdh = jkdh;
	}

	public String getJkr() {
		return jkr;
	}

	public void setJkr(String jkr) {
		this.jkr = jkr;
	}

	public String getSzbm() {
		return LUser.getDwbh();
	}

	public void setSzbm(String szbm) {
		this.szbm = szbm;
	}

	public String getJkje() {
		return jkje;
	}

	public void setJkje(String jkje) {
		this.jkje = jkje;
	}

	public String getCjkje() {
		return cjkje;
	}

	public void setCjkje(String cjkje) {
		this.cjkje = cjkje;
	}

	public String getBxlx() {
		return bxlx;
	}

	public void setBxlx(String bxlx) {
		this.bxlx = bxlx;
	}

}
