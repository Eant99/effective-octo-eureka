package com.googosoft.pojo.zccz;

import java.util.Date;

/**
* ZC_CZB 实体类
* 创建时间： 2016-11-04
*@author 
*/ 


public class ZC_CZB{
	private String czbh;//处置编号(sys_guid())
	private String yqbh;//资产编号
	private String bdxm;//处置项目(取值现状)
	private String bdqnr;//处置前内容(对应代码表zl=41)
	private String bdhnr;//处置后内容(对应代码表zl=41)
	private Date czrq;//处置日期
	private String sydw;//使用单位
	private Float dj;//单价
	private String gzrq;//购置日期
	private String czyy;//处置原因
	private String flh;//资产分类号
	private String czbgbh;//处置报告编号
	private String syfx;//使用方向
	private Float zzj;//总价
	private String bdbz;//变动标识(对应czbgb中的ztbz)
	private Float sykh;//数量
	private String jdr;//建档人
	private Date jdrq;//建档日期
	private String yqmc;//资产名称
	public void setCzbh(String CZBH){
	this.czbh=CZBH;
	}
	public String getCzbh(){
		return czbh;
	}
	public void setYqbh(String YQBH){
	this.yqbh=YQBH;
	}
	public String getYqbh(){
		return yqbh;
	}
	public void setBdxm(String BDXM){
	this.bdxm=BDXM;
	}
	public String getBdxm(){
		return bdxm;
	}
	public void setBdqnr(String BDQNR){
	this.bdqnr=BDQNR;
	}
	public String getBdqnr(){
		return bdqnr;
	}
	public void setBdhnr(String BDHNR){
	this.bdhnr=BDHNR;
	}
	public String getBdhnr(){
		return bdhnr;
	}
	public void setCzrq(Date CZRQ){
	this.czrq=CZRQ;
	}
	public Date getCzrq(){
		return czrq;
	}
	public void setSydw(String SYDW){
	this.sydw=SYDW;
	}
	public String getSydw(){
		return sydw;
	}
	public void setDj(Float DJ){
	this.dj=DJ;
	}
	public Float getDj(){
		return dj;
	}
	public void setGzrq(String GZRQ){
	this.gzrq=GZRQ;
	}
	public String getGzrq(){
		return gzrq;
	}
	public void setCzyy(String CZYY){
	this.czyy=CZYY;
	}
	public String getCzyy(){
		return czyy;
	}
	public void setFlh(String FLH){
	this.flh=FLH;
	}
	public String getFlh(){
		return flh;
	}
	public void setCzbgbh(String CZBGBH){
	this.czbgbh=CZBGBH;
	}
	public String getCzbgbh(){
		return czbgbh;
	}
	public void setSyfx(String SYFX){
	this.syfx=SYFX;
	}
	public String getSyfx(){
		return syfx;
	}
	public void setZzj(Float ZZJ){
	this.zzj=ZZJ;
	}
	public Float getZzj(){
		return zzj;
	}
	public void setBdbz(String BDBZ){
	this.bdbz=BDBZ;
	}
	public String getBdbz(){
		return bdbz;
	}
	public void setSykh(Float SYKH){
	this.sykh=SYKH;
	}
	public Float getSykh(){
		return sykh;
	}
	public void setJdr(String JDR){
	this.jdr=JDR;
	}
	public String getJdr(){
		return jdr;
	}
	public void setJdrq(Date JDRQ){
	this.jdrq=JDRQ;
	}
	public Date getJdrq(){
		return jdrq;
	}
	public void setYqmc(String YQMC){
	this.yqmc=YQMC;
	}
	public String getYqmc(){
		return yqmc;
	}
}

