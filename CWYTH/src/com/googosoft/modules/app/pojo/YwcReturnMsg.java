package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;


public class YwcReturnMsg {

	private String msg;//返回提示信息
	private Boolean success;//返回true或者false
	private String sclj;//返回上传路径
	private String lctjzszt;//流程提交 展示状态
	private List<Map<String,Object>> items;//返回数据信息集合
	private String zbid;//主表id
	private String ywdh;//业务单号
	private List<Map<String,Object>> ccfslist;//出差方式list
	private List<Map<String,Object>> jdbmlist;//接待部门list
	private List<Map<String,Object>> cjklist;//冲借款list
	private List<Map<String,Object>> qtrlist;//其他人list
	private List<Map<String,Object>> dgzflist;//对公支付list   
	private List<Map<String,Object>> provincelist;//省份list   
	private List<Map<String,Object>> xmlist;//项目list
	private List<Map<String,Object>> transportList;//交通工具
	private List<Map<String,Object>> yhkhlist;//银行卡号list
	
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
	public List<Map<String, Object>> getItems() {
		return items;
	}
	public void setItems(List<Map<String, Object>> items) {
		this.items = items;
	}
	public String getYwdh() {
		return ywdh;
	}
	public void setYwdh(String ywdh) {
		this.ywdh = ywdh;
	}
	public String getZbid() {
		return zbid;
	}
	public void setZbid(String zbid) {
		this.zbid = zbid;
	}
	public List<Map<String, Object>> getCcfslist() {
		return ccfslist;
	}
	public void setCcfslist(List<Map<String, Object>> ccfslist) {
		this.ccfslist = ccfslist;
	}
	public List<Map<String, Object>> getJdbmlist() {
		return jdbmlist;
	}
	public void setJdbmlist(List<Map<String, Object>> jdbmlist) {
		this.jdbmlist = jdbmlist;
	}
	public List<Map<String, Object>> getCjklist() {
		return cjklist;
	}
	public void setCjklist(List<Map<String, Object>> cjklist) {
		this.cjklist = cjklist;
	}
	public List<Map<String, Object>> getQtrlist() {
		return qtrlist;
	}
	public void setQtrlist(List<Map<String, Object>> qtrlist) {
		this.qtrlist = qtrlist;
	}
	public List<Map<String, Object>> getDgzflist() {
		return dgzflist;
	}
	public void setDgzflist(List<Map<String, Object>> dgzflist) {
		this.dgzflist = dgzflist;
	}
	public List<Map<String, Object>> getProvincelist() {
		return provincelist;
	}
	public void setProvincelist(List<Map<String, Object>> provincelist) {
		this.provincelist = provincelist;
	}
	public List<Map<String, Object>> getXmlist() {
		return xmlist;
	}
	public void setXmlist(List<Map<String, Object>> xmlist) {
		this.xmlist = xmlist;
	}
	public List<Map<String, Object>> getTransportList() {
		return transportList;
	}
	public void setTransportList(List<Map<String, Object>> transportList) {
		this.transportList = transportList;
	}
	public List<Map<String, Object>> getYhkhlist() {
		return yhkhlist;
	}
	public void setYhkhlist(List<Map<String, Object>> yhkhlist) {
		this.yhkhlist = yhkhlist;
	}
	public String getLctjzszt() {
		return lctjzszt;
	}
	public void setLctjzszt(String lctjzszt) {
		this.lctjzszt = lctjzszt;
	}
	
	
}
