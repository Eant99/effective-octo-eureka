<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.googosoft.util.Validate"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入支出决算总表打印</title>
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
<%String jsyf = Validate.isNullToDefaultString(request.getAttribute("jsyf"), "");
jsyf = jsyf.replace("-","年")+"月"; 
%>
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
			<table id="mydatatables" class="table table-striped table-bordered" border="0" bordercolor="#000000" style="border:1px;border-collapse:collapse;margin:0 auto 30px">
			<div id="print_title">
			<h2 style="text-align:center;">收入支出决算总表</h2>
					<div style="text-align:right;font-size:12px;">财决01表</div>
					<div style="text-align:left;font-size:12px;float:left;width:33%">编制单位：${bzdw.XXMC}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：<%=jsyf %></div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
					</div>
					</div>
		        	<thead>
				        <tr>
				            <th colspan="4" style="text-align:center;" >收入</th>
				            <th colspan="8" style="text-align:center; ">支出</th>
				        </tr>
				       </tr>
				        <tr>
				            <th style="text-align:center;width:8%" >项目</th>
				            <th style="text-align:center;width:5% ">行次</th>
				            <th style="text-align:center;" >年初预算数</th>
				            <th style="text-align:center; ">期末数</th>
				            <th style="text-align:center;width:8%" >项目（按功能分类）</th>
				            <th style="text-align:center;width:5% ">行次</th>
				            <th style="text-align:center;" >年初预算数</th>
				            <th style="text-align:center; ">期末数</th>
				            <th style="text-align:center;width:8%">项目（按支出性质和经济分类）</th>
				            <th style="text-align:center;width:5% ">行次</th>
				            <th style="text-align:center;" >年初预算数</th>
				            <th style="text-align:center; ">期末数</th>
				        </tr>
				         <tr>
				            <th style="text-align:center;" >栏次</th>
				            <th style="text-align:center; "></th>
				            <th style="text-align:center;" >1</th>
				            <th style="text-align:center; ">2</th>
				            <th style="text-align:center;" >栏次</th>
				            <th style="text-align:center; "></th>
				            <th style="text-align:center;" >3</th>
				            <th style="text-align:center; ">4</th>
				            <th style="text-align:center;" >栏次</th>
				            <th style="text-align:center; "></th>
				            <th style="text-align:center;" >5</th>
				            <th style="text-align:center; ">6</th>
				        </tr>
					</thead>
				    <tbody>
				     <tbody>
				    <c:forEach var="list" items="${list}"  begin="0" step="1"   varStatus="i">
				    <c:if test="${i.index<23}">
				 		<tr class="tr_${i.index+1}">
				 			<td class="text text-left" style="text-align:left;padding:0 1px">
				 				${list.SR}
				 			</td>
				 			<td class="text-center" style="text-align:center;padding:0 1px">
				 			${list.HC1}
				 			</td>
				 			<td class="num text-right" style="text-align:right;padding:0 1px">
				 			${list.NCYSS1}
				 			</td>
				 			<td class="num text-right" style="text-align:right;padding:0 1px">
				 			${list.QMS1}
				 			</td>
				 			<td class="text text-left" style="text-align:left;padding:0 1px">
				 				${list.ZC1}
				 			</td>
				 			<td class="text-center" style="text-align:center;padding:0 1px">${list.HC2}
				 			</td>
				 			<td class="num text-right" style="text-align:right;padding:0 1px">
				 			${list.NCYSS2}
				 				
				 			</td>
				 			<td class="num text-right" style="text-align:right;padding:0 1px">
				 			${list.QMS2}
				 			</td>
				 			<td class="text text-left" style="text-align:left;padding:0 1px">
				 				${list.ZC2}
				 			</td>
				 			<td class="text-center" style="text-align:center;padding:0 1px">
				 			${list.HC3}
				 			</td>
				 			<td class="num text-right" style="text-align:right;padding:0 1px">
				 			${list.NCYSS3}
				 			</td>
				 			<td class="num text-right" style="text-align:right;padding:0 1px">
				 			${list.QMS3}
				 			</td>
				 		</tr>
				 		</c:if>
				 		<c:if test="${i.index>=23&&i.index<=35}">
				 	<tr class="tr_${i.index+1}">
				 			<td class="text text-left" style="text-align:left;padding:0 1px">
				 				${list.SR}
				 			</td>
				 			<td class="text-center" style="text-align:center;padding:0 1px">
				 			${list.HC1}
				 			</td>
				 			<td class="num" style="text-align:right;padding:0 1px">
				 			${list.NCYSS1}
				 			</td>
				 			<td class="num" style="text-align:right;padding:0 1px">
				 			${list.QMS1}
				 			</td>
				 			<td class="text text-left" colspan="5" style="text-align:cneter;padding:0 1px">
				 				${list.ZC1}
				 			</td>
				 			<td class="text-center" style="text-align:center;padding:0 1px">
				 			${list.HC3}
				 			</td>
				 			<td class="num" style="text-align:right;padding:0 1px">
				 			${list.NCYSS3}
				 			</td>
				 			<td class="num" style="text-align:right;padding:0 1px">
				 			${list.QMS3}
				 			</td>
				 		</tr>
				 	</c:if>
				 	</c:forEach>
				    </tbody>
				   </tbody>
				   <tfoot>
				   <tr class="hide" style="text-align:right;">
				   <td width="100%" colspan="12">
				   <font tdata="PageNO" format="00" >第##页，</font><font tdata="PageCount" format="00">共##页</font>
				   </td>
				   </tr>
				   </tfoot>
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
			location.href="${ctx}/srfyj/goSrzcjszbPage?jzpz=${jzpz}&jsyf=${jsyf}&sfjz=${sfjz}&ztgid=${ztgid}"
		});
	 $("#btn_print").click(function(){
			PreviewMytable();
		});
	 
});
/* $(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
}); */
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