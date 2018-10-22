<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支付方式详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${zffssz.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑支付方式信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>支付方式信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>支付编码</span>
							<input type="text" id="txt_zfbm" name="zfbm" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${yhzhgl.yhdm}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>支付名称</span>
                           <select id="drp_zfmc" class="form-control input-radius select2" name="zfmc">
	                                <c:forEach var="zfmclist" items="${cgjglist}"> 
	                                   <option value="${zfmclist.DM}" <c:if test="${cgmlsz.zfmc == zfmclist.DM}">selected</c:if>>${zfmclist.MC}</option>
									</c:forEach>
	                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">启用状态</span>
							<select id="drp_qyzt" class="form-control input-radius select2" name="qyzt">
									<option value="1" <c:if test="${dmb.dmsx == '1'}">selected</c:if>>启用</option>
									<option value="2" <c:if test="${dmb.dmsx == '2'}">selected</c:if>>禁用</option>
								</select>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon">科目编号</span>
				 <input type="text" id="txt_kmbh" class="form-control input-radius window" name="kmbh" value="${zffssz.KMBH}">
				<span class="input-group-btn"><button type="button" id="btn_kmbh" class="btn btn-link btn-custom">选择</button></span>
				</div>
				</div>
				
				<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon">科目名称</span>
				 <input type="text" id="txt_kmmc" class="form-control input-radius window" name="kmmc" value="${zffssz.KMMC}">
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

	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{       
          zfbm:{validators: {notEmpty: {message: '支付编码不能为空'}}},
          zfmc:{validators: {notEmpty: {message: '支付名称不能为空'}}}      
          }
	      });
	//保存按钮
	$("#btn_save").click(function(){
		doSave1(validate,"myform","#",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}else{
				alert("保存成功！");
			}
		});
	});
	//科目名称弹框
	$("#btn_kmbh").click(function(){
		select_commonWin("${ctx}/pzmb/window?controlId=txt_kmbh","科目信息","920","630");
    });
	function doSave1(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){					
				},
				error:function(val){
					alert("保存成功！");					
				}	
			});
		}
	}
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/kjhs/zffssz/zffssz_list.jsp";
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
});

</script>
</body>
</html>