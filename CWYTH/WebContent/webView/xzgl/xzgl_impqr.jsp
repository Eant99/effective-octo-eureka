<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>在职薪资数据导入确认</title>
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body style="background-color: white;">
	<form id="myform" class="form-horizontal" action="" method="post"
		style="overflow: auto;">
		<div class="list" style="border: 0px;">
			<table id="mydatatables" class="table table-striped table-bordered">
			    <thead>
				     <tr>
				      <th style="text-align:center;">人员编号</th>
				      <th style="text-align:center;">姓&nbsp;&nbsp;名</th>
				      <th style="text-align:center;">工作月份</th>
				      <th style="text-align:center;">部&nbsp;&nbsp;门</th>
				      <th style="text-align:center;">人员类别</th>
				      <th style="text-align:center;">人员类型</th>
				      <th style="text-align:center;">编&nbsp;&nbsp;号</th>
				    </tr>
				<tbody>
					<c:forEach var="list" items="${list}">
						<tr>
							<td>${list.RYBH}</td>
							<td>${list.XM}</td>
							<td>${list.GZYF}</td>
							<td>${list.BM}</td>
							<td>${list.RYLB}</td>
							<td>${list.RYLX}</td>
							<td>${list.BH}</td>
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
	<script src="${pageContext.request.contextPath}/static/plugins/layer/layer.js"></script>
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
			$("#btn_dr")
					.click(
							function() {
// 								if ($("tbody").find("font").length > 0) {
// 									msg("数据有问题，请检查！");
// 								} else {
									var info_loading= layer.load(3);
									window.onload = function () {
										setIframeHeight(document.getElementById('content-iframe'));
										layer.close(info_loading);
									};
									var index;
									var file = "${file}";
									$.ajax({
											type : "post",
											url : "${pageContext.request.contextPath}/xzgl/doInsertXz",
											data : "&file=" + file,
// 											beforeSend: function () {
// 											    jqSender.hide().after('<img id="load" src="/static/images/index/db.png" />');
// 											   },
											success : function(val) {
												close(index);
												var data = $.trim(val);
												if (data == '') {
														alert("导入失败，请核对导入数据格式");
												} else {
													alert(data);
													var pname = "${param.pname}";
													var win = getIframWindow(pname);
													var winId = getTopFrame().layer.getFrameIndex(window.name);
													getIframWindow("iframe_200101").table.ajax.reload();
											    	close(winId);
													}
												}
											
											});
// 								}
							});
		});
	</script>
</body>
</html>