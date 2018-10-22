<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>图片上传</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.tool-fix{                 
		display:none;
	}
</style>
</head>
<body>
<input id="imageFile" name="imageFile[]" type="file" multiple class="file-loading">
<div id="errorBlock" class="help-block"></div>
<div style="height:30px;text-align: center;">
	<span id="s_msg" style="color:Red;padding-right:10px;"></span>
	<button id="btn_upload" type="button" class="btn btn-default" style="min-width:90px;"><i class="glyphicon glyphicon-upload text-info"></i> 上传图片</button>
	<button id="btn_sel" type="button" class="btn btn-default btn-primary" style="min-width:90px;"><i class="glyphicon glyphicon-folder-open"></i> 选择图片</button>
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	$("#imageFile").fileinput({
       	fileActionSettings:{
    		showUpload:false//每个图片右下角的上传按钮
    	},
    	initialPreviewAsData:true,//上传之后是否显示图片
        uploadUrl:'${pageContext.request.contextPath}/phone/doUploadSmsc',
        maxFilePreviewSize: 10240,
        showUpload:false,//选择按钮前边的批量上传按钮
        showClose:false,//显示图片的区域右上角的叉号，可以清空图片信息
        showCancel:false,//显示取消上传中的图片
        dropZoneTitle:"暂无图片",//显示图片的区域默认显示的文字
        dropZoneEnabled:true,//显示图片的区域
        initialPreviewShowDelete:true,
        showBrowse:false,//选择按钮
        showCaption:false,//选择按钮前边的文本框
        uploadExtraData:function(id,index){
        	return {"fold":"${fold}","id":"${dbh}","djlx":"${djlx}","stamp":"${stamp}","rybh":"${rybh}"};
        },
        allowedFileExtensions : ['jpg', 'png','gif','bmp', 'jpeg'],
        overwriteInitial:false,
        maxFileSize: 10240,
        maxFileCount:20,
        deleteUrl: "${pageContext.request.contextPath}/phone/doDelSmsc?rybh=${rybh}&stamp=${stamp}"
//     }).on("filebatchuploadsuccess",function(){
//     	$("#s_msg").prop("innerHTML","上传成功！");
    }).on("fileuploaded",function(event,info){
    	$("#s_msg").prop("innerHTML","上传成功！");
    }).on("filebatchuploadcomplete",function(){
    	$("div.kv-upload-progress,div.file-thumb-progress").addClass("hide");
    }).on("filebatchselected",function(){
    	$("#s_msg").prop("innerHTML","");
    });
	
	$('#btn_sel').click(function(){
		$("#imageFile").click();
	});
	
	$("#btn_upload").click(function(){
		$("#s_msg").prop("innerHTML","");
		if(getWscCnt()){
			$("#s_msg").prop("innerHTML","请先选择图片！");
		}
		else{
			$("#imageFile").fileinput("upload");
		}
	});
});
//true：没有需要上传的图片了  false：还有没上传的图片
var getWscCnt = function(){
	var bool = true;
	$("img.file-preview-image").each(function(){
		var imgurl = $(this).attr("src");
		if(imgurl.substr(0,4) == "data"){
			bool = false;
		}
	});
	return bool;
}
</script>
</body>
</html>