<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>功能菜单维护</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action=""  >
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>模块编号</span>
					<input type="text"  class="form-control input-radius" name="mkbh" readonly value="${mkbh}"/>
					<input type="hidden"  name="operateType" readonly value="${operateType}"/>
				</div>
			</div>
			</div>
			<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>模块名称</span>
					<input type="text"  class="form-control input-radius" name="mkmc"  value="${map.mkmc}"/>
				</div>
				
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon">路径</span>
					<input type="text"  class="form-control input-radius" name="url"  value="${map.url}"/>
				</div>
				
			</div>
			</div>
			<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>同级序号</span>
					<input type="text"  class="form-control input-radius int"  name="xh"  value="${map.xh}"/>
				</div>
				
			</div>
		</div>
			<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon">系统菜单样式</span>
					<input type="text"  class="form-control input-radius" name="icon"  value="${map.icon}"/>
				</div>
				
			</div>
		</div>
			<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<span class="input-group-addon">系统标志</span>
					<input type="text" class="form-control input-radius" name="xtbz"  value="${map.xtbz}"/>
				</div>
				
			</div>
		</div>
			<div class="row">
				<div class="col-md-6">
					<div class="input-group">
						<span class="input-group-addon">是否启用</span>
						<input type="hidden" name="qxbz" id="qxbz"  value="${map.qxbz}">
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox"  id="btn_qxbz" class="onoffswitch-checkbox" <c:if test="${map.qxbz == '1' }">checked</c:if> value="1"  /> 
									<label class="onoffswitch-label" for="btn_qxbz"> 
									<span class="onoffswitch-inner"></span> 
									<span class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<div class='page-bottom clearfix'>
        <div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save" style="display">
				<i class="fa icon-save"></i>
				保存
			</button>
			<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
				取消
			</button>
        </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script >
$(function(){
	//取消
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});	
	
	var validate = $('#myform').bootstrapValidator({fields:{
	    mkmc:{validators: {notEmpty: {message: '模块名称不能为空'}}},
		xh:{validators: {notEmpty: {message: '同级序号不能为空'}}}
	    }
	    });
	//onoff按扭切换
	$("#btn_qxbz").click(function(){
		if($("[name='qxbz']").val()=='0'){
			$("[name='qxbz']").val("1");
		}else if($("[name='qxbz']").val()=='1'){
			$("[name='qxbz']").val("0");
		}else{
			$("[name='qxbz']").val("1");
		}
	});
	//保存
	$("#btn_save").click(function(){
		var valid;
		if(validate){
			validate.bootstrapValidator("validate");
			valid = $("#myform").data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
		var json = $("#myform").serializeObject("mkbh","xtbz");  //json对象				
		var jsonStr = JSON.stringify(json);  //json字符串
		var qxbz = $("[name='qxbz']").val();
		console.log(qxbz)
			$.ajax({
				   url:"${ctx}/cdgl/doSave?qxbz="+qxbz,
		   			type:"post",
		   			data:"json="+jsonStr,
		   			success:function(data){
					alert("保存成功！"); 
					getIframWindow("${param.pname}").table.ajax.reload();
					var winId = getTopFrame().layer.getFrameIndex(window.name);
			    	close(winId);
		   			}
			});
		}
	});
});
</script>
</body>
</html>