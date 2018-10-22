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
				<!-- <div class="form-group">
	                <label>选择日期</label>
	             	<input  name="" class="input_info date form-control" style="width:100px;height:25px" value="2017-10-01" data-format="yyyy-MM-dd"/>
					 <label>至&nbsp; </label><input  name="" class="input_info date form-control" style="width:100px;height:25px" value="2017-10-31" data-format="yyyy-MM-dd"/>
					&nbsp;
					
					 <label>是否包含未记账凭证&nbsp; </label><select style="width:60px;" class="form-control select">					
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
					&nbsp;				
					 <label>是否包含结转凭证&nbsp; </label><select style="width:60px;" class="form-control select">					
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
					
				</div> -->
				<div class="btn-group pull-right" role="group">
				    <button type='button' class="btn btn-default" id="btn_back">返回</button>
					<button type='button' class="btn btn-default" id="btn_print">打印</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid" style="width: 80%">

		<center>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                 <span id="txt_bbmc" style="font-size: 16pt">资产负债表（年度）</span>
               </td>
	        </tr>
	    </center>
	    
	    <div class="btn-group pull-right" role="group">
	          <ul>
	          <li>
	    	 <td align="right" valign="middle" style="width: 400px; height: 20px;">
                              会高校01表             
                </td> 
                </tr>
                </li>
                <li>
                <tr>
                <td align="right" valign="bottom" style="width: 429px; height: 20px;">
                              单位：元
                </td> 
                </tr>
                </li>
                </ul>  
	    </div>
	  
	    <div style="margin-top: 20px;position: absolute;">        	 
                                编制单位：${bzdw.XXMC}
	    </div>
	      <div style="margin-left: 47%;margin-top:20px">    
	    	
                       ${sysDate}年度
               
	    </div>
	</div>


<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:82%;margin:0 auto">
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
				 			
				 			<td class="num">
				 				${list.ZC}
				 			</td>
				 			<td class="num">
				 				${list.QMYE1} 
				 				
				 			</td>
				 			<td class="num">
				 				${list.NCYE1}
				 			</td>
				 			<td class="num">
				 				${list.FZHJZC}
				 				
				 			</td>
				 			<td class="num">
				 				${list.QMYE2}
				 			</td>
				 			<td class="num">
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
			location.href="${ctx}/zcfz/zcfzbyear?bbnd=${sysDate}&jzpz=${jzpz}&bblx=${bblx}&sfjz=${sfjz}&ztgid=${ztgid}"
		});
	 $("#btn_print").click(function(){
			PreviewMytable();
		});
	 
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
function PreviewMytable(){
	var LODOP=getLodop();  
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
	var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}table{width:100%!important;}</style>"
	LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle+document.getElementById("print_div").innerHTML);
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