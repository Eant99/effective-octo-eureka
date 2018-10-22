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
							</div>
						</div>
					</div>
				</div>
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
				<h2 style="text-align:center;">会&nbsp;计&nbsp;科&nbsp;目&nbsp;余&nbsp;额&nbsp;表</h2>
				<div style="text-align:left;font-size:12px;float:left;width:33%">&emsp;</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">会计期间：<span id="span_date">${date}</span></div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
				</div>
		        <table id="mydatatables" class="table table-striped table-bordered"  border="0" bordercolor="#000000">		        	
					<thead>
		 				<tr  id="header">
						    <th rowspan="2">科目编号</th>
						    <th rowspan="2">科目名称</th>
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
	//查询按钮
	$(document).on("click","#btn_chaxun",function(){
		//会计期间的验证
		var year = $("[name='year']").val();                 //获取年份
		if(year==""){                                        //如果年份为空
			alert("会计期间年份不能为空!");                        //弹窗提示“会计期间年份不能为空!”
			return;
		}
		var startMonth = $("[name='startMonth']").val();     //获取开始月份
		var endMonth = $("[name='endMonth']").val();         //获取结束月份
		if(startMonth==""||endMonth===""){                   //如果会计区间开始月份、结束月份为空
			alert("会计期间月份不能为空!");                        //弹窗提示“会计期间月份不能为空!”
			return;
		}
		if(Number(startMonth)>Number(endMonth)){             //如果开始月份大于结束月份
			alert("会计期间起始月份不能大于截止月份!");               //弹窗提示“会计期间起始月份不能大于截止月份!”
			return;
		}
		var jsonStr = $("#myform").serialize();              //将mofrom里面的所有的name值放在josnStr中
  		var datestr = year+"年"+startMonth+"月至"+endMonth+"月";//datastr=拼接字符串
  		getTable(jsonStr,datestr);                            //将josnStr，datastr传到function getTable()中去
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
	
	//高级查询，选择科目弹窗
     $(document).on("click","[id=btn_kjkm]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId=km&controlId1=txt_km&flag=zhcx","科目信息","920","630");
	 });
	 $(document).on("click","[id=btn_kjkm1]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km1&controlId=txt_km1&flag=zhcx","科目信息","920","630");
	 });
	 $(document).on("click","[id=btn_kjkm2]",function(){			
			select_commonWin("${ctx}/jzmb/mainKjkmszTree?controlId1=txt_km2&controlId=txt_km2&flag=zhcx","科目信息","920","630");
	 });
	
	function getTable(jsonStr,datestr){
		var sjc = $("[name=startJc]").val();	 //获取开始科目级次
		var ejc = $("[name=endJc]").val();       //获取结束科目级次
		var mbmc = "${mbmc}";                    //模版表的模版名称，如果是1，显示模版一，如果是2，显示模版二
   		$.ajax({
			type:"post",
			async : false,                       //ajax同步执行
			url:"${ctx}/kmye/getPageList",       //获取页面数据的方法   
			data:"sjc="+sjc+"&ejc="+ejc+"&"+jsonStr+"&gs=kj&treebh=${param.kmbh}&kmsx=${param.kmsx}", //相关参数传到后台方法${ctx}/kmye/getPageList中
			dataType: "json",
			success:function(val){
				$("#span_date").text(datestr);   //将datastr的值复制给$("#span_date")
				$("tbody").html("");
				var mxList = val.mxList;         //用mxList接收存储过程返回的游标
				for (var i=0;i<mxList.length;i++){
					 var KMBH=mxList[i].KMBH;if(KMBH==null) KMBH='';//获取科目编号
					 var KMMC=mxList[i].KMMC;if(KMMC==null) KMMC='';//获取科目名称
					 var QCFX=mxList[i].QCFX;if(QCFX==null) QCFX='';//获取期初方向
					 var QCYE=mxList[i].QCYE;if(QCYE==null) QCYE='';//获取期初余额
					 var BQJF=mxList[i].BQJF;if(BQJF==null) BQJF='';//获取借方金额
					 var BQDF=mxList[i].BQDF;if(BQDF==null) BQDF='';//获取贷方金额
					 var QMFX=mxList[i].QMFX;if(QMFX==null) QMFX='';//获取期末方向
					 var QMYE=mxList[i].QMYE;if(QMYE==null) QMYE='';//获取期末余额
					 var QCJF=mxList[i].QCJF;if(QCJF==null) QCJF='';//获取期初借方金额
					 var QCDF=mxList[i].QCDF;if(QCDF==null) QCDF='';//获取期初贷方金额
					 var QMJF=mxList[i].QMJF;if(QMJF==null) QMJF='';//获取期末借方金额
					 var QMDF=mxList[i].QMDF;if(QMDF==null) QMDF='';//获取期末贷方金额
					 if(mbmc=='1'){                    //模版一要显示的内容
					 $("tbody").append(
								"<tr name='guid'>"+
								"<td id='kmbh' name='kmbh' value='"+KMBH+"' style='padding:0px 1px'>"+KMBH+"</td>"+
								"<td id='kmmc' name='kmmc' value='"+KMMC+"' style='padding:0px 1px'>"+KMMC+"</td>"+
								"<td id='fx' style='text-align:center;padding:0px 1px'>"+QCFX+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+QCYE+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
								"<td id='fx' style='text-align:center;padding:0px 1px'>"+QMFX+"</td>"+
								"<td style='text-align:right;padding:0px 1px'>"+QMYE+"</td>"+
								"</tr>"
					);
					} 
					 if(mbmc=='2'){                   //模版二要显示的内容
						 $("tbody").append(
									"<tr name='guid'>"+
									"<td id='kmbh' name='kmbh' value='"+KMBH+"' style='padding:0px 1px'>"+KMBH+"</td>"+
									"<td id='kmmc' name='kmmc' value='"+KMMC+"' style='padding:0px 1px'>"+KMMC+"</td>"+
									"<td id='fx' style='text-align:right;padding:0px 1px'>"+QCJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QCDF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+BQJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+BQDF+"</td>"+
									"<td id='fx' style='text-align:right;padding:0px 1px'>"+QMJF+"</td>"+
									"<td style='text-align:right;padding:0px 1px'>"+QMDF+"</td>"+
									"</tr>"
						);
					} 
				}
				var data = val.zjList[0];          //合计显示的内容
				$("tbody").append("<tr class='nojs' id='one'>"+
						"<td colspan='2' rowspan='2' style='text-align:center;padding:0px 1px'>合计</td>"+
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
			},
		});	 
	}
</script>
<script >
//刷新页面
function toUrl(params,date,gs){
	if(params){
		location.href = "${ctx}/kmye/kmyeList?flag=no&date="+date+"&gs="+gs;//重新加载页面
	}
}

//导出Excel
$(document).on("click","#btn_export",function(){
//获取开始级次和结束级次
var sjc = $("[name=startJc]").val();	
var ejc = $("[name=endJc]").val();	
var jsonStr = $("#myform").serialize();
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
			data : "sjc="+sjc+"&ejc="+ejc+"&"+jsonStr+"&gs=kj&treebh=${param.kmbh}&kmsx=${param.kmsx}",
			datatype:'json',
			url : "${ctx}/kmye/expExcel2",
			success : function(val) {
				var data = JSON.getJson(val);
				console.log(val+"=====val======="+val[0]+"dffff"+data.url);
			 FileDownload("${ctx}/file/fileDownload","会计科目余额表.xls",data.url);
		   }
	}); 
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
		LODOP.SET_PRINT_PAGESIZE(1, 0,0 ,"A4");
		LODOP.PREVIEW();		
};


//双击事件 id="header"
$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	var vamc = $(this).find("[name='kmbh']").attr("value");
	var kmmc = $(this).find("[name='kmmc']").attr("value");
	if(vamc.indexOf('小计')>0)
	{
		return;
	}
	var bbqj = $("#span_date").html();

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
	doOperate("${ctx}/mxz/toUrl?kmbh="+vamc+"&kmmc="+kmmc+"&bbqj="+bbqj+"&StartMonth="+startMonth+"&endMonth="+endMonth+"&pz=1&bmye=bmye&ye=yes");
});

function jumpWindow(){
	select_commonWin("${ctx}/kmye/jumpWindow","报表条件-科目余额表","600","470");
}
</script>
</body>
</html>