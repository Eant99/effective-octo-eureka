<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>薪资上报录入列表页</title>
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

</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>审核状态</label>
					<select style="width:150px;" class="form-control" id="shztc" table="B">
	                	<option value="00" <c:if test="${checkshzt=='00'}">selected</c:if>>未提交</option>
	                	<option value="11" <c:if test="${checkshzt=='11'}">selected</c:if>>审核中</option>
	                	<option value="99" <c:if test="${checkshzt=='99'}">selected</c:if>>已通过</option>
					</select>
				</div>
				<div class="form-group">
					<label>流水号</label>
					<input type="text" id="txt_djbh" class="form-control" name="fflsh" table="B" placeholder="请输入流水号">
				</div>
				<div class="form-group">
					<label>发放人员</label>
					<select style="width:150px;" class="form-control" id="rylx" name="ffry" table="B">
						<option value="">--请选择--</option>
						<option value="xs">学生</option>
						<option value="xnry">校内人员</option>
	                	<option value="xwry">校外人员</option>
					</select>
				</div> 
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_getmb">提取模版</button>
<!-- 	               <button type="button" class="btn btn-default" id="btn_expmb">模版导出</button> -->
<!-- 	               <button type="button" class="btn btn-default" id="btn_mbimp">模版导入</button> -->
	               <button type="button" class="btn btn-default" id="btn_plsc">批量删除</button>
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
				            <th>流水号</th>
				            <th>录入日期</th>
				            <th>发放方式</th>
				            <th>发放方案</th>
				            <th>摘要</th>
				            <th>财务项目</th>
				            <th>人员类型</th>
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
<script>
var suffix = "&mkbh=${param.mkbh}";
$(function () {
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="hidden" class="procinstid" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"><input type="checkbox" name="guid" shzt="'+full.SHZTDM+'" class="keyId"  ffry="'+full.FFRY+'"   value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:"", orderable:false,"class":"text-center","width":20},
       {"data": "SHZTDM",defaultContent:"","class":"text-center"},
       {"data": "FFLSH",defaultContent:""},
       {"data": "LRSJ",defaultContent:""},
       {"data": "FFFS",defaultContent:""},
       {"data": "FFFA",defaultContent:""},
       {"data": "ZY",defaultContent:""},
       {"data": "XMMC",defaultContent:""},
       {"data": "RYLX",defaultContent:""},
       {"data": "PROCINSTID",'render':function(data, type, full, meta){
    	   if(data=='undefined'){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" shzt="'+full.SHZT+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>';
    	   }else if(full.CHECKSHZT=="99"){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>'; 
    	   }else if(full.SHZT=="未提交"){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" shzt="'+full.SHZT+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>'; 
    	   }else if(full.CHECKSHZT=="00"&&full.SHZT!="未提交"){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" shzt="'+full.SHZT+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>';
    		   
    	   }else{
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>'; 
    	   }
	   		
      },orderable:false,"class":"text-center"}
     ];
  table = getDataTableByListHj("mydatatables","${ctx}/xzsblr/getPageList?mkbh=${mkbh}&shzt=${param.shzt}",[3,'desc'],columns,0,1,setGroup);
  $("#shztc").change(function(){
	  var shzt = $(this).val();
	  table.ajax.url("${ctx}/xzsblr/getPageList?shzt="+shzt);
	  table.ajax.reload();
  });
  $("#shzt").change(function(){
		var val = $(this).val();
		location.href="${ctx}/xzsblr/goPageList?shzt="+val;
	});
  
  	$(document).on("click","#btn_add",function(){
  		var rylx = $("#rylx").val();
  		var vNow = new Date();
  		var lsh = "";
  		lsh += String(vNow.getFullYear());
  		lsh += String(vNow.getMonth() + 1);
  		lsh += String(vNow.getDate());
  		lsh += String(vNow.getHours());
  		lsh += String(vNow.getMinutes());
  		lsh += String(vNow.getSeconds());
  		lsh += String(vNow.getMilliseconds());
  		if(rylx == ""||rylx == null||rylx == undefined){
  			alert("请选择人员类型!");
  			return;
  		}
  		doOperate("${pageContext.request.contextPath}/xzsblr/goEditPage?rylx="+rylx+"&lsh="+lsh,"C");
   	});
    //办理记录
   	$(document).on("click",".btn_bljl",function(){
   		var processInstanceId=$(this).attr('procinstid');
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb"+suffix,"C");
   		
   	});
  	//查看按钮
	   $(document).on("click",".btn_look",function(){
		   var rylx = $(this).parents("tr").find(":checkbox").attr("ffry");
	   	   var guid = $(this).parents("tr").find(":checkbox").val();
		   doOperate("${pageContext.request.contextPath}/xzsblr/goEditPage?rylx="+rylx+"&guid="+guid,"L");
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var rylx = $(this).parents("tr").find(":checkbox").attr("ffry");
   		var guid = $(this).parents("tr").find(":checkbox").val();
   		doOperate("${pageContext.request.contextPath}/xzsblr/goEditPage?rylx="+rylx+"&guid="+guid,"U");
   	});
   	//提取模版type用于页面判断
  	$(document).on("click","#btn_getmb",function(){
  		select_commonWin("${ctx}/webView/srgl/xzsblr/xzsblrmb_list.jsp?type=model","模版信息","920","630");
   	});
  //提交按钮
//   $(document).on("click",".btn_tj",function(){
//    			var guid = $(this).parents("tr").find(":checkbox").attr("guid");
//    			var alert1 = "是否提交？";
//    			confirm(alert1,"",function(){
// 		   			$.ajax({
// 		   	   			url:"${ctx}/xzsblr/submit",
// 		   	   			data:"guid="+guid,
// 		   	   			type:"post",
// 		   	   			async:"false",
// 		   	   			success:function(val){
// 		   	   				alert("操作成功！");
// 		   	   				table.ajax.reload();
// 		   	   			}
// 		   	   		});
// 		   		});   			
//    			});
//提交按钮
  $(document).on("click",".btn_tj",function(){
	  		var guid = $(this).parents("tr").find(":checkbox").attr("guid");
   			var alert1 = "是否提交？";
   			var tagg = false;
   			//------判断shibushi可以提交
   			$.ajax({
   				url:"${ctx}/wsbx/process/checkWhoSh",
   				data:"guid="+guid+"&type=srsblr",
   				type:"post",
   				async:false,
   				dataType:"json",
   				success:function(val){
   					if(val.XZ=="submit"){
   						alert1 = "单据提交至:"+val.XM+"审核,是否提交？";
   						tagg = true;
   					}else if(val.XZ=="hq"){
   						alert1 = "单据提交至:"+val.XM+"审核,是否提交？";
   						tagg = true;
   					}else{
   						select_commonWin("${ctx}/webView/wsbx/rcbx/jumpWindow.jsp?guid="+guid,"选择页面","240","200");
   						return;
   					}
   				}
   			});
   			//提交操作
   			if(tagg){
   				confirm(alert1,"",function(){
   		   			$.ajax({
   		   	   			url:"${ctx}/wsbx/process/submit",
   		   	   			data:"guid="+guid+"&type=srsblr",
   		   	   			type:"post",
   		   	   			async:"false",
   		   	   			success:function(val){
   		   	   				alert("操作成功！");
   		   	   				table.ajax.reload();
   		   	   			}
   		   	   		});
   		   		});
   			}
   			
   	});
	//撤销提交
  	$(document).on("click",".btn_cx",function(){
   			var guid = $(this).parents("tr").find(":checkbox").attr("guid");
   			var alert1 = "是否撤销？";
   			confirm(alert1,"",function(){
		   			$.ajax({
		   	   			url:"${ctx}/xzsblr/chexiao",
		   	   			data:"guid="+guid,
		   	   			type:"post",
		   	   			async:"false",
		   	   			success:function(val){
		   	   				alert("操作成功！");
		   	   				table.ajax.reload();
		   	   			}
		   	   		});
		   		});   			
   			});
    //批量提交
//    	$("#btn_tj").click(function(){
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
//    		var yes = 0;
//    		var no = 0;
//    		if(checkbox.length>0){
//    	   		var guid = [];
//    	   		checkbox.each(function(){
//    	   			var shzt = $(this).attr("shzt");
// 	   	   		if(shzt=="00"){
// 	   				guid.push($(this).val());
// 	   				yes++;
// 	   			}else{
// 	   				no++;
// 	   			}
//    	   		});
// 			var alert1 = "是否提交？";
// 			if(checkbox.length==1){
// 				$.ajax({
// 	   	   			type:"post",
// 	   	   			url:"${ctx}/wsbx/process/checkWhoSh",
// 	   	   			data:"type=rcbx&guid="+checkbox.val(),
// 	   	   			dataType:"json",
// 	   	   			async:false,
// 	   	   			success:function(val){
// 	   	   				alert1 = "单据提交至:"+val.XM+"审核是否提交？";
// 	   	   			}
// 	   	   		});
// 			}
// 	   	   	confirm(alert1,"",function(){
// 	   			$.ajax({
// 	   	   			url:"${ctx}/wsbx/process/submit",
// 	   	   			data:"guid="+guid.join(",")+"&type=rcbx",
// 	   	   			type:"post",
// 	   	   			async:"false",
// 	   	   			success:function(val){
// 	   	   			if(no>0){
//    						alert(yes+"条数据提交成功,"+no+"条数据已提交无法提交!");
//    					}else{
//    						alert(yes+"条数据提交成功!");
//    					}
// 	   	   				table.ajax.reload();
// 	   	   			}
// 	   	   		});
// 	   		});
//    		}else{
//    			alert("请选择至少一条信息!");
//    		}
//    	});
    //批量删除按钮
    $(document).on("click","#btn_plsc",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var yes = 0;
   		var no = 0;
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			var shzt = $(this).attr("shzt");
   	   			if(shzt=='未提交'){
	   				guid.push($(this).val());
	   				yes++;
	   			}else{
	   				no++;
	   			}
   	   		});
	   	   	confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/xzsblr/doDelete",
	   	   			data:"guid="+guid.join("','"),
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
		var parentTr = $(this).parents("tr").find(":checkbox");
   		var shzt = parentTr.attr("shzt");
   		if(shzt == '未提交'){
	   		var guid = parentTr.attr("guid");
			confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/xzsblr/doDelete",
	   	   			data:"guid="+guid,
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("删除成功");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("已提交的数据和未审核的数据能删除!");
   		}
	});
	//打印按钮
   	$(".btn_print").click(function(){
   		var parentTr = $(this).parents("tr").find(":checkbox");
   		var guid = parentTr.attr("guid");
   		location.href = "${ctx}/wsbx/rcbx/goPrint?guid="+guid;
   	});
	//导出Excel
	$(document).on("click","#btn_exp",function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/wsbx/rcbx/expExcel?treedwbh=${dwbh}","日常报销","${ctx}",guid.join(","));
   	});
	shzt();
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
	if(shzt=="00"||shzt=="all"){
		$(".no").css("display","");
	}else{
		$(".no").css("display","none");
	}
});
function shzt(){
	var json = searchJson("searchBox");
	$('#mydatatables').DataTable().search(json,"json").draw();
}
function select(guid,xz,type){
	 $.ajax({
		url:"${ctx}/wsbx/process/submit",
		data:"guid="+guid+"&type=srsblr&xz="+xz,
		type:"post",
		async:"false",
		success:function(val){
			alert("提交成功");
			if(type=="too"){
				location.href.reload();
			}else{0
				table.ajax.reload();
			}
		}
	});
}
</script>
</body>
</html>