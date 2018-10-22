<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>表单</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
	<form id="myform" class="form-horizontal" action="" method="post">
	<input  type="hidden" name="cmd" value="approve">
		<input type="hidden" name="pass"  />
		<input type="hidden" name="applyUser" value="${models.formData.applyUser}" />
		<input type="hidden" name="processInstanceId" value="${models.processInstanceId}"  />
		 <div class="container box-content">
			<div class="container-fluid dialog-body">
				<div class="box-header clearfix" style="margin-top: -5px;">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>
	            		表单信息
	            	</div>
        		</div>
				<div class="row">
					<div class="col-xs-6">
					${models.lastForm}
					</div>
				</div>
		</div>
		</div>
		<div class='page-bottom clearfix'>
			<!-- 保存和返回按钮 开始 -->
			<div class="pull-right">
				<button class="btn btn-default" id="btn_tg"> <i class="fa icon-save"></i> 通过</button>
				<button class="btn btn-default btn-without-icon" id="btn_th"></i> 退回</button>
			</div>
		</div>
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
		
$(function() {
	//保存按钮
	$("#btn_tg").click(function(e) {
		$("input[name=pass]").val("1");
		$.ajax({  
            type: "POST",  
            url:"${pageContext.request.contextPath}/model/getStartFormAndStartProcess",  
            data:$('#myform').serialize(),// 序列化表单值  
            async: false,  
            error: function(request) {  
                alert("Connection error");  
            },  
            success: function(data) { 
            	alert("通过成功");
            	getIframWindow("${param.pname}").table.ajax.reload()
            	var winId = getTopFrame().layer.getFrameIndex(window.name);
				close(winId);
            }  
        });
	});
	
	$("#btn_th").click(function(e) {
		$("input[name=pass]").val("2");
		$.ajax({  
            type: "POST",  
            url:"${pageContext.request.contextPath}/model/getStartFormAndStartProcess",  
            data:$('#myform').serialize(),// 序列化表单值  
            async: false,  
            error: function(request) {  
                alert("Connection error");  
            },  
            success: function(data) { 
            	alert("退回成功");
            	getIframWindow("${param.pname}").table.ajax.reload()
            	var winId = getTopFrame().layer.getFrameIndex(window.name);
				close(winId);
            }  
        });
	});
	//关闭
	$("#btn_cancelw").click(function() {
		var winId = getTopFrame().layer.getFrameIndex(window.name);
		close(winId);
	});
	
});
String.prototype.replaceAll = function(s1,s2){
	return this.replace(new RegExp(s1,"gm"),s2);
	};
	$(function(){
		var form='${models.lastForm}';
		 var index0=form.lastIndexOf(">");
		  var p=form.split("<p>"); 
          for(var i=1;i<p.length;i++){ 
              var pName=p[i].substring(0,p[i].indexOf("："));
              var index1=p[i].indexOf('name="');  
              var p0=p[i].substring(index1+6,p[i].lastIndexOf(">"));  
              var index2=p0.indexOf('"');  
              var keyName=p[i].substring(index1+6,index2+index1+6);
             var value=null;
            var formData='${models.formData}'.replaceAll("=",":\'");
            formData=formData.replaceAll(",","\',");
            formData=formData.replaceAll("}","\'}");
            formData =(new Function("","return "+formData))(); 
           for(var key in formData){ 
             if(key==="cmd"){ 
            
             }else{
            	 value=(formData)[key];
                 $("[name='"+key+"']").val(value);
                 $("input[name='"+key+"'][value='"+value+"']").attr("checked", true);
             } 
             
          } 
          }  
		
	});
	</script>
</body>
</html>