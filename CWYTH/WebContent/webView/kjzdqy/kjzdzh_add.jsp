<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>会计制度转换维护</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action=""  >
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>原科目编号</span>
					<input type="text" id="ykmbh" class="form-control input-radius" name="ykmbh"  value="${map.YKMBH}"/>
					<input type="hidden" name="guid" id="guid" value="${map.GUID}">
					<input type="hidden" name="operateType" id="operateType" value="${operateType}">
				</div>
				
			</div>
			</div>
			<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>原科目名称</span>
					<input type="text" id="ykmmc" class="form-control input-radius" name="ykmmc"  value="${map.YKMMC}"/>
				</div>
				
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>现科目编号</span>
					<input type="text" id="xkmbh" class="form-control input-radius" name="xkmbh"  value="${map.XKMBH }"/>
				</div>
				
			</div>
			</div>
			<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>现科目名称</span>
					<input type="text" id="xkmmc" class="form-control input-radius" name="xkmmc"  value="${map.XKMMC }"/>
				</div>
				
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
        <div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save" style="display">
				<i class="fa icon-save"></i>
				保存
			</button>
			<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
				取消
			</button>
        </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script >
$(function(){
// 	var validate = $('#myform').bootstrapValidator({fields:{
// 	    }
// 	    });

$("#ykmbh").bindChange("${ctx}/kjzdqy/getLxsr","KJKMZH");
$("#xkmbh").bindChange("${ctx}/kjzdqy/getLxsr","KJKMZH2");
//原科目编号丢失焦点
$("#ykmbh").focusout(function() {
	var ykmbh=$("#ykmbh").val();
	$.ajax({
		url:"${ctx}/kjzdqy/getKmmc",
		data:"ykmbh="+ykmbh,
		success:function(data){
			$("#ykmmc").val(data);
		}
	})
});
//现科目编号丢失焦点
$("#xkmbh").focusout(function() {
	var xkmbh=$("#xkmbh").val();
	$.ajax({
		url:"${ctx}/kjzdqy/getKmmc",
		data:"xkmbh="+xkmbh,
		success:function(data){
			$("#xkmmc").val(data);
		}
	})
});


	//取消
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});	
	
	var validate = $('#myform').bootstrapValidator({fields:{
	    ykmbh:{validators: {notEmpty: {message: '原科目编号不能为空'}}},
	    ykmmc:{validators: {notEmpty: {message: '原科目名称不能为空'}}},
		xkmbh:{validators: {notEmpty: {message: '现科目编号不能为空'}}},
		xkmmc:{validators: {notEmpty: {message: '现科目名称不能为空'}}}
	    }
	    });
	//保存
	$("#btn_save").click(function(){
		var valid;
		if(validate){
			validate.bootstrapValidator("validate");
			valid = $("#myform").data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
		var json = $("#myform").serializeObject("ykmbh","xkmmc");  //json对象				
		var jsonStr = JSON.stringify(json);  //json字符串
			$.ajax({
				   url:"${ctx}/kjzdqy/doSaveKjzd",
		   			type:"post",
		   			data:"json="+jsonStr,
		   			success:function(data){
					alert("保存成功！"); 
					getIframWindow("${param.pname}").table.ajax.reload();
					var winId = getTopFrame().layer.getFrameIndex(window.name);
			    	close(winId);
		   			}
			});
		}
		
		
		
// 			var json = $("#myform").serializeObject("ykmbh","xkmmc");  //json对象				
// 			var jsonStr = JSON.stringify(json);  //json字符串
// 			$.ajax({
// 				        url:"${ctx}/kjzdqy/doSaveKjzd",
// 			   			type:"post",
// 			   			data:"json="+jsonStr,
// 			   			success:function(data){
// 						alert("保存成功！"); 
// 						getIframWindow("${param.pname}").table.ajax.reload();
// 						var winId = getTopFrame().layer.getFrameIndex(window.name);
// 				    	close(winId);
// 			   			}
// 					}); 
	});

});
</script>

</body>
</html>