<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>离职薪资</title>
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
			        <input type="text" id="text_gzyf" class="form-control input-radius nydate2"  table="l" placeholder="请输入工资月份" value="${gzyf }">
				</div>
				<div class="form-group">
					<label>审核状态</label>
					<select class="form-control input-radius" id="sel_shzt"  table="l">
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
					        <th style="text-align:center;">审核状态</th>
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
		{"data":"XH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="guid" shzt="'+full.SHZT+'" value="' + data + '">';
// 	       	return '<input type="checkbox" class="keyId" name="gid" shzt="'+full.SHZT+'" value="' + data + '"><input type="hidden" class="keyId" name="guid" value="' + full.GUID + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
		
	   	{"data": "SHZT",defaultContent:""},
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
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/xzsh/getLzPageList",[0,'asc'],columns,0,1,setGroup);
   	$("#btn_search").click();
   	$("#btn_back").click(function(){
   		window.location.href="${ctx}/xzgl/lzxz_list";
   	});
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
	   	   			url:"${ctx}/xzsh/doTxtg",
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
	   	   			url:"${ctx}/xzsh/doTxtg",
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
	   	   			url:"${ctx}/xzsh/doTxth",
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
	   	   			url:"${ctx}/xzsh/doTxth",
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
	table.ajax.url("${ctx}/xzsh/getLzPageList?rybh="+rybh+"&bmbh="+bmmc+"&shzt="+shzt+"&gzyf="+gzyf+"&ryxm="+ryxm);
	table.ajax.reload();
});
</script>
</body>
</html>