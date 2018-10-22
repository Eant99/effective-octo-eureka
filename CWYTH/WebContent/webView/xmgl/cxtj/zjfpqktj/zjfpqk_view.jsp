<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金分配情况</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="btn-group pull-right" role="group">
	      	 <button type="button" class="btn btn-default" id="btn_exp">返回</button>
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
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>已分配金额</th>
				            <th>未分配金额</th>
				            <th>已分配比例(%)</th>
				            <th>未分配比例(%)</th>
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
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:""},
       {"data": "FSYJ",defaultContent:"0001"},
       {"data": "FSYJ",defaultContent:"建设项目"},
       {"data": "FSYJ",defaultContent:"100.00",class:"text-right"},
       {"data": "FSYJ",defaultContent:"100.00",class:"text-right"},
       {"data": "FSYJ",defaultContent:"50.00",class:"text-right"},
       {"data": "FSYJ",defaultContent:"50.00",class:"text-right"},
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xmsb/xmsb/getPageList",[2,'asc'],columns,0,1,setGroup);
   	$("#btn_exp").click(function(){
   		location.href="${ctx}/webView/xmgl/cxtj/zjfpqktj/school_view.jsp";
   	});
	//查询
	$("#btn_search").click(function(){
		window.location.reload();
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