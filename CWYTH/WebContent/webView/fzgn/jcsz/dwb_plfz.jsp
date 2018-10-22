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
						<option value="sjdw" id="bdq_sjdw">上级单位</option>
	                    <option value="dwxz" id="bdq_dwxz">单位性质</option>
	                    <option value="dz"  id="bdq_dz">地址</option>
	                    <option value="dwld" id="bdq_dwld">单位领导</option>
	                    <option value="jlrq" id="bdq_jlrq">建立日期</option>
	                    <option value="dwzt" id="bdq_dwzt">单位状态</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div  class="col-sm-12">
				<div class="input-group" id="bdh_sjdw">
					<span class="input-group-addon">替换内容：</span>
					<input type="text" id="txt_sjdw" class="form-control input-radius"  >
					<span class="input-group-btn"><button type="button" id="btn_sjdw" class="btn btn-link btn-custom">选择</button></span>
				</div>
				<div class="input-group none" id="bdh_dwxz">
					<span class="input-group-addon">替换内容：</span>
					<select id="txt_dwxz" class="form-control input-radius">
	                    <c:forEach var="dwxzlist" items="${dwxzlist}"> <option value="${dwxzlist.DM}">${dwxzlist.MC}</option>
						</c:forEach>
	                 </select>
				</div>
				<div class="input-group none"  id="bdh_dz">
					<span class="input-group-addon">替换内容：</span>
					<input type="text" id="txt_dz" class="form-control input-radius"/>
				</div>
				<div class="input-group none" id="bdh_dwld" >
					<span class="input-group-addon">替换内容：</span>
					<input type="text" id="txt_dwld" class="form-control input-radius">
					<span class="input-group-btn"><button type="button" id="btn_dwld" class="btn btn-link btn-custom">选择</button></span>
				</div>
				<div class="input-group none" id="bdh_jlrq">
					<span class="input-group-addon">替换内容：</span>
					<input type="text" id="txt_jlrq" class="form-control date"  data-format="yyyy-MM-dd" placeholder="单位成立日期">
					<span class='input-group-addon'><i class="glyphicon glyphicon-calendar"></i></span>
				</div>
				<div class="input-group none" id="bdh_dwzt">
					<span class="input-group-addon">替换内容：</span>
					<select id="txt_dwzt" class="form-control input-radius" >
						<option value="1" >正常</option>
						<option value="0" >禁用</option>
					</select>
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
	//联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_sjdw").bindChange("${ctx}/suggest/getXx","D");
	
	//上级单位选择框
	$("#btn_sjdw").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_sjdw","单位信息","920","630");
    });
	//单位领导选择框
	$("#btn_dwld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息","920","630");
	});
	//切换数据列
	$("#bdq").change(function(){
		var select = $(this).val();
		var target = "bdh_"+select;
		$("[id^='bdh_']").addClass("none");
		$("[id='"+target+"']").removeClass("none");
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
		var zdValue = $("#txt_"+ziduan).val();
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/dwb/doPlfuzhi",
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