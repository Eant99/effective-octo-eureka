package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;

public class Rcbxxq {
	private String	 msg;//返回提示信息
	private Boolean	 success;//返回true或false（true:成功，false：失败）
	private String	 djbh;//单据编号
	private String	 bxr;//报销人
	private String	 szbm;//所在部门
	private String	 fjzzs;//附件总张数
	private String	 bxzje;//报销总金额
	private String	 sfkylbx;//是否科研类报销（传是或者是否）
	private String	 bxsy;//报销事由
	private String	 bz	;//备注
	private List<Map<String,Object>> bxxxlist;//报销信息数据集合
	private List<Map<String,Object>> xmxxlist;//项目信息数据集合
	private List<Map<String,Object>> dgzflist;//对公支付数据集合
	private List<Map<String,Object>> dszflist;//对私支付数据集合
	private List<Map<String,Object>> cjklist;//冲借款数据集合
	private List<Map<String,Object>> gwklist;//公务卡数据集合
	private List<Map<String,Object>> imglist;//附件信息数据集合
	private List<Map<String,Object>> lclist	;//流程信息数据集合
	private List<Map<String,Object>> fplist	;//发票list
	
	public List<Map<String, Object>> getCjklist() {
		return cjklist;
	}
	public void setCjklist(List<Map<String, Object>> cjklist) {
		this.cjklist = cjklist;
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
	public String getBxr() {
		return bxr;
	}
	public void setBxr(String bxr) {
		this.bxr = bxr;
	}
	public String getSzbm() {
		return szbm;
	}
	public void setSzbm(String szbm) {
		this.szbm = szbm;
	}
	public String getFjzzs() {
		return fjzzs;
	}
	public void setFjzzs(String fjzzs) {
		this.fjzzs = fjzzs;
	}
	public String getBxzje() {
		return bxzje;
	}
	public void setBxzje(String bxzje) {
		this.bxzje = bxzje;
	}
	public String getSfkylbx() {
		return sfkylbx;
	}
	public void setSfkylbx(String sfkylbx) {
		this.sfkylbx = sfkylbx;
	}
	public String getBxsy() {
		return bxsy;
	}
	public void setBxsy(String bxsy) {
		this.bxsy = bxsy;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public List<Map<String, Object>> getBxxxlist() {
		return bxxxlist;
	}
	public void setBxxxlist(List<Map<String, Object>> bxxxlist) {
		this.bxxxlist = bxxxlist;
	}
	public List<Map<String, Object>> getXmxxlist() {
		return xmxxlist;
	}
	public void setXmxxlist(List<Map<String, Object>> xmxxlist) {
		this.xmxxlist = xmxxlist;
	}
	public List<Map<String, Object>> getDgzflist() {
		return dgzflist;
	}
	public void setDgzflist(List<Map<String, Object>> dgzflist) {
		this.dgzflist = dgzflist;
	}
	public List<Map<String, Object>> getDszflist() {
		return dszflist;
	}
	public void setDszflist(List<Map<String, Object>> dszflist) {
		this.dszflist = dszflist;
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
	public List<Map<String, Object>> getFplist() {
		return fplist;
	}
	public void setFplist(List<Map<String, Object>> fplist) {
		this.fplist = fplist;
	}

	
}
