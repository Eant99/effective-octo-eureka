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
<title>单位信息</title>
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
            		<c:when test="${operateType == 'L'}">查看经费设置信息</c:when>
            		<c:otherwise>编辑经费设置信息</c:otherwise>
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
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>经费设置信息
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
								<span class="input-group-addon"><span class="required">*</span>部门名称</span>
							</c:if>
							<c:if test="${operateType == 'C' and empty bmh}">
								<input type="text" id="txt_xmmc" class="form-control input-radius window" name="bmbh" value="">
							</c:if>
							<c:if test="${operateType == 'C' and not empty bmh}">
								<input type="text" id="txt_xmmc" class="form-control input-radius window" name="bmbh" value="(${bmh})${qc}">
							</c:if>
							<c:if test="${operateType == 'U' }">
								<input type="text" id="txt_xmmc" class="form-control input-radius window" name="bmbh" value="${dwb.bmmc}">
							</c:if>
							<span class="input-group-btn"><button type="button" id="btn_xmmc" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>年度</span>
							</c:if>
							<c:if test="${operateType == 'C' }">
								<input type="text" id="txt_nd" class="form-control input-radius year" name="nd" table="C" value="<%=date %>" data-format="yyyy" />
							</c:if>
							<c:if test="${ operateType == 'U' }">
								<input type="text" id="txt_nd" class="form-control input-radius date" name="nd" value="${dwb.ND}"/>
							</c:if>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目编号</span>
							<input type="text" id="txt_ktbh" class="form-control input-radius" name="xmbh" value="${dwb.xmbh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目名称</span>
							<input type="text" id="txt_bz" class="form-control input-radius" name="xmmc" value="${dwb.xmmc}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<c:if test="${operateType == 'U' or operateType == 'C' }">
								<span class="input-group-addon"><span class="required">*</span>项目负责人</span>
							</c:if>
							<input type="hidden" name="dwbh"  value="${dwb.DWBH}"/>
							<input type="text" id="txt_fzr" class="form-control input-radius window" name="fzr" value="${dwb.FZRMC}">
							<span class="input-group-btn"><button type="button" id="btn_fddbrh" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目类型</span>
<%-- 							<input type="text" id="txt_bz" class="form-control input-radius" name="xmlx" value="${dwb.XMLX}"/> --%>
							<select id="txt_xmfl" class="form-control input-radius select2" name="xmlx">
                            	<option value="">未选择</option>
                                <c:forEach var="item" items="${xmflList}">
                           			<option value="${item.DM}" data-id="${srxm.xmfl }" <c:if test="${item.DM == dwb.xmlx }">selected</c:if>>${item.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>经费类型</span>
<%-- 							<input type="text" id="txt_bz" class="form-control input-radius" name="xmlx" value="${dwb.XMLX}"/> --%>
							<select id="txt_jflx" class="form-control input-radius select2" name="jflx">
                            	<option value="">未选择</option>
                                <c:forEach var="item" items="${jflxList}">
                           			<option value="${item.DM}" <c:if test="${item.DM == dwb.jflx }">selected</c:if>>${item.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目开始时间</span>
							<input type="text" id="txt_kssj" class="form-control input-radius date" name="kssj" data-format="yyyy-mm-dd -hh-mm-ss" value="${dwb.KSSJ}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目结束时间</span>
							<input type="text" id="txt_jssj" class="form-control input-radius date" name="jssj" data-format="yyyy-mm-dd -hh-mm-ss" value="${dwb.JSSJ}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>预算金额（万元）</span>
							<input type="text" id="txt_ysje" class="form-control number1 input-radius " style="text-align: right;" name="ysje" value="${dwb.YSJE}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>剩余金额（万元）</span>
							<input type="text" id="txt_syje" class="form-control number1 input-radius" style="text-align: right;" name="syje" value="${dwb.SYJE}"/>
						</div>
					</div>
					
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required"></span>备注</span>
<%-- 							<input type="text" id="txt_bz" class="form-control input-radius" name="bz" value="${dwb.BZ}"/> --%>
							<textarea style="width:100%;height:100px" name="bz" class="form-control input-radius" >${dwb.bz}</textarea>
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
		window.location.href  = "${ctx}/grjfsz/goListPage";
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
  	//弹框
	$("#btn_zcr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_zcr","人员信息","920","630");
    });
	//保存按钮
	$("#btn_save").click(function(e){
		var guid = $("#111111").val();
		doSave(validate,"myform","${ctx}/grjfsz/doSave?guid="+guid,function(val){
			if(val.success){
				alert(val.msg);
				window.location.href  = "${ctx}/grjfsz/goListPage";
			}
		}); 
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