<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收据打印</title>
<%@include file="/static/include/public-list-js.inc"%>
</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>
<a id="btn_back" href="javascript:void(0)">返回</a>


<!-- 打印区域---------------------------------------------------------------------------------------------------- -->
<div id="div1" style="width: 90%;height: 540px;margin:10px auto;">
  <table width="100%" border="1" style="border-collapse:collapse">
   <caption><h1>收据打印</h1></caption>
  <tbody>
    <tr>
      <td width="13%" height="35">入账日期</td>
      <td width="29%">${map.cprq}</td>
      <td width="27%">票据号</td>
      <td width="31%">${map.pjh}</td>
    </tr>
    <tr>
      <td height="35">金额</td>
      <td colspan="3">${map.je}</td>
    </tr>
    <tr>
      <td height="35">交款单位</td>
      <td colspan="3">${map.skr}</td>
    </tr>
    <tr>
      <td height="35">收款方式</td>
      <td colspan="3">${map.YT}</td>
    </tr>
    <tr>
      <td height="35">收款事由</td>
      <td colspan="3">${map.fjxx}</td>
    </tr>
    <tr>
      <td height="35">年月日</td>
      <td colspan="3">${map.nyr}</td>
    </tr>
    <tr>
      <td height="35">开票人</td>
      <td>${map.kpr}</td>
      <td>制单人</td>
      <td>${map.zdr}</td>
    </tr>
  </tbody>
</table>
</div>
<!-- 打印区域---------------------------------------------------------------------------------------------------- -->
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script>
	//返回
$("#btn_back").click(function(){
	window.location.href="${ctx}/pjgl/rcyw/pjdy/goPjdyListPage";
});
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印");
		LODOP.ADD_PRINT_HTM(100,"5%","90%",1000,document.getElementById("div1").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
		LODOP.SET_PRINT_PAGESIZE(2, 2100, 2970,"A4");
		LODOP.PREVIEW();	
	};	
</script>
		

</body>
</html>