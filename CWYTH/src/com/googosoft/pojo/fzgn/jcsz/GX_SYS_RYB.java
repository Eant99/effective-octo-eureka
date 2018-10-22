package com.googosoft.pojo.fzgn.jcsz;

import java.util.Date;

import com.googosoft.constant.Constant;
import com.googosoft.util.Validate;

/**
* 人员表实体类
* 创建时间： 2016-09-23
*@author master
*/ 
public class GX_SYS_RYB{
	private String rybh;
	private String dwbh;
	private String xm;
	private String xb;
	private Date csrq;
	private String whcd;
	private Date byrq;
	private String sxzy;
	private Date gzrq;
	private Double sysgl;
	private String zyzc;
	private String zygz;
	private Date drrq;
	private Date txrq;
	private String bz;
	private String ryzt;
	private String zzbz;
	private Double pxxh;
	private String sfzh;
	private String rygh;
	private Integer rownums;
	private String url;
	private String cssclass;
	private String lxdh;
	private String qq;
	private String mail;
	private String zzzt;
	private String mm;
	private String guid;
	private String czr;
	private Date czrq;
	private String yhjs;
	private String saas;
	private String type;
	private String dwid;
	
	private String yjfx;
	private String xkly;
	private String yjxk;
	
	
	
	
	public String getYjfx() {
		return yjfx;
	}
	public void setYjfx(String yjfx) {
		this.yjfx = yjfx;
	}
	public String getXkly() {
		return xkly;
	}
	public void setXkly(String xkly) {
		this.xkly = xkly;
	}
	public String getYjxk() {
		return yjxk;
	}
	public void setYjxk(String yjxk) {
		this.yjxk = yjxk;
	}
	public String getDwid() {
		return dwid;
	}
	public String getSaas() {
		return saas;
	}

	public void setSaas(String saas) {
		this.saas = saas;
	}
	/**
	 * 明文密码
	 */
	private String mwmm;

	public String getMwmm() {
		return mwmm;
	}

	public void setMwmm(String mwmm) {
		this.mwmm = mwmm;
	}
	public String getYhjs() {
		return yhjs;
	}
	public void setYhjs(String yhjs) {
		this.yhjs = yhjs;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public Date getCzrq() {
		return czrq;
	}
	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public void setRybh(String RYBH){
		this.rybh=RYBH;
	}
	/**
	 * 人员编号
	 * @return
	 */
	public String getRybh(){
		return Validate.isNullToDefaultString(rybh,"");
	}
	public void setDwbh(String DWBH){
		this.dwbh=DWBH;
	}
	/**
	 * 单位编号
	 * @return
	 */
	public String getDwbh(){
		return dwbh;
	}
	public void setXm(String XM){
		this.xm=XM;
	}
	/**
	 * 姓名
	 * @return
	 */
	public String getXm(){
		return Validate.isNullToDefaultString(xm,"");
	}
	public void setXb(String XB){
		this.xb=XB;
	}
	/**
	 * 性别
	 * @return
	 */
	public String getXb(){
		return xb;
	}
	public void setCsrq(Date CSRQ){
		this.csrq=CSRQ;
	}
	/**
	 * 出生日期
	 * @return
	 */
	public Date getCsrq(){
		return csrq;
	}
	public void setWhcd(String WHCD){
		this.whcd=WHCD;
	}
	/**
	 * 文化程度
	 * @return
	 */
	public String getWhcd(){
		return whcd;
	}
	public void setByrq(Date BYRQ){
		this.byrq=BYRQ;
	}
	/**
	 * 毕业日期
	 * @return
	 */
	public Date getByrq(){
		return byrq;
	}
	public void setSxzy(String SXZY){
		this.sxzy=SXZY;
	}
	/**
	 * 所学专业
	 * @return
	 */
	public String getSxzy(){
		return sxzy;
	}
	public void setGzrq(Date GZRQ){
		this.gzrq=GZRQ;
	}
	/**
	 * 工作日期
	 * @return
	 */
	public Date getGzrq(){
		return gzrq;
	}
	public void setSysgl(Double SYSGL){
		this.sysgl=SYSGL;
	}
	/**
	 * 实验室工龄
	 * @return
	 */
	public Double getSysgl(){
		return sysgl;
	}
	public void setZyzc(String ZYZC){
		this.zyzc=ZYZC;
	}
	/**
	 * 专业职称
	 * @return
	 */
	public String getZyzc(){
		return zyzc;
	}
	public void setZygz(String ZYGZ){
		this.zygz=ZYGZ;
	}
	/**
	 * 主要工作
	 * @return
	 */
	public String getZygz(){
		return zygz;
	}
	public void setDrrq(Date DRRQ){
		this.drrq=DRRQ;
	}
	/**
	 * 调入日期
	 * @return
	 */
	public Date getDrrq(){
		return drrq;
	}
	public void setTxrq(Date TXRQ){
		this.txrq=TXRQ;
	}
	/**
	 * 退休日期
	 * @return
	 */
	public Date getTxrq(){
		return txrq;
	}
	public void setBz(String BZ){
		this.bz=BZ;
	}
	/**
	 * 备注
	 * @return
	 */
	public String getBz(){
		return bz;
	}
	public void setRyzt(String RYZT){
		this.ryzt=RYZT;
	}
	/**
	 * 人员状态
	 * @return
	 */
	public String getRyzt(){
		return ryzt;
	}
	public void setZzbz(String ZZBZ){
		this.zzbz=ZZBZ;
	}
	/**
	 * 人员类型
	 * @return
	 */
	public String getZzbz(){
		return zzbz;
	}
	public void setPxxh(Double PXXH){
		this.pxxh=PXXH;
	}
	/**
	 * 排序序号
	 * @return
	 */
	public Double getPxxh(){
		return pxxh;
	}
	public void setSfzh(String SFZH){
		this.sfzh=SFZH;
	}
	/**
	 * 身份证号
	 * @return
	 */
	public String getSfzh(){
		return sfzh;
	}
	public void setRygh(String RYGH){
		this.rygh=RYGH;
	}
	/**
	 * 人员工号
	 * @return
	 */
	public String getRygh(){
		return Validate.isNullToDefaultString(rygh,"");
	}
	public void setRownums(Integer ROWNUMS){
		this.rownums=ROWNUMS;
	}
	/**
	 * 显示行数
	 * @return
	 */
	public Integer getRownums(){
		return rownums;
	}
	public void setUrl(String URL){
		this.url=URL;
	}
	/**
	 * 单链接地址
	 * @return
	 */
	public String getUrl(){
		return url;
	}
	public void setCssclass(String CSSCLASS){
		this.cssclass=CSSCLASS;
	}
	/**
	 * 自定义皮肤
	 * @return
	 */
	public String getCssclass(){
		return cssclass;
	}
	public void setLxdh(String LXDH){
		this.lxdh=LXDH;
	}
	/**
	 * 联系电话
	 * @return
	 */
	public String getLxdh(){
		return lxdh;
	}
	public void setQq(String QQ){
		this.qq=QQ;
	}
	/**
	 * qq
	 * @return
	 */
	public String getQq(){
		return qq;
	}
	public void setMail(String MAIL){
		this.mail=MAIL;
	}
	/**
	 * Email
	 * @return
	 */
	public String getMail(){
		return mail;
	}
	public void setZzzt(String ZZZT){
		this.zzzt=ZZZT;
	}
	/**
	 * 在职状态
	 * @return
	 */
	public String getZzzt(){
		return zzzt;
	}
	public void setMm(String MM){
		this.mm=MM;
	}
	/**
	 * 密码
	 * @return
	 */
	public String getMm(){
		return mm;
	}
    /**
     * 人员类型
     * @return
     */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

