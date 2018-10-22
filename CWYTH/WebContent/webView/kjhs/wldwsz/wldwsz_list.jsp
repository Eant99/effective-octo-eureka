<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>往来单位设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">		
		<form id="myform" class="form-inline" action="" style="padding-top: 10px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>单位编号</label>
					<input type="text" id="txt_wlbm" class="form-control" name="wlbh" table="K" placeholder="请输入单位编号">
				</div>
				<div class="form-group">
					<label>户名</label>
					<input type="text" id="txt_dwmc" class="form-control" name="dwmc"  table="K" placeholder="请输入单位名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">

						<div class="form-group">
							<label>单位简称</label>
							<input type="text" id="txt_dwjc" class="form-control input-radius" table="K" name="dwjc" placeholder="请输入单位简称">
						</div>
						<div class="form-group">
							<label>办公电话</label>
							<input type="text" id="txt_bgdh" class="form-control input-radius" table="K" name="bgdh" placeholder="请输入办公电话">
						</div>
						<div class="form-group">
							<label>联系人</label>
							<input type="text" id="txt_lxr" class="form-control input-radius" table="K" name="lxr" placeholder="请输入联系人">
						</div>
						<div class="form-group">
							<label>单位类型</label>
							<select id="drp_dwlx" class="form-control select2" style="width:152px;" name="dwlx" table="K" types="E">
               					<option value="">未选择</option>
	                                <option value="供应商" <c:if test="${wldwsz.dwlx == '01'}">selected</c:if>>供应商</option>
									<option value="主管部门" <c:if test="${wldwsz.dwlx == '02'}">selected</c:if>>主管部门</option>
	                		</select>
						</div>
						<div class="form-group">
							<label>单位地址</label>
						<input type="text" id="txt_dwdz" class="form-control input-radius" table="K" name="dwdz" placeholder="请输入单位地址">
						</div>
						<div class="form-group">
							<label>对公支付</label>
							<select id="drp_dwlx" class="form-control" style="width:152px;" name="sfdgzf" table="K" types="E">
               					<option value="">未选择</option>
	                                <option value="01">是</option>
									<option value="02">否</option>
	                		</select>
						</div>
						<!-- <div class="form-group">
							<label>税号</label>
							<div class="input-group">
								<input type="text" id="txt_sh" class="form-control date" name="sh" table="K" placeholder="请输入税号">		
						   	</div>	
						</div> -->
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <!--  
	               <button type="button" class="btn btn-default" id="btn_xz">下载模板</button>
 	               <button type="button" class="btn btn-default" id="btn_dr">导入</button> 
 	               -->
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
				            <th>单位编号</th>
				            <th>户名</th>
				            <th>单位简称</th>
				            <th>单位类型</th>
				            <th>单位地址</th>
				            <th>联系人</th>           
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
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data":"WLBH","width":100,"render":function(data,type,full,mate){
    	   return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
       }},
       {"data": "DWMC",defaultContent:"","width":130},
       {"data": "DWJC",defaultContent:"","width":30},
       {"data": "DWLX",defaultContent:"","width":30},
       {"data": "DWDZ",defaultContent:"","width":180},
       {"data": "LXR",defaultContent:"","width":30},
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":80}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/wldwsz/getPageList",[2,'asc'],columns,0,1,setGroup);
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		doOperate("${ctx}/wldwsz/goEditPage");
   	});
   

	//导出Excel
	$(document).on("click","#btn_exp",function(){
//    	$("#btn_exp").click(function(){
   		var id = [];
   		var dwbh = $("#txt_wlbm").val();
   		var hm = $("#txt_dwmc").val();
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp(searchJson("searchBox"),"${ctx}/wldwsz/expExcel2","往来单位设置信息","${pageContext.request.contextPath}",id);
   	});
	
   	//编辑按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");
   		doOperate("${ctx}/wldwsz/goEdit1Page?guid="+guid,"U");
   	});
   	//查看按钮
   	$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
   		doOperate("${ctx}/wldwsz/goLookPage?guid="+guid,"L");
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
//      	$("#btn_del").click(function(){
     		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
     		var guid = []
     			 if(checkbox.length>0){
     	   			var guid = [];
     	   			checkbox.each(function(){
     	   				guid.push($(this).val());
     	   			});
     	   			doDel("guid="+guid.join("','"),"${ctx}/wldwsz/wldwDel",function(val){
     	   			window.location.href = "${ctx}/wldwsz/gowldwszListPage";
     	   	   		},function(val){
     	   	   		},guid.length); 
     	   		}else{
     	   			alert("请选择至少一条信息删除！");
     	   		} 
     	});
    //单条删除
 	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).attr("guid");
 		doDel("guid="+guid,"${ctx}/wldwsz/doDelete",function(val){
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