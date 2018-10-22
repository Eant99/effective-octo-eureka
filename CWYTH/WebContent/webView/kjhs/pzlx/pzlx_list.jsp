<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证类型代码</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		  <div class="search-simple">
				<div class="form-group">
					<label>类型名称</label>
					<input type="text" id="txt_lxmc" class="form-control" name="lxmc"  table="p" placeholder="请输入类型名称">
				</div>
				<div class="form-group">
					<label>凭证字</label>
					<input type="text" id="txt_pzz" class="form-control" name="pzz"  table="p" placeholder="请输入凭证字">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</a></button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <!-- <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button> -->
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
				            <th>类型编号</th>
				            <th>类型名称</th>
				            <th>凭证字</th>
				            <th>借方必有科目</th>
				            <th>贷方必有科目</th>
				            <th>凭证必有科目</th>
				            <th>凭证必无科目</th>
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
           {"data": "LXBH",defaultContent:""},           
 	       {"data": "LXMC",defaultContent:""},
 	       {"data": "PZZ",defaultContent:""},
 	       {"data": "JFBYKM",defaultContent:""},
 	       {"data": "DFBYKM",defaultContent:""},
 	       {"data": "PZBYKM",defaultContent:""},
 	       {"data": "PZBWKM",defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":220,"class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/pzlx/getPageList",[2,'asc'],columns,0,1,setGroup);
   // table = getDataTableByListHj("mydatatables","${ctx}/json/kjhs/pzsz/pzlxdm/pzlxdmList.json",[2,'asc'],columns,0,1,setGroup);
	
  //添加按钮
  $(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		
   		doOperate("${ctx}/pzlx/goAddPage");
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	doOperate("${ctx}/pzlx/goEdit?guid="+guid,"U");
	   	
   	});
  //批量删除
  $(document).on("click","#btn_del",function(){
//    	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var guid = []
   			 if(checkbox.length>0){
   	   			var guid = [];
   	   			checkbox.each(function(){
   	   				guid.push($(this).val());
   	   			});
   	   			 doDel("guid="+guid.join("','"),"${ctx}/pzlx/pzlxsDel",function(val){
   	   			window.location.href = "${ctx}/pzlx/gopzlxListPage";
   	   	   		},function(val){
   	   	   		},guid.length); 
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		} 
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();		
	 	confirm("确定删除？","",function(){
			$.ajax({
	   			url:"${ctx}/pzlx/pzlxDel",
	   			data:"guid="+guid,
	   			type:"post",
	   			async:"false",
	   			success:function(val){
	   				alert("删除成功");
   	   			window.location.href = "${ctx}/pzlx/gopzlxListPage";
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