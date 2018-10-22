package com.googosoft.pojo.system;

import java.io.Serializable;

/**
* ZC_XGWD 相关文档实体类
* 创建时间： 2016-11-11
* @author 芳芳郁金香
*/ 
@SuppressWarnings("serial")
public class ZC_XGWD implements Serializable{
	private String guid;
	private String djlx;
	private String dbh;
	private String filename;
	private String rybh;
	private String scsj;
	private String bz;
	private String path;
	private String filetype;
	private byte[] filecontent;
	public void setGuid(String GUID){
	this.guid=GUID;
	}
	public String getGuid(){
		return guid;
	}
	public void setDjlx(String DJLX){
	this.djlx=DJLX;
	}
	public String getDjlx(){
		return djlx;
	}
	public void setDbh(String DBH){
	this.dbh=DBH;
	}
	public String getDbh(){
		return dbh;
	}
	public void setFilename(String FILENAME){
	this.filename=FILENAME;
	}
	public String getFilename(){
		return filename;
	}
	public void setRybh(String RYBH){
	this.rybh=RYBH;
	}
	public String getRybh(){
		return rybh;
	}
	public void setScsj(String SCSJ){
	this.scsj=SCSJ;
	}
	public String getScsj(){
		return scsj;
	}
	public void setBz(String BZ){
	this.bz=BZ;
	}
	public String getBz(){
		return bz;
	}
	public void setPath(String PATH){
	this.path=PATH;
	}
	public String getPath(){
		return path;
	}
	public void setFiletype(String FILETYPE){
	this.filetype=FILETYPE;
	}
	public String getFiletype(){
		return filetype;
	}
	public void setFilecontent(byte[] FILECONTENT){
	this.filecontent=FILECONTENT;
	}
	public byte[] getFilecontent(){
		return filecontent;
	}
}

