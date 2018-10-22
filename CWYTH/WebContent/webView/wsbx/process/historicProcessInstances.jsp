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
				<th>标题</th>
<!-- 				<th>流程名称</th> -->
				<th>流程定义ID</th>
				<th>流程实例ID</th>
<!-- 				<th>流程发起人</th> -->
				<th>开始时间</th>
				<th>结束时间</th>
				<th>耗时</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${historicInstances}" var="instance" step="1">
				<tr>
					<td>${instance.processDefinitionKey}</td>
<%-- 					<td>${instance.name}</td> --%>
					<td>${instance.processDefinitionId}</td>
					<td>${instance.processInstanceId}</td>
<%-- 					<td>${instance.startUserId}</td> --%>
					<td><fmt:formatDate value="${instance.startTime}" type="both"/></td>
					<td><fmt:formatDate value="${instance.endTime}" type="both"/></td>
					<td>${instance.durationInMillis}</td>
					<td>
						<a href="${pageContext.request.contextPath}/process/viewProcess.io?processInstanceId=${instance.processInstanceId}">历史</a>|
					</td>
				</tr>
			</c:forEach>
			
		</table>
	</div>
</body>
</html>