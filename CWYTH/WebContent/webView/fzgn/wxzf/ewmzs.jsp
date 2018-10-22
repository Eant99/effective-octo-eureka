<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>承包商二维码</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	
</style>
</head>
<body>
   <div style="width: 100%;height: 100%;text-align: center;padding-top: 10px;">
   
     <img  src="${param.ewmurl }" width="240px;" height="260px;">
   </div>

<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var tag = false;
var operate = "${operateType}";
//var operate_src = ADDRESS+"/jcsz/jsxxs/insert";
$(function(){
});
</script>
</body>
</html>