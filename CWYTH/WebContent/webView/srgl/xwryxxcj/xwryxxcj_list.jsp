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
	               <c:if test="${ymbz != 'xzry'}">
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <!--  
	               <button type="button" class="btn btn-default" id="btn_xz">下载模板</button>
 	               <button type="button" class="btn btn-default" id="btn_dr">导入</button> 
 	               -->
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
	               </c:if>
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
				            <th style="text-align:center;">操作</th>
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
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "XH",defaultContent:"","width":30},
       {"data": "XM",defaultContent:"","width":30},
       {"data": "XBM",defaultContent:"","width":30},
       {"data": "CSRQ",defaultContent:"","class":"text-center","width":100},
       {"data": "SFZJLXM",defaultContent:"","width":30},
       {"data": "SFZH",defaultContent:"","width":30},
       {"data": "YHMC",defaultContent:"","width":30},
       {"data": "KHYHH",defaultContent:"","width":30},
       {"data": "LHH",defaultContent:"","width":30},
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":80}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xwryxxcj/getPageList",[1,'asc'],columns,0,1,setGroup);
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
   		doOperate("${ctx}/xwryxxcj/goXwryxxcjEditPage?operateType=C");
   	});
	//导出Excel
		$(document).on("click","#btn_exp",function(){
		var id = [];
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/xwryxxcj/expExcel2","校外人员信息","${pageContext.request.contextPath}",id);
   		
	});
	
   	//编辑按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");
   		doOperate("${ctx}/xwryxxcj/goXwryxxcjEditPage1?guid="+guid+"&operateType=U");
   	});
   	//查看按钮
   	$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
   		doOperate("${ctx}/xwryxxcj/goLookPage?guid="+guid,"L");
   	});
    //下载导入模版按钮
   	$("#btn_xz").click(function(){
   		var checkbox = $("#").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		guid.push($(this).val());
   	   		});
	   		doDel("guid="+guid.join(","),"${ctx}/jsxxs/doDelete",function(val){
	   			table.ajax.reload();
	   		},function(val){
	   			
	   		},guid.length);
   		}else{
   			alert("下载导入模版！");
   		}
   	});
    //批量删除
    $(document).on("click","#btn_del",function(){
     		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
     		var guid = [];
     			 if(checkbox.length>0){
     	   			var guid = [];
     	   			checkbox.each(function(){
     	   				guid.push($(this).val());
     	   			});
     	   			doDel("guid="+guid.join("','"),"${ctx}/xwryxxcj/doDelete2",function(val){
     	   			window.location.href = "${ctx}/webView/srgl/xwryxxcj/xwryxxcj_list.jsp";
     	   	   		},function(val){
     	   	   		},guid.length); 
     	   		}else{
     	   			alert("请选择至少一条信息删除！");
     	   		} 
     	});
    //单条删除
 	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).attr("guid");
 		doDel("guid="+guid,"${ctx}/xwryxxcj/doDelete",function(val){
   			table.ajax.reload();
   			//....
   		},function(val){
   			//....
   		},"1");
   	});
	
	//弹窗
	$("#btn_dwbh").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwbh","单位信息","920","630");
    });
	$("#btn_rybh").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_rybh","人员信息","920","630");
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