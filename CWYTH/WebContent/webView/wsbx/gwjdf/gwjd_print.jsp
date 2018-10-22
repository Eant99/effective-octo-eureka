<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
*{
	font-size:16px;
}
.div1{
		width:90%;
	}
.div12{
		width:80%;
		height:36px;
		margin:0 auto;
		text-align: center;
	}
.div121{
	float:left;
}
TABLE TD{
	height:40px;
}
.TD1{
	text-align: center;
}
.TD2{
	padding-left: 8px;
}
</style>
</head>
<body>
<div  style="margin:10px 5%">
	<button type="button" class="btn btn-default" id="btn_print">打印预览</button>
	<button type="button" class="btn btn-default" id="btn_back">返回</button>
</div>
<div class="div1" id="div1">
<!-- 	<div > -->
<!-- 		<div class="pull-right"> -->
<!-- 			<button type="button" class="btn btn-default" id="btn_print">打印预览</button> -->
<!-- 			<button type="button" class="btn btn-default" id="btn_back">返回</button> -->
<!--         </div> -->
<!-- 	</div> -->
	<h2 style="text-align: center;">${title }</h2>
	<div class="div12" style="text-align:center;">
		接待单位（公章）：${gwjdfsq.jdbm }&ensp;&ensp;&ensp;&ensp;经办人：${gwjdfsq.sqr }&ensp;&ensp;&ensp;&ensp;${gwjdfsq.year }年${gwjdfsq.mon }月${gwjdfsq.days }日
<%-- 		<div class="div121" style="width:45%;">接待单位（公章）：${gwjdfsq.jdbm }</div> --%>
<%-- 		<div class="div121" style="width:35%;">经办人：${gwjdfsq.sqr }</div> --%>
<%-- 		<div class="div121" style="width:20%;">${gwjdfsq.year }年${gwjdfsq.mon }月${gwjdfsq.days }日</div> --%>
	</div>

<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%;margin-top:5%" bordercolor="#000000">
  <TBODY>      
  <TR>
  	<TD rowspan="3" class="TD1" style="width:30px;text-align:center;">接待对象</TD>
    <TD class="TD1" style="text-align: center;    width: 100px;">姓&ensp;&ensp;&ensp;&ensp;名</TD>
    <TD class="TD2" >${gwjdfsq.lbxm }</TD>
   	<TD class="TD1" style="text-align: center;    width: 100px;">单&ensp;&ensp;&ensp;&ensp;位</TD>
    <TD class="TD2" >${gwjdfsq.lbdw }</TD>
    <TD class="TD1" style="text-align: center;    width: 100px;">职&ensp;&ensp;&ensp;&ensp;务</TD>
    <TD class="TD2" >${gwjdfsq.lbzw }</TD>
  <tr>
    <TD class="TD1" style="text-align: center;    width: 100px;">联&ensp;系&ensp;人</TD>
    <TD class="TD2" >${gwjdfsq.lxr }</TD>
    <TD class="TD1" style="text-align: center;    width: 100px;">联系电话</TD>
    <TD class="TD2" colspan="3">${gwjdfsq.lxdh }</TD>
 </tr>
<tr>
    <TD class="TD1" style="text-align: center;    width: 100px;">随&ensp;&ensp;&ensp;&ensp;员</TD>
    <TD class="TD2" colspan="5">${gwjdfsq.txry }</TD>
 </tr>
</TABLE>
<TABLE  border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%;margin-top:-1px" bordercolor="#000000">
	 <tr>
		<TD style="  border-bottom-width: 0px; text-align:center;">活动项目</th>
		<TD style=" border-bottom-width: 0px; text-align:center;">时间</th>
		<TD style=" border-bottom-width: 0px; text-align:center;">地点</th>
		<TD style=" border-bottom-width: 0px; text-align:center;" >校内参建人员</th>
     </tr>
     <c:forEach var="mxList" items="${mxList}"> 
    	<tr>
			<td style="padding: 4px !important">${mxList.hdxm }
			</td>
			<td style="padding: 4px !important" >${mxList.hdsj}
			</td>
			<td style="padding: 4px !important">${mxList.hddd}
			</td>
			<td style="padding: 4px !important">${mxList.ptry}
			</td>
		</tr>
	</c:forEach>
</TABLE>
<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%;margin-top:-1px" bordercolor="#000000">
  <TBODY>      
  <TR>
  	<TD>
  		<div style="width:100%;">
			<div>审批意见：</div>
			<div>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;${yjList1.shyj }</div>
			<div style="width:300px;float:right;">
				<div style="width:100%;margin-right:10px;text-align:center;height:30px;">部门负责人签字：${yjList1.xm }</div>
				<div style="width:100%;margin-right:10px;text-align:center;height:30px;">日期：${yjList1.sj }</div>
			</div>
		</div>
	</TD>
  <tr>
</TABLE>
<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%;margin-top:-1px" bordercolor="#000000">
  <TBODY>      
  <TR>
  	<TD>
  		<div style="width:100%;">
			<div>审批意见：</div>
			<div>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;${yjList2.shyj }</div>
			<div style="width:300px;float:right;margin-bottom:0px;">
				<div style="width:100%;text-align:center;height:30px;">校领导签字：${yjList2.xm }</div>
				<div style="width:100%;text-align:center;height:30px;">日期：${yjList2.sj }</div>
			</div>
		</div>
	</TD>
  <tr>
</TABLE>
<div class="div1">
	<div class="div12" style="font-size:12px;text-align:center;">
		填表说明：此表一式三份，一份交学校办公室，一份交财务处，一份本单位留存。
	</div>
</div>
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script> 
//返回按钮
$("#btn_back").click(function(){
	 window.history.back(-1);
//		window.location.href = "${ctx}/webView/wsbx/sqspcx/sqspcx_list.jsp";
});
$("#btn_print").click(function(){
		var LODOP=getLodop();  
		LODOP.PRINT_INITA("20mm","20mm");
		LODOP.ADD_PRINT_HTML(0,0,"257mm","170mm",document.getElementById("div1").innerHTML);
		LODOP.SET_PRINT_PAGESIZE(2,"210mm","297mm","A4");
		LODOP.PREVIEW();	
});	
</script>
		

</body>
