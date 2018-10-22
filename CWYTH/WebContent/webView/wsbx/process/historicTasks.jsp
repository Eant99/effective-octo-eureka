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
				<th>流程编号</th>
<!-- 				<th>流程名称</th> -->
				<th>流程实例</th>
				<th>任务名称</th>
				<th>任务处理人</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>持续时间</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${historicTasks}" var="task" step="1">
				<tr>
					<td>${task.processDefinitionKey}</td>
					<td>${task.processDefinitionId}</td>
<%-- 					<td>${task.processDefinitionName}</td> --%>
					<td>${task.processInstanceId}</td>
					<td>${task.name}</td>
					<td>${task.assignee}</td>
					<td><fmt:formatDate value="${task.startTime}" type="both"/>
					</td>
					<td><fmt:formatDate value="${task.endTime}" type="both"/></td>
					<td>${task.durationInMillis}</td>
					<td>
						<a href="${pageContext.request.contextPath}/process/viewProcess.io?processInstanceId=${task.processInstanceId}">历史</a>|
					</td>
				</tr>
			</c:forEach>
			
		</table>
	</div>
	
</body>
</html>