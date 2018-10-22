<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>使用年限设置-批量赋值</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="form" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="bzdm" id="bzdm" value="${bzdm}">
	<div class="container-fluid dialog-body">
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
					<div class="input-group">
						<span class="input-group-addon">选择内容</span>
<!-- 						<select id="drp_xznr" class="form-control input-radius" name="xznr" onchange="nrChange()"> -->
<!-- 						<option value="synx" >预计使用年限</option> -->
<!--                    		</select> -->
                       <input type="text"  class="form-control input-radius" readonly  value="预计使用年限">
					</div>
					</div>
				</div>
				<div class="row" id="qtid" >
					<div class="col-sm-6 col-sm-offset-3">
					<div class="input-group">
						<span class="input-group-addon">请填写内容</span>
						<input type="text" id="txt_content" class="form-control input-radius text-right number" name="content" value="">
					</div>
					</div>
				</div>					
		</div>
		<div class='page-bottom clearfix'>
	     <div class="pull-right">
			<button class="btn btn-default" id="btn_save" type="button" >
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
<script type="text/javascript">
$(function(){
	var pname = "${param.pname}";
	//取消
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	//保存按钮
	$("#btn_save").click(function(e){
		var pname = "${param.pname}";
//  		var xznr = $("#drp_xznr").val();
		var bzdm = $("#bzdm").val();
		var czflbh = "${czflbh}"; 
		var content="";
		content = $("#txt_content").val();
	        $.ajax({  
		       	type:"post",
		       	data:"xznr=synx&bzdm="+bzdm+"&content="+content+"&czflbh="+czflbh+"&flh=${flh}",
		       	url:"${pageContext.request.contextPath}/synxsz/doUpd",
		       	dataType:"json",
		       	success:function(val){
		       		if(val.success == 'true'){
		       		alert(val.msg);
		         	var winId = getTopFrame().layer.getFrameIndex(window.name);
		       		var pWindow = getIframWindow(pname);
		       		pWindow.location.href = pWindow.location.href;
		       		getTopFrame().close(winId); 
		       		}
		       	},
		       	error:function(){
		       		alert("数据请求错误！");
		       	}
	        }); 
	}); 
});
</script>
</body>
</html>