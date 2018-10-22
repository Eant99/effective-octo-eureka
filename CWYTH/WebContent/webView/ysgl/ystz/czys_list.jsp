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
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="k"  placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc" table="k"   placeholder="请输入项目名称">
				</div>
				
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>部门名称</label>
							<div class="input-group">
								<input type="text" id="txt_bmmc" class="form-control" name="bmmc" table="k" placeholder="请输入部门名称">
							</div>
						</div>
						<div class="form-group">
							<label>项目负责人</label>
							<div class="input-group">
								<input type="text" id="txt_fzrmc" class="form-control" name="fzrmc" table="k" placeholder="请输入项目负责人">
							</div>	
						</div>
						<div class="form-group">
							<label>项目类别</label>
							<div class="input-group">
								<input type="text" id="txt_xmlbmc" class="form-control" name="xmlbmc" table="k" placeholder="请输入项目类别">
							</div>
						</div>
						
						
						<div class="form-group">
							<label>项目大类</label>
							<input type="text" id="txt_xmdlmc" class="form-control" name="xmdlmc" table="k" placeholder="请输入项目大类">
						</div>
						<!-- <div class="form-group"> 
							<label>项目金额区间</label>
							<div class="input-group">
								<input type="text" id="txt_xmjeqj" class="form-control input-radius" name="xmjeqj"  table="k" placeholder="请输入项目金额区间" value="">
							</div>
						</div> -->
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
				            <th>部门名称</th>
				            <th>项目名称</th>
				            <th>项目大类</th>
				            <th>项目类别</th>
				            <th>项目类型</th>
				            <th>负责人</th>
				            <th>预算金额(元)</th>
				            <th>剩余金额(元)</th>
				            <th>赤字金额(元)</th>
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
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
var target = "${ctx}/czys";
$(function () {
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//加载列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" values="' +"("+ full.XMBH + ")"+full.XMMC+'" >';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
      // {"data": "XMBH",defaultContent:"","class":"text-left"},
       {"data": "XMBH","render":function (data, type, full, meta){
		   	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
	   }},
       {"data": "BMMC",defaultContent:"","class":"text-left"},
       {"data": "XMMC",'render': function (data, type, full, meta){
         return '<span name="xmmc">'+data +'</span>';
       },"class":"text-left"},
       {"data": "XMDLMC",defaultContent:"","class":"text-left"},
       {"data": "XMLBMC",defaultContent:"","class":"text-left",},
       {"data": "XMLX",defaultContent:"","width":120},
       {"data": "FZRMC",defaultContent:"","width":120,},
       {"data": "YSJE",defaultContent:"","class":"text-right"},
       {"data": "SYJE",defaultContent:"","class":"text-right"},
       {"data": "",'render': function (data, type, full, meta){
    	   var ave = 0;
         return ave;
       },"class":"text-right",orderable:false},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="' + data + '">查看</a>';
      },orderable:false,"width":220,"class":"text-center","width":100}
     ];
    table = getDataTableByListHj("mydatatables",target+"/getXmxxPageData?treesearch=${treesearch}&rybh=${rybh}&bmh=${bmh}",[2,'asc'],columns,0,1,setGroup);

    //编辑
	$(document).on("click",".btn_look",function(){
		var xmmc = $(this).parents("tr").find("[name='xmmc']").text();
		console.log(xmmc)
     	window.location.href ="${ctx}/webView/ysgl/ystz/czysEdit.jsp?xmmc="+xmmc; 
   	});
});
</script>
</body>
</html>