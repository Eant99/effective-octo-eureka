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
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="">
			<div class="box-header clearfix">
			</div>
    		<div class="search-simple">
				<div class="btn-group pull-right" role="group">
<!-- 					<button class="btn btn-default" type="button" id="btn1111">查询</button> -->
<!-- 	                <button class="btn btn-default" type="button" id="dyyl">打印</button> -->
<!-- 	                <button type="button" class="btn btn-default" id="btn_export">导出Excel</button> -->
						<button type="button" class="btn btn-default" id="btn_back">返回</button>
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid" id="div_print">
		<div class='responsive-table'>
			<div class='scrollable-area'>
				<h4 style="text-align:center;"><div style="padding-bottom:5px; width:110px;margin:0 auto;">科&nbsp;目&nbsp;余&nbsp;额&nbsp;表</div></h4>
				<div style="text-align:center;margin-bottom:20px;">报表期间：${date}</div>
		        <table id="mydatatables" class="table table-striped table-bordered">		        	
					<thead>
		 				<tr  id="header">
						    <th rowspan="2">科目编号</th>
						    <th rowspan="2">科目名称</th>
						    <th colspan="2">期初余额</th>
						    <th colspan="2">本期发生</th>
						    <th colspan="2">期末余额</th>
			   			</tr>
			   			<tr>
						    <th style="width:5%;">方向</th>
						    <th>余额</th>
						    <th>借方</th>
						    <th>贷方</th>
						    <th style="width:5%;">方向</th>
						    <th>余额</th>
			   			</tr>
			   			<tr>
							<th>1001</th>			   			
							<th>库存现金</th>			   			
							<th>借方</th>			   			
							<th>1000.00</th>			   			
							<th>银行存款</th>			   			
							<th>校内存款</th>			   			
							<th>贷方</th>			   			
							<th>5000.00</th>			   			
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
$(function(){
	var flag = "${flag}";
	if(flag==="select"){
		jumpWindow();
	}
	/* var columns = [
		{"data": "KMBH",orderable:false, "render": function (data, type, full, meta){
      			return '<input type="checkbox" name="kmbh"  value="'+full.KMBH+'">';
   		},"width":10,'searchable': false,class:"text-center"},
	   	{"data": "KMBH",defaultContent:""},
	   	{"data": "KMMC",defaultContent:""},
	   	{"data": "QCFX",defaultContent:""},
	   	{"data": "QCYE",defaultContent:"",class:"text-right"},
	   	{"data": "BQJF",defaultContent:"",class:"text-right"},
	   	{"data": "BQDF",defaultContent:"",class:"text-right"},
	   	{"data": "QMFX",defaultContent:""},
	   	{"data": "QMYE",defaultContent:"",class:"text-right"}
	];
   table = getDataTableByListHj("mydatatables","${ctx}/kmye/getPageList",[],columns,0,1,pjhj); */
	 $.ajax({
			type:"post",
			async : false, 
			url:"${ctx}/kmye/getPageList",
			dataType: "json",
			success:function(val){
				for (var i=0;i<val.length;i++){
					 var KMBH=val[i].KMBH;if(KMBH==null) KMBH='';
					 var KMMC=val[i].KMMC;if(KMMC==null) KMMC='';
					 var QCFX=val[i].QCFX;if(QCFX==null) QCFX='';
					 var QCYE=val[i].QCYE;if(QCYE==null) QCYE='';
					 var BQJF=val[i].BQJF;if(BQJF==null) BQJF='';
					 var BQDF=val[i].BQDF;if(BQDF==null) BQDF='';
					 var QMFX=val[i].QMFX;if(QMFX==null) QMFX='';
					 var QMYE=val[i].QMYE;if(QMYE==null) QMYE='';
					 $("tbody").append(
								"<tr name='guid'>"+
								"<td id='kmbh' name='kmbh' value='"+KMBH+"'>"+KMBH+"</td>"+
								"<td id='kmmc'>"+KMMC+"</td>"+
								"<td id='fx'>"+QCFX+"</td>"+
								"<td >"+QCYE+"</td>"+
								"<td >"+BQJF+"</td>"+
								"<td >"+BQDF+"</td>"+
								"<td id='fx' >"+QMFX+"</td>"+
								"<td >"+QMYE+"</td>"+
								"</tr>"
					);}
				pjhj()
				
			},
		});	 
//    kz();
});
</script>
<script >
function jumpWindow(){
	select_commonWin("${ctx}/kmye/jumpWindow","报表条件-科目余额表","600","470");
}
//刷新页面
function toUrl(params,date,gs){
	if(params){
		location.href = "${ctx}/kmye/kmyeList?flag=no&date="+date+"&gs="+gs;
	}
}
$("#btn1111").click(function(){
	jumpWindow();
});
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
function pjhj(){
	$("tbody").append("<tr class='nojs' id='one'>"+
		"<td colspan='2' rowspan='2' style='text-align:center;'>合计</td>"+
		"<td id='fx'>借</td>"+
		"<td id='qcj' ></td>"+
		"<td rowspan='2' id='bqj' ></td>"+
		"<td rowspan='2' id='bqd' ></td>"+
		"<td id='fx' >借</td>"+
		"<td id='qmj' ></td>"+
		"</tr>"+
		"<tr class='nojs' id='two'>"+
		"<td id='fx'>贷</td>"+
		"<td id='qcd' ></td>"+
		"<td id='fx'>贷</td>"+
		"<td id='qmd' ></td>"+
		"</tr>"
		);
	$.ajax({
		url:"${ctx}/kmye/computer",
		dataType:"json",
		type:"post",
		success:function(data){
			if(data){
				$("#qcj").text(data.qcj);
				$("#qcd").text(data.qcd);
				$("#qmj").text(data.qmj);
				$("#qmd").text(data.qmd);
				$("#bqj").text(data.bqj);
				$("#bqd").text(data.bqd);
			}
		}
	});
}
//返回按钮
$("#btn_back")
		.click(
				function(e) {
					doOperate("${ctx}/mbxz/mbxz_list");
					return false;
				});


$(".cxslook").click(function(){
	 var guid = $(this).parents("tr").find("[name='guid']").val();
	 var pzbh = $(this).parents("tr").find("[name=guid]").attr("pzbh");
	 var pzz = $(this).parents("tr").find("[name=guid]").attr("pzz");
	 pageSkip(target,"pzlr&pzz="+pzz+"&pzbh="+pzbh);
		
})
//导出Excel
$("#btn_export").click(function() {
//		var kmbh = $("[name='kmbh']").val();
//		alert("hahah"+kmbh);
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
//	    var bbyf = $("[name='bbyf']").val();
$.ajax({
		type : "post",
		data : {kmbh:kmbh},
		url : "${ctx}/kmye/expExcel2",
		success : function(val) {
//				alert(kmbh);
		 FileDownload("${ctx}/file/fileDownload","科目余额表.xls",val.url);
	   }
});
});

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


//双击事件 id="header"
$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	var vamc = $(this).find("[name='kmbh']").attr("value");
// 	select_commonWin("${ctx}/kmye/zjc?kmbh="+vamc,"科目列表","920","630");
	var bbqj = "${param.date}"
// 	alert(bbqj+"ddd="+vamc);
	doOperate("${ctx}/kmye/zjc?kmbh="+vamc+"&bbqj="+bbqj);
});


//打印
// $("[id$=print]").click(function(){
// 	 var guid = $(this).parents("tr").find("[name='guid']").val();
// 	 var pzbh = $(this).parents("tr").find("[name=guid]").attr("pzbh");
// 	 var pzz = $(this).parents("tr").find("[name=guid]").attr("pzz");
// 	select_commonWin("${ctx}/webView/kjhs/bbzx/kmye/kmyetc.jsp","双击页面打印","920","630");
// });

</script>
</body>
</html>