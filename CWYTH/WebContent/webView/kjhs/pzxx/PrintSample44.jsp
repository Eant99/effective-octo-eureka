<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@include file="/static/include/public-list-js.inc"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>

</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>
<c:if test="${mkbh=='pzjz' }">
	<a href="${ctx}/pzjz/goPzjz">返回</a>
</c:if>
<c:if test="${mkbh=='pzcx' }">
	<a href="${ctx}/pzcx/goPzcx">返回</a>
</c:if>

<div id="div2">
	
	<h1 id="div1" style="text-align: center;">记账凭证</h1>
	&emsp;&emsp;&emsp;<span>单位名称：${dwmc}</span><div style="float:right;margin-right: 7%;width: 8%;"><span>第${param.pzbh}号</span></div>
		<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;">摘要</TD>
		    <TD style="padding-left: 8px;text-align: center;">总账科目</TD>
		    <TD style="height:70px;width:180px;text-align: center;">明细科目</TD>
			<TD style="padding-left: 8px;text-align: center;">借方金额</TD>
			<TD style="padding-left: 8px;text-align: center;">贷方金额</TD>
		  </tr>
		  <c:forEach var="item" items="${info}">
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;">${item.zy }</TD>
		    <TD style="height:70px;width:20%;text-align: center;">${item.kmbh }</TD>
		    <TD style="height:70px;width:20%;text-align: center;">${item.kmmc }</TD>
		    <TD class="jfje" style="height:70px;width:20%;text-align: center;">${item.jfjehj }</TD>
		    <TD class="jfje2" style="height:70px;width:20%;text-align: center;">${item.dfjehj }</TD>
		  </tr>
		  </c:forEach>
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;border-right: none;">合      计</TD>
		    <TD colspan="2" style="padding-left: 8px;border-left: none;border-right: none;"><span id="dx"></span></TD>
			<TD style="padding-left: 8px;border-left: none;border-right: none;"><span id="ze" style="text-align:center;display: inline-block;width: 100%;"></span></TD>
			<TD style="padding-left: 8px;border-left: none;"><span id="ze2" style="text-align:center;display: inline-block;width: 100%;"></span></TD>
		  </tr>
		</TABLE>
		&emsp;&emsp;&emsp;<span>财务主管：</span><span>&emsp;记账：${info2.jzrmc}</span><span>&emsp;出纳：</span><span>&emsp;审核：${info2.fhrmc}</span><span>&emsp;制单：${info2.zdrmc}</span>
</div>
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script> 
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
		LODOP.ADD_PRINT_HTM(128,"5%","90%",504,strStyle+document.getElementById("div2").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
		LODOP.PREVIEW();	
	};	
	$(document).ready(function(){
		count();
		count2();
		var zje= $("#ze").text();
		console.log(zje);
		$("#dx").text(("报销金额（大写）："+smalltoBIG(parseFloat(zje))).replace("undefined", "零").replace("undefined", "零"));
	})
	function count(){
		var ze = 0.00;
   		$.each($(".jfje"),function(){
   			var val=$(this).text();
   			if(val==""||val==0||isNaN(val)){
   				val = 0.00;
   			}
   			ze = parseFloat(ze)+parseFloat(val);
    		});
   		console.log("ze======"+ze);
   		$("#ze").text(ze);
	}
	function count2(){
		var ze = 0.00;
   		$.each($(".jfje2"),function(){
   			var val=$(this).text();
   			if(val==""||val==0||isNaN(val)){
   				val = 0.00;
   			}
   			ze = parseFloat(ze)+parseFloat(val);
    		});
   		console.log("ze======"+ze);
   		$("#ze2").text(ze);
	}
	/** 数字金额大写转换(可以处理整数,小数,负数) */    
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
