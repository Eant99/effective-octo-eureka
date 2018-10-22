<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>帮助信息设置</title>
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
	body{
	background:#FFFFFF;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" style = "width:100%">
	<input type="hidden" name="guid" value="${bzxx.ID}" />
    <!---------------------------------------- box 页面的主体部分 开始  --------------------------------------------------->
		<div class="box-panel" style="border:0px; width:100%;">
			<div class="box-header clearfix">
            	<div class="sub-title">
            		<h3>${bzxx.YWMC}</h3>
            	</div>
<!--             	<div class="fjtitle"> -->
<%--             		建档人：${bzxx.JDR}&emsp;&ensp;建档时间：${bzxx.JDRQ} --%>
<!--             	</div> -->
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
			<div class="container box-content" style = "width:100%">
				<div class="row" >
					<div class="col-md-12" style = "padding-left:20px">
						<div class="input-group" >
							<div id="editor" >
								${Content}
							</div>
						</div>
					</div>
				</div>
			</div>
<!-- 				<div class='page-bottom clearfix' style="left: 0px;"> -->
<!-- 			        <div class="pull-right"> -->
<!-- 						<button class="btn btn-default" type="button" id="btn_cancelw">关闭 -->
<!-- 						</button> -->
<!-- 			        </div> -->
<!-- 			    </div> -->
		</div>
	<!-- ---------------------------------------------------- box 页面的主体部分 结束  ---------------------------------------->
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//查看页面时处理函数
	$("input,select,textarea").attr("readonly","true");
	$("select").attr("disabled","true");
	//关闭
// 	$("#btn_cancelw").click(function(){
// 		top.window.close();
// 	});
});
</script>
</body>
</html>