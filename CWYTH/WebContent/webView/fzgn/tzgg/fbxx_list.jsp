<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>通知公告</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-bottom: 2px;padding-top: 8px">
    		<div class="search-simple">
				<div class="form-group">
					<label>标&emsp;&emsp;题</label>
					<input type="text" id="txt_title" class="form-control input-radius" name="title" table="K" placeholder="请输入标题">
				</div>
				<div class="form-group">
					<label>发&ensp;布&ensp;人</label>
					<input type="text" id="txt_fbr" class="form-control input-radius" name="fbr" table="K" placeholder="请输入发布人">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button type="button" id="btn_add" class="btn btn-default">增加</button>
	                <button type="button" id="btn_del" class="btn btn-default">批量删除</button>
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
				        <th>标题</th>
				        <th>发布人</th>
				        <th>发布时间</th>
				        <th>是否展示</th>
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
$(function () {
   	//添加按钮
   	$(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		select_commonWin("${ctx}/fbxx/goEditPage?operateType=C","通知公告","770","690");
   	});
   	//修改操作
   	$(document).on("click",".btn_upd",function(){
		var gid = $(this).parents("tr").find("[name='gid']").val();
		select_commonWin("${ctx}/fbxx/goEditPage?operateType=U&gid="+gid,"通知公告", "770", "690");
	});
   	//查看操作
   	$(document).on("click",".btn_look",function(){
		var gid = $(this).parents("tr").find("[name='gid']").val();
		select_commonWin("${ctx}/window/goTzggEdit?operateType=L&gid="+gid,"通知公告", "920", "630");
	});
 	//单条删除操作
	$(document).on("click",".btn_delxx",function(){
   		var gid = $(this).parents("tr").find("[name='gid']").val();
   		doDel("gid="+gid,"${ctx}/fbxx/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='gid']").filter(":checked");
   		if(checkbox.length>0){
   			var gid = [];
   			checkbox.each(function(){
   				gid.push($(this).val());
   			});
   			doDel("gid="+gid.join(","),"${ctx}/fbxx/doDelete",function(val){
   	   			table.ajax.reload();
   	   		},function(val){
   	   		},gid.length);
   		}else{
   			alert("请选择至少一条信息删除！");
   		}
   	});
	//列表数据
   	var columns = [
		{"data": "GID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="gid" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
		{"data": "TITLE","render":function (data, type, full, meta){
		   	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link">'+ data +'</a></div>';
	    }},
	   	{"data": "FBR",defaultContent:""},
	   	{"data": "FBSJ",defaultContent:"","class":"text-center"},
	   	{"data": "SFZS",defaultContent:"","width":30,"class":"text-left"},
	   	{"data":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,width:95}
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/fbxx/getPageList",[4,'desc'],columns,0,1,setGroup);
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