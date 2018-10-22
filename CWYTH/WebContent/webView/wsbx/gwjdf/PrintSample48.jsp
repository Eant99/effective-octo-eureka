<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>
<!-- 安徽财贸职业学院差旅费报销单 -->

</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>

<div id="div1">
<h1 id="div1" style="text-align: center;">教职工出差审批表</h1>
</div>
<div id="div2">
<%-- <c:forEach var="printinfolist" items="${printinfolist}"> --%>
填表日期：共 张
<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
  	<TBODY>      
	  <tr>
	    <TD colspan="2" rowspan="1" style="height:20px;width:20%;text-align: center;">申请部门</TD>
	    <TD colspan="4" style="height:70px;width:20px;text-align: center;"></TD>
	    <TD colspan="1" style="height:70px;width:20px;text-align: center;">申请人</TD>
	    <TD colspan="3" rowspan="1"  style="height:70px;width:20px;text-align: center;"></TD>
	  </tr>
	  <tr>
	     <TD colspan="3" rowspan="1" style="height:70px;width:40px;text-align: center;">出差时间</TD>
	     <TD colspan="3" rowspan="1" style="height:70px;width:40px;text-align: center;"></TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">出差人员</TD>
	     <TD colspan="3" rowspan="1" style="height:70px;width:40px;text-align: center;"></TD>
	  </tr>
	  <tr>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">出差内容</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"></TD>
	  </tr>
	  <tr>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">部门领导意见</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"></TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">分管领导意见</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"></TD>
      </tr>
	  
	</TBODY>
	<br>
	注：报销人在报销时需附此审批表。
</TABLE>
<%-- </c:forEach> --%>
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
