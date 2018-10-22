<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>银行对账</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="btn-group pull-right" role="group">
				  <button type='button' class="btn btn-default" id="btn_sea">查询</button>
				</div>
			</div>
		</form>
	</div>
	
    <div class="container-fluid" id="print_div" >
      <div style="width:100%;overflow:auto;height:90%;margin-bottom:15px;">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;width:82%;margin:10px auto 30px;" border="1" bordercolor="#000000">
		        	<thead>
				        <tr id="header">
				            <th style="text-align:center; width: 100px;" id="tr_pzrq">凭证日期</th>
				            <th  style="text-align:center;" id="tr_pzbh">凭证类型</th>
				            <th  style="text-align:center;" id="tr_pzbh">凭证编号</th>
				            <th  style="text-align:center;" id="tr_zy">摘要</th>				            	                
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

<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/LodopFuncs.js"></script>
<script>
$(function () {
	 jumpWindow();

	  $.ajax({
			type:"post",
			async : false, 
			url:"${ctx}/yhdz/getPageList?zfsj=${zfsj}&je=${je}&khxlh=${khxlh}",
			dataType: "json",
			success:function(val){
				for (var i=0;i<val.length;i++){
					 var PZRQ=val[i].PZRQ;if(PZRQ==null) PZRQ='';
					 var PZBH=val[i].PZBH;if(PZH==null) PZBH='';
					 var GUID=val[i].GUID;if(GUID==null) GUID='';
					 var PZLXMC=val[i].PZLXMC;if(PZLXMC==null) PZLXMC='';
					 var ZY=val[i].ZY;if(ZY==null) ZY='';
					
					 $("tbody").append(
							 "<tr >"+
								"<td style='text-align:center;'>"+PZRQ+"</td>"+
								"<td name='pzlxmc' style='text-align:left;' value='"+PZLXBC+"'>"+PZLXBC+"</td>"+
								"<td name='pzbh' style='text-align:left;' value='"+GUID+"' >"+PZH+"</td>"+
								"<td >"+ZY+"</td>"+
								"</tr>"
					);}
			},
		});	 
	  
	  
});
function toUrl(zfsj,je,khxlh){
	location.href="${ctx}/yhdz/goYhdzPage?zfsj="+zfsj+"&je="+je+"&khxlh="+khxlh;
}

function jumpWindow(){
	var jump = "${jump}";
	var clicks = "${clicks}";
	if(jump=="yes"&&clicks!="yes"){
		$("#btn_sea").click();
	}
}
$("[id$=btn_sea]").click(function(){
 	select_commonWin("${ctx}/yhdz/jumpWindow","银行对账","520","400");
});

//双击事件 id="header"
$(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	var guid = $(this).find("[name='pzbh']").attr("value");
	pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&guid="+guid);
});

	
	
</script>
</body>
</html>