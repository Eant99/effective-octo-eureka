<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>固定资产管理系统发布系统消息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
	<div class="container-fluid" style="padding:0px;">
        <div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
				        <th>序号</th>
				        <th>标题</th>
				        <th>发布人</th>
				        <th>发布时间</th>
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
   	$(document).on("click",".look",function(){
   		var id = $(this).attr("gid");
   		window.location.href="${pageContext.request.contextPath}/window/goTzggEdit?gid="+id;
//    		select_commonWin("${pageContext.request.contextPath}/window/goTzggEdit?gid="+id,"通知公告","920","630");
   	});
   	$('#mydatatables').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/window/getTzggList"
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 3, 'desc' ],
        "serverSide": true,
        "columns": [
            		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	   		return data;
            		},"searchable": false,"width":41,"class":"text-center"},
            	   	{"data": "TITLE",'render': function (data, type, full, meta){
            	   		return '<a href="javascript:void(0)" gid="'+full.GID+'" class="look">'+full.TITLE+'</a>';
            		},"searchable": false},
            	   	{"data": "FBRMC",defaultContent:"",orderable:false,"width":300},
            	   	{"data": "FBSJ",defaultContent:"","width":200},
//             	   	{"data": "GID",orderable:false,'render': function (data, type, full, meta){
//             	   		return '<a href="javascript:void(0)" gid="'+full.GID+'" class="look">查看</a>';
//             		},"searchable": false,"width":55},
            	],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入标题或发布人关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
        "initComplete": function(){
        }
    });
 	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
});
</script>
</body>
</html>