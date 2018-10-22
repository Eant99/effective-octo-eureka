<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据库配置修改</title>
<%@include file="/static/include/public-manager-css.inc"%>
<link href="${pageContext.request.contextPath}/static/plugins/layer/skin/layer.css" rel="stylesheet"/><!-- 弹窗相关 -->
<style type="text/css">
body{
	background-color: #cec9c9;
}
</style>
</head>
<body>
<div class="container" style="margin-top:100px;">
<div id="modden" style="margin:0px auto;">
<form  id="myform" action="" method="post">
    <div class="row">
		<div class="col-md-12 " style="text-align: center;margin-bottom:20px;">
			<h3>数据库信息配置</h3>
		</div>
	</div>
	<div class="row">
	    <div class="col-md-4"></div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">数据库类型：</span>
				<select name="dbtype"  id="txt_dbtype" disabled="disabled" class="form-control input-radius"  style="width:100%;">
					<option value="oracle">ORACLE</option>
				</select>
			</div>
       </div>
       <div class="col-md-4"></div>
	</div>
	<div class="row">
	    <div class="col-md-4"></div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">服务器地址</span>
				<input type="text" id="txt_url" class="form-control input-radius" name="url" value=""/>
			</div>
       </div>
       <div class="col-md-4">（输入数据库服务器IP地址，例如：192.168.10.10）</div>
	</div>
	<div class="row">
	    <div class="col-md-4"></div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">端&ensp;口&ensp;号</span>
				<input type="text" id="txt_port" class="form-control input-radius" name="port" value="1521"/>
			</div>
        </div>
        <div class="col-md-4" style="color:red">（默认端口号为1521，如无特殊需要，请勿修改。）</div>
	</div>
	<div class="row">
	    <div class="col-md-4"></div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">实&ensp;例&ensp;名</span>
				<input type="text" id="txt_sid" class="form-control input-radius" name="sid" value=""/>
			</div>
       </div>
       <div class="col-md-4"></div>
	</div>
	<div class="row">
	    <div class="col-md-4"></div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">用&ensp;户&ensp;名</span>
				<input type="text" id="txt_username" class="form-control input-radius" name="username" value=""/>
			</div>
        </div>
        <div class="col-md-4"></div>
	</div>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">密&emsp;&emsp;码</span>
				<input type="text" id="txt_password" class="form-control input-radius" name="password" value=""/>
			</div>
        </div>
        <div class="col-md-4"></div>
	</div>
    <div class="row">
        <div class="col-md-4"></div>
		<div class="col-md-4" style="margin-left:90px;">
			<button class="btn btn-default" type="button" id="btn_save">
				<i class="fa icon-save"></i>&ensp;确&emsp;定
			</button>
       	</div>
       	<div class="col-md-4"></div> 
   </div>
</form>
</div>
</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/layer/layer.min.js"></script><!--弹窗-->
<script type="text/javascript">
$(function(){
	//确定按钮
	var index;
	$("#btn_save").click(function(){
		if(checkSubmit()){
			var dbtype=$.trim($("#txt_dbtype").val());
			var port=$.trim($("#txt_port").val());
			var sid=$.trim($("#txt_sid").val());
			var url=$.trim($("#txt_url").val());
			var username=$.trim($("#txt_username").val());
			var password=$.trim($("#txt_password").val());
			$.ajax({      
				type:"POST",
				dataType:"json", 
				url:"${pageContext.request.contextPath}/login/doDatabSet",
				data:"dbtype="+dbtype+"&url="+url+"&port="+port+"&sid="+sid+"&username="+username+"&password="+password,
				success:function(val){
					layer.close(index);
					if(val.success=="true"){
						confirm("配置成功！是否跳转到登录页面重新登录？",{title:"提示"},function(){
							window.location.href = "${pageContext.request.contextPath}/login/login";
						});
					}else{
						alert("数据库配置有误，请重新配置！");
					}
				},
				error:function(){
					alert("服务器地址配置错误！");
					layer.close(index);
				},
				beforeSend:function(){
					index = layer.load(2);
				}
			}); 
		}
	});
	//登录提交前客户端验证
	function checkSubmit(){
		if ($("#txt_url").val() == "") {
			layer.msg("服务器地址不能为空!!!");
			$("#txt_url").focus();
			return false;
		}
		if ($("#txt_port").val() == "") {
			layer.msg("端口号不能为空!!!");
			$("#txt_port").focus();
			return false;
		}
		if ($("#txt_sid").val() == "") {
			layer.msg("实例名不能为空!!!");
			$("#txt_sid").focus();
			return false;
		}
		if ($("#txt_username").val() == "") {
			layer.msg("用户名不能为空！！!");
			$("#txt_username").focus();
			return false;
		}
		if($("#txt_password").val()==""){
			layer.msg("密码不能为空！！!");
			$("#txt_password").focus();
			return false;
		}
		return true;
	}
});
</script>
</html>