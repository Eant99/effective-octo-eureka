<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金分配审核</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<div class="search-simple">
					<div class="form-group">
						<label>审核状态</label> <select id="drp_shzt" class="form-control"
							name="shzt" >
							<option value="">未选择</option>
							<option value="1">未提交</option>
							<option value="2">审核中</option>	
							<option value="3">审核退回</option>
							<option value="4">审核通过</option>					
						</select>
					</div>
					<div class="form-group">
						<label>项目名称</label> <input type="text" id="txt_xmmc"
							class="form-control" name="xmmc"  placeholder="请输入项目名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
					<div id="btn_search_more">
						<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
						<div class="search-more">
							<div class="form-group">
								<label>审核状态</label> <select id="drp_shzt" class="form-control"
									name="shzt" >
								<option value="">未选择</option>
							    <option value="1">通过</option>
							    <option value="2">不通过</option>	
								</select>
							</div>
							<div class="form-group">
								<label>项目名称</label> <input type="text" id="txt_xmmc"
									class="form-control input-radius"  name="xmmc"
									placeholder="请输入项目名称">
							</div>
							<div class="form-group">
								<label>分类名称</label> <input type="text" id="txt_flmc"
									class="form-control input-radius"  name="flmc"
									placeholder="请输入分类名称">
							</div>
							<div class="form-group">
								<label>服务专业</label> 
							<input type="text" id="txt_fwzy" class="form-control" name="fwzy" placeholder="请输入服务专业">
							</div>
                             <div class="form-group">
								<label>服务学科</label> <input type="text" id="txt_fwxk"
									class="form-control input-radius"  name="fwxk"
									placeholder="请输入服务学科">
							</div>		
							<div class="search-more-bottom clearfix">
								<div class="pull-right">
									<button type="button" class="btn btn-primary" id="btn_search">
										<i class="fa icon-chaxun"></i> 查 询
									</button>
									<button type="button" class="btn btn-default" id="btn_cancel">
										取 消</button>
								</div>
							</div>
						</div>
					</div>
					<div class="btn-group pull-right" role="group">				
						<button type="button" class="btn btn-default" id="btn_SH">审核</button>						
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
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>审核状态</th>
								<th>项目名称</th>
								<th>分类名称</th>
								<th>服务专业</th>
								<th>服务学科</th>
								<th>计划执行时间</th>
								<th>预算金额（元）</th>
								<th>已分配金额（元）</th>
								<th>资金编号</th>
								<th>资金来源</th>
								<th>创建人</th>
								<th>创建日期</th>	
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
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" shzt="'+full.SHZT+'" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "SHZT",defaultContent:"","class":"text-center"},
       {"data": "XMMC",defaultContent:""},
       {"data": "FLMC",defaultContent:""},
       {"data": "FWZY",defaultContent:""},
       {"data": "FWXK",defaultContent:""},
       {"data": "JHZXSJ",defaultContent:""},
       {"data": "YSJE",defaultContent:"","class":"text-right",'render':function(data, type, full, meta){
    	   if(data == ""||data == null){
    		   data = 0;
    	   }
    	   return data.toFixed(2);
       }},
       {"data": "YFPJE",defaultContent:"","class":"text-right",'render':function(data, type, full, meta){
    	   if(data == ""||data == null){
    		   data = 0;
    	   }
    	   return data.toFixed(2);
       }},
       {"data": "ZJBH",defaultContent:""},
       {"data": "ZJLY",defaultContent:""},
       {"data": "CJR",defaultContent:""},
       {"data": "CJRQ",defaultContent:""},
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
     },orderable:false,"width":220,"class":"text-center"}
       
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/zjfpfa/getShPageList",[2,'asc'],columns,0,1,setGroup);
    //查看
	$(".btn_look").click(function(){
		var guid = $(this).parents("tr").find(".keyId").val();
   		location.href="${ctx}/zjfpfa/goShViewPage?guid="+guid;
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功！");
   	});
    //审核按钮
   	$("#btn_SH").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked[shzt='待审核']");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
   	   	select_commonWin("${ctx}/webView/xmgl/zjgl/zjfpsh/zjfpsh_sh.jsp?guid="+guid.join("','"),"审核信息","620","330");
   		}else{
   			alert("请选择至少一条待审核信息!");
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
</script>
</body>
</html>