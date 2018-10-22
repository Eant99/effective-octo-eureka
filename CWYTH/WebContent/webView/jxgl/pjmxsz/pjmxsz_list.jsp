<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>评价模型设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">		
		<form id="myform" class="form-inline" action="" style="padding-top: 10px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>模型编号</label>
					<input type="text" id="txt_mbbh" class="form-control" name="mbbh" table="K" placeholder="请输入模型编号">
				</div>
				<div class="form-group">
					<label>模型名称</label>
					<input type="text" id="txt_mbmc" class="form-control" name="mbmc"  table="K" placeholder="请输入模型名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_pldel">批量删除</button>
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
				            <th>模型编号</th>
				            <th>模型名称</th>
				            <th>设置人</th>
				            <th>设置日期</th>
				            <th>是否启用</th>
				            <th>备注</th>
				            <th style="text-align:center;">操作</th>
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
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data":"MXBH","width":100,"render":function(data,type,full,mate){
    	   return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
       }},
       {"data": "MXMC",defaultContent:""},
       {"data": "SZR",defaultContent:""},
       {"data": "SZRQ",defaultContent:"","class":"text-center"},
       {"data": "SFQY","render":function(data,type,full,mate){
    	  if(data=="1"){
    		  return '是';
    	  }else{
    		  return '否';
    	  }
       }},
       {"data": "BZ",defaultContent:""},
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":80}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/pjmxsz/getPageList",[2,'asc'],columns,0,1,setGroup);
  	//添加按钮
   	$("#btn_add").click(function(){
   		//doOperate("${ctx}/wldwsz/goEditPage");
   		location.href="${ctx}/webView/jxgl/pjmxsz/pjmxsz_edit.jsp";
   	});
  //编辑按钮
   	$(document).on("click",".btn_upd",function(){
//    		var guid = $(this).attr("guid");
//    		doOperate("${ctx}/wldwsz/goEdit1Page?guid="+guid,"U");
   	    location.href="${ctx}/webView/jxgl/pjmxsz/pjmxsz_reEdit.jsp";
   	});  	
   	//查看按钮
   	$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
   		location.href="${ctx}/webView/jxgl/pjmxsz/pjmxsz_reEdit.jsp";
   		//doOperate("${ctx}/wldwsz/goLookPage?guid="+guid,"L");
   	});
    //单条删除
 	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).attr("guid");
 		alert("删除成功！")
   	});
    //批量删除
 	$("#btn_pldel").click(function() {
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		if (checkbox.length > 0) {
			var id = [];
			
			checkbox.each(function() {
				id.push($(this).val());
			
			});
			
			confirm("确认要删除？","",function(){
				alert("删除成功")
			});//confirm
		} else {
			alert("请选择至少一条信息！");
		}
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