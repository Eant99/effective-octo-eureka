<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>其他经费报销</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<div class="search-simple">
					<div class="form-group">
						<label>审批状态</label> <select id="drp_spzt" class="form-control select2"
							name="spzt" table="K">
							<option value="">全部</option>
							<option value="1">待审核</option>
							<option value="2">通过</option>
							<option value="2">退回</option>						
						</select>
					</div>
					<div class="form-group">
						<label>单据编号</label> <input type="text" id="txt_djbh"
							class="form-control" name="djbh" table="K" placeholder="请输入单据编号">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
					<div id="btn_search_more">
						<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
						<div class="search-more">
							<div class="form-group">
								<label>审批状态</label> <select id="drp_spzt" class="form-control select2"
									name="spzt" table="K">
								<option value="">全部</option>
								<option value="1">待审核</option>
								<option value="2">通过</option>
								<option value="2">退回</option>	
									</select>
							</div>
							<div class="form-group">
								<label>单据编号</label> <input type="text" id="txt_djbh"
									class="form-control input-radius" table="K" name="djbh"
									placeholder="请输入单据编号">
							</div>
							<div class="form-group">
								<label>经费代码</label> <input type="text" id="txt_ysdw"
									class="form-control input-radius" table="K" name="ysdw"
									placeholder="请输入经费代码">
							</div>
							<div class="form-group">
								<label>经费名称</label> <input type="text" id="txt_jfmc"
									class="form-control input-radius" table="K" name="jfmc"
									placeholder="请输入经费名称">
							</div>
							<div class="form-group">
								<label>报销日期</label> 
							<input type="text" id="txt_bxrq" class="form-control date" name="bxrq" value="<fmt:formatDate value="${gwjdfsq.JDRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="报销日期">
							</div>
                             
							<div class="form-group">
								<label>报销人</label> <input type="text" id="txt_bxr"
									class="form-control input-radius" table="K" name="bxr"
									placeholder="请输入报销人">
							</div>
							<div class="form-group">
								<label>报销部门</label> <input type="text" id="txt_bxbm"
									class="form-control input-radius" table="K" name="bxbm"
									placeholder="请输入报销部门">
							</div>
							<div class="form-group">
								<label>申请单据编号</label> <input type="text" id="txt_sqdjbh"
									class="form-control input-radius" table="K" name="sqdjbh"
									placeholder="请输入申请单据编号">
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
								<th>审批状态</th>
								<th>单据编号</th>
								<th>经费代码</th>
								<th>经费名称</th>
								<th>报销人</th>
								<th>报销部门</th>
								<th>报销事由</th>
								<th>附件张数（张）</th>
								<th>预算年度</th>
								<th>申请单据编号</th>
								<th>报销金额（元）</th>
								<th>报销日期</th>
								<th>备注</th>
								<th>审核意见</th>
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
       {"data": "SPZT",defaultContent:"","class":"text-center"},
       {"data": "DJBH",defaultContent:""},
       {"data": "JFDM",defaultContent:""},
       {"data": "JFMC",defaultContent:""},
       {"data": "BXR",defaultContent:""},
       {"data": "BXBM",defaultContent:""},
       {"data": "BXSY",defaultContent:"","class":"text-right"},
       {"data": "FJZS",defaultContent:"","class":"text-right"},
       {"data": "YSND",defaultContent:"","class":"text-right"},
       {"data": "SQDJBH",defaultContent:""},
       {"data": "BXJE",defaultContent:"","class":"text-right"},   
       {"data": "BXRQ",defaultContent:"","class":"text-right","class":"text-center"},  
       {"data": "BZ",defaultContent:""},   
       {"data": "SHYJ",defaultContent:""},   
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
      },orderable:false,"width":220}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/wsbx/cxtj/getPageList?table=CW_QTJFBX",[2,'asc'],columns,0,1,setGroup);
  //查看
   	$(".btn_look").click(function(){
   		location.href="${ctx}/wsbx/cxtj/operate?operate=Q";
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功！");
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