<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>个人所得税超额累进税率表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	table{
		border-collapse:collapse!important;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		 border-bottom-width: 0!important;
	}
	.select2-selection{
		border-radius: 4px!important;
	}
/* 	thead th{ */
/* 		text-align:!important; */
/* 	} */
	.allDiv{
		width:100%;
		height:20px;
	}
	.first{
		float:left;
		text-align:left;
		width:33%;
	}
	.second{
		float:left;
		text-align:center;
		width:34%;
	}
	.third{
		float:left;
		text-align:right;
		width:33%;
	}
	.div_bottom{
    	width: 99%;
    	margin-top: 20px;
    	bottom: 20px;
   		background-color: #f3f3f3;
	}
	.bom{
		float:left;
		text-align:left;
		width:33%;
	}
	.save{
	 margin-right: 10%;
	}
	.tjbb_title{
	height: 40px;
    font-size: 18px;
    font-weight: bold;
    text-align: center;
	}
	.ynsde{
	  text-align: center;
	  width: 600px;
	}
	.sl{
	  width: 200px;
	}
	.sskcs{
	  width: 200px;
	}
	.text2{
	   width: 100%;
	   text-align: right;
	}
	.text1{
	  text-align: right;
	}
	th{
	 height: 40px;
	 
	}
/* 	.table{ */
/* 		width:50%!important; */
/* 	} */
</style>
</head>
<body>
<div class="fullscreen">
 	<div class="search" id="searchBox" style="padding-top: 0px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="page-header">
				<div class="pull-left">
		            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑个人所得税超额累进税率信息</span>
				</div>
				<div class="pull-right save">
					<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
		        </div>
			</div>
		</form>
	</div>
	<div style="margin-top: 2%;">
	 <form id="myform2">
		<div class="container-fluid" style="width: 82%">	
			  <table style="width: 100%">
			     <tr>
			        <td colspan="3" class="tjbb_title">个人所得税超额累进税率表</td>
			     </tr>
			     <tr></tr>
			  </table>
	
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area' > 
				<table id="mydatatables" class="table table-striped table-bordered" style="width: 82%;margin:auto">
		        	<thead>
		        	   <tr>
		        	       <th style="text-align: center;">全月应纳税所得额</th>
		        	       <th style="text-align: center;">税率(%)</th>
		        	       <th style="text-align: center;">速算扣除数(元)</th>
		        	   </tr>
				        <tr>
				           <td class="ynsde">全月应纳税额不超过&ensp;<input type="hidden" name="qyynsjc" value="1"><input type="hidden" class="text1" name="qyynsbzl" value="${map1.QYYNSBZL }"><input type="text" class="text1" name="qyynsbzh" value="${map1.QYYNSBZH }">元 </td>
				           <td class="sl"><input type="text" class="text2" name="sl" value="${map1.SL }"></td>
				           <td class="sskcs"><input type="text" class="text2" name="sskcs" value="${map1.SSKCS }"></td>
				        </tr>
				         <tr>
				           <td class="ynsde">全月应纳税额超过&ensp;<input type="hidden" name="qyynsjc" value="2"><input type="text" class="text1" name="qyynsbzl" value="${map2.QYYNSBZL }">至<input type="text" class="text1" name="qyynsbzh" value="${map2.QYYNSBZH }">元 </td>
				           <td class="sl"><input type="text" class="text2" name="sl" value="${map2.SL }"></td>
				           <td class="sskcs"><input type="text" class="text2" name="sskcs" value="${map2.SSKCS }"></td>
				        </tr>
				         <tr>
				           <td class="ynsde">全月应纳税额超过&ensp;<input type="hidden" name="qyynsjc" value="3"><input type="text" class="text1" name="qyynsbzl" value="${map3.QYYNSBZL }">至<input type="text" class="text1" name="qyynsbzh" value="${map3.QYYNSBZH }">元 </td>
				           <td class="sl"><input type="text" class="text2" name="sl" value="${map3.SL }"></td>
				           <td class="sskcs"><input type="text" class="text2" name="sskcs" value="${map3.SSKCS }"></td>
				        </tr>
				         <tr>
				           <td class="ynsde">全月应纳税额超过&ensp;<input type="hidden" name="qyynsjc" value="4"><input type="text" class="text1" name="qyynsbzl" value="${map4.QYYNSBZL }">至<input type="text" class="text1" name="qyynsbzh" value="${map4.QYYNSBZH }">元 </td>
				           <td class="sl"><input type="text" class="text2" name="sl" value="${map4.SL }"></td>
				           <td class="sskcs"><input type="text" class="text2" name="sskcs" value="${map4.SSKCS }"></td>
				        </tr> 				       
				        <tr>
				           <td class="ynsde">全月应纳税额超过&ensp;<input type="hidden" name="qyynsjc" value="5"><input type="text" class="text1" name="qyynsbzl" value="${map5.QYYNSBZL }">至<input type="text" class="text1" name="qyynsbzh" value="${map5.QYYNSBZH }">元 </td>
				           <td class="sl"><input type="text" class="text2" name="sl" value="${map5.SL }"></td>
				           <td class="sskcs"><input type="text" class="text2" name="sskcs" value="${map5.SSKCS }"></td>
				        </tr>
				        <tr>
				           <td class="ynsde">全月应纳税额超过&ensp;<input type="hidden" name="qyynsjc" value="6"><input type="text" class="text1" name="qyynsbzl" value="${map6.QYYNSBZL }">至<input type="text" class="text1" name="qyynsbzh" value="${map6.QYYNSBZH }">元 </td>
				           <td class="sl"><input type="text" class="text2" name="sl" value="${map6.SL }"></td>
				           <td class="sskcs"><input type="text" class="text2" name="sskcs" value="${map6.SSKCS }"></td>
				        </tr>
				        <tr>
				           <td class="ynsde">全月应纳税额超过&ensp;<input type="hidden" name="qyynsjc" value="7"><input type="text" class="text1" name="qyynsbzl" value="${map7.QYYNSBZL }"><input type="hidden" class="text1" name="qyynsbzh" value="${map7.QYYNSBZH }">元 </td>
				           <td class="sl"><input type="text" class="text2" name="sl" value="${map7.SL }"></td>
				           <td class="sskcs"><input type="text" class="text2" name="sskcs" value="${map7.SSKCS }"></td>
				        </tr>
					</thead>
				    <tbody>

				    </tbody>
				</table>
			</div>	
			</div>
		</div>
	</form>
	</div>
</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {	

	
	//保存按钮
	$("#btn_save").click(function(){
		doSave("","myform2","${ctx}/grsdscelj/dogrsdsSave",function(val){
			window.location.reload();
		});
	});
});
</script>
</body>
</html>