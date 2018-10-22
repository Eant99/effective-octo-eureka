<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.googosoft.util.DateUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>汇总分析</title>
<style type="text/css">
#table{
	width: 1030px !important;
}
</style>
<%@include file="/static/include/public-draw-css.inc"%>
<%@include file="/static/include/public-draw-js.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<form id="form" class="form-horizontal" action="" method="post">
    <div class="container-fluid" >
    <div class="box">
        <div class="row" >
		    <div class="col-md-12">
			    <div class='box-panel'>
	     		    <div class='box-header clearfix'>
	         		    <div class="sub-title pull-left text-primary">
	            		    <i class='fa icon-jibenxinxi'></i>
	            		         汇总分析
	         		    </div>
		   			    <div class='actions'>
		       			    <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
		   			    </div>
	     		    </div>
				    <hr class="hr-normal">
				    <div class="container-fluid box-content" style="height:240px;"  >
				        <div id="d_zzt" style="height:220px;"></div>
				    </div>
	 		    </div>
		    </div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class='box-panel'>
		     		<div class='box-header clearfix'>
		         		<div class="sub-title pull-left text-primary">
		            		<i class='fa icon-jibenxinxi'></i>
		            		汇总分析（列表）
		         		</div>
		         		
			   			<div class='actions'>
			       			<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
			   			</div>
		     		</div>
					<hr class="hr-normal">
					<div class="container-fluid box-content" id="d_tab">
						${table}
					</div>
		 		</div>
			</div>
		</div>
    </div>
	</div>
</form>
<%@include file="/static/include/public-list-js.inc"%>
<script type="text/javascript">
$(function(){
	operate();
	$('#table').dataTable({
		"pagingType": "simple_numbers",
		"lengthMenu":20,//显示记录数   i
		"order": [],//排序列
        "serverSide": false,
        "dom":"<'row'<'overflow' t>><'row'<'col-md-3 col-sm-3 col-xs-3'li><'col-md-6 col-sm-6 col-xs-6'p>>",
        "drawCallback":function(){
        	
        }
	});
});
function operate(){
	var wstr = encodeURIComponent("${param.wstr}");
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/select/getZztXx",
		data:"lb=${param.lb}&jc=${param.jc}&tj=${param.tj}&wstr="+wstr,
		success:function(val){
// 			close(index);
			val = $.trim(val);
			val = JSON.getJson(val);
			if(val.option){
				setLineTb($("#d_zzt")[0],val.option);
			}else{
				$("#d_zzt").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
			}
		},
		error:function(val){
// 			close(index);
			alert(val);
		},
		beforeSend:function(val){
// 			index = loading();
		}
	});
}
</script>
</body>
</html>