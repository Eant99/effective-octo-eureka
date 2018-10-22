<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>常见问题维护</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="hj" style="display: none;">
	    <div class="btn-group pull-right btn-group-marginTop" role="group">
	        <button class="btn btn-default" id="btn_add">增加</button>
	        <button class="btn btn-default" id="btn_del" >批量删除</button>
<!-- 	        <button class="btn btn-default" id="btn_sel" >查看</button> -->
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
				        <th>问题</th>
<!-- 				        <th>建档人</th>  -->
				        <th>最近修改时间</th>
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
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
		       return '<input type="checkbox" class="keyId" name="id" value="' + data + '">';
		},"width":20,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		   	   return data;
		},"width":41,"searchable": false,"class":"text-center"},
		{"data": "TITLE",defaultContent:"","width":500,},
// 		{"data": "JDR","render":function (data, type, full, meta){
// 			   return '<a href="javascript:void(0);" class="btn  btn-link rylook" rybh="'+full.JDRBH+'" path="${ctx}">'+ data +'</a>';

// 		},"width":500,'searchable': false},
		{"data": "UPDDATE",defaultContent:"","width":150},
		{"data":function (data, type, full, meta){
		   	   return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":95}
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/cjwt/getPageList",[2,'asc'],columns,0,1,setGroup,"1");
   	
   	//添加按钮
   	$(document).on("click","#btn_add",function(){
   		select_commonWin("${ctx}/cjwt/goCjwtEditPage?operateType=C","常见问题维护", "770", "690");
   	});
   	
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
		var id = $(this).parents("tr").find("[name='id']").val();
		select_commonWin("${ctx}/cjwt/goCjwtEditPage?operateType=U&id="+id,"帮助信息设置", "770", "690");
	});
   	
    //查看按钮
   	$(document).on("click","#btn_sel",function(){
//    		var id = $(this).parents("tr").find("[name='id']").val();
// 		select_commonWin("${ctx}/cjwt/goCjwtEditPage?operateType=L&id="+id,"帮助信息设置", "770", "690");
		window.location.href="${ctx}/cjwt/goCjwtLookPage";
	});
   	
  	//单条删除操作
	$(document).on("click",".btn_delxx",function() {
		var id = $(this).parents("tr").find("[name='id']").val();
		doDel("id="+id,"${ctx}/cjwt/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   			
   		},"1");
	});

	//批量删除按钮
	$(document).on("click","#btn_del",function() {
   		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
   		if(checkbox.length>0){
   			var id = [];
   			checkbox.each(function(){
   				id.push($(this).val());
   			});
   			doDel("id="+id.join(","),"${ctx}/cjwt/doDelete",function(val){
   	   			table.ajax.reload();
   	   		},function(val){
   	   		},id.length);
   		}else{
   			alert("请选择至少一条信息删除");
   		}
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