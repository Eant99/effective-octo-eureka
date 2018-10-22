package com.googosoft.pojo.fzgn.jcsz;

/**
 * 岗位交接实体类
 * @author RC 2017-08-30
 */
public class GX_SYS_GWJJB {
	private String gid;//主键（32位guid）
	private String ywfqr;//业务发起人
	private String ywfqrdh;//业务发起人电话
	private String yqxsyr;//原权限所有人
	private String yqxsyrdh;//原权限所有人电话
	private String jgr;//接岗人
	private String jgrdh;//接岗人电话
	private String gwjjyy;//岗位交接原因
	
	/**
	 * 获取gid（主键）
	 * @return
	 */
	public String getGid() {
		return gid;
	}
	/**
	 * 设置gid（主键）
	 * @param gid
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}
	/**
	 * 获取业务发起人
	 * @return
	 */
	public String getYwfqr() {
		return ywfqr;
	}
	/**
	 * 设置业务发起人
	 * @param ywfqr
	 */
	public void setYwfqr(String ywfqr) {
		this.ywfqr = ywfqr;
	}
	/**
	 * 获取业务发起人电弧
	 * @return
	 */
	public String getYwfqrdh() {
		return ywfqrdh;
	}
	/**
	 * 设置业务发起人电话
	 * @param ywfqrdh
	 */
	public void setYwfqrdh(String ywfqrdh) {
		this.ywfqrdh = ywfqrdh;
	}
	/**
	 * 获取原权限所有人
	 * @return
	 */
	public String getYqxsyr() {
		return yqxsyr;
	}
	/**
	 * 设置原权限所有人
	 * @param yqxsyr
	 */
	public void setYqxsyr(String yqxsyr) {
		this.yqxsyr = yqxsyr;
	}
	/**
	 * 获取原权限所有人电话
	 * @return
	 */
	public String getYqxsyrdh() {
		return yqxsyrdh;
	}
	/**
	 * 设置原权限所有人电话
	 * @param yqxsyrdh
	 */
	public void setYqxsyrdh(String yqxsyrdh) {
		this.yqxsyrdh = yqxsyrdh;
	}
	/**
	 * 获取接岗人
	 * @return
	 */
	public String getJgr() {
		return jgr;
	}
	/**
	 * 设置接岗人
	 * @param jgr
	 */
	public void setJgr(String jgr) {
		this.jgr = jgr;
	}
	/**
	 * 获取接岗人电话
	 * @return
	 */
	public String getJgrdh() {
		return jgrdh;
	}
	/**
	 * 设置接岗人
	 * @param jgrdh
	 */
	public void setJgrdh(String jgrdh) {
		this.jgrdh = jgrdh;
	}
	/**
	 * 获取岗位交接原因
	 * @return
	 */
	public String getGwjjyy() {
		return gwjjyy;
	}
	/**
	 * 设置岗位交接原因
	 * @param gwjjyy
	 */
	public void setGwjjyy(String gwjjyy) {
		this.gwjjyy = gwjjyy;
	}
}
