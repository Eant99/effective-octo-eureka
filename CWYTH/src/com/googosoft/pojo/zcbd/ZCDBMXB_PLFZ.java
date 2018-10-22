package com.googosoft.pojo.zcbd;

import com.googosoft.util.Validate;

/**
 * 批量赋值实体类
 * @author Administrator
 *
 */
public class ZCDBMXB_PLFZ {
	private String bdbgbh;
	private String ksh;
	private String jsh;
	private String mxb_syr;
	private String mxb_sydw;
	private String mxb_cfdd;
	private String mxb_sl;
	private Float mxb_je;
	private String errmsg;

	/**
	 * 调拨编号
	 * @return
	 */
	public String getBdbgbh() {
		return bdbgbh;
	}
	/**
	 * 调拨编号
	 */
	public void setBdbgbh(String bdbgbh) {
		this.bdbgbh = bdbgbh;
	}
	/**
	 * 开始行
	 * @return
	 */
	public String getKsh() {
		return ksh;
	}
	public void setKsh(String ksh) {
		this.ksh = ksh;
	}
	/**
	 * 结束行
	 * @return
	 */
	public String getJsh() {
		return jsh;
	}
	public void setJsh(String jsh) {
		this.jsh = jsh;
	}
	/**
	 * 使用人
	 * @return
	 */
	public String getMxb_syr() {
		return mxb_syr;
	}
	public void setMxb_syr(String mxb_syr) {
		this.mxb_syr = mxb_syr;
	}
	/**
	 * 使用单位
	 * @return
	 */
	public String getMxb_sydw() {
		return mxb_sydw;
	}
	public void setMxb_sydw(String mxb_sydw) {
		this.mxb_sydw = mxb_sydw;
	}
	/**
	 * 存放地点
	 * @return
	 */
	public String getMxb_cfdd() {
		return mxb_cfdd;
	}
	public void setMxb_cfdd(String mxb_cfdd) {
		this.mxb_cfdd = mxb_cfdd;
	}
	/**
	 * 数量
	 * @return
	 */
	public String getMxb_sl() {
		return mxb_sl;
	}
	public void setMxb_sl(String mxb_sl) {
		this.mxb_sl = mxb_sl;
	}
	/**
	 * 金额
	 * @return
	 */
	public Float getMxb_je() {
		return mxb_je;
	}
	public void setMxb_je(Float mxb_je) {
		this.mxb_je = mxb_je;
	}
	/**
	 * 错误信息
	 * @return
	 */
	public String getErrmsg() {
		return errmsg;
	}
	/**
	 * 错误信息
	 * @return
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
