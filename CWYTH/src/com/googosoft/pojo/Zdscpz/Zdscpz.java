package com.googosoft.pojo.Zdscpz;


/**
 * 凭证自动化dto
 * @author googosoft
 *
 */
public class Zdscpz {
	private String zdscpz;//自动生成凭证
	private String zdlrpz;//自动录入凭证
	private String sfyxscpz;//是否允许删除凭证
	private String pzdjsfbc;//凭证单据是否必传
	
	public String getPzdjsfbc() {
		return pzdjsfbc;
	}
	public void setPzdjsfbc(String pzdjsfbc) {
		this.pzdjsfbc = pzdjsfbc;
	}
	public String getSfyxscpz() {
		return sfyxscpz;
	}
	public void setSfyxscpz(String sfyxscpz) {
		this.sfyxscpz = sfyxscpz;
	}
	private String zdr;//制单人
	private String fhr;//复核人
	public String getZdr() {
		return zdr;
	}
	public void setZdr(String zdr) {
		this.zdr = zdr;
	}
	public String getFhr() {
		return fhr;
	}
	public void setFhr(String fhr) {
		this.fhr = fhr;
	}
	public String getJzr() {
		return jzr;
	}
	public void setJzr(String jzr) {
		this.jzr = jzr;
	}
	private String jzr;//记账人
	
	public String getZdscpz() {
		return zdscpz;
	}
	public void setZdscpz(String zdscpz) {
		this.zdscpz = zdscpz;
	}
	public String getZdlrpz() {
		return zdlrpz;
	}
	public void setZdlrpz(String zdlrpz) {
		this.zdlrpz = zdlrpz;
	}
	@Override
	public String toString() {
		return "Zdscpz [zdscpz=" + zdscpz + ", zdlrpz=" + zdlrpz + "]";
	}
	
	

}
