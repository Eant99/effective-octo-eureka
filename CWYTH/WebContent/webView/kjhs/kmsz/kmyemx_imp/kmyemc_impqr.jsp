<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>数据对照</title>
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body style="background-color: white;">
	<form id="myform" class="form-horizontal" action="" method="post"
		style="overflow: auto;">
		<div class="list" style="border: 0px;">
			<table id="mydatatables" class="table table-striped table-bordered">
				<tbody>
					<c:forEach var="list" items="${list}">
						<tr>
							<c:if test="${not empty list.JJKM}">
								<td>${list.JJKM}</td>
							</c:if>
							<c:if test="${not empty list.BMBH}">
								<td>${list.BMBH}</td>
							</c:if>
							<c:if test="${not empty list.XMBH}">
								<td>${list.XMBH}</td>
							</c:if>
							<td>${list.NCYE}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<br> <br>
	</form>
	<div class='page-bottom clearfix'>
		<!-- 返回按钮 开始 -->
		<div class="pull-right">
			<button class="btn btn-default" type="button" id="btn_dr">导入</button>
			<button class="btn btn-default btn-without-icon" id="btn_cancelw">关闭</button>
		</div>
		<!-- 返回按钮 结束 -->
	</div>
	<%@include file="/static/include/public-list-js.inc"%>
	<%@include file="/static/include/public-manager-js.inc"%>
	<script type="text/javascript">
		$(function() {
			$(".refresh").remove();
			//取消按钮
			$("#btn_cancelw").click(function() {
				var winId = getTopFrame().layer.getFrameIndex(window.name);
				close(winId);
			});
			//导入按钮
		$("#btn_dr").click(function() {
					if ($("tbody").find("font").length > 0) {
						msg("数据有问题，请检查！");
					} else {
							var index;
							var file = "${file}";
							var kmyebh = "${kmyebh}";//科目余额编号，科目编号
							var kmmc = "${kmmc}";//科目名称
							$.ajax({
										type : "post",
										url : "${pageContext.request.contextPath}/kmsz/doInsert",
										data : "&file=" + file +"&kmyebh="+ kmyebh +"&kmmc="+ kmmc,
										success : function(val) {
												close(index);
												var data = $.trim(val);
												if (data == '') {
														alert("导入失败，请核对导入数据格式");
												} else {
													alert(data);
													var winId = getTopFrame().layer.getFrameIndex(window.name);
														close(winId);
													}
												}
											});
								}
							});
		});
	</script>
</body>
</html>