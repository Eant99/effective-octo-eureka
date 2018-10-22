<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>个人选项设置-外语水平</title>
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
<body id="mainBody" scroll="no" >
<div class="e8_box demo2">
			<div class='page-header' style="padding:0px;margin:0px;top:0px;">
		         <div class="e8_tablogo" id="e8_tablogo" style="margin-left: 6px; cursor: pointer;">
			       		<c:if test="${map.url==''||map.url==null}">
			       			<input type="hidden" class="txfont" value="上传头像">
			       			<img alt="头像" style="vertical-align:middle;width:43px;height:43px;" class="grtx" src="${pageContext.request.contextPath}/static/images/systemSet/grxx/icon_m_wev8.jpg">
						</c:if>
						<c:if test="${map.url!=''&&map.url!=null}">
							<input type="hidden" class="txfont" value="更换头像">
							<img alt="头像" style="width: 100%;height:43px;" class="grtx"  src="${pageContext.request.contextPath}/${map.url}" >
						</c:if>
			       		
			        </div>
					<div class="e8_ultab">
						<div class="e8_navtab" id="e8_navtab">
							<span id="objName">
								<font style="font-size: 16px">${map.xm}(</font>
							<font style="font-size:12px;">${map.xb}</font><font style="font-size: 16px">)</font>
							</span>
						</div>
						<div class="dw">
							<span >
							<font style="font-size:12px;">${map.dwmc}
								</font>
							</span>
				    </div>
				</div>
					   		<ul class="tab_menu" style="width: 1203px;">
					           <li   style="padding-left: 5px;">
					           <a href="${pageContext.request.contextPath}/grsz/goGrxxPage" id="jbxx">基本信息</a>
					           <span class="e8_rightBorder">|</span></li>
					           <li  class="current" style="padding-left: 5px;">
					           <a href="${pageContext.request.contextPath}/grsz/goGrWyspPage" id="wysp" >外语水平</a>
					           <span class="e8_rightBorder">|</span></li>
					           <li  style="padding-left: 5px;">
					           <a href="${pageContext.request.contextPath}/grsz/goGrLwqkPage" id="lwqk" >论文情况</a>
					           <span class="e8_rightBorder">|</span></li>
					           <li  style="padding-left: 5px;">
					           <a href="${pageContext.request.contextPath}/grsz/goGrJxqkPage" id="jxqk" >进修情况</a>
					           <span class="e8_rightBorder">|</span></li>
					           <li  style="padding-left: 5px;">
					           <a href="${pageContext.request.contextPath}/grsz/goGrZzqkPage" id="zzqk" >著作情况</a>
					           <span class="e8_rightBorder">|</span></li>
					           <li  style="padding-left: 5px;">
					           <a href="${pageContext.request.contextPath}/grsz/goGrCgjlPage" id="cgjl" >成果奖励</a>
					           </li>
					    	</ul>
		    </div>
	    <div class="tab_box">
			<table style="width: 100%;height:100%;margin-top: 86px;">
				<tr style="min-height:580px;">
					<%-- <td style="width: 251px;vertical-align: top;border-right:solid 1px gray;border-color: #E6E6E6;">
						<div id="leftInfo" style="min-height: 550px;padding-left: 10px;padding-right: 10px;padding-top: 10px;">
							<div style="">
								<c:if test="${map.url==''||map.url==null}">
									<img alt="头像" style="width: 100%;height:293px;" class="grtx" src="${pageContext.request.contextPath}/static/images/systemSet/grxx/man_wev8.png" >
								</c:if>
								<c:if test="${map.url!=''&&map.url!=null}">
								<img alt="头像" style="width: 100%;height:293px;" class="grtx"  src="${pageContext.request.contextPath}/${map.url}" >
								</c:if>
								<div id="divbg" style="position: absolute;border: 2px solid #ccc;margin-top:-50px;background-color: #CCCCCC;">
									<table style="width: 227px;height: 50px;text-align: center;vertical-align: middle;">
										<tr>
											<td><a href="javascript:void(0);" onclick="openmessage();return false;" title="发送短信"><img src="${pageContext.request.contextPath}/static/images/systemSet/grxx/msn_wev8.png" ></a></td>
											<td><a href="javascript:void(0);" onclick="openemail();return false;" title="发送邮件"><img src="${pageContext.request.contextPath}/static/images/systemSet/grxx/email_wev8.png" ></a></td>
											<td><a href="javascript:void(0);" onclick="doAddWorkPlan();return false;" title="新建日程"><img src="${pageContext.request.contextPath}/static/images/systemSet/grxx/workplan_wev8.png" ></a></td>
											<td><a href="javascript:void(0);" onclick="doAddCoWork();return false;" title="新建协作"><img src="${pageContext.request.contextPath}/static/images/systemSet/grxx/cowork_wev8.png" ></a></td>
<!-- 											<td><a href="javascript:void(0);" onclick="doAddCoWork();return false;" title="领用人建账"><i class="fa icon-shouhuoruku xtb" ></i></a></td> -->
<!-- 											<td><a href="javascript:void(0);" onclick="doAddCoWork();return false;" title="单位间调拔申请"><i class="fa icon-shujutubiao17 xtb" ></i></a></td> -->
<!-- 											<td><a href="javascript:void(0);" onclick="doAddCoWork();return false;" title="处置申请"><i class="fa icon-17feiqi xtb" ></i></a></td> -->
<!-- 											<td><a href="javascript:void(0);" onclick="doAddCoWork();return false;" title="维修申请"><i class="fa icon-weixiu xtb" ></i></a></td> -->
										</tr>
									</table>
								</div>
							</div>
							<table style="width:100%;padding-right: 10px;padding-left: 10px;">
								<tr>
									<td class="leftfont">姓名：${map.xm}</td>
					       		</tr>   
					       		<tr>
									<td class="leftfont">性别：${map.xb}</td>
					       		</tr>      	
					       		<tr>
									<td class="leftfont">工号：${map.rygh}</td>
					      	 	</tr>
					      	 	<tr>
									<td class="leftfont">单位：${map.dwmc}</td>
					      	 	</tr>
								<tr>
									<td class="leftfont">状态：${map.ryzt}</td>
					       		</tr>
							</table>
						</div>
					</td> --%>
					<td style="vertical-align: top;">
						<div id="rightInfo" style="min-height:630px;">
						<div class="box" style="margin:0px;padding:0px;border-top:0px;">
							<div class="box-panel" style="border-top:0px;">
								<div class="box-header clearfix">
					            	<div class="sub-title pull-left text-primary">
					            		<i class="fa icon-sangangy"></i>
					            		外语水平
					            	</div>
					            	<div class="actions">
					            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
					            	</div>
					        	</div>
								<hr class="hr-normal">
								<div class="container-fluid box-content">
								<c:if test="${wysp.size() !=0}">
								    <div class="row"  style="font-weight:bold;">
								        <div class="col-md-2">
											<div class="input-group">
												<span class="">语种</span>
											</div>
										</div>
										<div class="col-md-10">
											<div class="input-group">
												<span class="">水平</span>
											</div>
										</div>
								    </div>
								<c:forEach var="wysp" items="${wysp}" varStatus="i" >
									<div class="row">
										<div class="col-md-2">
											<div class="input-group">
												<span class="">${wysp.yz}</span>
											</div>
										</div>
										<div class="col-md-10">
											<div class="input-group">
												<span class="">${wysp.sp}</span>
											</div>
										</div>
									</div>
								</c:forEach>
								</c:if>
								<c:if test="${wysp.size() ==0}">
								<div class="row">
										<div class="col-md-2"  style="font-weight:bold;">
											<div class="input-group">
												<span class="">&emsp;暂无信息！</span>
											</div>
										</div>
									</div>
								</c:if>
								</div>
							</div>
						</div>
						</div>
					</td>
				</tr>
		  </table>
		</div>
	</div>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js" type="text/javascript"></script>
<script>
$(function () {
	//跳转个人设置
		$("#btn_bj").click(function(){
			select_commonWin("${pageContext.request.contextPath}/grsz/goPersonSetPage","个人信息","500","580","yes");
		});
		
	    $("#imageFile").prev("span.hidden-xs").html($(".txfont").val());
	    $(".glyphicon-folder-open").remove();
});
</script>
</body>
</html>