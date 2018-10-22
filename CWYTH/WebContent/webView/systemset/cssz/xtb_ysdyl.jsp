<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>打印格式预览</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<style type="text/css">
	td { font-SIZE: 12px; }
	.yshd td{
	    height:8mm;
	   }
	.kp td{
		height:7mm;
	}
	.tabW
	{
		width:185mm;
		margin:0;
		padding:0;
	}
	.tabH
	{
		height:135mm;
		margin:0;
		padding:0;
	}	
	
	.th {
		font-SIZE: 12px;
		font-weight:bold;
	}
	.tdp { 
		BORDER-RIGHT: #ffffff 0px solid; 
		BORDER-TOP: #ffffff 0px solid; 
		BORDER-LEFT: #000000 1px solid; 
		BORDER-BOTTOM: #000000 1px solid 
	}	
	
	/* 表外框加粗 */
	.tabp{ 
		BORDER-COLOR: #000000;
		BORDER-RIGHT: #000000 2px solid; 
		BORDER-TOP: #000000 2px solid; 
		BORDER-LEFT: #000000 2px solid; 
		BORDER-BOTTOM: #000000 2px solid ;
		BORDER-COLLAPSE: collapse; 
	}
	/* 表外框去掉样式 */
	.tabN
	{
		BORDER-STYLE: none; 
	}
	 /*  分页 */
	.PageNext { PAGE-BREAK-AFTER: always }
	.spanRed
	{
		color:red;
	}
	.tdW
	{
		width:20mm;
	}
</style>
</head>
<body>
	<center>
      	<form name="printContent" method="post" action="" id="printContent">
            <table class="tabW" cellspacing="0" cellpadding="0" border="0">
               <tr style="height: 10mm;">
                   <td  align="center" colspan="2">
                       <p><span id="" class=" spanRed" style="font-size:large; font-weight:bold;">中国城市大学&ensp;资产入库验收单</span>
                       <span style="font-family: C39HrP36DlTt;font-size: 30px;margin-left: 3mm;">*201700016*</span></p>
                   </td>
               </tr>
               <tr style="height: 8mm;">
                   <td align="left" style="width:70mm;">
                       <p style="margin-left: 1mm;"class="spanRed">单位名称（盖章）：(000001)中国城市大学</p>
                   </td>
                   <td style="width:110mm;" align="right">
                       <p style="margin-right: 1mm;">打印日期：2017年03月09日&emsp;&emsp;验收单编号：201700016</p>
                   </td>
               </tr>
            </table>
            <table class="tabW tabp yshd" cellspacing=0 cellpadding=2 border=1>
               <tr>
                   <td align="center" class="tdW spanRed">资产名称</td>
                   <td align="left" style="width: 50mm;" colspan="2" class="spanRed">静电复印制版机</td>
                   <td align="center"class="tdW">资产分类</td>
                   <td align="left" style="width: 50mm;" colspan="2">静电复印制版机</td>
		           <td align="center" class="tdW">国&emsp;&emsp;别</td>
		           <td align="left" style="">中国</td>
               </tr>
               <tr>
		           <td align="center">型&emsp;&emsp;号</td>
		           <td align="left" colspan="2"></td>
		           <td align="center">规&emsp;&emsp;格</td>
		           <td align="left" colspan="2"></td>
		           <td align="center">合同编号</td>
		           <td align="left">20170205</td>
               </tr>
               <tr>
		          <td align="center">生产厂家</td>
		          <td align="left" colspan="2"></td>
                  <td align="center">采购形式</td>
                  <td align="left" colspan="2">政府集中采购</td>
                  <td align="center">发&ensp;票&ensp;号</td>
                  <td align="left">55</td>
               </tr>
               <tr>
                   <td align="center" class="tdw spanRed">单&emsp;&emsp;价</td>
                   <td align="right" style="width:30mm" class=" spanRed">40.00</td>
                   <td align="center" class=" tdw spanRed">数量/单位</td>
                   <td align="left" class=" spanRed">2/套</td>
                   <td align="center" class="tdw spanRed">总&emsp;&emsp;价</td>
                   <td align="right" class=" spanRed">80.00</td>
                   <td align="center" class="tdw spanRed">经费来源</td>
                   <td align="left"class=" spanRed">教育事业费</td>
               </tr>
               <tr>
		           <td align="center">销&ensp;售&ensp;商</td>
		           <td align="left"></td>
                   <td align="center">采&ensp;购&ensp;人</td>
                   <td align="left">嘟嘟</td>
                   <td align="center">经&ensp;手&ensp;人</td>
                   <td align="left">超级管理员</td>
                   <td align="center">资产来源</td>
                   <td align="left">外购</td>
               </tr>
               <tr>
                   <td align="center">出厂日期</td>
                   <td align="left">2016-02-03</td>
                   <td align="center">购置日期</td>
                   <td align="left">2017-02-03</td>
                   <td align="center">货到日期</td>
                   <td align="left">2017-02-06</td>
                   <td align="center">验收日期</td>
                   <td align="left">2017-02-07</td>
               </tr>
               <tr>
					<td colspan="8" style="padding:0">
	               		<table class="tabN kp" style="width:100%" cellspacing=0 cellpadding=2 border=1>
			                <tr class="th">
			                    <td align="center">序号</td>
			                    <td align="center">资产编号</td>
			                    <td align="center">机身编号</td>
			                    <td align="center"class="spanRed">使&ensp;用&ensp;人</td>
			                    <td align="center"class="spanRed">使用单位</td>
			                    <td align="center"class="spanRed">存放地点</td>
			                </tr>
		               		<tr>
				                <td align="center">1</td>
				                <td align="left" >2017020096</td>
				                <td align="left"></td>
				                <td align="left"class="spanRed"></td>
				                <td align="left"class="spanRed"></td>
				                <td align="left"class="spanRed"></td>		
					        </tr>
					        <tr>
				                <td align="center">2</td>
				                <td align="left" >2017020097</td>
				                <td align="left"></td>
				                <td align="left"class="spanRed"></td>
				                <td align="left"class="spanRed"></td>
				                <td align="left"class="spanRed"></td>		
					        </tr>
	                	</table>
	                </td>             
				</tr>
           	</table>
       		<div class="tabW" style="height:1mm;border-bottom:#000000 1px dotted;"></div>
       		<table class="tabW" border="0px" cellpadding="5px" cellspacing="0px">
           		<tr style="height: 10mm;" class="th">
             		<td align="left" style="width:150px">经办人签名：</td>           
             		<td align="center">归口部门审核签名并盖章：</td>           
             		<td align="right" style="padding-right: 100px;">财务审核签名并盖章：</td>           
           		</tr>
           		<tr style="height: 10mm;"class="th">
             		<td align="left" colspan="3">资产管理员验收签名：</td>
           		</tr>
       		</table>
		</form>
	</center>
	<script type="text/javascript">
	</script>
</body>
</html>