<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印Demo</title>
<%@include file="/static/include/public-list-js.inc"%>
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
	margin-left:-4%;
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
<a id="btn_back" href="javascript:void(0)">返回</a>

<!-- <div class="div1" id="div1"> -->
<!-- 	<div > -->
<!-- 		<div class="pull-right"> -->
<!-- 			<button type="button" class="btn btn-default" id="btn_print">打印预览</button> -->
<!-- 			<button type="button" class="btn btn-default" id="btn_back">返回</button> -->
<!--         </div> -->
<!-- 	</div> -->
<!-- ---------------------------------------------------------------------------------------------------	 -->
<div id="div3">
	
	<h1 id="div1" style="text-align: center;">${organizationname}&nbsp;报&nbsp;销&nbsp;封&nbsp;面</h1>
		<TABLE border=1 cellSpacing=0 cellPadding=1 width="90%" style="border-collapse:collapse;margin-left: 5%" bordercolor="#333333">
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;"><b>开支内容</b></TD>
		    <TD colspan="2" style="padding-left: 8px;">${printinfolist.KZNR}</TD>
		    <TD style="height:70px;width:180px;text-align: center;"><b>单据张数</b></TD>
			<TD style="text-align: center;">${printinfolist.FJZZS}张</TD>
		  </tr>
		  <tr >
		    <TD rowspan="${length}" style="height:140px;width:20%;text-align: right;"><b>共计报销金额人民币（大写）</b></TD>
		    <TD rowspan="${length}" colspan="1" id="456" style="text-align: center;" id="zje" ></TD>
		    <TD rowspan="${length}" colspan="1" id="123" style="text-align: center;">￥${printinfolist.BXZJE}</TD>
		    <TD colspan="1"  id="" style="text-align: center;"><b>${printinfolist.fymc }</b></TD>
		    <TD colspan="1" id="" style="text-align: center;"><b>金额￥${printinfolist.BXZJE}（元）</b></TD>
		  </tr>
		  <c:forEach var="jjMap1" items="${jjMap}">
		  <tr>
		  	<td colspan="1"  id="" style="text-align: center;">${jjMap1.FYMC}</td>
		  	<td colspan="1"  id="" class="je" style="text-align: center;">${jjMap1.BXJE}</td>
		  </tr>
		  </c:forEach>
		  <tr>
		    <TD style="height:70px;width:20%;text-align: center;"><b>院领导审批意见&emsp;</b></TD>
		    <TD colspan="2"  style="height:70px;width:20%;text-align: center;"><b>部门负责人审批意见&emsp;</b></TD>
		    <TD style="height:40px;width:20%;text-align: center;"><b>会计审核意见&emsp;</b></TD>
		    <TD style="height:70px;width:20%;text-align: center;"><b>经办人&emsp;</b></TD>
		  </tr>
		  <tr style="height: 150px;">
		    <TD style="height:150px;width:20%;">
		    	<div style="height:100%;">
		    		<span style="width:100%;">${yxMap.SHYJ }</span><br>
		    		<div style="text-align:right;padding:20% 3% 2% 20% !important;">
		    		<span ><img src="${fjView }" style="width: 90px;height: 50px;"></span><br>
		    		</div>
		    		<div style="text-align:right;padding:0px 5% 1% 20% !important">
		    		<span style="text-align:right"><fmt:formatDate value="${yxMap.jdsj }" pattern="yyyy-MM-dd"/></span>
		    		</div>
		    	</div>
		    </TD>
		    <TD colspan="2"  style="height:150px;width:20%;">
		    	 <div style="height:100%;">
		    		<span style="width:100%;">${bmMap.SHYJ }</span><br>
		    		<div style="text-align:right;padding:20% 3% 2% 20% !important;">
		    		<span ><img src="${fjView2 }" style="width: 90px;height: 50px;"></span><br>
		    		</div>
		    		<div style="text-align:right;padding:0px 5% 1% 20% !important">
		    		<span style="text-align:right"><fmt:formatDate value="${bmMap.jdsj }" pattern="yyyy-MM-dd"/></span>
		    		</div>
		    	</div>
		   </TD>
		    <TD style="height:150px;width:20%;">
		    	<div style="height:100%;">
		    		<span style="width:100%;">${kjMap.SHYJ }</span><br>
		    		<div style="text-align:right;padding:20% 3% 2% 20% !important;">
		    		<span ><img src="${fjView3 }" style="width: 90px;height: 50px;"></span><br>
		    		</div>
		    		<div style="text-align:right;padding:0px 5% 1% 20% !important">
		    		<span style="text-align:right"><fmt:formatDate value="${kjMap.jdsj }" pattern="yyyy-MM-dd"/></span>
		    		</div>
		    	</div>
		    </TD>
		    <TD style="height:150px;width:20%;text-align: center;"><span>${printinfolist.BXRMC}</span></TD>
		  </tr>
		</TABLE>
		<div style="text-align: right; margin-right:5%;"><span>打印日期：${time}</span></div>
</div>

<div class="container-fluid" id="titleh" style="margin-top: 20px"> 
	<div> 
		<center>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                <h3>${organizationname}&nbsp; &nbsp; &nbsp;差 &nbsp;旅 &nbsp;费 &nbsp;报 &nbsp;销 &nbsp;单<h3>
               </td>
	        </tr>
	    </center>
	    <div>
	      <center>_________________________________________________________________________________________________</center>
	    </div>
	  </div>
	    <div class="btn-group pull-right" style="float: right;margin-top: 20px" role="group">
	       <b>  附件</b> &ensp;${clfbxzb.fjzzs} &ensp;<b>张 </b>        
	    </div>
	  
	    <div style="position: absolute;margin-top: 20px">        	 
                                <b>填报日期：</b>&ensp;&ensp;&ensp;${clfbxzb.czrq}&ensp;&ensp;&ensp;
	    </div>
	    
	</div>


<div class="container-fluid"  id="tbodyy">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;margin:0 auto">
		        	<thead>
				        <tr>
				            
				            <th colspan="2" style="text-align:center;">姓名</th>
				            <th colspan="4"style="text-align:center;">${clfbxzb.sqrmc}</th>
				            <th colspan="1" style="text-align:center;">工作部门</th>				            
				            <th colspan="3" style="text-align:center; width: 150px">${clfbxzb.szbmmc}</th>
				             <th colspan="3" style="text-align:center;">出差事由</th>	
				            <th  colspan="4" style="text-align:center;" >${clfbxzb.ccsy}</th>	 
				            <th colspan="2" style="text-align:center; width: 150px">住宿费</th>
				             
				        </tr>
				        <tr>
				            <th colspan="3" style="text-align:center; " id="tr_pzrq">起始时间</th>
				            <th colspan="3" style="text-align:center;" id="tr_kmbh">到达时间</th>
				            <th rowspan="3" style="text-align:center;width: 75px" id="tr_zy">飞机票</th>				            
				            <th rowspan="3" style="text-align:center; width: 75px" id="tr_kmmc">火车票</th>
				             <th rowspan="3" style="text-align:center;width: 75px" id="tr_kmbh">出租车票</th>	
				            <th rowspan="3" style="text-align:center;width: 75px" id="tr_dfje">汽车票</th>	 
				            <th rowspan="3" style="text-align:center; width: 75px" id="tr_pzrq">培训</th>
				            <th rowspan="3" style="text-align:center;width: 75px" id="tr_kmbh">会议</th>
				            <th rowspan="3" style="text-align:center;width: 75px" id="tr_zy">其它</th>
				            <th  colspan="4" style="text-align:center;" >生活补助</th>
				              <th rowspan="3" style="text-align:center;width: 45px" id="tr_dfje">张</th>	 
				            <th rowspan="3" style="text-align:center; width: 75px" id="tr_pzrq">金额</th>	    
				        </tr>
				        
				        <tr> 
				            <th rowspan="2" style="text-align:center;width: 80px" id="tr_pzrq">月</th>
				            <th rowspan="2" style="text-align:center;width: 80px" id="tr_kmbh">日</th>
				            <th rowspan="2" style="text-align:center;width: 150px" id="tr_zy">起点</th>				            
				            <th rowspan="2" style="text-align:center;width: 80px" id="tr_pzrq">月</th>
				            <th rowspan="2" style="text-align:center;width: 80px" id="tr_kmbh">日</th>
				            <th  rowspan="2" style="text-align:center;width: 150px" id="tr_zy">终点</th>	
				             <th colspan="2" style="text-align:center; width: 150px" id="tr_kmmc">教师</th>
				             <th colspan="2" style="text-align:center;" id="tr_kmbh">学生</th>	
				          			            		    
				        </tr>
				        <tr>
				        <th style="text-align:center;width: 45px" id="tr_pzrq">天</th>
				            <th style="text-align:center;width: 45px" id="tr_kmbh">金额</th>
				            <th style="text-align:center;width: 45px" id="tr_zy">天</th>				            
				            <th style="text-align:center;width: 45px " id="tr_kmmc">金额</th>
				        </tr>
					</thead>
				    <tbody>
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
				  <td style="text-align: center;" colspan="6">小&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;计</td>
				   <td id="fj"></td>
				   <td id="hc"></td>
				   <td id="czc"></td>
				   <td id="qc"></td>
				   <td id="px"></td>
				  <td id="hy"></td>
				   <td id="qt"></td>
				   <td id="jst"></td>
				   <td id="jsj"></td>
				   <td id="xst"></td>
				   <td id="xsj"></td>
				   <td id="fjs"></td>
				   <td id="zs"></td>
				   </tr> 
				   <tr>
				   <td colspan="13" id="zje" style="text-align: left;"><b>报销金额（大写）：</b></td>
				   <td colspan="6" style="text-align: left;">￥：${clfbxzb.bxzje}（元）</td>
				   </tr>
				   </tbody>
				</table>					
		</div>
			
		
			
<div class="bottom1">
<div><b>校长（签字）：</b><span>
	<c:if test="${not empty fjView }">
		<img src="${fjView }" style="width: 90px;height: 50px;"></span>
	</c:if>
</div>&emsp;
<div><b>部门分管领导（签字）：</b><span>
	<c:if test="${not empty fjView2 }">
		<img src="${fjView2 }" style="width: 90px;height: 50px;"></span>
	</c:if>
</div>&emsp;
<div><b>财务处负责人（签字）：</b><span>
	<c:if test="${not empty fjView3 }">
		<img src="${fjView3 }" style="width: 90px;height: 50px;"></span>
	</c:if>
</div>&emsp;
<div><b>部门负责人（签字）：</b><span>
	<c:if test="${not empty fjView4 }">
		<img src="${fjView4 }" style="width: 90px;height: 50px;"></span>
	</c:if>
</div>&emsp;
<div><b>报销人（签字）：</b><span>
	<c:if test="${not empty fjView5 }">
		<img src="${fjView5 }" style="width: 90px;height: 50px;"></span>
	</c:if>
</div>
<div style="float:right;margin-right:-40%;">打印日期：${time}</div>
</div>
</div>
		</div>
<!-- 	</div> -->
<!-- ---------------------------------------------------------------------------------------------------- -->


<div id="div4" style="">
<!-- 	<div style="width:500px;margin-left:35%;"> -->
	<div id="div31" style="float:left;width: 100%;height: 90px;text-align: center;padding-left: 35%;"><h1 style="width:300px;text-align: center;border-bottom:1px solid;">单&nbsp;据&nbsp;粘&nbsp;贴&nbsp;单</h1></div>
	<div style="width:220px;position: absolute;right: 65px;top: 0px;float:left;height: 40px;text-align: center;margin-top: 50px;"><SPAN style="FONT-SIZE: 32px; FONT-FAMILY: C39HrP36DlTt; WHITE-SPACE: nowrap; LETTER-SPACING: 1px">[${organizationname1.DJBH}]</SPAN></div>
<!-- 	</div> -->
	<div style="width:90%;height:600px;margin: 20px 5% 0px 5%;border: 1px dashed;">
		<p><h2 style="text-align: center;">单据粘贴注意事项</h2></p>
		<p style="margin-left:15%;">1.请将报销凭证沿装订线右侧，均匀、平整的粘贴在粘贴单上。</p>
		<p style="margin-left:15%;">2.粘贴单据时请尽量分类粘贴，或按报销时间、事项的顺序粘贴。</p>
		<p style="margin-left:15%;">3.会议通知、合同（报告）或其他标准A4纸大小规格的票据不用粘贴。</p>
		<p style="margin-left:15%;">4.凡取得增值税发票，绿色抵扣联不必粘贴，报销时单独送到财务处窗口即可。</p>
		<p style="margin-left:15%;">5.粘贴单据时注意事项可以被覆盖。</p>
<!-- 		<TABLE border=1 cellSpacing=0 cellPadding=1 width="100%" style="border-collapse:collapse;margin: 200px 0% 0px 0%;"" bordercolor="#333333"> -->
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
// 	location.href="${ctx}/kylbx/gowdbxListPage";
});
// 	var ze = 0.00;
// 	$.each($(".je"),function(){
// 		var val=$(this).val();
// 		if(val==""||val==0||isNaN(val)){
// 			val = 0.00;
// 		}
// 		ze = parseFloat(ze)+parseFloat(val);
// 		});
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印");
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
// 		LODOP.SET_PRINT_PAGESIZE(intOrient, PageWidth,PageHeight,strPageName)
        LODOP.ADD_PRINT_HTM(10,"5%","90%",1000,strStyle+document.getElementById("div3").innerHTML);
        LODOP.ADD_PRINT_HTM(128,"85px","85%",344,strStyle+document.getElementById("tbodyy").innerHTML);
	    LODOP.ADD_PRINT_HTM(36,"10%","85%",109,document.getElementById("titleh").innerHTML);
		
		LODOP.ADD_PRINT_HTM(10,"5%","90%",1000,strStyle+document.getElementById("div4").innerHTML);
// 		LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("div1").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",2);	
		LODOP.PREVIEW();	
	};	
	function init(){
		var zje= "${organizationname1.BXZJE}";
		$("#456").text((""+smalltoBIG(parseFloat(zje))).replace("undefined", "零").replace("undefined", "零"));
// 		$("#4561").text((""+smalltoBIG(parseFloat(zje))).replace("undefined", "零"));
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
	        s += "  " + (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + "  " + fraction[i]);//.replace(/零./, '');    
	    }    
// 	    s = s || '整';    
	    n = Math.floor(n);    
	  
	    for (var i = 0; i < unit[0].length ; i++)     
	    {    
	        var p = '';    
	        for (var j = 0; j < unit[1].length ; j++)     
	        {   
	        	if(n>0){
		            p = "  " + digit[n % 10] + "  " + unit[1][j] + p;    
		            n = Math.floor(n / 10);    
	        	}else{
	        		p = "    " + unit[1][j] + p;    
	        	}
	        }    
	        s = p  + unit[0][i] + s;    
	    }    
	    return head + s;    
	} 
</script>
		

</body>
</html>
