<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>采购目录信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.test1::before {
    	content: "正常" ! important;
	}
	.test1::after {
    	content: "禁用" ! important;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${cgmlsz.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>编辑采购目录信息
			</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>采购目录信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			
				<div class="row">
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目录代码</span>
							<input type="text" id="txt_mldm" class="form-control input-radius" name="mldm" maxlength="20" value="${cgmlsz.mldm}"/>
						</div>
					</div>
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目录名称</span>
							<input type="text" id="txt_mlmc" class="form-control input-radius" name="mlmc" maxlength="50" value="${cgmlsz.mlmc}"/>
						</div>
					</div>
				</div>
					
				<div class="row">
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">上级目录</span>
								<input type="text" id="txt_sjmlmc" class="form-control input-radius window" readonly="readonly" name="sjmlmc" maxlength="32" value="${cgmlsz.sjmlmc}">
								<input type="hidden" name='sjml' id="txt_sjml" readonly value="${cgmlsz.sjml}">
							<c:if test="${operateType == 'C' }">
								<span class="input-group-btn"><button type="button" id="btn_sjml" class="btn btn-link btn-custom">选择</button></span>
							</c:if>
						</div>
					</div>
					<div class="col-md-6">
						<div class="input-group">
								<span class="input-group-addon">备注</span>
							<input type="text" id="txt_bz" class="form-control input-radius" name="bz" maxlength="200" value="${cgmlsz.bz}"/>
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
	//必填验证
	var validate = $('#myform').bootstrapValidator({fields:{
        	    mldm:{validators:{notEmpty:{message:'不能为空'}}},
        	    mlmc:{validators:{notEmpty:{message: '不能为空'}}}	   
         }});
		
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href =  "${ctx}/webView/ysgl/xmsz/cgmlsz/cg_list.jsp?mldm="+"${param.mldm}";
	});
	
	//点击“选择”后，弹窗
	$("#btn_sjml").click(function(){
		select_commonWin("${ctx}/cgmlsz/window?controlId=txt_sjmlmc&text_id=txt_sjml","上级目录","920","630");
    });

	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"myform","${ctx}/cgmlsz/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	});	
	
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	//页面加载完后，控制实验室信息模块是否展示
	sysbz();
	//onoff按扭切换
	$("#btn_onoff").click(function(){
		if($("[name='sysbz']").val()=='0'){
			$("[name='sysbz']").val("1");
		}else if($("[name='sysbz']").val()=='1'){
			$("[name='sysbz']").val("0");
		}else{
			$("[name='sysbz']").val("0");
		}
		sysbz();
	});

	//nnoff按扭切换
	$("#btn_nnoff").click(function(){
		if($("[name='sfxy']").val()=='0'){
			$("[name='sfxy']").val("1");
		}else if($("[name='sfxy']").val()=='1'){
			$("[name='sfxy']").val("0");
		}else{
			$("[name='sfxy']").val("1");
		}
	});

	//dnoff按扭切换
	$("#btn_dnoff").click(function(){
		if($("[name='dwzt']").val()=='0'){
			$("[name='dwzt']").val("1");
		}else if($("[name='dwzt']").val()=='1'){
			$("[name='dwzt']").val("0");
		}else{
			$("[name='dwzt']").val("1");
		}
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&dwbh=${dwb.DWBH}";
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016";
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016";
	    	}
		 }
	});
});
function sysbz(){
	var $this = $("[name='sysbz']").val();
	if($this == '0'){
		$("#sysxx").show();
	}else{
		$("[name='syslx']").val("");
		$("[name='syslb']").val("");
		$("[name='sysmj']").val("0.00");
		$("[name='sysjb']").val("");
		$("[name='ssxk']").val("");
		$("[name='sysgs']").val("");
		$("#sysxx").hide();
	}
}
$(function(){
	$("[name='dwzt']").change(function(){
		if($("[name='dwzt']").val()=='0'){
		 $.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/dwb/getNewStatus",
			data:"dwbh=${dwb.DWBH}",
			success:function(val){
				var data = JSON.getJson(val);
				 if(data.success=='false'){
					alert(data.msg);
					$("#drp_dwzt option").eq(0).attr("selected",true);
					$("#drp_dwzt option").eq(1).attr("selected",false);
				} 
			},
		}); 
			
		}
	});
});
</script>

</html>