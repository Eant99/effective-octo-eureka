<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8 />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>修改他人密码</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<div class="box" >
		<div class="box-panel">
		    <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>修改他人密码</div>
        	</div>
        	<hr class="hr-normal"/>
			<div class="container-fluid box-content" >
				<div class="row">
					<div class="col-md-4 ">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>人员工号：</span> 
							<input type="text" id="txt_rygh" class="form-control input-radius" name="rygh" value="" types="R" table="K" placeholder="请选择人员工号"> 
							<span class="input-group-btn"><button type="button" id="btn_rygh" class="btn btn-link">选择</button></span>
						</div>
					</div>
				</div>
				<div class="row">
				 	<div class="col-md-4 ">
            			<div class="input-group">
		            		<span class="input-group-addon"><span class="required">*</span>新&ensp;密&ensp;码：</span>
	               			<input type="password" id="txt_pass" class="form-control input-radius" name="mm" table="K" placeholder="请输入新密码">
          	      		</div>
          	      		</div>
               	</div>
				<div class="row">
				 	<div class="col-md-4">
            			<div class="input-group">
		            		<span class="input-group-addon"><span class="required">*</span>确认密码：</span>
	               			<input type="password" id="txt_pass1" class="form-control input-radius" name="checkmm" table="K" placeholder="请再次输入新密码">
                		</div>
               		</div>
				</div>
				<div class="row">
				<div class="col-md-2 col-md-offset-1">
           			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
             		<button type="reset" id="btn_reset" class="btn btn-default"><i class="fa icon-reset"></i>重置</button>
       		    </div>
       		    </div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script>
	$(function() {
		//联想输入提示:人员工号
		$("#txt_rygh").bindChange("${ctx}/suggest/getXx","R");
		//判断两次输入的密码是否相同
		var validate = $('#myform').bootstrapValidator({fields:{
					rygh:{validators : {notEmpty : {message : '不能为空'}}},
					  mm:{stringLength : {min : 6,max : 16,message : '密码长度在6到16之间'},validators : {notEmpty : {message : '不能为空'}}},
				 checkmm:{validators : {identical : {field : 'mm',message : '两次输入的密码不一致'},notEmpty : {message : '不能为空'}}}},
		});
		//保存按钮
		$("#btn_save").click(function() {
			doSave(validate,"myform","${ctx}/xgtrmm/doUpdate",function(val){//成功
				document.getElementById("txt_pass").value = "";
				document.getElementById("txt_pass1").value = "";
				$('#myform').data('bootstrapValidator').resetForm(true);
			},function(val){//失败
			});
		});
		//弹窗 btn_rygh
		$("#btn_rygh").click(function() {
			select_commonWin("${ctx}/window/rypage?controlId=txt_rygh","人员信息", "920", "630");
		});
		$("#btn_reset").click(function(){
			$('#myform').data('bootstrapValidator').resetForm(true);
		});
	});
</script>
</body>
</html>