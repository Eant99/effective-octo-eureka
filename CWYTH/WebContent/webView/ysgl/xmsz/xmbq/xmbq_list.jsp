<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目标签</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
 <div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>标签编号</label>
					<input type="text" id="txt_bqbh" class="form-control" name="bqbh" table="A" placeholder="请输入标签编号">
				</div>
				<div class="form-group">
					<label>标签名称</label>
					<input type="text" id="txt_bqmc" class="form-control" name="bqmc"  table="A" placeholder="请输入标签名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search">查询</button>

				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
<!-- 	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button> -->
<!-- 	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button> -->
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
				            <th>标签编号</th>
				            <th>标签名称</th>
				            <th>项目名称</th>
				            <th>备注</th>
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
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
$(function () {
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "BQBH",defaultContent:""},
       {"data": "BQMC",defaultContent:""},
       {"data": "XMMC",orderable:false,defaultContent:""},
       {"data": "BZ",orderable:false,defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":80,"class":"text-center"}
     ];
     table = getDataTableByListHj("mydatatables","${ctx}/xmbq/getPageList",[2,'asc'],columns,0,1,setGroup);
 	//添加按钮
 	$(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		doOperate("${ctx}/xmbq/goEditPage","C");
   	});
   	//修改按钮
	$(document).on("click",".btn_upd",function(){
		var guid = $(this).attr("guid");

   		doOperate("${ctx}/xmbq/goEditPage?guid="+guid,"U");
   	});
   //查看按钮
   $(document).on("click",".btn_look",function(){
	   var guid = $(this).attr("guid");
	   doOperate("${ctx}/pzmb/goLookPage?guid="+guid,"L");
   });
 //删除
  	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
		confirm("确定删除该条信息？","",function(){
  			$.ajax({
  	   			url:"${ctx}/xmbq/doDelete",
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