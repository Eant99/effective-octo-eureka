<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>差旅报销报销列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<%--表头样式--%>
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
/*     	border-bottom-width: 0px; */
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}
	.search-simple .select2-container--default .select2-selection--single {
   		background-color: #fff;
   		border: 1px solid #ccc;
    	border-radius: 4px 4px 4px 4px;
	}
/* 	.dataTables_scrollHeadInner{ */
/*  		width:1000px ! important; */
/* 	}  */
/*  	table.dataTable{  */
/*  		width:1114px ! important; */
/*  	}  */
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
				<label>审核状态</label>
					<select style="width:150px;" id="status" class="form-control">
	                	<option value="0" <c:if test="${param.status=='0'}">selected</c:if>>未审核</option>
	               		<option value="1" <c:if test="${param.status=='1'}">selected</c:if>>已审核</option>
					</select>
				</div>
				<div class="form-group">
					<label>报&ensp;销&ensp;人</label>
					<input type="text" id="txt_bxr" class="form-control" name="BXR" table="B" placeholder="请输入报销人姓名">
				</div>
				<div class="form-group">
					<label>部门名称</label>
					<input type="text" id="txt_bmmc" class="form-control" name="SZBM"  table="B" placeholder="请输入部门名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default " id="btn_tg_sh">批量通过</button>
	               <button type="button" class="btn btn-default " id="btn_th_sh">批量退回</button>
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
				            <th>
				            	<input type="checkbox" class="select-all"/>
				            	<input type="hidden" name="status" value="${param.status}"/>
				            </th>
				            <th>审核状态</th>
				            <th>单据编号</th>
				            <th>报销人</th>
				            <th>所在部门</th>
<!-- 				            <th>经费类型</th> -->
				           <!--  <th>项目名称</th> -->
				            <th>出差类型</th>
				            <th>出差天数(天)</th>
				            <th>出差人数(人)</th>
				            <th>报销日期</th>
				            <th>报销总金额(元)</th>
				            <th>是否事前审批</th>
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
<%@include file="/static/include/public-list-js.inc"%>
<script src="${ctx}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
$(function () {
	var shzt = $("[name=status]").val();	
	if(shzt=="0"){
		$(".no").css("display","");
	}else{
		$(".no").css("display","none");
	}
	//审核状态改变
  	$("#status").change(function(){
  		shzt=$("[id='status']").val();
  		table.ajax.url("${ctx}/wsbx/ccbx/getShPageList?status="+shzt);
  	    table.ajax.reload();
  	});
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="hidden" class="procinstid" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"><input type="checkbox" name="guid" procinstid="'+full.PROCINSTID+'" shzt="'+full.SHZTDM+'" class="keyId" ccywguid="'+full.CCYWGUID+'" xmguid="'+full.XMGUID+'" djbh="'+full.DJBH+'" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "SHZT",defaultContent:"","class":"text-center","width":40},
       {"data": "DJBH",defaultContent:"","width":150},
       {"data": "BXR",defaultContent:"","width":120},
       {"data": "SZBM",defaultContent:"","width":200},
       {"data": "CCLX",defaultContent:""},
       {"data": "CCTS",defaultContent:"","class":"text-right"},
       {"data": "CCRS",defaultContent:"","class":"text-right"},
       {"data": "CZRQ",defaultContent:"","class":"text-center"},
       {"data": "BXZJE",defaultContent:"0.00","class":"text-right","width":100},
       {"data": "SFSQSP",defaultContent:"","class":"text-center"},
       {"data": "GUID",'render':function(data, type, full, meta){
    	   if(full.FLAG!="Y"){
  				return '<a href="javascript:void(0);" class="btn btn-link btn_check an" guid = "'+full.GUID+'" procinstid="'+full.PROCINSTID+'" ccywguid="'+full.CCYWGUID+'"  shzt="'+full.SHZTDM+'" >审核</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+full.PROCINSTID+'">办理记录</a>';
  			}else{
  				return '<a href="javascript:void(0);" class="btn btn-link btn_look " guid = "'+full.GUID+'"  ccywguid="'+full.CCYWGUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+full.PROCINSTID+'">办理记录</a>';
  			}
    },orderable:false,"width":140,"class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/wsbx/ccbx/getShPageList?mkbh=${mkbh}&type=sh&status="+$("#status").val(),[6,'desc'],columns,0,1,setGroup);

    //查看按钮
 	$(document).on("click",".btn_look",function(){
   		//var ccywguid = $(this).parents("tr").find("[name='guid']").attr("ccywguid");
   		//var guid = $(this).parents("tr").find("[name='guid']").val();
   		var ccywguid = $(this).attr("ccywguid");
   		var guid = $(this).attr("guid");
   		
   		location.href = "${ctx}/wsbx/ccbx/goViewJsp?guid="+guid+"&type=v&ccywguid="+ccywguid;
   	});
 	//审核按钮  	
	$(document).on("click",".btn_check",function(){
		var status = $("#status").find("option:selected").val();
		//var ccywguid = $(this).parents("tr").find("[name='guid']").attr("ccywguid");
   		//var guid = $(this).parents("tr").find("[name='guid']").val();
		//var procinstid = $(this).parents("tr").find("[name='procinstid']").val();
		var ccywguid = $(this).attr("ccywguid");
		var guid = $(this).attr("guid");
		var procinstid = $(this).attr("procinstid");
		
		var src = "";
		if(status=="1"){
			alert("不可重复审核！");
			return;
		}
		
		//var shzt = $(this).parents("tr").find("[name='guid']").attr("shzt");
		var shzt =  $(this).attr("shzt");
		
		if(shzt== "01" || shzt== "04")  {//如果是报账员审核，跳转编辑页面
			src  = "${ctx}/wsbx/ccbx/goViewJsp2?guid="+guid+"&type=sh&procinstid="+procinstid+"&ccywguid="+ccywguid;
		}else{
   		   src = "${ctx}/wsbx/ccbx/goViewJsp?guid="+guid+"&type=sh&procinstid="+procinstid+"&ccywguid="+ccywguid;
		}
		location.href = src;
   	});
 	
  	//同意
  	$(document).on("click",".btn_tg",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		var procinstid = $(this).parents("tr").find("[name='procinstid']").val();
		alert(procinstid);
		$.ajax({
			type:"post",
			url:"${ctx}/wsbx/rcbx/doApprove",
			data:"&procinstid="+procinstid+"&pass=true&guid="+guid+"&shyj=",
			success:function(val){
				var data = JSON.getJson(val);
				if(data.success='true'){
					alert("提交成功！");
				}
				table.ajax.reload();
				var winId = getTopFrame().layer.getFrameIndex(window.name);
				close(winId);
			}
		});
   	});
  	//退回
  	 $(document).on("click",".btn_th",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		var procinstid = $(this).parents("tr").find("[name='procinstid']").val();
		$.ajax({
			type:"post",
			url:"${ctx}/wsbx/rcbx/doApprove",
			data:"&procinstid="+procinstid+"&pass=false&guid="+guid+"&shyj=",
			success:function(val){
				var data = JSON.getJson(val);
				if(data.success='true'){
					alert("退回成功！");
				}
				table.ajax.reload();
				var winId = getTopFrame().layer.getFrameIndex(window.name);
				close(winId);
			}
		});
   	});
	//办理记录
   	$(document).on("click",".btn_bljl",function(){
   		//var processInstanceId=$(this).parents("tr").find("[name='procinstid']").val();
   		var processInstanceId=$(this).attr("procinstid");
   		
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb","L");
   		
   	});
    //批量审核
    $(document).on("click","#btn_tg_sh",function(){
    	
		var status = $("#status").find("option:selected").val();
		if(status=="1"){
			alert("不可重复审核！");
			return;
		}
	
		var flag = false;
		var checkbox = $("#mydatatables").find("[name='guid']")
				.filter(":checked");
		if (checkbox.length > 0) {
			debugger
			var id = [];
			var procinstid = [];
			checkbox.each(function() {
				id.push($(this).val());
				procinstid.push($(this).attr("procinstid"));
				var money = $(this).parents("tr").find("td").eq(9).text();
				var shzt = $(this).parents("tr").find("[name='guid']").attr("shzt");
				if(money<=5000&&!flag&&(shzt==="01"||shzt==="04")){
					$.ajax({
						url:"${ctx}/wsbx/process/selectWho",
						data:"guid="+$(this).val()+"&type=clfbx",
						type:"post",
						async:false,
						dataType:"json",
						success:function(data){
							if(data){
								flag = true;
							}
						}
					});
				}
			});
			 select_commonWin("${ctx}/wsbx/ccbx/check1?type=first&guid="+id.join(",")+"&procinstid="+procinstid.join(",")+"&flag="+flag,"通过页面","500","300");
		} else {
			alert("请选择至少一条信息！");
		}
	});
	//批量退回
	$(document).on("click","#btn_th_sh",function(){
		var status = $("#status").find("option:selected").val();
		if(status=="1"){
			alert("不可重复审核！");
			return;
		}
		var checkbox = $("#mydatatables").find("[name='guid']")
				.filter(":checked");
		if (checkbox.length > 0) {
			var id = [];
			var procinstid = [];
			checkbox.each(function() {
				id.push($(this).val());
				procinstid.push($(this).attr("procinstid"));
			});
			 select_commonWin("${ctx}/wsbx/ccbx/check1?type=second&guid="+id.join(",")+"&procinstid="+procinstid.join(","),"退回页面","500","300");
		} else {
			alert("请选择至少一条信息！");
		}
	});
	//导出Excel
	$(document).on("click","#btn_exp",function(){
   		/* var json = searchJson("searchBox"); */
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		if(checkbox.length>0){
		checkbox.each(function(){
			guid.push($(this).val());
		});
		}else{
			guid="";
		}
		console.log(guid);
   		/* doExp(json,"${ctx}/wsbx/ccbx/expExcel","差旅费报销","${ctx}",guid); */
   		doExp("","${ctx}/wsbx/ccbx/expExcel?status="+$('#status').val()+"&guid="+guid,"差旅费报销","${pageContext.request.contextPath}");
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