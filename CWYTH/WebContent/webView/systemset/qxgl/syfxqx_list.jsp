<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用方向权限设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
    <!-- MKBH模块编号 -->
	<input type="hidden" name="syfx" value="">
	<input type="hidden" name="operateType" id="operateType" value="U">
    <!-- RYBH人员编号 -->
	<input type="hidden" name="rybh" value="${rymap.RYBH}">
    <div class='page-header'>
		<div class="pull-left">
            <span class="page-title text-primary">
            	<i class='faw fa-user' style="font-size:17px;"></i>
				<span style="color: red;"><c:out value="(${rymap.RYGH})${rymap.XM}"/></span>的使用方向权限
			</span>
		</div>
		<div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save">
				<i class="faw fa-check"></i>
				保存
			</button>
        </div>
     </div>
     <div class="box" style="top:36px;margin-left: 9px;margin-right: 9px;">
         <div class="box-content">
         	<c:if test="${fn:length(syfx_list) > 0 }">
         		<c:forEach var="syfx" items="${syfx_list}">
         			<div class="row">
		             	<div class="col-md-4"></div>
		             	<div class="col-md-4">
		             		<input type="checkbox" name="che_syfx" value="${syfx.dm }" <c:if test="${syfx.cnt != '0'}"> checked </c:if> />${syfx.mc }
		             	</div>
		             </div>
         		</c:forEach>
         	</c:if>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function(){
		//页面验证
		var validate = $('#myform').bootstrapValidator({fields: {}});
		//保存按钮
		$("#btn_save").click(function(e){
			var $checkeds = $("#myform").find("[name='che_syfx']").filter(":checked");
			var dm =[];
			$checkeds.each(function() {
				dm.push($(this).val());
			});
			$("[name='syfx']").val(dm.join(","));
			doSave(validate,"myform","${ctx}/syfxqx/doSave",function(val){
				//......
			},function(val){
			});
		}); 
	});
</script>
</body>
</html>