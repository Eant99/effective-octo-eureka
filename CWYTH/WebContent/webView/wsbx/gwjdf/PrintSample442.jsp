<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>
<%@include file="/static/include/public-list-js.inc"%>
</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>
<%-- <a href="${ctx}/kylbx/goListPage">返回</a> --%>
<a href="${ctx}/kylbx/gowdbxListPage">返回</a>

<div id="div2">
	<c:forEach var="item" items="${List}"> 
	<h1 id="div1" style="text-align: center;">安徽财贸职业学院报销封面</h1>
		<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;">开支内容</TD>
		    <TD colspan="2" style="padding-left: 8px;">${printinfolist.FYMC}</TD>
		    <TD style="height:70px;width:180px;text-align: center;">单据张数</TD>
			<TD style="text-align: center;">${printinfolist.FJZZS}张</TD>
		  </tr>
		  <tr >
		    <TD rowspan="${length}" style="height:140px;width:20%;text-align: right;">共计报销金额人民币（大写）</TD>
		    <TD rowspan="${length}" colspan="1" id="456" style="text-align: center;" id="zje" ></TD>
		    <TD rowspan="${length}" colspan="1" id="123" style="text-align: center;">￥${printinfolist.BXZJE}</TD>
		    <TD colspan="1"  id="" style="text-align: center;">经济科目</TD>
		    <TD colspan="1" id="" style="text-align: center;">金额（元）</TD>
		  </tr>
		  <c:forEach var="jjMap1" items="${jjMap}">
		  <tr>
		  	<td colspan="1"  id="" style="text-align: center;">${jjMap1.FYMC}</td>
		  	<td colspan="1"  id="" style="text-align: center;">${jjMap1.BXJE}</td>
		  </tr>
		  </c:forEach>
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;">院领导审批意见&emsp;</TD>
		    <TD colspan="2"  style="height:70px;width:20%;text-align: center;">部门负责人审批意见&emsp;</TD>
		    <TD style="height:40px;width:20%;text-align: center;">会计审核意见&emsp;</TD>
		    <TD style="height:70px;width:20%;text-align: center;">报销人&emsp;</TD>
		  </tr>
		  <tr style="height: 150px;">
		    <TD style="height:150px;width:20%;text-align: center;"><span><img src="${fjView }" style="width: 90px;height: 50px;"></span><br>意见：${yxMap.SHYJ }<br>日期：<fmt:formatDate value="${yxMap.jdsj }"/></TD>
		    <TD colspan="2"  style="height:150px;width:20%;text-align: center;"><span><img src="${fjView2 }" style="width: 90px;height: 50px;"></span><br>意见：${bmMap.SHYJ }<br>日期：<fmt:formatDate value="${bmMap.jdsj }"/></TD>
		    <TD style="height:150px;width:20%;text-align: center;"><span><img src="${fjView3 }" style="width: 90px;height: 50px;"></span><br>意见：${kjMap.SHYJ }<br>日期：<fmt:formatDate value="${kjMap.jdsj }"/></TD>
		    <TD style="height:150px;width:20%;text-align: center;"><span>${printinfolist.BXRMC}</span></TD>
		  </tr>
		</TABLE>
		<br>
		<h1 id="div1" style="text-align: center;">安徽财贸职业学院报销封面</h1>
		<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;">开支内容</TD>
		    <TD colspan="2" style="padding-left: 8px;">${printinfolist.FYMC}</TD>
		    <TD style="height:70px;width:180px;text-align: center;">单据张数</TD>
			<TD style="text-align: center;">${printinfolist.FJZZS}张</TD>
		  </tr>
		  <tr >
		    <TD rowspan="${length}" style="height:140px;width:20%;text-align: right;">共计报销金额人民币（大写）</TD>
		    <TD rowspan="${length}" colspan="1" id="4561" style="text-align: center;" id="zje" ></TD>
		    <TD rowspan="${length}" colspan="1" id="123" style="text-align: center;">￥${printinfolist.BXZJE}</TD>
		    <TD colspan="1"  id="" style="text-align: center;">经济科目</TD>
		    <TD colspan="1" id="" style="text-align: center;">金额（元）</TD>
		  </tr>
		  <c:forEach var="jjMap1" items="${jjMap}">
		  <tr>
		  	<td colspan="1"  id="" style="text-align: center;">${jjMap1.FYMC}</td>
		  	<td colspan="1"  id="" style="text-align: center;">${jjMap1.BXJE}</td>
		  </tr>
		  </c:forEach>
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;">院领导审批意见&emsp;</TD>
		    <TD colspan="2"  style="height:70px;width:20%;text-align: center;">部门负责人审批意见&emsp;</TD>
		    <TD style="height:40px;width:20%;text-align: center;">会计审核意见&emsp;</TD>
		    <TD style="height:70px;width:20%;text-align: center;">报销人&emsp;</TD>
		  </tr>
		 <tr style="height: 150px;">
		    <TD style="height:150px;width:20%;text-align: center;"><span><img src="${fjView }" style="width: 90px;height: 50px;"></span><br>意见：${yxMap.SHYJ }<br>日期：<fmt:formatDate value="${yxMap.jdsj }"/></TD>
		    <TD colspan="2"  style="height:150px;width:20%;text-align: center;"><span><img src="${fjView2 }" style="width: 90px;height: 50px;"></span><br>意见：${bmMap.SHYJ }<br>日期：<fmt:formatDate value="${bmMap.jdsj }"/></TD>
		    <TD style="height:150px;width:20%;text-align: center;"><span><img src="${fjView3 }" style="width: 90px;height: 50px;"></span><br>意见：${kjMap.SHYJ }<br>日期：<fmt:formatDate value="${kjMap.jdsj }"/></TD>
		    <TD style="height:150px;width:20%;text-align: center;"><span>${printinfolist.BXRMC}</span></TD>
		  </tr>
		</TABLE>
		</c:forEach>
</div>
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script> 
	$(function(){
		init();
	})
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印");
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
		LODOP.ADD_PRINT_HTM(100,"5%","90%",800,strStyle+document.getElementById("div2").innerHTML);
		LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("div1").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
		LODOP.PREVIEW();	
	};	
	function init(){
		var zje= "${printinfolist.BXZJE}";
		$("#456").text(" "+smalltoBIG(parseFloat(zje)));
		$("#4561").text(" "+smalltoBIG(parseFloat(zje)));
	}
	function smalltoBIG(n)     
	{    
	    var fraction = ['角', '分'];    
	    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];    
	    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];    
	    var head = n < 0? '欠': '';    
	    n = Math.abs(n);    
	  
	    var s = '';    
	  
	    for (var i = 0; i < fraction.length; i++)     
	    {    
	        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');    
	    }    
	    s = s || '整';    
	    n = Math.floor(n);    
	  
	    for (var i = 0; i < unit[0].length && n > 0; i++)     
	    {    
	        var p = '';    
	        for (var j = 0; j < unit[1].length && n > 0; j++)     
	        {    
	            p = digit[n % 10] + unit[1][j] + p;    
	            n = Math.floor(n / 10);    
	        }    
	        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;    
	    }    
	    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');    
	} 
</script>
		

</body>
