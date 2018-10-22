package com.googosoft.constant;

public class OplogFlag {
	
	public static String ADD = "01";//新增操作
	public static String UPD = "02";//修改操作
	public static String DEL = "03";//删除操作
	public static String SUBMIT = "04";//提交操作
	public static String LOGIN = "05";//登录操作
	public static String BF = "06";//数据备份操作
	public static String CSH = "07";//数据初始化操作
	public static String DELALL = "08";//数据清空操作
	public static String BACK = "09";//撤销操作
	public static String CHECK = "10";//审核通过操作
	public static String RETREAT = "11";//审核退回操作
	
    public static String CasLink = "(case czlx when '" +
    OplogFlag.ADD + "' then '增加操作' when '" +
    OplogFlag.UPD + "' then '修改操作' when '" +
    OplogFlag.DEL + "' then '删除操作' when '" +
    OplogFlag.SUBMIT + "' then '提交操作' when '"+
    OplogFlag.BF + "' then '数据备份操作' when '"+
    OplogFlag.CSH + "' then '系统初始化操作' when '"+
    OplogFlag.DELALL + "' then '数据清空操作' when '"+
    OplogFlag.LOGIN + "' then '登录操作' when '"+
    OplogFlag.BACK + "' then '撤销操作' when '"+
    OplogFlag.CHECK + "' then '审核通过操作' when '"+
    OplogFlag.RETREAT + "' then '审核退回操作' end) AS CZLX ";
 
}
