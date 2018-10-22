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
.form-input{
	border:none;
	width:100%;
}
.sign-number{
	text-align:right;
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
	               <input  type="text" class="input_info years form-control" style="border:1px solid #ccc;" value="${year}"/>
	               <i class="glyphicon glyphicon-calendar"></i> 
					&nbsp;
				   <label>是否包含未记账凭证&nbsp; </label>
				   <select style="width:60px;border:1px solid #ccc;" id="jzpz" class="form-control select">			
				   		<option value="0" <c:if test="${jzpz=='0'}">selected</c:if>>否</option>		
						<option value="1" <c:if test="${jzpz=='1'}">selected</c:if>>是</option>
			      </select>
				</div>  --%>
				<div class="btn-group pull-right" role="group">
<!-- 					<button type='button' class="btn btn-default" id="btn_save">保存</button> -->
<!-- 					<button type='button' class="btn btn-default" id="btn_print">打印预览</button> -->
<!-- 					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button> -->
						<button type="button" class="btn btn-default" id="btn_back">返回</button>
				</div>
			</div>
		</form>
	</div>
<form id="saveForm" meyhod="post" >
<div class="container-fluid" >
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered" style="text-align:left;border-collapse:collapse;width:82%;margin:0 auto">
					<h4 style="text-align:center;">净资产变动表</h4>
					<h6 style="margin-left:49%;">${year}年</h6>
					<caption style="text-align:right;">
						会政财务03表
<%-- 					<h6 style="margin-left:49%;">${year}</h6> --%>
					<caption style="text-align:left;">
						编制单位：${bzdw.XXMC}
					<div style="float:right;margin-right:2%;">
						单位:元
					</div>
					</caption>
		        	<thead>
				        <tr>
				            <th rowspan="2" style="text-align:center;width:30%">项目</th>
				            <th colspan="4" style="text-align:center;width:35%">本年数</th>
				            <th colspan="4" style="text-align:center;width:35%">上年数</th>			
				        </tr>
				        <tr>
			            	<th style="text-align:center;">累计盈余</th>
				            <th style="text-align:center;">专用基金</th>	
				            <th style="text-align:center;">权益法调整</th>
				            <th style="text-align:center;">净资产合计</th>
				            <th style="text-align:center;">累计盈余</th>
				            <th style="text-align:center;">专用基金</th>	
				            <th style="text-align:center;">权益法调整</th>
				            <th style="text-align:center;">净资产合计</th>
				        </tr>
				        <tr>
				        	<th>一、上年年末余额</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>二、以前年度盈余调整(减少以”-“号填列)</th>
				        	<th>100000.00</th>
				        	<th>--</th>
				        	<th>--</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>三、本年年初余额</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>四、本年变动金额(减少以“-”号填列)</th>
				        	<th></th>
				        	<th>--</th>
				        	<th>--</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        
				        <tr>
				        	<th>(一)本年盈余</th>
				        	<th></th>
				        	<th></th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>(二)无偿调拨净资产</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>(三)归集调整预算结转结余</th>
				        	<th>100000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>(四)提取或设置专用基金</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th></th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>其中：从预算收入中提取 </th>
				        	<th>100000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>从预算结余中提取</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>设置的专用基金</th>
				        	<th>100000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>(五)使用专用基金</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>(六)权益法调整</th>
				        	<th>100000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
				        <tr>
				        	<th>五、本年年末余额</th>
				        	<th>100000.00</th>
				        	<th></th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        	<th>50000.00</th>
				        </tr>
					</thead>
				    <tbody>
				    	<c:forEach items="${list}" var="list" varStatus="i">
				    		<tr class="tr_${i.index+1}">
				    			<td>
				    				${list.XMMC}
				    				<input type="hidden" name="CZR" value="${login}" />
				    				<input type="hidden" name="XMBH" class="form-input" value="${list.XMMC}" readonly/>	
				    				<input type="hidden" name="BH" value="${list.BH}" />
				    				<input type="hidden" name="HC" value="${list.HC}" />
				    				<input type="hidden" name="YEAR" value="${year}" />
				    				<input type="hidden" name="JZPZ" value="${jzpz}" />
				    				<input type="hidden" name="BZDW" value="${bzdw.guid}" />
				    			</td>
				    			<td class="bl${list.BH}" role="bn">
				    				<input type="text" name="BNLJYY" class="form-input sign-number bl${list.BH}" value="${list.BNLJYY}" />
				    			</td>
				    			<td class="bz${list.BH}" role="bn">
				    				<input type="text" name="BNZYJJ" class="form-input sign-number bz${list.BH}" value="${list.BNZYJJ}" />
				    			</td>
				    			<td class="bq${list.BH}" role="bn">
				    				<input type="text" name="BNQYFTZ" class="form-input sign-number bq${list.BH}" value="${list.BNQYFTZ}" />
				    			</td>
				    			<td class="bj${list.BH}" role="bn">
				    				<input type="text" name="BNJZCHJ" class="form-input sign-number bj${list.BH}" value="${list.BNJZCHJ}" />
				    			</td>
				    			<td class="sl${list.BH}" role="sn">
				    				<input type="text" name="SNLJYY" class="form-input sign-number sl${list.BH}" value="" />
				    			</td>
				    			<td class="sz${list.BH}" role="sn">
				    				<input type="text" name="SNZYJJ" class="form-input sign-number sz${list.BH}" value="" />
				    			</td>
				    			<td class="sq${list.BH}" role="sn">
				    				<input type="text" name="SNQYFTZ" class="form-input sign-number sq${list.BH}" value="" />
				    			</td>
				    			<td class="sj${list.BH}" role="sn">
				    				<input type="text" name="SNJZCHJ" class="form-input sign-number sj${list.BH}" value="" />
				    			</td>
				    			<input type="hidden" name="SSZT" value="${sszt}" />
				    		</tr>
				    	</c:forEach>
				    		<tr>
				    			<td colspan="9" style="padding-left:50px;">注:&nbsp;&nbsp;&nbsp;&nbsp;"— —"标识单元格不需填列</td>
				    		</tr>
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
	    			location.href="${ctx}/jzcbdb/toUrl?year="+val+"&jzpz="+jzpz;
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
	snDatas();
	kz();
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
	var val = $(".years").val();
	location.href="${ctx}/jzcbdb/toUrl?year="+val+"&jzpz="+jzpz;
});
//点击保存按钮
$("#btn_save").click(function(){
	var json = $("#saveForm").serializeObject("CZR","SSZT");  //json对象				
	var jsonStr = JSON.stringify(json);
	$.ajax({
		url:"${ctx}/jzcbdb/doSave",
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
// 	doExp("","${ctx}/jzcbdb/expExcel?","净资产流动表","${ctx}","");
// });

//导出Excel
$("#btn_exp").click(function() {
		var val = $(".years").val();
		var jzpz = $("#jzpz").val();
			$.ajax({
				type : "post",
				data : {year:val,jzpz:jzpz},
				url : "${ctx}/jzcbdb/expExcel2",
				success : function(val) {
				   FileDownload("${ctx}/file/fileDownload","净资产流动表.xls",val.url);
				}
			});
	});
//点击打印
$("#btn_print").click(function(){
	var jzpz = $("#jzpz").val();
	var val = $(".years").val();
	location.href = "${ctx}/jzcbdb/goPrint?year="+val+"&jzpz="+jzpz;
});
function snDatas(){
	var year = "${year}";
	var sszt = "${sszt}";
	var jzpz = "${jzpz}";
	year = Number(year)-1;
	var params = "year="+year+"&jzpz="+jzpz+"&sszt="+sszt;
	$.ajax({
		url:"${ctx}/jzcbdb/SnDatas",
		data:params,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data){
				$.each(data,function(i,v){
					var num = i+1;
					$("td[class*=sl"+num+"]").find("input").val(v.BNLJYY);
					$("td[class*=sz"+num+"]").find("input").val(v.BNZYJJ);
					$("td[class*=sq"+num+"]").find("input").val(v.BNQYFTZ);
					$("td[class*=sj"+num+"]").find("input").val(v.BNJZCHJ);
				});
			}
		}
	});
}
function kz(){
	$("td[class*=l9]").text("— —");
	$("td[class*=l11]").text("— —");
	$("td[class*=l13]").text("— —");
	
	$("td[class*=z2]").text("— —");
	$("td[class*=z5]").text("— —");
	$("td[class*=z6]").text("— —");
	$("td[class*=z7]").text("— —");
	$("td[class*=z13]").text("— —");
	
	$("td[class*=q2]").text("— —");
	$("td[class*=q5]").text("— —");
	$("td[class*=q6]").text("— —");
	$("td[class*=q7]").text("— —");
	$("td[class*=q8").text("— —");
	$("td[class*=q9]").text("— —");
	$("td[class*=q10]").text("— —");
	$("td[class*=q11]").text("— —");
	$("td[class*=q12]").text("— —");
	$("[class*=14]").attr("readonly","readonly");
	$("[class*=bj],[class*=sj]").attr("readonly","readonly");
	$("tbody").find("tr").eq(2).find("input").attr("readonly","readonly");
	$("tbody").find("tr").eq(3).find("input").attr("readonly","readonly");
	$("tbody").find("tr").eq(7).find("input").attr("readonly","readonly");
}
//计算
function check(val){
	val = val+"";
	val = val.replace(",","");
	if(val==null||val=="null"||val=="undefined"||isNaN(val)||val==""){
		return 0;
	}
	return val;
}

$(document).on("keyup",".sign-number",function(){
	var td = $(this).parent("td");
	var val = $(this).val();
	val = check(val);
	var role = td.attr("role");
	var tr = td.parents("tr");
	var ljyy = tr.find("[role='"+role+"']").find("[name$=LJYY]").val();
	var zyjj = tr.find("[role='"+role+"']").find("[name$=ZYJJ]").val();
	var qyftz = tr.find("[role='"+role+"']").find("[name$=QYFTZ]").val();
	ljyy = check(ljyy);
	zyjj = check(zyjj);
	qyftz = check(qyftz);
	var money = 0;
	money = Number(ljyy)+Number(zyjj)+Number(qyftz);
	money = Number(check(money));
	tr.find("[role='"+role+"']").find("[name$=JZCHJ]").val(money.toFixed(2));
});
$(document).on("blur",".sign-number",function(){
	var td = $(this).parent("td");
	var role = td.attr("role");
	//bnnm(role);
});

$(document).on("keyup","[class*=l5],[class*=l6],[class*=l7],[class*=l8],[class*=l12]",function(){
	var role = $(this).parent("td").attr("role");
	var moneyl = 0;
	var bl5 = check($("[role='"+role+"']").find("[class*=l5]").val());
	var bl6 = check($("[role='"+role+"']").find("[class*=l6]").val());
	var bl7 = check($("[role='"+role+"']").find("[class*=l7]").val());
	var bl8 = check($("[role='"+role+"']").find("[class*=l8]").val());
	var bl12 = check($("[role='"+role+"']").find("[class*=l12]").val());
	moneyl = Number(bl5)+Number(bl6)+Number(bl7)+Number(bl8)+Number(bl12);
	$("[role='"+role+"']").find("[class*=l4]").val(moneyl.toFixed(2));
	$("[role='"+role+"']").find("[class*=l4]").keyup();
	bnnm(role);
});

$(document).on("keyup","[class*=z8],[class*=z12]",function(){
	var role = $(this).parent("td").attr("role");
	var money2 = 0;
	var z8 = check($("[role='"+role+"']").find("[class*=z8]").val());
	var z12 = check($("[role='"+role+"']").find("[class*=z12]").val());
	money2 = Number(z8)+Number(z12);
	$("[role='"+role+"']").find("[class*=z4]").val(money2.toFixed(2));
	$("[role='"+role+"']").find("[class*=z4]").keyup();
	bnnm(role);
});

$(document).on("keyup","[class*=q13]",function(){
	var role = $(this).parent("td").attr("role");
	var money3 = 0;
	var q13 = check($("[role='"+role+"']").find("[class*=q13]").val());
	money3 = Number(q13);
	$("[role='"+role+"']").find("[class*=q4]").val(money3.toFixed(2));
	$("[role='"+role+"']").find("[class*=q4]").keyup();
	bnnm(role);
});

$(document).on("keyup","[class*=l5],[class*=l6],[class*=l7],[class*=l8],[class*=l12]",function(){
	var role = $(this).parent("td").attr("role");
	var moneyl = 0;
	var bl5 = check($("[role='"+role+"']").find("[class*=l5]").val());
	var bl6 = check($("[role='"+role+"']").find("[class*=l6]").val());
	var bl7 = check($("[role='"+role+"']").find("[class*=l7]").val());
	var bl8 = check($("[role='"+role+"']").find("[class*=l8]").val());
	var bl12 = check($("[role='"+role+"']").find("[class*=l12]").val());
	moneyl = Number(bl5)+Number(bl6)+Number(bl7)+Number(bl8)+Number(bl12);
	$("[role='"+role+"']").find("[class*=l4]").val(moneyl.toFixed(2));
	$("[role='"+role+"']").find("[class*=l4]").keyup();
	bnnm(role);
});

$(document).on("keyup","[class*=l10]",function(){
	var role = $(this).parent("td").attr("role");
	var money = 0;
	var l10 = check($("[role='"+role+"']").find("[class*=l10]").val());
	money = Number(l10);
	$("[role='"+role+"']").find("[class*=l8]").val(money.toFixed(2));
	$("[role='"+role+"']").find("[class*=l8]").keyup();
});

$(document).on("keyup","[class*=z10],[class*=z9],[class*=z11]",function(){
	var role = $(this).parent("td").attr("role");
	var money = 0;
	var z10 = check($("[role='"+role+"']").find("[class*=z10]").val());
	var z9 = check($("[role='"+role+"']").find("[class*=z9]").val());
	var z11 = check($("[role='"+role+"']").find("[class*=z11]").val());
	money = Number(z10)+Number(z9)+Number(z11);
	$("[role='"+role+"']").find("[class*=z8]").val(money.toFixed(2));
	$("[role='"+role+"']").find("[class*=z8]").keyup();
});

$(document).on("keyup","[class*=l1],[class*=l2]",function(){
	var role = $(this).parent("td").attr("role");
	var money = 0;
	var l1 = check($("[role='"+role+"']").find("[class*=l1]").val());
	var l2 = check($("[role='"+role+"']").find("[class*=l2]").val());
	money = Number(l1)+Number(l2);
	$("[role='"+role+"']").find("[class*=l3]").val(money.toFixed(2));
	$("[role='"+role+"']").find("[class*=l3]").keyup();
	bnnm(role);
});

$(document).on("keyup","[class*=z1]",function(){
	var role = $(this).parent("td").attr("role");
	var money = 0;
	var z1 = check($("[role='"+role+"']").find("[class*=z1]").val());
	money = Number(z1);
	$("[role='"+role+"']").find("[class*=z3]").val(money.toFixed(2));
	$("[role='"+role+"']").find("[class*=z3]").keyup();
	bnnm(role);
});

$(document).on("keyup","[class*=q1]",function(){
	var role = $(this).parent("td").attr("role");
	var money = 0;
	var zq = check($("[role='"+role+"']").find("[class*=q1]").val());
	money = Number(zq);
	$("[role='"+role+"']").find("[class*=q3]").val(money.toFixed(2));
	$("[role='"+role+"']").find("[class*=q3]").keyup();
	bnnm(role);
});

$(document).on("keyup","[class*=l3],[class*=l4]",function(){
	var role = $(this).parent("td").attr("role");
	var money = 0;
	var l3 = check($("[role='"+role+"']").find("[class*=l3]").val());
	var l4 = check($("[role='"+role+"']").find("[class*=l4]").val());
	money = Number(l3)+Number(l4);
	$("[role='"+role+"']").find("[class*=l14]").val(money.toFixed(2));

});

$(document).on("keyup","[class*=z3],[class*=z4]",function(){
	var role = $(this).parent("td").attr("role");
	var money = 0;
	var z3 = check($("[role='"+role+"']").find("[class*=z3]").val());
	var z4 = check($("[role='"+role+"']").find("[class*=z4]").val());
	money = Number(z3)+Number(z4);
	$("[role='"+role+"']").find("[class*=z14]").val(money.toFixed(2));

});

$(document).on("keyup","[class*=q3],[class*=q4]",function(){
	var role = $(this).parent("td").attr("role");
	var money = 0;
	var q3 = check($("[role='"+role+"']").find("[class*=q3]").val());
	var q4 = check($("[role='"+role+"']").find("[class*=q4]").val());
	money = Number(q3)+Number(q4);
	$("[role='"+role+"']").find("[class*=q14]").val(money.toFixed(2));

});

function bnnm(role){
	var money = 0;
	var l14 = check($("[role='"+role+"']").find("[class*=l14]").val());
	var z14 = check($("[role='"+role+"']").find("[class*=z14]").val());
	var q14 = check($("[role='"+role+"']").find("[class*=q14]").val());
	money = Number(l14)+Number(z14)+Number(q14);
	$("[role='"+role+"']").find("[class*=j14]").val(money.toFixed(2));
}
</script>
</body>
</html>