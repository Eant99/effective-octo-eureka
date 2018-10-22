<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>查看收入预算</title>
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
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>收入预算查看</span>
		</div>
		<div class="pull-right">
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
        		</div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>收入预算信息
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
							<span class="input-group-addon">编制年度</span>
							<input type="text" id="txt_bznd" readonly class="form-control input-radius" name="bznd" value="${jsxx.xm}"/>
						
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">上年预算总额（万元）</span>
	                         <input type="text" id="txt_snysze" readonly class="form-control input-radius window" name="snysze" value="${jsxx.szdw}">
	                         
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上年收入总额（万元）</span>
	                       <input type="text" id="txt_snysze" readonly class="form-control input-radius text-right number" name="snysze" value="">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">本年预算总额（万元）</span>
							 <input type="text" id="txt_bnysze" readonly class="form-control input-radius text-right number" name="bnysze" value="${jsxx.szdw}">
						</div>
					</div>
				</div>
				<div class="row">				
                  <div class="col-md-4">
                  	<div class="input-group">
							<span class="input-group-addon">填报人</span>
							<input type="text" id="txt_tbr" readonly class="form-control input-radius window" name="tbr" value="${jsxx.szdw}">
                           
					</div>                	
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">填报时间</span>
                           <input type="text" id="txt_tbsj" readonly class="form-control input-radius" name="tbsj" value="${jsxx.szdw}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">审核人</span>
                             <input type="text" id="txt_shr" readonly class="form-control input-radius" name="shr" value="${jsxx.szdw}">
						</div>
					</div>
				</div>
				<div class="row">
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">审核日期</span>
                             <input type="text" id="txt_shrq" readonly class="form-control input-radius" name="shrq" value="${jsxx.szdw}">
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
		window.location.href = "${ctx}/webView/ysgl/yscx/zcyscx_list.jsp";
	});
});
</script>
</body>
</html>