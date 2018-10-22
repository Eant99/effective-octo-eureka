<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	
</form>

<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var target = "${ctx}/kjhs/pzxx/pzlr";
confirm("当前账套期间尚未添加凭证，是否录入凭证？","",{title:"提示"},function(){
	$("a.layui-layer-btn1",top.document)[0].click();
	pageSkip(target,"pzlr");
});
</script>
</body>
</html>