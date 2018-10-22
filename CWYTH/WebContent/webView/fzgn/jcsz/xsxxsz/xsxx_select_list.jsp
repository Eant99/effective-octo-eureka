<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学生信息维护</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>学&emsp;&emsp;号</label>
					<input type="text" id="txt_xh" class="form-control" name="xh" table="T" placeholder="请输入学生学号">
				</div>
				<div class="form-group">
					<label>姓&emsp;&emsp;名</label>
					<input type="text" id="txt_xm" class="form-control" name="xm"  table="T" placeholder="请输入学生姓名">
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
				            <th>学号</th>
				            <th>姓名</th>
				            <th>性别</th>
				            <th>出生日期</th>
				            <th>学生类别</th>
				            <th>所在院系</th>
				            <th>专业</th>
				            <th>年级</th>
				            <th>班级</th>
				            <th>民族</th>
				            <th>身份证件类型</th>
				            <th>证件号</th>
				            <th>政治面貌</th>
<!-- 				            <th>操作</th> -->
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
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" sfzh="'+full.SFZH+'" xm="'+full.XM+'" xh="'+full.XH+'" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "XH",defaultContent:"","class":"text-left"},
       {"data": "XM",defaultContent:""},
       {"data": "XB",defaultContent:"","class":"text-left"},
       {"data": "CSRQ",defaultContent:"","class":"text-center"},
       {"data": "XSLB",defaultContent:""},
       {"data": "SZYX",defaultContent:"","width":120},
       {"data": "SZZY",defaultContent:"","width":120,},
       {"data": "SZNJ",defaultContent:"","class":"text-left"},
       {"data": "SZBJ",defaultContent:"","width":120,},
       {"data": "MZ",defaultContent:""},
       {"data": "SFZJLX",defaultContent:""},
       {"data": "SFZH",defaultContent:"","class":"text-left"},
       {"data": "ZZMM",defaultContent:""},
//        {"data": "GUID",'render':function(data, type, full, meta){
// 	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" xh="'+full.XH+'" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" xh="'+full.XH+'" guid = "'+full.GUID+'">删除</a>';
//       },orderable:false,"width":220,"class":"text-center","width":100}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xsxx/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
    $(function () {
		$(document).on("dblclick","tbody tr",function(){
			var xh = $(this).find("[name='guid']").attr("xh");
			var xm = $(this).find("[name='guid']").attr("xm");
			var sfzh = $(this).find("[name='guid']").attr("sfzh");
			var guid = $(this).find("[name='guid']").val();
			var cdid="${param.controlId}";
			getIframeControl("${param.pname}","${param.controlId}").val(xh);
			getIframeControl("${param.pname}","${param.controlId2}").val(xm);
			getIframeControl("${param.pname}","${param.controlId3}").val(sfzh);
			getIframeControl("${param.pname}","${param.guid}").val(guid);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	getIframWindow("${param.pname}").cxyhk(guid,cdid);//
        	var winId = getTopFrame().layer.getFrameIndex(window.name);
        	close(winId);
	    });
    });
});
</script>
</body>
</html>