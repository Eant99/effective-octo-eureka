<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑流程记录</title>
</head>
<body>
	<form action="saveModel" method="post"> 
		<input type="hidden" name="cmd" value='${model eq null?"add":"update"}'>
		名称:<input  type="text" name="name" value="${model.name}">
		描述:<input  type="text" name="desc" value="${model.desc}">
		<input type="submit" value="提交"/>
	</form>
</body>
</html>