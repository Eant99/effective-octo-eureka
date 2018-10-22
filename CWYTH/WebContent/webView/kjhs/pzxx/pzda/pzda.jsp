<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证档案</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.input_info{
		width:100px;
	}
	button{
/* 		background-color: #00acec !important; */
/* 		color: white !important; */
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>会计年度</label>
					<select style="width:70px;" class="form-control select2">
						<option value="0">2017年</option>
						<option value="1">2016年</option>
						<option value="2">2015年</option>
					</select>
					<label>会计期间</label>
					<select style="width:100px;" class="form-control select2">
						<option value="0">第一季度</option>
						<option value="1">第二季度</option>
						<option value="2">第三季度</option>
						<option value="3">第四季度</option>
					</select>
					<label>&nbsp;凭&nbsp;证&nbsp;字</label>
					<select style="width:60px;" class="form-control select2">
						<option value="0">记</option>
					</select>
					<label> &nbsp;开始凭证号</label>
					<input type="text" name="" class="input_info form-control" value="" />
	             	<label>结束凭证号</label>
	             	<input type="text" name="" class="input_info form-control" value="" />
					<label>档案编号</label>
					<input type="text"  name="" class="input_info form-control" value="" />
				</div>
				<div class="btn-group pull-right" >
					<button type="button" class="btn btn-default" id="btn_cx"><i class="fa icon-cx"></i>生成</button>
        		</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				        	<th style="text-align:center;">序号</th>
				            <th style="text-align:center;">编号</th>
				            <th style="text-align:center;">会计年度（年）</th>
				            <th style="text-align:center;">凭证字</th>
				            <th style="text-align:center;">开始凭证</th>
				            <th style="text-align:center;">结束凭证</th>
				            <th style="text-align:center;">附件张数（张）</th>
				            <th style="text-align:center;">档案日期</th>
				            <th style="text-align:center;">档案编号</th>
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var columns = [
					{"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          			return data;},"width":41,"searchable":false,"class":"text-center"},
	               {"data": "PZBH",defaultContent:"0001","id":"tr_pzrq"},
	               {"data": "FHYJ",defaultContent:"2017","class":"text-center"},
	               {"data": "FHYJ",defaultContent:"字","id":"tr_zy"},
	               {"data": "FHYJ",defaultContent:"10252","id":"tr_kmbh"},
	               {"data": "FHYJ",defaultContent:"105200","id":"tr_kmmc"},
	               {"data": "FHYJ",defaultContent:"0","class":"text-right"},
	               {"data": "FHYJ",defaultContent:"2017-10-16","class":"text-center"},
	               {"data": "FHYJ",defaultContent:"2017101610001","id":"tr_bm","class":"text-center"},
	             ];
	           table = getDataTableByListHj("mydatatables","${ctx}/pzlr/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
	$("[id=btn_cx]").click(function(){
		 alert("操作成功");
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