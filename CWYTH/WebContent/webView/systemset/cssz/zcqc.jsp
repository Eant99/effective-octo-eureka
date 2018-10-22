<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产清查设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<div class="clearfix">
			<div class="pull-left sub-title text-primary">资产清查设置</div>
			<div class="btn-group pull-right" role="group">
				<button class="btn btn-default" type="button" id="btn_add">添加新过程</button>
				<button class="btn btn-default" type="button" id="btn_start">开始清查</button>
				<button class="btn btn-default" type="button" id="btn_end">结束清查</button>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
	    	<div class='scrollable-area'>
	        	<table id="mydatatables" class="table table-striped table-bordered">
					<thead>
						<tr>
							<th><input type="checkbox" class="select-all"/></th>
							<th>序号</th>
							<th>编号</th>
							<th>描述</th>
							<th>开始日期</th>
							<th>结束日期</th>
							<th>状态</th>
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
	var columns = [
		{"data": "QCBH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="qcbh" zt="'+full.ZT+'" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,"width":41,searchable: false,"class":"text-center"},
		{"data": "QCBH",defaultContent:""},
		{"data": "QCMS",defaultContent:""},
		{"data": "KSRQ",defaultContent:""},
        {"data": "JSRQ",defaultContent:""},
        {"data": "ZTMC",defaultContent:"",orderable:false},
	];
	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/zcqc/getPageList?flag=zcqc",[2,'desc'],columns,0,1,setGroup);
	//添加新过程按钮
	$(document).on("click","#btn_add",function(){
		//添加新过程，判断是否存在已添加的，或者正在进行的数据，有，不进行添加
		$.ajax({
			type:"post",
			data:"flag=zcqc",
			url:"${pageContext.request.contextPath}/zcqc/doCheck",
			dataType:"json",
			success:function(val){
				 if(val.success=='true'){
					doSave(null,"myform","${pageContext.request.contextPath}/zcqc/doSave?flag=zcqc",function(val){
						table.ajax.reload();
					});
				}else{
					alert("存在已添加或正在进行的过程，不允许添加！");
				} 
			}
		});
	  });
	//开始清查
	$("#btn_start").click(function(){
		var check = $("#mydatatables").find("[name='qcbh']");
		if(check.attr("zt")=='2'){
			$.ajax({
				type:"post",
				data:"flag=zcqc"+"&qcbh="+check.val(),
				dataType:"json",
				url:"${pageContext.request.contextPath}/zcqc/doStart",
				success:function(val){
					if(val.success){
						alert(val.msg);
						table.ajax.reload();
					}else{
						alert(val.msg);
					}
				}
			});
		}else{
			alert("没有可以开始清查的数据！");
		}
	});
	//结束清查
	$("#btn_end").click(function(){
		var check = $("#mydatatables").find("[name='qcbh']");
		if(check.attr("zt")=='1'){
			confirm("确定结束清查？",{title:"提示"},function(){
				$.ajax({
					type:"post",
					data:"flag=zcqc"+"&qcbh="+check.val(),
					dataType:"json",
					url:"${pageContext.request.contextPath}/zcqc/doEnd",
					success:function(val){
						if(val.success){
							alert(val.msg);
							table.ajax.reload();
						}else{
							alert(val.msg);
						}
					}
				});
			});
		}else{
			alert("没有可以结束清查的数据！");
		}
		
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