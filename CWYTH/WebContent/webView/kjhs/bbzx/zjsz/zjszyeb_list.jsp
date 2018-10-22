<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<div class="search" id="searchBox" style="padding-top: 0px;height: 40px;">
		<form id="myform" class="form-inline" action="">
		<input type="hidden" name="start" value="start" />
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
							<option value="${months.month} " <c:if test="${endMonth eq months.month}">selected</c:if> >${months.month}</option>
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
				<div class="btn-group pull-right" role="group">
	                <button class="btn btn-default" type="button" id="dyyl">打印</button>
	                <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
            	</div>
			</div>
			<input type="hidden" name="end" value="end" />
        </form>
    </div>
	<div class="container-fluid" id="print_div" style="padding-bottom: 30px;">
		<div class='responsive-table'>
			<div class='scrollable-area' style="overflow-x:auto;">
			<div id="print_title">
				<h2 style="text-align:center;">资&nbsp;金&nbsp;收&nbsp;支&nbsp;余&nbsp;额&nbsp;表</h2>
				<div style="text-align:left;font-size:12px;float:left;width:33%">&emsp;</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">会计期间：${date}</div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：万元</div>
				</div>
		        <table id="mydatatables" class="table table-striped table-bordered"  border="0" bordercolor="#000000">		        	
					<thead>
		 				<tr  id="header">
						    <th>项目</th>
						    <th>余额</th>
			   			</tr>
					</thead>
					<tbody>
					</tbody>
					<tfoot>
				   <tr class="hide" style="text-align:right;">
				   <td width="100%" colspan="8">
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
	$(document).on("click","#btn_chaxun",function(){
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
 		var json = $("#myform").serializeObject("year","jzpz");  //json对象				
   		var jsonStr = JSON.stringify(json);  //json字符串
  		var datestr = year+"年"+startMonth+"月至"+endMonth+"月";
  		console.log(jsonStr)
  		getTable(jsonStr,datestr);
	});
	$("#btn_chaxun").click();
});
	function getTable(jsonStr,datestr){
   		$.ajax({
			type:"post",
			async : false, 
			url:"${ctx}/zjszyeb/getPageList",
			data:"json="+jsonStr,
			dataType: "json",
			success:function(val){
				$("#span_date").html(datestr);
				$("tbody").html("");
				var mxList = val.mxList;
				for (var i=0;i<mxList.length;i++){
					 var XMMC=mxList[i].XMMC;if(XMMC==null) XMMC='';
					 var YE=mxList[i].YE;if(YE==null) YE='';
					 $("tbody").append(
								"<tr name='guid'>"+
								"<td id='xmmc' name='xmmc' value='"+XMMC+"' style='padding:0px 1px;text-align:left'>"+XMMC+"</td>"+
								"<td id='ye' name='ye' value='"+YE+"' style='padding:0px 1px;text-align:right'>"+YE+"</td>"+
								"</tr>"
					);
				}
			},
		});	 
	}
</script>
<script >
//导出Excel
$(document).on("click","#btn_export",function(){
	var json = $("#myform").serializeObject("year","jzpz");  //json对象				
	var jsonStr = JSON.stringify(json);  //json字符串
	doExp(jsonStr,"${ctx}/zjszyeb/expExcel","资金收支余额表","${pageContext.request.contextPath}");
});
$(document).on("click","#dyyl",function(){
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
</script>
</body>
</html>