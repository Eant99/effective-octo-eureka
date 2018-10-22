<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产折旧设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
	<form id="myform" class="form-horizontal" action="" method="post">
		<input id="hid_qyny" type="hidden" value="${zczjsz.qyny}">
		<div class="page-header">
			<div class="pull-left">
				<span class="page-title text-primary"> 
					<i class='fa icon-xiangxixinxi'></i> 
					资产折旧设置
				</span>
		 	</div>
	  	</div>
		<div class="box" style="margin-left: 15px;margin-right: 15px;padding-left:6px;padding-top:6px;">
			<div class="row" style="padding-top: 2px;">
			    <div class="col-md-7">
					<div class="alert alert-danger" style="font-size:13px;" role="alert">
					<strong >注 意：</strong><br/>
					 &emsp;&emsp;折旧一经开启不允许修改，如需修改需请关闭折旧功能再重新开启！
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4" style="margin-top:10px">
					<div class="input-group">
						<span class="input-group-addon" style="border-radius: 5px;"><span class="required">*</span>是否启用折旧</span>&emsp;
						<input type="hidden" value="${zczjsz.sfqy}">
						<input type="radio" id="rad_sfqy_1" name="sfqy" value="1" <c:if test="${zczjsz.sfqy == '1'}">checked="checked"</c:if> />启&emsp;用&emsp;&ensp;&ensp;
						<input type="radio" id="rad_sfqy_0" name="sfqy" value="0" <c:if test="${empty zczjsz.sfqy or zczjsz.sfqy == '0'}">checked="checked"</c:if> />禁&emsp;用
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-3">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>启用折旧年月</span>
						<input type="text" id="txt_qyny" class="form-control input-radius nydate" name="qyny" data-format="yyyy-MM" value="${fn:substring(zczjsz.qyny,0,7)}" />
						<span class='input-group-addon'>
					    	<i class="glyphicon glyphicon-calendar"></i>						    
						</span>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon"style="border-radius: 5px;"><span class="required">*</span>折旧方法设置分类&ensp;</span>&ensp;&ensp;
						<input type="radio" id="rad_zjff_0" name="zjff" value="0" <c:if test="${empty zczjsz.zjff or zczjsz.zjff == '0'}">checked="checked"</c:if> />教育部十六大类&emsp;
						<input type="radio" id="rad_zjff_1" name="zjff" value="1" <c:if test="${zczjsz.zjff == '1'}">checked="checked"</c:if> />财政部六大类&emsp;
					</div>
				</div>
			</div>
			<div class="row" style="margin-top:10px"> 
			  <div class="col-md-2 col-md-offset-1">    
        		<button class="btn btn-default" type="button" id="btn_save">
					<i class="faw fa-check"></i>确认
				</button>
			  </div>
       		</div>
       		<div class="row" style="height: 10px;"></div>
		</div>
	</form>
	<%@include file="/static/include/public-manager-js.inc"%>
	<script type="text/javascript">
		$(function(){
			$("#btn_save").click(function(){
				if($("#rad_sfqy_1").prop("checked")){
					if($("#txt_qyny").val() == ""){
						alert("请填写启用折旧年月！");
					}
					else if($("#hid_qyny").val()==""){
						doSave(null,"myform","${pageContext.request.contextPath}/zczjsz/doSave",function(val){
							$("#hid_qyny").val($("#txt_qyny").val());
						},function(val){}); 
						//$(".reload").click();
					}else{
						alert("折旧一经开启不允许修改，如需修改请关闭折旧功能再重新开启！");
					}
				}
				else if($("#rad_sfqy_0").prop("checked")){
					$("#txt_qyny").val("");
					doSave(null,"myform","${pageContext.request.contextPath}/zczjsz/doSave",function(val){
						$("#hid_qyny").val("");
					},function(val){});
					//$(".reload").click();
				} 
				
			});
			
		});
	</script>
</body>
</html>