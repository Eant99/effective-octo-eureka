<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学生补助金统计信息详情页面</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<input type="hidden" name="qc" value="${qc}">
	<input type="hidden" name="bmh" value="${bmh}">
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
				        <th>摘要</th>
				        <th>项目编号</th>
				        <th>银行名称</th>
				        <th>银行卡号</th>
				        <th>标准编号</th>
				        <th>发放金额</th>
				        <th>发放方式</th>
				        <th>发放时间</th>
				        <th>办理人</th>
				        <th>备注</th>
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
       				return '<input type="checkbox" class="keyId" name="keyId" value="' + data + '">';
	       	    },"width":10,'searchable': false},//0
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-left"},//1
	       	 	{"data": "FABH",defaultContent:"","class":"text-left"},
	       		{"data": "FAMC",defaultContent:"","class":"text-left"},
	       		{"data": "ZY",defaultContent:"","class":"text-left" },
	       		{"data": "XMBH",defaultContent:"","class":"text-left"},
	       		{"data": "YHMC",defaultContent:"","class":"text-left"},
	       		{"data": "YHKH",defaultContent:"","class":"text-left"},
	       		{"data": "BZBH",defaultContent:"","class":"text-left"},
	       	   	{"data": "FFJE",defaultContent:"","class":"text-right"},
	       	 	{"data": "FFFS",defaultContent:"","class":"text-left"},
	       		{"data": "FFSJ",defaultContent:"","class":"text-center"},
	       		{"data": "JBR",defaultContent:"","class":"text-left"},
	       		{"data": "BZ",defaultContent:"","class":"text-left"},
	       	];
	       	//表数据
          	table = getDataTableByListHj("mydatatables","${ctx}/xsbzjtj/getBzmxList?treedwbh=${dwbh}&qc=${qc}&bmh=${bmh}&xh=${param.xh}",[4,'asc'],columns);
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

