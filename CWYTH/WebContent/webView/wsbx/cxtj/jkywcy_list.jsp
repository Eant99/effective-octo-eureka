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
							<label>审核状态</label>
							<input type="text" id="txt_shzt" class="form-control input-radius" table="K" name="shzt" placeholder="请输入审核状态">
						</div>
						<div class="form-group">
							<label>借款人</label>
							<input type="text" id="txt_sqr" class="form-control input-radius" table="K" name="sqr" placeholder="请输入借款人">
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
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
					        <th>状态</th>
					        <th>借款人</th>
					        <th>部门名称</th>
					        <th>项目名称</th>
					        <th>借款金额（元）</th>
					        <th>申请日期</th>
					        <th>报销日期</th>
					        <th>支付方式</th>
					        <th>审核意见</th>
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
	$(function () {
	  
		//导出Excel
	   	$("#btn_exp").click(function(){
	   		alert("导出成功");
	   	});
	   	//列表数据
		var columns = [
			{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
		       	return '<input type="checkbox" class="keyId" name="id" value="' + data + '">';
		    },"width":10,'searchable': false},
			{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		   		return data;
			},"searchable": false,"class":"text-center"},
		   	{"data": "SHZT",defaultContent:"","class":"text-center"},
		   	{"data": "JKR",defaultContent:""},
		   	{"data": "BMMC",defaultContent:""},
		   	{"data": "XMMC",defaultContent:""},
		   	{"data": "JKJE",defaultContent:"0.00","class":"text-right"},
		   	{"data": "SQRQ",defaultContent:"","class":"text-center"},
			{"data": "BXRQ",defaultContent:"","class":"text-center"},
			{"data": "ZFFS",defaultContent:""},
			{"data": "SHYJ",defaultContent:""},
		   	{"data":function (data, type, full, meta){
		   		return '<a class="btn btn-link btn_look">查看</a>';
			},orderable:false,"width":95,"class":"text-center"}
		];
	   	table = getDataTableByListHj("mydatatables","${ctx}/wsbx/cxtj/getPageList?table=CW_JKYWB",[2,'asc'],columns,0,1,setGroup);
	  //查看
	   	$(".btn_look").click(function(){
	   		location.href="${ctx}/wsbx/cxtj/operate?operate=J";
	   	});
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