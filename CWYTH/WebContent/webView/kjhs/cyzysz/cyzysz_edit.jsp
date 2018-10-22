<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证类型详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<style type="text/css">
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    padding-left: 6px;
}
</style>
<body>
	<form id="myform" class="form-horizontal" action="" method="post">
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<input type="hidden" id="txt_guid" name="guid" value="${guid}"> 
		<div class="page-header">
			<div class="pull-left">
				<span class="page-title text-primary"><i
					class='fa icon-xiangxixinxi'></i>编辑常用摘要信息</span>
			</div>
			<div class="pull-right">
				<button type="button" class="btn btn-default" id="btn_save">
					<i class="fa icon-save"></i>保存
				</button>
				<button type="button" class="btn btn-default" id="btn_back">返回</button>
			</div>
		</div>
		<div class="box" style="top: 36px">
			<div class="box-panel">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i>常用摘要信息
					</div>
					<div class="actions">
						<a href="#" class="btn box-collapse btn-mini btn-link"><i
							class="fa"></i></a>
					</div>
				</div>
				<hr class="hr-normal">
				<div class="container-fluid box-content">
					<div class="row">
						<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>科目编号</span>
							<input type="text" id="txt_kmbh" name="kmbh" class="form-control input-radius window" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value=${cyzy.KMBH } >
							<span class="input-group-btn"><button type="button" id="btn_kmbh" class="btn btn-link">选择</button></span>
						</div>
                    </div>
				          <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否启用</span>
							<div class="radiodiv"> &nbsp; &nbsp;
<!--                                 <div class="pull-left" style="padding-left: 10px;"> -->
                                <c:if test="${operateType == 'C' }">
									<input type="radio" name="sfqy" value="1" checked="checked"> 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" name="sfqy" value="0" > 否
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="radio" name="sfqy" value="1" <c:if test="${1 == cyzy.SFQY }">checked="checked"</c:if>> 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" name="sfqy" value="0" <c:if test="${0 == cyzy.SFQY }">checked="checked"</c:if>> 否
								</c:if>
<!-- 								</div> -->
                           </div>
						</div>
					</div>
					</div>
					<div class="row">
					  <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>摘要内容</span>
                            <textarea id="txt_zynr" class="form-control" name="zynr" >${cyzy.zynr}</textarea>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>

		</div>
		</div>
	</form>
	<%@include file="/static/include/public-manager-js.inc"%>
	<script type="text/javascript">
		$(function() {
			//必填验证
			var validate = $('#myform').bootstrapValidator({fields:{
				kmbh:{validators:{notEmpty:{message:'不能为空'}}},
				zynr:{validators:{notEmpty: {message: '不能为空'},stringLength:{message:'字数过多',max:'50'}}}
            }});
			//保存按钮
			$("#btn_save").click(function(e){
				doSave(validate,"myform","${ctx}/cyzysz/doSave?operateType="+"${operateType}",function(val){
					if(val.success){
						alert(val.msg);
						window.location.href="${ctx}/cyzysz/goListPage";
					}
				});
			});	
           //返回按钮
			$("#btn_back").click(function() {
				window.location.href="${ctx}/cyzysz/goListPage";
			});
		});
		//弹出框
		$("#btn_kmbh").click(function(){
			select_commonWin("${ctx}/cyzysz/mainKjkmszTree?controlId=txt_kmbh","会计科目信息","920","630");
	    });
	</script>
</body>
</html>