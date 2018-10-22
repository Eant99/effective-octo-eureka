package com.googosoft.constant.shzt;
/**
 * 项目审核状态
 * @author fugangjie
 *
 * 2018年1月27日-下午3:25:53
 */
public interface XmShzt {
	/**
	 * 审核状态种类
	 */
	public static final String SHZTZL = "xmshzt";
	/**
	 * 审核状态：未提交
	 */
	public static final String WTJ = "0";
	/**
	 * 审核状态：初审中
	 */
	public static final String CSZ = "1";
	/**
	 * 审核状态：初审退回
	 */
	public static final String CSTH = "2";
	/**
	 * 审核状态：复审中
	 */
	public static final String FSZ = "3";
	/**
	 * 审核状态：复审退回
	 */
	public static final String FSTH = "4";
	/**
	 * 审核状态：审核通过
	 */
	public static final String SHTG = "5";
}
