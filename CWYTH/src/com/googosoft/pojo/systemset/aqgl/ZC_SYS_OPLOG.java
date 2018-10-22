package com.googosoft.pojo.systemset.aqgl;

import java.util.Date;

import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

import eu.bitwalker.useragentutils.Browser;


/**
* 操作日志管理 实体类
* 创建时间： 2016-10-14
*@author master
*/ 


public class ZC_SYS_OPLOG{
	private String logbh;//日志编号
	private String rybh;//人员编号
	private String xm;//姓名
	private Date czrq;//操作日期
	private String cznr;//操作内容
	private String czjq;//操作机器
	private String zt;//删除状态（1显示；0：不显示）
	private String[] dbh;//单据编号
	private String djlx;//单据类型
	private String czlx;//操作类型
	private String xtbz;//系统标志
	private String syd;//使用端
	private String llq;//浏览器
	/**
	 * 日志编号
	 */
	public String getLogbh() {
		return UuidUtil.get32UUID();
	}
	public void setLogbh(String logbh) {
		this.logbh = logbh;
	}
	/**
	 * 人员编号
	 */
	public String getRybh() {
		return rybh;
	}
	public void setRybh(String rybh) {
		this.rybh = rybh;
	}
	/**
	 * 姓名
	 */
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	/**
	 * 操作日期
	 */
	public Date getCzrq() {
		return czrq;
	}
	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}
	/**
	 * 操作内容
	 */
	public String getCznr() {
		return cznr;
	}
	public void setCznr(String cznr) {
		this.cznr = cznr;
	}
	/**
	 * 操作机器
	 */
	public String getCzjq() {
		return czjq;
	}
	public void setCzjq(String czjq) {
		this.czjq = czjq;
	}
	/**
	 * 删除状态
	 */
	public String getZt() {
		return Validate.isNullToDefaultString(zt,"1");
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	/**
	 * 单据编号
	 */
	public String[] getDbh() {
		return dbh;
	}
	public void setDbh(String... dbh) {
		this.dbh = dbh;
	}
	/**
	 * 单据类型
	 */
	public String getDjlx() {
		return djlx;
	}
	public void setDjlx(String djlx) {
		this.djlx = djlx;
	}
	/**
	 * 操作类型
	 */
	public String getCzlx() {
		return czlx;
	}
	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}
	/**
	 * 系统标志
	 */
	public String getXtbz() {
		return xtbz;
	}
	public void setXtbz(String xtbz) {
		this.xtbz = xtbz;
	}
	public String getSyd() {
		return syd;
	}
	public void setSyd(String syd) {
		this.syd = syd;
	}
	public String getLlq() {
		return llq;
	}
	public void setLlq(String llq) {
		this.llq = llq;
	}
}
