<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>票据账户列表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
#btns{
	margin-top: 10px !important;	
	}
</style>

<body>
<div class="fullscreen">
    <div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>票据账户</label>
					<input type="text" id="txt_xmlxbh" class="form-control" name="PJZHMC" table="K" placeholder="请输入票据账户">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
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
				            <th style="text-align:center">序号</th>
				            <th style="text-align:center" >票据账户</th>
				            <th style="text-align:center" >空白票据</th>
				            <th style="text-align:center" >已领票据</th>
				            <th style="text-align:center" >已出票票据</th>
				            <th style="text-align:center" >已报销票据</th>
				            <th style="text-align:center" >已作废票据</th>
				            <th style="text-align:center" >合计</th>
				            <th style="text-align:center" >操作</th>
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
<script src="${pageContext.request.contextPath}/static/javascript/public/public-checked.js"></script>
<script>
$(function () {
	var columns = [
	       {"data": "PJZH",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="guid" class="keyId " value="' + data + '" guid = "'+full.PJZH+'">';
	       },"width":10,'searchable': false,"class":"text-center"},
	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	          	return data;},"width":20,"searchable":false,"class":"text-center"},
	       {"data": "PJZHMC",defaultContent:"","width":40,"class":"text-left"},
	       {"data": "KBPJ",defaultContent:"","width":40,"class":"text-right"},
	       {"data": "YLPJ",defaultContent:"","class":"text-right","width":40},
	       {"data": "YCPJ",defaultContent:"","width":40,"class":"text-right"},
	       {"data": "YBXPJ",defaultContent:"","width":40,"class":"text-right"},
	       {"data": "YZFPJ",defaultContent:"","width":40,"class":"text-right"},
	       {"data": "HJ",defaultContent:"","width":40,"class":"text-right"},
	       {"data": "",'render':function(data,type,full,meta){
	          		return '<a href="javascript:void(0);" class="btn btn-link " onclick="getPjlb(this)" zhid="'+full.PJZH+'">票据列表</a>';
	          	},"class":"text-center",orderable:false,"width":80}
	     ];
	    table = getDataTableByListHj("mydatatables","${ctx}/pjgl/rcyw/pjlb/getPjzhList",[2,'asc'],columns,0,1,setGroup);
	
});
$(function() {	
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
function getPjlb(obj){
	var zhid=$(obj).attr('zhid');
	window.location.href  = "${ctx}/pjgl/rcyw/pjlb/goPjListPage?pjzh="+zhid;
}
</script>
</html>