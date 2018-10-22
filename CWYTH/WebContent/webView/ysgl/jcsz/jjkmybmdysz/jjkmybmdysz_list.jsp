<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>经济科目与部门对应设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
	.null{
		background-color: wheat;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>部门名称</label>
					<input type="text" id="txt_bmmc" class="form-control" name="bmmc"  table="K" placeholder="请输入部门名称">
				</div>
				<div class="form-group">
					<label>经济科目名称</label>
					<input type="text" id="txt_jjkmmc" class="form-control" name="kmmc"  table="K" placeholder="请输入经济科目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default addBtn" id="btn_add">增加</button>
	             <!--   <button type="button" class="btn btn-default" id="btn_save">保存</button> -->
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
			</div>
		</form>
	</div>
	<form id="myform1" action="" method="post" >
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>	
				            <th>序号</th>		            
				            <th>部门名称</th>
				            <th>经济科目名称</th>
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
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
   var columns = [
	   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
	       },"width":10,'searchable': false},
	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	       	   return  '<input type="hidden"  class="uid"  id="' + data + '">'+data+'';
	             	},"width":41,"searchable":false,"class":"text-center"},
	       {"data": "BMBH",defaultContent:""},
	       {"data": "JJKMBH",defaultContent:""},
	    
	       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
		   		return '<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
	      },orderable:false,"width":220}
	];
    table = getDataTableByListHj("mydatatables","${ctx}/Jjkmybmdysz/getPageLists?treeid=${param.dwbh}",[2,'asc'],columns,0,1,setGroup);
  //检查是否为空
   	function checkNull(items){
   		var tag = false;
   		$.each(items,function(){
   			var val = $(this).val;
   			if(val == "" ){
   				tag = true;
   				//$(this).addClass("null");
   			}
   		});
   		return tag;
   	}
//    	$(document).on("focus","input",function(){
//    		$(this).removeClass("null");
//    	})
  //增加
	$(document).on("click","[class*=addBtn]",function(){		
		tag = checkNull();
	
		if("${param.bmh}"==''){
			alert("请先选择部门名称！");
			return;
		}
		
		var tr = $(this).parents("tr").attr("id");
		select_commonWin("${ctx}/Jjkmybmdysz/window?controlId="+tr+"&type=jb","经济科目信息","920","630");	
		
	});
	//保存按钮
	$("#btn_save").click(function(){
		var json = $("#myform1").serializeObject("bmbh","jjkmbh");  //json对象
		var jsonStr = JSON.stringify(json);  //json字符串

		$.ajax({
  	   			url:"${ctx}/Jjkmybmdysz/addJjkmybmdysz",
  	   			type:"post",
  	   			data:"json="+jsonStr,
  	   			async:"false",
  	   			success:function(data){	
					alert("保存成功！");  	   					
  	   			}
  	   		});
   	});
	
	$.fn.serializeObject1 = function(start,end){
		var f = {"list":[]};
	    var a = this.serializeArray();
	    var o = {};
	    $.each(a, function() {
	    	if (this.name == start) {
	        	o = {};
	        	o[this.name] = this.value;
	        } else if(this.name == end){
	        	o[this.name] = this.value;
	        	f["list"].push(o);
	        }else{
	        	o[this.name] = this.value;
	        }
	    });
	    	console.log(JSON.stringify(f));
	    return f;
	};

	//导出Excel
   	$("#btn_exp").click(function(){
   		
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/Jjkmybmdysz/expExcel?treeid=${param.dwbh}&BMBH="+guid.join("','"),"经济科目与部门对应设置信息","${pageContext.request.contextPath}",guid.join("','"));
   	});
	
	//单条删除
//    	$(document).on("click",".btn_delxx",function(){
//    		var guid = $(this).parents("tr").find("[name='guid']").val();
   		
//    		 confirm("确定删除？","",function(){
//    			$.ajax({
//    				url:ADDRESS+"/jjkmybmdysz/delete",
//    	   			data:"guid="+guid, 	   			
//    	   			type:"post",
//    	   			async:"false",
//    	   			success:function(val){
//    	   				alert(val);
//    	   			window.location.href = "${ctx}/webView/ysgl/jcsz/jjkmybmdysz/jjkmybmdysz_list.jsp";
//    	   			}
//    	   		});
//    		}); 
// 	});
  //单条删除
 	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
 		doDel("guid="+guid,"${ctx}/Jjkmybmdysz/doDelete",function(val){
   			table.ajax.reload();
   			//....
   		},function(val){
   			//....
   		},"1");
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
var num=0;
function addJb(kmxx,id,kmbh){
  // alert(kmxx.length);
   for(i=0;i<kmxx.length;i++){
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
			//
			if(index%2==0 || index==1){
				trObj.setAttribute("class","odd");	
			}else{
				trObj.setAttribute("class","even");				
			}
			trObj.setAttribute("role","row");
			trObj.innerHTML = "<td><input type='checkbox' value='0'></td>"+
			"<td class='text-center'> <input type='hidden' class='uid' id='"+index+"'>"+index+"</td>"+
			"<td>${param.qc}<input type='hidden'  name='bmbh' value='${param.bmh}' /></td>"+ 
			"<td>"+kmxx[num]+"<input type='hidden' id='txt_jjkmbh"+index+"' name='jjkmbh' style='width:100%;border:none;' value='"+kmbh[num]+"'  readonly/></td>"+
			"<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>";
			document.getElementById("bod").appendChild(trObj);
			num++;
   }
}
function autosave(){
	var json = $("#myform1").serializeObject("bmbh","jjkmbh");  //json对象
	var jsonStr = JSON.stringify(json);  //json字符串

	$.ajax({
	   			url:"${ctx}/Jjkmybmdysz/addJjkmybmdysz",
	   			type:"post",
	   			data:"json="+jsonStr,
	   			async:"false",
	   			success:function(data){	
				//alert("保存成功！");  	   					
	   			}
	   		});
}
</script>
</body>
</html>