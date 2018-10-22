<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	tr td{
		line-height:30px !important;
		text-align: center;
		}
	tr th{
		text-align:center;
	}
</style>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox"style="height:35px;">
		<form id="myform" class="form-inline" action="">
			<input type="hidden" name="start" value="start" />
			<div class="box-header clearfix">
			</div>
    		<div class="search-simple">
    			<div class="form-group" style="width:350px;">
					<label>会计期间</label>
					<input type="text" class="form-control input-radius year" style="width: 80px!important;" name="year" value="${year}"/>
					年
					<select class="form-control input-radius select" name="startMonth" style="width:50px;"> 
						<c:forEach var="months" items="${months}">
							<option value="${months.month}" <c:if test="${startMonth eq months.month}">selected</c:if>>${months.month}</option>
						</c:forEach>
 					</select>
 					月&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;
 					<select class="form-control input-radius select" name="endMonth" style="width:50px;">
						<c:forEach var="months" items="${months}">
							<option value="${months.month}" <c:if test="${endMonth eq months.month}">selected</c:if>>${months.month}</option>
						</c:forEach>
					</select>
					月
<!-- 				<div class="form-group"> -->
<!-- 					<label>部门名称</label> -->
<!-- 					<input type="text"  class="input_info  form-control" name="jzpz" value=""   >  -->
<!-- 				</div> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label>项目名称</label> -->
<!-- 					<input type="text"  class="input_info  form-control" name="jzpz" value=""   >  -->
<!-- 				</div> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label>负责人</label> -->
<!-- 					<input type="text"  class="input_info  form-control" name="jzpz" value=""   >  -->
<!-- 				</div> -->
				</div>
				<div class="input-group">
					<label>包含全部未记账凭证</label>
					<input type="radio" class="" name="jzpz" value="1"  <c:if test="${jzpz eq '1'}">checked</c:if>  > 是 &nbsp; &nbsp; &nbsp;
					<input type="radio" class=""  name="jzpz" value="0" <c:if test="${jzpz eq '0'}">checked</c:if>> 否
				</div>
				<button type="button" class="btn btn-primary" id="btn_chaxun"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	                <button class="btn btn-default" type="button" id="dyyl">打印</button>
	                <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
            	</div>
			</div>
			<input type="hidden" name="end" value="end" />
			<div class="box-header clearfix">
			</div>
        </form>
    </div>
	<div class="container-fluid" id="print_div" style="padding-bottom: 30px;">
		<div class='responsive-table'>
			<div class='scrollable-area'  style="overflow-x:auto;">
			<div id="print_title">
				<h2 style="text-align:center;">项目经费余额表</h2>
				<div style="text-align:center;margin-bottom:0px;font-size:14px">报表期间：<sapn id="span_date">${date}</sapn></div>
				</div>
		        <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom: 50px;" border="1" bordercolor="#000000">
					<thead>
		 				<tr>
		 					<th >部门名称</th>
						    <th >项目名称</th>
						    <th >期初余额</th>
						    <th >借方金额</th>
						    <th >贷方金额</th>
						    <th >期末余额</th>
			   			</tr>
					</thead>
					
					<tbody>
					</tbody>
					<tfoot>
					   <tr class="hide" style="text-align:right;">
						   <td width="100%" colspan="10">
						   <font tdata="PageNO" format="00" >第##页，</font><font tdata="PageCount" format="00">共##页</font>
						   </td>
					   </tr>
				   </tfoot>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%> 
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function(){$(".right").attr("style-align","text-align: right")});
$(function(){
 	$(document).on("click","#btn_chaxun",function(){
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
 		var jsonStr = $("#myform").serialize();
  		var datestr = year+"年"+startMonth+"月至"+endMonth+"月";
  		getTable(jsonStr,datestr)
 	});
	$("#btn_chaxun").click();
//    kz();
});
	

function getTable(jsonStr,datestr){ 
	$.ajax({
		type:"post",
		async : false, 
		url:"${ctx}/xmjfye/getPageList",
		data:jsonStr+"&treebh=${param.dwbh}",
		dataType: "json",
		success:function(val){
			$("#span_date").html(datestr);
			$("tbody").html("");
			var mxList = val.mxList;
			for (var i=0;i<mxList.length;i++){
				 var BMBH=mxList[i].BMBH;if(BMBH==null) BMBH='';
				 var BMMC=mxList[i].BMMC;if(BMMC==null) BMMC='';
				 var XMBH=mxList[i].XMBH;if(XMBH==null) XMBH='';
				 var XMMC=mxList[i].XMMC;if(XMMC==null) XMMC='';
				 var QCYE=mxList[i].QCYE;if(QCYE==null) QCYE='';
				 var JFJE=mxList[i].JFJE;if(JFJE==null) JFJE='';
				 var DFJE=mxList[i].DFJE;if(DFJE==null) DFJE='';
				 var QMYE=mxList[i].QMYE;if(QMYE==null) QMYE='';
				 $("tbody").append(
							"<tr>"+
							"<td name='bmmc' value='"+BMMC+"' bmbh='"+BMBH+"' style='text-align:left;'>"+BMMC+"</td>"+
							"<td name='xmmc' value='"+XMMC+"' xmbh='"+XMBH+"' style='text-align:left;' >"+XMMC+"</td>"+
							"<td style='text-align:right'>"+QCYE+"</td>"+
							"<td style='text-align:right'>"+JFJE+"</td>"+
							"<td style='text-align:right'>"+DFJE+"</td>"+
							"<td style='text-align:right'>"+QMYE+"</td>"+
							"</tr>"
				);
		   }
		},
	});  
}
// function jumpWindow(){
// 	select_commonWin("${ctx}/kmye/jumpWindow?gs=bm","报表条件-部门余额表","600","470");
// }
// //刷新页面
// function toUrl(params,date,gs){
// 	if(params){
// 		location.href = "${ctx}/kmye/kmyeList?flag=no&date="+date+"&gs=bm";
// 	}
// }

$(document).on("click","#dyyl",function(){
	PreviewMytable();
});
//双击事件 id="header"
$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	//部门名称 、部门编号
	var bmbh = $(this).find("[name='bmmc']").attr("bmbh");
	var bmmc = $(this).find("[name='bmmc']").attr("value");
	//项目名称 、项目编号
	var xmbh = $(this).find("[name='xmmc']").attr("xmbh");
	var xmmc = $(this).find("[name='xmmc']").attr("value");
	var year = $("[name='year']").val();
	var jzpz = $('input[name="jzpz"]').filter(':checked').val();
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
	var bbqj = $("#span_date").html();
	doOperate("${ctx}/xmjfye/toUrl?bmbh="+bmbh+"&bmmc="+bmmc+"&xmbh="+xmbh+"&xmmc="+xmmc+"&startMonth="+startMonth+"&endMonth="+endMonth+"&year="+year+"&jzpz="+jzpz+"&from=xmjfyeb&bbqj="+bbqj+"&dwbh=${param.dwbh}");
});
$("#btn_export").click(function() {
	var kmbh = [];
	var checkbox = $("#mydatatables").find("[name='kmbh']").filter(":checked");
	if(checkbox.length > 0){
	checkbox.each(function(){
		kmbh.push($(this).val());
	});
	kmbh = kmbh.join("','");
	}else{
		kmbh = "";
	}
	$.ajax({
		type : "post",
		data:$("#myform").serialize()+"&gs=bm&treebh=${param.dwbh}",
		url : "${ctx}/xmjfye/doExp",
			success : function(val) {
			var data = JSON.getJson(val);
			FileDownload("${ctx}/file/fileDownload","项目经费余额表.xls",data.url);
	   }
	});
});
function PreviewMytable(){
	var LODOP=getLodop();  
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
	LODOP.NewPage();
	var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}table{width:100%!important;}</style>"
		LODOP.ADD_PRINT_HTM("5%","5%","85%","85%",document.getElementById("print_title").innerHTML);
	LODOP.ADD_PRINT_TABLE("5%","5%","85%","85%",strStyle+document.getElementById("print_div").innerHTML);
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
	LODOP.SET_PRINT_PAGESIZE(1, 2100, 2970,"打印");
	LODOP.PREVIEW();	
};
function kz(){
	var tr = $("tbody").find("tr");
	var arys = ["","一、","二、","三、","四、","五、"];
	$.each(tr,function(i,v){
		var kmbh = $(this).find("[name=kmbh]").val();
		if(kmbh.length<3){
		}
	});
}
</script>
</body>
</html>