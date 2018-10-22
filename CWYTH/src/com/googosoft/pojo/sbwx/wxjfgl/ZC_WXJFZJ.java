package com.googosoft.pojo.sbwx.wxjfgl;

import com.googosoft.util.AutoKey;
import com.googosoft.util.Validate;

/**
* ZC_WXJFZJ 实体类
* 创建时间： 2016-11-19
*@author LM
*/ 


public class ZC_WXJFZJ{
	private String sqbh;
	private String sqry;
	private String sqrq;
	private String sydw;
	private String sqyy;
	private float je;
	private String nd;
	private String dwry;
	private String dwshrq;
	private String dwyj;
	private String zccry;
	private String zccshrq;
	private String zccyj;
	private String zjsqzt;
	private String bz;
	
	
	/**
	 * 赋值申请编号
	 * @param SQBH
	 */
	public void setSqbh(String SQBH){
	this.sqbh=SQBH;
	}
	
	/**
	 * 取值申请编号
	 * @return SQBH
	 */
	public String getSqbh(){
		if(Validate.isNull(sqbh))
			sqbh = AutoKey.createKey("ZC_WXJFZJ","sqbh","4"); 
		return sqbh;
	}
	
	/**
	 * 赋值申请人员
	 * @param SQRY
	 */
	public void setSqry(String SQRY){
	this.sqry=SQRY;
	}
	
	/**
	 * 取值申请人员
	 * @return SQRY
	 */
	public String getSqry(){
		return sqry;
	}
	
	/**
	 * 赋值申请日期
	 * @param SQRQ
	 */
	public void setSqrq(String SQRQ){
	this.sqrq=SQRQ;
	}
	
	/**
	 * 取值申请日期
	 * @return SQRQ
	 */
	public String getSqrq(){
		return sqrq;
	}
	
	/**
	 * 赋值使用单位
	 * @param SYDW
	 */
	public void setSydw(String SYDW){
	this.sydw=SYDW;
	}
	
	/**
	 * 取值使用单位
	 * @return SYDW
	 */
	public String getSydw(){
		return sydw;
	}
	
	/**
	 * 赋值申请原因
	 * @param SQYY
	 */
	public void setSqyy(String SQYY){
	this.sqyy=SQYY;
	}
	
	/**
	 * 取值申请原因
	 * @return SQYY
	 */
	public String getSqyy(){
		return sqyy;
	}
	
	/**
	 * 赋值申请金额
	 * @param JE
	 */
	public void setJe(float JE){
	this.je=JE;
	}
	
	/**
	 * 取值申请金额
	 * @return JE
	 */
	public float getJe(){
		return je;
	}
	
	/**
	 * 赋值申请年度
	 * @param ND
	 */
	public void setNd(String ND){
	this.nd=ND;
	}
	
	/**
	 * 取值申请年度
	 * @return ND
	 */
	public String getNd(){
		return nd;
	}
	
	/**
	 * 赋值单位领导
	 * @param DWRY
	 */
	public void setDwry(String DWRY){
	this.dwry=DWRY;
	}
	
	/**
	 * 取值单位领导
	 * @return DWRY
	 */
	public String getDwry(){
		return dwry;
	}
	
	/**
	 * 赋值单位领导审核日期
	 * @param DWSHRQ
	 */
	public void setDwshrq(String DWSHRQ){
	this.dwshrq=DWSHRQ;
	}
	
	/**
	 * 取值单位领导审核日期
	 * @return DWSHRQ
	 */
	public String getDwshrq(){
		return dwshrq;
	}
	
	/**
	 * 赋值单位领导审核意见
	 * @param DWYJ
	 */
	public void setDwyj(String DWYJ){
	this.dwyj=DWYJ;
	}
	
	/**
	 * 取值单位领导审核意见
	 * @return DWYJ
	 */
	public String getDwyj(){
		return dwyj;
	}
	
	/**
	 * 赋值资产处人员
	 * @param ZCCRY
	 */
	public void setZccry(String ZCCRY){
	this.zccry=ZCCRY;
	}
	
	/**
	 * 取值资产处人员
	 * @return ZCCRY
	 */
	public String getZccry(){
		return zccry;
	}
	
	/**
	 * 赋值资产处审核日期
	 * @param ZCCSHRQ
	 */
	public void setZccshrq(String ZCCSHRQ){
	this.zccshrq=ZCCSHRQ;
	}
	
	/**
	 * 取值资产处审核日期
	 * @return ZCCSHRQ
	 */
	public String getZccshrq(){
		return zccshrq;
	}
	
	/**
	 * 赋值资产处审核意见
	 * @param ZCCYJ
	 */
	public void setZccyj(String ZCCYJ){
	this.zccyj=ZCCYJ;
	}
	
	/**
	 * 取值资产处审核意见
	 * @return ZCCYJ
	 */
	public String getZccyj(){
		return zccyj;
	}
	
	/**
	 * 赋值追加申请状态（未提交：55,已提交：00,单位领导不通过：10，单位领导通过：90，资产处不通过：91，资产处通过：99）
	 * @param ZJSQZT
	 */
	public void setHbsqzt(String ZJSQZT){
	this.zjsqzt=ZJSQZT;
	}
	
	/**
	 * 取值追加申请状态（未提交：55,已提交：00,单位领导不通过：10，单位领导通过：90，资产处不通过：91，资产处通过：99）
	 * @return ZJSQZT
	 */
	public String getZjsqzt(){
		return zjsqzt;
	}
	
	/**
	 * 赋值备注
	 * @param BZ
	 */
	public void setBz(String BZ){
	this.bz=BZ;
	}
	
	/**
	 * 取值备注
	 * @return BZ
	 */
	public String getBz(){
		return bz;
	}
}

