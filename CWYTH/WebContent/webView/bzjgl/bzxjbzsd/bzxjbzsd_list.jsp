<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>补助学金等级设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<input type="hidden" name="qc" value="${qc}">
	<input type="hidden" name="bmh" value="${bmh}">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>补助学金等级</label>
					<input type="text" id="txt_bzmc" class="form-control input-radius" name="bzmc" table="K" placeholder="请输入补助学金等级">
				</div>
				<div class="form-group">
					<label>补助学金金额</label>
					<input type="text" id="txt_bzje" class="form-control input-radius" name="bzje" table="K" placeholder="请输入补助学金金额">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_add">增加</button>
<!-- 	                <button type="button" class="btn btn-default" id="btn_imp">批量导入</button> -->
	                <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
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
				        <th>序号</th>
				        <th>标准编号</th>
				        <th>补助学金等级</th>
				        <th>补助金额</th>
				        <th>设置时间</th>
				        <th>是否启用</th>
				        <th>备注</th>
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
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var columns = [
	       		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
       				return '<input type="checkbox" class="keyId" name="keyId" guid = "'+full.GUID+'" value="' + data + '">';
	       	    },"width":10,'searchable': false},//0
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},//1
	       		{"data": "BZBH","render":function (data, type, full, meta){
	       			return '<div class="text-left"><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
			    },"width":100},
	       		{"data": "BZMC",defaultContent:"","class":"text-left"},
	       	   	{"data": "BZJE",defaultContent:"","class":"text-right"},
	       	 	{"data": "SZSJ",defaultContent:"","class":"text-center"},
	       		{"data": "SFQY",defaultContent:"","class":"text-left"},
	       		{"data": "BZ",defaultContent:""},
	       	   	{"data": "GUID",'render':function (data, type, full, meta){
	       	   		if(full.SFFH=='1'){
	       	   			return '';
	       	   		}else{
	       	   		return '<a class="btn btn-link btn_upd" dwbh = "'+full.GUID+'">编辑</a>|<a  class="btn btn-link btn_delxx" dwbh = "'+full.GUID+'">删除</a>';//btn_delxx	
	       	   		}
          			
	       		},orderable:false,"width":95}
	       	];
	       	//表数据
          	table = getDataTableByListHj("mydatatables","${ctx}/bzxjbzsd/getPageList?treedwbh=${dwbh}&qc=${qc}&bmh=${bmh}",[4,'asc'],columns);
   	//新增
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/bzxjbzsd/goEditPage","C");//operateType=C?dwbh=${param.dwbh}&qc=${qc}&bmh=${bmh}
   	});
   	
   	//编辑
   	$(document).on("click",".btn_upd",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/bzxjbzsd/goEditPage?dwbh="+dwbh,"U");
	});
   	//复核
  	$(document).on("click",".btn_fhxx",function(){
   		var dwbh = $(this).attr("dwbh");
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
   		var bzbh = $(this).attr("dwbh");
   		doDel("bzbh="+bzbh,"${ctx}/bzxjbzsd/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
	//批量删除
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
 		if(checkbox.length>0){
 	   		var bzbh = [];
 	   		checkbox.each(function(){
 	   			bzbh.push($(this).val());
 	   		});
 	   		doDel("bzbh="+bzbh.join("','"),"${ctx}/bzxjbzsd/doDelete2",function(val){
//    	   			parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
					location.reload();
   	   	   	},function(val){
   	   	   	},bzbh.length);
   	   	}else{
   	   		alert("请选择至少一条信息删除！");
   	   	}
   	});
	//查看按钮
	$(document).on("click",".btn_look",function(){
   		var dwbh = $(this).attr("guid");
   		doOperate("${ctx}/bzxjbzsd/goEditPage?dwbh="+dwbh,"L");
//    		window.location.href = "${ctx}/bzxsbzsd/goEditPage?dwbh="+dwbh+"&operateType=L";	
   	});
  	//数据导入
	$("#btn_imp").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/jcsz/jsxxsz/txl_imp3.jsp", "导入经费信息", 450,350);
		return false;
	});
   	//导出Excel
   	$("#btn_exp").click(function(){
   		var id = [];
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp(json,"${ctx}/bzxjbzsd/expExcel","补助学金标准设定信息","${pageContext.request.contextPath}",id);
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

