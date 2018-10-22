<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<link href="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post">
	<div class="row" style="margin-left: 0px;margin-right: 0px;">
		<div class="col-md-12 tabs" style="padding-left: 0px;padding-right: 0px;">
			<ul id="tabTop" class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a id="tab_card">卡片信息</a></li>
				<li role="presentation"><a id="tab_ysd">验收单</a></li>
				<li role="presentation"><a id="tab_bd">变动</a></li>
				<li role="presentation"><a id="tab_cz">处置</a></li>
				<li role="presentation"><a id="tab_fj">附件</a></li>
				<li role="presentation"><a id="tab_zclsb">资产流水表</a></li>
			</ul>
		</div>
	</div>
	<c:if test="${yqbh != ''}">
	<div class="box-panel" style="width: 97%;margin-left: 1px;">
		<div class="box-header clearfix">
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-2" style="margin-left: 6px;">
					资产编号：<c:out value="${yshd.YQBH}"></c:out>
				</div>
            	<div class="col-md-2 col-md-offset-2">
            		金额单位：（元）
            	</div>
            	<div class="col-md-2">
            		面积单位：（平方米）
            	</div>
            	<div class="col-md-3">
            		<span style="color: red;">财政分类：</span> <c:out value="${yshd.CZFLMC}"></c:out>
            	</div>
           	</div>
       	</div>
       	<hr class="hr-normal">
		<div class="container-fluid box-content" style="padding-left: 6px;">
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">资产名称</span>
						<input type="text" id="txt_yqmc" class="form-control input-radius" name="yqmc"  value="${yshd.YQMC}"/>
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">分类代码</span>
						<input type="text" id="txt_flh" class="form-control input-radius" name="flh" value="${yshd.FLH}"/>
						<c:if test="${operateType == 'U' or operateType == 'C'}">
							<span class="input-group-btn"><button type="button" id="btn_flh" class="btn btn-link ">选择</button></span>
						</c:if>
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">分类名称</span>
						<input type="text" id="txt_flmc" class="form-control input-radius" name="yqbh" value="${yshd.FLMC}" readonly="readonly"/>
					</div>
				</div>
			</div>
			<jsp:include page="${url}.jsp"></jsp:include>
			<div class="row" style="height: 40px;"></div>
		</div>
	</div>	
	<div class='page-bottom clearfix'>
		 <div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save">
				保存
			</button>
         </div>
   </div>
   </c:if>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var ysdh='${ysdh}';
	if(ysdh!=null&&ysdh!=""){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
		$(".date,.btn:not(.btn-mark)").unbind();
		$(".page-bottom").attr("style","display:none");
	}
	//验收单
   	$("#tab_ysd").click(function(){
   		parent.document.getElementById("div_bh").innerHTML="请输入验收单编号";
   		parent.document.getElementById("txt_lx").value="1";
   		window.location.href ="${pageContext.request.contextPath}/xxtz/goYsdPage?yqbh=${yqbh}&ysdh=${ysdh}";
   	});
    //变动
   	$("#tab_bd").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/changelist.jsp?yqbh=${yqbh}&ysdh=${ysdh}";
   	});
    //处置
   	$("#tab_cz").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/assetdolist.jsp?yqbh=${yqbh}&ysdh=${ysdh}";
   	});
    //附件
   	$("#tab_fj").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/assetfjlist.jsp?yqbh=${yqbh}&ysdh=${ysdh}";
   	});
    //资产流水表
   	$("#tab_zclsb").click(function(){
   		window.location.href ="${pageContext.request.contextPath}/webView/xxtz/zclslist.jsp?yqbh=${yqbh}&ysdh=${ysdh}";
   	});
});
</script>
</body>
</html>