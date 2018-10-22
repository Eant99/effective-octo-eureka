<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>离职薪资</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>人员编号</label>
			        <input type="text" id="" class="form-control input-radius" name="rybh"  table="b" placeholder="请输入人员编号">
				</div>
				<div class="form-group">
					<label>人员姓名</label>
			        <input type="text" id="" class="form-control input-radius" name="xm"  table="b" placeholder="请输入人员姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				
					<div class="btn-group pull-right btn-group-marginTop" role="group"style="padding-right: 20px;">
<!-- 						<button id="btn_add" type="button" class="btn btn-default">增加</button> -->
<!-- 						<button id="btn_delxx" type="button" class="btn btn-default">批量删除</button> -->
						<button id="btn_back" type="button" class="btn btn-default">返回</button>
					</div>
		</div>
    </div>
        </form>
	<div class="container-fluid">
        <div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
					    <tr>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th style="text-align:center;">序号</th>
					        <th style="text-align:center;">人员编号</th>
				            <th style="text-align:center;">姓&nbsp;&nbsp;名</th>
				            <th style="text-align:center;">部&nbsp;&nbsp;门</th>			            
				            <th style="text-align:center;">人员类别</th>			            
				            <th style="text-align:center;">基本工资</th>			            
				            <th style="text-align:center;">增加离退费</th>			            
				            <th style="text-align:center;">原职务补贴</th>			            
				            <th style="text-align:center;">岗位补贴</th>			            
				            <th style="text-align:center;">新住房贴</th>			            
				            <th style="text-align:center;">回族补贴</th>			            
				            <th style="text-align:center;">退休提高部分</th>			            
				            <th style="text-align:center;">生活补贴</th>			            
				            <th style="text-align:center;">新增补贴</th>			            
				            <th style="text-align:center;">物价补贴</th>			            
				            <th style="text-align:center;">特需护理</th>			            
				            <th style="text-align:center;">教护补贴</th>			            
				            <th style="text-align:center;">其他补贴</th>			            
				            <th style="text-align:center;">离退补贴</th>			            
				            <th style="text-align:center;">月增补贴</th>			            
				            <th style="text-align:center;">基础绩效</th>			            
				            <th style="text-align:center;">交通费</th>			            
				            <th style="text-align:center;">补贴</th>			            		            
				            <th style="text-align:center;">租房补贴</th>			            		            
				            <th style="text-align:center;">补工资</th>			            		            
				            <th style="text-align:center;">物业补贴</th>			            		            
				            <th style="text-align:center;">监考费</th>			            		            
				            <th style="text-align:center;">过节费</th>			            		            
				            <th style="text-align:center;">电话费</th>			            		            
				            <th style="text-align:center;">应发合计</th>			            		            
				            <th style="text-align:center;">房租</th>			            		            
				            <th style="text-align:center;">社保金</th>			            		            
				            <th style="text-align:center;">暖气费</th>			            		            
				            <th style="text-align:center;">暖气费1</th>			            		            
				            <th style="text-align:center;">物业费</th>			            		            
				            <th style="text-align:center;">借款</th>			            		            
				            <th style="text-align:center;">养老金</th>			            		            
				            <th style="text-align:center;">补公积金</th>			            		            
				            <th style="text-align:center;">补税</th>			            		            
				            <th style="text-align:center;">四季度菜款</th>			            		            
				            <th style="text-align:center;">失业金</th>			            		            
				            <th style="text-align:center;">扣款合计</th>			            		            
				            <th style="text-align:center;">实发合计</th>			            		            
				            <th style="text-align:center;">工资月份</th>			            		            
				            <th style="text-align:center;">编号</th>			            		            
				            <th style="text-align:center;">计税项</th>			            		            
				            <th style="text-align:center;">住房积金</th>			            		            
				            <th style="text-align:center;">年终奖</th>			            		            
				            <th style="text-align:center;">扣款</th>		       
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
	//列表数据
   	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="mkbh" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
		
	   	{"data": "RYBH",defaultContent:""},
		{"data": "XM",defaultContent:""},    
	   	{"data": "BM",defaultContent:""},
	   	{"data": "RYLB",defaultContent:""},
	   	{"data": "JBGZ",defaultContent:"","class":"text-right"},
	   	{"data": "ZJLTF",defaultContent:"","class":"text-right"},
	   	{"data": "YZWBT",defaultContent:"","class":"text-right"},
	   	{"data": "GWBT",defaultContent:"","class":"text-right"},
	   	{"data": "XZFT",defaultContent:"","class":"text-right"},
	   	{"data": "HZBT",defaultContent:"","class":"text-right"},
	   	{"data": "TXTGBF",defaultContent:"","class":"text-right"},
	   	{"data": "SHBT",defaultContent:"","class":"text-right"},
	   	{"data": "XZBT",defaultContent:"","class":"text-right"},
	   	{"data": "WJBT",defaultContent:"","class":"text-right"},
	   	{"data": "TXHL",defaultContent:"","class":"text-right"},
	   	{"data": "JHBT",defaultContent:"","class":"text-right"},
	   	{"data": "QTBT",defaultContent:"","class":"text-right"},
	   	{"data": "LTBT",defaultContent:"","class":"text-right"},
	   	{"data": "YZBT",defaultContent:"","class":"text-right"},
	   	{"data": "JCJX",defaultContent:"","class":"text-right"},
	   	{"data": "JTF",defaultContent:"","class":"text-right"},
	   	{"data": "BT",defaultContent:"","class":"text-right"},
	   	{"data": "ZFBT",defaultContent:"","class":"text-right"},
	   	{"data": "BGZ",defaultContent:"","class":"text-right"},
	   	{"data": "WYBT",defaultContent:"","class":"text-right"},
	   	{"data": "JKF",defaultContent:"","class":"text-right"},
	   	{"data": "GJF",defaultContent:"","class":"text-right"},
	   	{"data": "DHF",defaultContent:"","class":"text-right"},
	   	{"data": "YFHJ",defaultContent:"","class":"text-right"},
	   	{"data": "FZ",defaultContent:"","class":"text-right"},
	   	{"data": "SBJ",defaultContent:"","class":"text-right"},
	   	{"data": "NQF",defaultContent:"","class":"text-right"},
	   	{"data": "NQF1",defaultContent:"","class":"text-right"},
	   	{"data": "WYF",defaultContent:"","class":"text-right"},
	   	{"data": "JK",defaultContent:"","class":"text-right"},
	   	{"data": "YLJ",defaultContent:"","class":"text-right"},
	   	{"data": "BGJJ",defaultContent:"","class":"text-right"},
	   	{"data": "BS",defaultContent:"","class":"text-right"},
	   	{"data": "SJDCK",defaultContent:"","class":"text-right"},
	   	{"data": "SYJ",defaultContent:"","class":"text-right"},
	   	{"data": "KKHJ",defaultContent:"","class":"text-right"},
	   	{"data": "SFHJ",defaultContent:"","class":"text-right"},
	   	{"data": "GZYF",defaultContent:"","class":"text-right"},
	   	{"data": "BH",defaultContent:"","class":"text-right"},
	   	{"data": "JSX",defaultContent:"","class":"text-right"},
	   	{"data": "ZFJJ",defaultContent:"","class":"text-right"},
	   	{"data": "NZJ",defaultContent:"","class":"text-right"},
	   	{"data": "KK",defaultContent:"","class":"text-right"}
	   	
// 	    {"data":"GUID",'render':function(data, type, full, meta){
//           	 return '<a style="font-size:12px" href="javascript:void(0);" class="btn btn_upd" mkbh="'+full.MKBH+'">修改</a>|<a style="font-size:12px" href="javascript:void(0);" class="btn btn_del" mkbh="'+full.MKBH+'">删除</a>';
// //           	 return '<a style="font-size:12px" class="btn btn_show"  mkbh ="'+full.MKBH+'">查看详情</a>|<a style="font-size:12px" href="javascript:void(0);" class="btn btn_upd" mkbh="'+full.MKBH+'">修改</a>|<a style="font-size:12px" href="javascript:void(0);" class="btn btn_del" mkbh="'+full.MKBH+'">删除</a>';
//            },orderable:false,"width":100,"class":"text-center"}
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/xzgl/getLzxzPageList?bh=${mkbh}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
    
   	$("#btn_back").click(function(){
   		window.location.href="${ctx}/xzgl/lzxz_list";
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