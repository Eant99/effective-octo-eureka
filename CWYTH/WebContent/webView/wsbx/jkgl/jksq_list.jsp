<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>借款申请</title>
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
	                	<option value="01" <c:if test="${param.shzt=='01'}">selected</c:if>>未提交</option>
	                	<option value="02" <c:if test="${param.shzt=='02'}">selected</c:if>>审核中</option>
	                	<option value="03" <c:if test="${param.shzt=='03'}">selected</c:if>>已通过</option>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh" table="B" placeholder="">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default no" id="btn_del">批量删除</button>
	                <button type="button" class="btn btn-default no" id="btn_tj">批量提交</button>
	                <button type="button" class="btn btn-default " id="btn_exp">导出Excel</button>
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
//路径后缀
var suffix = "&mkbh=${param.mkbh}";
$(function () {
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="gid" shzt="'+full.SHZTDM+'" class="keyId" data-procinstid="'+full.PROCINSTID+'" djbh="'+full.DJBH+'" zffs="'+full.ZFFS+'"  value="' + data + '" gid = "'+full.GID+'">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:"","class":"text-center","width":20},
       {"data": "SHZT",defaultContent:"","class":"text-center"},
       {"data": "DJBH",defaultContent:""},
       {"data": "JKR",defaultContent:"",},
       {"data": "SZBM",defaultContent:""},
       {"data": "JKSJ",defaultContent:"","class":"text-center"},
       {"data": "JKZJE",defaultContent:"0.00","class":"text-right","width":100},
       {"data": "PROCINSTID",'render':function(data, type, full, meta){
    	   if(full.SHZTDM=='01'){//未提交
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GID+'" zffs="'+full.ZFFS+'">查看</a>';
    	   }else if(full.SHZTDM=='04'||full.SHZTDM=='09'||full.SHZTDM=='06'||full.SHZTDM=='16'||full.SHZTDM=='19'){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GID+'" zffs="'+full.ZFFS+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_record" procinstid="'+full.PROCINSTID+'">&nbsp;办理记录</a>'; 
    	   }
    	   else{
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GID+'" zffs="'+full.ZFFS+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_record" procinstid="'+full.PROCINSTID+'">&nbsp;办理记录</a>'; 
    	   }
      },orderable:false,"class":"text-center"}
     ];
  table = getDataTableByListHj("mydatatables","${ctx}/jksq/getPageList?shzt=${shztdm}",[3,'desc'],columns,0,1,setGroup);
  $(document).on("click","#btn_add",function(){
   		doOperate("${pageContext.request.contextPath}/jksq/goEditPage?mkbh=${param.mkbh}","C");
   	});
	
	 //修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var gid = $(this).attr('guid');
		doOperate("${pageContext.request.contextPath}/jksq/goEditPage?mkbh=${param.mkbh}&gid="+gid,"U");
   	});
  	//查看按钮
	   $(document).on("click",".btn_look",function(){
   		var shztdm = $("[id='shztdm']").val();
   		var gid = $(this).attr("guid");
   		var zffs = $(this).attr("zffs");
   		location.href = "${ctx}/jksq/goLookPage?mkbh=${mkbh}&guid="+gid+"&shztdm="+shztdm+"&zffs="+zffs;
   	});
    //查看办理记录
   	$(document).on("click",".btn_record",function(){
   		var processInstanceId=$(this).attr("procinstid");
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=jklc"+suffix,"C");
   	});
    
  //提交按钮
    $(document).on("click",".btn_tj",function(){
   			var gid = $(this).attr("guid");
   			var alert1 = "是否提交？";
   	   		confirm(alert1,"",function(){
   	   			$.ajax({
   	   	   			url:"${ctx}/jksq/submit",
   	   	   			data:"gid="+gid,
   	   	   			type:"post",
   	   	   			async:"false",
   	   	   			dataType:"json",
   	   	   			success:function(val){
   	   	   				if(val.success){
	   	   	   				alert("提交成功");
	   	   	   				table.ajax.reload();
   	   	   				}else{
   	   	   			    	alert("提交失败");
   	   	   				}
   	   	   			}
   	   	   		});
   	   		});
   	});
    //批量提交
    $(document).on("click","#btn_tj",function(){
   		var checkbox = $("#mydatatables").find("[name='gid']").filter(":checked");
   		var yes = 0;
   		var no = 0;
   		if(checkbox.length>0){
   	   		var gid = [];
   	   		checkbox.each(function(){
   	   			var shzt = $(this).attr("shzt");
   	   			if(shzt=="01"||shzt=="04"){
   	   				gid.push($(this).val());
   	   				yes++;
   	   			}else{
   	   				no++;
   	   			}
   	   		});
			var alert1 = "是否提交？";
	   	   	confirm(alert1,"",function(){
	   			$.ajax({
	   				url:"${ctx}/jksq/submit",
	   	   			data:"gid="+gid.join(","),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
   	   					if(no>0){
   	   						alert(yes+"条数据提交成功,"+no+"条数据无法重复提交!");
   	   					}else{
   	   						alert(yes+"条数据提交成功!");
   	   					}
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息!");
   		}
   	});
    //批量删除按钮
    $(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='gid']").filter(":checked");
   		var yes = 0;
   		var no = 0;
   		if(checkbox.length>0){
   	   		var gid = [];
   	   		checkbox.each(function(){
   	   			var shzt = $(this).attr("shzt");
	   			if(shzt=="01"||shzt=="04"){
	   				gid.push($(this).val());
	   				yes++;
	   			}else{
	   				no++;
	   			}
   	   		});
	   	   	confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/jksq/doDelete",
	   	   			data:"gid="+gid.join(","),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				if(no>0){
	   						alert(yes+"条数据删除成功,"+no+"条数据已提交无法删除!");
	   					}else{
	   						alert(yes+"条数据删除成功!");
	   					}
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息!");
   		}
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
   		var gid = $(this).attr("guid");
		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:"${ctx}/jksq/doDelete",
   	   			data:"gid="+gid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("删除成功");
   	   				table.ajax.reload();
   	   			}
   	   		});
   		});
	});
	//打印按钮
	$(document).on("click",".btn_print",function(){
   		var guid = $(this).attr("guid");
   		location.href = "${ctx}/wsbx/rcbx/goPrint?guid="+guid;
   	});
	//导出Excel
	$(document).on("click","#btn_exp",function(){
		var shzt = $("[id='shztdm']").val();
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='gid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
		debugger;
   		doExp(json,"${ctx}/jksq/expExcel?shzt="+shzt+"&guid="+guid.join("','"),"借款申请","${ctx}",guid.join("','"));
   	});
	

$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
	var shzt = $("[id='shztdm']").val();
	if(shzt=="01"||shzt=="all"){
		$(".no").css("display","");
	}else{
		$(".no").css("display","none");
	}
});
$("#shztdm").change(function(){
	var shzt = $(this).val();
	location.href = "${ctx}/jksq/goJksqPage?mkbh=${mkbh}&shzt="+shzt;
});
});
</script>
</body>
</html>