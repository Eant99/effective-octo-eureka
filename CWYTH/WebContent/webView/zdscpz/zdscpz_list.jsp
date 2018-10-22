<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>模板选择</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body style="background-color: white;">
<!-- <body style="background-color: #FAC303;"> -->


<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {

	//弹出选择模板框
	select_commonWin("${pageContext.request.contextPath}/zdscpz/tc","凭证自动化","800","600");
});
</script>
</body>
</html>