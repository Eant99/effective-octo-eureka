package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;

/**
*@author 杨超超
*@date   2018年2月26日---下午4:20:06
*/
public class Jkspxq {
	private String  msg;         //返回提示信息
	private Boolean  success;    //返回true或false（true:成功，false：失败）
	private String djbh;//单据编号
	private String jkrmc;//借款人名称
	private String szbm;//所在部门
	private String jksj;//借款时间
	private String jkzje;//借款总金额
	private String jksy;//借款事由
	private String zffs;//支付方式 0.对私 1.对公
	private List<Map<String,Object>>  xmxxlist;//项目信息list
	private List<Map<String,Object>>  dslist;//对私list
	private List<Map<String,Object>>  dglist;//对公list
	private List<Map<String,Object>>  lclist; //流程信息集合
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
	public String getDjbh() {
		return djbh;
	}
	public void setDjbh(String djbh) {
		this.djbh = djbh;
	}
	public String getJkrmc() {
		return jkrmc;
	}
	public void setJkrmc(String jkrmc) {
		this.jkrmc = jkrmc;
	}
	public String getSzbm() {
		return szbm;
	}
	public void setSzbm(String szbm) {
		this.szbm = szbm;
	}
	public String getJksj() {
		return jksj;
	}
	public void setJksj(String jksj) {
		this.jksj = jksj;
	}
	public String getJkzje() {
		return jkzje;
	}
	public void setJkzje(String jkzje) {
		this.jkzje = jkzje;
	}
	public String getJksy() {
		return jksy;
	}
	public void setJksy(String jksy) {
		this.jksy = jksy;
	}
	public String getZffs() {
		return zffs;
	}
	public void setZffs(String zffs) {
		this.zffs = zffs;
	}
	public List<Map<String, Object>> getXmxxlist() {
		return xmxxlist;
	}
	public void setXmxxlist(List<Map<String, Object>> xmxxlist) {
		this.xmxxlist = xmxxlist;
	}
	public List<Map<String, Object>> getDslist() {
		return dslist;
	}
	public void setDslist(List<Map<String, Object>> dslist) {
		this.dslist = dslist;
	}
	public List<Map<String, Object>> getDglist() {
		return dglist;
	}
	public void setDglist(List<Map<String, Object>> dglist) {
		this.dglist = dglist;
	}
	public List<Map<String, Object>> getLclist() {
		return lclist;
	}
	public void setLclist(List<Map<String, Object>> lclist) {
		this.lclist = lclist;
	}
	
	
}
