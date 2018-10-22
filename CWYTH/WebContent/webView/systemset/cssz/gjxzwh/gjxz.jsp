<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<base target="_blank" />
<title>工具下载</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.fyrow,.bottom{
		display:none;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<div class="clearfix">
			<div class="pull-left sub-title text-primary">工具下载</div>
			<div class="btn-group pull-right" role="group">
			</div>
		</div>
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
	$('#mydatatables').DataTable({
        "ajax": {
            url:"${pageContext.request.contextPath}/xzwhb/getPageList"//获取数据的方法
        },
        "lengthMenu":1000,
        "order": [2,'asc'],
        "serverSide": true,
        "columns":  [
                     {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
                         return '<input type="checkbox" name="id" class="keyId"  fname="'+full.FNAME+'" path="'+full.PATH+'"  value="' + data + '">';
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
                    	 return '<a href="javascript:void(0);" class="btn btn-link btn_xz">下载</a>';
                     },orderable:false,"width":95}
                  ],
        "dom":"<'row fyrow'<'page-left wxts'li>><'row'<t>> <'row bottom'<'pull-right'p ><'total'o>>"
	});
  	
  	//下载页面
  $(document).on("click",".btn_xz",function(){
		var GUID = $(this).parents("tr").find("[name='id']").val();
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
            }
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