package com.googosoft.pojo.systemset.cssz;

import java.io.Serializable;
import java.util.Date;

/**
* ZC_SYS_XTB 实体类
* 创建时间： 2016-10-29
*@author master
*/
public class ZC_SYS_XTB  implements Serializable{
	private String guid;  //主键
	private Integer zzbh;   //组织编号
	private Integer cgbh;   //账表统计是卡片数还是套件数
	private Integer tmcd;  //条形码长度
	private String td;  //套打
	private Float gzyq;  //贵重仪器价值起点
	private Date bfrq;  //备份日期
	private Integer bfts;  //备份天数
	private Integer nfcd;   //主机编号: 年份长度(资产流水最小值)
	private Integer nfqd;   //主机编号: 年份起点(资产流水最大值)
	private Integer lscd;   //主机编号: 流水长度(验收单流水最小值)
	private Integer lsqd;  //主机编号: 流水起点(验收单流水最小值)
	private Integer blcd;  //主机编号: 保留长度(人员流水最小值)
	private Integer blqd;  //主机编号: 保留起点(人员流水最小值)
	private String blqs;  //主机编号: 保留缺省值
	private String xxdm;   //学校代码
	private Integer dwlsfrom;  //单位流水下限
	private Integer dwlsto;   //单位流水上限
	private Integer bdlsfrom;  //变动流水下限
	private Integer bdlsto;   //变动流水上限
	private Integer czlsfrom;  //处置流水下限
	private Integer czlsto;  //处置流水上限
	private String yshdtd; //验收单套打模板
	private String zjbtd;   //卡片是否套打((1:套打，0：不套打))
	private String bdtd;   //变动单是否套打((1:套打，0：不套打))
	private String cztd;   //处置单是否套打((1:套打，0：不套打))
	private Integer zxs;   //在校生数
	private String xxlx;  //学校类型
	private String kpmb;  //卡片打印模板
	private String ysdmb;  //验收单打印模板
	private String tmmb;   //条形码模板
	private String sfmj;  //资产分类是否到末级
	private String mutisearchfirst;  //查询时 是否先展示综合查询条件(是：1； 否：0)
	private String sftjzmj;  //资产使用单位是否添加到末级
	private Integer dwcd;  //单位编号长度
	private Integer ddcd;  //地点编号长度
	private Integer rycd;  //人员编号长度
	private Integer rownums;  //列表每页显示行数
	private String yzdny;   //折旧已计提到年月
	private String qyny;   //折旧启用年用
	private String cxdc;   //查询设置 (默认设置:1,不允许导出excel:0,不允许打印:2,既不允许导出也不允许打印:3)
	private String saasdm;  //组织结构代码
	private String czsbqybz;  //处置申报系统是否启用(是：1； 否：0)
	private String zjff; //折旧使用的分类
	private String sfqy; //是否启用折旧(是：1； 否：0)
	private String ksdh; //是否启用管理员快速导航(是：1； 否：0)
	private String mail; 
	private String smtp; 
	private String yhm; 
	private String mm; 
	public void setBfrq(Date bfrq) {
		this.bfrq = bfrq;
	}
	public String getKsdh() {
		return ksdh;
	}
	public void setKsdh(String ksdh) {
		this.ksdh = ksdh;
	}
	public Date getBfrq() {
		return bfrq;
	}
	public void setGuid(String GUID){
	this.guid=GUID;
	}
	public String getGuid(){
		return guid;
	}
	public void setZzbh(Integer ZZBH){
		this.zzbh=ZZBH;
	}
	public Integer getZzbh(){
		return zzbh;
	}
	public void setCgbh(Integer CGBH){
		this.cgbh=CGBH;
	}
	public Integer getCgbh(){
		return cgbh;
	}
	public void setTmcd(Integer TMCD){
		this.tmcd=TMCD;
	}
	public Integer getTmcd(){
		return tmcd;
	}
	public void setTd(String TD){
		this.td=TD;
	}
	public String getTd(){
		return td;
	}
	public void setGzyq(Float GZYQ){
		this.gzyq=GZYQ;
	}
	public Float getGzyq(){
		return gzyq;
	}
	public void setBfts(Integer BFTS){
		this.bfts=BFTS;
	}
	public Integer getBfts(){
		return bfts;
	}
	public void setNfcd(Integer NFCD){
		this.nfcd=NFCD;
	}
	public Integer getNfcd(){
		return nfcd;
	}
	public void setNfqd(Integer NFQD){
		this.nfqd=NFQD;
	}
	public Integer getNfqd(){
		return nfqd;
	}
	public void setLscd(Integer LSCD){
	this.lscd=LSCD;
	}
	public Integer getLscd(){
		return lscd;
	}
	public void setLsqd(Integer LSQD){
		this.lsqd=LSQD;
	}
	public Integer getLsqd(){
		return lsqd;
	}
	public void setBlcd(Integer BLCD){
		this.blcd=BLCD;
	}
	public Integer getBlcd(){
		return blcd;
	}
	public void setBlqd(Integer BLQD){
		this.blqd=BLQD;
	}
	public Integer getBlqd(){
		return blqd;
	}
	public void setBlqs(String BLQS){
		this.blqs=BLQS;
	}
	public String getBlqs(){
		return blqs;
	}
	public void setXxdm(String XXDM){
	this.xxdm=XXDM;
	}
	public String getXxdm(){
		return xxdm;
	}
	public void setDwlsfrom(Integer DWLSFROM){
	this.dwlsfrom=DWLSFROM;
	}
	public Integer getDwlsfrom(){
		return dwlsfrom;
	}
	public void setDwlsto(Integer DWLSTO){
	this.dwlsto=DWLSTO;
	}
	public Integer getDwlsto(){
		return dwlsto;
	}
	public void setBdlsfrom(Integer BDLSFROM){
	this.bdlsfrom=BDLSFROM;
	}
	public Integer getBdlsfrom(){
		return bdlsfrom;
	}
	public void setBdlsto(Integer BDLSTO){
	this.bdlsto=BDLSTO;
	}
	public Integer getBdlsto(){
		return bdlsto;
	}
	public void setCzlsfrom(Integer CZLSFROM){
	this.czlsfrom=CZLSFROM;
	}
	public Integer getCzlsfrom(){
		return czlsfrom;
	}
	public void setCzlsto(Integer CZLSTO){
	this.czlsto=CZLSTO;
	}
	public Integer getCzlsto(){
		return czlsto;
	}
	public void setYshdtd(String YSHDTD){
	this.yshdtd=YSHDTD;
	}
	public String getYshdtd(){
		return yshdtd;
	}
	public void setZjbtd(String ZJBTD){
	this.zjbtd=ZJBTD;
	}
	public String getZjbtd(){
		return zjbtd;
	}
	public void setBdtd(String BDTD){
	this.bdtd=BDTD;
	}
	public String getBdtd(){
		return bdtd;
	}
	public void setCztd(String CZTD){
	this.cztd=CZTD;
	}
	public String getCztd(){
		return cztd;
	}
	public void setZxs(Integer ZXS){
		this.zxs=ZXS;
	}
	public Integer getZxs(){
		return zxs;
	}
	public void setXxlx(String XXLX){
	this.xxlx=XXLX;
	}
	public String getXxlx(){
		return xxlx;
	}
	public void setKpmb(String KPMB){
	this.kpmb=KPMB;
	}
	public String getKpmb(){
		return kpmb;
	}
	public void setYsdmb(String YSDMB){
	this.ysdmb=YSDMB;
	}
	public String getYsdmb(){
		return ysdmb;
	}
	public void setTmmb(String TMMB){
	this.tmmb=TMMB;
	}
	public String getTmmb(){
		return tmmb;
	}
	public void setSfmj(String SFMJ){
	this.sfmj=SFMJ;
	}
	public String getSfmj(){
		return sfmj;
	}
	public void setMutisearchfirst(String MUTISEARCHFIRST){
	this.mutisearchfirst=MUTISEARCHFIRST;
	}
	public String getMutisearchfirst(){
		return mutisearchfirst;
	}
	public void setSftjzmj(String SFTJZMJ){
	this.sftjzmj=SFTJZMJ;
	}
	public String getSftjzmj(){
		return sftjzmj;
	}
	public void setDwcd(Integer DWCD){
	this.dwcd=DWCD;
	}
	public Integer getDwcd(){
		return dwcd;
	}
	public void setDdcd(Integer DDCD){
	this.ddcd=DDCD;
	}
	public Integer getDdcd(){
		return ddcd;
	}
	public void setRycd(Integer RYCD){
	this.rycd=RYCD;
	}
	public Integer getRycd(){
		return rycd;
	}
	public void setRownums(Integer ROWNUMS){
	this.rownums=ROWNUMS;
	}
	public Integer getRownums(){
		return rownums;
	}
	public void setYzdny(String YZDNY){
	this.yzdny=YZDNY;
	}
	public String getYzdny(){
		return yzdny;
	}
	public void setQyny(String QYNY){
	this.qyny=QYNY;
	}
	public String getQyny(){
		return qyny;
	}
	public void setCxdc(String CXDC){
	this.cxdc=CXDC;
	}
	public String getCxdc(){
		return cxdc;
	}
	public void setSaasdm(String SAASDM){
	this.saasdm=SAASDM;
	}
	public String getSaasdm(){
		return saasdm;
	}
	public void setCzsbqybz(String CZSBQYBZ){
	this.czsbqybz=CZSBQYBZ;
	}
	public String getCzsbqybz(){
		return czsbqybz;
	}
	public void setZjff(String ZJFF){
	this.zjff=ZJFF;
	}
	public String getZjff(){
		return zjff;
	}
	public void setSfqy(String SFQY){
	this.sfqy=SFQY;
	}
	public String getSfqy(){
		return sfqy;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public String getYhm() {
		return yhm;
	}
	public void setYhm(String yhm) {
		this.yhm = yhm;
	}
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
}

