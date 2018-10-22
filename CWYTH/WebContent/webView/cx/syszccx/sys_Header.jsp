<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.pojo.StateManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>综合查询</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.td{
	height: 27px;
}

</style>
<body>
	<form action="" method="post" id="Form1" class="form-inline">
		<div id="centent_body" style="overflow: auto;">
			<div class="div_table" style="margin-top: 100px;">
				<table id="table" border='1' class="tab" style="width: 800px;margin:0 auto;border-color: rgba(211, 214, 214, 0.35);" cellspacing="0" cellpadding="0">
					<tr>
						<td class="td" style="text-align:center;font-weight:bolder;" colspan="6">请选择查询条件</td>
					</tr>
					<tr>
						<td colspan="6">
							<iframe src="${pageContext.request.contextPath}/webView/cx/zhcx/zhcxfrm.jsp?mkbh=${mkbh}&cols=" id="mainiframe" style="width: 100%;border:0px;height: 400px;"></iframe>
						</td>
					</tr>
					<tr class="td">
						<td colspan="6">
							<div style="text-align: center;">
								<input type="button" class="btn btn-default" id="button_qd" value="确定">
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
<%@include file="/static/include/public-list-js.inc"%>
<script type="text/javascript">
$(function(){
	//确定
	$("#button_qd").click(function(){
		$("#mainiframe").contents().find("#zhcxiframe").contents().find("#btn_qd").click();
 		var sql = "";
 		if($("#hid_zhcxsql").length > 0){
 			sql = $("#hid_zhcxsql").val();
 		}
 		location.href="${pageContext.request.contextPath}/webView/cx/syszccx/mainDwTree2.jsp?mkbh=${mkbh}&sql="+sql;
	});
});
</script>