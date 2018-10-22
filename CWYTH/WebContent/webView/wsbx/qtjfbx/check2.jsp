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
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid" value="${gid}" />
	<div class="box" >
		<div class="box-panel">		
			
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">退回意见</span>
							<textarea style="width:100%;heigth:100%;" class="form-control input-radius"></textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="pull-center" style="text-align:center;">
            				<button type="button" class="btn btn-default" id="btn_qd" style="background:#00acec;color: white;">确定</button>
            				<button type="button" class="btn btn-default" id="btn_fh" style="background:#00acec;color: white;">返回</button>
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
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//返回按钮
	$("#btn_qd").click(function(){
		alert("操作成功");
		getIframWindow("${param.pname}").location.href="${ctx}/webView/wsbx/qtjfbx/qtjfbxsh_edit.jsp?operateType=C";
		close(winId);
	});
	$("#btn_fh").click(function(){
		close(winId);
	});

});
</script>

</html>