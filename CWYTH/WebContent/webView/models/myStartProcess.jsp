<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.button{
		float:left;
		vertical-align: middle;
		width:50px;
		line-height:28px;
		border:1px solid green;
		margin:0px 5px 0px 5px;
		color:#FFFFFF;
		cursor: pointer;
		text-align: center;
	}
	.content_table {
		margin:5px;
		  border-collapse: collapse;
	}
	.content_table tr {
		border-top:1px solid red;
		border-left:1px solid gray;
	}
	.content_table td,.content_table th{
		border-bottom:1px solid gray;
		border-right:1px solid gray;
	}
</style>
</head>
<body>
	<div style="width:100%;border:1px solid red;" class="content"  >
		<table class="content_table">
			<tr>
				<th>流程实例id</th>
				<th>名称</th>
				<th>创建时间</th>
				<th>结束时间</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${processinstance}" var="ps" step="1">
				<tr>
				   <td>${ps.id}</td>
					<td>${ps.processDefinitionName}</td>
					<td><fmt:formatDate value="${ps.startTime}" type="both"/>  </td>
					<td><fmt:formatDate value="${ps.endTime}" type="both"/>  </td>
					<td>
						<a href="${pageContext.request.contextPath}/process/viewProcess.io?processInstanceId=${ps.id}">查看进度</a>
					</td>
				</tr>
			</c:forEach>
			
		</table>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/diagram-viewer/js/jquery/jquery.js"></script>
<script type="text/javascript" >
	$(function(){
		$(".create").click(function(){
			window.location.href="${pageContext.request.contextPath}/model/editModel.io?cmd=add"
		});
		$(".import").click(function(){
			window.location.href="${pageContext.request.contextPath}/model/editModel.io?cmd=import"
		});
	});
</script>
	
</body>
</html>