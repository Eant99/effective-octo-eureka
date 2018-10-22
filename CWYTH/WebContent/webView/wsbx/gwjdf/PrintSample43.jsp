<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>

</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>
<a href="${ctx}/gwjdfsq/goListPage">返回</a>

<div id="div1">
<h1 id="div1" style="text-align: center;">教职工出差审批表</h1>
</div>
<div id="div2">
<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
  <TBODY>      
  <TR>
    <TD style="height:70px;width:20%;text-align: center;">申请部门</TD>
    <TD style="padding-left: 8px;">教务处</TD>
    <TD style="height:70px;width:180px;text-align: center;">申请人</TD>
    <TD style="padding-left: 8px;">张三</TD>
  <tr>
    <TD style="height:70px;width:20%;text-align: center;">出差时间</TD>
    <TD style="padding-left: 8px;">2017年12月3日</TD>
    <TD style="height:70px;width:180px;text-align: center;">出差人员</TD>
	<TD style="padding-left: 8px;">张三、李四</TD>
 </tr>
<tr>
    <TD style="height:140px;width:20%;text-align: center;">出差内容</TD>
    <TD colspan="3" style="padding-left: 8px;">学习调研</TD>
 </tr>
 <tr>
    <TD style="height:70px;width:20%;text-align: center;">部门领导意见</TD>
    <TD style="padding-left: 8px;">同意</TD>
    <TD style="height:70px;width:20%;text-align: center;">分管领导意见</TD>
	<TD style="padding-left: 8px;">同意</TD>
 </tr>
   
</TABLE>
</div>
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script> 
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
		LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle+document.getElementById("div2").innerHTML);
		LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("div1").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
// 		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);		
// 		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
// 		LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
// 		LODOP.SET_PRINT_STYLEA(0,"FontColor","#FF0000");
// 		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
// 		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
// 		LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
// 		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.PREVIEW();	
	};	
</script>
		

</body>
