<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公务接待报销打印</title>
<%@include file="/static/include/public-list-js.inc"%>
<style type="text/css">
table{border-collapse:collapse;} 
table,td,th {border-color:black;}
</style>
</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>
<a id="btn_back" href="javascript:void(0)">返回</a>

<div class="div1" id="div1">
<!-- 	<div > -->
<!-- 		<div class="pull-right"> -->
<!-- 			<button type="button" class="btn btn-default" id="btn_print">打印预览</button> -->
<!-- 			<button type="button" class="btn btn-default" id="btn_back">返回</button> -->
<!--         </div> -->
<!-- 	</div> -->
	<h2 style="text-align: center;">${title }</h2>
	<div class="div12" style="text-align:left;font-size:14px;">
		<div class="div121" style="width:45%;float:left"><b>接待单位（公章）</b>：${gwjdfsq.jdbm }</div>
		<div class="div121" style="width:30%;float:left"><b>经办人</b>：${gwjdfsq.sqr }</div>
		<div class="div121" style="width:25%;float:left;text-align:right"><b>时间</b>：${gwjdfsq.year }年${gwjdfsq.mon }月${gwjdfsq.days }日</div> 
	</div>
</div>
<div id="div2">
<TABLE border=1  width="100%" style="border-collapse:collapse;" bordercolor="#000000" cellspacing="2" cellpadding="2">
  <TBODY>      
  <TR>
  	<Th rowspan="3" class="TD1" style="width:30px;text-align:center;">接待对象</Th>
    <Th class="TD1" style="text-align: center;    width: 100px;">姓&ensp;&ensp;&ensp;&ensp;名</Th>
    <TD class="TD2" width="200px">${gwjdfsq.lbxm }</TD>
   	<Th class="TD1" style="text-align: center;    width: 100px;">单&ensp;&ensp;&ensp;&ensp;位</Th>
    <TD class="TD2" >${gwjdfsq.lbdw }</TD>
    <Th class="TD1" style="text-align: center;    width: 100px;">职&ensp;&ensp;&ensp;&ensp;务</Th>
    <TD class="TD2" width="200px">${gwjdfsq.lbzw }</TD>
  <tr>
    <Th class="TD1" style="text-align: center;    width: 100px;">联&ensp;系&ensp;人</Th>
    <TD class="TD2" width="200px">${gwjdfsq.lxr }</TD>
    <Th class="TD1" style="text-align: center;    width: 100px;">联系电话</Th>
    <TD class="TD2" colspan="3">${gwjdfsq.lxdh }</TD>
 </tr>
<tr>
    <Th class="TD1" style="text-align: center;    width: 100px;">随&ensp;&ensp;&ensp;&ensp;员</Th>
    <TD class="TD2" colspan="5">${gwjdfsq.txry }</TD>
 </tr>
</TABLE>
<TABLE  border=1 width="100%" style="border-collapse:collapse;margin-top:-1px" bordercolor="#000000">
	 <tr>
		<Th style=" text-align:center;">活动项目名称</Th>
		<Th style=" text-align:center;">时间</Th>
		<Th style=" text-align:center;">地点</Th>
		<Th style=" text-align:center;" >校内参建人员</Th>
     </tr>
     <c:forEach var="mxList" items="${mxList}"> 
    	<tr>
			<td >${mxList.hdxm }
			</td>
			<td >${mxList.hdsj}
			</td>
			<td >${mxList.hddd}
			</td>
			<td>${mxList.ptry}
			</td>
		</tr>
	</c:forEach>
</TABLE>
<TABLE border=1  width="100%" style="border-collapse:collapse;margin-top:-1px" bordercolor="#000000">
  <TBODY>      
  <TR>
  	<TD>
  		<div style="width:100%;">
			<div><b>审批意见：</b></div>
			<div>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;${yjList1.shyj }</div>
			<div style="width:300px;float:right;">
				<div style="width:100%;margin-right:10px;text-align:center;height:30px;"><b>单位负责人签字</b>：${yjList1.xm }</div>
				<div style="width:100%;margin-right:10px;text-align:center;height:30px;"><b>日期</b>：${yjList1.sj }</div>
			</div>
		</div>
	</TD>
  <tr>
</TABLE>
<TABLE border=1  width="100%" style="border-collapse:collapse;margin-top:-1px" bordercolor="#000000">
  <TBODY>      
  <TR>
  	<TD>
  		<div style="width:100%;">
			<div><b>审批意见：</b></div>
			<div>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;${yjList2.shyj }</div>
			<div style="width:300px;float:right;margin-bottom:0px;">
				<div style="width:100%;text-align:center;height:30px;"><b>校领导签字</b>：${yjList2.xm }</div>
				<div style="width:100%;text-align:center;height:30px;"><b>日期</b>：${yjList2.sj }</div>
			</div>
		</div>
	</TD>
  <tr>
</TABLE>
<div class="div1">
	<div class="div12" style="font-size:14px;text-align:left;">
		<b>填表说明</b>：此表一式三份，一份交学校办公室，一份交财务处，一份本单位留存。
	</div>
</div>
</div>
<!-- ---------------------------------------------------------------------------------------------------- -->
<div id="div3">
	
	<h2 id="div1" style="text-align: center;">${organizationname}公务接待报销封面</h2>
		<TABLE border=1 cellSpacing=0 cellPadding=1 width="100%" style="border-collapse:collapse;" bordercolor="#000000">
		  <tr>
		    <TD width="20%" style="height:70px;text-align: center;"><b>开支内容</b></TD>
		    <TD width="30%" colspan="2" style="padding-left: 8px;">${printinfolist.KZNR}</TD>
		    <TD width="20%" style="height:70px;text-align: center;"><b>单据张数</b></TD>
			<TD width="30%" style="text-align: center;">${organizationname1.fjzzs}张</TD>
		  </tr>
		  <tr >
		    <TD rowspan="${length}" style="height:100px;width:20%;text-align: center;"><b>共计报销金额人民币（大写）</b></TD>
		    <TD rowspan="${length}" colspan="2" id="456" style="text-align: center;" id="zje" ></TD>
		    <TD colspan="1" id="" style="text-align: center;"><b>公务接待费</b></TD>
		    <TD colspan="1" id="" style="text-align: center;"><b>金额￥${printinfolist.BXZJE}</b></TD>
		  </tr>
		  <c:forEach var="jjMap1" items="${jjMap}">
		  <tr>
		  	<td colspan="1"  id="" style="text-align: center;">${jjMap1.FYMC}</td>
		  	<td colspan="1"  id="" class="je" style="text-align: center;">${jjMap1.BXJE}</td>
		  </tr>
		  </c:forEach>
		</TABLE>
		<TABLE border=1 width="100%" style="border-collapse:collapse;margin-top:-1px" bordercolor="#000000">
		<tr style="height:60px;">
		    <c:forEach var="printinfolists" items="${printinfolists}">
		   		<TD style="text-align: center;"><b>${printinfolists.TASKNAME}</b></TD>
		    </c:forEach>
		    <c:forEach var="printinfolistsqr" items="${printinfolistsqr}">
		       <TD style="text-align: center;"><b>${printinfolistsqr.TASKNAME}</b></TD>
		    </c:forEach>
		  </tr>
		  <tr style="height:20px;">
		    <c:forEach var="printinfolists" items="${printinfolists}">
		   		<TD style="text-align: left;border-bottom:none;">${printinfolists.SHYJ}</TD>
		    </c:forEach>
		       <TD style="text-align: left;border-bottom:none;"></TD>
		  </tr>
		  <tr style="height:20px;">
		    <c:forEach var="printinfolists" items="${printinfolists}">
		   		<TD style="text-align: center;border-top:none;border-bottom:none;">${printinfolists.XM}</TD>
		    </c:forEach>
		     <c:forEach var="printinfolistsqr" items="${printinfolistsqr}">
		       <TD style="text-align: center;border-top:none;border-bottom:none;">${printinfolistsqr.XM}</TD>
		       </c:forEach>
		  </tr>
		   <tr style="height:20px;">
		    <c:forEach var="printinfolists" items="${printinfolists}">
		   		<TD style="text-align: right;border-top:none;">${printinfolists.STARTTIME}</TD>
		    </c:forEach>
		     <c:forEach var="printinfolistsqr" items="${printinfolistsqr}">
		       <TD style="text-align: right;border-top:none;">${printinfolistsqr.STARTTIME}</TD>
		       </c:forEach>
		  </tr>
		   <tr style="height:100px;">
		    <c:forEach var="printinfolists" items="${printinfolists}">
		   		<TD style="text-align: right;"></TD>
		    </c:forEach>
		    <c:forEach var="printinfolistsqr" items="${printinfolistsqr}">
		       <TD style="text-align: right;"></TD>
		    </c:forEach>
		  </tr>
		</TABLE>
		<div style="text-align: right; "><span><b>打印日期</b>：${time}</span></div>
</div>

<!-- 		----------------------------------粘贴单开始--------------------------------------------------------	 -->
	<div id="div4" style="">
<!-- 	<div style="width:500px;margin-left:35%;"> -->
    <div style="border-left: 3px dashed; width:90%;height:650px;margin-left:10px">
    <p style="display:block;width:50px; height:20px; position:relative;top:240px; left:-27px;text-align: center; background: white;">装</p>
    <p style="display:block;width:50px; height:20px; position:relative;top:260px; left:-27px;text-align: center; background: white;">订</p>
    <p style="display:block;width:50px; height:20px; position:relative;top:280px; left:-27px;text-align: center; background: white;">线</p>
<%-- 	<div id="div31" style="float:top;width: 100%;height: 90px;text-align: center;> --%>
<!-- 		<div style="display:block; text-align: center;border-bottom:1px solid #000; font-size:32px; font-weight:bold;"> -->
<!-- 		单&nbsp;据&nbsp;粘&nbsp;贴&nbsp;单 -->
<!-- 		</div> -->
<!-- 	</div> -->

	<h1 id="div5" style="text-align: center;"><span style="border-bottom:1px solid #000;">山&nbsp;东&nbsp;农&nbsp;业&nbsp;工&nbsp;程&nbsp;学&nbsp;院&nbsp;单&nbsp;据&nbsp;粘&nbsp;贴&nbsp;单</span></h1>	
<!-- 		<p><h2 style="margin-left:5%;" >单据粘贴注意事项</h2></p> -->
<!-- 		<p style="margin-left:5%;">1.请将报销凭证沿装订线的右侧，均匀、平整的粘贴在粘贴单上。</p> -->
<!-- 		<p style="margin-left:5%;">2.粘贴单据时请尽量分类粘贴，或按报销时间、事项的顺序粘贴。</p> -->
<!-- 		<p style="margin-left:5%;">3.会议通知、合同（报告）或其他标准A4纸大小规格的票据对齐粘贴单粘贴到后边。</p> -->
<!-- 		<p style="margin-left:5%;">4.粘贴单据时注意事项可以被覆盖。</p> -->
<!-- 		<TABLE border=1 cellSpacing=0 cellPadding=1 width="100%" style="border-collapse:collapse;margin: 200px 0% 0px 0%;"" bordercolor="#000000"> -->
<!-- 		  <tr > -->
<%-- 		    <TD colspan="${printinfolist.size()}" id="456" style="text-align: center;" id="zje" ></TD> --%>
<!-- 		    <TD id="" style="text-align: center;"><b>金额小写</b></TD> -->
<%-- 		    <TD id="123" style="text-align: right;">${organizationname1.BXZJE}</TD> --%>
<!-- 		  </tr> -->
<!-- 		  <tr > -->
<!-- 		    <TD style="text-align: center;" >项目</TD> -->
<!-- 		    <TD style="text-align: center;">单据张数</TD> -->
<%-- 		    <c:forEach var="printinfolist" items="${printinfolist}"> --%>
<%-- 		   		<TD style="text-align: center;">${printinfolist.TASKNAME}</TD> --%>
<%-- 		    </c:forEach> --%>
<!-- 		  </tr> -->
<!-- 		  <tr > -->
<%-- 		    <TD style="text-align: center;" >${organizationname1.XMMC}</TD> --%>
<%-- 		    <TD style="text-align: center;">${organizationname1.FJZZS}</TD> --%>
<%-- 		    <c:forEach var="printinfolist" items="${printinfolist}"> --%>
<%-- 		   		<TD style="text-align: center;">${printinfolist.XM}</TD> --%>
<%-- 		    </c:forEach> --%>
<!-- 		  </tr> -->
<!-- 		</TABLE> -->
	</div>
</div>
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script> 
	$(function(){
		init();
	})
	
	//返回
$("#btn_back").click(function(){
	window.history.back(-1);
	});
	function PreviewMytable(){
		var fontStyle="<style> table,td,th {font-size:14px;} </style>";
		var LODOP=getLodop();  
      	LODOP.PRINT_INITA("20mm","${dyzdbj}mm");
        LODOP.ADD_PRINT_HTML(0,0,"${ymkd}mm","170mm",fontStyle+document.getElementById("div1").innerHTML);
        LODOP.ADD_PRINT_HTML("17mm",0,"${ymkd}mm","170mm",fontStyle+document.getElementById("div2").innerHTML);
        LODOP.NEWPAGE();
		LODOP.ADD_PRINT_HTML(0,0,"${ymkd}mm","170mm",fontStyle+document.getElementById("div3").innerHTML);
		LODOP.NEWPAGE();
		LODOP.ADD_PRINT_HTML("-5mm",-80,"${ztdkd}mm","190mm",document.getElementById("div4").innerHTML);
		LODOP.ADD_PRINT_BARCODE(0,"${txmbj}mm",200,70,"Code39","${organizationname1.DJBH}");
		LODOP.SET_PRINT_PAGESIZE(2,0,0,"A4");
		LODOP.PREVIEW();	
	};	
	function init(){
		var zje= "${organizationname1.BXZJE}";
		$("#456").text((""+smalltoBIG(parseFloat(zje))).replace("undefined", "零").replace("undefined", "零"));
	}

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
</html>
