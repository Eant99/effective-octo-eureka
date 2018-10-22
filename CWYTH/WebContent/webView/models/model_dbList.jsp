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
<title>我的待办</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<div class="container-fluid">
			<div class="search" id="searchBox">
				<form id="myform" class="form-inline" role="form" action=""
					onkeydown="if(event.keyCode==13){return false;}">
					<div class="search-simple">
						<div class="form-group">


							<label>流程名称</label> <input type="text" id="txt_gjc"
								class="form-control input-radius" name="name_" table="T"
								placeholder="请输入流程名称">
							<button type="button" class="btn btn-primary" id="btn_search">
								<i class="fa icon-chaxun"></i>查 询
							</button>
						</div>
					</div>
				</form>
			</div>
			<div class="list">
				<!-- 列表操作按钮组    开始 -->
				<div class="clearfix">
					<div class="pull-left sub-title text-primary">流程列表</div>
					<div class="btn-group pull-right">
						<!-- <button type="button" id="btn_del" class="btn btn-default">批量删除</button>
												<button id="btn_plsq" class="btn btn-default">批量授权</button>
												<button id="btn_qxsq" class="btn btn-default">取消讲师</button>
						<button type="button" id="btn_exp" class="btn btn-default">导出认证项目</button> -->
					</div>
				</div>
				<!-- 列表操作按钮组    结束 -->
				<hr class="hr-normal" />
				<div class='responsive-table'>
					<div class='scrollable-area'>
						<!-- datatables  开始 -->
						<table id="mydatatables"
							class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><input type="checkbox" class="select-all" />全选</th>
									<th>流程实例id</th>
									<th>名称</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<!-- datatables  结束 -->
					</div>
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
							return '<input type="checkbox" class="keyId" name="id" value="' + data + '">';
						},
						"width" : 10,
						'searchable' : false
					},
					{
						"data" : "PROC_INST_ID_",
						orderable : false,
						'render' : function(data, type, full, meta) {
							return '<input type="hidden"  name="PROC_INST_ID_" value="' + data + '">'+data;
						},
						"width" : '3%',
						"searchable" : false
					},
					{
						"data" : "NAME_",
						defaultContent : ""
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
						"data" : function(data, type, full, meta) {
							return '<a href="javascript:void(0);" class="btn btn-link btn_ls">办理记录</a>';
						},
						orderable : false,
						"width" : 95
					} ];
			table = getDataTable("mydatatables",
					"${pageContext.request.contextPath}/model/myStartProcessList", [ 3,
							'desc' ], columns);
		});
		//历史
		$(document).on(
					"click",
					".btn_ls",
					function() {
						var id = $(this).parents("tr").find("[name='PROC_INST_ID_']").val();
						window.location.href="${pageContext.request.contextPath}/process/processLs?processInstanceId="+id+"&type=lssl";
	 		});
		//单条删除操作
		 $(document).on("click",".btn_delxx",function() {
			var id = $(this).parents("tr").find("[name='id']").val();
			confirm("确定删除此流程模型？",{title:"提示"},function(){
				 $.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/model/"+id+"/delete",
					success:function(val){
						
						if(val!=null){
							alert("删除流程模型成功");
							table.ajax.reload();
						}else{
							alert("返回参数异常！");
						}
					}
				});
			});
			return false;
			}); 
		
		//批量删除按钮
		$("#btn_del")
				.click(
						function() {
							var checkbox = $("#mydatatables").find(
									"[name='id']").filter(":checked");
							if (checkbox.length > 0) {
								var id = [];
								checkbox.each(function() {
									id.push($(this).val());
								});
								doDel(
										"id=" + id.join(","),
										"${pageContext.request.contextPath}/rzxm/doDelete",
										function(val) {
											table.ajax.reload();
										}, function(val) {
										}, id.length);
							} else {
								alert("请选择至少一条信息删除");
							}
						});
		
	</script>
</body>
</html>