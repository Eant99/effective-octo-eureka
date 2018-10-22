<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>专用发票打印</title>
<%@include file="/static/include/public-list-js.inc"%>
</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>
<a id="btn_back" href="javascript:void(0)">返回</a>


<!-- 打印区域---------------------------------------------------------------------------------------------------- -->
<div id="div1" style="width: 90%;height: 540px;margin:10px auto;">
  <table width="100%" border="1" style="border-collapse:collapse">
   <caption><h1>专用发票打印</h1></caption>
  <tbody>
    <tr>
      <td width="24%" height="35"> 出票日期</td>
      <td width="22%">${map.cprq}</td>
      <td width="26%"> 发票号码</td>
      <td width="28%">${map.pjh}</td>
    </tr>
    <tr>
      <td height="35">发票代码</td>
      <td>${map.fpdm}</td>
      <td>金额</td>
      <td>${map.je}</td>
    </tr>
    <tr>
      <td height="35">购货单位名称</td>
      <td colspan="3">${map.skr}</td>
    </tr>
    <tr>
      <td height="35">单位纳税人识别号</td>
      <td>${map.ghdwsbm}</td>
      <td>购货单位电话</td>
      <td>${map.ghdwdh}</td>
    </tr>
    <tr>
      <td height="35">购货单位地址</td>
      <td colspan="3">${map.ghdwdz}</td>
    </tr>
    <tr>
      <td height="35">购货单位开户行</td>
      <td>${map.skyh}</td>
      <td>购货单位账号</td>
      <td>${map.skzh}</td>
    </tr>
    <tr>
      <td height="35">销货单位名称</td>
      <td colspan="3">${map.cpr}</td>
    </tr>
    <tr>
      <td height="35">单位纳税人识别号</td>
      <td>${map.shdwsbm}</td>
      <td>销货单位电话</td>
      <td>${map.shdwdh}</td>
    </tr>
    <tr>
      <td height="35">销货单位地址</td>
      <td colspan="3">${map.shdwdz}</td>
    </tr>
    <tr>
      <td height="35">销货单位开户行</td>
      <td>${map.cpyh}</td>
      <td>销货单位账户</td>
      <td>${map.cpzh}</td>
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