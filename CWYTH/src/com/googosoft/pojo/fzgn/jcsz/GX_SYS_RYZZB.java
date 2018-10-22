package com.googosoft.pojo.fzgn.jcsz;

import java.util.Date;

/**
* 人员著作表 实体类
* 创建时间： 2016-11-08
*@author master
*/ 
public class GX_SYS_RYZZB{
	private String guid;//sys_guid（pk）
	private String rybh;//人员编号
	private Date rq;//日期
	private String zzjb;//著作级别
	private String zzmc;//著作名称
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
	public void setRq(Date RQ){
	this.rq=RQ;
	}
	/**
	 * 日期
	 */
	public Date getRq(){
		return rq;
	}
	public void setZzjb(String ZZJB){
	this.zzjb=ZZJB;
	}
	/**
	 * 著作级别
	 */
	public String getZzjb(){
		return zzjb;
	}
	public void setZzmc(String ZZMC){
	this.zzmc=ZZMC;
	}
	/**
	 * 著作名称
	 */
	public String getZzmc(){
		return zzmc;
	}
}

