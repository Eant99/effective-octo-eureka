package com.googosoft.pojo.wsbx;

import java.io.Serializable;
import java.util.Date;

public class CW_GWKB implements Serializable {
	private String guid;
	private String skdh;
	private String szbm;
	private String ryxm;
	private String skrq;
	private String skje;
	private String sjskje;
	private String kh;
	private String bxlx;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getSkdh() {
		return skdh;
	}

	public void setSkdh(String skdh) {
		this.skdh = skdh;
	}

	public String getSzbm() {
		return szbm;
	}

	public void setSzbm(String szbm) {
		this.szbm = szbm;
	}

	public String getRyxm() {
		if(ryxm.indexOf(")")>0){
			ryxm = ryxm.substring(1,ryxm.indexOf(")"));
		}
		return ryxm;
	}

	public void setRyxm(String ryxm) {
		this.ryxm = ryxm;
	}

	public String getSkrq() {
		return skrq;
	}

	public void setSkrq(String skrq) {
		this.skrq = skrq;
	}

	public String getSkje() {
		return skje;
	}
	public String getSjskje() {
		return sjskje;
	}

	public void setSkje(String skje) {
		this.skje = skje;
	}
	public void setSjskje(String sjskje) {
		this.sjskje = sjskje;
	}

	public String getKh() {
		return kh;
	}

	public void setKh(String kh) {
		this.kh = kh;
	}

	public String getBxlx() {
		return bxlx;
	}

	public void setBxlx(String bxlx) {
		this.bxlx = bxlx;
	}

}
