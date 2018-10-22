<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>通知公告</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/ckeditor/ckeditor.js"></script>
<style type="text/css">
	.sub-title{
	font-size: 18px;
	text-align:center;
	color:#000;
	}
	.fjtitle{
	font-size: 12px;
	text-align:center;
	color:#9D9D9D;
	}
	.container {
    width: 860px;
	}
	body{
		overflow: hidden;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid" value="${xtxx.GID}" />
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>通知公告</span>
		</div>
    </div>
	<div class="box">
		<div class="box-panel" style="border:0px;">
			<div class="box-header clearfix" >
            	<div class="sub-title">
            		<h3>${xtxx.TITLE}</h3>
            	</div>
            	<div class="fjtitle" style="margin-top:30px;">
            		发布人：${xtxx.FBR}&emsp;&ensp;发布时间：${xtxx.FBSJ}
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container box-content">
				<div class="row">
					<div class="col-md-12">
						<div class="input-group" style="margin: 0 auto;margin-top:0px;" >
							<div id="editor" style="height:420px;overflow:auto;text-align: center;">
								${Content}
							</div>
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
	//刷新按钮隐藏
	$(".tool-fix").hide();
	$("#btn_back").click(function(){
		window.location.href="${pageContext.request.contextPath}/webView/window/tzgg/tzggList.jsp";
	});
	//取消
	$("#btn_cancelw").click(function(){
    	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	goBjxx();
	//查看页面时处理函数
	$("input,select,textarea").attr("readonly","true");
	$("select").attr("disabled","true");
});
function goBjxx(){
	var bh = $("[name='gid']").val();
	$.ajax({
		type:"post",
		async:false,
		url:"${pageContext.request.contextPath}/fbxx/goBjxx",
		data:"bh="+bh,
		success:function(val){
			val = $.trim(val);
			//top.getTzggsl();
			close(index);			
		},
	});
};
</script>
</body>
</html>