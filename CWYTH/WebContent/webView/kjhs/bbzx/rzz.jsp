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
	tr td{
		line-height:30px !important;
		text-align: right;
		}
	tr th{
		text-align:center;
	}
	#kmbh{
	text-align: left;
	}
	#kmmc{
	text-align: left;
	}
	#fx{
	text-align:center;
	}
</style>
</head>
<body>
<div class="fullscreen" >
	<div class="search" id="searchBox" style="padding-top: 0px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="btn-group pull-right" role="group">
				<button type='button' class="btn btn-default" id="btn_sea">查询</button>
				<button type='button' class="btn btn-default" id="dyyl">打印</button>
				<button type="button" class="btn btn-default" id="btn_export">导出Excel</button>	
				</div>
			</div>
		</form>
	</div>
<div class="container-fluid" id="print_div"> 
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<div id="print_title">
					<h2 style="text-align:center;"><div style="padding-bottom:5px; margin:0 auto;display:inline-block;"><a style="color:black;text-decoration: none;">总&nbsp;账</a></div></h2>
					<div style="text-align:center;margin-bottom:0px;font-size:14px">报表期间：${empty param.kjnd?pznd:param.kjnd}年${qskjqjyf}月&nbsp;至&nbsp;${empty param.kjnd?pznd:param.kjnd}年${jskjqjyf}月&nbsp;&nbsp;&nbsp;&nbsp;科目:${empty param.kjkm?kmmc:param.kjkm}</div>
				</div>
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:70%;margin:0 auto 30px" border="1" bordercolor="#000000">
		        	<thead>
		 				<tr  id="header">
						    <th rowspan="2">凭证日期</th>
						    <th rowspan="2">摘要</th>
						    <th colspan="2">本期发生</th>
						    <th colspan="2">期末余额</th>
			   			</tr>
			   			<tr>
						    <th>借方</th>
						    <th>贷方</th>
						    <th>方向</th>
						    <th>余额</th>
			   			</tr>
					</thead>
				    <tbody>
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
 $(function () {
	   var src = "${ctx}/Zz/getPageList?qskjqj=${qskjqj}&jskjqj=${jskjqj}&kmbh=${kjkm}&sfarhzcx=${sfarhzcx}";
	   var kmbh = "${kjkm}";
	   if(kmbh!=""&&kmbh!=null&&kmbh!="undefined"){
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
						 var JJFL=val[i].JJFL;if(JJFL==null) JJFL='';
						 var BMBH=val[i].BMBH;if(BMBH==null) BMBH='';
						 var XMBH=val[i].XMBH;if(XMBH==null) XMBH='';
						 var JFJE=val[i].JFJE;if(JFJE==null) JFJE='';
						 var DFJE=val[i].DFJE;if(DFJE==null) DFJE='';
						 var FX=val[i].FX;if(FX==null) FX='';
						 var YE=val[i].YE;if(YE==null) YE='';
						 $("tbody").append(
									"<tr class=\"tr\">"+
									"<td id='pzrq' style='text-align:center;'>"+PZRQ+"</td>"+
									"<td id='zy' style='text-align:left;padding:0px 1px'  title='"+ZY+"'>"+ZY.substring(0,50)+"</td>"+
									"<td style='text-align:right;padding:0px 1px'; id='jfje'>"+JFJE+"</td>"+
									"<td style='text-align:right;padding:0px 1px'; id='dfje'>"+DFJE+"</td>"+
									"<td id='fx' style='text-align:center;'>"+FX+"</td>"+
									"<td style='text-align:right;padding:0px 1px'; id='ye'>"+YE+"</td>"+
									"</tr>"
						);}					
				},
			}); 
	   }
	   var heights=$(window).outerHeight()-$("#searchBox").outerHeight()-20;
		$(".scrollable-area").height(heights); 
		
		
		
    $("[id$=btn_sea]").click(function(){
    	var kjkm = "${kjkm}";
    	var kmmc = "${kmmc}";
    	if(kjkm == ""||kjkm == null||kjkm == undefined){
    		alert("请先选择会计科目!");
    		return;
    	}
    	var startkjqj =  "${qskjqj}";
    	var endkjqj = "${jskjqj}";
		select_commonWin("${ctx}/Zz/jumpWindow?qskjqj="+startkjqj+"&jskjqj="+endkjqj+"&kjkm="+kjkm+"&kmmc="+kmmc,"报表条件-总账","540","420");
   });
});
 function kz(kjkm,qskjqj,jskjqj,kmmc,bhwjzpz,kjnd,sfarhzcx){
		var src = "${ctx}/Zz/goRzzPage?qskjqj="+qskjqj+"&jskjqj="+jskjqj+"&kmbh="+kjkm+"&kmmc="+kmmc+"&sfarhzcx="+sfarhzcx;
		location.href=src;
		
	}
function jumpWindow(){
	var clicks = "${clicks}";
	var jump  = "${jump}";
	if(jump=="yes"&&clicks!="yes"){
		 select_commonWin("${ctx}/Zz/jumpWindow","报表条件-总账","540","420");
	}
}
/**打印*/
$(document).on("click","#dyyl",function(){
// $("#dyyl").click(function(){
	PreviewMytable();
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


//导出Excel
$(document).on("click","#btn_export",function(){
// $("#btn_export").click(function() {
$.ajax({
		type : "post",
		url : "${ctx}/Zz/exportRzzExcel?qskjqj=${qskjqj}&jskjqj=${jskjqj}&kmbh=${kjkm}&sfarhzcx=${sfarhzcx}",
		success : function(val) {
		 	FileDownload("${ctx}/file/fileDownload","日总账.xls",val.url);
	   }
});
});
//双击跳转到明细账
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