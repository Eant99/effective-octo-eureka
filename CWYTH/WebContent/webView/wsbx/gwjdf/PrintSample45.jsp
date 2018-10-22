<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>
<!-- 安徽财贸职业学院差旅费报销单 -->

</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>

<div id="div1">
<h1 id="div1" style="text-align: center;">主持课题项目经费报销（结算）表</h1>
</div>
<div id="div2">
<c:forEach var="printinfolist" items="${printinfolist}">
<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
  	<TBODY>      
	  <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">项目名称</TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.xmmc}</TD>
	    <TD style="height:70px;width:180px;text-align: center;">项目主管部门</TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.xmzgbm}</TD>
	  </tr>
	   <tr>
	    <TD style="height:70px;width:20%;text-align: center;">主持人</TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.zcr}</TD>
	    <TD style="height:70px;width:180px;text-align: center;">项目类别</TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.lx}</TD>
	    
	  </tr>
	   <tr>
	    <TD style="height:70px;width:20%;text-align: center;">课题起止日期</TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.kssj}-${printinfolist.jssj}</TD>
	    <TD style="height:70px;width:180px;text-align: center;">课题编号</TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${printinfolist.xmbh}</TD>
	  </tr>
	  <tr>
	    <TD style="height:70px;width:20%;text-align: center;">经费资助总额（元）</TD>
	    <TD style="height:40px;width:180px;text-align: center;">${printinfolist.ysje}</TD>
	    <TD style="height:70px;width:180px;text-align: center;">前期已预付数额（元）</TD>
	    <TD style="height:70px;width:180px;text-align: center;">${printinfolist.syje}</TD>
	    <TD style="height:70px;width:180px;text-align: center;">现使用数额（元）</TD>
	    <TD style="height:70px;width:180px;text-align: center;">${printinfolist.xsyje}</TD>
	  </tr>
	  <tr>
	  	<td colspan="6" style="text-align: center;">本次实际支出明细</td>
	  </tr>
	  <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">序号</TD>
	    <TD colspan="3" style="padding-left: 8px;text-align: center;">研究经费实际开支内容</TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;">金额（元）</TD>
	  </tr>
	   <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">1</TD>
	    <TD colspan="3" style="padding-left: 8px;text-align: center;"></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;"></TD>
	  </tr>
	   <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">2</TD>
	    <TD colspan="3" style="padding-left: 8px;text-align: center;"></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;"></TD>
	  </tr>
	  <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">3</TD>
	    <TD colspan="3" style="padding-left: 8px;text-align: center;"></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;"></TD>
	  </tr>
	   <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">4</TD>
	    <TD colspan="3" style="padding-left: 8px;text-align: center;"></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;"></TD>
	  </tr>
	 <!--  <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">5</TD>
	    <TD colspan="3" style="padding-left: 8px;text-align: center;"></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;"></TD>
	  </tr>
	   <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">6</TD>
	    <TD colspan="3" style="padding-left: 8px;text-align: center;"></TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;"></TD>
	  </tr> -->
	  <tr>
	    <TD colspan="4" style="padding-left: 8px;text-align: center;">合&emsp;&emsp;&emsp;&emsp;&emsp;计</TD>
	    <TD colspan="2" style="height:70px;width:180px;text-align: center;"></TD>
	  </tr>
	   <tr>
	    <TD colspan="6" style="padding-left: 8px;height:70px;width:20%;text-align: center;">本次报销项目经费合计人民币（大写）：_________________,妥否，请批示
	    <br>项目主持人：________________<br>
	    </TD>
	  </tr>
	  <tr>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;">科研处意见</TD>
	    <TD colspan="1" style="padding-left: 8px;text-align: center;"></TD>
	    <TD colspan="1" style="height:70px;width:180px;text-align: center;">财务处意见</TD>
	    <TD colspan="1" style="height:70px;width:20%;text-align: center;"></TD>
	    <TD colspan="1" style="padding-left: 8px;text-align: center;">院领导意见</TD>
	    <TD colspan="1" style="height:70px;width:180px;text-align: center;"></TD>
	  </tr>
	 
	</TBODY>
</TABLE>
</c:forEach>
</div>
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script> 
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
		LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle+document.getElementById("div2").innerHTML);
		LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("div1").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
		LODOP.PREVIEW();	
	};	
</script>
		

</body>
