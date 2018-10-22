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
<title>补助学金标准信息</title>
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
	<input type="hidden" name="czrq" value="${bzxjb.czrq}">
	<input type="hidden" id="bzxjb" name="guid" value="${guid}">
	<input type="hidden" name="qc" value="${qc}">
	<input type="hidden" name="bmh" value="${bmh}">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">查看补助学金标准信息</c:when>
            		<c:otherwise>编辑补助学金标准信息</c:otherwise>
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
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>补助学金标准信息
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
							<span class="input-group-addon"><span class="required">*</span>标准编号</span>
							<c:if test="${operateType == 'U'}">
							<input type="text" id="txt_bzbh" class="form-control input-radius" readonly="readonly" name="bzbh"  value="${bzxjb.bzbh}"/>
							</c:if>
							<c:if test="${operateType == 'C'}">
							<input type="text" id="txt_bzbh" class="form-control input-radius" readonly name="bzbh"  value="系统自动生成"/>
							</c:if>
							<c:if test="${operateType == 'L'}">
							<input type="text" id="txt_bzbh" class="form-control input-radius" readonly name="bzbh"  value="${bzxjb.bzbh}"/>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>补助名称</span>
							<c:if test="${operateType == 'C' }">
							<input type="text" id="txt_bzmc" class="form-control  input-radius " style="text-align: left;" name="bzmc" value=""/>
							</c:if>
							<c:if test="${operateType == 'U' }">
							<input type="text" id="txt_bzmc" class="form-control  input-radius " style="text-align: left;" name="bzmc" value="${bzxjb.bzmc}"/>
							</c:if>
							<c:if test="${operateType == 'L' }">
							<input type="text" id="txt_bzmc" class="form-control  input-radius " style="text-align: left;" name="bzmc" value="${bzxjb.bzmc}"/>
							</c:if>
							
						</div>
					</div>
				   <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>补助金额</span>
							<c:if test="${operateType == 'C' }">
							<input type="text" id="txt_bzje" class="form-control input-radius number " style="text-align: right;" name="bzje" value=""/>
							</c:if>
							<c:if test="${operateType == 'U' }">
							<input type="text" id="txt_bzje" class="form-control input-radius number " style="text-align: right;" name="bzje" value="${bzxjb.bzje}"/>
							</c:if>
							<c:if test="${operateType == 'L' }">
							<input type="text" id="txt_bzje" class="form-control input-radius number " style="text-align: right;" name="bzje" value="${bzxjb.bzje}"/>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否启用</span>
							<c:if test="${operateType == 'C' }">
							<select id="drp_sfqy" class="form-control input-radius " name="sfqy">
								<option value="">请选择</option>
								<option value="1" <c:if test="${bzxjb.sfqy == 1}">selected</c:if>>是</option>
								<option value="0" <c:if test="${bzxjb.sfqy == 0}">selected</c:if>>否</option>
	                       	</select>
							</c:if>
							<c:if test="${operateType == 'U' }">
							<select id="drp_sfqy" class="form-control input-radius " name="sfqy">
                               	<option value="">请选择</option>
								<option value="1" <c:if test="${bzxjb.sfqy == 1}">selected</c:if>>是</option>
								<option value="0" <c:if test="${bzxjb.sfqy == 0}">selected</c:if>>否</option>
	                       	</select>
							</c:if>
							<c:if test="${operateType == 'L' }">
							<select id="drp_sfqy" class="form-control input-radius " name="sfqy">
                               	<option value="">请选择</option>
								<option value="1" <c:if test="${bzxjb.sfqy == 1}">selected</c:if>>是</option>
								<option value="0" <c:if test="${bzxjb.sfqy == 0}">selected</c:if>>否</option>
	                       	</select>
							</c:if>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required"></span>备注</span>
							<c:if test="${operateType == 'C' }">
								<textarea style="width:100%;height:100px" name="bz" class="form-control input-radius" ></textarea>
							</c:if>
							<c:if test="${operateType == 'U' }">
							<textarea style="width:100%;height:100px" name="bz" class="form-control input-radius">${bzxjb.bz}</textarea>
							</c:if>
							<c:if test="${operateType == 'L' }">
							<textarea style="width:100%;height:100px" name="bz" class="form-control input-radius">${bzxjb.bz}</textarea>
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
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	//返回按钮
	$("#btn_back").click(function(e){
		window.location.href  = "${ctx}/bzxjbzsd/goListPage";
	});
	//必填验证
	var validate = $('#myform').bootstrapValidator({fields:{
		bzbh:{validators:{notEmpty:{message:'不能为空'}}},
		bzmc:{validators:{notEmpty:{message:'不能为空'}}},
		bzje:{validators:{notEmpty: {message: '不能为空'}}},
    }});
	//弹框
	$("#btn_fddbrh").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_fzr","人员信息","920","630");
    });//
  	//弹框
	$("#btn_xmmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_xmmc","单位信息","920","630");
    });//
  	//弹框
	$("#btn_zcr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_zcr","人员信息","920","630");
    });
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"myform","${ctx}/bzxjbzsd/doSave",function(val){
			if(val.success){
				alert("信息保存成功！");
				window.location.href  = "${ctx}/bzxjbzsd/goListPage";
			}
		}); 
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