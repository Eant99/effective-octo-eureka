<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支出审核</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>审核状态</label>
					<select id="drp_shzt" class="form-control input-radius" name="shzt"> 							
	                        		<option value="" >未选择</option>
	                        		<option value="0" >通过</option>
	                        		<option value="1" >不通过</option>                        		                   
							</select>
				</div>
				<div class="form-group">
					<label>方案编号</label>
					<input type="text" id="txt_mlmc" class="form-control" name="mlmc"  table="A" placeholder="请输入方案编号">
				</div>
				<div class="form-group">
					<label>方案名称</label>
					<input type="text" id="txt_mlmc" class="form-control" name="mlmc"  table="A" placeholder="请输入方案名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_SH">审核</button>
	               <button type="button" class="btn btn-default" id="btn_PLSH">批量审核</button>
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
				            <th>物品名称</th>
				            <th>采购目录</th>	
				            <th>计划金额（元）</th>
				            <th>采购金额（元）</th>
				            <th>采购日期</th>
				            <th>资金编号</th>
				            <th>资金来源</th>
				            <th>支出日期</th>
				             <th>采购合同编号</th>  
				             <th>合同名称</th>
				             <th>计量单位</th>
				             <th>采购数量</th>
				             <th>单价（元）</th>
				             <th>品牌</th>
				             <th>产品型号</th>
				             <th>产地类型</th>
				             <th>存放地点</th>
				             <th>组织形式</th>
				             <th>采购机构</th>
				             <th>采购方式</th>
				             <th>经手人</th>
				             <th>供应商</th>       
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
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "SHZT",defaultContent:""},
       {"data": "WPMC",defaultContent:""},
       {"data": "CGML",defaultContent:""},
       {"data": "JHJE",defaultContent:""},
       {"data": "CGJE",defaultContent:""},
       {"data": "CGRQ",defaultContent:""},
       {"data": "ZJBH",defaultContent:""},
       {"data": "ZJLY",defaultContent:""},
       {"data": "ZCRQ",defaultContent:""},
       {"data": "CGHTBH",defaultContent:""},
       {"data": "HTMC",defaultContent:""},
       {"data": "JLDW",defaultContent:""},
       {"data": "CGSL",defaultContent:""},
       {"data": "DJ",defaultContent:""},
       {"data": "PP",defaultContent:""},
       {"data": "CPXH",defaultContent:""},
       {"data": "CDLX",defaultContent:""},
       {"data": "CFDD",defaultContent:""},
       {"data": "ZZXS",defaultContent:""},
       {"data": "CGJG",defaultContent:""},
       {"data": "CGFS",defaultContent:""},
       {"data": "JSR",defaultContent:""},
       {"data": "GYS",defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
      },orderable:false,"class":"text-center","width":220}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/zclr/getPageList",[2,'asc'],columns,0,1,setGroup);
	
  	//审核按钮
   	$("#btn_SH").click(function(){
   		
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		guid.push($(this).val());
   	   		});
   	   	doOperate("${ctx}/webView/xmgl/zcgl/zcsh_edit.jsp","C");
   	   
   		}else{
   			alert("请选择一条审核信息!");
   		}
   	});
	//批量审核按钮
   	$("#btn_PLSH").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		guid.push($(this).val());
   	   		});
   	   	select_commonWin("${ctx}/webView/xmgl/zcgl/zcsh_sh.jsp","审核信息","620","330");
   	   
   		}else{
   			alert("请选择至少一条审核信息!");
   		}
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功");
   	});
   	//查看按钮
   	$(document).on("click",".btn_look",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/xmgl/zcgl/zcsh_view.jsp","C");
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