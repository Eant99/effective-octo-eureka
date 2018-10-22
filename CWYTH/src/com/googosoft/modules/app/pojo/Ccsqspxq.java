package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;

import com.googosoft.util.Validate;

public class Ccsqspxq {
	private String msg;//返回提示信息
	private Boolean success;//返回true或者false
	
	private String djbh;//单据编号
	private String sqr;//申请人
	private String szbm;//所在部门
	private String ccts;//出差天数
	private String ccrs;//出差人数
	private String cclx;//出差类型
	private String pxfy;//培训费用
	private String sfyzf;//是否预支付（传是或者是否）
	private String yzfje;//预支付金额
	private String ccnr;//出差内容
	private String kssj;//开始时间
	private String jssj;//结束时间
	private String cfdd;//出发地点
	private String mddd;//目的地点
	private String ccsf;//出差省份
	private String ccsq;//市区
	private String jtgj;//交通工具（多个交通工具用逗号隔开）
	private List<Map<String,Object>> xmlist;//  项目数据集合
	private List<Map<String,Object>> rylist	;//	人员信息集合
	private List<Map<String,Object>> imglist;//	附件信息集合
	private List<Map<String,Object>> lclist;//	流程信息集合
	
	
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
	public String getSqr() {
		return sqr;
	}
	public void setSqr(String sqr) {
		this.sqr = sqr;
	}
	public String getSzbm() {
		return szbm;
	}
	public void setSzbm(String szbm) {
		this.szbm = szbm;
	}
	public String getCcts() {
		return ccts;
	}
	public void setCcts(String ccts) {
		this.ccts = ccts;
	}
	public String getCcrs() {
		return ccrs;
	}
	public void setCcrs(String ccrs) {
		this.ccrs = ccrs;
	}
	public String getCclx() {
		return cclx;
	}
	public void setCclx(String cclx) {
		this.cclx = cclx;
	}
	public String getPxfy() {
		return pxfy;
	}
	public void setPxfy(String pxfy) {
		this.pxfy = pxfy;
	}
	public String getSfyzf() {
		return sfyzf;
	}
	public void setSfyzf(String sfyzf) {
		this.sfyzf = sfyzf;
	}
	public String getCcnr() {
		return ccnr;
	}
	public void setCcnr(String ccnr) {
		this.ccnr = ccnr;
	}
	public String getKssj() {
		return kssj;
	}
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	public String getJssj() {
		return jssj;
	}
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	public String getCfdd() {
		return cfdd;
	}
	public void setCfdd(String cfdd) {
		this.cfdd = cfdd;
	}
	public String getMddd() {
		return mddd;
	}
	public void setMddd(String mddd) {
		this.mddd = mddd;
	}
	public String getJtgj() {
		return jtgj;
	}
	public void setJtgj(String jtgj) {
		this.jtgj = jtgj;
	}
	public List<Map<String, Object>> getXmlist() {
		return xmlist;
	}
	public void setXmlist(List<Map<String, Object>> xmlist) {
		this.xmlist = xmlist;
	}
	public List<Map<String, Object>> getRylist() {
		return rylist;
	}
	public void setRylist(List<Map<String, Object>> rylist) {
		this.rylist = rylist;
	}
	public List<Map<String, Object>> getImglist() {
		return imglist;
	}
	public void setImglist(List<Map<String, Object>> imglist) {
		this.imglist = imglist;
	}
	public List<Map<String, Object>> getLclist() {
		return lclist;
	}
	public void setLclist(List<Map<String, Object>> lclist) {
		this.lclist = lclist;
	}
	public String getYzfje() {
		return yzfje;
	}
	public void setYzfje(String yzfje) {
		this.yzfje = yzfje;
	}
	public String getCcsf() {
		return ccsf;
	}
	public void setCcsf(String ccsf) {
		this.ccsf = ccsf;
	}
	public String getCcsq() {
		return ccsq;
	}
	public void setCcsq(String ccsq) {
		this.ccsq = ccsq;
	}
}
