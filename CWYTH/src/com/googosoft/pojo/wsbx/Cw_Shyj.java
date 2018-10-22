/**
 * 
 */
package com.googosoft.pojo.wsbx;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.googosoft.commons.LUser;

/**
 * @author lifei
 * @date 2018-4-3
 * @title Cw_Shyj.java
 */
public class Cw_Shyj implements Serializable {
	@Override
	public String toString() {
		return "Cw_Shyj [guid=" + guid + ", czr=" + czr + ", shlx=" + shlx
				+ ", shyj=" + shyj + ", title=" + title + ", czrq=" + czrq
				+ "]";
	}

	private String guid;
	private final String czr = LUser.getGuid();
	private String shlx;
	private String shyj;
	private String title;
	private final String czrq = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	public String getCzrq() {
		return czrq;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCzr() {
		return czr;
	}

	public String getShlx() {
		return shlx;
	}

	public void setShlx(String shlx) {
		this.shlx = shlx;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
