<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>财政补助收入支出表</title>
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
	.number{
	text-align: right !important;
	}
	
/* 	.fullscreen{ */
/* 	width:80%; */
/* 	margin:0 auto; */
/* 	} */
/* .table-bordered > thead > tr > td, .table-bordered > thead > tr > th { */
/*     	border-bottom-width: 0px; */
/*     	border-bottom:1px solid #ddd; */
/* 	} */
/* 	 table{ */
/* 		border-collapse:collapse!important; */
/* 	} */

</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
	                <label>选择日期</label>
	             	<input  type="text" class="input_info nd form-control" style="border:1px solid #ccc;" value="${sysDate}"/>
	             	<i class="glyphicon glyphicon-calendar"></i> 
					&nbsp;
					
					 <label style="font-size: 14px;">是否包含未记账凭证&nbsp; </label> <select style="width:60px;border:1px solid #ccc;" id="jzpz" class="form-control select">			
					 	<option value="1" <c:if test="${jzpz=='1'}">selected</c:if>>是</option>
						<option value="0" <c:if test="${jzpz=='0'}">selected</c:if>>否</option>		
					</select>
					&nbsp;				
<!-- 					 <label>是否包含结转凭证&nbsp; </label> <select style="width:60px;border:1px solid #ccc;" id="sfjz" class="form-control select">			 -->
<%-- 					 	<option value="0" <c:if test="${sfjz=='0'}">selected</c:if>>否</option>		 --%>
<%-- 						<option value="1" <c:if test="${sfjz=='1'}">selected</c:if>>是</option> --%>
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

<form id="mysave" name="mysave" method="post">
<div class="container-fluid" >
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:100%;margin:0 auto 30px;">
			      <div id="print_title">
			<h2 style="text-align:center;">财政补助收入支出表</h2>
			<div style="text-align:right;font-size:12px;">会高校03表</div>
					<div style="text-align:left;font-size:12px;float:left;width:33%">编制单位：${bzdw.XXMC}</div>
					<div style="text-align:center;font-size:12px;float:left;width:34%">报表期间：${sysDate }年度</div>
					<div style="text-align:right;font-size:12px;float:left;width:33%">单位：元</div>
					</div>
					<thead>
				        <tr>
				            
<!-- 				            <th style="text-align:center; width: 150px" id="tr_pzrq">项目</th> -->
<!-- 				            <th style="text-align:center;" id="tr_kmbh">本年数</th> -->
<!-- 				            <th style="text-align:center;" id="tr_zy">上年数</th>		 -->
				            <th style="text-align:center;width:30%">项目</th>
				            <th style="text-align:center;width:35%">本年数</th>
				            <th style="text-align:center;width:35%">上年数</th>			            
				        </tr>
					</thead>
				    <tbody>
				     <tbody>
				    <c:forEach var="list" items="${list}" varStatus="i">
				 		<tr class="tr_${i.index+1}">
				 			
				 			<td class="num">
				 				${list.XMMC}
				 				<input type="hidden" name="czr" value="${login}">
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" class="form-control" name="xmbh" value="${list.XMMC}"/>
				 				<input type="hidden" name="zc" value="${list.ZC}"/>
				 				<input type="hidden" name="fzhjzc" value="${list.FZHJZC}"/>
				 				<input type="hidden" name="nd" value="${sysDate}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="read" value="${list.READ}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="ztgid" value="${ztgid}">
				 				<input type="hidden" name="ztbh" value="${sszt}"><!--所属账套-->
				 				<input type="hidden" name="hc" value="${list.XH}"><!--行数  -->
				 			</td>
				 			<td class="num">
				 				<input type="text" name="bns" style="text-align:right;" class="form-control number" value="${list.BYS}">
				 				
				 			</td>
				 			<td class="num">
				 				<input type="text" name="sns" style="text-align:right;" class="form-control number" ${list.READ} value="${list.BNLJS}">
				 			</td>
				 		</tr>
				 	</c:forEach>
				    </tbody>
				   </tbody>
				</table>					
		</div>
			</div>
		</div>	
	</form>		
		</div>

<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
 $(function () {
	 $(document).on("focus", ".nd", function(){
		    $(this).on("click", function() {
		    	WdatePicker({
		    		dateFmt:'yyyy',
		    		onpicked:function(){
		    			var val = $(this).val();
		    			var jzpz = $("#jzpz").val();
		    			var sfjz = $("#sfjz").val();
		    			if(val!=""&&jzpz!=""&&sfjz!=""){
		    				location.href="${ctx}/czbzsrzc/czbzsrzc_list?nd="+val+"&bblx=1&jzpz="+jzpz+"&sfjz="+sfjz;
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
			var json = $("#mysave").serializeObject("czr","sns");  //json对象				
			var jsonStr = JSON.stringify(json);
			$.ajax({
				url:"${ctx}/czbzsrzc/doSave",
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
			var json = $("#mysave").serializeObject("czr","sns");  //json对象				
			var jsonStr = JSON.stringify(json);
			$.ajax({
				url:"${ctx}/czbzsrzc/doSave",
				data:"jsonStr="+jsonStr,
				dataType:"json",
				type:"post",
				success:function(msg){
					if($.trim(msg)=="true"){
						var sysDate = $("[name='nd']").val();
						console.log(sysDate)
						var ztgid = $("[name='ztgid']").val();
						var sfjz = $("[name='sfjz']").val();
						var jzpz = $("#jzpz").val();
						location.href = "${ctx}/czbzsrzc/goPrint?sysDate="+sysDate+"&jzpz="+jzpz+"&sfjz="+sfjz+"&ztgid="+ztgid;
					}else{
						alert("预览失败!");
					}
				}
			});
			
		});
// 		//结转凭证条件切换
// 		 $("#sfjz").change(function(){
// 		 	var jzpz = $("#jzpz").val();
// 		 	var val = $(".nd").val();
// 		 	var sfjz=$(this).val();
// 		 	if(val!=""&&jzpz!=""&&sfjz!=""){
// 		 		location.href="${ctx}/czbzsrzc/czbzsrzc_list?bblx=1&nd="+val+"&bblx=1&jzpz="+jzpz+"&sfjz="+sfjz;
// 		 	}else{
// 		 		alert("存在空的查询条件!");
// 		 		return;
// 		 	}
// 		 });
		//记账凭证条件切换
		 $("#jzpz").change(function(){
			var jzpz = $(this).val();
		 	var val = $(".nd").val();
		 	var sfjz=$("#sfjz").val();
		 	if(val!=""&&jzpz!=""){
		 		location.href="${ctx}/czbzsrzc/czbzsrzc_list?bblx=1&nd="+val+"&bblx=1&jzpz="+jzpz+"&sfjz="+sfjz;
		 	}else{
		 		alert("存在空的查询条件!");
		 		return;
		 	}
		 });
			//列表右侧悬浮按钮
			$(window).resize(function(){
		    	$("div.dataTables_wrapper").width($("#searchBox").width());
		    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
		    	$(".dataTables_scrollBody").height(heights);
		    	table.draw();
			});

//导出Excel
$("#btn_exp").click(function() {
	var json = $("#mysave").serializeObject("czr","sns");  //json对象				
	var jsonStr = JSON.stringify(json);
	$.ajax({
		url:"${ctx}/czbzsrzc/doSave",
		data:"jsonStr="+jsonStr,
		dataType:"json",
		type:"post",
		success:function(msg){
			if($.trim(msg)=="true"){
				$.ajax({
					type : "post",
					url : "${ctx}/czbzsrzc/expExcel2",
					success : function(val) {
							FileDownload("${ctx}/file/fileDownload","财政补助收入支出表.xls", val.url);
					}
				});
			}else{
				alert("导出失败!");
			}
		}
	});
	});
	});
</script>
</body>
</html>