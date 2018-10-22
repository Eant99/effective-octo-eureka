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
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			<div class="form-group">
						<label>审核状态</label> <select id="drp_shzt" class="form-control"
							name="shzt" table="K">
							<option value="">未选择</option>
							<option value="1">未提交</option>
							<option value="2">审核中</option>	
							<option value="3">审核退回</option>
							<option value="4">审核通过</option>					
						</select>
					</div>
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_mldm" class="form-control" name="mldm" table="A" placeholder="请输入目录代码">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_mlmc" class="form-control" name="mlmc"  table="A" placeholder="请输入目录名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>审核状态</label>
							 <select id="drp_shzt" class="form-control input-radius" name="shzt"> 							
	                        		<option value="" >未选择</option>
	                        		<option value="0" >通过</option>
	                        		<option value="1" >不通过</option>                        		                   
							</select>
						</div>
						<div class="form-group">
							<label>项目编号</label>
							<input type="text" id="txt_fabh" class="form-control input-radius" name="fabh" value="" types="D" table="A" placeholder="请输入项目编号">         	
						</div>
						<div class="form-group">
							<label>项目名称</label>
							<div class="input-group">							
								<input type="text" id="txt_famc" class="form-control input-radius" name="famc" value="" types="D" table="A" placeholder="请输入项目名称"> 
							</div>
						</div>
						<div class="form-group">
							<label>分类名称</label>
							<input type="text" id="txt_cjrq" class="form-control input-radius" name="cjrq"   data-format="yyyy-MM-dd" placeholder="请输入分类名称">
						</div>
						<div class="form-group">
							<label>服务专业</label>
							<input type="text" id="txt_fabh" class="form-control input-radius" name="fabh" value="" types="D" table="A" placeholder="请输入服务专业">         	
						</div>
						<div class="form-group">
							<label>服务学科</label>
							<input type="text" id="txt_fabh" class="form-control input-radius" name="fabh" value="" types="D" table="A" placeholder="请输入服务学科">         	
						</div>
						<div class="form-group">
							<label>申报单位</label>
							<input type="text" id="txt_fabh" class="form-control input-radius" name="fabh" value="" types="D" table="A" placeholder="请输入申报单位">         	
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">录入</button>
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
				            <th>项目编号</th>
				            <th>项目名称</th>	
				            <th>分类名称</th>
				            <th>申报单位</th>
				            <th>服务专业</th>
				            <th>服务学科</th>
				            <th>预算金额（元）</th>
				            <th>计划执行时间</th>
				             <th>工期（天）</th>    		       
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
       {"data": "SHZT",defaultContent:"通过"},
       {"data": "XMBH",defaultContent:"001"},
       {"data": "XMMC",defaultContent:"测试项目"},
       {"data": "FLMC",defaultContent:"测试类"},
       {"data": "SBDW",defaultContent:""},
       {"data": "FWZY",defaultContent:""},
       {"data": "FWXK",defaultContent:""},
       {"data": "YSJE",defaultContent:""},
       {"data": "JHZXSJ",defaultContent:""},
       {"data": "GQ",defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
      },orderable:false,"class":"text-center","width":220}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/zclr/getPageList",[2,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
   	$("#btn_add").click(function(){
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
   	$("#btn_exp").click(function(){
   		alert("导出成功");
   	});
   	//查看按钮
   	$(document).on("click",".btn_look",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/xmgl/zcgl/zclr_view.jsp","C");
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