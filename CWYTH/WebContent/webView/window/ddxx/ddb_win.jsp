<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>地点信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body class="contrast-blue">
	<form id="myform" class="form-horizontal" action="" method="post" >
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<div class="container-fluid dialog-body">
			<div class="box-panel">
				<div class="container-fluid box-content">
					<div class="box-header clearfix">
		            	<div class="sub-title pull-left text-primary">
		            		<i class="fa icon-xiangxi"></i>
		            		地点信息
		            	</div>
		        	</div>
		        	<hr class="hr-normal">
					<!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置,此处表示一行三列   开始 -->
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group">
								<span class="">地&ensp;点&ensp;号</span>
								<input type="hidden" name="ddbh"  value="${ddb.DDBH}"/>
							</div>
						</div>
						<div class="col-xs-9">
							<div class="input-group">
								<span class="">${ddb.DDH}</span>
							</div>
						</div>
					</div>
					<div class="row">	
						<div class="col-xs-3">
							<div class="input-group">
								<span class="">地点名称</span>
							</div>
						</div>
						<div class="col-xs-9">
							<div class="input-group">
								<span class="">${ddb.MC}</span>
							</div>
						</div>
					</div>
					<div class="row">	
						<div class="col-xs-3">
							<div class="input-group">
								<span class="">所属单位</span>
							</div>
						</div>
						<div class="col-xs-9">
							<div class="input-group">
								<span class="">${ddb.dwmc}</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group">
								<span class="">上级地点</span>
							</div>
						</div>
						<div class="col-xs-9">
							<div class="input-group">
								<span class="">${ddb.sjddmc}</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group">
								<span class="">地点状态</span>
							</div>
						</div>
						<div class="col-xs-9">
							<div class="input-group">
								<span class="">${ddb.DDZTMC}</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class='page-bottom clearfix'>
			<!-- 保存和返回按钮 开始 -->
	        <div class="pull-right">
				<button type='button' class="btn btn-default btn-without-icon" id="btn_close">
					关闭
				</button>
	        </div>
	        <!-- 保存和返回按钮 结束 -->
	    </div>	
	</form>
	<%@include file="/static/include/public-manager-js.inc"%>
	<script type="text/javascript">
	$(function(){
		//查看时处理所有控件
		if($("[name='operateType']").val()=='L'){
			$("input,select,textarea").attr("readonly","true");
			$("select").attr("disabled","true");
		}
		//取消
		$("#btn_close").click(function(){
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});
		});		
	</script>
</body>
</html>