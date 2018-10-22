<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>日常报销列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<%--表头样式--%>
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
    	border-bottom:1px solid #ddd;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
				<label>审核状态</label>
					<select style="width:150px;" class="form-control" id="status">
	               		<option value="0" <c:if test="${param.status=='0'}">selected</c:if>>未审核</option>
	               		<option value="1" <c:if test="${param.status=='1'}">selected</c:if>>已审核</option>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh" table="D" placeholder="" value="${param.djbh}">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
					<button type="button" class="btn btn-default no" id="btn_tg_sh">批量通过</button>
	               <button type="button" class="btn btn-default no" id="btn_th_sh">批量退回</button>
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
				            <th>审核状态</th>
				            <th>单据编号</th>
				            <th>报销人</th>
				            <th>所在部门</th>
				            <th>报销项目</th>
				            <th>报销日期</th>
				            <th>报销总金额(元)</th>
				            <th>报销事由</th>
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
<input type="hidden" name="dual" value="0" />
<%@include file="/static/include/public-list-js.inc"%>
<script src="${ctx}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
var suffix = "&mkbh=${param.mkbh}";
$(function () {
	var shzt = $("#status").val();
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
                   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
                	   return '<input type="checkbox" xmmc="'+full.BXXM+'" money="'+full.BXZJE+'" bxr="'+full.BXR+'" name="guid" shzt="'+full.SHZTDM+'" procinstid="'+full.PROCINSTID+'" class="keyId" xmguid="'+full.XMGUID+'" djbh="'+full.DJBH+'" value="' + data + '" guid = "'+full.GUID+'">';
                     },"width":10,'searchable': false},
                     {"data": "SHZT",defaultContent:"","class":"text-center","width":40},
                     {"data": "DJBH",defaultContent:"","width":150},
                     {"data": "BXR",defaultContent:"","width":150},
                     {"data": "SZBM",defaultContent:"","width":150},
                     {"data": "BXXM",defaultContent:"","width":150},
                     {"data": "CZRQ",defaultContent:"","class":"text-center"},
                     {"data": "BXZJE",defaultContent:"0.00","class":"text-right","width":100},
                     {"data": "BXSY",defaultContent:"",},
       				{"data": "GUID",'render':function(data, type, full, meta){
    	   			if(shzt=="0"){
    	   				return '<a href="javascript:void(0);" class="btn btn-link btn_check an" djbh="'+full.DJBH+'" guid = "'+full.GUID+'" procinstid="'+full.PROCINSTID+'"  shzt="'+full.SHZTDM+'" xmmc="'+full.BXXM+'" money="'+full.BXZJE+'" bxr="'+full.BXR+'">审核</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+full.PROCINSTID+'" >办理记录</a>|<a href="javascript:void(0);" class="btn btn-link btn_view an" procinstid="'+full.PROCINSTID+'" guid = "'+full.GUID+'" >详情</a>';
    	   			}else{
    	   				return '<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+full.PROCINSTID+'" >办理记录</a>|<a href="javascript:void(0);" class="btn btn-link btn_view an" procinstid="'+full.PROCINSTID+'" guid = "'+full.GUID+'">详情</a>';
    	   			}
	   		
     				},orderable:false,"width":140}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/wsbx/rcbx/getRcbxDshPageList?treedwbh=${dwbh}&status=${param.status}",[2,'desc'],columns,0,1,setGroup);
	//单条数据审核审核
   	$(document).on("click",".btn_check",function(){
		var status = $("#status").find("option:selected").val();
		var shzt =  $(this).attr("shzt");
		var guid =  $(this).attr("guid");
		if(status=="1"){
			alert("不可重复审核！");
			return;
		}
		var procinstid = $(this).attr("procinstid");
		var src = "";
		var xmmc =  $(this).attr("xmmc");
		var money =  $(this).attr("money");
	 	if(shzt=="01"||shzt=="04"){//如果是报账员审核，跳转编辑页面
			var djbh = $(this).attr("djbh");
	 		
			var xmguid = "";
			var bxr = $(this).attr("bxr");
			$.ajax({//查询项目id
				type:"post",
				url:"${ctx}/wsbx/rcbx/getXmxx",
				data:{"zbid":guid},
				dataType:"json",
				async:false,
				success:function(val){
					$.each(val,function(index, n){
						xmguid = xmguid + n.XMGUID + ",";
					 });
					xmguid = xmguid.substring(0,xmguid.length-1);
					src += "${ctx}/wsbx/rcbx/ywblBybzy?";
					src += "&zbid="+guid+"&xmmc="+xmmc+"&xmguid="+xmguid+"&djbh="+djbh+"&money="+money+"&bz=delete&flag=1&bxr="+bxr+"&guid="+guid+"&procinstid="+procinstid;
				}
			});
		}else if(shzt=='03'){//如果是财务预审，可以修改报销事由和费用名称
			src = "${ctx}/wsbx/rcbx/operate?operate=CWYSCK&guid="+guid+"&procinstid="+procinstid+"&fcwys=cwys&ymbz=sh";
		}else{
			src = "${ctx}/wsbx/rcbx/operate?operate=CK&guid="+guid+"&procinstid="+procinstid;
		}
		location.href = src;
   	});

    //办理记录
   	$(document).on("click",".btn_bljl",function(){
   		var processInstanceId=$(this).attr("procinstid");
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb"+suffix,"C");
   		
   	});
  	//批量审核
  	$(document).on("click","#btn_tg_sh",function(){
//  	$("#btn_tg_sh").click(
// 		function() {
			var status = $("#status").find("option:selected").val();
			var flag = false;
			var sbzy = false;
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
					var money = $(this).parents("tr").find("td").eq(7).text();
					var shzt = $(this).parents("tr").find("[name='guid']").attr("shzt");
					if(money<=Number("${bmje}")&&(shzt==="01"||shzt==="04")){
						sbzy = true;
					}
				/* 	if(money<=5000&&!flag&&(shzt==="01"||shzt==="04")){
						$.ajax({
							url:"${ctx}/wsbx/process/selectWho",
							data:"guid="+$(this).val()+"&type=rcbx",
							type:"post",
							dataType:"json",
							async:false,
							success:function(data){
								if(data){
									flag = true;
								}
							}
						});
					} */
				});
				$.ajax({
					type:"post",
					url:"${ctx}/wsbx/rcbx/checkSfxypl",
					data:"guid="+id.join("','"),
					success:function(val){
						if(val=="0"){//不是学院直接提交到(二级院长或行政负责人审核)
							flag = false;
							select_commonWin("${ctx}/wsbx/rcbx/check1?type=first&guid="+id.join(",")+"&procinstid="+procinstid.join(",")+"&flag="+flag,"通过页面","500","300");
						}else if(val=="1"){
							if(sbzy){
								flag=true;
							}						
							select_commonWin("${ctx}/wsbx/rcbx/check1?type=first&guid="+id.join(",")+"&procinstid="+procinstid.join(",")+"&flag="+flag,"通过页面","500","300");
						}else{
							alert("所选项中同时包含学院与非学院的科目请重新选择");
						}
					}
				})
// 				select_commonWin("${ctx}/wsbx/rcbx/check1?type=first&guid="+id.join(",")+"&procinstid="+procinstid.join(",")+"&flag="+flag,"通过页面","500","300");
			} else {
				alert("请选择至少一条信息！");
			}
		});
  	//批量退回
  	$(document).on("click","#btn_th_sh",function(){
//   	$("#btn_th_sh").click(
// 			function() {
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
			 select_commonWin("${ctx}/wsbx/rcbx/check1?type=second&guid="+id.join(",")+"&procinstid="+procinstid.join(","),"退回页面","500","300");
		} else {
			alert("请选择至少一条信息！");
		}
	});
	
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/wsbx/rcbx/expExcel?type=CK&treedwbh=${dwbh}","日常报销","${ctx}",guid.join(","));
   	});
	$("#status").change(function(){
		var val = $(this).val();
		$("[name='dual']").val(val);
	});
});
  	
$("#status").change(function(){
	val = $(this).val();
	location.href="${ctx}/webView/wsbx/rcbx/check_list.jsp?status="+val+"&djbh="+$("[name='djbh']").val()+"&xmguid="+$("[name='xmguid']").val();
});

//审核状态改变
 /*  	$("#status").change(function(){
  		tableRelod(table);
  	});
  	
    var tableRelod = function(table) {
		var status=$("[id='status']").val();
		console.log("status=================reload"+status);
  	    table.ajax.url("${ctx}/wsbx/rcbx/getRcbxDshPageList?status="+status);
  	    table.ajax.reload();
  	}; */

$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
$(document).on("click",".btn_view",function(){
	var guid = $(this).attr("guid");
		var procinstid = $(this).attr("procinstid");
		location.href="${ctx}/wsbx/rcbx/operateByView?operate=L&guid="+guid+"&procinstid="+procinstid;
});
</script>
</body>
</html>