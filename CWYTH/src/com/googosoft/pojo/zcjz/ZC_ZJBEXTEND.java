package com.googosoft.pojo.zcjz;

import java.util.List;

public class ZC_ZJBEXTEND extends zc_yshd{
	public List<zc_zjb> zjbmx;

	public List<zc_zjb> getZjbmx() {
		return zjbmx;
	}

	public void setZjbmx(List<zc_zjb> zjbmx) {
		this.zjbmx = zjbmx;
	}
	
	public String errmsg;
	/**
	 * 错误信息
	 * @return
	 */
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
