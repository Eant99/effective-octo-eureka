<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html> 
<head> 
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产使用年限设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">    
			<div class="search-simple">
				<div class="form-group">
					<label>分&ensp;类&ensp;号</label>
					<div class="input-group">
						<input type="text" id="sel_flh" types="F" class="form-control input-radius" table="T" name="flh" placeholder="请选择分类号">
						<span class="input-group-btn"><button type="button" id="btn_flh" class="btn btn-link ">选择</button></span>
					</div>
				</div>
				<div class="form-group">
					<label>分类名称</label>
					<input type="text" id="txt_flmc" class="form-control" table="t" name="flmc" placeholder="请输入分类名称">
				</div>
				<div class="form-group">
					<label>预计使用年限</label>
					<input type="text" id="txt_synx" class="form-control int" name="synx"  table="t" placeholder="请输入使用年限">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
			       <button class="btn btn-default" type="button" id="btn_save">
			                     保存
			       </button>
			       <button class="btn btn-default" type="button" id="btn_upd">批量赋值</button>
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
							<th>分类号</th>
							<th>分类名称</th>
							<th>预计使用年限</th>
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
$(function() {
	$("#sel_flh").bindChange("${pageContext.request.contextPath}/suggest/getXx","FC");
  	//分类代码弹窗
	$("#btn_flh").click(function(){
		select_commonWin("${pageContext.request.contextPath}/zcfltree/goWindow?controlId=sel_flh&&Next=C","分类信息","920","630");
    });
	//左侧财政分类树，编号
	var bzdm = "${param.flh}"; 
	//高级查询的展开与隐藏
    $(".btn_search_more").on("click", function(e){
		$(".btn_search_more").toggleClass("dropup");
		$("#searchBox .row:not(:first-child)").toggleClass("hide");
		$(".center-content").toggleClass("hide");
	});
	//列表数据
	var columns = [
	    {"data": "BZDM",orderable:false, "render": function (data, type, full, meta){
	     return '<input type="checkbox" class="keyId" name="bzdm" value="' + data + '" bzdm = "'+full.BZDM+'"zjff="'+full.ZJFF+'"synx="'+full.SYNX+'"jczl="'+full.JCZL+'">';
		},"width":10,searchable: false},
		{"data":"_XH",orderable:false,"width":30,searchable: false,"class":"text-center"},
		{"data": "FLH",defaultContent:""},
        {"data": "FLMC",defaultContent:""},
        {"data": "SYNX","render":function (data, type, full, meta){
      	 return '<input type="text" name="synx" style="width:80px;" class="text-right number" value="' + data + '"/>'; 
   		},"width":100,orderable:false}
	];
	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/synxsz/getPageList?treebzdm="+bzdm+"&czflbh=6",[2,'asc'],columns,0,1,setGroup);	
	//批量赋值 														
	$("#btn_upd").click(function() {
		var checkbox = $("#mydatatables").find("[name='bzdm']").filter(":checked");
		var bzdm = [];
		if(checkbox.length>0){
			checkbox.each(function(){ 
   				bzdm.push($(this).val());
   			});
		//confirm("确认要批量赋值"+bzdm.length+"条信息",{title:"提示"},function(index){
		    select_commonWin("${pageContext.request.contextPath}/synxsz/getPlfzPage?bzdm="+bzdm.join(",")+"&czflbh=6","批量赋值", "400", "450");
		    //close(index);
		//});
		}else{
			alert("请选择至少一条信息赋值");
		}
	});
	//保存按钮
   	$("#btn_save").click(function(){ 
    		var checkbox = $("#mydatatables").find("[name='bzdm']").filter(":checked");
   		if(checkbox.length>0){
   			var bzdm = [];
   			var synx = [];
   			checkbox.each(function(){ 
   				bzdm.push($(this).val());
   				var $synx = $(this).parents("tr").find("[name='synx']").val();
   				synx.push($synx);
   			}); 
  	        $.ajax({  
		       	type:"post",
		       	data:"bzdm="+bzdm.join(",")+"&synx="+synx.join(",")+"&czflbh=6", 
		       	url:"${pageContext.request.contextPath}/synxsz/doSave",
		       	dataType:"json",
		       	success:function(val){
		       		alert("保存成功！"); 
		       	},
		       	error:function(){
		       		alert("数据请求错误！");
		       	}
	        });    
    		} 
   		else{
   			alert("请选择至少一条信息保存");
   		} 
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