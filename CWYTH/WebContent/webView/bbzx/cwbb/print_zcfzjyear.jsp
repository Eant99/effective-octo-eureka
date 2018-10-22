<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产负债表（月度）</title>
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
	}
	tr th{
	    border-bottom: 0px solid #ddd !important;
	}
/* 	.fullscreen{ */
/* 	width:80%; */
/* 	margin:0 auto; */
/* 	} */
.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
    	border-bottom-width: 0px;
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}

</style>
</head>
<body>
<div class="fullscreen">

	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="btn-group pull-right" role="group">
				    <button type='button' class="btn btn-default" id="btn_back">返回</button>
					<button type='button' class="btn btn-default" id="btn_print">打印</button>
				</div>
			</div>
		</form>
	</div>
	
<div class="container-fluid" id="print_div">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" border="1" bordercolor="#000000" style="border-collapse:collapse;width:100%;margin:0 auto 30px">
			<div id="print_title">
					<div id="print_title">
			<h2 style="text-align:center;">资产负债表（年度）</h2>
			<div style="text-align:right;font-size:12px;">会高校01表</div>
					<div style="text-align:left;font-size:12px;float:left;width:33%">编制单位：${bzdw.XXMC}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：${bbnd }年度</div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
					</div>
		        	<thead>
				        <tr>
				            
				            <th style="text-align:center; width: 150px" id="tr_pzrq">资产</th>
				            <th style="text-align:center;" id="tr_kmbh">期末余额</th>
				            <th style="text-align:center;" id="tr_zy">年初余额</th>				            
				            <th style="text-align:center; width: 150px" id="tr_kmmc">负债和净资产</th>
				             <th style="text-align:center;" id="tr_kmbh">期末余额</th>	
				            <th style="text-align:center;" id="tr_dfje">年初余额</th>	    
				        </tr>
					</thead>
				    <tbody>
				     <tbody>
				    <c:forEach var="list" items="${list}" varStatus="i">
				 		<tr class="tr_${i.index+1}">
				 			<td class="num" style='padding:0px 1px'>
				 				${list.ZC}
				 			</td>
				 			<td class="num" style="text-align:right;padding:0px 1px">
				 			${list.QMYE1}
				 			</td>
				 			<td class="num" style="text-align:right;padding:0px 1px">
				 			${list.NCYE1}
				 			</td>
				 			<td class="num" style='padding:0px 1px'>
				 				${list.FZHJZC}
				 			</td>
				 			<td class="num" style="text-align:right;padding:0px 1px">
				 			${list.QMYE2}
				 			</td>
				 			<td class="num" style="text-align:right;padding:0px 1px">
				 			${list.NCYE1}
				 			</td>
				 		</tr>
				 	</c:forEach>
				    </tbody>
				   </tbody>
				</table>					
		</div>
			</div>
		</div>	
<!-- <div class="bottom1"> -->
<!-- <table> -->
<!-- <tr> -->
<!-- <td>单位主要负责人（签字）：</td> -->
<!-- <td>财务负责人（签字）：</td> -->
<!-- <td>制表人（签字）：</td> -->
<!-- </tr> -->
<!-- <tr> -->
<!-- <td> -->
<!-- 注：本表反映核算单位各项资产及负债情况。 -->
<!-- </td> -->
<!-- </tr> -->
<!-- </table> -->
<!-- </div> -->
		</div>

<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
 $(function () {
		$("#btn_back").click(function(){
			location.href="${ctx}/zcfzj/goZcfzyearPage?bbnd=${bbnd}&jzpz=${jzpz}&bblx=${bblx}&sfjz=${sfjz}&ztgid=${ztgid}"
		});
	 $("#btn_print").click(function(){
			PreviewMytable();
		});
	 
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