<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金分配方案详细信息</title>

<%@include file="/static/include/public-manager-css.inc"%>
<style type='text/css'>
.bttn{
    background-color: #00acec;
    color: white;
}
table{
	border-collapse: collapse!important;
}
</style>

</head>
<body>
	<form id="myform" class="form-horizontal" action="" method="post">
		<input type="hidden" name="operateType" id="operateType"
			value="${operateType}"> <input type="hidden" name="guid"
			value="${zjfpfa.guid}"> <input type="hidden" name="czr"
			value="${loginId}">
		<div class="page-header">
			<div class="pull-left">
				<span class="page-title text-primary"><i
					class='fa icon-xiangxixinxi'></i>查看资金分配方案信息</span>
			</div>
			<div class="pull-right">
				<button type="button" class="btn btn-default" id="btn_back">返回</button>
			</div>
		</div>
		<div class="box" style="top: 36px">
			<div class="box-panel">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i>分配方案
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
								<span class="input-group-addon">方案编号</span> <input type="text"
									id="txt_fabh" name="fabh" readonly
									class="form-control input-radius" value="${fabh }" />
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">方案名称</span> <input type="text"
									id="txt_famc" readonly class="form-control input-radius"
									name="famc" value="${famc}" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="box-panel">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-zichanchuzhi01"></i> 项目信息
					</div>
					<div class="actions">
						<a href="#" class="btn box-collapse btn-mini btn-link"><i
							class="fa"></i></a>
					</div>
				</div>
				<hr class="hr-normal">
				<div class="container-fluid box-content">
				<c:forEach items="${info }" var="item">
					<table id="mydatatables"
						class="table table-striped table-bordered">
						<tr>
							<td style="width: 6px;" rowspan="4">
								<div class="input-group">
									<input type="checkbox" name="guid" class="keyId" id="txt_guid"
										style="margin-top: 50px">
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">项目名称</span> <input type="text"
										id="txt_xmmc" name="xmmc" readonly
										class="form-control input-radius" value="${item.XMMC }" />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">申报单位</span> <input type="text"
										id="txt_sbdw" name="sbdw" readonly
										class="form-control input-radius" value="${item.SBDW }" />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">计划执行时间</span> <input
										type="text" id="txt_jhzxsj" name="jhzxsj" readonly
										class="form-control input-radius" value="${item.JHZXSJ }" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="input-group">
									<span class="input-group-addon">预算金额（元）</span> <input
										type="text" id="txt_ysje" name="ysje" readonly
										class="form-control input-radius text-right number"
										value="${item.YSJE }" />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">已分配金额（元）</span> <input
										type="text" id="txt_yfpje" name="yfpje" readonly
										class="form-control input-radius text-right number"
										value="${item.YFPJE }" />
								</div>
							</td>
							<td>
								<div class="input-group">
									<span class="input-group-addon">资金编号</span> <input type="text"
										id="txt_zjbh" name="zjbh" readonly
										class="form-control input-radius" value="${item.ZJBH }" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="input-group">
									<span class="input-group-addon">资金来源</span> <input type="text"
										id="txt_zjly" name="zjly" readonly
										class="form-control input-radius" value="${item.ZJLY }" />
								</div>
							</td>
						</tr>
					</table>
					</c:forEach>
                 	<div class="col-dm-18">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
                               <textarea id="txt_shyj"  class="form-control" name="shyj" style=" height: 142px; width: 100%;" readonly>${shyj }</textarea>
						</div>
					</div>	
				</div>
			</div>
		</div>
	</form>
	<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/xmgl/zjgl/zjfpfa/zjfpfa_list.jsp";
	});
	//刷新按钮
	$(".reload").click(function(){
		window.location.reaload();
	});
});

</script>
</body>
</html>