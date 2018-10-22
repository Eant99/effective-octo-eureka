<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>原始数据导入</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.row > div[class*='col-']{
		margin-top:6px;
		margin-left:6px;
	}
</style>
</head>
<body>
<form id="myform"  class="form-horizontal" action="${ctx}/yssjdr/upload" method="post" enctype="multipart/form-data">
	<input type="hidden" id="sclx" name="sclx" value="">
	<div class="page-header">
		<div class="pull-left">
			<span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i> 原始数据导入</span>
		</div>
    </div>
   <div class="box">
       <div class="row">
		    <div class="col-md-7">
				<div class="alert alert-danger" style="font-size:13px;" role="alert">
				<strong >注 意：</strong><br/>
				&emsp;&emsp;1、 为保证您的数据安全准确，请先备份数据库，然后进行该操作！
				</div>
			</div>
		</div>
        <div class="row"> 
           	<div class="col-md-2">               
                 <div class="input-group">
					<span class="input-group-addon">数据类型</span>
					<select id="drp_lx" class="form-control input-radius" style="width: 150px;">
						<option value="00">全部大类</option> 
						<option value="01">房屋类</option>
						<option value="02">构筑物类</option>
						<option value="03">土地类</option>
						<option value="04">一般设备类</option>
						<option value="05">交通运输类</option>
						<option value="06">文物及陈列品类</option>
						<option value="07">图书类</option>
						<option value="08">家具用具及被服装具类</option>
						<option value="09">植物类</option>
						<option value="10">动物类</option>
						<option value="11">无形资产类</option>
					</select>
				</div>
            </div>                    
        </div>  
       	<div class="row"> 
           	<div class="col-md-2">               
                 <div class="input-group">
					<span class="input-group-addon" style="border-radius: 5px;">数据位置</span>
					<div class="col-md-12" style="height: 25px;padding-left: 5px;padding-top: 1px;">
						<input id="imageFile" name="imageFile" type="file" class="file-loading">
						<div id="errorBlock" class="help-block"></div>
					</div>
				</div>
            </div>                    
        </div> 
        <div class="row"> 
           	<div class="col-md-2">               
                 <div class="input-group">
					<span class="input-group-addon" style="height: 25px;border-radius: 5px;">模板下载</span>
					<div style="margin-top: 5px;">
						<a href="javascript:void(0);" id="btn_exp" style="color: red;text-decoration: none;">下载模板</a>
					</div>
				</div>
            </div>                    
        </div>
        <div class="row">
        	<div class="col-md-2 col-md-offset-1">
        		<button name="ibtnBf" type="button" id="ibtnBf" class="ibtn_bf btn btn-default btn-lg btn-block">
                     <i class="faw fa-cloud-upload" style="color: #03A9F4;font-size: 22px;"></i>导入数据
                 </button>
        	</div>
        </div> 
        <div class="row" style="text-align: center;" >
        	<div class="col-md-2">
        		<div style="overflow: auto; color: red;">
        			<c:forEach var="error" items="${error}">
        			${error}
        			</c:forEach>
        		</div>
        	</div>
        </div>
        <div class="row" style="height:10px;"></div> 
   	  </div>
  </form>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	var commonWin = "${commonWin}";
	if(commonWin=='T'){
		var file = "${file}";
		var sclx = "${sclx}";
		select_commonWin("${ctx}/yssjdr/getListPage?file="+file+"&sclx="+sclx,"数据对照","920","630","yes");
	}
	$("#ibtnBf").click(function(){
		var lx = $("#drp_lx").val();
		$("#sclx").val(lx);
		$("#myform").submit();				
	});	
	//下载模板
	$("#btn_exp").click(function(){
		var fname = "模板.rar";
		var path = "uploadFiles/模板.rar";
		FileDownload("${pageContext.request.contextPath}/xzwhb/fileDownloadImpo",fname,path);
	});
	//刷新按钮
	$(".reload").click(function(){
		window.location.href = "${pageContext.request.contextPath}/yssjdr/getPage?mkbh=${param.mkbh}";
	});
});
</script>
</body>
</html>