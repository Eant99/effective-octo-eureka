package com.googosoft.commons;
public class MessageBox {

	//操作参数
	public static String  FAIL_OPERATETPYE = "操作参数（operateType）传值有误，请重试！";
	//保存
	public static String  SUCCESS_SAVE = "信息保存成功！";
	public static String  FAIL_SAVE = "信息保存失败！";
	//删除
	public static String  SUCCESS_DELETE = "信息删除成功！";
	public static String  FAIL_DELETE = "信息删除失败！";
	//提交
	public static String  SUCCESS_SUBMIT = "信息提交成功！";
	public static String  FAIL_SUBMIT = "信息提交失败！";
	//复核
	public static String  SUCCESS_CHECK = "信息复核成功！";
	public static String  FAIL_CHECK = "信息复核失败！";
	/**
	 * 返回操作结果（JSON形式）
	 * @param sucess 结果标志
	 * @param msg 结果信息
	 * @return
	 */
	public static String show(boolean sucess,String msg)
	{
		String result=sucess==true?"true":"false";
		return "{\"success\":\""+result+"\",\"msg\":\""+msg+"\"}";
	}
	public static String toJson(boolean success,String msg)
	{
		return "{\"success\":"+success+",\"msg\":\""+msg+"\"}";
	}
	public static String show(boolean success) {
		return "{\"success\":"+success+"}";
	}
		
}