<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>设备维修参数设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.onoffswitch-inner::before {
	    content: "快速导航" ;
	    padding-left: 10px;
	    width: 100px !important;
	}
	.onoffswitch-inner::after {
    	content: "单位树";
	}
	.onoffswitch-switch {
    	width: 50%;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="">
    <div class="page-header">
		<div class="pull-left">
			<span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>管理员建账设置</span>
		</div>
	</div>
    <div class="box" style="margin-top: 17px;margin-right: 15px;">
		<div class="row">
			<div class="col-md-8" style="margin-top: 8px;">
				<div class="input-group">
					<span class="input-group-addon">管理员建账设置</span>
					<input type="hidden" name="ksdh" value="${xtb.KSDH}">
                    <div class="switch">
                        <div class="onoffswitch" style="width: 200px;">
                            <input type="checkbox" <c:if test="${xtb.KSDH == '1'}">checked</c:if> class="onoffswitch-checkbox" id="btn_onoff">
                            <label class="onoffswitch-label" for="btn_onoff">
                                <span class="onoffswitch-inner"></span>
                                <span class="onoffswitch-switch"></span>             
                            </label>
                        </div>
                    </div>
				</div>
			</div>
		</div>
		<div class="row">
		    <div class="col-md-2 col-md-offset-1">  
         	    <button type="button" class="btn btn-default" id="btn_submit">
         		确 定
         	    </button>
         	</div>
        </div>
        <div class="row" style="height: 20px;"></div>
    </div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var ksdh = "${xtb.KSDH}";
	if(ksdh==''||ksdh==undefined){
		$("[name='ksdh']").val("0");
	}else if(ksdh=='1'){
		
	}else{
		$(".onoffswitch-switch").css("border-style","none");
		$(".onoffswitch-switch").css("width","0px");
		$(".onoffswitch-inner::after").css("width","100px");
		$(".onoffswitch-inner::after").css("border-right","1px");
		$("[name='ksdh']").val("0");
	}
	//onoff按扭切换
	$("#btn_onoff").click(function(){
		if($("[name='ksdh']").val()=='0'){
			$("[name='ksdh']").val("1");
			$(".onoffswitch-switch").css("border-style","");
			$(".onoffswitch-switch").css("width","50%");
		}else if($("[name='ksdh']").val()=='1'){
			$(".onoffswitch-switch").css("border-style","none");
			$(".onoffswitch-switch").css("width","0px");
			$(".onoffswitch-inner::after").css("width","100px");
			$(".onoffswitch-inner::after").css("border-right","1px");
			$("[name='ksdh']").val("0");
		}else{
			$("[name='ksdh']").val("0");
		}
	});
	$(document).on("click","#btn_submit",function(){
		var ksdh = $("[name='ksdh']").val();
		$.ajax({
			type:"post",
			data:"ksdh="+ksdh,
			url:"${pageContext.request.contextPath}/glyjzsz/saveXx",
			dataType:"json",
			success:function(val){
				close(index);
				if(val.success == 'true'){
					alert(val.msg);
				}else if(val.success == "false"){
					alert(val.msg);
				}else{
					alert("返回参数异常！");
				}
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading(2);
			}
		});
   	});
});
</script>
</body>
</html>