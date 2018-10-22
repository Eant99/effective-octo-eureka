package com.googosoft.pojo.systemset.cssz;

import java.util.Date;

/**
* ZC_SYS_TOOLDOWN 实体类
* 创建时间： 2016-12-08
*@author master
*/ 


public class ZC_SYS_TOOLDOWN{
	private String guid;
	private String wjlx;
	private String wjmc;
	private String fname;
	private String wjms;
	private Date zjsj;
	private String zjr;
	private String path;
	
	/**路径
	 * @return
	 */
	public String getPath() {
		return path;
	}
	/**路径
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**编号
	 * @param GUID
	 */
	public void setGuid(String GUID){
	this.guid=GUID;
	}
	/**编号
	 * @return
	 */
	public String getGuid(){
		return guid;
	}
	/**文件类型
	 * @param WJLX
	 */
	public void setWjlx(String WJLX){
	this.wjlx=WJLX;
	}
	/**文件类型
	 * @return
	 */
	public String getWjlx(){
		return wjlx;
	}
	/**文件名称
	 * @param WJMC
	 */
	public void setWjmc(String WJMC){
	this.wjmc=WJMC;
	}
	/**文件名称
	 * @return
	 */
	public String getWjmc(){
		return wjmc;
	}
	/**实际文件名
	 * @param FNAME
	 */
	public void setFname(String FNAME){
	this.fname=FNAME;
	}
	/**实际文件名
	 * @return
	 */
	public String getFname(){
		return fname;
	}
	/**文件描述
	 * @param WJMS
	 */
	public void setWjms(String WJMS){
	this.wjms=WJMS;
	}
	/**文件描述
	 * @return
	 */
	public String getWjms(){
		return wjms;
	}
	/**添加时间
	 * @param ZJSJ
	 */
	public void setZjsj(Date ZJSJ){
	this.zjsj=ZJSJ;
	}
	/**添加时间
	 * @return
	 */
	public Date getZjsj(){
		return zjsj;
	}
	/**添加人
	 * @param ZJR
	 */
	public void setZjr(String ZJR){
	this.zjr=ZJR;
	}
	/**添加人
	 * @return
	 */
	public String getZjr(){
		return zjr;
	}
}

