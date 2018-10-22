<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>预算管理模型列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
 th{
 	text-align: center
 }
</style>
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
					<label>模型编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="k"  placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>模型名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc" table="k"   placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<c:if test="${empty isWindow}">
					<div class="btn-group pull-right" role="group">
		               <button type="button" class="btn btn-default" id="btn-add">增加</button>
		               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
		               <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
					</div>
				</c:if>
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
				            <th>模型编号</th>
				            <th>模型名称</th>
				            <th>模型分类</th>
				            <th>金额范围（元）</th>
				            <th>项目类型</th>
				            <th>预算方法</th>
				            <th>是否启用</th>
				            <c:if test="${empty isWindow}">
				            <th>操作</th>
				            </c:if>
				        </tr>
					</thead>
				    <tbody>
				    <tr role="row" class="odd" style="height: 37px;">
				    	<td style="width:13px">
				    	<input type="checkbox" name="guid" class="keyId text-center" value="E3A85BE7EEF54604873BF914C63CC6A1" xmbh="010101" values="(010101)2121" >
				    	</td>
				    	<td class=" text-center">1</td>
				    	<td class="sorting_1">
				    	<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid="E3A85BE7EEF54604873BF914C63CC6A1">010101</a></div>
				    	</td>
				    	<td class=" text-left">包干经费计算模型1</td>				    	
				    	<td class=" text-left">办公</td>
				    	<td class=" text-right">1-500000</td>
				    	<td>办公经费</td>
				    	<td>零基预算</td>
				    	<td class=" text-left">已启用</td><td class=" text-center"><a href="javascript:void(0);" class="btn btn-link btn-edit" type="button"  guid="E3A85BE7EEF54604873BF914C63CC6A1">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn-del" guid="E3A85BE7EEF54604873BF914C63CC6A1">删除</a></td></tr>
				     <tr role="row" class="odd" style="height: 37px;">
				    	<td>
				    	<input type="checkbox" name="guid" class="keyId" value="E3A85BE7EEF54604873BF914C63CC6A1" xmbh="010101" values="(010101)2121">
				    	</td>
				    	<td class=" text-center">2</td>
				    	<td class="sorting_1">
				    	<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid="E3A85BE7EEF54604873BF914C63CC6A1">010102</a></div>
				    	</td>
				    	<td class=" text-left">维修经费计算模型1</td>				    	
				    	<td class=" text-left">业务</td>
				    	<td class=" text-right">1-500000</td>
				    	<td>业务支出</td>
				    	<td>零基预算</td>
				    	<td class=" text-left">已启用</td><td class=" text-center"><a href="javascript:void(0);" class="btn btn-link btn-edit" type="button" guid="E3A85BE7EEF54604873BF914C63CC6A1">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn-del" guid="E3A85BE7EEF54604873BF914C63CC6A1">删除</a></td></tr>	
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
	$(document).on("click","#btn-add",function(){
	   	window.location.href = "${ctx}/webView/ysgl/yssz/glmx_edit.jsp";
   	});
	
	$(document).on("click",".btn-edit",function(){
	   	window.location.href = "${ctx}/webView/ysgl/yssz/glmx_edit.jsp";
   	});
</script>
</body>
</html>