<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>采购目录设置信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${cgmlsz.GUID}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑采购目录信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>目录信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目录代码</span>
							<input type="text" id="txt_mldm" name="mldm" class="form-control input-radius"  value="${cgmlsz.MLDM}"/>
							
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目录名称</span>
                            <input type="text" id="txt_mlmc" class="form-control input-radius" name="mlmc" value="${cgmlsz.MLMC}"/>
						</div>
					</div>		
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上级目录</span>
                            <input type="text" id="txt_sjml" class="form-control input-radius" name="sjml" value="${cgmlsz.SJML}" />
                            <span class="input-group-btn"><button type="button" id="btn_sjml" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>		
				</div>		
			<div class="row">
					
					<div class="col-sm-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                           <textarea id="txt_bz" class="form-control" name="bz" >${cgmlsz.BZ}</textarea>
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
	var operate = "${operateType}";
	var operate_src = ADDRESS+"/xmgl/jcsz/cgmlsz/doInsertflow";
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{   
          mldm:{validators: {notEmpty: {message: '不能为空'},stringLength:{message:'目录代码过长',max:'35'}}},
          mlmc:{validators: {notEmpty: {message: '不能为空'},stringLength:{message:'目录名称过长',max:'50'}}},
          sjml:{validators: {stringLength:{message:'上级目录过长',max:'50'}}},
          bz:{validators: {stringLength:{message:'备注过长',max:'500'}}}
          }
	      });
	//保存按钮
	$("#btn_save").click(function(){
// 		var sjml = $("#txt_sjml").val();
// 		if(sjml.indexOf(")")){
// 			sjml = sjml.substring(1,sjml.indexOf(")"));
// 		}
// 		$("[name='sjml']").val(sjml);
		if("U"==operate){
			operate_src = ADDRESS+"/xmgl/jcsz/cgmlsz/doUpdate";
		}
		doSaveEsb(validate,"myform",operate_src,function(val){
			parent.location.reload();
		});
	});
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/xmgl/jcsz/cgmlsz/cgmlsz_list.jsp";
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