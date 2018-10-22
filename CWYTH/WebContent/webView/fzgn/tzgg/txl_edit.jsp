<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>通讯录</title>
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
            		<c:when test="${operateType == 'L'}">查看通讯录信息</c:when>
            		<c:otherwise>编辑通讯录信息</c:otherwise>
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
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>通讯录</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>人员编号</span>
							<input type="text" id="txt_dwld" class="form-control input-radius window" name="rybh" value="${txl.ryb}">
							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"><button type="button" id="btn_dwld" class="btn btn-link btn-custom">选择</button></c:if></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">办公地点</span>
							<input type="text" id="txt_bgdd" class="form-control input-radius" name="bgdd" value="${txl.bgdd}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" >职&emsp;&emsp;务</span>
							<input type="text" id="txt_zw" class="form-control input-radius" name="zw" value="${txl.zw}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>办公电话</span>
							<input type="text" id="txt_bddh" class="form-control input-radius" name="bddh" value="${txl.bddh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>手机号码</span>
							<input type="text" id="txt_moblie" class="form-control input-radius" name="moblie" value="${txl.moblie}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">QQ</span>
							<input type="text" id="txt_qq" class="form-control input-radius" name="qq"  value="${txl.qq}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">Email</span>
							<input type="text" id="txt_email" class="form-control input-radius" name="email"  value="${txl.email}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">排序序号</span>
							<input type="text" id="txt_pxxh" class="form-control input-radius text-right int" name="pxxh"  value="${txl.pxxh}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">状&emsp;&emsp;态</span>
							<select id="drp_dwzt" class="form-control input-radius select2" name="zt">
								<option value="1" <c:if test="${txl.ZT == 1}">selected</c:if>>正常</option>
								<option value="0" <c:if test="${txl.ZT == 0}">selected</c:if>>禁用</option>
							</select>
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
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/txl/goTxlPage";
	});
	//联想输入
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
	      });
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"myform","${ctx}/txl/doSave",function(val){
		},function(val){
			//$("#txt_dwld").focus();
		});
		
	});	
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}

	if("000000" == "${dwb.SJDW}"){
		$("#txt_sjdw").attr("readonly","true");
		$("#btn_sjdw").hide();
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