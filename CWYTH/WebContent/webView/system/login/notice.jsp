<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>注意事项</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/ckeditor/ckeditor.js"></script>
<style type="text/css">
#edui1_toolbarbox{
 	display:none; 
}
#container{
	border:none;
	margin-top: 30px;
}
#container{
	margin: 0px auto;
	overflow: none;
	height :200px;
}
</style>
</head>
<form id="myform" class="form-horizontal" action="" method="post" >
<div class="" style ="margin-top:20px">
<div class="box-panel" style="border:0px;">
    <div class="container box-content">
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<div id="editor" style="width:630px;height:280px;overflow:auto;">
						${content}
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</form>
<body>
<script>
</script>
</body>
</html>