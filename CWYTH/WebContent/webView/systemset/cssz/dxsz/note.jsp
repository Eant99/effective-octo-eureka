<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>短信设置</title>
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
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>短信设置
			</span>
		</div>
        <div class="pull-right">
			<button class="btn btn-default" type="button" id="btn_save">
			<i class="fa icon-save"></i>保存
			</button>
        </div>
    </div>
	<div class="box" style="margin-top: 27px;">
		<div class="box-panel">
			<div class="box-header clearfix">
	           	<div class="sub-title pull-left text-primary">
	           		<i class="fa icon-xiangxi"></i>短信设置
	           	</div>
	           	<div class="actions">
	           		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	           	</div>
	       	</div>
			<hr class="hr-normal"/>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">公司：</span>
							<input type="text" id="txt_company" class="form-control input-radius" name="company" value="${dataMap.yzm_company}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">内容：</span>
							<input type="text" id="txt_content" class="form-control input-radius" name="content" value="${dataMap.yzm_text}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">模板：</span>
							<select id="txt_mbsz"  class="form-control input-radius" name="mbsz">
								<option></option>
								<option>简略模板</option>
								<option>一般模板</option>
								<option>特殊模板</option>
							</select>
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
		    	company:{validators: {notEmpty: {message: 'smtp不能为空'}}},
		        content:{validators:{notEmpty:{message:'登陆名不能为空'}}},
		           mbsz:{validators:{notEmpty:{message:'密码不能为空'}}}}
		});
		//保存按钮
		$("#btn_save").click(function(){
			var flag ="note";
			var company = $("#txt_company").val();
			var content = $("#txt_content").val();
			var mbsz = $("#txt_mbsz").val();
			doSave(validate,"myform","${ctx}/login/test?flag="+flag+"&company="+company+"&content="+content+"&mbsz="+mbsz,function(val){
				if(val.success){
					alert(val.msg);
				}
			},function(val){
				
			});
		});
	});
</script>
</body>
</html>