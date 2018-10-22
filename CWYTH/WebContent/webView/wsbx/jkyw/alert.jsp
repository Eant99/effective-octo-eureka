<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注意事项</title>
<%@include file="/static/include/public-list-css.inc"%> 
<style>
	*{
		maring: 0;
		padding: 0;
		font-size: 14px;
	}
	body{
		background-color: white;
	}
	p{
		height: 30px;
		line-height: 30px;
		font-size: 15px;
	}
</style>
</head>
<body>
	<div style="padding-left: 20px;padding-top: 20px;">
		<p>1、车双宿双飞的沙发沙发斯蒂芬撒旦法师打发送到</p>
		<p>2、上阿萨德干啥的法师打发斯蒂芬啥的方式当奥迪</p>
		<p>3、微商时代干啥德国法撒旦法</p>
		<p>4、第三方盛大发售的法师打发斯蒂芬请问各位</p>
		
	</div>
	<div style="text-align: center;">
	<button type="button" class="btn btn-primary" id="btn-cancel">确定</button>
		<label><input type="checkbox" name="" id="" style="width: 13px;height: 13px;"/>不再提醒</label>
	</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//返回按钮
	$("#btn-save").click(function(){
		alert("保存成功");
		close(winId);
	});
	$("#btn-cancel").click(function(){
		close(winId);
	});
});
</script>
</html>