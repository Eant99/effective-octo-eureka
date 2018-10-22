<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金日报表</title>
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
	text-align: right;
	}
		#kmbh{
	text-align: left !important;
	}
	#kmmc{
	text-align: left !important;
	}
	#fx{
	text-align: center !important;
	}
	#pzrq{
	text-align: center !important;
	}
	
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom:30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more" style="width: 450px">
						<div class="form-group">
							<label>科目编号</label>
							<input  type="text" name="kmbh"  id="txt_kmbh" class="input_info  form-control" style="width:130px;" placeholder="科目编号" value=""  />
						</div>
						<br>
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
				<button type='button' class="btn btn-default" id="btn_exp1">导出Excel</button>
				</div>
			</div>
		</form>
	</div>

<div class="container-fluid" id="print_div">
   <div class="responsive-table">
			<div class="scrollable-area" style="overflow-x:auto;"> 						
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:100%;margin:10px auto 30px;" border="0" bordercolor="#000000">
				<div id="print_title">
					<h2 style="text-align:center;"><div style="padding-bottom:5px; margin:0 auto;display:inline-block;"><a style="color:black;text-decoration: none;">&nbsp;资&nbsp;金&nbsp;日&nbsp;报&nbsp;表</a></div></h2>
					<div style="text-align:left;font-size:12px;float:left;width:33%">科目:${param.kmbh}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：${param.lxsj}</div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
		        	</div>
		        	<thead>
				        <tr id="header">				            
				            <th rowspan="2" style="text-align:center; width: 10%;" id="tr_pzrq">科目编号</th>
				            <th rowspan="2" style="text-align:center;width:20%" id="tr_kmbh">科目名称</th>
				            <th colspan="2" style="text-align:center;" id="tr_kmmc">昨日</th>
				            <th colspan="2" style="text-align:center;" id="tr_kmbh">今日余额发生</th>	
				            <th colspan="2" style="text-align:center;" id="tr_kmmc">今日</th>
				            <th rowspan="2" style="text-align:center;width:8%;" id="tr_kmbh">借方笔数</th>	
				            <th rowspan="2" style="text-align:center;width:8%;" id="tr_dfbh">贷方笔数</th>				            				                
				        </tr>
				        <tr>
				        <th style="text-align:center;width:5%" >方向</th>	
				        <th style="text-align:center;width:11%;" >余额</th>	
				        <th style="text-align:center;width:11%" id="tr_kmbh">借方</th>	
				        <th style="text-align:center;width:11%" id="tr_dfje">贷方</th>	
				        <th style="text-align:center;width:5%" >方向</th>	
				        <th style="text-align:center;width:11%;" >余额</th>
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
<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
 $(function () {
	 jumpWindow();
	 $.ajax({
			type:"post",
			async : false, 
			url:"${ctx}/zjrbb/getPageList1?treesearch=${treesearch}&kmbh=${kmbh}",
			dataType: "json",
			success:function(val){
				for (var i=0;i<val.length;i++){
					 var KMBH=val[i].KMBH;if(KMBH==null) KMBH='';
					 var KMMC=val[i].KMMC;if(KMMC==null) KMMC='';
					 var QCFX=val[i].QCFX;if(QCFX==null) QCFX='';
					 var QMFX=val[i].QMFX;if(QMFX==null) QMFX='';
					 var ZRYE=val[i].ZRYE;if(ZRYE==null) ZRYE='';
					 var JRYE=val[i].JRYE;if(JRYE==null) JRYE='';
					 var JRFSJF=val[i].JRFSJF;if(JRFSJF==null) JRFSJF='';
					 var JRFSDF=val[i].JRFSDF;if(JRFSDF==null) JRFSDF='';
					 var JFBS=val[i].JFBS;if(JFBS==null) JFBS='';
					 var DFBS=val[i].DFBS;if(DFBS==null) DFBS='';
					 $("tbody").append(
								"<tr class=\"tr\">"+
								"<td style='text-align:left;padding:0px 1px' name='kmbh' id='kmbh' value='"+KMBH+"'>"+KMBH+"</td>"+
								"<td style='text-align:center;' id='kmmc'>"+KMMC+"</td>"+
								"<td id='fx' style='text-align:center'>"+QCFX+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+ZRYE+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+JRFSJF+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+JRFSDF+"</td>"+
								"<td id='fx' style='text-align:center'>"+QMFX+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+JRYE+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+JFBS+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+DFBS+"</td>"+
								"</tr>"
					);}					
			},
		});	
	//综合查询
	 $("#btn_gjchaxun").click(function(){
		var kmbhh = $("#txt_kmbh").val();
		$.ajax({
			type:"post",
			async : false, 
			url:"${ctx}/zjrbb/getPageList1?treesearch=${treesearch}&kmbh=${kmbh}&kmbhh="+kmbhh,
			dataType: "json",
			success:function(val){
				$(".tr").remove();		
				$("#btn_search_more").removeClass("btn-search-more");
		   		$(".search-more").css("display", "none");
				for (var i=0;i<val.length;i++){
					 var KMBH=val[i].KMBH;if(KMBH==null) KMBH='';
					 var KMMC=val[i].KMMC;if(KMMC==null) KMMC='';
					 var QCFX=val[i].QCFX;if(QCFX==null) QCFX='';
					 var QMFX=val[i].QMFX;if(QMFX==null) QMFX='';
					 var ZRYE=val[i].ZRYE;if(ZRYE==null) ZRYE='';
					 var JRYE=val[i].JRYE;if(JRYE==null) JRYE='';
					 var JRFSJF=val[i].JRFSJF;if(JRFSJF==null) JRFSJF='';
					 var JRFSDF=val[i].JRFSDF;if(JRFSDF==null) JRFSDF='';
					 var JFBS=val[i].JFBS;if(JFBS==null) JFBS='';
					 var DFBS=val[i].DFBS;if(DFBS==null) DFBS='';
					 $("tbody").append(
								"<tr class=\"tr\">"+
								"<td style='text-align:left;padding:0px 1px' name='kmbh' id='kmbh' value='"+KMBH+"'>"+KMBH+"</td>"+
								"<td style='text-align:center;' id='kmmc'>"+KMMC+"</td>"+
								"<td id='fx' style='text-align:center'>"+QCFX+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+ZRYE+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+JRFSJF+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+JRFSDF+"</td>"+
								"<td id='fx' style='text-align:center'>"+QMFX+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+JRYE+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+JFBS+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+DFBS+"</td>"+
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
 function toUrl(kmbh,lxsj){
		location.href="${ctx}/webView/kjhs/bbzx/zjrbb.jsp?kmbh="+kmbh+"&lxsj="+lxsj;
	}

function jumpWindow(){
	var jump = "${jump}";
	var clicks = "${clicks}";
	if(jump=="yes"&&clicks!="yes"){
		$("#btn_sea").click();
	}
}
$("[id$=btn_sea]").click(function(){
 	select_commonWin("${ctx}/zjrbb/jumpWindow?kmmc=(${kmbh})${kmmc}&kmbh=${kmbh}","报表条件-资金日报表","610","440");
  });
	
	//点击打印
	$(document).on("click","#dyyl",function(){
// 	 $("#dyyl").click(function(){
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
	
		//打印
// 	  $("[id$=print]").click(function(){
// 	 	select_commonWin("${ctx}/webView/kjhs/pzxx/print.jsp","双击页面打印","920","630");
// 	  });
	
		
//双击事件 id="header"
$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	var vamc = $(this).find("[name='kmbh']").attr("value");
	var bbqj = "${param.date}"
	doOperate("${ctx}/zjrbb/zjc?kmbh="+vamc+"&bbqj="+bbqj);

});

		
//双击事件 id="header"
// $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
// 	var vamc = $(this).find("[name='kmbh']").attr("value");
// 	var bbqj1 = "${param.lxsj}";
// 	var bbqj = bbqj1.substring(0,4)+"年"+bbqj1.substring(5,7)+"月至"+bbqj1.substring(5,7)+" 月";

// 	doOperate("${ctx}/mxz/toUrl?kmbh="+vamc+"&bbqj="+bbqj);
// });	

	//导出Excel
	$(document).on("click","#btn_exp1",function(){
		var kjkmbh = '${param.kmbh}';
   		var checkbox = $("#mydatatables").find("#tr_pzrq").filter(":checked");
//    		if(checkbox.length > 0){
// 			checkbox.each(function(){
// 				kmbh.push($(this).val());
// 			});
// 			kmbh = kmbh.join("','");
//    		}else{
//    			kmbh = "";
//    		}
// 	    var bbyf = $("[name='bbyf']").val();
	$.ajax({
			type : "post",
			data:"kjkmbh="+kjkmbh,
			url : "${ctx}/zjrbb/expExcel2",
			success : function(val) {
// 				alert(kmbh);
			 FileDownload("${ctx}/file/fileDownload","资金日报表.xls",val.url);
		   }
	});
});	
</script>
</body>
</html>