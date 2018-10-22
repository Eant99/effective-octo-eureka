<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>登录信息设置</title>
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css"rel="stylesheet">
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/bootstrap/bootstrap.min.js"type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.css" media="all" rel="stylesheet" type="text/css"/>

<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
<style type="text/css">
	.box{
		overflow:auto;
	}
	.box-panel-left{
		float: left;
	    width: 58%;
	}
	.box-panel-right{
		float: left;
	    width: 40%;
	    margin-left:2%;
	}
	.edui-editor edui-default{
		width:110% !important;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="Content" value="${Content}">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>登录信息设置
			</span>
		</div>
        <div class="pull-right">
			<button class="btn btn-default" type="button" id="btn_save">
			<i class="fa icon-save"></i>保存
			</button>
        </div>
    </div>
	<div class="box" style="margin-top: 12px;overflow:hidden">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>系统信息设置
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal"/>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">学校名称：</span>
							<input type="text" id="txt_xxmc" class="form-control input-radius" name="xxmc" value="${dlxx.XXMC}"/>
						</div>
					</div>
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">系统名称：</span>
							<input type="text" id="txt_xtmc" class="form-control input-radius" name="xtmc" value="${dlxx.XTMC}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">技术支持：</span>
							<input type="text" id="txt_jszc" class="form-control input-radius" name="jszc" value="${dlxx.JSZC}"/>
						</div>
					</div>
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">联系电话：</span>
							<input type="text" id="txt_lxdh" class="form-control input-radius" name="lxdh" value="${dlxx.LXDH}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">电子邮箱：</span>
							<input type="text" id="txt_email" class="form-control input-radius" name="email" value="${dlxx.EMAIL}"/>
						</div>
					</div>
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">联系地址：</span>
							<input type="text" id="txt_lxdz" class="form-control input-radius" name="lxdz" value="${dlxx.LXDZ}"/>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>登录信息设置
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal"/>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon" style="border-right:1px solid #CCCCCC;">登录方式：</span>
							<label class="checkbox-inline" style="margin-left:20px;"><input type="checkbox" <c:if test="${fn:contains(dlxx.DLFS,1)}">checked</c:if> name="dlfs" value="1">姓名</label>
							<label class="checkbox-inline"><input type="checkbox" <c:if test="${fn:contains(dlxx.DLFS,2)}">checked</c:if> name="dlfs" value="2">工号</label>
<%-- 							<label class="checkbox-inline"><input type="checkbox" <c:if test="${fn:contains(dlxx.DLFS,3)}">checked</c:if> name="dlfs" value="3">身份证号</label> --%>
<%-- 							<label class="checkbox-inline"><input type="checkbox" <c:if test="${fn:contains(dlxx.DLFS,4)}">checked</c:if> name="dlfs" value="4">手机号</label> --%>
						</div>
					</div>
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">验证码形式：</span>
							<select name="yzmlx" id="drp_yzmlx" class="form-control">
								<option value="1" <c:if test="${dlxx.YZMLX==1}">selected</c:if>>四位数字</option>
								<option value="2" <c:if test="${dlxx.YZMLX==2}">selected</c:if>>六位数字</option>
								<option value="3" <c:if test="${dlxx.YZMLX==3}">selected</c:if>>四位字母</option>
								<option value="4" <c:if test="${dlxx.YZMLX==4}">selected</c:if>>六位字母</option>
								<option value="5" <c:if test="${dlxx.YZMLX==5}">selected</c:if>>四位字母加数字</option>
								<option value="6" <c:if test="${dlxx.YZMLX==6}">selected</c:if>>六位字母加数字</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">初始密码：</span>
							<input type="text" id="txt_csmm" class="form-control input-radius" name="csmm" value="${dlxx.CSMM}"/>
						</div>
					</div>
<!-- 					<div class="col-md-6"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">短消息验证：</span> -->
<%-- 							<input type="hidden" name="dxyz" id="txt_dxyz" value="${dlxx.DXYZ}"/>  --%>
<!--                             <div class="switch"> -->
<!--                                    <div class="onoffswitch"> -->
<%--                                        <input type="checkbox" class="onoffswitch-checkbox" <c:if test="${dlxx.DXYZ==1}">checked</c:if> id="btn_onoff"> --%>
<!--                                        <label class="onoffswitch-label" for="btn_onoff"> -->
<!--                                            <span class="onoffswitch-inner"></span> -->
<!--                                            <span class="onoffswitch-switch"></span> -->
<!--                                        </label> -->
<!--                                    </div> -->
<!--                              </div> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
				<div class="row">
				    <div class="col-md-8">
					    <div class="input-group">
						    <span class="input-group-addon">注意事项</span>
						    <div id="editor">
							    <textarea style="width:100%;height:500px;" name="content" id="content">${Content}</textarea>
						    </div>
					    </div>
				    </div>
			    </div>
			    
			    
			    
			    
			</div>
		</div>
	</div>
	
	
	<div class="box">
		<div class="box-panel box-panel-left bgImg">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>背景图片信息（可上传三张图片）
				</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
			</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-12">
						<input id="imageFile" name="imageFile[]" type="file" multiple class="file-loading">
						<div id="errorBlock" class="help-block"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel box-panel-right logoImg">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>Logo图片信息（可上传一张图片）
				</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
			</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-12">
						<input id="imageLogo" name="imageLogo[]" type="file" class="file-loading">
						<div id="errorBlock" class="help-block"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	editor();
	//短消息验证
	$(document).on("click","#btn_onoff",function(){
		if($("[name='dxyz']").val()=='0'){
			$("[name='dxyz']").val("1");
		}else if($("[name='dxyz']").val()=='1'){
			$("[name='dxyz']").val("0");
		}else{
			$("[name='dxyz']").val("0");
		}
	});
	//验证框架
	var validate = $('#myform').bootstrapValidator({fields:{
	    xtmc:{validators: {notEmpty: {message: '系统名称不能为空'}}},
	    jszc:{validators:{notEmpty:{message:'技术支持不能为空'}}},
	    email:{validators:{notEmpty:{message:'电子邮箱不能为空'},emailAddress:{message:'电子邮箱格式不正确'}}},
	    lxdz:{validators:{notEmpty:{message:'联系地址不能为空'}}},
	    lxdh:{validators: {notEmpty:{message:'联系电话不能为空'},regexp: {regexp: /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,message: '联系电话格式不正确'}}}}
    });
	//保存按钮${ctx}/dlxx/doSave
	$(document).on("click","#btn_save",function(){
		doSave(validate,"myform","${ctx}/dlxx/doSave",function(val){
			if($(".bgImg").find(".file-live-thumbs .file-preview-frame").length>0){
				$("#imageFile").fileinput("upload");
			}
			if($(".logoImg").find(".file-live-thumbs .file-preview-frame").length>0){
				$("#imageLogo").fileinput("upload");
			}
		},function(val){
			
		});
	});
	
	var logoFilePreview = [<%=request.getAttribute("logoFilePreview")%>];
	var logoFileConfig = [<%=request.getAttribute("logoFileConfig")%>];
	var imgFilePreview = [<%=request.getAttribute("imgFilePreview")%>]
	var imgFileConfig = [<%=request.getAttribute("imgFileConfig")%>];
	//文件上传(背景图)
	$("#imageFile").fileinput({
       	fileActionSettings:{
    		showUpload:false//隐藏上传按钮
    	},
    	initialPreview:imgFilePreview,
    	initialPreviewConfig:imgFileConfig,
    	initialPreviewAsData:true,
        uploadUrl: '${pageContext.request.contextPath}/file/fileUpload',//上传请求路径
        maxFilePreviewSize: 10240,//最大上传文件的大小
        showUpload:false,//是否显示上传按钮
        initialPreviewShowDelete:true,
        showBrowse:true,
        showCaption:true,
        uploadExtraData:function(id,index){
        	return {"fold":"bgImg","id":"logoImgAndbgImg","djlx":"bgImg"};
        },//上传额外的东东
        allowedFileExtensions : ['jpg', 'png','gif'],//允许的文件类型  
        overwriteInitial: false,  
        maxFileSize: 3000,//文件的最大大小  
        maxFilesCount: 3,//最多文件数量 
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    });
	//文件上传
	$("#imageLogo").fileinput({
       	fileActionSettings:{
    		showUpload:false//隐藏上传按钮
    	},
    	initialPreview:logoFilePreview,
    	initialPreviewConfig:logoFileConfig,
    	initialPreviewAsData:true,
        uploadUrl: '${pageContext.request.contextPath}/file/fileUpload',//上传请求路径
        maxFilePreviewSize: 10240,//最大上传文件的大小
        showUpload:false,//是否显示上传按钮
        initialPreviewShowDelete:true,
        showBrowse:true,
        showCaption:true,
        uploadExtraData:function(id,index){
        	return {"fold":"logoImg","id":"logoImgAndbgImg","djlx":"logoImg"};
        },//上传额外的东东
        allowedFileExtensions:['jpg','png','gif'],//允许的文件类型  
        overwriteInitial:false,
        maxFileSize:1000,//文件的最大大小  
        maxFilesCount:1,//最多文件数量 
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    });
	
	
});
function editor(){
	KindEditor.ready(function(K) {
        editor = K.create('#content',{
        	afterBlur : function() {
    			this.sync();
    		}
        });
	});
}
//特殊保存(字符串数组)
function doSave_dlxx(_validate,_formId,_url,_success,_fail){
	var index;
	var valid;
	if(_validate){
		_validate.bootstrapValidator("validate");
		valid = $('#'+_formId).data('bootstrapValidator').isValid();
	}
	else{
		valid = true;
	}
	if(valid){
		$.ajax({
			type:"post",
			url:_url,
			data:$('#'+_formId).serialize(),
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				alert(data.msg);
				if(data.success == "true"){
					$("#operateType").val("U");
					if(_success!=""&&_success!=""&&_success!=""){
						_success(data);
					}
				}
				else
				{
					if(_fail!=""&&_fail!=""&&_fail!=""){
						_fail(data);
					}
				}
			},
			error:function(val){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(val){
				index = loading(2);
			}
		});
	}
}
</script>
</body>
</html>