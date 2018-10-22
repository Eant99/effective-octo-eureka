package com.googosoft.modules.app.pojo;

import java.util.List;
import java.util.Map;

public class Syss {
	
	private String msg;//返回提示信息
	private Boolean success;//返回true或者false
	private List<Map<String,Object>> items;//出差方式list
	private String userId;
	private String ywlx;//业务类型：0、全部；1、出差申请；2、日常报销；3、出差报销；4、公务接待；5、接待报销
	private int index;//分页
	private String keyword;//搜索关键字
	
	private String bh;//搜索关键字
	private String title;//搜索关键字
	private String time;//搜索关键字
	private String sqr;//搜索关键字
	private String state;//状态（用来区分类型的，注意：1是出差申请审批；2是日常报销审批；3是差旅费报销审批；4是公务接待申请审批；5是公务接待报销审批
	
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public Syss(String msg, Boolean success, List<Map<String, Object>> items, String userId, String ywlx, int index,
			String keyword, String bh, String title, String time, String sqr, String state) {
		super();
		this.msg = msg;
		this.success = success;
		this.items = items;
		this.userId = userId;
		this.ywlx = ywlx;
		this.index = index;
		this.keyword = keyword;
		this.bh = bh;
		this.title = title;
		this.time = time;
		this.sqr = sqr;
		this.state = state;
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

	public List<Map<String, Object>> getItems() {
		return items;
	}

	public void setItems(List<Map<String, Object>> items) {
		this.items = items;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSqr() {
		return sqr;
	}

	public void setSqr(String sqr) {
		this.sqr = sqr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getYwlx() {
		return ywlx;
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	public Syss(String userId, String ywlx, int index, String keyword) {
		super();
		this.userId = userId;
		this.ywlx = ywlx;
		this.index = index;
		this.keyword = keyword;
	}
	public int getIndex() {
		return index;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Syss() {
		super();
	}
	
	
	
}
