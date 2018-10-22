<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>多选代码表信息(账表中用到了)</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.tool-fix{
		display:none;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
			    <table id="mydataTables" class="table table-striped table-bordered">
				      <thead>
					      <tr>
					          <th><input type="checkbox" class="select-all"/></th>
					          <th>序号</th>
					          <th>代码</th>
					          <th>名称</th>
					      </tr>
				      </thead>
				      <tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script type="text/javascript">
$(function (){
	//列表数据
    var columns = [
		{"data": "DMMC",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" value="' + data + '">';
	    },"width":30,'searchable': false,"class":"text-center"},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
		{"data": "DM",defaultContent:""},
	   	{"data": "MC",defaultContent:""}
	];
   	table = getDataTableByListHj("mydataTables","${pageContext.request.contextPath}/window/multiDmbList?zl=${param.zl}",[2,'asc'],columns,0,0);
	
   	$("div.total").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:2px;'>确认选择</button><button type='button' id='btn_cancel' class='btn btn-default'>取消</button>");

	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//取消
   	$("#btn_cancel").on("click",function(){
   		close(winId);
   	});

  	//确定按钮
   	$("#btn_sure").on("click",function(){
   		var checkbox = $("#mydataTables").find(".keyId").filter(":checked");
   		if(checkbox.length == 0){
   			alert("请先选择信息！");
   	   		return false;
   		}
   		else{
	   		var selDmS = [];
	   		checkbox.each(function(){
	   			selDmS.push($(this).val());
	   		});
	   		getIframeControl("${param.pname}","${param.controlId}").val(selDmS.join(";"));
	    	close(winId);
   		}
   	});
});
</script>
</body>
</html>