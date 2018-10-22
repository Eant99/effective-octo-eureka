package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;

/**
*@author 杨超超
*@date   2018年1月31日---下午2:45:55
*/
public class Ccsqbx {
	private String msg;//返回提示信息
	private Boolean success;//返回true或者false
	private String sclj;//返回上传路径
	private List<Map<String,Object>> items;//返回数据信息集合
	private String ccrs;//出差人数
	private String ccts;//出差天数
	private List<Map<String,Object>> cclxlist;//出差类型list
	private List<Map<String,Object>> xmxxlist;//项目信息list
	private String zbid;//主表ID
	private String djbh;//单据编号
	private List<Map<String,Object>> sflist;//省份list
	private List<Map<String,Object>> sqlist;//市区list
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
	public String getCcrs() {
		return ccrs;
	}
	public void setCcrs(String ccrs) {
		this.ccrs = ccrs;
	}
	public String getCcts() {
		return ccts;
	}
	public void setCcts(String ccts) {
		this.ccts = ccts;
	}
	public List<Map<String, Object>> getCclxlist() {
		return cclxlist;
	}
	public void setCclxlist(List<Map<String, Object>> cclxlist) {
		this.cclxlist = cclxlist;
	}
	public List<Map<String, Object>> getXmxxlist() {
		return xmxxlist;
	}
	public void setXmxxlist(List<Map<String, Object>> xmxxlist) {
		this.xmxxlist = xmxxlist;
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
	public List<Map<String, Object>> getSflist() {
		return sflist;
	}
	public void setSflist(List<Map<String, Object>> sflist) {
		this.sflist = sflist;
	}
	public List<Map<String, Object>> getSqlist() {
		return sqlist;
	}
	public void setSqlist(List<Map<String, Object>> sqlist) {
		this.sqlist = sqlist;
	}

}
