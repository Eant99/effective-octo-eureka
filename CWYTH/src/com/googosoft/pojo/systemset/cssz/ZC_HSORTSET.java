package com.googosoft.pojo.systemset.cssz;

/**
* ZC_HSORTSET 实体类
* 创建时间： 2016-10-29
*/ 
public class ZC_HSORTSET{
	private String zbh;  //组编号
	private String zmc;  //组名称
	private String blw;  //保留位
	private String flmc;  //分类名称
	
	public void setZbh(String ZBH){
	    this.zbh=ZBH;
	}
	/**
	 * 组编号
	 */
	public String getZbh(){
		return zbh;
	}
	
	public void setZmc(String ZMC){
	    this.zmc=ZMC;
	}
	/**
	 * 组名称
	 */
	public String getZmc(){
		return zmc;
	}
	
	public void setBlw(String BLW){
	    this.blw=BLW;
	}
	/**
	 * 保留位
	 */
	public String getBlw(){
		return blw;
	}
	/**
	 * 分类名称
	 */
	public String getFlmc() {
		return flmc;
	}
	public void setFlmc(String flmc) {
		this.flmc = flmc;
	}
}
