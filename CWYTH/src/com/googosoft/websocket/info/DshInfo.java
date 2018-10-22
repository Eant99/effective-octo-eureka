package com.googosoft.websocket.info;


/**
 * 待审核信息
 * @author 
 *
 */
public class DshInfo {
	private String guid;//业务guid
	private String procinstid;//流程图id
	private String djbh;//单据编号
	private String xm;//申请人姓名
	private String shmc;//审核名称
	private String mkbh;//模块编号
	private String sjmkbh;//上级模块编号
	private String rootmkbh;//根模块编号
	
	public DshInfo() {
		
	}
	
	public DshInfo(String guid, String procinstid, String djbh, String xm, String shmc, String mkbh, String sjmkbh,
			String rootmkbh) {
		super();
		this.guid = guid;
		this.procinstid = procinstid;
		this.djbh = djbh;
		this.xm = xm;
		this.shmc = shmc;
		this.mkbh = mkbh;
		this.sjmkbh = sjmkbh;
		this.rootmkbh = rootmkbh;
	}

	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getDjbh() {
		return djbh;
	}
	public void setDjbh(String djbh) {
		this.djbh = djbh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getShmc() {
		return shmc;
	}
	public void setShmc(String shmc) {
		this.shmc = shmc;
	}
	public String getMkbh() {
		return mkbh;
	}
	public void setMkbh(String mkbh) {
		this.mkbh = mkbh;
	}
	public String getSjmkbh() {
		return sjmkbh;
	}
	public void setSjmkbh(String sjmkbh) {
		this.sjmkbh = sjmkbh;
	}
	public String getRootmkbh() {
		return rootmkbh;
	}
	public void setRootmkbh(String rootmkbh) {
		this.rootmkbh = rootmkbh;
	}

	public String getProcinstid() {
		return procinstid;
	}

	public void setProcinstid(String procinstid) {
		this.procinstid = procinstid;
	}
	
}
