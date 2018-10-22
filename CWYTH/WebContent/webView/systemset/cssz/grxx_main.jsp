<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>个人选项设置-基本信息</title>
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
/*  #mainBody{
 	overflow: hidden !important;
 } */
</style>
</head>
<body id="mainBody" >
<div class="e8_box demo2">
			<div class='page-header' style="padding:0px;margin:0px;top:0px;">
		         <div class="e8_tablogo" id="e8_tablogo" style="margin-left: 6px; cursor: pointer;">
			       		<c:if test="${map.url==''||map.url==null}">
			       			<input type="hidden" class="txfont" value="上传头像">
			       			<img alt="头像" style="vertical-align:middle;width:43px;height:43px;" class="grtx" src="${pageContext.request.contextPath}/static/images/systemSet/grxx/icon_m_wev8.jpg">
						</c:if>
						<c:if test="${map.url!=''&&map.url!=null}">
							<input type="hidden" class="txfont" value="更换头像">
							<img alt="头像" style="width: 100%;height:43px;" class="grtx" src="${pageContext.request.contextPath}/${map.url}" >
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
<!-- 						<div class="e8_outbox" style="visibility: visible; top: 45px;"> -->
<!-- 							<div id="rightBox" class="e8_rightBox" style="width: 119px; visibility: visible; position: absolute;"> -->
<!-- 								<div id="tabcontentframe_box" class="_box"> -->
<!-- 									<input type="button" id="btn_bj" class="e8_btn_top_first" value="编辑" title="编辑" style="max-width: 100px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"> -->
<!-- 						 			<span title="菜单" class="cornerMenu middle"></span> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
				    </div>
				</div>
<!-- 				<div style="height:40px;"> -->
							
					   		<ul class="tab_menu" style="width: 1203px;">
					           <li class="current"  style="padding-left: 5px;">
					           <a href="${pageContext.request.contextPath}/grsz/goGrxxPage" id="jbxx">基本信息</a>
<!-- 					           <span class="e8_rightBorder">|</span> -->
					           </li>
<!-- 					           <li  style="padding-left: 5px;"> -->
<%-- 					           <a href="${pageContext.request.contextPath}/grsz/goGrWyspPage" id="wysp" >外语水平</a> --%>
<!-- 					           <span class="e8_rightBorder">|</span></li> -->
<!-- 					           <li  style="padding-left: 5px;"> -->
<%-- 					           <a href="${pageContext.request.contextPath}/grsz/goGrLwqkPage" id="lwqk" >论文情况</a> --%>
<!-- 					           <span class="e8_rightBorder">|</span></li> -->
<!-- 					           <li  style="padding-left: 5px;"> -->
<%-- 					           <a href="${pageContext.request.contextPath}/grsz/goGrJxqkPage" id="jxqk" >进修情况</a> --%>
<!-- 					           <span class="e8_rightBorder">|</span></li> -->
<!-- 					           <li  style="padding-left: 5px;"> -->
<%-- 					           <a href="${pageContext.request.contextPath}/grsz/goGrZzqkPage" id="zzqk" >著作情况</a> --%>
<!-- 					           <span class="e8_rightBorder">|</span></li> -->
<!-- 					           <li  style="padding-left: 5px;"> -->
<%-- 					           <a href="${pageContext.request.contextPath}/grsz/goGrCgjlPage" id="cgjl" >成果奖励</a> --%>
<!-- 					           </li> -->
					    	</ul>
				
<!-- 				</div> -->
		    </div>
	    <div class="tab_box">
			<table style="width: 100%;height:100%;margin-top: 86px;">
				<tr style="min-height:580px;">
					<td style="width: 251px;vertical-align: top;border-right:solid 1px gray;border-color: #E6E6E6;">
						<div id="leftInfo" style="min-height: 550px;padding-left: 10px;padding-right: 10px;padding-top: 10px;">
							<div style="">
								<c:if test="${map.url==''||map.url==null}">
									<img alt="头像" style="width: 100%;height:293px;" class="grtx" id="img_grtx" src="${pageContext.request.contextPath}/static/images/systemSet/grxx/man_wev8.png" >
								</c:if>
								<c:if test="${map.url!=''&&map.url!=null}">
									<img alt="头像" style="width: 100%;height:293px;cursor:pointer;" class="grtx" id="img_grtx" onclick="editImag()" title="点击编辑头像" src="${pageContext.request.contextPath}/${map.url}" >
								</c:if>
								<!-- <div id="divbg" style="position: absolute;border: 2px solid #ccc;margin-top:-50px;background-color: #CCCCCC;">
									<table style="width: 227px;height: 50px;text-align: center;vertical-align: middle;">
										<tr>
											
										</tr>
									</table>
								</div> -->
							</div>
							<div class="container-fluid box-content">
								<div class="row">
									<div class="col-md-12">
<!-- 					                	<input id="imageFile" name="imageFile[]" type="file" multiple class="file-loading"> -->
<!-- 										<div id="errorBlock" class="help-block"></div> -->
<!-- 										<span class="hidden-xs" id="changeImg">更改头像</span> -->
										<div>
										    <button type='button' style="margin-top: 7px;margin-left: 2px;float:left;" class="btn btn-primary" id="changeImg">更改头像</button>
										    <button type='button' style="margin-top: 7px;margin-left: 2px;float:left;" class="btn btn-primary" id="changeMM">修改密码</button>
<!-- 										<input type="hidden" id="initialPreview" value=""> -->
										</div>
					                </div>
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
									<td class="leftfont">状态：${map.RYZT}</td>
					       		</tr>
							</table>
						</div>
					</td>
					<td style="vertical-align: top;">
						<div id="rightInfo" style="min-height:630px;">
						<div class="box" style="margin:0px;padding:0px;border-top:0px;">
							<div class="box-panel" style="border-top:0px;">
								<div class="box-header clearfix">
					            	<div class="sub-title pull-left text-primary"><i class="fa icon-sangangy"></i>
					            		基本信息
					            	</div>
					            	<div class="actions">
					            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
					            	</div>
					        	</div>
								<hr class="hr-normal">
								<div class="container-fluid box-content">
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">身份证号</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<span class="">${map.sfzh}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">出生日期</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<span class="">${map.csrq}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">文化程度</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<span class="">${map.whcd}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">所学专业</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<span class="">${map.sxzy}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">人员类型</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<span class="">${map.zzbz}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">专业职称</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<span class="">${map.zyzc}</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-1">
											<div class="">
												<span class="">工作日期</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<span class="">${map.gzrq}</span>
											</div>
										</div>
									</div>
<!-- 									<div class="row"> -->
<!-- 										<div class="col-md-1"> -->
<!-- 											<div class="input-group"> -->
<!-- 												<span class="">调入日期</span> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 										<div class="col-md-9"> -->
<!-- 											<div class="input-group"> -->
<%-- 												<span class="">${map.drrq}</span> --%>
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
									<%-- <div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">单位领导</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<span class="">${map.ldxm}</span>
											</div>
										</div>
									</div> --%>
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">主要工作</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<span class="">${map.zygz}</span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="box-panel">
								<div class="box-header clearfix">
					            	<div class="sub-title pull-left text-primary"><i class="fa icon-sangangy"></i>
					            		通讯信息
					            	</div>
					            	<div class="actions">
					            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
					            	</div>
					        	</div>
								<hr class="hr-normal">
								<div class="container-fluid box-content">
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">联系方式</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<input type="text" id="txt_lxdh" class="form-control input-radius"  name="lxdh" value="${map.lxdh}" />
<%-- 												<span class="">${map.lxdh}</span> --%>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">QQ</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<input type="text" id="txt_qq" class="form-control input-radius"  name="qq" value="${map.qq}" />
<%-- 												<span class="">${map.qq}</span> --%>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">邮箱</span>
											</div>
										</div>
										<div class="col-md-9">
											<div class="input-group">
												<input type="text" id="txt_mail" class="form-control input-radius"  name="mail" value="${map.mail}" />
<%-- 												<span class="">${map.mail}</span> --%>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="box-panel">
								<div class="box-header clearfix">
					            	<div class="sub-title pull-left text-primary"><i class="fa icon-sangangy"></i>
					            		其他信息
					            	</div>
					            	<div class="actions">
					            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
					            	</div>
					        	</div>
								<hr class="hr-normal">
								<div class="container-fluid box-content">
									<div class="row">
										<div class="col-md-1">
											<div class="input-group">
												<span class="">每页行数</span>
											</div>
										</div>
										<div class="col-md-2">
											<div class="input-group">
												<input type="text" id="txt_rownums" class="form-control input-radius int text-right" name="rownums"  value="${map.ROWNUMS}"/>
											</div>
										</div>
										<div class="col-md-3">
											<div style="float: left;margin-top: 4px;">密保问题:</div>
											<div style="float: left;margin-left: 5px;" class="input-group">
												<input type="text" id="txt_mmwt" class="form-control input-radius" name="mmwt" value="${mapMmzh.mmwt}" placeholder="请输入密保问题"/>
											</div>
										</div>
										<div class="col-md-3">
											<div style="float: left;margin-top: 4px;">密保答案:</div>
											<div style="float: left;margin-left: 5px;" class="input-group">
												<input type="text" id="txt_mmda" class="form-control input-radius" name="mmda" value="${mapMmzh.mmda}" placeholder="请输入答案"/>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
								    <hr class="hr-normal">
									<div class="anzy" style="text-align:center">
										<button class="btn btn-default" id="btn_save" style="background-color: #00acec;color: white;">
											<i class="fa icon-save" style="color:white"></i>
											保存
										</button>
										<button class="btn btn-default" id="btn_back" style="background-color: #00acec;color: white;">
											<i class="fa icon-back" style="color:white"></i>
											返回
										</button>
									</div>
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
			select_commonWin("${pageContext.request.contextPath}/grsz/goPersonSetPage","个人信息","920","630","yes");
		});
	//返回
	$("#btn_back").click(function(){
		parent.location.href=document.referrer;
	});
		//验证方法
		var _validate = $('#rightInfo').bootstrapValidator({fields:{
	            mail:{validators:{emailAddress:{message: '请输入有效的E-mail地址'}}},
	            lxdh:{validators:{phone:{message: '请输入有效的联系电话'}}}
			}	
		});
		//保存按钮
		$("#btn_save").click(function(){
			var index;
			var valid;
			if(_validate){
				_validate.bootstrapValidator("validate");
				valid = $('#rightInfo').data('bootstrapValidator').isValid();
			}else{
				valid = true;
			}
			if(valid){
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/index/doSaveSzxx",
					data:"rownums="+$("#txt_rownums").val()+"&lxdh="+$("#txt_lxdh").val()+"&qq="+$("#txt_qq").val()+"&mail="+$("#txt_mail").val()+"&mmwt="+$("#txt_mmwt").val()+"&mmda="+$("#txt_mmda").val(),
					success:function(val){
						close(index);
						var data = JSON.getJson(val);
						alert(data.msg);
					},
					error:function(val){
						close(index);
						alert(getPubErrorMsg());
					},
					beforeSend:function(val){
						index = loading(2);
					}
				});
			}
		});
		$('#changeImg').click(function(){
			editImag();
		});
		$("#changeMM").click(function(){
			select_commonWin("${pageContext.request.contextPath}/index/goSzxxPage?flag=MM","修改密码","450","550");
		});
	    $("#imageFile").prev("span.hidden-xs").html($(".txfont").val());
	    $(".glyphicon-folder-open").remove();
});
var editImag = function(){
	select_commonWin("${pageContext.request.contextPath}/webView/systemset/cssz/imageMain.jsp?rybh=${rybh}&url="+$("#img_grtx").attr("src")+"&h="+$("#img_grtx").height()+"&w="+$("#img_grtx").width(),$(":hidden.txfont").val(),"1000","650","yes");
}
</script>
</body>
</html>