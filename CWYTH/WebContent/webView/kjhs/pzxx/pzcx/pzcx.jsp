<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证冲销</title>
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
					
	                <label>&emsp;日期&emsp;</label>
	             	<input id="kssj" types="CX1" name="pzrq" table="K" class="input_info date form-control" style="width:130px;" placeholder="请选择开始时间" value="${kssj }" data-format="yyyy-MM-dd" readonly/>
					<label>&emsp;至&emsp;</label>
					<input id="jssj" types="CX2" name="pzrq" table="K" class="input_info date form-control" placeholder="请选择结束时间" style="width:130px;" value="${jssj }" data-format="yyyy-MM-dd" readonly/>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>
					查 询
				 	</button>
				</div>
				<div class="btn-group pull-right" >
<!-- 					<button type="button" class="btn btn-default" id="btn_printpl">打印</button> -->
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
				            <th style="text-align:center;" id="tr_pzrq">凭证状态</th>
				            <th style="text-align:center;" id="tr_pzlx">凭证类型</th>
				            <th style="text-align:center;" id="tr_pzrq">凭证号</th>
				            <th style="text-align:center;" id="tr_pzrq">凭证日期</th>
				            <th style="text-align:center;" id="tr_zy">摘要</th>
				            <th style="text-align:center;" id="tr_pzzh">制单人</th>
				            <th style="text-align:center;" id="tr_pzzh">复核人</th>
				            <th style="text-align:center;" id="tr_pzzh">记账人</th>
				            <th style="text-align:right;" id="tr_dfje">分录条数</th>
				            <th style="text-align:right;" id="tr_xm">金额（元）</th>
				            <th style="text-align:right;" id="tr_pzzh">附件张数</th>
				            <th style="text-align:center;" id="tr_zyy">操作</th>
				        </tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="pzcx" class="form-control input-radius window" name="pzcx" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
var target = "${ctx}/pzcx";
$(function () {
	var columns = [
	               {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	                 return '<input type="checkbox" kjqj="'+full.KJQJ+'" pznd="'+full.PZND+'" name="guid" pzbh="'+full.PZBH+'" sszt="'+full.SSZT+'" sfzt="'+full.SHZT+'" pzz="'+full.PZZ+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
	               },"width":10,'searchable': false},
	               {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	        		   return data; },"width":41,"searchable":false,"class":"text-center"},
	        	   {"data": "PZZTMC",defaultContent:"","width":40},
	        	   {"data": "PZLXMC",defaultContent:"","class":"text-center","width":30},
	               {"data": "PZBH",defaultContent:"","class":"text-left","width":30,"id":"tr_pzzh","width":30,'render':function (data, type, full, meta){
		       		   	return '<a href="javascript:void(0);" class="btn btn-link cxlook" path="${ctx}">'+ (full.PZBH==undefined?'':full.PZBH) +'</a>';
		       		}},
	               {"data": "PZRQ",defaultContent:"","id":"tr_pzzh","class":"text-center","width":50},
	               {"data": "ZY",defaultContent:"无","id":"tr_zy",'render':function(data, type, full, meta){
	            	   var maxlength=20;
	           			if(data==""||data==null||data=="undefined"){
	           				return "";
	           			}else{
	           				if(data.length>=maxlength){
	           					return '<div class="change"><span title="'+data+'">'+data.substr(0,maxlength-3)+'...</sapn></div>'
	           				}else{
	           					return '<div class="change"><span title="'+data+'">'+ data +'</span></div>'
	           				}
	           			}
	               }},
	               {"data": "ZDRMC",defaultContent:"无","id":"tr_pzzh","width":80},
	               {"data": "FZRMC",defaultContent:"无","id":"tr_pzzh","width":80},
	               {"data": "JZRMC",defaultContent:"无","id":"tr_pzzh","width":80},
	               {"data": "FLTS",defaultContent:"","class":"text-right","width":20},
	               {"data": "JFJEHJ",defaultContent:"","id":"tr_bm","class":"text-right","width":30},
	               {"data": "FJZS",defaultContent:"0","id":"tr_pzzh","class":"text-right","width":80},
	               {"data": "GUID",'render':function(data, type, full, meta){
	    	   		if(full.CXZT == "1"){
	    	   			return '<a href="javascript:void(0);" class="btn btn-link btn_qxcx" >取消冲销</a>';
	    	   		}else { 
	    	   			return '<a href="javascript:void(0);" class="btn btn-link btn_cx">冲销</a>';
	    	   		}
	    	   	   },orderable:false,"width":90,"class":"text-center"}
	             ];
	           table = getDataTableByListHj("mydatatables","${ctx}/pzcx/getPageList?zt="+$("[name='pzzt']").val()+"&kssj="+$("[name='kssj']").val()+"&jssj="+$("#jssj").val(),[4,'asc'],columns,0,1,setGroup);
	           
	           
	         //冲销btn_qxcx
	      	 $(document).on("click",".btn_cx",function(){
	      	   	var guid = $(this).parents("tr").find("[name='guid']").val();
	      	    var pzz = $(this).parents("tr").find("[name='guid']").attr("pzz");
	      	   		 confirm("确定要冲销？",{title:"提示"},function(){
	      	   			$.ajax({
	      					type:"post",
	      					url:"${ctx}/pzcx/doDeal1?type=cx&pzz="+pzz,
	      					dataType:"json",
	      					data:"guid="+guid,
	      					success:function(val){	
	      						if(val){
	      							alert("数据已冲销，不可重复冲销！");
	      						}else{
	      							alert("冲销成功！");
	      						}
	      						table.ajax.reload();
	      						window.location.reload();
	      					}
	      				});
	      	   		}); 
	      	   	});
	         
	      	 //取消冲销
	      	 $(document).on("click",".btn_qxcx",function(){
	      	   		var guid = $(this).parents("tr").find("[name='guid']").val();
	      	   		var pzbh = $(this).parents("tr").find("[name=guid]").attr("pzbh");
	      	   		var sszt = $(this).parents("tr").find("[name=guid]").attr("sszt");
	      	   		console.log("=============="+pzbh);
	      	   		console.log("=============="+sszt);
	      	   	    console.log("=============="+guid);
	      	   		confirm("确定要取消冲销？",{title:"提示"},function(){
	      	   			$.ajax({
	      					type:"post",
	      					url:"${ctx}/pzcx/doDeal1?type=qxcx",
	      					dataType:"json",
	      					data:"guid="+guid+"&pzbh="+pzbh+"&sszt="+sszt,
	      					success:function(val){	
	      						if(val){
	      							alert("该数据已经被取消冲销或未冲销！");
	      						}else{
	      							alert("取消冲销成功！");
	      						}
	      						table.ajax.reload();
	      						window.location.reload();
	      					}
	      				});
	      	   		});
	      	   	});
//     	$("#btn_printpl").click(function(){
//      		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
//      		var pzbh = $("#mydatatables").find("[name='guid']").filter(":checked").attr("pzbh");
//      		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
//      		var pznd = checkbox.attr("pznd");
//      		var kjqj = checkbox.attr("kjqj");
//      		console.log("=============="+pzbh);
//      			if(checkbox.length>0 && checkbox.length<2 ){
//      	   			var dwbh = [];
//      	   			checkbox.each(function(){
//      	   				dwbh.push("'"+$(this).val()+"'");
//      	   			});
//   				//doOperate("${ctx}/pzcx/demoPrint?length="+checkbox.length+"&pzbh="+pzbh+"&guid="+dwbh);
//      	   		select_commonWin("${ctx}/kjhs/pzxx/pzlr/pageSkip?pageName=print&pzbh="+pzbh+"&pznd="+pznd+"&kjqj="+kjqj,"凭证打印","1000","650");
//      	   		}else{
//      	   			alert("请选择一条信息打印！");
//      	   		}
     		
//      	});
   	$("#btn_printpl").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var pzbh = $("#mydatatables").find("[name='guid']").filter(":checked").attr("pzbh");
   		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
   		var pznd = checkbox.attr("pznd");
   		var kjqj = checkbox.attr("kjqj");
   		var ymbz = "pz";
   		var guid = [];
   			if(checkbox.length>0 ){
   	   			var dwbh = [];
   	   			checkbox.each(function(){
   	   				dwbh.push("'"+$(this).val()+"'");
   	   			console.log($(this).val());
   	   			guid.push($(this).val());
   	   		console.log(guid);
   	   			});
   	   			//doOperate("${ctx}/pzjz/demoPrint?length="+checkbox.length+"&guid="+dwbh+"&pzbh="+pzbh+"&zt="+$("[name='pzzt']").val());
   	   		select_commonWin("${ctx}/kjhs/pzxx/pzlr/pageSkip?pageName=print&guid="+guid+"&type=jz&pzbh="+pzbh+"&pznd="+pznd+"&kjqj="+kjqj+"&ymbz="+ymbz,"凭证打印","1000","650");
   			}else{
   	   			alert("请选择一条信息打印！");
   	   		}
   		
   	});
	 $("#btn_selcolums").click(function(){
		 select_commonWin("${ctx}/pzjz/col?controlId=pzcx","列信息","500","700");
	 });
	 $("#btn_cxx").click(function(){
		   alert("操作成功");
	   });
	 $("[id=btn_cxs]").click(function(){
// 		   window.location.reload();
		    var zt = $("[name='pzzt']").val();
			var kssj = $("[name='kssj']").val();
			var jssj = $("#jssj").val();
			doOperate("${ctx}/pzcx/goPzcx?zt="+zt+"&kssj="+kssj+"&jssj="+jssj);
		   
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
	 $(document).on("click",".cxlook",function(){
		 var ymbz = "pz";
		 var gs = "pzcx";
		 var guid = $(this).parents("tr").find("[name='guid']").val();
		 pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&guid="+guid+"&ymbz="+ymbz+"&gs="+gs);
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
	var cols = $("#pzcx").val();
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