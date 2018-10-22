<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>预算调整审核</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			<div class="form-group">
						<label>审批状态</label> <select id="drp_shzt" class="form-control select2"
							name="shzt" table="K">
							<option value="">未选择</option>
							<option value="1">通过</option>
							<option value="2">不通过</option>					
						</select>
					</div>
				<div class="form-group">
					<label>单位名称</label>
					<input type="text" id="txt_dwmc" class="form-control" name="dwmc" table="A" placeholder="请输入单位名称">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  table="A" placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>综合查询</button>

				<div class="btn-group pull-right" role="group">
	               
<!-- 	               <button type="button" class="btn btn-default" id="btn_assig">批量赋值</button> -->
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
				            <th>审核意见</th>
				            <th>单据编号</th>
				            <th>单位名称</th>
				            <th>项目名称</th>
				            <th>项目分类</th>
				            <th>项目类型</th>
				            <th>负责人</th>
				            <th>预算金额（万元）</th>
				            <th>调整金额（万元）</th>
				            <th>调整原因</th>
				            <th>调整人</th>
				            <th>调整日期</th>
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
       {"data": "SHYJ",defaultContent:""},
       {"data": "DJBH",defaultContent:""},
       {"data": "DWMC",defaultContent:""},
       {"data": "XMMC",defaultContent:""},
       {"data": "XMFL",defaultContent:""},
       {"data": "XMLX",defaultContent:""},
       {"data": "FZR",defaultContent:""},
       {"data": "YSJE",defaultContent:""},
       {"data": "TZJE",defaultContent:""},
       {"data": "TZYY",defaultContent:""},
       {"data": "TZR",defaultContent:""},
       {"data": "TZRQ",defaultContent:"","class":"text-center"},
       {"data": "SHR",defaultContent:""},
       {"data": "SHRQ",defaultContent:"","class":"text-center"},
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">审核</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">查看</a>';
      },orderable:false,"width":150}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/ystz/getshPageList",[4,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/webView/ysgl/ystz/ystzsh_edit.jsp","C");
   	});
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
   	//审核按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/ysgl/ystz/ystzsh_edit.jsp","C");
   	});
  
	//查看按钮
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		doOperate("${ctx}/webView/ysgl/ystz/ystzsh_view.jsp","C");
	});
	
	//弹窗
	$("#btn_dwbh").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwbh","单位信息","920","630");
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