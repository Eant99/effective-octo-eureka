package com.googosoft.constant.shzt;
/**
 * 项目审核状态
 * @author lifei
 *
 * 2018年1月27日-下午3:25:53
 */
public interface YsglShzt {
	/**
	 * 审核状态种类
	 */
	public static final String SHZTZL = "ysglshzt";
	/**
	 * 审核状态：未提交
	 */
	public static final String WTJ = "0";
	/**
	 * 审核状态：部门负责人审核
	 */
	public static final String bmfzrsh = "1";
	/**
	 * 审核状态：部门负责人退回
	 */
	public static final String bmfzrth = "2";
	/**
	 * 审核状态：财务审核
	 */
	public static final String cwfzrsh = "3";
	/**
	 * 审核状态：财务退回
	 */
	public static final String cwfzrth = "4";
	/**
	 * 审核状态：院长审核
	 */
	public static final String yzsh = "5";
	/**
	 * 审核状态：院长退回
	 */
	public static final String yzth = "6";
	/**
	 * 审核状态：终审通过
	 */
	public static final String shtg = "100";
}
