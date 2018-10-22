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
					class='fa icon-xiangxixinxi'></i>编辑凭证类型信息</span>
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
						<i class="fa icon-xiangxi"></i>凭证类型信息
					</div>
					<div class="actions">
						<a href="#" class="btn box-collapse btn-mini btn-link"><i
							class="fa"></i></a>
					</div>
				</div>
				<hr class="hr-normal">
				<div class="container-fluid box-content">
					<div class="row">
				        <div class="col-md-6">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>凭证类型编号</span>
								 <c:if test="${operateType == 'C' }">
									<input type="text" id="txt_pzlxbh" name="pzlxbh"
										class="form-control input-radius"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"/>
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="text" id="txt_pzlxbh" name="pzlxbh"
										class="form-control input-radius"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"
										value="${pzlx.PZLXBH}" />
								</c:if>
							</div>
						</div>
					    <div class="col-md-6">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>凭证类型名称</span>
								   <c:if test="${operateType == 'C' }">
											<input type="text" id="txt_pzlxmc" name="pzlxmc"
											class="form-control input-radius"
											style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"/>
									</c:if>
									<c:if test="${ operateType == 'U' }">
										<input type="text" id="txt_pzlxmc" name="pzlxmc"
											class="form-control input-radius"
											style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"
											value="${pzlx.PZLXMC}" />
									</c:if>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>凭证包含</span>
							<div class="radiodiv"> &nbsp; &nbsp;
                                <c:if test="${operateType == 'C' }">
									<input type="checkbox" name="pzbh" value="10" > 资产类 &ensp;&ensp;
									<input type="checkbox" name="pzbh" value="20" > 负债类&ensp;&ensp;
									<input type="checkbox" name="pzbh" value="30" > 净资产类&ensp;&ensp;
									<input type="checkbox" name="pzbh" value="40" > 收入类&ensp;&ensp;
									<input type="checkbox" name="pzbh" value="50" > 支出类
								</c:if>
								<c:if test="${ operateType == 'U' }">
									<input type="checkbox" name="pzbh" value="10" <c:if test="${'10' ==pzbhs }">checked="checked"</c:if>>资产类 &ensp;&ensp;
									<input type="checkbox" name="pzbh" value="20" <c:if test="${'20' == pzbhs }">checked="checked"</c:if>> 负债类&ensp;&ensp;
									<input type="checkbox" name="pzbh" value="30" <c:if test="${'30' == pzbhs }">checked="checked"</c:if>> 净资产类&ensp;&ensp;
									<input type="checkbox" name="pzbh" value="40" <c:if test="${'40' == pzbhs }">checked="checked"</c:if>> 收入类&ensp;&ensp;
									<input type="checkbox" name="pzbh" value="50" <c:if test="${'50' == pzbhs }">checked="checked"</c:if>> 支出类
								</c:if>
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
				pzlxbh:{validators:{notEmpty: {message: '不能为空'}}},
				pzbh:{validators:{notEmpty: {message: '不能为空'}}},
				pzlxmc:{validators:{notEmpty: {message: '不能为空'}}}
            }});
			//保存按钮
			$("#btn_save").click(function(e){
				var checkbox = $("#myform").find("[name='pzbh']").filter(":checked");
			 	if(checkbox.length>0){
		   			var pzbhs = [];
			   	   	checkbox.each(function(){
			   	   		pzbhs.push($(this).val());
			   	   	});
				doSave(validate,"myform","${ctx}/pzlxNew/doSave?operateType="+"${operateType}&pzbhs="+pzbhs,function(val){
					if(val.success){
						alert(val.msg);
						window.location.href="${ctx}/pzlxNew/goListPage";
					}
				});
			 	}else{
			 		alert("请选择凭证包含");
			 	}
			});	
           //返回按钮
			$("#btn_back").click(function() {
				window.location.href="${ctx}/pzlxNew/goListPage";
			});
          //复选框 
           var a="${pzbhs}";
           var checkArray = a.split(",");
           var checkBoxAll = $("input[name='pzbh']");  
           for(var i=0;i<"${length}";i++){
        	 //获取所有复选框对象的value属性，然后，用checkArray[i]和他们匹配，如果有，则说明他应被选中
        	 $.each(checkBoxAll,function(j,checkbox){
	        	 //获取复选框的value属性
	        	 var checkValue=$(checkbox).val();
	        	
	        	 if(checkArray[i]==checkValue){
	
	        	 $(checkbox).attr("checked",true);
	
	        	 }

        	 })

        	 }
		});
	</script>
</body>
</html>