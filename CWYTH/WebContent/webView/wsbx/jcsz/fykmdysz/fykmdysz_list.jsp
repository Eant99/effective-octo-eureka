<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>费用科目对应设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	
</style>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<div class="search-simple">
					<div class="form-group">
						<label>费用分类</label> <select id="drp_fyfl" class="form-control "
							name="fyfl" table="K">
							<option value="">未选择</option>
							<option value="1">日常报销</option>
							<option value="2">差旅费</option>
							<option value="4">公务接待费</option>
						</select>
					</div>
					<div class="form-group">
						<label>费用名称</label> <input type="text" id="txt_fymc"
							class="form-control" name="fymc" table="K" placeholder="请输入费用名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
<!-- 					<div id="btn_search_more"> -->
<!-- 						<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span> -->
<!-- 						<div class="search-more"> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label>费用分类</label>  -->
<!-- 								<select id="drp_fyfl" class="form-control select2" -->
<!-- 									name="fyfl" table="K"> -->
<!-- 									<option value="">未选择</option> -->
<!-- 									<option value="1">日常报销</option> -->
<!-- 									<option value="2">差旅费</option> -->
<!-- 									<option value="3">预支付</option> -->
<!-- 									<option value="4">公务接待费</option> -->
<!-- 								</select> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label>分类名称</label> <input type="text" id="txt_flmc" -->
<!-- 									class="form-control input-radius" table="K" name="flmc" -->
<!-- 									placeholder="请输入分类名称"> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label>借方科目</label> <input type="text" id="txt_kmbh" -->
<!-- 									class="form-control input-radius" table="K" name="kmbh1" -->
<!-- 									placeholder="请输入借方科目"> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label>贷方科目</label> <input type="text" id="txt_kmbh" -->
<!-- 									class="form-control input-radius" table="K" name="kmbh2" -->
<!-- 									placeholder="请输入贷方科目"> -->
<!-- 							</div> -->

<!-- 							<div class="search-more-bottom clearfix"> -->
<!-- 								<div class="pull-right"> -->
<!-- 									<button type="button" class="btn btn-primary" id="btn_search"> -->
<!-- 										<i class="fa icon-chaxun"></i> 查 询 -->
<!-- 									</button> -->
<!-- 									<button type="button" class="btn btn-default" id="btn_cancel"> -->
<!-- 										取 消</button> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="btn-group pull-right" role="group">
						<button type="button" class="btn btn-default" id="btn_add">增加</button>
						<button type="button" class="btn btn-default" id="btn_del">批量删除</button>
						<button type="button" class="btn btn-default" id="btn_updxx">批量修改</button>
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
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>费用分类</th>
								<th>费用名称</th>
								<th>上级费用分类</th>
								<th>借贷方向</th>
								<th>科目编号</th>
								<th>科目名称</th>
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
	//导出Excel
//    	$("#btn_exp").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var id = [];
// 		checkbox.each(function(){
// 			id.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/fykmdysz/expExcel?sjfl=${param.fyfl}","费用科目","${pageContext.request.contextPath}",id.join(","));
//    	});
	
//    	$("#btn_exp").click(
// 			function() {
// 				var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/fykmdysz/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","费用科目应对设置.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
// 			});

	//导出Excel
	$(document).on("click","#btn_exp",function(){
//    	$("#btn_exp").click(function(){
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
   		doExp("","${ctx}/fykmdysz/expExcel2?fyfls=${param.fyfl}","费用科目应对设置","${pageContext.request.contextPath}",id);
   	});

	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "FYFL",defaultContent:""},
       {"data": "FYMC",defaultContent:""},
       {"data": "SJFL",defaultContent:""},
       {"data": "JDFX",defaultContent:"","width":41},
       {"data": "KMBH",defaultContent:""},
       {"data": "KMMC",defaultContent:""},
       
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":100,"class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/fykmdysz/getPageList?fyfls=${param.fyfl}",[2,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		doOperate("${ctx}/fykmdysz/goEditPage", "C");
   	});
	
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/fykmdysz/goEditPage?guid="+guid, "U");
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
//    	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		console.log("checkbox8888888888888+"+checkbox);
   		var id = $("#mydatatables").find(".djdw").filter(":checked");
   		//console.log("checkbox====id=====+"+id);
   		var a = $("[name=guid]").filter(":checked");
   		var m=0; 
   			if(checkbox.length>0){
   				$.each(a,function(){
   			    	var b = $(this).val();
   			    	console.log("checkbox====guid=====+"+b);
   			    	if(b=="903F854DA5A14D4BA9241B2F6C5DC302"||b=="9280E9E047964099A6E5577046023E03"){
   			    		m++;
   			    		
   			    	}
   			    	if(m>0){
   			    		alert("存在不可删除信息");
   			    	}else{
   			    		var id = [];
   		   	   			checkbox.each(function(){
   		   	   				id.push($(this).val());
   		   	   			
   		   	   			});
   		   	   			confirm("确认要批量删除"+id.length+"条信息？",{title:"提示"},function(index){
   		   	    			$.ajax({
   		   	    	   			url:"${ctx}/fykmdysz/doDelete",
   		   	    	   			data:"guid="+id,
   		   	    	   			type:"post",
   		   	    	   			async:"false",
   		   	    	   			success:function(val){
   		   	    	   				alert("删除成功");
   		   	    	   				table.ajax.reload();
   		   	    	   			}
   		   	    	   		});
   		   	    		
   		   	   			}); 
   			    	}
   				});
   	   			
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		}
   		
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		if(guid=="903F854DA5A14D4BA9241B2F6C5DC302"||guid=="9280E9E047964099A6E5577046023E03"){
			alert("该信息不允许删除！");
		}else{
   		confirm("确定删除？","",function(){
   			$.ajax({
   				url:"${ctx}/fykmdysz/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("删除成功");
   	   				table.ajax.reload();
   	   			}
   	   		});
   		});
		}
	});
	//弹窗
	$(document).on("click","#btn_dwbh",function(){
// 	$("#btn_dwbh").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwbh","单位信息","920","630");
    });
	$(document).on("click","#btn_rybh",function(){
// 	$("#btn_rybh").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_rybh","人员信息","920","630");
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