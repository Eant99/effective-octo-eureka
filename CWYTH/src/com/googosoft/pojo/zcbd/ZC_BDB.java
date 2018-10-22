package com.googosoft.pojo.zcbd;

import com.googosoft.util.Validate;

/**
* ZC_BDB 实体类
* 创建时间： 2016-11-07
*@author master
*/ 


public class ZC_BDB{
	/**
	 * 变动编号(sys_guid())
	 */
	private String bdbh;
	/**
	 * 标志(主件：1；附件：2)
	 */
	private String bz;
	/**
	 * 资产编号
	 */
	private String fjbh;
	/**
	 * 变动项目
	 */
	private String bdxm;
	/**
	 * 变动前内容
	 */
	private String bdqnr;
	/**
	 * 变动后内容
	 */
	private String bdhnr;
	/**
	 * 变动日期
	 */
	private String bdrq;
	/**
	 * 资产编号
	 */
	private String yqbh;
	/**
	 * 资产名称
	 */
	private String yqmc;
	/**
	 * 使用单位
	 */
	private String sydw;
	/**
	 * 单价
	 */
	private Float dj;
	/**
	 * 购置日期
	 */
	private String gzrq;
	/**
	 * 变动原因
	 */
	private String bdyy;
	/**
	 * 变动标识
	 */
	private String bdbz;
	/**
	 * 凭证号
	 */
	private String pzh;
	/**
	 * 记账日期
	 */
	private String jzrq;
	/**
	 * 分类号
	 */
	private String flh;
	/**
	 * 分类名称
	 */
	private String flmc;
	/**
	 * 变动报告编号
	 */
	private String bdbgbh;
	/**
	 * 记账标志(未记账：1；已记账：0)
	 */
	private String jzbz;
	/**
	 * 使用方向
	 */
	private String syfx;
	/**
	 * 总价
	 */
	private Float zzj;
	/**
	 * 使用人
	 */
	private String syr;
	/**
	 * 存放地点编号
	 */
	private String bzxx;
	/**
	 * 现状(对应代码表zl=41)
	 */
	private String xz;
	/**
	 * 是否计入主件(是：1，否：0)
	 */
	private String sfjrzj;
	/**
	 * 附件单价
	 */
	private Float fjdj;
	/**
	 * 数量
	 */
	private Float sykh;
	/**
	 * 变动项目标志(项目变动：0，单价变动：1，附件增加：2，附件处置：3，资产调拨：4，部分报废：6，单位内调拨：7，单位间调拨：8，领用人变动：9，存放地点变动：10)
	 */
	private String xmbz;
	/**
	 * 计量单位
	 */
	private String jldw;
	/**
	 * 生产厂家
	 */
	private String sccj;
	/**
	 * 销售商
	 */
	private String xss;
	/**
	 * 附件规格
	 */
	private String fjgg;
	/**
	 * 附件型号
	 */
	private String fjxh;
	/**
	 * 建档人
	 */
	private String jdr;
	/**
	 * 建档日期
	 */
	private String jdrq;
	/**
	 * 变动前内容名称（用于存放使用人、使用单位和存放地点变动时的人员姓名、单位名称和地点名称）
	 */
	private String bdqnrmc;
	/**
	 * 变动后内容名称（用于存放使用人、使用单位和存放地点变动时的人员姓名、单位名称和地点名称）
	 */
	private String bdhnrmc;
	
	/**   
	 * 获取：变动编号(sys_guid())   
	 * @return bdbh 变动编号(sys_guid())   
	 */
	public String getBdbh() {
		return bdbh;
	}
	/**   
	 * 获取：标志(主件：1；附件：2)   
	 * @return bz 标志(主件：1；附件：2)   
	 */
	public String getBz() {
		return bz;
	}
	/**   
	 * 获取：资产编号   
	 * @return fjbh 资产编号   
	 */
	public String getFjbh() {
		return fjbh;
	}
	/**   
	 * 获取：变动项目   
	 * @return bdxm 变动项目   
	 */
	public String getBdxm() {
		return bdxm;
	}
	/**   
	 * 获取：变动前内容   
	 * @return bdqnr 变动前内容   
	 */
	public String getBdqnr() {
		return bdqnr;
	}
	/**   
	 * 获取：变动后内容   
	 * @return bdhnr 变动后内容   
	 */
	public String getBdhnr() {
		return bdhnr;
	}
	/**   
	 * 获取：变动日期   
	 * @return bdrq 变动日期   
	 */
	public String getBdrq() {
		return bdrq;
	}
	/**   
	 * 获取：资产编号
	 * @return yqbh 资产编号 
	 */
	public String getYqbh() {
		return yqbh;
	}
	/**   
	 * 获取：资产名称   
	 * @return yqmc 资产名称   
	 */
	public String getYqmc() {
		return yqmc;
	}
	/**   
	 * 获取：使用单位   
	 * @return sydw 使用单位   
	 */
	public String getSydw() {
		return sydw;
	}
	/**   
	 * 获取：单价   
	 * @return dj 单价   
	 */
	public Float getDj() {
		return dj;
	}
	/**   
	 * 获取：购置日期   
	 * @return gzrq 购置日期   
	 */
	public String getGzrq() {
		return gzrq;
	}
	/**   
	 * 获取：变动原因   
	 * @return bdyy 变动原因   
	 */
	public String getBdyy() {
		return bdyy;
	}
	/**   
	 * 获取：变动标识   
	 * @return bdbz 变动标识   
	 */
	public String getBdbz() {
		return bdbz;
	}
	/**   
	 * 获取：凭证号   
	 * @return pzh 凭证号   
	 */
	public String getPzh() {
		return pzh;
	}
	/**   
	 * 获取：记账日期   
	 * @return jzrq 记账日期   
	 */
	public String getJzrq() {
		return jzrq;
	}
	/**   
	 * 获取：分类号   
	 * @return flh 分类号   
	 */
	public String getFlh() {
		return flh;
	}
	/**   
	 * 获取：分类名称   
	 * @return flmc 分类名称
	 */
	public String getFlmc() {
		return flmc;
	}
	/**   
	 * 获取：变动报告编号   
	 * @return bdbgbh 变动报告编号   
	 */
	public String getBdbgbh() {
		return bdbgbh;
	}
	/**   
	 * 获取：记账标志(未记账：1；已记账：0)   
	 * @return jzbz 记账标志(未记账：1；已记账：0)   
	 */
	public String getJzbz() {
		return jzbz;
	}
	/**   
	 * 获取：使用方向   
	 * @return syfx 使用方向   
	 */
	public String getSyfx() {
		return syfx;
	}
	/**   
	 * 获取：总价   
	 * @return zzj 总价   
	 */
	public Float getZzj() {
		return zzj;
	}
	/**   
	 * 获取：使用人   
	 * @return syr 使用人   
	 */
	public String getSyr() {
		return syr;
	}
	/**   
	 * 获取：存放地点编号   
	 * @return bzxx 存放地点编号   
	 */
	public String getBzxx() {
		return bzxx;
	}
	/**   
	 * 获取：现状(对应代码表zl=41)   
	 * @return xz 现状(对应代码表zl=41)   
	 */
	public String getXz() {
		return xz;
	}
	/**   
	 * 获取：是否计入主件(是：1，否：0)   
	 * @return sfjrzj 是否计入主件(是：1，否：0)   
	 */
	public String getSfjrzj() {
		return sfjrzj;
	}
	/**   
	 * 获取：附件单价   
	 * @return fjdj 附件单价   
	 */
	public Float getFjdj() {
		return fjdj;
	}
	/**   
	 * 获取：数量   
	 * @return sykh 数量   
	 */
	public Float getSykh() {
		return sykh;
	}
	/**   
	 * 获取：变动项目标志(单位内调拨：0，单位间调拨：1，价值变动：2，使用人变动：3，存放地点变动：4)   
	 * @return xmbz 变动项目标志(单位内调拨：0，单位间调拨：1，价值变动：2，使用人变动：3，存放地点变动：4)   
	 */
	public String getXmbz() {
		return xmbz;
	}
	/**   
	 * 获取：计量单位   
	 * @return jldw 计量单位   
	 */
	public String getJldw() {
		return Validate.isNullToDefaultString(jldw,"");
	}
	/**   
	 * 获取：生产厂家   
	 * @return sccj 生产厂家   
	 */
	public String getSccj() {
		return sccj;
	}
	/**   
	 * 获取：销售商   
	 * @return xss 销售商   
	 */
	public String getXss() {
		return xss;
	}
	/**   
	 * 获取：附件规格
	 * @return fjgg 附件规格
	 */
	public String getFjgg() {
		return fjgg;
	}
	/**   
	 * 获取：附件型号
	 * @return fjxh 附件型号
	 */
	public String getFjxh() {
		return fjxh;
	}
	/**   
	 * 获取：建档人   
	 * @return jdr 建档人   
	 */
	public String getJdr() {
		return jdr;
	}
	/**   
	 * 获取：建档日期   
	 * @return jdrq 建档日期   
	 */
	public String getJdrq() {
		return jdrq;
	}
	/**   
	 * 获取：变动前内容名称（用于存放使用人、使用单位和存放地点变动时的人员姓名、单位名称和地点名称）   
	 * @return bdqnrmc 变动前内容名称   
	 */
	public String getBdqnrmc() {
		return bdqnrmc;
	}
	/**   
	 * 获取：变动后内容名称（用于存放使用人、使用单位和存放地点变动时的人员姓名、单位名称和地点名称）   
	 * @return bdhnrmc 变动后内容名称   
	 */
	public String getBdhnrmc() {
		return bdhnrmc;
	}
	/**  
	 * 设置：变动编号(sys_guid())  
	 * @param bdbh 变动编号(sys_guid())  
	 */
	public void setBdbh(String bdbh) {
		this.bdbh = bdbh;
	}
	/**  
	 * 设置：标志(主件：1；附件：2)  
	 * @param bz 标志(主件：1；附件：2)  
	 */
	public void setBz(String bz) {
		this.bz = bz;
	}
	/**  
	 * 设置：资产编号  
	 * @param fjbh 资产编号  
	 */
	public void setFjbh(String fjbh) {
		this.fjbh = fjbh;
	}
	/**  
	 * 设置：变动项目  
	 * @param bdxm 变动项目  
	 */
	public void setBdxm(String bdxm) {
		this.bdxm = bdxm;
	}
	/**  
	 * 设置：变动前内容  
	 * @param bdqnr 变动前内容  
	 */
	public void setBdqnr(String bdqnr) {
		this.bdqnr = bdqnr;
	}
	/**  
	 * 设置：变动后内容  
	 * @param bdhnr 变动后内容  
	 */
	public void setBdhnr(String bdhnr) {
		this.bdhnr = bdhnr;
	}
	/**  
	 * 设置：变动日期  
	 * @param bdrq 变动日期  
	 */
	public void setBdrq(String bdrq) {
		this.bdrq = bdrq;
	}
	/**  
	 * 设置：资产编号 
	 * @param yqbh 资产编号
	 */
	public void setYqbh(String yqbh) {
		this.yqbh = yqbh;
	}
	/**  
	 * 设置：资产名称  
	 * @param yqmc 资产名称  
	 */
	public void setYqmc(String yqmc) {
		this.yqmc = yqmc;
	}
	/**  
	 * 设置：使用单位  
	 * @param sydw 使用单位  
	 */
	public void setSydw(String sydw) {
		this.sydw = sydw;
	}
	/**  
	 * 设置：单价  
	 * @param dj 单价  
	 */
	public void setDj(Float dj) {
		this.dj = dj;
	}
	/**  
	 * 设置：购置日期  
	 * @param gzrq 购置日期  
	 */
	public void setGzrq(String gzrq) {
		this.gzrq = gzrq;
	}
	/**  
	 * 设置：变动原因  
	 * @param bdyy 变动原因  
	 */
	public void setBdyy(String bdyy) {
		this.bdyy = bdyy;
	}
	/**  
	 * 设置：变动标识  
	 * @param bdbz 变动标识  
	 */
	public void setBdbz(String bdbz) {
		this.bdbz = bdbz;
	}
	/**  
	 * 设置：凭证号  
	 * @param pzh 凭证号  
	 */
	public void setPzh(String pzh) {
		this.pzh = pzh;
	}
	/**  
	 * 设置：记账日期  
	 * @param jzrq 记账日期  
	 */
	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}
	/**  
	 * 设置：分类号  
	 * @param flh 分类号  
	 */
	public void setFlh(String flh) {
		this.flh = flh;
	}
	/**  
	 * 设置：分类名称  
	 * @param flmc 分类名称  
	 */
	public void setFlmc(String flmc) {
		this.flmc = flmc;
	}
	/**  
	 * 设置：变动报告编号  
	 * @param bdbgbh 变动报告编号  
	 */
	public void setBdbgbh(String bdbgbh) {
		this.bdbgbh = bdbgbh;
	}
	/**  
	 * 设置：记账标志(未记账：1；已记账：0)  
	 * @param jzbz 记账标志(未记账：1；已记账：0)  
	 */
	public void setJzbz(String jzbz) {
		this.jzbz = jzbz;
	}
	/**  
	 * 设置：使用方向  
	 * @param syfx 使用方向  
	 */
	public void setSyfx(String syfx) {
		this.syfx = syfx;
	}
	/**  
	 * 设置：总价  
	 * @param zzj 总价  
	 */
	public void setZzj(Float zzj) {
		this.zzj = zzj;
	}
	/**  
	 * 设置：使用人  
	 * @param syr 使用人  
	 */
	public void setSyr(String syr) {
		this.syr = syr;
	}
	/**  
	 * 设置：存放地点编号  
	 * @param bzxx 存放地点编号  
	 */
	public void setBzxx(String bzxx) {
		this.bzxx = bzxx;
	}
	/**  
	 * 设置：现状(对应代码表zl=41)  
	 * @param xz 现状(对应代码表zl=41)  
	 */
	public void setXz(String xz) {
		this.xz = xz;
	}
	/**  
	 * 设置：是否计入主件(是：1，否：0)  
	 * @param sfjrzj 是否计入主件(是：1，否：0)  
	 */
	public void setSfjrzj(String sfjrzj) {
		this.sfjrzj = sfjrzj;
	}
	/**  
	 * 设置：附件单价  
	 * @param fjdj 附件单价  
	 */
	public void setFjdj(Float fjdj) {
		this.fjdj = fjdj;
	}
	/**  
	 * 设置：数量  
	 * @param sykh 数量  
	 */
	public void setSykh(Float sykh) {
		this.sykh = sykh;
	}
	/**  
	 * 设置：变动项目标志(单位内调拨：0，单位间调拨：1，价值变动：2，使用人变动：3，存放地点变动：4)  
	 * @param xmbz 变动项目标志(单位内调拨：0，单位间调拨：1，价值变动：2，使用人变动：3，存放地点变动：4)  
	 */
	public void setXmbz(String xmbz) {
		this.xmbz = xmbz;
	}
	/**  
	 * 设置：计量单位  
	 * @param jldw 计量单位  
	 */
	public void setJldw(String jldw) {
		this.jldw = jldw;
	}
	/**  
	 * 设置：生产厂家  
	 * @param sccj 生产厂家  
	 */
	public void setSccj(String sccj) {
		this.sccj = sccj;
	}
	/**  
	 * 设置：销售商  
	 * @param xss 销售商  
	 */
	public void setXss(String xss) {
		this.xss = xss;
	}
	/**  
	 * 设置：附件规格
	 * @param fjgg 附件规格
	 */
	public void setFjgg(String fjgg) {
		this.fjgg = fjgg;
	}
	/**  
	 * 设置：附件型号
	 * @param fjxh 附件型号
	 */
	public void setFjxh(String fjxh) {
		this.fjxh = fjxh;
	}
	/**  
	 * 设置：建档人  
	 * @param jdr 建档人  
	 */
	public void setJdr(String jdr) {
		this.jdr = jdr;
	}
	/**  
	 * 设置：建档日期  
	 * @param jdrq 建档日期  
	 */
	public void setJdrq(String jdrq) {
		this.jdrq = jdrq;
	}
	/**   
	 * 设置：变动前内容名称（用于存放使用人、使用单位和存放地点变动时的人员姓名、单位名称和地点名称）   
	 * @return bdqnrmc 变动前内容名称   
	 */
	public void setBdqnrmc(String bdqnrmc) {
		this.bdqnrmc = bdqnrmc;
	}
	/**   
	 * 设置：变动后内容名称（用于存放使用人、使用单位和存放地点变动时的人员姓名、单位名称和地点名称）   
	 * @return bdhnrmc 变动后内容名称   
	 */
	public void setBdhnrmc(String bdhnrmc) {
		this.bdhnrmc = bdhnrmc;
	}
}

