<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入费用表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.input_info{
		width:100px;
	}
	button{
/* 		background-color: #00acec !important; */
/* 		color: white !important; */
	}
	.div_bottom{
    	width: 99%;
    	position: absolute;
    	bottom: 20px;
   		background-color: #f3f3f3;
		
	}
	.bom{
		color:red;
	}
	.yc{
		display:none!important;
	}
	.form-control{
		border:none;
	}
	ul li{
		list-style-type:none;
	}
	.bottom1{
		margin-top: 50px;
	}
	.bottom1 tr td{
		width: 400px !important;
	}
	tr td{
		height: 30px;
	}
	.num{
		text-align:right;
	}
	.sign-number{
		text-align:right !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
	                <label>选择年度</label>
	             	<input  type="text" class="input_info nd form-control" readonly style="border:1px solid #ccc;" value="${sysDate}"/>
	             	<i class="glyphicon glyphicon-calendar"></i> 
					&nbsp;
					
					 <label>是否包含未记账凭证&nbsp; </label>
					 <select style="width:60px;border:1px solid #ccc;" id="jzpz" class="form-control select">			
					 	<option value="0" <c:if test="${jzpz=='0'}">selected</c:if>>否</option>		
						<option value="1" <c:if test="${jzpz=='1'}">selected</c:if>>是</option>
					</select>
				</div>
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_save">保存</button>
					<button type='button' class="btn btn-default" id="btn_print">打印预览</button>
					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
			</div>
		</form>
	</div>

<form id="mysave" method="post" >
<div class="container-fluid" >
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:100%;margin:0 auto 30px;">
		        	 <div id="print_title">
			<h2 style="text-align:center;">收入支出表（年度）</h2>
			<div style="text-align:right;font-size:12px;">会高校02表</div>
					<div style="text-align:left;font-size:12px;float:left;width:33%">编制单位：${bzdw.XXMC}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：${sysDate }年度</div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
					</div>
					<thead>
				        <tr>
				            <th style="text-align:center;width:30%">项目</th>
				            <th style="text-align:center;width:35%">上年数</th>
				            <th style="text-align:center;width:35%">本年数</th>				           
				        </tr>
					</thead>
				    <tbody>
				    <c:forEach var="list" items="${list}" varStatus="i">
				 		<tr class="tr_${i.index+1}">
				 			<td>${list.XMMC}
				 				<input type="hidden" name="czr" value="${login}">
				 				<input type="hidden" class="form-control" name="xmbh" value="${list.XMMC}"/>
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="ny" value="${sysDate}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="ztbh" value="${sszt}">
				 				<input type="hidden" name="hc" value="${list.XH}">
				 				<input type="hidden" name="flag" value="${list.FLAG}">
				 				<input type="hidden" name="read" value="${list.READ}">
				 			</td>
				 			<td class="num" role="bys">
				 				<input type="text" name="bys" flag="${list.FLAG}" class="form-control sign-number" ${list.READ} style="text-align:right;" value="${list.BYS}"/>
				 			</td>
				 			<td class="num" role="bnljs">
				 				<input type="text" name="bnljs" flag="${list.FLAG}" class="form-control sign-number" ${list.READ} style="text-align:right;" value="${list.BNLJS}"/>
				 				<input type="hidden" name="bblx" value="0">
				 			</td>
				 		</tr>
				 	</c:forEach>
				    </tbody>
				</table>					
		</div>
	</div>
</div>
</form>
<!-- <div class="bottom1"> -->
<!-- <table> -->
<!-- <tr> -->
<!-- <td>单位主要负责人（签字）：</td> -->
<!-- <td>财务负责人（签字）：</td> -->
<!-- <td>制表人（签字）：</td> -->
<!-- </tr> -->
<!-- <tr> -->
<!-- <td> -->
<!-- 注：本表反映核算单位当前年度的收入支出情况。 -->
<!-- </td> -->
<!-- </tr> -->
<!-- </table> -->
<!-- </div> -->
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${ctx }/static/javascript/public/public-cwbb.js"></script>
<script>
$(function () {
	$(document).on("focus", ".nd", function(){
	    $(this).on("click", function() {
	    	WdatePicker({
	    		dateFmt:'yyyy',
	    		onpicked:function(){
	    			var val = $(this).val();
	    			var jzpz = $("#jzpz").val();
	    			if(val!=""&&jzpz!=""){
	    				location.href="${ctx}/srfyj/toUrl?status=year&ny="+val+"&bblx=0&jzpz="+jzpz;
	    			}
	    		},
	    		onclearing:function(){
	    			alert("请选择年度!");
	    			return;
	    		}
	    	});
	    });
	    $(this).on("keypress", function() {
	        if (/[^0-9]/.test(String.fromCharCode(event.keyCode)))
	            event.keyCode = 0;
	    });
	    $(this).on("dragenter", function() {
	        return false;
	    });
	    $(this).on("paste", function() {
	        return false;
	    });
	});		 
});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
//记账凭证条件切换
$("#jzpz").change(function(){
	var jzpz = $(this).val();
	var val = $(".nd").val();
	if(val!=""&&jzpz!=""){
		location.href="${ctx}/srfyj/toUrl?status=year&ny="+val+"&bblx=0&jzpz="+jzpz;
	}else{
		alert("存在空的查询条件!");
		return;
	}
});
//点击保存按钮
$("#btn_save").click(function(){
	var json = $("#mysave").serializeObject("czr","bblx","bbyf");  //json对象				
	var jsonStr = JSON.stringify(json);
	$.ajax({
		url:"${ctx}/srfyj/doSave",
		data:"jsonStr="+jsonStr,
		dataType:"json",
		type:"post",
		success:function(msg){
			if($.trim(msg)=="true"){
				alert("保存成功!");
			}else{
				alert("保存失败!");
			}
		}
	});
});
//点击导出
// $("#btn_exp").click(function(){
// 	var bblx = $("[name='bblx']").val();
// 	var sysDate = $("[name='ny']").val();
// 	var sszt = $("[name='ztbh']").val();
// 	var jzpz = $("[name='jzpz']").val();
// 	var bzdw = $("[name='bzdw']").val();
// 	doExp("","${ctx}/srfyj/expExcel?bblx="+bblx+"&sysDate="+sysDate+"&sszt="+sszt+"&jzpz="+jzpz+"&bzdw="+bzdw,"收入费用年度","${ctx}","");
// });
//导出Excel
$("#btn_exp").click(function() {
	var json = $("#mysave").serializeObject("czr","bblx","bbyf");  //json对象				
	var jsonStr = JSON.stringify(json);
	$.ajax({
		url:"${ctx}/srfyj/doSave",
		data:"jsonStr="+jsonStr,
		dataType:"json",
		type:"post",
		success:function(msg){
			if($.trim(msg)=="true"){
				var bblx = $("[name='bblx']").val();
				var sysDate = $("[name='ny']").val();
				var sszt = $("[name='ztbh']").val();
				var jzpz = $("[name='jzpz']").val();
				var bzdw = $("[name='bzdw']").val();
				var flag= "年度";
						$.ajax({
							type : "post",
							data : {bblx:bblx,sysDate:sysDate, sszt:sszt,jzpz:jzpz,bzdw:bzdw,flag:flag},
							url : "${ctx}/srfyj/expExcel2",
							success : function(val) {
								FileDownload("${ctx}/file/fileDownload","${sysDate}年收入支出表.xls", val.url);
							}
						});
			}else{
				alert("导出失败!");
			}
		}
	});
		});
//点击打印
$("#btn_print").click(function(){
	//先保存后打印
	var json = $("#mysave").serializeObject("czr","bblx","bbyf");  //json对象				
	var jsonStr = JSON.stringify(json);
	$.ajax({
		url:"${ctx}/srfyj/doSave",
		data:"jsonStr="+jsonStr,
		dataType:"json",
		type:"post",
		success:function(msg){
			if($.trim(msg)=="true"){
				var bblx = $("[name='bblx']").val();
				var sysDate = $("[name='ny']").val();
				var sszt = $("[name='ztbh']").val();
				var jzpz = $("[name='jzpz']").val();
				var bzdw = $("[name='bzdw']").val();
				location.href = "${ctx}/srfyj/goPrint?status=year&bblx="+bblx+"&sysDate="+sysDate+"&sszt="+sszt+"&jzpz="+jzpz+"&bzdw="+bzdw;
			}else{
				alert("预览失败!");
			}
		}
	});
	});
<!--计算区域-->
$(".sign-number[name='bys']").on("keyup",function(){
	computeLinked(3,5,2,"bys");
	computeLinked(7,8,6,"bys");
	computeLinked(9,9,8,"bys");
	computeLinked(14,15,13,"bys");
	computeArray(new Array(2,6,10,11,12,13),1,"bys");
	computeLinked(18,22,17,"bys");
	computeLinked(25,29,24,"bys");
	computeArray(new Array(24,30,31,32,33),23,"bys");
	computeArray(new Array(23,17),16,"bys");
	computeLinked(35,37,34,"bys");
});
$(".sign-number[name='bnljs']").on("keyup",function(){
	computeLinked(3,5,2,"bnljs");
	computeLinked(7,8,6,"bnljs");
	computeLinked(9,9,8,"bnljs");
	computeLinked(14,15,13,"bnljs");
	computeArray(new Array(2,6,10,11,12,13),1,"bnljs");
	computeLinked(18,22,17,"bnljs");
	computeLinked(25,29,24,"bnljs");
	computeArray(new Array(24,30,31,32,33),23,"bnljs");
	computeArray(new Array(23,17),16,"bnljs");
	computeLinked(35,37,34,"bnljs");
});
<!--只读-->
readonlyArray(new Array(1,17,35,39,40,42,45),"bys");
readonlyArray(new Array(1,17,35,39,40,42,45),"bnljs");
</script>
</body>
</html>