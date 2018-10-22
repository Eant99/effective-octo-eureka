<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>帮助目录维护</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="container-fluid">
		<div class="list">
	    	<!-- 列表操作按钮组    开始 -->
	        <div class="clearfix">
	            <div class="pull-left sub-title text-primary">帮助目录维护信息</div>
	            <div class="btn-group pull-right" role="group">
	                <button class="btn btn-default" id="btn_add">增加</button>
	                <button id="btn_del" class="btn btn-default">批量删除</button>
	            </div>
		        <!-- 列表操作按钮组 -->
	        </div>
	        <hr class="hr-normal"/>
	        <div class='responsive-table'>
	            <div class='scrollable-area'>
	                 <table id="mydatatables" class="table table-striped table-bordered">
					    <thead>
					    <tr>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>目录编号</th>
					        <th>目录名称</th>
					        <th>排序序号</th>
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
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
   	//添加按钮
   	$("#btn_add").click(function(){
   		select_commonWin("${ctx}/bzml/goEditPage?operateType=C","帮助目录维护","400","450");
   	});
   	//修改操作
   	$(document).on("click",".btn_upd",function(){
		var bh = $(this).parents("tr").find("[name='bh']").val();
		select_commonWin("${ctx}/bzml/goEditPage?operateType=U&bh="+bh,"帮助目录维护", "400", "450");
	});
 	//单条删除操作
	$(document).on("click",".btn_delxx",function(){
   		var bh = $(this).parents("tr").find("[name='bh']").val();
   		doDel("bh="+bh,"${ctx}/bzml/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='bh']").filter(":checked");
   		if(checkbox.length>0){
   			var bh = [];
   			checkbox.each(function(){
   				bh.push($(this).val());
   			});
   			doDel("bh="+bh.join(","),"${ctx}/bzml/doDelete",function(val){
   	   			table.ajax.reload();
   	   		},function(val){
   	   		},bh.length);
   		}else{
   			alert("请选择至少一条信息删除");
   		}
   	});
	//列表数据
   	var columns = [
		{"data": "BH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="bh" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "BH",defaultContent:""},
	   	{"data": "MC",defaultContent:""},
	   	{"data": "ZT",defaultContent:""},
	   	{"data":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":95,}
	];
   	table = getDataTable("mydatatables","${ctx}/bzml/getPageList",[2,'asc'],columns);
});
</script>
</body>
</html>