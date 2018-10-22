package com.googosoft.pojo.systemset.qxgl;

/**
* Jsb 角色表 实体类
* 创建时间： 2016-10-20 by 刘帅
*/ 


public class JSB{
	/**
	 * 角色编号
	 */
	private String jsbh;
	/**
	 * 角色名称
	 */
	private String jsmc;
	/**
	 * 备注
	 */
	private String bz;
	/**
	 * 状态
	 */
	private float zt;
	/**
	 * 相关备注
	 */
	private float xgbz;
	/**
	 * 系统备注
	 */
	private String xtbz;
	/**   
	 * 获取角色编号   
	 * @return jsbh 角色编号   
	 */
	public String getJsbh() {
		return jsbh;
	}
	/**   
	 * 获取角色名称   
	 * @return jsmc 角色名称   
	 */
	public String getJsmc() {
		return jsmc;
	}
	/**   
	 * 获取备注   
	 * @return bz 备注   
	 */
	public String getBz() {
		return bz;
	}
	/**   
	 * 获取状态   
	 * @return zt 状态   
	 */
	public float getZt() {
		return zt;
	}
	/**   
	 * 获取相关备注   
	 * @return xgbz 相关备注   
	 */
	public float getXgbz() {
		return xgbz;
	}
	/**   
	 * 获取系统备注   
	 * @return xtbz 系统备注   
	 */
	public String getXtbz() {
		return xtbz;
	}
	/**  
	 * 设置角色编号  
	 * @param jsbh 角色编号  
	 */
	public void setJsbh(String jsbh) {
		this.jsbh = jsbh;
	}
	/**  
	 * 设置角色名称  
	 * @param jsmc 角色名称  
	 */
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	/**  
	 * 设置备注  
	 * @param bz 备注  
	 */
	public void setBz(String bz) {
		this.bz = bz;
	}
	/**  
	 * 设置状态  
	 * @param zt 状态  
	 */
	public void setZt(float zt) {
		this.zt = zt;
	}
	/**  
	 * 设置相关备注  
	 * @param xgbz 相关备注  
	 */
	public void setXgbz(float xgbz) {
		this.xgbz = xgbz;
	}
	/**  
	 * 设置系统备注  
	 * @param xtbz 系统备注  
	 */
	public void setXtbz(String xtbz) {
		this.xtbz = xtbz;
	}
	@Override
	public String toString() {
		return "JSB [jsbh=" + jsbh + ", jsmc=" + jsmc + ", bz=" + bz
				+ ", zt=" + zt + ", xgbz=" + xgbz + ", xtbz=" + xtbz + "]";
	}
}

