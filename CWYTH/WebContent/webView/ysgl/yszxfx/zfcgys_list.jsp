<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>政府采购预算汇总</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox">
			<form id="myform" class="form-inline" role="form" action="">
				<div class="search-simple">
					
					</div>
					<!-- <div class="form-group">
						<label>项目名称</label> <input type="text" id="txt_mc"
							class="form-control input-radius" name="xmmc" table="K"
							placeholder="请输入项目名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查 询
					</button> -->
					<div class="btn-group pull-right" role="group">
						<button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
	               <button type="button" class="btn btn-default" id="btn_print" onclick="PreviewMytable();">打印</button>
					</div>
				</div>
			</form>
		</div>
		<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'>
					<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
						<center><h4><strong>政府采购预算汇总表</strong></h4></center>
						<div class="search-simple">	
							<!-- <div class="form-group"> 
								<label>填报部门</label>					
 								<input type="text" readonly class="form-control input-radius" name="sbnd"  id="txt_sbnd" placeholder="请输入填报部门">
				 			</div>  -->
				 			<div class="pull-right">
								<label>单位：万元</label>
				 			</div>
						</div>
					</form>
				
					<table id="mydatatables" class="table table-striped table-bordered">
						<thead>
							<tr>
		        			<td rowspan="2"></td>
		        			<th style="text-align: center;" colspan="4" rowspan="2">采购项目</th>
		        			<th style="text-align: center;" colspan="3">2018年预算安排</th>
		        		</tr>
		        		<tr>
		        			
		        			<th style="text-align: center;" rowspan="2">计划购置数</th>
		        			<th style="text-align: center;" rowspan="2">单价（元）</th>
				            <th style="text-align: center;" rowspan="2">采购预算金额</th>
		        		</tr>
				        <tr>
				        	<th style="text-align: center;"><input type="checkbox" class="select-all" /></th>
				        	<th style="text-align: center;">序号</th>
				            <th style="text-align: center;">品目编码</th>
				            <th style="text-align: center;">品目名称</th>
				            <th style="text-align: center;">设备分类</th>
				        </tr>
						</thead>
						 <tbody >
				    </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
	$(function() {
// 		//联想输入提示
// 		$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
		//列表数据
	   var columns = [
		   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
		         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
		       },"width":10,'searchable': false},
	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	          	return data;},"width":41,"searchable":false,"class":"text-center"},  
	       {"data": "PMBM",defaultContent:"","class":"text-left"},
	       {"data": "PMMC",defaultContent:"","class":"text-left"},       
	       {"data": "SBFL",defaultContent:"","class":"text-left"},
	       {"data": "JHGZS",defaultContent:"","class":"text-left"},
	       {"data": "DJ",defaultContent:"","class":"text-right"},
	       {"data": "CGYSJE",defaultContent:"","class":"text-right"},
	       ]
	console.log("+++++++++++运行了");
	table = getDataTableByListHj("mydatatables","${ctx}/yszxfx2/getPageList4",[2,'asc'],columns,0,1,setGroup);
	
	//双击事件
	$("tbody tr").bind("dblclick",function(){
		//var xmmc = $(this).text();
		var pmmc=$(this).children('td').eq(3).text();
		console.log("----------"+pmmc);											
		doOperate("${ctx}/yszxfx2/gozfcgysListPage?pmmc="+pmmc);
	});	
	//打印
	$(document).on("click","#btn_print",function(){
   		doOperate("${ctx}/yszxfx2/ZfcgPrint");
	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/yszxfx2/expExcel4?guid="+guid.join("','"),"政府采购预算表","${pageContext.request.contextPath}",guid.join(","));
   	});
	  
	});
	$(function() {
		//列表右侧悬浮按钮
		$(window).resize(
				function() {
					$("div.dataTables_wrapper").width($("#searchBox").width());
					heights = $(window).outerHeight()
							- $(".search").outerHeight()
							- $(".row.bottom").outerHeight() - 20
							- $(".dataTables_scrollHead").outerHeight();
					$(".dataTables_scrollBody").height(heights);
					table.draw();
				});
		
	});
</script>
</body>
</html>
