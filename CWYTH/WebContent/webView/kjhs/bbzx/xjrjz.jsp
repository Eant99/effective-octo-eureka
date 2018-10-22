<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>日记账</title>
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
/*     	bottom: 20px; */
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
	text-align: right;
	}
	#pzbh{
	text-align: left !important;
	}
	#zy{
	text-align: left !important;
	}
	#fx{
	text-align: center !important;
	}
	#pzrq{
	text-align: center !important;
	}
	#mydatatables_length{
	display: none;
	}
</style>
</head>
<body>
<div class="fullscreen" scrolling="auto">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more" style="width: 450px">
						<div class="form-group">
							<label>摘&emsp;&emsp;&emsp;&emsp;要</label>
							<input  type="text" name="zy"  id="txt_zy" class="input_info  form-control" style="width:130px;" placeholder="摘要" value=""  />
						</div>
						<br>
						<div class="form-group">
							<label>凭&nbsp;证&emsp;编&nbsp;号&nbsp;</label>
							<input  type="text" name="pzbh" id="txt_pzbh" class="input_info  form-control" style="width:130px;" placeholder="凭证编号" value=""  />
						</div>
						<div class="form-group">
							<label>凭证日期区间</label>
							<input   type="text" name="PZRQ" types="TL_date" id="txt_PZRQ1" class="input_info  form-control"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:130px;" placeholder="凭证日期" value=""  />
							<label>至</label>
							<input   type="text" name="PZRQ"  types="TH_date" id="txt_PZRQ2" class="input_info  form-control"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="凭证日期" style="width:130px;" value=""  />
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_gjchaxun" >
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
				<button type='button' class="btn btn-default" id="btn_sea">查询</button>
				<button type='button' class="btn btn-default" id="dyyl">打印预览</button>
				   <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>	
				</div>
			</div>
		</form>
	</div>
	
    <div class="container-fluid" id="print_div" >
		<div class="responsive-table">
			<div class="scrollable-area" style="overflow-x:auto;"> 
				<table id="mydatatables" class="table table-striped table-bordered" style=" border-collapse:collapse;width:100%;margin:10px auto;" border="0" bordercolor="#000000">
					<div id="print_title">
						<h2 style="text-align:center;">
							<div style="padding-bottom:5px; margin:0 auto;display:inline-block;">
								<a style="color:black;text-decoration: none;">${kmmc}&nbsp;日&nbsp;记&nbsp;账</a>
							</div>
						</h2>
						<div style="text-align:left;font-size:12px;float:left;width:33%">科目:${kmbh}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：${bbqj}</div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
			        </div>
			        <thead>
					        <tr id="header" style="overflow-x:scroll">
					            <th style="text-align:center; width:8%" id="tr_pzrq">凭证日期</th>
				            <th  style="text-align:center;width:8%" id="tr_pzbh">凭证类型</th>
				            <th  style="text-align:center;width:8%" id="tr_pzbh">凭证编号</th>
				            <th  style="text-align:center;width:35%" id="tr_zy">摘要</th>		            
					            <th  style="text-align:center;" id="tr_jfje">借方金额</th>
					            <th  style="text-align:center;" id="tr_dfje">贷方金额</th>	
					            <th  style="text-align:center;" id="tr_fx">方向</th>
					            <th  style="text-align:center;" id="tr_ye">余额</th>				                
					        </tr>				       				       				        
					</thead>
					<tbody>
					</tbody>
					<tfoot>
				   <tr class="hide" style="text-align:right;">
				   <td width="100%" colspan="8">
				   <font tdata="PageNO" format="00" >第##页，</font><font tdata="PageCount" format="00">共##页</font>
				   </td>
				   </tr>
				   </tfoot>
				</table>						
			</div>
		</div>
    </div>
</div>
<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function () {
	 jumpWindow();
	/*  var columns = [
	        	   	{"data": "PZRQ",defaultContent:"",class:"text-center"},
	        	   	{"data": "PZH",defaultContent:""},
	        	   	{"data": "ZY",defaultContent:""},
	        	   	{"data": "JFJE",defaultContent:"",class:"text-right"},
	        	   	{"data": "DFJE",defaultContent:"",class:"text-right"},
	        	   	{"data": "FX",defaultContent:""},
	        	   	{"data": "YE",defaultContent:"",class:"text-right"}
	        	];
	  table = getDataTableByListHj("mydatatables","${ctx}/xjrjz/getPageList?kmbh=${kmbh}&zjrjzType=${zjrjzType}",[],columns,0,1,""); */
	 $.ajax({
			type:"post",
			async : false, 
			url:"${ctx}/xjrjz/getPageList?treesearch=${treesearch}&kmbh=${kmbh}&zjrjzType=${zjrjzType}&year=${year}",
			dataType: "json",
			success:function(val){
				for (var i=0;i<val.length;i++){
					 var PZRQ=val[i].PZRQ;if(PZRQ==null) PZRQ='';
					 var PZLX=val[i].PZLXMC;if(PZLX==null) PZLX='';
					 var PZBHGUID=val[i].PZBHGUID;if(PZBHGUID==null) PZBHGUID='';
					 var PZH=val[i].PZH;if(PZH==null) PZH='';
					 var ZY=val[i].ZY;if(ZY==null) ZY='';
					 var JFJE=val[i].JFJE;if(JFJE==null) JFJE='';
					 var DFJE=val[i].DFJE;if(DFJE==null) DFJE='';
					 var FX=val[i].FX;if(FX==null) FX='';
					 var YE=val[i].YE;if(YE==null) YE='';
					 $("tbody").append(
								"<tr class=\"tr\">"+
								"<td id='pzrq'>"+PZRQ+"</td>"+
								"<td name='pzlx' style='text-align:left;padding:0px 1px' value='"+PZLX+"'>"+PZLX+"</td>"+
								"<td name='pzbh' id='pzbh' value='"+PZBHGUID+"' style='text-align:left;padding:0px 1px'>"+PZH+"</td>"+
								"<td id='zy' title='"+ZY+"' style='text-align:left;padding:0px 1px'>"+ZY.substring(0,50)+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+JFJE+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+DFJE+"</td>"+
								"<td id='fx' style='text-align:center'>"+FX+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+YE+"</td>"+
								"</tr>"
					);}					
			},
		});
		 //综合查询
		 $("#btn_gjchaxun").click(function(){
			var zy = $("#txt_zy").val();
			var pzbh = $("#txt_pzbh").val();
			var pzrq1 = $("#txt_PZRQ1").val();
			var pzrq2 = $("#txt_PZRQ2").val();
			 $.ajax({
					type:"post",
					async : false, 
					url:"${ctx}/xjrjz/getPageList?treesearch=${treesearch}&kmbh=${kmbh}&zjrjzType=${zjrjzType}&year=${year}&zy="+zy+"&pzbh="+pzbh+"&pzrq1="+pzrq1+"&pzrq2="+pzrq2,
					dataType: "json",
					success:function(val){
						$(".tr").remove();		
						$("#btn_search_more").removeClass("btn-search-more");
				   		$(".search-more").css("display", "none");
						for (var i=0;i<val.length;i++){
							 var PZRQ=val[i].PZRQ;if(PZRQ==null) PZRQ='';
							 var PZLX=val[i].PZLXMC;if(PZLX==null) PZLX='';
							 var PZBHGUID=val[i].PZBHGUID;if(PZBHGUID==null) PZBHGUID='';
							 var PZH=val[i].PZH;if(PZH==null) PZH='';
							 var ZY=val[i].ZY;if(ZY==null) ZY='';
							 var JFJE=val[i].JFJE;if(JFJE==null) JFJE='';
							 var DFJE=val[i].DFJE;if(DFJE==null) DFJE='';
							 var FX=val[i].FX;if(FX==null) FX='';
							 var YE=val[i].YE;if(YE==null) YE='';
							 $("tbody").append(
										"<tr class=\"tr\">"+
										"<td id='pzrq'>"+PZRQ+"</td>"+
										"<td name='pzlx' style='text-align:left;padding:0px 1px' value='"+PZLX+"'>"+PZLX+"</td>"+
										"<td name='pzbh' id='pzbh' value='"+PZBHGUID+"' style='text-align:left;padding:0px 1px'>"+PZH+"</td>"+
										"<td id='zy' title='"+ZY+"' style='text-align:left;padding:0px 1px'>"+ZY.substring(0,50)+"</td>"+
										"<td style='text-align:right;padding:0px 1px'>"+JFJE+"</td>"+
										"<td style='text-align:right;padding:0px 1px'>"+DFJE+"</td>"+
										"<td id='fx' style='text-align:center'>"+FX+"</td>"+
										"<td style='text-align:right;padding:0px 1px'>"+YE+"</td>"+
										"</tr>"
							);}					
					},
				});
			
		});
	 var heights=$(window).outerHeight()-$("#searchBox").outerHeight()-40;
	 $(".scrollable-area").height(heights);  
});
$(function() {	
	$(window).resize(function(){
		var heights=$(window).outerHeight()-$("#searchBox").outerHeight()-40;
		$(".scrollable-area").height(heights); 
	});
});
function toUrl(kmbh,type,bbqj){
	//location.href="${ctx}/webView/kjhs/bbzx/xjrjz.jsp?kmbh="+kmbh+"&zjrjzType="+type;
	location.href="${ctx}/xjrjz/toUrl?kmbh="+kmbh+"&zjrjzType="+type+"&pageUrl=xjrjz&bbqj="+bbqj;
	
}

function jumpWindow(){
	var jump = "${jump}";
	var clicks = "${clicks}";
	if(jump=="yes"&&clicks!="yes"){
		$("#btn_sea").click();
	}
}
$("[id$=btn_sea]").click(function(){
 	select_commonWin("${ctx}/xjrjz/jumpWindow?kmmc=(${kmbh})${kmmc}","报表条件-日记账","520","400");
  });
//点击打印
$(document).on("click","#dyyl",function(){
 	 PreviewMytable();
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
 
//导出Excel
$(document).on("click","#btn_export",function(){
//  $("#btn_export").click(function() {
		var kmbh = $("[name='kmbh']").val();
		console.log("kmbh"+kmbh);
//  	var kmbh = [];
 		var checkbox = $("#mydatatables").find("[name='kmbh']").filter(":checked");
 		if(checkbox.length > 0){
 		checkbox.each(function(){
 			kmbh.push($(this).val());
 		});
 		kmbh = kmbh.join("','");
 		}else{
 			kmbh = "";
 		}
// 	    var bbyf = $("[name='bbyf']").val();
 $.ajax({
 		type : "post",
 		data : {kmbh:kmbh},
 		url : "${ctx}/xjrjz/expExcel2?kmbh=kmbh",
 		success : function(val) {
// 				alert(kmbh);
 		 FileDownload("${ctx}/file/fileDownload","${kmmc}日记账.xls",val.url);
 	   }
 });
 });
	//双击事件 id="header"
	$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
		var vamc = $(this).find("[name='pzbh']").attr("value");
		var target = "${ctx}/kjhs/pzxx/pzlr";
		if(vamc ==""){
			alert("该摘要无记账凭证!");
			return;
		}else{
			pageSkip(target,"pzlrck&gs=rjz&guid="+vamc);
		}
	});

 
	
</script>
</body>
</html>