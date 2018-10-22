package com.googosoft.pojo.fzgn.jcsz;

import java.util.Date;

/**
* 人员成果奖励表 实体类
* 创建时间： 2016-11-08
*@author master
*/ 
public class GX_SYS_RYCGJLB{
	private String guid;//sys_guid（pk）
	private String rybh;//人员编号
	private String cg;//成果
	private Date rq;//日期
	private String hjsm;//获奖说明
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
	public void setCg(String CG){
	this.cg=CG;
	}
	/**
	 * 成果
	 */
	public String getCg(){
		return cg;
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
	public void setHjsm(String HJSM){
	this.hjsm=HJSM;
	}
	/**
	 * 获奖说明
	 */
	public String getHjsm(){
		return hjsm;
	}
}

