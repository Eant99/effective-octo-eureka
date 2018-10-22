<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>账套详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.addBtn {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.addBtn:after {
	/* 		2722 */
	content: "+";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 17px;
	position: relative;
	cursor: pointer;
}

.deleteBtn {
	text-align: center;
	width: 26px;
	height: 26px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
}

.deleteBtn:after {
	/* 		2014 */
	content: "\2014";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 1em;
	position: relative;
	cursor: pointer;
	top: 3px;
}

.btn_td {
	width: 14mm !important;
	height: 6mm !important;
}

.selWindow {
	width: 280px !important;
}

.number, .sz {
	text-align: right;
}
</style>
</head>
<body>
<%
SimpleDateFormat ztnd = new SimpleDateFormat("yyyy");
String date = ztnd.format(new Date());
%>
	<form id="myform" class="form-horizontal" action="" method="post">
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<input type="hidden" id="txt_guid" name="guid" value="${dwb.guid}"> 
		<input type="hidden" name="czr"	value="${loginId}">
		<div class="page-header">
			<div class="pull-left">
				<span class="page-title text-primary"><i
					class='fa icon-xiangxixinxi'></i>编辑账套信息</span>
			</div>
			<div class="pull-right">
				<button type="button" class="btn btn-default" id="btn_save">
					<i class="fa icon-save"></i>保存
				</button>
				<button type="button" class="btn btn-default" id="btn_back">返回</button>
			</div>
		</div>
		<div class="box" style="top: 36px">
			<div class="box-panel">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i>账套信息
					</div>
					<div class="actions">
						<a href="#" class="btn box-collapse btn-mini btn-link"><i
							class="fa"></i></a>
					</div>
				</div>
				<hr class="hr-normal">
				<div class="container-fluid box-content">
					<div class="row">
						<div class="col-md-6">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>账套名称</span>
								<input type="text" id="txt_ztmc" name="ztmc"
									class="form-control input-radius"
									style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"
									value="${dwb.ZTMC}" />
							</div>
						</div>
				<c:choose>
					<c:when test="${operateType=='C' }">
					<div class="col-md-6" align="center">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>会计年度</span>
							<input type="text" id="txt_kjnd" class="form-control input-radius year"  name="kjnd" value="<%=date%>" data-format="yyyy"/>
							 <span class='input-group-addon'>
                           	<i class="glyphicon glyphicon-calendar"></i> 
                       		 </span>
						</div>
					</div>
					</c:when>
					<c:otherwise>
					<div class="col-md-6" align="center">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>会计年度</span>
							<input type="text" id="txt_kjnd" class="form-control input-radius year" name="kjnd" value="${dwb.kjnd }" data-format="yyyy"/>
							 <span class='input-group-addon'>
                           	<i class="glyphicon glyphicon-calendar"></i> 
                       		 </span>
						</div>
					</div>
					</c:otherwise>
					</c:choose>
					<c:choose>
					<c:when test="${operateType=='C' }">
						<div class="col-md-6">
							<div class="input-group">
								<span class="input-group-addon"><span class="required"></span>期间数</span>
								<input type="text" id="txt_qjs" name="qjs"
									class="form-control input-radius"
									style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;text-align:right"
									value="12" />
							</div>
						</div>
					
					</c:when>
					<c:otherwise>
					  <div class="col-md-6">
							<div class="input-group">
								<span class="input-group-addon"><span class="required"></span>期间数</span>
								<input type="text" id="txt_qjs" name="qjs"
									class="form-control input-radius"
									style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;text-align:right"
									value="${dwb.qjs}" />
							</div>
						</div>
					</c:otherwise>
					</c:choose>
					</div>
					<div class="row">
						<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>启用日期</span>
							<input type="text" id="txt_kssj" class="form-control input-radius date" name="qyrq" data-format="yyyy-mm-dd -hh-mm-ss" value="${dwb.qyrq}"/>
						</div>
					</div>
					</div>
				</div>
			</div>
		</div>

		</div>
		</div>
	</form>
	<%@include file="/static/include/public-manager-js.inc"%>
	<script type="text/javascript">
		$(function() {
			//必填验证
			var validate = $('#myform').bootstrapValidator({fields:{
				ztmc:{validators:{notEmpty:{message:'不能为空'}}},
				kjnd:{validators:{notEmpty: {message: '不能为空'}}},
				qyrq:{validators:{notEmpty: {message: '不能为空'}}},
         }});

			//验证方法
			var validate = $('#myform').bootstrapValidator({
				fields : {
					mbbh : {
						validators : {
							notEmpty : {
								message : '模板编号不能为空'
							}
						}
					},
					mbnr : {
						validators : {
							notEmpty : {
								message : '模板内容不能为空'
							}
						}
					}
				}
			});
			//验证账套名称是否存在
			$("#txt_ztmc").blur(function(){
 				var ztmc = $("#txt_ztmc").val();
 				var guid = $("[name=guid]").val();
				console.log("ztmc+++++++"+ztmc);
 				$.ajax({
 					url:"${ctx}/ztxx/doSelect",
 		   			data:"ztmc="+ztmc+"&guid="+guid,
		   			type:"post",
		   			dataType:"json",
 		   			//async:"false",
 		   			success:function(val){		
		   				console.log("val++++++++++"+val);
		   				if(val==false){
		   					alert("该账套名称已存在");   
		   				    $("#txt_ztmc").val("");
		   				}	   				
 		   			},
 					error : function(val) {
						
					}
		   		}); 	
			});
			//保存按钮
			$("#btn_save").click(function(e){
				doSave(validate,"myform","${ctx}/ztxx/doSave?operateType=C",function(val){
					if(val.success){
						alert(val.msg);
// 						parent.location.reload(true);
						window.location.href  = "${ctx}/ztxx/goztxxListPage";
					}
				});
			});	

			function doSave1(_validate, _formId, _url, _success, _fail) {
				var index;
				var valid;
				if (_validate) {
					_validate.bootstrapValidator("validate");
					valid = $('#' + _formId).data('bootstrapValidator')
							.isValid();
				} else {
					valid = true;
				}
				if (valid) {
					$.ajax({
						type : "post",
						url : _url,
						data : arrayToJson($('#' + _formId).serializeArray()),
						success : function(val) {
						},
						error : function(val) {
							alert("保存成功！");
						}
					});
				}
			}
			//返回按钮
			$("#btn_back")
					.click(
							function() {
								window.location.href = "${ctx}/webView/kjhs/pzsz/ztxx_list.jsp";
							});
			//刷新按钮
			$(".reload").click(
					function() {
						var operateType = $("[name='operateType']").val();
						if (operateType == 'U') {
							window.location.href = window.location.href
									+ "&operateType=U&rybh=${ryb.rybh}"
						} else {
							var url = window.location.href;
							if (url.indexOf("?") > 0) {
								window.location.href = window.location.href
										+ "&gxgdzc_uuid=googosoft2016"
							} else {
								window.location.href = window.location.href
										+ "?gxgdzc_uuid=googosoft2016"
							}
						}
					});
	
			//科目编号弹框
			$(document).on(
					"click",
					"[id^=btn_kmbh]",
					function() {
						var controlId = $(this).attr("sj");

						select_commonWin("${ctx}/pzmb/window?&controlId="
								+ controlId, "科目信息", "920", "630");
					});
			//新增按钮
			var num = 2;
			$(document).on("click", "[class=addBtn]", function() {
				var $parentTr = $(this).parents("tr").clone();
				$(this).removeClass("addBtn");
				$(this).addClass("deleteBtn");
				$parentTr.find(":input").val("");
				$parentTr.attr("id", "tr_" + num);
				$parentTr.find("[id^=txt_kmbh]").attr({
					"name" : "kmbh" + num,
					"id" : "txt_kmbh" + num
				});
				$parentTr.find("[id^=btn_kmbh]").attr({
					"id" : "btn_kmbh" + num,
					"sj" : "txt_kmbh" + num
				});
				$parentTr.find("[id^=btn_kmbh]").attr({
					"id" : "btn_kmbh" + num,
					"sj" : "txt_kmbh" + num
				});
				$parentTr.find("[id^=txt_kmmc]").attr({
					"name" : "kmmc" + num,
					"id" : "txt_kmmc" + num
				});
				num++;
				$(this).parents("tr").after($parentTr);
			});
			//删除按钮
			$(document).on("click", "[class=deleteBtn]", function() {
				$(this).parents("tr").remove();
			});
		});
	</script>
</body>
</html>