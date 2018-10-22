<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${sysname}-登录界面</title>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" /><!--默认，移动先行-->
<style type="text/css">
#logoImage {
 
    top: 31px !important;
    margin-left: 96px !important;
        font-size: 50px;
        letter-spacing:8px;
 
}
</style>
<script type="text/javascript">
getOs();
function getOs(){
 	var browser=navigator.appName;
	var b_version=navigator.appVersion;
	var version=b_version.split(";");
	var trim_Version=version[1].replace(/[ ]/g,"");
 	if(browser=="Microsoft Internet Explorer"){	
 		var flag = "${flag}";
		if(flag!="t"){
			if(trim_Version=="MSIE9.0"){
				location.href= "${pageContext.request.contextPath}/browser.jsp?num=9";
			}else if(trim_Version=="MSIE8.0"){
				location.href= "${pageContext.request.contextPath}/browser.jsp?num=8";
			}else if(trim_Version=="MSIE7.0"){
				location.href= "${pageContext.request.contextPath}/browser.jsp?num=7";
			}else if(trim_Version=="MSIE6.0"){
				location.href= "${pageContext.request.contextPath}/browser.jsp?num=6";
			}
 		}
 	}
}
</script>
</head>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/app_bg.png" type="image/x-icon"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" /><!--必须，前端框架的根本样式文件-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/login/camera.css" /><!--首页幻灯片样式-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/login/login.css" /><!--登录专用样式文件-->
<link href="${pageContext.request.contextPath}/static/plugins/layer/skin/layer.css" rel="stylesheet"/><!-- 弹窗相关 -->
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script><!--jquery-->
<script src="${pageContext.request.contextPath}/static/plugins/layer/layer.min.js"></script><!--弹窗-->
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101440674" data-redirecturi="http%3A%2F%2F192.168.10.111%3A8088%2FFrame%2Fafterlogin.do" charset="utf-8" ></script>
<body>
	<div style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
		<div id="logoImage" >${sysname}</div>
		<div id="login_box">
			<div id="d_yhdl" class="rightBox">
				<div id="logo">
					<h4>用户登录</h4>
				</div>
				<div class="input-group">
					<span class="input-group-addon"><i class="fa icon-admin"></i></span>
					<input type="text" class="form-control form-error" id="username" placeholder="${dlfsmc}" data-container="body" value="${username}" data-toggle="popover" data-trigger="manual" data-placement="right" data-content="用户名不存在">
					<input type="hidden" value="${dlfs}" id="dlfs">
				</div>
				<div class="input-group">
					<span class="input-group-addon"><i class="fa icon-mima"></i></span>
					<input type="password" class="form-control" id="password" placeholder="密码">
				</div>
				<div class="input-group">
					<span class="input-group-addon"><i class="fa icon-yanzheng"></i></span>
					<input type="text" class="form-control" id="verifycode" placeholder="验证码">
					<span class="codeimg"><img id="codeImg" alt="点击更换" title="点击更换" width="68px" height="27px" src="${pageContext.request.contextPath}/static/login/images/loading.png"/></span>
				</div>
				<div class="buttons">
					<button type="button" id="submit" class="btn btn-primary btn-block">登录</button>
				</div>
				<div class="form-group text-left" style="float: left;width:100%">
					<label ><input class="" type="checkbox" name="rememberMe" id="chk_rememberMe" style="vertical-align: text-top;" ${remenberme}>记住用户名</label>
					<%-- 暂时屏蔽--%>
<!-- 					&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp; -->
					
					<span id="forgetPassword" style="margin-left:30%;">忘记密码？</span>
					<label ><img src="${pageContext.request.contextPath}/static/login/images/llqts2.png" style="width:15px; height:15px" />&ensp;<a  href="javascript:void(0);"  id="btn_down" style="color:white;" >建议使用谷歌浏览器</a></label>
<!-- 					<span style="color:white;">|</span> -->
<!-- 					<span id="notice">注意事项？</span> -->
				</div>
				<img id="d_ewmqh" style="cursor:pointer;position:absolute;bottom:0px;right:0px;" title="切换为扫码登录" alt="" src="${pageContext.request.contextPath}/static/login/images/ewmRB.png">
			</div>
			 <div id="d_smdl" class="rightBox" style="display:none;">
				<div id="logo">
					<h4>扫码登录</h4>	
				</div>
				<div style="height:30px;line-height:20px;color:#ffffff;margin-left:-50px;margin-right:-50px;">请使用手机app中的“扫码登录”扫描下方二维码！</div>
				<div id="QRcode" style="height: 210px;"></div>
				<img id="pc" style="cursor:pointer;position:absolute;bottom:0px;right:0px;" title="切换为用户登录" alt="" src="${pageContext.request.contextPath}/static/login/images/pc.png">
			</div>  
			
			<div id="d_zhmm" class="rightBox" style="display:none;height:300px;">
				<div id="logo" style="margin-top: 25px;margin-bottom:28px;">
						<h4>找回密码</h4>	
				</div>
				<div style="height: 310px;">
					<div class="input-group" style="margin-bottom: 37px;">
						<span class="input-group-addon"><i class="fa icon-xmm" style="font-size: 12px;">教职工号</i></span>
						<input type="password" class="form-control" id="xmm" placeholder="" >
					</div>
					<div class="input-group" style="margin-bottom: 37px;">
						<span class="input-group-addon"><i class="fa icon-qrmm" style="font-size: 12px;">身份证号</i></span>
						<input type="password" class="form-control" id="qrmm" placeholder="" >
					</div>
					<div class="input-group" style="margin-top: 0px;">
						<div class="row">
							<div class="col-md-3">
								<div class="input-group">
<!-- 									<button type="button" id="submit" class="btn btn-primary btn-block">登录</button> -->
									<button type="button" class="btn btn-primary " id="zhmm_back" style="width: 138px;height: 30px;border: 1px sold white;"><font style="color: white;">发送邮件</font></button>
								</div>
							</div>
							<div class="col-md-3">
								<div class="input-group">
									<button type="button" class="btn btn-primary" id="zhmm_save" style="width:138px;height: 30px;margin-left: 105px;border: 1px sold white;"><font style="color: white;">发送短信</font></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer" style="color:white;">
		
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;技术支持：${jszc}</span>
	</div>
	<div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide"> 
		<c:choose>
			<c:when test="${not empty bglist}">
				<c:forEach var="bglist" items="${bglist}">
					<div data-src="${pageContext.request.contextPath}/${bglist.PATH}"></div>
				</c:forEach>
			</c:when> 
			<c:otherwise>
				<div data-src="${pageContext.request.contextPath}/static/login/images/login_container_bg_11.jpg"></div>
				<div data-src="${pageContext.request.contextPath}/static/login/images/login_container_bg_12.jpg"></div>
				<div data-src="${pageContext.request.contextPath}/static/login/images/login_container_bg_13.jpg"></div>
				<div data-src="${pageContext.request.contextPath}/static/login/images/login_container_bg_14.jpg"></div>
				<div data-src="${pageContext.request.contextPath}/static/login/images/login_container_bg_15.jpg"></div>
				<div data-src="${pageContext.request.contextPath}/static/login/images/login_container_bg_16.jpg"></div>
				
			</c:otherwise> 
		</c:choose>
		</div>
	</div>
<script src="${pageContext.request.contextPath}/static/login/js/jquery.easing.1.3.js"></script><!--幻灯片-->
<script src="${pageContext.request.contextPath}/static/javascript/bootstrap/bootstrap.min.js"></script><!--幻灯片-->
<script src="${pageContext.request.contextPath}/static/login/js/jquery.mobile.customized.min.js"></script><!--幻灯片-->
<script src="${pageContext.request.contextPath}/static/login/js/camera.js"></script><!--幻灯片-->
<script src="${pageContext.request.contextPath}/static/login/js/templatemo_script.js"></script><!--目前还没深究这个文件的作用-->
<script src="${pageContext.request.contextPath}/static/javascript/public/public-json.js"></script><!--解析JSON工具类-->
<script src="${pageContext.request.contextPath}/static/plugins/validator/bootstrapValidator.js"></script>

<script src="${pageContext.request.contextPath}/static/javascript/public/download.js"></script><!-- 下载功能的js -->
<c:if test="${appDisplay == '1'}">
<script type="text/javascript">
$(function(){
	
	//下载浏览器
	$("#btn_down").click(function(){
          FileDownload("${pageContext.request.contextPath}/file/fileDownload", "谷歌浏览器.exe", "soft\\谷歌浏览器.exe");
	});
	
	$("#d_ewmqh").show();
	
	$.ajax({
		type:"post",
		data:{"QRKey":"${QRKey}"},
		url:"${pageContext.request.contextPath}/phone/LoginCreateQRCode",
		success:function(val){
			//$("#QRcode").prop("innerHTML","<div id='txt_CLOUMN' style='height: 200px; width: 200px; position:absolute; top: 75px; left: 80px;z-index: 5;text-align:center'><img src='" + val + "' style='width:100%'/></div>");
			$("#QRcode").prop("innerHTML","<img src='" + val + "' style='width:180px;heigth:180px;'/>");
		}
	});
});

var echo_websocket;
var wsUri = "${websocket_uri}?fromUser=${QRKey}";
echo_websocket = new WebSocket(wsUri);
echo_websocket.onopen = function(evt) {
	console.log("WebSocket Connected !");
};
echo_websocket.onmessage = function(evt){
	$.ajax({
		type:"post",
		data:{"rybh":evt.data},
		url:"${pageContext.request.contextPath}/login/LoginByRybh",
		success:function(val){
			if(val == "true"){
				window.location.href = "${pageContext.request.contextPath}/index/login_toIndex";
			}
			else{
				alert(val);
			}
		}
	});
};
echo_websocket.onerror = function(evt) {
	console.log("ERROR:" + evt.data);
	echo_websocket.close();
};
window.addEventListener("beforeunload", destroy, false);
function destroy(){
	console.log("WebSocket Close !");
	echo_websocket.close();
}
</script>
</c:if>
<script src="${pageContext.request.contextPath}/static/login/js/login.js"></script>
<script type="text/javascript">
	var path = "${pageContext.request.contextPath}";
	if("${licenseInfo}"!=""){
		alert("${licenseInfo}");
		closeWebPage();
	}
</script>
</html>