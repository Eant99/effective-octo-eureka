<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>选择项目</title>
<%@include file="/static/include/public-list-css.inc"%>
<%--表头样式--%>
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
/*     	border-bottom-width: 0px; */
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}
	.search-simple .select2-container--default .select2-selection--single {
   		background-color: #fff;
   		border: 1px solid #ccc;
    	border-radius: 4px 4px 4px 4px;
	}
	.div_bottom{
    	width: 99%;
    	bottom: 60%;
    	position: absolute;
	}
	.dualTd{
		text-align:center!important;
	}
	.dualTd button{
		background:#00acec;
		color: white;
	}
	#btn_save{
		background-color: #00acec;
    	color: white;
	}
	#btn_save i {
   	 	color: white;
	}
	.text-green{
		color:green!important; 
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="pull-right" style="margin-top:3px;">
			
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color: #00ACEC; border-radius:13px;">
		      			<span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
		  			</div>
				</div>
				<div class="sub-title pull-left text-primary">选择项目&nbsp;></div>
				
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
		      			<span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
		  			</div>
				</div>
				<div class="sub-title pull-left">公务接待明细&nbsp;></div>
				
				<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
		      			<span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
		  			</div>
				</div>
				<div class="sub-title pull-left" style="padding-right: 20px;">结算方式</div>
			
	        </div>
	        <div class="pull-right">
     	 	</div>
			<div class="search-simple">
				<div class="form-group">
					<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>第一步,选择项目
            		</div>
            		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label>项目编号</label>
					<input type="text" id="txt_bxr" class="form-control" name="xmbh" table="A" placeholder="项目编号">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
				<div class="pull-right">
	        		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
						<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
						
					</button>
<!-- 					<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button> -->
<!-- 		  			<button type="button" class="btn btn-default" id="btn_back">返回</button> -->
     	 		</div>
				<table id="mydatatables" class="table table-striped table-bordered dataTable ">
		        	<thead>
				        <tr>
				        	<th><input type="checkbox" class="select-all"/></th>
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>项目类型</th>
				            <th>项目负责人</th>
				            <th>预算金额（元）</th>
				            <th>剩余金额(元)</th>
				            <th>授权截止日期</th>
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
var target = "${ctx}/wsbx/gwjdfbx/gwjdfbxsq";
var suffix = "&mkbh={param.mkbh}";
$(function () {
  
	$("#btn_back").click(function(e){
		pageSkip();
	});
	//列表数据
    var columns = [
			{"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
    			return '<input type="checkbox"  money="'+full.YE+'" name="guid" shzt="'+full.SHZT+'" xmbh="'+full.XMBH+'" jflx="'+full.JFLX+'" xmmc="'+full.XMMC+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
  			},"width":10,'searchable': false},
       {"data": "XMBH",defaultContent:""},
       {"data": "XMMC",defaultContent:""},
       {"data": "XMLX",defaultContent:""},
       {"data": "XMFZR",defaultContent:""},
       {"data": "YSJE",defaultContent:"0.00","class":"text-right"},
       {"data": "YE",defaultContent:"0.00","class":"text-right"},
       {"data": "JZSJ",defaultContent:"","class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/wsbx/rcbx/getPageListByLoginXm",[2,'asc'],columns,0,1,setGroup);

});
$(function() {	
	//回显选择的项目
	var guid = "${param.xmguid}";
	$.each($("[name='guid']"),function(){
		var $this = $(this);
		if($this.attr("guid")==guid){
			$this.attr("checked","checked");
		}
	});
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
	$("table:last").after("<br/><div class='dualTd'>"+
		"<button class='btn btn-default' id='btn_next' >下一步</button>"+
	"</div>");
});
$(document).on("click","[id='btn_next']",function(){
	var checkbox = $(".keyId").filter(":checked");
	if(checkbox.length!=1){
		alert("只能选择一条项目信息进行操作！");
	}else{
		var guid = checkbox.val();
		pageSkip(target,"gwjdmx_add&xmguid="+guid,suffix);
	}
});

$("#btn_ywsm").click(function(){
	zysx();
});
function zysx(){
	//业务说明
	select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
}
</script>
</body>
</html>