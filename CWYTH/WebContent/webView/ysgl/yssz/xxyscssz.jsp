<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>模板选择</title>
<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css"rel="stylesheet">
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/bootstrap/bootstrap.min.js"type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.dataTables_scrollHeadInner{width:100% ! important;} 
 	table.dataTable{ 

 	} 
 	.dataTables_scrollBody {height:347px!important;}
 	.bottom{width:97%!important;}
/*  	.box-panel + .box-panel{    margin-top: 50px!important;} */
 	.addBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.btn_td{
		width:14mm!important;
		height:6mm!important;
	}
	.selWindow{
		width:280px!important;
	}
</style>
</head>
<body >
<div class="fullscreen" style="padding:10px;height:1600px">
		<div class="box-panel" style="width:50%;float:left;display: inline-block">
			<div class="box-header clearfix">
				<div class="box-header clearfix">
					<div class="sub-title pull-left text-primary">
						<i class="fa icon-xiangxi"></i> 预算参数设置
					</div>
					<div class="pull-right" style="padding-right: 20px;">
							<button class="btn btn-default" id="btn_save8">	<i class="fa icon-save"></i>保存
							</button>
					</div>
				</div>
				<form id="myform8" class="" action="" method="post">
					<input type="hidden" name="operateType" id="operateType" value="${operateType}">
					<input type="hidden" name="id" value="${bzxx.ID}" />
					<div class="container-fluid dialog-body" style="margin-top: 32px">
						<div class="alert alert-info" style="text-align: center;">
							<strong>提示：输入后点击保存生效</strong>
						</div>
						<div class="row" style="width: 43%;float:left;margin: auto;margin-top: 15px!important;">
							<div class="col-sm-10">
								<div class="input-group" style="width: 260px;">
									<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>学生定额参数（元）</span>
									<input type="text" class="form-control input-radius window text-right" id="txt_xsde" name="xsde" value="4000"
										style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"  />
								</div>
							</div>
						</div>	
						<div class="row" style="width: 43%;float:left;margin: auto;margin-top: 15px!important;">							
							<div class="input-group" style="width: 260px;">
								<span class="input-group-addon" style="min-width:82px!important;"><span class="required" style="color:red;">*</span>教师定额参数（元）</span>
								<input type="text" class="form-control input-radius window text-right" id="txt_jsde" name="jsde" value="20000"
									style="border-bottom-right-radius: 4px; border-top-right-radius: 4px;"  />
							</div>
					  </div>
					</div>
				</form>
			</div>	
		</div>
		</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script  src="${ctx}/webView/zdscpz/bootstrap-switch.min.js"></script>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js" type="text/javascript"></script>
<script>

$("#btn_save8").click(function(){
	var ms = $("#txt_zzzy").val();
	$.ajax({
			url:"",
			type:"post",
			data:"ms="+ms,
			success:function(data){
				alert("保存成功");  
				location.reload();
			}
	}); 
	
});
</script>
</body>
</html>