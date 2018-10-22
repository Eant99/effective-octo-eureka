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
	.sorting{
		position:static !important;
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
	#pzrq{
	text-align: center;
	}
	#zy{
	text-align: left;
	}
	#fx{
	text-align: center;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="btn-group pull-right" role="group">
<!-- 				<button type='button' class="btn btn-default" id="btn_sea">查询</button> -->
<!-- 				<button type='button' class="btn btn-default" id="dyyl">打印</button> -->
<!-- 				<button type="button" class="btn btn-default" id="btn_export">导出Excel</button>	 -->
					<button type="button" class="btn btn-default" id="btn_back">返回</button>
				</div>
			</div>
		</form>
	</div>
<div class="container-fluid" id="div_print">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:100%;margin:0 auto">
					<h4 style="text-align:center;"><div style="padding-bottom:5px; margin:0 auto;display:inline-block;"><a style="color:black;text-decoration: none;">总&nbsp;账</a></div></h4>
					<div style="text-align:center;margin-bottom:20px;">报表期间：${kkqjj} &nbsp;&nbsp;&nbsp;&nbsp;科目:${kmxx}</div>
		        	<thead>
				        <tr id="header">				       
				            <th  style="text-align:center; width: 100px" id="tr_pzrq">凭证日期</th>
				            <th  style="text-align:center; width: 300px !important"  id="tr_kmbh">摘要</th>
				            <th  style="text-align:center; width: 100px !important" id="tr_zy">借方金额</th>				            
				            <th  style="text-align:center; width: 100px !important" id="tr_kmmc">贷方金额</th>
				            <th  style="text-align:center; width: 50px !important" id="tr_dfje">方向</th>	
				            <th  style="text-align:center; width: 100px !important" id="tr_kmmc">余额</th>
				        </tr>				       
				        
				        
					</thead>
				    <tbody>
				    <tbody>
				   <tr class=""><td id="pzrq">2018-01-20</td><td id="zy">日合计</td><td></td><td>555.00</td><td id="fx">借方</td><td>-555.00</td></tr><tr class=""><td id="pzrq">2018-02</td><td id="zy">本月合计</td><td>11,154.00</td><td>69.96</td><td id="fx">借方</td><td>11,084.04</td></tr><tr class=""><td id="pzrq">2018-01</td><td id="zy">本月合计</td><td></td><td>555.00</td><td id="fx">借方</td><td>-555.00</td></tr></tbody>
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
	 $.ajax({
			type:"post",
			async : false, //同步请求/CWYTH/src/com/googosoft/controller/kjhs/ZzController.java
			url:"${ctx}/Zz/getPageList?kmbh=${kmbh}",
			dataType: "json",
			success:function(val){
				for (var i=0;i<val.length;i++){
					 var PZRQ=val[i].PZRQ;if(PZRQ==null) PZRQ='';
					 var ZY=val[i].ZY;if(ZY==null) ZY='';
					 var JFJE=val[i].JFJE;if(JFJE==null) JFJE='';
					 var DFJE=val[i].DFJE;if(DFJE==null) DFJE='';
					 var FX=val[i].FX;if(FX==null) FX='';
					 var YE=val[i].YE;if(YE==null) YE='';
					 $("tbody").append(
								"<tr>"+
								"<td id='pzrq'>"+PZRQ+"</td>"+
								"<td id='zy'>"+ZY+"</td>"+
								"<td>"+JFJE+"</td>"+
								"<td>"+DFJE+"</td>"+
								"<td id='fx' >"+FX+"</td>"+
								"<td >"+YE+"</td>"+
								"</tr>"
					);}					
			},
		});
	 	
	 	jumpWindow();
	//列表数据
	/*    var columns = [
	       {"data":"PZRQ",orderable:false,'render':function(data,type,full,meta){
	          	return data;},"width":41,"searchable":false,"class":"text-center"},
	       {"data": "ZY",defaultContent:""},
	       {"data": "JFJE",defaultContent:""},
	       {"data": "DFJE",defaultContent:""},
	       {"data": "FX",defaultContent:""},
	       {"data": "YE",defaultContent:"","class":"text-center"}	      
	     ];
		  table = getDataTableByListHj("mydatatables","${ctx}/Zz/getPageList?kmbh=${kmbh}",[1,'desc'],columns,0,1,setGroup); */
		 
	
    $("[id$=btn_sea]").click(function(){
		 select_commonWin("${ctx}/Zz/jumpWindow","报表条件-总账","540","420");
   });
// 	 $("[id$=print]").click(function(){
		 
// 		 select_commonWin("${ctx}/webView/kjhs/pzxx/print.jsp","双击页面打印","920","630");
//    });
	 
	 //双击事件
	 /*    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	    	var val2 = $(this).find("[name='dmxh']").val();
	        var val3 = "("+$(this).find("[name='dm']").val()+")"+$(this).find("[name='dmmc']").val();
	        select_commonWin("${ctx}/webView/kjhs/bbzx/zjrbbmx.jsp","校内存款明细","920","630");	
	    }); */
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

$(function(){
// 	$("div[name='tb']").prop("innerHTML","");
	var jb=$("#qz1").val();
	var dm=$("#qz2").val();
// 	alert("jb="+jb+"dm="+dm);
if(jb==""||jb==null){
	jb="root";
}
	$.ajax({
		type:"post",
		async : false, //同步请求/CWYTH/src/com/googosoft/controller/kjhs/ZzController.java
		url:"${ctx}/Zz/getXxList?jb="+jb+"&dm="+dm,
// 		data:,
		dataType: "json",
		success:function(val){
// 			  $("").html(dates);//要刷新的div
			var km=val[0].KMMC; 
			var bm=val[0].BMHS;
			var bbqj=val[0].KMND;
			 $("#bm").html(bm);
			 $("#km").html(km);
			 $("#bbqj").html(bbqj);//要刷新的div
// 			  alert(ss);
			  },
	});	
});

function jumpWindow(){
	var clicks = "${clicks}";
	var jump  = "${jump}";
	if(jump=="yes"&&clicks!="yes"){
		 select_commonWin("${ctx}/Zz/jumpWindow","报表条件-总账","540","420");
	}
}

function toUrl(type,kmbh,kkqjj){
	if("first"==type){
		parent.location.href="${ctx}/webView/kjhs/bbzx/mainKjkmszTree.jsp?type="+type+"&kmbh="+kmbh+"&kkqjj="+kkqjj;
	}else{
		parent.location.href="${ctx}/webView/kjhs/bbzx/mainKjkmszTree2.jsp?type="+type+"&kmbh="+kmbh+"&kkqjj="+kkqjj;
	}
}
$("#dyyl").click(function(){
	PreviewMytable();
}); 
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

//返回按钮
$("#btn_back")
		.click(
				function(e) {
					doOperate("${ctx}/mbxz/mbxz_list");
					return false;
				});
//导出Excel
$("#btn_export").click(function() {
//		var kmbh = $("[name='kmbh']").val();
//		alert("hahah"+kmbh);
	var kmbh = [];
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		if(checkbox.length > 0){
		checkbox.each(function(){
			kmbh.push($(this).val());
		});
		kmbh = kmbh.join("','");
		}else{
			kmbh = "";
		}
//	    var bbyf = $("[name='bbyf']").val();
$.ajax({
		type : "post",
		data : {kmbh:kmbh},
		url : "${ctx}/Zz/expExcel2",
		success : function(val) {
//				alert(kmbh);
		 FileDownload("${ctx}/file/fileDownload","总账.xls",val.url);
	   }
});
});
//打印
// $("[id$=print]").click(function(){
// 	select_commonWin("${ctx}/webView/kjhs/pzxx/print.jsp","双击页面打印","920","630");
// });



/* $("#dyyl").click(function(){
	location.href = "${ctx}/kmye/goPrint?gs=haha&flag=no&date=${date}";
}); */


</script>
</body>
</html>