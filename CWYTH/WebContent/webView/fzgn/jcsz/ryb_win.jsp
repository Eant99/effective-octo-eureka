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
</style>
</head>
<body id="mainBody">
<div class="e8_box demo2">
	    <div class="tab_box">
			<table style="width: 100%;height:100%;">
				<tr>
					<%-- <td style="width: 100px;vertical-align: top;border-right:solid 1px gray;border-color: #E6E6E6;">
						<div id="leftInfo" style="padding-left: 10px;padding-right: 10px;padding-top: 10px;">
							<div style="width: 100px;">
								<c:if test="${map.url==''||map.url==null}">
									<img alt="头像" style="width: 100%;height:150px;" class="grtx" src="${pageContext.request.contextPath}/static/images/systemSet/grxx/man_wev8.png" >
								</c:if>
								<c:if test="${map.url!=''&&map.url!=null}">
								<img alt="头像" style="width: 100%;height:150px;" class="grtx"  src="${pageContext.request.contextPath}/${map.url}" >
								</c:if>
								<div id="divbg" style="position: absolute;border: 2px solid #ccc;margin-top:-50px;background-color: #CCCCCC;">
									<table style="width: 97px;height: 15px;text-align: center;vertical-align: middle;">
										<tr>
 											<td><a href="javascript:void(0);" onclick="openmessage();return false;" title="发送短信"><img src="${pageContext.request.contextPath}/static/images/systemSet/grxx/msn_wev8.png" ></a></td> 
											<td><a href="javascript:void(0);" onclick="openemail();return false;" title="发送邮件"><img src="${pageContext.request.contextPath}/static/images/systemSet/grxx/email_wev8.png" ></a></td> 
 											<td><a href="javascript:void(0);" onclick="doAddWorkPlan();return false;" title="新建日程"><img src="${pageContext.request.contextPath}/static/images/systemSet/grxx/workplan_wev8.png" ></a></td>
											<td><a href="javascript:void(0);" onclick="doAddCoWork();return false;" title="新建协作"><img src="${pageContext.request.contextPath}/static/images/systemSet/grxx/cowork_wev8.png" ></a></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						<hr style="margin-top: -20px;position:relative;">
					</td> --%>
					<td style="vertical-align: top;">
						<div id="rightInfo">
						<div class="box" style="margin:0px;padding:0px;border-top:0px;">
							<div class="box-panel" style="border-top:0px;">
								<div class="box-header clearfix">
					            	<div class="sub-title pull-left text-primary">
					            		<i class="fa icon-sangangy"></i>
					            		人员基本信息
					            	</div>
					        	</div>
								<hr class="hr-normal">
								<div class="container-fluid box-content">
									<div class="row">
										<div class="col-xs-3">
											<div class="input-group">
												<span class="">姓名</span>
											</div>
										</div>
										<div class="col-xs-9">
											<div class="input-group">
												<span class="">${map.xm}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-3">
											<div class="input-group">
												<span class="">性别</span>
											</div>
										</div>
										<div class="col-xs-9">
											<div class="input-group">
												<span class="">${map.xb}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-3">
											<div class="input-group">
												<span class="">工号</span>
											</div>
										</div>
										<div class="col-xs-9">
											<div class="input-group">
												<span class="">${map.rygh}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-3">
											<div class="input-group">
												<span class="">所在单位</span>
											</div>
										</div>
										<div class="col-xs-9">
											<div class="input-group">
												<span class="">${map.dwmc}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-3">
											<div class="input-group">
												<span class="">单位领导</span>
											</div>
										</div>
										<div class="col-xs-9">
											<div class="input-group">
												<span class="">${map.ldxm}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-3">
											<div class="input-group">
												<span class="">联系方式</span>
											</div>
										</div>
										<div class="col-xs-9">
											<div class="input-group">
												<span class="">${map.lxdh}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-3">
											<div class="input-group">
												<span class="">邮箱</span>
											</div>
										</div>
										<div class="col-xs-9">
											<div class="input-group">
												<span class="">${map.mail}</span>
											</div>
										</div>
									</div>
									<div class="row" style="height: 30px;"></div>
								</div>
							</div>
						</div>
						</div>
					</td>
				</tr>
		  </table>
		</div>
	</div>
	<div class='page-bottom clearfix'>
		<!-- 保存和返回按钮 开始 -->
        <div class="pull-right">
			<button type='button' class="btn btn-default btn-without-icon" id="btn_close">关闭</button>
        </div>
        <!-- 保存和返回按钮 结束 -->
    </div>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js" type="text/javascript"></script>
<script>
$(function () {
	$("#btn_close").click(function(){
// 		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
</body>
</html>