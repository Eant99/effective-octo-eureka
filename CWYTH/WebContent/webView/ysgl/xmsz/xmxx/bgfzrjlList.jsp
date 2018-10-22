<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>负责人变更信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>原负责人</label>
					<input type="text" id="txt_xmbh" class="form-control" name="OLDFZR"  placeholder="请输入原负责人">
				</div>
				<div class="form-group">
					<label>新负责人</label>
					<input type="text" id="txt_xmmc" class="form-control" name="NEWFZR"   placeholder="请输入新负责人">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
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
				              <th>部门名称</th>
					          <th>项目编号</th>
					          <th>项目名称</th>
					          <th>原负责人</th>
					          <th>新负责人</th>
					          <th>变更人</th>
					          <th>变更时间</th>
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
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
var target = "${ctx}/ysgl/xmsz/xmxx";
$(function () {
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//加载列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
			return '<input type="checkbox" name="guid" class="keyId" value="' + data + '">';
		},"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render':function(data,type,full,meta){
		  	return data;},"width":41,"searchable":false,"class":"text-center"},
		{"data": "BMMC",defaultContent:""},
		{"data": "XMBH",defaultContent:""},
		{"data": "XMMC",defaultContent:""},
		{"data": "OLDFZR",defaultContent:""},
		{"data": "NEWFZR",defaultContent:""},
		{"data": "BGR",defaultContent:""},
		{"data": "BGSJ",defaultContent:""}
     ];
    table = getDataTableByListHj("mydatatables",target+"/getBgfzrjlPageData?idppp=${id}",[8,'desc'],columns,0,1,setGroup);
});
</script>
</body>
</html>