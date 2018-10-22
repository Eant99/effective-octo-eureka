<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>汇总分析</title>
<style type="text/css">
	.dataTables_length{
    	padding-top: 0px !important;
	}
	.dataTables_info{
    	padding-top: 4px !important;
	}
	.table>tbody>tr>td {
   	 padding: 0px 4px !important;
	}
 	.dataTables_scroll{
   		margin-right: 4px !important;
   	    margin-left: 4px !important;
    }
	.tool-fix{
		display: none;
	}
</style>
<%@include file="/static/include/public-list-css.inc"%>
<body>
<div class="fullscreen"  style="width: 100%">
	<form action="" method="post" id="Form1">
		<div id="centent_body">
			<div class="div_title" style="text-align: center;width: 100%;padding-top: 2px">
			    <label class="div_title" style="font-weight: bolder;font-size: 20px;margin-bottom: 0px;">资 产 信 息 汇 总 分 析</label>
			</div>
			<div class="btn-group pull-right" role="group">
				<button type="button" id="btn_exp"  class="btn btn-default" >导出Excel</button>
			</div>
			<div class="div_table">
				<div class="container-fluid">
        			<div class='responsive-table'>
            			<div class='scrollable-area'>
							${table}
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
</body>
</html>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/layer/layer.js"></script>
<script type="text/javascript">
$(function(){
	$('#table').dataTable({
		"pagingType": "simple_numbers",
		"lengthMenu":[100],//显示记录数   i
		"order": [],//排序列
        "serverSide": false,
        "scrollX": true,
        "scrollY": true,
		"dom":"<'row fyrow'<'page-left wxts'li>><'row'<t>> <'row bottom'<'pull-right'p ><'total'o>>",
        "drawCallback":function(){
        	var heights = $(window).outerHeight()-$(".div_title").outerHeight()-$(".row.fyrow").outerHeight()-$(".row.bottom").outerHeight()-$(".dataTables_scrollHead").outerHeight();
        	$(".dataTables_scrollBody").height(heights);
        	$(".total").prop("innerHTML","资产数量：<font color='red'>" + '${zcsl}' + "</font>&emsp;单价：<font color='red'>" + '${dj}' + "</font>&emsp;总价：<font color='red'>" + '${zzj}' + "</font>");
        	
           	$(window).resize(function(){
           		$("div.dataTables_wrapper").width($("#searchBox").width());
                heights = $(window).outerHeight()-$(".div_title").outerHeight()-$(".row.fyrow").outerHeight()-$(".row.bottom").outerHeight()-$(".dataTables_scrollHead").outerHeight();
                $(".dataTables_scrollBody").height(heights); 
        	});
        }
	});
	//导出Excel
	$("#btn_exp").click(function(){
		var json = searchJson("searchBox");
		var yqbh = [];
	var checkbox = $("#mydatatables").find("[name='yqbh']").filter(":checked");
	checkbox.each(function(){
		yqbh.push($(this).attr("yqh"));
	});
    
	doExp(json,"${pageContext.request.contextPath}/select/expExcel2?mkmc=${param.mkmc}&lb=${param.lb}&where=${where}&jc=${param.jc}&tj=${param.tj}&txys=${param.txys}&text=${param.text}","资产信息汇总分析","${pageContext.request.contextPath}",yqbh.join(","));
	});
});
</script>