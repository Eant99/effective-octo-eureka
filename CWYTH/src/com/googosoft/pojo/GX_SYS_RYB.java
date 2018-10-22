package com.googosoft.pojo;

import java.io.Serializable;

/**
* 人员表实体类
* 创建时间： 2017-6-03
*@author wdm
*/ 
public class GX_SYS_RYB implements Serializable{
	/**
	 * 2018-1-12 18:26:41 wdm
	 * 集群cas+redis  保存对象要实现序列化
	 */
	private static final long serialVersionUID = 1L;
	private String xm;//姓名
    private String mm;//密码
	private String rybh;//人员编号 gid/id
	private String rygh;//人员工号 10001
	private String dwbh;//单位编号  gid/id
	private String dwmc;//单位名称
	private String saas;//人员saas码
	private String sf;//总校长 主管 教师.......
	private String identity;//人员身份  理论上有三种  teacher leader student
	private String lxdh;//联系电话
	private Integer rownums;
	private String type;
	private String guid;
	private String dwid;
	
	
	public Integer getRownums() {
		return rownums;
	}
	public void setRownums(Integer rownums) {
		this.rownums = rownums;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getDwid() {
		return dwid;
	}
	public void setDwid(String dwid) {
		this.dwid = dwid;
	}
	public String getSf() {
		return sf;
	}
	public void setSf(String sf) {
		this.sf = sf;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	public String getRybh() {
		return rybh;
	}
	public void setRybh(String rybh) {
		this.rybh = rybh;
	}
	public String getRygh() {
		return rygh;
	}
	public void setRygh(String rygh) {
		this.rygh = rygh;
	}
	public String getDwbh() {
		return dwbh;
	}
	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getSaas() {
		return saas;
	}
	public void setSaas(String saas) {
		this.saas = saas;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
}

