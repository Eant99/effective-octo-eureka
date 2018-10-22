<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<div class="box" style="top:36px">
		<div class="box-panel">		
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>退回意见</span>
							<textarea style="width:100%;heigth:100%;" class="form-control input-radius" name="shyj"></textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="pull-center" style="text-align:center;">
            				<button type="button" class="btn btn-default" id="btn_sure" style="background:#00acec;color: white;">确定</button>
            				<button type="button" class="btn btn-default" id="btn_cancel" style="background:#00acec;color: white;">取消</button>
        				</div>
					</div>
				</div>
			</div>
		</div>
		</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var basePath = "${ctx}/xmgl/xmsp";
var winId = getTopFrame().layer.getFrameIndex(window.name);
var fields={fields:{
    shyj:{validators:{notEmpty: {message: '审核意见不能为空'}}}
    }
  };
var validator = $('#myform').bootstrapValidator(fields);

$(function(){
	$("#btn_sure").click(function(){
		validator.bootstrapValidator("validate");
		var valid = $('#myform').data('bootstrapValidator').isValid();
		if(valid){
			var url = basePath+"/doCheck?splx=${param.splx}&checkResult=reject&guid=${param.guid}";
			var data = arrayToJson($("#myform").serializeArray());
			doAction(url,data,function(){
				getIframWindow("${param.pname}").history.back();
				close(winId);
			});
		}
	});
	$("#btn_cancel").click(function(){
		close(winId);
	});
});
</script>

</html>