<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>月末结账</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.input_info{
		width:100px;
	}
	button{
/* 		background-color: #00acec !important; */
/* 		color: white !important; */
	}
	.div_bottom{
    	width: 99%;
    	position: absolute;
    	bottom: 20px;
   		background-color: #f3f3f3;
		
	}
	.bom{
		color:red;
	}
	.yc{
		display:none!important;
	}
	#btn_search_more>span {
/* 		background-color:#00acec !important; */
/* 		color: white !important; */
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<select style="width:70px;" class="form-control select2">
						<option value="0">300</option>
						<option value="1"></option>
						<option value="2"></option>
					</select>
	               <label>选择年度</label>
	               <select style="width:70px;" class="form-control select2">
						<option value="0">2017年</option>
						<option value="1">2016年</option>
						<option value="2">2015年</option>
					</select>
					
				</div>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>套账年度</label>
							<select  class="form-control select2">
						<option value="0">2017年</option>
						<option value="1">2016年</option>
						<option value="2">2015年</option>
					</select>
						</div>
						<div class="form-group">
							<label>是否结账</label>
							<select  class="form-control select2">
						<option value="0">是</option>
						<option value="1">否</option>
					</select>
						</div>
						<div class="form-group">
							<label>会计期间</label>
							<input type="text" id="txt_kjqj" class="form-control input-radius"  placeholder="请输出会计期间">
						</div>
						<div class="form-group">
							<label>结账人员</label>
							<input type="text" id="txt_jzry" class="form-control input-radius"  placeholder="请输入结账人员">
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_cxs">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancels">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" >
				    <button type="button" class="btn btn-default" id="btn_jz">结账</button>
				     <button type="button" class="btn btn-default" id="btn_selcolums">列选择</button>
					<button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
	               
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
				            <th style="text-align:center;" id="tr_tznd">套账年度</th>
				            <th style="text-align:center;" id="tr_kjqj">会计期间</th>
				            <th style="text-align:center;" id="tr_sfjz">是否结账</th>
				            <th style="text-align:center;" id="tr_jzrq">结账日期</th>
				            <th style="text-align:center;" id="tr_jzry">结账人员</th>
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
				
		</div>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="pzcxs" class="form-control input-radius window" name="pzcxs" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var columns = [
	               {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	                 return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
	               },"width":10,'searchable': false},
	               {"data": "PZRQ",defaultContent:"2017","class":"text-center"},
	               {"data": "PZBH",defaultContent:"0001","id":"tr_pzzh","class":"text-center"},
	               {"data": "FHYJ",defaultContent:"是","id":"tr_zy","class":"text-center"},
	               {"data": "FHYJ",defaultContent:"2017-10-20","id":"tr_kmbh","class":"text-center"},
	               {"data": "FHYJ",defaultContent:"李四","id":"tr_kmmc","class":"text-center"},
	              
	             ];
	           table = getDataTableByListHj("mydatatables","${ctx}/pzlr/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
	           test();
	 $("#btn_selcolums").click(function(){
		 select_commonWin("${ctx}/pzjz/col?controlId=pzcxs","列信息","500","700");
	 });
	 $("[id=btn_cxs]").click(function(){
		   window.location.reload();
	   });
	   $("[id=btn_cancels]").click(function(){
		   window.location.reload();
	   });
	//打印
	 $("[id$=print]").click(function(){
		 
		 select_commonWin("${ctx}/webView/kjhs/pzxx/print.jsp","双击页面打印","920","630");
  });
	 $("#btn_view").click(function(){
		 var checkbox = $("#mydatatables").find("tbody").find(":checked").filter(":checked");
		   if(checkbox.length==0){
			   alert("请选择一条信息！");
			   return;
		   }
		   select_commonWin("${ctx}/pzjz/view","查看信息","1000","700");
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
function selectCol(){
	var cols = $("#pzcxs").val();
	var trs = $("[id^=tr_]");
	if(cols==""){
		$("[id^=tr_]").removeClass("yc");
		return;
	}
	$.each(trs,function(i,v){
		var cs = $(this).attr("id");
		if(cols.indexOf(cs)<0){
			$(this).addClass("yc");
		}else{
			$(this).removeClass("yc");
		}
	});
}
// function test(){
// 	$.each($("[role='row']"),function(){
// 		$(this).find("td").eq(1).attr("id","tr_pzrq");
// 	 	$(this).find("td").eq(2).attr("id","tr_pzzh");
// 	 	$(this).find("td").eq(3).attr("id","tr_zy");
// 		$(this).find("td").eq(4).attr("id","tr_kmbh");
// 		$(this).find("td").eq(5).attr("id","tr_kmmc");
// 		$(this).find("td").eq(6).attr("id","tr_jfje");
// 		$(this).find("td").eq(7).attr("id","tr_dfje");
// 		$(this).find("td").eq(8).attr("id","tr_bm");
// 		$(this).find("td").eq(9).attr("id","tr_xm");
// 		$(this).find("td").eq(10).attr("id","tr_zyy");
// 		$(this).find("td").eq(11).attr("id","tr_wldw");
// 		$(this).find("td").eq(12).attr("id","tr_zdr");
// 		$(this).find("td").eq(13).attr("id","tr_fhr");
// 		$(this).find("td").eq(14).attr("id","tr_cnr");
// 		$(this).find("td").eq(15).attr("id","tr_jzr");
// 		$(this).find("td").eq(16).attr("id","tr_pzzt");
// 		$(this).find("td").eq(17).attr("id","tr_sfdy");
		
// 	});
	
// }
</script>
</body>
</html>