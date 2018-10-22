<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@page import="com.googosoft.util.DateUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-manager-css.inc"%>
<%
	String date = Constant.MR_MONTH();
	%>
<style type="text/css">
.col-md-4 .input-radius{
	width:25%;
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
#zt1{
margin-top: 5px
}
#xzqb{
margin-top: 5px
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<div class="box" style="margin-top:20px";>
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
				<div class="row">
				<div class="col-md-4">
					<div class="input-group">
							<span class="input-group-addon">会计期间<span class="required">*</span></span>
							<input type="hidden" name="start" value="start" />
							<input type="text" class="form-control input-radius year" name="year" value="${year}">
							<div class="div_style" style="height:26px;line-height:26px;">
								&nbsp;&nbsp;年&nbsp;&nbsp;
							</div>
							<select class="form-control input-radius select" name="startMonth">
								<c:forEach var="months" items="${months}">
									<option value="${months.month}">${months.month}</option>
								</c:forEach>
							</select>
							<div class="div_style" style="height:26px;line-height:26px;">
								&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;
							</div>
							<select class="form-control input-radius select" name="endMonth">
								<c:forEach var="months" items="${months}">
<%-- 									<option value="" ><%=date%></option> --%>
									<option value="${months.month} " <c:if test="${mm eq months.month}">selected</c:if> >${months.month}</option>
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
							<input type="text" name="startKjkm" id="startKjkm" style="width:28%;" class="form-control input-radius" value=""/>
							<div class="div_style">
							<button type="button" id="start_kjkm" sj="startKjkm" class="btn btn-link btn-custom">选择</button>
							至&nbsp;&nbsp;&nbsp;
							</div>
							<input type="text" name="endKjkm" id="endKjkm" style="width:28%;" class="form-control input-radius" value=""/>
				    		<button type="button" id="end_kjkm" sj="endKjkm" style="float:left;" class="btn btn-link btn-custom">选择</button>
				    	
				    	
				    		<div id="xzqb" class="div_style">
							<span  style="margin-left: 18px">选择全部</span>
							</div>
							<input id="xzqb1" type="checkbox" style="width:18px;height:18px; margin-left: 5px" class="form-control" name="xzqb"  value=""/>
						</div>
							
							
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">科目级次<span class="required">*</span></span>
							<select class="form-control input-radius select" name="startJc">
								<c:forEach items="${list}" var="jb1">
									<option value="${jb1.jb}">${jb1.jb}</option>
								</c:forEach>
							</select>
							<div class="div_style">
								&nbsp;&nbsp;至&nbsp;&nbsp;
							</div>
							<select class="form-control input-radius select" name="endJc">
								<c:forEach items="${list}" var="jb2">
									<option value="${jb2.jb}">${jb2.jb}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">不汇总科目</span> -->
<!-- 							<input type="text" class="form-control" name="nokm" value="" placeholder="科目之间用,隔开"/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<div id="zt1" class="div_style"> -->
<!-- 							<span  >包含未记账凭证</span> -->
<!-- 							</div> -->
<!-- 							<input type="checkbox" style="width:18px;height:18px; margin-left: 5px" class="form-control" name="jzpz" value="1"/> -->
							
							
							
<!-- 							<div id="zt1" class="div_style"> -->
<!-- 							<span  style="margin-left: 18px">部门格式</span> -->
<!-- 							</div> -->
<!-- 							<input type="checkbox" style="width:18px;height:18px; margin-left: 5px" class="form-control" name="bm" value="1"/> -->
							
							
<!-- 							<div id="zt1" class="div_style"> -->
<!-- 							<span  style="margin-left: 18px;">项目格式</span> -->
<!-- 							</div> -->
<!-- 							<input type="checkbox" style="width:18px;height:18px; margin-left: 5px" class="form-control" name="xm" value="1"/> -->
							<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">包含全部未记账凭证1</span>
							    <div class="radiodiv"> &nbsp; &nbsp;
									<input type="radio" id="bb"  class="" name="jzpz" value="1" checked  > 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" id="bb"  class=""  name="jzpz" value="0" > 否
							   </div>
						</div>
					</div>
				</div>
				
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">部门格式</span> -->
<!-- 							    <div class="radiodiv"> &nbsp; &nbsp; -->
<!-- 									<input type="radio" id="cc"  class="" name="bm" value="1"   > 是 &nbsp; &nbsp; &nbsp; -->
<!-- 									<input type="radio" id="cc"  class=""  name="bm" value="0" checked> 否 -->
<!-- 							   </div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				
<!-- 						<div class="row"> -->
<!-- 							<div class="col-md-4"> -->
<!-- 								<div class="input-group"> -->
<!-- 									<span class="input-group-addon">项目格式</span> -->
<!-- 									    <div class="radiodiv"> &nbsp; &nbsp; -->
<!-- 											<input type="radio" id="dd"  class="" name="xm" value="1"   > 是 &nbsp; &nbsp; &nbsp; -->
<!-- 											<input type="radio" id="dd"  class=""  name="xm" value="0" checked> 否 -->
<!-- 									   </div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<input type="hidden" name="gs" value="${param.gs }"/>
						
						<input type="hidden" name="end" value="end" />
						</div>
					</div>
				</div>
			</div>
<!-- 			<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group" style="width:100%;"> -->
<!-- 							<input type="checkbox" style="width:5%;height:18px;" class="form-control" name="bm" value="1"/> -->
<!-- 							<div class="div_style"> -->
<!-- 								部门格式 -->
<!-- 							</div> -->
<!-- 							<input type="checkbox" style="width:5%;height:18px;" class="form-control" name="xm" value="1"/> -->
<!-- 							<div class="div_style"> -->
<!-- 								项目格式 -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<input type="hidden" name="end" value="end" /> -->
<!-- 				</div> -->
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


$("#startKjkm").bindChange("${ctx}/suggest/getXx2","KJKM");
$("#endKjkm").bindChange("${ctx}/suggest/getXx2","KJKM");

$(document).on("click","#xzqb1",function(){
	var qk = $("#xzqb1").val();
	if(qk==""){
		 $("#xzqb1").val("1");
	}else{
		 $("#xzqb1").val("");
	}
	 var year=$("[name='year']");
	var jc = $("[name='bm']");
	$.ajax({
		type:"post",
		async : false, 
		url:"${ctx}/kmye/getXxList?",
		dataType: "json",
		success:function(val){
			var aa=val[0].KMXX;
			var bb=val[1].KMXX;
			if(qk==''){
				 $("#startKjkm").val(aa);
				 $("#endKjkm").val(bb);
			}else{
				$("#startKjkm").val("");
				 $("#endKjkm").val("");
			}
			  },
	});	 
});


$(function(){
	//会计科目树
	$("[id$=_kjkm]").click(function(){
		var id = $(this).attr("sj");
		var year = $("[name='year']").val();
		if(year==""){
			alert("请先选择会计期间");
			return;
		}
		select_commonWin("${ctx}/webView/kjhs/bbzx/kmye/kjkmTree.jsp?controlId="+id+"&year="+year,"科目设置","920","630");
	});
	//取消按钮
	$("#btn_reset").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
		close(winId);
		
	});
	$("#btn_save").click(function(){
		//会计期间的验证
		var year = $("[name='year']").val();
		if(year==""){
			alert("会计期间年份不能为空!");
			return;
		}
		var startMonth = $("[name='startMonth']").val();
		var endMonth = $("[name='endMonth']").val();
		if(startMonth==""||endMonth===""){
			alert("会计期间月份不能为空!");
			return;
		}
		if(Number(startMonth)>Number(endMonth)){
			alert("会计期间起始月份不能大于截止月份!");
			return;
		}
		//科目级次的验证
		var startJc = $("[name='startJc']").val();
		var endJc = $("[name='endJc']").val();
		if(Number(endJc)<Number(startJc)){
			alert("起始科目级次不能大于截止级次!");
			return;
		}
		if(startJc==""||endJc===""){
			alert("科目级次不能为空！");
			return;
		}
		//会计科目的验证
		var startKjkm = $("[name='startKjkm']").val();
		var endKjkm = $("[name='endKjkm']").val();
		if(startKjkm==""||endKjkm===""){
			alert("会计科目不可为空!");
			return;
		}
		startKjkm = startKjkm.substring(1,startKjkm.indexOf(")"));
		endKjkm = endKjkm.substring(1,endKjkm.indexOf(")"));
		if(Number(startKjkm)>Number(endKjkm)){
			alert("起始会计科目不能大于截止会计科目!");
			return;
		}
		//

		
		
		
		
		//
		var json = $("#myform").serializeObject("start","end");  //json对象				
  		var jsonStr = JSON.stringify(json);  //json字符串
  		var date = year+"年"+startMonth+"月至"+endMonth+"月";
//   		var bm = $("[name='bm']").filter(":checked").val();
//   		var xm = $("[name='xm']").filter(":checked").val();
  		var xzqb = $("[name='xzqb']").val();
  		var gs = "${param.gs}";
//   		if(bm=="1"&&xm!="1"){
//   			gs = "bm";
//   		}
//   		if(xm=="1"){
//   			gs = "xm";
//   		}
  		$.ajax({
  			url:"${ctx}/kmye/paramSession",
  			data:"params="+jsonStr+"&gs=${param.gs}",
  			dataType:"json",
  			type:"post",
  			complete:function(){
  				getIframWindow("${param.pname}").toUrl(jsonStr,date,gs);
  				var winId = getTopFrame().layer.getFrameIndex(window.name);
  				close(winId);
  			}
  		});
	});
});
</script>

</html>