<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>补助学金录入</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<input type="hidden" name="qc" value="${qc}">
	<input type="hidden" name="bmh" value="${bmh}">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>方案名称</label>
					<input type="text" id="txt_famc" class="form-control input-radius" name="FAMC" table="K" placeholder="请输入方案名称">
				</div>
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control input-radius" name="XMMC" table="K" placeholder="请输入项目编号">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i> 查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_add">增加</button>
<!-- 	                <button type="button" class="btn btn-default" id="btn_imp">批量导入</button> -->
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
				        <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>方案编号</th>
				        <th>方案名称</th>
				        <th>发放金额（元）</th>
				        <th>摘要</th>
				        <th>项目编号</th>
				        <th>发放方式</th>
				        <th>发放时间</th>
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
$(function () {
	var columns = [
	       		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
       				return '<input type="checkbox" class="keyId" name="keyId" value="' + data + '" guid = "'+full.GUID+'" >';
	       	    },"width":10,'searchable': false},//0
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-left"},//1
	       	 	{"data": "FABH",'render':function (data, type, full, meta){
	       			return '<div class="text-left"><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
			    },"width":100},
	       	   	{"data": "FAMC",defaultContent:"","class":"text-left"},
	       		{"data": "FFJE",defaultContent:"","class":"text-right"},
	       	 	{"data": "ZY",defaultContent:"","class":"text-left"},
	       		{"data": "XMMC",defaultContent:"","class":"text-left"},
			    {"data": "FFFS",defaultContent:"","class":"text-left"},
			    {"data": "FFSJ",defaultContent:"","class":"text-center"},
	       	   	{"data": "GUID",'render':function (data, type, full, meta){
	       	   		if(full.SFFH=='1'){
	       	   			return '';
	       	   		}else{
	       	   		return '<a class="btn btn-link btn_upd" guid = "'+full.GUID+'" >编辑</a>|<a  class="btn btn-link btn_delxx" guid = "'+full.GUID+'" fabh = "'+full.FABH+'">删除</a>';//btn_delxx	
	       	   		}
          			
	       		},orderable:false,"width":95}
	       	];
	       	//表数据
          	table = getDataTableByListHj("mydatatables","${ctx}/xsbzxjlr/getPageList?treedwbh=${dwbh}&qc=${qc}&bmh=${bmh}",[4,'asc'],columns);
   	//新增
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/xsbzxjlr/goEditPage","C");//operateType=C?dwbh=${param.dwbh}&qc=${qc}&bmh=${bmh}
   	});
   	//编辑
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");
   		var jsbh = $(this).attr("jsbh");
		doOperate("${ctx}/xsbzxjlr/goEditPage?guid="+guid+"&jsbh="+jsbh,"U");
	});
	//查看
  	$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
   		doOperate("${ctx}/xsbzxjlr/goEditPage?guid="+guid,"L");
   	});
	//删除
	$(document).on("click",".btn_delxx",function(){
		var fabh = $(this).attr("fabh");
   		doDel("fabh="+fabh,"${ctx}/xsbzxjlr/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
	//批量删除
		$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
 		if(checkbox.length>0){
 	   		var guid = [];
 	   		checkbox.each(function(){
 	   			guid.push($(this).val());
 	   		});
 	   		doDel("guid="+guid.join("','"),"${ctx}/xsbzxjlr/doDelete2",function(val){
					location.reload();
   	   	   	},function(val){
   	   	   	},guid.length);
   	   	}else{
   	   		alert("请选择至少一条信息删除！");
   	   	}
   	});
  	//数据导入
	$("#btn_imp").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/jcsz/jsxxsz/txl_imp3.jsp", "导入经费信息", 450,350);
		return false;
	});
   	//导出Excel
   	$("#btn_exp").click(function(){
   		var id = [];
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
// 		alert(id);
// 		return;
   		doExp(json,"${ctx}/xsbzxjlr/expExcel","补助学金录入信息","${pageContext.request.contextPath}",id);
   	});
   	
    //查询联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	//查询弹窗
   	$("#btn_dwld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息","920","630");
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
</html>

