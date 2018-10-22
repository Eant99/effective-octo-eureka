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
				<th>名称</th>
				<th>流程定义ID</th>
				<th>流程实例ID</th>
				<th>流程发起人</th>
				<th>当前任务名称</th>
				<th>当前任务处理人</th>
				<th>开始时间</th>
<!-- 				<th>耗时</th> -->
				<th>状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${processInstances}" var="pd" step="1">
				<tr>
					<td>${pd.pname}</td>
					<td>${pd.proc_def_id_}</td>
					<td>${pd.proc_inst_id_}</td>
					<td>${pd.startuser}</td>
					<td>${pd.taskname}</td>
					<td>${pd.assignee}</td>
					<td>${pd.start_time_}</td>
<%-- 					<td>${pd.duration}</td> --%>
					<td>${pd.is_active_}</td>
					<td>
						<a href="${pageContext.request.contextPath}/modeler.html?modelId=27507">暂停</a>|
						<a href="${pageContext.request.contextPath}/model/${pd.proc_inst_id_}/delete">关闭</a>|
						<a href="${pageContext.request.contextPath}/model/27507/deploy">委派</a>|
						<a href="${pageContext.request.contextPath}/model/27507/export">历史</a>|
						<a href="${pageContext.request.contextPath}/model/27507/viewGraphic">跳转</a>
					</td>
				</tr>
			</c:forEach>
			
		</table>
	</div>
</body>
</html>