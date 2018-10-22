package com.googosoft.pojo.zcqc;

import java.util.Date;

/**
* ZC_ZCZCB 实体类
* 创建时间： 2016-12-06
*@author 
*/ 


public class ZC_ZCZCB{
	private String qcbh;
	private String yqbh;
	private String yqmc;
	private String flh;
	private String flmc;
	private String xz;
	private String sydw;
	private String syr;
	private Date gzrq;
	private float dj;
	private String cz10;
	private String cz6;
	private String txm;
	private String qcqk;
	private String syzk;
	private float yynx;
	private float jzmj;
	private float symj;
	private float czmj;
	private String qcyt;
	private String pzhm;
	private String pql;
	private float gls;
	private String pyyy;
	private String pkyy;
	private String bfyy;
	private String qczt;
	private String shr;
	private Date shsj;
	private String shyj;
	
	public void setQcbh(String QCBH){
	this.qcbh=QCBH;
	}
	/**
	 * 清查编号(对应zc_zczczt中的qcbh)
	 * @return
	 */
	public String getQcbh(){
		return qcbh;
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
	public void setFlh(String FLH){
	this.flh=FLH;
	}
	/**
	 * 分类号
	 * @return
	 */
	public String getFlh(){
		return flh;
	}
	public void setFlmc(String FLMC){
	this.flmc=FLMC;
	}
	/**
	 * 分类名称
	 * @return
	 */
	public String getFlmc(){
		return flmc;
	}
	public void setXz(String XZ){
	this.xz=XZ;
	}
	/**
	 * 现状(对应代码表zl=03)
	 * @return
	 */
	public String getXz(){
		return xz;
	}
	public void setSydw(String SYDW){
	this.sydw=SYDW;
	}
	/**
	 * 使用单位
	 * @param SYDW
	 */
	public String getSydw(){
		return sydw;
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
	public void setGzrq(Date GZRQ){
	this.gzrq=GZRQ;
	}
	/**
	 * 购置日期
	 * @return
	 */
	public Date getGzrq(){
		return gzrq;
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
	public void setCz10(String CZ10){
	this.cz10=CZ10;
	}
	/**
	 * 财政10大类分类号
	 * @return
	 */
	public String getCz10(){
		return cz10;
	}
	public void setCz6(String CZ6){
	this.cz6=CZ6;
	}
	/**
	 * 财政6大类分类号
	 * @return
	 */
	public String getCz6(){
		return cz6;
	}
	public void setTxm(String TXM){
	this.txm=TXM;
	}
	public String getTxm(){
		return txm;
	}
	public void setQcqk(String QCQK){
	this.qcqk=QCQK;
	}
	/**
	 * 清查情况(1：相符，2：不符（注意：清查情况不符的时候提交时更新qczt为1,进入审核流程，符合的直接将qczt更为5）)
	 * @return
	 */
	public String getQcqk(){
		return qcqk;
	}
	public void setSyzk(String SYZK){
	this.syzk=SYZK;
	}
	/**
	 * 使用状况(对应代码表zl=33)
	 * @return
	 */
	public String getSyzk(){
		return syzk;
	}
	public void setYynx(float YYNX){
	this.yynx=YYNX;
	}
	public float getYynx(){
		return yynx;
	}
	public void setJzmj(float JZMJ){
	this.jzmj=JZMJ;
	}
	public float getJzmj(){
		return jzmj;
	}
	public void setSymj(float SYMJ){
	this.symj=SYMJ;
	}
	public float getSymj(){
		return symj;
	}
	public void setCzmj(float CZMJ){
	this.czmj=CZMJ;
	}
	public float getCzmj(){
		return czmj;
	}
	public void setQcyt(String QCYT){
	this.qcyt=QCYT;
	}
	public String getQcyt(){
		return qcyt;
	}
	public void setPzhm(String PZHM){
	this.pzhm=PZHM;
	}
	public String getPzhm(){
		return pzhm;
	}
	public void setPql(String PQL){
	this.pql=PQL;
	}
	public String getPql(){
		return pql;
	}
	public void setGls(float GLS){
	this.gls=GLS;
	}
	public float getGls(){
		return gls;
	}
	public void setPyyy(String PYYY){
	this.pyyy=PYYY;
	}
	/**
	 * 盘盈原因
	 * @return
	 */
	public String getPyyy(){
		return pyyy;
	}
	public void setPkyy(String PKYY){
	this.pkyy=PKYY;
	}
	/**
	 * 盘亏原因
	 * @return
	 */
	public String getPkyy(){
		return pkyy;
	}
	public void setBfyy(String BFYY){
	this.bfyy=BFYY;
	}
	/**
	 * 不符原因
	 * @return
	 */
	public String getBfyy(){
		return bfyy;
	}
	public void setQczt(String QCZT){
	this.qczt=QCZT;
	}
	/**
	 * 清查状态(0：未提交，1：已提交，2：审核不通过，5：审核通过)
	 * @return
	 */
	public String getQczt(){
		return qczt;
	}
	public void setShr(String SHR){
	this.shr=SHR;
	}
	/**
	 * 审核人
	 * @return
	 */
	public String getShr(){
		return shr;
	}
	public void setShsj(Date SHSJ){
	this.shsj=SHSJ;
	}
	/**
	 * 审核时间
	 * @return
	 */
	public Date getShsj(){
		return shsj;
	}
	public void setShyj(String SHYJ){
	this.shyj=SHYJ;
	}
	/**
	 * 审核意见
	 * @return
	 */
	public String getShyj(){
		return shyj;
	}
}

