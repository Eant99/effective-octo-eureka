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
/* 		width:1020px ! important; */
/* 	} */
/* 	table.dataTable{ */
/* 		width:1020px ! important; */
/* 	} */
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>审核状态</label>
					<select style="width:150px;" id="sh" class="form-control">
	                	<option value="00" <c:if test="${shzt=='00'}">selected</c:if>>未提交</option>
	                	<option value="11" <c:if test="${shzt=='11'}">selected</c:if>>审核中</option>
	                	<option value="99" <c:if test="${shzt=='99'}">selected</c:if>>已通过</option>
	                	<option value="all" <c:if test="${shzt=='all'}">selected</c:if>>全部</option>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_bxr" class="form-control" name="DJBH" table="B" placeholder="请输入单据编号">
				</div>
				<div class="form-group">
					<label>报&ensp;销&ensp;人</label>
					<input type="text" id="txt_bxr" class="form-control" name="BXR" table="B" placeholder="请输入报销人姓名">
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label>所在部门</label> -->
<!-- 					<input type="text" id="txt_bmmc" class="form-control" name="SZBM"  table="B" placeholder="请输入部门名称"> -->
<!-- 				</div> -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default no" id="btn_del">批量删除</button>
 	                <!-- <button type="button" class="btn btn-default no" id="btn_pltj">批量提交</button> -->
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
				            <th>审核状态</th>
				            <th>单据编号</th>
				            <th>报销人</th>
				            <th>所在部门</th>
<!-- 				            <th>经费类型</th> -->
				            <th>报销日期</th>
				            <th>报销总金额(元)</th>
				            <th>出差类型</th>
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
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="hidden" class="procinstid" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"><input type="checkbox" name="guid"  money="'+full.MONEY+'" procinstid="'+full.PROCINSTID+'" ccywguid="'+full.CCYWGUID+'" shzt="'+full.CHECKSHZT+'" class="keyId" xmguid="'+full.XMGUID+'" djbh="'+full.DJBH+'" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:"","class":"text-center","width":10},
       {"data": "SHZT",defaultContent:"","class":"text-center","width":40},
       {"data": "DJBH",defaultContent:"","width":100},
       {"data": "BXR",defaultContent:"","width":40},
       {"data": "SZBM",defaultContent:"","width":180},
//        {"data": "JFLX",defaultContent:""},
       {"data": "CZRQ",defaultContent:"","class":"text-center"},
       {"data": "BXZJE",defaultContent:"0.00","class":"text-right","width":80},
       {"data": "CCLX",defaultContent:""},
       {"data": "PROCINSTID",'render':function(data, type, full, meta){
    	   if(data=='undefined'){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" xmguid="'+full.XMGUID+'" ccywguid="'+full.CCYWGUID+'"  money="'+full.MONEY+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'" ccywguid="'+full.CCYWGUID+'">查看</a>';  
    	   }else if(full.SHZT=="审核通过"){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'" ccywguid="'+full.CCYWGUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>'; 
    	   }else if(full.SHZT=="未提交"){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" xmguid="'+full.XMGUID+'" ccywguid="'+full.CCYWGUID+'"  money="'+full.MONEY+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'" ccywguid="'+full.CCYWGUID+'">查看</a>'; 
    	   }else if(full.CHECKSHZT=="00"&&full.SHZT!="未提交"){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" xmguid="'+full.XMGUID+'" ccywguid="'+full.CCYWGUID+'"  money="'+full.MONEY+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'" ccywguid="'+full.CCYWGUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>';  
    	   }
    	   else{
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'" ccywguid="'+full.CCYWGUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>'; 
    	   }
	   		
      },orderable:false,"class":"text-center"}
     ];
  table = getDataTableByListHj("mydatatables","${ctx}/wsbx/ccbx/getPageList?mkbh=${mkbh}&type=sq&shzt=${shzt}",[3,'desc,SHZTDM'],columns,0,1,setGroup);
  
//     table = getDataTableByListHj("mydatatables","${ctx}/json/wsbx/rcbx/rcbxList.json",[2,'asc'],columns,0,1,setGroup);
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
   		//location.href="${ctx}/wsbx/rcbx/operate?operate=C";
   		location.href = "${ctx}/webView/wsbx/ccbx/xzxm.jsp?operate=C&mkbh=${param.mkbh}";
   	});
  	//查看按钮
  	$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
   		var ccywguid = $(this).attr("ccywguid");
   		var shzt = $("[id='sh']").val();
   		location.href = "${ctx}/wsbx/ccbx/goViewJsp?guid="+guid+"&type=v&shzt="+shzt+"&ccywguid="+ccywguid;
   	});

	//导出Excel
	$(document).on("click","#btn_exp",function(){
   		var json = searchJson("searchBox");
   		var shzt = $("#sh").val();
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
		doExp(json,"${ctx}/wsbx/ccbx/expExcel_Sq?shzt="+shzt+"&guid="+guid.join(","),"差旅费报销申请","${ctx}","");
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var xmguid = $(this).attr("xmguid");
   		var zbid = $(this).attr('guid');
   		var djbh = $(this).attr("djbh");
   		var ccywguid = $(this).attr("ccywguid");
   		var money = $(this).attr("money");
   		location.href = "${ctx}/wsbx/ccbx/goYwblEditPage?sfbj=1&operate=U&Xmguid="+xmguid+"&mkbh=${mkbh}"+"&zbguid="+zbid+"&djbh="+djbh+"&ccywguid="+ccywguid+"&money="+money;
   	});
    //办理记录
   	$(document).on("click",".btn_bljl",function(){
   		var processInstanceId=$(this).attr('procinstid');
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb","C");
   		
   	});
  //提交按钮
  $(document).on("click",".btn_tj",function(){
   			var guid = $(this).attr("guid");
			var alert1 = "是否提交？";
			var tagg = false;
			//------判断shibushi可以提交st
   			$.ajax({
				type:"post",
				url:"${ctx}/wsbx/ccbx/checkIsSubmit",
				data:"zbid="+guid,
				async:false,
				success:function(val){
					console.log("___"+val)
					if(val=="1"){
						$.ajax({
			   				url:"${ctx}/wsbx/process/checkWhoSh",
			   				data:"guid="+guid+"&type=clfbx",
			   				type:"post",
			   				async:false,
			   				dataType:"json",
			   				success:function(val){
			   					if(val.XZ=="submit"){
			   						alert1 = "单据提交至:"+val.XM+"审核是否提交？";
			   						tagg = true;
			   					}else if(val.XZ=="hq"){
			   						alert1 = "单据提交至:"+val.XM+"审核是否提交？";
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
			   		   	   			data:"guid="+guid+"&type=clfbx",
			   		   	   			type:"post",
			   		   	   			async:"false",
			   		   	   			success:function(val){
			   		   	   				alert("操作成功！");
			   		   	   				table.ajax.reload();
			   		   	   			}
			   		   	   		});
			   		   		});
			   			}
					}else{
						alert("信息项不完整不能提交！")
					}
				}
			});
   			//------判断shibushi可以提交end
   			
   	});
    //批量提交
    $(document).on("click","#btn_pltj",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		var yes = 0;
   			var no = 0;
   	   		checkbox.each(function(){
   	   			var shzt = $(this).attr("shzt");
	   	   		if(shzt=="00"){
	   				guid.push($(this).val());
	   				yes++;
	   			}else{
	   				no++;
	   			}
   	   		});
   	   		$.ajax({
	   			type:"post",
	   			url:"${ctx}/wsbx/process/checkWhoSh",
	   			data:"type=clfbx&guid="+guid,
	   			dataType:"json",
	   			async:false,
	   			success:function(val){
	   				alert1 = "单据提交至:"+val.XM+"审核是否提交？";
	   			}
	   		});
			var alert1 = "是否提交？";
	   	   	confirm(alert1,"",function(){
	   			$.ajax({
	   	   			url:"${ctx}/wsbx/process/submit",
	   	   			data:"guid="+guid.join(",")+"&type=clfbx",
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
		   	   			if(no>0){
	   						alert(yes+"条数据提交成功,"+no+"条数据已提交无法提交!");
	   					}else{
	   						alert(yes+"条数据提交成功!");
	   					}
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息提交!");
   		}
   	});
    //批量删除按钮
    $(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		var yes = 0;
   			var no = 0;
   	   		checkbox.each(function(){
   	   			var shzt = $(this).attr("shzt");
   	   			if(shzt=="00"){
   					guid.push($(this).val());
   					yes++;
   				}else{
   					no++;
   				}
   	   		});
	   	   	confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/wsbx/ccbx/doDelete",
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
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
   		var guid = $(this).attr("guid");
		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:"${ctx}/wsbx/ccbx/doDelete",
   	   			data:"guid="+guid,
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
   	$(".btn_print").click(function(){
   		
   		
   		var parentTr = $(this).parents("tr").find(":checkbox");
   		var guid = parentTr.attr("guid");
   		location.href = "${ctx}/wsbx/ccbx/goPrint?guid="+guid;
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
	var shzt = $("[id='sh']").val();
	if(shzt=="00"||shzt=="all"){
		$(".no").css("display","");
	}else{
		$(".no").css("display","none");
	}
});
$("#sh").change(function(){
	var val = $(this).val();
	location.href="${ctx}/wsbx/ccbx/goCcbxListPage?shzt="+val;
});

//选择的方法
function select(guid,xz,type){
	 $.ajax({
		url:"${ctx}/wsbx/process/submit",
		data:"guid="+guid+"&type=clfbx&xz="+xz,
		type:"post",
		async:"false",
		success:function(val){
			alert("提交成功");
			if(type=="too"){
				location.href.reload();
			}else{
				table.ajax.reload();
			}
		}
	});
}
</script>
</body>
</html>