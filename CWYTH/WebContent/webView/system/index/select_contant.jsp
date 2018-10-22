<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background:white">
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var tj = "${param.tj}";
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	if(tj=='ysdcx'){
		select_commonWin("${pageContext.request.contextPath}/yshd/goZcflPage?ysdh=${ysdxx.YSDH}&flh=${ysdxx.FLH}&flmc=${ysdxx.YQBH}&czfl=${ysdxx.FLGBM}&operate=CK&operateType=L","验收单信息","1200","630","yes");
	}else if(tj == "bddcx"){
		select_commonWin("${pageContext.request.contextPath}/zcbdcx/goEditPage?bdbgbh=${bdxx.BDBGBH}&djbz=${bdxx.BDZT}&operate=CK&operateType=CK","变动单信息","1200","630","yes");
	}
	close(winId);
});
</script>
</body>
</html>