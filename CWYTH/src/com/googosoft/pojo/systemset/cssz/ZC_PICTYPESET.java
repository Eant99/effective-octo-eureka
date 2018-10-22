package com.googosoft.pojo.systemset.cssz;

/**
 * Tpb 图片类型设置：图片表实体类
 * 
 *  Create by 刘帅 on 2016-10-25 17:15
 * 
 */
public class ZC_PICTYPESET {
	/**
	 *类型编号 
	 */
	private String lxbh;
	/**
	 * 类型名称
	 */
	private String lxmc;
	/**
	 * 单据类型
	 */
	private String djlx;
	/**
	 * 单据名称
	 */
	private String djmc;
	/**
	 * 是否必须上传
	 */
	private String sfbxsc;
	/**
	 * 上传条件
	 */
	private String sctj;
	/**
	 * 备注
	 */
	private String bz;
	/**   
	 * 获取类型编号   
	 * @return lxbh 类型编号   
	 */
	public String getLxbh() {
		return lxbh;
	}
	/**   
	 * 获取类型名称   
	 * @return lxmc 类型名称   
	 */
	public String getLxmc() {
		return lxmc;
	}
	/**   
	 * 获取单据类型   
	 * @return djlx 单据类型   
	 */
	public String getDjlx() {
		return djlx;
	}
	/**   
	 * 获取单据名称   
	 * @return djmc 单据名称   
	 */
	public String getDjmc() {
		return djmc;
	}
	/**   
	 * 获取是否必须上传   
	 * @return sfbxsc 是否必须上传   
	 */
	public String getSfbxsc() {
		return sfbxsc;
	}
	/**   
	 * 获取上传条件   
	 * @return sctj 上传条件   
	 */
	public String getSctj() {
		return sctj;
	}
	/**   
	 * 获取备注   
	 * @return bz 备注   
	 */
	public String getBz() {
		return bz;
	}
	/**  
	 * 设置类型编号  
	 * @param lxbh 类型编号  
	 */
	public void setLxbh(String lxbh) {
		this.lxbh = lxbh;
	}
	/**  
	 * 设置类型名称  
	 * @param lxmc 类型名称  
	 */
	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}
	/**  
	 * 设置单据类型  
	 * @param djlx 单据类型  
	 */
	public void setDjlx(String djlx) {
		this.djlx = djlx;
	}
	/**  
	 * 设置单据名称  
	 * @param djmc 单据名称  
	 */
	public void setDjmc(String djmc) {
		this.djmc = djmc;
	}
	/**  
	 * 设置是否必须上传  
	 * @param sfbxsc 是否必须上传  
	 */
	public void setSfbxsc(String sfbxsc) {
		this.sfbxsc = sfbxsc;
	}
	/**  
	 * 设置上传条件  
	 * @param sctj 上传条件  
	 */
	public void setSctj(String sctj) {
		this.sctj = sctj;
	}
	/**  
	 * 设置备注  
	 * @param bz 备注  
	 */
	public void setBz(String bz) {
		this.bz = bz;
	}
	
	@Override
	public String toString() {
		return "ZC_PICTYPESET [lxbh=" + lxbh + ", lxmc=" + lxmc + ", djlx="
				+ djlx + ", djmc=" + djmc + ", sfbxsc=" + sfbxsc + ", sctj="
				+ sctj + ", bz=" + bz + "]";
	}
}
