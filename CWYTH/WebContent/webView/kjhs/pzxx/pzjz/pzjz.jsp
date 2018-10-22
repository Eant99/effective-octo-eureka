<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>>凭证记账</title>
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
				
					<select style="width:70px;" id="txt_pzzt" class="form-control select" name="pzzt" >
						<option value="all" <c:if test="${zt=='all'}">selected</c:if>>全部</option>
						<option value="01"<c:if test="${zt=='01'||zt==null ||zt eq '' }">selected</c:if>>未记账</option>
						<option value="02"<c:if test="${zt=='02' }">selected</c:if>>已记账</option>
<%-- 						<option value="00"<c:if test="${zt==00 }">selected</c:if>>已保存</option> --%>
<%-- 						<option value="99"<c:if test="${zt==99 }">selected</c:if>>已结账</option> --%>
<!-- 						<option value="">全部</option> -->
					</select>
					
	                <label>&emsp;日期&emsp;</label>
	             	<input id="kssj" name="pzrq" types="CX1" table="K" class="input_info date form-control" style="width:130px;" placeholder="请选择开始时间" value="${firstDay}" data-format="yyyy-MM-dd" readonly/>
					<label>&emsp;至&emsp;</label>
					<input id="jssj" name="pzrq" types="CX2" table="K" class="input_info date form-control" placeholder="请选择结束时间" style="width:130px;" value="${lastDay}" data-format="yyyy-MM-dd" readonly/>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>
					查 询
				 	</button>
				<div class="btn-group pull-right" >
<!-- 					<button type="button" class="btn btn-default" id="btn_view">查看</button> -->
					<button type="button" class="btn btn-default" id="btn_printpl">打印</button>
					<button type="button" class="btn btn-default plugin" id="btn_pljz" <c:if test="${zt!='01'&&zt==null &&zt eq '' }">style="display: none;"</c:if>>批量记账</button>
		             <button type="button" class="btn btn-default plugin" id="btn_plfjz" style="display: none;">批量反记账</button>
	       
<!-- 	                <button type="button" class="btn btn-default" id="btn_selcolums">列选择</button> -->
<!-- 	                <button type="button" class="btn btn-default" id="btn_write">记账</button> -->
<!-- 	                <button type="button" class="btn btn-default" id="btn_somewrite">多条记账</button> -->
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
				            <th style="text-align:center; " id="tr_pzrq">凭证状态</th>
				            <th style="text-align:center;" id="tr_pzrq">凭证日期</th>
				            <th style="text-align:center;" id="tr_pzlx">凭证类型</th>
				            <th style="text-align:center;" id="tr_pzzh">凭证号</th>
				            <th style="text-align:center;" id="tr_zy">凭证摘要</th>
				            <th style="text-align:center;" id="tr_pzzh">制单人</th>
				            <th style="text-align:center;" id="tr_pzzh">复核人</th>
				            <th style="text-align:center;" id="tr_pzzh">记账人</th>
				            <th style="text-align:center;" id="tr_jfje">金额（元）</th>
				            <th style="text-align:center;" id="tr_dfje">分录条数</th>
				            <th style="text-align:center;" id="tr_pzzh">附件张数</th>
				            <th style="text-align:center;" id="tr_sfdy">是否已打印</th>
				            <th style="text-align:center;" id="tr_dfje">操作</th>
<!-- 				            <th style="text-align:center;" id="tr_pzzt">分录条数（条）</th> -->
				        </tr>
					</thead>
					<tbody>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="pzjz" class="form-control input-radius window" name="pzjz" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
var target = "${ctx}/pzjz";
var zt = $("option :selected").val();
$(function () {
	var columns = [
	               {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	                 return '<input type="checkbox" name="guid" kjqj="'+full.KJQJ+'" zdr="'+full.ZDR+'" fhr="'+full.FHR+'" pznd="'+full.PZND+'" sfzt="'+full.SHZT+'" pzbh="'+full.PZBH+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
	               },"width":10,'searchable': false},
	               {"data": "PZZTMC",defaultContent:"","id":"tr_pzzh","class":"text-center"},
	               {"data": "PZRQ",defaultContent:"","class":"text-center"},
	               {"data": "PZLXMC",defaultContent:"","class":"text-center"},
	               {"data": "PZBH",defaultContent:"","id":"tr_pzzh",'render':function (data, type, full, meta){
	       		   	return '<a href="javascript:void(0);" class="btn btn-link jzlook" path="${ctx}">'+ (full.PZBH==undefined?'':full.PZBH) +'</a>';
	       		}},
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
	               {"data": "ZDRMC",defaultContent:"无","id":"tr_pzzh"},
	               {"data": "FHRMC",defaultContent:"无","id":"tr_pzzh"},
	               {"data": "JZRMC",defaultContent:"无","id":"tr_pzzh"},
	               {"data": "JFJE",defaultContent:"","class":"text-right"},
	               {"data": "FLTS",defaultContent:"","class":"text-right"},
	               {"data": "FJZS",defaultContent:"","class":"text-right"},
	               {"data": "SFDY",defaultContent:"","width":10},
	               {"data": "GUID",'render':function (data, type, full, meta){
		       	   		if(full.PZZT=='01'){
		       	   		return '<a  class="btn btn-link btn_jzxx" dwbh = "'+full.GUID+'">记账</a>';	
		       	   		}else if(full.PZZT=='02'){
		       	   		return '<a  class="btn btn-link btn_fjzxx" dwbh = "'+full.GUID+'">反记账</a>';	
		       	   		}else {
		       	   			return '';
		       	   		}
	          			
		       		},orderable:false,"width":50,"class":"text-center"}
	             ];
	
	           table = getDataTableByListHj("mydatatables","${ctx}/pzjz/getPageList?treedwbh=${dwbh}&zt=${zt}&kssj="+$("#kssj").val()+"&jssj="+$("#jssj").val(),[3,'asc'],columns,0,1,setGroup);
	           test();
    
	 $("#btn_selcolums").click(function(){
		 select_commonWin("${ctx}/pzjz/col?controlId=pzjz","列信息","500","700");
	 });
	
	$(document).on("click",".btn_fjzxx",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		confirm("确定要反记账？",{title:"提示"},function(){
   			$.ajax({
				type:"post",
				url:"${ctx}/pzjz/doDeal1?type=fjz",
				dataType:"json",
				data:"guid="+guid,
				success:function(val){	
					if(val){
						alert("反记账成功！");
					}else{
						alert("操作失败！");
					}
					table.ajax.reload();
				}
			});
   		});
   	});
	$(document).on("click",".btn_jzxx",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		var zdr = $(this).parents("tr").find("[name='guid']").attr("zdr");
   		var fhr = $(this).parents("tr").find("[name='guid']").attr("fhr");
   		var jzr = "${loginId}"
   		if(zdr==jzr){
   			alert("记账人和制单人不能是同一人！");
   			return;
   		}
   		if(fhr==jzr){
   			alert("记账人和复核人不能是同一人！");
   			return;
   		}
   		 confirm("确定要记账？",{title:"提示"},function(){
   			$.ajax({
				type:"post",
				url:"${ctx}/pzjz/doDeal1?type=jz",
				dataType:"json",
				data:"guid="+guid,
				success:function(val){	
					if(val){
						alert("记账成功！");
					}else{
						alert("操作失败！");
					}
					table.ajax.reload();
				}
			});
   		}); 
   	});
	
	$("#btn_search").click(function(){
// 		var zt = $("[name='pzzt']").val();
// 		var kssj = $("[name='kssj']").val();
// 		var jssj = $("#jssj").val();
// 		doOperate("${ctx}/pzjz/goPzjz?zt="+zt+"&kssj="+kssj+"&jssj="+jssj);
		$.session.set('isNotfirst', '1');
	});
	
	 $("[id$=write]").click(function(){
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
	//批量打印 /CWYTH/WebContent/webView/wsbx/gwjdf/PzjzPrintSample.jsp
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
	 $("#btn_view").click(function(){
		 var checkbox = $("#mydatatables").find("tbody").find(":checked").filter(":checked");
		   if(checkbox.length==0){
			   alert("请选择一条信息！");
			   return;
		   }
		   select_commonWin("${ctx}/pzjz/view","查看信息","1000","700");
	   });
	
	 $(document).on("click",".jzlook",function(){
		 var guid = $(this).parents("tr").find("[name='guid']").val();
		 var ymbz = "pz";
		 var gs = "pzjz";
		 pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&guid="+guid+"&ymbz="+ymbz+"&gs="+gs);
	 });
	 //批量反记账
	  $("#btn_plfjz").click(function(){
		  var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		  if(checkbox.length>0){
	   			var guid = [];
		   	   	checkbox.each(function(){
		   	   		guid.push($(this).val());
		   	   	});
		   	   	var url = "${ctx}/pzjz/doDeal1?type=fjz";
		   	 	confirm("确定要批量反记账选中的"+checkbox.length+"条信息？","",function(){
		   			$.ajax({
		   	   			url:url,
		   	   			data:"guid="+guid.join("','"),
		   	   			type:"post",
		   	   			async:"false",
		   	   			success:function(val){
		   					if(val){
		   						alert(checkbox.length+"条数据反记账成功");
		   					}
		   	   				table.ajax.reload();
		   	   			}
		   	   		});
		   		});
	   		}else{
	   			var guid = [];
		   	   	$("[name='guid']").each(function(){
		   	   		guid.push($(this).val());
		   	   	});
		   	 	var url = "${ctx}/pzjz/doDeal1?type=fjz";
		   	 	confirm("确定要批量反记账所有信息？","",function(){
		   			$.ajax({
		   	   			url:url,
		   	   			data:"guid="+guid.join("','"),
		   	   			type:"post",
		   	   			async:"false",
		   	   			success:function(val){
		   					if(val){
		   						alert("反记账成功");
		   					}
		   	   				table.ajax.reload();
		   	   			}
		   	   		});
		   		});
	   		}//else
		  
	  });
	 //批量记账
	 $("#btn_pljz").click(function(){
		 var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		 var jzr = "${loginId}"
		 var zdr = "";
		 var fhr = "";
		 var m = "0";
		 var n ="0";
		    if(checkbox.length>0){
		    	$.each(checkbox,function(){
		    		 zdr=$(this).attr("zdr");
		    		 fhr=$(this).attr("fhr");
		    		 if(zdr==jzr){
		    			 m++;
		    		 }
		    		 if(fhr==jzr){
		    			 n++;
		    		 }
		    	});
		    }
		    if(m>0){
		    	alert("记账人和制单人不能是同一人！");
		    	m="0";
		    	return;
		    }
		    if(n>0){
		    	alert("记账人和复核人不能是同一人！")
		    	n="0";
		    	return;
		    }
	   		if(checkbox.length>0){
	   			var guid = [];
		   	   	checkbox.each(function(){
		   	   		guid.push($(this).val());
		   	   	});
		   	   	var url = "${ctx}/pzjz/doDeal1?type=jz";
		   	 	confirm("确定要批量记账选中的"+checkbox.length+"条信息？","",function(){
		   			$.ajax({
		   	   			url:url,
		   	   			data:"guid="+guid.join("','"),
		   	   			type:"post",
		   	   			async:"false",
		   	   			success:function(val){
		   					if(val){
		   						alert(checkbox.length+"条数据记账成功");
		   					}
		   	   				table.ajax.reload();
		   	   			}
		   	   		});
		   		});
	   		}else{
	   			var guid = [];
		   	   	$("[name='guid']").each(function(){
		   	   		guid.push($(this).val());
		   	   	});
		   	 	var url = "${ctx}/pzjz/doDeal1?type=jz";
		   	 	confirm("确定要批量记账所有信息？","",function(){
		   			$.ajax({
		   	   			url:url,
		   	   			data:"guid="+guid.join("','"),
		   	   			type:"post",
		   	   			async:"false",
		   	   			success:function(val){
		   					if(val){
		   						alert("记账成功");
		   					}
		   	   				table.ajax.reload();
		   	   			}
		   	   		});
		   		});
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
function selectCol(){
	var cols = $("#pzjz").val();
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
		$(this).find("td").eq(8).attr("id","tr_pzzt");
	});
	
}
// $("[name='pzzt']").change(function(){
// 	//btn_search1.click();
// 	var zt = $("#txt_pzzt").val();
// 	console.log("zt======123===="+zt);
// });

	// 下面这个方法是原来的，在下拉框中选择后，刷新页面的方式来显示新数据的
// $("#txt_pzzt").change(function(){
// 	var zt = $(this).val();
// 	location.href="${ctx}/pzjz/goPzjz?zt="+zt;
// });

	// 下拉框（全部、已记账、未记账）改变  
	// ajax刷新，仿财务一体化/网上报销/报销业务查询
	// 根据列表table的显示的代码改的，只写其中改变了的下拉列表的这个参数（即zt）。
	// "${ctx}/pzjz/getPageList?treedwbh=${dwbh}&zt=${zt}&kssj="+$("[name='kssj']").val()+"&jssj="+$("#jssj").val()
	$("#txt_pzzt").change(function(){
		var val = $(this).val();
		if(val == "01"){
			$("#btn_pljz").show();
			$("#btn_plfjz").hide();
		}else if(val == "02"){
			$("#btn_pljz").hide();
			$("#btn_plfjz").show();
		}
		else{
			$(".plugin").hide();
		}
		tableRelod(table);
	});
	
	var tableRelod = function(table) {
		var zt=$("[id='txt_pzzt']").val();  	
	    table.ajax.url("${ctx}/pzjz/getPageList?zt="+zt+"&kssj="+$("#kssj").val()+"&jssj="+$("#jssj").val());
	    table.ajax.reload();
	};

</script>
</body>
</html>

