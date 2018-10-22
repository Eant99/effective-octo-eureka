<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产附件信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.search {
}
</style>
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
				<li role="presentation"><a id="tab_cz">处置</a></li>
				<li role="presentation"><a id="tab_fj">附件</a></li>
				<li role="presentation" class="active"><a id="tab_zclsb">资产流水表</a></li>
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
				        <th>仪器编号</th>
				        <th>仪器名称</th>
				        <th>分类号</th>
				        <th>分类名称</th>
				        <th>单价</th>
				        <th>型号</th>
				        <th>规格</th>
				        <th>计量单位</th>
				        <th>账页</th>
				        <th>凭证号</th>
				        <th>审核日期</th>
				        <th>经费科目</th>
				        <th>调转入日期</th>
				        <th>资产来源</th>
				        <th>购置日期</th>
				        <th>记账日期</th>
				        <th>十大类号</th>
				        <th>六大类号</th>
				        <th>生产厂家</th>
				        <th>销售商</th>
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
		{"data":"_XH",orderable:false,"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "YQBH",defaultContent:""},
	   	{"data": "YQMC",defaultContent:""},
	   	{"data": "FLH",defaultContent:""},
	   	{"data": "FLMC",defaultContent:""},
		{"data": "DJ",defaultContent:"","class":"text-right"},
	   	{"data": "XH",defaultContent:""},
	   	{"data": "GG",defaultContent:""},
	   	{"data": "JLDW",defaultContent:""},
	   	{"data": "ZY",defaultContent:""},
	   	{"data": "PZH",defaultContent:""},
	   	{"data": "CWRQ",defaultContent:""},
	   	{"data": "JFKM",defaultContent:""},
	   	{"data": "DZRRQ",defaultContent:""},
	   	{"data": "ZCLY",defaultContent:""},
	   	{"data": "GZRQ",defaultContent:""},
	   	{"data": "JZRQ",defaultContent:""},
	   	{"data": "CZ10",defaultContent:""},
	   	{"data": "FLGBM",defaultContent:""},
	   	{"data": "SCCJ",defaultContent:""},
	   	{"data": "XSS",defaultContent:""}
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/xxtz/getZclsPageList?yqbh=${param.yqbh}",[2,'asc'],columns,"","",setGroup);

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
    //处置
   	$("#tab_cz").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/assetdolist.jsp?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
   	});
    //附件
   	$("#tab_fj").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/assetfjlist.jsp?yqbh=${param.yqbh}&ysdh=${param.ysdh}";
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