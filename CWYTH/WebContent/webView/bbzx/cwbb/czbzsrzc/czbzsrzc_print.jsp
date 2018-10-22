<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>财政补助收入支出表-打印</title>
<%@include file="/static/include/public-list-css.inc"%>
<script src="${ctxStatic}/javascript/public/LodopFuncs.js"></script>
<style type="text/css">
</style>
</head>
<body>
<div class="fullscreen">
<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="search-simple">
			<div class="btn-group pull-right" role="group">
				<button type='button' class="btn btn-default" id="btn_back">返回</button>
				<button type='button' class="btn btn-default" id="btn_print">打印</button>
			</div>
		</div>
</div>
<div id="print_div" style="height:100%;">
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
			<table id="mydatatables" class="table table-striped table-bordered" border="1" bordercolor="#000000" style="border-collapse:collapse;width:100%;margin:0 auto 30px;" >
					<div id="print_title">
			<h2 style="text-align:center;">财政补助收入支出表</h2>
			<div style="text-align:right;font-size:12px;">会高校03表</div>
					<div style="text-align:left;font-size:12px;float:left;width:33%">编制单位：${map.XXMC}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：${sysDate }年度</div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
					</div>
<!-- 					<div class="allDiv" style="width:62%;height:20px;margin-left:18.55555%;"> -->
<!-- 						<div class="first" style="float:left;text-align:left;width:33%;"> -->
<%-- 							编制单位：${map.XXMC} --%>
<!-- 						</div> -->
<!-- 						<div class="second" style="float:left;text-align:center;width:28%;"> -->
<%-- 							${sysDate}${title} --%>
<!-- 						</div> -->
<!-- 						<div class="third" style="float:left;text-align:right;width:35%;"> -->
<!-- 							单位:元 -->
<!-- 						</div> -->
<!-- 					</div> -->
		        	<thead>
				        <tr>
				            <th style="text-align:center;width:35%">项目</th>
				            <th style="text-align:center;width:35%">本年数</th>
				            <th style="text-align:center;width:35%">上年数</th>				           
				        </tr>
					</thead>
				    <tbody>
				    <c:forEach var="list" items="${list}" varStatus="i">
				 		<tr class="tr_${i.index+1}">
				 			<td style="height:28px;padding:0px 1px">
				 				${list.XMMC}
				 			</td>
				 			<td style="text-align:right;height:28px;padding:0px 1px">
				 			${list.BYS}
				 			</td>
				 			<td style="text-align:right;height:28px;padding:0px 1px">
				 			${list.BNLJS}
				 			</td>
				 		</tr>
				 	</c:forEach>
				    </tbody>
				</table>					
			</div>
		</div>
	</div>
</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function () {
	$("#btn_back").click(function(){
		location.href="${ctx}/czbzsrzc/czbzsrzc_list?nd=${sysDate}&jzpz=${jzpz}&bblx=${bblx}"
	});
	$("#btn_print").click(function(){
		PreviewMytable();
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
function PreviewMytable(){
	var LODOP=getLodop();  
	LODOP.PRINT_INITA("7%","5%");
	LODOP.NEWPAGE();
	var strStyle="<style> table,td,th {border-collapse:collapse;font-size:12px} table{width:100%!important} thead tr th,tbody tr td{border:1px solid #000;}</style>"
	LODOP.ADD_PRINT_HTM(0,0,"94%","87%",document.getElementById("print_title").innerHTML);
	LODOP.ADD_PRINT_TABLE(0,0,"94%","87%",strStyle+document.getElementById("print_div").innerHTML);
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
	LODOP.SET_PRINT_PAGESIZE(1, 0,0 ,"A4");
	LODOP.PREVIEW();	
};	
</script>
</body>
</html>