<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.dataTables_scrollHeadInner{
 		width:630px ! important;
	} 
 	table.dataTable{ 
 		width:630px ! important;
 	} 
</style>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<div class="search-simple">
					<div class="form-group">
						<label>支付方式</label> 
						 <select id="drp_zffs" class="form-control "
							name="zffs" table="K">
							<option value="">全部</option>
							<c:forEach var="zffs" items="${zffs}">
								<option value="${zffs.DM }">${zffs.MC }</option>
							</c:forEach>
						 </select>
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>

					<div class="btn-group pull-right" role="group">
						<button type="button" class="btn btn-default" id="btn_add">增加</button>
						<button type="button" class="btn btn-default" id="btn_del">批量删除</button>
<!-- 						<button type="button" class="btn btn-default" id="btn_exp">导出Excel</button> -->
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
								<th>支付方式</th>		
								<th>科目编号</th>							
								<th>科目名称</th>
								<th>借贷方向</th>
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
	
	$(document).on("click","#btn_exp",function(){
// 	$("#btn_exp").click(function() {
		var id = [];
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/zffsdysz/expExcel2","支付方式科目应对设置","${pageContext.request.contextPath}",id);
		
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/zffsdysz/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","支付方式科目应对设置.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
			});
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "ZFFS1",defaultContent:""},
       
       {"data": "KMBH",defaultContent:""},
       {"data": "KMMC",defaultContent:""},
       {"data": "JDFX",defaultContent:"","width":41},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":100,"class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/zffsdysz/getPageList?fyfls=${param.fyfl}",[5,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		doOperate("${ctx}/zffsdysz/goAddpage", "C");
   	});
  	
	
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/zffsdysz/goEditPage?guid="+guid, "U");
   	});
	//批量赋值按钮
//    	$(document).on("click","#btn_updxx",function(){
//    		var guid = $(this).parents("tr").find("[name='guid']").val();
//    		doOperate("${ctx}/webView/wsbx/jcsz/fykmdysz/fykmdysz_edit.jsp", "C");
//    	});  
  //批量赋值按钮
  	$(document).on("click","#btn_updxx",function(){
// 		$("#btn_updxx").click(function() {
			var checkbox = $("#mydatatables").find("[name='guid']")
					.filter(":checked");
			var a = $("[name=guid]").filter(":checked");
			var m=0; 
			if (checkbox.length > 0) {
				$.each(a,function(){
   			    	var b = $(this).val();
   			    	console.log("checkbox====guid=====+"+b);
   			    	if(b=="903F854DA5A14D4BA9241B2F6C5DC302"||b=="9280E9E047964099A6E5577046023E03"){
   			    		m++;
   			    		
   			    	}
   			    	if(m>0){
   			    		alert("存在不可批量编辑信息");
   			    	}else{
				var id = [];
				checkbox.each(function() {
					id.push($(this).val());
				});
				 select_commonWin("${ctx}/fykmdysz/goUpdatePage?guid="+id,"批量赋值页面","500","400");
   			    	}
				});
			} else {
				alert("请选择至少一条信息！");
			}
		});
   //批量删除按钮
   $(document).on("click","#btn_del",function(){
//     $("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			confirm("确定删除所选"+checkbox.length+"条信息？","{title:'提示'}",function(){
   				var guid = [];
   	   	   		checkbox.each(function(){
   	   	   			guid.push($(this).val());
   	   	   		});
   	   			$.ajax({
   	   	   			//url:ADDRESS+"/srxm/delete",
   	   	   			url:"${ctx}/zffsdysz/doDelete",
   	   	   			data:"guid="+guid.join("','"),
   	   	   			type:"post",
   	   	   			async:true,
   	   	   			success:function(data){
   	   	   				var result = JSON.getJson(data);
   	   	   				if(result.success){
   							alert("删除成功！");  	   					
	   	   	   				table.ajax.reload();
   	   	   				}else{
   	   	   					alert("删除失败，请稍后重试！");
   	   	   				}
   	   	   			},
   	   			error:function(){
   	   				alert("抱歉，系统出现错误！");
   	   			}
   	   	   		});
   	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
   		confirm("确定删除？","",function(){
   			$.ajax({
   				url:"${ctx}/zffsdysz/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("删除成功");
   	   				table.ajax.reload();
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