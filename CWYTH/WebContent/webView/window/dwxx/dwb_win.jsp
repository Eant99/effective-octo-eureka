<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
    <!---------------------------------------- box 页面的主体部分 开始  --------------------------------------------------->
	<div class="container-fluid dialog-body">
		<div class="box-panel">
			<!-- 单位信息详细信息开始 -->
			<div class="container-fluid box-content">
				<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary">
	            		<i class="fa icon-xiangxi"></i>
	            		单位信息
	            	</div>
	        	</div>
	        	<hr class="hr-normal">
				<!-- row 表示一行，col-xs-x 表示一列占几个位置，一行一共12个位置,此处表示一行三列   开始 -->
				<div class="row">	
					<div class="col-xs-3">
						<div class="input-group">
							<input type="hidden" name="dwbh"  value="${dwb.DWBH}"/>
							<span class="">单位名称</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.DW}</span>
						</div>
					</div>
				</div>
				<div class="row">	
					<div class="col-xs-3">
						<div class="input-group">
							<span class="">单位简称</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.JC}</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group">
							<span class="" >地&emsp;&emsp;址</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="" >${dwb.DZ}</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group">
							<span class="">单位领导</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.DWLDMC}</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group">
							<span class="">分管领导</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.FGLDMC}</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group">
							<span class="">上级单位</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.SJDWMC}</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group">
							<span class="">单位状态</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.DWZTMC}</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group">
							<span class="">备&emsp;&emsp;注</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.BZ}</span>
						</div>
					</div>
				</div>
			</div>
			<!-- 单位信息详细信息结束 -->
		</div>
		<br>
		<div class="box-panel" id="sysxx">
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary">
	            		<i class="fa icon-zichanchuzhi01"></i>
	            		实验室信息
	            	</div>
	        	</div>
				<hr class="hr-normal">
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group">
							<span class="">实验室类型</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.SYSLXMC}</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group">
							<span class="">实验室类别</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.SYSLBMC}</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group">
							<span class="">实验室级别</span>
						</div>
					</div>
					<div class="col-xs-9">
						<div class="input-group">
							<span class="">${dwb.SYSJBMC}</span>
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
	<!-- ---------------------------------------------------- box 页面的主体部分 结束  ---------------------------------------->
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//页面加载完后，控制实验室信息模块是否展示
	sysbz();
	//查看页面时处理函数
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	//日期控件，如果想要日期到具体的时间，可以自己加上参数format:'yyyy-mm-dd hh:ii'
	$(".date").datetimepicker();
	
	//取消
	$("#btn_close").click(function(){
// 		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
	function sysbz(){
			var $this = $("[name='sysbz']").val();
			if($this == '0'){
				$("#sysxx").show();
			}else{
				$("[name='syslx']").val("");
				$("[name='syslb']").val("");
				$("[name='sysmj']").val("");
				$("[name='sysjb']").val("");
				$("[name='ssxk']").val("");
				$("[name='sysgs']").val("");
				$("#sysxx").hide();
			}
	}

</script>
</body>
</html>