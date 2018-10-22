<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>增加功能科目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.tool-fix{
		display:none;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid" value="${gid}" />
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">查看功能科目信息</c:when>
            		<c:otherwise>编辑功能科目信息</c:otherwise>
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
	<div class="box" style="top:36px">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>科目信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class ="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>科目编号</span>
							<input type="text" id="kmsz_kmbh" class="form-control input-radius window" name="kmbh" value="${kmsz.kmbh}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>科目名称</span>
							<input type="text" id="kmsz_kmnd" class="form-control input-radius" name="kmmc" value="${kmsz.kmmc}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">							
							<span class="input-group-addon" ><span class="required">*</span>所属会计科目编号</span>
							<input type="text" id="kmsz_kmmc" class="form-control input-radius" name="sskjkmbh" value="${kmsz.sskjkmbh}"/> 						
							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"></c:if><button type="button" id="btn_jfbykm1" class="btn btn-link btn-custom">选择</button></span>							
						</div>						
					</div>
					 
				</div>
				<div class="row">
					<%-- <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>操&ensp;作&ensp;人</span>
							<input type="text" id="kmsz_zkmdmsx" class="form-control input-radius" name="czr" value="${kmsz.czr}"/> 
							
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">操作日期</span>
							<input type="text" id="kmsz_kmjbdm" class="form-control input-radius" name="kmjbdm" value="${kmsz.kmjbdm}">
						</div>
					</div> --%>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/static/include/public-list-js.inc"%> 
</form>

<script >
$(function(){
	//json
	$.getJSON("${ctx}/json/kjhs/kmsz/gnkmsz/gnkmszForUpdate.json",function(data){
		$("#kmsz_kmbh").val(data["kmbh"]);
		$("#kmsz_kmnd").val(data["kmmc"]);
		$("#kmsz_kmmc").val(data["sskjkmbh"]);
	});
	//选择框
	 $("#btn_jfbykm1").click(function(){
   		select_commonWin("${ctx}/kmsz/window?controlId=kmsz_kmmc","科目信息","920","630");
    }); 
	
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/kmsz/goGnkmPage";
	});
	
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          kmbh:{validators: {notEmpty: {message: '科目编号不能为空'}}},
          kmmc:{validators: {notEmpty: {message: '科目名称不能为空'}}},
          sskjkmbh:{validators: {notEmpty: {message: '所属会计科目编号不能为空'}}},
   		  czr:{validators: {notEmpty: {message: '操作人不能为空'}}},
          }
	      });
	$("#btn_save").click(function(){
		doSave1(validate,"myform","${ctx}/Jjkm/doSave",function(val){
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
				},
				error:function(val){
					alert("保存成功！");					
				}	
			});
		}
	}

});

</script>

</body>
<%-- <%@include file="/static/include/public-manager-js.inc"%> --%>



</html>