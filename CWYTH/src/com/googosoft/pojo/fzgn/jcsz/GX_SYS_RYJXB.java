package com.googosoft.pojo.fzgn.jcsz;

import java.util.Date;

/**
* 人员进修表  实体类
* 创建时间： 2016-11-08
*@author master
*/ 
public class GX_SYS_RYJXB{
	private String jxnr;//进修内容
	private String guid;//sys_guid（pk）
	private String rybh;//人员编号
	private String jxzl;//进修种类(1:国内进修，2：国外进修)
	private Date jxrq;//开始日期
	private Integer jxsj;//进修时间(个月)
	public void setJxnr(String JXNR){
	this.jxnr=JXNR;
	}
	/**
	 * 进修内容
	 */
	public String getJxnr(){
		return jxnr;
	}
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
	 *人员编号
	 */
	public String getRybh(){
		return rybh;
	}
	public void setJxzl(String JXZL){
	this.jxzl=JXZL;
	}
	/**
	 * 进修种类
	 */
	public String getJxzl(){
		return jxzl;
	}
	public void setJxrq(Date JXRQ){
	this.jxrq=JXRQ;
	}
	/**
	 * 开始日期
	 */
	public Date getJxrq(){
		return jxrq;
	}
	public void setJxsj(Integer JXSJ){
	this.jxsj=JXSJ;
	}
	/**
	 * 进修时间
	 */
	public Integer getJxsj(){
		return jxsj;
	}
}

