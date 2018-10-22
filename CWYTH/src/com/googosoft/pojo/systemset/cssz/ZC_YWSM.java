package com.googosoft.pojo.systemset.cssz;

import java.sql.Blob;
import java.util.Date;

/**
* 业务说明表实体类
* 创建时间： 2016-10-25
* @author master
*/ 
public class ZC_YWSM{
	private String id;   //主键sys_guid()
	private String mkbh; //业务类型（模块编号）
	private String mkmc; //业务名称（模块名称）
	private String ywsm; //业务说明
	private String jdr;  //建档人
	private Date jdrq;   //建档日期
	
	
	public String getYwsm() {
		return ywsm;
	}
	public void setYwsm(String ywsm) {
		this.ywsm = ywsm;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMkbh() {
		return mkbh;
	}
	public void setMkbh(String mkbh) {
		this.mkbh = mkbh;
	}
	public String getMkmc() {
		return mkmc;
	}
	public void setMkmc(String mkmc) {
		this.mkmc = mkmc;
	}
	public String getJdr() {
		return jdr;
	}
	public void setJdr(String jdr) {
		this.jdr = jdr;
	}
	public Date getJdrq() {
		return jdrq;
	}
	public void setJdrq(Date jdrq) {
		this.jdrq = jdrq;
	}
	
}
