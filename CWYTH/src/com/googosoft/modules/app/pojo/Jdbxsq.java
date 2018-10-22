package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;

/**
*@author 杨超超
*@date   2018年2月1日---下午9:47:42
*/
public class Jdbxsq {

	private String msg;		//返回提示信息
	private Boolean success;//返回true或false（true:成功，false：失败）
	private String sclj;	//上传路径
	private String bxry;	//报销人员
	private String szbm;	//所在部门
	private String jdrq;	//接待日期
	private String jdbm;	//接待部门
	private String jdbmmc;	//接待部门名称
	private String lkdw;	//来客单位
	private String lbryjh;	//来宾人员及人数
	private String phryjh;	//陪同人员及人数
	private String jdsy;   //接待事由
	private String jddd;//接待地点
	private List<Map<String,Object>> rylist;//人员list
	private String zbid;//主表ID
	private String djbh;//单据编号
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getSclj() {
		return sclj;
	}
	public void setSclj(String sclj) {
		this.sclj = sclj;
	}
	public String getBxry() {
		return bxry;
	}
	public void setBxry(String bxry) {
		this.bxry = bxry;
	}
	public String getSzbm() {
		return szbm;
	}
	public void setSzbm(String szbm) {
		this.szbm = szbm;
	}
	public String getJdrq() {
		return jdrq;
	}
	public void setJdrq(String jdrq) {
		this.jdrq = jdrq;
	}
	public String getJdbm() {
		return jdbm;
	}
	public void setJdbm(String jdbm) {
		this.jdbm = jdbm;
	}
	public String getLkdw() {
		return lkdw;
	}
	public void setLkdw(String lkdw) {
		this.lkdw = lkdw;
	}
	public String getLbryjh() {
		return lbryjh;
	}
	public void setLbryjh(String lbryjh) {
		this.lbryjh = lbryjh;
	}
	public String getPhryjh() {
		return phryjh;
	}
	public void setPhryjh(String phryjh) {
		this.phryjh = phryjh;
	}
	public String getJdsy() {
		return jdsy;
	}
	public void setJdsy(String jdsy) {
		this.jdsy = jdsy;
	}
	public List<Map<String, Object>> getRylist() {
		return rylist;
	}
	public void setRylist(List<Map<String, Object>> rylist) {
		this.rylist = rylist;
	}
	public String getZbid() {
		return zbid;
	}
	public void setZbid(String zbid) {
		this.zbid = zbid;
	}
	public String getDjbh() {
		return djbh;
	}
	public void setDjbh(String djbh) {
		this.djbh = djbh;
	}
	public String getJddd() {
		return jddd;
	}
	public void setJddd(String jddd) {
		this.jddd = jddd;
	}
	public String getJdbmmc() {
		return jdbmmc;
	}
	public void setJdbmmc(String jdbmmc) {
		this.jdbmmc = jdbmmc;
	}
	
	

}
