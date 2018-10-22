<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<script src="${ctxStatic}/javascript/public/LodopFuncs.js"></script>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
			<div class="box-header clearfix">
			</div>
    		<div class="search-simple">
				<div class="btn-group pull-right" role="group">
					<button class="btn btn-default" type="button" id="btn_back">返回</button>
	                <button class="btn btn-default" type="button" id="btn_print">打印</button>
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid" id="div_print">
		<div class='responsive-table'>
			<div class='scrollable-area'>
				<h4 style="text-align:center;">科目余额表</h4>
		        <table id="mydatatables" class="table table-striped table-bordered">
		        	<caption style="text-align:left;">报表期间：${date}</caption>
					<thead>
		 				<tr>
		 					<th rowspan="2" style="text-align:center;"><input type="checkbox" name="kmbh" /></th>
						    <th rowspan="2" style="text-align:center;">科目编号</th>
						    <th rowspan="2" style="text-align:center;">科目名称</th>
						    <th colspan="2" style="text-align:center;">期初余额</th>
						    <th colspan="2" style="text-align:center;">本期发生</th>
						    <th colspan="2" style="text-align:center;">期末余额</th>
			   			</tr>
			   			<tr>
						    <th style="width:5%;text-align:center;">方向</th>
						    <th style="text-align:center;">余额</th>
						    <th style="text-align:center;">借方</th>
						    <th style="text-align:center;">贷方</th>
						    <th style="width:5%;text-align:center;">方向</th>
						    <th style="text-align:center;">余额</th>
			   			</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${list}" varStatus="i">
							<tr class="${i.index+1}">
							<td style="text-align:center;"><input type="checkbox" name="kmbh" /></td>
							<td>${list.KMBH}</td>
							<td>${list.KMMC}</td>
							<td>${list.QCFX}</td>
							<td style="text-align:right;">${list.QCYE}</td>
							<td style="text-align:right;">${list.BQJF}</td>
							<td style="text-align:right;">${list.BQDF}</td>
							<td>${list.QMFX}</td>
							<td style="text-align:right;">${list.QMYE}</td>
						</tr>
						</c:forEach>
						<tr>
							<td colspan="3" rowspan="2" style="text-align:center;">合计</td>
							<td>借</td>
							<td style="text-align:right;">${map.qcj}</td>
							<td rowspan="2" style="text-align:right;">${map.bqj}</td>
							<td rowspan="2" style="text-align:right;">${map.bqd}</td>
							<td>借</td>
							<td style="text-align:right;">${map.qmj}</td>
						</tr>
						<tr>
							<td>贷</td>
							<td style="text-align:right;">${map.qcd}</td>
							<td>贷</td>
							<td style="text-align:right;">${map.qmd}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>  
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function () {
	$("#btn_back").click(function(){
		location.href="${ctx}/kmye/kmyeList?gs=haha&date=${date}&flag=noooooo";
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
	LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle+document.getElementById("div_print").innerHTML);
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