<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>在职薪资管理</title>
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
				<div class="form-group">
					<label>部门名称</label>
			        <input type="text" id="" class="form-control input-radius" name="bm" table="t" placeholder="请输入部门名称">
				</div>
				<div class="form-group">
					<label>工资月份</label>
			        <input type="text" id="" class="form-control input-radius nydate2" name="gzyf" table="t" placeholder="请输入工资月份" value="${gzyf }">
				</div>
<!-- 				<input type="hidden" name="shzt" table="t" value="2"/> -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				
				
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_exp">导出</button>
				</div>
			</div>
		</form>
	</div>

<form id="mysave" name="mysave" method="post"  >
<div class="container-fluid abc" style="overflow: auto;">
		<div class='responsive-table' >
			<div class='scrollable-area'  > 
			<table id="mydatatables" class="table table-striped table-bordered" >
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
				            <th style="text-align:center;">人员编号</th>
				            <th style="text-align:center;">姓名</th>
				            <th style="text-align:center;">部门</th>			            
				            <th style="text-align:center;">排序序号</th>			            
				            <th style="text-align:center;">岗位工资</th>			            
				            <th style="text-align:center;">薪级工资</th>			            
				            <th style="text-align:center;">新住房贴</th>			            
				            <th style="text-align:center;">物业补贴</th>			            
				            <th style="text-align:center;">独/回</th>			            
				            <th style="text-align:center;">基础绩效</th>			            
				            <th style="text-align:center;">奖励绩效1</th>			            
				            <th style="text-align:center;">博士补贴</th>			            
				            <th style="text-align:center;">岗位补贴</th>			            
				            <th style="text-align:center;">补工资</th>			            
				            <th style="text-align:center;">应发合计</th>	
				            <th style="text-align:center;">住房积金</th>			            
				            <th style="text-align:center;">医疗保险</th>			            
				            <th style="text-align:center;">代扣税</th>			            
				            <th style="text-align:center;">房租</th>			            
				            <th style="text-align:center;">失业金</th>			            
				            <th style="text-align:center;">社保金</th>			            
				            <th style="text-align:center;">养老金</th>			            
				            <th style="text-align:center;">扣款</th>			            
				            <th style="text-align:center;">扣款合计</th>			            
				            <th style="text-align:center;">实发合计</th>			            
				        </tr>
					</thead>
				    <tbody>
				   </tbody>
				</table>					
		</div>
			</div>
		</div>	
	</form>		
		</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
 $(function () {
	 $("#sel_shzt").change(function(){
		 window.location.href="${ctx}/xzcx/goGzcxPage?zt="+$(this).val();
	 });
	 	var columns = [
	 	      		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	 	      	       	return '<input type="checkbox" class="keyId" name="guid" shzt="'+full.SHZT+'" value="' + full.GUID + '">';
	 	      	    },"width":10,'searchable': false},
	 	      		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	 	      	   		return data;
	 	      		},"width":41,"searchable": false,"class":"text-center"},
	 	      		{"data": "RYBH",defaultContent:""},
	 	      		{"data": "XM",defaultContent:"" },
	 	      		{"data": "BM",defaultContent:"" },
	 	      		{"data": "XH",defaultContent:"" },
	 	      		{"data": "GWGZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "XJGZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "XZFT",defaultContent:"","class":"text-right"},
	 	      		{"data": "WYBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "DSZF",defaultContent:"","class":"text-right"},
	 	      		{"data": "JCJX",defaultContent:"","class":"text-right"},
	 	      		{"data": "JLJX1",defaultContent:"","class":"text-right"},
	 	      		{"data": "BSBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "GWBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "BGZ",defaultContent:"","class":"text-right"},
	 	        	{"data": "YFHJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "ZFJJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "YLBX",defaultContent:"","class":"text-right"},
	 	      		{"data": "DKS",defaultContent:"","class":"text-right"},
	 	      		{"data": "FZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "SYJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "SBJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "YLJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "KK",defaultContent:"","class":"text-right"},
	 	      		{"data": "KKHJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "SFHJ",defaultContent:"","class":"text-right"}
	 	        	
	 	      	];
	 	        table = getDataTableByListHj("mydatatables","${ctx}/xzcx/getPageList?shzt=2",[3,'asc'],columns,0,1,setGroup);
	 	       $("#btn_search").click();
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
			   		doExp(json,"${ctx}/xzcx/expGzffcx","工资发放","${ctx}",id.join(","));
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