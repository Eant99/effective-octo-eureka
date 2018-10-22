<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>通讯录数据导入</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.row{
  margin-right:0
}
</style>
</head>
<body>
<form id="myform"  class="form-horizontal" action="${ctx}/txl/upload" method="post" enctype="multipart/form-data">
	<input type="hidden" id="sclx" name="sclx" value="">
    <div class="page-header">
		<div class="pull-left">
			<span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>通讯录数据导入</span>
		 </div>
	</div>
    <div class="box" style="margin-top: 17px;">
       	<div class="row" style="padding-top: 2px;"> 
           	<div class="col-md-2">               
                 <div class="input-group">
					  <span class="input-group-addon" style="border-radius: 5px;">数据位置</span>
					  <div class="col-md-12" style="height: 25px;">
						   <input id="imageFile" name="imageFile" type="file" class="file-loading">
						   <div id="errorBlock" class="help-block"></div>
					  </div>
				 </div>
             </div>                    
         </div> 
         <div class="row"> 
           	 <div class="col-md-2 ">               
                 <div class="input-group">
					 <span class="input-group-addon" style="height: 25px;border-radius: 5px;">模板下载</span>
					 <div style="margin-top: 5px;">
						 <a href="javascript:void(0);" id="btn_exp" style="color: red;text-decoration: none;">下载模板</a>
					 </div>
				 </div>
             </div>                    
        </div>
        <div class="row" style="margin-left: 100px;margin-right: 100px;">
        	<div class="col-md-2 col-md-offset-1">
        		<button name="ibtnBf" type="button" id="ibtnBf" class="ibtn_bf btn btn-default btn-lg btn-block">
                    <i class="faw fa-cloud-upload" style="color: #03A9F4;font-size: 22px;"></i>导入数据
                </button>
        	</div>
        </div> 
        <div class="row">
        	<div class="col-md-2 ">
        		<div style="overflow: auto; color: red;">
        			<c:forEach var="error" items="${error}">
        			   ${error}
        			</c:forEach>
        		</div>
        	</div>
        </div> 
        <div class="row" style="height: 10px;"></div>
   </div>
</form>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	//下载模板
	$("#btn_exp").click(function(){
		var fname = "通讯录模板信息.xls";
		var path = "uploadFiles/通讯录模板信息.xls";
		FileDownload("${pageContext.request.contextPath}/xzwhb/fileDownloadImpo",fname,path);
	});
	$("#ibtnBf").click(function(){
		var fileName=$("#imageFile").val();
		if(fileName==null||fileName==""){
			alert("请选择文件上传");
		}else if(fileName.trim().substr(fileName.lastIndexOf("."))==".xlsx"){
			alert("请将.xlsx文件另存为.xls文件!");
		}else if(fileName.trim().substr(fileName.lastIndexOf("."))!=".xls"){
			alert("请选择.xls格式的文件!");
		}else{
			confirm("您确定导入吗？",{title:"提示"},function(index){
				$("#myform").submit();
   				close(index);
   			});
		}
	});
});
</script>
</body>
</html>