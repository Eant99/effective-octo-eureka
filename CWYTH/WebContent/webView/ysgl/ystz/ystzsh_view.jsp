<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>查看预算调整审核</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.input-group-addon:first-child {
   		 min-width: 123px!important;
	}
	table{
		border-collapse:collapse!important;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   	 	border-bottom-width: 0!important;
	}
	th{
		text-align:center;
	}
	tr th:first-child,td:first-child{
		text-align:center;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>项目申报信息查看</span>
		</div>
		<div class="pull-right">
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
        		</div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	
        	</div>
			<hr class="hr-normal">
	<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单据编号</span>
							<input type="text" id="txt_djbh" name="djbh" readonly class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${jsxx.xh}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位名称</span>
                            <input type="text" id="txt_dwmc" readonly class="form-control input-radius" name="dwmc" value="${jsxx.xm}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							<input type="text" id="txt_xmmc" readonly class="form-control input-radius" name="xmmc" value="${jsxx.xm}"/>
						
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">负责人</span>
	                         <input type="text" id="txt_fzr" readonly class="form-control input-radius window" name="fzr" value="${jsxx.szdw}">
	                         
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算金额（万元）</span>
	                       <input type="text" id="txt_ysje" readonly class="form-control input-radius text-right number" name="ysje" value="">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">调整金额（万元）</span>
							 <input type="text" id="txt_tzje" readonly class="form-control input-radius text-right number" name="tzje" value="${jsxx.szdw}">
						</div>
					</div>
				</div>
				<div class="row">				
                  <div class="col-md-4">
                  	<div class="input-group">
							<span class="input-group-addon">调整人</span>
							<input type="text" id="txt_tzr" readonly class="form-control input-radius window" name="tzr" value="${jsxx.szdw}">
                           
					</div>                	
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">调整日期</span>
                           <input type="text" id="txt_tzrq" readonly class="form-control input-radius date" name="tzrq" value="${jsxx.szdw}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">调整原因</span>
                            <textarea id="txt_tzyy" readonly class="form-control" name="tzyy" >${gwjdfsq.bz}</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">审核意见</span>
                            <textarea id="txt_shyj" readonly class="form-control" name="shyj" >${gwjdfsq.bz}</textarea>
						</div>
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
		window.location.href = "${ctx}/webView/ysgl/ystz/ystzsh_list.jsp";
	});
});
</script>
</body>
</html>