package com.googosoft.pojo.wsbx;

/**
 * 审核意见表
 * @author master
 */
public class OA_SHYJB {

	private String gid;//主键
	private String rybh;//审核人人员编号
	private String procinstid;//流程实例id
	
	private String shyj;//审核意见
	private String shzt;//审核状态
	private String taskid;//任务ID
	public String getShzt() {
		return shzt;
	}
	public void setShzt(String shzt) {
		this.shzt = shzt;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getRybh() {
		return rybh;
	}
	public void setRybh(String rybh) {
		this.rybh = rybh;
	}
	public String getProcinstid() {
		return procinstid;
	}
	public void setProcinstid(String procinstid) {
		this.procinstid = procinstid;
	}
	public String getShyj() {
		return shyj;
	}
	public void setShyj(String shyj) {
		this.shyj = shyj;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	

}
