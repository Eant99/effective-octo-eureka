<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>费用科目对应设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.radiodiv {
	border: 1px solid #ccc;
	border-top-right-radius: 4px;
	border-bottom-right-radius: 4px;
	height: 25px;
	line-height: 25px;
	padding-left: 6px;
}
</style>
</head>
<body>
	<form id="myform" class="form-horizontal" action="" method="post">
		<input type="hidden" name="operateType" id="operateType"
			value="${operateType}"> <input type="hidden" name="guid"
			value="${fykmdysz.guid}"> <input type="hidden" name="czr"
			value="${loginId}">
		<div class="page-header">
			<div class="pull-left">
				<span class="page-title text-primary"><i
					class='fa icon-xiangxixinxi'></i>编辑费用科目对应</span>
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
						<i class="fa icon-xiangxi"></i>费用科目对应
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
								<span class="input-group-addon"><span class="required">*</span>费用分类</span>
								<select id="drp_fyfl" class="form-control select2 bc"
									name="fyfl" table="K" disabled>

									<option value="1"
										<c:if test="${fykmdysz.fyfl ==1}">selected</c:if>>日常报销</option>
									<option value="2"
										<c:if test="${fykmdysz.fyfl ==2}">selected</c:if>>差旅费</option>
									<option value="3"
										<c:if test="${fykmdysz.fyfl ==3}">selected</c:if>>预支付</option>
									<option value="4"
										<c:if test="${fykmdysz.fyfl ==4}">selected</c:if>>公务接待费</option>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>费用名称</span>
								<input type="text" id="txt_fymc"
									class="form-control input-radius" name="fymc"
									value="${fykmdysz.fymc}" readonly />
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>借贷方向</span>
								<div class="radiodiv bc">&nbsp;&nbsp;&nbsp;
									<input type="radio" class="bc" name="jdfx" value="0" checked
										<c:if test="${operateType == 'C' }">checked</c:if>>
									贷方&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" class="bc" name="jdfx" value="1"
										<c:if test="${fykmdysz.jdfx=='1'}">checked</c:if>> 借方
								</div>

							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">科目编号</span>
								<input type="text" id="txt_kmbh" class="form-control input-radius window" name="kmbh" value="${fykmdysz.kmbh}" readonly> 
								<span class="input-group-btn"><button type="button" id="btn_kmbh" class="btn btn-link btn-custom">选择</button></span>
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">科目名称</span> <input type="text"
									id="txt_kmmc" class="form-control input-radius" name="kmmc"
									value="${fykmdysz.kmmc}" readonly />
							</div>
						</div>

						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>上级分类</span>
								<input type="text" id="txt_sjfyfl"
									class="form-control input-radius window" name="sjfyfl"
									value="${fykmdysz.sjfyfl}" readonly> <input
									type="hidden" name='sjfl' id="txt_sjfl" readonly
									value="${fykmdysz.sjfl}" readonly> <span
									class="input-group-btn"><button type="button"
										id="btn_sjfl" class="btn btn-link btn-custom bc">选择</button></span>
							</div>
						</div>

					</div>
					<div class="row"></div>
					<div class="row">
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon">备&emsp;&emsp;注</span>
								<textarea id="txt_bz" class="form-control" name="bz" readonly>${fykmdysz.bz}</textarea>
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
			$(".bc").attr("disabled", true);

			//验证方法
			var validate = $('#myform').bootstrapValidator({
				fields : {
					fymc : {
						validators : {
							notEmpty : {
								message : '不能为空'
							},
							stringLength : {
								message : '费用名称过长',
								max : '100'
							}
						}
					},
					sjfyfl : {
						validators : {
							notEmpty : {
								message : '不能为空'
							},
							stringLength : {
								message : '上级分类过长',
								max : '32'
							}
						}
					},

				}
			});
			//保存按钮
			$("#btn_save").click(
					function(e) {
						$(".bc").removeAttr("disabled");
						doSave(validate, "myform", "${ctx}/fykmdysz/doSave",
								function(val) {
									if (val.success) {
										alert(val.msg);
										parent.location.reload(true);
									}
								});
					});
			//科目名称弹框
			//	$("#btn_kmbh").click(function(){
			//		select_commonWin("${ctx}/pzmb/window?controlId=txt_kmbh","科目信息","920","630");
			//    });
			//科目编号弹框
			$("#btn_kmbh")
					.click(
							function() {
								//select_commonWin("${ctx}/kmsz/window?controlId=txt_kmmc&txt_kmbh=txt_kmbh","科目信息","920","630");
								select_commonWin(
										"${ctx}/fykmdysz/kjkmWindow?controlId=txt_kmmc&controlId1=txt_kmbh",
										"科目信息", "920", "630");
							});
			//上级分类弹窗
			$("#btn_sjfl")
					.click(
							function() {
								select_commonWin(
										"${ctx}/fykmdysz/window?controlId=txt_sjfyfl&text_id=txt_sjfl",
										"费用上级分类", "920", "630");
							});
			//返回按钮
			$("#btn_back")
					.click(
							function() {
								window.location.href = "${ctx}/webView/wsbx/jcsz/fykmdysz/fykmdysz_list.jsp?fyfl="
										+ "${param.fyfl}";
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

		});
	</script>
</body>
</html>