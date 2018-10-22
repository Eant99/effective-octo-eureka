<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目申报</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="A" placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  table="A" placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>项目编号</label>
							<div class="input-group">
								<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="A" placeholder="请输入项目编号">
							</div>
						</div>
						<div class="form-group">
							<label>项目名称</label>
							<div class="input-group">
								<input type="text" id="txt_xmmc" class="form-control" name="xmmc" table="A" placeholder="项目名称">
							</div>
						</div>
						<div class="form-group">
							<label>分类名称</label>
							<div class="input-group">
								<input type="text" id="txt_bxr" class="form-control" name="bxr" table="A" placeholder="请输入分类名称">
							</div>
						</div>
						<div class="form-group">
							<label>服务专业</label>
							<input type="text" id="txt_bxr" class="form-control" name="bxr" table="A" placeholder="服务专业">
						</div>
						<div class="form-group"> 
							<label>服务学科</label>
							<div class="input-group">
								<input type="text" id="txt_bmmc" class="form-control input-radius" name="bmmc"  table="A" placeholder="服务学科">
							</div>
						</div>
						<div class="form-group">
							<label>项目名称</label>
							<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="A" placeholder="项目名称">
						</div>
						<div class="form-group">
							<label>是否上年度重新论证项目</label>
							<select style="width:70px;" id="txt_zffs" class="form-control select2" name="zffs">
									<option value="">未选择</option>
	                					<option value="1">是</option>
	                					<option value="2">否</option>
								</select>	
						</div>
						<div class="form-group">
							<label>申报单位</label>
							<input type="text" id="txt_bxrq" class="form-control" name="bxrq" table="A" placeholder="申报单位" value="" >
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
	               <button type="button" class="btn btn-default" id="btn_sh">审核</button>
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
				            <th>审核状态</th>
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>项目排名</th>
				            <th>分类名称</th>
				            <th>服务专业</th>
				            <th>服务学科</th>
				            <th>预算金额(元)</th>
				            <th>是否上年度重新论证项目</th>
				            <th>申请单位</th>
				            <th>申报人</th>
				            <th>申报日期</th>
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
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "SHZT",defaultContent:"待提交"},
       {"data": "XMBH",defaultContent:"0001"},
       {"data": "XMMC",defaultContent:"建设项目"},
       {"data": "XMPM",defaultContent:"1","class":"text-right"},
       {"data": "FLMC",defaultContent:"建筑类"},
       {"data": "FWZY",defaultContent:"建筑"},
       {"data": "FWXK",defaultContent:"建筑学"},
       {"data": "YSJE",defaultContent:"1000.25","class":"text-right"},
       {"data": "SFSNDCXLZXM",defaultContent:"否"},
       {"data": "SQDW",defaultContent:"山东国子软件"},
       {"data": "SBR",defaultContent:"超级管理员",},
       {"data": "SBRQ",defaultContent:"2017-10-19","class":"text-center"},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
      },orderable:false,"width":220,"class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xmsb/xmsb/getPageList",[2,'asc'],columns,0,1,setGroup);
	
   	$(".btn_look").click(function(){
   		location.href="${ctx}/webView/xmgl/xmsb/xmsb_view1.jsp";
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功");
   	});
	//查询
	$("#btn_search").click(function(){
		window.location.reload();
	});
	//审核
	$("#btn_sh").click(function(){
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length!=1){
   	   		alert("只能选择一条信息进行操作！");
   	   		return;
   		}else{
   			location.href="${ctx}/xmsb/xmsb/operate?operate=F";
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