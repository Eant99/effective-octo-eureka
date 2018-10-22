<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目授权</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
	<input type="hidden" id="xmbh3" value="${xmbh3}">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<div class="search-simple">
					<div class="form-group">
						<label>项目编号</label>
						<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="K" placeholder="请输入项目编号">
					</div>
					<div class="form-group">
						<label>项目名称</label> 
						<input type="text" id="txt_xmmc" class="form-control" name="xmmc" table="K" placeholder="请输入项目名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
					
					<div class="btn-group pull-right" role="group">
						<button type="button" class="btn btn-default" id="btn_plsq">批量授权</button>
						<button type="button" class="btn btn-default" id="btn_info">授权日志</button>					
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
						        <th>年度</th>
						        <th>项目编号</th>
						        <th>项目名称</th>
						        <th>负责人</th>
				           		<th>项目大类</th>
				        		 <th>项目类型</th>
						        <th>经费类型</th>
						        <th>项目开始时间</th>
						        <th>项目结束时间</th>
						        <th>预算金额（万元）</th>
				        		<th>剩余金额（万元）</th>
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
	//列表数据
	var rBtnXh = 0;//单选框序号
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" xmbh="'+full.XMBH+'" guid = "'+full.GUID+'" fzr="'+full.FZR+'" bmbh="'+full.BMBH+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
   	   	{"data": "ND",defaultContent:""},
   	 	{"data": "XMBH",defaultContent:""},
   	 	{"data": "XMMC",defaultContent:""},
   	 	{"data": "FZRMC",defaultContent:""},
   		{"data": "XMDLMC",defaultContent:""},
   	 	{"data": "XMLX",defaultContent:""},
   	 	{"data": "JFLXMC",defaultContent:""},
   	 	{"data": "KSSJ",defaultContent:"","class":"text-center"},
   	 	{"data": "JSSJ",defaultContent:"","class":"text-center"},
   	   	{"data": "YSJE",defaultContent:"","class":"text-right"},
   	   	{"data": "SYJE",defaultContent:"","class":"text-right"},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" bmbh = "'+full.BMBH+'" guid="'+full.GUID+'" fzr="'+full.FZR+'" xmbh="'+full.XMBH+'">管理项目授权</a>';
     },orderable:false,"width":220,"class":"text-center"}
     ];
//     table = getDataTableByListHj("mydatatables","${ctx}/json/wsbx/jcsz/xmsq.json",[2,'asc'],columns,0,1,setGroup);
	table = getDataTableByListHj("mydatatables","${ctx}/xmsq/getPageList",[2,'asc'],columns,0,1,setGroup);
	
	
  	//批量授权按钮
  	$(document).on("click","#btn_plsq",function(){
//    	$("#btn_plsq").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var dwbh = $(this).attr("dwbh");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		var xmbh = [];
   	   		var bmbh = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   			xmbh.push($(this).attr("xmbh"));
   	   			bmbh.push($(this).attr("bmbh"));
   	   		});
   	   	$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/xmsq/doCheck1?xmbh="+xmbh.join(","),
				dataType:"json",
				async:false,
				success:function(val){
					 if(val.success == "true"){
						 doOperate("${ctx}/xmsq/goSqEditPage?guid="+guid+"&xmbh="+xmbh+"&bmbh="+bmbh, "C");
					}else {
                   alert("没有授权权限！");
					}
				}
				
			});
//    	   	doOperate("${pageContext.request.contextPath}/webView/wsbx/jcsz/xmsq/xmsq_plsq.jsp");
			console.log("____"+guid+"___"+xmbh);
   	 		
   		}else{
   			alert("请选择至少一条信息授权!");
   		}
   		
   	});
	//管理项目授权按钮
   	$(document).on("click",".btn_upd",function(){
   		var fzr  = $(this).attr("fzr");
   		var guid = $(this).attr("guid");
   		var bmbh=$(this).attr("bmbh");
   		var xmbh = $(this).attr("xmbh");
   			$.ajax({
   				type:"post",
   				url:"${pageContext.request.contextPath}/xmsq/doCheck?xmbh="+xmbh+"&fzr="+fzr+"&bmbh="+bmbh,
   				dataType:"json",
   				async:false,
   				success:function(val){
   					 if(val.success == "true"){
   						doOperate("${ctx}/xmsq/getSqPageList?guid="+guid+"&xmbh="+xmbh+"&bmbh="+bmbh, "C");
   					}else {
                       alert("没有授权权限！");
   					}
   				}
   				
   			});
//    		doOperate("${ctx}/xmsq/getSqPageList?guid="+guid+"&xmbh="+dwbh, "C");
   	}); 
// 	$(document).on("click",".btn_upd",function(){
//    		var dwbh = $(this).attr("dwbh");
// 		doOperate("${ctx}/bmjfsz/goEditPage?dwbh="+dwbh,"U");
// 	});
	//授权日志
	$(document).on("click","#btn_info",function(){		
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var xmbh = $("#mydatatables").find("[name='guid']").filter(":checked").attr("xmbh");
		//alert(checkbox);
		if(checkbox.length>1){
			alert("请选择一条信息！");
		}else{
			if(checkbox.length>0){
	// 			doOperate("${pageContext.request.contextPath}/webView/wsbx/jcsz/xmsq/xmsqrz_list.jsp");
				console.log("xmbh3"+xmbh);
				doOperate("${ctx}/xmsq/goRzPage?xmbh3="+xmbh,"U");
	   		}else{
	   			alert("请选择一条信息!");
	   		}
		}
	});
	
	//弹窗
	$(document).on("click","#btn_bsqr",function(){
// 	$("#btn_bsqr").click(function(){
		select_commonWin("${pageContext.request.contextPath}/window/rypage?controlId=txt_bsqr","人员信息","920","630");
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