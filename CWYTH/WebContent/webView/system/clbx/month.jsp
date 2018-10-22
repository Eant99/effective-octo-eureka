<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>个人经费设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
		          	<div class="btn-group pull-right" role="group">
		          	<button type="button" class="btn btn-primary" id="btn_add">确定</button>
				</div>
				<div class="alert alert-info" style="height:29px;">
		          	<strong>提示：</strong>先找到需要的项目，然后<strong>双击</strong>这条信息或点击<strong>确定</strong>按钮添加这条信息（可多选）。
		        </div>
		        <hr class="hr-normal" id="hr">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>月份</th>
				        </tr>
					</thead>
				    <tbody>
				    	<tr><td><input type="checkbox" value="1" name="month" class="keyId"></td><td>1</td><td>一月</td></tr>
				    	<tr><td><input type="checkbox" value="2" name="month" class="keyId"></td><td>2</td><td>二月</td></tr>
				    	<tr><td><input type="checkbox" value="3" name="month" class="keyId"></td><td>3</td><td>三月</td></tr>
				    	<tr><td><input type="checkbox" value="4" name="month" class="keyId"></td><td>4</td><td>四月</td></tr>
				    	<tr><td><input type="checkbox" value="5" name="month" class="keyId"></td><td>5</td><td>五月</td></tr>
				    	<tr><td><input type="checkbox" value="6" name="month" class="keyId"></td><td>6</td><td>六月</td></tr>
				    	<tr><td><input type="checkbox" value="7" name="month" class="keyId"></td><td>7</td><td>七月</td></tr>
				    	<tr><td><input type="checkbox" value="8" name="month" class="keyId"></td><td>8</td><td>八月</td></tr>
				    	<tr><td><input type="checkbox" value="9" name="month" class="keyId"></td><td>9</td><td>九月</td></tr>
				    	<tr><td><input type="checkbox" value="10" name="month" class="keyId"></td><td>10</td><td>十月</td></tr>
				    	<tr><td><input type="checkbox" value="11" name="month" class="keyId"></td><td>11</td><td>十一月</td></tr>
				    	<tr><td><input type="checkbox" value="12" name="month" class="keyId"></td><td>12</td><td>十二月</td></tr>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	//点击确定，可多选
		$(document).on("click","#btn_add",function(){
// 	   		var $arr_tr = $("#mydatatables tr").has(".keyId:checked");
	   		var $arr_tr = $("#mydatatables tr").find("[name='month']").filter(":checked");
	   		var month=[];
	   		$arr_tr.each(function(){
	   			var aa = $(this).val();
	   			month.push($(this).val());
	   		});
	   		console.log("============="+month);
	   		if($arr_tr.length>0){
// 	   			getIframWindow("${param.pname}").addXmxx($arr_tr);
				getIframeControl("${param.pname}","${param.controlId}").val(month);
	   			var winId = getTopFrame().layer.getFrameIndex(window.name);
	   			close(winId);
	   		}else{
	   			alert("请选择至少一条信息！");
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