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
<h1 id="div1" style="text-align: center;">安徽财贸职业学院报销明细封面</h1>
</div>
<div id="div2">
<%-- <c:forEach var="printinfolist" items="${printinfolist}"> --%>
填表日期：共 张
<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
  	<TBODY>      
	  <tr>
	    <TD colspan="2" rowspan="1" style="height:20px;width:20%;text-align: center;">姓名</TD>
	    <TD colspan="4" style="height:70px;width:20px;text-align: center;"></TD>
	    <TD colspan="1" style="height:70px;width:20px;text-align: center;">工作部门</TD>
	    <TD colspan="3" rowspan="1"  style="height:70px;width:20px;text-align: center;"></TD>
	    <TD colspan="3" style="height:70px;width:40px;text-align: center;">出差事由</TD>
	    <TD colspan="4" style="height:70px;width:40px;text-align: center;">生活补助</TD>
	    <TD colspan="2" rowspan="1" style="height:70px;width:40px;text-align: center;">住宿费</TD>
	  </tr>
	   <tr>
	     <TD colspan="3" rowspan="1" style="height:70px;width:40px;text-align: center;">起始时间</TD>
	     <TD colspan="3" rowspan="1" style="height:70px;width:40px;text-align: center;">到达时间</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">飞机票</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">火车票</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">出租车票</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">汽车票</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">培训</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">会议</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">其他</TD>
	     <TD colspan="2" rowspan="1" style="height:70px;width:40px;text-align: center;">教师</TD>
	     <TD colspan="2" rowspan="1" style="height:70px;width:40px;text-align: center;">学生</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">张</TD>
	     <TD colspan="1" rowspan="2" style="height:70px;width:40px;text-align: center;">金额</TD>
	  </tr>
	  <tr>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 月 </TD>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 日 </TD>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 起点 </TD>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 月 </TD>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 日 </TD>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 终点 </TD>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 天 </TD>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 金额 </TD>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 天 </TD>
	     <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;"> 金额 </TD>
	  </tr>
	  <c:forEach var="mxList" items="${mxList}">
	   <tr>
		   <td class="kssjyear">${mxList.kssj}</td>
		   <td class="kssjmonth">${mxList.kssj}</td>
		   <td>${mxList.cfdd}</td>
		   <td class="jssjyear">${mxList.jssj}</td>
		   <td class="jssjmonth">${mxList.jssj}</td>
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
 	 <tr>
	   <TD colspan="6"  style="height:40px;width:180px;text-align: center;">小计</TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	   <TD colspan="1" rowspan="1" style="height:70px;width:40px;text-align: center;">  </TD>
	  </tr>
	  <tr>
	  	<td colspan="19"> 报销金额（大写）</td> 
	  </tr>
	</TBODY>
	<br>
	
</TABLE>
学院领导：&emsp;&emsp;&emsp;&emsp;审核人:&emsp;&emsp;&emsp;&emsp;部门领导：&emsp;&emsp;&emsp;&emsp;报销人：
<%-- </c:forEach> --%>
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
// 		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);		
// 		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
// 		LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
// 		LODOP.SET_PRINT_STYLEA(0,"FontColor","#FF0000");
// 		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
// 		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
// 		LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
// 		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.PREVIEW();	
	};	
</script>
		

</body>
