
package com.googosoft.pojo.fzgn.tzgg;

import java.util.Date;

public class ZC_SYS_TZXX {
	private String gid;
	private String title;//标题
	private String xx;//内容
	private Date fbsj;//发布时间
	private String fbr;//发布人
	private String sfzs;//是否展示
	private String dwbh;//单位编号
	
	public String getDwbh() {
		return dwbh;
	}
	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}
	/**
	 * guid
	 */
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	/**
	 * 标题
	 */
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 内容
	 */
	public String getXx() {
		return xx;
	}
	public void setXx(String xx) {
		this.xx = xx;
	}
	/**
	 * 发布时间
	 */
	public Date getFbsj() {
		return fbsj;
	}
	public void setFbsj(Date fbsj) {
		this.fbsj = fbsj;
	}
	/**
	 * 发布人
	 */
	public String getFbr() {
		return fbr;
	}
	public void setFbr(String fbr) {
		this.fbr = fbr;
	}
	/**
	 * 是否展示
	 */
	public String getSfzs() {
		return sfzs;
	}
	public void setSfzs(String sfzs) {
		this.sfzs = sfzs;
	}
}
