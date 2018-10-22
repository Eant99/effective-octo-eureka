<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>帮助信息设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
<style type="text/css">
	
	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    padding-left: 6px;
}
</style>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" id="guid"	name="guid" value="${param.guid}">
    <div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>取消复核意见</span>
					<div id="editor">
						<textarea class="zysx form-control" style="width:99%;height:160px;" name="fhyj" id="content"></textarea>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save" style="background:#00acec;color: white;">确定</button>
	    	<button type="reset" class="btn btn-default" id="btn_reset" style="background:#00acec;color: white;">取消</button>
		 </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var target = "${ctx}/kjhs/pzxx/pzfh";
var winId = getTopFrame().layer.getFrameIndex(window.name);
console.log(winId);
	$(function(){

		//保存
		$("#btn_save").click(function(){
			var guid = $("[name='guid']").val();
			var fhyj = $("[name='fhyj']").val();
			var pzzt = $("[name='pzzt']").val();
			if(fhyj != ""){
				$.ajax({
					type:"post",
					url:target+"/qxfh",
					//dataType:"json",
					data:"guid="+guid+"&fhyj="+fhyj,
					success:function(data){
						if(data){
							alert("取消复核成功！");
							getIframWindow("${param.pname}").toUrl();
			  				close(winId);
						}else{
							alert("操作未成功执行！");
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
	                    alert("不可重复执行！");
	                },
				});
			}else if(fhyj == ""){
				alert("复核意见不能为空！！");
			}
	});
// 		editor();
		//查看页面时处理函数
		if($("[name='operateType']").val()=='L'){
			$("input,select,textarea").attr("readonly","true");
			$("select").attr("disabled","true");
			$(".onoffswitch-checkbox").attr("id","true");
		}
		var sfxs = "${bzxx.SFXS}";
		if(sfxs==''||sfxs==undefined){
			$("[name='sfxs']").val("1");
		}
		//onoff按扭切换
		$("#btn_nnoff").click(function(){
			if($("[name='xm']").val()=='0'){
				$("[name='xm']").val("1");
			}else if($("[name='xm']").val()=='1'){
				$("[name='xm']").val("0");
			}else{
				$("[name='xm']").val("1");
			}
		});
		//取消
		$("#btn_reset").click(function(){
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});


		//页面验证
		var validate = {fields: {
			fhyj: {validators: {notEmpty: {message: '复核意见不能为空'}}}
			}
	    };
		
		

	});
</script>
</body>
</html>