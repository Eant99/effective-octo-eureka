<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>增加经济科目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.tool-fix{
		display:none;
	}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid" value="${kmsz.guid}" />
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">查看经济科目信息</c:when>
            		<c:otherwise>编辑经济科目信息</c:otherwise>
            	</c:choose>
			</span>
		</div>
        <div class="pull-right">
			<c:if test="${operateType == 'U' or operateType == 'C'}">
			  <!--  <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button> -->
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
							<span class="input-group-addon">科目编号</span>
							<input type="text" id="kmsz_kmbh" class="form-control input-radius window"  name="kmbh" value="${kmsz.kmbh}" readonly
							<c:if test="${operateType=='U'}" >readonly</c:if>>
							<input type="hidden" id="kmsz_k" class="form-control input-radius window"  name="k" value="${k}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目名称</span>
							<input type="text" id="kmsz_kmmc" class="form-control input-radius" name="kmmc" value="${kmsz.kmmc}" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目级次</span>
							<input type="text" id="kmsz_kmjc" class="form-control input-radius" readonly name="kmjc" value="${kmsz.kmjc}">
						</div>
					</div>
 					</div>
	 			<div class="row"> 
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否启用</span>
							<div class="radiodiv" >
								<input type="radio" id="txt_qyf1" name="qyf" value="1" class="bc"<c:if test="${kmsz.qyf!='0'}" >checked </c:if>/>是
								<input type="radio" id="txt_qyf2" name="qyf" value="0" class="bc"<c:if test="${kmsz.qyf=='0'}" >checked </c:if>/>否
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>预算类型</span>
							<select id="txt_yslx" class="form-control input-radius select2" name="yslx" disabled>
                           		<option value="01" <c:if test="${'01' == kmsz.yslx }">selected</c:if>>基本支出</option>
                           		<option value="02" <c:if test="${'02' == kmsz.yslx }">selected</c:if>>项目支出</option>
                            </select>
						</div>
					</div>
				</div>
				<div class ="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">说明</span>
                    		<textarea style="width:100%;height:60px;" name="sm" class="form-control input-radius" readonly>${kmsz.sm}</textarea>
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
	$(".bc").attr("disabled",true);
	//选择框
	 $("#btn_jfbykm1").click(function(){
  		select_commonWin("${ctx}/kmsz/window?controlId=kmsz_kmmc","科目信息","920","630");
   }); 
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/kmsz/goJjkmPage";
	});
	/* //联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	$("#btn_dwld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息","920","630");
    });
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          rybh:{validators:{notEmpty: {message: '人员工号不能为空'}}},
          bddh:{validators:{notEmpty: {message: '办公电话不能为空'},tel:{message: '请输入有效的办公电话'}}},
          moblie:{validators:{notEmpty: {message: '手机号码不能为空'},phone:{message: '请输入有效的手机号码'}}},
          email:{validators:{emailAddress:{message: '请输入有效的E-mail地址'}}}
          }
	      }); */
	//保存按钮
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          kmbh:{validators: {notEmpty: {message: '科目编号不能为空'},stringLength:{message:'编号过长',max:'20'}}},
          kmmc:{validators: {notEmpty: {message: '科目名称不能为空'}}},
          }
	      });
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/kmsz/doSave",function(val){
			if(val.success){
				alert(val.msg);
				//window.location.href = "${ctx}/kmsz/goJjkmPage";
				parent.location.href = "${ctx}/window/mainJjkmTree";
			}
		});
	});

	/* //查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}

	if("000000" == "${dwb.SJDW}"){
		$("#txt_sjdw").attr("readonly","true");
		$("#btn_sjdw").hide();
	} */
	//页面加载完后，控制实验室信息模块是否展示
	/* sysbz();
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
	}); */
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
</script>

</html>