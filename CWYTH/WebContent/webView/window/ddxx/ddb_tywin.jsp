<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>地点信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.tool-fix{
	display:none;
}
.row{
	height:25px;
	line-height:25px;
}
</style>
</head>
<body style="background-color: white;">
	<form id="myform" class="form-horizontal" action="" method="post" >
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<div class="container-fluid dialog-body">
			<div class="box-panel">
				<div class="container-fluid box-content">
		        	<hr class="hr-normal">
					<div class="row">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="">地点编号  :&emsp;${ddb.DDH}</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="">地点名称  :&emsp;${ddb.MC}</span>
							</div>
						</div>
					</div>
					<div class="row">	
						<div class="col-xs-12">
							<div class="input-group">
								<span class="">所属单位  :&emsp;${ddb.dwmc}</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class='page-bottom clearfix'>
	        <div class="pull-right">
				<button type="button" class="btn btn-default btn-without-icon" id="btn_close">
					关闭
				</button>
	        </div>
	    </div>	
	</form>
	<%@include file="/static/include/public-manager-js.inc"%>
	<script type="text/javascript">
	$(function(){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
		//取消
		$("#btn_close").click(function(){
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});
	});		
	</script>
</body>
</html>