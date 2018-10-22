<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>
<%@include file="/static/include/public-list-js.inc"%>
<!-- 安徽财贸职业学院差旅费报销单 -->
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>

<div id="div1">
</div>
<div id="div2">
	<h2 id="" style="text-align: center;">主&nbsp;持&nbsp;课&nbsp;题&nbsp;项&nbsp;目&nbsp;经&nbsp;费&nbsp;报&nbsp;销（结算）表</h2>
	<c:forEach var="printinfolist" items="${infokyrc}">
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
		    		${xmfzr}
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
		   <%int i = 1; %> 
		   <c:forEach var="listkyrc2" items="${listkyrc2}">
		   <tr>
		    <TD colspan="1" style="height:70px;width:20%;text-align: center;"><%=i %></TD>
		    <TD colspan="3" style="padding-left: 8px;text-align: center;">${listkyrc2.fymc}</TD>
		    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${listkyrc2.bxzje}</TD>
		    <%i++; %>
		  </tr>
		  </c:forEach>
		  <tr>
		    <TD colspan="4" style="padding-left: 8px;text-align: center;"><b>合&emsp;&emsp;&emsp;&emsp;&emsp;计</b></TD>
		    <TD colspan="2" style="height:70px;width:180px;text-align: center;">${map3.sbxzje}</TD>
		  </tr>
		   <tr>
		    <TD colspan="6" style="padding-left: 8px;height:70px;width:20%;text-align: center;"><b>本次报销项目经费合计人民币（大写）：</b><span id="hj"></span><b>,妥否，请批示</b>
		    <br><b>项目主持人：</b>
		    	<span>
		    		${xmfzr}
		  		</span>
		    <br>
		    </TD>
		  </tr>
		  <tr>
	    <TD colspan="1" rowspan="3" style="height:70px;width:20%;text-align: center;border-bottom:transparent;"><b>科研处意见</b></TD>
	    <TD colspan="1" style="padding-left: 8px;text-align:left;border-bottom:transparent;">${kycyjmap.shyj}</TD>
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
	    <TD colspan="1" style="padding-left: 8px;text-align:right;border-top:transparent;">
	    	<fmt:formatDate value="${kycyjmap.jdsj}" type="date" pattern="yyyy-MM-dd"/>
	    </TD>
	    <TD colspan="1" style="height:30px;width:20%;text-align:right;border-top:transparent;">
	    	<fmt:formatDate value="${cwdMap.jdsj}"  type="date"  pattern="yyyy-MM-dd"/>
	    </TD>
	    <TD colspan="1" style="height:30px;width:180px;text-align:right;border-top:transparent;">
	    	<fmt:formatDate value="${yxMap.jdsj}"  type="date"  pattern="yyyy-MM-dd"/>
	   </TD>
	  </tr>
		</TBODY>
	</table>
</c:forEach>
    <div style="text-align: right; margin-right:5%;"><span><b>打印日期：${time}</b></span></div>
</div>


<div id="div3">
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
		    <TD style="height:40px;width:180px;text-align: center;">${list22.skrq}</TD>
		    <TD style="height:40px;width:180px;text-align: center;">${list22.skje}</TD>
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
	<div style="text-align: right; margin-right:5%;"><span><b>打印日期：${time}</b></span></div>
</div>

<div id="div4">
		<h2 id="" style="text-align: center;">安&nbsp;徽&nbsp;财&nbsp;贸&nbsp;职&nbsp;业&nbsp;学&nbsp;院&nbsp;报&nbsp;销&nbsp;封&nbsp;面</h2>
		<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;"><b>开支内容</b></TD>
		    <TD colspan="2" style="padding-left: 8px;">${printinfolist.FYMC}</TD>
		    <TD style="height:70px;width:180px;text-align: center;"><b>单据张数</b></TD>
			<TD colspan="2" style="text-align: center;">${printinfolist.FJZZS}<b>张</b></TD>
		  </tr>
		  <tr >
		    <TD rowspan="${length}" style="height:140px;width:20%;text-align: right;"><b>共计报销金额人民币（大写）</b></TD>
		    <TD rowspan="${length}" colspan="1" id="456" style="text-align: center;" id="zje" ></TD>
		    <TD rowspan="${length}" colspan="1" id="123" style="text-align: center;">￥${printinfolist.BXZJE}</TD>
		    <TD colspan="1"  id="" style="text-align: center;"><b>经济科目</b></TD>
		    <TD colspan="2" id="" style="text-align: center;"><b>金额（元）</b></TD>
		  </tr>
		  <c:forEach var="jjMap1" items="${jjMap}">
		  <tr>
		  	<td colspan="1"  id="" style="text-align: center;">${jjMap1.FYMC}</td>
		  	<td colspan="1"  id="" style="text-align: center;">${jjMap1.BXJE}</td>
		  </tr>
		  </c:forEach>
		  <tr>
		    <TD style="height:70px;width:15%;text-align: center;"><b>院长审批意见&emsp;</b></TD>
		    <TD style="height:70px;width:15%;text-align: center;"><b>分管财务院领导审批意见&emsp;</b></TD>
		    <TD style="height:70px;width:15%;text-align: center;"><b>分管院领导审批意见&emsp;</b></TD>
		    <TD style="height:40px;width:15%;text-align: center;"><b>财务负责人审核意见&emsp;</b></TD>
		    <TD style="height:40px;width:15%;text-align: center;"><b>部门负责人审核意见&emsp;</b></TD>
		    <TD style="height:70px;width:15%;text-align: center;"><b>报销人&emsp;</b></TD>
		  </tr>
		  <tr style="height: 10px;">
		    <TD style="width:15%;text-align: left;border-bottom-color: #ffffff;">
		        ${yxMap.SHYJ }<br>
		    </TD>
		    <TD style="width:15%;text-align: left;border-bottom-color: #ffffff;">
		    	${bmMap.SHYJ }<br>
		    </TD>
		     <TD style="width:15%;text-align: left;border-bottom-color: #ffffff;">
		    	${kjMap.SHYJ }<br>
		    </TD>
		    <TD style="width:15%;text-align: left;border-bottom-color: #ffffff;">
		    	${cwMap.SHYJ }<br>
		    </TD>
		    <TD style="width:15%;text-align: left;border-bottom-color: #ffffff;">
		    	${bmfzrMap.SHYJ }<br>
		    </TD>
		    <TD style="width:15%;text-align: center;" rowspan="3">
		    	<span>${printinfolist.BXRMC}</span>
		    </TD>
		  </tr> 
		  <tr>
		  	<td style="text-align: center;border-bottom-color: #ffffff;">
		  		<span><c:if test="${not empty fjView}"><img src="${fjView }" style="width: 90px;height: 50px;text-align: center;"></c:if>
		  		      <c:if test="${empty fjView}">${yzxm}</c:if>
		  		</span>
		  	</td>
		  	<td style="text-align: center;border-bottom-color: #ffffff;">
		    	<span><c:if test="${not empty fjView2}"><img src="${fjView2 }" style="width: 90px;height: 50px;text-align: center;"></c:if>
		    	      <c:if test="${empty fjView2}">${fgcwyld}</c:if>
		    	</span>
		  	</td>
		  	<td style="text-align: center;border-bottom-color: #ffffff;">
		    	<span><c:if test="${not empty fjView3}"><img src="${fjView3}" style="width: 90px;height: 50px;text-align: center;"></c:if>
		    	      <c:if test="${empty fjView3}">${fgyld}</c:if>
		    	</span>
		  	</td>
		  	<td style="text-align: center;border-bottom-color: #ffffff;">
		    	<span><c:if test="${not empty fjView4}"><img src="${fjView4}" style="width: 90px;height: 50px;text-align: center;"></c:if>
		    	      <c:if test="${empty fjView4}">${cw}</c:if>
		    	</span>
		  	</td>
		  	<td style="text-align: center;border-bottom-color: #ffffff;">
		    	<span><c:if test="${not empty fjView5}"><img src="${fjView5 }" style="width: 90px;height: 50px;text-align: center;"></c:if>
		    	      <c:if test="${empty fjView5}">${bmfzr}</c:if>
		    	</span>
		  	</td>
		  </tr>
		  <tr>
		  	<td style="text-align: right;">
		    	<span ><fmt:formatDate value="${yxMap.jdsj }" pattern="yyyy-MM-dd"/></span>
		  	</td>
		  	<td style="text-align: right;"> 
		    	<span ><fmt:formatDate value="${bmMap.jdsj }" pattern="yyyy-MM-dd"/></span>
		  	</td>
		  	<td style="text-align: right;"> 
		    	<span ><fmt:formatDate value="${kjMap.jdsj }" pattern="yyyy-MM-dd"/></span>
		  	</td>
		  	<td style="text-align: right;">
		    	<span ><fmt:formatDate value="${cwMap.jdsj }" pattern="yyyy-MM-dd"/></span>
		  	</td>
		  	<td style="text-align: right;"> 
		  	        
		    	<span ><fmt:formatDate value="${bmfzrMap.jdsj }" pattern="yyyy-MM-dd"/></span>
		  	</td>
		  </tr>
		</TABLE>
		<div style="text-align: right; margin-right:5%;"><span><b>打印日期：${time}</b></span></div>
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
		LODOP.ADD_PRINT_HTM(128,"5%","90%",880,strStyle+document.getElementById("div2").innerHTML);
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM(128,"5%","90%",500,strStyle+document.getElementById("div3").innerHTML);
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM(128,"5%","90%",720,strStyle+document.getElementById("div4").innerHTML);
		LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("div1").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
		LODOP.PREVIEW();	
	};	
	function init(){
		var zje="${map3.sbxzje}";//
		var zje2="${printinfolist.BXZJE}";
		console.log(zje+"___1_____"+smalltoBIG(parseFloat(zje)));
		$("#hj").text(""+smalltoBIG(parseFloat(zje)));
		$("#hj2").text(""+smalltoBIG(parseFloat(zje2)));
		$("#456").text(" "+smalltoBIG(parseFloat(zje2)));
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
