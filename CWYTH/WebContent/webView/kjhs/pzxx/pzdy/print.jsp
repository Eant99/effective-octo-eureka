<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@include file="/static/include/public-list-js.inc"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>

</head>
<style>
.zyTextArea {
	font-size: 14px;
	height: 40px;
	line-height: 100%;
	width: 100%;
	padding: 0px;
	margin: 0px;
	float: left;
	overflow: hidden;
	resize: none;
	border: none;
	outline: none;
	padding-top: 25%;
	padding-bottom: 25%;
}

td {
	padding: 0px 5px;
}
</style>
<body>

	<a href="javascript:PreviewMytable();">打印预览</a>
<c:forEach var="finals" items="${finalList}" varStatus="status">
	<div id="divs${status.index}" style="padding-left: 10%; padding-right: 10%;">
			<input type="hidden" name="guid" value="${finals.zbMap.guid}"/>
			<h2 id="div1" style="text-align: center;">${finals.zbMap.xxmc}记账凭证</h2>
			<div
				style="height: 15px; line-height: 15px; text-align: center; position: relative;font-size:14px">
				<label style="position: absolute; left: 3px;"><b>附件</b>${finals.zbMap.fjzs }&nbsp;张</label>
				<label style="float: center; left: 3px;"><b></b>${finals.zbMap.pzrq }</label>
				<label style="float: right; position: absolute; right: 100px;">${finals.zbMap.pzlxmc}</label>
				<label style="float: right; position: absolute; right: 3px;"><b>第</b>${finals.zbMap.pzbh }<b>号</b>&nbsp;&nbsp;<span id="indexNums"></span><span id="sumNums"></span></label>
			</div>
             
<!-- 			<div -->
<!-- 				style="height: 20px; line-height: 20px; text-align: center; position: relative;"> -->
				
					
<%-- 						<label style="float: right; position: absolute; right: 3px;"><b>第</b>${finals.zbMap.pzbh }<b>号</b>&nbsp;&nbsp;<span id="indexNums"></span><span id="sumNums"></span></label> --%>
<!-- 			</div> -->
<!-- 			<div style="height: 20px; line-height: 20px; text-align: center; margin-left:10px; position: relative;"> -->
<%-- 				<label style="position: absolute; right: 3px;"><b>单位名称：</b>${finals.zbMap.dwmc}&nbsp;&nbsp;<span id="indexNums"></span><span id="sumNums"></span></label> --%>
<!-- 			</div> -->
			<TABLE  cellSpacing="0" cellPadding="1"
				style="border-collapse: collapse; width: 100%; margin: 2px 0px 2px 0px;" border="1" bordercolor="#000000">
				<tr>
					<TD style="height: 50px; width: 32%; text-align: center;"><b>摘要</b></TD>
					<TD style="padding-left: 8px; text-align: center;width:15%"><b>会计科目</b></TD>
					<TD style="padding-left: 8px; text-align: center;width:11%"><b>经济科目</b></TD>
					<TD style="padding-left: 8px; text-align: center;width:11%"><b>部门名称</b></TD>
					<TD style="padding-left: 8px; text-align: center;width:11%"><b>项目名称</b></TD>
					<TD style="padding-left: 8px; text-align: center;width:10%"><b>借方金额</b></TD>
					<TD style="padding-left: 8px; text-align: center;width:10%"><b>贷方金额</b></TD>
				</tr>
				<c:forEach var="item" items="${finals.mxList}">
					<tr>
						<TD style="height: 50px;  text-align: left;" >
							<div id='zy'<c:if test="${item.zy.length()>=20}">style="font-size:12px;"</c:if>>${item.zy }</div>
						</TD>
						<TD style="height: 50px;  text-align: left;">
							<div>${item.rootkmbhs}${item.kmmc}</div>
						</TD>
						<TD style="height: 50px; text-align: left;">
							<div><c:if test="${!empty item.jjflmc}">${item.jjflmc}</c:if></div>
						</TD>
						<TD style="height: 50px;  text-align: left;">
							<div><c:if test="${!empty item.bmbhmc}">${item.bmbhmc}</c:if></div>
						</TD>
						<TD style="height: 50px; text-align: left;">
							<div><c:if test="${!empty item.xmbhmc}">${item.xmbhmc}</c:if></div>
						</TD>
<%-- 						<TD style="height: 50px; width: 30%; text-align: left;">${item.rootkmbhs}&emsp;${item.kmmc}<br/><c:if test="${!empty item.jjflmc}">${item.jjflmc};</c:if><c:if test="${!empty item.bmbhmc}">${item.bmbhmc};</c:if><c:if test="${!empty item.xmbhmc}">${item.xmbhmc};</c:if></TD> --%>
						<TD class="jfje"
							style="height: 50px;  text-align: right;"><fmt:formatNumber
								value="${item.jfje }" pattern="0.00" /></TD>
						<TD class="jfje2"
							style="height: 50px; text-align: right;"><fmt:formatNumber
								value="${item.dfje }" pattern="0.00" /></TD>
					</tr>
				</c:forEach>
				<tr>
					<TD
						style="height: 50px;  text-align: center; border-right: none;"><b>合
							计</b></TD>
					<TD style="text-align: center;" id="bxzje" colspan="4"><fmt:formatNumber
							value="${finals.zbMap.jfzje }" pattern="0.00" /></TD>
					<TD style="text-align: right;"><fmt:formatNumber
							value="${finals.zbMap.jfzje }" pattern="￥0.00" /></TD>
					<TD style="text-align: right; border-left: none;"><fmt:formatNumber
							value="${finals.zbMap.dfzje }" pattern="￥0.00" /></TD>
				</tr>
				<tr>				
					<TD
						style="height: 50px; text-align: center; border-right: none;"><b>往来信息</b></TD>					
					<TD colspan='6' style="text-align: left;" ><c:forEach var="item" items="${finals.mxList}"><c:if test="${!empty item.zrr}">${item.zrr};</c:if><c:if test="${!empty item.dfdw}">${item.dfdw};</c:if></c:forEach></TD>
				</tr>
			</TABLE>
			<div style="text-align: left; height: 15px; line-height: 100%;font-size:14px">
			    <label style="display: inline-block; font-size:95%; width: 25%;"><b>财务负责人：</b>范正宽</label>
<%-- 				<label style="display: inline-block; font-size:95%; width: 19%;"><b>记账：</b>${finals.zbMap.jzr }</label> --%>
				<label style="display: inline-block; font-size:95%; width: 27%;"><b>记账：</b>李伟</label>
				<label style="display: inline-block; font-size:95%; width: 27%;"><b>复核：</b>${finals.zbMap.fhr }</label>
				<label style="display: inline-block; font-size:95%;"><b>制单：</b>${finals.zbMap.zdr }</label>
			
			</div>
			<div style="text-align: right; margin-right: 5%;">
				<span></span>
			</div>


	</div>
		</c:forEach>
	<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
	<script> 
	function PreviewMytable(){
		var guid = [];
			var oDate = new Date(); //实例一个时间对象；
			oDate.getFullYear();   //获取系统的年；
			oDate.getMonth()+1;   //获取系统月份，由于月份是从0开始计算，所以要加1
			oDate.getDate(); // 获取系统日，
			oDate.getHours(); //获取系统时，
			oDate.getMinutes(); //分
			oDate.getSeconds(); //秒
			var fontStyle="<style> table,td,th {font-size:14px;} </style>";
			
			var printTime = oDate.getFullYear()+"年"+oDate.getMonth()+1+"月"+oDate.getDate()+"日";
			$('span[id=printTime]').text(printTime);
		
			var LODOP=getLodop();
			LODOP.PRINT_INITA("20mm","${dyzdbj}mm");
			$('div[id^=divs]').each(function(i){
				guid.push($(this).find("[name=guid]").val());
				var limitRows = 9;
				var trCounts = $(document.getElementById("divs"+i)).find('table tr').length;
				if(trCounts>limitRows){
					
					var sums = parseInt(trCounts/limitRows) + 1;
					
					for(var ii=0;ii<sums;ii++){
						var newDoms = $(document.getElementById("divs"+i)).clone();
						newDoms.find('table tr').remove();
						newDoms.find('table').append($(document.getElementById("divs"+i)).find('table tr:first').clone());
						$(document.getElementById("divs"+i)).find('table tr').each(function(j){
							var $this = $(this);
							if(($this.index()>=(ii*limitRows))&&($this.index()<((ii+1)*limitRows))&&$this.index()!=0){
								newDoms.find('table').append($this.clone());
								
							}
						});
						
						LODOP.NEWPAGE();
						var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
						newDoms.find('span[id=indexNums]').text((ii+1));
						newDoms.find('span[id=sumNums]').text('/'+sums);
						newDoms.find('span[id=printTime]').text(printTime);
						LODOP.ADD_PRINT_HTML("0","0","${ymkd}mm","170mm",fontStyle+newDoms.html());
						LODOP.SET_PRINT_STYLEA(2,"ReadOnly",0);
						LODOP.SET_PRINT_PAGESIZE(2, 0, 0,"A4");
						//LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");//纵向A4
					}
				}else{
					LODOP.NEWPAGE();
					var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
					var finalDoms = $(document.getElementById("divs"+i));
					finalDoms.find('span[id=indexNums]').text('1');
					finalDoms.find('span[id=sumNums]').text('/1');
					finalDoms.find('span[id=printTime]').text(printTime);
					LODOP.ADD_PRINT_HTML("0","0","${ymkd}mm","170mm",fontStyle+finalDoms.html());
					LODOP.SET_PRINT_STYLEA(2,"ReadOnly",0);
					LODOP.SET_PRINT_PAGESIZE(2, 0, 0,"A4");
					finalDoms.find('span[id=indexNums]').text('');
					finalDoms.find('span[id=sumNums]').text('');
					
				}
			});
			$.ajax({
					type:"post",
					url:"${ctx}/kjhs/pzxx/pzlr/doUpdateOfPrint",
					dataType:"json",
					data:"guid="+guid.join(","),
					success:function(val){	
						LODOP.PREVIEW();
					}
				});
	};	
	 $(document).ready(function(){
		
		var oDate = new Date(); //实例一个时间对象；
			oDate.getFullYear();   //获取系统的年；
			oDate.getMonth()+1;   //获取系统月份，由于月份是从0开始计算，所以要加1
			oDate.getDate(); // 获取系统日，
			oDate.getHours(); //获取系统时，
			oDate.getMinutes(); //分
			oDate.getSeconds(); //秒
			
			var printTime = oDate.getFullYear()+"年"+oDate.getMonth()+1+"月"+oDate.getDate()+"日";
			$('span[id=printTime]').text(printTime);
		
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
   		$("#ze2").text(ze);
	}
	init();
	function init(){
		$('td[id=bxzje]').each(function(){
			var je = $(this).text();
			$(this).text((smalltoBIG(parseFloat(je))).replace("undefined", "零").replace("undefined", "零"));
		});
		var zje= "${zbMap.jfzje }";
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