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
					        <th style="text-align:center;">工资月份</th>	
				            <th style="text-align:center;">岗位工资</th>			            
				            <th style="text-align:center;">薪级工资</th>			            
				            <th style="text-align:center;">新住房贴</th>			            
				            <th style="text-align:center;">物业补贴</th>			            
				            <th style="text-align:center;">独生子费</th>			            
				            <th style="text-align:center;">基础绩效</th>			            
				            <th style="text-align:center;">奖励绩效1</th>			            
				            <th style="text-align:center;">博士补贴</th>			            
				            <th style="text-align:center;">岗位补贴</th>			            
				            <th style="text-align:center;">北校区值班补贴</th>			            
				            <th style="text-align:center;">生育补贴</th>			            
				            <th style="text-align:center;">教护补贴</th>			            
				            <th style="text-align:center;">驻济补贴</th>			            
				            <th style="text-align:center;">合同补贴</th>			            
				            <th style="text-align:center;">其他补贴</th>			            
				            <th style="text-align:center;">补工资</th>			            
				            <th style="text-align:center;">监考费</th>			            
				            <th style="text-align:center;">辅助工作量</th>			            
				            <th style="text-align:center;">招生奖励</th>			            
				            <th style="text-align:center;">辅导员夜间值班补贴</th>			            
				            <th style="text-align:center;">考核奖</th>			            
				            <th style="text-align:center;">电话费</th>			            
				            <th style="text-align:center;">补贴</th>			            
				            <th style="text-align:center;">租房补贴</th>			            
				            <th style="text-align:center;">应发合计</th>			            
				            <th style="text-align:center;">讲课费</th>			            
				            <th style="text-align:center;">补工资扣税</th>			            
				            <th style="text-align:center;">计税项</th>			            
				            <th style="text-align:center;">全年计税项</th>			            
				            <th style="text-align:center;">全年计税项1</th>			            
				            <th style="text-align:center;">全年计税项2</th>			            
				            <th style="text-align:center;">全年计税项3</th>			            
				            <th style="text-align:center;">补基础性绩效工资2014计税基数</th>			            
				            <th style="text-align:center;">北校补贴2014年1到10月计税基数</th>			            
				            <th style="text-align:center;">计税基数</th>			            
				            <th style="text-align:center;">住房积金</th>			            
				            <th style="text-align:center;">聘公积金</th>			            
				            <th style="text-align:center;">医疗保险</th>			            
				            <th style="text-align:center;">补公积金</th>			            
				            <th style="text-align:center;">代扣税</th>			            
				            <th style="text-align:center;">本年税额</th>			            
				            <th style="text-align:center;">上年税额</th>			            
				            <th style="text-align:center;">补税</th>			            
				            <th style="text-align:center;">房租</th>			            
				            <th style="text-align:center;">失业金</th>			            
				            <th style="text-align:center;">暖气费</th>			            
				            <th style="text-align:center;">暖气费1</th>			            
				            <th style="text-align:center;">物业费</th>			            
				            <th style="text-align:center;">社保金</th>			            
				            <th style="text-align:center;">四季度菜款</th>			            
				            <th style="text-align:center;">扣款</th>			            
				            <th style="text-align:center;">养老金</th>			            
				            <th style="text-align:center;">借款</th>			            
				            <th style="text-align:center;">爱心一日捐</th>			            
				            <th style="text-align:center;">扣款合计</th>			            
				            <th style="text-align:center;">交通费</th>			            
				            <th style="text-align:center;">年终奖</th>			            
				            <th style="text-align:center;">年终奖代扣税</th>			            
				            <th style="text-align:center;">工资代扣税</th>			            
				            <th style="text-align:center;">扣税合计</th>			            
				            <th style="text-align:center;">补扣医疗保险</th>			            
				            <th style="text-align:center;">补扣失业金</th>			            
				            <th style="text-align:center;">补扣养老金</th>			            
				            <th style="text-align:center;">补扣社保金</th>
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
	 	var columns = [
	 	      		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	 	      	       	return '<input type="checkbox" class="keyId" name="guid" shzt="'+full.SHZT+'" value="' + full.GUID + '">';
	 	      	    },"width":10,'searchable': false},
	 	      		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	 	      	   		return data;
	 	      		},"width":41,"searchable": false,"class":"text-center"},
	 	      		{"data": "GZYF",defaultContent:"","class":"text-right"},
	 	      		{"data": "GWGZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "XJGZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "XZFT",defaultContent:"","class":"text-right"},
	 	      		{"data": "WYBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "DSZF",defaultContent:"","class":"text-right"},
	 	      		{"data": "JCJX",defaultContent:"","class":"text-right"},
	 	      		{"data": "JLJX1",defaultContent:"","class":"text-right"},
	 	      		{"data": "BSBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "GWBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "BXQZBBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "SYBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "JHBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "ZJBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "HTBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "QTBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "BGZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "JKF",defaultContent:"","class":"text-right"},
	 	      		{"data": "FZGZL",defaultContent:"","class":"text-right"},
	 	      		{"data": "ZSJL",defaultContent:"","class":"text-right"},
	 	      		{"data": "FDYYJZBBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "KHJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "DHF",defaultContent:"","class":"text-right"},
	 	      		{"data": "BT",defaultContent:"","class":"text-right"},
	 	      		{"data": "ZFBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "YFHJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "JIANGKEF",defaultContent:"","class":"text-right"},
	 	      		{"data": "BGZKS",defaultContent:"","class":"text-right"},
	 	      		{"data": "JSX",defaultContent:"","class":"text-right"},
	 	      		{"data": "QNJSX",defaultContent:"","class":"text-right"},
	 	      		{"data": "QNJSX1",defaultContent:"","class":"text-right"},
	 	      		{"data": "QNJSX2",defaultContent:"","class":"text-right"},
	 	      		{"data": "QNJSX3",defaultContent:"","class":"text-right"},
	 	      		{"data": "BJCXJXGZ2014JSJS",defaultContent:"","class":"text-right"},
	 	      		{"data": "BXBT2014N1D10YJSJS",defaultContent:"","class":"text-right"},
	 	      		{"data": "JSJS",defaultContent:"","class":"text-right"},
	 	      		{"data": "ZFJJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "PGJJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "YLBX",defaultContent:"","class":"text-right"},
	 	      		{"data": "BGJJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "DKS",defaultContent:"","class":"text-right"},
	 	      		{"data": "BNSE",defaultContent:"","class":"text-right"},
	 	      		{"data": "SNSE",defaultContent:"","class":"text-right"},
	 	      		{"data": "BS",defaultContent:"","class":"text-right"},
	 	      		{"data": "FZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "SYJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "NQF",defaultContent:"","class":"text-right"},
	 	      		{"data": "NQF1",defaultContent:"","class":"text-right"},
	 	      		{"data": "WYF",defaultContent:"","class":"text-right"},
	 	      		{"data": "SBJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "SJDCK",defaultContent:"","class":"text-right"},
	 	      		{"data": "KK",defaultContent:"","class":"text-right"},
	 	      		{"data": "YLJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "JK",defaultContent:"","class":"text-right"},
	 	      		{"data": "AXYRJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "KKHJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "JTF",defaultContent:"","class":"text-right"},
	 	      		{"data": "NZJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "NZJDKS",defaultContent:"","class":"text-right"},
	 	      		{"data": "GZDKS",defaultContent:"","class":"text-right"},
	 	      		{"data": "KSHJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "BKYLBX",defaultContent:"","class":"text-right"},
	 	      		{"data": "BKSYJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "BKYLJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "BKSBJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "SFHJ",defaultContent:"","class":"text-right"},
	 	   
	 	      	];
	 	      	table = getDataTableByListHj("mydatatables","${ctx}/xzcx/getPageList?flag=person",[3,'asc'],columns,0,1,setGroup);
 		$("#btn_search").click();
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