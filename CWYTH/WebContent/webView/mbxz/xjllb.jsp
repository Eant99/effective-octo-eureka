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
thead th{
	text-align:center;
}
input .form-input{
	border:none;
	wisth:100%;
}
	#rr{
		text-align:right;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<%-- <div class="form-group">
	               <label>选择日期</label>
	               <input  type="text" class="input_info years form-control" style="border:1px solid #ccc;"  value="${year}"/>
	               <i class="glyphicon glyphicon-calendar"></i> 
					&nbsp;
				   <label>是否包含未记账凭证&nbsp; </label>
				   <select style="width:60px;border:1px solid #ccc;" id="jzpz" class="form-control select">			
				   		<option value="0" <c:if test="${jzpz=='0'}">selected</c:if>>否</option>		
						<option value="1" <c:if test="${jzpz=='1'}">selected</c:if>>是</option>
			      </select>
				</div> --%>
				<div class="btn-group pull-right" role="group">
<!-- 					<button type='button' class="btn btn-default" id="btn_save">保存</button> -->
<!-- 					<button type='button' class="btn btn-default" id="btn_print">打印预览</button> -->
<!-- 					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button> -->
						<button type="button" class="btn btn-default" id="btn_back">返回</button>
				</div>
			</div>
		</form>
	</div>
<form id="mysave" method="post" >
<div class="container-fluid" >
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:82%;margin:0 auto">
					<h4 style="text-align:center;">现金流量表</h4>
					<h6 style="margin-left:49.3%;">${year}年</h6>
					<caption style="text-align:right;">
						会政财务04表
					<caption style="text-align:left;">
						编制单位：${bzdw.XXMC}
					<div style="float:right;margin-right:2%;">
						单位:元
					</div>
					</caption>
		        	<thead>
				        <tr>
			            	<th style="text-align:center;">项目</th>
				            <th style="text-align:center;width:15%;">本年金额</th>	
				            <th style="text-align:center;width:15%;">上年金额</th>
				        </tr>
					</thead>
				   
				     <tbody>
				     
				 		<tr class="tr_1">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				<b>一、日常活动产生的现金流量：</b>

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="<b>一、日常活动产生的现金流量：</b>">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="rc">
				 				<input type="hidden" name="hc" value="1">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="rc" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="rc" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_2">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    财政基本支出拨款收到的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    财政基本支出拨款收到的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="1">
				 				<input type="hidden" name="hc" value="2">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="1" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="1" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_3">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    财政非资本性项目拨款收到的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    财政非资本性项目拨款收到的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="1">
				 				<input type="hidden" name="hc" value="3">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="1" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="1" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_4 selected">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    事业活动收到的除财政拨款的以外现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    事业活动收到的除财政拨款的以外现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="1">
				 				<input type="hidden" name="hc" value="4">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="1" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="1" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_5">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    收到的其他与日常活动有关的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    收到的其他与日常活动有关的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="1">
				 				<input type="hidden" name="hc" value="5">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="1" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="1" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_6">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    日常活动的现金流入小计

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    日常活动的现金流入小计">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="lrxj">
				 				<input type="hidden" name="hc" value="6">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="lrxj" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="lrxj" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_7">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    购买商品、接受劳务支付的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    购买商品、接受劳务支付的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="2">
				 				<input type="hidden" name="hc" value="7">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="2" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="2" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_8">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    支付给职工以及为职工支付的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    支付给职工以及为职工支付的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="2">
				 				<input type="hidden" name="hc" value="8">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="2" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="2" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_9">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    支付的各项税费

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    支付的各项税费">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="2">
				 				<input type="hidden" name="hc" value="9">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="2" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="2" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_10">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    支付的其他与日常活动有关的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    支付的其他与日常活动有关的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="2">
				 				<input type="hidden" name="hc" value="10">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="2" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="2" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_11">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    日常活动的现金流出小计

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    日常活动的现金流出小计">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="lcxj">
				 				<input type="hidden" name="hc" value="11">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="lcxj" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="lcxj" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_12">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    日常活动产生的现金流量净额

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    日常活动产生的现金流量净额">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="rcje">
				 				<input type="hidden" name="hc" value="12">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="rcje" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="rcje" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_13">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				二、投资活动产生的现金流量:

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="二、投资活动产生的现金流量:">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="tz">
				 				<input type="hidden" name="hc" value="13">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="tz" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="tz" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_14">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    收回投资收到的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    收回投资收到的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="3">
				 				<input type="hidden" name="hc" value="14">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="3" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="3" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_15">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    取得投资收益收到的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    取得投资收益收到的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="3">
				 				<input type="hidden" name="hc" value="15">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="3" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="3" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_16">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    处置固定资产、无形资产、公共基础设施等收回的现金净额

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    处置固定资产、无形资产、公共基础设施等收回的现金净额">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="3">
				 				<input type="hidden" name="hc" value="16">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="3" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="3" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_17">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    收到的其他与投资活动有关的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    收到的其他与投资活动有关的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="3">
				 				<input type="hidden" name="hc" value="17">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="3" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="3" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_18">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    投资活动的现金流入小计

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    投资活动的现金流入小计">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="trxj">
				 				<input type="hidden" name="hc" value="18">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="trxj" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="trxj" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_19">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    购建固定资产、无形资产、公共基础设施等支付的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    购建固定资产、无形资产、公共基础设施等支付的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="4">
				 				<input type="hidden" name="hc" value="19">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="4" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="4" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_20">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    对外投资支付的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    对外投资支付的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="4">
				 				<input type="hidden" name="hc" value="20">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="4" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="4" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_21">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    上缴处置固定资产、无形资产、公共基础设施等净收入支付的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    上缴处置固定资产、无形资产、公共基础设施等净收入支付的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="4">
				 				<input type="hidden" name="hc" value="21">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="4" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="4" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_22">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    支付的其他与投资活动有关的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    支付的其他与投资活动有关的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="4">
				 				<input type="hidden" name="hc" value="22">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="4" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="4" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_23">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    投资活动的现金流出小计

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    投资活动的现金流出小计">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="tcxj">
				 				<input type="hidden" name="hc" value="23">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="tcxj" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="tcxj" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_24">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    投资活动产生的现金流量净额

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    投资活动产生的现金流量净额">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="tcje">
				 				<input type="hidden" name="hc" value="24">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="tcje" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="tcje" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_25">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				三、筹资活动产生的现金流量

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="三、筹资活动产生的现金流量">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="cz">
				 				<input type="hidden" name="hc" value="25">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="cz" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="cz" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_26">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    财政资本性项目拨款收到的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    财政资本性项目拨款收到的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="5">
				 				<input type="hidden" name="hc" value="26">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="5" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="5" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_27">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    取得借款收到的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    取得借款收到的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="5">
				 				<input type="hidden" name="hc" value="27">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="5" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="5" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_28">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    收到的其他与筹资活动有关的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    收到的其他与筹资活动有关的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="5">
				 				<input type="hidden" name="hc" value="28">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="5" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="5" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_29">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    筹资活动的现金流入小计

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    筹资活动的现金流入小计">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="crxj">
				 				<input type="hidden" name="hc" value="29">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="crxj" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="crxj" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_30">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    偿还借款支付的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    偿还借款支付的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="6">
				 				<input type="hidden" name="hc" value="30">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="6" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="6" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_31">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    偿还利息支付的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    偿还利息支付的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="6">
				 				<input type="hidden" name="hc" value="31">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="6" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="6" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_32">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    支付的其他与筹资活动有关的现金

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    支付的其他与筹资活动有关的现金">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="6">
				 				<input type="hidden" name="hc" value="32">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="6" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="6" class="form-control num " value="132132.00">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_33">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    筹资活动的现金流出小计

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    筹资活动的现金流出小计">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="ccxj">
				 				<input type="hidden" name="hc" value="33">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="ccxj" class="form-control num " value="0">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="ccxj" class="form-control num " value="0">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_34">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				    筹资活动产生的现金流量净额

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="    筹资活动产生的现金流量净额">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="ccje">
				 				<input type="hidden" name="hc" value="34">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="ccje" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="ccje" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_35">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				四、汇率变动对现金的影响额

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="四、汇率变动对现金的影响额">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="hl">
				 				<input type="hidden" name="hc" value="35">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="hl" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="hl" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
				 		</tr>
				 	
				 		<tr class="tr_36">
				 			<td>
				 				<input type="hidden" name="czr" value="D728AAACD5914AAEB578B980D86073B8">
				 				五、现金净增加额

				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="xmmc" value="五、现金净增加额">
				 				<input type="hidden" id="bb" name="ny" value="2018">
				 				<input type="hidden" name="bzdw" value="001">
				 				<input type="hidden" name="jzpz" value="0">
				 				<input type="hidden" name="ztbh" value="B9BA12A24DBE4EA89763AFDE76B8C61A">
				 				<input type="hidden" name="tag" value="zj">
				 				<input type="hidden" name="hc" value="36">
				 			</td>
				 			<td class="num" role="bnje">

				 				<input type="text" id="rr" name="bnje" tag="zj" class="form-control num " value="">

				 			</td>
				 			<td class="num" role="snje">

				 				<input type="text" id="rr" name="snje" tag="zj" class="form-control num " value="">

				 				<input type="hidden" name="bblx" value="1">
				 			</td>
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
$(function () {
	$(document).on("focus", ".years", function(){
	    $(this).on("click", function() {
	    	WdatePicker({
	    		dateFmt:'yyyy',
	    		onpicked:function(){
	    			var val = $(this).val();
	    			var jzpz = $("#jzpz").val();
	    			location.href="${ctx}/xjllb/toUrl?year="+val+"&jzpz="+jzpz;
	    		},
	    		onclearing:function(){
	    			alert("请选择年度!");
	    			return;
	    		}
	    	});
	    });
	    $(this).on("keypress", function() {
	        if (/[^0-9]/.test(String.fromCharCode(event.keyCode)))
	            event.keyCode = 0;
	    });
	    $(this).on("dragenter", function() {
	        return false;
	    });
	    $(this).on("paste", function() {
	        return false;
	    });
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
//记账凭证条件切换

	$("#jzpz").change(function(){
		var jzpz = $("#jzpz").val();
		var val = $(".years").val();
// 		alert(jzpz);
		location.href="${ctx}/xjllb/toUrl?year="+val+"&jzpz="+jzpz;
// 	});
});
//点击保存按钮
$("#btn_save").click(function(){
	var json = $("#mysave").serializeObject("czr","bblx");  //json对象				
	var jsonStr = JSON.stringify(json);
// 	alert("jsonStr"+jsonStr);
	$.ajax({
		url:"${ctx}/xjllb/doSave",
		data:"jsonStr="+jsonStr,
		dataType:"json",
		type:"post",
		success:function(msg){
			if($.trim(msg)=="true"){
				alert("保存成功!");
			}else{
				alert("保存失败!");
			}
		}
	});
});
//点击导出
// $("#btn_exp").click(function(){
// 	var ss =  $("#bb").val();
// 	doExp("","${ctx}/xjllb/expExcel?abc="+ss,"现金流向表","${ctx}","");
// });

//导出Excel
$("#btn_exp").click(function() {
		var ss =  $("#bb").val();
		var jzpz = $("#jzpz").val();
		var val = $(".years").val();
		var ny = $("[name='ny']").val();
		$.ajax({
				type : "post",
				data : {abc:ss,jzpz:jzpz,year:val,ny:ny},
				url : "${ctx}/xjllb/expExcel2",
				success : function(val) {
				   FileDownload("${ctx}/file/fileDownload","现金流向表.xls",val.url);
				}
			});
	});
//返回按钮
$("#btn_back")
		.click(
				function(e) {
					doOperate("${ctx}/mbxz/mbxz_list");
					return false;
				});
//点击打印
$("#btn_print").click(function(){
	var jzpz = $("#jzpz").val();
	var val = $(".years").val();
	location.href = "${ctx}/xjllb/goPrint?year="+val+"&jzpz="+jzpz;
});

//日常活动的现金流入小计
$(document).on("keyup","[tag='1']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='1']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='lrxj']").val(com);
	computer(dual);
});

//一、日常活动产生的现金流量：
$(document).on("keyup","[tag='2']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='2']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='lcxj']").val(com);
	computer(dual);
});



//二、投资活动产生的现金流量:
$(document).on("keyup","[tag='3']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='3']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='trxj']").val(com);
	computer(dual);
});


//投资活动的现金流入小计
$(document).on("keyup","[tag='4']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='4']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='tcxj']").val(com);
	computer(dual);
});




//三、筹资活动产生的现金流量
$(document).on("keyup","[tag='5']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='5']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='crxj']").val(com);
	computer(dual);
});




//筹资活动的现金流入小计
$(document).on("keyup","[tag='6']",function(){
	var role = $(this).parents("td").attr("role");
	var dual = $("[role='"+role+"']");
	var com = 0;
	var val = $(this).val().replace(",","");
	if(val==""||val==0||val==0.00||isNaN(val)){
		val = "";
		return;
	}
	$.each(dual.find("[tag='6']"),function(i,v){
		var money = $(this).val().replace(",","");
		console.log(money);
		if(money==""||money==0||money==0.00||isNaN(money)){
			money = 0;
		}
		com = Number(com)+Number(money);
		console.log(com);
	});
	if(com==""||isNaN(com)){
		com = 0;
	}
	dual.find("[tag='ccxj']").val(com);
	computer(dual);
});




function computer(dual){
	var lr = dual.find("[tag='lrxj']").val().replace(",","");
	var lc = dual.find("[tag='lcxj']").val().replace(",","");;
	var zl = Number(lr)-Number(lc);
	dual.find("[tag='rc']").val(zl);
}


function computer(dual){
	var lr = dual.find("[tag='lrxj']").val().replace(",","");
	var lc = dual.find("[tag='lcxj']").val().replace(",","");;
	var zl = Number(lr)-Number(lc);
	dual.find("[tag='rc']").val(zl);
}

</script>
</body>
</html>