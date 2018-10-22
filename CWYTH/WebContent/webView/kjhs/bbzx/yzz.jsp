<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>总账</title>
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
	#btn_search_more>span {
/* 		background-color:#00acec !important; */
/* 		color: white !important; */
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
/* 	text-align: right; */
	}
	#pzrq{
	text-align: centet !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="btn-group pull-right" role="group">
				<button type='button' class="btn btn-default" id="btn_sea">查询</button>
				<button type='button' class="btn btn-default" id="dyyl">打印预览</button>
				<button type="button" class="btn btn-default" id="btn_export">导出Excel</button>		
				</div>
			</div>
		</form>
	</div>
	
<div class="container-fluid"  id="div_print">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<h4 style="text-align:center;"><div style="padding-bottom:5px; width:50px;margin:0 auto;">总&nbsp;账</div></h4>
				<div style="text-align:center;margin-bottom:20px;">报表期间：${param.kjnd}年${qskjqj}月&nbsp;至&nbsp;${param.kjnd}年${jskjqj}月&emsp; &emsp;&emsp;科目:${param.kjkm}</div>
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:70%;margin:0 auto">
		        	<thead>
				        <tr id="header">
				            <th style="text-align:center; width: 150px" id="tr_pzrq">会计期间</th>
				            <th  style="text-align:center;" id="tr_kmbh">期初余额</th>
				            <th  style="text-align:center;" id="tr_zy">借方发生</th>				            
				            <th  style="text-align:center;" id="tr_kmmc">贷方发生</th>
				            <th  style="text-align:center;" id="tr_dfje">借方累计</th>	
				            <th  style="text-align:center;" id="tr_kmmc">贷方累计</th>
				            <th  style="text-align:center;" id="tr_kmmc">期末余额</th>
				        </tr>				       				       				        
					</thead>
				    <tbody>
				    	<c:forEach items="${yzzList }" var="item" varStatus="i">
				    		<c:choose>
				    			<c:when test="${i.count != fn:length(yzzList) }">
						    		<tr>
						    			<td style="text-align:center;">${item.kjqj }</td>
						    			<td style="text-align:right;">${item.qcye }</td>
						    			<td style="text-align:right;">${item.jffs }</td>
						    			<td style="text-align:right;">${item.dffs }</td>
						    			<td style="text-align:right;">${item.jflj }</td>
						    			<td style="text-align:right;">${item.dflj }</td>
						    			<td style="text-align:right;">${item.qmye }</td>
						    		</tr>
				    			</c:when>
				    			<c:otherwise>
				    				<tr id="ender">
						    			<td colspan="2" style="text-align:center;">合计</td>
						    			<td>${item.jffs }</td>
						    			<td>${item.dffs }</td>
						    			<td></td>
						    			<td></td>
						    			<td></td>
						    		</tr>
				    			</c:otherwise>
				    		</c:choose>
				    	</c:forEach>
				   </tbody>
				</table>						
		</div>
			</div>
			</div>
		</div>

<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
if("${param.kjkm}" == ""){
	select_commonWin("${ctx}/Zz/jumpWindow","报表条件-总账","600","395");
}

$(function() {	
    $("[id$=btn_sea]").click(function(){
    	 select_commonWin("${ctx}/Zz/jumpWindow","报表条件-总账","600","395");
   });
});

//导出Excel
$("#btn_export").click(function() {
		$.ajax({
				type : "post",
				url : "${ctx}/Zz/exportYzzExcel",
				success : function(val) {
				 	FileDownload("${ctx}/file/fileDownload","月总账.xls",val.url);
			   }
		});
});

//点击打印
 $("#dyyl").click(function(){
	 PreviewMytable();
}); 
 function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
		LODOP.NewPage();
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}table{width:100%!important;}</style>"
		LODOP.ADD_PRINT_HTM(80,"5%","90%",844,strStyle+document.getElementById("div_print").innerHTML);
		//LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("print_div").innerHTML);
		LODOP.SET_PRINT_STYLEA(2,"ReadOnly",0);
		LODOP.PREVIEW();	
	};
function autoFill(str){
	if(str.length < 2){
		str = '0'+str;
	}
	return str;
}
///双击跳转到明细账
$(document).on("dblclick","#mydatatables tr:not(#header,#ender)",function(){
	var kmbh = "${param.kjkm}";
	var bbqj = "${param.kjnd}年${qskjqj}月至${param.kjnd}年${jskjqj}月";
	var qskjqj = autoFill("${qskjqj}");
	var jskjqj = autoFill("${jskjqj}");
	var jsonStr = '{"list":[{"year":"${param.kjnd}","startMonth":"'+qskjqj+'","endMonth":"'+jskjqj+'","startKjkm":"${param.kjkm}","Kjkmmc":"","jzpz":"${param.bhwjzpz}"}]}';
	$.ajax({
			url:"${ctx}/mxz/paramSession",
			data:"params="+jsonStr+"&bbqj="+bbqj,
			dataType:"json",
			type:"post",
			complete:function(){
				location.href="${ctx}/mxz/toUrl?bbqj="+bbqj+"&kmbh="+kmbh+"&pageUrl=mxb";
			}
		});
});

</script>
</body>
</html>