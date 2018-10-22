<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入支出表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	table{
		border-collapse:collapse!important;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		 border-bottom-width: 0!important;
	}
	.select2-selection{
		border-radius: 4px!important;
	}
	thead th{
		text-align:center!important;
	}
	.allDiv{
		width:100%;
		height:20px;
	}
	.first{
		float:left;
		text-align:left;
		width:33%;
	}
	.second{
		float:left;
		text-align:center;
		width:34%;
	}
	.third{
		float:left;
		text-align:right;
		width:33%;
	}
	.div_bottom{
    	width: 99%;
    	margin-top: 20px;
    	bottom: 20px;
   		background-color: #f3f3f3;
	}
	.bom{
		float:left;
		text-align:left;
		width:33%;
	}
/* 	.table{ */
/* 		width:50%!important; */
/* 	} */
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>选择日期</label>
					<input type="text" class="form-control year" name="ksrq"  value="2018">
				</div>
				<div class="form-group">
					<label>是否包含未记账凭证</label>
					<select style="width:70px;" class="form-control">
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
				</div>
				<div class="form-group">
					<label>是否包含转账凭证</label>
					<select style="width:70px;" class="form-control">
						<option value="2">否</option>
						<option value="1">是</option>
					</select>
				</div>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_exp">导出excel</button>
	               <button type="button" class="btn btn-default" id="btn_print">打印预览</button>
				</div>
			</div>
		</form>
	</div>
		<div class="container-fluid" style="width: 60%">

		<center>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                 <span id="txt_bbmc" style="font-size: 16pt">收入预算支出表</span>
               </td>
	        </tr>
	    </center>
	    
	    <div class="btn-group pull-right" role="group">
	          <ul>
	          <li>
	    	 <td align="right" valign="middle" style="width: 400px; height: 20px;">
                              会政财01表             
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
	      <div style="margin-left: 47.5%;margin-top:20px">    
	    	
                     2018年
               
	    </div>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
<!-- 			<center><h3>收入预算支出表</h3></center> -->
			<div class='scrollable-area' style="text-align:center;"> 
<!-- 				<div class="allDiv" style="width: 60%;margin:auto"> -->
<!-- 					<div class="first"> -->
<!-- 						编制单位：山东国子软件 -->
<!-- 					</div> -->
<!-- 					<div class="second"> -->
<!-- 						2018年 -->
<!-- 					</div> -->
<!-- 					<div class="third"> -->
<!-- 						单位:元 -->
<!-- 					</div> -->
<!-- 				</div> -->
				<table id="mydatatables" class="table table-striped table-bordered" style="width: 60%;margin:0 auto 30px">
		        	<thead>
				        <tr>
				        	<th>项目</th>
				        	<th>本年数</th>
				        	<th>上年数</th>
				        </tr>
					</thead>
				    <tbody>
				    	<c:forEach items="${list}" var="list">
				    		<tr>
					    		<td style="text-align:left;">${list.XMMC}</td>
					    		<td style="text-align:right;">${list.BYS}</td>
					    		<td style="text-align:right;">${list.BNLJS}</td>
				    		</tr>
				    	</c:forEach>
				    </tbody>
				</table>
<!-- 				<div class="div_bottom"> -->
<!-- 					<div class="bom">单位主要负责人(签字):</div> -->
<!-- 					<div class="bom">财务负责人(签字):</div> -->
<!-- 					<div class="bom">制表人(签字):</div> -->
<!-- 				</div> -->
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
// 	var columns = [
// 	           	{"data": "XH",orderable:false, "render": function (data, type, full, meta){
// 	    	       	return '<input type="checkbox" name="djbh" value="' + data + '">';// url="'+full.URL+'"
// 	    	    },"width":10,'searchable': false},
// 	       		{"data": "XMMC",defaultContent:"",orderable:false},
// 	       	   	{"data": "DATES",defaultContent:"",class:"text-right",orderable:false},
// 	       	   	{"data": "DATES",defaultContent:"",class:"text-right",orderable:false},
// 	       	];
// 	          	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/yskjbb/yssrzcb",[],columns,0,1,setGroup);
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