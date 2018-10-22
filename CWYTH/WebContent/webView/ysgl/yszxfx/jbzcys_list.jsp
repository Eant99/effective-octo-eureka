<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>基本支出预算汇总</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.th_style{
		background-color:transparent!important;
		width:31px;
	}
	th{
		text-align:center;
	}
	tr td:first-child{
		text-align:center;
	}
</style>
<!-- f/LodopFuncs.js"></script> -->

</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<input type="hidden" name="guid" value="" />
			<div class="search-simple">
				<div class="form-group">
					<label>确认状态</label>
	             	<select class="form-control" id="sel">
	             		<option value="0">未确认</option>
	             		<option value="1">已确认</option>
	             	</select>
				</div>	
				 <div class="form-group"> 
					<label>申报年度</label>					
 					<input type="text" class="form-control input-radius year" name="sbnd"  id="txt_sbnd" placeholder="请输入申报部年度" data-format="yyyy">
				 </div> 
				 				
				<button type="button" class="btn btn-primary" id="btn_searchforsbnd"><i class="fa icon-chaxun"></i>查 询</button>
 				<div class="btn-group pull-right" role="group">
				   <button type="button" class="btn btn-default" id="btn_submit">确认</button>
	               <!-- <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button> -->
	               <button type="button" class="btn btn-default" id="btn_print" onclick="PreviewMytable();">打印</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid" id="div2">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<center><h4><strong>基本支出预算</strong></h4></center>
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
		        		<tr>
				            <th colspan="8" style="text-align:center;" class="th_style">
				            	${map.firstYear}-${map.thirdYear}年度费用任务分解暨支出预算
				            </th>
				        </tr>
		        		<tr>
				            <th colspan="4" style="text-align:right;border-right:none;" class="th_style">编报部门：(公章)</th>
				            <th colspan="4" class="th_style" style="border-left:none;">(单位：万元,保留四位小数)</th>
				        </tr>
				        <tr>
				        	<th rowspan="2">经费类别</th>
				            <th rowspan="2">序号</th>
				            <th rowspan="2">支出经济科目</th>
				            <th colspan="3">基本支出经费(金额)</th>
				            <th rowspan="2">责任部门</th>
				            <th rowspan="2">说明</th>
				        </tr>
				        <tr>
				        	<th>${map.firstYear}</th>
				        	<th>${map.secondYear}</th>
				        	<th>${map.thirdYear}</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	datas();
	//SpanGrid();
  	//保存按钮
   	$("#btn_save").click(function(){
   		alert("保存成功！");
   	});
 	
	//导出Excel
   	$("#btn_exp").click(function(){
   		console.log("123213");
   		var guid = $("[name='guid']").val();
   		console.log("guid===="+guid);	
   		var json = searchJson(guid);
		console.log("json===="+json);		
   		doExp(guid,"${ctx}/yszxfx/expExceljbzc","基本支出预算汇总","${ctx}",guid.join(","));
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	doOperate("${ctx}/jsxxs/goJsxxPage?guid="+guid,"U");
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
var istbodynull;
function datas(){
	var sel = $("#sel").val();
    var sbnd = $("#txt_sbnd").val()
	var guid = "";
	$.ajax({
		url:"${ctx}/yszxfx/getJbzcList?sbnd="+sbnd,
		data:"qrzt="+sel,
		async:false,
		dataType:"json",
		success:function(data){
			istbodynull=data;
			if(data){
				$.each(data,function(i,v){
					var index = i+1;
					guid = guid + defaultNull(v.GUID)+",";
					$("tbody").append("<tr>"+
							"<td>"+defaultNull(v.MC)+"</td>"+
							"<td style='text-align:center;width:10px;'>"+index+"</td>"+
							"<td>"+defaultNull(v.KMMC)+"</td>"+
							"<td style='text-align:right;height:28px;'>"+defaultNull(v.DYNZC)+"</td>"+
							"<td style='text-align:right;'>"+defaultNull(v.DENZC)+"</td>"+
							"<td style='text-align:right;'>"+defaultNull(v.DSNZC)+"</td>"+
							"<td>"+defaultNull(v.DWMC)+"</td>"+
							"<td>"+defaultNull(v.BZ)+"</td>"
					);
				});
				guid = guid.substring(0,guid.lastIndexOf(","));
				$("[name='guid']").val(guid);
				autoRowSpan(tbody,0,0);
			}
		}
	});
}

/* function datas2(){
	var sel = $("#sel").val();
	var sbnd = $("#txt_sbnd").val()
	var guid = "";
	$.ajax({
		url:"${ctx}/yszxfx/getJbzcSbndList?sbnd="+sbnd,
		data:"qrzt="+sel,
		async:false,
		dataType:"json",
		success:function(data){
			istbodynull = data;
			if(data){
				$.each(data,function(i,v){					
					var index = i+1;
					guid = guid + defaultNull(v.GUID)+",";
					$("tbody").append("<tr>"+
							"<td>"+defaultNull(v.MC)+"</td>"+
							"<td style='text-align:center;width:10px;'>"+index+"</td>"+
							"<td>"+defaultNull(v.KMMC)+"</td>"+
							"<td style='text-align:right;'>"+defaultNull(v.DYNZC)+"</td>"+
							"<td style='text-align:right;'>"+defaultNull(v.DENZC)+"</td>"+
							"<td style='text-align:right;'>"+defaultNull(v.DSNZC)+"</td>"+
							"<td>"+defaultNull(v.DWMC)+"</td>"+
							"<td>"+defaultNull(v.BZ)+"</td>"
					);
				});
				guid = guid.substring(0,guid.lastIndexOf(","));
				$("[name='guid']").val(guid);
				autoRowSpan(tbody,0,0);
			}
		}
	});
} */

//选择下拉框重新绘制表格
$("#sel").change(function(){
	$("tbody").empty();
	datas();
});
$("#btn_searchforsbnd").click(function(){
		$("tbody").empty();
		datas();
	});
//点击确认按钮
/* $("#btn_submit").click(function(){
	
}); */
//设置合并行
function autoRowSpan(tb, row, col) {
	var lastValue = "";
	var value = "";
	var pos = 1;
	for (var i = row; i < tb.rows.length; i++) {
		value = tb.rows[i].cells[col].innerText;
		if (lastValue == value) {
			tb.rows[i].deleteCell(col);
		    tb.rows[i - pos].cells[col].rowSpan = tb.rows[i - pos].cells[col].rowSpan + 1;
			pos++;
		} else {
			lastValue = value;
			pos = 1;
		}
	}
}





//点击确认按钮
$("#btn_submit").click(function(){
	
	/* console.log("12312====="+istbodynull);
	console.log("11111111111111111==="+istbodynull.length); */
	if(istbodynull.length==0){
		alert("没有数据可提交");
		
	}else{
		var sel = $("#sel").val();
		if(sel==1){
			alert("当前已提交");
		}else{
			var guid = $("[name='guid']").val();
			var qrzt = $("#sel").val();
			$.ajax({
				url:"${ctx}/yszxfx/updateqQrzt",
				data:"guid="+guid,
				type:"post",
				success:function(){
					alert("确认成功！");
					$("tbody").empty();
					datas();
				}
			});
			
		} 
	
	}	
	
	
	
	
	
	
	
	
	
	
	
	
});
function PreviewMytable(){
	var LODOP=getLodop();  
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
	var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
	LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle+document.getElementById("div2").innerHTML);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
	LODOP.PREVIEW();	
};
</script>
</body>
</html>