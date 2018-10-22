<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产处置列表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
	<div class="row rybxx" style="margin-left:-15px">
		<div class="col-md-12 tabs" style="padding-right: 0px">
			<ul id="tabTop" class="nav nav-tabs" role="tablist">
				<li role="presentation"><a id="tab_card">卡片信息</a></li>
				<li role="presentation"><a id="tab_ysd">验收单</a></li>
				<li role="presentation"><a id="tab_bd">变动</a></li>
				<li role="presentation" class="active"><a id="tab_cz">处置</a></li>
				<li role="presentation"><a id="tab_fj">附件</a></li>
				<li role="presentation"><a id="tab_zclsb">资产流水表</a></li>
			</ul>
		</div>
	</div>
	</div>	
	<div class="container-fluid">	
        <div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
				        <th>序号</th>
				        <th>单据状态</th>
				        <th>处置日期</th>
				        <th>处置单号</th>
				        <th>项目</th>
				        <th>处置前内容</th>
				        <th>处置后内容</th>
				        <th>单价</th>
				        <th>总价</th>
				        <th>使用单位</th>
				        <th>处置原因</th>
				        <th>归口意见</th>
				    </tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
        	</div>
   		</div>
	</div>
</div>
<!-- --------------------------------列表项  box  结束------------------------------------------ -->
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
  //列表数据
   var columns = [
		{"data":"_XH",orderable:false,"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "ZTBZ",defaultContent:"","class":"text-red"},
	   	{"data": "CZRQ",defaultContent:""},
	   	{"data": "CZBGBH",defaultContent:""},
	   	{"data": "BDXM",defaultContent:""},
	   	{"data": "BDQNR",defaultContent:""},
	   	{"data": "BDHNR",defaultContent:""},
		{"data": "DJ",defaultContent:"","class":"text-right"},
		{"data": "ZZJ",defaultContent:"","class":"text-right"},
	   	{"data": "SYDW",defaultContent:""},
	   	{"data": "CZYY",defaultContent:""},
	   	{"data": "GKYJ",defaultContent:""}
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/xxtz/getCzPageList?yqbh=${param.yqbh}",[1,'asc'],columns,"","",setGroup,"xgyshzc");

    //卡片
   	$("#tab_card").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/xxtz/goCardPage?yqbh=${param.yqbh}";
   	});
    //验收单
   	$("#tab_ysd").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/xxtz/goYsdPage?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
   	});
    //变动
   	$("#tab_bd").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/changelist.jsp?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
   	});
    //附件
   	$("#tab_fj").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/assetfjlist.jsp?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
   	});
    //资产流水表
   	$("#tab_zclsb").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/zclslist.jsp?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
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
var setGroup = function(json){}
</script>
</body>
</html>