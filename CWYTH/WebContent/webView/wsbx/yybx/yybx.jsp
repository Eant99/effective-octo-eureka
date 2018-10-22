<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>日常报销列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.table{
		width:80%!important;
		margin:0 auto!important;
	}
	
	table{
		border-collapse:collapse!important;
	}
	table #head>tr>th{
		text-align:center!important;
	}
	span .red{
		color:red;
	}
	span .green{
		color:green;
	}
	caption {
	    font-size: 6mm;
	    padding-top: 8px;
	    padding-bottom: 8px;
	    color:#333;
	    text-align:center!important;
    }
    td[id^=db_] span{
    	cursor:pointer;
    }
    .table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		border-bottom-width: 1px!important;
    }
    table{
    	border-bottom:none!important;
    }
    td,th{
    	border-right:none!important;
    }
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
					<caption>预约报销时间</caption>
		        	<thead id="head">
				        <tr>
				            <th rowspan="2" style="border-top::1px solid #ddd;border-left:none;">开始时间</th>
				            <th rowspan="2" style="border-top::1px solid #ddd;">结束时间</th>
				            <th colspan="2" style="border-top::1px solid #ddd;">${sysDate}</th>
				            <th colspan="2" style="border-top::1px solid #ddd;">${beforeDate}</th>
				            <th colspan="2" style="border-top::1px solid #ddd;">${afterDate}</th>
				        </tr>
				        <tr>
				        	<th style="border-top:none;">预约人数</th>
				        	<th style="border-top:none;">预约</th>
				        	<th style="border-top:none;">预约人数</th>
				        	<th style="border-top:none;">预约</th>
				        	<th style="border-top:none;">预约人数</th>
				        	<th style="border-top:none;">预约</th>
				        </tr>
					</thead> 
				    <tbody>
				    	<c:forEach var="lists" items="${list}" varStatus="i">
				    		<tr i="tr_${i.index+1}">
				    			<td style="text-align:center;border-top:none;border-left:none;">${lists.KSSJ}</td>
				    			<td style="text-align:center;border-top:none;">${lists.JSSJ}</td>
				    			<td style="text-align:right;border-top:none;">
				    			<span class="red db_${i.index+1}">1</span>/
				    			<span class="green" style="color:green;">${lists.YXYYRS}</span>
				    			</td>
				    			<td style="text-align:center;border-top:none;" id="db_${i.index+1}" num="${lists.YXYYRS}"><span style="color:#00acec;">预约</span></td>
				    			<td style="text-align:right;border-top:none;">
				    			<span class="red db_${i.index+1}">2</span>/
				    			<span class="green" style="color:green;">${lists.YXYYRS}</span>
				    			</td>
				    			<td style="text-align:center;border-top:none;" id="db_${i.index+1}" num="${lists.YXYYRS}"><span style="color:#00acec;">预约</span></td>
				    			<td style="text-align:right;border-top:none;">
				    			<span class="red db_${i.index+1}">3</span>/
				    			<span class="green" style="color:green;">${lists.YXYYRS}</span>
				    			</td>
				    			<td style="text-align:center;border-top:none;" id="db_${i.index+1}" num="${lists.YXYYRS}"><span style="color:#00acec;">预约</span></td>
				    		</tr>
				    	</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	//点击预约
	var bool = false;
	$("td[id^=db_] span").click(function(){
		if(bool){
			alert("您已经预约，不可重复预约");
			return;
		}
		var num = $(this).parents("td").attr("num");
		var id = $(this).parents("td").attr("id");
		var yy = $(this).parents("td").prev().find("[class*='"+id+"']").text();
		if(isNaN(num)||num==0||num==""||Number(yy)>=Number(num)){
			alert("可预约人数不足");
			return;
		}
		var sjyy = Number(yy)+1;
		$(this).parents("td").prev().find("[class*='"+id+"']").text(sjyy);
		alert("预约成功");
		bool = true;
	});
});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</body>
</html>