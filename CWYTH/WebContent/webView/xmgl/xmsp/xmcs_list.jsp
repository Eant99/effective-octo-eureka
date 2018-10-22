<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目申报</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh"  placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"   placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>项目类型</label>
							<div class="input-group">
								<input type="text" id="txt_xmlx" class="form-control" name="xmlx"  placeholder="请输入项目类型">
							</div>
						</div>
						<div class="form-group">
							<label>服务专业</label>
							<input type="text" id="txt_fwzy" class="form-control" name="fwzy"  placeholder="服务专业">
						</div>
						<div class="form-group"> 
							<label>服务学科</label>
							<div class="input-group">
								<input type="text" id="txt_fwxk" class="form-control input-radius" name="fwxk"   placeholder="服务学科">
							</div>
						</div>
						<div class="form-group">
							<label>是否上年度重新论证项目</label>
							<select style="width:70px;" id="txt_sfsndcxlzxm" class="form-control" name="sfsndcxlzxm">
									<option value="">未选择</option>
                					<option value="是">是</option>
                					<option value="否">否</option>
							</select>	
						</div>
						<div class="form-group">
							<label>申报单位</label>
							<input type="text" id="txt_sbdw" class="form-control" name="sbdw"  placeholder="申报单位" value="" >
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>
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
	               <button type="button" class="btn btn-default" id="btn_exportExcel" data-file-name="项目初审" data-param="splx=${param.splx }">导出Excel</button>
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
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>项目类型</th>
				            <th>服务专业</th>
				            <th>服务学科</th>
				            <th>预算金额(元)</th>
				            <th>是否上年度重新论证项目</th>
				            <th>申报单位</th>
				            <th>申报人</th>
				            <th>申报日期</th>
				            <th>操作</th>
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
<script src="${pageContext.request.contextPath}/static/javascript/public/public-btn-action.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
var basePath = getBasePath();
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" data-guid="'+full.GUID+'" data-splx="${param.splx}">';
       },"width":10,'searchable': false},
       {"data": "_XH",width:41,"class":"text-center"},
       {"data": "SHZTMC",defaultContent:""},
       {"data": "XMBH",defaultContent:""},
       {"data": "XMMC",defaultContent:""},
       {"data": "XMLX",defaultContent:""},
       {"data": "FWZY",defaultContent:""},
       {"data": "FWXK",defaultContent:""},
       {"data": "YSJE",defaultContent:"","class":"text-right"},
       {"data": "SFSNDCXLZXM",defaultContent:"","class":"text-center"},
       {"data": "SBDW",defaultContent:""},
       {"data": "SBR",defaultContent:"",},
       {"data": "SBRQ",defaultContent:"","class":"text-center"},
       {"data": "GUID",'render':function(data, type, full, meta){
    	   var link=""; 
    	   link +=  HREF_VIEW.replace("%P","splx") + '|' + HREF_CHECK.replace("%P","splx");
	   		return link + '<input type="hidden" name="guid" class="keyId" data-guid="'+full.GUID+'" data-splx="${param.splx}">';
      },orderable:false,"width":220,"class":"text-center"}
     ];
     table = getDataTableByListHj("mydatatables",basePath+"/getListPageData?splx=${param.splx}",[2,'asc'],columns,0,1,setGroup);
});
$(document).ready(function(){
	$(".btn-link").bindAction();
});
$(document).on("click","#btn_exportExcel",function(){
	doExportExcel(getBasePath()+"/doExportExcel?splx=${param.splx}",function(result){
		FileDownload(getContextPath()+"/file/fileDownload","项目初审.xls",result.url);
	});
})
</script>
</body>
</html>