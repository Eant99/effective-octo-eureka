<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.dataTables_scrollHeadInner{
		width:600px ! important;
	}
	table.dataTable{
		width:600px ! important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>账&emsp;&emsp;号</label>
					<input type="text" id="txt_rygh" class="form-control" name="rygh" table="A" placeholder="请输入用户帐号">
				</div>
				<div class="form-group">
					<label>姓&emsp;&emsp;名</label>
					<input type="text" id="txt_xm" class="form-control" name="xm"  table="A" placeholder="请输入用户姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
<!-- 				<div id="btn_search_more"> -->
<!-- 					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span> -->
<!-- 					<div class="search-more"> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>所在单位</label> -->
<!-- 							<div class="input-group"> -->
<!-- 								<input type="text" id="txt_dwbh" class="form-control input-radius" name="dwbh" value="" types="D" table="A" placeholder="请选择所在单位"> -->
<!-- 		                    	<span class="input-group-btn"><button type="button" id="btn_dwbh" class="btn btn-link">选择</button></span> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>性&emsp;&emsp;别</label> -->
<!-- 							<select id="txt_xb" class="form-control" style="width:150px;" name="xb" table="A" types="E"> -->
<!-- 			                	<option value="">未选择</option> -->
<%-- 			                	<c:forEach var="sexList" items="${sexList}"> --%>
<%-- 				               		<option value="${sexList.DM}" <c:if test="${ryb.XB == sexList.DM}">selected</c:if>>${sexList.MC}</option> --%>
<%-- 				            	</c:forEach> --%>
<!-- 			                </select> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>专业职称</label> -->
<!-- 							<select id="txt_zyzc" class="form-control select2" style="width:150px;" name="zyzc" table="A" types="E"> -->
<!-- 			                     <option value="">未选择</option> -->
<%-- 			                     <c:forEach var="zyzcList" items="${zyzcList}"> --%>
<%-- 			               			<option value="${zyzcList.DM}" <c:if test="${ryb.zyzc == zyzcList.DM}">selected</c:if>>${zyzcList.MC}</option> --%>
<%-- 			               		</c:forEach> --%>
<!-- 		               		</select> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>文化程度</label> -->
<!-- 							<select id="txt_whcd" class="form-control select2" style="width:150px;" name="whcd" table="A" types="E"> -->
<!-- 			                     <option value="">未选择</option> -->
<%-- 			                     <c:forEach var="whcdList" items="${whcdList}"> --%>
<%-- 			               			<option value="${whcdList.DM}" <c:if test="${ryb.whcd == whcdList.DM}">selected</c:if>>${whcdList.MC}</option> --%>
<%-- 			               		 </c:forEach> --%>
<!-- 			                 </select> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>人员状态</label> -->
<!-- 							<select id="drp_ryzt" class="form-control" style="width:150px;" name="ryzt" table="A" types="E"> -->
<!-- 				                 <option value="">未选择</option> -->
<!-- 				                 <option value="1">正常</option> -->
<!-- 			                     <option value="0">禁用</option> -->
<!-- 			               	</select> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>调入日期</label> -->
<!-- 							<div class="input-group"> -->
<!-- 								<input type="text" id="txt_drrq_begin" class="form-control date" name="drrq" types="TL" table="A" data-format="yyyy-MM-dd" placeholder="开始日期"> -->
<!-- 								<span class='input-group-addon'> -->
<!-- 							    	<i class="glyphicon glyphicon-calendar"></i> -->
<!-- 							   	</span> -->
<!-- 						   	</div> -->
<!-- 							<label>-</label> -->
<!-- 							<div class="input-group"> -->
<!-- 								<input type="text" id="txt_drrq_end" class="form-control date" name="drrq" types="TH" table="A" data-format="yyyy-MM-dd" placeholder="截止日期"> -->
<!-- 								<span class='input-group-addon'> -->
<!-- 							    	<i class="glyphicon glyphicon-calendar"></i> -->
<!-- 						   		</span> -->
<!-- 							</div> -->
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
				<button type="button" class="btn btn-default" id="btn_mmcs">密码初始化</button>
				<button type="button" class="btn btn-default" id="btn_qy_new">启用</button>
	            <button type="button" class="btn btn-default" id="btn_jy_new">禁用</button>
				<div class="btn-group pull-right" role="group">
	               <!--  <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <button type="button" class="btn btn-default" id="btn_assig">批量赋值</button>
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>-->
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
				            <th>账号</th>
				            <th>姓名</th>
<!-- 				            <TH>性别</TH> -->
<!-- 				            <TH>文化程度</TH> -->
<!-- 				            <TH>调入日期</TH> -->
<!-- 				            <TH>专业职称</TH> -->
				            <th>所在单位</th>
<!-- 				            <th>人员状态</th> -->
<!-- 				            <th>用户角色</th> -->
							<th>人员状态</th>
<!--  				            <th>操作</th>  -->
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
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "RYBH",orderable:false, 'render': function (data, type, full, meta){
            return '<input type="checkbox" name="id" class="keyId" value="' + data + '" rygh = "'+full.RYGH+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
       	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "RYGH",defaultContent:"","width":40},
       {"data": "XM",'render':function(data,type,full,meta){
    		return '<a href="javascript:void(0);" class="btn  btn-link rylook" rybh="'+full.RYBH+'" path="${ctx}">'+ data +'</a>';
       },"width":50},
//        {"data": "XB",defaultContent:""},
//        {"data": "WHCD",defaultContent:""},
//        {"data": "DRRQ",defaultContent:""},
//        {"data": "ZYZC",defaultContent:""},
        {"data": "DWBH",defaultContent:"","width":180},
        {"data": "RYZT",defaultContent:"","width":30},
//       {"data": "RYZT",defaultContent:""},
//        {"data": "yhjs",defaultContent:""},
//     	   {"data": "RYBH",'render':function(data, type, full, meta){
//  	   		return '<a href="javascript:void(0);" class="btn btn-link btn_updpass">修改密码</a>';
//         },orderable:false,"width":220,class:"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/ryb/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
  
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
   		doOperate("${ctx}/ryb/goRybPage?dwbh=${dwbh}","C");
   	});
	//导出Excel
	$(document).on("click","#btn_exp",function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
   		doExp(json,"${ctx}/ryb/expExcel?treedwbh=${dwbh}","人员信息","${ctx}",id.join(","));
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var rybh = $(this).parents("tr").find("[name='id']").val();
	   	doOperate("${ctx}/ryb/goRybPage?rybh="+rybh,"U");
   	});
    //单位权限
  	$(document).on("click",".btn_dwqx",function(){
   		var rybh = $(this).parents("tr").find("[name='id']").val();
   		select_commonWin("${ctx}/ryb/goGlqxPage?rybh="+rybh,"管理权限设置","1050","680");
  	});
    //操作权限
    $(document).on("click",".btn_czqx",function(){
   		var rybh = $(this).parents("tr").find("[name='id']").val();
   		select_commonWin("${ctx}/ryb/goCzqxPage?rybh="+rybh,"操作权限设置","1100","700","yes");
   	});
    //批量删除按钮
    $(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
   		if(checkbox.length>0){
   	   		var rybh = [];
   	   		checkbox.each(function(){
   	   		rybh.push($(this).val());
   	   		});
	   		doDel("rybh="+rybh.join(","),"${ctx}/ryb/doDelete",function(val){
	   			table.ajax.reload();
	   		},function(val){
	   			
	   		},rybh.length);
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var rybh = $(this).parents("tr").find("[name='id']").val();
		doDel("rybh="+rybh,"${ctx}/ryb/doDelete",function(val){
			table.ajax.reload();
		},function(val){
			
		},"1");
	});
	//批量赋值按钮
	$(document).on("click","#btn_assig",function(){
   		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
   		if(checkbox.length>0){
   			var rybh = [];
   			checkbox.each(function(){
   				rybh.push($(this).val());
   			});
   			confirm("确认要批量赋值"+rybh.length+"条信息？",{title:"提示"},function(index){
   				select_commonWin("${ctx}/ryb/goPlfzPage?rybh="+rybh.join(","),"批量赋值","400","450");
   				close(index);
   			}); 
   		}else{
   			alert("请选择至少一条信息赋值!");
   		}
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
//密码初始化
$("#btn_mmcs").click(function(){
		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
		if(checkbox.length==0){
			alert("请至少选择一条信息进行操作！");
			return;
		}else{
			confirm("确定对所选择的用户进行密码初始化操作？",{title:"提示信息"},function(){
				var rybh = [];
	   			checkbox.each(function(){
	   				rybh.push($(this).val());
	   			});
				$.ajax({
					url:"${ctx}/ryb/csmm",
					data:"rybh="+rybh.join(","),
					type:"post",
					dataType:"json",
					success:function(data){
						if(data.success){
							alert("密码初始化成功！");
						}else{
							alert("密码初始化失败！");
						}
					}
				});
			});
		}
	});
	//启用禁用
	$(document).on("click","[id$=_new]",function(){
		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
		var id = $(this).attr("id");
		var msg = "";
		if(id=="btn_qy_new"){
			msg = "确定启用所选择的用户？";
		}else{
			msg = "确定禁用所选择的用户？";
		}
		if(checkbox.length==0){
			alert("请至少选择一条信息进行操作！");
			return;
		}else{
			confirm(msg,{title:"提示信息"},function(){
				var rybh = [];
	   			checkbox.each(function(){
	   				rybh.push($(this).val());
	   			});
				$.ajax({
					url:"${ctx}/ryb/qyjy",
					data:"rybh="+rybh.join(",")+"&type="+id,
					type:"post",
					dataType:"json",
					success:function(data){
						if(data.success){
							alert("操作成功！");
						}else{
							alert("操作失败！");
						}
					}
				});
				table.ajax.reload();
			});
		}
	});
	$(document).on("click",".btn_updpass",function(){
// 	$("#changeMM").click(function(){
		var rybh = $(this).parents("tr").find(".keyId").val();
		console.log("-----"+rybh);
		select_commonWin("${pageContext.request.contextPath}/ryb/goSzxxPage?rybh="+rybh,"修改密码","450","300");
	});
</script>
</body>
</html>