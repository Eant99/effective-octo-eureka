<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证汇总表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.input_info{
		width:100px !important;
		height:14px !important;
		font-size:2px !important;
	}
	.select_info{
		width:50px !important;
		height:18px !important;
		 border: 1px solid #ccc;
        border-radius: 4px;
        line-height: 28px;
      padding-left: 6px;
	}
	.top_table{
		width:50% !important;
		margin:auto;
		border-right:none;
		border-collapse:collapse!important;
	}
	.top_table>tbody>tr>td {
   	 	border-right: 1px solid #b3b0b0;
   	 	border-bottom: 1px solid #b3b0b0;
   	 	height:30px;
   	 	width:300px !important;
	}
	.index{
		border-left:1px solid #b3b0b0;
	}
	.input_title{
		border:none;
		width:180px;
		height:40px!important;
	}
	.pull-right button{
/* 		background-color: #00acec !important; */
/* 		color: white !important; */
	}
	.sub-title .zhanwei{
		color:#fff;
	}
	#bottom_info{
		width:60% !important;
		margin-left:18%!important;
	}
	span .s{
	   font:bold 15px "Microsoft YaHei";
	 
	}
	.money{
		width:100%;
		background:#f4f4f4;
		margin:0 auto;
	}
	.top_div{
		float:left;
		width:6.66%;
		border:1px solid rgba(191, 187, 187, 1);
	}
	.yc_style{
		display:none!important;
	}
	div .left_div{
		width:100%;
		height:100%;
	}
	div #green{
		border-right:1px solid green!important;
	}
	div #red{
		border-right:1px solid red!important;
	}
	div .left_bootom_div{
		float:left;
		width:6.66%;
		height:100%;
		border:1px solid rgba(191, 187, 187, 1);
	}
	.delete{
		background:#f5d8d8;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top:0px;height:40px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
	                <label>日&emsp;期&ensp;</label>
	             	<input  name="" class="date form-control" style="width:100px;" value="2016-1-1" data-format="yyyy-MM-dd" readonly/>
					<label>至&ensp;</label><input  name="" class="date form-control" style="width:100px;" value="2017-10-25" data-format="yyyy-MM-dd" readonly/>
				</div>
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_exp">打印预览</button>
					<!-- <button type='button' class="btn btn-default" id="btn_exp">导出Excel</button> -->
				</div>
		</div>
		</form>
	</div>
	<div class="container-fluid">
		<center>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                 <span id="txt_bbmc" style="font-size: 16pt">凭证汇总表</span>
               </td>
	        </tr>
	    </center>
	    
	   
	</div>
	
	
	

<div class="box">
		<div class="box-panel">
			<!-- <div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>凭证信息录入</div>
        	</div>	 -->	
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary" style="margin-left:43%;width:100%">
            	<span class="zhanwei">占</span><span class="zhanwei">占</span>
            	汇总凭证<br />
            	日期<input  name="" class="input_info date" value="${sysDate}" data-format="yyyy-MM-dd" readonly/>
            	</div>
            	<div class="actions text-primary" style="margin-right:30%;">
            		凭证字<select class="select_info">
            				<option value="0" selected>记</option>
            			</select>
            		<input  name="" style="width:50px;height:18px;" value="0001"/>号
            	</div>
        	</div>
			<hr class="hr-normal">
			<!-- table start -->
			<div class="container-fluid">
				<div class='responsive-table'>
					<div class='scrollable-area'> 
						<table class="top_table">
						    <tbody style="border:none;">
						   		 	<tr>
							            <td style="text-align:center;border-left:1px solid #b3b0b0;border-left:1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="middle">摘要</td>
							            <td style="text-align:center;border-left: 1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="middle"><span class="s">科目</span></td>
							            <td style="text-align:center;border-left: 1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="bottom">
							            <span class="s">借方金额</span>
							            	<div class="money">
							            		<div class="top_div">万</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">亿</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">万</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">元</div>
							            		<div class="top_div">角</div>
							            		<div class="top_div">分</div>
							            	</div>
							            </td>
							            <td style="height:40px;text-align:center;border-left: 1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="bottom">
							            <span class="s">贷方金额</span>
							            <div class="money">
							            		<div class="top_div">万</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">亿</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">万</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">元</div>
							            		<div class="top_div">角</div>
							            		<div class="top_div">分</div>
							            	</div>
							            </td>
							            <td rowspan="2" id="dual" valign="middle" style="text-align:left;border:none;height:40px;">
							     			&nbsp;附<br><br>
							     			&nbsp;单<br><br>
							     			&nbsp;据<br><br>
							     			&nbsp;<input name="" type="text" style="width:25px;height:20px;" 
							     					onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
							     			<br><br>
							     			&nbsp;张
							     		</td>
						      	  	</tr>	
						    		<tr id="tr_1" title="双击选中删除行，再次双击取消">
							            <td colspan="2" class="index" style="text-align:left;"><input type="" class="input_title writes"/>合计</td>
							            <!-- <td class="">
							           	 <input type="" class="input_title writes" id="km_1"readonly/>
							            </td> -->
							            <td class="div_td">
							            	<div class="left_div">
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="red"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            	</div>
							            	<input type="" class="input_title yc_style sq left"	onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
							            		style="text-align:right;"/>
							            </td>
							            <td class="div_td">
							            	<div class="left_div">
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="red"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            	</div>
							            	<input type="" class="input_title yc_style sq right" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
							            		style="text-align:right;"/>
							            </td>
							        </tr>
							      <%--   <c:forEach varStatus="i" begin="2" end="2">
								        <tr id="tr_${i.index}" title="双击选中删除行，再次双击取消">
								            <td class="index"><input type="" class="input_title writes" /></td>
								            <td class=""><input type="" class="input_title writes" id="km_2" readonly/></td>
								            <td class="div_td">
								            <div class="left_div">
							            		<div class="left_bootom_div" id="green">12312</div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="red"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            	</div>
								            	<input type="" class="input_title yc_style sq left"	onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
								            		style="text-align:right;"/>
								            </td>
								            <td class="div_td" valign="middle">
								            <div class="left_div">
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="red"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            	</div>
								            	<input type="" class="input_title yc_style sq right" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
								            		style="text-align:right;"/>
								            </td>
								        </tr>
							        </c:forEach> --%>
							    	 <tr class="tr_end">
							            <td colspan="2" class="index" style="text-align:left;border:none;"></td>
							            <td class="td_end ld" style="text-align:right; border:none;">
							            	<input type="" class="input_title"	readonly style="text-align:right;"/>
							            </td>
							            <td class="td_end rd" style="text-align:right;border:none;">
							            	<input class="input_title"	readonly value="制单人：${loginName}"/>
							            </td>
							        </tr> 
<!-- 							         <tr> -->
<!-- 							            <td colspan="3" style="border:none;"></td> -->
							            
<!-- 							            <td style="border:none;text-align:left;"> -->
<%-- 							            	<input class="input_title"	readonly value="制单人：${loginName}"/> --%>
<!-- 							            </td> -->
<!-- 							        </tr>   -->
							       
						    </tbody>
						</table>
				    </div>
				</div>
			</div>
</div>
<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
 $(function () {
	var columns = [
	               {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	                 return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
	               },"width":10,'searchable': false},
	               {"data": "PZRQ",defaultContent:"2017-10-16","class":"text-center",id:"tr_pzrq"},
	               {"data": "PZBH",defaultContent:"0001",id:"tr_pzzh"},
	               {"data": "FHYJ",defaultContent:"这是摘要",id:"tr_zy"},
	               {"data": "FHYJ",defaultContent:"10252",id:"tr_kmbh"},
	               {"data": "FHYJ",defaultContent:"会计科目",id:"tr_kmmc"},
	               {"data": "JFJEHJ",defaultContent:"1000.00","class":"text-right",id:"tr_jfje"},
	               {"data": "DFJEHJ",defaultContent:"500.00","class":"text-right",id:"tr_dfje"},
	               {"data": "FHYJ",defaultContent:"教务处",id:"tr_bm"},
	               {"data": "FHYJ",defaultContent:"建设项目",id:"tr_xm"},
	               {"data": "CZR",defaultContent:"李龙",id:"tr_zyy"},
	               {"data": "FHYJ",defaultContent:"山东国子软件",id:"tr_wldw"},
	               {"data": "ZDR",defaultContent:"超级管理员",id:"tr_zdr"},
	               {"data": "FHR",defaultContent:"超级管理员",id:"tr_fhr"},
	               {"data": "CNR",defaultContent:"超级管理员",id:"tr_cnr"},
	               {"data": "JZR",defaultContent:"超级管理员",id:"tr_jzr"},
	               {"data": "PZZT",defaultContent:"否",id:"tr_pzzt"},
	               {"data": "FHYJ",defaultContent:"是",id:"tr_sfdy"}
	             ];
	           table = getDataTableByListHj("mydatatables","${ctx}/pzlr/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
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