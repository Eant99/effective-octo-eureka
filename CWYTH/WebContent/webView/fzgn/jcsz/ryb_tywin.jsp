<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>个人选项设置</title>
<link href="${pageContext.request.contextPath}/static/css/systemSet/grxx.css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/static/css/systemSet/seachBody_wev8.css" rel="stylesheet"/>
<%@include file="/static/include/public-manager-css.inc"%>
 <style type="text/css">
 html,body{
 	height:100%;
 	width:100%;
 }
 .leftfont{
	 border-bottom: 1px solid;
	 border-color: #F3F2F2;
	 height: 30px;
 }
 .box-panel{
     border-left: 0px !important;
 }
 .btn-file{
 	left: 45px;
    top: 10px;
 }
 .xtb{
 	font-size: 23px;
 	color:#fff !important;
 }
 a:hover,a:focus{
 	color:#CCCCCC;
 }
 .dw{
 	height: 20px;
    line-height: 38px;
    margin-left: 5px;
    font-size: 12px;
    font-weight: normal;
    margin-top: 5px;
 }
 .row{
 	height:42px;
 }
 .tool-fix{
 	display:none;
 }
</style>
</head>
<body id="mainBody">
	<div class="e8_box demo2">
	    <div class="tab_box">
			<div id="rightInfo">
				<div class="box" style="margin:0px;padding:0px;border-top:0px;">
					<div class="box-panel" style="border-top:0px;">
						<hr class="hr-normal">
						<div class="container-fluid box-content">
							<div class="col-xs-2">
								<c:if test="${map.url==''||map.url==null}">
									<img alt="头像" style="width: 75px;height:100px;" class="grtx" src="${pageContext.request.contextPath}/static/images/systemSet/grxx/man_wev8.png" >
								</c:if>
								<c:if test="${map.url!=''&&map.url!=null}">
									<img alt="头像" style="width: 75px;height:100px;" class="grtx" src="${pageContext.request.contextPath}/${map.url}" >
								</c:if>
							</div>
							<div class="col-xs-10">
								<div class="row">
									<div class="col-xs-5">
										<div class="input-group">
											<span style="font-weight:bold;">姓&emsp;&emsp;名：</span>${map.xm}
										</div>
									</div>
									<div class="col-xs-7">
										<div class="input-group">
											<span style="font-weight:bold;">工&emsp;&emsp;号：</span>${map.rygh}
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-5">
										<div class="input-group">
											<span style="font-weight:bold;">性&emsp;&emsp;别：</span>${map.xb}
										</div>
									</div>
									<div class="col-xs-7">
										<div class="input-group">
											<span style="font-weight:bold;">所在单位：</span>${map.dwmc}
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-5">
										<div class="input-group">
											<span style="font-weight:bold;">联系方式：</span>${map.lxdh}
										</div>
									</div>
									<div class="col-xs-7">
										<div class="input-group">
											<span style="font-weight:bold;">邮&emsp;&emsp;箱：</span>${map.mail}
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
        <div class="pull-right">
			<button type="button" class="btn btn-default btn-without-icon" id="btn_close">关闭</button>
        </div>
    </div>
<%@include file="/static/include/public-list-js.inc"%>
<script type="text/javascript">
$(function() {
	$("#btn_close").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
</body>
</html>