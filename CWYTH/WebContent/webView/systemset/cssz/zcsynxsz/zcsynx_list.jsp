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
					<input type="text" id="sel_flh" class="form-control input-radius" table="T" name="flh" placeholder="请输入分类号">
				</div>
				<div class="form-group">
					<label>分类名称</label>
					<input type="text" id="txt_flmc" class="form-control" table="t" name="flmc" placeholder="请输入分类名称">
				</div> 
				<div class="form-group">
					<label>预计使用年限</label>
					<input type="text" id="txt_synx" style="text-align: right;" class="form-control int" name="synx"  table="t" placeholder="请输入使用年限　　　　　">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
					<button type="button" class="btn btn-default" id="btn_save">保存</button>
					<button type="button" class="btn btn-default" id="btn_plfz">批量赋值</button>
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
	//左侧财政分类树，编号
	var bzdm = "${param.flh}"; 
	//列表数据
	var columns = [
	    {"data": "BZDM",orderable:false, "render": function (data, type, full, meta){
	     return '<input type="checkbox" class="keyId" name="bzdm" value="' + data + '" bzdm = "'+full.BZDM+'"zjff="'+full.ZJFF+'"synx="'+full.SYNX+'"jczl="'+full.JCZL+'">';
		},"width":10,searchable: false},
		{"data":"_XH",orderable:false,"width":41,searchable: false,"class":"text-center"},
		{"data": "FLH",defaultContent:""},
        {"data": "FLMC",defaultContent:""},
        {"data": "SYNX","render":function (data, type, full, meta){ 
      	 return '<input type="text" name="synx" style="width:80px;" class="text-right int" value="' + data + '"/>'; 
   		},"width":100,orderable:false},
	];
	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/synxsz/getPageList?treebzdm="+bzdm,[2,'asc'],columns,0,1,setGroup);	
	//批量赋值 
	$("#btn_plfz").click(function() {
		var checkbox = $("#mydatatables").find("[name='bzdm']").filter(":checked");
		var bzdm = [];
		if(checkbox.length>0){
			checkbox.each(function(){ 
   				bzdm.push($(this).val());
   			});
			select_commonWin("${pageContext.request.contextPath}/synxsz/getPlfzPage?bzdm="+bzdm.join(","),"批量赋值", "400", "450");
	    	close(index);
		}else if('${param.flh}'!=''){
			select_commonWin("${pageContext.request.contextPath}/synxsz/getPlfzPage?flh=${param.flh}","批量赋值", "400", "450");
	    	close(index);
		}else{
			alert("请选择至少一条信息或者某一大类进行批量赋值！");
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
		       	data:"bzdm="+bzdm.join(",")+"&synx="+synx.join(","), 
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
   			alert("请选择至少一条信息保存！");
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