<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(path+"@"+basePath);
%>
<style type="text/css">

</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>上传单据</span>
		</div>
		 <div class="pull-right">
			 <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
<!-- 			<div class="box-header1 clearfix"> -->
<!--             	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>单据上传 -->
<!--             	</div> -->
<!--         	</div> -->
			<hr class="hr-normal">
			<div class="container-fluid box-content">
					<div class="box-panel">
						<div class="box-header clearfix">
							<div class="sub-title pull-left text-primary">
							<i class="fa icon-xiangxi"></i>上传单据
							</div>
							<button type="button" class="btn btn-default" style="margin-left:10px;" id="scanToServer">扫描上传</button>
						</div>
					<div class="container-fluid box-content">
					<div class="row">
						<div class="col-md-12">
							<input id="imageFile" name="path" type="file" multiple class="file-loading"/>
							<div id="errorBlock" class="help-block"></div>
						</div>
					</div>
					</div>
				</div>
				
				</div>
			</div>
		</div>
		</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<script type="text/javascript">
var tag = true;
$(function(){
	var type = "${type}";
	if(type=="U"){
		$("#btn_save").hide();
	}
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
        showBrowse:true,
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"pzdj","id":"${uploadId}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${pageContext.request.contextPath}/file/fileDelete"//删除文件的路径
    });
});
$(document).on("click","[id='btn_save']",function(){
		$("#imageFile").fileinput("upload");
		alert("上传成功！");
// 	 	var winId = getTopFrame().layer.getFrameIndex(window.name);
// 	 	close(winId);
});

$("#scanToServer").click(function(){
	scanToServer();
});
function scanToServer(){
	select_commonWin("${pageContext.request.contextPath}/ywsm/goScanPage?mkbh=${param.mkbh}&sfbc=1&zbid=${uploadId}&basePath=<%=basePath%>&fold=pzdj","", "920", "530");
}

SmscLoad("${pageContext.request.contextPath}",{"id":"${uploadId}","djlx":"000000","fold":"pzdj","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
</script>

</html>