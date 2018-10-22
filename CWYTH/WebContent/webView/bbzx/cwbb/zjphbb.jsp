<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金平衡表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.select2-selection{
		border-radius: 4px!important;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   	 	border-bottom-width: 0!important;
	}
	thead th{
		text-align:center!important;
	}
	.allDiv{
		width:100%;
		height:20px;
	}
	.first{
		float:left;
		text-align:left;
		width:33%;
	}
	.second{
		float:left;
		text-align:center;
		width:34%;
	}
	.third{
		float:left;
		text-align:right;
		width:33%;
	}
	.div_bottom{
    	width: 99%;
    	margin-top: 20px;
    	bottom: 20px;
   		background-color: #f3f3f3;
	}
	.bom{
		float:left;
		text-align:left;
		width:33%;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>选择日期</label>
					<input type="text" class="form-control date" name="ksrq"  value="2017-01-01"  data-format="yyyy-MM-dd">
					<label>至</label>
					<input type="text"  class="form-control date" name="jsrq"  value="2017-01-31"  data-format="yyyy-MM-dd">
				</div>
				<div class="form-group">
					<label>是否包含未记账凭证</label>
					<select style="width:70px;" class="form-control select2">
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
				</div>
				<div class="form-group">
					<label>是否包含转账凭证</label>
					<select style="width:70px;" class="form-control select2">
						<option value="2">否</option>
						<option value="1">是</option>
					</select>
				</div>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_exp">导出excel</button>
	               <button type="button" class="btn btn-default" id="btn_print">打印预览</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<center><h3>资金平衡表</h3></center>
			<div class='scrollable-area'> 
				<div class="allDiv">
					<div class="first">
						核算单位：山东国子软件
					</div>
					<div class="second">
						2017-01-01至2017-01-31
					</div>
					<div class="third">
						单位:元
					</div>
				</div>
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;">
		        	<thead>
				        <tr>
				            <th>资金来源</th>
				            <th>行次</th>
				            <th>年初数</th>
				            <th>期末数</th>
				            <th>资金来源</th>
				            <th>行次</th>
				            <th>年初数</th>
				            <th>期末数</th>
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
				<div class="div_bottom">
					<div class="bom">单位主要负责人(签字):</div>
					<div class="bom">财务负责人(签字):</div>
					<div class="bom">制表人(签字):</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
  	//打印预览
   	$("#btn_print").click(function(){
   		alert("暂无数据！");
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功！");
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