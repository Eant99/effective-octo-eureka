package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;

/**
*@author 杨超超
*@date   2018年2月2日---上午9:48:32
*/
public class Jdbxspxq {
                               
	private  String	 msg;	//  返回提示信息
	private  Boolean success;//	返回true或false（true:成功，false：失败）
	private  String	 djbh;//	单据编号
	private  String	 bxry;//	报销人员
	private  String	 szbm;//	所在部门
	private  String	 jdrq;//	接待日期
	private  String	 jdbm;//	接待部门
	private  String	 lbdw;//	来宾单位
	private  String	 jddd;//	接待地点
	private  String	 bxje;//	报销金额
	private  String	 gwhdxm;//公务活动项目
	private  String	 lbxmjrs;//	来宾姓名及人数
	private  String	 ptxmjrs;//	陪同姓名及人数
	private  String	 jdsy;//	陪同姓名及人数
	private  List<Map<String,Object>>	 cxjklist;//冲销借款集合
	private  List<Map<String,Object>>	 dszflist;//对私支付集合
	private  List<Map<String,Object>>	 dgzflist;//对公支付集合
	private  List<Map<String,Object>>	 gwklist;//	公务卡集合
	private  List<Map<String,Object>>	 imglist;//	附件信息集合
	private  List<Map<String,Object>>	 lclist;//	流程信息集合
	
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
	public String getBxje() {
		return bxje;
	}
	public void setBxje(String bxje) {
		this.bxje = bxje;
	}
	public String getGwhdxm() {
		return gwhdxm;
	}
	public void setGwhdxm(String gwhdxm) {
		this.gwhdxm = gwhdxm;
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
	public List<Map<String, Object>> getCxjklist() {
		return cxjklist;
	}
	public void setCxjklist(List<Map<String, Object>> cxjklist) {
		this.cxjklist = cxjklist;
	}
	public List<Map<String, Object>> getDszflist() {
		return dszflist;
	}
	public void setDszflist(List<Map<String, Object>> dszflist) {
		this.dszflist = dszflist;
	}
	public List<Map<String, Object>> getDgzflist() {
		return dgzflist;
	}
	public void setDgzflist(List<Map<String, Object>> dgzflist) {
		this.dgzflist = dgzflist;
	}
	public List<Map<String, Object>> getGwklist() {
		return gwklist;
	}
	public void setGwklist(List<Map<String, Object>> gwklist) {
		this.gwklist = gwklist;
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
	public String getJdsy() {
		return jdsy;
	}
	public void setJdsy(String jdsy) {
		this.jdsy = jdsy;
	}
	
	

}
