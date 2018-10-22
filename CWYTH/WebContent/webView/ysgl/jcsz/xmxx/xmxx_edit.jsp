<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.number1{
  text-align:right;
}
<%
	String date = Constant.MR_YEAR();
	%>
.test1::before {
	content: "正常" ! important;
}

.test1::after {
	content: "禁用" ! important;
}
.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	}
</style>
</head>
<body>
	<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden"  name="guid" value="${guid}"/>
		<div class='page-header'>
			<div class="pull-left">
				<span class="page-title text-primary"> <i
					class='fa icon-xiangxixinxi'></i>编辑项目信息
				</span>
			</div>
			<div class="pull-right">
				<button type="button" class="btn btn-default" id="btn_save">
					<i class="fa icon-save"></i>保存
				</button>
				<button type="button" class="btn btn-default" id="btn_back">返回</button>
			</div>
		</div>
		<div class="box">
			<div class="box-panel">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i>项目信息
					</div>
					<div class="actions">
						<a href="#" class="btn box-collapse btn-mini btn-link"><i
							class="fa"></i></a>
					</div>
				</div>
				<hr class="hr-normal">
				<div class="container-fluid box-content">
					<div class="row">
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>项目编号</span>
								<c:if test="${operateType=='C'}">
								<input type="text" id="txt_bmh" class="form-control input-radius" readonly name="xmbh"  value="系统自动生成" />
								</c:if>
								<c:if test="${operateType=='U'}">
								<input type="text" id="txt_bmh" class="form-control input-radius" readonly readonly name="xmbh"  value="${dwb.xmbh }" />
								</c:if>
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>项目名称</span>
								<input type="text" id="txt_bmh"
									class="form-control input-radius" name="xmmc" value="${dwb.xmmc}" />
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>上级项目</span>
							<input type="text" id="txt_sjxm" readonly="readonly" class="form-control input-radius window" name="sjxm" value="${dwb.sjxm}">
							<span class="input-group-btn"><button type="button" id="btn_sjxm" class="btn btn-link btn-custom">选择</button></span>
						</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>所属项目分类</span>
								<c:if test="${operateType=='C'}">
								<select id="txt_xmfl" class="form-control input-radius select2"	name="ssxmfl" >
									<option value="">未选择</option>
									<c:forEach var="item" items="${ssxmfl}">
										<option value="${item.DM}" >${item.MC}</option>
									</c:forEach>
								</select>
								</c:if>
								<c:if test="${operateType=='U'}">
								<select id="txt_xmfl" class="form-control input-radius select2"	name="ssxmfl" value="${dwb.ssxmfl}">
									
									<c:forEach var="item" items="${ssxmfl}">
										<option value="${item.DM}" >${item.MC}</option>
									</c:forEach>
								</select>
								</c:if>
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>提醒百分比</span>
								<input type="text" id="txt_bmh"
									class="form-control input-radius number" style="text-align:right" name="txbfb" value="${dwb.txbfb}" />
							</div>
						</div>
						
						<div class="col-md-4">
						<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>项目年度</span>
					 <c:if test="${operateType=='C'}"> 
						<input type="text" id="txt_nd" class="form-control input-radius year" name="xmnd" value="<%=date %>" data-format="yyyy" />
					 </c:if> 
					 <c:if test="${operateType=='U'}">
						<input type="text" id="txt_nd" class="form-control input-radius year" name="xmnd" value="${dwb.xmnd}" data-format="yyyy" />
					</c:if>
					
				</div>
						</div>
					
					
					
						
					</div>
						
					<div class="row">
						<div class="col-md-4">
							<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>所属部系</span>
							<input type="text" id="txt_ssxb" class="form-control input-radius window" name="ssxb" value="${dwb.ssxb}">
							<span class="input-group-btn"><button type="button" id="btn_ssxb" class="btn btn-link btn-custom">选择</button></span>
						</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>项目性质</span>
								<c:if test="${operateType=='C'}">
								<select id="txt_xmfl" class="form-control input-radius select2"
									name="xmxz">
									<option value="">未选择</option>
									<c:forEach var="item" items="${xmxz}">
										<option value="${item.DM}" data-id="${zy.mc}"
											>${item.MC}</option>
									</c:forEach>
								</select>
								</c:if>
								<c:if test="${operateType=='U'}">
								<select id="txt_xmfl" class="form-control input-radius select2" name="xmxz" value="${dwb.xmxz}">
									<c:forEach var="item" items="${xmxz}">
										<option value="${item.DM}" data-id="${zy.mc}"
											>${item.MC}</option>
									</c:forEach>
								</select>
								</c:if>
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>开支范围</span>
								<input type="text" id="txt_bmh"
									class="form-control input-radius number" name="kzfw" style="text-align:right" value="${dwb.kzfw}" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
							<div class="input-group">
							<span class="input-group-addon">是否启动</span>
								<div class="radiodiv">
									<input type="radio" id="txt_sfqd1" name="sfqd" value="1" <c:if test="${kmsz.qyf!='0'}" >checked</c:if>/>是
									<input type="radio" id="txt_sfqd2" name="sfqd" value="0" <c:if test="${kmsz.qyf=='0'}" >checked</c:if>/>否
								</div>
							</div>
						</div> 
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">启用时间</span>
							<input type="text" id="txt_qysj" class="form-control date" name="qysj" value="<fmt:formatDate value="${dwb.qysj}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="启用时间">
								<span class='input-group-addon'>
								<i class="glyphicon glyphicon-calendar"></i>
							</span>
						</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">完成时间</span>
							<input type="text" id="txt_wcsj" class="form-control date" name="wcsj" value="<fmt:formatDate value="${dwb.wcsj}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="完成时间">
								<span class='input-group-addon'>
								<i class="glyphicon glyphicon-calendar"></i>
							</span>
						</div>
						</div>
						
						
					</div>
					
					<div class="row">
						<div class="col-md-6">
							<div class="input-group">
							<span class="input-group-addon">有无上限</span>
								<div class="radiodiv" id="ywsx">
									<input type="radio" id="txt_ywsx1" name="ywsx" value="1" <c:if test="${kmsz.qyf1!='0'}" >checked</c:if>/>是
									<input type="radio" id="txt_ywsx2" name="ywsx" value="0" <c:if test="${kmsz.qyf1=='0'}" >checked</c:if>/>否
								</div>
							</div>
						</div>
						<div class="col-md-6" id="sxje">
						
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>上限金额(万元)</span>
								<input type="text" id="txt_bmh"
									class="form-control input-radius number1" name="sxje" style="text-align:right" value="${dwb.sxje}" />
									<input type="hidden" name="operateType" value="${operateType}">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="input-group">
							<span class="input-group-addon">是否允许超支</span>
								<div class="radiodiv" id="sfyxcz">
									<input type="radio" id="txt_sfyxcz1" name="sfyxcz" value="1" <c:if test="${kmsz.qyf!='0'}" >checked</c:if>/>是
									<input type="radio" id="txt_sfyxcz2" name="sfyxcz" value="0" <c:if test="${kmsz.qyf=='0'}" >checked</c:if>/>否
								</div>
							</div>
						</div>
						<div class="col-md-6" id="czbl">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>超支比例</span>
								<input type="text" id="txt_bmh"
									class="form-control input-radius number" name="czbl" style="text-align:right" value="${dwb.czbl}" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function() {
		//返回按钮
		$("#btn_back")
				.click(
						function(e) {
							doOperate("${ctx}/xmxxt/goXmxxPage");
							return false;
						});
		
		$("[name='ywsx']").bind("change", function() {
			var selectVal = $(this).val();
			if(selectVal == "1"){
				$("#sxje").css('display', 'block');
			} else {
				$("#sxje").css('display', 'none');
			}
		});
		
		$("[name='sfyxcz']").bind("change", function() {
			var selectVal = $(this).val();
			if(selectVal == "1"){
				$("#czbl").css('display', 'block');
			} else {
				$("#czbl").css('display', 'none');
			}
		});
		
		
		//联想输入
		$("#txt_ssxb").bindChange("${ctx}/suggest/getXx", "D");
		$("#txt_sjdw").bindChange("${ctx}/suggest/getXx", "D");
		//弹窗
		$("#btn_ssxb").click(function() {
			select_commonWin("${ctx}/window/dwpage?controlId=txt_ssxb","所属 部系", "920", "630");
		});
		$("#btn_sjxm").click(function() {
			select_commonWin("${ctx}/xmxxt/goXmxxTree?controlId=txt_sjxm","项目信息", "920", "630");
		});
		
		
		$(document).on("blur", ".number1", function(e){
			$(this).Num(4,true,false,e);
			return false;
		});

		
		//必填验证
	var validate = $('#myform').bootstrapValidator({
			fields : {
				xmbh : {validators : {notEmpty : {message : '项目编号不能为空'}}},
				xmmc : {validators : {notEmpty : {message : '项目名称不能为空'}}},
				ssxmfl : {validators : {notEmpty : {message : '所属项目分类不能为空'}}},
				sjxm : {validators : {notEmpty : {message : '上级项目不能为空'}}},
				ywsx : {validators : {notEmpty : {message : '有无上限不能为空'}}},
				sfyxcz : {validators : {notEmpty : {message : '是否允许超支不能为空'}}},
				xmnd : {validators : {notEmpty : {message : '项目年度不能为空'}}},
				ssxb :{validators : {notEmpty : {message : '所属系部不能为空'}}},
				xmxz:{validators:{notEmpty:{message:'项目性质不能为空'}}},
				sfqd:{validators : {notEmpty : {message : '是否启动不能为空'}}},
				qysj : {validators : {notEmpty : {message : '启用时间不能为空'}}},
				
			}
		});
	//日期验证
	$("#txt_qysj,#txt_wcsj").blur(function(){
		dateVerify();
	})
	//验证日期
	function dateVerify(){
		var kssj = $("#txt_qysj").val();
		var jssj = $("#txt_wcsj").val();

		if(!isNull(kssj) && !isNull(jssj)){
			
			 if(jssj < kssj){
				alert("结束时间不能小于开始时间！");
				$("#txt_wcsj").val("");
			}
		}
	}

		//保存按钮
		$("#btn_save").click(function(e) {
			doSave(validate, "myform", "${ctx}/xmxxt/doSave", function(val) {
				if (val.success) {
					alert(val.msg);
					//location.reload(true);
					window.location.href  = "${ctx}/xmxxt/goXmxxPage";
				}
			});
		});
		//查看页面
		if ($("[name='operateType']").val() == 'L') {
			$("input,select,textarea").attr("readonly", "true");
			$("select").attr("disabled", "true");
		}
		//页面加载完后，控制实验室信息模块是否展示
		sysbz();
		//onoff按扭切换
		$("#btn_onoff").click(function() {
			if ($("[name='sysbz']").val() == '0') {
				$("[name='sysbz']").val("1");
			} else if ($("[name='sysbz']").val() == '1') {
				$("[name='sysbz']").val("0");
			} else {
				$("[name='sysbz']").val("0");
			}
			sysbz();
		});

		//nnoff按扭切换
		$("#btn_nnoff").click(function() {
			if ($("[name='sfxy']").val() == '0') {
				$("[name='sfxy']").val("1");
			} else if ($("[name='sfxy']").val() == '1') {
				$("[name='sfxy']").val("0");
			} else {
				$("[name='sfxy']").val("1");
			}
		});

		//dnoff按扭切换
		$("#btn_dnoff").click(function() {
			if ($("[name='dwzt']").val() == '0') {
				$("[name='dwzt']").val("1");
			} else if ($("[name='dwzt']").val() == '1') {
				$("[name='dwzt']").val("0");
			} else {
				$("[name='dwzt']").val("1");
			}
		});
		//刷新按钮
		$(".reload").click(
				function() {
					var operateType = $("[name='operateType']").val();
					if (operateType == 'U') {
						window.location.href = window.location.href
								+ "&operateType=U&dwbh=${dwb.DWBH}";
					} else {
						var url = window.location.href;
						if (url.indexOf("?") > 0) {
							window.location.href = window.location.href
									+ "&gxgdzc_uuid=googosoft2016";
						} else {
							window.location.href = window.location.href
									+ "?gxgdzc_uuid=googosoft2016";
						}
					}
				});
	});
	function sysbz() {
		var $this = $("[name='sysbz']").val();
		if ($this == '0') {
			$("#sysxx").show();
		} else {
			$("[name='syslx']").val("");
			$("[name='syslb']").val("");
			$("[name='sysmj']").val("0.00");
			$("[name='sysjb']").val("");
			$("[name='ssxk']").val("");
			$("[name='sysgs']").val("");
			$("#sysxx").hide();
		}
	}
	$(function() {
		$("[name='dwzt']")
				.change(
						function() {
							if ($("[name='dwzt']").val() == '0') {
								$
										.ajax({
											type : "post",
											url : "${pageContext.request.contextPath}/dwb/getNewStatus",
											data : "dwbh=${dwb.DWBH}",
											success : function(val) {
												var data = JSON.getJson(val);
												if (data.success == 'false') {
													alert(data.msg);
													$("#drp_dwzt option").eq(0)
															.attr("selected",
																	true);
													$("#drp_dwzt option").eq(1)
															.attr("selected",
																	false);
												}
											},
										});

							}
						});
	});
</script>

</html>