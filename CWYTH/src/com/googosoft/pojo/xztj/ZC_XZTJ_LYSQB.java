package com.googosoft.pojo.xztj;

import java.util.Date;

/**
* ZC_XZTJ_LYSQB 实体类
* 创建时间： 2016-12-14
*@author master
*/ 
public class ZC_XZTJ_LYSQB{
	private String sqbh;
	private String sqry;
	private String sqdw;
	private Date sqrq;
	private String sqyy;
	private String bz;
	private String ztbz;
	private String zgshr;
	private Date zgshsj;
	private String gkshr;
	private String gkshyj;
	private Date gkshsj;
	private float tpje;
	private String zgshyj;
	public void setSqbh(String SQBH){
	this.sqbh=SQBH;
	}
	/**
	 * 申请编号
	 * @return
	 */
	public String getSqbh(){
		return sqbh;
	}
	public void setSqry(String SQRY){
	this.sqry=SQRY;
	}
	/**
	 * 申请人员
	 * @return
	 */
	public String getSqry(){
		return sqry;
	}
	public void setSqdw(String SQDW){
	this.sqdw=SQDW;
	}
	/**
	 * 申请单位
	 * @return
	 */
	public String getSqdw(){
		return sqdw;
	}
	public void setSqyy(String SQYY){
	this.sqyy=SQYY;
	}
	/**
	 * 申请原因
	 * @return
	 */
	public String getSqyy(){
		return sqyy;
	}
	public void setBz(String BZ){
	this.bz=BZ;
	}
	/**
	 * 备注
	 * @return
	 */
	public String getBz(){
		return bz;
	}
	public void setZtbz(String ZTBZ){
	this.ztbz=ZTBZ;
	}
	/**
	 * 状态标识
	 * @return
	 */
	public String getZtbz(){
		return ztbz;
	}
	public void setZgshr(String ZGSHR){
	this.zgshr=ZGSHR;
	}
	/**
	 * 主管审核人
	 * @return
	 */
	public String getZgshr(){
		return zgshr;
	}
	public void setGkshr(String GKSHR){
	this.gkshr=GKSHR;
	}
	/**
	 * 归口审核人
	 * @return
	 */
	public String getGkshr(){
		return gkshr;
	}
	public void setGkshyj(String GKSHYJ){
	this.gkshyj=GKSHYJ;
	}
	/**
	 * 归口审核意见
	 * @return
	 */
	public String getGkshyj(){
		return gkshyj;
	}
	public void setTpje(float TPJE){
	this.tpje=TPJE;
	}
	/**
	 * 谈判金额
	 * @return
	 */
	public float getTpje(){
		return tpje;
	}
	public void setZgshyj(String ZGSHYJ){
	this.zgshyj=ZGSHYJ;
	}
	/**
	 * 主管审核意见
	 * @return
	 */
	public String getZgshyj(){
		return zgshyj;
	}
	/**
	 * 申请日期
	 * @return
	 */
	public Date getSqrq() {
		return sqrq;
	}
	public void setSqrq(Date sqrq) {
		this.sqrq = sqrq;
	}
	/**
	 * 主管审核日期
	 * @return
	 */
	public Date getZgshsj() {
		return zgshsj;
	}
	public void setZgshsj(Date zgshsj) {
		this.zgshsj = zgshsj;
	}
	/**
	 * 归口审核日期
	 * @return
	 */
	public Date getGkshsj() {
		return gkshsj;
	}
	public void setGkshsj(Date gkshsj) {
		this.gkshsj = gkshsj;
	}
}

