package com.googosoft.pojo;

import java.util.Map;

public class TaskModel {
	private String lastForm;
	private Map<String, String> formData;
	private String processInstanceId;
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getLastForm() {
		return lastForm;
	}
	public void setLastForm(String lastForm) {
		this.lastForm = lastForm;
	}
	public Map<String, String> getFormData() {
		return formData;
	}
	public void setFormData(Map<String, String> formData) {
		this.formData = formData;
	}
	

}
