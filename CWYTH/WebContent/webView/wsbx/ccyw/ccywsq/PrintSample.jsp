<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>

</head>
<body>

<a href="javascript:PreviewMytable();">打印</a>
<a href="${ctx }/wsbx/ccyw/ccywsq/goCcywsqPage?shzt=01&mkbh=130201">返回</a>
<div id="div2">
<table border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#000000">
 <caption>
<h2 style="text-align: center;">教&nbsp;职&nbsp;工&nbsp;出&nbsp;差&nbsp;事&nbsp;前&nbsp;审&nbsp;批&nbsp;表</h2>

</caption>
  <tbody>
    <tr>
      <td width="18%" height="85" style="text-align:center"><strong>申请部门</strong></td>
      <td width="34%">${ccywsq.szbmmc }</td>
      <td width="15%" style="text-align:center"><strong>申请人</strong></td>
      <td width="33%">${ccywsq.sqrmc }</td>
    </tr>
    <tr>
      <td height="80" style="text-align:center"><strong>出差时间</strong></td>
      <td>${ccywsq.kssjmc }至${ccywsq.jssjmc }</td>
      <td style="text-align:center"><strong>出差人员</strong></td>
      <td><c:forEach items="${txryList }" var="item" varStatus="status">
			<c:if test="${(fn:length(txryList)) == status.count }">${item.xmm }</c:if>
			<c:if test="${(fn:length(txryList)) > status.count }">${item.xmm }，</c:if>
	  </c:forEach></td>
    </tr>
    <tr>
      <td height="111" style="text-align:center"><strong>出差内容</strong></td>
      <td colspan="3">${ccywsq.ccnr }</td>
    </tr>
    <tr>
      <td height="108" rowspan="3" style="text-align:center"><strong>领导部门意见</strong></td>
      <td height="34" style="text-align: left;border-bottom: none;">${bmldsh.shyj }</td>
      <td rowspan="3" style="text-align:center"><strong>分管领导意见</strong></td>
      <td style="text-align: left;border-bottom: none;">${fgldsh.shyj }</td>
    </tr>
    <tr>
      <td height="38" style="text-align: right;border-bottom: none;border-top: none">${bmldsh.xm }</td>
      <td style="text-align: right;border-bottom: none;border-top: none">${fgldsh.xm }</td>
    </tr>
    <tr>
      <td height="45" style="text-align: right;border-top: none">${bmldsh.shrq }</td>
      <td style="text-align: right;border-top: none">${fgldsh.shrq }</td>
    </tr>
  </tbody>
</table>
<div style="text-align: right; margin-right:5%;"><span>打印日期：${time}</span></div>
</div>
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script> 
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
		LODOP.ADD_PRINT_HTML("5%","10%","85%","100%",document.getElementById("div2").innerHTML);
		LODOP.SET_PRINT_PAGESIZE(2,2100,2970,"A4");
		LODOP.PREVIEW();	
	};	
</script>
		

</body>
