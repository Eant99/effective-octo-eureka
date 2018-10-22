package com.googosoft.pojo.tjfx;

import com.googosoft.constant.Constant;
import com.googosoft.util.Validate;

/**
 * @description 综合查询实体类
 * @author  zjy
 * @date 2016-10-24
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class Zhcx {
	private String ndqj1;
	private String ndqj2;
	private String ns;
	private String nd;
	private String rqqj1;
	private String rqqj2;
	private String ts;
	private String rq;
	private String sjqj1;
	private String sjqj2;
	private String age1;//开始年龄
	private String age2;//结束年龄
	private String agen;//年龄区间
	private String yfqj1;//开始年月份
	private String yfqj2;//结束年月份
	private String flag;
	private String dwbh;//单位编号
	private String bddw;//单位编号
	private String sydw;//使用单位编号
	private String xmbz;//项目变动
	
	public String getXmbz() {
		return xmbz;
	}
	/**
	 * 项目变动
	 * @return
	 */
	public void setXmbz(String xmbz) {
		this.xmbz = xmbz;
	}
	public String getSydw() {
		return sydw;
	}
	/**
	 * 使用单位编号
	 * @return
	 */
	public void setSydw(String sydw) {
		this.sydw = sydw;
	}
	public String getBddw() {
		return bddw;
	}
	public void setBddw(String bddw) {
		this.bddw = bddw;
	}
	private String syfx;//使用方向
	private String zcly;//资产来源
	private String zcfl;//分类
	private String jc;//单位级次
	private String lx;//分类类型（教育部16大类和财政部6大类）
	
	
	public String getJc() {
		return jc;
	}
	public void setJc(String jc) {
		this.jc = jc
				;
	}
	/**
	 * 获取年度区间开始年度
	 * @return
	 */
	public String getNdqj1() {
		return ndqj1;
	}
	/**
	 * 设置年度区间开始年度
	 * @param ndqj1
	 */
	public void setNdqj1(String ndqj1) {
		this.ndqj1 = ndqj1;
	}
	/**
	 * 获取年度区间结束年度
	 * @param 注意：nd是直接获取的这个值，在修改默认值的时候，需要特别关注一下
	 * @return
	 */
	public String getNdqj2() {
		return Validate.isNullToDefault(ndqj2,Constant.MR_YEAR()) + "";
	}
	/**
	 * 设置年度区间结束年度
	 * @return
	 */
	public void setNdqj2(String ndqj2) {
		this.ndqj2 = ndqj2;
	}
	/**
	 * 获取显示年数
	 * @return
	 */
	public String getNs() {
		return ns;
	}
	/**
	 * 设置显示年数
	 * @return
	 */
	public void setNs(String ns) {
		this.ns = ns;
	}
	/**
	 * 获取当前年度
	 * @return
	 */
	public String getNd() {
		return Validate.isNullToDefault(nd, this.getNdqj2()) + "";
	}
	/**
	 * 设置当前年度
	 * @return
	 */
	public void setNd(String nd) {
		this.nd = nd;
	}
	/**
	 * 获取日期区间开始日期
	 * @return
	 */
	public String getRqqj1() {
		return rqqj1;
	}
	/**
	 * 设置日期区间开始日期
	 * @return
	 */
	public void setRqqj1(String rqqj1) {
		this.rqqj1 = rqqj1;
	}
	/**
	 * 获取日期区间结束日期
	 * @param 注意：rq是直接获取的这个值，在修改默认值的时候，需要特别关注一下
	 * @return
	 */
	public String getRqqj2() {
		return Validate.isNullToDefault(rqqj2,Constant.MR_DATE()) + "";
	}
	/**
	 * 设置日期区间结束日期
	 * @return
	 */
	public void setRqqj2(String rqrj2) {
		this.rqqj2 = rqqj2;
	}
	/**
	 * 获取显示天数
	 * @return
	 */
	public String getTs() {
		return ts;
	}
	/**
	 * 设置显示天数
	 * @return
	 */
	public void setTs(String ts) {
		this.ts = ts;
	}
	/**
	 * 获取当前日期
	 * @return
	 */
	public String getRq() {
		return Validate.isNullToDefault(rq, this.getRqqj2()) + "";
	}
	/**
	 * 设置当前日期
	 * @return
	 */
	public void setRq(String rq) {
		this.rq = rq;
	}
	/**
	 * 获取时间区间开始时间
	 * @return
	 */
	public String getSjqj1() {
		return sjqj1;
	}
	/**
	 * 设置时间区间开始时间
	 * @return
	 */
	public void setSjqj1(String sjqj1) {
		this.sjqj1 = sjqj1;
	}
	/**
	 * 获取时间区间结束时间
	 * @return
	 */
	public String getSjqj2() {
		return sjqj2;
	}
	/**
	 * 设置时间区间结束时间
	 * @return
	 */
	public void setSjqj2(String sjqj2) {
		this.sjqj2 = sjqj2;
	}
	/**
	 * 获取开始年龄
	 * @return
	 */
	public String getAge1() {
		return age1;
	}
	/**
	 * 设置开始年龄
	 * @return
	 */
	public void setAge1(String age1) {
		this.age1 = age1;
	}
	/**
	 * 获取结束年龄
	 * @return
	 */
	public String getAge2() {
		return age2;
	}
	/**
	 * 设置结束年龄
	 * @return
	 */
	public void setAge2(String age2) {
		this.age2 = age2;
	}
	/**
	 * 获取年龄间隔
	 * @return
	 */
	public String getAgen() {
		return agen;
	}
	/**
	 * 设置年龄间隔
	 * @return
	 */
	public void setAgen(String agen) {
		this.agen = agen;
	}
	/**
	 * 获取月份区间开始月份
	 * @return
	 */
	public String getYfqj1() {
		return yfqj1;
	}
	/**
	 * 设置月份区间开始月份
	 * @return
	 */
	public void setYfqj1(String yfqj1) {
		this.yfqj1 = yfqj1;
	}
	/**
	 * 获取月份区间结束月份
	 * @return
	 */
	public String getYfqj2() {
		return yfqj2;
	}
	/**
	 * 设置月份区间结束月份
	 * @return
	 */
	public void setYfqj2(String yfqj2) {
		this.yfqj2 = yfqj2;
	}
	/**
	 * 获取方法标记信息
	 * @return
	 */
	public String getFlag() {
		return Validate.isNullToDefault(flag, "") + "";
	}
	/**
	 * 设置方法标记信息
	 * @return
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	//单位
	public String getDwbh() {
		return dwbh;
	}
	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}
	//使用方向
	public String getSyfx() {
		return syfx;
	}
	public void setSyfx(String syfx) {
		this.syfx = syfx;
	}
	//资产来源
	public String getZcly() {
		return zcly;
	}
	public void setZcly(String zcly) {
		this.zcly = zcly;
	}
	/**
	 * 获取分类信息
	 * @return
	 */
	public String getZcfl() {
		return zcfl;
	}
	/**
	 * 设置分类信息
	 * @return
	 */
	public void setZcfl(String zcfl) {
		this.zcfl = zcfl;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
}
