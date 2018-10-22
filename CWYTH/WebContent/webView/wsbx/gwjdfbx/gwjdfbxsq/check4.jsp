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
							<textarea style="width:100%;height: 100px;" name="shyj" class="form-control input-radius"></textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否退回到申请人</span>
							<input type="radio" name="apply" value="1">是							
							<input type="radio" name="apply" value="2" checked>否							
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
var target = "${ctx}/wsbx/gwjdfbx/gwjdfbxsq";
var guid = "${param.guid}";
var procinstid = "${param.procinstid}";
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//返回按钮
	$("#btn_qd").click(function(){
		var shyj = $("[name='shyj']").val();
		var apply = $("input[name='apply']:checked").val();
		if(shyj == ""){
			alert("请填写审核意见！");
		}else{
			
		$.ajax({
			type:"post",
			url:"${ctx}/wsbx/process/doApprove",
			data:"&procinstid="+procinstid+"&pass=false&type=gwjdfbx&guid="+guid+"&shyj="+shyj+"&apply="+apply,
			success:function(val){
				var data = JSON.getJson(val);
				if(data.success='true'){
					alert("操作成功！");
					getIframWindow("${param.pname}").location.href=target+"/pageSkip?pageName=gwjdfbxsh_list";
					close(winId);
				}
			}
		});
		}
	});
	$("#btn_fh").click(function(){
		close(winId);
	});

});
$(document).on("change","#sel_shyj",function(){
	var val =  $("#sel_shyj").find("option:selected").attr("title");
	$("[name='shyj']").val(val);
});
</script>

</html>