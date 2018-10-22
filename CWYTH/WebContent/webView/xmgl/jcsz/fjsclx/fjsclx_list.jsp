<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>上传附件类型设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
	.null{
		background-color: wheat;
	}
</style>
</head>
<body>
<div class="fullscreen">
 <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>分类类型</label>
					<input type="text" id="txt_flbh" class="form-control input-radius" name="flbh" table="K" placeholder="请输入分类类型">
				</div>
				<div class="form-group">
					<label>分类状态</label>
					<select id="drp_flzt" class="form-control input-radius" name="flzt">
									<option value="1" <c:if test="${dmb.dmsx == '1'}">selected</c:if>>启用</option>
									<option value="2" <c:if test="${dmb.dmsx == '2'}">selected</c:if>>禁用</option>
								</select>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_add">增加</button>
	                <button type="button" class="btn btn-default" id="btn_save">保存</button>
	                <button type="button" class="btn btn-default" id="btn_enable">启用</button>
                    <button type="button" class="btn btn-default" id="btn_disable">禁用</button>
	                <button type="button" class="btn btn-default" id="btn_del">批量删除</button> 
<!-- 	                <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button> -->
	            </div>
			</div>
        </form>
    </div>
    <form id="formForUpdate" action="" method="post" >
	<div class="container-fluid">
		<div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
				        <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>上级分类</th>
				        <th>附件类型</th>
				        <th>是否必传</th>
				        <th>数据状态</th>
				        <th>操作</th>
				    </tr>
				    </thead>
				    <tbody id="bod">
				    </tbody>
				</table>
            </div>
		</div>
	</div>
	</form>
</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
	//表数据
	var columns = [
		 {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
        return '<input type="checkbox" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
      },"width":10,'searchable': false},
      {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
   	   return  '<input type="hidden"  class="uid"  id="' + data + '">'+data+'';
         	},"width":41,"searchable":false,"class":"text-center"},
     	{"data": "SJFL",defaultContent:"",'render': function (data, type, full, meta){
 	         return '<input type="type" id="" name="sjfl" style="width:85%;border:none;"  value="' + data + '" guid = "'+full.SJFL+'"><span sj="txt_sjfl" id="btn_sjfl" class="btn btn-link btn-custom">选择</span>';
 	       },"width":400},
	   	{"data": "FJLX",defaultContent:"",'render': function (data, type, full, meta){
	         return '<input type="type" name="fjlx" style="width:100%;border:none;" value="' + data + '" guid = "'+full.FJLX+'" placeholder="不同类型请用,隔开">';
	       },"width":400},
	   	{"data": "SFBC",defaultContent:"",'render': function (data, type, full, meta){
	   		var a="",b="";
	   		if(data == "1"){
	   			a="selected;"
	   		}else if(data == "2"){
	   			b="selected";
	   		}
	   		return '<select id="drp_flzt" class="form-control input-radius" name="sfbc">'+
	   		'<option value="1" '+a+'>是</option>'+
	   		'<option value="2" '+b+'>否</option></select>';	
	   		
	         //return '<input type="type" name="sjzt" style="width:100%;border:none;" value="' + data + '" guid = "'+full.YHDM+'">';
	       },"width":400},	
	   	{"data": "SJZT",defaultContent:"",'render': function (data, type, full, meta){
	   		var a="",b="";
	   		if(data == "1"){
	   			a="selected;"
	   		}else if(data == "2"){
	   			b="selected";
	   		}
	   		return '<select id="drp_flzt" class="form-control input-radius" name="sjzt">'+
	   		'<option value="1" '+a+'>启用</option>'+
	   		'<option value="2" '+b+'>禁用</option></select>';	
	   		
	         //return '<input type="type" name="sjzt" style="width:100%;border:none;" value="' + data + '" guid = "'+full.YHDM+'">';
	       },"width":400},	   
	       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
		   		return '<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
	      },orderable:false,"width":220}
	];
  	table = getDataTableByListHj("mydatatables","${ctx}/fjscsz/getPageList2?treeflbh=${flbh}",[2,'asc'],columns,0,1,setGroup);
  	$.each($("#mydatatables").find("[name='sjfl']"),function(){
  		var num = $(this).parents("tr").find(".uid").attr("id");
  		$(this).attr("id","txt_sjfl"+num);
  	});
  	//
$(function () {
   	//新增
   	$("#btn_add").click(function(){
	//doOperate("${ctx}/webView/kjhs/yhzhgl/yhzhgl_edit.jsp","C");
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
		trObj.innerHTML = "<td><input type='checkbox' value='0'></td>"+
		"<td class='text-center'> <input type='hidden' class='uid' id='"+index+"'>"+index+"</td>"+
		"<td> <input type='text' id='txt_sjfl"+index+"' name='sjfl' style='width:85%;border:none;' /><span sj='txt_sjfl"+index+"' id='btn_sjfl' class='btn btn-link btn-custom'>选择</span></td>"+
		"<td><input type='text' name='fjlx'  style='width:100%;border:none;' value='' placeholder='不同类型请用,隔开'/></td>"+ 
		"<td> <select id='drp_sfbc' class='form-control input-radius' name='sfbc'>"+
   		"<option value='1'>是</option>"+
   		"<option value='2'>否</option></select></td>"+
		"<td> <select id='drp_flzt' class='form-control input-radius' name='sjzt'>"+
   		"<option value='1'>启用</option>"+
   		"<option value='2'>禁用</option></select></td>"+
		"<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>";
		document.getElementById("bod").appendChild(trObj);

   	});
   	
   	//检查是否为空
   	function checkNull(items){
   		var tag = false;
   		$.each(items,function(){
   			var val = $(this).val();
   			if(val == "" || val == "0"){
   				tag = true;
   				$(this).addClass("null");
   			}
   		});
   		return tag;
   	}
   	$(document).on("focus","input",function(){
   		$(this).removeClass("null");
   	})
  //排序
   	function px(){
   		var xh = $(".uid").parents("td").length;
   		$.each($(".uid").parents("td"),function(i,v){
   			var index = xh;
   			$(this).html("<input type='hidden' class='uid' id='"+index+"' value='"+index+"'>"+index+"");
   			xh--;
   		});
   	}
   	//修改
   	$(document).on("click",".btn_upd",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/xmfl/goEditPage?flbh="+dwbh,"U");
	});
  
  //单条删除
	$(document).on("click",".btn_delxx",function(){
		var $tr = $(this).parents("tr");
//		confirm("确定删除？","",function(){
				$tr.remove();
				px();
//				alert("删除成功！");
//    			$.ajax({
//    	   			url:ADDRESS+"/fjsclx/fjsclx_list",
//    	   			data:"guid="+guid,
//    	   			type:"post",
//    	   			async:"true",
//    	   			success:function(data){
//    	   				var result = JSON.getJson(data);
//   	   				if(result.success){
// 						alert("删除成功！");  	   					
// 	   	   				table.ajax.reload();
//   	   				}
//    	   			}
//    	   		});
//   		});
	});
    //批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[type='checkbox']").filter(":checked");
   		if(checkbox.length>0){
//   			confirm("确定删除"+checkbox.length+"条信息？","",function(){
   				$.each(checkbox,function(){
   					$(this).parents("tr").remove();
   				})
   				px();
//   				alert("删除成功！");
//    				var guid = [];
//    	   	   		checkbox.each(function(){
//    	   	   			guid.push($(this).val());
//    	   	   		});
//    	   			$.ajax({
//    	   	   			url:"${ctx}/fjsclx/fjsclx_list",
//    	   	   			data:"guid="+guid.join("','"),
//    	   	   			type:"post",
//    	   	   			async:"true",
//    	   	   			success:function(data){
//    	   	   				var result = JSON.getJson(data);
//    	   	   				if(result.success){
//    							alert("删除成功！");  	   					
// 	   	   	   				table.ajax.reload();
//    	   	   				}
//    	   	   			}
//    	   	   		});
//   	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//保存
	$("#btn_save").click(function(){
		var tag = checkNull($("[name='sjfl']"));
		tag |= checkNull($("[name='fjlx']"));
		if(tag){
			alert("必填项不能为空！");
			return;
		}
		var json = $("#formForUpdate").serializeObject("sjfl","sjzt");  //json对象
		var jsonStr = JSON.stringify(json);  //json字符串
		$.ajax({
  	   			url:ADDRESS+"/fjsclx/save?",
  	   			type:"post",
  	   			data:"json="+jsonStr,
  	   			async:"true",
  	   			success:function(data){
  	   				var result = JSON.getJson(data);
  	   				if(result.success){
						alert("保存成功！");  	   					
	   	   				table.ajax.reload();
  	   				}
  	   			}
  	   		});
   	});
	//启用
	$("#btn_enable").click(function(){
		var checkbox = $("#mydatatables").find("[type='checkbox']").filter(":checked");
		if(checkbox.length ==0){
			alert("请至少选中一条数据！");
			return;
		}
		$.each(checkbox,function(){
			$(this).parents("tr").find("[name='sjzt']").val("1");
		})
	})
	//禁用
	$("#btn_disable").click(function(){
		var checkbox = $("#mydatatables").find("[type='checkbox']").filter(":checked");
		if(checkbox.length ==0){
			alert("请至少选中一条数据！");
			return;
		}
		$.each(checkbox,function(){
			$(this).parents("tr").find("[name='sjzt']").val("2");
		})
	})
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
//弹窗
$(document).on("click",".btn-custom",function(){
	id = $(this).prev().attr("id");
	select_commonWin("${ctx}/fjscsz/window?controlId="+id,"项目分类信息","920","630");
});
</script>
</html>