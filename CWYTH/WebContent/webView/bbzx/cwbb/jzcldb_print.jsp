<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<script src="${ctxStatic}/javascript/public/LodopFuncs.js"></script>
<style type="text/css">
thead th{
	text-align:center;
}
.form-input{
	border:none;
	width:100%;
}
.number{
	text-align:right;
}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_back">返回</button>
					<button type='button' class="btn btn-default" id="btn_print">打印</button>
				</div>
			</div>
		</form>
	</div>
<div id="print_div" style="width:100%;">
<div class="container-fluid" >
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:82%;margin:0 auto">
					<h4 style="text-align:center;">净资产变动表</h4>
					<h6 style="margin-left:49%;">${year}</h6>
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
				    			<td class="bl${i.index+1}">
				    				<input type="text" name="BNLJYY" class="form-input number" value="${list.BNLJYY}" />
				    			</td>
				    			<td class="bz${i.index+1}">
				    				<input type="text" name="BNZYJJ" class="form-input number" value="${list.BNZYJJ}" />
				    			</td>
				    			<td class="bq${i.index+1}">
				    				<input type="text" name="BNQYFTZ" class="form-input number" value="${list.BNQYFTZ}" />
				    			</td>
				    			<td class="bj${i.index+1}">
				    				<input type="text" name="BNJZCHJ" class="form-input number" value="${list.BNJZCHJ}" />
				    			</td>
				    			<td class="sl${i.index+1}">
				    				<input type="text" name="SNLJYY" class="form-input number" value="" />
				    			</td>
				    			<td class="sz${i.index+1}">
				    				<input type="text" name="SNZYJJ" class="form-input number" value="" />
				    			</td>
				    			<td class="sq${i.index+1}">
				    				<input type="text" name="SNQYFTZ" class="form-input number" value="" />
				    			</td>
				    			<td class="sj${i.index+1}">
				    				<input type="text" name="SNJZCHJ" class="form-input number" value="" />
				    			</td>
				    			<input type="hidden" name="SSZT" value="${sszt}" />
				    		</tr>
				    	</c:forEach>
				    </tbody>
				</table>						
			</div>
		</div>
	</div>
</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function () {
	 $("#btn_back").click(function(){
		 location.href="${ctx}/jzcbdb/toUrl?year=${year}&jzpz=${jzpz}";
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

//点击打印
$("#btn_print").click(function(){
		PreviewMytable();
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
	$("td[class*=z13").text("— —");
	
	$("td[class*=q2]").text("— —");
	$("td[class*=q5]").text("— —");
	$("td[class*=q6]").text("— —");
	$("td[class*=q7]").text("— —");
	$("td[class*=q8").text("— —");
	$("td[class*=q9]").text("— —");
	$("td[class*=q10]").text("— —");
	$("td[class*=q11]").text("— —");
	$("td[class*=q12]").text("— —");
}
function PreviewMytable(){
	var LODOP=getLodop();  
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
	var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse} table{width:100%!important;} input{width:100%!important;border:none;}</style>"
	LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle+document.getElementById("print_div").innerHTML);
	//LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("print_div").innerHTML);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
	LODOP.SET_PRINT_STYLEA(0,"Vorient",3);		
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
	LODOP.SET_PRINT_STYLEA(0,"FontColor","#FF0000");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.PREVIEW();	
};	
</script>
</body>
</html>