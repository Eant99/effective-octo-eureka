<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改默认密码</title>
<%@include file="/static/include/public-manager-css.inc"%>
<link href="${pageContext.request.contextPath}/static/plugins/layer/skin/layer.css" rel="stylesheet"/>
<style type="text/css">
	.tool-fix{
		display:none;
	}
</style>
</head>
<body>
<div class="container" style="margin-top:40px;">
<form id="myform" action="" method="post">
	<div class="row">
		<div class="col-sm-4 col-xs-8 col-xs-offset-2">
			<div class="input-group">
				<span class="input-group-addon">旧&ensp;密&ensp;码</span>
				<input type="password" class="form-control input-radius" name="oldpassword" value=""/>
				<input type="hidden" name="rybh" value="${rybh}"/>
			</div>
       </div>
	</div>
	<div class="row">
		<div class="col-sm-4 col-xs-8 col-xs-offset-2">
			<div class="input-group">
				<span class="input-group-addon">新&ensp;密&ensp;码</span>
				<input type="password" class="form-control input-radius" name="newpassword" value=""/>
			</div>
        </div>
	</div>
	<div class="row">
		<div class="col-sm-4 col-xs-8 col-xs-offset-2">
			<div class="input-group">
				<span class="input-group-addon">确认密码</span>
				<input type="password" class="form-control input-radius" name="confirmpassword" value=""/>
			</div>
        </div>
	</div>
    <div class="row">
		<div class="col-xs-12" style="text-align:center;">
			<button class="btn btn-large" type="button" id="btn_save"><i class="fa icon-save"></i>确定</button>
       	</div> 
   </div>
</form>
</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/layer/layer.min.js"></script><!--弹窗-->
<script type="text/javascript">
$(function(){
	var index;
	$("#btn_save").click(function(){
		var xmm = $(":password[name='newpassword']").val();
		var qrmm = $(":password[name='confirmpassword']").val();
		if(xmm != qrmm){
			layer.alert("新密码与确认密码不一致！");
		}
		else if(xmm.length < 6 || xmm.length > 16){
			layer.alert("密码长度需要在6到16位之间！");
		}
		else{
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/login/doUpdpwd",
				data:$("form").serialize(),
				dataType:"json",
				success:function(val){
					layer.close(index);
					if(val.success){
						layer.alert(val.msg,function(index){
							parent.window.location.href = "${pageContext.request.contextPath}/login/login";
						});
					}else{
						layer.alert(val.msg);
						$("[name='oldpassword']").val("");
						$("[name='newpassword']").val("");
						$("[name='confirmpassword']").val("");
					}
				},
				error:function(){
					layer.msg(getPubErrorMsg());
					layer.close(index);				
				},
				beforeSend:function(){
					index = layer.load(2);
				}
			});
		}
	});
});
</script>
</html>