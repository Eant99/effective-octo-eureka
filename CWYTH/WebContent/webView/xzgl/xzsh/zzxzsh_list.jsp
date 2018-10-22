<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>在职薪资</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.bottom {
    width: 99%;
    position: absolute;
    bottom: 27px;
    background-color: #f3f3f3;
}
</style>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>人员编号或姓名</label>
			        <input type="text" id="text_rybh" class="form-control input-radius"   table="t" placeholder="请输入人员编号或姓名">
					<button type="button" id="btn_rybh" class="btn btn-link ">选择</button>
				</div>
				<div class="form-group">
					<label>部门编号或名称</label>
			        <input type="text" id="text_bm" class="form-control input-radius"   table="t" placeholder="请输入部门编号和名称">
					<button type="button" id="btn_bm" class="btn btn-link ">选择</button>
				</div>
				<div class="form-group">
					<label>工资月份</label>
			        <input type="text" id="text_gzyf" class="form-control input-radius nydate2"  table="t" placeholder="请输入工资月份" value="${gzyf }">
				</div>
				<div class="form-group">
					<label>审核状态</label>
					<select class="form-control input-radius" id="sel_shzt"  table="t">
					<option value=""selected>全部</option>
					<option value="1">待审核</option>
					<option value="2">审核通过</option>
					<option value="3">已退回</option>
					</select>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search1"><i class="fa icon-chaxun"></i>查 询</button>
				
					<div class="btn-group pull-right btn-group-marginTop" role="group"style="padding-right: 20px;">
						<button id="btn_tg" type="button" class="btn btn-default">通过</button>
						<button id="btn_th" type="button" class="btn btn-default">退回</button>
<!-- 						<button id="btn_back" type="button" class="btn btn-default">返回</button> -->
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
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>审核状态</th>
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
	$("#sel_shzt").change(function(){
		if($(this).val()=='1'){
			$("#btn_tg").show();
			$("#btn_th").show();
		}else{
			$("#btn_tg").hide();
			$("#btn_th").hide();
		}
	});
	$("#sel_shzt").change();
	//列表数据
   	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="guid" shzt="'+full.SHZT+'" value="' + data + '"> ';
// 	       	return '<input type="checkbox" class="keyId" name="gid" shzt="'+full.SHZT+'" value="' + data + '">  <input type="hidden" class="keyId" shzt="'+full.SHZT+'" name="guid" value="' + full.GUID + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
		
	   	{"data": "SHZT",defaultContent:""},
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
   	table = getDataTableByListHj("mydatatables","${ctx}/xzsh/getXzPageList",[3,'asc'],columns,0,1,setGroup);
//    	$("#btn_search").click();
   	//返回
//    	$("#btn_back").click(function(){
//    		window.location.href="${ctx}/xzgl/xzgl_list";
//    	});
   	
   	//查询人员编号
// 	$(document).on("click","[id=text_rybh]",function(){			
// 		select_commonWin("${ctx}/xzgl/rypage?controlId=text_rybh&model=xzcx","人员信息","920","630");
// 	});
	//查询人员姓名
// 	$(document).on("click","[id=text_ryxm]",function(){			
// 		select_commonWin("${ctx}/window/rypagexm?controlId=text_ryxm&model=xzcxxm","人员信息","920","630");
// 	});
	//查询人员编号或者姓名
	$(document).on("click","#btn_rybh",function(){			
		select_commonWin("${ctx}/xzgl/rypage?controlId=text_rybh&model=xzcx","人员信息","920","630");
	});
	//查询单位编号或者名称
	$(document).on("click","[id=btn_bm]",function(){			
		select_commonWin("${ctx}/window/dwpage?controlId=text_bm","部门信息","920","630");
	});
	//查询人员编号或者姓名
	$("#text_rybh").bindChange("${ctx}/suggest/getXx","R");
	//查询单位编号或者名称
	$("#text_bm").bindChange("${ctx}/suggest/getXx","D");
    //通过
   	$("#btn_tg").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var gzyf =$("#text_gzyf").val();
   		var flag = true;
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		 if($(this).attr("shzt") != "待审核"){
   	   			 flag=false;
   	   		 }
   	   		});
   	   		if( flag){
	   	   	confirm("确定通过？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/xzsh/doZztg",
	   	   			data:"guid="+guid.join(",")+"&gzyf="+gzyf,
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("操作成功！");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   	   		}else{ 
   	   		alert("不可重复审核！");
   	   		}
   		}else{
//    			var ck = $("#mydatatables").find("[name='guid']");
//    			var guid = [];
//    	   		ck.each(function(){
//    	   			guid.push($(this).val());
//    	   		 if($(this).attr("shzt") != "待审核"){
//    	   			 flag=false;
//    	   		 }
//    	   		})
//    			if( flag){
   		  	confirm("确定全部通过？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/xzsh/doZztg",
	   	   			type:"post",
	   	   			async:"false",
	   	   	    	data:"searchJson=" + searchJson("searchBox")+"&gzyf="+gzyf,
	   	   			success:function(val){
	   	   				alert("操作成功！");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
//    	   		}else{
//    	   		alert("不可重复审核！");
//    	   		}
   		}
   	});
    //退回
   	$("#btn_th").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var flag = true;
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		  if($(this).attr("shzt") != "待审核"){
   	   			 flag=false;
   	   		   }
   	   		});
   	   	if( flag){
	   	   	confirm("确定退回？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/xzsh/doZzth",
	   	   			data:"guid="+guid.join(","),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("操作成功！");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   	    	}else{
   	 	    	alert("不可重复审核！");
   	    	}
   		}else{
//    			var ck = $("#mydatatables").find("[name='guid']");
//    			var guid = [];
//    	   		ck.each(function(){
//    	   			guid.push($(this).val());
//    	   		 if($(this).attr("shzt") != "待审核"){
//    	   			 flag=false;
//    	   		 }
//    	   		})
//    			if(flag){
   		  	confirm("确定全部退回？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/xzsh/doZzth",
	   	   			type:"post",
	   	   			data:"searchJson=" + searchJson("searchBox"),
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("操作成功！");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
//    			}else{
//    				alert("不可重复审核！");
//    			}
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
$(document).on("click","#btn_search1",function(){
	var rybh = $("#text_rybh").val();
	var bmmc = $("#text_bm").val();
	var ryxm =$("#text_ryxm").val();
	var shzt = $("#sel_shzt").val();
	var gzyf = $("#text_gzyf").val();
	table.ajax.url("${ctx}/xzsh/getXzPageList?rybh="+rybh+"&bmmc="+bmmc+"&shzt="+shzt+"&gzyf="+gzyf+"&ryxm="+ryxm);
	table.ajax.reload();
});
</script>
</body>
</html>