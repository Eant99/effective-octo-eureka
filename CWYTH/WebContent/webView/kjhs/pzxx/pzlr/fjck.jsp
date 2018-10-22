<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>附件查看</title>
<%@include file="/static/include/public-manager-css.inc"%>

</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>查看附件信息</span>
		</div>
<!-- 		<div class="pull-right"> -->
<!-- 			<button type="button" class="btn btn-default" id="btn_back">返回</button> -->
<!--         </div> -->
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
				<div class=" pull-left text-primary">
					<i class="fa icon-xiangxi"></i>上传附件
				</div>
			</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-12">
						<input id="imageFile" name="path" type="file" multiple class="file-loading" disabled/>
						<div id="errorBlock" class="help-block"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var suffix = "&mkbh=${param.mkbh}";
$(function(){	
	//图片信息开始
	var fjView = [<%=request.getAttribute("fjView")%>];
	var fjConfig = [<%=request.getAttribute("fjConfig")%>];
	//附件信息
     $("#imageFile").fileinput({
       	fileActionSettings:{
    		showUpload:false//隐藏上传按钮
    	},
    	initialPreview:fjView,
    	initialPreviewAsData:true,
    	initialPreviewConfig:fjConfig,
    	uploadUrl: '${pageContext.request.contextPath}/file/fileUpload',//上传请求路径
        maxFilePreviewSize: 10240,//最大上传文件的大小
        showUpload:false,//是否显示上传按钮
        initialPreviewShowDelete:true,
        showBrowse:false,
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"zcxx","id":"${gwjdfsq.guid}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    });
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/kjhs/pzxx/pzlr/pageSkip?pageName=pzlr";
	});
	//刷新按钮
// 	$(".reload").click(function(){
// 		 var operateType =  $("[name='operateType']").val();
// 		 if(operateType=='U'){
// 			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"+suffix
// 		 }else{
// 			 var url = window.location.href;
// 	    	if(url.indexOf("?")>0){
// 	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"+suffix
// 	    	}else{
// 	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"+suffix
// 	    	}
// 		 }
// 	});
	
// 	SmscLoad("${pageContext.request.contextPath}",{"id":"${gwjdfsq.guid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
});

</script>
</body>
</html>