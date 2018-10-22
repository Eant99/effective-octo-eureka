<%@page import="com.googosoft.constant.MenuFlag"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<title>首页-业务草稿列表</title>
	<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	body{
		font-size:12px;
	}
	table th{
		text-align:center;
		background-color: #f3f3f3;
	}
</style>
<body>
<div class="box" style="margin-bottom:10px;">
	<div id="d_wxts" class="alert alert-info pull-left" style="padding: 2px auto;margin:2px 6px;">
       	<span><b>温馨提示：</b>点击数量可以跳转到相应页面</span>
    </div>
	<div class='box-content'>
	    <div class='responsive-table'>
	        <div class='scrollable-area'>
				<table id="mydatatables" class="table table-striped table-bordered">
					<thead>
						<tr id="header">
							<th style="width:20px;">序号</th>
							<th>名称</th>
							<th style="width:50px;">数量</th>
						</tr>
					</thead>
			      	<tbody>
				      	<c:if test="${list.size() > 0 }" >
					      	<c:forEach var="list" items="${list}" varStatus="i" >
					      		<tr path="${list.URL }" mc="【${list.SJMKMC }】待${list.MKMC }" bh="${list.MKBH }">
						      		<td>${i.index+1 }</td>
						      		<td>【${list.SJMKMC }】待${list.MKMC }</td>
						      		<td style="text-align:right;padding:5px;"><a href="javascript:void(0)">${list.SL }</a></td>
					      		</tr>
					      	</c:forEach>
				      	</c:if>
			      	</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	$("#d_wxts").css("width",$("#mydatatables").width() + "px");
	$(".tool-fix").hide();
	$(document).on("click","#mydatatables tr:not(#header) a",function(){
	 	var $this = $(this).parents("tr");
		var mc = $this.attr("mc");
		var bh = $this.attr("bh");
    	var val = "${pageContext.request.contextPath}/webView/ywsh/shMain.jsp?mkbhCheck="+bh;
    	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	parent.openRightWin(val,"<%=MenuFlag.YWSH%>","待审核信息");
    	close(winId);
    });
});

</script>
</body>
</html>