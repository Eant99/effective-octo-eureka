<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工具下载维护</title>
<%@include file="/static/include/public-manager-css.inc" %>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post">
    <input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="container-fluid dialog-body">
		<div class="row hide">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">文件编号</span>
					<input type="text" id="txt_guid" class="form-control input-radius" name="guid" value="${xzwhb.guid}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>文件名称</span>
					<input type="text" id="txt_wjmc" class="form-control input-radius" name="wjmc" value="${xzwhb.wjmc}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">文件描述</span>
					<textarea id="txt_wjms" class="form-control" name="wjms">${xzwhb.wjms}</textarea>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>文件信息
				</div>
			</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-12">
						<input id="path" name="path" type="file" multiple class="file-loading" value="${xzwhb.path}"/>
						<div id="errorBlock" class="help-block"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
	     <div class="pull-right">
			 <button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
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
$(function() {
	var fjView = [<%=request.getAttribute("fjView")%>];
	var fjConfig = [<%=request.getAttribute("fjConfig")%>];
	//附件信息
    $("#path").fileinput({
       	fileActionSettings:{
    		showUpload:false//隐藏上传按钮
    	},
    	initialPreview:fjView,
    	initialPreviewAsData:true,
    	initialPreviewConfig:fjConfig,
    	initialPreviewAsData:true,
    	uploadUrl: '${pageContext.request.contextPath}/xzwhb/fileUpload',//上传请求路径
        maxFilePreviewSize: 10240,//最大上传文件的大小
        showUpload:false,//是否显示上传按钮
        initialPreviewShowDelete:$("#operateType").val()=="U"?false:true,
        showBrowse:$("#operateType").val()=="U"?false:true,
        showCaption:$("#operateType").val()=="U"?false:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	var operateType=$("#operateType").val();
        	var wjmc = $("#txt_wjmc").val();
        	var wjms = $("#txt_wjms").val();
        	return {"fold":"gjxzwh","wjmc":wjmc,"wjms":wjms,"operateType":operateType};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    }).on("fileuploaded",function(){
    	alert("保存成功！");
    	var pname = "${param.pname}";
		var winId = getTopFrame().layer.getFrameIndex(window.name);
      		var pWindow = getIframWindow(pname);
      		pWindow.location.href = pWindow.location.href;
      		getTopFrame().close(winId);
    });
	//取消
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields :{
		wjmc: {validators : {notEmpty : {message : '不能为空'}}},}
	});
	//保存按钮
	$("#btn_save").click(function(e){
		var valid;
		if(validate){
			validate.bootstrapValidator("validate");
			valid = $('#myform').data('bootstrapValidator').isValid();
		}else{
			valid = true;
		}
		if(valid){
		    if($("[name = path]").val()== ""){
			    alert("请选择要上传的文件！");
		    }else{
		        $("#path").fileinput("upload");
		    }
		}
	});
});
</script>
</body>
</html>