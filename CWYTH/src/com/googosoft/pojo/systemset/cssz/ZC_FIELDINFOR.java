package com.googosoft.pojo.systemset.cssz;

/**
* ZC_FIELDINFOR 实体类
* 创建时间： 2016-10-25
*@author master
*/ 

public class ZC_FIELDINFOR{
	private String ctlname;   //文本框名称
	private float ctlreg;     //文本框标志
	private String wordnmae;  //label名称
	private String dtype;     //数据类型	
	private String modetype;  //类别(公用项：00，房屋类：a,构筑物类：b,土地类：c,植物类：d,交通运输类：g,一般设备及家具类：e，图书类：i,动物类：k)
	private String fieldname; //文本名称
	private String tablename; //表名称(验收单信息：yshd,卡片信息：zjb)
	public void setCtlname(String CTLNAME){
	this.ctlname=CTLNAME;
	}
	/**
	 * 文本框名称
	 */
	public String getCtlname(){
		return ctlname;
	}
	public void setCtlreg(float CTLREG){
	this.ctlreg=CTLREG;
	}
	/**
	 * 文本框标志
	 */
	public float getCtlreg(){
		return ctlreg;
	}
	public void setWordnmae(String WORDNMAE){
	this.wordnmae=WORDNMAE;
	}
	/**
	 * label名称
	 */
	public String getWordnmae(){
		return wordnmae;
	}
	public void setDtype(String DTYPE){
	this.dtype=DTYPE;
	}
	/**
	 * 数据类型
	 */
	public String getDtype(){
		return dtype;
	}
	public void setModetype(String MODETYPE){
	this.modetype=MODETYPE;
	}
	/**
	 * 类别
	 */
	public String getModetype(){
		return modetype;
	}
	public void setFieldname(String FIELDNAME){
	this.fieldname=FIELDNAME;
	}
	/**
	 * 表名称
	 */
	public String getFieldname(){
		return fieldname;
	}
	public void setTablename(String TABLENAME){
	this.tablename=TABLENAME;
	}
	/**
	 * 文本名称
	 */
	public String getTablename(){
		return tablename;
	}
}