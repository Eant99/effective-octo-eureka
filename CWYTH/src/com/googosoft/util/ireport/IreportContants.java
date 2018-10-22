package com.googosoft.util.ireport;

public interface IreportContants {
	String IREPORTTYPE = "PDF_EXCEL";
	String PDF = "PDF";
	String EXCEL = "EXCEL";
	String pdf = "pdf";
	String xls = "xls";
	String SPLIT = "/";
	String _ = "_";
	String DOT = ".";
	String ENCODING = "UTF-8";
	String PDF_MESSAGE = "---ireport配置中文：_"
			+ "---1）将模板设计中的文本框的属性中，在font栏中做如下设置： _"
			+ "---Font Name: 华文宋体 _"
			+ "---Pdf font name:STSong-Light _"
			+ "---Pdf Embeded: 打勾  |做以上设置后就OK了_"
			+ "---Pdf encoding: UniGB-UCS2-H _"
			+ "---2）list中不能为空，可以有一个空的map _";
}
