<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>成本查询页面</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			   <div class="form-group">
					<label>成本中心</label>
					<select class="input_title  form-control" id="txt_zfzt" name="zfzt"  style="width:152px;"  table="">
						<option value="">-请选择-</option>
						<option value="00">电气专业</option>
						<option value="01">计算机专业</option>
						<option value="02">播音专业</option>
					</select>
				</div>
				<div class="form-group">
					<label>成本对象</label>
					<select class="input_title  form-control" id="txt_zfzt" name="zfzt"  style="width:152px;"  table="">
						<option value="">-请选择-</option>
						<option value="00">学生</option>
						<option value="01">教师</option>
						<option value="02">校长</option>
					</select>
				</div>
				<div class="form-group">
					<label>成本核算模型设置</label>
					<select class="input_title  form-control" id="txt_zfzt" name="zfzt"  style="width:152px;"  table="">
						<option value="">-请选择-</option>
						<option value="00">模型一</option>
						<option value="01">模型二</option>
						<option value="02">模型三</option>
					</select>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
                <button type="button" class="btn btn-primary" id="btn_cancel"><i class="fa icon-chaxun"></i>取消</button>
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
				            <th>成本中心</th>
				            <th>成本对象</th>
				            <th>成本核算模型设置</th>
				            <th>成本核算</th>	
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
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	var zfzt = $("[name='zfzt']").val();
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "RYGH",defaultContent:""},
       {"data": "RYXM",defaultContent:""},
       {"data": "SSDWMC",defaultContent:""},       
       {"data": "XFDDMC",defaultContent:""},
     ];
    	table = getDataTableByListHj("mydatatables","${ctx}/wxzf/getPageList",[2,'asc'],columns,0,1,setGroup);
//查询项
 	$("#txt_zfzt").change(function(){
 		$("#btn_search").click();
 	})
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