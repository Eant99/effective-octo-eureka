<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公务接待费报销申请信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.btn-link{
	padding: 0px!important;
	margin: 0px!important;
}
.text-green{
		color:green!important; 
	}
	.box-bottom{
 		text-align: center; 
		padding-top: 20px; 
 		padding-bottom: 20px; 
 		margin-left: -1020px; 
	}
	.dataTables_scrollHeadInner{
		width:100%! important;
	}
/* 	table.dataTable{ */
/* 		width:750px ! important; */
/* 	} */
	#dualTd{
 			text-align:center! important;
 			padding-top:20px;
 			width:100%! important;
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
				<div class="sub-title pull-left text-primary">选择公务接待&nbsp;></div>
				
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
	        
			<div class="search-simple">
			
				<div class="form-group">
					<div class="sub-title2 pull-left text-primary" style="font-size: 14px;"><i class="fa icon-xiangxi"></i>第一步，选择公务接待</div>
					<label style="margin-left:50px;">单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh"  placeholder="请输入单据编号">
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
					</button>
					<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
	      		</div>
     	 		
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th style="width:10px;">序号</th>
				            <th style="width:150px;">单据编号</th>
				            <th style="width:100px;">申请人</th>
				            <th style="width:200px;">接待部门</th>
				            <th style="width:100px;">接待日期</th>
				            <th style="width:100px;">来宾单位</th>
				            <th style="width:100px;">接待地点</th>
				            <th style="width:100px;">申请日期</th>
				            
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
var suffix = "&mkbh=${param.mkbh}";
var type = "${param.type}";
//加载列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
    	   return '<input type="checkbox" name="guid" class="keyId" value="'+data+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "DJBH",defaultContent:""},
       {"data": "SQR",defaultContent:"","width":100},
       {"data": "JDDW",orderable:false,defaultContent:"","width":200},
       {"data": "JDRQ",defaultContent:"","class":"text-center"},
       {"data": "LBDW",defaultContent:"","width":400},
       {"data": "JDFD",defaultContent:"","width":400},
       {"data": "SQRQ",defaultContent:"","class":"text-center"},
       
     ];
    table = getDataTableByListHj("mydatatables",target+"/getGwjdPageList",[3,'asc'],columns,0,1,setGroup);
    kz();
//
$(function () {
	$(document).ready(function(){
		$("#mydatatables").after('<div class="0box-bottom0" id="dualTd"><button type="button" id="btn_next" class="btn btn-primary" >下一步</button></div>');
	});
	
  	$("#btn_back").click(function(){
  		pageSkip(target,"gwjdfbxsq_list",suffix);
  	});
  	
 /*  	document.onkeypress = function(){  
   	    if(event.keyCode == 13)   
   	   {  
   	         return  false;  
   	   }  
   	}; */
   	$(document).on("keypress",function(){
   		if(event.keyCode == 13)   
    	   {  
    	         return  false;  
    	   }  
   	});
  	
  	$(document).on("click","#btn_next",function(){
  		var checkbox = $("#mydatatables").find(".keyId:checked");
//   		if(checkbox.length==0){
// 			confirm("您还没有选择公务接待事前审批，是否直接填写报销信息？",null,{title:"提示"},function(){
// 				$("a.layui-layer-btn1",top.document)[0].click();
// 				pageSkip(target,"gwjdmx_add&sfsqsp=1",suffix);
// 			});
//   		}else if(checkbox.length==1){
//   			var guid = checkbox.val();
// 	  		pageSkip(target,"gwjdmx_add&gwjdsqspGuid="+guid+"&sfsqsp=1",suffix);
//   		}else{
//   			alert("只能选择一条信息");
//   		}
  		if(checkbox.length==1){
  			var guid = checkbox.val();
	  		pageSkip(target,"gwjdmx_add&gwjdsqspGuid="+guid+"&sfsqsp=1",suffix);
  		}else{
  			alert("只能选择一条信息");
  		}
  	});
  	$("#btn_ywsm").click(function(){
		select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
	});
});
function kz(){
	$.each($("[name='guid']"),function(i,v){
		var guid = $(this).val();
		if(guid=="${gwjdsqspGuid}"){
			$(this).attr("checked","checked");
		}
	});
}
</script>
</body>
</html>