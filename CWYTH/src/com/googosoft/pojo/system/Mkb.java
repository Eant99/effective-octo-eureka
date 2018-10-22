package com.googosoft.pojo.system;

import java.util.List;

/**
* MKB 实体类
* 创建时间： 2016-09-22
*@author master
*/ 
public class Mkb{
	
	
	private String mkbh;
	private String mkmc;
	private String url;
	private int xh;
	private String qxbz;
	private String icon;
	private String xtbz;
	private int czqx;
	private List<Mkb> childMkbs;
	
	
	public void setMkbh(String MKBH){
	this.mkbh=MKBH;
	}
	public String getMkbh(){
		return mkbh;
	}
	public void setMkmc(String MKMC){
	this.mkmc=MKMC;
	}
	public String getMkmc(){
		return mkmc;
	}
	public void setUrl(String URL){
	this.url=URL;
	}
	public String getUrl(){
		return url;
	}
	public void setQxbz(String QXBZ){
	this.qxbz=QXBZ;
	}
	public String getQxbz(){
		return qxbz;
	}
	public void setIcon(String ICON){
	this.icon=ICON;
	}
	public String getIcon(){
		return icon;
	}
	public void setXtbz(String XTBZ){
	this.xtbz=XTBZ;
	}
	public String getXtbz(){
		return xtbz;
	}

	public int getXh() {
		return xh;
	}
	public void setXh(int xh) {
		this.xh = xh;
	}
	public int getCzqx() {
		return czqx;
	}
	public void setCzqx(int czqx) {
		this.czqx = czqx;
	}
	public List<Mkb> getChildMkbs() {
		return childMkbs;
	}
	public void setChildMkbs(List<Mkb> childMkbs) {
		this.childMkbs = childMkbs;
	}
	
	
}

