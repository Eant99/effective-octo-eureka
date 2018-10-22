package com.googosoft.pojo.fzgn.jcsz;


/**
* 人员论文表 实体类
* 创建时间： 2016-11-07
*@author master
*/ 
public class GX_SYS_RYLWB{
	private String guid;//sys_guid（pk）
	private String rybh;//人员编号
	private String rq;//发表日期
	private String lwtm;//论文题目
	private String lwjb;//论文级别
	private String sfkw;//所发刊物
	public void setGuid(String GUID){
	this.guid=GUID;
	}
	/**
	 * sys_guid（pk）
	 */
	public String getGuid(){
		return  guid;
	}
	public void setRybh(String RYBH){
	this.rybh=RYBH;
	}
	/**
	 * 人员编号
	 */
	public String getRybh(){
		return rybh;
	}
	public void setRq(String RQ){
	this.rq=RQ;
	}
	/**
	 * 发表日期
	 */
	public String getRq(){
		return rq;
	}
	public void setLwtm(String LWTM){
	this.lwtm=LWTM;
	}
	/**
	 * 论文题目
	 */
	public String getLwtm(){
		return lwtm;
	}
	public void setLwjb(String LWJB){
	this.lwjb=LWJB;
	}
	/**
	 * 论文级别
	 */
	public String getLwjb(){
		return lwjb;
	}
	public void setSfkw(String SFKW){
	this.sfkw=SFKW;
	}
	/**
	 * 所发刊物
	 */
	public String getSfkw(){
		return sfkw;
	}
}

