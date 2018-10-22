<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>通讯录查询列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.dataTables_scroll{
    	margin-top: 4px !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="container-fluid">
        <div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
					    <tr>
					        <th>序号</th>
					        <th>人员编号</th>
					        <th>姓名</th>
					        <th>手机号</th>
					        <th>办公电话</th>
					        <th>QQ号</th>
					        <th>电子邮箱</th>
					        <th>职务</th>
					        <th>办公地点</th>
					    </tr>
				    </thead>
				    <tbody></tbody>
				</table>
            </div>
    	</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script type="text/javascript">
$(function () {
	var length = getTopFrame().window.sessionRowNumLength;
    table = $('#mydatatables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/index/getTxlList?key=${param.key}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":length,
        "order": [ 4, 'asc' ],
        "serverSide": true,
        "columns": [
		{"data":"_XH",orderable:false,"width":41,"searchable": false,"class":"text-center"},
  		{"data": "RYBH",defaultContent:""},
  	   	{"data": "XM",defaultContent:""},
  	    {"data": "MOBLIE",defaultContent:""},
  	   	{"data": "BDDH",defaultContent:""},
  	   	{"data": "QQ",defaultContent:""},
  	 	{"data": "EMAIL",defaultContent:""},
  	   	{"data":"ZW",defaultContent:""},
  	   	{"data":"BGDD",defaultContent:""}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入人员信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
    });   	
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
});
</script>
</body>
</html>