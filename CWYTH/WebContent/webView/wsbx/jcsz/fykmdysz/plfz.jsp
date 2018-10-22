<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    line-height: 25px;
    padding-left: 6px;
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid" value="${gid}" />
	<div class="box" >
		<div class="box-panel">		
		
			<div class="container-fluid box-content">
				<div class="row">
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>费用分类</span>
                            <select id="drp_fyfl" class="form-control select2" name="fyfl" table="K">
	                       		<option value="1" >日常报销</option>
									<option value="2" >差旅费</option>
									<option value="3" >预支付</option>
									<option value="4" >公务接待费</option>
	                   		</select>
						</div>
					</div>
					</div>
					<div class="row">
					<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon"><span class="required">*</span>上级分类</span>
				<input type="text" id="txt_sjfyfl" class="form-control input-radius window" name="sjfyfl" value="${fykmdysz.sjfyfl}">
				 <input type="hidden" name='sjfl' id="txt_sjfl" readonly value="${fykmdysz.sjfl}">
				<span class="input-group-btn"><button type="button" id="btn_sjfl" class="btn btn-link btn-custom">选择</button></span>
				    </div>
					</div>
					</div>
					<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>借贷方向</span>
                            <div class="radiodiv">
                             <input type="radio" name="jdfx" value="0" checked <c:if test="${operateType == 'C' }">checked</c:if>> 贷方&ensp;
							<input type="radio" name="jdfx" value="1" <c:if test="${fykmdysz.jdfx=='1'}">checked</c:if>> 借方
                            </div>
                             </div>
                           
						</div>
					</div>				
				
	               <div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目编号</span>
                            <input type="text" id="txt_kmbh" class="form-control input-radius window" name="kmbh" value="${fykmdysz.kmbh}">
				<span class="input-group-btn"><button type="button" id="btn_kmbh" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>				
				</div>
				
				 <div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目名称</span>
                       <input type="text" id="txt_kmmc" class="form-control input-radius" name="kmmc" value="${fykmdysz.kmmc}"/>
						</div>
					</div>				
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="pull-center" style="text-align:center;">
            				<button type="button" class="btn btn-default" id="btn_save" style="background:#00acec;color: white;">确定</button>
            				<button type="button" class="btn btn-default" id="btn_fh" style="background:#00acec;color: white;">返回</button>
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
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"myform","${ctx}/fykmdysz/doplSave?guid=${param.guid}",function(val){
			if(val.success){
				alert(val.msg);
				var pname = "${param.pname}";
				var win = getIframWindow(pname);
				var winId = getTopFrame().layer.getFrameIndex(window.name);
				getIframWindow("${param.pname}").table.ajax.reload();
		    	close(winId);
			}
		});
	});	
	$("#btn_fh").click(function(){
		close(winId);
	});
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
		 fymc:{validators: {stringLength:{message:'费用名称过长',max:'100'}}},
		 sjfyfl:{validators: {notEmpty:{message:'不能为空'},stringLength:{message:'上级分类过长',max:'32'}}},
		 bz:{validators: {stringLength:{message:'备注过长',max:'500'}}}
          }
	      });
	//科目编号弹框
	 $("#btn_kmbh").click(function(){
  		select_commonWin("${ctx}/kmsz/window?controlId=txt_kmmc&txt_kmbh=txt_kmbh","科目信息","920","630");
   }); 
	//上级分类弹窗
	 $("#btn_sjfl").click(function(){
			select_commonWin("${ctx}/fykmdysz/window?controlId=txt_sjfyfl&text_id=txt_sjfl","费用上级分类","920","630");
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

</html>