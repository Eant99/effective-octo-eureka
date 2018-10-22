<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金分配审核</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type='text/css'>
.bttn{
    background-color: #00acec;
    color: white;
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" id="txt_guid" name="guid"  value="${param.guid}">
	<input type="hidden" id="txt_czr" name="czr"  value="${loginID}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>资金分配审核</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-primary bttn" id="btn_refuse">退回</button>

			<button type="button" class="btn btn-primary bttn" id="btn_pass">通过</button>
			
			<button type="button" class="btn btn-primary bttn" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>审核详细</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">   
                 <div class="row">
                 <div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
                               <textarea id="txt_shyj"  class="form-control" name="shyj" style=" height: 142px; width: 456px;" ></textarea>
						</div>
					</div>	
                 </div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
function sh(a_shzt){
	var shyj = $("#txt_shyj").val();
	var guid = $("#txt_guid").val();
	$.ajax({
		type:"post",
		url:ADDRESS+"/zjfpsh/sh",
		data:"shzt="+a_shzt+"&shyj="+shyj+"&guid="+guid,
		success:function(data){
			var result = JSON.getJson(data);
			if(result.success){
				alert("操作成功！");
			}
		},
		error:function(){
			alert("操作失败！请稍后重试！");
		}
	})
}
$(function(){
	
	//退回按钮
	$("#btn_refuse").click(function(){
		sh("0004");
	});
	//通过按钮
	$("#btn_pass").click(function(){		
		sh("0003");
	});
	//返回按钮
	$("#btn_back").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	//刷新按钮
	$(".reload").click(function(){
		 window.location.reload();
	});

	
});

</script>
</body>
</html>