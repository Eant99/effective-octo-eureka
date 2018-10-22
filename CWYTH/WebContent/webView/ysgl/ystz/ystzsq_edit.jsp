<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>预算调整申请</title>
<%@include file="/static/include/public-manager-css.inc"%>

</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${jsxx.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑预算调整申请信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>调整信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单据编号</span>
							<input type="text" id="txt_djbh" name="djbh" readonly class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="001"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单位名称</span>
                            <input type="text" id="txt_dwmc" readonly class="form-control input-radius" name="dwmc" value="${jsxx.xm}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目名称</span>
							<input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value="${jsxx.xm}"/>
							<span class="input-group-btn"><button type="button" id="btn_xmmc" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>负责人</span>
	                         <input type="text" id="txt_fzr" readonly class="form-control input-radius window" name="fzr" value="${jsxx.szdw}">
	                         
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>预算金额（万元）</span>
	                       <input type="text" id="txt_ysje" readonly class="form-control input-radius text-right number1" name="ysje" value="">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>调整金额（万元）</span>
							 <input type="text" id="txt_tzje" class="form-control input-radius text-right number1" name="tzje" value="${jsxx.szdw}">
						</div>
					</div>
				</div>
				<div class="row">				
                  <div class="col-md-4">
                  	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>调整人</span>
							<input type="text" id="txt_tzr" class="form-control input-radius window" name="tzr" value="${jsxx.szdw}">
                            <span class="input-group-btn"><button type="button" id="btn_tzr" class="btn btn-link btn-custom">选择</button></span>
					</div>                	
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>调整日期</span>
                           <input type="text" id="txt_tzrq" class="form-control input-radius date" name="tzrq" value="${jsxx.szdw}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>调整原因</span>
                            <textarea id="txt_tzyy" class="form-control" name="tzyy" >${gwjdfsq.bz}</textarea>
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
	var d = new Date();
	var date = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();	
	$("#txt_tzrq").val(date);
	//单位弹窗 
	$("#btn_xmmc").click(function(){
		select_commonWin("${ctx}/webView/ysgl/ystz/main.jsp","项目信息","1020","630");
    });
	$("#btn_tzr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_tzr","人员信息","920","630");
    });
	//
	$(document).on("blur", ".number1", function(e){
	$(this).Num(4,true,false,e);
	return false;
});
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          tzyy:{validators:{ notEmpty: {message: '不能为空'},stringLength:{message:'字段过长',max:'1000'}}},
          djbh:{validators: {notEmpty: {message: '不能为空'}}},
          dwmc:{validators: {notEmpty: {message: '不能为空'}}},
          xmmc:{validators: {notEmpty: {message: '不能为空'}}},
          fzr:{validators:{notEmpty:{message: '不能为空'}}},
          tzr:{validators:{notEmpty:{message: '不能为空'}}},
          ysje:{validators:{ notEmpty:{message: '不能为空'},}},
          tzje:{validators:{notEmpty:{message: '不能为空'}}},
          tzrq:{validators:{notEmpty:{message: '不能为空'}}}
         
          }
	      });
	//保存按钮
	$("#btn_save").click(function(){
		doSave(validate,"myform","${ctx}/jsxxs/doSave",function(val){
			if(val.success){
				alert(val.msg);
				parent.location.reload(true);
			}
		});
	});
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/ysgl/ystz/ystzsq_list.jsp";
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
	$("[name='zzzt']").change(function(){
		if($(this).val() == '3'){
			$("#txrq").show();
		}else{
			$("#txrq").hide();
		}
	});
	//页面加载完后，退休日期是否可编辑
	txrq();
	function txrq(){
		var $this = $("[name='zzzt']").val();
		if($this == '3'){
			$("#txrq").show();
		}else{
			$("#txrq").hide();
		}
	}
});

</script>
</body>
</html>