<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>预算执行分析</title>
<%@include file="/static/include/public-list-css.inc"%>

</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					 <label>选择日期</label>
	             	<input  name="" class="input_info date form-control" style="width:100px;" value="2017-10-01" data-format="yyyy-MM-dd"/>
					<label>至</label><input  name="" class="input_info date form-control" style="width:100px;" value="2017-10-31" data-format="yyyy-MM-dd"/>
				</div>			
				<button type="button" class="btn btn-primary" id="btn_search1"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>项目名称</th>
				            <th>项目分类</th>
				            <th>项目类型</th>
				            <th>支出预算金额（万元）</th>
				            <th>实际执行（万元）</th>
				            <th>执行率</th>			           			            
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var d = new Date();
	var date = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
	
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false,defaultContent:"001", 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":150,"searchable":false,"class":"text-center"},
       {"data": "DJBH",defaultContent:"修路"},
       {"data": "XMFL",defaultContent:"修路"},
       {"data": "XMLX",defaultContent:"修路"},
       {"data": "ZCYSJE",defaultContent:"100.0000","class":"text-right"},
       {"data": "SJZX",defaultContent:"50.0000","class":"text-right"},
       {"data": "ZXL",defaultContent:"50%","class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/yszxfx/getPageList",[2,'asc'],columns,0,1,setGroup);
	
  	//保存按钮
   	$("#btn_save").click(function(){
   		alert("保存成功！");
   	});
 	$("#btn_search1").click(function(){
 		window.location.href = "${ctx}/webView/ysgl/yszxfx/yszxfx_list.jsp";
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/jsxxs/expExcel?treedwbh=${dwbh}","收入预算修正信息","${ctx}",guid.join(","));
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	doOperate("${ctx}/jsxxs/goJsxxPage?guid="+guid,"U");
   	});
  
   
	
	
});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</body>
</html>