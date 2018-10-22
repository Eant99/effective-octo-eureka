<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入支出决算总表</title>
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
<%String jsyf = Validate.isNullToDefaultString(request.getAttribute("jsyf"), "");
jsyf = jsyf.replace("-","年")+"月"; 
%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>选择日期</label>
					<input  type="text" class="input_info nd form-control" name="jsyf" style="border:1px solid #ccc;" value="${jsyf}"/>
	             	<i class="glyphicon glyphicon-calendar"></i> 
					&nbsp;
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
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:100%;margin:0 auto 30px;border-top:none;border-left:none;border-right:none">
					<div id="print_title">
			<h2 style="text-align:center;">收入支出决算总表</h2>
					<div style="text-align:right;font-size:12px;">财决01表</div>
					<div style="text-align:left;font-size:12px;float:left;width:33%">编制单位：${bzdw.XXMC}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：<%=jsyf %></div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
					</div>
					</div>
				        <tr>
				            <th colspan="4" style="text-align:center;" >收入</th>
				            <th colspan="8" style="text-align:center; ">支出</th>
				        </tr>
				       </tr>
				        <tr>
				            <th style="text-align:center;width:8%" >项目</th>
				            <th style="text-align:center;width:5% ">行次</th>
				            <th style="text-align:center;" >年初预算数</th>
				            <th style="text-align:center; ">期末数</th>
				            <th style="text-align:center;width:8%" >项目（按功能分类）</th>
				            <th style="text-align:center;width:5% ">行次</th>
				            <th style="text-align:center;" >年初预算数</th>
				            <th style="text-align:center; ">期末数</th>
				            <th style="text-align:center;width:8%">项目（按支出性质和经济分类）</th>
				            <th style="text-align:center;width:5% ">行次</th>
				            <th style="text-align:center;" >年初预算数</th>
				            <th style="text-align:center; ">期末数</th>
				        </tr>
				         <tr>
				            <th style="text-align:center;" >栏次</th>
				            <th style="text-align:center; "></th>
				            <th style="text-align:center;" >1</th>
				            <th style="text-align:center; ">2</th>
				            <th style="text-align:center;" >栏次</th>
				            <th style="text-align:center; "></th>
				            <th style="text-align:center;" >3</th>
				            <th style="text-align:center; ">4</th>
				            <th style="text-align:center;" >栏次</th>
				            <th style="text-align:center; "></th>
				            <th style="text-align:center;" >5</th>
				            <th style="text-align:center; ">6</th>
				        </tr>
					</thead>
				    <tbody>
				     <tbody>
				    <c:forEach var="list" items="${list}"  begin="0" step="1"   varStatus="i">
				    <c:if test="${i.index >=0&&i.index <=22}">
				 		<tr class="tr_${i.index+1}">
				 			<td class="text text-left">
				 				${list.SR}
				 				<input type="hidden" name="czr" value="${login}">
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="sr" value="${list.SR}">
				 				<input type="hidden" name="bh" value="${list.BH}">
				 				<input type="hidden" name="zc1" value="${list.ZC1}">
				 				<input type="hidden" name="zc2" value="${list.ZC2}">
				 				<input type="hidden" name="hc1" value="${list.HC1}">
				 				<input type="hidden" name="hc2" value="${list.HC2}">
				 				<input type="hidden" name="hc3" value="${list.HC3}">
				 				<input type="hidden" name="jsyf" value="${list.JSYF}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="ztgid" value="${ztgid}">
				 			</td>
				 			<td class="text-center">
				 			${list.HC1}
				 			</td>
				 			<td class="num">
				 				<input type="text" name="ncyss1" class="form-control sign-number" value="${list.NCYSS1}">
				 			</td>
				 			<td class="num">
				 			<input type="text" name="qms1" class="form-control sign-number" value="${list.QMS1}">
				 			</td>
				 			<td class="text text-left">
				 				${list.ZC1}
				 			</td>
				 			<td class="text-center">${list.HC2}
				 			</td>
				 			<td class="num">
				 				<input type="text" name="ncyss2" class="form-control sign-number" value="${list.NCYSS2}">
				 			</td>
				 			<td class="num">
				 				<input type="text" name="qms2" class="form-control sign-number" value="${list.QMS2}">
				 			</td>
				 			<td class="text text-left">
				 				${list.ZC2}
				 			</td>
				 			<td class="text-center">
				 			${list.HC3}
				 			</td>
				 			<td class="num">
				 				<input type="text" name="ncyss3" class="form-control sign-number" value="${list.NCYSS3}">
				 			</td>
				 			<td class="num">
				 				<input type="text" name="qms3" class="form-control sign-number" value="${list.QMS3}">
				 			</td>
				 		</tr>
				 	</c:if>
				 	<c:if test="${i.index>=23&&i.index<=35}">
				 	<tr class="tr_${i.index+1}">
				 			<td class="text text-left">
				 				${list.SR}
				 				<input type="hidden" name="czr" value="${login}">
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="sr" value="${list.SR}">
				 				<input type="hidden" name="bh" value="${list.BH}">
				 				<input type="hidden" name="zc1" value="${list.ZC1}">
				 				<input type="hidden" name="hc1" value="${list.HC1}">
				 				<input type="hidden" name="hc3" value="${list.HC3}">
				 				<input type="hidden" name="jsyf" value="${list.JSYF}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="ztgid" value="${ztgid}">
				 			</td>
				 			<td class="text-center">
				 			${list.HC1}
				 			</td>
				 			<td class="num">
				 				<input type="text" name="ncyss1" class="form-control sign-number" value="${list.NCYSS1}">
				 			</td>
				 			<td class="num">
				 			<input type="text" name="qms1" class="form-control sign-number" value="${list.QMS1}">
				 			</td>
				 			<td class="text text-left" colspan="5">
				 				${list.ZC1}
				 			</td>
				 			<td class="text-center">
				 			${list.HC3}
				 			</td>
				 			<td class="num">
				 				<input type="text" name="ncyss3" class="form-control sign-number" value="${list.NCYSS3}">
				 			</td>
				 			<td class="num">
				 				<input type="text" name="qms3" class="form-control sign-number" value="${list.QMS3}">
				 			</td>
				 		</tr>
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
	 //隐藏不需要的数据
	 hiddenArray(new Array(2,3,5,6,25),"ncyss1");
	 hiddenLinked(27,35,"ncyss1");
	 hiddenLinked(8,23,"ncyss1");
	 hiddenArray(new Array(2,3,5,6),"qms1");
	 hiddenLinked(25,35,"qms1");
	 hiddenLinked(8,23,"qms1");
	 hiddenLinked(1,4,"ncyss2");
	 hiddenLinked(6,23,"ncyss2");
	 hiddenLinked(1,4,"qms2");
	 hiddenLinked(6,23,"qms2");
	 hiddenLinked(new Array("5"),"qms2");
	 hiddenLinked(25,35,"ncyss3");
	 hiddenLinked(7,23,"ncyss3");
	 hiddenArray(new Array("5"),"ncyss3");
	 hiddenLinked(7,11,"qms3");
	 hiddenLinked(25,35,"qms3");
	 hiddenArray(new Array(5,16,17,19,21,22,23),"qms3");
	 //只读数据
	 readonlyArray(new Array(24,36),"ncyss1");
	 readonlyArray(new Array(24,36),"qms1");
	 readonlyArray(new Array("5"),"ncyss2");
	 readonlyArray(new Array("5"),"qms2");
	 readonlyArray(new Array(1,4,12,24,36),"ncyss3");
	 readonlyArray(new Array(1,4,12,24,36),"qms3");
	 //增加监听事件刷新页面数据
	 $(document).on("change", ".select", function(){
		    	var sfjz = $("[name='sfjz']").val();
				var jzpz = $("[name='jzpz']").val();
				var jsyf = $("[name='jsyf']").val();
				location.href="${ctx}/srfyj/srzcjszbList?jsyf="+jsyf+"&sfjz="+sfjz+"&jzpz="+jzpz;
		});	
	 //年份监听
	 $(document).on("focus", ".nd", function(){
		    $(this).on("click", function() {
		    	WdatePicker({
		    		dateFmt:'yyyy-MM',
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
	 //初始化数据
	 $("#btn_reset").on("click",function(){
		 	var sfjz = $("[name='sfjz']").val();
			var jzpz = $("[name='jzpz']").val();
			var jsyf = $("[name='jsyf']").val();
			location.href="${ctx}/srfyj/srzcjszbList?jsyf="+jsyf+"&sfjz="+sfjz+"&jzpz="+jzpz;
	 })
	  //查询已保存的数据
	 $("#btn_searchold").on("click",function(){
		 	var sfjz = $("[name='sfjz']").val();
			var jzpz = $("[name='jzpz']").val();
			var jsyf = $("[name='jsyf']").val();
			location.href="${ctx}/srfyj/goSrzcjszbPage?jsyf="+jsyf+"&sfjz="+sfjz+"&jzpz="+jzpz;
	 })
	//点击保存按钮
	$("#btn_save").click(function(){
		var json = $("#mysave").serializeObject("czr","qms3");  //json对象
		var sfjz = $("[name='sfjz']").val();
		var jzpz = $("[name='jzpz']").val();
		var jsonStr = JSON.stringify(json);
		$.ajax({
			url:"${ctx}/srfyj/doSaveSrzcjszb?sfjz="+sfjz+"&jzpz="+jzpz,
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
		var json = $("#mysave").serializeObject("czr","qms3");  //json对象
		var sfjz = $("[name='sfjz']").val();
		var jzpz = $("[name='jzpz']").val();
		var jsonStr = JSON.stringify(json);
		$.ajax({
			url:"${ctx}/srfyj/doSaveSrzcjszb?sfjz="+sfjz+"&jzpz="+jzpz,
			data:"jsonStr="+jsonStr,
			dataType:"json",
			type:"post",
			success:function(msg){
				if($.trim(msg)=="true"){
					var sfjz = $("[name='sfjz']").val();
					var jzpz = $("[name='jzpz']").val();
					var jsyf = $("[name='jsyf']").val();
					location.href="${ctx}/srfyj/goPrintSrzcjszb?jsyf="+jsyf+"&sfjz="+sfjz+"&jzpz="+jzpz;
				}else{
					alert("预览失败!");
				}
			}
		});
		
});
});	
	//导出Excel
   	$("#btn_exp").click(function() {
   		var json = $("#mysave").serializeObject("czr","qms3");  //json对象
		var sfjz = $("[name='sfjz']").val();
		var jzpz = $("[name='jzpz']").val();
		var jsonStr = JSON.stringify(json);
		$.ajax({
			url:"${ctx}/srfyj/doSaveSrzcjszb?sfjz="+sfjz+"&jzpz="+jzpz,
			data:"jsonStr="+jsonStr,
			dataType:"json",
			type:"post",
			success:function(msg){
				if($.trim(msg)=="true"){
					var filename="${jsyf}收入支出决算总表.xls";
					var sfjz = $("[name='sfjz']").val();
					var jzpz = $("[name='jzpz']").val();
					var jsyf = $("[name='jsyf']").val();
					$.ajax({
						type:"post",
						url : "${ctx}/srfyj/expExcel3?jsyf="+jsyf+"&sfjz="+sfjz+"&jzpz="+jzpz+"&filename="+filename,
						success : function(val) {
						var data = JSON.getJson(val);
						   FileDownload("${ctx}/file/fileDownload",filename,data.url);
						}
					});
				}else{
					alert("导出失败!");
				}
			}
			});
   	})
	//收入预算数合计
	$(document).on('keyup',".sign-number[name='ncyss1']",function(){
		computeArray(new Array(1,4,7),24,"ncyss1");
		computeArray(new Array(24,26),36,"ncyss1");
	})
	//收入期末数合计
	$(document).on('keyup',".sign-number[name='qms1']",function(){
		computeArray(new Array(1,4,7),24,"qms1");
		computeArray(new Array("24"),36,"qms1");
	})
	//支出预算数合计
	$(document).on('keyup',".sign-number[name='ncyss3']",function(){
		computeArray(new Array(2,3),1,"ncyss3");
		computeArray(new Array("6"),4,"ncyss3");
		computeArray(new Array(1,6),24,"ncyss3");
		computeArray(new Array("24"),36,"ncyss3");
		$(".tr_5 [name='ncyss2']").val($(".tr_24 [name='ncyss3']").val());
	})
	//支出期末数合计
	$(document).on('keyup',".sign-number[name='qms3']",function(){
		computeArray(new Array(2,3),1,"qms3");
		computeArray(new Array("6"),4,"qms3");
		computeArray(new Array(1,6),24,"qms3");
		computeArray(new Array("24"),36,"qms3");
		$(".tr_5 [name='qms2']").val($(".tr_24 [name='qms3']").val());
	})
</script>
</body>
</html>