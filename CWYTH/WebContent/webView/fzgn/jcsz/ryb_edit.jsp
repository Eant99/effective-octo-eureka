<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>人员详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑人员信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>人员信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>用户帐号</span>
							<input type="text" id="txt_rygh" disabled="disabled" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" name="rygh" value="${ryb.rygh}"/>
							<input type="hidden" id="hid_rybh" name="rybh"  value="${ryb.rybh}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>用户姓名</span>
                            <input type="text" id="txt_xm"  disabled="disabled" class="form-control input-radius" name="xm" value="${ryb.xm}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">用户角色</span>
	                         <input type="text" id="txt_yhjs" disabled="disabled" class="form-control input-radius window" name="yhjs" value="${ryb.yhjs}">	                        
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>所在单位</span>
	                         <input type="text" id="txt_dwbh" disabled="disabled" class="form-control input-radius window" name="dwbh" value="${ryb.dwbh}">
	                        
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>密码</span>
	                        <input type="password" id="txt_mm" class="form-control input-radius" name="mwmm" value="${ryb.mwmm}"/>
						</div>
					</div>
					
				</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon">说&emsp;&emsp;明</span>
                        <textarea id="txt_bz" class="form-control" name="bz" >${ryb.bz}</textarea>
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
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//单位弹窗 
	$("#btn_dwbh").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwbh","单位信息","920","630");
    });
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          rygh:{validators:{notEmpty: {message: '人员工号不能为空'},regexp: {regexp: /^[a-zA-Z0-9_]+$/,message: '人员工号只能包含大写、小写、数字和下划线'}}},
        	xm:{validators:{ notEmpty: {message: '人员姓名不能为空'}}},
          dwbh:{validators: {notEmpty: {message: '所属单位不能为空'}}},
          mwmm:{validators: {notEmpty: {message: '密码不能为空'}}},
          mail:{validators:{emailAddress:{message: '请输入有效的E-mail地址'}}},
          lxdh:{validators:{ phone:{message: '请输入有效的联系电话'},}},
          sfzh:{validators:{cardId:{message: '请输入有效的身份证号'},}}}
	      });
	//保存按钮
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/ryb/doSave",function(val){
			$("#hid_rybh").val(val.rybh);
		},function(val){
			$("#txt_rygh").focus();
		});
	});
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/ryb/goRybListPage";
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
	$("[name='zzzt']").change(function(){
		if($(this).val() == '3'){
			$("#txrq").show();
		}else{
			$("#txrq").hide();
		}
	});
	//页面加载完后，退休日期是否可编辑
	txrq();
	function txrq(){
		var $this = $("[name='zzzt']").val();
		if($this == '3'){
			$("#txrq").show();
		}else{
			$("#txrq").hide();
		}
	}
});
</script>
</body>
</html>