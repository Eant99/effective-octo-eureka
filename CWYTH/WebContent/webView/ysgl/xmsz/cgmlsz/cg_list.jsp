<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>采购目录设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox">
			<form id="myform" class="form-inline" role="form" action="">
				<div class="search-simple">
					<div class="form-group">
						<label>目录代码</label> <input type="text" id="txt_mldm"
							class="form-control input-radius" name="mldm" table="K"
							placeholder="请输入目录代码">
					</div>
					<div class="form-group">
						<label>目录名称</label> <input type="text" id="txt_mlmc"
							class="form-control input-radius" name="mlmc" table="K"
							placeholder="请输入目录名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查 询
					</button>
					
					<div class="btn-group pull-right" role="group">
						<button type="button" class="btn btn-default" id="btn_add">增加</button>
						<button type="button" class="btn btn-default" id="btn_del">批量删除</button>
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
								<th><input type="checkbox" class="select-all" /></th>
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
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
	$(function() {
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
		      },orderable:false,"width":220,"class":"text-center"},
		     ];
// 		    table = getDataTableByListHj("mydatatables","${ctx}/cgmlsz/getPageList?fyfls=${param.fyfl}",[2,'asc'],columns,0,1,setGroup);
		    table = getDataTableByListHj("mydatatables","${ctx}/cgmlsz/getPageList?mldms=${param.fyfl}",[2,'asc'],columns,0,1,setGroup);
		
		//新增
		$("#btn_add").click(function() {
			doOperate("${ctx}/cgmlsz/goCGEditPage?guid=${param.fyfl}", "C"); // 左侧采购分类guid(即之前的费用分类fyfl)传到这个页面后，再传到cg_edit.jsp页面
		});
		//修改按钮
	   	$(document).on("click",".btn_upd",function(){
	   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   		doOperate("${ctx}/cgmlsz/goCGEditPage?guid="+guid, "U");
	   	});
		
		//单条删除
		$(document).on("click",".btn_delxx",function(){
			var guid = $(this).parents("tr").find("[name='guid']").val();
	   		confirm("确定删除？","",function(){
	   			$.ajax({
	   				url:"${ctx}/cgmlsz/doDelete",
	   	   			data:"guid="+guid,
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   			alert(val.replace("\"","").replace("\"",""));
	   	   				parent.location.reload(true);
	   	   			}
	   	   		});
	   		});
		});
		
		//批量删除按钮
	   	$("#btn_del").click(function(){
	   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	   		var id = $("#mydatatables").find(".djdw").filter(":checked");
	   			if(checkbox.length>0){
	   	   			var id = [];
	   	   			checkbox.each(function(){
	   	   				id.push($(this).val());
	   	   			});
	   	   			confirm("确认要删除这"+id.length+"条信息？",{title:"提示"},function(index){
	   	    			$.ajax({
	   	    	   			url:"${ctx}/cgmlsz/doDelete",
	   	    	   			data:"guid="+id,
	   	    	   			type:"post",
	   	    	   			//dateType:"json",
	   	    	   			async:"false",
	   	    	   			success:function(val){
	   	    	   				console.log(val);
// 	   	    	   				var d = eval("("+val+")");
// 	   	    	   				var data = JSON.stringify(val).key;
	   	    	   				alert(val.replace("\"","").replace("\"",""));
	   	    	   					parent.location.reload(true);
	   	    	   			}
	   	    	   		});
	   	    		
	   	   			}); 
	   	   		}else{
	   	   			alert("请选择至少一条信息！");
	   	   		}
	   		
	   	});
		
		//导出Excel
		$("#btn_exp").click(function() {
			var json = searchJson("searchBox");
			var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
			var id = [];
			checkbox.each(function() {
				id.push($(this).val());
			});
			console.log("___"+id.join(","));
			doExp(json, "${ctx}/cgmlsz/expExcel?treedwbh=${dwbh}&id="+id.join(","),"采购目录信息","${pageContext.request.contextPath}", id.join(","));
		});
		
		//查询联想输入
		$("#txt_dwld").bindChange("${ctx}/suggest/getXx", "R");
		//查询弹窗
		$("#btn_dwld").click(function() {
			select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息", "920", "630");
		});
	});
	$(function() {
		//列表右侧悬浮按钮
		$(window).resize(
				function() {
					$("div.dataTables_wrapper").width($("#searchBox").width());
					heights = $(window).outerHeight()
							- $(".search").outerHeight()
							- $(".row.bottom").outerHeight() - 20
							- $(".dataTables_scrollHead").outerHeight();
					$(".dataTables_scrollBody").height(heights);
					table.draw();
				});
	});
</script>
</html>