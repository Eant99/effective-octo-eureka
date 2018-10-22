<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid" value="${gid}" />
	
	<div class="box">
		<div class="box-panel">		
		
			<div class="container-fluid box-content">
			<div class="row">
			<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
                                  <select id="drp_shyj" class="form-control input-radius " name="shyj">
                                   <option value="">--未选择--</option>
	                                <c:forEach var="shyjList" items="${shyjList}"> 	                                 
	                                   <option value="${shyjList.DM}">${shyjList.MC}</option>
									</c:forEach>
	                            </select>
						</div>
					</div>
					</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>审核意见</span>
							<textarea style="width:390px;height:216px;" id="txt_yj" name="shyjj" class="form-control input-radius">同意</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="pull-center" style="text-align:center;">
            				<button type="button" class="btn btn-default" id="btn_pass">通过</button>
		                    <button type="button" class="btn btn-default" id="btn_refuse">退回</button>
            				<button type="button" class="btn btn-default" id="btn_fh" style="background:#00acec;color: white;">返回</button>
        				</div>
					</div>
				</div>
			</div>
		</div>
		</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	var validate = $('#myform').bootstrapValidator({fields:{
		shyjj:{validators:{notEmpty:{message:'不能为空'}}}
	    
         }
	      });
	$("#btn_fh").click(function(){

		getIframWindow("${param.pname}").location.href="${ctx}/gwjdfsh/goEditPage?operateType=U;"
		close(winId);
	});
	$("#btn_pass").click(function(){
		var valid;
		if(validate){
			validate.bootstrapValidator("validate");
			valid = $('#myform').data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
	   			url:"${ctx}/gwjdfsh/pass",
	   			data:"guid="+"${guid}&shyj="+$("#txt_yj").val(),
	   			type:"post",
	   			async:"false",
	   			success:function(val){
	   				alert("审核成功，信息已通过！");
	   				parent.getIframWindow("${param.pname}").location.href = "${ctx}/webView/wsbx/gwjdf/gwjdfsh_list.jsp";
	   				close(winId);
	   			}
	   		});
			
		}
		
	});
	$("#btn_refuse").click(function(){
		var valid;
		if(validate){
			validate.bootstrapValidator("validate");
			valid = $('#myform').data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
	   			url:"${ctx}/gwjdfsh/refuse",
	   			data:"guid="+"${guid}&shyj="+$("#txt_yj").val(),
	   			type:"post",
	   			async:"false",
	   			success:function(val){
	   				alert("审核成功，信息已退回！");
	   				parent.getIframWindow("${param.pname}").location.href = "${ctx}/webView/wsbx/gwjdf/gwjdfsh_list.jsp";
	   				close(winId);
	   			}
	   		});
			
		}
		
	});
	$(document).on("change","#drp_shyj",function(){
		var shzt=$(this).text();
		var options=$("#drp_shyj option:selected"); 
		//alert(options.text());
		if(options.val()!=''){
			$("#txt_yj").text(options.text());
		}
		
	});
});
</script>

</html>