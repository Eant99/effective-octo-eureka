<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
   	<input type="hidden" name="zl" id="zl" value="${dwbh}" />
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action=""style="padding-top: 8px;padding-bottom: 2px">
    		<div class="search-simple">
    			<div class="form-group">
					<label>审核状态</label>
					<select style="width:150px;" class="form-control input-radius" name="checkshzt"  table="B" >
						<option value="">全部</option>
						<option value="00">未提交</option>
	                	<option value="11">审核中</option>
	                	<option value="99">已通过</option>
					</select>
				</div>
				<div class="form-group">
					<label>发放流水号</label>
					<input type="text" id="txt_dwbh" class="form-control input-radius" name="fflsh" table="B" placeholder="请输入发放流水号">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
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
				            <th>序号</th>
				            <th>审核状态</th>
				            <th>流水号</th>
				            <th>录入日期</th>
				            <th>发放方式</th>
				            <th>发放方案</th>
				            <th>摘要</th>
				            <th>财务项目</th>
				            <th>人员类型</th>
				            <th>操作</th>
				    </tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
            </div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var columns = [
	               {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	                 return '<input type="hidden" class="procinstid" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"><input type="checkbox" name="guid" shzt="'+full.SHZTDM+'" class="keyId"  ffry="'+full.FFRY+'"   value="' + data + '" guid = "'+full.GUID+'">';
	               },"width":10,'searchable': false},
	               {"data": "_XH",defaultContent:"","class":"text-center","width":20},
	               {"data": "SHZTDM",defaultContent:"","class":"text-center"},
	               {"data": "FFLSH",defaultContent:""},
	               {"data": "LRSJ",defaultContent:""},
	               {"data": "FFFS",defaultContent:""},
	               {"data": "FFFA",defaultContent:""},
	               {"data": "ZY",defaultContent:""},
	               {"data": "XMMC",defaultContent:""},
	               {"data": "RYLX",defaultContent:""},
	               {"data": "PROCINSTID",'render':function(data, type, full, meta){
	            	   if(data=='undefined'){
	            		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>';
	            	   }else if(full.CHECKSHZT=="99"){
	            		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>'; 
	            	   }else if(full.SHZT=="未提交"){
	            		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>'; 
	            	   }else if(full.CHECKSHZT=="00"&&full.SHZT!="未提交"){
	            		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>';
	            		   
	            	   }else{
	            		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>'; 
	            	   }
	        	   		
	              },orderable:false,"class":"text-center"}
	             ];
	       	//表数据
          	table = getDataTableByListHj("mydatatables","${ctx}/xzsbcx/getPageList?treedwbh=${dwbh}&shzt="+$("#shzt").val(),[2,'desc'],columns);
    //办理记录
   	$(document).on("click",".btn_bljl",function(){
   		var processInstanceId=$(this).parents("tr").find("[name='procinstid']").val();
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=ccyw","C");
   		
   	});
  	//打印
	$(document).on("click",".btn_print",function(){
 		var dwbh = $(this).attr("dwbh");
 		var procinstid = $(this).attr("procinstid"); 
 		console.log("procinstid------------"+procinstid);
 		var lx = $(this).attr("lx"); 
 		if(lx=="差旅费报销"){
 			doOperate("${ctx}/wsbx/ccbx/goPrint?guid="+dwbh+"&procinstid="+procinstid+"&lx="+lx);
 		}else{
 			doOperate("${ctx}/kylbx/singlePrintwdbxByMe?guid="+dwbh+"&procinstid="+procinstid+"&lx="+lx);
 		}		
	});
  //查看按钮
	   $(document).on("click",".btn_look",function(){
		   var rylx = $(this).parents("tr").find(":checkbox").attr("ffry");
	   	   var guid = $(this).parents("tr").find(":checkbox").val();
		   doOperate("${pageContext.request.contextPath}/xzsblr/goEditPage?rylx="+rylx+"&guid="+guid+"&ymbz=cx","L");
	});
   	//jsp 页面导出Excel
   	$(document).on("click","#btn_exp",function(){
   		var id = [];
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/xzsbcx/expExcel?shzt="+$("#shzt").val(),"个人收入信息","${pageContext.request.contextPath}",id);
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
</html>
