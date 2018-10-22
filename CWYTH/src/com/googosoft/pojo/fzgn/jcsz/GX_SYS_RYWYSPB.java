package com.googosoft.pojo.fzgn.jcsz;

/**
* 人员外语水平表  实体类
* 创建时间： 2016-11-08
*@author master
*/ 
public class GX_SYS_RYWYSPB{
	private String guid;//sys_guid（pk）
	private String rybh;//人员编号
	private String yz;//语种
	private String sp;//水平
	public void setGuid(String GUID){
	this.guid=GUID;
	}
	/**
	 * sys_guid（pk）
	 */
	public String getGuid(){
		return guid;
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
	public void setYz(String YZ){
	this.yz=YZ;
	}
	/**
	 * 外语语种
	 */
	public String getYz(){
		return yz;
	}
	public void setSp(String SP){
	this.sp=SP;
	}
	/**
	 * 外语水平
	 */
	public String getSp(){
		return sp;
	}
}

