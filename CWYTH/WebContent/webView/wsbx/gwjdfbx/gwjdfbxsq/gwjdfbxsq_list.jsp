<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公务接待费报销申请信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.btn-link{
	padding: 0px!important;
	margin: 0px!important;
}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<%-- <label>审核状态</label>
					<select class="form-control" id="txt_shzt">
						<c:forEach items="${shztList}" var="item">
							<option value="${item.DM }" <c:if test="${item.DM == param.shzt }">selected</c:if>>${item.MC }</option>
						</c:forEach>
					</select> --%>
					<label>审核状态</label>
					<select style="width:150px;" class="form-control" id="txt_shztdm">
	                	<option value="00" <c:if test="${param.shzt=='00'}">selected</c:if>>未提交</option>
	                	<option value="11" <c:if test="${param.shzt=='11'}">selected</c:if>>审核中</option>
	                	<option value="99" <c:if test="${param.shzt=='99'}">selected</c:if>>已通过</option>
	                	<option value="all" <c:if test="${param.shzt=='all'}">selected</c:if>>全部</option>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="DJBH" table="B" placeholder="请输入单据编号">
				</div>
				<div class="form-group">
					<label>报销人</label>
					<input class='form-control' type='text' id='txt_dwbh' name="RYBH" table="B" types="R" placeholder='请输入报销人姓名' ><span class='input-group-btn'>
<!-- 					<input type="text" id="txt_bxry" class="form-control" name="BXRY" table="B"  placeholder="请输入报销人姓名"> -->
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
					<c:if test="${add == true }">
	               <button type="button" class="btn btn-default plugin" id="btn_add">增加</button>
	               </c:if>
	               <button type="button" class="btn btn-default plugin no" id="btn_submit" style="display:none;">批量提交</button> 
	               <button type="button" class="btn btn-default plugin no" id="btn_del" style="display:none;">批量删除</button>
	               <button type="button" class="btn btn-default plugin" id="btn_export">导出Excel</button>
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
				            <th>报销人员</th>
				            <th>所在部门</th>
				            <th>报销金额（元）</th>
				            <th>接待场所</th>
				            <th>接待日期</th>
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
var target = "${ctx}/wsbx/gwjdfbx/gwjdfbxsq";
var suffix = "&mkbh=${param.mkbh}";
// var shzt = "${param.shztdm}";
//加载列表数据
var columns = [
   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	   return '<input type="hidden" class="procinstid" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"><input type="checkbox" name="guid" shzt="'+full.CHECKSHZT+'" class="keyId" xmguid="'+full.XMGUID+'" djbh="'+full.DJBH+'"   value="' + data + '" guid = "'+full.GUID+'">';
   },"width":10,'searchable': false},
   {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
      	return data;},"width":41,"searchable":false,"class":"text-center"},
   {"data": "SHZT",defaultContent:""},
   {"data": "DJBH",defaultContent:"","class":"text-center"},
   {"data": "BXRY",defaultContent:""},
   {"data": "SZBM",defaultContent:""},
   {"data": "BXJE",defaultContent:"0","class":"text-right bxje","width":100,"render":function(data,type,full,meta){
	   return parseFloat(data).toFixed(2);
   }},
   {"data": "JDCS",defaultContent:"","width":400},
   {"data": "JDRQ",defaultContent:"","class":"text-center"},
  // {"data": "A.GUID",'render':function(data, type, full, meta){
   {"data": "PROCINSTID",'render':function(data, type, full, meta){
	   var link = "";
// 	   	var shzt = full.SHZTDM;
// 	   	if((shzt >= 1&& shzt <=7) || shzt == 16){
// 	   		link = '<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;|</a>'
//     			+'<a href="javascript:void(0);" class="btn btn-link btn_record">&nbsp;办理记录&nbsp;</a>';
//   		}else if(shzt == 0){
//   			link = '<a href="javascript:void(0);" class="btn btn-link btn_edit">&nbsp;编辑&nbsp;|</a>'
//     			+'<a href="javascript:void(0);" class="btn btn-link btn_delxx">&nbsp;删除&nbsp;|</a>'
//     			+'<a href="javascript:void(0);" class="btn btn-link btn_submitxx">&nbsp;提交&nbsp;|</a>'
//     			+'<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;</a>';
//   		}else if(shzt == 8){
//   			link = '<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;|</a>'
//     			+'<a href="javascript:void(0);" class="btn btn-link btn_record">&nbsp;办理记录&nbsp;</a>';
// //     			+'<a href="javascript:void(0);" class="btn btn-link btn_print">&nbsp;打印&nbsp;</a>';
//   		}else if(shzt==17 || (shzt >= 9 && shzt<= 15)){
//   			link = '<a href="javascript:void(0);" class="btn btn-link btn_edit">&nbsp;编辑&nbsp;|</a>'
//     			+'<a href="javascript:void(0);" class="btn btn-link btn_delxx">&nbsp;删除&nbsp;|</a>'
//     			+'<a href="javascript:void(0);" class="btn btn-link btn_submitxx">&nbsp;提交&nbsp;|</a>'
//     			+'<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;|</a>'
//     			+'<a href="javascript:void(0);" class="btn btn-link btn_record">&nbsp;办理记录&nbsp;</a>';
//   		}
	   	if(data=='undefined'){
	   		link = '<a href="javascript:void(0);" class="btn btn-link btn_edit">&nbsp;编辑&nbsp;</a>|'
	   		+'<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>|'
	   		+'<a href="javascript:void(0);" class="btn btn-link btn_submitxx">提交</a>|'
	   		+'<a href="javascript:void(0);" class="btn btn-link btn_view">查看</a>|'
	   		+'<a href="javascript:void(0);" class="btn btn-link btn_print">打印</a>';  
 	   }else if(full.SHZT=="审核通过"){
 		  link = '<a href="javascript:void(0);" class="btn btn-link btn_view" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_record" procinstid="'+data+'">办理记录</a>'; 
 	   }else if(full.SHZT=="未提交"){
 		  link = '<a href="javascript:void(0);" class="btn btn-link btn_edit" guid="'+full.GUID+'">&nbsp;编辑&nbsp;</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_submitxx" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_view" guid="'+full.GUID+'">查看</a>'; 
 	   }else if(full.CHECKSHZT=="00"&&full.SHZT!="未提交"){
 		  link = '<a href="javascript:void(0);" class="btn btn-link btn_edit" guid="'+full.GUID+'">&nbsp;编辑&nbsp;</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_submitxx" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_view" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_record" procinstid="'+data+'">办理记录</a>';  
 	   }else{
 		  link = '<a href="javascript:void(0);" class="btn btn-link btn_view" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_record" procinstid="'+data+'">办理记录</a>'; 
 	   }
 	   	return link;
  },orderable:false,"class":"text-center",width:"400px"}
 ];
// table = getDataTableByListHj("mydatatables","${ctx}/wsbx/ccbx/getPageList?mkbh=${mkbh}&type=sq&shzt=${shzt}",[3,'desc,SHZTDM'],columns,0,1,setGroup);
//   table = getDataTableByListHj("mydatatables","wsbx/gwjdfbx/gwjdfbxsq/getPageList?mkbh=${mkbh}&type=sq&shzt=${shzt}",[3,'desc,SHZTDM'],columns,0,1,setGroup);

table = getDataTableByListHj("mydatatables",target+"/getGwjdfbxsqPageData?shzt=${param.shzt}",[3,'desc,JDRQ'],columns,0,1,setGroup);
//
$(function () {
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","R");//联想输入	
//   	$(document).ready(function(){
//   		if((shzt >= 1&&shzt <= 7 )||shzt == 8 || shzt == 16){
//   			$("#btn_add,#btn_submit,#btn_del").hide();
//   		}
//   	});
    //审核状态改变
//   	$(document).on("change","#txt_shzt",function(){
//   		var shzt = $("#txt_shzt").val();
//   		pageSkip(target,"gwjdfbxsq_list&shzt="+shzt);
//   	});
    //添加,批量提交，批量删除，导出excel，打印
    $(document).on("click","#btn_add",function(){
   		pageSkip(target,"xzgwjd&mkbh=${param.mkbh}&type=A",suffix);
   	});
    //批量提交
    $(document).on("click","#btn_submit",function(){
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
	   	 	  var url = "${ctx}/wsbx/process/submit";
		   	  var alert1 = "是否提交？";
		   		$.ajax({
		   			type:"post",
		   			url:"${ctx}/wsbx/process/checkWhoSh",
		   			data:"type=gwjdfbx&guid=guid",
		   			dataType:"json",
		   			async:false,
		   			success:function(val){
		   				alert1 = "单据提交至:"+val.XM+"审核是否提交？";
		   			}
		   		})
	   		 confirm(alert1,"",function(){
	   			$.ajax({
	   	   			url:url,
	   	   			data:"guid="+guid.join(",")+"&type=gwjdfbx",
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
	   	   	var url = target+"/gwjdfbxsqDelete";
	   		confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:url,
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
    $(document).on("click","#btn_export",function(){
   		debugger
   		var shzt = $("[id='txt_shztdm']").val();
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		if(checkbox.length > 0){
			checkbox.each(function(){
				guid.push($(this).val());
			});
			guid = guid.join("','");
		}else{
			guid = "";
		}
		alert(guid);
   		doExp("",target+"/expExcel?shzt="+shzt+"&guid="+guid,"公务接待费报销申请信息","${pageContext.request.contextPath}",guid);
   	});
   	$(document).on("click",".btn_print",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		///var url = target+"/pageSkip?pageName=PrintSample&guid="+guid;
   		//select_commonWin(url,"打印预览","850","700");
   		 select_commonWin("${ctx}/kylbx/singlePrint?guid="+guid+"&lx=公务接待报销","打印预览","850","700");
   	})
   	//编辑,单条删除,单挑提交，查看
   	$(document).on("click",".btn_edit",function(){
   		var guid = $(this).attr('guid');
   		pageSkip(target,"gwjdmx_edit&guid="+guid+"&type=E&sfbj=1");
   	});
	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).attr('guid');
 		var url = target+"/gwjdfbxsqDelete";
		doDel("guid="+guid,url,function(){
			table.ajax.reload();
		},"",1);
	});
   	$(document).on("click",".btn_submitxx",function(){
   		var guid = $(this).attr('guid');
   		var url = "${ctx}/wsbx/process/submit";
   		//------判断shibushi可以提交
		$.ajax({
		type:"post",
		url:"${ctx}/wsbx/gwjdfbx/gwjdfbxsq/checkIsSubmit",
		data:"zbid="+guid,
		async:false,
		success:function(val){
			if(val=="1"){
				var alert1 = "是否提交？";
		   		$.ajax({
		   			type:"post",
		   			url:"${ctx}/wsbx/process/checkWhoSh",
		   			data:"type=gwjdfbx&guid=guid",
		   			dataType:"json",
		   			async:false,
		   			success:function(val){
		   				alert1 = "单据提交至:"+val.XM+"审核是否提交？";
		   			}
		   		});
		   		confirm(alert1,"",function(){
			   		doSub("guid="+guid+"&type=gwjdfbx",url,function(){
			   			alert("操作成功！");			   			
			   			table.ajax.reload();
			   		},"",1);
		   		});
			}else{
				alert("信息项不完整不能提交！")
			}
		}
	});
		//------
   		
   	});
	$(document).on("click",".btn_view",function(){
 		var guid = $(this).attr('guid');
 		pageSkip(target,"gwjdmx_view&guid="+guid+"&link=sq");
	});
	//办理记录
   	$(document).on("click",".btn_record",function(){
   		var processInstanceId=$(this).attr('procinstid');
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb","C");
   	});
	
});
     var shzt = $("#txt_shztdm").val();
     if(shzt=="00"){
	 $(".no").show();
      }else{
     }
    
     $("#txt_shztdm").change(function(){
     var val = $(this).val();
     pageSkip(target,"gwjdfbxsq_list&shzt="+val);
     });

</script>
</body>
</html>