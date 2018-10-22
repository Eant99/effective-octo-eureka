<%@page import="com.googosoft.constant.MenuFlag"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加验收单</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	[class^="col-"] {
		padding: 0;
	}
	.content {
		overflow:auto;
	}
 	.left-menu {
		margin-bottom: 20px;
		margin-top: 20px;
	}
	.list-group {
		padding: 0 10px;
	}
	.list-group-item {
		padding: 5px 15px;
		cursor: pointer;
	}
	.list-group-item.active {
		padding: 8px 15px;
	}
	.list-group-item .list-group-item-heading {
		margin-bottom: 0;
	}
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-2 text-center" style="position: absolute;left: 0;height: 100%;background-color: white;">
			<div class="btn-group left-menu">
			  <button type="button" class="btn btn-primary">增加验收单</button>
			  <button type="button" class="btn btn-danger">批量删除</button>
			</div>
			<ul class="list-group">
			  <li class="list-group-item active">
			    <h5 class="list-group-item-heading">常用分类</h5>
			  </li>
			  <li class="list-group-item">普通设备</li>
			  <li class="list-group-item">普通设备</li>
			  <li class="list-group-item">普通设备</li>
			  <li class="list-group-item">普通设备</li>
			  <li class="list-group-item">普通设备</li>
			</ul>
			<ul class="list-group">
			  <li class="list-group-item active">
			    <h5 class="list-group-item-heading">常用分类</h5>
			  </li>
			  <li class="list-group-item">普通设备</li>
			  <li class="list-group-item">普通设备</li>
			  <li class="list-group-item">普通设备</li>
			  <li class="list-group-item">普通设备</li>
			  <li class="list-group-item">普通设备</li>
			</ul>
		</div>
		<div class="col-md-10" style="position: absolute;right: 0;height: 100%;">
			<iframe frameborder="0" width="100%" height="100%" src="/GXGDZC7/webView/zcjz/yshd_list.jsp?mkbh=<%=MenuFlag.ZCJZ_LYR%>"></iframe>
		</div>
	</div>
</div>
<%@include file="/static/include/public-manager-js.inc"%>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$(".list-group-item.active").click(function(){
			$(this).parents(".list-group").find(".list-group-item:not('.active')").slideToggle("fast");
		});
	});
</script>
</html>