<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>借款业务信息</title>
<%@include file="/static/include/public-list-css.inc"%> 
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>借&ensp;款&ensp;人</label>
					<input type="text" id="txt_jkr" class="form-control" name="jkr" table="A" placeholder="请输入借款人">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  table="A" placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
						<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
						<div class="search-more">

							<div class="form-group">
								<label>审核状态</label> <input type="text" id="txt_shzt"
									class="form-control input-radius" table="K" name="shzt"
									placeholder="请输入审核状态">
							</div>
							<div class="form-group">
								<label>申请日期</label> <input type="text" id="txt_sqr"
									class="form-control input-radius" table="K" name="sqr"
									placeholder="请输入申请日期">
							</div>
							<div class="search-more-bottom clearfix">
								<div class="pull-right">
									<button type="button" class="btn btn-primary" id="btn_search">
										<i class="fa icon-chaxun"></i> 查 询
									</button>
									<button type="button" class="btn btn-default" id="btn_cancel">
										取 消</button>
								</div>
							</div>
						</div>
					</div>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	                 <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <button type="button" class="btn btn-default" id="btn_pltj">批量提交 </button>
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
					        <th>审核状态</th>
					        <th>借款人</th>
					        <th>部门名称</th>
					        <th>项目编号</th>
					        <th>项目名称</th>
					        <th>借款金额（元）</th>
					        <th>申请日期</th>
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
	   	//列表数据
		var columns = [
			{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
		       	return '<input type="checkbox" class="keyId" name="guid" data-shzt="" value="' + data + '">';
		    },"width":10,'searchable': false},
			{"data":"_XH",orderable:false,"searchable": false,"class":"text-center"},
		   	{"data": "SHZT",defaultContent:"","class":"text-center"},
		   	{"data": "JKR",defaultContent:""},
		   	{"data": "BMMC",defaultContent:""},
		   	{"data": "XMBH",defaultContent:""},
		   	{"data": "XMMC",defaultContent:""},
		   	{"data": "JKJE",defaultContent:"0.00","class":"text-right","width":100},
		   	{"data": "SQRQ",defaultContent:"","class":"text-center"},
		   	{"data":function (data, type, full, meta){
		   		return '<a class="btn btn-link btn_update">编辑</a>|<a class="btn btn-link btn_delete">删除</a>|<a class="btn btn-link btn_submit">提交</a>|<a class="btn btn-link btn_view">查看</a>';
			},orderable:false,"class":"text-center","width":200}
		];
	   	table = getDataTableByListHj("mydatatables","${ctx}/json/wsbx/jkyw/jkyw.json?",[2,'asc'],columns,0,1,setGroup);
		//表格大小根据窗口变化而改变
		$(window).resize(function(){
	    	$("div.dataTables_wrapper").width($("#searchBox").width());
	    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
	    	$(".dataTables_scrollBody").height(heights);
	    	table.draw();
		});
		
	//按钮点击事件
	$(function() {
		//添加按钮
		$("#btn_add").click(function() {
			doOperate("${ctx}/jkyw/goAddPage?stepNum=1&mkbh=${param.mkbh}", "C");
		});
		//批量删除按钮
		$("#btn_del").click(
				function() {
					var checkbox = $("#mydatatables").find("[name='guid']")
							.filter(":checked");
					if (checkbox.length > 0) {
						confirm("确定删除"+checkbox.length+"条信息？", {title:"提示"}, function() {
							alert("删除成功");
						});
					} else {
						alert("请选择至少一条信息删除!");
					}
				});
		//批量提交按钮
		$("#btn_pltj").click(
				function() {
					var checkbox = $("#mydatatables").find("[name='guid']")
							.filter(":checked");
					if (checkbox.length > 0) {
						confirm("确认要批量提交" + checkbox.length + "条信息？", {
							title : "提示"
						}, function(index) {
							alert("提交成功！");
						});
					} else {
						alert("请选择至少一条信息提交!");
					}
				});
		//编辑按钮
		$(document).on("click",".btn_update",function() {
					var guid = $(this).parent().prevAll().find("input").val();
					doOperate("${ctx}/jkyw/goAddPage?stepNum=1&mkbh=${param.mkbh}", "C");
				});
		//单挑删除按钮
		$(document).on("click",".btn_delete",function() {
					confirm("确定删除？", "{title:提示信息}", function() {
						alert("删除成功");
					});
				});
		//查看按钮
		$(document).on("click", ".btn_view", function() {
			doOperate("${ctx}/jkyw/goViewPage?action=ck","V");
		});
		$(document).on("click", ".btn_submit", function() {
			alert("操作成功！");
		});
	});
</script>
</body>
</html>