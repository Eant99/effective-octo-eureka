<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>查看历史</title>
<%-- <link href="${pageContext.request.contextPath}/static/css/systemSet/seachBody_wev8.css" rel="stylesheet" /> --%>
<link href="${pageContext.request.contextPath}/static/plugins/datatable/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.leftfont {
	border-bottom: 1px solid;
	border-color: #F3F2F2;
	height: 30px;}

.box-panel {
	border-left: 0px !important;}

.btn-file {
	left: 34px;
	top: 10px;}

.xtb {
	font-size: 23px;
	color: #fff !important;
	}

a:hover,a:focus {
	color: #CCCCCC;}

.dw {
	height: 20px;
	line-height: 38px;
	margin-left: 5px;
	font-size: 12px;
	font-weight: normal;
	margin-top: 5px;
}
.tab_box{
	margin-top: 40px;
}
#tit {
	line-height: 39px !important;
}
.kssj,.jssj{
	margin-bottom: 15px;
    position: relative;
	display:inline-table;
	vertical-align:middle
	display: table;
    border-collapse: separate;
	}
</style>
</head>
<body id="mainBody" >
<form id="myform" class="form-horizontal" action="" method="post">
	<div class="e8_box demo2" style="position: relative;">
		<div class='page-header' style="padding: 0px; margin: 0px; top: 0px; height: 40px;">
			<div class="pull-left" style="line-height: 30px;">
				<span class="page-title text-primary" id="tit"> <i class='fa icon-xiangxixinxi'></i>流程历史</span>
			</div>
			<div class="pull-right" style="line-height: 5px;">
			<br/>
				<button type="button" class="btn btn-default" id="btn_back">返回</button>
			</div>
			<span class="e8_rightBorder"></span>
		</div>
		<div class="tab_box" >
			<table style="width: 100%; height: 100%;">
				<tr style="min-height: 200px;">
					<td style="width: 205px; vertical-align: top; border-right: solid 1px gray; border-color: #E6E6E6;">
						<div  style="min-height: 200px; padding: 10px 10px 0px 10px;position: relative;">
						<input type="hidden" id="type" value="${type} ">
						<img src="${pageContext.request.contextPath}/process/viewProcess?processInstanceId=${processInstanceId}">
					  </div>
					</td>
	</tr>
</table>
   <iframe style="height: 100%;width:100%;position: absolute;" src="${pageContext.request.contextPath}/process/getBljlPage?processInstanceId=${processInstanceId}" id='bljl'></iframe>
			
		</div>
	</div>
	</form>
<%@include file="/static/include/public-list-js.inc"%>

<script src="${pageContext.request.contextPath}/static/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/validator/bootstrapValidator.js"></script>
	<script
		src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js"
		type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/plugins/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/kindeditor-4.1.2/kindeditor.js"></script>
	<script>
	 /*文字编辑器*/
	function editor() {
		KindEditor.ready(function(K) {
			editor = K.create('#content', {
				uploadJson : '${pageContext.request.contextPath}/static/plugins/kindeditor-4.1.2/jsp/upload_json.jsp',
				afterBlur : function() {
					this.sync();
				}
			});
		});
	}
	
	/*文字编辑器2*/
	function editorone() {
	KindEditor.ready(function(K) {
		editor = K.create('#contentone', {
			uploadJson : '${pageContext.request.contextPath}/static/plugins/kindeditor-4.1.2/jsp/upload_json.jsp',
				readonlyMode:true,
			afterBlur : function() {
				this.sync();
			}
		});
		
	});
}

		$(function() {
			editor();
			editorone();
     	


	//返回
	 $("#btn_back").click(function(e) {
		 var type=$("#type").val().trim();
 		 window.history.back(-1);

		 });
		 });
		
</script>
</body>
</html>