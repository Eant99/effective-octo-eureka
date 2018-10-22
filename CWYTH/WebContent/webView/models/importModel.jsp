<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入流程</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/webuploader-0.1.5/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/webuploader-0.1.5/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/webuploader-0.1.5/style.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/webuploader-0.1.5/webuploader.css">
</head>
<body>
	<div style="height: 30px; color: red; font-size: 16px;">选择一个包含图片信息（用于坐标定位）的文件
		(.bpmn20.xml 或 .bpmn)</div>
	<div id="uploader" class="wu-example">
		<!--用来存放文件信息-->
		<div id="fileList" class="uploader-list"></div>
		<div class="btns">
			<div id="picker">选择文件</div>
			<button id="ctlBtn" class="btn btn-default">开始上传</button>
		</div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/editor-app/libs/jquery_1.11.0/jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/webuploader-0.1.5/webuploader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/model/import.js"></script>
</body>
</html>