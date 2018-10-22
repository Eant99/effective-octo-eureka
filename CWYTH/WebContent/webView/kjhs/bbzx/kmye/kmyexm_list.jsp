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
	#ext-gen16{
	overflow: hidden important;
	}
</style>
<body>
<div class="fullscreen" style="overflow-y: auto;">
	<div class="search" id="searchBox" style="height: 30px;">
		<form id="myform" class="form-inline" action="">
		<input type="hidden" name="start" value="start" />
			<div class="box-header clearfix">
			</div>
    		<div class="search-simple">
    		<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more" style="width: 450px">
						<div class="form-group">
							<label>会计科目</label>
							<br>
							<input  type="text" name="kmbh1" types="ToBhss" id="txt_km" class="input_info  form-control" style="width:130px;" placeholder="科目编号" value=""  />
							<button type="button" id="btn_kjkm" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group">
							<label>起止会计科目</label>
							<br>
							<input   type="text" name="kmbh2" types="BHL" id="txt_km1" class="input_info  form-control" style="width:130px;" placeholder="科目编号" value=""  />
							<button type="button" id="btn_kjkm1" class="btn btn-link ">选择</button>
							<label>至</label>
							<input   type="text" name="kmbh3"  types="BHH" id="txt_km2" class="input_info  form-control" placeholder="科目编号" style="width:130px;" value=""  />
							<button type="button" id="btn_kjkm2" class="btn btn-link ">选择</button>
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_gjchaxun" >
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
<!-- 								<button type="button" class="btn btn-default" id="btn_cancel"> -->
<!-- 									取 消 -->
<!-- 								</button> -->
							</div>
						</div>
					</div>
				</div>
    			<div class="form-group" style="width:350px;">
					<label>会计期间</label>
					<input type="text" class="form-control input-radius year" style="width: 80px!important;" name="year" value="${year}"/>
					年
					<select class="form-control input-radius select" name="startMonth" style="width:50px;"> 
						<c:forEach var="months" items="${months}">
							<option value="${months.month}">${months.month}</option>
						</c:forEach>
 					</select>
 					月&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;
 					<select class="form-control input-radius select" name="endMonth" style="width:50px;">
						<c:forEach var="months" items="${months}">
							<option value="${months.month} " <c:if test="${mm eq months.month}">selected</c:if> >${months.month}</option>
						</c:forEach>
					</select>
					月
				</div>
				 <div class="form-group" style="width:260px; display:none;">
					<label>科目级次&nbsp;&nbsp;</label>
					<select class="form-control input-radius select" name="startJc">
					<option value="">请选择</option>
						<c:forEach items="${list}" var="jb1">
							<option value="${jb1.jb}">${jb1.jb}</option>
						</c:forEach>
					</select>
					至
					<select class="form-control input-radius select" name="endJc">
					<option value="">请选择</option>
						<c:forEach items="${list}" var="jb2">
							<option value="${jb2.jb}">${jb2.jb}</option>
						</c:forEach>
					</select>
				</div> 
				<div class="input-group">
					<label>包含全部未记账凭证</label>
					<input type="radio" class="" name="jzpz" value="1" checked  > 是 &nbsp; &nbsp; &nbsp;
					<input type="radio" class=""  name="jzpz" value="0" > 否
				</div>
				<button type="button" class="btn btn-primary" id="btn_chaxun"><i class="fa icon-chaxun"></i>查询</button>
				
				
				
				
				<div class="btn-group pull-right" role="group">
<!-- 					<button class="btn btn-default" type="button" id="btn1111">综合查询</button> -->
	                <button class="btn btn-default" type="button" id="dyyl">打印</button>
	                <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
            	</div>
			</div>
			<input type="hidden" name="end" value="end" />
        </form>
    </div>
	<div class="container-fluid" id="print_div" style="padding-bottom: 30px;">
		<div class='responsive-table'>
			<div class='scrollable-area' style="overflow-x:auto;">
			<div id="print_title">
				<h2 style="text-align:center;">项&nbsp;目&nbsp;余&nbsp;额&nbsp;表</h2>
				<div style="text-align:left;font-size:12px;float:left;width:33%">&emsp;</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：<span id="span_date">${date}</span></div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
				</div>
		        <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom: 50px;" border="0" bordercolor="#000000">
					<thead>
		 				<tr>
		 				 	<th rowspan="2">部门名称</th>
						    <th rowspan="2">项目名称</th>
						    <th rowspan="2">科目编号</th>
						    <th rowspan="2">科目名称</th>
						    <th colspan="2">期初余额</th>
						    <th colspan="2">本期发生</th>
						    <th colspan="2">期末余额</th>
			   			</tr>
			   			<c:if test="${mbmc == '1'}">
				   			<tr>
							    <th style="width:5%;">方向</th>
							    <th>余额</th>
							    <th>借方</th>
							    <th>贷方</th>
							    <th style="width:5%;">方向</th>
							    <th>余额</th>
				   			</tr>
			   			</c:if>
			   			<c:if test="${mbmc == '2'}">
				   			<tr>
							    <th>借方</th>
							    <th>贷方</th>
							    <th>借方</th>
							    <th>贷方</th>
							    <th>借方</th>
							    <th>贷方</th>
				   			</tr>
			   			</c:if>
					</thead>
					<tbody></tbody>
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
$(function(){

	//高级查询，科目弹窗
	$(document).on("click","[id=btn_kjkm]",function(){			
				select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId=km&controlId1=txt_km&flag=zhcx","科目信息","920","630");
		 });
		 $(document).on("click","[id=btn_kjkm1]",function(){			
				select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km1&controlId=txt_km1&flag=zhcx","科目信息","920","630");
		 });
		 $(document).on("click","[id=btn_kjkm2]",function(){			
				select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km2&controlId=txt_km2&flag=zhcx","科目信息","920","630");
		 });
	function jumpWindow(){
		select_commonWin("${ctx}/kmye/jumpWindow?gs=bm","报表条件-部门余额表","600","470");
	}
	
	$(document).on("click","#btn_chaxun",function(){
// 	$("#btn_chaxun").click(function(){
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
  		getTable(jsonStr,datestr);
	});   
	$("#btn_chaxun").click();
});
	
//综合查询
$("#btn_gjchaxun").click(function(){
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
	getTable(jsonStr,datestr);
	//点击查询后，自动隐藏高级查询区域
	if($("#btn_search_more").length > 0){
    	$("#btn_search_more").removeClass("btn-search-more");
   		$(".search-more").css("display", "none");
	}
});

function getTable(jsonStr,datestr){ 
	var sjc = $("[name=startJc]").val();	
	var ejc = $("[name=endJc]").val();
	var mbmc = "${mbmc}";//模版表的模版名称，如果是1，显示模版一，如果是2，显示模版二
	if("${param.qc}" == '123'){
    $.ajax({
		type:"post",
		async : false, 
		url:"${ctx}/kmye/getPageList",
		data:"sjc="+sjc+"&ejc="+ejc+"&"+jsonStr+"&gs=xm&bmh=${param.bmh}",
		dataType: "json",
		success:function(val){
			$("#span_date").html(datestr);
			$("tbody").html("");
			var mxList = val.mxList;
			for (var i=0;i<mxList.length;i++){
				 var KMBH=mxList[i].KMBH;if(KMBH==null) KMBH='';
				 var KMMC=mxList[i].KMMC;if(KMMC==null) KMMC='';
				 var QCFX=mxList[i].QCFX;if(QCFX==null) QCFX='';
				 var QCYE=mxList[i].QCYE;if(QCYE==null) QCYE='';
				 var BQJF=mxList[i].BQJF;if(BQJF==null) BQJF='';
				 var BQDF=mxList[i].BQDF;if(BQDF==null) BQDF='';
				 var QMFX=mxList[i].QMFX;if(QMFX==null) QMFX='';
				 var QMYE=mxList[i].QMYE;if(QMYE==null) QMYE='';
				 var XMBH=mxList[i].XMBH;if(XMBH==null) XMBH='';
				 var XMMC=mxList[i].XMMC;if(XMMC==null) XMMC='';
				 var BMBH=mxList[i].BMBH;if(BMBH==null) BMBH='';
				 var BMMC=mxList[i].BMMC;if(BMMC==null) BMMC='';
				 var QCJF=mxList[i].QCJF;if(QCJF==null) QCJF='';
				 var QCDF=mxList[i].QCDF;if(QCDF==null) QCDF='';
				 var QMJF=mxList[i].QMJF;if(QMJF==null) QMJF='';
				 var QMDF=mxList[i].QMDF;if(QMDF==null) QMDF='';
		     if(mbmc=='1'){
				 $("tbody").append(
							"<tr>"+
							"<td style='text-align:left;padding:0px 1px'>"+BMMC+"</td>"+
							"<td style='text-align:left;padding:0px 1px'>"+XMMC+"</td>"+
							"<td style='text-align:left;padding:0px 1px' name='kmbh' kmmc='"+KMMC+"' bmmc='"+BMMC+"' xmbh='"+XMBH+"' bmbh='"+BMBH+"' value='"+KMBH+"'>"+KMBH+"</td>"+
							"<td style='text-align:left;padding:0px 1px'>"+KMMC+"</td>"+
							"<td style='text-align:center;padding:0px 1px'>"+QCFX+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+QCYE+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
							"<td style='text-align:center;padding:0px 1px'>"+QMFX+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+QMYE+"</td>"+
							"</tr>"
				);
			}
		     if(mbmc=='2'){
				 $("tbody").append(
							"<tr>"+
							"<td style='text-align:left;padding:0px 1px'>"+BMMC+"</td>"+
							"<td style='text-align:left;padding:0px 1px'>"+XMMC+"</td>"+
							"<td style='text-align:left;padding:0px 1px' name='kmbh' kmmc='"+KMMC+"' bmmc='"+BMMC+"' xmbh='"+XMBH+"' bmbh='"+BMBH+"' value='"+KMBH+"'>"+KMBH+"</td>"+
							"<td style='text-align:left;padding:0px 1px'>"+KMMC+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+QCJF+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+QCDF+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+QMJF+"</td>"+
							"<td style='text-align:right;padding:0px 1px'>"+QMDF+"</td>"+
							"</tr>"
				);
			}	 
			}	
			var data = val.zjList[0];
			$("tbody").append("<tr class='nojs' id='one'>"+
					"<td colspan='4' rowspan='2' style='text-align:center;padding:0px 1px'>合计</td>"+
					"<td style='text-align:center;padding:0px 1px'>借</td>"+
					"<td id='qcj' style='text-align:right;padding:0px 1px'>"+(data.QCJ==null||data.QCJ==0.00?"":data.QCJ)+"</td>"+
					"<td rowspan='2' id='bqj' style='text-align:right;padding:0px 1px'>"+(data.BQJ==null||data.BQJ==0.00?"":data.BQJ)+"</td>"+
					"<td rowspan='2' id='bqd' style='text-align:right;padding:0px 1px'>"+(data.BQD==null||data.BQD==0.00?"":data.BQD)+"</td>"+
					"<td style='text-align:center;padding:0px 1px'>借</td>"+
					"<td id='qmj' style='text-align:right;padding:0px 1px'>"+(data.QMJ==null||data.QMJ==0.00?"":data.QMJ)+"</td>"+
					"</tr>"+
					"<tr class='nojs' id='two'>"+
					"<td style='text-align:center;padding:0px 1px'>贷</td>"+
					"<td id='qcd' style='text-align:right;padding:0px 1px'>"+(data.QCD==null||data.QCD==0.00?"":data.QCD)+"</td>"+
					"<td style='text-align:center;padding:0px 1px'>贷</td>"+
					"<td id='qmd' style='text-align:right;padding:0px 1px'>"+(data.QMD==null||data.QMD==0.00?"":data.QMD)+"</td>"+
					"</tr>"
					);
		},
	});  
	}else{
		 $.ajax({
				type:"post",
				async : false, 
				url:"${ctx}/kmye/getPageList",
				data:"sjc="+sjc+"&ejc="+ejc+"&"+jsonStr+"&gs=xm&treebh=${param.bmbh}_${param.xmbh}",
				dataType: "json",
				success:function(val){
					$("#span_date").html(datestr);
					$("tbody").html("");
					var mxList = val.mxList;
					for (var i=0;i<mxList.length;i++){
						 var KMBH=mxList[i].KMBH;if(KMBH==null) KMBH='';
						 var KMMC=mxList[i].KMMC;if(KMMC==null) KMMC='';
						 var QCFX=mxList[i].QCFX;if(QCFX==null) QCFX='';
						 var QCYE=mxList[i].QCYE;if(QCYE==null) QCYE='';
						 var BQJF=mxList[i].BQJF;if(BQJF==null) BQJF='';
						 var BQDF=mxList[i].BQDF;if(BQDF==null) BQDF='';
						 var QMFX=mxList[i].QMFX;if(QMFX==null) QMFX='';
						 var QMYE=mxList[i].QMYE;if(QMYE==null) QMYE='';
						 var XMBH=mxList[i].XMBH;if(XMBH==null) XMBH='';
						 var XMMC=mxList[i].XMMC;if(XMMC==null) XMMC='';
						 var BMBH=mxList[i].BMBH;if(BMBH==null) BMBH='';
						 var BMMC=mxList[i].BMMC;if(BMMC==null) BMMC='';
						 var QCJF=mxList[i].QCJF;if(QCJF==null) QCJF='';
						 var QCDF=mxList[i].QCDF;if(QCDF==null) QCDF='';
						 var QMJF=mxList[i].QMJF;if(QMJF==null) QMJF='';
						 var QMDF=mxList[i].QMDF;if(QMDF==null) QMDF='';
				     if(mbmc=='1'){
						 $("tbody").append(
									"<tr>"+
									"<td style='text-align:left;padding:0px 1px'>"+BMMC+"</td>"+
									"<td style='text-align:left;padding:0px 1px'>"+XMMC+"</td>"+
									"<td style='text-align:left;padding:0px 1px' name='kmbh' kmmc='"+KMMC+"' bmmc='"+BMMC+"' xmbh='"+XMBH+"' bmbh='"+BMBH+"' value='"+KMBH+"'>"+KMBH+"</td>"+
									"<td style='text-align:left;padding:0px 1px'>"+KMMC+"</td>"+
									"<td style='text-align:center;padding:0px 1px'>"+QCFX+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QCYE+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
									"<td style='text-align:center;padding:0px 1px'>"+QMFX+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QMYE+"</td>"+
									"</tr>"
						);
					}
				     if(mbmc=='2'){
						 $("tbody").append(
									"<tr>"+
									"<td style='text-align:left;padding:0px 1px'>"+BMMC+"</td>"+
									"<td style='text-align:left;padding:0px 1px'>"+XMMC+"</td>"+
									"<td style='text-align:left;padding:0px 1px' name='kmbh' kmmc='"+KMMC+"' bmmc='"+BMMC+"' xmbh='"+XMBH+"' bmbh='"+BMBH+"' value='"+KMBH+"'>"+KMBH+"</td>"+
									"<td style='text-align:left;padding:0px 1px'>"+KMMC+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QCJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QCDF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QMJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QMDF+"</td>"+
									"</tr>"
						);
					}	 
					}	
					var data = val.zjList[0];
					$("tbody").append("<tr class='nojs' id='one'>"+
							"<td colspan='4' rowspan='2' style='text-align:center;padding:0px 1px'>合计</td>"+
							"<td style='text-align:center;padding:0px 1px'>借</td>"+
							"<td id='qcj' style='text-align:right;padding:0px 1px'>"+(data.QCJ==null||data.QCJ==0.00?"":data.QCJ)+"</td>"+
							"<td rowspan='2' id='bqj' style='text-align:right;padding:0px 1px'>"+(data.BQJ==null||data.BQJ==0.00?"":data.BQJ)+"</td>"+
							"<td rowspan='2' id='bqd' style='text-align:right;padding:0px 1px'>"+(data.BQD==null||data.BQD==0.00?"":data.BQD)+"</td>"+
							"<td style='text-align:center;padding:0px 1px'>借</td>"+
							"<td id='qmj' style='text-align:right;padding:0px 1px'>"+(data.QMJ==null||data.QMJ==0.00?"":data.QMJ)+"</td>"+
							"</tr>"+
							"<tr class='nojs' id='two'>"+
							"<td style='text-align:center;padding:0px 1px'>贷</td>"+
							"<td id='qcd' style='text-align:right;padding:0px 1px'>"+(data.QCD==null||data.QCD==0.00?"":data.QCD)+"</td>"+
							"<td style='text-align:center;padding:0px 1px'>贷</td>"+
							"<td id='qmd' style='text-align:right;padding:0px 1px'>"+(data.QMD==null||data.QMD==0.00?"":data.QMD)+"</td>"+
							"</tr>"
							);
				},
			});  
	}
}
$(document).on("click","#btn_export",function(){
// $("#btn_export").click(function() {
// 	var kmbh = [];
// 	var checkbox = $("#mydatatables").find("[name='kmbh']").filter(":checked");
// 	if(checkbox.length > 0){
// 		checkbox.each(function(){
// 			kmbh.push($(this).val());
// 		});
// 		kmbh = kmbh.join("','");
// 	}else{
// 		kmbh = "";
// 	}
	$.ajax({
		type : "post",
		data : $("#myform").serialize()+"&gs=xm&treebh=${param.bmbh}_${param.xmbh}",
		url : "${ctx}/kmye/xmexpExcel",
		success : function(val) {
			var data = JSON.getJson(val);
//				alert(kmbh);
		 FileDownload("${ctx}/file/fileDownload","项目余额表.xls",data.url);
	   }
	});
});
</script>
<script >
// function jumpWindow(){
// 	select_commonWin("${ctx}/kmye/jumpWindow?gs=xm","报表条件-项目余额表","600","470");
// }
//刷新页面
function toUrl(params,date,gs){
	if(params){
		location.href = "${ctx}/kmye/kmyeList?flag=no&date="+date+"&gs=xm";
	}
}
// $("#btn_cx").click(function(){
// 	jumpWindow();
// });
function kz(){
	var tr = $("tbody").find("tr");
	var arys = ["","一、","二、","三、","四、","五、"];
	$.each(tr,function(i,v){
		var kmbh = $(this).find("[name='kmbh']").val();
		if(kmbh.length<3){
			//var name = arys[kmbh];
			$(this).find("td").eq(1).text("");
		}
	});
}
// function pjhj(){
// 	var check = $("[aria-live='polite']").text();
// 	$("tbody").append("<tr class='nojs' id='one'>"+
// 		"<td colspan='4' rowspan='2' style='text-align:center;'>合计</td>"+
// 		"<td>借</td>"+
// 		"<td id='qcj' style='text-align:right;'></td>"+
// 		"<td rowspan='2' id='bqj' style='text-align:right;'></td>"+
// 		"<td rowspan='2' id='bqd' style='text-align:right;'></td>"+
// 		"<td>借</td>"+
// 		"<td id='qmj' style='text-align:right;'></td>"+
// 		"</tr>"+
// 		"<tr class='nojs' id='two'>"+
// 		"<td>贷</td>"+
// 		"<td id='qcd' style='text-align:right;'></td>"+
// 		"<td>贷</td>"+
// 		"<td id='qmd' style='text-align:right;'></td>"+
// 		"</tr>"
// 		);
// 	$.ajax({
// 		url:"${ctx}/kmye/computer",
// 		dataType:"json",
// 		type:"post",
// 		success:function(data){
// 			if(data){
// 				$("#qcj").text(data.qcj);
// 				$("#qcd").text(data.qcd);
// 				$("#qmj").text(data.qmj);
// 				$("#qmd").text(data.qmd);
// 				$("#bqj").text(data.bqj);
// 				$("#bqd").text(data.bqd);
// 			}
// 		}
// 	});
// }

$(document).on("click","#dyyl",function(){
// $("#dyyl").click(function(){
	PreviewMytable();
});
$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	var kmmc = $(this).find("[name='kmbh']").attr("kmmc");
	var kmbh = $(this).find("[name='kmbh']").attr("value");
	var xmbh = $(this).find("[name='kmbh']").attr("xmbh");
	var bmmc = $(this).find("[name='kmbh']").attr("bmmc");
	var bmbh = bmmc.substr(bmmc.indexOf("(")+1,bmmc.indexOf(")")-1);
	var xmbhbmbh = xmbh+","+bmbh;
	var bbqj = $("#span_date").html();
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
//     var starttime = year+"-"+startMonth;
//     var endtime = year+"-"+endMonth;
	doOperate("${ctx}/xmmxz1/toUrl?kmmc="+kmmc+"&kmbh="+kmbh+"&bbqj="+bbqj+"&xmbhbmbh="+xmbhbmbh+"&StartMonth="+startMonth+"&endMonth="+endMonth+"&pz=1&xmye=xmye&ye=yes");
// 	doOperate("${ctx}/mxz/toUrl?gs=xm&xmbh="+xmbh+"&bmbh="+bmbh+"&bbqj="+bbqj);
});
function PreviewMytable(){
	var LODOP=getLodop();  
	LODOP.PRINT_INITA("7%","5%");
	LODOP.NEWPAGE();
	var strStyle="<style> table,td,th {border-collapse:collapse;font-size:12px} table{width:100%!important} thead tr th,tbody tr td{border:1px solid #000;}</style>"
	LODOP.ADD_PRINT_HTM(0,0,"94%","87%",document.getElementById("print_title").innerHTML);
	LODOP.ADD_PRINT_TABLE(0,0,"94%","87%",strStyle+document.getElementById("print_div").innerHTML);
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
	LODOP.SET_PRINT_PAGESIZE(2, 0,0 ,"A4");
	LODOP.PREVIEW();	
};

</script>
</body>
</html>