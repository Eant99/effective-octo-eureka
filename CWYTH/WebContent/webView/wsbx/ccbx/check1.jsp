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
	
	<div class="box">
		<div class="box-panel">		
			
			<div class="container-fluid box-content">
			<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">选择审核意见</span>
							<select id="sel_shyj" class="form-control input-radius">
								<option value="" title="">--请选择--</option>
								<c:forEach var="list" items="${list}">
									<option value="${list.shyj}" title="${list.shyj}">${list.title}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
							<textarea style="width:100%;heigth:100%;" id="shyj" class="form-control input-radius">同意</textarea>
						</div>
					</div>
				</div>
				<div class="row" id="flag">
					<div class="col-md-4">
						<div class="input-group">
							<input type="radio" name="other" value="yxsj" checked>二级院系书记
							<input type="radio" name="other" value="yzfzr">二级院系院长或行政负责人
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
	var flag = "${param.flag}";
	if(flag!="true"){
		$("#flag").hide();
	}
	$("#btn_qd").click(function(){
		//getIframWindow("${param.pname}").location.href="${ctx}/wsbx/ccbx/goCheckListPage";
		var other = "";
		if(flag=="true"){
			other = $("[name='other']").filter(":checked").val();
		}
		$.ajax({
			type:"post",
			url:"${ctx}/wsbx/process/doApprove",
			data:"type=clfbx&procinstid="+"${procinstid}"+"&pass=true&ccywguid=${ccywguid}&guid="+"${guid}"+"&shyj="+$("#shyj").val()+"&other="+other,
			success:function(val){
				alert("操作成功！");
				getIframWindow("${param.pname}").location.href="${ctx}/wsbx/ccbx/goCheckListPage";
				close(winId);
			}
		});
	});
	$("#btn_fh").click(function(){
		close(winId);
	});

});
$(document).on("change","#sel_shyj",function(){
	var val =  $("#sel_shyj").find("option:selected").attr("title");
	$("[id='shyj']").val(val);
});
</script>

</html>