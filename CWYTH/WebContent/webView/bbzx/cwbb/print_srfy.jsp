<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入费用表</title>
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
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:62%;margin:0 auto">
					<h4 style="text-align:center;">收入费用表(${title})</h4>
					<h6 style="margin-left:45.5555%;">${sysDate}${title}</h6>
					<caption style="text-align:left;">
					编制单位：${map.XXMC}
					<div style="float:right;margin-right:2%;">
						单位:元
					</div>
					</caption>
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
				            <th style="text-align:center;width:35%">${two}</th>
				            <th style="text-align:center;width:35%">${three}</th>				           
				        </tr>
					</thead>
				    <tbody>
				    <c:forEach var="list" items="${list}" varStatus="i">
				 		<tr class="tr_${i.index+1}">
				 			<td style="height:28px;">
				 				${list.XMMC}
				 			</td>
				 			<td style="text-align:right;height:28px;">
				 				${list.BYS}
				 			</td>
				 			<td style="text-align:right;height:28px;">
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
		location.href="${ctx}/srfy/toUrl?status=${status}&sysDate=${sysDate}&jzpz=${jzpz}&bblx=${bblx}"
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
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
	var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}table{width:100%!important;}</style>"
	LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle+document.getElementById("print_div").innerHTML);
	//LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("print_div").innerHTML);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
	LODOP.SET_PRINT_STYLEA(0,"Vorient",3);		
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
	LODOP.SET_PRINT_STYLEA(0,"FontColor","#FF0000");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.PREVIEW();	
};	
</script>
</body>
</html>