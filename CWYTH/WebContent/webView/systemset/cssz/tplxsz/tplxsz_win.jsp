<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>图片设置信息-查看</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body  style="background-color: white;">
	<form id="myform" class="form-horizontal" action="">
		<div class="container-fluid dialog-body">
			<div class="row">
			<div  class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">类型编号</span> 
						<input type="text" class="form-control input-radius" name="lxbh"  value="${tpb.lxbh}" readonly/> 
					</div>
				</div>
			</div>
			<div class="row">
			<div  class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">单据名称</span> 
						<input type="text" class="form-control input-radius" name="djmc"  value="${tpb.djmc}" readonly/> 
					</div>
				</div>
			</div>
			<div class="row">
			<div  class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">图片类型</span> 
						<input type="text" class="form-control input-radius" name="tplx"  value="${tpb.lxmc}" readonly/> 
					</div>
				</div>
			</div>
			<div class="row">
			<div  class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">备&emsp;&emsp;注</span> 
						<textarea class="form-control" rows="4" cols="50" name="bz" readonly>${tpb.BZ}</textarea> 
					</div>
				</div>
			</div>
			<div class="row">
			<div  class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">是否必须上传</span> 
						<input type="text" class="form-control input-radius" name="sfbxsc"  value="<c:choose><c:when test="${tpb.sfbxsc == 1}">是</c:when><c:otherwise>否 </c:otherwise></c:choose>" readonly/> 
					</div>
				</div>
			</div>
		</div>
		<div class='page-bottom clearfix'>
	    <div class="pull-right">
			<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">取消</button>
	    </div>
	</div>
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//取消
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
</body>
</html>