<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支出录入</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 12px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="k"  placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc" table="k"   placeholder="请输入项目名称">
				</div>
				<div class="form-group">
					<label>部门名称</label>
					<input type="text" id="txt_bmmc" class="form-control" name="bmmc" table="k"   placeholder="请输入部门名称">
				</div>
				<div class="form-group">
					<label>项目负责人</label>
					<input type="text" id="txt_xmfzr" class="form-control" name="xmfzr" table="k"   placeholder="请输入项目负责人">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
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
				            <th>部门名称</th>
				            <th>项目大类</th>
				            <th>项目类别</th>
				            <th>项目编号</th>
				            <th>项目名称</th>	
				            <th>项目负责人</th>
				            <th>预算金额（元）</th>
				            <th>可用金额（元）</th>
				            <th>审核通过金额（元）</th>
				            <th>待审核金额（元）</th>
				            <th>剩余金额（元）</th>
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
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'" zcje = "'+full.ZCJE+'" dshje = "'+full.DSHJE+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "BMMC",defaultContent:"","class":"text-left"},
       {"data": "XMDLMC",defaultContent:"","class":"text-left"},
       {"data": "XMLBMC",defaultContent:"","class":"text-left",},
       {"data": "XMBH",defaultContent:""},
       {"data": "XMMC",defaultContent:""},
       {"data": "XMFZR",defaultContent:""},
       {"data": "YSJE",defaultContent:"","class":"text-right"},
       {"data": "KYJE",defaultContent:"","class":"text-right"},
       {"data": "ZCJE",'render':function(data, type, full, meta){
    	   if(full.ZCJE==undefined){
	   		return '<a href="javascript:void(0);" guid='+full.GUID+' zcje = '+full.ZCJE+' class="btn btn-link btn_look">0.00</a>';
    	   }else{
    	    return '<a href="javascript:void(0);" guid='+full.GUID+' zcje = '+full.ZCJE+' class="btn btn-link btn_look">'+data+'</a>';
    	   }
       },orderable:false,"class":"text-right","width":220},
       {"data": "DSHJE",'render':function(data, type, full, meta){
    	   if(full.DSHJE==undefined){
	   		return '<a href="javascript:void(0);" guid='+full.GUID+' dshje = '+full.DSHJE+' class="btn btn-link btn_dsh">0.00</a>';
    	   }else{
    	    return '<a href="javascript:void(0);" guid='+full.GUID+' dshje = '+full.DSHJE+' class="btn btn-link btn_dsh">'+data+'</a>';
    	   }
       },orderable:false,"class":"text-right","width":220},
       {"data": "SYJE",defaultContent:"","class":"text-right"}
     
      
       
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/wdxm/getPageList",[2,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		guid.push($(this).val());
   	   		});
   	   	doOperate("${ctx}/zclr/goEditPage","C");
   		}else{
   			alert("请选择一条项目信息进行录入!");
   		}
   	});
	//导出Excel
	$(document).on("click","#btn_exp",function(){
//    	$("#btn_exp").click(function(){
   		alert("导出成功");
   	});
   	//查看审核通过页面按钮
   	$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
    		window.location.href="${ctx}/wdxm/goMxPage?guid="+guid;
   	});
  	//查看待审核通过页面按钮
	$(document).on("click",".btn_dsh",function(){
   		var guid = $(this).attr("guid");
   		window.location.href="${ctx}/wdxm/goDshPage?guid="+guid;
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