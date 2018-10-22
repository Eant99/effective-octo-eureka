package com.googosoft.pojo.systemset.xtsz;

import java.util.UUID;

import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

public class ZC_CJWT {
	private String guid;
	private String title;
	private String xx;
	private String upddate;
	private String jdr;
	private String jdrq;
	public String getGuid() {
		if(Validate.isNull(guid))
			this.guid=UuidUtil.get32UUID();
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getXx() {
		return xx;
	}
	public void setXx(String xx) {
		this.xx = xx;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getJdr() {
		return jdr;
	}
	public void setJdr(String jdr) {
		this.jdr = jdr;
	}
	public String getJdrq() {
		return jdrq;
	}
	public void setJdrq(String jdrq) {
		this.jdrq = jdrq;
	}
	

}
