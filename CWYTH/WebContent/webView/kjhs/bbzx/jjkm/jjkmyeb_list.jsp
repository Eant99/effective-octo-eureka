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
	#kmbh{
	text-align: left;
	}
	#kmmc{
	text-align: left;
	}
	#fx{
	text-align:center;
	}
</style>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="height: 30px;">
		<form id="myform" class="form-inline" action="">
			<input type="hidden" name="kmbh" class="kmbh_click" val="">
			<input type="hidden" name="types" class="types" val="">
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
				<div class="form-group" style="width:260px;">
					<label>科目级次&nbsp;&nbsp;</label>
					<select class="form-control input-radius select" name="startJc">
					<option value="">请选择</option>
						<c:forEach items="${list}" var="jb1">
							<option value="${jb1.jb}">${jb1.jb}</option>
						</c:forEach>
					</select>
					至
					<select class="form-control input-radius select" name="endJc">
					<option value="">请选择</option>
						<c:forEach items="${list}" var="jb2">
							<option value="${jb2.jb}">${jb2.jb}</option>
						</c:forEach>
					</select>
				</div>
				<div class="input-group">
					<label>包含全部未记账凭证</label>
					<input type="radio" class="" name="jzpz" value="1" checked  > 是 &nbsp; &nbsp; &nbsp;
					<input type="radio" class=""  name="jzpz" value="0" > 否
				</div>
				<button type="button" class="btn btn-primary" id="btn_chaxun"><i class="fa icon-chaxun"></i>查询</button>
				
				
				
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
				<h2 style="text-align:center;">经&nbsp;济&nbsp;科&nbsp;目&nbsp;余&nbsp;额&nbsp;表</h2>
				<div style="text-align:left;font-size:12px;float:left;width:33%">&emsp;</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：<span id="span_date">${date}</span></div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
				</div>
		        <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom: 50px;" align="center" border="0" bordercolor="#000000">		        	
					<thead>
			   			<c:if test="${mbmc == '1'}">
			   				<tr  id="header">
			 					<th rowspan="2">经济科目编号</th>
							    <th rowspan="2">经济科目名称</th>
	<!-- 		 					<th rowspan="2">会计科目编号</th> -->
	<!-- 		 					<th rowspan="2">会计科目名称</th> -->
							    <th rowspan="2">期初余额</th>
							    <th colspan="2">本期发生</th>
							    <th rowspan="2">期末余额</th>
			   				</tr>
				   			<tr>
<!-- 							    <th>方向</th> -->
<!-- 							    <th>余额</th> -->
							    <th>借方</th>
							    <th>贷方</th>
<!-- 							    <th>方向</th> -->
<!-- 							    <th>余额</th> -->
				   			</tr>
			   			</c:if>
			   			<c:if test="${mbmc == '2'}">
			   				<tr  id="header">
			 					<th rowspan="2">经济科目编号</th>
							    <th rowspan="2">经济科目名称</th>
			 					<th rowspan="2">会计科目编号</th>
			 					<th rowspan="2">会计科目名称</th>
							    <th rowspan="1" colspan="2">期初余额</th>
							    <th colspan="2">本期发生</th>
							    <th rowspan="1" colspan="2">期末余额</th>
			   				</tr>
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
	$(".types").val("1");
	$(document).on("click","#btn_chaxun",function(){
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
	
});
function getTable(jsonStr,datestr){ 
	 var mbmc = "${mbmc}";//模版表的模版名称，如果是1，显示模版一，如果是2，显示模版二
	 var startJc = $("[name='startJc']").val();
	 var endJc = $("[name='endJc']").val();
	 if(endJc<startJc){
		 alert("开始科目级次必须小于结束科目级次!");
	 }
 	 $.ajax({
			type:"post",
			async : false, 
			url:"${ctx}/jjkmyeb/getPageList",
			data:jsonStr+"&treebh=${param.kmbh}&startJc="+startJc+"&endJc="+endJc,
			dataType: "json",
			success:function(val){
				$("#span_date").html(datestr);
				$("tbody").html("");
				var mxList = val.mxList;
				for (var i=0;i<mxList.length;i++){
					 var KMBH=mxList[i].KMBH;if(KMBH==null) KMBH='';
					 var KJKMBH=mxList[i].KJKMBH;if(KJKMBH==null) KJKMBH='';
					 var KJKMMC=mxList[i].KJKMMC;if(KJKMMC==null) KJKMMC='';
					 var KMMC=mxList[i].KMMC;if(KMMC==null) KMMC='';
					 var QCYE=mxList[i].QCYE;if(QCYE==null) QCYE='';
					 var BQJF=mxList[i].BQJF;if(BQJF==null) BQJF='';
					 var BQDF=mxList[i].BQDF;if(BQDF==null) BQDF='';
					 var QMYE=mxList[i].QMYE;if(QMYE==null) QMYE='';
					 var QCFX=mxList[i].QCFX;if(QCFX==null) QCFX='';
					 var QMFX=mxList[i].QMFX;if(QMFX==null) QMFX='';
					 var QCJF=mxList[i].QCJF;if(QCJF==null) QCJF='';
					 var QCDF=mxList[i].QCDF;if(QCDF==null) QCDF='';
					 var QMJF=mxList[i].QMJF;if(QMJF==null) QMJF='';
					 var QMDF=mxList[i].QMDF;if(QMDF==null) QMDF='';
					 if(mbmc=='1'){
					 $("tbody").append(
								"<tr name='guid'>"+
								"<td id='kmbh' name='kmbh' value='"+KMBH+"' kmbh='"+KMBH+"' style='padding:0px 1px'>"+KMBH+"</td>"+
								"<td id='kmmc' name='kmmc' value='"+KMMC+"' kmmc='"+KMMC+"' style='padding:0px 1px'>"+KMMC+"</td>"+
// 								"<td style='text-align:left;padding:0px 1px' id='kjkmbh' name='kjkmbh' value='"+KJKMBH+"'>"+KJKMBH+"</td>"+
// 								"<td style='text-align:left;padding:0px 1px' id='kjkmmc' name='kjkmmc' value='"+KJKMMC+"'>"+KJKMMC+"</td>"+								
// 								"<td style='text-align:center;padding:0px 1px'>"+QCFX+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+QCYE+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
// 								"<td style='text-align:center;padding:0px 1px'>"+QMFX+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+QMYE+"</td>"+
								"</tr>"
					);
					}
					 if(mbmc=='2'){
						 $("tbody").append(
									"<tr name='guid'>"+
									"<td id='kmbh' name='kmbh' value='"+KMBH+"' kmbh='"+KMBH+"' style='padding:0px 1px'>"+KMBH+"</td>"+
									"<td id='kmmc' name='kmmc' value='"+KMMC+"' kmmc='"+KMMC+"' style='padding:0px 1px'>"+KMMC+"</td>"+
									"<td style='text-align:left;padding:0px 1px' id='kjkmbh' name='kjkmbh' value='"+KJKMBH+"'>"+KJKMBH+"</td>"+
									"<td style='text-align:left;padding:0px 1px' id='kjkmmc' name='kjkmmc' value='"+KJKMMC+"'>"+KJKMMC+"</td>"+								
									"<td style='text-align:right;padding:0px 1px'>"+QCJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QCDF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QMJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QMDF+"</td>"+
									"</tr>"
						);
						}
				}
				var data = val.zjList[0];
				if(mbmc=='1'){
					$("tbody").append("<tr class='nojs' id='one'>"+
							"<td colspan='2' rowspan='2' style='text-align:center;padding:0px 1px'>合计</td>"+
							"<td rowspan='2' id='qc' style='text-align:right;padding:0px 1px'>"+(data.QCYE==null||data.QCYE==0.00?"":data.QCYE)+"</td>"+
							"<td rowspan='2' id='bqj' style='text-align:right;padding:0px 1px'>"+(data.BQJ==null||data.BQJ==0.00?"":data.BQJ)+"</td>"+
							"<td rowspan='2' id='bqd' style='text-align:right;padding:0px 1px'>"+(data.BQD==null||data.BQD==0.00?"":data.BQD)+"</td>"+
	// 						"<td rowspan='1'></td>"+
// 							"<td rowspan='1'></td>"+
							"<td  rowspan='2' id='qm' style='text-align:right;padding:0px 1px'>"+(data.QMYE==null||data.QMYE==0.00?"":data.QMYE)+"</td>"+
							"</tr>"
							);
				}
				if(mbmc=='2'){
					$("tbody").append("<tr class='nojs' id='one'>"+
							"<td colspan='4' rowspan='2' style='text-align:center;padding:0px 1px'>合计</td>"+
							"<td rowspan='2'></td>"+
							"<td rowspan='2' id='qc' style='text-align:right;padding:0px 1px'>"+(data.QCYE==null||data.QCYE==0.00?"":data.QCYE)+"</td>"+
							"<td rowspan='2' id='bqj' style='text-align:right;padding:0px 1px'>"+(data.BQJ==null||data.BQJ==0.00?"":data.BQJ)+"</td>"+
							"<td rowspan='2' id='bqd' style='text-align:right;padding:0px 1px'>"+(data.BQD==null||data.BQD==0.00?"":data.BQD)+"</td>"+
	// 						"<td rowspan='1'></td>"+
							"<td rowspan='1'></td>"+
							"<td  rowspan='2' id='qm' style='text-align:right;padding:0px 1px'>"+(data.QMYE==null||data.QMYE==0.00?"":data.QMYE)+"</td>"+
							"</tr>"
							);
				}
			},
		});	 
}

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
</script>
<script >
//刷新页面
function toUrl(params,date,gs){
	if(params){
		location.href = "${ctx}/jjkmyeb/jjkmList?date="+date;
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
// $("#btn_export").click(function() {
//		var kmbh = $("[name='kmbh']").val();
//		alert("hahah"+kmbh);
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
			data : $("#myform").serialize()+"&treebh=${param.kmbh}",
			url : "${ctx}/jjkmyeb/expExcel2",
			success : function(val) {
				var data = JSON.getJson(val);
				console.log(val+"=====val======="+val[0]+"dffff"+data.url);
	//				alert(kmbh);
			 FileDownload("${ctx}/file/fileDownload","经济科目余额表.xls",data.url);
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
// 		var kjbh = $(this).find("[name='kjkmbh']").attr("value");//会计科目编号
// 		var kjmc = $(this).find("[name='kjkmmc']").attr("value");//会计科目名称
		var kmbh = $(this).find("[name='kmbh']").attr("value");//经济科目编号
		var kmmc = $(this).find("[name='kmmc']").attr("value");//经济科目名称
		if(kmbh.indexOf('小计')>0)
		{
			return;
		}
// 		var kjbhjjbh = kjbh+","+jjbh;
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
	    var bbqj = starttime+"月至"+endtime+"月";//会计区间
	    var jsonStr = $("#myform").serialize();
  		var datestr = year+"年"+startMonth+"月至"+endMonth+"月";
 		doOperate("${ctx}/jjkmyeb/getJjkmyeb_click?kmbh="+kmbh+"&kmmc="+kmmc+"&jsonStr="+jsonStr+"&datestr="+datestr+"&year="+year+"&startMonth="+startMonth+"&endMonth="+endMonth);
	});


</script>
</body>
</html>