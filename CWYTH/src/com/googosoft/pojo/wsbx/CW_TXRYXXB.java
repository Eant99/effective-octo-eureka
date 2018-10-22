package com.googosoft.pojo.wsbx;

import com.googosoft.commons.LUser;

public class CW_TXRYXXB {    
	private String guid;
	private String txbh;
	private String rybh;
	private String ryxm;
	private String szdw;
	private String dwmc;
	private String sfxwry;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getTxbh() {
		return txbh;
	}
	public void setTxbh(String txbh) {
		this.txbh = txbh;
	}
	public String getRybh() {
		return rybh;
	}
	public void setRybh(String rybh) {
		this.rybh = rybh;
	}
	public String getSzdw() {
		return szdw;
	}
	public void setSzdw(String szdw) {
		this.szdw = szdw;
	}
	
	public String getRyxm() {
		return ryxm;
	}
	public void setRyxm(String ryxm) {
		this.ryxm = ryxm;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getSfxwry() {
		return sfxwry;
	}
	public void setSfxwry(String sfxwry) {
		this.sfxwry = sfxwry;
	}
	@Override
	public String toString() {
		return "CW_TXRYXXB [guid=" + guid + ", txbh=" + txbh + ", rybh=" + rybh + ", szdw=" + szdw + "]";
	}

}
