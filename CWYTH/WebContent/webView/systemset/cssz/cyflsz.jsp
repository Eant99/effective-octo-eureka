<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>常用分类设置表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="hj">
		<div class="alert alert-info pull-left" style="padding: 2px;margin-top:4px;margin-left: 20px;">
			<span><b>温馨提示：</b>&emsp;每种类型最多只能添加五条数据!</span>
		</div>
		<div class="btn-group pull-right btn-group-marginTop" role="group" style="margin-right: 20px;">
		 	<button type="button" class="btn btn-default" id="btn_add">增加</button>
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
				        <th>名称</th>
				        <th>分类号</th>
				        <th>分类名称</th>
				        <th>排序序号</th>
				        <th>类型</th>
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
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	//添加按钮
	$(document).on("click","#btn_add",function() {
		select_commonWin("${ctx}/cyflsz/getEditPage?operateType=C&lx=1","常用分类设置", "400", "450");
	});
	//修改操作
	$(document).on("click",".btn_upd",function() {
		var gid = $(this).parents("tr").find("[name='gid']").val();
		select_commonWin("${ctx}/cyflsz/getEditPage?operateType=U&lx=1&gid="+gid,"常用分类设置", "400", "450");
	});
 	//删除
	$(document).on("click",".btn_delxx",function(){
   		var gid = $(this).attr("gid");
   		doDel("gid="+gid,"${ctx}/cyflsz/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
	//表数据
	table = $('#mydatatables').DataTable({
   	    ajax: {
   	    	url: "${pageContext.request.contextPath}/cyflsz/getPageList?lx=1",//获取数据的方法
   	    },
   	    "pagingType":"full_numbers",
   	    "lengthMenu":[10],
   	    "order": [ 6, 'asc,pxxh asc' ],
   	    "serverSide": true,
   	    "scrollXOption": true,
   	    "scrollYOption": true,
   	    "columns": [
   	        {"data": "GID",orderable:false, "render": function (data, type, full, meta){
		       	return '<input type="checkbox" class="keyId" name="gid" value="' + data + '">';
		    },"width":10,'searchable': false},
			{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		   		return data;
			},"width":41,"searchable": false,"class":"text-center"},
		   	{"data": "MC",defaultContent:""},
		   	{"data": "FLH",defaultContent:""},
		   	{"data": "FLMC",defaultContent:""},
		   	{"data": "PXXH",defaultContent:""},
		   	{"data": "LX",defaultContent:""},
		   	{"data": "GID",'render':function (data, type, full, meta){
		   		return '<a  class="btn btn-link btn_upd" gid = "'+full.GID+'">编辑</a>|<a  class="btn btn-link btn_delxx" gid = "'+full.GID+'">删除</a>';
			},orderable:false,"width":95}
   	    ],
   	    "language":{
   	        "lengthMenu": "_MENU_ 条记录每页",
   	        "zeroRecords": "没有找到记录",
   	        "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
   	        "infoEmpty": "无记录",
   	        "infoFiltered": "(从 _MAX_ 条记录过滤)",
   	        "paginate": {
   	            "previous": "上一页",
   	            "next": "下一页",
   	            "first": "首页",
   	            "last": "末页",
   	            "jump":"跳转"
   	        },
   	    },
   	    "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
   	    "initComplete": function(){
   	    }
   	});
});

</script>
</html>