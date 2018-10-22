<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>
<style>
	td{
		text-align: center;
	}
</style>
</head>
<body>
<a href="javascript:PreviewMytable();">打印</a>
<div id="div1">
<h1 id="div1" style="text-align: center;">安徽财贸职业学院报销封面</h1>
</div>
<div id="div2">
	<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#000000">
	  <tr>
	    <TD style="height:70px;width:100px;text-align: center;">开支内容</TD>
	    <TD style="padding-left: 8px;">公务接待</TD>
	    <TD style="height:70px;width:100px;text-align: center;">报销人</TD>
		<TD style="padding-left: 8px;">${gwjdmx.bxry}</TD>
	  </tr>
	  <tr>
	    <TD style="height:140px;width:100px;text-align: right;">共计报销金额人民币（大写）</TD>
	    <TD id="je" colspan="3" style="padding-left: 8px;"></TD>
	  </tr>
	  <tr>
	    <TD style="height:70px;width:100px;text-align: center;">院领导审批意见</TD>
	    <TD style="height:70px;width:100px;text-align: center;">${gwjdmx.SHYJ }</TD>
	    <TD style="height:70px;width:100px;text-align: center;">部门负责人审批意见</TD>
	    <TD style="height:70px;width:100px;text-align: center;">${gwjdmx.SHYJ }</TD>
	  </tr>
	  <tr style="height: 150px;">
	    <TD style="height:70px;width:100px;text-align: center;">财务审核意见</TD>
	    <TD style="height:70px;text-align: center;" colspan="4">${gwjdmx.SHYJ }</TD>
	  </tr>
	</TABLE>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script> 
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
		LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle+document.getElementById("div2").innerHTML);
		LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("div1").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
		LODOP.PREVIEW();	
	};	
	var val = "${gwjdmx.bxje}";
	$("#je").text(NumberToChinese(val)+"元");
</script>
		

</body>
