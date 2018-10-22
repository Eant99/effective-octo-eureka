<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>设置选项</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background:white">
<form id="myform" class="form-horizontal" action="" method="post">
	<div class="box" >
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		修改密码
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-sm-12">
						<div class="input-group">
							<span class="input-group-addon">请输入新密码</span>
							<input type="password" id="txt_passwordn" class="form-control input-radius" name="passwordn" />
						</div>
					</div>									
				</div>	
				<div class="row">
					<div class="col-sm-12">
						<div class="input-group">
							<span class="input-group-addon">请再次输入新密码</span>
							<input type="password" id="txt_passwordnn" class="form-control input-radius" name="passwordnn"/>
						</div>
					 </div>									
				</div>
				<div class="row">
				<div class="col-sm-12">
				 <div class="anzy" style="text-align:center">
					<button type='button' class="btn btn-default" id="btn_saved" style="background-color: #00acec;color: white;">
								<i class="fa icon-save" style="color:white"></i>保存
					</button>
					</div>
				</div>
				</div>
			</div>

		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){

	//保存按钮（修改密码）
	$("#btn_saved").click(function(e){
		var validate = $('#myform').bootstrapValidator({fields : {
				passwordn : {validators : {notEmpty : {message : '不能为空'},stringLength : {min : 6,max : 16,message : '密码长度在6到16之间'}}},
				passwordnn: {validators : {notEmpty : {message : '不能为空'},identical : {field : 'passwordn',message : '两次输入的密码不一致'},}}
				}});
		var valid;
		if(validate){
			validate.bootstrapValidator("validate");
			valid = $('#myform').data('bootstrapValidator').isValid();
		}
		if(valid){
// 		var passwordo = $("[name='passwordo']").val();
		var passwordn = $("[name='passwordn']").val();
		var passwordnn = $("[name='passwordnn']").val();
		var rybh = "${rybh}";
		confirm("确定修改密码为:"+passwordn,{title:"提示信息"},function(){
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/ryb/updpwd",
				data:"rybh="+rybh+"&passwordn="+passwordn+"&passwordnn="+passwordnn,
				dataType:"json",
				success:function(val){
					
					if(val){
					    alert("密码修改成功！");
					    var winId = getTopFrame().layer.getFrameIndex(window.name);
			    	    close(winId);	
					}else{
						alert("密码修改失败！");
					}
				}
			});
		});
		}
	}); 
	//取消（修改密码）
	$("#btn_canceld").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
</body>
</html>