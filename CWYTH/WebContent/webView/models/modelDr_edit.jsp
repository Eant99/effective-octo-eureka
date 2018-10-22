<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>知识管理附件</title>
<%@include file="/static/include/public-manager-css.inc"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/webuploader-0.1.5/webuploader.css">
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/include/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/include/pageoffice.js" id="po_js_main"></script>
</head>
<style>
#tabTop {padding-left:35%;}
#li {width:120px;text-align:center;}
body{height:100%}
</style>
<body style="background-color: white;">
<input type="hidden" name="operateType" value="${operateType }">
<input type="hidden" name="pid" value="${pid}">
<input type="hidden" name="treeId" value="${treeId}">
<div class="over-hight">
<div id="main">
	<div id="content"  style="height:100%;width:100%;overflow:hidden;">
	<div class="input-group"  style="margin-top:20px;width:80%;margin-left:auto;margin-right:auto">
        <span class="input-group-addon" style="text-align: right;">
		<span class="required">*</span>流程类型</span>
		<select class="form-control input-radius" name="lx" id="lx">
							 <option value="1">收文流程</option> 
							<option value="2">发文流程</option>
							<option value="3">督察督办</option>
							<!-- <option value="4">公文流转</option> -->
							<option value="5">会议申请</option>
							<option value="9">通知公告</option>
							<option value="10">自定义表单</option>
							</select>
		
	</div>
		<!-- 新增上传 -->
		<div class="container-fluid " style="height:60%;width:80%;margn-top:0px">
			<div class="box-panel">
				<div class="box-header clearfix" >
					<div class="sub-title pull-left text-primary" style="height: 10%;">
						<i class="fa icon-xiangxi"></i>选择一个包含图片信息（用于坐标定位）的文件(.bpmn20.xml 或 .bpmn)
					</div>
				</div>
				<div class="container-fluid box-content">
					<div class="row">
						<div class="col-md-12">
							<input id="path" name="file" type="file" multiple class="file-loading" value=""/>
							<div id="errorBlock" class="help-block"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
			<!-- 保存和返回按钮 开始 -->
			<div class="pull-right">
				<button class="btn btn-default" id="btn_save"> <i class="fa icon-save"></i>导入</button>
				<button class="btn btn-default btn-without-icon" id="btn_cancelw"> 关闭</button>
			</div>
		</div>

<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript"
		src="${pageContext.request.contextPath}/webuploader-0.1.5/webuploader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js.model/import.js"></script>
<script type="text/javascript"> 
$(function(){
	setOverHeight();
	$("#pobmodal-dialog").hide();
	var fjView = "";
	var fjConfig = "";
 //附件信息
    $("#path").fileinput({
	       	fileActionSettings:{
	    		showUpload:false//隐藏上传按钮
	    	},
	    	initialPreview:fjView,
	    	initialPreviewAsData:true,
	    	initialPreviewConfig:fjConfig,
	    	initialPreviewAsData:true,
	    	uploadUrl: '${pageContext.request.contextPath}/model/saveImport',//上传请求路径
	        maxFilePreviewSize: 10240,//最大上传文件的大小
	        showUpload:false,//是否显示上传按钮
	        initialPreviewShowDelete:true,
	        showBrowse:true,
	        showCaption:true,
	        showClose:false,
	        uploadExtraData:function(id,index){
	        	var lx=$("[name=lx]").val();
	        	return {"lx":lx,"fold":"lc","fileType":"lcdy","treeId":"${treeId}"};
	        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
	        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
	        overwriteInitial: false,  
	        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
	    }).on("fileuploaded",function(){
	    	alert("保存成功！");
	    	getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
           
	    }); 
	  	//取消
		$("#btn_cancelw").click(function(){
			getIframWindow("${param.pname}").table.ajax.reload();       
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});
		//保存按钮
		$("#btn_save").click(function(e){
			var count = $(".file-preview-frame").length;
			var lx=$("[name=lx]").val();
			if(lx==""){
				alert("请先选择分类");
 			}else if(count < 1){
 			    alert("请选择上传文件！");
 		    }else{
		        $("#path").fileinput("upload");
		    }
		});  
	});
</script>
</body>
</html>