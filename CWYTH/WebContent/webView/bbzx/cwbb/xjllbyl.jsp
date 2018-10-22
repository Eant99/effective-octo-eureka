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
<form id="mysave" method="post" >
<div class="container-fluid" >
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:82%;margin:0 auto">
					<h4 style="text-align:center;">现金流量表</h4>
					<h6 style="margin-left:49.3%;">${year}</h6>
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
<%-- 				 				<input type="text" class="form-control" name="xmmc" value="${list.XMMC }" readonly/> --%>
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="${list.XMMC }">
				 				<input type="hidden" name="ny" value="${sysDate}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="ztbh" value="${sszt}">
				 				<input type="hidden" name="hc" value="${list.HC}">
				 			</td>
				 			<td class="num">
				 			${list.BNJE }
<%-- 				 				<input type="text" name="snje" class="form-control num" value="${list.BNJE }" readonly/> --%>
				 				<input type="hidden" name="snje" value="${list.BNJE}">
				 			</td>
				 			<td class="num">
				 			${list.SNJE }
<%-- 				 				<input type="text" name="bnje" class="form-control num" value="${list.SNJE }" readonly/> --%>
				 				<input type="hidden" name="bnje" value="${list.SNJE }">
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
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function () {
	 $("#btn_back").click(function(){
		 location.href="${ctx}/xjllb/toUrl?year=${year}&jzpz=${jzpz}";
	 });
});
$(function () {
	$(document).on("focus", ".years", function(){
	    $(this).on("click", function() {
	    	WdatePicker({
	    		dateFmt:'yyyy',
	    		onpicked:function(){
	    			alert("success");
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
	$("#jzpz").change(function(){
		var jzpz = $(this).val();
		var val = $(".years").val();
		location.href="${ctx}/xjllb/toUrl?year="+val+"&jzpz="+jzpz;
	});
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
		url:"${ctx}/xjllb/SnDatas",
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


//点击导出
$("#btn_exp").click(function(){
	doExp("","${ctx}/xjllb/expExcel?","现金流向表","${ctx}","");
});
//点击打印
$("#btn_print").click(function(){
		PreviewMytable();
	});


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