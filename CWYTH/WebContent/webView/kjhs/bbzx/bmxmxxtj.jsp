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
<body id="body">
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
<!-- 					<label>项目名称</label> -->
<!-- 					<input type="text"  class="input_info  form-control" name="jzpz" value=""   >  -->
<!-- 				</div> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label>负责人</label> -->
<!-- 					<input type="text"  class="input_info  form-control" name="jzpz" value=""   >  -->
<!-- 				</div> -->
				</div>
				<%-- <div class="input-group">
					<label>包含全部未记账凭证</label>
					<input type="radio" class="" name="jzpz" value="1"  <c:if test="${jzpz eq '1'}">checked</c:if>  > 是 &nbsp; &nbsp; &nbsp;
					<input type="radio" class=""  name="jzpz" value="0" <c:if test="${jzpz eq '0'}">checked</c:if>> 否
				</div> --%>
				
				&nbsp; &nbsp; &nbsp;
				<div class="input-group">
					<label>部门编号&nbsp;</label>
 					
 					<input type="text" id="txt_bmmc" class="" name="bmmc" value=""   > 
 					<input type="hidden" id="txt_bmbh" class="" name="bmbh" value=""   > 
 					<span class="input-group-btn"><button type="button" id="btn_bmbh" class="btn btn-link">选择</button></span>
						    
				</div>
				<div class="input-group">
					<label>项目大类  &nbsp; </label>
					<select id="drp_xb" class="form-control input-radius select2" name="xmdl"> 
								<option value="" >请选择</option>
								 <c:forEach var="xmdllist" items="${xmdllist}">
                           			<option value="${xmdllist.DM}" >${xmdllist.MC}</option>
                            	</c:forEach>
	             
					</select>
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
			<table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom: 50px;" border="1" bordercolor="#000000">
			
				<h2 style="text-align:center;">部门项目信息统计</h2>
				<div style="text-align:center;margin-bottom:0px;font-size:14px">报表期间：<sapn id="span_date">${date}</sapn></div>
				</div>
		       	<thead>
		 				<tr>				
		 					<th >部门</th>
						    <th >项目大类</th>
						    <th >项目名称</th>
						    <th >期初余额</th>
						    <th >期末余额</th>
						    <th >执行比例(%)</th>
			   			</tr>
					</thead>
					  
					<tbody>
					 </tbody>
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
	debugger
	$.ajax({
		type:"post",
		async : false, 
		url:"${ctx}/bmxmxxtj/getPageList",
		data:jsonStr+"&treebh=${param.dwbh}",
		dataType: "json",
		success:function(val){
			$("#span_date").html(datestr);
			$("tbody").html("");
			for (var i=0;i<val.length;i++){
				 var DWMC=val[i].DWMC;if(DWMC==null) DWMC='';
				 var BMBH=val[i].BMBH;if(BMBH==null) BMBH='';
				 var QCYE=val[i].QCYE;if(QCYE==null) QCYE='';
				 var QMYE=val[i].QMYE;if(QMYE==null) QMYE='';
				 var  xmdl= val[i].XMDL;
				 $("tbody").append(
							"<tr>"+
							"<td name='dwmc' value='"+DWMC+"' bmbh='"+BMBH+"' style='text-align:left;'>"+DWMC+"</td>"+
							"<td ></td>"+
							"<td ></td>"+
							"<td style='text-align:right'>"+QCYE+"</td>"+
							"<td style='text-align:right'>"+QMYE+"</td>"+
							"<td style='text-align:right'></td>"+
							"</tr>"
				);
				 for (var j=0;j<xmdl.length;j++){
					 var DLXMDLMC=xmdl[j].XMDLMC;if(DLXMDLMC==null) DLXMDLMC='';
					 var DLXMDL=xmdl[j].XMDL;if(DLXMDL==null) DLXMDL='';
					 var DLQCYE=xmdl[j].QCYE;if(DLQCYE==null) DLQCYE='';
					 var DLQMYE=xmdl[j].QMYE;if(DLQMYE==null) DLQMYE='';
					 var  xmxx= xmdl[j].XMXX;
					 $("tbody").append(
								"<tr>"+
								"<td ></td>"+
								"<td name='xmdl' value='"+DLXMDLMC+"' xmdl='"+DLXMDL+"' style='text-align:left;'>"+DLXMDLMC+"</td>"+
								"<td ></td>"+
								"<td style='text-align:right'>"+DLQCYE+"</td>"+
								"<td style='text-align:right'>"+DLQMYE+"</td>"+
								"<td style='text-align:right'></td>"+
								"</tr>"
					);
					  
					 for (var z=0;z<xmxx.length;z++){
						 var XXXMMC=xmxx[z].XMMC;if(XXXMMC==null) XXXMMC=''
						 var XXQCYE=xmxx[z].QCYE;if(XXQCYE==null) XXQCYE='';
						 var XXQMYE=xmxx[z].QMYE;if(XXQMYE==null) XXQMYE='';
						 var XXXMBH=xmxx[z].XXXMBH;if(XXXMBH==null) XXXMBH='';
						 var ZXZB=xmxx[z].ZXBZ;if(ZXZB==null) ZXZB='0.00';
						 $("tbody").append(
									"<tr>"+
									"<td ></td>"+
									"<td ></td>"+
									"<td name='xmxx' value='"+XXXMMC+"' xmbh='"+XXXMBH+"' style='text-align:left;'>"+XXXMMC+"</td>"+
									"<td style='text-align:right'>"+XXQCYE+"</td>"+
									"<td style='text-align:right'>"+XXQMYE+"</td>"+
									"<td style='text-align:right' class='number' >"+ZXZB+"</td>"+
									"</tr>"
						);
					 }
				 }	 
		   }
		},
	});  
}
$(document).on("click","#dyyl",function(){
	PreviewMytable();
});
$(document).on("click","#btn_bmbh",function(){
	select_commonWin("${ctx}/window/bmxmxxdwpage?controlId=txt_bmbh&controlName=txt_bmmc","部门信息","920","630");
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
	debugger
	var ul=document.getElementById("body");
	var obj=document.createElement("a");
	ul.appendChild(obj);
	 // 使用outerHTML属性获取整个table元素的HTML代码（包括<table>标签），然后包装成一个完整的HTML文档，设置charset为urf-8以防止中文乱码
    var html = "<html><head><meta charset='utf-8' /></head><body>" + document.getElementsByTagName("table")[0].outerHTML + "</body></html>";
    // 实例化一个Blob对象，其构造函数的第一个参数是包含文件内容的数组，第二个参数是包含文件类型属性的对象
    var blob = new Blob([html], { type: "application/vnd.ms-excel" });
    var a = document.getElementsByTagName("a")[0];
    // 利用URL.createObjectURL()方法为a元素生成blob URL
    a.href = URL.createObjectURL(blob);
    a.id = "dj";
    // 设置文件名
    a.download = "部门项目信息统计.xls";
	$("#dj")[0].click();
	$('#dj a').remove();
	
	
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