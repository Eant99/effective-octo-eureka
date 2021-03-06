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
				<%-- <div class="form-group">
	                <label>选择年度</label>
	             	<input  type="text" class="input_info nd form-control" readonly style="border:1px solid #ccc;" value="${sysDate}"/>
	             	<i class="glyphicon glyphicon-calendar"></i> 
					&nbsp;
					
					 <label>是否包含未记账凭证&nbsp; </label>
					 <select style="width:60px;border:1px solid #ccc;" id="jzpz" class="form-control select">			
					 	<option value="0" <c:if test="${jzpz=='0'}">selected</c:if>>否</option>		
						<option value="1" <c:if test="${jzpz=='1'}">selected</c:if>>是</option>
					</select>
				</div> --%>
				<div class="btn-group pull-right" role="group">
<!-- 					<button type='button' class="btn btn-default" id="btn_save">保存</button> -->
<!-- 					<button type='button' class="btn btn-default" id="btn_print">打印预览</button> -->
<!-- 					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button> -->
						<button type="button" class="btn btn-default" id="btn_back">返回</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid" style="width: 60%">

		<center>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                 <span id="txt_bbmc" style="font-size: 16pt">收入费用表(月度)</span>
               </td>
	        </tr>
	    </center>
	    
	    <div class="btn-group pull-right" role="group">
	          <ul>
	          <li>
	    	 <td align="right" valign="middle" style="width: 400px; height: 20px;">
                              会政财02表             
                </td> 
                </tr>
                </li>
                <li>
                <tr>
                <td align="right" valign="bottom" style="width: 429px; height: 20px;">
                              单位：元
                </td> 
                </tr>
                </li>
                </ul>  
	    </div>
	   <div style="margin-top: 20px;position: absolute;">        	 
                               编制单位：高校财务一体化
	   </div>
	   <div style="margin-left: 48.5%;margin-top:20px">    
               1月
	   </div>
	</div>

<form id="mysave" method="post" >
<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:62%;margin:0 auto">
		        	<thead>
				        <tr>
				            <th style="text-align:center;width:30%">项目</th>
				            <th style="text-align:center;width:35%">本月数</th>
				            <th style="text-align:center;width:35%">本年累计数</th>				           
				        </tr>
				        <tr>
				        	<th><h5>一、本期收入</h5 ></th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(一)财政拨款收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    其中:政府性基金收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(二)事业收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(三)上级补助收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(四)附属单位上缴收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(五)经营收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(六)非同级财政拨款收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(七)投资收益</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(八)捐赠收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(九)利息收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(十)租金收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(十一)其他收入</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th><h5>二、本期费用</h5></th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(一)业务活动费用</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(二)单位管理费用</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(三)经营费用</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(四)资产处置费用</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(五)上缴上级费用</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(六)对附属单位补助费用</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(七)所得税费用 </th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th>&nbsp;&nbsp;&nbsp;(八)其他费用</th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
				        </tr>
				        <tr>
				        	<th><h5>三、本期盈余</h5></th>
				        	<th style="text-align:right;">100000.00</th>
				        	<th style="text-align:right;">50000.00</th>
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
				 				<input type="text" name="bys" flag="${list.FLAG}" class="form-control sign-number" ${list.READ} value="${list.BYS}"/>
				 			</td>
				 			<td class="num" role="bnljs">
				 				<input type="text" name="bnljs" flag="${list.FLAG}" class="form-control sign-number" ${list.READ} value="${list.BNLJS}"/>
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
	    				location.href="${ctx}/srfy/toUrl?status=year&ny="+val+"&bblx=0&jzpz="+jzpz;
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
//返回按钮
$("#btn_back")
		.click(
				function(e) {
					doOperate("${ctx}/mbxz/mbxz_list");
					return false;
				});
//记账凭证条件切换
$("#jzpz").change(function(){
	var jzpz = $(this).val();
	var val = $(".nd").val();
	if(val!=""&&jzpz!=""){
		location.href="${ctx}/srfy/toUrl?status=year&ny="+val+"&bblx=0&jzpz="+jzpz;
	}else{
		alert("存在空的查询条件!");
		return;
	}
});
//点击保存按钮
$("#btn_save").click(function(){
	var json = $("#mysave").serializeObject("czr","bblx");  //json对象				
	var jsonStr = JSON.stringify(json);
	$.ajax({
		url:"${ctx}/srfy/doSave",
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
// 	doExp("","${ctx}/srfy/expExcel?bblx="+bblx+"&sysDate="+sysDate+"&sszt="+sszt+"&jzpz="+jzpz+"&bzdw="+bzdw,"收入费用年度","${ctx}","");
// });
//导出Excel
	$("#btn_exp").click(
		function() {
			var bblx = $("[name='bblx']").val();
			var sysDate = $("[name='ny']").val();
			var sszt = $("[name='ztbh']").val();
			var jzpz = $("[name='jzpz']").val();
			var bzdw = $("[name='bzdw']").val();
			var val = $(".nd").val();
			var status = "year";
			var flag = "年度";
				$.ajax({
					type : "post",
					data : {bblx:bblx,sysDate:sysDate,sszt:sszt,jzpz:jzpz,bzdw:bzdw,status:status,ny:val},
					url : "${ctx}/srfy/expExcel2",
					success : function(val) {
					   FileDownload("${ctx}/file/fileDownload","收入费用年度.xls",val.url);
					}
				});
		});
//点击打印
$("#btn_print").click(function(){
	var bblx = $("[name='bblx']").val();
	var sysDate = $("[name='ny']").val();
	var sszt = $("[name='ztbh']").val();
	var jzpz = $("[name='jzpz']").val();
	var bzdw = $("[name='bzdw']").val();
	location.href = "${ctx}/srfy/goPrint?status=year&bblx="+bblx+"&sysDate="+sysDate+"&sszt="+sszt+"&jzpz="+jzpz+"&bzdw="+bzdw;
});
//计算本期收入
$(document).on("keyup","[flag='cbqsr']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[flag='cbqsr']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[flag='bqsr']").val(com.toFixed(2));
	computer(dual);
});
//计算费用
$(document).on("keyup","[flag='cbqfy']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[flag='cbqfy']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[flag='bqfy']").val(com.toFixed(2));
	computer(dual);
});
//计算本期盈余
function computer(dual){
	var bqsr = dual.find("[flag='bqsr']").val().replace(",","");
	var bqfy = dual.find("[flag='bqfy']").val().replace(",","");;
	var bqyy = Number(bqsr)-Number(bqfy);
	dual.find("[flag='bqyy']").val(bqyy.toFixed(2));
}
</script>
</body>
</html>