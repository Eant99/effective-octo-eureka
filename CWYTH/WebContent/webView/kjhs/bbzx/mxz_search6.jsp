<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.col-md-4 .input-radius{
	width:24%;
}
.box{
	margin-bottom:10px !imnportant;
}
.div_style{
	float:left;
/* 	margin-top:5px; */
}
select .select{
	width:30px;
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
.col-md-4 .input-radius{
width:24% !important;
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>查询条件
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content" style="">
<!-- 				<input type="hidden" name="start" value="start" /> -->
				<div class="row">
				<div class="col-md-4">
					<div class="input-group">
							<span class="input-group-addon">会计期间<span class="required">*</span></span>
							<input type="hidden" name="start" value="start" />
							<input type="hidden" name="zttype" id="zttype" value="4" />
							<input type="hidden" name="wlbh" id="wlbh" value="${dm}" />
							<input type="text" class="form-control input-radius year" name="year" value="${year}">
							<div class="div_style" style="height:26px;line-height:26px;">
								&nbsp;&nbsp;年&nbsp;&nbsp;
							</div>
							<select class="form-control input-radius select" name="startMonth">
								<c:forEach var="months" items="${months}">
									<option <c:if test="${ StartMonth eq months.month}">selected</c:if> value="${months.month}">${months.month}</option>
								</c:forEach>
							</select>
							<div class="div_style" style="height:26px;line-height:26px;">
								&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;
							</div>
							<select class="form-control input-radius select" name="endMonth">
								<c:forEach var="months" items="${months}">
									<option value="${months.month}" <c:if test="${ endMonth eq months.month}">selected</c:if>>${months.month}</option>
								</c:forEach>
							</select>
							<div class="div_style" style="height:26px;line-height:26px;">
								&nbsp;&nbsp;月&nbsp;&nbsp;
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">会计科目<span class="required">*</span></span>
							<input type="text" name="startKjkm" id="startKjkm" style="width:55% !important;" class="form-control input-radius" value="${kmbh}"/>
							<input type="hidden" name="Kjkmmc" id="Kjkmmc" style="width:28% ;" class="form-control input-radius" value="${kmbh}"/>
							<div class="div_style">
							<button type="button" id="start_kjkm" sj="startKjkm" class="btn btn-link btn-custom">选择</button>
							</div>							
						</div>
					</div>
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">科目级次<span class="required">*</span></span> -->
<!-- 							<select class="form-control input-radius select" name="startJc"> -->
<%-- 								<c:forEach items="${list}" var="jb1"> --%>
<%-- 									<option value="${jb1.jb}">${jb1.jb}</option> --%>
<%-- 								</c:forEach> --%>
<!-- 							</select> -->
<!-- 							<div class="div_style"> -->
<!-- 								&nbsp;&nbsp;至&nbsp;&nbsp; -->
<!-- 							</div> -->
<!-- 							<select class="form-control input-radius select" name="endJc"> -->
<%-- 								<c:forEach items="${list}" var="jb2"> --%>
<%-- 									<option value="${jb2.jb}">${jb2.jb}</option> --%>
<%-- 								</c:forEach> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">排序方式</span> -->
<!-- 							    <div class="radiodiv"> &nbsp; &nbsp; -->
<!-- 									<input type="radio" id="aa"  class="" name="pxfs" value="0"  checked > 凭证日期优先 &nbsp; &nbsp; &nbsp; -->
<!-- 									<input type="radio" id="aa"  class=""  name="pxfs" value="1" > 凭证类型优先 -->
<!-- 							   </div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon" style="border-right:1px solid #c7bdbd;">包含未记账凭证</span> -->
<!-- 							    <input type="checkbox" style="width:10%;height:18px;" class="" name="jzpz" value="1"/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">包含未记账凭证</span>
							    <div class="radiodiv"> &nbsp; &nbsp;
									<input type="radio" id="bb"  class="" name="jzpz" value="1" <c:if test="${pz == '1' }">checked</c:if> <c:if test="${ empty pz}">checked</c:if> > 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" id="bb"  class=""  name="jzpz" value="0" <c:if test="${pz == '0' }">checked</c:if> > 否
							   </div>
						</div>
					</div>
				</div>
				
				<input type="hidden" name="end" value="end" />
			</div>
			
			<div class="container-fluid box-content">
				<div class="row">
					<div class="pull-center" style="text-align:center;">
	    				<button type="button" class="btn btn-default" id="btn_save" style="background:#00acec;color: white;">确定</button>
	    				<button type="reset" class="btn btn-default" id="btn_reset" style="background:#00acec;color: white;margin-left:20px;">取消</button>
	 				</div>
				</div>
		   </div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$("#startKjkm").bindChange("${ctx}/suggest/getXx","KJKM");
$("#endKjkm").bindChange("${ctx}/suggest/getXx","KJKM");
$(function(){
	var mkbh = '${param.pname}'.substr('${param.pname}'.lastIndexOf("_")+1);	
	//会计科目树
	$("[id$=_kjkm]").click(function(){
		var id = $(this).attr("sj");
		var year = $("[name='year']").val();
		if(year==""){
			alert("请先选择会计期间");
			return;
		}
		select_commonWin("${ctx}/webView/kjhs/bbzx/kmye/kjkmTree.jsp?controlId="+id+"&year="+year+"&mkbh="+mkbh,"科目设置","920","630");
	});
	//取消按钮
	$("#btn_reset").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
			close(winId);
	});
	$("#btn_save").click(function(){
		//会计期间的验证
		var year = $("[name='year']").val();
// 		if(year==""){
// 			alert("会计期间年份不能为空!");
// 			return;
// 		}
		var startMonth = $("[name='startMonth']").val();
		var endMonth = $("[name='endMonth']").val();
// 		if(startMonth==""||endMonth===""){
// 			alert("会计期间月份不能为空!");
// 			return;
// 		}
// 		if(Number(startMonth)>Number(endMonth)){
// 			alert("会计期间起始月份不能大于截止月份!");
// 			return;
// 		}
		//科目级次的验证
		var startJc = $("[name='startJc']").val();
		var endJc = $("[name='endJc']").val();
// 		if(Number(endJc)<Number(startJc)){
// 			alert("起始科目级次不能大于截止级次!");
// 			return;
// 		}
// 		if(startJc==""||endJc===""){
// 			alert("科目级次不能为空！");
// 			return;
// 		}
		//会计科目的验证
		var startKjkm = $("[name='startKjkm']").val();
// 		if(startKjkm==""){
// 			alert("会计科目不可为空!");
// 			return;
// 		}
		var json = $("#myform").serializeObject("start","end");  //json对象				
  		var jsonStr = JSON.stringify(json);  //json字符串
  		var bbqj = year+"年"+startMonth+"月至"+endMonth+"月";
  		var kmbh = $("[name='startKjkm']").val();
  		var pxfs = $("[name='pxfs']").filter(":checked").val();
  		var pz = $("input[name='jzpz']:checked").val();
  		$.ajax({
  			url:"${ctx}/bmmxz1/paramSession",
  			data:"params="+jsonStr+"&bbqj="+bbqj,
  			dataType:"json",
  			type:"post",
  			complete:function(){
  				getIframWindow("${param.pname}").kz(bbqj,kmbh,kmbh,startMonth,endMonth,pz);
  				var winId = getTopFrame().layer.getFrameIndex(window.name);
  				close(winId);
  			}
  		});
	});
});
</script>

</html>