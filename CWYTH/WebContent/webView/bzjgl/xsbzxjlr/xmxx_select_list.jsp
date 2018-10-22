<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 12px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="K"  placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_jflx" class="form-control" name="jflx" table="K"   placeholder="请输入项目名称">
				</div>
				<div class="form-group">
					<label>项目负责人</label>
					<input type="text" id="txt_fzr" class="form-control" name="fzr" table="K"   placeholder="请输入项目负责人">
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
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>项目类型</th>
				            <th>项目类别</th>
				            <th>项目大类</th>
				            <th>项目负责人</th>
				            <th>预算金额(元)</th>
				            <th>余额(元)</th>
				            <th>授权截止日期</th>
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
// var target = "${ctx}/bzjgl/xsbzxjlr";
$(function () {
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//加载列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" xmbh="' + full.XMBH + '"  values="' +"("+ full.XMBH + ")"+full.XMMC+'" >';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
      {"data": "XMBH",defaultContent:""},
      {"data": "XMMC",defaultContent:""},
      {"data": "JFLX",defaultContent:""},
      {"data": "XMLBMC",defaultContent:"","width":280},
      {"data": "XMDLMC",defaultContent:"","width":280},
      {"data": "FZR",defaultContent:"","width":300},
      {"data": "YSJE",orderable:false,defaultContent:"0.00","width":200,"class":"text-right"},
      {"data": "YE",defaultContent:"0.00","class":"text-right","width":200},
      {"data": "JZSJ",defaultContent:"","class":"text-center","width":30},     
      ];
    table = getDataTableByListHj("mydatatables","${ctx}/xsbzxjlr/getXmxxPageList",[2,'asc'],columns);
    $(function () {
		$(document).on("dblclick","tbody tr",function(){
			var xmmc = $(this).find("[name='guid']").attr("values");
			var xmbh = $(this).find("[name='guid']").val();
			getIframeControl("${param.pname}","${param.controlId}").val(xmmc);
			getIframeControl("${param.pname}","${param.xmbh}").val(xmbh);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	var winId = getTopFrame().layer.getFrameIndex(window.name);
        	close(winId);
	    });
    });
});
</script>
</body>
</html>