<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>在职薪资</title>
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
					        <th>序号</th>
					        <th>人员编号</th>
				            <th>姓&nbsp;&nbsp;名</th>
				            <th>部&nbsp;&nbsp;门</th>			            
				            <th>人员类别</th>			            
				            <th>人员类型</th>			            
				            <th>岗位工资</th>			            
				            <th>薪级工资</th>			            
				            <th>新住房贴</th>			            
				            <th>物业补贴</th>			            
				            <th>独生子费</th>			            
				            <th>基础绩效</th>			            
				            <th>奖励绩效1</th>			            
				            <th>博士补贴</th>			            
				            <th>岗位补贴</th>			            
				            <th>北校区值班补贴</th>			            
				            <th>生育补贴</th>			            
				            <th>教护补贴</th>			            
				            <th>驻济补贴</th>			            
				            <th>合同补贴</th>			            
				            <th>其他补贴</th>			            
				            <th>补工资</th>			            
				            <th>监考费</th>			            
				            <th>辅助工作量</th>			            
				            <th>招生奖励</th>			            
				            <th>辅导员夜间值班补贴</th>			            
				            <th>考核奖</th>			            
				            <th>电话费</th>			            
				            <th>补贴</th>			            
				            <th>租房补贴</th>			            
				            <th>应发合计</th>			            
				            <th>讲课费</th>			            
				            <th>补工资扣税</th>			            
				            <th>计税项</th>			            
				            <th>全年计税项</th>			            
				            <th>全年计税项1</th>			            
				            <th>全年计税项2</th>			            
				            <th>全年计税项3</th>			            
				            <th>补基础性绩效工资2014计税基数</th>			            
				            <th>北校补贴2014年1到10月计税基数</th>			            
				            <th>计税基数</th>			            
				            <th>住房积金</th>			            
				            <th>聘公积金</th>			            
				            <th>医疗保险</th>			            
				            <th>补公积金</th>			            
				            <th>代扣税</th>			            
				            <th>本年税额</th>			            
				            <th>上年税额</th>			            
				            <th>补税</th>			            
				            <th>房租</th>			            
				            <th>失业金</th>			            
				            <th>暖气费</th>			            
				            <th>暖气费1</th>			            
				            <th>物业费</th>			            
				            <th>社保金</th>			            
				            <th>四季度菜款</th>			            
				            <th>扣款</th>			            
				            <th>养老金</th>			            
				            <th>借款</th>			            
				            <th>爱心一日捐</th>			            
				            <th>扣款合计</th>			            
				            <th>实发合计</th>			            
				            <th>工资月份</th>			            
				            <th>编号</th>			            
				            <th>序号</th>			            
				            <th>交通费</th>			            
				            <th>年终奖</th>			            
				            <th>年终奖代扣税</th>			            
				            <th>工资代扣税</th>			            
				            <th>扣税合计</th>			            
				            <th>工号</th>			            
				            <th>是否在编</th>			            
				            <th>补扣医疗保险</th>			            
				            <th>补扣失业金</th>			            
				            <th>补扣养老金</th>			            
				            <th>补扣社保金</th>			            
				            <th>是否党员</th>			            
				            <th>入党情况</th>		       
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
	   	{"data": "RYLX",defaultContent:""},
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
	   	{"data": "SFHJ",defaultContent:"","class":"text-right"},
		{"data": "GZYF",defaultContent:"","class":"text-right"},    
	   	{"data": "BH",defaultContent:"","class":"text-right"},
	   	{"data": "XH",defaultContent:"","class":"text-right"},
	   	{"data": "JTF",defaultContent:"","class":"text-right"},
	   	{"data": "NZJ",defaultContent:"","class":"text-right"},	
	   	{"data": "NZJDKS",defaultContent:"","class":"text-right"},
	   	{"data": "GZDKS",defaultContent:"","class":"text-right"},
	   	{"data": "KSHJ",defaultContent:"","class":"text-right"},
	   	{"data": "GH",defaultContent:"","class":"text-right"},
	   	{"data": "SFZB",defaultContent:""},
	   	{"data": "BKYLBX",defaultContent:"","class":"text-right"},
		{"data": "BKSYJ",defaultContent:"","class":"text-right"},    
	   	{"data": "BKYLJ",defaultContent:"","class":"text-right"},
	   	{"data": "BKSBJ",defaultContent:"","class":"text-right"},
	   	{"data": "SFDY",defaultContent:""},
	   	{"data": "RDQK",defaultContent:""}
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/xzgl/getXzPageList?bh=${mkbh}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);

   	$("#btn_back").click(function(){
   		window.location.href="${ctx}/xzgl/xzgl_list";
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