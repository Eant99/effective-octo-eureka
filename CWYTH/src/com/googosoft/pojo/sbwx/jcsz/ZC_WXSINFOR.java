package com.googosoft.pojo.sbwx.jcsz;

import java.util.Date;

/**
* ZC_WXSINFOR 实体类
* 创建时间： 2016-11-14
*@author master
*/ 


public class ZC_WXSINFOR{
	private String gsbh;
	private String mc;
	private String fr;
	private String phone;
	private String address;
	private String buss;
	private String zjr;
	private Date zjsj;
	private String lxr;
	/**
	 * 维修商编号
	 * @param GSBH
	 */
	public void setGsbh(String GSBH){
	this.gsbh=GSBH;
	}
	/**
	 * 维修商编号
	 * @return
	 */
	public String getGsbh(){
		return gsbh;
	}
	/**
	 * 维修商名称
	 * @param MC
	 */
	public void setMc(String MC){
	this.mc=MC;
	}
	/**
	 * 维修商名称
	 * @return
	 */
	
	public String getMc(){
		return mc;
	}
	/**
	 * 法人
	 * @param FR
	 */
	public void setFr(String FR){
	this.fr=FR;
	}
	/**
	 * 法人
	 * @return
	 */
	public String getFr(){
		return fr;
	}
	/**
	 * 联系电话
	 * @param PHONE
	 */
	public void setPhone(String PHONE){
	this.phone=PHONE;
	}
	/**
	 * 联系电话
	 * @return
	 */
	public String getPhone(){
		return phone;
	}
	/**
	 * 地址
	 * @param ADDRESS
	 */
	public void setAddress(String ADDRESS){
	this.address=ADDRESS;
	}
	/**
	 * 地址
	 * @return
	 */
	public String getAddress(){
		return address;
	}
	/**
	 * 经营范围
	 * @param BUSS
	 */
	public void setBuss(String BUSS){
	this.buss=BUSS;
	}
	/**
	 * 经营范围
	 * @return
	 */
	public String getBuss(){
		return buss;
	}
	/**
	 * 添加人
	 * @param ZJR
	 */
	public void setZjr(String ZJR){
	this.zjr=ZJR;
	}
	/**
	 * 添加人
	 * @return
	 */
	public String getZjr(){
		return zjr;
	}
	/**
	 * 添加时间
	 * @param ZJSJ
	 */
	public void setZjsj(Date ZJSJ){
	this.zjsj=ZJSJ;
	}
	/**
	 * 添加时间
	 * @return
	 */
	public Date getZjsj(){
		return zjsj;
	}
	/**
	 * 联系人
	 * @param LXR
	 */
	public void setLxr(String LXR){
	this.lxr=LXR;
	}
	/**
	 * 联系人
	 * @return
	 */
	public String getLxr(){
		return lxr;
	}
}

