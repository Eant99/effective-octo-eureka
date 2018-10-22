<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>财务人员列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>人员姓名</label>
					<div class="form-group">
						<input type="text" name="xm" class="form-control input-radius"  placeholder="人员姓名" value="">
					</div>
					<div class="pull-right">
						<button type="button" class="btn btn-primary" id="btn_search" name="btncx">
							<i class="fa icon-chaxun"></i>
							查 询
						</button>
						<button type="button" class="btn btn-default" id="btn_cancel">
							取 消
						</button>
						<span class="alert alert-info">
							<strong>提示：</strong>先找到需要的信息，然后双击鼠标选择这条信息
						</span>
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
				        	<th>人员编号</th>
				        	<th>人员姓名</th>
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
$(function(){
	var columns = [
	   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
             return '<input type="checkbox" name="guid" class="keyId"  value="'+ data +'" data-bhxm="('+full.RYBH+')'+full.XM+'" >';
       },"width":10,"class":"text-center",'searchable': false},
	   {"data": "_XH",defaultContent:"","class":"text-center","width":20},
	   {"data": "RYBH",defaultContent:"","width":50},
       {"data": "XM",defaultContent:"","width":50}
	];
	 table = getDataTableByListHj("mydatatables","${ctx}/pzcxs/getCwryPageList",[2,'asc'],columns,0,1,setGroup);
	 var winId = getTopFrame().layer.getFrameIndex(window.name);
	 $(document).on("dblclick","tbody tr",function(){
			var guid = $(this).find("[name='guid']").val();
			var bhxm = $(this).find("[name='guid']").data("bhxm");
			getIframeControl("${param.pname}","${param.controlId}").val(bhxm);
			getIframeControl("${param.pname}","${param.ryid}").val(guid);
			close(winId);
	 });
});
</script>
</body>
</html>