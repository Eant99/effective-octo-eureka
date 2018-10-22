<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>人员信息批量赋值</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
#th_drrq,#th_zyzc,#th_dwbh {
	display: none;
}
</style>
</head>
<body style="background-color: white;">
	<form id="ryxx_form" class="form-horizontal" action="" method="post">
		<div class="container-fluid dialog-body">
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">数&ensp;据&ensp;列：</span> <select
							id="selxx" name="selxx" class="form-control" style="width: 100%">
							<option value="whcd" bj="sel">文化程度</option>
							<option value="drrq" bj="sel">调入日期</option>
							<option value="zyzc" bj="sel">专业职称</option>
							<option value="dwbh" bj="sel">所在单位</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">替换内容：</span>
						<!--文化程度  -->
						<span id="th_whcd"> <select
							class="form-control input-radius" id="sel_whcd">
								<c:forEach var="whcdList" items="${whcdList}">
									<option value="${whcdList.DM}"
										<c:if test="${ryb.whcd == whcdList.DM}">selected</c:if>>${whcdList.MC}</option>
								</c:forEach>
						</select>
						</span>
						<!--调入日期  -->
						<span id="th_drrq"> <input type="text"
							class="form-control date" style="width: 91%" id="sel_drrq"
							name="sel_drrq" value="" data-format="yyyy-MM-dd"
							placeholder="调入日期"> <span class='input-group-addon'
							style="padding: 5px;"> <i
								class="glyphicon glyphicon-calendar"></i>
						</span>
						</span>
						<!--专业职称 -->
						<span id="th_zyzc"> 
						<select class="form-control" id="sel_zyzc">
								<c:forEach var="zyzcList" items="${zyzcList}">
									<option value="${zyzcList.DM}"
										<c:if test="${ryb.zyzc == zyzcList.DM}">selected</c:if>>${zyzcList.MC}</option>
								</c:forEach>
						</select>
						</span>
						<!--所在单位 -->
						<span id="th_dwbh"> <input type="text"
							class="form-control input-radius" id="sel_dwbh" name="dwbh"
							value="" style="width: 83%"> <span
							class="input-group-btn"><button type="button"
									id="btn_dwbh" class="btn btn-link btn-custom">选择</button></span>
						</span>
					</div>
				</div>
			</div>
		</div>
		<div class='page-bottom clearfix'>
			<!-- 保存和返回按钮 开始 -->
			<div class="pull-right">
				<button type='button' class="btn btn-default" id="btn_save">
					<i class="fa icon-save"></i> 保存
				</button>
				<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
					取消</button>
			</div>
			<!-- 保存和返回按钮 结束 -->
		</div>
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//联想输入提示
	$("#sel_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//隐藏与显示
	$("#selxx").change(function(){
		var select = $(this).val();
		var target = "th_"+select;
		$("#th_whcd,#th_drrq,#th_zyzc,#th_dwbh").hide();
		$("[id='"+target+"']").show();
	});
	//弹窗开始
	$("#btn_dwbh").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=sel_dwbh","单位信息","920","630");
	});
	//取消
	$("#btn_cancelw").click(function(){
		/* getIframWindow("${param.pname}").table.ajax.reload(); */
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	//确定按钮
	$("#btn_save").click(function(){
		//目标字段
		var ids = "${rybh}";
		var ziduan = $("#selxx").val();
		//更改内容
		var target = "sel_"+ziduan;
		var zdValue = $("[id='"+target+"']").val();
		doSave(validate, "ryxx_form","${ctx}/ryb/doPlfz?ids="+ids+"&ziduan="+ziduan+"&zdValue="+zdValue, function(val){
	    	alert(val.msg);
	    	var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		}, function(val){
			
		});
		getIframWindow("${param.pname}").table.ajax.reload();
	});
	//验证方法
	var validate = $("#ryxx_form").bootstrapValidator({
		fields:{dwbh:{validators: {notEmpty: {message: '所属单位不能为空'}}}}
	});
});
</script>
</body>
</html>