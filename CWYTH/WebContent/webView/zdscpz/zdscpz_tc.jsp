<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="margin-top:16px">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>帮助信息设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/javascript/bootstrap/bootstrap.min.js">
<link rel="stylesheet" type="text/css" href="${ctx}/webView/zdscpz/bootstrap-switch.min.css">
<script src="${ctxStatic}/plugins/ckeditor/ckeditor.js"></script>
<style>
	.row{
		width: 60%;
		margin: auto;
		margin-top: 15px!important;
	}
	.input-group-addon{
		min-width: 150px!important;
	}
	.form-control{
		width: 65%!important;
	}
	.bg-white{
		background-color: white!important;
	}
</style>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="id" value="${bzxx.ID}" />
    <div class="container-fluid dialog-body" style="margin-top:16px">
    		<div class="alert alert-info" style="text-align: center;">
    			<strong >提示：双击文本框选择信息</strong>
    		</div>
			<div class="row" style="margin-top: 0px;">
				<div class="col-sm-12">
					<div class="input-group" >
						<span class="input-group-addon" >网上报销自动生成凭证</span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="zdscpz" id="zdscpz" class="onoffswitch-checkbox" 
									<c:if test="${pz.zdscpz == 'Y'}">checked</c:if> value="Y"/>
									 <label class="onoffswitch-label" for="zdscpz">
			                        <span class="onoffswitch-inner"></span>
			                        <span class="onoffswitch-switch"></span>
                                </label>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 0px;">
				<div class="col-sm-12">
					<div class="input-group" >
						<span class="input-group-addon" >是否允许删除凭证</span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="sfyxscpz" id="sfyxscpz" class="onoffswitch-checkbox" 
									<c:if test="${pz.sfyxscpz == 'Y'}">checked</c:if> value="Y" />
									 <label class="onoffswitch-label" for="sfyxscpz">
			                        <span class="onoffswitch-inner"></span>
			                        <span class="onoffswitch-switch"></span>
                                </label>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon" >&nbsp;&nbsp;&nbsp;&nbsp;凭证录入自动化&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="zdlrpz" id="zdlrpz" class="onoffswitch-checkbox"
									<c:if test="${pz.zdlrpz == 'Y' }">checked</c:if> value="Y"/>
									 <label class="onoffswitch-label" for="zdlrpz">
			                        <span class="onoffswitch-inner"></span>
			                        <span class="onoffswitch-switch"></span>
			                        </label>
							</div>
						</div>
					</div>
				</div>
				<!-- 		<div class="col-md-12"> -->
				<!-- 			<div class="input-group"> -->
				<!-- 				<span class="input-group-addon" style="font-size:18px;border-radius: 10px;">凭证录入自动化 </span> -->
				<!-- 				<div class="radio radio-primary radio-inline" style=" margin-left: 80px;"> -->
				<%-- 		            <input  type="radio" class="styled"  value="Y" name="zdlrpz" <c:if test="${pz.zdlrpz == 'Y' }">checked</c:if> /> --%>
				<!-- 		            <label for="B" style="font-size:16px">是</label> -->
				<!-- 		            </div> -->
				<!-- 				<div class="radio radio-primary radio-inline"> -->
				<%-- 		            <input   type="radio" class="styled"  value="N"   name="zdlrpz" <c:if test="${pz.zdlrpz == 'N' }">checked</c:if> /> --%>
				<!-- 		            <label for="B" style="font-size:16px">否</label> -->
				<!-- 		           </div> -->
				<!-- 			</div> -->
				<!-- 		</div> -->
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						 <span class="input-group-addon"><span class="required">*</span>制单人</span>
                         <input type="text" class="form-control input-radius window" id="txt_zdr" name="zdr" value="${pz.zdr }" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择制单人" readonly="readonly"/>
                         <span class="input-group-btn"><button type="button" id="btn_zdr" class="btn btn-link">选择</button></span>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						 <span class="input-group-addon"><span class="required">*</span>复核人</span>
                         <input type="text" class="form-control input-radius window" id="txt_fhr" name="fhr" value="${pz.fhr }" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择复核人" readonly="readonly"/>
                         <span class="input-group-btn"><button type="button" id="btn_fhr" class="btn btn-link">选择</button></span>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-6">
					<div class="input-group">
						 <span class="input-group-addon"><span class="required">*</span>记账人</span>
                         <input type="text" class="form-control input-radius window" id="txt_jzr" name="jzr" placeholder="选择记账人" value="${pz.jzr }" readonly/>
						<span class="input-group-btn"><button type="button" id="btn_jzr" class="btn btn-link">选择</button></span>
					</div>
				</div>
			</div>


		</div>
<div class='page-bottom clearfix'>
	<div class="pull-right">
		<button class="btn btn-default" id="btn_save" ><i class="fa icon-save"></i>保存</button>
<!-- 		<button class="btn btn-default btn-without-icon" id="btn_cancelw">取消</button> -->
	 </div>
</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script  src="${ctx}/webView/zdscpz/bootstrap-switch.min.js"></script>
<script type="text/javascript">

var fields = {fields:{
    zdr:{validators:{notEmpty: {message: ' '}}},
    fhr:{validators:{notEmpty: {message: ' '}}},
    jzr:{validators:{notEmpty: {message: ' '}}}
    }
  };
var validator = $('#myform').bootstrapValidator(fields);

	$(function(){
			
	//选择按钮		
		$(document).on("click","#btn_zdr",function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_zdr","制单人","1000","650");
		});
		$(document).on("click","#btn_fhr",function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_fhr","复核人","1000","650");
		});
		$(document).on("click","#btn_jzr",function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_jzr","记账人","1000","650");
		});
	//双击选择
// 		$("[name=zdr]").click(function(){
// 			var controlId = $(this).attr("id");
// 			select_commonWin("${ctx}/window/rypage?controlId="+controlId,"人员信息","1000","650");
// 		})
// 		$("[name=fhr]").dblclick(function(){
// 			var controlId = $(this).attr("id");
// 			select_commonWin("${ctx}/window/rypage?controlId="+controlId,"人员信息","1000","650");
// 		})
// 		$("[name=jzr]").dblclick(function(){
// 			var controlId = $(this).attr("id");
// 			select_commonWin("${ctx}/window/rypage?controlId="+controlId,"人员信息","1000","650");
// 		})
		
// 		//onoff按扭切换
// 		$("#zdscpz").click(function(){
// 			if($("[name='zdscpz']").val()=='N'){
// 				$("[name='zdscpz']").val("Y");
// 			}else if($("[name='zdscpz']").val()=='Y'){
// 				$("[name='zdscpz']").val("N");
// 			}else{
// 				$("[name='zdscpz']").val("Y");
// 			}
// 		});
// 		//onoff按扭切换
// 		$("#zdlrpz").click(function(){
// 			if($("[name='zdlrpz']").val()=='N'){
// 				$("[name='zdlrpz']").val("Y");
// 			}else if($("[name='zdlrpz']").val()=='Y'){
// 				$("[name='zdlrpz']").val("N");
// 			}else{
// 				$("[name='zdlrpz']").val("Y");
// 			}
// 		});

// 	$('[name="zdscpz"]').bootstrapSwitch({
// 			onSwitchChange : function(event, state) {
// 				if (state == true) {
// 					$(this).val('Y'); //sql =
// 				} else {
// 					$(this).val('N'); // sql !=
// 				}
// 			}
// 			,labelText:'        '
// 		});
// 	$('[name="zdlrpz"]').bootstrapSwitch({
// 			onSwitchChange : function(event, state) {
// 				if (state == true) {
// 					$(this).val('Y'); //sql =
// 				} else {
// 					$(this).val('N'); // sql !=
// 				}
// 			}
// 		});

		editor();
		//取消
		$("#btn_cancelw").click(function() {
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
			close(winId);
		});

		//页面验证
		
		function checkZdFh(){
			var tag = true;
			var zdr = $("#txt_zdr").val();
			var fhr = $("#txt_fhr").val();
			if(zdr == fhr){
				tag = false;
			}
			return tag;
		}
		$("#btn_save").click(
				function() {
					if(!checkZdFh()){
						alert("制单人，复核人不能相同！");
						return;
					};
					doSave(validator, "myform",
							"${pageContext.request.contextPath}/zdscpz/doSave",
							function(val) {
							}, function() {
							});
				});

	});
	function editor() {
		KindEditor.ready(function(K) {
			editor = K.create('#content', {
				afterBlur : function() {
					this.sync();
				}
			});
		});
	}
</script>
</body>
</html>