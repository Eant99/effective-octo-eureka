package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;

/**
*@author 杨超超
*@date   2018年2月2日---上午10:46:46
*/
public class Gwjdsqsp {

	private String  msg;         //返回提示信息
	private Boolean  success;    //返回true或false（true:成功，false：失败）
	private String  djbh;        //单据编号
	private String  sqr;         //申请人
	private String  szbm;        //所在部门
	private String  jdrq;        //接待日期
	private String  jdbm;        //接待部门
	private String  lbdw;        //来宾单位
	private String  jddd;        //接待地点
	private String  sfyzf;       //是否预支付
	private String  yzfje;       //预支付金额
	private String  lbxmjrs;     //来宾姓名及人数
	private String  ptxmjrs;     //陪同姓名及人数
	private String  sfjzbx;      //是否集中报销
	private String  jzbxr;       //集中报销人
	private String  jdsy;        //接待事由
	private String  lbxm;//来宾姓名
	private String  lbzw;//来宾职务
	private String  lxr;//联系人
	private String  lxdh;//联系电话
	private String  txry;//同行人员
	private String  lhzs;//附件张数,即来函张数
	private List<Map<String,Object>>  imglist;     //附件信息集合
	private List<Map<String,Object>>  lclist;      //流程信息集合
	private List<Map<String,Object>>  xmlist;      //流程信息集合
	
	
	public String getLhzs() {
		return lhzs;
	}
	public void setLhzs(String lhzs) {
		this.lhzs = lhzs;
	}
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
	public String getLbdw() {
		return lbdw;
	}
	public void setLbdw(String lbdw) {
		this.lbdw = lbdw;
	}
	public String getJddd() {
		return jddd;
	}
	public void setJddd(String jddd) {
		this.jddd = jddd;
	}
	public String getSfyzf() {
		return sfyzf;
	}
	public void setSfyzf(String sfyzf) {
		this.sfyzf = sfyzf;
	}
	public String getYzfje() {
		return yzfje;
	}
	public void setYzfje(String yzfje) {
		this.yzfje = yzfje;
	}
	public String getLbxmjrs() {
		return lbxmjrs;
	}
	public void setLbxmjrs(String lbxmjrs) {
		this.lbxmjrs = lbxmjrs;
	}
	public String getPtxmjrs() {
		return ptxmjrs;
	}
	public void setPtxmjrs(String ptxmjrs) {
		this.ptxmjrs = ptxmjrs;
	}
	public String getSfjzbx() {
		return sfjzbx;
	}
	public void setSfjzbx(String sfjzbx) {
		this.sfjzbx = sfjzbx;
	}
	public String getJzbxr() {
		return jzbxr;
	}
	public void setJzbxr(String jzbxr) {
		this.jzbxr = jzbxr;
	}
	public String getJdsy() {
		return jdsy;
	}
	public void setJdsy(String jdsy) {
		this.jdsy = jdsy;
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
	public String getLbxm() {
		return lbxm;
	}
	public void setLbxm(String lbxm) {
		this.lbxm = lbxm;
	}
	public String getLbzw() {
		return lbzw;
	}
	public void setLbzw(String lbzw) {
		this.lbzw = lbzw;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getTxry() {
		return txry;
	}
	public void setTxry(String txry) {
		this.txry = txry;
	}
	public List<Map<String, Object>> getXmlist() {
		return xmlist;
	}
	public void setXmlist(List<Map<String, Object>> xmlist) {
		this.xmlist = xmlist;
	}


}
