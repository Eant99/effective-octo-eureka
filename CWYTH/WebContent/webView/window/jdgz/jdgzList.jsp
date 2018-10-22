<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>首页-进度跟踪-更多列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.table>tbody>tr>td
{
padding:5px 4px;
}
</style>
</head>
<body>
<div class="box" style="margin-bottom:10px;">
	<div class='box-content'>
		<div class='responsive-table'>
			<div class='scrollable-area'>
				<table id="mydatatables" class="table table-striped table-bordered table-hover">
					<thead>
						<tr id="header">
							<th style="display:none;"></th>
							<th>序号</th>
							<th>业务类型</th>
				          	<th>编号</th>
				          	<th>名称</th>
				          	<th>业务进度</th>
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
$(function (){
    $('#mydatatables').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/index/jdgz"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 0, 'desc' ],
        "columns": [
           	{"data": "RQ",visible:false},
            {"data":"_XH",'render': function (data, type, full, meta){
            	return data;
        	},"width":41,"searchable": false,orderable:false,"class":"text-center"},
            {"data": "LX",defaultContent:""},
            {"data": "BH",defaultContent:""},
            {"data": "MC",defaultContent:""},
            {"data": "JD",defaultContent:""}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入搜索关键字"
        },
        "dom":"<'clearfix'<'page-left wxts'li><'page-left'f>>t<p>",
        "initComplete": function(){}
    });
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
});
</script>
</body>
</html>