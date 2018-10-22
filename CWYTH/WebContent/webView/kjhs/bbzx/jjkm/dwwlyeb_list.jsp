<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	tr td{
		line-height:30px !important;
		text-align: right;
		}
	tr th{
		text-align:center;
	}
	.left{
	text-align: left !important;
	}
	#kmmc{
	text-align: left !important;
	}
	#kjkmbh{
	text-align: left !important;
	}
	#kjkmmc{
	text-align: left !important;
	}
	#fx{
	text-align:center;
	}
</style>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="height: 30px;">
		<form id="myform" class="form-inline" action="">
			<div class="box-header clearfix">
			</div>
    		<div class="search-simple">
    			<div class="form-group" style="width:350px;">
					<label>会计期间</label>
					<input type="text" class="form-control input-radius year" style="width: 80px!important;" name="year" value="${year}"/>
					年
					<select class="form-control input-radius select" name="startMonth" style="width:50px;"> 
						<c:forEach var="months" items="${months}">
							<option value="${months.month}">${months.month}</option>
						</c:forEach>
 					</select>
 					月&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;
 					<select class="form-control input-radius select" name="endMonth" style="width:50px;">
						<c:forEach var="months" items="${months}">
							<option value="${months.month} " <c:if test="${mm eq months.month}">selected</c:if> >${months.month}</option>
						</c:forEach>
					</select>
					月
				</div>
				<div class="input-group">
					<label>包含全部未记账凭证</label>
					<input type="radio" class="" name="jzpz" value="1" checked  > 是 &nbsp; &nbsp; &nbsp;
					<input type="radio" class=""  name="jzpz" value="0" > 否
				</div>
				<button type="button" class="btn btn-primary" id="btn_chaxun"><i class="fa icon-chaxun"></i>查询</button>
				
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more" style="width: 450px">
						<div class="form-group">
							<label>会计科目</label>
							<br>
							<input  type="text" name="kmbh1" types="ToBhss" id="txt_km" class="input_info  form-control" style="width:130px;" placeholder="科目编号" value=""  />
							<button type="button" id="btn_kjkm" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group">
							<label>单位名称</label>
							<br>
							<input  type="text" name="dwmc"  class="input_info  form-control" style="width:130px;" placeholder="单位名称" value=""  />
						</div>
						<div class="form-group">
							<label>起止会计科目</label>
							<br>
							<input   type="text" name="kmbh2" types="BHL" id="txt_km1" class="input_info  form-control" style="width:130px;" placeholder="科目编号" value=""  />
							<button type="button" id="btn_kjkm1" class="btn btn-link ">选择</button>
							<label>至</label>
							<input   type="text" name="kmbh3"  types="BHH" id="txt_km2" class="input_info  form-control" placeholder="科目编号" style="width:130px;" value=""  />
							<button type="button" id="btn_kjkm2" class="btn btn-link ">选择</button>
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_gjchaxun" >
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
<!-- 								<button type="button" class="btn btn-default" id="btn_cancel"> -->
<!-- 									取 消 -->
<!-- 								</button> -->
							</div>
						</div>
					</div>
				</div>
				
				<div class="btn-group pull-right" role="group">
<!-- 					<button class="btn btn-default" type="button" id="btn1111">综合查询</button> -->
	                <button class="btn btn-default" type="button" id="dyyl">打印</button>
	                <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid" id="print_div" style="padding-bottom: 30px;">
		<div class='responsive-table'>
			<div class='scrollable-area' style="overflow-x:auto;">
			<div id="print_title">
				<h2 style="text-align:center;">单&nbsp;位&nbsp;往&nbsp;来&nbsp;余&nbsp;额&nbsp;表</h2>
				<div style="text-align:left;font-size:12px;float:left;width:33%">&emsp;</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：<span id="span_date">${date}</span></div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
				</div>
		        <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom: 50px;" border="0" bordercolor="#000000">		        	
					<thead>
		 				<tr  id="header">
		 					<th rowspan="2">科目编号</th>
				            <th rowspan="2">科目名称</th>
						    <th rowspan="2">单位编号</th>
						    <th rowspan="2">单位名称</th>
						    <th colspan="2">期初余额</th>
						    <th colspan="2">本期发生</th>
						    <th colspan="2">期末余额</th>
			   			</tr>
			   			<c:if test="${mbmc == '1'}">
				   			<tr>
							    <th style="width:5%;">方向</th>
							    <th>余额</th>
							    <th>借方</th>
							    <th>贷方</th>
							    <th style="width:5%;">方向</th>
							    <th>余额</th>
				   			</tr>
			   			</c:if>
			   			<c:if test="${mbmc == '2'}">
				   			<tr>
							    <th>借方</th>
							    <th>贷方</th>
							    <th>借方</th>
							    <th>贷方</th>
							    <th>借方</th>
							    <th>贷方</th>
				   			</tr>
			   			</c:if>
					</thead>
					<tbody>
					</tbody>
					<tfoot>
				   <tr class="hide" style="text-align:right;">
				   <td width="100%" colspan="10">
				   <font tdata="PageNO" format="00" >第##页，</font><font tdata="PageCount" format="00">共##页</font>
				   </td>
				   </tr>
				   </tfoot>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%> 
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function(){
// 	$("#btn_chaxun").click();


//高级查询，科目弹窗
   $(document).on("click","[id=btn_kjkm]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId=km&controlId1=txt_km&flag=zhcx","科目信息","920","630");
	 });
	 $(document).on("click","[id=btn_kjkm1]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km1&controlId=txt_km1&flag=zhcx","科目信息","920","630");
	 });
	 $(document).on("click","[id=btn_kjkm2]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km2&controlId=txt_km2&flag=zhcx","科目信息","920","630");
	 });
	 
	$(document).on("click","#btn_chaxun",function(){
// 	$("#btn_chaxun").click(function(){
		//会计期间的验证
		var year = $("[name='year']").val();
		if(year==""){
			alert("会计期间年份不能为空!");
			return;
		}
		var startMonth = $("[name='startMonth']").val();
		var endMonth = $("[name='endMonth']").val();
		if(startMonth==""||endMonth===""){
			alert("会计期间月份不能为空!");
			return;
		}
		if(Number(startMonth)>Number(endMonth)){
			alert("会计期间起始月份不能大于截止月份!");
			return;
		}
		var jsonStr = $("#myform").serialize();
// 		var json = $("#myform").serializeObject("start","end");  //json对象				
//   		var jsonStr = JSON.stringify(json);  //json字符串
  		var datestr = year+"年"+startMonth+"月至"+endMonth+"月";
  		getTable(jsonStr,datestr)
	});
	$("#btn_chaxun").click();
});
	
//综合查询
$("#btn_gjchaxun").click(function(){
	var year = $("[name='year']").val();
	if(year==""){
		alert("会计期间年份不能为空!");
		return;
	}
	var startMonth = $("[name='startMonth']").val();
	var endMonth = $("[name='endMonth']").val();
	if(startMonth==""||endMonth===""){
		alert("会计期间月份不能为空!");
		return;
	}
	if(Number(startMonth)>Number(endMonth)){
		alert("会计期间起始月份不能大于截止月份!");
		return;
	}
	var jsonStr = $("#myform").serialize();
	var datestr = year+"年"+startMonth+"月至"+endMonth+"月";
	getTable(jsonStr,datestr);
	//点击查询后，自动隐藏高级查询区域
	if($("#btn_search_more").length > 0){
    	$("#btn_search_more").removeClass("btn-search-more");
   		$(".search-more").css("display", "none");
	}
});
function getTable(jsonStr,datestr){ 
	var mbmc = "${mbmc}";//模版表的模版名称，如果是1，显示模版一，如果是2，显示模版二
	 $.ajax({
			type:"post",
			async : false, 
			url:"${ctx}/dwwlyeb/getPageList",
			data:jsonStr+"&treebh=${param.dm}",
			dataType: "json",
			success:function(val){
				$("#span_date").html(datestr);
				$("tbody").html("");
				var mxList = val.mxList;
				for (var i=0;i<mxList.length;i++){
					 var KMBH=mxList[i].KMBH;if(KMBH==null) KMBH='';
					 var KMMC=mxList[i].KMMC;if(KMMC==null) KMMC='';
					 var KJKMBH=mxList[i].KJKMBH;if(KJKMBH==null) KJKMBH='';
					 var KJKMMC=mxList[i].KJKMMC;if(KJKMMC==null) KJKMMC='';
					 var QCFX=mxList[i].QCFX;if(QCFX==null) QCFX='';
					 var QCYE=mxList[i].QCYE;if(QCYE==null) QCYE='';
					 var BQJF=mxList[i].BQJF;if(BQJF==null) BQJF='';
					 var BQDF=mxList[i].BQDF;if(BQDF==null) BQDF='';
					 var QMFX=mxList[i].QMFX;if(QMFX==null) QMFX='';
					 var QMYE=mxList[i].QMYE;if(QMYE==null) QMYE='';
					 var QCJF=mxList[i].QCJF;if(QCJF==null) QCJF='';
					 var QCDF=mxList[i].QCDF;if(QCDF==null) QCDF='';
					 var QMJF=mxList[i].QMJF;if(QMJF==null) QMJF='';
					 var QMDF=mxList[i].QMDF;if(QMDF==null) QMDF='';
				if(mbmc=='1'){
					 $("tbody").append(
								"<tr name='guid'>"+
								"<td id='kjkmbh'  name='kjkmbh' value='"+KJKMBH+"' kjkmbh='"+KJKMBH+"' style='text-align:left;padding:0px 1px'>"+KJKMBH+"</td>"+
								"<td id='kjkmmc'  name='kjkmmc' value='"+KJKMMC+"' kjkmmc='"+KJKMMC+"' style='text-align:left;padding:0px 1px'>"+KJKMMC+"</td>"+
								"<td id='kmbh' name='kmbh' value='"+KMBH+"' kmbh='"+KMBH+"' style='text-align:left;padding:0px 1px'>"+KMBH+"</td>"+
								"<td id='kmmc'  name='kmmc' value='"+KMMC+"' kmmc='"+KMMC+"' style='text-align:left;padding:0px 1px'>"+KMMC+"</td>"+
								"<td id='fx' style='text-align:center;padding:0px 1px'>"+QCFX+"</td>"+
								"<td  style='text-align:right;padding:0px 1px'>"+QCYE+"</td>"+
								"<td  style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
								"<td  style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
								"<td id='fx' style='text-align:center;padding:0px 1px'>"+QMFX+"</td>"+
								"<td  style='text-align:right;padding:0px 1px'>"+QMYE+"</td>"+
								"</tr>"
					);
				}
				if(mbmc=='2'){
					 $("tbody").append(
								"<tr name='guid'>"+
								"<td id='kjkmbh'  name='kjkmbh' value='"+KJKMBH+"' kjkmbh='"+KJKMBH+"' style='text-align:left;padding:0px 1px'>"+KJKMBH+"</td>"+
								"<td id='kjkmmc'  name='kjkmmc' value='"+KJKMMC+"' kjkmmc='"+KJKMMC+"' style='text-align:left;padding:0px 1px'>"+KJKMMC+"</td>"+
								"<td id='kmbh' name='kmbh' value='"+KMBH+"' kmbh='"+KMBH+"' style='text-align:left;padding:0px 1px'>"+KMBH+"</td>"+
								"<td id='kmmc'  name='kmmc' value='"+KMMC+"' kmmc='"+KMMC+"' style='text-align:left;padding:0px 1px'>"+KMMC+"</td>"+
								"<td id='fx' style='text-align:right;padding:0px 1px'>"+QCJF+"</td>"+
								"<td  style='text-align:right;padding:0px 1px'>"+QCDF+"</td>"+
								"<td  style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
								"<td  style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
								"<td id='fx' style='text-align:right;padding:0px 1px'>"+QMJF+"</td>"+
								"<td  style='text-align:right;padding:0px 1px'>"+QMDF+"</td>"+
								"</tr>"
					);
				}
				}
				var data = val.zjList[0];
				$("tbody").append("<tr class='nojs' id='one'>"+
						"<td colspan='4' rowspan='2' style='text-align:center;padding:0px 1px'>合计</td>"+
						"<td style='text-align:center;padding:0px 1px'>借</td>"+
						"<td id='qcj' style='text-align:right;padding:0px 1px'>"+(data.QCJ==null||data.QCJ==0.00?"":data.QCJ)+"</td>"+
						"<td rowspan='2' id='bqj' style='text-align:right;padding:0px 1px'>"+(data.BQJ==null||data.BQJ==0.00?"":data.BQJ)+"</td>"+
						"<td rowspan='2' id='bqd' style='text-align:right;padding:0px 1px'>"+(data.BQD==null||data.BQD==0.00?"":data.BQD)+"</td>"+
						"<td style='text-align:center;padding:0px 1px'>借</td>"+
						"<td id='qmj' style='text-align:right;padding:0px 1px'>"+(data.QMJ==null||data.QMJ==0.00?"":data.QMJ)+"</td>"+
						"</tr>"+
						"<tr class='nojs' id='two'>"+
						"<td style='text-align:center;padding:0px 1px'>贷</td>"+
						"<td id='qcd' style='text-align:right;padding:0px 1px'>"+(data.QCD==null||data.QCD==0.00?"":data.QCD)+"</td>"+
						"<td style='text-align:center;padding:0px 1px'>贷</td>"+
						"<td id='qmd' style='text-align:right;padding:0px 1px'>"+(data.QMD==null||data.QMD==0.00?"":data.QMD)+"</td>"+
						"</tr>"
						);
// 				$("tbody").append("<tr class='nojs' id='one'>"+
// 						"<td colspan='4' rowspan='2' style='text-align:center;'>合计</td>"+
// 						"<td rowspan='2' id='qc' style='text-align:right;'>"+(data.QCYE==null?"":data.QCYE)+"</td>"+
// 						"<td rowspan='2' id='bqj' style='text-align:right;'>"+(data.BQJ==null?"":data.BQJ)+"</td>"+
// 						"<td rowspan='2' id='bqd' style='text-align:right;'>"+(data.BQD==null?"":data.BQD)+"</td>"+
// 						"<td rowspan='2' id='qm' style='text-align:right;'>"+(data.QMYE==null?"":data.QMYE)+"</td>"+
// 						"</tr>"
// 						);
				
			},
		});	 
}
</script>
<script >
//刷新页面
function toUrl(params,date,gs){
	if(params){
		location.href = "${ctx}/dwwlyeb/jjkmList?date="+date;
	}
}


$(document).on("click",".cxslook",function(){
// $(".cxslook").click(function(){
	 var guid = $(this).parents("tr").find("[name='guid']").val();
	 var pzbh = $(this).parents("tr").find("[name=guid]").attr("pzbh");
	 var pzz = $(this).parents("tr").find("[name=guid]").attr("pzz");
	 pageSkip(target,"pzlr&pzz="+pzz+"&pzbh="+pzbh);
		
})
//导出Excel
$(document).on("click","#btn_export",function(){
	
	var kmbh = [];
	var checkbox = $("#mydatatables").find("[name='kmbh']").filter(":checked");
	if(checkbox.length > 0){
	checkbox.each(function(){
		kmbh.push($(this).val());
	});
	kmbh = kmbh.join("','");
	}else{
		kmbh = "";
	}
	$.ajax({
			type : "post",
			data : $("#myform").serialize()+"&treebh=${param.dm}",
			url : "${ctx}/dwwlyeb/expExcel2",
			success : function(val) {
				var data = JSON.getJson(val);
				console.log(val+"=====val======="+val[0]+"dffff"+data.url);
	//				alert(kmbh);
			 FileDownload("${ctx}/file/fileDownload","单位往来余额表.xls",data.url);
		   }
	}); 
});

$(document).on("click","#dyyl",function(){
//  $("#dyyl").click(function(){
 	PreviewMytable();
 });


 function PreviewMytable(){
	 var LODOP=getLodop();  
		LODOP.PRINT_INITA("7%","5%");
		LODOP.NEWPAGE();
		var strStyle="<style> table,td,th {border-collapse:collapse;font-size:12px} table{width:100%!important} thead tr th,tbody tr td{border:1px solid #000;}</style>"
		LODOP.ADD_PRINT_HTM(0,0,"94%","87%",document.getElementById("print_title").innerHTML);
		LODOP.ADD_PRINT_TABLE(0,0,"94%","87%",strStyle+document.getElementById("print_div").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
		LODOP.SET_PRINT_PAGESIZE(2, 0,0 ,"A4");
		LODOP.PREVIEW();	
	};



	$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
		var kmbh = $(this).find("[name='kmbh']").attr("value");//往来单位的编号
// 		var kmmc = $(this).find("[name='kmmc']").attr("value");//往来单位的名称
		var kjbh = $(this).find("[name='kjkmbh']").attr("value");//会计科目的编号
		var kjmc = $(this).find("[name='kjkmmc']").attr("value");//会计科目的名称
		var year = $("[name='year']").val();
		if(year==""){
			alert("会计期间年份不能为空!");
			return;
		}
		var startMonth = $("[name='startMonth']").val();
		var endMonth = $("[name='endMonth']").val();
		if(startMonth==""||endMonth===""){
			alert("会计期间月份不能为空!");
			return;
		}
		if(Number(startMonth)>Number(endMonth)){
			alert("会计期间起始月份不能大于截止月份!");
			return;
		}
	    var starttime = year+"-"+startMonth;
	    var endtime = year+"-"+endMonth;
	    var bbqj = starttime+"月至"+endtime+"月";
		doOperate("${ctx}/wldwmxz1/toUrl?kmbh="+kjbh+"&kmmc="+kjmc+"&dm="+kmbh+"&bbqj="+bbqj+"&StartMonth="+startMonth+"&endMonth="+endMonth+"&pz=1&dwye=dwye&ye=yes");
	});


</script>
</body>
</html>