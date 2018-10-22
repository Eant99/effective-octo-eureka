package com.googosoft.pojo.zcjz;

import com.googosoft.util.WindowUtil;

/**
 * 批量赋值实体类
 * @author Administrator
 *
 */
public class zjb_pifz {
	
	private String ksh;
	private String jsh;
	private String zjb_syr;
	private String zjb_sydw;
	private String zjb_bzxx;
	private String zjb_syfx;
	private String zjb_flh;
	private String zjb_flmc;
	private String zjb_yqbh;
	public String getZjb_flh() {
		return zjb_flh;
	}
	public void setZjb_flh(String zjb_flh) {
		this.zjb_flh = zjb_flh;
	}
	public String getZjb_flmc() {
		return zjb_flmc;
	}
	public void setZjb_flmc(String zjb_flmc) {
		this.zjb_flmc = zjb_flmc;
	}
	public String getZjb_yqbh() {
		return zjb_yqbh;
	}
	public void setZjb_yqbh(String zjb_yqbh) {
		this.zjb_yqbh = zjb_yqbh;
	}
	/**
	 * 开始行
	 * @return
	 */
	public String getKsh() {
		return ksh;
	}
	/**
	 * 开始行
	 */
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
	/**
	 * 结束行
	 */
	public void setJsh(String jsh) {
		this.jsh = jsh;
	}
	/**
	 * 使用人
	 * @return
	 */
	public String getZjb_syr() {
		return WindowUtil.getXx(zjb_syr,"R");
	}
	/**
	 * 使用人
	 */
	public void setZjb_syr(String zjb_syr) {
		this.zjb_syr = zjb_syr;
	}
	/**
	 * 使用单位
	 * @return
	 */
	public String getZjb_sydw() {
		return WindowUtil.getXx(zjb_sydw,"D");
	}
	/**
	 * 使用单位
	 */
	public void setZjb_sydw(String zjb_sydw) {
		this.zjb_sydw = zjb_sydw;
	}
	/**
	 * 存放地点
	 * @return
	 */
	public String getZjb_bzxx() {
		return WindowUtil.getXx(zjb_bzxx,"P");
	}
	/**
	 * 存放地点
	 */
	public void setZjb_bzxx(String zjb_bzxx) {
		this.zjb_bzxx = zjb_bzxx;
	}
	/**
	 * 使用方向
	 * @return
	 */
	public String getZjb_syfx() {
		return zjb_syfx;
	}
	/**
	 * 使用方向
	 */
	public void setZjb_syfx(String zjb_syfx) {
		this.zjb_syfx = zjb_syfx;
	}
	
}
