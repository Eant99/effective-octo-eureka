<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>借款审核</title>
<%@include file="/static/include/public-list-css.inc"%>
<%--表头样式--%>
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
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
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>审核状态</label>
					<select style="width:150px;" class="form-control" id="shztdm">
	                	<option value="01" <c:if test="${param.shzt=='01'}">selected</c:if>>未审核</option>
	                	<option value="02" <c:if test="${param.shzt=='02'}">selected</c:if>>已审核</option>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh" table="A" placeholder="">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<c:if test="${param.shzt!='02'}">
					<div class="btn-group pull-right" role="group">
		                <button type="button" class="btn btn-default" id="btn_tg_sh">批量通过</button>
		               <button type="button" class="btn btn-default" id="btn_th_sh">批量退回</button>
					</div>
				</c:if>
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
				            <th>审核状态</th>
				            <th>单据编号</th>
				            <th>借款人</th>
				            <th>所在部门</th>
				            <th>借款时间</th>
				            <th>借款总金额(元)</th>
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
var suffix = "&mkbh=${param.mkbh}";
$(function () {
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="gid" shzt="'+full.SHZTDM+'"  procinstid="'+full.PROCINSTID+'" class="keyId" djbh="'+full.DJBH+'"   value="' + data + '" gid = "'+full.GID+'">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:"","class":"text-center","width":20},
       {"data": "SHZT",defaultContent:"","class":"text-center"},
       {"data": "DJBH",defaultContent:""},
       {"data": "JKR",defaultContent:"",},
       {"data": "SZBM",defaultContent:""},
       {"data": "JKSJ",defaultContent:"","class":"text-center"},
       {"data": "JKZJE",defaultContent:"0.00","class":"text-right","width":100},
       {"data": "GID",'render':function(data, type, full, meta){
    	   if(full.SHZTPD=='01'){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GID+'" zffs="'+full.ZFFS+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_check an" guid="'+full.GID+'" procinstid="'+full.PROCINSTID+'">审核</a>|<a href="javascript:void(0);" class="btn btn-link btn_record" procinstid="'+full.PROCINSTID+'">&nbsp;办理记录</a>';   
    	   }else{
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GID+'" zffs="'+full.ZFFS+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_record" procinstid="'+full.PROCINSTID+'">&nbsp;办理记录</a>';
    	   }
    	  
      },orderable:false,"class":"text-center"}
     ];
  table = getDataTableByListHj("mydatatables","${ctx}/jksh/getPageList?shzt=${shzt}",[6,'desc'],columns,0,1,setGroup);
  	//审核按钮
	   $(document).on("click",".btn_check",function(){
   		var gid = $(this).attr("guid");
   		var procinstid = $(this).attr("procinstid");
   		location.href = "${ctx}/jksq/goLookPage?mkbh=${mkbh}&guid="+gid+"&flag=1"+"&procinstid="+procinstid;
   	});
	//查看按钮
   $(document).on("click",".btn_look",function(){
  		var shztdm = $("[id='shztdm']").val();
  		var gid = $(this).attr("guid");
  		var zffs = $(this).attr("zffs");
  		location.href = "${ctx}/jksq/goLookPage?mkbh=${mkbh}&guid="+gid+"&shztdm="+shztdm+"&zffs="+zffs+"&flag=3";
   	});
	 //查看办理记录
   	$(document).on("click",".btn_record",function(){
   		var processInstanceId=$(this).attr("procinstid");
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=jklc"+suffix,"C");
   	});
	   //批量审核
	   $(document).on("click","#btn_tg_sh",function(){
			var status = $("#status").find("option:selected").val();
			if(status=="1"){
				alert("不可重复审核！");
				return;
			}
			var checkbox = $("#mydatatables").find("[name='gid']").filter(":checked");
			if (checkbox.length > 0) {
				var gid = [];
				var procinstid = [];
				checkbox.each(function() {
					gid.push($(this).val());
					procinstid.push($(this).attr("procinstid"));
				});
				 select_commonWin("${ctx}/jksh/check1?type=first&gid="+gid.join(",")+"&procinstid="+procinstid.join(","),"通过页面","500","300");
			} else {
				alert("请选择至少一条信息！");
			}
		});
	//批量退回
	$(document).on("click","#btn_th_sh",function(){
			var checkbox = $("#mydatatables").find("[name='gid']").filter(":checked");
			if (checkbox.length > 0) {
				var gid = [];
				var procinstid = [];
				checkbox.each(function() {
					gid.push($(this).val());
					procinstid.push($(this).attr("procinstid"));
				});
				 select_commonWin("${ctx}/jksh/check1?type=second&gid="+gid.join(",")+"&procinstid="+procinstid.join(","),"退回页面","500","300");
			} else {
				alert("请选择至少一条信息！");
			}
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
$("#shztdm").change(function(){
	var shzt = $(this).val();
	location.href = "${ctx}/jksh/goJkshPage?mkbh=${mkbh}&shzt="+shzt;
});
});
</script>
</body>
</html>