<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>邮箱设置</title>
<link href="${ctxStatic}/plugins/layer/skin/layer.css" rel="stylesheet"/><!-- 弹窗相关 -->
<script src="${ctxStatic}/javascript/jquery/jquery.js"></script><!--jquery-->
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.box-panel + .box-panel{
		margin-top:0px;
	}
	.box{
		overflow:auto;
	}
	.box-panel-left{
		float: left;
	    width: 58%;
	}
	.box-panel-right{
		float: left;
	    width: 40%;
	    margin-left:2%;
	}
	.edui-editor  edui-default{
		width:110%;！important
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<div class="box">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>邮箱设置
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal"/>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4 ">
						<div class="input-group">
							<span class="input-group-addon">邮箱地址：</span>
							<input type="text" id="txt_mail" class="form-control input-radius" name="mail" value="${dataMap.mail}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 ">
						<div class="input-group">
							<span class="input-group-addon">smtp：</span>
							<select id="drp_smtp" class="form-control input-radius" name="smtp">
                                 <option value="smtp.qq.com" <c:if test="${dataMap.smtp == 'smtp.qq.com'}">selected</c:if>>smtp.qq.com</option>
                                 <option value="smtp.126.com"<c:if test="${dataMap.smtp == 'smtp.126.com'}">selected</c:if>>smtp.126.com</option>
                                 <option value="smtp.163.com"<c:if test="${dataMap.smtp == 'smtp.163.com'}">selected</c:if>>smtp.163.com</option>
                                 <option value="smtp.sina.com"<c:if test="${dataMap.smtp == 'smtp.sina.com'}">selected</c:if>>smtp.sina.com</option>
                            </select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 ">
						<div class="input-group">
							<span class="input-group-addon">用&ensp;户&ensp;名：</span>
							<input type="text" id="txt_yhm" class="form-control input-radius" name="yhm" value="${dataMap.yhm}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 ">
						<div class="input-group">
							<span class="input-group-addon">密&emsp;&emsp;码：</span>
							<input type="text" id="txt_mm" class="form-control input-radius" name="mm" value="${dataMap.mm}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div style="padding-left:85px;" >
			             	<button type="button" class="btn btn-default" id="btn_save">
			             		<i class="fa icon-save"></i>
			             		保存
			             	</button>
		            	</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<script src="${ctxStatic}/plugins/layer/layer.min.js"></script><!--弹窗-->
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//验证框架
	var validate = $('#myform').bootstrapValidator({fields:{
	        	smtp:{validators: {notEmpty: {message: 'smtp不能为空'}}},
	            yhm:{validators:{notEmpty:{message:'登陆名不能为空'}}},
	        mm:{validators:{notEmpty:{message:'密码不能为空'}}},
	            mail:{validators:{notEmpty:{message:'邮箱地址不能为空'},
	    emailAddress:{message:'电子邮箱格式不正确'}}}}
    });
	//保存按钮
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/yxsz/doSave",function(val){
		},function(val){
		});
	});
});
</script>
</body>
</html>