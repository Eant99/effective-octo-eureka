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
	.input_info{
		width:100px;
	}
	button{

	}
	.sorting{
		position:static  !important;
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
	text-align: left ;
	}
	
	#pzrq{
	text-align: center !important;
	}
	#jfje{
	text-align: right;
	}
	#dfje{
	text-align: right;
	}
	#ye{
	text-align: right;
	}
	#fx{
	text-align: center;
	}
	
</style>
</head>
<body>
<div class="fullscreen">
<input type="hidden" name="ly" value="${ly }">
<input type="hidden" name="kmbh" id="tr_kmbh" value="${kmbh}"/>
<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
	<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		<div class="search-simple">
			<div class="btn-group pull-right" role="group">			
			<button type='button' class="btn btn-default" id="btn_sea">查询</button>
			<button type='button' class="btn btn-default" id="dyyl">打印预览</button>
			 <button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>	
			</div>
		</div>
	</form>
</div>
<div class="container-fluid" id="div_print" >
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:100%;margin:0 auto">
					<h4 style="text-align:center;"><div style="padding-bottom:5px;margin:0 auto;display:inline-block;"><a style="color:black;text-decoration: none;">${kmmc}明细账</a></div></h4>
					<div style="text-align:center;margin-bottom:20px;">会计期间：${bbqj}</div>
		        	<thead>
				        <tr id="header">
				            <th style="text-align:center;" id="tr_pzrq">凭证日期</th>
				            <th style="text-align:center;" id="tr_kmbh">凭证类型</th>
				            <th style="text-align:center;" id="tr_kmbh">凭证号</th>
				            <th style="text-align:center;" id="tr_zy">摘要</th>	
				            <th style="text-align:center;" id="tr_jjfl">会计科目</th>
				            <th style="text-align:center;" id="tr_bmbh">部门名称</th>
				            <th style="text-align:center;" id="tr_xmbh">项目名称</th>			            
				            <th style="text-align:center;"  id="tr_kmmc">借方金额</th>
				            <th style="text-align:center;" id="tr_kmbh">贷方金额</th>				            
				            <th style="text-align:center;" id="tr_dfje">余额</th>
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
 $(function () {
	 //控制显示返回按钮
	 var type = "${type}";
	 if(type=="mxz"){
		 $("#btn_fh").hide();
	 }
	 //返回
	 $("#btn_fh").click(function(){
		window.history.go(-1); 
	 });
	 helloWord();
	
	   $.ajax({
			type:"post",
			async : false, 
			url:"${ctx}/mxz/getJjkmPageList?treesearch=${treesearch}&kmbh=${kmbh}&year=${year}",
			dataType: "json",
			success:function(val){
				for (var i=0;i<val.length;i++){
					 var PZRQ=val[i].PZRQ;if(PZRQ==null) PZRQ='';
					 var PZH=val[i].PZH;if(PZH==null) PZH='';
					 var GUID=val[i].GUID;if(GUID==null) GUID='';
					 var PZBHGUID=val[i].PZBHGUID;if(PZBHGUID==null) PZBHGUID='';
					 var PZLX=val[i].PZLXMC;if(PZLX==null) PZLX='';
					 var ZY=val[i].ZY;if(ZY==null) ZY='';
					 var KMMC=val[i].KMMC;if(KMMC==null) KMMC='';
					 var BMMC=val[i].BMMC;if(BMMC==null) BMMC='';
					 var XMMC=val[i].XMMC;if(XMMC==null) XMMC='';
					 var JFJE=val[i].JFJE;if(JFJE==null) JFJE='';
					 var DFJE=val[i].DFJE;if(DFJE==null) DFJE='';
					
					 var YE=val[i].YE;if(YE==null) YE='';
					 $("tbody").append(
								"<tr >"+
								"<td id='pzrq'>"+PZRQ+"</td>"+
								"<td name='pzlx' value='"+PZLX+"'>"+PZLX+"</td>"+
								"<td name='pzbh' value='"+PZBHGUID+"'>"+PZH+"</td>"+
								"<td id='zy'>"+ZY+"</td>"+
								"<td >"+KMMC+"</td>"+
								"<td >"+BMMC+"</td>"+
								"<td >"+XMMC+"</td>"+
								"<td id='jfje' >"+JFJE+"</td>"+
								"<td id='dfje'>"+DFJE+"</td>"+							
								"<td id='ye'>"+YE+"</td>"+
								"</tr>"
					);}					
			},
		}); 
	   
	  
	  
	//双击事件
// 	$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
// 		   var val2 = $(this).find("[name='dmxh']").val();
// 		   var val3 = "("+$(this).find("[name='dm']").val()+")"+$(this).find("[name='dmmc']").val();
// 		   select_commonWin("${ctx}/webView/kjhs/bbzx/zjrbbmx.jsp","校内存款明细","920","630");	
		    	
// 	}); 
});
function kz(bbqj,kmbh,kmmc){
	//location.href="${ctx}/mxz/toUrl?kmbh="+kmbh+"&bbqj="+bbqj;
	location.href="${ctx}/mxz/toUrl?bbqj="+bbqj+"&kmbh=${kmbh}&pageUrl=mxb&type=mxz&ly=jjkm";
	
}
function helloWord(){
	var jump = "${jump}";
	var clicks = "${clicks}";
	if(jump=="yes"&&clicks!="yes"){
		$("#btn_sea").click();
	}
}
$("[id$=btn_sea]").click(function(){
  	select_commonWin("${ctx}/mxz/jumpWindow","报表条件-明细账","550","360");
   });      
//点击打印
$("#dyyl").click(function(){
	 PreviewMytable();
}); 

//打印
// $("[id$=print]").click(function(){
// 	select_commonWin("${ctx}/webView/kjhs/pzxx/print.jsp","双击页面打印","920","630");
// });
function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}table{width:100%!important;}</style>"
		LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle+document.getElementById("div_print").innerHTML);
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

	
//导出Excel
	$("#btn_exp").click(function() {
		var kmbh = $("[name='kmbh']").val();
		console.log("kmbh=+=="+kmbh);
// 		var kmbh = [];
 		var checkbox = $("#mydatatables").find("#tr_kmbh").filter(":checked");
//  		if(checkbox.length > 0){
// 			checkbox.each(function(){
// 				kmbh.push($(this).val());
// 			});
// 			kmbh = kmbh.join("','");
//  		}else{
//  			kmbh = "";
//  		}
//	    var bbyf = $("[name='bbyf']").val();
	$.ajax({
			type : "post",
			data : {kmbh:kmbh},
			url : "${ctx}/mxz/expExcel2?kmbh=kmbh",
			success : function(val) {
			 FileDownload("${ctx}/file/fileDownload","明细账.xls",val.url);
		   }
			
	});
});	

	//双击事件 id="header"
	$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
// 		var bbqj = "${param.kmbh}"
		var vamc = $(this).find("[name='pzbh']").attr("value");
// 		var kkqjj1 = "${param.bbqj}";
		pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&guid="+vamc);
// 		alert(vamc);
// 		doOperate("${ctx}/mxz/zjc?bbqj="+bbqj+"&kkqjj="+kkqjj1+"&pzbh="+vamc);
// 			var target = "${ctx}/pzcxs";
// 			pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&guid="+vamc);
// 			pageSkip(target,"pzlr&pzz=undefined&pzbh="+vamc);
	});

	
	
	
</script>
</body>
</html>