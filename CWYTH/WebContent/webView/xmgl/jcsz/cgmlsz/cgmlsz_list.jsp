<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>采购目录设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>目录代码</label>
					<input type="text" id="txt_mldm" class="form-control" name="mldm" table="A" placeholder="请输入目录代码">
				</div>
				<div class="form-group">
					<label>目录名称</label>
					<input type="text" id="txt_mlmc" class="form-control" name="mlmc"  table="A" placeholder="请输入目录名称">
				</div>
				<div class="form-group">
					<label>上级目录</label>
					<input type="text" id="txt_sjml" class="form-control" name="sjml"  table="A" placeholder="请输入上级目录">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
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
				            <th>目录代码</th>
				            <th>目录名称</th>
				            <th>上级目录</th>			       
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
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "MLDM",defaultContent:""},
       {"data": "MLMC",defaultContent:""},
       {"data": "SJML",defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":220,"class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/cgmlsz/getPageList",[2,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/cgmlsz/goCgmlszPage","C");
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功");
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();

   		doOperate("${ctx}/cgmlsz/goCgmlszPage?guid="+guid,"U");	
   	});
	//添加按钮
   	$("#btn_search").click(function(){
   		doOperate("${ctx}/cgmlsz/goCgmlszPage","C");
   	});
	//单条删除
   	$(document).on("click",".btn_delxx",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:ADDRESS+"/xmgl/jcsz/cgmlsz/doDeleteflow",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert(val);
   	   			window.location.href = "${ctx}/webView/xmgl/jcsz/cgmlsz/cgmlsz_list.jsp";
   	   			}
   	   		});
   		});
	});
	

    });

//列表右侧悬浮按钮
$(function() {		
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