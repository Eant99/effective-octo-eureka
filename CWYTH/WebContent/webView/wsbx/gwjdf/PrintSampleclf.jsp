<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<!-- 安徽财贸职业学院差旅费报销单 -->
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
	margin-top: 10px;
	}
	.bottom1 tr td{
	width: 400px !important;
	}
	tr td{
	height: 30px;
	text-align: center;
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
   .bottom1{
  position: absolute;
   }
 #tbodyy .bottom1 div{
   float: left;list-style-type:none;margin-left: 80px;
   }
</style>
</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>
<a href="${ctx}/zkylbx/goListPage">返回</a>

	<div id="div1">
	
	</div>
	
<div id="div2">
<%-- <c:forEach var="printinfolist" items="${printinfolist}"> --%>
<h2 id="div1" style="text-align: center;">主&nbsp;持&nbsp;课&nbsp;题&nbsp;项&nbsp;目&nbsp;经&nbsp;费&nbsp;报&nbsp;销（结&nbsp;算）表</h2>
<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
  	<TBODY>      
	  <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;"><b>项目名称</b></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.xmmc1}</TD>
	    <TD style="height:70px;width:180px;text-align: center;"><b>项目主管部门</b></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.xmzgbm}</TD>
	  </tr>
	   <tr>
	    <TD style="height:70px;width:20%;text-align: center;"><b>主持人</b></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">
	    	<span>
	    	${printinfolist.zcr}
		  	</span>
	    </TD>
	    <TD style="height:70px;width:180px;text-align: center;"><b>项目类别</b></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.lx}</TD>
	    
	  </tr>
	   <tr>
	    <TD style="height:70px;width:20%;text-align: center;"><b>课题起止日期</b></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.kssj}-${printinfolist.jssj}</TD>
	    <TD style="height:70px;width:180px;text-align: center;"><b>课题编号</b></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.xmbh}</TD>
	  </tr>
	  <tr>
	    <TD style="height:70px;width:20%;text-align: center;"><b>经费资助总额（元）</b></TD>
	    <TD style="height:40px;width:180px;text-align: center;">${printinfolist.ysje}</TD>
	    <TD style="height:70px;width:180px;text-align: center;"><b>前期已预付数额（元）</b></TD>
	    <TD style="height:70px;width:180px;text-align: center;">${printinfolist.ysje - printinfolist.syje}</TD>
	    <TD style="height:70px;width:180px;text-align: center;"><b>现使用数额（元）</b></TD>
	    <TD style="height:70px;width:180px;text-align: center;">${printinfolist.BXZJE}</TD>
	  </tr>
	  <tr>
	  	<td colspan="6" style="text-align: center;"><b>本次实际支出明细</b></td>
	  </tr>
	  <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;"><b>序号</b></TD>
	    <TD colspan="3" style="padding-left: 8px;text-align: center;"><b>研究经费实际开支内容</b></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;"><b>金额（元）</b></TD>
	  </tr>
<%-- 	  <c:forEach var="printinfolist" items="${printinfolist}"> --%>
	   <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;"><b>1</b></TD>
	    <TD colspan="3" style="padding-left: 8px;text-align: center;"><b>差旅费报销</b></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.BXZJE}</TD>
	  </tr>
	  <tr>
	    <TD colspan="4" style="padding-left: 8px;text-align: center;"><b>合&emsp;&emsp;&emsp;&emsp;&emsp;计</b></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.BXZJE}</TD>
	  </tr>
	   <tr>
	    <TD colspan="6" style="padding-left: 8px;height:70px;width:20%;text-align: center;"><b>本次报销项目经费合计人民币（大写）：</b><span id="hj"></span><b>,妥否，请批示</b>
	    <br><b>项目主持人：</b>
	    	<span>
	    	${xmfzr}
		  	</span><br>
	    </TD>
	  </tr>
	  <tr>
	    <TD colspan="1" rowspan="3" style="height:70px;width:20%;text-align: center;border-bottom:transparent;"><b>科研处意见</b></TD>
	    <TD colspan="1" style="padding-left: 8px;text-align:left;border-bottom:transparent;">${kycymap.shyj}</TD>
	    <TD colspan="1" rowspan="3" style="height:50px;width:180px;text-align: center;border-bottom:transparent;"><b>财务处意见</b></TD>
	    <TD colspan="1" style="height:30px;width:20%;text-align:left;border-bottom:transparent;">${cwdMap.shyj}</TD>
	    <TD colspan="1" rowspan="3" style="padding-left: 8px;text-align: center;border-bottom:transparent;"><b>院领导意见</b></TD>
	    <TD colspan="1" style="height:30px;width:180px;text-align:left;border-bottom:transparent;">${yxMap.shyj}</TD>
	  </tr>
	  <tr>
	    <TD colspan="1" style="padding-left: 8px;text-align: center;border-top:transparent;border-bottom:transparent;">
	    	<span><c:if test="${not empty fjView1}"><img src="${fjView1}" style="width: 90px;height: 50px;text-align: center;"></c:if>
		  		  <c:if test="${empty fjView1}">${kyc}</c:if>
		  	</span>
	    </TD>
	    <TD colspan="1" style="height:30px;width:20%;text-align: center;border-top:transparent;border-bottom:transparent;">
	    	<span><c:if test="${not empty fjView4}"><img src="${fjView4}" style="width: 90px;height: 50px;text-align: center;"></c:if>
		  		  <c:if test="${empty fjView4}">${cw}</c:if>
		  	</span>
	    </TD>
	    <TD colspan="1" style="height:30px;width:180px;text-align: center;border-top:transparent;border-bottom:transparent;">
	    	<span><c:if test="${not empty fjView}"><img src="${fjView}" style="width: 90px;height: 50px;text-align: center;"></c:if>
		  		  <c:if test="${empty fjView}">${yzxm}</c:if>
		  	</span>
	    </TD>
	  </tr>
	  <tr>
<!-- 	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">科研处意见</TD> -->
	    <TD colspan="1" style="padding-left: 8px;text-align:right;border-top:transparent;">
	    	<fmt:formatDate value="${kycyjmap.jdsj}" type="date" pattern="yyyy-MM-dd"/>
	    </TD>
<!-- 	    <TD colspan="1" style="height:70px;width:180px;text-align: center;">财务处意见</TD> -->
	    <TD colspan="1" style="height:30px;width:20%;text-align:right;border-top:transparent;">
	    	<fmt:formatDate value="${cwdMap.jdsj}"  type="date"  pattern="yyyy-MM-dd"/>
	    </TD>
<!-- 	    <TD colspan="1" style="padding-left: 8px;text-align: center;">院领导意见</TD> -->
	    <TD colspan="1" style="height:30px;width:180px;text-align:right;border-top:transparent;">
	    	<fmt:formatDate value="${yxMap.jdsj}"  type="date"  pattern="yyyy-MM-dd"/>
	   </TD>
	  </tr>
	 
	</TBODY>
	
	
</TABLE>
<div style="text-align: right; margin-right:5%;"><span>打印日期：${time}</span></div>
<%-- </c:forEach> --%>
</div>
<div id="div3">

<div style="page-break-after(before)">
	<h2 id="" style="text-align: center;">安&nbsp;徽&nbsp;财&nbsp;贸&nbsp;职&nbsp;业&nbsp;学&nbsp;院&nbsp;报&nbsp;销&nbsp;明&nbsp;细&nbsp;封&nbsp;面</h2>
<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
  	<TBODY>      
	  <tr>
	    <TD colspan="1" rowspan="2" style="height:70px;width:20%;text-align: center;"><b>报销人</b></TD>
	    <TD colspan="4" style="height:70px;width:100px;text-align: center;"><b>光大公务卡</b></TD>
	    <TD colspan="2" style="height:70px;width:100px;text-align: center;"><b>建行卡（劳务费，差旅费补贴等）</b></TD>
	    <TD colspan="1" rowspan="2"  style="height:70px;width:100px;text-align: center;"><b>备注</b></TD>
	  </tr>
	   <tr>
	    <TD style="height:70px;width:100px%;text-align: center;"><b>刷卡日期</b></TD>
	    <TD colspan="1" style="height:70px;width:100px;text-align: center;"><b>刷卡金额</b></TD>
	    <TD style="height:70px;width:100px;text-align: center;"><b>报销金额</b></TD>
	    <TD colspan="1" style="height:70px;width:100px;text-align: center;"><b>卡号（后四位）</b></TD>
	    <TD style="height:70px;width:100px;text-align: center;"><b>卡号</b></TD>
	    <TD colspan="1" style="height:70px;width:100px;text-align: center;"><b>金额</b></TD>
	  </tr>
	  <c:forEach var="list22" items="${list22}">
	  <tr>
	    <TD style="height:40px;width:180px;text-align: center;">${list22.xm}</TD>
	    <TD style="height:40px;width:180px;text-align: center;"><fmt:formatDate value="${list22.skrq}"/></TD>
	    <TD style="height:40px;width:180px;text-align: center;"><fmt:formatNumber value="${list22.skje}" pattern=".00"></fmt:formatNumber></TD>
	    <TD style="height:40px;width:180px;text-align: center;">${list22.ysje}</TD>
	    <TD style="height:40px;width:180px;text-align: center;">${list22.kh}</TD>
	    <TD style="height:40px;width:180px;text-align: center;">${list22.DFZH}</TD>
	    <TD style="height:40px;width:180px;text-align: center;"><fmt:formatNumber value="${list22.JE}" pattern=".00"></fmt:formatNumber></TD>
	    <TD style="height:40px;width:180px;text-align: center;"></TD>
	  </tr>
 	 </c:forEach>
 	  <c:forEach var="list3" items="${list3}">
 	 <tr>
	    <TD style="height:40px;width:180px;text-align: center;"><b>合计</b></TD>
	    <TD style="height:40px;width:180px;text-align: center;"></TD>
	    <TD style="height:40px;width:180px;text-align: center;">${list3.cskje}</TD>
	    <TD style="height:40px;width:180px;text-align: center;"></TD>
	    <TD style="height:40px;width:180px;text-align: center;"></TD>
	    <TD style="height:40px;width:180px;text-align: center;"></TD>
	    <TD style="height:40px;width:180px;text-align: center;">${list3.cje}</TD>
	    <TD style="height:40px;width:180px;text-align: center;"></TD>
	  </tr>
	  </c:forEach>
	</TBODY>
</TABLE>
<div style="text-align: right; margin-right:5%;"><span>打印日期：${time}</span></div>
</div>

</div>
<div id="div4">

<div style="page-break-after(before)">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="search-simple">
			</div>
	</div>
	<div class="container-fluid" id="titleh" style="margin-top: 20px"> 
		<div> 
			<center>
				<tr style="height:25px;">
			       <td colspan="3" align="center" style="height: 40px">
	                 <span id="txt_bbmc" style="font-size: 12pt"><h2>安&nbsp;徽&nbsp;财&nbsp;贸&nbsp;职&nbsp;业&nbsp;学&nbsp;院 &ensp;差&nbsp;旅&nbsp;费&nbsp;报&nbsp;销&nbsp;单</h2></span>
	               </td>
		        </tr>
		    </center>
		    <div>
		    </div>
		</div>
		<div class="btn-group pull-right" style="float: right;margin-top: 20px;margin-right:5%" role="group">
		       <b>  附件</b> &ensp;${clfbxzb.fjzzs} &ensp;<b>张</b>         
		</div>  
		<div style="position: absolute;margin-top: 20px;margin-left:5%;">        	 
	                   <b> 填报日期：</b>&ensp;&ensp;&ensp;${clfbxzb.czrq}&ensp;&ensp;&ensp;
		</div>
	</div>
	<br>	<br>

<div class="container-fluid"  id="tbodyy">
	<div class='responsive-table'>
	<div class='scrollable-area'> 
		<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" 
				style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
	  	<TBODY>      
		  <tr>
		    <TD colspan="2" rowspan="1" style="height:20px;width:20%;text-align: center;"><b>姓名</b></TD>
		    <TD colspan="4" style="height:70px;width:20px;text-align: center;">${clfbxzb.sqrmc}</TD>
		    <TD colspan="1" style="height:70px;width:20px;text-align: center;"><b>工作部门</b></TD>
		    <TD colspan="3" rowspan="1"  style="height:70px;width:20px;text-align: center;">${clfbxzb.szbmmc}</TD>
		    <TD colspan="3" style="height:70px;width:40px;text-align: center;"><b>出差事由</b></TD>
		    <TD colspan="4" style="height:70px;width:40px;text-align: center;"><b>生活补助</b></TD>
		    <TD colspan="2" rowspan="1" style="height:70px;width:40px;text-align: center;"><b>住宿费</b></TD>
		  </tr>
		   <tr>
		     <TD colspan="3" rowspan="1" style="height:70px;width:40px;text-align: center;"><b>起始时间</b></TD>
		     <TD colspan="3" rowspan="1" style="height:70px;width:40px;text-align: center;"><b>到达时间</b></TD>
		     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"><b>飞机票</b></TD>
		     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"><b>火车票</b></TD>
		     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"><b>出租车票</b></TD>
		     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"><b>汽车票</b></TD>
		     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"><b>培训</b></TD>
		     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"><b>会议></b></TD>
		     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"><b>其他</b></TD>
		     <TD colspan="2" rowspan="1" style="height:70px;width:40px;text-align: center;"><b>教师</b></TD>
		     <TD colspan="2" rowspan="1" style="height:70px;width:40px;text-align: center;"><b>学生</b></TD>
		     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"><b>张</b></TD>
		     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;"><b>金额</b></TD>
		  </tr>
		  <tr>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> <b>月</b> </TD>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"><b> 日 </b></TD>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"><b> 起点</b> </TD>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"><b> 月</b> </TD>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"><b> 日</b> </TD>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"><b> 终点</b> </TD>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"><b> 天</b> </TD>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"><b> 金额</b> </TD>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"><b> 天</b> </TD>
		     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"><b> 金额 </b></TD>
		  </tr>
		  <c:forEach var="mxList" items="${mxList}">
			   <tr>
				   <td class="kssjyear">${mxList.ksyf}</td>
				   <td class="kssjmonth">${mxList.ksrq}</td>
				   <td>${mxList.cfdd}</td>
				   <td class="jssjyear">${mxList.jsyf}</td>
				   <td class="jssjmonth">${mxList.jsrq}</td>
				   <td>${mxList.mddd}</td>
				   <td class="fj">${mxList.fjje}</td>
				   <td class="hc">${mxList.hcje}</td>
				   <td class="czc">${mxList.czcje}</td>
				   <td class="qc">${mxList.qcje}</td>
				   <td class="px">${mxList.pxfy}</td>
				   <td class="hy">${mxList.hyfy}</td>
				   <td class="qt">${mxList.qtfy}</td>
				   <td class="jst">${mxList.lsshbzts}</td>
				   <td class="jsj">${mxList.lsshbzje}</td>
				   <td class="xst">${mxList.xsshbzts}</td>
				   <td class="xsj">${mxList.xsshbzje}</td>
				   <td class="fjs">${mxList.ffjs}</td>
				   <td class="zs">${mxList.zdfje}</td>
			   </tr>
		   </c:forEach> 
		   <c:forEach var="mxList2" items="${mxList2}">
		 	   <tr>
				   <TD colspan="6"  style="height:40px;width:180px;text-align: center;"><b>小计</b></TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.sfjje} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.shcje} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.sczcje} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.sqcje} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.spxfy} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.shyfy} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.qtfy} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.slsshbzts} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.sxsshbzts} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.slsshbzje} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.sxsshbzje} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.szdfje} </TD>
				   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> ${mxList2.sffjs} </TD>
			  </tr>
		  </c:forEach> 
		  <tr>
		  	<td colspan="13"> <b>报销金额（大写）:</b><span id="hj2"></span></td>
		  	<td colspan="6"> <b>报销金额:</b>  ${map2.BXZJE} </td>  
		  </tr>
		
		</TBODY>
		
		<table style="border: 0px;margin-left: 30px;">
			<tr style="border: 0px;">
				<td colspan="3" style="border: 0px;width: 8%;border-left: none; "><b>院长：</b>
					<span><c:if test="${not empty fjView}"><img src="${fjView }" style="width: 90px;height: 50px;text-align: center;"></c:if>
		  		      <c:if test="${empty fjView}">${yzxm}</c:if>
		  			</span>
				</td>
				<td colspan="3" style="border: 0px;width: 12%;"><b>分管财务院领导：</b>
					<span><c:if test="${not empty fjView2}"><img src="${fjView2 }" style="width: 90px;height: 50px;text-align: center;"></c:if>
		  		      <c:if test="${empty fjView2}">${cwfgld}</c:if>
		  			</span>
				</td>
				<td colspan="3" style="border: 0px;width: 12%;"><b>分管院领导：</b>
					<span><c:if test="${not empty fjView3}"><img src="${fjView3 }" style="width: 90px;height: 50px;text-align: center;"></c:if>
		  		      <c:if test="${empty fjView3}">${fgyld}</c:if>
		  			</span>
<%-- 					<span>${fgyld}</span> --%>
				</td>
				<td colspan="3" style="border: 0px;width: 12%;"><b>财务负责人</b>
					<span><c:if test="${not empty fjView4}"><img src="${fjView4 }" style="width: 90px;height: 50px;text-align: center;"></c:if>
		  		      <c:if test="${empty fjView4}">${cw}</c:if>
		  			</span>
<%-- 					<span>${cw}</span> --%>
				</td>
				<td colspan="3" style="border: 0px;width: 12%;"><b>部门负责人：</b>
					<span><c:if test="${not empty fjView5}"><img src="${fjView5 }" style="width: 90px;height: 50px;text-align: center;"></c:if>
		  		      <c:if test="${empty fjView5}">${bmfzr}</c:if>
		  			</span>
<%-- 					<span>${bmfzr}</span> --%>
				</td>
				<td colspan="4" style="border: 0px;width: 12%;border-right: none;"><b>报销人：</b>
					<span>${bxr}</span>
				</td>
			</tr>
			<div style="text-align: right; margin-right:5%;float:right"><span>打印日期：${time}</span></div>
		</table>
	
		
	</TABLE>	
					
</div>
	<div class="bottom1">
		
	</div>
</div>
		</div>
	</div>
	
	</div>
<div id="div5">
	


<%-- <c:if test="${not empty mxList4}"> --%>
	
<h2 id="div1" style="text-align: center;">教&nbsp;职&nbsp;工&nbsp;出&nbsp;差&nbsp;审&nbsp;批&nbsp;表</h2>
<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
  <TBODY>      
  <TR>
    <TD style="height:70px;width:20%;text-align: center;"><b>申请部门</b></TD>
    <TD style="padding-left: 8px;">${ccywsq.szbmmc }</TD>
    <TD style="height:70px;width:180px;text-align: center;"><b>申请人</b></TD>
    <TD style="padding-left: 8px;">${ccywsq.ryxm }</TD>
  <tr>
    <TD style="height:70px;width:20%;text-align: center;"><b>出差时间</b></TD>
    <TD style="padding-left: 8px;width:30%;">${ccywsq.kssjmc }&nbsp;<b>至</b>&nbsp;${ccywsq.jssjmc }</TD>
    <TD style="height:70px;width:180px;text-align: center;"><b>出差人员</b></TD>
	<TD style="padding-left: 8px;">
		<c:forEach items="${txryList }" var="item" varStatus="status">
			<c:if test="${(fn:length(txryList)) == status.count }">${item.xm }</c:if>
			<c:if test="${(fn:length(txryList)) > status.count }">${item.xm }，</c:if>
		</c:forEach>
	</TD>
 </tr>
<tr>
    <TD style="height:140px;width:20%;text-align: center;"><b>出差内容</b></TD>
    <TD colspan="3" style="padding-left: 8px;">${ccywsq.ccnr }</TD>
 </tr>
 <tr>
    <TD style="height:70px;width:20%;text-align: center;" rowspan="3"><b>部门领导意见</b></TD>
    <TD style="border:0px;text-align: left;">&emsp;${bmldsh.shyj}</td>
    <TD style="height:70px;width:20%;text-align: center;" rowspan="3"><b>分管领导意见</b></TD>
	<TD style="border:0px;text-align: left;">&emsp;${fgldsh.shyj }</TD>
 </tr>
 <tr >
	<td style="border:0px;text-align: center;"><span><img src=${fjView } style="width: 90px;height: 50px;"></span></td>
	<td style="border:0px;text-align: center;"><span><img src=${fjView2 } style="width: 90px;height: 50px;"></span></TD>
 </tr>
 <tr>
 	<td style="border:0px;text-align: right;"><fmt:formatDate value="${bmldsh.shrq }"/></td>
 	<td style="border:0px;text-align: right;"><fmt:formatDate value="${fgldsh.shrq }"/></td>
 </tr>
</TABLE>
<div style="text-align: right; margin-right:5%;"><span>打印日期：${time}</span></div>
<%-- </c:if> --%>
</div>
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script> 
	$(function(){
		init();
	})
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
		LODOP.ADD_PRINT_HTM(128,"5%","90%",720,strStyle+document.getElementById("div2").innerHTML);
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM(128,"5%","90%",720,strStyle+document.getElementById("div3").innerHTML);
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM(128,"5%","90%",720,strStyle+document.getElementById("div4").innerHTML);
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM(128,"5%","90%",720,strStyle+document.getElementById("div5").innerHTML);
		LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("div1").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
		LODOP.PREVIEW();	
		
	};	
	function init(){
		var zje="${printinfolist.BXZJE}";//
		var zje2="${map2.BXZJE}";
		$("#hj").text(""+smalltoBIG(parseFloat(zje)));
		$("#hj2").text(""+smalltoBIG(parseFloat(zje2)));
	}
	/** 数字金额大写转换(可以处理整数,小数,负数) */    
	function smalltoBIG(n)     
	{    
	    var fraction = ['角', '分'];    
	    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];    
	    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];    
	    var head = n < 0? '欠': '';    
	    n = Math.abs(n);    
	  
	    var s = '';    
	  
	    for (var i = 0; i < fraction.length; i++)     
	    {    
	        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');    
	    }    
	    s = s || '整';    
	    n = Math.floor(n);    
	  
	    for (var i = 0; i < unit[0].length && n > 0; i++)     
	    {    
	        var p = '';    
	        for (var j = 0; j < unit[1].length && n > 0; j++)     
	        {    
	            p = digit[n % 10] + unit[1][j] + p;    
	            n = Math.floor(n / 10);    
	        }    
	        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;    
	    }    
	    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');    
	}  
</script>
		

</body>
