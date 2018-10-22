<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产负债表</title>
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
	ul li{
	list-style-type:none;
	
	}
	.bottom1{
	margin-top: 50px;
	}
	.bottom1 tr td{
	width: 400px !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
	                <label>选择日期</label>
	             	<input type="text" id="txt_rq" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="form-control input-radius"  value="2017-01"/>					
					&nbsp;					
					是否包含未记账凭证&nbsp;<select style="width:60px;" class="form-control select2">					
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
					&nbsp;				
					是否包含结转凭证&nbsp;<select style="width:60px;" class="form-control select2">					
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
	<div class="container-fluid" style="width: 100%">

		<center>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                 <span id="txt_bbmc" style="font-size: 16pt">资产负债表</span>
               </td>
	        </tr>
	    </center>
	    
	    <div class="btn-group pull-right" role="group">
	          <ul>
	          <li>
	    	 <td align="right" valign="middle" style="width: 400px; height: 20px;">
                                会中高校01表             
                </td> 
                </tr>
                </li>
                <li>
                <tr>
                <td align="right" valign="bottom" style="width: 429px; height: 20px;">
                              单位：元
                </td> 
                </tr>
                </li>
                </ul>  
	    </div>
	  
	    <div style="margin-top: 20px;position: absolute;">        	
                                编制单位：山东国子软件
	    </div>
	      <div style="margin-left: 48%;margin-top:20px">    	    	
                        2017年01月29日
	    </div>
	</div>

</div>
<div class="container-fluid" >
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;">
		        	<thead>
				        <tr>
				            
				            <th style="text-align:center;width: 80px;" id="tr_pzrq" >科目</th>
				            <th style="text-align:center;" id="tr_pzzh">资产部类</th>
				            <th style="text-align:center;" id="tr_zy">年初数</th>
				            <th style="text-align:center;" id="tr_zy">期末数</th>
				              <th style="text-align:center;width: 80px;" id="tr_pzrq" >科目</th>
				            <th style="text-align:center;" id="tr_pzzh">资产部类</th>
				            <th style="text-align:center;" id="tr_zy">年初数</th>
				            <th style="text-align:center;" id="tr_zy">期末数</th>
				           
				           
				        </tr>
				        
					</thead>
				    <tbody>
				    </tbody>
				</table>					
		</div>
			</div>
			
			
<div class="bottom1">
<table>
<tr>
<td>单位主要负责人（签字）：</td>
<td>财务负责人（签字）：</td>
<td>制表人（签字）：</td>
</tr>
<tr>
<td>
注：本表反映核算单位各项资产及负债情况。
</td>
</tr>
</table>
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
	               {"data": "FHYJ",defaultContent:"是",id:"tr_sfdy"},
	               {"data": "PZZT",defaultContent:"否",id:"tr_pzzt"}
	             ];
	           //table = getDataTableByListHj("mydatatables","${ctx}/pzlr/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
	         
	//打印
	 $("[id$=print]").click(function(){
		 
		 select_commonWin("${ctx}/webView/kjhs/pzxx/print.jsp","双击页面打印","920","630");
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

	

</script>
</body>
</html>