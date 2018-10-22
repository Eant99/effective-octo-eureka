<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>旧资产负债表（月度）</title>
<%@include file="/static/include/public-list-css.inc"%>
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
	margin-top: 50px;
	}
	.bottom1 tr td{
	width: 400px !important;
	}
	tr td{
	height: 30px;
	
	}
	tr th{
	    border-bottom: 0px solid #ddd !important;
	}
	.sign-number{
	text-align:right !important;
	}
.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
    	border-bottom-width: 0px;
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}

</style>
<%
String sysDate = Validate.isNullToDefaultString(request.getAttribute("jsyf"), "");
sysDate = sysDate.replace("-","年")+"月";
%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
	                <label>选择日期</label>
	             	<input  type="text" class="input_info nd form-control" style="border:1px solid #ccc;" value="${jsyf}"/>
	             	<i class="glyphicon glyphicon-calendar"></i> 
					&nbsp;
					
					 <label>是否包含未记账凭证&nbsp; </label> <select style="width:60px;border:1px solid #ccc;" id="jzpz" class="form-control select">			
					 	<option value="0" <c:if test="${jzpz=='0'}">selected</c:if>>否</option>		
						<option value="1" <c:if test="${jzpz=='1'}">selected</c:if>>是</option>
					</select>
					&nbsp;				
					 <label>是否包含结转凭证&nbsp; </label> <select style="width:60px;border:1px solid #ccc;" id="sfjz" class="form-control select">			
					 	<option value="0" <c:if test="${sfjz=='0'}">selected</c:if>>否</option>		
						<option value="1" <c:if test="${sfjz=='1'}">selected</c:if>>是</option>
					</select>
					
				</div>
				<div class="btn-group pull-right" role="group">
				    <button type='button' class="btn btn-default" id="btn_save">保存</button>
					<button type='button' class="btn btn-default" id="btn_print">打印预览</button>
					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
			</div>
		</form>
	</div>
	

<form id="mysave" method="post">
<div class="container-fluid" style="position:realtive">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:100%;margin:0 auto 30px;">
					 <div id="print_title">
			<h2 style="text-align:center;">资产负债表（月度）</h2>
			<div style="text-align:right;font-size:12px;">会高校01表</div>
					<div style="text-align:left;font-size:12px;float:left;width:33%">编制单位：${bzdw.XXMC}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：<%=sysDate %></div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
					</div>
					<thead>
				        <tr>
				            
				            <th style="text-align:center; width: 150px" id="tr_pzrq">资产</th>
				            <th style="text-align:center;" id="tr_zy">期末余额</th>	
				            <th style="text-align:center;" id="tr_kmbh">期初余额</th>
				            			            
				            <th style="text-align:center; width: 150px" id="tr_kmmc">负债和净资产</th>
				            <th style="text-align:center;" id="tr_dfje">期末余额</th>	 
				             <th style="text-align:center;" id="tr_kmbh">期初余额</th>	
				               
				        </tr>
					</thead>
				    <tbody>
				     <tbody>
				    <c:forEach var="list" items="${list}"  varStatus="i">
				 	 <tr class="tr_${i.index+1}">
				 			<td class="num">
				 				${list.ZC}
				 				<input type="hidden" name="czr" value="${login}">
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="zc" value="${list.ZC}"/>
				 				<input type="hidden" name="fzhjzc" value="${list.FZHJZC}"/>
				 				<input type="hidden" name="nyr" value="${jsyf}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="ztgid" value="${ztgid}">
				 				<input type="hidden" name="bh" value="${list.BH}"><!--行数  -->
				 			</td>
				 		    <!-- //期初余额 -->
				 			<td class="num">
				 				<input type="text" name=qmye1 class="form-control sign-number" value="${list.QMYE1}">
				 			</td>
				 			<!-- //期末余额 -->
				 				<td class="num">
				 			<input type="text" name=ncye1 class="form-control sign-number" value="${list.NCYE1}">
				 			</td>
				 			<td class="num">
				 				${list.FZHJZC}
				 				
				 			</td>
				 			<!-- 1 -->
				 			<td class="num">
				 				<input type="text" name=qmye2 class="form-control sign-number" value="${list.QMYE2}">
				 			    <input type="hidden" name="bblx" value="0">
				 			</td>
				 			<!-- 2 -->
				 			<td class="num">
				 				<input type="text" name=ncye2 class="form-control sign-number" value="${list.NCYE2}">
				 			</td>
				 		</tr>
				 	</c:forEach>
				    </tbody>
				   </tbody>
				</table>					
		</div>
			</div>
		</div>	
		<br /><br /><br />
	</form>		
<!-- <div class="bottom1"> -->
<!-- <table> -->
<!-- <tr> -->
<!-- <td>单位主要负责人（签字）：</td> -->
<!-- <td>财务负责人（签字）：</td> -->
<!-- <td>制表人（签字）：</td> -->
<!-- </tr> -->
<!-- <tr> -->
<!-- <td> -->
<!-- 注：本表反映核算单位各项资产及负债情况。 -->
<!-- </td> -->
<!-- </tr> -->
<!-- </table> -->
<!-- </div> -->
		</div>

<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script src="${ctx }/static/javascript/public/public-cwbb.js"></script>
<script>
 $(function () {
	 $(document).on("focus", ".nd", function(){
		    $(this).on("click", function() {
		    	WdatePicker({
		    		dateFmt:'yyyy-MM',
		    		onpicked:function(){
		    			var val = $(this).val();
		    			var jzpz = $("#jzpz").val();
		    			if(val!=""&&jzpz!=""){
		    				location.href="${ctx}/zcfzj/zcfzList?status=month&jsyf="+val+"&jzpz="+jzpz;
		    			}
		    		},
		    		onclearing:function(){
		    			alert("请选择月份!");
		    			return;
		    		}
		    	});
		    });
		    $(this).on("keypress", function() {
		        if (/[^0-9]/.test(String.fromCharCode(event.keyCode)))
		            event.keyCode = 0;
		    });
		    $(this).on("dragenter", function() {
		        return false;
		    });
		    $(this).on("paste", function() {
		        return false;
		    });
		});	
	//点击保存按钮
	$("#btn_save").click(function(){
		var json = $("#mysave").serializeObject("czr","qmye2");  //json对象	
		var jsonStr = JSON.stringify(json);
		$.ajax({
			url:"${ctx}/zcfzj/doSave",
			data:"jsonStr="+jsonStr,
			dataType:"json",
			type:"post",
			success:function(msg){
				if($.trim(msg)=="true"){
					alert("保存成功!");
				}else{
					alert("保存失败!");
				}
			}
		});
	});
	//点击打印
	$("#btn_print").click(function(){
		var json = $("#mysave").serializeObject("czr","qmye2");  //json对象				
		var jsonStr = JSON.stringify(json);
		$.ajax({
			url:"${ctx}/zcfzj/doSave",
			data:"jsonStr="+jsonStr,
			dataType:"json",
			type:"post",
			success:function(msg){
				if($.trim(msg)=="true"){
					var ztgid = $("[name='ztgid']").val();
					var sfjz = $("[name='sfjz']").val();
					var jzpz = $("[name='jzpz']").val();
					location.href = "${ctx}/zcfzj/goPrint?jsyf=${jsyf}&ztgid="+ztgid+"&sfjz="+sfjz+"&jzpz="+jzpz;
				}else{
					alert("预览失败!");
				}
			}
		});
	});

	 
});	
//记账凭证条件切换
 $("#jzpz").change(function(){
 	var jzpz = $(this).val();
 	var val = $(".nd").val();
 	var sfjz=$("#sfjz").val();
 	var ztgid=$("#ztgid").val();
 	var sfjz=$("#sfjz").val();
 	var sfjz=$("#sfjz").val();
 	if(val!=""&&jzpz!=""&&sfjz!=""){
 		location.href="${ctx}/zcfzj/zcfzList?bblx=0&jsyf="+val+"&bblx=0&jzpz="+jzpz+"&sfjz="+sfjz;
 	}else{
 		alert("存在空的查询条件!");
 		return;
 	}
 });
//结转凭证条件切换
 $("#sfjz").change(function(){
 	var jzpz = $("#jzpz").val();
 	var val = $(".nd").val();
 	var sfjz=$(this).val();
 	if(val!=""&&jzpz!=""&&sfjz!=""){
 		location.href="${ctx}/zcfzj/zcfzList?bblx=0&jsyf="+val+"&bblx=0&jzpz="+jzpz+"&sfjz="+sfjz;
 	}else{
 		alert("存在空的查询条件!");
 		return;
 	}
 });
	//导出Excel
   	$("#btn_exp").click(function() {   
   		var json = $("#mysave").serializeObject("czr","qmye2");  //json对象	
		var jsonStr = JSON.stringify(json);
		$.ajax({
			url:"${ctx}/zcfzj/doSave",
			data:"jsonStr="+jsonStr,
			dataType:"json",
			type:"post",
			success:function(msg){
				if($.trim(msg)=="true"){
					var jzpz = $("#jzpz").val();
			 		var val = $(".nd").val();
			 		var sfjz=$("#sfjz").val();
			 		var ztgid=$("#ztgid").val();
								$.ajax({
									type : "post",
									data : {jsyf:val,bblx:0,jzpz:jzpz,sfjz:sfjz},
									url : "${ctx}/zcfzj/expExcel2",
									success : function(val) {
									   FileDownload("${ctx}/file/fileDownload","<%=sysDate %>资产负债表.xls",val.url);
									}
								});
				}else{
					alert("导出失败!");
				}
			}
		});
   		
			});
   	<!--计算区域-->
  //合计资产期末余额
  $(document).on('keyup',".sign-number[name='qmye1']",function(){
  	computeLinked(2,10,11,"qmye1");
  	computeTwo(15,16,14,"qmye1","-");
  	computeTwo(19,20,18,"qmye1","-");
  	computeArray(new Array(13,14,17,18,21),22,"qmye1");
  	computeArray(new Array(11,22),30,"qmye1");
  	
  });	
  //合计资产期初余额
  $(document).on('keyup',".sign-number[name='ncye1']",function(){
  	computeLinked(2,10,11,"ncye1");
  	computeTwo(15,16,14,"ncye1","-");
  	computeTwo(19,20,18,"ncye1","-");
  	computeArray(new Array(13,14,17,18,21),22,"ncye1");
  	computeArray(new Array(11,22),30,"ncye1");	
  });	
  //合计负债期末余额
  $(document).on('keyup',".sign-number[name='qmye2']",function(){
  	computeLinked(2,11,12,"qmye2");
  	computeLinked(14,16,17,"qmye2");
  	computeArray(new Array(12,17),18,"qmye2")
  	computeTwo(27,28,26,"qmye2","+");
  	computeLinked(20,26,29,"qmye2");
  	computeArray(new Array(18,29),30,"qmye2");
  });	
  //合计负债期初余额
  $(document).on('keyup',".sign-number[name='ncye2']",function(){
  	computeLinked(2,11,12,"ncye2");
  	computeLinked(14,16,17,"ncye2");
  	computeArray(new Array(12,17),18,"ncye2")
  	computeTwo(27,28,26,"ncye2","+");
  	computeLinked(20,26,29,"ncye2");
  	computeArray(new Array(18,29),30,"ncye2");
  	
  });	
  <!--隐藏只读-->
  //设置只读的行
  readonlyArray(new Array(11,14,18,22,30),"qmye1");
  readonlyArray(new Array(11,14,18,22,30),"ncye1");
  readonlyArray(new Array(12,17,18,26,29,30),"qmye2");
  readonlyArray(new Array(12,17,18,26,29,30),"ncye2");
  //隐藏的行
  hiddenLinked(23,29,"qmye1");
  hiddenLinked(23,29,"ncye1");
  hiddenArray(new Array(1,12),"qmye1");
  hiddenArray(new Array(1,12),"ncye1");
  hiddenArray(new Array(1,13,19),"qmye2");
  hiddenArray(new Array(1,13,19),"ncye2");
</script>
</body>
</html>