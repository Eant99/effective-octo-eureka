<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>出纳签字</title>
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
    	bottom: 50px;
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
						<option value="0">待操作</option>
						<option value="1">已操作</option>
						<option value="2">全部</option>
					</select>
	                <label>&emsp;日期&emsp;</label>
	             	<input  name="" class="input_info date form-control" style="width:100px;" value="${firstDay}" data-format="yyyy-MM-dd" readonly/>
					<label>&emsp;至&emsp;</label><input  name="" class="input_info date form-control" style="width:100px;" value="${lastDay}" data-format="yyyy-MM-dd" readonly/>
					<label>&emsp;打印&emsp;</label>
					<select style="width:60px;" class="form-control select2">
						<option value="0">全部</option>
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
				</div>
					<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>凭证日期</label>
							<div class="input-group">
								<input type="text" id="txt_dwld" class="form-control input-radius"  placeholder="请输入凭证日期">
							</div>
						</div>
						<div class="form-group">
							<label>凭证字号</label>
							<input type="text" id="txt_mc" class="form-control input-radius"  placeholder="请输入凭证字号">
						</div>
						<div class="form-group">
							<label>记&ensp;账&ensp;人</label>
							<input type="text" id="txt_dz" class="form-control input-radius"  placeholder="请输入记账人">
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
					<button type="button" class="btn btn-default" id="btn_view">查看</button>
	                <button type="button" class="btn btn-default" id="btn_check">出纳签字</button>
					<button type="button" class="btn btn-default" id="btn_someprint">批量打印</button>
	                <button type="button" class="btn btn-default" id="btn_selcolums">列选择</button>
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
				            <th style="text-align:center;" id="tr_pzrq">凭证日期</th>
				            <th style="text-align:center;" id="tr_pzzh">凭证字号</th>
				            <th style="text-align:center;" id="tr_zy">摘要</th>
				            <th style="text-align:center;" id="tr_kmbh">科目编号</th>
				            <th style="text-align:center;" id="tr_kmmc">科目名称</th>
				            <th style="text-align:center;" id="tr_jfje">借方金额（元）</th>
				            <th style="text-align:center;" id="tr_dfje">贷方金额（元）</th>
				            <th style="text-align:center;" id="tr_bm">部门</th>
				            <th style="text-align:center;" id="tr_xm">项目</th>
				            <th style="text-align:center;" id="tr_zyy">职员</th>
				            <th style="text-align:center;" id="tr_wldw">往来单位</th>
				            <th style="text-align:center;" id="tr_zdr">制单人</th>
				            <th style="text-align:center;" id="tr_fhr">复核人</th>
				            <th style="text-align:center;" id="tr_cnr">出纳人</th>
				            <th style="text-align:center;" id="tr_jzr">记账人</th>
				            <th style="text-align:center;" id="tr_pzzt">凭证状态</th>
				            <th style="text-align:center;" id="tr_sfdy">是否打印</th>
				        </tr>
					</thead>
				    <tbody>
<!-- 				    	<td><input type="checkbox" class="select-all"/></td> -->
<!-- 				            <td style="text-align:center;" id="tr_pzrq">2017-10-16</td> -->
<!-- 				            <td style=""  id="tr_pzzh">0001</td> -->
<!-- 				            <td style="" id="tr_zy">这是摘要</td> -->
<!-- 				            <td style="" id="tr_kmbh">10252</td> -->
<!-- 				            <td style="" id="tr_kmmc">会计科目</td> -->
<!-- 				            <td style="text-align:right;" id="tr_jfje">1000.00</td> -->
<!-- 				            <td style="text-align:right;" id="tr_dfje">500.00</td> -->
<!-- 				            <td style="" id="tr_bm">教务处</td> -->
<!-- 				            <td style="" id="tr_xm">建设项目</td> -->
<!-- 				            <td style="" id="tr_zyy">李龙</td> -->
<!-- 				            <td style="" id="tr_wldw">山东国子软件</td> -->
<!-- 				            <td style="" id="tr_zdr">超级管理员</td> -->
<!-- 				            <td style="" id="tr_fhr">超级管理员</td> -->
<!-- 				            <td style="" id="tr_cnr">超级管理员</td> -->
<!-- 				            <td style="" id="tr_jzr">超级管理员</td> -->
<!-- 				            <td style="" id="tr_pzzt">否</td> -->
<!-- 				            <td style="" id="tr_sfdy">是</td> -->
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<div class="div_bottom">
	<span class="bom">凭证张数：0</span><br>
	<span class="bom">附件总数：0</span>
</div>
<input type="hidden" id="cnqz" class="form-control input-radius window" name="cnqz" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	  var columns = [
           {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
             return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
           },"width":10,'searchable': false},
           {"data": "PZRQ",defaultContent:"2017-10-16","class":"text-center"},
           {"data": "PZBH",defaultContent:"0001","id":"tr_pzzh"},
           {"data": "FHYJ",defaultContent:"这是摘要","id":"tr_zy"},
           {"data": "FHYJ",defaultContent:"10252","id":"tr_kmbh"},
           {"data": "FHYJ",defaultContent:"会计科目","id":"tr_kmmc"},
           {"data": "JFJEHJ",defaultContent:"1000.00","class":"text-right"},
           {"data": "DFJEHJ",defaultContent:"500.00","class":"text-right"},
           {"data": "FHYJ",defaultContent:"教务处","id":"tr_bm"},
           {"data": "FHYJ",defaultContent:"建设项目","id":"tr_xm"},
           {"data": "CZR",defaultContent:"李龙","id":"tr_zyy"},
           {"data": "FHYJ",defaultContent:"山东国子软件","id":"tr_wldw"},
           {"data": "ZDR",defaultContent:"超级管理员","id":"tr_zdr"},
           {"data": "FHR",defaultContent:"超级管理员","id":"tr_fhr"},
           {"data": "CNR",defaultContent:"超级管理员","id":"tr_cnr"},
           {"data": "JZR",defaultContent:"超级管理员","id":"tr_jzr"},
           {"data": "PZZT",defaultContent:"否","id":"tr_pzzt","class":"text-center"},
           {"data": "FHYJ",defaultContent:"是","id":"tr_sfdy","class":"text-center"}
         ];
       table = getDataTableByListHj("mydatatables","${ctx}/pzlr/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
       test();
	 $("#btn_selcolums").click(function(){
		 select_commonWin("${ctx}/pzjz/col?controlId=cnqz","列信息","500","700");
	 });
	 
   $("#btn_check").click(function(){
	   alert("操作成功");
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
	var cols = $("#cnqz").val();
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
function test(){
	$.each($("[role='row']"),function(){
		$(this).find("td").eq(1).attr("id","tr_pzrq");
	 	$(this).find("td").eq(2).attr("id","tr_pzzh");
	 	$(this).find("td").eq(3).attr("id","tr_zy");
		$(this).find("td").eq(4).attr("id","tr_kmbh");
		$(this).find("td").eq(5).attr("id","tr_kmmc");
		$(this).find("td").eq(6).attr("id","tr_jfje");
		$(this).find("td").eq(7).attr("id","tr_dfje");
		$(this).find("td").eq(8).attr("id","tr_bm");
		$(this).find("td").eq(9).attr("id","tr_xm");
		$(this).find("td").eq(10).attr("id","tr_zyy");
		$(this).find("td").eq(11).attr("id","tr_wldw");
		$(this).find("td").eq(12).attr("id","tr_zdr");
		$(this).find("td").eq(13).attr("id","tr_fhr");
		$(this).find("td").eq(14).attr("id","tr_cnr");
		$(this).find("td").eq(15).attr("id","tr_jzr");
		$(this).find("td").eq(16).attr("id","tr_pzzt");
		$(this).find("td").eq(17).attr("id","tr_sfdy");
		
	});
	
}
</script>
</body>
</html>