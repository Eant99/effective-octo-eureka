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
/*  		width:900px ! important; */
/* 	}  */
/*  	table.dataTable{  */
/*  		width:900px ! important; */
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
					<select style="width:150px;" class="form-control" id="shztdm">
	                	<option value="00" <c:if test="${param.shzt=='00'}">selected</c:if>>未提交</option>
	                	<option value="11" <c:if test="${param.shzt=='11'}">selected</c:if>>审核中</option>
	                	<option value="99" <c:if test="${param.shzt=='99'}">selected</c:if>>已通过</option>
	                	<option value="all" <c:if test="${param.shzt=='all'}">selected</c:if>>全部</option>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh" table="B" placeholder="">
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label>项目编号</label> -->
<!-- 					<input type="text" id="txt_xmmc" class="form-control" name="xmguid"  table="B" placeholder=""> -->
<!-- 				</div> -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
<!-- 				<div id="btn_search_more"> -->
<!-- 					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span> -->
<!-- 					<div class="search-more"> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>状&emsp;&emsp;态</label> -->
<!-- 							<div class="input-group"> -->
<!-- 								<select style="width:70px;" class="form-control select2"> -->
<!-- 									<option value="">未选择</option> -->
<%-- 	                				<c:forEach var="shztList" items="${shztList}"> --%>
<%-- 		               					<option value="${shztList.DM}">${shztList.MC}</option> --%>
<%-- 		            				</c:forEach> --%>
<!-- 								</select> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>报&ensp;销&ensp;人</label> -->
<!-- 							<input type="text" id="txt_bxr" class="form-control" name="bxr" table="A" placeholder="请输入人员工号"> -->
<!-- 						</div> -->
<!-- 						<div class="form-group">  -->
<!-- 							<label>部门名称</label> -->
<!-- 							<div class="input-group"> -->
<!-- 								<input type="text" id="txt_bmmc" class="form-control input-radius" name="bmmc"  table="A" placeholder="请输入部门名称"> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>项目名称</label> -->
<!-- 							<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="A" placeholder="请输入项目名称"> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>申请时间</label> -->
<!-- 							<input type="text" id="txt_sqrq" class="form-control" name="sqrq" table="A" placeholder="申请时间"  -->
<!-- 							value=""  data-format="yyyy-MM-dd"> -->
<!-- 						</div> -->
						
<!-- 						<div class="search-more-bottom clearfix"> -->
<!-- 							<div class="pull-right"> -->
<!-- 								<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i> -->
<!-- 									查 询 -->
<!-- 								</button> -->
<!-- 								<button type="button" class="btn btn-default" id="btn_cancel"> -->
<!-- 									取 消 -->
<!-- 								</button> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default no" id="btn_del">批量删除</button>
<!-- 	                <button type="button" class="btn btn-default no" id="btn_tj">批量提交</button> -->
<!-- 	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button> -->
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
				            <th>报销日期</th>
				            <th>报销总金额(元)</th>
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
	checkBzy();
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="hidden" class="procinstid" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"><input type="checkbox" name="guid" shzt="'+full.CHECKSHZT+'" class="keyId" xmguid="'+full.XMGUID+'" djbh="'+full.DJBH+'"   value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:"","class":"text-center","width":20},
       {"data": "SHZT",defaultContent:"","class":"text-center"},
       {"data": "DJBH",defaultContent:""},
       {"data": "BXR",defaultContent:"",},
       {"data": "SZBM",defaultContent:""},
       {"data": "CZRQ",defaultContent:"","class":"text-center"},
       {"data": "BXZJE",defaultContent:"0.00","class":"text-right","width":100},
       {"data": "PROCINSTID",'render':function(data, type, full, meta){
    	   if(data=='undefined'){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" shzt="'+full.SHZT+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>';  
    	   }else if(full.CHECKSHZT=="99"){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>'; 
    	   }else if(full.SHZT=="未提交"){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" shzt="'+full.SHZT+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>'; 
    	   }else if(full.CHECKSHZT=="00"&&full.SHZT!="未提交"){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" shzt="'+full.SHZT+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid="'+full.GUID+'">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_tj" guid="'+full.GUID+'">提交</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>';
    		   
    	   }else{
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+data+'">办理记录</a>'; 
    	   }
	   		
      },orderable:false,"class":"text-center"}
     ];
  table = getDataTableByListHj("mydatatables","${ctx}/wsbx/rcbx/getPageList?mkbh=${mkbh}&shzt=${param.shzt}",[3,'desc'],columns,0,1,setGroup);
//     table = getDataTableByListHj("mydatatables","${ctx}/json/wsbx/rcbx/rcbxList.json",[2,'asc'],columns,0,1,setGroup);
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
   		//location.href="${ctx}/wsbx/rcbx/operate?operate=C";
   		location.href = "${ctx}/webView/wsbx/rcbx/selectXm.jsp?operate=C&mkbh=${param.mkbh}";
   	});
  	//查看按钮
   //	$(".btn_look").click(function(){
	   $(document).on("click",".btn_look",function(){
   		var shztdm = $("[id='shztdm']").val();
   		var guid = $(this).attr("guid");
   		location.href = "${ctx}/wsbx/rcbx/goViewJsp?mkbh=${mkbh}&guid="+guid+"&shztdm="+shztdm;
   	});
    //办理记录
   	$(document).on("click",".btn_bljl",function(){
   		var processInstanceId=$(this).attr('procinstid');
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb"+suffix,"C");
   		
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var shzt = $(this).attr("shzt");
 		var xmguid = "";
  	   	var zbid = $(this).attr('guid');
   	   	var djbh = $(this).attr("djbh");
		$.ajax({
			type:"post",
			url:"${ctx}/wsbx/rcbx/getXmxx?zbid="+zbid,
			data:{zbid:zbid},
			dataType:"json",
			async: true,
			success:function(val){
				$.each(val,function(index, n){
					xmguid = xmguid + n.XMGUID + ",";
				  });
				xmguid = xmguid.substring(0,xmguid.length-1);
			    location.href = "${ctx}/webView/wsbx/rcbx/selectXm.jsp?bjzt=U&flag=1&xmguid="+xmguid+"&mkbh=${mkbh}"+"&zbid="+zbid+"&djbh="+djbh+"&sfbj=1";
			}
		});
   	});
  //提交按钮
  $(document).on("click",".btn_tj",function(){
   			var guid = $(this).attr("guid");
  			
					var alert1 = "是否提交？";
		   			var tagg = false;
		   			//------判断shibushi可以提交
		   			$.ajax({
						type:"post",
						url:"${ctx}/wsbx/rcbx/checkIsSubmit",
						data:"zbid="+guid,
						async:false,
						dataType:"json",
						success:function(val){
							if(val=="1"){
								$.ajax({
									type:"post",
									url:"${ctx}/wsbx/rcbx/checkSfxy",
									data:"guid="+guid,
									async:false,
									dataType:"json",
									success:function(val){
										 if(val=="0"){//不是学院直接提交到(二级院长或行政负责人审核)
   												confirm("是否提交审核？","",function(){
   													$.ajax({
   														url:"${ctx}/wsbx/process/submit",
   														data:"guid="+guid+"&type=rcbx&xz=yzfzr",
   														type:"post",
   														async:"false",
   														success:function(val){
   															alert("操作成功！");
   															table.ajax.reload();
   														}
   													});
   												});
				   							}else{
											  $.ajax({
								   				url:"${ctx}/wsbx/process/checkWhoSh",
								   				data:"guid="+guid+"&type=rcbx",
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
				   							}
										   }
										});
					   			//提交操作
					   			if(tagg){
					   				confirm(alert1,"",function(){
					   		   			$.ajax({
					   		   	   			url:"${ctx}/wsbx/process/submit",
					   		   	   			data:"guid="+guid+"&type=rcbx",
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
								alert("信息项不完整不能提交！");
							}
						}//success
					});//ajax
         	});

    //批量提交
   	$("#btn_tj").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var yes = 0;
   		var no = 0;
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			var shzt = $(this).attr("shzt");
	   	   		if(shzt=="00"){
	   				guid.push($(this).val());
	   				yes++;
	   			}else{
	   				no++;
	   			}
   	   		});
			var alert1 = "是否提交？";
			if(checkbox.length==1){
				$.ajax({
	   	   			type:"post",
	   	   			url:"${ctx}/wsbx/process/checkWhoSh",
	   	   			data:"type=rcbx&guid="+checkbox.val(),
	   	   			dataType:"json",
	   	   			async:false,
	   	   			success:function(val){
	   	   				alert1 = "单据提交至:"+val.XM+"审核是否提交？";
	   	   			}
	   	   		});
			}
	   	   	confirm(alert1,"",function(){
	   			$.ajax({
	   	   			url:"${ctx}/wsbx/process/submit",
	   	   			data:"guid="+guid.join(",")+"&type=rcbx",
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
   			alert("请选择至少一条信息!");
   		}
   	});
    //批量删除按钮
    $(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var yes = 0;
   		var no = 0;
   		if(checkbox.length>0){
   	   		var guid = [];
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
	   	   			url:"${ctx}/wsbx/rcbx/doDelete",
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
   		var guid = $(this).attr("guid");
		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:"${ctx}/wsbx/rcbx/doDelete",
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
	$("#shztdm").change(function(){
		var shzt = $(this).val();
		location.href = "${ctx}/wsbx/rcbx/goRcbxListPage?mkbh=${mkbh}&shzt="+shzt;
	});
});
function checkBzy(){
	$.ajax({
		url:"${ctx}/wsbx/process/checkBzy",
		data:"",
		type:"post",
		dataType:"json",
		success:function(data){
			if(data){
				$("[name='sbzy']").val("true");
			}else{
				$("[name='sbzy']").val("false");
			}
		}
	});
}
  //选择的方法
function select(guid,xz,type){
	 $.ajax({
		url:"${ctx}/wsbx/process/submit",
		data:"guid="+guid+"&type=rcbx&xz="+xz,
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