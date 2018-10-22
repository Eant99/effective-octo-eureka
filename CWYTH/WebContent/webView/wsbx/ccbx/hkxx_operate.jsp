<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>添加收款信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
/* 	.col-md-4{ */
/* 		width:33.33%!important; */
/* 		float:left!important; */
/* 	} */
.col-md-4 .input-radius{
	width:60%!important;
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	添加收款信息
			</span>
		</div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>收款信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content" style="">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>收款单位</span>
							<input type="text" id="txt_skdw" class="form-control input-radius" name="skdw"  value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>汇款金额</span>
							<input type="text" id="txt_hkje" class="form-control input-radius number" name="hkje" value=""
								onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
								（汇款金额必须大于0）
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">用途附言</span>
							<input type="text" id="txt_ytfy" class="form-control input-radius" name="ytfy" value="" placeholder=""/>
							（限20字以内）
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>收款账号</span>
							<input type="text" id="txt_skzh" class="form-control input-radius" name="skzh" value="" placeholder=""/>
							（限输数字，不含特殊字符）
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>银行名称</span>
							<select name="yhmc" class="form-control input-radius select2">
								<option value="中国农业银行">中国农业银行</option>
							</select>
							（如果找不到银行名称，请选择"所有银行"）
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">网点省市</span>
							<select name="wdss" class="form-control input-radius select2">
								<option value="山东济南市">山东济南市</option>
							</select>
							（具体银行网点所在的省市）
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">网点关键字</span>
							<input type="text" id="" class="form-control input-radius" name="" value="" placeholder="请输入关键字"/>
							&nbsp;&nbsp;
							<button type="button" class="btn btn-default" id="" style="background:#00acec;color: white;">查询</button>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">网点名称</span>
							<select name="wdmc" class="form-control input-radius select2">
								<option value="">请选择...</option>
								<option value="山东济南市高新区分行">山东济南市高新区分行</option>
							</select>
							（如果找不到网点，请选择相应的省市分行营业部）
						</div>
					</div>
					</div>
			</div>
			<div class="container-fluid box-content">
			<div class="row">
			<div class="pull-center" style="text-align:center;">
	    		<button type="button" class="btn btn-default" id="btn_save" style="background:#00acec;color: white;">保存</button>
	    		<button type="button" class="btn btn-default" id="btn_fh" style="background:#00acec;color: white;">返回</button>
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
	//必填验证
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	var validate = $('#myform').bootstrapValidator({fields:{
        	    skdw:{validators:{notEmpty:{message:'不能为空'}}},
        	    hkje:{validators:{notEmpty: {message: '不能为空'}}},
        	    skzh:{validators:{notEmpty: {message: '不能为空'},regexp: {regexp: /^[0-9]\d*$/,message: '限输数字，不含特殊字符'}}},
        	    yhmc:{validators:{notEmpty: {message: '不能为空'}}},
        	    ytfy:{validators:{stringLength:{message:'限20字以内',max:'20'}}}
         }});
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"myform","${ctx}/dwb1/doSave1",function(val){
			
		});
	});	
	$("#btn_fh").click(function(e){
		close(winId);		
	});
	function doSave(_validate, _formId, _url, _success, _fail){
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
					var arry = $('#' + _formId).serializeArray();
					getIframWindow("${param.pname}").addHkInfo(arry);//
		        	close(winId);				
				}	
			});
		}
	}
});
</script>

</html>