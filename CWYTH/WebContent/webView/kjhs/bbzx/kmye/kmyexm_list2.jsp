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
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
			<div class="box-header clearfix">
			</div>
    		<div class="search-simple">
				<div class="btn-group pull-right" role="group">
					<button class="btn btn-default" type="button" id="btn_search">查询</button>
	                <button class="btn btn-default" type="button" id="dyyl">打印预览</button>
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid" id="div_print">
		<div class='responsive-table'>
			<div class='scrollable-area' >
				<h4 style="text-align:center;"><div style="padding-bottom:5px; width:110px;margin:0 auto;">科&nbsp;目&nbsp;余&nbsp;额&nbsp;表</div></h4>
				<div style="text-align:center;margin-bottom:20px;">报表期间：${date}</div>
		        <table id="mydatatables" class="table table-striped table-bordered">
					<thead>
		 				<tr>
						    <th rowspan="2">科目编号</th>
						    <th rowspan="2">科目名称</th>
						    <th rowspan="2">部门名称</th>
						    <th rowspan="2">项目名称</th>
						    <th colspan="2">期初余额</th>
						    <th colspan="2">本期发生</th>
						    <th colspan="2">期末余额</th>
			   			</tr>
			   			<tr>
						    <th>借方</th>
						    <th>贷方</th>
						    <th>借方</th>
						    <th>贷方</th>
						    <th>借方</th>
						    <th>贷方</th>
			   			</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%> 
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function(){
	/* var columns = [
		{"data": "KMBH",orderable:false, "render": function (data, type, full, meta){
				return '<input type="checkbox" name="kmbh"  value="'+full.KMBH+'">';
		},"width":10,'searchable': false,class:"text-center"},
	   	{"data": "KMBH",defaultContent:""},
	   	{"data": "KMMC",defaultContent:""},
	   	{"data": "BMMC",defaultContent:""},
	   	{"data": "XMMC",defaultContent:""},
	   	{"data": "QCFX",defaultContent:"",class:"text-right"},
	   	{"data": "QCYE",defaultContent:"",class:"text-right"},
	   	{"data": "BQJF",defaultContent:"",class:"text-right"},
	   	{"data": "BQDF",defaultContent:"",class:"text-right"},
	   	{"data": "QMFX",defaultContent:"",class:"text-right"},
	   	{"data": "QMYE",defaultContent:"",class:"text-right"}
	];
   table = getDataTableByListHj("mydatatables","${ctx}/kmye/getPageList2",[],columns,0,1,pjhj); */
   
   $.ajax({
		type:"post",
		async : false, 
		url:"${ctx}/kmye/getPageList2",
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
							"<tr>"+
							"<td>"+KMBH+"</td>"+
							"<td>"+KMMC+"</td>"+
							"<td>"+QCFX+"</td>"+
							"<td >"+QCYE+"</td>"+
							"<td >"+BQJF+"</td>"+
							"<td >"+BQDF+"</td>"+
							"<td >"+QMFX+"</td>"+
							"<td >"+QMYE+"</td>"+
							"</tr>"
				);}					
		},
	});
   kz();
});
</script>
<script >
function jumpWindow(){
	select_commonWin("${ctx}/kmye/jumpWindow","选择查询条件","600","350");
}
//刷新页面
function toUrl(params,date,gs){
	if(params){
		location.href = "${ctx}/kmye/kmyeList2?flag=no&date="+date+"&gs="+gs;
	}
}
$("#btn_search").click(function(){
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
	var check = $("[aria-live='polite']").text();
	$("tbody").append("<tr class='nojs' id='one'>"+
		"<td colspan='4' rowspan='2' style='text-align:center;'>合计</td>"+
		"<td rowspan='2' id='qcj' style='text-align:left;'></td>"+
		"<td rowspan='2' id='qcd' style='text-align:left;'></td>"+
		"<td rowspan='2' id='bqj' style='text-align:left;'></td>"+
		"<td rowspan='2' id='bqd' style='text-align:left;'></td>"+
		"<td rowspan='2' id='qmj' style='text-align:left;'></td>"+
		"<td rowspan='2' id='qmd' style='text-align:left;'></td>"+
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
</script>
</body>
</html>