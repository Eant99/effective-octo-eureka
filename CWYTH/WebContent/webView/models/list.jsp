<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
/* 	.dataTables_scrollHeadInner{
		width:860px ! important;
	}
	table.dataTable{
		width:860px ! important;
	} */
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-bottom: 2px;padding-top: 8px">
    		<div class="search-simple">
				<div class="form-group">
					<label>流程名称</label>
					<input type="text"  class="form-control input-radius" id="txt_gjc" name="name_"   placeholder="请输入流程模型名称">
					
					<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				</div>
				<div class="btn-group pull-right"role="group" >
		                <button type="button" class="btn btn-default" id="btn_add">新增</button>
						<button type="button" class="btn btn-default" id="btn_dr">导入</button>
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
				          <th>部署状态</th>
				          <th>流程模型类型</th>
				         <th>流程模型名称</th>
						<th>版本号</th>
						<th>创建时间</th>
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
			{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		   		return data;
			},"width":30,"searchable": false,"class":"text-center"},
			{
				"data" : "SFBSS",
				defaultContent : "",
				"width" : '3%'
			},
			{
				"data" : "LCMXMC",
				defaultContent : "",
				"width" : 50
			},
			{
				"data" : "NAME_",
				orderable : false,
				'render' : function(data, type, full, meta) {
					return data;
				},
				"width" : 100,
				"searchable" : false
			},
			{
				"data" : "BSSJ",
				defaultContent : "",
				"width" : 80
			},
			{
				"data" : "CREATE_TIME_",
				defaultContent : "",
				"class":"text-center",
				"width" : 80
			},
			
			{"data":"",defaultContent:"","render":function(data, type, full, meta) {
					if(full.LCMXMC=="自定义表单"){
						return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_bs">部署</a>|<a href="javascript:void(0);" class="btn btn-link btn_exp">导出</a>|<a href="javascript:void(0);" class="btn btn-link btn_cklct">查看流程图</a>|<a href="javascript:void(0);" class="btn btn-link btn_qd">启动流程</a>';
					}else{
						return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_bs">部署</a>|<a href="javascript:void(0);" class="btn btn-link btn_exp">导出</a>|<a href="javascript:void(0);" class="btn btn-link btn_cklct">查看流程图</a>';
					}
				},
				orderable : false,
				"width" : 95
			} ];
	table = getDataTableByListHj("mydatatables",
			"${pageContext.request.contextPath}/model/view", [ 5,
					'desc' ], columns,0,1,setGroup);
});
//添加按钮
$(document).on("click","#btn_add",function(){
					select_commonWin(
							"${pageContext.request.contextPath}/model/goEditPage",
							"添加流程模型定义", "450", "450", "yes");
					return false;
				});


//导入按钮
$(document).on("click","#btn_dr",function(){

					select_commonWin(
							"${pageContext.request.contextPath}/model/goDrPage",
							"导入流程模型定义", "450", "480", "yes");
					return false;
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

//部署
$(document).on("click",".btn_bs",
		function() {
	var id = $(this).parents("tr").find("[name='id']").val();
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/model/"+id+"/deploy",
		dataType:"json",
		success:function(val){
			 if(val.success == "true"){
				 alert("部署成功！")
				 table.ajax.reload();
			}else {
				 alert("流程图错误请重新修改！");
			}
		}
		
	});
			return false;
			
		});
//启动
$(document).on("click",".btn_qd",
		function() {
			var id = $(this).parents("tr").find("[name='id']").val();
			$.ajax({
				type:"post",
				data:"modelId="+id,
				url:"${pageContext.request.contextPath}/model/doCheckStartForm",
				dataType:"json",
				async:false,
				success:function(val){
					 if(val.success == "true"){
						 select_commonWin(
									"${pageContext.request.contextPath}/model/"+id+"/startlc",
									"启动表单", "450", "450", "yes");
						/*  window.location.href="${pageContext.request.contextPath}/model/"+id+"/startlc" */
					}else {
						 alert("该流程此方式启动无效");
					}
				}
				
			});
			
			
		});
//批量删除按钮
$(document).on("click","#btn_del",function() {
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
//修改操作
$(document).on("click",".btn_upd",
		function() {
			var gid = $(this).parents("tr").find("[name='id']").val();
			doOperate("${pageContext.request.contextPath}/modeler.html?modelId="+gid,"L");
		});
//导出Excel
$(document).on("click",".btn_exp",
		function() {
			var json = searchJson("searchBox");
			var id = $(this).parents("tr").find("[name='id']").val();
			window.location.href="${pageContext.request.contextPath}/model/"+id+"/export"
		});
//查看流程图
$(document).on("click",".btn_cklct",
		function() {
	var id = $(this).parents("tr").find("[name='id']").val();
	$.ajax({
		type:"post",
		data:"modelId="+id,
		url:"${pageContext.request.contextPath}/model/doCheckAdd",
		dataType:"json",
		success:function(val){
			 if(val.success == "true"){
				 window.location.href="${pageContext.request.contextPath}/model/"+id+"/viewGraphic"
			}else {
				 alert("请先保存在查看流程图！");
			}
		}
		
	});
		return  false;	
			
		});
</script>
</body>
</html>