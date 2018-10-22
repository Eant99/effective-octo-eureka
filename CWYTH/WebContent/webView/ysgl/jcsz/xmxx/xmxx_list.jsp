<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位机构设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox">
			<form id="myform" class="form-inline" role="form" action="">
				<div class="search-simple">
					<div class="form-group">
						<label>项目编号</label> <input type="text" 
							class="form-control input-radius" name="xmbh" table="K"
							placeholder="请输入项目编号">
					</div>
					<div class="form-group">
						<label>项目名称</label> <input type="text" id="txt_mc"
							class="form-control input-radius" name="xmmc" table="K"
							placeholder="请输入项目名称">
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
								<th>项目编号</th>
								<th>项目名称</th>
								<th>所属项目分类</th>
								<th>上级项目</th>
								<th>有无上限</th>
								<th>上限金额（万元）</th>
								<th>是否允许超支</th>
								<th>超支比例</th>
								<th>提醒百分比(%)</th>
								<th>项目年度</th>
								<th>所属系部</th>
								<th>项目性质</th>
								<th>是否启动</th>
								<th>启用时间</th>
								<th>完成时间</th>
								<th>开支范围</th>
								<th>操作</th>
							</tr>
						</thead>
						 <tbody >
				    </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
	$(function() {
// 		//联想输入提示
// 		$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
		//列表数据
	    var columns = [
	       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
	       },"width":10,'searchable': false},
	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	          	return data;},"width":41,"searchable":false,"class":"text-center"},  
	          	
	       {"data": "XMBH",defaultContent:""},
	       {"data": "XMMC",defaultContent:"","class":"text-left"},
	       {"data": "SSXMFLMC",defaultContent:"","class":"text-left"},       
	       {"data": "SJXM",defaultContent:"","class":"text-left"},
	       {"data": "YWSXMC",defaultContent:"","class":"text-left"},
	       {"data": "SXJE",defaultContent:"","class":"text-right"},
	       {"data": "SFYXCZMC",defaultContent:"","class":"text-center"},
	       {"data": "CZBL",defaultContent:"","class":"text-right"},
	       {"data": "TXBFB",defaultContent:"","class":"text-right"},
	       {"data": "XMND",defaultContent:"","class":"text-center"},       
	       {"data": "SSXB",defaultContent:"","class":"text-left"},
	       {"data": "XMXZMC",defaultContent:"","class":"text-left"},
	       {"data": "SFQDMC",defaultContent:"","class":"text-left"},
	       {"data": "QYSJ",defaultContent:"","class":"text-center"},
	       {"data": "WCSJ",defaultContent:"","class":"text-center"},
	       {"data": "KZFW",defaultContent:"","class":"text-right"},
	            
	       {"data": "GUID",'render':function(data, type, full, meta){
		   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" dwbh="'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" dwbh="'+full.GUID+'">删除</a>';
	      },orderable:false,"width":220}
	     ];
	    	table = getDataTableByListHj("mydatatables","${ctx}/xmxxt/getPageList",[2,'asc'],columns,0,1,setGroup);
		//新增
		$("#btn_add").click(function() {
			doOperate("${ctx}/xmxxt/goXmxxEditPage?dwbh=${param.dwbh}", "C");//operateType=C
		});
		//修改
		$(document).on("click", ".btn_upd", function() {
			var dwbh = $(this).attr("dwbh");
			console.log("__"+dwbh);
			doOperate("${ctx}/xmxxt/goXmxxEditPage?dwbh="+dwbh, "U");
		});
		
	 //删除
		 
		$(document).on("click",".btn_delxx",function(){
	   		var dwbh = $(this).attr("dwbh");
	   		console.log("__"+dwbh);
	   		doDel("dwbh="+dwbh,"${ctx}/xmxxt/doDelete",function(val){
	   			table.ajax.reload();
	   		},function(val){
	   		},"1");
	   	});
		//批量删除
		$("#btn_del")
				.click(
						function() {
							var checkbox = $("#mydatatables").find(
									"[name='guid']").filter(":checked");
							var djdw = $("#mydatatables").find(".djdw").filter(
									":checked");
							
							if (djdw.length > 0) {
								alert("包含最顶级单位，不允许删除！");
							} else {
								if (checkbox.length > 0) {
									var dwbh = [];
									checkbox.each(function() {
										dwbh.push($(this).val());
									});
									doDel(
											"dwbh=" + dwbh.join("','"),
											"${ctx}/xmxxt/doDelete",
											function(val) {
												table.ajax.reload();
											}, function(val) {
											}, dwbh.length);
								} else {
									alert("请选择至少一条信息删除！");
								}
							}

						});
		
		
		/* //导出Excel
	   	$("#btn_exp").click(function(){
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
	   		doExp("","${ctx}/xmxxt/expExcel","项目信息","${pageContext.request.contextPath}",id);
	   	}); */

		//导出Excel
	   	$("#btn_exp").click(function(){
	   		var json = searchJson("searchBox");
	   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
			var guid = [];
			checkbox.each(function(){
				guid.push($(this).val());
			});
	   		doExp(json,"${ctx}/xmxxt/expExcel?guid="+guid.join("','"),"项目信息表","${pageContext.request.contextPath}",guid.join(","));
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
</body>
</html>