<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产编号保留位设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
	    <div class="clearfix">
	        <div class="pull-left sub-title text-primary">资产编号保留位设置&emsp;(可以设置多个类别组以方便通过保留位来确定资产类别)</div>
	        <div class="btn-group pull-right" role="group">
	            <button type='button' id="btn_add" class="btn btn-default" >增加</button>
	            <button type='button' id="btn_del" class="btn btn-default">批量删除</button>
	        </div>
		</div>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
				<table id="mydatatables" class="table table-striped table-bordered">
			      <thead>
			          <tr>
			              <th><input type="checkbox" class="select-all"/></th>
			              <th>序号</th>
			              <th>组编号</th>
			              <th>组名称</th>
			              <th>保留位</th>
			              <th>资产类别</th>
			              <th>操作</th>
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
$(function (){
   	//添加按钮
   	$("#btn_add").click(function(){
   		select_commonWin("${pageContext.request.contextPath}/hsortset/goEditPage?operateType=C","资产编号保留位设置","450","500");
   	});
   	//修改操作
   	$(document).on("click",".btn_upd",function(){
		var zbh = $(this).parents("tr").find("[name='zbh']").val();
 	   select_commonWin("${pageContext.request.contextPath}/hsortset/goEditPage?operateType=U&zbh="+zbh,"资产编号保留位设置","450","500");
   
	});
 	//查看操作
   	$(document).on("click",".btn_look",function(){
		var zbh = $(this).parents("tr").find("[name='zbh']").val();
 	   select_commonWin("${pageContext.request.contextPath}/hsortset/goEditPage?operateType=L&zbh="+zbh,"资产编号保留位设置","450","500");
	});
   	//单条删除操作
   	$(document).on("click",".btn_delxx",function(){
   		var zbh = $(this).parents("tr").find("[name='zbh']").val();
   		doDel("zbh="+zbh,"${pageContext.request.contextPath}/hsortset/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1"); 
   	});
   	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='zbh']").filter(":checked");
   		if(checkbox.length>0){
   			var zbh = [];
   			checkbox.each(function(){
   				zbh.push($(this).val());
   			});
   			doDel("zbh="+zbh.join(","),"${pageContext.request.contextPath}/hsortset/doDelete",function(val){
   	   			table.ajax.reload();
   	   		},function(val){
   	   		},zbh.length);
   		}else{
   			alert("请选择至少一条信息删除！");
   		} 
   	});
   	//列表数据
   	var columns = [
		{"data": "ZBH",orderable:false, "render": function (data, type, full, meta){
		    return '<input type="checkbox" class="keyId" name="zbh" value="' + data + '">';
		},"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		return data;
		},"width":41,"searchable": false,"class":"text-center"},
		{"data": "ZBH",defaultContent:""},
		{"data": "ZMC",'render':function (data, type, full, meta){
			return '<a href="javascript:void(0);" class="btn btn-link btn_look">'+ data +'</a>';
		}},
		{"data": "BLW",defaultContent:""},
		{"data": "FLMC",defaultContent:""},
		{"data":function (data, type, full, meta){
		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":95}
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/hsortset/getPageList",[2,'asc'],columns,0,1,setGroup);
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