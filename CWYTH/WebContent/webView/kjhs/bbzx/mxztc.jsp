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
	.tool-fix{
	display: none;
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
</style>
</head>
<body>
<div class="fullscreen">
<!-- 		<div class="pull-right"> -->
<!-- 			 <button type="button" class="btn btn-default" id="btn_back">返回</button> -->
<!--         </div> -->
        <div class="search" id="searchBox" style="margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="">
			<div class="box-header clearfix">
			</div>
    		<div class="search-simple">
				<div class="btn-group pull-right" role="group">
					 <button type="button" class="btn btn-default" id="btn_back">返回</button>
<!-- 	                <button class="btn btn-default" type="button" id="dyyl">打印</button> -->
<!-- 	                <button type="button" class="btn btn-default" id="btn_export">导出Excel</button> -->
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid" style="width: 80%">

<!-- 		<center> -->
<!-- 			<tr style="height:25px;"> -->
<!-- 		       <td colspan="3" align="center" style="height: 40px"> -->
<!--                  <span id="txt_bbmc" style="font-size: 16pt">资产负债表（月度）</span> -->
<!--                </td> -->
<!-- 	        </tr> -->
<!-- 	    </center> -->
  		
	    
	    <div class="btn-group pull-right" role="group">
	          <ul>
	          <li>
	    	 <td align="left" valign="middle" style="width: 400px; height: 20px;">
                              报表期间：${param.kkqjj}            
                </td> 
                </tr>
                </li>
                <li>
                <tr>
                <td align="right" valign="bottom" style="width: 429px; height: 20px;">
                              第1页/共1页
                </td> 
                </tr>
                </li>
                </ul>  
	    </div>
	  
	    <div style="margin-top: 20px;position: absolute;">        	 
                                科目名称：${param.kmbh}
	    </div>
	      <div style="margin-left: 47.5%;margin-top:20px">    
	    	
                     ${sysDate}
               
	    </div>
	</div>

<form id="mysave" method="post">
<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:82%;margin:0 auto">
		        	<thead>
				        <tr id="header">
				            <th style="text-align:center; width: 150px" id="tr_pzrq">凭证日期</th>
				            <th style="text-align:center;" id="tr_pzbh">凭证号</th>
				            <th style="text-align:center;" id="tr_zy">摘要</th>				            
				            <th style="text-align:center; width: 150px" id="tr_jfje">借方金额</th>
				             <th style="text-align:center;" id="tr_dfje">贷方金额</th>	
				            <th style="text-align:center;" id="tr_fx">方向</th>	
				            <th style="text-align:center;" id="tr_ye">余额</th>	        
				        </tr>
					</thead>
				     <tbody>
				    <c:forEach var="mxz" items="${mxzlist}" varStatus="i">
				 		<tr>
				 			<td>${mxz.PZRQ}</td>
				 			<td class="pzbh">${mxz.PZBH}</td>
				 			<td>${mxz.ZY}</td>
				 			<td>${mxz.JFJE}</td>
				 			<td>${mxz.DFJE}</td>
				 			<td>${mxz.JDFX}</td>
				 			<td>${mxz.QMYE}</td>
				 		</tr>
				 	</c:forEach>
				    </tbody>
				</table>					
		</div>
			</div>
		</div>	
	</form>		
		</div>

<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
var target = "${ctx}/pzlr";
$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	var pzbh = $(this).find(".pzbh").text();
	var pzrq = $(this).find(".pzrq").text();
 	pageSkip(target,"pzcx&pzbh="+pzbh+"&pzrq="+pzrq);
});
$("#btn_back").click(function(){
	history.back();
})
</script>
</body>
</html>