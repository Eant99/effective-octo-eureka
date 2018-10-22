package com.googosoft.pojo.zcjy;

import com.googosoft.pojo.StateManager;
import com.googosoft.util.AutoKey;
import com.googosoft.util.Validate;

public class ZC_JYSQB {
	private String jysqbh;
	private String sqr;
	private String sqdw;
	private String jyrq;
	private String jyr;
	private String jybm;
	private String jyyy;
	private String yjghrq;
	private String ztbz;
	private String shry;
	private String shrq;
	private String shyj;
	private String jdr;
	private String jdrq;
	
	public String getJysqbh() {
		return Validate.isNullToDefaultString(jysqbh,AutoKey.createYear("zc_jysqb", "jysqbh", "4"));
	}
	public void setJysqbh(String jysqbh) {
		this.jysqbh = jysqbh;
	}
	public String getSqr() {
		return Validate.isNullToDefaultString(sqr,"");
	}
	public void setSqr(String sqr) {
		this.sqr = sqr;
	}
	public String getSqdw() {
		return Validate.isNullToDefaultString(sqdw,"");
	}
	public void setSqdw(String sqdw) {
		this.sqdw = sqdw;
	}
	public String getJyrq() {
		return Validate.isNullToDefaultString(jyrq,"");
	}
	public void setJyrq(String jyrq) {
		this.jyrq = jyrq;
	}
	public String getJyr() {
		return Validate.isNullToDefaultString(jyr,"");
	}
	public void setJyr(String jyr) {
		this.jyr = jyr;
	}
	public String getJybm() {
		return Validate.isNullToDefaultString(jybm,"");
	}
	public void setJybm(String jybm) {
		this.jybm = jybm;
	}
	public String getJyyy() {
		return Validate.isNullToDefaultString(jyyy,"");
	}
	public void setJyyy(String jyyy) {
		this.jyyy = jyyy;
	}
	public String getYjghrq() {
		return Validate.isNullToDefaultString(yjghrq,"");
	}
	public void setYjghrq(String yjghrq) {
		this.yjghrq = yjghrq;
	}
	public String getZtbz() {
		return Validate.isNullToDefaultString(ztbz,StateManager.ZCJY_SQ_WTJ);
	}
	public void setZtbz(String ztbz) {
		this.ztbz = ztbz;
	}
	public String getShry() {
		return Validate.isNullToDefaultString(shry,"");
	}
	public void setShry(String shry) {
		this.shry = shry;
	}
	public String getShrq() {
		return Validate.isNullToDefaultString(shrq,"");
	}
	public void setShrq(String shrq) {
		this.shrq = shrq;
	}
	public String getShyj() {
		return Validate.isNullToDefaultString(shyj,"");
	}
	public void setShyj(String shyj) {
		this.shyj = shyj;
	}
	public String getJdr() {
		return Validate.isNullToDefaultString(jdr,"");
	}
	public void setJdr(String jdr) {
		this.jdr = jdr;
	}
	public String getJdrq() {
		return Validate.isNullToDefaultString(jdrq,"");
	}
	public void setJdrq(String jdrq) {
		this.jdrq = jdrq;
	}
}
