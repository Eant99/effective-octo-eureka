<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- <base target="" /> -->
<title>历史任务</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
    		 <div class="search-simple">
 				<div class="form-group">
					<label>流程名称</label>
 					<input type="text"  class="form-control input-radius" id="txt_gjc" name="PROC_DEF_NAME_" table="T" placeholder="请输入流程名称"> 
	 				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
 				</div>
 				<div class="btn-group pull-right"role="group" >
<!--  		                <button type="button" class="btn btn-default" id="btn_add">新增</button>  -->
<!--  						<button type="button" class="btn btn-default" id="btn_dr">导入</button>  -->
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
				        <th><input type="checkbox" class="select-all" />全选</th>
				                   <th>序号</th>
									<th>标题</th>
									<th>流程实例ID</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>耗时</th>
									<th>操作</th>
				    </tr>
				    </thead>
				    <tbody></tbody>
				</table>
            </div>
        </div>
	</div>
	</div>
	<%@include file="/static/include/public-list-js.inc"%>
	<script
		src="${pageContext.request.contextPath}/static/javascript/public/download.js"></script>
	<script>
		$(function() {
			//列表数据
			var columns = [
					{
						"data" : "ID_",
						orderable : false,
						"render" : function(data, type, full, meta) {
							return '<input type="checkbox" class="keyId" name="id"   value="' + data + '">';
						},
						"width" : 10,
						'searchable' : false
					},
					{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
				   		return data;
					},"width":30,"searchable": false,"class":"text-center"},
					{
						"data" : "PROC_DEF_NAME_",
						defaultContent : ""
					},
				
					{
						"data" : "PROC_INST_ID_",
						"render" : function(data, type, full, meta) {
							return '<input type="hidden"  name="PROC_INST_ID_" value="' + data + '">'+data;
						},
						"width" : 10,
						'searchable' : false
					},
					
					{
						"data" : "START_TIME_",
						defaultContent : ""
					},
					{
						"data" : "END_TIME_",
						defaultContent : ""
					},
					{
						"data" : "DURATION_",
						defaultContent : ""
					},

					{
						"data" :"",'render': function(data, type, full, meta) {
								return '<a href="javascript:void(0);" class="btn btn-link btn_ls">历史</a>';
						} ,orderable : false,"width" : 95
					} ];
			table = getDataTableByListHj("mydatatables",
					"${pageContext.request.contextPath}/process/historicProcessInstances", [ 5,
							'desc' ], columns,0,1,setGroup);
		});
		
	

	//历史
	$(document).on(
				"click",
				".btn_ls",
				function() {
					var id = $(this).parents("tr").find("[name='PROC_INST_ID_']").val();
					window.location.href="${pageContext.request.contextPath}/process/processLs?processInstanceId="+id+"&type=lssl";
 		});
	
	
	</script>
</body>
</html>