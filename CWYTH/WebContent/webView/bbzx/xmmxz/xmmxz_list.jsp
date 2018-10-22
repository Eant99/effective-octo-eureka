<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位机构设置</title>
<style>
</style>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="" style="padding-top: 8px;padding-bottom: 2px">
    		<div class="search-simple">
				<div class="form-group">
					<label>项目负责人</label>
					<input type="text" id="txt_fzr" class="form-control input-radius" name="fzr" table="K" placeholder="请输入项目负责人">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div id="btn_search_more">
 					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group" style="width: 80%;">
						<label style="float: left;margin-top: 5px;">是否包含记账凭证</label>
							<div class="switch" style="float: right;">
								<div class="onoffswitch">
									<input type="checkbox" name="sfbhjzpz" id="sfbhjzpz" checked class="onoffswitch-checkbox" value="1" />
									<label class="onoffswitch-label" for="sfbhjzpz">
										<span class="onoffswitch-inner"></span>
										<span class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
<!--                             <div class="form-group"> -->
<!-- 							<label>是否包含记账凭证</label> -->
<!-- 							<select class="form-control " id="sfbhjzpz"  style="width:152px;" name="sfbhjzpz" table="K"> -->
<!-- 	             		 		<option value="">未选择</option> -->
<!-- 	                       		<option value="1">是</option> -->
<!-- 	                       		<option value="0">否</option> -->
<!-- 	                   		</select> -->
<!-- 						</div> -->
						<div class="form-group">
						<label>余额期间（元）</label>
							<input type="text" id="txt_syje" class="form-control input-radius number" table="K" style="width: 50px!important;margin-left: 45px;" name="qssyje" placeholder="">
							至
							<input type="text" id="txt_syje" class="form-control input-radius number" table="K" style="width: 50px!important;" name="jssyje" placeholder="">
						</div>
						<div class="form-group">
						<label>所属部门</label>
							<input type="text" id="txt_mc" class="form-control input-radius on-right" style="margin-left: 80px;" table="K" name="ssbm" placeholder="请输入所属部门">
						</div>
						<div class="search-more-bottom clearfix">
						<div class="pull-right">
							<button type="button" class="btn btn-primary" id="btn_search">
								<i class="fa icon-chaxun"></i>
								查 询
							</button>
							<button type="button" class="btn btn-default" id="btn_cancel">
								取 消
							</button>
						</div>
						</div>
					</div>
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
				        <th>项目编号</th>
				        <th>项目名称</th>
				        <th>项目负责人</th>
				        <th>所属部门</th>
				        <th>预算金额（元）</th>
				        <th>凭证类型</th>
				        <th>凭证号</th>
				        <th>凭证日期</th>
				        <th>凭证摘要</th>
				        <th>支出金额（元）</th>
				        <th>余额（元）</th>
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
<%@include file="/static/include/public-list-css.inc"%>
<script>
var target = "${ctx}/xmmxz";
var dm = "${param.dm}";
var columns = [
       		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
      				return '<input type="checkbox" class="keyId" name="keyId" value="' + data + '">';
       	    },"width":10,'searchable': false},
       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
       	   		return data;
       		},"width":41,"searchable": false,"class":"text-center"},
       		{"data": "XMBH",defaultContent:""},
       		{"data": "XMMC",defaultContent:""},
       	   	{"data": "FZR",defaultContent:""},
       	 	{"data": "SSBM",defaultContent:""},
       	 	{"data": "YSJE",defaultContent:"",width:"100","class":"text-right",'render':function(data){
       	 		return parseFloat(data).toFixed(2);
       	 	}},
       	    {"data": "PZLXMC",defaultContent:""},
       	 	{"data": "PZBH",defaultContent:"","class":"text-center"},
       	 	{"data": "PZRQ",defaultContent:"","class":"text-center"},
       	 	{"data": "PZZY",defaultContent:""},
       	 	{"data": "ZCJE",defaultContent:"",width:"100","class":"text-right",'render':function(data){
       	 		return parseFloat(data).toFixed(2);
       	 	}},
       	 	{"data": "YE",defaultContent:"",width:"100","class":"text-right",'render':function(data){
       	 		return parseFloat(data).toFixed(2);
       	 	}}
       	];
       	//表数据
table = getDataTableByListHj("mydatatables",target+"/getPageListData",[2,'asc'],columns);
   	
$("#sfbhjzpz").click(function(){
	var val = $(this).val();
	if(val == "1"){
		$(this).val("0");
	}else{
		$(this).val("1");
	}
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
</html>

