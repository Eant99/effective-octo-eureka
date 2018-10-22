<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>首页——我的名下资产-更多</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="container-fluid">
		<div class="list">
	        <div class='responsive-table'>
	            <div class='scrollable-area'>
	                 <table id="mydatatables" class="table table-striped table-bordered">
					    <thead>
					    <tr>
					        <th>序号</th>
					        <th>资产编号</th>
					        <th>资产名称</th>
					        <th>入账日期</th>
					        <th>总价</th>
					        <th>存放地点</th>
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
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
   	$(document).on("click",".look",function(){
   		var yqbh = $(this).attr("yqbh");
   		select_commonWin("${pageContext.request.contextPath}/zjb/goLookPage?yqbh="+yqbh,"资产信息",1200,630,"yes");
   	});
   	$('#mydatatables').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/window/getZcxxList"
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	   return data;
            },"searchable": false,"width":41,"class":"text-center"},
            {"data": "YQBH",defaultContent:"",},
            {"data": "YQMC",'render': function (data, type, full, meta){
            	   return '<a href="javascript:void(0)" yqbh="'+full.YQBH+'" class="look">'+full.YQMC+'</a>';
            },"searchable": false},
            {"data": "RZRQ",defaultContent:"",},
            {"data": "ZZJ",defaultContent:"","class":"text-right",},
            {"data": "BZXX",defaultContent:"",},
            {"data": "YQBH",orderable:false,'render': function (data, type, full, meta){
            	   return '<a href="javascript:void(0)" yqbh="'+full.YQBH+'" class="look">查看</a>';
            },"searchable": false,},
            ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入资产编号或资产名称"
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