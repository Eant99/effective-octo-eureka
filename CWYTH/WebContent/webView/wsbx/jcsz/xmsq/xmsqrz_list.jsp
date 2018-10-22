<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目授权日志</title>

<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
	<div class="fullscreen">
	<input type="hidden" id="xmbh3" value="${xmbh3}">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				
				<div class="search-simple">
					
					<div class="form-group">
						<label>授权人</label>
						<input type="text" id="txt_sqrmc" class="form-control" name="sqrmc" table="K" placeholder="请输入授权人姓名">
					</div>
					<div class="form-group">
						<label>被授权人</label>
						<input type="text" id="txt_bsqrmc" class="form-control" name="bsqrmc" table="K" placeholder="请输入被授权人姓名">
					</div>
					<div class="form-group">
						<label>起止时间</label>
						<div class="input-group">
							<input type="text" id="txt_kssj" class="form-control date" name="kssj" types="TL_date" table="K" data-format="yyyy-MM-dd" placeholder="开始时间">
							<span class='input-group-addon'>
								<i class="glyphicon glyphicon-calendar"></i>
							</span>
						</div>
						<label>-</label>
						<div class="input-group">
						<input type="text" id="txt_jzsj" class="form-control date" name="jzsj" types="TH_date"  table="K" data-format="yyyy-MM-dd" placeholder="截止时间">
							<span class='input-group-addon'>
								 <i class="glyphicon glyphicon-calendar"></i>
							</span>
						</div>
					</div>
					 <button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
					<div class="btn-group pull-right" role="group">
				   <button type="button" class="btn btn-default" id="btn_back">返回</button>
				   </div>
				</div>
				
			</form>
		</div>
		<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'>
					<table id="mydatatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>项目编号</th>
								<th>项目名称</th>
								<th>负责人</th>
								<th>授权人</th>
								<th>被授权人</th>
								<th>开始时间</th>
								<th>截止时间</th>
								<th>允许二次授权</th>
								<th>操作时间</th>
<!-- 								<th>取消授权时间</th> -->
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/static/include/public-list-js.inc"%>
	<%@include file="/static/include/public-list-css.inc"%>
	<script>
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "XMBH",defaultContent:""},
       {"data": "XMMC",defaultContent:""},
       {"data": "FZRMC",defaultContent:""},
       {"data": "SQRMC",defaultContent:""},
       {"data": "BSQRMC",defaultContent:""},
       {"data": "KSSJ",defaultContent:"","class":"text-center"},
       {"data": "JZSJ",defaultContent:"","class":"text-center"},
       {"data": "SFYXECSQ",defaultContent:""},   
       {"data": "CZRQ",defaultContent:""},  //取消授权时间和操作时间用的是一个字段CZSJ。
//        {"data": "CZRQ",defaultContent:"",'render':function(data, type, full, meta){
//     	   if(full.CZLX=='0'){//czlx操作类型：撤销授权0(新增授权，修改授权不填，为空)
//     		   return full.CZRQ;
//     	   }
//        },orderable:false,"width":220,"class":"text-center"}   
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xmsq/getRZPageList?xmbh3=${xmbh3}",[2,'asc'],columns,0,1,setGroup);	
});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
//返回按钮
$("#btn_back").click(function(){
	window.location.href = "${ctx}/webView/wsbx/jcsz/xmsq/xmsq_list.jsp";
});
</script>
</body>
</html>