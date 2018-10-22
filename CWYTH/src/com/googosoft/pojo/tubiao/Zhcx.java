package com.googosoft.pojo.tubiao;

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
	private String nd1;
	private String rqqj1;
	private String rqqj2;
	private String ts;
	private String rq;
	private String sjqj1;
	private String sjqj2;
	private String yxsh;
	private String nj;
	private String bj;//班级
	private String xh;
	private String xm;
	private String xslb;
	private String flag;
	private String xy;//学院
	private String dwbh;//单位编号
	private String sssys;//所属实验室
	private String age1;//开始年龄
	private String age2;//结束年龄
	private String agen;//年龄区间
	private String zy;//专业
	private String zy1;//专业1
	private String zy2;//专业2
	private String dqdm;//地区代码
	private String dqdm1;//地区代码
	private String yfqj1;//开始年月份
	private String yfqj2;//结束年月份
	private String syfx;//使用方向
	private String zcly;//资产来源
	private String zcfl;//分类
	private String sf;  //是否
	private String kcmc;  //课程名称
	private String xn; //学年
	private String xq; //学期
	
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
	 * 获取当前年度
	 * @return
	 */
	public String getNd1() {
		return Validate.isNullToDefault(nd1, this.getNdqj2()) + "";
	}
	/**
	 * 设置当前年度
	 * @return
	 */
	public void setNd1(String nd1) {
		this.nd1 = nd1;
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
	public void setRqqj2(String rqqj2) {
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
	 * 获取指定的院系所号
	 * @return
	 */
	public String getYxsh() {
		return Validate.isNullToDefault(yxsh,"") + "";
	}
	/**
	 * 设置指定的院系所号
	 * @return
	 */
	public void setYxsh(String yxsh) {
		this.yxsh = yxsh;
	}
	/**
	 * 获取指定的年级
	 * @return
	 */
	public String getNj() {
		return Validate.isNullToDefault(nj,"") + "";
	}
	/**
	 * 设置指定的年级
	 * @return
	 */
	public void setNj(String nj) {
		this.nj = nj;
	}
	/**
	 * 获取指定的班级
	 * @return
	 */
	public String getBj() {
		return bj;
	}
	/**
	 * 设置指定的班级
	 * @return
	 */
	public void setBj(String bj) {
		this.bj = bj;
	}
	/**
	 * 获取指定的学号
	 * @return
	 */
	public String getXh() {
		return Validate.isNullToDefault(xh,"") + "";
	}
	/**
	 * 设置指定的学号
	 * @return
	 */
	public void setXh(String xh) {
		this.xh = xh;
	}
	/**
	 * 获取指定的姓名
	 * @return
	 */
	public String getXm() {
		return xm;
	}
	/**
	 * 设置指定的姓名
	 * @return
	 */
	public void setXm(String xm) {
		this.xm = xm;
	}
	/**
	 * 获取指定的学生类别
	 * @return
	 */
	public String getXslb() {
		return Validate.isNullToDefault(xslb, "") + "";
	}
	/**
	 * 设置指定的学生类别
	 * @return
	 */
	public void setXslb(String xslb) {
		this.xslb = xslb;
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
	/**
	 * 获取单位编号
	 * @return
	 */
	public String getDwbh() {
		return dwbh;
	}
	/**
	 * 设置单位编号
	 * @return
	 */
	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}
	
	public String getAge1() {
		return age1;
	}
	public void setAge1(String age1) {
		this.age1 = age1;
	}
	public String getAge2() {
		return age2;
	}
	public void setAge2(String age2) {
		this.age2 = age2;
	}
	public String getAgen() {
		return agen;
	}
	public void setAgen(String agen) {
		this.agen = agen;
	}
	public String getZy() {
		return zy;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	public String getZy1() {
		return zy1;
	}
	public void setZy1(String zy1) {
		this.zy1 = zy1;
	}
	public String getZy2() {
		return zy2;
	}
	public void setZy2(String zy2) {
		this.zy2 = zy2;
	}
	public String getDqdm() {
		return dqdm;
	}
	public void setDqdm(String dqdm) {
		this.dqdm = dqdm;
	}
	public String getDqdm1() {
		return dqdm1;
	}
	public void setDqdm1(String dqdm1) {
		this.dqdm1 = dqdm1;
	}
	public String getYfqj1() {
		return yfqj1;
	}
	public void setYfqj1(String yfqj1) {
		this.yfqj1 = yfqj1;
	}
	public String getYfqj2() {
		return yfqj2;
		//return Validate.isNullToDefault(yfqj2,Constant.MR_YEARMONTH()) + "";
	}
	public void setYfqj2(String yfqj2) {
		this.yfqj2 = yfqj2;
	}
	public String getXy() {
		return xy;
	}
	public void setXy(String xy) {
		this.xy = xy;
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
	
	/**
	 * 获取实验室（dwbh）
	 * @return String 
	 */
    public String getSssys() {
        return sssys;
    }
    
    /**
     * 设置实验室（dwbh）
     * @return String 
     */
    public void setSssys(String sssys) {
        this.sssys = sssys;
    }
    public String getSf() {
        return sf;
    }
    public void setSf(String sf) {
        this.sf = sf;
    }
    public String getKcmc() {
        return kcmc;
    }
    public void setKcmc(String kcmc) {
        this.kcmc = kcmc;
    }
    public String getXn() {
        return xn;
    }
    public void setXn(String xn) {
        this.xn = xn;
    }
    public String getXq() {
        return xq;
    }
    public void setXq(String xq) {
        this.xq = xq;
    }
}
