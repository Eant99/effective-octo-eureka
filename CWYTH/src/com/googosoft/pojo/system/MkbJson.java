package com.googosoft.pojo.system;
/**
 * 首页按钮json数据实体类
 * 
 */
public class MkbJson {
	private String isleaf,//是否为子节点
	levels,//层级（目前系统一共三级）
	mclass,//样式
	mcolor,//
	mfix,//
	mgid,//节点编号
	mname,//节点名称
	mparent,//父级节点（如果为顶级，则为0）
	murl;//地址

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getMclass() {
		return mclass;
	}

	public void setMclass(String mclass) {
		this.mclass = mclass;
	}

	public String getMcolor() {
		return mcolor;
	}

	public void setMcolor(String mcolor) {
		this.mcolor = mcolor;
	}

	public String getMfix() {
		return mfix;
	}

	public void setMfix(String mfix) {
		this.mfix = mfix;
	}

	public String getMgid() {
		return mgid;
	}

	public void setMgid(String mgid) {
		this.mgid = mgid;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getMparent() {
		return mparent;
	}

	public void setMparent(String mparent) {
		this.mparent = mparent;
	}

	public String getMurl() {
		return murl;
	}

	public void setMurl(String murl) {
		this.murl = murl;
	}
}
