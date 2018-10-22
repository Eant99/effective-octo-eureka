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
	width:20%;
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
.button{
	border: 1px solid #c7bdbd;
	width:100px;
	height:30px;
	font-size:25;
	outline:none;
	background-color:#fff;
	float:left;
}
.ddj{
	background-color:#00acec;
	color:white;
}
.njjdjdj{
	width:260px;
	float:left;
	padding-left:5px;
}
.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 5px;
}
.chebox{
	width:15px;
	height:15px;
	margin-left:5px !important;
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
			<div style="width:468px;height:30px;">
            	<div class="box-month njjdjdj" ><input type="button" class="button "  id ="month" value="按月份查"><input type="button" class="button" id ="days" value="按日历查"></div>
        	</div>       	
			<hr class="hr-normal" style="position:relative;">
			<div class="box1-content">
			<div class="container-fluid box-content">
				<input type="hidden" name="start1" value="start1" />
				<div class="row">
				<div class="col-md-4">
						<div class="input-group" >
						<span class="input-group-addon">日期<span class="required">*</span></span>
						<c:choose>
							<c:when test="${not empty kjqj }">
							<input type="text" id="txt_kssj" name="kjqj" value="${kjqj }" style="width:143px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="form-control input-radius" />
							</c:when>
							<c:otherwise>
							<input type="text" id="txt_kssj" name="kjqj" style="width:143px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="form-control input-radius" />
							</c:otherwise>
						</c:choose>
	                    <span class='input-group-addon'>
	                        <i class="glyphicon glyphicon-calendar"></i>
	                    </span>
	                    <span style="float:left;line-height:25px;color:#337ab7">&nbsp;&nbsp;至&nbsp;&nbsp;</span>
	                    <c:choose>
	                    	<c:when test="${not empty kkjqj }">
	                    	<input type="text" id="txt_kkssj" name="kkjqj" value="${kkjqj }" style="width:143px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="form-control input-radius" />
	                    	</c:when>
	                    	<c:otherwise>
	                    	<input type="text" id="txt_kkssj" name="kkjqj" style="width:143px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="form-control input-radius" />
	                    	</c:otherwise>
	                    </c:choose>
	                     <span class='input-group-addon'>
	                        <i class="glyphicon glyphicon-calendar"></i>
	                    </span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">会计科目<span class="required">*</span></span>
							<c:if test="${kmmc == '()'}">
							<input type="text" name="startKjkm" id="startKjkm" style="width:36.5%;" class="form-control input-radius" value=""/>
							</c:if>
							<c:if test="${kmmc != '()'}">
							<input type="text" name="startKjkm" id="startKjkm" style="width:36.5%;" class="form-control input-radius" value="${kmmc }"/>
							</c:if>
							<div class="div_style">
							<button type="button" id="start_kjkm" sj="startKjkm" class="btn btn-link btn-custom">选择</button>
							</div>							
						</div>
					</div>
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">科目级次<span class="required">*</span></span> -->
<!-- 							<select class="form-control input-radius select" name="startJc" style="width:36.5%;"> -->
<%-- 								<c:forEach items="${list}" var="jb1"> --%>
<%-- 									<option value="${jb1.jb}">${jb1.jb}</option> --%>
<%-- 								</c:forEach> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group" style="width:180px;float:left;"> -->
<!-- 							<span class="input-group-addon" style="border-right:1px solid #c7bdbd; height:25px;">包含全部未记账凭证</span> -->
<!-- 							<input type="checkbox"  class="chebox"  class="form-control" name="qbjzpz" value="1"/> -->
<!-- 						</div> -->
<!-- 						<div class="input-group" style="width:180px;float:left;"> -->
<!-- 							<span class="input-group-addon" style="border-right:1px solid #c7bdbd; height:25px;">包含已复核未记账凭证</span> -->
<!-- 							<input type="checkbox"  class="chebox"  class="form-control" name="yfhjzpz" value="1"/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->

					<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">包含全部未记账凭证</span>
							    <div class="radiodiv"> &nbsp; &nbsp;
									<input type="radio" id="bb"  class="" name="qbjzpz" value="1"  checked > 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" id="bb"  class=""  name="qbjzpz" value="0" > 否
							   </div>
						</div>
					</div>
				</div>
				<input type="hidden" id="cc"  class="" name="yfhjzpz" value="1"   >
			<!-- 	<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">包含已复核未记账凭证</span>
							    <div class="radiodiv"> &nbsp; &nbsp;
									<input type="radio" id="cc"  class="" name="yfhjzpz" value="1"   > 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" id="cc"  class=""  name="yfhjzpz" value="0" checked> 否
							   </div>
						</div>
					</div>
				</div> -->

				<input type="hidden" name="end1" value="end1" />
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
		   
		   
		   
		   <div class="box2-content" style="display:none;">
			<div class="container-fluid box-content">
				<input type="hidden" name="start2" value="start2" />
				<div class="row">
					<div class="col-md-4">
							<div class="input-group">
							<span class="input-group-addon">日期<span class="required">*</span></span>
							<input type="hidden" name="start"  value="0">
							<c:choose>
								<c:when test="${empty syear2 }">
									<input type="text" id="txt_kssj2" name="kjqj2" style="width:143px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control input-radius" />
								</c:when>
								<c:otherwise>
									<input type="text" id="txt_kssj2" value="${syear2 }" name="kjqj2" style="width:143px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control input-radius" />
								</c:otherwise>
							</c:choose>
		                     <span class='input-group-addon'>
	                        <i class="glyphicon glyphicon-calendar"></i>
	                    </span>
		                    <span style="float:left;line-height:25px;color:#337ab7">&nbsp;&nbsp;至&nbsp;&nbsp;</span>
		                    <c:choose>
		                    	<c:when test="${empty eyear2 }">
		                    		<input type="text" id="txt_kkssj2" name="kkjqj2" style="width:143px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control input-radius" />
		                    	</c:when>
		                    	<c:otherwise>
		                    		<input type="text" id="txt_kkssj2" value="${eyear2 }" name="kkjqj2" style="width:143px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control input-radius" />
		                    	</c:otherwise>
		                    </c:choose>
		                   <span class='input-group-addon'>
	                        <i class="glyphicon glyphicon-calendar"></i>
	                    </span>
							</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">会计科目<span class="required">*</span></span>
							<c:if test="${empty kmmc }">
							<input type="text" name="startKjkm2" id="startKjkm2" style="width:36.5%;" class="form-control input-radius" value=""/>
							</c:if>
							<c:if test="${not empty kmmc }">
							<input type="text" name="startKjkm2" value="${kmmc }" id="startKjkm2" style="width:36.5%;" class="form-control input-radius" value=""/>
							</c:if>
							<div class="div_style">
							<button type="button" id="start_kjkm2" sj="startKjkm2" class="btn btn-link btn-custom">选择</button>
							</div>							
						</div>
					</div>
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">科目级次<span class="required">*</span></span> -->
<!-- 							<select class="form-control input-radius select" name="startJc2" style="width:36.5%;"> -->
<%-- 								<c:forEach items="${list}" var="jb1"> --%>
<%-- 									<option value="${jb1.jb}">${jb1.jb}</option> --%>
<%-- 								</c:forEach> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div class="input-group" style="width:180px;float:left;"> -->
<!-- 							<span class="input-group-addon" style="border-right:1px solid #c7bdbd; height:25px;">包含全部未记账凭证</span> -->
<!-- 							<input type="checkbox"  class="chebox"  class="form-control" name="qbjzpz2" value="1"/> -->
<!-- 						</div> -->
<!-- 						<div class="input-group" style="width:180px;float:left;"> -->
<!-- 							<span class="input-group-addon" style="border-right:1px solid #c7bdbd; height:25px;">包含已复核未记账凭证</span> -->
<!-- 							<input type="checkbox"  class="chebox"  class="form-control" name="yfhjzpz2" value="1"/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->


				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">包含全部未记账凭证</span>
							    <div class="radiodiv"> &nbsp; &nbsp;
									<input type="radio" id="dd"  class="" name="qbjzpz2" value="1"  checked > 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" id="dd"  class=""  name="qbjzpz2" value="0" > 否
							   </div>
						</div>
					</div>
				</div>
				<input type="hidden" id="ee"  class="" name="yfhjzpz2" value="1"   >
			<!-- 	<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">包含已复核未记账凭证</span>
							    <div class="radiodiv"> &nbsp; &nbsp;
									<input type="radio" id="ee"  class="" name="yfhjzpz2" value="1"   > 是 &nbsp; &nbsp; &nbsp;
									<input type="radio" id="ee"  class=""  name="yfhjzpz2" value="0" checked> 否
							   </div>
						</div>
					</div>
				</div> -->

				<input type="hidden" name="end2" value="end2" />
			</div>
			
			<div class="container-fluid box-content">
				<div class="row">
					<div class="pull-center" style="text-align:center;">
	    				<button type="button" class="btn btn-default" id="btn_save2" style="background:#00acec;color: white;">确定</button>
	    				<button type="reset" class="btn btn-default" id="btn_reset2" style="background:#00acec;color: white;margin-left:20px;">取消</button>
	 				</div>
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
	 $("div .button").click(function(){
	        $(this).addClass("ddj").siblings().removeClass("ddj");
		});
	 $("#month").click();
	$("#month").click(function(){
		$(".box1-content").css("display","block");
		$(".box2-content").css("display","none");
	})
	$("#days").click(function(){
		$(".box1-content").css("display","none");
		$(".box2-content").css("display","block");
	})		
	
			
	//会计科目树
	$("#start_kjkm").click(function(){
		var year = $("#txt_kssj").val();
		if(year==""){
			alert("请先选择日期");
			return;
		}
		year = year.substring(0,4);
		select_commonWin("${ctx}/webView/kjhs/bbzx/kmye/kjkmTree.jsp?controlId=startKjkm&mkbh=070701&year="+year,"科目设置","920","630");
	});
	
	$("#start_kjkm2").click(function(){
		var year = $("#txt_kssj2").val();
		if(year==""){
			alert("请先选择日期");
			return;
		}
		year = year.substring(0,4);
		select_commonWin("${ctx}/webView/kjhs/bbzx/kmye/kjkmTree.jsp?controlId=startKjkm2&year="+year,"科目设置","920","630");
	});
	
	
	
	
	$("#btn_reset2").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
			close(winId);
	});		
	//取消按钮
	$("#btn_reset").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
			close(winId);
	});
	
	
	
	$("#btn_save").click(function(){
		//会计期间的验证
		var syear = $("[name='kjqj']").val();
		var eyear = $("[name='kkjqj']").val();
		var ddqj=syear+"至"+eyear;
		if(eyear==""||syear==""){
			alert("日期不能为空!");
			return;
		}else if(syear.substring(0,4)!=eyear.substring(0,4)){
			alert("所选日期年份不相同");
			return;
		}
		
		//会计科目的验证
		var startKjkm = $("[name='startKjkm']").val();
		if(startKjkm==""){
			alert("会计科目不可为空!");
			return;
		}
		
		//科目级次的验证
		var startJc = $("[name='startJc']").val();
		if(startJc==""){
			alert("科目级次不能为空！");
			return;
		}				
		var json = $("#myform").serializeObject("start1","end1");			
  		var jsonStr = JSON.stringify(json);  //json字符串
  		var kmbh = $("[name='startKjkm']").val();
  		$.ajax({
  			url:"${ctx}/xjrjz/paramSession",
  			data:"xjrjzJson="+jsonStr+"&syear="+syear+"&eyear="+eyear,
  			dataType:"json",
  			type:"post",
  			complete:function(){
  				getIframWindow("${param.pname}").toUrl(kmbh,"ayfc",ddqj);
  				var winId = getTopFrame().layer.getFrameIndex(window.name);
  				close(winId);
  			}
  		});
	});
	
	
	$("#btn_save2").click(function(){
		//会计期间的验证
		var syear = $("[name='kjqj2']").val();
		var eyear = $("[name='kkjqj2']").val();
		if(eyear==""||syear==""){
			alert("日期不能为空!");
			return;
		}else if(syear.substring(0,4)!=eyear.substring(0,4)){
			alert("所选日期年份不相同");
			return;
		}
		
		//会计科目的验证
		var startKjkm = $("[name='startKjkm2']").val();
		if(startKjkm==""){
			alert("会计科目不可为空!");
			return;
		}
		
		//科目级次的验证
		var startJc = $("[name='startJc2']").val();
		if(startJc==""){
			alert("科目级次不能为空！");
			return;
		}				
		var json = $("#myform").serializeObject("start2","end2");			
  		var jsonStr = JSON.stringify(json);  //json字符串  
  		var kmbh = $("[name='startKjkm2']").val();
  		$.ajax({
  			url:"${ctx}/xjrjz/paramSession",
  			data:"xjrjzJson="+jsonStr+"&syear2="+syear+"&eyear2="+eyear,
  			dataType:"json",
  			type:"post",
  			complete:function(){
  				getIframWindow("${param.pname}").toUrl(kmbh,"anlc",syear+"至"+eyear);
  				var winId = getTopFrame().layer.getFrameIndex(window.name);
  				close(winId);
  			}
  		});
	});
	
	
});
</script>

</html>