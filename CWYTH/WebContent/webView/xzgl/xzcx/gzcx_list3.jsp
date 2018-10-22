<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>工资查询</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body style="height: 100%">
<div class="" style="background-color: white;">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 0px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>人员编号</label>
			        <input type="text" id="" class="form-control input-radius" name="rybh"  table="t" placeholder="请输入人员编号">
				</div>
				<div class="form-group">
					<label>人员姓名</label>
			        <input type="text" id="" class="form-control input-radius" name="xm" table="t" placeholder="请输入人员姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
			</div>
		</form>
	</div>

	<div class="container-fluid abc" style="overflow: auto;">
		<div class='responsive-table' >
			<div class='scrollable-area'  > 
				<table id="mydatatables" class="table table-striped table-bordered" >
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
				            <th style="text-align:center;">姓名</th>
				            <th style="text-align:center;">证件号码</th>			            
				            <th style="text-align:center;">所得项目</th>			            
				            <th style="text-align:center;">所得期间（起）</th>			            
				            <th style="text-align:center;">所得期间（止）</th>			            
				            <th style="text-align:center;">收入额</th>			            
				            <th style="text-align:center;">减费用额</th>			            
				            <th style="text-align:center;">应纳税所得额</th>			            
				            <th style="text-align:center;">税率</th>			            
				            <th style="text-align:center;">速算扣除数</th>			            
				            <th style="text-align:center;">应缴纳税额</th>			            
				            <th style="text-align:center;">已扣缴税款</th>			            
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
	 	var columns = [
	 	      		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	 	      	       	return '<input type="checkbox" class="keyId" name="guid" value="' + full.GUID + '">';
	 	      	    },"width":10,'searchable': false},
	 	      		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	 	      	   		return data;
	 	      		},"width":41,"searchable": false,"class":"text-center"},
	 	      		{"data": "XM",defaultContent:""},
	 	      		{"data": "SFZH",defaultContent:""},
	 	      		{"data": "SDXM",defaultContent:""},
	 	      		{"data": "STIME",defaultContent:""},
	 	      		{"data": "ETIME",defaultContent:""},
	 	      		{"data": "YFHJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "JFYE",defaultContent:"","class":"text-right"},
	 	      		{"data": "YNJSE",defaultContent:"","class":"text-right"},
	 	      		{"data": "SL",defaultContent:"","class":"text-right"},
	 	      		{"data": "SSKC",defaultContent:"","class":"text-right"},
	 	      		{"data": "YNSE",defaultContent:"","class":"text-right"},
	 	      		{"data": "YNSE",defaultContent:"","class":"text-right"}
	 	      	];
	 	        table = getDataTableByListHj("mydatatables","${ctx}/xzcx/getGzcxPageList",[3,'asc'],columns,0,1,setGroup);
	 
			//导出Excel
			$("#btn_exp").click(function() {
				var json = searchJson("searchBox");
				var id = [];
		   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		   		if(checkbox.length > 0){
					checkbox.each(function(){
						id.push($(this).val());
					});
		   		}
		   		doExp(json,"${ctx}/xzcx/expGzcx","项目信息","${ctx}",id.join("','"));
			});
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