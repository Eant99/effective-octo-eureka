<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位机构设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<input type="hidden" name="qc" value="${qc}">
	<input type="hidden" name="bmh" value="${bmh}">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_save">保存</button>
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
				        <th style="text-align: center;">列</th>
				        <th style="text-align: center;">是否显示</th>
				    </tr>
				    </thead>
				    <tbody>
				    	<th style="text-align: center;">审核状态</th>
				    	<th>列</th>
				    </tbody>
				</table>
            </div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
   	//新增
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/grjfsz/goEditPage?dwbh=${param.dwbh}&qc=${qc}&bmh=${bmh}","C");//operateType=C
   	});
   	
   	//编辑
   	$(document).on("click",".btn_upd",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/grjfsz/goEditPage?dwbh="+dwbh,"U");
	});
   	//复核
  	$(document).on("click",".btn_fhxx",function(){
   		var dwbh = $(this).attr("dwbh");
//    		if(confirm("是否确定复核？")==true){
// // 			doOperate("${ctx}/grjfsz/goFhPage?dwbh="+dwbh,"U");
//    			alert("复核成功！");
//    		}
   		confirm("确定要复核？",{title:"提示"},function(){
   			doOperate("${ctx}/grjfsz/goFhPage?dwbh="+dwbh,"U");
			parent.location.reload();
   			alert("复核成功！");
   		});
	});
    //批量赋值
   	$("#btn_assig").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
   		if(djdw.length>0){
   			alert("包含最顶级单位，不允许进行批量赋值，请单独修改！");
   		}else{
   			if(checkbox.length>0){
   	   			var dwbh = [];
   	   			checkbox.each(function(){
   	   				dwbh.push($(this).val());
   	   			});
   	   			confirm("确认要批量赋值"+dwbh.length+"条信息？",{title:"提示"},function(index){
   	   				select_commonWin("${ctx}/dwb/goPlfuzhiPage?dwbh="+dwbh.join(","),"批量赋值","400","450");
   	   				close(index);
   	   			}); 
   	   		}else{
   	   			alert("请选择至少一条信息赋值！");
   	   		}
   		}
   	});
 	//删除
	$(document).on("click",".btn_delxx",function(){
   		var dwbh = $(this).attr("dwbh");
   		doDel("dwbh="+dwbh,"${ctx}/grjfsz/doDelete",function(val){
//    			location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
	//批量删除
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
   		if(djdw.length>0){
   			alert("包含最顶级单位，不允许删除！");
   		}else{
   			if(checkbox.length>0){
   	   			var dwbh = [];
   	   			checkbox.each(function(){
   	   				dwbh.push($(this).val());
   	   			});
   	   			doDel("dwbh="+dwbh.join("','"),"${ctx}/grjfsz/doDelete",function(val){
//    	   				parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
						location.reload();
   	   	   		},function(val){
   	   	   		},dwbh.length);
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		}
   		}
   		
   	});
	//查看按钮
   	$("#btn_look").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		if(checkbox.length==1){
   			window.location.href = "${ctx}/dwb/goEditPage?operateType=L&dwbh="+checkbox.val();
   		}else{
   			alert("只能选择一条信息查看！");
   		}
   	});
  	//数据导入
	$("#btn_imp").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/jcsz/jsxxsz/txl_imp3.jsp", "导入经费信息", 450,350);
		return false;
	});
   	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
   		doExp(json,"${ctx}/grjfsz/expExcel?treedwbh=${dwbh}&id="+id.join("','"),"单位信息","${pageContext.request.contextPath}",id.join(","));
   	});
   	
    //查询联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	//查询弹窗
   	$("#btn_dwld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息","920","630");
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
</html>

