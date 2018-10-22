<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目授权管理</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    line-height: 25px;
    padding-left: 6px;
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${fykmdysz.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>项目批量授权</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目批量授权编辑</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>授权对象</span>
                            <input type="text" id="txt_sqdx" class="form-control input-radius window" name="sqdx" value="${xmsqgl.sqdx}"/>
                            <span class="input-group-btn"><button type="button" id="btn_sqdx" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
						<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>生效日期</span>
                            <input type="text" id="txt_sxrq" class="form-control input-radius date" name="sxrq" value="${xmsqgl.sxrq}" placeholder="生效日期"/>
                             <span class='input-group-addon'>
                            <i class="glyphicon glyphicon-calendar"></i>
                             </span>
						</div>
					</div>	
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>截止日期</span>
                            <input type="text" id="txt_jzrq" class="form-control input-radius date" name="jzrq" value="${xmsqgl.jzrq}" placeholder="截止日期"/>
                             <span class='input-group-addon'>
                            <i class="glyphicon glyphicon-calendar"></i>
                        </span>
						</div>
					</div>			
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>是否允许二次授权</span>
                            <div class="radiodiv">
                             <input type="radio" name="sfyxecsq" value="1"  <c:if test="${operateType == 'C' }">checked</c:if>> 是&ensp;
							<input type="radio" name="sfyxecsq" value="2" checked<c:if test="${xmsqgl.jdfx=='1'}">checked</c:if>> 否
                            </div>
                           
						</div>
					</div>	
			</div>
			<div class="row">
			<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                             <textarea id="txt_bz" class="form-control"  name="bz" >${xmsqgl.bz}</textarea>
						</div>
					</div>
			</div>
		</div>
	</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//获取json数据
	$.getJSON("${ctx}/json/wsbx/jcsz/fykmdysz/fykmdyUpdate.json",function(data){
// 		$("#txt_fyfl").val(data["FYFL"]);
// 		$("#txt_fymc").val(data["FYMC"]);
// 		$("#txt_kmbh").val(data["KMBH"]);
// 		$("#txt_sjfyfl").val(data["SJFYFL"]);
// 		$("#txt_jfkm").val(data["JFKM"]);
// 		$("#txt_dfkm").val(data["DFKM"]);
// 		$("#txt_bz").val(data["BZ"]);
	});
	
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
		 sqdx:{validators: {notEmpty: {message: '不能为空'}}},
		 sxrq:{validators: {notEmpty: {message: '不能为空'}}},
		 jzrq:{validators: {notEmpty: {message: '不能为空'}}},
		 
          }
	      });
	$("#btn_save").click(function(){
		doSave1(validate,"myform","#",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	});
	function doSave1(_validate, _formId, _url, _success, _fail){
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
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){	
					alert("保存成功！");	
					window.location.href =  "${ctx}/webView/wsbx/jcsz/xmsq/xmsqgl.jsp";
				},
				error:function(val){
					alert("保存成功！");					
				}	
			});
		}
	}

 
	//授权对象弹窗
	 $("#btn_sqdx").click(function(){
		 select_commonWin("${ctx}/webView/wsbx/jcsz/xmsq/window.jsp?controlId=txt_sqdx","人员信息","920","630");
	    });
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href =  "${ctx}/webView/wsbx/jcsz/xmsq/xmsqgl.jsp";
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});
	

	
});

</script>
</body>
</html>