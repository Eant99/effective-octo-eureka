<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通用错误页面</title>
<style type="text/css">
	html,body,div,img{
		height:100%;
		width:100%;
		margin:0px auto;
		padding:0px;
	}
</style>
</head>
<body>
<div id="d_body" style="overflow:hidden;position:relative;">
	<!-- top: 50%;left: 50%;transform: translateX(-50%) translateY(-50%); -->
	<div id="d_all" style="max-width:800px;max-height:500px;overflow:hidden;position:absolute;">
		
	</div>
</div>
<!-- <span style="color:#f00;size:20px;"> -->
<%-- 	<c:if test="${errinfo=='1'}"> --%>
<%-- 		<img alt="" src="${pageContext.request.contextPath}/static/images/error/cs.jpg"> --%>
<%-- 	</c:if> --%>
<%-- 	<c:if test="${errinfo=='2'}"> --%>
<%-- 		<img alt="" src="${pageContext.request.contextPath}/static/images/error/bcz.jpg"> --%>
<%-- 	</c:if> --%>
<%-- 	<c:if test="${errinfo=='3'}"> --%>
<%-- 		<img alt="" src="${pageContext.request.contextPath}/static/images/error/yhxxcf.jpg"> --%>
<%-- 	</c:if> --%>
<!-- </span> -->
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script type="text/javascript">
	if("${errortype}" == "show"){
		$("#d_all").prop("innerHTML",'<img style="position:absolute;top:0px;left:0px;" alt="" src="${pageContext.request.contextPath}/static/images/error/puberror.jpg"><div style="width:50%;height:20%;position:absolute;top:20%;left:15%;padding-top:10px;"><b>${errinfo}</b></div>');
	}
	else{
		alert('${errinfo}');
		self.close();
	}
$(function(){
	dominit();
	
	//列表右侧悬浮按钮
	$(window).resize(function(){
		dominit();
	});
});

var dominit = function(){
	var bh = $("#d_body").height();
	var bw = $("#d_body").width();
	
	var nh = $("#d_all").height();
	var nw = $("#d_all").width();
	
	var top = 0;
	if(bh > nh){
		top = Math.floor((bh - nh)/2);
	}
	
	var left = 0;
	if(bw > nw){
		left = Math.floor((bw - nw)/2);
	}
	
	$("#d_all").css({"top":top+"px","left":left+"px"});
}
</script>
</body>
</html>