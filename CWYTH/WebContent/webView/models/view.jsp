<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>表单</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
	<form id="myform" class="form-horizontal" action="" method="post">
		 <div class="container box-content">
			<div class="container-fluid dialog-body">
				<div class="box-header clearfix" style="margin-top: -5px;">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>
	            		表单信息
	            	</div>
        		</div>
        		<input  type="hidden" name="cmd" value="add">
				<div class="row">
					<div class="col-xs-6">
						${models}
					</div>
				</div>
		</div>
		</div>
		<div class='page-bottom clearfix'>
			<!-- 保存和返回按钮 开始 -->
			<div class="pull-right">
				<button class="btn btn-default" id="btn_save"> <i class="fa icon-save"></i> 保存 </button>
				<button class="btn btn-default btn-without-icon" id="btn_cancelw"></i> 关闭</button>
			</div>
		</div>
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
		
$(function() {
	//保存按钮
	$("#btn_save").click(function(e) {
		$.ajax({  
            type: "POST",  
            url:"${pageContext.request.contextPath}/model/getStartFormAndStartProcess",  
            data:$('#myform').serialize(),// 序列化表单值  
            async: false,  
            error: function(request) {  
                alert("Connection error");  
            },  
            success: function(data) { 
            	alert("启动成功");
            	getIframWindow("${param.pname}").table.ajax.reload()
            	var winId = getTopFrame().layer.getFrameIndex(window.name);
				close(winId);
            }  
        });
	});
	//关闭
	$("#btn_cancelw").click(function() {
		var winId = getTopFrame().layer.getFrameIndex(window.name);
		close(winId);
	});
	
});
	</script>
</body>
</html>