 <%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>往来单位明细账页面</title>
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
		<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more" style="width: 450px">
						<div class="form-group">
							<label>摘要内容</label>
							<br>
							<input  type="text" name="ZY" id="txt_ZY" class="input_info  form-control" style="width:280px;" placeholder="摘要内容" value=""  />
						</div>
						<div class="form-group hide" >
							<label>会计科目</label>
							<br>
							<input  type="text" name="kmbh" types="ToBhss" id="txt_km" class="input_info  form-control" style="width:130px;" placeholder="科目编号" value=""  />
							<button type="button" id="btn_kjkm" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group hide">
							<label>起止会计科目</label>
							<br>
							<input   type="text" name="kmbh" types="BHL" id="txt_km1" class="input_info  form-control" style="width:130px;" placeholder="科目编号" value=""  />
							<button type="button" id="btn_kjkm1" class="btn btn-link ">选择</button>
							<label>至</label>
							<input   type="text" name="kmbh"  types="BHH" id="txt_km2" class="input_info  form-control" placeholder="科目编号" style="width:130px;" value=""  />
							<button type="button" id="btn_kjkm2" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group">
							<label>凭证号区间</label>
							<br>
							<input   type="text" name="PZBH" types="NL" id="txt_PZBH1" class="input_info  form-control" style="width:130px;" placeholder="凭证号" value=""  />
							<label>至</label>
							<input   type="text" name="PZBH"  types="NH" id="txt_PZBH2" class="input_info  form-control" placeholder="凭证号" style="width:130px;" value=""  />
						</div>
						<div class="form-group">
							<label>凭证日期区间</label>
							<br>
							<input   type="text" name="PZRQ" types="TL_date" id="txt_PZRQ1" class="input_info  form-control"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:130px;" placeholder="凭证日期" value=""  />
							<label>至</label>
							<input   type="text" name="PZRQ"  types="TH_date" id="txt_PZRQ2" class="input_info  form-control"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="凭证日期" style="width:130px;" value=""  />
						</div>
						<div class="form-group">
							<label>借方金额区间</label>
							<br>
							<input   type="text" name="JFJE" types="NL" id="txt_JFJE1" class="input_info  form-control" style="width:130px;" placeholder="借方金额" value=""  />
							<label>至</label>
							<input   type="text" name="JFJE"  types="NH" id="txt_JFJE2" class="input_info  form-control" placeholder="借方金额" style="width:130px;" value=""  />
						</div>
						<div class="form-group">
							<label>贷方金额区间</label>
							<br>
							<input   type="text" name="DFJE" types="NL" id="txt_DFJE1" class="input_info  form-control" style="width:130px;" placeholder="贷方金额" value=""  />
							<label>至</label>
							<input   type="text" name="DFJE"  types="NH" id="txt_DFJE2" class="input_info  form-control" placeholder="贷方金额" style="width:130px;" value=""  />
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_sear" onclick="zhcx()" name="btncx">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
							</div>
						</div>
					</div>
				</div>
			<div class="btn-group pull-right" role="group">
			<button type='button' class="btn btn-default" id="btn_fh" style="display:none">返回</button>
			<button type='button' class="btn btn-default" id="btn_sea">查询</button>
			<button type='button' class="btn btn-default" id="dyyl">打印预览</button>
			 <button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>	
			</div>
		</div>
	</form>
</div>
<div class="container-fluid" id="print_div" style="padding-bottom: 10px;">
		<div class='responsive-table'>
			<div class='scrollable-area' style="overflow-x:auto;"> 
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:100%;margin:0 auto" border="0">
					<div id="print_title">
						<h2 style="text-align:center;">
						<div style="padding-bottom:5px;margin:0 auto;display:inline-block;">
						<a style="color:black;text-decoration: none;">${kmmc}&nbsp;单位往来明细账</a>
						</div>
						</h2>
						<div id="kjkm" style="text-align:left;font-size:12px;float:left;width:41%">往来单位名称:${wldwmc }</div>
					<div id="kjkm" style="text-align:left;font-size:12px;float:left;width:25%">科目名称:<c:if test="${fn:length(kmbh)>0}">(${kmbh})</c:if>${kmmc }</div>
					<div style="text-align:center;font-size:12px;float:left;width:21%">会计期间：${bbqj}</div>
					<div style="text-align:right;font-size:12px;float:left;width:13%">单位：元</div>
					</div>
		        	<thead>
				        <tr id="header">
				            <th style="text-align:center;width:7%" id="tr_pzrq">凭证日期</th>
				          
				            <th style="text-align:center;width:5%" id="tr_kmbh">凭证号</th>
				            <th style="text-align:center;width:15%" id="tr_zy">摘要</th>
				           
				          	            
				            <th style="text-align:center;"  id="tr_kmmc">借方金额</th>
				            <th style="text-align:center;" id="tr_kmbh">贷方金额</th>	
				           
				            <th style="text-align:center;" id="tr_dfje">余额</th>
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
	 //综合查询中的会计科目
	 $(document).on("click","[id=btn_kjkm]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId=km&controlId1=txt_km&flag=zhcx","科目信息","920","630");
	 });
	 //综合查询中的起止会计科目
	 $(document).on("click","[id=btn_kjkm1]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km1&controlId=txt_km1&flag=zhcx","科目信息","920","630");
	 });
	//综合查询中的起止会计科目
	 $(document).on("click","[id=btn_kjkm2]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km2&controlId=txt_km2&flag=zhcx","科目信息","920","630");
	 });
	 //控制显示返回按钮
	 var type = "${type}";
	 if(type=="mxz"){
		 $("#btn_fh").hide();
	 }
	 //跳转时的页面控制
	 var dwye = "${dwye}";
		if(dwye == 'dwye'){
			$("#btn_fh").show();
			$("#btn_sea").hide();
			$("#dyyl").hide();
			$("#btn_exp").hide();
			$("#btn_search_more").hide();
		}
	 //返回到首页
	 $(document).on("click","#btn_fh",function(){
		window.history.go(-1); 
	 });
	//控制页面
	helloWord();
	var dm = "${dm}";
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
	var src = "${ctx}/wldwmxz1/getPageList?StartMonth=${StartMonth}&endMonth=${endMonth}&bbqj=${bbqj}&kmbh=${kmbh}&pageUrl=wldwmxz1&type=wldwmxz1"
		src +="&zy="+zy+"&km1="+km1+"&km2="+km2+"&PZBH1="+PZBH1+"&PZBH2="+PZBH2+"&PZRQ1="+PZRQ1+"&PZRQ2="+PZRQ2+"&dm="+dm+""
		src +="&JFJE1="+JFJE1+"&JFJE2="+JFJE2+"&DFJE1="+DFJE1+"&DFJE2="+DFJE2+"&${treesearch}&kmbh=${kmbh}&year=${year}&dwye=${dwye}";
   var kmbh = "${kmbh}";
   //if(kmbh!=""&&kmbh!=null&&kmbh!="undefined"){
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
					 var KMBH=val[i].KMBH;if(KMBH==null) KMBH='';
					 var KMMC=val[i].KMMC;if(KMMC==null||KMMC=="()") KMMC='';
					 var JJFL=val[i].JJFL;if(JJFL==null) JJFL='';
					 var BMBH=val[i].BMBH;if(BMBH==null) BMBH='';
					 var XMBH=val[i].XMBH;if(XMBH==null) XMBH='';
					 var JFJE=val[i].JFJE;if(JFJE==null) JFJE='';
					 var DFJE=val[i].DFJE;if(DFJE==null) DFJE='';
					 var FX=val[i].FX;if(FX==null) FX='';
					 var YE=val[i].YE;if(YE==null) YE='';
					 $("tbody").append(
								"<tr class=\"tr\">"+
								"<td id='pzrq' style='text-align:center;padding:0px 1px'>"+PZRQ+"</td>"+
							
								"<td name='pzbh' value='"+PZBHGUID+"' style='text-align:left;padding:0px 1px'>"+PZH+"</td>"+
								"<td id='zy' style='text-align:left;' style='text-align:left;padding:0px 1px' title='"+ZY+"'>"+ZY.substring(0,50)+"</td>"+
								
							
								"<td id='jfje' style='text-align:right;padding:0px 1px'>"+JFJE+"</td>"+
								"<td id='dfje' style='text-align:right;padding:0px 1px'>"+DFJE+"</td>"+
								
								"<td id='ye' style='text-align:right;padding:0px 1px'>"+YE+"</td>"+
								"</tr>"
					);}					
			},
		}); 
   //}
   var heights=$(window).outerHeight()-$("#searchBox").outerHeight()-20;
	$(".scrollable-area").height(heights); 
});
$(function() {	
	$(window).resize(function(){
		var heights=$(window).outerHeight()-$("#searchBox").outerHeight()-20;
		$(".scrollable-area").height(heights); 
	});
});
 
function kz(bbqj,kmbh,kmmc,StartMonth,endMonth,pz){
	var dm = "${dm}";
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
	var src = "${ctx}/wldwmxz1/toUrl?bbqj="+bbqj+"&kmbh="+kmbh+"&pageUrl=wldwmxz1&type=wldwmxz1";
		src +="&zy="+zy+"&km1="+km1+"&km2="+km2+"&PZBH1="+PZBH1+"&PZBH2="+PZBH2+"&PZRQ1="+PZRQ1+"&PZRQ2="+PZRQ2+"&dm="+dm+""
		src +="&JFJE1="+JFJE1+"&JFJE2="+JFJE2+"&DFJE1="+DFJE1+"&DFJE2="+DFJE2+"&StartMonth="+StartMonth+"&endMonth="+endMonth+"&pz="+pz+"&ymtz=no";
	location.href=src;
	
}
//综合查询中的查询按钮
function zhcx(){
	var km = $("#txt_km").val();
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
	var src = "${ctx}/wldwmxz1/getPageList?bbqj=${bbqj}&pageUrl=wldwmxz1&type=wldwmxz1"
		src +="&zy="+zy+"&kmbh="+km+"+&km1="+km1+"&km2="+km2+"&PZBH1="+PZBH1+"&PZBH2="+PZBH2+"&PZRQ1="+PZRQ1+"&PZRQ2="+PZRQ2+""
		src +="&JFJE1="+JFJE1+"&JFJE2="+JFJE2+"&DFJE1="+DFJE1+"&DFJE2="+DFJE2+"&${treesearch}&year=${year}"
	$.ajax({
		type:"post",
		async : false, 
		url:src,
		dataType: "json",
		success:function(val){
			$(".tr").remove();		
			$("#btn_search_more").removeClass("btn-search-more");
	   		$(".search-more").css("display", "none");
			for (var i=0;i<val.length;i++){
				 var PZRQ=val[i].PZRQ;if(PZRQ==null) PZRQ='';
				 var PZH=val[i].PZH;if(PZH==null) PZH='';
				 var GUID=val[i].GUID;if(GUID==null) GUID='';
				 var PZBHGUID=val[i].PZBHGUID;if(PZBHGUID==null) PZBHGUID='';
				 var PZLX=val[i].PZLXMC;if(PZLX==null) PZLX='';
				 var ZY=val[i].ZY;if(ZY==null) ZY='';
				 var WLDWMC=val[i].WLDWMC;if(WLDWMC==null) WLDWMC='';
				 var KMBH=val[i].KMBH;if(KMBH==null) KMBH='';
				 var KMMC=val[i].KMMC;if(KMMC==null||KMMC=="()") KMMC='';
				 var JJFL=val[i].JJFL;if(JJFL==null) JJFL='';
				 var BMBH=val[i].BMBH;if(BMBH==null) BMBH='';
				 var XMBH=val[i].XMBH;if(XMBH==null) XMBH='';
				 var JFJE=val[i].JFJE;if(JFJE==null) JFJE='';
				 var DFJE=val[i].DFJE;if(DFJE==null) DFJE='';
				 var FX=val[i].FX;if(FX==null) FX='';
				 var YE=val[i].YE;if(YE==null) YE='';
				 $("tbody").append(
							"<tr class=\"tr\">"+
							"<td id='pzrq' style='text-align:center;padding:0px 1px'>"+PZRQ+"</td>"+
							
							"<td name='pzbh' value='"+PZBHGUID+"' style='text-align:left;padding:0px 1px'>"+PZH+"</td>"+
							"<td id='zy' style='text-align:left;' title='"+ZY+"'>"+ZY.substring(0,50)+"</td>"+
							
							
							"<td id='jfje' style='text-align:right;padding:0px 1px'>"+JFJE+"</td>"+
							"<td id='dfje' style='text-align:right;padding:0px 1px'>"+DFJE+"</td>"+
						
							"<td id='ye' style='text-align:right;padding:0px 1px'>"+YE+"</td>"+
							"</tr>"
				);}					
		},
	}); 
}
function helloWord(){
	var ymtz = "${ymtz}";
	var dm = "${dm}";
	if(dm == ""){
		
	}else{
		if(ymtz == 'no'){
			
		}else{
			sea(dm);
		}
	}
}
$("[id$=btn_sea]").click(function(){
	var dm = "${dm}";
	if(dm == null||dm == ""||dm == undefined){
		alert("请先选择单位!");
		return;
	}else{
  		select_commonWin("${ctx}/wldwmxz1/jumpWindow?bbqj=${bbqj}&kmbh=${kmbh}&StartMonth=${StartMonth}&endMonth=${endMonth}&pz=${pz}&dm=${dm}","报表条件-明细账","550","360");
	}
});      
//点击打印
$(document).on("click","#dyyl",function(){
	 PreviewMytable();
}); 
//打印功能
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
//导出Excel
$(document).on("click","#btn_exp",function(){
	var km = $("#txt_km").val();
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
	var src = "${ctx}/wldwmxz1/expExcel2?bbqj=${bbqj}&km=km&kmbh=${kmbh}&pageUrl=wldwmxz1&type=wldwmxz1"
		src +="&zy="+zy+"&km1="+km1+"&km2="+km2+"&PZBH1="+PZBH1+"&PZBH2="+PZBH2+"&PZRQ1="+PZRQ1+"&PZRQ2="+PZRQ2+""
		src +="&JFJE1="+JFJE1+"&JFJE2="+JFJE2+"&DFJE1="+DFJE1+"&DFJE2="+DFJE2+"&${treesearch}&kmbh=${kmbh}&year=${year}";
	var kmbh = "${kmbh}";
	/* if(kmbh==""||kmbh==null||kmbh=="undefined"){
		alert("请选择会计科目！");
		return;
	} */
	$.ajax({
			type : "post",
			data : src,
			url : "${ctx}/wldwmxz1/expExcel2?kmbh=kmbh",
			success : function(val) {
			 FileDownload("${ctx}/file/fileDownload","${kmmc}单位往来明细账.xls",val.url);
		   }
			
	});
});	
//双击事件 id="header"
$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	//双击事件 id="header"
	$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
		var vamc = $(this).find("[name='pzbh']").attr("value");
		if(vamc ==""){
			alert("该摘要无记账凭证!");
			return;
		}else{
			pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&gs=dwwlmx&ye=${ye}&guid="+vamc);
		}
	});
});
//弹窗查询也页面
function sea(dm){
	select_commonWin("${ctx}/wldwmxz1/jumpWindow?bbqj=${bbqj}&kmbh=${kmbh}&StartMonth=${StartMonth}&endMonth=${endMonth}&pz=${pz}&dm="+dm,"报表条件-明细账","550","360");
}
</script>
</body>
</html>