<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>差旅报销列表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<select style="width:70px;" class="form-control select2">
						<option value="">未选择</option>
	                	<c:forEach var="shztList" items="${shztList}">
		               		<option value="${shztList.DM}">${shztList.MC}</option>
		            	</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>报销人员</label>
					<input type="text" id="txt_bxr" class="form-control" name="bxr" table="A" placeholder="请输入人员工号">
				</div>
				<div class="form-group">
					<label>部门名称</label>
					<input type="text" id="txt_bmmc" class="form-control" name="bmmc"  table="A" placeholder="请输入部门名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>状&emsp;&emsp;态</label>
							<div class="input-group">
								<select style="width:70px;" class="form-control select2">
									<option value="">未选择</option>
	                				<c:forEach var="shztList" items="${shztList}">
		               					<option value="${shztList.DM}">${shztList.MC}</option>
		            				</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label>报销人员</label>
							<input type="text" id="txt_bxr" class="form-control" name="bxr" table="A" placeholder="请输入人员工号">
						</div>
						<div class="form-group"> 
							<label>部门名称</label>
							<div class="input-group">
								<input type="text" id="txt_bmmc" class="form-control input-radius" name="bmmc"  table="A" placeholder="请输入部门名称">
							</div>
						</div>
						<div class="form-group">
							<label>项目名称</label>
							<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="A" placeholder="请输入项目名称">
						</div>
						<div class="form-group">
							<label>申请日期</label>
							<input type="text" id="txt_sqrq" class="form-control" name="sqrq" table="A" placeholder="申请时间" 
							value=""  data-format="yyyy-MM-dd">
						</div>
						<div class="form-group">
							<label>报销日期</label>
							<input type="text" id="txt_bxrq" class="form-control" name="bxrq" table="A" placeholder="报销时间"
							value=""  data-format="yyyy-MM-dd">
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
				            <th>审核状态</th>
				            <th>部门名称</th>
				            <th>报销人员</th>
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>票据张数（张）</th>
				            <th>报销金额（元）</th>
				            <th>申请日期</th>
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
         return '<input type="checkbox" name="guid" shzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "SHZT",defaultContent:"","class":"text-center"},
       {"data": "BMMC",defaultContent:""},
       {"data": "BXRY",defaultContent:""},
       {"data": "XMBH",defaultContent:""},
       {"data": "XMMC",defaultContent:""},
       {"data": "PJZS",defaultContent:"0","class":"text-right"},
       {"data": "LKJE",defaultContent:"0.00","class":"text-right"},
       {"data": "SQRQ",defaultContent:"","class":"text-center"},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
      },orderable:false,"width":220,"class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/json/wsbx/ccbx/ccbxList.json",[2,'asc'],columns,0,1,setGroup);
	
  //添加按钮
   	$(".btn_look").click(function(){
   		location.href="${ctx}/wsbx/cxtj/operate?operate=C";
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/wsbx/ccbx/expExcelByCc?treedwbh=${dwbh}","出差报销","${ctx}",guid.join(","));
   	});
	//查询
	$("#btn_search").click(function(){
		window.location.reload();
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