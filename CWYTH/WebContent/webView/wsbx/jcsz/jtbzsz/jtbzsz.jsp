<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>交通标准设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
 <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<input type="hidden" name="guid" value="">
    		<input type="hidden" name="czr" value="">
    		<input type="hidden" name="czrq" value="">
    		<div class="search-simple">
				<div class="form-group">
					<label>人员级别</label>
					<input type="text" id="txt_flbh" class="form-control input-radius" name="flbh" table="K" placeholder="请输入人员级别">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_add">增加</button>
	                <button type="button" class="btn btn-default" id="btn_save">保存</button>
	                <button type="button" class="btn btn-default" id="btn_del">批量删除</button> 
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
				        <th>人员级别</th>
				        <th>火车级别</th>
				        <th>轮船级别</th>
				        <th>飞机级别</th>
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
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
   	//新增
   	$("#btn_add").click(function(){
   		$(".dataTables_empty").parents("tr").remove();
		var index = $(".uid:last").attr("id");
		//alert(index);
		if(index==null){
		index = 1;
		}else{
			index++;
		}
		var trObj = document.createElement("tr");
		trObj.setAttribute("id",index);
		//alert()
		if(index%2==0 || index==1){
			trObj.setAttribute("class","odd");	
		}else{
			trObj.setAttribute("class","even");
			
		}
		trObj.setAttribute("role","row");
		trObj.innerHTML = "<td><input type='checkbox' name='guid' value='0'></td>"+
		"<td class='text-center'> <input type='hidden' class='uid' id='"+index+"'>"+index+"</td>"+
		"<td><select id='drp_ryjb"+index+"' class='form-control input-radius' name='ryjb'></select></td>"+
		"<td><select id='drp_hcjb"+index+"' class='form-control input-radius' name='hcjb'></select></td>"+ 
		"<td><select id='drp_lcjb"+index+"' class='form-control input-radius' name='lcjb'></select></td>"+
		"<td><select id='drp_fjjb"+index+"' class='form-control input-radius' name='fjjb'></select></td>"+
		"<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>";
		document.getElementById("bod").appendChild(trObj);
		dictAjax(); 
   	});
 	//删除
	$(document).on("click",".btn_delxx",function(){
		$(this).parents("tr").remove();
		px();
	});
 	$("#btn_search").click(function(){
 		location.reload();
 	});
	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		$(this).parents("tr").remove();
   	   		});
	   		
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   		px();
   	});
	$("#btn_save").click(function(){
   		alert("操作成功！");
   	});
   	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
   		doExp(json,"${ctx}/dwb/expExcel?treedwbh=${dwbh}","单位信息","${pageContext.request.contextPath}",id.join(","));
   	});
   	var columns = [
		 {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
    	   return  '<input type="hidden"  class="uid"  id="' + data + '">'+data+'';
          	},"width":41,"searchable":false,"class":"text-center"},
      	{"data": "RYJB",defaultContent:"",orderable:false,'render': function (data, type, full, meta){
  	         return '<select id="drp_ryjb" class="form-control input-radius" name="ryjb"></select>';	
  	       },"width":400},
	   	{"data": "HCJB",defaultContent:"",orderable:false,'render': function (data, type, full, meta){
	         return '<select id="drp_hcjb" class="form-control input-radius" name="hcjb"></select>';	
	       },"width":400},
	   	{"data": "LCJB",defaultContent:"",orderable:false,'render': function (data, type, full, meta){
	   		return '<select id="drp_lcjb" class="form-control input-radius" name="lcjb"></select>';	
	   		
	         //return '<input type="type" name="sjzt" style="width:100%;border:none;" value="' + data + '" guid = "'+full.YHDM+'">';
	       },"width":400},	
	   	{"data": "FJJB",defaultContent:"",orderable:false,'render': function (data, type, full, meta){
	   		return '<select id="drp_fjjb" class="form-control input-radius" name="fjjb"></select>';	
	   		
	         //return '<input type="type" name="sjzt" style="width:100%;border:none;" value="' + data + '" guid = "'+full.YHDM+'">';
	       },"width":400},	   
	       {"data": "GUID",class:"text-center",'render':function(data, type, full, meta){
		   		return '<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
	      },orderable:false,"width":220}
	];
	//表数据
   	table = getDataTableByListHj("mydatatables","${ctx}/jtbzsz/getPageList",[],columns,0,1,setGroup);

});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
	dictAjax();
});
$(document).on("click",".btn-custom",function(){
	id = $(this).attr("sj");
	select_commonWin("${ctx}/fjscsz/window?controlId="+id,"项目分类信息","920","630");
});
function dictAjax(){
	var id = $("[id^=drp_]");
	var url = "${ctx}/jtbzsz/dict";
	$.each(id,function(i,v){
		var id = $(this).attr("id");
		var params = "name="+id;
		jQuery.getJSON(url,params,function callback(data){
			if(data){
				$.each(data,function(index,value){
					var dm = value.DM;
					var mc = value.MC;
					$("[id='"+id+"']").append("<option value="+dm+">"+mc+"</option>");
				});
			}
		});
	});
}
function px(){
	var xh = $(".uid").parents("td").length;
	$.each($(".uid").parents("td"),function(i,v){
		var index = xh;
		$(this).html("<input type='hidden' class='uid' id='"+index+"' value='"+index+"'>"+index+"");
		xh--;
	});
}
</script>
</html>