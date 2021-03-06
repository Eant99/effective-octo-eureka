<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>角色信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
	<form id="form" class="form-horizontal" action="" method="post" >
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	    <div class="container-fluid dialog-body">
			<div class="row">
				<div  class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>角色编号</span> 
						<input type="text" class="form-control" name="jsbh"  value="${jsb.jsbh}" readonly/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>角色名称</span> 
					<input type="text" class="form-control" name="jsmc" value="${jsb.jsmc}"/>
				</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon">备&emsp;&emsp;注</span> 
					<textarea class="form-control" rows="3" cols="50" name="bz" >${jsb.BZ}</textarea>
				</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">状&emsp;&emsp;态</span>
						<select class="form-control" name="zt">
							<option value="1" <c:if test="${jsb.zt == 1}">selected</c:if>>启用</option>
							<option value="0" <c:if test="${jsb.zt == 0}">selected</c:if>>禁用</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class='page-bottom clearfix'>
	        <div class="pull-right">
				<button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;"><i class="fa icon-save"></i>保存</button>
				<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">取消</button>
	        </div>
	    </div>
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var validate = $('#form').bootstrapValidator({fields: {
        jsmc:{validators:{notEmpty:{message:'不能为空'}}}}
    });
	//查看页面时处理函数
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"form","${ctx}/jsxx/doSave",
		function(val){//成功
			$("#operateType").val("U");
			getIframWindow("${param.pname}").$('#mydatatables').DataTable().ajax.reload();
 			var winId = getTopFrame().layer.getFrameIndex(window.name);
 	    	close(winId);
		},function(val){//失败
			
		});
	}); 
	//取消
	$("#btn_cancelw").click(function(){
		//getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
</body>
</html>