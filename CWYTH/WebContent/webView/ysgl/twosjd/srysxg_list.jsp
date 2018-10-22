<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入预算修改</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<div class="search-simple">
					<div class="form-group">
						<label>审核状态</label> <select id="drp_shzt" class="form-control select2"
							name="shzt" table="K">
							<option value="">未选择</option>
							<option value="1">通过</option>
							<option value="2">不通过</option>					
						</select>
					</div>
					<div class="form-group">
						<label>单位名称</label> <input type="text" id="txt_djbh"
							class="form-control" name="djbh" table="K" placeholder="请输入单位名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
					<div class="btn-group pull-right" role="group">
<!-- 						<button type="button" class="btn btn-default" id="btn_add">增加</button>						 -->
<!-- 						<button type="button" class="btn btn-default" id="btn_TJ">提交</button> -->
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
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>单据编号</th>
								<th>单位名称</th>
								<th>编制年度</th>
								<th>上年预算总额（万元）</th>
								<th>上年收入总额（万元）</th>
								<th>本年预算总额（万元）</th>
								<th>修改人</th>
								<th>修改时间</th>
								<th>审核意见</th>
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
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "DJBH",defaultContent:""},
       {"data": "DWMC",defaultContent:""},
       {"data": "BZND",defaultContent:""},
       {"data": "SNYSZE",defaultContent:"100.0000","class":"text-right"},
       {"data": "SNSRZE",defaultContent:"120.0000","class":"text-right"},
       {"data": "BNYSZE",defaultContent:"130.0000","class":"text-right"},
       {"data": "XGR",defaultContent:""},
       {"data": "XGSJ",defaultContent:""},
       {"data": "SHYJ",defaultContent:""},
       {"data": "GUID",class:"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">修改</a>';
      },orderable:false,"width":220}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/onesrysxz/getSrysxgPageList",[2,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/webView/ysgl/twosjd/srysxg_edit.jsp", "C");
   	});
	
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功！");
   	});
   
  
	//查看
	$(document).on("click",".btn_upd",function(){
		doOperate("${ctx}/webView/ysgl/twosjd/srysxg_edit.jsp", "C");
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