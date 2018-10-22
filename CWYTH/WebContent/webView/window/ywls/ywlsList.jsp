<%@page import="com.googosoft.util.DateUtil"%>
<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>首页-我的业务流水-更多列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
<%
String month = Validate.isNullToDefault(request.getParameter("month"), DateUtil.getMonth()) + "";
%>
</head>
<style type="text/css">
u{
	text-decoration:none;
}
</style>
<body>
<input type="hidden" name="month" value="<%=month%>"/>

<div class="box">
	<div class='box-content' style="padding-bottom: 0px; overflow:visible;">
              <div class='responsive-table'>
                  <div class='scrollable-area'>
				<table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					<thead>
						<tr>
							<th style="display:none;"></th>
							<th>序号</th>
							<th>日期</th>
				          	<th>时间</th>
				          	<th>类型</th>
				          	<th>内容</th>
						</tr>
					</thead>
			      	<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script>
$(function (){
    $('#mydatatables').DataTable({
        ajax: {
        	url: "${ctx}/window/ywls?month=<%=month%>"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 0, 'desc' ],
        "serverSide": true,
        "columns": [
           	{"data":"RQSJ",visible:false},
            {"data":"_XH",'render': function (data, type, full, meta){
            	return data;
        	},"width":41,"searchable":false,orderable:false,"class":"text-center"},
            {"data": "RQ",defaultContent:""},
            {"data": "SJ",defaultContent:""},
            {"data": "LX",defaultContent:""},
            {"data": "SM",defaultContent:"","render": function (data, type, full, meta){
               	if(full.URL == undefined){
               		return data;
               	}
               	else{
            		return '<a href="${ctx}/' + full.URL + '" target="_blank" style="color:#000000;">' + data + '</a>';
               	}
           	}}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入搜索关键字"
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