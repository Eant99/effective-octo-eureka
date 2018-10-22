<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入预算查询</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	      <div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			<div class="form-group">
						<label>状态</label> <select id="drp_shzt" class="form-control select2"
							name="shzt" table="K">
							<option value="">未选择</option>
							<option value="1">通过</option>
							<option value="2">未通过</option>					
						</select>
					</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh" table="K" placeholder="请输入单据编号">
				</div>
				<div class="form-group">
					<label>单位名称</label>
					<input type="text" id="txt_dwmc" class="form-control" name="dwmc"  table="K" placeholder="请输入单位名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
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
				            <th>审核状态</th>
				            <th>单据编号</th>
				            <th>单位名称</th>
				            <th>编制年度</th>
				            <th>上年预算总额（万元）</th>
				            <th>上年收入总额（万元）</th>
				            <th>本年预算总额（万元）</th>
				            <th>填报人</th>
				            <th>填报时间</th>
				            <th>审核人</th>
				            <th>审核日期</th>
				            <th>操作</th>
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
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "SHZT",defaultContent:"","class":"text-center"},
       {"data": "DJBH",defaultContent:""},
       {"data": "DWMC",defaultContent:""},
       {"data": "BZND",defaultContent:""},
       {"data": "SNYSZE",defaultContent:""},
       {"data": "SNSRZE",defaultContent:""},
       {"data": "BNYSZE",defaultContent:""},
       {"data": "TBR",defaultContent:""},
       {"data": "TBSJ",defaultContent:"","class":"text-center"},
       {"data": "SHR",defaultContent:""},
       {"data": "SHRQ",defaultContent:"","class":"text-center"},
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_delxx">查看</a>';
      },orderable:false,"width":120}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/yscx/getsrPageList",[4,'asc'],columns,0,1,setGroup);
	
  	
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/jsxxs/expExcel?treedwbh=${dwbh}","预算审核信息","${ctx}",guid.join(","));
   	});
   	
	//查看按钮
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		doOperate("${ctx}/webView/ysgl/yscx/sryscx_view.jsp","C");
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