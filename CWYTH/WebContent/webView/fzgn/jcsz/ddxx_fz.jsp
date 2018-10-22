<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>地点信息批量赋值</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
	<form id="ddxx_form" class="form-horizontal" action="" method="post">
		<div class="container-fluid dialog-body">
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">数&ensp;据&ensp;列：</span> 
						<select id="selxx" name="selxx" class="form-control" style="width: 100%"  onchange="nrChange()">
							<option value="mc" bj="sel">地点名称</option>
							<option value="dwbh" bj="sel">所属单位</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">替换内容：</span>
						<span id="th_mc" >
						    <input type = "text" class="form-control input-radius"  id="sel_mc" name="mc">
						</span>
						<span id="th_dwbh" class = "hide"> 
						    <input type="text"  class="form-control input-radius" id="sel_dwbh" name="dwbh" value="" style="width: 83%"> 
						    <span class="input-group-btn">
						        <button type="button" id="btn_dwbh" class="btn btn-link btn-custom">选择
						        </button>
						    </span>
						</span>
					</div>
				</div>
			</div>
		</div>
		<div class='page-bottom clearfix'>
			<div class="pull-right">
				<button type='button' class="btn btn-default" id="btn_save">
					<i class="fa icon-save"></i> 保存
				</button>
				<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
					取消
			    </button>
			</div>
		</div>
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//联想输入提示
	$("#sel_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//弹窗开始
	$("#btn_dwbh").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=sel_dwbh","单位信息","920","630");
	});
	//取消
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	//验证方法
	var validate = $("#ddxx_form").bootstrapValidator({
		fields:{mc:{validators: {notEmpty: {message: '地点名称不能为空'}}}}
	});
	//确定按钮
	$("#btn_save").click(function(){
		//目标字段
		var ids = "${ddbh}";
		var ziduan = $("#selxx").val();
		//更改内容
		var target = "sel_"+ziduan;
		var zdValue = $("[id='"+target+"']").val();
		doSave(validate, "ddxx_form","${ctx}/ddb/doplFuzhi?ddbh="+ids+"&ziduan="+ziduan+"&zdValue="+zdValue, function(val){
			alert(val.msg);
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		}, function(val){
		});
	});
});
function nrChange(){
	var ss=$("[name='selxx']").val();
	if (ss=="mc"){
		$("#th_mc").removeClass("hide");
		$("#th_dwbh").addClass("hide");
	} else{
		$("#th_mc").addClass("hide");
		$("#th_dwbh").removeClass("hide");
	}
}
</script>
</body>
</html>