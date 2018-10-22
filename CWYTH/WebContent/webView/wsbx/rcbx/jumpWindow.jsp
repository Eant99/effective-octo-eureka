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
	<div class="box">
		<div class="box-panel">		
			
			<div class="container-fluid box-content">
				<div class="row" id="flag">
					<div class="col-md-4">
						<div class="input-group">
							<input type="radio" name="other" value="yxsj" checked>二级院系书记
							<br>
							<input type="radio" id="sfxy0" name="other" value="yzfzr">二级院系院长或行政负责人
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
	var sfxy = "${param.sfxy}";
	if(sfxy=='0'){
		$("[name='other']").attr("disabled",true);
		$("#sfxy0").attr("checked","checked");
	}
	//按钮
	$("#btn_qd").click(function(){
		var xz = $("input[name='other']:checked").val();
	 	var guid="${param.guid}";
		var type = "${param.type}";
// 		var s = ;
// 		console.log("---------------"+s);
		$(top.document).contents().find("#${param.pname}")[0].contentWindow.select(guid,xz,type);
		if("${param.type}"!="too"){
			getIframWindow("${param.pname}").table.ajax.reload();
		}
		close(winId);  
	});
	$("#btn_fh").click(function(){
		close(winId);
	});

});
</script>

</html>