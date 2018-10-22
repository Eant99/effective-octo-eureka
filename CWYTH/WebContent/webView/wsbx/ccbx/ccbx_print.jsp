<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<script src="${ctx}/static/javascript/public/LodopFuncs.js"></script>
<style type="text/css">
<style type="text/css">
table{border-collapse:collapse;} 
table,td,th {border-color:black;}
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
	margin-left:0;
	}
	.bottom1 tr td{
	width: 400px !important;
	}
	tr td{
	height: 30px;
	text-align: center;
	}
/* 	tr th{ */
/* 	    border-bottom: 0px solid #ddd !important; */
/* 	} */
/* 	.fullscreen{ */
/* 	width:80%; */
/* 	margin:0 auto; */
/* 	} */
.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
    	border-bottom-width: 0px;
    	//border-bottom:1px solid #ddd;
	}
/* 	 table{ */
/* 		border-collapse:collapse!important; */
/* 	} */
   .bottom1{
  position: absolute;
   }
 #tbodyy .bottom1 div{
   float: left;list-style-type:none;margin-left: 80px;
   }
</style>
</head>
<body>
<!-- <a href="javascript:PreviewMytable();">打印预览</a> -->
<div class="fullscreen" style="margin:0 auto 30px">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="search-simple">
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_print">打印预览</button>
					<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
				</div>
			</div>
	</div>
<!-- 	------------------------------------------报销 封面开始----------------------------------------------------------- -->
<div id="div3">
	
	<h2 id="div1" style="text-align: center;">${organizationname}差旅费报销封面</h2>
		<TABLE border=1 width="100%" style="border-collapse:collapse;" bordercolor="#000000">
		  <tr>
		    <TD width="10%" style="height:70px;text-align: center;"><b>项目名称</b></TD>
		    <TD width="10%"  style="padding-left: 8px;">${xmlist[0].xmmc}</TD>
		    <TD width="10%" style="height:70px;text-align: center;"><b>开支内容</b></TD>
		    <TD width="20%"  style="padding-left: 8px;">${printinfolist.KZNR}</TD>
		    <TD width="20%" style="height:70px;text-align: center;"><b>单据张数</b></TD>
			<TD width="30%" style="text-align: center;">${organizationname1.FJZZS}张</TD>
		  </tr>
		  <tr>
		    <TD rowspan="${length}" colspan="2" style="height:100px;width:20%;text-align: center;"><b>共计报销金额人民币<br>（大写）</b></TD>
		    <TD rowspan="${length}" colspan="2" id="456" style="text-align: center;" id="zje" ></TD>
		    <TD colspan="1" id="" style="text-align: center;"><b>差旅费</b></TD>
		    <TD colspan="1" id="" style="text-align: center;">金额￥${printinfolist.BXZJE}</TD>
		  </tr>
		  <c:forEach var="jjMap1" items="${jjMap1}">
		  <tr>
		  	<td colspan="1"  id="" style="text-align: center;">${jjMap1.FYMC}</td>
		  	<td colspan="1"  id="" class="je" style="text-align: center;">${jjMap1.BXJE}</td>
		  </tr>
		  </c:forEach>
		 
		</TABLE>
		<TABLE border=1  width="100%"  style="border-collapse:collapse; margin-top:-1px" bordercolor="#000000">
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
		<div style="text-align: right;font-size:14px"><span><b>打印日期</b>：${time}</span></div>
</div>
<!-- 	------------------------------------------差旅报销单开始---------------------------------------------------------- -->
<div id="ccbxd">
	<div class="container-fluid" id="titleh" > 
	  <h2 id="div1" style="text-align: center;">${organizationname}差旅费报销单</h2>
	    <div class="btn-group pull-right" style="float: right;margin-top: 20px;font-size:14px" role="group">
	      <b>附件</b> ${clfbxzb.fjzzs}张        
	    </div>
	    <div style="position:absolute;margin-top: 20px;font-size:14px">        	 
                                <b>填报日期</b>：${clfbxzb.czrq}
	    </div>
	</div>
<div class="container-fluid"  id="tbodyy">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" border="1"  style="border-collapse:collapse;width:100%;" bordercolor="#000000">
		        	<thead>
				        <tr style="height:40px">
				            <th colspan="2" style="text-align:center;">姓名</th>
				            <td style="text-align:center;">${clfbxzb.sqrmc}</td>
				            <th colspan="2" style="text-align:center;">工作部门</th>				            
				            <td colspan="1" style="text-align:center;">${clfbxzb.szbmmc}</td>
				             <th colspan="2" style="text-align:center;">出差事由</th>	
				            <td  colspan="10" style="text-align:center;" >${clfbxzb.ccsy}</td> 
				        </tr>
				        <tr style="height:40px">
				            <th colspan="3" style="text-align:center; " id="tr_pzrq">起始时间</th>
				            <th colspan="3" style="text-align:center;" id="tr_kmbh">到达时间</th>
				            <th rowspan="3" style="text-align:center;width:6%" id="tr_zy">飞机票</th>				            
				            <th rowspan="3" style="text-align:center;width:6% " id="tr_kmmc">火车票</th>
				             <th rowspan="3" style="text-align:center;width:5%" id="tr_kmbh">出租车票</th>	
				            <th rowspan="3" style="text-align:center;width:6%" id="tr_dfje">汽车票</th>	 
				            <th rowspan="3" style="text-align:center;width:5%" id="tr_pzrq">培训</th>
				            <th rowspan="3" style="text-align:center;width:5%" id="tr_kmbh">会议</th>
				            <th rowspan="3" style="text-align:center;width:5%" id="tr_zy">其它</th>
				            <th  colspan="4" style="text-align:center;" >生活补助</th>
				            <th colspan="2" rowspan="2" style="text-align:center;width:6%">住宿费</th>
				        </tr>
				        
				        <tr style="height:40px"> 
				            <th rowspan="2" style="text-align:center;width:2%" id="tr_pzrq">月</th>
				            <th rowspan="2" style="text-align:center;width:6%" id="tr_kmbh">日</th>
				            <th rowspan="2" style="text-align:center;width:15%" id="tr_zy">起点</th>				            
				            <th rowspan="2" style="text-align:center;width:2%" id="tr_pzrq">月</th>
				            <th rowspan="2" style="text-align:center;width:6%" id="tr_kmbh">日</th>
				            <th  rowspan="2" style="text-align:center;width:15%" id="tr_zy">终点</th>	
				             <th colspan="2" rowspan="2" style="text-align:center;width:5%" id="tr_kmmc">教师</th>
				             <th colspan="2" rowspan="2" style="text-align:center;width:5%" id="tr_kmbh">学生</th> 
				          			            		    
				        </tr>
				       <!--  <tr>
				        <th style="text-align:center;width: 45px" id="tr_pzrq">金额</th>
				            <th style="text-align:center;width: 45px" id="tr_kmbh"></th>
				            <th style="text-align:center;width: 45px" id="tr_zy">天</th>				            
				            <th style="text-align:center;width: 45px " id="tr_kmmc">金额</th>
				        </tr> -->
					</thead>
				    <tbody>
				    <c:forEach var="mxList" items="${mxList}">
				   <tr style="height:40px">
				  <td class="kssjyear">${mxList.kssj}</td>
				   <td class="kssjmonth">${mxList.kssj}</td>
				   <td>${mxList.cfdd}</td>
				 <td class="jssjyear">${mxList.jssj}</td>
				   <td class="jssjmonth">${mxList.jssj}</td>
				   <td>${mxList.province}${mxList.city }</td>
				  <td class="fj" style="text-align:right">${mxList.fjp}</td>
				   <td class="hc" style="text-align:right">${mxList.hcp}</td>
				   <td class="czc" style="text-align:right">${mxList.czcp}</td>
				   <td class="qc" style="text-align:right">${mxList.qcp}</td>
				   <td class="px" style="text-align:right">${mxList.pxfy}</td>
				   <td class="hy" style="text-align:right">${mxList.hyfy}</td>
				   <td class="qt" style="text-align:right">${mxList.qtfy}</td>
				   <td colspan="2"  class="jst" style="text-align:right">${mxList.jsshbzje}</td>
				  <td colspan="2"  class="xst" style="text-align:right">${mxList.xsshbzje}</td>
				    <td class="zs" style="text-align:right">${mxList.zsfje}</td>
				    
				   </tr>
				   </c:forEach> 
				    
				    <tr style="height:40px">
				  <th style="text-align: center;" colspan="6">小&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;计</th>
				   <td id="fj" style="text-align:right"></td>
				   <td id="hc" style="text-align:right"></td>
				   <td id="czc" style="text-align:right"></td>
				   <td id="qc" style="text-align:right"></td>
				   <td id="px" style="text-align:right"></td>
				  <td id="hy" style="text-align:right"></td>
				   <td id="qt" style="text-align:right"></td>
				   <td colspan="2" id="jst" style="text-align:right"></td>
				   <td colspan="2" id="xst" style="text-align:right"></td>
				   <td id="zs" style="text-align:right"></td>
				   </tr> 
				   <tr>
				   <td colspan="13" id="zje" style="text-align: left;"><b>报销金额（大写）</b>：</td>
				   <td colspan="6" style="text-align: right;">￥：${clfbxzb.bxzje}（元）</td>
				   </tr>
				   </tbody>
				</table>					
		
			
<!-- <div class="bottom1"> -->
<!-- <b>校长（签字）：</b> -->
<%-- 	<c:if test="${not empty fjView }"> --%>
<%-- 	<span><img src="${fjView }" style="width: 10%;height: 50px;"></span> --%>
<%-- 	</c:if> --%>
<!-- &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; -->
<!-- <b>部门分管领导：</b> -->
<%-- 	<c:if test="${not empty fjView2 }"> --%>
<%-- 	<span><img src="${fjView2 }" style="width: 10%;height: 50px;"></span> --%>
<%-- 	</c:if> --%>
<!-- &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; -->
<!-- <b>财务处负责人：</b> -->
<%-- 	<c:if test="${not empty fjView3 }"> --%>
<%-- 		<span><img src="${fjView3 }" style="width: 10%;height: 50px;"></span> --%>
<%-- 	</c:if> --%>
<!-- &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; -->
<!-- <b>部门负责人：</b> -->
<%-- <c:if test="${not empty fjView4 }"> --%>
<%-- 	<span><img src="${fjView4 }" style="width: 10%;height: 50px;"></span> --%>
<%-- 	</c:if> --%>
<!-- &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; -->
<!-- <b>报销人：</b> -->
<%-- 	<c:if test="${not empty fjView5 }"> --%>
<%-- 	<span>	<img src="${fjView5 }" style="width: 10%;height: 50px;"></span> --%>
<%-- 	</c:if> --%>
<!-- &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; -->
<%-- <div style="float:right;"><p>打印日期：${time}</p></div> --%>
<!-- </div> -->
<br>
</div> 
</div>
		</div>
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
</div>
<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function(){
	init();
	init1();
	//console.log("++++"+"${mxList}");
	$("#btn_print").click(function(){
		PreviewMytable();
	});
	
})
function PreviewMytable(){
	var LODOP=getLodop(); 
	var fontStyle="<style> table,td,th {font-size:14px;} </style>";
	 LODOP.PRINT_INITA("20mm","${dyzdbj}mm");
	LODOP.NEWPAGE();
    LODOP.ADD_PRINT_HTML(0,0,"${ymkd}mm","170mm",fontStyle+document.getElementById("div3").innerHTML);
	LODOP.NEWPAGE();
	LODOP.ADD_PRINT_HTML(0,0,"${ymkd}mm","170mm",fontStyle+document.getElementById("ccbxd").innerHTML);
	LODOP.NEWPAGE();
	LODOP.ADD_PRINT_HTML("-5mm",-80,"${ztdkd}mm","190mm",document.getElementById("div4").innerHTML); 
	LODOP.ADD_PRINT_BARCODE(0,"${txmbj}mm",200,70,"Code39","${organizationname1.DJBH}");
	LODOP.SET_PRINT_PAGESIZE(2,0,0,"A4");
	LODOP.PREVIEW();	
};	
function init(){
	var zje="${clfbxzb.bxzje}";
	$("#zje").text("报销金额（大写）："+smalltoBIG(parseFloat(zje)));
	xjje("fj","fj");
	xjje("hc","hc");
	xjje("czc","czc");
	xjje("qc","qc");
	xjje("px","px");
	xjje("hy","hy");
	xjje("qt","qt");
	xjje("jsj","jsj");
	xjje("xsj","xsj");
	/* xjje("zs","zs"); */
	xjje("jst","jst");
	xjje("xst","xst");
	xjts("fjs","fjs");
	sjcl();
}
function init1(){
	var zje= "${organizationname1.BXZJE}";
	$("#456").text((""+smalltoBIG(parseFloat(zje))).replace("undefined", "零").replace("undefined", "零"));
}
function sjcl(){
	$.each($(".kssjyear"),function(){
		var kssjMonth=$(this).text();
		$(this).text(kssjMonth.substring(5,7));
	});
	$.each($(".kssjmonth"),function(){
		var kssjDay=$(this).text();
		$(this).text(kssjDay.substring(8));
	});
	$.each($(".jssjyear"),function(){
		var jssjMonth=$(this).text();
		$(this).text(jssjMonth.substring(5,7));
	});
	$.each($(".jssjmonth"),function(){
		var jssjDay=$(this).text();
		$(this).text(jssjDay.substring(8));
	});
	
	
	
	//alert();
}
//返回按钮
$("#btn_back").click(function(e){
	window.history.back(-1);
	//location.href="${ctx}/wsbx/ccbx/goCcbxListPage?mkbh=${param.mkbh}";
});
function xjje(className,xjName){
	var num=0;
	
	$.each($("[class="+className+"]"),function(){
		var je=$(this).text();
		if(je==''){
			je=0;
		}
		num+=parseFloat(je);
		
	})
	$("#"+xjName+"").text(num.toFixed(2));
}
function xjts(className,xjName){
	var num=0;
	$.each($("[class="+className+"]"),function(){
		var ts=$(this).text();
		if(ts==''){
			ts=0;
		}
		num+=parseFloat(ts);
		
	})
	$("#"+xjName+"").text(num);
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
</html>