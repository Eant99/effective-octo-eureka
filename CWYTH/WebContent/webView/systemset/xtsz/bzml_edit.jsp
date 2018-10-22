<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>帮助目录维护</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="form" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>目录编号</span> 
					<input type="text" class="form-control" name="bh"  value="${mlxx.bh}" readonly/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>目录名称</span>
					<input type="text" class="form-control" name="mc"  value="${mlxx.mc}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon">排序序号</span>
					<input type="number" min="1" id="txt_zt" class="form-control input-radius" name="zt" value="${mlxx.zt}">
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
	     <div class="pull-right">
			<button class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
			 <i class="fa icon-save"></i>
					保存
			</button>
			<button class="btn btn-default btn-without-icon" id="btn_cancelw">
					取消
			</button>
	      </div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//查看页面时处理函数
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	var validate = $('#form').bootstrapValidator({fields: {
           mc:{validators:{notEmpty:{message:'不能为空'}}},
           bh:{validators:{notEmpty:{message:'不能为空'}}},}
    });
	//取消
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"form","${ctx}/bzml/doSave",function(val){//成功
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	},function(val){//失败
	});
	}); 
});
</script>
</body>
</html>