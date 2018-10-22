<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>固定资产管理系统维修申请信息</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
</head>
<style type="text/css">
#mydataTables_filter input[type=search]{
	display:inline-block;
	width:auto;
}
body{
	overflow-x:hidden;
}
.go-back{
	display:none;
}
</style>
<body class="contrast-red">
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
                <div class='responsive-table'>
                    <div class='scrollable-area'>
                    <table id="mydataTables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					      <thead>
					      <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>申请单号</th>
					          <th>维修商</th>
					          <th>申请时间</th>
					      </tr>    
					      </thead>
					      <tbody>
					      </tbody>
					</table>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
</div>

<script>
$(function (){
    table = $('#mydataTables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/window/wxsq?dwbh={param.dwbh}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "REPORTID",orderable:false, 'render': function (data, type, full, meta){
		             return "<input type='checkbox' class='keyId' name='wxsq' value='"+data+"' wxsbh='" + full.WXSBH + "' wxsmc='" + full.WXSMC + "' sqrbh='" + full.REPLYPERSON + "' sqr='" + full.REPLYPERSONMC + "' sqdwbh='" + full.REPLYCOMPANY + "' sqdw='" + full.REPLYCOMPANYMC + "' wxsdh='" + (!full.PHONE?"":full.PHONE) + "' wxsdz='" + (!full.ADDRESS?"":full.ADDRESS) + "' yjfy='" + full.ABOUTMONEY + "'>";
		    },"width":10,'searchable': false},
            {"data": "REPORTID",defaultContent:""},
            {"data": "WXSMC",defaultContent:""},
            {"data": "REPLYTIME",defaultContent:""}
        ],
        "language":{
            "search":"搜索：",
            "searchPlaceholder":"请输入维修商关键字"
        },
        "dom":"<'row'<'col-md-4 col-sm-4 col-xs-4'i><'col-md-8 col-sm-8 col-xs-8'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
    //单击事件
    $(document).on("dblclick","#mydataTables tr:not(#header)",function(){
    	var val = $(this).find("[name='wxsq']").val();
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
        	
        	getIframeControl("${param.pname}","txt_repaircompany").val($(this).find("[name='wxsq']").attr("sqdw"));
        	getIframeControl("${param.pname}","txt_wxcj").val($(this).find("[name='wxsq']").attr("wxsmc"));
        	getIframeControl("${param.pname}","hid_wxcjbh").val($(this).find("[name='wxsq']").attr("wxsbh"));
        	getIframeControl("${param.pname}","txt_repaircontact").val($(this).find("[name='wxsq']").attr("wxsdh"));
        	getIframeControl("${param.pname}","txt_repairaddress").val($(this).find("[name='wxsq']").attr("wxsdz"));
//         	getIframeControl("${param.pname}","txt_repairmoney").val($(this).find("[name='wxsq']").attr("yjfy"));
        	
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("keydown");//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("keyup");//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	
        	var winId = top.layer.getFrameIndex(parent.window.name);
        	close(winId);
    	}
    });
});
</script>
</body>
</html>