<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<base target="_blank" />
<title>工具下载维护</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
	    		<div class="search-simple">
					<div class="form-group">
						<label>文件名称</label>
						<input type="text" class="form-control input-radius" name="wjmc"  table="Z" placeholder="请输入文件名称">
					</div>
					<div class="form-group">
						<label>文件类型</label>
						<input type="text" class="form-control input-radius" name="wjlx"  table="Z" placeholder="请输入文件类型">
					</div>
					<div class="form-group">
						<label>文件描述</label>
						<input type="text" class="form-control input-radius" name="wjms"  table="Z" placeholder="请输入文件描述">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
					<div class="btn-group pull-right" role="group">
		                <div class="btn-group pull-right" role="group">
							<button class="btn btn-default" type="button" id="btn_add">增加</button>
							<button class="btn btn-default" type="button"  id="btn_del">批量删除</button>
						</div>
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
							<th><input type="checkbox" class="select-all" /></th>
							<th>序号</th>
							<th>文件名称</th>
							<th>文件类型</th>
							<th>文件描述</th>
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
<script src="${pageContext.request.contextPath}/static/javascript/public/download.js"></script>
<script>
$(function () {  
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
            return '<input type="checkbox" name="id" class="keyId" xtbz="'+full.XTBZ+'" fname="'+full.FNAME+'" path="'+full.PATH+'"  value="' + data + '">';
       },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
        },"width":41,"searchable": false,"class":"text-center"},
        {"data": "WJMC",'render':function (data, type, full, meta){
       	 if(data==undefined){
       		 return '<a href="javascript:void(0);" class="btn btn-link btn_xz" style= "color:red;" >'+ "" +'</a>';
       	 }else{
       		return '<a href="javascript:void(0);" class="btn btn-link btn_xz" style= "color:red;" >'+ data +'</a>';
       	 }
        }},
        {"data": "WJLX",defaultContent:""},
        {"data": "WJMS",defaultContent:""},
        {'render':function(data, type, full, meta){
        	if(full.XTBZ=='0'){
        		return '<a style="color: red" class="btn btn-link" href="${pageContext.request.contextPath}'+full.PATH+'" target="_blank">下载</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
        	}else{
        		return '<a href="javascript:void(0);" class="btn btn-link btn_xz">下载</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
        	}
        },orderable:false,"width":95}
     ];
    table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/xzwhb/getPageList",[2,'asc'],columns,0,1,setGroup);
  	
    //添加页面（弹窗） 
   	$("#btn_add").click(function(){
   		select_commonWin("${pageContext.request.contextPath}/xzwhb/goXzwhPage?operateType=C","工具下载维护","850","610","yes");
   	});
  	//下载页面
  $(document).on("click",".btn_xz",function(){
		var fname = $(this).parents("tr").find("[name='id']").attr("fname");
		var path = $(this).parents("tr").find("[name='id']").attr("path");
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/xzwhb/doCheckWj",
			dataType:"json",
			data:"filePath="+path,
            success:function(val){	                	
	            if(val.success == 'true') {
		           FileDownload("${pageContext.request.contextPath}/xzwhb/fileDownload",fname,path);
	            } else{
	            	alert(val.msg);
	            }
	            close(index);
            },
            error:function(){
            	alert("请稍后再试！");
            },
            beforeSend:function(){
					index = loading(2);
				}
		});	
   	});
	//单条删除操作
	$(document).on("click",".btn_delxx",function() {
		var guid = $(this).parents("tr").find("[name='id']").val();
		var path = $(this).parents("tr").find("[name='id']").attr("path");
		var xtbz = $(this).parents("tr").find("[name='id']").attr("xtbz");
		doDel("guid="+guid+"&path="+path+"&xtbz="+xtbz,"${pageContext.request.contextPath}/xzwhb/delectGjxz",function(val){
 			table.ajax.reload();
 		},function(val){
 		},"1"); 
	});
  	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		guid.push($(this).val());
   	   		});
	   		doDel("guid="+guid.join(","),"${pageContext.request.contextPath}/xzwhb/delectGjxz",function(val){
	   			table.ajax.reload();
	   		},function(val){
	   			
	   		},guid.length);
   		}else{
   			alert("请选择至少一条信息删除！");
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