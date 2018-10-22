<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>新资产负债表</title>
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

</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group" style="width:350px;">
					<label>会计期间</label>
					<input type="text" class="form-control input-radius nd" style="width: 80px!important;" name="bbnd" value="${bbnd}"/>
					年
					<select class="form-control input-radius select" name="ksyfnum" style="width:50px;"> 
						<c:forEach var="months" items="${months}">
							<option value="${months.month}" <c:if test="${ksyf eq months.month}">selected</c:if> >${months.month}</option>
						</c:forEach>
 					</select>
 					月&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;
 					<select class="form-control input-radius select" name="jsyfnum" style="width:50px;">
						<c:forEach var="months" items="${months}">
							<option value="${months.month} " <c:if test="${jsyf eq months.month}">selected</c:if> >${months.month}</option>
						</c:forEach>
					</select>
					月
				</div>
				 <div class="form-group">
					 <label>是否包含未记账凭证&nbsp; </label> <select style="width:60px;border:1px solid #ccc;" id="jzpz" name="jzpz" class="form-control select">				
						<option value="1" <c:if test="${jzpz=='1'}">selected</c:if>>是</option>
						<option value="0" <c:if test="${jzpz=='0'}">selected</c:if>>否</option>	
					</select>
					&nbsp;				
					 <label>是否包含结转凭证&nbsp; </label> <select style="width:60px;border:1px solid #ccc;" id="sfjz" name="sfjz" class="form-control select">			
					 	<option value="1" <c:if test="${sfjz=='1'}">selected</c:if>>是</option>
					 	<option value="0" <c:if test="${sfjz=='0'}">selected</c:if>>否</option>		
					</select>
					 <button type='button' class="btn btn-primary" id="btn_searchold">查询已保存的数据</button>
					  <button type='button' class="btn btn-primary" id="btn_reset">重新生成数据</button>
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
			<h2 style="text-align:center;">资产负债表</h2>
			<div style="text-align:right;font-size:12px;">会高校01表</div>
					<div style="text-align:left;font-size:12px;float:left;width:33%">编制单位：${bzdw.XXMC}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：${bbnd}年${ksyf}月至${jsyf}月</div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
					</div>
					<thead>
				        <tr>
				            
				            <th style="text-align:center; width: 150px" id="tr_pzrq">资产</th>
				            <th style="text-align:center;" id="tr_zy">期末余额</th>	
				            <th style="text-align:center;" id="tr_kmbh">年初余额</th>
				            			            
				            <th style="text-align:center; width: 150px" id="tr_kmmc">负债和净资产</th>
				            <th style="text-align:center;" id="tr_dfje">期末余额</th>	 
				             <th style="text-align:center;" id="tr_kmbh">年初余额</th>	
				               
				        </tr>
					</thead>
				    <tbody>
				     <tbody>
				    <c:forEach var="list" items="${list}"  begin="0" step="1"   varStatus="i">
				    <c:if test="${i.index =='0'||i.index =='29'}">
				 		<tr class="tr_${i.index+1}">
				 			<td >
				 				${list.ZC}
				 				<input type="hidden" name="czr" value="${login}">
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="zc" value="${list.ZC}"/>
				 				<input type="hidden" name="bh" value="${list.BH}">
				 				<input type="hidden" name="fzhjzc" value="${list.FZHJZC}"/>
				 				<input type="hidden" name="ksyf" value="${list.KSYF}">
				 				<input type="hidden" name="jsyf" value="${list.JSYF}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="sfjz" value="${sfjz}">
				 				<input type="hidden" name="ztgid" value="${ztgid}">
				 				<input type="hidden" name="hc1" value="${list.HC1}"><!--行数  -->
				 			</td>
				 				<td >
				 				<input type="hidden" name=ncye1 class="form-control sign-number" value="${list.QMYE1}">
				 			</td>
				 			<td >
				 			<input type="hidden" name=qmye1 class="form-control sign-number" value="${list.NCYE1}">
				 			</td>
				 		
				 			<td >
				 				${list.FZHJZC}
				 				
				 			</td>
				 			<td >
				 				<input type="hidden" name=qmye2 class="form-control sign-number" value="${list.QMYE2}">
				 			</td>
				 			<td >
				 				<input type="hidden" name=ncye2 class="form-control sign-number" value="${list.NCYE2}">
				 			</td>
				 			
				 		</tr>
				 	</c:if>
				 	 <c:if test="${i.index !='0'&&i.index !='29'}">
				 	 <c:if test="${i.index !='11'&&i.index !='22'&&i.index !='24'&&i.index !='27'&&i.index !='28'&&i.index !='29'&&i.index !='30'&&i.index !='31'&&i.index !='32'&&i.index !='33'&&i.index!='18' &&i.index!='12'}">
				 	 <tr class="tr_${i.index+1}">
				 			<td >
				 				${list.ZC}
				 				<input type="hidden" name="czr" value="${login}">
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="zc" value="${list.ZC}"/>
				 				<input type="hidden" name="bh" value="${list.BH}">
				 				<input type="hidden" name="fzhjzc" value="${list.FZHJZC}"/>
				 				<input type="hidden" name="ksyf" value="${list.KSYF}">
				 				<input type="hidden" name="jsyf" value="${list.JSYF}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="sfjz" value="${sfjz}">
				 				<input type="hidden" name="ztgid" value="${ztgid}">
				 				<input type="hidden" name="hc1" value="${list.HC1}"><!--行数  -->
				 			</td>
				 		    <!-- //期初余额 -->
				 			<td >
				 				<input type="text" name="qmye1" class="form-control sign-number" value="${list.QMYE1}">
				 			</td>
				 			<!-- //期末余额 -->
				 				<td >
				 			
<%-- 				 			 <input type="hidden"  name=qmye1 class="form-control sign-number" value="${list.QMYE1}"> --%>
				 		
				 			<input type="text" name="ncye1" class="form-control sign-number" value="${list.NCYE1}">
				 			</td>
				 			<td >
				 				${list.FZHJZC}
				 			</td>
				 			<!-- 1 -->
				 			<td >
				 				<input type="text" name="qmye2" class="form-control sign-number" value="${list.QMYE2}">
				 			</td>
				 			<!-- 2 -->
				 			<td >
				 				<input type="text" name="ncye2" class="form-control sign-number" value="${list.NCYE2}">
				 			</td>
				 			
				 		</tr>
				 	 
				 	  </c:if>
				 	   	 <c:if test="${i.index =='11'||i.index =='22'||i.index =='24'||i.index =='27'||i.index =='28'||i.index =='29'||i.index =='30'||i.index =='31'||i.index =='32'||i.index =='33'}">
				 	 <tr class="tr_${i.index+1}">
				 			<td >
				 				${list.ZC}
				 				<input type="hidden" name="czr" value="${login}">
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="zc" value="${list.ZC}"/>
				 				<input type="hidden" name="bh" value="${list.BH}">
				 				<input type="hidden" name="fzhjzc" value="${list.FZHJZC}"/>
				 				<input type="hidden" name="ksyf" value="${list.KSYF}">
				 				<input type="hidden" name="jsyf" value="${list.JSYF}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="sfjz" value="${sfjz}">
				 				<input type="hidden" name="ztgid" value="${ztgid}">
				 				<input type="hidden" name="hc1" value="${list.HC1}"><!--行数  -->
				 			</td>
				 			<td >
				 				<input type="hidden" name="qmye1" class="form-control sign-number" value="${list.QMYE1}">
				 			</td>
				 			<td >
				 			<input type="hidden" name="ncye1" class="form-control sign-number" value="${list.NCYE1}">
				 			</td>
				 			<td >
				 				${list.FZHJZC}
				 			</td>
				 				<td >
				 				<input type="text" name="qmye2" class="form-control sign-number" value="${list.QMYE2}">
				 			</td>
				 			<td >
				 				<input type="text" name="ncye2" class="form-control sign-number" value="${list.NCYE2}">
				 			</td>
				 		</tr>
				 	  </c:if>
				 	  <c:if test="${i.index=='18' ||i.index=='12'}">
 						<tr class="tr_${i.index+1}">
				 			<td >
				 				${list.ZC}
				 				<input type="hidden" name="czr" value="${login}">
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="zc" value="${list.ZC}"/>
				 				<input type="hidden" name="bh" value="${list.BH}">
				 				<input type="hidden" name="fzhjzc" value="${list.FZHJZC}"/>
				 				<input type="hidden" name="ksyf" value="${list.KSYF}">
				 				<input type="hidden" name="jsyf" value="${list.JSYF}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="sfjz" value="${sfjz}">
				 				<input type="hidden" name="ztgid" value="${ztgid}">
				 				<input type="hidden" name="hc1" value="${list.HC1}"><!--行数  -->
				 			</td>
				 			<td >
				 				<input type="text" name="qmye1" class="form-control sign-number" value="${list.QMYE1}">
				 			</td>
				 			<td >
				 			<input type="text" name="ncye1" class="form-control sign-number" value="${list.NCYE1}">
				 			</td>
				 			<td >
				 				${list.FZHJZC}
				 			</td>
				 				<td >
				 				<input type="hidden" name="qmye2" class="form-control sign-number" value="${list.QMYE2}">
				 			</td>
				 			<td >
				 				<input type="hidden" name="ncye2" class="form-control sign-number" value="${list.NCYE2}">
				 			</td>
				 		
				 		</tr>
 						</c:if>
				</c:if>
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
	 //设置只读的行
	 readonlyArray(new Array(11,14,18,22,27,35),"qmye1");
	 readonlyArray(new Array(11,14,18,22,27,35),"ncye1");
	 readonlyArray(new Array(12,17,18,26,29,34,35),"qmye2");
	 readonlyArray(new Array(12,17,18,26,29,34,35),"ncye2");
	 //初始化数据
	 $("#btn_reset").on("click",function(){
		 var bbnd=$("[name='bbnd']").val();
	    	var sfjz = $("[name='sfjz']").val();
			var jzpz = $("[name='jzpz']").val();
			var ksyf = $("[name='ksyfnum']").val();
			var jsyf = $("[name='jsyfnum']").val();
			location.href="${ctx}/zcfzj/zcfzListNew?bbnd="+bbnd+"&ksyf="+ksyf+"&jsyf="+jsyf+"&sfjz="+sfjz+"&jzpz="+jzpz;
	 })
	  //查询已保存的数据
	 $("#btn_searchold").on("click",function(){
		 	var bbnd=$("[name='bbnd']").val();
	    	var sfjz = $("[name='sfjz']").val();
			var jzpz = $("[name='jzpz']").val();
			var ksyf = $("[name='ksyfnum']").val();
			var jsyf = $("[name='jsyfnum']").val();
			location.href="${ctx}/zcfzj/goZcfzbnewPage?bbnd="+bbnd+"&ksyf="+ksyf+"&jsyf="+jsyf+"&sfjz="+sfjz+"&jzpz="+jzpz;
	 })
	 //年份监听
	 $(document).on("focus", ".nd", function(){
		    $(this).on("click", function() {
		    	WdatePicker({
		    		dateFmt:'yyyy',
		    		onclearing:function(){
		    			alert("请选择年份!");
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
		var json = $("#mysave").serializeObject("czr","ncye2");  //json对象				
		var jsonStr = JSON.stringify(json);
		$.ajax({
			url:"${ctx}/zcfzj/doSaveNew",
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
		var json = $("#mysave").serializeObject("czr","ncye2");  //json对象				
		var jsonStr = JSON.stringify(json);
		$.ajax({
			url:"${ctx}/zcfzj/doSaveNew",
			data:"jsonStr="+jsonStr,
			dataType:"json",
			type:"post",
			success:function(msg){
				if($.trim(msg)=="true"){
					var bbnd=$("[name='bbnd']").val();
			    	var sfjz = $("[name='sfjz']").val();
					var jzpz = $("[name='jzpz']").val();
					var ksyf = $("[name='ksyf']").val();
					var jsyf = $("[name='jsyf']").val();
					location.href="${ctx}/zcfzj/goPrintNew?bbnd="+bbnd+"&ksyf="+ksyf+"&jsyf="+jsyf+"&sfjz="+sfjz+"&jzpz="+jzpz;
				}else{
					alert("预览失败!");
				}
			}
		});
});
});	
	//导出Excel
   	$("#btn_exp").click(function() {   
   		var json = $("#mysave").serializeObject("czr","ncye2");  //json对象				
		var jsonStr = JSON.stringify(json);
		$.ajax({
			url:"${ctx}/zcfzj/doSaveNew",
			data:"jsonStr="+jsonStr,
			dataType:"json",
			type:"post",
			success:function(msg){
				if($.trim(msg)=="true"){
					var bbnd=$("[name='bbnd']").val();
			    	var sfjz = $("[name='sfjz']").val();
					var jzpz = $("[name='jzpz']").val();
					var ksyf = $("[name='ksyf']").val();
					var jsyf = $("[name='jsyf']").val();
								$.ajax({
									type : "post",
									data : {bbnd:bbnd,ksyf:ksyf,jsyf:jsyf,jzpz:jzpz,sfjz:sfjz},
									url : "${ctx}/zcfzj/expExcel3",
									success : function(val) {
									   FileDownload("${ctx}/file/fileDownload","${bbnd}年${ksyf}月-${jsyf}月资产负债表.xls",val.url);
									}
								});
				}else{
					alert("导出失败!");
				}
			}
		});
   		
			});
	//合计资产期末余额
	$(document).on('keyup',".sign-number[name='qmye1']",function(){
		computeLinked(2,10,11,"qmye1");
		computeTwo(15,16,14,"qmye1","-");
		computeTwo(19,20,18,"qmye1","-");
		computeArray(new Array(13,14,17,18,21),22,"qmye1");
		computeArray(new Array("26"),27,"qmye1");
		computeArray(new Array(11,22,24,27),35,"qmye1");
		
	});	
	//合计资产期初余额
	$(document).on('keyup',".sign-number[name='ncye1']",function(){
		computeLinked(2,10,11,"ncye1");
		computeTwo(15,16,14,"ncye1","-");
		computeTwo(19,20,18,"ncye1","-");
		computeArray(new Array(13,14,17,18,21),22,"ncye1");
		computeArray(new Array("26"),27,"ncye1");
		computeArray(new Array(11,22,24,27),35,"ncye1");
		
	});	
	//合计负债期末余额
	$(document).on('keyup',".sign-number[name='qmye2']",function(){
		computeLinked(2,11,12,"qmye2");
		computeLinked(14,16,17,"qmye2");
		computeArray(new Array(12,17),18,"qmye2")
		computeTwo(27,28,26,"qmye2","+");
		computeLinked(20,26,29,"qmye2");
		computeLinked(31,33,34,"qmye2");
		computeArray(new Array(18,29,34),35,"qmye2");
		
	});	
	//合计负债期初余额
	$(document).on('keyup',".sign-number[name='ncye2']",function(){
		computeLinked(2,11,12,"ncye2");
		computeLinked(14,16,17,"ncye2");
		computeArray(new Array(12,17),18,"ncye2")
		computeTwo(27,28,26,"ncye2","+");
		computeLinked(20,26,29,"ncye2");
		computeLinked(31,33,34,"ncye2");
		computeArray(new Array(18,29,34),35,"ncye2");
		
	});	
	
</script>
</body>
</html>