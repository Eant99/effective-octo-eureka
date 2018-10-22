<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
thead th{
	text-align:center;
}
input .form-input{
	border:none;
	wisth:100%;
}
	#rr{
		text-align:right;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
	               <label>选择日期</label>
	               <input  type="text" class="input_info years form-control" style="border:1px solid #ccc;"  value="${year}"/>
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
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:82%;margin:0 auto 30px">
					<h4 style="text-align:center;">现金流量表</h4>
					<h6 style="margin-left:49.3%;">${year}年</h6>
					<caption style="text-align:right;">
						会政财务04表
					<caption style="text-align:left;">
						编制单位：${bzdw.XXMC}
					<div style="float:right;margin-right:2%;">
						单位:元
					</div>
					</caption>
		        	<thead>
				        <tr>
			            	<th style="text-align:center;">项目</th>
				            <th style="text-align:center;width:15%;">本年金额</th>	
				            <th style="text-align:center;width:15%;">上年金额</th>
				        </tr>
					</thead>
				    <tbody>
				     <c:forEach var="list" items="${list}" varStatus="i">
				 		<tr class="tr_${i.index+1}">
				 			<td>
				 				<input type="hidden" name="czr" value="${czr}">
				 				${list.XMMC }
<%-- 				 				<input type="text" class="form-control" name="xmmc" value="${list.XMMC }" /> --%>
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="${list.XMMC }">
				 				<input type="hidden" id="bb" name="ny" value="${year}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="ztbh" value="${sszt}">
				 				<input type="hidden" name="tag" value="${list.TAG}">
				 				<input type="hidden" name="hc" value="${list.HC}">
				 			</td>
				 			<td class="num" role="bnje">
<%-- 				 			${list.BNJE } --%>
				 				<input type="text" id="rr" name="bnje" tag="${list.TAG}"  class="form-control num " value="${list.BNJE }" />
<%-- 				 				<input type="hidden" name="snje" value="${list.BNJE }"> --%>
				 			</td>
				 			<td class="num" role="snje">
<%-- 				 			${list.SNJE } --%>
				 				<input type="text" id="rr" name="snje" tag="${list.TAG}" class="form-control num " value="${list.SNJE }" />
<%-- 				 				<input type="hidden" name="bnje" value="${list.SNJE }"> --%>
				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	</c:forEach>
				    </tbody>
				</table>						
			</div>
		</div>
	</div>
	</form>
</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	$(document).on("focus", ".years", function(){
	    $(this).on("click", function() {
	    	WdatePicker({
	    		dateFmt:'yyyy',
	    		onpicked:function(){
	    			var val = $(this).val();
	    			var jzpz = $("#jzpz").val();
	    			location.href="${ctx}/xjllb/toUrl?year="+val+"&jzpz="+jzpz;
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
		var jzpz = $("#jzpz").val();
		var val = $(".years").val();
// 		alert(jzpz);
		location.href="${ctx}/xjllb/toUrl?year="+val+"&jzpz="+jzpz;
// 	});
});
//点击保存按钮
$("#btn_save").click(function(){
	var json = $("#mysave").serializeObject("czr","bblx");  //json对象				
	var jsonStr = JSON.stringify(json);
// 	alert("jsonStr"+jsonStr);
	$.ajax({
		url:"${ctx}/xjllb/doSave",
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
// 	var ss =  $("#bb").val();
// 	doExp("","${ctx}/xjllb/expExcel?abc="+ss,"现金流向表","${ctx}","");
// });

//导出Excel
$("#btn_exp").click(function() {
		var ss =  $("#bb").val();
		var jzpz = $("#jzpz").val();
		var val = $(".years").val();
		var ny = $("[name='ny']").val();
		$.ajax({
				type : "post",
				data : {abc:ss,jzpz:jzpz,year:val,ny:ny},
				url : "${ctx}/xjllb/expExcel2",
				success : function(val) {
				   FileDownload("${ctx}/file/fileDownload","现金流量表.xls",val.url);
				}
			});
	});
//点击打印
$("#btn_print").click(function(){
	var jzpz = $("#jzpz").val();
	var val = $(".years").val();
	location.href = "${ctx}/xjllb/goPrint?year="+val+"&jzpz="+jzpz;
});

//日常活动的现金流入小计
$(document).on("keyup","[tag='1']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='1']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='lrxj']").val(com);
	computer(dual);
});

//一、日常活动产生的现金流量：
$(document).on("keyup","[tag='2']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='2']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='lcxj']").val(com);
	computer(dual);
});



//二、投资活动产生的现金流量:
$(document).on("keyup","[tag='3']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='3']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='trxj']").val(com);
	computer(dual);
});


//投资活动的现金流入小计
$(document).on("keyup","[tag='4']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='4']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='tcxj']").val(com);
	computer(dual);
});




//三、筹资活动产生的现金流量
$(document).on("keyup","[tag='5']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='5']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='crxj']").val(com);
	computer(dual);
});




//筹资活动的现金流入小计
$(document).on("keyup","[tag='6']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='6']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='ccxj']").val(com);
	computer(dual);
});




function computer(dual){
	var lr = dual.find("[tag='lrxj']").val().replace(",","");
	var lc = dual.find("[tag='lcxj']").val().replace(",","");;
	var zl = Number(lr)-Number(lc);
	dual.find("[tag='rc']").val(zl);
}


function computer(dual){
	var lr = dual.find("[tag='lrxj']").val().replace(",","");
	var lc = dual.find("[tag='lcxj']").val().replace(",","");;
	var zl = Number(lr)-Number(lc);
	dual.find("[tag='rc']").val(zl);
}

</script>
</body>
</html>