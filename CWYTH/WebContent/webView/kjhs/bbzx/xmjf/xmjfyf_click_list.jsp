 <%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>经济科目明细账</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
		
	tr td{
		line-height:30px !important;
		text-align: center;
		}
	tr th{
		text-align:center;
	}
</style>
</head>
<body>
<div class="fullscreen">
<input type="hidden" name="kmbh" id="tr_kmbh" value="${kmbh}"/>
<div class="search" id="searchBox" style="padding-top: 0px;height: 40px;">
	<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		<div class="search-simple">
			<div class="btn-group pull-right" role="group">
			    <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
				<button type='button' class="btn btn-default" id="btn_back">返回</button>
			</div>
		</div>
	</form>
</div>
<div class="container-fluid" id="print_div" style="padding-bottom: 10px;">
		<div class="responsive-table">
			<div class="scrollable-area" style="overflow-x:auto;"> 
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:100%;margin:0 auto" border="0">
					<div id="print_title">
					<h2 style="text-align:center;"><div style="padding-bottom:5px;margin:0 auto;display:inline-block;"><a style="color:black;text-decoration: none;">${xmmc}&nbsp;项目经费明细账</a></div></h2>
					<div style="text-align:left;font-size:12px;float:left;width:33%">&emsp;</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">会计期间：${param.bbqj}</div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
					</div>
		        	<thead>
				        <tr id="header">
				             <th style="text-align:center;width:7%" id="tr_pzrq">凭证日期</th>
				            <th style="text-align:center;width:6%" id="tr_pzlx">凭证类型</th>
				            <th style="text-align:center;width:5%" id="tr_pzh">凭证号</th>
				            <th style="text-align:center;width:15%" id="tr_zy">摘要</th>
				            <th style="text-align:center;width:8%" id="tr_bmmc">部门名称</th>
				            <th style="text-align:center;" id="tr_xmmc">项目名称</th>
				            <th style="text-align:center;" id="tr_kjkm">会计科目</th>
				            <th style="text-align:center;" id="tr_jjkm">经济科目</th>
				            <th style="text-align:center;"  id="tr_jfje">借方金额</th>
				            <th style="text-align:center;" id="tr_dfje">贷方金额</th>	
				            <th style="text-align:center;" id="tr_ye">余额</th>
				        </tr>
					</thead>
				    <tbody>
				   </tbody>
				   <tfoot>
				   <tr class="hide" style="text-align:right;">
				   <td width="100%" colspan="13">
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
 $(function () {
	 //返回
	 $(document).on("click","#btn_back",function(){
		 window.history.go(-1);
	 });
	var src = "${ctx}/xmjfye/getMxzPageList?bmbh=${bmbh}&xmbh=${xmbh}&year=${year}&startMonth=${startMonth}&endMonth=${endMonth}&jzpz=${jzpz}";
   var bmbh = "${bmbh}";
   if(bmbh!=""&&bmbh!=null&&bmbh!="undefined"){
	   $.ajax({
			type:"post",
			async : false, 
			url:src,
			dataType: "json",
			success:function(val){
				for (var i=0;i<val.length;i++){
					 var PZRQ=val[i].PZRQ;if(PZRQ==null) PZRQ='';
					 var PZH=val[i].PZH;if(PZH==null) PZH='';
					 var GUID=val[i].GUID;if(GUID==null) GUID='';
					 var PZBHGUID=val[i].PZBHGUID;if(PZBHGUID==null) PZBHGUID='';
					 var PZLX=val[i].PZLXMC;if(PZLX==null) PZLX='';
					 var ZY=val[i].ZY;if(ZY==null) ZY='';
					 var WLDWMC=val[i].WLDWMC;if(WLDWMC==null) WLDWMC='';
					 var KJKM=val[i].KJKM;if(KJKM==null) KJKM='';
					 var KMMC=val[i].KMMC;if(KMMC==null||KMMC=="()") KMMC='';
					 var JJFL=val[i].JJFL;if(JJFL==null) JJFL='';
					 var BMBH=val[i].BMBH;if(BMBH==null) BMBH='';
					 var XMBH=val[i].XMBH;if(XMBH==null) XMBH='';
					 var BMMC=val[i].BMMC;if(BMBH==null) BMMC='';
					 if(BMMC==''||BMMC==null||BMMC=='undefdend'){
						 BMMC='';
					 }
					 var XMMC=val[i].XMMC;if(XMBH==null) XMMC='';
					 if(XMMC==''||XMMC==null||XMMC=='undefined'){
						 XMMC='';
					 }
					 var JJKMMC=val[i].JJKMMC;if(JJKMMC==null) JJKMMC='';
					 if(JJKMMC==''||JJKMMC==null||JJKMMC=='undefined'||JJKMMC=='()'){
						 JJKMMC='';
					 }
					 var JFJE=val[i].JFJE;if(JFJE==null) JFJE='';
					 var DFJE=val[i].DFJE;if(DFJE==null) DFJE='';
					 var FX=val[i].FX;if(FX==null) FX='';
					 var YE=val[i].YE;if(YE==null) YE='';
					 $("tbody").append(
								"<tr class=\"tr\">"+
								"<td id='pzrq' style='text-align:center;'>"+PZRQ+"</td>"+
								"<td name='pzlx' value='"+PZLX+"' style='text-align:left;padding:0px 1px'>"+PZLX+"</td>"+
								"<td name='pzbh' value='"+PZBHGUID+"' style='text-align:left;padding:0px 1px'>"+PZH+"</td>"+
								"<td id='zy' style='text-align:left;padding:0px 1px' title='"+ZY+"'>"+ZY.substring(0,50)+"</td>"+
								"<td style='text-align:left;padding:0px 1px'>"+BMMC+"</td>"+
								"<td style='text-align:left;padding:0px 1px'>"+WLDWMC+"</td>"+
								"<td style='text-align:left;padding:0px 1px'>"+KMMC+"</td>"+
								"<td style='text-align:left;padding:0px 1px'>"+JJKMMC+"</td>"+
								"<td id='jfje' style='text-align:right;padding:0px 1px'>"+JFJE+"</td>"+
								"<td id='dfje' style='text-align:right;padding:0px 1px'>"+DFJE+"</td>"+
								"<td id='ye' style='text-align:right;padding:0px 1px'>"+YE+"</td>"+
								"</tr>"
					);}					
			},
		}); 
   }
   var heights=$(window).outerHeight()-$("#searchBox").outerHeight()-20;
	$(".scrollable-area").height(heights); 
});
$(function() {	
	$(window).resize(function(){
		var heights=$(window).outerHeight()-$("#searchBox").outerHeight()-20;
		$(".scrollable-area").height(heights); 
	});
});
function kz(bbqj,kjkm,kmmc,StartMonth,endMonth,pz,dm){
	var zy = $("#txt_ZY").val();
	var km2 = $("#txt_km2").val();
	var km1 = $("#txt_km1").val();
	var PZBH1 = $("#txt_PZBH1").val();
	var PZBH2 = $("#txt_PZBH2").val();
	var PZRQ1 = $("#txt_PZRQ1").val();
	var PZRQ2 = $("#txt_PZRQ2").val();
	var JFJE1 = $("#txt_JFJE1").val();
	var JFJE2 = $("#txt_JFJE2").val();
	var DFJE1 = $("#txt_DFJE1").val();
	var DFJE2 = $("#txt_DFJE2").val();
	var src = "${ctx}/kjhs/jjkmmxz1/toUrl?bbqj="+bbqj+"&kjkm="+kjkm+"&pageUrl=jjkmmxz1&type=jjkmmxz1";
		src +="&zy="+zy+"&km1="+km1+"&km2="+km2+"&PZBH1="+PZBH1+"&PZBH2="+PZBH2+"&PZRQ1="+PZRQ1+"&PZRQ2="+PZRQ2+"&dm="+dm+""
		src +="&JFJE1="+JFJE1+"&JFJE2="+JFJE2+"&DFJE1="+DFJE1+"&DFJE2="+DFJE2+"&StartMonth="+StartMonth+"&endMonth="+endMonth+"&pz="+pz+"&ymtz=no";
	location.href=src;
	
}
//双击事件 id="header"
$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	var vamc = $(this).find("[name='pzbh']").attr("value");
	if(vamc ==""){
		alert("该摘要无记账凭证!");return;
	}else{
   		pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&gs=xmjfyebmx&ye=${ye}&guid="+vamc+"&year=${year}&startMonth=${startMonth}&endMonth=${endMonth}&jzpz=${jzpz}&dwbh=${param.dwbh}");
	}
});
$("#btn_export").click(function() {
	$.ajax({
		type : "post",
		data:"",
		url : "${ctx}/xmjfye/doExp2?bmbh=${bmbh}&xmbh=${xmbh}&year=${year}",
			success : function(val) {
			var data = JSON.getJson(val);
			FileDownload("${ctx}/file/fileDownload","项目经费明细账.xls",data.url);
	   }
	});
});

</script>
</body>
</html>