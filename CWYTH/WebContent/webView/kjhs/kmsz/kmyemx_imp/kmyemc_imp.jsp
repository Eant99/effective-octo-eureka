<%-- <%@ include file="/WEB-INF/webView/include/taglib.jsp"%> --%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>基础数据导入</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.row {
	margin-right: 0
}
</style>
</head>
<body>
	<form id="myform" class="form-horizontal"  enctype="multipart/form-data" action="${ctx}/kmsz/kmyemx_upload?kmyebh=${kmyebh}&kmmc=${kmmc}"
		method="post" enctype="multipart/form-data">
		<div class="box" style="margin-top: 17px;">
			<div class="row">
				<div class="col-md-7">
					<div class="alert alert-danger" style="font-size: 14px;"
						role="alert">
						<strong>注 意：</strong><br />
						&emsp;&emsp;为保证您的数据正确导入，请先下载模板并在模板上输入所需导入数据（示例数据可删除）
					</div>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-2 ">
					<div class="input-group">
						<span class="input-group-addon" style="border-radius: 5px;">数据位置</span>
						<div class="col-md-12" style="height: 25px;">
							<input id="imageFile" name="imageFile" type="file"
								class="file-loading">
							<div id="errorBlock" class="help-block"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2 ">
					<div class="input-group">
						<span class="input-group-addon"
							style="height: 25px; border-radius: 5px;">模板下载</span>
						<div style="margin-top: 5px;">
							<a href="javascript:void(0);" id="btn_exp"
								style="color: red; font-size: 16px; text-decoration: none;">&nbsp;&nbsp;&nbsp;下载模板</a>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2 col-md-offset-1">
					<button name="ibtnBf" type="button" id="ibtnBf"
						class="ibtn_bf btn btn-default btn-lg btn-block">
						<i class="faw fa-cloud-upload"
							style="color: #03A9F4; font-size: 22px;"></i>导入数据
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
		</div>
		<div class='page-bottom clearfix'>
			<!-- 返回按钮 开始 -->
			<div class="pull-right">
				<button class="btn btn-default btn-without-icon" id="btn_cancelw">关闭</button>
			</div>
			<!-- 返回按钮 结束 -->
		</div>
	</form>
	<%@include file="/static/include/public-list-js.inc"%>
	<script>
		$(function() {
			
			//关闭
			$("#btn_cancelw").click(function() {
				var winId = getTopFrame().layer.getFrameIndex(window.name);
				close(winId);
			});
			
			var commonWin = "${commonWin}";
			if (commonWin == 'T') {
				var file = "${file}";
				var sclx = "${sclx}";
				var kmyebh = "${kmyebh}";//科目余额编号，科目编号
				var kmmc = "${kmmc}";//科目名称
// 				var winId = getTopFrame().layer.getFrameIndex(window.name);
				select_commonWin("${ctx}/kmsz/getImpPageQr?file="+ file + "&sclx=" + sclx +"&kmyebh="+ kmyebh +"&kmmc="+ kmmc, "数据确认", "750", "550");
// 				close(winId);
				$('#btn_cancelw').trigger("click");
				return false;
			}
			//导入
			$("#ibtnBf").click(function() {
				$("#myform").submit();
			});
// 			$("#ibtnBf").click(function(){
// 				var fileName=$("#imageFile").val();
// 				if(fileName==null||fileName==""){
// 					alert("请选择文件上传");
// 				}else if(fileName.trim().substr(fileName.lastIndexOf("."))==".xlsx"){
// 					alert("请将.xlsx文件另存为.xls文件!");
// 				}else if(fileName.trim().substr(fileName.lastIndexOf("."))!=".xls"){
// 					alert("请选择.xls格式的文件!");
// 				}else{
// 					confirm("您确定导入吗？",{title:"提示"},function(index){
// 						$("#myform").submit();
// 		   				close(index);
// 		   			});
// 				}
// 			});
			//点击下载对应模板
			var mblx = "${mblx}";
			$("#btn_exp").click(function(){
					var fname = "科目余额信息导入模板"+mblx+".xls";
					var path = "uploadFiles/kmyedr/科目余额信息导入模板"+mblx+".xls";
					FileDownload("${pageContext.request.contextPath}/xzwhb/fileDownloadImpo",fname,path);
			});
		});
		$(function() {
			//列表右侧悬浮按钮
			$(window).resize(
					function() {
						$("div.dataTables_wrapper").width(
								$("#searchBox").width());
						heights = $(window).outerHeight()
								- $(".search").outerHeight()
								- $(".row.bottom").outerHeight() - 20
								- $(".dataTables_scrollHead").outerHeight();
						$(".dataTables_scrollBody").height(heights);
						table.draw();
					});
		});
	</script>
</body>
</html>