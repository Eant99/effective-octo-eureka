<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入预算编辑</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
	border-bottom-width: 1px !important;
}

table {
	border-collapse: collapse !important;
}

.btn_td {
	text-align: center;
}

.btn_td {
	width: 14mm !important;
	height: 6mm !important;
}

.selWindow {
	width: 280px !important;
}

.number1 {
	text-align: right;
}

.yc {
	display: none;
}

tbody input {
	border: none;
}

thead th {
	text-align: center !important;
}

.noBlock {
	margin-bottom: 3px;
	position: absolute;
	z-index: 999;
	display: block;
	padding-left: 17%;
	color: red;
}

.border {
	border: 1px solid #a94442;
	background: url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right
		no-repeat;;
}

.radiodiv {
	border: 1px solid #ccc;
	border-top-right-radius: 4px;
	border-bottom-right-radius: 4px;
	/* 	    border-radius: 4px; */
	height: 25px;
	line-height: 25px;
	padding-left: 6px;
}

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
</style>
</head>
<body>
	<div id="myform" class="form-horizontal" action="" method="post">
		<input type="hidden" name="operateType" id="operateType"
			value="${operateType}">
		<div class='page-header'>
			<div class="pull-left">
				<span class="page-title text-primary"><i
					class='fa icon-xiangxixinxi'></i>部门预算查看</span>
			</div>
			<div class="pull-right">
				<button type="button" class="btn btn-default" id="btn_back">返回</button>
			</div>
		</div>
		<div class="box">
			<div class="box-panel">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i>部门预算信息
					</div>
					<div class="actions">
						<a href="#" class="btn box-collapse btn-mini btn-link"><i
							class="fa"></i></a>
					</div>
				</div>
				<hr class="hr-normal">
				<div class="container-fluid box-content">
					<form id="table1">
						<div class="row">
							<div class="col-md-4">
								<div class="input-group">
									<span class="input-group-addon">部门编号</span> <input readonly
										type="text" id="txt_bmbh" class="form-control input-radius"
										name="sbry1" value="${guid.BMBH}" readonly> 									<!--                             <span class="input-group-btn"><button type="button" id="btn_sbry" class="btn btn-link btn-custom">选择</button></span>
 -->
								</div>
							</div>
							<div class="col-md-4">
								<div class="input-group">
									<span class="input-group-addon">部门名称</span> <input readonly
										type="text" id="txt_bmmc"
										class="form-control input-radius window" name="bmmc"
										value="${guid.BMMC }" /> 
									<!-- 							<span class="input-group-btn"><button type="button" id="btn_sbbm" class="btn btn-link btn-custom">选择</button></span>
 -->
								</div>
							</div>
							<div class="col-md-4">
								<div class="input-group">
									<span class="input-group-addon">申报年度</span> <input readonly
										type="text" id="txt_sbnd" class="form-control input-radius"
										name="sbry1" value="${guid.SBND}" readonly> 
									<!--                             <span class="input-group-btn"><button type="button" id="btn_sbry" class="btn btn-link btn-custom">选择</button></span>
 -->
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<div class="input-group">
									<span class="input-group-addon">收入预算汇总（万元）</span> <input
										readonly type="text" id="txt_sryshz" style="text-align: right;"
										class="form-control input-radius" name="sryshz"
										value="${guid.SRYSHZ}" readonly> 
									<!--                             <span class="input-group-btn"><button type="button" id="btn_sbry" class="btn btn-link btn-custom">选择</button></span>
 -->
								</div>
							</div>
							<div class="col-md-4">
								<div class="input-group">
									<span class="input-group-addon">支出预算汇总（万元）</span> <input
										readonly type="text" id="txt_zcyshz" style="text-align: right;"
										class="form-control input-radius" name="zcyshz"
										value="${guid.ZCYSHZ}" readonly> 
									<!--                             <span class="input-group-btn"><button type="button" id="btn_sbry" class="btn btn-link btn-custom">选择</button></span>
 -->
								</div>
							</div>
							<div class="col-md-4">
								<div class="input-group">
									<span class="input-group-addon">建议金额（万元）</span> <input readonly
										type="text" id="txt_jyje" class="form-control input-radius"  style="text-align: right;"
										name="jyje" value="${guid.JYJE }" readonly> 
									<!--                             <span class="input-group-btn"><button type="button" id="btn_sbry" class="btn btn-link btn-custom">选择</button></span>
 -->
								</div>
							</div>
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function() {

		//返回按钮
		$("#btn_back").click(function(e) {
			location.href = "${ctx}/webView/ysgl/jcsz/bmysck/bmysck_list.jsp";
		});

	});
</script>

</html>