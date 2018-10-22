package com.googosoft.pojo.systemset.cssz;

/**是否提示说明类
* zc_ywsmset 实体类
* 创建时间： 2016-12-29
*@author dddd
*/ 


public class ZC_YWSMSET{
	private String rybh;//登陆人编号
	private String mkbh;//模块编号
	private Integer sfts;//是否提示
	public void setRybh(String RYBH){
	this.rybh=RYBH;
	}
	public String getRybh(){
		return rybh;
	}
	public void setMkbh(String MKBH){
	this.mkbh=MKBH;
	}
	public String getMkbh(){
		return mkbh;
	}
	public Integer getSfts() {
		return sfts;
	}
	public void setSfts(Integer sfts) {
		this.sfts = sfts;
	}

}

