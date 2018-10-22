<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>图片类型设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="hj" style="display: none;">
		<div class="btn-group pull-right btn-group-marginTop" role="group">
			<button type="button" class="btn btn-default" id="btn_add">增加</button>
			<button type="button" class="btn btn-default" id="btn_del">批量删除</button>
		</div>
	</div>
	<div class="container-fluid">
     	<div class='responsive-table'>
        	<div class='scrollable-area'>
	            <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
					    <tr>
							<th><input type="checkbox" class="select-all"/></th>
							<th>序号</th>
							<th>类型编号</th>
							<th>单据名称</th>
							<th>图片类型</th>
							<th>是否必须上传</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	//列表数据
	var columns = [
	    {"data": "LXBH",orderable:false, "render": function (data, type, full, meta){
		 return '<input type="checkbox" class="keyId" name="lxbh" value="' + data + '"/>';
		},"width":10,searchable: false},
		{"data":"_XH",orderable:false,"width":41,searchable: false,"class":"text-center"},
		{"data": "LXBH",defaultContent:""},
           {"data": "DJMC","render":function (data, type, full, meta){
		   	return '<a href="javascript:void(0);" class="btn btn-link btn-look">'+ data +'</a>';
		}},
           {"data": "LXMC",defaultContent:""},
           {"data": "SFBXSC",defaultContent:""},
           {"data": function (data, type, full, meta){
              	return '<div><a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a></div>';
       	},orderable:false,"width":95}
	];
	table = getDataTableByListHj("mydatatables","${ctx}/tplxsz/getPageList",[2,'asc'],columns,0,1,setGroup,"1");

	//添加按钮
	$(document).on("click","#btn_add",function() {
		select_commonWin("${ctx}/tplxsz/getTplxszPage?operateType=C","图片类型设置", "400", "450");
	});
	
	//查看
   	$(document).on("click",".btn-look",function(){
   		var lxbh = $(this).parents("tr").find("[name='lxbh']").val();
   		select_commonWin("${ctx}/tplxsz/getTplxszPage?operateType=L&lxbh="+lxbh,"图片类型设置", "400", "450");
   	});
	
	//单条删除操作
	$(document).on("click",".btn_delxx",function() {
		var lxbh = $(this).parents("tr").find("[name='lxbh']").val();
		doDel("lxbh="+lxbh,"${ctx}/tplxsz/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   			
   		},"1");
	});
	
	//批量删除按钮
	$(document).on("click","#btn_del",function() {
   		var checkbox = $("#mydatatables").find("[name='lxbh']").filter(":checked");
   		if(checkbox.length>0){
   			var lxbh = [];
   			checkbox.each(function(){
   				lxbh.push($(this).val());
   			});
   			doDel("lxbh="+lxbh.join(","),"${ctx}/tplxsz/doDelete",function(val){
   	   			table.ajax.reload();
   	   			//....
   	   		},function(val){
   	   			//....
   	   		},lxbh.length);
   		}else{
   			alert("请选择至少一条信息删除！");
   		}
   	});
	//修改操作
	$(document).on("click",".btn_upd",function() {
		var lxbh = $(this).parents("tr").find("[name='lxbh']").val();
		select_commonWin("${ctx}/tplxsz/getTplxszPage?operateType=U&lxbh="+lxbh,"图片类型设置", "400", "450");
	});
   	$(".wxts").append($(".hj").html());
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