<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目查询</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<center><h4><strong>入库项目情况表</strong></h4></center>
			<div class="search-simple pull-right" style="margin-top:5px;">
				<div class="form-group">
				
						<select class="form-control input-radius" id="sel" style="width:100px;">
							<option value="lb">列&emsp;表</option>
							<option value="bar">柱状图</option>
							<option value="pie">饼状图</option>
						</select>
						<center><h5>单位：万元</strong></center>
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
				            <th rowspan="2"><input type="checkbox" class="select-all"/></th>
				            <th rowspan="2">序号</th>
				            <th rowspan="2">单位名称</th>
				            <th colspan="2">合计</th>
				            <th colspan="2">修缮类</th>
				            <th colspan="2">科研平台类</th>
				            <th colspan="2">教学平台类</th>
				            <th colspan="2">学科建设类</th>
				            <th colspan="2">公共服务平台</th>
				            <th colspan="2">人才团队建设</th>
				        </tr>
				         <tr>
				            <th>金额（元）</th>
				            <th>数量</th>
				            <th>金额（元）</th>
				            <th>数量</th>
				            <th>金额（元）</th>
				            <th>数量</th>
				            <th>金额（元）</th>
				            <th>数量</th>
				            <th>金额（元）</th>
				            <th>数量</th>
				            <th>金额（元）</th>
				            <th>数量</th>
				            <th>金额（元）</th>
				            <th>数量</th>
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
<script>
$(function () {
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:""},
       {"data": "SBDW",defaultContent:"山东大学",'render': function (data, type, full, meta){
    	   return '<a href="javascript:void(0);" class="btn btn-link btn_look">'+full.SBDW+'</a>';}
       },
       {"data": "FSYJ",defaultContent:"60000.00","class":"text-right"},
       {"data": "FSYJ",defaultContent:"60","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10000.00","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10000.00","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10000.00","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10000.00","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10000.00","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10000.00","class":"text-right"},
       {"data": "FSYJ",defaultContent:"10","class":"text-right"},
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xmsb/xmsb/getPageList",[2,'asc'],columns,0,1,setGroup);
   	$(".btn_look").click(function(){
   		location.href="${ctx}/webView/xmgl/cxtj/xmtj/school_view.jsp";
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功");
   	});
	//查询
	$("#btn_search").click(function(){
		window.location.reload();
	});
	//视图切换
	$("#sel").change(function(){
		var view = $(this).val();
		if(view=="lb"){
			return;
		}
		if(view=="bar"){
			location.href="${ctx}/webView/xmgl/cxtj/xmtj/zzt_view.jsp";
			return;
		}
		if(view=="pie"){
			location.href="${ctx}/webView/xmgl/cxtj/xmtj/pzt_view.jsp";
			return;
		}
	});
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
</script>
</body>
</html>