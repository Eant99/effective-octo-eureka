<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<title>首页-待办事项-更多列表</title>
	<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="box">
	<div class='box-content' style="padding-bottom: 0px; overflow:visible;">
              <div class='responsive-table'>
                  <div class='scrollable-area'>
				<table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0px;width:100%;">
					<thead>
						<tr>
							<th>序号</th>
							<th>申请单号</th>
							<th>名称</th>
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
$(function (){
    $('#mydatatables').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/window/dbsx"
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data":"_XH",'render': function (data, type, full, meta){
            	return data;
        	},"width":41,"searchable": false,orderable:false,"class":"text-center"},
            {"data":"DBH",defaultContent:""},
            {"data":"MC",defaultContent:""},
            {"data":"MC","width":60,orderable:false,'render':function(data,type,full,meta){
            	return '<button type='button' class="btn btn-link dbsx" data-url="${pageContext.request.contextPath}/webView/ywsh/shMain.jsp?pageUrl='+full.TZLJ+'?djbh='+full.DBH+'&sqmkbh='+full.SQMKBH+'" data-mkbh="'+full.MKBH+'" data-mkmc="业务审核" >处理</button>';
            }},
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
    	$(document).on("click",".dbsx",function(){
			var winId = getTopFrame().layer.getFrameIndex(window.name);
    		getIframWindow("${param.pname}").openRightWin($(this).attr("data-url"),$(this).attr("data-mkbh"),$(this).attr("data-mkmc"));
	    	close(winId);
    	});
});


</script>
</body>
</html>