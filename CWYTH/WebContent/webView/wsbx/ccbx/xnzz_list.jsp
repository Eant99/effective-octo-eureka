<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%> 
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
/*     	border-bottom-width: 0px; */
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}
	.search-simple .select2-container--default .select2-selection--single {
   		background-color: #fff;
   		border: 1px solid #ccc;
    	border-radius: 4px 4px 4px 4px;
	}
	th{
		text-align:center; 
	}
</style>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>经费项目</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  table="A" placeholder="请输入经费项目">
				</div>
				<div class="form-group">
					<label>转账用途</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  table="A" placeholder="请输入转账用途">
				</div>
				<div class="form-group">
					<label>部门</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  table="A" placeholder="请输入部门">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
<!-- 				<div class="btn-group pull-right" role="group"> -->
<!-- 	               <button type="button" class="btn btn-default btn_select">选择</button> -->
<!-- 				</div> -->
			</div>
        </form>
    </div>
	<div class="container-fluid">
        <div class='responsive-table'>
            <div class='scrollable-area' style="margin-top: 5px">
				<table id="mydatatables" class="table table-striped table-bordered">
					<thead>
					    <tr>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>转账日期</th>
				            <th>部门</th>
				            <th>经费项目</th>
				            <th>转账人员</th>
				            <th>转账金额</th>
				            <th>转账用途</th>
					        <th>操作</th>
					    </tr>
				    </thead>
				    <tbody>
				    	 <tr>
					        <td style="text-align:center;"><input type="checkbox" class="select-all"/></th>
					        <td style="text-align:center;">2017-11-10</td>
					        <td>教务处</td>
					        <td>这是一个项目</td>
					        <td>超级管理员</td>
					        <td style="width:120px;text-align:right;">100.00</td>
					        <td>项目转账经费</td>
					        <td style="text-align:center;"><button type="button" class="btn btn-default btn_select">选择</button></td>
					    </tr>
				    </tbody>
				</table>
            </div>
        </div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
	$(function () {
		var winId = getTopFrame().layer.getFrameIndex(window.name);
		$(document).on("click",".btn_select",function(){
//     		getIframeControl("${param.pname}","${param.controlId}").val(val);
//         	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframWindow("${param.pname}").xnzzJs();//
        	close(winId);
	    });
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