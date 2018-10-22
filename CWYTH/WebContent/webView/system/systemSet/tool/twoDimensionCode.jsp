<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>二维码生成</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
	<form id="myform" class="form-horizontal" action="" method="post">
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<div class="page-header">
			<div class="pull-left">
				<span class="page-title text-primary">
					<i class='fa icon-xiangxixinxi'></i>
					二维码
				</span>
			</div>
		</div>
		<div class="box" >
			<div class="box-panel" style="margin:4px auto;">
				<div class="box-header clearfix">
					<div class=" pull-left text-primary">
						<i class="fa icon-xiangxi"></i>
						<input type="text" id="encoderContent" title="输入内容" value="" style="width:300px;">
						<button type="button" id="btn_sc" class="btn btn-link btn-custom">生成二维码</button>
					</div>
				</div>
				<hr class="hr-normal">
				<div class="container-fluid box-content">
					<div class="row">
						<div class="col-md-6">
							<div style="width:285px;float: left;">
								<div class="widget-box">
									<div class="widget-header widget-header-flat widget-header-small">
										<h5><i class="icon-credit-card"></i> 
											生成二维码
										</h5>
									</div>
									
									<div class="widget-body">
									 <div class="widget-main">
									 		<img id="encoderImgId" cache="false" src="${pageContext.request.contextPath}/static/images/uploadFiles/twoDimensionCode/default.png" width="265px" height="265px;"/>
									 </div><!--/widget-main-->
									</div><!--/widget-body-->
								</div><!--/widget-box-->
							 </div>
						</div>
<!-- 						<div class="col-md-6"> -->
<!-- 							<div style="width:285px;float: left;"> -->
<!-- 								<div class="widget-box"> -->
<!-- 									<div class="widget-header widget-header-flat widget-header-small"> -->
<!-- 										<h5><i class="icon-credit-card"></i>  -->
<!-- 											解析二维码 -->
<!-- 										</h5> -->
<!-- 									</div> -->
<!-- 									<div class="widget-body"> -->
<!-- 										<div class="widget-main"> -->
<!-- 											<div> -->
<!-- 												<textarea id="readContent" title="解析结果" placeholder="显示解析结果" class="autosize-transition span12" style="width:375px;height:160px;"> -->
<!-- 												</textarea> -->
<!-- 											</div> -->
<!-- 											<div> -->
<!-- 												<div style="float: left;" id="tipsTwo"><input type="file" name="TP_URL" id="uploadify1" keepDefaultStyle = "true"/></div> -->
<!-- 												<div><a class="btn btn-mini btn-success" id="btn_jx" onclick="uploadTwo();">解析</a></div> -->
<!-- 											</div> -->
<!-- 										</div>/widget-main -->
<!-- 									</div>/widget-body -->
<!-- 							 </div> -->
<!-- 						</div> -->
					</div>
				</div>
			</div>
		</div>
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/myjs/readTwoD.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/layer/layer.js"></script>
<script type="text/javascript">
$(function(){
	var src = "${param.url }";
	//生成二维码
	$("#btn_sc").click(function(){
		$("#encoderImgId").attr("src","${pageContext.request.contextPath}/static/images/uploadFiles/twoDimensionCode/jzx.gif");
		$.ajax({
			type: "POST",
			url: '${pageContext.request.contextPath}/tool/createTwoDimensionCode',
	    	data: {encoderContent:$("#encoderContent").val(),tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				console.log(data);
				 if(encoderContent.value==""){
					 alert("空白不能生产二维码！");
				 }
				 else if("success" == data.result){
					 layer.alert("生成成功！");
					 $("#encoderImgId").attr("src",'${pageContext.request.contextPath}/uploadFiles/' + data.encoderImgId+"?time="+new Date().valueOf());    
				 }else{
					 layer.alert("生成失败！");
					 $("#encoderImgId").attr("src","${pageContext.request.contextPath}/static/images/uploadFiles/twoDimensionCode/default.png");
					 return;
				 }
			}
		});
	});
	$("#btn_jx").click(function(){
		$.ajax({
			type: "POST",
			url: locat+'/tool/readTwoDimensionCode.do',
	    	data: {imgId:str,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					 if('null' == data.readContent || null == data.readContent){
						 $("#readContent").text("读不出内容, 检查是否有效二维码");
					 }else{
						 $("#readContent").tips({
								side:1,
					            msg:'读取成功',
					            bg:'#75C117',
					            time:3
					     });
						 $("#readContent").text(data.readContent);
					 }
				 }else{
					 $("#readContent").tips({
							side:3,
				            msg:'读取失败,后台有误',
				            bg:'#FF5080',
				            time:10
				     });
					 return;
				 }
			}
		});
	});
	
});
</script>
</body>
</html>