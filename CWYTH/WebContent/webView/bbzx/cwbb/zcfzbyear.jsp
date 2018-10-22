<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产负债表（年度）</title>
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
				<div class="form-group">
	                <label>选择日期</label>
	             	<input  type="text" class="input_info nd form-control" style="border:1px solid #ccc;" value="${sysDate}"/>
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
				    <button type='button' class="btn btn-default" id="btn_save">保存	</button>
					<button type='button' class="btn btn-default" id="btn_print">打印预览</button>
					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid" style="width: 80%">

		<center>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                 <h2 id="txt_bbmc" >资产负债表（年度）</h2>
               </td>
	        </tr>
	    </center>
	    
<!-- 	    <div class="btn-group pull-right" role="group" style="margin-top:5px;"> -->
<!-- 	          <ul> -->
<!-- 	          <li> -->
<!-- 	    	 <td align="right" valign="middle" style="width: 400px; height: 20px;font-size:14px;"> -->
<!--                               会政财01表              -->
<!--                 </td>  -->
<!--                 </tr> -->
<!--                 </li> -->
<!--                 <li> -->
<!--                 <tr> -->
<!--                 <td align="right" valign="bottom" style="width: 429px; height: 20px;font-size:14px;"> -->
<!--                               单位：元 -->
<!--                 </td>  -->
<!--                 </tr> -->
<!--                 </li> -->
<!--                 </ul>   -->
<!-- 	    </div> -->
	  
	      <div style="margin-left: 48%;margin-top:10px;font-size:14px;">    
                       ${sysDate}
	    </div>
	    <div style="margin-top: 20px;position: absolute;font-size:14px;">        	 
                                编制单位：${bzdw.XXMC}
	    </div>
	     <div style="margin-top: 20px;margin-left: 90%;font-size:14px;">    
                	单位:元
	    </div>
	</div>

<form id="mysave" method="post">
<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:82%;margin:0 auto 30px">
		        	<thead>
				        <tr>
				            
				            <th style="text-align:center; width: 150px" id="tr_pzrq">资产</th>
				            <th style="text-align:center;" id="tr_kmbh">期末余额</th>
				            <th style="text-align:center;" id="tr_zy">年初余额</th>				            
				            <th style="text-align:center; width: 150px" id="tr_kmmc">负债和净资产</th>
				             <th style="text-align:center;" id="tr_kmbh">期末余额</th>	
				            <th style="text-align:center;" id="tr_dfje">年初余额</th>	    
				        </tr>
					</thead>
				    <tbody>
				     <tbody>
				    <c:forEach var="list" items="${list}" varStatus="i">
				 		<tr class="tr_${i.index+1}">
				 			
				 			<td class="num">
				 				${list.ZC}
				 				<input type="hidden" name="czr" value="${login}">
				 				<!-- 各种隐藏域 -->
				 				<input type="hidden" name="zc" value="${list.ZC}"/>
				 				<input type="hidden" name="fzhjzc" value="${list.FZHJZC}"/>
				 				<input type="hidden" name="bbnd" value="${sysDate}">
				 				<input type="hidden" name="bzdw" value="${bzdw.GUID}">
				 				<input type="hidden" name="jzpz" value="${jzpz}">
				 				<input type="hidden" name="ztgid" value="${ztgid}">
				 				<input type="hidden" name="bh" value="${list.HC1}">
				 			</td>
				 			<td class="num">
				 				<input type="text" name=qmye1 class="form-control number" style="text-align:right;" value="${list.QMYE1}">
				 				
				 			</td>
				 			<td class="num">
				 				<input type="text" name=ncye1 class="form-control number" style="text-align:right;" value="${list.NCYE1}">
				 			</td>
				 			<td class="num">
				 				${list.FZHJZC}
				 				
				 			</td>
				 			<td class="num">
				 				<input type="text" name=qmye2 class="form-control number" style="text-align:right;" value="${list.QMYE2}">
				 			</td>
				 			<td class="num">
				 				<input type="text" name=ncye2 class="form-control number" style="text-align:right;" value="${list.NCYE2}">
				 				<input type="hidden" name="bblx" value="1">
				 			
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
		    				location.href="${ctx}/zcfz/zcfzbyear?bbnd="+val+"&bblx=1&jzpz="+jzpz+"&sfjz="+sfjz;
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
			var json = $("#mysave").serializeObject("czr","bblx");  //json对象				
			var jsonStr = JSON.stringify(json);
			$.ajax({
				url:"${ctx}/zcfz/doSave",
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
			var sysDate = $("[name='bbnd']").val();
			var ztgid = $("[name='ztgid']").val();
			var sfjz = $("[name='sfjz']").val();
			var jzpz = $("[name='jzpz']").val();
			location.href = "${ctx}/zcfz/goPrintyear?bbnd="+sysDate+"&jzpz="+jzpz+"&sfjz="+sfjz+"&ztgid="+ztgid;
		});
		//结转凭证条件切换
		 $("#sfjz").change(function(){
		 	var jzpz = $("#jzpz").val();
		 	var val = $(".nd").val();
		 	var sfjz=$(this).val();
		 	if(val!=""&&jzpz!=""&&sfjz!=""){
		 		location.href="${ctx}/zcfz/zcfzbyear?bblx=1&bbnd="+val+"&bblx=1&jzpz="+jzpz+"&sfjz="+sfjz;
		 	}else{
		 		alert("存在空的查询条件!");
		 		return;
		 	}
		 });
		//记账凭证条件切换
		 $("#jzpz").change(function(){
			var jzpz = $(this).val();
		 	var val = $(".nd").val();
		 	var sfjz=$("#sfjz").val();
		 	if(val!=""&&jzpz!=""){
		 		location.href="${ctx}/zcfz/zcfzbyear?bblx=1&bbnd="+val+"&bblx=1&jzpz="+jzpz+"&sfjz="+sfjz;
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
			//点击导出
// 			$("#btn_exp").click(function(){
// 				/* var bblx = $("[name='bblx']").val();
// 				var sysDate = $("[name='ny']").val();
// 				var sszt = $("[name='ztbh']").val();
// 				var jzpz = $("[name='jzpz']").val();
// 				var bzdw = $("[name='bzdw']").val(); */
// 				doExp("","${ctx}/zcfz/expExcel","资产负债表(年度)","${ctx}","");
// 			});
	
	//导出Excel
		$("#btn_exp").click(
				function() {
					var foo = "年度";
					var jzpz = $("#jzpz").val();
			   	 	var val = $(".nd").val();
			   	 	var sfjz=$("#sfjz").val();
			   	 	var ztgid=$("#ztgid").val();
			   	 	var sfjz=$("#sfjz").val();
			   	 	var sfjz=$("#sfjz").val();
			   	    var bbnd = $("[name='bbnd']").val();
					$.ajax({
						type : "post",
						data : {bblx:0,jzpz:jzpz,sfjz:sfjz,ztgid:ztgid,bbnd:bbnd,foo : foo},
						url : "${ctx}/zcfz/expExcel2",
						success : function(val) {
							FileDownload("${ctx}/file/fileDownload","资产负债表--年度.xls", val.url);
						}
					});
				});

	});
</script>
</body>
</html>