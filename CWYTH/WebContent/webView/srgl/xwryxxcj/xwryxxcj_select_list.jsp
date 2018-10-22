<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>校外人员信息采集</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">		
		<form id="myform" class="form-inline" action="" style="padding-top: 10px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>人员编号</label>
					<input type="text" id="txt_xh" class="form-control" name="xh" table="K" placeholder="请输入人员编号">
				</div>
				<div class="form-group">
					<label>姓名</label>
					<input type="text" id="txt_xm" class="form-control" name="xm"  table="K" placeholder="请输入人员姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<button type="button" class="btn btn-primary" id="btn_cancel"><i class="fa icon-chaxun"></i>取消</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
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
				            <th>姓名</th>
				            <th>性别</th>
				            <th>出生日期</th>
				            <th>身份证件类型码</th>
				            <th>身份证件号</th>				         
				            <th>银行名称</th>
				            <th>开户行账号</th>
				            <th>联行号</th>
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
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" sfzh="'+full.SFZH+'" xm="'+full.XM+'" xh="'+full.XH+'" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "XH",defaultContent:"","width":30},
       {"data": "XM",defaultContent:"","width":30},
       {"data": "XBM",defaultContent:"","width":30},
       {"data": "CSRQ",defaultContent:"","width":100},
       {"data": "SFZJLXM",defaultContent:"","width":30},
       {"data": "SFZH",defaultContent:"","width":30},
       {"data": "YHMC",defaultContent:"","width":30},
       {"data": "KHYHH",defaultContent:"","width":30},
       {"data": "LHH",defaultContent:"","width":30},
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xwryxxcj/getPageList",[1,'asc'],columns,0,1,setGroup);
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
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
   		doOperate("${ctx}/xwryxxcj/goXwryxxcjEditPage?operateType=C&bz=xzsblr");//页面跳转时添加标志
   	});
});	
</script>
</body>
</html>