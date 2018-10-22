<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证类型管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.dataTables_scrollHeadInner{
 		width:580px ! important;
	} 
 	table.dataTable{ 
 		width:580px ! important;
 	} 
</style>
</head>
<body>
 <div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 10px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>凭证类型名称</label>
					<input type="text" class="form-control" name="PZLXMC" table="K" placeholder="请输入凭证类型名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               
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
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>凭证类型编号</th>
				            <th>凭证类型名称</th>
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
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
           	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "PZLXBH",defaultContent:"","width":50},
       {"data": "PZLXMC",defaultContent:"","width":150},     
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":100,"class":"text-center"}
     ];
	 table = getDataTableByListHj("mydatatables","${ctx}/pzlxNew/getPageList",[2,'asc'],columns,0,1,setGroup);
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		doOperate("${ctx}/pzlxNew/goEditPage","C");
   	});
  	//编辑按钮
	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/pzlxNew/goEditPage?guid="+guid,"U");
   	});
  
 	//删除
   	$(document).on("click",".btn_delxx",function(){
  		var guid = $(this).parents("tr").find("[name='guid']").val();
		confirm("确定删除该条信息？","",function(){
   			$.ajax({
   	   			url:"${ctx}/pzlxNew/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"true",
   	   			success:function(data){
   	   				var result = JSON.getJson(data);
  	   				if(result.success){
						alert("删除成功！");  	   					
	   	   				table.ajax.reload();
  	   				}
   	   			},
   	   			error:function(){
   	   				alert("抱歉，系统出现错误！");
   	   			}
   	   		});
   		});
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