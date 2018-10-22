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
<title>流程实例</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
 				<div class="form-group">
 					<label>流程名称</label> 
 					<input type="text"  class="form-control input-radius" id="txt_gjc" name="pname" table="K"   placeholder="请输入流程名称">
 					&nbsp;&nbsp;
 					<label>流程发起人</label> 
 					<input type="text"  class="form-control input-radius" name="kqr"  table="K"  placeholder="请输入流程发起人姓名">
	 				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button> 
				</div> 
 				<div class="btn-group pull-right"role="group" > 
<!--  		                <button type="button" class="btn btn-default" id="btn_add">新增</button> -->
<!--  						<button type="button" class="btn btn-default" id="btn_dr">导入</button> -->
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
									<th>名称</th>
									<th>流程发起人</th>
									<th>当前任务名称</th>
									<th>当前任务处理人</th>
									<th>开始时间</th>
									<th>状态</th>
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
							return '<input type="checkbox" class="keyId" name="id" taskid='+full.TASKID+'  value="' + data + '"><input type="hidden"  name="PROC_INST_ID_"   value="' + full.PROC_INST_ID_ + '">';
						},
						"width" : 10,
						'searchable' : false
					},
					{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
				   		return data;
					},"width":30,"searchable": false,"class":"text-center"},
					{
						"data" : "PNAME",
						defaultContent : ""
					},
					{
						"data" : "KQR",
						defaultContent : ""
					},
					{
						"data" : "TASKNAME",
						defaultContent : ""
					},
					{
						"data" : "ASSIGNEE",
						defaultContent : ""
					},
					
					{
						"data" : "START_TIME_",
						defaultContent : ""
					},
					{
						"data" : "SUSPENSION_STATE",
						defaultContent : ""
					},

					{
						"data" :"",'render': function(data, type, full, meta) {
							 if("2"==full.SUSPENSION_STATE_){ 
								return '<a href="javascript:void(0);" class="btn btn-link btn_yx">运行</a>|<a href="javascript:void(0);" class="btn btn-link btn_gb">关闭</a>|<a href="javascript:void(0);" class="btn btn-link btn_ls">历史</a>';
							 }else{//未提交
								return '<a href="javascript:void(0);" class="btn btn-link btn_zt">暂停</a>|<a href="javascript:void(0);" class="btn btn-link btn_gb">关闭</a>|<a href="javascript:void(0);" class="btn btn-link btn_ls">历史</a>';}
						} ,orderable : false,"width" : 95
					} ];
			table = getDataTableByListHj("mydatatables",
					"${pageContext.request.contextPath}/process/viewProcessInstance", [ 6,
							'desc' ],columns,0,1,setGroup);
		});
		
		//单条删除操作
		$(document).on(
				"click",
				".btn_delxx",
				function() {
					
					var id = $(this).parents("tr").find("[name='id']").val();
					doDel("id=" + id,
							"${pageContext.request.contextPath}/model/"+id+"/delete",
							function(val) {
								table.ajax.reload();
							}, function(val) {
							}, "1");
					return false;
				});
		//运行
	$(document).on(
				"click",
				".btn_yx",
				function() {
					var id = $(this).parents("tr").find("[name='PROC_INST_ID_']").val();
					$.post("${pageContext.request.contextPath}/process/activeProcessInstance",{'processInstanceId':id},function(val){table.ajax.reload(); });
				});
	//暂停
	$(document).on(
				"click",
				".btn_zt",
				function() {
					var id = $(this).parents("tr").find("[name='PROC_INST_ID_']").val();
					$.post("${pageContext.request.contextPath}/process/suspendProcessInstance",{'processInstanceId':id},function(val){table.ajax.reload(); });
					//window.location.href="${pageContext.request.contextPath}/process/suspendProcessInstance?processInstanceId="+id
					
				});
	//关闭
	$(document).on(
				"click",
				".btn_gb",
				function() {
					var id = $(this).parents("tr").find("[name='PROC_INST_ID_']").val();
					$.post("${pageContext.request.contextPath}/process/deleteProcessInstance",{'processInstanceId':id},function(val){table.ajax.reload(); });
				});
	//委派
	$(document).on(
				"click",
				".btn_wp",
				function() {
					var id = $(this).parents("tr").find("[name='id']").attr("taskid");
					$.post("${pageContext.request.contextPath}/process/delegateTask",{'taskId':id,'userId':'000000'},function(val){table.ajax.reload(); });
				});
	//历史
	$(document).on(
				"click",
				".btn_ls",
				function() {
					var id = $(this).parents("tr").find("[name='PROC_INST_ID_']").val();
					window.location.href="${pageContext.request.contextPath}/process/processLs?processInstanceId="+id;
 		});
	
	
	
	</script>
</body>
</html>