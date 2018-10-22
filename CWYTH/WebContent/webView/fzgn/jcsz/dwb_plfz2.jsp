<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位信息批量赋值页面</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.none {
		display: none;
	}
	
	.inline {
		display: inline;
	}
</style>
</head>
<body  style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="container-fluid dialog-body">
		<div class="row">
			<div  class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon">数&ensp;据&ensp;列：</span>
					<input type="hidden" id="ids" name ="ids" value="${ids}">
					<select id="bdq" class="form-control input-radius" name="bdq">
						<c:forEach var="item" items="${czfslist}"> 
						   <option value="${item.xmjc}">${item.xmmc}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div  class="col-sm-12">
				<div class="input-group" id="bdh_sjdw">
					<span class="input-group-addon">替换内容：</span>
					<input type="text" id="txt_thnr" class="form-control input-radius" style="text-align: right;">
<!-- 					<span class="input-group-btn"><button type="button" id="btn_sjdw" class="btn btn-link btn-custom">选择</button></span> -->
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
	     <div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save" >
			 <i class="fa icon-save"></i>
					保存
			</button>
			<button type="button" class="btn btn-default btn-without-icon" id="btn_cancelw">
					取消
			</button>
	      </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	
	//单位领导选择框
	$("#btn_dwld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息","920","630");
	});
	//取消按钮
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	//保存按钮
	$("#btn_save").click(function(e){
		//目标字段
		var ids = "${ids}";
		var ziduan = $("#bdq").val();
		//更改内容
		var zdValue = $("#txt_thnr").val();
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/xzgl/doPlfuzhi?rybh=${rybh}&shzt=${shzt}&gzyf=${gzyf}&xm=${xm}&bm=${bm}",
				data:"ids="+ids+"&ziduan="+ziduan+"&zdValue="+zdValue,
				success:function(val){
					var data = JSON.getJson(val);
					close(index);
					if(data.success){
						alert(data.msg);
						getIframWindow("${param.pname}").table.ajax.reload();
						var winId = getTopFrame().layer.getFrameIndex(window.name);
				    	close(winId);
					}else{
						alert(data.msg);
					}
				},
				error:function(val){
					alert("请稍后再试！");
				},
				beforeSend:function(val){
					index = loading(2);
				}
			});
	});
});
</script>
</body>
</html>