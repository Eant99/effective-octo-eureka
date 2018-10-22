package com.googosoft.pojo.kjhs.bbzx;

import java.io.Serializable;

import com.googosoft.util.Validate;

public class Params implements Serializable {
	private String year;
	private String startMonth;
	private String endMonth;
	private String kjkm;
	private String startKjkm;
	private String endKjkm;
	private String startJc;
	private String endJc;
	private String nokm;
	private String jzpz;
	private String gs;
	private String sszt;
	private String kjzd;//会计制度
	private String treebh;
	private String kmbh1;
	private String kmbh2;
	private String kmbh3;
	private String bmh;
	private String kmbh;
	private String types;
	private String bmbh;
	private String xmbh;

	public String getBmh() {
		return bmh;
	}

	public void setBmh(String bmh) {
		this.bmh = bmh;
	}

	public String getKmbh1() {
		return kmbh1;
	}

	public void setKmbh1(String kmbh1) {
		this.kmbh1 = kmbh1;
	}

	public String getKmbh2() {
		return kmbh2;
	}

	public void setKmbh2(String kmbh2) {
		this.kmbh2 = kmbh2;
	}

	public String getKmbh3() {
		return kmbh3;
	}

	public void setKmbh3(String kmbh3) {
		this.kmbh3 = kmbh3;
	}

	public String getTreebh() {
		return treebh;
	}

	public void setTreebh(String treebh) {
		this.treebh = treebh;
	}

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

	public String getYear() {
		return Validate.isNullToDefaultString(year, "");
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStartMonth() {
		return Validate.isNullToDefaultString(startMonth, "");
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndMonth() {
		return Validate.isNullToDefaultString(endMonth, "");
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getKjkm() {
		return Validate.isNullToDefaultString(kjkm, "");
	}

	public void setKjkm(String kjkm) {
		this.kjkm = kjkm;
	}

	public String getStartKjkm() {
		return Validate.isNullToDefaultString(startKjkm, "");
	}

	public void setStartKjkm(String startKjkm) {
		this.startKjkm = startKjkm;
	}

	public String getEndKjkm() {
		return Validate.isNullToDefaultString(endKjkm, "");
	}

	public void setEndKjkm(String endKjkm) {
		this.endKjkm = endKjkm;
	}

	public String getStartJc() {
		return Validate.isNullToDefaultString(startJc, "");
	}

	public void setStartJc(String startJc) {
		this.startJc = startJc;
	}

	public String getEndJc() {
		return Validate.isNullToDefaultString(endJc, "");
	}

	public void setEndJc(String endJc) {
		this.endJc = endJc;
	}

	public String getNokm() {
		return Validate.isNullToDefaultString(nokm, "");
	}

	public void setNokm(String nokm) {
		this.nokm = nokm;
	}

	public String getJzpz() {
		return Validate.isNullToDefaultString(jzpz, "");
	}

	public void setJzpz(String jzpz) {
		this.jzpz = jzpz;
	}

	public String getGs() {
		return Validate.isNullToDefaultString(gs, "");
	}

	public void setGs(String gs) {
		this.gs = gs;
	}
	
	public String getKmbh() {
		return kmbh;
	}

	public void setKmbh(String kmbh) {
		this.kmbh = kmbh;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getBmbh() {
		return bmbh;
	}

	public void setBmbh(String bmbh) {
		this.bmbh = bmbh;
	}

	public String getXmbh() {
		return xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	@Override
	public String toString() {
		return "Params [year=" + year + ", startMonth=" + startMonth
				+ ", endMonth=" + endMonth + ", kjkm=" + kjkm + ", startKjkm="
				+ startKjkm + ", endKjkm=" + endKjkm + ", startJc=" + startJc
				+ ", endJc=" + endJc + ", nokm=" + nokm + ", jzpz=" + jzpz
				+ ", gs=" + gs + ", sszt=" + sszt + ", kjzd=" + kjzd
				+ ", treebh=" + treebh + ", kmbh1=" + kmbh1 + ", kmbh2="
				+ kmbh2 + ", kmbh3=" + kmbh3 + ", bmh=" + bmh + ", kmbh="
				+ kmbh + ", types=" + types + ", bmbh=" + bmbh + ", xmbh="
				+ xmbh + "]";
	}

	
}
