<%@page import="com.googosoft.constant.Constant"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学生补助金统计信息</title>
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
<%
	String date = Constant.MR_YEAR();
	%>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="czr" value="${loginId}">
	<input type="hidden" name="czrq" value="${dwb.czrq}">
	<input type="hidden" id="111111" name="guid" value="${guid}">
	<input type="hidden" name="qc" value="${qc}">
	<input type="hidden" name="bmh" value="${bmh}">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">学生补助金统计信息</c:when>
            		<c:otherwise>学生补助金统计信息</c:otherwise>
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
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>学生补助金统计信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>学号</span>
							</c:if>
							<c:if test="${operateType == 'C' and empty bmh}">
								<input type="text" id="txt_xh" class="form-control input-radius window" name="xh" value="">
							</c:if>
							<c:if test="${operateType == 'C' and not empty bmh}">
								<input type="text" id="txt_xh" class="form-control input-radius window" name="xh" value="(${bmh})${qc}">
							</c:if>
							<c:if test="${operateType == 'U' }">
								<input type="text" id="txt_xh" class="form-control input-radius window" name="xh" value="201309280010">
							</c:if>
							<span class="input-group-btn"><button type="button" id="btn_xm" class="btn btn-link btn-custom">选择</button></span>
<!-- 							<span class="input-group-btn"><button type="button" id="btn_xmmc" class="btn btn-link btn-custom">选择</button></span> -->
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>姓名</span>
							</c:if>
							<c:if test="${operateType == 'C' }">
								<input type="text" id="txt_xm" class="form-control input-radius " name="nd" table="C" />
							</c:if>
							<c:if test="${ operateType == 'U' }">
								<input type="text" id="txt_xm" class="form-control input-radius " name="nd" value="苏桂莲"/>
							</c:if>
						</div>
					</div>
 					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>所在院系</span>
							</c:if>
							<c:if test="${operateType == 'C' and empty bmh}">
								<input type="text" id="txt_xmmc" class="form-control input-radius window" name="bmbh" value="">
							</c:if>
							<c:if test="${operateType == 'C' and not empty bmh}">
								<input type="text" id="txt_xmmc" class="form-control input-radius window" name="bmbh" value="(${bmh})${qc}">
							</c:if>
							<c:if test="${operateType == 'U' }">
								<input type="text" id="txt_xmmc" class="form-control input-radius window" name="bmbh" value="财务管理">
							</c:if>
							<span class="input-group-btn"><button type="button" id="btn_xmmc" class="btn btn-link btn-custom">选择</button></span> 
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>专业</span>
							</c:if>
							<c:if test="${operateType == 'C' and empty bmh}">
								<input type="text" id="txt_zy" class="form-control input-radius window" name="zy" value="">
							</c:if>
							<c:if test="${operateType == 'C' and not empty bmh}">
								<input type="text" id="txt_zy" class="form-control input-radius window" name="zy" value="(${bmh})${qc}">
							</c:if>
							<c:if test="${operateType == 'U' }">
								<input type="text" id="txt_zy" class="form-control input-radius window" name="zy" value="财经系">
							</c:if>
							<span class="input-group-btn"><button type="button" id="btn_xmmc" class="btn btn-link btn-custom">选择</button></span> 
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>等级</span>
							</c:if>
							<c:if test="${operateType == 'C' }">
								<input type="text" id="txt_nd" class="form-control input-radius " name="nd" table="C" />
							</c:if>
							<c:if test="${ operateType == 'U' }">
								<input type="text" id="txt_nd" class="form-control input-radius date" name="nd" value="三级"/>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>金额</span>
							</c:if>
							<c:if test="${operateType == 'C' }">
								<input type="text" id="txt_nd" class="form-control input-radius " style="text-align: right;" name="nd" table="C" />
							</c:if>
							<c:if test="${ operateType == 'U' }">
								<input type="text" id="txt_nd" class="form-control input-radius date" style="text-align: right;" name="nd" value="3000.00"/>
							</c:if>
						</div>
					</div>
				</div>
				
				
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required"></span>备注</span>
							<c:if test="${operateType == 'C' }">
								<textarea style="width:100%;height:100px" name="bz" class="form-control input-radius" >${dwb.bz}</textarea>
							</c:if>
							<c:if test="${operateType == 'U' }">
							<textarea style="width:100%;height:100px" name="bz" class="form-control input-radius" >苏桂莲于2017年5月被授予3000元人民币整奖金</textarea>
							</c:if>
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
		window.location.href  = "${ctx}/xsbzjtj/goListPage";
	});
	//联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_fgld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_sjdw").bindChange("${ctx}/suggest/getXx","D");	
	//必填验证
	var validate = $('#myform').bootstrapValidator({fields:{
		fzr:{validators:{notEmpty:{message:'不能为空'}}},
		xmmc:{validators:{notEmpty:{message:'不能为空'}}},
		nd:{validators:{notEmpty: {message: '不能为空'}}},
		ysje:{validators:{notEmpty: {message: '不能为空'}}},
		zcr:{validators:{notEmpty: {message: '不能为空'}}},
		xmfl:{validators:{notEmpty: {message: '不能为空'}}},
		kssj:{validators:{notEmpty: {message: '不能为空'}}},
		jssj:{validators:{notEmpty: {message: '不能为空'}}},
		ktbh:{validators:{notEmpty: {message: '不能为空'}}},
		syje:{validators:{notEmpty: {message: '不能为空'}}}
    }});
	
	//弹框
	$("#btn_fddbrh").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_fzr","人员信息","920","630");
    });//
  	//弹框
	$("#btn_xmmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_xmmc","单位信息","920","630");
    });//
    $("#btn_xm").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_xm","单位信息","920","630");
    });//
  	//弹框
	$("#btn_zcr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_zcr","人员信息","920","630");
    });
	//保存按钮
	$("#btn_save").click(function(e){
		var guid = $("#111111").val();
// 		doSave(validate,"myform","${ctx}/grjfsz/doSave?guid="+guid,function(val){
// 			if(val.success){
				alert("信息保存成功！");
				window.location.href  = "${ctx}/xsbzjtj/goListPage";
// 			}
// 		}); 
	});	
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