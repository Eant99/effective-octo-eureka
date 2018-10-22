<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<div>
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">查看审核意见信息</c:when>
            		<c:otherwise>编辑审核意见信息</c:otherwise>
            	</c:choose>
			</span>
		</div>
        <div class="pull-right">
			<c:if test="${operateType == 'U' or operateType == 'C'}">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			</c:if>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>审核意见信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<input type="hidden" name="operateType" id="operateType" value="${operateType}">
			<input type="hidden" name="guid" value="${guid}">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>审核类型</span>
                               <select id="drp_dwbb" class="form-control input-radius " name="shlx">
                               	 	<option value="退回" <c:if test="${map.SHLX == '退回'}">selected</c:if>>退回</option>
                               	 	<option value="通过" <c:if test="${map.SHLX == '通过'}">selected</c:if>>通过</option>
                               </select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>标题</span>
	                        <input id="txt_title"  class="form-control input-radius" name="title" value="${map.TITLE}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>审核意见</span>
	                        <textarea id="txt_shyj"  class="form-control input-radius" name="shyj" style="height:100px;width:100%;">${map.SHYJ}</textarea>
						</div>
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
	//返回按钮
	$("#btn_back").click(function(e){
		window.location.href  = "${ctx}/wsbx/shyj/goListPage";
	});
	//必填验证
	var validate = $('#myform').bootstrapValidator({fields:{
				title:{validators:{notEmpty:{message:'不能为空'},stringLength:{message:'标题过长',max:'50'}}},
				shyj:{validators:{notEmpty: {message: '不能为空'},stringLength:{message:'审核意见过长',max:'200'}}}
         }});
	//保存按钮
	$("#btn_save").click(function(e){	
		doSaveNew(validate,"myform","${ctx}/wsbx/shyj/doSave",function(val){
// 			console.log("=====");
// 			alert(val.msg);
// 			var pname = "${param.pname}";
// 			var winId = getTopFrame().layer.getFrameIndex(window.name);
// 			getIframWindow("${param.pname}").table.ajax.reload();
// 	    	close(winId);
		},function(val){
			
		});	
	});
	function doSaveNew(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:$('#' + _formId).serialize(),
				success:function(val){
					close(index);
					var data = JSON.getJson(val);
					alert(data.msg);
					var pname = "${param.pname}";
					var winId = getTopFrame().layer.getFrameIndex(window.name);
					getIframWindow("${param.pname}").table.ajax.reload();
			    	close(winId);
					if(data.success == "true"){
						$("#operateType").val("U");
						if(_success != "" && _success != "" && _success != ""){
							_success(data);
						}
					} else {
						if(_fail != "" && _fail != "" && _fail != ""){
							_fail(data);
						}
					}
				},
				error:function(val){
					close(index);
					alert(getPubErrorMsg());
				},
				beforeSend:function(val){
					index = loading(2);
				}
			});
		}
	}
});
</script>

</html>