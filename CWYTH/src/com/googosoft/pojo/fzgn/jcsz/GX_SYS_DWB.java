package com.googosoft.pojo.fzgn.jcsz;

import java.util.Date;

/**
* 单位表实体类
* 创建时间： 2016-09-30
* @author 
*/ 
public class GX_SYS_DWB {
	
	private String dwbh;//单位编号
	private String mc;//单位名称
	private String jc;//单位简称
	private String dz;//单位地址
	private String dwxz;//单位性质(对应代码表zl=32)
	private Date jlrq;//成立日期
	private String dwld;//单位领导人员编号
	private String fgld;//分管领导人员编号
	private String sjdw;//上级单位单位编号
	private String dwzt;//单位状态(正常：1；禁用 0)
	private int dwjc;//单位级次
	private String mjbz;//末级标志(末级：1；非末级：0)
	private String pxxh;//排序序号
	private String bmh;//部门号
	private String bmsx;//部门属性
	private String bz;//备注
	private String sysbz;//实验室标志(0:实验室，1;非实验室)
	private String sysjb;//实验室级别(对应代码表zl=47)
	private String sysgs;//实验室归属(对应代码表zl=48)
	private String syslb;//实验室类别(对应代码表zl=11)
	private Double sysmj;//实验室面积
	private String jlnf;//建立年份
	private String syslx;//实验室类型(对应代码表zl=56)
	private String ssxk;//所属学科(对应代码表zl=01)
	private String dwbb;//单位办别 
	private String sfxy;//是否学院 
	private String dwywmc; //单位英文名称
	private String dwywjc; //单位英文简称
	private Date sxrq; //失效日期
	private String sfst; //是否实体
	private String czr; //操作人
	private Date czrq; //操作日期
	private String guid;
	private String fjbh;
	private String xmbh;
	private String zfcgje;
	private String zfcgsyje;
	private String fzfcgje;
	private String fzfcgsyje;
	private String sj;
	
	private String gdfw;
	
	
	public String getGdfw() {
		return gdfw;
	}
	public void setGdfw(String gdfw) {
		this.gdfw = gdfw;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getZfcgje() {
		return zfcgje;
	}
	public void setZfcgje(String zfcgje) {
		this.zfcgje = zfcgje;
	}
	public String getZfcgsyje() {
		return zfcgsyje;
	}
	public void setZfcgsyje(String zfcgsyje) {
		this.zfcgsyje = zfcgsyje;
	}
	public String getFzfcgje() {
		return fzfcgje;
	}
	public void setFzfcgje(String fzfcgje) {
		this.fzfcgje = fzfcgje;
	}
	public String getFzfcgsyje() {
		return fzfcgsyje;
	}
	public void setFzfcgsyje(String fzfcgsyje) {
		this.fzfcgsyje = fzfcgsyje;
	}
	public String getPxxh() {
		return pxxh;
	}
	public void setPxxh(String pxxh) {
		this.pxxh = pxxh;
	}
	public String getXmbh() {
		return xmbh;
	}
	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}
	public String getFjbh() {
		return fjbh;
	}
	public void setFjbh(String fjbh) {
		this.fjbh = fjbh;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getDwbb() {
		return dwbb;
	}
	public void setDwbb(String dwbb) {
		this.dwbb = dwbb;
	}
	public String getSfxy() {
		return sfxy;
	}
	public void setSfxy(String sfxy) {
		this.sfxy = sfxy;
	}
	public String getDwywmc() {
		return dwywmc;
	}
	public void setDwywmc(String dwywmc) {
		this.dwywmc = dwywmc;
	}
	public String getDwywjc() {
		return dwywjc;
	}
	public void setDwywjc(String dwywjc) {
		this.dwywjc = dwywjc;
	}
	public Date getSxrq() {
		return sxrq;
	}
	public void setSxrq(Date sxrq) {
		this.sxrq = sxrq;
	}
	public String getSfst() {
		return sfst;
	}
	public void setSfst(String sfst) {
		this.sfst = sfst;
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
	public String getDwbh() {
		return dwbh;
	}
	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getJc() {
		return jc;
	}
	public void setJc(String jc) {
		this.jc = jc;
	}
	public String getDz() {
		return dz;
	}
	public void setDz(String dz) {
		this.dz = dz;
	}
	public String getDwxz() {
		return dwxz;
	}
	public void setDwxz(String dwxz) {
		this.dwxz = dwxz;
	}
	public Date getJlrq() {
		return jlrq;
	}
	public void setJlrq(Date jlrq) {
		this.jlrq = jlrq;
	}
	public String getDwld() {
		return dwld;
	}
	public void setDwld(String dwld) {
		this.dwld = dwld;
	}
	public String getFgld() {
		return fgld;
	}
	public void setFgld(String fgld) {
		this.fgld = fgld;
	}
	public String getSjdw() {
		return sjdw;
	}
	public void setSjdw(String sjdw) {
		this.sjdw = sjdw;
	}
	public String getDwzt() {
		return dwzt;
	}
	public void setDwzt(String dwzt) {
		this.dwzt = dwzt;
	}
	public int getDwjc() {
		return dwjc;
	}
	public void setDwjc(int dwjc) {
		this.dwjc = dwjc;
	}
	public String getMjbz() {
		return mjbz;
	}
	public void setMjbz(String mjbz) {
		this.mjbz = mjbz;
	}
	public String getBmh() {
		return bmh;
	}
	public void setBmh(String bmh) {
		this.bmh = bmh;
	}
	public String getBmsx() {
		return bmsx;
	}
	public void setBmsx(String bmsx) {
		this.bmsx = bmsx;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getSysbz() {
		return sysbz;
	}
	public void setSysbz(String sysbz) {
		this.sysbz = sysbz;
	}
	public String getSysjb() {
		return sysjb;
	}
	public void setSysjb(String sysjb) {
		this.sysjb = sysjb;
	}
	public String getSysgs() {
		return sysgs;
	}
	public void setSysgs(String sysgs) {
		this.sysgs = sysgs;
	}
	public String getSyslb() {
		return syslb;
	}
	public void setSyslb(String syslb) {
		this.syslb = syslb;
	}
	public Double getSysmj() {
		return sysmj;
	}
	public void setSysmj(Double sysmj) {
		this.sysmj = sysmj;
	}
	public String getJlnf() {
		return jlnf;
	}
	public void setJlnf(String jlnf) {
		this.jlnf = jlnf;
	}
	public String getSyslx() {
		return syslx;
	}
	public void setSyslx(String syslx) {
		this.syslx = syslx;
	}
	public String getSsxk() {
		return ssxk;
	}
	public void setSsxk(String ssxk) {
		this.ssxk = ssxk;
	}
	@Override
	public String toString() {
		return "Dwb [dwbh=" + dwbh + ", mc=" + mc + ", jc=" + jc + ", dz=" + dz + ", dwxz=" + dwxz + ", jlrq=" + jlrq
				+ ", dwld=" + dwld + ", fgld=" + fgld + ", sjdw=" + sjdw + ", dwzt=" + dwzt + ", dwjc=" + dwjc
				+ ", mjbz=" + mjbz + ", pxxh=" + pxxh + ", bmh=" + bmh + ", bmsx=" + bmsx + ", bz=" + bz + ", sysbz="
				+ sysbz + ", sysjb=" + sysjb + ", sysgs=" + sysgs + ", syslb=" + syslb + ", sysmj=" + sysmj + ", jlnf="
				+ jlnf + ", syslx=" + syslx + ", ssxk=" + ssxk + "]";
	}
	
}

