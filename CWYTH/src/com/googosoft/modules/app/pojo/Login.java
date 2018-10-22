package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;

/**
*@author 杨超超
*@date   2018年3月1日---上午9:00:59
*/
public class Login {
	
		private String  msg;         //返回提示信息
		private Boolean  success;    //返回true或false（true:成功，false：失败）
		private List<Map<String,Object>> items;//返回数据信息集合
		private String rybh;//人员编号
		private String userId;//人员guid
		private String name;//人员姓名
		private String department;//部门名称
		private String saas;//部门号
		private String sex;//性别
		private String tximg;//头像url
		
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
		public List<Map<String, Object>> getItems() {
			return items;
		}
		public void setItems(List<Map<String, Object>> items) {
			this.items = items;
		}
		public String getRybh() {
			return rybh;
		}
		public void setRybh(String rybh) {
			this.rybh = rybh;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getSaas() {
			return saas;
		}
		public void setSaas(String saas) {
			this.saas = saas;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getTximg() {
			return tximg;
		}
		public void setTximg(String tximg) {
			this.tximg = tximg;
		}
		
}
