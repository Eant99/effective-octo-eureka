package com.googosoft.pojo.systemset.cssz;

/**
* PT_SYS_XTCSB 实体类
* 创建时间： 2016-11-30
* @author master
*/ 
public class SYS_XTCSB{
	private String dlfs;//登录方式
	private String dxyz;//短信验证
	private String yzmlx;//验证码类型
	private String xtmc;//系统名称
	private String jszc;//
	private String lxdh;//联系电话
	private String email;//电子邮箱
	private String lxdz;//联系地址
	private String bgimg;//背景img
	private String logoimg;
	private String xtsm;//系统说明
	private String content;//内容
	private String dsfdl;//第三方登录
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getXtsm() {
		return xtsm;
	}
	public void setXtsm(String xtsm) {
		this.xtsm = xtsm;
	}
	
	public String getDlfs() {
		return dlfs;
	}
	public void setDlfs(String dlfs) {
		this.dlfs = dlfs;
	}
	public void setDxyz(String DXYZ){
	this.dxyz=DXYZ;
	}
	public String getDxyz(){
		return dxyz;
	}
	public void setYzmlx(String YZMLX){
	this.yzmlx=YZMLX;
	}
	public String getYzmlx(){
		return yzmlx;
	}
	public void setXtmc(String XTMC){
	this.xtmc=XTMC;
	}
	public String getXtmc(){
		return xtmc;
	}
	public void setJszc(String JSZC){
	this.jszc=JSZC;
	}
	public String getJszc(){
		return jszc;
	}
	public void setLxdh(String LXDH){
	this.lxdh=LXDH;
	}
	public String getLxdh(){
		return lxdh;
	}
	public void setEmail(String EMAIL){
	this.email=EMAIL;
	}
	public String getEmail(){
		return email;
	}
	public void setLxdz(String LXDZ){
	this.lxdz=LXDZ;
	}
	public String getLxdz(){
		return lxdz;
	}
	public void setBgimg(String BGIMG){
	this.bgimg=BGIMG;
	}
	public String getBgimg(){
		return bgimg;
	}
	public void setLogoimg(String LOGOIMG){
	this.logoimg=LOGOIMG;
	}
	public String getLogoimg(){
		return logoimg;
	}
	public String getDsfdl() {
		return dsfdl;
	}
	public void setDsfdl(String dsfdl) {
		this.dsfdl = dsfdl;
	}
}

