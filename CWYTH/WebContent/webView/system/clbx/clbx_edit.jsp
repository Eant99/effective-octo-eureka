<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
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
	.xx{	
		width:30px !important;
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
	.bottom1 tr td{ */
 	width: 400px !important; */
 	} 
	tr td{
	height: 30px;
	
	}
	tr td{
	    border-bottom: 0px solid #ddd !important;
	}
	.number{
	text-align:right !important;
	}
/* 	.fullscreen{ */
/* 	width:80%; */
/* 	margin:0 auto; */
/* 	} */
.table-bordered > tdead > tr > td, .table-bordered > tdead > tr > td {
    	border-bottom-width: 0px;
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}
</style>
</head>
<body style="overflow-x:scroll">
<div class="fullscreen" style="overflow-x:scroll">
 	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 7px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 28px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="btn-group pull-right" role="group">
				    <button type='button' class="btn btn-default" style="margin-top: -25px;" id="btn_save">保存</button>
<!-- 					<button type='button' class="btn btn-default" id="btn_print">打印预览</button> -->
<!-- 					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button> -->
				</div>
			</div>
		</form>
	</div>
		<div class="container-fluid" style="width: 80%">
			<center>
				<tr style="height:25px;">
			       <td colspan="3" align="center" style="height: 40px">
	                 <span id="txt_bbmc" style="font-size: 16pt">差旅住宿费和伙食补助费标准表</span>
	               </td>
		        </tr>
		    </center>
		</div>
		<div>
			<span style="margin-left: 1123px;">单位：元/天</span>
		</div>
	<form id="mysave" metdod="post">
	<div class="container-fluid" style="overflow: auto;">
		<div class='responsive-table'>
			<div class='scrollable-area' > 
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width: 82%;margin:0 auto">
		        	<thead>
				        <tr>
				        	<th rowspan="2" style="text-align:center;" class="xx" >省份、地市</th>
				        	<th colspan="2" style="text-align:center;width:30px" class="xx">住宿费标准</th>
				        	<th rowspan="2" style="text-align:center;" >旺季期间（月份）</th>
				        	<th colspan="2" style="text-align:center;width:30px">淡旺季浮动标准建议</th>
				        	<th rowspan="2" style="text-align:center;width: 30px" >上浮比例%</th>
				        	<th rowspan="2" style="text-align:center;width: 30px" >伙食补助费标准</th>
				        	<th rowspan="2" style="text-align:center;width: 30px" >市内交通补助标准</th>
				        </tr>
				        <tr>
				        	<th style="text-align:left;" >厅局级</th>
				        	<th style="text-align:left;" >其他人员</th>
				        	<th style="text-align:left;">厅局级</th> 
 				        	<th style="text-align:left;">其他人员</th> 
				        </tr>
					</thead>
				    <tbody>
				    	<tr>
				        	<td style="text-align:center;" ><input name="city" value="济南市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="number aa" name="zsbz1" value="${map1.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" class="number bb" value="${map1.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj10" value="${map1.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map1.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map1.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input class="number cc" name="sfbl1" value="${map1.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="bz" value="${map1.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map1.JTBZ }"> </td>
				        </tr>
				       
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="青岛市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class=" number aa" name="zsbz1" value="${map2.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" class="number bb" value="${map2.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj11" value="${map2.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map2.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map2.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input class="number cc" name="sfbl1" value="${map2.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="bz" value="${map2.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map2.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="淄博市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map3.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map3.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj12" value="${map3.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map3.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map3.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="sfbl1" value="${map3.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="bz" value="${map3.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map3.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="枣庄市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map4.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map4.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj13" value="${map4.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map4.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map4.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="sfbl1" value="${map4.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="bz" value="${map4.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map4.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="东营市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map5.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map5.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj14" value="${map5.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map5.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map5.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="sfbl1" value="${map5.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="bz" value="${map5.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map5.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="烟台市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map6.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map6.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj15" value="${map6.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map6.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map6.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map6.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map6.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map6.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="潍坊市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map7.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map7.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj16" value="${map7.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map7.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map7.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map7.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map7.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map7.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="济宁市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map8.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map8.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj17" value="${map8.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map8.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map8.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map8.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map8.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map8.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="泰安市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map9.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map9.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj18" value="${map9.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map9.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map9.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map9.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map9.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map9.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="威海市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map10.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map10.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj19" value="${map10.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map10.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map10.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map10.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map10.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map10.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="日照市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map11.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map11.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj121" value="${map11.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map11.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map11.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map11.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map11.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map11.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="莱芜市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map12.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map12.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj122" value="${map12.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map12.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map12.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map12.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map12.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map12.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="临沂市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map13.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map13.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj123" value="${map13.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map13.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map13.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map13.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map13.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map13.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="德州市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map14.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map14.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj124" value="${map14.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map14.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map14.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map14.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map14.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map14.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="聊城市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map15.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map15.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj125" value="${map15.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map15.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map15.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map15.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map15.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map15.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="滨州市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map16.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map16.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj126" value="${map16.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map16.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map16.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map16.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map16.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map16.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="菏泽市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map17.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map17.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj127" value="${map17.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map17.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map17.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map17.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map17.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map17.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="北京市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map18.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map18.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj128" value="${map18.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map18.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map18.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map18.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map18.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map18.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="天津市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map19.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map19.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj129" value="${map19.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map19.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map19.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map19.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map19.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map19.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="河北省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map20.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map20.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj130" value="${map20.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map20.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map20.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map20.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map20.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map20.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="山西省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map21.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map21.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj131" value="${map21.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map21.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map21.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map21.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map21.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map21.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="内蒙古自治区" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map22.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map22.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj132" value="${map22.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map22.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map22.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map22.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map22.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map22.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="辽宁省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map23.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map23.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj133" value="${map23.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map23.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map23.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map23.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map23.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map23.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="大连市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map24.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map24.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj134" value="${map24.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map24.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map24.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map24.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map24.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map24.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="吉林省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map25.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map25.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj135" value="${map25.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map25.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map25.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map25.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map25.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map25.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="黑龙江省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map26.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map26.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj136" value="${map26.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map26.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map26.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map26.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map26.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map26.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="上海市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map27.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map27.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj137" value="${map27.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map27.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map27.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map27.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map27.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map27.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="江苏省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map28.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map28.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj138" value="${map28.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map28.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map28.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map28.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map28.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map28.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="浙江省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map29.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map29.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj139" value="${map29.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map29.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map29.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map29.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map29.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map29.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="宁波市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map30.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map30.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj140" value="${map30.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map30.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map30.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map30.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map30.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map30.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="安徽省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map31.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map31.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj141" value="${map31.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map31.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map31.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map31.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map31.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map31.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="福建省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map32.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map32.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj142" value="${map32.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map32.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map32.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map32.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map32.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map32.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="厦门市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map33.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map33.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj143" value="${map33.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map33.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map33.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map33.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map33.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map33.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="江西省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map34.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map34.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj144" value="${map34.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map34.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map34.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map34.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map34.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map34.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="河南省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map35.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map35.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj145" value="${map35.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map35.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map35.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map35.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map35.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map35.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="湖北省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map36.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map36.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj146" value="${map36.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map36.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map36.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map36.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map36.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map36.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="湖南省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map37.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map37.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj147" value="${map37.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map37.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map37.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map37.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map37.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map37.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="广东省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map38.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map38.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj148" value="${map38.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map38.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map38.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map38.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map38.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map38.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="深圳市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map39.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map39.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj149" value="${map39.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map39.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map39.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map39.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map39.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map39.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="广西壮族自治区" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map40.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map40.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj150" value="${map40.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map40.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map40.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map40.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map40.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map40.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="海南省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map41.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map41.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj151" value="${map41.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map41.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map41.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map41.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map41.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map41.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="重庆市" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map42.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map42.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj152" value="${map42.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map42.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map42.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map42.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map42.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map42.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="四川省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map43.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map43.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj153" value="${map43.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map43.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map43.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map43.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map43.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map43.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="贵州省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map44.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map44.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj154" value="${map44.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map44.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map44.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map44.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map44.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map44.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="云南省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map45.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map45.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj155" value="${map45.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map45.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map45.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map45.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map45.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map45.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="西藏自治区" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map46.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map46.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj156" value="${map46.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map46.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map46.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map46.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map46.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map46.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="陕西省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map47.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map47.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj157" value="${map47.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map47.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map47.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map47.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map47.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map47.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="甘肃省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map48.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map48.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj158" value="${map48.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map48.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map48.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map48.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map48.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map48.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="青海省" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map49.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map49.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj159" value="${map49.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map49.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map49.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map49.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map49.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map49.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="宁夏回族自治区" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map50.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map50.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj160" value="${map50.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map50.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map50.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map50.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map50.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map50.JTBZ }"> </td>
				        </tr>
				        <tr>
				        	<td style="text-align:center;" ><input name="city" value="新疆维吾尔自治区" readonly style="text-align:center"></td>
				        	<td style="text-align:left;" ><input class="aa" name="zsbz1" value="${map51.ZSBZ1 }"></td>
				        	<td style="text-align:right;" ><input name="zsbz2" value="${map51.ZSBZ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj1" id="wj161" value="${map51.WJ1 }"> </td>
				        	<td style="text-align:right;" ><input name="wj2" value="${map51.WJ2 }"> </td>
				        	<td style="text-align:right;" ><input name="wj3" value="${map51.WJ3 }"> </td>
				        	<td style="text-align:right;" ><input name="sfbl1" class="number" value="${map51.SFBL1 }"> </td>
				        	<td style="text-align:right;" ><input name="bz" class="number" value="${map51.BZ }"> </td>
				        	<td style="text-align:right;" ><input class="number" name="jtbz" value="${map51.JTBZ }"> </td>
				        </tr>
				    </tbody>
				</table>
			</div>	
			</div>
		</div>
	</form>
	</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {	
	//列表右侧悬浮按钮
// 	$(window).resize(function(){
//     	$("div.dataTables_wrapper").width($("#searchBox").width());
//     	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
//     	$(".dataTables_scrollBody").height(heights);
//     	table.draw();
// 	});
	//导出按钮
// 	$("#btn_exp").click(function(e) {
// 		alert("导出成功！");
// 	});
	/* $("#myform input").val("N/A");
	$("#myform input").click(function(){
		$(this).val("");
	}); */
// 	$("#myform input").blur(function(){
// 		if($(this).val()==""){
// 			$(this).val("N/A");
// 		}
// 	});
/* 	$(".aa").blur(function(){
		var aa = $(".aa").val();
		var cc = $(".cc").val();
		if(aa!=""&&cc!=""){
			var dd = aa*(1+cc);
			$(".bb").val(dd);
		}
	});
	$(".cc").blur(function(){
		var aa = $(".aa").val();
		var cc = $(".cc").val();
		if(aa!=""&&cc!=""){
			var dd = aa*(1+cc);
			$(".bb").val(dd);
		}
	}); */
	$("[name='zsbz1']").addClass("zsbz1");
	$("[name='zsbz2']").addClass("zsbz2");
	$("[name='wj2']").addClass("wj2");
	$("[name='wj3']").addClass("wj3");
	$("[name='sfbl1']").addClass("sfbl1");
	 $(".zsbz1,.sfbl1").change(function(){
		var zsbz1 = $(this).parents("tr").find(".zsbz1").val();
		var sfbl1 = $(this).parents("tr").find(".sfbl1").val();
		var fdbz = zsbz1*(1+sfbl1/100);
		$(this).parents("tr").find(".wj2").val(fdbz.toFixed(2));
	}); 
	 $(".zsbz2,.sfbl1").change(function(){
		var wj2 = $(this).parents("tr").find(".zsbz2").val();
		var sfbl1 = $(this).parents("tr").find(".sfbl1").val();
		var fdbz = wj2*(1+sfbl1/100);
		$(this).parents("tr").find(".wj3").val(fdbz.toFixed(2));
	}); 
	$("[name='zsbz1']").addClass("number");
	$("[name='zsbz2']").addClass("number");
	$("[name='wj1']").addClass("wj1");
	$("[name='wj2']").addClass("number");
	$("[name='wj3']").addClass("number");
	
	$(".wj1").click(function(){
		var id = $(this).attr("id");
// 		var fyid = $(this).parents("td").find("[id^=txt_fyid]").attr("id");
		select_commonWin("/CWYTH/webView/system/clbx/month.jsp?controlId="+id,"月份选择","1000","650");
	});
	//验证方法
	var validate = $('#mysave').bootstrapValidator({fields:{ 
        }
	      });
	//保存按钮
	$("#btn_save").click(function(){
		doSave(validate,"mysave","${ctx}/xxxxwh/doclbxSave",function(val){
		});
	});
});
</script>
</body>
</html>