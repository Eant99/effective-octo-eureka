<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目授权</title>

</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<div class="search-simple">
					<div class="form-group">
						<label>模块编号</label>
						<input type="text" id="txt_xmbh" class="form-control" name="mkbh" table="K" placeholder="请输入模块编号">
					</div>
					<div class="form-group">
						<label>模块名称</label>
						<input type="text" id="txt_xmbh" class="form-control" name="mkmc" table="K" placeholder="请输入模块名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
					<div class="btn-group pull-right" role="group">
		               <button type="button" class="btn btn-default" id="btn_add">增加</button>
		               <button type="button" class="btn btn-default" id="btn_show">展示</button>
		               <button type="button" class="btn btn-default" id="btn_hide">不展示</button>	
		         <!--  <button type="button" class="btn btn-default" id="btn_save">保存</button>   -->           
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
								<th><input type="checkbox" class="select-all" /></th>
						 	<!--<th>序号</th>-->
								<th>模块编号</th>
								<th>模块名称</th>
								<th>注意事项内容</th>
								<th>是否展示</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="bod">
				    	</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
	<%@include file="/static/include/public-list-css.inc"%>
	<script>
$(function () {
	var target = "${ctx}/zysxsz";
	//列表数据
	var rBtnXh = 0;//单选框序号
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
//        {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
//           	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "MKBH",defaultContent:"","width":50},
       {"data": "MKMC",defaultContent:"","width":500},
       {"data": "ZYSXNR",defaultContent:"","width":500},
       {"data": "SFXS",defaultContent:"是","render":function (data, type, full, meta){
	   		if(data == "1"){
	   			return '是';
	   		}else return '否';
	   
	   	},"width":20}
	,
       {"data":"GUID","render":function (data, type, full, meta){
	   		if(full.BZS != "1"){
	   			return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
	   		}else return '';
	   
	   	},orderable:false,"width":90}	
      
     ];
	
    table = getDataTableByListHj("mydatatables","${ctx}/zysxsz/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
//     table = getDataTableByListHj("mydatatables","http://192.168.10.155/apis/app/pageListZYSX",[2,'asc'],columns,0,1,setGroup);
//     table = getDataTableByListHj("mydatatables","${ctx}/json/zysx/zysx.json",[2,'asc'],columns,0,1,setGroup);

	//展示不展示
	$(document).on("click","#btn_show",function(){
// 	$("#btn_show").click(function(){
		var checkbox = $(":checkbox").filter(":checked");
		if(checkbox.length==0){
			alert("请至少选择一条信息进行操作！");
			return;
		}
		var ids = [];
		var $this;
		checkbox.each(function() {
			$this = $(this);
			ids.push($this.val());
		});
		var ids = {
	            guid:ids.join("','"),
	            sfxs:"1"
	    };
		$.ajax({
			type:"post",
			url:"${ctx}/zysxsz/Xs",
			data:ids,
			async:true,
			success:function(val){			
				alert("操作成功！");
		 		location.reload();
			}
		});
// 		alert("操作成功！");
// 		location.reload();
	});
	$(document).on("click","#btn_hide",function(){
// 	$("#btn_hide").click(function(){
		var checkbox = $(":checkbox").filter(":checked");
		if(checkbox.length==0){
			alert("请至少选择一条信息进行操作！");
			return;
		}
		var ids = [];
		var $this;
		checkbox.each(function() {
			$this = $(this);
			ids.push($this.val());
		});
		var ids = {
	            guid:ids.join("','"),
	            sfxs:"0"
	    };
		$.ajax({
			type:"post",
			url:"${ctx}/zysxsz/Bxs",
			data:ids,
			async:true,
			success:function(val){			
				alert("操作成功！");
		 		location.reload();
			}
		});
// 		alert("操作成功！");
// 		location.reload();
	});
  	
    //添加按钮
    $(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
		select_commonWin("${ctx}/zysxsz/goAddPage?operateType=C","注意事项内容", "770", "715");
   	});
	    
  //修改按钮 
 	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
		//doOperate("${ctx}/zysxsz/goEditPage?guid="+guid,"U");
   		select_commonWin("${ctx}/zysxsz/goEditPage?operateType=U&guid="+guid,"注意事项内容", "770", "715");
	});
   	
	//授权日志
	$(document).on("click","#btn_info",function(){		
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		//alert(checkbox);
		if(checkbox.length>0){
			doOperate("${pageContext.request.contextPath}/webView/wsbx/jcsz/xmsq/xmsqrz_list.jsp");
   		}else{
   			alert("请选择一条信息!");
   		}
		
	});
 	//单条删除
 	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
		confirm("确定删除该条信息？","",function(){
   			$.ajax({
   	   			//url:ADDRESS+"/srxm/delete",
   	   			url:target+"/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"true",
   	   			success:function(data){
   	   				var result = JSON.getJson(data);
  	   				if(result.success){
						alert("删除成功！");  	   					
	   	   				table.ajax.reload();
  	   				}
   	   			},
   	   			error:function(){
   	   				alert("抱歉，系统出现错误！");
   	   			}
   	   		});
   		});
	});
// 	$(document).on("click",".btn_delxx",function() {
// 		var guid = $(this).parents("tr").find("[name='id']").val();
// 		var path = $(this).parents("tr").find("[name='id']").attr("path");
// 		var xtbz = $(this).parents("tr").find("[name='id']").attr("xtbz");
// 		doDel("guid="+guid+"&path="+path+"&xtbz="+xtbz,"${pageContext.request.contextPath}/xzwhb/delectGjxz",function(val){
//  			table.ajax.reload();
//  		},function(val){
//  		},"1"); 
// 	});
	
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