package com.googosoft.pojo.xztj;

/**
* ZC_XZTJ_LYSQMXB 实体类
* 创建时间： 2016-12-14
*@author master
*/ 


public class ZC_XZTJ_LYSQMXB{
	private String guid;
	private String sqbh;
	private String yqbh;
	private String yqmc;
	private String syr;
	private String sydw;
	private String syfx;
	private float dj;
	private float zzj;
	private float tpje;
	private String ztbz;
	private float cjje;
	public void setGuid(String GUID){
	this.guid=GUID;
	}
	/**
	 * 主键
	 * @return
	 */
	public String getGuid(){
		return guid;
	}
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
	public void setYqbh(String YQBH){
	this.yqbh=YQBH;
	}
	/**
	 * 资产编号
	 * @return
	 */
	public String getYqbh(){
		return yqbh;
	}
	public void setYqmc(String YQMC){
	this.yqmc=YQMC;
	}
	/**
	 * 资产名称
	 * @return
	 */
	public String getYqmc(){
		return yqmc;
	}
	public void setSyr(String SYR){
	this.syr=SYR;
	}
	/**
	 * 使用人
	 * @return
	 */
	public String getSyr(){
		return syr;
	}
	public void setSydw(String SYDW){
	this.sydw=SYDW;
	}
	/**
	 * 使用单位
	 * @return
	 */
	public String getSydw(){
		return sydw;
	}
	public void setSyfx(String SYFX){
	this.syfx=SYFX;
	}
	/**
	 * 使用方向
	 * @return
	 */
	public String getSyfx(){
		return syfx;
	}
	public void setDj(float DJ){
	this.dj=DJ;
	}
	/**
	 * 单价
	 * @return
	 */
	public float getDj(){
		return dj;
	}
	public void setZzj(float ZZJ){
	this.zzj=ZZJ;
	}
	/**
	 * 总价
	 * @return
	 */
	public float getZzj(){
		return zzj;
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
	public void setCjje(float CJJE){
	this.cjje=CJJE;
	}
	/**
	 * 成交金额
	 * @return
	 */
	public float getCjje(){
		return cjje;
	}
}

