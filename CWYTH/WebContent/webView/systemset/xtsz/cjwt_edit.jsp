<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>常见问题</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid" value="${cjwt.GUID}" />
    <div class="container-fluid dialog-body">
	<div class="row">
		<div class="col-md-6">
			<div class="input-group">
				<span class="input-group-addon"><span class="required">*</span>问题</span>
				<input type="text" name="title" id="title" class="form-control input-radius"  value="${cjwt.TITLE}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="input-group">
				<span class="input-group-addon"><span class="required">*</span>答案</span>
				<div id="editor">
					<textarea style="width:99%;height:500px;" name="xx" id="xx">${cjwt.XX}</textarea>
				</div>
			</div>
		</div>
	</div>
</div>
<div class='page-bottom clearfix'>
	<div class="pull-right">
		<button class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
			 <i class="fa icon-save"></i>保存
		</button>
		<button class="btn btn-default btn-without-icon" id="btn_cancelw">取消</button>
	 </div>
</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function(){
		$(".tool-fix").hide();
		//查看页面时处理函数
		if($("[name='operateType']").val()=='L'){
			$("input,select,textarea").attr("readonly","true");
		}
		//取消
		$("#btn_cancelw").click(function(){
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});
		//页面验证
		var validate = $('#myform').bootstrapValidator({
			fields: {
				title: {validators: {notEmpty: {message: '不能为空'}}},
	            xx: {validators: {notEmpty: {message: '不能为空'}}}
	            }
	    });
		//保存按钮
		$("#btn_save").click(function(e){
			doSave(validate,"myform","${ctx}/cjwt/doSave",function(val){
				$("#btn_cancelw").click();
			},function(val){
			});
		}); 
	});
</script>
</body>
</html>