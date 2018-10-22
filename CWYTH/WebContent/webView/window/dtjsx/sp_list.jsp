<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<title>首页-业务草稿列表</title>
	<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="box">
	<div class='box-content' style="padding-bottom: 0px; overflow:visible;">
              <div class='responsive-table'>
                  <div class='scrollable-area'>
				<table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0px;width:100%;">
					<thead>
						<tr id="header">
							<th style="width:20px;">序号</th>
							<th>名称</th>
							<th style="width:20px;">数量</th>
						</tr>
					</thead>
			      	<tbody>
				      	<c:if test="${list.size() > 0 }" >
					      	<c:forEach var="list" items="${list}" varStatus="i" >
					      		<tr path="${list.URL }" mc="${list.MKMC }" bh="${list.MKBH }">
						      		<td>${i.index+1 }</td>
						      		<td>${list.MKMC }</td>
						      		<td>${list.SL }</td>
					      		</tr>
					      	</c:forEach>
				      	</c:if>
			      	</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	$(".tool-fix").hide();
//     $('#mydatatables').DataTable({
//         ajax: {
//         	url: "${pageContext.request.contextPath}/window/dtjxx"
//         },
//         "lengthMenu":getTopFrame().window.sessionRowNumLength,
//         "order": [ 1, 'asc' ],
//         "serverSide": true,
//         "columns": [
// 			{"data": "PATH",orderable:false, "render": function (data, type, full, meta){
// 				return '<input type="checkbox" class="keyId" name="url" value="' + data + '" mc = "' + full.MC +'">';  	
// 			},"width":10,'searchable': false},
//             {"data":"_XH",'render': function (data, type, full, meta){
//             	return data;
//         	},"width":41,"searchable": false,orderable:false},
//             {"data":"MC",defaultContent:""},
//             {"data":"SL","width":60,defaultContent:""}
//         ],
//         "language":{
//             "search":"",
//             "searchPlaceholder":"请输入搜索关键字"
//         },
//         "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
//         "initComplete": function(){
//         }
//     });
//         $("input[type=search]").parent("label").addClass("zhxxcx");
//     	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    	 $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	    	var val = "${pageContext.request.contextPath}"+$(this).attr("path");
    	    	var mc = $(this).attr("mc");
    	    	var bh = $(this).attr("bh");
    	    	var winId = getTopFrame().layer.getFrameIndex(window.name);
    	    	parent.openRightWin(val,bh,mc);
    	    	close(winId);
    	    });
});


</script>
</body>
</html>