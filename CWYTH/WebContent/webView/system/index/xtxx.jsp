<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>通知消息</title>
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
					    	<th><input type="checkbox" class="select-all" /></th>
					        <th>序号</th>
					        <th>发布人</th>
					        <th>发布时间</th>
					        <th>内容</th>
					        <th>状态</th>
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
<script type="text/javascript">
$(function () {
	var length = getTopFrame().window.sessionRowNumLength;
    table = $('#mydatatables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/index/getTzxxList"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":length,
        "order": [ 5, 'asc' ],
        "serverSide": true,
        "columns": [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
			return '<input type="checkbox" class="keyId" name="keyId" pzbh="'+full.PZBH+'" pzlx="'+full.PZZ+'" value="' + data + '">';
		},"width":10,'searchable': false},
		{"data":"_XH",orderable:false,"width":41,"searchable": false,"class":"text-center"},
  		{"data": "FBRXM",defaultContent:""},
  	   	{"data": "FBSJ",defaultContent:""},
  	    {"data": "XXNR",defaultContent:""},
  	  	{"data": "ZT",defaultContent:""},
  	  	{"data": "SJID",defaultContent:"",orderable:false, 'render': function (data, type, full, meta){
  	  		return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
	       }}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入通知信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
    });   	
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$("").css("position","absolute");
	$("div.button").prop("innerHTML","通知信息列表").css("font-size","14px");
	$(document).on("click",".btn_look",function(){
		var guid = $(this).parents("tr").find("[name=keyId]").val();
		var pzbh=$(this).parents("tr").find("[name=keyId]").attr("pzbh");
		var pzlx=$(this).parents("tr").find("[name=keyId]").attr("pzlx");
		$.ajax({
			url:"${pageContext.request.contextPath}/index/doUpdateTzxx",
			data:"guid="+guid+"&pzbh="+pzbh+"&pzlx="+pzlx,
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				if(data.success){
					var ifrurl="${pageContext.request.contextPath}/kjhs/pzxx/pzlr/pageSkip?pageName=pzlr&pzbh="+pzbh+"&pzlx="+pzlx;
					getTopFrame().tzxx(ifrurl);
					var winId = getTopFrame().layer.getFrameIndex(window.name);
					close(winId);
				}
			}
		});
// 		getIframWindow("${param.pname}").tzxx(ifrurl);
// 		var $obj = getIframWindow("${param.pname}").find("a[data-mgid='04']").clone().attr("ifrurl",ifrurl);
// 		getIframWindow("${param.pname}").kz($obj);
// 		getIframWindow("${param.pname}").n($obj);
//    		getIframWindow("${param.pname}").find("a[ifrid]:first-child").parent().removeClass("active");
//    		getIframWindow("${param.pname}").find("a[ifrid='0401']").parent().addClass("active");
//    		getIframWindow("${param.pname}").find("a.menuList[ifrid='040101']").addClass("active");
	});
});
</script>
</body>
</html>