<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
<title>兼容性</title>
<script type="text/javascript">
function getOs(){
	var explorer = window.navigator.userAgent.toLowerCase() ;
	 //ie 
	 if (explorer.indexOf("msie") >= 0) {
	    var ver=explorer.match(/msie ([\d.]+)/)[1];
	    //低于ie8 给予提示
	    if(parseFloat(ver)>8)
	    {
	    	location.href= "${pageContext.request.contextPath}/";
		}
	 }
	 else
	 {
	  location.href= "${pageContext.request.contextPath}/";
	 }
	 return false;
}
</script>
</head>
<body onload="getOs();">
<%
String num = request.getParameter("version");
String type = request.getParameter("type");
%>
<div>
	<div style="background:#EDF4FE;font-family:微软雅黑;font-size:18px;height:120px;line-height:33px;text-align:center;margin-top:280px">
		<br>
		很抱歉！您正在使用的浏览器版本过低，无法正常访问，请升级后再试！<br/>
		为正常访问网站，建议您升级或选用以下浏览器&emsp;<a href="${pageContext.request.contextPath}/">继续访问》》</a><br>
	</div>
	<div class = "row">
	<div  style="width:700px;margin:10px auto;height:171px;background: url('static/images/systemSet/grxx/bbgd.png') no-repeat 0 0">
	</div>
	</div>
</div>
</body>
</html>