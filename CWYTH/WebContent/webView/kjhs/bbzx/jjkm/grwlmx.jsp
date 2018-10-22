<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
<!--              <div class="form-group" style="width:350px;"> -->
<!-- 					会计期间： -->
<%-- 					${bbqj} --%>
<!-- 				</div> -->
						
				<div class="btn-group pull-right" role="group">
					 <button type="button" class="btn btn-default" id="btn_back">返回</button>
            	</div>
			</div>
		</form>
		<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'> 
					<table id="mydatatables" class="table table-striped table-bordered">
			        	<thead>
					        <tr>
					             <th><input type="checkbox" class="select-all"/></th>
					            <th>序号</th>
					            <th>凭证日期</th>
					            <th>凭证类型</th>
					            <th>凭证号</th>
					            <th>摘要</th>
					            <th>个人</th>
					            <th>会计科目</th>
					            <th>经济科目</th>
					            <th>部门</th>
					            <th>项目</th>
					            <th>借方金额</th>
					            <th>贷方金额</th>
					            <th>余额</th>
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
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
	var columns = [
		{"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
		    return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" xh="'+full.XH+'" pzid = "'+full.ZBID+'" guid = "'+full.GUID+'">';
		  },"width":10,'searchable': false},
		  {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
		     	return data;},"width":41,"searchable":false,"class":"text-center"},      
		  {"data": "PZRQ",defaultContent:"","class":"text-center",orderable:false},
		  {"data": "PZLXMC",defaultContent:"",orderable:false},
		  {"data": "PZBH",defaultContent:"",orderable:false},
		  {"data": "ZY",defaultContent:"",orderable:false,"width":300},
		  {"data": "XM",defaultContent:"",orderable:false},
		  {"data": "KJKM",defaultContent:"",orderable:false},
		  {"data": "JJKM",defaultContent:"",orderable:false},
		  {"data": "BMMC",defaultContent:"",orderable:false},
		  {"data": "XMMC",defaultContent:"",orderable:false},
		  {"data": "JFJE",defaultContent:"","width":100,"class":"text-right",orderable:false},
		  {"data": "DFJE",defaultContent:"","width":100,"class":"text-right",orderable:false},
		  {"data": "YE",defaultContent:"","width":100,"class":"text-right",orderable:false},       
		];
		table = getDataTableByListHj("mydatatables","${ctx}/kjhs/grwlmxz/getGrwlPageList?treezrr=${dm}",[],columns,0,1,setGroup);
		$("#btn_back").click(function(){
		history.back(-1);
	});
});
$(document).on("dblclick","tbody tr",function(){
	var vamc = $(this).find("[name='guid']").attr("pzid");
 	pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&gs=${param.gs}&guid="+vamc);
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