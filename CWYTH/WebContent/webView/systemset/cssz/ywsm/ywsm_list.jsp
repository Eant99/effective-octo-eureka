<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>业务说明设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
    <div class="container-fluid">
        <div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
				        <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>业务编号</th>
				        <th>业务类型</th>
				        <th>业务名称</th>
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
$(function (){
	//列表数据
   	var columns = [
		{"data": "ID",orderable:false, "render": function (data, type, full, meta){
		           return '<input type="checkbox" class="keyId" name="id" value="' + data + '" mkbh = "'+full.MKBH+'">';
		},"width":20,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		   	   return data;
		},"width":41,"searchable": false,"class":"text-center"},
		{"data": "MKBH",defaultContent:""},
		{"data": "MKLX",defaultContent:"",orderable:false},
		{"data": "MKMC","render":function (data, type, full, meta){
			   	return data;
		}},
		{"data":function (data, type, full, meta){
		   	return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>';
		},orderable:false,"width":50}
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/ywsm/getYwsmJson",[2,'asc'],columns,0,1,setGroup,"1");
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
		var id = $(this).parents("tr").find("[name='id']").val();
		select_commonWin("${ctx}/ywsm/getYwsm?operateType=U&id="+id,"业务说明设置", "770", "690");
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