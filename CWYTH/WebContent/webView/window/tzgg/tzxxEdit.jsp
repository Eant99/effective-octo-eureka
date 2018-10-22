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
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid" value="${xtxx.GID}" />
    <!---------------------------------------- box 页面的主体部分 开始  --------------------------------------------------->
	<div class="">
		<div class="box-panel" style="border:0px;">
			<div class="box-header clearfix">
            	<div class="sub-title">
            		<h3>${xtxx.TITLE}</h3>
            	</div>
            	<div class="fjtitle">
            		发布人：${xtxx.FBR}&emsp;&ensp;发布时间：${xtxx.FBSJ}
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container box-content">
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<div id="editor" style="width:830px;height:430px;overflow:auto;">
								${Content}
							</div>
						</div>
					</div>
				</div>
			</div>
				<div class='page-bottom clearfix' style="left: 0px;">
			        <div class="pull-right">
						<button type='button' class="btn btn-default " id="btn_cancelw">
							关闭
						</button>
			        </div>
			    </div>
		</div>
	</div>
	<!-- ---------------------------------------------------- box 页面的主体部分 结束  ---------------------------------------->
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	goBjxx(),
	
	//查看页面时处理函数
	$("input,select,textarea").attr("readonly","true");
	$("select").attr("disabled","true");
	//取消
	$("#btn_cancelw").click(function(){
    	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
function goBjxx(){
	var bh = $("[name='gid']").val();
	$.ajax({
		type:"post",
		async:false,
		url:"${pageContext.request.contextPath}/tzxx/goBjxx",
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