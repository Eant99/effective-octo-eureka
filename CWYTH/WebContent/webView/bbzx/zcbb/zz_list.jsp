<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>总账</title>
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
	<div class="search" id="searchBox" style="padding-top: 0px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
				    <label>选择年度&ensp;</label>
					<select style="width:70px;" class="form-control select2">
						<option value="0">2017年</option>
						<option value="1">2016年</option>						
					</select>
	                &nbsp;
	                <label>日&emsp;期&ensp;</label>
	             	<input  name="" class="input_info date form-control" style="width:100px;" value="2016-1-1" data-format="yyyy-MM-dd" readonly/>
					 <label>至&ensp; </label><input  name="" class="input_info date form-control" style="width:100px;" value="2017-10-25" data-format="yyyy-MM-dd" readonly/>
					&nbsp;
					
					 <label>是否包含记账凭证&ensp; </label><select style="width:60px;" class="form-control select2">					
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
					&nbsp;				
					 <label>是否包含结转凭证&ensp; </label><select style="width:60px;" class="form-control select2">					
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
					
				</div>
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_exp">打印预览</button>
					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
				
				
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<center>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                 <span id="txt_bbmc" style="font-size: 16pt">总账</span>
               </td>
	        </tr>
	    </center>
	    
	    <div class="btn-group pull-right" role="group">
	    	 <td align="right" valign="bottom" style="width: 400px; height: 20px;">
                                打印日期：
                 <input name="txt_dyrq" type="text" id="txt_dyrq" style="width: 80px;" onfocus="javascript:WdatePicker();" value="2017-10-25">
                </td>
	    </div>
	    <div class="form-group">
	    	 <td align="left" valign="bottom" style="width: 429px; height: 20px;">
                                币种：人民币
                </td>
	    
	    </div>
	</div>
	
	
	

<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse">
		        	<thead>
				        <tr>
				            
				            <th style="text-align:center;"  rowspan="2">科目编号</th>
				            <th style="text-align:center;"  rowspan="2">科目名称</th>
				            <th style="text-align:center;"  rowspan="2">期间</th>
				            <th style="text-align:center;"  rowspan="2">摘要</th>
				            <th style="text-align:center;"  rowspan="2">借方</th>
				            <th style="text-align:center;"  rowspan="2">贷方</th>
				            <th style="text-align:center;"  colspan="2">余额</th>		
				        </tr>
				        <tr>
				            <th style="text-align:center;" id="tr_kmbh">方向</th>
				            <th style="text-align:center;" id="tr_kmbh">余额</th>
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
<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
 $(function () {
	var columns = [
	              
	               {"data": "PZRQ",orderable : false,defaultContent:"2017-10-16","class":"text-center",id:"tr_pzrq"},
	               {"data": "PZBH",orderable : false,defaultContent:"0001",id:"tr_pzzh"},
	               {"data": "FHYJ",orderable : false,defaultContent:"这是摘要",id:"tr_zy"},
	               {"data": "FHYJ",orderable : false,defaultContent:"10252",id:"tr_kmbh"},
	               {"data": "FHYJ",orderable : false,defaultContent:"会计科目",id:"tr_kmmc"},
	               {"data": "JFJEHJ",orderable : false,defaultContent:"1000.00","class":"text-right",id:"tr_jfje"},
	               {"data": "DFJEHJ",orderable : false,defaultContent:"500.00","class":"text-right",id:"tr_dfje"},
	               {"data": "FHYJ",orderable : false,defaultContent:"教务处",id:"tr_bm"},
	              
	             ];
	          // table = getDataTableByListHjbb("mydatatables","${ctx}/pzlr/getPageList?treedwbh=${dwbh}",null,columns,0,1,setGroup);
	           test();
	 $("#btn_selcolums").click(function(){
		 select_commonWin("${ctx}/pzjz/col?controlId=pzfh","列信息","500","700");
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
	var cols = $("#pzfh").val();
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